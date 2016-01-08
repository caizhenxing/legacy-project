/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchUnregistAction.java
 *    Description : ���o�^�\���ҏ�񌟍��O�A�N�V�����N���X
 *
 *    Author      : 
 *    Date        : 2005/03/25
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/03/25    V1.0                       �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���o�^�\���ҏ�񌟍��A�N�V�����N���X�B
 * ���o�^�\���ҏ��ꗗ��ʂ�\������B
 *
 * @author yoshikawa_h
 */
public class SearchUnregistListAction extends BaseAction {

    /* 
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

        //------�L�����Z����        
        if (isCancelled(request)) {
            return forwardCancel(mapping);
        }

        //���������̎擾
        ShinseishaSearchForm searchForm = (ShinseishaSearchForm)form;

        //-------�� VO�Ƀf�[�^���Z�b�g����B
        ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
        try {
            PropertyUtils.copyProperties(searchInfo, searchForm);
        } catch (Exception e) {
            log.error(e);
            throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
        }

        searchInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());

// 2005/03/31 �폜 ��������--------------------------------------------
// ���R �s�v�ȏ����̂���    
//        searchInfo.setShozokuName(container.getUserInfo().getShozokuInfo().getShozokuName());
// �폜 �����܂�-------------------------------------------------------

        //�������s
        Page result =
            getSystemServise(
                IServiceName.KENKYUSHA_MAINTENANCE_SERVICE).searchUnregist(
                container.getUserInfo(),
                searchInfo);

        //�o�^���ʂ����N�G�X�g�����ɃZ�b�g
        request.setAttribute(IConstants.RESULT_INFO,result);

        //�g�[�N�����Z�b�V�����ɕۑ�����B
        saveToken(request);

        //2005/04/20 �ǉ� ��������------------------------------------
        //���������ێ��̂���
        container.setShinseishaSearchInfo(searchInfo);
        //�ǉ� �����܂�-----------------------------------------------

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        return forwardSuccess(mapping);
    }
}