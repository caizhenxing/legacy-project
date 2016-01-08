/*======================================================================
 *    SYSTEM      : 電子申請システム（科学研究費補助金）
 *    Source name : MasterJigyoInfoDao
 *    Description : 事業マスタデータアクセスクラス
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.*;
import java.util.*;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * 事業マスタデータアクセスクラス。
 * ID RCSfile="$RCSfile: MasterJigyoInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class MasterJigyoInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static final Log log = LogFactory.getLog(MasterJigyoInfoDao.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 実行するユーザ情報 */
	private UserInfo userInfo = null;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	
	/**
	 * コンストラクタ。
	 * @param userInfo	実行するユーザ情報
	 */
	public MasterJigyoInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * 事業情報の一覧を取得する。
	 * @param	connection			コネクション
	 * @return						事業情報
	 * @throws ApplicationException
	 */
	public static List selectJigyoInfoList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"//事業名CD
			+ ",A.JIGYO_NAME"//事業名称
			+ " FROM MASTER_JIGYO A"
			+ " ORDER BY JIGYO_CD";		//事業コード順						
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"事業マスタに1件もデータがありません。",
				e);
		}
	}

	/**
	 * ログインユーザ用の事業名リストを返す。
	 * 業務担当者以外は、selectJigyoInfoList(Connection connection) と
	 * 同じ結果が返る。
	 * <li>業務担当者･･････自分が担当する事業区分に該当する事業リスト</li>
	 * <li>業務担当者以外･･･全部</li>
	 * @param connection
	 * @return
	 * @throws ApplicationException
	 */
	public List selectJigyoInfoList4Users(Connection connection)
		throws ApplicationException {

		//検索条件
		String addQuery = null;
		
		//業務担当者の場合は自分が担当する事業区分のみ。
		if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
			Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoKubun().iterator();
			String tantoJigyoKubun = StringUtil.changeIterator2CSV(ite,true);
			addQuery = new StringBuffer(" WHERE A.JIGYO_KUBUN IN (")
						 .append(tantoJigyoKubun)
						 .append(")")
						 .toString();
		}else{
			return selectJigyoInfoList(connection);
		}
		
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"//事業名CD
			+ ",A.JIGYO_NAME"//事業名称
			+ " FROM MASTER_JIGYO A"
			+ addQuery
			+ " ORDER BY JIGYO_CD";		//事業コード順						
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"事業マスタに1件もデータがありません。",
				e);
		}
	}

	/**
	 * ログインユーザ用の事業名リストを返す。
	 * 審査対象の事業区分を事業名リストに追加する。<br>
	 * <li>業務担当者･･････自分が担当する事業区分のうち、審査対象の事業区分に該当する事業リスト</li>
	 * <li>業務担当者以外･･･審査対象の事業区分に該当する事業リスト</li>
	 * @param connection
	 * @return List
	 * @throws ApplicationException
     * @throws NoDataFoundException
	 */
	public List selectShinsaTaishoJigyoInfoList4Users(Connection connection)
		throws ApplicationException, NoDataFoundException {

		//検索条件
		String addQuery = null;

//delete start dyh 2006/06/06 原因：使用しない
//		//事業区分リスト
//		ArrayList jigyoKubunList = new ArrayList();
//delete end dyh 2006/06/06

		//担当事業区分から審査対象分の事業区分のみ取得
		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);			
		
		//CSVを取得
		String tantoJigyoKubun = StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(),true);
		addQuery = new StringBuffer(" WHERE A.JIGYO_KUBUN IN (")
					 .append(tantoJigyoKubun)
					 .append(")")
					 .toString();

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"				//事業コード
			+ ",A.JIGYO_NAME"			//事業名
			+ " FROM MASTER_JIGYO A"
			+ addQuery
			+ " ORDER BY JIGYO_CD";		//事業コード順						
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw e;
		}
	}	

	/**
	 * 指定された事業区分の事業名リストを返す。
	 * @param connection
	 * @param jigyoKubun
	 * @return List
	 * @throws ApplicationException
     * @throws NoDataFoundException
	 */
	public List selectJigyoInfoList(Connection connection, String jigyoKubun)
			throws ApplicationException, NoDataFoundException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"				//事業コード
			+ ",A.JIGYO_NAME"			//事業名
			+ " FROM MASTER_JIGYO A"
			+ " WHERE A.JIGYO_KUBUN = '"
			+ EscapeUtil.toSqlString(jigyoKubun)
			+ "' ORDER BY JIGYO_CD";		//事業コード順						
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw e;
		}
	}	

	/**
	 * 事業区分を取得する。
	 * @param connection			コネクション
	 * @param jigyoCd				事業コード
	 * @return						事業情報
	 * @throws ApplicationException
     * @throws NoDataFoundException
	 */
	public static String selectJigyoKubun(
		Connection connection,
		String jigyoCd)
		throws ApplicationException, NoDataFoundException {

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
				+ " NVL(A.JIGYO_KUBUN,0) JIGYO_KUBUN"
				+ " FROM MASTER_JIGYO A"
				+ " WHERE JIGYO_CD = ?";
		StringBuffer query = new StringBuffer(select);

		//-----------------------
		// リスト取得
		//-----------------------
		try {
			List result =
				SelectUtil.select(
					connection,
					query.toString(),
					new String[] { jigyoCd });
			return ((Map) result.get(0)).get("JIGYO_KUBUN").toString();
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業区分の取得に失敗しました。事業コード'" + jigyoCd + "'",
				new ErrorInfo("errors.4004"),
				e);
		}
	}

	/**
	 * 事業マスタの１レコードをMap形式で返す。
	 * 引数には主キー値を渡す。
	 * @param connection
	 * @param jigyoCd 事業コード
	 * @return Map
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, String jigyoCd)
		throws NoDataFoundException, DataAccessException {
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"				//事業コード
			+ ",A.JIGYO_NAME"			//事業名称
			+ ",A.KEI_KUBUN"			//系名区分
			+ ",A.JIGYO_KUBUN"			//事業区分
			+ ",A.SHINSA_KUBUN"			//審査区分
			+ ",A.BIKO"					//備考
			+ " FROM MASTER_JIGYO A"
			+ " WHERE JIGYO_CD = ? "
			;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// レコード取得
		//-----------------------
		List result = SelectUtil.select(connection,
										select, 
										new String[]{jigyoCd});
		if(result.isEmpty()){
			throw new NoDataFoundException(
				"当該レコードは存在しません。事業コード="+jigyoCd);
		}
		return (Map)result.get(0);
	}

	//2005/04/28 追加　ここから-------------------------------------------
	//自分の担当する事業CDで絞り込んだ事業名リストを取得するため
	/**
	 * 事業CDで絞り込んだログインユーザ用の事業名リストを返す。
	 * 業務担当者以外は、selectJigyoInfoList(Connection connection) と
	 * 同じ結果が返る。
	 * <li>業務担当者･･････自分が担当する事業区分に該当する事業リスト</li>
	 * <li>業務担当者以外･･･全部</li>
	 * @param connection
	 * @return
	 * @throws ApplicationException
	 */
	public List selectJigyoInfoListByJigyoCds(Connection connection)
		throws ApplicationException {
		//検索条件
		String addQuery = null;
		
		//業務担当者の場合は自分が担当する事業のみ。
		if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
			Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
			String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
			addQuery = new StringBuffer(" WHERE A.JIGYO_CD IN (")
						 .append(tantoJigyoCd)
						 .append(")")
						 .toString();
		}else{
			return selectJigyoInfoList(connection);
		}
		
		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"//事業名CD
			+ ",A.JIGYO_NAME"//事業名称
			+ " FROM MASTER_JIGYO A"
			+ addQuery
			+ " ORDER BY JIGYO_CD";		//事業コード順						
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"事業マスタに1件もデータがありません。",
				e);
		}
	}
	//追加 ここまで---------------------------------------------------------	

    /**
     * 事業区分・事業CDで絞り込んだログインユーザ用の事業名リストを返す。
     * 業務担当者以外は、selectJigyoInfoList(Connection connection) と
     * 同じ結果が返る。
     * <li>業務担当者･･････自分が担当する事業区分に該当する事業リスト</li>
     * <li>業務担当者以外･･･全部</li>
     * @param connection
     * @param jigyoKubun 事業区分
     * @return List
     * @throws ApplicationException
     */
    public List selectJigyoInfoListByJigyoCds(Connection connection,
            String jigyoKubun) throws ApplicationException {

        //検索条件
        String addQuery = null;
        
        //業務担当者の場合は自分が担当する事業のみ。
        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
            Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
            String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
            addQuery = new StringBuffer(" WHERE A.JIGYO_CD IN (")
                         .append(tantoJigyoCd)
                         .append(")")
                         .append(" AND JIGYO_KUBUN = '")
                         .append(jigyoKubun)
                         .append("'")
                         .toString();
        }else{
            return selectJigyoInfoList(connection);
        }
        
        //-----------------------
        // SQL文の作成
        //-----------------------
        String select =
            "SELECT"
            + " A.JIGYO_CD"//事業名CD
            + ",A.JIGYO_NAME"//事業名称
            + " FROM MASTER_JIGYO A "
            + addQuery
            + " ORDER BY JIGYO_CD";     //事業コード順                        
            StringBuffer query = new StringBuffer(select);

        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //-----------------------
        // リスト取得
        //-----------------------
        try {
            return SelectUtil.select(connection,query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException(
                "事業情報検索中にDBエラーが発生しました。",
                new ErrorInfo("errors.4004"),
                e);
        } catch (NoDataFoundException e) {
            throw new SystemException(
                "事業マスタに1件もデータがありません。",
                e);
        }
    }

