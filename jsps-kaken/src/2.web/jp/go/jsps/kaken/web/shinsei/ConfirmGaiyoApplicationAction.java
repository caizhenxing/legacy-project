/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ConfirmApplicationAction.java
 *    Description : �̈�v�揑�T�v�m�F�������s���B�m�F������ʕ\��
 *
 *    Author      : DIS.gongXB
 *    Date        : 2006/06/19
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/19    v1.0        DIS.gongXB     �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �̈�v�揑�T�v�m�F�������s���B�m�F������ʕ\��
 * ID RCSfile="$RCSfile: ConfirmGaiyoApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:01 $"
 */
public class ConfirmGaiyoApplicationAction extends BaseAction {

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

        //-----�ȈՐ\�������̓t�H�[���̎擾
        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm)form;
        
        RyoikiKeikakushoPk ryoikikeikakushoPk=new RyoikiKeikakushoPk();
        ryoikikeikakushoPk.setRyoikiSystemNo(ryoikiGaiyoForm.getRyoikiSystemNo());
        
        //�T�[�o�T�[�r�X�̌Ăяo���i�\�����m�F�����j
        ISystemServise servise = getSystemServise(
                        IServiceName.SHINSEI_MAINTENANCE_SERVICE);
        try{
            servise.confirmGaiyoComplete(container.getUserInfo(),ryoikikeikakushoPk);
        }catch(NoDataFoundException e){
            errors.add("�Y�������񂪑��݂��܂���ł����B",new ActionError("errors.5002"));
        }
        
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