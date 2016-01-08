/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e��(JSPS)
 *    Source name : IkenSaveAction.java
 *    Description : �ӌ����ޔ�����A�N�V�����N���X�B
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     �V�K
 *
 *====================================================================== 
 */package jp.go.jsps.kaken.web.iken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.IkenInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * @author user1
 *
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */
public class IkenSaveAction extends BaseAction {

	/* (Javadoc �Ȃ�)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response,
				UserContainer container)
			throws ApplicationException {
		
		//�t�H�[���ݒ�
		IkenForm frm = (IkenForm)form;
		
		//���ӌ����v�]�����擾����B
		String strIken = frm.getIkenInfo();
		if (log.isDebugEnabled()){
			log.debug("�Ώێ�ID�F" + frm.getTaishoID());
			//log.debug("���ӌ��F" + strIken.length() + ":" + strIken);
		}

		//�ӌ����N���X����
		IkenInfo ikenInfo = new IkenInfo();
		
		//�Ώێ҂h�c�ݒ�
		ikenInfo.setTaisho_id( Integer.parseInt( frm.getTaishoID() ));
		
		//�ӌ����e�ݒ�
		ikenInfo.setIken(strIken);
		
		//DB�֏�������
		//�T�[�o�T�[�r�X�̌Ăяo���i�V�K�o�^�j
		ISystemServise servise = getSystemServise(IServiceName.IKEN_MAINTENANCE_SERVICE);
		servise.insert(	ikenInfo );
		
		// ���̉�ʂ֑J��
		return forwardSuccess(mapping);
	}

}
