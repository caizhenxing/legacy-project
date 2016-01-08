/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

import java.text.*;
import java.util.*;

import jp.go.jsps.kaken.util.StringUtil;

/**
 * 
 * 文字列の論理フォーマットをチェックするクラス。
 * 
 * ID RCSfile="$RCSfile: DateUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 * 
 */
public class DateUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------


	//2005/04/19 修正 -------------------------------------------------------------ここから
	//各年号の記号を全角で表示しなければならないため
	// 暦変換テーブル(文字列で大小比較できるように年月日は10桁固定とする)
	//private static final String rekiTbl[][] =
	//	{ { "1868/09/08", "1912/07/29", "M" }, // 明治開始年月日、最終年月日、明治記号
	//	{
	//		"1912/07/30", "1926/12/24", "T" }, // 大正開始年月日、最終年月日、大正記号
	//	{
	//		"1926/12/25", "1989/01/07", "S" }, // 昭和開始年月日、最終年月日、昭和記号
	//	{
	//		"1989/01/08", "9999/12/31", "H" } // 平成開始年月日、最終年、平成記号
	//};

	// 暦変換テーブル(文字列で大小比較できるように年月日は10桁固定とする)
	private static final String rekiTbl[][] =
		{ { "1868/09/08", "1912/07/29", "M", "Ｍ" }, // 明治開始年月日、最終年月日、明治記号、明治記号(全角)
		{
			"1912/07/30", "1926/12/24", "T", "Ｔ" }, // 大正開始年月日、最終年月日、大正記号、大正記号(全角)
		{
			"1926/12/25", "1989/01/07", "S", "Ｓ" }, // 昭和開始年月日、最終年月日、昭和記号、昭和記号(全角)
		{
			"1989/01/08", "9999/12/31", "H", "Ｈ" } // 平成開始年月日、最終年、平成記号、平成記号(全角)
	};
	//2005/04/19 修正 -------------------------------------------------------------ここまで

