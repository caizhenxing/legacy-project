/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : DeleteGaiyoConfirmSaveAction.java
 *    Description : �̈�v�揑�폜�m�F�����s����A�N�V�����N���X
 *
 *    Author      : Dis.mcj
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/27    V1.0        DIS.mcj        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �̈�v�揑�폜�m�F�����s����A�N�V�����N���X
 */
public class DeleteGaiyoConfirmSaveAction extends BaseAction {
    public ActionForward doMainProcessing(
            ActionMapping mapping, 
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {

        // -----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm) form;
        String ryoikiSystemNo = ryoikiGaiyoForm.getRyoikiSystemNo();
        
        try {
             getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE).
                    deleteFlagRyoikiGaiyo(container.getUserInfo(),ryoikiSystemNo);
        }
        catch (NoDataFoundException e) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.5045","�̈�v�揑�폜�m�F"));
        }   
       
        // -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}