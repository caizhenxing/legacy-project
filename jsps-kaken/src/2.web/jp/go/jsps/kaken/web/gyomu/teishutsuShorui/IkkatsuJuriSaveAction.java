/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : IkkatsuJuriSaveAction.java
 *    Description : �ꊇ��(��o���ވꗗ)�����s�A�N�V�����N���X�B
 *
 *    Author      : Dis.mcj
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.mcj        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �ꊇ��(��o���ވꗗ)�����s�A�N�V�����N���X�B
 * ID RCSfile="$RCSfile: IkkatsuJuriSaveAction.java,v $"
 * Revision="$Revision: 1.1 $" Date="$Date: 2007/06/28 02:06:56 $"
 */
public class IkkatsuJuriSaveAction extends BaseAction {

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
        TeishutsuShoruiSearchInfo searchInfo = container.getTeishutsuShoruiSearchInfo();

        try {
            getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE).
            executeIkkatuJuri(container.getUserInfo(), searchInfo);
        }
        catch (DataAccessException ex) {
            errors.add("�ꊇ�󗝂ŗ̈�v�揑�T�v�e�[�u���̌������ɗ�O���������܂����B", new ActionError("errors.5002"));
        }
        catch (ApplicationException ex) {
            errors.add("�ꊇ�󗝂ŃG���[���������܂����B", new ActionError("errors.4002"));
        }
        // -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}