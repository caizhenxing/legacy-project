/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : PdfConvert.java
 *    Description : PDF変換を行うファサードクラス。
 *
 *    Author      : Admin
 *    Date        : 2003/01/10
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/01/10    v1.0        Admin          新規作成
 *    2006/07/19    v1.1        DIS.gongXB     変更
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.impl.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.pdf.webdoc.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.util.*;
import jp.go.jsps.kaken.web.util.DateFormat;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;

import org.apache.commons.logging.*;
import org.apache.velocity.*;
import org.apache.velocity.app.*;

/**
 * PDF変換を行うファサードクラス。
 */
public class PdfConvert implements IPdfConvert {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	private static Log log = LogFactory.getLog(PdfConvert.class);

	/** 事業IDごとの設定情報 */
	private static Map iodSettings = Collections.synchronizedMap(new HashMap());

    /** XML変換用テンプレート */
    private static String xmlTemplate = null;  

//2006/07/03 iso 変更 ここから---------------------------------------------------------------------
	//2006.07.03 iso PDF変換サーバ振り分け処理のため追加
//	/** 変換サーバURL文字列 */
//	private static final String SERVER_URL = ApplicationSettings.getString(ISettingKeys.PDF_CONV_SERVLET_URL);

	/** 変換サーバURL文字列 */
	private static final String SERVER_URLS[] = ApplicationSettings.getStrings(ISettingKeys.PDF_CONV_SERVLET_URL);

	/** 変換サーバURL文字列 */
	private static final String SERVER_WEIGHTS[] = ApplicationSettings.getStrings(ISettingKeys.PDF_CONV_SERVLET_WEIGHTS);

	//2006.07.03 iso 添付ファイル変換サーバ振り分け処理のため追加
	/** 添付ファイル変換サーバURL文字列 */
	private static final String ANNEX_SERVER_URLS[] = ApplicationSettings.getStrings(ISettingKeys.ANNEX_CONV_SERVLET_URL);

	/** 添付ファイル変換サーバURL文字列 */
	private static final String ANNEX_SERVER_WEIGHTS[] = ApplicationSettings.getStrings(ISettingKeys.ANNEX_CONV_SERVLET_WEIGHTS);
// 2006/07/03 iso 変更 ここまで---------------------------------------------------------------------

	/** 変換サーバURL文字列 */
	private static final String REPORT_SETTING_FILE_PATH =ApplicationSettings.getString(ISettingKeys.PDF_REPORT_SETTING_FILE_PATH);

	/** 申請書PDFファイル格納フォルダ(本フォルダ配下に「事業ID\システム受付番号\pdf」と続く) */
	private static String SHINSEI_PDF_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_PDF_FOLDER);		

    /** 申請書XMLファイル格納フォルダ */
    private static String SHINSEI_XML_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_XML_FOLDER);    
    
    /** ロック用オブジェクト */
    private static Object lock = new Object();  

    /** DBリンク名 */
	private String dbLink = "";

    /** データ保管サーバUNC */
    protected static final String HOKAN_SERVER_UNC = ApplicationSettings.getString(ISettingKeys.HOKAN_SERVER_UNC);
   
    /** UNCに変換するドライブレター */
    protected static final String DRIVE_LETTER_CONVERTED_TO_UNC = ApplicationSettings.getString(ISettingKeys.DRIVE_LETTER_CONVERTED_TO_UNC);

	//2005/05/25 追加 ここから---------------------------------------------------------------
	//理由　表紙pdfフォルダ指定のため
   	/** 表紙PDFファイル格納フォルダ */
   	private static String PDF_COVER = ApplicationSettings.getString(ISettingKeys.PDF_COVER);
   	
   	private static final String HYOSHI_PDF_TEMPLATE_NAME="hyoshi";
   	//追加 ここまで--------------------------------------------------------------------------

// 2006/06/27 dyh add start
    /** 領域計画書表紙PDFファイル格納フォルダ(本フォルダ配下に「事業ID\仮領域番号\pdf」と続く) */
    private static String PDF_DOMAINCOVER = ApplicationSettings.getString(ISettingKeys.PDF_DOMAINCOVER);

    /** 領域計画書表紙PDFファイルの変換設定テンプレート名 */
    private static final String HYOSHIRYOIKI_PDF_TEMPLATE_NAME = "hyoshiRyoiki";
// 2006/06/27 dyh add end

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------

    /**
     * コンストラクタ。
     */
    public PdfConvert() {
        super();
    }

    /**
     * コンストラクタ。
     * @param dbLink データベースリンク名
     */
    public PdfConvert(String dbLink) {
        super();
		this.dbLink = dbLink;
    }

	//---------------------------------------------------------------------
	// implements IPdfConvert
	//---------------------------------------------------------------------

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.pdf.IPdfConvert#ShinseiDataConvert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
     * <b>データベースリンク未対応。</b>
	 */
	public void shinseiDataConvert(UserInfo userInfo, ShinseiDataPk pkInfo)
		throws ApplicationException {

		Connection connection = null;
		boolean success = false;
		try {
			connection = DatabaseUtil.getConnection();

			//------------------
			//申請データXML/PDF変換
			//------------------
			convertShinseiData(connection, userInfo, pkInfo);

			if (log.isDebugEnabled()) {
				log.debug("-->>添付ファイルを変換します。");
			}
			//------------------
			//添付ファイルPDF変換
			//------------------
// 2007/02/08 張志男　修正 ここから           
            //convertShinseiTenpuFile(connection, userInfo, pkInfo);
              /** 確認完了以外（[次へ進む]時）*/
            convertShinseiTenpuFile(connection, userInfo, pkInfo , false);
// 2007/02/08　張志男　修正 ここまで	

			if (log.isDebugEnabled()) {
				log.debug("--<<添付ファイルを変換します。");
			}
			//------------------
			//登録正常終了
			//------------------
			success = true;			
	
		} catch (DataAccessException e) {
			throw new ConvertException("申請データ情報の取得に失敗しました。",new ErrorInfo("errors.8003"),e);
		} catch (IOException e) {
			throw new ConvertException("変換ファイルの保存に失敗しました。(ファイルIO)",new ErrorInfo("errors.8003"),e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ConvertException("PDFデータ変換DB登録中にエラーが発生しました。",e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.pdf.IPdfConvert#getShinseiFileResource(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public FileResource getShinseiFileResource(
		UserInfo userInfo,
		final ShinseiDataPk pkInfo)
		throws ApplicationException, NoDataFoundException, ConvertException {
		return getShinseiFileResource(userInfo, pkInfo, true);
	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.IPdfConvert#getShinseiFileResourceWithoutLock(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public FileResource getShinseiFileResourceWithoutLock(
		UserInfo userInfo,
		ShinseiDataPk pkInfo)
		throws ApplicationException, NoDataFoundException, ConvertException{
			return getShinseiFileResource(userInfo, pkInfo, false);
	}	

	//---------------------------------------------------------------------
	// methods
	//---------------------------------------------------------------------	
	
	/**
	 * 申請データよりPDFファイルを作成する。
	 * lockFlagがtrueのとき、作成されたPDFファイルにパスワードロックをかける。
	 * @param userInfo
	 * @param pkInfo
	 * @param lockFlag
	 * @return
	 * @throws ApplicationException
	 * @throws NoDataFoundException
	 * @throws ConvertException
	 */
	public FileResource getShinseiFileResource(
		UserInfo userInfo,
		final ShinseiDataPk pkInfo,
		boolean lockFlag)
		throws ApplicationException, NoDataFoundException, ConvertException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//申請データ管理DAO　2007/6/15 追加
            ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo, dbLink);
			
			//----------------------------
			//結合するファイルの取得
			//----------------------------
			List iodFiles  = null;
			try{
				//2007/6/15 修正
				//iodFiles = new ShinseiDataInfoDao(userInfo,dbLink).getIodFilesToMerge(connection,pkInfo);
				iodFiles = shinseiDao.getIodFilesToMerge(connection,pkInfo);
				//2007/6/15　修正完了
			}catch(NoDataFoundException e){
				try{
					//もしもiodファイルパスが存在しなかった場合は再度変換処理をかけ、その上で再試行を行う（１回だけ）
					shinseiDataConvert(userInfo, pkInfo);
					//2007/6/15 修正
					//iodFiles = new ShinseiDataInfoDao(userInfo,dbLink).getIodFilesToMerge(connection,pkInfo);
					iodFiles = shinseiDao.getIodFilesToMerge(connection,pkInfo);
					//2007/6/15　修正完了
				}catch(Exception ex){
					//それでも失敗した場合は上位側に例外を投げる
					throw e;
				}
			}
			List iodFileResource = new ArrayList();

			//----------------------------
            //DBリンクを考慮してファイル読込み
			//----------------------------
			for (Iterator iter = iodFiles.iterator(); iter.hasNext();) {
				File element = (File) iter.next();
                File targetFile = null;
                if (dbLink == null || dbLink.length() == 0) {
                    //通常
                    targetFile = element;
				} else {
					targetFile =
						new File(StringUtil.substrReplace(element.getAbsolutePath(),DRIVE_LETTER_CONVERTED_TO_UNC,HOKAN_SERVER_UNC));
					//パス文字列をUNC形式に変換する
                    if(log.isDebugEnabled()){
						log.debug("dbLink経由のため、'"
								+ element
								+ "'→'"
								+ targetFile
								+ "'ファイルを読み込みます。");
                    }
                }
                iodFileResource.add(readFile(targetFile));
 			}

			//----------------------------
			//パスワード
			//----------------------------
			String password = null;
			if(lockFlag){
				password = getPassword(connection,userInfo);
			}

			if(log.isDebugEnabled()){
				log.debug("--->>PDFを作成します。");
			}

			//----------------------------
			//変換
			//----------------------------
			//2006.07.03 iso PDF変換サーバ振り分け処理のため追加
//			ISystemServise servise =
//				SystemServiceFactory.getSystemService(
//					IServiceName.CONVERT_SERVICE,
//					SERVER_URL);
//			try{
//				return servise.iodToPdf(iodFileResource,password);
//			}finally{
//				if (log.isDebugEnabled()) {
//					log.debug("---<<PDFを作成しました。");
//				}
//			}

// 2006/07/19 dyh update start 原因：共通方法を作成
//			try {
//				String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
//				for(int i=0; i < urls.length; i++) {
//					ISystemServise servise =
//						SystemServiceFactory.getSystemService(
//							IServiceName.CONVERT_SERVICE,
//							urls[i]);
//					try{
//						FileResource fileResource = servise.iodToPdf(iodFileResource,password);
//						if (log.isDebugEnabled()) {
//							log.debug("---<<PDFを作成しました。");
//						}
//						return fileResource;
//					} catch (Exception e) {
//						log.info("PDF変換サーバでエラーが発生しました。URL:" + urls[i], e);
//					}
//				}
//				//全サーバ分のループが終わってもfileResourceが帰らない場合、
//				//接続エラー、変換エラーの両方が考えられるが、ConvertExceptionとする。
//				throw new ConvertException("全てのPDF変換サーバで処理に失敗しました。");
//			} catch(IllegalArgumentException e) {
//				throw new SystemException("PDF変換サーバURLの取得に失敗しました。");
//			}
			//2007/6/15 修正
            //return getPdfFileResource(iodFileResource, password);

			ShinseiDataInfo shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, pkInfo, false);
            String jokyoId = shinseiDataInfo.getJokyoId();// 申請状況ID
            String jigyoKbn = shinseiDataInfo.getKadaiInfo().getJigyoKubun();// 事業ID
			
            return getPdfFileResource(iodFileResource, password, jokyoId, jigyoKbn);
			//2007/6/15 修正完了
            
// 2006/07/19 dyh update start
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"PDFファイルの取得に失敗しました。", new ErrorInfo("errors.8003"),e);
		} finally{
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * ユーザのパスワードを取得する。
	 * @param userInfo
	 * @return
	 */
	private String getPassword(Connection connection ,UserInfo userInfo)
            throws NoDataFoundException, DataAccessException {

		if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
			//申請者のとき
			ShinseishaInfo info = new ShinseishaInfoDao(userInfo)
                .selectShinseishaInfo(connection,userInfo.getShinseishaInfo());
			return info.getPassword();
		}else if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
			//所属機関担当者のとき
			ShozokuInfo info = new ShozokuInfoDao(userInfo)
                .selectShozokuInfo(connection,userInfo.getShozokuInfo());	
			return info.getPassword();
		}else if(userInfo.getRole().equals(UserRole.SHINSAIN)){
			//審査員のとき
            ShinsainInfo info = new ShinsainInfoDao(userInfo)
                .selectShinsainInfo(connection,userInfo.getShinsainInfo());    
            return info.getPassword();
		}else if(userInfo.getRole().equals(UserRole.GYOMUTANTO)
                ||userInfo.getRole().equals(UserRole.SYSTEM)){
			//業務担当者・システム管理者のとき
			GyomutantoInfo info = new GyomutantoInfoDao(userInfo)
                .selectGyomutantoInfo(connection,userInfo.getGyomutantoInfo());	
			return info.getPassword();
        //2005/04/11 追加 ここから------------------------------------------------------------
        //理由 部局担当者の場合のパスワード取得のため
		}else if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
			BukyokutantoInfo info = new BukyokutantoInfoDao(userInfo)
                .selectBukyokutantoInfo(connection, userInfo.getBukyokutantoInfo());
			return info.getPassword();
		//追加 ここまで
		}else{
			throw new SystemException("ユーザを特定できません。");
		}
	}

	/**
	 * 申請データ情報を取得し、申請データをPDFに変換し、保存ファイルパスをDBに書き込む。
 	 * @param connection
	 * @param userInfo
	 * @param pkInfo
	 * @throws ApplicationException		変換に失敗したとき。
	 * @throws DataAccessException		
	 * @throws IOException
	 */
	private void convertShinseiData(
            Connection connection,
            UserInfo userInfo,
            ShinseiDataPk pkInfo)
            throws ApplicationException, DataAccessException, IOException {
		
		//----------------------------
		//申請データ情報の取得
		//----------------------------
		ShinseiDataInfo shinseiDataInfo = null;
		try{
			shinseiDataInfo = new ShinseiMaintenance().selectShinseiDataInfo(userInfo, pkInfo);
		}catch(ApplicationException e){
			throw new ConvertException("申請データの取得に失敗しました。(データアクセス)",new ErrorInfo("errors.8003"),e);
		}
		
		//2005/04/19 追加 ------------------------------ここから
		//理由 若手研究の場合は、誕生日情報を取得する必要がある。
//		if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(shinseiDataInfo.getJigyoCd())
//				||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(shinseiDataInfo.getJigyoCd())){
        // 2006/02/14 追加する
		// 理由　若手研究、若手スタートアップ、特別研究促進費の若手研究（AB）の場合は、誕生日情報を取得する必要ある。
		if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_WAKATESTART.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(shinseiDataInfo.getJigyoCd())
//2007/02/03 苗　追加ここから
                ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(shinseiDataInfo.getJigyoCd())
//2007/02/03 苗　追加ここまで
//2007/03/13 劉長宇　削除　ここから
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(shinseiDataInfo.getJigyoCd())
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(shinseiDataInfo.getJigyoCd())
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(shinseiDataInfo.getJigyoCd())
//2007/03/13 劉長宇　削除　ここまで
        ){
			ShinseishaPk pk= new ShinseishaPk();
			pk.setShinseishaId(shinseiDataInfo.getShinseishaId());
			ShinseishaInfo shinseishaInfo=new ShinseishaInfoDao(userInfo).selectShinseishaInfo(connection,pk);
			shinseiDataInfo.setBirthDay(shinseishaInfo.getBirthday());
		}
		//2005/04/19 追加 ------------------------------ここまで
		
		//----------------------------
		//保存ファイルの作成(PDF)
		//----------------------------
		File iodFile = new File(MessageFormat.format(SHINSEI_PDF_FOLDER,
                new Object[] {shinseiDataInfo.getJigyoId(),shinseiDataInfo.getSystemNo()}));
		if(log.isDebugEnabled()){
			log.debug("申請データPDF変換ファイルは'" + iodFile + "'です。");
		}

        //----------------------------
        //保存ファイルの作成(XML)
        //----------------------------
        File xmlFile = new File(MessageFormat.format(SHINSEI_XML_FOLDER,
                new Object[] {shinseiDataInfo.getJigyoId(),shinseiDataInfo.getSystemNo()}));
        if(log.isDebugEnabled()){
            log.debug("申請データXML変換ファイルは'" + xmlFile + "'です。");
        }
        		
		//----------------------------
		//DB更新
		//----------------------------
        new ShinseiDataInfoDao(userInfo).updateFilePath(connection, pkInfo, iodFile ,xmlFile);
		
		//----------------------------
		//申請データ情報の変換(PDF)
		//----------------------------
