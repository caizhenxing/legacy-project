/*======================================================================
 *	  SYSTEM	  :
 *	  Source name :
 *	  Description :
 *
 *	  Author	  : takano
 *	  Date		  : 2005/01/17
 *
 *	  Revision history
 *	  Date			Revision	Author		   Description
 *
 *======================================================================
 */
package jp.go.jsps.kaken.web.shinsei.validate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.ActionMapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

/**
 * �f�t�H���g�\���`���`�F�b�N�N���X
 * ID RCSfile="$RCSfile: DefaultShinseiValidator.java,v $"
 * Revision="$Revision: 1.5 $"
 * Date="$Date: 2007/07/24 02:27:53 $"
 */
public class DefaultShinseiValidator implements IShinseiValidator {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(DefaultShinseiValidator.class);

	/** �\����� */
	protected ShinseiDataInfo shinseiDataInfo = null;


	/**
	 * �R���X�g���N�^
	 * @param shinseidataInfo
	 */
	public DefaultShinseiValidator(ShinseiDataInfo shinseiDataInfo){
		this.shinseiDataInfo = shinseiDataInfo;
	}


	/**
	 * �`���`�F�b�N�i�󃁃\�b�h�B�����������Ȃ��B�j
	 * @see jp.go.jsps.kaken.web.shinsei.validate.IShinseiValidator#validate(jp.go.jsps.kaken.web.struts.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request, int page, ActionErrors errors) {
		//�󃁃\�b�h�B�����������Ȃ��B
		return new ActionErrors();

	}

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
	 * @param page	 �y�[�W�ԍ��i�����؃��x���j
	 */
	protected void validateKenkyuSoshiki(ActionErrors errors, int page)
	{
		String	  name		          = null;				//���ږ�
		String	  value 	          = null;				//�l
		String	  property	          = null;				//�v���p�e�B��
		Set 	  kenkyuNoSet         = new HashSet();		//�����Ҕԍ��̃Z�b�g�i�d���������Ȃ����߁j
		boolean  buntanEffort        = false;				//���S�҂̃G�t�H�[�g�L���t���O
		boolean  kyoryokusha         = false;				//���͎҃t���O
		int 	  kyoryokushaCnt      = 0;					//���͎Ґ�
		int 	  buntanshaCnt        = 0;					//��\�ҁ{���S�Ґ�
		int 	  cnt                 =0;					//�G���[�ɂĕ\�����郉�x���̔ԍ�
		String	  namePrefix          = null;				//�G���[�ɂĕ\�����郉�x���̃v���t�B�b�N�X
		boolean  kikanFlg	          = true;				//�@�փR�[�h�����������t���O
		String	  daihyoKikan         = null;				//��\�@��
		long     diffKikanTotalKeihi  = 0;					//��\�҂ƈقȂ�@�ւ̕��S�Ҍo��̍��v�l
//		<!-- ADD�@START 2007/07/09 BIS ���� -->
		String	  objKey	          = null;				//�G���[���b�Z�W�[�L�[
		int 	  cntKey              =0;					//�G���[���b�Z�W�̔ԍ�
//		<!-- ADD�@END�@ 2007/07/09 BIS ���� -->		
		
		//========== �����g�D�\�̃��X�g���J��Ԃ� �n�܂� ==========
		List kenkyushaList = shinseiDataInfo.getKenkyuSoshikiInfoList();
//		<!-- ADD�@START 2007/07/09 BIS ���� -->		
		HashMap errMap = new HashMap();
		errMap.clear() ;
//		<!-- ADD�@END�@ 2007/07/09 BIS ���� -->				
		for(int i=1; i<=kenkyushaList.size(); i++){
			//������
			KenkyuSoshikiKenkyushaInfo kenkyushaInfo =
						(KenkyuSoshikiKenkyushaInfo)kenkyushaList.get(i-1);

			//TODO 2005.09.29 iso ���S�t���O�ɋ󂪓���Ή��ňꎞ�ǉ�
			if(StringUtil.isBlank(kenkyushaInfo.getBuntanFlag())) {
				kenkyushaInfo.setBuntanFlag("2");
				log.info("�y�����z���S�t���O��FDefaultShinseiValidator");
			}

			//�������͎҂��ǂ�������
			kyoryokusha = ("3".equals(kenkyushaInfo.getBuntanFlag()));
			//�@�փR�[�h�����������t���O��������
			kikanFlg	 = true;

			if(kyoryokusha){
				namePrefix="�����g�D(�������͎�)".intern();
				kyoryokushaCnt++;
				cnt=kyoryokushaCnt;
			} else {
//UPDATE�@START 2007/07/13 BIS �����_ 				
				//namePrefix="�����g�D(������\�ҋy�ь������S��)".intern();
				namePrefix="�����g�D(������\�ҁA�������S�ҋy�јA�g������)".intern();
//UPDATE�@END�@ 2007/07/13 BIS �����_ 				
				buntanshaCnt++;
				cnt=buntanshaCnt;
			}
			
//			<!-- ADD�@START 2007/07/09 BIS ���� -->				
			if(cnt > 0){
				cntKey = cnt -1;
			}else{
				cntKey = cnt;
			}
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->					
			if(!kyoryokusha){
//ADD�@START 2007/07/24 BIS �����_ //�󔒂̏ꍇ�̓G���[�Ƃ���
				name	 = namePrefix+" �敪 "+cnt+"�s��";
				value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getKenkyuNo());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.buntanFlag";			
				objKey 	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].buntanFlag";
				if("5".equals(kenkyushaInfo.getBuntanFlag())){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);								
					errMap.put(objKey ,name);
				}
//ADD�@END�@ 2007/07/24 BIS �����_
				//-----�����Ҕԍ�-----
				name	 = namePrefix+" �����Ҕԍ� "+cnt+"�s��";
				value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getKenkyuNo());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.kenkyuNo";
