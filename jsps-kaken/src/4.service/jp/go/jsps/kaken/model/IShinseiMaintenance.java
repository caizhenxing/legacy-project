/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : IShinseiMaintenance.java
 *    Description : 申請情報の管理を行うインターフェース
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/12/08    V1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemBusyException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.HyokaSearchInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.KeizokuInfo;
import jp.go.jsps.kaken.model.vo.KeizokuPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * 申請情報の管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IShinseiMaintenance.java,v $"
 * Revision="$Revision: 1.7 $"
 * Date="$Date: 2007/07/26 07:37:38 $"
 */
public interface IShinseiMaintenance {
    
    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------    
    /** 戻り値Mapキー値：申請情報 */
    public static final String KEY_SHINSEIDATA_INFO = "key_shinseidata_info";
    
    /** 戻り値Mapキー値：系統の区分リスト（申請入力用） */
    public static final String KEY_KEI_KUBUN_LIST   = "key_kei_kubun_list";
    
    /** 戻り値Mapキー値：推薦の観点リスト（申請入力用） */
    public static final String KEY_SUISEN_LIST      = "key_suisen_list";
    
    /** 戻り値Mapキー値：職種リスト（申請入力用） */
    public static final String KEY_SHOKUSHU_LIST    = "key_shokushu_list";
    
// 20050527 Start
    /** 戻り値Mapキー値：領域リスト（申請入力用） */
    public static final String KEY_RYOUIKI_LIST     = "key_ryouiki_list";
// Horikoshi End
    
//2007/02/08 苗　追加ここから
    /** 戻り値Mapキー値：領域リスト（申請入力用） */
    public static final String KEY_KIBOUBUMON_WAKA_LIST     = "key_kiboubumon_waka_list";
//2007/02/08 苗　追加ここまで    
    
//2006/06/22 苗　追加ここから
    /** 戻り値Mapキー値：領域計画書（概要）情報 */
    public static final String KEY_RYOIKIKEIKAKUSHO_INFO = "key_ryoikikeikakusho_info";
    
    /** 戻り値Mapキー値：審査希望部門リスト（領域計画書入力用） */
    public static final String KEY_KIBOUBUMON_LIST  = "key_kiboubumon_list";
    
    /** 戻り値Mapキー値：事前調査リスト（領域計画書入力用） */
    public static final String KEY_JIZENCHOUSA_LIST  = "key_jizenchousa_list";
    
    /** 戻り値Mapキー値：研究の必要性リスト（領域計画書入力用） */
    public static final String KEY_KENKYUHITSUYOUSEI_LIST  = "key_kenkyuHitsuyousei_list";
    
    /** 戻り値Mapキー値：15分類リスト（領域計画書入力用） */
    public static final String KEY_KANRENBUNYABUNRUI_LIST  = "key_kanrenbunyaBunrui_list";
    
    /** 戻り値Mapキー値：添付ファールフラグリスト */
    public static final String KEY_RYOIKITENPUFLAG_LIST = "key_ryoikiTenpuFlg_list";
    
    /** 領域計画書確定フラグ（通常） */
    public static final String FLAG_RYOIKIKEIKAKUSHO_NOT_KAKUTEI = "0";
    
    /** 領域計画書確定フラグ（領域計画書確定済） */
    public static final String FLAG_RYOIKIKEIKAKUSHO_KAKUTEI = "1";
    
    /** 領域計画書解除フラグ（通常） */
    public static final String FLAG_RYOIKIKEIKAKUSHO_NOT_CANCEL = "0";
    
    /** 領域計画書解除フラグ（承認解除済） */
    public static final String FLAG_RYOIKIKEIKAKUSHO_CANCEL = "1";
    
    /** 参考資料ありフラグ */    
    public static final String FLAG_TENPU_ARI = "1";
    
    /** 参考資料なしフラグ */ 
    public static final String FLAG_TENPU_NASI = "0";
    
//2006/06/22 苗　追加ここまで        

    /** 申請書削除フラグ（通常） */
    public static final String FLAG_APPLICATION_NOT_DELETE = "0";
    
    /** 申請書削除フラグ（削除済み） */
    public static final String FLAG_APPLICATION_DELETE     = "1";
    
/** 20050621 定数 */