// 2006/07/19 dyh update start
//		FileResource iodFileResource = makeIodFormShinseiData(connection ,shinseiDataInfo);
        FileResource iodFileResource = makeIodFormShinseiData(shinseiDataInfo);
// 2006/07/19 dyh update end
		
		//----------------------------
		//PDFファイル書込(毎回更新するため上書きのみ)
		//----------------------------
		FileUtil.writeFile(iodFile,iodFileResource.getBinary());
        iodFileResource = null;

		//----------------------------
		//申請データ情報のXML変換処理
		//----------------------------
		String xmlString = makeXmlFormShinseiData(shinseiDataInfo);

//		if (log.isDebugEnabled()) {
//			log.debug("XMLの表示\n" + xmlString);
//		}

		//----------------------------
		//XMLファイルUTF-8で書込(毎回更新するため上書きのみ)
		//----------------------------
		FileUtil.writeFile(xmlFile, xmlString.getBytes("UTF-8"));

	}
// 2007/02/08 張志男　追加ここから
    /**
     *確認完了時のshinseiDataConvertForConfirmメソッドでは、
     *  「convertShinseiTenpuFile」ではなく、「convertShinseiTenpuFileForConfirm」を呼び出すよう変更。
     * @param connection
     * @param userInfo
     * @param pkInfo
     * @throws DataAccessException
     * @throws ApplicationException
     * @throws IOException
    */
    private void convertShinseiTenpuFileForConfirm(
            Connection connection, UserInfo userInfo, ShinseiDataPk pkInfo)
            throws DataAccessException,  ApplicationException, IOException {
           convertShinseiTenpuFile(connection, userInfo, pkInfo, true);
          }
