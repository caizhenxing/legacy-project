/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsaJokyo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoSearchInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CSV出力アクションクラス。
 * 
 * ID RCSfile="$RCSfile: CsvOutAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:16 $"
 */
public class CsvOutAction extends BaseAction {
	
	/**
	 * CSVファイル名の接頭辞。
	 */
	//2006.12.08 iso 件数一覧とファイル名を分けるよう変更
//	public static final String filename = "SHINSAJOKYO";

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
			
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//検索条件の取得
		ShinsaJokyoSearchForm searchForm = (ShinsaJokyoSearchForm)form;
			
		//-------▼ VOにデータをセットする。
		ShinsaJokyoSearchInfo searchInfo = new ShinsaJokyoSearchInfo();

//		searchInfo.setValues(searchForm.getValues());
		searchInfo.setShinsainNo(searchForm.getShinsainNo());
		searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());
		searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());
		searchInfo.setNameKanaSei(searchForm.getNameKanaSei());
		searchInfo.setNameKanaMei(searchForm.getNameKanaMei());
		searchInfo.setShozokuCd(searchForm.getShozokuCd());
		searchInfo.setNendo(searchForm.getNendo());
		searchInfo.setKaisu(searchForm.getKaisu());
		searchInfo.setKeiName(searchForm.getKeiName());
		searchInfo.setRigaiJokyo(searchForm.getRigaiJokyo());			//利害関係入力完了状況 2007/5/8
		searchInfo.setShinsaJokyo(searchForm.getShinsaJokyo());
		searchInfo.setLoginDate(searchForm.getLoginDate());				//最終ログイン日を追加	2005/10/25
		searchInfo.setRigaiKankeisha(searchForm.getRigaiKankeisha());	//利害関係者追加		2005/10/25
		searchInfo.setSeiriNo(searchForm.getSeiriNo());					//整理番号を追加		2005/11/2

		//2005.11.03 iso 表示方式を追加
		searchInfo.setHyojiHoshikiShinsaJokyo(searchForm.getHyojiHoshikiShinsaJokyo());

		//2005.05.18 iso アクセス管理を事業区分→事業CDに変更
		//チェックボックスの事業コードリストをセット
		if(!searchForm.getValues().isEmpty()){
			searchInfo.setValues(searchForm.getValues());	
		}else{
			//割り振りの検索では、ここで審査担当区分をセットしているが、
			//審査状況管理では、ShinsaJokyoKakuninクラスで行っている。
			//動作が違うので注意。
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setValues(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
			}
		}

		//検索実行
		List result =
			getSystemServise(
				IServiceName.SHINSAJOKYO_KAKUNIN_SERVICE).searchCsvData(
				container.getUserInfo(),
				searchInfo);
		
		//2006.12.08 iso 件数一覧とファイル名を分けるよう変更
		String filename = "SHINSAJOKYO";	//一応デフォルトファイル名をSHINSAJOKYOにしておく
		if("1".equals(searchForm.getHyojiHoshikiShinsaJokyo())) {
			filename = "SHINSAJOKYO";
		} else if("2".equals(searchForm.getHyojiHoshikiShinsaJokyo())) {
			filename = "SHINSAKENSU";
		}
		
		
		DownloadFileUtil.downloadCSV(request, response, result, filename);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
