/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.util;

import java.text.*;
import java.util.*;

import jp.go.jsps.kaken.util.*;

/**
 * 申請のフォーマットを行う。
 * ID RCSfile="$RCSfile: ShinseiFormat.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:59 $"
 */
public class ShinseiFormat {

	private static final String HYPHEN = "-";

	/**
	 * 事業IDから申請種別を取得する.<br/>
	 * 
	 * 申請種別の定義は以下の通り。<br/>
	 * <table bgcolor="#000000">
	 * <tr bgcolor="#333333"><td><div style="color:white"><b>申請種別</b></div></td><td><div style="color:white"><b>事業</b></div></td></tr>
	 * <tr bgcolor="#ffffff"><td>01</td><td>特別推進研究</td></tr>
	 * <tr bgcolor="#ffffff"><td>03</td><td>基盤研究(S)</td></tr>
	 * <tr bgcolor="#ffffff"><td>04_1</td><td>基盤研究(A)(一般)</td></tr>
	 * <tr bgcolor="#ffffff"><td>04_3</td><td>基盤研究(A)(海外学術調査)</td></tr>
	 * <tr bgcolor="#ffffff"><td>05_1</td><td>基盤研究(B)(一般)</td></tr>
	 * <tr bgcolor="#ffffff"><td>05_3</td><td>基盤研究(B)(海外学術調査)</td></tr>
	 * <tr bgcolor="#ffffff"><td>06_1</td><td>基盤研究(C)(一般)</td></tr>
	 * <tr bgcolor="#ffffff"><td>06_2</td><td>基盤研究(C)(企画調査)</td></tr>
	 * <tr bgcolor="#ffffff"><td>11</td><td>萌芽研究</td></tr>
	 * <tr bgcolor="#ffffff"><td>12</td><td>若手研究(A)</td></tr>
	 * <tr bgcolor="#ffffff"><td>13</td><td>若手研究(B)</td></tr>
	 * <tr bgcolor="#ffffff"><td>52</td><td>学術創成研究費（推薦分・募集分）</td></tr>
	 * </table>
	 * 
	 * @param jigyo_id
	 * @return 申請種別
	 */
	public static String getShinseiShubetu(String jigyo_id) {
		//2005/03/24 修正 ------------------------------------------------ここから
		//理由 基盤事業が追加されたため
		//		return jigyo_id.substring(4, 6);

		String shinseiShubetu = jigyo_id.substring(4, 6);
        String betu = jigyo_id.substring(6, 7);

// 2006/06/29 dyh update start
//		//区分が04、05、06の場合（基盤一般、海外、企画）は枝番を付加して返却
//		if (shinseiShubetu.equals("04") || shinseiShubetu.equals("05")
//                || shinseiShubetu.equals("06")) {
//            return shinseiShubetu + "_" + jigyo_id.substring(6, 7);
        // 区分が02、04、05、06の場合（特定領域研究、基盤一般、海外、企画）は枝番を付加して返却
        if (shinseiShubetu.equals("02") || shinseiShubetu.equals("04")
                || shinseiShubetu.equals("05") || shinseiShubetu.equals("06")) {
            return shinseiShubetu + "_" + betu;
// 2006/06/29 dyh update end
        }
		
// 2006/02/14 Start 
// 理由　  特別研究促進費を追加する		
		//区分が15の場合（特別研究促進費）は枝番を付加して返却
		if (shinseiShubetu.equals("15")) {
			if (betu.equals("2") || betu.equals("3") || betu.equals("4")) {
				return shinseiShubetu + "_1";
			}
			if (betu.equals("5") || betu.equals("6")) {
				return shinseiShubetu + "_2";
			}
		}
// Nae End		

// 2006/06/29 dyh add start
// 理由:特定領域研究（新規領域）、特定領域研究（新規領域）領域計画書概要を追加する
        // 区分が02の場合（特定領域研究（新規領域）、特定領域研究（新規領域）領域計画書概要）は枝番を付加して返却
        if("02".equals(shinseiShubetu)){
            return shinseiShubetu + "_" + betu;
        }
// 2006/06/29 dyh add end
        
//2007/02/03 苗　追加ここから　若手S追加ので、 区分が12の場合（若手Aと若手S）は枝番を付加して返却
        if("12".equals(shinseiShubetu)){
            if (betu.equals("0")) {
                return shinseiShubetu + "_" + betu;
            } else {
                return shinseiShubetu;
            }
        }
//2007/02/03 苗　追加ここまで        

		return shinseiShubetu;
		//2005/03/24 修正 ------------------------------------------------ここまで
	}

