/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : RyoikiGaiyoAction.java
 *    Description :  �̈�v�揑�쐬��ʂ̕\���A�N�V�����N���X
 *
 *    Author      : DIS.miaom & DIS.dyh
 *    Date        : 2006/06/19
 *
 *    Revision history
 *    Date          Revision    Author                 Description
 *    2006/06/19    v1.0        DIS.miaom & DIS.dyh    �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * �̈�v�揑�쐬��ʃA�N�V�����N���X�B
 * �̈�v�揑�Ɨ̈�v�揑�̈ꗗ���擾����
 * �̈�v�揑�쐬��ʂ�\������B
 */
public class RyoikiGaiyoAction extends BaseAction{

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

        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm)form;
        String screenFlg = ryoikiGaiyoForm.getScreenFlg();
        String kariryoikiNo = ryoikiGaiyoForm.getKariryoikiNo();
        if(!StringUtil.isBlank(screenFlg) && "come".equals(screenFlg)){
            kariryoikiNo = "";
        }

        // �T�[�o�T�[�r�X�̌Ăяo���i�����v�撲���ꗗ�f�[�^�擾�j
        ISystemServise servise = getSystemServise(
                        IServiceName.TEISHUTU_MAINTENANCE_SERVICE);
        List resultList = servise.getRyoikiAndKenkyuList(
                container.getUserInfo(), kariryoikiNo);

        if(resultList == null || resultList.size() == 0
                || (List)resultList.get(0) == null
                || ((List)resultList.get(0)).size() == 0){
            errors.add("�̈�v�揑�ꗗ�f�[�^",new ActionError("errors.5002"));
        }

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }

        // ����ʂ���J�ڎ�(�V�X�e����t�ԍ������w��)�A�V�X�e����t�ԍ���ݒ�
        if(StringUtil.isBlank(kariryoikiNo)){
            HashMap map = (HashMap)((List)resultList.get(0)).get(0);
            String ryoikiSystemNo0 = (String)map.get("RYOIKI_SYSTEM_NO");
            String kariryoikiNo0 = (String)map.get("KARIRYOIKI_NO");
            ryoikiGaiyoForm.setRyoikiSystemNo(ryoikiSystemNo0);
            ryoikiGaiyoForm.setKariryoikiNo(kariryoikiNo0);
        }

        // �������ʂ����N�G�X�g�����ɃZ�b�g
        request.setAttribute(IConstants.RESULT_INFO, resultList);
        updateFormBean(mapping,request,ryoikiGaiyoForm);

        return forwardSuccess(mapping);
    }
}