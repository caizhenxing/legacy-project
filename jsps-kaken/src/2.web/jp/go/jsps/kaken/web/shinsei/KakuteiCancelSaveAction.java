/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : KatuteiCancelSaveAction.java
 *    Description : �̈�������v�撲���m��������s���A�N�V�����N���X�B
 *
 *    Author      : DIS.jzx
 *    Date        : 2006/06/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS.jzx        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �̈�������v�撲���m��������s���A�N�V�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KakuteiCancelSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:00 $"
 */
public class KakuteiCancelSaveAction extends BaseAction {
    
    /** �����@�֎�t��(��ID:03)�ȍ~�̏�ID */
    private static String[] JOKYO_ID = new String[]{       
        StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI //�\���󋵁F�u�̈��\�Ҋm��ς݁v
    };
	
    /* (�� Javadoc)
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

        //-----�ȈՐ\�������̓t�H�[���̎擾
        RyoikiGaiyoForm ryoikiGaiyoForm =(RyoikiGaiyoForm)form;
        ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();
        shinseiDataInfo.setSystemNo(ryoikiGaiyoForm.getRyoikiSystemNo());
        shinseiDataInfo.setRyouikiNo(ryoikiGaiyoForm.getKariryoikiNo());
        //�\���󋵁F�u�̈��\�Ҋm�F���v 
        shinseiDataInfo.setJokyoIds(JOKYO_ID);
        try {
            getSystemServise(IServiceName.TEISHUTU_MAINTENANCE_SERVICE) 
                 .cancelKakuteiRyoikiGaiyo(container.getUserInfo(),shinseiDataInfo);
        }catch (NoDataFoundException ex) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.9000","���Y�̈�������v�撲���m�����"));
        }catch (ApplicationException ex) {
            errors.add("�f�[�^��������DB�G���[���������܂����B", new ActionError("errors.4000"));
        }

        //-----��ʑJ�ځi��^�����j-----
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        
        return forwardSuccess(mapping); 
    }
}