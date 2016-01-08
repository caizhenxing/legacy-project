/*
 * Created on 2005/04/18
 *
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.KeizokuInfo;
import jp.go.jsps.kaken.model.vo.KeizokuPk;
import jp.go.jsps.kaken.model.vo.UserInfo;

/**
 * @author masuo_t
 *
 */
public class MasterKeizokuInfoDao {

	
	/** 実行するユーザ情報 */
	private UserInfo userInfo = null;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	
	
	/**
	 * コンストラクタ。
	 * @param userInfo	実行するユーザ情報
	 */
	public MasterKeizokuInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	/**
	 * 継続課題マスタから該当するデータを取得する。
	 * 
	 * @param connection
	 * @param pk
	 * @return	resultList	
	 * @throws DataAccessException
	 */
	public List select(Connection connection, KeizokuPk pk)
		throws DataAccessException, NoDataFoundException{
		
		List resultList = new ArrayList();
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		
		try {	
			String select = 
				"SELECT "
				+ " JIGYO_ID"				//事業ID
				+ ", KADAI_NO"				//課題番号
				+ ", ZENNENDO_KUBUN"		//前年度応募可否区分
				+ ", BIKO"					//備考
				+ " FROM MASTER_KEIZOKU"	//継続課題マスタ	
				+ " WHERE JIGYO_ID = ?"
				+ " AND KADAI_NO = ?";		//主キー：事業ID、課題番号
			
			StringBuffer query = new StringBuffer(select);
			
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			KeizokuInfo result = new KeizokuInfo();
			preparedStatement.setString(i++, pk.getJigyoId());
			preparedStatement.setString(i++, pk.getKadaiNo());
			recordSet = preparedStatement.executeQuery();
			//データの個数分処理を繰り返し
			if(recordSet.next()){
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setKadaiNo(recordSet.getString("KADAI_NO"));
				result.setZennendoKubun(recordSet.getString("ZENNENDO_KUBUN"));
				result.setBiko(recordSet.getString("BIKO"));
				//ListにKeizokuInfoを格納
				resultList.add(result);	
			
			}else{
				throw new NoDataFoundException(
					"継続課題マスタテーブルに該当するデータが見つかりません。検索キー：事業CD'"
						+ pk.getJigyoId()
						+"', 課題番号'"
						+ pk.getKadaiNo()
						+ "'");
			}			
		} catch (SQLException ex) {
			throw new DataAccessException("継続課題マスタ検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return resultList;
	}
	

	//2005/04/22 追加 ここから----------------------------------------------------
	//継続課題マスタ取り込み用にINSERT処理の追加
	/**
	 * 継続課題マスタに値を登録する。
	 * 
	 * @param connection
	 * @param info
	 * @throws DataAccessException
	 */
	public void insertKeizokuInfo(Connection connection, KeizokuInfo info)
		throws DataAccessException{

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		
		try {	
			String select = 
					"INSERT INTO MASTER_KEIZOKU("
					+ " JIGYO_ID"				//事業ID
					+ ", KADAI_NO"				//課題番号
					+ ", ZENNENDO_KUBUN"		//前年度応募可否区分
					+ ", BIKO" 					//備考
//<!-- UPDATE　START 2007/07/11 BIS 張楠 -->					
					+ ", KENKYU_NO" 			//研究者番号
					+ ", KADAI_NAME_KANJI" 		//研究課題名
					+ ", NAIYAKUGAKU1" 			//1年目内約額
					+ ", NAIYAKUGAKU2" 			//2年目内約額
					+ ", NAIYAKUGAKU3" 			//3年目内約額
					+ ", NAIYAKUGAKU4" 			//4年目内約額
					+ ", NAIYAKUGAKU5" 			//5年目内約額
					+")"					
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
//<!-- UPDATE　END 2007/07/11 BIS 張楠 -->			
			StringBuffer query = new StringBuffer(select);
			
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,info.getJigyoId());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getKadaiNo());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getZennendoKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getBiko());
//			<!-- UPDATE　START 2007/07/11 BIS 張楠 -->							
			DatabaseUtil.setParameter(preparedStatement,i++,info.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getKadaiNameKanji());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getNaiyakugaku1());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getNaiyakugaku2());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getNaiyakugaku3());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getNaiyakugaku4());
			DatabaseUtil.setParameter(preparedStatement,i++,info.getNaiyakugaku5());
