package jp.go.jsps.kaken.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * チェックデジット情報データアクセスクラス。
 * ID RCSfile="$RCSfile: CheckDiditUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class CheckDiditUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(CheckDiditUtil.class);
	
	/** チェックデジットの形式（数字） */
	public static final String FORM_NUM  = "form_num";

	/** チェックデジットの形式（アルファベット） */
	public static final String FORM_ALP  = "form_alp";

	//---------------------------------------------------------------------
	// Static Methods
	//---------------------------------------------------------------------

	/**
	 * チェックデジットを取得する。チェックデジットの生成方法はモジュラス10のウエイト3とする。
	 * チェックデジットの形式は、数字で取得する。
	 * @param data 				モジュラス10で算出するデータ
	 * @return	チェックデジット			
	 */
	public static String getCheckDigit(String data) {
		
		return getCheckDigit(data, CheckDiditUtil.FORM_NUM);

	}
	
	/**
	 * チェックデジットを取得する。チェックデジットの生成方法はモジュラス10のウエイト3とする。
	 * @param data 			モジュラス10で算出するデータ
	 * @param form 			チェックデジットの形式。
	 *                          CheckDiditUtil.FORM_NUM（数字）またはCheckDiditUtil.FORM_ALP（アルファベット）。
	 * @return	チェックデジット			
	 */
	public static String getCheckDigit(String data , String form) {
			
		//チェックデジット
		int digit = 0;
			
		//ウエイト
		int[] weight = {3,1};
		

		//文字列を文字数の数だけ分割し、String型の配列で返す
		int count = data.length();
		String[] result = new String[count];
		
		for (int i = 0; i < count; i++){
			result[i] = data.substring(i,i+1);						
		}	
	
		//総和
		int sum = 0;
		//ウエイトの選択カウント
		int weightCount = 0;
		
		//データの末尾の桁からウエイトを3/1/3/1とかけていき総和を求める。
		for(int i = result.length-1; i >= 0; i--){
			//仮に数値として認識できない場合は処理を飛ばす
			if(StringUtil.isDigit(result[i])){
				sum = sum + (Integer.parseInt(result[i]) * weight[weightCount]);
			}
			weightCount++;
			//ウエイトカウントをリセット
			if(weightCount == 2){
				weightCount = 0;
			}
		}
		
		//総和を10で割りその余りを求める。
		int surplus = sum%10;		
		
		//10より余りを引いた値がチェックデジット
		if(surplus == 0){
			//余りが0の場合はチェックデジット「0」			
		}else{
			digit = 10-surplus;
		}
		
		String strDigit = null;
		
		if((CheckDiditUtil.FORM_NUM).equals(form)){
			//数字形式
			strDigit =  Integer.toString(digit);			
		}else if((CheckDiditUtil.FORM_ALP).equals(form)){
			//アルファベット形式
			strDigit = convertCheckDigit(digit);			
		}else{
			//それ以外の場合は、そのまま返す
			strDigit = Integer.toString(digit);						
		}
		
		return strDigit;
	}	

	/**
	 * 半角数字1～9までのチェックデジットをアルファベットに変換する。
	 * 引数で渡されたチェックデジットが半角数字1～9ではない場合は、数字を文字列に変換して返す。
	 * @param data 				半角数字1～9までのチェックデジット
	 * @return	アルファベットに変換されたチェックデジット			
	 */
	public static String convertCheckDigit(int data) {
		String digit = null;	

		String[] alp = {"A", "B", "C", "E", "F", "H", "J", "K", "L", "M"}; 
		
		try{
			digit = alp[data];
		}catch(ArrayIndexOutOfBoundsException e){
			digit = new Integer(data).toString();
		}
		
		return digit;
	}

}
