/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（JSPS)
 *    Source name : PunchDataMaintenance.java
 *    Description : パンチデータ管理
 *
 *    Author      : Yuji Kainuma
 *    Date        : 2004/11/01
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/11/01    V1.0                       新規作成
 *    2005/05/31    V1.1        向　　　　　　　特定領域パンチデータ出力追加
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.go.jsps.kaken.model.IPunchDataMaintenance;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.PunchKanriInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.PunchDataKanriInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;

/**
 * パンチデータ作成管理クラス. <br />
 * <br />
 * 
 * パンチデータの作成、保管などを行う。 <br />
 * <br />
 * 
 * 使用しているテーブル <br />
 * <br />
 * ・パンチデータ管理テーブル:PUNCHKANRI <br />
 * 出力するパンチデータ情報を管理する。 <br />
 * <br />
 * ・申請データ管理テーブル:SHINSEIDATAKANRI <br />
 * 申請データの情報を管理する。 <br />
 * <br />
 * ・研究組織表管理テーブル:KENKYUSOSHIKIKANRI <br />
 * 申請データの研究組織表情報を管理する。 <br />
 * <br />
 * ・審査結果テーブル:SHINSAKEKKA <br />
 * 審査員割り振り結果情報と申請書の審査結果を管理する。 <br />
 * <br />
 */
public class PunchDataMaintenance implements IPunchDataMaintenance {

	//	---------------------------------------------------------------------
	//	 Static data
	//	---------------------------------------------------------------------

	/**
	 * パンチデータ保管先. <br />
	 * <br />
	 * 具体的な値は、" <b>${shinsei_path}/punchdata/{0} </b>"
	 */
	private final static String PUNCHDATA_HOKAN_LOCATION =
				ApplicationSettings.getString(ISettingKeys.PUNCHDATA_HOKAN_LOCATION);

	/**
	 * パンチデータ情報の取得. <br />
	 * <br />
	 * 
	 * パンチデータ情報の一覧をListで取得する。発行するSQL文は、以下の通り。 <br />
	 * <br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  
	 *  SELECT
	 *  	A.PUNCH_SHUBETU				--パンチデータ種別
	 *  	,A.PUNCH_NAME				--パンチデータ名称
	 *  	,A.JIGYO_KUBUN				--事業区分
	 *  	,TO_CHAR
	 *  		(A.SAKUSEI_DATE,'YYYY/MM/DD HH24:MI:SS')
	 *  		SAKUSEI_DATE			--作成日時
	 *  	,A.PUNCH_PATH				--パンチデータファイルパス
	 *  FROM
	 *  	PUNCHKANRI A
	 *  WHERE
	 *  	A.JIGYO_KUBUN IN (&quot;&lt;b&gt;担当事業区分&lt;/b&gt;&quot;) (※)
	 *  ORDER BY
	 *  	A.PUNCH_SHUBETU
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> <br />
	 * 
	 * (※)業務担当者の場合は自分が担当する事業区分のみなので、この条件文が入る。 " <b>担当事業区分
	 * </b>"には、引数userInfoが持つGyomutantoInfoの変数TantoJigyoKubunの値が","をはさんですべて並ぶ。
	 * <br />
	 * <br />
	 * 
	 * 取得した値は、列名をキーにしたMapに格納される。そのMapがListに格納され、返却される。
	 * 
	 * @param userInfo UserInfo
	 * @return パンチデータ情報の一覧を持つList
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IPunchDataMaintenance#selectList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public List selectList(UserInfo userInfo) throws ApplicationException
	{

		//PunchKanriDataInfoDaoをnewする
		PunchKanriInfoDao punchdatainfodao = new PunchKanriInfoDao(userInfo);

		//DBコネクションを取得する
		Connection connection = null;

		//リストオブジェクトを作成する
		List result = null;

		try {
			connection = DatabaseUtil.getConnection();
			//Listオブジェクトを取得する
			result = punchdatainfodao.selectList(connection, userInfo);
			//Listオブジェクトをreturnする
			return result;
		} finally {
			//DBコネクションを閉じる
			DatabaseUtil.closeConnection(connection);
		}

	}

	/**
	 * パンチ管理情報の取得. <br />
	 * <br />
	 * 
	 * パンチ管理情報の取得を行う。発行するSQL文は以下の通り。(バインド変数は、SQLの下の表を参照)
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  SELECT
	 *  	A.PUNCH_SHUBETU			--パンチデータ種別
	 *  	,A.PUNCH_NAME			--パンチデータ名称
	 *  	,A.JIGYO_KUBUN			--事業区分
	 *  	,A.SAKUSEI_DATE			--作成日時
	 *  	,A.PUNCH_PATH			--パンチデータファイルパス
	 *  FROM
	 *  	PUNCHKANRI A
	 *  WHERE
	 *  	PUNCH_SHUBETU = ?
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> <br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
	 * width="600">
	 * <tr style="color:white;font-weight: bold">
	 * <td>列名</td>
	 * <td>値</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>データ種別</td>
	 * <td>第二引数punchShubetuを使用する。</td>
	 * </tr>
	 * </table> <br />
	 * 
	 * 取得したデータ内の" <b>PunchPath </b>"の値から、パンチデータファイルを取得する。
	 * そのファイルを読み込んで、FileResourceを返却する。
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param punchShubetu 作成するパンチデータの種類
	 * @return FileResource
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IPunchDataMaintenance#getPunchDataResource(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public FileResource getPunchDataResource(
				UserInfo userInfo,
				String punchShubetu) throws ApplicationException 
	{

		//PunchKanriInfoDaoをnewする
		PunchKanriInfoDao punchkanriinfodao = new PunchKanriInfoDao(userInfo);

		//ユーザが選択したPunchDataKanriInfoを取る
		PunchDataKanriInfo punchdatakanriInfo = null;

		//DBコネクションを取得する
		Connection connection = null;

		try {
			connection = DatabaseUtil.getConnection();
			punchdatakanriInfo = punchkanriinfodao.selectPunchKanriInfo(connection, punchShubetu);

		} catch (ApplicationException e) {
			throw new ApplicationException("パンチデータ検索中ににDBエラーが発生しました。",
					new ErrorInfo("errors.4004"), e);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ApplicationException("パンチデータ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"), e);
		} finally {
			//DBコネクションを閉じる
			DatabaseUtil.closeConnection(connection);
		}

		//PunchDataKanriInfoからPUNCH_PATHを取得する
		String path = punchdatakanriInfo.getPunchPath();

		//GetしたPUNCH_PATHにあるパンチデータを取得する
		File file = new File(path);

		FileResource fileResource = null;
		try {
			fileResource = FileUtil.readFile(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw new ApplicationException("指定したファイルが見つかりません。path=" + path,
					new ErrorInfo("errors.7004"), e1);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new ApplicationException("出入力エラー。", new ErrorInfo(
					"errors.appliaction"), e1);
		}

		//取得したパンチデータファイルをPunchDataOutActionクラスに返す
		return fileResource;
	}

	/**
	 * パンチデータの新規作成. <br />
	 * <br />
	 * 
	 * 第二引数punchShubetuによって、作成するファイルの種類が変わる。呼び出すメソッドは、すべて自メソッド。 <br />
	 * <br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
	 * width="600">
	 * <tr style="color:white;font-weight: bold">
	 * <td>punchShubetu</td>
	 * <td>作成するファイル</td>
	 * <td>呼び出すメソッド</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>1</td>
	 * <td>学術創成研究費応募情報パンチデータファイル</td>
	 * <td>getPunchDataGakuso(userInfo)</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>2</td>
	 * <td>特別推進研究応募情報パンチデータファイル</td>
	 * <td>getPunchDataTokusui(userInfo)</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>3</td>
	 * <td>基盤研究等応募情報パンチデータファイル</td>
	 * <td>getPunchDataKibanKenkyu(userInfo)</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>4</td>
	 * <td>特定領域研究(継続領域)応募情報パンチデータファイル</td>
	 * <td>getPunchDataTokutei(userInfo)</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>5</td>
	 * <td>基盤研究等評定表パンチデータファイル</td>
	 * <td>getPunchDataKibanHyotei(userInfo)</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>その他</td>
	 * <td>nullを返す。</td>
	 * <td>なし</td>
	 * </tr>
	 * </table> <br />
	 * 
	 * ファイルを作成後、作成したパンチデータ情報を持つPunchDataKanriInfoを返却する。
	 * 
	 * なお、参照先のメソッド内で発行されるSQL文中の、 " <b>AND TO_NUMBER(JOKYO_ID) IN
	 * (6,8,9,10,11,12) </b>"の、各状況については、 <br />
	 * <br />
	 * ・6…学振受理 <br />
	 * ・8…審査員割り振り処理後 <br />
	 * ・9…割り振りチェック完了 <br />
	 * ・10…１次審査中 <br />
	 * ・11…１次審査：判定 <br />
	 * ・12…２次審査完了 <br />
	 * <br />
	 * 
	 * という状況のものである。
	 * 
	 * @param userInfo UserInfo
	 * @param punchShubetu 作成するパンチデータの種類
	 * @return 作成したパンチデータ情報を持つPunchDataKanriInfo
	 * @throws ApplicationException
	 */

