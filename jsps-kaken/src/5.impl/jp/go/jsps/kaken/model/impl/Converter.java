/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jp.go.jsps.kaken.model.IConverter;
import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.model.pdf.autoConverter.AutoConverter;
import jp.go.jsps.kaken.model.pdf.autoConverter.ConvertResult;
import jp.go.jsps.kaken.model.pdf.webdoc.WebdocUtil;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * データの変換を実装するクラス。
 * 
 * ID RCSfile="$RCSfile: Converter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:47 $"
 */
public class Converter implements IConverter {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(Converter.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public Converter() {
		super();
	}

	//---------------------------------------------------------------------
	// implements IConverter
	//---------------------------------------------------------------------

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.IConverter#iodFileCreation(java.util.List)
	 */
	public FileResource iodFileCreation(List iodSettingInfo) throws ConvertException{
		
		//-------------------------------------
		// 処理状況判定フラグ
		//-------------------------------------
		boolean success = false;
		
		//-------------------------------------
		// ファイル作成。
		//-------------------------------------
		File outIodFile = WebdocUtil.iodFileCreation(iodSettingInfo);
		
		//-------------------------------------
		//作成したファイルを読み込む。
		//-------------------------------------
		try {
			//PDFファイルを取得する。
			FileResource iodFileResource = FileUtil.readFile(outIodFile);
			success = true;
			//PDFファイルのリターン
			return iodFileResource;
		} catch (IOException e) {
			throw new ConvertException(
				"作成ファイル'" + outIodFile + "'情報の取得に失敗しました。",
				new ErrorInfo("errors.8005"),
				e);
		}finally{
			if (success) {
				//-------------------------------------
				//作業ファイルの削除
				//-------------------------------------
				FileUtil.delete(outIodFile.getParentFile());
			}
		}
	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.IConverter#iodFileCreation(jp.go.jsps.kaken.util.FileResource)
	 */
	public synchronized FileResource iodFileCreation(FileResource attachedResource) throws ConvertException {

		//-------------------------------------
		// 添付ファイルをPDFにする。
		//-------------------------------------
		ConvertResult convertResult = AutoConverter.getConverter().setFileResource(attachedResource);
		
		//-------------------------------------
		// PDFに変換したデータを取得する。
		//-------------------------------------
		FileResource result = convertResult.getResult();
		//2005.07.14 iso 添付ファイルはPDFに変換するように変更
//		result.setPath(attachedResource.getName()+ WebdocUtil.IOD);
		result.setPath(attachedResource.getName()+ WebdocUtil.PDF);

		//-------------------------------------
		// 変換したデータをリターン
		//-------------------------------------
		return result;
	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.IConverter#iodToPdf(java.util.List, java.lang.String)
	 */
	public FileResource iodToPdf(List iodFileResources, String password,
									String jokyoId, 	String jigyoKbn)
	throws ConvertException {

		//-------------------------------------
		// 処理状況判定フラグ
		//-------------------------------------
		boolean success = false;

		//-------------------------------------
		// IODファイルをPDFにする。
		//-------------------------------------
		File outPdfFile = WebdocUtil.iodToPdf(iodFileResources, password);

        //2007/06/15 追加
		// 事業区分が4:基盤、且つ申請状況IDが02：未確認の場合、すかし文字を表示する
        if (!StringUtil.isBlank(jokyoId) && !StringUtil.isBlank(jigyoKbn)) {
    		if ("4".equals(jigyoKbn)) {
    			if (StatusCode.STATUS_SHINSEISHO_MIKAKUNIN.equals(jokyoId)) {
    				File pdfFile = WebdocUtil.addWaterMark(outPdfFile, password, "提出確認用");
    				// 背景追加成功
    				if(pdfFile != null){
    					outPdfFile = pdfFile;
    				}
    			}
    		}
        }
		// 2007/06/15 追加ここまで
		
		try {
			//-------------------------------------
			// PDFに変換したデータを取得する。
			//-------------------------------------
			FileResource result = FileUtil.readFile(outPdfFile);

			//-------------------------------------
			//ファイル名は先頭のものにする。		
			//-------------------------------------
			FileResource outPdfFileName = (FileResource) iodFileResources.get(0);
			result.setPath(outPdfFileName.getName());
			
			success = true;
		
			//-------------------------------------
			// 変換したデータをリターン
			//-------------------------------------
			return result;
		
		} catch (IOException e) {
			throw new ConvertException(
				"変換したpdfファイル'" + outPdfFile + "'の読込みに失敗しました。",
					new ErrorInfo("errors.8003"),
					e);
		}finally{
			if (success) {
				//-------------------------------------
				//作業ファイルの削除
				//-------------------------------------
				FileUtil.delete(outPdfFile.getParentFile());
			}
		}
	}

	//2005.07.15 iso PDF添付機能追加
	/* (非 Javadoc)
	 * @see jp.go.jsps.model.IConverter#checkPdf(java.lang.String)
	 */
	public int checkPdf(FileResource fileRes) throws ConvertException {
		return WebdocUtil.checkPdf(fileRes);
	}

}
