/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.rule;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���[���ݒ���l�I�u�W�F�N�g��o�^����B
 * �t�H�[�����A�X�V�����N���A����B
 *  
 * ID RCSfile="$RCSfile: EditSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:45 $"
 */
public class EditSaveAction extends BaseAction {

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

		//------�C���o�^�t�H�[�����̎擾
		RuleForm editForm = (RuleForm) form;

		//------�C���o�^���̐ݒ�
		List editList = new ArrayList();
		for(int i = 0; i < editForm.getTaishoIdList().size(); i++){
			RuleInfo editInfo = new RuleInfo();
			if(editForm.getTaishoIdList().get(i) != null){
				editInfo.setTaishoId(editForm.getTaishoIdList().get(i).toString());
			}
			if(editForm.getMojisuChkList().get(i) != null) {
				editInfo.setMojisuChk(editForm.getMojisuChkList().get(i).toString());
			}
			if(editForm.getCharChk1List().get(i) != null) {
				editInfo.setCharChk1(editForm.getCharChk1List().get(i).toString());
			}
			if(editForm.getCharChk2List().get(i) != null) {
				editInfo.setCharChk2(editForm.getCharChk2List().get(i).toString());
			}
/*���ݖ��g�p�ɂ����O
			if(editForm.getCharChk3List().get(i) != null) {
				editInfo.setCharChk3(editForm.getCharChk3List().get(i).toString());
			}
			if(editForm.getCharChk4List().get(i) != null) {
				editInfo.setCharChk4(editForm.getCharChk4List().get(i).toString());
			}
			if(editForm.getCharChk5List().get(i) != null) {
				editInfo.setCharChk5(editForm.getCharChk5List().get(i).toString());
			}
			if(editForm.getBikoList().get(i) != null) {
				editInfo.setBiko(editForm.getBikoList().get(i).toString());
			}
*/
			//�L�������iString��Date)
			if(editForm.getYukoDateYearList().get(i) != null && editForm.getYukoDateMonthList().get(i) != null && editForm.getYukoDateDayList().get(i) != null) {
				DateUtil dateUtil = new DateUtil();
				dateUtil.setCal(editForm.getYukoDateYearList().get(i).toString(), editForm.getYukoDateMonthList().get(i).toString(), editForm.getYukoDateDayList().get(i).toString());
				editInfo.setYukoDate(dateUtil.getCal().getTime());
			}

			editList.add(editInfo);
		}

		//------�X�V
		ISystemServise servise = getSystemServise(
						IServiceName.RULE_MAINTENANCE_SERVICE);
		servise.updateAll(container.getUserInfo(),editList);

		if (log.isDebugEnabled()) {
			log.debug("���[���ݒ��� �C���o�^ '" + editList + "'");
		}
		
		//------�g�[�N���̍폜	
		resetToken(request);
		//------�t�H�[�����̍폜
		removeFormBean(mapping,request);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
