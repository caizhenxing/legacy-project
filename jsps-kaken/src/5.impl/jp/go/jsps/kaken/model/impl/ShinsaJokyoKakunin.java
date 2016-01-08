/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.IShinsaJokyoKakunin;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.MasterSogoHyokaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinsaKekkaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinsainInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInfo;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 審査状況確認を行うクラス.<br><br>
 * 
 * ・使用テーブル<br>
 * <table>
 * <tr>審査結果テーブル<td>：審査員割り振り結果情報と申請書の審査結果を管理</td></tr>
 * <tr>申請データ管理テーブル<td>：申請データの情報を管理</td></tr>
 * <tr>審査員マスタテーブル<td>：審査員の情報を管理</td></tr>
 * </table>
 * 
 */
public class ShinsaJokyoKakunin implements IShinsaJokyoKakunin {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(ShinsaJokyoKakunin.class);

	/** 申請書削除フラグ（削除済み） */
	private static final String FLAG_APPLICATION_DELETE     = IShinseiMaintenance.FLAG_APPLICATION_DELETE;
	
	/** 審査件数CSVの共通部分(件数の前)の個数 **/
//2006/11/06 苗　修正ここから    
//	private static final int NUM_CSV_COMMON	= 18;
//	private static final int NUM_CSV_COMMON = 19;
    private static final int NUM_CSV_COMMON = 20;	//利害関係入力完了状況を追加した為
//2006/11/06　苗　修正ここまで    

	/** 審査件数CSVの件数の個数 **/
    
//2006/05/23 更新ここから
//  private static final int NUM_CSV_COUNT	= 22;
//	private static final int NUM_CSV_COUNT	= 24; 2006/04/10 LY
//    private static final int NUM_CSV_COUNT  = 34;  2006/11/06 苗　削除
//苗　更新ここまで
    
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public ShinsaJokyoKakunin() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IShinsaiJokyoKakunin
	//---------------------------------------------------------------------

	/**
	 * 審査状況を返却.<br />
	 * 何も処理をせず、第二引数をそのまま返却する。
	 * @param userInfo UserInfo
	 * @param addInfo ShinsaJokyoInfo
	 * @return 第二引数のaddInfo
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoInfo)
	 */
	public ShinsaJokyoInfo insert(UserInfo userInfo, ShinsaJokyoInfo addInfo)
		throws ApplicationException {
			return addInfo;
	}

	/**
	 * 空実装メソッド.<br />
	 * @param userInfo UserInfo
	 * @param addInfo ShinsaJokyoInfo
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoInfo)
	 */
	public void update(UserInfo userInfo, ShinsaKekkaInfo updateInfo)
		throws ApplicationException {
	}

	/**
	 * 空実装メソッド.<br />
	 * @param userInfo UserInfo
	 * @param addInfo ShinsaJokyoInfo
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoInfo)
	 */
	public void delete(UserInfo userInfo, ShinsaJokyoInfo deleteInfo)
		throws ApplicationException {

	}

