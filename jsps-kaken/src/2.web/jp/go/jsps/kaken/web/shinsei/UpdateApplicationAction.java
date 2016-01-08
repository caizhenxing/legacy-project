/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.model.vo.shinsei.KadaiInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * �\�������͑O�A�N�V�����N���X�B
 * �t�H�[���ɐ\�������͘^��ʂɕK�v�ȃf�[�^���Z�b�g����B
 * �\�������͉�ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: UpdateApplicationAction.java,v $"
 * Revision="$Revision: 1.8 $"
 * Date="$Date: 2007/07/26 08:18:43 $"
 */
public class UpdateApplicationAction extends BaseAction {

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

		//-----�\�������̓t�H�[���̎擾
		ShinseiForm shinseiForm = getUpdateForm(container, (ShinseiForm)form);
		
//2006/02/08 Start �N�x���X�g��ǉ�����
		//-----��ʂ̔N�x���X�g��ݒ肷��
		String nendo = shinseiForm.getShinseiDataInfo().getNendo();
		String pre1Nendo = Integer.toString(Integer.parseInt(nendo) - 1);
		String pre2Nendo = Integer.toString(Integer.parseInt(nendo) - 2);
		String pre3Nendo = Integer.toString(Integer.parseInt(nendo) - 3);
		
		//���i�擾�N�x���X�g��ݒ肷��
		List sikakuDateList = new ArrayList();
		sikakuDateList.add(new LabelValueBean(pre1Nendo, pre1Nendo));
		sikakuDateList.add(new LabelValueBean(nendo, nendo));
		shinseiForm.setSikakuDateList(sikakuDateList);
		
        //��x���J�n�N�x���X�g��ݒ肷��
		List ikukyuStartDateList = new ArrayList();
		ikukyuStartDateList.add(new LabelValueBean(pre3Nendo, pre3Nendo));
		ikukyuStartDateList.add(new LabelValueBean(pre2Nendo, pre2Nendo));
		ikukyuStartDateList.add(new LabelValueBean(pre1Nendo, pre1Nendo));
		shinseiForm.setIkukyuStartDateList(ikukyuStartDateList);
		
		DateUtil dateUtil = new DateUtil();
		String jigyoKubun = shinseiForm.getShinseiDataInfo().getKadaiInfo().getJigyoKubun();
		String ouboShikaku = shinseiForm.getShinseiDataInfo().getOuboShikaku();
		if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)){
			if ( ouboShikaku == null ||!("1".equals(ouboShikaku) || "2".equals(ouboShikaku) || "3"
					.equals(ouboShikaku))){
				ActionError error = new ActionError("errors.9021");
				errors.add(null,error);
			}else{
				if(ouboShikaku.equals("1")||ouboShikaku.equals("2")){
					if(shinseiForm.getShinseiDataInfo().getSikakuDate() != null){
						String sikakuDate = shinseiForm.getShinseiDataInfo().getSikakuDate().toString();
						dateUtil.setCal(sikakuDate.substring(0,4),"4","1");
						shinseiForm.setSikakuDateYear(dateUtil.getNendo());
						shinseiForm.setSikakuDateMonth(Integer.toString(Integer.parseInt(sikakuDate.substring(5,7))));
						shinseiForm.setSikakuDateDay(Integer.toString(Integer.parseInt(sikakuDate.substring(8,10))));
					}
				}else if(ouboShikaku.equals("3")){
					if(shinseiForm.getShinseiDataInfo().getIkukyuStartDate() != null){
						String ikukyuStartDate = shinseiForm.getShinseiDataInfo().getIkukyuStartDate().toString();
						dateUtil.setCal(ikukyuStartDate.substring(0,4),"4","1");
						shinseiForm.setIkukyuStartDateYear(dateUtil.getNendo());
						shinseiForm.setIkukyuStartDateMonth(Integer.toString(Integer.parseInt(ikukyuStartDate.substring(5,7))));
						shinseiForm.setIkukyuStartDateDay(Integer.toString(Integer.parseInt(ikukyuStartDate.substring(8,10))));
					}
					if(shinseiForm.getShinseiDataInfo().getIkukyuEndDate() != null){
						String ikukyuEndDate = shinseiForm.getShinseiDataInfo().getIkukyuEndDate().toString();
						dateUtil.setCal(ikukyuEndDate.substring(0,4),"4","1");
						shinseiForm.setIkukyuEndDateYear(dateUtil.getNendo());
						shinseiForm.setIkukyuEndDateMonth(Integer.toString(Integer.parseInt(ikukyuEndDate.substring(5,7))));
						shinseiForm.setIkukyuEndDateDay(Integer.toString(Integer.parseInt(ikukyuEndDate.substring(8,10))));
					}
				}
			}
		}
		if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
			if(shinseiForm.getShinseiDataInfo().getSaiyoDate() != null){
				String saiyoDate = shinseiForm.getShinseiDataInfo().getSaiyoDate().toString();
				dateUtil.setCal(saiyoDate.substring(0,4),"4","1");
				shinseiForm.setSaiyoDateYear(dateUtil.getNendo());
				shinseiForm.setSaiyoDateMonth(Integer.toString(Integer.parseInt(saiyoDate.substring(5,7))));
				shinseiForm.setSaiyoDateDay(Integer.toString(Integer.parseInt(saiyoDate.substring(8,10))));
			}
		}
