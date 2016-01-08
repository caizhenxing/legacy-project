/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/07/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaInfo;
import jp.go.jsps.kaken.model.vo.shinsei.KenkyuSoshikiKenkyushaPk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �����g�D�\�Ǘ��e�[�u���A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: KenkyuSoshikikanriDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class KenkyuSoshikikanriDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(KenkyuSoshikikanriDao.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���s���郆�[�U��� */
	private UserInfo userInfo = null;

	/** DB�����N�� */
	private String   dbLink   = "";
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	
	/**
	 * �R���X�g���N�^�B
	 * @param userInfo	���s���郆�[�U���
	 */
	public KenkyuSoshikikanriDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	/**
	 * �R���X�g���N�^�B
	 * @param userInfo ���s���郆�[�U���
	 * @param dbLink   DB�����N��
	 */
	public KenkyuSoshikikanriDao(UserInfo userInfo, String dbLink){
		this.userInfo = userInfo;
		this.dbLink   = dbLink;
	}


	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �L�[�Ɉ�v���錤���g�D�����擾����B
	 * @param connection			    �R�l�N�V����
	 * @param pkInfo				    ��L�[���
	 * @return						    �����g�D���
	 * @throws DataAccessException	    �f�[�^�擾���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public KenkyuSoshikiKenkyushaInfo selectKenkyuSoshikiKenkyushaInfo(
		Connection connection,
		KenkyuSoshikiKenkyushaPk pkInfo)
		throws DataAccessException, NoDataFoundException
	{
			String query =
				"SELECT "
					+ " SYSTEM_NO"
					+ ",SEQ_NO"
					+ ",JIGYO_ID"
					+ ",BUNTAN_FLG"
					+ ",KENKYU_NO"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",SHOZOKU_CD"
					+ ",SHOZOKU_NAME"
					+ ",BUKYOKU_CD"
					+ ",BUKYOKU_NAME"
					+ ",SHOKUSHU_CD"
					+ ",SHOKUSHU_NAME_KANJI"
					+ ",SENMON"
					+ ",GAKUI"
					+ ",BUNTAN"
					+ ",KEIHI"
					+ ",EFFORT"
					+ ",NENREI"
					+ " FROM KENKYUSOSHIKIKANRI"+dbLink
					+ " WHERE"
					+ "   SYSTEM_NO = ?"
					+ " AND"
					+ "   SEQ_NO = ?"
					;
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			try {
				KenkyuSoshikiKenkyushaInfo result = new KenkyuSoshikiKenkyushaInfo();
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getSystemNo());
				DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getSeqNo());				
				recordSet = preparedStatement.executeQuery();
				if (recordSet.next()) {
					result.setSystemNo(recordSet.getString("SYSTEM_NO"));
					result.setSeqNo(recordSet.getString("SEQ_NO"));
					result.setJigyoID(recordSet.getString("JIGYO_ID"));
					result.setBuntanFlag(recordSet.getString("BUNTAN_FLG"));
					result.setKenkyuNo(recordSet.getString("KENKYU_NO"));
					result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));
					result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));
					result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
					result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
					result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
					result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
					result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
					result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
					result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));
					result.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));
					result.setSenmon(recordSet.getString("SENMON"));
					result.setGakui(recordSet.getString("GAKUI"));
					result.setBuntan(recordSet.getString("BUNTAN"));
					result.setKeihi(recordSet.getString("KEIHI"));
					result.setEffort(recordSet.getString("EFFORT"));
					result.setNenrei(recordSet.getString("NENREI"));
					return result;
				} else {
					throw new NoDataFoundException(
						"�����g�D���e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
							+ pkInfo.getSystemNo()
							+ "'�A�V�[�P���X�ԍ�'"
							+ pkInfo.getSeqNo()
							+ "'");
				}
			} catch (SQLException ex) {
				throw new DataAccessException("�����g�D���e�[�u���������s���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}	
	
	

	/**
	 * �\�����̃L�[���Ɉ�v���錤���g�D�����擾����B
	 * KenkyuSoshikiKenkyushaInfo �̃��X�g���߂�B
	 * @param connection			    �R�l�N�V����
	 * @param pkInfo				    �\�����̃L�[���
	 * @return						    �����g�D�����҂̃��X�g
	 * @throws DataAccessException	    �f�[�^�擾���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public List selectKenkyuSoshikiKenkyushaInfo(
		Connection connection,
	    ShinseiDataPk pkInfo)
		throws DataAccessException, NoDataFoundException
	{
			String query =
				"SELECT "
					+ " SYSTEM_NO"
					+ ",SEQ_NO"
					+ ",JIGYO_ID"
					+ ",BUNTAN_FLG"
					+ ",KENKYU_NO"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",SHOZOKU_CD"
					+ ",SHOZOKU_NAME"
					+ ",BUKYOKU_CD"
					+ ",BUKYOKU_NAME"
					+ ",SHOKUSHU_CD"
					+ ",SHOKUSHU_NAME_KANJI"
					+ ",SENMON"
					+ ",GAKUI"
					+ ",BUNTAN"
					+ ",KEIHI"
					+ ",EFFORT"
					+ ",NENREI"
					+ " FROM KENKYUSOSHIKIKANRI"+dbLink
					+ " WHERE"
					+ "   SYSTEM_NO = ?"
					+ " ORDER BY"
					+ "   SEQ_NO"
					;
			PreparedStatement preparedStatement = null;
			ResultSet recordSet = null;
			try {
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getSystemNo());
				recordSet = preparedStatement.executeQuery();
				
				//�Y�����R�[�h�����ׂĎ擾
				List resultList = new ArrayList();
				while (recordSet.next()) {
					KenkyuSoshikiKenkyushaInfo result = new KenkyuSoshikiKenkyushaInfo();
					result.setSystemNo(recordSet.getString("SYSTEM_NO"));
					result.setSeqNo(recordSet.getString("SEQ_NO"));
					result.setJigyoID(recordSet.getString("JIGYO_ID"));
					result.setBuntanFlag(recordSet.getString("BUNTAN_FLG"));
					result.setKenkyuNo(recordSet.getString("KENKYU_NO"));
					result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));
					result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));
					result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
					result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
					result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));
					result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
					result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));
					result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
					result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));
					result.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));
					result.setSenmon(recordSet.getString("SENMON"));
					result.setGakui(recordSet.getString("GAKUI"));
					result.setBuntan(recordSet.getString("BUNTAN"));
					result.setKeihi(recordSet.getString("KEIHI"));
					result.setEffort(recordSet.getString("EFFORT"));
					result.setNenrei(recordSet.getString("NENREI"));
					resultList.add(result);	
				}
				
				//�Y�����R�[�h�����݂��Ȃ������ꍇ
				if(resultList.size() == 0){
					throw new NoDataFoundException(
						"�����g�D���e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
							+ pkInfo.getSystemNo()
							+ "'");
				}
				
				return resultList;
				
			} catch (SQLException ex) {
				throw new DataAccessException("�����g�D���e�[�u���������s���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(recordSet, preparedStatement);
			}
	}	
	
	
	
	/**
	 * �����g�D����o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^���錤���g�D���
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertKenkyuSoshikiKanriInfo(
		Connection connection,
		KenkyuSoshikiKenkyushaInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
			//�d���`�F�b�N
			try {
				selectKenkyuSoshikiKenkyushaInfo(connection, addInfo);
				//NG
				throw new DuplicateKeyException(
					"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
			} catch (NoDataFoundException e) {
				//OK
			}
		
			String query =
				"INSERT INTO KENKYUSOSHIKIKANRI"+dbLink
					+ "("
					+ " SYSTEM_NO"
					+ ",SEQ_NO"
					+ ",JIGYO_ID"
					+ ",BUNTAN_FLG"
					+ ",KENKYU_NO"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",SHOZOKU_CD"
					+ ",SHOZOKU_NAME"
					+ ",BUKYOKU_CD"
					+ ",BUKYOKU_NAME"
					+ ",SHOKUSHU_CD"
					+ ",SHOKUSHU_NAME_KANJI"
					+ ",SENMON"
					+ ",GAKUI"
					+ ",BUNTAN"
					+ ",KEIHI"
					+ ",EFFORT"
					+ ",NENREI"
					+ ") "
					+ "VALUES "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
					;
					
			PreparedStatement preparedStatement = null;
			try {
				//�o�^
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSystemNo());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSeqNo());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoID());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBuntanFlag());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuNo());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiSei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiMei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuName());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuNameKanji());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSenmon());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getGakui());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBuntan());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKeihi());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getEffort());
				DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNenrei());
				DatabaseUtil.executeUpdate(preparedStatement);
			} catch (SQLException ex) {
				throw new DataAccessException("�����g�D���o�^���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	
	
	/**
	 * �����g�D�����X�V����B
	 * @param connection				�R�l�N�V����
	 * @param updateInfo				�X�V���錤���g�D���
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateGyomutantoInfo(
		Connection connection,
		KenkyuSoshikiKenkyushaInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
			//����
			selectKenkyuSoshikiKenkyushaInfo(connection, updateInfo);
	
			String query =
				"UPDATE KENKYUSOSHIKIKANRI"+dbLink
					+ " SET"
					+ " SYSTEM_NO = ? "
					+ ",SEQ_NO = ? "
					+ ",JIGYO_ID = ? "
					+ ",BUNTAN_FLG = ? "
					+ ",KENKYU_NO = ? "
					+ ",NAME_KANJI_SEI = ? "
					+ ",NAME_KANJI_MEI = ? "
					+ ",NAME_KANA_SEI = ? "
					+ ",NAME_KANA_MEI = ? "
					+ ",SHOZOKU_CD = ? "
					+ ",SHOZOKU_NAME = ? "
					+ ",BUKYOKU_CD = ? "
					+ ",BUKYOKU_NAME = ? "
					+ ",SHOKUSHU_CD = ? "
					+ ",SHOKUSHU_NAME_KANJI = ? "
					+ ",SENMON = ? "
					+ ",GAKUI = ? "
					+ ",BUNTAN = ? "
					+ ",KEIHI = ? "
					+ ",EFFORT = ? "
					+ ",NENREI"					
					+ " WHERE"
					+ "   SYSTEM_NO = ?"
					+ " AND"
					+ "   SEQ_NO = ?"
					;

			PreparedStatement preparedStatement = null;
			try {
				//�o�^
				preparedStatement = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSystemNo());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSeqNo());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoID());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBuntanFlag());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuNo());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuCd());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuNameKanji());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSenmon());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getGakui());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBuntan());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKeihi());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getEffort());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNenrei());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSystemNo());
				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSeqNo());
				DatabaseUtil.executeUpdate(preparedStatement);
			} catch (SQLException ex) {
				throw new DataAccessException("�����g�D���X�V���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(null, preparedStatement);
			}
	}
	
	
	
	/**
	 * �����g�D�����폜����B(�����폜)
	 * @param connection			    �R�l�N�V����
	 * @param pkInfo				    �폜���錤���g�D��L�[���
	 * @throws DataAccessException     �폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException    �Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteKenkyuSoshikiKanriInfo(
		Connection connection,
		KenkyuSoshikiKenkyushaInfo deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�폜�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectKenkyuSoshikiKenkyushaInfo(connection, deleteInfo);
				
		String query =
			"DELETE FROM KENKYUSOSHIKIKANRI"+dbLink
				+ " WHERE"
				+ "   SYSTEM_NO = ?"
				+ " AND"
				+ "   SEQ_NO = ?"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getSeqNo());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�����g�D���폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	
	
	/**
	 * �����g�D�����폜����B(�����폜)
	 * ���Y�\�����ɕR�t�������g�D����S�č폜����B
	 * @param connection			    �R�l�N�V����
	 * @param pkInfo				    �폜����\������L�[���
	 * @throws DataAccessException     �폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException    �Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteKenkyuSoshikiKanriInfo(
		Connection connection,
		ShinseiDataPk pkInfo)
		throws DataAccessException, NoDataFoundException
	{
				
		String query =
			"DELETE FROM KENKYUSOSHIKIKANRI"+dbLink
				+ " WHERE"
				+ "   SYSTEM_NO = ?"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,pkInfo.getSystemNo());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�����g�D���폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	
		
	/**
	 * �����g�D�����폜����B(�����폜)
	 * ���Y���ƂɕR�t�������g�D����S�č폜����B
	 * @param connection			    �R�l�N�V����
	 * @param pkInfo				    �폜���鎖��ID
	 * @throws DataAccessException     �폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException    �Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteKenkyuSoshikiKanriInfo(
		Connection connection,
		String jigyoId)
		throws DataAccessException, NoDataFoundException
	{
				
		String query =
			"DELETE FROM KENKYUSOSHIKIKANRI"+dbLink
				+ " WHERE"
				+ "   JIGYO_ID = ?"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,jigyoId);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�����g�D���폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
			
	/**
	 * CSV�o�͂��郊�X�g��Ԃ��B
	 * @param connection			    �R�l�N�V����
	 * @param pkInfo				    �폜����\������L�[���
	 * @throws DataAccessException     �폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException    �Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public List searchCsvData(
		Connection connection,
		ShinseiSearchInfo searchInfo)
		throws DataAccessException, NoDataFoundException
	{
		//SQL�̌��������������쐬
		String query = ShinseiDataInfoDao.getQueryString("", searchInfo);
		
		String select =	
		"SELECT "
			+ " C.UKETUKE_NO				\"�\���ԍ�\"				"
			+ ",B.SEQ_NO					\"�V�[�P���X�ԍ�\"			"
			+ ",B.JIGYO_ID					\"����ID\"					"
			+ ",B.BUNTAN_FLG				\"��\�ҕ��S�ҕ�\"			"
			+ ",B.KENKYU_NO					\"�����Ҕԍ�\"				"
			+ ",B.NAME_KANJI_SEI			\"�����i�����|���j\"			"
			+ ",B.NAME_KANJI_MEI			\"�����i�����|���j\"			"
			+ ",B.NAME_KANA_SEI				\"�����i�t���K�i�|���j\"		"
			+ ",B.NAME_KANA_MEI				\"�����i�t���K�i�|���j\"		"
			+ ",B.SHOZOKU_CD				\"���������@�֖��i�ԍ��j\"	"
			+ ",B.SHOZOKU_NAME				\"���������@�֖��i�a���j\"	"
			+ ",B.BUKYOKU_CD				\"���ǖ��i�ԍ��j\"			"
			+ ",B.BUKYOKU_NAME				\"���ǖ��i�a���j\"		"
			+ ",B.SHOKUSHU_CD				\"�E���i�ԍ��j\"			"
			+ ",B.SHOKUSHU_NAME_KANJI		\"�E���i�a���j\"			"
			+ ",B.SENMON					\"���݂̐��\"			"
			+ ",B.GAKUI						\"�w��\"					"
			+ ",B.BUNTAN					\"�������S\"				"
			+ ",B.KEIHI						\"�����o��\"				"
			+ ",B.EFFORT					\"�G�t�H�[�g\"			"
			+ ",B.NENREI					\"�N��\"					"
			+ " FROM "
			+ "   KENKYUSOSHIKIKANRI"+dbLink+" B"
			+ "  ,(SELECT  A.SYSTEM_NO"
			+ "           ,A.UKETUKE_NO "
			+ "            FROM SHINSEIDATAKANRI"+dbLink+" A "
			+              query					//�₢���킹��������
			+ "   ) C"
			+ " WHERE"
			+ "   B.SYSTEM_NO = C.SYSTEM_NO"
			+ " ORDER BY "
			+ "   C.UKETUKE_NO, B.SEQ_NO"
			;	
	
			//for debug
			if(log.isDebugEnabled()){
				log.debug("query:" + select);
			}
	
		//CSV���X�g�擾�i�J���������L�[���ږ��Ƃ���j
		return SelectUtil.selectCsvList(connection, select, true);
	
	}
		
	
		
	/**
	 * ���[�J���ɑ��݂���Y�����R�[�h�̓��e��DBLink��̃e�[�u���ɑ}������B
	 * DBLink��ɓ������R�[�h������ꍇ�́A�\�ߍ폜���Ă������ƁB
	 * DBLink���ݒ肳��Ă��Ȃ��ꍇ�̓G���[�ƂȂ�B
	 * @param connection
	 * @param jigyoId
	 * @throws DataAccessException
	 */
	public void copy2HokanDB(
		Connection connection,
		String     jigyoId)
		throws DataAccessException
	{
		//DBLink�����Z�b�g����Ă��Ȃ��ꍇ
		if(dbLink == null || dbLink.length() == 0){
			throw new DataAccessException("DB�����N�����ݒ肳��Ă��܂���BDBLink="+dbLink);
		}
		
		String query =
			"INSERT INTO KENKYUSOSHIKIKANRI"+dbLink
				+ " SELECT * FROM KENKYUSOSHIKIKANRI WHERE JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);		//���������i����ID�j
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�����g�D�\�Ǘ��e�[�u���ۊǒ��ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
}
