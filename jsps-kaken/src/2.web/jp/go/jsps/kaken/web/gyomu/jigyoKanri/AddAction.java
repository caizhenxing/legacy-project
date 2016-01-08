/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : AddAction.java
 *    Description : ���ƊǗ����o�^�O�A�N�V�����N���X
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;
import jp.go.jsps.kaken.util.DateUtil;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * ���ƊǗ����o�^�O�A�N�V�����N���X�B
 * �t�H�[���Ɏ��ƊǗ����o�^��ʂɕK�v�ȃf�[�^���Z�b�g����B
 * ���ƊǗ����V�K�o�^��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: AddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class AddAction extends BaseAction {

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

        //------�V�K�o�^�t�H�[�����̍쐬
        JigyoKanriForm addForm = new JigyoKanriForm();

        //------�X�V���[�h
        addForm.setAction(BaseForm.ADD_ACTION);

        // �Ɩ��S���ҏ��Ƀ��O�C�������Ɩ��S����ID���Z�b�g
        GyomutantoInfo gyomutantoInfo = new GyomutantoInfo();
        String gyomutantoId = (container.getUserInfo()).getGyomutantoInfo()
                .getGyomutantoId();
        gyomutantoInfo.setGyomutantoId(gyomutantoId);

        // ���O�C��ID���L�[�Ƃ��ċƖ��S���ҏ����擾
        gyomutantoInfo = getSystemServise(
                IServiceName.GYOMUTANTO_MAINTENANCE_SERVICE).select(
                container.getUserInfo(), gyomutantoInfo);

        //------�擾�����Ɩ��S���ҏ����Z�b�g
        if (gyomutantoInfo != null) {
            // �Ɩ��S����
            addForm.setTantokaName(gyomutantoInfo.getBukaName());
            // �Ɩ��S���W��
            addForm.setTantoKakari(gyomutantoInfo.getKakariName());
            // �₢���킹��S���Җ�
            addForm.setToiawaseName(gyomutantoInfo.getNameKanjiSei() + " "
                    + gyomutantoInfo.getNameKanjiMei());

//2007/3/9  ���u�j�@�ǉ� ��������
            // �N�x
            DateUtil dateUtil = new DateUtil();
            addForm.setNendo(dateUtil.getNendo());
//2007/3/9  ���u�j�@�ǉ� �����܂�
        }
        else {
            throw new SystemException("�Y������Ɩ��S���҂����݂��܂���B�Ɩ��S����ID'"
                    + gyomutantoId + "'");
        }

        //------�v���_�E���f�[�^�Z�b�g
        // ���ƃ��X�g�̎擾�i�S�����鎖�Ƌ敪�̂݁j
        addForm.setJigyoNameList(LabelValueManager.getJigyoNameList(container
                .getUserInfo()));

        //------���W�I�{�^���f�[�^�Z�b�g
        // �]���p�t�@�C���L��
        addForm.setFlgList(LabelValueManager.getFlgList());

        //------�V�K�o�^�t�H�[���ɃZ�b�g����B
        updateFormBean(mapping, request, addForm);

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        return forwardSuccess(mapping);
    }
}