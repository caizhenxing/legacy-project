/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
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
import jp.go.jsps.kaken.model.IShinseiMaintenance;

/**
 * �`���`�F�b�N�N���X�i����̈�p�j
 * ID RCSfile="$RCSfile: ShinseiValidatorTokutei.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/16 06:28:39 $"
 */
public class ShinseiValidatorTokutei extends DefaultShinseiValidator {
 
    /**
	 * �R���X�g���N�^
	 * @param shinseiDataInfo
	 * @param page
	 */
	public ShinseiValidatorTokutei(ShinseiDataInfo shinseiDataInfo) {
		super(shinseiDataInfo);
	}


// 20050617
	/**
	 * �`���`�F�b�N�i����̈�p�j
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
			ActionMapping mapping,
			HttpServletRequest request,
			int page,
			ActionErrors errors) {

		validateAndSetTokutei(errors, request, page);		//����̈�̃`�F�b�N�ƃ`�F�b�N�{�b�N�X����
		validateAndSetKeihiTotal(errors, page);				//�����o��̍��v���Z�o���A�ő�l�`�F�b�N���s��
		validateKenkyuSoshiki(errors, page);				//�����g�D�\�̌`���`�F�b�N���s��
		countKenkyushaNinzu(errors, page);					//������
//2006/08/24�@�c�@�ǉ���������
        validateKomokuNo(errors);                           //�������ڔԍ��̐��K�\���`�F�b�N
//2006/08/24�@�c�@�ǉ������܂�        

		return errors;
	}

	/**
	 * ����̈���̏�Ԃ��Ǘ�����
	 * @param errors
	 * @param request
	 * @param page
	 */
	private void validateAndSetTokutei(ActionErrors errors, HttpServletRequest request, int page)
	{
		String	name			=	null;			//���ږ�
		String	value			=	null;			//�l
		String	property		=	null;			//�v���p�e�B��
		int		cnt				=	0;				//�G���[�ɂĕ\�����郉�x���̔ԍ�
		String	namePrefix		=	null;			//�G���[�ɂĕ\�����郉�x���̃v���t�B�b�N�X	

// �`�F�b�N�{�b�N�X����
		if(IShinseiMaintenance.CHECK_ON.equals(request.getParameter("shinseiDataInfo.changeFlg"))){		//�啝�ȕύX
			shinseiDataInfo.setChangeFlg(IShinseiMaintenance.CHECK_ON);}					//�I��
		else{shinseiDataInfo.setChangeFlg(IShinseiMaintenance.CHECK_OFF);}					//�I�t
		
		//2005.08.09 iso ���匤���Ȃ璲���ǂɁu-�v�������Z�b�g
		//������
//		if(IShinseiMaintenance.CHECK_ON.equals(request.getParameter("shinseiDataInfo.chouseiFlg"))){		// ������
//			shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_ON);}					//�I��
//		else{shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_OFF);}					//�I�t
		//���匤���ȊO�Ń`�F�b�N�Ȃ��̏ꍇ
		if(!IShinseiMaintenance.KUBUN_KOUBO.equals(shinseiDataInfo.getKenkyuKubun())
				&& IShinseiMaintenance.CHECK_ON.equals(request.getParameter("shinseiDataInfo.chouseiFlg"))) {
			shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_ON);}					//�I��
		else{shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_OFF);}				//�I�t

		/** �����敪 */
		/** �V�K�E�p���敪 */
		if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun())){
			//�����敪�ŏI���������I������Ă����ꍇ�ɂ͐\���敪�ɐV�K���Z�b�g
			shinseiDataInfo.setShinseiKubun(IShinseiMaintenance.SHINSEI_NEW);
		}

		/** �啝�ȕύX�𔺂������ۑ� */
		if (page >= IShinseiMaintenance.CHECK_TWO){
			if (IShinseiMaintenance.CHECK_ON.equals(shinseiDataInfo.getChangeFlg())
				&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())
// �I�������ȊO�̏ꍇ�������ɒǉ�
				&& !IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun())
				){
				// �`�F�b�N����Ă���ꍇ�A�V�K�E�p���敪�ŐV�K���I������Ă���ꍇ�ɂ̓G���[�i.xml���Ŏ��s�H�j
				ActionError error = new ActionError(
						"errors.5038",
						IShinseiMaintenance.SHINSEI_KUBUN,
						IShinseiMaintenance.CHANGE_FLG
						);
				errors.add("shinseiDataInfo.changeFlg", error);
			}
		}

		/** �p���̏ꍇ�̌����ۑ�ԍ� */
		// �l�������[�ɋL�q����Ă���p���ۑ�f�[�^�Ƃ́H
