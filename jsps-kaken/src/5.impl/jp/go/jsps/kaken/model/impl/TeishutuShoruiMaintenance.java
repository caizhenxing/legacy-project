/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : TeishutuShoruiMaintenance.java
 *    Description : 仮領域番号発行・領域計画書作成・領域計画書提出確認（特定領域研究）管理を行うクラス。
 *
 *    Author      : DIS
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS            新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.IPdfConvert;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.IShozokuMaintenance;
import jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.impl.JigyoKanriInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKenkyushaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.RyoikiKeikakushoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.FileIOException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemBusyException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.KenkyushaPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoSystemInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.status.StatusGaiyoManager;
import jp.go.jsps.kaken.status.StatusManager;
import jp.go.jsps.kaken.util.CsvUtil;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.RandomPwd;
import jp.go.jsps.kaken.util.SendMailer;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.util.DateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 仮領域番号発行・領域計画書作成・領域計画書提出確認（特定領域研究）管理を行うクラス。
 * 
 * ID RCSfile="$RCSfile: TeishutuShoruiMaintenance.java,v $"
 * Revision="$Revision: 1.13 $"
 * Date="$Date: 2007/07/25 07:56:37 $"
 */
public class TeishutuShoruiMaintenance implements ITeishutuShoruiMaintenance {
    
    /** 仮領域削除フラグ（削除済み） */
    public static final String FLAG_APPLICATION_DELETE     = "1";

    //add start ly 2006/06/19
    /** ログ */
    protected static Log log = LogFactory.getLog(TeishutuShoruiMaintenance.class);

    /**
     * 応募書類の提出書ファイル格納フォルダ .<br /><br />
     * 具体的な値は、"<b>${shinsei_path}/work/oubo/</b>"<br />
     */
    private static String OUBO_WORK_FOLDER = ApplicationSettings.getString(ISettingKeys.OUBO_WORK_FOLDER);

    /**
     * 応募書類の提出書Wordファイル格納フォルダ.<br /><br />
     * 具体的な値は、"<b>${shinsei_path}/settings/oubo/</b>"<br />
     */
    private static String OUBO_FORMAT_PATH = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_PATH);

    /**
     * 応募書類の提出書Wordファイル名.<br /><br />
     *
     * 具体的な値(基盤)は、"<b>kiban.doc</b>"<br />
     * 具体的な値(特定領域)は、"<b>tokutei.doc</b>"<br />
     * 具体的な値(若手スタートアップ)は、"<b>wakate.doc</b>"<br />
     * 具体的な値(特別研究促進費)は、"<b>shokushinhi.doc</b>"<br />
     */
    private static String OUBO_FORMAT_FILE_NAME_TOKUTEI = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_FILE_NAME_TOKUTEI_SHINKI);
    //add end ly 2006/06/19
    
//  2006/06/27 追加　李義華　ここから
    /** メールサーバアドレス */
    private static final String SMTP_SERVER_ADDRESS = 
                                ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS); 
    /** 差出人 統一して１つ */
    private static final String FROM_ADDRESS = 
                                ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);
    
    /** メール内容（応募者が仮領域番号発行情報を登録したとき）「件名」 */
    private static final String SUBJECT_KARIRYOIKINO_KAKUNIN_IRAI = 
                                ApplicationSettings.getString(ISettingKeys.SUBJECT_KARIRYOIKINO_KAKUNIN_IRAI);

    /** メール内容（応募者が仮領域番号発行情報を登録したとき）「本文」 */
    private static final String CONTENT_KARIRYOIKINO_KAKUNIN_IRAI = 
                                ApplicationSettings.getString(ISettingKeys.CONTENT_KARIRYOIKINO_KAKUNIN_IRAI); 
//　2006/06/27 追加　李義華　ここまで
    
//  2006/06/29 追加　zjp　ここから
    /** 未承認申請書締め切り期限までの日付 */
    protected static final int DATE_BY_KAKUNIN_TOKUSOKU = 
        ApplicationSettings.getInt(ISettingKeys.DATE_BY_KAKUNIN_TOKUSOKU);

    /** 仮領域番号最大リトライ回数 */
    protected static final int KARIRYOIKI_NO_MAX_RETRY_COUNT = 
        ApplicationSettings.getInt(ISettingKeys.SYSTEM_NO_MAX_RETRY_COUNT);

    /** メール内容（所属機関担当者への未承認確認通知）「件名」 */
    protected static final String SUBJECT_SHINSEISHO_KAKUNIN_TOKUSOKU = 
        ApplicationSettings.getString(ISettingKeys.SUBJECT_SHINSEISHO_KAKUNIN_TOKUSOKU);
        
    /** メール内容（所属機関担当者への未承認確認通知）「本文」 */
    protected static final String CONTENT_SHINSEISHO_KAKUNIN_TOKUSOKU = 
        ApplicationSettings.getString(ISettingKeys.CONTENT_SHINSEISHO_KAKUNIN_TOKUSOKU);
    
    /** 未承認申請書締め切り期限までの日付 */
    protected static final int DATE_BY_SHONIN_TOKUSOKU = 
        ApplicationSettings.getInt(ISettingKeys.DATE_BY_SHONIN_TOKUSOKU);
    
    /** メール内容（所属機関担当者への未承認確認通知）「件名」 */
    protected static final String SUBJECT_RYOIKIGAIYO_SHONIN_TOKUSOKU = 
        ApplicationSettings.getString(ISettingKeys.SUBJECT_RYOIKIGAIYO_SHONIN_TOKUSOKU);
        
    /** メール内容（所属機関担当者への未承認確認通知）「本文」 */
    protected static final String CONTENT_RYOIKIGAIYO_SHONIN_TOKUSOKU = 
        ApplicationSettings.getString(ISettingKeys.CONTENT_RYOIKIGAIYO_SHONIN_TOKUSOKU);
