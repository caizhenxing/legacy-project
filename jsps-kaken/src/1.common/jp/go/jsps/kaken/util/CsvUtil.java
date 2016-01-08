package jp.go.jsps.kaken.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.FileIOException;

/**
 * CSVファイルを生成するクラス。
 *
 * ID RCSfile="$RCSfile: CsvUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class CsvUtil {

	//---------------------------------------------------------------------
	// Static Methods
	//---------------------------------------------------------------------	
	/**
	 * Listの文字列配列要素をCSVファイルへ出力する。
	 *
	 * @param list CSVに出力するリスト
	 * @param filepath ファイル出力先パス
	 * @param filename ファイル名の接頭辞。
	 * @return 処理結果。trueの場合は成功。
     * @throws ApplicationException
	 */
	public static boolean output(List list, String filepath, String filename)
            throws ApplicationException {

		return outputCSV(list, filepath, filename);	
	}

	/**
	 * Listの文字列配列要素をCSVファイルへ出力する。
	 * 現在審査依頼通知書出力専用状態（ファイル名が固定のため）
	 * @param list CSVに出力するリスト
     * @param filepath ファイルパス
	 * @param filename ファイル名
	 * @return 処理結果。trueの場合は成功。
     * @throws FileIOException
	 */
	public static boolean outputCSV(List list, String filepath, String filename)
            throws FileIOException {

		ServletOutputStream so = null;
				
		try{	
			//出力フォルダの作成
			//TODO　暫定出力先
			File file = new File(filepath);

			//フォルダが無い場合は作成する(想定上は必ず作成する)
			if(!file.exists()){
				file.mkdirs();
			}
			String file_path = file.getPath();

			//ファイル出力
			PrintWriter writer = new PrintWriter(
                                 new BufferedWriter(
                                 new FileWriter(file_path + "/" + filename + ".csv")));
			
			//CSVデータを生成する
			for(int i = 0;i < list.size(); i++){
				List line = (List)list.get(i);
				Iterator iterator = line.iterator();
				String row ="";

				//1行分のCSVデータを作成する
				CSVLine csvline = new CSVLine();

				//1行分のデータ項目数くりかえす			
				while(iterator.hasNext()){
					String col = (String)iterator.next();
					csvline.addItem(col);
				}

				//1行分をファイルへ書き込み
				writer.println(csvline.getLine());
			}

			writer.close();

		}catch(IOException e){
			throw new FileIOException(
				"ファイルの入出力中にエラーが発生しました。", 
				e);
		}
					
		return true;
	}
}