    // チェック状態
    public static final String TOKUTEI_HENKOU        =    "1";        // 特定領域(大幅な変更)
    public static final String TOKUTEI_CHOUSEI       =    "1";        // 特定領域(調整班)
    public static final String CHECK_OFF             =    "0";
    public static final String CHECK_ON              =    "1";        // チェックボックス状態管理（ON）
                                                                      // ↑調整班のフラグにも使用
                                                                      // ↑削除のフラグにも使用
    //研究区分
    public static final String NAME_KEIKAKU          =    "計画研究";
    public static final String NAME_KOUBO            =    "公募研究";
//20050822 終了研究から終了研究領域に変更
    public static final String NAME_SHUURYOU         =    "終了研究領域";
    public static final String KUBUN_KEIKAKU         =    "1";        // 計画研究区分
    public static final String KUBUN_KOUBO           =    "2";        // 公募研究区分
    public static final String KUBUN_SHUURYOU        =    "3";        // 終了研究区分
    public static final String RYOUIKI_KEIKAKU       =    "000";      // 領域番号(計画研究)
    public static final String RYOUIKI_KOUBO         =    "001";      // 領域番号(公募研究)
    public static final String RYOUIKI_SHUURYOU      =    "999";      // 領域番号(終了研究)
    public static final String SHUURYOU_NAME         =    "成果取りまとめ";
    // 申請区分
    public static final String SHINSEI_NEW           =    "1";        // 新規
    public static final String SHINSEI_CONTINUE      =    "2";        // 継続

    // 班区分
    public static final String HAN_SOUKATU           =    "X00";      // 総括班
    public static final String HAN_SHIEN             =    "Y00";      // 支援班
    public static final String HAN_CHAR              =    "X";        // 終了研究の際に自動セットする値(頭文字)
    public static final String HAN_NO                =    "00";       // 終了研究の際に自動セットする値(番号)

    // チェックレベル
    public static final int    CHECK_ZERO            =    0;
    public static final int    CHECK_ONE             =    1;
    public static final int    CHECK_TWO             =    2;
    public static final int    CHECK_THREE           =    3;
    public static final int    KOUMOKU_CHECK_NUM     =    0;          //チェックする文字列インデックス
    // 項目名
    public static final String KENKYUU_KUBUN         =    "研究区分";
    public static final String SHINSEI_KUBUN         =    "新規・継続区分";
    public static final String CHANGE_FLG            =    "大幅な変更を伴う研究課題";
    public static final String KADAI_NUM             =    "継続の場合の研究課題番号";
    public static final String RYOUIKI_NUM           =    "領域番号";
    public static final String RYOUIKI_NAME          =    "領域略称名";
    public static final String KENKYUU_NUM           =    "研究項目番号";
    public static final String CHOUSEI_FLG           =    "計画研究のうち調整班";
    public static final String KENKYUU_KEIHI         =    "研究経費";
    // 研究費

    public static final int    NENSU = 5;                      // 研究費を何年分入力するか 
//2006/07/03 苗　追加ここから
    public static final int    NENSU_TOKUTEI_SINNKI  = 6;    //　研究費を何年分入力するか（特定領域新規用）
//2006/07/03　苗　追加ここまで    
    public static final int    MAX_KENKYUKEIHI       =    9999999;    // 研究経費システムMAX値
    public static final int    MAX_KENKYUKEIHI_GOKEI =    9999999;    // 研究経費合計最高金額(円)
    public static final int    MIN_KENKYUKEIHI       =    100;        // 各年度の研究経費(万円)
    // 研究分担者
    public static final String KENKYUU_BUNTAN        =    "2";        // 研究分担者
    public static final String KENKYUU_ERROR_NO      =    "99999999"; // エラーとなる研究者番号   

    // 若手研究の年齢制限
    public static final int    WAKATE_LIMIT			=    37;
    public static final String STR_COUNT				=    "GET_COUNT";

    //若手研究Sの年齢制限
    public static final int    WAKATE_S_LIMIT			=    42;
    
    // 開示希望の有無
    public static final String KAIJI_FLG_SET         =    "0";        // 計画研究の場合にセットするフラグ値
// Horikoshi

    // エフォート
    public static final String EFFORT_MIN            =    "1";
    public static final String EFFORT_MAX            =    "100";
//2006/08/17 苗　追加ここから
    public static final String EFFORT_MIN_SINNKI     =    "0";
//2006/08/17　苗　追加ここまで    
    // 時限付き細目番号でない細目番号の最大値
    public static final int    MAX_SAIMOKU_NOT_JIGEN =    8999;

