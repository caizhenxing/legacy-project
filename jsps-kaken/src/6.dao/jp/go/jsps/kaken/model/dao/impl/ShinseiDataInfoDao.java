/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : ShinseiDataInfoDao.java
 *    Description : 申請情報データアクセスクラス
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/12/08    V1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.select.BaseCallbackHandler;
import jp.go.jsps.kaken.model.dao.select.JDBCReading;
import jp.go.jsps.kaken.model.dao.select.PreparedStatementCreator;
import jp.go.jsps.kaken.model.dao.select.RowCallbackHandler;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.UserAuthorityException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.HyokaSearchInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.shinsei.DaihyouInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KadaiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KanrenBunyaKenkyushaInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuKeihiSoukeiInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.SortUtil;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 申請情報データアクセスクラス。
 */
public class ShinseiDataInfoDao {

    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------
	
    /** ログ */
    protected static final Log log               = LogFactory.getLog(ShinseiDataInfoDao.class);
    
    /** CSVカラム名用リスト */
    private static List csvColumnList             = null;
    
    /** CSVカラム名用リスト（関連分野）*/
    private static List csvColumnList4Kanrenbunya = null;
    
//2005/08/16 takano 事業コードはIJigyoCdから取得させるため削除 ここから -----
//  //2005/04/05 追加 ----------------------------------------------ここから 
//  //理由 二重登録のチェックを行うため
//  /** 事業コード：基盤事業(S) */
//  private static final String JIGYO_CD_KIBAN_S = "00031";
//  
//  /** 事業コード：基盤事業(A)（一般） */
//  private static final String JIGYO_CD_KIBAN_A_IPPAN = "00041";
//  
//  /** 事業コード：基盤事業(A)（海外） */
//  private static final String JIGYO_CD_KIBAN_A_KAIGAI = "00043";
//  
//  /** 事業コード：基盤事業(B)（一般） */
//  private static final String JIGYO_CD_KIBAN_B_IPPAN = "00051";
//  
//  /** 事業コード：基盤事業(B)（海外） */
//  private static final String JIGYO_CD_KIBAN_B_KAIGAI = "00053";
//  
//  /** 事業コード：基盤事業(C)（一般） */
//  //2005/04/26 基盤事業(C)（一般）の事業コードを00063から00061に変更
//  private static final String JIGYO_CD_KIBAN_C_IPPAN = "00061";
//  
//  /** 事業コード：基盤事業(C)（企画） */
//  private static final String JIGYO_CD_KIBAN_C_KIKAKU = "00062";
//  
//  /** 事業コード：萌芽研究 */
//  private static final String JIGYO_CD_HOUGA = "00111";
//  
//  /** 事業コード：若手研究(A) */
//  private static final String JIGYO_CD_WAKATE_A = "00121";
//  
//  /** 事業コード：若手研究(B) */
//  private static final String JIGYO_CD_WAKATE_B = "00131";
//
//// 20050523 Start
//  private static final String JIGYO_CD_TOKUTEI    =   "00021";    /** 事業コード：特定領域 */
//// Hoirkoshi End
//  
//2005/08/16 takano 事業コードはIJigyoCdから取得させるため削除 ここまで -----

    /** 二重申請のチェック対象となる事業CDのLISTを各事業CDをキーとして格納されているMap */
    private static final Map dupliCheckJigyoCDMap = getDupliCheckJigyoCDMap();
//  //2005/04/05 追加 ----------------------------------------------ここまで 

    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------

    /** 実行するユーザ情報 */
    private UserInfo userInfo = null;

    /** DBリンク名 */
    private String   dbLink   = "";

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------
    /**
     * コンストラクタ。
     * @param userInfo  実行するユーザ情報
     */
    public ShinseiDataInfoDao(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    
    //2005/04/05 追加 ----------------------------------------------ここから 
    //理由 二重登録のチェックを行うため
    /**
     * 二重登録のチェックを行う
     * @return Map
     */
    private static Map getDupliCheckJigyoCDMap() {
        HashMap jigyoCDMap = new HashMap();
        ArrayList dupliCheckJigyoCD = new ArrayList();

//2005/08/16 takano 事業コードはIJigyoCdから取得させるため削除 ここから -----
//      //基盤S
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_KIKAKU);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_S,dupliCheckJigyoCD);
//
//      //基盤(A)（一般）
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_A_IPPAN,dupliCheckJigyoCD);
//
//      //基盤(A)（海外）
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_A_KAIGAI,dupliCheckJigyoCD);
//  
//      //基盤(B)（一般）
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_B_IPPAN,dupliCheckJigyoCD);
//
//      //基盤(B)（海外）
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_B_KAIGAI,dupliCheckJigyoCD);
//      
//      //基盤(C)（一般）
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_HOUGA);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_C_IPPAN,dupliCheckJigyoCD);
//
//      //基盤(C)（企画）
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_KIKAKU);
//      jigyoCDMap.put(JIGYO_CD_KIBAN_C_KIKAKU,dupliCheckJigyoCD);
//
//      //萌芽
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_HOUGA);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_HOUGA,dupliCheckJigyoCD);
//
//      //若手(A)
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_WAKATE_A,dupliCheckJigyoCD);
//
//      //若手(B)
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_S);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_A_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_B_KAIGAI);
//      dupliCheckJigyoCD.add(JIGYO_CD_KIBAN_C_IPPAN);
//      dupliCheckJigyoCD.add(JIGYO_CD_HOUGA);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_A);
//      dupliCheckJigyoCD.add(JIGYO_CD_WAKATE_B);
//      jigyoCDMap.put(JIGYO_CD_WAKATE_B,dupliCheckJigyoCD);
//2005/08/16 takano 事業コードはIJigyoCdから取得させるため削除 ここまで -----

//2005/08/16 takano 事業コードはIJigyoCdから取得させる ここから -----
        // 基盤S
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        
        
        //ADD START 2007/07/19 BIS 趙一非
        //若手（S）の重複チェックを修正
        //・若手（S)が申請済みの場合と、新たに若手（S）を申請する場合とで、重複対象となる研究種目にずれがあったため修正。
        //・若スタ、促進費は若手Sと重複可能であるため、「○」に修正。
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);//若手研究(S)
        //ADD END 2007/07/19 BIS 趙一非
        
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_S,dupliCheckJigyoCD);

        // 基盤(A)（一般）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN,dupliCheckJigyoCD);

        // 基盤(A)（海外）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI,dupliCheckJigyoCD);
    
        // 基盤(B)（一般）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN,dupliCheckJigyoCD);

        // 基盤(B)（海外）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI,dupliCheckJigyoCD);
        
        // 基盤(C)（一般）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN,dupliCheckJigyoCD);

        // 基盤(C)（企画）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU,dupliCheckJigyoCD);

        // 萌芽
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_HOGA,dupliCheckJigyoCD);

        // 若手(A)
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A,dupliCheckJigyoCD);

        // 若手(B)
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B,dupliCheckJigyoCD);
        
//2007/02/03 苗　追加ここから
        // 若手(S)
        
        // UPDATE START 2007/07/19 BIS 趙一非
        //若手（S）の重複チェックを修正
        //・若手（S)が申請済みの場合と、新たに若手（S）を申請する場合とで、重複対象となる研究種目にずれがあったため修正。
        //・若スタ、促進費は若手Sと重複可能であるため、「○」に修正。
        dupliCheckJigyoCD=new ArrayList();
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
//        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_S);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_TOKUSUI);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
        //UPDATE END 2007/07/19 BIS 趙一非
        
        
        
        
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S,dupliCheckJigyoCD);
//2007/02/03 苗　追加ここまで        
//2005/08/16 takano 事業コードはIJigyoCdから取得させる ここまで -----
        
//2005/08/16 takano 他事業についても追加する ここから -----
        // 学創（公募）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO,dupliCheckJigyoCD);
        
        // 学創（非公募）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO,dupliCheckJigyoCD);
        
        // 特推
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_TOKUSUI);
        
        
        //ADD START 2007/07/19 BIS 趙一非
        //若手（S）の重複チェックを修正
        //・若手（S)が申請済みの場合と、新たに若手（S）を申請する場合とで、重複対象となる研究種目にずれがあったため修正。
        //・若スタ、促進費は若手Sと重複可能であるため、「○」に修正。
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);//若手研究(S)
        //ADD END 2007/07/19 BIS 趙一非
        
        
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_TOKUSUI,dupliCheckJigyoCD);
        
        // 特定継続
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU,dupliCheckJigyoCD);
        
//2006/07/27　苗　追加ここから
        // 特定新規
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI);
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI,dupliCheckJigyoCD);
//2006/07/27　苗　追加ここまで        
      
//2005/08/16 takano 他事業についても追加する ここまで -----
        
//      // 若手スタートアップ 
//      dupliCheckJigyoCD=new ArrayList();
//      dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      jigyoCDMap.put(IJigyoCd.JIGYO_CD_WAKATESTART,dupliCheckJigyoCD);
        
// 2006/02/13 Start
        // 特別研究促進費（若手研究(A)相当）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS 趙一非
        //若手（S）の重複チェックを修正
        //・若手（S)が申請済みの場合と、新たに若手（S）を申請する場合とで、重複対象となる研究種目にずれがあったため修正。
        //・若スタ、促進費は若手Sと重複可能であるため、「○」に修正。
//2007/02/13 苗　追加ここから
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13　苗　追加ここまで        
        
//DEL END 2007/07/19 BIS 趙一非
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A,dupliCheckJigyoCD);
        
        // 特別研究促進費（若手研究(B)相当）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS 趙一非
        //若手（S）の重複チェックを修正
        //・若手（S)が申請済みの場合と、新たに若手（S）を申請する場合とで、重複対象となる研究種目にずれがあったため修正。
        //・若スタ、促進費は若手Sと重複可能であるため、「○」に修正。
//2007/02/13 苗　追加ここから
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13　苗　追加ここまで        
        
//DEL END 2007/07/19 BIS 趙一非
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B,dupliCheckJigyoCD);
//Nae End
        
        //2006/02/14
        // 特別研究促進費（基盤研究(A)相当） 
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS 趙一非
        //若手（S）の重複チェックを修正
        //・若手（S)が申請済みの場合と、新たに若手（S）を申請する場合とで、重複対象となる研究種目にずれがあったため修正。
        //・若スタ、促進費は若手Sと重複可能であるため、「○」に修正。
//2007/02/13 苗　追加ここから
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13　苗　追加ここまで        
        
//DEL END 2007/07/19 BIS 趙一非
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A,dupliCheckJigyoCD);
        
        // 特別研究促進費（基盤研究(B)相当）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS 趙一非
        //若手（S）の重複チェックを修正
        //・若手（S)が申請済みの場合と、新たに若手（S）を申請する場合とで、重複対象となる研究種目にずれがあったため修正。
        //・若スタ、促進費は若手Sと重複可能であるため、「○」に修正。
//2007/02/13 苗　追加ここから
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13　苗　追加ここまで        
        
//DEL END 2007/07/19 BIS 趙一非
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B,dupliCheckJigyoCD);

        // 特別研究促進費（基盤研究(C)相当）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS 趙一非
        //若手（S）の重複チェックを修正
        //・若手（S)が申請済みの場合と、新たに若手（S）を申請する場合とで、重複対象となる研究種目にずれがあったため修正。
        //・若スタ、促進費は若手Sと重複可能であるため、「○」に修正。
//2007/02/13 苗　追加ここから
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13　苗　追加ここまで        
        
//DEL END 2007/07/19 BIS 趙一非
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C,dupliCheckJigyoCD);

        // 若手研究（スタートアップ）
        dupliCheckJigyoCD=new ArrayList();
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
        dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_WAKATESTART);
//      DEL START 2007/07/19 BIS 趙一非
        //若手（S）の重複チェックを修正
        //・若手（S)が申請済みの場合と、新たに若手（S）を申請する場合とで、重複対象となる研究種目にずれがあったため修正。
        //・若スタ、促進費は若手Sと重複可能であるため、「○」に修正。
//2007/02/13 苗　追加ここから
        //dupliCheckJigyoCD.add(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/02/13　苗　追加ここまで        
        
//DEL END 2007/07/19 BIS 趙一非
        
        jigyoCDMap.put(IJigyoCd.JIGYO_CD_WAKATESTART,dupliCheckJigyoCD);
// syuu 追加 ----------------------------------------------ここまで   
        return jigyoCDMap;
    }
    //2005/04/05 追加 ----------------------------------------------ここまで 

    /**
     * コンストラクタ。
     * @param userInfo 実行するユーザ情報
     * @param dbLink   DBリンク名
     */
    public ShinseiDataInfoDao(UserInfo userInfo, String dbLink){
        this.userInfo = userInfo;
        this.dbLink   = dbLink;
    }

    //---------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------
    

    /**
     * 整理番号を返す。
     * <li>学創･･･募集区分(1桁)＋同事業ID、同所属機関ごとの連番(3桁)</li>
     * <li>特推･･･同事業ID、同所属機関ごとの連番(4桁)</li>
     * <li>基盤･･･同事業ID、同所属機関ごとの連番(4桁)（暫定）</li>
     * 連番は１から始まり、桁があふれた場合は０から再度連番を振っていく。
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    public String getSeiriNumber(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        String jigyoKubun = dataInfo.getKadaiInfo().getJigyoKubun();
        //学創の場合
        if(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(jigyoKubun) ||
           IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO.equals(jigyoKubun)) {
            return getSequenceNumber4Gakusou(connection, dataInfo);

        //特推の場合
        } else if (IJigyoKubun.JIGYO_KUBUN_TOKUSUI.equals(jigyoKubun)){
            return getSequenceNumber4Tokusui(connection, dataInfo);

        //基盤の場合
        } else if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKubun)){
            return getSequenceNumber4Kiban(connection, dataInfo);

// 20050523 Start
        //特定領域の場合
        } else if (IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(jigyoKubun)){
            return getSequenceNumber4Tokutei(connection, dataInfo);
// Horikoshi End

//2006/03/07 追加ここから
        //若手スタートアップの場合  
        } else if (IJigyoKubun.JIGYO_KUBUN_WAKATESTART.equals(jigyoKubun)){
            return getSequenceNumber4Kiban(connection, dataInfo);

        //特別研究促進費の場合    
        } else if (IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI.equals(jigyoKubun)){
            return getSequenceNumber4Kiban(connection, dataInfo);
//苗　追加ここまで

        //それ以外の場合暫定的に特推と同じ採番ルールにしておく）
        } else {
            return getSequenceNumber4Tokusui(connection, dataInfo);
        }
    }   

    /**
     * 整理番号（学創用）を返す。
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    private String getSequenceNumber4Gakusou(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        //受付番号の8桁目から3文字抜き出した値の最大値にプラス1。）
        String select = "TO_CHAR(MAX(SUBSTR(A.UKETUKE_NO,8,3)) + 1, 'FM000') SEQ_NUM";
        
        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI"+dbLink+" A"
            + " WHERE"
            + "  A.UKETUKE_NO IS NOT NULL"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, dataInfo.getDaihyouInfo().getShozokuCd());
            preparedStatement.setString(i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
             
            String seqNumber = null;
            if (recordSet.next()) {
                seqNumber= recordSet.getString(1);
                if(seqNumber == null){
                    seqNumber = "001";
                }
            }
            
            //後ろから3桁に変換する（桁あふれ対策）
            seqNumber = StringUtils.right(seqNumber,3);
            String jigyoKubun = dataInfo.getKadaiInfo().getJigyoKubun();        
            if(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(jigyoKubun)){
                //非公募の場合は頭に「0」をつける
                seqNumber = "0" + seqNumber;
            }else{
                //公募の場合は頭に回数をつける
                seqNumber = dataInfo.getKaisu() + seqNumber;
            }
            return seqNumber;
             
        } catch (SQLException ex) {
            throw new DataAccessException("申請データテーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

    /**
     * 整理番号（特推用）を返す。
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    private String getSequenceNumber4Tokusui(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        //受付番号の7桁目から4文字抜き出した値の最大値にプラス1。）
        String select = "TO_CHAR(MAX(SUBSTR(A.UKETUKE_NO,7,4)) + 1, 'FM0000') SEQ_NUM";

        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI" + dbLink + " A"
            + " WHERE"
            + "  A.UKETUKE_NO IS NOT NULL"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, dataInfo.getDaihyouInfo().getShozokuCd());
            preparedStatement.setString(i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
             
            String seqNumber = null;
            if (recordSet.next()) {
                seqNumber= recordSet.getString(1);
                if(seqNumber == null){
                    seqNumber = "0001";
                }
            }
            return StringUtils.right(seqNumber,4);
             
        } catch (SQLException ex) {
            throw new DataAccessException("申請データテーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

// 20050523 Start
    /**
     * 整理番号（特定領域用）を返す。
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    private String getSequenceNumber4Tokutei(Connection connection, ShinseiDataInfo dataInfo)
            throws DataAccessException {

        //受付番号の7桁目から4文字抜き出した値の最大値にプラス1。）
//2007/02/03 苗　修正ここから   conutからmaxへ変更 
//        String select = "TO_CHAR(count(*) + 1, 'FM0000') SEQ_NUM";
        String select = "TO_CHAR(MAX(SUBSTR(UKETUKE_NO,7,4)) + 1, 'FM0000') SEQ_NUM";
//2007/02/03　苗　修正ここまで        
        String query = 
            "SELECT "
                + select
                + " FROM"
                + "  SHINSEIDATAKANRI" + dbLink + " A"
                + " WHERE"
                + "  A.UKETUKE_NO IS NOT NULL"
                + " AND"
                + "  A.SHOZOKU_CD = ?"
                + " AND"
                //2005.08.09 iso 空対応
//              + "  A.RYOIKI_NO = ?"
                + "  DECODE(A.RYOIKI_NO, NULL, '-', RYOIKI_NO) = ?"     //空の場合"-"に置き換える
                + " AND"
                //2005.08.09 iso 空対応
//              + "  A.KOMOKU_NO = ?"
                + "  DECODE(A.KOMOKU_NO, NULL, '-', KOMOKU_NO) = ?"     //空の場合"-"に置き換える
                //2005.08.08 iso 整理番号条件に事業IDを追加
                + " AND"
                + "  A.JIGYO_ID = ?"
                ;
        
        //for debug
        if(log.isDebugEnabled()){log.debug("query:" + query);}

        //DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, dataInfo.getDaihyouInfo().getShozokuCd());
            //2005.08.09 iso 空対応
            if(dataInfo.getRyouikiNo() == null || "".equals(dataInfo.getRyouikiNo())) {
                preparedStatement.setString(i++, "-");          //空の場合"-"に置き換える
            } else {
                preparedStatement.setString(i++, dataInfo.getRyouikiNo());
            }
            //2005.08.09 iso 空対応
            if(dataInfo.getRyouikiKoumokuNo() == null || "".equals(dataInfo.getRyouikiKoumokuNo())) {
                preparedStatement.setString(i++, "-");          //空の場合"-"に置き換える
            } else {
                preparedStatement.setString(i++, dataInfo.getRyouikiKoumokuNo());
            }
            //2005.08.08 iso 整理番号条件に事業IDを追加
            preparedStatement.setString(i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
            String seqNumber = null;
            if (recordSet.next()) {
                seqNumber= recordSet.getString(1);
                if(seqNumber == null){
                    seqNumber = "0001";
                }
            }
            return StringUtils.right(seqNumber,4);
             
        } catch (SQLException ex) {
            throw new DataAccessException("申請データテーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
// Horikoshi End

//  2005/04/12 追加 ここから----------
//  理由:基盤の申請IDルール実装のため
    /**
     * 整理番号（基盤用）を返す。
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    private String getSequenceNumber4Kiban(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        //所属機関・新規/継続・細目コード・分割番号ごとに連番を取得する
//        String select = "TO_CHAR(count(*) + 1, 'FM0000') SEQ_NUM";
//2006/11/21 受付番号番号重複のバグは以下の修正で解決できる
        String select = "TO_CHAR(MAX(SUBSTR(UKETUKE_NO,7,4)) + 1, 'FM0000') SEQ_NUM";
        
        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI" + dbLink + " A"
            + " WHERE"
            + "  A.UKETUKE_NO IS NOT NULL"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            //2005.08.09 iso 空対応
//          + "  A.SHINSEI_KUBUN = ?"
            //2005.11.17 iso 新規継続区分は採番ルールから除く
//          + "  DECODE(A.SHINSEI_KUBUN, NULL, '-', SHINSEI_KUBUN) = ?"     //空の場合"-"に置き換える
//          + " AND"
            + "  A.BUNKASAIMOKU_CD = ?"
            + " AND"
            //2005.08.08 iso 整理番号条件に事業IDを追加
//          + "  A.BUNKATSU_NO = ?"
            + "  DECODE(A.BUNKATSU_NO, NULL, '-', BUNKATSU_NO) = ?"         //空の場合"-"に置き換える
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, dataInfo.getDaihyouInfo().getShozokuCd());
            //2005.11.17 iso 新規継続区分は採番ルールから除く
//          //2005.08.09 iso 空対応
//          if(dataInfo.getShinseiKubun() == null || "".equals(dataInfo.getShinseiKubun())) {
//              preparedStatement.setString(i++, "-");          //空の場合"-"に置き換える
//          } else {
//              preparedStatement.setString(i++, dataInfo.getShinseiKubun());
//          }
            preparedStatement.setString(i++, dataInfo.getKadaiInfo().getBunkaSaimokuCd());
            //2005.08.08 iso 整理番号条件に事業IDを追加
            if(dataInfo.getKadaiInfo().getBunkatsuNo() == null || "".equals(dataInfo.getKadaiInfo().getBunkatsuNo())) {
                preparedStatement.setString(i++, "-");          //空の場合"-"に置き換える
            } else {
                preparedStatement.setString(i++, dataInfo.getKadaiInfo().getBunkatsuNo());
            }
            preparedStatement.setString(i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
             
            String seqNumber = null;
            if (recordSet.next()) {
                seqNumber= recordSet.getString(1);
                if(seqNumber == null){
                    seqNumber = "0001";
                }
            }
            return StringUtils.right(seqNumber,4);

        } catch (SQLException ex) {
            throw new DataAccessException("申請データテーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//  2005/04/12 追加 ここまで----------
    
    /**
     * 当該申請書を参照することができるかチェックする。
     * 参照可能に該当しない場合は NoDataAccessExceptioin を発生させる。
     * <p>
     * 判断基準は以下の通り。
     * <li>申請者の場合･･･自分の申請者IDのものかどうか
     * <li>所属機関担当者の場合･･･自分の所属機関IDのものかどうか
     * <li>審査員の場合･･･自分に割り振られた申請書かどうか
     * <li>業務担当者･･･自分が担当する事業の申請書かどうか
     * <li>その他の場合･･･なし（すべてOK）
     * </p>
     * @param connection
     * @param primaryKey
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void checkOwnShinseiData(Connection connection, ShinseiDataPk primaryKey)
        throws DataAccessException, NoDataFoundException {
        checkOwnShinseiData(connection, new ShinseiDataPk[]{primaryKey});
    }

    /**
     * 当該申請書を参照することができるかチェックする。
     * 参照可能に該当しない場合は NoDataAccessExceptioin を発生させる。
     * 指定数と該当データ数が一致しない場合も NoDataAccessExceptioin を発生させる。
     * <p>
     * 判断基準は以下の通り。
     * <li>申請者の場合･･･自分の申請者IDのものかどうか
     * <li>所属機関担当者の場合･･･自分の所属機関IDのものかどうか
     * <li>審査員の場合･･･自分に割り振られた申請書かどうか
     * <li>業務担当者･･･自分が担当する事業の申請書かどうか
     * <li>その他の場合･･･なし（すべてOK）
     * </p>
     * @param connection
     * @param primaryKeys
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void checkOwnShinseiData(Connection connection, ShinseiDataPk[] primaryKeys)
        throws DataAccessException, NoDataFoundException {

        String table = " SHINSEIDATAKANRI" + dbLink + " A";
        String query = null;
        
        //ログインユーザによって条件式を分岐する
        //---申請者
        if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
            query = " AND"
                    + " A.SHINSEISHA_ID = '"
                    + EscapeUtil.toSqlString(userInfo.getShinseishaInfo().getShinseishaId())
                    + "'";
                    
        //---所属機関担当者
        }else if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
            query = " AND"
                    + " A.SHOZOKU_CD = '"
                    + EscapeUtil.toSqlString(userInfo.getShozokuInfo().getShozokuCd())
                    + "'";
                    
        //---審査員
        }else if(userInfo.getRole().equals(UserRole.SHINSAIN)){
            table = " SHINSEIDATAKANRI" + dbLink + " A,"
                    + " (SELECT SYSTEM_NO FROM SHINSAKEKKA"+dbLink
                    + "  WHERE"
                    + "    SHINSAIN_NO = '"
                    +      EscapeUtil.toSqlString(userInfo.getShinsainInfo().getShinsainNo())
                    + "') B";
                    
            query = " AND"
                    + " A.SYSTEM_NO = B.SYSTEM_NO"; 
        
        //---業務担当者
        }else if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
            table = " SHINSEIDATAKANRI" + dbLink + " A,"
                    + " (SELECT JIGYO_CD FROM ACCESSKANRI"
                    + "  WHERE"
                    + "    GYOMUTANTO_ID = '"
                    +      EscapeUtil.toSqlString(userInfo.getGyomutantoInfo().getGyomutantoId())
                    + "') B";
                    
            query = " AND"
            		//2007/3/23 仕様変更の為修正する
                    //特別研究促進費（年複数回応募の試行）の場合は促進費の全事業で検索する
            		//+ " B.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5)";   //事業コード
            		+ " B.JIGYO_CD = DECODE(SUBSTR(A.JIGYO_ID,3,5),'00152','00154','00153','00154','00155','00154','00156','00154',SUBSTR(A.JIGYO_ID,3,5))";
        
        //---それ以外
        }else{
            query = ""; 
        }
        
        //SQLの作成
        String select = 
            "SELECT COUNT(A.SYSTEM_NO) FROM " + table
                + " WHERE"
                + " A.SYSTEM_NO IN (" + getQuestionMark(primaryKeys.length) + ")"
                + query
                ;

        if (log.isDebugEnabled()){
        	log.debug("審査結果参照："+select);
        }
        
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        int count = 0;
        try {
            //登録
            preparedStatement = connection.prepareStatement(select);
            int index = 1;
            for(int i=0; i<primaryKeys.length; i++){
                DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys[i].getSystemNo());
            }
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("申請データ管理テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }       
        
        //該当件数のチェック
        if(count !=  primaryKeys.length){
            throw new UserAuthorityException("参照可能な申請データではありません。");
        }
    }

    /**
     * 指定されたシステム受付番号の簡易申請情報を取得する。
     * @param connection
     * @param primaryKeys
     * @param checkFlg
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public SimpleShinseiDataInfo selectSimpleShinseiDataInfo(
        Connection connection,
        ShinseiDataPk primaryKeys,
        boolean checkFlg)
            throws DataAccessException, NoDataFoundException {
        
        return selectSimpleShinseiDataInfos(
            connection,
            new ShinseiDataPk[]{primaryKeys},
            checkFlg)[0];
    }

    /**
     * 指定されたシステム受付番号の簡易申請情報を取得する。
     * 複数のシステム受付番号を指定した場合、１件でも該当するレコードが存在すれば
     * NoDataFoundExceptionは発生しない。すべてのレコードが取得できたかどうかは
     * 上位側で判断すること。（削除済みの申請データは除外となる。）
     * @param connection
     * @param primaryKeys
     * @param checkFlg
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException １件も該当するレコードが存在しなかった場合
     */
    public SimpleShinseiDataInfo[] selectSimpleShinseiDataInfos(
            Connection connection,
            ShinseiDataPk[] primaryKeys,
            boolean checkFlg)
            throws DataAccessException, NoDataFoundException {

        //システム受付番号が指定されていない場合はnullを返す
        if(primaryKeys == null || primaryKeys.length == 0){
            return null;
        }
                
        if(checkFlg){
            //参照可能申請データかチェック
            checkOwnShinseiData(connection, primaryKeys);
        }
        
        String query =
        "SELECT "
            + " A.SYSTEM_NO,"                   //システム受付番号
            + " A.UKETUKE_NO,"                  //申請番号
            + " A.JIGYO_ID,"                    //事業ID
            + " A.NENDO,"                       //年度
            + " A.KAISU,"                       //回数
            + " A.JIGYO_NAME,"                  //事業名
            + " A.SHINSEISHA_ID,"               //申請者ID
            + " A.SAKUSEI_DATE,"                //申請書作成日
            + " A.SHONIN_DATE,"                 //所属期間承認日
            + " A.NAME_KANJI_SEI,"              //申請者氏名（漢字等-姓）
            + " A.NAME_KANJI_MEI,"              //申請者氏名（漢字等-名）
            + " A.KENKYU_NO,"                   //申請者研究者番号
            + " A.SHOZOKU_CD,"                  //所属機関コード
            + " A.SHOZOKU_NAME,"                //所属機関名
            + " A.SHOZOKU_NAME_RYAKU,"          //所属機関名（略称）
            + " A.BUKYOKU_NAME,"                //部局名
            + " A.BUKYOKU_NAME_RYAKU,"          //部局名（略称）
            + " A.SHOKUSHU_NAME_KANJI,"         //職名
            + " A.SHOKUSHU_NAME_RYAKU,"         //職名（略称）    
            + " A.KADAI_NAME_KANJI,"            //研究課題名(和文）
            + " A.JIGYO_KUBUN,"                 //事業区分
            + " A.SUISENSHO_PATH,"              //推薦書パス
            + " A.JURI_KEKKA,"                  //受理結果
            + " A.JURI_BIKO,"                   //受理備考
//2005/07/25 整理番号追加
            + " A.JURI_SEIRI_NO,"               //整理番号
//2005/07/25
            + " A.KEKKA1_ABC,"                  //1次審査結果(ABC)
            + " A.KEKKA1_TEN,"                  //1次審査結果(点数)
            + " A.KEKKA1_TEN_SORTED,"           //1次審査結果(点数順)
            + " A.KEKKA2,"                      //2次審査結果
            + " A.JOKYO_ID,"                    //申請状況ID
            + " A.SAISHINSEI_FLG,"              //再申請フラグ
//2006/06/16 苗　追加ここから
            + " A.KOMOKU_NO,"                   //研究項目番号
            + " A.CHOSEIHAN,"                   //調整班
            + " A.EDITION,"                     //版  
//2006/06/16 苗　追加ここまで            
            + " B.UKETUKEKIKAN_END,"             //学振受付期限（終了）
            + " B.RYOIKI_KAKUTEIKIKAN_END"        //領域代表者確定締切日
            + " FROM"
            + " SHINSEIDATAKANRI"+dbLink+" A,"  //申請データ管理テーブル
            + " JIGYOKANRI"+dbLink+" B"         //事業情報管理テーブル
            + " WHERE"
            + " A.SYSTEM_NO IN ("+ getQuestionMark(primaryKeys.length) +")"
            + " AND"
            + " A.DEL_FLG = 0"                  //削除フラグが[0]
            + " AND"
            + " A.JIGYO_ID = B.JIGYO_ID"        //事業IDが同じもの
            + " ORDER BY A.SYSTEM_NO";
        
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        
        SimpleShinseiDataInfo[] simpleInfos = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            for(int i=0; i<primaryKeys.length; i++){
                DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys[i].getSystemNo());
            }
            recordSet = preparedStatement.executeQuery();
            
            List resultList = new ArrayList();
            while(recordSet.next()){
                SimpleShinseiDataInfo result = new SimpleShinseiDataInfo();
                result.setSystemNo(recordSet.getString("SYSTEM_NO"));
                result.setUketukeNo(recordSet.getString("UKETUKE_NO"));
                result.setJigyoId(recordSet.getString("JIGYO_ID"));
                result.setNendo(recordSet.getString("NENDO"));
                result.setKaisu(recordSet.getString("KAISU"));
                result.setJigyoName(recordSet.getString("JIGYO_NAME"));
                result.setShinseishaId(recordSet.getString("SHINSEISHA_ID"));
                result.setSakuseiDate(recordSet.getDate("SAKUSEI_DATE"));
                result.setShoninDate(recordSet.getDate("SHONIN_DATE"));
                result.setShinseishaNameSei(recordSet.getString("NAME_KANJI_SEI"));             
                result.setShinseishaNameMei(recordSet.getString("NAME_KANJI_MEI"));
                result.setKenkyuNo(recordSet.getString("KENKYU_NO"));
                result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
                result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
                result.setShozokuNameRyaku(recordSet.getString("SHOZOKU_NAME_RYAKU"));
                result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
                result.setBukyokuNameRyaku(recordSet.getString("BUKYOKU_NAME_RYAKU"));
                result.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));
                result.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));
                result.setKadaiName(recordSet.getString("KADAI_NAME_KANJI"));
                result.setSuisenshoPath(recordSet.getString("SUISENSHO_PATH"));
                result.setJuriKekka(recordSet.getString("JURI_KEKKA"));
                result.setJuriBiko(recordSet.getString("JURI_BIKO"));
//              2005/07/25 整理番号追加
                result.setSeiriNo(recordSet.getString("JURI_SEIRI_NO"));
                
                result.setKekka1Abc(recordSet.getString("KEKKA1_ABC"));             
                result.setKekka1Ten(recordSet.getString("KEKKA1_TEN"));
                result.setKekka1TenSorted(recordSet.getString("KEKKA1_TEN_SORTED"));
                result.setKekka2(recordSet.getString("KEKKA2"));                
                result.setJokyoId(recordSet.getString("JOKYO_ID"));
                result.setSaishinseiFlg(recordSet.getString("SAISHINSEI_FLG"));
//2006/06/16 苗　追加ここから
                result.setKomokuNo(recordSet.getString("KOMOKU_NO"));
                result.setChoseihan(recordSet.getString("CHOSEIHAN"));
                result.setEdition(recordSet.getString("EDITION"));
//2006/06/16　苗　追加ここまで                
                result.setUketukeKikanEnd(recordSet.getDate("UKETUKEKIKAN_END"));
                result.setRyoikiKakuteikikanEnd(recordSet.getDate("RYOIKI_KAKUTEIKIKAN_END"));
                resultList.add(result);             
            }
            
            //戻り値
            simpleInfos = (SimpleShinseiDataInfo[])resultList.toArray(new SimpleShinseiDataInfo[0]);
            if(simpleInfos.length == 0){
                throw new NoDataFoundException(
                    "申請データ管理テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
                        + Arrays.asList(primaryKeys).toString()
                        + "'");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("申請データ管理テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
        
        return simpleInfos;
    }
    
    /**
     * 申請情報を取得する。
     * 削除フラグが「0」の場合（削除されていない場合）のみ検索する。
     * @param connection            コネクション
     * @param primaryKeys           主キー情報
     * @param checkFlg              参照チェックフラグ
     * @return                      所属機関情報
     * @throws DataAccessException  データ取得中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public ShinseiDataInfo selectShinseiDataInfo(
            Connection connection,
            ShinseiDataPk primaryKeys,
            boolean checkFlg)
            throws DataAccessException, NoDataFoundException {

        return selectShinseiDataInfo(connection, primaryKeys, false, checkFlg);
    }

    /**
     * 申請情報を取得する。
     * 削除フラグが「0」の場合（削除されていない場合）のみ検索する。
     * 取得したレコードをロックする。（comitが行われるまで。）
     * @param connection            コネクション
     * @param primaryKeys           主キー情報
     * @param checkFlg              参照チェックフラグ
     * @return                      所属機関情報
     * @throws DataAccessException  データ取得中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public ShinseiDataInfo selectShinseiDataInfoForLock(
            Connection connection,
            ShinseiDataPk primaryKeys,
            boolean checkFlg)
            throws DataAccessException, NoDataFoundException {

        return selectShinseiDataInfo(connection, primaryKeys, true, checkFlg);
    }

    /**
     * 申請情報を取得する。
     * 削除フラグが「0」の場合（削除されていない場合）のみ検索する。
     * @param connection            コネクション
     * @param primaryKeys           主キー情報
     * @param lock                 trueの場合はレコードをロックする
     * @param checkFlg              参照チェックフラグ
     * @return                      所属機関情報
     * @throws DataAccessException  データ取得中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    private ShinseiDataInfo selectShinseiDataInfo(
            Connection connection,
            ShinseiDataPk primaryKeys,
            boolean lock,
            boolean checkFlg)
            throws DataAccessException, NoDataFoundException {

        if(checkFlg){
            //参照可能申請データかチェック
            checkOwnShinseiData(connection, primaryKeys);
        }
        
        String query =
            "SELECT "
                + " A.SYSTEM_NO"                //システム受付番号
                + ",A.UKETUKE_NO"               //申請番号
                + ",A.JIGYO_ID"                 //事業ID
                + ",A.NENDO"                    //年度
                + ",A.KAISU"                    //回数
                + ",A.JIGYO_NAME"               //事業名
                + ",A.SHINSEISHA_ID"            //申請者ID
                + ",A.SAKUSEI_DATE"             //申請書作成日
                + ",A.SHONIN_DATE"              //所属機関承認日
//2006/07/26 dyh add start 理由：DBで領域代表者確定日を追加
                + ",A.RYOIKI_KAKUTEI_DATE"      //領域代表者確定日
//2006/07/26 dyh add end                
                + ",A.JYURI_DATE"               //学振受理日
                + ",A.NAME_KANJI_SEI"           //申請者氏名（漢字等-姓）
                + ",A.NAME_KANJI_MEI"           //申請者氏名（漢字等-名）
                + ",A.NAME_KANA_SEI"            //申請者氏名（フリガナ-姓）
                + ",A.NAME_KANA_MEI"            //申請者氏名（フリガナ-名）
                + ",A.NAME_RO_SEI"              //申請者氏名（ローマ字-姓）
                + ",A.NAME_RO_MEI"              //申請者氏名（ローマ字-名）
                + ",A.NENREI"                   //年齢
                + ",A.KENKYU_NO"                //申請者研究者番号
                + ",A.SHOZOKU_CD"               //所属機関コード
                + ",A.SHOZOKU_NAME"             //所属機関名
                + ",A.SHOZOKU_NAME_RYAKU"       //所属機関名（略称）
                + ",A.BUKYOKU_CD"               //部局コード
                + ",A.BUKYOKU_NAME"             //部局名
                + ",A.BUKYOKU_NAME_RYAKU"       //部局名（略称）
                + ",A.SHOKUSHU_CD"              //職名コード
                + ",A.SHOKUSHU_NAME_KANJI"      //職名（和文）
                + ",A.SHOKUSHU_NAME_RYAKU"      //職名（略称）
                + ",A.ZIP"                      //郵便番号
                + ",A.ADDRESS"                  //住所
                + ",A.TEL"                      //TEL
                + ",A.FAX"                      //FAX
                + ",A.EMAIL"                    //E-Mail
//2006/06/30 苗　追加ここから
                + ",A.URL"                      //URL  
//2006/06/30　苗　追加ここまで                
                + ",A.SENMON"                   //現在の専門
                + ",A.GAKUI"                    //学位
                + ",A.BUNTAN"                   //役割分担
                + ",A.KADAI_NAME_KANJI"         //研究課題名(和文）
                + ",A.KADAI_NAME_EIGO"          //研究課題名(英文）
                + ",A.JIGYO_KUBUN"              //事業区分
                + ",A.SHINSA_KUBUN"             //審査区分
                + ",A.SHINSA_KUBUN_MEISHO"      //審査区分名称
                + ",A.BUNKATSU_NO"              //分割番号
                + ",A.BUNKATSU_NO_MEISHO"       //分割番号名称
                + ",A.KENKYU_TAISHO"            //研究対象の類型
                + ",A.KEI_NAME_NO"              //系等の区分番号
                + ",A.KEI_NAME"                 //系等の区分
                + ",A.KEI_NAME_RYAKU"           //系等の区分略称
                + ",A.BUNKASAIMOKU_CD"          //細目番号
                + ",A.BUNYA_NAME"               //分野
                + ",A.BUNKA_NAME"               //分科
                + ",A.SAIMOKU_NAME"             //細目
                + ",A.BUNKASAIMOKU_CD2"         //細目番号2
                + ",A.BUNYA_NAME2"              //分野2
                + ",A.BUNKA_NAME2"              //分科2
                + ",A.SAIMOKU_NAME2"            //細目2
                + ",A.KANTEN_NO"                //推薦の観点番号
                + ",A.KANTEN"                   //推薦の観点
                + ",A.KANTEN_RYAKU"             //推薦の観点略称
// 20050803
                + ",A.KAIGIHI"                  //会議費
                + ",A.INSATSUHI"                //印刷費
// Horikoshi
                + ",A.KEIHI1"                   //1年目研究経費
                + ",A.BIHINHI1"                 //1年目設備備品費
                + ",A.SHOMOHINHI1"              //1年目消耗品費
                + ",A.KOKUNAIRYOHI1"            //1年目国内旅費
                + ",A.GAIKOKURYOHI1"            //1年目外国旅費
                + ",A.RYOHI1"                   //1年目旅費
                + ",A.SHAKIN1"                  //1年目謝金等
                + ",A.SONOTA1"                  //1年目その他
                + ",A.KEIHI2"                   //2年目研究経費
                + ",A.BIHINHI2"                 //2年目設備備品費
                + ",A.SHOMOHINHI2"              //2年目消耗品費
                + ",A.KOKUNAIRYOHI2"            //2年目国内旅費
                + ",A.GAIKOKURYOHI2"            //2年目外国旅費
                + ",A.RYOHI2"                   //2年目旅費
                + ",A.SHAKIN2"                  //2年目謝金等
                + ",A.SONOTA2"                  //2年目その他
                + ",A.KEIHI3"                   //3年目研究経費
                + ",A.BIHINHI3"                 //3年目設備備品費
                + ",A.SHOMOHINHI3"              //3年目消耗品費
                + ",A.KOKUNAIRYOHI3"            //3年目国内旅費
                + ",A.GAIKOKURYOHI3"            //3年目外国旅費
                + ",A.RYOHI3"                   //3年目旅費
                + ",A.SHAKIN3"                  //3年目謝金等
                + ",A.SONOTA3"                  //3年目その他
                + ",A.KEIHI4"                   //4年目研究経費
                + ",A.BIHINHI4"                 //4年目設備備品費
                + ",A.SHOMOHINHI4"              //4年目消耗品費
                + ",A.KOKUNAIRYOHI4"            //4年目国内旅費
                + ",A.GAIKOKURYOHI4"            //4年目外国旅費
                + ",A.RYOHI4"                   //4年目旅費
                + ",A.SHAKIN4"                  //4年目謝金等
                + ",A.SONOTA4"                  //4年目その他
                + ",A.KEIHI5"                   //5年目研究経費
                + ",A.BIHINHI5"                 //5年目設備備品費
                + ",A.SHOMOHINHI5"              //5年目消耗品費
                + ",A.KOKUNAIRYOHI5"            //5年目国内旅費
                + ",A.GAIKOKURYOHI5"            //5年目外国旅費
                + ",A.RYOHI5"                   //5年目旅費
                + ",A.SHAKIN5"                  //5年目謝金等
                + ",A.SONOTA5"                  //5年目その他
//2006/06/15 苗　追加ここから  6年目の研究経費を追加する
                + ",KEIHI6"                    //6年目研究経費
                + ",BIHINHI6"                  //6年目設備備品費
                + ",SHOMOHINHI6"               //6年目消耗品費
                + ",KOKUNAIRYOHI6"             //6年目国内旅費
                + ",GAIKOKURYOHI6"             //6年目外国旅費
                + ",RYOHI6"                    //6年目旅費
                + ",SHAKIN6"                   //6年目謝金等
                + ",SONOTA6"                   //6年目その他
//2006/06/15 苗　追加ここまで                  
                + ",A.KEIHI_TOTAL"              //総計-研究経費
                + ",A.BIHINHI_TOTAL"            //総計-設備備品費
                + ",A.SHOMOHINHI_TOTAL"         //総計-消耗品費
                + ",A.KOKUNAIRYOHI_TOTAL"       //総計-国内旅費
                + ",A.GAIKOKURYOHI_TOTAL"       //総計-外国旅費
                + ",A.RYOHI_TOTAL"              //総計-旅費
                + ",A.SHAKIN_TOTAL"             //総計-謝金等
                + ",A.SONOTA_TOTAL"             //総計-その他
                + ",A.SOSHIKI_KEITAI_NO"        //研究組織の形態番号
                + ",A.SOSHIKI_KEITAI"           //研究組織の形態
                + ",A.BUNTANKIN_FLG"            //分担金の有無
                + ",A.KOYOHI"                   //研究支援者雇用経費
                + ",A.KENKYU_NINZU"             //研究者数
                + ",A.TAKIKAN_NINZU"            //他機関の分担者数
                //2005/03/31 追加 ----------------------------------ここから 
                //理由 研究協力者情報入力欄が追加されたため
                + ",A.KYORYOKUSHA_NINZU"        //他機関の分担者数
                //2005/03/31 追加 ----------------------------------ここまで 
                + ",A.SHINSEI_KUBUN"            //新規継続区分
                + ",A.KADAI_NO_KEIZOKU"         //継続分の研究課題番号
                + ",A.SHINSEI_FLG_NO"           //継続分の研究課題番号
                + ",A.SHINSEI_FLG"              //申請の有無
                + ",A.KADAI_NO_SAISYU"          //最終年度課題番号
                + ",A.KAIJIKIBO_FLG_NO"         //開示希望の有無番号
                + ",A.KAIJIKIBO_FLG"            //開示希望の有無
                + ",A.KAIGAIBUNYA_CD"           //海外分野コード
                + ",A.KAIGAIBUNYA_NAME"         //海外分野名称
                + ",A.KAIGAIBUNYA_NAME_RYAKU"   //海外分野略称
                + ",A.KANREN_SHIMEI1"           //関連分野の研究者-氏名1
                + ",A.KANREN_KIKAN1"            //関連分野の研究者-所属機関1
                + ",A.KANREN_BUKYOKU1"          //関連分野の研究者-所属部局1
                + ",A.KANREN_SHOKU1"            //関連分野の研究者-職名1
                + ",A.KANREN_SENMON1"           //関連分野の研究者-専門分野1
                + ",A.KANREN_TEL1"              //関連分野の研究者-勤務先電話番号1
                + ",A.KANREN_JITAKUTEL1"        //関連分野の研究者-自宅電話番号1
                + ",A.KANREN_MAIL1"             //関連分野の研究者-E-mail1
                + ",A.KANREN_SHIMEI2"           //関連分野の研究者-氏名2
                + ",A.KANREN_KIKAN2"            //関連分野の研究者-所属機関2
                + ",A.KANREN_BUKYOKU2"          //関連分野の研究者-所属部局2
                + ",A.KANREN_SHOKU2"            //関連分野の研究者-職名2
                + ",A.KANREN_SENMON2"           //関連分野の研究者-専門分野2
                + ",A.KANREN_TEL2"              //関連分野の研究者-勤務先電話番号2
                + ",A.KANREN_JITAKUTEL2"        //関連分野の研究者-自宅電話番号2
                + ",A.KANREN_MAIL2"             //関連分野の研究者-E-mail2
                + ",A.KANREN_SHIMEI3"           //関連分野の研究者-氏名3
                + ",A.KANREN_KIKAN3"            //関連分野の研究者-所属機関3
                + ",A.KANREN_BUKYOKU3"          //関連分野の研究者-所属部局3
                + ",A.KANREN_SHOKU3"            //関連分野の研究者-職名3
                + ",A.KANREN_SENMON3"           //関連分野の研究者-専門分野3
                + ",A.KANREN_TEL3"              //関連分野の研究者-勤務先電話番号3
                + ",A.KANREN_JITAKUTEL3"        //関連分野の研究者-自宅電話番号3
                + ",A.KANREN_MAIL3"             //関連分野の研究者-E-mail3
                + ",A.XML_PATH"                 //XMLの格納パス
                + ",A.PDF_PATH"                 //PDFの格納パス
                + ",A.JURI_KEKKA"               //受理結果
                + ",A.JURI_BIKO"                //受理結果備考
//2005/07/25 整理番号追加
                + ",A.JURI_SEIRI_NO"            //整理番号
//2005/07/25
                + ",A.SUISENSHO_PATH"           //推薦書の格納パス
                + ",A.KEKKA1_ABC"               //１次審査結果(ABC)
                + ",A.KEKKA1_TEN"               //１次審査結果(点数)
                + ",A.KEKKA1_TEN_SORTED"        //１次審査結果(点数順)
                + ",A.SHINSA1_BIKO"             //１次審査備考
                + ",A.KEKKA2"                   //２次審査結果
                + ",A.SOU_KEHI"                 //総経費（学振入力）
                + ",A.SHONEN_KEHI"              //初年度経費（学振入力）
                + ",A.SHINSA2_BIKO"             //業務担当者記入欄
                + ",A.JOKYO_ID"                 //申請状況ID
                + ",A.SAISHINSEI_FLG"           //再申請フラグ
//              2005/04/13 追加 ここから----------
//              理由:版追加のため

                + ",A.EDITION"                  //版
                
//              2005/04/13 追加 ここまで----------

// 20050530 Start 特定領域の情報を追加したため
                + ",A.KENKYU_KUBUN"             //研究区分
                + ",A.OHABAHENKO"               //大幅な変更
                + ",A.RYOIKI_NO"                //領域番号
                + ",A.RYOIKI_RYAKU"             //領域略称
                + ",A.KOMOKU_NO"                //項目番号
                + ",A.CHOSEIHAN"                //調整班
// Horikoshi End

// 20050712 キーワード情報の追加
                + ",A.KEYWORD_CD"
                + ",A.SAIMOKU_KEYWORD"
                + ",A.OTHER_KEYWORD"
// Horikoshi
//2006/02/10    Syu
                + " ,A.SAIYO_DATE"                //採用年月日
                + " ,A.KINMU_HOUR"                //勤務時間数
                + " ,A.NAIYAKUGAKU"               //特別研究員奨励費内約額
//End
//2007/02/02 苗　追加ここから
                + " ,A.SHOREIHI_NO_NENDO"         //特別研究員奨励費課題番号-年度
                + " ,A.SHOREIHI_NO_SEIRI"         //特別研究員奨励費課題番号-整理番号
//2007/02/02　苗　追加ここまで                
//2006/02/13 Start
                + " ,A.OUBO_SHIKAKU"             //応募資格
                + " ,A.SIKAKU_DATE"              //資格取得年月日
                + " ,A.SYUTOKUMAE_KIKAN"         //資格取得前機関名
                + " ,A.IKUKYU_START_DATE"        //育休等開始日
                + " ,A.IKUKYU_END_DATE"          //育休等終了日
//2006/02/13 End    
//              2006/02/15
                + " ,A.SHINSARYOIKI_NAME"        //分野名
                + " ,A.SHINSARYOIKI_CD"          //分野コード
// syuu End 

                + ",SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD"        //事業コード

                + ",A.DEL_FLG"                  //削除フラグ
// ADD START 2007-07-26 BIS 王志安
                + ",A.NAIYAKUGAKU1"
                + ",A.NAIYAKUGAKU2"
                + ",A.NAIYAKUGAKU3"
                + ",A.NAIYAKUGAKU4"
                + ",A.NAIYAKUGAKU5"
// ADD END 2007-07-26 BIS 王志安
                + " FROM SHINSEIDATAKANRI" + dbLink + " A"
                + " WHERE SYSTEM_NO = ?"
//update2004/10/26 システム管理者向け申請情報検索対応
//              + " AND DEL_FLG = 0"                //削除フラグが[0]
                ;
                                
            //排他制御を行う場合
            if(lock){
                query = query + " FOR UPDATE";
            }
                
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            ShinseiDataInfo result = new ShinseiDataInfo();
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getSystemNo());   //システム受付番号
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                //---基本情報（前半）
                result.setSystemNo(recordSet.getString("SYSTEM_NO"));
                result.setUketukeNo(recordSet.getString("UKETUKE_NO"));
                result.setJigyoId(recordSet.getString("JIGYO_ID"));
                result.setNendo(recordSet.getString("NENDO"));
                result.setKaisu(recordSet.getString("KAISU"));
                result.setJigyoName(recordSet.getString("JIGYO_NAME"));
                result.setShinseishaId(recordSet.getString("SHINSEISHA_ID"));
                result.setSakuseiDate(recordSet.getDate("SAKUSEI_DATE"));
                result.setShoninDate(recordSet.getDate("SHONIN_DATE"));
                result.setRyoikiKakuteiDate(recordSet.getDate("RYOIKI_KAKUTEI_DATE"));
                result.setJyuriDate(recordSet.getDate("JYURI_DATE"));
                result.setJigyoCd(recordSet.getString("JIGYO_CD"));
               
                //---申請者（研究代表者）
                DaihyouInfo daihyouInfo = result.getDaihyouInfo();
                daihyouInfo.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));
                daihyouInfo.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));
                daihyouInfo.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
                daihyouInfo.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
                daihyouInfo.setNameRoSei(recordSet.getString("NAME_RO_SEI"));
                daihyouInfo.setNameRoMei(recordSet.getString("NAME_RO_MEI"));
                daihyouInfo.setNenrei(recordSet.getString("NENREI"));
                daihyouInfo.setKenkyuNo(recordSet.getString("KENKYU_NO"));
                daihyouInfo.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
                daihyouInfo.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
                daihyouInfo.setShozokuNameRyaku(recordSet.getString("SHOZOKU_NAME_RYAKU"));
                daihyouInfo.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
                daihyouInfo.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
                daihyouInfo.setBukyokuNameRyaku(recordSet.getString("BUKYOKU_NAME_RYAKU"));
                daihyouInfo.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));
                daihyouInfo.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));
                daihyouInfo.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));
                daihyouInfo.setZip(recordSet.getString("ZIP"));
                daihyouInfo.setAddress(recordSet.getString("ADDRESS"));
                daihyouInfo.setTel(recordSet.getString("TEL"));
                daihyouInfo.setFax(recordSet.getString("FAX"));
                daihyouInfo.setEmail(recordSet.getString("EMAIL"));
//2006/06/30 苗　追加ここから
                daihyouInfo.setUrl(recordSet.getString("URL"));
//2006/06/30　苗　追加ここまで                
                daihyouInfo.setSenmon(recordSet.getString("SENMON"));
                daihyouInfo.setGakui(recordSet.getString("GAKUI"));
                daihyouInfo.setBuntan(recordSet.getString("BUNTAN"));

                //---研究課題
                KadaiInfo kadaiInfo = result.getKadaiInfo();
                kadaiInfo.setKadaiNameKanji(recordSet.getString("KADAI_NAME_KANJI"));
                kadaiInfo.setKadaiNameEigo(recordSet.getString("KADAI_NAME_EIGO"));
                kadaiInfo.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));
                kadaiInfo.setShinsaKubun(recordSet.getString("SHINSA_KUBUN"));
                kadaiInfo.setShinsaKubunMeisho(recordSet.getString("SHINSA_KUBUN_MEISHO"));
                kadaiInfo.setBunkatsuNo(recordSet.getString("BUNKATSU_NO"));
                kadaiInfo.setBunkatsuNoMeisho(recordSet.getString("BUNKATSU_NO_MEISHO"));
                kadaiInfo.setKenkyuTaisho(recordSet.getString("KENKYU_TAISHO"));
                kadaiInfo.setKeiNameNo(recordSet.getString("KEI_NAME_NO"));
                kadaiInfo.setKeiName(recordSet.getString("KEI_NAME"));
                kadaiInfo.setKeiNameRyaku(recordSet.getString("KEI_NAME_RYAKU"));
                kadaiInfo.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
                kadaiInfo.setBunya(recordSet.getString("BUNYA_NAME"));
                kadaiInfo.setBunka(recordSet.getString("BUNKA_NAME"));
                kadaiInfo.setSaimokuName(recordSet.getString("SAIMOKU_NAME"));
                kadaiInfo.setBunkaSaimokuCd2(recordSet.getString("BUNKASAIMOKU_CD2"));
                kadaiInfo.setBunya2(recordSet.getString("BUNYA_NAME2"));
                kadaiInfo.setBunka2(recordSet.getString("BUNKA_NAME2"));
                kadaiInfo.setSaimokuName2(recordSet.getString("SAIMOKU_NAME2"));
                kadaiInfo.setKantenNo(recordSet.getString("KANTEN_NO"));
                kadaiInfo.setKanten(recordSet.getString("KANTEN"));
                kadaiInfo.setKantenRyaku(recordSet.getString("KANTEN_RYAKU"));

// 2005/04/13 追加 ここから----------
// 理由:版追加のため
                kadaiInfo.setEdition(recordSet.getInt("EDITION"));
// 2005/04/13 追加 ここまで----------

                //---研究経費
                KenkyuKeihiSoukeiInfo soukeiInfo = result.getKenkyuKeihiSoukeiInfo();
//2006/07/03 苗　修正ここから                
//                KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi();
//                for(int j=0; j<keihiInfo.length; j++){
//                    int n = j+1;
//                    keihiInfo[j].setKeihi(recordSet.getString("KEIHI"+n));
//                    keihiInfo[j].setBihinhi(recordSet.getString("BIHINHI"+n));
//                    keihiInfo[j].setShomohinhi(recordSet.getString("SHOMOHINHI"+n));
//                    keihiInfo[j].setKokunairyohi(recordSet.getString("KOKUNAIRYOHI"+n));
//                    keihiInfo[j].setGaikokuryohi(recordSet.getString("GAIKOKURYOHI"+n));
//                    keihiInfo[j].setRyohi(recordSet.getString("RYOHI"+n));
//                    keihiInfo[j].setShakin(recordSet.getString("SHAKIN"+n));
//                    keihiInfo[j].setSonota(recordSet.getString("SONOTA"+n));
//                }
                if (IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(result.getJigyoCd())) {
                    KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi6();
                    for (int j = 0; j < keihiInfo.length; j++) {
                        int n = j + 1;
                        keihiInfo[j].setKeihi(recordSet.getString("KEIHI" + n));
                        keihiInfo[j].setBihinhi(recordSet.getString("BIHINHI" + n));
                        keihiInfo[j].setShomohinhi(recordSet.getString("SHOMOHINHI" + n));
                        keihiInfo[j].setKokunairyohi(recordSet.getString("KOKUNAIRYOHI" + n));
                        keihiInfo[j].setGaikokuryohi(recordSet.getString("GAIKOKURYOHI" + n));
                        keihiInfo[j].setRyohi(recordSet.getString("RYOHI" + n));
                        keihiInfo[j].setShakin(recordSet.getString("SHAKIN" + n));
                        keihiInfo[j].setSonota(recordSet.getString("SONOTA" + n));
                    }
                } else {
                    KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi();
                    for (int j = 0; j < keihiInfo.length; j++) {
                        int n = j + 1;
                        keihiInfo[j].setKeihi(recordSet.getString("KEIHI" + n));
                        keihiInfo[j].setBihinhi(recordSet.getString("BIHINHI" + n));
                        keihiInfo[j].setShomohinhi(recordSet.getString("SHOMOHINHI" + n));
                        keihiInfo[j].setKokunairyohi(recordSet.getString("KOKUNAIRYOHI" + n));
                        keihiInfo[j].setGaikokuryohi(recordSet.getString("GAIKOKURYOHI" + n));
                        keihiInfo[j].setRyohi(recordSet.getString("RYOHI" + n));
                        keihiInfo[j].setShakin(recordSet.getString("SHAKIN" + n));
                        keihiInfo[j].setSonota(recordSet.getString("SONOTA" + n));
                    }
                }
//2006/07/03　苗　修正ここまで                
                soukeiInfo.setKeihiTotal(recordSet.getString("KEIHI_TOTAL"));
                soukeiInfo.setBihinhiTotal(recordSet.getString("BIHINHI_TOTAL"));
                soukeiInfo.setShomohinhiTotal(recordSet.getString("SHOMOHINHI_TOTAL"));
                soukeiInfo.setKokunairyohiTotal(recordSet.getString("KOKUNAIRYOHI_TOTAL"));
                soukeiInfo.setGaikokuryohiTotal(recordSet.getString("GAIKOKURYOHI_TOTAL"));
                soukeiInfo.setRyohiTotal(recordSet.getString("RYOHI_TOTAL"));
                soukeiInfo.setShakinTotal(recordSet.getString("SHAKIN_TOTAL"));
                soukeiInfo.setSonotaTotal(recordSet.getString("SONOTA_TOTAL"));
// 20050803
                soukeiInfo.setMeetingCost(recordSet.getString("KAIGIHI"));      //会議費
                soukeiInfo.setPrintingCost(recordSet.getString("INSATSUHI"));   //印刷費
// Horikoshi

                //---基本情報（中盤）
                result.setSoshikiKeitaiNo(recordSet.getString("SOSHIKI_KEITAI_NO"));
                result.setSoshikiKeitai(recordSet.getString("SOSHIKI_KEITAI"));
                result.setBuntankinFlg(recordSet.getString("BUNTANKIN_FLG"));
                result.setKoyohi(recordSet.getString("KOYOHI"));
                result.setKenkyuNinzu(recordSet.getString("KENKYU_NINZU"));
                result.setTakikanNinzu(recordSet.getString("TAKIKAN_NINZU"));
                //2005/03/31 追加 ------------------------------------------------------ここから
                //理由 研究協力者入力欄が追加されたため
                result.setKyoryokushaNinzu(recordSet.getString("KYORYOKUSHA_NINZU"));
                
                try {
                    result.setKyoryokushaNinzuInt(Integer.parseInt(result.getKyoryokushaNinzu()));
                } catch (NumberFormatException e) {
                    result.setKyoryokushaNinzuInt(0);
                }
                try {
                    result.setKenkyuNinzuInt(Integer.parseInt(result.getKenkyuNinzu()));
                } catch (NumberFormatException e) {
                    result.setKenkyuNinzuInt(1);
                }
                //2005/03/31 追加 ------------------------------------------------------ここまで
                result.setShinseiKubun(recordSet.getString("SHINSEI_KUBUN"));
                result.setKadaiNoKeizoku(recordSet.getString("KADAI_NO_KEIZOKU"));
                result.setShinseiFlgNo(recordSet.getString("SHINSEI_FLG_NO"));
                result.setShinseiFlg(recordSet.getString("SHINSEI_FLG"));
                result.setKadaiNoSaisyu(recordSet.getString("KADAI_NO_SAISYU"));
                result.setKaijikiboFlgNo(recordSet.getString("KAIJIKIBO_FLG_NO"));
                result.setKaijiKiboFlg(recordSet.getString("KAIJIKIBO_FLG"));
                result.setKaigaibunyaCd(recordSet.getString("KAIGAIBUNYA_CD"));
                result.setKaigaibunyaName(recordSet.getString("KAIGAIBUNYA_NAME"));
                result.setKaigaibunyaNameRyaku(recordSet.getString("KAIGAIBUNYA_NAME_RYAKU"));

                //---関連分野の研究者
                KanrenBunyaKenkyushaInfo[] kanrenInfo = result.getKanrenBunyaKenkyushaInfo();
                for(int j=0; j<kanrenInfo.length; j++){
                    int n = j+1;
                    kanrenInfo[j].setKanrenShimei(recordSet.getString("KANREN_SHIMEI"+n));
                    kanrenInfo[j].setKanrenKikan(recordSet.getString("KANREN_KIKAN"+n));
                    kanrenInfo[j].setKanrenBukyoku(recordSet.getString("KANREN_BUKYOKU"+n));
                    kanrenInfo[j].setKanrenShoku(recordSet.getString("KANREN_SHOKU"+n));
                    kanrenInfo[j].setKanrenSenmon(recordSet.getString("KANREN_SENMON"+n));
                    kanrenInfo[j].setKanrenTel(recordSet.getString("KANREN_TEL"+n));
                    kanrenInfo[j].setKanrenJitakuTel(recordSet.getString("KANREN_JITAKUTEL"+n));
                    kanrenInfo[j].setKanrenMail(recordSet.getString("KANREN_MAIL"+n));
                }

                //---基本情報（後半）
                result.setXmlPath(recordSet.getString("XML_PATH"));
                result.setPdfPath(recordSet.getString("PDF_PATH"));
                result.setJuriKekka(recordSet.getString("JURI_KEKKA"));
                result.setJuriBiko(recordSet.getString("JURI_BIKO"));
//              2005/07/25 整理番号追加
                result.setSeiriNo(recordSet.getString("JURI_SEIRI_NO"));

                result.setSuisenshoPath(recordSet.getString("SUISENSHO_PATH"));
                result.setKekka1Abc(recordSet.getString("KEKKA1_ABC"));
                result.setKekka1Ten(recordSet.getString("KEKKA1_TEN"));
                result.setKekka1TenSorted(recordSet.getString("KEKKA1_TEN_SORTED"));
                result.setShinsa1Biko(recordSet.getString("SHINSA1_BIKO"));
                result.setKekka2(recordSet.getString("KEKKA2"));
                result.setSouKehi(recordSet.getString("SOU_KEHI"));
                result.setShonenKehi(recordSet.getString("SHONEN_KEHI"));
                result.setShinsa2Biko(recordSet.getString("SHINSA2_BIKO"));
                result.setJokyoId(recordSet.getString("JOKYO_ID"));
                result.setSaishinseiFlg(recordSet.getString("SAISHINSEI_FLG"));
                result.setDelFlg(recordSet.getString("DEL_FLG"));

// 20050530 Start 特定領域のため追加
                result.setKenkyuKubun(recordSet.getString("KENKYU_KUBUN"));                             // 研究区分
                if(IShinseiMaintenance.TOKUTEI_HENKOU.equals(recordSet.getString("OHABAHENKO"))){       // 大幅な変更
                    result.setChangeFlg(IShinseiMaintenance.CHECK_ON);}                                 //チェックオン
                else{result.setChangeFlg(IShinseiMaintenance.CHECK_OFF);}                               //チェックオフ
                result.setRyouikiNo(recordSet.getString("RYOIKI_NO"));                                  // 領域番号
                result.setRyouikiRyakuName(recordSet.getString("RYOIKI_RYAKU"));                        // 領域略称
                result.setRyouikiKoumokuNo(recordSet.getString("KOMOKU_NO"));                           // 項目番号
                if(IShinseiMaintenance.TOKUTEI_CHOUSEI.equals(recordSet.getString("CHOSEIHAN"))){       // 調整班
                    result.setChouseiFlg(IShinseiMaintenance.CHECK_ON);}                                //チェックオン
                else{result.setChouseiFlg(IShinseiMaintenance.CHECK_OFF);}                              //チェックオフ
// Horikoshi End

// 20050712 キーワード情報の追加
                result.setKigou(recordSet.getString("KEYWORD_CD"));
                result.setKeyName(recordSet.getString("SAIMOKU_KEYWORD"));
                result.setKeyOtherName(recordSet.getString("OTHER_KEYWORD"));
// Horikoshi
                
//2006/02/10   syu
                result.setSaiyoDate(recordSet.getDate("SAIYO_DATE"));//採用年月日
                result.setKinmuHour(recordSet.getString("KINMU_HOUR")); //勤務時間数
                result.setNaiyakugaku(recordSet.getString("NAIYAKUGAKU"));//特別研究員奨励費内約額
//End
//2007/02/02 苗　追加ここから
                result.setShoreihiNoNendo(recordSet.getString("SHOREIHI_NO_NENDO"));//特別研究員奨励費課題番号-年度
                result.setShoreihiNoSeiri(recordSet.getString("SHOREIHI_NO_SEIRI"));//特別研究員奨励費課題番号-整理番号
//2007/02/02　苗　追加ここまで 
            
//2006/02/13 Start
                result.setOuboShikaku(recordSet.getString("OUBO_SHIKAKU")); //応募資格
                result.setSikakuDate(recordSet.getDate("SIKAKU_DATE"));//資格取得年月日
                result.setSyuTokumaeKikan(recordSet.getString("SYUTOKUMAE_KIKAN"));//資格取得前機関名
                result.setIkukyuStartDate(recordSet.getDate("IKUKYU_START_DATE"));//育休等開始日
                result.setIkukyuEndDate(recordSet.getDate("IKUKYU_END_DATE"));//育休等終了日     
//Nae End
    
//2006/02/16 Start
                result.setShinsaRyoikiName(recordSet.getString("SHINSARYOIKI_NAME"));//分野名
                result.setShinsaRyoikiCd(recordSet.getString("SHINSARYOIKI_CD"));//分野コード            
//syuu End
// ADD START 2007-07-26 BIS 王志安
                String naiyakugaku1 = recordSet.getString("NAIYAKUGAKU1");
                String naiyakugaku2 = recordSet.getString("NAIYAKUGAKU2");
                String naiyakugaku3 = recordSet.getString("NAIYAKUGAKU3");
                String naiyakugaku4 = recordSet.getString("NAIYAKUGAKU4");
                String naiyakugaku5 = recordSet.getString("NAIYAKUGAKU5");
                
                if (naiyakugaku1 != null && !"".equals(naiyakugaku1)
            		&& naiyakugaku2 != null && !"".equals(naiyakugaku2)
            		&& naiyakugaku3 != null && !"".equals(naiyakugaku3)
            		&& naiyakugaku4 != null && !"".equals(naiyakugaku4)
            		&& naiyakugaku5 != null && !"".equals(naiyakugaku5))
                {
                	result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[0].setNaiyaku(naiyakugaku1);
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[1].setNaiyaku(naiyakugaku2);
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[2].setNaiyaku(naiyakugaku3);
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[3].setNaiyaku(naiyakugaku4);
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[4].setNaiyaku(naiyakugaku5);
                    
	                long totle = Long.parseLong(recordSet.getString("NAIYAKUGAKU1"));
	                totle += Long.parseLong(recordSet.getString("NAIYAKUGAKU2"));
	                totle += Long.parseLong(recordSet.getString("NAIYAKUGAKU3"));
	                totle += Long.parseLong(recordSet.getString("NAIYAKUGAKU4"));
	                totle += Long.parseLong(recordSet.getString("NAIYAKUGAKU5"));
	                result.getKenkyuKeihiSoukeiInfo().setNaiyakuTotal(String.valueOf(totle));
                } else {
                	result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[0].setNaiyaku("");
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[1].setNaiyaku("");
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[2].setNaiyaku("");
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[3].setNaiyaku("");
                    result.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[4].setNaiyaku("");
                	result.getKenkyuKeihiSoukeiInfo().setNaiyakuTotal("");
                }
// ADD END 2007-07-26 BIS 王志安
                return result;

            } else {
                throw new NoDataFoundException(
                    "申請データ管理テーブルに該当するデータが見つかりません。検索キー：システム受付番号'"
                        + primaryKeys.getSystemNo()
                        + "'");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("申請データ管理テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

//add start dyh 2006/06/02
    /**
     * 申請情報を取得する。
     * 削除フラグが「0」の場合（削除されていない場合）のみ検索する。
     * 取得したレコードをロックする。（comitが行われるまで。）
     * @param connection            コネクション
     * @param searchInfo            検索情報
     * @param systemNos             主キー情報格納
     * @return                      所属機関情報
     * @throws DataAccessException  データ取得中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     * @throws ApplicationException
     */
    public Page selectShinseiDataInfoList(
            Connection connection,
            ShinseiSearchInfo searchInfo,
            String[] systemNos)
            throws DataAccessException , NoDataFoundException, ApplicationException {

        String select =
            "SELECT "
                + " A.SYSTEM_NO"                //システム受付番号
                + ",A.UKETUKE_NO"               //申請番号
                + ",A.JIGYO_ID"                 //事業ID
                + ",A.NENDO"                    //年度
                + ",A.KAISU"                    //回数
                + ",A.JIGYO_NAME"               //事業名
                + ",A.SHINSEISHA_ID"            //申請者ID
                + ",A.SAKUSEI_DATE"             //申請書作成日
                + ",A.SHONIN_DATE"              //所属機関承認日
// 2006/07/26 dyh add start 理由：DBで領域代表者確定日を追加
                + ",A.RYOIKI_KAKUTEI_DATE"      //領域代表者確定日
// 2006/07/26 dyh add end
                + ",A.JYURI_DATE"               //学振受理日
                + ",A.NAME_KANJI_SEI"           //申請者氏名（漢字等-姓）
                + ",A.NAME_KANJI_MEI"           //申請者氏名（漢字等-名）
                + ",A.NAME_KANA_SEI"            //申請者氏名（フリガナ-姓）
                + ",A.NAME_KANA_MEI"            //申請者氏名（フリガナ-名）
                + ",A.NAME_RO_SEI"              //申請者氏名（ローマ字-姓）
                + ",A.NAME_RO_MEI"              //申請者氏名（ローマ字-名）
                + ",A.NENREI"                   //年齢
                + ",A.KENKYU_NO"                //申請者研究者番号
                + ",A.SHOZOKU_CD"               //所属機関コード
                + ",A.SHOZOKU_NAME"             //所属機関名
                + ",A.SHOZOKU_NAME_RYAKU"       //所属機関名（略称）
                + ",A.BUKYOKU_CD"               //部局コード
                + ",A.BUKYOKU_NAME"             //部局名
                + ",A.BUKYOKU_NAME_RYAKU"       //部局名（略称）
                + ",A.SHOKUSHU_CD"              //職名コード
                + ",A.SHOKUSHU_NAME_KANJI"      //職名（和文）
                + ",A.SHOKUSHU_NAME_RYAKU"      //職名（略称）
                + ",A.ZIP"                      //郵便番号
                + ",A.ADDRESS"                  //住所
                + ",A.TEL"                      //TEL
                + ",A.FAX"                      //FAX
                + ",A.EMAIL"                    //E-Mail
//2006/06/30 苗　追加ここから
                + ",A.URL"                      //URL  
//2006/06/30　苗　追加ここまで    
                + ",A.SENMON"                   //現在の専門
                + ",A.GAKUI"                    //学位
                + ",A.BUNTAN"                   //役割分担
                + ",A.KADAI_NAME_KANJI"         //研究課題名(和文）
                + ",A.KADAI_NAME_EIGO"          //研究課題名(英文）
                + ",A.JIGYO_KUBUN"              //事業区分
                + ",A.SHINSA_KUBUN"             //審査区分
                + ",A.SHINSA_KUBUN_MEISHO"      //審査区分名称
                + ",A.BUNKATSU_NO"              //分割番号
                + ",A.BUNKATSU_NO_MEISHO"       //分割番号名称
                + ",A.KENKYU_TAISHO"            //研究対象の類型
                + ",A.KEI_NAME_NO"              //系等の区分番号
                + ",A.KEI_NAME"                 //系等の区分
                + ",A.KEI_NAME_RYAKU"           //系等の区分略称
                + ",A.BUNKASAIMOKU_CD"          //細目番号
                + ",A.BUNYA_NAME"               //分野
                + ",A.BUNKA_NAME"               //分科
                + ",A.SAIMOKU_NAME"             //細目
                + ",A.BUNKASAIMOKU_CD2"         //細目番号2
                + ",A.BUNYA_NAME2"              //分野2
                + ",A.BUNKA_NAME2"              //分科2
                + ",A.SAIMOKU_NAME2"            //細目2
                + ",A.KANTEN_NO"                //推薦の観点番号
                + ",A.KANTEN"                   //推薦の観点
                + ",A.KANTEN_RYAKU"             //推薦の観点略称
                + ",A.KAIGIHI"                  //会議費
                + ",A.INSATSUHI"                //印刷費
                + ",A.KEIHI1"                   //1年目研究経費
                + ",A.BIHINHI1"                 //1年目設備備品費
                + ",A.SHOMOHINHI1"              //1年目消耗品費
                + ",A.KOKUNAIRYOHI1"            //1年目国内旅費
                + ",A.GAIKOKURYOHI1"            //1年目外国旅費
                + ",A.RYOHI1"                   //1年目旅費
                + ",A.SHAKIN1"                  //1年目謝金等
                + ",A.SONOTA1"                  //1年目その他
                + ",A.KEIHI2"                   //2年目研究経費
                + ",A.BIHINHI2"                 //2年目設備備品費
                + ",A.SHOMOHINHI2"              //2年目消耗品費
                + ",A.KOKUNAIRYOHI2"            //2年目国内旅費
                + ",A.GAIKOKURYOHI2"            //2年目外国旅費
                + ",A.RYOHI2"                   //2年目旅費
                + ",A.SHAKIN2"                  //2年目謝金等
                + ",A.SONOTA2"                  //2年目その他
                + ",A.KEIHI3"                   //3年目研究経費
                + ",A.BIHINHI3"                 //3年目設備備品費
                + ",A.SHOMOHINHI3"              //3年目消耗品費
                + ",A.KOKUNAIRYOHI3"            //3年目国内旅費
                + ",A.GAIKOKURYOHI3"            //3年目外国旅費
                + ",A.RYOHI3"                   //3年目旅費
                + ",A.SHAKIN3"                  //3年目謝金等
                + ",A.SONOTA3"                  //3年目その他
                + ",A.KEIHI4"                   //4年目研究経費
                + ",A.BIHINHI4"                 //4年目設備備品費
                + ",A.SHOMOHINHI4"              //4年目消耗品費
                + ",A.KOKUNAIRYOHI4"            //4年目国内旅費
                + ",A.GAIKOKURYOHI4"            //4年目外国旅費
                + ",A.RYOHI4"                   //4年目旅費
                + ",A.SHAKIN4"                  //4年目謝金等
                + ",A.SONOTA4"                  //4年目その他
                + ",A.KEIHI5"                   //5年目研究経費
                + ",A.BIHINHI5"                 //5年目設備備品費
                + ",A.SHOMOHINHI5"              //5年目消耗品費
                + ",A.KOKUNAIRYOHI5"            //5年目国内旅費
                + ",A.GAIKOKURYOHI5"            //5年目外国旅費
                + ",A.RYOHI5"                   //5年目旅費
                + ",A.SHAKIN5"                  //5年目謝金等
                + ",A.SONOTA5"                  //5年目その他
                + ",A.KEIHI_TOTAL"              //総計-研究経費
                + ",A.BIHINHI_TOTAL"            //総計-設備備品費
                + ",A.SHOMOHINHI_TOTAL"         //総計-消耗品費
                + ",A.KOKUNAIRYOHI_TOTAL"       //総計-国内旅費
                + ",A.GAIKOKURYOHI_TOTAL"       //総計-外国旅費
                + ",A.RYOHI_TOTAL"              //総計-旅費
                + ",A.SHAKIN_TOTAL"             //総計-謝金等
                + ",A.SONOTA_TOTAL"             //総計-その他
                + ",A.SOSHIKI_KEITAI_NO"        //研究組織の形態番号
                + ",A.SOSHIKI_KEITAI"           //研究組織の形態
                + ",A.BUNTANKIN_FLG"            //分担金の有無
                + ",A.KOYOHI"                   //研究支援者雇用経費
                + ",A.KENKYU_NINZU"             //研究者数
                + ",A.TAKIKAN_NINZU"            //他機関の分担者数
                + ",A.KYORYOKUSHA_NINZU"        //他機関の分担者数
                + ",A.SHINSEI_KUBUN"            //新規継続区分
                + ",A.KADAI_NO_KEIZOKU"         //継続分の研究課題番号
                + ",A.SHINSEI_FLG_NO"           //継続分の研究課題番号
                + ",A.SHINSEI_FLG"              //申請の有無
                + ",A.KADAI_NO_SAISYU"          //最終年度課題番号
                + ",A.KAIJIKIBO_FLG_NO"         //開示希望の有無番号
                + ",A.KAIJIKIBO_FLG"            //開示希望の有無
                + ",A.KAIGAIBUNYA_CD"           //海外分野コード
                + ",A.KAIGAIBUNYA_NAME"         //海外分野名称
                + ",A.KAIGAIBUNYA_NAME_RYAKU"   //海外分野略称
                + ",A.KANREN_SHIMEI1"           //関連分野の研究者-氏名1
                + ",A.KANREN_KIKAN1"            //関連分野の研究者-所属機関1
                + ",A.KANREN_BUKYOKU1"          //関連分野の研究者-所属部局1
                + ",A.KANREN_SHOKU1"            //関連分野の研究者-職名1
                + ",A.KANREN_SENMON1"           //関連分野の研究者-専門分野1
                + ",A.KANREN_TEL1"              //関連分野の研究者-勤務先電話番号1
                + ",A.KANREN_JITAKUTEL1"        //関連分野の研究者-自宅電話番号1
                + ",A.KANREN_MAIL1"             //関連分野の研究者-E-mail1
                + ",A.KANREN_SHIMEI2"           //関連分野の研究者-氏名2
                + ",A.KANREN_KIKAN2"            //関連分野の研究者-所属機関2
                + ",A.KANREN_BUKYOKU2"          //関連分野の研究者-所属部局2
                + ",A.KANREN_SHOKU2"            //関連分野の研究者-職名2
                + ",A.KANREN_SENMON2"           //関連分野の研究者-専門分野2
                + ",A.KANREN_TEL2"              //関連分野の研究者-勤務先電話番号2
                + ",A.KANREN_JITAKUTEL2"        //関連分野の研究者-自宅電話番号2
                + ",A.KANREN_MAIL2"             //関連分野の研究者-E-mail2
                + ",A.KANREN_SHIMEI3"           //関連分野の研究者-氏名3
                + ",A.KANREN_KIKAN3"            //関連分野の研究者-所属機関3
                + ",A.KANREN_BUKYOKU3"          //関連分野の研究者-所属部局3
                + ",A.KANREN_SHOKU3"            //関連分野の研究者-職名3
                + ",A.KANREN_SENMON3"           //関連分野の研究者-専門分野3
                + ",A.KANREN_TEL3"              //関連分野の研究者-勤務先電話番号3
                + ",A.KANREN_JITAKUTEL3"        //関連分野の研究者-自宅電話番号3
                + ",A.KANREN_MAIL3"             //関連分野の研究者-E-mail3
                + ",A.XML_PATH"                 //XMLの格納パス
                + ",A.PDF_PATH"                 //PDFの格納パス
                + ",A.JURI_KEKKA"               //受理結果
                + ",A.JURI_BIKO"                //受理結果備考
                + ",A.JURI_SEIRI_NO"            //整理番号
                + ",A.SUISENSHO_PATH"           //推薦書の格納パス
                + ",A.KEKKA1_ABC"               //１次審査結果(ABC)
                + ",A.KEKKA1_TEN"               //１次審査結果(点数)
                + ",A.KEKKA1_TEN_SORTED"        //１次審査結果(点数順)
                + ",A.SHINSA1_BIKO"             //１次審査備考
                + ",A.KEKKA2"                   //２次審査結果
                + ",A.SOU_KEHI"                 //総経費（学振入力）
                + ",A.SHONEN_KEHI"              //初年度経費（学振入力）
                + ",A.SHINSA2_BIKO"             //業務担当者記入欄
                + ",A.JOKYO_ID"                 //申請状況ID
                + ",A.SAISHINSEI_FLG"           //再申請フラグ
                + ",A.EDITION"                  //版
                + ",A.KENKYU_KUBUN"             //研究区分
                + ",A.OHABAHENKO"               //大幅な変更
                + ",A.RYOIKI_NO"                //領域番号
                + ",A.RYOIKI_RYAKU"             //領域略称
                + ",A.KOMOKU_NO"                //項目番号
                + ",A.CHOSEIHAN"                //調整班
                + ",A.KEYWORD_CD"
                + ",A.SAIMOKU_KEYWORD"
                + ",A.OTHER_KEYWORD"
                + ",A.SAIYO_DATE"               //採用年月日
                + ",A.KINMU_HOUR"               //勤務時間数
                + ",A.NAIYAKUGAKU"              //特別研究員奨励費内約額
                + ",A.OUBO_SHIKAKU"             //応募資格
                + ",A.SIKAKU_DATE"              //資格取得年月日
                + ",A.SYUTOKUMAE_KIKAN"         //資格取得前機関名
                + ",A.IKUKYU_START_DATE"        //育休等開始日
                + ",A.IKUKYU_END_DATE"          //育休等終了日
                + ",A.SHINSARYOIKI_NAME"        //分野名
                + ",A.SHINSARYOIKI_CD"          //分野コード
                + ",SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD"        //事業コード
                + ",A.DEL_FLG"                  //削除フラグ
                + " FROM SHINSEIDATAKANRI" + dbLink + " A"
                + " WHERE SYSTEM_NO IN ("
                + StringUtil.changeArray2CSV(systemNos,true)
                + ")"
                ;
        
        //検索条件を元にSQL文を生成する。
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        try{
            // ページ取得
            return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
//add end dyh 2006/06/02

    /**
     * 申請情報を登録する。
     * @param connection                コネクション
     * @param addInfo                   登録する申請情報
     * @throws DataAccessException      登録中に例外が発生した場合。
     * @throws DuplicateKeyException    キーに一致するデータが既に存在する場合
     */
    public void insertShinseiDataInfo(
            Connection connection,
            ShinseiDataInfo addInfo)
            throws DataAccessException, DuplicateKeyException {

        //キー重複チェック
        try {
            selectShinseiDataInfo(connection, addInfo, true);
            //NG
            throw new DuplicateKeyException(
                "'" + addInfo + "'は既に登録されています。");
        } catch (NoDataFoundException e) {
            //OK
        }
        
        String query =
            "INSERT INTO SHINSEIDATAKANRI"+dbLink+" "
                + "("
                + "  SYSTEM_NO"                 //システム受付番号
                + " ,UKETUKE_NO"                //申請番号
                + " ,JIGYO_ID"                  //事業ID
                + " ,NENDO"                     //年度
                + " ,KAISU"                     //回数
                + " ,JIGYO_NAME"                //事業名
                + " ,SHINSEISHA_ID"             //申請者ID
                + " ,SAKUSEI_DATE"              //申請書作成日
                + " ,SHONIN_DATE"               //所属機関承認日
// 2006/07/26 dyh add start 理由：DBで領域代表者確定日を追加
                + " ,RYOIKI_KAKUTEI_DATE"       //領域代表者確定日
// 2006/07/26 dyh add end
                + " ,JYURI_DATE"                //学振受理日
                + " ,NAME_KANJI_SEI"            //申請者氏名（漢字等-姓）
                + " ,NAME_KANJI_MEI"            //申請者氏名（漢字等-名）
                + " ,NAME_KANA_SEI"             //申請者氏名（フリガナ-姓）
                + " ,NAME_KANA_MEI"             //申請者氏名（フリガナ-名）
                + " ,NAME_RO_SEI"               //申請者氏名（ローマ字-姓）
                + " ,NAME_RO_MEI"               //申請者氏名（ローマ字-名）
                + " ,NENREI"                    //年齢
                + " ,KENKYU_NO"                 //申請者研究者番号
                + " ,SHOZOKU_CD"                //所属機関コード
                + " ,SHOZOKU_NAME"              //所属機関名
                + " ,SHOZOKU_NAME_RYAKU"        //所属機関名（略称）
                + " ,BUKYOKU_CD"                //部局コード
                + " ,BUKYOKU_NAME"              //部局名
                + " ,BUKYOKU_NAME_RYAKU"        //部局名（略称）
                + " ,SHOKUSHU_CD"               //職名コード
                + " ,SHOKUSHU_NAME_KANJI"       //職名（和文）
                + " ,SHOKUSHU_NAME_RYAKU"       //職名（略称）
                + " ,ZIP"                       //郵便番号
                + " ,ADDRESS"                   //住所
                + " ,TEL"                       //TEL
                + " ,FAX"                       //FAX
                + " ,EMAIL"                     //E-Mail
//2006/06/30 苗　追加ここから
                + " ,URL"                       //URL  
//2006/06/30　苗　追加ここまで                    
                + " ,SENMON"                    //現在の専門
                + " ,GAKUI"                     //学位
                + " ,BUNTAN"                    //役割分担
                + " ,KADAI_NAME_KANJI"          //研究課題名(和文）
                + " ,KADAI_NAME_EIGO"           //研究課題名(英文）
                + " ,JIGYO_KUBUN"               //事業区分
                + " ,SHINSA_KUBUN"              //審査区分
                + " ,SHINSA_KUBUN_MEISHO"       //審査区分名称
                + " ,BUNKATSU_NO"               //分割番号
                + " ,BUNKATSU_NO_MEISHO"        //分割番号名称
                + " ,KENKYU_TAISHO"             //研究対象の類型
                + " ,KEI_NAME_NO"               //系等の区分番号
                + " ,KEI_NAME"                  //系等の区分
                + " ,KEI_NAME_RYAKU"            //系等の区分略称
                + " ,BUNKASAIMOKU_CD"           //細目番号
                + " ,BUNYA_NAME"                //分野
                + " ,BUNKA_NAME"                //分科
                + " ,SAIMOKU_NAME"              //細目
                + " ,BUNKASAIMOKU_CD2"          //細目番号2
                + " ,BUNYA_NAME2"               //分野2
                + " ,BUNKA_NAME2"               //分科2
                + " ,SAIMOKU_NAME2"             //細目2
                + " ,KANTEN_NO"                 //推薦の観点番号
                + " ,KANTEN"                    //推薦の観点
                + " ,KANTEN_RYAKU"              //推薦の観点略称               
//              2005/04/13 追加 ここから----------
//              理由:版追加のため

                + " ,EDITION"                   //版
                
//              2005/04/13 追加 ここまで----------
                + " ,KEIHI1"                    //1年目研究経費
                + " ,BIHINHI1"                  //1年目設備備品費
                + " ,SHOMOHINHI1"               //1年目消耗品費
                + " ,KOKUNAIRYOHI1"             //1年目国内旅費
                + " ,GAIKOKURYOHI1"             //1年目外国旅費
                + " ,RYOHI1"                    //1年目旅費
                + " ,SHAKIN1"                   //1年目謝金等
                + " ,SONOTA1"                   //1年目その他
                + " ,KEIHI2"                    //2年目研究経費
                + " ,BIHINHI2"                  //2年目設備備品費
                + " ,SHOMOHINHI2"               //2年目消耗品費
                + " ,KOKUNAIRYOHI2"             //2年目国内旅費
                + " ,GAIKOKURYOHI2"             //2年目外国旅費
                + " ,RYOHI2"                    //2年目旅費
                + " ,SHAKIN2"                   //2年目謝金等
                + " ,SONOTA2"                   //2年目その他
                + " ,KEIHI3"                    //3年目研究経費
                + " ,BIHINHI3"                  //3年目設備備品費
                + " ,SHOMOHINHI3"               //3年目消耗品費
                + " ,KOKUNAIRYOHI3"             //3年目国内旅費
                + " ,GAIKOKURYOHI3"             //3年目外国旅費
                + " ,RYOHI3"                    //3年目旅費
                + " ,SHAKIN3"                   //3年目謝金等
                + " ,SONOTA3"                   //3年目その他
                + " ,KEIHI4"                    //4年目研究経費
                + " ,BIHINHI4"                  //4年目設備備品費
                + " ,SHOMOHINHI4"               //4年目消耗品費
                + " ,KOKUNAIRYOHI4"             //4年目国内旅費
                + " ,GAIKOKURYOHI4"             //4年目外国旅費
                + " ,RYOHI4"                    //4年目旅費
                + " ,SHAKIN4"                   //4年目謝金等
                + " ,SONOTA4"                   //4年目その他
                + " ,KEIHI5"                    //5年目研究経費
                + " ,BIHINHI5"                  //5年目設備備品費
                + " ,SHOMOHINHI5"               //5年目消耗品費
                + " ,KOKUNAIRYOHI5"             //5年目国内旅費
                + " ,GAIKOKURYOHI5"             //5年目外国旅費
                + " ,RYOHI5"                    //5年目旅費
                + " ,SHAKIN5"                   //5年目謝金等
                + " ,SONOTA5"                   //5年目その他
//2006/06/15 苗　追加ここから  6年目の研究経費を追加する
                + " ,KEIHI6"                    //6年目研究経費
                + " ,BIHINHI6"                  //6年目設備備品費
                + " ,SHOMOHINHI6"               //6年目消耗品費
                + " ,KOKUNAIRYOHI6"             //6年目国内旅費
                + " ,GAIKOKURYOHI6"             //6年目外国旅費
                + " ,RYOHI6"                    //6年目旅費
                + " ,SHAKIN6"                   //6年目謝金等
                + " ,SONOTA6"                   //6年目その他
//2006/06/15 苗　追加ここまで                
                + " ,KEIHI_TOTAL"               //総計-研究経費
                + " ,BIHINHI_TOTAL"             //総計-設備備品費
                + " ,SHOMOHINHI_TOTAL"          //総計-消耗品費
                + " ,KOKUNAIRYOHI_TOTAL"        //総計-国内旅費
                + " ,GAIKOKURYOHI_TOTAL"        //総計-外国旅費
                + " ,RYOHI_TOTAL"               //総計-旅費
                + " ,SHAKIN_TOTAL"              //総計-謝金等
                + " ,SONOTA_TOTAL"              //総計-その他
                + " ,SOSHIKI_KEITAI_NO"         //研究組織の形態番号
                + " ,SOSHIKI_KEITAI"            //研究組織の形態
                + " ,BUNTANKIN_FLG"             //分担金の有無
                + " ,KOYOHI"                    //研究支援者雇用経費
                + " ,KENKYU_NINZU"              //研究者数
                + " ,TAKIKAN_NINZU"             //他機関の分担者数
                //2005/03/31 追加 ---------------------------------------ここから
                //理由 研究協力者の入力欄が追加されたため
                + " ,KYORYOKUSHA_NINZU"         //研究協力者数
                //2005/03/31 追加 ---------------------------------------ここまで
                + " ,SHINSEI_KUBUN"             //新規継続区分
                + " ,KADAI_NO_KEIZOKU"          //継続分の研究課題番号
                + " ,SHINSEI_FLG_NO"            //研究計画最終年度前年度の応募
                + " ,SHINSEI_FLG"               //申請の有無
                + " ,KADAI_NO_SAISYU"           //最終年度課題番号
                + " ,KAIJIKIBO_FLG_NO"          //開示希望の有無番号
                + " ,KAIJIKIBO_FLG"             //開示希望の有無
                + " ,KAIGAIBUNYA_CD"            //海外分野コード
                + " ,KAIGAIBUNYA_NAME"          //海外分野名称
                + " ,KAIGAIBUNYA_NAME_RYAKU"    //海外分野略称
                + " ,KANREN_SHIMEI1"            //関連分野の研究者-氏名1
                + " ,KANREN_KIKAN1"             //関連分野の研究者-所属機関1
                + " ,KANREN_BUKYOKU1"           //関連分野の研究者-所属部局1
                + " ,KANREN_SHOKU1"             //関連分野の研究者-職名1
                + " ,KANREN_SENMON1"            //関連分野の研究者-専門分野1
                + " ,KANREN_TEL1"               //関連分野の研究者-勤務先電話番号1
                + " ,KANREN_JITAKUTEL1"         //関連分野の研究者-自宅電話番号1
                + " ,KANREN_MAIL1"              //関連分野の研究者-E-mail1
                + " ,KANREN_SHIMEI2"            //関連分野の研究者-氏名2
                + " ,KANREN_KIKAN2"             //関連分野の研究者-所属機関2
                + " ,KANREN_BUKYOKU2"           //関連分野の研究者-所属部局2
                + " ,KANREN_SHOKU2"             //関連分野の研究者-職名2
                + " ,KANREN_SENMON2"            //関連分野の研究者-専門分野2
                + " ,KANREN_TEL2"               //関連分野の研究者-勤務先電話番号2
                + " ,KANREN_JITAKUTEL2"         //関連分野の研究者-自宅電話番号2
                + " ,KANREN_MAIL2"              //関連分野の研究者-E-mail2
                + " ,KANREN_SHIMEI3"            //関連分野の研究者-氏名3
                + " ,KANREN_KIKAN3"             //関連分野の研究者-所属機関3
                + " ,KANREN_BUKYOKU3"           //関連分野の研究者-所属部局3
                + " ,KANREN_SHOKU3"             //関連分野の研究者-職名3
                + " ,KANREN_SENMON3"            //関連分野の研究者-専門分野3
                + " ,KANREN_TEL3"               //関連分野の研究者-勤務先電話番号3
                + " ,KANREN_JITAKUTEL3"         //関連分野の研究者-自宅電話番号3
                + " ,KANREN_MAIL3"              //関連分野の研究者-E-mail3
                + " ,XML_PATH"                  //XMLの格納パス
                + " ,PDF_PATH"                  //PDFの格納パス
                + " ,JURI_KEKKA"                //受理結果
                + " ,JURI_BIKO"                 //受理結果備考
//2005/07/25 受理番号
                + " ,JURI_SEIRI_NO"             //受理番号
                + " ,SUISENSHO_PATH"            //推薦書の格納パス
                + " ,KEKKA1_ABC"                //１次審査結果(ABC)
                + " ,KEKKA1_TEN"                //１次審査結果(点数)
                + " ,KEKKA1_TEN_SORTED"         //１次審査結果(点数順)
                + " ,SHINSA1_BIKO"              //１次審査備考
                + " ,KEKKA2"                    //２次審査結果
                + " ,SOU_KEHI"                  //総経費（学振入力）
                + " ,SHONEN_KEHI"               //初年度経費（学振入力）
                + " ,SHINSA2_BIKO"              //業務担当者記入欄
                + " ,JOKYO_ID"                  //申請状況ID
                + " ,SAISHINSEI_FLG"            //再申請フラグ
                + " ,DEL_FLG"                   //削除フラグ
// 20050530 Start
                + ", KENKYU_KUBUN"              //研究区分
                + ", OHABAHENKO"                //大幅な変更フラグ
                + ", RYOIKI_NO"                 //領域番号
                + ", RYOIKI_RYAKU"              //領域略称
                + ", KOMOKU_NO"                 //項目番号
                + ", CHOSEIHAN"                 //調整班フラグ
// Horikoshi End
                
// 20050712 キーワード情報の追加
                + ", KEYWORD_CD"                //記号
                + ", SAIMOKU_KEYWORD"           //細目表キーワード
                + ", OTHER_KEYWORD "            //細目表以外のキーワード
// Horikoshi

// 20050803
                + " ,KAIGIHI"                   //会議費
                + " ,INSATSUHI"                 //印刷費
// Horikoshi
//2006/02/09
                + " ,SAIYO_DATE"                //採用年月日
                + " ,KINMU_HOUR"                //勤務時間数
                + " ,NAIYAKUGAKU"               //特別研究員奨励費内約額
//End
//2007/02/02 苗　追加ここから
                + " ,SHOREIHI_NO_NENDO"               //特別研究員奨励費課題番号-年度
                + " ,SHOREIHI_NO_SEIRI"         //特別研究員奨励費課題番号-整理番号 
//2007/02/02　苗　追加ここまで                
//2006/02/13 Start
                + " ,OUBO_SHIKAKU"              //応募資格
                + " ,SIKAKU_DATE"               //資格取得年月日
                + " ,SYUTOKUMAE_KIKAN"          //資格取得前機関名
                + " ,IKUKYU_START_DATE"         //育休等開始日
                + " ,IKUKYU_END_DATE"           //育休等終了日
//  Nae End 
//2006/02/15
                + " ,SHINSARYOIKI_CD"           //審査領域コード
                + " ,SHINSARYOIKI_NAME"         //審査領域名称
// syuu End         
// ADD START 2007-07-26 BIS 王志安
                + " ,NAIYAKUGAKU1"				//1年目内約額
                + " ,NAIYAKUGAKU2"				//2年目内約額
                + " ,NAIYAKUGAKU3"				//3年目内約額
                + " ,NAIYAKUGAKU4"				//4年目内約額
                + " ,NAIYAKUGAKU5"				//5年目内約額
// ADD END 2007-07-26 BIS 王志安
                + ") "
                + "VALUES "
                + "("
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //25個
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //50個
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //75個
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //100個
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //125個
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"  //150個
// 20050530 Start 特定領域の追加項目分を追加
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"               //169個
// Horikoshi End

// 20050803 会議費と印刷費を追加
                + ",?,?"                                                //171個
// Horikoshi

// 20050712 キーワード情報の追加
                + ",?,?,?,?"                                            //175個
// Horikoshi
//2006/02/09
                + ",?,?,?"                                              //178個
//End
//2006/02/13 Start  
                + ",?,?,?,?,?"                                          //183個
//Nae End   
                + ",?,?"                                                //185個
//2006/06/30 苗　追加ここから　6年目の研究経費を追加する
                + ",?,?,?,?,?,?,?,?,?,?"                                //195個
//2006/06/30　苗　追加ここまで
//2007/02/02 苗　追加ここから　特別研究員奨励費課題番号を追加する
                + ",?,?"                                                  //196個
//2007/02/02　苗　追加ここまで     
// ADD START 2007-07-26 BIS 王志安
                + ",?,?,?,?,?"
// ADD END 2007-07-26 BIS 王志安
                + ")";
        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            this.setAllParameter(preparedStatement, addInfo);           
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            log.error("申請情報登録中に例外が発生しました。 ", ex);
            //TODO プライマリーキー重複エラー調査のため、一時的に追加
            log.info(addInfo);
            throw new DataAccessException("申請情報登録中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 申請情報を更新する。
     * @param connection                コネクション
     * @param updateInfo                更新する申請情報
     * @param chekcFlg                  参照可能チェックフラグ
     * @throws DataAccessException      更新中に例外が発生した場合
     * @throws NoDataFoundException     対象データが見つからない場合
     */
    public void updateShinseiDataInfo(
            Connection connection,
            ShinseiDataInfo updateInfo,
            boolean chekcFlg)
            throws DataAccessException, NoDataFoundException {
        
        if(chekcFlg){
            //参照可能申請データかチェック
            checkOwnShinseiData(connection, updateInfo);
        }

        String query =
            //処理効率化のためテーブルのカラム順とは変更している
            "UPDATE SHINSEIDATAKANRI"+dbLink    
                + " SET"
                + "  SYSTEM_NO = ?"                 //システム受付番号
                + " ,UKETUKE_NO = ?"                //申請番号
                + " ,JIGYO_ID = ?"                  //事業ID
                + " ,NENDO = ?"                     //年度
                + " ,KAISU = ?"                     //回数
                + " ,JIGYO_NAME = ?"                //事業名
                + " ,SHINSEISHA_ID = ?"             //申請者ID
                + " ,SAKUSEI_DATE = ?"              //申請書作成日
                + " ,SHONIN_DATE = ?"               //所属機関承認日
// 2006/07/26 dyh add start 理由：DBで領域代表者確定日を追加
                + " ,RYOIKI_KAKUTEI_DATE = ?"       //領域代表者確定日
// 2006/07/26 dyh add end
                + " ,JYURI_DATE = ?"                //学振受理日
                + " ,NAME_KANJI_SEI = ?"            //申請者氏名（漢字等-姓）
                + " ,NAME_KANJI_MEI = ?"            //申請者氏名（漢字等-名）
                + " ,NAME_KANA_SEI = ?"             //申請者氏名（フリガナ-姓）
                + " ,NAME_KANA_MEI = ?"             //申請者氏名（フリガナ-名）
                + " ,NAME_RO_SEI = ?"               //申請者氏名（ローマ字-姓）
                + " ,NAME_RO_MEI = ?"               //申請者氏名（ローマ字-名）
                + " ,NENREI = ?"                    //年齢
                + " ,KENKYU_NO = ?"                 //申請者研究者番号
                + " ,SHOZOKU_CD = ?"                //所属機関コード
                + " ,SHOZOKU_NAME = ?"              //所属機関名
                + " ,SHOZOKU_NAME_RYAKU = ?"        //所属機関名（略称）
                + " ,BUKYOKU_CD = ?"                //部局コード
                + " ,BUKYOKU_NAME = ?"              //部局名
                + " ,BUKYOKU_NAME_RYAKU = ?"        //部局名（略称）
                + " ,SHOKUSHU_CD = ?"               //職名コード
                + " ,SHOKUSHU_NAME_KANJI = ?"       //職名（和文）
                + " ,SHOKUSHU_NAME_RYAKU = ?"       //職名（略称）
                + " ,ZIP = ?"                       //郵便番号
                + " ,ADDRESS = ?"                   //住所
                + " ,TEL = ?"                       //TEL
                + " ,FAX = ?"                       //FAX
                + " ,EMAIL = ?"                     //E-Mail
//2006/06/30 苗　追加ここから
                + " ,URL = ?"                       //URL
//2006/06/30　苗　追加ここまで                
                + " ,SENMON = ?"                    //現在の専門
                + " ,GAKUI = ?"                     //学位
                + " ,BUNTAN = ?"                    //役割分担
                + " ,KADAI_NAME_KANJI = ?"          //研究課題名(和文）
                + " ,KADAI_NAME_EIGO = ?"           //研究課題名(英文）
                + " ,JIGYO_KUBUN = ?"               //事業区分
                + " ,SHINSA_KUBUN = ?"              //審査区分
                + " ,SHINSA_KUBUN_MEISHO = ?"       //審査区分名称
                + " ,BUNKATSU_NO = ?"               //分割番号
                + " ,BUNKATSU_NO_MEISHO = ?"        //分割番号名称
                + " ,KENKYU_TAISHO = ?"             //研究対象の類型
                + " ,KEI_NAME_NO = ?"               //系等の区分番号
                + " ,KEI_NAME = ?"                  //系等の区分
                + " ,KEI_NAME_RYAKU = ?"            //系等の区分略称
                + " ,BUNKASAIMOKU_CD = ?"           //細目番号
                + " ,BUNYA_NAME = ?"                //分野
                + " ,BUNKA_NAME = ?"                //分科
                + " ,SAIMOKU_NAME = ?"              //細目
                + " ,BUNKASAIMOKU_CD2 = ?"          //細目番号2
                + " ,BUNYA_NAME2 = ?"               //分野2
                + " ,BUNKA_NAME2 = ?"               //分科2
                + " ,SAIMOKU_NAME2 = ?"             //細目2
                + " ,KANTEN_NO = ?"                 //推薦の観点番号
                + " ,KANTEN = ?"                    //推薦の観点
                + " ,KANTEN_RYAKU = ?"              //推薦の観点略称
//              2005/04/13 追加 ここから----------
//              理由:分割番号追加のため

                + " ,EDITION = ?"                   //版
                
//              2005/04/13 追加 ここまで----------
                + " ,KEIHI1 = ?"                    //1年目研究経費
                + " ,BIHINHI1 = ?"                  //1年目設備備品費
                + " ,SHOMOHINHI1 = ?"               //1年目消耗品費
                + " ,KOKUNAIRYOHI1 = ?"             //1年目国内旅費
                + " ,GAIKOKURYOHI1 = ?"             //1年目外国旅費
                + " ,RYOHI1 = ?"                    //1年目旅費
                + " ,SHAKIN1 = ?"                   //1年目謝金等
                + " ,SONOTA1 = ?"                   //1年目その他
                + " ,KEIHI2 = ?"                    //2年目研究経費
                + " ,BIHINHI2 = ?"                  //2年目設備備品費
                + " ,SHOMOHINHI2 = ?"               //2年目消耗品費
                + " ,KOKUNAIRYOHI2 = ?"             //2年目国内旅費
                + " ,GAIKOKURYOHI2 = ?"             //2年目外国旅費
                + " ,RYOHI2 = ?"                    //2年目旅費
                + " ,SHAKIN2 = ?"                   //2年目謝金等
                + " ,SONOTA2 = ?"                   //2年目その他
                + " ,KEIHI3 = ?"                    //3年目研究経費
                + " ,BIHINHI3 = ?"                  //3年目設備備品費
                + " ,SHOMOHINHI3 = ?"               //3年目消耗品費
                + " ,KOKUNAIRYOHI3 = ?"             //3年目国内旅費
                + " ,GAIKOKURYOHI3 = ?"             //3年目外国旅費
                + " ,RYOHI3 = ?"                    //3年目旅費
                + " ,SHAKIN3 = ?"                   //3年目謝金等
                + " ,SONOTA3 = ?"                   //3年目その他
                + " ,KEIHI4 = ?"                    //4年目研究経費
                + " ,BIHINHI4 = ?"                  //4年目設備備品費
                + " ,SHOMOHINHI4 = ?"               //4年目消耗品費
                + " ,KOKUNAIRYOHI4 = ?"             //4年目国内旅費
                + " ,GAIKOKURYOHI4 = ?"             //4年目外国旅費
                + " ,RYOHI4 = ?"                    //4年目旅費
                + " ,SHAKIN4 = ?"                   //4年目謝金等
                + " ,SONOTA4 = ?"                   //4年目その他
                + " ,KEIHI5 = ?"                    //5年目研究経費
                + " ,BIHINHI5 = ?"                  //5年目設備備品費
                + " ,SHOMOHINHI5 = ?"               //5年目消耗品費
                + " ,KOKUNAIRYOHI5 = ?"             //5年目国内旅費
                + " ,GAIKOKURYOHI5 = ?"             //5年目外国旅費
                + " ,RYOHI5 = ?"                    //5年目旅費
                + " ,SHAKIN5 = ?"                   //5年目謝金等
                + " ,SONOTA5 = ?"                   //5年目その他
//2006/06/15 苗　追加ここから  6年目の研究経費を追加する
                + " ,KEIHI6 = ?"                    //6年目研究経費
                + " ,BIHINHI6 = ?"                  //6年目設備備品費
                + " ,SHOMOHINHI6 = ?"               //6年目消耗品費
                + " ,KOKUNAIRYOHI6 = ?"             //6年目国内旅費
                + " ,GAIKOKURYOHI6 = ?"             //6年目外国旅費
                + " ,RYOHI6 = ?"                    //6年目旅費
                + " ,SHAKIN6 = ?"                   //6年目謝金等
                + " ,SONOTA6 = ?"                   //6年目その他
//2006/06/15 苗　追加ここまで                     
                + " ,KEIHI_TOTAL = ?"               //総計-研究経費
                + " ,BIHINHI_TOTAL = ?"             //総計-設備備品費
                + " ,SHOMOHINHI_TOTAL = ?"          //総計-消耗品費
                + " ,KOKUNAIRYOHI_TOTAL = ?"        //総計-国内旅費
                + " ,GAIKOKURYOHI_TOTAL = ?"        //総計-外国旅費
                + " ,RYOHI_TOTAL = ?"               //総計-旅費
                + " ,SHAKIN_TOTAL = ?"              //総計-謝金等
                + " ,SONOTA_TOTAL = ?"              //総計-その他
                + " ,SOSHIKI_KEITAI_NO = ?"         //研究組織の形態番号
                + " ,SOSHIKI_KEITAI = ?"            //研究組織の形態
                + " ,BUNTANKIN_FLG = ?"             //分担金の有無
                + " ,KOYOHI = ?"                    //研究支援者雇用経費
                + " ,KENKYU_NINZU = ?"              //研究者数
                + " ,TAKIKAN_NINZU = ?"             //他機関の分担者数
                //2005/03/31 追加 ---------------------------------------ここから
                //理由 研究協力者入力欄が追加されたため
                + " ,KYORYOKUSHA_NINZU = ?"         //研究協力者数
                //2005/03/31 追加 ---------------------------------------ここまで
                + " ,SHINSEI_KUBUN = ?"             //新規継続区分
                + " ,KADAI_NO_KEIZOKU = ?"          //継続分の研究課題番号
                + " ,SHINSEI_FLG_NO = ?"            //研究計画最終年度前年度の応募
                + " ,SHINSEI_FLG = ?"               //申請の有無
                + " ,KADAI_NO_SAISYU = ?"           //最終年度課題番号
                + " ,KAIJIKIBO_FLG_NO = ?"          //開示希望の有無番号
                + " ,KAIJIKIBO_FLG = ?"             //開示希望の有無
                + " ,KAIGAIBUNYA_CD = ?"            //海外分野コード
                + " ,KAIGAIBUNYA_NAME = ?"          //海外分野名称
                + " ,KAIGAIBUNYA_NAME_RYAKU = ?"    //海外分野略称
                + " ,KANREN_SHIMEI1 = ?"            //関連分野の研究者-氏名1
                + " ,KANREN_KIKAN1 = ?"             //関連分野の研究者-所属機関1
                + " ,KANREN_BUKYOKU1 = ?"           //関連分野の研究者-所属部局1
                + " ,KANREN_SHOKU1 = ?"             //関連分野の研究者-職名1
                + " ,KANREN_SENMON1 = ?"            //関連分野の研究者-専門分野1
                + " ,KANREN_TEL1 = ?"               //関連分野の研究者-勤務先電話番号1
                + " ,KANREN_JITAKUTEL1 = ?"         //関連分野の研究者-自宅電話番号1              
                + " ,KANREN_MAIL1 = ?"              //関連分野の研究者-E-mail1
                + " ,KANREN_SHIMEI2 = ?"            //関連分野の研究者-氏名2
                + " ,KANREN_KIKAN2 = ?"             //関連分野の研究者-所属機関2
                + " ,KANREN_BUKYOKU2 = ?"           //関連分野の研究者-所属部局2
                + " ,KANREN_SHOKU2 = ?"             //関連分野の研究者-職名2
                + " ,KANREN_SENMON2 = ?"            //関連分野の研究者-専門分野2
                + " ,KANREN_TEL2 = ?"               //関連分野の研究者-勤務先電話番号2
                + " ,KANREN_JITAKUTEL2 = ?"         //関連分野の研究者-自宅電話番号2              
                + " ,KANREN_MAIL2 = ?"              //関連分野の研究者-E-mail2
                + " ,KANREN_SHIMEI3 = ?"            //関連分野の研究者-氏名3
                + " ,KANREN_KIKAN3 = ?"             //関連分野の研究者-所属機関3
                + " ,KANREN_BUKYOKU3 = ?"           //関連分野の研究者-所属部局3
                + " ,KANREN_SHOKU3 = ?"             //関連分野の研究者-職名3
                + " ,KANREN_SENMON3 = ?"            //関連分野の研究者-専門分野3
                + " ,KANREN_TEL3 = ?"               //関連分野の研究者-勤務先電話番号3
                + " ,KANREN_JITAKUTEL3 = ?"         //関連分野の研究者-自宅電話番号3              
                + " ,KANREN_MAIL3 = ?"              //関連分野の研究者-E-mail3
                + " ,XML_PATH = ?"                  //XMLの格納パス
                + " ,PDF_PATH = ?"                  //PDFの格納パス
                + " ,JURI_KEKKA = ?"                //受理結果
                + " ,JURI_BIKO = ?"                 //受理結果備考
                //整理番号追加
                + " ,JURI_SEIRI_NO = ?"             //受理整理番号
                + " ,SUISENSHO_PATH = ?"            //推薦書の格納パス
                + " ,KEKKA1_ABC = ?"                //１次審査結果(ABC)
                + " ,KEKKA1_TEN = ?"                //１次審査結果(点数)
                + " ,KEKKA1_TEN_SORTED = ?"         //１次審査結果(点数順)
                + " ,SHINSA1_BIKO = ?"              //１次審査備考
                + " ,KEKKA2 = ?"                    //２次審査結果
                + " ,SOU_KEHI = ?"                  //総経費（学振入力）
                + " ,SHONEN_KEHI = ?"               //初年度経費（学振入力）
                + " ,SHINSA2_BIKO = ?"              //業務担当者記入欄
                + " ,JOKYO_ID = ?"                  //申請状況ID
                + " ,SAISHINSEI_FLG = ?"            //再申請フラグ
                + " ,DEL_FLG = ?"                   //削除フラグ
// 20050530 Start
                + " ,KENKYU_KUBUN = ?"              //研究区分
                + " ,OHABAHENKO = ?"                //大幅な変更フラグ
                + " ,RYOIKI_NO = ?"                 //領域番号
                + " ,RYOIKI_RYAKU = ?"              //領域略称
                + " ,KOMOKU_NO = ?"                 //項目番号
                + " ,CHOSEIHAN = ?"                 //調整班フラグ
// Horikoshi End

// 20050712 キーワード情報追加のため
                + ", KEYWORD_CD = ?"                //記号
                + ", SAIMOKU_KEYWORD = ?"           //細目表キーワード
                + ", OTHER_KEYWORD = ?"             //細目表以外のキーワード
// Horikoshi

// 20050803 会議費、印刷費　追加のため
                + " ,KAIGIHI = ?"                   //会議費
                + " ,INSATSUHI = ?"                 //印刷費
// Horikoshi
                
//2006/02/09
                + " ,SAIYO_DATE = ?"                //採用年月日
                + " ,KINMU_HOUR = ?"                //勤務時間数
                + " ,NAIYAKUGAKU = ?"               //特別研究員奨励費内約額
//2007/02/02 苗　追加ここから
                + " ,SHOREIHI_NO_NENDO = ?"         //特別研究員奨励費課題番号-年度
                + " ,SHOREIHI_NO_SEIRI = ?"         //特別研究員奨励費課題番号-整理番号
//2007/02/02　苗　追加ここまで                
//2006/02/13 Start
                + " ,OUBO_SHIKAKU = ?"              //応募資格
                + " ,SIKAKU_DATE = ?"               //資格取得年月日
                + " ,SYUTOKUMAE_KIKAN = ?"          //資格取得前機関名
                + " ,IKUKYU_START_DATE = ?"         //育休等開始日
                + " ,IKUKYU_END_DATE = ?"           //育休等終了日
//Nae End
                + " ,SHINSARYOIKI_CD = ?"           //審査領域コード
                + " ,SHINSARYOIKI_NAME = ?"         //審査領域名称
                
// ADD START 2007-07-26 BIS 王志安
                + " ,NAIYAKUGAKU1 = ?"				//1年目内約額
                + " ,NAIYAKUGAKU2 = ?"				//2年目内約額
                + " ,NAIYAKUGAKU3 = ?"				//3年目内約額
                + " ,NAIYAKUGAKU4 = ?"				//4年目内約額
                + " ,NAIYAKUGAKU5 = ?"				//5年目内約額
// ADD END 2007-07-26 BIS 王志安

                + " WHERE"
                + " SYSTEM_NO = ?";                 //システム受付番号
        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = this.setAllParameter(preparedStatement, updateInfo);
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSystemNo()); //システム受付番号                  
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            log.error("申請情報更新中に例外が発生しました。 ", ex);
            throw new DataAccessException("申請情報更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 申請状況IDのみを指定されたステータスへ更新する。
     * @param connection
     * @param pkInfo
     * @param status
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateStatus(
            Connection connection,
            ShinseiDataPk pkInfo,
            String status)
            throws DataAccessException, NoDataFoundException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " JOKYO_ID = ?"           //申請状況ID                
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,status);
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //システム受付番号
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報ステータス更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * マスタ取り込み用申請データ更新メソッド。
     * 申請状況IDのステータスへと上書き情報を更新する。
     * @param connection
     * @param updateInfo
     * @param status
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateTorikomiShinseiDataInfo(
            Connection connection,
            ShinseiDataInfo updateInfo,
            String status)
            throws DataAccessException, NoDataFoundException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, updateInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " JOKYO_ID = ?"                   //申請状況ID
                + " ,KADAI_NAME_KANJI = ?"          //研究課題名(和文）
                + " ,NAME_KANJI_SEI = ?"            //申請者氏名（漢字等-姓）
                + " ,NAME_KANJI_MEI = ?"            //申請者氏名（漢字等-名）
                + " ,BUKYOKU_CD = ?"                //部局コード
                + " ,SHOKUSHU_CD = ?"               //職名コード
                + " ,KAIGAIBUNYA_CD = ?"            //海外分野コード
                + " ,SHINSEI_FLG_NO = ?"            //研究計画最終年度前年度の応募
                + " ,BUNTANKIN_FLG = ?"             //分担金の有無
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++, status);
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getKadaiInfo().getKadaiNameKanji());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getDaihyouInfo().getNameKanjiSei());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getDaihyouInfo().getNameKanjiMei());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getDaihyouInfo().getBukyokuCd());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getDaihyouInfo().getShokushuCd());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getKaigaibunyaCd());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getShinseiFlgNo());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getBuntankinFlg());
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSystemNo()); //システム受付番号
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報ステータス更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 申請状況IDと再申請フラグを指定された値へ更新する。    
     * @param connection
     * @param pkInfo
     * @param status
     * @param saishinseiFlg
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateStatus(
            Connection connection,
            ShinseiDataPk pkInfo,
            String status,
            String saishinseiFlg)
            throws DataAccessException, NoDataFoundException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " JOKYO_ID = ?"           //申請状況ID                
                + ",SAISHINSEI_FLG = ?"     //再申請フラグ
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,status);
            DatabaseUtil.setParameter(preparedStatement,index++,saishinseiFlg);
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //システム受付番号
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報ステータス更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }   

    /**
     * 申請情報を削除する。(削除フラグ) 
     * @param connection            コネクション
     * @param pkInfo                削除する申請データ主キー情報
     * @throws DataAccessException  削除中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public void deleteFlagShinseiDataInfo(
            Connection connection,
            ShinseiDataPk pkInfo)
            throws DataAccessException, NoDataFoundException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " DEL_FLG = 1"    //削除フラグ             
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //システム受付番号
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報削除中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 申請情報を削除する。(削除フラグ) 
     * 事業IDに紐づく申請データ全てに対して削除フラグを立てる。
     * @param connection            コネクション
     * @param pkInfo                削除する事業データ主キー情報
     * @throws DataAccessException  削除中に例外が発生した場合
     */
    public void deleteFlagShinseiDataInfo(
            Connection connection,
            JigyoKanriPk pkInfo)
            throws DataAccessException {

        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " DEL_FLG = 1"    //削除フラグ             
                + " WHERE"
                + " JIGYO_ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getJigyoId());   //事業ID
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報削除中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 申請情報を削除する。(物理削除) 
     * 基本的に使用しない。
     * @param connection            コネクション
     * @param pkInfo                削除する申請データ主キー情報
     * @throws DataAccessException  削除中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public void deleteShinseiDataInfo(
            Connection connection,
            ShinseiDataPk pkInfo)
            throws DataAccessException, NoDataFoundException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "DELETE FROM SHINSEIDATAKANRI"+dbLink
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //システム受付番号
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報削除中（物理削除）に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 申請情報を削除する。(物理削除) 
     * 事業IDに紐づく申請データを全て削除する。
     * 該当データが存在しなかった場合は何も処理しない。
     * @param connection            コネクション
     * @param jigyoId               検索条件（事業ID）
     * @throws DataAccessException  削除中に例外が発生した場合
     */
    public void deleteShinseiDataInfo(
            Connection connection,
            String jigyoId)
            throws DataAccessException {

        String query =
            "DELETE FROM SHINSEIDATAKANRI"+dbLink
                + " WHERE"
                + " JIGYO_ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,jigyoId);   //検索条件（事業ID）
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報削除中（物理削除）に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }   

//2005/08/16 takano 重複チェックのロジック最適化により削除 ここから ---
//★今回はコメントアウトにしましたが、最終的にはソース上から削除しておいた方が誤解が無くて良いと思います。
//  /**
//   * 重複申請レコード数を返す。
//   * 現時点（2004/08/04）で未実装の状態。
//   * 
//   * @param connection
//   * @param shinseiDataInfo
//   * @return
//   * @throws DataAccessException
//   * @throws ApplicationException
//   */
//  public int countDuplicateApplication(
//      Connection connection, 
//      ShinseiDataInfo shinseiDataInfo)
//      throws DataAccessException, ApplicationException
//  {
//
//      //2005/04/05 追加 ------------------------------------ここから
//      //理由 重複申請チェック実装のため
//      //重複チェックは基盤の場合のみ行う
//      KadaiInfo kadaiInfo = shinseiDataInfo.getKadaiInfo();
//      if(kadaiInfo!=null&&IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(kadaiInfo.getJigyoKubun())){
//          String jigyoCD=shinseiDataInfo.getJigyoCd();
//          
//          List dupliCheckJigyoCDList=(List)dupliCheckJigyoCDMap.get(jigyoCD);
//          if(dupliCheckJigyoCDList==null||dupliCheckJigyoCDList.size()==0){
//              return 0;
//          }
//          
//          String sql= "SELECT " +
//                      "   COUNT(*) CNT "+
//                      "FROM " +
//                      "   SHINSEIDATAKANRI SDK "+
//                      "       INNER JOIN JIGYOKANRI JK "+
//                      "       ON JK.JIGYO_ID= SDK.JIGYO_ID "+
//                      "       AND JK.NENDO = ";
//          
//          StringBuffer query=new StringBuffer(sql);
//          query.append(EscapeUtil.toSqlString(shinseiDataInfo.getNendo()))
//                      .append(" AND JK.JIGYO_KUBUN = 4 ")
//          //2005/04/26 追加 ここから------------------------------------------------------
//          //重複チェックに回数の絞込みを追加
//                      .append(" AND JK.KAISU = SDK.KAISU ")
//          //追加 ここまで-----------------------------------------------------------------
//                      .append("WHERE ")
//                      .append("SDK.SHINSEISHA_ID = '")
//                      .append(EscapeUtil.toSqlString(shinseiDataInfo.getShinseishaId()))
//                      .append("' AND SUBSTR(JK.JIGYO_ID,3,5) IN (")
//                      .append(changeIterator2CSV(dupliCheckJigyoCDList.iterator()))
//                      .append(")")
//          //2005/04/26 追加 ここから------------------------------------------------------
//          //重複チェックに削除フラグのチェックと回数の絞込みを追加
//                      .append(" AND SDK.DEL_FLG = 0")
//                      .append(" AND SDK.KAISU = ")
//                      .append(EscapeUtil.toSqlString(shinseiDataInfo.getKaisu()));
//          //追加 ここまで-----------------------------------------------------------------
//
//// 20050809 重複チェックの条件に状況IDを追加
//          query.append(" AND SDK.JOKYO_ID >= '02'")
//              .append(" AND SDK.JOKYO_ID <> '05'")
//              ;
//// Horikoshi
//          //2005.08.10 iso 修正時に自分自身を重複チェックからはじくように変更
//          if(shinseiDataInfo.getSystemNo() != null || shinseiDataInfo.getSystemNo().length() > 0) {
//          query.append(" AND SDK.SYSTEM_NO <> '")
//               .append(shinseiDataInfo.getSystemNo())
//               .append("'")
//               ;
//         }
//
//          //for debug
//          if(log.isDebugEnabled()){
//              log.debug("query:" + query);
//          }
//          
//          PreparedStatement preparedStatement = null;
//          ResultSet         resultSet         = null;
//          
//          int retValue=0;
//          try {
//              //登録
//              preparedStatement = connection.prepareStatement(query.toString());
//              resultSet = preparedStatement.executeQuery();
//              
//              if(resultSet.next()){
//                  Object obj=resultSet.getObject("CNT");
//                  if(obj!=null){
//                      retValue=Integer.parseInt(obj.toString());
//                  }
//              }
//          } catch (SQLException ex) {
//              throw new DataAccessException("申請情報排他制御中に例外が発生しました。 ", ex);
//          } finally {
//              DatabaseUtil.closeResource(resultSet, preparedStatement);
//          }
//          
//          return retValue;
//      }
//      //2005/04/05 追加 ------------------------------------ここまで
//
//// 20050713 20050617 特定領域重複申請チェックを追加
//      else if(IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(kadaiInfo.getJigyoKubun()) ||
//              IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(kadaiInfo.getJigyoKubun()) ||
//              IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO.equals(kadaiInfo.getJigyoKubun()) ||
//              IJigyoKubun.JIGYO_KUBUN_TOKUSUI.equals(kadaiInfo.getJigyoKubun())
//              ){
//
//          String strQuery = "";
//          StringBuffer sbSQL= new StringBuffer(strQuery);
//          if(IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(kadaiInfo.getJigyoKubun())){
//              sbSQL = selectDupTokutei(shinseiDataInfo);
//          }
//          else if(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(kadaiInfo.getJigyoKubun())){
//              sbSQL = selectDupGakuHikoubo(shinseiDataInfo);
//          }
//          else if(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO.equals(kadaiInfo.getJigyoKubun())){
//              sbSQL = selectDupGakuKoubo(shinseiDataInfo);
//          }
//          else if(IJigyoKubun.JIGYO_KUBUN_TOKUSUI.equals(kadaiInfo.getJigyoKubun())){
//              sbSQL = selectDupTokusui(shinseiDataInfo);
//          }
//
//          //検索の実行
//          int retValue=0;
//          if(sbSQL != null){
//              if(log.isDebugEnabled()){log.debug("query:" + sbSQL.toString());}
//              PreparedStatement preparedStatement = null;
//              ResultSet         resultSet         = null;
//              try {
//                  //登録
//                  preparedStatement = connection.prepareStatement(sbSQL.toString());
//                  resultSet = preparedStatement.executeQuery();
//                  if(resultSet.next()){
//                      Object obj=resultSet.getObject(IShinseiMaintenance.STR_COUNT);
//                      if(obj!=null){
//                          retValue=Integer.parseInt(obj.toString());
//                      }
//                  }
//              } catch (SQLException ex) {
//                  throw new DataAccessException("申請情報排他制御中に例外が発生しました。 ", ex);
//              } finally {
//                  DatabaseUtil.closeResource(resultSet, preparedStatement);
//              }
//          }
//
//          return retValue;
//      }
//// Horikoshi
//
//      return 0;
//  }
//2005/08/16 takano 重複チェックのロジック最適化により削除 ここまで ---

//2005/08/16 takano 重複チェックのロジック最適化により追加 ここから ---
    /**
     * 重複申請レコード数を返す。
     * 
     * @param connection
     * @param shinseiDataInfo
     * @return
     * @throws DataAccessException
     * @throws ApplicationException
     */
    public int countDuplicateApplication(
            Connection connection, 
            ShinseiDataInfo shinseiDataInfo)
            throws DataAccessException, ApplicationException {

        //当該事業コードの重複対象事業コードリストを取得する
        List dupliCheckJigyoCDList = (List)dupliCheckJigyoCDMap.get(shinseiDataInfo.getJigyoCd());
        if(dupliCheckJigyoCDList == null || dupliCheckJigyoCDList.size() == 0){
            //重複対象事業リストが存在しない場合は、重複チェック無しと判断する
            return 0;
        }

        //SQL（共通部分）
        String sql = "SELECT COUNT(SYSTEM_NO) AS CNT FROM SHINSEIDATAKANRI "
                   + "WHERE "
                   + " KENKYU_NO = ? "                  //同じ研究者が（変数）
                   + "AND "
                   + " SUBSTR(JIGYO_ID,1,2) = ? "       //同じ年度で（変数）
                   + "AND "
                   + " SUBSTR(JIGYO_ID,3,5) IN ("       //重複対象事業コードで（変数）
                   + StringUtil.getQuestionMark(dupliCheckJigyoCDList.size())
                   + ") "
                   + "AND "
                   + " SUBSTR(JIGYO_ID,8,1) = ? "       //同じ回数で（変数）
                   + "AND "
                   + " SYSTEM_NO <> ? "                 //自分自身（同じシステム受付番号）以外で（変数）
                   + "AND "
                   + " JOKYO_ID >= '02' "               //申請未確認[02]以上で（固定）
                   + "AND "
                   + " JOKYO_ID <> '05' "               //所属機関却下[05]以外で（固定）
                   + "AND "
                   + " DEL_FLG = 0 "                    //削除されていないもの（固定）
                   ;
        
        StringBuffer query = new StringBuffer(sql);
        
        //-----特定領域の場合は検索条件を追加-----
        if(IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(shinseiDataInfo.getKadaiInfo().getJigyoKubun())){
            query.append(makeQuery4DupTokutei(shinseiDataInfo));
        }
        //2005.10.27 iso [前年度応募あり・新規]と[前年度応募なし・継続]の重複応募を許す条件を追加
        //特推のみ条件追加
        else if(IJigyoKubun.JIGYO_KUBUN_TOKUSUI.equals(shinseiDataInfo.getKadaiInfo().getJigyoKubun())){
            query.append(makeQuery4DupZennenShinki(shinseiDataInfo));
        }
        
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        int retValue=0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(ps, index++, userInfo.getShinseishaInfo().getKenkyuNo());
            DatabaseUtil.setParameter(ps, index++, shinseiDataInfo.getJigyoId().substring(0,2));
            for(int i=0; i<dupliCheckJigyoCDList.size(); i++){
                DatabaseUtil.setParameter(ps, index++, (String)dupliCheckJigyoCDList.get(i));
            }
            DatabaseUtil.setParameter(ps, index++, shinseiDataInfo.getJigyoId().substring(7,8));
            DatabaseUtil.setParameter(ps, index++, shinseiDataInfo.getSystemNo());
            rs = ps.executeQuery();
            if(rs.next()){
                Object obj=rs.getObject("CNT");
                if(obj!=null){
                    retValue=Integer.parseInt(obj.toString());
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報検索中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(rs, ps);
        }
        return retValue;
    }
//2005/08/16 takano 重複チェックのロジック最適化により追加 ここまで ---

    /**
     * 受理登録時の重複申請レコード数を返す。
     * 現時点（2004/08/04）で未実装の状態。
     * 
     * @param connection
     * @param shinseiDataInfo
     * @return
     * @throws DataAccessException
     * @throws ApplicationException
     */
    public int countDuplicateApplicationForJuri(
        Connection connection, 
        ShinseiDataInfo shinseiDataInfo)
        throws DataAccessException, ApplicationException {
        //TODO takano 未実装の状態（重複申請チェック）。
        return 0;
    }
    
    /**
     * 指定検索条件に該当する申請書データを取得する。
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  申請者情報がセットされていなかった場合
     */
    public Page searchApplication(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException , NoDataFoundException, ApplicationException {

        String select =
        "SELECT "
            + " A.SYSTEM_NO,"                   //システム受付番号
            + " A.UKETUKE_NO,"                  //申請番号
            + " A.JIGYO_ID,"                    //事業ID
            + " A.NENDO,"                       //年度
            + " A.KAISU,"                       //回数
            + " A.JIGYO_NAME,"                  //事業名
            + " A.SHINSEISHA_ID,"               //申請者ID
            + " A.SAKUSEI_DATE,"                //申請書作成日
            + " A.SHONIN_DATE,"                 //所属機関承認日
            + " A.NAME_KANJI_SEI,"              //申請者氏名（漢字等-姓）
            + " A.NAME_KANJI_MEI,"              //申請者氏名（漢字等-名）
            + " A.KENKYU_NO,"                   //申請者研究者番号
            + " A.SHOZOKU_CD,"                  //所属機関コード
            + " A.SHOZOKU_NAME,"                //所属機関名
            + " A.SHOZOKU_NAME_RYAKU,"          //所属機関名（略称）
            + " A.BUKYOKU_NAME,"                //部局名
            + " A.BUKYOKU_NAME_RYAKU,"          //部局名（略称）
            + " A.SHOKUSHU_NAME_KANJI,"         //職名
            + " A.SHOKUSHU_NAME_RYAKU,"         //職名（略称）            
            + " A.KADAI_NAME_KANJI,"            //研究課題名(和文）
            + " A.JIGYO_KUBUN,"                 //事業区分
            + " A.KEKKA1_ABC,"                  //1次審査結果(ABC)
            + " A.KEKKA1_TEN,"                  //1次審査結果(点数)
            + " A.KEKKA1_TEN_SORTED,"           //1次審査結果(点数順)
            + " A.KEKKA2,"                      //2次審査結果
            + " A.JOKYO_ID,"                    //申請状況ID
            + " A.SAISHINSEI_FLG,"              //再申請フラグ
            + " A.KEI_NAME_RYAKU,"              //系統の区分（略称）
            + " A.KANTEN_RYAKU,"                //推薦の観点（略称）
            + " A.NENREI,"                      //年齢
//2007/2/25 劉長宇　追加　ここから
            + " A.DEL_FLG,"                     //削除フラグ
//2007/2/25 劉長宇　追加　ここまで

// 20050622
            + " CHCKLIST.KAKUTEI_DATE,"         //確定日
// Horikoshi
// 20050711 コメント確認機能追加のため
            + " A.JURI_BIKO,"                   //受理備考
// Horikoshi
            + " A.JURI_SEIRI_NO,"               //受理整理番号 2005/07/25

//2006/06/23 劉佳変更 ここから-------------------------------------------------------
        //2005/04/30 追加 ここから-------------------------------------------------------
        //理由 申請状況一覧で基盤の学振受付中以降の場合は申請書を修正できないようにするため
            + "CASE WHEN CHCKLIST.SHOZOKU_CD IS NULL THEN 'TRUE' ELSE 'FALSE' END EDITABLE, "
        //    + "CASE WHEN CHCKLIST.JOKYO_ID = '03' THEN 'FALSE' ELSE 'TRUE' END EDITABLE, "
        //追加 ここまで------------------------------------------------------------------
//2006/06/23 劉佳変更 ここまで------------------------------------------------------- 
            
//2006/06/23 劉佳追加 ここから-------------------------------------------------------
            + "CASE WHEN RYOIKI.RYOIKIKEIKAKUSHO_KAKUTEI_FLG = '1' THEN 'FALSE' ELSE 'TRUE' END RYOIKI_KAKUTEI_FLG, "
            + "RYOIKI.RYOIKIKEIKAKUSHO_KAKUTEI_FLG, "
//2006/06/23 劉佳追加 ここまで-------------------------------------------------------
//2006/07/07 劉佳追加　ここから-------------------------------------------------------
            + " DECODE (SIGN(TO_DATE"
            + "( TO_CHAR(B.RYOIKI_KAKUTEIKIKAN_END,"
            + "'YYYY/MM/DD'), 'YYYY/MM/DD' )- "
            + "TO_DATE( TO_CHAR(SYSDATE ,'YYYY/MM/DD'), 'YYYY/MM/DD' )),0 ,"
            + "'TRUE',1,'TRUE',-1,'FALSE') RYOIKI_KAKUTEIKIKAN_FLAG ,"
            + " B.RYOIKI_KAKUTEIKIKAN_END ," //領域代表者確定締切日  
//2006/07/07 劉佳追加 ここまで -------------------------------------------------------          
            + " DECODE"
            + " ("
            + "  NVL(A.SUISENSHO_PATH,'null') "
            + "  ,'null','FALSE'"               //推薦書パスがNULLのとき
            + "  ,      'TRUE'"                 //推薦書パスがNULL以外のとき
            + " ) SUISENSHO_FLG, "              //推薦書登録フラグ
            + " B.UKETUKEKIKAN_END,"            //学振受付期限（終了）
            + " B.HOKAN_DATE,"                  //データ保管日
            + " B.YUKO_DATE,"                   //保管有効期限
            + " DECODE"
            + " ("
            + "  SIGN( "
            + "       TO_DATE( TO_CHAR(B.UKETUKEKIKAN_END,'YYYY/MM/DD'), 'YYYY/MM/DD' ) "
            + "     - TO_DATE( TO_CHAR(SYSDATE           ,'YYYY/MM/DD'), 'YYYY/MM/DD' ) "
            + "      )"
            
            //2006.11.16 iso 締切日当日ラジオボタンが出ないバグを修正
//            + "  ,0 , 'TRUE'"                   //現在時刻と同じ場合
            + "  ,0 , 'FALSE'"                   //現在時刻と同じ場合
            
            + "  ,-1 , 'TRUE'"                   //現在時刻の方が受付期限より前
            + "  ,1, 'FALSE'"                  //現在時刻の方が受付期限より後
            + " ) UKETUKE_END_FLAG,"            //学振受付期限（終了）到達フラグ
            + " DECODE"
            + " ("
            + "  NVL(A.PDF_PATH,'null') "
            + "  ,'null','FALSE'"               //PDFの格納パスがNULLのとき
            + "  ,      'TRUE'"                 //PDFの格納パスがNULL以外のとき
            + " ) PDF_PATH_FLG, "               //PDFの格納パスフラグ
            + " DECODE"
            + " ("
            + "  NVL(C.SYSTEM_NO,'null') "
            + "  ,'null','FALSE'"               //添付ファイルがNULLのとき
            + "  ,      'TRUE'"                 //添付ファイルがNULL以外のとき
            + " ) TENPUFILE_FLG "               //添付ファイルフラグ 
            + " FROM"
            + " SHINSEIDATAKANRI"+dbLink+" A"   //申請データ管理テーブル
            
            //2005/04/08 追加 ここから-------------------------------------------------------
            //理由 部局担当者の場合の条件追加のため
            + " INNER JOIN JIGYOKANRI"+dbLink+" B"
            +" ON A.JIGYO_ID = B.JIGYO_ID "
            + " LEFT JOIN TENPUFILEINFO"+dbLink+" C "
            +" ON A.SYSTEM_NO = C.SYSTEM_NO "
            +" AND C.TENPU_PATH IS NOT NULL ";
            //部局担当者の担当部局管理テーブルに値がある場合に条件を追加する
            if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
                select = select 
                        + " INNER JOIN TANTOBUKYOKUKANRI T"
                        +" ON T.SHOZOKU_CD = A.SHOZOKU_CD"
                        +" AND T.BUKYOKU_CD = A.BUKYOKU_CD"
                        //2005/04/20　追加 ここから------------------------------------------
                        //理由 条件不足のため 
                        + " AND T.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
                        //追加 ここまで------------------------------------------------------
            }
            //追加 ここまで------------------------------------------------------------------
            
            //2005/04/30 追加 ここから-------------------------------------------------------
            //理由 申請状況一覧で基盤の学振受付中以降の場合は申請書を修正できないようにするため
            select = select + "LEFT JOIN CHCKLISTINFO CHCKLIST "
                        + "ON CHCKLIST.JIGYO_ID = A.JIGYO_ID "
                        + "AND CHCKLIST.SHOZOKU_CD = A.SHOZOKU_CD "                   
                        + "AND CHCKLIST.JOKYO_ID <> '03' "               
            //2006/06/21 劉佳　追加 ここから-------------------------------------------------------
                        + "LEFT JOIN RYOIKIKEIKAKUSHOINFO RYOIKI "
                        + "ON RYOIKI.KARIRYOIKI_NO = A.RYOIKI_NO "
                        + "AND RYOIKI.DEL_FLG = 0 ";
            //2006/06/21 劉佳　追加 ここまで-------------------------------------------------------
            //追加 ここまで------------------------------------------------------------------
            
            //2005/04/08 削除 ここから-------------------------------------------------------
            //理由 部局担当者の場合の条件追加のため   
            //+ " , JIGYOKANRI"+dbLink+" B,"        //事業情報管理テーブル
            //+ " (SELECT DISTINCT SYSTEM_NO FROM TENPUFILEINFO "
            //+     " WHERE TENPU_PATH IS NOT NULL) C"  //添付ファイルテーブル（添付ファイルパスがnullではない場合のみ） 
            //削除 ここまで------------------------------------------------------------------
            select = select + " WHERE 1=1 ";
//update2004/10/26 システム管理者向け申請情報検索への対応
//          + " A.DEL_FLG = 0"                  //削除フラグが[0]
//          + " AND"
//            + " A.JIGYO_ID = B.JIGYO_ID";       //事業IDが同じもの
            //2005/04/11 削除 
//          + " AND"
//          + " A.SYSTEM_NO = C.SYSTEM_NO(+)"   //テーブルの連結(Cに連結するデータがない場合も表示)
            //削除 ここまで-----------------------------------------------------------
        
        //検索条件を元にSQL文を生成する。
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        // ページ取得
        return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query);
    }

    /**
     * CSV出力するリストを返す。
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchCsvData(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select =
        "SELECT "
//          2005/04/22 追加 ここから----------
//          理由:申請状況ID追加のため
            + "  A.JOKYO_ID"                //申請状況ID
//          2005/04/22 追加 ここまで----------
            + " ,A.SYSTEM_NO"               //システム受付番号
            + " ,A.UKETUKE_NO"              //申請番号
            + " ,A.JIGYO_ID"                //事業ID
            + " ,A.NENDO"                   //年度
            + " ,A.KAISU"                   //回数
            + " ,A.JIGYO_NAME"              //事業名
            + " ,A.SHINSEISHA_ID"           //申請者ID
            + " ,TO_CHAR(A.SAKUSEI_DATE, 'YYYY/MM/DD')"         //申請書作成日
            + " ,TO_CHAR(A.SHONIN_DATE, 'YYYY/MM/DD')"          //所属機関承認日
            //add start ly 2006/08/02
            + " ,TO_CHAR(A.RYOIKI_KAKUTEI_DATE, 'YYYY/MM/DD')"  //領域代表者確定日
            //add end ly 2006/08/02
            + " ,TO_CHAR(A.JYURI_DATE, 'YYYY/MM/DD')"           //学振受理日
            + " ,A.NAME_KANJI_SEI"          //申請者氏名（漢字等-姓）
            + " ,A.NAME_KANJI_MEI"          //申請者氏名（漢字等-名）
            + " ,A.NAME_KANA_SEI"           //申請者氏名（フリガナ-姓）
            + " ,A.NAME_KANA_MEI"           //申請者氏名（フリガナ-名）
            + " ,A.NAME_RO_SEI"             //申請者氏名（ローマ字-姓）
            + " ,A.NAME_RO_MEI"             //申請者氏名（ローマ字-名）
            + " ,A.NENREI"                  //年齢
            + " ,A.KENKYU_NO"               //申請者研究者番号
            + " ,A.SHOZOKU_CD"              //所属機関コード
            + " ,A.SHOZOKU_NAME"            //所属機関名
            + " ,A.SHOZOKU_NAME_RYAKU"      //所属機関名（略称）
            + " ,A.BUKYOKU_CD"              //部局コード
            + " ,A.BUKYOKU_NAME"            //部局名
            + " ,A.BUKYOKU_NAME_RYAKU"      //部局名（略称）
            + " ,A.SHOKUSHU_CD"             //職名コード
            + " ,A.SHOKUSHU_NAME_KANJI"     //職名（和文）
            + " ,A.SHOKUSHU_NAME_RYAKU"     //職名（略称）
            + " ,A.ZIP"                     //郵便番号
            + " ,A.ADDRESS"                 //住所
            + " ,A.TEL"                     //TEL
            + " ,A.FAX"                     //FAX
            + " ,A.EMAIL"                   //E-Mail
            + " ,A.KADAI_NAME_KANJI"        //研究課題名(和文）
            + " ,A.KADAI_NAME_EIGO"         //研究課題名(英文）
            + " ,A.KEI_NAME_NO"             //系等の区分番号
            + " ,A.KEI_NAME"                //系等の区分
            + " ,A.KEI_NAME_RYAKU"          //系等の区分略称
            + " ,A.BUNKASAIMOKU_CD"         //細目番号
            + " ,A.BUNYA_NAME"              //分野
            + " ,A.BUNKA_NAME"              //分科
            + " ,A.SAIMOKU_NAME"            //細目
            + " ,A.BUNKASAIMOKU_CD2"        //細目番号2
            + " ,A.BUNYA_NAME2"             //分野2
            + " ,A.BUNKA_NAME2"             //分科2
            + " ,A.SAIMOKU_NAME2"           //細目2
            + " ,A.KANTEN_NO"               //推薦の観点番号
            + " ,A.KANTEN"                  //推薦の観点
            + " ,A.KANTEN_RYAKU"            //推薦の観点略称
// 20050622
            + " ,A.KENKYU_KUBUN"            //研究区分
            + " ,A.OHABAHENKO"              //大幅な変更
            + " ,A.RYOIKI_NO"               //領域番号
            + " ,A.KOMOKU_NO"               //項目番号
            + " ,A.CHOSEIHAN"               //調整班
// Horikoshi

// 20050712
            + " ,A.SAIMOKU_KEYWORD"             //細目表キーワード
            + " ,A.OTHER_KEYWORD"               //細目表以外のキーワード
// Horikoshi

            + " ,A.KEIHI1"                  //1年目研究経費
            + " ,A.BIHINHI1"                //1年目設備備品費
            + " ,A.SHOMOHINHI1"             //1年目消耗品費
            + " ,A.RYOHI1"                  //1年目旅費
            + " ,A.SHAKIN1"                 //1年目謝金等
            + " ,A.SONOTA1"                 //1年目その他
            + " ,A.KEIHI2"                  //2年目研究経費
            + " ,A.BIHINHI2"                //2年目設備備品費
            + " ,A.SHOMOHINHI2"             //2年目消耗品費
            + " ,A.RYOHI2"                  //2年目旅費
            + " ,A.SHAKIN2"                 //2年目謝金等
            + " ,A.SONOTA2"                 //2年目その他
            + " ,A.KEIHI3"                  //3年目研究経費
            + " ,A.BIHINHI3"                //3年目設備備品費
            + " ,A.SHOMOHINHI3"             //3年目消耗品費
            + " ,A.RYOHI3"                  //3年目旅費
            + " ,A.SHAKIN3"                 //3年目謝金等
            + " ,A.SONOTA3"                 //3年目その他
            + " ,A.KEIHI4"                  //4年目研究経費
            + " ,A.BIHINHI4"                //4年目設備備品費
            + " ,A.SHOMOHINHI4"             //4年目消耗品費
            + " ,A.RYOHI4"                  //4年目旅費
            + " ,A.SHAKIN4"                 //4年目謝金等
            + " ,A.SONOTA4"                 //4年目その他
            + " ,A.KEIHI5"                  //5年目研究経費
            + " ,A.BIHINHI5"                //5年目設備備品費
            + " ,A.SHOMOHINHI5"             //5年目消耗品費
            + " ,A.RYOHI5"                  //5年目旅費
            + " ,A.SHAKIN5"                 //5年目謝金等
            + " ,A.SONOTA5"                 //5年目その他
            //add start ly 2006/08/02
            + " ,A.KEIHI6"                  //6年目研究経費
            + " ,A.BIHINHI6"                //6年目設備備品費
            + " ,A.SHOMOHINHI6"             //6年目消耗品費
            + " ,A.RYOHI6"                  //6年目旅費
            + " ,A.SHAKIN6"                 //6年目謝金等
            + " ,A.SONOTA6"                 //6年目その他
            //add end ly 2006/08/02
            + " ,A.KEIHI_TOTAL"             //総計-研究経費
            + " ,A.BIHINHI_TOTAL"           //総計-設備備品費
            + " ,A.SHOMOHINHI_TOTAL"        //総計-消耗品費
            + " ,A.RYOHI_TOTAL"             //総計-旅費
            + " ,A.SHAKIN_TOTAL"            //総計-謝金等
            + " ,A.SONOTA_TOTAL"            //総計-その他
            + " ,A.SOSHIKI_KEITAI_NO"       //研究組織の形態番号
            + " ,A.SOSHIKI_KEITAI"          //研究組織の形態
            + " ,A.BUNTANKIN_FLG"           //分担金の有無
            + " ,A.KENKYU_NINZU"            //研究者数
            + " ,A.TAKIKAN_NINZU"           //他機関の分担者数
//          2005/04/22 削除 ここから----------
//          理由:仕様に存在しないため
            //2005/03/31 追加 ------------------------------------ここから
            //理由 研究協力者入力欄が追加されたため
//          + " ,A.TAKIKAN_NINZU"           //研究協力者数
            //2005/03/31 追加 ------------------------------------ここまで
//          2005/04/22 削除 ここまで----------
            + " ,A.SHINSEI_KUBUN"           //新規継続区分
            + " ,A.KADAI_NO_KEIZOKU"        //継続分の研究課題番号
            + " ,A.SHINSEI_FLG_NO"          //研究計画最終年度前年度の応募
//          + " ,A.SHINSEI_FLG"             //申請の有無
            + " ,A.KADAI_NO_SAISYU"         //最終年度課題番号
            + " ,A.KAIGAIBUNYA_CD"          //海外分野コード
            + " ,A.KAIGAIBUNYA_NAME"        //海外分野名称
            + " ,A.KAIGAIBUNYA_NAME_RYAKU"  //海外分野略称
            + " ,A.KANREN_SHIMEI1"          //関連分野の研究者-氏名1
            + " ,A.KANREN_KIKAN1"           //関連分野の研究者-所属機関1
            + " ,A.KANREN_BUKYOKU1"         //関連分野の研究者-所属部局1
            + " ,A.KANREN_SHOKU1"           //関連分野の研究者-職名1
            + " ,A.KANREN_SENMON1"          //関連分野の研究者-専門分野1
            + " ,A.KANREN_TEL1"             //関連分野の研究者-勤務先電話番号1
            + " ,A.KANREN_JITAKUTEL1"       //関連分野の研究者-自宅電話番号1              
            + " ,A.KANREN_MAIL1"            //関連分野の研究者-Email1               
            + " ,A.KANREN_SHIMEI2"          //関連分野の研究者-氏名2
            + " ,A.KANREN_KIKAN2"           //関連分野の研究者-所属機関2
            + " ,A.KANREN_BUKYOKU2"         //関連分野の研究者-所属部局2
            + " ,A.KANREN_SHOKU2"           //関連分野の研究者-職名2
            + " ,A.KANREN_SENMON2"          //関連分野の研究者-専門分野2
            + " ,A.KANREN_TEL2"             //関連分野の研究者-勤務先電話番号2
            + " ,A.KANREN_JITAKUTEL2"       //関連分野の研究者-自宅電話番号2              
            + " ,A.KANREN_MAIL2"            //関連分野の研究者-Email2               
            + " ,A.KANREN_SHIMEI3"          //関連分野の研究者-氏名3
            + " ,A.KANREN_KIKAN3"           //関連分野の研究者-所属機関3
            + " ,A.KANREN_BUKYOKU3"         //関連分野の研究者-所属部局3
            + " ,A.KANREN_SHOKU3"           //関連分野の研究者-職名3
            + " ,A.KANREN_SENMON3"          //関連分野の研究者-専門分野3
            + " ,A.KANREN_TEL3"             //関連分野の研究者-勤務先電話番号3
            + " ,A.KANREN_JITAKUTEL3"       //関連分野の研究者-自宅電話番号3              
            + " ,A.KANREN_MAIL3"            //関連分野の研究者-Email3   
// 2007/02/13  張志男　追加ここから
            + " ,A.SHINSARYOIKI_CD"         //審査希望分野コード
            + " ,A.SHINSARYOIKI_NAME"       //審査希望分野名
// 2007/02/13　張志男　追加ここまで
//          2006/02/13 START
            + " ,TO_CHAR(A.SAIYO_DATE, 'YYYY/MM/DD')"             //採用年月日
            + " ,A.KINMU_HOUR"               //勤務時間数
            + " ,A.NAIYAKUGAKU"              //特別研究員奨励費内約額
// 2007/02/13  張志男　追加ここから
            + " ,A.SHOREIHI_NO_NENDO"        //特別研究員奨励費課題番号-年度
            + " ,A.SHOREIHI_NO_SEIRI"        //特別研究員奨励費課題番号-整理番号
// 2007/02/13　張志男　追加ここまで 
            + " ,A.OUBO_SHIKAKU"              //応募資格
            + " ,TO_CHAR(A.SIKAKU_DATE, 'YYYY/MM/DD')"            //資格取得年月日
            + " ,A.SYUTOKUMAE_KIKAN"         //資格取得前機関名
            + " ,TO_CHAR(A.IKUKYU_START_DATE, 'YYYY/MM/DD')"      //育休等開始日
            + " ,TO_CHAR(A.IKUKYU_END_DATE, 'YYYY/MM/DD')"        //育休等終了日
            //2006/02/13 END
            + " ,A.JURI_KEKKA"              //受理結果
            + " ,A.JURI_BIKO"               //受理結果備考
            + " ,A.JURI_SEIRI_NO"           //整理番号  2005/07/25
            + " ,A.KEKKA1_ABC"              //１次審査結果(ABC)
            + " ,A.KEKKA1_TEN"              //１次審査結果(点数)
//          + " ,A.KEKKA1_TEN_SORTED"       //１次審査結果(点数順)
            + " ,A.SHINSA1_BIKO"            //１次審査備考
            + " ,A.KEKKA2"                  //２次審査結果
            + " ,A.SHINSA2_BIKO"            //業務担当者記入欄
            //2005/04/26 削除　-----------------------------------------------ここから
            //理由 状況IDを1項目目へ移動したため
            //+ " ,A.JOKYO_ID"              //申請状況ID
            //2005/04/26 削除　-----------------------------------------------ここまで
            + " ,A.SAISHINSEI_FLG"          //再申請フラグ
//          2005/04/22 追加 ここから----------
//          理由:版追加のため
            + " ,A.EDITION"                 //版
//          2005/04/22 追加 ここまで----------
            + " ,TO_CHAR(B.HOKAN_DATE, 'YYYY/MM/DD')"       //データ保管日
            + " ,TO_CHAR(B.YUKO_DATE, 'YYYY/MM/DD')"        //保管有効期限
            + " FROM"
            + " SHINSEIDATAKANRI" + dbLink + " A,"          //申請データ管理テーブル
            + " JIGYOKANRI" + dbLink + " B"                 //事業情報管理テーブル
            + " WHERE"
            + " A.DEL_FLG = 0"                              //削除フラグが[0]
            + " AND"
            + " A.JIGYO_ID = B.JIGYO_ID"                    //事業IDが同じもの
            ;
        
        //検索条件を元にSQL文を生成する。
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //カラム名リストを生成する（最初の１回のみ）
        //※指定文字列はSQLの識別子長を超えてしまうため別にセットする。
        //2005.10.04 iso 申請→応募、所属機関→所属研究機関、コード→番号、申請書→研究計画調書
        if(csvColumnList == null){
            String[] columnArray = {
//                  2005/04/22 追加 ここから----------
//                  理由:申請状況ID追加のため
                                    "応募状況ID"
//                  2005/04/22 追加 ここまで----------
                                   ,"システム受付番号"
                                   ,"応募番号"
                                   ,"事業ID"
                                   ,"年度"
                                   ,"回数"
                                   ,"事業名"
                                   ,"応募者ID"
                                   ,"研究計画調書作成日"
                                   ,"所属研究機関承認日"
                                   //add start ly 2006/08/02
                                   ,"領域代表者確定日"
                                   //add end ly 2006/08/02
                                   ,"学振受理日"
                                   ,"応募者氏名（漢字等-姓）"
                                   ,"応募者氏名（漢字等-名）"
                                   ,"応募者氏名（フリガナ-姓）"
                                   ,"応募者氏名（フリガナ-名）"
                                   ,"応募者氏名（ローマ字-姓）"
                                   ,"応募者氏名（ローマ字-名）"
                                   ,"年齢"
                                   ,"応募者研究者番号"
                                   ,"所属研究機関番号"
                                   ,"所属研究機関名"
                                   ,"所属研究機関名（略称）"
                                   ,"部局番号"
                                   ,"部局名"
                                   ,"部局名（略称）"
                                   ,"職名番号"
                                   ,"職名（和文）"
                                   ,"職名（略称）"
                                   ,"郵便番号"
                                   ,"住所"
                                   ,"TEL"
                                   ,"FAX"
                                   ,"Eｍail"
                                   ,"研究課題名(和文）"
                                   ,"研究課題名(英文）"
                                   ,"系等の区分番号"
                                   ,"系等の区分"
                                   ,"系等の区分略称"
                                   ,"細目番号"
                                   ,"分野"
                                   ,"分科"
                                   ,"細目"
                                   ,"細目番号2"
                                   ,"分野2"
                                   ,"分科2"
                                   ,"細目2"
                                   ,"推薦の観点番号"
                                   ,"推薦の観点"
                                   ,"推薦の観点略称"
// 20050622
                                   ,"研究区分"
                                   ,"大幅な変更を伴う研究課題"
                                   ,"領域番号"
                                   ,"研究項目番号"
                                   ,"調整班"
// Horikoshi

// 20050712 キーワード情報の追加
                                   ,"細目表キーワード"
                                   ,"細目表以外のキーワード"
// Horikoshi

                                   ,"1年目研究経費"
                                   ,"1年目設備備品費"
                                   ,"1年目消耗品費"
                                   ,"1年目旅費"
                                   ,"1年目謝金等"
                                   ,"1年目その他"
                                   ,"2年目研究経費"
                                   ,"2年目設備備品費"
                                   ,"2年目消耗品費"
                                   ,"2年目旅費"
                                   ,"2年目謝金等"
                                   ,"2年目その他"
                                   ,"3年目研究経費"
                                   ,"3年目設備備品費"
                                   ,"3年目消耗品費"
                                   ,"3年目旅費"
                                   ,"3年目謝金等"
                                   ,"3年目その他"
                                   ,"4年目研究経費"
                                   ,"4年目設備備品費"
                                   ,"4年目消耗品費"
                                   ,"4年目旅費"
                                   ,"4年目謝金等"
                                   ,"4年目その他"
                                   ,"5年目研究経費"
                                   ,"5年目設備備品費"
                                   ,"5年目消耗品費"
                                   ,"5年目旅費"
                                   ,"5年目謝金等"
                                   ,"5年目その他"
                                   //add start ly 2006/08/02
                                   ,"6年目研究経費"
                                   ,"6年目設備備品費"
                                   ,"6年目消耗品費"
                                   ,"6年目旅費"
                                   ,"6年目謝金等"
                                   ,"6年目その他"
                                   //add end ly 2006/08/02
                                   ,"総計-研究経費"
                                   ,"総計-設備備品費"
                                   ,"総計-消耗品費"
                                   ,"総計-旅費"
                                   ,"総計-謝金等"
                                   ,"総計-その他"
                                   ,"研究組織の形態番号"
                                   ,"研究組織の形態"
                                   ,"分担金の有無"
                                   ,"研究者数"
                                   ,"他機関の分担者数"
                                   ,"新規継続区分"
                                   ,"継続分の研究課題番号"
                                   ,"研究計画最終年度前年度の応募"
//                                 ,"申請の有無"
                                   ,"最終年度課題番号"
                                   ,"海外分野番号"
                                   ,"海外分野名称"
                                   ,"海外分野略称"
                                   ,"関連分野の研究者-氏名1"
                                   ,"関連分野の研究者-所属研究機関1"
                                   ,"関連分野の研究者-所属部局1"
                                   ,"関連分野の研究者-職名1"
                                   ,"関連分野の研究者-専門分野1"
                                   ,"関連分野の研究者-勤務先電話番号1"
                                   ,"関連分野の研究者-自宅電話番号1"
// 2007/03/01  張志男　修正 ここから
                                   //,"関連分野の研究者-Email1"
                                   ,"関連分野の研究者-E-mail1"
// 2007/03/01  張志男　修正 ここまで
                                   ,"関連分野の研究者-氏名2"
                                   ,"関連分野の研究者-所属研究機関2"
                                   ,"関連分野の研究者-所属部局2"
                                   ,"関連分野の研究者-職名2"
                                   ,"関連分野の研究者-専門分野2"
                                   ,"関連分野の研究者-勤務先電話番号2"
                                   ,"関連分野の研究者-自宅電話番号2"
// 2007/03/01  張志男　修正 ここから
                                   //,"関連分野の研究者-Email2"
                                   ,"関連分野の研究者-E-mail2"
// 2007/03/01  張志男　修正 ここまで
                                   ,"関連分野の研究者-氏名3"
                                   ,"関連分野の研究者-所属研究機関3"
                                   ,"関連分野の研究者-所属部局3"
                                   ,"関連分野の研究者-職名3"
                                   ,"関連分野の研究者-専門分野3"
                                   ,"関連分野の研究者-勤務先電話番号3"
                                   ,"関連分野の研究者-自宅電話番号3"
// 2007/03/01  張志男　修正 ここから
                                   //,"関連分野の研究者-Email3"
                                   ,"関連分野の研究者-E-mail3"
// 2007/03/01  張志男　修正 ここまで
// 2007/02/13  張志男　追加ここから
                                   ,"審査希望分野コード"
                                   ,"審査希望分野名"
// 2007/02/13　張志男　追加ここまで
//                               2006/02/13 START
                                   ,"採用年月日"
// 2007/02/13  張志男　修正 ここから
                                   //,"勤務時間数"
                                   ,"週当たりの勤務時間数"
// 2007/02/13　張志男　修正 ここまで                                   
                                   ,"特別研究員奨励費内約額"
// 2007/02/13  張志男　追加 ここから
                                   ,"特別研究員奨励費課題番号-年度"
                                   ,"特別研究員奨励費課題番号-整理番号"
// 2007/02/13　張志男　追加 ここまで
                                   ,"応募資格"
                                   ,"資格取得年月日"
                                   ,"資格取得前機関名"
                                   ,"育休等開始日"
                                   ,"育休等終了日"
                                   //2006/02/13 END
                                   ,"受理結果"
                                   ,"受理結果備考"
                                   ,"整理番号(学創用)"
                                   ,"１次審査結果(ABC)"
                                   ,"１次審査結果(点数)"
//                                 ,"１次審査結果(点数順)"                                  
                                   ,"１次審査備考"
                                   ,"２次審査結果"
                                   ,"業務担当者記入欄"
                                    //2005/04/26 削除　-----------------------------------------------ここから
                                    //理由 状況IDを1項目目へ移動したため
                                    //,"申請状況ID"
                                    //2005/04/26 削除　-----------------------------------------------ここまで
                                   ,"再応募フラグ"
//                      2005/04/22 追加 ここから----------
//                                  理由:版追加のため
                                   ,"版"
//                      2005/04/22 追加 ここまで----------
                                   ,"データ保管日"
                                   ,"保管有効期限"
                                    };
            //念のためスレッドセーフ化
            csvColumnList = Collections.synchronizedList(Arrays.asList(columnArray));
        }
        
        //CSVリスト取得（カラム名をキー項目名としない）
        List csvDataList = SelectUtil.selectCsvList(connection, query, false);
        
        //最初の要素にカラム名リストを挿入する
        csvDataList.add(0, csvColumnList);
        csvColumnList = null;
        return csvDataList;
    }

    //add start ly 2006/07/11
    /**
     * 応募情報一覧（所属機関）CSV出力するリストを返す。
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchShozokuCsvData(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select =
        "SELECT "
            + "  A.JIGYO_NAME"                              //事業名
            + " ,TO_CHAR(A.SAKUSEI_DATE, 'YYYY/MM/DD')"     //研究計画調書作成日
            + " ,M.SHOZOKU_HYOJI"                           //応募状況
            + " ,TO_CHAR(A.SHONIN_DATE, 'YYYY/MM/DD')"      //所属研究機関承認日
            + " ,A.NAME_KANJI_SEI"                          //申請者氏名（漢字等-姓）
            + " ,A.NAME_KANJI_MEI"                          //申請者氏名（漢字等-名）
            + " ,A.NAME_KANA_SEI"                           //申請者氏名（フリガナ-姓）
            + " ,A.NAME_KANA_MEI"                           //申請者氏名（フリガナ-名）
            + " ,A.NENREI"                                  //年齢
            + " ,A.KENKYU_NO"                               //申請者研究者番号
            + " ,A.SHOZOKU_CD"                              //所属機関コード
            + " ,A.SHOZOKU_NAME"                            //所属機関名
            + " ,A.BUKYOKU_CD"                              //部局コード
            + " ,A.BUKYOKU_NAME"                            //部局名
            + " ,A.SHOKUSHU_CD"                             //職名コード
            + " ,A.SHOKUSHU_NAME_KANJI"                     //職名（和文）
            + " ,A.ZIP"                                     //郵便番号
            + " ,A.ADDRESS"                                 //住所
            + " ,A.TEL"                                     //TEL
            + " ,A.FAX"                                     //FAX
            + " ,A.EMAIL"                                   //E-Mail
//            + " ,A.URL"                                     //URL
            + " ,A.KADAI_NAME_KANJI"                        //研究課題名(和文）
            + " ,A.KEI_NAME_NO"                             //系等の区分番号
            + " ,A.KEI_NAME"                                //系等の区分
            + " ,A.BUNKASAIMOKU_CD"                         //細目番号
            + " ,A.BUNKATSU_NO"                             //分割番号
            + " ,A.BUNYA_NAME"                              //分野
            + " ,A.BUNKA_NAME"                              //分科
            + " ,A.SAIMOKU_NAME"                            //細目
            + " ,A.BUNKASAIMOKU_CD2"                        //細目番号2
            + " ,A.BUNYA_NAME2"                             //分野2
            + " ,A.BUNKA_NAME2"                             //分科2
            + " ,A.SAIMOKU_NAME2"                           //細目2
            + " ,A.KENKYU_KUBUN"                            //研究区分
            + " ,A.OHABAHENKO"                              //大幅な変更を伴う研究課題
            + " ,A.RYOIKI_NO"                               //領域番号
            + " ,A.KOMOKU_NO"                               //項目番号
            + " ,A.CHOSEIHAN"                               //調整班
            + " ,A.RYOIKI_RYAKU"                            //領域略称名
            + " ,SUBSTR(A.UKETUKE_NO,7,10)as UKETUKE_NO"    //整理番号
            + " ,A.EDITION"                                 //版数
            + " ,A.SAIMOKU_KEYWORD"                         //細目表キーワード
            + " ,A.OTHER_KEYWORD"                           //細目表以外のキーワード
            + " ,A.KEIHI1"                                  //1年目研究経費   
            + " ,A.BIHINHI1"                                //1年目設備備品費 
            + " ,A.SHOMOHINHI1"                             //1年目消耗品費   
            + " ,A.RYOHI1"                                  //1年目旅費       
            + " ,A.SHAKIN1"                                 //1年目謝金等     
            + " ,A.SONOTA1"                                 //1年目その他     
            + " ,A.KEIHI2"                                  //2年目研究経費   
            + " ,A.BIHINHI2"                                //2年目設備備品費 
            + " ,A.SHOMOHINHI2"                             //2年目消耗品費   
            + " ,A.RYOHI2"                                  //2年目旅費       
            + " ,A.SHAKIN2"                                 //2年目謝金等     
            + " ,A.SONOTA2"                                 //2年目その他     
            + " ,A.KEIHI3"                                  //3年目研究経費   
            + " ,A.BIHINHI3"                                //3年目設備備品費 
            + " ,A.SHOMOHINHI3"                             //3年目消耗品費   
            + " ,A.RYOHI3"                                  //3年目旅費       
            + " ,A.SHAKIN3"                                 //3年目謝金等     
            + " ,A.SONOTA3"                                 //3年目その他     
            + " ,A.KEIHI4"                                  //4年目研究経費   
            + " ,A.BIHINHI4"                                //4年目設備備品費 
            + " ,A.SHOMOHINHI4"                             //4年目消耗品費   
            + " ,A.RYOHI4"                                  //4年目旅費       
            + " ,A.SHAKIN4"                                 //4年目謝金等     
            + " ,A.SONOTA4"                                 //4年目その他     
            + " ,A.KEIHI5"                                  //5年目研究経費   
            + " ,A.BIHINHI5"                                //5年目設備備品費 
            + " ,A.SHOMOHINHI5"                             //5年目消耗品費   
            + " ,A.RYOHI5"                                  //5年目旅費       
            + " ,A.SHAKIN5"                                 //5年目謝金等     
            + " ,A.SONOTA5"                                 //5年目その他   
            + " ,A.KEIHI6"                                  //6年目研究経費   
            + " ,A.BIHINHI6"                                //6年目設備備品費 
            + " ,A.SHOMOHINHI6"                             //6年目消耗品費   
            + " ,A.RYOHI6"                                  //6年目旅費       
            + " ,A.SHAKIN6"                                 //6年目謝金等     
            + " ,A.SONOTA6"                                 //6年目その他
            + " ,A.KEIHI_TOTAL"                             //総計-研究経費
            + " ,A.BIHINHI_TOTAL"                           //総計-設備備品費
            + " ,A.SHOMOHINHI_TOTAL"                        //総計-消耗品費
            + " ,A.RYOHI_TOTAL"                             //総計-旅費
            + " ,A.SHAKIN_TOTAL"                            //総計-謝金等
            + " ,A.SONOTA_TOTAL"                            //総計-その他
            + " ,A.BUNTANKIN_FLG"                           //分担金の有無
            + " ,A.KAIJIKIBO_FLG_NO"                        //開示希望の有無
            + " ,A.KENKYU_NINZU"                            //研究者数
            + " ,A.TAKIKAN_NINZU"                           //他機関の分担者数 
            + " ,A.SHINSEI_KUBUN"                           //新規継続区分
            + " ,A.KADAI_NO_KEIZOKU"                        //継続分の研究課題番号
            + " ,A.SHINSEI_FLG_NO"                          //研究計画最終年度前年度の応募
            + " ,A.KADAI_NO_SAISYU"                         //最終年度課題番号
            + " ,A.KAIGAIBUNYA_CD"                          //海外分野コード
            + " ,A.KAIGAIBUNYA_NAME"                        //海外分野名称
            + " ,A.KAIGAIBUNYA_NAME_RYAKU"                  //海外分野略称
// 2007/02/14  張志男　追加 ここから
            + " ,A.SHINSARYOIKI_CD"                         //審査希望分野コード
            + " ,A.SHINSARYOIKI_NAME"                       //審査希望分野名
// 2007/02/14　張志男　追加 ここまで
            + " ,TO_CHAR(A.SAIYO_DATE, 'YYYY/MM/DD')"       //採用年月日
            + " ,A.KINMU_HOUR"                              //勤務時間数
            + " ,A.NAIYAKUGAKU"                             //特別研究員奨励費内約額 
// 2007/02/14  張志男　追加 ここから
            + " ,A.SHOREIHI_NO_NENDO"                       //特別研究員奨励費課題番号-年度
            + " ,A.SHOREIHI_NO_SEIRI"                       //特別研究員奨励費課題番号-整理番号
// 2007/02/14　張志男　追加 ここまで
            + " ,A.OUBO_SHIKAKU"                            //応募資格
            + " ,TO_CHAR(A.SIKAKU_DATE, 'YYYY/MM/DD')"      //資格取得年月日
            + " ,A.SYUTOKUMAE_KIKAN"                        //資格取得前機関名
            + " ,TO_CHAR(A.IKUKYU_START_DATE, 'YYYY/MM/DD')"//育休等開始日
            + " ,TO_CHAR(A.IKUKYU_END_DATE, 'YYYY/MM/DD')"  //育休等終了日
            + " FROM"
            + " SHINSEIDATAKANRI" + dbLink + " A"           //申請データ管理テーブル
            + " LEFT JOIN MASTER_STATUS M" 
            + " ON A.JOKYO_ID = M.JOKYO_ID  "
            + " AND A.SAISHINSEI_FLG = M.SAISHINSEI_FLG"
//2007/3/27　事業情報管理テーブルから項目を取得してない為、コメントする
//            + " ,JIGYOKANRI" + dbLink + " B"                //事業情報管理テーブル
            + " WHERE"
            + " A.DEL_FLG = 0"                              //削除フラグが[0]
//            + " AND"
//            + " A.JIGYO_ID = B.JIGYO_ID"                    //事業IDが同じもの
            ;
        
        //検索条件を元にSQL文を生成する。
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //カラム名リストを生成する（最初の１回のみ）
        if(csvColumnList == null){
            String[] columnArray = {
                     "事業名"
                    ,"研究計画調書作成日"
                    ,"応募状況"
                    ,"所属研究機関承認日"
                    ,"応募者氏名（漢字等-姓）"
                    ,"応募者氏名（漢字等-名）"
                    ,"応募者氏名（フリガナ-姓）"
                    ,"応募者氏名（フリガナ-名）"
                    ,"年齢"
                    ,"応募者研究者番号"
                    ,"所属研究機関番号"
                    ,"所属研究機関名"
                    ,"部局番号"
                    ,"部局名"
                    ,"職名番号"
                    ,"職名（和文）"
                    ,"郵便番号"
                    ,"住所"
                    ,"TEL"
                    ,"FAX"
                    ,"Eｍail"
 //                   ,"URL"
                    ,"研究課題名(和文）"
                    ,"系等の区分番号"
                    ,"系等の区分"
                    ,"細目番号"
                    ,"分割番号"
                    ,"分野"
                    ,"分科"
                    ,"細目"
                    ,"細目番号2"
                    ,"分野2"
                    ,"分科2"
                    ,"細目2"
                    ,"研究区分"
                    ,"大幅な変更を伴う研究課題"
                    ,"領域番号"
                    ,"研究項目番号"
                    ,"調整班"
                    ,"領域略称名"
                    ,"整理番号"
                    ,"版数"
                    ,"細目表キーワード"
                    ,"細目表以外のキーワード"
                    ,"1年目研究経費"
                    ,"1年目設備備品費"
                    ,"1年目消耗品費"
                    ,"1年目旅費"
                    ,"1年目謝金等"
                    ,"1年目その他"
                    ,"2年目研究経費"
                    ,"2年目設備備品費"
                    ,"2年目消耗品費"
                    ,"2年目旅費"
                    ,"2年目謝金等"
                    ,"2年目その他"
                    ,"3年目研究経費"
                    ,"3年目設備備品費"
                    ,"3年目消耗品費"
                    ,"3年目旅費"
                    ,"3年目謝金等"
                    ,"3年目その他"
                    ,"4年目研究経費"
                    ,"4年目設備備品費"
                    ,"4年目消耗品費"
                    ,"4年目旅費"
                    ,"4年目謝金等"
                    ,"4年目その他"
                    ,"5年目研究経費"
                    ,"5年目設備備品費"
                    ,"5年目消耗品費"
                    ,"5年目旅費"
                    ,"5年目謝金等 "
                    ,"5年目その他"
                    ,"6年目研究経費"
                    ,"6年目設備備品費"
                    ,"6年目消耗品費"
                    ,"6年目旅費"
                    ,"6年目謝金等"
                    ,"6年目その他"
                    ,"総計-研究経費"
                    ,"総計-設備備品費"
                    ,"総計-消耗品費"
                    ,"総計-旅費"
                    ,"総計-謝金等"
                    ,"総計-その他"
                    ,"分担金の有無"
                    ,"開示希望の有無"
                    ,"研究者数"
                    ,"他機関の分担者数"
                    ,"新規継続区分"
                    ,"継続分の研究課題番号"
                    ,"研究計画最終年度前年度の応募"
                    ,"最終年度課題番号"
                    ,"海外分野番号"
                    ,"海外分野名称"
                    ,"海外分野略称"
// 2007/02/14  張志男　追加 ここから
                    ,"審査希望分野コード"
                    ,"審査希望分野名"
// 2007/02/14　張志男　追加 ここまで
                    ,"採用年月日"                  
// 2007/02/14  張志男　修正 ここから
                    //,"勤務時間数"
                    ,"週当たりの勤務時間数"
// 2007/02/14　張志男　修正 ここまで
                    ,"特別研究員奨励費内約額"
// 2007/02/14  張志男　追加 ここから
                    ,"特別研究員奨励費課題番号-年度"
                    ,"特別研究員奨励費課題番号-整理番号"
// 2007/02/14　張志男　追加 ここまで
                    ,"応募資格"
                    ,"資格取得年月日"
                    ,"資格取得前機関名"
                    ,"育休等開始日"
                    ,"育休等終了日"
                    };
            //念のためスレッドセーフ化
            csvColumnList = Collections.synchronizedList(Arrays.asList(columnArray));
        }
        
        //CSVリスト取得（カラム名をキー項目名としない）
        List csvDataList = SelectUtil.selectCsvList(connection, query, false);
        
        //最初の要素にカラム名リストを挿入する
        csvDataList.add(0, csvColumnList);
        csvColumnList = null;
        return csvDataList;
    }   
    //add end ly 2006/07/11
    
    /**
     * 研究組織表CSV出力するリストを返す。
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchKenkyuSoshikiCsvData(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select = 
        "SELECT "
            + " B.SYSTEM_NO               \"システム受付番号\""
            + ",B.SEQ_NO                  \"シーケンス番号\""
            + ",B.JIGYO_ID                \"事業ID\""
            + ",B.BUNTAN_FLG              \"代表者分担者別\""
            + ",B.KENKYU_NO               \"研究者番号\""
            + ",B.NAME_KANJI_SEI          \"氏名（漢字－姓）\""
            + ",B.NAME_KANJI_MEI          \"氏名（漢字－名）\""
            + ",B.NAME_KANA_SEI           \"氏名（フリガナ－姓）\""
            + ",B.NAME_KANA_MEI           \"氏名（フリガナ－名）\""
            + ",B.SHOZOKU_CD              \"所属研究機関名（番号）\""
            + ",B.SHOZOKU_NAME            \"所属研究機関名（和文）\""
            + ",B.BUKYOKU_CD              \"部局名（番号）\""
            + ",B.BUKYOKU_NAME            \"部局名（和文）\""
            + ",B.SHOKUSHU_CD             \"職名（番号）\""
            + ",B.SHOKUSHU_NAME_KANJI     \"職名（和文）\""
            + ",B.SENMON                  \"現在の専門\""
            + ",B.GAKUI                   \"学位\""
            + ",B.BUNTAN                  \"役割分担\""
            + ",B.KEIHI                   \"研究経費\""
            + ",B.EFFORT                  \"エフォート\""
            + ",B.NENREI                  \"年齢\""
            + ",A.KADAI_NAME_KANJI        \"研究課題名（和文）\""        //2005.12.13 iso 追加
            + ",A.JURI_SEIRI_NO           \"整理番号（学創用）\""        //2005.12.13 iso 追加
//          2006.2.10  追加//
//          +",A.OUBO_SHIKAKU"
            + " FROM"
            + "  SHINSEIDATAKANRI" + dbLink + " A"
            + " ,KENKYUSOSHIKIKANRI" + dbLink + " B"
            + " WHERE"
            + "  B.SYSTEM_NO = A.SYSTEM_NO"     //システム受付番号が同じもの
            ;
            
        //検索条件を元にSQL文を生成する。
        String query = getQueryString(select, searchInfo);
            
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }   
    
        //CSVリスト取得（カラム名をキー項目とする）
        List csvDataList = SelectUtil.selectCsvList(connection, query, true);
        return csvDataList;
    }   

    /**
     * 指定検索条件に該当する関連分野情報を返す。
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public Page searchKanrenbunyaList(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException , NoDataFoundException, ApplicationException {

        String select =
        "SELECT "
            + " A.SYSTEM_NO,"                   //システム受付番号
            + " A.UKETUKE_NO,"                  //申請番号
            + " A.JIGYO_ID,"                    //事業ID
            + " A.NENDO,"                       //年度
            + " A.KAISU,"                       //回数
            + " A.JIGYO_NAME,"                  //事業名
            + " A.SHINSEISHA_ID,"               //申請者ID
            + " A.NAME_KANJI_SEI,"              //申請者氏名（漢字等-姓）
            + " A.NAME_KANJI_MEI,"              //申請者氏名（漢字等-名）
            + " A.SHOZOKU_CD,"                  //所属機関コード
            + " A.SHOZOKU_NAME_RYAKU,"          //所属機関名（略称）
            + " A.BUKYOKU_NAME_RYAKU,"          //部局名（略称）
            + " A.SHOKUSHU_NAME_RYAKU,"         //職名（略称）
            + " A.KADAI_NAME_KANJI,"            //研究課題名(和文）
            + " A.KEI_NAME_RYAKU,"              //系統の区分（略称）
            + " A.JURI_SEIRI_NO,"               //整理番号（学創用）　    2005･11･2追加
            + " A.KANREN_SHIMEI1,"              //関連分野の研究者-氏名1
            + " A.KANREN_KIKAN1,"               //関連分野の研究者-所属機関1
            + " A.KANREN_BUKYOKU1,"             //関連分野の研究者-所属部局1
            + " A.KANREN_SHOKU1,"               //関連分野の研究者-職名1
            + " A.KANREN_SENMON1,"              //関連分野の研究者-専門分野1
            + " A.KANREN_TEL1,"                 //関連分野の研究者-勤務先電話番号1
            + " A.KANREN_JITAKUTEL1,"           //関連分野の研究者-自宅電話番号1
            + " A.KANREN_MAIL1,"                //関連分野の研究者-E-Mail1
            + " A.KANREN_SHIMEI2,"              //関連分野の研究者-氏名2
            + " A.KANREN_KIKAN2,"               //関連分野の研究者-所属機関2
            + " A.KANREN_BUKYOKU2,"             //関連分野の研究者-所属部局2
            + " A.KANREN_SHOKU2,"               //関連分野の研究者-職名2
            + " A.KANREN_SENMON2,"              //関連分野の研究者-専門分野2
            + " A.KANREN_TEL2,"                 //関連分野の研究者-勤務先電話番号2
            + " A.KANREN_JITAKUTEL2,"           //関連分野の研究者-自宅電話番号2
            + " A.KANREN_MAIL2,"                //関連分野の研究者-E-Mail2
            + " A.KANREN_SHIMEI3,"              //関連分野の研究者-氏名3
            + " A.KANREN_KIKAN3,"               //関連分野の研究者-所属機関3
            + " A.KANREN_BUKYOKU3,"             //関連分野の研究者-所属部局3
            + " A.KANREN_SHOKU3,"               //関連分野の研究者-職名3
            + " A.KANREN_SENMON3,"              //関連分野の研究者-専門分野3
            + " A.KANREN_TEL3,"                 //関連分野の研究者-勤務先電話番号3
            + " A.KANREN_JITAKUTEL3,"           //関連分野の研究者-自宅電話番号3
            + " A.KANREN_MAIL3,"                //関連分野の研究者-E-Mail3
            + " A.JOKYO_ID,"                    //申請状況ID
            + " A.SAISHINSEI_FLG"               //再申請フラグ
            + " FROM"
            + " SHINSEIDATAKANRI" + dbLink + " A"//申請データ管理テーブル
            + " WHERE"
            + " A.DEL_FLG = 0"                  //削除フラグが[0]
            ;
        
        //検索条件を元にSQL文を生成する。
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //ページ取得
        return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query);
    }

    /**
     * 指定検索条件に該当する関連分野情報(CSV)を返す。
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchKanrenbunyaListCSV(
            Connection connection,
            ShinseiSearchInfo searchInfo)
            throws DataAccessException , NoDataFoundException, ApplicationException {

        String select =
        "SELECT "
            + " A.NENDO,"                       //年度
            + " A.KAISU,"                       //回数
            + " A.JIGYO_NAME,"                  //事業名
            + " A.UKETUKE_NO,"                  //申請番号
            + " A.KEI_NAME,"                    //系統の区分
            + " A.JURI_SEIRI_NO,"               //整理番号（学創用）2005/11/2追加
            + " A.KADAI_NAME_KANJI,"            //研究課題名(和文）
            + " A.NAME_KANJI_SEI,"              //申請者氏名（漢字等-姓）
            + " A.NAME_KANJI_MEI,"              //申請者氏名（漢字等-名）
            + " A.SHOZOKU_CD,"                  //所属機関コード
            + " A.SHOZOKU_NAME,"                //所属機関名     
            + " A.BUKYOKU_NAME,"                //部局名
            + " A.SHOKUSHU_NAME_KANJI,"         //職名
            + " A.KANREN_SHIMEI1,"              //関連分野の研究者-氏名1
            + " A.KANREN_KIKAN1,"               //関連分野の研究者-所属機関1
            + " A.KANREN_BUKYOKU1,"             //関連分野の研究者-所属部局1
            + " A.KANREN_SHOKU1,"               //関連分野の研究者-職名1
            + " A.KANREN_SENMON1,"              //関連分野の研究者-専門分野1
            + " A.KANREN_TEL1,"                 //関連分野の研究者-勤務先電話番号1
            + " A.KANREN_JITAKUTEL1,"           //関連分野の研究者-自宅電話番号1
            + " A.KANREN_MAIL1,"                //関連分野の研究者-E-Mail1
            + " A.KANREN_SHIMEI2,"              //関連分野の研究者-氏名2
            + " A.KANREN_KIKAN2,"               //関連分野の研究者-所属機関2
            + " A.KANREN_BUKYOKU2,"             //関連分野の研究者-所属部局2
            + " A.KANREN_SHOKU2,"               //関連分野の研究者-職名2
            + " A.KANREN_SENMON2,"              //関連分野の研究者-専門分野2
            + " A.KANREN_TEL2,"                 //関連分野の研究者-勤務先電話番号2
            + " A.KANREN_JITAKUTEL2,"           //関連分野の研究者-自宅電話番号2
            + " A.KANREN_MAIL2,"                //関連分野の研究者-E-Mail2
            + " A.KANREN_SHIMEI3,"              //関連分野の研究者-氏名3
            + " A.KANREN_KIKAN3,"               //関連分野の研究者-所属機関3
            + " A.KANREN_BUKYOKU3,"             //関連分野の研究者-所属部局3
            + " A.KANREN_SHOKU3,"               //関連分野の研究者-職名3
            + " A.KANREN_SENMON3,"              //関連分野の研究者-専門分野3
            + " A.KANREN_TEL3,"                 //関連分野の研究者-勤務先電話番号3
            + " A.KANREN_JITAKUTEL3,"           //関連分野の研究者-自宅電話番号3
            + " A.KANREN_MAIL3"                 //関連分野の研究者-E-Mail3
            + " FROM"
            + " SHINSEIDATAKANRI" + dbLink + " A"//申請データ管理テーブル
            + " WHERE"
            + " A.DEL_FLG = 0"                  //削除フラグが[0]
            ;
        
        //検索条件を元にSQL文を生成する。
        String query = getQueryString(select, searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //データリスト取得
        List dataList = SelectUtil.select(connection, query);
        
        //CSVの形式に変換する
        List csvDataList = new ArrayList();
        for(int i=0; i<dataList.size(); i++){
            Map recordMap = (Map)dataList.get(i);
            //関連分野研究者（３人）
            for(int j=1; j<=3; j++){
                List record = new ArrayList();
                record.add(StringUtil.defaultString(recordMap.get("NENDO")));
                record.add(StringUtil.defaultString(recordMap.get("KAISU")));
                record.add(StringUtil.defaultString(recordMap.get("JIGYO_NAME")));
                record.add(StringUtil.defaultString(recordMap.get("UKETUKE_NO")));
                record.add(StringUtil.defaultString(recordMap.get("KEI_NAME")));
                record.add(StringUtil.defaultString(recordMap.get("JURI_SEIRI_NO")));
                record.add(StringUtil.defaultString(recordMap.get("KADAI_NAME_KANJI")));
                record.add(StringUtil.defaultString(recordMap.get("NAME_KANJI_SEI")));
                record.add(StringUtil.defaultString(recordMap.get("NAME_KANJI_MEI")));
                record.add(StringUtil.defaultString(recordMap.get("SHOZOKU_CD")));
                record.add(StringUtil.defaultString(recordMap.get("SHOZOKU_NAME")));
                record.add(StringUtil.defaultString(recordMap.get("BUKYOKU_NAME")));
                record.add(StringUtil.defaultString(recordMap.get("SHOKUSHU_NAME_KANJI")));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_SHIMEI"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_KIKAN"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_BUKYOKU"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_SHOKU"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_SENMON"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_TEL"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_JITAKUTEL"+j)));
                record.add(StringUtil.defaultString(recordMap.get("KANREN_MAIL"+j)));
                csvDataList.add(record);
            }
        }
        
        //カラム名リストを生成する（最初の１回のみ）
        //※指定文字列はSQLの識別子長を超えてしまう可能性があるため別にセットする。    
        //2005.10.04 iso 申請→応募、所属機関→所属研究機関、コード→番号、申請書→研究計画調書    
        if(csvColumnList4Kanrenbunya == null){
            String[] columnArray = {
                                    "年度",
                                    "回数",
                                    "事業名",
                                    "応募番号",
                                    "系統の区分",
                                    "整理番号（学創用）",
                                    "研究課題名(和文）",
                                    "応募者氏名（漢字等-姓）",
                                    "応募者氏名（漢字等-名）",
                                    "所属研究機関番号",
                                    "所属研究機関名",
                                    "部局名",
                                    "職名（和文）",
                                    "関連分野の研究者-氏名1～3",
                                    "関連分野の研究者-所属研究機関1～3",
                                    "関連分野の研究者-所属部局1～3",
                                    "関連分野の研究者-職名1～3",
                                    "関連分野の研究者-専門分野1～3",
                                    "関連分野の研究者-勤務先電話番号1～3",
                                    "関連分野の研究者-自宅電話番号1～3",
                                    "関連分野の研究者-Email1～3"
                                    };
            //念のためスレッドセーフ化
            csvColumnList4Kanrenbunya = Collections.synchronizedList(Arrays.asList(columnArray));
        }
        
        //最初の要素にカラム名リストを挿入する
        csvDataList.add(0, csvColumnList4Kanrenbunya);
        return csvDataList;
    }

    /**
     * 審査依頼発行通知時のステータス更新メソッド。
     * 審査員割り振り処理後[08]の申請状況を審査中[10]に更新する。
     * 既に審査中[10]のもの、または審査員割り振り処理後[08]以外のもの
     * に対しては更新処理を行わない。（申請状況はそのままとなる。）
     * @param connection
     * @param shinseiDataPk
     * @throws DataAccessException
     * @throws ApplicationException
     */
    public void updateStatusForShinsaIraiIssue(
            Connection connection,
            ShinseiDataPk[] shinseiDataPk)
            throws DataAccessException , ApplicationException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, shinseiDataPk);
        
        //-----排他制御-----
        String select = 
            "SELECT * FROM SHINSEIDATAKANRI"+dbLink
                + " WHERE"
                + " SYSTEM_NO IN ("+ getQuestionMark(shinseiDataPk.length) +")"
                + " FOR UPDATE"
                ;

        PreparedStatement preparedStatement = null;
        ResultSet         resultSet         = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(select);
            int index = 1;
            for(int i=0; i<shinseiDataPk.length; i++){
                DatabaseUtil.setParameter(preparedStatement, index++, shinseiDataPk[i].getSystemNo());
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報排他制御中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(resultSet, preparedStatement);
        }
            
        //-----DB更新-----
        String query =
            "UPDATE SHINSEIDATAKANRI"+dbLink
                + " SET"
                + " JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "' "      //申請状況ID                
                + " WHERE"
                + " SYSTEM_NO IN ("+ getQuestionMark(shinseiDataPk.length) +")"
                + " AND"
                + " JOKYO_ID = '" + StatusCode.STATUS_GAKUSIN_JYURI + "' "  //申請状況ID[06]の場合のみ
                ;

        PreparedStatement preparedStatement2 = null;
        try {
            //登録
            preparedStatement2 = connection.prepareStatement(query);
            int index = 1;
            for(int i=0; i<shinseiDataPk.length; i++){
                DatabaseUtil.setParameter(preparedStatement2, index++, shinseiDataPk[i].getSystemNo());
            }
            preparedStatement2.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報ステータス更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement2);
        }
    }

    /**
     * ローカルに存在する該当レコードの内容をDBLink先のテーブルに挿入する。
     * DBLink先に同じレコードがある場合は、予め削除しておくこと。
     * DBLinkが設定されていない場合はエラーとなる。
     * @param connection
     * @param jigyoId
     * @throws DataAccessException
     */
    public void copy2HokanDB(
            Connection connection,
            String     jigyoId)
            throws DataAccessException {

        //DBLink名がセットされていない場合
        if(dbLink == null || dbLink.length() == 0){
            throw new DataAccessException("DBリンク名が設定されていません。DBLink="+dbLink);
        }
        
        String query =
            "INSERT INTO SHINSEIDATAKANRI"+dbLink
                + " SELECT * FROM SHINSEIDATAKANRI WHERE JIGYO_ID = ?";
        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);     //検索条件（事業ID）
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("申請データ管理テーブル保管中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * 指定事業の申請データ件数を返す。
     * @param connection
     * @param jigyoId
     * @return
     * @throws DataAccessException
     */
    public int countShinseiDataByJigyoID(
            Connection connection,
            String     jigyoId)
            throws DataAccessException {

        String query =
            "SELECT COUNT(SYSTEM_NO) FROM SHINSEIDATAKANRI"+dbLink
                + " WHERE"
                + " JIGYO_ID = ?"   //指定事業IDのもの
                ;
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            //検索
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,jigyoId);
            recordSet = preparedStatement.executeQuery();
            int count = 0;
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            return count;
        } catch (SQLException ex) {
            throw new DataAccessException("申請データ管理テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

    /**
     * 事業ID＋申請番号（機関整理番号）の申請データ件数を返す。
     * ※科研における一意データの件数（本来ならば１件になるはず）
     * @param connection
     * @param jigyoId
     * @param uketukeNo
     * @return
     * @throws DataAccessException
     */
    public int countShinseiData(
            Connection connection,
            String     jigyoId,
            String     uketukeNo)
            throws DataAccessException {

        String query =
            "SELECT COUNT(SYSTEM_NO) FROM SHINSEIDATAKANRI" + dbLink
                + " WHERE"
                + " JIGYO_ID = ?"   //指定事業IDのもの
                + " AND"
                + " UKETUKE_NO = ?" //指定申請番号のもの
                ;
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            //検索
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,jigyoId);
            DatabaseUtil.setParameter(preparedStatement,index++,uketukeNo);
            recordSet = preparedStatement.executeQuery();
            int count = 0;
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            return count;
        } catch (SQLException ex) {
            throw new DataAccessException("申請データ管理テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }   

    //---------------------------------------------------------------------
    // Private Methods
    //--------------------------------------------------------------------- 
    /**
     * 指定PreparedStatementに申請データすべてのパラメータをセットする。
     * SQLステートメントには全てのパラメータを指定可能にしておくこと。
     * @param preparedStatement
     * @param dataInfo
     * @return index          次のパラメータインデックス
     * @throws SQLException
     */
    private int setAllParameter(PreparedStatement preparedStatement, 
                                   ShinseiDataInfo dataInfo)
            throws SQLException {

        int index = 1;
        //---基本情報（前半）
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSystemNo());         // システム受付番号
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getUketukeNo());        // 申請番号
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJigyoId());          // 事業ID
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getNendo());            // 年度
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaisu());            // 回数
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJigyoName());        // 事業名
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinseishaId());     // 申請者ID
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSakuseiDate());      // 申請書作成日
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShoninDate());       // 所属期間承認日
// 2006/07/26 dyh add start 理由：DBで領域代表者確定日追加
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getRyoikiKakuteiDate());// 領域代表者確定日
// 2006/07/26 dyh add end
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJyuriDate());        // 学振受理日

        //---申請者（研究代表者）
        DaihyouInfo daihyouInfo = dataInfo.getDaihyouInfo();
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameKanjiSei());     // 申請者氏名（漢字等-姓）
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameKanjiMei());     // 申請者氏名（漢字等-名）
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameKanaSei());      // 申請者氏名（カナ-姓）
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameKanaMei());      // 申請者氏名（カナ-名）
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameRoSei());        // 申請者氏名（ローマ字-姓）
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNameRoMei());        // 申請者氏名（ローマ字-名）
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getNenrei());           // 年齢
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getKenkyuNo());         // 申請者研究者番号
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShozokuCd());        // 所属機関コード
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShozokuName());      // 所属機関名
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShozokuNameRyaku()); // 所属機関名（略称）
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getBukyokuCd());        // 部局コード
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getBukyokuName());      // 部局名
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getBukyokuNameRyaku()); // 部局名（略称）
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShokushuCd());       // 職名コード
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShokushuNameKanji());// 職名（和文）
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getShokushuNameRyaku());// 職名（略称）
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getZip());              // 郵便番号
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getAddress());          // 住所
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getTel());              // TEL
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getFax());              // FAX
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getEmail());            // E-mail
//2006/06/30 苗　追加ここから
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getUrl());              // URL
//2006/06/30　苗　追加ここまで        
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getSenmon());           // 現在の専門
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getGakui());            // 学位
        DatabaseUtil.setParameter(preparedStatement,index++, daihyouInfo.getBuntan());           // 役割分担

        //---研究課題
        KadaiInfo kadaiInfo = dataInfo.getKadaiInfo();
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKadaiNameKanji());     // 研究課題名(和文）
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKadaiNameEigo());      // 研究課題名(英文）
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getJigyoKubun());         // 事業区分
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getShinsaKubun());        // 審査区分
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getShinsaKubunMeisho());  // 審査区分名称
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunkatsuNo());         // 分割番号
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunkatsuNoMeisho());   // 分割番号名称
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKenkyuTaisho());       // 研究対象の類型
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKeiNameNo());          // 系統の区分番号
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKeiName());            // 系統の区分
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKeiNameRyaku());       // 系統の区分（略称）
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunkaSaimokuCd());     // 細目番号
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunya());              // 分野
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunka());              // 分科
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getSaimokuName());        // 細目
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunkaSaimokuCd2());    // 細目番号2
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunya2());             // 分野2
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getBunka2());             // 分科2
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getSaimokuName2());       // 細目2
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKantenNo());           // 推薦の観点番号
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKanten());             // 推薦の観点
        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getKantenRyaku());        // 推薦の観点略称
        
//      2005/04/13 追加 ここから----------
//      理由:分割番号追加のため

        DatabaseUtil.setParameter(preparedStatement,index++, kadaiInfo.getEdition());            // 版
        
//      2005/04/13 追加 ここまで----------
        
        //---研究経費
        KenkyuKeihiSoukeiInfo soukeiInfo = dataInfo.getKenkyuKeihiSoukeiInfo();
//2006/07/03 苗　修正ここから
//        KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi();
//        for(int j=0; j<keihiInfo.length; j++){
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getKeihi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getBihinhi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getShomohinhi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getKokunairyohi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getGaikokuryohi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getRyohi());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getShakin());
//            DatabaseUtil.setParameter(preparedStatement,index++, keihiInfo[j].getSonota());
//        }
        if(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(dataInfo.getJigyoCd())){
            KenkyuKeihiInfo[] keihiInfo6 = soukeiInfo.getKenkyuKeihi6();
            for (int j = 0; j < keihiInfo6.length; j++) {
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getKeihi());// 研究経費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getBihinhi());// 設備備品費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getShomohinhi());// 消耗品費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getKokunairyohi());// 国内旅費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getGaikokuryohi());// 外国旅費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getRyohi());// 旅費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getShakin());// 謝金等
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo6[j].getSonota());// その他
          }
        } else {
            KenkyuKeihiInfo[] keihiInfo = soukeiInfo.getKenkyuKeihi();
            for (int j = 0; j < keihiInfo.length; j++) {
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getKeihi());// 研究経費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getBihinhi());// 設備備品費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getShomohinhi());// 消耗品費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getKokunairyohi());// 国内旅費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getGaikokuryohi());// 外国旅費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getRyohi());// 旅費
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getShakin());// 謝金等
                DatabaseUtil.setParameter(preparedStatement, index++, keihiInfo[j].getSonota());// その他
            }
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
            DatabaseUtil.setParameter(preparedStatement, index++, 0);// 
        }
//2006/07/03　苗　修正ここまで        
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getKeihiTotal());// 総計-研究経費
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getBihinhiTotal());// 総計-設備備品費
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getShomohinhiTotal());// 総計-消耗品費
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getKokunairyohiTotal());// 総計-国内旅費
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getGaikokuryohiTotal());// 総計-外国旅費
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getRyohiTotal());// 総計-旅費
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getShakinTotal());// 総計-謝金等
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getSonotaTotal());// 総計-その他

        //---基本情報（中盤）
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSoshikiKeitaiNo());     // 研究組織の形態番号
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSoshikiKeitai());       // 研究組織の形態
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getBuntankinFlg());        // 分担金の有無
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKoyohi());              // 研究支援者雇用経費
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKenkyuNinzu());         // 研究者数
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getTakikanNinzu());        // 他機関の分担者数
        //2005/03/31 追加 ----------------------------------------------------ここから
        //理由 研究協力者情報の入力欄を追加したため
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKyoryokushaNinzu());    // 研究協力者数
        //2005/03/31 追加 ----------------------------------------------------ここまで
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinseiKubun());        // 新規継続区分
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKadaiNoKeizoku());      // 継続分の研究課題番号
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinseiFlgNo());        // 研究計画最終年度前年度の応募
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinseiFlg());          // 申請の有無
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKadaiNoSaisyu());       // 最終年度課題番号
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaijikiboFlgNo());      // 開示希望の有無番号
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaijiKiboFlg());        // 開示希望の有無
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaigaibunyaCd());       // 海外分野コード
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaigaibunyaName());     // 海外分野名称
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKaigaibunyaNameRyaku());// 海外分野名称略称

        //---関連分野の研究者
        KanrenBunyaKenkyushaInfo[] kanrenInfo = dataInfo.getKanrenBunyaKenkyushaInfo();
        for(int j=0; j<kanrenInfo.length; j++){
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenShimei());   // 関連分野の研究者-氏名
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenKikan());    // 関連分野の研究者-所属機関
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenBukyoku());  // 関連分野の研究者-所属部局
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenShoku());    // 関連分野の研究者-職名
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenSenmon());   // 関連分野の研究者-専門分野
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenTel());      // 関連分野の研究者-勤務先電話番号
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenJitakuTel());// 関連分野の研究者-自宅電話番号
            DatabaseUtil.setParameter(preparedStatement,index++, kanrenInfo[j].getKanrenMail());     // 関連分野の研究者-Email
        }

        //---基本情報（後半）
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getXmlPath());        // XMLの格納パス
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getPdfPath());        // PDFの格納パス
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJuriKekka());      // 受理結果
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJuriBiko());       // 受理結果備考
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSeiriNo());        // 受理整理番号
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSuisenshoPath());  // 推薦書の格納パス
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKekka1Abc());      // １次審査結果(ABC)
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKekka1Ten());      // １次審査結果(点数)
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKekka1TenSorted());// １次審査結果（点数順）
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinsa1Biko());    // １次審査備考
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKekka2());         // ２次審査結果
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSouKehi());        // 総経費（学振入力）
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShonenKehi());     // 初年度経費（学振入力）
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinsa2Biko());    // 業務担当者記入欄
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getJokyoId());        // 申請状況ID
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSaishinseiFlg());  // 再申請フラグ
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getDelFlg());         // 削除フラグ

// 20050530 Start 特定領域
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKenkyuKubun());    // 計画研究・公募研究・終了研究領域区分
        if(IShinseiMaintenance.CHECK_ON.equals(dataInfo.getChangeFlg())){
            DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_ON);
        }else{
            DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_OFF);
        }
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getRyouikiNo());       // 領域番号
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getRyouikiRyakuName());// 領域略称名
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getRyouikiKoumokuNo());// 研究項目番号
//2006/06/16 苗　修正ここから        
//      if(IShinseiMaintenance.CHECK_ON.equals(dataInfo.getChouseiFlg())){
//          DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_ON);}
//      else{DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_OFF);}
        if(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(dataInfo.getJigyoCd())){
            if(StringUtil.isBlank(dataInfo.getChouseiFlg())){
                DatabaseUtil.setParameter(preparedStatement,index++, "0");// 
            } else if (IShinseiMaintenance.CHECK_ON.equals(dataInfo.getChouseiFlg())){
                DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_ON);
            } else {
                DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_OFF);
            }
        }else {
            if(IShinseiMaintenance.CHECK_ON.equals(dataInfo.getChouseiFlg())){
                DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_ON);
            } else {
                DatabaseUtil.setParameter(preparedStatement,index++, IShinseiMaintenance.CHECK_OFF);
            }
        }
//2006/06/16　苗　修正ここまで        
// Horikoshi End
        
// 20050713 キーワード追加
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKigou());           // 記号
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKeyName());         // キーワード(名称)
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKeyOtherName());    // 細目表以外のキーワード(名称)
// Horikoshi

// 20050803 会議費、印刷費の追加
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getMeetingCost());   // 会議費
        DatabaseUtil.setParameter(preparedStatement,index++, soukeiInfo.getPrintingCost());  // 印刷費
// Horikoshi
        
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSaiyoDate());       //採用年月日
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getKinmuHour());       //勤務時間数
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getNaiyakugaku());     //特別研究員奨励費内約額
//2007/02/02 苗　追加ここから
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShoreihiNoNendo());  //特別研究員奨励費課題番号-年度
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShoreihiNoSeiri());  //特別研究員奨励費課題番号-整理番号
//2007/02/02　苗　追加ここまで   
//2006/02/13 Start
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getOuboShikaku());     //応募資格
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSikakuDate());      //資格取得年月日
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getSyuTokumaeKikan()); //資格取得前機関名
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getIkukyuStartDate()); //育休等開始日
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getIkukyuEndDate());   //育休等終了日
// Nae End  

//2006/02/15
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinsaRyoikiCd());  //審査領域コード
        DatabaseUtil.setParameter(preparedStatement,index++, dataInfo.getShinsaRyoikiName());//審査領域名称
// syuu End
        
// ADD START 2007-07-26 BIS 王志安
        String naiyaku1 = dataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[0].getNaiyaku();
        String naiyaku2 = dataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[1].getNaiyaku();
        String naiyaku3 = dataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[2].getNaiyaku();
        String naiyaku4 = dataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[3].getNaiyaku();
        String naiyaku5 = dataInfo.getKenkyuKeihiSoukeiInfo().getKenkyuKeihi()[4].getNaiyaku();
        
        naiyaku1 = (naiyaku1 == null || "".equals(naiyaku1)) ? "0" : naiyaku1;
        naiyaku2 = (naiyaku2 == null || "".equals(naiyaku2)) ? "0" : naiyaku2;
        naiyaku3 = (naiyaku3 == null || "".equals(naiyaku3)) ? "0" : naiyaku3;
        naiyaku4 = (naiyaku4 == null || "".equals(naiyaku4)) ? "0" : naiyaku4;
        naiyaku5 = (naiyaku5 == null || "".equals(naiyaku5)) ? "0" : naiyaku5;
        
        DatabaseUtil.setParameter(preparedStatement,index++, naiyaku1);//1年目内約額
        DatabaseUtil.setParameter(preparedStatement,index++, naiyaku2);//2年目内約額
        DatabaseUtil.setParameter(preparedStatement,index++, naiyaku3);//3年目内約額
        DatabaseUtil.setParameter(preparedStatement,index++, naiyaku4);//4年目内約額
        DatabaseUtil.setParameter(preparedStatement,index++, naiyaku5);//5年目内約額
// ADD END 2007-07-26 BIS 王志安
        return index;
    }

    /**
     * 
     * @param count
     * @return String
     */
    private String getQuestionMark(int count){
        StringBuffer buf = new StringBuffer("?");
        for(int i=1; i<count; i++){
            buf.append(",?");
        }
        return buf.toString();
    }

    /**
     * 
     * @param array
     * @return
     */
    private static String changeArray2CSV(String[] array){
        return StringUtil.changeArray2CSV(array, true);
    }

    /**
     * 
     * @param array
     * @return
     */
    private static String changeIterator2CSV(Iterator ite){
        return StringUtil.changeIterator2CSV(ite, true);
    }   

    /**
     * 
     * @param array
     * @return
     */
    private static String changeArray2CSV(int[] array){
        String[] strArray = new String[array.length];
        for(int i=0; i<strArray.length; i++){
            strArray[i] = String.valueOf(array[i]);
        }
        return changeArray2CSV(strArray);
    }

    /**
     * 申請検索条件オブジェクトから検索条件を取得しSQLの問い合わせ部分を生成する。
     * 生成した問い合わせ部分は第一引数の文字列の後ろに結合される。
     * @param select      
     * @param searchInfo
     * @return
     */
    protected static String getQueryString(String select, ShinseiSearchInfo searchInfo) {

        //-----検索条件オブジェクトの内容をSQLに結合していく-----
        StringBuffer query = new StringBuffer(select);
        //システム受付番号
        if(searchInfo.getSystemNo() != null && searchInfo.getSystemNo().length() != 0){
            query.append(" AND A.SYSTEM_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getSystemNo()))
                 .append("'");
        }
        //申請番号
        if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
            query.append(" AND A.UKETUKE_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
                 .append("'");
        }
        //事業ID
        if(searchInfo.getJigyoId() != null && searchInfo.getJigyoId().length() != 0){
            query.append(" AND A.JIGYO_ID = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoId()))
                 .append("'");          
        }
        //事業CD（事業IDの3文字目から5文字分）
        if(searchInfo.getJigyoCd() != null && searchInfo.getJigyoCd().length() != 0){
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoCd()))
                 .append("'");          
        }
        //担当事業CD（事業IDの3文字目から5文字分）
        if(searchInfo.getTantoJigyoCd() != null && searchInfo.getTantoJigyoCd().size() != 0){
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
                 .append(changeIterator2CSV(searchInfo.getTantoJigyoCd().iterator()))
                 .append(")");
        }
        //事業名
        if(searchInfo.getJigyoName() != null && searchInfo.getJigyoName().length() != 0){
            query.append(" AND A.JIGYO_NAME = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoName()))
                 .append("'");          
        }
        //年度
        if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
            query.append(" AND A.NENDO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getNendo()))
                 .append("'");          
        }       
        //回数
        if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))
                 .append("'");          
        }       
        //申請者ID
        if(searchInfo.getShinseishaId() != null && searchInfo.getShinseishaId().length() != 0){
            query.append(" AND A.SHINSEISHA_ID = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShinseishaId()))
                 .append("'");          
        }       
        //申請者名（漢字：姓）（部分一致）
        if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
            query.append(" AND A.NAME_KANJI_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
                 .append("%'");         
        }               
        //申請者名（漢字：名）（部分一致）
        if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
            query.append(" AND A.NAME_KANJI_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
                 .append("%'");         
        }
        //申請者名（カナ：姓）（部分一致）
        if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
            query.append(" AND A.NAME_KANA_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
                 .append("%'");         
        }                       
        //申請者名（カナ：名）（部分一致）
        if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
            query.append(" AND A.NAME_KANA_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
                 .append("%'");         
        }                       
        //申請者名（ローマ字：姓）（部分一致）
        if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
                 .append("%'");         
        }                       
        //申請者名（ローマ字：名）（部分一致）
        if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
                 .append("%'");         
        }
        //申請者研究者番号
        if(searchInfo.getKenkyuNo() != null && searchInfo.getKenkyuNo().length() != 0){
            query.append(" AND A.KENKYU_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKenkyuNo()))
                 .append("'");          
        }
        //事業区分
        if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().size() != 0){
            query.append(" AND A.JIGYO_KUBUN IN (")
                 .append(changeIterator2CSV(searchInfo.getJigyoKubun().iterator()))
                 .append(")");
        }
        //所属機関コード
        if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
            query.append(" AND A.SHOZOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))
                 .append("'");          
        }
        //2006/06/02 jzx　add start
        //所属研究機関名(名称)
        if(searchInfo.getShozokNm() != null && searchInfo.getShozokNm().length() != 0){
            query.append("AND A.SHOZOKU_NAME_RYAKU like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokNm()))
                 .append("%' ");            
        }   
        //2006/06/02 jzx　add end
        //系統の区分番号
        if(searchInfo.getKeiNameNo() != null && searchInfo.getKeiNameNo().length() != 0){
            query.append(" AND A.KEI_NAME_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKeiNameNo()))
                 .append("'");          
        }       
        //系統の区分または系統の区分（略称）
        if(searchInfo.getKeiName() != null && searchInfo.getKeiName().length() != 0){
            String tmp = EscapeUtil.toSqlString(searchInfo.getKeiName());
            query.append(" AND (A.KEI_NAME like '%")
                 .append(tmp)
                 .append("%'")
                 .append(" OR A.KEI_NAME_RYAKU like '%")
                 .append(tmp)
                 .append("%')");            
        }
        //細目番号1または細目番号2
        if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
            String tmp = EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd());
            query.append(" AND (A.BUNKASAIMOKU_CD = '")
                 .append(tmp)
                 .append("'")
                 .append(" OR A.BUNKASAIMOKU_CD2 = '")
                 .append(tmp)
                 .append("')");         
        }               
        //推薦の観点番号
        if(searchInfo.getKantenNo() != null && searchInfo.getKantenNo().length() != 0){
            query.append(" AND A.KANTEN_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKantenNo()))
                 .append("'");          
        }               
        //推薦の観点
        if(searchInfo.getKanten() != null && searchInfo.getKanten().length() != 0){
            query.append(" AND A.KANTEN = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKanten()))
                 .append("'");          
        }                       
        //推薦の観点略称
        if(searchInfo.getKantenRyaku() != null && searchInfo.getKantenRyaku().length() != 0){
            query.append(" AND A.KANTEN_RYAKU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKantenRyaku()))
                 .append("'");          
        }                       
        //関連分野の研究者氏名1～3のいずれか（部分一致）
        if(searchInfo.getKanrenShimei() != null && searchInfo.getKanrenShimei().length() != 0){
            String tmp = EscapeUtil.toSqlString(searchInfo.getKanrenShimei());
            query.append(" AND (A.KANREN_SHIMEI1 like '%")
                 .append(tmp)
                 .append("%'")
                 .append(" OR A.KANREN_SHIMEI2 like '%")
                 .append(tmp)
                 .append("%'")
                 .append(" OR A.KANREN_SHIMEI3 like '%")
                 .append(tmp)
                 .append("%')");            
        }                       
        //申請状況
        if(searchInfo.getJokyoId() != null && searchInfo.getJokyoId().length != 0){
            query.append(" AND A.JOKYO_ID IN (")
                 .append(changeArray2CSV(searchInfo.getJokyoId()))
                 .append(")");          
        }
        //再申請フラグ
        if(searchInfo.getSaishinseiFlg() != null && searchInfo.getSaishinseiFlg().length != 0){
            query.append(" AND A.SAISHINSEI_FLG IN (")
                 .append(changeArray2CSV(searchInfo.getSaishinseiFlg()))
                 .append(")");          
        }                       
        //2次審査結果
        if(searchInfo.getKekka2() != null && searchInfo.getKekka2().length != 0){
            query.append(" AND A.KEKKA2 IN (")
                 .append(changeArray2CSV(searchInfo.getKekka2()))
                 .append(")");          
        }
        //作成日（From）
        if(searchInfo.getSakuseiDateFrom() != null && searchInfo.getSakuseiDateFrom().length() != 0){
            query.append(" AND A.SAKUSEI_DATE >= TO_DATE('")
                 .append(EscapeUtil.toSqlString(searchInfo.getSakuseiDateFrom()))
                 .append("', 'YYYY/MM/DD') ");          
        }               
        //作成日（To）
        if(searchInfo.getSakuseiDateTo() != null && searchInfo.getSakuseiDateTo().length() != 0){
            query.append(" AND A.SAKUSEI_DATE <= TO_DATE('")
                 .append(EscapeUtil.toSqlString(searchInfo.getSakuseiDateTo()))
                 .append("', 'YYYY/MM/DD') ");          
        }                       
        //所属機関承認日（From）
        if(searchInfo.getShoninDateFrom() != null && searchInfo.getShoninDateFrom().length() != 0){
            query.append(" AND A.SHONIN_DATE >= TO_DATE('")
                 .append(EscapeUtil.toSqlString(searchInfo.getShoninDateFrom()))
                 .append("', 'YYYY/MM/DD') ");          
        }                       
        //所属機関承認日（To）
        if(searchInfo.getShoninDateTo() != null && searchInfo.getShoninDateTo().length() != 0){
            query.append(" AND A.SHONIN_DATE <= TO_DATE('")
                 .append(EscapeUtil.toSqlString(searchInfo.getShoninDateTo()))
                 .append("', 'YYYY/MM/DD') ");          
        }
        //部局コード
        if(searchInfo.getBukyokuCd() != null && searchInfo.getBukyokuCd().length() != 0){
            query.append(" AND A.BUKYOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBukyokuCd()))
                 .append("'");          
        }           
        //組み合わせステータス状況
        if(searchInfo.getStatusSearchInfo() != null && searchInfo.getStatusSearchInfo().hasQuery()){
            query.append(" AND")
                 .append(searchInfo.getStatusSearchInfo().getQuery());
        }
        //削除フラグ
        if(searchInfo.getDelFlg() != null && searchInfo.getDelFlg().length != 0){
            query.append(" AND A.DEL_FLG IN (")
                 .append(changeArray2CSV(searchInfo.getDelFlg()))
                 .append(")");      
        }       

        //整理番号　2005/07/25
        if(searchInfo.getSeiriNo() != null && searchInfo.getSeiriNo().length() != 0){
            query.append(" AND A.JURI_SEIRI_NO LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()))
                 .append("%'");         
        }           

        //2005/9/2 検索条件追加
        //新規・継続
        if (searchInfo.getShinseiKubun() != null && searchInfo.getShinseiKubun().length() != 0){
            if (!"3".equals(searchInfo.getShinseiKubun()) ){
                query.append(" AND A.SHINSEI_KUBUN = '")
                     .append(EscapeUtil.toSqlString(searchInfo.getShinseiKubun()))
                     .append("'");
            }
            //大幅な変更の場合
            else{
                query.append(" AND A.OHABAHENKO = 1");
            }
        }
        //分割番号（基盤等）
        if (searchInfo.getBunkatsuNo() != null && searchInfo.getBunkatsuNo().length() != 0){
            query.append(" AND A.BUNKATSU_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBunkatsuNo()))
                 .append("'");
        }
        //最終年度前年度応募
        if (searchInfo.getShinseiFlgNo() != null && searchInfo.getShinseiFlgNo().length() != 0){
            query.append(" AND A.SHINSEI_FLG_NO = ")
                 .append(EscapeUtil.toSqlString(searchInfo.getShinseiFlgNo()))
                 ;
        }
        //計画研究・公募研究・終了研究領域区分（特定）
        if (searchInfo.getKenkyuKubun() != null && searchInfo.getKenkyuKubun().length() != 0){
            query.append(" AND A.KENKYU_KUBUN = ")
                 .append(EscapeUtil.toSqlString(searchInfo.getKenkyuKubun()))
                 ;
        }
        //計画研究のうち調整班（特定）
        if (searchInfo.getChouseiFlg() != null && searchInfo.getChouseiFlg().length() != 0){
            query.append(" AND A.CHOSEIHAN = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getChouseiFlg()))
                 .append("'");
        }
        //領域番号（特定）
        if (searchInfo.getRyouikiNo() != null && searchInfo.getRyouikiNo().length() != 0){
            query.append(" AND A.RYOIKI_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getRyouikiNo()))
                 .append("'");
        }
        //研究項目番号（特定）
        if (searchInfo.getRyouikiKoumokuNo() != null && searchInfo.getRyouikiKoumokuNo().length() != 0){
            query.append(" AND A.KOMOKU_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getRyouikiKoumokuNo()))
                 .append("'");
        }
        //分担金の配分有無
        if (searchInfo.getBuntankinFlg() != null && searchInfo.getBuntankinFlg().length() != 0){
            query.append(" AND A.BUNTANKIN_FLG = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBuntankinFlg()))
                 .append("'");
        }
        //開示希望の有無
        if (searchInfo.getKaijiKiboFlg() != null && searchInfo.getKaijiKiboFlg().length() != 0){
            query.append(" AND A.KAIJIKIBO_FLG_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaijiKiboFlg()))
                 .append("'");
        }
        
        //-----整列順序を指定する-----
        if(searchInfo.getOrder() != null && searchInfo.getOrder().length() != 0){
            query.append(" ORDER BY ")
                 .append(searchInfo.getOrder());
        }
        
        return query.toString();
    }

    /**
     * 申請データキー情報より対象となるIODファイルを取得する。
     * @param connection            コネクション
     * @param pkInfo                申請データキー情報
     * @return List                 変換するIOFファイルリスト
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List getIodFilesToMerge(Connection connection, final ShinseiDataPk pkInfo)
            throws NoDataFoundException, DataAccessException {
    
        //1.ファイル取得用SQL分
        final StringBuffer query = new StringBuffer();
        query.append("SELECT 0 SEQ_NUM,0 SEQ_TENPU,D.PDF_PATH IOD_FILE_PATH FROM SHINSEIDATAKANRI"+ dbLink + " D WHERE D.SYSTEM_NO = ? ");
        query.append("UNION ALL ");
        //★変換されていない添付ファイルを無視するとき。
        //query.append("SELECT 1 SEQ_NUM,A.SEQ_TENPU SEQ_TENPU,A.PDF_PATH IOD_FILE_PATH FROM TENPUFILEINFO" + dbLink + " A WHERE A.SYSTEM_NO = ? AND PDF_PATH IS NOT NULL ");
        query.append("SELECT 1 SEQ_NUM,A.SEQ_TENPU SEQ_TENPU,A.PDF_PATH IOD_FILE_PATH FROM TENPUFILEINFO" + dbLink + " A WHERE A.SYSTEM_NO = ? ");
        query.append("ORDER BY SEQ_NUM,SEQ_TENPU");

        if(log.isDebugEnabled()){
            log.debug("申請データキー情報 '" + pkInfo);
        }
        
        //2.検索用SQL作成オブジェクトを生成する。        
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                throws SQLException {
                PreparedStatement statement = conn.prepareStatement(query.toString());
                int i = 1;
                DatabaseUtil.setParameter(statement,i++,pkInfo.getSystemNo());
                DatabaseUtil.setParameter(statement,i++,pkInfo.getSystemNo());
                //システム番号
                return statement;
            }
        };
        //3.申請データ中間ファイル・添付ファイル中間ファイルを取得する。  
        final List iodFile = new ArrayList();
        
        //4.検索を実行する。
        RowCallbackHandler handler = new BaseCallbackHandler() {
            protected void processRow(ResultSet rs, int rowNum)
                throws SQLException, NoDataFoundException {
                String iodFilePath = rs.getString("IOD_FILE_PATH");
                if (iodFilePath == null || "".equals(iodFilePath)) {
                    throw new NoDataFoundException("申請データIODファイル情報が見つかりませんでした。検索キー：システム受付番号'"
                    + pkInfo.getSystemNo()
                    + "'");
                } else {
                    iodFile.add(new File(iodFilePath));
                }
            }
        };
        new JDBCReading().query(connection,creator, handler);
        
        if (log.isDebugEnabled()) {
            for (Iterator iter = iodFile.iterator(); iter.hasNext();) {
                log.debug("結合対象ファイル:" + iter.next());
            }
        }
        
        //5.結合ファイルをチェックする。
        if(iodFile.isEmpty()){
            throw new NoDataFoundException("結合するファイルが見つかりませんでした。");
        }
        return iodFile;
    }

    /**
     * 申請データ情報のPDF,XMLファイルパスを更新する。
     * @param connection            コネクション
     * @param pkInfo                主キー情報
     * @param iodFile               PDFファイル
     * @param xmlFile               XMLファイル
     * @throws DataAccessException  更新処理中の例外。
     * @throws NoDataFoundException　処理対象データがみつからないとき。
     */
    public void updateFilePath(
            Connection connection,
            final ShinseiDataPk pkInfo,
            File iodFile,
            File xmlFile)
            throws DataAccessException, NoDataFoundException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, pkInfo);
        
        String updateQuery =
                "UPDATE SHINSEIDATAKANRI"+ dbLink 
                    + " SET"
                    + " PDF_PATH = ?,XML_PATH = ?"
                    + " WHERE SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(updateQuery);
            int i = 1;
            //PDFファイルパス。
            DatabaseUtil.setParameter(preparedStatement,i++,iodFile.getAbsolutePath());
            //XMLファイルパス。
            DatabaseUtil.setParameter(preparedStatement,i++,xmlFile.getAbsolutePath());
            //システム受付番号
            DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getSystemNo());
            DatabaseUtil.executeUpdate(preparedStatement);

        } catch (SQLException ex) {
            throw new DataAccessException(
                "申請情報PDFファイルパス更新中に例外が発生しました。 ：システム受付番号'"
                    + pkInfo.getSystemNo()
                    + "'",
                ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }   

    /**
     * 申請データ情報の推薦書ファイルパスを更新する。
     * @param connection
     * @param pkInfo
     * @param filePath
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateSuisenshoFilePath(
            Connection connection,
            final ShinseiDataPk pkInfo,
            String filePath)
            throws DataAccessException, NoDataFoundException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, pkInfo);
        
        String updateQuery =
                "UPDATE SHINSEIDATAKANRI"+ dbLink 
                    + " SET"
                    + " SUISENSHO_PATH  = ?"
                    + " WHERE SYSTEM_NO = ?"
                    ;
        PreparedStatement preparedStatement = null;
        try {
            //更新
            preparedStatement = connection.prepareStatement(updateQuery);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement,i++,filePath);            //推薦書ファイルパス
            DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getSystemNo());//システム受付番号
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException(
                "申請情報推薦書ファイルパス更新中に例外が発生しました。 ：システム受付番号'"
                    + pkInfo.getSystemNo()
                    + "'",
                ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }   
    
//  //---------------------------------------------------------------------
//  // --
//  //---------------------------------------------------------------------     
//  /**
//   * 指定検索条件に該当する申請評価データを取得する。
//   * 
//   * @param connection
//   * @param searchInfo
//   * @return
//   * @throws DataAccessException
//   * @throws NoDataFoundException
//   * @throws ApplicationException  申請評価情報がセットされていなかった場合
//   */
//  public Page searchCommentList(
//      Connection connection,
//      HyokaSearchInfo searchInfo)
//      throws DataAccessException , NoDataFoundException, ApplicationException
//  {
//      String select = "SELECT "
//                    + "A.SYSTEM_NO, "                                         //システム受付番号
//                    + "A.UKETUKE_NO, "                                        //申請番号
//                    + "A.KEI_NAME, "                                          //分野系別
//                    + "A.NENDO, "                                             //年度
//                    + "A.KAISU, "                                             //回数
//                    + "A.JIGYO_NAME, "                                        //事業名
//                    + "A.NAME_KANJI_SEI, "                                    //申請者氏名(漢字等-姓)
//                    + "A.NAME_KANJI_MEI, "                                    //申請者氏名(漢字等-名)
//                    + "B.KEKKA_ABC, "                                         //審査結果(ABC)
//                    + "B.SENMON_CHK, "                                        //専門領域チェック
//                    + "B.KEKKA_TEN, "                                         //審査結果(点数)
//                    + "B.COMMENTS, "                                          //コメント
//                    + "B.SHINSAIN_NO, "                                       //審査員番号
//                    + "B.SHINSAIN_NAME_KANJI_SEI, "                           //審査員名(漢字-姓)
//                    + "B.SHINSAIN_NAME_KANJI_MEI "                            //審査員名(漢字-名)
//                    + "FROM SHINSEIDATAKANRI"+dbLink+" A, "
//                    + "SHINSAKEKKA"+dbLink+" B "
//                    + "WHERE A.SYSTEM_NO = B.SYSTEM_NO ";
//
//      
//      //-----検索条件オブジェクトの内容をSQLに結合していく-----
//      StringBuffer query = new StringBuffer(select);
//      //完全一致部
//      //事業名(CD)
//      List jigyoList = searchInfo.getJigyoList();
//      if(jigyoList != null && jigyoList.size() != 0){
//          for(int i=0; i<jigyoList.size(); i++){
//              if(i == 0){
//                  query.append("AND (SUBSTR(A.JIGYO_ID,3,5) = '")
//                       .append(EscapeUtil.toSqlString((String)jigyoList.get(i)))
//                       .append("' ");
//              }else{
//                  query.append("OR SUBSTR(A.JIGYO_ID,3,5) = '")
//                       .append(EscapeUtil.toSqlString((String)jigyoList.get(i)))
//                       .append("' ");
//              }
//          }
//          query.append(") ");
//      }
//      //年度
//      if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
//          query.append("AND A.NENDO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
//               .append("' ");
//      }
//      //回数
//      if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
//          query.append("AND A.KAISU = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
//               .append("' ");
//      }
//      //分野・系別
//      if(searchInfo.getBunya() != null && searchInfo.getBunya().length() != 0){
//          query.append("AND A.KEI_NAME = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getBunya()))         
//               .append("' ");
//      }
//      //申請番号
//      if(searchInfo.getShinseiNo() != null && searchInfo.getShinseiNo().length() != 0){
//          query.append("AND A.UKETUKE_NO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getShinseiNo()))         
//               .append("' ");
//      }
//      //評価（From）（前方一致）
//      if(searchInfo.getHyokaFrom() != null && searchInfo.getHyokaFrom().length() != 0){
//          query.append("AND A.KEKKA1_ABC >= '")
//               .append(EscapeUtil.toSqlString(searchInfo.getHyokaFrom()))
//               .append("' ");         
//      }                       
//      //評価（To）（前方一致）
//      if(searchInfo.getHyokaTo() != null && searchInfo.getHyokaTo().length() != 0){
//          String hyokaTo = searchInfo.getHyokaTo().toString();
//          //指定が3桁未満の場合、3桁になるようにFで補完する
//          if(hyokaTo.length() == 1){
//              hyokaTo = hyokaTo + "FF";
//          }else if(hyokaTo.length() == 2){
//              hyokaTo = hyokaTo + "F";
//          }
//          query.append("AND A.KEKKA1_ABC <= '")
//               .append(EscapeUtil.toSqlString(hyokaTo))
//               .append("' ");         
//      }                       
//      //部分一致部
//      //申請者名（漢字：姓）（部分一致）
//      if(searchInfo.getNameSei() != null && searchInfo.getNameSei().length() != 0){
//          query.append("AND A.NAME_KANJI_SEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameSei()))
//               .append("%' ");            
//      }               
//      //申請者名（漢字：名）（部分一致）
//      if(searchInfo.getNameMei() != null && searchInfo.getNameMei().length() != 0){
//          query.append("AND A.NAME_KANJI_MEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameMei()))
//               .append("%' ");            
//      }                       
//      //申請者名（ローマ字：姓）（部分一致）
//      if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
//          query.append("AND UPPER(A.NAME_RO_SEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
//               .append("%' ");            
//      }               
//      //申請者名（ローマ字：名）（部分一致）
//      if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
//          query.append("AND UPPER(A.NAME_RO_MEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
//               .append("%' ");            
//      }                       
//      
//      query.append("ORDER BY ")
//           .append("LENGTH(NVL(A.KEKKA1_ABC,' ')) DESC,") //申請書の審査結果(ABC)の審査結果数の降順
//           .append("A.KEKKA1_ABC ASC, ")                  //申請書の審査結果(ABC)の昇順
//           .append("A.KEKKA1_TEN DESC, ")                 //申請書の審査結果(点)の降順
//           .append("A.UKETUKE_NO ASC, ")                  //申請番号の昇順
//           .append("B.KEKKA_ABC ASC, ")                   //審査員ごとの審査結果(ABC)の昇順
//           .append("B.KEKKA_TEN DESC")                    //審査員ごとの審査結果(点)の降順
//           ;
//      
//      
//      //for debug
//      if(log.isDebugEnabled()){
//          log.debug("query:" + query);
//      }
//
//      // ページ取得
//      return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query.toString());
//      
//  }   
//  
//  /**
//   * 指定検索条件に該当する申請評価データを取得する。
//   * 
//   * @param connection
//   * @param searchInfo
//   * @return
//   * @throws DataAccessException
//   * @throws NoDataFoundException
//   * @throws ApplicationException  申請評価情報がセットされていなかった場合
//   */
//  public Page searchHyokaList(
//      Connection connection,
//      HyokaSearchInfo searchInfo)
//      throws DataAccessException , NoDataFoundException, ApplicationException
//  {
//      String select = "SELECT "
//                    + "A.SYSTEM_NO, "                                         //システム番号
//                    + "A.KEKKA1_ABC, "                                        //一時審査結果(ABC)
//                    + "A.KEKKA1_TEN, "                                        //一時審査結果(点数)
//                    + "A.BUNKASAIMOKU_CD, "                                   //分科細目コード
//                    + "A.BUNKA_NAME, "                                        //分科
//                    + "A.SAIMOKU_NAME, "                                      //細目
//                    + "A.KEI_NAME, "                                          //分野系列
//                    + "A.UKETUKE_NO, "                                        //申請番号
//                    + "A.NENDO, "                                             //年度
//                    + "A.KAISU, "                                             //回数
//                    + "A.JIGYO_NAME, "                                        //事業名
//                    + "A.KADAI_NAME_KANJI, "                                  //研究課題名又はセミナー名
//                    + "A.NAME_KANJI_SEI, "                                    //申請者氏名(漢字等-姓)
//                    + "A.NAME_KANJI_MEI, "                                    //申請者氏名(漢字等-名)
//                    + "A.SHOZOKU_NAME_RYAKU, "                                //所属機関名
//                    + "A.BUKYOKU_NAME, "                                      //部局名
//                    + "A.SHOKUSHU_NAME_KANJI, "                               //職種名
//                    + "A.NINZU_KEI, "                                         //参加人数合計
//                    + "A.NINZU_NIHON, "                                       //日本人数
//                    + "A.NAME_FAMILY_AITE, "                                  //相手国代表者名(ファミリーネーム)
//                    + "A.NAME_FIRST_AITE, "                                   //相手国代表者名(ファーストネーム)
//                    + "A.SHOZOKU_NAME_KANJI_AITE, "                           //相手国代表者所属機関名
//                    + "A.BUKYOKU_NAME_KANJI_AITE, "                           //相手国代表者部局名
//                    + "A.SHOKUSHU_NAME_KANJI_AITE, "                          //相手国代表者職名
//                    + "A.NINZU_AITE, "                                        //相手国人数
//                    + "A.KIKAN_START1, "                                      //期間1(開始)
//                    + "A.KIKAN_END1, "                                        //期間1(終了)
//                    + "A.JIGYO_ID, "                                          //事業ID
//                    + "A.KIKAN_HI1, "                                         //期間1(日数)
//                    + "A.KIKAN_TUKI, "                                        //期間1(月数)
//                    + "A.KAISAICHI1, "                                        //開催地1
//                    + "B.KEKKA_ABC, "                                         //審査結果(ABC)
//                    + "B.SENMON_CHK, "                                        //専門領域チェック
//                    + "B.KEKKA_TEN, "                                         //審査結果(点数)
//                    + "B.SHINSAIN_NO, "                                       //審査員番号
//                    + "B.SHINSAIN_NAME_KANJI_SEI, "                           //審査員名(漢字-姓)
//                    + "B.SHINSAIN_NAME_KANJI_MEI "                            //審査員名(漢字-名)
//                    + "FROM "
//                    + "SHINSEIDATAKANRI"+dbLink+" A, "
//                    + "SHINSAKEKKA"+dbLink+" B "
//                    + "WHERE A.SYSTEM_NO = B.SYSTEM_NO ";
//      
//      //-----検索条件オブジェクトの内容をSQLに結合していく-----
//      StringBuffer query = new StringBuffer(select);
//      //完全一致部
//      //事業名(CD)
//      List jigyoList = searchInfo.getJigyoList();
//      if(jigyoList != null && jigyoList.size() != 0){
//          for(int i=0; i<jigyoList.size(); i++){
//              if(i == 0){
//                  query.append("AND (SUBSTR(A.JIGYO_ID,3,5) = '")
//                       .append(EscapeUtil.toSqlString((String)jigyoList.get(i)))
//                       .append("' ");
//              }else{
//                  query.append("OR SUBSTR(A.JIGYO_ID,3,5) = '")
//                       .append(EscapeUtil.toSqlString((String)jigyoList.get(i)))
//                       .append("' ");
//              }
//          }
//          query.append(") ");
//      }
//      //年度
//      if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
//          query.append("AND A.NENDO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
//               .append("' ");
//      }
//      //回数
//      if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
//          query.append("AND A.KAISU = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
//               .append("' ");
//      }
//      //分野・系別
//      if(searchInfo.getBunya() != null && searchInfo.getBunya().length() != 0){
//          query.append("AND A.KEI_NAME = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getBunya()))         
//               .append("' ");
//      }
//      //申請番号
//      if(searchInfo.getShinseiNo() != null && searchInfo.getShinseiNo().length() != 0){
//          query.append("AND A.UKETUKE_NO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getShinseiNo()))         
//               .append("' ");
//      }
//      //評価（From）（前方一致）
//      if(searchInfo.getHyokaFrom() != null && searchInfo.getHyokaFrom().length() != 0){
//          query.append("AND A.KEKKA1_ABC >= '")
//               .append(EscapeUtil.toSqlString(searchInfo.getHyokaFrom()))
//               .append("' ");         
//      }                       
//      //評価（To）（前方一致）
//      if(searchInfo.getHyokaTo() != null && searchInfo.getHyokaTo().length() != 0){
//          String hyokaTo = searchInfo.getHyokaTo().toString();
//          //指定が3桁未満の場合、3桁になるようにFで補完する
//          if(hyokaTo.length() == 1){
//              hyokaTo = hyokaTo + "FF";
//          }else if(hyokaTo.length() == 2){
//              hyokaTo = hyokaTo + "F";
//          }
//          query.append("AND A.KEKKA1_ABC <= '")
//               .append(EscapeUtil.toSqlString(hyokaTo))
//               .append("' ");         
//      }                       
//      //部分一致部
//      //申請者名（漢字：姓）（部分一致）
//      if(searchInfo.getNameSei() != null && searchInfo.getNameSei().length() != 0){
//          query.append("AND A.NAME_KANJI_SEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameSei()))
//               .append("%' ");            
//      }               
//      //申請者名（漢字：名）（部分一致）
//      if(searchInfo.getNameMei() != null && searchInfo.getNameMei().length() != 0){
//          query.append("AND A.NAME_KANJI_MEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameMei()))
//               .append("%' ");            
//      }                       
//      //申請者名（ローマ字：姓）（部分一致）
//      if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
//          query.append("AND UPPER(A.NAME_RO_SEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
//               .append("%' ");            
//      }               
//      //申請者名（ローマ字：名）（部分一致）
//      if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
//          query.append("AND UPPER(A.NAME_RO_MEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
//               .append("%' ");            
//      }                       
//
//      query.append("ORDER BY ")
//           .append("LENGTH(NVL(A.KEKKA1_ABC,' ')) DESC,") //申請書の審査結果(ABC)の審査結果数の降順
//           .append("A.KEKKA1_ABC ASC, ")                  //申請書の審査結果(ABC)の昇順
//           .append("A.KEKKA1_TEN DESC, ")                 //申請書の審査結果(点)の降順
//           .append("A.UKETUKE_NO ASC, ")                  //申請番号の昇順
//           .append("B.KEKKA_ABC ASC, ")                   //審査員ごとの審査結果(ABC)の昇順
//           .append("B.KEKKA_TEN DESC")                    //審査員ごとの審査結果(点)の降順
//           ;
//      
//      
//      //for debug
//      if(log.isDebugEnabled()){
//          log.debug("query:" + query);
//      }
//
//      // ページ取得
//      return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query.toString());
//      
//  }   

    /**
     * 指定検索条件に該当する申請評価データを取得する(科研用)。
     * 
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  申請評価情報がセットされていなかった場合
     */
    public Page searchHyokaList(
            Connection connection,
            HyokaSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select = "SELECT"
                      + " A.SYSTEM_NO"                  //システム番号
                      + ", A.NENDO"                     //年度
                      + ", A.KAISU"                     //回数
                      + ", A.JIGYO_NAME"                //事業名
                      + ", A.KADAI_NAME_KANJI"          //研究課題名
                      + ", A.UKETUKE_NO"                //申請番号
                      + ", A.NAME_KANJI_SEI"            //申請者氏名(漢字等-姓)
                      + ", A.NAME_KANJI_MEI"            //申請者氏名(漢字等-名)
                      + ", A.SHOZOKU_NAME_RYAKU"        //所属機関名
                      + ", A.BUKYOKU_NAME_RYAKU"        //部局名
                      + ", A.SHOKUSHU_NAME_RYAKU"       //職種名
                      + ", A.KEKKA1_ABC"                //一時審査結果(ABC)
                      + ", A.KEKKA1_TEN"                //一時審査結果(点数)
                      + ", A.KANTEN_RYAKU"              //推薦の観点
                      + ", A.JIGYO_KUBUN"               //事業区分
                      + ", A.JURI_SEIRI_NO"             //整理番号　2005/10/18追加
                      + " FROM"
                      + " SHINSEIDATAKANRI" + dbLink + " A"
                      
                      //ここより、審査員が1人も割り当てられていない申請データを表示させないための制御
                      //審査員が1人以上割り当てられているSYSTEM_NOを検索してから、そのSYSTEM_NOを検索条件とする。
                      + " WHERE EXISTS"
                      + " (SELECT *"
                      + " FROM"
                      + " SHINSEIDATAKANRI" + dbLink + " A"
                      + ", SHINSAKEKKA" + dbLink + " B"
                      + " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
                      + " AND A.JIGYO_KUBUN = B.JIGYO_KUBUN"
                      + " AND B.SHINSAIN_NO NOT LIKE '@%')"//審査員が1人も割り当てられていない(全員@XXX)だと引っかからない

                      //2005.12.19 iso 検索条件を独立
                      ;
//                    + " AND A.DEL_FLG = 0"
//                    + " AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')";
//      
//      //-----検索条件オブジェクトの内容をSQLに結合していく-----
//      StringBuffer query = new StringBuffer(select);
//      
//      //完全一致部
////        //事業名(CD)
////        LabelValueBean labelValueBean = new LabelValueBean();
////        List jigyoList = searchInfo.getJigyoList();
////        if(jigyoList != null && jigyoList.size() > 0) {
////            query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
////            int i;
////            for(i = 0; i < jigyoList.size()-1; i++) {
////                labelValueBean = (LabelValueBean)jigyoList.get(i);
////                query.append("'")
////                     .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
////                     .append("', ");
////            }
////            labelValueBean = (LabelValueBean)jigyoList.get(i);
////            query.append("'")
////                 .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
////                 .append("')");
////        }
//      //年度
//      if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
//          query.append(" AND A.NENDO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
//               .append("'");
//      }
//      //回数
//      if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
//          query.append(" AND A.KAISU = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
//               .append("'");
//      }
//      //申請番号
//      if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
//          query.append(" AND A.UKETUKE_NO = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
//               .append("'");
//      }
//      //所属機関コード
//      if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
//          query.append(" AND A.SHOZOKU_CD = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))         
//               .append("'");
//      }
//      //細目番号
//      if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
//          query.append(" AND A.BUNKASAIMOKU_CD = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))            
//               .append("'");
//      }
//      //部分一致部
//      //系等の区分と略称の両方を検索する
//      if(searchInfo.getKeiName() != null && searchInfo.getKeiName().length() != 0){
//          query.append(" AND (A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
//              + "%' OR A.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
//      }
//      //申請者名（漢字：姓）（部分一致）
//      if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
//          query.append(" AND A.NAME_KANJI_SEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
//               .append("%'");
//      }
//      //申請者名（漢字：名）（部分一致）
//      if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
//          query.append(" AND A.NAME_KANJI_MEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
//               .append("%'");
//      }
//      //申請者名（フリガナ：姓）（部分一致）
//      if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
//          query.append(" AND A.NAME_KANA_SEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
//               .append("%'");
//      }
//      //申請者名（フリガナ：名）（部分一致）
//      if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
//          query.append(" AND A.NAME_KANA_MEI like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
//               .append("%'");
//      }
//      //申請者名（ローマ字：姓）（部分一致）
//      if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
//          query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
//               .append("%'");
//      }               
//      //申請者名（ローマ字：名）（部分一致）
//      if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
//          query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
//               .append("%'");
//      }                       
//      //事業区分
//      if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
//          query.append(" AND A.JIGYO_KUBUN = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))            
//               .append("'");
//      }
//// 2005/10/18　整理番号追加
//      // 整理番号　
//       if(searchInfo.getSeiriNo() != null && searchInfo.getSeiriNo().length() != 0){
//           query.append(" AND A.JURI_SEIRI_NO LIKE '%")
//                .append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()))
//                .append("%'");            
//       }
////        query.append(" ORDER BY")
////             .append(" LENGTH(NVL(REPLACE(A.KEKKA1_ABC, '-', ''),' ')) DESC");      //申請書の審査結果(ABC)の審査結果数の降順
//      
//      if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("1")) {
//          query.append(" ORDER BY TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")                                                           //申請書の審査結果(点)の降順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //Aの多い順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-の多い順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //Bの多い順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC");        //B-の多い順
//               .append(", NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0') DESC");      //申請書の審査結果(ABC)の昇順
////        } else if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("2")) {
//      } else {    //"1","2"でヒットしなかった場合エラーとなるので、elseとしておく
////            query.append(" ORDER BY LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //Aの多い順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-の多い順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //Bの多い順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC")     //B-の多い順
//          query.append(" ORDER BY NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0')DESC")        //申請書の審査結果(ABC)の昇順
//               .append(", TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC");                                                          //申請書の審査結果(点)の降順
//      }
//      query.append(", A.SYSTEM_NO ASC");                                          //申請番号の昇順   

        StringBuffer query = makeQueryHyokaGakuso(new StringBuffer(select), searchInfo);
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        // ページ取得
        return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query.toString());
    }   

//  CSV出力機能を追加      2005/10/28
    /**
     * 評価結果一覧のCSVリストを出力する(学創用)。
     * @param connection                コネクション
     * @param searchInfo
     * @return List
     * @throws DataAccessException      更新中に例外が発生した場合
     * @throws NoDataFoundException 対象データが見つからない場合
     */
    public List searchCsvData(Connection connection, HyokaSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException {

        //※項目の変更があった場合、下の並べ替えに影響がでる可能性があるので、注意。
        //-----------------------
        // SQL文の作成
        //-----------------------
        StringBuffer query = new StringBuffer();
        query.append("SELECT")
             .append(" A.JIGYO_ID")
             .append(", A.NENDO")
             .append(", A.KAISU")
             .append(", A.JIGYO_NAME")
             .append(", A.UKETUKE_NO")
             .append(", A.JURI_SEIRI_NO")
             .append(", A.KADAI_NAME_KANJI")
             .append(", A.NAME_KANJI_SEI")
             .append(", A.NAME_KANJI_MEI")
             .append(", A.SHOZOKU_CD")
             .append(", A.SHOZOKU_NAME")
             .append(", A.SHOZOKU_NAME_RYAKU")
             .append(", A.BUKYOKU_CD")
             .append(", A.BUKYOKU_NAME")
             .append(", A.BUKYOKU_NAME_RYAKU")
             .append(", A.SHOKUSHU_CD")
             .append(", A.SHOKUSHU_NAME_KANJI")
             .append(", SUBSTR(REPLACE(REPLACE(A.KEKKA1_ABC, 'A', ',A'), 'B', ',B'), 2, 17)")       //１次審査結果(ABC)"             
             .append(", A.KEKKA1_TEN")
             .append(", A.KANTEN")
             .append(", A.KANTEN_RYAKU")
             //2005.12.26 iso 並び替えを行わなくなったので削除
//           .append(", A.SYSTEM_NO")                           //並び替え用に使用する。下で出力からはカットするので、常に一番下にしておくこと。
             //2005.12.19 iso 検索条件を独立
//           .append(" FROM SHINSAKEKKA B, SHINSEIDATAKANRI A")
//           .append(" WHERE B.SYSTEM_NO = A.SYSTEM_NO")
//           .append(" AND A.JIGYO_KUBUN = B.JIGYO_KUBUN")
//         
//           .append(" AND A.DEL_FLG = 0")
//           .append(" AND B.SHINSAIN_NO NOT LIKE '@%'")
//           .append(" AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')")
//           ;
//
             .append(" FROM")
             .append(" SHINSEIDATAKANRI" + dbLink + " A")
             .append(" WHERE EXISTS")
             .append(" (SELECT *")
             .append(" FROM")
             .append(" SHINSEIDATAKANRI" + dbLink + " A")
             .append(", SHINSAKEKKA" + dbLink + " B")
             .append(" WHERE A.SYSTEM_NO = B.SYSTEM_NO")
             .append(" AND A.JIGYO_KUBUN = B.JIGYO_KUBUN")
             .append(" AND B.SHINSAIN_NO NOT LIKE '@%')")
             ;
//      //年度
//      if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
//          query.append(" AND A.NENDO = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getNendo()))          
//              .append("'");
//      }
//      //回数
//      if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
//          query.append(" AND A.KAISU = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))          
//              .append("'");
//      }
//      //申請番号
//      if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
//          query.append(" AND A.UKETUKE_NO = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))          
//              .append("'");
//      }
//      //所属機関コード
//      if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
//          query.append(" AND A.SHOZOKU_CD = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))          
//              .append("'");
//      }
//      //細目番号
//      if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
//          query.append(" AND A.BUNKASAIMOKU_CD = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))         
//              .append("'");
//      }
//      //部分一致部
//      //系等の区分と略称の両方を検索する
//      if(searchInfo.getKeiName() != null && searchInfo.getKeiName().length() != 0){
//          query.append(" AND (A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
//                  + "%' OR A.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
//      }
//      //申請者名（漢字：姓）（部分一致）
//      if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
//          query.append(" AND A.NAME_KANJI_SEI like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
//              .append("%'");          
//      }
//      //申請者名（漢字：名）（部分一致）
//      if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
//          query.append(" AND A.NAME_KANJI_MEI like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
//              .append("%'");          
//      }
//      //申請者名（フリガナ：姓）（部分一致）
//      if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
//          query.append(" AND A.NAME_KANA_SEI like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
//              .append("%'");          
//      }
//      //申請者名（フリガナ：名）（部分一致）
//      if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
//          query.append(" AND A.NAME_KANA_MEI like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
//              .append("%'");          
//      }
//      //申請者名（ローマ字：姓）（部分一致）
//      if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
//          query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
//              .append("%'");          
//      }               
//      //申請者名（ローマ字：名）（部分一致）
//      if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
//          query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
//              .append("%'");          
//      }
//      //事業区分
//      if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
//          query.append(" AND A.JIGYO_KUBUN = '")
//              .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))         
//              .append("'");
//      }
//      //整理番号  2005/10/21 追加（baba）
//      if(searchInfo.getSeiriNo() != null && searchInfo.getSeiriNo().length() != 0){
//          query.append(" AND A.JURI_SEIRI_NO LIKE '%")
//              .append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()))
//              .append("%'");          
//      }
//
//      if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("1")) {
//          query.append(" ORDER BY TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")                                                           //申請書の審査結果(点)の降順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //Aの多い順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-の多い順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //Bの多い順
////                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC");        //B-の多い順
//                   .append(", NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0') DESC");      //申請書の審査結果(ABC)の昇順
////        } else if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("2")) {
//      } else {    //"1","2"でヒットしなかった場合エラーとなるので、elseとしておく
////                            query.append(" ORDER BY LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //Aの多い順
////                                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-の多い順
////                                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //Bの多い順
////                                 .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC")     //B-の多い順
//          query.append(" ORDER BY NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0')DESC")        //申請書の審査結果(ABC)の昇順
//               .append(", TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC");                                                          //申請書の審査結果(点)の降順
//      }
//      query.append(", A.SYSTEM_NO ASC");                  //申請番号の昇順
//      query.append(", A.JIGYO_ID ASC");                       //事業IDの昇順
//      query.append(", B.KEKKA_ABC ASC");                  //審査結果(ABC)の昇順
//      query.append(", B.KEKKA_TEN DESC");                 //審査結果(点数)の降順
//      query.append(", B.SHINSAIN_NO ASC");                    //審査員番号の昇順
//      query.append(", B.JIGYO_KUBUN ASC");                    //事業区分の昇順
        query = makeQueryHyokaGakuso(query, searchInfo);
        
        
//for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        String[] columnArray = {"事業ID"
                                 ,"年度"
                                 ,"回数"
                                 ,"事業名"
                                 ,"応募番号"
                                 ,"整理番号（学創用）"
                                 ,"研究課題名（和文）"
                                 ,"応募者氏名（漢字等-姓）"
                                 ,"応募者氏名（漢字等-名）"
                                 ,"所属研究機関番号"
                                 ,"所属研究機関名"
                                 ,"所属研究機関名（略称）"
                                 ,"部局番号"
                                 ,"部局名"
                                 ,"部局名（略称）"
                                 ,"職名番号"
                                 ,"職名（和文）"
                                 ,"1次審査結果（ABC）"
                                 ,"1次審査結果（点数）"
                                 ,"推薦の観点"
                                 ,"推薦の観点（略称）"
                        
                                 };
    
        List csvDataList = SelectUtil.selectCsvList(connection, query.toString(), false);
        //2005.12.19 iso CSV出力不具合修正
        csvDataList.add(0, Arrays.asList(columnArray));
    
//      ArrayList newList = new ArrayList(Arrays.asList(columnArray));  //新しく作る1申請毎のリスト(新CSV1行分)
//      ArrayList csvList = new ArrayList();                            //CSVとして出力するリスト
//      String beforeJigyoId = "0";                                     //1個前の事業ID：初期値0(初めに一致しなければ何でもいい)
//      //2005.12.19 iso 申請書をシステム番号でまとめるよう変更
////        String beforeUketukeNo = "0";                                   //1個前の申請番号：初期値0(初めに一致しなければ何でもいい)
//      String beforeSystemNo = "0";                                    //1個前のシステム番号：初期値0(初めに一致しなければ何でもいい)
//
//      for(int i = 0; i < csvDataList.size(); i++) {
//          ArrayList shinseiList = (ArrayList)csvDataList.get(i);          //元データの1申請毎のリスト(元CSV1行分)
//          
//          //2005.12.19 iso CSV出力不具合修正
//          //応募番号の位置修正と「応募番号+所属機関番号」で一意となるよう変更
////                if(shinseiList.get(0).equals(beforeJigyoId) && shinseiList.get(1).equals(beforeUketukeNo)){}
//          String nowSystemNo = shinseiList.get(shinseiList.size()-1).toString();
//          shinseiList.remove(shinseiList.size()-1);                   //CSV出力しないシステム番号(常に最後)をカットする。
//          
//          if(shinseiList.get(0).equals(beforeJigyoId) && nowSystemNo.equals(beforeSystemNo)){} 
//          else {
//              //1個前と違う申請データの場合、新しく作った1行分の申請データを新CSVリストに登録。
//              //登録後、newListを新しい申請データで初期化。     
//              csvList.add(newList);
//              newList = new ArrayList(shinseiList);
//              //現在の事業ID・申請番号を次の判断に使うためにセット。
//              beforeJigyoId = shinseiList.get(0).toString();
//              //2005.12.19 iso CSV出力不具合修正
////                    beforeUketukeNo = shinseiList.get(1).toString();
//              beforeSystemNo = nowSystemNo;
//          }
//          //最後のデータは上のelseに引っかからないので、ここでcsvListに格納する。
//          if(i == csvDataList.size()-1) {
//              //審査結果の数がきちんとあるデータのみ出力する。
//              csvList.add(newList);
//              
//          }
//      }
//      return csvList;
        return csvDataList;
    }

    /**
     * 評価検索条件オブジェクトから検索条件を取得しSQLの問い合わせ部分を生成する。
     * @param query
     * @param searchInfo
     * @return
     */
    private StringBuffer makeQueryHyokaGakuso(StringBuffer query, HyokaSearchInfo searchInfo) {

        //年度
        if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
            query.append(" AND A.NENDO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
                 .append("'");
        }
        //回数
        if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
                 .append("'");
        }
        //申請番号
        if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
            query.append(" AND A.UKETUKE_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
                 .append("'");
        }
        //所属機関コード
        if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
            query.append(" AND A.SHOZOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))         
                 .append("'");
        }
        //細目番号
        if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
            query.append(" AND A.BUNKASAIMOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))            
                 .append("'");
        }
        //部分一致部
        //系等の区分と略称の両方を検索する
        if(searchInfo.getKeiName() != null && searchInfo.getKeiName().length() != 0){
            query.append(" AND (A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
                    + "%' OR A.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
        }
        //申請者名（漢字：姓）（部分一致）
        if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
            query.append(" AND A.NAME_KANJI_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
                 .append("%'");         
        }
        //申請者名（漢字：名）（部分一致）
        if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
            query.append(" AND A.NAME_KANJI_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
                 .append("%'");         
        }
        //申請者名（フリガナ：姓）（部分一致）
        if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
            query.append(" AND A.NAME_KANA_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
                 .append("%'");         
        }
        //申請者名（フリガナ：名）（部分一致）
        if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
            query.append(" AND A.NAME_KANA_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
                 .append("%'");         
        }
        //申請者名（ローマ字：姓）（部分一致）
        if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
                 .append("%'");         
        }               
        //申請者名（ローマ字：名）（部分一致）
        if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
                 .append("%'");         
        }
        //事業区分
        if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
            query.append(" AND A.JIGYO_KUBUN = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))            
                 .append("'");
        }
        //整理番号  2005/10/21 追加（baba）
        if(searchInfo.getSeiriNo() != null && searchInfo.getSeiriNo().length() != 0){
            query.append(" AND A.JURI_SEIRI_NO LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()))
                 .append("%'");         
        }

        query.append(" AND A.DEL_FLG = 0")
             .append(" AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')");
    
        if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("1")) {
            query.append(" ORDER BY TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")                                   
                 .append(", NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0') DESC");      //申請書の審査結果(ABC)の昇順
        } else {    //"1","2"でヒットしなかった場合エラーとなるので、elseとしておく
            query.append(" ORDER BY NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0')DESC")        //申請書の審査結果(ABC)の昇順
                 .append(", TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC");                                                          //申請書の審査結果(点)の降順
        }
        query.append(", A.SYSTEM_NO ASC");                  //申請番号の昇順
        query.append(", A.JIGYO_ID ASC");                   //事業IDの昇順

        return query;
    }

    /**
     * 指定検索条件に該当する申請評価データを取得する(基盤用)。
     * 
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  申請評価情報がセットされていなかった場合
     */
    public Page searchHyokaListKiban(
            Connection connection,
            HyokaSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select = "SELECT"
                      + " A.SYSTEM_NO"                      //システム番号
                      + ", A.NENDO"                         //年度
                      + ", A.KAISU"                         //回数
                      + ", A.JIGYO_NAME"                    //事業名
                      + ", A.BUNKASAIMOKU_CD"               //細目番号
                      + ", A.SAIMOKU_NAME"                  //細目名
                      + ", A.KAIGAIBUNYA_NAME_RYAKU"        //海外分野名(略称)
                      //add start ly 2006/04/26
                      + " ,A.KAIGAIBUNYA_NAME"              //海外分野名(基盤の場合)
                      + " ,A.SHINSARYOIKI_NAME"             //海外分野名
                      //add end ly 2006/04/26
                      + ", A.SHOZOKU_NAME_RYAKU"            //所属機関名
                      + ", A.BUKYOKU_NAME_RYAKU"            //部局名
                      + ", A.SHOKUSHU_NAME_RYAKU"           //職種名
                      + ", A.NAME_KANJI_SEI"                //申請者氏名(漢字等-姓)
                      + ", A.NAME_KANJI_MEI"                //申請者氏名(漢字等-名)
                      + ", A.UKETUKE_NO"                    //申請番号
                      + ", A.KEKKA1_TEN_SORTED"             //一時審査結果(総合評点)
                      + ", A.KEKKA1_TEN"                    //一時審査結果(計)
                      + ", A.JIGYO_KUBUN"                   //事業区分
                      + " FROM"
                      + " SHINSEIDATAKANRI"+dbLink+" A"
                      
                      //ここより、審査員が1人も割り当てられていない申請データを表示させないための制御
                      //審査員が1人以上割り当てられているSYSTEM_NOを検索してから、そのSYSTEM_NOを検索条件とする。
                      + " WHERE EXISTS"
                      + " (SELECT *"
                      + " FROM"
                      + " SHINSEIDATAKANRI"+dbLink+" A"
                      + ", SHINSAKEKKA"+dbLink+" B"
                      + " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
                      + " AND A.JIGYO_KUBUN = B.JIGYO_KUBUN"
                      + " AND B.SHINSAIN_NO NOT LIKE '@%')" //審査員が1人も割り当てられていない(全員@XXX)だと引っかからない

                      + " AND A.DEL_FLG = 0"
                      + " AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')";
        
        //-----検索条件オブジェクトの内容をSQLに結合していく-----
        StringBuffer query = new StringBuffer(select);
        
//      //事業名(CD)
//      if(searchInfo.getJigyoCd() != null && searchInfo.getJigyoCd().length() != 0) {
//          query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getJigyoCd()))
//               .append("'");
//      }
        //2005.05.19 iso アクセス管理の事業区分→事業CD変更対応
        //事業コード
        List jigyoCdValueList = searchInfo.getValues();
        if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
                 .append(changeIterator2CSV(searchInfo.getValues().iterator()))
                 .append(")");
        }
//      LabelValueBean labelValueBean = new LabelValueBean();
//      List jigyoList = searchInfo.getJigyoList();
//      if(jigyoList != null && jigyoList.size() > 0) {
//          query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
//          int i;
//          for(i = 0; i < jigyoList.size()-1; i++) {
//              labelValueBean = (LabelValueBean)jigyoList.get(i);
//              query.append("'")
//                   .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//                   .append("', ");
//          }
//          labelValueBean = (LabelValueBean)jigyoList.get(i);
//          query.append("'")
//               .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//               .append("')");
//      }
        //年度
        if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
            query.append(" AND A.NENDO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
                 .append("'");
        }
        //回数
        if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
                 .append("'");
        }
        //申請者名（漢字：姓）（部分一致）
        if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
            query.append(" AND A.NAME_KANJI_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
                 .append("%'");
        }
        //申請者名（漢字：名）（部分一致）
        if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
            query.append(" AND A.NAME_KANJI_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
                 .append("%'");
        }
        //申請者名（フリガナ：姓）（部分一致）
        if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
            query.append(" AND A.NAME_KANA_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
                 .append("%'");
        }
        //申請者名（フリガナ：名）（部分一致）
        if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
            query.append(" AND A.NAME_KANA_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
                 .append("%'");
        }
        //申請者名（ローマ字：姓）（部分一致）
        if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
                 .append("%'");
        }
        //申請者名（ローマ字：名）（部分一致）
        if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
                 .append("%'");
        }
        //所属機関コード
        if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
            query.append(" AND A.SHOZOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))         
                 .append("'");
        }
        //細目番号
        if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
            query.append(" AND A.BUNKASAIMOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))            
                 .append("'");
        }
        //海外分野(略称)
        //update start ly 2006/04/26
//      if(searchInfo.getKaigaibunyaName()!= null && searchInfo.getKaigaibunyaName().length() != 0){
//          query.append(" AND (A.KAIGAIBUNYA_NAME LIKE '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
//               .append("%' OR A.KAIGAIBUNYA_NAME_RYAKU LIKE '%")
//               .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
//               .append("%')");
//      }
        if(searchInfo.getKaigaibunyaName()!= null && searchInfo.getKaigaibunyaName().length() != 0){
            query.append(" AND (A.KAIGAIBUNYA_NAME LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
                 .append("%' OR A.KAIGAIBUNYA_NAME_RYAKU LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
                 .append("%' OR A.SHINSARYOIKI_NAME LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
                 .append("%')");
        }
        //update end ly 2006/04/26
        //申請番号
        if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
            query.append(" AND A.UKETUKE_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
                 .append("'");
        }
        //評価（点）（高）
        if(searchInfo.getHyokaHigh() != null && searchInfo.getHyokaHigh().length() != 0){
            query.append(" AND NVL(A.KEKKA1_TEN, -1) <= '")
                 .append(EscapeUtil.toSqlString(searchInfo.getHyokaHigh()))         
                 .append("'");
        }
        //評価（点）（低）
        if(searchInfo.getHyokaLow() != null && searchInfo.getHyokaLow().length() != 0){
            query.append(" AND A.KEKKA1_TEN >= '")
                 .append(EscapeUtil.toSqlString(searchInfo.getHyokaLow()))          
                 .append("'");
        }
        //事業区分
 //update start ly 2006/04/26
//      if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
//          query.append(" AND A.JIGYO_KUBUN = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))            
//               .append("'");
//      }
        if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
//2006/05/09　追加ここから            
//          query.append(" AND (A.JIGYO_KUBUN = '4' or A.JIGYO_KUBUN = '6' or A.JIGYO_KUBUN = '7') ");
            query.append(" AND A.JIGYO_KUBUN IN (");
            query.append(StringUtil.changeIterator2CSV(JigyoKubunFilter.Convert2ShinseishoJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(),true));
            query.append(")");
//苗　追加ここまで            
      }
 //update end ly 2006/04/26

        query.append(" ORDER BY")
             .append(" TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")//一時審査結果(計)の降順

             .append(", A.KEKKA1_TEN_SORTED DESC")              //一時審査結果(総合評点)の降順

             .append(", A.SYSTEM_NO ASC");                      //申請番号の昇順   

        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        // ページ取得
        return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query.toString());
    }   

    /**
     * 指定検索条件に該当する評価コメントデータを取得する(基盤用)。
     * 
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  申請評価情報がセットされていなかった場合
     */
    public Page searchCommentListKiban(
            Connection connection,
            HyokaSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select = "SELECT"
                      + " A.SYSTEM_NO"                       //システム番号
                      + ", A.NENDO"                          //年度
                      + ", A.KAISU"                          //回数
                      + ", A.JIGYO_NAME"                     //事業名
                      + ", A.BUNKASAIMOKU_CD"                //細目番号
                      + ", A.SAIMOKU_NAME"                   //細目名
                      + ", A.KAIGAIBUNYA_NAME_RYAKU"         //海外分野名(略称)
                      + ", A.SHOZOKU_NAME_RYAKU"             //所属機関名
                      + ", A.BUKYOKU_NAME_RYAKU"             //部局名
                      + ", A.SHOKUSHU_NAME_RYAKU"            //職種名
                      + ", A.NAME_KANJI_SEI"                 //申請者氏名(漢字等-姓)
                      + ", A.NAME_KANJI_MEI"                 //申請者氏名(漢字等-名)
                      + ", A.UKETUKE_NO"                     //申請番号
                      + ", A.KEKKA1_TEN_SORTED"              //一時審査結果(総合評点)
                      + ", A.KEKKA1_TEN"                     //一時審査結果(計)
                      + ", B.COMMENTS"                       //コメント
                      + ", B.SHINSAIN_NO"                    //審査員氏名(漢字等-姓)
                      + ", B.SHINSAIN_NAME_KANJI_SEI"        //審査員氏名(漢字等-姓)
                      + ", B.SHINSAIN_NAME_KANJI_MEI"        //審査員氏名(漢字等-名)
                      + ", A.JIGYO_KUBUN"                    //事業区分
                      + " FROM"
                      + " SHINSEIDATAKANRI"+dbLink+" A"
                      + ", SHINSAKEKKA"+dbLink+" B"
                      
                      //ここより、審査員が1人も割り当てられていない申請データを表示させないための制御
                      //審査員が1人以上割り当てられているSYSTEM_NOを検索してから、そのSYSTEM_NOを検索条件とする。
                      + " WHERE EXISTS"
                      + " (SELECT *"
                      + " FROM"
                      + " SHINSEIDATAKANRI" + dbLink + " A"
                      + ", SHINSAKEKKA" + dbLink + " B"
                      + " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
                      //+ " AND A.JIGYO_KUBUN = B.JIGYO_KUBUN"
                      + " AND B.SHINSAIN_NO NOT LIKE '@%')"  //審査員が1人も割り当てられていない(全員@XXX)だと引っかからない

                      + " AND A.DEL_FLG = 0"
                      + " AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')"
                      + " AND A.SYSTEM_NO = B.SYSTEM_NO"
                      + " AND A.JIGYO_KUBUN = B.JIGYO_KUBUN";
        
        //-----検索条件オブジェクトの内容をSQLに結合していく-----
        StringBuffer query = new StringBuffer(select);
        
//      //事業名(CD)
//      if(searchInfo.getJigyoCd() != null && searchInfo.getJigyoCd().length() != 0) {
//          query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '")
//               .append(EscapeUtil.toSqlString(searchInfo.getJigyoCd()))
//               .append("'");
//      }
        //2005.05.19 iso アクセス管理の事業区分→事業CD変更対応
        //事業コード
        List jigyoCdValueList = searchInfo.getValues();
        if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
                 .append(changeIterator2CSV(searchInfo.getValues().iterator()))
                 .append(")");
        }
//      LabelValueBean labelValueBean = new LabelValueBean();
//      List jigyoList = searchInfo.getJigyoList();
//      if(jigyoList != null && jigyoList.size() > 0) {
//          query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
//          int i;
//          for(i = 0; i < jigyoList.size()-1; i++) {
//              labelValueBean = (LabelValueBean)jigyoList.get(i);
//              query.append("'")
//                   .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//                   .append("', ");
//          }
//          labelValueBean = (LabelValueBean)jigyoList.get(i);
//          query.append("'")
//               .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//               .append("')");
//      }
        //年度
        if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
            query.append(" AND A.NENDO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
                 .append("'");
        }
        //回数
        if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
                 .append("'");
        }
        //申請者名（漢字：姓）（部分一致）
        if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
            query.append(" AND A.NAME_KANJI_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
                 .append("%'");
        }
        //申請者名（漢字：名）（部分一致）
        if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
            query.append(" AND A.NAME_KANJI_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
                 .append("%'");
        }
        //申請者名（フリガナ：姓）（部分一致）
        if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
            query.append(" AND A.NAME_KANA_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
                 .append("%'");
        }
        //申請者名（フリガナ：名）（部分一致）
        if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
            query.append(" AND A.NAME_KANA_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
                 .append("%'");
        }
        //申請者名（ローマ字：姓）（部分一致）
        if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
                 .append("%'");
        }
        //申請者名（ローマ字：名）（部分一致）
        if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
                 .append("%'");
        }
        //所属機関コード
        if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
            query.append(" AND A.SHOZOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))         
                 .append("'");
        }
        //細目番号
        if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
            query.append(" AND A.BUNKASAIMOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))            
                 .append("'");
        }
        //海外分野(略称)
        if(searchInfo.getKaigaibunyaName()!= null && searchInfo.getKaigaibunyaName().length() != 0){
            query.append(" AND (A.KAIGAIBUNYA_NAME LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
                 .append("%' OR A.KAIGAIBUNYA_NAME_RYAKU LIKE '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaigaibunyaName()))
                 .append("%')");
        }
        //申請番号
        if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
            query.append(" AND A.UKETUKE_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
                 .append("'");
        }
        //評価（点）（高）
        if(searchInfo.getHyokaHigh() != null && searchInfo.getHyokaHigh().length() != 0){
            query.append(" AND A.KEKKA1_TEN <= '")
                 .append(EscapeUtil.toSqlString(searchInfo.getHyokaHigh()))         
                 .append("'");
        }
        //評価（点）（低）
        if(searchInfo.getHyokaLow() != null && searchInfo.getHyokaLow().length() != 0){
            query.append(" AND A.KEKKA1_TEN >= '")
                 .append(EscapeUtil.toSqlString(searchInfo.getHyokaLow()))          
                 .append("'");
        }
        //事業区分
        if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
            query.append(" AND A.JIGYO_KUBUN = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))            
                 .append("'");
        }

        query.append(" ORDER BY")
             .append(" TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")    //一時審査結果(計)の降順
             .append(", A.KEKKA1_TEN_SORTED DESC")                  //一時審査結果(総合評点)の降順
             .append(", A.SYSTEM_NO ASC")                           //申請番号の昇順   
             //2005/11/15 利害関係「-」をセットされるので、文字列でソートする
             //.append(", TO_NUMBER(NVL(REPLACE(B.KEKKA_TEN, '-', '0'), '-1')) DESC")   //審査結果(点数)の降順
             .append(", NVL(B.KEKKA_TEN, ' ') DESC")    //審査結果(点数)の降順

             .append(", B.SHINSAIN_NO ASC");                        //審査員番号の昇順  

        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query.toString());
    }   

    /**
     * 指定検索条件に該当する申請評価データを取得する。
     * 
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  申請評価情報がセットされていなかった場合
     */
    public List searchCsvHyokaList(
            Connection connection,
            HyokaSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        String select = "SELECT"
                        + " B.JIGYO_ID"                             //事業ID
                        + ", B.UKETUKE_NO"                          //申請番号
                        + ", A.NENDO"                               //年度
                        + ", A.KAISU"                               //回数
                        + ", A.JIGYO_NAME"                          //事業名
                        + ", A.JURI_SEIRI_NO"                       //整理番号                  2005/10/21　追加（baba）
                        + ", A.NAME_KANJI_SEI"                      //申請者氏名（漢字等-姓）
                        + ", A.NAME_KANJI_MEI"                      //申請者氏名（漢字等-名)
                        + ", A.SHOZOKU_CD"                          //所属機関コード
                        + ", A.SHOZOKU_NAME"                        //所属機関名
                        + ", A.SHOZOKU_NAME_RYAKU"                  //所属機関名（略称）
                        + ", A.BUKYOKU_CD"                          //部局コード
                        + ", A.BUKYOKU_NAME"                        //部局名
                        + ", A.BUKYOKU_NAME_RYAKU"                  //部局名（略称）
                        + ", A.SHOKUSHU_CD"                         //職種コード
                        + ", A.SHOKUSHU_NAME_KANJI"                 //職名（和文）
                        + ", A.SHOKUSHU_NAME_RYAKU"                 //職名（略称）
                        + ", A.KADAI_NAME_KANJI"                    //研究課題名(和文）
                        + ", A.KANTEN"                              //推薦の観点
                        + ", A.KEI_NAME"                            //系等の区分
                        + ", A.KENKYU_NO"                           //申請者研究者番号
                        + ", SUBSTR(REPLACE(REPLACE(A.KEKKA1_ABC, 'A', ',A'), 'B', ',B'), 2, 17)"       //１次審査結果(ABC)
                        + ", B.SHINSAIN_NAME_KANJI_SEI"             //審査員名（漢字等-姓）
                        + ", B.SHINSAIN_NAME_KANJI_MEI"             //審査員名（漢字等-名)
                        + ", B.SHOZOKU_NAME SHINSAIN_SHOZOKU_NAME"  //審査員所属機関名　→　申請者とかぶるので念のためリネーム
                        + ", B.BUKYOKU_NAME SHINSAIN_BUKYOKU_NAME"  //審査員部局名　→　申請者とかぶるので念のためリネーム
                        + ", B.KEKKA_ABC"                           //審査結果
                        + ", B.COMMENT1"                            //コメント1
                        + ", B.COMMENT2"                            //コメント2
                        + ", B.COMMENT3"                            //コメント3
                        + ", B.COMMENT4"                            //コメント4
                        + ", B.COMMENT5"                            //コメント5
                        + ", B.COMMENT6"                            //コメント6
                        + " FROM"
                        + " SHINSEIDATAKANRI"+dbLink+" A"
                        + ", SHINSAKEKKA"+dbLink+" B"
                        
                        //ここより、審査員が1人も割り当てられていない申請データを表示させないための制御
                        //審査員が1人以上割り当てられているSYSTEM_NOを検索してから、そのSYSTEM_NOを検索条件とする。
                        + " WHERE EXISTS"
                        + " (SELECT *"
                        + " FROM"
                        + " SHINSEIDATAKANRI" + dbLink + " A"
                        + ", SHINSAKEKKA" + dbLink + " B"
                        + " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
                        + " AND A.JIGYO_KUBUN = B.JIGYO_KUBUN"
                        + " AND B.SHINSAIN_NO NOT LIKE '@%')"        //審査員が1人も割り当てられていない(全員@XXX)だと引っかからない

                        + " AND A.DEL_FLG = 0"
                        + " AND A.SYSTEM_NO = B.SYSTEM_NO"
                        + " AND A.JOKYO_ID IN ('08', '09', '10', '11', '12')";
        
        //-----検索条件オブジェクトの内容をSQLに結合していく-----
        StringBuffer query = new StringBuffer(select);

        //完全一致部
        //事業名(CD)
//      if(searchInfo.getJigyoId() != null && )
//      LabelValueBean labelValueBean = new LabelValueBean();
//      List jigyoList = searchInfo.getJigyoList();
//      if(jigyoList != null && jigyoList.size() > 0) {
//          query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
//          int i;
//          for(i = 0; i < jigyoList.size()-1; i++) {
//              labelValueBean = (LabelValueBean)jigyoList.get(i);
//              query.append("'")
//                   .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//                   .append("', ");
//          }
//          labelValueBean = (LabelValueBean)jigyoList.get(i);
//          query.append("'")
//               .append(EscapeUtil.toSqlString(labelValueBean.getValue()))
//               .append("')");
//      }
        //年度
        if(searchInfo.getNendo() != null && searchInfo.getNendo().length() != 0){
            query.append(" AND A.NENDO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getNendo()))         
                 .append("'");
        }
        //回数
        if(searchInfo.getKaisu() != null && searchInfo.getKaisu().length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getKaisu()))         
                 .append("'");
        }
        //申請番号
        if(searchInfo.getUketukeNo() != null && searchInfo.getUketukeNo().length() != 0){
            query.append(" AND A.UKETUKE_NO = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()))         
                 .append("'");
        }
        //所属機関コード
        if(searchInfo.getShozokuCd() != null && searchInfo.getShozokuCd().length() != 0){
            query.append(" AND A.SHOZOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()))         
                 .append("'");
        }
        //細目番号
        if(searchInfo.getBunkasaimokuCd() != null && searchInfo.getBunkasaimokuCd().length() != 0){
            query.append(" AND A.BUNKASAIMOKU_CD = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getBunkasaimokuCd()))            
                 .append("'");
        }
        //部分一致部
        //系等の区分と略称の両方を検索する
        if(searchInfo.getKeiName() != null && searchInfo.getKeiName().length() != 0){
            query.append(" AND (A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
                + "%' OR A.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
        }
        //申請者名（漢字：姓）（部分一致）
        if(searchInfo.getNameKanjiSei() != null && searchInfo.getNameKanjiSei().length() != 0){
            query.append(" AND A.NAME_KANJI_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()))
                 .append("%'");         
        }
        //申請者名（漢字：名）（部分一致）
        if(searchInfo.getNameKanjiMei() != null && searchInfo.getNameKanjiMei().length() != 0){
            query.append(" AND A.NAME_KANJI_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()))
                 .append("%'");         
        }
        //申請者名（フリガナ：姓）（部分一致）
        if(searchInfo.getNameKanaSei() != null && searchInfo.getNameKanaSei().length() != 0){
            query.append(" AND A.NAME_KANA_SEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()))
                 .append("%'");         
        }
        //申請者名（フリガナ：名）（部分一致）
        if(searchInfo.getNameKanaMei() != null && searchInfo.getNameKanaMei().length() != 0){
            query.append(" AND A.NAME_KANA_MEI like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()))
                 .append("%'");         
        }
        //申請者名（ローマ字：姓）（部分一致）
        if(searchInfo.getNameRoSei() != null && searchInfo.getNameRoSei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_SEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()))
                 .append("%'");         
        }               
        //申請者名（ローマ字：名）（部分一致）
        if(searchInfo.getNameRoMei() != null && searchInfo.getNameRoMei().length() != 0){
            query.append(" AND UPPER(A.NAME_RO_MEI) like '%")
                 .append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()))
                 .append("%'");         
        }
        //事業区分
        if(searchInfo.getJigyoKubun() != null && searchInfo.getJigyoKubun().length() != 0){
            query.append(" AND A.JIGYO_KUBUN = '")
                 .append(EscapeUtil.toSqlString(searchInfo.getJigyoKubun()))            
                 .append("'");
        }
        //整理番号  2005/10/21 追加（baba）
        if(searchInfo.getSeiriNo() != null && searchInfo.getSeiriNo().length() != 0){
                  query.append(" AND A.JURI_SEIRI_NO LIKE '%")
                       .append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()))
                       .append("%'");           
        }
//      query.append(" ORDER BY")
//           .append(" LENGTH(NVL(REPLACE(A.KEKKA1_ABC, '-', ''),' ')) DESC");  //申請書の審査結果(ABC)の審査結果数の降順

        if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("1")) {
            query.append(" ORDER BY TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC")                                                           //申請書の審査結果(点)の降順
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //Aの多い順
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-の多い順
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //Bの多い順
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC");        //B-の多い順
            .append(", NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0') DESC");       //申請書の審査結果(ABC)の昇順
//           } else if(searchInfo.getHyojiHoshiki() != null && searchInfo.getHyojiHoshiki().equals("2")) {
        } else {    //"1","2"でヒットしなかった場合エラーとなるので、elseとしておく
//          query.append(" ORDER BY LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', '00'), 'B', ''), ' ')) DESC")     //Aの多い順
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '00'), 'B-', ''), 'A', ''), 'B', ''), ' ')) DESC")     //A-の多い順
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', ''), 'A', ''), 'B', '00'), ' ')) DESC")     //Bの多い順
//               .append(", LENGTH(NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', ''), 'B-', '00'), 'A', ''), 'B', ''), ' ')) DESC")     //B-の多い順
            query.append(" ORDER BY NVL(REPLACE(REPLACE(REPLACE(REPLACE(KEKKA1_ABC, 'A-', '3'), 'B-', '1'), 'A', '4'), 'B', '2'), '0')DESC")        //申請書の審査結果(ABC)の昇順
                 .append(", TO_NUMBER(NVL(A.KEKKA1_TEN, '-1')) DESC");                                                          //申請書の審査結果(点)の降順
        }
        query.append(", A.SYSTEM_NO ASC");                                          //申請番号の昇順
        query.append(", A.JIGYO_ID ASC");                                           //事業IDの昇順
        query.append(", B.KEKKA_ABC ASC");                                          //審査結果(ABC)の昇順
        query.append(", B.KEKKA_TEN DESC");                                         //審査結果(点数)の降順
        query.append(", B.SHINSAIN_NO ASC");                                        //審査員番号の昇順
        query.append(", B.JIGYO_KUBUN ASC");                                        //事業区分の昇順

        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        return SelectUtil.selectCsvList(connection, query.toString(), false);
    }   

    //2005/04/22 追加 ここから--------------------------------------------
    //理由　取り込みマスタで更新する申請データがあるかどうかのチェック用SQLを追加
    /**
     * 取り込みマスタのデータが申請情報テーブルに存在するかチェックする。
     * 
     * @param connection
     * @param info
     * @return String
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String selectShinseiTorikomiData(Connection connection, ShinseiDataInfo info)
        throws DataAccessException, NoDataFoundException {
        
        String systemNo = null;
        
        String bunkatsuNo = info.getKadaiInfo().getBunkatsuNo();
        String saimokuCd = info.getKadaiInfo().getBunkaSaimokuCd();
        
        String query = "SELECT "
                     + " SYSTEM_NO"                  //システム受付番号
                     + " FROM" 
                     + " SHINSEIDATAKANRI"
                     + " WHERE"
                     + " UKETUKE_NO = ?"
                     + " AND JIGYO_ID = ?"
                     + " AND DEL_FLG = 0";               //削除フラグが[0]
        if(saimokuCd != null && !saimokuCd.equals("")){
            query +=  " AND BUNKASAIMOKU_CD = ?";
        }
        if(bunkatsuNo!= null && !bunkatsuNo.equals("-")){
            query += " AND BUNKATSU_NO = ?";
        }
//2006/05/09 追加ここから           
//        query = query + " AND (JIGYO_KUBUN = 4 or JIGYO_KUBUN = 6 )"
//      query = query + " AND ( JIGYO_KUBUN = "
//                      + IJigyoKubun.JIGYO_KUBUN_KIBAN 
//                      + "OR JIGYO_KUBUN = "
//                      + IJigyoKubun.JIGYO_KUBUN_WAKATESTART 
//                      + "OR JIGYO_KUBUN = "
//                      + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI
//                      + ")"
        query = query   + " AND JIGYO_KUBUN IN ("
                        + StringUtil.changeIterator2CSV(
                                JigyoKubunFilter.Convert2ShinseishoJigyoKubun(
                                        IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(), true) + ")"
//苗　追加ここまで                      
                    + " FOR UPDATE"
                    ;
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++, info.getUketukeNo());
            DatabaseUtil.setParameter(preparedStatement,index++, info.getJigyoId());    //システム受付番号
            if(saimokuCd != null && !saimokuCd.equals("")){
                DatabaseUtil.setParameter(preparedStatement,index++, saimokuCd);
            }
            if(bunkatsuNo!= null && !bunkatsuNo.equals("-")){
                DatabaseUtil.setParameter(preparedStatement,index++, bunkatsuNo);
            }
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                systemNo =recordSet.getString("SYSTEM_NO");
            }else{
                throw new NoDataFoundException(
                    "申請データ管理テーブルに該当するデータが見つかりません。" +
                    "検索キー：申請番号'"+ info.getUketukeNo()+ "', "+
                    "事業ID'"+info.getJigyoId()+"',"+
                    "細目番号'"+info.getKadaiInfo().getBunkaSaimokuCd()+"',"+
                    "分割番号'"+info.getKadaiInfo().getBunkatsuNo()+"'");
            }
            
        } catch (SQLException ex) {
            throw new DataAccessException("申請データ管理テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
        return systemNo;
    }

    /**
     * 研究者マスタに研究者情報が存在するかチェック
     * @param connection
     * @param strSQL
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String selectKenkyuushaCount(
            Connection connection,
            String strSQL
            )throws DataAccessException, NoDataFoundException {

        String              strCount            =   null;
        //未使用の為コメントする
        //List              lstResult           =   new ArrayList();    //検索結果取得用リスト
        ResultSet           recordSet           =   null;
        PreparedStatement   preparedStatement   =   null;

        try{
            preparedStatement = connection.prepareStatement(strSQL);
            recordSet = preparedStatement.executeQuery();

            if(recordSet.next()){
                strCount =recordSet.getString(IShinseiMaintenance.STR_COUNT);
            }else{
                throw new NoDataFoundException("研究者マスタテーブルに該当するデータが見つかりません。");
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("研究者情報の検索中に例外が発生しました。",   ex);}
//      catch(DataAccessException ex){
//          throw new DataAccessException("研究者情報の検索中に例外が発生しました。",   ex);}
        catch(NoDataFoundException ex){
            throw new NoDataFoundException("該当する研究者情報が存在しません。",ex);}
        finally{
            //TODO:finallyでの処理
        }
        return strCount;
    }
// Horikoshi End

//2005/08/18 takano 重複チェックのロジック最適化により追加 ここから ---
    /**
     * 特定領域用重複チェック用の条件SQLを生成する。
     * @param shinseiDataInfo
     * @throws IllegalArgumentException 研究区分が想定外の場合
     * @return
     */
    private String makeQuery4DupTokutei(ShinseiDataInfo shinseiDataInfo)
            throws IllegalArgumentException {

        //------------------------------------------------------
        // 重複ルールの仕様理解を容易にするため、文字列結合に敢えて
        // StringBufferを使っていない記述がある。
        //------------------------------------------------------
        
        final String kenkyuKubun = StringUtils.defaultString(shinseiDataInfo.getKenkyuKubun());
        final String komokuNo    = StringUtils.defaultString(shinseiDataInfo.getRyouikiKoumokuNo());
        final String ryoikiNo    = EscapeUtil.toSqlString(StringUtils.defaultString(shinseiDataInfo.getRyouikiNo()));
        
        StringBuffer query = new StringBuffer();
        
        //===「計画研究」のとき ===
        if(IShinseiMaintenance.KUBUN_KEIKAKU.equals(kenkyuKubun)){
            //「計画研究」で「調整班」のとき
            if(IShinseiMaintenance.CHECK_ON.equals(shinseiDataInfo.getChouseiFlg())){
                query.append("AND ( ")
                     //--総括で異領域は重複エラー
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO ='" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--調整班は重複エラー
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND CHOSEIHAN = '" + IShinseiMaintenance.CHECK_ON + "') ")
                     //--その他で異領域は重複エラー
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SHIEN + "' ")
                     .append("  AND CHOSEIHAN <> '" + IShinseiMaintenance.CHECK_ON + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--公募研究は重複エラー
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KOUBO + "') ")
                     .append(") ")
                     ;
                
            //「計画研究」で「総括」のとき
            }else if(komokuNo.equals(IShinseiMaintenance.HAN_SOUKATU)){
                query.append("AND ( ")
                     //--総括は重複エラー
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO ='" + IShinseiMaintenance.HAN_SOUKATU + "') ")
                     //--調整班で異領域は重複エラー
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND CHOSEIHAN = '" + IShinseiMaintenance.CHECK_ON + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--その他で異領域は重複エラー
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SHIEN + "' ")
                     .append("  AND CHOSEIHAN <> '" + IShinseiMaintenance.CHECK_ON + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--公募研究で異領域は重複エラー
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KOUBO + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     .append(") ")
                     ;
                
            //「計画研究」で「支援」のとき
            }else if(komokuNo.equals(IShinseiMaintenance.HAN_SHIEN)){
                //支援のときは重複エラー無し
                query.append("AND ( ")
                     .append(" 1=0 ")       //必ずヒットしないようにする
                     .append(") ")
                     ;
                
            //「計画研究」で「その他」のとき
            }else{
                query.append("AND ( ")
                     //--総括で異領域は重複エラー
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO ='" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--調整班で異領域は重複エラー
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND CHOSEIHAN = '" + IShinseiMaintenance.CHECK_ON + "' ")
                     .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                     //--その他は重複エラー
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                     .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SHIEN + "' ")
                     .append("  AND CHOSEIHAN <> '" + IShinseiMaintenance.CHECK_ON + "') ")
                     //--公募研究は重複エラー
                     .append("OR ")
                     .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KOUBO+"') ")
                     .append(") ")
                     ;
                
            }
            
        //===「公募研究」のとき ===/
        }else if(IShinseiMaintenance.KUBUN_KOUBO.equals(kenkyuKubun)){
            query.append("AND ( ")
                 //--総括で異領域は重複エラー
                 .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                 .append("  AND KOMOKU_NO ='" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                 .append("  AND RYOIKI_NO <> '" + ryoikiNo + "') ")
                 //--調整班は重複エラー
                 .append("OR ")
                 .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                 .append("  AND CHOSEIHAN = '" + IShinseiMaintenance.CHECK_ON + "') ")
                 //--その他は重複エラー
                 .append("OR ")
                 .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KEIKAKU + "' ")
                 .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SOUKATU + "' ")
                 .append("  AND KOMOKU_NO <>'" + IShinseiMaintenance.HAN_SHIEN + "' ")
                 .append("  AND CHOSEIHAN <> '" + IShinseiMaintenance.CHECK_ON + "') ")
                 //--公募研究で同領域は重複エラー
                 .append("OR ")
                 .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_KOUBO + "' ")
                 .append("  AND RYOIKI_NO = '" + ryoikiNo + "') ")
                 .append(") ")
                 ;
            
        //===「終了研究」のとき ===/
        }else if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(kenkyuKubun)){
            query.append("AND ( ")
                 //--終了研究で同領域は重複エラー
                 .append(" (KENKYU_KUBUN = '" + IShinseiMaintenance.KUBUN_SHUURYOU + "' ")
                 .append("  AND RYOIKI_NO = '" + ryoikiNo + "') ")
                 .append(") ")
                 ;
            
        //=== 想定外の研究区分の場合は例外を発生する
        }else{
            String msg = "特定領域の重複チェックにおいて、研究区分が想定外です。kenkyuKubun="+kenkyuKubun;
            if(log.isDebugEnabled()){
                log.debug(msg);
            }
            throw new IllegalArgumentException(msg);
        }
        
        return query.toString();
    }
//2005/08/18 takano 重複チェックのロジック最適化により追加 ここまで ---

    /**
     * [前年度応募あり・新規]と[前年度応募なし・継続]の重複応募を許す条件SQLを生成する。
     * @param   shinseiDataInfo
     * @return
     */
    private String makeQuery4DupZennenShinki(ShinseiDataInfo shinseiDataInfo) {
        
        StringBuffer query = new StringBuffer();
        
        //新規で、前年度応募有りの場合
        if(IShinseiMaintenance.SHINSEI_NEW.equals(StringUtils.defaultString(shinseiDataInfo.getShinseiKubun()))
                && IShinseiMaintenance.ZENNEN_ON.equals(StringUtils.defaultString(shinseiDataInfo.getShinseiFlgNo()))) {
            //前年度応募なしで、継続の応募を重複チェックから除外
            //※空文字は未使用の'0'に変換。
            query.append("AND (NVL(SHINSEI_KUBUN, '0') <> '" + IShinseiMaintenance.SHINSEI_CONTINUE + "' ")
                 .append("OR NVL(SHINSEI_FLG_NO, '0') <> '" + IShinseiMaintenance.ZENNEN_OFF + "') ")
                 ;
        }
        //継続で、前年度応募なしの場合
        else if(IShinseiMaintenance.SHINSEI_CONTINUE.equals(StringUtils.defaultString(shinseiDataInfo.getShinseiKubun()))
                && IShinseiMaintenance.ZENNEN_OFF.equals(StringUtils.defaultString(shinseiDataInfo.getShinseiFlgNo()))) {
            //前年度応募有りで、新規の応募を重複チェックから除外
            query.append("AND (NVL(SHINSEI_KUBUN, '0') <> '" + IShinseiMaintenance.SHINSEI_NEW + "' ")
                 .append("OR NVL(SHINSEI_FLG_NO, '0') <> '" + IShinseiMaintenance.ZENNEN_ON + "') ")
                 ;
        }
        
        return query.toString();
    }

//2005/08/16 takano 重複チェックのロジック最適化により削除 ここから ---
// ★今回はコメントアウトにしましたが、最終的にはソース上から削除しておいた方が誤解が無くて良いと思います。
//
//// 20050713 重複チェックのSQL作成メソッド　※各事業で重複チェックが行われる場合のためSQL作成メソッド
//  /**
//   * 特定領域　重複チェックSQL作成
//   * @param shinseiDataInfo
//   * @return
//   */
//  private StringBuffer selectDupTokutei(
//          ShinseiDataInfo shinseiDataInfo
//          ){
//
//      String strQuery = "";
//      StringBuffer sbSQL= new StringBuffer(strQuery);
//      sbSQL.append("Select")
//              .append(" COUNT(SYSTEM_NO) " + IShinseiMaintenance.STR_COUNT + " ")
//              .append("FROM")
//              .append(" SHINSEIDATAKANRI ")
//              .append("WHERE")
//              .append(" JIGYO_ID = '"         + shinseiDataInfo.getJigyoId()  +   "' ");
//
//      //システムNOが存在する(修正、再開)場合には対象から外す
//      if(shinseiDataInfo.getSystemNo() != null || shinseiDataInfo.getSystemNo().length() > 0){
//          sbSQL.append("AND")
//              .append(" SYSTEM_NO <> '"       + shinseiDataInfo.getSystemNo() +   "' ")
//              ;
//      }
//
//      // 20050627
//      if(userInfo.getShinseishaInfo().getShozokuCd() != null &&
//          userInfo.getShinseishaInfo().getShozokuCd() != ""){
//          sbSQL.append("AND")
//              .append(" SHOZOKU_CD = '" + userInfo.getShinseishaInfo().getShozokuCd() + "' ");
//      }
//      // End
//
//      if(userInfo.getShinseishaInfo().getShinseishaId() != null &&
//              userInfo.getShinseishaInfo().getShinseishaId() != ""){
//              sbSQL.append("AND")
//                  .append(" SHINSEISHA_ID = '" + userInfo.getShinseishaInfo().getShinseishaId() + "' ")
//                  ;
//          }
//
//      sbSQL.append("AND")
//              .append(" DEL_FLG = "           + IShinseiMaintenance.CHECK_OFF +   " ")
//              ;
//
//// 20050809 重複チェックの条件に状況IDを追加
//      sbSQL.append(" AND JOKYO_ID >= '02' ")
//          .append(" AND JOKYO_ID <> '05' ")
//          ;
////Horikoshi
//
///** 計画研究 **/
//      if(IShinseiMaintenance.KUBUN_KEIKAKU.equals(shinseiDataInfo.getKenkyuKubun())){
//          if(IShinseiMaintenance.CHECK_ON.equals(shinseiDataInfo.getChouseiFlg()) ||
//              shinseiDataInfo.getRyouikiKoumokuNo().indexOf(IShinseiMaintenance.HAN_SOUKATU) == 0){
//              //調整班、または総括班だった場合
//              if(IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())){
//                  //新規
//                  sbSQL.append("AND ("
//                      //計画研究の対象
//                      + "(KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KEIKAKU + " AND RYOIKI_NO <> '" + shinseiDataInfo.getRyouikiNo() + "' AND (CHOSEIHAN = " + IShinseiMaintenance.CHECK_ON + " OR SUBSTR(KOMOKU_NO, 1, 1) <> '" + IShinseiMaintenance.HAN_SHIEN + "'))"
//                      //公募研究の対象
//                      + "OR (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KOUBO + " AND (CHOSEIHAN = " + IShinseiMaintenance.CHECK_ON + " OR SUBSTR(KOMOKU_NO, 1, 1) <> '" + IShinseiMaintenance.HAN_SHIEN + "')) "
//                      //終了研究の対象（新規の場合にはチェック無し）
//                      + ")")
//                      ;
//              }
//              else {
//                  //継続
//                  sbSQL.append("AND ("
//                      //計画研究の対象
//                      + "(KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KEIKAKU + " AND RYOIKI_NO <> '" + shinseiDataInfo.getRyouikiNo() + "' AND (CHOSEIHAN = " + IShinseiMaintenance.CHECK_ON + " OR SUBSTR(KOMOKU_NO, 1, 1) <> '" + IShinseiMaintenance.HAN_SHIEN + "'))"
//                      //公募研究の対象
//                      + "OR (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KOUBO + " AND (CHOSEIHAN = " + IShinseiMaintenance.CHECK_ON + " OR SUBSTR(KOMOKU_NO, 1, 1) <> '" + IShinseiMaintenance.HAN_SHIEN + "')) "
//                      //終了研究の対象（新規の場合には終了研究の存在をチェック）
//                      + "OR (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_SHUURYOU + "))")
//                      ;
//              }
//          }
//          else{
//              if(shinseiDataInfo.getRyouikiKoumokuNo().indexOf(IShinseiMaintenance.HAN_SHIEN) == 0){
//                  //支援班だった場合
//                  if(IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())){
//                      //新規（すべて重複OKのためチェック無し）
//                      return null;
//                  }
//                  else {
//                      //継続（終了研究が登録されていた場合のみエラー）
//                      sbSQL.append("AND (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_SHUURYOU + ")")
//                      ;
//                  }
//              }
//          }
//      }
//
///** 公募研究 **/
//      else if(IShinseiMaintenance.KUBUN_KOUBO.equals(shinseiDataInfo.getKenkyuKubun())){
//          sbSQL.append("AND ((KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KEIKAKU + " AND (CHOSEIHAN = " + IShinseiMaintenance.CHECK_ON + " OR SUBSTR(KOMOKU_NO, 1, 1) <> '" + IShinseiMaintenance.HAN_SHIEN + "')) "
//                  + "OR (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KOUBO + " AND RYOIKI_NO = '" + shinseiDataInfo.getRyouikiNo() + "') "
//                  + ")")
//                  ;
//      }
//
///** 終了研究 **/
//      else if(IShinseiMaintenance.KUBUN_SHUURYOU.equals(shinseiDataInfo.getKenkyuKubun())){
//          if(IShinseiMaintenance.SHINSEI_NEW.equals(shinseiDataInfo.getShinseiKubun())){
//              //新規
//              sbSQL.append("AND (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_SHUURYOU + ")")
//              ;
//          }
//          else{
//              //継続
//              sbSQL.append("AND ((KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_KEIKAKU + ") OR (KENKYU_KUBUN = " + IShinseiMaintenance.KUBUN_SHUURYOU + "))")
//              ;
//          }
//      }
//
//      return sbSQL;
//  }
//
//
//
//  /**
//   * 学術創成(公募)　重複チェックSQL作成
//   * @param shinseiDataInfo
//   * @return
//   */
//  private StringBuffer selectDupGakuKoubo(
//          ShinseiDataInfo shinseiDataInfo
//          ){
//
//      String strQuery = "";
//      StringBuffer sbSQL= new StringBuffer(strQuery);
//      sbSQL.append("Select")
//          .append(" COUNT(SYSTEM_NO) " + IShinseiMaintenance.STR_COUNT + " ")
//          .append("FROM")
//          .append(" SHINSEIDATAKANRI ")
//          .append("WHERE")
//          .append(" JIGYO_ID = '"         + shinseiDataInfo.getJigyoId()  +   "' ")
//          ;
//
//      //システムNOが存在する(修正、再開)場合には対象から外す
//      if(shinseiDataInfo.getSystemNo() != null || shinseiDataInfo.getSystemNo().length() > 0){
//          sbSQL.append("AND")
//              .append(" SYSTEM_NO <> '"       + shinseiDataInfo.getSystemNo() +   "' ")
//              ;
//      }
//
//      //所属研究機関ごと
//      if(userInfo.getShinseishaInfo().getShozokuCd() != null &&
//          userInfo.getShinseishaInfo().getShozokuCd() != ""){
//          sbSQL.append("AND")
//              .append(" SHOZOKU_CD = '" + userInfo.getShinseishaInfo().getShozokuCd() + "' ")
//              ;
//      }
//
//// 20050720
//      //研究者(応募者)ごと
////        if(userInfo.getShinseishaInfo().getKenkyuNo() != null &&
////                userInfo.getShinseishaInfo().getKenkyuNo() != ""){
////                sbSQL.append("AND")
////                    .append(" KENKYU_NO = '" + userInfo.getShinseishaInfo().getKenkyuNo() + "' ")
////                    ;
////            }
//      if(userInfo.getShinseishaInfo().getShinseishaId() != null &&
//              userInfo.getShinseishaInfo().getShinseishaId() != ""){
//              sbSQL.append("AND")
//                  .append(" SHINSEISHA_ID = '" + userInfo.getShinseishaInfo().getShinseishaId() + "' ")
//                  ;
//          }
//// ↑条件が不足していたため追加
//
//      //削除されていない
//      sbSQL.append("AND")
//              .append(" DEL_FLG = "           + IShinseiMaintenance.CHECK_OFF +   " ")
//              ;
//
//// 20050809 重複チェックの条件に状況IDを追加
//      sbSQL.append(" AND JOKYO_ID >= '02'")
//          .append(" AND JOKYO_ID <> '05'")
//          ;
////Horikoshi
//
//      return sbSQL;
//  }
//
//  /**
//   * 学術創成(非公募)　重複チェックSQL作成
//   * @param shinseiDataInfo
//   * @return
//   */
//  private StringBuffer selectDupGakuHikoubo(
//          ShinseiDataInfo shinseiDataInfo
//          ){
//
//      String strQuery = "";
//      StringBuffer sbSQL= new StringBuffer(strQuery);
//      sbSQL.append("Select")
//          .append(" COUNT(SYSTEM_NO)" + IShinseiMaintenance.STR_COUNT + " ")
//          .append("FROM")
//          .append(" SHINSEIDATAKANRI ")
//          .append("WHERE")
//          .append(" JIGYO_ID = '"         + shinseiDataInfo.getJigyoId()  +   "' ")
//          ;
//
//      //システムNOが存在する(修正、再開)場合には対象から外す
//      if(shinseiDataInfo.getSystemNo() != null || shinseiDataInfo.getSystemNo().length() > 0){
//          sbSQL.append("AND")
//              .append(" SYSTEM_NO <> '"       + shinseiDataInfo.getSystemNo() +   "' ")
//              ;
//      }
//
//      //所属研究機関ごと
//      if(userInfo.getShinseishaInfo().getShozokuCd() != null &&
//          userInfo.getShinseishaInfo().getShozokuCd() != ""){
//          sbSQL.append("AND")
//              .append(" SHOZOKU_CD = '" + userInfo.getShinseishaInfo().getShozokuCd() + "' ")
//              ;
//      }
//
//// 20050720
//      //研究者(応募者)ごと
////        if(userInfo.getShinseishaInfo().getKenkyuNo() != null &&
////                userInfo.getShinseishaInfo().getKenkyuNo() != ""){
////                sbSQL.append("AND")
////                    .append(" KENKYU_NO = '" + userInfo.getShinseishaInfo().getKenkyuNo() + "' ")
////                    ;
////            }
//      if(userInfo.getShinseishaInfo().getShinseishaId() != null &&
//              userInfo.getShinseishaInfo().getShinseishaId() != ""){
//              sbSQL.append("AND")
//                  .append(" SHINSEISHA_ID = '" + userInfo.getShinseishaInfo().getShinseishaId() + "' ")
//                  ;
//          }
//// ↑条件が不足していたため追加
//
//      //削除されていない
//      sbSQL.append("AND")
//              .append(" DEL_FLG = "           + IShinseiMaintenance.CHECK_OFF +   " ")
//              ;
//
//// 20050809 重複チェックの条件に状況IDを追加
//      sbSQL.append(" AND JOKYO_ID >= '02'")
//          .append(" AND JOKYO_ID <> '05'")
//          ;
////Horikoshi
//
//      return sbSQL;
//  }
//
//  /**
//   * 特別推進　重複チェックSQL作成
//   * @param shinseiDataInfo
//   * @return
//   */
//  private StringBuffer selectDupTokusui(
//          ShinseiDataInfo shinseiDataInfo
//          ){
//
//      String strQuery = "";
//      StringBuffer sbSQL= new StringBuffer(strQuery);
//      sbSQL.append("Select")
//          .append(" COUNT(SYSTEM_NO) " + IShinseiMaintenance.STR_COUNT + " ")
//          .append("FROM")
//          .append(" SHINSEIDATAKANRI ")
//          .append("WHERE")
//          .append(" JIGYO_ID = '"         + shinseiDataInfo.getJigyoId()  +   "' ")
//          ;
//
//      //システムNOが存在する(修正、再開)場合には対象から外す
//      if(shinseiDataInfo.getSystemNo() != null || shinseiDataInfo.getSystemNo().length() > 0){
//          sbSQL.append("AND")
//              .append(" SYSTEM_NO <> '"       + shinseiDataInfo.getSystemNo() +   "' ")
//              ;
//      }
//
//      //所属研究機関ごと
//      if(userInfo.getShinseishaInfo().getShozokuCd() != null &&
//          userInfo.getShinseishaInfo().getShozokuCd() != ""){
//          sbSQL.append("AND")
//              .append(" SHOZOKU_CD = '" + userInfo.getShinseishaInfo().getShozokuCd() + "' ")
//              ;
//      }
//
//// 20050720
//      //研究者(応募者)ごと
////        if(userInfo.getShinseishaInfo().getKenkyuNo() != null &&
////                userInfo.getShinseishaInfo().getKenkyuNo() != ""){
////                sbSQL.append("AND")
////                    .append(" KENKYU_NO = '" + userInfo.getShinseishaInfo().getKenkyuNo() + "' ")
////                    ;
////            }
//      if(userInfo.getShinseishaInfo().getShinseishaId() != null &&
//              userInfo.getShinseishaInfo().getShinseishaId() != ""){
//              sbSQL.append("AND")
//                  .append(" SHINSEISHA_ID = '" + userInfo.getShinseishaInfo().getShinseishaId() + "' ")
//                  ;
//          }
//// ↑条件が不足していたため追加
//
//      //削除されていない
//      sbSQL.append("AND")
//              .append(" DEL_FLG = "           + IShinseiMaintenance.CHECK_OFF +   " ")
//              ;
//
//// 20050809 重複チェックの条件に状況IDを追加
//      sbSQL.append(" AND JOKYO_ID >= '02'")
//          .append(" AND JOKYO_ID <> '05'")
//          ;
////Horikoshi
//
//      return sbSQL;
//  }
//
//// Horikoshi
//2005/08/16 takano 重複チェックのロジック最適化により削除 ここまで ---


    //2005/11/02 追加 ここから--------------------------------------------------------------------------------
    //理由　取り込みマスタで審査結果テーブルのデータ件数を取得するSQL文を追加（受理登録時ダミーデータを登録する件数が変更(6→12)になった為）
    /**
     * システム番号＋事業区分の審査結果データ件数を返す。
     * 
     * @param connection
     * @param systemNo システム番号
     * @param jigyoKubun 事業区分
     * @return int
     * @throws DataAccessException
     */
    public int countShinsaKekkaData(
            Connection connection,
            String     systemNo,
            String     jigyoKubun)
            throws DataAccessException {

        String query =
            "SELECT COUNT(*) FROM SHINSAKEKKA"+dbLink
                + " WHERE"
                + " SYSTEM_NO = ?"
                + " AND"
                + " JIGYO_KUBUN = ?"
                ;
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            //検索
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,systemNo);
            DatabaseUtil.setParameter(preparedStatement,index++,jigyoKubun);
            recordSet = preparedStatement.executeQuery();
            int count = 0;
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            return count;
        } catch (SQLException ex) {
            throw new DataAccessException("審査結果テーブル検索実行中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    //2005/11/02 追加 ここまで--------------------------------------------------------------------------------

//   2006/06/15 dyh add start
    /**
     * 研究計画調書一覧データを返す。
     * 
     * @param connection
     * @param ryoikiNo 領域番号
     * @param jokyoIds 申請状況ID
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectKeikakuTyosyoList(
            Connection connection,
            String ryoikiNo,
            String[] jokyoIds)
            throws DataAccessException,NoDataFoundException {

        // 検索条件:領域番号(RYOIKI_NO),申請状況ID(JOKYO_ID),削除フラグ(DEL_FLG)
        StringBuffer addQuery = new StringBuffer();
        if(!StringUtil.isBlank(ryoikiNo)){
            addQuery.append(" AND ");
            addQuery.append(" S.RYOIKI_NO = '");
            addQuery.append(EscapeUtil.toSqlString(ryoikiNo));
            addQuery.append("' ");
        }
        if(jokyoIds != null && jokyoIds.length > 0){
            addQuery.append(" AND ");
            addQuery.append(" S.JOKYO_ID IN(");
            addQuery.append(StringUtil.changeArray2CSV(jokyoIds,true));
            addQuery.append(") ");
        }
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" R.RYOIKI_NAME,");          // 応募領域名
        query.append(" S.KOMOKU_NO,");            // 研究項目番号
        query.append(" S.SYSTEM_NO,");            // システム番号
        query.append(" S.KENKYU_KUBUN,");         // 研究区分
        query.append(" S.CHOSEIHAN,");            // 調整班
        query.append(" S.NAME_KANJI_SEI,");       // 申請者氏名（漢字等-姓）
        query.append(" S.NAME_KANJI_MEI,");       // 申請者氏名（漢字等-名）
        query.append(" S.SHOZOKU_NAME_RYAKU,");   // 所属機関名（略称）
        query.append(" S.BUKYOKU_NAME_RYAKU,");   // 部局名（略称）
        query.append(" S.SHOKUSHU_NAME_RYAKU,");  // 職名（略称）
        query.append(" S.KENKYU_NO,");            // 申請者研究者番号
        query.append(" S.KADAI_NAME_KANJI,");     // 研究課題名(和文）
        query.append(" S.EDITION,");              // 版
        query.append(" S.SAKUSEI_DATE,");         // 申請書作成日
        query.append(" S.PDF_PATH, ");            // PDFの格納パス
        
        //2006.11.06 iso受付番号（ 所属研究機関番号も含む）でソートするよう変更
//        query.append(" SUBSTR(S.UKETUKE_NO,7,4) AS UKETUKE_NO ");   // 整理番号
        query.append(" S.UKETUKE_NO ");			  // 整理番号
        
        query.append(" FROM SHINSEIDATAKANRI S ");// 申請データ管理テーブル
        query.append(dbLink);
        query.append(" INNER JOIN RYOIKIKEIKAKUSHOINFO R ");// 領域計画書（概要）情報管理テーブル
        query.append(dbLink);
        query.append(" ON S.RYOIKI_NO =　R.KARIRYOIKI_NO ");
        query.append(" WHERE");
        query.append(" S.DEL_FLG = 0");
        query.append(" AND R.DEL_FLG = 0");
        query.append(addQuery);
        query.append(" ORDER BY S.KOMOKU_NO ASC,S.CHOSEIHAN DESC,UKETUKE_NO ASC");

        // for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        // データリスト取得
        List dataList = SelectUtil.select(connection, query.toString());
        return SortUtil.sortByKomokuNo(dataList);
    }

    /**
     * 研究計画調書一覧データを返す。
     * 
     * @param connection
     * @param ryoikiNo 領域番号
     * @param jokyoIds 申請状況ID
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectKeikakuTyosyoList(
            Connection connection,
            String ryoikiNo)
            throws DataAccessException,NoDataFoundException {

        // 検索条件:
        // 領域番号(RYOIKI_NO=渡される「仮領域番号」)
        // 申請状況ID(JOKYO_ID=[04,06,07,08,09,10,11,12,21,22,23,24]
        //       or (JOKYO_ID=[02,03,05] and SAISHINSEI_FLG=[3]))
        // 削除フラグ(DEL_FLG=0)
        StringBuffer addQuery = new StringBuffer();
        if(!StringUtil.isBlank(ryoikiNo)){
            addQuery.append(" AND ");
            addQuery.append(" S.RYOIKI_NO = '");
            addQuery.append(EscapeUtil.toSqlString(ryoikiNo));
            addQuery.append("' ");
        }
        String[] jokyoIds1 = new String[]{
                StatusCode.STATUS_GAKUSIN_SHORITYU,          // 学振受付中:04
                StatusCode.STATUS_GAKUSIN_JYURI,             // 学振受理:06
                StatusCode.STATUS_GAKUSIN_FUJYURI,           // 学振不受理:07
                StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, // 審査員割り振り処理後:08
                StatusCode.STATUS_WARIFURI_CHECK_KANRYO,     // 割り振りチェック完了:09
                StatusCode.STATUS_1st_SHINSATYU,             // 一次審査中:10
                StatusCode.STATUS_1st_SHINSA_KANRYO,         // 一次審査：判定:11
                StatusCode.STATUS_2nd_SHINSA_KANRYO,         // 二次審査完了:12
                StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN,    // 領域代表者確認中:21
                StatusCode.STATUS_RYOUIKIDAIHYOU_KYAKKA,     // 領域代表者却下:22
                StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI,// 領域代表者確定済み:23
                StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE     // 領域代表者所属研究機関受付中:24
        };
        String[] jokyoIds2 = new String[]{
                StatusCode.STATUS_SHINSEISHO_MIKAKUNIN,      // 申請書未確認:02
                StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,   // 所属機関受付中:03
                StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,       // 所属機関却下:05
        };
        addQuery.append(" AND ");
        addQuery.append(" (S.JOKYO_ID IN(");
        addQuery.append(StringUtil.changeArray2CSV(jokyoIds1,true));
        addQuery.append(") ");
        addQuery.append(" OR (");
        addQuery.append(" S.JOKYO_ID IN(");
        addQuery.append(StringUtil.changeArray2CSV(jokyoIds2,true));
        addQuery.append(") AND S.SAISHINSEI_FLG = '3'))");

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" S.SYSTEM_NO,");            // システム番号
        query.append(" S.KOMOKU_NO,");            // 研究項目番号
        query.append(" S.KENKYU_KUBUN,");         // 研究区分
        query.append(" S.CHOSEIHAN,");            // 調整班
        query.append(" S.NAME_KANJI_SEI,");       // 申請者氏名（漢字等-姓）
        query.append(" S.NAME_KANJI_MEI,");       // 申請者氏名（漢字等-名）
        query.append(" S.SHOZOKU_NAME_RYAKU,");   // 所属機関名（略称）
        query.append(" S.BUKYOKU_NAME_RYAKU,");   // 部局名（略称）
        query.append(" S.SHOKUSHU_NAME_RYAKU,");  // 職名（略称）
        query.append(" S.KENKYU_NO,");            // 申請者研究者番号
        query.append(" S.KADAI_NAME_KANJI,");     // 研究課題名(和文）
        query.append(" S.EDITION,");              // 版
        query.append(" S.SAKUSEI_DATE,");         // 申請書作成日
        query.append(" S.RYOIKI_KAKUTEI_DATE,");  // 領域代表者確定日
        //query.append(" S.PDF_PATH, ");            // PDFの格納パス
        query.append(" M.RYOIKIDAIHYOSHA_HYOJI, ");// 領域代表者向け表示
        query.append(" M.JOKYO_NAME, ");          // 申請状況名称（総称）
        query.append(" S.JOKYO_ID,");             // 申請状況ID(状況名称用)
        query.append(" S.JIGYO_ID,");             // 事業ID(状況名称用)
        query.append(" S.SAISHINSEI_FLG,");       // 再申請フラグ(状況名称用)
        query.append(" S.KEKKA1_ABC,");           // １次審査結果(ABC)(状況名称用)
        query.append(" S.KEKKA1_TEN,");           // １次審査結果(点数)(状況名称用)
        query.append(" S.KEKKA2");                // ２次審査結果(状況名称用)
        query.append(" FROM SHINSEIDATAKANRI S"); // 申請データ管理テーブル
        query.append(dbLink);
        query.append(" INNER JOIN RYOIKIKEIKAKUSHOINFO R");// 領域計画書（概要）情報管理テーブル
        query.append(dbLink);
        query.append(" ON S.RYOIKI_NO =　R.KARIRYOIKI_NO ");
        query.append(" INNER JOIN MASTER_STATUS M");// 申請状況マスタテーブル
        query.append(dbLink);
        query.append(" ON S.JOKYO_ID =　M.JOKYO_ID ");
        query.append(" AND S.SAISHINSEI_FLG =　M.SAISHINSEI_FLG ");
        query.append(" WHERE");
        query.append(" S.DEL_FLG = 0");
        query.append(" AND R.DEL_FLG = 0");
        query.append(addQuery);

        //2006.11.06 iso ソート順を受付番号へ変更（A.JURI_SEIRI_NOは学創用）
//        query.append(" ORDER BY S.KOMOKU_NO ASC,S.CHOSEIHAN DESC,S.JURI_SEIRI_NO ASC");
//		<!-- UPDATE　START 2007/07/10 BIS 張楠 -->  
//		古いコード : query.append(" ORDER BY S.KOMOKU_NO ASC,S.CHOSEIHAN DESC,S.UKETUKE_NO ASC");        
        query.append(" ORDER BY S.KOMOKU_NO ASC,S.CHOSEIHAN DESC,S.HYOJIJUN,S.UKETUKE_NO ASC");
//		<!-- UPDATE　END 2007/07/10 BIS 張楠 -->        
        // for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }

        // データリスト取得
        List dataList = SelectUtil.select(connection, query.toString());
        return SortUtil.sortByKomokuNo(dataList);
    }
// 2006/06/15 dyh add end

    //2006/06/16 Wang Xiancheng add start
    /**
     * 指定検索条件に該当する申請書データを取得する。
     * @param connection
     * @param searchInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException  申請者情報がセットされていなかった場合
     */
    public Page searchConfirmInfo(Connection connection,
            ShinseiSearchInfo searchInfo) throws DataAccessException,
            NoDataFoundException, ApplicationException {

        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" A.SYSTEM_NO,");             // システム受付番号
        select.append(" A.UKETUKE_NO,");            // 申請番号
        select.append(" A.JIGYO_ID,");              // 事業ID
        select.append(" A.NENDO,");                 // 年度
        select.append(" A.KAISU,");                 // 回数
        select.append(" A.JIGYO_NAME,");            // 事業名
        select.append(" A.SHINSEISHA_ID,");         // 申請者ID
        select.append(" A.SAKUSEI_DATE,");          // 申請書作成日
        select.append(" A.SHONIN_DATE,");           // 所属機関承認日
        select.append(" A.NAME_KANJI_SEI,");        // 申請者氏名（漢字等-姓）
        select.append(" A.NAME_KANJI_MEI,");        // 申請者氏名（漢字等-名）
        select.append(" A.KENKYU_NO,");             // 申請者研究者番号
        select.append(" A.SHOZOKU_CD,");            // 所属機関コード
        select.append(" A.SHOZOKU_NAME,");          // 所属機関名
        select.append(" A.SHOZOKU_NAME_RYAKU,");    // 所属機関名（略称）
        select.append(" A.BUKYOKU_NAME,");          // 部局名
        select.append(" A.BUKYOKU_NAME_RYAKU,");    // 部局名（略称）
        select.append(" A.SHOKUSHU_NAME_KANJI,");   // 職名
        select.append(" A.SHOKUSHU_NAME_RYAKU,");   // 職名（略称）
        select.append(" A.KADAI_NAME_KANJI,");      // 研究課題名(和文）
        select.append(" A.JIGYO_KUBUN,");           // 事業区分
        select.append(" A.KEKKA1_ABC,");            // 1次審査結果(ABC)
        select.append(" A.KEKKA1_TEN,");            // 1次審査結果(点数)
        select.append(" A.KEKKA1_TEN_SORTED,");     // 1次審査結果(点数順)
        select.append(" A.KEKKA2,");                // 2次審査結果
        select.append(" A.JOKYO_ID,");              // 申請状況ID
        select.append(" A.SAISHINSEI_FLG,");        // 再申請フラグ
        select.append(" A.KEI_NAME_RYAKU,");        // 系統の区分（略称）
        select.append(" A.KANTEN_RYAKU,");          // 推薦の観点（略称）
        select.append(" A.NENREI,");                // 年齢
        select.append(" CHCKLIST.KAKUTEI_DATE,");   // 確定日
        // コメント確認機能追加のため
        select.append(" A.JURI_BIKO,");             // 受理備考
        select.append(" A.JURI_SEIRI_NO,");         // 受理整理番号
        // 申請状況一覧で基盤の学振受付中以降の場合は申請書を修正できないようにするため
        select.append("CASE WHEN CHCKLIST.SHOZOKU_CD IS NULL THEN 'TRUE' ELSE 'FALSE' END EDITABLE, ");
        select.append(" DECODE");
        select.append(" (");
        select.append("  NVL(A.SUISENSHO_PATH,'null') ");
        select.append("  ,'null','FALSE'");         // 推薦書パスがNULLのとき
        select.append("  ,      'TRUE'");           // 推薦書パスがNULL以外のとき
        select.append(" ) SUISENSHO_FLG, ");        // 推薦書登録フラグ
        select.append(" B.UKETUKEKIKAN_END,");      // 学振受付期限（終了）
        //add start ly 2006/07/10
        select.append("B.RYOIKI_KAKUTEIKIKAN_END,");//領域代表者確定締切日
        select.append(" DECODE");
        select.append(" (");
        select.append("  SIGN( ");
        select.append("       TO_DATE( TO_CHAR(B.RYOIKI_KAKUTEIKIKAN_END,'YYYY/MM/DD'), 'YYYY/MM/DD' ) ");
        select.append("     - TO_DATE( TO_CHAR(SYSDATE           ,'YYYY/MM/DD'), 'YYYY/MM/DD' ) ");
        select.append("      )"); select.append("  ,0 , '0'"); // 現在時刻と同じ場合
        select.append("  ,-1 , '1'");               // 現在時刻の方が受付期限より前
        select.append("  ,1, '0'");                 // 現在時刻の方が受付期限より後
        select.append(" ) RYOIKI_KAKUTEIKIKAN_END_FLAG,"); // 領域代表者確定締切日
        //add end ly 2006/07/10
        select.append(" B.HOKAN_DATE,");            // データ保管日
        select.append(" B.YUKO_DATE,");             // 保管有効期限
        select.append(" DECODE");
        select.append(" (");
        select.append("  SIGN( ");
        select.append("       TO_DATE( TO_CHAR(B.UKETUKEKIKAN_END,'YYYY/MM/DD'), 'YYYY/MM/DD' ) ");
        select.append("     - TO_DATE( TO_CHAR(SYSDATE           ,'YYYY/MM/DD'), 'YYYY/MM/DD' ) ");

        //2006.11.16 iso 締切日当日ラジオボタンが出ないバグを修正
//        select.append("      )"); select.append("  ,0 , 'TRUE'"); // 現在時刻と同じ場合
        select.append("      )"); select.append("  ,0 , 'FALSE'"); // 現在時刻と同じ場合
        
        select.append("  ,-1 , 'TRUE'");            // 現在時刻の方が受付期限より前
        select.append("  ,1, 'FALSE'");             // 現在時刻の方が受付期限より後
        select.append(" ) UKETUKE_END_FLAG,");      // 学振受付期限（終了）到達フラグ
        select.append(" DECODE" ).append(" (").append("  NVL(A.PDF_PATH,'null') ");
        select.append("  ,'null','FALSE'");         // PDFの格納パスがNULLのとき
        select.append("  ,      'TRUE'");           // PDFの格納パスがNULL以外のとき
        select.append(" ) PDF_PATH_FLG, ");         // PDFの格納パスフラグ
        select.append(" DECODE").append(" (").append("  NVL(C.SYSTEM_NO,'null') ");
        select.append("  ,'null','FALSE'");         // 添付ファイルがNULLのとき
        select.append("  ,      'TRUE'");           // 添付ファイルがNULL以外のとき
        select.append(" ) TENPUFILE_FLG, ");        // 添付ファイルフラグ
        //add start ly 2006/06/21
        select.append(" M.SHOZOKU_HYOJI,");         //応募状況
        //add end ly 2006/06/21
        select.append("RYOIKI.RYOIKIKEIKAKUSHO_KAKUTEI_FLG ");
        select.append(" FROM");
        select.append(" SHINSEIDATAKANRI").append(dbLink).append(" A"); // 申請データ管理テーブル

        // 部局担当者の場合の条件追加
        select.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" B");
        select.append(" ON A.JIGYO_ID = B.JIGYO_ID ");
        select.append(" LEFT JOIN TENPUFILEINFO").append(dbLink).append(" C ");
        select.append(" ON A.SYSTEM_NO = C.SYSTEM_NO ");
        select.append(" AND C.TENPU_PATH IS NOT NULL ");

        // 部局担当者の担当部局管理テーブルに値がある場合に条件を追加する
        if (userInfo.getBukyokutantoInfo() != null
                && userInfo.getBukyokutantoInfo().getTantoFlg()) {
            select.append(" INNER JOIN TANTOBUKYOKUKANRI T");
            select.append(" ON T.SHOZOKU_CD = A.SHOZOKU_CD");
            select.append(" AND T.BUKYOKU_CD = A.BUKYOKU_CD");
            select.append(" AND T.BUKYOKUTANTO_ID = '");
            select.append(EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo()
                    .getBukyokutantoId()));
            select.append("' ");
        }

        // 申請状況一覧で基盤の学振受付中以降の場合は申請書を修正できないようにするため
        select.append("LEFT JOIN CHCKLISTINFO CHCKLIST ");
        select.append("ON CHCKLIST.JIGYO_ID = A.JIGYO_ID ");
        select.append("AND CHCKLIST.SHOZOKU_CD = A.SHOZOKU_CD ");
        select.append("AND CHCKLIST.JOKYO_ID <> '03' ");

        // 領域計画書確定済み
        select.append("LEFT JOIN RYOIKIKEIKAKUSHOINFO RYOIKI ");
        select.append("ON RYOIKI.KARIRYOIKI_NO = A.RYOIKI_NO ");
        select.append("AND RYOIKI.DEL_FLG = 0 ");
        select.append("LEFT JOIN MASTER_STATUS M ");
        select.append("ON M.JOKYO_ID = A.JOKYO_ID ");
        select.append("AND M.SAISHINSEI_FLG = A.SAISHINSEI_FLG ");
        select.append("WHERE");
        select.append(" A.JIGYO_ID = B.JIGYO_ID");// 事業IDが同じもの

        // 検索条件を元にSQL文を生成する。
        String query = getQueryString(select.toString(), searchInfo);

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }
        // ページ取得
        return SelectUtil.selectPageInfo(connection, (SearchInfo) searchInfo,
                query);
    }
    //2006/06/16 Wang Xiancheng add end
    
    // 2006/06/20 liujia add start------------------------------
    /**
     * 研究経費表データを返す。
     * 
     * @param connection
     * @param keihiInfo 研究経費情報
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectKenkyuKeihiList(Connection connection,
            KenkyuSoshikiKenkyushaInfo keihiInfo)
            throws DataAccessException, NoDataFoundException {

        List dataList = null;

        // 検索条件:申請状況ID(JOKYO_ID),削除フラグ(DEL_FLG)
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append("A.KOMOKU_NO, ");// 研究項目番号
        query.append("SUM(A.KEIHI1) AS KEIHI1, ");// 1年目研究経費
        query.append("SUM(A.KEIHI2) AS KEIHI2, ");// 2年目研究経費
        query.append("SUM(A.KEIHI3) AS KEIHI3, ");// 3年目研究経費
        query.append("SUM(A.KEIHI4) AS KEIHI4, ");// 4年目研究経費
        query.append("SUM(A.KEIHI5) AS KEIHI5, ");// 5年目研究経費
        query.append("SUM(A.KEIHI6) AS KEIHI6, ");// 6年目研究経費
        query.append("SUM(A.KEIHI_TOTAL) AS KEIHI_TOTAL, ");// 総計-研究経費
        query.append(" B.KENKYU_SYOKEI_1,");      // 研究経費（1年目)-小計
        query.append(" B.KENKYU_UTIWAKE_1,");     // 研究経費（1年目)-内訳
        query.append(" B.KENKYU_SYOKEI_2,");      // 研究経費（2年目)-小計
        query.append(" B.KENKYU_UTIWAKE_2,");     // 研究経費（2年目)-内訳
        query.append(" B.KENKYU_SYOKEI_3,");      // 研究経費（3年目)-小計
        query.append(" B.KENKYU_UTIWAKE_3,");     // 研究経費（3年目)-内訳
        query.append(" B.KENKYU_SYOKEI_4,");      // 研究経費（4年目)-小計
        query.append(" B.KENKYU_UTIWAKE_4,");     // 研究経費（4年目)-内訳
        query.append(" B.KENKYU_SYOKEI_5,");      // 研究経費（5年目)-小計
        query.append(" B.KENKYU_UTIWAKE_5,");     // 研究経費（5年目)-内訳
        query.append(" B.KENKYU_SYOKEI_6,");      // 研究経費（6年目)-小計
        query.append(" B.KENKYU_UTIWAKE_6 ");     // 研究経費（6年目)-内訳
        query.append("FROM SHINSEIDATAKANRI A ");
        query.append("INNER JOIN RYOIKIKEIKAKUSHOINFO B ");// 領域計画書（概要）情報管理テーブル
        query.append("ON A.RYOIKI_NO =　B.KARIRYOIKI_NO ");
        query.append("WHERE A.JOKYO_ID IN(");
        query.append(StringUtil.changeArray2CSV(keihiInfo.getJokyoId(), true));
        query.append(")");
        query.append(" AND A.DEL_FLG = 0");
        query.append(" AND B.DEL_FLG = 0");//2006/08/01 add liuJia
        if (!StringUtil.isBlank(keihiInfo.getKariryoikiNo())) {
            query.append(" AND B.KARIRYOIKI_NO = '");
            query.append(EscapeUtil.toSqlString(keihiInfo.getKariryoikiNo()));
            query.append("' ");
        }
        query.append(" GROUP BY A.KOMOKU_NO,");
        query.append(" B.KENKYU_SYOKEI_1,");
        query.append(" B.KENKYU_UTIWAKE_1,");
        query.append(" B.KENKYU_SYOKEI_2,");
        query.append(" B.KENKYU_UTIWAKE_2,");
        query.append(" B.KENKYU_SYOKEI_3,");
        query.append(" B.KENKYU_UTIWAKE_3,");
        query.append(" B.KENKYU_SYOKEI_4,");
        query.append(" B.KENKYU_UTIWAKE_4,");
        query.append(" B.KENKYU_SYOKEI_5,");
        query.append(" B.KENKYU_UTIWAKE_5,");
        query.append(" B.KENKYU_SYOKEI_6,");
        query.append(" B.KENKYU_UTIWAKE_6 ");
        query.append("ORDER BY A.KOMOKU_NO ASC ");

        // データリスト取得
        dataList = SelectUtil.select(connection, query.toString());
        return SortUtil.sortByKomokuNo(dataList);
    }
    // 2006/06/20 liujia add end------------------------------------------

    // 2006/06/16 追加 李義華 ここから
    /**
     * 研究組織表データを返す。
     * 
     * @param connection
     * @param kenkyuSoshikiKenkyushaInfo
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    /**
    public List selectKenkyuSosiki(Connection connection,
            KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo)
            throws DataAccessException, NoDataFoundException {

        // 検索条件:申請状況ID(JOKYO_ID),削除フラグ(DEL_FLG)
        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" A.KOMOKU_NO,");          // 研究項目番号
        query.append(" A.KENKYU_KUBUN,");       // 研究区分
        query.append(" A.CHOSEIHAN,");          // 調整班
        query.append(" A.KADAI_NAME_KANJI,");   // 研究課題名(和文）
        query.append(" A.NENDO,");              // 事業年度
        query.append(" A.KEIHI1,");             // 1年目研究経費
        query.append(" A.KEIHI2,");             // 2年目研究経費
        query.append(" A.KEIHI3,");             // 3年目研究経費
        query.append(" A.KEIHI4,");             // 4年目研究経費
        query.append(" A.KEIHI5,");             // 5年目研究経費
        query.append(" A.KEIHI6,");             // 6年目研究経費
        query.append(" A.KENKYU_NO,");          // 申請者研究者番号
        query.append(" A.NAME_KANJI_SEI,");     // 申請者氏名（漢字等-姓）
        query.append(" A.NAME_KANJI_MEI,");     // 申請者氏名（漢字等-名）
        query.append(" A.SHOZOKU_CD,");         // 所属研究機関番号
        query.append(" A.SHOZOKU_NAME_RYAKU,"); // 所属機関名（略称）
        query.append(" A.BUKYOKU_CD,");         // 部局コード
        
        //2006.10.30 iso 組織表には正式名称を出すよう修正
        query.append(" A.BUKYOKU_NAME,"); // 部局名
        
        query.append(" A.BUKYOKU_NAME_RYAKU,"); // 部局名（略称）
        query.append(" A.SHOKUSHU_CD,");        // 職名コード
        query.append(" A.SHOKUSHU_NAME_RYAKU,");// 職名

        //2006.11.06 iso ソート順を受付番号へ変更（A.JURI_SEIRI_NOは学創用）
//        query.append(" A.JURI_SEIRI_NO,");      // 整理番号
        
        query.append(" COUNT(*) COUNT ");       // 構成人数
        query.append(" FROM SHINSEIDATAKANRI A ");// 申請データ管理テーブル
        query.append(" INNER JOIN RYOIKIKEIKAKUSHOINFO B ");// 領域計画書（概要）情報管理テーブル
        query.append(" ON A.RYOIKI_NO =　B.KARIRYOIKI_NO AND B.DEL_FLG = 0"); // 領域番号
        query.append(" INNER JOIN KENKYUSOSHIKIKANRI C ");// 研究組織表管理テーブル
        query.append(" ON A.SYSTEM_NO =　C.SYSTEM_NO ");
        query.append("WHERE");
        query.append(" A.RYOIKI_NO = '");
        query.append(EscapeUtil.toSqlString(kenkyuSoshikiKenkyushaInfo.getKariryoikiNo())); // 仮領域番号
        query.append("' ");
        query.append(" AND JOKYO_ID IN(");
        query.append(StringUtil.changeArray2CSV(kenkyuSoshikiKenkyushaInfo.getJokyoId(), true));
        query.append(") ");
        query.append(" AND A.DEL_FLG = 0");
        query.append("GROUP BY");
        query.append(" A.KOMOKU_NO,A.KENKYU_KUBUN,A.CHOSEIHAN,A.KADAI_NAME_KANJI,A.NENDO,");
        query.append(" A.KEIHI1,A.KEIHI2, A.KEIHI3, A.KEIHI4, A.KEIHI5, A.KEIHI6,A.KENKYU_NO,");
        query.append(" A.NAME_KANJI_SEI, A.NAME_KANJI_MEI, A.SHOZOKU_CD, A.SHOZOKU_NAME_RYAKU, A.BUKYOKU_CD,");

        //2006.11.06 iso ソート順を受付番号へ変更（A.JURI_SEIRI_NOは学創用）
//        //2006.10.30 iso 組織表には正式名称を出すよう修正
////        query.append(" A.BUKYOKU_NAME_RYAKU, A.SHOKUSHU_CD, A.SHOKUSHU_NAME_RYAKU,A.JURI_SEIRI_NO ");
//        query.append(" A.BUKYOKU_NAME, A.BUKYOKU_NAME_RYAKU, A.SHOKUSHU_CD, A.SHOKUSHU_NAME_RYAKU,A.JURI_SEIRI_NO ");
        query.append(" A.BUKYOKU_NAME, A.BUKYOKU_NAME_RYAKU, A.SHOKUSHU_CD, A.SHOKUSHU_NAME_RYAKU, A.UKETUKE_NO ");
        
        query.append("ORDER BY");
        
        //2006.11.06 iso ソート順を受付番号へ変更（A.JURI_SEIRI_NOは学創用）
//        query.append(" A.KOMOKU_NO ASC,A.CHOSEIHAN DESC,A.JURI_SEIRI_NO ASC");
        query.append(" A.KOMOKU_NO ASC,A.CHOSEIHAN DESC,A.UKETUKE_NO ASC");

        // データリスト取得
        List dataList = SelectUtil.select(connection, query.toString());
        return SortUtil.sortByKomokuNo(dataList);
    }
    // 2006/06/16 追加 李義華 ここまで
    */
//	<!-- UPDATE　START　 2007/07/16 BIS 張楠 -->  
    public List selectKenkyuSosiki(Connection connection,
            KenkyuSoshikiKenkyushaInfo kenkyuSoshikiKenkyushaInfo)
            throws DataAccessException, NoDataFoundException {
        // 検索条件:申請状況ID(JOKYO_ID),削除フラグ(DEL_FLG)
        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" A.SYSTEM_NO,");          // システム受付番号
        query.append(" A.RYOIKI_NO,");          // 仮領域番号
        query.append(" A.HYOJIJUN,");           // 表示順
        query.append(" A.KOMOKU_NO,");          // 研究項目番号
        query.append(" A.KENKYU_KUBUN,");       // 研究区分
        query.append(" A.CHOSEIHAN,");          // 調整班
        query.append(" A.KADAI_NAME_KANJI,");   // 研究課題名(和文）
        query.append(" A.NENDO,");              // 事業年度
        query.append(" A.KEIHI1,");             // 1年目研究経費
        query.append(" A.KEIHI2,");             // 2年目研究経費
        query.append(" A.KEIHI3,");             // 3年目研究経費
        query.append(" A.KEIHI4,");             // 4年目研究経費
        query.append(" A.KEIHI5,");             // 5年目研究経費
        query.append(" A.KEIHI6,");             // 6年目研究経費
        query.append(" A.KENKYU_NO,");          // 申請者研究者番号
        query.append(" A.NAME_KANJI_SEI,");     // 申請者氏名（漢字等-姓）
        query.append(" A.NAME_KANJI_MEI,");     // 申請者氏名（漢字等-名）
        query.append(" A.SHOZOKU_CD,");         // 所属研究機関番号
        query.append(" A.SHOZOKU_NAME,"); 		// 所属機関名
        query.append(" A.BUKYOKU_CD,");         // 部局コード
        query.append(" A.BUKYOKU_NAME,"); // 部局名
        query.append(" A.BUKYOKU_NAME_RYAKU,"); // 部局名（略称）
        query.append(" A.SHOKUSHU_CD,");        // 職名コード
        query.append(" A.SHOKUSHU_NAME_KANJI,");// 職名
        query.append(" COUNT(*) COUNT ");       // 構成人数
        query.append(" FROM SHINSEIDATAKANRI A ");// 申請データ管理テーブル
        query.append(" INNER JOIN RYOIKIKEIKAKUSHOINFO B ");// 領域計画書（概要）情報管理テーブル
        query.append(" ON A.RYOIKI_NO =　B.KARIRYOIKI_NO AND B.DEL_FLG = 0"); // 領域番号
        query.append(" INNER JOIN KENKYUSOSHIKIKANRI C ");// 研究組織表管理テーブル
        query.append(" ON A.SYSTEM_NO =　C.SYSTEM_NO ");
        query.append("WHERE");
        query.append(" A.RYOIKI_NO = '");
        query.append(EscapeUtil.toSqlString(kenkyuSoshikiKenkyushaInfo.getKariryoikiNo())); // 仮領域番号
        query.append("' AND");
        query.append(" JOKYO_ID IN(");
        query.append(StringUtil.changeArray2CSV(kenkyuSoshikiKenkyushaInfo.getJokyoId(), true));
        query.append(") ");
        query.append(" AND A.DEL_FLG = 0 ");
        query.append(" GROUP BY");
        query.append(" A.RYOIKI_NO,A.HYOJIJUN,A.KOMOKU_NO,A.KENKYU_KUBUN,A.CHOSEIHAN,A.KADAI_NAME_KANJI,A.NENDO,");
        query.append(" A.KEIHI1,A.KEIHI2, A.KEIHI3, A.KEIHI4, A.KEIHI5, A.KEIHI6,A.KENKYU_NO,");
        query.append(" A.NAME_KANJI_SEI, A.NAME_KANJI_MEI, A.SHOZOKU_CD, A.SHOZOKU_NAME, A.BUKYOKU_CD,");
        query.append(" A.BUKYOKU_NAME, A.BUKYOKU_NAME_RYAKU, A.SHOKUSHU_CD, A.SHOKUSHU_NAME_KANJI, A.UKETUKE_NO, A.SYSTEM_NO ");
        query.append("ORDER BY");
        query.append(" A.KOMOKU_NO ASC,A.CHOSEIHAN DESC,A.HYOJIJUN ASC,A.UKETUKE_NO ASC");
        
        List dataList = SelectUtil.select(connection, query.toString());
		return SortUtil.sortByKomokuNo(dataList);
    }
//  <!-- UPDATE　END　 2007/07/16 BIS 張楠 --> 
    
    
    // 2006/06/16 追加 mcj ここから
    /**
     * 申請状況IDのみを指定されたステータスへ更新する。
     * 
     * @param connection
     * @param pkInfo
     * @param status
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateSyoninCancel(
            Connection connection,
            ShinseiDataPk pkInfo,
            String status)
            throws DataAccessException, NoDataFoundException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI" + dbLink
                + " SET"
                + " JOKYO_ID = ?,"           // 申請状況ID       
                + " SHONIN_DATE = null"        // 所属機関承認日              
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,status);
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //システム受付番号
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報ステータス更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
    
    /**
     * 申請状況IDのみを指定されたステータスへ更新する。
     * @param connection
     * @param pkInfo キー情報
     * @param status 状況
     * @param jyuriDate 受理日付
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateIkkastu(
            Connection connection,
            ShinseiDataPk pkInfo,
            String status,
            Date jyuriDate)
            throws DataAccessException, NoDataFoundException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, pkInfo);
        
        String query =
            "UPDATE SHINSEIDATAKANRI" + dbLink
                + " SET"
                + " JOKYO_ID = ?,"           // 申請状況ID       
                + " JYURI_DATE = ?"        // 所属機関承認日              
                + " WHERE"
                + " SYSTEM_NO = ?";

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++,status);
            DatabaseUtil.setParameter(preparedStatement,index++,jyuriDate);
            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //システム受付番号
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報ステータス更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
    // 2006/06/16 追加 mcj ここまで

//  宮　2006/06/21　ここから
    /**
     * 申請状況IDのみを指定されたステータスへ更新する。
     * @param connection
     * @param dataInfo 申請データ情報
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
//2007/02/05 苗　修正ここから　パラメータが多いので
//    public void updateShinseiInfo(
//            Connection connection,
//            ShinseiDataPk pkInfo,
//            String status,
//            int edintion,
//            Date sakuseiDate,
//            File iodFile,
//            File xmlFile)
    public void updateShinseiInfo(Connection connection,
                                  ShinseiDataInfo dataInfo)
//2007/02/05　苗　修正ここまで    
            throws DataAccessException, NoDataFoundException {

        //参照可能申請データかチェック
        checkOwnShinseiData(connection, dataInfo);

//2006/07/21 苗　修正ここから
//        String query =
//                "UPDATE SHINSEIDATAKANRI"+dbLink
//                    + " SET"
//                    + " JOKYO_ID = ?,"          //申請状況ID
//                    + " EDITION = ?,"
//                    + " SAKUSEI_DATE = SYSDATE"
//                    + " WHERE"
//                    + " SYSTEM_NO = ?";
        
        StringBuffer query = new StringBuffer();
          
        query.append("UPDATE SHINSEIDATAKANRI");
        query.append(dbLink);
        query.append(" SET");
        query.append(" JOKYO_ID = ?,");
        query.append(" EDITION = ?,");
        query.append(" SAKUSEI_DATE = ?,");
        query.append(" PDF_PATH = ?,");
        query.append(" XML_PATH = ?,");
//2007/02/05 苗　追加ここから
        query.append(" UKETUKE_NO = ?");
//2007/02/25　苗　追加ここまで        
        query.append(" WHERE SYSTEM_NO = ?");

        PreparedStatement preparedStatement = null;
        try {
            //登録
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
//2007/02/05 苗　修正ここから            
//            DatabaseUtil.setParameter(preparedStatement,index++,status);
//            DatabaseUtil.setParameter(preparedStatement,index++,edintion);
//            //2006/07/24 add start
//            DatabaseUtil.setParameter(preparedStatement,index++,sakuseiDate);
//            //2006/07/24 add end
//            //PDFファイルパス。
//            DatabaseUtil.setParameter(preparedStatement,index++,iodFile.getAbsolutePath());
//            //XMLファイルパス。
//            DatabaseUtil.setParameter(preparedStatement,index++,xmlFile.getAbsolutePath());
//            DatabaseUtil.setParameter(preparedStatement,index++,pkInfo.getSystemNo());  //システム受付番号
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getJokyoId());
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getKadaiInfo().getEdition());
            //2006/07/24 add start
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getSakuseiDate());
            //2006/07/24 add end
            //PDFファイルパス。
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getPdfPath());
            //XMLファイルパス。
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getXmlPath());
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getUketukeNo());
            DatabaseUtil.setParameter(preparedStatement,index++,dataInfo.getSystemNo());  //システム受付番号
//2007/02/05　苗　修正ここまで            
//206/07/21 苗　修正ここまで
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("申請情報ステータス更新中に例外が発生しました。 ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
//宮　ここまで

    //2006/06/23 by jzx add start
    /**
     * 領域計画書確定画面形式チェックを更新する。
     * @param  connection               コネクション
     * @param  shinseiDataInfo          チェックリスト検索情報
     * @return int
     * @throws DataAccessException      更新中に例外が発生した場合
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void selectCheckKakuteiSaveInfo(Connection connection, ShinseiDataInfo shinseiDataInfo)
            throws DataAccessException, NoDataFoundException, ApplicationException {

        if(shinseiDataInfo == null){
            throw new NoDataFoundException("例外が発生がセットされていません。");
        }
        String ryoikiNo = shinseiDataInfo.getRyouikiNo();
        if(ryoikiNo == null || ryoikiNo.equals("")){
            throw new NoDataFoundException("仮領域番号がセットされていません。");
        }
        //userInfo中取得科研研究者番号
        String kenkyuNo = userInfo.getShinseishaInfo().getKenkyuNo();
        if(kenkyuNo == null || kenkyuNo.equals("")){
            throw new NoDataFoundException("申請者研究者番号がセットされていません。");
        }
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" COUNT(*)");
        query.append(" FROM");
        query.append(" SHINSEIDATAKANRI A");
        query.append(" WHERE");
        query.append(" A.RYOIKI_NO = ?");
        query.append(" AND A.KENKYU_NO = ?");
        query.append(" AND A.KOMOKU_NO = ?");
        query.append(" AND A.JOKYO_ID = ?");
        query.append(" AND A.DEL_FLG = ?");
        
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        //変更前の状況IDを取得
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        int count = 0;
        try{
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, ryoikiNo);
            DatabaseUtil.setParameter(preparedStatement, i++, kenkyuNo);
            DatabaseUtil.setParameter(preparedStatement, i++, shinseiDataInfo.getRyouikiKoumokuNo());
            DatabaseUtil.setParameter(preparedStatement, i++, shinseiDataInfo.getJokyoId());
            DatabaseUtil.setParameter(preparedStatement, i++, shinseiDataInfo.getDelFlg());
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
        }catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        finally {
            if(count != 1 ){
                throw new ApplicationException(
                        "領域内研究計画調書に、総括班「X00」の研究計画調書がありません。",
                        new ErrorInfo("errors.9026"));
            }
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

    /**
     * 領域計画書確定画面形式チェックを更新する。
     * @param  connection               コネクション
     * @param  shinseiDataInfo          チェックリスト検索情報
     * @return List
     * @throws DataAccessException      更新中に例外が発生した場合
     * @throws NoDataFoundException
     */
    public List selectShinseiDataPkInfo(
            Connection connection,
            ShinseiDataInfo shinseiDataInfo)
            throws DataAccessException, NoDataFoundException{

        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" A.SYSTEM_NO " );
        select.append("FROM ");
        select.append(" SHINSEIDATAKANRI").append(dbLink).append(" A ");
        select.append("WHERE A.DEL_FLG = " );
        select.append(EscapeUtil.toSqlString(shinseiDataInfo.getDelFlg()));
        select.append(" AND A.JOKYO_ID = '");
        select.append(EscapeUtil.toSqlString(shinseiDataInfo.getJokyoId()));
        select.append("' ");
        select.append(" AND A.RYOIKI_NO = '");
        select.append(EscapeUtil.toSqlString(shinseiDataInfo.getRyouikiNo()));
        select.append("' ");
        select.append(" GROUP BY A.SYSTEM_NO ");

        // for debug
        if (log.isDebugEnabled()){
              log.debug("query:" + select.toString());
        }
        return SelectUtil.select(connection, select.toString());
    }
    //2006/06/23 by jzx add end

    //ADD START LY 2006/06/27
    /**
     * 当該申請書を参照することができるかチェックする。
     * 参照可能に該当しない場合は NoDataAccessExceptioin を発生させる。
     * 指定数と該当データ数が一致しない場合も NoDataAccessExceptioin を発生させる。
     * <p>
     * 判断基準は以下の通り。
     * <li>SHINSEIDATAKANRIテーブルのRYOII_NO（領域番号）が、RYOIKIKEIKAUSHOINFOのKARIRYOIKI_NOと等しいデータ
     * <li>SHINSEIDAKATANRIテーブルのDEL_FLG（削除フラグ）が「０」
     * <li>SHINSEIDATAKANRIテーブルのJOKYO_ID（状況ID）がshinseiInfo.getJokyoIds()
     * </p>
     * @param connection
     * @param shinseiInfo
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void checkSystemNo(Connection connection, ShinseiDataInfo shinseiInfo)
            throws DataAccessException, NoDataFoundException {

        // SQLの作成
        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" SYSTEM_NO ");
        select.append("FROM SHINSEIDATAKANRI");
        select.append(" WHERE ");
        select.append(" RYOIKI_NO = '");
        select.append(EscapeUtil.toSqlString(shinseiInfo.getRyouikiNo()));
        select.append("' ");
        select.append(" AND JOKYO_ID IN (");
        select.append(StringUtil.changeArray2CSV(shinseiInfo.getJokyoIds(), true));
        select.append(")");
        select.append(" AND DEL_FLG = 0");
        
        // for debug
        if (log.isDebugEnabled()){
              log.debug("query:" + select.toString());
        }

        SelectUtil.select(connection, select.toString());
    }

    /**
     * 申請状況ID,領域番号のみを指定されたステータスへ更新する。
     * 
     * @param connection
     * @param shinseiInfo ShinseiDataInfo
     * @param status
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void updateShinseis(Connection connection,
            ShinseiDataInfo shinseiInfo, String status)
            throws DataAccessException, NoDataFoundException {
        
        checkSystemNo( connection,  shinseiInfo);
        
        String query = "UPDATE SHINSEIDATAKANRI" + dbLink + " SET"
                + " JOKYO_ID = ? ";// 申請状況ID
        // 2006/06/28 by jzx add start
        if (shinseiInfo.getSaishinseiFlg() != null) {
            query = query + " ,SAISHINSEI_FLG = ?";
        }
        if (shinseiInfo.getRyoikiKakuteiDate() != null
                ||(shinseiInfo.getRyoikiKakuteiDateFlg() != null
                && shinseiInfo.getRyoikiKakuteiDateFlg().equals("1"))) {
            query = query + " ,RYOIKI_KAKUTEI_DATE = ?";
        }
        if (shinseiInfo.getJyuriDate() != null
                ||(shinseiInfo.getJyuriDateFlg() != null 
                && shinseiInfo.getJyuriDateFlg().equals("1"))) {
            query = query + " ,JYURI_DATE = ?";
        }
        // 2006/06/28 by jzx add end
        if (shinseiInfo.getShoninDate() != null) {
            query = query + " ,SHONIN_DATE = ?";
        }
        query = query + " WHERE" + "  RYOIKI_NO = ?";
        if (shinseiInfo.getJokyoIds() != null
                && shinseiInfo.getJokyoIds().length != 0) {
            query = query
                    + " AND JOKYO_ID IN ("
                    + StringUtil.changeArray2CSV(shinseiInfo.getJokyoIds(),
                            true) + ") ";
        }
        query = query + " AND DEL_FLG = 0";

        // for debug
        if (log.isDebugEnabled()){
              log.debug("query:" + query);
        }

        PreparedStatement preparedStatement = null;
        try {
            // 登録
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, status);
            // 2006/06/28 by jzx add start
            if (shinseiInfo.getSaishinseiFlg() != null) {
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getSaishinseiFlg());
            }
            if (shinseiInfo.getRyoikiKakuteiDate() != null) {
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getRyoikiKakuteiDate());
            }else if (shinseiInfo.getRyoikiKakuteiDateFlg() != null
                    && shinseiInfo.getRyoikiKakuteiDateFlg().equals("1")) {
                shinseiInfo.setRyoikiKakuteiDateFlg(null);
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getRyoikiKakuteiDate());
            }
            if (shinseiInfo.getJyuriDate() != null) {
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getJyuriDate()); 
            }else if(shinseiInfo.getJyuriDateFlg() != null 
                && shinseiInfo.getJyuriDateFlg().equals("1")){
                shinseiInfo.setJyuriDateFlg(null);
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getJyuriDate());
            }
            // 2006/06/28 by jzx add end
            if (shinseiInfo.getShoninDate() != null
                    && shinseiInfo.getShoninDateMark().equals("1")) {
                shinseiInfo.setShoninDate(null);
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getShoninDate()); //
            }
            else if (shinseiInfo.getShoninDate() != null
                    && StringUtil.isBlank(shinseiInfo.getShoninDateMark())) {
                DatabaseUtil.setParameter(preparedStatement, index++,
                        shinseiInfo.getShoninDate()); //   
            }
            DatabaseUtil.setParameter(preparedStatement, index++, shinseiInfo
                    .getRyouikiNo()); // システム受付番号

            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DataAccessException("申請情報ステータス更新中に例外が発生しました。 ", ex);
        }
        finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
    //add end ly 2006/06/27  
    
    // 2006/07/25 zjp 追加ここから
    /**
     * 複数申請の承認がメール送信。
     * @param  connection               コネクション
     * @param  shinseiDataPks           
     * @return List
     * @throws DataAccessException      更新中に例外が発生した場合
     * @throws NoDataFoundException
     */
    public List selectShinseiDataListForMail(
            Connection connection,
            ShinseiDataPk[] shinseiDataPks)
            throws DataAccessException, NoDataFoundException{

        String[] string  = new String[shinseiDataPks.length];
        
        for (int i = 0; i < string.length; i++) {
            string[i] = shinseiDataPks[i].getSystemNo();
        }
        
        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" NENDO," );
        select.append(" KAISU," );
        select.append(" JIGYO_NAME," );
        select.append(" JIGYO_ID," );
        select.append(" SHONIN_DATE," );
        select.append(" COUNT(*) COUNT " );
        select.append(" FROM ");
        select.append(" SHINSEIDATAKANRI");
        select.append(" WHERE SYSTEM_NO IN (" );
        select.append(StringUtil.changeArray2CSV(string, true));
        select.append(") GROUP BY ");
        select.append(" NENDO,KAISU,JIGYO_NAME,JIGYO_ID,SHONIN_DATE ");

        // for debug
        if (log.isDebugEnabled()){
              log.debug("query:" + select.toString());
        }
        return SelectUtil.select(connection, select.toString());
    }
    // 2006/07/25 zjp 追加ここまで
    
//2006/07/26 苗　追加ここから 
    
    /**
     * すでに同一課題番号を検索する
     * 
     * @param connection
     * @param shinseiDataInfo
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectKadaiNoSaisyu(
            Connection connection,
            ShinseiDataInfo shinseiDataInfo)
            throws DataAccessException, NoDataFoundException {
        
        String[] jokyoIds = new String[]{StatusCode.STATUS_SAKUSEITHU,
                                         StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,
                                         StatusCode.STATUS_GAKUSIN_FUJYURI};
        StringBuffer query = new StringBuffer();
        
        query.append(" SELECT S.KADAI_NO_SAISYU FROM");
        query.append(" SHINSEIDATAKANRI S WHERE");
        
        //2006.08.31 iso チェックは事業IDを見ないよう変更
//        query.append(" S.JIGYO_ID = ");
//        query.append(EscapeUtil.toSqlString(shinseiDataInfo.getJigyoId()));
//        query.append(" AND S.KADAI_NO_SAISYU = ");
        query.append(" S.KADAI_NO_SAISYU = ");
        
        query.append(EscapeUtil.toSqlString(shinseiDataInfo.getKadaiNoSaisyu()));

        //2006.08.31 異なる年度ではチェックしたいので、年度を条件に加える。
        query.append(" AND S.NENDO = ");
        query.append(EscapeUtil.toSqlString(shinseiDataInfo.getNendo()));
        
        query.append(" AND S.JOKYO_ID NOT IN (");
        query.append(changeArray2CSV(jokyoIds));
        query.append(")");
        query.append(" AND S.DEL_FLG = 0");
//2006/08/21 苗　追加ここから
        if(!StringUtil.isBlank(shinseiDataInfo.getSystemNo())){
            query.append(" AND S.SYSTEM_NO <> ");
            query.append(EscapeUtil.toSqlString(shinseiDataInfo.getSystemNo()));
        }
//2006/08/21　苗　追加ここまで        
        
        return SelectUtil.select(connection, query.toString());
   }    
//2006/07/26　苗　追加ここまで    
    
//2007/02/05 苗　追加ここから
    /**
     * 整理番号のカウントを返す。
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    public int countSeiriNumber(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        String jigyoKubun = dataInfo.getKadaiInfo().getJigyoKubun();
        //学創の場合
        if(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(jigyoKubun) ||
           IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO.equals(jigyoKubun)) {
            return getSequenceNumber4GakusouCount(connection, dataInfo);

        //特推の場合
        } else if (IJigyoKubun.JIGYO_KUBUN_TOKUSUI.equals(jigyoKubun)){
            return getSequenceNumber4TokusuiCount(connection, dataInfo);

        //基盤の場合
        } else if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKubun)){
            return getSequenceNumber4KibanCount(connection, dataInfo);

        //特定領域の場合
        } else if (IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(jigyoKubun)){
            return getSequenceNumber4TokuteiCount(connection, dataInfo);

        //若手スタートアップの場合  
        } else if (IJigyoKubun.JIGYO_KUBUN_WAKATESTART.equals(jigyoKubun)){
            return getSequenceNumber4KibanCount(connection, dataInfo);

        //特別研究促進費の場合    
        } else if (IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI.equals(jigyoKubun)){
            return getSequenceNumber4KibanCount(connection, dataInfo);

        //それ以外の場合暫定的に特推と同じ採番ルールにしておく）
        } else {
            return getSequenceNumber4TokusuiCount(connection, dataInfo);
        }
    }
    
    /**
     * 整理番号のカウント（学創用）を返す。
     * @param connection
     * @param dataInfo
     * @return int
     * @throws DataAccessException
     */
    private int getSequenceNumber4GakusouCount(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        String select = "COUNT(A.UKETUKE_NO)";
        
        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI"+dbLink+" A"
            + " WHERE"
            + "  A.UKETUKE_NO = ?"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getUketukeNo()); 
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getDaihyouInfo().getShozokuCd());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
             
            int count = 0;
            if (recordSet.next()){
                count =  recordSet.getInt(1);
            }
            
            return count;
             
        } catch (SQLException ex) {
            throw new DataAccessException("申請データテーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    
    /**
     * 整理番号のカウント（特推用）を返す。
     * @param connection
     * @param dataInfo
     * @return int
     * @throws DataAccessException
     */
    private int getSequenceNumber4TokusuiCount(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        String select = "COUNT(A.UKETUKE_NO)";

        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI" + dbLink + " A"
            + " WHERE"
            + "  A.UKETUKE_NO = ?"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getUketukeNo());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getDaihyouInfo().getShozokuCd());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
             
            int count = 0;
            if (recordSet.next()){
                count =  recordSet.getInt(1);
            }
            
            return count;
             
        } catch (SQLException ex) {
            throw new DataAccessException("申請データテーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

    /**
     * 整理番号のカウント（基盤用）を返す。
     * @param connection
     * @param dataInfo
     * @return int
     * @throws DataAccessException
     */
    private int getSequenceNumber4KibanCount(Connection connection, ShinseiDataInfo dataInfo)
        throws DataAccessException {

        String select = "COUNT(A.UKETUKE_NO)";
        
        String query = 
        "SELECT "
            + select
            + " FROM"
            + "  SHINSEIDATAKANRI" + dbLink + " A"
            + " WHERE"
            + "  A.UKETUKE_NO = ?"
            + " AND"
            + "  A.SHOZOKU_CD = ?"
            + " AND"
            + "  A.BUNKASAIMOKU_CD = ?"
            + " AND"
            + "  DECODE(A.BUNKATSU_NO, NULL, '-', BUNKATSU_NO) = ?"         //空の場合"-"に置き換える
            + " AND"
            + "  A.JIGYO_ID = ?"
            ;
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getUketukeNo());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getDaihyouInfo().getShozokuCd());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getKadaiInfo().getBunkaSaimokuCd());
            if(dataInfo.getKadaiInfo().getBunkatsuNo() == null || "".equals(dataInfo.getKadaiInfo().getBunkatsuNo())) {
                DatabaseUtil.setParameter(preparedStatement, i++, "-");          //空の場合"-"に置き換える
            } else {
                DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getKadaiInfo().getBunkatsuNo());
            }
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
            
            int count = 0;
            if (recordSet.next()){
                count =  recordSet.getInt(1);
            }
            
            return count;

        } catch (SQLException ex) {
            throw new DataAccessException("申請データテーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    
    /**
     * 整理番号のカウント（特定領域用）を返す。
     * @param connection
     * @param dataInfo
     * @return
     * @throws DataAccessException
     */
    private int getSequenceNumber4TokuteiCount(Connection connection, ShinseiDataInfo dataInfo)
            throws DataAccessException {

        String select = "COUNT(UKETUKE_NO)";      
        
        String query = 
            "SELECT "
                + select
                + " FROM"
                + "  SHINSEIDATAKANRI" + dbLink + " A"
                + " WHERE"
                + "  A.UKETUKE_NO = ?"
                + " AND"
                + "  A.SHOZOKU_CD = ?"
                + " AND"
                + "  DECODE(A.RYOIKI_NO, NULL, '-', RYOIKI_NO) = ?"     //空の場合"-"に置き換える
                + " AND"
                + "  DECODE(A.KOMOKU_NO, NULL, '-', KOMOKU_NO) = ?"     //空の場合"-"に置き換える
                + " AND"
                + "  A.JIGYO_ID = ?"
                ;
        
        //for debug
        if(log.isDebugEnabled()){log.debug("query:" + query);}

        //DB接続
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getUketukeNo());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getDaihyouInfo().getShozokuCd());
            if(dataInfo.getRyouikiNo() == null || "".equals(dataInfo.getRyouikiNo())) {
                DatabaseUtil.setParameter(preparedStatement, i++, "-");          //空の場合"-"に置き換える
            } else {
                DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getRyouikiNo());
            }
            if(dataInfo.getRyouikiKoumokuNo() == null || "".equals(dataInfo.getRyouikiKoumokuNo())) {
                DatabaseUtil.setParameter(preparedStatement, i++, "-");          //空の場合"-"に置き換える
            } else {
                DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getRyouikiKoumokuNo());
            }
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
            
            int count = 0;
            if (recordSet.next()){
                count =  recordSet.getInt(1);
            }
            
            return count;
             
        } catch (SQLException ex) {
            throw new DataAccessException("申請データテーブル検索実行中に例外が発生しました。", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2007/02/05　苗　追加ここまで    
}