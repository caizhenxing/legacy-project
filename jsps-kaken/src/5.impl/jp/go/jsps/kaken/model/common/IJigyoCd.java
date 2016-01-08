/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Hashimoto
 *    Date        : 2005/04/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.common;

/**
 * 事業CDを定義する。
 */
public interface IJigyoCd {
	
	/** 学術創成（非公募） */
	public static final String JIGYO_CD_GAKUSOU_HIKOUBO     = "00521";
	
	/** 学術創成（公募） */
	public static final String JIGYO_CD_GAKUSOU_KOUBO       = "00522";
		
	/** 特別推進研究 */
	public static final String JIGYO_CD_TOKUSUI             = "00011";
	
	/** 基盤研究(S) */
	public static final String JIGYO_CD_KIBAN_S             = "00031";
	
	/** 基盤研究(A)(一般) */
	public static final String JIGYO_CD_KIBAN_A_IPPAN       = "00041";
	
	/** 基盤研究(A)(海外学術調査) */
	public static final String JIGYO_CD_KIBAN_A_KAIGAI      = "00043";
	
	/** 基盤研究(B)(一般) */
	public static final String JIGYO_CD_KIBAN_B_IPPAN       = "00051";
	
	/** 基盤研究(B)(海外学術調査) */
	public static final String JIGYO_CD_KIBAN_B_KAIGAI      = "00053";
	
	/** 基盤研究(C)(一般) */
	public static final String JIGYO_CD_KIBAN_C_IPPAN       = "00061";
	
	/** 基盤研究(C)(企画調査) */
	public static final String JIGYO_CD_KIBAN_C_KIKAKU      = "00062";
	
	/** 萌芽研究 */
	public static final String JIGYO_CD_KIBAN_HOGA          = "00111";
	
	/** 若手研究(A) */
	public static final String JIGYO_CD_KIBAN_WAKATE_A      = "00121";
	
	/** 若手研究(B) */
	public static final String JIGYO_CD_KIBAN_WAKATE_B      = "00131";
    
//2007/02/03 苗　追加ここから
    /** 若手研究(S) */
    public static final String JIGYO_CD_KIBAN_WAKATE_S      = "00120";
//2007/02/03 苗　追加ここまで
	
//2006/06/14 苗　修正ここから
    // 20050601 Start 特定領域
	/** 特定領域 */
//	public static final String IJigyoCd.JIGYO_CD_TOKUTEI				= "00021";
    public static final String JIGYO_CD_TOKUTEI_KEIZOKU     = "00021";
    // Horikoshi End
//2006/06/14 苗　修正ここまで  
    
//2006/06/14 追加ここから
    /** 特定領域研究（新規領域） */
    public static final String JIGYO_CD_TOKUTEI_SINKI       = "00022";        
//　苗　追加ここまで     
	
	/** 若手研究（スタートアップ） */
// 2005/02/09   
	public static final String JIGYO_CD_WAKATESTART         = "00141";
// Syuu End	
	
// 2006/02/10 Start
	/** 特別研究促進費（基盤研究(A)相当） */
	public static final String JIGYO_CD_SHOKUSHINHI_KIBAN_A = "00152";

	/** 特別研究促進費（基盤研究(B)相当） */
	public static final String JIGYO_CD_SHOKUSHINHI_KIBAN_B = "00153";

	/** 特別研究促進費（基盤研究(C)相当） */
	public static final String JIGYO_CD_SHOKUSHINHI_KIBAN_C = "00154";

	/** 特別研究促進費（若手研究(A)相当） */
	public static final String JIGYO_CD_SHOKUSHINHI_WAKATE_A = "00155";

	/** 特別研究促進費（若手研究(B)相当） */
	public static final String JIGYO_CD_SHOKUSHINHI_WAKATE_B = "00156";
// Byou End       
}
