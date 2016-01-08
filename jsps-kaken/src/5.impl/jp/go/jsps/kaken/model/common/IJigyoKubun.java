/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/08/04
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.common;

/**
 * 事業区分を定義する。
 * 
 * ID RCSfile="$RCSfile: IJigyoKubun.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public interface IJigyoKubun {
	
	/** 学術創成（非公募） */
	public static final String JIGYO_KUBUN_GAKUSOU_HIKOUBO     = "1";
	
	/** 学術創成（公募） */
	public static final String JIGYO_KUBUN_GAKUSOU_KOUBO       = "2";
		
	/** 特別推進研究 */
	public static final String JIGYO_KUBUN_TOKUSUI             = "3";
	
	/** 基盤研究 */
	public static final String JIGYO_KUBUN_KIBAN               = "4";

	/** 特定領域研究 */
	public static final String JIGYO_KUBUN_TOKUTEI             = "5";

	/** 若手スタートアップ */
	public static final String JIGYO_KUBUN_WAKATESTART         = "6";
	
	/** 特別研究促進費 */
	public static final String JIGYO_KUBUN_SHOKUSHINHI         = "7";

	/** 基盤の様式数 */
	public static final int KIBAN_YOSHIKISU	=	10;
//2006.05.09 Modify By Sai
/*	public static final String[] SHINSA_KANOU_JIGYO_KUBUN	= {
		JIGYO_KUBUN_KIBAN, 
		JIGYO_KUBUN_WAKATESTART
	};*/
}
