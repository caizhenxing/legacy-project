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

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.IShinsainWarifuri;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.*;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinsainPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.WarifuriInfo;
import jp.go.jsps.kaken.model.vo.WarifuriPk;
import jp.go.jsps.kaken.model.vo.WarifuriSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.CsvUtil;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 審査員割り振りクラス.<br />
 * <br />
 * <b>概要</b><br />
 * 審査員割り振り情報は審査結果テーブル（SHINSAKEKKA）にて管理する。<br />
 * 審査結果テーブルのレコードは申請とともに登録される。（必要なレコードは申請の種類によって決まっている。）<br />
 * 割り振り情報の登録は、作成済みのレコードに対して更新を行うことにより行う。<br />
 * 審査員を更新する場合は、審査員情報情報のみ更新を行う。その際、同じ審査員が一つの申請に対して複数審査することは出来ない。<br />
 * 審査員割り振り情報を削除する場合は、レコードを初期状態に戻す。
 * 
 */
public class ShinsainWarifuri implements IShinsainWarifuri {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(ShinsainWarifuri.class);

	/** 審査依頼通知書ファイル格納フォルダ */
	private static String IRAI_WORK_FOLDER = ApplicationSettings.getString(ISettingKeys.IRAI_WORK_FOLDER);		

	/** 審査依頼書Wordファイル格納フォルダ */
	private static String IRAI_FORMAT_PATH = ApplicationSettings.getString(ISettingKeys.IRAI_FORMAT_PATH);		

	/** 審査依頼書Wordファイル名 */
	private static String IRAI_FORMAT_FILE_NAME = ApplicationSettings.getString(ISettingKeys.IRAI_FORMAT_FILE_NAME);		

	/**
	 * CSVファイル名の接頭辞。
	 */
	public static final String CSV_FILENAME = "SHINSAIRAI";

	/** 申請書削除フラグ（削除済み） */
	private static final String FLAG_APPLICATION_DELETE     = IShinseiMaintenance.FLAG_APPLICATION_DELETE;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public ShinsainWarifuri() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IShinsainWarifuri
	//---------------------------------------------------------------------

	/** 
	 * 割り振り結果情報を新規作成する.<br/><br/> 
	 * 第二引数をそのまま返却する。
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinseishaInfo)
	 */
	public WarifuriInfo insert(UserInfo userInfo, WarifuriInfo addInfo)
		throws ApplicationException {
			return addInfo;
	}

