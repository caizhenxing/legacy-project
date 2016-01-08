/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchAction.java
 *    Description : �󗝓o�^�Ώۉ����񌟍��\���A�N�V�����N���X
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/05/30
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/05/30    V1.0        DIS.dyh        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.juri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �󗝓o�^�Ώۉ����񌟍��\���A�N�V�����N���X�B
 * �󗝓o�^�Ώۉ����񌟍���ʂ�\������B
 */
public class SearchAction extends BaseAction {

    public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {

        // -----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        // ��������������΃N���A����B
        removeFormBean(mapping, request);

        // �����������t�H�[�����Z�b�g����B
        JuriSearchForm searchForm = new JuriSearchForm();

        // ���ƃ��X�g�̎擾�i�S�����鎖�Ƌ敪�̂݁j
        UserInfo userInfo = container.getUserInfo();
        
//2006/06/06 by jzx update start
        //List jigyoList = LabelValueManager.getJigyoNameList(userInfo);
        List jigyoList = LabelValueManager.getJigyoNameListByJigyoCds2(userInfo);
//2006/06/06 by jzx update end
        
        // ------�v���_�E���f�[�^�Z�b�g
        searchForm.setJigyoNmList(jigyoList); // ���Ɩ����X�g

        updateFormBean(mapping, request, searchForm);

        // -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}