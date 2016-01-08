/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : IkkatsuJuriAction.java
 *    Description : �ꊇ�󗝊m�F�A�N�V����
 *
 *    Author      : yoshikawa_h
 *    Date        : 2005/04/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/21    V1.0                       �V�K�쐬
 *    2006/06/06    V1.1        DIS.LiWanJun   �C���i��ʂŃ��W�I��ǉ����邩��j
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �ꊇ�󗝊m�F�A�N�V����
 * @author yoshikawa_h
 */
public class IkkatsuJuriAction extends BaseAction {

    public ActionForward doMainProcessing(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        UserContainer container)
        throws ApplicationException {

        //-----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();
        
        ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
        //------�L�����Z������-----------------
        String forwardStr = (String)request.getParameter("goBack");
        if ("goBack".equals(forwardStr)) {
            removeFormBean(mapping,request);
            return mapping.findForward(forwardStr);
        }
//2006/06/06 �c �����R�@�ǉ���������
        //------�V�X�e���ԍ��̎擾
        JuriListForm listForm = (JuriListForm)form;
        String[] sysNos =listForm.getSystemNos();

        //��ʂŃ��W�I��I�����Ȃ��̏ꍇ
        String radioButton = listForm.getSelectRadioBn();
        if(radioButton == null || radioButton.equals("false")){
            //�G���[���b�Z�[�W
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.requiredSelect","�󗝂��鉞����"));
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        
        //�y�[�W����
        searchInfo.setPageSize(0);
        searchInfo.setMaxSize(0);
        searchInfo.setStartPosition(0);
        
        //�\�[�g�����Z�b�g
        searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);   //--����ID��
        searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO); //--�\���ԍ���
        
        Page result = null;
        try{
            result = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                .getShinseiJuriAll(container.getUserInfo(),searchInfo,sysNos);
        }catch(NoDataFoundException e){
            //0���̃y�[�W�I�u�W�F�N�g�𐶐�
            result = Page.EMPTY_PAGE;
            errors.add("",new ActionError("errors.5002"));
        }
        request.setAttribute(IConstants.RESULT_INFO,result);
//2006/06/06 �c �����R �ǉ������܂�

//2007/03/23 �����F�@��������
        updateFormBean(mapping, request.getSession(), listForm);
//2007/03/23 �����F�@�����܂�
        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        //�g�[�N�����Z�b�V�����ɕۑ�����B
        saveToken(request);
        return forwardSuccess(mapping);
    }
}