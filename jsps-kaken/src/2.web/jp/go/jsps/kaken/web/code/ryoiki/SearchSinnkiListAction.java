/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchSinnkiListAction
 *    Description : �̈��񌟍��i�V�K�̈�j�A�N�V�����N���X
 *
 *    Author      : �c
 *    Date        : 2006/07/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *�@      2006/07/24    v1.0        �c                           �V�K�쐬  
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.code.ryoiki;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ICodeMaintenance;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �̈��񌟍��i�V�K�̈�j�A�N�V�����N���X�B
 * �̈�ꗗ��ʁi�V�K�̈�j��\������B
 * 
 */
public class SearchSinnkiListAction extends BaseAction {

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

        Map result = null;
        //�������s
        try{
            result =
                getSystemServise(
                    IServiceName.CODE_MAINTENANCE_SERVICE).getRyoikiSinnkiInfoList(
                                    container.getUserInfo());
        }catch(NoDataFoundException e){
            //0���̃y�[�W�I�u�W�F�N�g�𐶐�
            result = new HashMap();
        }
        
        //Map��������擾
        List searchList = (List)result.get(ICodeMaintenance.KEY_SEARCH_LIST);

        //�������ʂ����N�G�X�g�����ɃZ�b�g
        request.setAttribute(IConstants.SEARCH_INFO,searchList);    //�������

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}