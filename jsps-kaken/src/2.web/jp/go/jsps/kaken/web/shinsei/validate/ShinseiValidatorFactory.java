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
 * 申請入力形式チェックオブジェクト生成クラス
 * ID RCSfile="$RCSfile: ShinseiValidatorFactory.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:08 $"
 */
public class ShinseiValidatorFactory {
	
	
	/**
	 * 形式チェックオブジェクト生成メソッド
	 * 申請情報ごとの形式チェックオブジェクトを返す。
	 * 今回は事業区分または事業CDごとに振り分ける。
	 * @param shinseiDataInfo
	 * @return
	 */
	public static IShinseiValidator getShinseiValidator(ShinseiDataInfo shinseiDataInfo){
		
		String jigyoCd = shinseiDataInfo.getJigyoCd();
		
		//-----学創（非公募）の場合-----
		if(IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO.equals(jigyoCd)){
			return new ShinseiValidatorGakusou(shinseiDataInfo);
			
	    //-----学創（公募）の場合-----
		}else if(IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO.equals(jigyoCd)){
			return new ShinseiValidatorGakusou(shinseiDataInfo);
		
		//-----特推の場合-----
		}else if(IJigyoCd.JIGYO_CD_TOKUSUI.equals(jigyoCd)){
			return new ShinseiValidatorTokusui(shinseiDataInfo);
		
		//-----基盤の場合（S）-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyoCd)){
			return new ShinseiValidatorKibanS(shinseiDataInfo);
			
		//-----基盤の場合（A一般）-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyoCd)){
			return new ShinseiValidatorKibanAIppan(shinseiDataInfo);
			
		//-----基盤の場合（A海外）-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyoCd)){
			return new ShinseiValidatorKibanAKaigai(shinseiDataInfo);
			
		//-----基盤の場合（B一般）-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyoCd)){
			return new ShinseiValidatorKibanBIppan(shinseiDataInfo);
			
		//-----基盤の場合（B海外）-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyoCd)){
			return new ShinseiValidatorKibanBKaigai(shinseiDataInfo);
			
		//-----基盤の場合（C一般）-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd)){
			return new ShinseiValidatorKibanCIppan(shinseiDataInfo);
			
		//-----基盤の場合（C企画）-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd)){
			return new ShinseiValidatorKibanCKikaku(shinseiDataInfo);
			
		//-----基盤の場合（萌芽）-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyoCd)){
			return new ShinseiValidatorKibanHoga(shinseiDataInfo);
			
		//-----基盤の場合（若手A）-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd)){
			return new ShinseiValidatorKibanWakateA(shinseiDataInfo);
			
		//-----基盤の場合（若手B）-----
		}else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)){
			return new ShinseiValidatorKibanWakateB(shinseiDataInfo);
		}
//2007/02/03 苗　追加ここから        
        //-----基盤の場合（若手S）-----
        else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(jigyoCd)){
            return new ShinseiValidatorKibanWakateS(shinseiDataInfo);
        }
//2007/02/03 苗　追加ここまで        
// 20050601 Start
		else if(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU.equals(jigyoCd)){
			return new ShinseiValidatorTokutei(shinseiDataInfo);
		}
// Horikoshi End
		//-----若手研究(スタートアップ)-----
		else if(IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyoCd)){
			return new ShinseiValidatorWakatestart(shinseiDataInfo);
		}
// 2006/02/10 Start
		//-----特別研究促進費の場合（基盤A）-----
		else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyoCd)){
			return new ShinseiValidatorShokushinhiKibanA(shinseiDataInfo);
		}
		//-----特別研究促進費の場合（基盤B）-----
		else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyoCd)){
			return new ShinseiValidatorShokushinhiKibanB(shinseiDataInfo);
		}
		//-----特別研究促進費の場合（基盤C）-----
		else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyoCd)){
			return new ShinseiValidatorShokushinhiKibanC(shinseiDataInfo);
		}
		//-----特別研究促進費の場合（若手A）-----
		else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyoCd)){
			return new ShinseiValidatorShokushinhiWakateA(shinseiDataInfo);
		}
		//-----特別研究促進費の場合（若手B）-----
		else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyoCd)){
			return new ShinseiValidatorShokushinhiWakateB(shinseiDataInfo);
		}
// Byou End
// 2006/06/14 追加ここから
        //-----特定領域研究（新規領域）の場合-----
        else if(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(jigyoCd)){
            return new ShinseiValidatorTokuteiSinki(shinseiDataInfo);
        }
//　苗　追加ここまで        
		//-----それ以外の場合（暫定的に空オブジェクトを返す。）-----
		else{
			return new DefaultShinseiValidator(shinseiDataInfo);
		}
	}
	
	
	
}
