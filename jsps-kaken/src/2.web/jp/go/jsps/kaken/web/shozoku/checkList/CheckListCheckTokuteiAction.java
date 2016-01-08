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

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �`�F�b�N���X�g�o�͊m�F�A�N�V�����N���X�B
 * �`�F�b�N���X�g�̏�ID�𔻕ʂ��A
 * '03'(�����@�֎�t��)�̏ꍇ�̓`�F�b�N���X�g�o�͊m�F��ʂ�\������B
 * '04'(�w�U��t��)�̏ꍇ�́A�`�F�b�N���X�g�A�N�V�����N���X�����_�C���N�g�ŌĂяo���B
 * 
 */
public class CheckListCheckTokuteiAction extends BaseAction {

	private static final String FORWARD_KIKAN = "kikan";
	private static final String FORWARD_GAKUSIN = "gakusin";
	private static final String SYOZOKU_UKETUKETYU = "03";
	private static final String GAKUSIN_UKETUKETYU = "04";

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//�錾
		//ShinseiSearchForm searchForm = new ShinseiSearchForm();

		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
// 20050606 Start
		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi End
		CheckListForm checkForm = (CheckListForm)request.getSession().getAttribute("checkListForm");	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}

		//�������s
		String jokyoId = 
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).checkJokyoId(
				container.getUserInfo(),
				checkInfo);

		//2005/04/12 �ǉ� ��������--------------------------------------------------
		//�L�������`�F�b�N�̒ǉ�
		boolean inPeriod = getSystemServise(
			IServiceName.CHECKLIST_MAINTENANCE_SERVICE).checkLimitDate(
			container.getUserInfo(),
			checkInfo);
		//�ǉ� �����܂�-------------------------------------------------------------
		
		//��ID�ɂ���đJ�ڐ��ύX����	  
		if(jokyoId != null && jokyoId.equals(SYOZOKU_UKETUKETYU)){ 	       //�����@�֎�t��:03	
			//�L�������`�F�b�N���ʂ��t�H�[���Ɋi�[
			checkForm.setPeriod(inPeriod);
			//�����������t�H�[�����Z�b�g����B
			request.setAttribute(IConstants.RESULT_INFO, checkForm);
			//�`�F�b�N���X�g�o�͊m�F��ʂ�
			return mapping.findForward(FORWARD_KIKAN);
		
		//2005/04/11 �ǉ� ��������--------------------------------------------------
		//�w�U��t���ȊO�ɂ��\������悤�ɏC��	
		}else if(jokyoId != null && jokyoId.equals(GAKUSIN_UKETUKETYU)     //�w�U��t��:04
			|| jokyoId.equals(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA)       //�����@�֋p��:05
			|| jokyoId.equals(StatusCode.STATUS_GAKUSIN_JYURI)             //�w�U��:06
			|| jokyoId.equals(StatusCode.STATUS_GAKUSIN_FUJYURI)           //�w�U�s��:07
			|| jokyoId.equals(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO) //�R��������U�菈����:08
			|| jokyoId.equals(StatusCode.STATUS_WARIFURI_CHECK_KANRYO)     //����U��`�F�b�N����:09
			|| jokyoId.equals(StatusCode.STATUS_1st_SHINSATYU)             //1���R����:10
			|| jokyoId.equals(StatusCode.STATUS_1st_SHINSA_KANRYO)         //1���R���F����:11
			|| jokyoId.equals(StatusCode.STATUS_2nd_SHINSA_KANRYO)){       //2���R������:12
		
		//�ǉ� �����܂�-------------------------------------------------------------	
			
			//�`�F�b�N���X�g�o��
			return mapping.findForward(FORWARD_GAKUSIN);					
		}else{
			errors.add("errors.5006", new ActionError("errors.5006", "�`�F�b�N���X�g"));
			saveErrors(request, errors);
			//	�G���[����
			return forwardFailure(mapping);		
		}
	}
}