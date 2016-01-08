/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : IkkatsuJuriSaveAction.java
 *    Description : 一括受理処理を行うアクション。
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/14    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CheckListInfo;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 一括受理処理を行うアクション
 * 
 * @author masuo_t
 */
public class IkkatsuJuriSaveAction extends BaseAction {

	/** 状況IDが04(学振受付中)のものを表示 */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_GAKUSIN_SHORITYU		//学振受付中
	};
		
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//2005/04/20 削除 ここから-------------------------------------
		//理由 処理中画面表示用にトークンのチェックをIkkatsuJuriCheckで行うように変更したため
		//-----ActionErrorsの宣言（定型処理）-----
		//ActionErrors errors = new ActionErrors();

		//-----取得したトークンが無効であるとき
		//if (!isTokenValid(request)) {
		//	errors.add(
		//		ActionErrors.GLOBAL_ERROR,
		//		new ActionError("error.transaction.token"));
		//	saveErrors(request, errors);
		//	return forwardTokenError(mapping);
		//}
		
		//------トークンの削除	
		//resetToken(request);
		//削除 ここまで-------------------------------------------------------------------

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//検索条件の取得
		CheckListInfo checkInfo = container.getCheckListInfo();
		
		//-------▼ VOにデータをセットする。
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();
	
		//検索条件の設定
		if(checkInfo.getJigyoCd() != null && !checkInfo.getJigyoCd().equals("")){
			searchInfo.setJigyoCd(checkInfo.getJigyoCd());
		}
		if(checkInfo.getShozokuCd() != null && !checkInfo.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(checkInfo.getShozokuCd());
		}
		//2005.11.18 iso 所属機関名が抜けていたので追加
		if(!StringUtil.isBlank(checkInfo.getShozokuName())){
			searchInfo.setShozokuName(checkInfo.getShozokuName());
		}
	
		//状況IDが04(学振受付中)のものを表示 
		searchInfo.setSearchJokyoId(JIGYO_IDS);
		//20060216  dhy start
		CheckListSearchForm checkForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
		searchInfo.setJigyoKubun(checkForm.getJigyoKbn());
		//20060216   end

		//検索実行
		Page result = 
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectListData(
				container.getUserInfo(),
				searchInfo);
		if (result == null) {
			return forwardFailure(mapping);
		}
		
		//2005.05.22 iso 一括受理のパフォーマンス改善
//		//------所属CDの配列
//		ArrayList shozokuCdArray = new ArrayList();
//		//------事業IDの配列
//		ArrayList jigyoIdArray = new ArrayList();
//		//------SYSTEM_NOの配列
//		ArrayList systemNoArray = new ArrayList();

		//------所属CDの配列
		ArrayList shozokuCdArray = new ArrayList(result.getSize());
		//------事業IDの配列
		ArrayList jigyoIdArray = new ArrayList(result.getSize());
		//------SYSTEM_NOの配列
		ArrayList systemNoArray = new ArrayList(result.getSize());
		//何度も同じ組織表チェックを繰り返すバグを修正
		//所属CD+事業IDを一意キーとして格納するSet
		Set beforeKeySet = new HashSet(result.getSize());
		//組織表チェック用の所属CD
		ArrayList checkShozokuCdArray = new ArrayList(result.getSize());
		//組織表チェック用の事業ID
		ArrayList checkJigyoIdArray = new ArrayList(result.getSize());
		
		
		//------申請者ID項目名：SHINSEISHA_ID
		String shozokuCd = ShinseiSearchInfo.ORDER_BY_SHOZOKU_CD;
		String jigyoId = ShinseiSearchInfo.ORDER_BY_JIGYO_ID;
		String systemNo = ShinseiSearchInfo.ORDER_BY_SYSTEM_NO;

		//------申請者の数分繰り返し
		for (int i = 0; i < result.getSize(); i++) {

			//------各申請者の情報を取得
			HashMap juriDataMap = (HashMap) result.getList().get(i);

			Object shozokuData = juriDataMap.get(shozokuCd);
			Object jigyoData = juriDataMap.get(jigyoId);
			Object systemData = juriDataMap.get(systemNo);
			//------申請者IDにデータがある場合は配列に格納	
			if (shozokuData != null && !shozokuData.equals("")
				&& jigyoData != null && !jigyoData.equals("")
				&& systemData!= null && !systemData.equals("")) {
					shozokuCdArray.add(shozokuData);
					jigyoIdArray.add(jigyoData);
					systemNoArray.add(systemData);

					//2005.05.22 iso 一括受理のパフォーマンス改善
					if(!beforeKeySet.contains(shozokuData.toString() + jigyoData.toString())) {
						//過去にない所属CD+事業IDなら、チェック用変数に格納
						checkShozokuCdArray.add(shozokuData);
						checkJigyoIdArray.add(jigyoData);
						//今回の所属CD+事業IDキーを一意キーとして登録
						beforeKeySet.add(shozokuData.toString() + jigyoData.toString());
					}
			}
		}
		ISystemServise servise =
			getSystemServise(IServiceName.CHECKLIST_MAINTENANCE_SERVICE);

// 20050823 一括受理時に研究者マスタのチェックを実行する
		List lstErrors = new ArrayList();
		CheckListSearchInfo kenkyuCheck = new CheckListSearchInfo();
		kenkyuCheck.setJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);
		//20060216    dhy  update
		//kenkyuCheck.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
		CheckListSearchForm searchForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
		kenkyuCheck.setJigyoKubun(searchForm.getJigyoKbn());
		//20060216   end
		try{
			lstErrors = servise.IkkatuKenkyushaExist(
					container.getUserInfo(),
					kenkyuCheck,
					//2005.05.22 iso 一括受理のパフォーマンス改善
//					shozokuCdArray,
//					jigyoIdArray
					checkShozokuCdArray,
					checkJigyoIdArray
					);
			for(int n=0; n<lstErrors.size(); n++){
				ActionError error = new ActionError("errors.5051",lstErrors.get(n));
				errors.add("kenkyuExists", error);
			}
		}catch(ApplicationException ex){
			ActionError error = new ActionError("errors.4002");
			errors.add("受理登録でエラーが発生しました。", error);
		}finally{
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
		}

// 20050721
//		servise.juriAll(container.getUserInfo(), shozokuCdArray, jigyoIdArray, systemNoArray);
		servise.juriAll(container.getUserInfo(), shozokuCdArray, jigyoIdArray, systemNoArray, null);
// Horikoshi

		return forwardSuccess(mapping);
	}
}