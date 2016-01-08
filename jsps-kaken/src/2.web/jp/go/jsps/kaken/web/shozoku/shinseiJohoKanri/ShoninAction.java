/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ShoninAction.java
 *    Description : �����v�撲�����F��ʂ�\������
 *
 *    Author      : Admin
 *    Date        : 2004/02/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/02/12    V1.0        Admin          �V�K�쐬
 *    2006/06/05    V1.1        DIS.wangXC     �C���i�����v�撲�����F��ʂ�ǉ����邩��j
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �\����񏳔F�O�A�N�V�����N���X�B
 * ���F�Ώې\�������擾�B�Z�b�V�����ɓo�^����B 
 * �����v�撲�����F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ShoninAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:39 $"
 */
public class ShoninAction extends BaseAction {

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

		//## �폜���v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shinseishaInfo.�v���p�e�B��

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------���F�Ώې\�����V�X�e���ԍ��̎擾
		ShinseiDataForm shoninForm = (ShinseiDataForm)form;

// 2006/06/05 WangXiancheng update start
        if (isCancelled(request)) {
            removeFormBean(mapping,request);
            return forwardCancel(mapping);
        }
//		//------���F�Ώې\���V�X�e���ԍ��̎擾
//		ShinseiDataPk pkInfo = new ShinseiDataPk();
//		//------�L�[���
//		String systemNo = shoninForm.getSystemNo();
//		pkInfo.setSystemNo(systemNo);
//        
//�@�@�@�@�@//------�L�[�������ɐ\���f�[�^�擾	
//		SimpleShinseiDataInfo shinseiInfo = getSystemServise(
//                IServiceName.SHINSEI_MAINTENANCE_SERVICE)
//                .selectSimpleShinseiDataInfo(container.getUserInfo(),pkInfo);
//		
//		//------���F�Ώۏ������N�G�X�g�����ɃZ�b�g
//		container.setSimpleShinseiDataInfo(shinseiInfo);

        //------���F�Ώې\�����V�X�e���ԍ��̎擾
        String[] sysNos = shoninForm.getTantoSystemNo();
        ArrayList selectNos = new ArrayList();
        for (int i = 0; i < sysNos.length; i ++) {
           if (! StringUtil.isBlank(sysNos[i])) {
               selectNos.add(sysNos[i]);
           }
        } 
        if(selectNos.size()==0) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("errors.requiredSelect", "���F���鉞����"));
            saveErrors(request, errors);
            return forwardFailure(mapping);
        }
        //------���F�Ώې\���V�X�e���ԍ��̎擾
        ShinseiDataPk[] pkInfo  = new ShinseiDataPk[selectNos.size()];
        for (int i = 0; i < selectNos.size(); i ++) {
            pkInfo[i] = new ShinseiDataPk();
            pkInfo[i].setSystemNo((String)selectNos.get(i));
        }       
		//------�L�[�������ɐ\���f�[�^�擾
		SimpleShinseiDataInfo[] shinseiInfo = null;
		try {
            shinseiInfo = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE)
                    .selectSimpleShinseiDataInfos(container.getUserInfo(), pkInfo);
            // ------���F�Ώۏ������N�G�X�g�����ɃZ�b�g
            container.setSimpleShinseiDataInfos(shinseiInfo);
        }
        catch (NoDataFoundException e) {
			errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError(e.getErrorCode(), e.getMessage()));
		} catch (ApplicationException e) {
			errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError(e.getErrorCode(), e.getMessage()));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		ArrayList list =new ArrayList();
        for(int i=0;i<shinseiInfo.length;i++){
           list.add(shinseiInfo[i]); 
        }        
        request.getSession().setAttribute(IConstants.RESULT_INFO,list);		
		//-----��ʑJ�ځi��^�����j-----
		//if (!errors.isEmpty()) {
		//	saveErrors(request, errors);
		//	return forwardFailure(mapping);
		//}
// 2006/06/05 WangXiancheng update end

		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);

		return forwardSuccess(mapping);
	}
}