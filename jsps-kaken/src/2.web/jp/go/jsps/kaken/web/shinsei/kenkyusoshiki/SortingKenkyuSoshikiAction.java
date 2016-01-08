/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : ����
 *    Date        : 2007/7/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.kenkyusoshiki;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shinsei.RyoikiGaiyoForm;
import jp.go.jsps.kaken.web.struts.BaseAction;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * �����g�D�\�A�N�V�����N���X�B
 * �����g�D�\�ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SortingKenkyuSoshikiAction.java,v $"
 * Revision="$Revision: 1.15 $"
 * Date="$Date: 2007/07/19 02:11:57 $"
 */
public class SortingKenkyuSoshikiAction extends BaseAction {
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
        //      �O��ʂŉ��̈�ԍ�
        kenkyuSoshikiKenkyushaInfo.setKariryoikiNo(ryoikiGaiyoForm.getRyoikikeikakushoInfo().getKariryoikiNo());
        kenkyuSoshikiKenkyushaInfo.setJokyoId(JOKYO_ID);
        
        List result = ryoikiGaiyoForm.getShinseiDataInfo().getKenkyuSoshikiInfoList();

        try{
        	//�\�����̃`�F�b�N
        	ryoikiGaiyoForm.setErrorsList(null);
        	List errorsList = new ArrayList();
        	if(result != null && result.size()>0){
        		for (int i=0;i < result.size();i++){
        			String hyojijun = request.getParameter("shinseiDataInfo.kenkyuSoshikiInfoList["+i+"].HYOJIJUN");
        			((HashMap)(result).get(i)).put("HYOJIJUN",hyojijun);
                	if (hyojijun != null && !"".equals(hyojijun)){
                    	String property = "shinseiDataInfo.kenkyuSoshikiInfoList["+i+"].HYOJIJUN";
                    	String meg = "�\���� " + (i+1) + " �s��";
                        if (hyojijun.length()>3) {
    						ActionError error = new ActionError("errors.length",meg);
    						errors.add(property, error);
    						errorsList.add("�\���� " + (i+1) + "�s�ڂ�3���œ��͂��Ă��������B");
                        }
                        if (!StringUtil.isDigit(hyojijun)) {
    						ActionError error = new ActionError("errors.numeric",meg);
    						errors.add(property, error);
    						errorsList.add("�\���� " + (i+1) + "�s�ڂ͔��p�����œ��͂��Ă��������B");
                        }
                	}
        		}
        	}
        	
            // -----��ʑJ�ځi��^�����j-----
            if (!errors.isEmpty()) {
                saveErrors(request, errors);
                ryoikiGaiyoForm.setErrorsList(errorsList);
                return forwardFailure(mapping);
            }

        	if(result != null && result.size()>0){
                for (int i=0;i < result.size();i++){
                	String hyojijun = (String)((HashMap)(result).get(i)).get("HYOJIJUN");
                	//String hyojijun = request.getParameter("shinseiDataInfo.kenkyuSoshikiInfoList["+i+"].HYOJIJUN");
                	if (hyojijun != null){
                    	if( hyojijun.length() == 2){
                    		hyojijun =  "0"+hyojijun;
                    	}else if( hyojijun.length() == 1){
                    		hyojijun =  "00"+hyojijun;
                    	}else if( hyojijun.length() == 0){
                    		hyojijun =  "000";
                    	}
                    	kenkyuSoshikiKenkyushaInfo.setHyojijun(hyojijun);
                		kenkyuSoshikiKenkyushaInfo.setSystemNo((String)((HashMap)(result).get(i)).get("SYSTEM_NO"));
                		
                    	getSystemServise(
                				IServiceName.SHINSEI_MAINTENANCE_SERVICE).updateHyojijun(
                				container.getUserInfo(),
                				kenkyuSoshikiKenkyushaInfo);
                	}
                }
        	}

        	 result =
    			getSystemServise(
    				IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchKenkyuSosiki(
    				container.getUserInfo(),
    				kenkyuSoshikiKenkyushaInfo);
        	 
        	 ryoikiGaiyoForm.getShinseiDataInfo().setKenkyuSoshikiInfoList(result);
        	 
        }catch(NoDataFoundException e){
            errors.add("�Y���f�[�^�͂���܂���", new ActionError("errors.5002"));
        }catch(DataAccessException e){
            errors.add("�f�[�^��������DB�G���[���������܂����B",new ActionError("errors.4000"));
        }


        // �������ʂ��t�H�[���ɃZ�b�g����B
        request.setAttribute(IConstants.RESULT_INFO, ryoikiGaiyoForm);

        return forwardSuccess(mapping);
    }
}
