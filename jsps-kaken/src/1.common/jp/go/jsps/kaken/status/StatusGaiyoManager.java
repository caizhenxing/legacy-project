/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : StatusGaiyoManager.java
 *    Description : ステータス（概要）管理
 *
 *    Author      : DIS.dyh
 *    Date        : 2006/07/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/07/14    v1.0        DIS.dyh        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.status;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * ステータス（概要）管理
 */
public class StatusGaiyoManager {

    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------
    
    /** 
     * 申請状況マスタMap<!--。-->
     * 各実行ユーザごとの申請状況名称Mapが格納されている。
     * 事業公開前後のMapがListで格納されている。
     * List(0)が公開前、List(1)が公開後のMap。公開前と公開後が同じ場合もある。
     * キー値が'null'の場合は申請状況名称（総称）のMapが返る。
     * 申請状況名称Map（総称）が欲しい場合は、文字列"DEFAULT"をキー値として指定する。
     * キー項目：各ユーザのUserRole
     */
    private static Map MASTER_RYOIKI_STATUS_MAP           = new HashMap();                                
    
    /**
     * 申請状況名称Map（総称）
     * キー値：ステータスコード(Integer)
     */
    private static Map RYOIKI_STATUS_MAP                  = new HashMap();
    
    /**
     * 申請状況名称Map（領域代表者向け表示名）
     * キー値：ステータスコード(Integer)
     */
    private static Map RYOUIKIDAIHYOU_RYOIKI_STATUS_MAP    = new HashMap();

    /**
     * 申請状況名称Map（所属機関向け表示名称）
     * キー値：ステータスコード(Integer)
     */
    private static Map SHOZOKU_RYOIKI_STATUS_MAP           = new HashMap();

    /**
     * 申請状況名称Map（業務担当者向け表示名称）
     * キー値：ステータスコード(Integer)
     */
    private static Map GENKA_RYOIKI_STATUS_MAP             = new HashMap();
    
    /**
     * 申請状況名称Map（システム管理者向け表示名称）
     * キー値：ステータスコード(Integer)
     */
    private static Map SYSTEM_RYOIKI_STATUS_MAP            = new HashMap();   
    
    //---------------------------------------------------------------------
    // Static initialize
    //---------------------------------------------------------------------
    static{
        initAllMap();
        initMasterStatusMap();
    }

    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------
    /** 実行するユーザ情報 */
    private UserInfo userInfo = null;

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------
    /**
     * コンストラクタ
     * @param userInfo
     */
    public StatusGaiyoManager(UserInfo userInfo){
        this.userInfo = userInfo;
    }

    /**
     * ステータス更新用メソッド。
     */
    public void refreshStatus() {
        initAllMap();
        initMasterStatusMap();
    }

    //---------------------------------------------------------------------
    // Private Methods
    //---------------------------------------------------------------------
    /**
     * 各Mapに対して、申請状況IDをキーに領域計画書概要状況マスタから申請状況名をセットしていく。
     * @throws ExceptionInInitializerError
     */
    private static void initAllMap()
        throws ExceptionInInitializerError {

        // SQL文の作成
        String select = "SELECT * FROM MASTER_RYOIKI_STATUS ORDER BY JOKYO_ID";    
            
        // DBレコード取得
        Connection connection = null;
        List resultList = null;
        try {
            connection= DatabaseUtil.getConnection();
            resultList = SelectUtil.select(connection, select);
        }catch(Exception e){
            throw new ExceptionInInitializerError("領域計画書概要状況マスタ検索中にDBエラーが発生しました。");
        }finally{
            DatabaseUtil.closeConnection(connection);
        }
        
        // リストよりDBレコードを取得してMapを生成する
        for (int i = 0; i < resultList.size(); i++) {
            Map recordMap = (Map)resultList.get(i);

            //申請状況と再申請フラグを結合したものをキー項目にする
            String jokyoId = (String)recordMap.get("JOKYO_ID");
            String kaijyoFlg = (String)recordMap.get("KAIJYO_FLG");
            String pKey = jokyoId + kaijyoFlg;

            // 各担当のステータス文字列を格納していく
            RYOIKI_STATUS_MAP.put(pKey, recordMap.get("JOKYO_NAME"));
            RYOUIKIDAIHYOU_RYOIKI_STATUS_MAP.put(pKey, recordMap.get("RYOIKIDAIHYOU_HYOJI"));
            SHOZOKU_RYOIKI_STATUS_MAP.put(pKey, recordMap.get("SHOZOKU_HYOJI"));
            GENKA_RYOIKI_STATUS_MAP.put(pKey, recordMap.get("GENKA_HYOJI"));
            SYSTEM_RYOIKI_STATUS_MAP.put(pKey, recordMap.get("SYSTEM_HYOJI"));// システム管理者向け表示名称
        }
    }

