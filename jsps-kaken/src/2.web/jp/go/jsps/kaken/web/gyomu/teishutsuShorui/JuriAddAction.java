/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JuriAddAction.java
 *    Description : �󗝓o�^��ʂ�\������A�N�V�����N���X
 *
 *    Author      : DIS.jzx
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.jzx        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �󗝓o�^��ʂ�\������A�N�V�����N���X
 * ID RCSfile="$RCSfile: JuriAddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:56 $"
 */
public class JuriAddAction extends BaseAction {

    /**
     * Action�N���X�̎�v�ȋ@�\����������B
     * �߂�l�Ƃ��āA���̑J�ڐ��ActionForward�^�ŕԂ���B
     */
    public ActionForward doMainProcessing(
            ActionMapping mapping, 
            ActionForm form,
            HttpServletRequest request, 
            HttpServletResponse response, 
            UserContainer container)
            throws ApplicationException {

        // -----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        // ���������̎擾
        JuriGaiyoForm juriGaiyoForm = (JuriGaiyoForm) form;

        // -------�� VO�Ƀf�[�^���Z�b�g����B
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk(juriGaiyoForm.getSystemNo());
        UserInfo userInfo = container.getUserInfo();
        // ------�v���_�E���f�[�^�Z�b�g
        // �󗝏��
        juriGaiyoForm.setJuriKekkaList(LabelValueManager.getJuriFujuriList());

        // �������s
        RyoikiKeikakushoInfo selectInfo = new RyoikiKeikakushoInfo();
        try {
            selectInfo = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE)
                    .selectRyoikikeikakushoInfo(userInfo, pkInfo);

        }catch(NoDataFoundException e){
            errors.add("�Y���f�[�^�͂���܂���B",new ActionError("errors.5002"));
        }catch (DataAccessException de) {
            errors.add("�f�[�^��������DB�G���[���������܂����B",new ActionError("errors.4000"));
        }

        //----�Z�b�V�����ɐ\���f�[�^���i�󗝉������j���Z�b�g����B
        container.setRyoikikeikakushoInfo(selectInfo);

        // -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}