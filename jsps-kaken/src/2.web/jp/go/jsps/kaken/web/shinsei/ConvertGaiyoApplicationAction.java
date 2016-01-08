/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ConvertGaiyoApplicationAction.java
 *    Description : �̈�v�揑�i�T�v�jPDF�ϊ�����
 *
 *    Author      : DIS.zhangt
 *    Date        : 2006/06/29
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/29    V1.0        DIS.zhangt     �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �̈�v�揑�T�v�t�@�C���ϊ��A�N�V�����N���X�B
 * �w��V�X�e����t�ԍ��̐\�����ɑ΂��āAXML�ϊ��APDF�ϊ��v���𓊂���B
 * ID RCSfile="$RCSfile: ConvertGaiyoApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:01 $"
 */
public class ConvertGaiyoApplicationAction extends BaseAction {

    /* (�� Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //-----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        //-----��������̓t�H�[���̎擾
        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm) form;

        // �T�[�o�T�[�r�X�̌Ăяo���i�t�@�C���ϊ�
        RyoikiKeikakushoPk ryoikiKeikakushoPk = new RyoikiKeikakushoPk(
                ryoikiGaiyoForm.getRyoikikeikakushoInfo().getRyoikiSystemNo());

        try {
            getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .convertGaiyoApplication(container.getUserInfo(),
                            ryoikiKeikakushoPk);
        } catch (ValidationException e) {
            List errorList = e.getErrors();
            for (int i = 0; i < errorList.size(); i++) {
                ErrorInfo errInfo = (ErrorInfo) errorList.get(i);
                errors.add(errInfo.getProperty(), new ActionError(errInfo
                        .getErrorCode(), errInfo.getErrorArgs()));
            }
            // ���؃G���[�������̓g�[�N�����ăZ�b�g���i���́j��ʂ֑J�ڂ�����
            if (!errors.isEmpty()) {
                //�g�[�N�����Z�b�V�����ɕۑ�����B
                saveToken(request);
                saveErrors(request, errors);
                return forwardFailure(mapping);
            }
        } catch (ApplicationException e) {
            //�G���[�������̓g�[�N�����ăZ�b�g���i���́j��ʂ֑J�ڂ�����
            //�g�[�N�����Z�b�V�����ɕۑ�����B
            saveToken(request);
            saveErrors(request, errors);
            throw e;
        }

        //�ȈՐ\���t�H�[������
        RyoikiGaiyoForm gaiyoForm = new RyoikiGaiyoForm();
        gaiyoForm.setRyoikiSystemNo(ryoikiKeikakushoPk.getRyoikiSystemNo()); //�V�X�e����t�ԍ��Z�b�g
        gaiyoForm.setJigyoId(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getJigyoId());//����ID
        gaiyoForm.setKariryoikiNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getKariryoikiNo());//���̈�ԍ�

        //���N�G�X�g�ɊȈՐ\���t�H�[�����Z�b�g����
        request.setAttribute("ryoikiGaiyoForm", gaiyoForm);
        
        //------�t�H�[�����̍폜
        removeFormBean(mapping, request);

        return forwardSuccess(mapping);
    }
}