	/** 
	 * 割り振り結果情報を更新する.<br/><br/>
	 * <b>1.更新チェック</b><br/>
	 *	第二引数のwarifuriPkのOldShinsainNoの値と、ShinsainNoの値を比較し、同じであった場合は更新されていないので、
	 *	何も処理をせずに終了する。<br/>
	 * <br/>
	 * <b>2.審査員情報取得</b><br/>
	 *	以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 *	レコードが存在しない場合は例外をthrowする。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		SI.SHINSAIN_ID SHINSAIN_ID
	 *		, MS.SHINSAIN_NO SHINSAIN_NO
	 *		, MS.JIGYO_KUBUN JIGYO_KUBUN
	 *		, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 *		, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 *		, MS.NAME_KANA_SEI NAME_KANA_SEI
	 *		, MS.NAME_KANA_MEI NAME_KANA_MEI
	 *		, MS.SHOZOKU_CD SHOZOKU_CD
	 *		, MS.SHOZOKU_NAME SHOZOKU_NAME
	 *		, MS.BUKYOKU_NAME BUKYOKU_NAME
	 *		, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 *		, MS.SOFU_ZIP SOFU_ZIP
	 *		, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 *		, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 *		, MS.SHOZOKU_TEL SHOZOKU_TEL
	 *		, MS.SHOZOKU_FAX SHOZOKU_FAX
	 *		, MS.URL URL
	 *		, MS.SENMON SENMON
	 *		, MS.KOSHIN_DATE KOSHIN_DATE
	 *		, MS.BIKO BIKO
	 *		, SI.PASSWORD PASSWORD
	 *		, SI.YUKO_DATE YUKO_DATE
	 *		, SI.DEL_FLG DEL_FLG
	 *	FROM 
	 *		MASTER_SHINSAIN MS
	 *		, SHINSAININFO SI
	 *	WHERE
	 *		SI.DEL_FLG = 0
	 *		AND MS.SHINSAIN_NO = ?
	 *		AND MS.JIGYO_KUBUN = ?
	 *		AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 *		AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1) </pre>
	 *	</td></tr>
	 *	</table><br>
	 *
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数warifuriPkの変数（ShinsainNo）を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数warifuriPkの変数（JigyoKubun）を使用する。</td></tr>
	 *	</table><br/>
	 * <b>3.重複チェック</b><br/>
	 *	同じ申請に同じ審査員が割り振られていないかどうかをチェックために、以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 *	レコードが存在する場合は例外をthrowする。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		 A.SYSTEM_NO		--  システム受付番号
	 *		,A.UKETUKE_NO		--  申請番号
	 *		,A.SHINSAIN_NO		--  審査員番号
	 *		,A.JIGYO_KUBUN		--  事業区分
	 *		,A.SEQ_NO			--  シーケンス番号
	 *		,A.SHINSA_KUBUN		--  審査区分
	 *		,A.SHINSAIN_NAME_KANJI_SEI	--  審査員名（漢字－姓）
	 *		,A.SHINSAIN_NAME_KANJI_MEI	--  審査員名（漢字－名）
	 *		,A.NAME_KANA_SEI		--  審査員名（フリガナ－姓）
	 *		,A.NAME_KANA_MEI		--  審査員名（フリガナ－名）
	 *		,A.SHOZOKU_NAME		--  審査員所属機関名
	 *		,A.BUKYOKU_NAME		--  審査員部局名
	 *		,A.SHOKUSHU_NAME		--  審査員職名
	 *		,A.JIGYO_ID		--  事業ID
	 *		,A.JIGYO_NAME		--  事業名
	 *		,A.BUNKASAIMOKU_CD		--  細目番号
	 *		,A.EDA_NO			--  枝番
	 *		,A.CHECKDIGIT		--  チェックデジット
	 *		,A.KEKKA_ABC		--  総合評価（ABC）
	 *		,A.KEKKA_TEN		--  総合評価（点数）
	 *		,A.COMMENT1		--  コメント1
	 *		,A.COMMENT2		--  コメント2
	 *		,A.COMMENT3		--  コメント3
	 *		,A.COMMENT4		--  コメント4
	 *		,A.COMMENT5		--  コメント5
	 *		,A.COMMENT6		--  コメント6
	 *		,A.KENKYUNAIYO		--  研究内容
	 *		,A.KENKYUKEIKAKU		--  研究計画
	 *		,A.TEKISETSU_KAIGAI		--  適切性-海外
	 *		,A.TEKISETSU_KENKYU1		--  適切性-研究（1）
	 *		,A.TEKISETSU		--  適切性
	 *		,A.DATO			--  妥当性
	 *		,A.SHINSEISHA		--  研究代表者
	 *		,A.KENKYUBUNTANSHA		--  研究分担者
	 *		,A.HITOGENOMU		--  ヒトゲノム
	 *		,A.TOKUTEI			--  特定胚
	 *		,A.HITOES			--  ヒトES細胞
	 *		,A.KUMIKAE			--  遺伝子組換え実験
	 *		,A.CHIRYO			--  遺伝子治療臨床研究
	 *		,A.EKIGAKU			--  疫学研究
	 *		,A.COMMENTS		--  コメント
	 *		,A.TENPU_PATH		--  添付ファイル格納パス
	 *		,DECODE (
	 *			NVL(A.TENPU_PATH,'null') ,'null','FALSE','TRUE'
	 *		) TENPU_FLG		--  添付ファイル格納フラグ
	 *					--   添付ファイル格納パスがNULL:FALSE
	 *					--   NULL以外:TRUE
	 *		,A.SHINSA_JOKYO		--  審査状況
	 *		,A.BIKO			--  備考
	 *	FROM
	 *		SHINSAKEKKA A
	 *	WHERE
	 *		SYSTEM_NO = 'システム受付番号'
	 *		AND SHINSAIN_NO = '審査員番号'
	 *		AND JIGYO_KUBUN = '事業区分'
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">※システム受付番号、審査員番号、事業区分は第二引数のwarifuriPkより取得する。</div><br />
	 *
	 * <b>4.審査結果取得＋排他制御＋レコード削除</b><br/>
	 *	修正前の審査結果情報を取得しつつ排他ロックするため、以下のSQLを実行する。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		 A.SYSTEM_NO		--  システム受付番号
	 *		,A.UKETUKE_NO		--  申請番号
	 *		,A.SHINSAIN_NO		--  審査員番号
	 *		,A.JIGYO_KUBUN		--  事業区分
	 *		,A.SEQ_NO			--  シーケンス番号
	 *		,A.SHINSA_KUBUN		--  審査区分
	 *		,A.SHINSAIN_NAME_KANJI_SEI	--  審査員名（漢字－姓）
	 *		,A.SHINSAIN_NAME_KANJI_MEI	--  審査員名（漢字－名）
	 *		,A.NAME_KANA_SEI		--  審査員名（フリガナ－姓）
	 *		,A.NAME_KANA_MEI		--  審査員名（フリガナ－名）
	 *		,A.SHOZOKU_NAME		--  審査員所属機関名
	 *		,A.BUKYOKU_NAME		--  審査員部局名
	 *		,A.SHOKUSHU_NAME		--  審査員職名
	 *		,A.JIGYO_ID		--  事業ID
	 *		,A.JIGYO_NAME		--  事業名
	 *		,A.BUNKASAIMOKU_CD		--  細目番号
	 *		,A.EDA_NO			--  枝番
	 *		,A.CHECKDIGIT		--  チェックデジット
	 *		,A.KEKKA_ABC		--  総合評価（ABC）
	 *		,A.KEKKA_TEN		--  総合評価（点数）
	 *		,A.COMMENT1		--  コメント1
	 *		,A.COMMENT2		--  コメント2
	 *		,A.COMMENT3		--  コメント3
	 *		,A.COMMENT4		--  コメント4
	 *		,A.COMMENT5		--  コメント5
	 *		,A.COMMENT6		--  コメント6
	 *		,A.KENKYUNAIYO		--  研究内容
	 *		,A.KENKYUKEIKAKU		--  研究計画
	 *		,A.TEKISETSU_KAIGAI		--  適切性-海外
	 *		,A.TEKISETSU_KENKYU1		--  適切性-研究（1）
	 *		,A.TEKISETSU		--  適切性
	 *		,A.DATO			--  妥当性
	 *		,A.SHINSEISHA		--  研究代表者
	 *		,A.KENKYUBUNTANSHA		--  研究分担者
	 *		,A.HITOGENOMU		--  ヒトゲノム
	 *		,A.TOKUTEI			--  特定胚
	 *		,A.HITOES			--  ヒトES細胞
	 *		,A.KUMIKAE			--  遺伝子組換え実験
	 *		,A.CHIRYO			--  遺伝子治療臨床研究
	 *		,A.EKIGAKU			--  疫学研究
	 *		,A.COMMENTS		--  コメント
	 *		,A.TENPU_PATH		--  添付ファイル格納パス
	 *		,DECODE (
	 *			NVL(A.TENPU_PATH,'null') ,'null','FALSE','TRUE'
	 *	 	) TENPU_FLG		--  添付ファイル格納フラグ
	 *					--   添付ファイル格納パスがNULL:FALSE
	 *					--   NULLじゃないTRUE
	 *		,A.SHINSA_JOKYO		--  審査状況
	 *		,A.BIKO			--  備考
	 *	FROM
	 *		SHINSAKEKKA A
	 *	WHERE
	 *		SYSTEM_NO = 'システム受付番号'
	 *		AND SHINSAIN_NO = '審査員番号'
	 *		AND JIGYO_KUBUN = '事業区分'
	 *	FOR UPDATE
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">※システム受付番号、審査員番号、事業区分は第二引数のwarifuriPkより取得する。ただし、審査員番号は以前の番号（OldShinsainNo）を利用する。</div><br />
	 *	
	 *	続いて以下のSQLでレコードの削除を行う。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		SHINSAKEKKA
	 *	WHERE
	 *		SYSTEM_NO = 'システム受付番号'
	 *		AND SHINSAIN_NO = '審査員番号'
	 *		AND JIGYO_KUBUN = '事業区分'
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">※システム受付番号、審査員番号、事業区分は第二引数のwarifuriPkより取得する。ただし、審査員番号は以前の番号（OldShinsainNo）を利用する。</div><br />
	 *
	 * <b>5.レコード登録</b><br/>
	 *	以下のSQLを実行する。（バインド変数はSQLの下の表を参照）
	 *	ほとんどの情報は4で取得した情報をそのまま使用するが、審査員番号は新しい番号で上書きする。
	 *	また、審査員名等は2で取得した審査員マスタの情報で上書きする。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO SHINSAKEKKA(
	 *		SYSTEM_NO			--  システム受付番号
	 *		,UKETUKE_NO		--  申請番号
	 *		,SHINSAIN_NO		--  審査員番号
	 *		,JIGYO_KUBUN		--  事業区分
	 *		,SEQ_NO			--  シーケンス番号
	 *		,SHINSA_KUBUN		--  審査区分
	 *		,SHINSAIN_NAME_KANJI_SEI	--  審査員名（漢字－姓）
	 *		,SHINSAIN_NAME_KANJI_MEI	--  審査員名（漢字－名）
	 *		,NAME_KANA_SEI		--  審査員名（フリガナ－姓）
	 *		,NAME_KANA_MEI		--  審査員名（フリガナ－名）
	 *		,SHOZOKU_NAME		--  審査員所属機関名
	 *		,BUKYOKU_NAME		--  審査員部局名
	 *		,SHOKUSHU_NAME		--  審査員職名
	 *		,JIGYO_ID			--  事業ID
	 *		,JIGYO_NAME		--  事業名
	 *		,BUNKASAIMOKU_CD		--  分科細目コード
	 *		,EDA_NO			--  枝番
	 *		,CHECKDIGIT		--  チェックデジット
	 *		,KEKKA_ABC			--  総合評価（ABC）
	 *		,KEKKA_TEN			--  総合評価（点数）
	 *		,COMMENT1			--  コメント1
	 *		,COMMENT2			--  コメント2
	 *		,COMMENT3			--  コメント3
	 *		,COMMENT4			--  コメント4
	 *		,COMMENT5			--  コメント5
	 *		,COMMENT6			--  コメント6
	 *		,KENKYUNAIYO		--  研究内容
	 *		,KENKYUKEIKAKU		--  研究計画
	 *		,TEKISETSU_KAIGAI		--  適切性-海外
	 *		,TEKISETSU_KENKYU1		--  適切性-研究（1）
	 *		,TEKISETSU			--  適切性
	 *		,DATO			--  妥当性
	 *		,SHINSEISHA		--  研究代表者
	 *		,KENKYUBUNTANSHA		--  研究分担者
	 *		,HITOGENOMU		--  ヒトゲノム
	 *		,TOKUTEI			--  特定胚
	 *		,HITOES			--  ヒトES細胞
	 *		,KUMIKAE			--  遺伝子組換え実験
	 *		,CHIRYO			--  遺伝子治療臨床研究
	 *		,EKIGAKU			--  疫学研究
	 *		,COMMENTS			--  コメント
	 *		,TENPU_PATH		--  添付ファイル格納パス
	 *		,SHINSA_JOKYO		--  審査状況
	 *		,BIKO			--  備考
	 *	) VALUES (
	 *		?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,
	 *		?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?
	 *	)
	 *	</td></tr>
	 *	</table><br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>4で取得したシステム受付番号を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請番号</td><td>4で取得した申請番号を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td style="color:882200">第二引数（warifuriPk）の審査員番号を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>4で取得した事業区分を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>シーケンス番号</td><td>4で取得したシーケンス番号を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査区分</td><td>4で取得した審査区分を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（漢字－姓）</td><td style="color:008855">2で取得した審査員名（漢字－姓）を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（漢字－名）</td><td style="color:008855">2で取得した審査員名（漢字－名）を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－姓）</td><td style="color:008855">2で取得した審査員名（フリガナ－姓）を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－名）</td><td style="color:008855">2で取得した審査員名（フリガナ－名）を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員所属機関名</td><td style="color:008855">2で取得した審査員所属機関名を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員部局名</td><td style="color:008855">2で取得した審査員部局名を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員職名</td><td style="color:008855">2で取得した審査員職名を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>4で取得した事業IDを使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業名</td><td>4で取得した事業名を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>分科細目コード</td><td>4で取得した分科細目コードを使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>枝番</td><td>4で取得した枝番を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>チェックデジット</td><td>4で取得したチェックデジットを使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>総合評価（ABC）</td><td>4で取得した総合評価（ABC）を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>総合評価（点数）</td><td>4で取得した総合評価（点数）を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント1</td><td>4で取得したコメント1を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント2</td><td>4で取得したコメント2を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント3</td><td>4で取得したコメント3を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント4</td><td>4で取得したコメント4を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント5</td><td>4で取得したコメント5を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント6</td><td>4で取得したコメント6を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究内容</td><td>4で取得した研究内容を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究計画</td><td>4で取得した研究計画を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>適切性-海外</td><td>4で取得した適切性-海外を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>適切性-研究（1）</td><td>4で取得した適切性-研究（1）を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>適切性</td><td>4で取得した適切性を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>妥当性</td><td>4で取得した妥当性を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究代表者</td><td>4で取得した研究代表者を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究分担者</td><td>4で取得した研究分担者を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ヒトゲノム</td><td>4で取得したヒトゲノムを使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>特定胚</td><td>4で取得した特定胚を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ヒトES細胞</td><td>4で取得したヒトES細胞を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>遺伝子組換え実験</td><td>4で取得した遺伝子組換え実験を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>遺伝子治療臨床研究</td><td>4で取得した遺伝子治療臨床研究を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>疫学研究</td><td>4で取得した疫学研究を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント</td><td>4で取得したコメントを使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>添付ファイル格納パス</td><td>4で取得した添付ファイル格納パスを使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査状況</td><td>4で取得した審査状況を使用する。</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>備考</td><td>4で取得した備考を使用する。</td></tr>
	 *	</table><br/>
	 *
	 * 
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.WarifuriPk)
	 */
	public void update(UserInfo userInfo, WarifuriPk warifuriPk)
		throws ApplicationException {


		//---------------------------------------
		//修正されているかどうかをチェック
		//---------------------------------------
		//審査員番号（修正前）と審査員番号（修正後）が一致している場合
		if(warifuriPk.getOldShinsainNo().equals(warifuriPk.getShinsainNo())){
			//処理終了
			return;
		}

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			ShinsaKekkaInfoDao shinsaKekkaDao = new ShinsaKekkaInfoDao(userInfo);	
			//---------------------------------------
			//審査員番号チェック
			//---------------------------------------
			//審査員マスタに該当するデータが存在しているどうかをチェック
			ShinsainInfo shinsainInfo = null;
			try {
				ShinsainPk shinsainPk = new ShinsainPk();
				shinsainPk.setShinsainNo(warifuriPk.getShinsainNo());		//審査員番号
				shinsainPk.setJigyoKubun(warifuriPk.getJigyoKubun());		//事業区分		
				ShinsainInfoDao shinsainDao = new ShinsainInfoDao(userInfo);
				shinsainInfo = shinsainDao.selectShinsainInfo(connection, shinsainPk);
			} catch (NoDataFoundException e) {
				throw e;
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"審査員マスタ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}
			
//審査員番号チェックで、審査結果テーブルの事業区分を検索条件としているため、改めて検査する必要なし			
//			//---------------------------------------
//			//担当事業チェック
//			//---------------------------------------
//			//審査員情報の事業区分と審査結果情報の事業区分が一致しているかどうかをチェック
//			if(warifuriPk.getJigyoKubun() != null){
//				//一致してなかったら
//				if(!warifuriPk.getJigyoKubun().equals(shinsainInfo.getJigyoKubun())){
//					//例外発生
//					throw new ApplicationException(
//						"この申請データは担当事業ではありません。" 
//						+ "申請データ「システム番号'" + warifuriPk.getSystemNo() + "',事業区分'" + warifuriPk.getJigyoKubun() + "'」,"
//						+ "審査員「審査員番号'" + shinsainInfo.getShinsainNo() + "',事業区分'" + shinsainInfo.getJigyoKubun() + "'",
//						new ErrorInfo("errors.4010")); 
//				}
//			}
			

			//---------------------------------------
			//重複チェック用の審査結果キー
			//審査員番号に審査員番号（修正後）をセット
			//---------------------------------------
			ShinsaKekkaPk selectPk = new ShinsaKekkaPk();
			selectPk.setSystemNo(warifuriPk.getSystemNo());		//システム番号
			selectPk.setShinsainNo(warifuriPk.getShinsainNo());	//審査員番号（修正後）をセット
			selectPk.setJigyoKubun(warifuriPk.getJigyoKubun());	//事業区分			

			//---------------------------------------
			//重複チェック
			//---------------------------------------
			//同じ申請に同じ審査員が割り振られていないかどうかをチェック
			//修正後の審査結果情報を取得する
			try {
				shinsaKekkaDao.selectShinsaKekkaInfo(connection, selectPk);
				//NG
				throw new ApplicationException(
								"すでに同一の審査員が登録されています。審査員番号'" + selectPk.getShinsainNo() +"'," 
								+ "事業区分'" + selectPk.getJigyoKubun() + "'" , 
								new ErrorInfo("errors.4007", new String[]{"審査員"})
								);
			} catch (NoDataFoundException e) {
				//OK
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"審査結果データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}
					
			//---------------------------------------
			//更新用の審査結果キー
			//審査員番号に審査員番号（修正前）をセット
			//---------------------------------------
			ShinsaKekkaPk updatePk = new ShinsaKekkaPk();
			updatePk.setSystemNo(warifuriPk.getSystemNo());			//システム番号
			updatePk.setShinsainNo(warifuriPk.getOldShinsainNo());	//審査員番号（修正前）をセット
			updatePk.setJigyoKubun(warifuriPk.getJigyoKubun());		//事業区分		
			

			//---------------------------------------
			//排他制御のため既存データを取得する
			//---------------------------------------
			ShinsaKekkaInfo shinsaKekkaInfo = null;
			try {
				shinsaKekkaInfo = shinsaKekkaDao.selectShinsaKekkaInfoForLock(connection, updatePk);
			} catch (NoDataFoundException e) {
				throw e;
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"審査結果データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}

			//---------------------------------------
			//審査結果テーブルの削除
			//---------------------------------------
			//修正前の審査結果情報を削除する
			try {
				//DB更新
				shinsaKekkaDao.deleteShinsaKekkaInfo(connection, updatePk);
			} catch (NoDataFoundException e) {
				throw e;
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"審査結果データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}			
			
//2005/11/04 追加 start
			//---------------------------------------
			//代理フラグのチェック
			//---------------------------------------
			String dairiFlg = shinsaKekkaInfo.getDairi();
			//修正前の審査員番号がダミー審査員の場合、代理フラグを立てる（基盤のみ）
//2006/05/31 追加ここから            
//			if(warifuriPk.getOldShinsainNo().startsWith("@") && warifuriPk.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)
            if(warifuriPk.getOldShinsainNo().startsWith("@") && ( warifuriPk.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN) 
                    || warifuriPk.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART) 
                    || warifuriPk.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI))

//苗　追加ここまで                    
            ){
				dairiFlg = "1";
			}
//2005/11/04 追加 end
//2005/11/08 追加 start
			//---------------------------------------
			//ID登録日付の作成
			//---------------------------------------
			Date koshin_date = new Date();
//2005/11/08 追加 end

			//審査員情報をセット
			shinsaKekkaInfo.setShinsainNo(warifuriPk.getShinsainNo());					//審査員番号（修正後）
			shinsaKekkaInfo.setShinsainNameKanjiSei(shinsainInfo.getNameKanjiSei());	//審査員名（漢字-姓）
			shinsaKekkaInfo.setShinsainNameKanjiMei(shinsainInfo.getNameKanjiMei());	//審査員名（漢字-名）
			shinsaKekkaInfo.setNameKanaSei(shinsainInfo.getNameKanaSei());				//審査員名（フリガナ-姓）
			shinsaKekkaInfo.setNameKanaMei(shinsainInfo.getNameKanaMei());				//審査員名（フリガナ-名）
			shinsaKekkaInfo.setShozokuName(shinsainInfo.getShozokuName());				//審査員所属機関名
			shinsaKekkaInfo.setBukyokuName(shinsainInfo.getBukyokuName());				//審査員部局名
			shinsaKekkaInfo.setShokushuName(shinsainInfo.getShokushuName());			//審査員職名
			shinsaKekkaInfo.setDairi(dairiFlg);											//代理フラグ	//2005/11/04 追加
			shinsaKekkaInfo.setKoshinDate(koshin_date);									//割り振り更新日 //2005/11/08 追加
			

			try {
				//DB更新
				shinsaKekkaDao.insertShinsaKekkaInfo(connection, shinsaKekkaInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"審査結果データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			}

			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

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
	 * 割り振り結果情報取得.<br/>
	 * <br />
	 * <b>1.割振り情報検索</b><br/>
	 *	同クラスのsearchメソッドを使用して割り振り結果情報を検索する。
	 *	検索する際、システム番号、審査員番号および事業区分は第二引数のpkInfoより取得する。<br />
	 *	<b>現状では検索結果がない場合はNullPointerが発生すると思われる → <font color="red">要修正</font></b>
	 * <br />
	 * <b>2.割り振り結果情報取得</b><br/>
	 *	1で取得した割り振り結果情報の1件目のレコードをWarifuriInfoにセットする。
	 *	WarifuriInfoにセットする情報は以下の通り。<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>変数名（日本語）</td><td>変数名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>Nendo</td><td>年度</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>Kaisu</td><td>回数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>JigyoName</td><td>事業名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SystemNo</td><td>システム番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>UketukeNo</td><td>申請番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KeiNameRyaku</td><td>系等の区分（略称）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KadaiNameKanji</td><td>研究課題名（和文）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NameKanjiSei</td><td>申請者名（漢字等-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NameKanjiMei</td><td>申請者名（漢字等-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShozokuNameRyaku</td><td>所属機関名（略称）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BukyokuNameRyaku</td><td>部局名（略称）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShokushuNameRyaku</td><td>職名（略称）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainNo</td><td>審査員番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainNameKanjiSei</td><td>審査員名（漢字-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainNameKanjiMei</td><td>審査員名（漢字-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainShozokuName</td><td>審査員所属機関名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainBukyokuName</td><td>審査員部局名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainShokuName</td><td>審査員職名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>JigyoKubun</td><td>事業区分（審査結果）</td></tr>
	 *	</table><br/>
	 * <b>3.WarifuriInfo返却</b><br/>
	 *	2で作成したWarifuriInfoを返却する。
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsaKekkaPk)
	 */
	public WarifuriInfo select(UserInfo userInfo, ShinsaKekkaPk pkInfo)
		throws ApplicationException {

		WarifuriSearchInfo searchInfo = new WarifuriSearchInfo();
		
		searchInfo.setSystemNo(pkInfo.getSystemNo());		//システム番号
		searchInfo.setShinsainNo(pkInfo.getShinsainNo());	//審査員番号
		searchInfo.setJigyoKubun(pkInfo.getJigyoKubun());	//事業区分

		Page page = search(userInfo, searchInfo);
		HashMap editInfo = (HashMap)page.getList().get(0);

		WarifuriInfo warifuriInfo = new WarifuriInfo();
			
		warifuriInfo.setNendo((String)editInfo.get("NENDO"));									//年度
		warifuriInfo.setKaisu(editInfo.get("KAISU").toString());								//回数
		warifuriInfo.setJigyoName((String)editInfo.get("JIGYO_NAME"));							//事業名
		warifuriInfo.setSystemNo((String)editInfo.get("SYSTEM_NO"));							//システム番号
		warifuriInfo.setUketukeNo((String)editInfo.get("UKETUKE_NO"));							//申請番号
		warifuriInfo.setKeiNameRyaku((String)editInfo.get("KEI_NAME_RYAKU"));					//系等の区分（略称）
		warifuriInfo.setKadaiNameKanji((String)editInfo.get("KADAI_NAME_KANJI"));				//研究課題名（和文）
		warifuriInfo.setNameKanjiSei((String)editInfo.get("NAME_KANJI_SEI"));					//申請者名（漢字等-姓）
		warifuriInfo.setNameKanjiMei((String)editInfo.get("NAME_KANJI_MEI"));					//申請者名（漢字等-名）
		warifuriInfo.setShozokuNameRyaku((String)editInfo.get("SHOZOKU_NAME_RYAKU"));			//所属機関名（略称）
		warifuriInfo.setBukyokuNameRyaku((String)editInfo.get("BUKYOKU_NAME_RYAKU"));			//部局名（略称）
		warifuriInfo.setShokushuNameRyaku((String)editInfo.get("SHOKUSHU_NAME_RYAKU"));			//職名（略称）
		warifuriInfo.setShinsainNo((String)editInfo.get("SHINSAIN_NO"));						//審査員番号
		warifuriInfo.setShinsainNameKanjiSei((String)editInfo.get("SHINSAIN_NAME_KANJI_SEI"));	//審査員名（漢字-姓）
		warifuriInfo.setShinsainNameKanjiMei((String)editInfo.get("SHINSAIN_NAME_KANJI_MEI"));	//審査員名（漢字-名）
		warifuriInfo.setShinsainShozokuName((String)editInfo.get("SHINSAIN_SHOZOKU_NAME"));		//審査員所属機関名
		warifuriInfo.setShinsainBukyokuName((String)editInfo.get("SHINSAIN_BUKYOKU_NAME"));		//審査員部局名
		warifuriInfo.setShinsainShokuName((String)editInfo.get("SHINSAIN_SHOKUSHU_NAME"));		//審査員職名
//整理番号を追加　2005/10/17
		warifuriInfo.setSeiriNo((String)editInfo.get("JURI_SEIRI_NO"));							//整理番号  

		String jigyoKubun = new Integer(((Number) editInfo.get("JIGYO_KUBUN")).intValue()).toString();
		warifuriInfo.setJigyoKubun(jigyoKubun);						//事業区分（審査結果）
				
		return warifuriInfo;
	}

	/**
	 * 割り振り結果情報を検索する.<br />
	 * <br />
	 * <b>1.割り振り結果情報検索</b><br/>
	 *	以下の検索SQLを実行する。
	 *	なお、検索結果がない場合、もしくは結果件数がserchInfoのMaxSizeより大きい場合は例外をthrowする。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		B.SYSTEM_NO,			--  システム番号
	 *		A.NENDO,				--  年度
	 *		A.KAISU,				--  回数
	 *		B.JIGYO_NAME,			--  事業名
	 *		B.UKETUKE_NO,			--  申請番号
	 *		A.KEI_NAME_RYAKU,			--  系等の区分（略称）
	 *		A.KADAI_NAME_KANJI,			--  研究課題名（和文）
	 *		A.NAME_KANJI_SEI,			--  申請者氏名（漢字等-姓）
	 *		A.NAME_KANJI_MEI,			--  申請者氏名（漢字等-名）
	 *		A.SHOZOKU_NAME_RYAKU, 		--  所属機関名（略称）
	 *		A.BUKYOKU_NAME_RYAKU, 		--  部局名（略称）
	 *		A.SHOKUSHU_NAME_RYAKU, 		--  職名（略称）
	 *		B.SHINSAIN_NO, 			--  審査員番号
	 *		B.JIGYO_KUBUN, 			--  事業区分（審査結果）
	 *		B.SHINSAIN_NAME_KANJI_SEI,		--  審査員名（漢字-姓）
	 *		B.SHINSAIN_NAME_KANJI_MEI,		--  審査員名（漢字-名）
	 *		B.SHOZOKU_NAME SHINSAIN_SHOZOKU_NAME,	--  審査員所属機関名
	 *		B.BUKYOKU_NAME SHINSAIN_BUKYOKU_NAME,	--  審査員部局名
	 *		B.SHOKUSHU_NAME SHINSAIN_SHOKUSHU_NAME,	--  審査員職名
	 *		B.SHINSA_JOKYO,			--  審査完了フラグ
	 *		A.JOKYO_ID				--  状況ID
	 *	FROM
	 *		(
	 *			SELECT * 
	 *			FROM SHINSEIDATAKANRI
	 *			WHERE DEL_FLG=0
	 *		) A,
	 *		SHINSAKEKKA B
	 *	WHERE
	 *		A.SYSTEM_NO = B.SYSTEM_NO
	 *	
	 *				<b><span style="color:#002288">－－動的検索条件1－－</span></b>
	 *	
	 *		AND (	--　ステータス状況
	 *			(A.JOKYO_ID = '06' AND A.SAISHINSEI_FLG IN ('0','2'))
	 *						--　受理,再申請フラグ
	 *						--  （初期値、再申請済み）
	 *			OR (A.JOKYO_ID = '08')	--　審査員割り振り処理後
	 *			OR (A.JOKYO_ID = '09')	--　割り振りチェック完了
	 *			OR (A.JOKYO_ID = '10')	--　1次審査中
	 *			OR (A.JOKYO_ID = '11')	--　1次審査完了
	 *			OR (A.JOKYO_ID = '12')	--　2次審査完了
	 *		)
	 *	
	 *				<b><span style="color:#882200">－－動的検索条件2－－</span></b>
	 *	
	 *	ORDER BY B.JIGYO_ID, B.UKETUKE_NO, B.SHINSAIN_NO
	 *	</td></tr>
	 *	</table><br />
	 *	<b><span style="color:#002288">動的検索条件1</span></b><br />
	 *	引数のsearchInfoの値によって検索条件が動的に変化する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr style="color:white;font-weight: bold"><td>変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請番号</td><td>UketukeNo</td><td> AND B.UKETUKE_NO = '申請番号'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>jigyoCdValueList</td><td>AND SUBSTR(B.JIGYO_ID, 3, 5) IN ('事業コード1','事業コード2'…)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>担当事業区分（複数）</td><td>TantoJigyoKubun</td><td>AND B.JIGYO_KUBUN IN ('担当事業区分1','担当事業区分2'…)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>年度</td><td>Nendo</td><td>AND A.NENDO = '年度'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>回数</td><td>Kaisu</td><td>AND A.KAISU = '回数'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（漢字等-姓）</td><td>NameKanjiSei</td><td>AND A.NAME_KANJI_SEI LIKE '%申請者氏名（漢字等-姓）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（漢字等-名）</td><td>NameKanjiMei</td><td>AND A.NAME_KANJI_MEI LIKE '%申請者氏名（漢字等-名）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（フリガナ-姓）</td><td>NameKanaSei</td><td>AND A.NAME_KANA_SEI LIKE '%申請者氏名（フリガナ-姓）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（フリガナ-名）</td><td>NameKanaMei</td><td>AND A.NAME_KANA_MEI LIKE '%申請者氏名（フリガナ-名）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（ローマ字-姓）</td><td>NameRoSei</td><td>AND UPPER(A.NAME_RO_SEI) LIKE '%申請者氏名（ローマ字-姓）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（ローマ字-名）</td><td>NameRoMei</td><td>AND UPPER(A.NAME_RO_MEI) LIKE '%申請者氏名（ローマ字-名）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>ShozokuCd</td><td>AND A.SHOZOKU_CD = '所属機関コード'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>細目番号</td><td>BunkaSaimokuCd</td><td>AND A.BUNKASAIMOKU_CD = '細目番号'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>系等の区分</td><td>KeiName</td><td>AND A.KEI_NAME LIKE '%系等の区分%'</td></tr>
	 *	</table><br/>
	 *	<b><span style="color:#882200">動的検索条件2</span></b><br />
	 *	引数のsearchInfoの値によって検索条件が動的に変化する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr style="color:white;font-weight: bold"><td>変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>システム番号</td><td>SystemNo</td><td>AND B.SYSTEM_NO = システム番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>ShinsainNo</td><td>AND B.SHINSAIN_NO = '審査員番号'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業区分（審査結果）</td><td>JigyoKubun</td><td>AND B.JIGYO_KUBUN = '事業区分（審査結果）'</td></tr>
	 *	</table><br/>
	 * <b>2.Pageの返却</b><br/>
	 *	検索結果をPageに格納後返却する。
	 * 
	 * 
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.WarifuriSearchInfo)
	 */
	public Page search(UserInfo userInfo, WarifuriSearchInfo searchInfo)
		throws ApplicationException {

		//一覧に表示できる申請データの組み合わせステータス検索条件を作成
		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
		String[] saishinseiArray = new String[]{
                StatusCode.SAISHINSEI_FLG_DEFAULT,
                StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI
        };//再申請フラグ（初期値、再申請済み）
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);    	 //「受理」:06
		statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null); 	     //「審査員割り振り処理後」:08
		statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);    		 //「割り振りチェック完了」:09
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);    				 //「1次審査中」:10
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);    			 //「1次審査完了」:11
		statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);    			 //「2次審査完了」:12
		
		
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------

		String select = "SELECT"
							+ " B.SYSTEM_NO,"								//システム番号
							+ " A.NENDO,"									//年度
							+ " A.KAISU,"									//回数
							+ " B.JIGYO_NAME,"								//事業名
							+ " B.UKETUKE_NO,"								//申請番号
							+ " B.NYURYOKU_JOKYO,"							//利益相反入力状況 2006/10/24 add by liucy
							+ " A.KEI_NAME_RYAKU,"							//系等の区分（略称）
							+ " A.JURI_SEIRI_NO,"							//整理番号			2005/10/17追加
							+ " A.KADAI_NAME_KANJI,"						//研究課題名（和文）
							+ " A.NAME_KANJI_SEI,"							//申請者氏名（漢字等-姓）
							+ " A.NAME_KANJI_MEI,"							//申請者氏名（漢字等-名）
							+ " A.SHOZOKU_NAME_RYAKU, "						//所属機関名（略称）
							+ " A.BUKYOKU_NAME_RYAKU, "						//部局名（略称）
							+ " A.SHOKUSHU_NAME_RYAKU, "					//職名（略称）
							+ " B.SHINSAIN_NO, "							//審査員番号
							+ " B.JIGYO_KUBUN, "							//事業区分（審査結果）
							+ " B.SHINSAIN_NAME_KANJI_SEI,"					//審査員名（漢字-姓）	
							+ " B.SHINSAIN_NAME_KANJI_MEI,"					//審査員名（漢字-名）
							+ " B.SHOZOKU_NAME SHINSAIN_SHOZOKU_NAME, "		//審査員所属機関名
							+ " B.BUKYOKU_NAME SHINSAIN_BUKYOKU_NAME,"		//審査員部局名
							+ " B.SHOKUSHU_NAME SHINSAIN_SHOKUSHU_NAME,"	//審査員職名
							+ " B.SHINSA_JOKYO,"							//審査完了フラグ
							+ " B.RIGAI,"									//利害関係			2005/11/1追加
							+ " B.DAIRI,"									//代理フラグ		2005/11/4追加
							+ " B.KOSHIN_DATE,"								//割り振り更新日		2005/11/8追加
							+ " A.JOKYO_ID"									//状況ID
	  						+ " FROM "
							+ " (SELECT * FROM SHINSEIDATAKANRI"
							+ " WHERE DEL_FLG=0"
