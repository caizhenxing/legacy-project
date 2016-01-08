/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : RegistKaribangoAction.java
 *    Description : ���̈�ԍ����s���o�^���s���A�N�V����
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

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���̈�ԍ����s���o�^���s���A�N�V����
 * ���̈�ԍ����s���o�^������ʂ�\������
 */
public class RegistKaribangoSaveAction extends BaseAction {

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
        //UPDATE START 2007/07/23 BIS ����
//        ShinseiDataInfo shinseiDataInfo=((ShinseiForm)form).getShinseiDataInfo();
//
//        // �L�[���
//        String jigyoId = shinseiDataInfo.getJigyoId();
        RyoikiGaiyoForm ryoikiGaiyoForm =(RyoikiGaiyoForm)form;
       //------�L�[���-----
        String jigyoId = ryoikiGaiyoForm.getJigyoId();
        pkInfo.setZennendoOuboFlg(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getZennendoOuboFlg());;
        pkInfo.setZennendoOuboNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getZennendoOuboNo());
        pkInfo.setZennendoOuboRyoikiRyaku(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getZennendoOuboRyoikiRyaku());
        pkInfo.setZennendoOuboSettei(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getZennendoOuboSettei());
//      UPDATE END 2007/07/23 BIS ����
        pkInfo.setJigyoId(jigyoId); 
      
        UserInfo userInfo=container.getUserInfo();
        
        try{
            getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE)
               .registKariBangoHakkoInfo(userInfo,pkInfo);
        }catch (NoDataFoundException ex) {
            errors.add("�Y���f�[�^�͂���܂���", new ActionError("errors.5002"));
        }
        
        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}