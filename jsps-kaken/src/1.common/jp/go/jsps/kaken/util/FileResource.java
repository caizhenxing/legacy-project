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

import java.io.File;

/**
 * ファイルの送受信で使用するリソースクラス。
 * 
 * ID RCSfile="$RCSfile: FileResource.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 * 
 */
public class FileResource implements java.io.Serializable {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------


	/** システムに依存するパス区切り文字 */
	public static final char pathSeparatorChar = File.pathSeparatorChar;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	private String path; // パス
	private byte[] binary; // バイナリ
	private long time; // 最新の変更された時刻

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 * FileResourceを作成する。
	 */
	public FileResource() {
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * パスを返す。
	 * @return パス
	 */
	public String getPath() {
		return path;
	}

	/**
	 * パスを設定する。
	 * @param path パス
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * この抽象パス名が示すファイルまたはディレクトリの名前を返す。
	 * @return ファイル名。
	 */
	public String getName() {
		return new File(path).getName();
	}

	/**
	 * バイナリを返す。
	 * @return バイナリ
	 */
	public byte[] getBinary() {
		return binary;
	}

	/**
	 * バイナリを設定する。
	 * @param binary バイナリ
	 */
	public void setBinary(byte[] binary) {
		this.binary = binary;
	}

	/**
	 * ファイルが最後に変更された時刻を返す。
	 * @return 最新の変更された時刻
	 */
	public long lastModified() {
		return time;
	}

	/**
	 *	ファイルが変更された時刻を設定する。
	 *	@param time 最新の変更された時刻
	 */
	public void setLastModified(long time) {
		this.time = time;
	}

}