//							+ " AND"
//							+ " (" 
//							+ " (JOKYO_ID = '" +StatusCode.STATUS_GAKUSIN_JYURI + "'"		//申請状況が[06]で再申請フラグが[0]または[1]
//							+ " AND SAISHINSEI_FLG IN ('" + StatusCode.SAISHINSEI_FLG_DEFAULT 
//							+ " ', '" + StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI + "')" 
//							+ " )"			
//							+ " OR"
//							+ " JOKYO_ID IN('" 	
//							+  StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO 		//申請状況が[08]
//							+ "', '" + StatusCode.STATUS_WARIFURI_CHECK_KANRYO		//申請状況が[09]
//							+ "', '" + StatusCode.STATUS_1st_SHINSATYU				//申請状況が[10]
//							+ "', '" + StatusCode.STATUS_1st_SHINSA_KANRYO 		//申請状況が[11]
//							+ "', '" + StatusCode.STATUS_2nd_SHINSA_KANRYO +  "')"	//申請状況が[12]
//							+ " )"					
							+ " ) A,"
							+ " SHINSAKEKKA B"
	  						+ " WHERE A.SYSTEM_NO = B.SYSTEM_NO "
						//2005/04/28 追加 ここから------------------------------------------
						//基盤で状況IDが06(学振受理)は表示しないように条件の追加
//                            + " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
//2006/05/09 追加ここから               
	  						//若手で状況IDが06(学振受理)は表示しないように条件の追加
//							+ " AND NOT ((A.JIGYO_KUBUN = '6' OR A.JIGYO_KUBUN = '4') AND A.JOKYO_ID = '06')"
                            + " AND NOT ((A.JIGYO_KUBUN IN ("
                            + StringUtil.changeIterator2CSV(JigyoKubunFilter.Convert2ShinseishoJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(),true)
                            + ")) AND A.JOKYO_ID = '06')"
// 苗　追加ここまで  
						//追加 ここまで-----------------------------------------------------
						;

		//2005.11.16 iso 検索条件は別メソッドへ