    //2005.10.27 iso 研究計画最終年度前年度応募の有無フラグを追加
    public static final String ZENNEN_ON             =    "1";     //応募する
    public static final String ZENNEN_OFF            =    "2";     //応募しない

    //---------------------------------------------------------------------
    // Methods 
    //---------------------------------------------------------------------    
    /**
     * 申請情報を検索する。
     * @param userInfo 実行するユーザ情報
     * @param searchInfo 申請書検索情報
     * @return Page 取得した申請情報ページオブジェクト
     * @throws ApplicationException
     * @throws NoDataFoundException 対象データが見つからない場合の例外。
     */
    public Page searchApplication(UserInfo userInfo, ShinseiSearchInfo searchInfo) 
        throws NoDataFoundException, ApplicationException;

    /**
     * 簡易申請書情報を取得する。
     * 申請書情報を抽出する。
     * @param userInfo 実行するユーザ情報
     * @param pkInfo 申請データ情報（キー）
     * @return SimpleShinseiDataInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public SimpleShinseiDataInfo selectSimpleShinseiDataInfo(
            UserInfo userInfo,
            ShinseiDataPk pkInfo)
        throws NoDataFoundException, ApplicationException;

    /**
     * 簡易申請書情報を取得する。
     * @param userInfo 実行するユーザ情報
     * @param pkInfo 申請データ情報（キー）配列
     * @return SimpleShinseiDataInfo[]
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public SimpleShinseiDataInfo[] selectSimpleShinseiDataInfos(
            UserInfo userInfo,
            ShinseiDataPk[] pkInfo)
        throws NoDataFoundException, ApplicationException;

    /**
     * 申請書情報を抽出する。
     * @param userInfo 実行するユーザ情報
     * @param pkInfo 申請データ情報（キー）
     * @return ShinseiDataInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public ShinseiDataInfo selectShinseiDataInfo(UserInfo userInfo,
                                                 ShinseiDataPk pkInfo)
        throws NoDataFoundException, ApplicationException;
 
    /**
     * 新規入力用の申請情報を抽出する。
     * 申請入力に必要な事業情報をセットした申請情報を返する。
     * @param userInfo              実行するユーザ情報
     * @param pkInfo                事業管理情報主キー
     * @return Map                  新規入力用申請情報と系列リストのMap
     * @throws ApplicationException    
     * @throws NoDataFoundException 対象データ（事業情報）が見つからない場合の例外
     */
    public Map selectShinseiDataForInput(UserInfo userInfo,
                                         JigyoKanriPk pkInfo) 
        throws NoDataFoundException, ApplicationException;

    /**
     * 既存データ更新用の申請情報を抽出する。
     * @param userInfo              実行するユーザ情報
     * @param pkInfo                申請データ主キー
     * @return Map                  既存申請情報と系列リストのMap
     * @throws ApplicationException    
     * @throws NoDataFoundException 対象データが見つからない場合の例外
     */
    public Map selectShinseiDataForInput(UserInfo userInfo, 
                                         ShinseiDataPk pkInfo) 
        throws NoDataFoundException, ApplicationException;

    /**
     * 申請情報を仮保存する。（新規）
     * @param userInfo              実行するユーザ情報
     * @param addInfo               作成する申請情報
     * @param fileRes               添付ファイル（存在しない場合はnull）
     * @return ShinseiDataInfo      新規登録した申請情報
     * @throws ValidationException    
     * @throws ApplicationException    
     */
    public ShinseiDataInfo transientSaveNew(UserInfo userInfo, 
                                            ShinseiDataInfo addInfo, 
                                            FileResource fileRes) 
        throws ValidationException, ApplicationException;    

    /**
     * 申請情報を仮保存する。（更新）
     * @param userInfo              実行するユーザ情報
     * @param addInfo               作成する申請情報
     * @param fileRes               添付ファイル（存在しない場合はnull）
     * @throws ValidationException    
     * @throws NoDataFoundException
     * @throws ApplicationException    
     */
    public void transientSaveUpdate(UserInfo userInfo, 
                                    ShinseiDataInfo addInfo, 
                                    FileResource fileRes) 
        throws ValidationException, NoDataFoundException, ApplicationException;    

