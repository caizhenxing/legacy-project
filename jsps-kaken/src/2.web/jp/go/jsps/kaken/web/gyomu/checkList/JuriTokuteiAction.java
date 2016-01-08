/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JuriSaveTokuteiAction.java
 *    Description : �󗝓o�^�m�F(����)���s���B
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/14    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import jp.go.jsps.kaken.web.common.*;

/**
 * �󗝓o�^�m�F(����)���s���B
 * 
 * @author masuo_t
 */
public class JuriTokuteiAction extends BaseAction {

// 2006/07/21 dyh delete start ���R�F�g�p���Ȃ�
//    /** ��ID��04(�w�U��t��)�̂��̂�\�� */
//    private static String[] JIGYO_IDS = new String[]{
//        StatusCode.STATUS_GAKUSIN_SHORITYU        //�w�U��t��
//    };
// 2006/07/21 dyh delete end

    public ActionForward doMainProcessing(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            UserContainer container)
            throws ApplicationException {

        CheckListForm checkListForm = (CheckListForm) form;                //���������̎擾
        JuriCheckListForm juriForm = new JuriCheckListForm();            //�󗝃t�H�[������
        CheckListSearchInfo searchInfo = new CheckListSearchInfo();        //-------�� VO�Ƀf�[�^���Z�b�g����B

        //���������̐ݒ�
        if (checkListForm.getJigyoId() != null
                && !checkListForm.getJigyoId().equals("")) {
            searchInfo.setJigyoCd(checkListForm.getJigyoId().substring(2, 7));
            searchInfo.setJigyoId(checkListForm.getJigyoId());
            juriForm.setJigyoCD(checkListForm.getJigyoId().substring(2, 7));
            juriForm.setJigyoID(checkListForm.getJigyoId());
        }
        if (checkListForm.getShozokuCd() != null
                && !checkListForm.getShozokuCd().equals("")) {
            searchInfo.setShozokuCd(checkListForm.getShozokuCd());
            juriForm.setShozokuCD(checkListForm.getShozokuCd());
        }
        if(checkListForm.getKaisu() != null
                && !checkListForm.getKaisu().equals("")){
            searchInfo.setKaisu(checkListForm.getKaisu());
            juriForm.setKaisu(checkListForm.getKaisu());
        }

// 20050719
        // ��ID��04�܂���07�̂��̂�\��
        if(checkListForm.getJokyoId() != null
                && !checkListForm.getJokyoId().equals("")){
            String[] JokyoStr = new String[]{checkListForm.getJokyoId()};
            searchInfo.setSearchJokyoId(JokyoStr);                    //��ID�����������ɃZ�b�g
            juriForm.setJokyoID(checkListForm.getJokyoId());        //��ID���󗝃t�H�[���ɃZ�b�g
        }
//        //��ID��04(�w�U��t��)�̂��̂�\��
//        searchInfo.setSearchJokyoId(JIGYO_IDS);
// Horikoshi
        searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);        //���������ɓ���̈�̎��Ƌ敪��ǉ�

        // �������s
        Page result = getSystemServise(
                IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectCheckList(
                container.getUserInfo(), searchInfo, true);

        //�������ʂ��Z�b�g����
        juriForm.setCheckListPage(result);                                    //��������
        juriForm.setJuriFujuri(LabelValueManager.getJuriFujuriList());        //���X�g����
        juriForm.setJuriKekka(checkListForm.getJokyoId());                    //���X�g�\��
        request.setAttribute(IConstants.RESULT_INFO, result);                //

        //�󗝔��l���������ʂ���擾���ĕ\��
        HashMap dataMap = (HashMap)result.getList().get(0);
        Object juriBiko = dataMap.get("JURI_BIKO");
        if(juriBiko != null){
            juriForm.setJuriBiko(juriBiko.toString());
        }

        //TODO�FsetAttribute()�̎g�p���@
        request.getSession().setAttribute("juriCheckListForm", juriForm);    //�󗝃t�H�[��
//        updateFormBean(mapping, request, juriForm);

        //�g�[�N�����Z�b�V�����ɕۑ�����B
        saveToken(request);
        return forwardSuccess(mapping);
    }
}