// 20050713
	private static final String WarekiTbl[][] =
	{ { "1868/09/08", "1912/07/29", "M", "明治" }, // 明治開始年月日、最終年月日、明治記号、明治記号(全角)
	{
		"1912/07/30", "1926/12/24", "T", "大正" }, // 大正開始年月日、最終年月日、大正記号、大正記号(全角)
	{
		"1926/12/25", "1989/01/07", "S", "昭和" }, // 昭和開始年月日、最終年月日、昭和記号、昭和記号(全角)
	{
		"1989/01/08", "9999/12/31", "H", "平成" } // 平成開始年月日、最終年、平成記号、平成記号(全角)
};
// Horikoshi

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	// 基準カレンダー
	private Calendar cal;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ
	 */
	public DateUtil() {
		cal = Calendar.getInstance(); // 基準カレンダーに今日をセット
	}
	
	/**
	 * コンストラクタ
	 * @param date
	 */
	public DateUtil(Date date) {
		cal = Calendar.getInstance();
		cal.setTime(date); // 基準カレンダーにをセット
	}
	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * 基準カレンダーに年月日をセットする
	 * @param c 年月日がセットされたカレンダー
	 * 
	 * @return  true 正しい年月日
	 *          false 誤った年月日
	 */
	public boolean setCal(Calendar c) {
		if (c == null) {
			return false;
		}
		cal.set(
			c.get(Calendar.YEAR),
			c.get(Calendar.MONTH),
			c.get(Calendar.DAY_OF_MONTH));
		return true;
	}

	/**
	 * 基準カレンダーに年月日をセットする
	 * @param yyyy  西暦年文字列
	 * @param mm    西暦月文字列
	 * @param dd    西暦日文字列
	 * @return      true 正しい年月日
	 *              false 誤った年月日
	 */
	public boolean setCal(String yyyy, String mm, String dd) {
		// 年月日の正当性をチェック
		if (checkDate(yyyy, mm, dd) == false) {
			return false;
		}
		try {
			// 年月日を基準カレンダーへセット
			cal.set(
				Integer.parseInt(yyyy),
				Integer.parseInt(mm) - 1,
				Integer.parseInt(dd));
			return true;
		} catch (NumberFormatException e) { // 数字でなければエラー
			return false;
		}
	}

	/**
	 * 基準カレンダーに年月日をセットする
	 * @param yyyy 西暦年数値
	 * @param mm   西暦月数値(1～12)
	 * @param dd   西暦日数値(1～31)
	 * @return     true 正しい年月日
	 *             false 誤った年月日
	 */
	public boolean setCal(int yyyy, int mm, int dd) {
		// 年月日の正当性をチェック
		if (checkDate(yyyy, mm, dd) == false) {
			return false;
		}
		try {
			// 年月日を基準カレンダーへセット
			cal.set(yyyy, mm - 1, dd);
			return true;
		} catch (NumberFormatException e) { // 数字でなければエラー
			return false;
		}
	}

	/**
	 * 基準カレンダーに年と1月1日をセットする
	 * @param yyyy 西暦年数値
	 * @return     true 正しい年
	 *             false 誤った年
	 */
	public boolean setCal(int yyyy) {
		// 年の正当性をチェック
		if (checkDate(yyyy, 1, 1) == false) {
			return false;
		}
		try {
			// 年+1月1日を基準カレンダーへセット
			cal.set(yyyy, 0, 1);
			return true;
		} catch (NumberFormatException e) { // 数字でなければエラー
			return false;
		}
	}

	/**
	 * 基準カレンダーに年月日をセットする
	 *
	 * @param date 西暦年月日(年月日の間はデリミタで区切られていること)
	 * @param dim  デリミタ
	 * @return     true 正しい年月日
	 *             false 誤った年月日
	 */
	public boolean setCal(String date, String dim) {
		// 年月日の正当性をチェック
		if (checkDate(date) == false) {
			return false;
		}
		// デリミタを/としてパラメータ年月日を分割します
		StringTokenizer st = new StringTokenizer(date, dim);
		String d[] = new String[3];
		if (st.countTokens() > d.length) { // トークンが多過ぎる場合はエラー
			return false;
		}
		int i = 0;
		while (st.hasMoreTokens()) {
			d[i] = st.nextToken();
			i++;
		}
		try {
			// 基準カレンダーに年月日をセットします
			cal.set(
				Integer.parseInt(d[0]),
				Integer.parseInt(d[1]) - 1,
				Integer.parseInt(d[2]));
			return true;
		} catch (NumberFormatException e) { // 数字でなければエラー
			return false;
		}
	}

	/**
	 * 基準カレンダーに和暦で年月日をセットする
	 * @param jpy 和暦年月日（年月日はデリミタで区切られていること）
	 *             和暦年月日は明治33年1月1日以降であること
	 * @param dim  デリミタ
	 * @return     true 正しい年月日
	 *             false 誤った年月日
	 */
	public boolean setCalJpy(String jpy, String dim) {
		// デリミタを/としてパラメータ年月日を分割します
		StringTokenizer st = new StringTokenizer(jpy.trim(), dim);
		String d[] = new String[3];
		if (st.countTokens() > d.length) { // トークンが多過ぎる場合はエラー
			return false;
		}

		// 年月日をトークンとして取り出し
		int i = 0;
		while (st.hasMoreTokens()) {
			d[i] = st.nextToken();
			i++;
		}

		// 分割した年月日をそれぞれ整数化します
		int jpyy;
		int mm;
		int dd;
		try {
			jpyy = Integer.parseInt(d[0].substring(1));
			mm = Integer.parseInt(d[1]);
			dd = Integer.parseInt(d[2]);
		} catch (NumberFormatException e) { // 数字でなければエラー
			return false;
		}

		// パラメータの和暦年の元号部分で暦変換テーブルを検索
		int j = 0;
		for (j = 0; j < rekiTbl.length; j++) {
			// 元号がマッチしたらビンゴ！
			if (d[0].startsWith(rekiTbl[j][2])) {
				break;
			}
		}

		// 西暦年月日の取得
		try {
			// 暦テーブルに該当レコードがあったら
			if (j < rekiTbl.length) {
				// 和暦年が0以下ならエラー
				if (jpyy <= 0) {
					return false;
				}
				// 西暦年を計算して求めます
				int ad =
					Integer.parseInt(rekiTbl[j][0].substring(0, 4)) + jpyy - 1;
				// 西暦年が1900年未満なら和暦変換エラー
				if (ad < 1900) {
					return false;
				}
				// 西暦年月日の正当性をチェックします
				if (checkDate(ad, mm, dd)) {
					// 基準カレンダーに年月日をセットします
					cal.set(ad, mm - 1, dd);
					return true;
				} else {
					return false;
				}
			} else {
				// 該当がなければエラー
				return false;
			}
		} catch (NumberFormatException e) { // パラメータが数字でなければエラー
			return false;
		}
	}

	/**
	 * 基準カレンダーをCalendar変数として返すメソッド
	 * 
	 * @return 基準カレンダーをCalendar変数として返す
	 */
	public Calendar getCal() {
		return cal;
	}

	/**
	 * 基準カレンダーを誕生日として現在年齢を算出するメソッド
	 * 
	 * @return 現在年齢を整数として返す。
	 *         エラーの場合は-1を返す。
	 */
	public int getAge() {

		// 今現在のカレンダーを作成
		Calendar now = Calendar.getInstance();

		// 現在年-誕生年=概算年齢
		int age = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);

		// 今年の誕生月が来ていなければ
		if (now.get(Calendar.MONTH) < cal.get(Calendar.MONTH)) {
			age--; //概算年齢から１年引く
			// 今が誕生月で
		} else if (now.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) {
			// まだ誕生日が来ていなければ
			if (now.get(Calendar.DAY_OF_MONTH)
				< cal.get(Calendar.DAY_OF_MONTH)) {
				age--; //概算年齢から１年引く
			}
		}
		// 誕生日が今日より大きければエラーとする
		if (age < 0) {
			return -1; // エラー
		} else {
			return age; // 年齢を返す
		}
	}

	/**
	 * 基準カレンダーの年月日と指定されたDateUtilの年月日との日数差を算出するクラス
	 *
	 * @param date
	 * @return 日数差を整数として返す。
	 */
	public int getElapse(DateUtil date) {

		// ターゲットのカレンダーを取得
		Calendar d = date.getCal();

		// 現在までの差分ミリ秒を求める
		long ed = d.getTimeInMillis() - cal.getTimeInMillis();

		//    差分ミリ秒を日数へ換算
		int elapse = (int) (ed / 1000 / 60 / 60 / 24);

		return elapse; // 日数差を返す
	}
	
	
	/**
	 * 基準カレンダーの年月と指定されたDateUtilの年月との月数差を算出するメソッド
	 * 指定されたDateUtilの日付と基準カレンダーの日付を判断し、指定された日付の方が
	 * 大きい場合（同じ場合も含む）は、１ヶ月として判断する。<br>
	 * <ul>
	 * <li>2005/4/1 ～ 2007/3/31→「2年（24ヶ月）」</li>
	 * <li>2005/4/1 ～ 2007/4/1 →「2年と1ヶ月（25ヶ月）」</li>
	 * <li>2005/4/15～ 2007/9/30→「2年と6ヶ月（30ヶ月）」</li>
	 * <li>2005/2/28～ 2005/3/28→「0年と2ヶ月（2ヶ月）」</li>
	 * </ul>
	 * targetの方が昔だった場合はマイナスで返る。
	 * @param target
	 * @return 月数差を整数として返す。
	 */
	public int getElapseMonth(DateUtil target){
		
		//基準カレンダーの年,月を取得する
		int myYYYY = cal.get(Calendar.YEAR);
		int myMM   = cal.get(Calendar.MONTH) + 1;
		int myDD   = cal.get(Calendar.DATE);
		
		//ターゲットカレンダーの年,月を取得する
		Calendar targetCal = target.getCal();
		int targetYYYY = targetCal.get(Calendar.YEAR);
		int targetMM   = targetCal.get(Calendar.MONTH) + 1;
		int targetDD   = targetCal.get(Calendar.DATE);
		
		//月数の差分を計算する
		int yearDiff  = targetYYYY - myYYYY;
		int monthDiff = (targetMM + yearDiff*12) - myMM;
		
		//指定された日付の方が大きい場合（同じ場合も含む）はプラス１
		if(targetDD>=myDD){
			monthDiff++;
		}
		
		return monthDiff;
		
	}
	
	
	/**
	 * 基準カレンダーに指定日数を加算するメソッド
	 *
	 * @param day  指定日数(-は過去、+は未来を示す)
	 *
	 */
	public void addDate(int day) {
		// 基準カレンダーに指定日数を加算した年月日を求める
		cal.add(Calendar.DATE, day);
	}

	/**
	 * 和暦年月日の文字列を返すメソッド
	 *
	 * @param dim 年月日を区切るデリミタ
	 * @return 和暦年月日を$YY?MM?DD形式の文字列で返す（例> H15/1/1）
	 *         $は明治=M、大正=T、昭和=S、平成=Hで元号を表すものとする
	 *         エラーの場合はNULLを返す
	 *
	 */
	public String getJpyString(String dim) {

		// 基準カレンダーの年月日をそれぞれ整数化します
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);

		// 1900年以前であればエラーとします
		if (yyyy < 1900) {
			return null;
		}

		// １０桁固定のYYYY/MM/DD形式に変換
		String ady =
			new DecimalFormat("0000").format(yyyy)
				+ new DecimalFormat("00").format(mm)
				+ new DecimalFormat("00").format(dd);

		// 暦変換テーブルをサーチ
		int i = 0;
		for (i = 0; i < rekiTbl.length; i++) {
			// 当該西暦が開始年以上で最終年以下ならヒット！
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// 暦変換テーブルに該当レコードがあったら
			if (i < rekiTbl.length) {
				// 和暦年を計算して求めます
				int jpy =
					yyyy - Integer.parseInt(rekiTbl[i][0].substring(0, 4)) + 1;
				// 和号+和暦年を返します
				return rekiTbl[i][2]
					+ String.valueOf(jpy)
					+ dim
					+ new DecimalFormat("00").format(mm)
					+ dim
					+ new DecimalFormat("00").format(dd);
			} else {
				// 該当がなければエラー
				return null;
			}
		} catch (NumberFormatException e) { // パラメータが数字でなければエラー
			return null;
		}
	}

	//2005/04/19 追加---------------------------------------------------ここから
	//理由 和暦誕生日を表示する必要があるため
	/**
	 * 和暦年月の文字列を返すメソッド
	 *
	 * @param date 変換対象となる日付
	 * @return ex)"Ｓ．62年 3月"
	 *
	 */
	public String getJpyString(Date date) {

		cal.setTime(date);
		// 基準カレンダーの年月日をそれぞれ整数化します
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);

		// 1900年以前であればエラーとします
		if (yyyy < 1900) {
			return null;
		}

		// １０桁固定のYYYY/MM/DD形式に変換
		String ady =
			new DecimalFormat("0000").format(yyyy)
				+ new DecimalFormat("00").format(mm)
				+ new DecimalFormat("00").format(dd);

		// 暦変換テーブルをサーチ
		int i = 0;
		for (i = 0; i < rekiTbl.length; i++) {
			// 当該西暦が開始年以上で最終年以下ならヒット！
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// 暦変換テーブルに該当レコードがあったら
			if (i < rekiTbl.length) {
				// 和暦年を計算して求めます
				int jpy =
					yyyy - Integer.parseInt(rekiTbl[i][0].substring(0, 4)) + 1;
				// 和号+和暦年を返します
				return rekiTbl[i][3]
					+ "．"
					+ String.valueOf(jpy)
					+ "年"
					+ new DecimalFormat("00").format(mm)
					+ "月";
			} else {
				// 該当がなければエラー
				return null;
			}
		} catch (NumberFormatException e) { // パラメータが数字でなければエラー
			return null;
		}
	}
	//2005/04/19 追加---------------------------------------------------ここまで

	/**
	 * 西暦年月日の文字列を返すメソッド
	 * 
	 * @param dim 年月日を区切るデリミタ
	 * @return 西暦年月日をデリミタで区切った形式の文字列で返す（例> 2003/10/1）
	 */
	public String getAdString(String dim) {
		// デリミタで区切ったYYYY?MM?DD形式の文字列を返す
		return String.valueOf(cal.get(Calendar.YEAR))
			+ dim
			+ String.valueOf(cal.get(Calendar.MONTH) + 1)
			+ dim
			+ String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * 西暦年月日の年（下２桁）だけを返す。
	 * @return
	 */
	public String getYearYY() {
		int yyyy = cal.get(Calendar.YEAR) % 100;
		String yy = new DecimalFormat("00").format(yyyy);
		return yy;
	}
	
	/**
	 * 西暦年月日の年（下4桁）だけを返す。
	 * @return
	 */
	public String getYearYYYY() {
		int yyyy = cal.get(Calendar.YEAR);
		String YYYY = new DecimalFormat("0000").format(yyyy);
		return YYYY;
	}
	
	/**
	 * 基準カレンダーの日時情報を、年月日に丸めたDateオブジェクトで返す。
	 * @return
	 */
	public Date getDateYYYYMMDD(){
		
		// 基準カレンダーの年月日をそれぞれ整数化します
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);

		// １０桁固定のYYYY/MM/DD形式に変換
		String ady =
			new DecimalFormat("0000").format(yyyy)
				+ new DecimalFormat("00").format(mm)
				+ new DecimalFormat("00").format(dd);
		
		//日時情報を年月日に丸める
		Date date = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			date = sdf.parse(ady);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;

	}	
	
	
	/**
	 * 年度を返す。
	 * @return 年度
	 */
	public String getNendo() {

		// 基準カレンダーの年月日をそれぞれ整数化します
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);

		// 1900年以前であればエラーとします
		if (yyyy < 1900) {
			return null;
		}

		// １０桁固定のYYYY/MM/DD形式に変換
		String ady =
			new DecimalFormat("0000").format(yyyy)
				+ new DecimalFormat("00").format(mm)
				+ new DecimalFormat("00").format(dd);

		// 暦変換テーブルをサーチ
		int i = 0;
		for (i = 0; i < rekiTbl.length; i++) {
			// 当該西暦が開始年以上で最終年以下ならヒット！
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// 暦変換テーブルに該当レコードがあったら
			if (i < rekiTbl.length) {
				// 和暦年を計算して求めます
				int jpy =
					yyyy - Integer.parseInt(rekiTbl[i][0].substring(0, 4)) + 1;

				//4月を見て年度を選択する。				
				if (cal.get(Calendar.MONTH) < Calendar.APRIL) {
					jpy = jpy - 1;
				}

				// 年度を返します。
				return new DecimalFormat("00").format(jpy);
			} else {
				// 該当がなければエラー
				return null;
			}
		} catch (NumberFormatException e) { // パラメータが数字でなければエラー
			return null;
		}
	}

	/**
	 * 干支を取得するメソッド
	 * 
	 * @return 干支を文字列で返す
	 *         エラーの場合はNULLを返す
	 */
	public String getEto() {
		// 十二支文字列テーブル
		String etoTbl[] =
			{ "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥" };

		// 年を整数化します
		int yyyy = cal.get(Calendar.YEAR);
		int i = (yyyy + 8) % 12;
		return etoTbl[i];
	}

	/**
	 * 文字列が存在する年月日(YYYY/MM/DD)であるかどうかをチェックする
	 * 大小の月、閏年などもチェックする
	 * 
	 * @param date チェックする西暦年月日文字列
	 *             年と月と日は必ずスラッシュで区切られていること。
	 * @return チェック結果 true：正しい false：誤り
	 */
	public static boolean checkDate(String date) {
		// 前後の空白を除去
		String d = new String(date.trim());
		// 日付フォーマッターを定義
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		// 解析レベルを厳密にする
		df.setLenient(false);
		try {
			df.parse(d); // 入力年月日を解析
		} catch (ParseException e) {
			return false; // エラー
		}
		return true;
	}

	/**
	 * 文字列が存在する年(YYYY),月(MM),日(DD)であるかどうかをチェックする
	 * 大小の月、閏年などもチェックする
	 * 
	 * @param yyyy チェックする西暦年文字列
	 * @param mm   チェックする月文字列
	 * @param dd   チェックする日文字列
	 * @return チェック結果 true：正しい false：誤り
	 */
	private boolean checkDate(String yyyy, String mm, String dd) {
		return checkDate(yyyy + "/" + mm + "/" + dd);
	}

	/**
	 * 数値が存在する年(YYYY),月(MM),日(DD)であるかどうかをチェックする
	 * 大小の月、閏年などもチェックする
	 * 
	 * @param yyyy チェックする西暦年数値
	 * @param mm   チェックする月数値
	 * @param dd   チェックする日数値
	 * @return チェック結果 true：正しい false：誤り
	 */
	private boolean checkDate(int yyyy, int mm, int dd) {
		// 年月日の正当性をチェック
		return checkDate(
			String.valueOf(yyyy)
				+ "/"
				+ String.valueOf(mm)
				+ "/"
				+ String.valueOf(dd));
	}
	
	
	
	/**
	 * 和暦年度から西暦年度（下２ケタ）へ変換する。
	 * @param warekiNendo
	 * @return
	 */
	public static String changeWareki2Seireki(String warekiNendo){
		
		String nendo = new DecimalFormat("00")
							.format(Integer.parseInt(warekiNendo));
		
		StringBuffer buffer = new StringBuffer()
							  .append("H")			//平成
							  .append(nendo)		//年度
							  .append("/01/01");	//仮として年月をセット
		
		DateUtil nendoUtil = new DateUtil();
		nendoUtil.setCalJpy(buffer.toString() ,"/");
		
		//年度を西暦年度（2桁）に変換
		return nendoUtil.getYearYY();
		
	}
	
	/**
	 * 和暦年度から西暦年度（下4ケタ）へ変換する。
	 * @param warekiNendo
	 * @return
	 */
	public static String changeWareki4Seireki(String warekiNendo){
		
		String nendo = new DecimalFormat("00")
							.format(Integer.parseInt(warekiNendo));
		
		StringBuffer buffer = new StringBuffer()
							  .append("H")			//平成
							  .append(nendo)		//年度
							  .append("/01/01");	//仮として年月をセット
		
		DateUtil nendoUtil = new DateUtil();
		nendoUtil.setCalJpy(buffer.toString() ,"/");
		
		//年度を西暦年度（2桁）に変換
		return nendoUtil.getYearYYYY();
		
	}
	