    /**
     * 申請情報を登録する。（新規）
     * @param userInfo              実行するユーザ情報
     * @param updateInfo            更新する申請情報
     * @param fileRes               添付ファイル
     * @return                      新規登録した申請情報
     * @throws ValidationException    
     * @throws ApplicationException
     */
    public ShinseiDataInfo registApplicationNew(UserInfo userInfo,
                                                ShinseiDataInfo updateInfo,
                                                FileResource fileRes) 
        throws ValidationException, ApplicationException;

    /**
     * 申請情報を登録する。（更新）
     * @param userInfo              実行するユーザ情報
     * @param updateInfo            更新する申請情報
     * @param fileRes               添付ファイル（存在しない場合はnull）
     * @return ShinseiDataInfo      申請データ情報
     * @throws ValidationException    
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public ShinseiDataInfo registApplicationUpdate(UserInfo userInfo,
            ShinseiDataInfo updateInfo,FileResource fileRes) 
        throws ValidationException, NoDataFoundException, ApplicationException;

    /**
     * 申請データをXML変換、PDF変換する。
     * 処理後、申請状況を「申請未確認」に更新する。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void convertApplication(UserInfo userInfo,
                                   ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

    /**
     * 申請情報を削除します。（申請者）
     * @param userInfo              実行するユーザ情報
     * @param shinseiDataPk         申請データ情報（キー）
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void deleteApplication(UserInfo userInfo,
                                  ShinseiDataPk shinseiDataPk) 
        throws NoDataFoundException, ApplicationException;

    /**
     * PDF変換後のファイルを取得する。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @return FileResource
     * @throws ApplicationException
     */
    public FileResource getPdfFileRes(UserInfo userInfo,
                                      ShinseiDataPk shinseiDataPk)
        throws ApplicationException;

    /**
     * PDF変換後前の添付ファイルを取得する。
     * 添付ファイルが複数存在する場合、最初のファイルリソースを取得する。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @return FileResource
     * @throws ApplicationException
     */
    public FileResource getTenpuFileRes(UserInfo userInfo,
                                        ShinseiDataPk shinseiDataPk)
        throws ApplicationException;

    /**
     * PDF変換後前の添付ファイルを取得する。
     * 添付ファイルが複数存在する場合、すべてのファイルリソースを取得する。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @return FileResource[]
     * @throws ApplicationException
     */
    public FileResource[] getAllTenpuFileRes(UserInfo userInfo,
                                             ShinseiDataPk shinseiDataPk)
        throws ApplicationException;

    /**
     * 申請書の確認を完了する。（申請者）
     * 処理後、申請状況を「所属期間受付中」にする。
     * 所属機関担当者宛てに「申請書確認完了メール」を送信する。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void confirmComplete(UserInfo userInfo,
                                ShinseiDataPk shinseiDataPk)
         throws NoDataFoundException,ApplicationException;

    /**
     * 申請書を承認する。（所属機関担当者）
     * 処理後、申請状況を「学振処理中」にする。
     * 所属機関担当者宛てに「申請書学振到達メール」を送信する。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void recognizeApplication(UserInfo userInfo,
                                       ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

    /**
     * 申請書を却下する。（所属機関担当者）
     * 処理後、申請状況を「所属機関却下」にする。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void rejectApplication(UserInfo userInfo,
                                     ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

//    /**
//     * 評価コメント情報を検索する。
//     * @param userInfo              実行するユーザ情報
//     * @param searchInfo            評価コメント検索情報
//     * @return                      取得した評価コメント情報ページオブジェクト
//     * @throws ApplicationException    
//     * @throws NoDataFoundException 対象データが見つからない場合の例外。
//     */
//    public Page searchCommentList(UserInfo userInfo, HyokaSearchInfo searchInfo) 
//        throws NoDataFoundException, ApplicationException;

    /**
     * 評価情報を検索する。
     * @param userInfo              実行するユーザ情報
     * @param searchInfo            評価コメント検索情報
     * @return Page                 取得した評価コメント情報ページオブジェクト
     * @throws ApplicationException    
     * @throws NoDataFoundException 対象データが見つからない場合の例外。
     */
    public Page searchHyokaList(UserInfo userInfo, HyokaSearchInfo searchInfo) 
        throws NoDataFoundException, ApplicationException;

