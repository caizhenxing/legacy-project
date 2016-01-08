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
import jp.go.jsps.kaken.model.IKanrenBunyaMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.sql.Connection;
import java.util.List;



/**
 * 関連分野情報管理を行うクラス.<br><br>
 * 
 * 概要：<br>
 * 申請データ管理テーブル：申請データの情報を管理
 */
public class KanrenBunyaMaintenance implements IKanrenBunyaMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	
	/** ログ. */
	protected static Log log = LogFactory.getLog(KanrenBunyaMaintenance.class);
	
		
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ.
	 */
	public KanrenBunyaMaintenance() {
		super();
	}
	
	//---------------------------------------------------------------------
	// implement IKanrenBunyaMaintenance
	//---------------------------------------------------------------------
	/** 
	 * 関連分野情報のページ情報を取得.<br><br>
	 * 
	 * 関連分野情報のページ情報を取得する。<br><br>
	 * 
	 * 第二引数searchInfoを検索条件に基づいて、申請データ管理テーブルを検索する。<br>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SYSTEM_NO,					-- システム受付番号
	 *     A.UKETUKE_NO,				-- 申請番号
	 *     A.JIGYO_ID,					-- 事業ID
	 *     A.NENDO,						-- 年度
	 *     A.KAISU,						-- 回数
	 *     A.JIGYO_NAME,				-- 事業名
	 *     A.SHINSEISHA_ID,				-- 申請者ID
	 *     A.NAME_KANJI_SEI,			-- 申請者氏名（漢字等-姓）
	 *     A.NAME_KANJI_MEI,			-- 申請者氏名（漢字等-名）
	 *     A.SHOZOKU_CD,				-- 所属機関コード
	 *     A.SHOZOKU_NAME_RYAKU,		-- 所属機関名（略称）
	 *     A.BUKYOKU_NAME_RYAKU,		-- 部局名（略称）
	 *     A.SHOKUSHU_NAME_RYAKU,		-- 職名（略称）
	 *     A.KADAI_NAME_KANJI,			-- 研究課題名(和文）
	 *     A.KEI_NAME_RYAKU,			-- 系統の区分（略称）
	 *     A.KANREN_SHIMEI1,		-- 関連分野の研究者-氏名1
	 *     A.KANREN_KIKAN1,			-- 関連分野の研究者-所属機関1
	 *     A.KANREN_BUKYOKU1,		-- 関連分野の研究者-所属部局1
	 *     A.KANREN_SHOKU1,			-- 関連分野の研究者-職名1
	 *     A.KANREN_SENMON1,		-- 関連分野の研究者-専門分野1
	 *     A.KANREN_TEL1,			-- 関連分野の研究者-勤務先電話番号1
	 *     A.KANREN_JITAKUTEL1,		-- 関連分野の研究者-自宅電話番号1
	 *     A.KANREN_MAIL1,			-- 関連分野の研究者-E-Mail1
	 *     A.KANREN_SHIMEI2,		-- 関連分野の研究者-氏名2
	 *     A.KANREN_KIKAN2,			-- 関連分野の研究者-所属機関2
	 *     A.KANREN_BUKYOKU2,		-- 関連分野の研究者-所属部局2
	 *     A.KANREN_SHOKU2,			-- 関連分野の研究者-職名2
	 *     A.KANREN_SENMON2,		-- 関連分野の研究者-専門分野2
	 *     A.KANREN_TEL2,			-- 関連分野の研究者-勤務先電話番号2
	 *     A.KANREN_JITAKUTEL2,		-- 関連分野の研究者-自宅電話番号2
	 *     A.KANREN_MAIL2,			-- 関連分野の研究者-E-Mail2
	 *     A.KANREN_SHIMEI3,		-- 関連分野の研究者-氏名3
	 *     A.KANREN_KIKAN3,			-- 関連分野の研究者-所属機関3
	 *     A.KANREN_BUKYOKU3,		-- 関連分野の研究者-所属部局3
	 *     A.KANREN_SHOKU3,			-- 関連分野の研究者-職名3
	 *     A.KANREN_SENMON3,		-- 関連分野の研究者-専門分野3
	 *     A.KANREN_TEL3,			-- 関連分野の研究者-勤務先電話番号3
	 *     A.KANREN_JITAKUTEL3,		-- 関連分野の研究者-自宅電話番号3
	 *     A.KANREN_MAIL3,			-- 関連分野の研究者-E-Mail3
	 *     A.JOKYO_ID,				-- 申請状況ID
	 *     A.SAISHINSEI_FLG			-- 再申請フラグ
	 * FROM
	 *     SHINSEIDATAKANRI A		-- 申請データ管理テーブル
	 * WHERE
	 *     A.DEL_FLG = 0			-- 削除フラグが[0]
	 * 
	 * 	--- 動的検索条件1 ---
	 * 
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">動的検索条件1</span></b><br/>
	 * searchInfoによって検索条件が動的に変化する。<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>システム番号</td><td>systemNo</td><td>AND JIGYO_ID = 'システム番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請番号</td><td>uketukeNo</td><td>AND A.UKETUKE_NO = '申請番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoId</td><td>AND A.JIGYO_ID = '事業ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業CD</td><td>jigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) = '事業CD'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当事業CD</td><td>tantoJigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) IN ('担当事業CD1', '担当事業CD2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業名</td><td>jigyoName</td><td>AND A.JIGYO_NAME = '事業名'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>年度</td><td>nend</td><td>AND A.NENDO = '年度'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>回数</td><td>kaisu</td><td>AND A.KAISU = '回数'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者ID</td><td>shinseishaId</td><td>AND A.SHINSEISHA_ID = '申請者ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（漢字：姓）</td><td>nameKanjiSei</td><td>AND A.NAME_KANJI_SEI like '%申請者名（漢字)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（漢字：名）</td><td>nameKanjiMei</td><td>AND A.NAME_KANJI_MEI like '%申請者名（漢字：名）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（カナ：姓）</td><td>nameKanaSei</td><td>AND A.NAME_KANA_SEI like '%申請者名（カナ：姓）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（カナ：名）</td><td>nameKanaMei</td><td>AND A.NAME_KANA_MEI like '%申請者名（カナ：名）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（ローマ字：姓）</td><td>nameRoSei</td><td>AND UPPER(A.NAME_RO_SEI) like '%申請者名（ローマ字：姓）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（ローマ字：名）</td><td>nameRoMei</td><td>AND UPPER(A.NAME_RO_MEI) like '%申請者名（ローマ字：名）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者研究者番号</td><td>kenkyuNo</td><td>AND A.KENKYU_NO = '申請者研究者番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>jigyoKubun</td><td>AND A.JIGYO_KUBUN IN ('事業区分1', '事業区分2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>shozokuCd</td><td>AND A.SHOZOKU_CD = '所属機関コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>系統の区分番号</td><td>keiNameNo</td><td>AND A.KEI_NAME_NO = '系統の区分番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>系統の区分</td><td>keiName</td><td>AND (A.KEI_NAME like '%系統の区分%' OR A.KEI_NAME_RYAKU like '%系統の区分%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>細目番号</td><td>bunkasaimokuCd</td><td>AND (A.BUNKASAIMOKU_CD = '細目番号' OR A.BUNKASAIMOKU_CD2 = '細目番号')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>推薦の観点番号</td><td>kantenNo</td><td>AND A.KANTEN_NO = '推薦の観点番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>推薦の観点</td><td>kanten</td><td>AND A.KANTEN = '推薦の観点'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>推薦の観点略称</td><td>kantenRyaku</td><td>AND A.KANTEN_RYAKU = '推薦の観点略称'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>関連分野の研究者氏名</td><td>kanrenShimei</td><td>AND (A.KANREN_SHIMEI1 like '%関連分野の研究者氏名1%' OR A.KANREN_SHIMEI2 like '%関連分野の研究者氏名%' OR A.KANREN_SHIMEI3 like '%関連分野の研究者氏名%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請状況</td><td>jokyoId</td><td>AND A.JOKYO_ID IN ('申請状況1', '申請状況2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>再申請フラグ</td><td>saishinseiFlg</td><td>AND A.SAISHINSEI_FLG IN ('再申請フラグ1', '再申請フラグ2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>2次審査結果</td><td>kekka2</td><td>AND A.KEKKA2 IN ('2次審査結果1', '2次審査結果2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>作成日（From）</td><td>sakuseiDateFrom</td><td>AND A.SAKUSEI_DATE >= TO_DATE('作成日（From）', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>作成日（To）</td><td>sakuseiDateTo</td><td>AND A.SAKUSEI_DATE <= TO_DATE('作成日（To）', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関承認日（From）</td><td>shoninDateFrom</td><td>AND A.SHONIN_DATE >= TO_DATE('所属機関承認日（From）', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関承認日（To）</td><td>shoninDateTo</td><td>AND A.SHONIN_DATE <= TO_DATE('所属機関承認日（To）', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>部局コード</td><td>bukyokuCd</td><td>AND A.BUKYOKU_CD = '部局コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>組み合わせステータス状況</td><td>query</td><td>AND '組み合わせステータス状況'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>delFlg</td><td>AND A.DEL_FLG IN ('削除フラグ1', '削除フラグ2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>整列キー</td><td>order</td><td>ORDER BY '整列キー1', '整列キー2',…</td></tr>
	 * </table><br><br>
	 * 
	 * 取得した値をPage型pageInfoに格納する。<br><br>
	 * 
	 * pageInfoを返却する。
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	検索条件(ShinseiSearchInfo)
	 * @see jp.go.jsps.kaken.model.IKanrenBunyaMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiSearchInfo)
	 */
	public Page search(UserInfo userInfo, ShinseiSearchInfo searchInfo)
		throws ApplicationException {
		
		//ShinseiDataInfoDaoをnewする
		ShinseiDataInfoDao shinseidatainfodao = new ShinseiDataInfoDao(userInfo);
		
		//Pageオブジェクトを作成する
		Page pageInfo = null;
		
		//DBコネクションを取得する
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			//該当レコードを全件取得する
			pageInfo = shinseidatainfodao.searchKanrenbunyaList(connection, searchInfo);
			
			//Pageオブジェクトをreturnする
			return pageInfo;
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"DBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} 
		finally {
			//DBコネクションを閉じる
			DatabaseUtil.closeConnection(connection);
		}
		
		
	}
	
	/**
	 * 関連分野情報CSV出力データ作成.<br><br>
	 * 
	 * 関連分野情報をCSV出力するために、検索条件に該当するレコードをListへ格納し、呼び出し元へ返却する。<br>
	 * その際、各レコード情報は列ごとにListへ格納されたうえで返却するListへ格納される。(Listによる2次元配列構造)<br>
	 * なお、返却するListの一つ目の要素は、ヘッダー情報を格納する。<br><br>
	 * 
	 * (1)関連分野情報を取得し、CSV形式のリストを作成する。<br>
	 * 以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.NENDO,						-- 年度
	 *     A.KAISU,						-- 回数
	 *     A.JIGYO_NAME,				-- 事業名
	 *     A.KEI_NAME,					-- 系統の区分
	 *     A.KADAI_NAME_KANJI,			-- 研究課題名(和文）
	 *     A.NAME_KANJI_SEI,			-- 申請者氏名（漢字等-姓）
	 *     A.NAME_KANJI_MEI,			-- 申請者氏名（漢字等-名）
	 *     A.SHOZOKU_CD,				-- 所属機関コード
	 *     A.SHOZOKU_NAME,				-- 所属機関名
	 *     A.BUKYOKU_NAME,				-- 部局名
	 *     A.SHOKUSHU_NAME_KANJI,		-- 職名
	 *     A.KANREN_SHIMEI1,		-- 関連分野の研究者-氏名1
	 *     A.KANREN_KIKAN1,			-- 関連分野の研究者-所属機関1
	 *     A.KANREN_BUKYOKU1,		-- 関連分野の研究者-所属部局1
	 *     A.KANREN_SHOKU1,			-- 関連分野の研究者-職名1
	 *     A.KANREN_SENMON1,		-- 関連分野の研究者-専門分野1
	 *     A.KANREN_TEL1,			-- 関連分野の研究者-勤務先電話番号1
	 *     A.KANREN_JITAKUTEL1,		-- 関連分野の研究者-自宅電話番号1
	 *     A.KANREN_MAIL1,			-- 関連分野の研究者-E-Mail1
	 *     A.KANREN_SHIMEI2,		-- 関連分野の研究者-氏名2
	 *     A.KANREN_KIKAN2,			-- 関連分野の研究者-所属機関2
	 *     A.KANREN_BUKYOKU2,		-- 関連分野の研究者-所属部局2
	 *     A.KANREN_SHOKU2,			-- 関連分野の研究者-職名2
	 *     A.KANREN_SENMON2,		-- 関連分野の研究者-専門分野2
	 *     A.KANREN_TEL2,			-- 関連分野の研究者-勤務先電話番号2
	 *     A.KANREN_JITAKUTEL2,		-- 関連分野の研究者-自宅電話番号2
	 *     A.KANREN_MAIL2,			-- 関連分野の研究者-E-Mail2
	 *     A.KANREN_SHIMEI3,		-- 関連分野の研究者-氏名3
	 *     A.KANREN_KIKAN3,			-- 関連分野の研究者-所属機関3
	 *     A.KANREN_BUKYOKU3,		-- 関連分野の研究者-所属部局3
	 *     A.KANREN_SHOKU3,			-- 関連分野の研究者-職名3
	 *     A.KANREN_SENMON3,		-- 関連分野の研究者-専門分野3
	 *     A.KANREN_TEL3,			-- 関連分野の研究者-勤務先電話番号3
	 *     A.KANREN_JITAKUTEL3,		-- 関連分野の研究者-自宅電話番号3
	 *     A.KANREN_MAIL3"			-- 関連分野の研究者-E-Mail3
	 * FROM
	 *     SHINSEIDATAKANRI A		-- 申請データ管理テーブル
	 * WHERE
	 *     A.DEL_FLG = 0				-- 削除フラグが[0]
	 * 
	 * 	--- 動的検索条件1 ---
	 * 
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">動的検索条件1</span></b><br>
	 * searchInfoによって検索条件が動的に変化する。<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>システム番号</td><td>systemNo</td><td>AND JIGYO_ID = 'システム番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請番号</td><td>uketukeNo</td><td>AND A.UKETUKE_NO = '申請番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoId</td><td>AND A.JIGYO_ID = '事業ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業CD</td><td>jigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) = '事業CD'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当事業CD</td><td>tantoJigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) IN ('担当事業CD1', '担当事業CD2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業名</td><td>jigyoName</td><td>AND A.JIGYO_NAME = '事業名'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>年度</td><td>nend</td><td>AND A.NENDO = '年度'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>回数</td><td>kaisu</td><td>AND A.KAISU = '回数'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者ID</td><td>shinseishaId</td><td>AND A.SHINSEISHA_ID = '申請者ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（漢字：姓）</td><td>nameKanjiSei</td><td>AND A.NAME_KANJI_SEI like '%申請者名（漢字)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（漢字：名）</td><td>nameKanjiMei</td><td>AND A.NAME_KANJI_MEI like '%申請者名（漢字：名）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（カナ：姓）</td><td>nameKanaSei</td><td>AND A.NAME_KANA_SEI like '%申請者名（カナ：姓）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（カナ：名）</td><td>nameKanaMei</td><td>AND A.NAME_KANA_MEI like '%申請者名（カナ：名）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（ローマ字：姓）</td><td>nameRoSei</td><td>AND UPPER(A.NAME_RO_SEI) like '%申請者名（ローマ字：姓）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者名（ローマ字：名）</td><td>nameRoMei</td><td>AND UPPER(A.NAME_RO_MEI) like '%申請者名（ローマ字：名）%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者研究者番号</td><td>kenkyuNo</td><td>AND A.KENKYU_NO = '申請者研究者番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>事業区分</td><td>jigyoKubun</td><td>AND A.JIGYO_KUBUN IN ('事業区分1', '事業区分2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>shozokuCd</td><td>AND A.SHOZOKU_CD = '所属機関コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>系統の区分番号</td><td>keiNameNo</td><td>AND A.KEI_NAME_NO = '系統の区分番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>系統の区分</td><td>keiName</td><td>AND (A.KEI_NAME like '%系統の区分%' OR A.KEI_NAME_RYAKU like '%系統の区分%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>細目番号</td><td>bunkasaimokuCd</td><td>AND (A.BUNKASAIMOKU_CD = '細目番号' OR A.BUNKASAIMOKU_CD2 = '細目番号')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>推薦の観点番号</td><td>kantenNo</td><td>AND A.KANTEN_NO = '推薦の観点番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>推薦の観点</td><td>kanten</td><td>AND A.KANTEN = '推薦の観点'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>推薦の観点略称</td><td>kantenRyaku</td><td>AND A.KANTEN_RYAKU = '推薦の観点略称'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>関連分野の研究者氏名</td><td>kanrenShimei</td><td>AND (A.KANREN_SHIMEI1 like '%関連分野の研究者氏名1%' OR A.KANREN_SHIMEI2 like '%関連分野の研究者氏名%' OR A.KANREN_SHIMEI3 like '%関連分野の研究者氏名%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請状況</td><td>jokyoId</td><td>AND A.JOKYO_ID IN ('申請状況1', '申請状況2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>再申請フラグ</td><td>saishinseiFlg</td><td>AND A.SAISHINSEI_FLG IN ('再申請フラグ1', '再申請フラグ2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>2次審査結果</td><td>kekka2</td><td>AND A.KEKKA2 IN ('2次審査結果1', '2次審査結果2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>作成日（From）</td><td>sakuseiDateFrom</td><td>AND A.SAKUSEI_DATE >= TO_DATE('作成日（From）', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>作成日（To）</td><td>sakuseiDateTo</td><td>AND A.SAKUSEI_DATE <= TO_DATE('作成日（To）', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関承認日（From）</td><td>shoninDateFrom</td><td>AND A.SHONIN_DATE >= TO_DATE('所属機関承認日（From）', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関承認日（To）</td><td>shoninDateTo</td><td>AND A.SHONIN_DATE <= TO_DATE('所属機関承認日（To）', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>部局コード</td><td>bukyokuCd</td><td>AND A.BUKYOKU_CD = '部局コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>組み合わせステータス状況</td><td>query</td><td>AND '組み合わせステータス状況'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>delFlg</td><td>AND A.DEL_FLG IN ('削除フラグ1', '削除フラグ2', …)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>整列キー</td><td>order</td><td>ORDER BY '整列キー1', '整列キー2',…</td></tr>
	 * </table><br><br>
	 * 
	 * (2)カラム名を挿入<br>
	 * カラム名リストを生成し、(1)で取得したリストの最初の要素に挿入する。<br>
	 * 指定文字列はSQLの識別子長を超えてしまう可能性があるため別にセットする。<br><br>
	 * カラム名リストは以下の通り。<br>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * 年度
	 * 回数
	 * 事業名
	 * 申請番号
	 * 系統の区分
	 * 研究課題名(和文）
	 * 申請者氏名（漢字等-姓）
	 * 申請者氏名（漢字等-名）
	 * 所属機関コード
	 * 所属機関名
	 * 部局名
	 * 職名（和文）
	 * 関連分野の研究者-氏名1～3
	 * 関連分野の研究者-所属機関1～3
	 * 関連分野の研究者-所属部局1～3
	 * 関連分野の研究者-職名1～3
	 * 関連分野の研究者-専門分野1～3
	 * 関連分野の研究者-勤務先電話番号1～3
	 * 関連分野の研究者-自宅電話番号1～3
	 * 関連分野の研究者-Email1～3
	 * </pre>
     * </td></tr>
	 * </table><br><br>
	 * 
	 * (3)リストを返却する。<br>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	検索条件(ShinseiSearchInfo)
	 * @return 関連分野情報リスト(CSV)
	 * @see jp.go.jsps.kaken.model.IKanrenBunyaMaintenance#searchCsvData(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiSearchInfo)
	 */
	public List kanrenSearchCsvData(UserInfo userInfo,ShinseiSearchInfo searchInfo )
		throws ApplicationException
	{
		//ShinseiDataInfoDaoをnewする
		ShinseiDataInfoDao shinseidatainfodao = new ShinseiDataInfoDao(userInfo);
		
		//DBコネクションを取得する
		Connection connection = null;
		
		//Listオブジェクトを作成する
		List result = null;
		
		try {
			
				connection = DatabaseUtil.getConnection();
				//Listオブジェクトを取得する
				result = shinseidatainfodao.searchKanrenbunyaListCSV(connection, searchInfo);
				//Listオブジェクトをreturnする
				return result;
					
		}catch(DataAccessException e){
			throw new ApplicationException(
			"DBエラーが発生しました。",
			new ErrorInfo("errors.4004"),
			e);
			
		}
		finally{
				//DBコネクションを閉じる
				DatabaseUtil.closeConnection(connection);
		}
		
	}
		

}


