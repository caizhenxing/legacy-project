/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : GaiyoUploadFileAction
 *    Description : �Y�t�t�@�C���A
 *
 *    Author      : �c
 *    Date        : 2006/06/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/23    v1.0        �c�c�@�@�@�@�@�@�@�@�@�@ �V�K�쐬�@
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �Y�t�t�@�C���A�b�v���[�h�A�N�V�����N���X�B
 * �\���Y�t�t�@�C�����Z�b�V�����ɕێ�����B
 */
public class GaiyoUploadFileAction extends BaseAction {

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
        
        //-----�\���t�H�[���̎擾
        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm)form;
        
        //-----UploadFileAction�̊����t���O���Z�b�g����
        ryoikiGaiyoForm.setUploadActionEnd(true);
        
        //-----�\���t�H�[�����Z�b�V�����ɕێ��B
        updateFormBean(mapping, request, ryoikiGaiyoForm);
        
        return forwardSuccess(mapping);
        
    } 
}