	public PunchDataKanriInfo getPunchData(UserInfo userInfo,
			String punchShubetu) throws ApplicationException {

		//種別によって呼び出す処理を振り分ける
		if (punchShubetu.equals("1")) {
			//学術創成研究費応募情報パンチデータ
			return getPunchDataGakuso(userInfo);
		}
		else if (punchShubetu.equals("2")) {
			//特別推進研究応募情報パンチデータ
			return getPunchDataTokusui(userInfo);
		}
		else if (punchShubetu.equals("3")) {
			//基盤研究等応募情報パンチデータ
			return getPunchDataKibanKenkyu(userInfo);
		}
		else if (punchShubetu.equals("4")) {
			//特定領域研究(継続領域)応募カードパンチデータ
			return getPunchDataTokuteiKenzokuChosho(userInfo);
		}
		else if (punchShubetu.equals("5")) {
			//基盤研究等評定表パンチデータ
			return getPunchDataKibanHyotei(userInfo);
		}
//2006/04/11 追加ここから		
		else if (punchShubetu.equals("6")) {
			//若手研究（スタートアップ）応募情報パンチデータ
			return getPunchDataWakastartKenkyu(userInfo);
		}
		else if (punchShubetu.equals("7")) {
			//特別研究促進費応募情報パンチデータ
			return getPunchDataSokushinKenkyu(userInfo);
		}
//苗　追加ここまで
        
//2006/05/25 追加ここから
        else if (punchShubetu.equals("8")){
            //若手研究（スタートアップ）評定表パンチデータ
            return getPunchDataWakateHyotei(userInfo);
        }
        else if (punchShubetu.equals("9")){
            //特別研究促進費評定表パンチデータ
            return getPunchDataSokushinHyotei(userInfo);
        }
//苗　追加ここまで 
        
//2006/06/20 苗　追加ここから
        else if (punchShubetu.equals("10")){
            //特定領域研究(新規領域)応募情報パンチデータ
            return getPunchDataTokuteiSinnkiChosho(userInfo);
        }
        else if (punchShubetu.equals("11")){
            //特定領域研究（新規領域）領域計画書概要パンチデータ
        	//Modify By Sai 2006.09.19 Start
            //return getPunchDataTokuteiSinnkiRyoiki(userInfo);
            return searchCsvData(userInfo);
//          Modify By Sai 2006.09.19 End
        }
//2006/06/20　苗　追加ここまで        
		else {
			return null;
		}
	}