//  2006/06/02 　苗　追加ここから    
    /**
     * 事業区分・事業CDで絞り込んだログインユーザ用の事業名リストを返す。
     * 業務担当者以外は、selectJigyoInfoList(Connection connection) と
     * 同じ結果が返る。
     * <li>業務担当者･･････自分が担当する事業区分に該当する事業リスト</li>
     * <li>業務担当者以外･･･全部</li>
     * @param connection
     * @param jigyoCds 事業コードリスト
     * @param jigyoKubun 事業区分
     * @return List
     * @throws ApplicationException
     */
    public List selectJigyoInfoListByJigyoCds(
            Connection connection,
            String[] jigyoCds,
            String jigyoKubun)
            throws ApplicationException {

        //業務担当者の自分が担当する事業
        Set tantoJigyoCds = userInfo.getGyomutantoInfo().getTantoJigyoCd();
        Set finalJigyoCdsSet = new HashSet();

        // 業務担当者の場合
        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
            // 業務担当者の自分が担当する事業と完全電子化基盤の事業CDを比較する
            if(jigyoCds != null && jigyoCds.length > 0){
                for( int i = 0 ; i < jigyoCds.length ; i++){
                    if (tantoJigyoCds.contains(jigyoCds[i])){
                        finalJigyoCdsSet.add(jigyoCds[i]);
                    }
                }
            }else{
                finalJigyoCdsSet = tantoJigyoCds;
            }
        }
        // 所属機関担当者と部局担当者の場合
        else{
            if(jigyoCds != null && jigyoCds.length > 0){
                for(int i = 0 ; i < jigyoCds.length ; i++){
                    finalJigyoCdsSet.add(jigyoCds[i]);
                }
            }
        }
        //WHERE条件
        StringBuffer where = new StringBuffer("");
        if(finalJigyoCdsSet.size() > 0){
            if(finalJigyoCdsSet.size() == 1){
                where.append(" WHERE A.JIGYO_CD = ");
                where.append(StringUtil.changeIterator2CSV(finalJigyoCdsSet.iterator(),true));
            }else{
                where.append(" WHERE A.JIGYO_CD IN (");
                where.append(StringUtil.changeIterator2CSV(finalJigyoCdsSet.iterator(),true));
                where.append(")");
            }
        }
        if(!StringUtil.isBlank(jigyoKubun)){
            if(where.length() == 0){
                where.append(" WHERE ");
            }else{
                where.append(" AND ");
            }
            where.append(" JIGYO_KUBUN = '");
            where.append(jigyoKubun);
            where.append("'");
        }

        //-----------------------
        // SQL文の作成
        //-----------------------
        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" A.JIGYO_CD");//事業名CD
        query.append(",A.JIGYO_NAME");//事業名称
        query.append(" FROM MASTER_JIGYO A ");
        query.append(where);
        query.append(" ORDER BY JIGYO_CD");//事業コード順

        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        
        //-----------------------
        // リスト取得
        //-----------------------
        try {
            return SelectUtil.select(connection,query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException(
                "事業情報検索中にDBエラーが発生しました。",
                new ErrorInfo("errors.4004"),
                e);
        } catch (NoDataFoundException e) {
            throw new SystemException(
                "事業マスタに1件もデータがありません。",
                e);
        }
    }
