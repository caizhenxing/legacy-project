/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.Map;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * 審査結果情報を管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IShinsaKekkaMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IShinsaKekkaMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** 戻り値Mapキー値：1次審査結果（参照用） */
	public static final String KEY_SHINSAKEKKA_1ST = "key_shinsakekka_1st";
	
	/** 戻り値Mapキー値：2次審査結果 */
	public static final String KEY_SHINSAKEKKA_2ND = "key_shinsakekka_2nd";

	/** 戻り値Mapキー値：審査担当分一覧用（一覧データ） */
	public static final String KEY_SHINSATANTO_LIST = "key_shinsatanto_list";
	
	/** 戻り値Mapキー値：審査担当分一覧用（審査完了フラグ） */
	public static final String KEY_SHINSACOMPLETE_FLG = "key_shinsacomplete_flg";
    
//2006/10/27 苗　追加ここから
    /** 戻り値Mapキー値：利害関係入力状況一覧用（入力完了フラグ） */
    public static final String KEY_NYURYOKUCOMPLETE_FLG = "key_nyuryokucomplete_flg";
//2006/10/27　苗　追加ここまで

	/** 戻り値Mapキー値：審査担当分一覧用（総合評点リスト） */
	public static final String KEY_SOGOHYOTEN_LIST = "key_sogohyoten_list";
		
//審査項目
	public static final String	RIGAI_ON			=	"1";	//利害関係有り
	public static final String	RIGAI_OFF			=	"0";	//利害関係なし

//その他の評価項目・適切性	
	public static final String	TEKISETU_JINKEN			=	"3";	//適切性・人権「×」のとき
	public static final String	TEKISETU_BUNTANKIN		=	"3";	//適切性・分担金「×」のとき
	public static final String	TEKISETU_KEIHI			=	"3";	//適切性・研究経費「×」のとき
	