    /**
     * 1次審査結果における業務担当者用備考を登録する。
     * @param userInfo 実行するユーザ情報
     * @param shinsaKekkaRefInfo 1次審査結果情報
     * @throws NoClassDefFoundError
     * @throws ApplicationException
     */
    public void regist1stShinsaKekkaBiko(UserInfo userInfo,
            ShinsaKekkaReferenceInfo shinsaKekkaRefInfo)
        throws NoClassDefFoundError, ApplicationException;

    /**
     * 2次審査結果を登録する。
     * @param userInfo 実行するユーザ情報
     * @param shinsaKekka2nd 2次審査結果情報
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void regist2ndShinsaKekka(UserInfo userInfo,
                                     ShinsaKekka2ndInfo shinsaKekka2nd)
        throws NoDataFoundException, ApplicationException;    

    /**
     * 申請書を受理する。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @param jigyoCd 事業コード
     * @param comment
     * @param seiriNo 整理番号
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registShinseiJuri(UserInfo userInfo,
                                    ShinseiDataPk shinseiDataPk,
                                    String jigyoCd,
                                    String comment,
                                    String seiriNo
                                    )
        throws NoDataFoundException, ApplicationException;

    /**
     * 申請書を不受理する。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @param jigyoCd 事業コード
     * @param comment
     * @param seiriNo 整理番号
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registShinseiFujuri(UserInfo userInfo,
                                    ShinseiDataPk shinseiDataPk,
                                    String jigyoCd, 
                                    String comment,
                                    String seiriNo)
        throws NoDataFoundException, ApplicationException;

    /**
     * 申請書を修正依頼する。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @param jigyoCd 事業コード
     * @param comment
     * @param seiriNo 整理番号
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registShinseiShuseiIrai(UserInfo userInfo,
                                        ShinseiDataPk shinseiDataPk,
                                        String jigyoCd, 
                                        String comment,
                                        String seiriNo)
        throws NoDataFoundException, ApplicationException;

//add start dyh 2006/06/02
    /**
     * 一括受理.<br><br>
     * 一括受理データを表示。（学創、特推、基盤で使用）
     * 
     * @param userInfo 実行するユーザ情報
     * @param searchInfo 申請書検索情報
     * @param systemNos キー配列
     * @return Page　一括受理データ情報
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public Page getShinseiJuriAll(UserInfo userInfo,
                                  ShinseiSearchInfo searchInfo,
                                  String[] systemNos)
        throws NoDataFoundException, ApplicationException;
//add end dyh 2006/06/02

    //2005/04/22 追加 ここから-----------------------------------------
    /**
     * 一括受理を行う。
     * @param userInfo 実行するユーザ情報
     * @param pks キーリスト
     * @return List 一括受理データ情報
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
//    public void registShinseiJuriAll(UserInfo userInfo,List pks)
    public List registShinseiJuriAll(UserInfo userInfo,List pks)
        throws NoDataFoundException, ApplicationException;
    // 追加 ここまで----------------------------------------------------

    /**
     * 審査依頼発行通知時のステータス更新メソッド。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void updateStatusForShinsaIraiIssue(UserInfo userInfo,
                                               ShinseiDataPk[] shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

    /**
     * CSV出力用の申請者情報を検索する（業務用）。
     * 
     * @param userInfo 実行するユーザ情報
     * @param searchInfo 申請書検索情報
     * @return List CSV出力用の申請者情報
     * @throws ApplicationException
     */
    public List searchCsvData(UserInfo userInfo, ShinseiSearchInfo searchInfo) 
        throws ApplicationException;

    /**
     * CSV出力用の申請者情報を検索する(所属用)。
     * 
     * @param userInfo 実行するユーザ情報
     * @param searchInfo 申請書検索情報
     * @return List CSV出力用の申請者情報
     * @throws ApplicationException
     */
    public List searchShozokuCsvData(UserInfo userInfo,
                                     ShinseiSearchInfo searchInfo) 
        throws ApplicationException;

    /**
     * 研究組織CSV出力用の申請者情報を検索する。
     * 
     * @param userInfo 実行するユーザ情報
     * @param searchInfo 申請書検索情報
     * @return List 研究組織CSV出力用の申請者情報
     * @throws ApplicationException
     */
    public List searchKenkyuSoshikiCsvData(UserInfo userInfo,
                                           ShinseiSearchInfo searchInfo) 
        throws ApplicationException;