// 2007/02/08　張志男　追加ここまで

	/**
	 * 申請データ情報より、添付ファイル情報を取得し、添付ファイルをPDFに変換し、保存ファイルパスをDBに書き込む。
	 * @param connection
	 * @param userInfo
	 * @param pkInfo
     * @param confirmFlg  false：確認完了以外（[次へ進む]時） true：確認完了時
	 * @throws DataAccessException
	 * @throws ApplicationException
	 * @throws IOException
	 */
	private void convertShinseiTenpuFile(
		Connection connection,
		UserInfo userInfo,
		ShinseiDataPk pkInfo,
        boolean confirmFlg)
		throws
			DataAccessException,
			ApplicationException,
			IOException {
		try{
			//----------------------------
			//申請データの添付ファイル情報の取得
			//----------------------------
			TenpuFileInfoDao dao = new TenpuFileInfoDao(userInfo);
			TenpuFileInfo[] fileInfos = dao.selectTenpuFileInfos(connection,pkInfo);
			
			for (int i = 0; i < fileInfos.length; i++) {
				//----------------------------
				//変換されていないときは
				//----------------------------
				if(!isConverted(fileInfos[i])){
					
					//----------------------------
					//変換する。						
					//----------------------------
					File wordFile = new File(fileInfos[i].getTenpuPath());
					FileResource pdfFileResource = annexFileConvert(readFile(wordFile));
					
					//----------------------------
					//添付フォルダにファイル書込
					//----------------------------
					File pdfFile = new File(wordFile.getParentFile(),pdfFileResource.getName());
					fileInfos[i].setPdfPath(pdfFile.getAbsolutePath());
					FileUtil.writeFile(pdfFile,pdfFileResource.getBinary());
					
					//----------------------------
					//DB更新
					//----------------------------
					dao.updateTenpuFileInfo(connection,fileInfos[i]);
				}

//2007/02/14　苗　追加ここから
                //確認完了時                
                if (confirmFlg) {
                    //ページ数を取得する
                    int pageNum = WebdocUtil.checkPageNum(fileInfos[i].getPdfPath(), 
                            userInfo.getShinseishaInfo().getPassword());
                    
                    //事業情報テーブルにページ数範囲を取得
                    JigyoKanriInfo jigyoKanriInfo = new JigyoKanriInfo();                   
                    jigyoKanriInfo.setJigyoId(fileInfos[i].getJigyoId());
                    
                    JigyoKanriInfoDao jigyoKanriInfoDao = new JigyoKanriInfoDao(userInfo);                                   
                    jigyoKanriInfo = jigyoKanriInfoDao.selectJigyoKanriInfo(connection,jigyoKanriInfo);
                    
                    //エラーメッセージ
                    String errorMessage = "";
                    //ページ数範囲が全部で入力
                    if (!StringUtil.isBlank(jigyoKanriInfo.getPageTo())
                            && !StringUtil.isBlank(jigyoKanriInfo.getPageFrom())) {
                        //ページの上限と下限が設定されていて、上限 == 下限の時
                        if (StringUtil.parseInt(jigyoKanriInfo.getPageFrom()) 
                                == StringUtil.parseInt(jigyoKanriInfo.getPageTo())) {
                            errorMessage = jigyoKanriInfo.getPageFrom() + "ページ";
                        //ページの上限と下限が設定されていて、上限 != 下限の時    
                        } else {
                            errorMessage = jigyoKanriInfo.getPageFrom() + "ページ以上、"
                                                    + jigyoKanriInfo.getPageTo() + "ページ以下";
                        }
                    }
                    //ページの下限のみ設定されてる（上限は空）の場合
                    else if (!StringUtil.isBlank(jigyoKanriInfo.getPageFrom())
                            && StringUtil.isBlank(jigyoKanriInfo.getPageTo())) {
                        errorMessage = jigyoKanriInfo.getPageFrom() + "ページ以上";
                    }
                    //ページの上限のみ設定されてる（下限は空）の場合
                    else if (!StringUtil.isBlank(jigyoKanriInfo.getPageTo())
                            && StringUtil.isBlank(jigyoKanriInfo.getPageFrom())) {
                        errorMessage = jigyoKanriInfo.getPageTo() + "ページ以下";
                    }

                    //ページ数範囲下限が入力したら、PDFページ数＜ページ数範囲下限の場合はエラーとする
                    if (!StringUtil.isBlank(jigyoKanriInfo.getPageFrom())
                            && pageNum < StringUtil.parseInt(jigyoKanriInfo.getPageFrom())) {
                        throw new ApplicationException(
                                "PDFページ数チェックにエラーが発生しました。", new ErrorInfo(
                                        "errors.9031", new String[] { errorMessage }));
                    }
                    //ページ数範囲上限が入力したら、PDFページ数＞ページ数範囲上限の場合はエラーとする
                    if (!StringUtil.isBlank(jigyoKanriInfo.getPageTo())
                            && pageNum > StringUtil.parseInt(jigyoKanriInfo.getPageTo())) {
                        throw new ApplicationException(
                                "PDFページ数チェックにエラーが発生しました。", new ErrorInfo(
                                        "errors.9031", new String[] { errorMessage }));
                    }
                }
//2007/02/14　苗　追加ここまで
			}
		}catch(NoDataFoundException e){
			if(log.isDebugEnabled()){
				log.debug("添付ファイルがありません。",e);
			}
		}catch(ConvertException e){
			//PDF変換処理のタイムアウトの場合は、アラートメールを送信する。
			if("errors.8001".equals(e.getErrorCode())){
				//アラート用なのでベタ書き。
				String SMTP_SERVER_ADDRESS = ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS);
				String FROM_ADDRESS = ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);
				String to = ApplicationSettings.getString(ISettingKeys.TO_ADDRESS_FOR_ALERT);;
				String subject = "【日本学術振興会電子申請システム】アラート通知";
				String content = "PDF変換処理でタイムアウトが発生しました。\n"
								+"・ユーザID："+userInfo.getId()+"\n"
								+"・申請書（システム受付番号）："+pkInfo.getSystemNo()+"\n"
								+"・発生日時："+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date())+"\n"
								;
				//-----メール送信
				try{
					SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
					mailer.sendMail(FROM_ADDRESS,			//差出人
									to,						//to
									null,					//cc
									null,					//bcc
									subject,				//件名
									content);				//本文
				}catch(Exception ex){
					log.warn("メール送信に失敗しました。",ex);
					return;
				}
			}
			throw e;
		}		
	}
	
	/**
	 * 変換されているかチェックする。
	 * @param fileInfos				添付ファイル情報。
	 * @return						true 変換されている false 変換されていない。
	 * @throws DataAccessException	添付ファイルが設定されていないとき、または、存在しないとき。
	 */
	private boolean isConverted(TenpuFileInfo fileInfos) throws ConvertException{
		//添付ファイルのチェック

		//2005.07.14 iso PDFファイル添付機能
		//PDFファイルが直接添付されPDFパス情報が存在する場合は、すぐにtrueを返すよう変更。
		if(fileInfos.getPdfPath() != null && !fileInfos.getPdfPath().equals("")) {
			return true;
		} else {
			String wordFilePath = fileInfos.getTenpuPath();
			if (wordFilePath == null || wordFilePath.length() == 0) {
				throw new ConvertException("添付ファイルが設定されていません。"
						+ "システム受付番号'"
						+ fileInfos.getSystemNo()
						+ "' ：シーケンス番号'"
						+ fileInfos.getSeqTenpu()
						+ "'",new ErrorInfo("errors.8003"));
			}
			
			File wordFile = new File(wordFilePath);
			if(!wordFile.exists()){
				throw new ConvertException("添付ファイル'" + wordFile + "'が見つかりません。"
						+ "システム受付番号'"
						+ fileInfos.getSystemNo()
						+ "' ：シーケンス番号'"
						+ fileInfos.getSeqTenpu()
						+ "'",new ErrorInfo("errors.8003"));
			}
	
			//変換ファイルが存在しない場合。
			if (fileInfos.getPdfPath() == null
				|| fileInfos.getPdfPath().length() == 0 ) {
				return false;
			}
			return true;
		}
	}

    /**
     * 申請データ情報よりXMLファイルを作成する。
     * @param shinseiDataInfo       申請データオブジェクト
     * @return                      XML文字列
     * @throws ApplicationException 
     */
// 2006/07/19 dyh update start
//	private String makeXmlFormShinseiData(ShinseiDataInfo shinseiDataInfo)
//		    throws ApplicationException {
    private String makeXmlFormShinseiData(ShinseiDataInfo shinseiDataInfo){
// 2006/07/19 dyh update end
		synchronized (lock) {
			//テンプレート情報の確認            
			if (xmlTemplate == null) {
				//テンプレートの取得
				File xmlTemplateFile =
					ApplicationSettings.getFile(
						ISettingKeys.SHINSEI_XML_TEMPLATE);
				xmlTemplate = readSettingFile(xmlTemplateFile);
			}
			//テンプレートを申請データを使用して変換する。
			return merge(xmlTemplate, shinseiDataInfo);
		}
	}
    
	/**
	 * 申請データ情報よりpdfファイルを作成する。
     * @param connection
	 * @param shinseiDataInfo		申請データオブジェクト
	 * @return						pdfファイル
	 * @throws ApplicationException	
	 * @throws ConvertException		pdfファイル作成時に例外が発生した場合。
	 */
// 2006/07/19 dyh update start
//	private FileResource makeIodFormShinseiData(Connection connection,ShinseiDataInfo shinseiDataInfo)
    private FileResource makeIodFormShinseiData(ShinseiDataInfo shinseiDataInfo)
