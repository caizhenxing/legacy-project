/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;

import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;

import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���Q�֌W�ӌ����͏��l�I�u�W�F�N�g���X�V����B 
 * �t�H�[�����A�X�V�����N���A����B
 */
public class RiekiSohanInputSaveAction extends BaseAction {

    /* (�� Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        // ------�L�����Z����
        if (isCancelled(request)) {
            return forwardCancel(mapping);
        }

        // -----�擾�����g�[�N���������ł���Ƃ�
        if (!isTokenValid(request)) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                    "error.transaction.token"));
            saveErrors(request, errors);
            return forwardTokenError(mapping);
        }

        // ------�V�K�o�^�t�H�[�����̎擾
        ShinsaKekkaRigaiForm addForm = (ShinsaKekkaRigaiForm) form;

        // ------�Z�b�V�������X�V���̎擾
        ShinsaKekkaInputInfo addInfo = container.getShinsaKekkaInputInfo();

        // -------�� VO�Ƀf�[�^���Z�b�g����B
        addInfo.setComments(addForm.getComments());
        addInfo.setRigai(addForm.getRigai());
        // �@���Q�֌W�Ƀ`�F�b�N���āu�ۑ��v�{�^�����������ꍇ�A�R�����ʏ����N���A�Ƃ���
        if(IShinsaKekkaMaintenance.RIGAI_ON.equals(addForm.getRigai())){
        	//��茤��S�Ƃ��Ă̑Ó����@2007/5/11�ǉ�
        	addInfo.setWakates(null);
			//�w�p�I�d�v���E�Ó���
			addInfo.setJuyosei(null);        
			//�����v��
			addInfo.setKenkyuKeikaku(null);
			//�Ƒn���E�v�V��
			addInfo.setDokusosei(null);
			//�g�y���ʁE���Ր�
			addInfo.setHakyukoka(null);
			//���s�\�́E���̓K�ؐ�
			addInfo.setSuikonoryoku(null);
			//�K�ؐ�-�C�O�A�G��
			addInfo.setTekisetsuKaigai(null);
			//�����]���i�_���j
			addInfo.setKekkaTen("-");
			addInfo.setKekkaTenLabel("-");
			addInfo.setKekkaTenHogaLabel("-");
			//�Ó���
			addInfo.setDato(null);
			//���S��
			addInfo.setBuntankin(null);
			//�l��
			addInfo.setJinken(null);
			//���̑��̃R�����g
			addInfo.setOtherComment(null);
        }

        if (addForm.getRigai() != null && !addForm.getRigai().equals("")) {
            addInfo.setRigaiLabel(LabelValueManager.getlabelName(addForm.getRigaiList(), 
                                                                 addForm.getRigai()));
        }

        // DB�o�^
        ISystemServise service = getSystemServise(IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE);
        service.registRiekiSohan(container.getUserInfo(), addInfo);

        if (log.isDebugEnabled()) {
            log.debug("�R�����ʓ��͏��@�o�^��� '" + addInfo);
        }

        // -----�Z�b�V�����̐R�����ʓ��͏������Z�b�g
        container.setShinsaKekkaInputInfo(addInfo);

        // -----�t�H�[�����폜
        removeFormBean(mapping, request);

        // ������ʂɓ��͏���\�����邽�߂����ŃZ�b�g
        request.setAttribute(IConstants.RESULT_INFO, addInfo);

        // ------�g�[�N���̍폜
        resetToken(request);

        // -----��ʑJ�ځi��^�����j-----

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}