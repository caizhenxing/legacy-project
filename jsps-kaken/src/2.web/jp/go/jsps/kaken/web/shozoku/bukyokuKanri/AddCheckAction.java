/*
 * Created on 2005/04/05
 *
 */
package jp.go.jsps.kaken.web.shozoku.bukyokuKanri;

import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �����S���ғo�^/�C���m�F�p�A�N�V�����N���X�B
 * �V�K�o�^�A�y�яC���̊m�F��ʕ\�����ɌĂяo�����B
 *
 */
public class AddCheckAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
			
		BukyokuForm bukyokuForm = (BukyokuForm)form;
		
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();
		
		//�t�H�[������擾�������ǃR�[�h�̔z��
		ArrayList array = (ArrayList)bukyokuForm.getBukyokuList();
		//�i�[���ꂽ�f�[�^��ێ�����HashSet
		HashSet set = new HashSet();
		
		if(array != null){			
			int count = 0;
			for(int i = 0; i < array.size(); i++){
				if(!array.isEmpty() && array.get(i) != null && !array.get(i).equals("")){
					set.add(array.get(i));
					count++;
				}
			}
			//�z����ɏd�������l������ꍇ�ɏd���G���[��Ԃ�
			if(count != set.size()){
				errors.add("errors.2010", new ActionError("errors.2010","���ǃR�[�h"));
				//�G���[��ۑ��B
				saveErrors(request, errors);
				//---���͓��e�ɕs��������̂ōē���
				return forwardInput(mapping);
			}
		}
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		BukyokutantoInfo addInfo = new BukyokutantoInfo();
		try {
			PropertyUtils.copyProperties(addInfo, bukyokuForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		//-------��
		
		//---�T�[�o���̓`�F�b�N
		if(set.size() != 0){
			try{
				//���ǃR�[�h�����͂���Ă���ꍇ�ɃR�[�h�̊m�F
				getSystemServise(
					IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).CheckBukyokuCd(
					container.getUserInfo(),
					set);
			
			}catch (ValidationException e) {
				//�T�[�o�[�G���[��ۑ��B
				saveServerErrors(request, errors, e);
				//---���͓��e�ɕs��������̂ōē���
				return forwardInput(mapping);
			}
		}
	
		//-----�Z�b�V�����ɕ��ǒS���ҏ���o�^����B
		container.setBukyokutantoInfo(addInfo);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}

}
