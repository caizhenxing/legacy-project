/*======================================================================
 *    SYSTEM      : 
 *    Source name : QuestionShinseiInfoDao.java
 *    Description : アンケート情報ＤＢアクセスクラス
 *
 *    Author      : Admin
 *    Date        : 2005/10/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *　　2005/10/27    1.0         Amemiya     新規作成
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.vo.QuestionInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author user1
 *
 */
public class QuestionInfoDao {

	/** ログ */
	protected static final Log log = LogFactory.getLog(QuestionInfoDao.class);
		
	/**
	 * コンストラクタ。
	 */
	public QuestionInfoDao( ) {
	}

	
	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	
	/**
	 * アンケート内容登録
	 * @param connection
	 * @param addInfo
	 * @throws DataAccessException
	 * @throws DuplicateKeyException
	 */
	public void insertQuestionShinseishaInfo(
			Connection connection,
			QuestionInfo addInfo)
			throws DataAccessException, DuplicateKeyException {
		
		String query =
			"INSERT INTO QUESTION_OUBO ("
				+ "  UKETUKE_NO"
				+ ", KINYU_DATE"
				+ ", SHOZOKU_NAME"
				+ ", R1"
				+ ", A1"
				+ ", R2"
				+ ", A3"
				+ ", KANKYO_OS"
				+ ", KANKYO_OS_SONOTA"
				+ ", KANKYO_WEB"
				+ ", KANKYO_WEB_SONOTA"
				+ ", R3"
				+ ", R4"
				+ ", R5"
				+ ", R6"
				+ ", R7"
				+ ", R8"
				+ ", R9"
				+ ", R10"
				+ ", R11"
				+ ", R12"
				+ ", A4"
				+ ", R13"
				+ ", R14"
				+ ", A5"
				+ ", R15"
				+ ", R16"
				+ ", A6"
				+ ", A7"
				+ ", OUBO_KEISIKI"
				+ ", OUBO_KEISIKI_SONOTA"
				+ ", RIYOU_TIME"
				+ ", R17"
				+ ", A8"
				+ ", A9"
				+ ", A10"
				+ ", IP"			//2005.11.02 iso IPアドレスを追加
				+ ")"
				+ " VALUES "
//				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				+ "(?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement preparedStatement = null;
		try {
			
			
			//登録
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUketukeNo());
			//2005.11.21 iso 秒単位まで格納するよう変更
//			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKinyuDate());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBenri1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOs());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKankyoosSonota());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getWeb());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKankyowebSonota());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai5());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai6());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai7());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai8());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai9());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai10());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai11());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYonda1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai12());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA5());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYonda2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai13());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA6());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA7());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKeisiki());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOubokeisikiSonota());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRiyoutime());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBenri2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA8());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA9());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA10());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getIp());
			
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("アンケート情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * アンケート内容登録
	 * @param connection
	 * @param addInfo
	 * @throws DataAccessException
	 * @throws DuplicateKeyException
	 */
	public void insertQuestionShozokuInfo(
			Connection connection,
			QuestionInfo addInfo)
			throws DataAccessException, DuplicateKeyException {
		
		String query =
			"INSERT INTO QUESTION_SHOZOKU ("
				+ "  UKETUKE_NO"
				+ ", KINYU_DATE"
				+ ", SHOZOKU_NAME"
				+ ", R1"
				+ ", A1"
				+ ", R2"
				+ ", A3"
				+ ", KANKYO_OS"
				+ ", KANKYO_OS_SONOTA"
				+ ", KANKYO_WEB"
				+ ", KANKYO_WEB_SONOTA"
				+ ", R3"
				+ ", R4"
				+ ", R5"
				+ ", R6"
				+ ", R7"
				+ ", R8"
				+ ", R9"
				+ ", R10"
				+ ", R11"
				+ ", R12"
				+ ", A4"
				+ ", R13"
				+ ", R14"
				+ ", A5"
				+ ", R15"
				+ ", R16"
				+ ", A6"
				+ ", A7"
				+ ", R18"
				+ ", R19"
				+ ", A11"
				+ ", A12"
				+ ", RIYOU_TIME"
				+ ", R17"
				+ ", A8"
				+ ", OUBO_TOIAWASE_KENSU"
				
				//2005.11.10 iso チェックボックス対応
				+ ", OUBO_TOI_A"
				+ ", OUBO_TOI_B"
				+ ", OUBO_TOI_C"
				+ ", OUBO_TOI_D"
				+ ", OUBO_TOI_E"
				+ ", OUBO_TOI_F"
				
				+ ", OUBO_TOIAWASE_SONOTA"
				+ ", BUKYOKU_TOIAWASE_KENSU"
				+ ", BUKYOKU_TOI_A"
				+ ", BUKYOKU_TOI_B"
				+ ", BUKYOKU_TOI_C"
				+ ", BUKYOKU_TOI_D"
				+ ", BUKYOKU_TOI_E"
				+ ", BUKYOKU_TOI_F"				
				+ ", BUKYOKU_TOIAWASE_SONOTA"
				+ ", A9"
				+ ", A10"
				+ ", IP"			//2005.11.02 iso IPアドレスを追加
				+ ")"
				+ " VALUES "
//				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				+ "(?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement preparedStatement = null;
		try {
			
			
			//登録
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUketukeNo());
			//2005.11.21 iso 秒単位まで格納するよう変更
//			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKinyuDate());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBenri1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOs());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKankyoosSonota());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getWeb());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKankyowebSonota());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai5());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai6());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai7());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai8());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai9());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai10());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai11());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYonda1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai12());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA5());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYonda2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai13());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA6());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA7());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getCallriyou());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getCallrikai());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA11());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA12());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRiyoutime());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBenri2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA8());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getToiawase1());

			//2005.11.10 iso チェックボックス対応
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "a"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "b"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "c"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "d"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "e"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "f"));
			
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA13());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getToiawase2());
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getBukyokuToiValues(), "a"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getBukyokuToiValues(), "b"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getBukyokuToiValues(), "c"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getBukyokuToiValues(), "d"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getBukyokuToiValues(), "e"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getBukyokuToiValues(), "f"));
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA14());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA9());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA10());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getIp());
			
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("アンケート情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * アンケート内容登録
	 * @param connection
	 * @param addInfo
	 * @throws DataAccessException
	 * @throws DuplicateKeyException
	 */
	public void insertQuestionBukyokuInfo(
			Connection connection,
			QuestionInfo addInfo)
			throws DataAccessException, DuplicateKeyException {
		
		String query =
			"INSERT INTO QUESTION_BUKYOKU ("
				+ "  UKETUKE_NO"
				+ ", KINYU_DATE"
				+ ", SHOZOKU_NAME"
				+ ", R1"
				+ ", A1"
				+ ", R2"
				+ ", A3"
				+ ", KANKYO_OS"
				+ ", KANKYO_OS_SONOTA"
				+ ", KANKYO_WEB"
				+ ", KANKYO_WEB_SONOTA"
				+ ", R3"
				+ ", R4"
				+ ", R5"
				+ ", R6"
				+ ", R7"
				+ ", R8"
				+ ", R9"
				+ ", R10"
				+ ", R11"
				+ ", R12"
				+ ", A4"
				+ ", R15"
				+ ", R16"
				+ ", A6"
				+ ", A7"
				+ ", RIYOU_TIME"
				+ ", R17"
				+ ", A8"
				+ ", OUBO_TOIAWASE_KENSU"
				+ ", OUBO_TOI_A"
				+ ", OUBO_TOI_B"
				+ ", OUBO_TOI_C"
				+ ", OUBO_TOI_D"
				+ ", OUBO_TOI_E"
				+ ", OUBO_TOI_F"
				+ ", OUBO_TOIAWASE_SONOTA"
				+ ", A9"
				+ ", A10"
				+ ", IP"			//2005.11.02 iso IPアドレスを追加
				+ ")"
				+ " VALUES "
//				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				+ "(?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement preparedStatement = null;
		try {
			
			
			//登録
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUketukeNo());
			//2005.11.21 iso 秒単位まで格納するよう変更
//			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKinyuDate());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBenri1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOs());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKankyoosSonota());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getWeb());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKankyowebSonota());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai5());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai6());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai7());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai8());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai9());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai10());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai11());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYonda2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai13());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA6());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA7());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRiyoutime());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBenri2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA8());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getToiawase1());
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "a"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "b"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "c"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "d"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "e"));
			DatabaseUtil.setParameter(preparedStatement,i++, getChekFlg(addInfo.getOuboToiValues(), "f"));
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA13());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA9());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA10());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getIp());
			
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("アンケート情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	/**
	 * アンケート内容登録
	 * @param connection
	 * @param addInfo
	 * @throws DataAccessException
	 * @throws DuplicateKeyException
	 */
	public void insertQuestionShinsainInfo(
			Connection connection,
			QuestionInfo addInfo)
			throws DataAccessException, DuplicateKeyException {
		
		String query =
			"INSERT INTO QUESTION_SHINSAIN ("
				+ "  UKETUKE_NO"
				+ ", KINYU_DATE"
				+ ", SHINSAIN_NAME_SEI"
				+ ", SHINSAIN_NAME_MEI"
				+ ", R1"
				+ ", A1"
				+ ", R2"
				+ ", A3"
				+ ", KANKYO_OS"
				+ ", KANKYO_OS_SONOTA"
				+ ", KANKYO_WEB"
				+ ", KANKYO_WEB_SONOTA"
				+ ", R20"
				+ ", A15"
				+ ", R13"
				+ ", R14"
				+ ", A5"
				+ ", R15"
				+ ", R16"
				+ ", A6"
				+ ", A7"
				+ ", RIYOU_TIME"
				+ ", R17"
				+ ", A8"
				+ ", A10"
				+ ", IP"			//2005.11.02 iso IPアドレスを追加
				+ ")"
				+ " VALUES "
//				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				+ "(?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement preparedStatement = null;
		try {
			
			
			//登録
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUketukeNo());
//			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKinyuDate());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSHINSAIN_NAME_SEI());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSHINSAIN_NAME_MEI());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBenri1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOs());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKankyoosSonota());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getWeb());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKankyowebSonota());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai14());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA15());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYonda1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai12());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA15());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYonda2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRikai13());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA6());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA7());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRiyoutime());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBenri2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA8());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getA10());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getIp());
			
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("アンケート情報登録中に例外が発生しました。 ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/*
	 * Listに値が含まれていれば、1を返す。
	 * @param	list
	 * @param	values
	 */
	private String getChekFlg(List list, String value) {
		if(!list.isEmpty() && list.contains(value)) {
			return "1";
		} else {
			return "0";
		}
	}
	
}