// 2006/07/19 dyh update end
		throws ApplicationException {

		//様式種別
		String jigyoKubun = ShinseiFormat.getShinseiShubetu(shinseiDataInfo.getJigyoId());
		
		//事業区に該当する設定情報を取得する
		String template = getSettingTemplate(jigyoKubun);

		//テンプレートを申請データを使用して変換する。
		String iodSetting = merge(template, shinseiDataInfo);

        //########## DEBUG ##########       
//        if(log.isDebugEnabled()){
//            log.debug("PDF出力用XML '" + iodSetting + "'");
//        }

		//設定ファイルの読込み
		IodSettings settings = new IodSettings(new StringReader(iodSetting));

		//設定情報オブジェクトの作成
		List iodSettingInfo = settings.getContents();

        if (log.isDebugEnabled()) {
            log.debug(
            	//2005.07.14 iso PDF変換に変更
//                ">>IODファイル作成サービスを呼び出します。");
				">>IODファイル作成サービスを呼び出します。");
        }

		//PDFファイル作成サービス呼び出し
		//2006.07.03 iso PDF変換サーバ振り分け処理のため追加
//		ISystemServise servise =
//			SystemServiceFactory.getSystemService(
//				IServiceName.CONVERT_SERVICE,
//				SERVER_URL);
//		FileResource iodFileResource = servise.iodFileCreation(iodSettingInfo);
//
//		//########## DEBUG ##########		
//		if (log.isDebugEnabled()) {
//			log.debug(
//				//2005.07.14 iso PDF変換に変更
////				">>作成IODファイル名 '"
//				">>作成IODファイル名 '"
//					+ iodFileResource.getName()
//					+ " サイズ '"
//					+ iodFileResource.getBinary().length
//					+ "'");
//		}
//      return iodFileResource;

// 2006/07/19 dyh update start------------------------
		try {
			String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
//			for(int i=0; i < urls.length; i++) {
//				ISystemServise servise =
//					SystemServiceFactory.getSystemService(
//						IServiceName.CONVERT_SERVICE,
//						urls[i]);
//				try{
//					FileResource iodFileResource = servise.iodFileCreation(iodSettingInfo);
//					if (log.isDebugEnabled()) {
//						log.debug(
//						">>作成IODファイル名 '"
//							+ iodFileResource.getName()
//							+ " サイズ '"
//							+ iodFileResource.getBinary().length
//							+ "'");
//					}
//					return iodFileResource;
//				} catch (Exception e) {
//					log.info("PDF変換サーバでエラーが発生しました。URL:" + urls[i], e);
//				}
//			}
//			//全サーバ分のループが終わってもiodFileResourceeが取得できない場合、
//			//接続エラー、変換エラーの両方が考えられるが、ConvertExceptionとする。
//			throw new ConvertException("全てのPDF変換サーバで処理に失敗しました。");

            return getIodFileResourceShinsei(urls, iodSettingInfo);
		} catch(IllegalArgumentException e) {
			throw new SystemException("PDF変換サーバURLの取得に失敗しました。");
		}
// 2006/07/19 dyh update end------------------------
	}

	/**
	 * 添付ファイルよりpdfファイルを作成する。
	 * @param annexFileResource		添付ファイルリソース
	 * @return						pdfファイルリソース					
	 * @throws ApplicationException
	 * @throws ConvertException
	 */
	private FileResource annexFileConvert(FileResource annexFileResource)
		throws ApplicationException, ConvertException {

		if(log.isDebugEnabled()){
			log.debug("\t--->>>変換処理を開始します。");
		}

		//PDFファイル作成サービス呼び出し
		//2006.07.03 iso PDF変換サーバ振り分け処理のため追加
//		ISystemServise servise =
//			SystemServiceFactory.getSystemService(
//				IServiceName.CONVERT_SERVICE,
//				SERVER_URL);
//		FileResource iodFileResource =
//			servise.iodFileCreation(annexFileResource);
//
//		//取得ファイルのデバッグ表示
//		if (log.isDebugEnabled()) {
//			log.debug(
//				//PDF変換に変更
////				"変換後IODファイル名 '"
//				"変換後IODファイル名 '"
//					+ iodFileResource.getName()
//					+ " サイズ '"
//					+ iodFileResource.getBinary().length
//					+ "'");
//		}
//
//		if (log.isDebugEnabled()) {
//			log.debug("\t---<<<変換処理を終了ました。");
//		}
//      return iodFileResource;
		try {
			String[] urls;
			if(ANNEX_SERVER_URLS.length != 0) {
				//添付ファイル変換サーバのみURL選択を独立させたい場合、ANNEX_SERVER_URLSにURLを設定する。
				urls = getSortedUrls(ANNEX_SERVER_URLS, ANNEX_SERVER_WEIGHTS);
			} else {
				//ANNEX_SERVER_URLSが空なら、添付ファイル変換サーバはPDF変換サーバと同じになる。
				urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
			}
			for(int i=0; i < urls.length; i++) {
				ISystemServise servise =
					SystemServiceFactory.getSystemService(
						IServiceName.CONVERT_SERVICE,
						urls[i]);
				try{
					FileResource iodFileResource = servise.iodFileCreation(annexFileResource);
					if (log.isDebugEnabled()) {
						log.debug(
							"変換後IODファイル名 '"
								+ iodFileResource.getName()
								+ " サイズ '"
								+ iodFileResource.getBinary().length
								+ "'");
						log.debug("\t---<<<変換処理を終了ました。");
					}
					return iodFileResource;
				} catch (Exception e) {
					log.info("PDF変換サーバでエラーが発生しました。URL:" + urls[i], e);
				}
			}
			//全サーバ分のループが終わってもiodFileResourceeが取得できない場合、
			//接続エラー、変換エラーの両方が考えられるが、ConvertExceptionとする。
			throw new ConvertException("全てのPDF変換サーバで処理に失敗しました。");
		} catch(IllegalArgumentException e) {
			throw new SystemException("PDF変換サーバURLの取得に失敗しました。");
		}
	}

	/**
	 * 事業区分より、PDF出力に必要な設定テンプレート情報を取得する。
	 * @param jigyoKubun	事業区分
	 * @return				設定情報文字列
	 */
	private String getSettingTemplate(String jigyoKubun) {
		synchronized (lock) {
			//既に読み込まれている場合。
			if (iodSettings.containsKey(jigyoKubun)) {
				return (String) iodSettings.get(jigyoKubun);
			//以外
			} else {
				//2.事業区分に一致するマッチングXMLファイルを取得する(１つ)
				String settingFilePath =
					MessageFormat.format(
						REPORT_SETTING_FILE_PATH,
						new Object[] { jigyoKubun });
				File iodSettingFile = new File(settingFilePath);

				String template = readSettingFile(iodSettingFile);
				iodSettings.put(jigyoKubun, template);
				//3.設定情報ファイルを読み込む。
				return template;
			}
		}
	}

	/**
	 * 変換設定テンプレートファイルを読み込み文字列にする。
	 * @param settingFile		変換設定テンプレートファイル
	 * @return					変換設定テンプレート
	 * @throws SystemException	変換設定テンプレートファイル読み込み中の例外
	 */
	private String readSettingFile(File settingFile) throws SystemException {
		Reader is = null;
		try {
			is = new FileReader(settingFile);
			return readerToString(is);
		} catch (IOException e) {
			throw new SystemException(
				"変換設定テンプレートファイル'" + settingFile + "'情報の読み込みに失敗しました。",
				e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new SystemException(
						"変換設定テンプレートファイル'"
							+ settingFile
							+ "'情報の読込中にIOエラーが発生しました。",
						e);
				}
			}
		}
	}

	/**
	 * 変換設定テンプレートに申請データ情報をマージし、PDF出力用設定情報XMLを作成する。
	 * @param settingInfo			変換設定テンプレート
	 * @param dataInfo				マージに使うオブジェクト
	 * @return						PDF出力用設定情報XML
	 * @throws SystemException		テンプレートのマージに失敗した場合。
	 */
	private String merge(String template, Object dataInfo)
		throws SystemException {
		try {

			/* まず実行時エンジンを初期化する。デフォルトでよい。 */
			Velocity.init();

			/* Context を作成して、データを入れる */
			VelocityContext context = new VelocityContext();
			
			//申請データ情報
			context.put("shinseiDataInfo", dataInfo);
			//ツールオブジェクト
			context.put("escape", new Escape());
			context.put("dateFormat", new DateFormat());
            context.put("shinseiFormat", new ShinseiFormat());
            //2005/04/19 追加 -----------------------------------------ここから
            //和暦変換ツール追加
			context.put("dateUtil", new DateUtil());
            //2005/04/19 追加 -----------------------------------------ここまで

			/* テンプレートを処理する */
			StringWriter writer = new StringWriter();

			/* 処理する文字列を作成する */
			Velocity.evaluate(context, writer, "iodSetting", template);
		
			return writer.getBuffer().toString();

		} catch (Exception e) {
			throw new SystemException("変換設定ファイルのマージ中にエラーが発生しました。", e);
		}
	}

	/**
	 * 指定ファイルを読み取る。
	 * @param aFile					読み込むファイル
	 * @return						読み込んだファイルリソース
	 * @throws ConvertException		ファイルの取得に失敗したとき。
	 */
	private FileResource readFile(File aFile) throws ConvertException {
		try {
			if (!aFile.exists()) {
				throw new ConvertException("対象ファイル'" + aFile + "'が見つかりません。",
                        new ErrorInfo("errors.8003"));
			}
			//添付ファイルを取得する。
			return FileUtil.readFile(aFile);
		} catch (FileNotFoundException e) {
			throw new ConvertException("対象ファイル'" + aFile + "'情報の取得に失敗しました。",
                    new ErrorInfo("errors.8003"),e);
		} catch (IOException e) {
			throw new ConvertException("対象ファイル'" + aFile + "'情報の取得に失敗しました。",
                    new ErrorInfo("errors.8003"),e);
		}
	}

	/** 使用するブロックサイズ */
	private static final int BLKSIZ = 8192;

	/**
	 * Readerの内容をStringに読み取る。
	 * @param is		Reader
	 * @return			読み取った文字列。
	 * @throws IOException
	 */
	private static String readerToString(Reader is) throws IOException {
		StringBuffer buffer = new StringBuffer();
		char[] b = new char[BLKSIZ];
		int n;
		while ((n = is.read(b)) > 0) {
			buffer.append(b, 0, n);
		}
		return buffer.toString();
	}

	//2005/05/24 追加　ここから---------------------------------------------------
	//表紙PDF作成のため
	/**
	 * 表紙PDFデータ情報を取得し、表紙PDFデータをIODに変換し、
	 * IODをPDFに変換し、PDFファイルパスをDBに書き込む。
	 * 
	 * @param connection	Connection
	 * @param userInfo	UserInfo
	 * @param pkInfo	CheckListSearchInfo
	 * @throws ApplicationException		変換に失敗したとき。
	 * @throws DataAccessException		
	 * @throws IOException
	 */
	public void convertHyoshiData(
            Connection connection,
            UserInfo userInfo,
            CheckListSearchInfo checkInfo)
            throws ApplicationException, DataAccessException, IOException {
	
		//----------------------------
		//表紙PDFデータ情報の取得
		//----------------------------
		CheckListInfo checkListInfo = null;
		try{
			checkListInfo = new CheckListInfoDao(userInfo).selectPdfData(connection, checkInfo);
		}catch(DataAccessException e){
			throw new ConvertException("表紙PDFデータの取得に失敗しました。(データアクセス)",
                    new ErrorInfo("errors.8003"),e);
		}

		//----------------------------
		//保存ファイルの作成(PDF)
		//----------------------------
		File iodFile = new File(MessageFormat.format(PDF_COVER, 
                new Object[] {checkInfo.getJigyoId(),checkInfo.getShozokuCd()}));
		if(log.isDebugEnabled()){
			log.debug("表紙PDFファイルは'" + iodFile + "'です。");
		}
       		
		//----------------------------
		//DB更新
		//----------------------------
		new CheckListInfoDao(userInfo).updateFilePath(connection, checkInfo, iodFile);

		//----------------------------
		//表紙PDFデータ情報の変換(PDF)
		//----------------------------
		FileResource pdfFileResource = makeIodFormHyoshiData(userInfo, checkListInfo);
	
		//----------------------------
		//PDFファイル書込(毎回更新するため上書きのみ)
		//----------------------------
		FileUtil.writeFile(iodFile, pdfFileResource.getBinary());
		pdfFileResource = null;	
	}
    
//   2006/06/29 dyh add start
    /**
     * 領域計画書表紙PDFデータよりPDFファイルを作成する。
     * IODをPDFに変換し、PDFファイルパスをDBに書き込む。
     * 
     * @param connection Connection
     * @param userInfo UserInfo
     * @param ryoikiSystemNo システム受付番号
     * @throws ApplicationException 変換に失敗したとき。
     * @throws ConvertException      
     * @throws IOException
     */
    public void convertGaiyoHyoshiPdf(
            Connection connection,
            UserInfo userInfo,
            String ryoikiSystemNo)
            throws ConvertException, IOException, ApplicationException {
    
        //----------------------------
        //領域計画書表紙PDFデータ情報の取得
        //----------------------------
        RyoikiKeikakushoInfo pdfInfo = null;
        RyoikiKeikakushoInfoDao ryoikiKeikakushoInfoDao =
            new RyoikiKeikakushoInfoDao(userInfo);
        try{
            pdfInfo = ryoikiKeikakushoInfoDao
                    .selectGaiyoHyoshiPdfData(connection, ryoikiSystemNo);
        }catch(DataAccessException e){
            throw new ConvertException(
                    "領域計画書表紙PDFデータの取得に失敗しました。(データアクセス)",
                    new ErrorInfo("errors.8003"),e);
        }

        //----------------------------
        //保存ファイルの作成(PDF)
        //----------------------------
        File iodFile = new File(MessageFormat.format(PDF_DOMAINCOVER,
                new Object[] {pdfInfo.getJigyoId(),pdfInfo.getKariryoikiNo()}));
        if(log.isDebugEnabled()){
            log.debug("領域計画書表紙PDFファイルは'" + iodFile + "'です。");
        }
            
        //----------------------------
        //DB更新
        //----------------------------
        try{
            ryoikiKeikakushoInfoDao.updateHyoshiPdfPath(connection,
                    ryoikiSystemNo, iodFile);
        }catch(DataAccessException e){
            throw new ConvertException(
                    "領域計画書表紙PDFデータの取得に失敗しました。(データアクセス)",
                    new ErrorInfo("errors.4002"),e);
        }

        //----------------------------
        //領域計画書表紙PDFデータ情報の変換(PDF)
        //----------------------------
        FileResource pdfFileResource = makeIodFormGaiyoHyoshi(userInfo, pdfInfo,
                HYOSHIRYOIKI_PDF_TEMPLATE_NAME);
        
        //----------------------------
        //PDFファイル書込(毎回更新するため上書きのみ)
        //----------------------------
        FileUtil.writeFile(iodFile, pdfFileResource.getBinary());
        pdfFileResource = null;
    }

    /**
     * 領域計画書表紙PDFデータ情報よりiodファイルを作成し、
     * makeIodFormData()メソッドを呼び出して、
     * getHyoshiFileResource()メソッドを呼び出して、
     * iodファイルからPDFファイルを作成する。
     * 
     * @param UserInfo ユーザ情報
     * @param pdfInfo 領域計画書表紙PDFオブジェクト
     * @return FileResource PDFファイル
     * @throws ApplicationException 
     */
    private FileResource makeIodFormGaiyoHyoshi(UserInfo userInfo,
            RyoikiKeikakushoInfo pdfInfo,String pdfTemplateName)
            throws ApplicationException {

        //該当する設定情報を取得する
        String template = getSettingTemplate(pdfTemplateName);
        
        //テンプレートを領域計画書表紙PDFデータを使用して変換する。
        String iodSetting = merge(template, pdfInfo);

        //設定ファイルの読込み
        IodSettings settings = new IodSettings(new StringReader(iodSetting));

        //設定情報オブジェクトの作成
        List iodSettingInfo = settings.getContents();

        if (log.isDebugEnabled()) {
            log.debug(
                ">>IODファイル作成サービスを呼び出します。");
        }

        FileResource iodFileResource = null;
        try {
            String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
            iodFileResource = getIodFileResourceHyoshi(urls, iodSettingInfo);

            //下でiodFileResourceを引数として使用するので、ここでConvertExceptionは投げない。
            //上のforループの最後のcatchでConvertExceptionを投げる。
        } catch(IllegalArgumentException e) {
            throw new SystemException("PDF変換サーバURLの取得に失敗しました。");
        }

        // PDFファイルの取得(パスワードを指定しないようにfalseを設定)
        FileResource pdfFileResource = getHyoshiFileResource(userInfo, iodFileResource, false);
        return pdfFileResource;
    }

