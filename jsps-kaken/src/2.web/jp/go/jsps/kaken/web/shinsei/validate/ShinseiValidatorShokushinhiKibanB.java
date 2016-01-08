/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : syuu
 *    Date        : 2006/02/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */

package jp.go.jsps.kaken.web.shinsei.validate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * �`���`�F�b�N�N���X�i���ʌ������i��i��Ռ���(B)�����j�j
 * ID RCSfile="$RCSfile: ShinseiValidatorShokushinhiKibanB.java,v $"
 * Revision="$Revision: 1.3 $"
 * Date="$Date: 2007/07/13 07:18:05 $"
 */
public class ShinseiValidatorShokushinhiKibanB extends DefaultShinseiValidator {

	/** ����������N�����͂��邩 */
	private int NENSU = 4;

	/** �����o��V�X�e��MAX�l */
	private int MAX_KENKYUKEIHI = 9999999;

	/** �����o��v�Œ���z(�~) */
	private int MIN_KENKYUKEIHI_GOKEI = 5000;

	/** �����o��v�ō����z(�~) */
	private int MAX_KENKYUKEIHI_GOKEI = 20000;

	/** �e�N�x�̌����o��(���~) */
	private int MIN_KENKYUKEIHI = 100;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param shinseiDataInfo
	 * @param page
	 */
	public ShinseiValidatorShokushinhiKibanB(ShinseiDataInfo shinseiDataInfo) {
		super(shinseiDataInfo);
	}

