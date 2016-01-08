/*
 * Created on 2005/04/15
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �����ҏ��o�^�O�A�N�V�����N���X�B
 * �t�H�[���Ɍ����ҏ��o�^��ʂɕK�v�ȃf�[�^���Z�b�g����B
 * �����ҏ��V�K�o�^��ʂ�\������B
 * 
 * @author masuo_t
 */
public class AddAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//------�V�K�o�^�t�H�[�����̍쐬
		KenkyushaForm searchForm = new KenkyushaForm();

		//------�X�V���[�h
		searchForm.setAction(BaseForm.ADD_ACTION);

		//------�v���_�E���f�[�^�Z�b�g
		searchForm.setShokushuCdList(LabelValueManager.getShokushuCdList());
		searchForm.setGakuiList(LabelValueManager.getGakuiList());
		searchForm.setSeibetsuList(LabelValueManager.getSeibetsuList());
		
		//------�V�K�o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping, request, searchForm);

		return forwardSuccess(mapping);
	}

}
