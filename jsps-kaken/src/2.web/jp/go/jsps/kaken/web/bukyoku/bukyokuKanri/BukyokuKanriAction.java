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
 * �o�^�ςݕ��ǒS���ҏ��擾�A�N�V����
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

		//���ǒS����ID�擾	
		BukyokutantoInfo info = new BukyokutantoInfo();
		info.setBukyokutantoId(container.getUserInfo().getBukyokutantoInfo().getBukyokutantoId());	
		
		//���ǒS���ҏ����擾����
		info =
			getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectBukyokuData(
			container.getUserInfo(),
			info);
		
		//���ǒS���ҏ����Z�b�V�����ɓo�^�B
		container.setBukyokutantoInfo(info);
		
		return forwardSuccess(mapping);
	}

}