	/**
	 * 月数を年月数に変換(Xヶ月→Y年Zヶ月)する。
	 * 引数が数値型に変換できない場合は空文字を返す。
	 * @param org_tuki
	 * @return
	 */
	public static String getYear(String org_tuki) {
		try{
			return getYear(Integer.parseInt(org_tuki));
		}catch(NumberFormatException e){
			return "";
		}
	}
	
	/**
	 * 月数を年月数に変換(Xヶ月→Y年Zヶ月)する。
	 * @param org_tuki
	 * @return
	 */
	public static String getYear(int org_tuki) {
		String ret_str = "";

		int nen = org_tuki / 12;
		if (nen != 0) {
			ret_str = nen + "年";
		}

		int tuki = org_tuki % 12;
		if (tuki != 0) {
			ret_str = ret_str + tuki + "ヶ月";
		}

		return ret_str;
	}

	/**
	 * リストの中に一致する値があるかを判定する。
	 * @param list
     * @param value
	 * @return boolean
	 */
	public static boolean checkInList(List list, String value) {
		return list.contains(value);
	}

	/**
	 * 氏名、振り仮名等2つの文字列を連結する。
     * aString、bStringの両方が入っているときのみ半角スペースを間に入れる。
	 * @param aString  
	 * @param bString
	 * @return
	 */
	public static String concat(String aString, String bString) {
		if (StringUtil.isBlank(aString) || StringUtil.isBlank(bString)) {
			return (aString == null ? "" : aString)
				+ (bString == null ? "" : bString);
		} else {
			return aString + " " + bString;
		}
	}

	/**
	 * 数値文字列を3桁区切りの文字列に変換する。
	 * 数値に変換できない場合は文字列をそのまま返す。
	 * 数値の範囲はLong型まで対応。
	 * ex.「9999999999」→「9,999,999,999」
	 * @param str
	 * @return
	 */
	public static String numericFormat(String str){
		
		if(str == null || str.length() == 0){
			return str;
		}
		
		DecimalFormat df = new DecimalFormat("#,###");
		try{
			return df.format(new Long(str));
		}catch(Exception e){
			//e.printStackTrace();
			return str;
		}
	}

	/**
	 * 第二引数のリストから、第一引数で指定したインデックスの値を返す。
	 * インデックス値が不正な場合は空文字を返す。
	 * @param index
	 * @param list
	 * @return
	 */
	public static String getTextFromList(int index, List list){
		try{
			return (String)list.get(index);
		}catch(Exception e){
			//e.printStackTrace();
			return "";
		}
	}	

	/**
	 * 第二引数のリストから、第一引数で指定したインデックスの値を返す。
	 * インデックス値が不正な場合は空文字を返す。
	 * @param strIndex
	 * @param list
	 * @return
	 */
	public static String getTextFromList(String strIndex, List list){
		try{
			return (String)list.get(Integer.parseInt(strIndex));
		}catch(Exception e){
			//e.printStackTrace();
			return "";
		}
	}	
	
	/**
	 * 引数が空なら「-」を返す。
	 * @param s
	 * @return
	 */
	public static String getHyphen(String s) {
		if(s == null || "".equals(s)) {
			return HYPHEN;
		} else {
			return s;
		}
	}
}