    /**
     * 推薦書ファイルを格納し、申請データテーブルにパス情報を登録する。
     * 登録済みの場合は上書きする。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @param fileRes ファイルの送受信で使用するリソース
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registSuisenFile(
            UserInfo userInfo,
            ShinseiDataPk shinseiDataPk,
            FileResource fileRes)
        throws NoDataFoundException, ApplicationException;

    /**
     * 指定された申請データの推薦書ファイルを返す。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @return FileResource 申請データの推薦書ファイル
     * @throws NoDataFoundException
     * @throws ApplicationException
     */    
    public FileResource getSuisenFileRes(
            UserInfo userInfo,
            ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

    /**
     * 指定された申請データの推薦書ファイルパス情報を削除する。
     * 実際のファイルは消さない。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void deleteSuisenFile(
            UserInfo userInfo,
            ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;

    /**
     * 評価結果CSV出力用の評価情報を検索する。
     * @param userInfo              実行するユーザ情報
     * @param searchInfo            評価コメント検索情報
     * @return FileResource         取得した評価コメント情報ファイル
     * @throws ApplicationException    
     * @throws NoDataFoundException 対象データが見つからない場合の例外。
     */
    public FileResource searchCsvHyokaList(
            UserInfo userInfo,
            HyokaSearchInfo searchInfo) 
        throws NoDataFoundException, ApplicationException;

    /**
     * CSV出力用の評価情報を検索する。
     * @param userInfo              実行するユーザ情報。
     * @param searchInfo            検索情報
     * @return List                 取得した割り振り情報
     * @throws ApplicationException    
     */
    public List searchCsvData(UserInfo userInfo, HyokaSearchInfo searchInfo) 
        throws ApplicationException;

    // 20060605 Wang Xiancheng add start
    /**
     * 複数申請書を承認する。（所属機関担当者）
     * 処理後、申請状況を「学振処理中」にする。
     * 所属機関担当者宛てに「申請書学振到達メール」を送信する。
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPks 申請データ情報（キー）配列
     * @param checkKbn 確認と承認の区分
     * @throws ValidationException
     * @throws ApplicationException
     */    
    public void recognizeMultiApplication(
            UserInfo userInfo,
            ShinseiDataPk[] shinseiDataPks,
            String checkKbn)
        throws ValidationException, ApplicationException;
    // 20060605 Wang Xiancheng add end

    // 2006/06/14 dyh add start 研究計画調書一覧用
    /**
     * 研究計画調書一覧データを取得
     * 
     * @param userInfo 実行するユーザ情報
     * @param searchInfo 申請書検索情報
     * @return List 研究計画調書一覧データ
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List getKeikakuTyosyoList(
            UserInfo userInfo,
            ShinseiSearchInfo searchInfo)
        throws NoDataFoundException, ApplicationException;
    // 2006/06/14 dyh add end

    // 2006/06/16 Wang Xiancheng add start
    /**
     * 確認・却下対象応募情報一覧データを取得
     * 
     * @param userInfo 実行するユーザ情報
     * @param searchInfo 申請書検索情報
     * @return List 一覧データ
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public Page searchConfirmInfo(
            UserInfo userInfo,
            ShinseiSearchInfo searchInfo)
        throws NoDataFoundException, ApplicationException;
    // 2006/06/16 Wang Xiancheng add end

    // 2006/06/16 苗 追加ここから
    /**
     * 応募状況（研究計画調書（特定領域新規））の却下.<br>
     * <br>
     * 
     * @param userInfo 実行するユーザ情報
     * @param shinseiDataPk 申請データ情報（キー）
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.IShinseiMaintenance#rejectApplication(jp.go.jsps.kaken.model.vo.UserInfo,
     *      jp.go.jsps.kaken.model.vo.ShinseiDataPk)
     */
    public void rejectApplicationForTokuteiSinnki(
            UserInfo userInfo,
            ShinseiDataPk shinseiDataPk)
        throws NoDataFoundException, ApplicationException;
    // 2006/06/16 苗 追加ここまで

    // 2006/06/20 劉佳 追加ここから
    /**
     * 研究経費表情報を検索
     * 
     * @param userInfo 実行するユーザ情報
     * @param dataInfo 研究代表者及び分担者（研究組織表）情報
     * @return List 一覧データ
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.IShinseiMaintenance#rejectApplication(jp.go.jsps.kaken.model.vo.UserInfo,
     *      jp.go.jsps.kaken.model.vo.KenkyuSoshikiKenkyushaInfo)
     */
    public List searchKenkyuKeihi(
            UserInfo userInfo,
            KenkyuSoshikiKenkyushaInfo dataInfo)
        throws NoDataFoundException, ApplicationException;
    // 2006/06/20 劉佳 追加ここまで

	// 2006/06/16 追加　李義華　ここから
	/**
	 * 研究組織表情報を検索
	 * 
	 * @param userInfo 実行するユーザ情報
	 * @param dataInfo KenkyuSoshikiKenkyushaInfo
	 * @return List 一覧データ			
	 * @throws ApplicationException	
	 * @throws DataAccessException
     * @throws NoDataFoundException 
	 */
	public List searchKenkyuSosiki(
			UserInfo userInfo,
			KenkyuSoshikiKenkyushaInfo dataInfo) 
	    throws ApplicationException, DataAccessException,NoDataFoundException;
    // 2006/06/16 追加　李義華　ここまで
//<!-- ADD　START 2007/07/10 BIS 張楠 -->	
	/**
	 * 研究組織表情報をupdate
	 * 
	 * @param userInfo 実行するユーザ情報
	 * @param dataInfo KenkyuSoshikiKenkyushaInfo	
	 * @throws ApplicationException	
	 * @throws ValidationException
	 * @throws DataAccessException
	 */
	public void updateHyojijun(
			UserInfo userInfo,
			KenkyuSoshikiKenkyushaInfo dataInfo) 
	    throws ApplicationException, ValidationException,DataAccessException;
//<!-- ADD　END　 2007/07/10 BIS 張楠 -->	
//  2006/06/21 苗　追加ここから    
    /**
     * 新規領域計画書（概要）入力用初期データを返却.<br><br>
     * 
     * @param userInfo ログオンしたユーザ情報
     * @param pkInfo 事業管理テーブル主キー
     * @param ryoikiSystemNo システム受付番号
     * @return 新規入力用初期データ(Map)
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public Map selectRyoikiKeikakushoInfoForInput(
            UserInfo userInfo,
            JigyoKanriPk pkInfo,
            String ryoikiSystemNo)
        throws NoDataFoundException, ApplicationException;

    /**
     * 領域計画書情報の登録.<br><br>
     * 
     * 領域計画書、添付ファイルの登録を行う。<br>
     * 途中で例外が発生したら、ロールバックする。<br><br>
     * 
     * @param userInfo  ログオンしたユーザ情報
     * @param dataInfo  RyoikiKeikakushoInfo
     * @param fileRes   FileResource
     * @return 領域計画書(RyoikikeikakushoInfo)
     * @throws ValidationException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo registGaiyoApplicationNew(
            UserInfo userInfo,
            RyoikiKeikakushoInfo dataInfo,
            FileResource fileRes)
        throws ValidationException, ApplicationException;

    /**
     * 領域計画書情報の更新.<br><br>
     * 
     * 領域計画書、添付ファイルの登録を行う。<br>
     * 途中で例外が発生したら、ロールバックする。<br><br>
     * 
     * @param userInfo  ログオンしたユーザ情報
     * @param ryoikikeikakushoInfo 領域計画書（概要）情報
     * @param fileRes   FileResource
     * @return 領域計画書(RyoikikeikakushoInfo)
     * @throws ValidationException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo registGaiyoApplicationUpdate(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo,
            FileResource fileRes)
        throws ValidationException, ApplicationException;

    /**
     * 領域計画書情報の一時保存.<br><br>
     * 
     * 領域計画書、添付ファイルの登録を行う。<br>
     * 途中で例外が発生したら、ロールバックする。<br><br>
     * 
     * @param userInfo  ログオンしたユーザ情報
     * @param ryoikikeikakushoInfo 領域計画書（概要）情報
     * @param fileRes   FileResource
     * @return 領域計画書(RyoikikeikakushoInfo)
     * @throws ValidationException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo transientGaiyoApplicationNew (
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo,
            FileResource fileRes)
        throws ValidationException, ApplicationException;
//2006/06/21　苗　追加ここまで     

//  宮　2006/06/29　ここから
    /**
     * 領域計画書確認完了確認
     * @param userInfo  ログオンしたユーザ情報
     * @param ryoikikeikakushoPk 領域計画書（概要）キー情報
     * @throws NoDataFoundException
     * @throws DataAccessException
     * @throws ApplicationException
     */
    public void confirmGaiyoComplete(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikikeikakushoPk)
        throws NoDataFoundException, ApplicationException;
    //宮　2006/06/29　ここまで

    //2006.07.03 zhangt add start
    /**
     * 領域計画書PDF情報の変換
     * @param userInfo ログオンしたユーザ情報
     * @param ryoikiKeikakushoPk 領域計画書（概要）キー情報
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void convertGaiyoApplication(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiKeikakushoPk)
        throws NoDataFoundException, ApplicationException ;
    //2006.07.03 zhangt add end

    /**
     * 領域計画書情報の一時保存.(更新)<br><br>
     * 
     * 領域計画書、添付ファイルの登録を行う。<br>
     * 途中で例外が発生したら、ロールバックする。<br><br>
     * 
     * @param userInfo ログオンしたユーザ情報
     * @param ryoikikeikakushoInfo 領域計画書（概要）情報
     * @param fileRes   ファイルの送受信で使用するリソース
     * @throws ValidationException
     * @throws ApplicationException
     */
    public void transientGaiyoApplicationUpdate (
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo,
            FileResource fileRes)
        throws ValidationException, ApplicationException;

//2006/07/21 苗　追加ここから
    /**
     * 申請情報を取得.<br><br>
     * 
     * @param userInfo  ログオンしたユーザ情報
     * @param connection  コネクション
     * @param pkInfo  申請データ情報（キー）
     * @return ShinseiDataInfo 申請情報
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public ShinseiDataInfo selectShinseiDataInfoForConfirm(
            UserInfo userInfo,
            Connection connection,
            ShinseiDataPk pkInfo)
        throws NoDataFoundException, ApplicationException;

    /**
     * 簡易申請情報を取得.（領域代表者用）<br>
     * <br>
     * 簡易申請情報を取得する。<br>
     * <br>
     * ※削除画面で使用<br>
     * <br>
     * 自クラスのselectSimpleShinseiDataInfos(UserInfo,ShinseiDataPk[])メソッドを呼ぶ。<br>
     * 引数に、第一引数userInfoと第二引数pkInfoを格納した配列(ShinseiDataPk)を渡す。<br>
     * <br>
     * 取得したSimpleShinseiDataInfoを返却する。<br>
     * <br>
     * 
     * @param userInfo ログオンしたユーザ情報
     * @param pkInfo 申請データ情報（キー）
     * @return SimpleShinseiDataInfo 簡易申請情報
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.IShinseiMaintenance#selectSimpleShinseiDataInfo(jp.go.jsps.kaken.model.vo.UserInfo,
     *      jp.go.jsps.kaken.model.vo.ShinseiDataPk)
     */
    public SimpleShinseiDataInfo selectSimpleShinseiDataInfoForGaiyo(
            UserInfo userInfo,
            ShinseiDataPk pkInfo)
        throws NoDataFoundException, ApplicationException;
//2006/07/21　苗　追加ここまで    
    

    //2006.09.25 iso iso タイトルに「概要」をつけたPDF作成のため
    /**
     * 領域計画書概要PDF作成
     * @param userInfo  UserInfo
     * @return なし
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void GaiyoPdfConvert(UserInfo userInfo) throws NoDataFoundException,ApplicationException;
    
    //<!-- ADD　START 2007/07/20 BIS 張楠 -->
    /**
     * 領域内研究計画調書の初年度研究経費チェック
     * @param userInfo  UserInfo
     * @param ryouikiNo  仮領域番号
     * @return なし
     * @throws ValidationException
     * @throws ApplicationException
     */
    public void CheckKenkyuKeihiSoukeiInfo(UserInfo userInfo,String ryouikiNo) throws ValidationException,ApplicationException ;
    //<!-- ADD　START 2007/07/20 BIS 張楠 -->
	// ADD START 2007-07-10 BIS 王志安
	/**
	 * 継続課題情報取得
	 * @param jigyoId
	 * @param kadaiNo
	 * @return
	 */
    public KeizokuInfo getKenkyukadaiInfo(UserInfo userInfo, String kenkyuNo, String kadaiNo)
	    throws SystemBusyException, NoDataFoundException,
		DataAccessException;
	//　ADD END 2007-07-10 BIS 王志安
}