	/**
	 * 特別推進課題名カードパンチデータファイルを作成. <br />
	 * <br />
	 * 
	 * 以下のSQL文を発行して、取得した値をすべて連結して1つの文字列を作成する。 <br />
	 * <br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  SELECT
	 *  	A.SHOZOKU_CD                   		--所属機関コード(5バイト)
	 *  	,NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER
	 *  		--整理番号(4バイト)申請番号のハイフン以下
	 *  	,RPAD(NVL(A.KADAI_NAME_KANJI,' '),162,'　') KADAINAMEKANJI
	 *  		--研究課題名(和文)(162バイト)
	 *  FROM
	 *  	SHINSEIDATAKANRI A
	 *  WHERE
	 *  	DEL_FLG = 0
	 *  	AND JIGYO_KUBUN = IJigyoKubun.JIGYO_KUBUN_TOKUSUI(※)
	 *  	AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)
	 *  ORDER BY
	 *  	A.JIGYO_ID, A.UKETUKE_NO		--事業ID,申請番号(機関整理番号)
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> (※)" <b>IJigyoKubun.JIGYO_KUBUN_TOKUSUI </b>"の値は3。 <br />
	 * <br />
	 * 
	 * 作成した文字列をバイトの配列にして格納したFileResourceを作成し、 自メソッドの" <b>kakuno(FileResource,
	 * String "TOKUSUI-KADAI") </b>"にて、サーバに格納する。 <br />
	 * <br />
	 * 
	 * パンチデータ管理infoにパンチデータ情報を格納し、その情報を元にパンチデータ管理テーブルの更新を行う。
	 * 作成するSQL文は以下の通り。(バインド変数は、SQLの下の表を参照) <br />
	 * <br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  
	 *  UPDATE
	 *  		PUNCHKANRI
	 *  SET
	 *  		PUNCH_SHUBETU = ?
	 *  		,PUNCH_NAME = ?
	 *  		,JIGYO_KUBUN = ?
	 *  		,SAKUSEI_DATE = SYSDATE
	 *  		,PUNCH_PATH = ?
	 *  WHERE
	 *  		PUNCH_SHUBETU = ?
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> <br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
	 * width="600">
	 * <tr style="color:white;font-weight: bold">
	 * <td>列名</td>
	 * <td>値</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>データ種別</td>
	 * <td>2</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>データ名称</td>
	 * <td>"特別推進課題名パンチデータ"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>事業区分</td>
	 * <td>3</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>パンチデータファイルパス</td>
	 * <td>格納したパンチデータファイルの絶対パス</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>データ種別</td>
	 * <td>2</td>
	 * </tr>
	 * </table> <br />
	 * 
	 * 特別推進研究パンチデータ管理infoを返却する。
	 * 
	 * @param userInfo UserInfo
	 * @return 処理結果情報を持つPunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataTokusui(UserInfo userInfo)
			throws ApplicationException
	{

		//DBコネクションを取得する
		Connection connection = null;

		boolean success = false;

		//Stringオブジェクトを作成する
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
			String tableName = "TOKUSUI-OUBO";

			//パンチデータ作成に必要なDAOをnewする
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//Listを結合したStringを取得する
			punchResult = punchKanriInfodao.punchDataTokushi(connection);

			//FileResourceを作成
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//格納メソッドを呼ぶ
			String punchPath = kakuno(punchFileResource, tableName);

			//パンチデータ管理infoをnewする
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//パンチデータ管理テーブルを更新する
			punchDataKanriinfo.setPunchShubetu("2");
			punchDataKanriinfo.setPunchName("特別推進研究応募情報パンチデータ");
			punchDataKanriinfo.setJigyoKubun("3");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB更新メソッドを呼びだす
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//結果ををreturnする
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} finally {
			try {
				if (success) {
					//コミット
					DatabaseUtil.commit(connection);
				} else {
					//ロールバック
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("データベース更新中にエラーが発生しました。",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 学術創成研究費応募情報パンチデータファイルを作成. <br />
	 * <br />
	 * 
	 * 以下のSQL文を発行して、取得した値をすべて連結して1つの文字列を作成する。 <br />
	 * <br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  SELECT
	 *  	NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER
	 *  			--整理番号(4バイト)申請番号のハイフン以下
	 *  	,A.SHOZOKU_CD SHOZOKU_CD_DAIHYO		--所属機関コード(5バイト)
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1
	 * 			--1年目研究経費(7バイト)
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2
	 * 			--2年目研究経費(7バイト)
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3
	 * 			--3年目研究経費(7バイト)
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4
	 * 			--4年目研究経費(7バイト)
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5
	 * 			--5年目研究経費(7バイト)
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN ' ' ELSE A.KEI_NAME_NO END KEINAMENO
	 * 			--系等の区分番号（1バイト）
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '    ' ELSE A.BUNKASAIMOKU_CD END BUNKASAIMOKUCD
	 * 			--細目コード（4バイト）
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN
	 * 		'　　　　　　　　　　　　　　　　　　　　　　　　　　　　　'
	 * 		ELSE RPAD(A.KADAI_NAME_KANJI,80,'　') END KADAINAMEKANJI
	 * 			--研究課題名（和文）（80バイト）
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,' ') END KENKYUNINZU
	 * 			--研究者数（2バイト）
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN ' ' ELSE LPAD(A.BUNTANKIN_FLG,1,' ') END BUNTANKINFLG
	 * 			--分担金の有無
	 *  	,NVL(B.BUNTAN_FLG,' ') BUNTANFLG	--代表者分担者別(1バイト)
	 *  	,LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD
	 *  			--所属機関名(コード)研究組織表管理テーブルより(5バイト)
	 *  	,LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD	--部局名(コード)(3バイト)
	 *  	,LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD--職名コード(2バイト)
	 *  	,LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO	--研究者番号(8バイト)
	 *  	,B.NAME_KANA_SEI NAMEKANASEI   	--氏名（姓）フリガナ(32バイト、16文字）
	 *  	,B.NAME_KANA_MEI NAMEKANAMEI	--氏名フリガナ(コンマを含め16文字）
	 *  FROM
	 *  	SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B
	 *  WHERE
	 *  	DEL_FLG = 0&quot;
	 *  	AND A.SYSTEM_NO = B.SYSTEM_NO
	 *  	AND JIGYO_KUBUN = IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO(※)
	 *  	AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)
	 *  ORDER BY
	 *  	A.JIGYO_ID, A.UKETUKE_NO, B.SEQ_NO
	 *  			--事業ID,申請番号(機関整理番号),研究組織表連番
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> (※)" <b>IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO </b>"の値は1。
	 * <br />
	 * <br />
	 * 
	 * 作成した文字列をバイトの配列にして格納したFileResourceを作成し、 自メソッドの" <b>kakuno(FileResource,
	 * String "GAKUSO-SUISEN-SOSHIKI") </b>"にて、サーバに格納する。 <br />
	 * <br />
	 * 
	 * パンチデータ管理infoにパンチデータ情報を格納し、その情報を元にパンチデータ管理テーブルの更新を行う。
	 * 作成するSQL文は以下の通り。(バインド変数は、SQLの下の表を参照) <br />
	 * <br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  
	 *  UPDATE
	 *  		PUNCHKANRI
	 *  SET
	 *  		PUNCH_SHUBETU = ?
	 *  		,PUNCH_NAME = ?
	 *  		,JIGYO_KUBUN = ?
	 *  		,SAKUSEI_DATE = SYSDATE
	 *  		,PUNCH_PATH = ?
	 *  WHERE
	 *  		PUNCH_SHUBETU = ?
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> <br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
	 * width="600">
	 * <tr style="color:white;font-weight: bold">
	 * <td>列名</td>
	 * <td>値</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>データ種別</td>
	 * <td>4</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>データ名称</td>
	 * <td>"学術創成(推薦分)組織表パンチデータ"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>事業区分</td>
	 * <td>1</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>パンチデータファイルパス</td>
	 * <td>格納したパンチデータファイルの絶対パス</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>データ種別</td>
	 * <td>4</td>
	 * </tr>
	 * </table> <br />
	 * 
	 * パンチデータ管理infoを返却する。
	 * 
	 * @param userInfo UserInfo
	 * @return 処理結果情報を持つPunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataGakuso(UserInfo userInfo)
			throws ApplicationException
	{

		//DBコネクションを取得する
		Connection connection = null;

		boolean success = false;

		//Stringオブジェクトを作成する
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
			String tableName = "GAKUSO-OUBO";

			//パンチデータ作成に必要なDAOをnewする
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//Listを結合したStringを取得する
			punchResult = punchKanriInfodao.punchDataGakuso(connection);

			//FileResourceを作成
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//格納メソッドを呼ぶ
			String punchPath = kakuno(punchFileResource, tableName);

			//パンチデータ管理infoをnewする
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//パンチデータ管理テーブルを更新する
			punchDataKanriinfo.setPunchShubetu("1");
			punchDataKanriinfo.setPunchName("学術創成研究費応募情報パンチデータ");
			punchDataKanriinfo.setJigyoKubun("1");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB更新メソッドを呼びだす
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//結果ををreturnする
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} finally {
			try {
				if (success) {
					//コミット
					DatabaseUtil.commit(connection);
				} else {
					//ロールバック
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("データベース更新中にエラーが発生しました。",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 評定表パンチデータファイルを作成. <br />
	 * <br />
	 * 
	 * 以下のSQL文を発行して、取得した値をすべて連結して1つの文字列を作成する。 <br />
	 * <br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  SELECT
	 *  	NVL(SUBSTR(B.UKETUKE_NO,1,5),'    ') SHOZOKUCDDAIHYO
	 *  			--所属機関コード(5バイト)
	 *  
	 *  	,NVL(SUBSTR(B.UKETUKE_NO,7,4),'    ') BUNKASAIMOKUCD	--整理番号(4バイト)
	 *  	,NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO
	 *  			--審査員枝番(審査員番号の下２桁)(2バイト)
	 *  
	 *  	,NVL(B.KEKKA_TEN, ' ') KEKKATEN			--総合評価
	 *  	,NVL(B.KENKYUNAIYO, ' ') KENKYUNAIYO			--研究内容
	 *  	,NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU		--研究計画
	 *  	,CASE WHEN B.TEKISETSU_KENKYU1 = '0'
	 *  		THEN ' ' ELSE NVL(B.TEKISETSU_KENKYU1, ' ')
	 *  		END TEKISETSUKENKYU1			--分担金評価
	 *  	,CASE WHEN B.TEKISETSU_KAIGAI = '0'
	 *  		THEN ' ' ELSE NVL(B.TEKISETSU_KAIGAI,' ')
	 *  		END TEKISETSUKAIGAI			--適切性海外
	 *  	,CASE WHEN B.DATO = '0'
	 *  		THEN ' ' ELSE NVL(B.DATO, ' ') END DATO	--経費の妥当性
	 *  	,CASE WHEN B.SHINSEISHA = '1'
	 *  		THEN '1' ELSE ' ' END DAIHYOSHAKUBUN		--代表区分
	 *  	,CASE WHEN B.SHINSEISHA = '2'
	 *  		THEN '1' ELSE ' '  END BUNTANSHAKUBUN	--分担者区分
	 *  	,CASE WHEN B.SHINSEISHA = '3'
	 *  		THEN '1' ELSE ' '  END KANKEISHAKUBUN	--関係者区分
	 *  	,CASE WHEN B.COMMENTS IS NULL
	 *  		THEN '　' ELSE TO_MULTI_BYTE(B.COMMENTS)
	 *  		END COMMENTS				--コメント
	 *  	,CASE WHEN B.SHINSA_JOKYO IS NULL
	 *  		THEN ' ' ELSE B.SHINSA_JOKYO
	 *  		END SHINSAJOKYO				--審査状況	
	 *  FROM
	 *  	SHINSEIDATAKANRI A, SHINSAKEKKA B
	 *  WHERE
	 *  	A.DEL_FLG = 0
	 *  	AND A.JIGYO_KUBUN = B.JIGYO_KUBUN
	 *  	AND A.SYSTEM_NO = B.SYSTEM_NO
	 *  	AND A.JIGYO_KUBUN = IJigyoKubun.JIGYO_KUBUN_KIBAN
	 *  	AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)
	 *  	AND NOT B.SHINSAIN_NO LIKE '@%'
	 *  ORDER BY
	 *  	SHOZOKUCDDAIHYO,BUNKASAIMOKUCD, SHINSAINNO
	 *  			--所属機関コード、整理番号、審査員枝番
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> (※)" <b>IJigyoKubun.JIGYO_KUBUN_KIBAN </b>"の値は4。 <br />
	 * <br />
	 * 
	 * 作成した文字列をバイトの配列にして格納したFileResourceを作成し、 自メソッドの" <b>kakuno(FileResource,
	 * String "HYOTEI") </b>"にて、サーバに格納する。 <br />
	 * <br />
	 * 
	 * パンチデータ管理infoにパンチデータ情報を格納し、その情報を元にパンチデータ管理テーブルの更新を行う。
	 * 作成するSQL文は以下の通り。(バインド変数は、SQLの下の表を参照) <br />
	 * <br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  
	 *  UPDATE
	 *  		PUNCHKANRI
	 *  SET
	 *  		PUNCH_SHUBETU = ?
	 *  		,PUNCH_NAME = ?
	 *  		,JIGYO_KUBUN = ?
	 *  		,SAKUSEI_DATE = SYSDATE
	 *  		,PUNCH_PATH = ?
	 *  WHERE
	 *  		PUNCH_SHUBETU = ?
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> <br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
	 * width="600">
	 * <tr style="color:white;font-weight: bold">
	 * <td>列名</td>
	 * <td>値</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>データ種別</td>
	 * <td>6</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>データ名称</td>
	 * <td>"評定表パンチデータ"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>事業区分</td>
	 * <td>4</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>パンチデータファイルパス</td>
	 * <td>格納したパンチデータファイルの絶対パス</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>データ種別</td>
	 * <td>6</td>
	 * </tr>
	 * </table> <br />
	 * 
	 * 基盤研究等評定表パンチデータ管理infoを返却する。
	 * 
	 * @param userInfo  UserInfo
	 * @return 処理結果情報を持つPunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataKibanHyotei(UserInfo userInfo)
			throws ApplicationException 
	{

		//DBコネクションを取得する
		Connection connection = null;

		boolean success = false;

		//Stringオブジェクトを作成する
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
			String tableName = "HYOTEI";

			//パンチデータ作成に必要なDAOをnewする
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//Listを結合したStringを取得する
			punchResult = punchKanriInfodao.punchDataHyotei(connection);

			//FileResourceを作成
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//格納メソッドを呼ぶ
			String punchPath = kakuno(punchFileResource, tableName);

			//パンチデータ管理infoをnewする
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//パンチデータ管理テーブルを更新する
			punchDataKanriinfo.setPunchShubetu("5");
			punchDataKanriinfo.setPunchName("基盤研究等評定表パンチデータ");
			punchDataKanriinfo.setJigyoKubun("4");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB更新メソッドを呼びだす
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//結果ををreturnする
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} finally {
			try {
				if (success) {
					//コミット
					DatabaseUtil.commit(connection);
				} else {
					//ロールバック
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("データベース更新中にエラーが発生しました。",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * ファイルを日付を名前にしてサーバに保存する. <br />
	 * <br />
	 * 
	 * 出力先のフォルダは、" <b>D:/shinsei-kaken/punchdata/第二引数のテーブル名 </b>" <br />
	 * パンチデータのデータ名は、" <b>第二引数のテーブル名_本日の日付.txt </b>" <br />
	 * 日付は、フォーマットが"yyyyMMdd"で、WASより取得。 <br />
	 * <br />
	 * 第二引数のテーブル名については、以下の表を参照。 <table border="0" cellspacing="1"
	 * cellpadding="1" bgcolor="#000000">
	 * <tr style="color:white;font-weight: bold">
	 * <td>punchShubetu</td>
	 * <td>参照先のメソッド</td>
	 * <td>与えられるテーブル名</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>1</td>
	 * <td>getPunchDataTokusuiShinsei(userInfo)</td>
	 * <td>"TOKUSUI-CARD"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>2</td>
	 * <td>getPunchDataTokusuiKadai(userInfo)</td>
	 * <td>"TOKUSUI-KADAI"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>3</td>
	 * <td>getPunchDataTokusuiBuntansya(userInfo)</td>
	 * <td>"TOKUSUI-BUNTANSYA"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>4</td>
	 * <td>getPunchDataGakusoSuisen(userInfo)</td>
	 * <td>"GAKUSO-SUISEN-SOSHIKI"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>5</td>
	 * <td>getPunchDataGakusoBoshu(userInfo)</td>
	 * <td>"GAKUSO-BOSYU-SOSHIKI"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>6</td>
	 * <td>getPunchDataHyotei(userInfo)</td>
	 * <td>"HYOTEI"</td>
	 * </tr>
	 * </table> <br />
	 * パンチデータファイルをサーバに格納し、このファイルの絶対パスのStringを返却する。
	 * 
	 * @param fileRes アップロードファイルリソース。
	 * @param tableName 保存先のフォルダ名
	 * @return 格納したパンチデータファイルの絶対パスのString
	 * @throws ApplicationException パンチデータファイルファイル格納に失敗した場合
	 */
	private String kakuno(FileResource fileRes, String tableName)
			throws ApplicationException 
	{
		//出力先フォルダ
		String path = MessageFormat.format(PUNCHDATA_HOKAN_LOCATION, new String[] { tableName });
		File parent = new File(path);

		//パンチデータの命名規則を規定
		String name = new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".txt";
		name = tableName + "_" + name;
		fileRes.setPath(name);

		try {
			//パンチデータファイルをサーバに格納するメソッドを呼び出す
			FileUtil.writeFile(parent, fileRes);
		} catch (IOException e) {
			throw new ApplicationException("パンチデータファイルの格納中に失敗しました。",
					new ErrorInfo("errors.7001"), e);
		}
		return new File(parent, name).getAbsolutePath();
	}
	/*
	 * @param fileRes アップロードファイルリソース。
	 * @param tableName(Csv) 保存先のフォルダ名
	 * @return 格納したパンチデータファイルの絶対パスのString
	 * @throws ApplicationException パンチデータファイルファイル格納に失敗した場合
	 */
	private String kakunoCsv(FileResource fileRes, String tableName)
			throws ApplicationException 
	{
		//出力先フォルダ
		String path = MessageFormat.format(PUNCHDATA_HOKAN_LOCATION, new String[] { tableName });
		File parent = new File(path);

		//パンチデータの命名規則を規定
		String name = new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".csv";
		name = tableName + "_" + name;
		fileRes.setPath(name);

		try {
			//パンチデータファイルをサーバに格納するメソッドを呼び出す
			FileUtil.writeFile(parent, fileRes);
		} catch (IOException e) {
			throw new ApplicationException("パンチデータファイルの格納中に失敗しました。",
					new ErrorInfo("errors.7001"), e);
		}
		return new File(parent, name).getAbsolutePath();
	}	

