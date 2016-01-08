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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.model.pdf.webdoc.WebdocUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 添付ファイル変換クラス。
 * ID RCSfile="$RCSfile: AutoConverter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:55 $"
 */
public class AutoConverter {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ログクラス。 
	 */
	private static final Log log = LogFactory.getLog(AutoConverter.class);

	/** 
	 * 受け渡しクラス。 
	 */
	private static final AutoConverter Converter;

	/**
	 * 初期化
	 */
	static {
		Converter = new AutoConverter();
		//ワーカースレッドの実行
		new FileWatcher(Converter).start();
	}

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * 変換対象ファイル情報を保持するマップ。
	 */
	private final Map fileInfo = Collections.synchronizedMap(new HashMap());

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 *	コンストラクタ。
	 */
	private AutoConverter() {
		super();
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * 添付ファイル変換クラスを取得する。
	 * @return	変換クラス
	 */
	public static AutoConverter getConverter() {
		return Converter;
	}

	/**
	 * 変換サービスを実行する。
	 * @param attachedResource		変換対象ファイル情報
	 * @return						変換対象結果オブジェクト
	 * @throws ConvertException	変換対象添付ファイルを指定INフォルダへのコピーするときに失敗したとき。
	 */
	public synchronized ConvertResult setFileResource(FileResource attachedResource)
		throws ConvertException {

		ConvertResult result = new ConvertResult(this);
		//変更するための一意の名前
		File attachedFile =
			new File(
				FileWatcher.getInFolder(),
				WebdocUtil.getTempName() + "_" + attachedResource.getName());
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			boolean bWrite =
				FileUtil.writeFile(
					attachedFile,
					attachedResource.getBinary());
			if (!bWrite) {
				throw new ConvertException("'" + attachedFile + "'変換対象ファイルを変換ディレクトリに書き込めませんでした。");
			}
		} catch (IOException e) {
			throw new ConvertException(
				"変換対象ファイルを変換ディレクトリにコピー時" + attachedFile + "に例外が発生しました。",e);
		}

		//変換対象にセットする。		
		putFileInfo(attachedFile, result);

		return result;
	}

	/**
	 * 変換対象ファイルを追加する。
	 * @param attachedResource	変換対象ファイル情報。
	 * @param result			変換結果オブジェクト。
	 */
	private synchronized void putFileInfo(
		File attachedFile,
		ConvertResult result) {
		if (fileInfo.isEmpty()) {
			notifyAll();
		}
		fileInfo.put(attachedFile.getName(), result);
		if (log.isDebugEnabled()) {
			log.debug("変換対象ファイルをセットしました。待機中ファイルリスト::" + fileInfo.keySet());
		}
	}

	/**
	 * 変換対象ファイル情報を取得する。
	 * @param attachedResource	変換対象ファイル情報。
	 * @param result			変換結果オブジェクト。
	 */
	public synchronized Map getFileInfo() {
		if (fileInfo.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				log.warn("変換対象ファイル情報待機中に割込が発生しました。");
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("変換対象ファイル情報を取得しました。待機中ファイルリスト::" + fileInfo.keySet());
		}
		return fileInfo;
	}

	/**
	 * タイムアウト時等、変換対象ファイル情報を削除する。
	 * @param result	変換対象結果オブジェクト
	 */
	public synchronized void removeFileInfo(ConvertResult result) {
		if (fileInfo.containsValue(result)) {
			log.debug("変換対象ファイルよりファイル情報を削除します。");
			for (Iterator iter = fileInfo.keySet().iterator();
				iter.hasNext();
				) {
				String attachedFileName = (String) iter.next();
				if (fileInfo.get(attachedFileName) == result) {
					fileInfo.remove(attachedFileName);
					log.debug(
						"変換対象ファイルよりファイル情報" + attachedFileName + "を削除しました。");
					break;
				}
			}
		}
	}
}