//		//2005.08.09 iso ���匤�� OR �v�挤��(�V�K)�̏ꍇ�A�����ۑ�ԍ��Ƀu�����N���Z�b�g
//		if(IShinseiMaintenance.KUBUN_KOUBO.equals(shinseiDataInfo.getKenkyuKubun())
//				|| (IShinseiMaintenance.KUBUN_KEIKAKU.equals(shinseiDataInfo.getKenkyuKubun())
//					&& IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun()))) {
//			shinseiDataInfo.setKadaiNoKeizoku("");
//		}

		/** �̈�ԍ����̈旪�̖� */
		if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun()) || 
			IShinseiMaintenance.RYOUIKI_SHUURYOU.equals(shinseiDataInfo.getRyouikiNo())){
			shinseiDataInfo.setRyouikiNo(IShinseiMaintenance.RYOUIKI_SHUURYOU);				// �����敪�ŏI�������̈悪�I������Ă���ꍇ�ɂ͎����Łu999�v���Z�b�g
			shinseiDataInfo.setRyouikiRyakuName(IShinseiMaintenance.SHUURYOU_NAME);			// �����敪�ŏI�������̈悪�I������Ă���ꍇ�ɂ͎����Łu���ʎ��܂Ƃ߁v���Z�b�g
		}
		else{
			// �̈�}�X�^�ɑ��݂��Ȃ��ԍ����̖��̏ꍇ(ShinseiMaintenance�ɂĎ��s)
		}

		/** �������ڔԍ� */
// xml�ɕK�{�`�F�b�N��ǉ��������ߏ����t���K�{�`�F�b�N�͍폜
//		if(
//			(IShinseiMaintenance.KUBUN_KEIKAKU.equals(shinseiDataInfo.getKenkyuKubun())) ||
//			(IShinseiMaintenance.KUBUN_KOUBO.equals(shinseiDataInfo.getKenkyuKubun()))
//			){
//			//�v�挤���A�܂��͌��匤���̏ꍇ
//			if((shinseiDataInfo.getRyouikiKoumokuNo() == null || shinseiDataInfo.getRyouikiKoumokuNo().length() <= 0) &&
//				shinseiDataInfo.getChouseiFlg() == IShinseiMaintenance.CHECK_OFF
//				){
//				//�������ڔԍ������͂���Ă��炸�A�����ǃ`�F�b�N�{�b�N�X�ɂ��`�F�b�N����Ă��Ȃ��ꍇ
//				ActionError error = new ActionError(
//						"errors.required",
//						IShinseiMaintenance.KENKYUU_NUM
//						);
//				errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
//			}
//		}
		if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun())){
			//�����敪���I�������ł������ꍇ�ɂ͌������ڔԍ����Z�b�g�@��IShinseiMaintenance�̒萔�ɂĊǗ�
			shinseiDataInfo.setRyouikiKoumokuNo(IShinseiMaintenance.HAN_CHAR + IShinseiMaintenance.HAN_NO);
		}

// 20050818 �����ǂɂ��Ẵ`�F�b�N���C��
		if (page >= IShinseiMaintenance.CHECK_TWO){
		if(shinseiDataInfo.getRyouikiKoumokuNo() != null && shinseiDataInfo.getRyouikiKoumokuNo().length() >= 1){
			String chHan = shinseiDataInfo.getRyouikiKoumokuNo();
			if(IShinseiMaintenance.CHECK_ON.equals(shinseiDataInfo.getChouseiFlg()) &&
				("X00".equals(chHan) || "Y00".equals(chHan))
				){
				ActionError error = new ActionError(
						"errors.5054",
						IShinseiMaintenance.KENKYUU_NUM + "��" + chHan,
						IShinseiMaintenance.CHOUSEI_FLG
						);
				errors.add("shinseiDataInfo.chouseiFlg", error);
			}
// �����I�ɃZ�b�g���Ȃ����ߍ폜
//			//�������ڔԍ��ɓ��͂���Ă����ꍇ�ɒ����ǂ������I�Ƀ`�F�b�N�I�t
//			shinseiDataInfo.setChouseiFlg(IShinseiMaintenance.CHECK_OFF);
		}
		}

