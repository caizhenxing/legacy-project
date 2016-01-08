/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/03/01
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.datahokan;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �f�[�^�ۊǉ�ʃA�N�V�����N���X�B
 * �f�[�^�ۊǑΏێ��Ƃ��w�肷���ʂ�\������B
 * ID RCSfile="$RCSfile: DataHokanInvokeAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:23 $"
 */
public class DataHokanInvokeAction extends BaseAction {

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
			
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();
		
		//�t�H�[���̎擾
		DataHokanForm dataHokanForm = (DataHokanForm)form;
		
		//����ID
		JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(dataHokanForm.getJigyoId());
		
		//�ۊǗL�������̍\�z
		DateUtil dateUtil = new DateUtil();
		dateUtil.setCal(dataHokanForm.getYukoKigenYear(),
						dataHokanForm.getYukoKigenMonth(),
						dataHokanForm.getYukoKigenDate());
		Date dateYukoKigen = dateUtil.getCal().getTime();
		
		//�T�[�o�T�[�r�X�̌Ăяo���i�f�[�^�ۊǁj
		ISystemServise servise = getSystemServise(
						IServiceName.DATA_HOKAN_MAINTENANCE_SERVICE);
		int dataCount = servise.dataHokanInvoke(
		                             container.getUserInfo(),
		                             jigyoKanriPk,
									 dateYukoKigen);		
		
		//�f�[�^�ۊǌ����i�\���f�[�^���j���Z�b�g����
		dataHokanForm.setShoriKensu(dataCount);
	
		//-----�t�H�[�������N�G�X�g�ɃZ�b�g
		updateFormBean(mapping, request, dataHokanForm);	
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
}