// 　2006/06/29 追加　zjp　ここまで

 //2006/06/16 by jzx add start   
    /**
     * 受理登録又は受理解除の情報を取得する。
     * @param userInfo  UserInfo
     * @param pkInfo    RyoikikeikakushoPk
     * @return 領域計画書（概要）情報管理(RyoikikeikakushoInfo)
     * @throws NoDataFoundException
     * @throws DataAccessException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#selectRyoikikeikakushoInfo(jp.go.jsps.kaken.model.vo.UserInfo, RyoikiKeikakushoPk)
     */
    public RyoikiKeikakushoInfo selectRyoikikeikakushoInfo(
            UserInfo userInfo,
            RyoikiKeikakushoPk pkInfo
            ) throws DataAccessException,
                     NoDataFoundException,
                     ApplicationException {

        // DBコネクションの取得
        Connection connection = null;
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;

        // 簡易申請情報
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao.selectRyoikiKeikakushoInfo(connection, pkInfo);
        }catch (SystemBusyException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
                    "errors.4004"), se);
        } 
        // 申請状況名をセット
        finally {
            DatabaseUtil.closeConnection(connection);
        }
        return ryoikikeikakushoInfo;
    }

    /**
     * 受理登録（提出書類）。
     * @param userInfo  UserInfo
     * @param pkInfo    RyoikikeikakushoPk
     * @param juriKekka String
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registTeisyutusyoJuri(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo,
            String juriKekka
            ) throws ApplicationException, NoDataFoundException {
        // DBコネクションの取得
        Connection connection = null;
        boolean success = false;

        // 排他制御のため既存データを取得する
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = new RyoikiKeikakushoInfo();
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk();
        ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();

        // 申請データ管理DAO
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(
                userInfo);
        ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
        try {
            // DBコネクションの取得
            connection = DatabaseUtil.getConnection();
            pkInfo.setRyoikiSystemNo(ryoikiInfo.getRyoikiSystemNo());
            ryoikikeikakushoInfoDao.checkKenkyuNoInfo(connection, pkInfo);
            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao
                    .selectRyoikiKeikakushoInfoForLock(connection, pkInfo);
            // ---領域計画書（概要）情報管理データ削除フラグチェック---
            String delFlag = ryoikikeikakushoInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("当該領域計画書情報データは削除されています。",
                        new ErrorInfo("errors.9001"));
            }

            // ---DB更新---
            // 領域計画書概要応募状況ID
            ryoikikeikakushoInfo.setRyoikiJokyoId(juriKekka);
            // 所属機関承認日
            ryoikikeikakushoInfo.setJyuriDate(new Date());
            shinseiInfo.setJokyoId(ryoikikeikakushoInfo.getRyoikiJokyoId());
            shinseiInfo.setRyouikiNo(ryoikikeikakushoInfo.getKariryoikiNo());
            shinseiInfo.setJokyoIds(ryoikiInfo.getJokyoIds());
            shinseiInfo.setJyuriDate(new Date());
            // shinseiInfo.setSaishinseiFlg("0"); //SAISHINSEI_FLG 再申請フラグ

            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection,
                    ryoikikeikakushoInfo);
            shinseiDao.updateShinseis(connection, shinseiInfo, juriKekka);
            success = true;
        }
        catch (SystemBusyException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
                    "errors.4004"), se);
        }
        catch (DataAccessException e) {
            throw new ApplicationException("排他取得中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        }
        finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }
    
    /**
     * 受理解除（提出書類）。
     * @param  userInfo
     * @param  searchInfo
     * @param  pkInfo
     * @return void 
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void cancelTeisyutusyoJuri(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo
            ) throws NoDataFoundException, ApplicationException {

        // DBコネクションの取得
        Connection connection = null;
        boolean success = false;

        // 排他制御のため既存データを取得する
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = new RyoikiKeikakushoInfo();
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk();
        ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();

        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = 
            new RyoikiKeikakushoInfoDao(userInfo);
        ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
        try {
            // DBコネクションの取得
            connection = DatabaseUtil.getConnection();
            pkInfo.setRyoikiSystemNo(ryoikiInfo.getRyoikiSystemNo());
            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao
                    .selectRyoikiKeikakushoInfoForLock(connection, pkInfo);

            //---領域計画書（概要）情報管理データ削除フラグチェック---
            String delFlag = ryoikikeikakushoInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("当該領域計画書情報データは削除されています。",
                        new ErrorInfo("errors.9001"));
            }

            //---DB更新---
            ryoikikeikakushoInfo
                    .setRyoikiJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);// 領域計画書概要応募状況ID
            ryoikikeikakushoInfo.setJyuriDate(null); // 所属機関承認日
            shinseiInfo.setJokyoId(ryoikikeikakushoInfo.getRyoikiJokyoId());//領域計画書（概要）申請状況ID
            shinseiInfo.setRyouikiNo(ryoikikeikakushoInfo.getKariryoikiNo());//仮領域番号
            shinseiInfo.setJokyoIds(ryoikiInfo.getJokyoIds());//申請状況ID配列
            shinseiInfo.setJyuriDate(null);//学振受理日
            shinseiInfo.setJyuriDateFlg("1");//学振受理日Flg
            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection,
                    ryoikikeikakushoInfo);

            String status = ryoikikeikakushoInfo.getRyoikiJokyoId();
            shinseiDao.updateShinseis(connection, shinseiInfo, status);
            success = true;
        }
        catch (SystemBusyException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
                    "errors.4004"), se);
        }
        catch (DataAccessException e) {
            throw new ApplicationException("排他取得中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        }
        finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }
    
    /**
     * 領域内研究計画調書確定を行う
     * @param  userInfo
     * @param  ryoikiGaiyoForm
     * @return void
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void kakuteiRyoikiGaiyo(
            UserInfo userInfo,
            ShinseiDataInfo shinseiDataInfo
            ) throws NoDataFoundException, ApplicationException{

        //DBコネクションの取得
        Connection connection = null;   
        boolean success = false;
        ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);
        try {
            connection = DatabaseUtil.getConnection();
            shinseiDataInfoDao.selectCheckKakuteiSaveInfo(connection, shinseiDataInfo);

            //申請データ管理テーブルの搜索条件
            ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();
            shinseiInfo.setRyouikiNo(shinseiDataInfo.getRyouikiNo());//領域番号
            shinseiInfo.setJokyoIds(shinseiDataInfo.getJokyoIds());//応募状況ID
            shinseiInfo.setDelFlg("0");//所属機関承認日

            //DB更新条件
            String status = StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI;
            shinseiInfo.setSaishinseiFlg("0");
            shinseiInfo.setRyoikiKakuteiDate(new Date());
            shinseiDataInfoDao.updateShinseis(connection, shinseiInfo, status);

            //領域計画書（概要）情報管理
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
            RyoikiKeikakushoPk ryoikiPk = new RyoikiKeikakushoPk();
            ryoikiPk.setRyoikiSystemNo(shinseiDataInfo.getSystemNo());
            RyoikiKeikakushoInfo ryoikiInfo = ryoikikeikakushoInfoDao
                    .selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);

            //---領域計画書（概要）情報管理データ削除フラグチェック---
            String delFlag = ryoikiInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("当該領域計画書情報データは削除されています。",
                        new ErrorInfo("errors.9001"));
            }
            ryoikiInfo.setRyoikikeikakushoKakuteiFlg("1");//確定フラグ
            ryoikiInfo.setKakuteiDate(new Date());//領域計画書確定日を設定

            //---DB更新---
            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection,ryoikiInfo);
            success = true;
        }catch (SystemBusyException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
            "errors.4004"), se);
        }catch (DataAccessException e) {
            throw new ApplicationException("情報更新中にDBエラーが発生しました。",
                new ErrorInfo("errors.4001"), e);
        }finally {
            if (success) {
                DatabaseUtil.commit(connection);
            } else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    } 

    /**
     * 領域内研究計画調書確定解除。
     * @param  userInfo
     * @param  ryoikiGaiyoForm
     * @return void
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void cancelKakuteiRyoikiGaiyo(
            UserInfo userInfo, 
            ShinseiDataInfo shinseiDataInfo
            ) throws  NoDataFoundException, ApplicationException {

        //DBコネクションの取得
        Connection connection = null;   
        boolean success = false;
        ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);
        try {
            connection = DatabaseUtil.getConnection();

            //申請データ管理テーブルの搜索条件
            ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();
            shinseiInfo.setRyouikiNo(shinseiDataInfo.getRyouikiNo());//領域番号
            shinseiInfo.setJokyoIds(shinseiDataInfo.getJokyoIds());//応募状況ID
            shinseiInfo.setDelFlg("0");//削除フラグ 

            //DB更新条件
            String status = StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN;
            shinseiInfo.setRyoikiKakuteiDate(null);
            shinseiInfo.setRyoikiKakuteiDateFlg("1");
            shinseiDataInfoDao.updateShinseis(connection, shinseiInfo, status);

            //領域計画書（概要）情報管理
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
            RyoikiKeikakushoPk ryoikiPk = new RyoikiKeikakushoPk();
            ryoikiPk.setRyoikiSystemNo(shinseiDataInfo.getSystemNo());
            RyoikiKeikakushoInfo ryoikiInfo = ryoikikeikakushoInfoDao
                    .selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);

            //---領域計画書（概要）情報管理データ削除フラグチェック---
            String delFlag = ryoikiInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("当該領域計画書情報データは削除されています。",
                        new ErrorInfo("errors.9001"));
            }
            if(ryoikiInfo.getRyoikiJokyoId().equals(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN)||
                    ryoikiInfo.getRyoikiJokyoId().equals(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA)){
                ryoikiInfo.setRyoikiJokyoId(StatusCode.STATUS_SAKUSEITHU);
            }
            ryoikiInfo.setRyoikikeikakushoKakuteiFlg("0");//確定フラグ 
            ryoikiInfo.setKakuteiDate(null);//領域計画書確定日を設定

            //---DB更新---
            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection,ryoikiInfo);
            success = true;
        }catch (SystemBusyException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
            "errors.4004"), se);
        }catch (DataAccessException e) {
            throw new ApplicationException("情報更新中にDBエラーが発生しました。",
                new ErrorInfo("errors.4001"), e);
        }finally {
            if (success) {
                DatabaseUtil.commit(connection);
            } else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }
//2006/06/16 by jzx add end
    
    //  張拓 2006.6.15 ここから
    /**
     * 飛び番号リスト用のデータを取得する。
     * @param userInfo  UserInfo
     * @return list
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#selectAllTeishutuShorui(jp.go.jsps.kaken.model.vo.UserInfo)
     */
    public List selectTeisyutusyoTobiSinkiList(
            UserInfo userInfo,
            TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo
            ) throws NoDataFoundException, ApplicationException {

        // DBコネクションの取得
        Connection connection = null;
        List list = null;
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoinfoDao = new RyoikiKeikakushoInfoDao(userInfo);
            list = ryoinfoDao.selectAllTeishutuShorui(connection, teishutsuShoruiSearchInfo);
        } catch (SystemBusyException se) {
            throw new ApplicationException("検索実行中にエラーが発生しました。",
                    new ErrorInfo("errors.4004"), se);
        } catch(NoDataFoundException e){
            throw new NoDataFoundException("該当する情報が存在しませんでした。",
                    new ErrorInfo("errors.5002"), e);
        } catch(DataAccessException de){
            throw new ApplicationException("検索中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"),de); 
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        return list;
    }
    // 張拓 2006.6.15 ここまで
    
    //  2006/06/15 lwj add start
    /**
     * 提出書類一覧表示用のデータを取得する。
     * @param userInfo 実行するユーザ情報
     * @param teishutsuShoruiSearchInfo
     * @return List情報
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List selectTeishutuShoruiList(UserInfo userInfo,
            TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo
            )throws NoDataFoundException, ApplicationException {

        //DBコネクションの取得
        Connection connection = null;       
        List result = null;
        try{
            connection = DatabaseUtil.getConnection(); 
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao =
                new RyoikiKeikakushoInfoDao(userInfo);
            result = ryoikikeikakushoInfoDao.selectAllTeishutuShorui(
                    connection, teishutsuShoruiSearchInfo);
            new StatusGaiyoManager(userInfo).setRyoikiStatusName(result);
        } catch (SystemBusyException se) {
            throw new ApplicationException("DBアクセス処理でエラーが発生しました。",
                    new ErrorInfo("errors.4000"), se);
        } catch(NoDataFoundException e){
            throw new NoDataFoundException("該当する情報が存在しませんでした。",
                    new ErrorInfo("errors.5002"), e);
        } catch(DataAccessException de){
            throw new ApplicationException("検索中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"),de); 
        } finally {
            DatabaseUtil.closeConnection(connection);
        }          
        return result;
    }
    // 2006/06/15 lwj add end
    
   //  2006/06/15 宮　ここから
    /**
     * 提出確認（特定領域研究(新規領域)）一覧情報を取得する。
     * @param userInfo 実行するユーザ情報
     * @param ryoikiInfo
     * @return List 提出確認用のデータ
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchTeisyutuKakuninList(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException {

        Connection connection = null;
        List list=null;
        try{
           connection = DatabaseUtil.getConnection();
           RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
           list = ryoikikeikakushoInfoDao.searchRyoikiInfoList(connection, ryoikiInfo);
           new StatusGaiyoManager(userInfo).setRyoikiStatusName(list);
        }catch (SystemBusyException se) {
            throw new ApplicationException("DBアクセス処理でエラーが発生しました",
                    new ErrorInfo("errors.4000"), se);
        }catch(NoDataFoundException e){
            throw new NoDataFoundException("該当する情報が存在しませんでした。",
                    new ErrorInfo("errors.5002"), e);
        }catch(DataAccessException de){
            throw new ApplicationException("DBアクセス処理でエラーが発生しました。",
                    new ErrorInfo("errors.4000"), de);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }       
        return list;
    }    
    //  2006/06/15 宮　ここまで


    //add start ly 2006/06/15
    /**
     * 簡易仮領域番号発行情報を取得.<br><br>
     * 　※仮領域番号発行確認と仮領域番号発行却下確認画面で使用<br><br>
     * 自クラスのselectRyoikikeikakushoInfo(UserInfo,ryoikiPk)メソッドを呼ぶ。<br>
     * 引数に、第一引数userInfoと第二引数ryoikiPkoを格納した配列(ryoikiPk)を渡す。<br><br>
     * 取得したRyoikikeikakushoInfoを返却する。<br><br>
     * 
     * @param userInfo  UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @return 簡易仮領域情報(RyoikikeikakushoInfo)
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#sryoikiInfo(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
     */
    public RyoikiKeikakushoInfo selectRyoikiInfo(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException {

        // DBコネクションの取得
        Connection connection = null;
        RyoikiKeikakushoInfo ryoikiInfo = null;
        try {
            connection = DatabaseUtil.getConnection();

            //---簡易仮領域情報
            RyoikiKeikakushoInfoDao ryoikiDao = new RyoikiKeikakushoInfoDao(
                    userInfo);
            ryoikiInfo = ryoikiDao.selectRyoikiKeikakushoInfo(connection,
                    ryoikiPk);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("当該応募情報は既に削除されています。",
                    new ErrorInfo("errors.9001"), e);
        } catch (DataAccessException ex) {
            throw new ApplicationException("検索実行中にエラーが発生しました。",
                    new ErrorInfo("errors.4004"), ex);
        }catch (SystemBusyException se) {
            throw new ApplicationException("検索実行中にエラーが発生しました。", new ErrorInfo(
            "errors.4004"), se);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        return ryoikiInfo;
    }
    //add end ly 2006/06/16
    
    //add start ly 2006/06/16
    /**
     * 仮領域番号発行確認を実行（所属機関担当者）
     * 更新処理が正常に行われたとき、コミットを行う。<br>
     * 　DatabaseUtilクラスのcommit()メソッドにて、コミットする。<br><br>
     * 仮領域番号発行確認する。（所属機関担当者） 処理後、申RYOIKI_JOKYO_ID=33に更新される。
     * 更新処理の途中で例外が発生したとき、ロールバックを行う。<br>
     * 　DatabaseUtilクラスのrollback()メソッドにて、ロールバックする。<br><br>
     * 
     * @param userInfo          UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#shoninApplication(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk)
     */
    public void confirmKariBangoHakko(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException {

        Connection connection = null;
        RyoikiKeikakushoInfo ryoikiInfo = null;
        boolean success = false;
        // 申請データ管理DAO
        RyoikiKeikakushoInfoDao ryoikiDao = new RyoikiKeikakushoInfoDao(userInfo);

        try {
            // DBコネクションの取得
            connection = DatabaseUtil.getConnection();
            ryoikiInfo = ryoikiDao.selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);

            // ---領域計画書（概要）情報管理データ削除フラグチェック---
            String delFlag = ryoikiInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException(
                        "当該領域計画書情報は既に削除されています。RYOIKI_SYSTEM_NO="
                                + ryoikiPk.getRyoikiSystemNo(),
                                new ErrorInfo("errors.9025"));
            }

            //---DB更新---
            ryoikiInfo.setRyoikiJokyoId(StatusCode.STATUS_KARIRYOIKINO_HAKKUZUMI); // （応募状況ID）

            int existCount = 0;// DBで該当発行した仮領域番号の件数
            int retryCount = 0;// リトライ回数

            // 発行した番号はDBに0件でなければ、仮領域番号の発行を最大リトライ数回繰り返す
            do {
                ryoikiInfo.setKariryoikiNo(RandomPwd.generate(true, false,true, 5)); // 仮領域番号が発行
                existCount = ryoikiDao.KariryoikiNoCount(connection, ryoikiInfo);
                retryCount++;
            } while (existCount != 0 && retryCount < KARIRYOIKI_NO_MAX_RETRY_COUNT);

            // 発行成功の場合、登録する
            if (existCount == 0) {
                ryoikiDao.updateRyoikiKeikakushoInfo(connection, ryoikiInfo);
                success = true;

            // 発行失敗の場合、エラーが表示
            } else {
                throw new ApplicationException(
                        "発行しようとする仮領域番号がすでに登録されているため、仮領域番号が発行できません。",
                        new ErrorInfo("errors.9029"));
            }
        }catch (DataAccessException e) {
            throw new ApplicationException("領域計画書（概要）情報更新中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4001"), e);
        }catch (SystemBusyException e) {
            throw new ApplicationException("領域計画書（概要）情報管理データ取得中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        }finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }

    /**
     * 更新処理が正常に行われたとき、コミットを行う。<br>
     * DatabaseUtilクラスのcommit()メソッドにて、コミットする。<br>
     * <br>
     * 仮領域番号発行却下確認する。（所属機関担当者） 処理後、申RYOIKI_JOKYO_ID=32に更新される。
     * 更新処理の途中で例外が発生したとき、ロールバックを行う。<br>
     * DatabaseUtilクラスのrollback()メソッドにて、ロールバックする。<br>
     * <br>
     * 
     * @param userInfo UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @return なし
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#shoninApplication(jp.go.jsps.kaken.model.vo.UserInfo,
     *      jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk)
     */
    public void rejectKariBangoHakko(UserInfo userInfo, RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException {

        Connection connection = null;
        RyoikiKeikakushoInfo ryoikiInfo = null;
        boolean success = false;

        RyoikiKeikakushoInfoDao ryoikiDao = new RyoikiKeikakushoInfoDao(userInfo);
        try {
            // DBコネクションの取得
            connection = DatabaseUtil.getConnection();

            ryoikiInfo = ryoikiDao.selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);

            // ---領域計画書（概要）情報管理データ削除フラグチェック---
            String delFlag = ryoikiInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException(
                        "当該領域計画書情報は既に削除されています。RYOIKI_SYSTEM_NO="
                                + ryoikiPk.getRyoikiSystemNo(), new ErrorInfo(
                                "errors.9025"));
            }

            //---DB更新---
            ryoikiInfo.setRyoikiJokyoId(StatusCode.STATUS_KARIRYOIKINO_HAKKUKYAKKA); // （応募状況ID）仮領域番号発行却下
            ryoikiDao.updateRyoikiKeikakushoInfo(connection, ryoikiInfo);
            success = true;

        }catch (DataAccessException e) {
            throw new ApplicationException("領域計画書（概要）情報更新中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4001"), e);
        }catch (SystemBusyException e) {
            throw new ApplicationException("領域計画書（概要）情報管理データ取得中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        }finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }

    /**
     * 応募書類の提出書出力（基盤研究等、特定領域研究）
     * @param UserInfo userInfo ログイン者情報
     * @param checkInfo チェックリスト検索条件
     * @return　FileResource　出力情報CSVファウル
     */
    public FileResource createOuboTeishutusho(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws ApplicationException {

        //DBレコード取得
        List csv_data = null;
        Connection connection = null;
        RyoikiKeikakushoInfoDao ryoikiDao = new RyoikiKeikakushoInfoDao(userInfo);
        try {
            //DBコネクションの取得
            connection = DatabaseUtil.getConnection();
            csv_data = ryoikiDao.createTokuteiShinki(connection,ryoikiInfo);
        }catch (ApplicationException e) {
            throw e;
        } finally {
            DatabaseUtil.closeConnection(connection);
        }

        //-----------------------
        // CSVファイル出力
        //-----------------------
        String csvFileName = "tokutei_shinki";
        String filepath = null;
        synchronized(log){
            filepath = OUBO_WORK_FOLDER + userInfo.getShozokuInfo().getShozokuTantoId()
                     + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";   
        }
        CsvUtil.output(csv_data, filepath, csvFileName);

        //-----------------------
        // 依頼書ファイルのコピー
        //-----------------------
        FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + OUBO_FORMAT_FILE_NAME_TOKUTEI),
                  new File(filepath + OUBO_FORMAT_FILE_NAME_TOKUTEI));
        FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + "$5"), new File(filepath + "$"));
        
        //-----------------------
        // ファイルの圧縮
        //-----------------------
        String comp_file_name = csvFileName + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        FileUtil.fileCompress(filepath, filepath, comp_file_name);

        //-------------------------------------
        //作成したファイルを読み込む。
        //-------------------------------------
        File exe_file = new File(filepath + comp_file_name + ".EXE");
        FileResource compFileRes = null;
        try {
            compFileRes = FileUtil.readFile(exe_file);
        } catch (IOException e) {
            throw new ApplicationException(
                "作成ファイル'" + comp_file_name + ".EXE'情報の取得に失敗しました。",
                new ErrorInfo("errors.8005"),
                e);
        } finally{
            //作業ファイルの削除
            FileUtil.delete(exe_file.getParentFile());
        }

        //自己解凍型圧縮ファイルをリターン
        return compFileRes;
    }
    //add end ly 2006/06/16

//  add start zjp 2006/06/15
    /**
     * 応募書類情報を取得.<br><br>
     * 　※応募書類画面で使用<br><br>
     * 
     * 自クラスのsearchSyonin(UserInfo,ryoikiPk)メソッドを呼ぶ。<br>
     * 引数に、第一引数userInfoと第二引数ryoikiPkoを格納した配列(ryoikiPk)を渡す。<br><br>
     * 
     * 取得したRyoikikeikakushoInfoを返却する。<br><br>
     * 
     * @param userInfo  UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @return 応募書類承認情報(RyoikikeikakushoInfo)
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#searchSyonin(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk)
     */
    public RyoikiKeikakushoInfo searchOuboSyoruiInfo(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo)
            throws NoDataFoundException, ApplicationException {

        // DBコネクションの取得
        Connection connection = null;

        // ---応募書類承認情報
        RyoikiKeikakushoInfo ryoikiInfo = null;
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(
                    userInfo);
            ryoikiInfo = ryoikikeikakushoInfoDao.searchSyoninInfo(connection,
                    ryoikiPk, ryoikikeikakushoInfo);
        } catch (DataAccessException e) {
            throw new ApplicationException("検索中にDBエラーが発生しました。", new ErrorInfo(
                    "errors.4004"), e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        return ryoikiInfo;
    }

    /**
     * 応募書類を承認.<br>
     * ※応募書類承認画面で使用<br>
     * 自クラスのsearchSyonin(UserInfo,ryoikiPk)メソッドを呼ぶ。<br>
     * 引数に、第一引数userInfoと第二引数ryoikiPkoを格納した配列(ryoikiPk)を渡す。<br>
     * <br>
     * @param userInfo UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#searchSyonin(jp.go.jsps.kaken.model.vo.UserInfo,
     *      jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk)
     */
    public void approveOuboSyorui(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException {

        //DBコネクションの取得
        Connection connection = null;   
        boolean success = false; 
        String kariryoikiNo = null;
        
        //排他制御のため既存データを取得する
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;
        
        //申請データ管理DAO
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
        ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);
        try {
            // DBコネクションの取得
            connection = DatabaseUtil.getConnection();            
            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao.selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);   
            kariryoikiNo = ryoikikeikakushoInfo.getKariryoikiNo();
            ryoikikeikakushoInfoDao.searchSystemNoList(connection,kariryoikiNo,ryoikiInfo);

            // ---領域計画書（概要）情報管理データ削除フラグチェック---
            String delFlag = ryoikikeikakushoInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("当該領域計画書情報データは削除されています。",
                        new ErrorInfo("errors.9001"));
            }
  
            // ---DB更新---
            ryoikikeikakushoInfo.setRyoikiJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU); // 領域計画書概要応募状況ID 
            ryoikikeikakushoInfo.setCancelFlg(StatusCode.SAISHINSEI_FLG_DEFAULT);      // Cancelフラグ 
            ryoikikeikakushoInfo.setShoninDate(new Date());                            // 所属機関承認日                                     // 所属機関承認日
            ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();
            String status = StatusCode.STATUS_GAKUSIN_SHORITYU;                        // 応募状況ID
            shinseiDataInfo.setRyouikiNo(kariryoikiNo);                                // 仮領域番号
            shinseiDataInfo.setJokyoIds(ryoikiInfo.getJokyoIds());                      
        
            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection, ryoikikeikakushoInfo);
            shinseiDataInfoDao.updateShinseis(connection,shinseiDataInfo,status);
            success = true;
        }
        catch (NoDataFoundException e) {
            throw e;
        }
        catch (DataAccessException e) {
            throw new ApplicationException("排他取得中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        }       
        finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }       
    }
    
    /**
     * 応募書類を却下.<br>
     * 　※応募書類却下画面で使用<br>
     * 
     * 自クラスのsearchKyakkaSave(UserInfo,ryoikiPk)メソッドを呼ぶ。<br>
     * 引数に、第一引数userInfoと第二引数ryoikiPkoを格納した配列(ryoikiPk)を渡す。<br><br>
     * 
     * @param userInfo  UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#searchSyonin(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk)
     */
    public void rejectOuboSyorui(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException {

        // DBコネクションの取得
        Connection connection = null;
        boolean success = false;
        String kariryoikiNo = null;

        // 排他制御のため既存データを取得する
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;

        // 申請データ管理DAO
        RyoikiKeikakushoInfoDao ryoikiDao = new RyoikiKeikakushoInfoDao(userInfo);
        ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);

        try {
            // DBコネクションの取得
            connection = DatabaseUtil.getConnection();
            ryoikikeikakushoInfo = ryoikiDao.selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);
            kariryoikiNo = ryoikikeikakushoInfo.getKariryoikiNo();
            ryoikiDao.searchSystemNoList(connection, kariryoikiNo, ryoikiInfo);

            // ---領域計画書（概要）情報管理データ削除フラグチェック---
            String delFlag = ryoikikeikakushoInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("当該領域計画書情報データは削除されています。",
                        new ErrorInfo("errors.9001"));
            }

            // ---DB更新---
            ryoikikeikakushoInfo.setRyoikiJokyoId(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA); // 領域計画書概要応募状況ID
            ryoikikeikakushoInfo.setShoninDate(null); // 所属機関承認日
            ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();
            String status = StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI; // 応募状況ID
            shinseiDataInfo.setRyouikiNo(kariryoikiNo); // 仮領域番号
            shinseiDataInfo.setJokyoIds(ryoikiInfo.getJokyoIds());

            ryoikiDao.updateRyoikiKeikakushoInfo(connection,ryoikikeikakushoInfo);
            shinseiDataInfoDao.updateShinseis(connection, shinseiDataInfo,status);
            success = true;
        }catch (NoDataFoundException e) {
            throw e;
        }catch (DataAccessException e) {
            throw new ApplicationException("排他取得中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        }finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }
    
    /**
     * 確認メール自動通知送信実行
     * 
     * @param userInfo
     * @throws ApplicationException
     */
    public void sendMailKakuninTokusoku(UserInfo userInfo)
            throws ApplicationException {

        List jigyoList = null;
        Connection connection = null;

        //承認締め切り日時の設定
        DateUtil du = new DateUtil();
        du.addDate(DATE_BY_KAKUNIN_TOKUSOKU);   //指定日付を加算する
        Date date = du.getCal().getTime();

        try {
            //DBコネクションの取得
            connection = DatabaseUtil.getConnection();
            //事業管理DAO
            RyoikiKeikakushoInfoDao ryoikidao = new RyoikiKeikakushoInfoDao(userInfo);
            try {
                jigyoList = ryoikidao.selectKakuninTokusokuInfo(connection, DATE_BY_KAKUNIN_TOKUSOKU);
            } catch (NoDataFoundException e) {
                //何も処理しない
            } catch (DataAccessException e) {
                throw new ApplicationException("承認確認メール自動通知送信事業データ取得中にDBエラーが発生しました。",
                        new ErrorInfo("errors.4004"), e);
            }
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        connection = null;

        //該当する事業が存在しなかった場合
        if (jigyoList == null || jigyoList.size() == 0) {
            String strDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
            String msg = "募集締め切りが[" + strDate + "]の事業で、承認確認の申請書を持つ事業は存在しません。";
            log.info(msg);
            return;
        }

        //-----データ構造を変換（重複データを含む単一リストから所属機関担当者ごとのマップへ変換する）
        //全体Map
        Map saisokuMap = new HashMap();
        for (int i = 0; i < jigyoList.size(); i++) {

            //1レコード
            Map recordMap = (Map) jigyoList.get(i);
            String nendo = (String) recordMap.get("NENDO");
            BigDecimal kaisu = (BigDecimal) recordMap.get("KAISU");
            String jigyo_name = (String) recordMap.get("JIGYO_NAME");
            String tanto_email = (String) recordMap.get("TANTO_EMAIL");

            String kaisu_hyoji = "";
            if (kaisu.intValue() > 1) {
                kaisu_hyoji = "第" + kaisu + "回 "; //回数が1回以上の場合は表示する
            }

            //事業名を「年度＋事業名」へ変換し、問い合わせ先を（改行＋全角空白）で結合。
// UPDATE START 2007-07-06 BIS 王志安
//            String jigyo_info = new StringBuffer("【研究種目名】平成")
//                                            .append(nendo)
//                                            .append("年度 ")
//                                            .append(kaisu_hyoji)
//                                            .append(jigyo_name)
//                                            .toString();
            String jigyo_info = new StringBuffer("    平成")
								            .append(nendo)
								            .append("年度 ")
								            .append(kaisu_hyoji)
								            .append(jigyo_name)
								            .toString();
//　UPDATE END 2007-07-06 BIS 王志安
            //全体Mapに当該所属機関担当者データが存在していた場合
            if (saisokuMap.containsKey(tanto_email)) {
                List dataList = (List) saisokuMap.get(tanto_email);
                dataList.add(jigyo_info); //次レコードに事業情報
            } else {
                //初の事業の場合   
                List dataList = new ArrayList();
                dataList.add(tanto_email); //1レコード目にメールアドレス
                dataList.add(jigyo_info); //2レコード目に事業情報
                saisokuMap.put(tanto_email, dataList);
            }
        }

        //---------------
        // メール送信
        //---------------
        //-----メール本文ファイルの読み込み
        String content = null;
        try {
            File contentFile = new File(CONTENT_SHINSEISHO_KAKUNIN_TOKUSOKU);
            FileResource fileRes = FileUtil.readFile(contentFile);
            content = new String(fileRes.getBinary());
        } catch (FileNotFoundException e) {
            log.warn("メール本文ファイルが見つかりませんでした。", e);
            return;
        } catch (IOException e) {
            log.warn("メール本文ファイル読み込み時にエラーが発生しました。", e);
            return;
        }

        //承認締め切り日付フォーマットを変更する
        String kigenDate = new StringBuffer("平成")
                                .append(new DateUtil(date).getNendo())
                                .append("年")
                                .append(new SimpleDateFormat("M月d日").format(date))
                                .toString();

        //-----メール送信（１人ずつ送信）
        for (Iterator iter = saisokuMap.keySet().iterator(); iter.hasNext();) {

            //所属機関担当者ごとのデータリストを取得する
            String tantoEmail = (String) iter.next();
            List dataList = (List) saisokuMap.get(tantoEmail);

            //メールアドレスが設定されていない場合は処理を飛ばす
            String to = (String) dataList.get(0);
            if (to == null || to.length() == 0) {
                continue;
            }

            //-----メール本文ファイルの動的項目変更
// UPDATE START 2007-07-24 BIS 王志安
//            StringBuffer jigyoNameList = new StringBuffer("\n");
//            for (int i = 1; i < dataList.size(); i++) {
//            	jigyoNameList.append(dataList.get(i)).append("\n");
//            }
            StringBuffer jigyoNameList = new StringBuffer("");
            for (int i = 1; i < dataList.size(); i++) {
            	jigyoNameList.append("\n");
            	jigyoNameList.append(dataList.get(i));
            }
// UPDATE END 2007-07-24 BIS 王志安
            String[] param = new String[] { kigenDate, //承認締め切り日付
                                    jigyoNameList.toString()//事業名リスト
                                };
            String body = MessageFormat.format(content, param);

            if (log.isDebugEnabled()){
                log.debug("送信情報：********************\n" + body);
            }
            try {
                SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
                mailer.sendMail(FROM_ADDRESS, //差出人
                        to, //to
                        null, //cc
                        null, //bcc
                        SUBJECT_SHINSEISHO_KAKUNIN_TOKUSOKU, //件名
                        body); //本文
            } catch (Exception e) {
                log.warn("メール送信に失敗しました。", e);
                continue;
            }
        }
    }
    
    /**
     * 承認督促メール自動通知送信実行
     * 特定領域研究事業のみ
     * @param userInfo
     * @throws ApplicationException
     */
    public void sendMailShoninTokusoku(UserInfo userInfo)
            throws ApplicationException {

        List jigyoList = null;
        Connection connection = null;

        //承認締め切り日時の設定
        DateUtil du = new DateUtil();
        du.addDate(DATE_BY_KAKUNIN_TOKUSOKU);   //指定日付を加算する
        Date date = du.getCal().getTime();

        try {
            //DBコネクションの取得
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikidao = new RyoikiKeikakushoInfoDao(userInfo);
            try {
                jigyoList = ryoikidao.selectShoninTokusokuInfo(connection, DATE_BY_SHONIN_TOKUSOKU);
            } catch (NoDataFoundException e) {
                //何も処理しない
            } catch (DataAccessException e) {
                throw new ApplicationException("承認督促メール自動通知送信事業データ取得中にDBエラーが発生しました。",
                        new ErrorInfo("errors.4004"), e);
            }
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        connection = null;

        //該当する事業が存在しなかった場合
        if (jigyoList == null || jigyoList.size() == 0) {
            String strDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
            String msg = "募集締め切りが[" + strDate + "]の事業で、承認督促の申請書を持つ事業は存在しません。";
            log.info(msg);
            return;
        }

        //-----データ構造を変換（重複データを含む単一リストから所属機関担当者ごとのマップへ変換する）
        //全体Map
        Map saisokuMap = new HashMap();
        for (int i = 0; i < jigyoList.size(); i++) {

            //1レコード
            Map recordMap = (Map) jigyoList.get(i);
            String nendo = (String) recordMap.get("NENDO");
            BigDecimal kaisu = (BigDecimal) recordMap.get("KAISU");
            String jigyo_name = (String) recordMap.get("JIGYO_NAME");
            String tanto_email = (String) recordMap.get("TANTO_EMAIL");

            String kaisu_hyoji = "";
            if (kaisu.intValue() > 1) {
                kaisu_hyoji = "第" + kaisu + "回 "; //回数が1回以上の場合は表示する
            }

            //事業名を「年度＋事業名」へ変換し、問い合わせ先を（改行＋全角空白）で結合。
// UPDATE START 2007-07-06 王志安
//            String jigyo_info = new StringBuffer("【研究種目名】平成")
//                                            .append(nendo)
//                                            .append("年度 ")
//                                            .append(kaisu_hyoji)
//                                            .append(jigyo_name)
//                                            .toString();
            String jigyo_info = new StringBuffer("    平成")
								            .append(nendo)
								            .append("年度 ")
								            .append(kaisu_hyoji)
								            .append(jigyo_name)
								            .toString();
// UPDATE END 2007-07-06 王志安
            //全体Mapに当該所属機関担当者データが存在していた場合
            if (saisokuMap.containsKey(tanto_email)) {
                List dataList = (List) saisokuMap.get(tanto_email);
                dataList.add(jigyo_info); //次レコードに事業情報
            } else {
                //初の事業の場合   
                List dataList = new ArrayList();
                dataList.add(tanto_email); //1レコード目にメールアドレス
                dataList.add(jigyo_info); //2レコード目に事業情報
                saisokuMap.put(tanto_email, dataList);
            }
        }

        //---------------
        // メール送信
        //---------------
        //-----メール本文ファイルの読み込み
        String content = null;
        try {
            File contentFile = new File(CONTENT_RYOIKIGAIYO_SHONIN_TOKUSOKU);
            FileResource fileRes = FileUtil.readFile(contentFile);
            content = new String(fileRes.getBinary());
        } catch (FileNotFoundException e) {
            log.warn("メール本文ファイルが見つかりませんでした。", e);
            return;
        } catch (IOException e) {
            log.warn("メール本文ファイル読み込み時にエラーが発生しました。", e);
            return;
        }

        //承認締め切り日付フォーマットを変更する
        String kigenDate = new StringBuffer("平成")
                                .append(new DateUtil(date).getNendo())
                                .append("年")
                                .append(new SimpleDateFormat("M月d日").format(date))
                                .toString();

        //-----メール送信（１人ずつ送信）
        for (Iterator iter = saisokuMap.keySet().iterator(); iter.hasNext();) {

            //所属機関担当者ごとのデータリストを取得する
            String tantoEmail = (String) iter.next();
            List dataList = (List) saisokuMap.get(tantoEmail);

            //メールアドレスが設定されていない場合は処理を飛ばす
            String to = (String) dataList.get(0);
            if (to == null || to.length() == 0) {
                continue;
            }

            //-----メール本文ファイルの動的項目変更
// UPDATE START 2007-07-24 BIS 王志安
//            StringBuffer jigyoNameList = new StringBuffer("\n");
//            for (int i = 1; i < dataList.size(); i++) {
//            	jigyoNameList.append(dataList.get(i)).append("\n");
//            }
			StringBuffer jigyoNameList = new StringBuffer("");
			for (int i = 1; i < dataList.size(); i++) {
				jigyoNameList.append("\n");
				jigyoNameList.append(dataList.get(i));
			}
// UPDATE END 2007-07-24 BIS 王志安
            String[] param = new String[] { kigenDate, //承認締め切り日付
                                    jigyoNameList.toString() //事業名リスト
                                };
            String body = MessageFormat.format(content, param);

            if (log.isDebugEnabled()){
                log.debug("送信情報：********************\n" + body);
            }
            try {
                SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
                mailer.sendMail(FROM_ADDRESS, //差出人
                        to, //to
                        null, //cc
                        null, //bcc
                        SUBJECT_RYOIKIGAIYO_SHONIN_TOKUSOKU, //件名
                        body); //本文
            } catch (Exception e) {
                log.warn("メール送信に失敗しました。", e);
                continue;
            }
        }
    }
// add end zjp 2006/06/15
    
// 宮 2006/06/19 ここから
    /**
     * 仮領域番号発行情報を登録する
     * 
     * @param userInfo ユーザ情報
     * @param addInfo
     * @param pkInfo
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registKariBangoHakkoInfo(UserInfo userInfo,JigyoKanriPk pkInfo)
            throws NoDataFoundException,ApplicationException {

        // DBコネクションの取得
        Connection connection = null;  

        if ( log.isDebugEnabled() ){
            log.debug("仮領域番号発行情報を登録開始");
        }
        
        //2006/07/17 start
        String jigyoId = pkInfo.getJigyoId();
        ShinseishaInfo addInfo = userInfo.getShinseishaInfo();

        String chNendo = jigyoId.substring(0, 2);
        chNendo = "20" + chNendo; //2000年以降でよい
        if (StringUtil.isDigit(chNendo)){
            addInfo.setNenrei("" + DateFormat.getAgeOnApril1st(
                    addInfo.getBirthday(),StringUtil.parseInt(chNendo))); //年齢
        }else{
            //西暦が不正な場合は？
        }
        //2006/07/17 end
        
        //--------------------
        // アンケートデータ登録
        //--------------------
        JigyoKanriInfo jigyoKanriInfo=null;
        boolean success = false;
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao =
                new RyoikiKeikakushoInfoDao(userInfo);

            // データが存在した場合、エラーとする
            ryoikikeikakushoInfoDao.isExistRyoikiInfo(connection, addInfo, pkInfo);
            // 領域計画書概要のテーブルにデータが挿入され、所属研究機関担当者宛に確認依頼のメールが送信される。
            JigyoKanriInfoDao jigyoKanriInfoDao = 
                new JigyoKanriInfoDao(userInfo);
            jigyoKanriInfo = 
                jigyoKanriInfoDao.selectJigyoKanriInfo(connection, pkInfo);
            RyoikiKeikakushoInfo ryoikiKeikakushoInfo=new RyoikiKeikakushoInfo();
            ryoikiKeikakushoInfo.setRyoikiSystemNo(ShinseiMaintenance.getSystemNumberForGaiyo());
            
            ryoikiKeikakushoInfo.setJigyoId(jigyoKanriInfo.getJigyoId());
            ryoikiKeikakushoInfo.setNendo(jigyoKanriInfo.getNendo());
            ryoikiKeikakushoInfo.setKaisu(jigyoKanriInfo.getKaisu());
            ryoikiKeikakushoInfo.setJigyoName(jigyoKanriInfo.getJigyoName());           
            ryoikiKeikakushoInfo.setShinseishaId(addInfo.getShinseishaId());
            ryoikiKeikakushoInfo.setSakuseiDate(new Date());
            ryoikiKeikakushoInfo.setNameKanjiSei(addInfo.getNameKanjiSei());
            ryoikiKeikakushoInfo.setNameKanjiMei(addInfo.getNameKanjiMei());
            ryoikiKeikakushoInfo.setNameKanaSei(addInfo.getNameKanaSei());
            ryoikiKeikakushoInfo.setNameKanaMei(addInfo.getNameKanaMei());
            ryoikiKeikakushoInfo.setNenrei(addInfo.getNenrei());         
            ryoikiKeikakushoInfo.setKenkyuNo(addInfo.getKenkyuNo());
            ryoikiKeikakushoInfo.setShozokuCd(addInfo.getShozokuCd());
            ryoikiKeikakushoInfo.setShozokuName(addInfo.getShozokuName());
            ryoikiKeikakushoInfo.setShozokuNameRyaku(addInfo.getShozokuNameRyaku());
            ryoikiKeikakushoInfo.setBukyokuCd(addInfo.getBukyokuCd());
            ryoikiKeikakushoInfo.setBukyokuName(addInfo.getBukyokuName());
            ryoikiKeikakushoInfo.setBukyokuNameRyaku(addInfo.getBukyokuNameRyaku());
            ryoikiKeikakushoInfo.setShokushuCd(addInfo.getShokushuCd());
            ryoikiKeikakushoInfo.setShokushuNameKanji(addInfo.getShokushuNameKanji());
            ryoikiKeikakushoInfo.setShokushuNameRyaku(addInfo.getShokushuNameRyaku());
            ryoikiKeikakushoInfo.setRyoikiJokyoId(StatusCode.STATUS_KARIRYOIKINO_KAKUNINMATI);
            ryoikiKeikakushoInfo.setRyoikikeikakushoKakuteiFlg(IShinseiMaintenance.FLAG_RYOIKIKEIKAKUSHO_NOT_KAKUTEI); 
            ryoikiKeikakushoInfo.setCancelFlg(IShinseiMaintenance.FLAG_RYOIKIKEIKAKUSHO_NOT_CANCEL); 
            ryoikiKeikakushoInfo.setDelFlg(IShinseiMaintenance.FLAG_RYOIKIKEIKAKUSHO_NOT_CANCEL); 
            
//          ADD START 2007/07/23 BIS 趙一非
            ryoikiKeikakushoInfo.setZennendoOuboFlg(pkInfo.getZennendoOuboFlg());
            ryoikiKeikakushoInfo.setZennendoOuboNo(pkInfo.getZennendoOuboNo());
            ryoikiKeikakushoInfo.setZennendoOuboRyoikiRyaku(pkInfo.getZennendoOuboRyoikiRyaku());
            ryoikiKeikakushoInfo.setZennendoOuboSettei(pkInfo.getZennendoOuboSettei());
            //ADD END 2007/07/23 BIS 趙一非 
            
            ryoikikeikakushoInfoDao.insertRyoikiKeikakushoInfo(connection,ryoikiKeikakushoInfo);
            
            // 登録正常終了
            success = true;
        } catch (DuplicateKeyException se) {
            throw new ApplicationException("検索実行中にエラーが発生しました。",
                    new ErrorInfo("errors.4007",new String[] { "仮領域番号発行情報" }),se);
        } catch (SystemBusyException se) {
            throw new ApplicationException("検索実行中にエラーが発生しました。",
                                           new ErrorInfo("errors.4004"), se);
        } catch(DataAccessException de){
            throw new ApplicationException("DBアクセス処理でエラーが発生しました",
                    new ErrorInfo("errors.4000"), de);
        } finally {
            try {
                if (success) {
                    DatabaseUtil.commit(connection);
                } else {
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ApplicationException("DBアクセス処理でエラーが発生しました",
                        new ErrorInfo("errors.4000"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
//      2006/06/27 追加　李義華　ここから
        //---------------
        // メール送信
        //---------------
        //-----メール差出人情報取得
        String to = null;
        try{
            //当該申請者の所属機関担当者情報を取得する
            String shozokuCd = addInfo.getShozokuCd();
            IShozokuMaintenance shozokuMainte = new ShozokuMaintenance();
            List shozokuTantoList = shozokuMainte.searchShozokuInfo(userInfo, 
                                                                    shozokuCd);
            //所属担当者情報が取得できなかった場合
            if(shozokuTantoList == null || shozokuTantoList.size() == 0){
                log.warn("所属機関担当者情報を取得できませんでした。所属コード:"+shozokuCd);
                return;
            }

            //リストの１人目の情報を取得する（担当者Email1に対してのみ送信する）
            to = (String)( (Map)shozokuTantoList.get(0) ).get("TANTO_EMAIL");

        }catch(ApplicationException e){
            log.warn("メール宛先情報取得に失敗しました。", e);
            return;
        }

        //-----メール本文ファイルの読み込み
        String content = null;
        try{
            File contentFile = new File(CONTENT_KARIRYOIKINO_KAKUNIN_IRAI);
            FileResource fileRes = FileUtil.readFile(contentFile);
            content = new String(fileRes.getBinary());
        }catch(FileNotFoundException e){
            log.warn("メール本文ファイルが見つかりませんでした。", e);
            return;
        }catch(IOException e){
            log.warn("メール本文ファイル読み込み時にエラーが発生しました。",e);
            return;
        }
        
//2006/07/31 苗　追加ここから
        //事業年度の設定
        String nendo = "";
        if(jigyoKanriInfo.getKaisu().equals("1")){
            nendo = "平成"+ jigyoKanriInfo.getNendo() +"年度";
        } else {
            nendo = "平成" + jigyoKanriInfo.getNendo() + "年度" + " 第" + jigyoKanriInfo.getKaisu()
                    + "回 ";
        }
        //-----メール本文ファイルの動的項目変更
        String[] param = new String[]{
            nendo,
//2006/07/31 苗　追加ここまで            
            jigyoKanriInfo.getJigyoName(), //事業名
            addInfo.getNameKanjiSei(),     //申請者名－姓
            addInfo.getNameKanjiMei(),     //申請者名－名                   
        };
        content = MessageFormat.format(content, param);
        
        if (log.isDebugEnabled()){
            log.debug("content:" + content);
        }

        //-----メール送信
        try{
            SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
            mailer.sendMail(FROM_ADDRESS,                       //差出人
                            to,                                 //to
                            null,                               //cc
                            null,                               //bcc
                            SUBJECT_KARIRYOIKINO_KAKUNIN_IRAI,  //件名
                            content);                           //本文
        }catch(Exception e){
            log.warn("メール送信に失敗しました。",e);
            return;
        }
//2006/06/27 追加　李義華　ここまで
    }
    
//  mcj add start       
    /**
     * 一括受理(提出書類)を実行
     * 
     * @param  userInfo
     * @param  searchInfo
     * @return なし 
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void executeIkkatuJuri(UserInfo userInfo, 
            TeishutsuShoruiSearchInfo searchInfo)
            throws DataAccessException,
            NoDataFoundException,
            ApplicationException {

        Connection connection = null;   
        boolean success = false;
        
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = new RyoikiKeikakushoInfo();
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk();
        
        // 申請データ管理DAO
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
        ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao (userInfo);
        MasterKenkyushaInfoDao mKenkyuDao = new MasterKenkyushaInfoDao (userInfo);
        List ryoikiResult =  null;
        try {
            // DBコネクションの取得
            connection = DatabaseUtil.getConnection();
            ryoikiResult = ryoikikeikakushoInfoDao.selectAllTeishutuShorui(connection, searchInfo);
        }
        catch (DataAccessException e) {
            throw new ApplicationException("領域計画書（概要）情報管理データ排他取得中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"), e);
        }
        try{
          for(int i=0; i<ryoikiResult.size(); i++){
              HashMap hashMap = (HashMap)ryoikiResult.get(i);
              //検索の結果の取る
              String ryoikiSystemNo = (String)hashMap.get("RYOIKI_SYSTEM_NO");  //システム受付番号
              String kariryoikiNo = (String)hashMap.get("KARIRYOIKI_NO");       //仮領域番号
              
              pkInfo.setRyoikiSystemNo(ryoikiSystemNo);

              //領域計画書を参照することができるかチェックする
              ryoikikeikakushoInfo = ryoikikeikakushoInfoDao
                        .selectRyoikiKeikakushoInfoForLock(connection, pkInfo);
              
              ryoikikeikakushoInfo.setRyoikiJokyoId(StatusCode.STATUS_GAKUSIN_JYURI );//応募状況=06
              ryoikikeikakushoInfo.setJyuriDate(new Date());                          //システム日付

              //領域計画書（概要）情報が更新 
              ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection,ryoikikeikakushoInfo);
              
              ryoikikeikakushoInfo.setJokyoIds(searchInfo.getSearchJokyoId());

              //指定された事業コードの申請データ管理テーブル（SHINSEIDATAKANRI）のシステム受付番号を取得する。
              List sysNo = ryoikikeikakushoInfoDao.searchSystemNoList(
                        connection, kariryoikiNo, ryoikikeikakushoInfo);
              
              for(int j=0; j<sysNo.size(); j++){
                  HashMap sysNomap = (HashMap)sysNo.get(j);
                  String SNo = (String)sysNomap.get("SYSTEM_NO"); 
                  ShinseiDataPk shinseiPk = new ShinseiDataPk();
                  shinseiPk.setSystemNo(SNo);
                  
                  ShinseiDataInfo shinseiInfo=shinseiDao.selectShinseiDataInfo(connection, shinseiPk, true);
                  //研究者マスタの存在チェックを実施
                  KenkyushaPk kenkyushaPk=new KenkyushaPk();
                  kenkyushaPk.setKenkyuNo(shinseiInfo.getDaihyouInfo().getKenkyuNo());
                  kenkyushaPk.setShozokuCd(shinseiInfo.getDaihyouInfo().getShozokuCd());
                  try{
                	  //存在チェックなので、排他制御が不要です。 2007/6/7
                      //mKenkyuDao.selectKenkyushaInfo(connection,kenkyushaPk,true);
                      mKenkyuDao.selectKenkyushaInfo(connection,kenkyushaPk,false);
                  }catch(NoDataFoundException e){
                      throw new NoDataFoundException(
                            "該当研究者マスタに以下の研究者が存在しません'",
                            new ErrorInfo("errors.4004"), e);
                  }
                  
                  shinseiInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);
                  shinseiInfo.setJyuriDate(new Date());                  
                  shinseiDao.updateShinseiDataInfo(connection, shinseiInfo, true);
              }
          }
          success=true;
        }finally {
            if(success){
                DatabaseUtil.commit(connection);
            }else{
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }

    /**
     * 承認解除(提出書類)を行う。
     * 
     * @param userInfo
     * @param ryoikiPk
     * @param pkInfo
     * @return なし
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void cancelTeisyutusyoSyonin(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws DataAccessException,
                   NoDataFoundException,
                   ApplicationException {

        Connection connection = null;
        boolean success = false;
        // 排他制御のため既存データを取得する
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;

        // 領域計画書（概要）情報管理DAOと申請情報データDAO
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
        ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);

        try {
            // DBコネクションの取得
            connection = DatabaseUtil.getConnection();

            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao.selectRyoikiKeikakushoInfoForLock(
                    connection, ryoikiPk);
            // ---領域計画書（概要）情報管理データ削除フラグチェック---
            String delFlag = ryoikikeikakushoInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("当該領域計画書情報データは削除されています。",
                        new ErrorInfo("errors.9001"));
            }
            // ---DB更新---
            ryoikikeikakushoInfo.setCancelFlg(StatusCode.SAISHINSEI_FLG_SAISHINSEITYU); // Cancelフラグ
            ryoikikeikakushoInfo.setRyoikiJokyoId(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU); // 領域計画書概要応募状況ID
//mengchuanjun 2006/07/27 add start 
            ryoikikeikakushoInfo.setShoninDate(null);
//mengchuanjun 2006/07/27 add end                 
            ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();
            shinseiDataInfo.setRyouikiNo(ryoikikeikakushoInfo.getKariryoikiNo());
            shinseiDataInfo.setJokyoIds(ryoikiInfo.getJokyoIds());
//mengchuanjun 2006/07/27 add start 
            //shinseiDataInfo.setShoninDate(null); // 所属期間承認日 
//mengchuanjun 2006/07/27 add end             
            shinseiDataInfo.setShoninDateMark("1");
            String status = StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE; // 応募状況ID
            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection, ryoikikeikakushoInfo);
            shinseiDataInfoDao.updateShinseis(connection, shinseiDataInfo, status);
            success = true;

        }
        catch (DataAccessException e) {
            throw new ApplicationException("領域計画書（概要）情報更新中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4001"), e);
        }
        finally {
            try {
                if (success) {
                    DatabaseUtil.commit(connection);
                }
                else {
                    DatabaseUtil.rollback(connection);
                }
            }
            catch (TransactionException e) {
                throw new ApplicationException("領域計画書（概要）情報管理データDB登録中にエラーが発生しました。",
                        new ErrorInfo("errors.4001"), e);
            }
            finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
    
    /**
     * 領域計画書削除情報を取得する。
     * 
     * @param userInfo
     * @param ryoikiSystemNo
     * @return RyoikiKeikakushoInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo getRyoikiGaiyoDeleteInfo(
            UserInfo userInfo,
            String ryoikiSystemNo)
            throws NoDataFoundException, 
                   ApplicationException {

        // DBコネクションの取得
        Connection connection = null;
        RyoikiKeikakushoInfo ryoikiKeikakushoInfo = new RyoikiKeikakushoInfo();
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
        
        // 簡易申請情報
        try {
            connection = DatabaseUtil.getConnection();
            ryoikiKeikakushoInfo = ryoikikeikakushoInfoDao.selectInfo(connection, ryoikiSystemNo);
            new StatusGaiyoManager(userInfo).setRyoikiStatusName(ryoikiKeikakushoInfo);
        }catch (NoDataFoundException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
                    "errors.4004"), se);
        } 
        catch (DataAccessException e) {
            throw new ApplicationException("DBエラーが発生しました。", new ErrorInfo(
                    "errors.4001"), e);
        }   
        finally {
            DatabaseUtil.closeConnection(connection);
        }
        return ryoikiKeikakushoInfo;
    }

    /**
     * 領域計画書削除を実行する。
     * 
     * @param  userInfo
     * @param  ryoikiSystemNo
     * @return なし 
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void deleteFlagRyoikiGaiyo(UserInfo userInfo, String ryoikiSystemNo) 
            throws NoDataFoundException, ApplicationException{
        
        // DBコネクションの取得        
        Connection connection = null;
        boolean success = false;

        RyoikiKeikakushoInfo ryoikikeikakushoInfo = new RyoikiKeikakushoInfo();
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk();
        pkInfo.setRyoikiSystemNo(ryoikiSystemNo);     
        
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
       
        try{
            //DBコネクションの取得
            connection = DatabaseUtil.getConnection();

            //形式チェック
            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao
                    .selectRyoikiKeikakushoInfoForLock(connection, pkInfo);
            String ryoikiJokyoId = ryoikikeikakushoInfo.getRyoikiJokyoId();
            if (!(ryoikiJokyoId.equals(StatusCode.STATUS_KARIRYOIKINO_KAKUNINMATI)
                    || ryoikiJokyoId.equals(StatusCode.STATUS_KARIRYOIKINO_HAKKUKYAKKA)
                    || ryoikiJokyoId.equals(StatusCode.STATUS_KARIRYOIKINO_HAKKUZUMI)
                    || ryoikiJokyoId.equals(StatusCode.STATUS_SAKUSEITHU)
                    || ryoikiJokyoId.equals(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN)
                    || ryoikiJokyoId.equals(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA))){

                throw new ApplicationException("形式チェックでエラーがあります、削除できません。",
                        new ErrorInfo("errors.5066"));
            }
            ryoikikeikakushoInfoDao.existCount(connection,ryoikikeikakushoInfo);

            //---DB更新---
            ryoikikeikakushoInfoDao.deleteFlagRyoikiKeikakushoInfo(connection, ryoikiSystemNo);
            success = true;
        }
        catch (NoDataFoundException e) {
            throw new NoDataFoundException("該当する情報が存在しませんでした。",
                    new ErrorInfo("errors.5002"), e);
        } 
        catch (DuplicateKeyException e) {
            throw new ApplicationException("形式チェックでエラーがあります、削除できません。",
                    new ErrorInfo("errors.5066"));
        }
        catch (DataAccessException e) {
            throw new ApplicationException("DBエラーが発生しました。",
                    new ErrorInfo("errors.4001"), e);
        }
        finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }
//mcj add end

//  2006/06/21 dyh add start
    /**    
     * 領域計画書と研究計画調書の一覧を取得する。
     * @param userInfo ユーザ情報
     * @param kariryoikiNo 仮領域番号
     * @return List 領域計画書と研究計画調書の一覧
     * @throws ApplicationException
     */
    public List getRyoikiAndKenkyuList(
            UserInfo userInfo,
            String kariryoikiNo)
            throws ApplicationException {

        // DBコネクションの取得
        Connection connection = null;
        List resultList = new ArrayList(2);
        try {
            // DBコネクションの取得
            connection = DatabaseUtil.getConnection();

            // 領域計画書一覧を取得する
            RyoikiKeikakushoInfoDao ryoikiInfoDao = 
                new RyoikiKeikakushoInfoDao(userInfo);
            String shinseishaId = userInfo.getShinseishaInfo()
                    .getShinseishaId();
            List gaiyoList = ryoikiInfoDao
                    .selectRyoikiKeikakushoInfo(connection, shinseishaId);
            new StatusGaiyoManager(userInfo).setRyoikiStatusName(gaiyoList);
            resultList.add(gaiyoList);

            // 研究計画調書の一覧を取得する
            String ryoikiNo = kariryoikiNo;
            if (StringUtil.isBlank(kariryoikiNo)) {
                ryoikiNo = (String) ((HashMap) gaiyoList.get(0))
                        .get("KARIRYOIKI_NO");
            }
            if (StringUtil.isBlank(ryoikiNo)) {
                return resultList;
            }
            ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(
                    userInfo);
            List keikakusyoList = shinseiDataInfoDao.selectKeikakuTyosyoList(
                    connection, ryoikiNo);
            new StatusManager(userInfo).setRyoikiStatusName(connection,
                    keikakusyoList);
            resultList.add(keikakusyoList);

            return resultList;
        } catch (DataAccessException de) {
            throw new ApplicationException("DBエラーが発生しました。", new ErrorInfo(
                    "errors.4004"), de);
        } catch (NoDataFoundException ne) {
            return resultList;
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    /**
     * 領域計画書表紙PDFファイルを取得。
     * 
     * @param userInfo
     * @param ryoikiSystemNo
     * @return
     * @throws ApplicationException
     */
    public FileResource getGaiyoCoverPdfFile(
            UserInfo userInfo,
            String ryoikiSystemNo)
            throws ApplicationException {

        String pdfPath = "";
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikidao =
                new RyoikiKeikakushoInfoDao(userInfo);

            // PDFファイルパスの取得
            IPdfConvert pdfConvert = new PdfConvert();
            pdfConvert.convertGaiyoHyoshiPdf(connection, userInfo, ryoikiSystemNo);
            pdfPath = ryoikidao.selectHyoshiPdfPath(connection, ryoikiSystemNo);
        } catch (IOException e) {
            throw new ApplicationException("領域計画書表紙PDFの作成でエラーが発生しました",
                    new ErrorInfo("errors.4002"), e);
        } catch (DataAccessException e) {
            throw new ApplicationException("領域計画書表紙PDFの作成でエラーが発生しました",
                    new ErrorInfo("errors.4004"), e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }

        FileResource fileRes = null;
        File file = new File(pdfPath);
        try {
            fileRes = FileUtil.readFile(file);
        }
        catch (IOException e) {
            throw new FileIOException("ファイルの入力中にエラーが発生しました。", e);
        }

        return fileRes;
    }

    /**
     * 領域計画書確認PDFファイルを取得。
     * 
     * @param userInfo ログイン者情報
     * @param ryoikiSystemNo システム受付番号
     * @return FileResource PDFファイル
     * @throws ApplicationException
     */
    public FileResource getRyoikiGaiyoPdfFile(
            UserInfo userInfo,
            RyoikiKeikakushoPk pkInfo)
            throws ApplicationException{

        //===== PDF変換サービスメソッド呼び出し =====
        IPdfConvert pdfConvert = new PdfConvert();
        return pdfConvert.getGaiyouResource(userInfo, pkInfo);  
    }

    /**
     * 領域計画書（概要）情報管理テーブルに、データの存在チェック
     * ログイン者の申請者IDと一致し、DEL_FLG（削除フラグ）が[0]
     * @param userInfo
     * @return boolean
     * @throws ApplicationException
     */
    public boolean isExistRyoikiGaiyoInfo(UserInfo userInfo)
            throws ApplicationException {

        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikiInfoDao = new RyoikiKeikakushoInfoDao(
                    UserInfo.SYSTEM_USER);
            int infoCount = ryoikiInfoDao.selectCountByShinseishaId(
                    connection, userInfo.getShinseishaInfo());
            if (infoCount > 0) {
                return true;
            } else {
                return false;
            }
        }catch(DataAccessException e){
            throw new ApplicationException(
                    "領域計画書（概要）情報管理テーブル検索中にDBエラーが発生しました。",
                    new ErrorInfo("errors.4004"),
                    e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
//  2006/06/21 dyh end

//ADD　START 2007/07/02 BIS 張楠 -->   
	
    /**    
     * 領域計画書情報の一覧を取得する。
     * 
     * @param searchInfo 領域計画書情報
     * @return List 領域計画書の情報
     * @throws ApplicationException
     */	
	public List getRyoikiResult(RyoikiKeikakushoSystemInfo searchInfo) throws ApplicationException {
		
        // システム受付番号が指定されていない場合はnullを返す
        if (searchInfo == null) {
            throw new NoDataFoundException(
                    "領域計画書（概要）情報管理テーブルに該当するデータが見つかりません。"
                    + "検索キー：領域計画書（概要）情報管理");
        }
        
        StringBuffer query = new StringBuffer();
        
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");
        query.append(" A.JIGYO_NAME,");
        query.append(" A.SHOZOKU_CD,");
        query.append(" A.SHOZOKU_NAME,");
        query.append(" A.KARIRYOIKI_NO,");
        query.append(" A.UKETUKE_NO,");
        query.append(" A.RYOIKI_NAME,");
        query.append(" A.BUKYOKU_NAME,");
        query.append(" A.SHOKUSHU_NAME_KANJI,");
        query.append(" A.NAME_KANJI_SEI,");
        query.append(" A.NAME_KANJI_MEI,");
        query.append(" A.EDITION,");
        query.append(" A.PDF_PATH,");
        query.append(" A.JYURI_DATE,");
        query.append(" B.SYSTEM_HYOJI");
        query.append(" FROM RYOIKIKEIKAKUSHOINFO").append(" A");
        query.append(" INNER JOIN MASTER_RYOIKI_STATUS").append(" B");
        query.append(" ON A.RYOIKI_JOKYO_ID = B.JOKYO_ID ");
        query.append(" INNER JOIN MASTER_JIGYO").append(" C");
        query.append(" ON C.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) ");
        query.append(" WHERE C.JIGYO_CD = '00022' ");
        query.append(" AND B.KAIJYO_FLG = '0' ");
        query.append(" AND A.DEL_FLG = 0 ");
        
        if (!"".equals(searchInfo.getJigyoName()) && searchInfo.getJigyoName() != null ){
            query.append(" AND A.JIGYO_NAME = '");
            query.append(searchInfo.getJigyoName());
            query.append("'");	
        }
        if (!"".equals(searchInfo.getKariryoikiNo()) && searchInfo.getKariryoikiNo() != null ){
            query.append(" AND A.KARIRYOIKI_NO = '");
            query.append(searchInfo.getKariryoikiNo());
            query.append("'");
        }
        if (!"".equals(searchInfo.getNameKanjiSei()) && searchInfo.getNameKanjiSei() != null ){
            query.append(" AND A.NAME_KANJI_SEI like '%");
            query.append(searchInfo.getNameKanjiSei());
            query.append("%'");
        }
        if (!"".equals(searchInfo.getNameKanjiMei()) && searchInfo.getNameKanjiMei() != null ){
            query.append(" AND A.NAME_KANJI_MEI like '%");
            query.append(searchInfo.getNameKanjiMei());
            query.append("%'");
        }
        if (!"".equals(searchInfo.getShozokuName()) && searchInfo.getShozokuName() != null ){
            query.append(" AND A.SHOZOKU_NAME like '%");
            query.append(searchInfo.getShozokuName());
            query.append("%'");
        }
        if (!"".equals(searchInfo.getShozokuCd()) && searchInfo.getShozokuCd() != null ){
            query.append(" AND A.SHOZOKU_CD = '");
            query.append(searchInfo.getShozokuCd());
            query.append("'");
        }
        if (!"".equals(searchInfo.getRyoikiJokyoId()) && searchInfo.getRyoikiJokyoId() != null ){
            query.append(" AND A.RYOIKI_JOKYO_ID = '");
            query.append(searchInfo.getRyoikiJokyoId());
            query.append("'");
        }
        query.append(" ORDER BY A.SHOZOKU_CD,A.KARIRYOIKI_NO, A.UKETUKE_NO ");
        
        
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        //DBコネクションの取得
        Connection connection = null;
        try {
        	connection = DatabaseUtil.getConnection();
        	return SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException(
                "データ検索中にDBエラーが発生しました。",
                new ErrorInfo("errors.4004"),
                e);
        }finally {
        	DatabaseUtil.closeConnection(connection);
        }
	}
	

// ADD　END　 2007/07/02 BIS 張楠 -->    
    
}