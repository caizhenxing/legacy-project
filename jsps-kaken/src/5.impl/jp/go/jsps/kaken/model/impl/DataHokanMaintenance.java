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

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.impl.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.status.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.LabelValueBean;

import org.apache.commons.logging.*;

/**
 * データ保管管理を行うクラス.<br /><br />
 * 使用しているテーブル<br />
 * 　・<b>事業情報管理テーブル</b>：事業の基本事項を管理<br />
 * 　・<b>審査結果テーブル</b>：審査員割り振り結果情報と申請書の審査結果を管理<br />
 * 　・<b>添付ファイル管理テーブル</b>：申請書に添付されたファイルの格納先ディレクトリ情報を管理<br />
 * 　・<b>申請データ管理テーブル</b>：申請データの情報を管理<br />
 * 　・<b>研究組織表管理テーブル</b>：申請データの研究組織表情報を管理<br />
 * 　・<b>ラベルマスタ</b>：プルダウン等の名称、略称情報を管理<br />
 */
public class DataHokanMaintenance implements IDataHokanMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(DataHokanMaintenance.class);
	
	/** 
	 * データ保管サーバDBリンク名.<br /><br />
	 * バックアップを取るデータベースのデータベース名。<br />
	 * 具体的な値は、"@KAKENHOKANDB"
	 */
	protected static final String HOKAN_SERVER_DB_LINK = 
										ApplicationSettings.getString(ISettingKeys.HOKAN_SERVER_DB_LINK);
	
	/** 
	 * データ保管サーバUNC.<br /><br />
	 * パス文字列をUNC形式に変換する際に使用される。<br />
	 * 具体的な値は、"\\\\127.0.0.1\\shinsei_hokan"
	 */
	protected static final String HOKAN_SERVER_UNC = 
										ApplicationSettings.getString(ISettingKeys.HOKAN_SERVER_UNC);
										
	/** 
	 * UNCに変換するドライブレター.<br /><br />
	 * パス文字列をUNC形式に変換する際に使用される。<br />
	 * この値が、<b>HOKAN_SERVER_UNC</b>に変換される。<br />
	 * 具体的な値は、"${shinsei_path}"
	 */
	protected static final String DRIVE_LETTER_CONVERTED_TO_UNC = 
										ApplicationSettings.getString(ISettingKeys.DRIVE_LETTER_CONVERTED_TO_UNC);
	
	/** 
	 * データ保管サーバにコピーするディレクトリ.<br /><br />
	 * データを保管するディレクトリのパス。<br />
	 * 具体的な値は、<br />
	 * 　・HOKAN_TARGET_DIRECTORY[0]="${shinsei_path}/data/{0}"<br />
	 * 　・HOKAN_TARGET_DIRECTORY[1]="${shinsei_path}/data/xml/{0}"<br />
	 * 　・HOKAN_TARGET_DIRECTORY[2]="${shinsei_path}/data/pdf/{0}"<br />
	 */
	protected static final String[] HOKAN_TARGET_DIRECTORY = getDirectoryList();
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public DataHokanMaintenance() {
		super();
	}
	
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
	/**
	 * 保管対象ディレクトリリストの取得.<br /><br />
	 * 
	 * (1)値の取得<br />
	 * リソースバンドルから、キーに対応する値(String)を取得する。<br /><br />
	 * キーは、<b>"HOKAN_TARGET_DIRECTORY[i]"</b>,i=0,1,2<br /><br />
	 * 対応する値がない場合は、nullを返す。<br />
	 * nullが返されるまで値の取得を繰り返し、String型の配列として扱う。<br /><br />
	 * 
	 * 
	 * (2)配列の返却<br />
	 * nullが返されたところで、この配列をtoArrayメソッドでソートして返却する。
	 * @return 保管対象ディレクトリのString[]
	 */
	private static String[] getDirectoryList()
	{
		List valueList = new ArrayList();
		for(int i=0;;i++){
			String key   = ISettingKeys.HOKAN_TARGET_DIRECTORY + "[" + i + "]";
			String value = ApplicationSettings.getString(key, false); 
			
			if(value == null){
				break;				//値がnullになった（キーが存在しなくなった）時点でループを抜ける
			}else{
				valueList.add(value);
			}
		}
		return (String[])valueList.toArray(new String[0]);		
	}
	
	
	
	/**
	 * ファイル保管メソッド.<br /><br />
	 * 
	 * (1)保管するディレクトリの確認<br />
	 * "HOKAN_TARGET_DIRECTORY"が定義されていない場合には、ログを出力する。<br /><br />
	 * 
	 * 
	 * (2)コピー元のディレクトリを指す文字列の作成<br />
	 * String<b>"HOKAN_TARGET_DIRECTORY[i]"</b>の値の先頭の"${shinsei_path}"がApplicationSettings.propertiesに設定された値で置き換えられ、
	 * 語尾に、引数であるString<b>"jigyoId"</b>の値を連結させたものを、<b>"fromPath"</b>とする。<br /><br />
	 * 
	 * 
	 * (3)コピー先のディレクトリを指す文字列の作成<br />
	 * fromPathがString<b>"DRIVE_LETTER_CONVERTED_TO_UNC"</b>を含む文字列であれば、
	 * fromPath内のDRIVE_LETTER_CONVERTED_TO_UNCの部分を<b>"HOKAN_SERVER_UNC"</b>に置換し、得た文字列を<b>"toPath"</b>とする。<br /><br />
	 * 
	 * 
	 * (4)ディレクトリのコピー実行<br />
	 * Stringである、2,3の値をファイルオブジェクトに変換する。<br />
	 * fromFileからtoFileへ事業ごとのディレクトリのコピーを行う。コピー先に同じファイルが存在していた場合は上書きする。
	 * この際、コピーに失敗した場合はログを出力し、例外をthrowする。<br /><br />
	 * 
	 * 
	 * (5)繰り返し<br />
	 * 項番2～4を、保管対象ディレクトリの数だけ繰り返す。
	 * @param jigyoId 事業ID
	 * @return
	 * @throws Exception
	 */
	private void fileHokan(String jigyoId) throws Exception
	{
		if(HOKAN_TARGET_DIRECTORY.length == 0){
			if(log.isDebugEnabled()){
				String msg = "保管対象となるディレクトリが設定されていない。-> ファイル保管を行わない。";
				log.info(msg);
			}
			return;
		}
		
		//保管対象ディレクトリ数繰り返す
		for(int i=0; i<HOKAN_TARGET_DIRECTORY.length; i++){
			
			String fromPath = MessageFormat.format(HOKAN_TARGET_DIRECTORY[i], new String[]{jigyoId});
			String toPath   = StringUtil.substrReplace(fromPath, DRIVE_LETTER_CONVERTED_TO_UNC, HOKAN_SERVER_UNC);
			File   fromFile = new File(fromPath);
			File   toFile   = new File(toPath);
			
			//コピー元ディレクトリが存在しなかった場合は処理を飛ばす
			if(!fromFile.exists()){
				if(log.isDebugEnabled()){
					String msg = "コピー元ディレクトリが存在しません。fromFile=" + fromFile;
					log.info(msg);
				}
				continue;				
			}
			
			//ディレクトリコピー
			if(!FileUtil.directoryCopy(fromFile, toFile)){
				String msg = "ファイルコピー中にエラーが発生しました。\n処理が途中で終了している可能性があります。";
				if(log.isDebugEnabled()){
					log.error(msg);
				}
				throw new IOException(msg);
			}
			
		}
		
	}
	
//2006/11/22 苗　追加ここから    
    /**
     * ファイル保管メソッド(領域計画書のみ).<br /><br />
     * 
     * (1)保管するディレクトリの確認<br />
     * "HOKAN_TARGET_DIRECTORY"が定義されていない場合には、ログを出力する。<br /><br />
     * 
     * 
     * (2)コピー元のディレクトリを指す文字列の作成<br />
     * String<b>"HOKAN_TARGET_DIRECTORY[i]"</b>の値の先頭の"${shinsei_path}"がApplicationSettings.propertiesに設定された値で置き換えられ、
     * 語尾に、引数であるString<b>"jigyoId" + "_RG"</b>の値を連結させたものを、<b>"fromPathRG"</b>とする。<br /><br />
     * 
     * 
     * (3)コピー先のディレクトリを指す文字列の作成<br />
     * fromPathRGがString<b>"DRIVE_LETTER_CONVERTED_TO_UNC"</b>を含む文字列であれば、
     * fromPathRG内のDRIVE_LETTER_CONVERTED_TO_UNCの部分を<b>"HOKAN_SERVER_UNC"</b>に置換し、得た文字列を<b>"toPathRG"</b>とする。<br /><br />
     * 
     * 
     * (4)ディレクトリのコピー実行<br />
     * Stringである、2,3の値をファイルオブジェクトに変換する。<br />
     * fromFileRGからtoFileRGへ事業ごとのディレクトリのコピーを行う。コピー先に同じファイルが存在していた場合は上書きする。
     * この際、コピーに失敗した場合はログを出力し、例外をthrowする。<br /><br />
     * 
     * 
     * (5)繰り返し<br />
     * 項番2～4を、保管対象ディレクトリの数だけ繰り返す。
     * @param jigyoId 事業ID
     * @return
     * @throws Exception
     */
    private void fileHokanGaiyo(String jigyoId) throws Exception {

        if (HOKAN_TARGET_DIRECTORY.length == 0) {
            if (log.isDebugEnabled()) {
                String msg = "保管対象となるディレクトリが設定されていない。-> ファイル保管を行わない。";
                log.info(msg);
            }
            return;
        }

        //保管対象ディレクトリ数繰り返す
        for (int i = 0; i < HOKAN_TARGET_DIRECTORY.length; i++) {
            String formPathRG = MessageFormat.format(HOKAN_TARGET_DIRECTORY[i], new String[] { jigyoId
                    + "_RG" });
            String toPathRG = StringUtil.substrReplace(formPathRG, DRIVE_LETTER_CONVERTED_TO_UNC,
                    HOKAN_SERVER_UNC);
            File fromFileRG = new File(formPathRG);
            File toFileRG = new File(toPathRG);

            //コピー元ディレクトリが存在しなかった場合は処理を飛ばす
            if (!fromFileRG.exists()) {
                if (log.isDebugEnabled()) {
                    String msg = "コピー元ディレクトリが存在しません。fromFileRG=" + fromFileRG;
                    log.info(msg);
                }
                continue;
            }

            //ディレクトリコピー
            if (!FileUtil.directoryCopy(fromFileRG, toFileRG)) {
                String msg = "ファイルコピー中にエラーが発生しました。\n処理が途中で終了している可能性があります。";
                if (log.isDebugEnabled()) {
                    log.error(msg);
                }
                throw new IOException(msg);
            }

        }
    }