	/**
	 * 空実装メソッド.<br />
	 * @param userInfo UserInfo
	 * @param addInfo ShinsaJokyoInfo
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoInfo)
	 */
	public ShinsaJokyoInfo select(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
		throws ApplicationException {
		return null;
	}


	/**
	 * 
	 * 表示方式から、審査状況CSV・審査件数CSVを判断する。
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return 検索結果を格納したListオブジェクト
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoSearchInfo)
	 */
	public List searchCsvData(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {

		List csvList = null;
		if(!StringUtil.isBlank(searchInfo.getHyojiHoshikiShinsaJokyo())) {
			if("1".equals(searchInfo.getHyojiHoshikiShinsaJokyo())) {
				//審査状況一覧出力
				csvList =  searchJokyoCsvData(userInfo, searchInfo);
			} else if("2".equals(searchInfo.getHyojiHoshikiShinsaJokyo())) {
				//審査件数一覧出力
				csvList = searchKensuCsvData(userInfo, searchInfo);
			}
		} else {
			throw new ApplicationException(
				"検索条件が不正です。",
				new ErrorInfo("errors.4004"));
		}
		return csvList;
	}

	/**
	 * CSV出力用のList作成.<br />
	 * <br />
	 * 1.SQL文の作成<br /><br />
	 * 以下のSQL文を実行し、審査員情報を取得する。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *  SELECT
	 * 		C.SHINSAIN_NO "審査員番号"
	 * 		, C.NAME_KANJI_SEI "氏名（漢字等－姓）"
	 * 		, C.NAME_KANJI_MEI "氏名（漢字等－名）"
	 * 		, C.NAME_KANA_SEI "氏名（フリガナ－姓）"
	 * 		, C.NAME_KANA_MEI "氏名（フリガナ－名）"
	 * 		, C.SHOZOKU_CD "所属機関名（コード）"
	 * 		, C.SHOZOKU_NAME "所属機関名（和文）"
	 * 		, C.BUKYOKU_NAME "部局名（和文）"
	 * 		, C.SHOKUSHU_NAME "職種名称"
	 * 		, C.SOFU_ZIP "送付先（郵便番号）"
	 *		, C.SOFU_ZIPADDRESS "送付先（住所）"
	 *		, C.SOFU_ZIPEMAIL "送付先（Email）"
	 *		, C.SHOZOKU_TEL "電話番号"
	 *		, C.URL "URL"
	 *	 	, C.SENMON "専門分野"
	 * 		, TO_CHAR(C.KOSHIN_DATE, 'YYYY/MM/DD') "データ更新日"
	 * 		, C.BIKO "備考"
	 * 		, A.UKETUKE_NO "申請番号"
	 * 		, A.JIGYO_ID "事業ID"
	 * 		, A.JIGYO_NAME "事業名"
	 * 		, A.KEKKA_ABC "審査結果(ABC)"
	 * 		, A.KEKKA_TEN "審査結果(点数)"
	 * 		, A.COMMENT1 "コメント1"
	 * 		, A.COMMENT2 "コメント2"
	 * 		, A.COMMENT3 "コメント3"
	 * 		, A.COMMENT4 "コメント4"
	 * 		, A.COMMENT5 "コメント5"
	 * 		, A.COMMENT6 "コメント6"
	 * 		, A.KENKYUNAIYO "研究内容"
	 * 		, A.KENKYUKEIKAKU "研究計画"
	 * 		, A.TEKISETSU_KAIGAI "適切性-海外･企画調査･萌芽研究"
	 * 		, A.TEKISETSU_KENKYU1 "適切性-分担金"
	 * 		, A.DATO "研究経費の妥当性"
	 * 		, A.SHINSEISHA "関係者等"
	 * 		, A.COMMENTS "コメント"
	 * 		, REPLACE(
	 * 		         REPLACE(A.SHINSA_JOKYO, '0', '未完了')
	 * 		          , '1', '完了') "審査状況"
	 * 	FROM
	 * 		SHINSAKEKKA A
	 * 		, SHINSEIDATAKANRI B
	 * 		, MASTER_SHINSAIN C
	 * 	WHERE
	 * 		A.SYSTEM_NO = B.SYSTEM_NO
	 * 		AND A.SHINSAIN_NO = C.SHINSAIN_NO
	 * 		AND A.SHINSAIN_NO NOT LIKE '@%'
	 * 		AND B.DEL_FLG = 0 
	 *	
	 *				<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 *	
	 * ORDER BY A.SHINSAIN_NO, A.JIGYO_ID, B.UKETUKE_NO
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 *	<b><span style="color:#002288">動的検索条件</span></b><br />
	 *	引数のsearchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>shinsainNo</td><td>AND A.SHINSAIN_NO = '審査員番号'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（漢字等－姓）</td><td>nameKanjiSei</td><td>AND A.SHINSAIN_NAME_KANJI_SEI LIKE '%氏名（漢字等－姓）%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（漢字等－名）</td><td>nameKanjiMei</td><td>AND A.SHINSAIN_NAME_KANJI_MEI LIKE '%氏名（漢字等－名）%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ－姓）</td><td>nameKanaSei</td><td>AND A.NAME_KANA_SEI LIKE '%氏名（フリガナ－姓）%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ－名）</td><td>nameKanaMei</td><td>AND A.NAME_KANA_MEI LIKE '%氏名（フリガナ－名）%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>年度</td><td>nendo</td><td>AND B.NENDO = '年度'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>回数</td><td>kaisu</td><td>AND B.KAISU = '回数'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>values</td><td>AND SUBSTR(A.JIGYO_ID,3,5) IN ('事業コード1,事業コード2…')</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>shozokuCd</td><td>AND B.SHOZOKU_CD = '所属機関コード'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>担当事業区分</td><td>-</td><td>AND B.JIGYO_KUBUN IN (1,4)</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>系統の区分</td><td>keiName</td><td>AND (B.KEI_NAME LIKE '%系統の区分%' OR KEI_NAME_RYAKU LIKE '%系統の区分%')</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査状況</td><td>shinsaJokyo</td><td>AND A.SHINSA_JOKYO = '審査状況'</td></tr>
	 * 	</table>
	 *	<div style="font-size:8pt">※担当事業区分はユーザが業務担当者の場合、担当する事業区分によってIN句の値が変わる。事業区分の定義は以下の通り。<br/>
	 *	1:学術創成（推薦分）　4:基盤研究</div><br />
	 * 
	 * 2.検索・検索結果返却<br />
	 * 1.で作成したSQLを実行し検索結果をListに格納する。検索結果のListには書くレコードの情報を格納したListが格納されている。
	 * なお、検索結果のListの一つ目には、ResultSetMetaDataから取得したテーブル列名を格納している。<br/>
	 * すべてのレコードの格納処理が終わった段階で、呼び出し元へ返却する。
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return CSV出力用List 
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoSearchInfo)
	 */
	private List searchJokyoCsvData(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {
	
		//-----------------------
		// SQL文の作成
		//-----------------------
		//2005.10.04 iso 申請→応募、所属機関→所属研究機関、コード→番号、申請書→研究計画調書
		String select = "SELECT "
//2006/11/06　苗　修正ここから            
//						+ " C.SHINSAIN_NO \"審査員番号\""
                        + " C.SHINSAIN_NO \"審査員割振番号\""
//2006/11/06　苗　修正ここまで                        
						+ ", C.NAME_KANJI_SEI \"氏名（漢字等－姓）\""
						+ ", C.NAME_KANJI_MEI \"氏名（漢字等－名）\""
						+ ", C.NAME_KANA_SEI \"氏名（フリガナ－姓）\""
						+ ", C.NAME_KANA_MEI \"氏名（フリガナ－名）\""
						+ ", C.SHOZOKU_CD \"所属研究機関名（番号）\""
						+ ", C.SHOZOKU_NAME \"所属研究機関名（和文）\""
//						+ ", C.BUKYOKU_CD \"部局名（コード）\""
						+ ", C.BUKYOKU_NAME \"部局名（和文）\""
//						+ ", C.SHOKUSHU_CD \"職種コード\""
						+ ", C.SHOKUSHU_NAME \"職種名称\""
//						+ ", C.SHINSA_KAHI \"審査可不可\""
						+ ", C.SOFU_ZIP \"送付先（郵便番号）\""
						+ ", C.SOFU_ZIPADDRESS \"送付先（住所）\""
						+ ", C.SOFU_ZIPEMAIL \"送付先（Email）\""
//						+ ", C.SOFU_ZIPEMAIL2 \"送付先（Email2）\""
						+ ", C.SHOZOKU_TEL \"電話番号\""
//						+ ", C.SHOZOKU_FAX \"FAX番号\""
//						+ ", C.JITAKU_TEL \"自宅電話番号\""
//						+ ", C.SINKI_KEIZOKU_FLG \"新規・継続\""
//						+ ", C.KIZOKU_START \"委嘱開始日\""
//						+ ", C.KIZOKU_END \"委嘱終了日\""
//						+ ", C.SHAKIN \"謝金\""
						+ ", C.URL \"URL\""
//						+ ", C.LEVEL_A1 \"分科細目コード(A)\""
//						+ ", C.LEVEL_B1_1 \"分科細目コード(B1-1)\""
//						+ ", C.LEVEL_B1_2 \"分科細目コード(B1-2)\""
//						+ ", C.LEVEL_B1_3 \"分科細目コード(B1-3)\""
//						+ ", C.LEVEL_B2_1 \"分科細目コード(B2-1)\""
//						+ ", C.LEVEL_B2_2 \"分科細目コード(B2-2)\""
//						+ ", C.LEVEL_B2_3 \"分科細目コード(B2-3)\""
//						+ ", C.KEY1 \"専門分野のキーワード（1）\""
//						+ ", C.KEY2 \"専門分野のキーワード（2）\""
//						+ ", C.KEY3 \"専門分野のキーワード（3）\""
//						+ ", C.KEY4 \"専門分野のキーワード（4）\""
//						+ ", C.KEY5 \"専門分野のキーワード（5）\""
//						+ ", C.KEY6 \"専門分野のキーワード（6）\""
//						+ ", C.KEY7 \"専門分野のキーワード（7）\""
						+ ", C.SENMON \"専門分野\""
						+ ", TO_CHAR(C.KOSHIN_DATE, 'YYYY/MM/DD') \"データ更新日\""
						+ ", C.BIKO \"備考\""
						+ ", TO_CHAR(D.LOGIN_DATE, 'YYYY/MM/DD') \"最終ログイン日付\""		//最終ログイン日追加
						+ ", A.UKETUKE_NO \"応募番号\""
						+ ", A.JIGYO_ID \"事業ID\""
						+ ", B.SHOZOKU_NAME \"研究機関名\""	
						+ ", B.SHOZOKU_CD \"機関番号\""
						+ ", B.BUNKASAIMOKU_CD \"細目番号\""
						+ ", B.BUNKATSU_NO \"分割番号\""
						+ ",NVL(SUBSTR(B.UKETUKE_NO,7,4),'    ') \"整理番号\"" 	
						+ ", A.JIGYO_NAME \"事業名\""
						+ ", B.JURI_SEIRI_NO \"整理番号（学創用）\""
						+ ", B.KADAI_NAME_KANJI \"研究課題名（和文）\""
						+ ", B.NAME_KANJI_SEI \"応募者氏名（漢字等-姓） \""
						+ ", B.NAME_KANJI_MEI \"応募者氏名（漢字等-名） \""
						+ ", A.KEKKA_ABC \"審査結果(ABC)\""
						+ ", A.KEKKA_TEN \"審査結果(点数)\""
//2006/11/06 苗　追加ここから
                        + ", REPLACE(REPLACE(NVL(A.RIGAI, '0'), '0', '利害関係なし'), '1', '利害関係あり') \"利害関係\""
                        + ", A.COMMENTS \"審査意見\""
                        + ", A.OTHER_COMMENT \"コメント\""
//2006/11/06　苗　追加ここまで
//						+ ", A.SENMON_CHK \"専門領域チェック\""
						+ ", A.COMMENT1 \"コメント1\""
						+ ", A.COMMENT2 \"コメント2\""
						+ ", A.COMMENT3 \"コメント3\""
						+ ", A.COMMENT4 \"コメント4\""
						+ ", A.COMMENT5 \"コメント5\""
						+ ", A.COMMENT6 \"コメント6\""
//						+ ", A.KENKYUNAIYO \"研究内容\""
//						+ ", A.KENKYUKEIKAKU \"研究計画\""
//						+ ", A.TEKISETSU_KAIGAI \"適切性-海外･企画調査･萌芽研究\""
//						+ ", A.TEKISETSU_KENKYU1 \"適切性-分担金\""
//						+ ", A.TEKISETSU \"適切性\""
//						+ ", A.DATO \"研究経費の妥当性\""
//						+ ", A.SHINSEISHA \"関係者等\""
//						+ ", A.SHINSEISHA \"研究代表者\""
//						+ ", A.KENKYUBUNTANSHA \"研究分担者\""
//						+ ", A.HITOGENOMU \"ヒトゲノム\""
//						+ ", A.TOKUTEI \"特定胚\""
//						+ ", A.HITOES \"ヒトES細胞\""
//						+ ", A.KUMIKAE \"遺伝子組換え実験\""
//						+ ", A.CHIRYO \"遺伝子治療臨床研究\""
//						+ ", A.EKIGAKU \"疫学研究\""
//						+ ", A.COMMENTS \"コメント\""
						+ ", REPLACE(REPLACE(A.SHINSA_JOKYO, '0', '未完了'), '1', '完了') \"審査状況\""
						//2007/5/8 追加
						+ ", REPLACE(REPLACE(NVL(A.NYURYOKU_JOKYO,'0'), '0', '未完了'), '1', '完了') \"利害関係入力完了状況\""
						//2007/5/8 追加完了
						+ " FROM SHINSAKEKKA A INNER JOIN SHINSEIDATAKANRI B ON A.SYSTEM_NO = B.SYSTEM_NO" +
								//" INNER JOIN MASTER_SHINSAIN C ON A.SHINSAIN_NO = C.SHINSAIN_NO AND A.JIGYO_KUBUN = C.JIGYO_KUBUN " +
								" INNER JOIN MASTER_SHINSAIN C ON A.SHINSAIN_NO = C.SHINSAIN_NO AND "+
//2006/05/10 追加ここから
//								" ((A.JIGYO_KUBUN = C.JIGYO_KUBUN) OR (A.JIGYO_KUBUN = 6 AND C.JIGYO_KUBUN = 4)) " +
                                " ((A.JIGYO_KUBUN = C.JIGYO_KUBUN) OR (" 
                                + " A.JIGYO_KUBUN IN ("
                                + StringUtil.changeArray2CSV(JigyoKubunFilter.KIBAN_JIGYO_KUBUN, true) + ")" 
                                + " AND C.JIGYO_KUBUN = '" + 
                                IJigyoKubun.JIGYO_KUBUN_KIBAN + "'))" +
//苗　追加ここまで                                
								" INNER JOIN JIGYOKANRI E ON A.JIGYO_ID = E.JIGYO_ID" +
								" INNER JOIN SHINSAININFO D ON SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO" 					
						+ " WHERE"
						+ " B.DEL_FLG = 0";
						

		//2005.11.03 iso 検索条件を別メソッドに独立
//		StringBuffer query = new StringBuffer(select);
//
//		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){					//審査員番号
//			query.append(" AND A.SHINSAIN_NO = '" + searchInfo.getShinsainNo() + "' ");
//		}
//		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){				//審査員氏名（漢字-姓）
//			query.append(" AND A.SHINSAIN_NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
//		}
//		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){				//審査員氏名（漢字-名）
//			query.append(" AND A.SHINSAIN_NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
//		}
//		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){				//審査員氏名（フリガナ-姓）
//			query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
//		}
//		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){				//審査員氏名（フリガナ-名）
//			query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
//		}
////		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){				//審査員所属機関名
////			query.append(" AND A.SHOZOKU_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");
////		}
////		if(searchInfo.getJigyoName() != null && !searchInfo.getJigyoName().equals("")){					//事業名（事業コード）
////			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '" + searchInfo.getJigyoName() + "' ");
////		}
//		if(searchInfo.getNendo() != null && !searchInfo.getNendo().equals("")){							//年度
//			query.append(" AND B.NENDO = " + searchInfo.getNendo());
//		}
//		if(searchInfo.getKaisu() != null && !searchInfo.getKaisu().equals("")){							//回数
//			query.append(" AND  B.KAISU = " + searchInfo.getKaisu());
//		}
//		if(searchInfo.getValues() != null && searchInfo.getValues().size() != 0){							//事業名（事業コード）
//			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
//			int i;
//			for(i = 0; i < searchInfo.getValues().size()-1; i++) {
//				query.append("'" + searchInfo.getValues().get(i) + "', ");
//			}
//			query.append("'" + searchInfo.getValues().get(i) + "')");
//		}
//		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){					//審査員所属機関コード
//			query.append(" AND B.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
//		}
//		//業務担当者の担当事業区分を取得
//		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);
//		if(shinsaTaishoSet != null && shinsaTaishoSet.size() != 0){
//			query.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(), false))
//				 .append(")");
//		}
////		Set set = userInfo.getGyomutantoInfo().getTantoJigyoKubun();
////		if(set != null) {
////			String comma = "";
////			query.append(" AND B.JIGYO_KUBUN IN (");
////			//学術創成（非公募）が含まれていれば
////			if(set.contains(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
////				query.append("'" + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO + "'");
////				comma = ", ";
////			}
////			//基盤研究が含まれていれば
////			if(set.contains(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
////				query.append(comma + "'" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'");
////			}
////			query.append(")");
////		}
//		//系等の区分と略称の両方を検索する
//		if(searchInfo.getKeiName() != null && !searchInfo.getKeiName().equals("")){						//系等の区分
//			query.append(" AND (B.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
//				+ "%' OR KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
//		}
//		if(searchInfo.getShinsaJokyo() != null && !searchInfo.getShinsaJokyo().equals("")
//				&& !searchInfo.getShinsaJokyo().equals("2")){												//審査状況
//			query.append(" AND A.SHINSA_JOKYO = '" + EscapeUtil.toSqlString(searchInfo.getShinsaJokyo()) + "'");
//		}
//		
//		//最終ログイン日を追加		2005/10/25追加 
//		if (searchInfo.getLoginDate() != null && searchInfo.getLoginDate().length() != 0){
//			if("1".equals(searchInfo.getLoginDate()) ){
//				query.append(" AND E.LOGIN_DATE IS NOT NULL");
//			}else{
//				query.append(" AND E.LOGIN_DATE IS NULL ");
//			}	 
//		}
//		
//		//利害関係者を追加			2005/10/26追加
//		if(searchInfo.getRigaiKankeisha() != null && searchInfo.getRigaiKankeisha().length() != 0){
//			if("1".equals(searchInfo.getRigaiKankeisha()) ){
//				query.append(" AND A.RIGAI = '" + searchInfo.getRigaiKankeisha() +"'");
//			}else{
//				query.append(" AND ( A.RIGAI IS NULL OR A.RIGAI = '" + searchInfo.getRigaiKankeisha() + "' ) ");
//			}
//		}
//
//		//整理番号を追加	2005/11/2
//		if(searchInfo.getSeiriNo() != null && !searchInfo.getSeiriNo().equals("")){				//審査員氏名（フリガナ-姓）
//			query.append(" AND B.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//				
//		//ソート順（審査員番号、事業ID、申請番号の昇順）
//		query.append(" ORDER BY A.SHINSAIN_NO, A.JIGYO_ID, B.UKETUKE_NO ");
		String query = getQueryString(select, userInfo, searchInfo, true);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		


		//-----------------------
		// リスト取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectCsvList(connection,query.toString(), true);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV出力データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}


	/**
	 * 
	 * 表示方式から、審査状況一覧・審査件数一覧を判断する。
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return 検索結果を格納したPageオブジェクト
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoSearchInfo)
	 */
	public Page search(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {
		Page page = null;
		if(!StringUtil.isBlank(searchInfo.getHyojiHoshikiShinsaJokyo())) {
			if("1".equals(searchInfo.getHyojiHoshikiShinsaJokyo())) {
				//審査状況一覧
				page =  searchJokyo(userInfo, searchInfo);
			} else if("2".equals(searchInfo.getHyojiHoshikiShinsaJokyo())) {
				//審査件数一覧
				page = searchKensu(userInfo, searchInfo);
			}
		} else {
			page =  searchJokyo(userInfo, searchInfo);		//NULLの時「リセット」「再審査」ができないため、応急処置　2005/11/14馬場
				
//			throw new ApplicationException(
//				"検索条件が不正です。",
//				new ErrorInfo("errors.4004"));
			
		}
		return page;
	}

	/**
	 * SQL文の作成・発行.<br /><br />
	 * 
	 * 1.SQL文の作成<br /><br />
	 * まず、以下のSQL文を作成する。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *  SELECT
	 * 		B.NENDO
	 * 		, B.KAISU
	 * 		, B.JIGYO_NAME
	 * 		, A.SYSTEM_NO
	 * 		, A.SHINSAIN_NO
	 * 		, A.SHINSAIN_NAME_KANJI_SEI
	 * 		, A.SHINSAIN_NAME_KANJI_MEI
	 * 		, B.NAME_KANJI_SEI
	 * 		, B.NAME_KANJI_MEI
	 * 		, B.KADAI_NAME_KANJI
	 *		, A.KEKKA_ABC
	 *		, A.KEKKA_TEN
	 *		, C.SHINSAKIGEN
	 *		, A.SHINSA_JOKYO
	 *	 	, B.JIGYO_KUBUN
	 * 		, B.JOKYO_ID
	 * 		, A.JIGYO_ID
	 * 	FROM
	 * 		SHINSAKEKKA A
	 * 		, SHINSEIDATAKANRI B
	 * 		, JIGYOKANRI C
	 * 	WHERE
	 * 		A.SYSTEM_NO = B.SYSTEM_NO
	 * 		AND A.JIGYO_ID = C.JIGYO_ID
	 * 		AND A.SHINSAIN_NO NOT LIKE '@%'
	 * 		AND B.DEL_FLG = 0 </pre>
	 *	
	 *				<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 *	
	 *	ORDER BY A.SHINSAIN_NO, A.JIGYO_ID, B.UKETUKE_NO
	 * </td></tr>
	 * </table><br />
	 * 
	 *	<b><span style="color:#002288">動的検索条件</span></b><br />
	 *	引数のsearchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>shinsainNo</td><td>AND A.SHINSAIN_NO = '審査員番号'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（漢字等－姓）</td><td>nameKanjiSei</td><td>AND A.SHINSAIN_NAME_KANJI_SEI LIKE '%氏名（漢字等－姓）%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（漢字等－名）</td><td>nameKanjiMei</td><td>AND A.SHINSAIN_NAME_KANJI_MEI LIKE '%氏名（漢字等－名）%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ－姓）</td><td>nameKanaSei</td><td>AND A.NAME_KANA_SEI LIKE '%氏名（フリガナ－姓）%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ－名）</td><td>nameKanaMei</td><td>AND A.NAME_KANA_MEI LIKE '%氏名（フリガナ－名）%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>年度</td><td>nendo</td><td>AND B.NENDO = '年度'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>回数</td><td>kaisu</td><td>AND B.KAISU = '回数'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>values</td><td>AND SUBSTR(A.JIGYO_ID,3,5) IN ('事業コード1,事業コード2…')</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>shozokuCd</td><td>AND B.SHOZOKU_CD = '所属機関コード'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>担当事業区分</td><td>-</td><td>AND B.JIGYO_KUBUN IN (1,4)</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>系統の区分</td><td>keiName</td><td>AND (B.KEI_NAME LIKE '%系統の区分%' OR KEI_NAME_RYAKU LIKE '%系統の区分%')</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査状況</td><td>shinsaJokyo</td><td>AND A.SHINSA_JOKYO = '審査状況'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID(再審査用)</td><td>jigyoId</td><td>AND A.JIGYO_ID = '事業ID(再審査用)'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分(再審査用)</td><td>jigyoKubun</td><td>AND A.JIGYO_KUBUN = '事業区分(再審査用)'</td></tr>
	 * 	</table>
	 *	<div style="font-size:8pt">※担当事業区分はユーザが業務担当者の場合、担当する事業区分によってIN句の値が変わる。事業区分の定義は以下の通り。<br/>
	 *	1:学術創成（推薦分）　4:基盤研究</div><br />
	 * 
	 * 2.Pageの作成・返却<br /><br />
	 * 検索結果をPageオブジェクトへセットし呼び出し元へ返却する。なお、Pageオブジェクトにセットされる情報は、すべての検索結果を格納したListで、
	 * 各要素には1レコード分の情報を、列名をキーに値をセットしたMapが格納されている。
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return 検索結果を格納したPageオブジェクト
	 */
	public Page searchJokyo(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {

		
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------

		String select = "SELECT"
							+ " B.NENDO"
							+ ", B.KAISU"
							+ ", B.JIGYO_NAME"
							+ ", A.SYSTEM_NO"
							+ ", A.SHINSAIN_NO"
							+ ", A.SHINSAIN_NAME_KANJI_SEI"
							+ ", A.SHINSAIN_NAME_KANJI_MEI"
//							+ ", A.SHOZOKU_NAME"
							+ ", B.NAME_KANJI_SEI"
							+ ", B.NAME_KANJI_MEI"
							+ ", B.KADAI_NAME_KANJI"
							+ ", A.KEKKA_ABC"
							+ ", A.KEKKA_TEN"
							+ ", A.RIGAI"
//							+ ", A.SENMON_CHK"
							+ ", C.SHINSAKIGEN"
//							+ ", C.SHINSA_TYPE"
							+ ", A.SHINSA_JOKYO"
							+ ", A.NYURYOKU_JOKYO"  // 2006/10/24 于広偉
							+ ", B.JIGYO_KUBUN"
							+ ", B.JOKYO_ID"
							+ ", A.JIGYO_ID"
							+ ", D.LOGIN_DATE"
							+ ", B.JURI_SEIRI_NO"
						+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, JIGYOKANRI C, SHINSAININFO D"
						+ " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
						+ " AND A.JIGYO_ID = C.JIGYO_ID"
						+ " AND SUBSTR(D.SHINSAIN_ID,4,7)  = A.SHINSAIN_NO "
						+ " AND A.SHINSAIN_NO NOT LIKE '@%'"
						+ " AND B.DEL_FLG = 0";
	  	
		//2005.11.03 iso 検索条件を別メソッドに独立
//		StringBuffer query = new StringBuffer(select);
//
//		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){					//審査員番号
//			query.append(" AND A.SHINSAIN_NO = '" + searchInfo.getShinsainNo() + "' ");
//		}
//		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){				//審査員氏名（漢字-姓）
//			query.append(" AND A.SHINSAIN_NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
//		}
//		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){				//審査員氏名（漢字-名）
//			query.append(" AND A.SHINSAIN_NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
//		}
//		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){				//審査員氏名（フリガナ-姓）
//			query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
//		}
//		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){				//審査員氏名（フリガナ-名）
//			query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
//		}
////		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){				//審査員所属機関名
////			query.append(" AND A.SHOZOKU_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");
////		}
////		if(searchInfo.getJigyoName() != null && !searchInfo.getJigyoName().equals("")){					//事業名（事業コード）
////			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '" + searchInfo.getJigyoName() + "' ");
////		}
//		if(searchInfo.getNendo() != null && !searchInfo.getNendo().equals("")){							//年度
//			query.append(" AND B.NENDO = " + searchInfo.getNendo());
//		}
//		if(searchInfo.getKaisu() != null && !searchInfo.getKaisu().equals("")){							//回数
//			query.append(" AND  B.KAISU = " + searchInfo.getKaisu());
//		}
//		if(searchInfo.getValues() != null && searchInfo.getValues().size() != 0){							//事業名（事業コード）
//			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
//			int i;
//			for(i = 0; i < searchInfo.getValues().size()-1; i++) {
//				query.append("'" + searchInfo.getValues().get(i) + "', ");
//			}
//			query.append("'" + searchInfo.getValues().get(i) + "')");
//		}
//		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){					//審査員所属機関コード
//			query.append(" AND B.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
//		}
//		//業務担当者の担当事業区分を取得
//		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);
//		if(shinsaTaishoSet != null && shinsaTaishoSet.size() != 0){
//			query.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(), false))
//				 .append(")");
//		}
////		Set set = userInfo.getGyomutantoInfo().getTantoJigyoKubun();
////		if(set != null) {
////			String comma = "";
////			query.append(" AND B.JIGYO_KUBUN IN (");
////			//学術創成（非公募）が含まれていれば
////			if(set.contains(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
////				query.append("'" + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO + "'");
////				comma = ", ";
////			}
////			//基盤研究が含まれていれば
////			if(set.contains(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
////				query.append(comma + "'" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'");
////			}
////			query.append(")");
////		}
//		//系等の区分と略称の両方を検索する
//		if(searchInfo.getKeiName() != null && !searchInfo.getKeiName().equals("")){						//系等の区分
//			query.append(" AND (B.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
//				+ "%' OR KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
//		}
//		if(searchInfo.getShinsaJokyo() != null && !searchInfo.getShinsaJokyo().equals("")
//				&& !searchInfo.getShinsaJokyo().equals("2")){												//審査状況
//			query.append(" AND A.SHINSA_JOKYO = '" + EscapeUtil.toSqlString(searchInfo.getShinsaJokyo()) + "'");
//		}	
//		
////最終ログイン日を追加		2005/10/25追加 
//		if (searchInfo.getLoginDate() != null && searchInfo.getLoginDate().length() != 0){
//			if("1".equals(searchInfo.getLoginDate()) ){
//				query.append(" AND D.LOGIN_DATE IS NOT NULL");
//			}else{
//				query.append(" AND D.LOGIN_DATE IS NULL ");
//			}	 
//		}
//
////利害関係者を追加			2005/10/25追加
//		if(searchInfo.getRigaiKankeisha() != null && searchInfo.getRigaiKankeisha().length() != 0){
//			if("1".equals(searchInfo.getRigaiKankeisha()) ){
//				query.append(" AND A.RIGAI = '" + searchInfo.getRigaiKankeisha() + "'");
//			}else{
//				query.append(" AND ( A.RIGAI IS NULL OR A.RIGAI = '" + searchInfo.getRigaiKankeisha() + "' ) ");
//			}
//		}
////整理番号を追加	2005/11/2
//		if(searchInfo.getSeiriNo() != null && !searchInfo.getSeiriNo().equals("")){				//審査員氏名（フリガナ-姓）
//			query.append(" AND B.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//				  			
//		if(searchInfo.getJigyoId() != null && !searchInfo.getJigyoId().equals("")){						//事業ID(再審査用)
//			query.append(" AND A.JIGYO_ID = " + searchInfo.getJigyoId());
//		}
//		if(searchInfo.getJigyoKubun() != null && !searchInfo.getJigyoKubun().equals("")){					//事業区分(再審査用)
//			query.append(" AND A.JIGYO_KUBUN = " + searchInfo.getJigyoKubun());
//		}
//
//		//ソート順（審査員番号、事業ID、申請番号の昇順）
//		query.append(" ORDER BY A.SHINSAIN_NO, A.JIGYO_ID, B.UKETUKE_NO ");
		String query = getQueryString(select, userInfo, searchInfo, true);
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ページ取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection,searchInfo, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査状況データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * 審査件数一覧を返す。
	 * 一覧表示で、年度・回数を考慮していないので、
	 * 年度・回数が異なっても、同じ事業ならまとめてカウントする。
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return 検索結果を格納したPageオブジェクト
	 */
	public Page searchKensu(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {

		Page result = null;
		int totalSize = 0;
		
		//全体の検索結果ではページ制御が不可能なので、
		//審査結果からダミーを除いた審査員番号をORDERして取得し、
		//スタートポジションからページサイズ分取得する。
		int startPosition = searchInfo.getStartPosition();
		int pageSize = searchInfo.getPageSize();
		
		//ページ制御は審査員番号で行うため、全体では無効にしておく。
		searchInfo.setStartPosition(0);
		searchInfo.setPageSize(0);
		
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		
		//対象審査員Noを検索するSQL部分
		String selectShinsainNo = "SELECT DISTINCT SHINSAIN_NO"
								+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, SHINSAININFO D"
								+ " WHERE"
								+ " A.SYSTEM_NO = B.SYSTEM_NO"
								+ " AND SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO"
								+ " AND A.SHINSAIN_NO NOT LIKE '@%'"
								+ " AND B.DEL_FLG = 0"
								//若手研究（スタートアップ）を追加 2006/4/3
								//+ " AND A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//2006/05/09 追加ここから                                 
//								+ " AND (A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//								+ " OR A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART + "'"
//                                + " OR A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI + "')"
                                + " AND A.JIGYO_KUBUN IN ("
                                + StringUtil.changeIterator2CSV(JigyoKubunFilter.Convert2ShinseishoJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(),true)
                                + ")"
//苗　追加ここまで                                
                                //追加完了
								;
		String queryShinsainNo = getQueryString(selectShinsainNo, userInfo, searchInfo, false);

		//対象審査員NoをカウントするSQL
		String countShinsainNo = "SELECT COUNT(SHINSAIN_NO) FROM ("
								+ queryShinsainNo
								+ ")"
								;

		//ページデータ検索SQL
		//対象が基盤のみなので、入力・未入力判定は、「利害フラグ」「審査結果(点数)」で行っている。
		String select = "SELECT"
						+ " SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD"
						+ ", A.JIGYO_KUBUN"						//2005.11.15 iso 審査員情報表示のため事業区分を追加
						+ ", A.SHINSAIN_NO"
//2006/11/02 苗　修正ここから                        
//						+ ", A.SHINSAIN_NAME_KANJI_SEI"
//						+ ", A.SHINSAIN_NAME_KANJI_MEI"
                        + ", E.NAME_KANJI_SEI"
                        + ", E.NAME_KANJI_MEI"
//2006/11/02　苗　修正ここまで                        
						+ ", D.LOGIN_DATE"
						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) ALL_COUNT"
//2007/5/8				+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) - SUM(DECODE((A.KEKKA_TEN || A.RIGAI), NULL, 0, '', 0, 1)) NOT_INPUT_COUNT"
						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) - SUM(DECODE((A.KEKKA_TEN || A.RIGAI), NULL, 0, '', 0, '0', 0, 1)) NOT_INPUT_COUNT"
					+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, SHINSAININFO D"
//2006/11/02 苗　追加ここから
                    + " , MASTER_SHINSAIN E"
//2006/11/02　苗　追加ここまで                    
					+ " WHERE"
					+ " A.SYSTEM_NO = B.SYSTEM_NO"
					+ " AND SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO"
					//ここからページ制御
//2006/11/02 苗　修正ここから                       
//					+ " AND SHINSAIN_NO IN"
                    + " AND A.SHINSAIN_NO IN"
//2006/11/02　苗　修正ここまで                     
					+ " (SELECT SHINSAIN_NO FROM "
					+ "  (SELECT SHINSAIN_NO, ROWNUM ROW_COUNT FROM"
					+ "   (" + queryShinsainNo + " ORDER BY SHINSAIN_NO)"
					+ "  )"
					+ "  WHERE ROW_COUNT BETWEEN " + (startPosition + 1)
					+ "  AND " + (startPosition + pageSize)
					+ " )"
					//ここまでページ制御
					+ " AND B.DEL_FLG = 0"
//2006/11/02 苗　追加ここから 
                    + " AND A.SHINSAIN_NO = E.SHINSAIN_NO"
                    + " AND E.JIGYO_KUBUN = "
                    + IJigyoKubun.JIGYO_KUBUN_KIBAN
//2006/11/02　苗　追加ここまで                     
					;
		
		//1つヒットしたら全事業を出すので、検索条件をコメント化
//		String query = getHyojiQueryString(slect, userInfo, searchInfo);
        
//      2006/10/26  検索表示指定の研究種目 に 追加 于広偉 
        select = getQueryString(select, userInfo, searchInfo, false);
		
		String query = select
				//2005.11.15 iso 審査員情報表示のため事業区分を追加
//				+ " GROUP BY SUBSTR(A.JIGYO_ID,3,5), A.SHINSAIN_NO, A.SHINSAIN_NAME_KANJI_SEI, A.SHINSAIN_NAME_KANJI_MEI, D.LOGIN_DATE"
//2006/11/02 苗　修正ここから        
//				+ " GROUP BY SUBSTR(A.JIGYO_ID,3,5), A.JIGYO_KUBUN, A.SHINSAIN_NO, A.SHINSAIN_NAME_KANJI_SEI, A.SHINSAIN_NAME_KANJI_MEI, D.LOGIN_DATE"
		        + " GROUP BY SUBSTR(A.JIGYO_ID,3,5), A.JIGYO_KUBUN, A.SHINSAIN_NO, E.NAME_KANJI_SEI, E.NAME_KANJI_MEI, D.LOGIN_DATE"
//2006/11/02　苗　修正ここまで
				+ " ORDER BY A.SHINSAIN_NO, JIGYO_CD"
				;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ページ取得
		//-----------------------
		Connection connection = null;
		ShinsaKekkaInfoDao shinsaKekkaDao = new ShinsaKekkaInfoDao(userInfo);
		try {
			connection = DatabaseUtil.getConnection();
			
			totalSize = shinsaKekkaDao.countTotalPage(connection, countShinsainNo);
			if(totalSize < 1) {
				throw new NoDataFoundException("該当データがありませんでした。", new ErrorInfo("errors.5002"));
			}
			
			result = SelectUtil.selectPageInfo(connection,searchInfo, query);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査状況データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		HashMap newMap = new HashMap();					//新しく作る審査員ごとのリスト(新List1行分)
		ArrayList newList = new ArrayList();			//ページ情報として出力するリスト
		String beforeShinsainNo = "";
		int allCount = 0;									//すべての全件
		int notInputCount = 0;								//すべての未入力
		int i = 0;
		for(Iterator ite = result.getList().iterator(); ite.hasNext(); i++) {
			Map recordMap = (Map)ite.next();
			String shinsainNo = recordMap.get("SHINSAIN_NO").toString();

			//1個前の審査員番号と一致した場合、1行分のデータなのでputする。
			if(shinsainNo.equals(beforeShinsainNo)) {
				newMap.put("ALL_COUNT_" + recordMap.get("JIGYO_CD").toString(), recordMap.get("ALL_COUNT"));
				newMap.put("NOT_INPUT_COUNT_" + recordMap.get("JIGYO_CD").toString(), recordMap.get("NOT_INPUT_COUNT"));
				//累積カウントを追加
				allCount += Integer.parseInt(recordMap.get("ALL_COUNT").toString());
				notInputCount += Integer.parseInt(recordMap.get("NOT_INPUT_COUNT").toString());
			} else {
				//1個前と違う審査員番号の場合、前の審査員の1行分データを新CSVリストに登録
				//最初は必ず空データなので、登録しない
				if(i != 0) {
					//すべてのカウントをセットして初期化する
					newMap.put("ALL_COUNT", Integer.toString(allCount));
					newMap.put("NOT_INPUT_COUNT", Integer.toString(notInputCount));
					allCount =0;
					notInputCount = 0;
					newList.add(newMap);
				}
				
				//現審査員分の基本データを作成
				newMap = new HashMap();
				newMap.put("JIGYO_KUBUN", recordMap.get("JIGYO_KUBUN"));		//2005.11.15 iso 審査員情報表示のため事業区分を追加
				newMap.put("SHINSAIN_NO", shinsainNo);
//2006/11/02 苗　修正ここから                
//				newMap.put("SHINSAIN_NAME_KANJI_SEI", recordMap.get("SHINSAIN_NAME_KANJI_SEI"));
//				newMap.put("SHINSAIN_NAME_KANJI_MEI", recordMap.get("SHINSAIN_NAME_KANJI_MEI"));
                newMap.put("NAME_KANJI_SEI", recordMap.get("NAME_KANJI_SEI"));
                newMap.put("NAME_KANJI_MEI", recordMap.get("NAME_KANJI_MEI"));
//2006/11/02　苗　修正ここまで                
				newMap.put("LOGIN_DATE", recordMap.get("LOGIN_DATE"));
				
				//各事業ごとのNAMEには、事業コードを付加
				newMap.put("ALL_COUNT_" + recordMap.get("JIGYO_CD").toString(), recordMap.get("ALL_COUNT"));
				newMap.put("NOT_INPUT_COUNT_" + recordMap.get("JIGYO_CD").toString(), recordMap.get("NOT_INPUT_COUNT"));

				//累積カウントを追加
				allCount += Integer.parseInt(recordMap.get("ALL_COUNT").toString());
				notInputCount += Integer.parseInt(recordMap.get("NOT_INPUT_COUNT").toString());
				
				//現在の審査員番号を次の比較に使うためにセット
				beforeShinsainNo = shinsainNo;
			}

			//最後のデータは上のelseに引っかからないので、ここでcsvListに格納する。
			if(i == result.getList().size()-1) {
				//すべてのカウントをセット
				newMap.put("ALL_COUNT", Integer.toString(allCount));
				newMap.put("NOT_INPUT_COUNT", Integer.toString(notInputCount));
				newList.add(newMap);							//出力CSVに審査員ごとにまとめたデータをセット
			}
		}
		
		return new Page(newList, startPosition, pageSize, totalSize);
	}


	//2005.11.08 iso 追加
	/**
	 * 審査件数一覧のCSVを返す。
	 * 一覧表示で、年度・回数を考慮していないので、
	 * 年度・回数が異なっても、同じ事業ならまとめてカウントする。
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return 検索結果を格納したPageオブジェクト
	 */
	public List searchKensuCsvData(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {

		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------

		//2005.12.02 iso このSQLだと入力された検索条件のみしか出力されない。
		//審査員がヒットすれば、全事業の件数を出すよう変更
//		//ページデータ検索SQL
//		//対象が基盤のみなので、入力・未入力判定は、「利害フラグ」「審査結果(点数)」で行っている。
//		String slect = "SELECT"
//						+ " C.SHINSAIN_NO"										//審査員番号
//						+ ", C.NAME_KANJI_SEI"									//審査員名(漢字－姓)	※ページとは別のテーブルから取得
//						+ ", C.NAME_KANJI_MEI"									//審査員名(漢字－名)	※ページとは別のテーブルから取得
//						+ ", C.NAME_KANA_SEI"									//審査員名(フリガナ－姓)
//						+ ", C.NAME_KANA_MEI"									//審査員名(フリガナ－名)
//						+ ", C.SHOZOKU_CD"										//審査員所属機関コード
//						+ ", C.SHOZOKU_NAME"									//審査員所属機関名
//						+ ", C.BUKYOKU_NAME"									//審査員部局名
//						+ ", C.SHOKUSHU_NAME"									//審査員職種名
//						+ ", C.SOFU_ZIP"										//送付先(郵便番号)
//						+ ", C.SOFU_ZIPADDRESS"									//送付先(住所)
//						+ ", C.SOFU_ZIPEMAIL"									//送付先(Email)
//						+ ", C.SHOZOKU_TEL"										//電話番号
//						+ ", C.URL"												//URL
//						+ ", C.SENMON"											//専門分野
//						+ ", TO_CHAR(C.KOSHIN_DATE , 'YYYY/MM/DD') KOSHIN_DATE"	//データ更新日
//						+ ", C.BIKO"											//備考
//						+ ", TO_CHAR(D.LOGIN_DATE, 'YYYY/MM/DD') LOGIN_DATE"	//ログイン日
//						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) ALL_COUNT"
//						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) - SUM(DECODE((A.KEKKA_TEN || A.RIGAI), NULL, 0, '', 0, 1)) NOT_INPUT_COUNT"
//						+ ", SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD"
//					+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, MASTER_SHINSAIN C, SHINSAININFO D"
//					+ " WHERE"
//					+ " A.SYSTEM_NO = B.SYSTEM_NO"
//					+ " AND SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO"
//					+ " AND C.SHINSAIN_NO = A.SHINSAIN_NO"
//					+ " AND A.SHINSAIN_NO NOT LIKE '@%'"
//					+ " AND B.DEL_FLG = 0"
//					//学創で同じ審査員NOがいると倍の件数になるので、念のため審査員マスタの区分にする。
//					//ページ検索では、審査結果テーブルなので注意
//					+ " AND C.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//					;
//
//		String query = getQueryString(slect, userInfo, searchInfo, false);
//				
//		query += " GROUP BY SUBSTR(A.JIGYO_ID,3,5), C.SHINSAIN_NO, C.NAME_KANJI_SEI"
//				+ ", C.NAME_KANA_MEI, C.NAME_KANA_SEI, C.NAME_KANJI_MEI, C.SHOZOKU_CD"
//				+ ", C.SHOZOKU_NAME, C.BUKYOKU_NAME, C.SHOKUSHU_NAME, C.SOFU_ZIP"
//				+ ", C.SOFU_ZIPADDRESS, C.SOFU_ZIPEMAIL, C.SHOZOKU_TEL, C.URL, C.SENMON"
//				+ ", C.KOSHIN_DATE, C.BIKO, D.LOGIN_DATE"
//				+ " ORDER BY C.SHINSAIN_NO, JIGYO_CD"
//				;
		
		//対象審査員Noを検索するSQL部分
		String selectShinsainNo = "SELECT DISTINCT SHINSAIN_NO"
								+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, SHINSAININFO D"
								+ " WHERE"
								+ " A.SYSTEM_NO = B.SYSTEM_NO"
								+ " AND SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO"
								+ " AND A.SHINSAIN_NO NOT LIKE '@%'"
								+ " AND B.DEL_FLG = 0"
//2006/05/09 苗　追加ここから                                 
//								+ " AND (A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//							    + " AND A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//								+ " OR A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART + "')"
                                + " AND A.JIGYO_KUBUN IN ("
                                + StringUtil.changeIterator2CSV(JigyoKubunFilter.Convert2ShinseishoJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(),true)
                                + ")"
//2006/05/09　苗　追加ここまで                                     

								;
		String queryShinsainNo = getQueryString(selectShinsainNo, userInfo, searchInfo, false);

		//ページデータ検索SQL
		//対象が基盤のみなので、入力・未入力判定は、「利害フラグ」「審査結果(点数)」で行っている。
		String select = "SELECT"
						+ " C.SHINSAIN_NO"										//審査員番号
						+ ", C.NAME_KANJI_SEI"									//審査員名(漢字－姓)	※ページとは別のテーブルから取得
						+ ", C.NAME_KANJI_MEI"									//審査員名(漢字－名)	※ページとは別のテーブルから取得
						+ ", C.NAME_KANA_SEI"									//審査員名(フリガナ－姓)
						+ ", C.NAME_KANA_MEI"									//審査員名(フリガナ－名)
						+ ", C.SHOZOKU_CD"										//審査員所属機関コード
						+ ", C.SHOZOKU_NAME"									//審査員所属機関名
						+ ", C.BUKYOKU_NAME"									//審査員部局名
						+ ", C.SHOKUSHU_NAME"									//審査員職種名
						+ ", C.SOFU_ZIP"										//送付先(郵便番号)
						+ ", C.SOFU_ZIPADDRESS"								    //送付先(住所)
						+ ", C.SOFU_ZIPEMAIL"									//送付先(Email)
						+ ", C.SHOZOKU_TEL"									    //電話番号
						+ ", C.URL"											    //URL
						+ ", C.SENMON"											//専門分野
						+ ", TO_CHAR(C.KOSHIN_DATE , 'YYYY/MM/DD') KOSHIN_DATE" //データ更新日
						+ ", C.BIKO"											//備考
						+ ", TO_CHAR(D.LOGIN_DATE, 'YYYY/MM/DD') LOGIN_DATE"	//ログイン日
//2006/11/06 苗　追加ここから
                        + ", SUM(NVL(A.SHINSA_JOKYO, 0)) SHINSA_JOKYO"        	//審査状況
//2006/11/06　苗　追加ここまで                        
                        + ", SUM(NVL(A.NYURYOKU_JOKYO, 0)) NYURYOKU_JOKYO"      //利害関係入力完了状況 2007/5/9
						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) ALL_COUNT"
//2007/5/8				+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) - SUM(DECODE((A.KEKKA_TEN || A.RIGAI), NULL, 0, '', 0, 1)) NOT_INPUT_COUNT"
						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) - SUM(DECODE((A.KEKKA_TEN || A.RIGAI), NULL, 0, '', 0, '0', 0, 1)) NOT_INPUT_COUNT"
//2006/11/06 苗　追加ここから
                        + ", SUM(NVL(A.RIGAI,'0'))"                             //利益相反入力件数
//2006/11/06　苗　追加ここまで                         
						+ ", SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD"
					+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, MASTER_SHINSAIN C, SHINSAININFO D"
					+ " WHERE"
					+ " A.SYSTEM_NO = B.SYSTEM_NO"
					+ " AND SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO"
					+ " AND C.SHINSAIN_NO = A.SHINSAIN_NO"
					+ " AND B.DEL_FLG = 0"
					//学創で同じ審査員NOがいると倍の件数になるので、念のため審査員マスタの区分にする。
					//審査員番号検索部分では、審査結果テーブルなので注意
//2006/05/09 苗　追加ここから  
//					+ " AND (CC.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//                  + " AND  CC.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//					+ " OR CC.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART + "')"
                    + " AND C.JIGYO_KUBUN IN ("
                    + StringUtil.changeIterator2CSV(JigyoKubunFilter.Convert2ShinseishoJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(),true)
                    + ")"
//2006/05/09　苗　追加ここまで   
					+ " AND A.SHINSAIN_NO IN (" + queryShinsainNo + ")"
					;
//2006/11/06 苗　追加ここから
        select = getQueryString(select, userInfo, searchInfo, false);
//2006/11/06　苗　追加ここまで        
		select += " GROUP BY SUBSTR(A.JIGYO_ID,3,5), C.SHINSAIN_NO, C.NAME_KANJI_SEI"
				+ ", C.NAME_KANA_MEI, C.NAME_KANA_SEI, C.NAME_KANJI_MEI, C.SHOZOKU_CD"
				+ ", C.SHOZOKU_NAME, C.BUKYOKU_NAME, C.SHOKUSHU_NAME, C.SOFU_ZIP"
				+ ", C.SOFU_ZIPADDRESS, C.SOFU_ZIPEMAIL, C.SHOZOKU_TEL, C.URL, C.SENMON"
//審査件数一覧ＣＳＶ出力に種目の件数が不正のため修正 2006/11/15
//				+ ", C.KOSHIN_DATE, C.BIKO, D.LOGIN_DATE, A.SHINSA_JOKYO"
				+ ", C.KOSHIN_DATE, C.BIKO, D.LOGIN_DATE"
				+ " ORDER BY C.SHINSAIN_NO, JIGYO_CD"
				;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		List result = null;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			result = SelectUtil.selectCsvList(connection, select, false);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV出力データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		//CSVカラムの作成
//2006/11/06　苗　追加ここから
        StringBuffer columnName = new StringBuffer();
        columnName.append("審査員割振番号");
        columnName.append(",氏名(漢字等－姓)");
        columnName.append(",氏名(漢字等－名)");
        columnName.append(",氏名(フリガナ－姓)");
        columnName.append(",氏名(フリガナ－名)");
        columnName.append(",所属研究機関名(番号)");
        columnName.append(",所属研究機関名(和文)");
        columnName.append(",部局名(和文)");
        columnName.append(",職種名称");
        columnName.append(",送付先(郵便番号)");
        columnName.append(",送付先(住所)");
        columnName.append(",送付先(Email)");
        columnName.append(",電話番号");
        columnName.append(",URL");
        columnName.append(",専門分野");
        columnName.append(",データ更新日");
        columnName.append(",備考");
        columnName.append(",最終ログイン日");
        columnName.append(",審査状況");
        columnName.append(",利害関係入力完了状況");	//2007/5/9追加
        columnName.append(",合計(未入力)");
        columnName.append(",合計(すべて)");
        if (!searchInfo.getValues().isEmpty()){
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_S)) {
                columnName.append(",基盤研究(S)(未入力)");
                columnName.append(",基盤研究(S)(すべて)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN)) {
                columnName.append(",基盤研究(A)一般(未入力)");
                columnName.append(",基盤研究(A)一般(すべて)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI)) {
                columnName.append(",基盤研究(A)海外(未入力)");
                columnName.append(",基盤研究(A)海外(すべて)");
            }
            if ( searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN)) {
                columnName.append(",基盤研究(B)一般(未入力)");
                columnName.append(",基盤研究(B)一般(すべて)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI)) {
                columnName.append(",基盤研究(B)海外(未入力)");
                columnName.append(",基盤研究(B)海外(すべて)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN)) {
                columnName.append(",基盤研究(C)一般(未入力)");
                columnName.append(",基盤研究(C)一般(すべて)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU)) {
                columnName.append(",基盤研究(C)企画(未入力)");
                columnName.append(",基盤研究(C)企画(すべて)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_HOGA)) {
                columnName.append(",萌芽研究(未入力)");
                columnName.append(",萌芽研究(すべて)");
            }
            //2007/5/9若手S追加
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S)) {
                columnName.append(",若手研究(S)(未入力)");
                columnName.append(",若手研究(S)(すべて)");
            }            
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A)) {
                columnName.append(",若手研究(A)(未入力)");
                columnName.append(",若手研究(A)(すべて)");
            }            
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B)) {
                columnName.append(",若手研究(B)(未入力)");
                columnName.append(",若手研究(B)(すべて)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_WAKATESTART)) {
                columnName.append(",若手研究（スタートアップ）(未入力)");
                columnName.append(",若手研究（スタートアップ）(すべて)");
            }
//2007/5/18 修正
//            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A)) {
//                columnName.append(",特別研究促進費(基盤研究(A)相当)（未入力）");
//                columnName.append(",特別研究促進費(基盤研究(A)相当)（すべて）");
//            }
//            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B)) {
//                columnName.append(",特別研究促進費(基盤研究(B)相当)（未入力）");
//                columnName.append(",特別研究促進費(基盤研究(B)相当)（すべて）");
//            }
//2007/5/18 修正完了
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C)) {
//                columnName.append(",特別研究促進費(基盤研究(C)相当)（未入力）");
//                columnName.append(",特別研究促進費(基盤研究(C)相当)（すべて）");
                columnName.append(",特別研究促進費(年複数回の試行)（未入力）");
                columnName.append(",特別研究促進費(年複数回の試行)（すべて）");
            }
//          2007/5/18 修正
//            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A)) {
//                columnName.append(",特別研究促進費(若手研究(A)相当)（未入力）");
//                columnName.append(",特別研究促進費(若手研究(A)相当)（すべて）");
//            }
//            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B)) {
//                columnName.append(",特別研究促進費(若手研究(B)相当)（未入力）");
//                columnName.append(",特別研究促進費(若手研究(B)相当)（すべて）");
//            }
//          2007/5/18 修正完了
        }
        columnName.append(",利益相反入力件数");

//		String[] columnArray = {"審査員番号"
//								, "氏名(漢字等－姓)"
//								, "氏名(漢字等－名)"
//								, "氏名(フリガナ－姓)"
//								, "氏名(フリガナ－名)"
//								, "所属研究機関名(番号)"
//								, "所属研究機関名(和文)"
//								, "部局名(和文)"
//								, "職種名称"
//								, "送付先(郵便番号)"
//								, "送付先(住所)"
//								, "送付先(Email)"
//								, "電話番号"
//								, "URL"
//								, "専門分野"
//								, "データ更新日"
//								, "備考"
//								, "最終ログイン日"                              
//								, "合計(未入力)"
//								, "合計(すべて)"
//								, "基盤研究(S)(未入力)"
//								, "基盤研究(S)(すべて)"
//								, "基盤研究(A)一般(未入力)"
//								, "基盤研究(A)一般(すべて)"
//								, "基盤研究(A)海外(未入力)"
//								, "基盤研究(A)海外(すべて)"
//								, "基盤研究(B)一般(未入力)"
//								, "基盤研究(B)一般(すべて)"
//								, "基盤研究(B)海外(未入力)"
//								, "基盤研究(B)海外(すべて)"
//								, "基盤研究(C)一般(未入力)"
//								, "基盤研究(C)一般(すべて)"
//								, "基盤研究(C)企画(未入力)"
//								, "基盤研究(C)企画(すべて)"
//								, "萌芽研究(未入力)"
//								, "萌芽研究(すべて)"
//								, "若手研究(A)(未入力)"
//								, "若手研究(A)(すべて)"
//								, "若手研究(B)(未入力)"
//								, "若手研究(B)(すべて)"
//								//ADD START LY 2006/04/10
//								, "若手研究（スタートアップ）(未入力) "
//								, "若手研究（スタートアップ）(すべて) "
//								//ADD END LY 2006/04/10
////2006/05/23 苗　追加ここから
//                                , "特別研究促進費(基盤研究(A)相当)（未入力）"
//                                , "特別研究促進費(基盤研究(A)相当)（すべて）"
//                                , "特別研究促進費(基盤研究(B)相当)（未入力）"
//                                , "特別研究促進費(基盤研究(B)相当)（すべて）"
//                                , "特別研究促進費(基盤研究(C)相当)（未入力）"
//                                , "特別研究促進費(基盤研究(C)相当)（すべて）"
//                                , "特別研究促進費(若手研究(A)相当)（未入力）"
//                                , "特別研究促進費(若手研究(A)相当)（すべて）"
//                                , "特別研究促進費(若手研究(B)相当)（未入力）"
//                                , "特別研究促進費(若手研究(B)相当)（すべて）"
////2006/05/23　苗　追加ここまで                          
//     							};
        String[] columnArray = columnName.toString().split(",");
//2006/11/06 苗　追加ここまで
//2006/11/06　苗　修正ここから        
//		String[] initArray = new String[NUM_CSV_COUNT];
//		for(int i = 0; i < NUM_CSV_COUNT; i++) {
//			initArray[i] = "0";
//		}
        //データが空の件数を0としなければならないので、件数の初期配列に0を格納しておく
        String[] initArray = new String[columnArray.length - NUM_CSV_COMMON];
        for(int i = 0; i < columnArray.length - NUM_CSV_COMMON; i++) {
            initArray[i] = "0";
        }
        //選択の研究種目の数
        int shumokuNum = columnArray.length - NUM_CSV_COMMON - 3;
        List tempList = searchInfo.getValues();
        //選択の研究種目の格納
        String[] jigyoCds = new String[shumokuNum / 2];
        //研究種目に順に並べた 
        for(int i = 0 ; i < shumokuNum / 2 ; i++){
            if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_S)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_S;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_S);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
            } else if ( tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_HOGA)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_HOGA;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
//2007/5/9 若手S追加
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/5/9 若手S追加完了
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_WAKATESTART)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_WAKATESTART;
                tempList.remove(IJigyoCd.JIGYO_CD_WAKATESTART);
//              2007/5/18 修正
//            } else if (tempList.contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A)) {
//                jigyoCds[i] = IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A;
//                tempList.remove(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
//            } else if (tempList.contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B)) {
//                jigyoCds[i] = IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B;
//                tempList.remove(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C;
                tempList.remove(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
//              2007/5/18 修正
//            } else if (tempList.contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A)) {
//                jigyoCds[i] = IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A;
//                tempList.remove(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
//            } else if (tempList.contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B)) {
//                jigyoCds[i] = IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B;
//                tempList.remove(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
            }
        }
