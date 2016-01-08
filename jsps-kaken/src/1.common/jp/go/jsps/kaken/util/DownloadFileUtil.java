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

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;

import org.apache.commons.logging.*;

/**
 * ファイル操作を行うクラス。
 * 
 * ID RCSfile="$RCSfile: DownloadFileUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 * 
 */
public class DownloadFileUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 
	 * ログ
	 */
	private static Log log = LogFactory.getLog(DownloadFileUtil.class);
	
	/** コンテントタイプ（PDF） */
	public static final String CONTENT_TYPE_PDF      = "application/pdf";
		
	/** コンテントタイプ（PDF） */
	public static final String CONTENT_TYPE_MS_WORD  = "application/msword";
	
	/** 拡張子（PDF） */
	private static final String EXTENSION_PDF        = ".pdf";
	
	/** 拡張子（WORD） */
	private static final String EXTENSION_MS_WORD    = ".doc";
	
	/** 拡張子（CSV） */
	private static final String EXTENSION_CSV        = ".csv";	
	
	/** デリミタ */
	private static final String DELIM                = "_";
	
	/** 拡張子contentType対応テーブル */
	private final static String contentTypeTable[][] = {
															{"doc", "application/msword"},
															{"pdf", "application/pdf"},
															{"txt", "text/plain"},
															{"xls", "application/vnd.ms-excel"},
															{"csv", "application/csv"},
															{"zip", "application/zip"}
														};
	private final static int EXTENTION=0;
	private final static int CONTENT_TYPE=1;

	

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
	/**
	 * ファイルをダウンロードする。
	 * 指定されたファイルをローカルディスクより取得し、レスポンスに返す。
	 * contentTypeは拡張子を元に自動的に判断する。
	 * @param response
	 * @param file ダウンロードするファイル
	 * @throws FileIOException
	 * @return ファイルが正常にダウンロードされた場合はtrue、そうでない場合はfalse。
	 */
	public static boolean downloadFile(
			HttpServletResponse response,
			File file) 
		throws FileIOException
	{
		//拡張子からcontentTypeを獲得
		String contentType = getContentType(file.getName());
		return downloadFile(response, file, contentType);
	}
	
	
	
	/**
	 * ファイルをダウンロードする。
	 * 指定されたファイルをローカルディスクより取得し、レスポンスに返す。
	 * @param response
	 * @param file ダウンロードするファイル
	 * @param contentType コンテントタイプ
	 * @throws FileIOException
	 * @return ファイルが正常にダウンロードされた場合はtrue、そうでない場合はfalse。
	 */
	public static boolean downloadFile(
			HttpServletResponse response,
			File file,
			String contentType) 
		throws FileIOException
	{
		//ファイルが存在していない場合
		if(file == null || !file.exists()){
			return false;
		}
		FileResource fileRes = null;
		try{
			fileRes = FileUtil.readFile(file);
		}catch(IOException e){
			throw new FileIOException(
				"ファイルの入力中にエラーが発生しました。",
				e);
		}
		return downloadFile(response, fileRes, contentType);
	}
	
	
	
	/**
	 * ファイルをダウンロードする。
	 * 指定されたファイルリソースをレスポンスに返す。
	 * contentTypeは拡張子を元に自動的に判断する。
	 * @param response
	 * @param fileRes ダウンロードするファイルリソース
	 * @throws FileIOException 
	 * @return ファイルが正常にダウンロードされた場合はtrue、そうでない場合はfalse。
	 */
	public static boolean downloadFile(
			HttpServletResponse response, 
			FileResource fileRes) 
		throws FileIOException
	{
		//拡張子からcontentTypeを獲得
		String contentType = getContentType(fileRes.getName());
		return downloadFile(response, fileRes, contentType);		
	}
	
	
	
	/**
	 * ファイルをダウンロードする。
	 * 指定されたファイルリソースをレスポンスに返す。
	 * @param response
	 * @param fileRes ダウンロードするファイルリソース
	 * @param contentType コンテントタイプ
	 * @throws FileIOException 
	 * @return ファイルが正常にダウンロードされた場合はtrue、そうでない場合はfalse。
	 */
	public static boolean downloadFile(
			HttpServletResponse response, 
			FileResource fileRes, 
			String contentType) 
		throws FileIOException
	{
		ServletOutputStream so = null;
		InputStream input = null;
		
		//ファイルリソースまたはファイルリソースにセットされているファイル名が存在していない場合
		if(fileRes == null || fileRes.getPath() == null){
			return false;
		}
		
		try{	
			//コンテキストにダウンロードファイル情報を設定する
			response.setContentType(contentType);
			String filename = fileRes.getPath();
			
			//ファイル名に全角文字が含まれていた場合は、デフォルトのファイル名を使用。
			if(!StringUtil.isHankaku(filename)){				
				filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "." + FileUtil.getExtention(filename); 
			}
			response.setContentLength(fileRes.getBinary().length);
			response.setHeader("Content-disposition", "attachment;filename=" + filename);

			//出力用のストリームを生成する
			try{
				so = response.getOutputStream();
			}catch(IOException e){
				throw new FileIOException(
					"ファイル出力用のストリームの生成に失敗しました。",
					new ErrorInfo("errors.3000"),
					e);
			}
			
			//ファイルを入力しながら出力ストリームに流す
			try{
				input = new BufferedInputStream(new ByteArrayInputStream(fileRes.getBinary()));				
				int i=0;
				while ((i = input.read()) != -1) {
					so.write(i);
				}		
			}catch(IOException e){
				throw new FileIOException(
					"ファイルの入出力中にエラーが発生しました。",
					e);
			}
			
		}finally{
			if(input != null){
				try{
					input.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if(so != null){
				try{
					so.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}				
		return true;
	}

	/**
	  * 拡張子からcontentTypeを取り出す。
	  * @param fileName ファイル名
	  * @return contentType
	  */
	public static String getContentType(String fileName) {
		String extention = FileUtil.getExtention(fileName);
		for (int j=0; j < contentTypeTable.length; j++) {
			if (contentTypeTable[j][EXTENTION].equalsIgnoreCase(extention)) {
			return contentTypeTable[j][CONTENT_TYPE];
			}
		}
		return "application/octet-stream";
	}

    /**
     * Listの文字列配列要素をCSVファイルへ出力する。
     * 「"」 か「 ,」 を含んでいる場合にエンクォートする。
     * ファイル名は、「filename_現在日付.csv」とする。
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param list CSVに出力するリスト
     * @param filename ファイル名
     * @return 処理結果。trueの場合は成功。
     * @throws FileIOException
     */
    public static boolean downloadCSV(
            HttpServletRequest request,
            HttpServletResponse response,
            List list, String filename)
            throws FileIOException {

        return downloadCSV(request, response, list, filename, true, true);
    }

	/**
	 * Listの文字列配列要素をCSVファイルへ出力する。
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param list CSVに出力するリスト
	 * @param filename ファイル名
	 * @param suffixFlg 接尾語フラグ（「filename_現在日付.csv」とする場合は、trueとする）
	 * @param enquoteFlg 「"」 か「 ,」 を含んでいる場合にエンクォートする場合はtrue、エンクォートしない場合はfalseとする
	 * @return 処理結果。trueの場合は成功。
     * @throws FileIOException
	 */
	public static boolean downloadCSV(
            HttpServletRequest request,
            HttpServletResponse response,
            List list,
            String filename,
            boolean suffixFlg,
            boolean enquoteFlg)
            throws FileIOException {
		
		//ServletOutputStream so = null;
		PrintWriter pr = null;
				
		try{	
			//コンテキストにダウンロードファイル情報を設定する
			response.setContentType("application/csv;charset=WINDOWS-31J");
			if(suffixFlg){
				response.setHeader("Content-disposition", "attachment;filename=" + getTempFileName(filename, true));
			}else{
				response.setHeader("Content-disposition", "attachment;filename=" + getTempFileName(filename, false));				
			}
			
			//出力用のストリームを生成する
			try{
				//so = response.getOutputStream();
				pr = response.getWriter();
			}catch(IOException e){
				throw new FileIOException(
						"ファイル出力用のストリームの生成に失敗しました。", 
						e);
			}
			
			//CSVデータを生成する
			for(int i = 0;i < list.size(); i++){
				List line = (List)list.get(i);
				Iterator iterator = line.iterator();
				//1行分のデータ項目数くりかえす
				if(enquoteFlg){
					//エンクォートする場合
					//1行分のCSVデータを作成する
					CSVLine csvline = new CSVLine();
					while(iterator.hasNext()){
						String col = (String)iterator.next();
						csvline.addItem(col);
					}
					
					//2005/08/11 takano PrintWriterへ変更
					//try{
					//	//1行分をファイルへ書き込み
					//	so.println(csvline.getLine());
					//}catch(IOException e){
					//	throw new FileIOException(
					//		"ファイルの入出力中にエラーが発生しました。", 
					//		e);
					//}
					
					//1行分をファイルへ書き込み
					pr.println(csvline.getLine());
					
				}else{
					//エンクォートしない場合
					StringBuffer buffer = new StringBuffer();
					int count = 0;
					while(iterator.hasNext()){
						String col = (String)iterator.next();
						buffer.append(col);
						count++;
						if(line.size() != count){
							buffer.append(",");
						}			
					}
					
					//2005/08/11 takano PrintWriterへ変更
					//try{
					//	//1行分をファイルへ書き込み
					//	so.println(buffer.toString());
					//}catch(IOException e){
					//	throw new FileIOException(
					//		"ファイルの入出力中にエラーが発生しました。", 
					//		e);
					//}
					
					//1行分をファイルへ書き込み
					pr.println(buffer.toString());
					
				}
			}
		}finally{
			//2005/08/11 takano PrintWriterへ変更
			//if (so != null) {
			//	try{
			//		so.close();
			//	}catch(IOException e){
			//		e.printStackTrace();
			//	}
			//}
			
			//リソースクローズ
			if(pr != null){
				pr.close();
			}
			
		}
		return true;
	}

	/**
	 * テンポラリーファイル名を取得する。
	 * @param filename ファイル名
	 * @param suffixFlg 接尾語フラグ（「filename_現在日付.csv」とする場合は、true）
	 * @return テンポラリーファイル名
	 */
	private static synchronized String getTempFileName(String filename, boolean suffixFlg) {
		StringBuffer sb = new StringBuffer(filename);
		if(suffixFlg){
			sb.append(DELIM);
//2006/11/06 苗　修正ここから            
//			sb.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            sb.append(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
//2006/11/06　苗　修正ここまで            
		}
		sb.append(EXTENSION_CSV);
		return sb.toString();
	}
}