//2006/11/22　苗　追加ここまで 
	
	
	//---------------------------------------------------------------------
	// implement IDataHokanMaintenance
	//---------------------------------------------------------------------	
	
	/**
	 * テーブルデータの更新・保管.<br /><br />
	 * テーブルデータの更新と、バックアップ用のデータベースとの同期を取る。<br /><br />
	 * 
	 * 
	 * <b>1.事業管理テーブルの更新</b><br /><br />
	 * 
	 * 事業管理テーブルの<br />
	 * 　・データ保管日<br />
	 * 　・保管有効期限<br />
	 * の更新を行う。<br /><br />
	 * 
	 * (1)jigyoIdの取得<br />
	 * 　第二引数"jigyouKanriPk"より、String<b>"jigyoId"</b>を取得する。<br /><br />
	 * 
	 * 
	 * (2)ファイルの保管<br />
	 * 　メソッド<b>"fileHokan()"</b>を、引数に"jigyoId"を与えて実行し、ファイルの保管を行う。<br />
	 * 　メソッド実行中にエラーが発生した場合は、例外をthrowする。<br /><br />
	 * 
	 * 
	 * (3)DBから値を取得<br />
	 * 　以下のSQL文を発行する。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.JIGYO_ID			--事業ID
	 *		,A.NENDO			--年度
	 *		,A.KAISU			--回数
	 *		,A.JIGYO_NAME		--事業名
	 *		,A.JIGYO_KUBUN			--事業区分
	 *		,A.SHINSA_KUBUN			--審査区分
	 *		,A.TANTOKA_NAME			--業務担当課
	 *		,A.TANTOKAKARI			--業務担当係名
	 *		,A.TOIAWASE_NAME		--問い合わせ先担当者名
	 *		,A.TOIAWASE_TEL			--問い合わせ先電話番号
	 *		,A.TOIAWASE_EMAIL		--問い合わせ先E-mail
	 *		,A.UKETUKEKIKAN_START	--学振受付期間（開始）
	 *		,A.UKETUKEKIKAN_END		--学振受付期間（終了）
	 *		,A.SHINSAKIGEN			--審査期限
	 *		,A.TENPU_NAME			--添付文書名
	 *		,A.TENPU_WIN		--添付ファイル格納フォルダ（Win）
	 *		,A.TENPU_MAC		--添付ファイル格納フォルダ（Mac）
	 *		,A.HYOKA_FILE_FLG	--評価用ファイル有無
	 *		,A.HYOKA_FILE		--評価用ファイル格納フォルダ
	 *		,A.KOKAI_FLG		--公開フラグ
	 *		,A.KESSAI_NO		--公開決裁番号
	 *		,A.KOKAI_ID			--公開確定者ID
	 *		,A.HOKAN_DATE		--データ保管日
	 *		,A.YUKO_DATE		--保管有効期限
	 *		,A.BIKO				--備考
	 *		,A.DEL_FLG			--削除フラグ
	 *	FROM
	 *		JIGYOKANRIA
	 *	WHERE
	 *		JIGYO_ID=? </pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数primaryKeysの変数JigyoIdを使用する。</td></tr>
	 * </table><br />
	 * 　取得した値を<b>"JigyoKanriInfo"</b>のオブジェクトに格納し、取得する。<br />
	 * 　その後、<br />
	 * 　　・データ保管日(WASから取得した現在の日付)<br />
	 * 　　・保管有効期限(第三引数のDate<b>"yukokigen"</b>)<br />
	 * 　を取得して、このオブジェクトに格納し、更新する。<br /><br />
	 * 
	 * 
	 * (4)事業管理テーブルの更新（業務処理サーバ）<br /><br />
	 * 　以下のSQL文を発行し、テーブルの更新を行う。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	UPDATE JIGYOKANRI A 
	 *		 SET	
	 *		 A.JIGYO_ID = ?			--事業ID
	 *		,A.NENDO = ?			--年度
	 *		,A.KAISU = ?			--回数
	 *		,A.JIGYO_NAME = ?		--事業名
	 *		,A.JIGYO_KUBUN = ?		--事業区分
	 *		,A.SHINSA_KUBUN = ?		--審査区分
	 *		,A.TANTOKA_NAME = ?		--業務担当課
	 *		,A.TANTOKAKARI = ?		--業務担当係名
	 *		,A.TOIAWASE_NAME = ?	--問い合わせ先担当者名
	 *		,A.TOIAWASE_TEL = ?		--問い合わせ先電話番号
	 *		,A.TOIAWASE_EMAIL = ?		--問い合わせ先E-mail
	 *		,A.UKETUKEKIKAN_START = ?	--学振受付期間（開始）
	 *		,A.UKETUKEKIKAN_END = ?		--学振受付期間（終了）
	 *		,A.SHINSAKIGEN = ?			--審査期限
	 *		,A.TENPU_NAME = ?			--添付文書名
	 *		,A.TENPU_WIN = ?		--添付ファイル格納フォルダ（Win）
	 *		,A.TENPU_MAC = ?		--添付ファイル格納フォルダ（Mac）
	 *		,A.HYOKA_FILE_FLG = ?	--評価用ファイル有無
	 *		,A.HYOKA_FILE = ?		--評価用ファイル格納フォルダ
	 *		,A.KOKAI_FLG = ?			--公開フラグ
	 *		,A.KESSAI_NO = ?			--公開決裁番号
	 *		,A.KOKAI_ID = ?			--公開確定者ID
	 *		,A.HOKAN_DATE = ?			--データ保管日
	 *		,A.YUKO_DATE = ?			--保管有効期限
	 *		,A.BIKO = ?			--備考
	 *		,A.DEL_FLG = ?			--削除フラグ
	 *	WHERE
	 *		 JIGYO_ID = ?			--事業ID  </pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>項番4で取得したinfoの変数(JigyoId)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>年度</td><td>項番4で取得したinfoの変数(Nendo)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>回数</td><td>項番4で取得したinfoの変数(Kaisu)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業名</td><td>項番4で取得したinfoの変数(JigyoName)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>項番4で取得したinfoの変数(JigyoKubun)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査区分</td><td>項番4で取得したinfoの変数(ShinsaKubun)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当課</td><td>項番4で取得したinfoの変数(TantokaName)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>業務担当係名</td><td>項番4で取得したinfoの変数(TantoKakari)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>問い合わせ先担当者名</td><td>項番4で取得したinfoの変数(ToiawaseName)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>問い合わせ先電話番号</td><td>項番4で取得したinfoの変数(ToiawaseTel)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>問い合わせ先E-mail</td><td>項番4で取得したinfoの変数(ToiawaseEmail)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>学振受付期間（開始）</td><td>項番4で取得したinfoの変数(UketukekikanStart)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>学振受付期間（終了）</td><td>項番4で取得したinfoの変数(UketukekikanEnd)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査期限</td><td>項番4で取得したinfoの変数(ShinsaKigen)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>添付文書名</td><td>項番4で取得したinfoの変数(TenpuName)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>添付ファイル格納フォルダ（Win）</td><td>項番4で取得したinfoの変数(TenpuWin)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>添付ファイル格納フォルダ（Mac）</td><td>項番4で取得したinfoの変数(TenpuMac)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>評価用ファイル有無</td><td>項番4で取得したinfoの変数(HyokaFileFlg)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>評価用ファイル格納フォルダ</td><td>項番4で取得したinfoの変数(HyokaFile)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>公開フラグ</td><td>項番4で取得したinfoの変数(KokaiFlg)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>公開決裁番号</td><td>項番4で取得したinfoの変数(KessaiNo)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>公開確定者ID</td><td>項番4で取得したinfoの変数(KokaiID)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>データ保管日</td><td>項番4で取得したinfoの変数(HokanDate)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>保管有効期限</td><td>項番4で取得したinfoの変数(YukoDate)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>項番4で取得したinfoの変数(getBiko)を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>削除フラグ</td><td>"0"を使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>項番4で取得したinfoの変数(JigyoId)を使用する。</td></tr>
	 * </table><br />
	 * 　エラーが発生した場合には、例外をthrowする。<br /><br /><br />
	 * 
	 * 
	 * <b>2.テーブルデータの同期を取る.</b><br /><br />
	 * 
	 * 以下の作業で、バックアップ用のデータベースのデータの同期を取る。対象となるテーブルは、<br />
	 * 　・事業情報管理テーブル:JIGYOKNARI<br />
	 * 　・審査結果テーブル:SHINSAKEKKA<br />
	 * 　・添付ファイルテーブル:TENPUFILEINFO<br />
	 * 　・申請データテーブル:SHINSEIDATAKANRI<br />
	 * 　・研究組織用テーブル:KENKYUSOSHIKIKANRI<br />
	 * である。"dbLink"は、"<b>HOKAN_SERVER_DB_LINK</b>"である。<br />
	 * (以下のSQL文の※に入るのは、ここで挙げた5つのテーブルのテーブル名)<br /><br />
	 * 
	 * (1)データの削除<br />
	 * 　データの物理削除を行う。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	DELETE 
	 *	FROM
	 *		(※)<b>テーブル名</b> "dbLink"
	 *	WHERE
	 *		JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>jigyoKanriPkの変数JigyoIdを使用する。</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * (2)データの挿入<br />
	 * データの挿入を行う。(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	INSERT INTO (※)<b>テーブル名</b> "dbLink"
	 *		SELECT
	 *			*
	 *		FROM
	 * 			(※)<b>テーブル名</b>
	 * 		WHERE
	 * 			JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数のJigyoIdを使用する。</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * (3)申請データ保管件数の取得<br />
	 * 申請データ保管時のみ、件数の取得を行う。<br />
	 * 他の4つのテーブルに関しては、この処理は行わない。<br />
	 * (バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT 
	 *		COUNT(SYSTEM_NO) 
	 *	FROM 
	 *		SHINSEIDATAKANRI "dbLink"
	 *	WHERE
	 *		JIGYO_ID = ?  </pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数のJigyoIdを使用する。</td></tr>
	 * </table><br /><br /><br />
	 * 
	 * 
	 * <b>3.値の返却とロールバック</b><br /><br />
	 * 
	 * 
	 * (1)データ保管件数の返却<br />
	 * データ保管件数の返却を行う。<br /><br />
	 * 
	 * 
	 * (2)ロールバックの実行<br />
	 * この処理にたどり着く前に例外が発生した場合は、例外をthrowし、ロールバックを行う。<br />
	 * ロールバックが正常に行われなかった場合には、例外をthrowする。
	 * @param userInfo UserInfo
	 * @param jigyoKanriPk JigyoKanriPk
	 * @param yukoKigen データ保管の有効期限のDate
	 * @return 保管データの件数を示すint
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#dataHokanInvoke(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriPk, java.util.Date)
	 */
	public int dataHokanInvoke(
		UserInfo userInfo,
		JigyoKanriPk jigyoKanriPk,
		Date yukoKigen)
		throws NoDataFoundException, ApplicationException
	{
		//事業ID		
		String jigyoId = jigyoKanriPk.getJigyoId();
        //事業CD
        String jigyoCd = jigyoId.substring(2, 7);
		

        //=====ファイル保管=====
		try{
			fileHokan(jigyoId);
			//2006/11/22 苗　追加ここから            
            if (IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(jigyoCd)) {
                fileHokanGaiyo(jigyoId);
            }
            //2006/11/22 苗　追加ここまで            
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException(
				"ファイル保管中にエラーが発生しました。",
				new ErrorInfo("errors.7003"),
				e);
		}
		
		
		//=====DB保管=====
		Connection connection = null;
		boolean success = false;
		try{
			connection = DatabaseUtil.getConnection();

			//-----事業管理テーブルの更新（業務処理サーバ）-----
			try{
				
				JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);

				//--更新--
				JigyoKanriInfo info = dao.selectJigyoKanriInfo(connection, jigyoKanriPk);

				//削除フラグが１の場合、エラーとする2007/5/25
				if ("1".equals(info.getDelFlg())){
					throw new ApplicationException(
							"事業情報データは既に削除されました。",
							new ErrorInfo("errors.5002"));
				}
				
//2007/5/25 保管日付と保管期限のみを更新する
//				info.setHokanDate(new Date());		//データ保管日
//				info.setYukoDate(yukoKigen);		//データ保管有効期限
//				dao.updateJigyoKanriInfo(connection, info);
				dao.updateHokanInfo(connection, jigyoKanriPk, yukoKigen);
//2007/5/25 修正完了			
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"事業情報データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4009"),
					e);
			}
			
			//-----事業管理テーブルの保管-----
			try{
				JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				dao.deleteJigyoKanriInfoNoCheck(connection, jigyoKanriPk);
				dao.copy2HokanDB(connection, jigyoId);
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"事業情報データ保管中にDBエラーが発生しました。",
					new ErrorInfo("errors.4009"),
					e);
			}
						
			//-----審査結果テーブルの保管-----
			try{
				ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				dao.deleteShinsaKekkaInfo(connection, jigyoId);
				dao.copy2HokanDB(connection, jigyoId);
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"審査結果データ保管中にDBエラーが発生しました。",
					new ErrorInfo("errors.4009"),
					e);
			}
						
			//-----添付ファイルテーブルの保管-----
			try{
				TenpuFileInfoDao dao = new TenpuFileInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				dao.deleteTenpuFileInfos(connection, jigyoId);
				dao.copy2HokanDB(connection, jigyoId);
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"添付ファイルデータ保管中にDBエラーが発生しました。",
					new ErrorInfo("errors.4009"),
					e);
			}
			
			//-----申請データテーブルの保管-----
			int hokanDataCount = 0;
			try{
				ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				dao.deleteShinseiDataInfo(connection, jigyoId);
				dao.copy2HokanDB(connection, jigyoId);
				
				//保管件数を取得する
				hokanDataCount = dao.countShinseiDataByJigyoID(connection, jigyoId);
				
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"申請書管理データ保管中にDBエラーが発生しました。",
					new ErrorInfo("errors.4009"),
					e);
			}
			
			//-----研究組織表テーブルの保管-----
			try{
				KenkyuSoshikikanriDao dao = new KenkyuSoshikikanriDao(userInfo, HOKAN_SERVER_DB_LINK);
				dao.deleteKenkyuSoshikiKanriInfo(connection, jigyoId);
				dao.copy2HokanDB(connection, jigyoId);
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"添付ファイルデータ保管中にDBエラーが発生しました。",
					new ErrorInfo("errors.4009"),
					e);
			}	
			
// 2006/10/24 add by liucy start
            // -----領域計画書データテーブルの保管-----
            if (IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(jigyoCd)) {
                try {
                    RyoikiKeikakushoInfoDao dao = new RyoikiKeikakushoInfoDao(
                            userInfo, HOKAN_SERVER_DB_LINK);
                    dao.deleteRyoikiKeikakushoInfo(connection, jigyoId);
                    dao.copy2HokanDB(connection, jigyoId);
                } catch (DataAccessException e) {
                    success = false;
                    throw new ApplicationException(
                            "領域計画書管理データ保管中にDBエラーが発生しました。", new ErrorInfo(
                                    "errors.4009"), e);
                }
            }