//2006/11/06 苗　修正ここまで
		List newList = new ArrayList(Arrays.asList(columnArray));	//新しく作る審査員ごとのリスト(新List1行分)
		List newCsvList = new ArrayList();							//ページ情報として出力するリスト
		String beforeShinsainNo = "";
		int allCountSubete = 0;									    //すべての全件
		int notInputCountSubete = 0;								//すべての未入力
        int rigaiSubete = 0;                                        //すべての利害関係
        int shinsaJokyo = 0;                                        //審査状況
        int rigaiJokyo = 0;                                        	//利害関係入力完了状況
		int i = 0;
		for(Iterator ite = result.iterator(); ite.hasNext(); i++) {
			List recordList = (List)ite.next();
			String shinsainNo = recordList.get(0).toString();		//審査員番号
			
			//2005.11.15 iso この部分は変数としては使わない(部分Listとして切り出している)のでコメント化
//			String nameKanjiSei = recordList.get(1).toString();		//審査員名(漢字－姓)
//			String nameKanjiMei = recordList.get(2).toString();		//審査員名(漢字－名)
//			String nameKanaSei = recordList.get(3).toString();		//審査員名(フリガナ－姓)
//			String nameKanaMei = recordList.get(4).toString();		//審査員名(フリガナ－名)
//			String shozokuCd = recordList.get(5).toString();		//審査員所属機関コード
//			String shozokuName = recordList.get(6).toString();		//審査員所属機関名
//			String bukyokuName = recordList.get(7).toString();		//審査員部局名
//			String shokushuName = recordList.get(8).toString();		//審査員職種名
//			String sofuZip = recordList.get(9).toString();			//送付先(郵便番号)
//			String sofuZipAddress = recordList.get(10).toString();	//送付先(住所)
//			String sofuZipEmail = recordList.get(11).toString();	//送付先(Email)
//			String shozokuTel = recordList.get(12).toString();		//電話番号
//			String url = recordList.get(13).toString();				//URL
//			String senmon = recordList.get(14).toString();			//専門分野
//			String koshinDate = recordList.get(15).toString();		//データ更新日
//			String biko = recordList.get(16).toString();			//備考
//			String loginDate = recordList.get(17).toString();		//ログイン日
//			String allCount = recordList.get(18).toString();		//件数(すべて)
//			String motInputCount = recordList.get(19).toString();	//件数(未入力)
			
//2006/11/06 苗　修正ここから
//            String jigyoCd = recordList.get(20).toString();           //事業コード
            String jigyoCd = recordList.get(NUM_CSV_COMMON + 3).toString();//事業コード        

			//1個前の審査員番号と一致した場合、1行分のデータなのでputする
            int j = 0;
			if(shinsainNo.equals(beforeShinsainNo)) {
				//各事業の件数を更新する
				//各事業の順番はカラムリストに合わせること
				//※すぐ下に同じ処理がもう1個、たいしたことないけど独立メソッドにすべき？               
//                if(IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyoCd)) {                 //基盤研究(S)：00031
//                    newList.set(NUM_CSV_COMMON+2, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+3, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyoCd)) {        //基盤研究(A)(一般)：00041
//                    newList.set(NUM_CSV_COMMON+4, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+5, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyoCd)) {       //基盤研究(A)(海外学術調査)：00043
//                    newList.set(NUM_CSV_COMMON+6, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+7, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyoCd)) {        //基盤研究(B)(一般)：00051
//                    newList.set(NUM_CSV_COMMON+8, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+9, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyoCd)) {       //基盤研究(B)(海外学術調査)：00053
//                    newList.set(NUM_CSV_COMMON+10, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+11, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd)) {        //基盤研究(C)(一般)：00061
//                    newList.set(NUM_CSV_COMMON+12, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+13, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd)) {       //基盤研究(C)(企画調査)：00062
//                    newList.set(NUM_CSV_COMMON+14, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+15, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyoCd)) {           //萌芽研究：00111
//                    newList.set(NUM_CSV_COMMON+16, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+17, recordList.get(18));
//                }else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd)) {        //若手研究(A)：00121
//                    newList.set(NUM_CSV_COMMON+18, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+19, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)) {       //若手研究(B)：00131
//                    newList.set(NUM_CSV_COMMON+20, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+21, recordList.get(18));
//                } 
//                //add start ly 2006/04/10
//                else if(IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyoCd)) {            //若手研究（スタートアップ）：00141
//                    newList.set(NUM_CSV_COMMON+22, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+23, recordList.get(18));
//                } 
//                //add end ly 2006/04/10
////2006/05/23　追加ここから
//                else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyoCd)) {     //特別研究促進費（基盤研究(A)相当）：00152
//                    newList.set(NUM_CSV_COMMON+24, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+25, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyoCd)) {     //特別研究促進費（基盤研究(B)相当）：00153
//                    newList.set(NUM_CSV_COMMON+26, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+27, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyoCd)) {     //特別研究促進費（基盤研究(C)相当）：00154
//                    newList.set(NUM_CSV_COMMON+28, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+29, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyoCd)) {     //特別研究促進費（若手研究(A)相当）：00155
//                    newList.set(NUM_CSV_COMMON+30, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+31, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyoCd)) {     //特別研究促進費（若手研究(B)相当）：00156
//                    newList.set(NUM_CSV_COMMON+32, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+33, recordList.get(18));
//                }    
////苗　追加ここまで
                //基盤研究(S)：00031
                if (IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_S)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //基盤研究(A)(一般)：00041    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //基盤研究(A)(海外学術調査)：00043
                } else if (IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //基盤研究(B)(一般)：00051
                } else if (IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //基盤研究(B)(海外学術調査)：00053
                } else if (IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //基盤研究(C)(一般)：00061     
                } else if (IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //基盤研究(C)(企画調査)：00062    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //萌芽研究：00111    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_HOGA)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //若手研究(S)：00120　2007/5/9追加    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //若手研究(A)：00121    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //若手研究(B)：00131    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //若手研究（スタートアップ）：00141
                } else if (IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_WAKATESTART)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
//                  2007/5/18 修正
//                //特別研究促進費（基盤研究(A)相当）：00152
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
//                //特別研究促進費（基盤研究(B)相当）：00153
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
                //特別研究促進費（基盤研究(C)相当）：00154
                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
// 2007/5/18 修正
//                //特別研究促進費（若手研究(A)相当）：00155   
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyoCd)) {
//                    //特別研究促進費（若手研究(A)相当）：00155
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
//                //特別研究促進費（若手研究(B)相当）：00156 
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
                }            