//    /**
//     * PDFデータ情報よりiodファイルを作成し
//     * 
//     * @param pdfInfo PDFオブジェクト
//     * @param pdfTemplateName 変換設定テンプレート
//     * @return FileResource PDFファイル
//     * @throws ApplicationException 
//     */
//    private FileResource makeIodFormData(
//            Object pdfInfo,
//            String pdfTemplateName)
//            throws ApplicationException {
//
//        //該当する設定情報を取得する
//        String template = getSettingTemplate(pdfTemplateName);
//        
//        //テンプレートを領域計画書表紙PDFデータを使用して変換する。
//        String iodSetting = merge(template, pdfInfo);
//
//        //設定ファイルの読込み
//        IodSettings settings = new IodSettings(new StringReader(iodSetting));
//
//        //設定情報オブジェクトの作成
//        List iodSettingInfo = settings.getContents();
//
//        if (log.isDebugEnabled()) {
//            log.debug(
//                ">>IODファイル作成サービスを呼び出します。");
//        }
//
//        FileResource iodFileResource = getIodFileResource(iodSettingInfo);
//
//        return iodFileResource;
//    }
// 2006/06/29 dyh add end

    //2006/07/03 zhangt add start
    /**
     * 領域計画書概要PDFデータよりPDFファイルを作成する。
     * IODをPDFに変換し、PDFファイルパスをDBに書き込む。
     * 
     * @param connection Connection
     * @param userInfo UserInfo
     * @param ryoikiSystemNo システム受付番号
     * @throws ApplicationException 変換に失敗したとき。
     * @throws ConvertException      
     * @throws IOException
     */
    public void convertRyoikiGaiyoPdf(UserInfo userInfo, RyoikiKeikakushoPk ryoikiKeikakushoPk)
            throws ApplicationException {
    
        //----------------------------
        //領域計画書表紙PDFデータ情報の取得
        //----------------------------
        Connection connection = null;
        boolean success = false;
        try {
            connection = DatabaseUtil.getConnection();

            //------------------
            //申請データXML/PDF変換
            //------------------
            convertRyoikiKeikakushoData(connection, userInfo, ryoikiKeikakushoPk);

            if (log.isDebugEnabled()) {
                log.debug("-->>添付ファイルを変換します。");
            }
            //------------------
            //添付ファイルPDF変換
            //------------------
            convertRyoikiKeikakushoTenpuFile(connection, userInfo, ryoikiKeikakushoPk);

            if (log.isDebugEnabled()) {
                log.debug("--<<添付ファイルを変換します。");
            }
            //------------------
            //登録正常終了
            //------------------
            success = true;         
    
        } catch (DataAccessException e) {
            throw new ConvertException("申請データ情報の取得に失敗しました。",
                    new ErrorInfo("errors.8003"),e);
        } catch (IOException e) {
            throw new ConvertException("変換ファイルの保存に失敗しました。(ファイルIO)",
                    new ErrorInfo("errors.8003"),e);
        } finally {
            try {
                if (success) {
                    DatabaseUtil.commit(connection);
                } else {
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ConvertException("PDFデータ変換DB登録中にエラーが発生しました。",e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
 
    /**
     * 申請データ情報を取得し、申請データをPDFに変換し、保存ファイルパスをDBに書き込む。
     * @param connection
     * @param userInfo
     * @param pkInfo
     * @throws ApplicationException     変換に失敗したとき。
     * @throws DataAccessException      
     * @throws IOException
     */
    private void convertRyoikiKeikakushoData(
            Connection connection,
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiKeikakushoPk)
            throws
                ApplicationException,
                DataAccessException,
                IOException {
        
        //----------------------------
        //申請データ情報の取得
        //----------------------------
        RyoikiKeikakushoInfo ryoikiKeikakushoInfo = null;
        try {
            ryoikiKeikakushoInfo = new ShinseiMaintenance()
                    .selectRyoikiKeikakushoInfo(userInfo, ryoikiKeikakushoPk);
        } catch (ApplicationException e) {
            throw new ConvertException("申請データの取得に失敗しました。(データアクセス)",
                    new ErrorInfo("errors.8003"),e);
        }
        //----------------------------
        //保存ファイルの作成(PDF)
        //----------------------------
        File iodFile = new File(MessageFormat.format(SHINSEI_PDF_FOLDER,
                new Object[] { ryoikiKeikakushoInfo.getJigyoId() + "_RG",
                        ryoikiKeikakushoInfo.getRyoikiSystemNo() }));
        if (log.isDebugEnabled()) {
            log.debug("申請データPDF変換ファイルは'" + iodFile + "'です。");
        }

        //----------------------------
        //保存ファイルの作成(XML)
        //----------------------------
        File xmlFile = new File(MessageFormat.format(SHINSEI_XML_FOLDER,
                new Object[] { ryoikiKeikakushoInfo.getJigyoId(),
                        ryoikiKeikakushoInfo.getRyoikiSystemNo() }));
        if (log.isDebugEnabled()) {
            log.debug("申請データXML変換ファイルは'" + xmlFile + "'です。");
        }
                
        //----------------------------
        //DB更新
        //----------------------------
        new RyoikiKeikakushoInfoDao(userInfo).updatePdfPath(connection,
                ryoikiKeikakushoInfo.getRyoikiSystemNo(), iodFile);

        //----------------------------
        //申請データ情報の変換(PDF)
        //----------------------------
        FileResource iodFileResource = makeIodFormRyoikiKeikakushoInfo(ryoikiKeikakushoInfo);
        
        //----------------------------
        //PDFファイル書込(毎回更新するため上書きのみ)
        //----------------------------
        FileUtil.writeFile(iodFile,iodFileResource.getBinary());
        iodFileResource = null;

        //----------------------------
        //申請データ情報のXML変換処理
        //----------------------------
        String xmlString = makeXmlFormRyoikiKeikakushoInfo(ryoikiKeikakushoInfo);

//      if (log.isDebugEnabled()) {
//          log.debug("XMLの表示\n" + xmlString);
//      }

        //----------------------------
        //XMLファイルUTF-8で書込(毎回更新するため上書きのみ)
        //----------------------------
        FileUtil.writeFile(xmlFile, xmlString.getBytes("UTF-8"));
    }

    /**
     * 申請データ情報よりpdfファイルを作成する。
     * @param connection
     * @param shinseiDataInfo       申請データオブジェクト
     * @return                      pdfファイル
     * @throws ApplicationException 
     * @throws ConvertException     pdfファイル作成時に例外が発生した場合。
     */
    private FileResource makeIodFormRyoikiKeikakushoInfo(RyoikiKeikakushoInfo ryoikiKeikakushoInfo)
        throws ApplicationException {
        
        //様式種別
        String jigyoKubun = "02_RG";
        
        String template = getSettingTemplate(jigyoKubun);
        
        //テンプレートを領域計画書表紙PDFデータを使用して変換する。
        String iodSetting = merge(template, ryoikiKeikakushoInfo);

        //設定ファイルの読込み
        IodSettings settings = new IodSettings(new StringReader(iodSetting));

        //設定情報オブジェクトの作成
        List iodSettingInfo = settings.getContents();

        if (log.isDebugEnabled()) {
            log.debug(
                ">>IODファイル作成サービスを呼び出します。");
        }
        try {
            String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
            return getIodFileResourceShinsei(urls, iodSettingInfo);
        } catch(IllegalArgumentException e) {
            throw new SystemException("PDF変換サーバURLの取得に失敗しました。");
        }
    }

    /**
     * 申請データ情報よりXMLファイルを作成する。
     * @param shinseiDataInfo 申請データオブジェクト
     * @return String         XML文字列
     */
    private String makeXmlFormRyoikiKeikakushoInfo(RyoikiKeikakushoInfo ryoikiKeikakushoInfo) {
        synchronized (lock) {
            //テンプレート情報の確認            
            if (xmlTemplate == null) {
                //テンプレートの取得
                File xmlTemplateFile =
                    ApplicationSettings.getFile(
                        ISettingKeys.SHINSEI_XML_TEMPLATE);
                xmlTemplate = readSettingFile(xmlTemplateFile);
            }
            //テンプレートを申請データを使用して変換する。
            return merge(xmlTemplate, ryoikiKeikakushoInfo);
        }
    }

    /**
     * 領域計画書（概要）情報より、添付ファイル情報を取得し、添付ファイルをPDFに変換し、保存ファイルパスをDBに書き込む。
     * @param connection
     * @param userInfo
     * @param ryoikiKeikakushoPk
     * @throws DataAccessException
     * @throws ApplicationException
     * @throws IOException
     */
    private void convertRyoikiKeikakushoTenpuFile(
            Connection connection,
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiKeikakushoPk)
            throws DataAccessException, ApplicationException, IOException {

        try{
            //----------------------------
            //申請データの添付ファイル情報の取得
            //----------------------------
            TenpuFileInfoDao dao = new TenpuFileInfoDao(userInfo);
            TenpuFileInfo[] fileInfos = dao.selectTenpuFiles(connection,ryoikiKeikakushoPk);
            
            for (int i = 0; i < fileInfos.length; i++) {
                //----------------------------
                //変換されていないときは
                //----------------------------
                if(!isConverted(fileInfos[i])){
                    
                    //----------------------------
                    //変換する。                     
                    //----------------------------
                    File wordFile = new File(fileInfos[i].getTenpuPath());
                    FileResource pdfFileResource = annexFileConvert(readFile(wordFile));
                    
                    //----------------------------
                    //添付フォルダにファイル書込
                    //----------------------------
                    File pdfFile = new File(wordFile.getParentFile(),pdfFileResource.getName());
                    fileInfos[i].setPdfPath(pdfFile.getAbsolutePath());
                    FileUtil.writeFile(pdfFile,pdfFileResource.getBinary());
                    
                    //----------------------------
                    //DB更新
                    //----------------------------
                    dao.updateTenpuFileInfo(connection,fileInfos[i]);
                }
            }
        }catch(NoDataFoundException e){
            if(log.isDebugEnabled()){
                log.debug("添付ファイルがありません。",e);
            }
        }catch(ConvertException e){
            //PDF変換処理のタイムアウトの場合は、アラートメールを送信する。
            if("errors.8001".equals(e.getErrorCode())){
                //アラート用なのでベタ書き。
                String SMTP_SERVER_ADDRESS = ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS);
                String FROM_ADDRESS = ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);
                String to = ApplicationSettings.getString(ISettingKeys.TO_ADDRESS_FOR_ALERT);;
                String subject = "【日本学術振興会電子申請システム】アラート通知";
                String content = "PDF変換処理でタイムアウトが発生しました。\n"
                                +"・ユーザID："+userInfo.getId()+"\n"
                                +"・申請書（システム受付番号）："+ryoikiKeikakushoPk.getRyoikiSystemNo()+"\n"
                                +"・発生日時："+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date())+"\n"
                                ;
                //-----メール送信
                try{
                    SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
                    mailer.sendMail(FROM_ADDRESS,           //差出人
                                    to,                     //to
                                    null,                   //cc
                                    null,                   //bcc
                                    subject,                //件名
                                    content);               //本文
                }catch(Exception ex){
                    log.warn("メール送信に失敗しました。",ex);
                    return;
                }
            }
            throw e;
        }
    }
    //2006/07/03 zhangt add end

	/**
	 * 表紙PDFデータ情報よりiodファイルを作成し、
	 * getHyoshiFileResource()メソッドを呼び出して、iodファイルからPDFファイルを作成する。
	 * 
	 * @param UserInfo				ユーザ情報
	 * @param checkInfo			表紙PDFオブジェクト
	 * @return						PDFファイル
	 * @throws ApplicationException	
	 */
	private FileResource makeIodFormHyoshiData(UserInfo userInfo, CheckListInfo checkInfo)
		throws ApplicationException {

		//該当する設定情報を取得する
		String template = getSettingTemplate(HYOSHI_PDF_TEMPLATE_NAME);
		
		//テンプレートを表紙PDFデータを使用して変換する。
		String iodSetting = merge(template, checkInfo);

		//設定ファイルの読込み
		IodSettings settings = new IodSettings(new StringReader(iodSetting));

		//設定情報オブジェクトの作成
		List iodSettingInfo = settings.getContents();

		if (log.isDebugEnabled()) {
			log.debug(
				">>IODファイル作成サービスを呼び出します。");
		}

		//IODファイル作成サービス呼び出し
		//2006.07.03 iso PDF変換サーバ振り分け処理のため追加
//		ISystemServise servise =
//			SystemServiceFactory.getSystemService(
//				IServiceName.CONVERT_SERVICE,
//				SERVER_URL);
//
//		//IODファイル取得
//		FileResource iodFileResource = servise.iodFileCreation(iodSettingInfo);
//
//		//########## DEBUG ##########		
//		if (log.isDebugEnabled()) {
//			log.debug(
//				">>作成IODファイル名 '"
//					+ iodFileResource.getName()
//					+ " サイズ '"
//					+ iodFileResource.getBinary().length
//					+ "'");
//		}

// 2006/07/19 dyh update start 原因：共通方法を作成
		FileResource iodFileResource = null;
		try {
			String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
//			for(int i=0; i < urls.length; i++) {
//				ISystemServise servise =
//					SystemServiceFactory.getSystemService(
//						IServiceName.CONVERT_SERVICE,
//						urls[i]);
//				try{
//					iodFileResource = servise.iodFileCreation(iodSettingInfo);
//					if (log.isDebugEnabled()) {
//						log.debug(
//								">>作成IODファイル名 '"
//								+ iodFileResource.getName()
//								+ " サイズ '"
//								+ iodFileResource.getBinary().length
//								+ "'");
//					}
//					break;
//				} catch (Exception e) {
//					log.info("PDF変換サーバでエラーが発生しました。URL:" + urls[i], e);
//					if(i == urls.length - 1) {
//						//全サーバ分のループが終わってもpdfFileResourceが取得できない場合、
//						//接続エラー、変換エラーの両方が考えられるが、ConvertExceptionとする。
//						throw new ConvertException("全てのPDF変換サーバで処理に失敗しました。");
//					}
//				}
//			}
            iodFileResource = getIodFileResourceHyoshi(urls, iodSettingInfo);

			//下でiodFileResourceを引数として使用するので、ここでConvertExceptionは投げない。
			//上のforループの最後のcatchでConvertExceptionを投げる。
		} catch(IllegalArgumentException e) {
			throw new SystemException("PDF変換サーバURLの取得に失敗しました。");
		}
// 2006/07/19 dyh update end

		//PDFファイルの取得(パスワードを指定しないようにfalseを設定)
		FileResource pdfFileResource = getHyoshiFileResource(userInfo, iodFileResource, false);
		
		return pdfFileResource;
	}

	/**
	 * 表紙PDFデータよりPDFファイルを作成する。
	 * lockFlagがtrueのとき、作成されたPDFファイルにパスワードロックをかける。
	 * 
	 * @param userInfo		ユーザ情報
	 * @param file			iodファイルリソース
	 * @param lockFlag		パスワードロックフラグ
	 * @return	PDFファイルリソース
	 * @throws ApplicationException
	 * @throws ConvertException
	 */
	public FileResource getHyoshiFileResource(
		UserInfo userInfo,
		FileResource file, 
		boolean lockFlag)
		throws ApplicationException, ConvertException {

//		FileResource resource = null; 
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			List iodFileResource = new ArrayList();
			iodFileResource.add(file);
	
			//----------------------------
			//パスワード
			//----------------------------
			String password = null;
			if(lockFlag){
				password = getPassword(connection, userInfo);
			}
			
			if(log.isDebugEnabled()){
				log.debug("--->>PDFを作成します。");
			}
						
			//----------------------------
			//変換
			//----------------------------
			//2006.07.03 iso PDF変換サーバ振り分け処理のため追加
//			ISystemServise servise =
//				SystemServiceFactory.getSystemService(
//					IServiceName.CONVERT_SERVICE,
//					SERVER_URL);
//			
//			try{
//				resource =  servise.iodToPdf(iodFileResource,password);
//			}finally{
//				if (log.isDebugEnabled()) {
//					log.debug("---<<PDFを作成しました。");
//				}
//			}

// 2006/07/19 dyh update start 原因：共通方法を作成
//			try {
//				String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
//				for(int i=0; i < urls.length; i++) {
//					ISystemServise servise =
//						SystemServiceFactory.getSystemService(
//							IServiceName.CONVERT_SERVICE,
//							urls[i]);
//					try{
//						FileResource resource = servise.iodToPdf(iodFileResource,password);
//						if (log.isDebugEnabled()) {
//							log.debug("---<<PDFを作成しました。");
//						}
//						return resource;
//					} catch (Exception e) {
//						log.info("PDF変換サーバでエラーが発生しました。URL:" + urls[i], e);
//					}
//				}
//				//全サーバ分のループが終わってもresourceが取得できない場合、
//				//接続エラー、変換エラーの両方が考えられるが、ConvertExceptionとする。
//				throw new ConvertException("全てのPDF変換サーバで処理に失敗しました。");
//			} catch(IllegalArgumentException e) {
//				throw new SystemException("PDF変換サーバURLの取得に失敗しました。");
//			}
            return getPdfFileResource(iodFileResource,password);
// 2006/07/19 dyh update end

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"PDFファイルの取得に失敗しました。", new ErrorInfo("errors.8003"),e);
		} finally{
			DatabaseUtil.closeConnection(connection);
		}
//		return resource;
	}
	//追加　ここまで------------------------------------------------------------

	//2005.07.15 iso PDF添付機能追加
	/**
	 * PDF添付ファイルが有効かチェックする。
	 * 無効な場合は、エラーを返す。
	 * @param fileRes				添付ファイルリソース	
	 */
	public int  checkPdf(FileResource fileRes) throws ApplicationException {
		//2006.07.03 iso PDF変換サーバ振り分け処理のため追加
//		ISystemServise servise
//			= SystemServiceFactory.getSystemService(IServiceName.CONVERT_SERVICE, SERVER_URL);
//		return servise.checkPdf(fileRes);
		try {
			String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
			for(int i=0; i < urls.length; i++) {
				ISystemServise servise =
					SystemServiceFactory.getSystemService(
						IServiceName.CONVERT_SERVICE,
						urls[i]);
				try{
					return servise.checkPdf(fileRes);
				} catch (Exception e) {
					log.info("PDF変換サーバでエラーが発生しました。URL:" + urls[i], e);
				}
			}
			//全サーバ分のループが終わってもチェック結果が取得できない場合、
			//接続エラー、変換エラーの両方が考えられるが、ConvertExceptionとする。
			throw new ConvertException("全てのPDF変換サーバで処理に失敗しました。");
		} catch(IllegalArgumentException e) {
			throw new SystemException("PDF変換サーバURLの取得に失敗しました。");
		}
	}