// Nae End
		
		//-----�\�������̓t�H�[���Œ�̃v���_�E�����X�g���Z�b�g
		//�V�K�E�p�����X�g
		shinseiForm.setShinkiKeibetuList(LabelValueManager.getSinkiKeizokuFlgList());
		//����CD���擾
		String jigyoCd = shinseiForm.getShinseiDataInfo().getJigyoCd();
		//�����v��ŏI�N�x�O�N�x�̉��僊�X�g
		if (jigyoCd.equals(IJigyoCd.JIGYO_CD_TOKUSUI)) {
			//���ʐ��i����
			shinseiForm.setZennendoList(LabelValueManager.getZennendoOboListTokusui());
		}else{
			//���ʐ��i���ƈȊO
			shinseiForm.setZennendoList(LabelValueManager.getZennendoOboList(false));
		}
		//���S���̗L�����X�g
		shinseiForm.setBuntankinList(LabelValueManager.getBuntankinList());

		//2005/03/31 �ǉ� -----------------------------------------------��������
		//���R �J����]�̗L���ǉ��̂���
		shinseiForm.setKaijiKiboList(LabelValueManager.getKaijiKiboList());
		//2005/03/31 �ǉ� -----------------------------------------------�����܂�
				
		//2005/04/08 �ǉ� ��������----------------------------
		//���R�F�����Ώۂ̗ތ^�ǉ��̂���
		shinseiForm.setKenkyuTaishoList(LabelValueManager.getKenkyuTaishoList());
		//�ǉ��@�����܂�--------------------------------------
		
		//2005/04/18 �ǉ� ��������----------------------------
		//���R�F�R����]����ǉ��̂���
		shinseiForm.setShinsaKiboBunyaList(LabelValueManager.getKaigaiBunyaList());
		//�ǉ��@�����܂�--------------------------------------
		
//		2005/04/13 �ǉ� ��������----------
//		���R:�����ԍ���"-"���\������Ă��܂�����
		//�����ԍ������͂���Ȃ������ꍇ�́A"-"��DB�ɓo�^����Ă���̂ŋ󔒂ɖ߂�
		KadaiInfo kadaiInfo = shinseiForm.getShinseiDataInfo().getKadaiInfo();
		if("-".equals(kadaiInfo.getBunkatsuNo())){
			kadaiInfo.setBunkatsuNo("");
		}
//		2005/04/13 �ǉ� �����܂�----------

// 20050530 Start ����̈�(�����敪)�̒ǉ�
		shinseiForm.setKenkyuKubunList(LabelValueManager.getKenkyuKubunList());
