/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.pdf.autoConverter;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 変換ファイル監視クラス。
 * ID RCSfile="$RCSfile: FileWatcher.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:55 $"
 */
public class FileWatcher extends Thread {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログクラス。 */
	private static final Log log = LogFactory.getLog(FileWatcher.class);

	/** 出力ファイル拡張子。 */
	//2005.07.15 iso PDF添付機能の実装
//	private static String OUT_FILE_SUFFIX = ".iod";
	private static String OUT_FILE_SUFFIX = ".pdf";

	/**  監視時間間隔(秒) */
	private static int refreshSeconds = ApplicationSettings.getInteger(ISettingKeys.PDF_REFRESH_SECONDS).intValue();

	/** 変換対象INフォルダ。 */
	private static File IN_FOLDER = ApplicationSettings.getFile(ISettingKeys.PDF_IN_FOLDER);

	/** 変換結果OUTフォルダ。*/
	private static File OUT_FOLDER = ApplicationSettings.getFile(ISettingKeys.PDF_OUT_FOLDER);

	/** 変換ステータスファイル出力先フォルダ。 */
	private static File STATUS_FOLDER = ApplicationSettings.getFile(ISettingKeys.PDF_STATUS_FOLDER);

	/** 変換結果エラー時格納フォルダ。 */
	private static File ERR_FOLDER = ApplicationSettings.getFile(ISettingKeys.PDF_ERR_FOLDER);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 監視時間間隔。 */
	private final long delay;

	/** 変換リクエスト受け渡しクラス。*/
	private final AutoConverter converter;

	//---------------------------------------------------------------------
	// constructor
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 * @param converter	監視対象変換クラス。
	 */
	public FileWatcher(AutoConverter converter) {
		super();
		this.converter = converter;
		setDaemon(true);
		delay = refreshSeconds * 1000;
	}

	/**
	 * 変換対象INフォルダを取得する。
	 * @return	変換対象INフォルダ。
	 */
	public static File getInFolder() {
		return IN_FOLDER;
	}

	/**
	 * ファイル変更をチェックするスレッド。
	 */
	public void run() {
		while (true) {
			//変換対象入力ファイルチェック処理。
			if (log.isDebugEnabled()) {
				log.debug("--------- 変換ファイル確認開始 ---------");
			}
			checkFiles(converter.getFileInfo());
			try {
				sleep(delay);
			} catch (InterruptedException ie) {
				log.warn("ファイル監視スレッド待機中に割込が発生しました。");
			}
		}
	}

	/**
	 * 処理対象ファイル情報でステータス出力先フォルダを監視する。
	 * @param inputFiles	処理対象ファイル情報
	 */
	public void checkFiles(Map inputFiles) {
		//ステータスフォルダのファイルを一覧を取得する。
		if(!STATUS_FOLDER.exists()){
			throw new SystemException("変換ステータスファイル出力先フォルダ。'" + STATUS_FOLDER + "'が見つかりません。");
		}
		File[] statusFileList = STATUS_FOLDER.listFiles();
		
		for (int i = 0; i < statusFileList.length; i++) {
			File statusFile = statusFileList[i];
			if (log.isDebugEnabled()) {
				log.debug("チェック対象ステータスファイル名 ::" + statusFile);
			}
			synchronized (inputFiles) {
				//変換対象のファイル一覧を取得する。
				for (Iterator iter = inputFiles.keySet().iterator();iter.hasNext();) {
					//変換対象入力ファイル。	
					String inputFile = (String) iter.next();
					//処理結果オブジェクト
					ConvertResult result =(ConvertResult) inputFiles.get(inputFile);
					//ステータスファイルチェック
					String status = checkStatusFile(statusFile, inputFile);

					if (STATUS_NONE.equals(status)) {
						//該当ファイル以外
						continue;
					}
					//処理結果判別用フラグ
					try {
						if (STATUS_OK.equals(status)) {
							//OK START-----------------
							processOk(inputFile, result);
							return;
							//OK END  -----------------
						} else {
							//NG START-----------------
							processNg(statusFile, result);
							return;
							//NG END  -----------------
						}
					} finally {
						//オリジナルのステータスファイルを削除する。
						if (log.isDebugEnabled()) {
							log.debug("ステータスファイル'" + statusFile + "'の削除します。");
						}
						FileUtil.delete(statusFile);
					}
				}
			}
		}
	}

