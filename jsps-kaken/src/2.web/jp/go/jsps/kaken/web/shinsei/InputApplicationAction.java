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
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;
import jp.go.jsps.kaken.web.common.LabelValueManager;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * �\�������͑O�A�N�V�����N���X�B
 * �t�H�[���ɐ\�������͘^��ʂɕK�v�ȃf�[�^���Z�b�g����B
 * �\�������͉�ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: InputApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:01 $"
 */
public class InputApplicationAction extends BaseAction {

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
		ShinseiForm shinseiForm = getInputNewForm(container, (ShinseiForm)form);
		
// 2006/02/08 Start �N�x���X�g��ǉ�����
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
		//�v�挤���E���匤���E�I�������̈�敪���X�g
		shinseiForm.setKenkyuKubunList(LabelValueManager.getKenkyuKubunList());

// 2006/02/15	�ǉ� -----------------------------------------------��������	
		shinseiForm.getShinseiDataInfo().setNaiyakugaku("0");
// syuu         �ǉ� -----------------------------------------------�����܂�
		//2005/03/28 �ǉ� -----------------------------------------------��������
		//���R �J����]�̗L���ǉ��̂���
		shinseiForm.setKaijiKiboList(LabelValueManager.getKaijiKiboList());
		//2005/03/28 �ǉ� -----------------------------------------------�����܂�

		//2005/04/08 �ǉ� ��������----------------------------
		//���R�F�����Ώۂ̗ތ^�ǉ��̂���
		shinseiForm.setKenkyuTaishoList(LabelValueManager.getKenkyuTaishoList());
		//�ǉ��@�����܂�--------------------------------------

		//2005/04/18 �ǉ� ��������----------------------------       
		//���R�F�R����]����ǉ��̂���
		shinseiForm.setShinsaKiboBunyaList(LabelValueManager.getKaigaiBunyaList());
		//�ǉ��@�����܂�--------------------------------------
//2007/02/08�@�c�@�폜��������    �R����]����̓��x���}�X�^�Ɏ擾��ύX����        
//		//2006/02/15
//        //�R�����얼
//		shinseiForm.setShinsaKiboRyoikiList(LabelValueManager.getKiboRyoikiList());
//		//syuu End
//2007/02/08�@�c�@�폜�����܂�        
		//2005/04/18 �ǉ� ��������----------------------------
		//���R�F��p��0���ߒǉ�
		KenkyuKeihiSoukeiInfo soukeiInfo = shinseiForm.getShinseiDataInfo().getKenkyuKeihiSoukeiInfo();
		KenkyuKeihiInfo[] keihiList = soukeiInfo.getKenkyuKeihi();
//2006/06/14 �ǉ���������        
//		for(int nensu=0;nensu < 5;nensu++){
        for(int nensu=0;nensu < IShinseiMaintenance.NENSU;nensu++){    
//�c�@�ǉ������܂�            
			keihiList[nensu].setKeihi("0");
			keihiList[nensu].setBihinhi("0");
			keihiList[nensu].setShomohinhi("0");
			keihiList[nensu].setKokunairyohi("0");
			keihiList[nensu].setGaikokuryohi("0");
			keihiList[nensu].setRyohi("0");
			keihiList[nensu].setShakin("0");
			keihiList[nensu].setSonota("0");
		}
//2006/07/03 �c�@�ǉ���������
        KenkyuKeihiInfo[] keihiList6 = soukeiInfo.getKenkyuKeihi6();
        for(int nensu=0;nensu < IShinseiMaintenance.NENSU_TOKUTEI_SINNKI;nensu++){               
            keihiList6[nensu].setKeihi("0");
            keihiList6[nensu].setBihinhi("0");
            keihiList6[nensu].setShomohinhi("0");
            keihiList6[nensu].setKokunairyohi("0");
            keihiList6[nensu].setGaikokuryohi("0");
            keihiList6[nensu].setRyohi("0");
            keihiList6[nensu].setShakin("0");
            keihiList6[nensu].setSonota("0");
        }
//2006/07/03�@�c�@�ǉ������܂�        
		soukeiInfo.setKeihiTotal("0");
		soukeiInfo.setBihinhiTotal("0");
		soukeiInfo.setShomohinhiTotal("0");
		soukeiInfo.setKokunairyohiTotal("0");
		soukeiInfo.setGaikokuryohiTotal("0");
		soukeiInfo.setRyohiTotal("0");
		soukeiInfo.setShakinTotal("0");
		soukeiInfo.setSonotaTotal("0");
// 20050803 ���ڒǉ�
		soukeiInfo.setMeetingCost("0");		//��c��
		soukeiInfo.setPrintingCost("0");	//�����
// Horikoshi
		//�ǉ��@�����܂�--------------------------------------
		
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
	 * �\�����V�K���͗p�t�H�[���擾���\�b�h�B
	 * @param container �\���ҏ��
	 * @param form �\�������̓t�H�[��
	 * @return �V�K�p�\�������̓t�H�[��
	 * @throws ApplicationException
	 */
	private ShinseiForm getInputNewForm(UserContainer container, ShinseiForm form)
		throws ApplicationException
	{
		//����ID���擾����
		String jigyoId = form.getShinseiDataInfo().getJigyoId();
		
		//���ƊǗ���L�[�I�u�W�F�N�g�̐���
		JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(jigyoId);
		
		//DB��背�R�[�h���擾
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			Map resultMap = servise.selectShinseiDataForInput(container.getUserInfo(),jigyoKanriPk);
		
		//�\�����A�e�v���_�E�����j���[���X�g�擾
		ShinseiDataInfo shinseiDataInfo = (ShinseiDataInfo)resultMap.get(ISystemServise.KEY_SHINSEIDATA_INFO);
		List            keiKubunList    = (List)resultMap.get(ISystemServise.KEY_KEI_KUBUN_LIST);
		List            suisenList      = (List)resultMap.get(ISystemServise.KEY_SUISEN_LIST);
		List            shokushuList    = (List)resultMap.get(ISystemServise.KEY_SHOKUSHU_LIST);

// 20050526 Start �v�挤���E���匤���E�I�������̈�敪
		List			ryouikiList		= (List)resultMap.get(ISystemServise.KEY_RYOUIKI_LIST);
// Horikoshi End
		
// 2007/02/08 �c�@�ǉ���������
        List            kiboubumonList  = (List)resultMap.get(ISystemServise.KEY_KIBOUBUMON_WAKA_LIST);
//�@2007/02/08�@�c�@�ǉ������܂�        
// 2006/02/08 Start �\�����i�t���O��ǉ�
		shinseiDataInfo.setOuboShikaku(container.getUserInfo().getShinseishaInfo().getOuboshikaku());
// Nae End
		
//		�\�����̓t�H�[���̐���
		ShinseiForm inputForm = new ShinseiForm();
		inputForm.setShinseiDataInfo(shinseiDataInfo);
		inputForm.setKeitouList(keiKubunList);
		inputForm.setSuisenList(suisenList);
		inputForm.setShokushuList(shokushuList);
        
//2007/02/08 �c�@�ǉ���������
        inputForm.setShinsaKiboRyoikiList(kiboubumonList);
//2007/02/08�@�c�@�ǉ������܂�        

// 20050527 Start
		inputForm.setKenkyuKubunList(ryouikiList);
// Horikoshi End

		inputForm.setYoshikiShubetu(shinseiDataInfo.getJigyoCd().substring(2,4));

		return inputForm;
		
	}
}