    /**
     * 申請状況マスタMapの初期化。
     */
    private static void initMasterStatusMap(){
        
        //公開前後のList
        List list = null;

        //総称
        list = new ArrayList(1);
        list.add(RYOIKI_STATUS_MAP);
        MASTER_RYOIKI_STATUS_MAP.put(null, list); //総称はnullに紐付ける
                
        //申請者
        list = new ArrayList(1);
        list.add(RYOUIKIDAIHYOU_RYOIKI_STATUS_MAP);
        MASTER_RYOIKI_STATUS_MAP.put(UserRole.SHINSEISHA, list);
        
        //所属機関担当者
        list = new ArrayList(1);
        list.add(SHOZOKU_RYOIKI_STATUS_MAP);       
        MASTER_RYOIKI_STATUS_MAP.put(UserRole.SHOZOKUTANTO, list);     
        
        //業務担当者（原課）
        list = new ArrayList(1);
        list.add(GENKA_RYOIKI_STATUS_MAP);     
        MASTER_RYOIKI_STATUS_MAP.put(UserRole.GYOMUTANTO, list);

        //システム管理者
        list = new ArrayList(1);
        list.add(SYSTEM_RYOIKI_STATUS_MAP);        
        MASTER_RYOIKI_STATUS_MAP.put(UserRole.SYSTEM, list);

        //部局担当者(所属と同様)
        list = new ArrayList(1);
        list.add(SHOZOKU_RYOIKI_STATUS_MAP);       
        MASTER_RYOIKI_STATUS_MAP.put(UserRole.BUKYOKUTANTO, list);
    }

    /**
     * オブジェクトの文字列表現を返す。
     * 引数がnullの場合はnullを返す。
     * @param obj
     * @return
     */
    private String object2String(Object obj){
        
        //戻り値
        String ret = null;
        
        if(obj instanceof String ){
            ret = (String)obj;      //String型の場合はキャスト
        }else if(obj != null){
            ret = obj.toString();   //Strin型g以外の場合はtoString()
        }else{
            ret = null;             //objがnullの場合はnull
        }
        
        return ret;
    }

    //---------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------
    /**
     * 当該申請データの申請状況名（申請状況を表す文字列）を、ryoikiInfoにセットする。
     * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
     * @param ryoikiInfo
     */
    public void setRyoikiStatusName(RyoikiKeikakushoInfo ryoikiInfo) {
        String ryoikiJokyoName = getRyoikiJokyoName(
                ryoikiInfo.getRyoikiJokyoId(),
                ryoikiInfo.getCancelFlg());
        ryoikiInfo.setRyoikiJokyoName(ryoikiJokyoName);
    }
    
    
    /**
     * 当該申請データの申請状況名（申請状況を表す文字列）を、ryoikiInfosにセットする。
     * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
     * @param ryoikiInfos
     */
    public void setRyoikiStatusName(RyoikiKeikakushoInfo[] ryoikiInfos){
        for(int i=0; i<ryoikiInfos.length; i++){
            setRyoikiStatusName(ryoikiInfos[i]);
        }
    }

    /**
     * 当該申請データの申請状況名（申請状況を表す文字列）を、pageオブジェクトに
     * 格納されているListにセットする。申請状況名は、キー値「JOKYO_NAME」としてMapに追加される。
     * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
     * pageに格納されているListには、
     * <li>申請状況ID["RYOIKI_JOKYO_ID"]</li>
     * <li>解除フラグ["CANCEL_FLG"]</li>
     * がMap形式で格納されていること。
     * @param page
     */
    public void setRyoikiStatusName(Page page){
        //Page内のリストを取得し、各レコードマップに対して申請状況名をセットする。
        List infoList = page.getList();
        for(int i=0; i<infoList.size(); i++){
            Map info = (Map)infoList.get(i);
            String jokyoName = getRyoikiJokyoName(
                    object2String(info.get("RYOIKI_JOKYO_ID")),
                    object2String(info.get("CANCEL_FLG")));
            info.put("RYOIKI_JOKYO_NAME", jokyoName);
        }
    }

    /**
     * 当該申請データの申請状況名（申請状況を表す文字列）を、pageオブジェクトに
     * 格納されているListにセットする。申請状況名は、キー値「JOKYO_NAME」としてMapに追加される。
     * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
     * pageに格納されているListには、
     * <li>申請状況ID["RYOIKI_JOKYO_ID"]</li>
     * <li>解除フラグ["CANCEL_FLG"]</li>
     * がMap形式で格納されていること。
     * @param infoList
     */
    public void setRyoikiStatusName(List infoList){
        // リスト内の各レコードマップに対して申請状況名をセットする。
        for(int i=0; i<infoList.size(); i++){
            Map info = (Map)infoList.get(i);
            String jokyoName = getRyoikiJokyoName(
                    object2String(info.get("RYOIKI_JOKYO_ID")),
                    object2String(info.get("CANCEL_FLG")));
            info.put("RYOIKI_JOKYO_NAME", jokyoName);
        }
    }

    /**
     * 実行するユーザに該当する申請状況名（申請状況を表す文字列）を返す。
     * 文字列に{KEKKA1}が含まれていた場合は、1次審査結果(ABC)と1次審査結果(点数)を
     * 文字列結合したものと置換、{KEKKA2}が含まれていた場合は2次審査結果(文字列)と
     * 置換した文字列を返す。
     * @param jokyoId
     * @param kaijyoFlg 解除フラグ
     * @return String
     */
    private String getRyoikiJokyoName(String jokyoId,String kaijyoFlg) {

        // 実行ユーザに紐づいた申請状況Mapを取得する
        List list = (List)MASTER_RYOIKI_STATUS_MAP.get(userInfo.getRole());
        Map  map  = (Map)list.get(0);
        
        // 申請状況ID＋再申請フラグを元に申請状況名称を取得
        String jokyoName = (String)map.get(jokyoId + kaijyoFlg);
    
        return jokyoName;
    }
}