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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.ILabelKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �R�����ʓ��͉�ʕ\���O�A�N�V�����N���X�B
 * �R�����ʓ��͉�ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaInputAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ShinsaKekkaInputAction extends BaseAction {

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
		
		//------�L�[���
		ShinsaKekkaPk pkInfo  = new ShinsaKekkaPk();
		pkInfo.setSystemNo(((ShinsaKekkaForm)form).getSystemNo());		//�V�X�e����t�ԍ�
		pkInfo.setShinsainNo(container.getUserInfo().getShinsainInfo().getShinsainNo());	//�R�����ԍ�
		pkInfo.setJigyoKubun(container.getUserInfo().getShinsainInfo().getJigyoKubun());	//���Ƌ敪	
			
		//------�L�[�������ɍX�V�f�[�^�擾	
		ShinsaKekkaInputInfo selectInfo = 
					getSystemServise(IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE).select1stShinsaKekka(
								container.getUserInfo(),
								pkInfo);
		
		//------�X�V�ΏېR�����ʏ����A�o�^�t�H�[�����̍X�V
		ShinsaKekkaForm inputForm = new ShinsaKekkaForm();
		
		try {
			PropertyUtils.copyProperties(inputForm, selectInfo);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}

		//------���W�I�{�^���f�[�^�Z�b�g
		//2005.10.26 iso �܂Ƃ߂Ď擾����悤�ύX
		String[] labelNames = {ILabelKubun.KEKKA_ABC, ILabelKubun.KEKKA_TEN, ILabelKubun.KEKKA_TEN_HOGA,ILabelKubun.KENKYUNAIYO,
								ILabelKubun.KENKYUKEIKAKU, ILabelKubun.TEKISETSU_KAIGAI, ILabelKubun.TEKISETSU_KENKYU1,
								ILabelKubun.TEKISETSU, ILabelKubun.DATO, ILabelKubun.SHINSEISHA,
								ILabelKubun.KENKYUBUNTANSHA, ILabelKubun.HITOGENOMU, ILabelKubun.TOKUTEI,
								ILabelKubun.HITOES, ILabelKubun.KUMIKAE, ILabelKubun.CHIRYO,
								ILabelKubun.EKIGAKU,ILabelKubun.RIGAI,ILabelKubun.JUYOSEI,ILabelKubun.DOKUSOSEI,
								ILabelKubun.HAKYUKOKA,ILabelKubun.SUIKONORYOKU,ILabelKubun.JINKEN,
								ILabelKubun.BUNTANKIN};
		HashMap labelMap = (HashMap)LabelValueManager.getLabelMap(labelNames);
		
		//�����]���iABC�j
//		inputForm.setKekkaAbcList(LabelValueManager.getKekkaAbcList());
		inputForm.setKekkaAbcList((List)labelMap.get(ILabelKubun.KEKKA_ABC));
		//�����]���i�_���j
//		inputForm.setKekkaTenList(LabelValueManager.getKekkaTenList());
		inputForm.setKekkaTenList((List)labelMap.get(ILabelKubun.KEKKA_TEN));
		
		//�����]���i�G��j
		inputForm.setKekkaTenHogaList((List)labelMap.get(ILabelKubun.KEKKA_TEN_HOGA));
		
		//�������e
//		inputForm.setKenkyuNaiyoList(LabelValueManager.getKenkyuNaiyoList());
		inputForm.setKenkyuNaiyoList((List)labelMap.get(ILabelKubun.KENKYUNAIYO));
		//�����v��
//		inputForm.setKenkyuKeikakuList(LabelValueManager.getKenkyuKeikakuList());
		inputForm.setKenkyuKeikakuList((List)labelMap.get(ILabelKubun.KENKYUKEIKAKU));
		//�K�ؐ�-�C�O
//		inputForm.setTekisetsuKaigaiList(LabelValueManager.getTekisetsuKaigaiList());
		inputForm.setTekisetsuKaigaiList((List)labelMap.get(ILabelKubun.TEKISETSU_KAIGAI));
		//�K�ؐ�-�����i1�j
//		inputForm.setTekisetsuKenkyu1List(LabelValueManager.getTekisetsuKenkyu1List());
		inputForm.setTekisetsuKenkyu1List((List)labelMap.get(ILabelKubun.TEKISETSU_KENKYU1));
		//�K�ؐ�
//		inputForm.setTekisetsuList(LabelValueManager.getTekisetsuList());
		inputForm.setTekisetsuList((List)labelMap.get(ILabelKubun.TEKISETSU));
		//�Ó���
//		inputForm.setDatoList(LabelValueManager.getDatoList());
		inputForm.setDatoList((List)labelMap.get(ILabelKubun.DATO));
		//������\��
//		inputForm.setShinseishaList(LabelValueManager.getShinseishaList());
		inputForm.setShinseishaList((List)labelMap.get(ILabelKubun.SHINSEISHA));
		//�������S��
//		inputForm.setKenkyuBuntanshaList(LabelValueManager.getKenkyuBuntanshaList());
		inputForm.setKenkyuBuntanshaList((List)labelMap.get(ILabelKubun.KENKYUBUNTANSHA));
		//�q�g�Q�m��
//		inputForm.setHitogenomuList(LabelValueManager.getHitogenomuList());
		inputForm.setHitogenomuList((List)labelMap.get(ILabelKubun.HITOGENOMU));
		//������
//		inputForm.setTokuteiList(LabelValueManager.getTokuteiList());
		inputForm.setTokuteiList((List)labelMap.get(ILabelKubun.TOKUTEI));
		//�q�gES�זE
//		inputForm.setHitoEsList(LabelValueManager.getHitoEsList());
		inputForm.setHitoEsList((List)labelMap.get(ILabelKubun.HITOES));	
		//��`�q�g��������
//		inputForm.setKumikaeList(LabelValueManager.getKumikaeList());
		inputForm.setKumikaeList((List)labelMap.get(ILabelKubun.KUMIKAE));
		//��`�q���×Տ�����
//		inputForm.setChiryoList(LabelValueManager.getChiryoList());
		inputForm.setChiryoList((List)labelMap.get(ILabelKubun.CHIRYO));
		//�u�w����
//		inputForm.setEkigakuList(LabelValueManager.getEkigakuList());
		inputForm.setEkigakuList((List)labelMap.get(ILabelKubun.EKIGAKU));

		//2005.10.26 kainuma
		//���Q�֌W
		inputForm.setRigaiList((List)labelMap.get(ILabelKubun.RIGAI));
		//�w�p�I�d�v���E�Ó���
		inputForm.setJuyoseiList((List)labelMap.get(ILabelKubun.JUYOSEI));
		//�Ƒn���E�v�V��
		inputForm.setDokusoseiList((List)labelMap.get(ILabelKubun.DOKUSOSEI));	
		//�g�y���ʁE���Ր�
		inputForm.setHakyukokaList((List)labelMap.get(ILabelKubun.HAKYUKOKA));
		//���s�\�́E���̓K�ؐ�
		inputForm.setSuikonoryokuList((List)labelMap.get(ILabelKubun.SUIKONORYOKU));
		//�l���̕ی�E�@�ߓ��̏���
		inputForm.setJinkenList((List)labelMap.get(ILabelKubun.JINKEN));
		//���S���z��
		inputForm.setBuntankinList((List)labelMap.get(ILabelKubun.BUNTANKIN));
		

		
		//------�\���Ώۏ����Z�b�V�����ɓo�^�B
		container.setShinsaKekkaInputInfo(selectInfo);

		//------�V�K�o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping, request, inputForm);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//2005.10.14 �m�F��ʂƊ�����ʂ𓝍�
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
			  
		return forwardSuccess(mapping);
	}

}