//		StringBuffer query = new StringBuffer(select);
//
//		//申請番号
//		if(searchInfo.getUketukeNo() != null && !searchInfo.getUketukeNo().equals("")){
//			query.append(" AND B.UKETUKE_NO = '" + searchInfo.getUketukeNo() + "'");
//		}
//		//事業コード
//		List jigyoCdValueList = searchInfo.getJigyoCdValueList();
//		if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
//			query.append(" AND SUBSTR(B.JIGYO_ID, 3, 5) IN (")
//				 .append(changeIterator2CSV(searchInfo.getJigyoCdValueList().iterator()))
//				 .append(")");
//		}
//		//担当事業区分（複数）
//		if(searchInfo.getTantoJigyoKubun() != null && searchInfo.getTantoJigyoKubun().size() != 0){
//			query.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(changeIterator2CSV(searchInfo.getTantoJigyoKubun().iterator()))
//				 .append(")");
//		}
//		//年度
//		if(searchInfo.getNendo() != null && !searchInfo.getNendo().equals("")){
//			query.append(" AND A.NENDO = '" + EscapeUtil.toSqlString(searchInfo.getNendo()) + "'");
//		}
//		//回数
//		if(searchInfo.getKaisu() != null && !searchInfo.getKaisu().equals("")){
//			query.append(" AND A.KAISU = '" + EscapeUtil.toSqlString(searchInfo.getKaisu()) + "'");
//		}
//		//申請者氏名（漢字等-姓）
//		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	
//			query.append(" AND A.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
//		}
//		//申請者氏名（漢字等-名）
//		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	
//			query.append(" AND A.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
//		}
//		//申請者氏名（フリガナ-姓）
//		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	
//			query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
//		}
//		//申請者氏名（フリガナ-名）
//		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){
//			query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
//		}
//		//申請者氏名（ローマ字-姓）
//		if(searchInfo.getNameRoSei() != null && !searchInfo.getNameRoSei().equals("")){
//			query.append(" AND UPPER(A.NAME_RO_SEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()) + "%'");
//		}
//		//申請者氏名（ローマ字-名）
//		if(searchInfo.getNameRoMei() != null && !searchInfo.getNameRoMei().equals("")){
//			query.append(" AND UPPER(A.NAME_RO_MEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()) + "%'");
//		}
//		//所属機関コード
//		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){
//			query.append(" AND A.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
//		}
//		//細目番号
//		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){
//			query.append(" AND A.BUNKASAIMOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'");
//		}
//		//系等の区分
//		if(searchInfo.getKeiName() != null && !searchInfo.getKeiName().equals("")){
//			query.append(" AND A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%'");
//		}
//		//整理番号			2005/10/17　追加
//		if(searchInfo.getSeiriNo() != null && !searchInfo.getSeiriNo().equals("")){
//			query.append(" AND A.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//
//		//2005/11/8追加
//		//利害関係
//		if(searchInfo.getRigai() != null && "1".equals(searchInfo.getRigai())){
//			query.append(" AND B.SYSTEM_NO IN (SELECT DISTINCT SYSTEM_NO FROM SHINSAKEKKA");
//			query.append(					" WHERE RIGAI = '1' AND SHINSA_JOKYO = '1')");
//		}
//		
//		//割振り
//		if(searchInfo.getWarifuriFlg() != null && "1".equals(searchInfo.getWarifuriFlg())){
//			query.append(" AND B.SYSTEM_NO IN (SELECT A.SYSTEM_NO");
//			query.append("  FROM (");
//			query.append("    SELECT SYSTEM_NO,");
//			query.append("           SUM(CASE WHEN RIGAI='1' AND SHINSA_JOKYO = '1' THEN 1 ELSE 0 END) RIGAI_CNT,");
//			query.append("           SUM(DECODE(DAIRI,'1',1,0)) DAIRI_CNT");
//			query.append("      FROM SHINSAKEKKA");
//			query.append("     GROUP BY SYSTEM_NO) A");
//			query.append(" WHERE A.RIGAI_CNT > 0");
//			query.append("   AND A.DAIRI_CNT = 0");
//			query.append(")");
//		}
//		//2005/11/8追加完了
//		
//		//組み合わせステータス状況
//		if(statusInfo != null && statusInfo.hasQuery()){
//			query.append(" AND")
//					 .append(statusInfo.getQuery());
//		}
		StringBuffer query = new StringBuffer(getQueryString(select, userInfo, searchInfo, statusInfo));
				
		//----ここから、審査員割り振り結果登録画面表示に必要な検索条件
		//システム番号
		if(searchInfo.getSystemNo() != null && !searchInfo.getSystemNo().equals("")){
			query.append(" AND B.SYSTEM_NO = " + EscapeUtil.toSqlString(searchInfo.getSystemNo()));
		}
		//審査員番号
		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){
			query.append(" AND B.SHINSAIN_NO = '" + EscapeUtil.toSqlString(searchInfo.getShinsainNo())+ "'");
		}
		//事業区分（審査結果）
		if(searchInfo.getJigyoKubun() != null && !searchInfo.getJigyoKubun().equals("")){
			query.append(" AND B.JIGYO_KUBUN = '" + EscapeUtil.toSqlString(searchInfo.getJigyoKubun())+ "'");
		}

		//----ここまで
			
		//ソート順（事業ID、申請番号、システム番号(申請書毎のグループ化のため)、審査員番号の昇順）
		query.append(" ORDER BY B.JIGYO_ID, B.UKETUKE_NO, B.SYSTEM_NO, B.SHINSAIN_NO ");
		
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
				"割り振り結果データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}


	/**
	 * 割り振り結果情報を削除する.<br />
	 * <br />
	 * <b>1.審査結果情報取得</b><br/>
	 *	以下のSQLを実行し、取得した情報をShinsaKekkaInfoへ格納する。（バインド変数はSQLの下の表を参照）
	 *	レコードが存在しない場合は例外をthrowする。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		 A.SYSTEM_NO		--  システム受付番号
	 *		,A.UKETUKE_NO		--  申請番号
	 *		,A.SHINSAIN_NO		--  審査員番号
	 *		,A.JIGYO_KUBUN		--  事業区分
	 *		,A.SEQ_NO			--  シーケンス番号
	 *		,A.SHINSA_KUBUN		--  審査区分
	 *		,A.SHINSAIN_NAME_KANJI_SEI	--  審査員名（漢字－姓）
	 *		,A.SHINSAIN_NAME_KANJI_MEI	--  審査員名（漢字－名）
	 *		,A.NAME_KANA_SEI		--  審査員名（フリガナ－姓）
	 *		,A.NAME_KANA_MEI		--  審査員名（フリガナ－名）
	 *		,A.SHOZOKU_NAME		--  審査員所属機関名
	 *		,A.BUKYOKU_NAME		--  審査員部局名
	 *		,A.SHOKUSHU_NAME		--  審査員職名
	 *		,A.JIGYO_ID		--  事業ID
	 *		,A.JIGYO_NAME		--  事業名
	 *		,A.BUNKASAIMOKU_CD		--  細目番号
	 *		,A.EDA_NO			--  枝番
	 *		,A.CHECKDIGIT		--  チェックデジット
	 *		,A.KEKKA_ABC		--  総合評価（ABC）
	 *		,A.KEKKA_TEN		--  総合評価（点数）
	 *		,A.COMMENT1		--  コメント1
	 *		,A.COMMENT2		--  コメント2
	 *		,A.COMMENT3		--  コメント3
	 *		,A.COMMENT4		--  コメント4
	 *		,A.COMMENT5		--  コメント5
	 *		,A.COMMENT6		--  コメント6
	 *		,A.KENKYUNAIYO		--  研究内容
	 *		,A.KENKYUKEIKAKU		--  研究計画
	 *		,A.TEKISETSU_KAIGAI		--  適切性-海外
	 *		,A.TEKISETSU_KENKYU1		--  適切性-研究（1）
	 *		,A.TEKISETSU		--  適切性
	 *		,A.DATO			--  妥当性
	 *		,A.SHINSEISHA		--  研究代表者
	 *		,A.KENKYUBUNTANSHA		--  研究分担者
	 *		,A.HITOGENOMU		--  ヒトゲノム
	 *		,A.TOKUTEI			--  特定胚
	 *		,A.HITOES			--  ヒトES細胞
	 *		,A.KUMIKAE			--  遺伝子組換え実験
	 *		,A.CHIRYO			--  遺伝子治療臨床研究
	 *		,A.EKIGAKU			--  疫学研究
	 *		,A.COMMENTS		--  コメント
	 *		,A.TENPU_PATH		--  添付ファイル格納パス
	 *		,DECODE (
	 *			NVL(A.TENPU_PATH,'null') ,'null','FALSE','TRUE'
	 *	 	) TENPU_FLG		--  添付ファイル格納フラグ
	 *					--  添付ファイル格納パスがNULL:FALSE
	 *					--	NULLじゃない:TRUE
	 *		,A.SHINSA_JOKYO		--  審査状況
	 *		,A.BIKO			--  備考
	 *	FROM
	 *		SHINSAKEKKA A
	 *	WHERE
	 *		SYSTEM_NO = 'システム受付番号'
	 *		AND SHINSAIN_NO = '審査員番号'
	 *		AND JIGYO_KUBUN = '事業区分'
	 *	FOR UPDATE
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>第二引数のshinsaKekkaPkの変数（systemNo）の値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数のshinsaKekkaPkの変数（shinsainNo）の値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数のshinsaKekkaPkの変数（jigyoKubun）の値</td></tr>
	 *	</table><br/>
	 * <br />
	 * <b>2.審査結果情報削除</b><br/>
	 *	以下のSQLを実行し、審査結果情報を物理削除する。（バインド変数はSQLの下の表を参照）
	 *	レコードが存在しない場合は例外をthrowする。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		SHINSAKEKKA
	 *	WHERE
	 *		SYSTEM_NO = 'システム受付番号'
	 *		AND SHINSAIN_NO = '審査員番号'
	 *		AND JIGYO_KUBUN = '事業区分'
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>第二引数のshinsaKekkaPkの変数（systemNo）の値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数のshinsaKekkaPkの変数（shinsainNo）の値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数のshinsaKekkaPkの変数（jigyoKubun）の値</td></tr>
	 *	</table><br/>
	 * <br />
	 * <b>3.審査結果を初期状態に戻す</b><br/>
	 *	以下のSQLを実行し、審査結果情報をINSERTする。（バインド変数はSQLの下の表を参照）
	 *	レコードが存在しない場合は例外をthrowする。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO 
	 *		SHINSAKEKKA(
	 *			SYSTEM_NO			--  システム受付番号
	 *			,UKETUKE_NO		--  申請番号
	 *			,SHINSAIN_NO		--  審査員番号
	 *			,JIGYO_KUBUN		--  事業区分
	 *			,SEQ_NO			--  シーケンス番号
	 *			,SHINSA_KUBUN		--  審査区分
	 *			,SHINSAIN_NAME_KANJI_SEI	--  審査員名（漢字－姓）
	 *			,SHINSAIN_NAME_KANJI_MEI	--  審査員名（漢字－名）
	 *			,NAME_KANA_SEI		--  審査員名（フリガナ－姓）
	 *			,NAME_KANA_MEI		--  審査員名（フリガナ－名）
	 *			,SHOZOKU_NAME		--  審査員所属機関名
	 *			,BUKYOKU_NAME		--  審査員部局名
	 *			,SHOKUSHU_NAME		--  審査員職名
	 *			,JIGYO_ID			--  事業ID
	 *			,JIGYO_NAME		--  事業名
	 *			,BUNKASAIMOKU_CD		--  分科細目コード
	 *			,EDA_NO			--  枝番
	 *			,CHECKDIGIT		--  チェックデジット
	 *			,KEKKA_ABC			--  総合評価（ABC）
	 *			,KEKKA_TEN			--  総合評価（点数）
	 *			,COMMENT1			--  コメント1
	 *			,COMMENT2			--  コメント2
	 *			,COMMENT3			--  コメント3
	 *			,COMMENT4			--  コメント4
	 *			,COMMENT5			--  コメント5
	 *			,COMMENT6			--  コメント6
	 *			,KENKYUNAIYO		--  研究内容
	 *			,KENKYUKEIKAKU		--  研究計画
	 *			,TEKISETSU_KAIGAI		--  適切性-海外
	 *			,TEKISETSU_KENKYU1		--  適切性-研究（1）
	 *			,TEKISETSU			--  適切性
	 *			,DATO			--  妥当性
	 *			,SHINSEISHA		--  研究代表者
	 *			,KENKYUBUNTANSHA		--  研究分担者
	 *			,HITOGENOMU		--  ヒトゲノム
	 *			,TOKUTEI			--  特定胚
	 *			,HITOES			--  ヒトES細胞
	 *			,KUMIKAE			--  遺伝子組換え実験
	 *			,CHIRYO			--  遺伝子治療臨床研究
	 *			,EKIGAKU			--  疫学研究
	 *			,COMMENTS			--  コメント
	 *			,TENPU_PATH		--  添付ファイル格納パス
	 *			,SHINSA_JOKYO		--  審査状況
	 *			,BIKO			--  備考
	 *		)
	 *	VALUES
	 *		(?,?,?,?,?,?,?,?,?,?,?,?,?,?,
	 *		 ?,?,?,?,?,?,?,?,?,?,?,?,?,?,
	 *		 ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>1で取得したシステム受付番号を利用</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請番号</td><td>1で取得した申請番号を利用</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>@00000+1で取得したシーケンス番号を利用</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>1で取得した事業区分を利用</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>シーケンス番号</td><td>1で取得したシーケンス番号を利用</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査区分</td><td>1で取得した審査区分を利用</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（漢字－姓）</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（漢字－名）</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－姓）</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－名）</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員所属機関名</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員部局名</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員職名</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>1で取得した事業IDを利用</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業名</td><td>1で取得した事業名を利用</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>分科細目コード</td><td>1で取得した分科細目コードを利用</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>枝番</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>チェックデジット</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>総合評価（ABC）</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>総合評価（点数）</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント1</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント2</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント3</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント4</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント5</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント6</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究内容</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究計画</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>適切性-海外</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>適切性-研究（1）</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>適切性</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>妥当性</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究代表者</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究分担者</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ヒトゲノム</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>特定胚</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ヒトES細胞</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>遺伝子組換え実験</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>遺伝子治療臨床研究</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>疫学研究</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>コメント</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>添付ファイル格納パス</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査状況</td><td>0(未完了)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>備考</td><td>null</td></tr>
	 *	</table><br/>
	 * 
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsaKekkaPk)
	 */
	public void delete(UserInfo userInfo, ShinsaKekkaPk shinsaKekkaPk) throws ApplicationException {
		
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
				
			ShinsaKekkaInfoDao shinsaKekkaDao = new ShinsaKekkaInfoDao(userInfo);
			//---------------------------------------
			//審査結果情報の取得
			//---------------------------------------
			ShinsaKekkaInfo updateInfo = null;
			try {
				updateInfo = shinsaKekkaDao.selectShinsaKekkaInfoForLock(connection, shinsaKekkaPk);		
			} catch (NoDataFoundException e) {
				throw e;
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"審査結果データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4003"),
					e);
			}
			
			//---------------------------------------
			//審査結果テーブルの削除
			//---------------------------------------
			try {
				//DB更新
				shinsaKekkaDao.deleteShinsaKekkaInfo(connection, shinsaKekkaPk);
			} catch (NoDataFoundException e) {
				throw e;
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"審査結果データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4003"),
					e);
			}
			
			//2004/12/3update
			//削除の場合は、登録された審査結果を初期状態に戻すように変更。
			//---------------------------------------
			//審査結果テーブルの登録
			//---------------------------------------
			//新規に審査結果情報を作成
			ShinsaKekkaInfo newShinsaKekkaInfo = new 	ShinsaKekkaInfo();
			newShinsaKekkaInfo.setSystemNo(updateInfo.getSystemNo());				//システム受付番号
			newShinsaKekkaInfo.setUketukeNo(updateInfo.getUketukeNo());				//申請番号
			if(Integer.parseInt(updateInfo.getSeqNo()) >= 10){
				newShinsaKekkaInfo.setShinsainNo("@0000" + updateInfo.getSeqNo());	//審査員番号(7桁)
			}else{
				newShinsaKekkaInfo.setShinsainNo("@00000" + updateInfo.getSeqNo());	//審査員番号(7桁)
			}
			newShinsaKekkaInfo.setJigyoKubun(updateInfo.getJigyoKubun());			//事業区分
			newShinsaKekkaInfo.setSeqNo(updateInfo.getSeqNo());						//シーケンス番号
			newShinsaKekkaInfo.setShinsaKubun(updateInfo.getShinsaKubun());			//審査区分
			newShinsaKekkaInfo.setJigyoId(updateInfo.getJigyoId());					//事業ID
			newShinsaKekkaInfo.setJigyoName(updateInfo.getJigyoName());				//事業名
			newShinsaKekkaInfo.setBunkaSaimokuCd(updateInfo.getBunkaSaimokuCd());	//分科細目コード									
			newShinsaKekkaInfo.setShinsaJokyo("0");									//審査状況（未完了）											
						
			//---------------------------------------
			//審査結果テーブルの登録
			//---------------------------------------
