/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/29
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �X�V���ꂽ�R�������̓��̓`�F�b�N���s���B
 * �R�����o�^���l�I�u�W�F�N�g���쐬����B
 * �C���m�F��ʂ�\������B 
 * 
 * ID RCSfile="$RCSfile: EditCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class EditCheckAction extends BaseAction {

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
		ShinsainForm editForm = (ShinsainForm) form;
		
		//------�Z�b�V�������X�V�Ώۏ��̎擾
		ShinsainInfo editInfo = container.getShinsainInfo();

		//VO�Ƀf�[�^���Z�b�g����B
		editInfo.setShozokuCd(editForm.getShozokuCd());
		editInfo.setShozokuName(editForm.getShozokuName());
//		editInfo.setBukyokuCd(editForm.getBukyokuCd());
		editInfo.setBukyokuName(editForm.getBukyokuName());
//		editInfo.setShokushuCd(editForm.getShokushuCd());
		editInfo.setShokushuName(editForm.getShokushuName());
//		editInfo.setKeiCd(editForm.getKeiCd());
//		editInfo.setLevelA1(editForm.getLevelA1());
//		editInfo.setLevelB11(editForm.getLevelB11());
//		editInfo.setLevelB12(editForm.getLevelB12());
//		editInfo.setLevelB13(editForm.getLevelB13());
//		editInfo.setLevelB21(editForm.getLevelB21());
//		editInfo.setLevelB22(editForm.getLevelB22());
//		editInfo.setLevelB23(editForm.getLevelB23());
//		editInfo.setShinsaKahi(editForm.getShinsaKahi());
		editInfo.setSofuZip(editForm.getSofuZip());
		editInfo.setSofuZipaddress(editForm.getSofuZipaddress());
		editInfo.setSofuZipemail(editForm.getSofuZipemail());
//		editInfo.setJitakuTel(editForm.getJitakuTel());
		editInfo.setShozokuTel(editForm.getShozokuTel());
//		editInfo.setSinkiKeizokuFlg(editForm.getSinkiKeizokuFlg());
//		//�I�����ꂽ�V�K�E�p��
//		if(editForm.getSinkiKeizokuFlg() != null) {
//			editInfo.setSinkiKeizokuHyoji(LabelValueManager.getSinkiKeizokuFlgList(editForm.getSinkiKeizokuFlg()));
//		} else{
//			editInfo.setSinkiKeizokuHyoji(editForm.getSinkiKeizokuHyoji());
//		}
//		editInfo.setShakin(editForm.getShakin());
//		//�I�����ꂽ�Ӌ���
//		if(editForm.getShakin() != null) {
//			editInfo.setShakinHyoji(LabelValueManager.getShakinList(editForm.getShakin()));
//		} else{
//			editInfo.setShakinHyoji(editForm.getShakinHyoji());
//		}
//		editInfo.setKey1(editForm.getKey1());
//		editInfo.setKey2(editForm.getKey2());
//		editInfo.setKey3(editForm.getKey3());
//		editInfo.setKey4(editForm.getKey4());
//		editInfo.setKey5(editForm.getKey5());
//		editInfo.setKey6(editForm.getKey6());
//		editInfo.setKey7(editForm.getKey7());
		editInfo.setUrl(editForm.getUrl());
		editInfo.setBiko(editForm.getBiko());
		
		editInfo.setNameKanjiSei(editForm.getNameKanjiSei());
		editInfo.setNameKanjiMei(editForm.getNameKanjiMei());
		editInfo.setNameKanaSei(editForm.getNameKanaSei());
		editInfo.setNameKanaMei(editForm.getNameKanaMei());
		editInfo.setShozokuFax(editForm.getShozokuFax());
		editInfo.setJigyoKubun(editForm.getJigyoKubun());
		editInfo.setSenmon(editForm.getSenmon());

//		2006/10/24 �Ո� �ǉ���������
        if("1".equals(request.getParameter("downloadFlag"))){
            editInfo.setDownloadFlag("1");
        } else {
            editInfo.setDownloadFlag("0");
        }
//		2006/10/24 �Ո� �ǉ������܂� 
		
		//�f�[�^�X�V���t�̐ݒ�
		DateUtil dateUtil = new DateUtil();
		editInfo.setKoshinDate(dateUtil.getCal().getTime());
		
//		//�Ϗ��J�n���iString��Date)
//		DateUtil dateUtil = new DateUtil();
//		String kizokuStartYear = editForm.getKizokuStartYear();
//		if(kizokuStartYear.equals("")){
//			//�Ϗ��J�n���E�N�������͂̏ꍇ��null���Z�b�g����
//			editInfo.setKizokuStart(null);
//		}else{
//			dateUtil.setCal(kizokuStartYear, editForm.getKizokuStartMonth(), editForm.getKizokuStartDay());
//			editInfo.setKizokuStart(dateUtil.getCal().getTime());
//		}
//		//�Ϗ��I�����iString��Date)
//		dateUtil = new DateUtil();
//		String kizokuEndYear = editForm.getKizokuEndYear();
//		if(kizokuEndYear.equals("")){
//			//�Ϗ��I�����E�N�������͂̏ꍇ��null���Z�b�g����
//			editInfo.setKizokuEnd(null);
//		}else{
//			dateUtil.setCal(kizokuEndYear, editForm.getKizokuEndMonth(), editForm.getKizokuEndDay());
//			editInfo.setKizokuEnd(dateUtil.getCal().getTime());
//		}
		//�L�������iString��Date)
		dateUtil = new DateUtil();
		dateUtil.setCal(editForm.getYukoDateYear(),editForm.getYukoDateMonth(),editForm.getYukoDateDay());
		editInfo.setYukoDate(dateUtil.getCal().getTime());
		//-------��
		
		try {
			//�T�[�o���̓`�F�b�N
			editInfo =
				getSystemServise(
					IServiceName.SHINSAIN_MAINTENANCE_SERVICE).validate(
					container.getUserInfo(),
					editInfo,
					IMaintenanceName.EDIT_MODE);
		} catch (ValidationException e) {
			//�T�[�o�[�G���[��ۑ��B
			saveServerErrors(request, errors, e);
			//---���͓��e�ɕs��������̂ōē���
			return forwardInput(mapping);
		}
		
		//-----�Z�b�V�����ɐR��������o�^����B
		container.setShinsainInfo(editInfo);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}

}
