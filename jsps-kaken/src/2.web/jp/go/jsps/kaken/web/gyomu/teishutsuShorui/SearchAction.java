/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchAction.java
 *    Description : ��o���ތ����\���A�N�V�����N���X
 *
 *    Author      : DIS.lwj
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.lwj        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ��o���ތ����\���A�N�V�����N���X�B
 * ��o���ތ�����ʂ�\������B
 */
public class SearchAction extends BaseAction {

    public ActionForward doMainProcessing(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, UserContainer container)
            throws ApplicationException {

        //-----ActionErrors�̐錾�i��^�����j-----
        //ActionErrors errors = new ActionErrors();
        
        // ��������������΃N���A����B
        removeFormBean(mapping, request);

        // �����������t�H�[�����Z�b�g����B
        TeishutsuShoruiSearchForm searchForm = new TeishutsuShoruiSearchForm();

        // ------�v���_�E���f�[�^�Z�b�g
        List result = LabelValueManager.getJigyoNameListByJigyoCds2(container.getUserInfo(),
                IJigyoCd.JIGYO_CD_TOKUTEI_SINKI);               
        searchForm.setJigyoList(result);
        //�󗝏󋵂̃��X�g���Z�b�g����B
        searchForm.setJuriList(LabelValueManager.getJuriJokyoList2());
        
        HttpSession session=request.getSession(true);
        session.setAttribute(IConstants.RESULT_INFO, result);

        updateFormBean(mapping, request, searchForm);
        
        return forwardSuccess(mapping);
    }
}