//2006/06/02 苗　追加ここまで

//    //2006/06/06 jzx　add start
//    //自分の担当する事業CDで絞り込んだ事業名リストを取得するため
//    /**
//     * 事業CDで絞り込んだログインユーザ用の事業名リストを返す。
//     * 業務担当者以外は、selectJigyoInfoList(Connection connection) と
//     * 同じ結果が返る。
//     * <li>業務担当者･･････自分が担当する事業区分に該当する事業リスト</li>
//     * <li>業務担当者以外･･･全部</li>
//     * @param connection
//     * @return List
//     * @throws ApplicationException
//     */
//    public List selectJigyoInfoListByJigyoCds2(Connection connection,
//                                               String[] validJigyoCds)
//            throws ApplicationException {
//
//        //検索条件
//        String addQuery = null;
//        
////        String[] validJigyoCds = new String[]{IJigyoCd.JIGYO_CD_TOKUSUI,        //特別推進研究
////                                              IJigyoCd.JIGYO_CD_KIBAN_S,        //基盤研究(S)
////                                              IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN,  //基盤研究(A)(一般)
////                                              IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI, //基盤研究(A)(海外学術調査)
////                                              IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN,  //基盤研究(B)(一般)
////                                              IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI, //基盤研究(B)(海外学術調査)
////                                              IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO,//学術創成（非公募）
////                                              IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO}; //学術創成（公募）
//        
//        //業務担当者の場合は自分が担当する事業のみ。
//        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
//            Set tantoJigyoCds = userInfo.getGyomutantoInfo().getTantoJigyoCd();
//            //業務担当者の自分が担当する事業と完全電子化基盤の事業CDを比較する
//            Set finalJigyoCdsSet = new HashSet();
//            for( int i = 0 ; i < validJigyoCds.length ; i++){
//                if (tantoJigyoCds.contains(validJigyoCds[i])){
//                    finalJigyoCdsSet.add(validJigyoCds[i]);
//                }
//            }
//            addQuery = new StringBuffer(" WHERE A.JIGYO_CD IN (")
//                           .append(StringUtil.changeIterator2CSV(finalJigyoCdsSet.iterator(),true))
//                           .append(")")
//                           .toString();
//        }else{
//            addQuery = new StringBuffer(" WHERE A.JIGYO_CD IN (")
//                       .append(StringUtil.changeArray2CSV(validJigyoCds,true))
//                       .append(")")
//                       .toString();
//            
//        }        
//
//        //-----------------------
//        // SQL文の作成
//        //-----------------------
//        String select =
//            "SELECT"
//            + " A.JIGYO_CD"//事業名CD
//            + ",A.JIGYO_NAME"//事業名称
//            + " FROM MASTER_JIGYO A"
//            + addQuery
//            + " ORDER BY A.JIGYO_CD";     //事業コード順                        
//            StringBuffer query = new StringBuffer(select);
//
//        if(log.isDebugEnabled()){
//            log.debug("query:" + query);
//        }
//        
//        //-----------------------
//        // リスト取得
//        //-----------------------
//        try {
//            return SelectUtil.select(connection,query.toString());
//        } catch (DataAccessException e) {
//            throw new ApplicationException(
//                "事業情報検索中にDBエラーが発生しました。",
//                new ErrorInfo("errors.4004"),
//                e);
//        } catch (NoDataFoundException e) {
//            throw new SystemException(
//                "事業マスタに1件もデータがありません。",
//                e);
//        }
//    }
//    //2006/06/06 jzx　add end
    
