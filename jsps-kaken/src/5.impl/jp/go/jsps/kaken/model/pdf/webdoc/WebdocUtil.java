/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/25
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.pdf.webdoc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yss.iothe.iowebdoc.iodoc;
import yss.iothe.iowebdoc.iodtopdf;
import yss.iothe.iowebdoc.webdocmem;
import yss.pdfmakeup.pdfcombine;
import yss.pdfmakeup.pmudst;
import yss.pdfmakeup.pmuobjwatermark;
import yss.pdfmakeup.pmusrc;

/**
 * IOWEBDOCのAPIを呼び出すためのユーティリティクラス。
 * 
 * ID RCSfile="$RCSfile: WebdocUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:58 $"
 */
public class WebdocUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ログクラス。 
	 */
	private static final Log log = LogFactory.getLog(WebdocUtil.class);

	/** ログ（PDF MakeUp）*/
	protected static Log pmLog = LogFactory.getLog("pdfmakeup");
	
	//---------------------------------------------------------------------
	// static data
	//---------------------------------------------------------------------

	/** 
	 * ユーザ用作業フォルダ親ディレクトリ。
	 */
	private static File WORK_PARENT_FOLDER;

	/** 
	 * 出力先PDFファイル名。
	 */
	public static final String PDF = ".pdf";

	/** 
	 * 出力先PDFファイル名。
	 */
	public static final String IOD = ".iod";

	/** 
	 * PDF変換中のログファイル名。
	 */
	public static final String LOG = ".log";

	//---------------------------------------------------------------------
	// static initializer  
	//---------------------------------------------------------------------

	static {
		WORK_PARENT_FOLDER =
			ApplicationSettings.getFile(ISettingKeys.PDF_WORK_FOLDER);
		WORK_PARENT_FOLDER.mkdirs();
	}

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public WebdocUtil() {
		super();
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * 設定情報よりpdfファイルを作成する。
	 * @param 	iodSettingInfo			設定情報
	 * @return	pdfファイル				作成したPDFファイル
	 */
	public static File iodFileCreation(List iodSettingInfo) {

		//-------------------------------------
		// パラメータチェック
		//-------------------------------------
		if (iodSettingInfo == null || iodSettingInfo.isEmpty())
			throw new IllegalArgumentException(
				//2005.07.14 iso PDF変換に変更
//				WebdocUtil.class +"::iod帳票作成設定情報が無効です。");
				WebdocUtil.class +"::pdf帳票作成設定情報が無効です。");

		//-------------------------------------
		// 変数初期化
		//-------------------------------------
		//1頁めの設定情報を取得
		PageInfo pageInfo = (PageInfo) iodSettingInfo.get(0);
		String tempName = getTempName();
		File workFolder = getWorkFolder(tempName);

		//2005.06.14 iso PDF変換に変更
//		File outIodFile = new File(workFolder, tempName + IOD);
		File outIodFile = new File(workFolder, tempName + PDF);
		File outIodLogFile = new File(workFolder, outIodFile.getName() + LOG);

		//-------------------------------------
		// 作成
		//-------------------------------------
		/* IOWEBDOC用ログファイル。	 */
		final String IoDocLogFilePath = outIodLogFile.getAbsolutePath();

		/* ①インスタンス作成 */
		webdocmem webdocmem = new webdocmem();

		try {
			/* ログファイル名を指定します。 */
			if (webdocmem.setlog(IoDocLogFilePath) < 0) {
				log.error(
					"webdocmem#setlog ログファイルの設定に失敗しました。エラーメッセージ→"
						+ webdocmem.getmess());
				throw new SystemException(webdocmem.getmess());
			}

			//デバッグ情報出力
			if (log.isDebugEnabled()) {
				log.debug("webdocmem info :: ログファイル→" + webdocmem.getlog());
			}

			/* ②圧縮の種類を指定します */
			if (webdocmem.setcompress(iodoc.WEBDOC_COMPRESS_DEFAULT) < 0) {
				log.error(
					"webdocmem#setcompress 圧縮種類の設定に失敗しました。エラーメッセージ→"
						+ webdocmem.getmess());
				throw new SystemException(webdocmem.getmess());
			}

			/* ③IODをロードする */
			if (webdocmem.loadiod(pageInfo.getTemplateFile().getAbsolutePath())
				< 0) {
				log.error(
					"webdocmem#loadiod レイアウト(.iod)ファイルのロードに失敗しました。エラーメッセージ→"
						+ webdocmem.getmess());
				throw new SystemException(webdocmem.getmess());
			}

			//デバッグ情報出力
			if (log.isDebugEnabled()) {
				log.debug(
					"webdocmem info :: iodファイル→" + webdocmem.getloadiod());
			}

			/* ④出力するIODのファイル名を設定 */
			//2005.07.14 iso PDF変換に変更
//			if (webdocmem.setoutiod(outIodFile.getAbsolutePath()) < 0) {
//				log.error(
//					"webdocmem#setoutiod 出力(.iod)ファイルの指定に失敗しました。エラーメッセージ→"
//						+ webdocmem.getmess());
//				throw new SystemException(webdocmem.getmess());
//			}
//
//			//デバッグ情報出力
//			if (log.isDebugEnabled()) {
//				log.debug(
//					"webdocmem info :: 出力(.iod)ファイル→ " + webdocmem.getoutiod());
//			}
			if (webdocmem.setoutpdf(outIodFile.getAbsolutePath()) < 0) {
				log.error(
					"webdocmem#setoutiod 出力(.pdf)ファイルの指定に失敗しました。エラーメッセージ→"
						+ webdocmem.getmess());
				throw new SystemException(webdocmem.getmess());
			}
			//デバッグ情報出力
			if (log.isDebugEnabled()) {
				log.debug(
					"webdocmem info :: 出力(.pdf)ファイル→ " + webdocmem.getoutpdf());
			}

			//-------------------------------------
			// ロードされているIODにデータを設定する。
			//-------------------------------------
			for (Iterator iter = iodSettingInfo.iterator(); iter.hasNext();) {
				PageInfo element = (PageInfo) iter.next();
				if (element != pageInfo) {
					webdocmem.setiddata(
						"iod:",
						element.getTemplateFile().getAbsolutePath());
				}

				/* ⑥１頁分のデータを設定する */
				for (Iterator iterator = element.getFields().iterator();
					iterator.hasNext();
					) {
					FieldInfo field = (FieldInfo) iterator.next();
					String id = field.getName();
					String data = field.getValue();
					if (webdocmem.setiddata(id, data) < 0) {
						log.error(
							"webdocmem#setiddata データの指定に失敗しました。エラーメッセージ→"
								+ webdocmem.getmess());
						throw new SystemException(webdocmem.getmess());
					}
				}

				/* ⑤改ページ */
				if (webdocmem.outpage() < 0) {
					log.error(
						"webdocmem#outpage 改ページに失敗しました。エラーメッセージ→"
							+ webdocmem.getmess());
					throw new SystemException(webdocmem.getmess());
				}

			}

			/* データ設定の終了(iod のクローズ)  */
			if (webdocmem.outend() < 0) {
				log.error(
					"webdoc#outend IODのファイルのクローズに失敗しました。エラーメッセージ→"
						+ webdocmem.getmess());
				throw new SystemException(webdocmem.getmess());
			}

			if (log.isDebugEnabled()) {
				log.debug(outIodFile + "を作成しました。");
			}
		} finally {
			/* インスタンスの開放 */
			webdocmem.release();
		}

		return outIodFile;
	}

	/**
	 * iodファイルリソースOR pdfファイルリソースを結合し、パスワード付PDFリソースを取得する。
	 * @param iodFileResources			結合するファイルリソース。
	 * @param password					PDFを開くためのパスワード。	
	 * @return							結合したPDFファイルリソース
	 * @throws ConvertException			ファイルの保存に失敗したときなど。
	 */
	public static File iodToPdf(List iodFileResources, String password)
		throws ConvertException {

		//-------------------------------------
		// パラメータチェック
		//-------------------------------------
		if (iodFileResources == null || iodFileResources.isEmpty()) {
			throw new IllegalArgumentException("PDF変換するiodファイルが指定されていません。");
		}

		//-------------------------------------
		//変数初期化
		//-------------------------------------
		String tempName = getTempName();

		//作業フォルダの作成
		File workFolder = getWorkFolder(tempName);
		File outPdfFile = new File(workFolder, tempName + PDF);
		File logPdfFile = new File(workFolder, outPdfFile.getName() + LOG);

		//結合するファイル達
		File[] iodFiles = new File[iodFileResources.size()];

		//2005.07.14 iso PDF添付機能の実装により、PDF結合とIOD→PDF作成の2種類を作成。
		//旧データがIODで残っているため、IOD→PDF作成機能も残す。
//		//iodファイルを書き込む
//		int i = 0;
//		for (Iterator iter = iodFileResources.iterator(); iter.hasNext();) {
//			FileResource element = (FileResource) iter.next();
//			iodFiles[i] =
//				new File(workFolder, getTempName() + "-" + element.getName());
//			try {
//				if (!FileUtil.writeFile(iodFiles[i], element.getBinary())) {
//					throw new ConvertException(
//						"PDF変換するiodファイル'" + element.getPath() + "'の書込み失敗しました。",new ErrorInfo("errors.8003"));
//				}
//			} catch (IOException e) {
//				throw new ConvertException(
//					"PDF変換するiodファイル'" + element.getPath() + "'の書込み失敗しました。",new ErrorInfo("errors.8003"), e);
//			}
//			//データの開放。
//			element.setBinary(null);
//			i++;
//		}
//
//		//変換	
//		iodToPdf(iodFiles, outPdfFile, logPdfFile, password);

		//iodファイルを書き込む
		int i = 0;
		String fileType = PDF;
		for (Iterator iter = iodFileResources.iterator(); iter.hasNext();) {
			FileResource element = (FileResource) iter.next();
			//添付ファイルがIODに変換してある場合は、IOD→PDF変換を行う。
			if(element.getName() != null && element.getName().endsWith(IOD)) {
				fileType = IOD;
			}
			iodFiles[i] =
				new File(workFolder, getTempName() + "-" + element.getName());
			try {
				if (!FileUtil.writeFile(iodFiles[i], element.getBinary())) {
					throw new ConvertException(
						"PDF変換するiodファイル'" + element.getPath() + "'の書込み失敗しました。",new ErrorInfo("errors.8003"));
				}
			} catch (IOException e) {
				throw new ConvertException(
					"PDF変換するiodファイル'" + element.getPath() + "'の書込み失敗しました。",new ErrorInfo("errors.8003"), e);
			}
			//データの開放。
			element.setBinary(null);
			i++;
		}

		//変換
		if(fileType.equals(PDF)) {
			combPdf(iodFiles, outPdfFile, password);
		} else {
			iodToPdf(iodFiles, outPdfFile, logPdfFile, password);
		}

		//作成したpdfを取得
		return outPdfFile;

	}

	/**
	 * 作業フォルダを取得する。
	 * @param tempName	
	 * @return			作業フォルダ
	 */
	private static File getWorkFolder(String tempName) {
		File workFolder =
			new File(
				WORK_PARENT_FOLDER
					+ File.separator
					+ tempName
					+ File.separator);
		workFolder.mkdirs();
		return workFolder;
	}

	/**
	 * IOCSVDOC・IOCSVCELAで作成した複数のＩＯＤファイルをまとめてＰＤＦファイルに変換する。
	 * @param iodFiles					結合するファイル。
	 * @param outPdfFile				出力PDFファイル
	 * @param outPdfLogFile				出力PDFログファイル
	 * @param password					パスワード
	 */
	private static void iodToPdf(
		File[] iodFiles,
		File outPdfFile,
		File outPdfLogFile,
		String password) {

		/* IOWEBDOC用ログファイル。	 */
		final String IoDocLogFilePath = outPdfLogFile.getAbsolutePath();

		iodtopdf iodtopdf = new iodtopdf();

		/* ログファイル設定 */
		iodtopdf.setlog(IoDocLogFilePath);

		/* ＰＤＦ出力時の圧縮方法を設定 */
		iodtopdf.setcompress(iodoc.IODTOPDF_COMPRESS_DEFAULT); /*デフォルト圧縮*/

		//2006.12.13 iso パスワードなしPDFはセキュリティ情報をセットしないよう変更
//		/* セキュリティ情報を設定 */
//		//印刷・編集・転載・注釈追加
//		if (iodtopdf
//		.setpdfsecurity(password, password,
//			 false	//印刷不可
//			,true	//編集不可
//		  	,true	//転載不可
//		    ,false)	//注釈不可
//			< 0) {
//			if (log.isErrorEnabled()) {
//				log.error(
//					"iodtopdf#setpdfsecurity セキュリティ情報の設定に失敗しました。エラーメッセージ→"
//						+ iodtopdf.getmess());
//			}
//			throw new SystemException(iodtopdf.getmess());
//		}
		if(!StringUtil.isBlank(password)) {
			/* セキュリティ情報を設定 */
			//印刷・編集・転載・注釈追加
			if (iodtopdf.setpdfsecurity(password, password,
				 false	//印刷不可
				,true	//編集不可
			  	,true	//転載不可
			    ,false)	//注釈不可
				< 0) {
				if (log.isErrorEnabled()) {
					log.error(
						"iodtopdf#setpdfsecurity セキュリティ情報の設定に失敗しました。エラーメッセージ→"
							+ iodtopdf.getmess());
				}
				throw new SystemException(iodtopdf.getmess());
			}
		}
		
		/* 文書情報を設定 */
		if (iodtopdf.setpdfdocinf("申請書", "", "JSPS", "JSPS電子申請システム")
			< 0) {
			if (log.isErrorEnabled()) {
				log.error(
					"iodtopdf#setpdfdocinf ＰＤＦの文書情報の設定に失敗しました。エラーメッセージ→"
						+ iodtopdf.getmess());
			}
			throw new SystemException(iodtopdf.getmess());
		}

		/* ＰＤＦファイルを開く */
		int status = iodtopdf.pdfopen(outPdfFile.getAbsolutePath());
		try {
			if (status < 0) {
				if (log.isErrorEnabled()) {
					log.error(
						"iodtopdf#pdfopen 出力用にＰＤＦをオープンに失敗しました。エラーメッセージ→"
							+ iodtopdf.getmess()
							+ " logファイル→"
							+ IoDocLogFilePath);
				}
				throw new SystemException(iodtopdf.getmess());
			}

			for (int i = 0; i < iodFiles.length; i++) {
				/* 指定したＩＯＤを変換して、オープン済のＰＤＦに追加します。*/
				if (iodtopdf.topdf(iodFiles[i].getAbsolutePath(), null) < 0) {
					if (log.isErrorEnabled()) {
						log.error(
							"iodtopdf#topdf "
								+ iodFiles[i]
								+ "ファイルのＰＤＦ変換出力に失敗しました。エラーメッセージ→"
								+ iodtopdf.getmess()
								+ " logファイル→"
								+ IoDocLogFilePath);
					}
					throw new SystemException(iodtopdf.getmess());
				}
			}

			/* オープン済のＰＤＦをクローズします。 */
			if (iodtopdf.pdfclose() < 0) {
				log.error(
					"iodtopdf#pdfclose オープン済のＰＤＦのクローズに失敗しました。エラーメッセージ→"
						+ iodtopdf.getmess()
						+ " logファイル→"
						+ IoDocLogFilePath);
				throw new SystemException(iodtopdf.getmess());
			}

		} finally {
			/* 内部で保持しているハンドルを開放します。 */
			iodtopdf.release();
		}
	}

	/**
	 * PDFファイルをまとめて1つのファイルに結合する。
	 * @param pdfFiles					結合するファイル。
	 * @param outPdfFile				出力PDFファイル
	 * @param password					パスワード
	 */
	//2005.01.10 iso Tomcatが落ちるので緊急回避
//	private static void combPdf(
	private static synchronized void combPdf(
			File[] iodFiles,
			File outPdfFile,
			String password)
			throws ConvertException {

		pdfcombine comb = new pdfcombine();

		/* 連結クラスの初期化 */
		if(comb.init() < 0) {
			if(log.isErrorEnabled()) {
				log.error("combPdf#init PDF結合設定の初期化で失敗しました。エラーメッセージ→"
					+ comb.geterror());
			}
			pmLog.error(comb.geterror());
			throw new ConvertException(comb.geterror());
		}
		
		/* Webへ最適化 */
		if(comb.setfastwebview(true) < 0) {
			if(log.isErrorEnabled()) {
				log.error("combPdf#setfastwebview Web最適化の設定で失敗しました。エラーメッセージ→"
					+ comb.geterror());
			}
			pmLog.error(comb.geterror());
			throw new ConvertException(comb.geterror());
		}
		
		/* PDF文章情報の設定 */
		if(comb.setdocinfo("申請書", "", "JSPS", "JSPS電子申請システム", "") < 0) {
			if(log.isErrorEnabled()) {
				log.error("combPdf#setdocinfo PDF文章情報の設定で失敗しました。エラーメッセージ→"
					+ comb.geterror());
			}
			pmLog.error(comb.geterror());
			throw new ConvertException(comb.geterror());
		}
		
		//2006.12.13 iso パスワードなしPDFはセキュリティ情報をセットしないよう変更
//		/* セキュリティ情報を設定 */
//		if(comb.setsecurity(
//				false	//連結元の先頭ファイルの設定は使用しない
//				,password
//				,password
//				,false	//印刷不可
//				,true	//編集不可
//				,true	//転載不可
//				,false)	//注釈不可
//				< 0) {
//			if(log.isErrorEnabled()) {
//				log.error("combPdf#setsecurity セキュリティ情報の設定に失敗しました。エラーメッセージ→"
//					+ comb.geterror());
//			}
//			pmLog.error(comb.geterror());
//			throw new ConvertException(comb.geterror());
//		}
		/* セキュリティ情報を設定 */
		if(!StringUtil.isBlank(password)) {
			if(comb.setsecurity(
					false	//連結元の先頭ファイルの設定は使用しない
					,password
					,password
					,false	//印刷不可
					,true	//編集不可
					,true	//転載不可
					,false)	//注釈不可
					< 0) {
				if(log.isErrorEnabled()) {
					log.error("combPdf#setsecurity セキュリティ情報の設定に失敗しました。エラーメッセージ→"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
				throw new ConvertException(comb.geterror());
			}
		}
		

		/* PDFファイルを開く */
		int status = comb.open(outPdfFile.getAbsolutePath());
		try {
			if(status < 0) {
				if(log.isErrorEnabled()) {
					log.error("combPdf#open 出力用PDFのオープンに失敗しました。エラーメッセージ→"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
				throw new ConvertException(comb.geterror());
			}

			for(int i = 0; i < iodFiles.length; i++) {
				/* 指定したPDFをオープン済のPDFに追加します。*/
				if(comb.combine(iodFiles[i].getAbsolutePath()) < 0) {
					if(log.isErrorEnabled()) {
						log.error("combPdf#combine "
							+ iodFiles[i]
							+ "連結PDFファイルの追加処理に失敗しました。エラーメッセージ→"
							+ comb.geterror());
					}
					pmLog.error(comb.geterror());
					throw new ConvertException(comb.geterror());
				}
			}

			/* PDFを連結。及び連結後出力PDFをクローズ */
			if (comb.close() < 0) {
				if(log.isErrorEnabled()) {
					log.error("combPdf#close PDFの連結、及び連結後出力PDFのクローズに失敗しました。エラーメッセージ→"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
				throw new ConvertException(comb.geterror());
			}

		} finally {
			/* 内部で保持しているハンドルを開放します。 */
			comb.release();
		}
	}


	//2005.07.15 iso PDF添付機能追加
	/**
	 * PDFファイルが有効かを調べる。
	 * 無効な場合は、エラーを返す。
	 * @param FileResource		調査するファイル。
	 * @return				有効：0　無効：エラーコード
	 */
	//2005.01.10 iso Tomcatが落ちるので緊急回避
//	public static int checkPdf(FileResource fileRes) throws ConvertException {
	public static synchronized int checkPdf(FileResource fileRes) throws ConvertException {

		pdfcombine comb = new pdfcombine();
		
		//ダミーファイル
		String tmpfile = WORK_PARENT_FOLDER + File.separator + "tmp.pdf";
		
		String filePath = WORK_PARENT_FOLDER + File.separator + getTempName() + PDF;
		File checkfile = new File(filePath);
		try {
			FileUtil.writeFile(checkfile, fileRes.getBinary());
		} catch (IOException e) {
			throw new ConvertException(
				"チェックするファイル'" + fileRes.getPath() + "'の書込み失敗しました。",new ErrorInfo("errors.8003"), e);
		}

		/* 連結クラスの初期化 */
		if(comb.init() < 0) {
			if(log.isErrorEnabled()) {
				log.error("checkPdf#init PDF結合設定の初期化で失敗しました。エラーメッセージ→"
					+ comb.geterror());
			}
			pmLog.error(comb.geterror());
//			return comb.geterrorno();
			return getErrorNo(comb.geterror());
		}

		/* PDFファイルを開く */
		int status = comb.open(tmpfile);
		try {
			if(status < 0) {
				if(log.isErrorEnabled()) {
					log.error("checkPdf#open 出力用PDFのオープンに失敗しました。エラーメッセージ→"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
//				return comb.geterrorno();
				return getErrorNo(comb.geterror());
			}
			
			if(comb.combine(filePath) < 0) {
				if(log.isErrorEnabled()) {
					log.error("checkPdf#combine "
						+ filePath
						+ "連結PDFファイルの追加処理に失敗しました。エラーメッセージ→"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
//				return comb.geterrorno();
				return getErrorNo(comb.geterror());
			}
				
			/* PDFを連結。及び連結後出力PDFをクローズ */
			if (comb.close() < 0) {
				if(log.isErrorEnabled()) {
					log.error("checkPdfclose PDFの連結、及び連結後出力PDFのクローズに失敗しました。エラーメッセージ→"
						+ comb.geterror());
				}
				pmLog.error(comb.geterror());
//				return comb.geterrorno();
				return getErrorNo(comb.geterror());
			}

		} finally {
			/* 内部で保持しているハンドルを開放します。 */
			comb.release();
			//一時ファイルの削除
			FileUtil.delete(checkfile);
		}
		return 0;
	}

	/**
	 * PDF Makeupのエラーメッセージから、エラーコードを返す。
	 * ※geterrorno()メソッドにバグがあるので、修正されるまで一時的に使用する。
	 * @param errMsg		エラーメッセージ。
	 * @return	結果コード
	 */
	private static int getErrorNo(String errMsg) {
		try {
			return Integer.parseInt(errMsg.substring(errMsg.indexOf("[")+1, errMsg.indexOf("]")));
		} catch(NumberFormatException e) {
			if(log.isErrorEnabled()) {
				log.error("PDF変換エラーコードの取得に失敗しました。");
			}
			return -1;
		}
	}


	/** テンポラリーファイルＩＤ*/
	private static int tempId = 0;

	/** テンポラリーファイルロックオブジェクト */
	private static Object lockTempName = new Object();

	/**
	 * テンポラリーファイル名を取得します
	 * @return	テンポラリーファイル名
	 */
	public static String getTempName() {
		synchronized (lockTempName) {
			StringBuffer sb = new StringBuffer();
			SimpleDateFormat df =
				new SimpleDateFormat("yyyy-MM-dd,HH-mm-ss,SSS");
			sb.append(df.format(new Date()));

			//３桁のIDにする。	
			if (tempId > 999)
				tempId = 0;
			String cntStr = "00" + Integer.toString(tempId++);
			sb.append("-" + cntStr.substring(cntStr.length() - 3));
			return sb.toString();
		}
	}
	
	
	/**
	 * PDFのページ数チェック
	 */
	public static int checkPageNum(String filePath, String password) {
		
		pmusrc pmu = new pmusrc();
		pmu.init();
		pmu.openpdf(filePath, password);
		
		return  pmu.getpagecount();
		
	}
	
	/**
	 * ウォーターマーク追記(PDFの背景に文字列を追加)
	 * @param pdfFile　ＰＤＦファイル
	 * @param password　ＰＤＦファイルのパスワード
	 * @param outText　背景文字列
	 * @return File    Nullの時エラー、Null以外時新たなPDFファイル
	 */
	public static File addWaterMark(File pdfFile, String password, String outText) 	{
		int sts;
		pmuobjwatermark objwater;
		int layer;
		int pos;
		int pagetype;
		File tempFile = null;

		try {
			// 出力先インスタンス作成 
			pmudst dstPdf = new pmudst();

			//初期化
			dstPdf.init();
			
			//元のPDFの全てのページを出力する
			sts = dstPdf.addsrcfile(pdfFile.getPath(), password);
			if (sts < 0 && log.isErrorEnabled()) {
				log.error("addWaterMark 出力PDFファイルを作成できない。エラーメッセージ→"
							+ dstPdf.geterror());
				
				//処理中止
				throw new SystemException(dstPdf.geterror());
			}
			
			if(!StringUtil.isBlank(password)) {
				// セキュリティ情報を設定. 印刷・編集・転載・注釈追加
				sts = dstPdf.setsecurity( password, password
										,false		//印刷不可
										,true		//編集不可
									  	,true		//転載不可
									    ,false)	;	//注釈不可
					
				if (sts < 0 && log.isErrorEnabled()) {
					log.error("addWaterMark セキュリティ情報の設定に失敗しました。エラーメッセージ→"
								+ dstPdf.geterror());
					
					//処理中止
					throw new SystemException(dstPdf.geterror());
				}
			}
			
			//ウォーターマークを生成
			objwater = dstPdf.createobjwatermark();
	
			// pos = pmuobjwatermark.POS_XY ;/* ＸＹを使用 */
			// pos = pmuobjwatermark.POS_LT ;/* 左上 */
			// pos = pmuobjwatermark.POS_LM ;/* 左中段 */
			// pos = pmuobjwatermark.POS_LB ;/* 左下 */
			// pos = pmuobjwatermark.POS_CT ;/* 中央上 */
			pos = pmuobjwatermark.POS_CM;/* 中央中段 */
			// pos = pmuobjwatermark.POS_CB ;/* 中央下 */
			// pos = pmuobjwatermark.POS_RT ;/* 右上 */
			// pos = pmuobjwatermark.POS_RM ;/* 右中段 */
			// pos = pmuobjwatermark.POS_RB ;/* 右下 */
			sts = objwater.setbasepos(pos);
	
			/* 位置調整(絶対値) */
			sts = objwater.setpos(0, 0);
	
			/* 位置調整(移動) */
			sts = objwater.movepos(0, 0);
	
			/* 配置(前面) */
			//layer = pmuobjwatermark.LAYER_FRONT;
			layer = pmuobjwatermark.LAYER_BACK;
			sts = objwater.setlayer(layer);
	
			/* ページ番号指定 */
			pagetype = pmuobjwatermark.PAGETYPE_ALL;
			//pagetype = pmuobjwatermark.PAGETYPE_PAGE;
			//pagetype = pmuobjwatermark.PAGETYPE_FROMTO;
			//pagetype = pmuobjwatermark.PAGETYPE_FROM;
			//pagetype = pmuobjwatermark.PAGETYPE_TO;
			if (pagetype == pmuobjwatermark.PAGETYPE_ALL) { /* 全てのページ */
				sts = objwater.settargetpage(pagetype, 0, 0);
			} else if (pagetype == pmuobjwatermark.PAGETYPE_PAGE) { /* 単一ページ */
				sts = objwater.settargetpage(pagetype, 1, 0);
				sts = objwater.settargetpage(pagetype, 2, 0);
			} else if (pagetype == pmuobjwatermark.PAGETYPE_FROMTO) { /* ページ範囲 */
				sts = objwater.settargetpage(pagetype, 1, 2);
			} else if (pagetype == pmuobjwatermark.PAGETYPE_FROM) { /* このページ以降 */
				sts = objwater.settargetpage(pagetype, 3, 0);
			} else if (pagetype == pmuobjwatermark.PAGETYPE_TO) { /* このページ迄 */
				sts = objwater.settargetpage(pagetype, 10, 0);
			}
	
			sts = objwater.setfontscale(120);
	
			sts = objwater.setfontrotate(45.0);
	
			/* フォント */
			sts = objwater.setfont("ＭＳ 明朝");
	
			/* 文字サイズ */
			sts = objwater.setfontsize(96.0);
	
			/* 文字の色 */
			sts = objwater.setfontcolor(200, 200, 200);
	
			/* 強調文字 */
			sts = objwater.setfontbold(true);
	
			/* 斜体文字 */
			sts = objwater.setfontitalic(true);
	
			sts = objwater.setstring(outText);

			//一時ファイル名を作成
			String tempName = pdfFile.getParent() + File.separator + getTempName() + PDF;
			
			/* 編集後のＰＤＦを出力する */
			sts = dstPdf.outputpdf(tempName);
			if (sts < 0){
				//ＰＤＦファイルの書き込みは失敗した
				log.error("addWaterMark PDFファイルの書き込みに失敗しました。エラーメッセージ→"
						+ dstPdf.geterror());
			}else{
				//新たなＰＤＦファイルを生成する
				tempFile = new File(tempName);
			}
			
			/* 出力先インスタンス開放 */
			dstPdf.release();
		}catch (Exception e){
			//何もしない
		}

		return  tempFile;
	}
}
