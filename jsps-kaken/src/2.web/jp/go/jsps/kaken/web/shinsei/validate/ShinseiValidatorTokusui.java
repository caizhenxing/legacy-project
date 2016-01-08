/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2005/01/17
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
 * �`���`�F�b�N�N���X�i�����p�j
 * ID RCSfile="$RCSfile: ShinseiValidatorTokusui.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/16 06:28:39 $"
 */
public class ShinseiValidatorTokusui extends DefaultShinseiValidator {

	/**
	 * �R���X�g���N�^
	 * @param shinseiDataInfo
	 * @param page
	 */
	public ShinseiValidatorTokusui(ShinseiDataInfo shinseiDataInfo) {
		super(shinseiDataInfo);
	}

	
	
	
	/**
	 * �`���`�F�b�N�i�����p�j
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request, int page, ActionErrors errors) {

		//��^����----- 
		//ActionErrors errors = new ActionErrors();

		//�����o��̍��v���Z�o���A�ő�l�`�F�b�N���s��
		validateAndSetKeihiTotal(errors, page);
		
		//2005/06/02 �ǉ� ��������----------------------------------------------
		//�eShinseiValidater�N���X��validate�������s�����߃R�����g����
		//�����g�D�\�̌`���`�F�b�N���s���B
		validateKenkyuSoshiki(errors, page);
		
		countKenkyushaNinzu(errors, page);
		//�ǉ� �����܂�---------------------------------------------------------
		
		return errors;
	}
	
	
	
	/**
	 * �����o��̑��v���Z�o���Đ\�����ɃZ�b�g����B
	 * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
	 * @param page   �y�[�W�ԍ��i�����؃��x���j
	 */
	private void validateAndSetKeihiTotal(ActionErrors errors, int page)
	{
		//�����o��V�X�e��MAX�l
		int MAX_KENKYUKEIHI = 9999999;
		
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
		for(int i=0; i<keihiInfo.length; i++){
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
			
			//�P�N�x���v�̌`���`�F�b�N
			if(MAX_KENKYUKEIHI < intKeihi){
				int rowIndex = i+1;
				name = "�����o�� " + rowIndex + "�s��";
				ActionError error = new ActionError(errKey,name,value);
				errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
				String propertyRow = property + (rowIndex - 1);
				errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
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
	
	//2005/06/02 �ǉ� ��������----------------------------------------------
	//�eShinseiValidater�N���X��validate�������s�����߃R�����g����	
	
	/**
	 * �����g�D�\�I�u�W�F�N�g�ɑ΂��Č`���`�F�b�N��������B
	 * page���u0�ȏ�v�̏ꍇ�A�`���`�F�b�N���s���B�K�{�`�F�b�N�͍s��Ȃ��B<br>
	 * page���u2�ȏ�v�̏ꍇ�A�K�{�`�F�b�N���s���B<br>
	 * <p>
	 * ex.<br>
	 * <li>page���u1�v�̂Ƃ��A�`���`�F�b�N�̂ݍs���B</li>
	 * <li>page���u2�v�̂Ƃ��A�K�{�`�F�b�N�{�`���`�F�b�N���s���B</li>
	 * </p>
	 * �i��List�^�̃I�u�W�F�N�g��Struts��Validator�������Ă��A
	 * �v�������������������Ȃ̂ŁA�Ǝ��Ɏ�������B�j
	 * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
	 * @param page   �y�[�W�ԍ��i�����؃��x���j
	 */
/* *********************************************** 2005/9/1 ���ʊ֐��ɂȂ����̂ŃR�����g����
	private void validateKenkyuSoshiki(ActionErrors errors, int page)
	{
		String    name         = null;				//���ږ�
		String    value        = null;				//�l
		String    property     = null;				//�v���p�e�B��
		
		Set       kenkyuNoSet  = new HashSet();	//�����Ҕԍ��̃Z�b�g�i�d���������Ȃ����߁j
		boolean  buntanEffort = false;			//���S�҂̃G�t�H�[�g�L���t���O
		
		//2005/03/29 �ǉ� ---------------------------------------��������
		//���R �����g�D�Ɍ����҂��ǉ����ꂽ����
		boolean  kyoryokusha = false;			//���͎҃t���O
		int       kyoryokushaCnt = 0;			//���͎Ґ�
		int       buntanshaCnt = 0;				//��\�ҁ{���S�Ґ�
		int       cnt=0;						//�G���[�ɂĕ\�����郉�x���̔ԍ�
		String    namePrefix = null;			//�G���[�ɂĕ\�����郉�x���̃v���t�B�b�N�X	
		//2005/03/29 �ǉ� ---------------------------------------�����܂�
		
		//========== �����g�D�\�̃��X�g���J��Ԃ� �n�܂� ==========
		List kenkyushaList = shinseiDataInfo.getKenkyuSoshikiInfoList();
		for(int i=1; i<=kenkyushaList.size(); i++){
			//������
			KenkyuSoshikiKenkyushaInfo kenkyushaInfo = 
						(KenkyuSoshikiKenkyushaInfo)kenkyushaList.get(i-1);
			
			//�������͎҂��ǂ�������
			kyoryokusha = ("3".equals(kenkyushaInfo.getBuntanFlag()));

			if(kyoryokusha){
				//2005/06/02 �ύX ��������-----------------------------------
				//���̕ύX�̂���
				//namePrefix="�����g�D(���͎�)".intern();
				namePrefix="�����g�D(�������͎�)".intern();
				//�ύX �����܂�----------------------------------------------
				kyoryokushaCnt++;
				cnt=kyoryokushaCnt;
			} else {
				//2005/06/02 �ύX ��������-----------------------------------
				//���̕ύX�̂���
				//namePrefix="�����g�D".intern();
				namePrefix="�����g�D(������\�ҋy�ь������S��)".intern();
				//�ύX �����܂�----------------------------------------------
				buntanshaCnt++;
				cnt=buntanshaCnt;
			}
			
			if(!kyoryokusha){
				//-----�����Ҕԍ�-----
				name     = namePrefix+" �����Ҕԍ� "+cnt+"�s��";
				value    = StringUtil.toHankakuDigit(kenkyushaInfo.getKenkyuNo());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.kenkyuNo";

				//�{�o�^
				if(page >= 2){
					//�K�{�`�F�b�N
					if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
					}else{
						//�d���`�F�b�N
						if(kenkyuNoSet.contains(value)){
							//���̑��u99999999�v�̂Ƃ��͏d���G���[�Ƃ��Ȃ�
							if(!"99999999".equals(value)){
								ActionError error = new ActionError("errors.5021",name);
								errors.add(property, error);
							}
						}else{
							kenkyuNoSet.add(value);	//���݂��Ă��Ȃ������ꍇ�̓Z�b�g�Ɋi�[
						}
					}
				}
				//�ꎞ�ۑ�, �{�o�^
				if(page >=0){
					if(!StringUtil.isBlank(value)){
						//���l�`�F�b�N
						if(!StringUtil.isDigit(value)){
							ActionError error = new ActionError("errors.numeric",name);
							errors.add(property, error);
						//�������`�F�b�N
						}else if(value.length() != 8){
							ActionError error = new ActionError("errors.length",name,"8");
							errors.add(property, error);
						}
					}
				}
				kenkyushaInfo.setKenkyuNo(value);	//���p���l�ɕϊ������l���Z�b�g
				name     = null;
				value    = null;
				property = null;
			}
			
			
			//-----�����i����-���j-----
			name     = namePrefix+" �����i�������j�i���j "+cnt+"�s��";
			value    = kenkyushaInfo.getNameKanjiSei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanjiSei";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
			
			//-----�����i����-���j-----
			name     = namePrefix+" �����i�������j�i���j "+cnt+"�s��";
			value    = kenkyushaInfo.getNameKanjiMei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanjiMei";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
			
			//-----�����i�t���K�i-���j-----
			name     = namePrefix+" �����i�t���K�i�j�i���j "+cnt+"�s��";
			value    = kenkyushaInfo.getNameKanaSei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanaSei";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�S�p�`�F�b�N
					if(!StringUtil.isZenkaku(value)){
						ActionError error = new ActionError("errors.mask_zenkaku",name);
						errors.add(property, error);
					//�ő啶�����`�F�b�N
					}else if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
			
			//-----�����i�t���K�i-���j-----
			name     = namePrefix+" �����i�t���K�i�j�i���j "+cnt+"�s��";
			value    = kenkyushaInfo.getNameKanaMei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanaMei";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�S�p�`�F�b�N
					if(!StringUtil.isZenkaku(value)){
						ActionError error = new ActionError("errors.mask_zenkaku",name);
						errors.add(property, error);
					//�ő啶�����`�F�b�N
					}else if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
		
			//-----�N��-----
			name     = namePrefix+" �N�� "+cnt+"�s��";
			value    = StringUtil.toHankakuDigit(kenkyushaInfo.getNenrei());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nenrei";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//���l�`�F�b�N
					if(!StringUtil.isDigit(value)){
						ActionError error = new ActionError("errors.numeric",name);
						errors.add(property, error);
					//�ő啶�����`�F�b�N
					}else if(value.length() > 2){
						ActionError error = new ActionError("errors.maxlength",name,"2");
						errors.add(property, error);
					}
				}
			}
			kenkyushaInfo.setNenrei(value);		//���p���l�ɕϊ������l���Z�b�g
			name     = null;
			value    = null;
			property = null;
		
		
			if(!kyoryokusha){
				//-----�����@�֖��i�R�[�h�j-----
				name     = namePrefix+" ���������@�ցi�R�[�h�j "+cnt+"�s��";
				value    = StringUtil.toHankakuDigit(kenkyushaInfo.getShozokuCd());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.shozokuCd";
				//�{�o�^
				if(page >= 2){
					//�K�{�`�F�b�N
					if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
					}
				}
				//�ꎞ�ۑ�, �{�o�^
				if(page >=0){
					if(!StringUtil.isBlank(value)){
						//���l�`�F�b�N
						if(!StringUtil.isDigit(value)){
							ActionError error = new ActionError("errors.numeric",name);
							errors.add(property, error);
						//�������`�F�b�N
						}else if(value.length() != 5){
							ActionError error = new ActionError("errors.length",name,"5");
							errors.add(property, error);
						}
					}
				}
				kenkyushaInfo.setShozokuCd(value);		//���p���l�ɕϊ������l���Z�b�g
				name     = null;
				value    = null;
				property = null;
			
			
				//-----���ǖ��i�R�[�h�j-----
				name     = namePrefix+" ���ǁi�R�[�h�j "+cnt+"�s��";
				value    = StringUtil.toHankakuDigit(kenkyushaInfo.getBukyokuCd());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.bukyokuCd";
				//�{�o�^
				if(page >= 2){
					//�K�{�`�F�b�N
					if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
					}
				}
				//�ꎞ�ۑ�, �{�o�^
				if(page >=0){
					if(!StringUtil.isBlank(value)){
						//���l�`�F�b�N
						if(!StringUtil.isDigit(value)){
							ActionError error = new ActionError("errors.numeric",name);
							errors.add(property, error);
						//�������`�F�b�N
						}else if(value.length() != 3){
							ActionError error = new ActionError("errors.length",name,"3");
							errors.add(property, error);
						}
					}
				}
				kenkyushaInfo.setBukyokuCd(value);		//���p���l�ɕϊ������l���Z�b�g
				name     = null;
				value    = null;
				property = null;
			}
			
		
		
			//-----���ǖ��i�a���j-----
			name     = namePrefix+" ���ǁi�a���j "+cnt+"�s��";
			value    = kenkyushaInfo.getBukyokuName();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.bukyokuName";
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 50){
						ActionError error = new ActionError("errors.maxlength",name,"50");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
		
		
		
			//-----�E���R�[�h-----
			name     = namePrefix+" �E "+cnt+"�s��";
			value    = kenkyushaInfo.getShokushuCd();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.shokushuCd";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}		
			name     = null;
			value    = null;
			property = null;
		
		
			//-----�E���i�a���j-----
			name     = namePrefix+" �E "+cnt+"�s��";
			value    = kenkyushaInfo.getShokushuNameKanji();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.shokushuNameKanji";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N�i�E��R�[�h���u���̑�(25)�v�̂Ƃ��j
				if("25".equals(kenkyushaInfo.getShokushuCd())){
					if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
					}
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 20){
						ActionError error = new ActionError("errors.maxlength",name,"20");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
			//�������͎҂͈ȉ��̓��͊m�F���s��Ȃ�
			if(kyoryokusha){
				continue;
			}
			
			//-----���݂̐��-----
			name     = namePrefix+" ���݂̐�� "+cnt+"�s��";
			value    = kenkyushaInfo.getSenmon();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.senmon";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 20){
						ActionError error = new ActionError("errors.maxlength",name,"20");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
			
			
			//-----�w��-----
			name     = namePrefix+" �w�� "+cnt+"�s��";
			value    = kenkyushaInfo.getGakui();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.gakui";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 20){
						ActionError error = new ActionError("errors.maxlength",name,"20");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
		
		
			//-----�������S-----
			name     = namePrefix+" �������S "+cnt+"�s��";
			value    = kenkyushaInfo.getBuntan();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.buntan";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 50){
						ActionError error = new ActionError("errors.maxlength",name,"50");
						errors.add(property, error);
					}
				}
			}
			name     = null;
			value    = null;
			property = null;
		
		
			//-----�����o��-----
			name     = namePrefix+" �����o�� "+cnt+"�s��";
			value    = StringUtil.toHankakuDigit(kenkyushaInfo.getKeihi());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				if(StringUtil.isBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
				}
// 20050721
				else{
					if(!"1".equals(kenkyushaInfo.getBuntanFlag())){
						//������\�҈ȊO�̏ꍇ�̂݃`�F�b�N
						if("1".equals(shinseiDataInfo.getBuntankinFlg())){
							//���S��������ꍇ
							if(StringUtil.parseInt(value) == 0){
								ActionError error = new ActionError("errors.5049",name);
								errors.add(property, error);
							}
						}
						else{
							//���S�����Ȃ��ꍇ
							if(StringUtil.parseInt(value) != 0){
								ActionError error = new ActionError("errors.5050",name);
								errors.add(property, error);
							}
						}
					}
				}
// Horikoshi
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//���l�`�F�b�N
					if(!StringUtil.isDigit(value)){
						ActionError error = new ActionError("errors.numeric",name);
						errors.add(property, error);
					//�ő啶�����`�F�b�N
					}else if(value.length() > 7){
						ActionError error = new ActionError("errors.maxlength",name,"7");
						errors.add(property, error);
					}
				}
			}
			kenkyushaInfo.setKeihi(value);	//���p���l�ɕϊ������l���Z�b�g
			name     = null;
			value    = null;
			property = null;
	

			//-----�G�t�H�[�g-----
			name     = namePrefix+" �G�t�H�[�g "+cnt+"�s��";
			value    = StringUtil.toHankakuDigit(kenkyushaInfo.getEffort());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.effort";
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N�i������\�҂̂Ƃ��j
//				if("1".equals(kenkyushaInfo.getBuntanFlag())){
					if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
					}
//				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//���݃`�F�b�N�i�������S�҂̂Ƃ��j
					if("2".equals(kenkyushaInfo.getBuntanFlag())){
						buntanEffort = true;					
					}					
					//���l�`�F�b�N
					if(!StringUtil.isDigit(value)){
						ActionError error = new ActionError("errors.numeric",name);
						errors.add(property, error);
					//�͈̓`�F�b�N�i1�`100�j
					}else if(StringUtil.parseInt(value)<=0 || StringUtil.parseInt(value)>100){
						ActionError error = new ActionError("errors.range",name,
								IShinseiMaintenance.EFFORT_MIN,
								IShinseiMaintenance.EFFORT_MAX
								);
						errors.add(property, error);
					}
				}
			}
			kenkyushaInfo.setEffort(value);		//���p���l�ɕϊ������l���Z�b�g
			name     = null;
			value    = null;
			property = null;
	
		}
		//========== �����g�D�\�̃��X�g���J��Ԃ� �I��� ==========
		
		
//		//-----���S���̗L���Ƃ̑g�ݍ��킹�`���`�F�b�N-----
//		property = "shinseiDataInfo.buntankinFlg";
//		//�{�o�^
//		if(page >= 2){
//			//���S���u�L�v�̏ꍇ�i���S�҃G�t�H�[�g���P�����͂���Ă��Ȃ��ꍇ�̓G���[�j
//			if("1".equals(shinseiDataInfo.getBuntankinFlg()) && !buntanEffort){
//				ActionError error = new ActionError("errors.5022");
//				errors.add(property, error);
//			//���S���u���v�ꍇ�i���S�҃G�t�H�[�g���P�ł����͂���Ă���ƃG���[�j
//			}else if("2".equals(shinseiDataInfo.getBuntankinFlg()) && buntanEffort){
//				ActionError error = new ActionError("errors.5022");
//				errors.add(property, error);
//			}
//		}
		property = null;
		
		
	}
************************************************* 2005/9/1 */	
	
	
	/**
	 * �����g�D�\���A�S�����Ґ��A���@�ւ̌����Ґ����Z�o����B
	 * �܂��A�����o��̍��v���\���f�[�^�̌����o��i1�N�ځj�Ɠ����l�ɂȂ邩���`�F�b�N����B
	 * ����Ă����ꍇ�� ValidationException ���X���[����B
	 * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
	 * @param page   �y�[�W�ԍ��i�����؃��x���j
	 */
	private void countKenkyushaNinzu(ActionErrors errors, int page)
	{
		//�����g�D�\�̌����o��v
		int keihiTotal = 0;
		
		//�����g�D�\�̑�\�ҔN���\���f�[�^�ɂ��Z�b�g����
		List kenkyuSoshikiList = shinseiDataInfo.getKenkyuSoshikiInfoList();
		
		//������\�҂̏����@�փR�[�h���擾����
		DaihyouInfo daihyouInfo = shinseiDataInfo.getDaihyouInfo();
		String daihyouKikanCd = daihyouInfo.getShozokuCd();
		
		//������\�҂̔N��A���@�ւ̌����Ґ����擾����
		int kenkyuNinzu  = kenkyuSoshikiList.size();
		int takikanNinzu = 0;
		
		//2005/03/30 �ǉ� -------------------------��������
		//���R �������͎҂������g�D�\�ɒǉ����ꂽ����
		int kyoryokushaNinzu = 0;
		//2005/03/30 �ǉ� -------------------------�����܂�

		for(int i=0; i<kenkyuNinzu; i++){
			KenkyuSoshikiKenkyushaInfo kenkyushainfo = (KenkyuSoshikiKenkyushaInfo)kenkyuSoshikiList.get(i);

			//2005/03/30 �C�� -------------------------��������
			//���R �������͎҂������g�D�\�ɒǉ����ꂽ����
			////��\�ҁi�C���f�b�N�X�l���u0�v�̂��́j
			//if(i==0){
			//	//��\�҂̔N���\���f�[�^���ɃZ�b�g����
			//	daihyouInfo.setNenrei(kenkyushainfo.getNenrei());
			////���S��
			//}else{
			//	//��\�҂̏����@�փR�[�h�ƈႤ�ꍇ�̓J�E���g�v���X�P
			//	if( daihyouKikanCd != null && 
			//	   !daihyouKikanCd.equals(kenkyushainfo.getShozokuCd()))
			//	{
			//		takikanNinzu++;
			//	}
			//}

			//��\�ҁi�C���f�b�N�X�l���u0�v�̂��́j
			if(i==0){
				//��\�҂̔N���\���f�[�^���ɃZ�b�g����
				daihyouInfo.setNenrei(kenkyushainfo.getNenrei());
			//���S��
			}else if("2".equals(kenkyushainfo.getBuntanFlag())){
				//��\�҂̏����@�փR�[�h�ƈႤ�ꍇ�̓J�E���g�v���X�P
				if( daihyouKikanCd != null && 
				   !daihyouKikanCd.equals(kenkyushainfo.getShozokuCd()))
				{
					takikanNinzu++;
				}
			//���͎�
			} else {
				kyoryokushaNinzu++;
			}
			//2005/03/30 �C�� -------------------------�����܂�

			keihiTotal = keihiTotal + StringUtil.parseInt((String)kenkyushainfo.getKeihi());
		}
		//2005/06/02 �ύX ��������-----------------------------------
		//���̕ύX�̂���
		//shinseiDataInfo.setKenkyuNinzu(String.valueOf(kenkyuNinzu));	//�����Ґ�
		shinseiDataInfo.setKenkyuNinzu(String.valueOf(kenkyuNinzu-kyoryokushaNinzu));	//�����Ґ�
		//�ύX �����܂�----------------------------------------------
		shinseiDataInfo.setTakikanNinzu(String.valueOf(takikanNinzu));	//���@�ւ̌����Ґ�

		//2005/03/30 �ǉ� -------------------------��������
		//���R �������͎҂������g�D�\�ɒǉ����ꂽ����
		shinseiDataInfo.setKyoryokushaNinzu(String.valueOf(kyoryokushaNinzu));	//�������͎Ґ�
		//2005/03/30 �ǉ� -------------------------�����܂�

		
		//�{�o�^
		if(page >= 2){
// 20050809 ���S���̗L���ƌ����l�����r ���L�̏ꍇ�ɂ͌����l����2�ȏ� ���������S�҂����݂��Ȃ��ꍇ�Ƀ`�F�b�N�����蔲���Ă���
			if(Integer.parseInt(shinseiDataInfo.getKenkyuNinzu()) == 1 &&
				"1".equals(shinseiDataInfo.getBuntankinFlg())
				){
				String property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
				ActionError error = new ActionError("errors.5055");
				errors.add(property, error);
//				<!-- ADD�@START 2007/07/09 BIS ���� -->	
				shinseiDataInfo.getErrorsMap().put(property,"���S���̗L��:�u�L�v���u�����g�D�\�v�́u���S�ҁv�̋L��������A���A�u�����o��v�ɓ��͂����邱�Ɓi������΃G��");
				shinseiDataInfo.setErrorsMap( shinseiDataInfo.getErrorsMap());
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			}
// Horikoshi
			//�����o��̍��v���\���f�[�^��1�N�ڌ����o��Ɠ����ɂȂ��Ă��邩�`�F�b�N����
			if(keihiTotal != 
			   StringUtil.parseInt(shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[0].getKeihi()))
			{
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
	//�ǉ� �����܂�-------------------------------------------------------------------
}