//				<!-- ADD�@START 2007/07/09 BIS ���� -->					
				objKey 	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].kenkyuNo";
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->						
				//�{�o�^

				if(page >= 2){
					//�K�{�`�F�b�N
					//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//					if(StringUtil.isBlank(value)){
					if(StringUtil.isEscapeBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->								
					}else{
						//�d���`�F�b�N
						if(kenkyuNoSet.contains(value)){
							//���̑��u99999999�v�̂Ƃ��͏d���G���[�Ƃ��Ȃ�
							if(!"99999999".equals(value)){
								ActionError error = new ActionError("errors.5021",name);
								errors.add(property, error);
//								<!-- ADD�@START 2007/07/09 BIS ���� -->									
								errMap.put(objKey ,name);
//								<!-- ADD�@END�@ 2007/07/09 BIS ���� -->		
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
//							<!-- ADD�@START 2007/07/09 BIS ���� -->									
							errMap.put(objKey ,name);
//							<!-- ADD�@END�@ 2007/07/09 BIS ���� -->		
						//�������`�F�b�N
						}else if(value.length() != 8){
							ActionError error = new ActionError("errors.length",name,"8");
							errors.add(property, error);
//							<!-- ADD�@START 2007/07/09 BIS ���� -->									
							errMap.put(objKey ,name);
//							<!-- ADD�@END�@ 2007/07/09 BIS ���� -->		
						}
					}
				}
				kenkyushaInfo.setKenkyuNo(value);	//���p���l�ɕϊ������l���Z�b�g
				name	 = null;
				value	 = null;
				property = null;
//				<!-- ADD�@START 2007/07/09 BIS ���� -->					
				objKey 	 = null;
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->						
			}


			//-----�����i����-���j-----
			name	 = namePrefix+" �����i�������j�i���j "+cnt+"�s��";
			value	 = kenkyushaInfo.getNameKanjiSei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanjiSei";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey 	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].nameKanjiSei";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD�@START 2007/07/09 BIS ���� -->									
					errMap.put(objKey ,name);
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			


			//-----�����i����-���j-----
			name	 = namePrefix+" �����i�������j�i���j "+cnt+"�s��";
			value	 = kenkyushaInfo.getNameKanjiMei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanjiMei";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey 	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].nameKanjiMei";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//2005.10.18 iso ������̏ꍇ������̂ŁA�K�{�`�F�b�N�͍s��Ȃ��悤�ύX
