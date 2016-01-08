/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : StatusCode.java
 *    Description : 申請書状況コード定義
 *
 *    Author      : takano
 *    Date        : 2004/01/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/01/20    V1.0        takano         新規作成
 *    2006/06/15    V1.0        DIS.dyh        修正（申請状況を追加）
 *    2006/06/16    V1.0        DIS.miaoM      修正（再申請フラグ：却下[3]を追加）
 *====================================================================== 
 */
package jp.go.jsps.kaken.status;

import java.util.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.web.common.*;

/**
 * 申請書状況コード定義。
 * ID RCSfile="$RCSfile: StatusCode.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:15 $"
 */
public class StatusCode {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 申請状況：「作成中」*/
	public static final String STATUS_SAKUSEITHU                    = "01";
	
	/** 申請状況：「申請書未確認」*/
    public static final String STATUS_SHINSEISHO_MIKAKUNIN          = "02";
	
	/** 申請状況：「所属機関受付中」*/
    public static final String STATUS_SHOZOKUKIKAN_UKETUKETYU       = "03";
	
	/** 申請状況：「学振処理中」*/
    public static final String STATUS_GAKUSIN_SHORITYU              = "04";

	/** 申請状況：「所属機関却下」*/
    public static final String STATUS_SHOZOKUKIKAN_KYAKKA           = "05";

	/** 申請状況：「学振受理」*/
    public static final String STATUS_GAKUSIN_JYURI                 = "06";

	/** 申請状況：「学振不受理」*/
    public static final String STATUS_GAKUSIN_FUJYURI               = "07";
	
	/** 申請状況：「審査員割り振り処理後」*/
    public static final String STATUS_SHINSAIN_WARIFURI_SHORIGO     = "08";
	
	/** 申請状況：「割り振りチェック完了」*/
    public static final String STATUS_WARIFURI_CHECK_KANRYO         = "09";
	
	/** 申請状況：「1次審査中」*/
    public static final String STATUS_1st_SHINSATYU                 = "10";

	/** 申請状況：「1次審査完了」*/
    public static final String STATUS_1st_SHINSA_KANRYO             = "11";

	/** 申請状況：「2次審査完了」*/
    public static final String STATUS_2nd_SHINSA_KANRYO             = "12";

// 2006/06/15 dyh add start
    /** 申請状況：「領域代表者確認中」 */
    public static final String  STATUS_RYOUIKIDAIHYOU_KAKUNIN       = "21";

    /** 申請状況：「領域代表者却下」 */
    public static final String  STATUS_RYOUIKIDAIHYOU_KYAKKA        = "22";

    /** 申請状況：「領域代表者確定済み」 */
    public static final String  STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI   = "23";

    /** 申請状況：「領域代表者所属研究機関受付中」 */
    public static final String  STATUS_RYOUIKISHOZOKU_UKETUKE       = "24";

    /** 申請状況：「仮領域番号確認待ち」 */
    public static final String  STATUS_KARIRYOIKINO_KAKUNINMATI     = "31";

    /** 申請状況：「仮領域番号発行却下」 */
    public static final String  STATUS_KARIRYOIKINO_HAKKUKYAKKA     = "32";

    /** 申請状況：「仮領域番号発行済み」 */
    public static final String  STATUS_KARIRYOIKINO_HAKKUZUMI       = "33";
// 2006/06/15 dyh add end

	/** 2次審査結果：「採択」*/
    public static final int    KEKKA2_SAITAKU                       = 1;
	
	/** 2次審査結果：「採用候補」*/
    public static final int    KEKKA2_SAIYOKOHO                     = 2;
	
	/** 2次審査結果：「補欠1」 */
    public static final int    KEKKA2_HOKETU1                       = 3;
	
	/** 2次審査結果：「補欠2」 */
    public static final int    KEKKA2_HOKETU2                       = 4;
	
	/** 2次審査結果：「補欠3」 */
    public static final int    KEKKA2_HOKETU3                       = 5;
	
	/** 2次審査結果：「補欠4」 */
    public static final int    KEKKA2_HOKETU4                       = 6;
	
	/** 2次審査結果：「補欠5」 */
    public static final int    KEKKA2_HOKETU5                       = 7;
	
	/** 2次審査結果：「不採択」 */
    public static final int    KEKKA2_FUSAITAKU                     = 8;
	