//2006/11/06 苗　修正ここまで
				//累積カウントを追加
				notInputCountSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON + 1).toString());
				allCountSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON).toString());
                rigaiSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON + 2).toString());
                //利害関係入力完了状況を追加した為 2007/5/9
                //shinsaJokyo += Integer.parseInt(recordList.get(NUM_CSV_COMMON - 1).toString());
                shinsaJokyo += Integer.parseInt(recordList.get(NUM_CSV_COMMON - 2).toString());
                rigaiJokyo += Integer.parseInt(recordList.get(NUM_CSV_COMMON - 1).toString());
			} else {
				//累積カウントをセットしたら初期化
				//最初のカラムでは行わない。
				if(i != 0) {
					newList.set(NUM_CSV_COMMON, Integer.toString(notInputCountSubete));
					newList.set(NUM_CSV_COMMON + 1, Integer.toString(allCountSubete));
                    newList.set(columnArray.length - 1, Integer.toString(rigaiSubete));
                    if(shinsaJokyo == allCountSubete){
                        newList.set(NUM_CSV_COMMON - 2, "完了");
                    } else {
                        newList.set(NUM_CSV_COMMON - 2, "未完了");
                    }
                    if(rigaiJokyo == allCountSubete){
                        newList.set(NUM_CSV_COMMON - 1, "完了");
                    } else {
                        newList.set(NUM_CSV_COMMON - 1, "未完了");
                    }
					allCountSubete =0;
					notInputCountSubete = 0;
                    rigaiSubete = 0;
                    shinsaJokyo = 0;
                    rigaiJokyo = 0;
				}
				//1個前と違う審査員番号の場合、前の審査員の1行分データを新CSVリストに登録
				newCsvList.add(newList);
				
				//現審査員分の基本データを作成
				//共通部分をセット
				newList = new ArrayList(recordList.subList(0, NUM_CSV_COMMON));
				//件数部分の初期状態をセット
				newList.addAll(Arrays.asList(initArray));

				//2005.12.02 iso 各審査員ごとの1個目の事業分の更新
				//各事業の件数を更新する
				//各事業の順番はカラムリストに合わせること
//2006/11/06 苗　修正ここから                
//                if(IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyoCd)) {                 //基盤研究(S)：00031
//                    newList.set(NUM_CSV_COMMON+2, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+3, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyoCd)) {        //基盤研究(A)(一般)：00041
//                    newList.set(NUM_CSV_COMMON+4, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+5, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyoCd)) {       //基盤研究(A)(海外学術調査)：00043
//                    newList.set(NUM_CSV_COMMON+6, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+7, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyoCd)) {        //基盤研究(B)(一般)：00051
//                    newList.set(NUM_CSV_COMMON+8, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+9, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyoCd)) {       //基盤研究(B)(海外学術調査)：00053
//                    newList.set(NUM_CSV_COMMON+10, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+11, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd)) {        //基盤研究(C)(一般)：00061
//                    newList.set(NUM_CSV_COMMON+12, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+13, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd)) {       //基盤研究(C)(企画調査)：00062
//                    newList.set(NUM_CSV_COMMON+14, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+15, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyoCd)) {           //萌芽研究：00111
//                    newList.set(NUM_CSV_COMMON+16, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+17, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd)) {       //若手研究(A)：00121
//                    newList.set(NUM_CSV_COMMON+18, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+19, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)) {       //若手研究(B)：00131
//                    newList.set(NUM_CSV_COMMON+20, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+21, recordList.get(18));
//                } 
//                //add start ly 2006/04/10
//                else if(IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyoCd)) {            //若手研究（スタートアップ）：00141
//                    newList.set(NUM_CSV_COMMON+22, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+23, recordList.get(18));
//                } 
//                //add end ly 2006/04/10
////2006/05/23　追加ここから
//                else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyoCd)) {     //特別研究促進費（基盤研究(A)相当）：00152
//                    newList.set(NUM_CSV_COMMON+24, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+25, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyoCd)) {     //特別研究促進費（基盤研究(B)相当）：00153
//                    newList.set(NUM_CSV_COMMON+26, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+27, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyoCd)) {     //特別研究促進費（基盤研究(C)相当）：00154
//                    newList.set(NUM_CSV_COMMON+28, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+29, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyoCd)) {     //特別研究促進費（若手研究(A)相当）：00155
//                    newList.set(NUM_CSV_COMMON+30, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+31, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyoCd)) {     //特別研究促進費（若手研究(B)相当）：00156
//                    newList.set(NUM_CSV_COMMON+32, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+33, recordList.get(18));
//                }    
////苗　追加ここまで  
                //基盤研究(S)：00031
                if (IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_S)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //基盤研究(A)(一般)：00041    
				} else if(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyoCd)) {		//基盤研究(A)(一般)：00041
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //基盤研究(A)(海外学術調査)：00043
                } else if (IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //基盤研究(B)(一般)：00051
                } else if (IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //基盤研究(B)(海外学術調査)：00053
                } else if (IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //基盤研究(C)(一般)：00061     
                } else if (IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //基盤研究(C)(企画調査)：00062    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //萌芽研究：00111    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_HOGA)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //若手研究(S)：00120  2007/5/8 追加
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //若手研究(A)：00121    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //若手研究(B)：00131    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //若手研究（スタートアップ）：00141
                } else if (IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_WAKATESTART)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
