/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.hyoka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.HyokaSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 評価情報検索アクションクラス。
 * 評価情報一覧画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:19 $"
 */
public class SearchListAction extends BaseAction {

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

		//ユーザ情報の取得
		UserInfo userInfo = container.getUserInfo();

		//検索条件の取得
		HyokaSearchForm searchForm = (HyokaSearchForm)form;
		
		//審査員6人分で1件と数えるので特殊制御が必要。
		//コメント一覧で、表示件数・最大数が初期値(BaseSearchFormの値)の時は、6倍する。
		BaseSearchForm baseSearchForm = new BaseSearchForm();
		if(searchForm.getJigyoKubun().equals("4") && searchForm.getHyojiHoshikiKiban().equals("2")	//コメントリストの時
				&& searchForm.getPageSize() == baseSearchForm.getPageSize()) {						//初期値の時
			searchForm.setPageSize(baseSearchForm.getPageSize() * 6);
			searchForm.setMaxSize(baseSearchForm.getMaxSize() * 6);
		}
		
		//-------▼ VOにデータをセットする。
		HyokaSearchInfo searchInfo = new HyokaSearchInfo();
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//2005.05.18 iso アクセス管理を事業区分→事業CDに変更
		//事業を選択している場合は、選択した事業CDを
		//選択していない場合は、担当事業CDをセットする。
		//変更前に使っていたHyokaSearchInfoのjigyoCdは無効化した。
		//※学創は事業CDをセットしない。
		if(searchForm.getJigyoCd() != null && !searchForm.getJigyoCd().equals("")){
			searchInfo.setValues(searchForm.getJigyoCd());	
		}else{
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setValues(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
			}
		}

		//検索実行
		Page result =
			getSystemServise(
				IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchHyokaList(
				container.getUserInfo(),
				searchInfo);

		List list = result.getList();
		for(int i = 0; i < result.getSize(); i++) {
			HashMap hashMap = (HashMap)list.get(i);
			//第1回は表示しないので空にする
			String kaisu = hashMap.get("KAISU").toString();
			if(kaisu != null && kaisu.equals("1")) {
				hashMap.put("KAISU", null);
			}
		}

		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
