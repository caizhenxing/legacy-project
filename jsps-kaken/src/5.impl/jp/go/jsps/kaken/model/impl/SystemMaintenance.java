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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.CheckListInfoDao;
import jp.go.jsps.kaken.model.dao.impl.JigyoKanriInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterBukyokuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterJigyoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKaigaibunyaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKanriInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKeizokuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKenkyushaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKeywordInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterRyouikiInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterSaimokuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterShokushuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.RuleInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinsaKekkaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinsainInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseishaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShozokuInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.CSVTokenizer;
import jp.go.jsps.kaken.util.CheckDiditUtil;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.util.HanZenConverter;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.RandomPwd;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;


/**
 * システム管理者機能メンテナンスクラス.<br /><br />
 * <b>概要</b><br />
 * 事業情報の管理と、各種マスタの取り込み処理を行う。
 * マスタの取り込みでは、格納されるCSVのバックアップと、テーブルのバックアップがWASへ格納される。
 * また、取り込みを行った件数や、エラーが発生した際のエラーメッセージの管理をマスタ管理マスタにて行う。
 * 
 */
public class SystemMaintenance implements ISystemMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log   = LogFactory.getLog(SystemMaintenance.class);
	
	/** ログ（マスタ取り込み）*/
	protected static Log mtLog = LogFactory.getLog("masterTorikomi");
	
	/** マスタのバックアップ（ダンプ）を取得する際のコマンド */
	private static String EXPORT_COMMAND  = ApplicationSettings.getString(ISettingKeys.EXPORT_COMMAND);	
	
	/** csvファイル保存先 */
	private static String CSV_TORIKOMI_LOCATION = ApplicationSettings.getString(ISettingKeys.CSV_TORIKOMI_LOCATION);	
	
	//2005.08.30 iso 処理件数をログに出力する機能を追加
	/** 何件ごとにマスタ取り込み件数を表示するかを設定 */
	private static final int LOGCOUNT = 1000;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public SystemMaintenance() {
		super();
	}
	
	

	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------
	
	/**
	 * CSVファイル情報読込.<br />
	 * <br />
	 * CSVファイルのFileResourceをリストの形式へ変換する。<br />
	 * カンマ区切りの１カラムずつをリストに格納する。
	 * それらのリストを全レコード分、返却用リストに格納し返却する。 
	 * @param fileRes		CSVファイルのFileResourceインスタンス
	 * @return	CSVファイルの情報を格納したList
	 * @throws IOException	ファイル読込例外
	 */
	private List csvFile2List(FileResource fileRes)
		throws IOException
	{
		BufferedReader bir = new BufferedReader(
								new InputStreamReader(
									new ByteArrayInputStream(fileRes.getBinary())));
		//全レコードのリスト
		List dt = new ArrayList();
		
		//ファイルから本文情報の取得
		String tmpData = null;
		while((tmpData = bir.readLine()) != null){
			dt.add(this.divideString(tmpData));	//1行ずつ追加していく
		}		
		return dt;
		
	}
	
	
	/**
	 * CSVデータをListに変換.<br />
	 * <br />
	 * CSVデータ（String）をカンマで分割し、ArrayListに格納後に返却する。
	 */
	private ArrayList divideString(String strData){
		ArrayList list = new ArrayList();
		CSVTokenizer ct = new CSVTokenizer(strData);
		while(ct.hasMoreTokens()){
			list.add(ct.nextToken());
		}
		return list;
	}


	/**
	 * 半角数字チェック.<br />
	 * <br />
	 * 引数の文字列に半角数字以外の文字が存在するかチェックする。<br />
	 * 引数がnullの場合はfalseを返却。
	 * @param strData チェックする文字列
	 * @return true:半角数字、false:半角数字以外の文字列を含んでいる
	 */
	private boolean checkNum(String strData){
	   //半角文字チェック
	   if (!checkHan (strData)) {
		   return false;
	   }
	   char chrField;
	   for (int i=0;i<strData.length();i++) {
		   chrField = strData.charAt (i);
		   if(!Character.isDigit(chrField)){
			   return false;			// 1文字でも数値以外ならfalse;
		   }
	   }
	   return true;					// すべて数値ならtrue
	}
	
	
	/**
	 * 半角チェック.<br />
	 * <br />
	 * 引数の文字列に半角以外の文字が存在するかチェックする。<br />
	 * 引数がnullの場合はfalseを返却。
	 * @param strData チェックする文字列
	 * @return true:半角、false:全角の文字列を含んでいる
	 */
	private boolean checkHan (String strField) {
		//文字列がnullの場合はfalseを返す。
		if(strField == null){
			return false;
		}
		char chrField;
		for (int i=0;i<strField.length();i++) {
			chrField = strField.charAt (i);
			if(!StringUtil.isAscii(chrField)){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 文字列の長さチェック（バイト）.<br />
	 * <br />
	 * 最大文字数以下かどうかチェックする。<br />
	 * 文字列長を全角2バイト、半角1バイトのバイト換算でカウントする。
	 * 半角カナに対応（ShiftJIS専用）
	 * @param strField 検査対象文字列
	 * @param intMaxLenB 最大バイト数
	 * @return true:最大文字数以内、false:最大文字数より大きい 
	 */
	private boolean checkLenB (String strField,int intMaxLenB) {
		char chrField;
		int lenB = 0;
		for (int i=0;i<strField.length();i++) {
			chrField = strField.charAt (i);
			boolean blIsHan = false;
			for (char c=' ';c<='~';c++) {
				if (chrField == c) {
					blIsHan = true;
					break;
				}
			}
			for (char ch='｡';ch<='ﾟ';ch++) {
				if (chrField == ch) {
					blIsHan = true;
					break;
				}
			}
			if (blIsHan) {
				lenB++;
			}
			else {
				lenB+=2;
			}
		}

		if (lenB <= intMaxLenB) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * csvファイルの保存.<br />
	 * <br />
	 * csvファイル（第1引数で渡されるFileResource）をサーバに保存する。
	 * 保存先フォルダ、ファイル名は以下の通り
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr>
	 *			<td style="color:white;font-weight: bold">フォルダ</td>
	 *			<td bgcolor="#FFFFFF">
	 *				ApplicationSettings.propertiesのCSV_TORIKOMI_LOCATIONで指定される場所<br />
	 *				例)${shinsei_path}/mastercsv/第二引数
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style="color:white;font-weight: bold">ファイル名</td>
	 *			<td bgcolor="#FFFFFF">yyyyMMddHHmmssSSS.csv</td>
	 *		</tr>
	 *	</table>
	 * 
	 * @param fileRes				 アップロードファイルリソース。
	 * @param tableName             保存先のフォルダ名
	 * @return                      格納したCSVファイルの絶対パス名
	 * @throws ApplicationException CSFVファイル格納に失敗した場合
	 */
	private String kakuno(FileResource fileRes, String tableName)
		throws ApplicationException
	{
		//出力先フォルダ
		String path = MessageFormat.format(CSV_TORIKOMI_LOCATION, new String[]{tableName});
		File parent = new File(path);
		
		//csvファイルの命名規則を規定
		String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".csv";
		fileRes.setPath(name);
		
		try {
			//csvファイルをサーバに格納するメソッドを呼び出す
			FileUtil.writeFile(parent, fileRes);
		} catch (IOException e) {
			throw new ApplicationException("CSVファイルの格納中に失敗しました。",
											 new ErrorInfo("errors.7001"), 
											 e);
		}
		return new File(parent, name).getAbsolutePath();
		
	}	
	
	
	/**
	 * 機関情報取得.<br />
	 * <br />
	 * 機関情報を取得する。
	 * 第二引数のkikanMapに、機関コードをキー、機関名（和文）を値としてセットする。
	 * 例外が発生した場合はfalseを返す。 
	 * なお、機関情報はMASTER_KIKANテーブルより取得する。
	 * @param connection	Connection
	 * @param kikanMap		機関情報を格納するMap
	 * @return	true:格納成功、false:格納失敗
	 */
	private boolean setKikanNameList(Connection connection, Map kikanMap){
		try{
			List list = MasterKikanInfoDao.selectKikanList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				kikanMap.put(recordMap.get("SHOZOKU_CD"),
							 recordMap.get("SHOZOKU_NAME_KANJI"));
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}
	
	
	/**
	 * 機関情報取得.<br />
	 * <br />
	 * 機関情報を取得する。<br />
	 * 機関コードをキー、機関情報（Map）を値として、第二引数のkikanMapセットする。
	 * 取得する機関情報は以下の通り
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHUBETU_CD</td><td>機関種別コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KIKAN_KUBUN</td><td>機関区分</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_KANJI</td><td>機関名称（日本語）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_RYAKUSHO</td><td>機関略称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_EIGO</td><td>機関名称（英語）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_ZIP</td><td>郵便番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_ADDRESS1</td><td>住所１</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_ADDRESS2</td><td>住所２</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_TEL</td><td>電話番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_FAX</td><td>FAX番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_DAIHYO_NAME</td><td>代表者</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BIKO</td><td>備考</td></tr>
	 *	</table><br/>
	 * なお、機関情報はMASTER_KIKANテーブルより取得する。
	 * 取得する際に、例外が発生した場合はfalseを返す。 
	 * @param connection	Connection
	 * @param kikanMap		機関情報を格納するMap
	 * @return	true:取得成功、false:取得失敗
	 */
	private boolean setKikanInfoList(Connection connection, Map kikanMap){
		try{
			List list = MasterKikanInfoDao.selectKikanList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				kikanMap.put(recordMap.get("SHOZOKU_CD"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}
	
	
	/**
	 * 部局情報取得.<br />
	 * <br />
	 * 部局情報を取得する。<br />
	 * 部局コードをキー、部局情報（Map）を値として、第二引数のbukyokuMapセットする。
	 * 取得する機関情報は以下の通り
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKYOKU_CD</td><td>部局コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKA_NAME</td><td>部科名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKA_RYAKUSHO</td><td>部科略称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKA_KATEGORI</td><td>カテゴリ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SORT_NO</td><td>ソート番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BIKO</td><td>備考</td></tr>
	 *	</table><br/>
	 * なお、部局情報はMASTER_BUKYOKUテーブルより取得する。
	 * 取得する際に、例外が発生した場合はfalseを返す。 
	 * @param connection	Connection
	 * @param bukyokuMap	部局情報を格納するMap
	 * @return	true:取得成功、false:取得失敗
	 */
	private boolean setBukyokuInfoList(Connection connection, Map bukyokuMap){
		try{
			List list = MasterBukyokuInfoDao.selectBukyokuInfoList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				bukyokuMap.put(recordMap.get("BUKYOKU_CD"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}	
	
	
	/**
	 * 職情報取得.<br />
	 * <br />
	 * 職情報を取得する。<br />
	 * 職種コードをキー、職情報（Map）を値として、第二引数のshokuMapセットする。
	 * 取得する職情報は以下の通り
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOKUSHU_CD</td><td>職種コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOKUSHU_NAME</td><td>職名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOKUSHU_NAME_RYAKU</td><td>職名(略称)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BIKO</td><td>備考</td></tr>
	 *	</table><br/>
	 * なお、職情報はMASTER_SHOKUSHUテーブルより取得する。
	 * 取得する際に、例外が発生した場合はfalseを返す。 
	 * @param connection	Connection
	 * @param shokuMap		職情報を格納するMap
	 * @return	true:取得成功、false:取得失敗
	 */
	private boolean setShokuInfoList(Connection connection, Map shokuMap){
		try{
			List list = MasterShokushuInfoDao.selectShokushuList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				shokuMap.put(recordMap.get("SHOKUSHU_CD"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}		
	
	
	/**
	 * 細目報取得.<br />
	 * <br />
	 * 細目情報を取得する。<br />
	 * 分科細目コードをキー、細目情報（Map）を値として、第二引数のsaimokuMapセットする。
	 * 取得する細目情報は以下の通り
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUNKASAIMOKU_CD</td><td>分科細目コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SAIMOKU_NAME</td><td>細目名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUNKA_CD</td><td>分科コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUNKA_NAME</td><td>分科名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUNYA_CD</td><td>分野コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUNYA_NAME</td><td>分野名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KEI</td><td>系</td></tr>
	 *	</table><br/>
	 * なお、細目情報はMASTER_SAIMOKUテーブルより取得する。
	 * 取得する際に、例外が発生した場合はfalseを返す。 
	 * @param connection	Connection
	 * @param saimokuMap		細目情報を格納するMap
	 * @return	true:取得成功、false:取得失敗
	 */
	private boolean setSaimokuInfoList(Connection connection, Map saimokuMap){
		try{
			List list = MasterSaimokuInfoDao.selectSaimokuInfoList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				saimokuMap.put(recordMap.get("BUNKASAIMOKU_CD"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}		
	
	
	/**
	 * 海外分野報取得.<br />
	 * <br />
	 * 海外分野情報を取得する。<br />
	 * 海外分野コードをキー、海外分野情報（Map）を値として、第二引数のkaigaiMapセットする。
	 * 取得する海外分野情報は以下の通り
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KAIGAIBUNYA_CD</td><td>海外分野CD</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KAIGAIBUNYA_NAME</td><td>海外分野名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KAIGAIBUNYA_NAME_RYAKU</td><td>海外分野名略</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BIKO</td><td>備考</td></tr>
	 *	</table><br/>
	 * なお、海外分野情報はMASTER_KAIGAIBUNYAテーブルより取得する。
	 * 取得する際に、例外が発生した場合はfalseを返す。 
	 * @param connection	Connection
	 * @param kaigaiMap		海外分野情報を格納するMap
	 * @return	true:取得成功、false:取得失敗
	 */
	private boolean setKaigaibunyaInfoList(Connection connection, Map kaigaiMap){
		try{
			List list = MasterKaigaibunyaInfoDao.selectKaigaibunyaList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				kaigaiMap.put(recordMap.get("KAIGAIBUNYA_CD"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}		
	
	
	/**
	 * 審査員報取得.<br />
	 * <br />
	 * 事業区分が4（基盤）の審査員情報を取得する。<br />
	 * 審査員コードをキー、審査員情報（Map）を値として、第二引数のshinsainMapセットする。
	 * 取得する審査員情報は以下の通り
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHINSAIN_NO</td><td>審査員番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>JIGYO_KUBUN</td><td>事業区分</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANJI_SEI</td><td>氏名（漢字等－姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANJI_MEI</td><td>氏名（漢字等－名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANA_SEI</td><td>氏名（フリガナ－姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANA_MEI</td><td>氏名（フリガナ－名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>所属機関名（コード）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME</td><td>所属機関名（和文）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKYOKU_NAME</td><td>部局名（和文）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOKUSHU_NAME</td><td>職種名称</td></tr>
	 *	</table><br/>
	 * なお、審査員情報はMASTER_SHINSAINテーブルより取得する。
	 * 取得する際に、例外が発生した場合はfalseを返す。 
	 * @param connection	Connection
	 * @param shinsainMap	審査員情報を格納するMap
	 * @return	true:取得成功、false:取得失敗
	 */
	private boolean setShinsainInfoList(Connection connection, Map shinsainMap){
		try{
			//基盤のみ
			List list = ShinsainInfoDao.selectShinsainInfoList(connection, IJigyoKubun.JIGYO_KUBUN_KIBAN);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				shinsainMap.put(recordMap.get("SHINSAIN_NO"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}		
	
	
	/**
	 * 事業報取得.<br />
	 * <br />
	 * 事業区分が4（基盤）の事業情報を取得する。
	 * 事業コードをキー、事業情報（Map）を値として、第二引数のjigyoMapセットする。
	 * 取得する事業情報は以下の通り
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHINSAIN_NO</td><td>事業番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>JIGYO_KUBUN</td><td>事業区分</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANJI_SEI</td><td>氏名（漢字等－姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANJI_MEI</td><td>氏名（漢字等－名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANA_SEI</td><td>氏名（フリガナ－姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANA_MEI</td><td>氏名（フリガナ－名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>所属機関名（コード）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME</td><td>所属機関名（和文）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKYOKU_NAME</td><td>部局名（和文）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOKUSHU_NAME</td><td>職種名称</td></tr>
	 *	</table><br/>
	 * なお、事業情報はJIGYOKANRIテーブルより取得する。
	 * 取得する際に、例外が発生した場合はfalseを返す。 
	 * @param connection	Connection
	 * @param jigyoMap		事業情報を格納するMap
	 * @return	true:取得成功、false:取得失敗
	 */
	private boolean setJigyoInfoList(Connection connection, Map jigyoMap){
		try{
//			//基盤のみ
//			List list = JigyoKanriInfoDao.selectJigyoKanriList(connection, IJigyoKubun.JIGYO_KUBUN_KIBAN);
//			for(int i=0; i<list.size(); i++){
//				Map recordMap = (Map)list.get(i);
//				jigyoMap.put(recordMap.get("JIGYO_ID"), recordMap);
//			}
//            //若手スタートアップ
//			list = JigyoKanriInfoDao.selectJigyoKanriList(connection, IJigyoKubun.JIGYO_KUBUN_WAKATESTART);
//			for(int i=0; i<list.size(); i++){
//				Map recordMap = (Map)list.get(i);
//				jigyoMap.put(recordMap.get("JIGYO_ID"), recordMap);
//			}
//2006/04/26 追加ここから
            
            List list = JigyoKanriInfoDao.selectKibanJigyoKubun(connection);
            for (int i = 0; i < list.size(); i++)
            {
                Map recordMap = (Map)list.get(i);
                jigyoMap.put(recordMap.get("JIGYO_ID"), recordMap);
            }
//苗　追加ここまで            
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}			
	
	
	
	//---------------------------------------------------------------------
	// implement ISystemMaintenance
	//---------------------------------------------------------------------

	/**
	 * マスタ管理情報取得.<br />
	 * <br />
	 * マスタ管理マスタからマスタ管理情報を取得し、Listに格納して返却する。<br />
	 * 以下のSQLを発行し、マスタ管理情報を取得する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		A.MASTER_SHUBETU,	-- マスタ種別
	 *		A.MASTER_NAME,	-- マスタ名称
	 *		TO_CHAR(A.IMPORT_DATE, 'YYYY/MM/DD HH24:MI:SS')
	 * 			IMPORT_DATE,	-- 取り込み日時
	 *		A.KENSU,	-- 件数
	 *		A.IMPORT_TABLE,	-- 取り込みテーブル名
	 *		A.IMPORT_FLG,	-- 新規・更新フラグ
	 *		A.IMPORT_MSG,	-- 処理状況
	 *		A.CSV_PATH	-- CSVファイルパス
	 *	FROM
	 *		MASTER_INFO A
	 *	ORDER BY
	 *		TO_NUMBER(MASTER_SHUBETU)
	 *	</pre>
	 *	</td></tr>
	 *	</table><br />
	 * 
	 *  各レコードの情報はキーを列名、としてMapに格納し、返却用のListへセットして呼び出し元へ返却される。
	 * 
	 * @param userInfo	UserInfo
	 * @return	マスタ管理情報
	 * @throws	ApplicationException	何らかの例外
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public List selectList(UserInfo userInfo)
		throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return MasterKanriInfoDao.selectList(connection);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	
	
	/**
	 * マスタCSVファイルのFileResource取得.<br />
	 * <br />
	 * 第二引数で渡されたマスタ種別に対応する情報をCSVファイルパスを、マスタ管理マスタから取得する。<br />
	 * 取得したCSVファイルパスに対応するファイルを読み込み、
	 * ファイル情報をFileResourceクラスにセットし、呼び出しもとへ返却する。<br />
	 * なお、マスタ管理マスタ情報を取得する際のSQLは以下の通り。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		A.MASTER_SHUBETU,	-- マスタ種別
	 *		A.MASTER_NAME,	-- マスタ名称
	 *		A.IMPORT_DATE,	-- 取り込み日時
	 *		A.KENSU,	-- 件数
	 *		A.IMPORT_TABLE,	-- 取り込みテーブル名
	 *		A.IMPORT_FLG,	-- 新規・更新フラグ
	 *		A.IMPORT_MSG,	-- 処理状況
	 *		A.CSV_PATH	-- CSVファイルパス
	 *	FROM
	 *		MASTER_INFO A
	 *	WHERE
	 *		MASTER_SHUBETU = ?
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">※バインド変数は第二引数のmasterShubetuをセットする。</div><br />
	 * 
	 * @param userInfo UserInfo
	 * @param masterShubetu		マスタ種別
	 * @return マスタCSVファイル情報（FileResource）
	 * @throws ApplicationException 何らかの例外
	 * @see jp.go.jsps.kaken.model.ISystemMaintenance#getCsvFileResource(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
	 */
	public FileResource getCsvFileResource(UserInfo userInfo, String masterShubetu) 
		throws ApplicationException {
		
		//MasterKanriInfoDaoをnewする
		MasterKanriInfoDao masterKanriInfoDao = new MasterKanriInfoDao(userInfo);
		
		//ユーザが選択したMasterKanriInfoを取る
		MasterKanriInfo masterKanriInfo = null;
		
		Connection connection = null;
		try {
			//コネクション獲得
			connection = DatabaseUtil.getConnection();
			masterKanriInfo = masterKanriInfoDao.selectMasterKanriInfo(connection, masterShubetu);
		} catch (NoDataFoundException e) {
			e.printStackTrace();
			throw new ApplicationException ("マスタ管理ファイル検索中にDBエラーが発生しました。", new ErrorInfo("error.4004"),e);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ApplicationException ("マスタ管理ファイル検索中にDBエラーが発生しました。", new ErrorInfo("error.4004"),e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		//MasterKanriInfoからCSV_PATHを取得する
		String path = masterKanriInfo.getCsvPath();
		
		//GetしたCSV_PATHにあるCSVファイルを取得する
		File file = new File(path);
		FileResource fileResource = null;
		try {
			fileResource = FileUtil.readFile(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw new ApplicationException("指定したファイルが見つかりません。path="+path, new ErrorInfo("errors.7004"),e1);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new ApplicationException("出入力エラー。", new ErrorInfo("errors.appliaction"), e1);
		}
		
		//取得したCSVファイルをCsvOutActionクラスに返す
		return fileResource;

	}
	
	
	
	/**
	 * マスタ情報取込.<br />
	 * マスタ情報の取り込みを行う。<br />
	 * 取り込みを行うマスタは、第三引数のマスタ種別と、第四引数の新規更新フラグから決定する。
	 * マスタ種別・新規更新フラグと取り込みを行うマスタの対応は以下の通り。<br/>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr style="color:white;font-weight: bold"><td>マスタ種別</td><td>新規更新フラグ</td><td>対象マスタ名</td><td>呼び出しメソッド</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>-</td><td>分科細目マスタ</td><td>torikomiMasterSaimoku</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>-</td><td>所属機関マスタ</td><td>torikomiMasterKikan</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>-</td><td>部局マスタ</td><td>torikomiMasterBukyoku</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>0</td><td>学創審査員マスタ（新規）</td><td>torikomiMasterShinsainGakusou(,,false)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>0以外</td><td>学創審査員マスタ（更新）</td><td>torikomiMasterShinsainGakusou(,,true)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>0</td><td>基盤審査員マスタ（新規）</td><td>torikomiMasterShinsainKiban(,,false)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>0以外</td><td>学創審査員マスタ（更新）</td><td>torikomiMasterShinsainKiban(,,true)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>9</td><td>-</td><td>基盤用割り振りデータ</td><td>torikomiMasterWarifuriKekka</td></tr>
	 *	</table>
	 * 上記テーブルで定義されるマスタ種別以外が引き渡された場合は、例外をthrowする。
	 * @param userInfo			UserInfo
	 * @param fileRes			FileResource
	 * @param masterShubetu		マスタ種別
	 * @param shinkiKoshinFlg	新規更新フラグ
	 * @exception	ApplicationException なんらかの例外
	 * @see jp.go.jsps.kaken.model.ISystemMaintenance#torikomimaster(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.util.FileResource, java.lang.String, java.lang.String)
	 */
	public MasterKanriInfo torikomimaster(
		UserInfo userInfo,
		FileResource fileRes,
		String masterShubetu,
		String shinkiKoshinFlg)
		throws ApplicationException
	{
		if(MASTER_SAIMOKU.equals(masterShubetu)){
			return torikomiMasterSaimoku(userInfo, fileRes);						//分科細目マスタ
			
		}else if(MASTER_KIKAN.equals(masterShubetu)){
			return torikomiMasterKikan(userInfo, fileRes);							//所属機関マスタ
				
		}else if(MASTER_BUKYOKU.equals(masterShubetu)){
			return torikomiMasterBukyoku(userInfo, fileRes);						//部局マスタ
				
		}else if(MASTER_SHINSAIN_GAKUJUTU.equals(masterShubetu)){
			if(MASTER_TORIKOMI_SHINKI.equals(shinkiKoshinFlg)){
				return torikomiMasterShinsainGakusou(userInfo, fileRes ,false);	//学創審査員マスタ（新規）
			}else{
				return torikomiMasterShinsainGakusou(userInfo, fileRes, true);		//学創審査員マスタ（更新）
			}
		}else if(MASTER_SHINSAIN_KIBAN.equals(masterShubetu)){
			if(MASTER_TORIKOMI_SHINKI.equals(shinkiKoshinFlg)){
				return torikomiMasterShinsainKiban(userInfo, fileRes, false);		//基盤審査員マスタ（新規）
			}else{
				return torikomiMasterShinsainKiban(userInfo, fileRes, true);		//基盤審査員マスタ（更新）
			}
		}else if(MASTER_WARIFURIKEKKA.equals(masterShubetu)){
			return torikomiMasterWarifuriKekka(userInfo, fileRes);					//基盤用割り振りデータ
		
		//2005/04/22 追加 ここから-----------------------------------------------
		//研究者マスタの追加のため
		}else if(MASTER_KENKYUSHA.equals(masterShubetu)){
			return torikomiMasterKenkyusha(userInfo, fileRes);						//研究者マスタ
		
		//継続課題マスタの追加のため
		}else if(MASTER_KEIZOKUKADAI.equals(masterShubetu)){
			return torikomiMasterKeizoku(userInfo, fileRes);						//継続課題マスタ
		//追加 ここまで----------------------------------------------------------		
		
		//領域マスタ取り込み 2005/08/11追加
		}else if(MASTER_RYOIKI.equals(masterShubetu)){
			return torikomiMasterRyoiki(userInfo, fileRes);							//領域マスタ
			
		//キーワードマスタ取り込み 2005/07/21追加
		}else if(MASTER_KEYWORD.equals(masterShubetu)){
		//}else if(masterShubetu.equals("10")){
			return torikomiMasterKeyword(userInfo, fileRes);						//キーワードマスタ

		}else{
			throw new ApplicationException("*****マスタ種別が不正です。masterShubetu1="+masterShubetu);
		}
	}	
	
	
	
	/**
	 * 事業管理情報検索.<br />
	 * 事業管理情報を検索する。検索処理は、JigyoKanriMaintenanceに委譲する。
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	検索条件（SearchInfo）
	 * @see jp.go.jsps.kaken.model.ISystemMaintenance#selectJigyoList(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.SearchInfo)
	 */
	public Page selectJigyoList(UserInfo userInfo, SearchInfo searchInfo)
		throws ApplicationException
	{
		//事業情報管理ビジネスロジックに処理を委譲する
		IJigyoKanriMaintenance jigyoMainte = new JigyoKanriMaintenance();
		return jigyoMainte.search(userInfo, searchInfo);
	}
	
	
	
	/**
	 * 事業情報削除.<br />
	 * 事業情報の削除を行う。<br />
	 * 削除処理は以下の通り。<br />
	 * <b>1.事業情報取得</b><br />
	 * 以下のSQLを実行して事業情報を取得し、JigyoKanriInfoにセットする。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		 A.JIGYO_ID	-- 事業ID
	 *		,A.NENDO	-- 年度
	 *		,A.KAISU	-- 回数
	 *		,A.JIGYO_NAME	-- 事業名
	 *		,A.JIGYO_KUBUN	-- 事業区分
	 *		,A.SHINSA_KUBUN	-- 審査区分
	 *		,A.TANTOKA_NAME	-- 業務担当課
	 *		,A.TANTOKAKARI	-- 業務担当係名
	 *		,A.TOIAWASE_NAME	-- 問い合わせ先担当者名
	 *		,A.TOIAWASE_TEL	-- 問い合わせ先電話番号
	 *		,A.TOIAWASE_EMAIL	-- 問い合わせ先E-mail
	 *		,A.UKETUKEKIKAN_START	-- 学振受付期間（開始）
	 *		,A.UKETUKEKIKAN_END	-- 学振受付期間（終了）
	 *		,A.SHINSAKIGEN	-- 審査期限
	 *		,A.TENPU_NAME	-- 添付文書名
	 *		,A.TENPU_WIN	-- 添付ファイル格納フォルダ（Win）
	 *		,A.TENPU_MAC	-- 添付ファイル格納フォルダ（Mac）
	 *		,A.HYOKA_FILE_FLG	-- 評価用ファイル有無
	 *		,A.HYOKA_FILE	-- 評価用ファイル格納フォルダ
	 *		,A.KOKAI_FLG	-- 公開フラグ
	 *		,A.KESSAI_NO	-- 公開決裁番号
	 *		,A.KOKAI_ID	-- 公開確定者ID
	 *		,A.HOKAN_DATE	-- データ保管日
	 *		,A.YUKO_DATE	-- 保管有効期限
	 *		,A.BIKO	-- 備考
	 *		,A.DEL_FLG	-- 削除フラグ
	 *	FROM
	 *		JIGYOKANRI A
	 *	WHERE
	 *		JIGYO_ID = ?
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">※バインド変数は第二引数jigyoPkのjigyoIdをセットする。</div>
	 *	<br />
	 *	<br />
	 * <b>2.保管済みチェック</b><br />
	 * 1.で取得した情報の、保管日（HokanDate）を確認する。nullの場合（未保管）は例外をthrowする。<br />
	 *	<br />
	 * <b>3.事業情報データ削除</b><br />
	 * 以下のSQLを実行して、事業情報を論理削除する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	UPDATE
	 *		JIGYOKANRI
	 *	SET
	 *		DEL_FLG = 1	-- 削除フラグ
	 *	WHERE
	 *		JIGYO_ID = ?
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">※バインド変数は第二引数jigyoPkのjigyoIdをセットする。</div>
	 *	<br />
	 *	<br />
	 * <b>4.申請データ削除</b><br />
	 * 削除した事業に関する申請情報を下記SQLで論理削除する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	UPDATE
	 *		SHINSEIDATAKANRI
	 *	SET
	 *		DEL_FLG = 1	-- 削除フラグ
	 *	WHERE
	 *		JIGYO_ID = ?
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">※バインド変数は第二引数jigyoPkのjigyoIdをセットする。</div>
	 *	<br />
	 * 	1.～4.の処理実行中に例外が発生した場合は、rollbackを行う。
	 * @param userInfo	UserInfo
	 * @param jigyoPk	削除を行う事業情報のPK情報（JigyoKanriPk）
	 * @see jp.go.jsps.kaken.model.ISystemMaintenance#deleteJigyo(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriPk)
	 */
	public JigyoKanriInfo deleteJigyo(UserInfo userInfo, JigyoKanriPk jigyoPk)
		throws ApplicationException
	{
		//DBコネクションの取得
		Connection connection = null;
		boolean success = false;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---事業情報取得
			JigyoKanriInfo info = null;
			JigyoKanriInfoDao jigyoKanriDao = new JigyoKanriInfoDao(userInfo);
			try{
				info = jigyoKanriDao.selectJigyoKanriInfo(connection, jigyoPk);
			} catch (DataAccessException e) {
				success = false;
				throw new ApplicationException(
					"事業情報管理データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//保管済みチェック
			if(info.getHokanDate() == null){
				throw new ApplicationException(
					"当該事業情報は保管されていません。jigyoId=" + jigyoPk.getJigyoId(),
					new ErrorInfo("errors.5012"));
			}
			
			//---事業情報データ削除（削除フラグ）
			try{
				jigyoKanriDao.deleteFlgJigyoKanriInfo(connection, jigyoPk);
			} catch (DataAccessException e) {
				success = false;
				throw new ApplicationException(
					"事業情報管理データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			}
			jigyoKanriDao = null;

			//---申請データ削除（削除フラグ）
			try {
				ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo);
				dao.deleteFlagShinseiDataInfo(connection, jigyoPk);
			} catch (DataAccessException e) {
				success = false;
				throw new ApplicationException(
					"申請書管理データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			}

			success = true;	//ここまで処理が進めば正常と判断できる
			return info;
			
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"事業データ削除中にエラーが発生しました。",
					new ErrorInfo("errors.4009"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
		
	/**
	 * 分科細目マスタ取込.<br />
	 * 分科細目マスタ情報を取得する。<br />
	 * <b>1.CSVデータをリスト形式に変換</b><br />
	 * 引数で渡されたfileResをList形式へ変換する。Listの要素としてさらにListを持ち、
	 * その要素にCSV情報の一レコード分の情報を格納する。
	 * <br /><br />
	 * <b>2.カラム数チェック</b><br />
	 * 1.で作成したListの一つ目の要素のListの要素数が、7であるか確認する。それ以外の場合は、例外をthrowする。
	 * <br /><br />
	 * <b>3.csvファイル格納</b><br />
	 * 自クラスのkakuno()メソッドを使用してCSVファイルをサーバへ格納する。
	 * <br /><br />
	 * <b>4.マスタエクスポート</b><br />
	 * 分科細目マスタをバックアップ用にエクスポートする。エクスポートは、ApplicationSettings.propertiesの、
	 * EXPORT_COMMANDで定義されているコマンドを実行することによって行われる。<br />
	 * 出力されるダンプファイルのファイル名は以下の通り。<br />
	 * MASTER_SAIMOKU_yyyyMMddHHmmssSSS.DMP
	 * <br />
	 * <br />
	 * <b>5.分科細目情報削除</b><br />
	 * 分科細目情報を、以下のSQLを実行することによって削除する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM MASTER_SAIMOKU
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * <br />
	 * <b>6.情報取込</b><br />
	 * 1.で作成したCSVファイル情報Listを分科細目マスタへINSERTする。
	 * Listに格納されている要素Listには以下の情報が格納されている。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>要素番号</td><td>テーブル列名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>細目コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>細目名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>分科コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>分科名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>分野コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>分野名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>系</td></tr>
	 *	</table><br />
	 * 
	 * 各列の情報は、桁数チェック、型チェックおよび一意キー制約チェックを行い、問題がなければINSERT処理を行う。
	 * 問題があった場合は、エラー情報を管理するListに格納したのち、次の情報へ処理を進める。
	 * なお、分科細目コード、分科コード、分野コードは、各チェックとともに左0埋め処理を行う。<br />
	 * INSERTする際に実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		MASTER_SAIMOKU(
	 *			 BUNKASAIMOKU_CD		-- 分科細目コード
	 *			,SAIMOKU_NAME		-- 細目名
	 *			,BUNKA_CD		-- 分科コード
	 *			,BUNKA_NAME		-- 分科名
	 *			,BUNYA_CD		-- 分野コード
	 *			,BUNYA_NAME		-- 分野名
	 *			,KEI		-- 系
	 *		)
	 *	VALUES 
	 *		(?,?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>分科細目コード</td><td>CSVから取得した分科細目コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>細目名</td><td>CSVから取得した細目名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>分科コード</td><td>CSVから取得した分科コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>分科名</td><td>CSVから取得した分科名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>分野コード</td><td>CSVから取得した分野コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>分野名</td><td>CSVから取得した分野名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>系</td><td>CSVから取得した系</td></tr>
	 *	</table><br/>
	 * 
	 * <br />
	 * <b>7.マスタ管理マスタ更新</b><br />
	 * マスタ管理マスタを以下の情報で更新する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>更新情報</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ種別</td><td>1</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ名称</td><td>"分科細目マスタ"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込み日時</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>件数</td><td>Insertしたレコード数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込みテーブル名</td><td>"MASTER_SAIMOKU"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>新規・更新フラグ</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>処理状況</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取込エラーメッセージ</td><td>レコードINSERTに失敗した場合、エラーメッセージ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSVファイルパス</td><td>CSV格納先フォルダ＋CSVファイル名</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>8.マスタ管理情報返却</b><br />
	 * 7.で更新した情報を呼び出し元へ返却する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param fileRes				アップロードファイルリソース。
	 * @return						処理結果情報
	 * @throws NoDateFoundException	対象データが見つからない場合の例外。
	 * @throws ApplicationException	何らかの例外
	 */
	private MasterKanriInfo torikomiMasterSaimoku(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException {
		
		//2005/04/22 修正 ここから--------------------------------------------
		//理由 カラム数変更のため
		//int columnSize = 7;
		int columnSize = 8;
		//修正 ここまで-------------------------------------------------------
		
		boolean success = false;
		Connection connection = null;

		try{
			//CSVデータをリスト形式に変換する
			List dt = csvFile2List(fileRes);

			//カラム数チェック(1行目のみ)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//項目数が違う場合はエラーを出力し処理を終了する。
					throw new ApplicationException(
								"CSVファイルが分科細目マスタの定義と異なります。",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csvファイル格納
			String table_name = "MASTER_SAIMOKU";
			String csvPath = kakuno(fileRes, table_name);
			
			//DBのエクスポート
			String file_name = "MASTER_SAIMOKU_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//コネクション取得
			connection = DatabaseUtil.getConnection();

			//取込（DELETE → INSERT処理）
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);
			
			int cnt = 0;									//正常取込件数
			ArrayList cd_list  = new ArrayList();			//取込データのキー格納配列(一意制約チェック用)
			ArrayList err_list = new ArrayList();			//取込みエラー文言格納配列
			int dtsize = dt.size();							//取込全件数
			
			//DAO
			MasterSaimokuInfoDao saimokuDao = new MasterSaimokuInfoDao(userInfo);
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);
				
				if(line.size() != columnSize){
					//項目数が違う場合、エラーとして処理を飛ばす。（行数を確保する）
					String msg = i+"行目 分科細目マスタのテーブル定義と一致しません。";
					mtLog.warn(msg);
					//err_list.add(msg);				//エラー内容を格納 → 保留。コメントアウト
					err_list.add(Integer.toString(i));	//エラーの行数を確保
					
				}else{
					//正常処理
					//エラーフラグ
					int err_flg = 0;
					
					//マスタデータの取得
					String bunkasaimoku_cd = (String)line.get(0);		//細目コード
					//2005/04/22 追加 ここから-----------------------------------------
					String bunkatsu_no      = (String)line.get(1);		//分割番号
					//追加 ここまで----------------------------------------------------
					String saimoku_name    = (String)line.get(2);		//細目名
					String bunka_cd        = (String)line.get(3);		//分科コード
					String bunka_name      = (String)line.get(4);		//分科名
					String bunya_cd        = (String)line.get(5);		//分野コード
					String bunya_name      = (String)line.get(6);		//分野名
					String kei             = (String)line.get(7);		//系
										
					//-----分科細目コードチェック
					//数値チェック
					//2005/04/25 追加 ここから------------------------------------------
					//理由 細目コードの必須チェック追加のため
					if(bunkasaimoku_cd == null || bunkasaimoku_cd.equals("")){
						String msg = i+"行目 細目コードは必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
					//追加 ここまで-----------------------------------------------------						
						if(!this.checkNum(bunkasaimoku_cd)){
							//半角数字で無い場合
							String msg = i+"行目 細目コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（4桁で無い場合は、先頭に"0"を加える）
							int cd_length = bunkasaimoku_cd.length();
							if(cd_length == 1){
								bunkasaimoku_cd = "000" + bunkasaimoku_cd;
							}else if(cd_length == 2){
								bunkasaimoku_cd = "00" + bunkasaimoku_cd;
							}else if(cd_length == 3){
								bunkasaimoku_cd = "0" + bunkasaimoku_cd;
							}else if(cd_length == 0 || cd_length > 4){
								String msg = i+"行目 細目コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
							//KEY項目の重複チェック
							//2005/04/25　追加 ここから----------------------------------
							//理由　Key値に分割番号が追加されたため
							if(cd_list.contains(bunkasaimoku_cd + bunkatsu_no)){
								String msg = i+"行目 Key値(細目コード,分割番号)は重複しています。";
							//if(cd_list.contains(bunkasaimoku_cd)){
							//	String msg = i+"行目 細目コードは重複しています。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}else{
								cd_list.add(bunkasaimoku_cd+bunkatsu_no);
							}
						}
					}
					
					//-----細目名チェック
					if(saimoku_name == null || saimoku_name.equals("")){
						String msg = i+"行目 細目名は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(saimoku_name, 60)){
							String msg = i+"行目 細目名の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----分科コード
					//数値チェック
					//2005/04/25 追加 ここから------------------------------------------
					//理由 分科コードの必須チェック追加のため
					if(bunka_cd == null || bunka_cd.equals("") || bunka_cd.equals("\"")){
						//2005.07.29 iso 分科コードは空を許す
//						String msg = i+"行目 分科コードは必須項目です。";
//						mtLog.warn(msg);
//						err_flg = 1;	
					}else{
					//追加 ここまで-----------------------------------------------------
						if(!this.checkNum(bunka_cd)){
							//半角数字で無い場合
							String msg = i+"行目 分科コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（4桁で無い場合は、先頭に"0"を加える）
							int cd_length = bunka_cd.length();
							if(cd_length == 1){
								bunka_cd = "000" + bunka_cd;
							}else if(cd_length == 2){
								bunka_cd = "00" + bunka_cd;
							}else if(cd_length == 3){
								bunka_cd = "0" + bunka_cd;
							}else if(cd_length == 0 || cd_length > 4){
								String msg = i+"行目 分科コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}
					
					//-----分科名チェック
					if(!this.checkLenB(bunka_name, 60)){
						String msg = i+"行目 分科名の長さが不正です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}
					//-----分野コード
					//数値チェック
					if(!this.checkNum(bunya_cd)){
						//半角数字で無い場合
						String msg = i+"行目 分野コードは半角数字ではありません。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//半角数字の場合
						//桁数チェック（4桁で無い場合は、先頭に"0"を加える）
						int cd_length = bunya_cd.length();
						if(cd_length == 1){
							bunya_cd = "000" + bunya_cd;
						}else if(cd_length == 2){
							bunya_cd = "00" + bunya_cd;
						}else if(cd_length == 3){
							bunya_cd = "0" + bunya_cd;
						}else if(cd_length == 0 || cd_length > 4){
							String msg = i+"行目 分野コードの桁数が不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----分野名チェック
					if(!this.checkLenB(bunya_name, 60)){
						String msg = i+"行目 分野名の長さが不正です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}
					//-----系チェック
					if(!this.checkLenB(kei, 1)){
						String msg = i+"行目 系の長さが不正です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}
					
					//2005/04/22 追加 ここから-----------------------------------------
					//分割番号の追加
					//----分割番号チェック
					//2005.08.15 iso 分割番号が空の場合も「-」にするよう変更
					if(bunkatsu_no == null|| bunkatsu_no.equals("")){
//						String msg = i+"行目 分割番号は必須項目です。";
//						mtLog.warn(msg);
//						err_flg = 1;
						bunkatsu_no = "-";
					}else{
						if(!this.checkLenB(bunkatsu_no, 1)){
							String msg = i+"行目 分割番号の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						
						//<!-- UPDATE　START 2007/07/21 BIS 張楠 -->
						/*古いコード
						//分割番号が1,2,A,B以外の場合は"-"で登録する
						if(bunkatsu_no != null && !bunkatsu_no.equals("1") && !bunkatsu_no.equals("2") 
							&& !bunkatsu_no.equals("A") && !bunkatsu_no.equals("B")){
								bunkatsu_no = "-";
						}
						*/
						//分割番号が1,2,3,4,5,A,B以外の場合は"-"で登録する
						if(bunkatsu_no != null && !bunkatsu_no.equals("1") && !bunkatsu_no.equals("2") 
								&& !bunkatsu_no.equals("3") && !bunkatsu_no.equals("4") && !bunkatsu_no.equals("5") 
								&& !bunkatsu_no.equals("A") && !bunkatsu_no.equals("B")){
									bunkatsu_no = "-";
							}
						//<!-- UPDATE　END 2007/07/21 BIS 張楠 -->
					}
					//追加 ここまで----------------------------------------------------					
					
					//エラーが無かった場合のみ登録する
					if(err_flg == 0){
						//DBに登録する
						SaimokuInfo info = new SaimokuInfo();
						info.setBunkaSaimokuCd(bunkasaimoku_cd);
						info.setSaimokuName(saimoku_name);
						info.setBunkaCd(bunka_cd);
						info.setBunkaName(bunka_name);
						info.setBunyaCd(bunya_cd);
						info.setBunyaName(bunya_name);
						info.setKei(kei);
						//2005/04/22 追加 ここから-----------------------------------------
						//分割番号の追加
						info.setBunkatsuNo(bunkatsu_no);
						//追加 ここまで----------------------------------------------------
												
						saimokuDao = new MasterSaimokuInfoDao(userInfo);
						saimokuDao.insertSaimokuInfo(connection, info);
						cnt++;	//取り込み件数をカウント
					}else{
						//データに不備があるため、登録を行わない（行数を確保する）
						err_list.add(Integer.toString(i));
					}
				}		
			}
			
			//マスタ管理テーブルの更新
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_SAIMOKU);
			mkInfo.setMasterName("分科細目マスタ");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);			//使用してない？		
			mkInfo.setImportErrMsg(err_list);	//エラー情報（画面で使用）
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;
			
		} catch (IOException e) {
			throw new ApplicationException(
						"CSVファイルの読込中にエラーが発生しました。",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"マスタ取込中にDBエラーが発生しました。",
						new ErrorInfo("errors.4001"),
						e);
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
				throw new ApplicationException(
					"マスタ取込中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	/**
	 * 所属機関マスタ取込.<br />
	 * 所属機関マスタ情報を取得する。<br />
	 * <b>1.CSVデータをリスト形式に変換</b><br />
	 * 引数で渡されたfileResをList形式へ変換する。Listの要素としてさらにListを持ち、
	 * その要素にCSV情報の一レコード分の情報を格納する。
	 * <br /><br />
	 * <b>2.カラム数チェック</b><br />
	 * 1.で作成したListの一つ目の要素のListの要素数が、9であるか確認する。それ以外の場合は、例外をthrowする。
	 * <br /><br />
	 * <b>3.csvファイル格納</b><br />
	 * 自クラスのkakuno()メソッドを使用してCSVファイルをサーバへ格納する。
	 * <br /><br />
	 * <b>4.マスタエクスポート</b><br />
	 * 所属機関マスタをバックアップ用にエクスポートする。エクスポートは、ApplicationSettings.propertiesの、
	 * EXPORT_COMMANDで定義されているコマンドを実行することによって行われる。<br />
	 * 出力されるダンプファイルのファイル名は以下の通り。<br />
	 * MASTER_KIKAN_yyyyMMddHHmmssSSS.DMP
	 * <br />
	 * <br />
	 * <b>5.所属機関情報削除</b><br />
	 * 所属機関情報を、以下のSQLを実行することによって削除する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM MASTER_KIKAN
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * <br />
	 * <b>6.情報取込</b><br />
	 * 1.で作成したCSVファイル情報Listを所属機関マスタへINSERTする。
	 * Listに格納されている要素Listには以下の情報が格納されている。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>要素番号</td><td>テーブル列名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>機関種別コード（CSVでは機関区分）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>機関区分（CSVでは機関種別）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>機関名称(日本語)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>機関略称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>郵便番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>住所１</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>住所２</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>備考</td></tr>
	 *	</table><br />
	 * 
	 * 各列の情報は、桁数チェック、型チェックおよび一意キー制約チェックを行い、問題がなければINSERT処理を行う。
	 * 問題があった場合は、エラー情報を管理するListに格納したのち、次の情報へ処理を進める。
	 * なお、機関コード、郵便番号は、各チェックとともに左0埋め処理を行う。
	 * INSERTする際に実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		MASTER_KIKAN(
	 *			 SHUBETU_CD		-- 機関種別コード
	 *			,KIKAN_KUBUN		-- 機関区分
	 *			,SHOZOKU_CD		-- 機関コード
	 *			,SHOZOKU_NAME_KANJI		-- 機関名称
	 *			,SHOZOKU_RYAKUSHO		-- 機関名略称
	 *			,SHOZOKU_NAME_EIGO		-- 機関名称（英語）
	 *			,SHOZOKU_ZIP		-- 郵便番号
	 *			,SHOZOKU_ADDRESS1		-- 住所1
	 *			,SHOZOKU_ADDRESS2		-- 住所2
	 *			,SHOZOKU_TEL		-- 電話番号
	 *			,SHOZOKU_FAX		-- FAX
	 *			,SHOZOKU_DAIHYO_NAME		-- 代表者氏名
	 *			,BIKO		-- 備考
	 *		)
	 *	VALUES
	 *		(?,?,?,?,?,?,?,?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>機関種別コード</td><td>CSVから取得した機関種別コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>機関区分</td><td>CSVから取得した機関区分</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>機関コード</td><td>CSVから取得した機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>機関名称</td><td>CSVから取得した機関名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>機関名略称</td><td>CSVから取得した機関略称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>機関名称（英語）</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>郵便番号</td><td>CSVから取得した郵便番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>住所1</td><td>CSVから取得した住所1</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>住所2</td><td>CSVから取得した住所2</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>電話番号</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>FAX</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>代表者氏名</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>備考</td><td>CSVから取得した備考</td></tr>
	 *	</table><br/>
	 * INSERTが成功したら、以下の二つのSQLを実行し、所属担当者情報と申請者情報を更新する。
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	UPDATE
	 *		SHOZOKUTANTOINFO
	 *	SET
	 *		 SHOZOKU_NAME_KANJI = ?		-- 機関名（和文）
	 *		,SHOZOKU_RYAKUSHO = ?		-- 機関名（略称）
	 *	WHERE
	 *		SHOZOKU_CD = ?	-- 機関コード
	 *		AND (
	 *			SHOZOKU_NAME_KANJI <> ?	-- 機関名（和文）が違う
	 *			OR SHOZOKU_RYAKUSHO <> ?	-- 機関名（略称）が違う
	 *		)
	 *	/
	 *	UPDATE
	 *		SHINSEISHAINFO
	 *	SET
	 *		 SHOZOKU_NAME = ?	-- 機関名（和文）
	 *		,SHOZOKU_NAME_RYAKU = ?		--機関名（略称）
	 *	WHERE"
	 *		SHOZOKU_CD = ?	-- 機関コード
	 *		AND (
	 *			SHOZOKU_NAME <> ?	-- 機関名（和文）が違う
	 *			OR SHOZOKU_NAME_RYAKU <> ?	-- 機関名（略称）が違う
	 *		 )
	 *	/
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">※機関コード、機関名（和文）および機関名（略称）はCSVの情報を使用する。</div><br />
	 *	<br />
	 * 
	 * <br />
	 * <b>7.マスタ管理マスタ更新</b><br />
	 * マスタ管理マスタを以下の情報で更新する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>更新情報</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ種別</td><td>2</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ名称</td><td>"所属機関マスタ"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込み日時</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>件数</td><td>Insertしたレコード数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込みテーブル名</td><td>"MASTER_KIKAN"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>新規・更新フラグ</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>処理状況</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取込エラーメッセージ</td><td>レコードINSERTに失敗した場合、エラーメッセージ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSVファイルパス</td><td>CSV格納先フォルダ＋CSVファイル名</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>8.マスタ管理情報返却</b><br />
	 * 7.で更新した情報を呼び出し元へ返却する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param fileRes				アップロードファイルリソース。
	 * @return						処理結果情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	private MasterKanriInfo torikomiMasterKikan(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException {

		int columnSize = 9;
		
		boolean success = false;
		Connection connection = null;
		
		try{
			//CSVデータをリスト形式に変換する
			List dt = csvFile2List(fileRes);
			
			//カラム数チェック(1行目のみ)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//項目数が違う場合、エラーを出力し処理を終了する。
					throw new ApplicationException(
								"CSVファイルが所属機関マスタの定義と異なります。",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csvファイル格納
			String table_name = "MASTER_KIKAN";
			String csvPath = kakuno(fileRes, table_name);
			
			//DBのエクスポート
			String file_name = "MASTER_KIKAN_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//コネクション取得
			connection = DatabaseUtil.getConnection();

			//取込（DELETE → INSERT処理）
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);
			
			int cnt = 0;											//正常取込件数
			ArrayList cd_list  = new ArrayList();					//取込データのキー格納配列(一意制約チェック用)
			ArrayList err_list = new ArrayList();					//取込みエラー行格納配列
			int dtsize = dt.size();									//取込全件数
			
			//DAO
			MasterKikanInfoDao kikanDao      = new MasterKikanInfoDao(userInfo);
			ShozokuInfoDao     shozokuDao    = new ShozokuInfoDao(userInfo);
			ShinseishaInfoDao  shinseishaDao = new ShinseishaInfoDao(userInfo);
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//項目数が違う場合はエラーとして処理を飛ばす。（行数を確保する）
					String msg = i+"行目 機関マスタのテーブル定義と一致しません。";
					mtLog.warn(msg);
					//err_list.add(msg);				//エラー内容を格納 → 保留。コメントアウト
					err_list.add(Integer.toString(i));	//エラーの行数を確保
					
				}else{
					//正常処理
					//エラーフラグ
					int err_flg = 0;
					
					//マスタデータの取得
					//※機関名称(英語)、電話番号、FAX番号、代表者名はCSVに存在しない。
					String shubetu_cd         = (String)line.get(0);	//機関種別コード（CSVでは機関区分）
					String kikan_kubun        = (String)line.get(1);	//機関区分（CSVでは機関種別）
					String shozoku_cd         = (String)line.get(2);	//機関コード
					String shozoku_name_kanji = (String)line.get(3);	//機関名称(日本語)
					String shozoku_ryakusho   = (String)line.get(4);	//機関略称
					String shozoku_zip        = (String)line.get(5);	//郵便番号
					String shozoku_address1   = (String)line.get(6);	//住所１
					String shozoku_address2   = (String)line.get(7);	//住所２
					String shozoku_biko       = (String)line.get(8);	//備考
					
					//-----機関種別コード（CSVでは機関区分）コードチェック
					if(shubetu_cd == null || shubetu_cd.equals("")){
						String msg = i+"行目 機関区分（DBでは機関種別）は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//数値チェック
						if(!this.checkNum(shubetu_cd)){
							//半角数字で無い場合
							String msg = i+"行目 機関区分（DBでは機関種別）は半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(shubetu_cd, 1)){
							String msg = i+"行目 機関区分（DBでは機関種別）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----機関区分（CSVでは機関種別）チェック
					//数値チェック
					if(!this.checkNum(kikan_kubun)){
						//半角数字で無い場合
						String msg = i+"行目 機関種別（DBでは機関区分）は半角数字ではありません。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(kikan_kubun, 1)){
							String msg = i+"行目 機関種別（DBでは機関区分）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----機関コードチェック
					//数値チェック
					if(!this.checkNum(shozoku_cd)){
						//半角数字で無い場合
						String msg = i+"行目 機関コードは半角数字ではありません。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//半角数字の場合
						//桁数チェック（5桁で無い場合は、先頭に"0"を加える）
						int cd_length = shozoku_cd.length();
						if(cd_length == 1){
							shozoku_cd = "0000" + shozoku_cd;
						}else if(cd_length == 2){
							shozoku_cd = "000" + shozoku_cd;
						}else if(cd_length == 3){
							shozoku_cd = "00" + shozoku_cd;
						}else if(cd_length == 4){
							shozoku_cd = "0" + shozoku_cd;
						}else if(cd_length == 0 || cd_length > 5){
							String msg = i+"行目 機関コードの桁数が不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						//KEY項目の重複チェック
						if(cd_list.contains(shozoku_cd)){
							String msg = i+"行目 機関コードは重複しています。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							cd_list.add(shozoku_cd);
						}
					}
					//-----機関名称(日本語)チェック
					if(shozoku_name_kanji == null || shozoku_name_kanji.equals("")){
						String msg = i+"行目 機関名称(日本語)は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//1005.07.29 iso 制限を変更
//						if(!this.checkLenB(shozoku_name_kanji, 50)){
						if(!this.checkLenB(shozoku_name_kanji, 80)){
							String msg = i+"行目 機関名称(日本語)の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----機関略称チェック
					if(shozoku_ryakusho == null || shozoku_ryakusho.equals("")){
						String msg = i+"行目 機関略称は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(shozoku_ryakusho, 20)){
							String msg = i+"行目 機関略称の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----郵便番号チェック
					//数値チェック
					if(!this.checkNum(shozoku_zip)){
						//半角数字で無い場合
						String msg = i+"行目 郵便番号は半角数字ではありません。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//半角数字の場合
						//桁数チェック（7桁で無い場合は、先頭に"0"を加える）
						int cd_length = shozoku_zip.length();
						if(cd_length == 1){
							shozoku_zip = "000000" + shozoku_zip;
						}else if(cd_length == 2){
							shozoku_zip = "00000" + shozoku_zip;
						}else if(cd_length == 3){
							shozoku_zip = "0000" + shozoku_zip;
						}else if(cd_length == 4){
							shozoku_zip = "000" + shozoku_zip;
						}else if(cd_length == 5){
							shozoku_zip = "00" + shozoku_zip;
						}else if(cd_length == 6){
							shozoku_zip = "0" + shozoku_zip;
						}else if(cd_length == 0 || cd_length > 7){
							String msg = i+"行目 郵便番号の桁数が不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}					
						//4桁目にハイフンを挿入する
						shozoku_zip = shozoku_zip.substring(0,3)
									  + "-"
									  + shozoku_zip.substring(3);
					}
					//-----住所1チェック
					if(shozoku_address1 != null && !shozoku_address1.equals("")){
						if(!this.checkLenB(shozoku_address1, 50)){
							String msg = i+"行目 住所1の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----住所2チェック
					if(shozoku_address2 != null && !shozoku_address2.equals("")){
						if(!this.checkLenB(shozoku_address2, 50)){
							String msg = i+"行目 住所2の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----備考チェック
					if(shozoku_biko != null && !shozoku_biko.equals("")){
						if(!this.checkLenB(shozoku_biko, 200)){
							String msg = i+"行目 備考の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					
					//2007/4/27 仕様変更より追加
					//機関種別と機関区分は公開区分マスタに存在しない場合エラーとする
					if (err_flg == 0){
						int kensu = kikanDao.checkKokaiKubun(connection, shubetu_cd, kikan_kubun);
						if (kensu == 0){
							String msg = i+"行目 機関区分と機関種別の組み合わせが「公開区分マスタ」に存在しません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//2007/4/27 仕様変更より追加完了
					
					//エラーが無かった場合のみ登録する
					if(err_flg == 0){
						//DBに登録する
						KikanInfo info = new KikanInfo();
						info.setShubetuCd(shubetu_cd);
						info.setKikanKubun(kikan_kubun);
						info.setShozokuCd(shozoku_cd);
						info.setShozokuNameKanji(shozoku_name_kanji);
						info.setShozokuRyakusho(shozoku_ryakusho);
						info.setShozokuZip(shozoku_zip);
						info.setShozokuAddress1(shozoku_address1);
						info.setShozokuAddress2(shozoku_address2);
						info.setBiko(shozoku_biko);
						kikanDao.insertMasterKikan(connection, info);
						
						//所属担当者の属性情報を更新（機関名（和文）、機関名（略称））
						shozokuDao.updateShozokuInfo(connection, info);			
						//申請者の属性情報を更新（機関名（和文）、機関名（略称））
						shinseishaDao.updateShinseishaInfo(connection, info);
						
						cnt++;	//取り込み件数をカウント
					}else{
						//データに不備があるため、登録を行わない（行数を確保する）
						err_list.add(Integer.toString(i));
					}
						
				}
				
			}
			
			//マスタ管理テーブルの更新
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_KIKAN);
			mkInfo.setMasterName("所属機関マスタ");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);			//使用してない？		
			mkInfo.setImportErrMsg(err_list);	//エラー情報（画面で使用）
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;
						
		} catch (IOException e) {
			throw new ApplicationException(
						"CSVファイルの読込中にエラーが発生しました。",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"マスタ取込中にDBエラーが発生しました。",
						new ErrorInfo("errors.4001"),
						e);
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
				throw new ApplicationException(
					"マスタ取込中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	/**
	 * 部局マスタ取込.<br />
	 * 部局マスタ情報を取得する。<br />
	 * <b>1.CSVデータをリスト形式に変換</b><br />
	 * 引数で渡されたfileResをList形式へ変換する。Listの要素としてさらにListを持ち、
	 * その要素にCSV情報の一レコード分の情報を格納する。
	 * <br /><br />
	 * <b>2.カラム数チェック</b><br />
	 * 1.で作成したListの一つ目の要素のListの要素数が、6であるか確認する。それ以外の場合は、例外をthrowする。
	 * <br /><br />
	 * <b>3.csvファイル格納</b><br />
	 * 自クラスのkakuno()メソッドを使用してCSVファイルをサーバへ格納する。
	 * <br /><br />
	 * <b>4.マスタエクスポート</b><br />
	 * 部局マスタをバックアップ用にエクスポートする。エクスポートは、ApplicationSettings.propertiesの、
	 * EXPORT_COMMANDで定義されているコマンドを実行することによって行われる。<br />
	 * 出力されるダンプファイルのファイル名は以下の通り。<br />
	 * MASTER_BUKYOKU_yyyyMMddHHmmssSSS.DMP
	 * <br /><br />
	 * <b>5.部局情報削除</b><br />
	 * 部局情報を、以下のSQLを実行することによって削除する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM MASTER_BUKYOKU
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br /><br />
	 * <b>6.情報取込</b><br />
	 * 1.で作成したCSVファイル情報Listを部局マスタへINSERTする。
	 * Listに格納されている要素Listには、以下の情報が格納されている。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>要素番号</td><td>テーブル列名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>部局コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>部科名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>部科略称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>カテゴリ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>ソート番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>備考</td></tr>
	 *	</table><br />
	 * 
	 * 各列の情報は、桁数チェック、型チェックおよび一意キー制約チェックを行い、問題がなければINSERT処理を行う。
	 * 問題があった場合は、エラー情報を管理するListに格納したのち、次の情報へ処理を進める。
	 * なお、部局コードは、各チェックとともに左0埋め処理を行う。
	 * INSERTする際に実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		MASTER_BUKYOKU(
	 *			 BUKYOKU_CD		-- 部局コード
	 *			,BUKA_NAME		-- 部科名称
	 *			,BUKA_RYAKUSHO		-- 部科略称
	 *			,BUKA_KATEGORI		-- カテゴリ
	 *			,SORT_NO		-- ソート番号
	 *			,BIKO		-- 備考
	 *		)
	 *	VALUES
	 *		(?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>部局コード</td><td>CSVから取得した部局コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>部科名称</td><td>CSVから取得した部科名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>部科略称</td><td>CSVから取得した部科略称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>カテゴリ</td><td>CSVから取得したカテゴリ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ソート番号</td><td>CSVから取得したソート番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>備考</td><td>CSVから取得した備考</td></tr>
	 *	</table>
	 * <br /><br />
	 * <b>7.マスタ管理マスタ更新</b><br />
	 * マスタ管理マスタを以下の情報で更新する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>更新情報</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ種別</td><td>4</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ名称</td><td>"部局マスタ"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込み日時</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>件数</td><td>Insertしたレコード数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込みテーブル名</td><td>"MASTER_BUKYOKU"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>新規・更新フラグ</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>処理状況</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取込エラーメッセージ</td><td>レコードINSERTに失敗した場合、エラーメッセージ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSVファイルパス</td><td>CSV格納先フォルダ＋CSVファイル名</td></tr>
	 *	</table>
	 * <br /><br />
	 * <b>8.マスタ管理情報返却</b><br />
	 * 7.で更新した情報を呼び出し元へ返却する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param fileRes				アップロードファイルリソース。
	 * @return						処理結果情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	private MasterKanriInfo torikomiMasterBukyoku(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException {

		int columnSize = 6;
		
		boolean success = false;
		Connection connection = null;

		try{
			//CSVデータをリスト形式に変換する
			List dt = csvFile2List(fileRes);
			
			//カラム数チェック(1行目のみ)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				
				if(line1.size() != columnSize){
					//項目数が違う場合、エラーを出力し処理を終了する。
					throw new ApplicationException(
								"CSVファイルが部局マスタの定義と異なります。",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csvファイル格納
			String table_name = "MASTER_BUKYOKU";
			String csvPath = kakuno(fileRes, table_name);
			
			//DBのエクスポート
			String file_name = "MASTER_BUKYOKU_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//コネクション取得
			connection = DatabaseUtil.getConnection();

			//取込（DELETE → INSERT処理）
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);
			
			int cnt = 0;													//正常取込件数
			ArrayList cd_list  = new ArrayList();							//取込データのキー格納配列(一意制約チェック用)
			ArrayList err_list = new ArrayList();							//取込みエラー行格納配列
			int dtsize = dt.size();											//取込全件数
			
			//DAO
			MasterBukyokuInfoDao bukyokuDao = new MasterBukyokuInfoDao(userInfo);
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//項目数が違う場合はエラーとして処理を飛ばす。（行数を確保する）
					String msg = i+"行目 所属(部局)マスタのテーブル定義と一致しません。";
					mtLog.warn(msg);
					//err_list.add(msg);				//エラー内容を格納 → 保留。コメントアウト
					err_list.add(Integer.toString(i));	//エラーの行数を確保
					
				}else{
					//正常処理
					//エラーフラグ
					int err_flg = 0;
					
					//マスタデータの取得
					String bukyoku_cd    = (String)line.get(0);		//部局コード
					String buka_name     = (String)line.get(1);		//部科名称
					String buka_ryakusho = (String)line.get(2);		//部科略称
					String buka_kategori = (String)line.get(3);		//カテゴリ
					String sort_no       = (String)line.get(4);		//ソート番号
					String biko          = (String)line.get(5);		//備考
										
					//-----部局コードチェック
					//数値チェック
					if(!this.checkNum(bukyoku_cd)){
						//半角数字で無い場合
						String msg = i+"行目 部局コードは半角数字ではありません。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//半角数字の場合
						//桁数チェック（3桁で無い場合は、先頭に"0"を加える）
						int cd_length = bukyoku_cd.length();
						if(cd_length == 1){
							bukyoku_cd = "00" + bukyoku_cd;
						}else if(cd_length == 2){
							bukyoku_cd = "0" + bukyoku_cd;
						}else if(cd_length == 0 || cd_length > 3){
							String msg = i+"行目 部局コードの桁数が不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						//KEY項目の重複チェック
						if(cd_list.contains(bukyoku_cd)){
							String msg = i+"行目 部局コードは重複しています。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							cd_list.add(bukyoku_cd);
						}
					}
					//-----部科名称チェック
					if(buka_name == null || buka_name.equals("")){
						String msg = i+"行目 部科名称は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(buka_name, 30)){
							String msg = i+"行目 部科名称の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----部科略称チェック
					if(buka_ryakusho == null || buka_ryakusho.equals("")){
						String msg = i+"行目 部科略称は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(buka_ryakusho, 8)){
							String msg = i+"行目 部科略称の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----カテゴリチェック
					if(buka_kategori == null || buka_kategori.equals("")){
						String msg = i+"行目 カテゴリは必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//数値チェック
						if(!this.checkNum(buka_kategori)){
							//半角数字で無い場合
							String msg = i+"行目 カテゴリは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（2桁で無い場合は、先頭に"0"を加える）
							int cd_length = buka_kategori.length();
							if(cd_length == 1){
								buka_kategori = "0" + buka_kategori;
							}else if(cd_length == 0 || cd_length > 2){
								String msg = i+"行目 カテゴリの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}					
					}
					//-----ソート番号チェック
					if(sort_no != null && !sort_no.equals("")){
						//数値チェック
						if(!this.checkNum(sort_no)){
							//半角数字で無い場合
							String msg = i+"行目 ソート番号は半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							if(!this.checkLenB(sort_no, 4)){
								String msg = i+"行目 ソート番号の長さが不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}					
					}					
					//-----備考チェック
					if(biko != null && !biko.equals("")){
						if(!this.checkLenB(biko, 200)){
							String msg = i+"行目 備考の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					

					
					//エラーが無かった場合のみ登録する
					if(err_flg == 0){
						//DBに登録する
						BukyokuInfo info = new BukyokuInfo();
						info.setBukyokuCd(bukyoku_cd);
						info.setBukaName(buka_name);
						info.setBukaRyakusyo(buka_ryakusho);
						info.setBukaKategori(buka_kategori);
						info.setSortNo(sort_no);
						info.setBiko(biko);
						bukyokuDao.insertBukyokuInfo(connection, info);
						cnt++;	//取り込み件数をカウント
					}else{
						//データに不備があるため、登録を行わない（行数を確保する）
						err_list.add(Integer.toString(i));
					}
						
				}
				
			}

			//マスタ管理テーブルの更新
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_BUKYOKU);
			mkInfo.setMasterName("部局マスタ");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);			//使用してない？		
			mkInfo.setImportErrMsg(err_list);	//エラー情報（画面で使用）
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;
			
		} catch (IOException e) {
			throw new ApplicationException(
						"CSVファイルの読込中にエラーが発生しました。",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"マスタ取込中にDBエラーが発生しました。",
						new ErrorInfo("errors.4001"),
						e);
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
				throw new ApplicationException(
					"マスタ取込中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	/**
	 * 学創用審査員マスタ取込.<br />
	 * 学創用審査員マスタ情報を取得する。<br />
	 * <b>1.CSVデータをリスト形式に変換</b><br />
	 * 引数で渡されたfileResをList形式へ変換する。Listの要素としてさらにListを持ち、
	 * その要素にCSV情報の一レコード分の情報を格納する。
	 * <br /><br />
	 * <b>2.カラム数チェック</b><br />
	 * 1.で作成したListの一つ目の要素のListの要素数が、17であるか確認する。それ以外の場合は、例外をthrowする。
	 * <br /><br />
	 * <b>3.csvファイル格納</b><br />
	 * 自クラスのkakuno()メソッドを使用してCSVファイルをサーバへ格納する。
	 * <br /><br />
	 * <b>4.マスタエクスポート</b><br />
	 * 学創用審査員マスタをバックアップ用にエクスポートする。エクスポートは、ApplicationSettings.propertiesの、
	 * EXPORT_COMMANDで定義されているコマンドを実行することによって行われる。<br />
	 * 出力されるダンプファイルのファイル名は以下の通り。<br />
	 * MASTER_SHINSAIN_GAKUSOU_KOSHIN_yyyyMMddHHmmssSSS.DMP(更新)<br />
	 * MASTER_SHINSAIN_GAKUSOU_SHINKI_yyyyMMddHHmmssSSS.DMP(新規)<br />
	 * <br />
	 * <br />
	 * <b>5.学創用審査員情報削除</b><br />
	 * 審査員マスタを、以下のSQLを実行することによって削除する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		MASTER_SHINSAIN
	 *	WHERE
	 *		JIGYO_KUBUN = 1 -- 事業区分（学創推薦）
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * 第三引数がtrueの場合は、続いて審査員情報を以下のSQLにて削除する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		SHINSAININFO
	 *	WHERE
	 *		WHERE SUBSTR(SHINSAIN_ID,3,1) = 1 -- 事業区分（学創推薦）
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * <br />
	 * <b>6.情報取込</b><br />
	 * 1.で作成したCSVファイル情報Listを審査員マスタへINSERTする。
	 * Listに格納されている要素Listには以下の情報が格納されている。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>要素番号</td><td>テーブル列名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>審査員番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>氏名（姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>氏名（名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>氏名フリガナ（姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>氏名フリガナ（名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>機関名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>部局名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>職種</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>9</td><td>送付先郵便番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>10</td><td>送付先住所</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>11</td><td>送付先Email</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>12</td><td>電話番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>13</td><td>FAX番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>14</td><td>URL</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>15</td><td>専門分野</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>16</td><td>備考</td></tr>
	 *	</table><br />
	 * 
	 * 各列の情報は、桁数チェック、型チェックおよび一意キー制約チェックを行い、問題がなければINSERT処理を行う。
	 * INSERTする際、事業区分は1（学創推薦）とする。
	 * 問題があった場合は、エラー情報を管理するListに格納したのち、次の情報へ処理を進める。
	 * なお、審査員番号、機関コードは、各チェックとともに左0埋め処理を行う。
	 * INSERTする際に実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		MASTER_SHINSAIN(
	 *			 SHINSAIN_NO		-- 審査員番号
	 *			,JIGYO_KUBUN		-- 事業区分
	 *			,NAME_KANJI_SEI		-- 氏名（姓）
	 *			,NAME_KANJI_MEI		-- 氏名（名）
	 *			,NAME_KANA_SEI		-- 氏名フリガナ（姓）
	 *			,NAME_KANA_MEI		-- 氏名フリガナ（名）
	 *			,SHOZOKU_CD		-- 所属機関コード
	 *			,SHOZOKU_NAME		-- 機関名称
	 *			,BUKYOKU_NAME		-- 部局名称
	 *			,SHOKUSHU_NAME		-- 職種名称
	 *			,SOFU_ZIP		-- 送付先郵便番号
	 *			,SOFU_ZIPADDRESS		-- 送付先住所
	 *			,SOFU_ZIPEMAIL		-- 送付先Email
	 *			,SHOZOKU_TEL		-- 電話番号
	 *			,SHOZOKU_FAX		-- FAX
	 *			,URL		-- URL
	 *			,SENMON		-- 専門分野
	 *			,KOSHIN_DATE		-- 更新日
	 *			,BIKO		-- 備考
	 *		)
	 *	VALUES
	 *		(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>CSVから取得した審査員番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>1</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（姓）</td><td>CSVから取得した氏名（姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（名）</td><td>CSVから取得した氏名（名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名フリガナ（姓）</td><td>CSVから取得した氏名フリガナ（姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名フリガナ（名）</td><td>CSVから取得した氏名フリガナ（名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>CSVから取得した所属機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>機関名称</td><td>CSVから取得した機関名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>部局名称</td><td>CSVから取得した部局名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>職種名称</td><td>CSVから取得した職種名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>送付先郵便番号</td><td>CSVから取得した送付先郵便番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>送付先住所</td><td>CSVから取得した送付先住所</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>送付先Email</td><td>CSVから取得した送付先Email</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>電話番号</td><td>CSVから取得した電話番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>FAX</td><td>CSVから取得したFAX</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>URL</td><td>CSVから取得したURL</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>専門分野</td><td>CSVから取得した専門分野</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>更新日</td><td>WASの現在日付</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>備考</td><td>CSVから取得した備考</td></tr>
	 *	</table><br/>
	 * 審査員マスタへINSERTした審査員情報が、審査員情報テーブルに存在しない場合は、同時にINSERTを行う。
	 * INSERTする際に実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		SHINSAININFO(
	 *			 SHINSAIN_ID	-- 審査員ID
	 *			,PASSWORD		-- パスワード
	 *			,YUKO_DATE		-- 有効期限
	 *			,DEL_FLG		-- 削除フラグ
	 *		)
	 *	VALUES
	 *		(?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員ID</td><td>年度＋1＋審査員番号+チェックデジット</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>パスワード</td><td>ＩＤパスワード発行ルールによって作成</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>有効期限</td><td>ＩＤパスワード発行ルールテーブルから取得</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>0</td></tr>
	 *	</table><br/>
	 * <br />
	 * <b>7.マスタ管理マスタ更新</b><br />
	 * マスタ管理マスタを以下の情報で更新する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>更新情報</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ種別</td><td>5</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ名称</td><td>"審査員マスタ（学術創成用）"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込み日時</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>件数</td><td>Insertしたレコード数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込みテーブル名</td><td>"MASTER_SHINSAIN_GAKUSOU"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>新規・更新フラグ</td><td>0:新規、1:更新</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>処理状況</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取込エラーメッセージ</td><td>レコードINSERTに失敗した場合、エラーメッセージ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSVファイルパス</td><td>CSV格納先フォルダ＋CSVファイル名</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>8.マスタ管理情報返却</b><br />
	 * 7.で更新した情報を呼び出し元へ返却する。
	 * 
	 * @param userInfo		UserInfo
	 * @param fileRes		CSVファイル情報
	 * @param koshinFlg		true:更新取り込み、false:新規取り込み
	 * @return	学創用審査員マスタのマスタ管理情報
	 * @throws ApplicationException	何らかの例外
	 */
	private MasterKanriInfo torikomiMasterShinsainGakusou(UserInfo     userInfo,
														  FileResource fileRes,
														  boolean     koshinFlg)
		throws ApplicationException
	{
		int columnSize = 17;
		
		boolean success = false;
		Connection connection = null;

		try{
			//CSVデータをリスト形式に変換する
			List dt = csvFile2List(fileRes);

			//カラム数チェック(1行目のみ)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//項目数が違う場合はエラーを出力し処理を終了する。
					throw new ApplicationException(
								"CSVファイルが審査員マスタ（学創）の定義と異なります。",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csvファイル格納
			String tableName = "MASTER_SHINSAIN_GAKUSOU";
			String csvPath = kakuno(fileRes, tableName);
			
			//DBのエクスポート
			String footer = (koshinFlg ? "_KOSHIN_" : "_SHINKI_"); 
			String table_name = "MASTER_SHINSAIN,SHINSAININFO";
			String file_name = tableName + footer + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//コネクション取得
			connection = DatabaseUtil.getConnection();

			//有効期限の取得
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSAIN);
			RuleInfoDao ruledao = new RuleInfoDao(userInfo);
			RuleInfo rinfo = ruledao.selectRuleInfo(connection, rulePk);
//			String yukoDate = new SimpleDateFormat("yyyyMMdd").format(rinfo.getYukoDate());

			//取込（DELETE → INSERT処理）（事業区分が学創[1]のみ）
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection,
							 "MASTER_SHINSAIN", 
							 "WHERE JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
			if(!koshinFlg){
				//「新規」のときは審査員情報テーブルも削除（事業区分が学創[1]のみ）
				dao.deleteMaster(connection, 
								 "SHINSAININFO",
								 "WHERE SUBSTR(SHINSAIN_ID,3,1) = " + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
			}
			
			int cnt = 0;												//正常取込件数
			ArrayList cd_list  = new ArrayList();						//取込データのキー格納配列(一意制約チェック用)
			ArrayList err_list = new ArrayList();						//取込みエラー行格納配列
			int dtsize = dt.size();										//取込全件数
			Map kikanMap = new HashMap();								//機関コードと機関名称のMap
			DateUtil nowDateUtil = new DateUtil(new Date());			//現時点での日時情報
			String strNendo = nowDateUtil.getYearYY();					//現時点での年度（西暦年の下２桁） 
			
			//DAO
			ShinsainInfoDao shinsainDao = new ShinsainInfoDao(userInfo);
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//項目数が違う場合はエラーとして処理を飛ばす。（行数を確保する）
					String msg = i+"行目 審査員マスタ（学創）のテーブル定義と一致しません。";
					mtLog.warn(msg);
					//err_list.add(msg);				//エラー内容を格納 → 保留。コメントアウト
					err_list.add(Integer.toString(i));	//エラーの行数を確保
					
				}else{
					//正常処理
					//エラーフラグ
					int err_flg = 0;
					
					//マスタデータの取得
					int j = 0;
					String shinsain_no        = (String)line.get(j++);	//審査員番号
					String shimei_kanji_sei   = (String)line.get(j++);	//氏名（姓）
					String shimei_kanji_mei   = (String)line.get(j++);	//氏名（名）
					String shimei_kana_sei    = (String)line.get(j++);	//氏名フリガナ（姓）
					String shimei_kana_mei    = (String)line.get(j++);	//氏名フリガナ（名）
					String kikan_cd           = (String)line.get(j++);	//機関コード
					String kikan_name         = (String)line.get(j++);	//機関名
					String bukyoku_name       = (String)line.get(j++);	//部局名
					String shokushu           = (String)line.get(j++);	//職種
					String sofusaki_zip       = (String)line.get(j++);	//送付先郵便番号
					String sofusaki_address   = (String)line.get(j++);	//送付先住所
					String sofusaki_emai      = (String)line.get(j++);	//送付先Email
					String tel                = (String)line.get(j++);	//電話番号
					String fax                = (String)line.get(j++);	//FAX番号
					String url                = (String)line.get(j++);	//URL
					String senmonbunya        = (String)line.get(j++);	//専門分野
					String biko               = (String)line.get(j++);	//備考
					
					//-----審査員番号チェック
					//数値チェック
					if(!this.checkNum(shinsain_no)){
						//半角数字で無い場合
						String msg = i+"行目 審査員番号は半角数字ではありません。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//半角数字の場合
						//桁数チェック（7桁で無い場合は、先頭に"0"を加える）
						int cd_length = shinsain_no.length();
						if(cd_length == 1){
							shinsain_no = "000000" + shinsain_no;
						}else if(cd_length == 2){
							shinsain_no = "00000" + shinsain_no;
						}else if(cd_length == 3){
							shinsain_no = "0000" + shinsain_no;
						}else if(cd_length == 4){
							shinsain_no = "000" + shinsain_no;
						}else if(cd_length == 5){
							shinsain_no = "00" + shinsain_no;
						}else if(cd_length == 6){
							shinsain_no = "0" + shinsain_no;
						}else if(cd_length == 0 || cd_length > 7){
							String msg = i+"行目 審査員番号の桁数が不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						//KEY項目の重複チェック
						if(cd_list.contains(shinsain_no)){
							String msg = i+"行目 審査員番号は重複しています。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							cd_list.add(shinsain_no);
						}
					}
					//-----氏名（姓）チェック
					if(shimei_kanji_sei == null || shimei_kanji_sei.equals("")){
						String msg = i+"行目 氏名（漢字等－姓）は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(shimei_kanji_sei, 100)){
							String msg = i+"行目 氏名（漢字等－姓）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----氏名（名）チェック
					if(shimei_kanji_mei != null && !shimei_kanji_mei.equals("")){
						if(!this.checkLenB(shimei_kanji_mei, 100)){
							String msg = i+"行目 氏名（漢字等－名）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----氏名フリガナ（姓）チェック
					if(shimei_kana_sei != null && !shimei_kana_sei.equals("")){
						if(!this.checkLenB(shimei_kana_sei, 100)){
							String msg = i+"行目 氏名（フリガナ－姓）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----氏名フリガナ（名）チェック
					if(shimei_kana_mei != null && !shimei_kana_mei.equals("")){
						if(!this.checkLenB(shimei_kana_mei, 100)){
							String msg = i+"行目 氏名（フリガナ－名）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----機関コードチェック
					//2005.11.21 iso 半角チェックに変更
//					//数値チェック
//					if(!this.checkNum(kikan_cd)){
					if(!this.checkHan(kikan_cd)){
						//半角数字で無い場合
						String msg = i+"行目 機関コードは半角数字ではありません。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//半角数字の場合
						//桁数チェック（5桁で無い場合は、先頭に"0"を加える）
						int cd_length = kikan_cd.length();
						if(cd_length == 1){
							kikan_cd = "0000" + kikan_cd;
						}else if(cd_length == 2){
							kikan_cd = "000" + kikan_cd;
						}else if(cd_length == 3){
							kikan_cd = "00" + kikan_cd;
						}else if(cd_length == 4){
							kikan_cd = "0" + kikan_cd;
						//2005.11.21 iso 空所属機関コード空で登録するよう変更
//						}else if(cd_length == 0 || cd_length > 5){
						}else if(cd_length > 5){
							String msg = i+"行目 機関コードの桁数が不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----機関名（和文）チェック
					if(kikan_name == null || kikan_name.equals("")){
						//最初の１回だけDBアクセス
						if(kikanMap.isEmpty()){
							setKikanNameList(connection, kikanMap);
						}
						//当該機関コードの機関名（和文）をゲット
						if(kikanMap.containsKey(kikan_cd)){
							kikan_name = (String)kikanMap.get(kikan_cd);	//マップに存在すればそれをセット	
						}else{
							kikan_name = null;								//存在しない場合はnullで登録
						}
					}else{
						if(!this.checkLenB(kikan_name, 100)){
							String msg = i+"行目 機関名（和文）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}															
					//-----部局名チェック
					if(bukyoku_name != null && !bukyoku_name.equals("")){
						if(!this.checkLenB(bukyoku_name, 100)){
							String msg = i+"行目 部局名の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----職種名チェック
					if(shokushu != null && !shokushu.equals("")){
						if(!this.checkLenB(shokushu, 40)){
							String msg = i+"行目 職種名の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}															
					//-----送付先郵便番号チェック
					if(sofusaki_zip != null && !sofusaki_zip.equals("")){
						if(!this.checkLenB(sofusaki_zip, 8)){
							String msg = i+"行目 送付先郵便番号の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}																				
					//-----送付先Emailチェック
					if(sofusaki_emai != null && !sofusaki_emai.equals("")){
						if(!GenericValidator.isEmail(sofusaki_emai)){
							String msg = i+"行目 送付先Emailの長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							if(!this.checkLenB(sofusaki_emai, 100)){
								String msg = i+"行目 送付先Emailの長さが不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}																				
					//-----電話番号チェック
					if(tel != null && !tel.equals("")){
						if(!this.checkLenB(tel, 50)){
							String msg = i+"行目 電話番号の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}															
					//-----FAX番号チェック
					if(fax != null && !fax.equals("")){
						if(!this.checkLenB(fax, 50)){
							String msg = i+"行目 FAX番号の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}																				
					//-----URLチェック
					if(url != null && !url.equals("")){
						if(!this.checkLenB(url, 100)){
							String msg = i+"行目 URLの長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}																				
					//-----専門分野チェック
					if(senmonbunya != null && !senmonbunya.equals("")){
						if(!this.checkLenB(senmonbunya, 40)){
							String msg = i+"行目 専門分野の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}																									
					//-----備考チェック
					if(biko != null && !biko.equals("")){
						if(!this.checkLenB(biko, 200)){
							String msg = i+"行目 備考の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}																									

					
					//エラーが無かった場合のみ登録する
					if(err_flg == 0){
						//審査員IDの取得（年度＋区分（学創は[1]固定）＋審査員番号）
						String key = strNendo + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO + shinsain_no;
						String shinsain_id = key + CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
						
						//審査員情報の生成
						ShinsainInfo info = new ShinsainInfo();
						info.setShinsainId(shinsain_id);					//審査員ID
						info.setPassword(RandomPwd.generate(rinfo));		//パスワードの取得
						info.setYukoDate(rinfo.getYukoDate());				//有効期限
						info.setDelFlg("0");								//削除フラグ（0:固定）
						info.setShinsainNo(shinsain_no);
						info.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);	//事業区分（学創は[1]固定）
						info.setNameKanjiSei(shimei_kanji_sei);
						info.setNameKanjiMei(shimei_kanji_mei);
						info.setNameKanaSei(shimei_kana_sei);
						info.setNameKanaMei(shimei_kana_mei);
						info.setShozokuCd(kikan_cd);
						info.setShozokuName(kikan_name);
						info.setBukyokuName(bukyoku_name);
						info.setShokushuName(shokushu);
						info.setSofuZip(sofusaki_zip);
						info.setSofuZipaddress(sofusaki_address);
						info.setSofuZipemail(sofusaki_emai);						
						info.setShozokuTel(tel);
						info.setShozokuFax(fax);
						info.setUrl(url);
						info.setSenmon(senmonbunya);
						info.setKoshinDate(nowDateUtil.getDateYYYYMMDD());	//更新日時
						info.setBiko(biko);
						
						//メールフラグ（「送付先E-mail」が入力されていたらフラグを"0"で更新、未入力の場合はnullで更新）
						if(StringUtil.isBlank(sofusaki_emai)){
							info.setMailFlg(null);
						}else{
							info.setMailFlg("0");
						}
						
						//---新規のとき---
						if(!koshinFlg){
							//DBに登録する（審査員マスタと審査員情報テーブル）
							shinsainDao.insertShinsainInfo(connection, info);
						//---更新のとき---	
						}else{
							//更新で、審査員情報テーブルに既存データが無い場合
							if(shinsainDao.checkShinsainInfo(connection,
															 shinsain_no,
															 IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO) == 0){
								//DBに登録する（審査員マスタと審査員情報テーブル）
								shinsainDao.insertShinsainInfo(connection, info);
							//更新で、審査員情報テーブルに既存データがある場合
							}else{
								//DBに登録する（審査員マスタと審査員情報テーブルのメールフラグのみ）
								shinsainDao.insertShinsainInfoOnlyMaster(connection, info);
								shinsainDao.updateMailFlgShinsainInfo(connection, info);
							}
						}

						cnt++;	//取り込み件数をカウント
						
					}else{
						//データに不備があるため、登録を行わない（行数を確保する）
						err_list.add(Integer.toString(i));
					}
				
				}	
				
			}
			
			//マスタ管理テーブルの更新
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_SHINSAIN_GAKUJUTU);
			mkInfo.setMasterName("審査員マスタ（学術創成用）");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg((koshinFlg ? "1" : "0"));	//新規=0, 更新=1
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);						//使用してない？		
			mkInfo.setImportErrMsg(err_list);				//エラー情報（画面で使用）
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;			
			
		} catch (IOException e) {
			throw new ApplicationException(
						"CSVファイルの読込中にエラーが発生しました。",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"マスタ取込中にDBエラーが発生しました。",
						new ErrorInfo("errors.4001"),
						e);
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
				throw new ApplicationException(
					"マスタ取込中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	/**
	 * 基盤用審査員マスタ取込.<br />
	 * 基盤用審査員マスタ情報を取得する。<br />
	 * <b>1.CSVデータをリスト形式に変換</b><br />
	 * 引数で渡されたfileResをList形式へ変換する。Listの要素としてさらにListを持ち、
	 * その要素にCSV情報の一レコード分の情報を格納する。
	 * <br /><br />
	 * <b>2.カラム数チェック</b><br />
	 * 1.で作成したListの一つ目の要素のListの要素数が、9であるか確認する。それ以外の場合は、例外をthrowする。
	 * <br /><br />
	 * <b>3.csvファイル格納</b><br />
	 * 自クラスのkakuno()メソッドを使用してCSVファイルをサーバへ格納する。
	 * <br /><br />
	 * <b>4.マスタエクスポート</b><br />
	 * 基盤用審査員マスタをバックアップ用にエクスポートする。エクスポートは、ApplicationSettings.propertiesの、
	 * EXPORT_COMMANDで定義されているコマンドを実行することによって行われる。<br />
	 * 出力されるダンプファイルのファイル名は以下の通り。<br />
	 * MASTER_SHINSAIN_KIBAN_KOSHIN_yyyyMMddHHmmssSSS.DMP(更新)<br />
	 * MASTER_SHINSAIN_KIBAN_SHINKI_yyyyMMddHHmmssSSS.DMP(新規)<br />
	 * <br />
	 * <br />
	 * <b>5.基盤用審査員情報削除</b><br />
	 * 審査員マスタを、以下のSQLを実行することによって削除する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		MASTER_SHINSAIN
	 *	WHERE
	 *		JIGYO_KUBUN = 4 -- 事業区分（基盤）
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * 第三引数がtrueの場合は、続いて審査員情報を以下のSQLにて削除する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		SHINSAININFO
	 *	WHERE
	 *		WHERE SUBSTR(SHINSAIN_ID,3,1) = 4 -- 事業区分（基盤）
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * <br />
	 * <b>6.情報取込</b><br />
	 * 1.で作成したCSVファイル情報Listを審査員マスタへINSERTする。
	 * Listに格納されている要素Listには以下の情報が格納されている。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>要素番号</td><td>テーブル列名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>審査員番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>氏名（姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>氏名（名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>氏名フリガナ（姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>氏名フリガナ（名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>機関名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>部局名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>職種</td></tr>
	 *	</table><br />
	 * 
	 * 各列の情報は、桁数チェック、型チェックおよび一意キー制約チェックを行い、問題がなければINSERT処理を行う。
	 * INSERTする際、事業コードは4（基盤とする。
	 * 問題があった場合は、エラー情報を管理するListに格納したのち、次の情報へ処理を進める。
	 * なお、機関コードは、各チェックとともに左0埋め処理を行う。
	 * INSERTする際に実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		MASTER_SHINSAIN(
	 *			 SHINSAIN_NO		-- 審査員番号
	 *			,JIGYO_KUBUN		-- 事業区分
	 *			,NAME_KANJI_SEI		-- 氏名（姓）
	 *			,NAME_KANJI_MEI		-- 氏名（名）
	 *			,NAME_KANA_SEI		-- 氏名フリガナ（姓）
	 *			,NAME_KANA_MEI		-- 氏名フリガナ（名）
	 *			,SHOZOKU_CD		-- 所属機関コード
	 *			,SHOZOKU_NAME		-- 機関名称
	 *			,BUKYOKU_NAME		-- 部局名称
	 *			,SHOKUSHU_NAME		-- 職種名称
	 *			,SOFU_ZIP		-- 送付先郵便番号
	 *			,SOFU_ZIPADDRESS		-- 送付先住所
	 *			,SOFU_ZIPEMAIL		-- 送付先Email
	 *			,SHOZOKU_TEL		-- 電話番号
	 *			,SHOZOKU_FAX		-- FAX
	 *			,URL		-- URL
	 *			,SENMON		-- 専門分野
	 *			,KOSHIN_DATE		-- 更新日
	 *			,BIKO		-- 備考
	 *		)
	 *	VALUES
	 *		(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>CSVから取得した審査員番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>4</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（姓）</td><td>CSVから取得した氏名（姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（名）</td><td>CSVから取得した氏名（名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名フリガナ（姓）</td><td>CSVから取得した氏名フリガナ（姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名フリガナ（名）</td><td>CSVから取得した氏名フリガナ（名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>CSVから取得した所属機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>機関名称</td><td>CSVから取得した機関名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>部局名称</td><td>CSVから取得した部局名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>職種名称</td><td>CSVから取得した職種名称</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>送付先郵便番号</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>送付先住所</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>送付先Email</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>電話番号</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>FAX</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>URL</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>専門分野</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>更新日</td><td>WASの現在日付</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>備考</td><td>null</td></tr>
	 *	</table><br/>
	 * 審査員マスタへINSERTした審査員情報が、審査員情報テーブルに存在しない場合は、同時にINSERTを行う。
	 * INSERTする際に実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		SHINSAININFO(
	 *			 SHINSAIN_ID	-- 審査員ID
	 *			,PASSWORD		-- パスワード
	 *			,YUKO_DATE		-- 有効期限
	 *			,DEL_FLG		-- 削除フラグ
	 *		)
	 *	VALUES
	 *		(?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員ID</td><td>年度＋4＋審査員番号+チェックデジット</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>パスワード</td><td>ＩＤパスワード発行ルールによって作成</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>有効期限</td><td>ＩＤパスワード発行ルールテーブルから取得</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>0</td></tr>
	 *	</table><br/>
	 * 
	 * <br />
	 * <b>7.マスタ管理マスタ更新</b><br />
	 * マスタ管理マスタを以下の情報で更新する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>更新情報</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ種別</td><td>6</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ名称</td><td>"審査員マスタ（基盤研究等）"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込み日時</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>件数</td><td>Insertしたレコード数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込みテーブル名</td><td>"MASTER_SHINSAIN,SHINSAININFO"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>新規・更新フラグ</td><td>0:新規、1:更新</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>処理状況</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取込エラーメッセージ</td><td>レコードINSERTに失敗した場合、エラーメッセージ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSVファイルパス</td><td>CSV格納先フォルダ＋CSVファイル名</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>8.マスタ管理情報返却</b><br />
	 * 7.で更新した情報を呼び出し元へ返却する。
	 * 
	 * @param userInfo		UserInfo
	 * @param fileRes		CSVファイル情報
	 * @param koshinFlg		true:新規取り込み、false:更新
	 * @return	基盤用審査員マスタのマスタ管理情報
	 * @throws ApplicationException	何らかの例外
	 */
	private MasterKanriInfo torikomiMasterShinsainKiban(UserInfo     userInfo,
														FileResource fileRes,
														boolean     koshinFlg)
		throws ApplicationException
	{
		int columnSize = 9;
		
		boolean success = false;
		Connection connection = null;

		try{
			//CSVデータをリスト形式に変換する
			List dt = csvFile2List(fileRes);

			//カラム数チェック(1行目のみ)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//項目数が違う場合はエラーを出力し処理を終了する。
					throw new ApplicationException(
								"CSVファイルが審査員マスタ（基盤）の定義と異なります。",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csvファイル格納
			String tableName = "MASTER_SHINSAIN_KIBAN";
			String csvPath = kakuno(fileRes, tableName);
			
			//DBのエクスポート
			String footer = (koshinFlg ? "_KOSHIN_" : "_SHINKI_"); 
			String table_name = "MASTER_SHINSAIN,SHINSAININFO";
			String file_name = tableName + footer + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//コネクション取得
			connection = DatabaseUtil.getConnection();

			//有効期限の取得
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSAIN);
			RuleInfoDao ruledao = new RuleInfoDao(userInfo);
			RuleInfo rinfo = ruledao.selectRuleInfo(connection, rulePk);
//			String yukoDate = new SimpleDateFormat("yyyyMMdd").format(rinfo.getYukoDate());

			//取込（DELETE → INSERT処理）（事業区分が基盤[4]のみ）
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection,
							 "MASTER_SHINSAIN", 
							 "WHERE JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN);
			if(!koshinFlg){
				//「新規」のときは審査員情報テーブルも削除（事業区分が基盤[4]のみ）
				dao.deleteMaster(connection, 
								 "SHINSAININFO",
								 "WHERE SUBSTR(SHINSAIN_ID,3,1) = " + IJigyoKubun.JIGYO_KUBUN_KIBAN);
			}
			
			int cnt = 0;												//正常取込件数
			ArrayList cd_list  = new ArrayList();						//取込データのキー格納配列(一意制約チェック用)
			ArrayList err_list = new ArrayList();						//取込みエラー行格納配列
			int dtsize = dt.size();									//取込全件数
			Map kikanMap = new HashMap();								//機関コードと機関名称のMap
			DateUtil nowDateUtil = new DateUtil(new Date());			//現時点での日時情報
			String strNendo = nowDateUtil.getYearYY();					//現時点での年度（西暦年の下２桁） 
			
			//半角全角コンバータ
			HanZenConverter converter = new HanZenConverter();
			//DAO
			ShinsainInfoDao shinsainDao = new ShinsainInfoDao(userInfo);
			for(int i=1; i<=dtsize; i++){
				
				if((i-1) % 1000 == 0) {
					mtLog.info("審査員マスタ取り込み::" + (i-1) + "件");
				}
				
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//項目数が違う場合はエラーとして処理を飛ばす。（行数を確保する）
					String msg = i+"行目 審査員マスタ（基盤）のテーブル定義と一致しません。";
					mtLog.warn(msg);
					//err_list.add(msg);				//エラー内容を格納 → 保留。コメントアウト
					err_list.add(Integer.toString(i));	//エラーの行数を確保
					
				}else{
					//正常処理
					//エラーフラグ
					int err_flg = 0;
					
					//マスタデータの取得
					int j = 0;
					String shinsain_no        = (String)line.get(j++);	//審査員番号
					String shimei_kanji_sei   = (String)line.get(j++);	//氏名（姓）
					String shimei_kanji_mei   = (String)line.get(j++);	//氏名（名）
					String shimei_kana_sei    = (String)line.get(j++);	//氏名フリガナ（姓）
					String shimei_kana_mei    = (String)line.get(j++);	//氏名フリガナ（名）
					String kikan_cd           = (String)line.get(j++);	//機関コード
					String kikan_name         = (String)line.get(j++);	//機関名
					String bukyoku_name       = (String)line.get(j++);	//部局名
					String shokushu           = (String)line.get(j++);	//職種
					
					//-----審査員番号チェック
					//半角英数字チェック
					if(!this.checkHan(shinsain_no)){
						//半角数字で無い場合
						String msg = i+"行目 審査員番号は半角英数字ではありません。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//半角数字の場合
						//桁数チェック（7桁で無い場合は、先頭に"0"を加える）
						int cd_length = shinsain_no.length();
						if(cd_length == 1){
							shinsain_no = "000000" + shinsain_no;
						}else if(cd_length == 2){
							shinsain_no = "00000" + shinsain_no;
						}else if(cd_length == 3){
							shinsain_no = "0000" + shinsain_no;
						}else if(cd_length == 4){
							shinsain_no = "000" + shinsain_no;
						}else if(cd_length == 5){
							shinsain_no = "00" + shinsain_no;
						}else if(cd_length == 6){
							shinsain_no = "0" + shinsain_no;
						}else if(cd_length == 0 || cd_length > 7){
							String msg = i+"行目 審査員番号の桁数が不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						//KEY項目の重複チェック
						if(cd_list.contains(shinsain_no)){
							String msg = i+"行目 審査員番号は重複しています。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							cd_list.add(shinsain_no);
						}
					}
					//-----氏名（姓）チェック
					if(shimei_kanji_sei == null || shimei_kanji_sei.equals("")){
						String msg = i+"行目 氏名（漢字等－姓）は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(shimei_kanji_sei, 100)){
							String msg = i+"行目 氏名（漢字等－姓）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----氏名（名）チェック
					if(shimei_kanji_mei != null && !shimei_kanji_mei.equals("")){
						if(!this.checkLenB(shimei_kanji_mei, 100)){
							String msg = i+"行目 氏名（漢字等－名）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----氏名フリガナ（姓）チェック
					if(shimei_kana_sei != null && !shimei_kana_sei.equals("")){
						shimei_kana_sei = converter.convert(shimei_kana_sei);	//半角カナは全角に変換
						if(!this.checkLenB(shimei_kana_sei, 100)){
							String msg = i+"行目 氏名（フリガナ－姓）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----氏名フリガナ（名）チェック
					if(shimei_kana_mei != null && !shimei_kana_mei.equals("")){
						shimei_kana_mei = converter.convert(shimei_kana_mei);	//半角カナは全角に変換
						if(!this.checkLenB(shimei_kana_mei, 100)){
							String msg = i+"行目 氏名（フリガナ－名）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----機関コードチェック
					//2005.11.21 iso 半角チェックに変更
//					//数値チェック
//					if(!this.checkNum(kikan_cd)){
					if(!this.checkHan(kikan_cd)){
						//半角数字で無い場合
						String msg = i+"行目 機関コードは半角数字ではありません。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//半角数字の場合
						//桁数チェック（5桁で無い場合は、先頭に"0"を加える）
						int cd_length = kikan_cd.length();
						if(cd_length == 1){
							kikan_cd = "0000" + kikan_cd;
						}else if(cd_length == 2){
							kikan_cd = "000" + kikan_cd;
						}else if(cd_length == 3){
							kikan_cd = "00" + kikan_cd;
						}else if(cd_length == 4){
							kikan_cd = "0" + kikan_cd;
						//2005.11.21 iso 空所属機関コード空で登録するよう変更
//						}else if(cd_length == 0 || cd_length > 5){
						}else if(cd_length > 5){
							String msg = i+"行目 機関コードの桁数が不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----機関名（和文）チェック
					if(kikan_name == null || kikan_name.equals("")){
						//最初の１回だけDBアクセス
						if(kikanMap.isEmpty()){
							setKikanNameList(connection, kikanMap);
						}
						//当該機関コードの機関名（和文）をゲット
						if(kikanMap.containsKey(kikan_cd)){
							kikan_name = (String)kikanMap.get(kikan_cd);	//マップに存在すればそれをセット	
						}else{
							kikan_name = null;								//存在しない場合はnullで登録
						}
					}else{
						if(!this.checkLenB(kikan_name, 100)){
							String msg = i+"行目 機関名（和文）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}															
					//-----部局名チェック
					if(bukyoku_name != null && !bukyoku_name.equals("")){
						if(!this.checkLenB(bukyoku_name, 100)){
							String msg = i+"行目 部局名の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----職種名チェック
					if(shokushu != null && !shokushu.equals("")){
						if(!this.checkLenB(shokushu, 40)){
							String msg = i+"行目 職種名の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}															

					
					//エラーが無かった場合のみ登録する
					if(err_flg == 0){
						//審査員IDの取得（年度＋区分（基盤は[4]固定）＋審査員番号）
						String key = strNendo + IJigyoKubun.JIGYO_KUBUN_KIBAN + shinsain_no;
						String shinsain_id = key + CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
						
						//審査員情報の生成
						ShinsainInfo info = new ShinsainInfo();
						info.setShinsainId(shinsain_id);					//審査員ID
						info.setPassword(RandomPwd.generate(rinfo));		//パスワードの取得
						info.setYukoDate(rinfo.getYukoDate());				//有効期限
						info.setDelFlg("0");								//削除フラグ（0:固定）
						info.setShinsainNo(shinsain_no);
						info.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);	//事業区分（基盤は[4]定）
						info.setNameKanjiSei(shimei_kanji_sei);
						info.setNameKanjiMei(shimei_kanji_mei);
						info.setNameKanaSei(shimei_kana_sei);
						info.setNameKanaMei(shimei_kana_mei);
						info.setShozokuCd(kikan_cd);
						info.setShozokuName(kikan_name);
						info.setBukyokuName(bukyoku_name);
						info.setShokushuName(shokushu);
						info.setKoshinDate(nowDateUtil.getDateYYYYMMDD());	//更新日時
						
						//---新規のとき---
						if(!koshinFlg){
							//DBに登録する（審査員マスタと審査員情報テーブル）
							shinsainDao.insertShinsainInfo(connection, info);
						//---更新のとき---	
						}else{
							//更新で、審査員情報テーブルに既存データが無い場合
							if(shinsainDao.checkShinsainInfo(connection,
															 shinsain_no,
															 IJigyoKubun.JIGYO_KUBUN_KIBAN) == 0){
								//DBに登録する（審査員マスタと審査員情報テーブル）
								shinsainDao.insertShinsainInfo(connection, info);
							//更新で、審査員情報テーブルに既存データがある場合
							}else{
								//DBに登録する（審査員マスタのみ）
								shinsainDao.insertShinsainInfoOnlyMaster(connection, info);
							}
						}

						cnt++;	//取り込み件数をカウント
						
					}else{
						//データに不備があるため、登録を行わない（行数を確保する）
						err_list.add(Integer.toString(i));
					}
				
				}	
				
			}

			mtLog.info("審査員マスタ取り込み全件完了");
			
			//マスタ管理テーブルの更新
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_SHINSAIN_KIBAN);
			mkInfo.setMasterName("審査員マスタ（基盤研究等）");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg((koshinFlg ? "1" : "0"));	//新規=0, 更新=1
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);						//使用してない？		
			mkInfo.setImportErrMsg(err_list);				//エラー情報（画面で使用）
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;			
			
		} catch (IOException e) {
			throw new ApplicationException(
						"CSVファイルの読込中にエラーが発生しました。",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"マスタ取込中にDBエラーが発生しました。",
						new ErrorInfo("errors.4001"),
						e);
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
				throw new ApplicationException(
					"マスタ取込中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	/**
	 * 基盤用割り振りデータ取込.<br />
	 * 基盤用割り振りデータ情報を取得する。<br />
	 * <b>1.CSVデータをリスト形式に変換</b><br />
	 * 引数で渡されたfileResをList形式へ変換する。Listの要素としてさらにListを持ち、
	 * その要素にCSV情報の一レコード分の情報を格納する。
	 * <br /><br />
	 * <b>2.カラム数チェック</b><br />
	 * 1.で作成したListの一つ目の要素のListの要素数が、21であるか確認する。それ以外の場合は、例外をthrowする。
	 * <br /><br />
	 * <b>3.csvファイル格納</b><br />
	 * 自クラスのkakuno()メソッドを使用してCSVファイルをサーバへ格納する。
	 * <br /><br />
	 * <b>4.情報取込</b><br />
	 * 1.で作成したCSVファイル情報Listを基盤用割り振りデータへINSERTする。
	 * Listに格納されている要素Listには以下の情報が格納されている。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>要素番号</td><td>テーブル列名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>事業コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>年度</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>回数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>整理番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>研究課題名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>研究代表者氏名（姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>研究代表者氏名（名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>部局コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>9</td><td>職種コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>10</td><td>細目コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>11</td><td>分割番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>12</td><td>海外分野コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>13</td><td>最終年度前年度申請</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>14</td><td>分担金の有無</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>15</td><td>審査員番号1</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>16</td><td>審査員番号2</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>17</td><td>審査員番号3</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>18</td><td>審査員番号4</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>19</td><td>審査員番号5</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>20</td><td>審査員番号6</td></tr>
	 *	</table>
	 * これ以外の情報はCSVにはないため、nullとする。<br />
	 * 
	 * 各列の情報は、桁数チェック、型チェックおよび一意キー制約チェックを行い、問題が無い場合はテーブルを更新する。
	 * 更新するテーブルは申請情報テーブル、チェックリスト情報テーブル、審査結果テーブルの３つ。
	 * 申請情報テーブルは事業コード+機関番号+細目コード+分割番号+整理番号が一致する申請データについて状況IDを更新する。
	 * チェックリスト情報テーブルは所属コードと事業IDで状況IDを取得し、取得した状況IDが06の場合は10二変更する。
	 * 審査結果テーブルは事業区分とシステム受付番号が一致し、審査員番号が@000001～@000006のデータについて更新する。
	 * 問題があった場合は、エラー情報を管理するListに格納したのち、次の情報へ処理を進める。
	 * なお、事業コード、整理番号、機関コード、部局コード、職コード、細目コード、海外分野コード、審査員番号は、
	 * 各チェックとともに左0埋め処理を行う。また、該事業IDから事業名を、機関コードから機関名称と機関名略称を、
	 * 部局コードから部局名称と部局名略称を、該職コードから職種名称と職種名略称を、細目コードから細目名と分科名および分野名を、
	 * 海外分野コードから海外分野名称と海外分野名略称を、審査員番号から審査員名（漢字-姓、漢字-名、カナ-姓、カナ-名）、
	 * 機関名、部局名、職名を、それぞれ各マスタから取得する。
	 * 実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *	<b>申請情報</b>
		UPDATE 
				SHINSEIDATAKANRI
		SET
				JOKYO_ID = ?		
		WHERE
				SYSTEM_NO = ?
	
	 * 	<b>チェックリスト</b>	
		UPDATE 
				CHCKLISTINFO
		SET
				JOKYO_ID = ?
		WHERE
				SHOZOKU_CD = ?
		AND
				JIGYO_ID = ?
		AND
				JOKYO_ID = ?
				
	 *	<b>審査結果</b>
		UPDATE 
				SHINSAKEKKA
		SET	
				SHINSAIN_NO = ? ,
				JIGYO_KUBUN = ? ,
				SHINSAIN_NAME_KANJI_SEI = ? ,
				SHINSAIN_NAME_KANJI_MEI = ? ,
				NAME_KANA_SEI = ? ,
				NAME_KANA_MEI = ? ,
				SHOZOKU_NAME = ? ,
				BUKYOKU_NAME = ? ,
				SHOKUSHU_NAME = ? ,
		WHERE 
				SHINSAIN_NO = ?
		AND 
				JIGYO_KUBUN = ?
		AND 
				SYSTEM_NO = ?
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<b>申請情報</b>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>西暦年度(yy)+CSVから取得した事業コード+CSVから取得した回数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>yyyyMMddHHmmssSSSSSS</td></tr>
	 *	</table>
	 *	<b>チェックリスト</b>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>状況ID</td><td>10</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>所属CD</td><td>CSVから取得した所属コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>西暦年度(yy)+CSVから取得した事業コード+CSVから取得した回数</td></tr>
	 *	</table>
	 *	<b>審査結果</b>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>CSVから取得した審査員番号1～6</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>"4"(基盤)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（漢字－姓）</td><td>マスタから取得した審査員名（漢字－姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（漢字－名）</td><td>マスタから取得した審査員名（漢字－名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－姓）</td><td>マスタから取得した審査員名（フリガナ－姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員名（フリガナ－名）</td><td>マスタから取得した審査員名（フリガナ－名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員所属機関名</td><td>マスタから取得した審査員所属機関名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員部局名</td><td>マスタから取得した審査員部局名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>審査員職名</td><td>マスタから取得した審査員職名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>yyyyMMddHHmmssSSSSSS（申請情報のシステム受付番号）</td></tr>
	 *	</table>
	 * <b>5.マスタ管理マスタ更新</b><br />
	 * マスタ管理マスタを以下の情報で更新する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>更新情報</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ種別</td><td>1</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ名称</td><td>"基盤用割り振りデータ"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込み日時</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>件数</td><td>Insertしたレコード数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込みテーブル名</td><td>"MASTER_SAIMOKU"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>新規・更新フラグ</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>処理状況</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取込エラーメッセージ</td><td>レコードINSERTに失敗した場合、エラーメッセージ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSVファイルパス</td><td>CSV格納先フォルダ＋CSVファイル名</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>6.マスタ管理情報返却</b><br />
	 * 5.で更新した情報を呼び出し元へ返却する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param fileRes				アップロードファイルリソース。
	 * @return						処理結果情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	private MasterKanriInfo torikomiMasterWarifuriKekka(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException
	{
		int columnSize = 21;
		
		boolean success = false;
		Connection connection = null;

		try{
			//CSVデータをリスト形式に変換する
			List dt = csvFile2List(fileRes);

			//カラム数チェック(1行目のみ)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//項目数が違う場合はエラーを出力し処理を終了する。
					throw new ApplicationException(
								"CSVファイルが基盤研究等割り振りデータの定義と異なります。",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csvファイル格納
			String tableName = "KIBAN_WARIFURIDATA";
			String csvPath = kakuno(fileRes, tableName);
			
			/* 2004/11/24
			 * 仕様としてDBエクスポートダンプを取らない。
			 * 該当件数が膨大であること、基盤以外の申請データも含まれてしまうため。
			 */
			//DBのエクスポート
			String table_name = "SHINSEIDATAKANRI,SHINSAKEKKA";
			//String file_name = tableName + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			//String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			//FileUtil.execCommand(cmd);
			
			//コネクション取得
			connection = DatabaseUtil.getConnection();

			int cnt = 0;												//正常取込件数
			ArrayList err_list = new ArrayList();						//取込みエラー行格納配列
			int dtsize = dt.size();									//取込全件数
			
			//各コードとそれに対応する属性情報
//2006/4/13未使用変数をコメントする
//			Map kikanMap    = new HashMap();							//機関コードと属性情報
//			Map bukyokuMap  = new HashMap();							//部局コードと属性情報
//			Map shokuMap    = new HashMap();							//職種コードと属性情報
//			Map saimokuMap  = new HashMap();							//細目コードと属性情報
			Map shinsainMap = new HashMap();							//審査員番号と属性情報
			Map jigyoMap    = new HashMap();							//事業IDと属性情報
			
			//当該コードに対応する名称が存在しなかった場合にセットする文字列
//			final String OTHER_NAME_VALUE    = "その他";
			//取り込みデータ用ダミー申請者ID
			//final String DUMMY_SHINSEISHA_ID = "@torikomidata"; 	未使用2005/9/27 
			
			//DAO
			MasterKanriInfoDao dao            = new MasterKanriInfoDao(userInfo);
			ShinseiDataInfoDao shinseiDao     = new ShinseiDataInfoDao(userInfo);
			ShinsaKekkaInfoDao shinsakekkaDao = new ShinsaKekkaInfoDao(userInfo);
			
			//2005/04/22追加 ここから---------------------------------------------
			//使用するDao等の追加
			
			CheckListInfoDao checkDao  = new CheckListInfoDao(userInfo);
			ArrayList cd_list          = new ArrayList();	

			//2005.12.05 iso 申請データを上書きしないよう戻す。
			//2005.11.21 iso 取り込みデータで申請情報を上書きするよう変更
			ShinseiDataPk shinseiPk    = new ShinseiDataPk();
//			ShinseiDataInfo updateInfo	= new ShinseiDataInfo();
			
			CheckListSearchInfo checkInfo    = new CheckListSearchInfo();
			
			//追加 ここまで-------------------------------------------------------
			
			//---CSVレコードの繰り返し
			//csvRecordCount:
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//項目数が違う場合はエラーとして処理を飛ばす。（行数を確保する）
					String msg = i+"行目 基盤研究等割り振り結果の定義と一致しません。";
					mtLog.warn(msg);
					//err_list.add(msg);				//エラー内容を格納 → 保留。コメントアウト
					err_list.add(Integer.toString(i));	//エラーの行数を確保
					
				}else{
					//正常処理
					//エラーフラグ
					int err_flg = 0;
					
					//CSVデータの取得
					int j = 0;
					String   jigyo_cd               = (String)line.get(j++);	//事業コード
					String   jigyo_id               = null;						//事業ID（CSVには無い）
//					String   jigyo_name             = null;						//事業名（CSVには無い）
//					String   shinsa_kubun           = null;						//審査区分（CSVには無い）
					String   nendo                  = (String)line.get(j++);	//年度
					String   kaisu                  = (String)line.get(j++);	//回数
					String   seiri_no               = (String)line.get(j++);	//整理番号
//					String   uketuke_no             = null;						//申請番号（CSVには無い）
					String   kadai_name             = (String)line.get(j++);	//研究課題名
					String   shimei_kanji_sei       = (String)line.get(j++);	//研究代表者氏名（姓）
					String   shimei_kanji_mei       = (String)line.get(j++);	//研究代表者氏名（名）
					String   kikan_cd               = (String)line.get(j++);	//機関コード
//					String   kikan_name             = null;						//機関名（CSVには無い）
//					String   kikan_name_ryaku       = null;						//機関名略（CSVには無い）
					String   bukyoku_cd             = (String)line.get(j++);	//部局コード
//					String   bukyoku_name           = null;						//部局名（CSVには無い）
//					String   bukyoku_name_ryaku     = null;						//部局名略（CSVには無い）
					String   shoku_cd               = (String)line.get(j++);	//職種コード
//					String   shoku_name             = null;						//職名（CSVには無い）
//					String   shoku_name_ryaku       = null;						//職名略（CSVには無い）
					String   saimoku_cd             = (String)line.get(j++);	//細目コード
//					String   saimoku_name           = null;						//細目名（CSVには無い）
//					String   bunka_name             = null;						//分科名（CSVには無い）
//					String   bunya_name             = null;						//分野名（CSVには無い）
					String   bunkatsu_no            = (String)line.get(j++);	//分割番号
					String   kaigaibunya_cd         = (String)line.get(j++);	//海外分野コード
//					String   kaigaibunya_name       = null;						//海外分野名（CSVには無い）
//					String   kaigaibunya_name_ryaku = null;						//海外分野名略（CSVには無い）
					String   zennendo_shinsei       = (String)line.get(j++);	//最終年度前年度申請
					String   buntankin              = (String)line.get(j++);	//分担金の有無
					String[] shinsain_no            = new String[6];			//審査員番号
					String[] shinsain_kanji_sei     = new String[6];			//審査員名（漢字-姓）（CSVには無い）
					String[] shinsain_kanji_mei     = new String[6];			//審査員名（漢字-名）（CSVには無い）
					String[] shinsain_kana_sei      = new String[6];			//審査員名（カナ-姓）（CSVには無い）
					String[] shinsain_kana_mei      = new String[6];			//審査員名（カナ-名）（CSVには無い）
					String[] shinsain_kikan_name    = new String[6];			//審査員機関名（CSVには無い）
					String[] shinsain_bukyoku_name  = new String[6];			//審査員部局名（CSVには無い）
					String[] shinsain_shoku_name    = new String[6];			//審査員職名（CSVには無い）
					
					//16カラムから6つは審査員番号
					for(int k=0; k<shinsain_no.length; k++){
						shinsain_no[k] = (String)line.get(j++);
					}
					
					//-----事業コードチェック
					if(jigyo_cd == null || jigyo_cd.length() == 0){
						String msg = i+"行目 事業コードは必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{						
						//数値チェック
						if(!this.checkNum(jigyo_cd)){
							//半角数字で無い場合
							String msg = i+"行目 事業コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（5桁で無い場合は、先頭に"0"を加える）
							int cd_length = jigyo_cd.length();
							if(cd_length == 1){
								jigyo_cd = "0000" + jigyo_cd;
							}else if(cd_length == 2){
								jigyo_cd = "000" + jigyo_cd;
							}else if(cd_length == 3){
								jigyo_cd = "00" + jigyo_cd;
							}else if(cd_length == 4){
								jigyo_cd = "0" + jigyo_cd;
							}
							//再度桁数をチェックし、問題無しならば審査区分を取得。
							if(cd_length == 0 || cd_length > 5){
								String msg = i+"行目 事業コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}
					//-----年度チェック
					if(nendo == null || nendo.length() == 0){
						String msg = i+"行目 年度は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{						
						//数値チェック
						if(!this.checkNum(nendo)){
							//半角数字で無い場合
							String msg = i+"行目 年度は半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（2桁で無い場合は、先頭に"0"を加える）
							int cd_length = nendo.length();
							if(cd_length == 1){
								nendo = "0" + nendo;
							}else if(cd_length == 0 || cd_length > 2){
								String msg = i+"行目 年度の桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}		
					//-----回数チェック
					if(kaisu == null || kaisu.length() == 0){
						String msg = i+"行目 回数は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{						
						//数値チェック
						if(!this.checkNum(kaisu)){
							//半角数字で無い場合
							String msg = i+"行目 回数は半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							if(!this.checkLenB(kaisu, 1)){
								String msg = i+"行目 回数の長さが不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}
					//=====事業IDの存在チェック（あわせて事業名を取得）
					//事業IDを作成（西暦年度+事業コード+回数）
					if (err_flg != 1) {
						jigyo_id = DateUtil.changeWareki2Seireki(nendo) + jigyo_cd + kaisu;	
						//最初の１回だけDBアクセス
						if(jigyoMap.isEmpty()){
							setJigyoInfoList(connection, jigyoMap);
						}
						//当該事業IDの事業情報をゲット
						if(!jigyoMap.containsKey(jigyo_id)){
						//マップに存在すればそれをセット
//							Map recordMap = (Map)jigyoMap.get(jigyo_id);	
//							jigyo_name    = (String)recordMap.get("JIGYO_NAME");
//						}else{
						//存在しない場合はエラー
						String msg = i+"行目 当該事業IDは存在しません。事業ID="+jigyo_id;
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
						}
					}
					//-----整理番号チェック
					if(seiri_no == null || seiri_no.length() == 0){
						String msg = i+"行目 整理番号は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{						
						//数値チェック
						if(!this.checkNum(seiri_no)){
							//半角数字で無い場合
							String msg = i+"行目 整理番号は半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（4桁で無い場合は、先頭に"0"を加える）
							int cd_length = seiri_no.length();
							if(cd_length == 1){
								seiri_no = "000" + seiri_no;
							}else if(cd_length == 2){
								seiri_no = "00" + seiri_no;
							}else if(cd_length == 3){
								seiri_no = "0" + seiri_no;
							}else if(cd_length == 0 || cd_length > 4){
								String msg = i+"行目 整理番号の桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}		
					//-----研究課題名チェック
					if(kadai_name != null && kadai_name.length() != 0){
						if(!this.checkLenB(kadai_name, 80)){
							String msg = i+"行目 研究課題名の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}							
					//-----研究代表者氏名（姓）チェック
					if(shimei_kanji_sei != null && shimei_kanji_sei.length() != 0){
						if(!this.checkLenB(shimei_kanji_sei, 100)){
							String msg = i+"行目 研究代表者氏名（漢字等－姓）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----研究代表者氏名（名）チェック
					if(shimei_kanji_mei != null && shimei_kanji_mei.length() != 0){
						if(!this.checkLenB(shimei_kanji_mei, 100)){
							String msg = i+"行目 研究代表者氏名（漢字等－名）の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----機関コードチェック
					if(kikan_cd == null || kikan_cd.length() == 0){
						String msg = i+"行目 機関コードは必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{						
						//数値チェック
						if(!this.checkNum(kikan_cd)){
							//半角数字で無い場合
							String msg = i+"行目 機関コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（5桁で無い場合は、先頭に"0"を加える）
							int cd_length = kikan_cd.length();
							if(cd_length == 1){
								kikan_cd = "0000" + kikan_cd;
							}else if(cd_length == 2){
								kikan_cd = "000" + kikan_cd;
							}else if(cd_length == 3){
								kikan_cd = "00" + kikan_cd;
							}else if(cd_length == 4){
								kikan_cd = "0" + kikan_cd;
							}else if(cd_length == 0 || cd_length > 5){
								String msg = i+"行目 機関コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
						//=====機関名、機関名略を取得
						//最初の１回だけDBアクセス
//						if(kikanMap.isEmpty()){
//							setKikanInfoList(connection, kikanMap);
//						}
						//当該機関コードの機関情報をゲット
//						if(kikanMap.containsKey(kikan_cd)){
							//マップに存在すればそれをセット	
//							Map recordMap    = (Map)kikanMap.get(kikan_cd);
//							kikan_name       = (String)recordMap.get("SHOZOKU_NAME_KANJI");
//							kikan_name_ryaku = (String)recordMap.get("SHOZOKU_RYAKUSHO");
//						}else{
							//存在しない場合は「その他」で登録
//							kikan_name       = OTHER_NAME_VALUE;
//							kikan_name_ryaku = OTHER_NAME_VALUE;
//						}
					}
					//-----部局コードチェック
					if(bukyoku_cd != null && bukyoku_cd.length() != 0){
						//数値チェック
						if(!this.checkNum(bukyoku_cd)){
							//半角数字で無い場合
							String msg = i+"行目 部局コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（3桁で無い場合は、先頭に"0"を加える）
							int cd_length = bukyoku_cd.length();
							if(cd_length == 1){
								bukyoku_cd = "00" + bukyoku_cd;
							}else if(cd_length == 2){
								bukyoku_cd = "0" + bukyoku_cd;
							}else if(cd_length == 0 || cd_length > 3){
								String msg = i+"行目 部局コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
						//=====部局名、部局名略を取得
						//最初の１回だけDBアクセス
//						if(bukyokuMap.isEmpty()){
//							setBukyokuInfoList(connection, bukyokuMap);
//						}
						//当該部局コードの部局情報をゲット
//						if(bukyokuMap.containsKey(bukyoku_cd)){
							//マップに存在すればそれをセット	
//							Map recordMap      = (Map)bukyokuMap.get(bukyoku_cd);
//							bukyoku_name       = (String)recordMap.get("BUKA_NAME");
//							bukyoku_name_ryaku = (String)recordMap.get("BUKA_RYAKUSHO");
//						}else{
							//存在しない場合は「その他」で登録
//							bukyoku_name       = OTHER_NAME_VALUE;
//							bukyoku_name_ryaku = OTHER_NAME_VALUE;
//						}				
					}
					//-----職コードチェック
					if(shoku_cd != null && shoku_cd.length() != 0){
						//数値チェック
						if(!this.checkNum(shoku_cd)){
							//半角数字で無い場合
							String msg = i+"行目 職コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（2桁で無い場合は、先頭に"0"を加える）
							int cd_length = shoku_cd.length();
							if(cd_length == 1){
								shoku_cd = "0" + shoku_cd;
							}else if(cd_length == 0 || cd_length > 2){
								String msg = i+"行目 職コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
						//=====職名、職名略を取得
						//最初の１回だけDBアクセス
//						if(shokuMap.isEmpty()){
//							setShokuInfoList(connection, shokuMap);
//						}
						//当該職コードの職情報をゲット
//						if(shokuMap.containsKey(shoku_cd)){
							//マップに存在すればそれをセット	
//							Map recordMap    = (Map)shokuMap.get(shoku_cd);
//							shoku_name       = (String)recordMap.get("SHOKUSHU_NAME");
//							shoku_name_ryaku = (String)recordMap.get("SHOKUSHU_NAME_RYAKU");
//						}else{
							//存在しない場合は「その他」で登録
//							shoku_name       = OTHER_NAME_VALUE;
//							shoku_name_ryaku = OTHER_NAME_VALUE;
//						}
					}										
					//-----細目コードチェック
					if(saimoku_cd != null && saimoku_cd.length() != 0){
						//数値チェック
						if(!this.checkNum(saimoku_cd)){
							//半角数字で無い場合
							String msg = i+"行目 細目コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（4桁で無い場合は、先頭に"0"を加える）
							int cd_length = saimoku_cd.length();
							if(cd_length == 1){
								saimoku_cd = "000" + saimoku_cd;
							}else if(cd_length == 2){
								saimoku_cd = "00" + saimoku_cd;
							}else if(cd_length == 3){
								saimoku_cd = "0" + saimoku_cd;
							}else if(cd_length == 0 || cd_length > 4){
								String msg = i+"行目 細目コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
						//=====細目名、分科名、分野名を取得
						//最初の１回だけDBアクセス
//						if(saimokuMap.isEmpty()){
//							setSaimokuInfoList(connection, saimokuMap);
//						}
						//当該細目コードの細目情報をゲット
//						if(saimokuMap.containsKey(saimoku_cd)){
							//マップに存在すればそれをセット	
//							Map recordMap    = (Map)saimokuMap.get(saimoku_cd);
//							saimoku_name     = (String)recordMap.get("SAIMOKU_NAME");
//							bunka_name       = (String)recordMap.get("BUNKA_NAME");
//							bunya_name       = (String)recordMap.get("BUNYA_NAME");
//						}else{
							//存在しない場合は「その他」で登録
//							saimoku_name     = OTHER_NAME_VALUE;
//							bunka_name       = OTHER_NAME_VALUE;
//							bunya_name       = OTHER_NAME_VALUE;
//						}
					}
					//-----分割番号チェック
					if(bunkatsu_no != null && bunkatsu_no.length() != 0){
						if(!this.checkLenB(bunkatsu_no, 1)){
							String msg = i+"行目 分割番号の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//2005/04/22 追加 ここから--------------------------------------------
					//理由 分割番号がnullの場合は-にする
					else {
						bunkatsu_no = "-";
					}
					//追加 ここまで-------------------------------------------------------
											
					//-----海外分野コードチェック
					if(kaigaibunya_cd != null && kaigaibunya_cd.length() != 0){
						//数値チェック
						if(!this.checkNum(kaigaibunya_cd)){
							//半角数字で無い場合
							String msg = i+"行目 海外分野コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//半角数字の場合
							//桁数チェック（2桁で無い場合は、先頭に"0"を加える）
							int cd_length = kaigaibunya_cd.length();
							if(cd_length == 1){
								kaigaibunya_cd = "0" + kaigaibunya_cd;
							}else if(cd_length == 0 || cd_length > 2){
								String msg = i+"行目 海外分野コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
						//=====海外分野名、海外分野名略を取得
						//最初の１回だけDBアクセス
//						if(kaigaiMap.isEmpty()){
//							setKaigaibunyaInfoList(connection, kaigaiMap);
//						}
						//当該細目コードの細目情報をゲット
//						if(kaigaiMap.containsKey(kaigaibunya_cd)){
							//マップに存在すればそれをセット	
//							Map recordMap          = (Map)kaigaiMap.get(kaigaibunya_cd);
//							kaigaibunya_name       = (String)recordMap.get("KAIGAIBUNYA_NAME");
//							kaigaibunya_name_ryaku = (String)recordMap.get("KAIGAIBUNYA_NAME_RYAKU");
//						}else{
							//存在しない場合は「その他」で登録
//							kaigaibunya_name       = OTHER_NAME_VALUE;
//							kaigaibunya_name_ryaku = OTHER_NAME_VALUE;
//						}
					}
					//-----最終年度前年度の申請チェック
					if(zennendo_shinsei != null && zennendo_shinsei.length() != 0){
						if(!this.checkLenB(zennendo_shinsei, 1)){
							String msg = i+"行目 最終年度前年度の申請の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}							
					//----分担金の有無チェック
					if(buntankin != null && buntankin.length() != 0){
						if(!this.checkLenB(buntankin, 1)){
							String msg = i+"行目 分担金の有無の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}

					//-----審査員番号チェック（１～６）
					for(int k=0; k<shinsain_no.length; k++){
						//最初の１つだけは必須
						if( (k==0) && (shinsain_no[k] == null || shinsain_no[k].length() == 0) ){
							String msg = i+"行目 審査員番号"+(k+1)+"は必須項目です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						if(shinsain_no[k] != null && shinsain_no[k].length() != 0){
							//数値チェック
							if(!this.checkHan(shinsain_no[k])){
								//半角英数字で無い場合
								String msg = i+"行目 審査員番号"+(k+1)+"は半角英数字ではありません。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}else if(shinsain_no[k].length() > 7){
								String msg = i+"行目 審査員番号"+(k+1)+"の桁数が不正です。";
								mtLog.warn(msg);
								err_flg = 1;
							}else{
								//半角数字の場合
								//桁数チェック（7桁で無い場合は、先頭に"0"を加える）
								int cd_length = shinsain_no[k].length();
								if(cd_length == 1){
									shinsain_no[k] = "000000" + shinsain_no[k];
								}else if(cd_length == 2){
									shinsain_no[k] = "00000" + shinsain_no[k];
								}else if(cd_length == 3){
									shinsain_no[k] = "0000" + shinsain_no[k];
								}else if(cd_length == 4){
									shinsain_no[k] = "000" + shinsain_no[k];
								}else if(cd_length == 5){
									shinsain_no[k] = "00" + shinsain_no[k];
								}else if(cd_length == 6){
									shinsain_no[k] = "0" + shinsain_no[k];
									//err_list.add(msg);
								}
								
							//=====審査員名（漢字-姓、漢字-名、カナ-姓、カナ-名）、機関名、部局名、職名を取得
							//最初の１回だけDBアクセス
							if(shinsainMap.isEmpty()){
								setShinsainInfoList(connection, shinsainMap);
							}
							//当該審査員番号の審査員情報をゲット
							if(shinsainMap.containsKey(shinsain_no[k])){
								//マップに存在すればそれをセット	
								Map recordMap            = (Map)shinsainMap.get(shinsain_no[k]);
								shinsain_kanji_sei[k]    = (String)recordMap.get("NAME_KANJI_SEI");
								shinsain_kanji_mei[k]    = (String)recordMap.get("NAME_KANJI_MEI");
								shinsain_kana_sei[k]     = (String)recordMap.get("NAME_KANA_SEI");
								shinsain_kana_mei[k]     = (String)recordMap.get("NAME_KANA_MEI");
								shinsain_kikan_name[k]   = (String)recordMap.get("SHOZOKU_NAME");
								shinsain_bukyoku_name[k] = (String)recordMap.get("BUKYOKU_NAME");
								shinsain_shoku_name[k]   = (String)recordMap.get("SHOKUSHU_NAME");
							}else{
								//存在しない場合はエラー
								String msg = i+"行目 審査員番号"+(k+1)+"の審査員は存在しません。審査員番号="+shinsain_no[k];
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
								}
							}
						}else{
							//審査員番号がnullまたは空の場合はダミー審査員番号をセットする
							shinsain_no[k] = "@00000"+ new Integer(k+1).toString();
						}
					}
					
					//重複データ（事業ID＋申請番号（機関コード+整理番号））をチェックする
					
					//2005/04/22 削除 ここから-------------------------------------------
					//理由 重複チェック方法変更のため
					
					/*
 					uketuke_no = kikan_cd + "-" + seiri_no;
					if(shinseiDao.countShinseiData(connection, jigyo_id, uketuke_no) != 0){
						//存在しない場合はエラー
						String msg = i+"行目 当該レコードは重複しています。事業ID="+jigyo_id + ",申請番号="+uketuke_no;
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}
					*/					
					//削除 ここまで------------------------------------------------------
					//2005/04/22 追加 ここから-------------------------------------------
					//理由 重複チェック方法変更のため
					String key = jigyo_id + kikan_cd + saimoku_cd +bunkatsu_no+seiri_no;
					if(cd_list.contains(key)){
						String msg = i+"行目 Key項目(事業コード,年度,回数,研究代表者所属機関コード,細目番号,分割番号,整理番号)が重複しています。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						cd_list.add(key);
					}
					
					String uketukeNo = kikan_cd + "-"+seiri_no;
//					String jigyoId =  DateUtil.changeWareki2Seireki(nendo) + jigyo_cd + kaisu;
					
					ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();
					shinseiInfo.setUketukeNo(uketukeNo);
					shinseiInfo.setJigyoId(jigyo_id);
					//shinseiInfo.getDaihyouInfo().setShozokuCd(kikan_cd);
					shinseiInfo.getKadaiInfo().setBunkaSaimokuCd(saimoku_cd);
					shinseiInfo.getKadaiInfo().setBunkatsuNo(bunkatsu_no);
					String systemNo = null;
					try{
						systemNo = shinseiDao.selectShinseiTorikomiData(connection, shinseiInfo);
					}catch(NoDataFoundException e){
						String msg = i+"行目 該当する申請情報が存在しません。";
						mtLog.warn(msg);
						err_flg = 1;					
					}

					//2005.12.05 iso 申請データを上書きしないよう戻す。
					//2005.11.21 iso 取り込みデータで申請情報を上書きするよう変更
					shinseiPk.setSystemNo(systemNo);
//					updateInfo.setSystemNo(systemNo);
//					updateInfo.setKaigaibunyaCd(kaigaibunya_cd);
//					updateInfo.setShinseiFlgNo(zennendo_shinsei);
//					updateInfo.setBuntankinFlg(buntankin);
//					
//					KadaiInfo kadaiInfo = new KadaiInfo();
//					kadaiInfo.setKadaiNameKanji(kadai_name);
//					updateInfo.setKadaiInfo(kadaiInfo);
//					
//					DaihyouInfo daihyouInfo = new DaihyouInfo();
//					daihyouInfo.setNameKanjiSei(shimei_kanji_sei);
//					daihyouInfo.setNameKanjiMei(shimei_kanji_mei);
//					daihyouInfo.setBukyokuCd(bukyoku_cd);
//					daihyouInfo.setShokushuCd(shoku_cd);
//					updateInfo.setDaihyouInfo(daihyouInfo);
					
					checkInfo.setJigyoId(jigyo_id);
					checkInfo.setShozokuCd(kikan_cd);
								
					//エラーが無かった場合のみ登録する
					if(err_flg == 0){
						
						//2005/04/22 削除 ここから-------------------------------------------
						//理由 割り込み結果情報の取り込み処理がINSERTからUPDATEに変更されたため削除
					
						/*
						Date now = new Date();										//現在時刻
						String system_no = null;									//システム受付番号は登録直前に採番
						
						//===== 申請データ情報 =====
						ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();
						shinseiInfo.setUketukeNo(uketuke_no);
						shinseiInfo.setJigyoId(jigyo_id);
						shinseiInfo.setNendo(nendo);
						shinseiInfo.setKaisu(kaisu);
						shinseiInfo.setJigyoName(jigyo_name);
						shinseiInfo.setShinseishaId(DUMMY_SHINSEISHA_ID);			//ダミー申請者ID
						shinseiInfo.setSakuseiDate(now);							//現在時刻
						shinseiInfo.setShoninDate(now);								//現在時刻
						shinseiInfo.setJyuriDate(now);								//現在時刻
						//---申請者（研究代表者）
						DaihyouInfo daihyouInfo = shinseiInfo.getDaihyouInfo();
						daihyouInfo.setNameKanjiSei(shimei_kanji_sei);
						daihyouInfo.setNameKanjiMei(shimei_kanji_mei);
						daihyouInfo.setShozokuCd(kikan_cd);
						daihyouInfo.setShozokuName(kikan_name);
						daihyouInfo.setShozokuNameRyaku(kikan_name_ryaku);
						daihyouInfo.setBukyokuCd(bukyoku_cd);
						daihyouInfo.setBukyokuName(bukyoku_name);
						daihyouInfo.setBukyokuNameRyaku(bukyoku_name_ryaku);
						daihyouInfo.setShokushuCd(shoku_cd);
						daihyouInfo.setShokushuNameKanji(shoku_name);
						daihyouInfo.setShokushuNameRyaku(shoku_name_ryaku);
						//---研究課題
						KadaiInfo kadaiInfo = shinseiInfo.getKadaiInfo();
						kadaiInfo.setKadaiNameKanji(kadai_name);
						kadaiInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);		//基盤固定
						kadaiInfo.setShinsaKubun(shinsa_kubun);
						kadaiInfo.setBunkatsuNo(bunkatsu_no);
						kadaiInfo.setBunkaSaimokuCd(saimoku_cd);
						kadaiInfo.setBunya(bunya_name);
						kadaiInfo.setBunka(bunka_name);
						kadaiInfo.setSaimokuName(saimoku_name);
						//---基本情報（中盤）
						shinseiInfo.setBuntankinFlg(buntankin);
						shinseiInfo.setShinseiFlgNo(zennendo_shinsei);
						shinseiInfo.setKaigaibunyaCd(kaigaibunya_cd);
						shinseiInfo.setKaigaibunyaName(kaigaibunya_name);
						shinseiInfo.setKaigaibunyaNameRyaku(kaigaibunya_name_ryaku);						
						//---基本情報（後半）
						shinseiInfo.setJuriKekka(ShinseiMaintenance.FLAG_JURI_KEKKA_JURI);
						shinseiInfo.setJokyoId(StatusCode.STATUS_1st_SHINSATYU);	//申請状況[審査中]
						shinseiInfo.setSaishinseiFlg(StatusCode.SAISHINSEI_FLG_DEFAULT);
						shinseiInfo.setDelFlg(IShinseiMaintenance.FLAG_APPLICATION_NOT_DELETE);
						
						//申請データをDBに登録する（もし主キー情報が重複していた場合は再試行）
						final int TRY_REGIST_COUNT = 5;		//試行回数
						for(int x=0; x<TRY_REGIST_COUNT; x++){
							try{
								system_no = ShinseiMaintenance.getSystemNumber();	//システム受付番号
								shinseiInfo.setSystemNo(system_no);
								shinseiDao.insertShinseiDataInfo(connection, shinseiInfo);
								break;	//正常に登録できた場合はループを抜ける
							}catch(DuplicateKeyException dke){
								if(x == TRY_REGIST_COUNT){
									//試行回数繰り返しても重複した場合はエラーとする
									String msg = i+"行目 採番処理を"+TRY_REGIST_COUNT+"回試行しましたが、"
										          +"システム受付番号が重複しました。systemNo="+system_no
										          +" -> 当該レコードは処理をスキップします。";
									mtLog.warn(msg);
									//err_list.add(msg);
									err_list.add(Integer.toString(i));	//エラーの行数を確保する
									continue csvRecordCount;			//次のレコードへスキップする
								}else{
									//一応ログには出力する
									String msg = i+"行目 システム受付番号が重複しました。systemNo="+system_no
									              +" -> 再試行します。("+(x+1)+"回目)";
									mtLog.warn(msg);
									continue;							//再試行	
								}
							}
						}
						*/						
						//削除 ここまで-----------------------------------------------------
						//2005/04/22 追加 ここから------------------------------------------
						//理由 申請情報の更新処理を追加

						//2006.11.15 iso 受理前のチェックリストありの場合、申請データのみ更新されるバグ修正。
						//アップデートはチェックリストの後に移動する。
//						//2005.12.05 iso 申請データを上書きしないよう戻す。
//						//2005.11.21 iso マスタ情報で申請データを上書きするよう変更
//						//書き換える情報が多いので、通常の申請データ更新メソッドを使うべき？
////						//申請書の状況IDを10に変更
//						shinseiDao.updateStatus(connection, shinseiPk, StatusCode.STATUS_1st_SHINSATYU);
////						shinseiDao.updateTorikomiShinseiDataInfo(connection, updateInfo, StatusCode.STATUS_1st_SHINSATYU);
						String jigyoKbn = "";
//2006/04/28 追加ここから                        
//						if (IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyo_cd)){
//							jigyoKbn = IJigyoKubun.JIGYO_KUBUN_WAKATESTART;
//						}else{
//							jigyoKbn = IJigyoKubun.JIGYO_KUBUN_KIBAN;
//						}
            
						if (IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyo_cd)){
                            jigyoKbn = IJigyoKubun.JIGYO_KUBUN_WAKATESTART;
                        } else if (IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyo_cd) 
                                 ||IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyo_cd) 
                                 ||IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(jigyo_cd)	//若手Sを追加2007/5/8
                                 ||IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyo_cd))
                        {
                            jigyoKbn = IJigyoKubun.JIGYO_KUBUN_KIBAN;
                        } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyo_cd) 
                                 ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyo_cd))
                        {
                            jigyoKbn = IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI;
                        }
//苗　追加ここまで                               
						checkInfo.setJigyoKubun(jigyoKbn);
//2006/10/26 苗　修正ここから                           
//基盤Ｓ、基盤Ａ(一般・海外)、基盤Ｂ(一般・海外)、若手研究Sの場合、チェックリスト情報をチェックしない
//原因：該当種目はチェックリスト情報に登録しなくなるから
						//チェックリストのチェック
                        String jokyoId = "";
                        if (!(IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyo_cd)
                            || IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyo_cd)
                            || IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyo_cd)
                            || IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyo_cd) 
                            || IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyo_cd)
                            || IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(jigyo_cd)	//若手Sを追加2007/5/8
                            || IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyo_cd)	//促進費（年複数回応募の試行）2007/5/22
                            || IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyo_cd)		//若手スタート 2007/5/23
                        )) {
                            // String jokyoId = checkDao.checkJokyoId(connection, checkInfo, true);
                            jokyoId = checkDao.checkJokyoId(connection, checkInfo, true);
                            //if (jokyoId != null && jokyoId.equals(StatusCode.STATUS_GAKUSIN_JYURI)) {
                            if (jokyoId != null && StatusCode.STATUS_GAKUSIN_JYURI.equals(jokyoId)) {
                                // チェックリストに所属コードと事業IDの一緒なデータがあり、状況IDが6の場合は状況IDを10にする
                                checkInfo.setChangeJokyoId(StatusCode.STATUS_1st_SHINSATYU);
                                checkInfo.setJokyoId(jokyoId);
                                checkDao.updateCheckListInfo(connection, checkInfo, false);
                                // 2006/05/22 追加ここから
                            }
                            else if (StatusCode.STATUS_1st_SHINSATYU.equals(jokyoId)
                                   || StatusCode.STATUS_1st_SHINSA_KANRYO.equals(jokyoId)
                                   || StatusCode.STATUS_2nd_SHINSA_KANRYO.equals(jokyoId)) {
                                // 処理なし
                            }
                            else {
                                err_list.add(Integer.toString(i));
//2006/05/23 修正Start By　Saiここから                            
                                // String msg = i + "行目 申請リストの申請状況が受理済みではありません。";
                                String msg = i + "行目 チェックリストの応募状況が受理済みではありません。";
//2006/05/23 修正 End　By　Sai
                                mtLog.warn(msg);
                                continue;
                            }
                        }
//2006/10/26　苗　修正ここまで                           
//苗　追加まで

						//2006.11.15 iso 受理前のチェックリストありの場合、申請データのみ更新されるバグ修正。
                        //チェックリストの処理前にあったのを移動
						shinseiDao.updateStatus(connection, shinseiPk, StatusCode.STATUS_1st_SHINSATYU);
						
						//追加 ここまで-----------------------------------------------------
						
						
						//===== 審査結果情報 =====
						for(int k=0; k<shinsain_no.length; k++){
							ShinsainInfo shinsainInfo = new ShinsainInfo();
							
							shinsainInfo.setShinsainNo(shinsain_no[k]);
							//shinsainInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
							shinsainInfo.setJigyoKubun(jigyoKbn);
							shinsainInfo.setNameKanjiSei(shinsain_kanji_sei[k]);
							shinsainInfo.setNameKanjiMei(shinsain_kanji_mei[k]);
							shinsainInfo.setNameKanaSei(shinsain_kana_sei[k]);
							shinsainInfo.setNameKanaMei(shinsain_kana_mei[k]);
							shinsainInfo.setShozokuName(shinsain_kikan_name[k]);
							shinsainInfo.setBukyokuName(shinsain_bukyoku_name[k]);
							shinsainInfo.setShokushuName(shinsain_shoku_name[k]);
						/*	ShinsaKekkaInfo kekkaInfo = new ShinsaKekkaInfo();
							kekkaInfo = new ShinsaKekkaInfo();
							kekkaInfo.setSystemNo(system_no);
							kekkaInfo.setUketukeNo(uketuke_no);
							kekkaInfo.setShinsainNo(shinsain_no[k]);		
							kekkaInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);		//基盤固定
							kekkaInfo.setSeqNo(Integer.toString(k+1));
							kekkaInfo.setShinsaKubun(shinsa_kubun);
							kekkaInfo.setShinsainNameKanjiSei(shinsain_kanji_sei[k]);
							kekkaInfo.setShinsainNameKanjiMei(shinsain_kanji_mei[k]);
							kekkaInfo.setNameKanaSei(shinsain_kana_sei[k]);
							kekkaInfo.setNameKanaMei(shinsain_kana_mei[k]);
							kekkaInfo.setShozokuName(shinsain_kikan_name[k]);
							kekkaInfo.setBukyokuName(shinsain_bukyoku_name[k]);
							kekkaInfo.setShokushuName(shinsain_shoku_name[k]);
							//kekkaInfo.setJigyoId();
							kekkaInfo.setJigyoName(jigyo_name);
							kekkaInfo.setBunkaSaimokuCd(saimoku_cd);
							//kekkaInfo.setShinsaJokyo("0");								//[0]固定
						*/	//DBに登録する
							//shinsakekkaDao.insertShinsaKekkaInfo(connection, kekkaInfo);
							shinsakekkaDao.updateShinsainInfo(connection,shinsainInfo,"0",jigyo_id, systemNo, k+1);
						}
						
						//2005.12.14 iso 
						//本番環境では、旧ロジックで受理したデータ(割り振り6件)がない。
						//パフォーマンスに影響するので、以下の処理は行わない。
//						//2005/11/02 追加 ここから------------------------------------------
//						//理由 審査結果が６件（ダミーデータを含む）しか登録されていない場合、さらに６件追加する必要があるため
//						int shinsainCnt = shinseiDao.countShinsaKekkaData(connection, systemNo, IJigyoKubun.JIGYO_KUBUN_KIBAN);
//						
//						if(shinsainCnt == 6){
//							ShinsaKekkaInfo kekkaInfo = new ShinsaKekkaInfo();
//							kekkaInfo.setSystemNo(systemNo);							//システム番号
//							kekkaInfo.setUketukeNo(uketukeNo);							//申請番号
//							kekkaInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);		//事業区分
//							kekkaInfo.setShinsaKubun(shinsa_kubun);						//審査区分
//							kekkaInfo.setJigyoId(jigyo_id);								//事業ID
//							kekkaInfo.setJigyoName(jigyo_name);							//事業名
//							kekkaInfo.setBunkaSaimokuCd(saimoku_cd);					//細目番号
//							kekkaInfo.setShinsaJokyo("0");								//審査状況
//							
//							//ダミーデータを追加(シーケンス番号 = 7～12)
//							for(int k = 6; k < IShinsainWarifuri.SHINSAIN_NINZU_KIBAN; k++){
//								if(k < 9){
//									kekkaInfo.setShinsainNo("@00000"+ new Integer(k+1).toString());	//審査員番号(7桁)
//								}else{
//									kekkaInfo.setShinsainNo("@0000"+ new Integer(k+1).toString());		//審査員番号(7桁)
//								}
//								kekkaInfo.setSeqNo(new Integer(k+1).toString());
//								shinsakekkaDao.insertShinsaKekkaInfo(connection, kekkaInfo);
//							}
//							
//						}
//						//追加 ここまで-----------------------------------------------------
						
//						mtLog.info(i+"行目 正常。");
						cnt++;	//取り込み件数をカウント
						
					}else{
						//データに不備があるため、登録を行わない（行数を確保する）
						err_list.add(Integer.toString(i));
					}
				
				}
				//2005.08.30 iso 処理件数をログに出力する機能を追加
				if((i > 0) && (i % LOGCOUNT == 0)) {
					mtLog.info("基盤研究等割り振り結果マスタ取り込み" + i + "件終了"); 
				}
				
			}
			//2005.08.30 iso 処理件数をログに出力する機能を追加
			mtLog.info("基盤研究等割り振り結果マスタ取り込み全件終了"); 
			
			//マスタ管理テーブルの更新
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_WARIFURIKEKKA);
			mkInfo.setMasterName("基盤研究等割り振り結果");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");						//新規=0のみ
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);						//使用してない？		
			mkInfo.setImportErrMsg(err_list);				//エラー情報（画面で使用）
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;			
			
		} catch (IOException e) {
			throw new ApplicationException(
						"CSVファイルの読込中にエラーが発生しました。",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"マスタ取込中にDBエラーが発生しました。",
						new ErrorInfo("errors.4001"),
						e);
		} finally {
			try {
				if (success) {
					//コミット
					DatabaseUtil.commit(connection);
					mtLog.info("基盤研究等割り振り結果 取り込み処理正常終了。-> DBをコミットしました。");
				} else {
					//ロールバック
					DatabaseUtil.rollback(connection);
					mtLog.info("基盤研究等割り振り結果 取り込み処理異常終了。-> DBをロールバックしました。");
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"マスタ取込中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	//2005/04/22 追加 ここから------------------------------------------------------------
	//理由 研究者マスタ取り込みと継続課題マスタ取り込みの追加のため

	/**
	 * 研究者マスタ取込.<br />
	 * 研究者マスタ情報を取得する。<br />
	 * <b>1.CSVデータをリスト形式に変換</b><br />
	 * 引数で渡されたfileResをList形式へ変換する。Listの要素としてさらにListを持ち、
	 * その要素にCSV情報の一レコード分の情報を格納する。
	 * <br /><br />
	 * <b>2.カラム数チェック</b><br />
	 * 1.で作成したListの一つ目の要素のListの要素数が、13であるか確認する。それ以外の場合は、例外をthrowする。
	 * <br /><br />
	 * <b>3.csvファイル格納</b><br />
	 * 自クラスのkakuno()メソッドを使用してCSVファイルをサーバへ格納する。
	 * <br /><br />
	 * <b>4.マスタエクスポート</b><br />
	 * 研究者マスタをバックアップ用にエクスポートする。エクスポートは、ApplicationSettings.propertiesの、
	 * EXPORT_COMMANDで定義されているコマンドを実行することによって行われる。<br />
	 * 出力されるダンプファイルのファイル名は以下の通り。<br />
	 * MASTER_KENKYUSHA_yyyyMMddHHmmssSSS.DMP
	 * <br />
	 * <br />
	 * <b>5.各データチェック</b><br />
	 * CSVファイルの各データの必須チェック、サイズチェック、形式チェックを行う。
	 * 問題があった場合は、エラー情報を管理するListに格納したのち、次の情報へ処理を進める。
	 * なお、研究者番号、所属機関コード、部局コード、職コードは、各チェックとともに左0埋め処理を行う。<br /> 
	 * <br />
	 * <br />
	 * <b>6.研究者マスタ検索</b><br />
	 * 以下のSQLを実行することによって研究者マスタに同じ主キーの値があるかどうかをチェックする。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
		SELECT 
				KENKYU_NO,				//研究者番号
				NAME_KANJI_SEI,			//氏名（漢字等-姓）
				NAME_KANJI_MEI,			//氏名（漢字等-名）
				NAME_KANA_SEI,			//氏名（フリガナ-姓）
				NAME_KANA_MEI,			//氏名（フリガナ-名）
				SEIBETSU,				//性別
				BIRTHDAY,				//生年月日
				GAKUI,					//学位
				SHOZOKU_CD,				//所属機関コード
				BUKYOKU_CD,				//部局コード
				SHOKUSHU_CD,			//職コード
				KOSHIN_DATE,			//更新日時
				BIKO,					//備考
				DEL_FLG					//削除フラグ
		FROM 
				MASTER_KENKYUSHA KENKYU	//研究者マスタ
		WHERE 
				KENKYU.SHOZOKU_CD = ?
		AND 
				KENKYU.KENKYU_NO = ?
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<BR>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>CSVから取得した研究者番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>所属コード</td><td>CSVから取得した所属コード</td></tr>
	 *	<br />
	 * <br />
	 * <b>7-1.情報取込</b><br />
	 * 6で同じ主キーの値が取得できない場合、1.で作成したCSVファイル情報Listを研究者マスタへINSERTする。
	 * Listに格納されている要素Listには以下の情報が格納されている。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>要素番号</td><td>テーブル列名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>研究者番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>氏名（フリガナ-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>氏名（フリガナ-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>氏名（漢字等-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>氏名（漢字等-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>性別</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>生年月日</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>学位</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>所属機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>部局コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>職名コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>データ更新日</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>削除フラグ</td></tr>
	 *	</table><br />
	 *
	 * INSERTする際に実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	INSERT INTO 
		MASTER_KENKYUSHA (
				KENKYU_NO,
				NAME_KANA_SEI,
				NAME_KANA_MEI,
				NAME_KANJI_SEI,
				NAME_KANJI_MEI,
				SEIBETSU,
				BIRTHDAY,
				GAKUI,
				SHOZOKU_CD,
				BUKYOKU_CD,
				SHOKUSHU_CD,
				KOSHIN_DATE,
				BIKO,
				DEL_FLG
				)
		VALUES
				(?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>CSVから取得した研究者番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ-姓）</td><td>CSVから取得した氏名（フリガナ-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ-名）</td><td>CSVから取得した氏名（フリガナ-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（漢字等-姓）</td><td>CSVから取得した氏名（漢字等-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（漢字等-名）</td><td>CSVから取得した氏名（漢字等-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>性別</td><td>CSVから取得した性別</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>生年月日</td><td>CSVから取得した生年月日</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>学位</td><td>CSVから取得した学位</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>CSVから取得した所属機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>部局コード</td><tdCSVから取得した>部局コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>職名コード</td><td>CSVから取得した職名コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>データ更新日</td><td>CSVから取得したデータ更新日</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>備考</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>CSVから取得した削除フラグ</td></tr>
	 *	</table><br/>
	 *	<br />
	 * <b>7-2.情報取込</b><br />
	 * 6で同じ主キーの値が取得できる場合、1.で作成したCSVファイル情報Listを研究者マスタへUPDATEする。
	 * Listに格納されている要素Listには以下の情報が格納されている。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>要素番号</td><td>テーブル列名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>研究者番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>氏名（フリガナ-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>氏名（フリガナ-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>氏名（漢字等-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>氏名（漢字等-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>性別</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>生年月日</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>学位</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>所属機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>部局コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>職名コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>データ更新日</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>削除フラグ</td></tr>
	 *	</table><br />
	 *
	 * UPDATEする際に実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	UPDATE 
			MASTER_KENKYUSHA
	SET
			KENKYU_NO = ?,
			NAME_KANA_SEI = ?,
			NAME_KANA_MEI = ?,
			NAME_KANJI_SEI = ?,
			NAME_KANJI_MEI = ?,
			SEIBETSU = ?,
			BIRTHDAY = ?,
			GAKUI = ?,
			SHOZOKU_CD = ?,
			BUKYOKU_CD = ?,
			SHOKUSHU_CD = ?,
			KOSHIN_DATE = ?,
			BIKO = ?,
			DEL_FLG = ? 
	WHERE
			KENKYU_NO = ?
	AND 
			SHOZOKU_CD = ?
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>CSVから取得した研究者番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ-姓）</td><td>CSVから取得した氏名（フリガナ-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ-名）</td><td>CSVから取得した氏名（フリガナ-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（漢字等-姓）</td><td>CSVから取得した氏名（漢字等-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（漢字等-名）</td><td>CSVから取得した氏名（漢字等-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>性別</td><td>CSVから取得した性別</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>生年月日</td><td>CSVから取得した生年月日</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>学位</td><td>CSVから取得した学位</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>CSVから取得した所属機関コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>部局コード</td><tdCSVから取得した>部局コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>職名コード</td><td>CSVから取得した職名コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>データ更新日</td><td>CSVから取得したデータ更新日</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>備考</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>CSVから取得した削除フラグ</td></tr>
	 *	</table><br/>
	 * UPDATEが成功したら、SHINSEISHAINFOを検索し、研究者番号と所属コードが一致するデータがあるか確認する。
	 * ある場合は、SHINSEISHAINFOについても更新処理を行う。
	 * <br /> 
	 * <br />
	 * <b>8.マスタ管理マスタ更新</b><br />
	 * マスタ管理マスタを以下の情報で更新する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>更新情報</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ種別</td><td>2</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ名称</td><td>"研究者マスタ"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込み日時</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>件数</td><td>Insertしたレコード数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込みテーブル名</td><td>"MASTER_KENKYUSHA"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>新規・更新フラグ</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>処理状況</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取込エラーメッセージ</td><td>レコードINSERTに失敗した場合、エラーメッセージ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSVファイルパス</td><td>CSV格納先フォルダ＋CSVファイル名</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>9.マスタ管理情報返却</b><br />
	 * 7.で更新した情報を呼び出し元へ返却する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param fileRes				アップロードファイルリソース。
	 * @return						処理結果情報
	 * @throws ApplicationException	
	 */
	private MasterKanriInfo torikomiMasterKenkyusha(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException {

		int columnSize = 26;	//14;	2007/4/27仕様変更
		String ISHOKUMASK = "★";
		
		boolean success = false;
		Connection connection = null;
		//半角全角コンバータ
		HanZenConverter converter = new HanZenConverter();
		DateUtil dateUtil = new DateUtil();
		try{
			//CSVデータをリスト形式に変換する
			List dt = csvFile2List(fileRes);
			
			//カラム数チェック(1行目のみ)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//項目数が違う場合、エラーを出力し処理を終了する。
					throw new ApplicationException(
								"CSVファイルが研究者マスタの定義と異なります。",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csvファイル格納
			String table_name = "MASTER_KENKYUSHA";
			String csvPath = kakuno(fileRes, table_name);
			
			//DBのエクスポート
			String file_name = "MASTER_KENKYUSHA_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//コネクション取得
			connection = DatabaseUtil.getConnection();
			
			int cnt = 0;											//正常取込件数
			ArrayList cd_list = new ArrayList();					//重複チェック用配列
			ArrayList err_list = new ArrayList();					//取込みエラー行格納配列
			int dtsize = dt.size();									//取込全件数
			
			//DAO
			MasterKanriInfoDao     dao            = new MasterKanriInfoDao(userInfo);
			MasterKenkyushaInfoDao kenkyuDao      = new MasterKenkyushaInfoDao(userInfo);
			MasterShokushuInfoDao  shokushuDao    = new MasterShokushuInfoDao(userInfo);
			MasterBukyokuInfoDao   bukyokuDao     = new MasterBukyokuInfoDao(userInfo);
			
			//PK
			ShokushuPk             shokushuPk     = new ShokushuPk();
			BukyokuPk              bukyokuPk      = new BukyokuPk();
			KenkyushaPk            pk             = new KenkyushaPk();
			
			//INFO
			MasterKanriInfo        mkInfo         = new MasterKanriInfo();
			ShokushuInfo           shokushuInfo   = new ShokushuInfo();
			BukyokuInfo            bukyokuInfo    = new BukyokuInfo();
			KenkyushaInfo          info           = new KenkyushaInfo();
			
			//マスタ管理テーブルからデータの取得
			mkInfo = dao.selectMasterKanriInfo(connection, MASTER_KENKYUSHA);
			//Date lastUpdateDate = mkInfo.getImportDate();			
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//項目数が違う場合はエラーとして処理を飛ばす。（行数を確保する）
					String msg = i+"行目 研究者マスタのテーブル定義と一致しません。";
					mtLog.warn(msg);
					err_list.add(Integer.toString(i));	//エラーの行数を確保
					
				}else{
					//正常処理
					//エラーフラグ
					int err_flg = 0;
					
					//マスタデータの取得
					String kenkyu_no        = (String)line.get(0);	//研究者番号
					String name_kana_sei    = (String)line.get(1);	//氏名（フリガナ-姓）
					String name_kana_mei    = (String)line.get(2);	//氏名（フリガナ-名）
					String name_kanji_sei   = (String)line.get(3);	//氏名（漢字等-姓）
					String name_kanji_mei   = (String)line.get(4);	//氏名（漢字等-名）
					String seibetsu         = (String)line.get(5);	//性別
					String birthday         = (String)line.get(6);	//生年月日
					String gakui            = (String)line.get(7);	//学位
					String shozoku_cd       = (String)line.get(8);	//所属機関コード
					String bukyoku_cd       = (String)line.get(9);	//部局コード
					String shokushu_cd      = (String)line.get(10);	//職名コード
					String oubo_shikaku     = (String)line.get(11); //応募資格
					//2007/4/27　若手Sカスタマイズより追加
					String otherKikanFlg1	= (String)line.get(12);	//他の機関1（委嘱先マーク）
					String otherKikanCd1	= (String)line.get(13);	//他の機関番号1
					String otherKikanName1	= (String)line.get(14);	//他の機関名1
					String otherKikanFlg2	= (String)line.get(15);	//他の機関2（委嘱先マーク）
					String otherKikanCd2	= (String)line.get(16);	//他の機関番号2
					String otherKikanName2	= (String)line.get(17);	//他の機関名2
					String otherKikanFlg3	= (String)line.get(18);	//他の機関3（委嘱先マーク）
					String otherKikanCd3	= (String)line.get(19);	//他の機関番号3
					String otherKikanName3	= (String)line.get(20);	//他の機関名3
					String otherKikanFlg4	= (String)line.get(21);	//他の機関4（委嘱先マーク）
					String otherKikanCd4	= (String)line.get(22);	//他の機関番号4
					String otherKikanName4	= (String)line.get(23);	//他の機関名4
					//2007/4/27 追加完了
					String koshin_date      = (String)line.get(24);	//データ更新日
					String del_flg          = (String)line.get(25);	//削除フラグ
					
					Date birthdayDate = new Date();
					Date koshinDate = new Date();
					
					//研究者番号チェック
					if(StringUtil.isBlank(kenkyu_no)){
						String msg = i+"行目 研究者番号は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						//数値チェック
						if(!this.checkLenB(kenkyu_no, 8)){
							String msg = i+"行目 研究者番号の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						//研究者が8桁以下の場合は先頭に0を追加
						if (kenkyu_no.length() == 7) {
							kenkyu_no = "0" + kenkyu_no;
						} else if (kenkyu_no.length() == 6) {
							kenkyu_no = "00" + kenkyu_no;
						} else if (kenkyu_no.length() == 5) {
							kenkyu_no = "000" + kenkyu_no;
						} else if (kenkyu_no.length() == 4) {
							kenkyu_no = "0000" + kenkyu_no;
						} else if (kenkyu_no.length() == 3) {
							kenkyu_no = "00000" + kenkyu_no;
						} else if (kenkyu_no.length() == 2) {
							kenkyu_no = "000000" + kenkyu_no;
						} else if (kenkyu_no.length() == 1) {
							kenkyu_no = "0000000" + kenkyu_no;
						}
					}
					
					//氏名（フリガナ-姓）チェック
					if(StringUtil.isBlank(name_kana_sei)){
						String msg = i+"行目 氏名（フリガナ-姓）は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						//2005/9/14 半角英数も変換できるように修正
						//name_kana_sei = converter.convert(name_kana_sei);
						name_kana_sei = converter.convertAll(name_kana_sei);
						if(!this.checkLenB(name_kana_sei, 32)){
							String msg = i+"行目 氏名（フリガナ-姓）の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//氏名（フリガナ-名）チェック
					if(!StringUtil.isBlank(name_kana_mei)){
						//2005/9/14 半角英数も変換できるように修正
						//name_kana_mei = converter.convert(name_kana_mei);	//半角カナは全角に変換
						name_kana_mei = converter.convertAll(name_kana_mei);	//半角カナは全角に変換
						if(!this.checkLenB(name_kana_mei, 32)){
							String msg = i+"行目 氏名（フリガナ-名）の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//氏名（漢字等-姓）チェック
					if(StringUtil.isBlank(name_kanji_sei)){
						String msg = i+"行目 氏名（漢字等-姓）は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						name_kanji_sei = converter.convert(name_kanji_sei);	//半角カナは全角に変換
						if(!this.checkLenB(name_kanji_sei, 32)){
							String msg = i+"行目 氏名（漢字等-姓）の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//氏名（漢字等-名）チェック
					if(!StringUtil.isBlank(name_kanji_mei)){
						name_kanji_mei = converter.convert(name_kanji_mei);	//半角カナは全角に変換
						if(!this.checkLenB(name_kanji_mei, 32)){
							String msg = i+"行目 氏名（漢字等-名）の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//性別チェック
					if(StringUtil.isBlank(seibetsu)){
						String msg = i+"行目 性別は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(seibetsu, 1)){
							String msg = i+"行目 性別の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}	
						if(!(seibetsu.equals("1") || seibetsu.equals("2"))){
							String msg = i+"行目 性別の値が1(男),2(女)以外です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					
					//-----生年月日チェック
					if(StringUtil.isBlank(birthday)){
						String msg = i+"行目 生年月日は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkNum(birthday)){
							String msg = i+"行目 生年月日は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(birthday.length() != 8){
							String msg = i+"行目 生年月日が8桁ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(this.checkNum(birthday) && birthday.length() == 8){
							//2005/8/30　月と日の取得が不正の為修正
							//dateUtil.setCal(birthday.substring(0,4),birthday.substring(5,6),birthday.substring(7));
							dateUtil.setCal(birthday.substring(0,4),birthday.substring(4,6),birthday.substring(6));
							birthdayDate = dateUtil.getCal().getTime();
						}
					}
					//-----学位チェック
					if(!StringUtil.isBlank(gakui) && !gakui.equals("\"")){
						if(!this.checkLenB(gakui, 2)){
							String msg = i+"行目 学位の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!(gakui.equals("") || gakui.equals("10") || gakui.equals("11"))){
							String msg = i+"行目 学位の値が10(修士),11(博士),BLANK(該当なし)以外です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//-----所属機関コードチェック
					if(StringUtil.isBlank(shozoku_cd)){
						String msg = i+"行目 所属機関コードは必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}
					else {
						if(!this.checkNum(shozoku_cd)){
							String msg = i+"行目 所属機関コードは半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(shozoku_cd, 5)){
							String msg = i+"行目 所属機関コードの長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(shozoku_cd.length() == 4){
							shozoku_cd ="0"+shozoku_cd;
						}
						else if(shozoku_cd.length() == 3){
							shozoku_cd ="00"+shozoku_cd;
						}
						else if(shozoku_cd.length() == 2){
							shozoku_cd ="000"+shozoku_cd;
						}
						else if(shozoku_cd.length() == 1){
							shozoku_cd ="0000"+shozoku_cd;
						}
					}
					
					//-----部局コードチェック
					if(StringUtil.isBlank(bukyoku_cd)){
						String msg = i+"行目 部局コードは必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkNum(bukyoku_cd)){
							String msg = i+"行目 部局コードは半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(bukyoku_cd, 3)){
							String msg = i+"行目 部局コードの長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(bukyoku_cd.length() == 2){
							bukyoku_cd ="0"+bukyoku_cd;
						}
						else if(bukyoku_cd.length() == 1){
							bukyoku_cd ="00"+bukyoku_cd;
						}

						//部局名の取得
						bukyokuPk.setBukyokuCd(bukyoku_cd);
						try{
							bukyokuInfo = bukyokuDao.selectBukyokuInfo(connection, bukyokuPk);
						}catch(NoDataFoundException e){
							String msg = i+"行目 部局コードと一致する部局名がマスタにありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}	
									
					//-----職名コードチェック
					if(!StringUtil.isBlank(shokushu_cd)){
						if(!this.checkNum(shokushu_cd)){
							String msg = i+"行目 職名コードは半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(shokushu_cd, 2)){
							String msg = i+"行目 職名コードの長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(shokushu_cd.length() == 1){
							shokushu_cd = "0"+shokushu_cd;
						}
						//職名の取得
						shokushuPk.setShokushuCd(shokushu_cd);
						try{
							shokushuInfo = shokushuDao.selectShokushuInfo(connection,shokushuPk);	
						}catch(NoDataFoundException e){
							//取得に失敗した場合はエラーを返す
							String msg = i+"行目 職名コードと一致する職名がマスタにありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
                    
					//応募資格チェック
					if(!StringUtil.isBlank(oubo_shikaku)){
						if(!this.checkNum(shokushu_cd)){
							String msg = i+"行目 応募資格は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(oubo_shikaku, 1)){
							String msg = i+"行目 応募資格の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
//2007/4/27　仕様変更より追加
					//他の機関1（委嘱先マーク）チェック
					if (!StringUtil.isBlank(otherKikanFlg1) && !ISHOKUMASK.equals(otherKikanFlg1)){
						String msg = i+"行目 他の機関1（委嘱先マーク）は有効値ではありません。";
						mtLog.warn(msg);
						err_flg = 1;
					}
					
					//他の機関番号1チェック
					if (!StringUtil.isBlank(otherKikanCd1)){
						if(!this.checkNum(otherKikanCd1)){
							String msg = i+"行目 他の機関番号1は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(otherKikanCd1, 5)){
							String msg = i+"行目 他の機関番号1の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if (otherKikanCd1.length() < 5){
							otherKikanCd1 = StringUtil.fillLZero(otherKikanCd1, 5);
						}
					}

					//他の機関名1チェック
					if (!StringUtil.isBlank(otherKikanName1)){
						if(!this.checkLenB(otherKikanName1, 80)){
							String msg = i+"行目 他の機関名1の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//他の機関2（委嘱先マーク）チェック
					if (!StringUtil.isBlank(otherKikanFlg2) && !ISHOKUMASK.equals(otherKikanFlg2)){
						String msg = i+"行目 他の機関2（委嘱先マーク）は有効値ではありません。";
						mtLog.warn(msg);
						err_flg = 1;
					}
					
					//他の機関番号2チェック
					if (!StringUtil.isBlank(otherKikanCd2)){
						if(!this.checkNum(otherKikanCd2)){
							String msg = i+"行目 他の機関番号2は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(otherKikanCd2, 5)){
							String msg = i+"行目 他の機関番号2の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if (otherKikanCd2.length() < 5){
							otherKikanCd2 = StringUtil.fillLZero(otherKikanCd2, 5);
						}
					}

					//他の機関名2チェック
					if (!StringUtil.isBlank(otherKikanName2)){
						if(!this.checkLenB(otherKikanName2, 80)){
							String msg = i+"行目 他の機関名2の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//他の機関3（委嘱先マーク）チェック
					if (!StringUtil.isBlank(otherKikanFlg3) && !ISHOKUMASK.equals(otherKikanFlg3)){
						String msg = i+"行目 他の機関3（委嘱先マーク）は有効値ではありません。";
						mtLog.warn(msg);
						err_flg = 1;
					}
					
					//他の機関番号3チェック
					if (!StringUtil.isBlank(otherKikanCd3)){
						if(!this.checkNum(otherKikanCd3)){
							String msg = i+"行目 他の機関番号3は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(otherKikanCd3, 5)){
							String msg = i+"行目 他の機関番号3の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if (otherKikanCd3.length() < 5){
							otherKikanCd3 = StringUtil.fillLZero(otherKikanCd3, 5);
						}
					}

					//他の機関名3チェック
					if (!StringUtil.isBlank(otherKikanName3)){
						if(!this.checkLenB(otherKikanName3, 80)){
							String msg = i+"行目 他の機関名3の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//他の機関4（委嘱先マーク）チェック
					if (!StringUtil.isBlank(otherKikanFlg4) && !ISHOKUMASK.equals(otherKikanFlg4)){
						String msg = i+"行目 他の機関4（委嘱先マーク）は有効値ではありません。";
						mtLog.warn(msg);
						err_flg = 1;
					}
					

					//他の機関番号4チェック
					if (!StringUtil.isBlank(otherKikanCd4)){
						if(!this.checkNum(otherKikanCd4)){
							String msg = i+"行目 他の機関番号4は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(otherKikanCd4, 5)){
							String msg = i+"行目 他の機関番号4の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if (otherKikanCd4.length() < 5){
							otherKikanCd4 = StringUtil.fillLZero(otherKikanCd4, 5);
						}
					}

					//他の機関名4チェック
					if (!StringUtil.isBlank(otherKikanName4)){
						if(!this.checkLenB(otherKikanName4, 80)){
							String msg = i+"行目 他の機関名4の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
//2007/4/27　仕様変更より追加完了
					
					//-----データ更新日チェック(不要？）
					if(!StringUtil.isBlank(koshin_date)){
						if(!this.checkNum(koshin_date)){
							String msg = i+"行目 データ更新日は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(koshin_date != null && koshin_date.length() != 8){
							String msg = i+"行目 データ更新日が8桁ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(this.checkNum(koshin_date) && koshin_date.length() == 8){
							//2005/8/30 修正
							//dateUtil.setCal(koshin_date.substring(0,4),koshin_date.substring(5,6),koshin_date.substring(7));
							dateUtil.setCal(koshin_date.substring(0,4),koshin_date.substring(4,6),koshin_date.substring(6));
							koshinDate = dateUtil.getCal().getTime();
						}
					}
					//削除フラグチェック
					if(!StringUtil.isBlank(del_flg) && !del_flg.equals("\"")){
						if(!this.checkLenB(del_flg, 1)){
							String msg = i+"行目 削除フラグの長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}	
						if(!(del_flg.equals("0") || del_flg.equals("1"))){
							String msg = i+"行目 削除フラグの値が0,1以外です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}else{
						//削除フラグがセットされていない場合は0(削除しない)とする
						del_flg = "0";
					}
					
					//KEY項目の重複チェック
					String key = kenkyu_no + shozoku_cd;
					if(cd_list.contains(key)){
						String msg = i+"行目 Key項目(研究者番号、所属機関コード)が重複しています。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						cd_list.add(key);
					}

					//エラーが無かった場合のみ登録する
					if(err_flg == 0){
						
						//DBに登録する
						pk.setKenkyuNo(kenkyu_no);
						pk.setShozokuCd(shozoku_cd);
						info.setKenkyuNo(kenkyu_no);
						info.setShozokuCd(shozoku_cd);
						info.setNameKanjiSei(name_kanji_sei);
						info.setNameKanjiMei(name_kanji_mei);
						info.setNameKanaSei(name_kana_sei);
						info.setNameKanaMei(name_kana_mei);
						info.setSeibetsu(seibetsu);
						info.setBirthday(birthdayDate);
						info.setGakui(gakui);
						info.setBukyokuCd(bukyoku_cd);
						info.setShokushuCd(shokushu_cd);
						if (oubo_shikaku.equals("1") || oubo_shikaku.equals("2") || oubo_shikaku.equals("3")){
							info.setOuboShikaku(oubo_shikaku);
						}else{
							info.setOuboShikaku(null);
						}
						//2007/4/27 追加
						info.setOtherKikanFlg1(otherKikanFlg1);
						info.setOtherKikanCd1(otherKikanCd1);
						info.setOtherKikanName1(otherKikanName1);
						info.setOtherKikanFlg2(otherKikanFlg2);
						info.setOtherKikanCd2(otherKikanCd2);
						info.setOtherKikanName2(otherKikanName2);
						info.setOtherKikanFlg3(otherKikanFlg3);
						info.setOtherKikanCd3(otherKikanCd3);
						info.setOtherKikanName3(otherKikanName3);
						info.setOtherKikanFlg4(otherKikanFlg4);
						info.setOtherKikanCd4(otherKikanCd4);
						info.setOtherKikanName4(otherKikanName4);
						//2007/4/27 追加完了
						info.setKoshinDate(koshinDate);
						info.setDelFlg(del_flg);
						
						//主キー(研究者番号,所属コード)で検索を実行する
						//KenkyushaInfo result = new KenkyushaInfo();
						KenkyushaInfo result = null;	//登録と更新が重複処理しない為
						try{
							result = kenkyuDao.select(connection, pk);
						}catch(NoDataFoundException e){
							//一致するデータが存在しない場合は登録処理を行う
							kenkyuDao.insertKenkyushaInfo(connection, info);
						}
						//一致するデータが存在する場合は更新処理を行う
						if(result != null){
						
							kenkyuDao.updateKenkyushaInfo(connection, info);
							
							//申請者情報のチェック
							HashMap shinseiMap = new HashMap();
							ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
							searchInfo.setKenkyuNo(info.getKenkyuNo());
							searchInfo.setShozokuCd(info.getShozokuCd());
							ShinseishaMaintenance shinsei = new ShinseishaMaintenance();
							boolean existShinseishaInfo = true;
							try {
								Page shinseishaPage = shinsei.search(userInfo, searchInfo, connection);
								shinseiMap = (HashMap) shinseishaPage.getList().get(0);
							} catch (NoDataFoundException e) {
								//該当するデータがない場合はそのまま処理を終える
								existShinseishaInfo = false;
							}
							//該当するデータが申請者テーブルに存在する場合は更新処理を行う
							if (existShinseishaInfo
								&& shinseiMap.get("SHINSEISHA_ID") != null
								&& !shinseiMap.get("SHINSEISHA_ID").equals("")) {
								
								ShinseishaInfoDao shinseiDao = new ShinseishaInfoDao(userInfo);
								ShinseishaInfo shinseiInfo = new ShinseishaInfo();
								shinseiInfo.setShinseishaId((String) shinseiMap.get("SHINSEISHA_ID"));
								shinseiInfo.setBirthday(info.getBirthday());
								shinseiInfo.setBukyokuCd(info.getBukyokuCd());
								shinseiInfo.setBukyokuName(bukyokuInfo.getBukaName());
								shinseiInfo.setBukyokuNameRyaku(bukyokuInfo.getBukaRyakusyo());
								shinseiInfo.setKenkyuNo(info.getKenkyuNo());
								shinseiInfo.setNameKanaMei(info.getNameKanaMei());
								shinseiInfo.setNameKanaSei(info.getNameKanaSei());
								shinseiInfo.setNameKanjiMei(info.getNameKanjiMei());
								shinseiInfo.setNameKanjiSei(info.getNameKanjiSei());
								shinseiInfo.setShokushuCd(info.getShokushuCd());
								shinseiInfo.setShokushuNameKanji(shokushuInfo.getShokushuName());
								shinseiInfo.setShokushuNameRyaku(shokushuInfo.getShokushuNameRyaku());
								shinseiInfo.setShozokuCd(info.getShozokuCd());
								shinseiInfo.setShozokuName((String)shinseiMap.get("SHOZOKU_NAME"));
								shinseiInfo.setShozokuNameRyaku((String)shinseiMap.get("SHOZOKU_NAME_RYAKU"));
								shinseiInfo.setShozokuNameEigo((String)shinseiMap.get("SHOZOKU_NAME_EIGO"));
								shinseiInfo.setBukyokuShubetuName((String) shinseiMap.get("OTHER_BUKYOKU"));
								shinseiInfo.setBukyokuShubetuCd((String) shinseiMap.get("SHUBETU_CD"));
								shinseiInfo.setHakkoDate((Date) shinseiMap.get("HAKKO_DATE"));
								shinseiInfo.setHakkoshaId((String) shinseiMap.get("HAKKOSHA_ID"));
								shinseiInfo.setHikoboFlg(shinseiMap.get("HIKOBO_FLG").toString());
								shinseiInfo.setNameRoMei((String) shinseiMap.get("NAME_RO_MEI"));
								shinseiInfo.setNameRoSei((String) shinseiMap.get("NAME_RO_SEI"));
								shinseiInfo.setPassword((String) shinseiMap.get("PASSWORD"));
								shinseiInfo.setYukoDate((Date) shinseiMap.get("YUKO_DATE"));
								//2005/08/30 takano 削除フラグについては反映させない。（→常に削除無し[0]）
								shinseiInfo.setDelFlg("0");
								//申請者の更新
								shinseiDao.updateShinseishaInfo(connection, shinseiInfo);
							}	
						}
						cnt++;	//取り込み件数をカウント

					}else{
						//データに不備があるため、登録を行わない（行数を確保する）
						err_list.add(Integer.toString(i));
					}			
				}
				//2005.08.30 iso 処理件数をログに出力する機能を追加
				if((i > 0) && (i % LOGCOUNT == 0)) {
					mtLog.info("研究者マスタ取り込み" + i + "件終了"); 
				}
			}
			//2005.08.30 iso 処理件数をログに出力する機能を追加
			mtLog.info("研究者マスタ取り込み全件終了"); 
			
			//マスタ管理テーブルの更新
			mkInfo.setMasterShubetu(MASTER_KENKYUSHA);//マスタ種別
			mkInfo.setMasterName("研究者マスタ");//マスタ名称
			mkInfo.setImportDate(new Date());//取り込み日時
			mkInfo.setKensu(Integer.toString(cnt));//件数
			mkInfo.setImportTable(table_name);//取り込みテーブル名
			mkInfo.setImportFlg("0");//新規・更新フラグ
			mkInfo.setCsvPath(csvPath);//CSVファイルパス
			mkInfo.setImportMsg(null);//処理状況   //使用してない？		
			mkInfo.setImportErrMsg(err_list);	//エラー情報（画面で使用）
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;
						
		} catch (IOException e) {
			throw new ApplicationException(
						"CSVファイルの読込中にエラーが発生しました。",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"マスタ取込中にDBエラーが発生しました。",
						new ErrorInfo("errors.4001"),
						e);
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
				throw new ApplicationException(
					"マスタ取込中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}


	/**
	 * 継続課題マスタ取込.<br />
	 * 継続課題マスタ情報を取得する。<br />
	 * <b>1.CSVデータをリスト形式に変換</b><br />
	 * 引数で渡されたfileResをList形式へ変換する。Listの要素としてさらにListを持ち、
	 * その要素にCSV情報の一レコード分の情報を格納する。
	 * <br /><br />
	 * <b>2.カラム数チェック</b><br />
	 * 1.で作成したListの一つ目の要素のListの要素数が、5であるか確認する。それ以外の場合は、例外をthrowする。
	 * <br /><br />
	 * <b>3.csvファイル格納</b><br />
	 * 自クラスのkakuno()メソッドを使用してCSVファイルをサーバへ格納する。
	 * <br /><br />
	 * <b>4.マスタエクスポート</b><br />
	 * 研究者マスタをバックアップ用にエクスポートする。エクスポートは、ApplicationSettings.propertiesの、
	 * EXPORT_COMMANDで定義されているコマンドを実行することによって行われる。<br />
	 * 出力されるダンプファイルのファイル名は以下の通り。<br />
	 * MASTER_KEIZOKU_yyyyMMddHHmmssSSS.DMP
	 * <br />
	 * <br />
	 * <b>5.所属機関情報削除</b><br />
	 * 所属機関情報を、以下のSQLを実行することによって削除する。<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM MASTER_KIKAN
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * <br />
	 * <b>6.各データチェック</b><br />
	 * CSVファイルの各データの必須チェック、サイズチェック、形式チェックを行う。
	 * 問題があった場合は、エラー情報を管理するListに格納したのち、次の情報へ処理を進める。
	 * なお、事業コード、課題番号は、各チェックとともに左0埋め処理を行う。<br /> 
	 * <br />
	 * <br />
	 * <b>7.情報取込</b><br />
	 * データチェックで問題が無い場合、1.で作成したCSVファイル情報Listを研究者マスタへINSERTする。
	 * Listに格納されている要素Listには以下の情報が格納されている。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>要素番号</td><td>テーブル列名</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>事業コード</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>年度</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>回数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>課題番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>前年度応募可否区分</td></tr>
	 *	</table><br />
	 *
	 * INSERTする際に実行するSQLは以下の通り。（バインド変数はSQLの下の表を参照）
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
		INSERT INTO MASTER_KEIZOKU(
					JIGYO_ID,
					KADAI_NO,
					ZENNENDO_KUBUN,
					BIKO
					)					
		VALUES (?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>CSVから取得した研究者番号</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ-姓）</td><td>CSVから取得した氏名（フリガナ-姓）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（フリガナ-名）</td><td>CSVから取得した氏名（フリガナ-名）</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>氏名（漢字等-姓）</td><td>CSVから取得した氏名（漢字等-姓）</td></tr>
	 *	</table><br/>
	 *	<br />
	 * <b>8.マスタ管理マスタ更新</b><br />
	 * マスタ管理マスタを以下の情報で更新する。
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>列名</td><td>更新情報</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ種別</td><td>2</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>マスタ名称</td><td>"研究者マスタ"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込み日時</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>件数</td><td>Insertしたレコード数</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取り込みテーブル名</td><td>"MASTER_KENKYUSHA"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>新規・更新フラグ</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>処理状況</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>取込エラーメッセージ</td><td>レコードINSERTに失敗した場合、エラーメッセージ</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSVファイルパス</td><td>CSV格納先フォルダ＋CSVファイル名</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>9.マスタ管理情報返却</b><br />
	 * 7.で更新した情報を呼び出し元へ返却する。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param fileRes				アップロードファイルリソース。
	 * @return						処理結果情報
	 * @throws ApplicationException	
	 */
	private MasterKanriInfo torikomiMasterKeizoku(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException {
//		 <!-- UPDATE　START 2007/07/11 BIS 張楠 -->	
		//int columnSize = 5;
		int columnSize = 12;
//		 <!-- UPDATE　END 2007/07/11 BIS 張楠 -->	
		boolean success = false;
		Connection connection = null;
		try{
			//CSVデータをリスト形式に変換する
			List dt = csvFile2List(fileRes);
			
			//カラム数チェック(1行目のみ)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//項目数が違う場合、エラーを出力し処理を終了する。
					throw new ApplicationException(
								"CSVファイルが継続課題マスタの定義と異なります。",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csvファイル格納
			String table_name = "MASTER_KEIZOKU";
			String csvPath = kakuno(fileRes, table_name);
			
			//DBのエクスポート
			String file_name = "MASTER_KEIZOKU_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//コネクション取得
			connection = DatabaseUtil.getConnection();
			
			//取込（DELETE → INSERT処理）
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);
			
			int cnt = 0;											//正常取込件数
			ArrayList cd_list  = new ArrayList();
			ArrayList err_list = new ArrayList();					//取込みエラー行格納配列
			int dtsize = dt.size();									//取込全件数
			
			//DAO
			//JigyoKanriInfoDao      jigyoKanriDao  = new JigyoKanriInfoDao(userInfo); 2005/9/28未使用
			MasterKeizokuInfoDao   keizokuDao     = new MasterKeizokuInfoDao(userInfo);
			
			//PK
			//JigyoKanriPk           jigyoKanriPk   = new JigyoKanriPk(); 2005/9/28未使用
			
			//INFO
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			KeizokuInfo info = new KeizokuInfo();
					
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//項目数が違う場合はエラーとして処理を飛ばす。（行数を確保する）
					String msg = i+"行目 継続課題マスタのテーブル定義と一致しません。";
					mtLog.warn(msg);
					err_list.add(Integer.toString(i));	//エラーの行数を確保
					
				}else{
					//正常処理
					//エラーフラグ
					int err_flg = 0;
					
					//マスタデータの取得
					String jigyo_cd           = (String)line.get(0);	//事業コード
					String nendo              = (String)line.get(1);	//年度
					String kaisu              = (String)line.get(2);	//回数
					String kadai_no           = (String)line.get(3);	//課題番号
					String kahi_kubun         = (String)line.get(4);	//前年度応募可否区分
//					 <!-- ADD　START 2007/07/11 BIS 張楠 -->	
					String kenkyu_no   		  = (String)line.get(5);	//研究者番号
					String kadai_name_kanji   = (String)line.get(6);	//研究課題名
					String naiyakugaku1       = (String)line.get(7);	//１年目内約額
					String naiyakugaku2       = (String)line.get(8);	//２年目内約額
					String naiyakugaku3       = (String)line.get(9);	//３年目内約額
					String naiyakugaku4       = (String)line.get(10);	//４年目内約額
					String naiyakugaku5       = (String)line.get(11);	//５年目内約額
//					 <!-- ADD　END 2007/07/11 BIS 張楠 -->	
					//事業コードチェック
					if(jigyo_cd == null || jigyo_cd.equals("")){
						String msg = i+"行目 事業コードは必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						//桁数チェック
						if(!this.checkLenB(jigyo_cd, 5)){
							String msg = i+"行目 事業コードの長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						//事業コードが5桁以下の場合は先頭に0を追加
						if (jigyo_cd.length() == 4) {
							jigyo_cd = "0" + jigyo_cd;
						} else if (jigyo_cd.length() == 3) {
							jigyo_cd = "00" + jigyo_cd;
						} else if (jigyo_cd.length() == 2) {
							jigyo_cd = "000" + jigyo_cd;
						} else if (jigyo_cd.length() == 1) {
							jigyo_cd = "0000" + jigyo_cd;
						}

						//DBに事業コードが登録されているかチェックする
						try{
							MasterJigyoInfoDao.selectRecord(connection, jigyo_cd);
						}catch(NoDataFoundException e){
							String msg = i+"行目 該当する事業コードが存在しません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					
					//年度チェック
					if(nendo == null || nendo.equals("")){
						String msg = i+"行目 年度は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(nendo, 2)){
							String msg = i+"行目 年度の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
//						 <!-- ADD　START 2007/07/04 BIS 張楠 -->
						if(!this.checkNum(nendo)){
							String msg = i+"行目 年度は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
//						<!-- ADD　END　 2007/07/04 BIS 張楠 -->
					}
					
					//回数チェック
					if(kaisu == null || kaisu.equals("")){
						String msg = i+"行目 回数は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(kaisu, 1)){
							String msg = i+"行目 回数の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
//						 <!-- ADD　START 2007/07/04 BIS 張楠 -->
						if(!this.checkNum(kaisu)){
							String msg = i+"行目 回数は半角英数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
//						<!-- ADD　END　 2007/07/04 BIS 張楠 -->
					}

					//課題番号チェック
					if(kadai_no == null || kadai_no.equals("")){
						String msg = i+"行目 課題番号は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkNum(kadai_no)){
							String msg = i+"行目 課題番号は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(kadai_no, 8)){
							String msg = i+"行目 課題番号の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						else if(kadai_no.length() == 7){
							kadai_no ="0"+kadai_no;
						}
						else if(kadai_no.length() == 6){
							kadai_no ="00"+kadai_no;
						}
						else if(kadai_no.length() == 5){
							kadai_no ="000"+kadai_no;
						}
						else if(kadai_no.length() == 4){
							kadai_no ="0000"+kadai_no;
						}
						else if(kadai_no.length() == 3){
							kadai_no ="00000"+kadai_no;
						}
						else if(kadai_no.length() == 2){
							kadai_no ="000000"+kadai_no;
						}
						else if(kadai_no.length() == 1){
							kadai_no ="0000000"+kadai_no;
						}	
					}
					
					//前年度応募可否区分チェック
					if(kahi_kubun == null || kahi_kubun.equals("")){
						String msg = i+"行目 前年度応募可否区分は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(kahi_kubun, 1)){
							String msg = i+"行目 前年度応募可否区分の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}	
						if(!(kahi_kubun.equals("1") || kahi_kubun.equals("2"))){
							String msg = i+"行目 前年度応募可否区分の値が1(可),2(不可)以外です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					
//					 <!-- ADD　START 2007/07/11 BIS 張楠 -->	
					
					//研究者番号
					if(kenkyu_no != null && !"".equals(kenkyu_no)){
						if(!this.checkHan(kenkyu_no)){
							String msg = i+"行目 研究者番号は半角英数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(kenkyu_no, 8)){
							String msg = i+"行目 研究者番号の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						else if(kenkyu_no.length() == 7){
							kenkyu_no ="0"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 6){
							kenkyu_no ="00"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 5){
							kenkyu_no ="000"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 4){
							kenkyu_no ="0000"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 3){
							kenkyu_no ="00000"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 2){
							kenkyu_no ="000000"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 1){
							kenkyu_no ="0000000"+kenkyu_no;
						}	
					}
					
					//研究課題名
					if(kadai_name_kanji != null && !"".equals(kadai_name_kanji)){
						if(!this.checkLenB(kadai_name_kanji, 80)){
							String msg = i+"行目 研究課題名の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					
					//１年目内約額
					if(naiyakugaku1 != null && !"".equals(naiyakugaku1)){
						if(!this.checkNum(naiyakugaku1)){
							String msg = i+"行目 １年目内約額は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(naiyakugaku1, 7)){
							String msg = i+"行目 １年目内約額の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					
					//２年目内約額
					if(naiyakugaku2 != null && !"".equals(naiyakugaku2)){
						if(!this.checkNum(naiyakugaku2)){
							String msg = i+"行目 ２年目内約額は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(naiyakugaku2, 7)){
							String msg = i+"行目 ２年目内約額の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//３年目内約額
					if(naiyakugaku3 != null && !"".equals(naiyakugaku3)){
						if(!this.checkNum(naiyakugaku3)){
							String msg = i+"行目 ３年目内約額は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(naiyakugaku3, 7)){
							String msg = i+"行目 ３年目内約額の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//４年目内約額
					if(naiyakugaku4 != null && !"".equals(naiyakugaku4)){
						if(!this.checkNum(naiyakugaku4)){
							String msg = i+"行目 ４年目内約額は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(naiyakugaku4, 7)){
							String msg = i+"行目 ４年目内約額の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//５年目内約額
					if(naiyakugaku5 != null && !"".equals(naiyakugaku5)){
						if(!this.checkNum(naiyakugaku5)){
							String msg = i+"行目 ５年目内約額は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(naiyakugaku5, 7)){
							String msg = i+"行目 ５年目内約額の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

//					<!-- ADD　END　 2007/07/11 BIS 張楠 -->
//					<!-- UPDATE　START　 2007/07/27 BIS 張楠 -->
					/** 
					 * 	
					 * //2005/08/22 takano 重複チェックを事業ID＋課題番号に修正
					    String jigyo_id = DateUtil.changeWareki2Seireki(nendo) + jigyo_cd + kaisu;
						//KEY項目の重複チェック
						//String key = jigyo_cd+kadai_no;
						String key = jigyo_id+kadai_no;
						if(cd_list.contains(key)){
							//String msg = i+"行目 Key項目(事業コード、課題番号)が重複しています。";
							String msg = i+"行目 Key項目(事業ID、課題番号)が重複しています。";
							mtLog.warn(msg);
							err_flg = 1;
						}else{
							cd_list.add(key);
						}
						
					*/
					//2005/08/22 takano 重複チェックを事業ID＋課題番号に修正
					String jigyo_id = null;
					if(err_flg != 1){
						jigyo_id = DateUtil.changeWareki2Seireki(nendo) + jigyo_cd + kaisu;
						//KEY項目の重複チェック
						//String key = jigyo_cd+kadai_no;
						String key = jigyo_id+kadai_no;
						if(cd_list.contains(key)){
							//String msg = i+"行目 Key項目(事業コード、課題番号)が重複しています。";
							String msg = i+"行目 Key項目(事業ID、課題番号)が重複しています。";
							mtLog.warn(msg);
							err_flg = 1;
						}else{
							cd_list.add(key);
						}
					}
//					<!-- UPDATE　END　 2007/07/27 BIS 張楠 -->
					//DBに事業IDが登録されているかチェックする
//					String jigyo_id = DateUtil.changeWareki2Seireki(nendo) + jigyo_cd + kaisu;	
//					2005/08/11 不要となる						
//					jigyoKanriPk.setJigyoId(jigyo_id);
//					try{
//						jigyoKanriDao.selectJigyoKanriInfo(connection,jigyoKanriPk);
//					}catch(NoDataFoundException e){
//						String msg = i+"行目 該当する事業IDが事業管理テーブルに存在しません。";
//						mtLog.warn(msg);
//						err_flg = 1;
//					}
					
					//エラーが無かった場合のみ登録する
					if(err_flg == 0){
						info.setJigyoId(jigyo_id);
						info.setKadaiNo(kadai_no);					
						info.setZennendoKubun(kahi_kubun);
						info.setBiko(null);			
//						<!-- ADD　START　 2007/07/11 BIS 張楠 -->						
						info.setKenkyuNo(kenkyu_no);
						info.setKadaiNameKanji(kadai_name_kanji);
						info.setNaiyakugaku1(naiyakugaku1);
						info.setNaiyakugaku2(naiyakugaku2);
						info.setNaiyakugaku3(naiyakugaku3);
						info.setNaiyakugaku4(naiyakugaku4);
						info.setNaiyakugaku5(naiyakugaku5);
//						<!-- ADD　END　 2007/07/11 BIS 張楠 -->	
						keizokuDao.insertKeizokuInfo(connection, info);
						cnt++;	//取り込み件数をカウント
					}else{
						//データに不備があるため、登録を行わない（行数を確保する）
						err_list.add(Integer.toString(i));
					}			
				}
			}
			
			//マスタ管理テーブルの更新
			mkInfo.setMasterShubetu(MASTER_KEIZOKUKADAI);
			mkInfo.setMasterName("継続課題マスタ");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);			
			mkInfo.setImportErrMsg(err_list);	//エラー情報（画面で使用）
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;
						
		} catch (IOException e) {
			throw new ApplicationException(
						"CSVファイルの読込中にエラーが発生しました。",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"マスタ取込中にDBエラーが発生しました。",
						new ErrorInfo("errors.4001"),
						e);
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
				throw new ApplicationException(
					"マスタ取込中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	// 追加 ここまで-----------------------------------------------------------------

	/**
	 * キーワードマスタ取り込み処理 2005/07/21
	 * @param userInfo
	 * @param fileRes CVSファイル
	 * @return　マスタ管理情報
	 * @throws ApplicationException
	 */
	private MasterKanriInfo torikomiMasterKeyword(UserInfo userInfo, FileResource fileRes)
			throws ApplicationException
	{

		//カラム数設定
		int columnSize = 10;

		boolean success = false;
		Connection connection = null;

		try {
			//CSVデータをリスト形式に変換する
			List dt = csvFile2List(fileRes);

			//カラム数チェック(1行目のみ)
			if (dt.size() != 0) {
				ArrayList line1 = (ArrayList) dt.get(0);
				if (line1.size() != columnSize) {
					//項目数が違う場合はエラーを出力し処理を終了する。
					throw new ApplicationException("CSVファイルがキーワードマスタの定義と異なります。",
							new ErrorInfo("errors.7002"));
				}
			}

			//csvファイル格納
			String table_name = "MASTER_KEYWORD";
			String csvPath = kakuno(fileRes, table_name);

			//DBのエクスポート
			String file_name = "MASTER_KEYWORD_"
					+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[] { file_name,	table_name });
			FileUtil.execCommand(cmd);

			//コネクション取得
			connection = DatabaseUtil.getConnection();

			//取込（DELETE → INSERT処理）
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);

			int cnt = 0; //正常取込件数
			ArrayList cd_list = new ArrayList(); //取込データのキー格納配列(一意制約チェック用)
			ArrayList err_list = new ArrayList(); //取込みエラー文言格納配列
			int dtsize = dt.size(); //取込全件数

			//DAO
			MasterKeywordInfoDao keywordDao = new MasterKeywordInfoDao(userInfo);
			for (int i = 1; i <= dtsize; i++) {
				ArrayList line = (ArrayList) dt.get(i - 1);

				if (line.size() != columnSize) {
					//項目数が違う場合、エラーとして処理を飛ばす。（行数を確保する）
					String msg = i + "行目 キーワードマスタのテーブル定義と一致しません。";
					mtLog.warn(msg);
					//err_list.add(msg); //エラー内容を格納 → 保留。コメントアウト
					err_list.add(Integer.toString(i)); //エラーの行数を確保

				} else {
					//正常処理
					//エラーフラグ
					int err_flg = 0;
					int index = 0;
					//マスタデータの取得
					String bunkasaimoku_cd = (String) line.get(index++);//細目コード
					String bunkatsu_no = (String) line.get(index++);	//分割番号の追加
					String saimoku_name = (String) line.get(index++); 	//細目名
					String bunka_cd = (String) line.get(index++); 		//分科コード
					String bunka_name = (String) line.get(index++); 	//分科名
					String bunya_cd = (String) line.get(index++); 		//分野コード
					String bunya_name = (String) line.get(index++); 	//分野名
					String kei = (String) line.get(index++); 			//系
					String keywordcd = (String) line.get(index++); 		//記号
					String keyword = (String) line.get(index++); 		//キーワード

//					//----分割番号はA,B,1,2以外時,"-"を設定する
//					if (bunkatsu_no == null || bunkatsu_no.equals("")) {
//						bunkatsu_no = "-";
//					}else if (!"A".equals(bunkatsu_no) && !"B".equals(bunkatsu_no)
//							&& !"1".equals(bunkatsu_no) && !"2".equals(bunkatsu_no)){
//						bunkatsu_no = "-";
//					}

					//-----分科細目コードチェック
					//数値チェック
					if (bunkasaimoku_cd == null || bunkasaimoku_cd.equals("")) {
						String msg = i + "行目 細目コードは必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						if (!this.checkNum(bunkasaimoku_cd)) {
							//半角数字で無い場合
							String msg = i + "行目 細目コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						} else {
							//半角数字の場合
							//桁数チェック（4桁で無い場合は、先頭に"0"を加える）
							int cd_length = bunkasaimoku_cd.length();
							if (cd_length == 1) {
								bunkasaimoku_cd = "000" + bunkasaimoku_cd;
							} else if (cd_length == 2) {
								bunkasaimoku_cd = "00" + bunkasaimoku_cd;
							} else if (cd_length == 3) {
								bunkasaimoku_cd = "0" + bunkasaimoku_cd;
							} else if (cd_length == 0 || cd_length > 4) {
								String msg = i + "行目 細目コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}

					//----分割番号チェック
					if (bunkatsu_no == null || bunkatsu_no.equals("")) {
//						String msg = i + "行目 分割番号は必須項目です。";
//						mtLog.warn(msg);
//						err_flg = 1;
						bunkatsu_no = "-";
					} else {
						if (!this.checkLenB(bunkatsu_no, 1)) {
							String msg = i + "行目 分割番号の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
						//<!-- UPDATE　START 2007/07/21 BIS 張楠 -->
						//分割番号が1,2,A,B以外の場合は"-"で登録する
						/*古いコード
						if ( !bunkatsu_no.equals("1") && !bunkatsu_no.equals("2")
								&& !bunkatsu_no.equals("A") && !bunkatsu_no.equals("B")) {
							bunkatsu_no = "-";
						}
						*/
						//分割番号が1,2,3,4,5,A,B以外の場合は"-"で登録する
						if ( !bunkatsu_no.equals("1") && !bunkatsu_no.equals("2")
								&& !bunkatsu_no.equals("3") && !bunkatsu_no.equals("4") && !bunkatsu_no.equals("5")
								&& !bunkatsu_no.equals("A") && !bunkatsu_no.equals("B")) {
							bunkatsu_no = "-";
						}
						//<!-- UPDATE　END 2007/07/21 BIS 張楠 -->
					}

					//-----細目名チェック
					if (saimoku_name == null || saimoku_name.equals("")) {
						String msg = i + "行目 細目名は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					} else {
						if (!this.checkLenB(saimoku_name, 60)) {
							String msg = i + "行目 細目名の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					
					//-----分科コード
					if (bunka_cd == null || bunka_cd.equals("")) {
						String msg = i + "行目 分科コードは必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						//数値チェック
						if (!this.checkNum(bunka_cd)) {
							//半角数字で無い場合
							String msg = i + "行目 分科コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						} else {
							//半角数字の場合
							//桁数チェック（4桁で無い場合は、先頭に"0"を加える）
							int cd_length = bunka_cd.length();
							if (cd_length == 1) {
								bunka_cd = "000" + bunka_cd;
							} else if (cd_length == 2) {
								bunka_cd = "00" + bunka_cd;
							} else if (cd_length == 3) {
								bunka_cd = "0" + bunka_cd;
							} else if (cd_length == 0 || cd_length > 4) {
								String msg = i + "行目 分科コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}

					//-----分科名チェック
					if (!this.checkLenB(bunka_name, 60)) {
						String msg = i + "行目 分科名の長さが不正です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}

					//-----分野コード
					if (bunya_cd == null || bunya_cd.equals("")) {
						String msg = i + "行目 分野コードは必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						//数値チェック
						if (!this.checkNum(bunya_cd)) {
							//半角数字で無い場合
							String msg = i + "行目 分野コードは半角数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						} else {
							//半角数字の場合
							//桁数チェック（4桁で無い場合は、先頭に"0"を加える）
							int cd_length = bunya_cd.length();
							if (cd_length == 1) {
								bunya_cd = "000" + bunya_cd;
							} else if (cd_length == 2) {
								bunya_cd = "00" + bunya_cd;
							} else if (cd_length == 3) {
								bunya_cd = "0" + bunya_cd;
							} else if (cd_length == 0 || cd_length > 4) {
								String msg = i + "行目 分野コードの桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}
					
					//-----分野名チェック
					if (!this.checkLenB(bunya_name, 60)) {
						String msg = i + "行目 分野名の長さが不正です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}
					
					//-----系チェック
					if (!this.checkLenB(kei, 1)) {
						String msg = i + "行目 系の長さが不正です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}

					//----記号チェック
					if (keywordcd == null || keywordcd.equals("")) {
						String msg = i + "行目 記号は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						if (!this.checkLenB(keywordcd, 1)) {
							String msg = i + "行目 記号の長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//----キーワードチェック
					if (keyword == null || keyword.equals("")) {
						String msg = i + "行目 キーワードは必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						if (!this.checkLenB(keyword, 120)) {
							String msg = i + "行目 キーワードの長さが不正です。";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					if (err_flg == 0) {
						//KEY項目の重複チェック
						//理由 Key値に分割番号が追加されたため
						if (cd_list.contains(bunkasaimoku_cd + bunkatsu_no + keywordcd)) {
							String msg = i + "行目 Key値(細目コード,分割番号,記号)は重複しています。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						} else {
							cd_list.add(bunkasaimoku_cd + bunkatsu_no + keywordcd);
						}
					}
					
					//エラーが無かった場合のみ登録する
					if (err_flg == 0) {
						//DBに登録する
						KeywordInfo info = new KeywordInfo();
						info.setBunkaSaimokuCd(bunkasaimoku_cd);
						info.setSaimokuName(saimoku_name);
						info.setBunkaCd(bunka_cd);
						info.setBunkaName(bunka_name);
						info.setBunyaCd(bunya_cd);
						info.setBunyaName(bunya_name);
						info.setKei(kei);
						info.setBunkatsuNo(bunkatsu_no);
						info.setKeywordCd(keywordcd);
						info.setKeyword(keyword);

						keywordDao.insertKeywordInfo(connection, info);
						cnt++; //取り込み件数をカウント
					} else {
						//データに不備があるため、登録を行わない（行数を確保する）
						err_list.add(Integer.toString(i));
					}
				}
			}

			//マスタ管理テーブルの更新
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_KEYWORD);
			mkInfo.setMasterName("キーワードマスタ");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null); //使用してない？
			mkInfo.setImportErrMsg(err_list); //エラー情報（画面で使用）

			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;

		} catch (IOException e) {
			throw new ApplicationException("CSVファイルの読込中にエラーが発生しました。", new ErrorInfo(
					"errors.7002"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("マスタ取込中にDBエラーが発生しました。", new ErrorInfo(
					"errors.4001"), e);
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
				throw new ApplicationException("マスタ取込中にDBエラーが発生しました。", new ErrorInfo(
						"errors.4001"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * 領域マスタ取り込み処理 2005/08/11
	 * @param userInfo
	 * @param fileRes CVSファイル
	 * @return　マスタ管理情報
	 * @throws ApplicationException
	 */
	private MasterKanriInfo torikomiMasterRyoiki(UserInfo userInfo, FileResource fileRes)
			throws ApplicationException
	{

		//カラム数設定
		int columnSize = 9;

		boolean success = false;
		Connection connection = null;

		try {
			//CSVデータをリスト形式に変換する
			List dt = csvFile2List(fileRes);

			//カラム数チェック(1行目のみ)
			if (dt.size() != 0) {
				ArrayList line1 = (ArrayList) dt.get(0);
				if (line1.size() != columnSize) {
					//項目数が違う場合はエラーを出力し処理を終了する。
					throw new ApplicationException("CSVファイルがキーワードマスタの定義と異なります。",
							new ErrorInfo("errors.7002"));
				}
			}

			//csvファイル格納
			String table_name = "MASTER_RYOIKI";
			String csvPath = kakuno(fileRes, table_name);

			//DBのエクスポート
			String file_name = "MASTER_RYOIKI_"
					+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[] { file_name,	table_name });
			FileUtil.execCommand(cmd);

			//コネクション取得
			connection = DatabaseUtil.getConnection();

			//取込（DELETE → INSERT処理）
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);

			int cnt = 0; //正常取込件数
			ArrayList cd_list = new ArrayList(); //取込データのキー格納配列(一意制約チェック用)
			ArrayList err_list = new ArrayList(); //取込みエラー文言格納配列
			int dtsize = dt.size(); //取込全件数

			//DAO
			MasterRyouikiInfoDao ryoikiDao = new MasterRyouikiInfoDao(userInfo);
			for (int i = 1; i <= dtsize; i++) {
				ArrayList line = (ArrayList) dt.get(i - 1);

				if (line.size() != columnSize) {
					//項目数が違う場合、エラーとして処理を飛ばす。（行数を確保する）
					String msg = i + "行目 領域マスタのテーブル定義と一致しません。";
					mtLog.warn(msg);
					//err_list.add(msg); //エラー内容を格納 → 保留。コメントアウト
					err_list.add(Integer.toString(i)); //エラーの行数を確保

				} else {
					//正常処理
					//エラーフラグ
					int err_flg = 0;
					int index = 0;
					//マスタデータの取得
					String ryoiki_no = (String) line.get(index++);		//領域番号
					String ryoiki_name = (String) line.get(index++); 	//領域略略称名
					String komoku_no = (String) line.get(index++); 		//研究項目番号
					String kobou = (String) line.get(index++); 			//公募フラグ
					String keikaku = (String) line.get(index++); 		//計画研究フラグ
                    //update start ly 2006/06/30
                    String ZennendoOuboFlg = (String) line.get(index++);//前年度応募対象フラグ
                    String SettelKikanKaishi=(String)line.get(index++); //設定期間（開始年度）
                    String SettelKikanShuryo= (String)line.get(index++);//設定期間（終了年度）
					String biko = (String) line.get(index++); 			//備考
                    //update end ly 2006/06/30

					//-----領域番号チェック
					//数値チェック
					if (ryoiki_no == null || ryoiki_no.equals("")) {
						String msg = i + "行目 領域番号は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						if (!this.checkNum(ryoiki_no)) {
							//半角数字で無い場合
							String msg = i + "行目 領域番号は半角数字ではありません。";
							mtLog.warn(msg);
							err_flg = 1;
						} else {
							//半角数字の場合
							//桁数チェック（3桁で無い場合は、先頭に"0"を加える）
							int cd_length = ryoiki_no.length();
							if (cd_length == 1) {
								ryoiki_no = "00" + ryoiki_no;
							} else if (cd_length == 2) {
								ryoiki_no = "0" + ryoiki_no;
							} else if (cd_length == 0 || cd_length > 3) {
								String msg = i + "行目 領域番号の桁数が不正です。";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}

					//-----領域略略称名チェック
					if (ryoiki_name == null || ryoiki_name.equals("")) {
						String msg = i + "行目 領域略略称名は必須項目です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					} else {
						if (!this.checkLenB(ryoiki_name, 16)) {
							String msg = i + "行目 領域略略称名の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					
					//-----研究項目番号チェック
					if (komoku_no == null || komoku_no.equals("")) {
						String msg = i + "行目 研究項目番号は必須項目です。";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						//半角英数チェック
						if (!this.checkHan(komoku_no)) {
							//半角数字で無い場合
							String msg = i + "行目 研究項目番号は半角英数字ではありません。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						//桁数チェック　2005/9/28追加
						if (!this.checkLenB(komoku_no, 3)) {
							String msg = i + "行目 研究項目番号の長さが不正です。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}

					//-----公募フラグチェック
					if (!"1".equals(kobou)) {
						kobou = "0";	//1以外の場合、０を設定
					}

					//-----計画研究フラグチェック
					if (!"1".equals(keikaku)) {
						keikaku = "0";	//1以外の場合、０を設定
					}
                    
                    // -----前年度応募フラグ
                    if (!"1".equals(ZennendoOuboFlg)) {
                        ZennendoOuboFlg = "0";  //1以外の場合、０を設定
                    }
					
					//-----備考チェック
					if (!this.checkLenB(biko, 200)) {
						String msg = i + "行目 備考の長さが不正です。";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}

					if (err_flg == 0) {
						//KEY項目の重複チェック
						//理由 Key値に分割番号が追加されたため
						if (cd_list.contains(ryoiki_no + komoku_no)) {
							String msg = i + "行目 Key値(領域番号,研究項目番号)は重複しています。";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						} else {
							cd_list.add(ryoiki_no + komoku_no);
						}
					}
					//設定期間開始年度,設定期間（終了年度）チェック
                    if (!StringUtil.isBlank(SettelKikanKaishi)){
                        if (!this.checkNum(SettelKikanKaishi)) {
                            //半角数字で無い場合
                            String msg = i + "行目 設定期間(開始年度)は半角数字ではありません。";
                            mtLog.warn(msg);
                            err_flg = 1;
                        } else if (!this.checkLenB(SettelKikanKaishi, 2)) {
                            String msg = i + "行目 設定期間(開始年度)の長さが不正です。";
                            mtLog.warn(msg);
                            //err_list.add(msg);
                            err_flg = 1;
                        }
                    }
                    if (!StringUtil.isBlank(SettelKikanShuryo)){
                        if (!this.checkNum(SettelKikanShuryo)) {
                            //半角数字で無い場合
                            String msg = i + "行目 設定期間（終了年度）は半角数字ではありません。";
                            mtLog.warn(msg);
                            err_flg = 1;
                        } else if (!this.checkLenB(SettelKikanShuryo, 2)) {
                            String msg = i + "行目 設定期間（終了年度）の長さが不正です。";
                            mtLog.warn(msg);
                            //err_list.add(msg);
                            err_flg = 1;
                        }
                    }
                    if(!StringUtil.isBlank(SettelKikanKaishi)&&!StringUtil.isBlank(SettelKikanShuryo)&&err_flg != 1){
                       int kaishi=Integer.parseInt(SettelKikanKaishi) ;
                       int shuryo=Integer.parseInt(SettelKikanShuryo) ;
                       if(kaishi>shuryo){
                           String msg = i + "行目 設定期間（終了年度）に不正な日付の範囲が指定されています。";
                           mtLog.warn(msg);
                           err_flg = 1;  
                       }
                       
                    }
					//エラーが無かった場合のみ登録する
					if (err_flg == 0) {
						//DBに登録する
						RyouikiInfo info = new RyouikiInfo();
						info.setRyoikiNo(ryoiki_no);
						info.setRyoikiName(ryoiki_name);
						info.setKomokuNo(komoku_no);
						info.setKobou(kobou);
						info.setKeikaku(keikaku);
                        //update start liuyi 2006/06/30
                        info.setZennendoOuboFlg(ZennendoOuboFlg);
                        info.setSettelKikanKaishi(SettelKikanKaishi);
                        info.setSettelKikanShuryo(SettelKikanShuryo);
                        String s="平成"+SettelKikanKaishi+"年度～平成"+SettelKikanShuryo+"年度";
                        info.setSettelKikan(s);
						info.setBiko(biko);
                        //update end liuyi 2006/06/30 
						ryoikiDao.insertRyoikiInfo(connection, info);
						cnt++; //取り込み件数をカウント
					} else {
						//データに不備があるため、登録を行わない（行数を確保する）
						err_list.add(Integer.toString(i));
					}
				}
			}

			//マスタ管理テーブルの更新
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_RYOIKI);
			mkInfo.setMasterName("領域マスタ");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null); //使用してない？
			mkInfo.setImportErrMsg(err_list); //エラー情報（画面で使用）

			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;

		} catch (IOException e) {
			throw new ApplicationException("CSVファイルの読込中にエラーが発生しました。", new ErrorInfo(
					"errors.7002"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("マスタ取込中にDBエラーが発生しました。", new ErrorInfo(
					"errors.4001"), e);
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
				throw new ApplicationException("マスタ取込中にDBエラーが発生しました。", new ErrorInfo(
						"errors.4001"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

}
