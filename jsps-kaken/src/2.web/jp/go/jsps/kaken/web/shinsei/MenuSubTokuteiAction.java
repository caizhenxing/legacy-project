/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : KariryoikiMenuAction.java
 *    Description : ���̈�ԍ����s�E�̈�v�揑�T�v�쐬�E�̈�������v�撲���m��i����̈挤���E�V�K�̈�j�A�N�V�����N���X
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/06/19
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/19    v1.0        DIS.dyh        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���̈�ԍ����s�E�̈�v�揑�T�v�쐬�E�̈�������v�撲���m��i����̈挤���E�V�K�̈�j�A�N�V�����N���X
 * @author DIS.dyh
 */
public class MenuSubTokuteiAction extends BaseAction {

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

        // �T�[�o�T�[�r�X�̌Ăяo���i�����v�撲���ꗗ�f�[�^�擾�j
        ISystemServise servise = getSystemServise(
                        IServiceName.TEISHUTU_MAINTENANCE_SERVICE);
        boolean isExist = servise.isExistRyoikiGaiyoInfo(container.getUserInfo());
        container.getUserInfo().getShinseishaInfo().setRyoikiGaiyoFlg(isExist);

        // �������ʂ����N�G�X�g�����ɃZ�b�g
        return forwardSuccess(mapping);
    }
}