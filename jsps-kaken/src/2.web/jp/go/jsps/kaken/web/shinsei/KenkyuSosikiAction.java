/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : kenkyuSosikiAction.java
 *    Description : �����g�D�\�A�N�V����
 *
 *    Author      : ���`��
 *    Date        : 2006/06/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/16    V1.0        ���`��     �@�@�@�@�@�@�@�V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
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
 * �����g�D�\�A�N�V����
 * ID RCSfile="$RCSfile: KenkyuSosikiAction.java,v $"
 * Revision="$Revision: 1.10 $"
 * Date="$Date: 2007/07/21 06:08:03 $"
 */
public class KenkyuSosikiAction extends BaseAction{
	
    /**�Y���\���f�[�^�Ǘ��e�[�u���AJOKYO_ID=[21,23]�̎擾*/
    private static String[] JOKYO_ID = new String[]{
        StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN,     // �\���󋵁F�u�̈��\�Ҋm�F���v
        StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI, // �\���󋵁F�u�̈��\�Ҋm��ς݁v
    };

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

        RyoikiGaiyoForm ryoikiGaiyoForm = (RyoikiGaiyoForm) form;
        KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo = new KenkyuSoshikiKenkyushaInfo();

        // �O��ʂŉ��̈�ԍ�
        kenkyuSoshikiKenkyushaInfo.setKariryoikiNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getKariryoikiNo());
        
        kenkyuSoshikiKenkyushaInfo.setJokyoId(JOKYO_ID);
        
        List result = null;
//      <!-- UPDATE�@START 2007/07/16 BIS ���� -->  
        //�\�����̃G���[���b�Z�[�W���N���A
        ryoikiGaiyoForm.setErrorsList(null);
//      <!-- UPDATE�@END 2007/07/16 BIS ���� -->  
        try{
        	 result =
    			getSystemServise(
    				IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchKenkyuSosiki(
    				container.getUserInfo(),
    				kenkyuSoshikiKenkyushaInfo);
//        	<!-- ADD�@START 2007/07/16 BIS ���� -->
        	 ryoikiGaiyoForm.getShinseiDataInfo().setKenkyuSoshikiInfoList(result);
//        	<!-- ADD�@END 2007/07/16 BIS ���� -->
        }catch(NoDataFoundException ne){
            errors.add("�Y���f�[�^�͂���܂���", new ActionError("errors.5002"));
        }catch(DataAccessException e){
            errors.add("�f�[�^��������DB�G���[���������܂����B",new ActionError("errors.4000"));
        }
        
        // -----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        // �������ʂ��t�H�[���ɃZ�b�g����B
//      <!-- UPDATE�@START 2007/07/16 BIS ���� -->        
        //request.setAttribute(IConstants.RESULT_INFO, result);
        updateFormBean(mapping, request, ryoikiGaiyoForm);
//      <!-- UPDATE�@END 2007/07/16 BIS ���� -->
        return forwardSuccess(mapping);
    }

}