//			<!-- UPDATE　END 2007/07/11 BIS 張楠 -->					
			DatabaseUtil.executeUpdate(preparedStatement);
		
		} catch (SQLException ex) {
			throw new DataAccessException("継続課題マスタ登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}


// 20050826 課題番号のみで継続課題マスタから情報を取得する
	/**
	 * 継続課題マスタから課題番号のみで該当するデータを取得する。
	 * 
	 * @param connection
	 * @param kadaiNo
	 * @return	resultList
	 * @throws DataAccessException
	 */
	public List select(Connection connection, String kadaiNo)
		throws DataAccessException, NoDataFoundException{

		List resultList = new ArrayList();
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;

		try {
			String select = 
				"SELECT"
				+ " JIGYO_ID"			//事業ID
				+ ", KADAI_NO"			//課題番号
				+ ", ZENNENDO_KUBUN"	//前年度応募可否区分
				+ ", BIKO "				//備考
				+ "FROM"
				+ " MASTER_KEIZOKU "	//継続課題マスタ	
				+ "WHERE"
				+ " KADAI_NO = ? ";		//課題番号
			
			StringBuffer query = new StringBuffer(select);

			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			KeizokuInfo result = new KeizokuInfo();
			preparedStatement.setString(i++, kadaiNo);
			recordSet = preparedStatement.executeQuery();
			//データの個数分処理を繰り返し
			if(recordSet.next()){
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setKadaiNo(recordSet.getString("KADAI_NO"));
				result.setZennendoKubun(recordSet.getString("ZENNENDO_KUBUN"));
				result.setBiko(recordSet.getString("BIKO"));
				//ListにKeizokuInfoを格納
				resultList.add(result);	
			
			}else{
				throw new NoDataFoundException(
					"継続課題マスタテーブルに該当するデータが見つかりません。検索キー："
						+" 課題番号'"
						+ kadaiNo
						+ "'");
			}			
		} catch (SQLException ex) {
			throw new DataAccessException("継続課題マスタ検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return resultList;
	}

// ADD　START 2007-07-26 BIS 王志安
	/**
	 * 継続課題マスタから該当するデータを取得する。
	 * 
	 * @param connection
	 * @param pk
	 * @return	resultList	
	 * @throws DataAccessException
	 */
	public List select(Connection connection, String kenkyuNo, String kadaiNo)
		throws DataAccessException, NoDataFoundException{
		
		List resultList = new ArrayList();
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		
		try {	
			String select = 
				"SELECT KADAI_NAME_KANJI" 		//研究課題名
				+ ", NAIYAKUGAKU1" 			//1年目内約額
				+ ", NAIYAKUGAKU2" 			//2年目内約額
				+ ", NAIYAKUGAKU3" 			//3年目内約額
				+ ", NAIYAKUGAKU4" 			//4年目内約額
				+ ", NAIYAKUGAKU5" 			//5年目内約額
				+ " FROM MASTER_KEIZOKU"	//継続課題マスタ	
				+ " WHERE KENKYU_NO = ?"
				+ " AND KADAI_NO = ?";		//課題番号
			
			StringBuffer query = new StringBuffer(select);
			
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			KeizokuInfo result = new KeizokuInfo();
			preparedStatement.setString(i++, kenkyuNo);
			preparedStatement.setString(i++, kadaiNo);
			recordSet = preparedStatement.executeQuery();
			//データの個数分処理を繰り返し
			if(recordSet.next()){
				result.setKadaiNameKanji(recordSet.getString("KADAI_NAME_KANJI"));
				result.setNaiyakugaku1(recordSet.getString("NAIYAKUGAKU1"));
				result.setNaiyakugaku2(recordSet.getString("NAIYAKUGAKU2"));
				result.setNaiyakugaku3(recordSet.getString("NAIYAKUGAKU3"));
				result.setNaiyakugaku4(recordSet.getString("NAIYAKUGAKU4"));
				result.setNaiyakugaku5(recordSet.getString("NAIYAKUGAKU5"));
				//ListにKeizokuInfoを格納
				resultList.add(result);	
			
			}else{
				throw new NoDataFoundException(
					"継続課題マスタテーブルに該当するデータが見つかりません。検索キー：研究者番号'"
						+ kenkyuNo
						+"', 課題番号'"
						+ kadaiNo
						+ "'");
			}			
		} catch (SQLException ex) {
			throw new DataAccessException("継続課題マスタ検索実行中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return resultList;
	}
// ADD　END 2007-07-26 BIS 王志安
}
