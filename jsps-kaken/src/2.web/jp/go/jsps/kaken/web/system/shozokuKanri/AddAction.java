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
package jp.go.jsps.kaken.web.system.shozokuKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.KikanInfo;
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �����@�֏��o�^�O�A�N�V�����N���X�B
 * �t�H�[���ɏ����@�֏��o�^��ʂɕK�v�ȃf�[�^���Z�b�g����B
 * �����@�֏��V�K�o�^��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: AddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:03 $"
 */
public class AddAction extends BaseAction {

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

		//���������̎擾
		ShozokuForm searchForm = (ShozokuForm)form;
		
		//------�������[�h
		searchForm.setAction(BaseForm.ADD_ACTION);
		
		//�����@�֒S���ҏ��e�[�u���ɂ��łɏ����@�փR�[�h��
		//���������@�֒S���҂��o�^����Ă��Ȃ����ǂ������m�F
		int count =
			getSystemServise(
				IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(
				container.getUserInfo(),
				searchForm.getShozokuCd());	

		//���łɓo�^����Ă���ꍇ
		if(count > 0){
			String[] error = {"���������@�֒S����"};
			throw new ApplicationException("���łɏ����@�֒S���҂��o�^����Ă��܂��B�����L�[�F�����@�փR�[�h'" 
														+ searchForm.getShozokuCd() + "'", 	new ErrorInfo("errors.4007", error));			
		}
		
		KikanInfo kikanInfo = new KikanInfo();
		kikanInfo.setShozokuCd(searchForm.getShozokuCd());//�����@�փR�[�h
		
		//�����@�փ}�X�^���珊���@�֏����擾
		KikanInfo result =
			getSystemServise(
				IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(
				container.getUserInfo(),
					kikanInfo);	

		//------�L�����������l�Z�b�g
		RulePk rulePk = new RulePk();
		rulePk.setTaishoId(ITaishoId.SHOZOKUTANTO);
		RuleInfo ruleInfo =
			getSystemServise(
				IServiceName.RULE_MAINTENANCE_SERVICE).select(
				container.getUserInfo(),
				rulePk);
		Calendar cal = Calendar.getInstance();
		cal.setTime(ruleInfo.getYukoDate());

		ShozokuForm addForm = new ShozokuForm();
		//------�f�[�^���͂�2�y�[�W��
		addForm.setPage(2);
	
		//�t�H�[���Ƀf�[�^���Z�b�g����B	
		addForm.setShozokuCd(result.getShozokuCd());							//�����@�֖��i�R�[�h�j
		addForm.setShozokuNameEigo(result.getShozokuNameEigo());				//�����@�֖��i�p��j
		addForm.setTantoTel(result.getShozokuTel());							//�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
		addForm.setTantoFax(result.getShozokuFax());							//�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
		addForm.setTantoZip(result.getShozokuZip());							//�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
		if(result.getShozokuAddress2() != null && result.getShozokuAddress2().length() != 0){
			addForm.setTantoAddress(result.getShozokuAddress1() + " " + result.getShozokuAddress2());	//�S���ҕ��Ǐ��ݒn�i�Z���j
		}else{
			addForm.setTantoAddress(result.getShozokuAddress1());
		}
		addForm.setBiko(result.getBiko());										//���l
		addForm.setYukoDateYear(Integer.toString(cal.get(Calendar.YEAR)));		//�L�������i�N�j
		addForm.setYukoDateMonth(Integer.toString(cal.get(Calendar.MONTH)+1));	//�L�������i���j
		addForm.setYukoDateDay(Integer.toString(cal.get(Calendar.DATE)));		//�L�������i���j	

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShozokuInfo addInfo = new ShozokuInfo();
		addInfo.setShozokuCd(result.getShozokuCd());				//�����@�֖��i�R�[�h�j
		addInfo.setShubetuCd(result.getShubetuCd());				//�@�֎��		
		addInfo.setShozokuName(result.getShozokuNameKanji());		//�����@�֖��i���{��j
		addInfo.setShozokuRyakusho(result.getShozokuRyakusho());	//�����@�֖��i���́j
		addInfo.setShozokuNameEigo(result.getShozokuNameEigo());	//�����@�֖��i�p��j
		//-------��
				
		//-----�Z�b�V�����ɏ����@�֏���o�^����B
		container.setShozokuInfo(addInfo);		

		//------�o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping,request,addForm);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
