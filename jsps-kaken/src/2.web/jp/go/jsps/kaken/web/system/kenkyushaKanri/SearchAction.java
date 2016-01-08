/*
 * Created on 2005/04/15
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 研究者情報検索前アクションクラス。
 * 研究者情報検索画面を表示する。
 * 
 */
public class SearchAction extends BaseAction {

	
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {


		//検索条件があればクリアする。
		removeFormBean(mapping,request);
		
		return forwardSuccess(mapping);
	}

}