	/** 2次審査結果配列：「補欠1～5」の配列 */
    public static final int[]  KEKKA2_HOKETU_ARRAY  = new int[]{KEKKA2_HOKETU1,
                                                                KEKKA2_HOKETU2,
                                                                KEKKA2_HOKETU3,
                                                                KEKKA2_HOKETU4,
                                                                KEKKA2_HOKETU5};
	
	/** 2次審査結果文字列 */
	// 2次審査結果文字列をラベルマスタで管理する。
	// 変更が即反映されるようにするため、フィールドでは持たず毎回DBアクセスする。
//	private static final String[] KEKKA2_NAME_LIST   = new String[]{null,
//                                                                    "採択",
//                                                                    "採用候補",
//                                                                    "補欠1",
//                                                                    "補欠2",
//                                                                    "補欠3",
//                                                                    "補欠4",
//                                                                    "補欠5",
//                                                                    "不採択"};
	
	/** 再申請フラグ：初期値[0] */
    public static final String  SAISHINSEI_FLG_DEFAULT              = "0";
	
	/** 再申請フラグ：再申請中[1] */
    public static final String  SAISHINSEI_FLG_SAISHINSEITYU        = "1";
		
	/** 再申請フラグ：再申請済み[2] */
    public static final String  SAISHINSEI_FLG_SAISHINSEIZUMI       = "2";

// 2006/06/16 miao add start
    /** 再申請フラグ：却下[3] */
    public static final String  SAISHINSEI_FLG_KYAKKA               = "3"; 
// 2006/06/16 miao add end

	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------

    //---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
//    /**
//     * @return 申請状況：「作成中」(01)
//     */
//    public static String getSTATUS_SAKUSEITHU() {
//        return STATUS_SAKUSEITHU;
//    }
//
//    /**
//     * @return 申請状況：「申請書未確認」(02)
//     */
//    public static String getSTATUS_SHINSEISHO_MIKAKUNIN() {
//        return STATUS_SHINSEISHO_MIKAKUNIN;
//    }
//
//    /**
//     * @return 申請状況：「所属機関受付中」(03)
//     */
//    public static String getSTATUS_SHOZOKUKIKAN_UKETUKETYU() {
//        return STATUS_SHOZOKUKIKAN_UKETUKETYU;
//    }
//
//    /**
//     * @return 申請状況：「学振処理中」(04)
//     */
//    public static String getSTATUS_GAKUSIN_SHORITYU() {
//        return STATUS_GAKUSIN_SHORITYU;
//    }
//
//    /**
//     * @return 申請状況：「所属機関却下」(05)
//     */
//    public static String getSTATUS_SHOZOKUKIKAN_KYAKKA() {
//        return STATUS_SHOZOKUKIKAN_KYAKKA;
//    }
//
//    /**
//     * @return 申請状況：「学振受理」(06)
//     */
//    public static String getSTATUS_GAKUSIN_JYURI() {
//        return STATUS_GAKUSIN_JYURI;
//    }
//
//    /**
//     * @return 申請状況：「学振不受理」(07)
//     */
//    public static String getSTATUS_GAKUSIN_FUJYURI() {
//        return STATUS_GAKUSIN_FUJYURI;
//    }
//
//    /**
//     * @return 申請状況：「審査員割り振り処理後」(08)
//     */
//    public static String getSTATUS_SHINSAIN_WARIFURI_SHORIGO() {
//        return STATUS_SHINSAIN_WARIFURI_SHORIGO;
//    }
//
//    /**
//     * @return 申請状況：「割り振りチェック完了」(09)
//     */
//    public static String getSTATUS_WARIFURI_CHECK_KANRYO() {
//        return STATUS_WARIFURI_CHECK_KANRYO;
//    }
//
//    /**
//     * @return 申請状況：「1次審査中」(10)
//     */
//    public static String getSTATUS_1st_SHINSATYU() {
//        return STATUS_1st_SHINSATYU;
//    }
//
//    /**
//     * @return 申請状況：「1次審査完了」(11)
//     */
//    public static String getSTATUS_1st_SHINSA_KANRYO() {
//        return STATUS_1st_SHINSA_KANRYO;
//    }
//
//    /**
//     * @return 申請状況：「2次審査完了」(12)
//     */
//    public static String getSTATUS_2nd_SHINSA_KANRYO() {
//        return STATUS_2nd_SHINSA_KANRYO;
//    }
//
//// 2006/06/15 dyh add start
//    /**
//     * @return 申請状況：「領域代表者確認中」(21)
//     */
//    public static String getSTATUS_RYOUIKIDAIHYOU_KAKUNIN() {
//        return STATUS_RYOUIKIDAIHYOU_KAKUNIN;
//    }
//
//    /**
//     * @return 申請状況：「領域代表者却下」(22)
//     */
//    public static String getSTATUS_RYOUIKIDAIHYOU_KYAKKA() {
//        return STATUS_RYOUIKIDAIHYOU_KYAKKA;
//    }
//
//    /**
//     * @return 申請状況：「領域代表者確定済み」(23)
//     */
//    public static String getSTATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI() {
//        return STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI;
//    }
//
//    /**
//     * @return 申請状況：「領域代表者所属研究機関受付中」(24)
//     */
//    public static String getSTATUS_RYOUIKISHOZOKU_UKETUKE() {
//        return STATUS_RYOUIKISHOZOKU_UKETUKE;
//    }
//// 2006/06/15 add end

