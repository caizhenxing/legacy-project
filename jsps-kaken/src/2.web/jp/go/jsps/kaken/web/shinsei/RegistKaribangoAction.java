/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : RegistKaribangoAction.java
 *    Description : ���̈�ԍ����s���o�^�A�N�V����
 *
 *    Author      : DIS.gongXB
 *    Date        : 2006/06/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/16    V1.0        DIS.gongXB     �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���̈�ԍ����s���o�^�A�N�V�����N���X
 */
public class RegistKaribangoAction extends BaseAction {

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
        
        

        JigyoKanriPk pkInfo=new JigyoKanriPk();
        ShinseiDataInfo shinseiDataInfo=((ShinseiForm)form).getShinseiDataInfo();

        //------�L�[���-----
        String jigyoId = shinseiDataInfo.getJigyoId();
        pkInfo.setJigyoId(jigyoId);    
        ISystemServise servise = getSystemServise(IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
        JigyoKanriInfo jigyoKanriInfo=servise.select(container.getUserInfo(),pkInfo);
        jigyoKanriInfo.setNendoSeireki("20"+jigyoId.substring(0,2));
              
        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        //�������ʂ��t�H�[���ɃZ�b�g����B
        request.setAttribute(IConstants.RESULT_INFO, jigyoKanriInfo);
//ADD START 2007/07/13 BIS ����
       
        RyoikiGaiyoForm ryoikiGaiyoForm =new RyoikiGaiyoForm();
        ryoikiGaiyoForm.getRyoikikeikakushoInfo().setZennendoOuboFlg(request.getParameter("ryoikikeikakushoInfo.zennendoOuboFlg"));
        ryoikiGaiyoForm.getRyoikikeikakushoInfo().setZennendoOuboNo(request.getParameter("ryoikikeikakushoInfo.zennendoOuboNo"));
       //�����̈�ŏI�N�x�O�N�x�̉���L�����X�g
        ryoikiGaiyoForm.setZenNendoOuboFlgList(LabelValueManager.getBuntankinList());
        ryoikiGaiyoForm.setJigyoId(jigyoId);
        request.setAttribute("ryoikiGaiyoForm",ryoikiGaiyoForm);
        
        //ADD END 2007/07/13 BIS ����
        return forwardSuccess(mapping);
    }
}