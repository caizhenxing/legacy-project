/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : ���u�j
 *    Date        : 2006/10/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���Q�֌W���͊�����\������B 
 * 
 * ID RCSfile="$RCSfile: RiekiSohanCheckAction.java,v $"
 * Revision="$Revision: 1.1 $" 
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class RiekiSohanCheckAction extends BaseAction {
    
    /* (�� Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        // ���������̎擾
        ShinsaKekkaSearchForm shinsaKekkaSearchForm = (ShinsaKekkaSearchForm) form;

        // -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        // ------�t�H�[���ɃZ�b�g����B
        updateFormBean(mapping, request, shinsaKekkaSearchForm);

        //�g�[�N�����Z�b�V�����ɕۑ�����B
        saveToken(request);

        return forwardSuccess(mapping);
    }
}