	/**
	 * 基盤研究パンチデータファイルを作成. <br />
	 * <br />
	 * 
	 * @param userInfo UserInfo
	 * @return 処理結果情報を持つPunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataKibanKenkyu(UserInfo userInfo)
			throws ApplicationException
	{

		//DBコネクションを取得する
		Connection connection = null;

		boolean success = false;

		//Stringオブジェクトを作成する
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
			String tableName = "GAKUSIN-OUBO";

			//パンチデータ作成に必要なDAOをnewする
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//Listを結合したStringを取得する
			punchResult = punchKanriInfodao.punchDataKibanKenkyu(connection);

			//FileResourceを作成
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//格納メソッドを呼ぶ
			String punchPath = kakuno(punchFileResource, tableName);

			//パンチデータ管理infoをnewする
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//パンチデータ管理テーブルを更新する
			punchDataKanriinfo.setPunchShubetu("3");
			punchDataKanriinfo.setPunchName("基盤研究等応募情報パンチデータ");
			punchDataKanriinfo.setJigyoKubun("4");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB更新メソッドを呼びだす
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//結果ををreturnする
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} finally {
			try {
				if (success) {
					//コミット
					DatabaseUtil.commit(connection);
				} else {
					//ロールバック
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("データベース更新中にエラーが発生しました。",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		//return null;
	}

	/**
	 * 特定領域研究(継続)応募カードパンチデータファイルを作成. <br />
	 * <br />
	 * 
	 * 
	 * @param userInfo
	 * @return 処理結果情報を持つPunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataTokuteiKenzokuChosho(UserInfo userInfo)
			throws ApplicationException 
	{

		//DBコネクションを取得する
		Connection connection = null;

		boolean success = false;

		//Stringオブジェクトを作成する
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
			String tableName = "TOKUTEI-CARD";

			//パンチデータ作成に必要なDAOをnewする
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//Listを結合したStringを取得する
			punchResult = punchKanriInfodao.punchDataTokuteiKeiZokuChosho(connection);
			

			//FileResourceを作成
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//格納メソッドを呼ぶ
			String punchPath = kakuno(punchFileResource, tableName);

			//パンチデータ管理infoをnewする
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//パンチデータ管理テーブルを更新する
			punchDataKanriinfo.setPunchShubetu("4");
			punchDataKanriinfo.setPunchName("特定領域研究(継続領域)応募カードパンチデータ");
			punchDataKanriinfo.setJigyoKubun("5");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB更新メソッドを呼びだす
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//結果ををreturnする
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} finally {
			try {
				if (success) {
					//コミット
					DatabaseUtil.commit(connection);
				} else {
					//ロールバック
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("データベース更新中にエラーが発生しました。",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}

		//return null;
	}
//2006/04/11 追加ここから
	/**
	 * 若手研究（スタートアップ）パンチデータファイルを作成. <br />
	 * <br />
	 * 
	 * @param userInfo UserInfo
	 * @return 処理結果情報を持つPunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataWakastartKenkyu(UserInfo userInfo)
			throws ApplicationException
	{

		//DBコネクションを取得する
		Connection connection = null;

		boolean success = false;

		//Stringオブジェクトを作成する
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
			String tableName = "WAKASTARTUP-OUBO";

			//パンチデータ作成に必要なDAOをnewする
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//Listを結合したStringを取得する
			punchResult = punchKanriInfodao.punchDataWakastartKenkyu(connection);

			//FileResourceを作成
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//格納メソッドを呼ぶ
			String punchPath = kakuno(punchFileResource, tableName);

			//パンチデータ管理infoをnewする
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//パンチデータ管理テーブルを更新する
			punchDataKanriinfo.setPunchShubetu("6");
			punchDataKanriinfo.setPunchName("若手研究（スタートアップ）応募情報パンチデータ");
			punchDataKanriinfo.setJigyoKubun("6");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB更新メソッドを呼びだす
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//結果ををreturnする
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} finally {
			try {
				if (success) {
					//コミット
					DatabaseUtil.commit(connection);
				} else {
					//ロールバック
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("データベース更新中にエラーが発生しました。",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		//return null;
	}
	
	/**
	 * 特別研究促進費応募情報パンチデータファイルを作成. <br />
	 * <br />
	 * 
	 * @param userInfo UserInfo
	 * @return 処理結果情報を持つPunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataSokushinKenkyu(UserInfo userInfo)
			throws ApplicationException
	{

		//DBコネクションを取得する
		Connection connection = null;

		boolean success = false;

		//Stringオブジェクトを作成する
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
			String tableName = "SOKUSHIN-OUBO";

			//パンチデータ作成に必要なDAOをnewする
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//Listを結合したStringを取得する
			punchResult = punchKanriInfodao.punchDataSokushinKenkyu(connection);

			//FileResourceを作成
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//格納メソッドを呼ぶ
			String punchPath = kakuno(punchFileResource, tableName);

			//パンチデータ管理infoをnewする
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//パンチデータ管理テーブルを更新する
			punchDataKanriinfo.setPunchShubetu("7");
			punchDataKanriinfo.setPunchName("特別研究促進費応募情報パンチデータ");
			punchDataKanriinfo.setJigyoKubun("7");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB更新メソッドを呼びだす
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//結果ををreturnする
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
					new ErrorInfo("errors.4000"), e);
		} finally {
			try {
				if (success) {
					//コミット
					DatabaseUtil.commit(connection);
				} else {
					//ロールバック
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("データベース更新中にエラーが発生しました。",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		//return null;
	}

//苗　追加ここまで	
    
//2006/05/25 追加ここから
    /**
     * 若手研究（スタートアップ）評定表パンチデータファイルを作成. <br />
     * <br />
     * 
     * 以下のSQL文を発行して、取得した値をすべて連結して1つの文字列を作成する。 <br />
     * <br />
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
     * width="600">
     * <tr bgcolor="#FFFFFF">
     * <td>
     * 
     * <pre>
     * 
     *    SELECT 
     *         '14' KENKYUSHUMOKU       研究種目番号（2バイト)  
     *         ,'1' SHINSA_KUBUN        審査区分(1バイト)  
     *         ,NVL(SUBSTR(B.UKETUKE_NO,1,5),'     ') SHOZOKUCDDAIHYO"    所属機関コード(5バイト)
     *         ,NVL(A.BUNKASAIMOKU_CD,'    ') BUNKASAIMOKUCD"             細目コード（4バイト）
     *         ,CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
     *               WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"    分割番号（1バイト）
     *         ,' ' BUNKATSUNO_12"                                                    分割番号（1バイト）
     *         ,NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER"                     整理番号(4バイト)申請番号のハイフン以下
     *         ,NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO"                      審査員枝番(審査員番号の下２桁）（2バイト）
     *         ,CASE WHEN B.RIGAI = '1' THEN '1' ELSE ' '  END RIGAIKANKEI"           利害関係
     *         ,NVL(B.JUYOSEI, ' ') JUYOSEI"                                          研究課題の学術的重要性
     *         ,NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU"                              研究計画・方法の妥当性
     *         ,NVL(B.DOKUSOSEI, ' ') DOKUSOSEI"                   独創性及び革新性
     *         ,NVL(B.HAKYUKOKA, ' ') HAKYUKOKA"                   波及効果
     *         ,NVL(B.SUIKONORYOKU, ' ') SUIKONORYOKU"             遂行能力
     *         ,CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '111') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_HOGA"           適切性・萌芽
     *         ,CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '043')OR(SUBSTR(A.JIGYO_ID,5,3) = '053') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_KAIGAI"  適切性・萌芽
     *         ,CASE WHEN B.KEKKA_TEN = '-' THEN ' ' ELSE NVL(B.KEKKA_TEN, ' ') END KEKKATEN"   総合評価
     *         ,CASE WHEN B.JINKEN = '3' THEN '3' ELSE ' ' END JINKEN"            人権
     *         ,CASE WHEN B.BUNTANKIN = '3' THEN '3' ELSE ' ' END BUNTANKIN"      分担金
     *         ,CASE WHEN B.DATO = '0' THEN ' ' ELSE NVL(B.DATO, ' ') END DATO"   経費の妥当性
     *         ,CASE WHEN B.COMMENTS IS NULL THEN '　' ELSE TO_MULTI_BYTE(B.COMMENTS) END COMMENTS"    審査意見
     *         ,CASE WHEN B.OTHER_COMMENT IS NULL THEN '　' ELSE TO_MULTI_BYTE(B.OTHER_COMMENT) END OTHER_COMMENT"   コメント
     *         ,NVL(B.SHINSA_JOKYO, '0') SHINSAJOKYO"     審査状況
     *         FROM SHINSEIDATAKANRI A, SHINSAKEKKA B"
     *         WHERE B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_WAKATESTART
     *               AND NOT B.SHINSAIN_NO LIKE '@%'"     ダミー審査員を除外する(ダミー審査員番号が＠付いてる)
     *               AND A.SYSTEM_NO = B.SYSTEM_NO" 
     *               AND A.JIGYO_KUBUN = B.JIGYO_KUBUN "
     *               AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART +  ")"
     *               AND A.DEL_FLG = 0"
     *               AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
     *         ORDER BY KENKYUSHUMOKU, SHINSA_KUBUN, SHOZOKUCDDAIHYO, BUNKASAIMOKUCD, A.BUNKATSU_NO, SHINSAINNO, SEIRINUMBER";   所属機関コード、,整理番号、審査員枝番
     * </pre>
     * 
     * </td>
     * </tr>
     * </table> (※)" <b>IJigyoKubun.JIGYO_KUBUN_WAKATESTART </b>"の値は6。 <br />
     * <br />
     * 
     * 作成した文字列をバイトの配列にして格納したFileResourceを作成し、 自メソッドの" <b>kakuno(FileResource,
     * String "HYOTEI") </b>"にて、サーバに格納する。 <br />
     * <br />
     * 
     * パンチデータ管理infoにパンチデータ情報を格納し、その情報を元にパンチデータ管理テーブルの更新を行う。
     * 作成するSQL文は以下の通り。(バインド変数は、SQLの下の表を参照) <br />
     * <br />
     * 
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
     * width="600">
     * <tr bgcolor="#FFFFFF">
     * <td>
     * 
     * <pre>
     * 
     *  
     *  UPDATE
     *          PUNCHKANRI
     *  SET
     *          PUNCH_SHUBETU = ?
     *          ,PUNCH_NAME = ?
     *          ,JIGYO_KUBUN = ?
     *          ,SAKUSEI_DATE = SYSDATE
     *          ,PUNCH_PATH = ?
     *  WHERE
     *          PUNCH_SHUBETU = ?
     * </pre>
     * 
     * </td>
     * </tr>
     * </table> <br />
     * 
     * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
     * width="600">
     * <tr style="color:white;font-weight: bold">
     * <td>列名</td>
     * <td>値</td>
     * <tr/>
     * <tr bgcolor="#FFFFFF">
     * <td>データ種別</td>
     * <td>6</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>データ名称</td>
     * <td>"評定表パンチデータ"</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>事業区分</td>
     * <td>4</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>パンチデータファイルパス</td>
     * <td>格納したパンチデータファイルの絶対パス</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>データ種別</td>
     * <td>6</td>
     * </tr>
     * </table> <br />
     * 
     * 若手研究（スタートアップ）評定表パンチデータ管理infoを返却する。
     * 
     * @param userInfo  UserInfo
     * @return 処理結果情報を持つPunchDataKanriInfo
     * @throws ApplicationException
     */
    private PunchDataKanriInfo getPunchDataWakateHyotei(UserInfo userInfo)
            throws ApplicationException 
    {

        //DBコネクションを取得する
        Connection connection = null;

        boolean success = false;

        //Stringオブジェクトを作成する
        String punchResult = null;

        try {
            connection = DatabaseUtil.getConnection();

            //パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
            String tableName = "WAKASTARTUP-HYOTEI";

            //パンチデータ作成に必要なDAOをnewする
            PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

            //Listを結合したStringを取得する
            punchResult = punchKanriInfodao.punchDataWakateHyotei(connection);

            //FileResourceを作成
            FileResource punchFileResource = new FileResource();
            punchFileResource.setBinary(punchResult.getBytes());

            //格納メソッドを呼ぶ
            String punchPath = kakuno(punchFileResource, tableName);

            //パンチデータ管理infoをnewする
            PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

            //パンチデータ管理テーブルを更新する
            punchDataKanriinfo.setPunchShubetu("8");
            punchDataKanriinfo.setPunchName("若手研究（スタートアップ）評定表パンチデータ");
            punchDataKanriinfo.setJigyoKubun("6");
            punchDataKanriinfo.setSakuseiDate(new Date());
            punchDataKanriinfo.setPunchPath(punchPath);

            //DB更新メソッドを呼びだす
            punchKanriInfodao.update(connection, punchDataKanriinfo);

            success = true;

            //結果ををreturnする
            return punchDataKanriinfo;

        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4010"), e);
        } catch (ApplicationException e) {
            throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4000"), e);
        } catch (DataAccessException e) {
            throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4000"), e);
        } finally {
            try {
                if (success) {
                    //コミット
                    DatabaseUtil.commit(connection);
                } else {
                    //ロールバック
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ApplicationException("データベース更新中にエラーが発生しました。",
                        new ErrorInfo("errors.4000"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
    
    /**
     * 特別研究促進費評定表パンチデータファイルを作成. <br />
     * <br />
     * 
     * 以下のSQL文を発行して、取得した値をすべて連結して1つの文字列を作成する。 <br />
     * <br />
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
     * width="600">
     * <tr bgcolor="#FFFFFF">
     * <td>
     * 
     * <pre>
     * 
     * 
     *    SELECT 
     *          　　　　'15' KENKYUSHUMOKU"    //研究種目番号（2バイト)  
     *         ,CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00152' THEN '2'"
     *               WHEN SUBSTR(A.JIGYO_ID,3,5) = '00153' THEN '3' " 
     *               WHEN SUBSTR(A.JIGYO_ID,3,5) = '00154' THEN '4' " 
     *               WHEN SUBSTR(A.JIGYO_ID,3,5) = '00155' THEN '5' " 
     *               WHEN SUBSTR(A.JIGYO_ID,3,5) = '00156' THEN '6' " 
     *               ELSE ' ' END SHINSA_KUBUN"  //審査区分（1バイト）
     *         ,NVL(SUBSTR(B.UKETUKE_NO,1,5),'     ') SHOZOKUCDDAIHYO"    所属機関コード(5バイト)
     *         ,NVL(A.BUNKASAIMOKU_CD,'    ') BUNKASAIMOKUCD"             細目コード（4バイト）
     *         ,CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
     *               WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"    分割番号（1バイト）
     *         ,CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
     *               WHEN A.BUNKATSU_NO = '2' THEN '2' ELSE ' ' END BUNKATSUNO_12"  //分割番号（1バイト）
     *         ,NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER"                     整理番号(4バイト)申請番号のハイフン以下
     *         ,NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO"                      審査員枝番(審査員番号の下２桁）（2バイト）
     *         ,CASE WHEN B.RIGAI = '1' THEN '1' ELSE ' '  END RIGAIKANKEI"           利害関係
     *         ,NVL(B.JUYOSEI, ' ') JUYOSEI"                                          研究課題の学術的重要性
     *         ,NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU"                              研究計画・方法の妥当性
     *         ,NVL(B.DOKUSOSEI, ' ') DOKUSOSEI"                   独創性及び革新性
     *         ,NVL(B.HAKYUKOKA, ' ') HAKYUKOKA"                   波及効果
     *         ,NVL(B.SUIKONORYOKU, ' ') SUIKONORYOKU"             遂行能力
     *         ,CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '111') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_HOGA"           適切性・萌芽
     *         ,CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '043')OR(SUBSTR(A.JIGYO_ID,5,3) = '053') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_KAIGAI"  適切性・萌芽
     *         ,CASE WHEN B.KEKKA_TEN = '-' THEN ' ' ELSE NVL(B.KEKKA_TEN, ' ') END KEKKATEN"   総合評価
     *         ,CASE WHEN B.JINKEN = '3' THEN '3' ELSE ' ' END JINKEN"            人権
     *         ,CASE WHEN B.BUNTANKIN = '3' THEN '3' ELSE ' ' END BUNTANKIN"      分担金
     *         ,CASE WHEN B.DATO = '0' THEN ' ' ELSE NVL(B.DATO, ' ') END DATO"   経費の妥当性
     *         ,CASE WHEN B.COMMENTS IS NULL THEN '　' ELSE TO_MULTI_BYTE(B.COMMENTS) END COMMENTS"    審査意見
     *         ,CASE WHEN B.OTHER_COMMENT IS NULL THEN '　' ELSE TO_MULTI_BYTE(B.OTHER_COMMENT) END OTHER_COMMENT"   コメント
     *         ,NVL(B.SHINSA_JOKYO, '0') SHINSAJOKYO"     審査状況
     *         FROM SHINSEIDATAKANRI A, SHINSAKEKKA B"
     *         WHERE B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_WAKATESTART
     *               AND NOT B.SHINSAIN_NO LIKE '@%'"     ダミー審査員を除外する(ダミー審査員番号が＠付いてる)
     *               AND A.SYSTEM_NO = B.SYSTEM_NO" 
     *               AND A.JIGYO_KUBUN = B.JIGYO_KUBUN "
     *               AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART +  ")"
     *               AND A.DEL_FLG = 0"
     *               AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
     *         ORDER BY KENKYUSHUMOKU, SHINSA_KUBUN, SHOZOKUCDDAIHYO, BUNKASAIMOKUCD, A.BUNKATSU_NO, SHINSAINNO, SEIRINUMBER";   所属機関コード、,整理番号、審査員枝番
     * </pre>
     * 
     * </td>
     * </tr>
     * </table> (※)" <b>IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI </b>"の値は7。 <br />
     * <br />
     * 
     * 作成した文字列をバイトの配列にして格納したFileResourceを作成し、 自メソッドの" <b>kakuno(FileResource,
     * String "HYOTEI") </b>"にて、サーバに格納する。 <br />
     * <br />
     * 
     * パンチデータ管理infoにパンチデータ情報を格納し、その情報を元にパンチデータ管理テーブルの更新を行う。
     * 作成するSQL文は以下の通り。(バインド変数は、SQLの下の表を参照) <br />
     * <br />
     * 
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
     * width="600">
     * <tr bgcolor="#FFFFFF">
     * <td>
     * 
     * <pre>
     * 
     *  
     *  UPDATE
     *          PUNCHKANRI
     *  SET
     *          PUNCH_SHUBETU = ?
     *          ,PUNCH_NAME = ?
     *          ,JIGYO_KUBUN = ?
     *          ,SAKUSEI_DATE = SYSDATE
     *          ,PUNCH_PATH = ?
     *  WHERE
     *          PUNCH_SHUBETU = ?
     * </pre>
     * 
     * </td>
     * </tr>
     * </table> <br />
     * 
     * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
     * width="600">
     * <tr style="color:white;font-weight: bold">
     * <td>列名</td>
     * <td>値</td>
     * <tr/>
     * <tr bgcolor="#FFFFFF">
     * <td>データ種別</td>
     * <td>6</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>データ名称</td>
     * <td>"評定表パンチデータ"</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>事業区分</td>
     * <td>4</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>パンチデータファイルパス</td>
     * <td>格納したパンチデータファイルの絶対パス</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>データ種別</td>
     * <td>6</td>
     * </tr>
     * </table> <br />
     * 
     * 特別研究促進費評定表パンチデータ管理infoを返却する。
     * 
     * @param userInfo  UserInfo
     * @return 処理結果情報を持つPunchDataKanriInfo
     * @throws ApplicationException
     */
    private PunchDataKanriInfo getPunchDataSokushinHyotei(UserInfo userInfo)
            throws ApplicationException 
    {

        //DBコネクションを取得する
        Connection connection = null;

        boolean success = false;

        //Stringオブジェクトを作成する
        String punchResult = null;

        try {
            connection = DatabaseUtil.getConnection();

            //パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
            String tableName = "SOKUSHIN-HYOTEI";

            //パンチデータ作成に必要なDAOをnewする
            PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

            //Listを結合したStringを取得する
            punchResult = punchKanriInfodao.punchDataSokushinHyotei(connection);

            //FileResourceを作成
            FileResource punchFileResource = new FileResource();
            punchFileResource.setBinary(punchResult.getBytes());

            //格納メソッドを呼ぶ
            String punchPath = kakuno(punchFileResource, tableName);

            //パンチデータ管理infoをnewする
            PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

            //パンチデータ管理テーブルを更新する
            punchDataKanriinfo.setPunchShubetu("9");
            punchDataKanriinfo.setPunchName("特別研究促進費評定表パンチデータ");
            punchDataKanriinfo.setJigyoKubun("7");
            punchDataKanriinfo.setSakuseiDate(new Date());
            punchDataKanriinfo.setPunchPath(punchPath);

            //DB更新メソッドを呼びだす
            punchKanriInfodao.update(connection, punchDataKanriinfo);

            success = true;

            //結果ををreturnする
            return punchDataKanriinfo;

        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4010"), e);
        } catch (ApplicationException e) {
            throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4000"), e);
        } catch (DataAccessException e) {
            throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4000"), e);
        } finally {
            try {
                if (success) {
                    //コミット
                    DatabaseUtil.commit(connection);
                } else {
                    //ロールバック
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ApplicationException("データベース更新中にエラーが発生しました。",
                        new ErrorInfo("errors.4000"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
//苗　追加ここまで    
    
//2006/06/20 苗　追加ここから
    /**
     * 特定領域研究(新規領域)応募情報パンチデータファイルを作成. <br />
     * <br />
     * 
     * 
     * @param userInfo
     * @return 処理結果情報を持つPunchDataKanriInfo
     * @throws ApplicationException
     */
    private PunchDataKanriInfo getPunchDataTokuteiSinnkiChosho(UserInfo userInfo)
            throws ApplicationException 
    {

        //DBコネクションを取得する
        Connection connection = null;

        boolean success = false;

        //Stringオブジェクトを作成する
        String punchResult = null;

        try {
            connection = DatabaseUtil.getConnection();

            //パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
            String tableName = "TOKUTEI-SHINKI-CHOSHO";

            //パンチデータ作成に必要なDAOをnewする
            PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

            //Listを結合したStringを取得する
            //TODO　　2006/06/20　苗
            punchResult = punchKanriInfodao.punchDataTokuteiSinnkiChosho(connection);

            //FileResourceを作成
            FileResource punchFileResource = new FileResource();
            punchFileResource.setBinary(punchResult.getBytes());

            //格納メソッドを呼ぶ
            String punchPath = kakuno(punchFileResource, tableName);

            //パンチデータ管理infoをnewする
            PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

            //パンチデータ管理テーブルを更新する
            punchDataKanriinfo.setPunchShubetu("10");
            punchDataKanriinfo.setPunchName("特定領域研究(新規領域)応募情報パンチデータ");
            punchDataKanriinfo.setJigyoKubun("5");
            punchDataKanriinfo.setSakuseiDate(new Date());
            punchDataKanriinfo.setPunchPath(punchPath);

            //DB更新メソッドを呼びだす
            punchKanriInfodao.update(connection, punchDataKanriinfo);

            success = true;

            //結果ををreturnする
            return punchDataKanriinfo;

        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4010"), e);
        } catch (ApplicationException e) {
            throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4000"), e);
        } catch (DataAccessException e) {
            throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4000"), e);
        } finally {
            try {
                if (success) {
                    //コミット
                    DatabaseUtil.commit(connection);
                } else {
                    //ロールバック
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ApplicationException("データベース更新中にエラーが発生しました。",
                        new ErrorInfo("errors.4000"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
    
    /**
     * 特定領域研究（新規領域）領域計画書概要パンチデータファイルを作成. <br />
     * <br />
     * 
     * 
     * @param userInfo
     * @return 処理結果情報を持つPunchDataKanriInfo
     * @throws ApplicationException
     */
    private PunchDataKanriInfo getPunchDataTokuteiSinnkiRyoiki(UserInfo userInfo)
            throws ApplicationException 
    {

        //DBコネクションを取得する
        Connection connection = null;

        boolean success = false;

        //Stringオブジェクトを作成する
        String punchResult = null;

        try {
            connection = DatabaseUtil.getConnection();

            //パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
            String tableName = "TOKUTEI-SHINKI-RYOIKI";

            //パンチデータ作成に必要なDAOをnewする
            PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

            //Listを結合したStringを取得する
            //TODO 2006/06/20　苗
            punchResult = punchKanriInfodao.punchDataTokuteiSinnkiGaiyo(connection);

            //FileResourceを作成
            FileResource punchFileResource = new FileResource();
            punchFileResource.setBinary(punchResult.getBytes());

            //格納メソッドを呼ぶ
            String punchPath = kakuno(punchFileResource, tableName);

            //パンチデータ管理infoをnewする
            PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

            //パンチデータ管理テーブルを更新する
            punchDataKanriinfo.setPunchShubetu("11");
            punchDataKanriinfo.setPunchName("特定領域研究（新規領域）領域計画書概要パンチデータ");
            punchDataKanriinfo.setJigyoKubun("5");
            punchDataKanriinfo.setSakuseiDate(new Date());
            punchDataKanriinfo.setPunchPath(punchPath);

            //DB更新メソッドを呼びだす
            punchKanriInfodao.update(connection, punchDataKanriinfo);

            success = true;

            //結果ををreturnする
            return punchDataKanriinfo;

        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4010"), e);
        } catch (ApplicationException e) {
            throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4000"), e);
        } catch (DataAccessException e) {
            throw new ApplicationException("パンチデータ作成中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4000"), e);
        } finally {
            try {
                if (success) {
                    //コミット
                    DatabaseUtil.commit(connection);
                } else {
                    //ロールバック
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ApplicationException("データベース更新中にエラーが発生しました。",
                        new ErrorInfo("errors.4000"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
    
//2006/06/20　苗　追加ここまで  
    
//  CSV出力機能を追加        2006.09.19
    /* 
     * 特定領域研究（新規領域）領域計画書概要パンチデータファイルをCSV出力
     * @see jp.go.jsps.kaken.model.IPunchDataMaintenance#searchCsvData(jp.go.jsps.kaken.model.vo.UserInfo)
     */
    private PunchDataKanriInfo searchCsvData(UserInfo userInfo)
            throws ApplicationException {

        //DBコネクションの取得
        Connection connection = null;
        boolean success = false;
        //Stringオブジェクトを作成する
        String punchResult = null;       

        try {
            //パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定

            connection = DatabaseUtil.getConnection();
            //パンチデータのパンチデータ管理テーブルに格納されるファイル名を指定
            String tableName = "TOKUTEI-SHINKI-RYOIKI";
            //パンチデータ作成に必要なDAOをnewする
            PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);
            
            try {
            	
                //パンチデータ作成に必要なDAOをnewする
            	punchResult = punchKanriInfodao.punchDataTokuteiSinnkiGaiyoCsv(connection);
                //FileResourceを作成
                FileResource punchFileResource = new FileResource();
                punchFileResource.setBinary(punchResult.getBytes());

                //格納メソッドを呼ぶ
                String punchPath = kakunoCsv(punchFileResource, tableName);
                

                //パンチデータ管理infoをnewする
                PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

                //パンチデータ管理テーブルを更新する
                punchDataKanriinfo.setPunchShubetu("11");
                punchDataKanriinfo.setPunchName("特定領域研究（新規領域）領域計画書概要パンチデータ");
                punchDataKanriinfo.setJigyoKubun("5");
                punchDataKanriinfo.setSakuseiDate(new Date());
                punchDataKanriinfo.setPunchPath(punchPath);

                //DB更新メソッドを呼びだす
                punchKanriInfodao.update(connection, punchDataKanriinfo);

                success = true;

                //結果ををreturnする
                return punchDataKanriinfo;                
            } catch (DataAccessException e) {
                throw new ApplicationException(
                        "特定領域研究（新規領域）領域計画書概要データ検索中にDBエラーが発生しました。",
                        new ErrorInfo("errors.4004"),
                        e);
            } catch(NoDataFoundException e) {
                throw new ApplicationException(
                        "特定領域研究（新規領域）領域計画書概要が見つかりません。",
                        new ErrorInfo("errors.4000"),
                        e);
            }
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }       
}

