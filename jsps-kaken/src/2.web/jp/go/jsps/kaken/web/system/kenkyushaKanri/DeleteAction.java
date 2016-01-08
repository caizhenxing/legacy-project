/*
 *  Created on 2005/04/18
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.model.vo.KenkyushaPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �����ҏ��폜�O�A�N�V�����N���X�B
 * �폜�Ώی����ҏ����擾�B�Z�b�V�����ɓo�^����B 
 * �폜�m�F��ʂ�\������B
 */
public class DeleteAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//## �폜���v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.kenkyushaInfo.�v���p�e�B��

		//------�C���o�^�t�H�[�����̎擾
		KenkyushaForm deleteForm = (KenkyushaForm) form;
		
		//------�폜�Ώی����ҏ��̎擾
		KenkyushaPk pkInfo = new KenkyushaPk();
		//------�L�[���
		String kenkyuNo = deleteForm.getKenkyuNo();
		String shozokuCd = deleteForm.getShozokuCd();
		pkInfo.setKenkyuNo(kenkyuNo);
		pkInfo.setShozokuCd(shozokuCd);

		//------�L�[�������ɍ폜�f�[�^�擾	
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		KenkyushaInfo deleteInfo = service.selectKenkyushaData(container.getUserInfo(),pkInfo, false);
		
		//------�폜�Ώۏ����Z�b�V�����ɓo�^�B
		container.setKenkyushaInfo(deleteInfo);

		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
