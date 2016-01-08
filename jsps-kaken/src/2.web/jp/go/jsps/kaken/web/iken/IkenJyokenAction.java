/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム(JSPS)
 *    Source name : IkenJyokenAction.java
 *    Description : 意見情報検索条件入力アクションクラス。
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     新規
 *
 *====================================================================== 
 */package jp.go.jsps.kaken.web.iken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
//import jp.go.jsps.kaken.model.vo.UserInfo;
//import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 意見情報検索前アクションクラス。
 * Beanを初期化して、意見情報検索画面を表示する。
 * 
 * ID RCSfile="$RCSfile: IkenJyokenAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:18 $"
 */
public class IkenJyokenAction extends BaseAction {

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

		//if ( !((IkenSearchForm)form).getAction().equals("edit") ){
			//検索条件があればクリアする。(Beanをクリアした)
			removeFormBean(mapping,request);
		//}
		
		//検索条件をフォームをセットする。
		IkenSearchForm searchForm = new IkenSearchForm();

		/*
		//元の条件をセット
		IkenSearchForm oldForm = (IkenSearchForm)form;
		searchForm.setShinseisya(oldForm.getShinseisya());	//申請者
		searchForm.setSyozoku(oldForm.getSyozoku());		//所属機関担当者
		searchForm.setBukyoku(oldForm.getBukyoku());		//部局担当者
		searchForm.setShinsyain(oldForm.getShinsyain());	//審査員
		searchForm.setSakuseiDateFromYear(oldForm.getSakuseiDateFromYear());
		searchForm.setSakuseiDateFromMonth(oldForm.getSakuseiDateFromMonth());
		searchForm.setSakuseiDateFromDay(oldForm.getSakuseiDateFromDay());
		searchForm.setSakuseiDateToYear(oldForm.getSakuseiDateToYear());
		searchForm.setSakuseiDateToMonth(oldForm.getSakuseiDateToMonth());
		searchForm.setSakuseiDateToDay(oldForm.getSakuseiDateToDay());
		searchForm.setDispmode(oldForm.getDispmode());
		searchForm.setAction("");

		if (log.isDebugEnabled()){
			log.debug("setShinseisya:" + searchForm.getShinseisya());
			log.debug("setSakuseiDateFromYear:" + searchForm.getSakuseiDateFromYear());
		}
		*/
		
		updateFormBean(mapping,request,searchForm);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
