/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;

/**
 * PDF変換・PDF作成を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IPdfConvert.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IPdfConvert {

	/**
	 * 申請データ情報、添付ファイル情報よりIODファイルを作成し、申請データ管理に登録する。
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				申請データ主キー。
	 * @throws ApplicationException	
	 * @throws ConvertException		変換に失敗したとき。
	 */
	public void shinseiDataConvert(
		UserInfo userInfo,
		ShinseiDataPk pkInfo)
		throws ApplicationException,ConvertException;
    
    //2004.07.03 zhangt add start
    /**
     * 領域計画書PDFデータよりPDFファイルを作成する。
     * IODをPDFに変換し、PDFファイルパスをDBに書き込む。
     * @param userInfo              実行するユーザ情報。
     * @param pkInfo                申請データ主キー。
     * @throws ApplicationException 
     * @throws ConvertException     変換に失敗したとき。
     */
    public void convertRyoikiGaiyoPdf(
            UserInfo userInfo, 
            RyoikiKeikakushoPk ryoikiKeikakushoPk)
        throws ApplicationException;
    //2004.07.03 zhangt add start
	
	/**
	 * 申請データよりPDFファイルを作成する。
	 * 作成されたPDFファイルには、ログインユーザのパスワードロックがかける。
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				申請データ主キー。
	 * @return						申請データファイルリソース
	 * @throws ApplicationException	申請データの取得に失敗した場合。
	 */
	public FileResource getShinseiFileResource(
		UserInfo userInfo,
		final ShinseiDataPk pkInfo)
		throws ApplicationException, NoDataFoundException, ConvertException;
	
	
	/**
	 * 申請データよりPDFファイルを作成する。
	 * 作成されたPDFファイルには、パスワードロックをかけない。
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				申請データ主キー。
	 * @return						申請データファイルリソース
	 * @throws ApplicationException	申請データの取得に失敗した場合。
	 */
	public FileResource getShinseiFileResourceWithoutLock(
		UserInfo userInfo,
		final ShinseiDataPk pkInfo)
		throws ApplicationException, NoDataFoundException, ConvertException;
	
	
	//2005/05/25 追加 ここから-------------------------------------------------
	//理由　PDFファイル取得のため
	
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
		throws	ApplicationException, DataAccessException, IOException;
		
	//追加 ここまで------------------------------------------------------------

// 2006/06/28 dyh add start
    /**
     * 領域計画書表紙PDFデータよりPDFファイルを作成する。
     * IODをPDFに変換し、PDFファイルパスをDBに書き込む。
     * 
     * @param connection Connection
     * @param userInfo UserInfo
     * @param ryoikiSystemNo システム受付番号
     * @throws ApplicationException 変換に失敗したとき。
     * @throws DataAccessException      
     * @throws IOException
     */
    public void convertGaiyoHyoshiPdf(
            Connection connection,
            UserInfo userInfo,
            String ryoikiSystemNo)
            throws ApplicationException, IOException;
// 2006/06/28 dyh add end

	//2005.07.15 iso PDF添付機能追加
	/**
	 * PDFファイルが有効かを調べる。
	 * @param fileRes					調査するファイルリソース。
	 * @return							有効：0　無効:エラーコード
	 * @throws ConvertException		変換中に例外が発生したとき。
	 */
	public int checkPdf(FileResource fileRes) throws ApplicationException ;
    
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
    public FileResource getGaiyouResource(UserInfo userInfo, final RyoikiKeikakushoPk pkInfo)
            throws ApplicationException, NoDataFoundException, ConvertException;
   
//2006/07/12 苗　追加ここから    
    /**
     * 申請データ情報、添付ファイル情報よりIODファイルを作成し、申請データ管理に登録する。(応募情報又は研究計画調書確認用)
     * @param userInfo              実行するユーザ情報。
     * @param pkInfo                申請データ主キー。
     * @throws ApplicationException 
     * @throws ConvertException     変換に失敗したとき。
     */
    public void shinseiDataConvertForConfirm(UserInfo userInfo, Connection connection, ShinseiDataPk pkInfo, File iodFile,
            File xmlFile) throws ApplicationException;

    /**
     * 領域計画書PDFデータよりPDFファイルを作成する。(領域計画書確認用)
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
            RyoikiKeikakushoPk ryoikiKeikakushoPk) throws ApplicationException;
//2006/07/21　苗　追加ここまで    


    //2006.09.15 iso タイトルに「概要」をつけたPDF作成のため
    /**
     * 申請データをPDF変換し、添付ファイルのPDFと結合する。
     * @param connection
     * @param userInfo
     * @param ryoikikeikakushoInfo
     * @throws ApplicationException     変換に失敗したとき。   
     * @throws IOException
     */
    public FileResource convertRyoikiKeikakushoGaiyo(
    		Connection connection, UserInfo userInfo, RyoikiKeikakushoInfo ryoikikeikakushoInfo)
    		throws ApplicationException;
    

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
    		throws ApplicationException, NoDataFoundException, ConvertException;
    
}