//			//�{�o�^
//			if(page >= 2){
//				//�K�{�`�F�b�N
//				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
////				if(StringUtil.isBlank(value)){
//				if(StringUtil.isEscapeBlank(value)){
//					ActionError error = new ActionError("errors.required",name);
//					errors.add(property, error);
//				}
//			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			


			//-----�����i�t���K�i-���j-----
			name	 = namePrefix+" �����i�t���K�i�j�i���j "+cnt+"�s��";
			value	 = kenkyushaInfo.getNameKanaSei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanaSei";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].nameKanaSei";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD�@START 2007/07/09 BIS ���� -->									
					errMap.put(objKey ,name);
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�S�p�`�F�b�N
					if(!StringUtil.isZenkaku(value)){
						ActionError error = new ActionError("errors.mask_zenkaku",name);
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					//�ő啶�����`�F�b�N
					}else if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			


			//-----�����i�t���K�i-���j-----
			name	 = namePrefix+" �����i�t���K�i�j�i���j "+cnt+"�s��";
			value	 = kenkyushaInfo.getNameKanaMei();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nameKanaMei";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].nameKanaMei";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//2005.10.18 iso ������̏ꍇ������̂ŁA�K�{�`�F�b�N�͍s��Ȃ��悤�ύX
//			//�{�o�^
//			if(page >= 2){
//				//�K�{�`�F�b�N
//				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
////				if(StringUtil.isBlank(value)){
//				if(StringUtil.isEscapeBlank(value)){
//					ActionError error = new ActionError("errors.required",name);
//					errors.add(property, error);
//				}
//			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�S�p�`�F�b�N
					if(!StringUtil.isZenkaku(value)){
						ActionError error = new ActionError("errors.mask_zenkaku",name);
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					//�ő啶�����`�F�b�N
					}else if(value.length() > 16){
						ActionError error = new ActionError("errors.maxlength",name,"16");
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			


			//-----�N��-----
			name	 = namePrefix+" �N�� "+cnt+"�s��";
			value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getNenrei());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.nenrei";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].nenrei";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD�@START 2007/07/09 BIS ���� -->									
					errMap.put(objKey ,name);
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//���l�`�F�b�N
					if(!StringUtil.isDigit(value)){
						ActionError error = new ActionError("errors.numeric",name);
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					//�ő啶�����`�F�b�N
					}else if(value.length() > 2){
						ActionError error = new ActionError("errors.maxlength",name,"2");
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			kenkyushaInfo.setNenrei(value);		//���p���l�ɕϊ������l���Z�b�g
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			


			if(!kyoryokusha){
				//-----�����@�֖��i�R�[�h�j-----
				name	 = namePrefix+" ���������@�ցi�ԍ��j "+cnt+"�s��";
				value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getShozokuCd());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.shozokuCd";
//				<!-- ADD�@START 2007/07/09 BIS ���� -->	
				objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].shozokuCd";
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
				//�{�o�^
				if(page >= 2){
					//�K�{�`�F�b�N
					//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//					if(StringUtil.isBlank(value)){
					if(StringUtil.isEscapeBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
						kikanFlg = false;	//�@�փR�[�h���s���Ƃ���
					}
				}
				//�ꎞ�ۑ�, �{�o�^
				if(page >=0){
					if(!StringUtil.isBlank(value)){
						//���l�`�F�b�N
						if(!StringUtil.isDigit(value)){
							ActionError error = new ActionError("errors.numeric",name);
							errors.add(property, error);
//							<!-- ADD�@START 2007/07/09 BIS ���� -->									
							errMap.put(objKey ,name);
//							<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
							kikanFlg = false;	//�@�փR�[�h���s���Ƃ���
						//�������`�F�b�N
						}else if(value.length() != 5){
							ActionError error = new ActionError("errors.length",name,"5");
							errors.add(property, error);
//							<!-- ADD�@START 2007/07/09 BIS ���� -->									
							errMap.put(objKey ,name);
//							<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
							kikanFlg = false;	//�@�փR�[�h���s���Ƃ���
						}
					}
				}
				kenkyushaInfo.setShozokuCd(value);		//���p���l�ɕϊ������l���Z�b�g
				//��\�@�փR�[�h��ޔ�����
				if (i == 1){
					daihyoKikan = value;
				}
				name	 = null;
				value	 = null;
				property = null;
//				<!-- ADD�@START 2007/07/09 BIS ���� -->					
				objKey 	 = null;
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			
				
				//-----���ǖ��i�R�[�h�j-----
				name	 = namePrefix+" ���ǁi�ԍ��j "+cnt+"�s��";
				value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getBukyokuCd());
				property = "shinseiDataInfo.kenkyuSoshikiInfoList.bukyokuCd";