//総合評点		
	public static final String	SOGO_HYOTEN				=	"-";	//総合評点「-」のとき
		
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	
	/**
	 * 審査員向けの書類管理情報を含めた審査対象事業一覧を返す。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						取得したユーザ情報。
	 * @throws ApplicationException
	 */
	public Page getShinsaJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException;


	/**
	 * 審査員向けの審査対象事業一覧を返す。
	 * 書類管理情報は除く。
	 * 
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報。
	 * @return						取得したユーザ情報。
	 * @throws ApplicationException
	 */
	public Page searchShinsaJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException;

	/**
	 * 審査結果入力情報のファイルリソースを取得する。
	 * @param userInfo				実行するユーザ情報。
	 * @param pkInfo				検索情報。
	 * @return						検索結果ページ情報。
	 * @throws ApplicationException	
	 */
	public FileResource getHyokaFileRes(UserInfo userInfo, ShinsaKekkaPk pkInfo) throws ApplicationException;
	
	/**
	 * 審査担当分申請一覧を返す。
	 * @param userInfo
	 * @param jigyoId
	 * @param searchInfo
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map selectShinsaKekkaTantoList(UserInfo   userInfo,
											String     jigyoId,
											SearchInfo searchInfo)
		throws NoDataFoundException, ApplicationException;	
	
	/**
	 * 審査担当分申請一覧を返す。
	 * @param userInfo
	 * @param jigyoId
	 * @param kekkaTen
	 * @param searchInfo
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map selectShinsaKekkaTantoList(UserInfo userInfo,
											String jigyoId,
											String kekkaTen,
                                            String countKbn,
                                            String rigai,
											SearchInfo searchInfo)
		throws NoDataFoundException, ApplicationException;
		
	/**
	 * 1次審査結果入力情報を返す。
	 * @param userInfo
	 * @param shinsaKekkaPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekkaInputInfo select1stShinsaKekka(UserInfo userInfo,
														ShinsaKekkaPk shinsaKekkaPk)
		throws NoDataFoundException, ApplicationException;
	
	
	/**
	 * 1次審査結果を登録する。
	 * 審査結果テーブルの当該レコードを更新した後、申請データを更新する。
	 * @param userInfo
	 * @param shinsaKekkaInputInfo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void regist1stShinsaKekka(UserInfo userInfo,
										ShinsaKekkaInputInfo shinsaKekkaInputInfo)
		throws NoDataFoundException, ApplicationException;	
	
	
	/**
	 * 審査結果を返す。
	 * 1次審査結果（参照用）と2次審査結果をMap形式で返す。
	 * Mapのキーは[KEY_SHINSAKEKKA_1ST], [KEY_SHINSAKEKKA_2ND]となる。
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map getShinsaKekkaBoth(UserInfo userInfo,
								   ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;

	/**
	 * 1次審査結果（参照用）を返す。
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekkaReferenceInfo getShinsaKekkaReferenceInfo(UserInfo userInfo,
																 ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;

	/**
	 * 2次審査結果を返す。
	 * @param userInfo
	 * @param shinseiDataPk
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public ShinsaKekka2ndInfo getShinsaKekka2nd(UserInfo userInfo,
											  	 ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException;

	
	
	/**
	 * 審査催促メールを送信する。
	 * 設定ファイルに指定された日数後に審査期限になる事業に対して、
	 * 未審査の申請書が存在する審査員に向けてメールを送信する。
	 * 当該審査期限の事業が存在しない場合は何も処理しない。
	 * @param userInfo
	 * @throws ApplicationException
	 */
	public void sendMailShinsaSaisoku(UserInfo userInfo)
		throws ApplicationException;
	
	/**
	 * 審査結果の審査状況を審査完了に更新する。
	 * @param userInfo
	 * @param jigyoId
	 * @return 総合評価がNULLのデータがある場合は、falseを返す。審査状況の更新に成功した場合は、trueを返す。
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaComplete(UserInfo userInfo,
												String jigyoId)
		throws NoDataFoundException, ApplicationException;

	/**
	 * 審査結果の審査状況を審査未完了に更新する。
	 * @param userInfo
	 * @param jigyoId
	 * @return 総合評価がNULLのデータがある場合は、falseを返す。審査状況の更新に成功した場合は、trueを返す。
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaCompleteYet(UserInfo userInfo,
												String jigyoId)
		throws NoDataFoundException, ApplicationException;

	/**
	 * 審査結果の審査状況を指定した状況に更新する。
	 * @param userInfo
	 * @param jigyoId
	 * @param shinsaJokyo
	 * @return 総合評価がNULLのデータがある場合は、falseを返す。審査状況の更新に成功した場合は、trueを返す。
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaComplete(UserInfo userInfo,
												String jigyoId,
												String shinsaJokyo)
		throws NoDataFoundException, ApplicationException;

	/**
	 * 審査結果の審査状況を更新する。
	 * @param userInfo
	 * @param systemNo
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void updateShinseiShinsaComplete(UserInfo userInfo,
												String systemNo)
		throws NoDataFoundException, ApplicationException;
	
// 206-10-25 張志男 利害関係入力完了 追加 ここから
    /**
     * 利害関係入力の入力状況を利害関係入力完了に更新する。     
     * @param userInfo
     * @param jigyoId
     * @return 入力状況の更新に成功した場合は、trueを返す。
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void updateRiekiSohanComplete(UserInfo userInfo, String jigyoId)
            throws NoDataFoundException, ApplicationException;
// 206-10-25 張志男 利害関係入力完了 追加 ここまで
    
// 2006/10/27 苗　追加ここから
    /**
     * 利害関係意見の登録
     * @param userInfo UserInfo
     * @param shinsaKekkaInputInfo ShinsaKekkaInputInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registRiekiSohan(
        UserInfo userInfo,
        ShinsaKekkaInputInfo shinsaKekkaInputInfo)
        throws NoDataFoundException, ApplicationException;
    
    /**
     * 審査結果情報の取得(利害関係用)
     * @param userInfo UserInfo
     * @param shinsaKekkaPk ShinsaKekkaPk
     * @return 審査結果情報を持つShinsaKekkaInputInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public ShinsaKekkaInputInfo selectShinsaKekkaForRiekiSohan(
        UserInfo userInfo,
        ShinsaKekkaPk shinsaKekkaPk)
        throws NoDataFoundException, ApplicationException;
//　2006/10/27　苗　追加ここまで    
}