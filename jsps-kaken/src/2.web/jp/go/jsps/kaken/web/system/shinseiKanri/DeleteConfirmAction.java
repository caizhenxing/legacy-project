/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : �����F
 *    Date        : 2007/02/25
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.shinseiKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shinsei.SimpleShinseiForm;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ������폜�m�F�A�N�V�����N���X�B
 * �폜�m�F��ʂ𐶐�����B
 * ID RCSfile="$RCSfile: DeleteConfirmAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:48 $"
 */
public class DeleteConfirmAction extends BaseAction {

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
            
        //-----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        //-----�ȈՉ�����̎擾
        SimpleShinseiForm simpleShinseiForm = (SimpleShinseiForm)form;
        
        //�T�[�o�T�[�r�X�̌Ăяo���i�ȈՉ�����擾�j
        ShinseiDataPk shinseiPk = new ShinseiDataPk(simpleShinseiForm.getSystemNo());
        ISystemServise servise = getSystemServise(
                        IServiceName.SHINSEI_MAINTENANCE_SERVICE);
        SimpleShinseiDataInfo simpleInfo = servise.selectSimpleShinseiDataInfo(container.getUserInfo(),shinseiPk);
        
        //-----�ȈՉ���������N�G�X�g�����ɃZ�b�g
        request.setAttribute(IConstants.RESULT_INFO, simpleInfo);
        
        //------�t�H�[�����̍폜
        removeFormBean(mapping,request);

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        
        return forwardSuccess(mapping);
    }   
}