//    //2006/06/14 lwj　add start
//    /**
//     * 指定された事業区分の事業名リストを返す。
//     * @param connection
//     * @param jigyoCd
//     * @return List
//     * @throws ApplicationException
//     * @throws NoDataFoundException
//     */
//    public List selectJigyoInfoListByJigyoCds3(Connection connection,
//            String jigyoCd) throws ApplicationException{
//
//        //検索条件
//        String addQuery = null;
//        
//        //業務担当者の場合は自分が担当する事業のみ。
//        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
//            addQuery = " WHERE A.JIGYO_CD IN ("
//                         +jigyoCd
//                         +")";
//        }else{
//            return selectJigyoInfoList(connection);
//        }
//        
//        //-----------------------
//        // SQL文の作成
//        //-----------------------
//        String select =
//            "SELECT"
//            + " A.JIGYO_CD"//事業名CD
//            + ",A.JIGYO_NAME"//事業名称
//            + " FROM MASTER_JIGYO A"
//            + addQuery
//            + " ORDER BY JIGYO_CD";     //事業コード順   
//
//        if(log.isDebugEnabled()){
//            log.debug("select:" + select);
//        }
//        
//        //-----------------------
//        // リスト取得
//        //-----------------------
//        try {
//            return SelectUtil.select(connection,select);
//        } catch (DataAccessException e) {
//            throw new ApplicationException(
//                "事業情報検索中にDBエラーが発生しました。",
//                new ErrorInfo("errors.4004"),
//                e);
//        } catch (NoDataFoundException e) {
//            throw new SystemException(
//                "事業マスタに1件もデータがありません。",
//                e);
//        }
//    }
//    //2006/06/14 lwj　add end

	/**
	 * ログインユーザ用の事業名リストを返す。
	 * 審査対象の事業区分を事業名リストに追加する。<br>
	 * <li>業務担当者･･････自分が担当する事業のうち、審査対象の事業区分に該当する事業リスト</li>
	 * <li>業務担当者以外･･･審査対象の事業区分に該当する事業リスト</li>
	 * @param connection
	 * @return
	 * @throws ApplicationException
     * @throws NoDataFoundException
	 */
	public List selectShinsaTaishoList4UsersByJigyoCds(Connection connection)
			throws ApplicationException, NoDataFoundException {
		
		//検索条件
		StringBuffer addQuery;

		//業務担当者の場合は自分が担当する事業のみ。
		if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
			Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
			String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
			addQuery = new StringBuffer(" WHERE A.JIGYO_CD IN (")
						 .append(tantoJigyoCd)
						 .append(") AND")
						 ;
		}else{
			addQuery = new StringBuffer(" WHERE");
		}
//delete start dyh 2006/06/06 原因：使用しない		
//		//事業区分リスト
//		ArrayList jigyoKubunList = new ArrayList();
//delete end dyh 2006/06/06

		//担当事業区分から審査対象分の事業区分のみ取得
		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);			
		
		//CSVを取得
		String tantoJigyoKubun = StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(),true);
		addQuery.append(" A.JIGYO_KUBUN IN (")
				.append(tantoJigyoKubun)
				.append(")")
					 ;

		//-----------------------
		// SQL文の作成
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"				//事業コード
			+ ",A.JIGYO_NAME"			//事業名
			+ " FROM MASTER_JIGYO A"
			+ addQuery.toString()
			+ " ORDER BY JIGYO_CD";		//事業コード順						
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// リスト取得
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"事業情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw e;
		}
	}
}