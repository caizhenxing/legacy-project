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
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinsainPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �R�������X�V�O�A�N�V�����N���X�B
 * �L�[�Ɉ�v����R���������擾�A�����l�Ƃ��ăf�[�^���Z�b�g����B
 * �R�������X�V��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: EditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class EditAction extends BaseAction {

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

		//## �X�V�s�̃v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shinsainInfo.�v���p�e�B��
		//## �X�V�Ώۃv���p�e�B 				$!shinsainForm.�v���p�e�B��
		//##

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�X�V�Ώې\���ҏ��̎擾
		ShinsainPk pkInfo = new ShinsainPk();
		//------�L�[���
		String shinsainNo = ((ShinsainForm)form).getShinsainNo();
		String jigyoKubun = ((ShinsainForm)form).getJigyoKubun();
		pkInfo.setShinsainNo(shinsainNo);
		pkInfo.setJigyoKubun(jigyoKubun);
		
		//------�L�[�������ɍX�V�f�[�^�擾	
		ShinsainInfo editInfo = getSystemServise(IServiceName.SHINSAIN_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
	
		//------�X�V�Ώۏ����Z�b�V�����ɓo�^�B
		container.setShinsainInfo(editInfo);
		
		//------�X�V�Ώې\���ҏ����A�X�V�o�^�t�H�[�����̍X�V
		ShinsainForm editForm = new ShinsainForm();
		editForm.setAction(BaseForm.EDIT_ACTION);
			
//		//------���W�I�{�^���f�[�^�Z�b�g
//		//�V�K��p��
//		editForm.setSinkiKeizokuFlgList(LabelValueManager.getSinkiKeizokuFlgList());
//		//�Ӌ�
//		editForm.setShakinList(LabelValueManager.getShakinList());

		try {
//			BeanUtils.copyProperty(editForm,"shinsainId",editInfo.getShinsainId());
//			BeanUtils.copyProperty(editForm,"shinsainNo",editInfo.getShinsainNo());
			BeanUtils.copyProperty(editForm,"nameKanjiSei",editInfo.getNameKanjiSei());
			BeanUtils.copyProperty(editForm,"nameKanjiMei",editInfo.getNameKanjiMei());
			BeanUtils.copyProperty(editForm,"nameKanaSei",editInfo.getNameKanaSei());
			BeanUtils.copyProperty(editForm,"nameKanaMei",editInfo.getNameKanaMei());
			BeanUtils.copyProperty(editForm,"shozokuCd",editInfo.getShozokuCd());
			BeanUtils.copyProperty(editForm,"shozokuName",editInfo.getShozokuName());
//			BeanUtils.copyProperty(editForm,"bukyokuCd",editInfo.getBukyokuCd());
			BeanUtils.copyProperty(editForm,"bukyokuName",editInfo.getBukyokuName());
//			BeanUtils.copyProperty(editForm,"shokushuCd",editInfo.getShokushuCd());
			BeanUtils.copyProperty(editForm,"shokushuName",editInfo.getShokushuName());
//			BeanUtils.copyProperty(editForm,"keiCd",editInfo.getKeiCd());
//			BeanUtils.copyProperty(editForm,"levelA1",editInfo.getLevelA1());
//			BeanUtils.copyProperty(editForm,"levelB11",editInfo.getLevelB11());
//			BeanUtils.copyProperty(editForm,"levelB12",editInfo.getLevelB12());
//			BeanUtils.copyProperty(editForm,"levelB13",editInfo.getLevelB13());
//			BeanUtils.copyProperty(editForm,"levelB21",editInfo.getLevelB21());
//			BeanUtils.copyProperty(editForm,"levelB22",editInfo.getLevelB22());
//			BeanUtils.copyProperty(editForm,"levelB23",editInfo.getLevelB23());
//			BeanUtils.copyProperty(editForm,"shinsaKahi",editInfo.getShinsaKahi());
			BeanUtils.copyProperty(editForm,"sofuZip",editInfo.getSofuZip());
			BeanUtils.copyProperty(editForm,"sofuZipaddress",editInfo.getSofuZipaddress());
			BeanUtils.copyProperty(editForm,"sofuZipemail",editInfo.getSofuZipemail());
//			BeanUtils.copyProperty(editForm,"jitakuTel",editInfo.getJitakuTel());
			BeanUtils.copyProperty(editForm,"shozokuTel",editInfo.getShozokuTel());
//			BeanUtils.copyProperty(editForm,"sinkiKeizokuFlg",editInfo.getSinkiKeizokuFlg());
//			BeanUtils.copyProperty(editForm,"shakin",editInfo.getShakin());
//			BeanUtils.copyProperty(editForm,"key1",editInfo.getKey1());
//			BeanUtils.copyProperty(editForm,"key2",editInfo.getKey2());
//			BeanUtils.copyProperty(editForm,"key3",editInfo.getKey3());
//			BeanUtils.copyProperty(editForm,"key4",editInfo.getKey4());
//			BeanUtils.copyProperty(editForm,"key5",editInfo.getKey5());
//			BeanUtils.copyProperty(editForm,"key6",editInfo.getKey6());
//			BeanUtils.copyProperty(editForm,"key7",editInfo.getKey7());
			BeanUtils.copyProperty(editForm,"url",editInfo.getUrl());
			BeanUtils.copyProperty(editForm,"biko",editInfo.getBiko());
			BeanUtils.copyProperty(editForm,"jigyoKubun",editInfo.getJigyoKubun());
			BeanUtils.copyProperty(editForm,"shozokuFax",editInfo.getShozokuFax());
			BeanUtils.copyProperty(editForm,"senmon",editInfo.getSenmon());

//			2006/10/24    �Ո�	�ǉ���������
			BeanUtils.copyProperty(editForm,"downloadFlag",editInfo.getDownloadFlag());
//			2006/10/24    �Ո�	�ǉ������܂�
			
			//���t�̐ݒ�		
			Calendar calendar = Calendar.getInstance();
//			//�Ϗ��J�n��
//			if(editInfo.getKizokuStart() != null){
//				calendar.setTime(editInfo.getKizokuStart());
//				editForm.setKizokuStartYear("" + calendar.get(Calendar.YEAR));
//				editForm.setKizokuStartMonth("" + (calendar.get(Calendar.MONTH) + 1));
//				editForm.setKizokuStartDay("" + calendar.get(Calendar.DATE));
//			}
//			//�Ϗ��I����
//			if(editInfo.getKizokuEnd() != null){
//				calendar.setTime(editInfo.getKizokuEnd());
//				editForm.setKizokuEndYear("" + calendar.get(Calendar.YEAR));
//				editForm.setKizokuEndMonth("" + (calendar.get(Calendar.MONTH) + 1));
//				editForm.setKizokuEndDay("" + calendar.get(Calendar.DATE));
//			}
			//�L������
			if(editInfo.getYukoDate() != null){
				calendar.setTime(editInfo.getYukoDate());
				editForm.setYukoDateYear("" + calendar.get(Calendar.YEAR));
				editForm.setYukoDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
				editForm.setYukoDateDay("" + calendar.get(Calendar.DATE));
			}

		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		//------�C���o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping,request,editForm);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