// ���`���̃`�F�b�N���폜
//		if (page >= IShinseiMaintenance.CHECK_TWO){
//			if (shinseiDataInfo.getRyouikiKoumokuNo() != null &&
//				shinseiDataInfo.getRyouikiKoumokuNo().length() >= 1){
//
//				if (shinseiDataInfo.getRyouikiKoumokuNo().indexOf(IShinseiMaintenance.HAN_SOUKATU) 
//						!= IShinseiMaintenance.KOUMOKU_CHECK_NUM
//					&& shinseiDataInfo.getRyouikiKoumokuNo().indexOf(IShinseiMaintenance.HAN_SHIEN) 
//						!= IShinseiMaintenance.KOUMOKU_CHECK_NUM){
//					// ������̍ŏ����uX�v�܂��́uY�v�ȊO�̏ꍇ�̓G���[
//					ActionError error = new ActionError(
//							"errors.5039",
//							IShinseiMaintenance.KENKYUU_NUM,
//							IShinseiMaintenance.HAN_SOUKATU,
//							IShinseiMaintenance.HAN_SHIEN);
//					errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
//				}
//				else{
//					// �̈�}�X�^�ɑ��݂��Ȃ��ԍ��̏ꍇ(ShinseMaintenance.java�ɂĎ��s)
//				}
//			}
//		}

		/** ������ */
		if (page >= IShinseiMaintenance.CHECK_TWO){
			if(IShinseiMaintenance.CHECK_ON.equals(shinseiDataInfo.getChouseiFlg())
				&& !IShinseiMaintenance.KUBUN_KEIKAKU.equals(shinseiDataInfo.getKenkyuKubun())){
				// �`�F�b�N����Ă���ꍇ�A�����敪�Ōv�挤���ȊO���I������Ă���ꍇ�ɂ̓G���[
				ActionError error = new ActionError(
						"errors.5038",
						IShinseiMaintenance.KENKYUU_KUBUN,
						IShinseiMaintenance.CHOUSEI_FLG);
				errors.add("shinseiDataInfo.chouseiFlg", error);
			}
			else{
				// �����������ڔԍ����łP�̂ݑ��݉\�i�o�^���ɂ͓��������ڔԍ����ɒ����ǂ̑��݂������Ȃ��j
				// ���������ڔԍ����̒����ǃ`�F�b�N(�d���`�F�b�N�Ɋ܂܂�邽�ߎ����Ȃ�)
			}
		}

		/** �J����]�̗L�� **/
// �����I�ɃZ�b�g�����ɃG���[��\�����邱�ƂɂȂ�������
//		if(IShinseiMaintenance.KUBUN_KEIKAKU.equals(shinseiDataInfo.getKenkyuKubun())){
//			//�����敪�Ōv�挤�����I������Ă����ꍇ�ɂ͊J����]�̗L���Ƀu�����N���Z�b�g
//			shinseiDataInfo.setKaijikiboFlgNo(IShinseiMaintenance.KAIJI_FLG_SET);
//		}

	}