// 2007/5/18 修正
//                    //特別研究促進費（基盤研究(A)相当）：00152
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
//                    //特別研究促進費（基盤研究(B)相当）：00153
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
                    //特別研究促進費（基盤研究(C)相当）：00154
                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
// 2007/5/18 修正
//                    //特別研究促進費（若手研究(A)相当）：00155   
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyoCd)) {
//                    //特別研究促進費（若手研究(A)相当）：00155
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
//                    //特別研究促進費（若手研究(B)相当）：00156 
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
                } 
//2006/11/06 苗　修正ここまで
				//累積カウントを追加
				notInputCountSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON + 1).toString());
				allCountSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON).toString());
                rigaiSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON + 2).toString());
                shinsaJokyo += Integer.parseInt(recordList.get(NUM_CSV_COMMON - 2).toString());
                rigaiJokyo += Integer.parseInt(recordList.get(NUM_CSV_COMMON - 1).toString());
				
				//現在の審査員番号を次の比較に使うためにセット
				beforeShinsainNo = shinsainNo;
			}

			//最後のデータは上のelseに引っかからないので、ここでcsvListに格納する。
			if(i == result.size()-1) {
				//累積カウントをセットしたら初期化
				newList.set(NUM_CSV_COMMON, Integer.toString(notInputCountSubete));
				newList.set(NUM_CSV_COMMON + 1, Integer.toString(allCountSubete));  
                newList.set(columnArray.length - 1, Integer.toString(rigaiSubete));
                if(shinsaJokyo == allCountSubete){
                    newList.set(NUM_CSV_COMMON - 2, "完了");
                } else {
                    newList.set(NUM_CSV_COMMON - 2, "未完了");
                }
                if(rigaiJokyo == allCountSubete){
                    newList.set(NUM_CSV_COMMON - 1, "完了");
                } else {
                    newList.set(NUM_CSV_COMMON - 1, "未完了");
                }
                //出力CSVに審査員ごとにまとめたデータをセット
				newCsvList.add(newList);
			}
		}  
		return newCsvList;
	}

	/**
	 * 審査状況の更新.<br /><br />
	 * 
	 * 1.審査員の有無のチェック<br /><br />
	 * まず再審査対象審査員が存在するかチェックする。<br />
	 * <b>shinsainInfoDao</b>の<b>selectShinsainInfo</b>メソッドを用い、審査員が存在する場合には審査員情報を取得する。<br />
	 * 再審査担当の審査員が削除されている場合は、例外をthrowする。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *  SELECT
	 * 		SI.SHINSAIN_ID SHINSAIN_ID
	 * 		, MS.SHINSAIN_NO SHINSAIN_NO
	 * 		, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 		, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 		, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 		, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 		, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 		, MS.SHOZOKU_CD SHOZOKU_CD
	 * 		, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 		, MS.BUKYOKU_NAME BUKYOKU_NAME
	 *		, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 *		, MS.SOFU_ZIP SOFU_ZIP
	 *		, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 *		, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 *	 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 		, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 		, MS.URL URL
	 *		, MS.SENMON SENMON
	 *		, MS.KOSHIN_DATE KOSHIN_DATE
	 *		, MS.BIKO BIKO
	 *		, SI.PASSWORD PASSWORD
	 *		, SI.YUKO_DATE YUKO_DATE
	 *		, SI.DEL_FLG DEL_FLG
	 * 	FROM
	 * 		MASTER_SHINSAIN MS
	 * 		, SHINSAININFO SI
	 * 	WHERE
	 * 		SI.DEL_FLG = 0
	 * 		AND MS.SHINSAIN_NO = ?
	 * 		AND MS.JIGYO_KUBUN = ?
	 * 		AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7) 
	 * 		AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)	</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数shinsainInfoの変数(ShinsainNo)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数shinsainInfoの変数(JigyoKubun)を使用する。</td></tr>
	 * </table><br />
	 * 
	 * 2.審査結果情報の更新<br /><br />
	 * <b>shinsaKekkaInfoDao</b>の<b>updateShinsainInfo</b>メソッドを用いて、担当審査結果情報の審査員情報を更新する。<br />
	 * 今回は第四引数の<b>shinsaJokyoInfo.getJigyoId()</b>がnullではないため、審査完了しているものが対象となる。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	UPDATE SHINSAKEKKA
	 *	SET
	 * 		SHINSAIN_NO = ?			-- 審査員番号
	 * 		, JIGYO_KUBUN = ?				-- 事業区分
	 * 		, SHINSAIN_NAME_KANJI_SEI = ?	-- 審査員名（漢字-姓）
	 * 		, SHINSAIN_NAME_KANJI_MEI = ?	-- 審査員名（漢字-名）
	 * 		, NAME_KANA_SEI = ?		-- 審査員名（フリガナ－姓）
	 * 		, NAME_KANA_MEI = ?		-- 審査員名（フリガナ－名）
	 * 		, SHOZOKU_NAME = ?		-- 審査員所属機関名
	 * 		, BUKYOKU_NAME = ?		-- 審査員部局名
	 * 		, SHOKUSHU_NAME = ?		-- 審査員職名
	 *	WHERE
	 * 		SHINSAIN_NO = ?				-- 審査員番号
	 * 		AND JIGYO_KUBUN = ?			-- 事業区分
	 * 		AND SHINSA_JOKYO = 1	-- 審査状況（1:審査状況（完了））
	 *	
	 *			<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 * 
	 * 		AND EXISTS (
	 * 			SELECT * 
	 * 			FROM
	 * 				SHINSEIDATAKANRI A
	 * 				, SHINSAKEKKA B
	 * 			WHERE
	 * 				A.DEL_FLG = 0
	 * 				AND A.SYSTEM_NO = B.SYSTEM_NO
	 * 		)
	 * 	</pre>
	 * </td></tr>
	 * </table><br />
	 * バインド変数
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数shinsaJokyoInfoの変数(ShinsainNo)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数shinsaJokyoInfoの変数(JigyoKubun)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（漢字-姓）</td><td>第二引数shinsaJokyoInfoの変数(NameKanjiSei)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（漢字-名）</td><td>第二引数shinsaJokyoInfoの変数(NameKanjiMei)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－姓）</td><td>第二引数shinsaJokyoInfoの変数(NameKanaSei)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－名）</td><td>第二引数shinsaJokyoInfoの変数(NameKanaMei)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員所属機関名</td><td>第二引数shinsaJokyoInfoの変数(ShozokuName)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員部局名</td><td>第二引数shinsaJokyoInfoの変数(BukyokuName)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員職名</td><td>第二引数shinsaJokyoInfoの変数(ShokushuName)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数shinsaJokyoInfoの変数(ShinsainNo)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数shinsaJokyoInfoの変数(JigyoKubun)を使用する。</td></tr>
	 * </table><br />
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数shinsaJokyoInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoId</td><td>AND JIGYO_ID = '事業ID'</td></tr>
	 * </table><br/>
	 * 
	 * 3.審査状況の更新<br /><br />
	 * <b>shinsaKekkaInfoDao</b>の<b>updateShinsaKekkaInfo</b>メソッドを用い、審査結果情報の審査状況を更新する。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	UPDATE
	 * 		SHINSAKEKKA A
	 *	SET
	 * 		A.SHINSA_JOKYO = ?		-- 審査状況
	 *	WHERE
	 * 		A.SYSTEM_NO IN (
	 * 			SELECT
	 * 				B.SYSTEM_NO
	 * 			FROM
	 * 				SHINSEIDATAKANRI B
	 * 				, SHINSAKEKKA C
	 * 			WHERE
	 * 				B.SYSTEM_NO=C.SYSTEM_NO
	 * 				AND B.DEL_FLG=0
	 * 				AND (
	 * 					B.JOKYO_ID = '10'
	 * 						-- 申請状況(10:「1次審査中」)
	 * 					OR B.JOKYO_ID = '11'
	 * 						-- 申請状況(11:「1次審査完了」)
	 * 				)
	 *	
	 *				<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 * 
	 * 			)
	 * 		AND A.SHINSAIN_NO = ?			-- 審査員番号
	 * 		AND A.JIGYO_KUBUN = ?			-- 担当事業区分
	 * 		AND A.JIGYO_ID = ?				-- 事業ID
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 * バインド変数
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>審査状況</td><td>第二引数shinsaJokyoInfoの変数(ShinsainNo)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数shinsaJokyoInfoの変数(JigyoKubun)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>担当事業区分</td><td>第二引数shinsaJokyoInfoの変数(NameKanjiSei)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td><b>null</b>をセット</td></tr>
	 * </table>
	 * <br />
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param shinsaJokyoInfo ShinsaJokyoInfo
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#saishinsa(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoSearchInfo)
	 */
	public void saishinsa(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo)
			throws ApplicationException, ValidationException {
		
//		//審査状況フラグを未完了に設定
//		UserInfo userInfoShinsain = new UserInfo();
//		userInfoShinsain.setShinsainInfo(shinsainInfo);
//		ShinsaKekkaMaintenance shinsaKekkaMaintenance = new ShinsaKekkaMaintenance();
//		shinsaKekkaMaintenance.updateJigyoShinsaCompleteYet(userInfoShinsain, shinsaJokyoInfo.getJigyoId());
		
		
		//再審査対象審査員が存在するかチェック
		ShinsainInfo shinsainInfo = new ShinsainInfo();
		shinsainInfo.setJigyoKubun(shinsaJokyoInfo.getJigyoKubun());
		shinsainInfo.setShinsainNo(shinsaJokyoInfo.getShinsainNo());

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			try {
				//再審査担当の審査員が削除されている場合、エラーとする。
				ShinsainInfoDao shinsainInfoDao = new ShinsainInfoDao(userInfo);
				//存在する場合、審査結果テーブルに最新の審査員情報を反映させるため、審査員情報を取得する。
				shinsainInfo = shinsainInfoDao.selectShinsainInfo(connection, shinsainInfo);
			} catch(NoDataFoundException e) {
				//エラー情報保持用リスト
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.5030"));
				throw new ValidationException(
					"担当の審査員が削除されています。",
					errors);
			}
			shinsainInfo.setJigyoKubun(shinsaJokyoInfo.getJigyoKubun());
			
			ShinsaKekkaInfoDao shinsaKekkaInfoDao = new ShinsaKekkaInfoDao(userInfo);
			//再審査対象事業の審査結果情報で、審査完了しているものが対象。
			shinsaKekkaInfoDao.updateShinsainInfo(connection,
												  shinsainInfo,
												  ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE,
												  shinsaJokyoInfo.getJigyoId());
			//再審査設定を行う。
			shinsaKekkaInfoDao.updateShinsaKekkaInfo(connection, 
													 shinsainInfo.getShinsainNo(), 
													 shinsainInfo.getJigyoKubun(), 
													 null,
													 shinsaJokyoInfo.getJigyoId(),
													 ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET);

			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;
		} catch(NoDataFoundException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査結果データ更新中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"審査結果データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
	}
	