	/**
	 * �`���`�F�b�N�i���ʌ������i��i��Ռ���(B)�����j�j
	 * 
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, int page, ActionErrors errors) {

		// ActionErrors errors = new ActionErrors();

		// �����o��̍��v���Z�o���A�ő�l�`�F�b�N���s��
		validateAndSetKeihiTotal(errors, page);

		// ���͌����g�D�[���������߃`�F�b�N���s��Ȃ�
		// �����g�D�\�̌`���`�F�b�N���s���B
		validateKenkyuSoshiki(errors, page);

		countKenkyushaNinzu(errors, page);

		return errors;
	}

	/**
	 * �����g�D�\���A�S�����Ґ��A���@�ւ̌����Ґ����Z�o����B �܂��A�����o��̍��v���\���f�[�^�̌����o��i1�N�ځj�Ɠ����l�ɂȂ邩���`�F�b�N����B
	 * ����Ă����ꍇ�� ValidationException ���X���[����B
	 * 
	 * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
	 * @param page �y�[�W�ԍ��i�����؃��x���j
	 */
	private void countKenkyushaNinzu(ActionErrors errors, int page) {
		int keihiTotal = 0; // �����g�D�\�̌����o��v
		List kenkyuSoshikiList = shinseiDataInfo.getKenkyuSoshikiInfoList(); // �����g�D�\�̑�\�ҔN���\���f�[�^�ɂ��Z�b�g����

		// ������\�҂̏����@�փR�[�h���擾����
		DaihyouInfo daihyouInfo = shinseiDataInfo.getDaihyouInfo();
		String daihyouKikanCd = daihyouInfo.getShozokuCd();

		// ������\�҂̔N��A���@�ւ̌����Ґ����擾����
		int kenkyuNinzu = kenkyuSoshikiList.size();
		int takikanNinzu = 0;
		int kyoryokushaNinzu = 0;

		for (int i = 0; i < kenkyuNinzu; i++) {
			KenkyuSoshikiKenkyushaInfo kenkyushainfo = (KenkyuSoshikiKenkyushaInfo) kenkyuSoshikiList
					.get(i);

			if (i == 0) { // ��\�ҁi�C���f�b�N�X�l���u0�v�̂��́j
				daihyouInfo.setNenrei(kenkyushainfo.getNenrei()); // ��\�҂̔N���\���f�[�^���ɃZ�b�g����
			} else if ("2".equals(kenkyushainfo.getBuntanFlag())) { // ���S��
				if (daihyouKikanCd != null && // ��\�҂̏����@�փR�[�h�ƈႤ�ꍇ�̓J�E���g�v���X�P
						!daihyouKikanCd.equals(kenkyushainfo.getShozokuCd())) {
					takikanNinzu++;
				}
			} else { // ���͎�
				kyoryokushaNinzu++;
			}
			keihiTotal = keihiTotal
					+ StringUtil.parseInt((String) kenkyushainfo.getKeihi());
		}
		shinseiDataInfo.setKenkyuNinzu(String.valueOf(kenkyuNinzu
				- kyoryokushaNinzu)); // �����Ґ�
		shinseiDataInfo.setTakikanNinzu(String.valueOf(takikanNinzu)); // ���@�ւ̌����Ґ�
		shinseiDataInfo.setKyoryokushaNinzu(String.valueOf(kyoryokushaNinzu)); // �������͎Ґ�

		// �{�o�^
		if (page >= 2) {

			// 20050809 ���S���̗L���ƌ����l�����r ���L�̏ꍇ�ɂ͌����l����2�ȏ�
			// ���������S�҂����݂��Ȃ��ꍇ�Ƀ`�F�b�N�����蔲���Ă���
			if (Integer.parseInt(shinseiDataInfo.getKenkyuNinzu()) == 1
					&& "1".equals(shinseiDataInfo.getBuntankinFlg())) {
				String property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
				ActionError error = new ActionError("errors.5055");
				errors.add(property, error);
//				<!-- ADD�@START 2007/07/09 BIS ���� -->	
				shinseiDataInfo.getErrorsMap().put(property,"���S���̗L��:�u�L�v���u�����g�D�\�v�́u���S�ҁv�̋L��������A���A�u�����o��v�ɓ��͂����邱�Ɓi������΃G��");
				shinseiDataInfo.setErrorsMap( shinseiDataInfo.getErrorsMap());
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			}
			// Horikoshi
			// �����o��̍��v���\���f�[�^��1�N�ڌ����o��Ɠ����ɂȂ��Ă��邩�`�F�b�N����
			if (keihiTotal != StringUtil.parseInt(shinseiDataInfo
					.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[0].getKeihi())) {
				String property = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.kenkyuKeihi";
				ActionError error = new ActionError("errors.5017");
				errors.add(property, error);
//				<!-- ADD�@START 2007/07/09 BIS ���� -->	
				shinseiDataInfo.getErrorsMap().put(property,"�����o��(1�N��)�������g�D�\�̌����o��v�ƈႢ�܂��B ");
				shinseiDataInfo.setErrorsMap( shinseiDataInfo.getErrorsMap());
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			}
		}
	}