//			if(updateInfo.getSeqNo() != null){
//				updateInfo.setShinsainNo("@0000" + updateInfo.getSeqNo());		//審査員番号
//			}else{
//				throw new ApplicationException(
//					"審査結果情報のシーケンス番号が不正です。"
//					+ "審査員番号'" + updateInfo.getShinsainNo() +"','"
//					+ "シーケンス番号'" + updateInfo.getSeqNo() + "'" ,
//					new ErrorInfo("errors.4003"));				
//			}
//			updateInfo.setShinsainNameKanjiSei(null);		//審査員名（漢字-姓）
//			updateInfo.setShinsainNameKanjiMei(null);		//審査員名（漢字-名）
//			updateInfo.setNameKanaSei(null);				//審査員名（フリガナ-姓）
//			updateInfo.setNameKanaMei(null);				//審査員名（フリガナ-名）
//			updateInfo.setShozokuName(null);				//審査員所属機関名
//			updateInfo.setBukyokuName(null);				//審査員部局名
//			updateInfo.setShokushuName(null);				//審査員職名	

			try {
				//DB更新
				shinsaKekkaDao.insertShinsaKekkaInfo(connection, newShinsaKekkaInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"審査結果データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4003"),
					e);
			}
			
			//2005.01.24
			//申請データテーブルの評価順にソートされた1次審査結果情報を更新する。
			//複数箇所で使用するので別メソッドにすべき？
			//申請データ管理DAO
			ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);

			//排他制御のため既存データを取得する
			ShinseiDataPk shinseiDataPk = new ShinseiDataPk(shinsaKekkaPk.getSystemNo());
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
					"当該申請データは削除されています。SystemNo=" + shinsaKekkaPk.getSystemNo(),
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
					+ shinsaKekkaPk.getSystemNo(),
					new ErrorInfo("errors.9012"));
			}

			//---審査結果レコード取得（結果ABCの昇順）---
			ShinsaKekkaInfo[] shinsaKekkaInfoArray = null;						
			try{
				shinsaKekkaInfoArray = shinsaKekkaDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}

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
				for(int i=0; i<shinsaKekkaInfoArray.length; i++){
					try{
						//総合評価（ABC）と総合評価（点数）は混在しない
						if(shinsaKekkaInfoArray[i].getKekkaAbc() != null){
							kekkaAbc = kekkaAbc + shinsaKekkaInfoArray[i].getKekkaAbc();
							String tensu = (String) sogoHyokaMap.get(shinsaKekkaInfoArray[i].getJigyoKubun()
														+ shinsaKekkaInfoArray[i].getKekkaAbc());
							if(tensu == null){
								throw new ApplicationException(
									"総合評価マスタに一致するデータが存在しません。検索キー：総合評価'"
									+ shinsaKekkaInfoArray[i].getKekkaAbc() 
									+ "',事業区分：'" +  shinsaKekkaInfoArray[i].getJigyoKubun() + "'",
									new ErrorInfo("errors.4002"));	
							}
							intKekkaTen = intKekkaTen
												+ Integer.parseInt((String) sogoHyokaMap.get(
																shinsaKekkaInfoArray[i].getJigyoKubun()
																	 + shinsaKekkaInfoArray[i].getKekkaAbc()));
							kekkaTenFlag = true;	//1つでも点数が設定されていた場合[true]						
						}else if(shinsaKekkaInfoArray[i].getKekkaTen() != null){
								//１次審査結果(点数)
								intKekkaTen = intKekkaTen
													+ Integer.parseInt((String) sogoHyokaMap.get(
																	shinsaKekkaInfoArray[i].getJigyoKubun()
																		 + shinsaKekkaInfoArray[i].getKekkaTen()));
								
								//１次審査結果(点数順)
								if(kekkaTenSorted.equals("")){
									kekkaTenSorted = kekkaTenSorted + shinsaKekkaInfoArray[i].getKekkaTen();
								}else{
									kekkaTenSorted = kekkaTenSorted + " " + shinsaKekkaInfoArray[i].getKekkaTen();									
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

			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

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
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}	
	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.WarifuriInfo)
	 */
//	public WarifuriInfo validate(UserInfo userInfo, WarifuriInfo info, String mode)
//		throws ApplicationException, ValidationException {
//
//			Connection connection = null;	
//			try {
//				connection = DatabaseUtil.getConnection();
//				//エラー情報保持用リスト
//				List errors = new ArrayList();
//
//				//2重登録チェック
//				//同じ申請に同じ審査員が、割り振られていないかどうかを確認
//				ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);
//				ShinsaKekkaPk shinsaKekkaPk = new ShinsaKekkaPk();
//				shinsaKekkaPk.setSystemNo(info.getSystemNo());
//				shinsaKekkaPk.setShinsainNo(info.getShinsainNo());
//				shinsaKekkaPk.setJigyoKubun(info.getJigyoKubun());
//				try {
//					dao.selectShinsaKekkaInfo(connection, shinsaKekkaPk);
//					//NG
//					String[] error = {"審査員"};
//					throw new ApplicationException("すでに同一の審査員が登録されています。", 	new ErrorInfo("errors.4007", error));			
//				} catch (NoDataFoundException e) {
//					//OK
//				}
//
//				//-----入力エラーがあった場合は例外をなげる-----
//				if (!errors.isEmpty()) {
//					throw new ValidationException(
//						"審査員割り振りデータチェック中にエラーが見つかりました。",
//						errors);
//				}
//				return info;
//			} catch (DataAccessException e) {
//				throw new ApplicationException(
//					"審査員管理データチェック中にDBエラーが発生しました。",
//					new ErrorInfo("errors.4005"),
//					e);
//			} finally {
//				DatabaseUtil.closeConnection(connection);
//			}
//
//	}

	/**
	 * 依頼書作成.<br />
	 * <br />
	 * 依頼書の作成を行い、作成したCSVファイルのFileResourceを返却する。<br />
	 * <b>1.審査情報取得</b><br/>
	 *	以下のSQLを実行し、審査員の情報を取得する。（バインド変数はSQLの下の表を参照）
	 *	検索結果は各レコードの情報をListに格納し、更に全体を管理するListへ格納する。
	 *	ただし、取得する審査員の情報は、審査割振された者だけとする。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT DISTINCT
	 *		B.SHINSAIN_NO	"審査員番号",
	 *		C.NAME_KANJI_SEI	"審査員名（漢字-姓）",
	 *		C.NAME_KANJI_MEI	"審査員名（漢字-名）",
	 *		C.SHOZOKU_NAME	"審査員所属機関名",
	 *		C.BUKYOKU_NAME	"審査員部局名",
	 *		C.SHOKUSHU_NAME	"審査員職名",
	 *		C.SOFU_ZIP	"送付先（郵便番号）",
	 *		C.SOFU_ZIPADDRESS	"送付先（住所）",
	 *		C.SOFU_ZIPEMAIL	"送付先（Email）",
	 *		C.SHOZOKU_TEL	"電話番号",
	 *		C.SHOZOKU_FAX	"FAX番号",
	 *		C.BIKO	"備考",
	 *		D.SHINSAIN_ID	"審査員ID",
	 *		D.PASSWORD	"パスワード",
	 *		TO_CHAR(D.YUKO_DATE, 'YYYY/MM/DD')	"有効期限"
	 *	FROM 
	 *		SHINSEIDATAKANRI A,
	 *		SHINSAKEKKA B,
	 *		MASTER_SHINSAIN C,
	 *		SHINSAININFO D
	 *	WHERE
	 *		A.SYSTEM_NO = B.SYSTEM_NO		--　システム番号
	 *		AND B.SHINSAIN_NO = C.SHINSAIN_NO	--　審査員番号
	 *		AND B.SHINSAIN_NO = SUBSTR(D.SHINSAIN_ID,4,7)
	 *						--　審査員番号(7桁)
	 *		AND A.DEL_FLG = 0
	 *	
	 *					－－動的検索条件－－
	 *	
	 *		AND (		--　ステータス状況
	 *			(A.JOKYO_ID = '06' AND A.SAISHINSEI_FLG IN ('0','2'))
	 *				--　受理,再申請フラグ（初期値、再申請済み）
	 *			OR (A.JOKYO_ID = '08')	--　審査員割り振り処理後
	 *			OR (A.JOKYO_ID = '09')	--　割り振りチェック完了
	 *			OR (A.JOKYO_ID = '10')	--　1次審査中
	 *			OR (A.JOKYO_ID = '11')	--　1次審査完了
	 *			OR (A.JOKYO_ID = '12')	--　2次審査完了
	 *		)
	 *	
	 *	ORDER BY B.SHINSAIN_NO
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<b><span style="color:#002288">動的検索条件</span></b><br />
	 *	引数のsearchInfoの値によって検索条件が動的に変化する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr style="color:white;font-weight: bold"><td>変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請番号</td><td>UketukeNo</td><td>AND B.UKETUKE_NO = '申請番号'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>jigyoCdValueList</td><td>AND SUBSTR(B.JIGYO_ID, 3, 5) IN ('事業コード1','事業コード2'…)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>担当事業区分（複数）</td><td>TantoJigyoKubun</td><td>AND B.JIGYO_KUBUN IN ('担当事業区分1','担当事業区分2'…)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>年度</td><td>Nendo</td><td>AND A.NENDO = '年度'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>回数</td><td>Kaisu</td><td>AND A.KAISU = '回数'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（漢字等-姓）</td><td>NameKanjiSei</td><td>AND A.NAME_KANJI_SEI LIKE '%申請者氏名（漢字等-姓）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（漢字等-名）</td><td>NameKanjiMei</td><td>AND A.NAME_KANJI_MEI LIKE '%申請者氏名（漢字等-名）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（フリガナ-姓）</td><td>NameKanaSei</td><td>AND A.NAME_KANA_SEI LIKE '%申請者氏名（フリガナ-姓）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（フリガナ-名）</td><td>NameKanaMei</td><td>AND A.NAME_KANA_MEI LIKE '%申請者氏名（フリガナ-名）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（ローマ字-姓）</td><td>NameRoSei</td><td>AND UPPER(A.NAME_RO_SEI) LIKE '%申請者氏名（ローマ字-姓）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（ローマ字-名）</td><td>NameRoMei</td><td>AND UPPER(A.NAME_RO_MEI) LIKE '%申請者氏名（ローマ字-名）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>ShozokuCd</td><td>AND A.SHOZOKU_CD = '所属機関コード'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>細目番号</td><td>BunkaSaimokuCd</td><td>AND A.BUNKASAIMOKU_CD = '細目番号'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>系等の区分</td><td>KeiName</td><td>AND A.KEI_NAME LIKE '%系等の区分%'</td></tr>
	 *	</table>
	 *	<br/><br/>
	 * <b>2.システム受付番号取得</b><br/>
	 *	以下のSQLを実行し、審査員が割振られている申請データのシステム番号を取得する。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *	
	 *	SELECT DISTINCT 
	 *		B.SYSTEM_NO			--　システム受付番号
	 *	FROM 
	 *		SHINSEIDATAKANRI A,
	 *		SHINSAKEKKA B
	 *	WHERE
	 *		A.SYSTEM_NO = B.SYSTEM_NO
	 *		AND A.DEL_FLG = 0
	 *		AND B.SHINSAIN_NAME_KANJI_SEI IS NOT NULL
	 *	
	 *			－－動的検索条件－－
	 *	
	 *		AND (			--　ステータス状況
	 *			(A.JOKYO_ID = '06' AND A.SAISHINSEI_FLG IN ('0','2'))
	 *				--　受理,再申請フラグ（初期値、再申請済み）
	 *			OR (A.JOKYO_ID = '08')	--　審査員割り振り処理後
	 *			OR (A.JOKYO_ID = '09')	--　割り振りチェック完了
	 *			OR (A.JOKYO_ID = '10')	--　1次審査中
	 *			OR (A.JOKYO_ID = '11')	--　1次審査完了
	 *			OR (A.JOKYO_ID = '12')	--　2次審査完了
	 *		)
	 *	
	 *	ORDER BY B.SHINSAIN_NO
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<b><span style="color:#002288">動的検索条件</span></b><br />
	 *	引数のsearchInfoの値によって検索条件が動的に変化する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr style="color:white;font-weight: bold"><td>変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請番号</td><td>UketukeNo</td><td>AND B.UKETUKE_NO = '申請番号'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業コード</td><td>jigyoCdValueList</td><td>AND SUBSTR(B.JIGYO_ID, 3, 5) IN ('事業コード1','事業コード2'…)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>担当事業区分（複数）</td><td>TantoJigyoKubun</td><td>AND B.JIGYO_KUBUN IN ('担当事業区分1','担当事業区分2'…)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>年度</td><td>Nendo</td><td>AND A.NENDO = '年度'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>回数</td><td>Kaisu</td><td>AND A.KAISU = '回数'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（漢字等-姓）</td><td>NameKanjiSei</td><td>AND A.NAME_KANJI_SEI LIKE '%申請者氏名（漢字等-姓）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（漢字等-名）</td><td>NameKanjiMei</td><td>AND A.NAME_KANJI_MEI LIKE '%申請者氏名（漢字等-名）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（フリガナ-姓）</td><td>NameKanaSei</td><td>AND A.NAME_KANA_SEI LIKE '%申請者氏名（フリガナ-姓）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（フリガナ-名）</td><td>NameKanaMei</td><td>AND A.NAME_KANA_MEI LIKE '%申請者氏名（フリガナ-名）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（ローマ字-姓）</td><td>NameRoSei</td><td>AND UPPER(A.NAME_RO_SEI) LIKE '%申請者氏名（ローマ字-姓）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>申請者氏名（ローマ字-名）</td><td>NameRoMei</td><td>AND UPPER(A.NAME_RO_MEI) LIKE '%申請者氏名（ローマ字-名）%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>ShozokuCd</td><td>AND A.SHOZOKU_CD = '所属機関コード'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>細目番号</td><td>BunkaSaimokuCd</td><td>AND A.BUNKASAIMOKU_CD = '細目番号'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>系等の区分</td><td>KeiName</td><td>AND A.KEI_NAME LIKE '%系等の区分%'</td></tr>
	 *	</table>
	 *	<br/><br />
	 * <b>3.CSV出力</b><br/>
	 *	検索結果をCSVへ吐き出す。<br />
	 *	出力されるCSVには、ヘッダをつける。ただし、ヘッダは、ResultSetMetaDataから取得する。→SQLの列名のAliasがヘッダとなる。<br/>
	 *	CSVファイルのパス付きファイル名は以下の通り。<br/>
	 *	&nbsp;&nbsp;IRAI_WORK_FOLDERyyyyMMddHHmmss/SHINSAIRAI.csv<br/>
	 *	&nbsp;&nbsp;&nbsp;&nbsp;※IRAI_WORK_FOLDERはApplicationSettings.propertiesにて定義される。<br/>
	 *	<br/><br />
	 * <b>4.依頼書ファイルのコピー</b><br/>
	 *	依頼書ファイルをコピーする。<br />
	 *	コピー元と、コピー先は以下の通り
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr><td style="color:white;font-weight: bold" rowspan="2">コピー元ファイル</td><td bgcolor="#FFFFFF"><b>IRAI_FORMAT_PATH</b>/<b>IRAI_FORMAT_FILE_NAME</b></td></tr>
	 *		<tr><td bgcolor="#FFFFFF"><b>IRAI_FORMAT_PATH</b>/$</td></tr>
	 *		<tr><td style="color:white;font-weight: bold">コピー先</td><td bgcolor="#FFFFFF"><b>IRAI_WORK_FOLDER</b>yyyyMMddHHmmss/</td></tr>
	 *	</table>
	 *	<br/><br />
	 * <b>5.依頼書圧縮</b><br/>
	 *	依頼書ファイルの圧縮を行う。<br/>
	 *	圧縮はLha32によって行い、通常の圧縮形式のファイルを出力後、自己解凍形式のファイルを出力する。<br/>
	 *	実行コマンドは以下の通り。<br/>
	 *	&nbsp;&nbsp;&nbsp;Lha32 u -a1 -n1 -o2 -jyo1 "IRAI_WORK_FOLDERyyyyMMddHHmmss/SHINSAIRAI_yyyyMMdd.lzh" "" "IRAI_WORK_FOLDERyyyyMMddHHmmss/*"<br/>
	 *	&nbsp;&nbsp;&nbsp;Lha32 s -gw2 -n1 -jyo1 "IRAI_WORK_FOLDERyyyyMMddHHmmss/SHINSAIRAI_yyyyMMdd" "IRAI_WORK_FOLDERyyyyMMddHHmmss/"<br/>
	 *	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;→SHINSAIRAI_yyyyMMdd.exeというファイルが作成される<br/>
	 *	<br/><br />
	 * <b>6.ステータス更新処理</b><br/>
	 *	2.で取得したシステム受付番号に対応するレコードのステータスを更新する。<br/>
	 *	更新の際には、以下のSQLを実行する。<br/>
	 * 	<br/>
	 * 	①排他制御
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *	
	 *	SELECT
	 *		*
	 *	FROM
	 *		SHINSEIDATAKANRI
	 *	WHERE
	 *		SYSTEM_NO IN (システム受付番号1,システム受付番号2…)
	 *	FOR UPDATE
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 * 	②更新処理
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *	
	 *	UPDATE SHINSEIDATAKANRI
	 *	SET
	 *		JOKYO_ID = '10'			--  1次審査中
	 *	WHERE
	 *		SYSTEM_NO IN (システム受付番号1,システム受付番号2…)
	 *		AND	JOKYO_ID = '06' 		--  学振受理
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<br/><br />
	 * <b>7.FileResource返却</b><br/>
	 * 	5.で作成した圧縮ファイルを読込み、FileResourceクラスにセットして返却する。
	 * 
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.WarifuriSearchInfo)
	 */
	public FileResource createIraisho(UserInfo userInfo, WarifuriSearchInfo searchInfo)
		throws ApplicationException {

		//一覧に表示できる申請データの組み合わせステータス検索条件を作成
		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
		String[] saishinseiArray = new String[]{
                StatusCode.SAISHINSEI_FLG_DEFAULT,
                StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI
        };     //再申請フラグ（初期値、再申請済み）
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);     //「受理」:06
		statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);    //「審査員割り振り処理後」:08
		statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);        //「割り振りチェック完了」:09
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);                //「1次審査中」:10
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);            //「1次審査完了」:11
		statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);            //「2次審査完了」:12
			
		//-----------------------
		// 検索条件よりCSVデータ取得SQL文の作成
		//-----------------------
		//2004/12/9update 審査員名、所属機関名、部局名、職名を審査結果テーブル→審査員マスタから取得するように変更。
