/*
 * �쐬��: 2005/03/28
 *
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.*;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * �����҃}�X�^�f�[�^�A�N�Z�X�N���X
 * 
 * @author yoshikawa_h
 *
 */
public class MasterKenkyushaInfoDao {
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterKenkyushaInfoDao.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���s���郆�[�U��� */
	private UserInfo userInfo = null;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	
	/**
	 * �R���X�g���N�^�B
	 * @param userInfo	���s���郆�[�U���
	 */
	public MasterKenkyushaInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	/**
	 * �L�[�Ɉ�v���錤���ҏ����擾����B
	 * �������̌����ҏ��������҃}�X�^����擾����B
	 * 
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @param lock					���b�N�̗L��
	 * @return						�����ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public KenkyushaInfo selectKenkyushaInfo(Connection connection,KenkyushaPk primaryKey,boolean lock)
		throws DataAccessException, NoDataFoundException {

		KenkyushaInfo result = new KenkyushaInfo();
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			StringBuffer query = new StringBuffer();
			
			query.append(
				"SELECT "
				+ " KENKYU.KENKYU_NO"				//�����Ҕԍ�
				+ ", KENKYU.NAME_KANJI_SEI"			//�����i������-���j
				+ ", KENKYU.NAME_KANJI_MEI"			//�����i������-���j
				+ ", KENKYU.NAME_KANA_SEI"			//�����i�t���K�i-���j
				+ ", KENKYU.NAME_KANA_MEI"			//�����i�t���K�i-���j
				+ ", KENKYU.SEIBETSU"				//����
				+ ", KENKYU.BIRTHDAY"				//���N����
				+ ", KENKYU.GAKUI"					//�w��
				+ ", KENKYU.SHOZOKU_CD"				//�����@�փR�[�h
				+ ", KIKAN.SHOZOKU_NAME_KANJI"		//�����@�֖��i�a���j
				+ ", KIKAN.SHOZOKU_NAME_EIGO"		//�����@�֖��i�p���j
				+ ", KIKAN.SHOZOKU_RYAKUSHO"		//�����@�֖��i���́j
				+ ", KENKYU.BUKYOKU_CD"				//���ǃR�[�h
				+ ", BUKYOKU.BUKA_NAME"				//���ǖ�
				+ ", BUKYOKU.BUKA_RYAKUSHO"			//���Ǘ���
				+ ", KENKYU.SHOKUSHU_CD"			//�E�R�[�h
				+ ", SHOKU.SHOKUSHU_NAME"			//�E��
				+ ", SHOKU.SHOKUSHU_NAME_RYAKU"		//�E����
				+ ", KENKYU.KOSHIN_DATE"			//�X�V����
				+ ", KENKYU.BIKO"					//���l
				//2005/04/21�@�ǉ� ��������----------------------------------------
				//�������}�X�^�ɍ폜�t���O�ǉ��̂���
				+ ", KENKYU.DEL_FLG"				//�폜�t���O
				//�ǉ� �����܂�----------------------------------------------------
				+ " FROM MASTER_KENKYUSHA KENKYU"					//�����҃}�X�^
					+ " INNER JOIN MASTER_KIKAN KIKAN"				//�����@�փ}�X�^
						+ " ON KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD"
					+ " INNER JOIN MASTER_BUKYOKU BUKYOKU"			//���ǃ}�X�^
						+ " ON KENKYU.BUKYOKU_CD = BUKYOKU.BUKYOKU_CD"
					+ " INNER JOIN MASTER_SHOKUSHU SHOKU"			//�E��}�X�^
						+ " ON KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD"
				+ " WHERE KENKYU.SHOZOKU_CD = ?"
				+ " AND KENKYU.KENKYU_NO = ?"		//��L�[�F�����@�փR�[�h�A�����Ҕԍ�
				//2005/04/21�@�ǉ� ��������----------------------------------------
				//�������}�X�^�ɍ폜�t���O�ǉ��̂���
				+ " AND KENKYU.DEL_FLG = 0"
				//�ǉ� �����܂�----------------------------------------------------
			);
			
			//�r������
			if(lock){
				query.append(" FOR UPDATE");
			}
			
			preparedStatement =
				connection.prepareStatement(query.toString());
			
			int i = 1;
			preparedStatement.setString(i++, primaryKey.getShozokuCd());
			preparedStatement.setString(i++, primaryKey.getKenkyuNo());
			
			recordSet = preparedStatement.executeQuery();
			
			if (recordSet.next()) {
				result.setKenkyuNo(recordSet.getString("KENKYU_NO"));					//�����Ҕԍ�
				result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));			//�����i������-���j
				result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));			//�����i������-���j
				result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));			//�����i�t���K�i-���j
				result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));			//�����i�t���K�i-���j
				result.setSeibetsu(recordSet.getString("SEIBETSU"));					//����
				result.setBirthday(recordSet.getDate("BIRTHDAY"));						//���N����
				result.setGakui(recordSet.getString("GAKUI"));							//�w��
				result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));					//�����@�փR�[�h
				result.setShozokuNameKanji(recordSet.getString("SHOZOKU_NAME_KANJI"));	//�����@�֖��i�a���j
				result.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));	//�����@�֖��i�p���j
				result.setShozokuRyakusho(recordSet.getString("SHOZOKU_RYAKUSHO"));		//�����@�֖��i���́j
				result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));					//���ǃR�[�h
				result.setBukyokuName(recordSet.getString("BUKA_NAME"));				//���ǖ�
				result.setBukyokuNameRyaku(recordSet.getString("BUKA_RYAKUSHO"));		//���Ǘ���
				result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));				//�E�R�[�h
				result.setShokushuName(recordSet.getString("SHOKUSHU_NAME"));			//�E��
				result.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));//�E������
				result.setKoshinDate(recordSet.getDate("KOSHIN_DATE"));					//�X�V����
				result.setBiko(recordSet.getString("BIKO"));							//���l
				//...
			}else{
				throw new NoDataFoundException(
						"�����҃}�X�^�ɊY������f�[�^��������܂���B�����L�[�F'�����Ҕԍ�'"
						+ primaryKey.getKenkyuNo()
						+ " '�����@�փR�[�h'" + primaryKey.getShozokuCd()
						+ "");
			}
			
		} catch (SQLException ex) {
			throw new DataAccessException("�����҃}�X�^�������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return result;
	}
	
	/**
	 * ���o�^�\���ҏ����擾����B
	 * 
	 * @param userInfo
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public Page searchUnregist(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
	throws ApplicationException {
		
		//�����@�փR�[�h�擾
		String shozokuCd = EscapeUtil.toSqlString(searchInfo.getShozokuCd());
		
		String sql = "";
		
		if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
			//���ǒS���҂̂Ƃ�
			BukyokutantoInfo info = userInfo.getBukyokutantoInfo();
			
			if(info.getTantoFlg()){
				//�S�����ǂ̐ݒ肪����Ƃ�
				sql =
					" TANTOBUKYOKUKANRI TANTO"
						+ " INNER JOIN MASTER_KENKYUSHA KENKYU2"
						+ " ON TANTO.BUKYOKU_CD = KENKYU2.BUKYOKU_CD"
						+ " AND KENKYU2.SHOZOKU_CD = '" + shozokuCd + "'"
						//2005/04/21�@�ǉ� ��������----------------------------------------
						//�������}�X�^�ɍ폜�t���O�ǉ��̂���
						+ " AND KENKYU2.DEL_FLG = 0"
						//�ǉ� �����܂�----------------------------------------------------
						+ " WHERE"
						+ " TANTO.BUKYOKUTANTO_ID = '" + EscapeUtil.toSqlString(info.getBukyokutantoId()) + "'";
			
			//2005/04/19 �ǉ� ��������-------------------------------------------------
			//���R ���ǒS���҂ŒS�����ǂ̐ݒ肪�Ȃ��ꍇ�̏�����ǉ�
			}else{
				sql = " MASTER_KENKYUSHA KENKYU2"
					+ " WHERE"
					+ " KENKYU2.DEL_FLG = 0 ";
			}
			//�ǉ� �����܂�------------------------------------------------------------
			
		}else{
			sql = " MASTER_KENKYUSHA KENKYU2"
				+ " WHERE"
				+ " KENKYU2.DEL_FLG = 0 ";
		}
		
		
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
				+ " KENKYU.KENKYU_NO,"
				+ " KENKYU.NAME_KANJI_SEI,"
				+ " KENKYU.NAME_KANJI_MEI,"
				+ " KENKYU.NAME_KANA_SEI,"
				+ " KENKYU.NAME_KANA_MEI,"
				+ " KENKYU.SEIBETSU,"
				+ " KENKYU.BIRTHDAY,"
				+ " KENKYU.GAKUI,"
				+ " KENKYU.SHOZOKU_CD,"
				+ " KENKYU.BUKYOKU_CD,"
				+ " BUKYOKU.BUKA_RYAKUSHO,"
				+ " KENKYU.SHOKUSHU_CD,"
				+ " SHOKU.SHOKUSHU_NAME_RYAKU,"
				+ " KENKYU.KOSHIN_DATE,"
				+ " KENKYU.BIKO"
				+ " FROM"
				+ "("
					+ "SELECT"
						+ " KENKYU2.KENKYU_NO,"
						+ " KENKYU2.NAME_KANA_SEI,"
						+ " KENKYU2.NAME_KANA_MEI,"
						+ " KENKYU2.NAME_KANJI_SEI,"
						+ " KENKYU2.NAME_KANJI_MEI,"
						+ " KENKYU2.SEIBETSU,"
						+ " KENKYU2.BIRTHDAY,"
						+ " KENKYU2.GAKUI,"
						+ " KENKYU2.SHOZOKU_CD,"
						+ " KENKYU2.BUKYOKU_CD,"
						+ " KENKYU2.SHOKUSHU_CD,"
						+ " KENKYU2.KOSHIN_DATE,"
						+ " KENKYU2.BIKO"
					+ " FROM"
					+ sql
					+ " MINUS"
					+ " SELECT"
						+ " MK.KENKYU_NO,"
						+ " MK.NAME_KANA_SEI,"
						+ " MK.NAME_KANA_MEI,"
						+ " MK.NAME_KANJI_SEI,"
						+ " MK.NAME_KANJI_MEI,"
						+ " MK.SEIBETSU,"
						+ " MK.BIRTHDAY,"
						+ " MK.GAKUI,"
						+ " MK.SHOZOKU_CD,"
						+ " MK.BUKYOKU_CD,"
						+ " MK.SHOKUSHU_CD,"
						+ " MK.KOSHIN_DATE,"
						+ " MK.BIKO"
					+ " FROM"
						+ " SHINSEISHAINFO SH"
						+ " INNER JOIN MASTER_KENKYUSHA MK"
						//2005.06.16 iso �Ⴄ�����@�ւɓ��������Ҕԍ��̌����҂�����Ɩ��o�^�ꗗ�ɕ\������Ȃ��o�O���C��
//						+ " ON SH.KENKYU_NO = MK.KENKYU_NO"
						+ " ON (SH.KENKYU_NO = MK.KENKYU_NO AND SH.SHOZOKU_CD = MK.SHOZOKU_CD)"
						+ " AND MK.SHOZOKU_CD = '" + shozokuCd + "'"
						//2005/04/21�@�ǉ� ��������----------------------------------------
						//�������}�X�^�ɍ폜�t���O�ǉ��̂���
						+ " AND MK.DEL_FLG = 0"
						//�ǉ� �����܂�----------------------------------------------------
						+ " WHERE"
						+ " SH.DEL_FLG = 0"
				+ " ) KENKYU"
					+ " INNER JOIN MASTER_BUKYOKU BUKYOKU"
						+ " ON KENKYU.BUKYOKU_CD = BUKYOKU.BUKYOKU_CD"
						+ " INNER JOIN MASTER_SHOKUSHU SHOKU"
						+ " ON KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD";
		
		StringBuffer query = new StringBuffer(select);
		
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	//�\���Ҏ����i����-���j
			query.append(" AND KENKYU.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	//�\���Ҏ����i����-���j
			query.append(" AND KENKYU.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	//�\���Ҏ����i�t���K�i-���j
			query.append(" AND KENKYU.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
		}
		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){	//�\���Ҏ����i�t���K�i-���j
			query.append(" AND KENKYU.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
		}
		if(searchInfo.getBukyokuCd() != null && !searchInfo.getBukyokuCd().equals("")){		//���ǃR�[�h
			query.append(" AND KENKYU.BUKYOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBukyokuCd()) +"'");
		
		}
		if(searchInfo.getKenkyuNo() != null && !searchInfo.getKenkyuNo().equals("")){			//�����Ҕԍ�
			query.append(" AND KENKYU.KENKYU_NO = '" + EscapeUtil.toSqlString(searchInfo.getKenkyuNo()) +"'");
		}
		//2005/04/19 �ǉ� ��������-------------------------------------------------------------
		//���R �����̏�������\���҂̏��݂̂�\������悤�ɏ�����ǉ�
		if(shozokuCd != null && !shozokuCd.equals("")){
			query.append(" AND KENKYU.SHOZOKU_CD = '" + shozokuCd +"'");
		}
		//�ǉ� �����܂�------------------------------------------------------------------------

		//�\�[�g���i�\����ID�̏����j
		query.append(" ORDER BY KENKYU.KENKYU_NO");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection,searchInfo, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���o�^�\���҃f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * �����ҏ��̐����擾����B
	 * 
	 * @param connection			�R�l�N�V����
	 * @param searchInfo			�����ҏ��
	 * @return						�����ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public int countKenkyushaInfo(
		Connection connection,
		KenkyushaInfo searchInfo)
		throws DataAccessException {
		return countKenkyushaInfo(connection, searchInfo, true);
	}
	
	//2005/04/15 �ǉ� ��������---------------------------------------------------------------
	//�����ҏ��̏����̂���
	/**
	 * �����ҏ��̐����擾����B
	 * 
	 * @param connection			�R�l�N�V����
	 * @param searchInfo			�����ҏ��
	 * @param delFlg				�폜�t���O�`�F�b�N���邩
	 * @return						�����ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public int countKenkyushaInfo(
		Connection connection,
		KenkyushaInfo searchInfo, boolean delFlg)
		throws DataAccessException {
			
		int count = 0;	
		String query =
				"SELECT COUNT(*)"
					+ " FROM MASTER_KENKYUSHA"
					+ " WHERE KENKYU_NO = ?"
					+ " AND SHOZOKU_CD = ?";
					//2005/04/21�@�ǉ� ��������----------------------------------------
					//�������}�X�^�ɍ폜�t���O�ǉ��̂���
		//2005/8/26 
		if (delFlg){
			query = query + " AND DEL_FLG = 0";
		}
				//�ǉ� �����܂�----------------------------------------------------
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			//�����Ҕԍ�
			DatabaseUtil.setParameter(preparedStatement, i++, searchInfo.getKenkyuNo());
			//����CD
			DatabaseUtil.setParameter(preparedStatement, i++, searchInfo.getShozokuCd());
			recordSet = preparedStatement.executeQuery();
			
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�����ҏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return count;
	}
	
	
	/**
	 * �����ҏ���o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^���镔�Ǐ��
	 * @throws DataAccessException		�o�^�ɗ�O�����������ꍇ�B
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertKenkyushaInfo(Connection connection,KenkyushaInfo addInfo)
		throws DataAccessException,DuplicateKeyException {
		
		//�d���o�^�`�F�b�N
		int count = countKenkyushaInfo(connection, addInfo);
		if(count > 0){//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		}
		
		String query = "INSERT INTO MASTER_KENKYUSHA "
					+ "("
					+ " KENKYU_NO"
					+ ",NAME_KANA_SEI"
					+ ",NAME_KANA_MEI"
					+ ",NAME_KANJI_SEI"
					+ ",NAME_KANJI_MEI"
					+ ",SEIBETSU"
					+ ",BIRTHDAY"
					+ ",GAKUI"
					+ ",SHOZOKU_CD"
					+ ",BUKYOKU_CD"
					+ ",SHOKUSHU_CD"
					+ ",OUBO_SHIKAKU"
					//2007/4/27�@�ǉ�
					+ ",OTHER_KIKAN1_FLG"
					+ ",OTHER_KIKAN1_CD"
					+ ",OTHER_KIKAN1_NAME"
					+ ",OTHER_KIKAN2_FLG"
					+ ",OTHER_KIKAN2_CD"
					+ ",OTHER_KIKAN2_NAME"
					+ ",OTHER_KIKAN3_FLG"
					+ ",OTHER_KIKAN3_CD"
					+ ",OTHER_KIKAN3_NAME"
					+ ",OTHER_KIKAN4_FLG"
					+ ",OTHER_KIKAN4_CD"
					+ ",OTHER_KIKAN4_NAME"
					//2007/4/27�@�ǉ�����
					+ ",KOSHIN_DATE"
					+ ",BIKO"
					//2005/04/21�@�ǉ� ��������----------------------------------------
					//�������}�X�^�ɍ폜�t���O�ǉ��̂���
					+ ",DEL_FLG"				//�폜�t���O
					//�ǉ� �����܂�----------------------------------------------------
					+ ") "
					+ "VALUES "
					+ "(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)"
					;

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSeibetsu());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBirthday());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getGakui());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuCd());
			//2006/02/08�@�ǉ��@��������----------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOuboShikaku());

			//2007/4/27 �ǉ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanFlg1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanCd1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanName1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanFlg2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanCd2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanName2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanFlg3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanCd3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanName3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanFlg4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanCd4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherKikanName4());
			//2007/4/27 �ǉ�����
			
			//2005/04/22�@�ǉ��@��������----------------------------------------------
			//�X�V���t��KenkyushaInfo�̒l�œn���悤�ɕύX 
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKoshinDate());
			//�ǉ� �����܂�-----------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			//2005/04/22�@�ǉ��@��������----------------------------------------------
			//�폜�t���O��KenkyushaInfo�̒l�œn���悤�ɕύX 
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getDelFlg());
			//�ǉ� �����܂�-----------------------------------------------------------
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("�����҃}�X�^�o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
		
		
	/**
	 * �����ҏ����X�V����B
	 * 
	 * @param connection			�R�l�N�V����
	 * @param addInfo				�X�V���錤���ҏ��
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateKenkyushaInfo(
		Connection connection,
		KenkyushaInfo updateInfo)
		throws DataAccessException, ApplicationException{
	
		String query =
			"UPDATE MASTER_KENKYUSHA"
				+ " SET"
					+ " KENKYU_NO = ? "
					+ ",NAME_KANA_SEI = ? "
					+ ",NAME_KANA_MEI = ? "
					+ ",NAME_KANJI_SEI = ? "
					+ ",NAME_KANJI_MEI = ? "
					+ ",SEIBETSU = ? "
					+ ",BIRTHDAY = ? "
					+ ",GAKUI = ? "
					+ ",SHOZOKU_CD = ? "
					+ ",BUKYOKU_CD = ? "
					+ ",SHOKUSHU_CD = ? "
					+ ",OUBO_SHIKAKU = ?"
					//2007/4/27�@�ǉ�
					+ ",OTHER_KIKAN1_FLG = ?"
					+ ",OTHER_KIKAN1_CD = ?"
					+ ",OTHER_KIKAN1_NAME = ?"
					+ ",OTHER_KIKAN2_FLG = ?"
					+ ",OTHER_KIKAN2_CD = ?"
					+ ",OTHER_KIKAN2_NAME = ?"
					+ ",OTHER_KIKAN3_FLG = ?"
					+ ",OTHER_KIKAN3_CD = ?"
					+ ",OTHER_KIKAN3_NAME = ?"
					+ ",OTHER_KIKAN4_FLG = ?"
					+ ",OTHER_KIKAN4_CD = ?"
					+ ",OTHER_KIKAN4_NAME = ?"
					//2007/4/27�@�ǉ�����
					+ ",KOSHIN_DATE = ? "
					+ ",BIKO = ? "
					//2005/04/21�@�ǉ� ��������----------------------------------------
					//�������}�X�^�ɍ폜�t���O�ǉ��̂���
					+ ",DEL_FLG = ? "				//�폜�t���O
					//�ǉ� �����܂�----------------------------------------------------
				+ " WHERE"
					+ " KENKYU_NO = ?"
					+ " AND SHOZOKU_CD = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSeibetsu());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBirthday());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getGakui());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuCd());
			//2006/02/08�@�ǉ��@��������----------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOuboShikaku());
			
			//2007/4/27 �ǉ�
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanFlg1());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanCd1());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanName1());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanFlg2());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanCd2());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanName2());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanFlg3());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanCd3());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanName3());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanFlg4());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanCd4());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherKikanName4());
			//2007/4/27 �ǉ�����
			
			//2005/04/22�@�ǉ��@��������----------------------------------------------
			//�X�V���t��KenkyushaInfo�̒l�œn���悤�ɕύX 
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKoshinDate());
			//�ǉ� �����܂�-----------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
			//2005/04/22�@�ǉ��@��������----------------------------------------------
			//�폜�t���O��KenkyushaInfo�̒l�œn���悤�ɕύX 
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDelFlg());
			//�ǉ� �����܂�-----------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			DatabaseUtil.executeUpdate(preparedStatement);
			
			//2005/04/22 �폜 ��������
			//���R �\���ҏ��̃`�F�b�N��Maintenance�N���X�ōs���悤�ɏC��
		/*	
			//�\���ҏ��̃`�F�b�N
			HashMap shinseiMap = new HashMap();
			ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
			searchInfo.setKenkyuNo(updateInfo.getKenkyuNo());
			searchInfo.setShozokuCd(updateInfo.getShozokuCd());
			ShinseishaMaintenance shinsei = new ShinseishaMaintenance();
			boolean existShinseishaInfo = true;
			try{
				Page shinseishaPage = shinsei.search(userInfo, searchInfo);
				shinseiMap = (HashMap)shinseishaPage.getList().get(0);
			}catch(NoDataFoundException e){
				//�Y������f�[�^���Ȃ��ꍇ�͂��̂܂܏������I����
				existShinseishaInfo = false;
			}
			if(existShinseishaInfo
				&& shinseiMap.get("SHINSEISHA_ID") != null 
				&& !shinseiMap.get("SHINSEISHA_ID").equals("")){
				
				ShinseishaInfoDao shinseiDao = new ShinseishaInfoDao(userInfo);
				ShinseishaInfo shinseiInfo = new ShinseishaInfo();
				shinseiInfo.setShinseishaId((String)shinseiMap.get("SHINSEISHA_ID"));
				shinseiInfo.setBirthday(updateInfo.getBirthday());
				shinseiInfo.setBukyokuCd(updateInfo.getBukyokuCd());
				shinseiInfo.setBukyokuName(updateInfo.getBukyokuName());
				shinseiInfo.setBukyokuNameRyaku(updateInfo.getBukyokuNameRyaku());
				shinseiInfo.setKenkyuNo(updateInfo.getKenkyuNo());
				shinseiInfo.setNameKanaMei(updateInfo.getNameKanaMei());
				shinseiInfo.setNameKanaSei(updateInfo.getNameKanaSei());
				shinseiInfo.setNameKanjiMei(updateInfo.getNameKanjiMei());
				shinseiInfo.setNameKanjiSei(updateInfo.getNameKanjiSei());
				shinseiInfo.setShokushuCd(updateInfo.getShokushuCd());
				shinseiInfo.setShokushuNameKanji(updateInfo.getShokushuName());
				shinseiInfo.setShokushuNameRyaku(updateInfo.getShokushuNameRyaku());
				shinseiInfo.setShozokuCd(updateInfo.getShozokuCd());
				shinseiInfo.setShozokuName(updateInfo.getShozokuNameKanji());
				shinseiInfo.setShozokuNameRyaku(updateInfo.getShozokuRyakusho());
				shinseiInfo.setShozokuNameEigo(updateInfo.getShozokuNameEigo());
				shinseiInfo.setBukyokuShubetuName((String)shinseiMap.get("OTHER_BUKYOKU"));
				shinseiInfo.setBukyokuShubetuCd((String)shinseiMap.get("SHUBETU_CD"));
				shinseiInfo.setDelFlg(shinseiMap.get("DEL_FLG").toString());
				shinseiInfo.setHakkoDate((Date)shinseiMap.get("HAKKO_DATE"));
				shinseiInfo.setHakkoshaId((String)shinseiMap.get("HAKKOSHA_ID"));
				shinseiInfo.setHikoboFlg(shinseiMap.get("HIKOBO_FLG").toString());
				shinseiInfo.setNameRoMei((String)shinseiMap.get("NAME_RO_MEI"));
				shinseiInfo.setNameRoSei((String)shinseiMap.get("NAME_RO_SEI"));
				shinseiInfo.setPassword((String)shinseiMap.get("PASSWORD"));
				shinseiInfo.setYukoDate((Date)shinseiMap.get("YUKO_DATE"));
				shinseiDao.updateShinseishaInfo(connection, shinseiInfo);
			}
			*/	
			//�폜 �����܂�------------------------------------------------------------
			
		} catch (SQLException ex) {
			throw new DataAccessException("�����ҏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * �����ҏ����폜����B<BR>
	 * 
	 * �����҃}�X�^�̏��𕨗��폜������A�\���ҏ�񂪂���ꍇ�͐\���ҏ���DEL_FLG��1���Z�b�g����B
	 * 
	 * @param connection			    �R�l�N�V����
	 * @param pkInfo				    �폜���錤���ҏ��
	 * @throws DataAccessException     �폜���ɗ�O�����������ꍇ
	 */
	public void deleteKenkyushaInfo(
		Connection connection,
		KenkyushaInfo deleteInfo)
		throws DataAccessException, ApplicationException{
		
		String query =
			//2005/04/21�@�ǉ� ��������----------------------------------------
			//�������}�X�^�ɍ폜�t���O��ǉ��������߁A�����폜����_���폜�ɕύX
			
			//"DELETE FROM MASTER_KENKYUSHA"		
			"UPDATE MASTER_KENKYUSHA SET DEL_FLG = 1"
			
			//�ǉ� �����܂�----------------------------------------------------
				+ " WHERE"
				+ "   KENKYU_NO = ?"
				+ " AND"
				+ "   SHOZOKU_CD = ?"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getKenkyuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getShozokuCd());
			//�����҃}�X�^�̃��R�[�h�폜
			DatabaseUtil.executeUpdate(preparedStatement);
			
			//2005/08/29 takano �\����ID�͍폜���Ȃ��i���O�C�����Ɍ����҃}�X�^�̑��݃`�F�b�N������B�j��������
			////�\���ҏ��̍폜����
			//HashMap shinseiMap = new HashMap();
			//ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
			//searchInfo.setKenkyuNo(deleteInfo.getKenkyuNo());
			//searchInfo.setShozokuCd(deleteInfo.getShozokuCd());
			//ShinseishaMaintenance shinsei = new ShinseishaMaintenance();
			//boolean existShinseishaInfo = true;
			//try{
			//	Page shinseishaPage = shinsei.search(userInfo, searchInfo);
			//	shinseiMap = (HashMap)shinseishaPage.getList().get(0);
			//}catch(NoDataFoundException e){
			//	//�Y������f�[�^���Ȃ��ꍇ�͂��̂܂܏������I����
			//	existShinseishaInfo = false;
			//}
			//if(existShinseishaInfo 
			//	&& shinseiMap.get("SHINSEISHA_ID") != null
			//	&& !shinseiMap.get("SHINSEISHA_ID").equals("")){
			//
			//	ShinseishaInfo shinseishaInfo = new ShinseishaInfo();
			//	ShinseishaInfoDao shinseishaDao = new ShinseishaInfoDao(userInfo);
			//	shinseishaInfo.setShinseishaId((String)shinseiMap.get("SHINSEISHA_ID"));
			//	//�\���ҏ���DEL_FLG��1�ɂ���
			//	shinseishaDao.deleteFlgShinseishaInfo(connection, shinseishaInfo);
			//}
			//2005/08/09 takano �\����ID�͍폜���Ȃ��i���O�C�����Ɍ����҃}�X�^�̑��݃`�F�b�N������B�j�����܂�
			
		} catch (SQLException ex) {
			throw new DataAccessException("�����ҏ��폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	
	/**
	 * �L�[�Ɉ�v���錤���ҏ����擾����B
	 * �������̌����ҏ��������҃}�X�^����擾����B
	 * 
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @param lock					���b�N�̗L��
	 * @return						�����ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public KenkyushaInfo getKenkyushaData(Connection connection,KenkyushaPk primaryKey, boolean lock)
		throws DataAccessException, NoDataFoundException {
		
		KenkyushaInfo result = new KenkyushaInfo();
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			StringBuffer query = new StringBuffer();
			
			query.append(
				"SELECT "
				+ " KENKYU.KENKYU_NO"				//�����Ҕԍ�
				+ ", KENKYU.NAME_KANJI_SEI"			//�����i������-���j
				+ ", KENKYU.NAME_KANJI_MEI"			//�����i������-���j
				+ ", KENKYU.NAME_KANA_SEI"			//�����i�t���K�i-���j
				+ ", KENKYU.NAME_KANA_MEI"			//�����i�t���K�i-���j
				+ ", KENKYU.SEIBETSU"				//����
				+ ", KENKYU.BIRTHDAY"				//���N����
				+ ", KENKYU.GAKUI"					//�w��
				+ ", KENKYU.SHOZOKU_CD"				//�����@�փR�[�h
				+ ", KENKYU.BUKYOKU_CD"				//���ǃR�[�h
				+ ", KENKYU.SHOKUSHU_CD"			//�E�R�[�h
				+ ", KENKYU.KOSHIN_DATE"			//�X�V����
				+ ", KENKYU.BIKO"					//���l
				+ ", KIKAN.SHOZOKU_NAME_KANJI"		//�����@�֖��i�a���j
				+ ", KIKAN.SHOZOKU_NAME_EIGO"		//�����@�֖��i�p���j
				+ ", KIKAN.SHOZOKU_RYAKUSHO"		//�����@�֖��i���́j
				+ ", SHOKU.SHOKUSHU_NAME"			//�E��
				+ ", SHOKU.SHOKUSHU_NAME_RYAKU"		//�E����
//ADD Start By Nae 2006/02/27
				+ ", KENKYU.OUBO_SHIKAKU"           //���厑�i
//ADD End 				
				+ " FROM MASTER_KENKYUSHA KENKYU"					//�����҃}�X�^
					+ " INNER JOIN MASTER_KIKAN KIKAN"				//�����@�փ}�X�^
					+ " ON KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD"
					+ " INNER JOIN MASTER_SHOKUSHU SHOKU"			//�E��}�X�^
					+ " ON KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD"
				+ " WHERE KENKYU.SHOZOKU_CD = ?"
				+ " AND KENKYU.KENKYU_NO = ?"		//��L�[�F�����@�փR�[�h�A�����Ҕԍ�
				//2005/04/21�@�ǉ� ��������----------------------------------------
				//�������}�X�^�ɍ폜�t���O�ǉ��̂���
				+ " AND KENKYU.DEL_FLG = 0"				//�폜�t���O
				//�ǉ� �����܂�----------------------------------------------------
			);
			
			//�r������
			if(lock){
				query.append(" FOR UPDATE");
			}
			
			preparedStatement =
				connection.prepareStatement(query.toString());
			
			int i = 1;
			preparedStatement.setString(i++, primaryKey.getShozokuCd());
			preparedStatement.setString(i++, primaryKey.getKenkyuNo());
			
			recordSet = preparedStatement.executeQuery();
			
			if (recordSet.next()) {
				result.setKenkyuNo(recordSet.getString("KENKYU_NO"));				//�����Ҕԍ�
				result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));		//�����i������-���j
				result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));		//�����i������-���j
				result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));		//�����i�t���K�i-���j
				result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));		//�����i�t���K�i-���j
				result.setSeibetsu(recordSet.getString("SEIBETSU"));				//����
				result.setBirthday(recordSet.getDate("BIRTHDAY"));					//���N����
				result.setGakui(recordSet.getString("GAKUI"));						//�w��
				result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));				//�����@�փR�[�h
				result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));				//���ǃR�[�h
				result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));			//�E�R�[�h
				result.setKoshinDate(recordSet.getDate("KOSHIN_DATE"));				//�X�V����
				result.setBiko(recordSet.getString("BIKO"));						//���l
				result.setShozokuNameKanji(recordSet.getString("SHOZOKU_NAME_KANJI"));		//�����@�֖��i�a���j
				result.setShozokuNameEigo(recordSet.getString("SHOZOKU_NAME_EIGO"));		//�����@�֖��i�p���j
				result.setShozokuRyakusho(recordSet.getString("SHOZOKU_RYAKUSHO"));			//�����@�֖��i���́j
				result.setShokushuName(recordSet.getString("SHOKUSHU_NAME"));				//�E��
				result.setShokushuNameRyaku(recordSet.getString("SHOKUSHU_NAME_RYAKU"));	//�E������
//ADD Start By Nae 2006/02/27
				result.setOuboShikaku(recordSet.getString("OUBO_SHIKAKU"));          //���厑�i
//ADD End 				

			}else{
				throw new NoDataFoundException(
						"�����҃}�X�^�ɊY������f�[�^��������܂���B�����L�[�F'�����Ҕԍ�'"
						+ primaryKey.getKenkyuNo()
						+ " '�����@�փR�[�h'" + primaryKey.getShozokuCd()
						+ "");
			}
			
		} catch (SQLException ex) {
			throw new DataAccessException("�����҃}�X�^�������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return result;
	}
	//�ǉ� �����܂�--------------------------------------------------------------------------

	//2005/04/22 �ǉ� ��������-----------------------------------------------------------
	//�폜�t���O���܂߂����擾�̂���
	/**
	 * �L�[�Ɉ�v���錤���ҏ����擾����B
	 * �폜�t���O���܂߂������ҏ��������҃}�X�^����擾����B
	 * 
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @param lock					���b�N�̗L��
	 * @return						�����ҏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public KenkyushaInfo select(Connection connection, KenkyushaPk pk)
			throws DataAccessException, NoDataFoundException {
		
	KenkyushaInfo result = new KenkyushaInfo();
		
	PreparedStatement preparedStatement = null;
	ResultSet recordSet = null;
	try {
		StringBuffer query = new StringBuffer();
			
		query.append(
			"SELECT "
			+ " KENKYU_NO"			//�����Ҕԍ�
			+ ", NAME_KANJI_SEI"			//�����i������-���j
			+ ", NAME_KANJI_MEI"			//�����i������-���j
			+ ", NAME_KANA_SEI"				//�����i�t���K�i-���j
			+ ", NAME_KANA_MEI"				//�����i�t���K�i-���j
			+ ", SEIBETSU"					//����
			+ ", BIRTHDAY"					//���N����
			+ ", GAKUI"						//�w��
			+ ", SHOZOKU_CD"				//�����@�փR�[�h
			+ ", BUKYOKU_CD"				//���ǃR�[�h
			+ ", SHOKUSHU_CD"				//�E�R�[�h
			+ ", KOSHIN_DATE"				//�X�V����
			+ ", BIKO"						//���l
			+ ", DEL_FLG"					//�폜�t���O
			+ " FROM MASTER_KENKYUSHA KENKYU"					//�����҃}�X�^
			+ " WHERE KENKYU.SHOZOKU_CD = ?"
			+ " AND KENKYU.KENKYU_NO = ?"		//��L�[�F�����@�փR�[�h�A�����Ҕԍ�
		);
			
		preparedStatement =
			connection.prepareStatement(query.toString());
			
		int i = 1;
		preparedStatement.setString(i++, pk.getShozokuCd());
		preparedStatement.setString(i++, pk.getKenkyuNo());
			
		recordSet = preparedStatement.executeQuery();
			
		if (recordSet.next()) {
			result.setKenkyuNo(recordSet.getString("KENKYU_NO"));				//�����Ҕԍ�
			result.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));		//�����i������-���j
			result.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));		//�����i������-���j
			result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));		//�����i�t���K�i-���j
			result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));		//�����i�t���K�i-���j
			result.setSeibetsu(recordSet.getString("SEIBETSU"));				//����
			result.setBirthday(recordSet.getDate("BIRTHDAY"));					//���N����
			result.setGakui(recordSet.getString("GAKUI"));						//�w��
			result.setShozokuCd(recordSet.getString("SHOZOKU_CD"));				//�����@�փR�[�h
			result.setBukyokuCd(recordSet.getString("BUKYOKU_CD"));				//���ǃR�[�h
			result.setShokushuCd(recordSet.getString("SHOKUSHU_CD"));			//�E�R�[�h
			result.setKoshinDate(recordSet.getDate("KOSHIN_DATE"));				//�X�V����
			result.setBiko(recordSet.getString("BIKO"));						//���l
			result.setDelFlg(recordSet.getString("DEL_FLG"));					//�폜�t���O

		}else{
			throw new NoDataFoundException(
					"�����҃}�X�^�ɊY������f�[�^��������܂���B�����L�[�F'�����Ҕԍ�'"
					+ pk.getKenkyuNo()
					+ " '�����@�փR�[�h'" + pk.getShozokuCd()
					+ "");
		}
			
		} catch (SQLException ex) {
			throw new DataAccessException("�����҃}�X�^�������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		return result;
	}
	//	�ǉ� �����܂�---------------------------------------------------------------------

	/**
	 * �����Җ���_�E�����[�h�f�[�^���擾����
	 * @return List ���O�C���ҏ����@�ւ̌����Җ���f�[�^
	 */
	public List selectKenkyushaMeiboInfo(Connection connection)
	        throws DataAccessException, NoDataFoundException {

		StringBuffer query = new StringBuffer(1024);

		query.append("SELECT")
		     .append(" KENKYU.KENKYU_NO \"�����Ҕԍ�\"") 										// �����Ҕԍ�
		     .append(",KENKYU.NAME_KANJI_SEI ||'�@'|| KENKYU.NAME_KANJI_MEI \"�����i�����j\"")	// �����i�����j
		     .append(",KENKYU.NAME_KANA_SEI ||'�@'|| KENKYU.NAME_KANA_MEI \"�����i�J�i�j\"") 	// �����i�t���K�i-���j
		     .append(",KENKYU.SEIBETSU \"���ʔԍ�\"") 											// ���ʔԍ�
		     .append(",DECODE(KENKYU.SEIBETSU,'1','�j','2','��','') \"����\"")					// ����
		     .append(",KENKYU.SHOZOKU_CD \"�@�֔ԍ�\"") 										// �����@�փR�[�h
		     .append(",KIKAN.SHOZOKU_NAME_KANJI \"�@�֖�\"") 									// �����@�֖��i�a���j
		     .append(",KENKYU.BUKYOKU_CD \"���ǔԍ�\"") 										// ���ǃR�[�h
		     .append(",BUKYOKU.BUKA_NAME \"���ǖ�\"") 											// ���ǖ�
		     .append(",KENKYU.SHOKUSHU_CD \"�E�ԍ�\"") 											// �E�R�[�h
		     .append(",SHOKU.SHOKUSHU_NAME \"�E��\"") 											// �E��
		     .append(",KENKYU.GAKUI \"�w�ʔԍ�\"")												// �w�ʔԍ�
		     .append(",DECODE(KENKYU.GAKUI,'10','�C�m','11','���m','') \"�w��\"") 				// �w��
		     .append(",TO_CHAR(KENKYU.BIRTHDAY, 'YYYY/MM/DD') \"���N����\"")					// ���N����
		     .append(",KENKYU.OTHER_KIKAN1_FLG \"���̋@��1�i�Ϗ���}�[�N�j\"")
		     .append(",KENKYU.OTHER_KIKAN1_CD \"���̋@�֔ԍ�1\"")
		     .append(",KENKYU.OTHER_KIKAN1_NAME \"���̋@�֖�1\"")
		     .append(",KENKYU.OTHER_KIKAN2_FLG \"���̋@��2�i�Ϗ���}�[�N�j\"")
		     .append(",KENKYU.OTHER_KIKAN2_CD \"���̋@�֔ԍ�2\"")
		     .append(",KENKYU.OTHER_KIKAN2_NAME \"���̋@�֖�2\"")
		     .append(",KENKYU.OTHER_KIKAN3_FLG \"���̋@��3�i�Ϗ���}�[�N�j\"")
		     .append(",KENKYU.OTHER_KIKAN3_CD \"���̋@�֔ԍ�3\"")
		     .append(",KENKYU.OTHER_KIKAN3_NAME \"���̋@�֖�3\"")
		     .append(",KENKYU.OTHER_KIKAN4_FLG \"���̋@��4�i�Ϗ���}�[�N�j\"")
		     .append(",KENKYU.OTHER_KIKAN4_CD \"���̋@�֔ԍ�4\"")
		     .append(",KENKYU.OTHER_KIKAN4_NAME \"���̋@�֖�4\"")
		     .append(" FROM MASTER_KENKYUSHA KENKYU")					// �����҃}�X�^
		     .append(" INNER JOIN MASTER_KIKAN KIKAN") 					// �����@�փ}�X�^
		     .append(" ON KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD")
		     .append(" INNER JOIN MASTER_BUKYOKU BUKYOKU") 				// ���ǃ}�X�^
		     .append(" ON KENKYU.BUKYOKU_CD = BUKYOKU.BUKYOKU_CD")
		     .append(" INNER JOIN MASTER_SHOKUSHU SHOKU") 				// �E��}�X�^
		     .append(" ON KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD")
		     .append(" WHERE KENKYU.SHOZOKU_CD = '" + userInfo.getShozokuInfo().getShozokuCd() + "'")// ��L�[�F�����@�փR�[�h
		     .append(" AND KENKYU.DEL_FLG = 0")			        // �������}�X�^�ɍ폜�t���O�ǉ��̂���
		     .append(" ORDER BY KENKYU.KENKYU_NO")				// �����Ҕԍ��̏����i2007-05-25 takano�j
		     ;

		if (log.isDebugEnabled()){
			log.debug("query:" + query.toString());
		}

        //CSV���X�g�擾�i�J���������L�[���ڂƂ���j
        List csvDataList = SelectUtil.selectCsvList(connection, query.toString(), true);

        return csvDataList;

	}
}