// 2006/07/20 dyh delete start 原因：getSortedUrls方法修正後、使用しない。
//	//2006.07.03 iso PDF変換サーバ振り分けのために追加
//	/**
//	 * 文字配列をList型にDEEPコピーする。
//	 * 文字配列が空ならnullを返す。
//	 * @param strings	文字配列
//	 * @return 文字オブジェクトリスト
//	 */
//	private List deepCopyArrays2List(String[] strings) {
//		
//		if(strings == null) {
//			return null;
//		}
//		
//		List list = new LinkedList();
//		for(int i = 0; i < strings.length; i++) {
//			list.add(strings[i]);
//		}
//		return list;
//	}
// 2006/07/20 dyh delete end

	//2006.07.03 iso PDF変換サーバ振り分けのために追加
	/**
	 * URL文字配列をランダムにソート（重み付けの影響を受ける）して返す。
	 * @param serverUrls	URL文字配列
	 * @param serverUrls	URL重み付け文字配列
	 * @return ソート済みURL文字配列
	 * @throws IllegalArgumentException  URL文字配列、URL重み付け文字配列が不正な場合
	 */
	private String[] getSortedUrls(String[] serverUrls, String[] serverWeights)
            throws IllegalArgumentException {

		//URL文字配列が1個の場合は重み等は関係なく、無条件にそのまま返す。
		if(serverUrls.length == 1) {
			return serverUrls;
		}
		
		//2つの配列の長さが0か、異なる場合エラーとする。
		if(serverUrls.length == 0 || serverUrls.length != serverWeights.length) {
			throw new IllegalArgumentException("サーバURL設定文字列が不正です");
		}

// 2006/07/20 dyh update start 原因：IndexOutOfBoundsExceptionがあるので
//		//重みが0以上のURLのみソート対象とする。
//		List urlList = deepCopyArrays2List(serverUrls);			//ソート対象URLリスト。このリストが空になるまでループを繰り返す。
//		List weightList = deepCopyArrays2List(serverWeights);	//ソート対象URL重みリスト。0以上の数値（に変換出来る文字列）を格納。
//		
//		for(int i = 0; i < serverUrls.length; i++) {
//			if(StringUtil.parseInt(serverWeights[i]) <= 0) {
//				//重みが0以下、数値以外のURLをソート対象から除外する。
//				urlList.remove(i);
//				weightList.remove(i);
//			}
//		}
        // 重みが0以上のURLのみソート対象とする。
        List urlList = new ArrayList(serverUrls.length);         //ソート対象URLリスト。このリストが空になるまでループを繰り返す。
        List weightList = new ArrayList(serverWeights.length);   //ソート対象URL重みリスト。0以上の数値（に変換出来る文字列）を格納。
        
        for(int i = 0; i < serverUrls.length; i++) {
            if(StringUtil.parseInt(serverWeights[i]) > 0) {
                //重みが0以下、数値以外のURLをソート対象から除外する。
                urlList.add(serverUrls[i]);
                weightList.add(serverWeights[i]);
            }
        }
// 2006/07/20 dyh update start

		int maxListSize = urlList.size();						//ソート対象リストサイズ。重みが不正な場合を除いた初期値。

		String[] sortedUrls = new String[maxListSize];			//ソート済み配列を格納する
		
		for(int i = 0; i < maxListSize; i++) {
			int totalWeight = 0;								//ソート対象リストの全重みの合計
			int tmpListSize = urlList.size();					//ソート対象リストのサイズ。下の処理でリストのサイズが0になるまで減り続ける。
			double[] ruisekiWeight = new double[tmpListSize];	//各重みの累積
			
			for(int j = 0; j < tmpListSize; j++) {
				int weight = Integer.parseInt(weightList.get(j).toString());
				totalWeight += weight;
				ruisekiWeight[j] = totalWeight;
			}

			Random random = new Random();
			int selectNum = random.nextInt(totalWeight);
			
			for(int j = 0; j < tmpListSize; j++) {
				if(ruisekiWeight[j] > selectNum) {
					sortedUrls[i] = urlList.get(j).toString();	//ヒットしたURL文字列をソート済み配列に格納し、
					urlList.remove(j);							//ヒットしたURLはソート対象URLリストから削除する。
					weightList.remove(j);						//ヒットしたURLの重みも対象リストから削除する。
					break;
				}
			}
		}
		
		return sortedUrls;
	}