	/**
	 * �����o��̑��v���Z�o���Đ\�����ɃZ�b�g����B
	 * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
	 * @param page   �y�[�W�ԍ��i�����؃��x���j
	 */
	private void validateAndSetKeihiTotal(ActionErrors errors, int page)
	{
		
		//�G���[���b�Z�[�W
		String name         = null;												//���ږ�
		String value        = String.valueOf(MAX_KENKYUKEIHI);						//�l
		String errKey       = "errors.maxValue";									//�L�[������
		String property     = "shinseidataInfo.kenkyuKeihiSoukeiInfo.keihiTotal";	//�v���p�e�B��

		
		KenkyuKeihiSoukeiInfo soukeiInfo = shinseiDataInfo.getKenkyuKeihiSoukeiInfo();
		KenkyuKeihiInfo[]     keihiInfo  = soukeiInfo.getKenkyuKeihi();
		
		//�����o��̑��v���Z�o����
		int intKeihiTotal          = 0;
		int intBihinhiTotal        = 0;
		int intShomohinhiTotal     = 0;
		int intKokunairyohiTotal   = 0;
		int intGaikokuryohiTotal   = 0;
		int intRyohiTotal          = 0;
		int intShakinTotal         = 0;
		int intSonotaTotal         = 0;
		
		//�N�x���ƂɌJ��Ԃ�
		for(int i=0; i<NENSU; i++){
			//�P�N�x
			int intBihinhi        = StringUtil.parseInt(keihiInfo[i].getBihinhi());
			int intShomohinhi     = StringUtil.parseInt(keihiInfo[i].getShomohinhi());
			int intKokunairyohi   = StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
			int intGaikokuryohi   = StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
			int intRyohi          = StringUtil.parseInt(keihiInfo[i].getRyohi());
			int intShakin         = StringUtil.parseInt(keihiInfo[i].getShakin());
			int intSonota         = StringUtil.parseInt(keihiInfo[i].getSonota());
			//�P�N�x���v
			int intKeihi          = intBihinhi
								   + intShomohinhi
								   + intKokunairyohi
								   + intGaikokuryohi
								   + intRyohi
								   + intShakin
								   + intSonota
								   ;
			//�P�N�x���v���I�u�W�F�N�g�ɃZ�b�g����
			keihiInfo[i].setKeihi(String.valueOf(intKeihi)); 

			int rowIndex = i+1;
			name = "�����o�� " + rowIndex + "�s��";
			//�P�N�x���v�̌`���`�F�b�N
			if(MAX_KENKYUKEIHI < intKeihi){
				ActionError error = new ActionError(errKey,name,value);
				errors.add(property, error);
			}
			
			//�`�F�b�N�͈ꎞ�ۑ����ɂ͍s��Ȃ�
			if(page >= 2){
				//�{�o�^�ł̂݃`�F�b�N����
				if(page >= 2){
					//�����o���10���~�ȏ�łȂ���΂Ȃ�Ȃ�(3�N�ڂ�4�N�ڂ��̂���)
					if(i==2 || i==3){
						//�V�K�E�p���敪���V�K�̏ꍇ�݂̂ɏ�����ǉ�
//						if(MIN_KENKYUKEIHI > intKeihi && intKeihi!=0){	
						if(MIN_KENKYUKEIHI > intKeihi && intKeihi!=0
//2006/08/22�@�c�@�폜��������                                
//								&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22�@�c�@�폜�����܂�                                
								){
							ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
							errors.add(property, error);
						}
					//1�N�ڂ͐V�K�Ɠ������[��
					}else if(i == 0){
						if(MIN_KENKYUKEIHI > intKeihi){	
							ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
							errors.add(property, error);
						}

					}else{
						if(MIN_KENKYUKEIHI > intKeihi
//2006/08/22�@�c�@�폜��������                                  
//								&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22�@�c�@�폜�����܂�                                 
								){	
							ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
							errors.add(property, error);
						}
					}

//					//��N�ڈȍ~�őO�N�x�̒l���u0�v�łȂ����Ƃ��`�F�b�N
//					if(i>0
/*					&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())	*/
//						){
//						int lastKeihi = StringUtil.parseInt(keihiInfo[i-1].getKeihi());
//						if(		lastKeihi == 0		//�O�N�x�̌����o�0
//							&&	intKeihi != 0		//�����o�0�ȊO
//							){
//							ActionError error = new ActionError(
//									"errors.5032",
//									"�����o�� " + String.valueOf(i) + "�s��",
//									"0�~",
//									"�����o�� " + String.valueOf(i+1) + "�s��"
//									);
//							errors.add(property, error);
//						}
//					}
				}
			}

			//���v
			intKeihiTotal          = intKeihiTotal        + intKeihi;
			intBihinhiTotal        = intBihinhiTotal      + StringUtil.parseInt(keihiInfo[i].getBihinhi());
			intShomohinhiTotal     = intShomohinhiTotal   + StringUtil.parseInt(keihiInfo[i].getShomohinhi());
			intKokunairyohiTotal   = intKokunairyohiTotal + StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
			intGaikokuryohiTotal   = intGaikokuryohiTotal + StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
			intRyohiTotal          = intRyohiTotal        + StringUtil.parseInt(keihiInfo[i].getRyohi());
			intShakinTotal         = intShakinTotal       + StringUtil.parseInt(keihiInfo[i].getShakin());
			intSonotaTotal         = intSonotaTotal       + StringUtil.parseInt(keihiInfo[i].getSonota());
		}
		
		//�`�F�b�N�͈ꎞ�ۑ����ɂ͍s��Ȃ�
		if(page >= 2){
			//�{�o�^�ł̂݃`�F�b�N����
			if(page >= 2){
				//-----�����o��̑g�ݍ��킹�`�F�b�N-----
				//3�N�ڂ̌����o��O�~�̏ꍇ�A4�N�ڂ��O�~�łȂ���΂Ȃ�Ȃ�
				if("0".equals(keihiInfo[2].getKeihi()) && !("0".equals(keihiInfo[3].getKeihi()))){
					ActionError error = new ActionError("errors.5032","�����o�� 3�s��","0�~","�����o�� 4�s��");
					errors.add(property, error);
				}
//2006/08/22�@�c�@�폜��������                
				//�\���敪���p���̏ꍇ�́A4�N�ڂ͂O�~�łȂ���΂Ȃ�Ȃ�
//				if("2".equals(shinseiDataInfo.getShinseiKubun()) && !("0".equals(keihiInfo[3].getKeihi()))){
//					ActionError error = new ActionError("errors.5032","�V�K�E�p���敪","�p��","�����o�� 4�s��");
//					errors.add(property, error);
//				}
//2006/08/22�@�c�@�폜�����܂�                
			}
		}
		
		//���v���Z�b�g����
		soukeiInfo.setKeihiTotal(String.valueOf(intKeihiTotal));
		soukeiInfo.setBihinhiTotal(String.valueOf(intBihinhiTotal));
		soukeiInfo.setShomohinhiTotal(String.valueOf(intShomohinhiTotal));
		soukeiInfo.setKokunairyohiTotal(String.valueOf(intKokunairyohiTotal));
		soukeiInfo.setGaikokuryohiTotal(String.valueOf(intGaikokuryohiTotal));
		soukeiInfo.setRyohiTotal(String.valueOf(intRyohiTotal));
		soukeiInfo.setShakinTotal(String.valueOf(intShakinTotal));
		soukeiInfo.setSonotaTotal(String.valueOf(intSonotaTotal));
		
		
		//-----���v�̌`���`�F�b�N-----
		
		//�͈͂̃`�F�b�N�͈ꎞ�ۑ����ɂ͍s��Ȃ�
		if(page >= 2){
			if(MIN_KENKYUKEIHI_GOKEI > intKeihiTotal
//2006/08/22�@�c�@�폜��������                      
					//�V�K�̏ꍇ��ǉ�
//					&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22�@�c�@�폜�����܂�                    
					){
				name = "�����o�� ���v";
				ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI_GOKEI));
				errors.add(property, error);
			}
			if(MAX_KENKYUKEIHI_GOKEI < intKeihiTotal){
				name = "�����o�� ���v";
				ActionError error = new ActionError("errors.5033",name,String.valueOf(MAX_KENKYUKEIHI_GOKEI));
				errors.add(property, error);
			}
		}
		
		//�V�X�e������̃`�F�b�N
		if(MAX_KENKYUKEIHI < intKeihiTotal){
			name = "�����o�� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intBihinhiTotal){
			name = "�ݔ����i�� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intShomohinhiTotal){
			name = "���Օi�� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intKokunairyohiTotal){
			name = "�������� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intGaikokuryohiTotal){
			name = "�O������ ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intRyohiTotal){
			name = "���� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(MAX_KENKYUKEIHI < intShakinTotal){
			name = "�Ӌ��� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}		
		if(MAX_KENKYUKEIHI < intSonotaTotal){
			name = "���̑� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}		
	}
}