//		String select = "SELECT DISTINCT"
//							  + " B.SHINSAIN_NO \"審査員番号\","							//審査員番号
//							  + " C.NAME_KANJI_SEI \"審査員名（漢字-姓）\","					//審査員名（漢字-姓）
//							  + " C.NAME_KANJI_MEI \"審査員名（漢字-名）\","					//審査員名（漢字-名）
//							  + " C.SHOZOKU_NAME \"審査員所属機関名\","						//審査員所属機関名
//							  + " C.BUKYOKU_NAME \"審査員部局名\","							//審査員部局名
//							  + " C.SHOKUSHU_NAME \"審査員職名\","							//審査員職名
//							  + " C.SOFU_ZIP \"送付先（郵便番号）\","						//送付先（郵便番号）
//							  + " C.SOFU_ZIPADDRESS \"送付先（住所）\","						//送付先（住所）
//							  + " C.SOFU_ZIPEMAIL \"送付先（Email）\","						//送付先（Email）
//							  + " C.SHOZOKU_TEL \"電話番号\","								//電話番号
//							  + " C.SHOZOKU_FAX \"FAX番号\","								//FAX番号
//							  + " C.BIKO \"備考\","											//備考
//							  + " D.SHINSAIN_ID \"審査員ID\","								//審査員ID
//							  + " D.PASSWORD \"パスワード\","								//パスワード
//							  + " TO_CHAR(D.YUKO_DATE, 'YYYY/MM/DD') \"有効期限\""			//有効期限
//							  + " FROM SHINSEIDATAKANRI A,"
//							  + " SHINSAKEKKA B,"
//							  + " MASTER_SHINSAIN C,"
//							  + " SHINSAININFO D "
// 							  + " WHERE"
//							  + " A.SYSTEM_NO = B.SYSTEM_NO"							//システム番号
//							  + " AND B.SHINSAIN_NO = C.SHINSAIN_NO"					//審査員番号
//							  + " AND B.SHINSAIN_NO = SUBSTR(D.SHINSAIN_ID,4,7)"		//審査員番号(7桁)
//							  +	" AND A.DEL_FLG = 0"
//							//2005/04/28 追加 ここから------------------------------------------
//							//基盤で状況IDが06(学振受理)はCSVに出力しないように条件の追加
//							  + " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
//							  + " AND A.JIGYO_KUBUN = C.JIGYO_KUBUN"					//事業区分
//							//追加 ここまで-----------------------------------------------------
//							;
		//2005/10/27 事業ごとの件数を表示するように変更		
		
		//2005.01.06 iso 整理番号対応
		//単純に整理番号を追加すると整理番号の異なる申請書が事業ごとにグループ化されないので大幅変更
