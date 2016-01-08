/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : StatusCode.java
 *    Description : �\�����󋵃R�[�h��`
 *
 *    Author      : takano
 *    Date        : 2004/01/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/01/20    V1.0        takano         �V�K�쐬
 *    2006/06/15    V1.0        DIS.dyh        �C���i�\���󋵂�ǉ��j
 *    2006/06/16    V1.0        DIS.miaoM      �C���i�Đ\���t���O�F�p��[3]��ǉ��j
 *====================================================================== 
 */
package jp.go.jsps.kaken.status;

import java.util.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.web.common.*;

/**
 * �\�����󋵃R�[�h��`�B
 * ID RCSfile="$RCSfile: StatusCode.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:15 $"
 */
public class StatusCode {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** �\���󋵁F�u�쐬���v*/
	public static final String STATUS_SAKUSEITHU                    = "01";
	
	/** �\���󋵁F�u�\�������m�F�v*/
    public static final String STATUS_SHINSEISHO_MIKAKUNIN          = "02";
	
	/** �\���󋵁F�u�����@�֎�t���v*/
    public static final String STATUS_SHOZOKUKIKAN_UKETUKETYU       = "03";
	
	/** �\���󋵁F�u�w�U�������v*/
    public static final String STATUS_GAKUSIN_SHORITYU              = "04";

	/** �\���󋵁F�u�����@�֋p���v*/
    public static final String STATUS_SHOZOKUKIKAN_KYAKKA           = "05";

	/** �\���󋵁F�u�w�U�󗝁v*/
    public static final String STATUS_GAKUSIN_JYURI                 = "06";

	/** �\���󋵁F�u�w�U�s�󗝁v*/
    public static final String STATUS_GAKUSIN_FUJYURI               = "07";
	
	/** �\���󋵁F�u�R��������U�菈����v*/
    public static final String STATUS_SHINSAIN_WARIFURI_SHORIGO     = "08";
	
	/** �\���󋵁F�u����U��`�F�b�N�����v*/
    public static final String STATUS_WARIFURI_CHECK_KANRYO         = "09";
	
	/** �\���󋵁F�u1���R�����v*/
    public static final String STATUS_1st_SHINSATYU                 = "10";

	/** �\���󋵁F�u1���R�������v*/
    public static final String STATUS_1st_SHINSA_KANRYO             = "11";

	/** �\���󋵁F�u2���R�������v*/
    public static final String STATUS_2nd_SHINSA_KANRYO             = "12";

// 2006/06/15 dyh add start
    /** �\���󋵁F�u�̈��\�Ҋm�F���v */
    public static final String  STATUS_RYOUIKIDAIHYOU_KAKUNIN       = "21";

    /** �\���󋵁F�u�̈��\�ҋp���v */
    public static final String  STATUS_RYOUIKIDAIHYOU_KYAKKA        = "22";

    /** �\���󋵁F�u�̈��\�Ҋm��ς݁v */
    public static final String  STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI   = "23";

    /** �\���󋵁F�u�̈��\�ҏ��������@�֎�t���v */
    public static final String  STATUS_RYOUIKISHOZOKU_UKETUKE       = "24";

    /** �\���󋵁F�u���̈�ԍ��m�F�҂��v */
    public static final String  STATUS_KARIRYOIKINO_KAKUNINMATI     = "31";

    /** �\���󋵁F�u���̈�ԍ����s�p���v */
    public static final String  STATUS_KARIRYOIKINO_HAKKUKYAKKA     = "32";

    /** �\���󋵁F�u���̈�ԍ����s�ς݁v */
    public static final String  STATUS_KARIRYOIKINO_HAKKUZUMI       = "33";
// 2006/06/15 dyh add end

	/** 2���R�����ʁF�u�̑��v*/
    public static final int    KEKKA2_SAITAKU                       = 1;
	
	/** 2���R�����ʁF�u�̗p���v*/
    public static final int    KEKKA2_SAIYOKOHO                     = 2;
	
	/** 2���R�����ʁF�u�⌇1�v */
    public static final int    KEKKA2_HOKETU1                       = 3;
	
	/** 2���R�����ʁF�u�⌇2�v */
    public static final int    KEKKA2_HOKETU2                       = 4;
	
	/** 2���R�����ʁF�u�⌇3�v */
    public static final int    KEKKA2_HOKETU3                       = 5;
	
	/** 2���R�����ʁF�u�⌇4�v */
    public static final int    KEKKA2_HOKETU4                       = 6;
	
	/** 2���R�����ʁF�u�⌇5�v */
    public static final int    KEKKA2_HOKETU5                       = 7;
	
	/** 2���R�����ʁF�u�s�̑��v */
    public static final int    KEKKA2_FUSAITAKU                     = 8;
	
	/** 2���R�����ʔz��F�u�⌇1�`5�v�̔z�� */
    public static final int[]  KEKKA2_HOKETU_ARRAY  = new int[]{KEKKA2_HOKETU1,
                                                                KEKKA2_HOKETU2,
                                                                KEKKA2_HOKETU3,
                                                                KEKKA2_HOKETU4,
                                                                KEKKA2_HOKETU5};
	
	/** 2���R�����ʕ����� */
	// 2���R�����ʕ���������x���}�X�^�ŊǗ�����B
	// �ύX�������f�����悤�ɂ��邽�߁A�t�B�[���h�ł͎���������DB�A�N�Z�X����B
//	private static final String[] KEKKA2_NAME_LIST   = new String[]{null,
//                                                                    "�̑�",
//                                                                    "�̗p���",
//                                                                    "�⌇1",
//                                                                    "�⌇2",
//                                                                    "�⌇3",
//                                                                    "�⌇4",
//                                                                    "�⌇5",
//                                                                    "�s�̑�"};
	
	/** �Đ\���t���O�F�����l[0] */
    public static final String  SAISHINSEI_FLG_DEFAULT              = "0";
	
	/** �Đ\���t���O�F�Đ\����[1] */
    public static final String  SAISHINSEI_FLG_SAISHINSEITYU        = "1";
		
	/** �Đ\���t���O�F�Đ\���ς�[2] */
    public static final String  SAISHINSEI_FLG_SAISHINSEIZUMI       = "2";

// 2006/06/16 miao add start
    /** �Đ\���t���O�F�p��[3] */
    public static final String  SAISHINSEI_FLG_KYAKKA               = "3"; 
// 2006/06/16 miao add end

	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------

    //---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
//    /**
//     * @return �\���󋵁F�u�쐬���v(01)
//     */
//    public static String getSTATUS_SAKUSEITHU() {
//        return STATUS_SAKUSEITHU;
//    }
//
//    /**
//     * @return �\���󋵁F�u�\�������m�F�v(02)
//     */
//    public static String getSTATUS_SHINSEISHO_MIKAKUNIN() {
//        return STATUS_SHINSEISHO_MIKAKUNIN;
//    }
//
//    /**
//     * @return �\���󋵁F�u�����@�֎�t���v(03)
//     */
//    public static String getSTATUS_SHOZOKUKIKAN_UKETUKETYU() {
//        return STATUS_SHOZOKUKIKAN_UKETUKETYU;
//    }
//
//    /**
//     * @return �\���󋵁F�u�w�U�������v(04)
//     */
//    public static String getSTATUS_GAKUSIN_SHORITYU() {
//        return STATUS_GAKUSIN_SHORITYU;
//    }
//
//    /**
//     * @return �\���󋵁F�u�����@�֋p���v(05)
//     */
//    public static String getSTATUS_SHOZOKUKIKAN_KYAKKA() {
//        return STATUS_SHOZOKUKIKAN_KYAKKA;
//    }
//
//    /**
//     * @return �\���󋵁F�u�w�U�󗝁v(06)
//     */
//    public static String getSTATUS_GAKUSIN_JYURI() {
//        return STATUS_GAKUSIN_JYURI;
//    }
//
//    /**
//     * @return �\���󋵁F�u�w�U�s�󗝁v(07)
//     */
//    public static String getSTATUS_GAKUSIN_FUJYURI() {
//        return STATUS_GAKUSIN_FUJYURI;
//    }
//
//    /**
//     * @return �\���󋵁F�u�R��������U�菈����v(08)
//     */
//    public static String getSTATUS_SHINSAIN_WARIFURI_SHORIGO() {
//        return STATUS_SHINSAIN_WARIFURI_SHORIGO;
//    }
//
//    /**
//     * @return �\���󋵁F�u����U��`�F�b�N�����v(09)
//     */
//    public static String getSTATUS_WARIFURI_CHECK_KANRYO() {
//        return STATUS_WARIFURI_CHECK_KANRYO;
//    }
//
//    /**
//     * @return �\���󋵁F�u1���R�����v(10)
//     */
//    public static String getSTATUS_1st_SHINSATYU() {
//        return STATUS_1st_SHINSATYU;
//    }
//
//    /**
//     * @return �\���󋵁F�u1���R�������v(11)
//     */
//    public static String getSTATUS_1st_SHINSA_KANRYO() {
//        return STATUS_1st_SHINSA_KANRYO;
//    }
//
//    /**
//     * @return �\���󋵁F�u2���R�������v(12)
//     */
//    public static String getSTATUS_2nd_SHINSA_KANRYO() {
//        return STATUS_2nd_SHINSA_KANRYO;
//    }
//
//// 2006/06/15 dyh add start
//    /**
//     * @return �\���󋵁F�u�̈��\�Ҋm�F���v(21)
//     */
//    public static String getSTATUS_RYOUIKIDAIHYOU_KAKUNIN() {
//        return STATUS_RYOUIKIDAIHYOU_KAKUNIN;
//    }
//
//    /**
//     * @return �\���󋵁F�u�̈��\�ҋp���v(22)
//     */
//    public static String getSTATUS_RYOUIKIDAIHYOU_KYAKKA() {
//        return STATUS_RYOUIKIDAIHYOU_KYAKKA;
//    }
//
//    /**
//     * @return �\���󋵁F�u�̈��\�Ҋm��ς݁v(23)
//     */
//    public static String getSTATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI() {
//        return STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI;
//    }
//
//    /**
//     * @return �\���󋵁F�u�̈��\�ҏ��������@�֎�t���v(24)
//     */
//    public static String getSTATUS_RYOUIKISHOZOKU_UKETUKE() {
//        return STATUS_RYOUIKISHOZOKU_UKETUKE;
//    }
//// 2006/06/15 add end

	/**
	 * 2���R�����ʕ������Ԃ��B
	 * �w�肳�ꂽ2���R�����ʃR�[�h�ɊY������2���R�����ʕ����񂪑��݂��Ȃ��ꍇ�́A
	 * 2���R�����ʃR�[�h���̂��̂�Ԃ��B
	 * @param kekka2Value
	 * @return
	 */
	public static String getKekka2Name(String kekka2Value){
		String kekka2Name = kekka2Value;  //�����l�Ƃ���2���R�����ʃR�[�h���Z�b�g����
		try{
			List kekka2List = LabelValueManager.getAllShinsaKekka2ndList();
			for(int i=0; i<kekka2List.size(); i++){
				LabelValueBean bean = (LabelValueBean)kekka2List.get(i);
				if(bean.getValue().equals(kekka2Value)){
					kekka2Name = bean.getLabel();
					break;
				}
			}
		}catch(ApplicationException e){
			System.out.println("2���R�����ʂ��擾�ł��܂���ł����Bkekka2Value="+kekka2Value);
			e.printStackTrace();
		}
		return kekka2Name;
	}
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

//	/**
//	 * @return 2���R�����ʁF�u�⌇1�v(3)
//	 */
//	public static int getKEKKA2_HOKETU1() {
//		return KEKKA2_HOKETU1;
//	}
//
//	/**
//	 * @return 2���R�����ʁF�u�⌇2�v(4)
//	 */
//	public static int getKEKKA2_HOKETU2() {
//		return KEKKA2_HOKETU2;
//	}
//
//	/**
//	 * @return 2���R�����ʁF�u�⌇3�v(5)
//	 */
//	public static int getKEKKA2_HOKETU3() {
//		return KEKKA2_HOKETU3;
//	}
//
//	/**
//	 * @return 2���R�����ʁF�u�⌇4�v(6)
//	 */
//	public static int getKEKKA2_HOKETU4() {
//		return KEKKA2_HOKETU4;
//	}
//
//	/**
//	 * @return 2���R�����ʁF�u�⌇5�v(7)
//	 */
//	public static int getKEKKA2_HOKETU5() {
//		return KEKKA2_HOKETU5;
//	}
//
//    /**
//     * @return 2���R�����ʁF�u�s�̑��v(8)
//     */
//    public static int getKEKKA2_FUSAITAKU() {
//        return KEKKA2_FUSAITAKU;
//    }
//
//	/**
//	 * @return 2���R�����ʁF�u�̑��v(1)
//	 */
//	public static int getKEKKA2_SAITAKU() {
//		return KEKKA2_SAITAKU;
//	}
//
//	/**
//	 * @return 2���R�����ʁF�u�̗p���v(2)
//	 */
//	public static int getKEKKA2_SAIYOKOHO() {
//		return KEKKA2_SAIYOKOHO;
//	}
//
//	/**
//	 * @return 2���R�����ʔz��F�u�⌇1�`5�v�̔z��
//	 */
//	public static int[] getKEKKA2_HOKETU_ARRAY() {
//		return KEKKA2_HOKETU_ARRAY;
//	}
//
//	/**
//	 * @return �Đ\���t���O�F�����l[0]
//	 */
//	public static String getSAISHINSEI_FLG_DEFAULT() {
//		return SAISHINSEI_FLG_DEFAULT;
//	}
//
//	/**
//	 * @return �Đ\���t���O�F�Đ\����[1]
//	 */
//	public static String getSAISHINSEI_FLG_SAISHINSEITYU() {
//		return SAISHINSEI_FLG_SAISHINSEITYU;
//	}
//
//	/**
//	 * @return �Đ\���t���O�F�Đ\���ς�[2]
//	 */
//	public static String getSAISHINSEI_FLG_SAISHINSEIZUMI() {
//		return SAISHINSEI_FLG_SAISHINSEIZUMI;
//	}
//
//    /**
//     * @return �Đ\���t���O�F�p��[3]
//     */
//    public static String getSAISHINSEI_FLG_KYAKKA() {
//        return SAISHINSEI_FLG_KYAKKA;
//    }
}