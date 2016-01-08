/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchListAction.java
 *    Description : 受理登録対象申請情報一覧表示アクションクラス
 *
 *    Author      : 
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0                       新規作成
 *    2006/06/02    V1.1        DIS.jiangZX    修正（検索条件を変更）
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.LabelValueBean;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 受理登録対象申請情報一覧表示アクションクラス。
 * 受理登録対象申請情報一覧画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:47 $"
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
        JuriSearchForm searchForm = (JuriSearchForm)form;
        //------キャンセル時        
        if (isCancelled(request)) {
            searchForm.setStartPosition(0); 
            return forwardCancel(mapping);
        }

		//検索条件の取得
// update start dyh 2006/05/30
//		BaseSearchForm searchForm = (BaseSearchForm)form;
        

		//-------▼ VOにデータをセットする。
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
        searchInfo.setJigyoCd(searchForm.getJigyoCd());
        searchInfo.setShozokuCd(searchForm.getShozokuCd());
// 2006/06/02 jzx　add start
        //検索条件の所属研究機関名(名称)
        searchInfo.setShozokNm(searchForm.getShozokuNm());
// 2006/06/02 jzx　add end       

		//指定されていない場合は、（業務担当者ならば）自分が担当する事業コードのみ
		if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
			GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
			searchInfo.setTantoJigyoCd(gyomutantoInfo.getTantoJigyoCd());
		}

		//申請状況をセット
		//申請状況条件（固定：業務担当者が参照可能なステータスのもの）
//		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//		statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	// 再申請中（申請状況に関わらず）	
//		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);						//「学振処理中」:04
//		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);							//「学振受理」:06
//		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);						//「学振不受理」:07
//		searchInfo.setStatusSearchInfo(statusInfo);

// 2006/06/02 by jzx　update start 原因：検索条件変更
//		//2005/04/20 追加 ここから---------------------------------------------
//		//理由 基盤の場合は表示しないようにするため
//        // 検索条件の事業区分
//		ArrayList array = new ArrayList();
//      2006/06/02 by jzx　update start
//      array.add("1");//学術創成（非公募）
//      array.add("2");//学術創成（公募）
//      array.add("3");//特別推進研究）
//		searchInfo.setJigyoKubun(array);
        // 検索条件の担当事業コードを設定
        List array = new ArrayList();
        List jigyoCdNms = searchForm.getJigyoNmList();
        for(int i = 0;i < jigyoCdNms.size(); i++){
            LabelValueBean bean = (LabelValueBean)jigyoCdNms.get(i);
            array.add(bean.getValue());
        }
		searchInfo.setTantoJigyoCd(array);
// 2006/06/02 by jzx　update end
        //追加 ここまで--------------------------------------------------------	
        
		//受理前
		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
		String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
                                                StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};     //再申請フラグ（初期値、再申請済み）
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);    		//「学振処理中」:04
		searchInfo.setStatusSearchInfo(statusInfo);

		//ソート順をセット
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);	//--事業ID順
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);	//--申請番号順

		//ページ制御
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());
		searchInfo.setStartPosition(searchForm.getStartPosition());

		//検索実行
		Page result = null;
		try{
			result =
				getSystemServise(
					IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchApplication(
					container.getUserInfo(),
					searchInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			result = Page.EMPTY_PAGE;
            errors.add("",new ActionError("errors.5002"));
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