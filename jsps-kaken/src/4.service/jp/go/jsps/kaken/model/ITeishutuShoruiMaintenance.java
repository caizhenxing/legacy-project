/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ITeishutuShoruiMaintenance.java
 *    Description : 仮領域番号発行、応募書類承認・却下を行うインターフェース。
 *
 *    Author      : DIS
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS            新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoSystemInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;

/**
 * 仮領域番号発行、応募書類承認・却下を行うインターフェース。
 * ID RCSfile="$RCSfile: ITeishutuShoruiMaintenance.java,v $"
 * Revision="$Revision: 1.8 $"
 * Date="$Date: 2007/07/25 07:56:21 $"
 */
public interface ITeishutuShoruiMaintenance {

//2006/06/16 by jzx add start
    /**
     * 受理登録又は受理解除の情報を取得する。
     * 
     * @param userInfo ログイン者情報
     * @param pkInfo
     * @return RyoikikeikakushoInfo
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo selectRyoikikeikakushoInfo(
            UserInfo userInfo,
            RyoikiKeikakushoPk pkInfo)
            throws DataAccessException,
                   NoDataFoundException,
                   ApplicationException;

    /**
     * 受理登録（提出書類）。
     * 
     * @param  userInfo ログイン者情報
     * @param  ryoikiInfo キー情報
     * @param  juriKekka 受理結果
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registTeisyutusyoJuri(UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo, String juriKekka)
            throws  NoDataFoundException,
            ApplicationException;

    /**
     * 受理解除（提出書類）。
     * 
     * @param  userInfo ログイン者情報
     * @param  ryoikiInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void cancelTeisyutusyoJuri(UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo) throws NoDataFoundException,
			ApplicationException;
    
    /**
     * 領域内研究計画調書確定。
     * @param userInfo ログイン者情報
     * @param shinseiDataInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void kakuteiRyoikiGaiyo(UserInfo userInfo,
            ShinseiDataInfo shinseiDataInfo) throws NoDataFoundException,
            ApplicationException;

    /**
	 * 領域内研究計画調書確定解除。
     * 
	 * @param userInfo ログイン者情報
	 * @param shinseiDataInfo
	 * @param pkInfo
	 * @return void
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
    public void cancelKakuteiRyoikiGaiyo(UserInfo userInfo,
            ShinseiDataInfo shinseiDataInfo) throws NoDataFoundException,
			ApplicationException;
    // 2006/06/16 by jzx add end
    
    // 2006/06/20 張拓　ここから
    /**
     * 飛び番号リスト用のデータを取得する。
     * 
     * @param  userInfo ログイン者情報
     * @param  teishutsuShoruiSearchInfo 検索条件情報
     * @return List
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List selectTeisyutusyoTobiSinkiList(UserInfo userInfo,
            TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo)
            throws NoDataFoundException,ApplicationException;
    // 2006/06/20 張拓　ここまで

    // 2006/06/15 lwj add start
    /**
     * 提出書類一覧表示用のデータを取得する。
     * 
     * @param userInfo ログイン者情報
     * @param teishutsuShoruiSearchInfo 検索条件情報
     * @return List
     * @throws NoDataFoundException
     * @throws DataAccessException
     * @throws ApplicationException
     */
    public List selectTeishutuShoruiList(UserInfo userInfo,
            TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo) 
            throws NoDataFoundException,
            ApplicationException;

    // 2006/06/15 lwj add end

