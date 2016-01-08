/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import java.util.ArrayList;
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * �`�F�b�N���X�g�m��m�F�A�N�V�����N���X�B
 */
public class KakuteiCheckAction extends BaseAction {

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
// 20050606 Start
//update start dyh 2006/2/8
		CheckListForm listForm = (CheckListForm)form;
		checkInfo.setJigyoKubun(listForm.getJigyoKbn().trim());//���Ƌ敪
//		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
//update end dyh 2006/2/8
		ActionErrors errors = new ActionErrors();
// Horikoshi End
		CheckListForm checkForm = (CheckListForm)form;	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}

		//�L�������`�F�b�N
		boolean inPeriod = getSystemServise(
			IServiceName.CHECKLIST_MAINTENANCE_SERVICE).checkLimitDate(
			container.getUserInfo(),
			checkInfo);
			
		//�L�������`�F�b�N���ʂ��t�H�[���Ɋi�[
		checkForm.setPeriod(inPeriod);

// 20050714 �����҃}�X�^�̑��݃`�F�b�N
		List lstResult = new ArrayList();
		try{
			lstResult = blnKenkyushaExistCheck(container.getUserInfo(), checkInfo);
			for(int n=0; n<lstResult.size(); n++){
				ActionError error = new ActionError("errors.5051",lstResult.get(n));
				errors.add("kenkyuExists", error);
			}
		}catch(ApplicationException ex){
			throw new ApplicationException("�����҂̌�������DB�G���[���������܂����B",new ErrorInfo("errors.4004"),ex);
		}
		//���ʂ��t�H�[���Ɋi�[
		if(lstResult.isEmpty()){
			checkForm.setKenkyushaExist(true);
		}
		else{
			checkForm.setKenkyushaExist(false);
		}
// Horikoshi

		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);

// 20050722
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
// Horikoshi

		//�`�F�b�N���X�g�o�͊m�F��ʂ�
		return forwardSuccess(mapping);
	}

	List blnKenkyushaExistCheck(
			UserInfo userInfo,
			CheckListSearchInfo checkInfo
			)
	throws
			ApplicationException
	{

		List lstErrorInfo = new ArrayList();
//delete start dyh 2006/2/9 �g�p���Ȃ�
//		List lstKenkyusha = new ArrayList();		//������NO�A���������@�փR�[�h�i�[���X�g
//delete end dyh 2006/2/9
		try{
			lstErrorInfo = getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).chkKenkyushaExist(
					userInfo,
					checkInfo,
					null
					);
		}catch(ApplicationException ex){
			throw new ApplicationException("�����҂̌�������DB�G���[���������܂����B",new ErrorInfo("errors.4004"),ex);
		}
		finally{
//			lstKenkyusha.clear();
		}

		return lstErrorInfo;
	}
}
