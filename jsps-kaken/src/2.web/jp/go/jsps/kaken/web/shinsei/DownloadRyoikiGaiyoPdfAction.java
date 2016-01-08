/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : DownloadGaiyoConfirmPdfAction.java
 *    Description : �̈�v�揑�T�v�m�F�_�E�����[�h�A�N�V����
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/06/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/26    V1.0        DIS.dyh        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * �̈�v�揑�T�v�m�F�_�E�����[�h�A�N�V����
 * ID RCSfile="$RCSfile: DownloadRyoikiGaiyoPdfAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:00 $"
 */
public class DownloadRyoikiGaiyoPdfAction extends BaseAction {

    /**
     * Action�N���X�̎�v�ȋ@�\����������B
     * �߂�l�Ƃ��āA���̑J�ڐ��ActionForward�^�ŕԂ���B
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //-----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm) form;

        //�T�[�o�T�[�r�X�̌Ăяo���i�\��PDF�t�@�C���擾�j
        FileResource fileRes = null;
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk(ryoikiGaiyoForm.getRyoikiSystemNo());
        try {
            fileRes = getSystemServise(
                    IServiceName.TEISHUTU_MAINTENANCE_SERVICE).getRyoikiGaiyoPdfFile(
                    container.getUserInfo(), pkInfo);
        }
        catch (ValidationException e) {
            //�T�[�o�[�G���[��ۑ��B
            saveServerErrors(request, errors, e);
        }

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        //-----�t�@�C���̃_�E�����[�h
        DownloadFileUtil.downloadFile(response, fileRes);
        return forwardSuccess(mapping);
    }
}