// 20050713
	
	/**
	 * 和暦年月の文字列を返すメソッド
	 *
	 * @param chYear 変換対象となる日付の年
	 * @param chMonth 変換対象となる日付の月
	 * @param chDay 変換対象となる日付の日
	 * @return ex)"平成XX年XX月XX日"
	 *
	 */
	public String getJpyString(String chYear, String chMonth, String chDay) {

		int nYear;
		int nMonth;
		int nDay;

		if(chYear != null && chYear.length() > 0 && 
			chMonth != null && chMonth.length() > 0 &&
			chDay != null && chDay.length() > 0){

			nYear = StringUtil.parseInt(chYear);
			nMonth = StringUtil.parseInt(chMonth);
			nDay = StringUtil.parseInt(chDay);

		}
		else{
			return null;
		}

		try {
			return getJpyString(nYear,nMonth,nDay);
		}catch (NumberFormatException e) { // パラメータが数字でなければエラー
			return null;}
	}

	/**
	 * 和暦年月の文字列を返すメソッド
	 *
	 * @param nYear 変換対象となる日付の年(int)
	 * @param nMonth 変換対象となる日付の月(int)
	 * @param nDay 変換対象となる日付の日(int)
	 * @return ex)"平成XX年XX月XX日"
	 *
	 */

	public String getJpyString(int nYear, int nMonth, int nDay) {

		// １０桁固定のYYYY/MM/DD形式に変換
		String ady =
			new DecimalFormat("0000").format(nYear)
				+ "/"
				+ new DecimalFormat("00").format(nMonth)
				+ "/"
				+ new DecimalFormat("00").format(nDay);

		// 暦変換テーブルをサーチ
		int i = 0;
		for (i = 0; i < WarekiTbl.length; i++) {
			// 当該西暦が開始年以上で最終年以下ならヒット！
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// 暦変換テーブルに該当レコードがあったら
			if (i < WarekiTbl.length) {
				// 和暦年を計算して求めます
				int jpy =
					nYear - Integer.parseInt(WarekiTbl[i][0].substring(0, 4)) + 1;
				// 和号+和暦年+月+日を返します
				return WarekiTbl[i][3]
					+ ""
					+ String.valueOf(jpy)
					+ "年"
					+ new DecimalFormat("").format(nMonth)
					+ "月"
					+ new DecimalFormat("").format(nDay)
					+ "日";
			} else {
				// 該当がなければエラー
				return null;
			}
		} catch (NumberFormatException e) { // パラメータが数字でなければエラー
			return null;
		}
	}

