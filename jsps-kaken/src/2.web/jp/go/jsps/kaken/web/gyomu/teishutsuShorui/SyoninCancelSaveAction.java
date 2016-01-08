/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SyoninCancelSaveAction.java
 *    Description : ���F����(��o����)�����s�A�N�V����
 *    Author      : mcj
 *    Date        : 2006/06/13
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.mcj        �V�K�쐬   
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���F����(��o����)�����s�A�N�V����
 * ID RCSfile="$RCSfile: SyoninCancelSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class SyoninCancelSaveAction extends BaseAction {

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

        //-----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        // ����������ݒ�
        JuriGaiyoForm juriGaiyoForm = (JuriGaiyoForm) form; // �t�H�[���擾
        RyoikiKeikakushoInfo ryoikiInfo = new RyoikiKeikakushoInfo();
        ryoikiInfo.setJokyoIds(container.getTeishutsuShoruiSearchInfo().getSearchJokyoId());
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk(juriGaiyoForm.getSystemNo());

        try {
            ISystemServise servise = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE);
            servise.cancelTeisyutusyoSyonin(container.getUserInfo(), pkInfo,ryoikiInfo);
        }
        catch (NoDataFoundException ne) {
            ActionError error = new ActionError("errors.4002");
            errors.add("���F�����ŗ̈�v�揑�i�T�v�j���Ǘ��e�[�u���ɊY������f�[�^��������܂���B", error);
        }
        catch (DataAccessException de) {
            ActionError error = new ActionError("errors.4000");
            errors.add("���F�����ŗ̈�v�揑�T�v�e�[�u���̌������ɗ�O���������܂����B", error);
        }
        
        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}