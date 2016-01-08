/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : 張楠
 *    Date        : 2007/6/29
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.ryoikiKeikaku;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.impl.LabelValueMaintenance;
import jp.go.jsps.kaken.web.common.LabelValueBean;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 領域計画書情報検索前アクションクラス。
 * 領域計画書情報検索画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchAction.java,v $"
 * Revision="$Revision: 1.8 $"
 * Date="$Date: 2007/07/25 09:44:59 $"
 */
public class SearchAction extends BaseAction {

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

		//検索条件があればクリアする。
		removeFormBean(mapping,request);
		
		//検索条件をフォームをセットする。
		RyoikiGaiyoSearchForm searchForm = new RyoikiGaiyoSearchForm();
		
		
		LabelValueMaintenance labelValueMaintenance = new LabelValueMaintenance();
		String jigyoName = labelValueMaintenance.searchJigyoName(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI);
		//------プルダウンデータセット
		searchForm.setJigyoName(jigyoName);

		List listT = new ArrayList();
		listT.add(new LabelValueBean(" ","0"));
		List listTemp = labelValueMaintenance.getLabelList("OUBOJOKYO");
		if(listTemp.size()>0){
			for(int i=0;i<listTemp.size();i++){
				listT.add(listTemp.get(i));
			}
		}
		//		応募状況リスト
		searchForm.setJokyoList(listT);
						
		updateFormBean(mapping,request,searchForm);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