	/**
	 * 変換に失敗した場合の処理
	 * ステータスファイルをエラーフォルダに移動する。
	 * @param statusFile	
	 * @param result
	 */
	private void processNg(File statusFile, ConvertResult result) {
		try {
			//ステータスファイルをエラーフォルダへ移動する。
			FileResource statusResource = FileUtil.readFile(statusFile.getParentFile(),statusFile.getName());
			if (!FileUtil.writeFile(ERR_FOLDER, statusResource)) {
				log.warn("ステータスファイル'" + statusFile + "'をエラーフォルダへの移動に失敗しました。");
			}
		} catch (IOException e) {
			log.warn(
				"ステータスファイル'" + statusFile + "'のエラーフォルダへの移動に失敗しました。", e);
		}
		
		result.setRealData(new SystemException("ファイルの変換に失敗しました。変換エラーファイル'"	+ new File(ERR_FOLDER,statusFile.getName())+ "'"));
	}

	/**
	 * 正常に変換された場合の処理。
	 * 変換したファイルを取得し、処理結果オブジェクトにセットする。
	 * 
	 * @param inputFile
	 * @param result
	 */
	private void processOk(String inputFile, ConvertResult result) {
		try {
			result.setRealData(getResult(inputFile));
		} catch (IOException e) {
			result.setRealData(
				new SystemException(
					"変換正常終了。結果ファイル'" + inputFile + "'の取得に失敗しました 。 ",
					e));
		}
	}
	

	/**
	 * 変換したファイルを取得する。
	 * @param inputFile	入力ファイル名
	 * @return	変換後ファイルリソース
	 * @throws IOException	ファイルの取得中のIO例外
	 */
	private FileResource getResult(String inputFile) throws IOException {
		//結果ファイルを取得する。
		File resultFile = new File(OUT_FOLDER, inputFile + OUT_FILE_SUFFIX);
		FileResource resource =	FileUtil.readFile(resultFile);
		//出力ファイルを削除する。
		if (!FileUtil.delete(resultFile)) {
			log.warn("出力ファイルの削除に失敗しました。:" + resultFile);
		}
		return resource;
	}

	/**
	 * ステータスファイルが入力ファイルの処理結果ファイルであるかをチェックする。
	 * 処理結果をチェックする。
	 * @param statusFile	ステータスファイル
	 * @param inputFile		元ファイル
	 * @return				正常終了時 true 処理結果が取得できないとき false
	 * @throws ConvertException		処理結果ファイルがNGまたはERRのとき
	 */
	private String checkStatusFile(File statusFile, String inputFile) {
		
		//ステータスファイル形式のチェック 【～#OK.元ファイル名】	
		Matcher matcher = pattern.matcher(statusFile.getName());
		if (matcher.find()) {
			if (log.isDebugEnabled()) {
				log.debug("ステータス '" + matcher.group(STATUS_GROUPID) + "'");
				log.debug("ファイル名 '" + matcher.group(ORG_FILE_NAME_GROUPID) + "'");
			}
			//ステータスファイル名が元ファイル名であるかどうか。
			if (inputFile.equals(matcher.group(ORG_FILE_NAME_GROUPID))) {
				//元ファイル名を入力ファイル情報より削除する。
				converter.getFileInfo().remove(inputFile);
				//2005.01.24 iso 大文字小文字を区別しないよう設定
//				if (STATUS_OK.equals(matcher.group(STATUS_GROUPID))) {
				if (STATUS_OK.equals(matcher.group(STATUS_GROUPID).toLowerCase())) {
					//正常終了時
					return STATUS_OK;
				} else {
					//以外
					return STATUS_ERR;
				}
			}
		}
		return STATUS_NONE;
	}

	/** ステータスファイル判断用正規表現パターン。*/
	private Pattern pattern =
		Pattern.compile(
			"^~\\d+?.("
				+ STATUS_OK
				+ "|"
				+ STATUS_NG
				+ "|"
				+ STATUS_ERR
				+ ").(\\S*)$"
				,Pattern.CASE_INSENSITIVE);		//2005.01.24 iso 大文字小文字を区別しないよう設定
	
	/** ステータス情報グループ。*/
	private int STATUS_GROUPID = 1;
	/** ファイル名グループ。 */
	private int ORG_FILE_NAME_GROUPID = 2;
	/** 変換ステータスOK */
	private static final String STATUS_OK = "ok";
	/** 変換ステータスNG。 */
	private static final String STATUS_NG = "ng";
	/** 変換ステータスERR。 */
	private static final String STATUS_ERR = "err";
	/** 対象ファイル以外のとき。 */
	private static final String STATUS_NONE = "";

}