//				<!-- ADD�@START 2007/07/09 BIS ���� -->	
				objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].bukyokuCd";
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
				//�{�o�^
				if(page >= 2){
					//�K�{�`�F�b�N
					//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//					if(StringUtil.isBlank(value)){
					if(StringUtil.isEscapeBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
				//�ꎞ�ۑ�, �{�o�^
				if(page >=0){
					if(!StringUtil.isBlank(value)){
						//���l�`�F�b�N
						if(!StringUtil.isDigit(value)){
							ActionError error = new ActionError("errors.numeric",name);
							errors.add(property, error);
//							<!-- ADD�@START 2007/07/09 BIS ���� -->									
							errMap.put(objKey ,name);
//							<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
						//�������`�F�b�N
						}else if(value.length() != 3){
							ActionError error = new ActionError("errors.length",name,"3");
							errors.add(property, error);
//							<!-- ADD�@START 2007/07/09 BIS ���� -->									
							errMap.put(objKey ,name);
//							<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
						}
					}
				}
				kenkyushaInfo.setBukyokuCd(value);		//���p���l�ɕϊ������l���Z�b�g
				name	 = null;
				value	 = null;
				property = null;
//				<!-- ADD�@START 2007/07/09 BIS ���� -->					
				objKey 	 = null;
//				<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			
			}



			//-----���ǖ��i�a���j-----
			name	 = namePrefix+" ���ǁi�a���j "+cnt+"�s��";
			value	 = kenkyushaInfo.getBukyokuName();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.bukyokuName";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].bukyokuName";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 50){
						ActionError error = new ActionError("errors.maxlength",name,"50");
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			



			//-----�E���R�[�h-----
			name	 = namePrefix+" �E "+cnt+"�s��";
			value	 = kenkyushaInfo.getShokushuCd();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.shokushuCd";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].shokushuCd";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD�@START 2007/07/09 BIS ���� -->									
					errMap.put(objKey ,name);
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			


			//-----�E���i�a���j-----
			name	 = namePrefix+" �E "+cnt+"�s��";
			value	 = kenkyushaInfo.getShokushuNameKanji();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.shokushuNameKanji";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].shokushuNameKanji";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N�i�E��R�[�h���u���̑�(25)�v�̂Ƃ��j
				if("25".equals(kenkyushaInfo.getShokushuCd())){
					//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//					if(StringUtil.isBlank(value)){
					if(StringUtil.isEscapeBlank(value)){
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
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
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			

			//�������͎҂͈ȉ��̓��͊m�F���s��Ȃ�
			if(kyoryokusha){
				continue;
			}

			//-----���݂̐��-----
			name	 = namePrefix+" ���݂̐�� "+cnt+"�s��";
			value	 = kenkyushaInfo.getSenmon();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.senmon";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].senmon";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD�@START 2007/07/09 BIS ���� -->									
					errMap.put(objKey ,name);
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 20){
						ActionError error = new ActionError("errors.maxlength",name,"20");
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			


			//-----�w��-----
			name	 = namePrefix+" �w�� "+cnt+"�s��";
			value	 = kenkyushaInfo.getGakui();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.gakui";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].gakui";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD�@START 2007/07/09 BIS ���� -->									
					errMap.put(objKey ,name);
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 20){
						ActionError error = new ActionError("errors.maxlength",name,"20");
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			


			//-----�������S-----
			name	 = namePrefix+" �������S "+cnt+"�s��";
			value	 = kenkyushaInfo.getBuntan();
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.buntan";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].buntan";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD�@START 2007/07/09 BIS ���� -->									
					errMap.put(objKey ,name);
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
				}
			}
			//�ꎞ�ۑ�, �{�o�^
			if(page >=0){
				if(!StringUtil.isBlank(value)){
					//�ő啶�����`�F�b�N
					if(value.length() > 50){
						ActionError error = new ActionError("errors.maxlength",name,"50");
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			


			//-----�����o��-----
			name	 = namePrefix+" �����o�� "+cnt+"�s��";
			value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getKeihi());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].keihi";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			//�{�o�^
			if(page >= 2){
				//�K�{�`�F�b�N
				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
//UPDATE�@START 2007/07/23 BIS �����_  //�A�g�����ҁF�l�������Ă��Ȃ��ꍇ�̓`�F�b�N���Ȃ��B
			/*					
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD�@START 2007/07/09 BIS ���� -->									
					errMap.put(objKey ,name);
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
			 */		
					if(!"4".equals(kenkyushaInfo.getBuntanFlag())){						
						ActionError error = new ActionError("errors.required",name);
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
//UPDATE�@END�@ 2007/07/23 BIS �����_	
				}
				else{
					//������\�҈ȊO�̏ꍇ�̂݃`�F�b�N
//UPDATE�@START 2007/07/23 BIS �����_  //�A�g�����ҁF�l�������Ă��Ȃ��ꍇ�̓`�F�b�N���Ȃ��B					
//					if(!"1".equals(kenkyushaInfo.getBuntanFlag())){
					if("4".equals(kenkyushaInfo.getBuntanFlag())){
						ActionError error = new ActionError("errors.5076",name);
						errors.add(property, error);								
						errMap.put(objKey ,name);
						
					}
					else if(!"1".equals(kenkyushaInfo.getBuntanFlag())){
//UPDATE�@END�@ 2007/07/23 BIS �����_						
						//���S��������ꍇ
						if("1".equals(shinseiDataInfo.getBuntankinFlg())){
//							�`�F�b�N���[�����ύX�����ׁA�R�����g���� 2005/9/1
//							if(StringUtil.parseInt(value) == 0){
//								ActionError error = new ActionError("errors.5049",name);
//								errors.add(property, error);
//							}

							//���͋@�փR�[�h��������
							if (kikanFlg) {
								//�@�փR�[�h���擾����
								String kikanCd = StringUtil.toHankakuDigit(kenkyushaInfo.getShozokuCd());
								
								//��\�ҋ@�ւƈقȂ�ꍇ
								if (!daihyoKikan.equals(kikanCd)){
									//�o��̍��v�l���m�ۂ���
//									diffKikanTotalKeihi = diffKikanTotalKeihi + Long.parseLong(value);
									diffKikanTotalKeihi = diffKikanTotalKeihi + StringUtil.parseLong(value);
								}else{
//2007/02/07�@�����F�@�ǉ��@��������
                                    //���S�҂̏ꍇ�A�����@�փR�[�h����\�҂̏����@�փR�[�h�Ɠ����ꍇ�A�u�O�v�ȊO�̓G���[�B
								    if(StringUtil.parseInt(value) != 0){
								        ActionError error = new ActionError("errors.9030",name);
                                        errors.add(property, error);
//                						<!-- ADD�@START 2007/07/09 BIS ���� -->									
                						errMap.put(objKey ,name);
//                						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
								    }
//2007/02/07�@�����F�@�ǉ��@��������
                                }
							}
							
						}
						else{
							//���S�����Ȃ��ꍇ
							if(StringUtil.parseInt(value) != 0){
								ActionError error = new ActionError("errors.5050",name);
								errors.add(property, error);
//								<!-- ADD�@START 2007/07/09 BIS ���� -->									
								errMap.put(objKey ,name);
//								<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
							}
						}
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
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					//�ő啶�����`�F�b�N
					}else if(value.length() > 7){
						ActionError error = new ActionError("errors.maxlength",name,"7");
						errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					}
				}
			}
			kenkyushaInfo.setKeihi(value);	//���p���l�ɕϊ������l���Z�b�g
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			


			//-----�G�t�H�[�g-----
			name	 = namePrefix+" �G�t�H�[�g "+cnt+"�s��";
			value	 = StringUtil.toHankakuDigit(kenkyushaInfo.getEffort());
			property = "shinseiDataInfo.kenkyuSoshikiInfoList.effort";
//			<!-- ADD�@START 2007/07/09 BIS ���� -->	
			objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList["+cntKey+"].effort";
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
            
//2006/08/15 �c�@�ǉ���������
            String keihiNendo1 = shinseiDataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi6()[0].getKeihi();
            String jigyoCd = shinseiDataInfo.getJigyoCd();
//2006/08/15�@�c�@�ǉ������܂�            
			//�{�o�^
			if(page >= 2){
				// 20050728 �G�t�H�[�g�͑S�Ăɂ����ĕK�{�ł��邽�ߕ��S���̗L���Ɋւ��Ȃ�
				//2005.09.08 iso ���p�X�y�[�X�݂̂��K�{�`�F�b�N�ł͂����悤�ύX
//				if(StringUtil.isBlank(value)){
				if(StringUtil.isEscapeBlank(value)){
					ActionError error = new ActionError("errors.required",name);
					errors.add(property, error);
//					<!-- ADD�@START 2007/07/09 BIS ���� -->									
					errMap.put(objKey ,name);
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
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
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
//2006/08/15 �c�@�C����������
					//�͈̓`�F�b�N�i1�`100�j�@20006/08/15�@����i�V�K�j�݂̂ŁA��N�ڂ̌����o�0�̏ꍇ�A�u0�v�̂݋�����B                       
                    }else if(StringUtil.parseInt(value)<=0 || StringUtil.parseInt(value)>100){
                        if (!IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(jigyoCd)) {
                            ActionError error = new ActionError("errors.range",
                                    name, IShinseiMaintenance.EFFORT_MIN,
                                    IShinseiMaintenance.EFFORT_MAX);
                            errors.add(property, error);
//    						<!-- ADD�@START 2007/07/09 BIS ���� -->									
    						errMap.put(objKey ,name);
//    						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
                        } else if (StringUtil.parseInt(value) == 0 && 
                                StringUtil.parseInt(keihiNendo1) != 0) {
                            ActionError error = new ActionError("errors.range",
                                    name, IShinseiMaintenance.EFFORT_MIN,
                                    IShinseiMaintenance.EFFORT_MAX);
                            errors.add(property, error);
//    						<!-- ADD�@START 2007/07/09 BIS ���� -->									
    						errMap.put(objKey ,name);
//    						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
                        } else if (StringUtil.parseInt(value)>100){
                            ActionError error = new ActionError("errors.range",
                                    name,
                                    IShinseiMaintenance.EFFORT_MIN_SINNKI,
                                    IShinseiMaintenance.EFFORT_MAX);
                            errors.add(property, error);
//    						<!-- ADD�@START 2007/07/09 BIS ���� -->									
    						errMap.put(objKey ,name);
//    						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
                        }
                    //����i�V�K�j�݂̂ŁA��N�ڂ̌����o��u0�v�ł͂Ȃ��ꍇ�A�u0�v�����Ȃ��B
					}else if (StringUtil.parseInt(value) != 0 && "0".equals(keihiNendo1)
                            && IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(jigyoCd)) {
                        ActionError error = new ActionError("errors.5069", Integer.toString(cnt));
                        errors.add(property, error);
//						<!-- ADD�@START 2007/07/09 BIS ���� -->									
						errMap.put(objKey ,name);
//						<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
                    }
// 2006/08/15 �c �C�������܂�
				}
			}
			kenkyushaInfo.setEffort(value);		//���p���l�ɕϊ������l���Z�b�g
			name	 = null;
			value	 = null;
			property = null;
//			<!-- ADD�@START 2007/07/09 BIS ���� -->					
			objKey 	 = null;
//			<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			

		}
		//========== �����g�D�\�̃��X�g���J��Ԃ� �I��� ==========

		//���S���̔z�����L�̏ꍇ
		//2005.09.12 iso �o��`�F�b�N��{�o�^���݂̂ɏC��
		if(page >= 2) {
			if("1".equals(shinseiDataInfo.getBuntankinFlg())){
				//��\�҂ƈقȂ�@�ւ̕��S�Ҍo��̃g�[�^�����O�̏ꍇ�͂m�f
				if (diffKikanTotalKeihi == 0){
					name	 = namePrefix;
					property = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";
//					<!-- ADD�@START 2007/07/09 BIS ���� -->	
					objKey	 = "shinseiDataInfo.kenkyuSoshikiInfoList.keihi";;
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
					ActionError error = new ActionError("errors.5061",name);
					errors.add(property, error);
//					<!-- ADD�@START 2007/07/09 BIS ���� -->									
					errMap.put(objKey ,name);
//					<!-- ADD�@END�@ 2007/07/09 BIS ���� -->	
				}
			}
		}
		property = null;
//		<!-- ADD�@START 2007/07/09 BIS ���� -->					
		objKey 	 = null;
		shinseiDataInfo.setErrorsMap(errMap );
//		<!-- ADD�@END�@ 2007/07/09 BIS ���� -->			
	}
}