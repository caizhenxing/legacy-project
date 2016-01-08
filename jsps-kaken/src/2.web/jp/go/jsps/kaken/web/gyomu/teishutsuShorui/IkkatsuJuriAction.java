/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : IkkatsuJuriAction.java
 *    Description : �ꊇ��(��o���ވꗗ)��ʂ�\������A�N�V�����N���X
 *
 *    Author      : Dis.mcj
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.mcj        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.teishutsuShorui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �ꊇ��(��o���ވꗗ)��ʂ�\������A�N�V�����N���X
 */
public class IkkatsuJuriAction extends BaseAction {

    /** ��ID��04(�w�U��t��)�̂��̂�\�� */
    private static String[] JIGYO_IDS = new String[] { 
        StatusCode.STATUS_GAKUSIN_SHORITYU // �w�U��t��
    };

    public ActionForward doMainProcessing(
            ActionMapping mapping, 
            ActionForm form,
            HttpServletRequest request, 
            HttpServletResponse response, 
            UserContainer container)
            throws ApplicationException {

        // -----ActionErrors�̐錾�i��^�����j-----
        ActionErrors errors = new ActionErrors();

        // ���������̎擾
        TeishutsuShoruiSearchInfo searchInfo = container.getTeishutsuShoruiSearchInfo();

        searchInfo.setRyoikiJokyoId(JIGYO_IDS);
        searchInfo.setDelFlg("0");
        List result = null;
        try {
            // �������s
            result = getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE)
                    .selectTeishutuShoruiList(container.getUserInfo(), searchInfo);

            // ------���F�Ώۏ������N�G�X�g�����ɃZ�b�g
            container.setTeishutsuShoruiSearchInfo(searchInfo);
            container.setShoruiKanriList(result);          
        } catch (NoDataFoundException e) {
            errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("errors.5045","�ꊇ�󗝑Ώ�"));
        } catch (ApplicationException e) {
            errors.add("���������ŃG���[���������܂����B",new ActionError("errors.4004"));
        } 

        // �������ʂ��Z�b�g����B
        request.setAttribute(IConstants.RESULT_INFO, result);

        // �g�[�N�����Z�b�V�����ɕۑ�����B
        saveToken(request);
        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}