// Horikoshi End

// 2007/02/08�@�c�@�폜��������    �R����]����̓��x���}�X�^�Ɏ擾��ύX����         
////		2006/02/15
//        //�R�����얼
//		shinseiForm.setShinsaKiboRyoikiList(LabelValueManager.getKiboRyoikiList());
//		//syuu End
// 2007/02/08 �c�@�폜�����܂�
        
//2007/02/08 �c�@�ǉ���������
        //�R����]����̖���ݒ�
        String shinsaRyoikiName = LabelValueManager.getlabelName(
                                            shinseiForm.getShinsaKiboRyoikiList(), 
                                            shinseiForm.getShinseiDataInfo().getShinsaRyoikiCd());
        shinseiForm.getShinseiDataInfo().setShinsaRyoikiName(shinsaRyoikiName);
//2007/02/08�@�c�@�ǉ������܂�      

		//-----�\�������̓t�H�[���ɃZ�b�g����B
		updateFormBean(mapping, request, shinseiForm);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
				
		return forwardSuccess(mapping);
		
	}
	
	

	/**
	 * �\�����C�����͗p�t�H�[���擾���\�b�h�B
	 * @param container �\���ҏ��
	 * @param form�@�\�������̓t�H�[��
	 * @return �C���p�\�������̓t�H�[��
	 * @throws ApplicationException
	 */
	private ShinseiForm getUpdateForm(UserContainer container, ShinseiForm form)
		throws ApplicationException
	{
		//�V�X�e����tNo���擾����
		String systemNo = form.getShinseiDataInfo().getSystemNo();
		
		//���ƊǗ���L�[�I�u�W�F�N�g�̐���
		ShinseiDataPk shinseiDataPk = new ShinseiDataPk(systemNo);
		
		//DB��背�R�[�h���擾
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			Map resultMap = servise.selectShinseiDataForInput(container.getUserInfo(),shinseiDataPk);
		
		//�\�����A�e�v���_�E�����j���[���X�g�擾
		ShinseiDataInfo shinseiDataInfo = (ShinseiDataInfo)resultMap.get(ISystemServise.KEY_SHINSEIDATA_INFO);
		List            keiKubunList    = (List)resultMap.get(ISystemServise.KEY_KEI_KUBUN_LIST);
		List            suisenList      = (List)resultMap.get(ISystemServise.KEY_SUISEN_LIST);
		List            shokushuList    = (List)resultMap.get(ISystemServise.KEY_SHOKUSHU_LIST);
// 20050527 Start ����̈�p�̃v���_�E�����j���[���X�g��ǉ�
		List            ryouikiList		= (List)resultMap.get(ISystemServise.KEY_RYOUIKI_LIST);
// Horikoshi End
// 2007/02/08 �c�@�ǉ���������
        List            kiboubumonList  = (List)resultMap.get(ISystemServise.KEY_KIBOUBUMON_WAKA_LIST);
//�@2007/02/08�@�c�@�ǉ������܂�
		
// 2006/02/08 Start �\�����i�t���O��ǉ�
		shinseiDataInfo.setOuboShikaku(container.getUserInfo().getShinseishaInfo().getOuboshikaku());
// Nae End

		//�\�����̓t�H�[���̐���
		ShinseiForm inputForm = new ShinseiForm();
		inputForm.setShinseiDataInfo(shinseiDataInfo);
		inputForm.setKeitouList(keiKubunList);
		inputForm.setSuisenList(suisenList);
		inputForm.setShokushuList(shokushuList);
// 20050527 Start
		inputForm.setKenkyuKubunList(ryouikiList);
// Horikoshi End
		inputForm.setYoshikiShubetu(shinseiDataInfo.getJigyoCd().substring(2,4));
//2007/02/08 �c�@�ǉ���������
        inputForm.setShinsaKiboRyoikiList(kiboubumonList);
//2007/02/08�@�c�@�ǉ������܂�

		return inputForm;
		
	}
}
