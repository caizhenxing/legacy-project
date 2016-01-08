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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ファイル操作を行うクラス。
 * 
 * ID RCSfile="$RCSfile: FileUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 * 
 */
public class FileUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 
	 * ログ
	 */
	private static Log log = LogFactory.getLog(FileUtil.class);
	
	/** コンテントタイプ（PDF） */
//	public static String CONTENT_TYPE_PDF = "application/pdf;charset=WINDOWS-31J";
	/** コンテントタイプ（PDF） */
	public static String CONTENT_TYPE_PDF = "application/pdf";
		
	/** コンテントタイプ（PDF） */
	public static String CONTENT_TYPE_MS_WORD = "application/ms-word";
	
	/** 拡張子（PDF） */
	public static String EXTENSION_PDF = "pdf";
	
	/** 拡張子（WORD） */
	public static String EXTENSION_MS_WORD = "doc";
	
	/** デリミタ */
	public static String DELIM = ".";	

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 * FileUtilを作成する。
	 */
	public FileUtil() {
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
	 * 親ディレクトリに格納されているファイルの一覧を返す。
	 * 子にディレクトリが存在する場合、その子のファイルも取得する。
	 * @param dir	ディレクトリ
	 * @return 子のファイル名（パス名）
	 */
	public static String[] getFileList(File parent) {
		if (parent == null) {
			return null;
		}
		String[] list = parent.list();
		if (list == null) {
			return null;
		}
		File file;
		Vector vec = new Vector();
		String[] grandchild;
		for (int i = 0; i < list.length; i++) {
			file = new File(parent, list[i]);
			if (file.isDirectory()) {
				grandchild = getFileList(file);
				if (grandchild == null || grandchild.length == 0) {
					continue;
				}
				for (int j = 0; j < grandchild.length; j++) {
					vec.addElement(
						(new File(list[i], grandchild[j])).getPath());
				}
			} else {
				vec.addElement(list[i]);
			}
		}
		// ベクターからファイルリストの作成
		list = new String[vec.size()];
		for (int i = 0; i < vec.size(); i++) {
			list[i] = (String) vec.get(i);
		}
		return list;
	}

	/**
	 * ファイル読み込みファイルリソースを返す。
	 * filePathの引数はFileResourceのパスに設定されます。
	 * @param file	読み込むファイル
	 * @return ファイルリソース
	 * @throws FileNotFoundException ファイルが存在しないか、普通のファイルではなくディレクトリであるか、またはなんらかの理由で開くことができない場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public static FileResource readFile(File file)
		throws FileNotFoundException, IOException {
		return readFile(file.getParentFile(), file.getName());
	}

	/**
	 * ファイル読み込みファイルリソースを返す。
	 * filePathの引数はFileResourceのパスに設定されます。
	 * @param parent	読み込むファイルの親ディレクトリ
	 * @param filePath	読み込むファイル名（または親からの相対パス）
	 * @return ファイルリソース
	 * @throws FileNotFoundException ファイルが存在しないか、普通のファイルではなくディレクトリであるか、またはなんらかの理由で開くことができない場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public static FileResource readFile(File parent, String filePath)
		throws FileNotFoundException, IOException {
		File file = new File(parent, filePath);
		FileInputStream fis = new FileInputStream(file);
		byte[] binary = new byte[fis.available()];
		fis.read(binary);
		fis.close();
		FileResource fileRes = new FileResource();
		fileRes.setPath(filePath);
		fileRes.setBinary(binary);
		fileRes.setLastModified(file.lastModified());
		return fileRes;
	}

	/**
	 * ファイル読み込みファイルリソースの配列を返す。
	 * filePathの引数はFileResourceのパスに設定する。
	 * @param parent	読み込むファイルの親ディレクトリ
	 * @return ファイルリソースの配列
	 * @throws FileNotFoundException ファイルが存在しないか、普通のファイルではなくディレクトリであるか、またはなんらかの理由で開くことができない場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public static FileResource[] readFiles(File parent)
		throws FileNotFoundException, IOException {
		String[] list = getFileList(parent);
		FileResource[] fileRes = new FileResource[list.length];
		for (int i = 0; i < list.length; i++) {
			fileRes[i] = FileUtil.readFile(parent, list[i]);
		}
		return fileRes;
	}

	/**
	 * ファイルにバイトの配列を書き込む。
	 * @param parent 書き込む子のfileResの親ディレクトリ
	 * @param fileRes 書き込むリソース
	 * @return 正常に書き込まれた場合は true、そうでない場合は false
	 * @throws FileNotFoundException ファイルは存在するが、普通のファイルではなくディレクトリである場合、ファイルは存在せず作成もできない場合、またはなんらかの理由で開くことができない場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public static boolean writeFile(File parent, FileResource[] fileRes)
		throws FileNotFoundException, IOException {
		String path;
		File file;
		if (fileRes == null) {
			return false;
		}
		for (int i = 0; i < fileRes.length; i++) {
			path = fileRes[i].getPath();
			if (path == null || path.length() == 0) {
				continue;
			}
			file = new File(parent, path);
			writeFile(file, fileRes[i].getBinary());
			if (fileRes[i].lastModified() > 0) {
				file.setLastModified(fileRes[i].lastModified());
			}
		}
		return true;
	}

	/**
	 * ファイルにバイトの配列を書き込む。
	 * @param parent 書き込む子のfileResの親ディレクトリ
	 * @param fileRes 書き込むリソース
	 * @return 正常に書き込まれた場合は true、そうでない場合は false
	 * @throws FileNotFoundException ファイルは存在するが、普通のファイルではなくディレクトリである場合、ファイルは存在せず作成もできない場合、またはなんらかの理由で開くことができない場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public static boolean writeFile(File parent, FileResource fileRes)
		throws FileNotFoundException, IOException {
		String path;
		File file;
		if (fileRes == null) {
			return false;
		}
		path = fileRes.getPath();
		if (path == null || path.length() == 0) {
			return false;
		}
		file = new File(parent, path);
		writeFile(file, fileRes.getBinary());
		if (fileRes.lastModified() > 0) {
			file.setLastModified(fileRes.lastModified());
		}
		return true;
	}

	/**
	 * ファイルにバイトの配列を書き込む。
	 * @param file ファイル
	 * @param binary バイトの配列
	 * @return 正常に書き込まれた場合は true、そうでない場合は false
	 * @throws FileNotFoundException ファイルは存在するが、普通のファイルではなくディレクトリである場合、ファイルは存在せず作成もできない場合、またはなんらかの理由で開くことができない場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public static boolean writeFile(File file, byte[] binary)
		throws FileNotFoundException, IOException {

		File parent = file.getParentFile();
		if (parent != null) {
			parent.mkdirs();
		}
		if (binary == null) {
			return false;
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(binary);
		fos.close();
		return true;
	}

	/**
	 * ファイルまたはディレクトリを削除する。
	 * fileがディレクトリの場合そのディレクトリ内のすべての
	 * ファイルとディレクトリを削除する。
	 * 削除に失敗した場合でも、いくつかのファイル（またはディレクトリ）は
	 * 削除される場合があります。
	 * @param file ファイルorディレクトリ
	 * @return ファイルまたはディレクトリが正常に削除された場合は true、そうでない場合は false
	 */
	public static boolean delete(File file) {
		if (file == null) {
			return false;
		}
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			if (list != null && list.length > 0) {
				for (int i = 0; i < list.length; i++) {
					delete(list[i]);
				}
			}
		}
		return file.delete();
	}

	/**
	 * ディレクトリをコピーする。
	 * @param fromDir コピー元
	 * @param toDir コピー先
	 * @return ディレクトリが正常にコピーされた場合は true、そうでない場合は false
	 */
	public static boolean directoryCopy(File fromDir, File toDir) {
		delete(toDir);
		toDir.mkdirs();
		String[] list = getFileList(fromDir);
		if (list == null) {
			return false;
		}
		FileResource res;
		for (int i = 0; i < list.length; i++) {
			try {
				res = readFile(fromDir, list[i]);
				writeFile(new File(toDir, list[i]), res.getBinary());
			} catch (Exception e) {
				if(log.isInfoEnabled()){
					log.info("ディレクトリコピー　fromDir='" + fromDir + "' toDir='" + toDir + "に失敗しました。", e);
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * ファイルをコピーする。
	 * @param fromFile コピー元ファイル
	 * @param toFile コピー先ファイル
	 * @return ファイルが正常にコピーされた場合はtrue、そうでない場合はfalse。
	 */
	public static boolean fileCopy(File fromFile, File toFile) {
		try {
			FileResource fileRes =
				readFile((File) null, fromFile.getAbsolutePath());
			writeFile(toFile, fileRes.getBinary());
			if (fileRes.lastModified() > 0) {
				toFile.setLastModified(fileRes.lastModified());
			}
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				log.info("ファイルコピー　fromFile='" + fromFile + "' toFile='" + toFile + "に失敗しました。", e);
			}
			return false;
		}
		return true;
	}

	/**
	 * ファイルを自己解凍形式で圧縮する。
	 * @param fromFile 圧縮元ファイルパス
	 * @param toFile 圧縮先ファイルパス
	 * @param filename 圧縮ファイル名（拡張子無し）
	 * @return ファイルが正常に圧縮された場合はtrue、そうでない場合はfalse。
	 */
	public static boolean fileCompress(String fromFilepath, String toFilepath, String filename) {
		try{
			//TODO　プログラムのパスはどうしましょう？
			//指定ファイルをlzhに圧縮するコマンド
			String[] command = {"Lha32",										//実行プラグラム
								"u",											//圧縮命令
								"-a1",											//オプション
								"-n1",
								"-o2",
								"-jyo1",
				                "\"" + toFilepath + filename + ".lzh\"",		//圧縮ファイル出力先
								"\"\"",											//基準ディレクトリ
								"\"" + fromFilepath + "*\""};					//圧縮対象ファイル（フォルダ）

			//指定lzhを自己解凍に変換するコマンド
			String[] command2 = {"Lha32",
								 "s",
								 "-gw2",
								 "-n1",
								 "-jyo1",
								 "\"" + toFilepath + filename + "\"",			//圧縮ファイルのパスおよびファイル名（拡張子は除く）
								 "\"" + toFilepath + "\""};						//自己解凍ファイルの出力先（ファイル名はlzhファイルと同じで.EXEとなる）

			try{
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();

				p = null;

				p = Runtime.getRuntime().exec(command2);
				p.waitFor();

			}catch(IOException e){
				if(log.isInfoEnabled()){
					log.info("ファイル圧縮　fromFilepath='" + fromFilepath + "' toFilepath='" + toFilepath + "' filename='" + filename + "'　に失敗しました。", e);
				}
				return false;
			}

		}catch(InterruptedException e){
			if(log.isInfoEnabled()){
				log.info("ファイル圧縮　fromFilepath='" + fromFilepath + "' toFilepath='" + toFilepath + "' filename='" + filename + "'　に失敗しました。", e);
			}
			return false;
		}
		return true;

	}

	/**
	 * 指定されたコマンドを実行するを行う。
	 * @param command 実行するコマンド
	 * @return 正常にコマンドが実行された場合はtrue、そうでない場合はfalse。
	 */
	public static boolean execCommand(String command) {
		try{

			try{
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();

			}catch(IOException e){
				if(log.isInfoEnabled()){
					log.info("実行コマンド　command='" + command + "'　が失敗しました。", e);
				}
				return false;
			}

		}catch(InterruptedException e){
			if(log.isInfoEnabled()){
				log.info("実行コマンド　command='" + command + "'　が失敗しました。", e);
			}
			return false;
		}
		return true;

	}
	
	/**
	  * ファイル名から拡張子を取り出す。
	  * @param filename ファイル名
	  * @return 拡張子
	  */
	public static String getExtention(String fileName) {
		int idx = fileName.lastIndexOf('.');
		if (idx!=-1) return (fileName.substring(idx+1, fileName.length())).toLowerCase();
		return "";
	}

	/**
	  * ファイルパスからファイル名を取り出す。パスの区切り文字の'\'を'/'に置換する。
	  * @param filepath ファイルパス
	  * @return ファイル名
	  */
	public static String getFileName(String filepath) {
		String filename = null;
		if(filepath != null && filepath.length() != 0){
			//'\'を'/'に置換
			filepath = filepath.replace('\\', '/');
			filename = new File(filepath).getName();
		}
		return filename;
	}

}