//		String select = "SELECT"
//		              + " B.SHINSAIN_NO SHINSAIN_NO, "										//審査員番号
//		              + " C.NAME_KANJI_SEI NAME_KANJI_SEI,"									//審査員名（漢字-姓）
//		              + " C.NAME_KANJI_MEI NAME_KANJI_MEI,"									//審査員名（漢字-名）
//		              + " C.SHOZOKU_NAME SHOZOKU_NAME,"										//審査員所属機関名
//		              + " C.BUKYOKU_NAME BUKYOKU_NAME,"										//審査員部局名
//		              + " C.SHOKUSHU_NAME SHOKUSHU_NAME,"									//審査員職名
//		              + " C.SOFU_ZIP SOFU_ZIP,"												//送付先（郵便番号）
//		              + " C.SOFU_ZIPADDRESS SOFU_ZIPADDRESS,"								//送付先（住所）
//		              + " C.SOFU_ZIPEMAIL SOFU_ZIPEMAIL,"									//送付先（Email）
//					  + " C.SHOZOKU_TEL SHOZOKU_TEL,"										//電話番号
//					  + " C.SHOZOKU_FAX SHOZOKU_FAX,"										//FAX番号
//		              + " C.BIKO BIKO,"														//備考
//		              + " D.SHINSAIN_ID SHINSAIN_ID,"										//審査員ID
//		              + " D.PASSWORD PASSWORD,"												//パスワード
//		              + " TO_CHAR(D.YUKO_DATE, 'YYYY/MM/DD') YUKO_DATE,"					//有効期限
//					  + " A.NENDO NENDO, "													//事業年度
//					  + " A.KAISU KAISU, "													//事業回数
//					  + " A.JIGYO_NAME JIGYO_NAME, "										//事業名
//					  + " COUNT(*) COUNT"													//事業数
//		              
//		              
//		              + " FROM SHINSEIDATAKANRI A,"
//		              + " SHINSAKEKKA B,"
//		              + " MASTER_SHINSAIN C,"
//		              + " SHINSAININFO D "
//		              + " WHERE"
//		              + " A.SYSTEM_NO = B.SYSTEM_NO"							//システム番号
//				      + " AND B.SHINSAIN_NO = C.SHINSAIN_NO"					//審査員番号
//					  + " AND B.SHINSAIN_NO = SUBSTR(D.SHINSAIN_ID,4,7)"		//審査員番号(7桁)
//					  +	" AND A.DEL_FLG = 0"
//					//2005/04/28 追加 ここから------------------------------------------
//					//基盤で状況IDが06(学振受理)はCSVに出力しないように条件の追加
//					  + " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
//					  + " AND A.JIGYO_KUBUN = C.JIGYO_KUBUN"					//事業区分
//					//追加 ここまで-----------------------------------------------------
//					  //2005.11.16 iso 審査完了を除くよう変更
//					  + " AND B.SHINSA_JOKYO <> '1'"
//					;
//		//2005.11.16 iso 検索条件は別メソッドへ
//		StringBuffer query = new StringBuffer(select);
//
//		//申請番号
//		if(searchInfo.getUketukeNo() != null && !searchInfo.getUketukeNo().equals("")){
//			query.append(" AND B.UKETUKE_NO = '" + searchInfo.getUketukeNo() + "'");
//		}
//		//事業コード
//		List jigyoCdValueList = searchInfo.getJigyoCdValueList();
//		if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
//			query.append(" AND SUBSTR(B.JIGYO_ID, 3, 5) IN (")
//				 .append(changeIterator2CSV(searchInfo.getJigyoCdValueList().iterator()))
//				 .append(")");
//		}
//		//担当事業区分（複数）
//		if(searchInfo.getTantoJigyoKubun() != null && searchInfo.getTantoJigyoKubun().size() != 0){
//			query.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(changeIterator2CSV(searchInfo.getTantoJigyoKubun().iterator()))
//				 .append(")");
//		}
//		//年度
//		if(searchInfo.getNendo() != null && !searchInfo.getNendo().equals("")){
//			query.append(" AND A.NENDO = '" + EscapeUtil.toSqlString(searchInfo.getNendo()) + "'");
//		}
//		//回数
//		if(searchInfo.getKaisu() != null && !searchInfo.getKaisu().equals("")){
//			query.append(" AND A.KAISU = '" + EscapeUtil.toSqlString(searchInfo.getKaisu()) + "'");
//		}
//		//申請者氏名（漢字等-姓）
//		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	
//			query.append(" AND A.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
//		}
//		//申請者氏名（漢字等-名）
//		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	
//			query.append(" AND A.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
//		}
//		//申請者氏名（フリガナ-姓）
//		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	
//			query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
//		}
//		//申請者氏名（フリガナ-名）
//		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){
//			query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
//		}
//		//申請者氏名（ローマ字-姓）
//		if(searchInfo.getNameRoSei() != null && !searchInfo.getNameRoSei().equals("")){
//			query.append(" AND UPPER(A.NAME_RO_SEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()) + "%'");
//		}
//		//申請者氏名（ローマ字-名）
//		if(searchInfo.getNameRoMei() != null && !searchInfo.getNameRoMei().equals("")){
//			query.append(" AND UPPER(A.NAME_RO_MEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()) + "%'");
//		}
//		//所属機関コード
//		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){
//			query.append(" AND A.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
//		}
//		//細目番号
//		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){
//			query.append(" AND A.BUNKASAIMOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'");
//		}
//		//系等の区分
//		if(searchInfo.getKeiName() != null && !searchInfo.getKeiName().equals("")){
//			query.append(" AND A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%'");
//		}
//		//整理番号			2005/10/26　追加
//		if(searchInfo.getSeiriNo() != null && !searchInfo.getSeiriNo().equals("")){
//		    query.append(" AND A.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//		
//		//組み合わせステータス状況
//		if(statusInfo != null && statusInfo.hasQuery()){
//			query.append(" AND")
//					 .append(statusInfo.getQuery());
//		}
//		StringBuffer query = new StringBuffer(getQueryString(select, userInfo, searchInfo, statusInfo));
//		
////		2005.10.26 事業毎の件数を表示するために追加
//		query.append("GROUP BY ")
//			.append("B.SHINSAIN_NO, C.NAME_KANJI_SEI, C.NAME_KANJI_MEI, ")
//			.append("C.SHOZOKU_NAME, C.BUKYOKU_NAME, C.SHOKUSHU_NAME,")
//			.append("C.SOFU_ZIP, C.SOFU_ZIPADDRESS, C.SOFU_ZIPEMAIL,")
//			.append("C.SHOZOKU_TEL, C.SHOZOKU_FAX,")
//			.append("C.BIKO,")
//			.append("D.SHINSAIN_ID, D.PASSWORD, D.YUKO_DATE,")
//			.append("A.NENDO, A.KAISU, A.JIGYO_NAME")
//			;
//					
//		//ソート順（審査員番号の昇順）
//		query.append(" ORDER BY B.SHINSAIN_NO");

		//2006/4/14修正　By　向
		//若手スタートの事業区分が６であるのに、基盤と同じ審査員を割り振りしている。
		//審査員マスタから審査員情報を取得する時、事業データ管理テーブルの事業区分と
		//審査員マスタの事業区分は直接に結ぶことができないので修正する
		
		//審査員事業区分を設定する
		String jigyoKbn = "";
		if (IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(searchInfo.getJigyoKubun())){
			//学術創成（非公募）
			jigyoKbn = IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO;
		}else{
			//基盤と若手スタート
			jigyoKbn = IJigyoKubun.JIGYO_KUBUN_KIBAN;
		}
		
		String selectSeiri = "SELECT DISTINCT"
						+ " B.SHINSAIN_NO SHINSAIN_NO, "						//審査員番号
						+ " C.NAME_KANJI_SEI NAME_KANJI_SEI,"					//審査員名（漢字-姓）
						+ " C.NAME_KANJI_MEI NAME_KANJI_MEI,"					//審査員名（漢字-名）
						+ " C.SHOZOKU_NAME SHOZOKU_NAME,"						//審査員所属機関名
						+ " C.BUKYOKU_NAME BUKYOKU_NAME,"						//審査員部局名
						+ " C.SHOKUSHU_NAME SHOKUSHU_NAME,"						//審査員職名
						+ " C.SOFU_ZIP SOFU_ZIP,"								//送付先（郵便番号）
						+ " C.SOFU_ZIPADDRESS SOFU_ZIPADDRESS,"					//送付先（住所）
						+ " C.SOFU_ZIPEMAIL SOFU_ZIPEMAIL,"						//送付先（Email）
						+ " C.SHOZOKU_TEL SHOZOKU_TEL,"							//電話番号
						+ " C.SHOZOKU_FAX SHOZOKU_FAX,"							//FAX番号
						+ " C.BIKO BIKO,"										//備考
						+ " D.SHINSAIN_ID SHINSAIN_ID,"							//審査員ID
						+ " D.PASSWORD PASSWORD,"								//パスワード
						+ " TO_CHAR(D.YUKO_DATE, 'YYYY/MM/DD') YUKO_DATE,"		//有効期限
						+ " A.JURI_SEIRI_NO,"									//整理番号（学創）
						+ " A.NENDO NENDO,"										//事業年度
						+ " A.KAISU KAISU,"										//事業回数
						+ " A.JIGYO_NAME JIGYO_NAME,"							//事業名
						+ " A.JIGYO_ID"											//事業ID
						+ " FROM SHINSEIDATAKANRI A,"
						+ "      SHINSAKEKKA B,"
						//2006/4/14 begin
						//+ "      MASTER_SHINSAIN C,"
						+ " (SELECT * FROM MASTER_SHINSAIN WHERE JIGYO_KUBUN = " + jigyoKbn + ") C,"
						//end
						+ "      SHINSAININFO D "
						+ " WHERE"
						+ " A.SYSTEM_NO = B.SYSTEM_NO"							//システム番号
						+ " AND B.SHINSAIN_NO = C.SHINSAIN_NO"					//審査員番号
						+ " AND B.SHINSAIN_NO = SUBSTR(D.SHINSAIN_ID,4,7)"		//審査員番号(7桁)
						+ " AND A.DEL_FLG = 0"
						//2005/04/28 追加 ここから------------------------------------------
						//基盤で状況IDが06(学振受理)はCSVに出力しないように条件の追加
						//+ " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
						+ " AND (A.JIGYO_KUBUN = '1' OR A.JOKYO_ID != '06')"
						//+ " AND A.JIGYO_KUBUN = C.JIGYO_KUBUN"					//事業区分
						
						//追加 ここまで-----------------------------------------------------
						//2005.11.16 iso 審査完了を除くよう変更
						+ " AND B.SHINSA_JOKYO <> '1'"
						;

		String selectCount = "SELECT"
						+ " B.SHINSAIN_NO SHINSAIN_NO, "						//審査員番号
						+ " A.JIGYO_ID JIGYO_ID, "								//事業名
						+ " COUNT(*) COUNT"										//事業数
						+ " FROM SHINSEIDATAKANRI A,"
						+ " SHINSAKEKKA B"
						+ " WHERE"
						+ " A.SYSTEM_NO = B.SYSTEM_NO"							//システム番号
						+	" AND A.DEL_FLG = 0"
						//2005/04/28 追加 ここから------------------------------------------
						//基盤で状況IDが06(学振受理)はCSVに出力しないように条件の追加
						//+ " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
						+ " AND (A.JIGYO_KUBUN = '1' OR A.JOKYO_ID != '06')"
						//追加 ここまで-----------------------------------------------------
						//2005.11.16 iso 審査完了を除くよう変更
						+ " AND B.SHINSA_JOKYO <> '1'"
						;

		StringBuffer querySeiri = new StringBuffer(getQueryString(selectSeiri, userInfo, searchInfo, statusInfo));
		StringBuffer queryCount = new StringBuffer(getQueryString(selectCount, userInfo, searchInfo, statusInfo));
		
//2005.10.26 事業毎の件数を表示するために追加
		queryCount.append("GROUP BY B.SHINSAIN_NO, A.JIGYO_ID");
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT")
			.append(" E.SHINSAIN_NO")
			.append(", E.NAME_KANJI_SEI")
			.append(", E.NAME_KANJI_MEI")
			.append(", E.SHOZOKU_NAME")
			.append(", E.BUKYOKU_NAME")
			.append(", E.SHOKUSHU_NAME")
			.append(", E.SOFU_ZIP")
			.append(", E.SOFU_ZIPADDRESS")
			.append(", E.SOFU_ZIPEMAIL")
			.append(", E.SHOZOKU_TEL")
			.append(", E.SHOZOKU_FAX")
			.append(", E.BIKO")
			.append(", E.SHINSAIN_ID")
			.append(", E.PASSWORD")
			.append(", E.YUKO_DATE")
			.append(", E.NENDO")
			.append(", E.KAISU")
			.append(", E.JIGYO_NAME")
			.append(", F.COUNT")
			.append(", E.JURI_SEIRI_NO")
			.append(" FROM")
			.append(" (" + querySeiri + ") E")
			.append(", (" + queryCount + ") F")
			.append(" WHERE E.JIGYO_ID = F.JIGYO_ID")
			.append(" AND E.SHINSAIN_NO = F.SHINSAIN_NO")
			;
		
		//ソート順（審査員番号の昇順）
		query.append(" ORDER BY E.SHINSAIN_NO");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		//-----------------------
		// ステータス更新システム受付番号取得SQL文の作成
		//-----------------------
		String select2 = "SELECT DISTINCT B.SYSTEM_NO"		//システム受付番号
					   + " FROM SHINSEIDATAKANRI A,"
					   + " SHINSAKEKKA B "
					   + " WHERE "
					   + " A.SYSTEM_NO = B.SYSTEM_NO"
					   + " AND A.DEL_FLG = 0"
					   + " AND B.SHINSAIN_NAME_KANJI_SEI IS NOT NULL "
					//2005/04/28 追加 ここから------------------------------------------
					//基盤で状況IDが06(学振受理)は取得しないように条件の追加
					   //+ " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
					   + " AND (A.JIGYO_KUBUN = '1' OR A.JOKYO_ID != '06')"
					//追加 ここまで-----------------------------------------------------
					   //2005.11.16 iso 審査完了を除くよう変更
					   + " AND B.SHINSA_JOKYO <> '1'"
		;

