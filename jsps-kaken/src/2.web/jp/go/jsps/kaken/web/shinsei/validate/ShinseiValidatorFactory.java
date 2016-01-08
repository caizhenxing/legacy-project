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

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.vo.*;

/**
 * �\�����͌`���`�F�b�N�I�u�W�F�N�g�����N���X
 * ID RCSfile="$RCSfile: ShinseiValidatorFactory.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:08 $"
 */
public class ShinseiValidatorFactory {
	
	
	/**
	 * �`���`�F�b�N�I�u�W�F�N�g�������\�b�h
	 * �\����񂲂Ƃ̌`���`�F�b�N�I�u�W�F�N�g��Ԃ��B
	 * ����͎��Ƌ敪�܂��͎���CD���ƂɐU�蕪����B
	 * @param shinseiDataInfo
	 * @return
	 */
	public static IShinseiValidator getShinseiValidator(ShinseiDataInfo shinseiDataInfo){
		
		String jigyoCd = shinseiDataInfo.getJigyoCd();
		
		//-----�w�n�i�����j�̏ꍇ-----
		if(IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO.equals(jigyoCd)){
			return new ShinseiValidatorGakusou(shinseiDataInfo);
			
	    //-----�w�n�i����j�̏ꍇ-----
		}else if(IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO.equals(jigyoCd)){
			return new ShinseiValidatorGakusou(shinseiDataInfo);
		
		//-----�����̏ꍇ-----
		}else if(IJigyoCd.JIGYO_CD_TOKUSUI.equals(jigyoCd)){
			return new ShinseiValidatorTokusui(shinseiDataInfo);
		
		//-----��Ղ̏ꍇ�iS�j-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyoCd)){
			return new ShinseiValidatorKibanS(shinseiDataInfo);
			
		//-----��Ղ̏ꍇ�iA��ʁj-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyoCd)){
			return new ShinseiValidatorKibanAIppan(shinseiDataInfo);
			
		//-----��Ղ̏ꍇ�iA�C�O�j-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyoCd)){
			return new ShinseiValidatorKibanAKaigai(shinseiDataInfo);
			
		//-----��Ղ̏ꍇ�iB��ʁj-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyoCd)){
			return new ShinseiValidatorKibanBIppan(shinseiDataInfo);
			
		//-----��Ղ̏ꍇ�iB�C�O�j-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyoCd)){
			return new ShinseiValidatorKibanBKaigai(shinseiDataInfo);
			
		//-----��Ղ̏ꍇ�iC��ʁj-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd)){
			return new ShinseiValidatorKibanCIppan(shinseiDataInfo);
			
		//-----��Ղ̏ꍇ�iC���j-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd)){
			return new ShinseiValidatorKibanCKikaku(shinseiDataInfo);
			
		//-----��Ղ̏ꍇ�i�G��j-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyoCd)){
			return new ShinseiValidatorKibanHoga(shinseiDataInfo);
			
		//-----��Ղ̏ꍇ�i���A�j-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd)){
			return new ShinseiValidatorKibanWakateA(shinseiDataInfo);
			
		//-----��Ղ̏ꍇ�i���B�j-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)){
			return new ShinseiValidatorKibanWakateB(shinseiDataInfo);
		}
//2007/02/03 �c�@�ǉ���������        
        //-----��Ղ̏ꍇ�i���S�j-----
        else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(jigyoCd)){
            return new ShinseiValidatorKibanWakateS(shinseiDataInfo);
        }
//2007/02/03 �c�@�ǉ������܂�        
// 20050601 Start
		else if(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU.equals(jigyoCd)){
			return new ShinseiValidatorTokutei(shinseiDataInfo);
		}
// Horikoshi End
		//-----��茤��(�X�^�[�g�A�b�v)-----
		else if(IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyoCd)){
			return new ShinseiValidatorWakatestart(shinseiDataInfo);
		}
// 2006/02/10 Start
		//-----���ʌ������i��̏ꍇ�i���A�j-----
		else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyoCd)){
			return new ShinseiValidatorShokushinhiKibanA(shinseiDataInfo);
		}
		//-----���ʌ������i��̏ꍇ�i���B�j-----
		else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyoCd)){
			return new ShinseiValidatorShokushinhiKibanB(shinseiDataInfo);
		}
		//-----���ʌ������i��̏ꍇ�i���C�j-----
		else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyoCd)){
			return new ShinseiValidatorShokushinhiKibanC(shinseiDataInfo);
		}
		//-----���ʌ������i��̏ꍇ�i���A�j-----
		else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyoCd)){
			return new ShinseiValidatorShokushinhiWakateA(shinseiDataInfo);
		}
		//-----���ʌ������i��̏ꍇ�i���B�j-----
		else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyoCd)){
			return new ShinseiValidatorShokushinhiWakateB(shinseiDataInfo);
		}
// Byou End
// 2006/06/14 �ǉ���������
        //-----����̈挤���i�V�K�̈�j�̏ꍇ-----
        else if(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(jigyoCd)){
            return new ShinseiValidatorTokuteiSinki(shinseiDataInfo);
        }
//�@�c�@�ǉ������܂�        
		//-----����ȊO�̏ꍇ�i�b��I�ɋ�I�u�W�F�N�g��Ԃ��B�j-----
		else{
			return new DefaultShinseiValidator(shinseiDataInfo);
		}
	}
	
	
	
}