//2006/07/17　苗　追加ここから------------------------------------------------------------------

    /* (非 Javadoc)
     * @see jp.go.jsps.kaken.model.pdf.IPdfConvert#getShinseiFileResource(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
     */
    public FileResource getGaiyouResource(UserInfo userInfo, final RyoikiKeikakushoPk pkInfo)
            throws ApplicationException, NoDataFoundException, ConvertException {
        return getGaiyouFileResource(userInfo, pkInfo, true);
    }
    
    /**
     * 領域計画書（概要）情報よりPDFファイルを作成する。 lockFlagがtrueのとき、作成されたPDFファイルにパスワードロックをかける。
     * 
     * @param userInfo
     * @param pkInfo
     * @param lockFlag
     * @return
     * @throws ApplicationException
     * @throws NoDataFoundException
     * @throws ConvertException
     */
    public FileResource getGaiyouFileResource(UserInfo userInfo, final RyoikiKeikakushoPk pkInfo,
            boolean lockFlag) throws ApplicationException, NoDataFoundException, ConvertException {

        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();

            //----------------------------
            //結合するファイルの取得
            //----------------------------
            List iodFiles  = null;
            try{
                iodFiles = new RyoikiKeikakushoInfoDao(userInfo,dbLink).getIodFilesToMerge(connection,pkInfo);
            }catch(NoDataFoundException e){
                try{
                    //もしもiodファイルパスが存在しなかった場合は再度変換処理をかけ、その上で再試行を行う（１回だけ）
                    convertRyoikiGaiyoPdf(userInfo, pkInfo);
                    iodFiles = new RyoikiKeikakushoInfoDao(userInfo,dbLink).getIodFilesToMerge(connection,pkInfo);
                }catch(Exception ex){
                    //それでも失敗した場合は上位側に例外を投げる
                    throw e;
                }
            }
            List iodFileResource = new ArrayList();
            
            //----------------------------
            //DBリンクを考慮してファイル読込み
            //----------------------------
            for (Iterator iter = iodFiles.iterator(); iter.hasNext();) {
                File element = (File) iter.next();
                File targetFile = null;
                if (dbLink == null || dbLink.length() == 0) {
                    //通常
                    targetFile = element;
                } else {
                    targetFile =
                        new File(StringUtil.substrReplace(element.getAbsolutePath(),
                                DRIVE_LETTER_CONVERTED_TO_UNC,HOKAN_SERVER_UNC));
                    //パス文字列をUNC形式に変換する
                    if(log.isDebugEnabled()){
                        log.debug("dbLink経由のため、'"
                                + element
                                + "'→'"
                                + targetFile
                                + "'ファイルを読み込みます。");
                    }
                }
                iodFileResource.add(readFile(targetFile));
            }
            
            //----------------------------
            //パスワード
            //----------------------------
            String password = null;
            if(lockFlag){
                password = getPassword(connection,userInfo);
            }
            
            if(log.isDebugEnabled()){
                log.debug("--->>PDFを作成します。");
            }

            return getPdfFileResource(iodFileResource, password);

        } catch (DataAccessException e) {
            throw new ApplicationException(
                "PDFファイルの取得に失敗しました。", new ErrorInfo("errors.8003"),e);
        } finally{
            DatabaseUtil.closeConnection(connection);
        }
    }
//  2006/07/17 苗 追加ここまで------------------------------------------------------------------

    //2006.09.29 iso 「概要」つきPDFを作成し、指定フォルダに書き込む
    /**
     * ファイルリストを指定フォルダに書き込む。
     * 
     * @param connection
     * @param userInfo
     * @param fileList
     * @param outFile
     * @return
     * @throws ApplicationException
     * @throws NoDataFoundException
     * @throws ConvertException
     */
    public void writeGaiyoFileResource(
    		Connection connection, UserInfo userInfo, FileResource fileResource, List fileList, File outFile)
    		throws ApplicationException, NoDataFoundException, ConvertException {

        //テスト用。
        //DBに値があって、ローカルマシンにファイルがない場合、エラーになる。
        //エラーとなる環境で、エラーを無視したい場合、trueとする。
    	//boolean debugFlg = true;		//2006/10/3 エラーログファイルへ出力とする
    	
        //----------------------------
        //パスワード
        //----------------------------
        String password = null;

        List fileResourceList = new ArrayList();
        for (Iterator iter = fileList.iterator(); iter.hasNext();) {
            File element = (File) iter.next();
        	try {
                fileResourceList.add(readFile(element));
        	}
        	catch(ConvertException e) {
//2006/10/3
//	            if(debugFlg) {
//	            	log.info("以下の元ファイルが見つかりませんが無視します：" + outFile);
//	            } else {
//	    			throw new ApplicationException("以下の元ファイルが見つかりません：" + outFile);
//	            }
    			throw new NoDataFoundException("以下の元ファイルが見つかりません：" + outFile);
        	}
        }
        fileResourceList.add(0, fileResource);
        
        if(log.isDebugEnabled()){
            log.debug("--->>PDFを作成します。");
        }
        FileResource writeResource = getPdfFileResource(fileResourceList, password);

        try {
            FileUtil.writeFile(outFile, writeResource.getBinary());
        } catch (IOException e) {
            throw new ConvertException("ファイルの保存に失敗しました。(ファイルIO)",
                    new ErrorInfo("errors.8003"),e);
        } 
    }
    
    /**
     * PDFに変換したデータを取得する（元の使い方）
     * @param iodFileResources
     * @param password
     * @return FileResource
     * @throws ConvertException
     */
    private FileResource getPdfFileResource(List iodFileResources, String password)
            throws ConvertException, SystemException {
    	return getPdfFileResource(iodFileResources, password, null, null);
    }
    
    // 2006/07/19 dyh add start
    /**
     * PDFに変換したデータを取得する（背景出力の使い方）2007/6/15追加
     * @param iodFileResources
     * @param password
     * @param jokyoId    申請状況ID
     * @param jigyoKbn    事業ID
     * @return FileResource
     * @throws ConvertException
     */
    private FileResource getPdfFileResource(
    		List iodFileResources,	String password, 
    		String jokyoId,    		String jigyoKbn)
            throws ConvertException, SystemException {

        try {
            String[] urls = getSortedUrls(SERVER_URLS, SERVER_WEIGHTS);
            for(int i=0; i < urls.length; i++) {
                ISystemServise servise =
                    SystemServiceFactory.getSystemService(
                        IServiceName.CONVERT_SERVICE,
                        urls[i]);
                try{
                	//2007/6/15 すかし文字出力
                    //FileResource fileResource = servise.iodToPdf(iodFileResources,password);
                    FileResource fileResource = servise.iodToPdf(iodFileResources,password, jokyoId, jigyoKbn);
                    if (log.isDebugEnabled()) {
                        log.debug("---<<PDFを作成しました。");
                    }
                    return fileResource;
                } catch (Exception e) {
                    log.info("PDF変換サーバでエラーが発生しました。URL:" + urls[i], e);
                }
            }
            //全サーバ分のループが終わってもfileResourceが帰らない場合、
            //接続エラー、変換エラーの両方が考えられるが、ConvertExceptionとする。
            throw new ConvertException("全てのPDF変換サーバで処理に失敗しました。");
        } catch(IllegalArgumentException e) {
            throw new SystemException("PDF変換サーバURLの取得に失敗しました。");
        }
    }

    /**
     * 設定情報に基づきIODファイルを取得する
     * @param iodSettingInfo
     * @return
     */
    private FileResource getIodFileResourceHyoshi(String[] urls, List iodSettingInfo)
            throws ConvertException, SystemException {

        FileResource iodFileResource = null;
        for(int i=0; i < urls.length; i++) {
            ISystemServise servise =
                SystemServiceFactory.getSystemService(
                    IServiceName.CONVERT_SERVICE,
                    urls[i]);
            try{
                iodFileResource = servise.iodFileCreation(iodSettingInfo);
                if (log.isDebugEnabled()) {
                    log.debug(
                            ">>作成IODファイル名 '"
                            + iodFileResource.getName()
                            + " サイズ '"
                            + iodFileResource.getBinary().length
                            + "'");
                }
                break;
            } catch (Exception e) {
                log.info("PDF変換サーバでエラーが発生しました。URL:" + urls[i], e);
                if(i == urls.length - 1) {
                    //全サーバ分のループが終わってもpdfFileResourceが取得できない場合、
                    //接続エラー、変換エラーの両方が考えられるが、ConvertExceptionとする。
                    throw new ConvertException("全てのPDF変換サーバで処理に失敗しました。");
                }
            }
        }
        return iodFileResource;
    }

    /**
     * 設定情報に基づきIODファイルを取得する
     * @param iodSettingInfo
     * @return
     */
    private FileResource getIodFileResourceShinsei(String[] urls, List iodSettingInfo)
            throws ConvertException {

        for(int i=0; i < urls.length; i++) {
            ISystemServise servise =
                SystemServiceFactory.getSystemService(
                    IServiceName.CONVERT_SERVICE,
                    urls[i]);
            try{
                FileResource iodFileResource = servise.iodFileCreation(iodSettingInfo);
                if (log.isDebugEnabled()) {
                    log.debug(
                    ">>作成IODファイル名 '"
                        + iodFileResource.getName()
                        + " サイズ '"
                        + iodFileResource.getBinary().length
                        + "'");
                }
                return iodFileResource;
            } catch (Exception e) {
                log.info("PDF変換サーバでエラーが発生しました。URL:" + urls[i], e);
            }
        }

        //全サーバ分のループが終わってもiodFileResourceeが取得できない場合、
        //接続エラー、変換エラーの両方が考えられるが、ConvertExceptionとする。
        throw new ConvertException("全てのPDF変換サーバで処理に失敗しました。");
    }
