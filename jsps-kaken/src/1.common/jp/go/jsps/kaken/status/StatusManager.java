/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : StatusGaiyoManager.java
 *    Description : ステータス（概要）管理
 *
 *    Author      : takano
 *    Date        : 2004/01/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/01/20    v1.0        takano         新規作成
 *    2006/07/17    v1.1        DIS.dyh        変更
 *====================================================================== 
 */
package jp.go.jsps.kaken.status;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * ステータス管理
 */
public class StatusManager {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 置換文字列：1次審査結果 */
	private static final String SHINSA_KEKKA_1            = "\\{KEKKA1\\}";
	
	/** 置換文字列：2次審査結果 */
	private static final String SHINSA_KEKKA_2            = "\\{KEKKA2\\}";

    /** 申請者と領域代表者の区分(2:領域代表者) */
	private int RYOIKI = 2;

    /** 申請者と領域代表者の区分(0:申請者) */
	private int NOT_RYOIKI = 0;

	/** 
	 * 申請状況マスタMap<!--。-->
	 * 各実行ユーザごとの申請状況名称Mapが格納されている。
	 * 事業公開前後のMapがListで格納されている。
	 * List(0)が公開前、List(1)が公開後のMap。公開前と公開後が同じ場合もある。
	 * キー値が'null'の場合は申請状況名称（総称）のMapが返る。
	 * 申請状況名称Map（総称）が欲しい場合は、文字列"DEFAULT"をキー値として指定する。
	 * キー項目：各ユーザのUserRole
	 */
	private static Map  MASTER_STATUS_MAP                  = new HashMap();                                
	
	/**
	 * 申請状況名称Map（総称）
	 * キー値：ステータスコード(Integer)
	 */
	private static Map  STATUS_MAP                         = new HashMap();
	
	/**
	 * 申請状況名称Map（申請者向け表示名称）
	 * キー値：ステータスコード(Integer)
	 */
	private static Map  SHINSEISHA_STATUS_MAP              = new HashMap();
	
	/**
	 * 申請状況名称Map（申請者向け公開前表示名称）
	 * キー値：ステータスコード(Integer)
	 */
	private static Map  SHINSNEISHA_KOKAIMAE_STATUS_MAP    = new HashMap();
	
	/**
	 * 申請状況名称Map（領域代表者向け表示名）
	 * キー値：ステータスコード(Integer)
	 */
	private static Map  RYOUIKIDAIHYOU_STATUS_MAP    = new HashMap();	
	/**
	 * 申請状況名称Map（所属機関向け表示名称）
	 * キー値：ステータスコード(Integer)
	 */
	private static Map  SHOZOKU_STATUS_MAP                 = new HashMap();
	
	/**
	 * 申請状況名称Map（所属機関向け公開前表示名称）
	 * キー値：ステータスコード(Integer)
	 */
	private static Map  SHOZOKU_KOKAIMAE_STATUS_MAP        = new HashMap();

	/**
	 * 申請状況名称Map（原課向け表示名称）
	 * キー値：ステータスコード(Integer)
	 */
	private static Map  GENKA_STATUS_MAP                   = new HashMap();
	
	/**
	 * 申請状況名称Map（審査員向け表示名称）
	 * キー値：ステータスコード(Integer)
	 */
	private static Map  SHINSAIN_STATUS_MAP                = new HashMap();
	
	/**
	 * 申請状況名称Map（システム管理者向け表示名称）
	 * キー値：ステータスコード(Integer)
	 */
	private static Map  SYSTEM_STATUS_MAP                = new HashMap();	
	
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

	/** DBLnk */
	private String dbLink = "";
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ
	 * @param userInfo
	 */
	public StatusManager(UserInfo userInfo){
		this.userInfo = userInfo;
	}