//2006/10/25 jinbaogang add start
	/**
	 * 利益相反再入力完了。
	 * @param userInfo				実行するユーザ情報。
	 * @param shinsaJokyoInfo		利益相反再入力状況情報。
	 * @throws ApplicationException	
	 * @throws ValidationException	
	 */
	public void updateSaiNyuryoku(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo)
			throws ApplicationException, ValidationException {
			
		ShinsainInfo shinsainInfo = new ShinsainInfo();
		shinsainInfo.setJigyoKubun(shinsaJokyoInfo.getJigyoKubun());
		shinsainInfo.setShinsainNo(shinsaJokyoInfo.getShinsainNo());

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			try {
				ShinsainInfoDao shinsainInfoDao = new ShinsainInfoDao(userInfo);
				shinsainInfo = shinsainInfoDao.selectShinsainInfo(connection, shinsainInfo);
			} catch(NoDataFoundException e) {
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.5030"));
				throw new ValidationException(
					"利益相反再入力完了",
					errors);
			}
			shinsainInfo.setJigyoKubun(shinsaJokyoInfo.getJigyoKubun());
			
			ShinsaKekkaInfoDao shinsaKekkaInfoDao = new ShinsaKekkaInfoDao(userInfo);
			//利益相反再入力完了を行う
			shinsaKekkaInfoDao.updateNyuryokuJokyo(connection, 
													 shinsainInfo.getShinsainNo(), 
													 shinsainInfo.getJigyoKubun(), 
													 shinsaJokyoInfo.getJigyoId(),
													 ShinsaKekkaMaintenance.NYURYOKUJOKYO_COMPLETE_YET);

			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;
		} catch(NoDataFoundException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査結果データ更新中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"審査結果データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}		
	}	