    // 2006/06/15 宮 ここから
    /**
     * 提出確認（特定領域研究(新規領域)）一覧情報を取得する。
     * 
     * @param userInfo ログイン者情報
     * @param ryoikiInfo
     * @return List
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchTeisyutuKakuninList(UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException;
    // 2006/06/15 宮 ここまで

    // add start ly 2006/06/15
    /**
     * 簡易仮領域番号発行確認情報を取得する。
     * 
     * @param userInfo ログイン者情報
     * @param ryoikiPk
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo selectRyoikiInfo(UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException;

    /**
     * 仮領域番号発行確認を実行。（所属機関担当者）
     * 処理後、RYOIKI_JOKYO_ID=33に更新される。
     * 
     * @param userInfo ログイン者情報
     * @param ryoikiPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void confirmKariBangoHakko(UserInfo userInfo, RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException;

    /**
     * 仮領域番号発行却下確認する。（所属機関担当者）
     * 処理後、RYOIKI_JOKYO_ID=32に更新される。
     * 
     * @param userInfo ログイン者情報
     * @param ryoikiPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void rejectKariBangoHakko(UserInfo userInfo, RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException;

    /**
     * 応募書類の提出書出力（基盤研究等、特定領域研究）
     * 
     * @param userInfo ログイン者情報
     * @param ryoikiInfo チェックリスト検索条件
     * @return　FileResource　出力情報CSVファウル
     * @throws ApplicationException
     */
    public FileResource createOuboTeishutusho(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws ApplicationException;
    // add end ly 2006/06/15
    
    //add start zjp 2006/06/15
    /**
     * 応募書類情報を取得する。
     * 
     * @param userInfo ログイン者情報
     * @param ryoikiPk
     * @param ryoikiInfo
     * @return RyoikikeikakushoInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo searchOuboSyoruiInfo(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException;
    
    /**
     * 応募書類を承認
     * 
     * @param userInfo ログイン者情報
     * @param ryoikiPk
     * @param ryoikiInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void approveOuboSyorui(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException;
    
    /**
     * 応募書類を却下
     * 
     * @param userInfo ログイン者情報
     * @param ryoikiPk
     * @param ryoikiInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void rejectOuboSyorui(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException;
    
    /**
     * 確認メールを送信する。
     * 学振締切日の?日前の0:02に申請期限になる事業に対して、
     * 申請者が所属する所属担当者に向けてメールを送信する。
     * 当該申請期限の事業が存在しない場合は何も処理しない。
     * @param userInfo
     * @throws ApplicationException
     */
    public void sendMailKakuninTokusoku(UserInfo userInfo)
        throws ApplicationException;
    
    /**
     * 承認メールを送信する。
     * 学振締切日の1日前の0:02に申請期限になる事業に対して、
     * 申請者が所属する所属担当者に向けてメールを送信する。
     * 当該申請期限の事業が存在しない場合は何も処理しない。
     * @param userInfo
     * @throws ApplicationException
     */
    public void sendMailShoninTokusoku(UserInfo userInfo)
        throws ApplicationException;
    //add end zjp 2006/06/15
    
//  宮　2006/06/19 ここから
    /**    
     * 仮領域番号発行情報を登録する
     * @param userInfo ユーザ情報
     * @param pkInfo JigyoKanriPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @author DIS.gongXB
     */
    public void registKariBangoHakkoInfo(
            UserInfo userInfo,
            JigyoKanriPk pkInfo)
            throws NoDataFoundException,ApplicationException;
// 宮 ここまで
    
//  mcj　2006/06/29 ここから
    /**
     * 承認解除(提出書類)
     * 
     * @param userInfo
     * @param ryoikikeikakushoPk
     * @param ryoikiInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void cancelTeisyutusyoSyonin(UserInfo userInfo,
            RyoikiKeikakushoPk ryoikikeikakushoPk,
            RyoikiKeikakushoInfo ryoikiInfo) 
            throws DataAccessException, NoDataFoundException, ApplicationException;

    /**
     * 一括受理(提出書類一覧)を実行
     * 
     * @param userInfo
     * @param searchInfo
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void executeIkkatuJuri(
            UserInfo userInfo,
            TeishutsuShoruiSearchInfo searchInfo)
            throws DataAccessException,NoDataFoundException, ApplicationException;

    /**
     * 領域計画書削除情報を取得する。
     * 
     * @param userInfo
     * @param ryoikiSystemNo システム受付番号
     * @return RyoikiKeikakushoInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo getRyoikiGaiyoDeleteInfo(
            UserInfo userInfo, 
            String ryoikiSystemNo)
            throws NoDataFoundException, ApplicationException;

    /**
     * 領域計画書削除確認のデータを削除する。
     * 
     * @param userInfo
     * @param ryoikiSystemNo システム受付番号
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void deleteFlagRyoikiGaiyo(
            UserInfo userInfo,
            String ryoikiSystemNo)
            throws NoDataFoundException, ApplicationException;
// mcj 2006/06/29 ここまで

//　2006/06/21 dyh add start
    /**    
     * 領域計画書と研究計画調書の一覧を取得する。
     * 
     * @param userInfo ユーザ情報
     * @param kariryoikiNo 仮領域番号
     * @return List 領域計画書と研究計画調書の一覧
     * @throws ApplicationException
     */
    public List getRyoikiAndKenkyuList(UserInfo userInfo, String kariryoikiNo)
            throws ApplicationException;

    /**
     * 領域計画書表紙PDFファイルを取得。
     * 
     * @param userInfo ログイン者情報
     * @param ryoikiSystemNo システム受付番号
     * @return FileResource PDFファイル
     * @throws ApplicationException
     */
    public FileResource getGaiyoCoverPdfFile(UserInfo userInfo, String ryoikiSystemNo)
            throws ApplicationException;

    /**
     * 領域計画書確認PDFファイルを取得。
     * 
     * @param userInfo ログイン者情報
     * @param pkInfo システム受付番号
     * @return FileResource PDFファイル
     * @throws ApplicationException
     */
    public FileResource getRyoikiGaiyoPdfFile(UserInfo userInfo, RyoikiKeikakushoPk pkInfo)
            throws ApplicationException;
// 2006/06/21 dyh add end

// 2006/07/21 dyh add start
    /**
     * 領域計画書（概要）情報管理テーブルに、データの存在チェック
     * ログイン者の申請者IDと一致し、DEL_FLG（削除フラグ）が[0]
     * @param userInfo
     * @return boolean
     * @throws ApplicationException
     */
    public boolean isExistRyoikiGaiyoInfo(UserInfo userInfo)
            throws ApplicationException;
// 2006/07/21 dyh add end
    
//ADD　START 2007/07/02 BIS 張楠 -->
    /**    
     * 領域計画書情報の一覧を取得する。
     * 
     * @param searchInfo 領域計画書情報
     * @return List 領域計画書の情報
     * @throws ApplicationException
     */
    public List getRyoikiResult(RyoikiKeikakushoSystemInfo searchInfo)
            throws ApplicationException;    
//ADD　END　 2007/07/02 BIS 張楠 -->    

}