	/**
	 * 2次審査結果文字列を返す。
	 * 指定された2次審査結果コードに該当する2次審査結果文字列が存在しない場合は、
	 * 2次審査結果コードそのものを返す。
	 * @param kekka2Value
	 * @return
	 */
	public static String getKekka2Name(String kekka2Value){
		String kekka2Name = kekka2Value;  //初期値として2次審査結果コードをセットする
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
			System.out.println("2次審査結果を取得できませんでした。kekka2Value="+kekka2Value);
			e.printStackTrace();
		}
		return kekka2Name;
	}
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

//	/**
//	 * @return 2次審査結果：「補欠1」(3)
//	 */
//	public static int getKEKKA2_HOKETU1() {
//		return KEKKA2_HOKETU1;
//	}
//
//	/**
//	 * @return 2次審査結果：「補欠2」(4)
//	 */
//	public static int getKEKKA2_HOKETU2() {
//		return KEKKA2_HOKETU2;
//	}
//
//	/**
//	 * @return 2次審査結果：「補欠3」(5)
//	 */
//	public static int getKEKKA2_HOKETU3() {
//		return KEKKA2_HOKETU3;
//	}
//
//	/**
//	 * @return 2次審査結果：「補欠4」(6)
//	 */
//	public static int getKEKKA2_HOKETU4() {
//		return KEKKA2_HOKETU4;
//	}
//
//	/**
//	 * @return 2次審査結果：「補欠5」(7)
//	 */
//	public static int getKEKKA2_HOKETU5() {
//		return KEKKA2_HOKETU5;
//	}
//
//    /**
//     * @return 2次審査結果：「不採択」(8)
//     */
//    public static int getKEKKA2_FUSAITAKU() {
//        return KEKKA2_FUSAITAKU;
//    }
//
//	/**
//	 * @return 2次審査結果：「採択」(1)
//	 */
//	public static int getKEKKA2_SAITAKU() {
//		return KEKKA2_SAITAKU;
//	}
//
//	/**
//	 * @return 2次審査結果：「採用候補」(2)
//	 */
//	public static int getKEKKA2_SAIYOKOHO() {
//		return KEKKA2_SAIYOKOHO;
//	}
//
//	/**
//	 * @return 2次審査結果配列：「補欠1～5」の配列
//	 */
//	public static int[] getKEKKA2_HOKETU_ARRAY() {
//		return KEKKA2_HOKETU_ARRAY;
//	}
//
//	/**
//	 * @return 再申請フラグ：初期値[0]
//	 */
//	public static String getSAISHINSEI_FLG_DEFAULT() {
//		return SAISHINSEI_FLG_DEFAULT;
//	}
//
//	/**
//	 * @return 再申請フラグ：再申請中[1]
//	 */
//	public static String getSAISHINSEI_FLG_SAISHINSEITYU() {
//		return SAISHINSEI_FLG_SAISHINSEITYU;
//	}
//
//	/**
//	 * @return 再申請フラグ：再申請済み[2]
//	 */
//	public static String getSAISHINSEI_FLG_SAISHINSEIZUMI() {
//		return SAISHINSEI_FLG_SAISHINSEIZUMI;
//	}
//
//    /**
//     * @return 再申請フラグ：却下[3]
//     */
//    public static String getSAISHINSEI_FLG_KYAKKA() {
//        return SAISHINSEI_FLG_KYAKKA;
//    }
}