//2006/10/25 adde jinbaogang  end
	
	/**
	 * 審査結果のクリア.<br /><br />
	 * 
	 * 以下のSQLを実行し、審査結果をクリアする。
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	 UPDATE
	 *	 	SHINSAKEKKA
	 *	 SET
	 *	 	 KEKKA_ABC = NULL	-- 総合評価（ABC）
	 *	 	,KEKKA_TEN = NULL	-- 総合評価（点数）
	 *	 	,COMMENT1 = NULL	-- コメント1
	 *	 	,COMMENT2 = NULL	-- コメント2
	 *	 	,COMMENT3 = NULL	-- コメント3
	 *	 	,COMMENT4 = NULL	-- コメント4
	 *	 	,COMMENT5 = NULL	-- コメント5
	 *	 	,COMMENT6 = NULL	-- コメント6
	 *	 	,KENKYUNAIYO = NULL	-- 研究内容
	 *	 	,KENKYUKEIKAKU = NULL	-- 研究計画
	 *	 	,TEKISETSU_KAIGAI = NULL	-- 適切性-海外
	 *	 	,TEKISETSU_KENKYU1 = NULL	-- 適切性-研究（1）
	 *	 	,TEKISETSU = NULL	-- 適切性
	 *	 	,DATO = NULL	-- 妥当性
	 *	 	,SHINSEISHA = NULL	-- 研究代表者
	 *	 	,KENKYUBUNTANSHA = NULL	-- 研究分担者
	 *	 	,HITOGENOMU = NULL	-- ヒトゲノム
	 *	 	,TOKUTEI = NULL	-- 特定胚
	 *	 	,HITOES = NULL	-- ヒトES細胞
	 *	 	,KUMIKAE = NULL	-- 遺伝子組換え実験
	 *	 	,CHIRYO = NULL	-- 遺伝子治療臨床研究
	 *	 	,EKIGAKU = NULL	-- 疫学研究
	 *	 	,COMMENTS = NULL	-- コメント
	 *	 	,TENPU_PATH = NULL	-- 添付ファイル格納パス
	 *	 WHERE
	 *	 	SHINSAIN_NO = ?	-- 審査員番号
	 *	 	AND JIGYO_ID = ?	-- 事業ID
	 *	 	AND SHINSA_JOKYO = '0'	-- 審査状況（未完了）
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 * ※申請データの審査結果（ソート済み）についてはクリアせず、審査未完了のものだけ更新する。
	 * 
	 * @param userInfo	UserInfo
	 * @param shinsaJokyoInfo	クリアする申請情報（ShinsaJokyoInfo）
	 * @throws ApplicationException
	 */
	public void clearShinsaKekka(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo)
			throws ApplicationException
	{
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			ShinsaKekkaInfoDao shinsaKekkaInfoDao = new ShinsaKekkaInfoDao(userInfo);
			shinsaKekkaInfoDao.clearShinsaKekka(connection, shinsaJokyoInfo);
			
			//2005.01.24
			//申請データテーブルの評価順にソートされた1次審査結果情報を更新する。
			//複数箇所で使用するので別メソッドにすべき？
			//申請データ管理DAO
			ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);

			//クリア対象審査結果情報(配列)を取得する。
			ShinsaKekkaInfo[] tantoShinsaKekkaInfoArray = null;					
			try {
				tantoShinsaKekkaInfoArray = shinsaKekkaInfoDao.selectShinsaKekkaInfo(connection, shinsaJokyoInfo);
			} catch(NoDataFoundException e) {
				throw e;
			} catch(DataAccessException e) {
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//申請データの書き換えは複数対象。
			for(int i = 0; i < tantoShinsaKekkaInfoArray.length; i++) {
				//排他制御のため既存データを取得する
				ShinseiDataPk shinseiDataPk = new ShinseiDataPk(tantoShinsaKekkaInfoArray[i].getSystemNo());
				ShinseiDataInfo existInfo = null;
				try{
					existInfo = shinseiDataInfoDao.selectShinseiDataInfoForLock(connection, shinseiDataPk, true);
				}catch(NoDataFoundException e){
					throw e;
				}catch(DataAccessException e){
					throw new ApplicationException(
						"申請書管理データ排他取得中にDBエラーが発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}
				//---申請データ削除フラグチェック---
				String delFlag = existInfo.getDelFlg(); 
				if(FLAG_APPLICATION_DELETE.equals(delFlag)) {
					throw new ApplicationException(
						"当該申請データは削除されています。SystemNo=" + tantoShinsaKekkaInfoArray[i].getSystemNo(),
						new ErrorInfo("errors.9001"));
				}
				//---申請データステータスチェック---
				String jyokyoId = existInfo.getJokyoId();
				//---審査員割り振り処理後、割り振りチェック完了、1次審査中、1次審査完了以外の場合はエラー
				if( !(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO.equals(jyokyoId)) &&
					 !(StatusCode.STATUS_WARIFURI_CHECK_KANRYO.equals(jyokyoId)) &&
					 !(StatusCode.STATUS_1st_SHINSATYU.equals(jyokyoId)) &&
					 !(StatusCode.STATUS_1st_SHINSA_KANRYO.equals(jyokyoId)) )
				{
					throw new ApplicationException(
						"当該申請データは１次審査登録可能なステータスではありません。SystemNo="
						+ tantoShinsaKekkaInfoArray[i].getSystemNo(),
						new ErrorInfo("errors.9012"));
				}
	
				//---審査結果レコード取得（結果ABCの昇順）---
				ShinsaKekkaInfo[] shinsaKekkaInfoArray = null;						
				try{
					shinsaKekkaInfoArray = shinsaKekkaInfoDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
				}catch(NoDataFoundException e){
					throw e;
				}catch(DataAccessException e){
					throw new ApplicationException(
						"審査結果データ取得中にDBエラーが発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}
	
				//統一性をとるためfor中だが、外に出すべき?
				//---総合評価マスタから総合評価情報の一覧を取得する---					
				HashMap sogoHyokaMap = new HashMap();
				List hyokaList = MasterSogoHyokaInfoDao.selectSogoHyokaInfoList(connection);
				Iterator iterator = hyokaList.iterator();
				while(iterator.hasNext()){
					Map map = (Map)iterator.next();
					String sogoHyoka = (String) map.get("SOGO_HYOKA");											//総合評価
					String jigyoKubun = new Integer(((Number) map.get("JIGYO_KUBUN")).intValue()).toString();	//事業区分
					String tensu =  new Integer(((Number) map.get("TENSU")).intValue()).toString();			//点数
					sogoHyokaMap.put(jigyoKubun + sogoHyoka, tensu);											//キー：事業区分+総合評価、値：点数 
				}
	
				//---DB更新---
				try {
					String kekkaAbc = "";
					int intKekkaTen = 0;
					String kekkaTenSorted = "";
					boolean kekkaTenFlag = false;
					for(int j=0; j<shinsaKekkaInfoArray.length; j++){
						try{
							//総合評価（ABC）と総合評価（点数）は混在しない
							if(shinsaKekkaInfoArray[j].getKekkaAbc() != null){
								kekkaAbc = kekkaAbc + shinsaKekkaInfoArray[j].getKekkaAbc();
								String tensu = (String) sogoHyokaMap.get(shinsaKekkaInfoArray[j].getJigyoKubun()
															+ shinsaKekkaInfoArray[j].getKekkaAbc());
								if(tensu == null){
									throw new ApplicationException(
										"総合評価マスタに一致するデータが存在しません。検索キー：総合評価'"
										+ shinsaKekkaInfoArray[j].getKekkaAbc() 
										+ "',事業区分：'" +  shinsaKekkaInfoArray[j].getJigyoKubun() + "'",
										new ErrorInfo("errors.4002"));	
								}
								intKekkaTen = intKekkaTen
													+ Integer.parseInt((String) sogoHyokaMap.get(
																	shinsaKekkaInfoArray[j].getJigyoKubun()
																		 + shinsaKekkaInfoArray[j].getKekkaAbc()));
								kekkaTenFlag = true;	//1つでも点数が設定されていた場合[true]						
							}else if(shinsaKekkaInfoArray[j].getKekkaTen() != null){
									//１次審査結果(点数)
									intKekkaTen = intKekkaTen
														+ Integer.parseInt((String) sogoHyokaMap.get(
																		shinsaKekkaInfoArray[j].getJigyoKubun()
																			 + shinsaKekkaInfoArray[j].getKekkaTen()));
									
									//１次審査結果(点数順)
									if(kekkaTenSorted.equals("")){
										kekkaTenSorted = kekkaTenSorted + shinsaKekkaInfoArray[j].getKekkaTen();
									}else{
										kekkaTenSorted = kekkaTenSorted + " " + shinsaKekkaInfoArray[j].getKekkaTen();									
									}
									
								kekkaTenFlag = true;	//1つでも点数が設定されていた場合[true]
							}
						}catch(NumberFormatException e){
							//数値として認識できない場合は処理を飛ばす
						}
					}
					
					//数値として認識できる点数が１つでもセットされていた場合は登録する
					String kekkaTen = null;
					if(kekkaTenFlag){
						kekkaTen = new Integer(intKekkaTen).toString();
					}
					
					//更新データをセットする
					existInfo.setKekka1Abc(kekkaAbc);								//１次審査結果(ABC)
					existInfo.setKekka1Ten(kekkaTen);								//１次審査結果(点数)
					existInfo.setKekka1TenSorted(kekkaTenSorted);					//１次審査結果(点数順)				
					existInfo.setJokyoId(StatusCode.STATUS_1st_SHINSA_KANRYO);	//申請状況

					shinseiDataInfoDao.updateShinseiDataInfo(connection, existInfo, true);
				} catch (DataAccessException e) {
					throw new ApplicationException(
						"申請情報更新中にDBエラーが発生しました。",
						new ErrorInfo("errors.4002"),
						e);
				} catch (ApplicationException e) {
					throw new ApplicationException(
						"申請情報更新中にDBエラーが発生しました。",
						new ErrorInfo("errors.4002"),
						e);
				}
			}
			
			success = true;
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"審査結果データ更新中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"審査結果データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
	}
	
	
	
	/**
	 * 入力値チェック.<br/>
	 * 何もせず第二引数のinfoを返却.<br />
	 * @param userInfo UserInfo
	 * @param info ShinsaJokyoInfo
	 * @param mode String
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoSearchInfo)
	 */
	public ShinsaJokyoInfo validate(UserInfo userInfo, ShinsaJokyoInfo info, String mode)
		throws ApplicationException, ValidationException {
			return info;
	}
	
	
	

	/**
	 * 審査状況検索条件オブジェクトから検索条件を取得しSQLの問い合わせ部分を生成する。
	 * 生成した問い合わせ部分は第一引数の文字列の後ろに結合される。
	 * 第四引数でOREDER文を付加するか判断する。
	 * @param select    
	 * @param userInfo  
	 * @param searchInfo
	 * @param orderFlg	ture:ORDER文を付加　false:OREDER文省略
	 * @return
	 */
	protected static String getQueryString(String select, UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo, boolean orderFlg) {

		//-----検索条件オブジェクトの内容をSQLに結合していく-----
		StringBuffer query = new StringBuffer(select);
		
		//2005.11.08 原因不明のバグ(ORA-03113)のため審査員番号は最後に移動
//		if(!StringUtil.isBlank(searchInfo.getShinsainNo())) {					//審査員番号
//			query.append(" AND A.SHINSAIN_NO = '" + searchInfo.getShinsainNo() + "' ");
//		}

		//審査員氏名（漢字-姓）
		if(!StringUtil.isBlank(searchInfo.getNameKanjiSei())) {
			query.append(" AND A.SHINSAIN_NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		//審査員氏名（漢字-名）
		if(!StringUtil.isBlank(searchInfo.getNameKanjiMei())) {
			query.append(" AND A.SHINSAIN_NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		//審査員氏名（フリガナ-姓）
		if(!StringUtil.isBlank(searchInfo.getNameKanaSei())) {
			query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
		}
		//審査員氏名（フリガナ-名）
		if(!StringUtil.isBlank(searchInfo.getNameKanaMei())) {
			query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
		}
    	//審査員所属研究機関名	 2006/07/03 dyh 追加
        if(!StringUtil.isBlank(searchInfo.getShozokuName())) {
            query.append(" AND A.SHOZOKU_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");
        }
		//年度
		if(!StringUtil.isBlank(searchInfo.getNendo())) {
			query.append(" AND B.NENDO = " + EscapeUtil.toSqlString(searchInfo.getNendo()));
		}
		//回数
		if(!StringUtil.isBlank(searchInfo.getKaisu())) {
			query.append(" AND  B.KAISU = " + EscapeUtil.toSqlString(searchInfo.getKaisu()));
		}
		//事業名（事業コード）
		if(!searchInfo.getValues().isEmpty()) {
			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (")
				 .append(StringUtil.changeIterator2CSV(searchInfo.getValues().iterator(), false))
				 .append(")");
		}
		//審査員所属機関コード
		if(!StringUtil.isBlank(searchInfo.getShozokuCd())) {
			query.append(" AND B.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		//業務担当者の担当事業区分を取得
		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);
		if(!shinsaTaishoSet.isEmpty()) {
			query.append(" AND B.JIGYO_KUBUN IN (")
				 .append(StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(), false))
				 .append(")");
		}
		//系等の区分と略称の両方を検索する
		if(!StringUtil.isBlank(searchInfo.getKeiName())) {
			query.append(" AND (B.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
				+ "%' OR B.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
		}
		
		//利害関係入力完了状況 2007/5/9 追加　By xiang
		if(!StringUtil.isBlank(searchInfo.getRigaiJokyo())
				&& !"2".equals(searchInfo.getRigaiJokyo())) {
			query.append(" AND NVL(A.NYURYOKU_JOKYO,'0') = '" + EscapeUtil.toSqlString(searchInfo.getRigaiJokyo()) + "'");
		}	

		//審査状況
		if(!StringUtil.isBlank(searchInfo.getShinsaJokyo())
				&& !"2".equals(searchInfo.getShinsaJokyo())) {
			query.append(" AND A.SHINSA_JOKYO = '" + EscapeUtil.toSqlString(searchInfo.getShinsaJokyo()) + "'");
		}	
		
		//最終ログイン日を追加		2005/10/25追加 
		if (!StringUtil.isBlank(searchInfo.getLoginDate())) {
			if("1".equals(searchInfo.getLoginDate()) ){
				query.append(" AND D.LOGIN_DATE IS NOT NULL");
			}else{
				query.append(" AND D.LOGIN_DATE IS NULL ");
			}	 
		}

		//利害関係者を追加			2005/10/25追加
		if(!StringUtil.isBlank(searchInfo.getRigaiKankeisha())) {
			if("1".equals(searchInfo.getRigaiKankeisha()) ){
				query.append(" AND A.RIGAI = '" + EscapeUtil.toSqlString(searchInfo.getRigaiKankeisha()) + "'");
			}else{
				query.append(" AND ( A.RIGAI IS NULL OR A.RIGAI = '" + EscapeUtil.toSqlString(searchInfo.getRigaiKankeisha()) + "' ) ");
			}
		}
		//整理番号を追加	2005/11/2
		if(!StringUtil.isBlank(searchInfo.getSeiriNo())) {
			query.append(" AND B.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
		}
				  			
		//事業ID(再審査用)
		if(!StringUtil.isBlank(searchInfo.getJigyoId())) {
			query.append(" AND A.JIGYO_ID = '" + EscapeUtil.toSqlString(searchInfo.getJigyoId()) + "' ");
		}

		//事業区分(再審査用)
		if(!StringUtil.isBlank(searchInfo.getJigyoKubun())) {
			query.append(" AND A.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()));
		}

		//2005.11.08 原因不明のバグ(ORA-03113)のため審査員番号を最後に移動
		if(!StringUtil.isBlank(searchInfo.getShinsainNo())) {		//審査員番号
			query.append(" AND A.SHINSAIN_NO = '" + EscapeUtil.toSqlString(searchInfo.getShinsainNo()) + "' ");
		}

		if(orderFlg) {
			//ソート順（審査員番号、事業ID、申請番号の昇順）
			query.append(" ORDER BY A.SHINSAIN_NO, A.JIGYO_ID, B.UKETUKE_NO ");
		}

		return query.toString();
	}
	


//	/**
//	 * 審査件数情報一覧の表示する事業SQLの問い合わせ部分を生成する。
//	 * 生成した問い合わせ部分は第一引数の文字列の後ろに結合される。
//	 * @param select    
//	 * @param userInfo  
//	 * @param searchInfo
//	 * @return
//	 */
//	protected static String getHyojiQueryString(String select, UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo) {
//
//		//-----検索条件オブジェクトの内容をSQLに結合していく-----
//		StringBuffer query = new StringBuffer(select);
//
//		if(!StringUtil.isBlank(searchInfo.getNendo())) {							//年度
//			query.append(" AND B.NENDO = " + EscapeUtil.toSqlString(searchInfo.getNendo()));
//		}
//		if(!StringUtil.isBlank(searchInfo.getKaisu())) {							//回数
//			query.append(" AND  B.KAISU = " + EscapeUtil.toSqlString(searchInfo.getKaisu()));
//		}
//		if(!searchInfo.getValues().isEmpty()) {							//事業名（事業コード）
//			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (")
//				 .append(StringUtil.changeIterator2CSV(searchInfo.getValues().iterator(), false))
//				 .append(")");
//		}
//		//業務担当者の担当事業区分を取得
//		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);
//		if(!shinsaTaishoSet.isEmpty()) {
//			query.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(), false))
//				 .append(")");
//		}
//		//系等の区分と略称の両方を検索する
//		if(!StringUtil.isBlank(searchInfo.getKeiName())) {						//系等の区分
//			query.append(" AND (B.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
//				+ "%' OR B.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
//		}
//		if(!StringUtil.isBlank(searchInfo.getShinsaJokyo())
//				&& !searchInfo.getShinsaJokyo().equals("2")) {												//審査状況
//			query.append(" AND A.SHINSA_JOKYO = '" + EscapeUtil.toSqlString(searchInfo.getShinsaJokyo()) + "'");
//		}
//		if(!StringUtil.isBlank(searchInfo.getRigaiKankeisha())) {
//			if("1".equals(searchInfo.getRigaiKankeisha()) ){
//				query.append(" AND A.RIGAI = '" + EscapeUtil.toSqlString(searchInfo.getRigaiKankeisha()) + "'");
//			}else{
//				query.append(" AND ( A.RIGAI IS NULL OR A.RIGAI = '" + EscapeUtil.toSqlString(searchInfo.getRigaiKankeisha()) + "' ) ");
//			}
//		}
//		if(!StringUtil.isBlank(searchInfo.getSeiriNo())) {				//整理番号
//			query.append(" AND B.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//	  	
//		return query.toString();
//	}
	
	
	
}
