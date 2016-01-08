/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : syuu
 *    Date        : 2006/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.validate;

import javax.servlet.http.HttpServletRequest;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * �`���`�F�b�N�i��茤��(�X�^�[�g�A�b�v)�j
 * ID RCSfile="$RCSfile: ShinseiValidatorWakatestart.java,v $"
 * Revision="$Revision: 1.3 $"
 * Date="$Date: 2007/07/11 06:51:41 $"
 */
public class ShinseiValidatorWakatestart extends DefaultShinseiValidator {

	/** ����������N�����͂��邩 */
	private int NENSU = 2;

	/** �����o��V�X�e��MAX�l */
	private int MAX_KENKYUKEIHI = 1500;

	/** �����o��v�ō����z(�~) */
	private int MAX_KENKYUKEIHI_GOKEI = 9999999;

	/** �e�N�x�̌����o��(���~) */
	private int MIN_KENKYUKEIHI = 100;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param shinseiDataInfo
	 * @param page
	 */
	public ShinseiValidatorWakatestart(ShinseiDataInfo shinseiDataInfo) {
		super(shinseiDataInfo);
	}

	/**
	 *�`���`�F�b�N�i��茤��(�X�^�[�g�A�b�v)�j
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, int page, ActionErrors errors) {

		// ��^����-----
		// ActionErrors errors = new ActionErrors();

		// �����o��̍��v���Z�o���A�ő�l�`�F�b�N���s��
		validateAndSetKeihiTotal(errors, page);

		//���ʌ�������������z
		validateAndSetNaiyakugaku(errors, page);
		
		validateAndKinmuHour(errors,page);

//2007/03/20 �����F�@�C���@��������
        if (page >= 2) {
            //2007/02/25 �����F�@�ǉ��@��������  
            validateAndShoreihiNoNendo(errors, page);
            //2007/02/25 �����F�@�ǉ��@�����܂�
        }
//2007/03/20 �����F�@�C���@�����܂�
        return errors;
	}
	
	/**
	 * �Ζ����Ԑ����Đ\�����ɃZ�b�g����B
	 * 
	 * @param errors
	 *            ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
	 * @param page
	 *            �y�[�W�ԍ��i�����؃��x���j
	 */
	private void validateAndKinmuHour(ActionErrors errors, int page) {
		String name = "�Ζ����Ԑ�";// ���ږ�
		String property = "shinseiDataInfo.kinmuHour"; // �v���p�e�B��
		int valueAnd = StringUtil.parseInt(shinseiDataInfo.getKinmuHour());
		int MINKINMUHOUR = 31;
		int MAXKINMUHOUR = 99;
		if(page >= 2){
			if (shinseiDataInfo.getKinmuHour().length() != 0
					&& shinseiDataInfo.getKinmuHour() != null) {
				//2006/4/11 �Ζ����Ԃ�100�ȏ����͂��ꂽ�ꍇ�A�����̃`�F�b�N������̂�
				//�d���G���[��\�����Ȃ��悤�ɏC�������B
				//if (valueAnd > MAXKINMUHOUR || valueAnd < MINKINMUHOUR) {
				if (valueAnd < MINKINMUHOUR) {
					ActionError error = new ActionError("errors.9024", name, String
							.valueOf(MINKINMUHOUR), String.valueOf(MAXKINMUHOUR));
					errors.add(property, error);

				}
			}
		}	
	}

	/**
	 * ���ʌ�������������z���Đ\�����ɃZ�b�g����B
	 * 
	 * @param errors
	 *            ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
	 * @param page
	 *            �y�[�W�ԍ��i�����؃��x���j
	 */
	private void validateAndSetNaiyakugaku(ActionErrors errors, int page) {
		String name = "���ʌ�������������z";// ���ږ�
		String property = "shinseiDataInfo.naiyakugaku"; // �v���p�e�B��
		int valueAnd = StringUtil.parseInt(shinseiDataInfo.getNaiyakugaku());
		int MINNAIYAKUGAKU = 100;
		int MAXNAIYAKUGAKU = 3000;
		if(page >= 2){
			if (valueAnd != 0 ) {
				if (valueAnd > MAXNAIYAKUGAKU || valueAnd < MINNAIYAKUGAKU) {
					ActionError error = new ActionError("errors.9023", name, String
							.valueOf(MINNAIYAKUGAKU), String.valueOf(MAXNAIYAKUGAKU));
					errors.add(property, error);

				}
			}
		}
	}
	/**
	 * �����o��̑��v���Z�o���Đ\�����ɃZ�b�g����B
	 * 
	 * @param errors
	 *            ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
	 * @param page
	 *            �y�[�W�ԍ��i�����؃��x���j
	 */
	private void validateAndSetKeihiTotal(ActionErrors errors, int page) {

		//�G���[���b�Z�[�W
		String name = null; //���ږ�
		String value = String.valueOf(MAX_KENKYUKEIHI); //�l
		String value1 = String.valueOf(MAX_KENKYUKEIHI_GOKEI);//�l
		String errKey = "errors.maxValue"; //�L�[������
		String property = "shinseidataInfo.kenkyuKeihiSoukeiInfo.keihiTotal"; //�v���p�e�B��
//ADD�@START 2007-07-11 BIS ������
		//�s�ڃv���p�e�B
		String propertyRow = null;
//ADD�@END 2007-07-11 BIS ������

		KenkyuKeihiSoukeiInfo soukeiInfo = shinseiDataInfo
				.getKenkyuKeihiSoukeiInfo();
		KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi();

		//�����o��̑��v���Z�o����
		int intKeihiTotal = 0;
		int intBihinhiTotal = 0;
		int intShomohinhiTotal = 0;
		int intKokunairyohiTotal = 0;
		int intGaikokuryohiTotal = 0;
		int intRyohiTotal = 0;
		int intShakinTotal = 0;
		int intSonotaTotal = 0;

		//�N�x���ƂɌJ��Ԃ�
		for (int i = 0; i < NENSU; i++) {
			//�P�N�x
			int intBihinhi = StringUtil.parseInt(keihiInfo[i].getBihinhi());
			int intShomohinhi = StringUtil.parseInt(keihiInfo[i].getShomohinhi());
			int intKokunairyohi = StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
			int intGaikokuryohi = StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
			int intRyohi = StringUtil.parseInt(keihiInfo[i].getRyohi());
			int intShakin = StringUtil.parseInt(keihiInfo[i].getShakin());
			int intSonota = StringUtil.parseInt(keihiInfo[i].getSonota());
			//�P�N�x���v
			int intKeihi = intBihinhi + intShomohinhi + intKokunairyohi
					+ intGaikokuryohi + intRyohi + intShakin + intSonota;
			//�P�N�x���v���I�u�W�F�N�g�ɃZ�b�g����
			keihiInfo[i].setKeihi(String.valueOf(intKeihi));

			int rowIndex = i + 1;
//ADD�@START 2007-07-11 BIS ������
			//�s�ڂ�ݒ肷��
			propertyRow = property + (rowIndex - 1);
//ADD�@END 2007-07-11 BIS ������
			name = "�����o�� " + rowIndex + "�s��";
//2007/03/26 �����F�@�X�V�@��������
			//�P�N�x���v�̌`���`�F�b�N
            if (MAX_KENKYUKEIHI_GOKEI < intKeihi) {
                ActionError error = new ActionError(errKey, name, value1);
                errors.add(property, error);
//ADD�@START 2007-07-11 BIS ������
				errors.add(propertyRow, error);
//ADD�@END 2007-07-11 BIS ������
            }
			
			//�`�F�b�N�͈ꎞ�ۑ����ɂ͍s��Ȃ�
			if (page >= 2) {
				//�P�N�x���v�̍ő�l�`�F�b�N(150���~�ȉ�)
				if (MAX_KENKYUKEIHI  <  intKeihi) {					
					ActionError error = new ActionError("errors.5033", name, value);
					errors.add(property, error);
//ADD�@START 2007-07-11 BIS ������
					errors.add(propertyRow, error);
//ADD�@END 2007-07-11 BIS ������
				}
//2007/03/26 �����F�@�X�V�@�����܂�

				//�����o���10���~�ȏ�łȂ���΂Ȃ�Ȃ�(1,2�N�ڂ��̂���)
				if (i == 0 || i == 1) {
					if (MIN_KENKYUKEIHI > intKeihi
							//�V�K�̏ꍇ�������ɒǉ�
							&& IShinseiMaintenance.SHINSEI_NEW
									.equals(shinseiDataInfo.getShinseiKubun())) {
						ActionError error = new ActionError("errors.5031",
								name, String.valueOf(MIN_KENKYUKEIHI));
						errors.add(property, error);
//ADD�@START 2007-07-11 BIS ������
						errors.add(propertyRow, error);
//ADD�@END 2007-07-11 BIS ������
					}

				} 
			}

			//���v
			intKeihiTotal = intKeihiTotal + intKeihi;
			intBihinhiTotal = intBihinhiTotal
					+ StringUtil.parseInt(keihiInfo[i].getBihinhi());
			intShomohinhiTotal = intShomohinhiTotal
					+ StringUtil.parseInt(keihiInfo[i].getShomohinhi());
			intKokunairyohiTotal = intKokunairyohiTotal
					+ StringUtil.parseInt(keihiInfo[i].getKokunairyohi());
			intGaikokuryohiTotal = intGaikokuryohiTotal
					+ StringUtil.parseInt(keihiInfo[i].getGaikokuryohi());
			intRyohiTotal = intRyohiTotal
					+ StringUtil.parseInt(keihiInfo[i].getRyohi());
			intShakinTotal = intShakinTotal
					+ StringUtil.parseInt(keihiInfo[i].getShakin());
			intSonotaTotal = intSonotaTotal
					+ StringUtil.parseInt(keihiInfo[i].getSonota());
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
//		if (page == 2) {
//			if (MAX_KENKYUKEIHI_GOKEI < intKeihiTotal) {
//				name = "�����o�� ���v";
//				ActionError error = new ActionError("errors.5033", name, String
//						.valueOf(MAX_KENKYUKEIHI_GOKEI));
//				errors.add(property, error);
//			}
//		}
		
		if (MAX_KENKYUKEIHI_GOKEI < intKeihiTotal) {
			name = "�����o�� ���v";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intBihinhiTotal) {
			name = "�ݔ����i�� ���v";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intShomohinhiTotal) {
			name = "���Օi�� ���v";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intKokunairyohiTotal) {
			name = "�������� ���v";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intGaikokuryohiTotal) {
			name = "�O������ ���v";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intRyohiTotal) {
			name = "���� ���v";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intShakinTotal) {
			name = "�Ӌ��� ���v";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}
		if (MAX_KENKYUKEIHI_GOKEI < intSonotaTotal) {
			name = "���̑� ���v";
			ActionError error = new ActionError(errKey, name, value1);
			errors.add(property, error);
		}

	}
    
//2007/02/25 �����F�@�ǉ��@��������  
    /**
     * ���ʌ����������ۑ�ԍ�-�N�x�Ɠ��ʌ����������ۑ�ԍ��|�����ԍ����`�F�b�N�B
     * 
     * @param errors
     *            ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
     * @param page
     *            �y�[�W�ԍ��i�����؃��x���j
     */
    private void validateAndShoreihiNoNendo(ActionErrors errors, int page) {

    	//���ʌ�������������z�ɋ��z�����͂��ꂽ�i0�łȂ��j�ꍇ�A�K�{.
    	if (!StringUtil.isBlank(shinseiDataInfo.getNaiyakugaku())
                && StringUtil.parseInt(shinseiDataInfo.getNaiyakugaku()) != 0) {
//2007/03/13 �����F�@�C���@��������        
            if (StringUtil.isBlank(shinseiDataInfo.getShoreihiNoNendo())) {
                ActionError error = new ActionError("errors.9033");
                errors.add("shinseiDataInfo.shoreihiNoNendo", error);
            }
            if (StringUtil.isBlank(shinseiDataInfo.getShoreihiNoSeiri())) {
                ActionError error = new ActionError("errors.9034");
                errors.add("shinseiDataInfo.shoreihiNoSeiri", error);
            }
        }
        //���ʌ�������������z��0�̏ꍇ�A���͂ł��܂���.
        if (!StringUtil.isBlank(shinseiDataInfo.getNaiyakugaku())
                && StringUtil.parseInt(shinseiDataInfo.getNaiyakugaku()) == 0) {
            if (!StringUtil.isBlank(shinseiDataInfo.getShoreihiNoNendo())) {
                ActionError error = new ActionError("errors.9035");
                errors.add("shinseiDataInfo.shoreihiNoNendo", error);
            }
            if (!StringUtil.isBlank(shinseiDataInfo.getShoreihiNoSeiri())) {
                ActionError error = new ActionError("errors.9036");
                errors.add("shinseiDataInfo.shoreihiNoSeiri", error);
            }

        }
//2007/03/13 �����F�@�C���@�����܂� 
    }
//  2007/02/25 �����F�@�ǉ��@�����܂�

}