// 2006/10/24 add by liucy end
			
			//ここまで処理が進めば正常と判断できる
			success = true;
			return hokanDataCount;
			
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"DB保管中にエラーが発生しました。",
					new ErrorInfo("errors.4009"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
	}	
	
	
	
	
	/**
	 * ページ情報を取得する.<br /><br />
	 * 
	 * 第二引数で渡された検索条件に基づき、申請書一覧のページ情報を取得する。<br />
	 * 申請書一覧のページ情報には、当該申請データの申請状況名（申請状況を表す文字列）がセットされる。<br /><br />
	 * 
	 * 　(1)検索条件からSQL文を作成し、結果を取得する。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *SELECT 
	 *	A.SYSTEM_NO,				--システム受付番号
	 *	A.UKETUKE_NO,				--申請番号
	 *	A.JIGYO_ID,				--事業ID
	 *	A.NENDO,					--年度
	 *	A.KAISU,					--回数
	 *	A.JIGYO_NAME,				--事業名
	 *	A.SHINSEISHA_ID,				--申請者ID
	 *	A.SAKUSEI_DATE,				--申請書作成日
	 *	A.SHONIN_DATE,				--所属機関承認日
   	 * 	A.NAME_KANJI_SEI,				--申請者氏名（漢字等-姓）
	 *	A.NAME_KANJI_MEI,				--申請者氏名（漢字等-名）
	 *	A.KENKYU_NO,				--申請者研究者番号
	 * 	A.SHOZOKU_CD,				--所属機関コード
	 *	A.SHOZOKU_NAME,				--所属機関名
	 *	A.SHOZOKU_NAME_RYAKU,			--所属機関名（略称）
	 *	A.BUKYOKU_NAME,				--部局名
	 *	A.BUKYOKU_NAME_RYAKU,			--部局名（略称）
	 *	A.SHOKUSHU_NAME_KANJI,			--職名
	 *	A.SHOKUSHU_NAME_RYAKU,			--職名（略称）
	 *	A.KADAI_NAME_KANJI,			--研究課題名(和文）
	 *	A.JIGYO_KUBUN,				--事業区分
	 *	A.KEKKA1_ABC,				--1次審査結果(ABC)
	 *	A.KEKKA1_TEN,				--1次審査結果(点数)
	 *	A.KEKKA1_TEN_SORTED,			--1次審査結果(点数順)
	 *	A.KEKKA2,					--2次審査結果
	 *	A.JOKYO_ID,				--申請状況ID
	 *	A.SAISHINSEI_FLG,				--再申請フラグ
	 *	A.KEI_NAME_RYAKU,				--系統の区分（略称）
	 *	A.KANTEN_RYAKU,				--推薦の観点（略称）
	 *	A.NENREI,					--年齢
	 *	DECODE
	 *	(
	 *	 NVL(A.SUISENSHO_PATH,'null') 
	 *	 ,'null','FALSE'		--推薦書パスがNULLのとき
	 *	 ,'TRUE'				--推薦書パスがNULL以外のとき
	 *	) SUISENSHO_FLG, 		--推薦書登録フラグ
	 *	B.UKETUKEKIKAN_END,			--学振受付期限（終了）
	 *	B.HOKAN_DATE,				--データ保管日
	 *	B.YUKO_DATE,				--保管有効期限
	 *	DECODE
	 *	(
	 *	 SIGN( 
	 *	      TO_DATE( TO_CHAR(B.UKETUKEKIKAN_END,'YYYY/MM/DD'), 'YYYY/MM/DD' ) 
	 *	    - TO_DATE( TO_CHAR(SYSDATE           ,'YYYY/MM/DD'), 'YYYY/MM/DD' ) 
	 *	     )
	 *	 ,0 , 'TRUE'			--現在時刻と同じ場合
	 *	 ,1 , 'TRUE'			--現在時刻の方が受付期限より前
	 *	 ,-1, 'FALSE'			--現在時刻の方が受付期限より後
	 *	) UKETUKE_END_FLAG,		--学振受付期限（終了）到達フラグ
	 *	DECODE
	 *	(
	 *	 NVL(A.PDF_PATH,'null') 
	 *	 ,'null','FALSE'				--PDFの格納パスがNULLのとき
	 * 	 ,      'TRUE'				--PDFの格納パスがNULL以外のとき
	 *	) PDF_PATH_FLG, 				--PDFの格納パスフラグ
	 *	DECODE
	 *	(
	 *	 NVL(C.SYSTEM_NO,'null') 
	 *	 ,'null','FALSE'		--添付ファイルがNULLのとき
	 *	 ,      'TRUE'			--添付ファイルがNULL以外のとき
	 *	) TENPUFILE_FLG 		--添付ファイルフラグ
	 *	FROM
	 *	SHINSEIDATAKANRI A,			--申請データ管理テーブル
	 *	JIGYOKANRI B,				--事業情報管理テーブル
	 *	(SELECT DISTINCT SYSTEM_NO FROM TENPUFILEINFO
	 *	 WHERE TENPU_PATH IS NOT NULL) C
	 *		--添付ファイルテーブル（添付ファイルパスがnullではない場合のみ）
	 *	WHERE
	 *	A.JIGYO_ID = B.JIGYO_ID		--事業IDが同じもの
	 *	AND
	 *	A.SYSTEM_NO = C.SYSTEM_NO()
	 *		--テーブルの連結(Cに連結するデータがない場合も表示) </pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>第二引数primaryKeysの変数JigyoIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 　(2)Page内のリストを取得し、各レコードマップに対して申請状況名をセットする。<br />
	 * 　　いかのSQL文より公開フラグを取得し、それを元に申請状況名を取得する。<br />
	 * 　　・申請状況名…実行するユーザに該当する、申請状況を表す文字列。<br />
	 * 　　・申請状況を表す文字列…実行するユーザ、事業の公開前後、申請状況IDに該当する文字列。<br />
	 * 　　文字列に{KEKKA1}が含まれていた場合は、1次審査結果(ABC)と1次審査結果(点数)を<br />
	 * 　　文字列結合したものと置換、{KEKKA2}が含まれていた場合は2次審査結果(文字列)と<br />
	 * 　　置換した文字列。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * SELECT 
	 * 	KOKAI_FLG 
	 * FROM 
	 * 	JIGYOKANRI 
	 * WHERE 
	 * 	JIGYO_ID = 'jigyoId'</pre>
	 * </td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(3)Pageを返却する。<br /><br />
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseiSearchInfo
	 * @return 申請書一覧ページ情報が格納されたPage
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#searchApplication(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiSearchInfo)
	 */
	public Page searchApplication(
		UserInfo userInfo,
		ShinseiSearchInfo searchInfo)
		throws NoDataFoundException, ApplicationException
	{
		//DBコネクションの取得
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---申請書一覧ページ情報
			Page pageInfo = null;
			try {
				ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				pageInfo = dao.searchApplication(connection, searchInfo);	//該当レコードを全件取得
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"申請書管理データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			//申請状況名をセット
			new StatusManager(userInfo, HOKAN_SERVER_DB_LINK).setStatusName(connection, pageInfo);
			return pageInfo;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}	
	
	
	/**
	 * PDFデータの作成.<br /><br />
	 * 申請データよりPDFファイルを作成する。<br />
	 * 作成されたPDFファイルには、ログインユーザのパスワードロックがかけられる。<br /><br />
	 * 
	 * 
	 * 　(1)申請データキー情報より対象となるIODファイルを取得する。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * SELECT 
	 * 	0 SEQ_NUM
	 * 	,0 SEQ_TENPU
	 * 	,D.PDF_PATH IOD_FILE_PATH 
	 * FROM 
	 * 	SHINSEIDATAKANRI D 
	 * WHERE 
	 * 	D.SYSTEM_NO = ? 
	 * 	UNION ALL 
	 * 		SELECT
	 *  			1 SEQ_NUM
	 * 			,A.SEQ_TENPU SEQ_TENPU
	 * 			,A.PDF_PATH IOD_FILE_PATH 
	 * 		FROM 
	 * 			TENPUFILEINFO A 
	 * 		WHERE 
	 * 			A.SYSTEM_NO = ? 
	 * 		ORDER BY 
	 * 			SEQ_NUM
	 * 			,SEQ_TENPU</pre>
	 * </td></tr>
	 * </table><br />
	 * 　　IODファイルパスが存在しなかった場合は再度変換処理をかけ、その上で１回だけ再試行を行う。<br />
	 * 　　それでも失敗した場合は例外をthrowする。<br /><br />
	 * 
	 * 
	 * 　(2)取得したIODファイルをリストに加えていく。<br /><br />
	 * 
	 * 
	 * 　(3)ユーザのパスワードを取得する。<br /><br />
	 * 
	 * 
	 * 　(4)IODファイルのリストとパスワードを与え、パスワード付PDFリソースを取得し、返却する。
	 * 　　ここでは、iodファイルからpdfファイルへの変換を行う。使用しているクラスは、<br />
	 * 　　　・iodoc<br />
	 * 　　　・iodtopdf<br />
	 * 　　　・webdocmem<br />
	 * 　　である。
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return PDFデータのFileResource
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getPdfFileRes(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public FileResource getPdfFileRes(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws ApplicationException
	{
		//===== PDF変換サービスメソッド呼び出し =====
		IPdfConvert pdfConvert = new PdfConvert(HOKAN_SERVER_DB_LINK);
		return pdfConvert.getShinseiFileResource(userInfo, shinseiDataPk);
	}
	
	
	/**
	 * CSV出力データの作成.<br />
	 * 申請書一覧ページ情報をCSV出力するため、CSV出力データの作成を行う。<br /><br />
	 * 
	 * 
	 * 　(1)検索条件を元にSQL文を生成し、バックアップ用データベースから検索を実行する。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT 
	 *	   A.SYSTEM_NO				--システム受付番号
	 *	  ,A.UKETUKE_NO				--申請番号
	 *	  ,A.JIGYO_ID				--事業ID
	 *	  ,A.NENDO				--年度
	 *	  ,A.KAISU				--回数
	 *	  ,A.JIGYO_NAME				--事業名
	 *	  ,A.SHINSEISHA_ID				--申請者ID
	 *	  ,TO_CHAR(A.SAKUSEI_DATE, 'YYYY/MM/DD')	--申請書作成日
	 *	  ,TO_CHAR(A.SHONIN_DATE, 'YYYY/MM/DD')	--所属機関承認日
	 *	  ,TO_CHAR(A.JYURI_DATE, 'YYYY/MM/DD')	--学振受理日
	 *	  ,A.NAME_KANJI_SEI			--申請者氏名（漢字等-姓）
	 *	  ,A.NAME_KANJI_MEI			--申請者氏名（漢字等-名）
	 *	  ,A.NAME_KANA_SEI				--申請者氏名（フリガナ-姓）
	 *	  ,A.NAME_RO_SEI				--申請者氏名（ローマ字-姓）
	 *	  ,A.NAME_RO_MEI				--申請者氏名（ローマ字-名）
	 *	  ,A.NENREI				--年齢
	 *	  ,A.KENKYU_NO				--申請者研究者番号
	 *	  ,A.SHOZOKU_CD				--所属機関コード
	 *	  ,A.SHOZOKU_NAME				--所属機関名
	 *	  ,A.SHOZOKU_NAME_RYAKU			--所属機関名（略称）
	 *	  ,A.BUKYOKU_CD				--部局コード
	 *	  ,A.BUKYOKU_NAME				--部局名
	 *	  ,A.BUKYOKU_NAME_RYAKU			--部局名（略称）
	 *	  ,A.SHOKUSHU_CD				--職名コード
	 *	  ,A.SHOKUSHU_NAME_KANJI			--職名（和文）
	 *	  ,A.SHOKUSHU_NAME_RYAKU			--職名（略称）
	 *	  ,A.ZIP					--郵便番号
	 *	  ,A.ADDRESS				--住所
	 *	  ,A.TEL					--TEL
	 *	  ,A.FAX					--FAX
	 *	  ,A.EMAIL				--E-Mail
	 *	  ,A.KADAI_NAME_KANJI			--研究課題名(和文）
	 *	  ,A.KADAI_NAME_EIGO			--研究課題名(英文）
	 *	  ,A.KEI_NAME_NO				--系等の区分番号
	 *	  ,A.KEI_NAME				--系等の区分
	 *	  ,A.KEI_NAME_RYAKU			--系等の区分略称
	 *	  ,A.BUNKASAIMOKU_CD			--細目番号
	 *	  ,A.BUNYA_NAME				--分野
	 *	  ,A.BUNKA_NAME				--分科
	 *	  ,A.SAIMOKU_NAME				--細目
	 *	  ,A.BUNKASAIMOKU_CD2			--細目番号2
	 *	  ,A.BUNYA_NAME2				--分野2
	 *	  ,A.BUNKA_NAME2				--分科2
	 *	  ,A.SAIMOKU_NAME2				--細目2
	 *	  ,A.KANTEN_NO				--推薦の観点番号
	 *	  ,A.KANTEN				--推薦の観点
	 *	  ,A.KANTEN_RYAKU				--推薦の観点略称
	 *	  ,A.KEIHI1				--1年目研究経費
	 *	  ,A.BIHINHI1				--1年目設備備品費
	 *	  ,A.SHOMOHINHI1				--1年目消耗品費
	 *	  ,A.RYOHI1				--1年目旅費
	 *	  ,A.SHAKIN1				--1年目謝金等
	 *	  ,A.SONOTA1				--1年目その他
	 *	  ,A.KEIHI2				--2年目研究経費
	 *	  ,A.BIHINHI2				--2年目設備備品費
	 *	  ,A.SHOMOHINHI2				--2年目消耗品費
	 *	  ,A.RYOHI2				--2年目旅費
	 *	  ,A.SHAKIN2				--2年目謝金等
	 *	  ,A.SONOTA2				--2年目その他
	 *	  ,A.KEIHI3				--3年目研究経費
	 *	  ,A.BIHINHI3				--3年目設備備品費
	 *	  ,A.SHOMOHINHI3				--3年目消耗品費
	 *	  ,A.RYOHI3				--3年目旅費
	 *	  ,A.SHAKIN3				--3年目謝金等
	 *	  ,A.SONOTA3				--3年目その他
	 *	  ,A.KEIHI4				--4年目研究経費
	 *	  ,A.BIHINHI4				--4年目設備備品費
	 *	  ,A.SHOMOHINHI4				--4年目消耗品費
	 *	  ,A.RYOHI4				--4年目旅費
	 *	  ,A.SHAKIN4				--4年目謝金等
	 *	  ,A.SONOTA4				--4年目その他
	 *	  ,A.KEIHI5				--5年目研究経費
	 *	  ,A.BIHINHI5				--5年目設備備品費
	 *	  ,A.SHOMOHINHI5				--5年目消耗品費
	 *	  ,A.RYOHI5				--5年目旅費
	 *	  ,A.SHAKIN5				--5年目謝金等
	 *	  ,A.SONOTA5				--5年目その他
	 *	  ,A.KEIHI_TOTAL				--総計-研究経費
	 *	  ,A.BIHINHI_TOTAL				--総計-設備備品費
	 *	  ,A.SHOMOHINHI_TOTAL			--総計-消耗品費
	 *	  ,A.RYOHI_TOTAL				--総計-旅費
	 *	  ,A.SHAKIN_TOTAL				--総計-謝金等
	 *	  ,A.SONOTA_TOTAL				--総計-その他
	 *	  ,A.SOSHIKI_KEITAI_NO			--研究組織の形態番号
	 *	  ,A.SOSHIKI_KEITAI			--研究組織の形態
	 *	  ,A.BUNTANKIN_FLG				--分担金の有無
	 *	  ,A.KENKYU_NINZU				--研究者数
	 *	  ,A.TAKIKAN_NINZU				--他機関の分担者数
	 *	  ,A.SHINSEI_KUBUN				--新規継続区分
	 *	  ,A.KADAI_NO_KEIZOKU			--継続分の研究課題番号
	 *	  ,A.SHINSEI_FLG_NO			--研究計画最終年度前年度の応募
	 *	  ,A.KADAI_NO_SAISYU			--最終年度課題番号
	 *	  ,A.KAIGAIBUNYA_CD			--海外分野コード
	 *	  ,A.KAIGAIBUNYA_NAME			--海外分野名称
	 *	  ,A.KAIGAIBUNYA_NAME_RYAKU			--海外分野略称
	 *	  ,A.KANREN_SHIMEI1			--関連分野の研究者-氏名1
	 *	  ,A.KANREN_KIKAN1			--関連分野の研究者-所属機関1
	 *	  ,A.KANREN_BUKYOKU1		--関連分野の研究者-所属部局1
	 *	  ,A.KANREN_SHOKU1			--関連分野の研究者-職名1
	 *	  ,A.KANREN_SENMON1			--関連分野の研究者-専門分野1
	 *	  ,A.KANREN_TEL1			--関連分野の研究者-勤務先電話番号1
	 *	  ,A.KANREN_JITAKUTEL1		--関連分野の研究者-自宅電話番号1
	 *	  ,A.KANREN_MAIL1			--関連分野の研究者-Email1
	 *	  ,A.KANREN_SHIMEI2			--関連分野の研究者-氏名2
	 *	  ,A.KANREN_KIKAN2			--関連分野の研究者-所属機関2
	 *	  ,A.KANREN_BUKYOKU2		--関連分野の研究者-所属部局2
	 *	  ,A.KANREN_SHOKU2			--関連分野の研究者-職名2
	 *	  ,A.KANREN_SENMON2			--関連分野の研究者-専門分野2
	 *	  ,A.KANREN_TEL2			--関連分野の研究者-勤務先電話番号2
	 *	  ,A.KANREN_JITAKUTEL2		--関連分野の研究者-自宅電話番号2
	 *	  ,A.KANREN_MAIL2			--関連分野の研究者-Email2
	 *	  ,A.KANREN_SHIMEI3			--関連分野の研究者-氏名3
	 *	  ,A.KANREN_KIKAN3			--関連分野の研究者-所属機関3
	 *	  ,A.KANREN_BUKYOKU3		--関連分野の研究者-所属部局3
	 *	  ,A.KANREN_SHOKU3			--関連分野の研究者-職名3
	 *	  ,A.KANREN_SENMON3			--関連分野の研究者-専門分野3
	 *	  ,A.KANREN_TEL3			--関連分野の研究者-勤務先電話番号3
	 *	  ,A.KANREN_JITAKUTEL3		--関連分野の研究者-自宅電話番号3
	 *	  ,A.KANREN_MAIL3			--関連分野の研究者-Email3
	 *	  ,A.JURI_KEKKA				--受理結果
	 *	  ,A.JURI_BIKO				--受理結果備考
	 *	  ,A.KEKKA1_ABC				--１次審査結果(ABC)
	 *	  ,A.KEKKA1_TEN				--１次審査結果(点数)
	 *	  ,A.SHINSA1_BIKO				--１次審査備考
	 *	  ,A.KEKKA2				--２次審査結果
	 *	  ,A.SHINSA2_BIKO				--業務担当者記入欄
	 *	  ,A.JOKYO_ID				--申請状況ID
	 *	  ,A.SAISHINSEI_FLG			--再申請フラグ
	 *	  ,TO_CHAR(B.HOKAN_DATE, 'YYYY/MM/DD')	--データ保管日
	 *	  ,TO_CHAR(B.YUKO_DATE, 'YYYY/MM/DD')	--保管有効期限
	 *	FROM
	 *	  SHINSEIDATAKANRI 'dbLink' A,		--申請データ管理テーブル
	 *	  JIGYOKANRI 'dbLink' B			--事業情報管理テーブル
	 *	WHERE
	 *	  A.DEL_FLG = 0				--削除フラグが[0]
	 *	  AND
	 *	  A.JIGYO_ID = B.JIGYO_ID			--事業IDが同じもの</pre>
	 * </td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(2)Listの最初の要素にカラム名リストを挿入する。<br />
	 * 　　セットされるカラム名は、以下の通りである。<br />
	 * 　　指定文字列はSQLの識別子長を超えてしまうため別にセットする。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	システム受付番号
	 *	申請番号
	 *	事業ID
	 *	年度
	 *	回数
	 *	事業名
	 *	申請者ID
	 *	申請書作成日
	 *	所属機関承認日
	 *	学振受理日
	 *	申請者氏名（漢字等-姓）
	 *	申請者氏名（漢字等-名）
	 *	申請者氏名（フリガナ-姓）
	 *	申請者氏名（フリガナ-名）
	 *	申請者氏名（ローマ字-姓）
	 *	申請者氏名（ローマ字-名）
	 *	年齢
	 *	申請者研究者番号
	 *	所属機関コード
	 *	所属機関名
	 *	所属機関名（略称）
	 *	部局コード
	 *	部局名
	 *	部局名（略称）
	 *	職名コード
	 *	職名（和文）
	 *	職名（略称）
	 *	郵便番号
	 *	住所
	 *	TEL
	 *	FAX
	 *	Eｍail
	 *	研究課題名(和文）
	 *	研究課題名(英文）
	 *	系等の区分番号
	 *	系等の区分
	 *	系等の区分略称
	 *	細目番号
	 *	分野
	 *	分科
	 *	細目
	 *	細目番号2
	 *	分野2
	 *	分科2
	 *	細目2
	 *	推薦の観点番号
	 *	推薦の観点
	 *	推薦の観点略称
	 *	1年目研究経費
	 *	1年目設備備品費
	 *	1年目消耗品費
	 *	1年目旅費
	 *	1年目謝金等
	 *	1年目その他
	 *	2年目研究経費
	 *	2年目設備備品費
	 *	2年目消耗品費
	 *	2年目旅費
	 *	2年目謝金等
	 *	2年目その他
	 *	3年目研究経費
	 *	3年目設備備品費
	 *	3年目消耗品費
	 *	3年目旅費
	 *	3年目謝金等
	 *	3年目その他
	 *	4年目研究経費
	 *	4年目設備備品費
	 *	4年目消耗品費
	 *	4年目旅費
	 *	4年目謝金等
	 *	4年目その他
	 *	5年目研究経費
	 *	5年目設備備品費
	 *	5年目消耗品費
	 *	5年目旅費
	 *	5年目謝金等
	 *	5年目その他
	 *	総計-研究経費
	 *	総計-設備備品費
	 *	総計-消耗品費
	 *	総計-旅費
	 *	総計-謝金等
	 *	総計-その他
	 *	研究組織の形態番号
	 *	研究組織の形態
	 *	分担金の有無
	 *	研究者数
	 *	他機関の分担者数
	 *	新規継続区分
	 *	継続分の研究課題番号
	 *	研究計画最終年度前年度の応募
	 *	最終年度課題番号
	 *	海外分野コード
	 *	海外分野名称
	 *	海外分野略称
	 *	関連分野の研究者-氏名1
	 *	関連分野の研究者-所属機関1
	 *	関連分野の研究者-所属部局1
	 *	関連分野の研究者-職名1
	 *	関連分野の研究者-専門分野1
	 *	関連分野の研究者-勤務先電話番号1
	 *	関連分野の研究者-自宅電話番号1
	 *	関連分野の研究者-Email1
	 *	関連分野の研究者-氏名2
	 *	関連分野の研究者-所属機関2
	 *	関連分野の研究者-所属部局2
	 *	関連分野の研究者-職名2
	 *	関連分野の研究者-専門分野2
	 *	関連分野の研究者-勤務先電話番号2
	 *	関連分野の研究者-自宅電話番号2
	 *	関連分野の研究者-Email2
	 *	関連分野の研究者-氏名3
	 *	関連分野の研究者-所属機関3
	 *	関連分野の研究者-所属部局3
	 *	関連分野の研究者-職名3
	 *	関連分野の研究者-専門分野3
	 *	関連分野の研究者-勤務先電話番号3
	 *	関連分野の研究者-自宅電話番号3
	 *	関連分野の研究者-Email3
	 *	受理結果
	 *	受理結果備考
	 *	１次審査結果(ABC)
	 *	１次審査結果(点数)
	 *	１次審査備考
	 *	２次審査結果
	 *	業務担当者記入欄
	 *	申請状況ID
	 *	再申請フラグ
	 *	データ保管日
	 *	保管有効期限</pre>
	 * </td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(3)CSVのListを返却する。<br /><br />
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseiSearchInfo
	 * @return CSV出力データのList
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#searchCsvData(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiSearchInfo)
	 */
	public List searchCsvData(UserInfo userInfo, ShinseiSearchInfo searchInfo)
		throws ApplicationException
	{
		//DBコネクションの取得
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---申請書一覧ページ情報
			List csvList = null;
			try {
				ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				csvList = dao.searchCsvData(connection, searchInfo);	//保管レコードを取得
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"申請書管理データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			return csvList;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}	
	
	

	/**
	 * CSV出力データの作成.<br /><br />
	 * 申請書一覧に紐付く研究組織データをCSV出力するため、CSV出力データの作成を行う。<br /><br />
	 * 
	 * 
	 * 　(1)検索条件を元にSQL文を生成し、バックアップ用のデータベースから検索を実行する。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT 
	 *		B.SYSTEM_NO			\システム受付番号\
	 *		,B.SEQ_NO				\シーケンス番号\
	 *		,B.JIGYO_ID			\事業ID\
	 *		,B.BUNTAN_FLG			\代表者分担者別\
	 *		,B.KENKYU_NO			\研究者番号\
	 *		,B.NAME_KANJI_SEI			\氏名（漢字－姓）\
	 *		,B.NAME_KANJI_MEI			\氏名（漢字－名）\
	 *		,B.NAME_KANA_SEI			\氏名（フリガナ－姓）\
	 *		,B.NAME_KANA_MEI			\氏名（フリガナ－名）\
	 *		,B.SHOZOKU_CD			\所属機関名（コード）\
	 *		,B.SHOZOKU_NAME			\所属機関名（和文）\
	 *		,B.BUKYOKU_CD			\部局名（コード）\
	 *		,B.BUKYOKU_NAME			\部局名（和文）\
	 *		,B.SHOKUSHU_CD			\職名コード\
	 *		,B.SHOKUSHU_NAME_KANJI		\職名（和文）\
	 *		,B.SENMON				\現在の専門\
	 *		,B.GAKUI				\学位\
	 *		,B.BUNTAN				\役割分担\
	 *		,B.KEIHI				\研究経費\
	 *		,B.EFFORT				\エフォート\
	 *		,B.NENREI				\年齢\
	 *	FROM
	 *		SHINSEIDATAKANRI 'dbLink' A
	 *		,KENKYUSOSHIKIKANRI 'dbLink' B
	 *	WHERE
	 *		B.SYSTEM_NO = A.SYSTEM_NO		-- システム受付番号が同じもの</pre>
	 * </td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(2)Listの最初の要素にカラム名リストを挿入する。<br /><br />
	 * 
	 * 
	 * 　(3)CSVのListを返却する。<br /><br />
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseiSearchInfo
	 * @return CSV出力データのList
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#searchKenkyuSoshikiCsvData(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiSearchInfo)
	 */
	public List searchKenkyuSoshikiCsvData(UserInfo userInfo, ShinseiSearchInfo searchInfo)
		throws ApplicationException
	{
		//DBコネクションの取得
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---申請書一覧に紐付く研究組織データ
			List csvList = null;
			try {
				ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				csvList = dao.searchKenkyuSoshikiCsvData(connection, searchInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"研究組織管理データ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			return csvList;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}			
	
	
	/**
	 * 1次,2次審査結果をMapに格納.<br /><br />
	 * ユーザ情報と申請データ情報から、1次,2次審査結果をMapに格納し、返却する。<br />
	 * 呼び出すメソッドは、以下で説明される自メソッドの<br />
	 * 　・<b>getShinsaKekkaReferenceInfo(userInfo, shinseiDataPk)</b><br />
	 * 　　　キーは"key_shinsakekka_1st"<br />
	 * 　・<b>getShinsaKekka2nd(userInfo, shinseiDataPk)</b><br />
	 * 　　　キーは"key_shinsakekka_2st"<br />
	 * である。<br />
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return 1次,2次審査結果を持つMap
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getShinsaKekkaBoth(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public Map getShinsaKekkaBoth(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Map map = new HashMap();
		
		//1次審査結果（参照用）
		map.put(
			KEY_SHINSAKEKKA_1ST,
			getShinsaKekkaReferenceInfo(userInfo, shinseiDataPk)
		);
		
		//2次審査結果
		map.put(
			KEY_SHINSAKEKKA_2ND,
			getShinsaKekka2nd(userInfo, shinseiDataPk)
		);
		
		return map;
	}

	
	
	/**
	 * 1次審査結果（参照用）生成.<br /><br />
	 * 
	 * 
	 * 　(1)検索条件を元にSQL文を生成し、バックアップ用のデータベースから該当申請データを取得する。<br />
	 * 　　　(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--システム受付番号
	 *		,A.UKETUKE_NO			--申請番号
	 *		,A.JIGYO_ID			--事業ID
	 *		,A.NENDO				--年度
	 *		,A.KAISU				--回数
	 *		,A.JIGYO_NAME			--事業名
	 *		,A.SHINSEISHA_ID			--申請者ID
	 *		,A.SAKUSEI_DATE			--申請書作成日
	 *		,A.SHONIN_DATE			--所属機関承認日
	 *		,A.JYURI_DATE			--学振受理日
	 *		,A.NAME_KANJI_SEI			--申請者氏名（漢字等-姓）
	 *		,A.NAME_KANJI_MEI			--申請者氏名（漢字等-名）
	 *		,A.NAME_KANA_SEI			--申請者氏名（フリガナ-姓）
	 *		,A.NAME_KANA_MEI			--申請者氏名（フリガナ-名）
	 *		,A.NAME_RO_SEI			--申請者氏名（ローマ字-姓）
	 *		,A.NAME_RO_MEI			--申請者氏名（ローマ字-名）
	 *		,A.NENREI				--年齢
	 *		,A.KENKYU_NO			--申請者研究者番号
	 *		,A.SHOZOKU_CD			--所属機関コード
	 *		,A.SHOZOKU_NAME			--所属機関名
	 *		,A.SHOZOKU_NAME_RYAKU		--所属機関名（略称）
	 *		,A.BUKYOKU_CD			--部局コード
	 *		,A.BUKYOKU_NAME			--部局名
	 *		,A.BUKYOKU_NAME_RYAKU		--部局名（略称）
	 *		,A.SHOKUSHU_CD			--職名コード
	 *		,A.SHOKUSHU_NAME_KANJI		--職名（和文）
	 *		,A.SHOKUSHU_NAME_RYAKU		--職名（略称）
	 *		,A.ZIP				--郵便番号
	 *		,A.ADDRESS			--住所
	 *		,A.TEL				--TEL
	 *		,A.FAX				--FAX
	 *		,A.EMAIL				--E-Mail
	 *		,A.SENMON				--現在の専門
	 *		,A.GAKUI				--学位
	 *		,A.BUNTAN				--役割分担
	 *		,A.KADAI_NAME_KANJI		--研究課題名(和文）
	 *		,A.KADAI_NAME_EIGO			--研究課題名(英文）
	 *		,A.JIGYO_KUBUN			--事業区分
	 *		,A.SHINSA_KUBUN			--審査区分
	 *		,A.SHINSA_KUBUN_MEISHO		--審査区分名称
	 *		,A.BUNKATSU_NO			--分割番号
	 *		,A.BUNKATSU_NO_MEISHO		--分割番号名称
	 *		,A.KENKYU_TAISHO			--研究対象の類型
	 *		,A.KEI_NAME_NO			--系等の区分番号
	 *		,A.KEI_NAME			--系等の区分
	 *		,A.KEI_NAME_RYAKU			--系等の区分略称
	 *		,A.BUNKASAIMOKU_CD			--細目番号
	 *		,A.BUNYA_NAME			--分野
	 *		,A.BUNKA_NAME			--分科
	 *		,A.SAIMOKU_NAME			--細目
	 *		,A.BUNKASAIMOKU_CD2		--細目番号2
	 *		,A.BUNYA_NAME2			--分野2
	 *		,A.BUNKA_NAME2			--分科2
	 *		,A.SAIMOKU_NAME2			--細目2
	 *		,A.KANTEN_NO			--推薦の観点番号
	 *		,A.KANTEN				--推薦の観点
	 *		,A.KANTEN_RYAKU			--推薦の観点略称
	 *		,A.KEIHI1				--1年目研究経費
	 *		,A.BIHINHI1			--1年目設備備品費
	 *		,A.SHOMOHINHI1			--1年目消耗品費
	 *		,A.KOKUNAIRYOHI1			--1年目国内旅費
	 *		,A.GAIKOKURYOHI1			--1年目外国旅費
	 *		,A.RYOHI1				--1年目旅費
	 *		,A.SHAKIN1			--1年目謝金等
	 *		,A.SONOTA1			--1年目その他
	 *		,A.KEIHI2				--2年目研究経費
	 *		,A.BIHINHI2			--2年目設備備品費
	 *		,A.SHOMOHINHI2			--2年目消耗品費
	 *		,A.KOKUNAIRYOHI2			--2年目国内旅費
	 *		,A.GAIKOKURYOHI2			--2年目外国旅費
	 *		,A.RYOHI2				--2年目旅費
	 *		,A.SHAKIN2			--2年目謝金等
	 *		,A.SONOTA2			--2年目その他
	 *		,A.KEIHI3				--3年目研究経費
	 *		,A.BIHINHI3			--3年目設備備品費
	 *		,A.SHOMOHINHI3			--3年目消耗品費
	 *		,A.KOKUNAIRYOHI3			--3年目国内旅費
	 *		,A.GAIKOKURYOHI3			--3年目外国旅費
	 *		,A.RYOHI3				--3年目旅費
	 *		,A.SHAKIN3			--3年目謝金等
	 *		,A.SONOTA3			--3年目その他
	 *		,A.KEIHI4				--4年目研究経費
	 *		,A.BIHINHI4			--4年目設備備品費
	 *		,A.SHOMOHINHI4			--4年目消耗品費
	 *		,A.KOKUNAIRYOHI4			--4年目国内旅費
	 *		,A.GAIKOKURYOHI4			--4年目外国旅費
	 *		,A.RYOHI4				--4年目旅費
	 *		,A.SHAKIN4			--4年目謝金等
	 *		,A.SONOTA4			--4年目その他
	 *		,A.KEIHI5				--5年目研究経費
	 *		,A.BIHINHI5			--5年目設備備品費
	 *		,A.SHOMOHINHI5			--5年目消耗品費
	 *		,A.KOKUNAIRYOHI5			--5年目国内旅費
	 *		,A.GAIKOKURYOHI5			--5年目外国旅費
	 *		,A.RYOHI5				--5年目旅費
	 *		,A.SHAKIN5			--5年目謝金等
	 *		,A.SONOTA5			--5年目その他
	 *		,A.KEIHI_TOTAL			--総計-研究経費
	 *		,A.BIHINHI_TOTAL			--総計-設備備品費
	 *		,A.SHOMOHINHI_TOTAL		--総計-消耗品費
	 *		,A.KOKUNAIRYOHI_TOTAL		--総計-国内旅費
	 *		,A.GAIKOKURYOHI_TOTAL		--総計-外国旅費
	 *		,A.RYOHI_TOTAL			--総計-旅費
	 *		,A.SHAKIN_TOTAL			--総計-謝金等
	 *		,A.SONOTA_TOTAL			--総計-その他
	 *		,A.SOSHIKI_KEITAI_NO		--研究組織の形態番号
	 *		,A.SOSHIKI_KEITAI			--研究組織の形態
	 *		,A.BUNTANKIN_FLG			--分担金の有無
	 *		,A.KOYOHI				--研究支援者雇用経費
	 *		,A.KENKYU_NINZU			--研究者数
	 *		,A.TAKIKAN_NINZU			--他機関の分担者数
	 *		,A.SHINSEI_KUBUN			--新規継続区分
	 *		,A.KADAI_NO_KEIZOKU		--継続分の研究課題番号
	 *		,A.SHINSEI_FLG_NO			--継続分の研究課題番号
	 *		,A.SHINSEI_FLG			--申請の有無
	 *		,A.KADAI_NO_SAISYU			--最終年度課題番号
	 *		,A.KAIJIKIBO_FLG_NO		--開示希望の有無番号
	 *		,A.KAIJIKIBO_FLG			--開示希望の有無
	 *		,A.KAIGAIBUNYA_CD			--海外分野コード
	 *		,A.KAIGAIBUNYA_NAME		--海外分野名称
	 *		,A.KAIGAIBUNYA_NAME_RYAKU		--海外分野略称
	 *		,A.KANREN_SHIMEI1		--関連分野の研究者-氏名1
	 *		,A.KANREN_KIKAN1		--関連分野の研究者-所属機関1
	 *		,A.KANREN_BUKYOKU1		--関連分野の研究者-所属部局1
	 *		,A.KANREN_SHOKU1		--関連分野の研究者-職名1
	 *		,A.KANREN_SENMON1		--関連分野の研究者-専門分野1
	 *		,A.KANREN_TEL1		--関連分野の研究者-勤務先電話番号1
	 *		,A.KANREN_JITAKUTEL1	--関連分野の研究者-自宅電話番号1
	 *		,A.KANREN_MAIL1			--関連分野の研究者-E-mail1
	 *		,A.KANREN_SHIMEI2		--関連分野の研究者-氏名2
	 *		,A.KANREN_KIKAN2		--関連分野の研究者-所属機関2
	 *		,A.KANREN_BUKYOKU2		--関連分野の研究者-所属部局2
	 *		,A.KANREN_SHOKU2		--関連分野の研究者-職名2
	 *		,A.KANREN_SENMON2		--関連分野の研究者-専門分野2
	 *		,A.KANREN_TEL2		--関連分野の研究者-勤務先電話番号2
	 *		,A.KANREN_JITAKUTEL2	--関連分野の研究者-自宅電話番号2
	 *		,A.KANREN_MAIL2			--関連分野の研究者-E-mail2
	 *		,A.KANREN_SHIMEI3		--関連分野の研究者-氏名3
	 *		,A.KANREN_KIKAN3		--関連分野の研究者-所属機関3
	 *		,A.KANREN_BUKYOKU3		--関連分野の研究者-所属部局3
	 *		,A.KANREN_SHOKU3		--関連分野の研究者-職名3
	 *		,A.KANREN_SENMON3		--関連分野の研究者-専門分野3
	 *		,A.KANREN_TEL3		--関連分野の研究者-勤務先電話番号3
	 *		,A.KANREN_JITAKUTEL3	--関連分野の研究者-自宅電話番号3
	 *		,A.KANREN_MAIL3			--関連分野の研究者-E-mail3
	 *		,A.XML_PATH			--XMLの格納パス
	 *		,A.PDF_PATH			--PDFの格納パス
	 *		,A.JURI_KEKKA			--受理結果
	 *		,A.JURI_BIKO			--受理結果備考
	 *		,A.SUISENSHO_PATH			--推薦書の格納パス
	 *		,A.KEKKA1_ABC			--１次審査結果(ABC)
	 *		,A.KEKKA1_TEN			--１次審査結果(点数)
	 *		,A.KEKKA1_TEN_SORTED		--１次審査結果(点数順)
	 *		,A.SHINSA1_BIKO			--１次審査備考
	 *		,A.KEKKA2				--２次審査結果
	 *		,A.SOU_KEHI			--総経費（学振入力）
	 *		,A.SHONEN_KEHI			--初年度経費（学振入力）
	 *		,A.SHINSA2_BIKO			--業務担当者記入欄
	 *		,A.JOKYO_ID			--申請状況ID
	 *		,A.SAISHINSEI_FLG			--再申請フラグ
	 *		,A.DEL_FLG			--削除フラグ
	 *	FROM
	 *		SHINSEIDATAKANRI 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>第二引数shinseiDataPkの変数SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(2)検索条件を元にSQL文を生成し、バックアップ用のデータベースから該当審査結果データを取得する。<br />
	 * 　　　(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--システム受付番号
	 *		,A.UKETUKE_NO			--申請番号
	 *		,A.SHINSAIN_NO			--審査員番号
	 *		,A.JIGYO_KUBUN			--事業区分
	 *		,A.SEQ_NO				--シーケンス番号
	 *		,A.SHINSA_KUBUN			--審査区分
	 *		,A.SHINSAIN_NAME_KANJI_SEI		--審査員名（漢字－姓）
	 *		,A.SHINSAIN_NAME_KANJI_MEI		--審査員名（漢字－名）
	 *		,A.NAME_KANA_SEI			--審査員名（フリガナ－姓）
	 *		,A.NAME_KANA_MEI			--審査員名（フリガナ－名）
	 *		,A.SHOZOKU_NAME			--審査員所属機関名
	 *		,A.BUKYOKU_NAME			--審査員部局名
	 *		,A.SHOKUSHU_NAME			--審査員職名
	 *		,A.JIGYO_ID			--事業ID
	 *		,A.JIGYO_NAME			--事業名
	 *		,A.BUNKASAIMOKU_CD			--細目番号
	 *		,A.EDA_NO				--枝番
	 *		,A.CHECKDIGIT			--チェックデジット
	 *		,A.KEKKA_ABC			--総合評価（ABC）
	 *		,A.KEKKA_TEN			--総合評価（点数）
	 *		,NVL(REPLACE(A.KEKKA_TEN,'-','0'),'-1')SORT_KEKKA_TEN
	 *		--ソート用。審査結果（点数）の値NULL→'-1'、'-'→'0'に置換）
	 *		,A.COMMENT1			--コメント1
	 *		,A.COMMENT2			--コメント2
	 *		,A.COMMENT3			--コメント3
	 *		,A.COMMENT4			--コメント4
	 *		,A.COMMENT5			--コメント5
	 *		,A.COMMENT6			--コメント6
	 *		,A.KENKYUNAIYO			--研究内容
	 *		,A.KENKYUKEIKAKU			--研究計画
	 *		,A.TEKISETSU_KAIGAI		--適切性-海外
	 *		,A.TEKISETSU_KENKYU1		--適切性-研究（1）
	 *		,A.TEKISETSU			--適切性
	 *		,A.DATO				--妥当性
	 *		,A.SHINSEISHA			--研究代表者
	 *		,A.KENKYUBUNTANSHA			--研究分担者
	 *		,A.HITOGENOMU			--ヒトゲノム
	 *		,A.TOKUTEI			--特定胚
	 *		,A.HITOES				--ヒトES細胞
	 *		,A.KUMIKAE			--遺伝子組換え実験
	 *		,A.CHIRYO				--遺伝子治療臨床研究
	 *		,A.EKIGAKU			--疫学研究
	 *		,A.COMMENTS			--コメント
	 *		,A.TENPU_PATH			--添付ファイル格納パス
	 *		,A.SHINSA_JOKYO			--審査状況
	 *		,A.BIKO				--備考
	 *	FROM
	 *		SHINSAKEKKA 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?
	 *	ORDER BY
	 *		KEKKA_ABC ASC			--総合評価（ABC）の昇順
	 *		,SORT_KEKKA_TEN DESC		--総合評価（点数）の降順
	 *		,SHINSAIN_NO ASC			--審査員番号の昇順
	 *		,JIGYO_KUBUN ASC			--事業区分の昇順</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>第二引数shinseiDataPkの変数SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(3)取得した審査結果データの各々に、添付ファイル名・総合評価（ABC）の表示ラベル名を加える。<br />
	 * 　　　添付ファイル名は、取得した審査結果データ内の"添付ファイル格納パス"の値から取得する。<br />
	 * 　　　総合評価（ABC）の表示ラベル名は、以下のSQL文を発行して取得する。<br />
	 * 　　　(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.ATAI
	 *		,A.NAME
	 *		,A.RYAKU
	 *		,A.SORT
	 *		,A.BIKO
	 *	FROM
	 *		MASTER_LABEL A
	 *	WHERE
	 *		A.LABEL_KUBUN=?
	 *		AND A.ATAI=?
	 *	ORDER BY
	 *		SORT</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>ラベル区分</td><td>リテラル"KEKKA_ABC"</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>値</td><td>shinsaKekkaInfoの変数KekkaAbc</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(4)申請データと審査結果データを1次審査結果として<b>ShinsaKekkaReferenceInfo</b>に格納し、返却する。<br /><br />
	 * 　　格納する値は、以下のもの。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * 	SystemNo			//システム受付番号
	 * 	UketukeNo			//申請番号
	 * 	Nendo			//年度
	 * 	Kaisu			//回数
	 * 	JigyoId			//事業ID
	 * 	JigyoName			//事業名
	 * 	KadaiNameKanji			//研究課題名
	 * 	NameKanjiSei			//申請者名（姓）
	 * 	NameKanjiMei			//申請者名（名）
	 * 	ShozokuName			//所属機関名
	 * 	BukyokuName			//部局名
	 * 	ShokushuNameKanji			//職名
	 * 	KenkyuNo			//研究者番号
	 * 	shinsaKekkaInfo			//1次審査結果情報
	 * 	Shinsa1Biko			//業務担当者用備考</pre>
	 * </td></tr>
	 * </table><br />
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return 1次審査結果を持つShinsaKekkaReferenceInfo
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getShinsaKekkaReferenceInfo(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public ShinsaKekkaReferenceInfo getShinsaKekkaReferenceInfo(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			
			//申請データ管理DAO
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//該当申請データを取得する
			ShinseiDataInfo shinseiDataInfo = null;
			try{
				shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"申請書管理データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//審査結果DAO
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//該当審査結果データを取得する
			ShinsaKekkaInfo[] shinsaKekkaInfo = null;
			try{
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//2005.09.09 iso 基盤等の審査結果を表示させるため追加
			//ラベル名
			List kekkaAbcList = null;		//総合評価（ABC）
			List kekkaTenList = null;		//総合評価（点数）
			List kekkaTenHogaList = null;		//総合評価（萌芽） 
			try{
				String[] labelKubun = new String[]{ILabelKubun.KEKKA_ABC,
													ILabelKubun.KEKKA_TEN,
													ILabelKubun.KEKKA_TEN_HOGA};
				List bothList = new LabelValueMaintenance().getLabelList(labelKubun);	//4つのラベルリスト
				kekkaAbcList = (List)bothList.get(0);		
				kekkaTenList = (List)bothList.get(1);
				kekkaTenHogaList = (List)bothList.get(2);		
			}catch(ApplicationException e){
				throw new ApplicationException(
					"ラベルマスタ検索中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}

			for(int i = 0; i < shinsaKekkaInfo.length; i++){
				//添付ファイル名をセット
				String tenpuPath = shinsaKekkaInfo[i].getTenpuPath();
				if(tenpuPath != null && tenpuPath.length() != 0){
					shinsaKekkaInfo[i].setTenpuName(new File(tenpuPath).getName());
				}

				//2005.09.09 iso 基盤等の審査結果を表示させるため変更
//				//総合評価（ABC）の表示ラベル名をセット
//				if(shinsaKekkaInfo[i].getKekkaAbc() != null && shinsaKekkaInfo[i].getKekkaAbc().length() != 0){
//					try{
//						Map resultMap = MasterLabelInfoDao.selectRecord(connection,
//																		ILabelKubun.KEKKA_ABC,
//																		shinsaKekkaInfo[i].getKekkaAbc());
//						shinsaKekkaInfo[i].setKekkaAbcLabel((String)resultMap.get("NAME"));
//					}catch(NoDataFoundException e){
//						//例外NoDataFoundExceptionが発生したときは、総合評価（ABC）をセット
//						shinsaKekkaInfo[i].setKekkaAbcLabel(shinsaKekkaInfo[i].getKekkaAbc());	
//					}catch (DataAccessException e) {
//						throw new ApplicationException(
//							"ラベルマスタ情報取得中に例外が発生しました。",
//							new ErrorInfo("errors.4004"),
//							e);
//					}
//				}
				//総合評価（ABC）の表示ラベル名をセット
				String kekkaAbcLabel = getLabelName(kekkaAbcList, shinsaKekkaInfo[i].getKekkaAbc());
				shinsaKekkaInfo[i].setKekkaAbcLabel(kekkaAbcLabel);

				//総合評価（点数）の表示ラベル名をセット
				String kekkaTenLabel = getLabelName(kekkaTenList, shinsaKekkaInfo[i].getKekkaTen());
				shinsaKekkaInfo[i].setKekkaTenLabel(kekkaTenLabel);
				
//				総合評価（萌芽）の表示ラベル名をセット
				String kekkaTenHogaLabel = getLabelName(kekkaTenHogaList, shinsaKekkaInfo[i].getKekkaTen());
				shinsaKekkaInfo[i].setKekkaTenHogaLabel(kekkaTenHogaLabel);
			}				
		
			//1次審査結果（参照用）の生成
			ShinsaKekkaReferenceInfo refInfo = new ShinsaKekkaReferenceInfo();
			refInfo.setSystemNo(shinseiDataInfo.getSystemNo());										//システム受付番号
			refInfo.setUketukeNo(shinseiDataInfo.getUketukeNo());									//申請番号
			refInfo.setNendo(shinseiDataInfo.getNendo());											//年度
			refInfo.setKaisu(shinseiDataInfo.getKaisu());											//回数
			refInfo.setJigyoId(shinseiDataInfo.getJigyoId());										//事業ID
			refInfo.setJigyoName(shinseiDataInfo.getJigyoName());									//事業名
			refInfo.setJigyoCd(shinseiDataInfo.getJigyoCd());										//事業コード
			refInfo.setKadaiNameKanji(shinseiDataInfo.getKadaiInfo().getKadaiNameKanji());			//研究課題名
			refInfo.setNameKanjiSei(shinseiDataInfo.getDaihyouInfo().getNameKanjiSei());			//申請者名（姓）
			refInfo.setNameKanjiMei(shinseiDataInfo.getDaihyouInfo().getNameKanjiMei());			//申請者名（名）
			refInfo.setShozokuName(shinseiDataInfo.getDaihyouInfo().getShozokuName());				//所属機関名
			refInfo.setBukyokuName(shinseiDataInfo.getDaihyouInfo().getBukyokuName());				//部局名
			refInfo.setShokushuNameKanji(shinseiDataInfo.getDaihyouInfo().getShokushuNameKanji());	//職名
			refInfo.setKenkyuNo(shinseiDataInfo.getDaihyouInfo().getKenkyuNo());					//研究者番号			
			refInfo.setShinsaKekkaInfo(shinsaKekkaInfo);											//1次審査結果情報
			refInfo.setShinsa1Biko(shinseiDataInfo.getShinsa1Biko());								//業務担当者用備考

			return refInfo;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}
	
	
	
	/**
	 * 2次審査結果（参照用）生成.<br /><br />
	 * 
	 * 
	 * 　(1)検索条件を元にSQL文を生成し、バックアップ用のデータベースから該当申請データを取得する。<br />
	 * 　　　(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--システム受付番号
	 *		,A.UKETUKE_NO			--申請番号
	 *		,A.JIGYO_ID			--事業ID
	 *		,A.NENDO				--年度
	 *		,A.KAISU				--回数
	 *		,A.JIGYO_NAME			--事業名
	 *		,A.SHINSEISHA_ID			--申請者ID
	 *		,A.SAKUSEI_DATE			--申請書作成日
	 *		,A.SHONIN_DATE			--所属機関承認日
	 *		,A.JYURI_DATE			--学振受理日
	 *		,A.NAME_KANJI_SEI			--申請者氏名（漢字等-姓）
	 *		,A.NAME_KANJI_MEI			--申請者氏名（漢字等-名）
	 *		,A.NAME_KANA_SEI			--申請者氏名（フリガナ-姓）
	 *		,A.NAME_KANA_MEI			--申請者氏名（フリガナ-名）
	 *		,A.NAME_RO_SEI			--申請者氏名（ローマ字-姓）
	 *		,A.NAME_RO_MEI			--申請者氏名（ローマ字-名）
	 *		,A.NENREI				--年齢
	 *		,A.KENKYU_NO			--申請者研究者番号
	 *		,A.SHOZOKU_CD			--所属機関コード
	 *		,A.SHOZOKU_NAME			--所属機関名
	 *		,A.SHOZOKU_NAME_RYAKU		--所属機関名（略称）
	 *		,A.BUKYOKU_CD			--部局コード
	 *		,A.BUKYOKU_NAME			--部局名
	 *		,A.BUKYOKU_NAME_RYAKU		--部局名（略称）
	 *		,A.SHOKUSHU_CD			--職名コード
	 *		,A.SHOKUSHU_NAME_KANJI		--職名（和文）
	 *		,A.SHOKUSHU_NAME_RYAKU		--職名（略称）
	 *		,A.ZIP				--郵便番号
	 *		,A.ADDRESS			--住所
	 *		,A.TEL				--TEL
	 *		,A.FAX				--FAX
	 *		,A.EMAIL				--E-Mail
	 *		,A.SENMON				--現在の専門
	 *		,A.GAKUI				--学位
	 *		,A.BUNTAN				--役割分担
	 *		,A.KADAI_NAME_KANJI		--研究課題名(和文）
	 *		,A.KADAI_NAME_EIGO			--研究課題名(英文）
	 *		,A.JIGYO_KUBUN			--事業区分
	 *		,A.SHINSA_KUBUN			--審査区分
	 *		,A.SHINSA_KUBUN_MEISHO		--審査区分名称
	 *		,A.BUNKATSU_NO			--分割番号
	 *		,A.BUNKATSU_NO_MEISHO		--分割番号名称
	 *		,A.KENKYU_TAISHO			--研究対象の類型
	 *		,A.KEI_NAME_NO			--系等の区分番号
	 *		,A.KEI_NAME			--系等の区分
	 *		,A.KEI_NAME_RYAKU			--系等の区分略称
	 *		,A.BUNKASAIMOKU_CD			--細目番号
	 *		,A.BUNYA_NAME			--分野
	 *		,A.BUNKA_NAME			--分科
	 *		,A.SAIMOKU_NAME			--細目
	 *		,A.BUNKASAIMOKU_CD2		--細目番号2
	 *		,A.BUNYA_NAME2			--分野2
	 *		,A.BUNKA_NAME2			--分科2
	 *		,A.SAIMOKU_NAME2			--細目2
	 *		,A.KANTEN_NO			--推薦の観点番号
	 *		,A.KANTEN				--推薦の観点
	 *		,A.KANTEN_RYAKU			--推薦の観点略称
	 *		,A.KEIHI1				--1年目研究経費
	 *		,A.BIHINHI1			--1年目設備備品費
	 *		,A.SHOMOHINHI1			--1年目消耗品費
	 *		,A.KOKUNAIRYOHI1			--1年目国内旅費
	 *		,A.GAIKOKURYOHI1			--1年目外国旅費
	 *		,A.RYOHI1				--1年目旅費
	 *		,A.SHAKIN1			--1年目謝金等
	 *		,A.SONOTA1			--1年目その他
	 *		,A.KEIHI2				--2年目研究経費
	 *		,A.BIHINHI2			--2年目設備備品費
	 *		,A.SHOMOHINHI2			--2年目消耗品費
	 *		,A.KOKUNAIRYOHI2			--2年目国内旅費
	 *		,A.GAIKOKURYOHI2			--2年目外国旅費
	 *		,A.RYOHI2				--2年目旅費
	 *		,A.SHAKIN2			--2年目謝金等
	 *		,A.SONOTA2			--2年目その他
	 *		,A.KEIHI3				--3年目研究経費
	 *		,A.BIHINHI3			--3年目設備備品費
	 *		,A.SHOMOHINHI3			--3年目消耗品費
	 *		,A.KOKUNAIRYOHI3			--3年目国内旅費
	 *		,A.GAIKOKURYOHI3			--3年目外国旅費
	 *		,A.RYOHI3				--3年目旅費
	 *		,A.SHAKIN3			--3年目謝金等
	 *		,A.SONOTA3			--3年目その他
	 *		,A.KEIHI4				--4年目研究経費
	 *		,A.BIHINHI4			--4年目設備備品費
	 *		,A.SHOMOHINHI4			--4年目消耗品費
	 *		,A.KOKUNAIRYOHI4			--4年目国内旅費
	 *		,A.GAIKOKURYOHI4			--4年目外国旅費
	 *		,A.RYOHI4				--4年目旅費
	 *		,A.SHAKIN4			--4年目謝金等
	 *		,A.SONOTA4			--4年目その他
	 *		,A.KEIHI5				--5年目研究経費
	 *		,A.BIHINHI5			--5年目設備備品費
	 *		,A.SHOMOHINHI5			--5年目消耗品費
	 *		,A.KOKUNAIRYOHI5			--5年目国内旅費
	 *		,A.GAIKOKURYOHI5			--5年目外国旅費
	 *		,A.RYOHI5				--5年目旅費
	 *		,A.SHAKIN5			--5年目謝金等
	 *		,A.SONOTA5			--5年目その他
	 *		,A.KEIHI_TOTAL			--総計-研究経費
	 *		,A.BIHINHI_TOTAL			--総計-設備備品費
	 *		,A.SHOMOHINHI_TOTAL		--総計-消耗品費
	 *		,A.KOKUNAIRYOHI_TOTAL		--総計-国内旅費
	 *		,A.GAIKOKURYOHI_TOTAL		--総計-外国旅費
	 *		,A.RYOHI_TOTAL			--総計-旅費
	 *		,A.SHAKIN_TOTAL			--総計-謝金等
	 *		,A.SONOTA_TOTAL			--総計-その他
	 *		,A.SOSHIKI_KEITAI_NO		--研究組織の形態番号
	 *		,A.SOSHIKI_KEITAI			--研究組織の形態
	 *		,A.BUNTANKIN_FLG			--分担金の有無
	 *		,A.KOYOHI				--研究支援者雇用経費
	 *		,A.KENKYU_NINZU			--研究者数
	 *		,A.TAKIKAN_NINZU			--他機関の分担者数
	 *		,A.SHINSEI_KUBUN			--新規継続区分
	 *		,A.KADAI_NO_KEIZOKU		--継続分の研究課題番号
	 *		,A.SHINSEI_FLG_NO			--継続分の研究課題番号
	 *		,A.SHINSEI_FLG			--申請の有無
	 *		,A.KADAI_NO_SAISYU			--最終年度課題番号
	 *		,A.KAIJIKIBO_FLG_NO		--開示希望の有無番号
	 *		,A.KAIJIKIBO_FLG			--開示希望の有無
	 *		,A.KAIGAIBUNYA_CD			--海外分野コード
	 *		,A.KAIGAIBUNYA_NAME		--海外分野名称
	 *		,A.KAIGAIBUNYA_NAME_RYAKU		--海外分野略称
	 *		,A.KANREN_SHIMEI1		--関連分野の研究者-氏名1
	 *		,A.KANREN_KIKAN1		--関連分野の研究者-所属機関1
	 *		,A.KANREN_BUKYOKU1		--関連分野の研究者-所属部局1
	 *		,A.KANREN_SHOKU1		--関連分野の研究者-職名1
	 *		,A.KANREN_SENMON1		--関連分野の研究者-専門分野1
	 *		,A.KANREN_TEL1		--関連分野の研究者-勤務先電話番号1
	 *		,A.KANREN_JITAKUTEL1	--関連分野の研究者-自宅電話番号1
	 *		,A.KANREN_MAIL1		--関連分野の研究者-E-mail1
	 *		,A.KANREN_SHIMEI2		--関連分野の研究者-氏名2
	 *		,A.KANREN_KIKAN2		--関連分野の研究者-所属機関2
	 *		,A.KANREN_BUKYOKU2		--関連分野の研究者-所属部局2
	 *		,A.KANREN_SHOKU2		--関連分野の研究者-職名2
	 *		,A.KANREN_SENMON2		--関連分野の研究者-専門分野2
	 *		,A.KANREN_TEL2		--関連分野の研究者-勤務先電話番号2
	 *		,A.KANREN_JITAKUTEL2	--関連分野の研究者-自宅電話番号2
	 *		,A.KANREN_MAIL2		--関連分野の研究者-E-mail2
	 *		,A.KANREN_SHIMEI3		--関連分野の研究者-氏名3
	 *		,A.KANREN_KIKAN3		--関連分野の研究者-所属機関3
	 *		,A.KANREN_BUKYOKU3		--関連分野の研究者-所属部局3
	 *		,A.KANREN_SHOKU3		--関連分野の研究者-職名3
	 *		,A.KANREN_SENMON3		--関連分野の研究者-専門分野3
	 *		,A.KANREN_TEL3		--関連分野の研究者-勤務先電話番号3
	 *		,A.KANREN_JITAKUTEL3	--関連分野の研究者-自宅電話番号3
	 *		,A.KANREN_MAIL3		--関連分野の研究者-E-mail3
	 *		,A.XML_PATH			--XMLの格納パス
	 *		,A.PDF_PATH			--PDFの格納パス
	 *		,A.JURI_KEKKA			--受理結果
	 *		,A.JURI_BIKO			--受理結果備考
	 *		,A.SUISENSHO_PATH			--推薦書の格納パス
	 *		,A.KEKKA1_ABC			--１次審査結果(ABC)
	 *		,A.KEKKA1_TEN			--１次審査結果(点数)
	 *		,A.KEKKA1_TEN_SORTED		--１次審査結果(点数順)
	 *		,A.SHINSA1_BIKO			--１次審査備考
	 *		,A.KEKKA2				--２次審査結果
	 *		,A.SOU_KEHI			--総経費（学振入力）
	 *		,A.SHONEN_KEHI			--初年度経費（学振入力）
	 *		,A.SHINSA2_BIKO			--業務担当者記入欄
	 *		,A.JOKYO_ID			--申請状況ID
	 *		,A.SAISHINSEI_FLG			--再申請フラグ
	 *		,A.DEL_FLG			--削除フラグ
	 *	FROM
	 *		SHINSEIDATAKANRI 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>第二引数shinseiDataPkの変数SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(2)申請データを2次審査結果として<b>ShinsaKekka2ndInfo</b>に格納し、返却する。<br /><br />
	 * 　　格納する値は、以下のもの。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * 	SystemNo			-- システム受付番号
	 * 	Kekka2			-- 2次審査結果
	 * 	SouKehi			-- 総経費
	 * 	ShonenKehi			-- 初年度経費
	 * 	Shinsa2Biko			-- 業務担当者記入欄</pre>
	 * </td></tr>
	 * </table><br />
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return 2次審査結果を持つShinsaKekka2ndInfo
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getShinsaKekka2nd(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public ShinsaKekka2ndInfo getShinsaKekka2nd(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			
			//申請データ管理DAO
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//該当申請データを取得する
			ShinseiDataInfo shinseiDataInfo = null;
			try{
				shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"申請書管理データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//2次審査結果（参照用）の生成
			ShinsaKekka2ndInfo kekka2ndInfo = new ShinsaKekka2ndInfo();
			kekka2ndInfo.setSystemNo(shinseiDataInfo.getSystemNo());		//システム受付番号
			kekka2ndInfo.setKekka2(shinseiDataInfo.getKekka2());			//2次審査結果
			kekka2ndInfo.setSouKehi(shinseiDataInfo.getSouKehi());			//総経費
			kekka2ndInfo.setShonenKehi(shinseiDataInfo.getShonenKehi());	//初年度経費
			kekka2ndInfo.setShinsa2Biko(shinseiDataInfo.getShinsa2Biko());	//業務担当者記入欄
			
			return kekka2ndInfo;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}
	
	
	
	/**
	 * 審査結果情報の取得.<br /><br />
	 * 
	 * 
	 * 　(1)検索条件を元にSQL文を生成し、バックアップ用のデータベースから該当申請データを取得する。<br />
	 * 　　　(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--システム受付番号
	 *		,A.UKETUKE_NO			--申請番号
	 *		,A.JIGYO_ID			--事業ID
	 *		,A.NENDO				--年度
	 *		,A.KAISU				--回数
	 *		,A.JIGYO_NAME			--事業名
	 *		,A.SHINSEISHA_ID			--申請者ID
	 *		,A.SAKUSEI_DATE			--申請書作成日
	 *		,A.SHONIN_DATE			--所属機関承認日
	 *		,A.JYURI_DATE			--学振受理日
	 *		,A.NAME_KANJI_SEI			--申請者氏名（漢字等-姓）
	 *		,A.NAME_KANJI_MEI			--申請者氏名（漢字等-名）
	 *		,A.NAME_KANA_SEI			--申請者氏名（フリガナ-姓）
	 *		,A.NAME_KANA_MEI			--申請者氏名（フリガナ-名）
	 *		,A.NAME_RO_SEI			--申請者氏名（ローマ字-姓）
	 *		,A.NAME_RO_MEI			--申請者氏名（ローマ字-名）
	 *		,A.NENREI				--年齢
	 *		,A.KENKYU_NO			--申請者研究者番号
	 *		,A.SHOZOKU_CD			--所属機関コード
	 *		,A.SHOZOKU_NAME			--所属機関名
	 *		,A.SHOZOKU_NAME_RYAKU		--所属機関名（略称）
	 *		,A.BUKYOKU_CD			--部局コード
	 *		,A.BUKYOKU_NAME			--部局名
	 *		,A.BUKYOKU_NAME_RYAKU		--部局名（略称）
	 *		,A.SHOKUSHU_CD			--職名コード
	 *		,A.SHOKUSHU_NAME_KANJI		--職名（和文）
	 *		,A.SHOKUSHU_NAME_RYAKU		--職名（略称）
	 *		,A.ZIP				--郵便番号
	 *		,A.ADDRESS			--住所
	 *		,A.TEL				--TEL
	 *		,A.FAX				--FAX
	 *		,A.EMAIL				--E-Mail
	 *		,A.SENMON				--現在の専門
	 *		,A.GAKUI				--学位
	 *		,A.BUNTAN				--役割分担
	 *		,A.KADAI_NAME_KANJI		--研究課題名(和文）
	 *		,A.KADAI_NAME_EIGO			--研究課題名(英文）
	 *		,A.JIGYO_KUBUN			--事業区分
	 *		,A.SHINSA_KUBUN			--審査区分
	 *		,A.SHINSA_KUBUN_MEISHO		--審査区分名称
	 *		,A.BUNKATSU_NO			--分割番号
	 *		,A.BUNKATSU_NO_MEISHO		--分割番号名称
	 *		,A.KENKYU_TAISHO			--研究対象の類型
	 *		,A.KEI_NAME_NO			--系等の区分番号
	 *		,A.KEI_NAME			--系等の区分
	 *		,A.KEI_NAME_RYAKU			--系等の区分略称
	 *		,A.BUNKASAIMOKU_CD			--細目番号
	 *		,A.BUNYA_NAME			--分野
	 *		,A.BUNKA_NAME			--分科
	 *		,A.SAIMOKU_NAME			--細目
	 *		,A.BUNKASAIMOKU_CD2		--細目番号2
	 *		,A.BUNYA_NAME2			--分野2
	 *		,A.BUNKA_NAME2			--分科2
	 *		,A.SAIMOKU_NAME2			--細目2
	 *		,A.KANTEN_NO			--推薦の観点番号
	 *		,A.KANTEN				--推薦の観点
	 *		,A.KANTEN_RYAKU			--推薦の観点略称
	 *		,A.KEIHI1				--1年目研究経費
	 *		,A.BIHINHI1			--1年目設備備品費
	 *		,A.SHOMOHINHI1			--1年目消耗品費
	 *		,A.KOKUNAIRYOHI1			--1年目国内旅費
	 *		,A.GAIKOKURYOHI1			--1年目外国旅費
	 *		,A.RYOHI1				--1年目旅費
	 *		,A.SHAKIN1			--1年目謝金等
	 *		,A.SONOTA1			--1年目その他
	 *		,A.KEIHI2				--2年目研究経費
	 *		,A.BIHINHI2			--2年目設備備品費
	 *		,A.SHOMOHINHI2			--2年目消耗品費
	 *		,A.KOKUNAIRYOHI2			--2年目国内旅費
	 *		,A.GAIKOKURYOHI2			--2年目外国旅費
	 *		,A.RYOHI2				--2年目旅費
	 *		,A.SHAKIN2			--2年目謝金等
	 *		,A.SONOTA2			--2年目その他
	 *		,A.KEIHI3				--3年目研究経費
	 *		,A.BIHINHI3			--3年目設備備品費
	 *		,A.SHOMOHINHI3			--3年目消耗品費
	 *		,A.KOKUNAIRYOHI3			--3年目国内旅費
	 *		,A.GAIKOKURYOHI3			--3年目外国旅費
	 *		,A.RYOHI3				--3年目旅費
	 *		,A.SHAKIN3			--3年目謝金等
	 *		,A.SONOTA3			--3年目その他
	 *		,A.KEIHI4				--4年目研究経費
	 *		,A.BIHINHI4			--4年目設備備品費
	 *		,A.SHOMOHINHI4			--4年目消耗品費
	 *		,A.KOKUNAIRYOHI4			--4年目国内旅費
	 *		,A.GAIKOKURYOHI4			--4年目外国旅費
	 *		,A.RYOHI4				--4年目旅費
	 *		,A.SHAKIN4			--4年目謝金等
	 *		,A.SONOTA4			--4年目その他
	 *		,A.KEIHI5				--5年目研究経費
	 *		,A.BIHINHI5			--5年目設備備品費
	 *		,A.SHOMOHINHI5			--5年目消耗品費
	 *		,A.KOKUNAIRYOHI5			--5年目国内旅費
	 *		,A.GAIKOKURYOHI5			--5年目外国旅費
	 *		,A.RYOHI5				--5年目旅費
	 *		,A.SHAKIN5			--5年目謝金等
	 *		,A.SONOTA5			--5年目その他
	 *		,A.KEIHI_TOTAL			--総計-研究経費
	 *		,A.BIHINHI_TOTAL			--総計-設備備品費
	 *		,A.SHOMOHINHI_TOTAL		--総計-消耗品費
	 *		,A.KOKUNAIRYOHI_TOTAL		--総計-国内旅費
	 *		,A.GAIKOKURYOHI_TOTAL		--総計-外国旅費
	 *		,A.RYOHI_TOTAL			--総計-旅費
	 *		,A.SHAKIN_TOTAL			--総計-謝金等
	 *		,A.SONOTA_TOTAL			--総計-その他
	 *		,A.SOSHIKI_KEITAI_NO		--研究組織の形態番号
	 *		,A.SOSHIKI_KEITAI			--研究組織の形態
	 *		,A.BUNTANKIN_FLG			--分担金の有無
	 *		,A.KOYOHI				--研究支援者雇用経費
	 *		,A.KENKYU_NINZU			--研究者数
	 *		,A.TAKIKAN_NINZU			--他機関の分担者数
	 *		,A.SHINSEI_KUBUN			--新規継続区分
	 *		,A.KADAI_NO_KEIZOKU		--継続分の研究課題番号
	 *		,A.SHINSEI_FLG_NO			--継続分の研究課題番号
	 *		,A.SHINSEI_FLG			--申請の有無
	 *		,A.KADAI_NO_SAISYU			--最終年度課題番号
	 *		,A.KAIJIKIBO_FLG_NO		--開示希望の有無番号
	 *		,A.KAIJIKIBO_FLG			--開示希望の有無
	 *		,A.KAIGAIBUNYA_CD			--海外分野コード
	 *		,A.KAIGAIBUNYA_NAME		--海外分野名称
	 *		,A.KAIGAIBUNYA_NAME_RYAKU		--海外分野略称
	 *		,A.KANREN_SHIMEI1		--関連分野の研究者-氏名1
	 *		,A.KANREN_KIKAN1		--関連分野の研究者-所属機関1
	 *		,A.KANREN_BUKYOKU1		--関連分野の研究者-所属部局1
	 *		,A.KANREN_SHOKU1		--関連分野の研究者-職名1
	 *		,A.KANREN_SENMON1		--関連分野の研究者-専門分野1
	 *		,A.KANREN_TEL1		--関連分野の研究者-勤務先電話番号1
	 *		,A.KANREN_JITAKUTEL1	--関連分野の研究者-自宅電話番号1
	 *		,A.KANREN_MAIL1		--関連分野の研究者-E-mail1
	 *		,A.KANREN_SHIMEI2		--関連分野の研究者-氏名2
	 *		,A.KANREN_KIKAN2		--関連分野の研究者-所属機関2
	 *		,A.KANREN_BUKYOKU2		--関連分野の研究者-所属部局2
	 *		,A.KANREN_SHOKU2		--関連分野の研究者-職名2
	 *		,A.KANREN_SENMON2		--関連分野の研究者-専門分野2
	 *		,A.KANREN_TEL2		--関連分野の研究者-勤務先電話番号2
	 *		,A.KANREN_JITAKUTEL2	--関連分野の研究者-自宅電話番号2
	 *		,A.KANREN_MAIL2		--関連分野の研究者-E-mail2
	 *		,A.KANREN_SHIMEI3		--関連分野の研究者-氏名3
	 *		,A.KANREN_KIKAN3		--関連分野の研究者-所属機関3
	 *		,A.KANREN_BUKYOKU3		--関連分野の研究者-所属部局3
	 *		,A.KANREN_SHOKU3		--関連分野の研究者-職名3
	 *		,A.KANREN_SENMON3		--関連分野の研究者-専門分野3
	 *		,A.KANREN_TEL3		--関連分野の研究者-勤務先電話番号3
	 *		,A.KANREN_JITAKUTEL3	--関連分野の研究者-自宅電話番号3
	 *		,A.KANREN_MAIL3		--関連分野の研究者-E-mail3
	 *		,A.XML_PATH			--XMLの格納パス
	 *		,A.PDF_PATH			--PDFの格納パス
	 *		,A.JURI_KEKKA			--受理結果
	 *		,A.JURI_BIKO			--受理結果備考
	 *		,A.SUISENSHO_PATH			--推薦書の格納パス
	 *		,A.KEKKA1_ABC			--１次審査結果(ABC)
	 *		,A.KEKKA1_TEN			--１次審査結果(点数)
	 *		,A.KEKKA1_TEN_SORTED		--１次審査結果(点数順)
	 *		,A.SHINSA1_BIKO			--１次審査備考
	 *		,A.KEKKA2				--２次審査結果
	 *		,A.SOU_KEHI			--総経費（学振入力）
	 *		,A.SHONEN_KEHI			--初年度経費（学振入力）
	 *		,A.SHINSA2_BIKO			--業務担当者記入欄
	 *		,A.JOKYO_ID			--申請状況ID
	 *		,A.SAISHINSEI_FLG			--再申請フラグ
	 *		,A.DEL_FLG			--削除フラグ
	 *	FROM
	 *		SHINSEIDATAKANRI 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>第二引数shinseiDataPkの変数SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(2)検索条件を元にSQL文を生成し、バックアップ用のデータベースから該当審査結果データを取得する。<br />
	 * 　　　(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--システム受付番号
	 *		,A.UKETUKE_NO			--申請番号
	 *		,A.SHINSAIN_NO			--審査員番号
	 *		,A.JIGYO_KUBUN			--事業区分
	 *		,A.SEQ_NO				--シーケンス番号
	 *		,A.SHINSA_KUBUN			--審査区分
	 *		,A.SHINSAIN_NAME_KANJI_SEI		--審査員名（漢字－姓）
	 *		,A.SHINSAIN_NAME_KANJI_MEI		--審査員名（漢字－名）
	 *		,A.NAME_KANA_SEI			--審査員名（フリガナ－姓）
	 *		,A.NAME_KANA_MEI			--審査員名（フリガナ－名）
	 *		,A.SHOZOKU_NAME			--審査員所属機関名
	 *		,A.BUKYOKU_NAME			--審査員部局名
	 *		,A.SHOKUSHU_NAME			--審査員職名
	 *		,A.JIGYO_ID			--事業ID
	 *		,A.JIGYO_NAME			--事業名
	 *		,A.BUNKASAIMOKU_CD			--細目番号
	 *		,A.EDA_NO				--枝番
	 *		,A.CHECKDIGIT			--チェックデジット
	 *		,A.KEKKA_ABC			--総合評価（ABC）
	 *		,A.KEKKA_TEN			--総合評価（点数）
	 *		,NVL(REPLACE(A.KEKKA_TEN,'-','0'),'-1')SORT_KEKKA_TEN
	 *		--ソート用。審査結果（点数）の値NULL→'-1'、'-'→'0'に置換）
	 *		,A.COMMENT1			--コメント1
	 *		,A.COMMENT2			--コメント2
	 *		,A.COMMENT3			--コメント3
	 *		,A.COMMENT4			--コメント4
	 *		,A.COMMENT5			--コメント5
	 *		,A.COMMENT6			--コメント6
	 *		,A.KENKYUNAIYO			--研究内容
	 *		,A.KENKYUKEIKAKU			--研究計画
	 *		,A.TEKISETSU_KAIGAI		--適切性-海外
	 *		,A.TEKISETSU_KENKYU1		--適切性-研究（1）
	 *		,A.TEKISETSU			--適切性
	 *		,A.DATO				--妥当性
	 *		,A.SHINSEISHA			--研究代表者
	 *		,A.KENKYUBUNTANSHA			--研究分担者
	 *		,A.HITOGENOMU			--ヒトゲノム
	 *		,A.TOKUTEI			--特定胚
	 *		,A.HITOES				--ヒトES細胞
	 *		,A.KUMIKAE			--遺伝子組換え実験
	 *		,A.CHIRYO				--遺伝子治療臨床研究
	 *		,A.EKIGAKU			--疫学研究
	 *		,A.COMMENTS			--コメント
	 *		,A.TENPU_PATH			--添付ファイル格納パス
	 *		,A.SHINSA_JOKYO			--審査状況
	 *		,A.BIKO				--備考
	 *	FROM
	 *		SHINSAKEKKA 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?
	 *	ORDER BY
	 *		KEKKA_ABCASC			--総合評価（ABC）の昇順
	 *		,SORT_KEKKA_TENDESC		--総合評価（点数）の降順
	 *		,SHINSAIN_NOASC			--審査員番号の昇順
	 *		,JIGYO_KUBUNASC			--事業区分の昇順</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>第二引数shinseiDataPkの変数SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(3)検索条件を元にSQL文を生成し、バックアップ用のデータベースから該当事業管理データを取得する。<br />
	 * 　　　(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.JIGYO_ID			--事業ID
	 *		,A.NENDO				--年度
	 *		,A.KAISU				--回数
	 *		,A.JIGYO_NAME			--事業名
	 *		,A.JIGYO_KUBUN			--事業区分
	 *		,A.SHINSA_KUBUN			--審査区分
	 *		,A.TANTOKA_NAME			--業務担当課
	 *		,A.TANTOKAKARI			--業務担当係名
	 *		,A.TOIAWASE_NAME			--問い合わせ先担当者名
	 *		,A.TOIAWASE_TEL			--問い合わせ先電話番号
	 *		,A.TOIAWASE_EMAIL			--問い合わせ先E-mail
	 *		,A.UKETUKEKIKAN_START		--学振受付期間（開始）
	 *		,A.UKETUKEKIKAN_END		--学振受付期間（終了）
	 *		,A.SHINSAKIGEN			--審査期限
	 *		,A.TENPU_NAME			--添付文書名
	 *		,A.TENPU_WIN		--添付ファイル格納フォルダ（Win）
	 *		,A.TENPU_MAC		--添付ファイル格納フォルダ（Mac）
	 *		,A.HYOKA_FILE_FLG			--評価用ファイル有無
	 *		,A.HYOKA_FILE			--評価用ファイル格納フォルダ
	 *		,A.KOKAI_FLG			--公開フラグ
	 *		,A.KESSAI_NO			--公開決裁番号
	 *		,A.KOKAI_ID			--公開確定者ID
	 *		,A.HOKAN_DATE			--データ保管日
	 *		,A.YUKO_DATE			--保管有効期限
	 *		,A.BIKO				--備考
	 *		,A.DEL_FLG			--削除フラグ
	 *	FROM
	 *		JIGYOKANRI 'dbLink' A
	 *	WHERE
	 *		JIGYO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>事業ID</td><td>JigyoKanriPkの変数JigyoId</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(4)取得した申請データ・審査結果データ・事業管理データの値を、<b>ShinsaKekkaInputInfo</b>に格納する。<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * 	SystemNo			-- システム受付番号
	 * 	ShinsainNo			-- 審査員番号
	 * 	JigyoKubun			-- 事業区分
	 * 	Nendo			-- 年度
	 * 	Kaisu			-- 回数
	 * 	JigyoId			-- 海外研究分野名（基盤）
	 * 	BunkaSaimokuCd			-- 細目番号（基盤）
	 * 	SaimokuName			-- 細目名（基盤）
	 * 	JigyoId			-- 事業ID
	 * 	JigyoName			-- 研究種目名
	 * 	UketukeNo			-- 申請番号
	 * 	KadaiNameKanji			-- 研究課題名
	 * 	NameKanjiSei			-- 申請者名-姓
	 * 	NameKanjiMei			-- 申請者名-名
	 * 	ShozokuName			-- 所属機関名
	 * 	BukyokuName			-- 部局名
	 * 	ShokushuNameKanji			-- 職名
	 * 	Kanten			-- 推薦の観点
	 * 	KeiName			-- 系等の区分
	 * 	KekkaAbc			-- 総合評点（ABC）
	 * 	KekkaTen			-- 総合評点（点数）
	 * 	Comment1			-- コメント1
	 * 	Comment2			-- コメント2
	 * 	Comment3			-- コメント3
	 * 	Comment4			-- コメント4
	 * 	Comment5			-- コメント5
	 * 	Comment6			-- コメント6
	 * 	KenkyuNaiyo			-- 研究内容（基盤）
	 * 	KenkyuKeikaku			-- 研究計画（基盤）
	 * 	TekisetsuKaigai			-- 適切性-海外（基盤）
	 * 	TekisetsuKenkyu1			-- 適切性-研究(1)（基盤）
	 * 	Tekisetsu			-- 適切性（基盤）
	 * 	Dato			-- 妥当性（基盤）
	 * 	Shinseisha			-- 研究代表者（基盤）
	 * 	KenkyuBuntansha			-- 研究分担者（基盤）
	 * 	Hitogenomu			-- ヒトゲノム（基盤）
	 * 	Tokutei			-- 特定胚（基盤）
	 * 	HitoEs			-- ヒトES細胞（基盤）
	 * 	Kumikae			-- 遺伝子組換え実験（基盤）
	 * 	Chiryo			-- 遺伝子治療臨床研究（基盤）
	 * 	Ekigaku			-- 疫学研究（基盤）
	 * 	Comments			-- コメント（基盤）
	 * 	TenpuFlg			-- 添付ファイル格納フラグ
	 * 	HyokaFileFlg			-- 評価用ファイル有無</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * 
	 * 　(5)(4)で値を格納した<b>ShinsaKekkaInputInfo</b>に、添付ファイル名・総合評価（ABC）の表示ラベル名を加える。<br />
	 * 　　　添付ファイル名は、取得した審査結果データ内の"添付ファイル格納パス"の値から取得する。<br />
	 * 　　　総合評価（ABC）の表示ラベル名は、以下のSQL文を発行して取得する。<br />
	 * 　　　(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.ATAI
	 *		,A.NAME
	 *		,A.RYAKU
	 *		,A.SORT
	 *		,A.BIKO
	 *	FROM
	 *		MASTER_LABEL A
	 *	WHERE
	 *		A.LABEL_KUBUN=?
	 *		AND A.ATAI=?
	 *	ORDER BY
	 *		SORT</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>ラベル区分</td><td>リテラル"KEKKA_ABC"</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>値</td><td>ShinsaKekkaInputInfoの変数KekkaAbc</td></tr>
	 * </table><br /><br />
	 * 
	 * 　(6)ShinsaKekkaInputInfoを返却する。
	 * @param userInfo UserInfo
	 * @param shinsaKekkaPk ShinsaKekkaPk
	 * @return 審査結果情報（審査結果入力画面用）を保持するShinsaKekkaInputInfo
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#select1stShinsaKekka(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsaKekkaPk)
	 */
	public ShinsaKekkaInputInfo select1stShinsaKekka(
		UserInfo userInfo,
		ShinsaKekkaPk shinsaKekkaPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			
			//申請データ管理DAO
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//該当申請データを取得する
			ShinseiDataPk shinseiDataPk = new ShinseiDataPk(shinsaKekkaPk.getSystemNo());
			ShinseiDataInfo shinseiDataInfo = null;
			try{
				shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"申請書管理データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			//審査結果DAO
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//該当審査結果データを取得する
			ShinsaKekkaInfo shinsaKekkaInfo = null;
			try{
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection, shinsaKekkaPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//事業管理DAO
			JigyoKanriInfoDao jigyoDao = new JigyoKanriInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//該当事業管理データを取得する
			JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(shinseiDataInfo.getJigyoId());
			
			JigyoKanriInfo jigyoKanriInfo = null;
			try{
				jigyoKanriInfo = jigyoDao.selectJigyoKanriInfo(connection, jigyoKanriPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"事業管理データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//---審査結果入力オブジェクトの生成
			ShinsaKekkaInputInfo info = new ShinsaKekkaInputInfo();
			info.setSystemNo(shinsaKekkaPk.getSystemNo());									//システム受付番号
			info.setShinsainNo(shinsaKekkaPk.getShinsainNo());								//審査員番号
			info.setJigyoKubun(shinsaKekkaPk.getJigyoKubun());								//事業区分
			
			info.setNendo(shinseiDataInfo.getNendo());										//年度
			info.setKaisu(shinseiDataInfo.getKaisu());										//回数
			info.setJigyoId(shinseiDataInfo.getJigyoId());									//海外研究分野名（基盤）
			info.setBunkaSaimokuCd(shinseiDataInfo.getKadaiInfo().getBunkaSaimokuCd());		//細目番号（基盤）
			info.setSaimokuName(shinseiDataInfo.getKadaiInfo().getSaimokuName());			//細目名（基盤）
			info.setJigyoId(shinseiDataInfo.getJigyoId());									//事業ID
			info.setJigyoName(shinseiDataInfo.getJigyoName());								//研究種目名
			info.setUketukeNo(shinseiDataInfo.getUketukeNo());								//申請番号
			info.setKadaiNameKanji(shinseiDataInfo.getKadaiInfo().getKadaiNameKanji());		//研究課題名
			info.setNameKanjiSei(shinseiDataInfo.getDaihyouInfo().getNameKanjiSei());		//申請者名-姓
			info.setNameKanjiMei(shinseiDataInfo.getDaihyouInfo().getNameKanjiMei());		//申請者名-名
			info.setShozokuName(shinseiDataInfo.getDaihyouInfo().getShozokuName());			//所属機関名
			info.setBukyokuName(shinseiDataInfo.getDaihyouInfo().getBukyokuName());			//部局名
			info.setShokushuName(shinseiDataInfo.getDaihyouInfo().getShokushuNameKanji());	//職名
			info.setKanten(shinseiDataInfo.getKadaiInfo().getKanten());						//推薦の観点
			info.setKeiName(shinseiDataInfo.getKadaiInfo().getKeiName());					//系等の区分
			
			//2005.09.13 iso 基盤等追加対応
			info.setSoshikiKeitaiNo(shinseiDataInfo.getSoshikiKeitaiNo());					//研究組織の形態番号
			info.setShinseiFlgNo(shinseiDataInfo.getShinseiFlgNo());						//研究計画最終年度前年度の応募
			info.setBuntankinFlg(shinseiDataInfo.getBuntankinFlg());						//分担金の有無
			info.setBunkatsuNo(shinseiDataInfo.getKadaiInfo().getBunkatsuNo());				//分割番号
			info.setKaigaibunyaCd(shinseiDataInfo.getKaigaibunyaCd());						//海外分野コード
			info.setKaigaibunyaName(shinseiDataInfo.getKaigaibunyaName());					//海外分野名
			
			info.setKekkaAbc(shinsaKekkaInfo.getKekkaAbc());								//総合評点（ABC）
			info.setKekkaTen(shinsaKekkaInfo.getKekkaTen());								//総合評点（点数）
			info.setComment1(shinsaKekkaInfo.getComment1());								//コメント1
			info.setComment2(shinsaKekkaInfo.getComment2());								//コメント2
			info.setComment3(shinsaKekkaInfo.getComment3());								//コメント3
			info.setComment4(shinsaKekkaInfo.getComment4());								//コメント4
			info.setComment5(shinsaKekkaInfo.getComment5());								//コメント5
			info.setComment6(shinsaKekkaInfo.getComment6());								//コメント6
			info.setKenkyuNaiyo(shinsaKekkaInfo.getKenkyuNaiyo());							//研究内容（基盤）
			info.setKenkyuKeikaku(shinsaKekkaInfo.getKenkyuKeikaku());						//研究計画（基盤）
			info.setTekisetsuKaigai(shinsaKekkaInfo.getTekisetsuKaigai());					//適切性-海外（基盤）
			info.setTekisetsuKenkyu1(shinsaKekkaInfo.getTekisetsuKenkyu1());					//適切性-研究(1)（基盤）
			info.setTekisetsu(shinsaKekkaInfo.getTekisetsu());								//適切性（基盤）
			info.setDato(shinsaKekkaInfo.getDato());										//妥当性（基盤）
			info.setShinseisha(shinsaKekkaInfo.getShinseisha());							//研究代表者（基盤）
			info.setKenkyuBuntansha(shinsaKekkaInfo.getKenkyuBuntansha());					//研究分担者（基盤）	
			info.setHitogenomu(shinsaKekkaInfo.getHitogenomu());							//ヒトゲノム（基盤）
			info.setTokutei(shinsaKekkaInfo.getTokutei());									//特定胚（基盤）
			info.setHitoEs(shinsaKekkaInfo.getHitoEs());									//ヒトES細胞（基盤）
			info.setKumikae(shinsaKekkaInfo.getKumikae());									//遺伝子組換え実験（基盤）
			info.setChiryo(shinsaKekkaInfo.getChiryo());									//遺伝子治療臨床研究（基盤）
			info.setEkigaku(shinsaKekkaInfo.getEkigaku());									//疫学研究（基盤）							
			info.setComments(shinsaKekkaInfo.getComments());								//コメント（基盤）
			info.setTenpuFlg(shinsaKekkaInfo.getTenpuFlg());								//添付ファイル格納フラグ
			info.setHyokaFileFlg(jigyoKanriInfo.getHyokaFileFlg());							//評価用ファイル有無

			//2005.09.13 iso 基盤等追加対応
			info.setJigyoCd(shinsaKekkaInfo.getJigyoId().substring(2,7));					//事業コード
			
			//添付ファイル名をセット
			String tenpuPath = shinsaKekkaInfo.getTenpuPath();
			if(tenpuPath != null && tenpuPath.length() != 0){
				info.setTenpuName(new File(tenpuPath).getName());
			}
						
			//総合評価（ABC）の表示ラベル名をセット
			if(info.getKekkaAbc() != null && info.getKekkaAbc().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KEKKA_ABC,
																	info.getKekkaAbc());		
					info.setKekkaAbcLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、総合評価（ABC）をセット
					info.setKekkaAbcLabel(info.getKekkaAbc());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}			
			}

			//2005.09.13 iso 基盤等追加対応
			//総合評価（点数）の表示ラベル名をセット
			if(info.getKekkaTen() != null && info.getKekkaTen().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KEKKA_TEN,
																	info.getKekkaTen());		
					info.setKekkaTenLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、総合評価（点数）をセット
					info.setKekkaTenLabel(info.getKekkaTen());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			
			//総合評価（萌芽）の表示ラベル名をセット
			if(info.getKekkaTen() != null && info.getKekkaTen().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KEKKA_TEN_HOGA,
																	info.getKekkaTen());		
					info.setKekkaTenHogaLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、総合評価（萌芽）をセット
					info.setKekkaTenHogaLabel(info.getKekkaTen());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			
			//研究内容の表示ラベル名をセット
			if(info.getKenkyuNaiyo() != null && info.getKenkyuNaiyo().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KENKYUNAIYO,
																	info.getKenkyuNaiyo());		
					info.setKenkyuNaiyoLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、研究内容をセット
					info.setKenkyuNaiyoLabel(info.getKenkyuNaiyo());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//研究計画の表示ラベル名をセット
			if(info.getKenkyuKeikaku() != null && info.getKenkyuKeikaku().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KENKYUKEIKAKU,
																	info.getKenkyuKeikaku());		
					info.setKenkyuKeikakuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、研究計画をセット
					info.setKenkyuKeikakuLabel(info.getKenkyuKeikaku());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//適切性-海外の表示ラベル名をセット
			if(info.getTekisetsuKaigai() != null && info.getTekisetsuKaigai().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TEKISETSU_KAIGAI,
																	info.getTekisetsuKaigai());		
					info.setTekisetsuKaigaiLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、適切性-海外をセット
					info.setTekisetsuKaigaiLabel(info.getTekisetsuKaigai());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//適切性-研究(1)の表示ラベル名をセット
			if(info.getTekisetsuKenkyu1() != null && info.getTekisetsuKenkyu1().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TEKISETSU_KENKYU1,
																	info.getTekisetsuKenkyu1());		
					info.setTekisetsuKenkyu1Label((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、適切性-研究(1)をセット
					info.setTekisetsuKenkyu1Label(info.getTekisetsuKenkyu1());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//適切性の表示ラベル名をセット
			if(info.getTekisetsu() != null && info.getTekisetsu().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TEKISETSU,
																	info.getTekisetsu());		
					info.setTekisetsuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、適切性をセット
					info.setTekisetsuLabel(info.getTekisetsu());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//妥当性の表示ラベル名をセット
			if(info.getDato() != null && info.getDato().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.DATO,
																	info.getDato());		
					info.setDatoLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、妥当性をセット
					info.setDatoLabel(info.getDato());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//研究代表者の表示ラベル名をセット
			if(info.getShinseisha() != null && info.getShinseisha().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.SHINSEISHA,
																	info.getShinseisha());		
					info.setShinseishaLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、研究代表者をセット
					info.setShinseishaLabel(info.getShinseisha());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//研究分担者の表示ラベル名をセット
			if(info.getKenkyuBuntansha() != null && info.getKenkyuBuntansha().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KENKYUBUNTANSHA,
																	info.getKenkyuBuntansha());		
					info.setKenkyuBuntanshaLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、研究分担者をセット
					info.setKenkyuBuntanshaLabel(info.getKenkyuBuntansha());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//ヒトゲノムの表示ラベル名をセット
			if(info.getHitogenomu() != null && info.getHitogenomu().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.HITOGENOMU,
																	info.getHitogenomu());		
					info.setHitogenomuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、ヒトゲノムをセット
					info.setHitogenomuLabel(info.getHitogenomu());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//特定胚の表示ラベル名をセット
			if(info.getTokutei() != null && info.getTokutei().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TOKUTEI,
																	info.getTokutei());		
					info.setTokuteiLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、特定胚をセット
					info.setTokuteiLabel(info.getTokutei());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//ヒトＥＳ細胞の表示ラベル名をセット
			if(info.getHitoEs() != null && info.getHitoEs().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.HITOES,
																	info.getHitoEs());		
					info.setHitoEsLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、ヒトＥＳ細胞をセット
					info.setHitoEsLabel(info.getHitoEs());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//遺伝子組換え実験の表示ラベル名をセット
			if(info.getKumikae() != null && info.getKumikae().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KUMIKAE,
																	info.getKumikae());		
					info.setKumikaeLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、遺伝子組換え実験をセット
					info.setKumikaeLabel(info.getKumikae());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//遺伝子治療臨床研究の表示ラベル名をセット
			if(info.getChiryo() != null && info.getChiryo().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.CHIRYO,
																	info.getChiryo());		
					info.setChiryoLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、遺伝子治療臨床研究をセット
					info.setChiryoLabel(info.getChiryo());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//疫学研究の表示ラベル名をセット
			if(info.getEkigaku() != null && info.getEkigaku().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.EKIGAKU,
																	info.getEkigaku());		
					info.setEkigakuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//例外NoDataFoundExceptionが発生したときは、疫学研究をセット
					info.setEkigakuLabel(info.getEkigaku());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"ラベルマスタ情報取得中に例外が発生しました。",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			
			return info;
			
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	
	}
	
	
	
	/**
	 * 添付ファイルの取得.<br /><br />
	 * 
	 * 
	 * 　(1)検索条件を元にSQL文を生成し、バックアップ用のデータベースから該当審査結果情報を取得する。<br />
	 * 　　　(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--システム受付番号
	 *		,A.UKETUKE_NO			--申請番号
	 *		,A.SHINSAIN_NO			--審査員番号
	 *		,A.JIGYO_KUBUN			--事業区分
	 *		,A.SEQ_NO				--シーケンス番号
	 *		,A.SHINSA_KUBUN			--審査区分
	 *		,A.SHINSAIN_NAME_KANJI_SEI		--審査員名（漢字－姓）
	 *		,A.SHINSAIN_NAME_KANJI_MEI		--審査員名（漢字－名）
	 *		,A.NAME_KANA_SEI			--審査員名（フリガナ－姓）
	 *		,A.NAME_KANA_MEI			--審査員名（フリガナ－名）
	 *		,A.SHOZOKU_NAME			--審査員所属機関名
	 *		,A.BUKYOKU_NAME			--審査員部局名
	 *		,A.SHOKUSHU_NAME			--審査員職名
	 *		,A.JIGYO_ID			--事業ID
	 *		,A.JIGYO_NAME			--事業名
	 *		,A.BUNKASAIMOKU_CD			--細目番号
	 *		,A.EDA_NO				--枝番
	 *		,A.CHECKDIGIT			--チェックデジット
	 *		,A.KEKKA_ABC			--総合評価（ABC）
	 *		,A.KEKKA_TEN			--総合評価（点数）
	 *		,A.COMMENT1			--コメント1
	 *		,A.COMMENT2			--コメント2
	 *		,A.COMMENT3			--コメント3
	 *		,A.COMMENT4			--コメント4
	 *		,A.COMMENT5			--コメント5
	 *		,A.COMMENT6			--コメント6
	 *		,A.KENKYUNAIYO			--研究内容
	 *		,A.KENKYUKEIKAKU			--研究計画
	 *		,A.TEKISETSU_KAIGAI		--適切性-海外
	 *		,A.TEKISETSU_KENKYU1		--適切性-研究（1）
	 *		,A.TEKISETSU			--適切性
	 *		,A.DATO				--妥当性
	 *		,A.SHINSEISHA			--研究代表者
	 *		,A.KENKYUBUNTANSHA			--研究分担者
	 *		,A.HITOGENOMU			--ヒトゲノム
	 *		,A.TOKUTEI			--特定胚
	 *		,A.HITOES				--ヒトES細胞
	 *		,A.KUMIKAE			--遺伝子組換え実験
	 *		,A.CHIRYO				--遺伝子治療臨床研究
	 *		,A.EKIGAKU			--疫学研究
	 *		,A.COMMENTS			--コメント
	 *		,A.TENPU_PATH			--添付ファイル格納パス
	 *		,DECODE
	 *		(
	 *			NVL(A.TENPU_PATH,'null')
	 *			,'null','FALSE'	--添付ファイル格納パスがNULLのとき
	 *			,'TRUE'		--添付ファイル格納パスがNULL以外のとき
	 *		)
	 *		TENPU_FLG				--添付ファイル格納フラグ
	 *		,A.SHINSA_JOKYO			--審査状況
	 *		,A.BIKO				--備考
	 *	FROM
	 *		SHINSAKEKKA 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?
	 *		AND SHINSAIN_NO=?
	 *		AND JIGYO_KUBUN=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>第二引数ShinsaKekkaPkの変数SystemNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>審査員番号</td><td>第二引数ShinsaKekkaPkの変数ShinsainNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>事業区分</td><td>第二引数ShinsaKekkaPkの変数JigyoKubun</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(2)取得した審査結果情報内の値"添付ファイル格納パス名"をUNC形式に変換する。<br /><br />
	 * 
	 * 
	 * 　(3)変換したファイルパスを利用してファイルを読み込み、返却する。<br /><br />
	 * @param userInfo UserInfo
	 * @param pkInfo ShinsaKekkaPk
	 * @return 審査結果情報の添付ファイルのFileResource
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getHyokaFileRes(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsaKekkaPk)
	 */
	public FileResource getHyokaFileRes(
		UserInfo userInfo,
		ShinsaKekkaPk pkInfo)
		throws ApplicationException
	{
		//-----審査結果情報の取得-----
		Connection   connection  = null;
		ShinsaKekkaInfo shinsaKekkaInfo = null;
		try {		
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			
			//審査結果DAO（DBLink）
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			try{
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection, pkInfo);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"審査結果データ取得中にDBエラーが発生しました。",
					new ErrorInfo("errors.4004"),
					e);
			}		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}		
		
		
		//-----パス情報の変換-----
		String filePath = shinsaKekkaInfo.getTenpuPath();
		if(filePath == null || filePath.equals("")){
			throw new FileIOException(
				"ファイルパスが不正です。ファイルパス'" + filePath + "'");			
		}
		//パス文字列をUNC形式に変換する
		filePath = StringUtil.substrReplace(filePath, DRIVE_LETTER_CONVERTED_TO_UNC, HOKAN_SERVER_UNC);
		
		
		//-----ファイル取得-----
		FileResource fileRes = null;
		try{
			File file = new File(filePath);
			fileRes = FileUtil.readFile(file);
		}catch(FileNotFoundException e){
			throw new FileIOException(
				"ファイルが見つかりませんでした。",			
				e);
		}catch(IOException e){
			throw new FileIOException(
				"ファイルの入出力中にエラーが発生しました。",
				e);
		}
		return fileRes;		
		
	}
	
	
	
	/**
	 * 推薦書ファイルの取得.<br /><br />
	 * 
	 * 
	 * 　(1)検索条件を元にSQL文を生成し、バックアップ用のデータベースから該当申請データを取得する。<br />
	 * 　　　(バインド変数は、SQLの下の表を参照)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--システム受付番号
	 *		,A.UKETUKE_NO			--申請番号
	 *		,A.JIGYO_ID			--事業ID
	 *		,A.NENDO				--年度
	 *		,A.KAISU				--回数
	 *		,A.JIGYO_NAME			--事業名
	 *		,A.SHINSEISHA_ID			--申請者ID
	 *		,A.SAKUSEI_DATE			--申請書作成日
	 *		,A.SHONIN_DATE			--所属機関承認日
	 *		,A.JYURI_DATE			--学振受理日
	 *		,A.NAME_KANJI_SEI			--申請者氏名（漢字等-姓）
	 *		,A.NAME_KANJI_MEI			--申請者氏名（漢字等-名）
	 *		,A.NAME_KANA_SEI			--申請者氏名（フリガナ-姓）
	 *		,A.NAME_KANA_MEI			--申請者氏名（フリガナ-名）
	 *		,A.NAME_RO_SEI			--申請者氏名（ローマ字-姓）
	 *		,A.NAME_RO_MEI			--申請者氏名（ローマ字-名）
	 *		,A.NENREI				--年齢
	 *		,A.KENKYU_NO			--申請者研究者番号
	 *		,A.SHOZOKU_CD			--所属機関コード
	 *		,A.SHOZOKU_NAME			--所属機関名
	 *		,A.SHOZOKU_NAME_RYAKU		--所属機関名（略称）
	 *		,A.BUKYOKU_CD			--部局コード
	 *		,A.BUKYOKU_NAME			--部局名
	 *		,A.BUKYOKU_NAME_RYAKU		--部局名（略称）
	 *		,A.SHOKUSHU_CD			--職名コード
	 *		,A.SHOKUSHU_NAME_KANJI		--職名（和文）
	 *		,A.SHOKUSHU_NAME_RYAKU		--職名（略称）
	 *		,A.ZIP				--郵便番号
	 *		,A.ADDRESS			--住所
	 *		,A.TEL				--TEL
	 *		,A.FAX				--FAX
	 *		,A.EMAIL				--E-Mail
	 *		,A.SENMON				--現在の専門
	 *		,A.GAKUI				--学位
	 *		,A.BUNTAN				--役割分担
	 *		,A.KADAI_NAME_KANJI		--研究課題名(和文）
	 *		,A.KADAI_NAME_EIGO			--研究課題名(英文）
	 *		,A.JIGYO_KUBUN			--事業区分
	 *		,A.SHINSA_KUBUN			--審査区分
	 *		,A.SHINSA_KUBUN_MEISHO		--審査区分名称
	 *		,A.BUNKATSU_NO			--分割番号
	 *		,A.BUNKATSU_NO_MEISHO		--分割番号名称
	 *		,A.KENKYU_TAISHO			--研究対象の類型
	 *		,A.KEI_NAME_NO			--系等の区分番号
	 *		,A.KEI_NAME			--系等の区分
	 *		,A.KEI_NAME_RYAKU			--系等の区分略称
	 *		,A.BUNKASAIMOKU_CD			--細目番号
	 *		,A.BUNYA_NAME			--分野
	 *		,A.BUNKA_NAME			--分科
	 *		,A.SAIMOKU_NAME			--細目
	 *		,A.BUNKASAIMOKU_CD2		--細目番号2
	 *		,A.BUNYA_NAME2			--分野2
	 *		,A.BUNKA_NAME2			--分科2
	 *		,A.SAIMOKU_NAME2			--細目2
	 *		,A.KANTEN_NO			--推薦の観点番号
	 *		,A.KANTEN				--推薦の観点
	 *		,A.KANTEN_RYAKU			--推薦の観点略称
	 *		,A.KEIHI1				--1年目研究経費
	 *		,A.BIHINHI1			--1年目設備備品費
	 *		,A.SHOMOHINHI1			--1年目消耗品費
	 *		,A.KOKUNAIRYOHI1			--1年目国内旅費
	 *		,A.GAIKOKURYOHI1			--1年目外国旅費
	 *		,A.RYOHI1				--1年目旅費
	 *		,A.SHAKIN1			--1年目謝金等
	 *		,A.SONOTA1			--1年目その他
	 *		,A.KEIHI2				--2年目研究経費
	 *		,A.BIHINHI2			--2年目設備備品費
	 *		,A.SHOMOHINHI2			--2年目消耗品費
	 *		,A.KOKUNAIRYOHI2			--2年目国内旅費
	 *		,A.GAIKOKURYOHI2			--2年目外国旅費
	 *		,A.RYOHI2				--2年目旅費
	 *		,A.SHAKIN2			--2年目謝金等
	 *		,A.SONOTA2			--2年目その他
	 *		,A.KEIHI3				--3年目研究経費
	 *		,A.BIHINHI3			--3年目設備備品費
	 *		,A.SHOMOHINHI3			--3年目消耗品費
	 *		,A.KOKUNAIRYOHI3			--3年目国内旅費
	 *		,A.GAIKOKURYOHI3			--3年目外国旅費
	 *		,A.RYOHI3				--3年目旅費
	 *		,A.SHAKIN3			--3年目謝金等
	 *		,A.SONOTA3			--3年目その他
	 *		,A.KEIHI4				--4年目研究経費
	 *		,A.BIHINHI4			--4年目設備備品費
	 *		,A.SHOMOHINHI4			--4年目消耗品費
	 *		,A.KOKUNAIRYOHI4			--4年目国内旅費
	 *		,A.GAIKOKURYOHI4			--4年目外国旅費
	 *		,A.RYOHI4				--4年目旅費
	 *		,A.SHAKIN4			--4年目謝金等
	 *		,A.SONOTA4			--4年目その他
	 *		,A.KEIHI5				--5年目研究経費
	 *		,A.BIHINHI5			--5年目設備備品費
	 *		,A.SHOMOHINHI5			--5年目消耗品費
	 *		,A.KOKUNAIRYOHI5			--5年目国内旅費
	 *		,A.GAIKOKURYOHI5			--5年目外国旅費
	 *		,A.RYOHI5				--5年目旅費
	 *		,A.SHAKIN5			--5年目謝金等
	 *		,A.SONOTA5			--5年目その他
	 *		,A.KEIHI_TOTAL			--総計-研究経費
	 *		,A.BIHINHI_TOTAL			--総計-設備備品費
	 *		,A.SHOMOHINHI_TOTAL		--総計-消耗品費
	 *		,A.KOKUNAIRYOHI_TOTAL		--総計-国内旅費
	 *		,A.GAIKOKURYOHI_TOTAL		--総計-外国旅費
	 *		,A.RYOHI_TOTAL			--総計-旅費
	 *		,A.SHAKIN_TOTAL			--総計-謝金等
	 *		,A.SONOTA_TOTAL			--総計-その他
	 *		,A.SOSHIKI_KEITAI_NO		--研究組織の形態番号
	 *		,A.SOSHIKI_KEITAI			--研究組織の形態
	 *		,A.BUNTANKIN_FLG			--分担金の有無
	 *		,A.KOYOHI				--研究支援者雇用経費
	 *		,A.KENKYU_NINZU			--研究者数
	 *		,A.TAKIKAN_NINZU			--他機関の分担者数
	 *		,A.SHINSEI_KUBUN			--新規継続区分
	 *		,A.KADAI_NO_KEIZOKU		--継続分の研究課題番号
	 *		,A.SHINSEI_FLG_NO			--継続分の研究課題番号
	 *		,A.SHINSEI_FLG			--申請の有無
	 *		,A.KADAI_NO_SAISYU			--最終年度課題番号
	 *		,A.KAIJIKIBO_FLG_NO		--開示希望の有無番号
	 *		,A.KAIJIKIBO_FLG			--開示希望の有無
	 *		,A.KAIGAIBUNYA_CD			--海外分野コード
	 *		,A.KAIGAIBUNYA_NAME		--海外分野名称
	 *		,A.KAIGAIBUNYA_NAME_RYAKU		--海外分野略称
	 *		,A.KANREN_SHIMEI1		--関連分野の研究者-氏名1
	 *		,A.KANREN_KIKAN1		--関連分野の研究者-所属機関1
	 *		,A.KANREN_BUKYOKU1		--関連分野の研究者-所属部局1
	 *		,A.KANREN_SHOKU1		--関連分野の研究者-職名1
	 *		,A.KANREN_SENMON1		--関連分野の研究者-専門分野1
	 *		,A.KANREN_TEL1		--関連分野の研究者-勤務先電話番号1
	 *		,A.KANREN_JITAKUTEL1	--関連分野の研究者-自宅電話番号1
	 *		,A.KANREN_MAIL1		--関連分野の研究者-E-mail1
	 *		,A.KANREN_SHIMEI2		--関連分野の研究者-氏名2
	 *		,A.KANREN_KIKAN2		--関連分野の研究者-所属機関2
	 *		,A.KANREN_BUKYOKU2		--関連分野の研究者-所属部局2
	 *		,A.KANREN_SHOKU2		--関連分野の研究者-職名2
	 *		,A.KANREN_SENMON2		--関連分野の研究者-専門分野2
	 *		,A.KANREN_TEL2		--関連分野の研究者-勤務先電話番号2
	 *		,A.KANREN_JITAKUTEL2	--関連分野の研究者-自宅電話番号2
	 *		,A.KANREN_MAIL2		--関連分野の研究者-E-mail2
	 *		,A.KANREN_SHIMEI3		--関連分野の研究者-氏名3
	 *		,A.KANREN_KIKAN3		--関連分野の研究者-所属機関3
	 *		,A.KANREN_BUKYOKU3		--関連分野の研究者-所属部局3
	 *		,A.KANREN_SHOKU3		--関連分野の研究者-職名3
	 *		,A.KANREN_SENMON3		--関連分野の研究者-専門分野3
	 *		,A.KANREN_TEL3		--関連分野の研究者-勤務先電話番号3
	 *		,A.KANREN_JITAKUTEL3	--関連分野の研究者-自宅電話番号3
	 *		,A.KANREN_MAIL3		--関連分野の研究者-E-mail3
	 *		,A.XML_PATH			--XMLの格納パス
	 *		,A.PDF_PATH			--PDFの格納パス
	 *		,A.JURI_KEKKA			--受理結果
	 *		,A.JURI_BIKO			--受理結果備考
	 *		,A.SUISENSHO_PATH			--推薦書の格納パス
	 *		,A.KEKKA1_ABC			--１次審査結果(ABC)
	 *		,A.KEKKA1_TEN			--１次審査結果(点数)
	 *		,A.KEKKA1_TEN_SORTED		--１次審査結果(点数順)
	 *		,A.SHINSA1_BIKO			--１次審査備考
	 *		,A.KEKKA2				--２次審査結果
	 *		,A.SOU_KEHI			--総経費（学振入力）
	 *		,A.SHONEN_KEHI			--初年度経費（学振入力）
	 *		,A.SHINSA2_BIKO			--業務担当者記入欄
	 *		,A.JOKYO_ID			--申請状況ID
	 *		,A.SAISHINSEI_FLG			--再申請フラグ
	 *		,A.DEL_FLG			--削除フラグ
	 *	FROM
	 *		SHINSEIDATAKANRI 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>システム受付番号</td><td>第二引数shinseiDataPkの変数SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 　(2)取得した申請データ内の値"推薦書の格納パス名"をUNC形式に変換する。<br /><br />
	 * 
	 * 
	 * 　(3)変換したファイルパスを利用して推薦書ファイルを読み込み、返却する。<br /><br />
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return FileResource
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getSuisenFileRes(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public FileResource getSuisenFileRes(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection connection = null;
		String     filePath   = null;
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			
			ShinseiDataInfo dataInfo = 
								dao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			
			filePath = dataInfo.getSuisenshoPath();
			if(filePath == null || filePath.length() == 0){
				throw new FileIOException(
					"ファイルパスが不正です。filePath="+filePath);			
			}
			//パス文字列をUNC形式に変換する
			filePath = StringUtil.substrReplace(filePath, DRIVE_LETTER_CONVERTED_TO_UNC, HOKAN_SERVER_UNC);
			
			//推薦書ファイルを読み込む
			FileResource fileRes = FileUtil.readFile(new File(filePath));
			return fileRes;
			
		}catch(FileNotFoundException e){
			throw new FileIOException(
				"ファイルが見つかりませんでした。filePath="+filePath,
				e);
		}catch(IOException e){
			throw new FileIOException(
				"ファイルの入出力中にエラーが発生しました。filePath="+filePath,
				e);
		}catch(DataAccessException e){
			throw new ApplicationException(
				"申請書管理データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		}finally{
			DatabaseUtil.closeConnection(connection);
		}
		
	}
	

	//2005.09.09 iso 基盤等の審査結果を表示させるため追加
	/**
	 * ラベル名の取得.<br /><br />
	 * 
	 * 第一引数のListが持つLabelValueBeanの中のラベル名から、
	 * 指定された値に該当するラベル名を返す。<br /><br />
	 * 
	 * 指定された値に該当するラベル名が存在しない場合は、
	 * 値そのものを返す。<br /><br />
	 * 
	 * @param list LabelValueBeanを持つList
	 * @param value String
	 * @return ラベル名のString
	 */
	private String getLabelName(List list, String value){
		String labelName = value;  //初期値として値をセットする
		for(int i=0; i<list.size(); i++){
			LabelValueBean bean = (LabelValueBean)list.get(i);
			if(bean.getValue().equals(value)){
				labelName = bean.getLabel();
				break;
			}
		}
		return labelName;
	}

	
	



}