// Horikoshi End


	
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
		Set       kenkyuNoSet  = new HashSet();		//�����Ҕԍ��̃Z�b�g�i�d���������Ȃ����߁j
		boolean  buntanEffort = false;				//���S�҂̃G�t�H�[�g�L���t���O
		boolean  kyoryokusha = false;				//���͎҃t���O
		int       kyoryokushaCnt = 0;				//���͎Ґ�
		int       buntanshaCnt = 0;					//��\�ҁ{���S�Ґ�
		int       cnt=0;							//�G���[�ɂĕ\�����郉�x���̔ԍ�
		String    namePrefix = null;				//�G���[�ɂĕ\�����郉�x���̃v���t�B�b�N�X	
		
		//========== �����g�D�\�̃��X�g���J��Ԃ� �n�܂� ==========
		List kenkyushaList = shinseiDataInfo.getKenkyuSoshikiInfoList();
		for(int i=1; i<=kenkyushaList.size(); i++){
			//������
			KenkyuSoshikiKenkyushaInfo kenkyushaInfo = 
						(KenkyuSoshikiKenkyushaInfo)kenkyushaList.get(i-1);

			//�������͎҂��ǂ�������
			kyoryokusha = ("3".equals(kenkyushaInfo.getBuntanFlag()));

			if(kyoryokusha){
				namePrefix="�����g�D(�������͎�)".intern();
				kyoryokushaCnt++;
				cnt=kyoryokushaCnt;
			} else {
				namePrefix="�����g�D(������\�ҋy�ь������S��)".intern();
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

// 20050628 �u99999999�v���G���[�Ƃ��Ĉ������ߏd�����ɂ͂��ׂăG���[�ƂȂ�@���@�������폜
// ShinseiMaintenance�Łu99999999�v�̏ꍇ�ɂ̓G���[�Ƃ��邽�߁A�d���`�F�b�N�Łu99999999�v��ΏۂƂ���K�v�͂Ȃ�
// �eShinseiValidator�ŋL�q����K�v�͂Ȃ�
//							if(!"99999999".equals(value)){
								ActionError error = new ActionError("errors.5021",name);
								errors.add(property, error);
//							}
// Horikoshi

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
				// 20050728 �G�t�H�[�g�͑S�Ăɂ����ĕK�{�ł��邽�ߕ��S���̗L���Ɋւ��Ȃ�
				if(StringUtil.isBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
				}
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
					// 20050712 �G�t�H�[�g�͑S�ĕK�{���ڂłO�������Ȃ�����
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
		

// 20050728 ���S���̗L���ƃG�t�H�[�g���r���邱�Ƃ͂��Ȃ�
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
// Horikoshi

		property = null;
	}
*********************************************************** 2005/9/1 */

	/**
	 * �����g�D�\���A�S�����Ґ��A���@�ւ̌����Ґ����Z�o����B
	 * �܂��A�����o��̍��v���\���f�[�^�̌����o��i1�N�ځj�Ɠ����l�ɂȂ邩���`�F�b�N����B
	 * ����Ă����ꍇ�� ValidationException ���X���[����B
	 * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
	 * @param page   �y�[�W�ԍ��i�����؃��x���j
	 */
	private void countKenkyushaNinzu(ActionErrors errors, int page)
	{
		int keihiTotal = 0;															//�����g�D�\�̌����o��v
		List kenkyuSoshikiList = shinseiDataInfo.getKenkyuSoshikiInfoList();		//�����g�D�\�̑�\�ҔN���\���f�[�^�ɂ��Z�b�g����

		//������\�҂̏����@�փR�[�h���擾����
		DaihyouInfo daihyouInfo = shinseiDataInfo.getDaihyouInfo();
		String daihyouKikanCd = daihyouInfo.getShozokuCd();

		//������\�҂̔N��A���@�ւ̌����Ґ����擾����
		int kenkyuNinzu  = kenkyuSoshikiList.size();
		int takikanNinzu = 0;
		int kyoryokushaNinzu = 0;

		for(int i=0; i<kenkyuNinzu; i++){
			KenkyuSoshikiKenkyushaInfo kenkyushainfo = (KenkyuSoshikiKenkyushaInfo)kenkyuSoshikiList.get(i);

			if(i==0){														//��\�ҁi�C���f�b�N�X�l���u0�v�̂��́j
				daihyouInfo.setNenrei(kenkyushainfo.getNenrei());			//��\�҂̔N���\���f�[�^���ɃZ�b�g����
			}else if("2".equals(kenkyushainfo.getBuntanFlag())){			//���S��
				if( daihyouKikanCd != null && 								//��\�҂̏����@�փR�[�h�ƈႤ�ꍇ�̓J�E���g�v���X�P
				   !daihyouKikanCd.equals(kenkyushainfo.getShozokuCd()))
				{
					takikanNinzu++;
				}
			} else {														//���͎�
				kyoryokushaNinzu++;
			}
			keihiTotal = keihiTotal + StringUtil.parseInt((String)kenkyushainfo.getKeihi());
		}
		shinseiDataInfo.setKenkyuNinzu(String.valueOf(kenkyuNinzu-kyoryokushaNinzu));	//�����Ґ�
		shinseiDataInfo.setTakikanNinzu(String.valueOf(takikanNinzu));					//���@�ւ̌����Ґ�
		shinseiDataInfo.setKyoryokushaNinzu(String.valueOf(kyoryokushaNinzu));			//�������͎Ґ�
		
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
	
	
	
	/**
	 * �����o��̑��v���Z�o���Đ\�����ɃZ�b�g����B
	 * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
	 * @param page   �y�[�W�ԍ��i�����؃��x���j
	 */
	private void validateAndSetKeihiTotal(
			ActionErrors errors,
			int page)	{

		//�G���[���b�Z�[�W
		String name         = null;													//���ږ�
		String value        = String.valueOf(IShinseiMaintenance.MAX_KENKYUKEIHI);	//�l
		String errKey       = "errors.maxValue";									//�L�[������
		String property     = "shinseiDataInfo.kenkyuKeihiSoukeiInfo.keihiTotal";	//�v���p�e�B��
//ADD�@START 2007-07-16 BIS ������
		//�s�ڃv���p�e�B
		String propertyRow = null;
//ADD�@END 2007-07-16 BIS ������

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
		for(int i=0; i<IShinseiMaintenance.NENSU; i++){            
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
//ADD�@START 2007-07-16 BIS ������
			//�s�ڂ�ݒ肷��
			propertyRow = property + (rowIndex - 1);
//ADD�@END 2007-07-16 BIS ������
			name = "�����o�� " + rowIndex + "�s��";
			//�P�N�x���v�̌`���`�F�b�N
			if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKeihi){
				ActionError error = new ActionError(errKey,name,value);
				errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
				errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
			}

			//�`�F�b�N�͈ꎞ�ۑ����ɂ͍s��Ȃ�
			if(page >= 2){

// 20050611 Start
				switch(i){
					case(0):			//�P�N��
//						if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
						if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi){
							ActionError error = new ActionError(
									"errors.5031",
									name,
									String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						break;

					case(1):			//�Q�N��
						//�I�������̏ꍇ�ɂ�0�~
						if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " " + IShinseiMaintenance.KENKYUU_KUBUN,
									IShinseiMaintenance.NAME_SHUURYOU,
									""
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						//0�~�A�܂���10���~�ȏ�
						else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5031",
									name,
									String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						break;

					case(2):			//�R�N��
						//���匤���̏ꍇ�ɂ�0�~
						if(IShinseiMaintenance.KUBUN_KOUBO.equals(shinseiDataInfo.getKenkyuKubun()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " " + IShinseiMaintenance.KENKYUU_KUBUN,
									IShinseiMaintenance.NAME_KOUBO,
									""
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						//�I�������̏ꍇ�ɂ�0�~
						else if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " " + IShinseiMaintenance.KENKYUU_KUBUN,
									IShinseiMaintenance.NAME_SHUURYOU,
									""
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						//�O�N�x��0�~�̏ꍇ�ɂ�0�~
						else if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " �O�N�x",
									"0�~",
									""
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						//0�~�A�܂���10���~�ȏ�
						else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5031",
									name,
									String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						break;

					case(3):			//�S�N��
						//�I�������̏ꍇ�ɂ�0�~
						if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " " + IShinseiMaintenance.KENKYUU_KUBUN,
									IShinseiMaintenance.NAME_SHUURYOU,
									""
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						//�O�N�x��0�~�̏ꍇ�ɂ�0�~
						else if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " �O�N�x",
									"0�~",
									""
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						//0�~�A�܂���10���~�ȏ�
						else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5031",
									name,
									String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						break;

					case(4):			//�T�N��
						//�I�������̏ꍇ�ɂ�0�~
						if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " " + IShinseiMaintenance.KENKYUU_KUBUN,
									IShinseiMaintenance.NAME_SHUURYOU,
									""
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						//�O�N�x��0�~�̏ꍇ�ɂ�0�~
						else if("0".equals(keihiInfo[i-1].getKeihi()) && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5032",
									name + " �O�N�x",
									"0�~",
									""
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						//0�~�A�܂���10���~�ȏ�
						else if(IShinseiMaintenance.MIN_KENKYUKEIHI > intKeihi && intKeihi != 0){
							ActionError error = new ActionError(
									"errors.5031",
									name,
									String.valueOf(IShinseiMaintenance.MIN_KENKYUKEIHI)
									);
							errors.add(property, error);
//ADD�@START 2007-07-16 BIS ������
							errors.add(propertyRow, error);
//ADD�@END 2007-07-16 BIS ������
						}
						break;
				}
// Horikoshi End

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

		//�͈͂̃`�F�b�N�͈ꎞ�ۑ����ɂ͍s��Ȃ�
		if(page == 2){
			if(IShinseiMaintenance.MAX_KENKYUKEIHI_GOKEI < intKeihiTotal){
				name = "�����o�� ���v";
				ActionError error = new ActionError(
						"errors.5033",
						name,
						String.valueOf(IShinseiMaintenance.MAX_KENKYUKEIHI_GOKEI)
						);
				errors.add(property, error);
			}
		}

		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKeihiTotal){
			name = "�����o�� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intBihinhiTotal){
			name = "�ݔ����i�� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intShomohinhiTotal){
			name = "���Օi�� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intKokunairyohiTotal){
			name = "�������� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intGaikokuryohiTotal){
			name = "�O������ ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intRyohiTotal){
			name = "���� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intShakinTotal){
			name = "�Ӌ��� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
		if(IShinseiMaintenance.MAX_KENKYUKEIHI < intSonotaTotal){
			name = "���̑� ���v";
			ActionError error = new ActionError(errKey,name,value);
			errors.add(property, error);
		}
	}
    
//2006/08/24�@�c�@�ǉ���������    
    /**
     * �������ڔԍ��̐��K�\���`�F�b�N
     * 
     * @param errors ���؃G���[���������ꍇ�́Aerrors�ɒǉ����Ă����B
     */
    private void validateKomokuNo(ActionErrors errors){
        
        //�������ڔԍ�
        String komokuNo = shinseiDataInfo.getRyouikiKoumokuNo();
        
        //�R�������̃`�F�b�N
        if(komokuNo != null && komokuNo.length() == 3){
            char komokuNoLetter = komokuNo.charAt(0);
            String komokuNoDigit = komokuNo.substring(1);

            //�A���t�@�x�b�g�P�����i�啶���j�łȂ���΂Ȃ�Ȃ�
            if (!StringUtil.isCapitalLetter(komokuNoLetter)) {
                ActionError error = new ActionError("errors.2001", "�������ڔԍ�");
                errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                return;
            }

            //�P���ڂ��u�w�v�܂��u�x�v�̏ꍇ�C�Q�`�R���ڂ́C�O�O�̂�
            if (komokuNoLetter == 'X' || komokuNoLetter == 'Y') {
                if (!"00".equals(komokuNoDigit)) {
                    ActionError error = new ActionError("errors.2001", "�������ڔԍ�");
                    errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                    return;
                }

            //�P���ڂ�����ȊO�̃A���t�@�x�b�g�̏ꍇ�C�Q�`�R���ڂ́C�O�P�`�X�X�̂�    
            } else {
                if (!StringUtil.isDigit(komokuNoDigit)
                        || StringUtil.parseInt(komokuNoDigit) < 1
                        || StringUtil.parseInt(komokuNoDigit) > 99) {
                    ActionError error = new ActionError("errors.2001", "�������ڔԍ�");
                    errors.add("shinseiDataInfo.ryouikiKoumokuNo", error);
                    return;
                }
            }
        }
    }
//2006/08/24�@�c�@�ǉ������܂�    
}