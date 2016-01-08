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

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * �`���`�F�b�N�N���X�i���ʌ������i��i��茤��(A)�����j�j
 * ID RCSfile="$RCSfile: ShinseiValidatorShokushinhiWakateA.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:09 $"
 */
public class ShinseiValidatorShokushinhiWakateA extends DefaultShinseiValidator{
	
	/** ����������N�����͂��邩 */
	private int NENSU = 3;
	
	/** �����o��V�X�e��MAX�l */
	private int MAX_KENKYUKEIHI = 9999999;
	
	/** �����o��v�Œ���z(�~) */
	private int MIN_KENKYUKEIHI_GOKEI = 5000;
	
	/** �����o��v�ō����z(�~) */
	private int MAX_KENKYUKEIHI_GOKEI = 30000;
	
	/** �e�N�x�̌����o��(���~) */
	private int MIN_KENKYUKEIHI = 100;

	/**
	 * �R���X�g���N�^
	 * @param shinseiDataInfo
	 * @param page
	 */
	public ShinseiValidatorShokushinhiWakateA(ShinseiDataInfo shinseiDataInfo) {
		super(shinseiDataInfo);
	}

	/**
	 * �`���`�F�b�N�i���ʌ������i��i��茤��(A)�����j�j
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request, int page, ActionErrors errors) {

		//ActionErrors errors = new ActionErrors();

		//�����o��̍��v���Z�o���A�ő�l�`�F�b�N���s��
		validateAndSetKeihiTotal(errors, page);
		
		return errors;
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
// 2�N�ڂ̍��v�́u100�ȏ�v��������
				if(i==1){
					if(MIN_KENKYUKEIHI > intKeihi
//2006/08/22�@�c�@�폜��������                            
							//�V�K�̏ꍇ�������ɒǉ�
//							&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22�@�c�@�폜�����܂�                            
							){	
						ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
						errors.add(property, error);
					}
				}
				//�����o���10���~�ȏ�łȂ���΂Ȃ�Ȃ�(3�N�ڂ��̂���)
				else if(i==2){
					if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
						ActionError error = new ActionError("errors.5032",name + " �O�N�x","0�~","");
						errors.add(property, error);
					}
					else if(MIN_KENKYUKEIHI > intKeihi && intKeihi!=0
//2006/08/22�@�c�@�폜��������                            
							//�V�K�̏ꍇ�������ɒǉ�
//							&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22 �c�@�폜�����܂�                            
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
							//�V�K�̏ꍇ�������ɒǉ�
//							&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
//2006/08/22�@�c�@�폜�����܂�                            
							){	
						ActionError error = new ActionError("errors.5031",name,String.valueOf(MIN_KENKYUKEIHI));
						errors.add(property, error);
					}
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
			//�\���敪���p���̏ꍇ�́A3�N�ڂ͂O�~�łȂ���΂Ȃ�Ȃ�
//2006/08/22�@�c�@�폜��������            
//			if("2".equals(shinseiDataInfo.getShinseiKubun()) && !("0".equals(keihiInfo[2].getKeihi()))){
//				ActionError error = new ActionError("errors.5032","�V�K�E�p���敪","�p��","�����o�� 3�s��");
//				errors.add(property, error);
//			}
//2006/08/22�@�c�@�폜�����܂�            
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
		if(page == 2){
			if(MIN_KENKYUKEIHI_GOKEI > intKeihiTotal
//2006/08/22�@�c�@�폜��������
					//�V�K�̏ꍇ�������ɒǉ�
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