// Horikoshi

//	2006/02/08 add start by miaomiao
	/**
	 * 和暦年月の文字列を返すメソッド
	 *
	 * @param chYear 変換対象となる日付の年(String)
	 * @param chMonth 変換対象となる日付の月(String)
	 * @return ex)"平成XX年XX月"
	 *
	 */
	public String getJpyString(String chYear, String chMonth) {

		int nYear;
		int nMonth;

		if(chYear != null && chYear.length() > 0 && 
			chMonth != null && chMonth.length() > 0 ){

			nYear = StringUtil.parseInt(chYear);
			nMonth = StringUtil.parseInt(chMonth);
		}
		else{
			return null;
		}

		try {
			return getJpyString(nYear,nMonth);
		}catch (NumberFormatException e) { // パラメータが数字でなければエラー
			return null;}
	}

	/**
	 * 和暦年月の文字列を返すメソッド
	 *
	 * @param nYear 変換対象となる日付の年(int)
	 * @param nMonth 変換対象となる日付の月(int)
	 * @return ex)"平成XX年XX月"
	 *
	 */
	public String getJpyString(int nYear, int nMonth) {

		// 7桁固定のYYYY/MM形式に変換
		String ady =
			new DecimalFormat("0000").format(nYear)
				+ "/"
				+ new DecimalFormat("00").format(nMonth);

		// 暦変換テーブルをサーチ
		int i = 0;
		for (i = 0; i < WarekiTbl.length; i++) {
			// 当該西暦が開始年以上で最終年以下ならヒット！
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// 暦変換テーブルに該当レコードがあったら
			if (i < WarekiTbl.length) {
				// 和暦年を計算して求めます
				int jpy =
					nYear - Integer.parseInt(WarekiTbl[i][0].substring(0, 4)) + 1;
				// 和号+和暦年+月+日を返します
				return WarekiTbl[i][3]
					+ ""
					+ String.valueOf(jpy)
					+ "年"
					+ new DecimalFormat("").format(nMonth)
					+ "月";
			} else {
				// 該当がなければエラー
				return null;
			}
		} catch (NumberFormatException e) { // パラメータが数字でなければエラー
			return null;
		}
	}

	/**
	 * 和暦年度の文字列を返すメソッド
	 *
	 * @param chYear 変換対象となる日付の年(String)
	 * @return ex)"平成XX年"
	 *
	 */
	public String getJpyNedo(String chYear) {

		int nYear;

		if(chYear != null && chYear.length() > 0){			
			nYear = StringUtil.parseInt(chYear);
		}else{
			return null;
		}

		try {
			return getJpyString(nYear);
		}catch (NumberFormatException e) { // パラメータが数字でなければエラー
			return null;}
	}

	/**
	 * 和暦年の文字列(和号+和暦年)を返すメソッド
	 *
	 * @param nYear 変換対象となる日付の年(int)
	 * @return ex)"平成XX年"
	 */
	public String getJpyString(int nYear) {

		// 4桁固定のYYYY形式に変換
		String ady =
			new DecimalFormat("0000").format(nYear);
		// 暦変換テーブルをサーチ
		int i = 0;
		for (i = 0; i < WarekiTbl.length; i++) {
			// 当該西暦が開始年以上で最終年以下ならヒット！
			if (ady.compareTo(rekiTbl[i][0]) >= 0
				&& ady.compareTo(rekiTbl[i][1]) <= 0) {
				break;
			}
		}

		try {
			// 暦変換テーブルに該当レコードがあったら
			if (i < WarekiTbl.length) {
				// 和暦年を計算して求めます
				int jpy =
					nYear - Integer.parseInt(WarekiTbl[i][0].substring(0, 4)) + 1;
				// 和号+和暦年を返します
				return WarekiTbl[i][3]
					+ ""
					+ String.valueOf(jpy)
					+ "年";
			} else {
				// 該当がなければエラー
				return null;
			}
		} catch (NumberFormatException e) { // パラメータが数字でなければエラー
			return null;
		}
	}
	
//2005/01/16 add end by miaomiao

   /**
	* HTTP用の日付フォーマットを返す。（RFC 1123）
	* @return String
	*/
   public String getHTTPDate() {
	   String pattern = "E, dd MMM yyyy hh:mm:ss zzz";
	   SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
	   sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	   return sdf.format(cal.getTime());
   }
}