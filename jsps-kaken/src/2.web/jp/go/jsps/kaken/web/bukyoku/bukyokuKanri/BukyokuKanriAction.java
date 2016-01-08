/*
 * Created on 2005/04/14
 *
 */
package jp.go.jsps.kaken.web.bukyoku.bukyokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * “o˜^Ï‚İ•”‹Ç’S“–Òî•ñæ“¾ƒAƒNƒVƒ‡ƒ“
 * 
 * @author masuo_t
 *
 */
public class BukyokuKanriAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//•”‹Ç’S“–ÒIDæ“¾	
		BukyokutantoInfo info = new BukyokutantoInfo();
		info.setBukyokutantoId(container.getUserInfo().getBukyokutantoInfo().getBukyokutantoId());	
		
		//•”‹Ç’S“–Òî•ñ‚ğæ“¾‚·‚é
		info =
			getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectBukyokuData(
			container.getUserInfo(),
			info);
		
		//•”‹Ç’S“–Òî•ñ‚ğƒZƒbƒVƒ‡ƒ“‚É“o˜^B
		container.setBukyokutantoInfo(info);
		
		return forwardSuccess(mapping);
	}

}