//		StringBuffer query2 = new StringBuffer(select2);
//
//		//申請番号
//		if(searchInfo.getUketukeNo() != null && !searchInfo.getUketukeNo().equals("")){
//			query2.append(" AND B.UKETUKE_NO = '" + searchInfo.getUketukeNo() + "'");
//		}
//		//事業コード
//		if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
//			query2.append(" AND SUBSTR(B.JIGYO_ID, 3, 5) IN (")
//				 .append(changeIterator2CSV(searchInfo.getJigyoCdValueList().iterator()))
//				 .append(")");
//		}
//		//担当事業区分（複数）
//		if(searchInfo.getTantoJigyoKubun() != null && searchInfo.getTantoJigyoKubun().size() != 0){
//			query2.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(changeIterator2CSV(searchInfo.getTantoJigyoKubun().iterator()))
//				 .append(")");
//		}
//		//年度
//		if(searchInfo.getNendo() != null && !searchInfo.getNendo().equals("")){
//			query2.append(" AND A.NENDO = '" + EscapeUtil.toSqlString(searchInfo.getNendo()) + "'");
//		}
//		//回数
//		if(searchInfo.getKaisu() != null && !searchInfo.getKaisu().equals("")){
//			query2.append(" AND A.KAISU = '" + EscapeUtil.toSqlString(searchInfo.getKaisu()) + "'");
//		}
//		//申請者氏名（漢字等-姓）
//		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	
//			query2.append(" AND A.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
//		}
//		//申請者氏名（漢字等-名）
//		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	
//			query2.append(" AND A.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
//		}
//		//申請者氏名（フリガナ-姓）
//		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	
//			query2.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
//		}
//		////申請者氏名（フリガナ-名）
//		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){
//			query2.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
//		}
//		//申請者氏名（ローマ字-姓）
//		if(searchInfo.getNameRoSei() != null && !searchInfo.getNameRoSei().equals("")){
//			query2.append(" AND UPPER(A.NAME_RO_SEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()) + "%'");
//		}
//		//申請者氏名（ローマ字-名）
//		if(searchInfo.getNameRoMei() != null && !searchInfo.getNameRoMei().equals("")){
//			query2.append(" AND UPPER(A.NAME_RO_MEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()) + "%'");
//		}
//		//所属機関コード
//		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){
//			query2.append(" AND A.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
//		}
//		//細目番号
//		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){
//			query2.append(" AND A.BUNKASAIMOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'");
//		}
//		//系等の区分
//		if(searchInfo.getKeiName() != null && !searchInfo.getKeiName().equals("")){
//			query2.append(" AND A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%'");
//		}
//		//整理番号			2005/10/26　追加
//		if(searchInfo.getSeiriNo() != null && !searchInfo.getSeiriNo().equals("")){
//			query2.append(" AND A.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//		
//		//組み合わせステータス状況
//		if(statusInfo != null && statusInfo.hasQuery()){
//			query2.append(" AND")
//					 .append(statusInfo.getQuery());
//		}
		StringBuffer query2 = new StringBuffer(getQueryString(select2, userInfo, searchInfo, statusInfo));
		
		if(log.isDebugEnabled()){
			log.debug("query2:" + query2);
		}

		//-----------------------
		// CSVデータリスト取得
		//-----------------------
		Connection connection = null;
		List csv_data = new ArrayList();
		List sys_no = new ArrayList();

		try {
			connection = DatabaseUtil.getConnection();
//			csv_data = SelectUtil.selectCsvList(connection,query.toString(), true);
//	事業ごとに件数を表示するよう変更
			csv_data = SelectUtil.selectCsvList(connection,query.toString(), false);					
			sys_no = SelectUtil.select(connection,query2.toString());
		} catch (NoDataFoundException e) {
			List errors = new ArrayList();
			errors.add(new ErrorInfo("errors.5023"));
			throw new ValidationException("審査依頼書を出力できる状態の審査員が登録されていません。", errors);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSVデータ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//-----------------------
		// CSVファイル出力
		//-----------------------
		//2005.10.26	事業ごとに件数を表示するよう変更
		//-----データ構造を変換（重複データを含む単一リストから審査員ごとのマップへ変換する）
		String[] columnArray = {"審査員番号"
								,"審査員名（漢字-姓）"
								,"審査員名（漢字-名）"
								,"審査員所属研究機関名"
								,"審査員部局名"
								,"審査員職名"
								,"送付先（郵便番号）"
								,"送付先（住所）"
								,"送付先（Email）"
								,"電話番号"
								,"FAX番号"
								,"備考"
								,"審査員ID"
								,"パスワード"
								,"有効期限"
								,"事業情報"
								,"整理番号（学創）"						//2005.01.06 iso 追加
								};
								
							
		List newList = new ArrayList(Arrays.asList(columnArray));		//新しく作る審査員ごとのリスト(新CSV1行分)
		ArrayList newCsvList = new ArrayList();							//CSVとして出力するリスト
		String jigyoInfo = "";
		String beforeShinsainNo = "";
		//2005.01.06 iso 整理番号対応
		String beforeJigyo = "";
		String allSeiriNo = "";
			
		for(int i = 0; i < csv_data.size(); i++) {
			//SQL文の順番を変更したら、変わるので注意
			List recordList 			= (List)csv_data.get(i);
			//2005.01.06 iso 制御方法を変更
//			String shinsainNo  			= recordList.get(0).toString();		//審査員番号
////			String shinsainNameKanjiSei	= recordList.get(1).toString();		//審査員名（漢字-姓）
////			String shinsainNameKanjiMei	= recordList.get(2).toString();		//審査員名（漢字-名）
////			String shozokuName			= recordList.get(3).toString();		//審査員所属研究機関名
////			String bukyokuName			= recordList.get(4).toString();		//審査員部局名
////			String shokushuName			= recordList.get(5).toString();		//審査員職名	
////			String sofuZip				= recordList.get(6).toString();		//送付先（郵便番号）	
////			String sofuZipaddress		= recordList.get(7).toString();		//送付先（住所）
////			String sofuZipemail			= recordList.get(8).toString();		//送付先（Email）
////			String shozokuTel			= recordList.get(9).toString();		//電話番号
////			String shozokuFax			= recordList.get(10).toString();	//FAX番号
////			String biko					= recordList.get(11).toString();	//備考
////			String shinsainId  			= recordList.get(12).toString();	//審査員ID
////			String password  			= recordList.get(13).toString();	//パスワード
////			String yukoDate  			= recordList.get(14).toString();	//有効期限
//			String nendo  				= recordList.get(15).toString();	//事業年度
//			String kaisu  				= recordList.get(16).toString();	//事業回数
//			String jigyoName  			= recordList.get(17).toString();	//事業名
//			String count	  			= recordList.get(18).toString();	//事業数
			
			String shinsainNo  			= recordList.get(0).toString();	//審査員番号
            int j = 15;
			String nendo  				= recordList.get(j++).toString();	//事業年度
			String kaisu  				= recordList.get(j++).toString();	//事業回数
			String jigyoName  			= recordList.get(j++).toString();	//事業名
			String count	  			= recordList.get(j++).toString();	//事業数
			//2005.01.06 iso 追加
			String seiriNo				= recordList.get(j++).toString();	//整理番頭（学創）
			
			String kaisuHyoji = "";
			if(!kaisu.equals("1")) {
				kaisuHyoji = "第" + kaisu + "回 ";							//回数が1回以上の場合は表示する
			}
				
			//1個前の審査員番号と一致した場合、同じ依頼書のデータなので事業情報をまとめる(改行が入る)
			if(shinsainNo.equals(beforeShinsainNo)) {
//				jigyoInfo = jigyoInfo + "\r\n平成" + nendo + "年度 " + kaisuHyoji + jigyoName + " " + count + "件";
				//2005.01.06 iso 整理番号追加対応
				if(!beforeJigyo.equals(nendo+kaisu+jigyoName)) {
					//前の事業と一致した場合は、事業情報はすでにjigyoInfoに格納されている。
					//一致しない時のみ事業情報に格納する。
					jigyoInfo = jigyoInfo + "\r\n平成" + nendo + "年度 " + kaisuHyoji + jigyoName + " " + count + "件";
					
					//2006.01.16 iso 同じ事業が件数分表示されるバグを修正
					//1個前の事業を更新
					beforeJigyo = nendo+kaisu+jigyoName;
				}
				if(!StringUtil.isBlank(seiriNo)) {
					allSeiriNo += "　" + seiriNo;
				}
			} else {
				//1個前と違う審査員番号の場合、前の審査員の1行分データを新CSVリストに登録。
				if(jigyoInfo != null && !jigyoInfo.equals("")) {		//最初は空なので(columnArray格納時)事業情報を追加しない
					newList.add(jigyoInfo);		//審査員(1個前)ごとにまとめた事業情報
					//2005.01.06 iso 整理番号追加対応
					newList.add(allSeiriNo);	//審査員(1個前)ごとにまとめた整理番号
				}
				newCsvList.add(newList);		//出力CSVに審査員ごとにまとめたデータをセット
					
				//1個前の審査員毎の情報を出力リストにセットしたら、現審査員情報で、データを初期化する
				newList = new ArrayList(recordList.subList(0, 15));	//newListを現審査員情報で初期化
				jigyoInfo = "平成" + nendo + "年度 "				//事業情報を現情報で初期化
							+ kaisuHyoji + jigyoName + " " + count + "件";
				//2005.01.06 iso 整理番号追加対応
				allSeiriNo = seiriNo;								//整理番号（学創）を現情報で初期化
				
				//現在の審査員番号を次の比較に使うためにセット
				beforeShinsainNo = recordList.get(0).toString();

				//2006.01.16 iso 同じ事業が件数分表示されるバグを修正
				//1個前の事業を現在の事業で初期化
				beforeJigyo = nendo+kaisu+jigyoName;
			}
			//最後のデータは上のelseに引っかからないので、ここでcsvListに格納する。
			if(i == csv_data.size()-1) {
				newList.add(jigyoInfo);								//審査員(1個前)ごとにまとめた事業情報
				//2005.01.06 iso 整理番号追加対応
				newList.add(allSeiriNo);							//審査員(1個前)ごとにまとめた整理番号
				newCsvList.add(newList);							//出力CSVに審査員ごとにまとめたデータをセット
			}	
		}
		//ファイル出力パスを指定
		//2005/09/09 takano フォルダ名をミリ秒単位に変更。念のため同時に同期処理も組み込み。
		String filepath = null;
		synchronized(log){
			filepath = IRAI_WORK_FOLDER + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
		}
//		CsvUtil.output(csv_data, filepath, CSV_FILENAME);
		CsvUtil.output(newCsvList, filepath, CSV_FILENAME);

		//-----------------------
		// 依頼書ファイルのコピー
		//-----------------------
		//TODO 所定の位置にフォーマットファイルが無かった場合、エラーにするべきか？
		FileUtil.fileCopy(new File(IRAI_FORMAT_PATH + IRAI_FORMAT_FILE_NAME), new File(filepath + IRAI_FORMAT_FILE_NAME));
		FileUtil.fileCopy(new File(IRAI_FORMAT_PATH + "$"), new File(filepath + "$"));

		//-----------------------
		// ファイルの圧縮
		//-----------------------
		//圧縮ファイル名
		String comp_file_name = CSV_FILENAME + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
		//圧縮処理
		FileUtil.fileCompress(filepath, filepath, comp_file_name);

		//-------------------------------------
		// 処理状況判定フラグ
		//-------------------------------------
		boolean success = false;
		
		//-------------------------------------
		//作成したファイルを読み込む。
		//-------------------------------------
		//ファイルを取得する。
		File exe_file = new File(filepath + comp_file_name + ".EXE");
		FileResource iodFileResource = null;

		try {	
			iodFileResource = FileUtil.readFile(exe_file);
			success = true;
		} catch (IOException e) {
			throw new ApplicationException(
				"作成ファイル'" + comp_file_name + ".EXE'情報の取得に失敗しました。",
				new ErrorInfo("errors.8005"),
				e);
		}finally{
			if (success) {
				//-------------------------------------
				//作業ファイルの削除
				//-------------------------------------
				//TODO ★佐藤★ →テスト用作成IODファイルを削除しない。
				FileUtil.delete(exe_file.getParentFile());
			}
		}

		//-------------------------
		// ステータス更新処理
		//-------------------------
		int cnt_sn = sys_no.size();
		ShinseiDataPk[] shinseiPk = new ShinseiDataPk[cnt_sn];
		for(int i=0; i<cnt_sn; i++){
			HashMap value = (HashMap)sys_no.get(i);

			shinseiPk[i] = new ShinseiDataPk((String)value.get("SYSTEM_NO"));
		}
		
		IShinseiMaintenance shinseiMainte = new ShinseiMaintenance();
		shinseiMainte.updateStatusForShinsaIraiIssue(userInfo, shinseiPk);

		//IODファイルのリターン
		return iodFileResource;
	}
	
	/**
	 * 事業区分を取得する。
	 */
	public String selectJigyoKubun(UserInfo userInfo, String jigyoCd)
		throws ApplicationException {
			
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			return MasterJigyoInfoDao.selectJigyoKubun(connection, jigyoCd);
			
		} catch (NoDataFoundException e) {
			throw e;
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * 引数で与えられたIteratorをカンマ区切りのStringに変換して返却.<br />
	 * <br />
	 * 引数のIteratorの要素をStringでキャスティングして、すべての要素をカンマ区切りで連結する。
	 * ただし、すべての要素をダブルクォーテーションで囲う。もし、引数のItaratorがnullの場合は、例外をthrowする。
	 * また、要素が空の場合は""を返却する。
	 * @param ite	Iterator
	 * @return	iteを要素をカンマ区切りで連結したstring
	 */
	private static String changeIterator2CSV(Iterator ite){
		return StringUtil.changeIterator2CSV(ite, true);
	}	

	/**
	 * 割り振り検索条件オブジェクトから検索条件を取得しSQLの問い合わせ部分を生成する。
	 * 生成した問い合わせ部分は第一引数の文字列の後ろに結合される。
	 * @param select    
	 * @param userInfo  
	 * @param searchInfo
	 * @return String SQLの問い合わせ部分
	 */
	protected static String getQueryString(
										String select,
										UserInfo userInfo,
										WarifuriSearchInfo searchInfo,
										CombinedStatusSearchInfo statusInfo) {

		//-----検索条件オブジェクトの内容をSQLに結合していく-----
		StringBuffer query = new StringBuffer(select);

		//申請番号
		if(!StringUtil.isBlank(searchInfo.getUketukeNo())){
			query.append(" AND B.UKETUKE_NO = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()));
            query.append("'");
		}
		//事業コード
		List jigyoCdValueList = searchInfo.getJigyoCdValueList();
		if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
			query.append(" AND SUBSTR(B.JIGYO_ID, 3, 5) IN (");
            query.append(changeIterator2CSV(searchInfo.getJigyoCdValueList().iterator()));
            query.append(")");
		}
		//担当事業区分（複数）
		if(searchInfo.getTantoJigyoKubun() != null && searchInfo.getTantoJigyoKubun().size() != 0){
			query.append(" AND B.JIGYO_KUBUN IN (");
            query.append(changeIterator2CSV(searchInfo.getTantoJigyoKubun().iterator()));
            query.append(")");
		}
		//年度
		if(!StringUtil.isBlank(searchInfo.getNendo())){
			query.append(" AND A.NENDO = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getNendo()));
            query.append("'");
		}
		//回数
		if(!StringUtil.isBlank(searchInfo.getKaisu())){
			query.append(" AND A.KAISU = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getKaisu()));
            query.append("'");
		}
		//申請者氏名（漢字等-姓）
		if(!StringUtil.isBlank(searchInfo.getNameKanjiSei())){	
			query.append(" AND A.NAME_KANJI_SEI LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()));
            query.append("%'");
		}
		//申請者氏名（漢字等-名）
		if(!StringUtil.isBlank(searchInfo.getNameKanjiMei())){	
			query.append(" AND A.NAME_KANJI_MEI LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()));
            query.append("%'");
		}
		//申請者氏名（フリガナ-姓）
		if(!StringUtil.isBlank(searchInfo.getNameKanaSei())){	
			query.append(" AND A.NAME_KANA_SEI LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()));
            query.append("%'");
		}
		//申請者氏名（フリガナ-名）
		if(!StringUtil.isBlank(searchInfo.getNameKanaMei())){
			query.append(" AND A.NAME_KANA_MEI LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()));
            query.append("%'");
		}
		//申請者氏名（ローマ字-姓）
		if(!StringUtil.isBlank(searchInfo.getNameRoSei())){
			query.append(" AND UPPER(A.NAME_RO_SEI) LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()));
            query.append("%'");
		}
		//申請者氏名（ローマ字-名）
		if(!StringUtil.isBlank(searchInfo.getNameRoMei())){
			query.append(" AND UPPER(A.NAME_RO_MEI) LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()));
            query.append("%'");
		}
		//所属機関コード
		if(!StringUtil.isBlank(searchInfo.getShozokuCd())){
			query.append(" AND A.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()));
            query.append("'");
		}
		//細目番号
		if(!StringUtil.isBlank(searchInfo.getBunkaSaimokuCd())){
			query.append(" AND A.BUNKASAIMOKU_CD = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()));
            query.append("'");
		}
		//系等の区分
		if(!StringUtil.isBlank(searchInfo.getKeiName())){
			query.append(" AND A.KEI_NAME LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getKeiName()));
            query.append("%'");
		}
// 2006/07/03 dyh add start 原因：割り振り検索画面で審査員所属研究機関名条件を追加
        //審査員所属研究機関名
        if(!StringUtil.isBlank(searchInfo.getShozokuName())){
//2006/10/5 利害関係があれば画面表示は崩れるため、事業単位の全審査件数を取得とする
//            query.append(" AND B.SHOZOKU_NAME LIKE '%");
//            query.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()));
//            query.append("%'");
			query.append(" AND B.SYSTEM_NO IN (SELECT DISTINCT SYSTEM_NO FROM SHINSAKEKKA");
			query.append(	" WHERE SHOZOKU_NAME LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()));
			query.append("%')");
        }
// 2006/07/03 dyh add end
		//整理番号 2005/10/17　追加
		if(!StringUtil.isBlank(searchInfo.getSeiriNo())){
			query.append(" AND A.JURI_SEIRI_NO LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()));
            query.append("%'");
		}
		//利害関係
		if(searchInfo.getRigai() != null && "1".equals(searchInfo.getRigai())){
			query.append(" AND B.SYSTEM_NO IN (SELECT DISTINCT SYSTEM_NO FROM SHINSAKEKKA");
			//2007.01.15 iso 検索条件を「審査完了」→「入力完了」へ変更
//			query.append(					" WHERE RIGAI = '1' AND SHINSA_JOKYO = '1')");
			query.append(					" WHERE RIGAI = '1' AND NYURYOKU_JOKYO = '1')");
		}
		
		//割振り
		if(searchInfo.getWarifuriFlg() != null && "1".equals(searchInfo.getWarifuriFlg())){
			query.append(" AND B.SYSTEM_NO IN (SELECT SYSTEM_NO");
			query.append("  FROM (");
			query.append("    SELECT SYSTEM_NO,");
//			2007.01.15 iso 検索条件を「審査完了」→「入力完了」へ変更
//			query.append("           SUM(CASE WHEN RIGAI='1' AND SHINSA_JOKYO = '1' THEN 1 ELSE 0 END) RIGAI_CNT,");
			query.append("           SUM(CASE WHEN RIGAI='1' AND NYURYOKU_JOKYO = '1' THEN 1 ELSE 0 END) RIGAI_CNT,");
			query.append("           SUM(DECODE(DAIRI,'1',1,0)) DAIRI_CNT");
			query.append("      FROM SHINSAKEKKA");
			query.append("     GROUP BY SYSTEM_NO ) ");
			query.append(" WHERE RIGAI_CNT > DAIRI_CNT");
//			query.append(" WHERE A.RIGAI_CNT > 0");
//			query.append("   AND A.DAIRI_CNT = 0");
			query.append(")");
		}
		
		//組み合わせステータス状況
		if(statusInfo != null && statusInfo.hasQuery()){
			query.append(" AND")
				 .append(statusInfo.getQuery());
		}

		return query.toString();
	}
}