// 2006/07/19 dyh add end

//2006/07/21 苗　追加ここから
    /**
     * 申請データ情報、添付ファイル情報よりIODファイルを作成し、申請データ管理に登録する。(応募情報又は研究計画調書確認用)
     * @param userInfo              実行するユーザ情報。
     * @param pkInfo                申請データ主キー。
     * @throws ApplicationException 
     * @throws ConvertException     変換に失敗したとき。
     */
    public void shinseiDataConvertForConfirm(UserInfo userInfo, Connection connection,
            ShinseiDataPk pkInfo, File iodFile, File xmlFile) throws ApplicationException {
        
        try {

        	//2007.03.23 iso ページチェックエラーの時、PDFの版数等が更新されるバグ対応
//            //------------------
//            //申請データXML/PDF変換
//            //------------------
//            convertShinseiDataForConfirm(connection, userInfo, pkInfo, iodFile, xmlFile);
//
//            if (log.isDebugEnabled()) {
//                log.debug("-->>添付ファイルを変換します。");
//            }
            //------------------
            //添付ファイルPDF変換
            //------------------           
           
// 2007/02/05 張志男　 修正 ここから
            //convertShinseiTenpuFile(connection, userInfo, pkInfo);
            /** 確認完了時  */
            convertShinseiTenpuFileForConfirm(connection, userInfo, pkInfo);
//2007/02/05　張志男　 修正 ここまで          
            
            if (log.isDebugEnabled()) {
                log.debug("--<<添付ファイルを変換します。");
            }       

        	//2007.03.23 iso ページチェックエラーの時、PDFの版数等が更新されるバグ対応
            //------------------
            //申請データXML/PDF変換
            //------------------
            convertShinseiDataForConfirm(connection, userInfo, pkInfo, iodFile, xmlFile);

            if (log.isDebugEnabled()) {
                log.debug("-->>添付ファイルを変換します。");
            }
            
        } catch (DataAccessException e) {
            throw new ConvertException("申請データ情報の取得に失敗しました。",
                    new ErrorInfo("errors.8003"),e);
        } catch (IOException e) {
            throw new ConvertException("変換ファイルの保存に失敗しました。(ファイルIO)",
                    new ErrorInfo("errors.8003"),e);
        }
    }
    
    /**
     * 申請データ情報を取得し、申請データをPDFに変換し、保存ファイルパスをDBに書き込む。(応募情報又は研究計画調書確認用)
     * @param connection
     * @param userInfo
     * @param pkInfo
     * @throws ApplicationException     変換に失敗したとき。
     * @throws DataAccessException      
     * @throws IOException
     */
    private void convertShinseiDataForConfirm(
            Connection connection,
            UserInfo userInfo,
            ShinseiDataPk pkInfo,
            File iodFile,
            File xmlFile)
            throws
                ApplicationException,
                DataAccessException,
                IOException {
        
        //----------------------------
        //申請データ情報の取得
        //----------------------------
        ShinseiDataInfo shinseiDataInfo = null;
        try{
            shinseiDataInfo = new ShinseiMaintenance().selectShinseiDataInfoForConfirm(userInfo,
                    connection, pkInfo);
        }catch(ApplicationException e){
            throw new ConvertException("申請データの取得に失敗しました。(データアクセス)",
                    new ErrorInfo("errors.8003"),e);
        }
        
        if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_WAKATESTART.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(shinseiDataInfo.getJigyoCd())
                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(shinseiDataInfo.getJigyoCd())
//2007/02/06 苗　追加ここから
                ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(shinseiDataInfo.getJigyoCd())
//2007/02/06　苗　追加ここまで
//2007/03/13  劉長宇　削除 ここから
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(shinseiDataInfo.getJigyoCd())
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(shinseiDataInfo.getJigyoCd())
//                ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(shinseiDataInfo.getJigyoCd())
//2007/03/13  劉長宇　削除 ここまで
        ){
            ShinseishaPk pk= new ShinseishaPk();
            pk.setShinseishaId(shinseiDataInfo.getShinseishaId());
            ShinseishaInfo shinseishaInfo = new ShinseishaInfoDao(userInfo)
                    .selectShinseishaInfo(connection, pk);
            shinseiDataInfo.setBirthDay(shinseishaInfo.getBirthday());
        }
        
        //----------------------------
        //申請データ情報の変換(PDF)
        //----------------------------
        FileResource iodFileResource = makeIodFormShinseiData(shinseiDataInfo);
        
        //----------------------------
        //PDFファイル書込(毎回更新するため上書きのみ)
        //----------------------------
        FileUtil.writeFile(iodFile,iodFileResource.getBinary());
        iodFileResource = null;

        //----------------------------
        //申請データ情報のXML変換処理
        //----------------------------
        String xmlString = makeXmlFormShinseiData(shinseiDataInfo);

        //----------------------------
        //XMLファイルUTF-8で書込(毎回更新するため上書きのみ)
        //----------------------------
        FileUtil.writeFile(xmlFile, xmlString.getBytes("UTF-8"));
    }
    
    /**
     * 申請データ情報を取得し、申請データをPDFに変換し、保存ファイルパスをDBに書き込む。(領域計画書確認用)
     * @param connection
     * @param userInfo
     * @param pkInfo
     * @throws ApplicationException     変換に失敗したとき。
     * @throws DataAccessException      
     * @throws IOException
     */
    private void convertRyoikiKeikakushoDataForConfirm(
            Connection connection,
            UserInfo userInfo,
            File iodFile,
            RyoikiKeikakushoPk ryoikiKeikakushoPk)
            throws
                ApplicationException,
                DataAccessException,
                IOException {
        
        //----------------------------
        //申請データ情報の取得
        //----------------------------
        RyoikiKeikakushoInfo ryoikiKeikakushoInfo = null;
        try {
            ryoikiKeikakushoInfo = new ShinseiMaintenance()
                    .selectRyoikiKeikakushoInfoForConfirm(userInfo, connection, ryoikiKeikakushoPk);
        } catch (ApplicationException e) {
            throw new ConvertException("申請データの取得に失敗しました。(データアクセス)",
                    new ErrorInfo("errors.8003"),e);
        }

        //----------------------------
        //保存ファイルの作成(XML)
        //----------------------------
        File xmlFile = new File(MessageFormat.format(SHINSEI_XML_FOLDER,
                new Object[] { ryoikiKeikakushoInfo.getJigyoId(),
                        ryoikiKeikakushoInfo.getRyoikiSystemNo() }));
        if (log.isDebugEnabled()) {
            log.debug("申請データXML変換ファイルは'" + xmlFile + "'です。");
        }
                
        //----------------------------
        //申請データ情報の変換(PDF)
        //----------------------------
        FileResource iodFileResource = makeIodFormRyoikiKeikakushoInfo(ryoikiKeikakushoInfo);
        
        //----------------------------
        //PDFファイル書込(毎回更新するため上書きのみ)
        //----------------------------
        FileUtil.writeFile(iodFile,iodFileResource.getBinary());
        iodFileResource = null;

        //----------------------------
        //申請データ情報のXML変換処理
        //----------------------------
        String xmlString = makeXmlFormRyoikiKeikakushoInfo(ryoikiKeikakushoInfo);

        //----------------------------
        //XMLファイルUTF-8で書込(毎回更新するため上書きのみ)
        //----------------------------
        FileUtil.writeFile(xmlFile, xmlString.getBytes("UTF-8"));
    }

    //2006.09.15 iso タイトルに「概要」をつけたPDF作成のため
    /**
     * 申請データをPDF変換し、添付ファイルのPDFと結合する。
     * @param connection
     * @param userInfo
     * @param ryoikikeikakushoInfo
     * @throws ApplicationException     変換に失敗したとき。
     * @throws DataAccessException      
     * @throws IOException
     */
    public FileResource convertRyoikiKeikakushoGaiyo(
    		Connection connection, UserInfo userInfo, RyoikiKeikakushoInfo ryoikikeikakushoInfo)
    		throws ApplicationException{
        
    	ryoikikeikakushoInfo.setAddTitele("概要");	//特に変更されることもないだろうし、べた書き。
        
        //----------------------------
        //申請データ情報の変換(PDF)
        //----------------------------
        return makeIodFormRyoikiKeikakushoInfo(ryoikikeikakushoInfo);
        
    }
    
    /**
     * 領域計画書概要PDFデータよりPDFファイルを作成する。(領域計画書確認用)
     * IODをPDFに変換し、PDFファイルパスをDBに書き込む。
     * 
     * @param connection Connection
     * @param userInfo UserInfo
     * @param ryoikiSystemNo システム受付番号
     * @throws ApplicationException 変換に失敗したとき。
     * @throws ConvertException      
     * @throws IOException
     */
    public void convertRyoikiGaiyoPdfForConfirm(Connection connection, UserInfo userInfo, File iodFile,
            RyoikiKeikakushoPk ryoikiKeikakushoPk) throws ApplicationException {
    
        //----------------------------
        //領域計画書表紙PDFデータ情報の取得
        //----------------------------
        try {

            //------------------
            //申請データXML/PDF変換
            //------------------
            convertRyoikiKeikakushoDataForConfirm(connection, userInfo, iodFile, ryoikiKeikakushoPk);

            if (log.isDebugEnabled()) {
                log.debug("-->>添付ファイルを変換します。");
            }
            //------------------
            //添付ファイルPDF変換
            //------------------
            convertRyoikiKeikakushoTenpuFile(connection, userInfo, ryoikiKeikakushoPk);

            if (log.isDebugEnabled()) {
                log.debug("--<<添付ファイルを変換します。");
            }      
    
        } catch (DataAccessException e) {
            throw new ConvertException("申請データ情報の取得に失敗しました。",
                    new ErrorInfo("errors.8003"),e);
        } catch (IOException e) {
            throw new ConvertException("変換ファイルの保存に失敗しました。(ファイルIO)",
                    new ErrorInfo("errors.8003"),e);
        } 
    }
//2006/07/21　苗　追加ここまで        
}