	/**
	 * コンストラクタ
	 * @param userInfo
	 */
	public StatusManager(UserInfo userInfo, String dbLink){
		this.userInfo = userInfo;
		this.dbLink = dbLink;
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
	 * 各Mapに対して、申請状況IDをキーに申請状況名をセットしていく。
	 * @throws ExceptionInInitializerError
	 */
	private static void initAllMap()
		throws ExceptionInInitializerError {

		// SQL文の作成
		String select = "SELECT * FROM MASTER_STATUS ORDER BY JOKYO_ID";	
			
		// DBレコード取得
		Connection connection = null;
		List resultList = null;
		try {
			connection= DatabaseUtil.getConnection();
			resultList = SelectUtil.select(connection, select);
		}catch(Exception e){
			throw new ExceptionInInitializerError("申請状況マスタ検索中にDBエラーが発生しました。");
		}finally{
			DatabaseUtil.closeConnection(connection);
		}
		
		// リストよりDBレコードを取得してMapを生成する
		for(int i=0; i<resultList.size(); i++){
			Map recordMap = (Map)resultList.get(i);

			//申請状況と再申請フラグを結合したものをキー項目にする
			String jokyoId = (String)recordMap.get("JOKYO_ID");
			String saishinseiFlg = (String)recordMap.get("SAISHINSEI_FLG");
			String pKey = jokyoId + saishinseiFlg;

			//各担当のステータス文字列を格納していく
			STATUS_MAP.put(pKey, recordMap.get("JOKYO_NAME"));
			SHINSEISHA_STATUS_MAP.put(pKey, recordMap.get("SHINSEISHA_HYOJI"));
			SHINSNEISHA_KOKAIMAE_STATUS_MAP.put(pKey, recordMap.get("SHINSEISHA_HYOJI_KOKAIMAE"));
			RYOUIKIDAIHYOU_STATUS_MAP.put(pKey, recordMap.get("RYOIKIDAIHYOSHA_HYOJI"));
			SHOZOKU_STATUS_MAP.put(pKey, recordMap.get("SHOZOKU_HYOJI"));
			SHOZOKU_KOKAIMAE_STATUS_MAP.put(pKey, recordMap.get("SHOZOKU_HYOJI_KOKAIMAE"));
			GENKA_STATUS_MAP.put(pKey, recordMap.get("GENKA_HYOJI"));
			SHINSAIN_STATUS_MAP.put(pKey, recordMap.get("SHINSAIN_HYOJI"));
			SYSTEM_STATUS_MAP.put(pKey, recordMap.get("SYSTEM_HYOJI"));
		}
	}

	/**
	 * 申請状況マスタMapの初期化。
	 */
	private static void initMasterStatusMap(){
		
		//公開前後のList
		List list = null;

		//総称
		list = new ArrayList(3);
		list.add(STATUS_MAP);		//公開前後で同じものが入る
		list.add(STATUS_MAP);
		list.add(STATUS_MAP);
		MASTER_STATUS_MAP.put(null, list); //総称はnullに紐付ける
				
		//申請者
		list = new ArrayList(3);
		list.add(SHINSNEISHA_KOKAIMAE_STATUS_MAP);
		list.add(SHINSEISHA_STATUS_MAP);
		list.add(RYOUIKIDAIHYOU_STATUS_MAP);
		MASTER_STATUS_MAP.put(UserRole.SHINSEISHA, list);
		
		//所属機関担当者
		list = new ArrayList(3);
		list.add(SHOZOKU_KOKAIMAE_STATUS_MAP);
		list.add(SHOZOKU_STATUS_MAP);
		list.add(SHOZOKU_STATUS_MAP);		
		MASTER_STATUS_MAP.put(UserRole.SHOZOKUTANTO, list);		
		
		//原課（業務担当者）
		list = new ArrayList(3);
		list.add(GENKA_STATUS_MAP);		//公開前後で同じものが入る
		list.add(GENKA_STATUS_MAP);
		list.add(GENKA_STATUS_MAP);		
		MASTER_STATUS_MAP.put(UserRole.GYOMUTANTO, list);
				
		//審査員
		list = new ArrayList(3);
		list.add(SHINSAIN_STATUS_MAP);	//公開前後で同じものが入る
		list.add(SHINSAIN_STATUS_MAP);
		list.add(SHINSAIN_STATUS_MAP);		
		MASTER_STATUS_MAP.put(UserRole.SHINSAIN, list);

		//システム管理者
		list = new ArrayList(3);
		list.add(SYSTEM_STATUS_MAP);	//公開前後で同じものが入る
		list.add(SYSTEM_STATUS_MAP);
		list.add(SYSTEM_STATUS_MAP);		
		MASTER_STATUS_MAP.put(UserRole.SYSTEM, list);
		
		//2005/4/11 追加 ここから---------------------------------------
		//理由 部局担当者の場合の指定がされていないため
		//部局担当者(所属と同様)
		list = new ArrayList(3);
		list.add(SHOZOKU_KOKAIMAE_STATUS_MAP);
		list.add(SHOZOKU_STATUS_MAP);
		list.add(SHOZOKU_STATUS_MAP);		
		MASTER_STATUS_MAP.put(UserRole.BUKYOKUTANTO, list);
		//追加 ここまで-------------------------------------------------
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
			ret = null;				//objがnullの場合はnull
		}
		
		return ret;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * 当該申請データの申請状況名（申請状況を表す文字列）を、simpleInfoにセットする。
	 * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
	 * @param connection
	 * @param simpleInfo
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void setStatusName(Connection connection, SimpleShinseiDataInfo simpleInfo)
		    throws NoDataFoundException, ApplicationException {

//2006/07/17 dyh update start
//		String jokyoName = getJokyoName(connection,
//										simpleInfo.getJigyoId(),
//										simpleInfo.getJokyoId(),
//										simpleInfo.getSaishinseiFlg(),
//										simpleInfo.getKekka1Abc(),
//										simpleInfo.getKekka1Ten(),
//										simpleInfo.getKekka2());
        String jokyoName = getJokyoName(connection,
                simpleInfo.getJigyoId(),
                simpleInfo.getJokyoId(),
                simpleInfo.getSaishinseiFlg(),
                simpleInfo.getKekka1Abc(),
                simpleInfo.getKekka1Ten(),
                simpleInfo.getKekka2(),
                NOT_RYOIKI);
// 2006/07/17 dyh update end

		simpleInfo.setJokyoName(jokyoName);
	}
	
	
	/**
	 * 当該申請データの申請状況名（申請状況を表す文字列）を、simpleInfoにセットする。
	 * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
	 * @param connection
	 * @param simpleInfos
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void setStatusNames(Connection connection, SimpleShinseiDataInfo[] simpleInfos)
		throws NoDataFoundException, ApplicationException {
		for(int i=0; i<simpleInfos.length; i++){
			setStatusName(connection, simpleInfos[i]);
		}
	}

	/**
	 * 当該申請データの申請状況名（申請状況を表す文字列）を、pageオブジェクトに
	 * 格納されているListにセットする。申請状況名は、キー値「JOKYO_NAME」としてMapに追加される。
	 * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
	 * pageに格納されているListには、
	 * <li>事業ID["JIGYO_ID"]</li>
	 * <li>申請状況ID["JOKYO_ID"]</li>
	 * <li>再申請フラグ["SAISHINSEI_FLG"]</li>
	 * <li>1次審査結果(ABC)["KEKKA1_ABC"]</li>
	 * <li>1次審査結果(点数)["KEKKA1_TEN"]</li>
	 * <li>2次審査結果["KEKKA2"]</li>
	 * がMap形式で格納されていること。
	 * @param connection
 	 * @param page
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void setStatusName(Connection connection, Page page)
		throws NoDataFoundException, ApplicationException {

		//Page内のリストを取得し、各レコードマップに対して申請状況名をセットする。
        List infoList = page.getList();
// 2006/07/17 dyh update start
//		for(int i=0; i<infoList.size(); i++){
//			Map info = (Map)infoList.get(i);
//			String jokyoName = getJokyoName(connection,
//											object2String(info.get("JIGYO_ID")),
//											object2String(info.get("JOKYO_ID")),
//											object2String(info.get("SAISHINSEI_FLG")),
//											object2String(info.get("KEKKA1_ABC")),
//											object2String(info.get("KEKKA1_TEN")),
//											object2String(info.get("KEKKA2")));
//			info.put("JOKYO_NAME", jokyoName);
//		}
        setStatusName(connection, infoList);
// 2006/07/17 dyh update end
	}

//2006/7/17 dyh add start
    /**
     * 当該申請データの申請状況名（領域代表者向け表示文字列）を、pageオブジェクトに
     * 格納されているListにセットする。申請状況名は、キー値「RYOIKI_JOKYO_NAME」としてMapに追加される。
     * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
     * pageに格納されているListには、
     * <li>事業ID["JIGYO_ID"]</li>
     * <li>申請状況ID["JOKYO_ID"]</li>
     * <li>再申請フラグ["SAISHINSEI_FLG"]</li>
     * <li>1次審査結果(ABC)["KEKKA1_ABC"]</li>
     * <li>1次審査結果(点数)["KEKKA1_TEN"]</li>
     * <li>2次審査結果["KEKKA2"]</li>
     * がMap形式で格納されていること。
     * @param connection
     * @param page
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void setRyoikiStatusName(Connection connection, Page page)
            throws NoDataFoundException, ApplicationException {

        // Page内のリストを取得し、各レコードマップに対して申請状況名をセットする。
        List infoList = page.getList();
        setRyoikiStatusName(connection, infoList);
    }

    /**
     * 当該申請データの申請状況名（申請状況を表す文字列）を、Listオブジェクトに
     * 格納されているListにセットする。申請状況名は、キー値「JOKYO_NAME」「RYOIKI_JOKYO_NAME」としてMapに追加される。
     * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
     * Listには、
     * <li>事業ID["JIGYO_ID"]</li>
     * <li>申請状況ID["JOKYO_ID"]</li>
     * <li>再申請フラグ["SAISHINSEI_FLG"]</li>
     * <li>1次審査結果(ABC)["KEKKA1_ABC"]</li>
     * <li>1次審査結果(点数)["KEKKA1_TEN"]</li>
     * <li>2次審査結果["KEKKA2"]</li>
     * がMap形式で格納されていること。
     * @param connection
     * @param infoList
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void setStatusNames(Connection connection, List infoList)
            throws NoDataFoundException, ApplicationException {

        // リスト内の各レコードマップに対して申請状況名をセットする。
        for (int i = 0; i < infoList.size(); i++) {
            Map info = (Map)infoList.get(i);

            // 申請状況名(申請者向け表示)を設定
            String jokyoName = getJokyoName(
                    connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    object2String(info.get("SAISHINSEI_FLG")),
                    object2String(info.get("KEKKA1_ABC")),
                    object2String(info.get("KEKKA1_TEN")),
                    object2String(info.get("KEKKA2")),
                    NOT_RYOIKI);
            info.put("JOKYO_NAME", jokyoName);
                
            // 申請状況名(領域代表者向け表示)を設定
            String ryoikiJokyoName = getJokyoName(
                    connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    object2String(info.get("SAISHINSEI_FLG")),
                    object2String(info.get("KEKKA1_ABC")),
                    object2String(info.get("KEKKA1_TEN")),
                    object2String(info.get("KEKKA2")),
                    RYOIKI);
            info.put("RYOIKI_JOKYO_NAME", ryoikiJokyoName);
        }
    }

    /**
     * 当該申請データの申請状況名（申請状況を表す文字列）を、Listオブジェクトに
     * 格納されているListにセットする。申請状況名は、キー値「JOKYO_NAME」としてMapに追加される。
     * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
     * Listには、
     * <li>事業ID["JIGYO_ID"]</li>
     * <li>申請状況ID["JOKYO_ID"]</li>
     * <li>再申請フラグ["SAISHINSEI_FLG"]</li>
     * <li>1次審査結果(ABC)["KEKKA1_ABC"]</li>
     * <li>1次審査結果(点数)["KEKKA1_TEN"]</li>
     * <li>2次審査結果["KEKKA2"]</li>
     * がMap形式で格納されていること。
     * @param connection
     * @param infoList
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void setStatusName(Connection connection, List infoList)
            throws NoDataFoundException, ApplicationException {

        // リスト内の各レコードマップに対して申請状況名をセットする。
        for (int i = 0; i < infoList.size(); i++) {
            Map info = (Map)infoList.get(i);
            
            String ryoikiJokyoName = getJokyoName(connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    object2String(info.get("SAISHINSEI_FLG")),
                    object2String(info.get("KEKKA1_ABC")),
                    object2String(info.get("KEKKA1_TEN")),
                    object2String(info.get("KEKKA2")),
                    NOT_RYOIKI);
            info.put("JOKYO_NAME", ryoikiJokyoName);
        }
    }

    /**
     * 当該申請データの申請状況名（領域代表者向け表示文字列）を、Listオブジェクトに
     * 格納されているListにセットする。申請状況名は、キー値「RYOIKI_JOKYO_NAME」としてMapに追加される。
     * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
     * Listには、
     * <li>事業ID["JIGYO_ID"]</li>
     * <li>申請状況ID["JOKYO_ID"]</li>
     * <li>再申請フラグ["SAISHINSEI_FLG"]</li>
     * <li>1次審査結果(ABC)["KEKKA1_ABC"]</li>
     * <li>1次審査結果(点数)["KEKKA1_TEN"]</li>
     * <li>2次審査結果["KEKKA2"]</li>
     * がMap形式で格納されていること。
     * @param connection
     * @param infoList
     * @param ryoikiFlg 申請者と領域代表者の区分
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void setRyoikiStatusName(Connection connection, List infoList)
            throws NoDataFoundException, ApplicationException {

        // リスト内の各レコードマップに対して申請状況名をセットする。
        for (int i = 0; i < infoList.size(); i++) {
            Map info = (Map)infoList.get(i);
            
            String ryoikiJokyoName = getJokyoName(connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    object2String(info.get("SAISHINSEI_FLG")),
                    object2String(info.get("KEKKA1_ABC")),
                    object2String(info.get("KEKKA1_TEN")),
                    object2String(info.get("KEKKA2")),
                    RYOIKI);
            info.put("RYOIKI_JOKYO_NAME", ryoikiJokyoName);
        }
    }
// 2006/7/17 dyh add end

	/**
	 * 当該申請データの申請状況名（申請状況を表す文字列）を、pageオブジェクトに
	 * 格納されているListにセットする。申請状況名は、キー値「JOKYO_NAME」としてMapに追加される。
	 * 申請状況名は、実行するユーザ、事業の公開前後、申請状況IDに該当する文字列がセットされる。
	 * pageに格納されているListには、
	 * <li>申請状況ID["JOKYO_ID"]</li>
	 * がMap形式で格納されていること。
	 * @param connection
	 * @param page
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void setJokyoName(Connection connection, Page page)
			throws NoDataFoundException, ApplicationException {
		
		//Page内のリストを取得し、各レコードマップに対して申請状況名をセットする。
		List infoList = page.getList();
		for(int i=0; i<infoList.size(); i++){
			Map info = (Map)infoList.get(i);
// 2006/07/17 dyh update start
//			String jokyoName = getJokyoName(connection,
//											object2String(info.get("JIGYO_ID")),
//											object2String(info.get("JOKYO_ID")),
//											StatusCode.SAISHINSEI_FLG_DEFAULT,
//											null,
//											null,
//											null
//											);
            String jokyoName = getJokyoName(
                    connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    StatusCode.SAISHINSEI_FLG_DEFAULT,
                    null,
                    null,
                    null,
                    NOT_RYOIKI);
// 2006/07/17 dyh update end
			String ryoikijokyoName = getJokyoName(
                    connection,
                    object2String(info.get("JIGYO_ID")),
                    object2String(info.get("JOKYO_ID")),
                    StatusCode.SAISHINSEI_FLG_DEFAULT,
                    null,
                    null,
                    null,
                    RYOIKI);
			info.put("JOKYO_NAME", jokyoName);
			info.put("RYOIKI_JOKYO_NAME", ryoikijokyoName);
		}
	}

// 2006/07/17 dyh delete start 修正後使用しない
//	/**
//	 * 実行するユーザに該当する申請状況名（申請状況を表す文字列）を返す。
//	 * 文字列に{KEKKA1}が含まれていた場合は、1次審査結果(ABC)と1次審査結果(点数)を
//	 * 文字列結合したものと置換、{KEKKA2}が含まれていた場合は2次審査結果(文字列)と
//	 * 置換した文字列を返す。
//	 * @param connection
//	 * @param jigyoId
//	 * @param jokyoId
//	 * @param saishinseiFlag
//	 * @param kekka1Abc
//	 * @param kekka1Ten
//	 * @param kekka2
//	 * @return String
//	 * @throws NoDataFoundException
//	 * @throws ApplicationException
//	 */
//	protected String getJokyoName(Connection connection,
//								  String jigyoId, 
//								  String jokyoId,
//								  String saishinseiFlag,
//								  String kekka1Abc,
//								  String kekka1Ten,
//								  String kekka2)
//		throws NoDataFoundException, ApplicationException {
//		//-----事業IDを元に公開フラグを取得する-----
//		// SQL文の作成
//		String select = "SELECT KOKAI_FLG FROM JIGYOKANRI WHERE JIGYO_ID = '" + jigyoId + "'";	
//			
//		//DBレコード取得
//		List resultList = null;
//		try {
//			resultList = SelectUtil.select(connection, select);
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new ExceptionInInitializerError("事業管理データ検索中にDBエラーが発生しました。");
//		}
//		
//		//公開フラグを取得する
//		Map resultMap = (Map)resultList.get(0);
//		Number kokaiFlgObj = (Number)resultMap.get("KOKAI_FLG");
//		int kokaiFlg = 0;
//		if(kokaiFlgObj != null){
//			kokaiFlg = kokaiFlgObj.intValue();
//		}
//		
//		//実行ユーザに紐づいた申請状況Mapを取得する
//		List list = (List)MASTER_STATUS_MAP.get(userInfo.getRole());
//		Map  map  = (Map)list.get(kokaiFlg);
//		
//		//申請状況ID＋再申請フラグを元に申請状況名称を取得
//		String jokyoName = (String)map.get(jokyoId + saishinseiFlag);
//		
//		//1次審査結果文字列の取得
//		StringBuffer strKekka1 = new StringBuffer();
//		if(kekka1Abc != null){
//			strKekka1.append(kekka1Abc);
//		}else{
//			//1次審査結果ABCがセットされていない場合は、1次審査結果点を表示する
//			if(kekka1Ten != null){
//				strKekka1.append(kekka1Ten);
//			}
//		}
//		//2次審査結果文字列の取得
//		StringBuffer strKekka2 = new StringBuffer();
//		if(kekka2 != null){
//			try{
//				strKekka2.append(StatusCode.getKekka2Name(kekka2));
//			}catch(NumberFormatException e){
//				System.out.println("2次審査結果を数値として認識できませんでした。");
//			}
//		}
//		
//		//置換対象文字列を置換
//		if(jokyoName != null){
//			jokyoName = jokyoName.replaceAll(SHINSA_KEKKA_1, strKekka1.toString());
//			jokyoName = jokyoName.replaceAll(SHINSA_KEKKA_2, strKekka2.toString());
//		}else{
//			System.out.println("申請処理状況がnull。空文字に置き換えます。 " + userInfo.getRole());
//			jokyoName = "";
//		}
//
//		String jokyoName = getJokyoName(connection,
//				                        jigyoId,
//				                        jokyoId,
//				                        saishinseiFlag,
//				                        kekka1Abc,
//				                        kekka1Ten,
//				                        kekka2,
//				                        NOT_RYOIKI
//										);
//		return jokyoName;
//	}
// 2006/07/17 dyh delete end

	/**
	 * 実行するユーザに該当する申請状況名（申請状況を表す文字列）を返す。
	 * 文字列に{KEKKA1}が含まれていた場合は、1次審査結果(ABC)と1次審査結果(点数)を
	 * 文字列結合したものと置換、{KEKKA2}が含まれていた場合は2次審査結果(文字列)と
	 * 置換した文字列を返す。
	 * @param connection
	 * @param jigyoId
	 * @param jokyoId
	 * @param saishinseiFlag
	 * @param kekka1Abc
	 * @param kekka1Ten
	 * @param kekka2
     * @param ryoikiFlg 申請者と領域代表者の区分
	 * @return String
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	protected String getJokyoName(Connection connection,
								  String jigyoId,
								  String jokyoId,
								  String saishinseiFlag,
								  String kekka1Abc,
								  String kekka1Ten,
								  String kekka2,
								  int ryoikiFlg)
		    throws NoDataFoundException, ApplicationException {
// 2006/07/17 dyh update start
//		//-----事業IDを元に公開フラグを取得する-----
//		// SQL文の作成
//		String select = "SELECT KOKAI_FLG FROM JIGYOKANRI WHERE JIGYO_ID = '" + jigyoId + "'";	
//			
//		//DBレコード取得
//		List resultList = null;
//		try {
//			resultList = SelectUtil.select(connection, select);
//		}catch(Exception e){
//			throw new ExceptionInInitializerError("事業管理データ検索中にDBエラーが発生しました。");
//		}
//		int jokyoNameFlg;
//		if(ryoikiFlg == NOT_RYOIKI) {
//			//公開フラグを取得する
//			Map resultMap = (Map)resultList.get(0);
//			Number kokaiFlgObj = (Number)resultMap.get("KOKAI_FLG");
//			jokyoNameFlg = 0;
//			if(kokaiFlgObj != null){
//				jokyoNameFlg = kokaiFlgObj.intValue();
//			}
//		} else {
//			jokyoNameFlg = ryoikiFlg;
//		}
        int jokyoNameFlg = 0;
        if(ryoikiFlg == NOT_RYOIKI){
            // 公開フラグを取得する
            jokyoNameFlg = getKokaiFlg(connection, jigyoId);
        }else{
            jokyoNameFlg = ryoikiFlg;
        }
// 2006/07/17 dyh update end

		//実行ユーザに紐づいた申請状況Mapを取得する
		List list = (List)MASTER_STATUS_MAP.get(userInfo.getRole());
		Map  map  = (Map)list.get(jokyoNameFlg);
		
		//申請状況ID＋再申請フラグを元に申請状況名称を取得
		String jokyoName = (String)map.get(jokyoId + saishinseiFlag);
		
		//1次審査結果文字列の取得
		StringBuffer strKekka1 = new StringBuffer();
		if(kekka1Abc != null){
			strKekka1.append(kekka1Abc);
		}else{
			//1次審査結果ABCがセットされていない場合は、1次審査結果点を表示する
			if(kekka1Ten != null){
				strKekka1.append(kekka1Ten);
			}
		}
		//2次審査結果文字列の取得
		StringBuffer strKekka2 = new StringBuffer();
		if(kekka2 != null){
			try{
				strKekka2.append(StatusCode.getKekka2Name(kekka2));
			}catch(NumberFormatException e){
				System.out.println("2次審査結果を数値として認識できませんでした。");
			}
		}
		
		//置換対象文字列を置換
		if(jokyoName != null){
			jokyoName = jokyoName.replaceAll(SHINSA_KEKKA_1, strKekka1.toString());
			jokyoName = jokyoName.replaceAll(SHINSA_KEKKA_2, strKekka2.toString());
		}else{
			System.out.println("申請処理状況がnull。空文字に置き換えます。 " + userInfo.getRole());
			jokyoName = "";
		}
	
		return jokyoName;
	}

// 2006/07/17 dyh add start
    /**
     * 公開フラグを取得する
     * @param connection コネクション
     * @param jigyoId 事業ID
     */
    private int getKokaiFlg(Connection connection,String jigyoId)
            throws NoDataFoundException, ApplicationException{

        //-----事業IDを元に公開フラグを取得する-----
        // SQL文の作成
        String select = "SELECT KOKAI_FLG FROM JIGYOKANRI " + dbLink + " WHERE JIGYO_ID = '" + jigyoId + "'";  
            
        //DBレコード取得
        List resultList = null;
        try {
            resultList = SelectUtil.select(connection, select);
        }catch(DataAccessException e){
            throw new ApplicationException("事業管理データ検索中にDBエラーが発生しました。");
        }
        // 公開フラグを取得する
        Map resultMap = (Map)resultList.get(0);
        Number kokaiFlgObj = (Number)resultMap.get("KOKAI_FLG");
        if(kokaiFlgObj != null){
            return kokaiFlgObj.intValue();
        }
        return 0;
    }
// 2006/07/17 dyh add end
}