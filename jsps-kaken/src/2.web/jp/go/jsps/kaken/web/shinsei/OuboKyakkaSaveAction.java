/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : OuboKyakkaSaveAction.java
 *    Description : ������p������
 *
 *    Author      : �c�c
 *    Date        : 2006/06/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/16    v1.0        �c�c                        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ������p���A�N�V�����N���X�B
 * �p���Ώې\�������X�V����B 
 * 
 */
public class OuboKyakkaSaveAction extends BaseAction {
    
    /* (�� Javadoc)
     * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
     */
    public ActionForward doMainProcessing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        // -----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        //------�p���Ώې\�����V�X�e���ԍ��̎擾
        RyoikiGaiyoForm shoninForm = (RyoikiGaiyoForm)form;
        
        //------�p���Ώې\���V�X�e���ԍ��̎擾
        ShinseiDataPk pkInfo = new ShinseiDataPk();
        //------�L�[���
        String systemNo = shoninForm.getSystemNo();
        pkInfo.setSystemNo(systemNo);

        //------�L�[�������ɐ\�������X�V
        try{
            getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .rejectApplicationForTokuteiSinnki(container.getUserInfo(), pkInfo);
        }catch(NoDataFoundException e){
            errors.add(e.getMessage(),new ActionError(e.getErrorCode(), e.getErrorArgs()));
        }

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        return forwardSuccess(mapping);
    }

}