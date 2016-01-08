/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
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
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * �R�������f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: ShinsainInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class ShinsainInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** �R�������Ǘ��V�[�P���X�� */
	public static final String SEQ_SHINSAININFO = "SEQ_SHINSAININFO";

	/** ���O */
	protected static final Log log = LogFactory.getLog(ShinsainInfoDao.class);

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
	public ShinsainInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * �L�[�Ɉ�v����R���������擾����B
	 * @param connection			�R�l�N�V����
	 * @param pkInfo				��L�[���
	 * @return						�R�������
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public ShinsainInfo selectShinsainInfo(
			Connection connection,
			ShinsainPk pkInfo)
			throws DataAccessException, NoDataFoundException {
 
//2006/04/23 �ǉ���������
        //�\�����̎��Ƌ敪
        String jigyoKubun = pkInfo.getJigyoKubun();
        //�R�����̎��Ƌ敪��Set
        Set shinsainSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(jigyoKubun);
        //�R�����̎��Ƌ敪
        String shinsainJigyoKubun = StringUtil.changeIterator2CSV(shinsainSet.iterator(),true);
//�c�@�ǉ������܂�
        
		String query = "SELECT SI.SHINSAIN_ID SHINSAIN_ID"
			+ ", MS.SHINSAIN_NO SHINSAIN_NO"
			+ ", MS.JIGYO_KUBUN JIGYO_KUBUN"
			+ ", MS.NAME_KANJI_SEI NAME_KANJI_SEI"
			+ ", MS.NAME_KANJI_MEI NAME_KANJI_MEI"
			+ ", MS.NAME_KANA_SEI NAME_KANA_SEI"
			+ ", MS.NAME_KANA_MEI NAME_KANA_MEI"
			+ ", MS.SHOZOKU_CD SHOZOKU_CD"
			+ ", MS.SHOZOKU_NAME SHOZOKU_NAME"
//			+ ", MS.BUKYOKU_CD BUKYOKU_CD"
			+ ", MS.BUKYOKU_NAME BUKYOKU_NAME"
//			+ ", MS.SHOKUSHU_CD SHOKUSHU_CD"
			+ ", MS.SHOKUSHU_NAME SHOKUSHU_NAME"
//			+ ", MS.KEI_CD KEI_CD"
//			+ ", MS.SHINSA_KAHI SHINSA_KAHI"
			+ ", MS.SOFU_ZIP SOFU_ZIP"
			+ ", MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS"
			+ ", MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL"
//			+ ", MS.SOFU_ZIPEMAIL2 SOFU_ZIPEMAIL2"
			+ ", MS.SHOZOKU_TEL SHOZOKU_TEL"
			+ ", MS.SHOZOKU_FAX SHOZOKU_FAX"
//			+ ", MS.JITAKU_TEL JITAKU_TEL"
//			+ ", MS.SINKI_KEIZOKU_FLG SINKI_KEIZOKU_FLG"
//			+ ", MS.KIZOKU_START KIZOKU_START"
//			+ ", MS.KIZOKU_END KIZOKU_END"
//			+ ", MS.SHAKIN SHAKIN"
			+ ", MS.URL URL"
//			+ ", MS.LEVEL_A1 LEVEL_A1"
//			+ ", MS.LEVEL_B1_1 LEVEL_B1_1"
//			+ ", MS.LEVEL_B1_2 LEVEL_B1_2"
//			+ ", MS.LEVEL_B1_3 LEVEL_B1_3"
//			+ ", MS.LEVEL_B2_1 LEVEL_B2_1"
//			+ ", MS.LEVEL_B2_2 LEVEL_B2_2"
//			+ ", MS.LEVEL_B2_3 LEVEL_B2_3"
//			+ ", MS.KEY1 KEY1"
//			+ ", MS.KEY2 KEY2"
//			+ ", MS.KEY3 KEY3"
//			+ ", MS.KEY4 KEY4"
//			+ ", MS.KEY5 KEY5"
//			+ ", MS.KEY6 KEY6"
//			+ ", MS.KEY7 KEY7"
			+ ", MS.SENMON SENMON"
			+ ", MS.KOSHIN_DATE KOSHIN_DATE"
			+ ", MS.BIKO BIKO"
			
			+ ", SI.PASSWORD PASSWORD"
			+ ", SI.YUKO_DATE YUKO_DATE"
			+ ", SI.LOGIN_DATE"				//2005/10/20 �ŏI���O�C�����ǉ�
			+ ", SI.MAIL_FLG"				//2005/10/26 ���[���t���O�ǉ�
			+ ", SI.DEL_FLG DEL_FLG"
//			2006/10/24 �Ո� �ǉ���������
			+ ", SI.DOWNLOAD_FLG"
//			2006/10/24 �Ո� �ǉ������܂� 
			+ " FROM MASTER_SHINSAIN MS"
			+ ", SHINSAININFO SI"
			+ " WHERE SI.DEL_FLG = 0"
			+ " AND MS.SHINSAIN_NO = ?"
//			+ " AND MS.JIGYO_KUBUN = ?"
//2006/04/23 �ǉ���������
            + " AND MS.JIGYO_KUBUN IN ("
            + shinsainJigyoKubun
            + ")"
//�c�@�ǉ������܂�    
			+ " AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)"
			+ " AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)"; 

		PreparedStatement ps = null;
		ResultSet rset = null;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		try {
			ShinsainInfo result = new ShinsainInfo();
			ps = connection.prepareStatement(query);
			int i = 1;
			ps.setString(i++, pkInfo.getShinsainNo());
//			ps.setString(i++, pkInfo.getJigyoKubun());
//2006/04/23 �c�@�폜��������            
//			if (IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(pkInfo.getJigyoKubun())){
//				ps.setString(i++, IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
//			}else{
//				ps.setString(i++, IJigyoKubun.JIGYO_KUBUN_KIBAN);
//			}
//�폜�����܂�       
            
			rset = ps.executeQuery();

			if (rset.next()) {
				result.setShinsainId(rset.getString("SHINSAIN_ID"));
				result.setShinsainNo(rset.getString("SHINSAIN_NO"));
				result.setJigyoKubun(rset.getString("JIGYO_KUBUN"));
				result.setNameKanjiSei(rset.getString("NAME_KANJI_SEI"));
				result.setNameKanjiMei(rset.getString("NAME_KANJI_MEI"));
				result.setNameKanaSei(rset.getString("NAME_KANA_SEI"));
				result.setNameKanaMei(rset.getString("NAME_KANA_MEI"));
				result.setShozokuCd(rset.getString("SHOZOKU_CD"));
				result.setShozokuName(rset.getString("SHOZOKU_NAME"));
//				result.setBukyokuCd(rset.getString("BUKYOKU_CD"));
				result.setBukyokuName(rset.getString("BUKYOKU_NAME"));
//				result.setShokushuCd(rset.getString("SHOKUSHU_CD"));
				result.setShokushuName(rset.getString("SHOKUSHU_NAME"));
//				result.setKeiCd(rset.getString("KEI_CD"));
//				result.setShinsaKahi(rset.getString("SHINSA_KAHI"));
				result.setSofuZip(rset.getString("SOFU_ZIP"));
				result.setSofuZipaddress(rset.getString("SOFU_ZIPADDRESS"));
				result.setSofuZipemail(rset.getString("SOFU_ZIPEMAIL"));
//				result.setSofuZipemail2(rset.getString("SOFU_ZIPEMAIL2"));
				result.setShozokuTel(rset.getString("SHOZOKU_TEL"));
				result.setShozokuFax(rset.getString("SHOZOKU_FAX"));
//				result.setJitakuTel(rset.getString("JITAKU_TEL"));
//				result.setSinkiKeizokuFlg(rset.getString("SINKI_KEIZOKU_FLG"));
//				result.setKizokuStart(rset.getDate("KIZOKU_START"));
//				result.setKizokuEnd(rset.getDate("KIZOKU_END"));
//				result.setShakin(rset.getString("SHAKIN"));
				result.setUrl(rset.getString("URL"));
//				result.setLevelA1(rset.getString("LEVEL_A1"));
//				result.setLevelB11(rset.getString("LEVEL_B1_1"));
//				result.setLevelB12(rset.getString("LEVEL_B1_2"));
//				result.setLevelB13(rset.getString("LEVEL_B1_3"));
//				result.setLevelB21(rset.getString("LEVEL_B2_1"));
//				result.setLevelB22(rset.getString("LEVEL_B2_2"));
//				result.setLevelB23(rset.getString("LEVEL_B2_3"));
//				result.setKey1(rset.getString("KEY1"));
//				result.setKey2(rset.getString("KEY2"));
//				result.setKey3(rset.getString("KEY3"));
//				result.setKey4(rset.getString("KEY4"));
//				result.setKey5(rset.getString("KEY5"));
//				result.setKey6(rset.getString("KEY6"));
//				result.setKey7(rset.getString("KEY7"));
				result.setSenmon(rset.getString("SENMON"));
				result.setKoshinDate(rset.getDate("KOSHIN_DATE"));
				result.setBiko(rset.getString("BIKO"));
				result.setPassword(rset.getString("PASSWORD"));
				result.setYukoDate(rset.getDate("YUKO_DATE"));
				result.setLoginDate(rset.getDate("LOGIN_DATE"));
				result.setMailFlg(rset.getString("MAIL_FLG"));
				result.setDelFlg(rset.getString("DEL_FLG"));
//				2006/10/24 �Ո� �ǉ���������
				result.setDownloadFlag(rset.getString("DOWNLOAD_FLG"));
//				2006/10/24 �Ո� �ǉ������܂� 
				return result;
			} else {
				throw new NoDataFoundException(
					"�R�������e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�R�����ԍ��E���Ƌ敪'"
						+ pkInfo.getShinsainNo()
						+ "���Ƌ敪"
						+ pkInfo.getJigyoKubun()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�R�������e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(rset, ps);
		}
	}
	
	

	/**
	 * �R��������o�^����B
	 * �R�����}�X�^�e�[�u���ƐR�������e�[�u���ɓo�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����R�������
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertShinsainInfo(Connection connection, ShinsainInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
		insertShinsainInfo(connection, addInfo, true);
	}
	
	/**
	 * �R��������o�^����B
	 * �R�����}�X�^�e�[�u���݂̂ɓo�^����B�i�R�������e�[�u���ɂ͓o�^���Ȃ��B�j
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����R�������
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertShinsainInfoOnlyMaster(Connection connection, ShinsainInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
		insertShinsainInfo(connection, addInfo, false);
	}
	
	/**
	 * �R��������o�^����B
	 * ��O������true�̏ꍇ�A�R�����}�X�^�ƐR�������e�[�u���̗����ɓo�^����B
	 * ��O������false�̏ꍇ�A�R�����}�X�^�e�[�u���݂̂ɓo�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����R�������
	 * @param bothTable                true�F�R�����}�X�^�e�[�u���ƐR�������e�[�u���ɓo�^����B
	 * 									false�F�R�����}�X�^�e�[�u���̂ݓo�^����B
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	private void insertShinsainInfo(Connection connection, 
									  ShinsainInfo addInfo, 
									  boolean bothTable)
		throws DataAccessException, DuplicateKeyException
	{
		//�d���`�F�b�N
		try {
			selectShinsainInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}

		String query1 =
			"INSERT INTO MASTER_SHINSAIN"
				+ " (SHINSAIN_NO"
				+ ", JIGYO_KUBUN"
				+ ", NAME_KANJI_SEI"
				+ ", NAME_KANJI_MEI"
				+ ", NAME_KANA_SEI"
				+ ", NAME_KANA_MEI"
				+ ", SHOZOKU_CD"
				+ ", SHOZOKU_NAME"
				+ ", BUKYOKU_NAME"
				+ ", SHOKUSHU_NAME"
				+ ", SOFU_ZIP"
				+ ", SOFU_ZIPADDRESS"
				+ ", SOFU_ZIPEMAIL"
				+ ", SHOZOKU_TEL"
				+ ", SHOZOKU_FAX"
				+ ", URL"
				+ ", SENMON"
				+ ", KOSHIN_DATE"
				+ ", BIKO)"
				+ " VALUES"
				+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String query2 =
			"INSERT INTO SHINSAININFO"
				+ " (SHINSAIN_ID"
				+ ", PASSWORD"
				+ ", YUKO_DATE"
				+ ", LOGIN_DATE"			//2005/10/20�@�ŏI���O�C�����ǉ�
				+ ", MAIL_FLG"				//2005/10/26  ���[���t���O�ǉ�
				+ ", DEL_FLG)"
				+ " VALUES"
				+ " (?,?,?,?,?,?)";

		PreparedStatement preparedStatement  = null;
		PreparedStatement preparedStatement2 = null;
		try {
			//�R�����}�X�^�o�^
			preparedStatement = connection.prepareStatement(query1);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSofuZip());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSofuZipaddress());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSofuZipemail());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuTel());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuFax());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUrl());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSenmon());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKoshinDate());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.executeUpdate(preparedStatement);

			//�t���O��true�̏ꍇ�A�R�������e�[�u���ɂ��o�^����
			if(bothTable){
				i = 1;
				//�R�������e�[�u���o�^
				preparedStatement2 = connection.prepareStatement(query2);
				DatabaseUtil.setParameter(preparedStatement2,i++,addInfo.getShinsainId());
				DatabaseUtil.setParameter(preparedStatement2,i++,addInfo.getPassword());
				DatabaseUtil.setParameter(preparedStatement2,i++,addInfo.getYukoDate());
				DatabaseUtil.setParameter(preparedStatement2,i++,addInfo.getLoginDate());
				DatabaseUtil.setParameter(preparedStatement2,i++,addInfo.getMailFlg());
				DatabaseUtil.setParameter(preparedStatement2,i++, 0);
				DatabaseUtil.executeUpdate(preparedStatement2);
			}
		
		} catch (SQLException ex) {
			throw new DataAccessException("�R�������o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
			DatabaseUtil.closeResource(null, preparedStatement2);
		}
	}
	
	
	
	/**
	 * �R���������X�V����B
	 * @param connection			�R�l�N�V����
	 * @param updateInfo			�X�V����R�������
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateShinsainInfo(
			Connection connection,
			ShinsainInfo updateInfo)
			throws DataAccessException, NoDataFoundException {
		
		//����
		selectShinsainInfo(connection, updateInfo);

//2006/05/22�@�ǉ���������        
        Set shinsaiSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(updateInfo.getJigyoKubun()) ; //�R�����̎��Ƌ敪��Set
        String jigyoKubun= (String)shinsaiSet.iterator().next();
//�c�@�ǉ������܂�        

		//�R�����}�X�^TBL�X�VSQL
		String query1 = "UPDATE MASTER_SHINSAIN"
				+ " SET"
				+ " NAME_KANJI_SEI = ?"
				+ ", NAME_KANJI_MEI = ?"
				+ ", NAME_KANA_SEI = ?"
				+ ", NAME_KANA_MEI = ?"
				+ ", SHOZOKU_CD = ?"
				+ ", SHOZOKU_NAME = ?"
//				+ ", BUKYOKU_CD = ?"
				+ ", BUKYOKU_NAME = ?"
//				+ ", SHOKUSHU_CD = ?"
				+ ", SHOKUSHU_NAME = ?"
//				+ ", KEI_CD = ?"
//				+ ", SHINSA_KAHI = ?"
				+ ", SOFU_ZIP = ?"
				+ ", SOFU_ZIPADDRESS = ?"
				+ ", SOFU_ZIPEMAIL = ?"
				+ ", SHOZOKU_TEL = ?"
				+ ", SHOZOKU_FAX = ?"
//				+ ", JITAKU_TEL = ?"
//				+ ", SINKI_KEIZOKU_FLG = ?"
//				+ ", KIZOKU_START = ?"
//				+ ", KIZOKU_END = ?"
//				+ ", SHAKIN = ?"
				+ ", URL = ?"
//				+ ", LEVEL_A1 = ?"
//				+ ", LEVEL_B1_1 = ?"
//				+ ", LEVEL_B1_2 = ?"
//				+ ", LEVEL_B1_3 = ?"
//				+ ", LEVEL_B2_1 = ?"
//				+ ", LEVEL_B2_2 = ?"
//				+ ", LEVEL_B2_3 = ?"
//				+ ", KEY1 = ?"
//				+ ", KEY2 = ?"
//				+ ", KEY3 = ?"
//				+ ", KEY4 = ?"
//				+ ", KEY5 = ?"
//				+ ", KEY6 = ?"
//				+ ", KEY7 = ?"
				+ ", SENMON = ?"
				+ ", KOSHIN_DATE = ?"
				+ ", BIKO = ?"
				+ " WHERE"
//2006/05/22�@�ǉ���������
//                + " JIGYO_KUBUN = ? "
				+ " JIGYO_KUBUN IN("
                + jigyoKubun
                + ")"
//�c�@�ǉ������܂�                
				+ " AND SHINSAIN_NO = ?";

		//�R�������TBL�X�VSQL
		String query2 = "UPDATE SHINSAININFO"
				+ " SET"
				+ " PASSWORD = ?"
				+ " ,YUKO_DATE = ?"
				+ " ,LOGIN_DATE = ?"		//2005/10/20 �ŏI���O�C�����ǉ�
				+ " ,MAIL_FLG = ?"			//2005/10/26 ���[���t���O�ǉ�
				+ " ,DOWNLOAD_FLG = ?"		//2006/10/24 �����v�撲���_�E�����[�h  �Ո� �ǉ�
				+ " WHERE"
				+ " SHINSAIN_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�R�����}�X�^�X�V
			preparedStatement = connection.prepareStatement(query1);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());
//				DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKeiCd());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsaKahi());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSofuZip());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSofuZipaddress());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSofuZipemail());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuTel());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuFax());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJitakuTel());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSinkiKeizokuFlg());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKizokuStart());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKizokuEnd());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShakin());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUrl());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelA1());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB11());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB12());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB13());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB21());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB22());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLevelB23());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey1());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey2());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey3());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey4());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey5());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey6());
//			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKey7());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSenmon());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKoshinDate());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());
//2006/05/22 �폜��������            
//		�@�@�@ DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
//�c�@�폜�����܂�            
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
			DatabaseUtil.executeUpdate(preparedStatement);

			//������
			preparedStatement = null;
			i = 1;

			//�R�����}�X�^�X�V
			preparedStatement = connection.prepareStatement(query2);

			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPassword());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());	
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getLoginDate());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getMailFlg());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDownloadFlag());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainId());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("�R�������X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * �R���������폜����B(�폜�t���O) 
	 * @param connection			�R�l�N�V����
	 * @param deleteInfo			�폜����R������L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteFlgShinsainInfo(
			Connection connection,
			ShinsainPk deleteInfo)
			throws DataAccessException, NoDataFoundException {
			
		//����
		ShinsainInfo shinsainInfo = selectShinsainInfo(connection, deleteInfo);

		String query = "UPDATE SHINSAININFO"
				+ " SET"
				+ " DEL_FLG = 1"
				+ " WHERE"
				+ " SHINSAIN_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�X�V(�t���O�ɂ��폜)
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainInfo.getShinsainId());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("�R�������폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * �R���������폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param deleteInfo				�폜����R������L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteShinsainInfo(
			Connection connection,
			ShinsainPk deleteInfo)
			throws DataAccessException, NoDataFoundException {
			
		//����
		ShinsainInfo shinsainInfo = selectShinsainInfo(connection, deleteInfo);

		//�R�������e�[�u���̍폜
		String query1 = "DELETE FROM SHINSAININFO"
				+ " WHERE"
				+ " SHINSAIN_ID = ?";

		//�R�����}�X�^�̍폜
		String query2 = "DELETE FROM MASTER_SHINSAIN"
				+ " WHERE"
				+ " SHINSAIN_NO = ?"
				+ " AND JIGYO_KUBUN = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�R�������e�[�u���̍폜
			preparedStatement = connection.prepareStatement(query1);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainInfo.getShinsainId());
			DatabaseUtil.executeUpdate(preparedStatement);

			//������
			preparedStatement = null;
			i = 1;
			//�R�����}�X�^�̍폜
			preparedStatement = connection.prepareStatement(query2);
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainInfo.getJigyoKubun());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("�R�������폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * ���[�UID�A�p�X���[�h�̔F�؂��s���B
	 * @param connection			�R�l�N�V����
	 * @param userid				���[�UID
	 * @param password				�p�X���[�h
	 * @return						�F�؂ɐ��������ꍇ true �ȊO false
	 * @throws DataAccessException	�f�[�^�x�[�X�A�N�Z�X���̗�O
	 */
	public boolean authenticateShinsainInfo(
		Connection connection,
		String userid,
		String password)
		throws DataAccessException {

			String query = "SELECT count(*) "
						 + "FROM SHINSAININFO "
						 + "WHERE DEL_FLG = 0 "
						 + "AND SHINSAIN_ID = ? "
						 + "AND PASSWORD = ?";

			PreparedStatement ps = null;
			ResultSet rset = null;
			int count = 0;

			try {
				ps = connection.prepareStatement(query);
				int i = 1;
				ps.setString(i++, userid);
				ps.setString(i++, password);
				rset = ps.executeQuery();

				if (rset.next()) {
					count = rset.getInt(1);
				}

				//��v����f�[�^�����݂���ꍇ�́Atrue�Ԃ�
				if (count > 0) {
					return true;
				} else {
					return false;
				}

			} catch (SQLException ex) {
				throw new DataAccessException("�R�������e�[�u���������s���ɗ�O���������܂����B ", ex);
			} finally {
				DatabaseUtil.closeResource(rset, ps);
			}
	}
	
	
	
	/**
	 * �p�X���[�h��ύX����B 
	 * @param connection
	 * @param pkInfo				��L�[���
	 * @param newPassword			�V�����p�X���[�h
	 * @return              		�p�X���[�h�̕ύX�ɐ��������ꍇ true �ȊO false
	 * @throws DataAccessException	�ύX���ɗ�O�����������ꍇ
	 */
	public boolean changePasswordShinsainInfo(
		Connection connection,
		ShinsainPk pkInfo,
		String newPassword)
		throws DataAccessException {

			String query = "UPDATE SHINSAININFO"
						 + " SET"
						 + " PASSWORD = ?"
						 + " WHERE"
						 + " SUBSTR(SHINSAIN_ID,3,8) = ?"						//���Ƌ敪+�R�����ԍ�(7��)
						 + " AND DEL_FLG = 0";									//�폜�t���O

			PreparedStatement ps = null;
			
			if(log.isDebugEnabled()){
				log.debug("query:" + query);
			}
		
			try {
				//�o�^
				ps = connection.prepareStatement(query);
				int i = 1;
				DatabaseUtil.setParameter(ps,i++, newPassword);										//�V�����p�X���[�h
				DatabaseUtil.setParameter(ps,i++, pkInfo.getJigyoKubun()+pkInfo.getShinsainNo());	//�R�����ԍ�
				DatabaseUtil.executeUpdate(ps);
			} catch (SQLException ex) {
				throw new DataAccessException("�p�X���[�h�ύX���ɗ�O���������܂����B ", ex);

			} finally {
				DatabaseUtil.closeResource(null, ps);
			}
			return true;
	}
	
	
	
	/**
	 * �R�������̐����擾����B�폜�t���O���u0�v�̏ꍇ�̂݌�������B
	 * @param connection		�R�l�N�V����
	 * @param searchInfo		�R�������
	 * @return					�R������
	 */
	public int countShinsainInfo(Connection connection, ShinsainInfo searchInfo)
			throws DataAccessException {

		String query = "SELECT COUNT(*)"
				+ " FROM MASTER_SHINSAIN MS"
				+ ", SHINSAININFO SI"
				+ " WHERE MS.SHINSAIN_NO = ?"
				+ " AND MS.JIGYO_KUBUN = ?"
				+ " AND SI.DEL_FLG = 0"				//�폜�t���O
				+ " AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)"
				+ " AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)";

		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;

		try {
			ShinsainInfo result = new ShinsainInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,searchInfo.getJigyoKubun());
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("�R�������e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	
	/**
	 * �R�������e�[�u���Ɏw�肵���R�����ԍ��{�敪�̃��R�[�h�����邩�𔻒f����B
	 * �폜�t���O���u1�i�폜�ς݁v�̃f�[�^���ΏۂƂȂ�B
	 * @param	connection			�R�l�N�V����
	 * @param�@shinsainNo			�R�����ԍ�
	 * @param  kubun               �敪
	 * @return						���R�[�h�L���iboolean�j
	 * @throws ApplicationException
	 */
	public int checkShinsainInfo(Connection connection, 
								  String     shinsainNo,
								  String     kubun)
		throws DataAccessException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select = "SELECT COUNT(*) FROM SHINSAININFO "
					  + " WHERE"
					  + "  SUBSTR(SHINSAIN_ID,4,7) = ?"		//�R�����ԍ�(7��)
					  + " AND"
					  + "  SUBSTR(SHINSAIN_ID,3,1) = ?"		//���Ƌ敪(1��)
					  ;

		PreparedStatement preparedStatement = null;
		ResultSet         rset              = null;
		try {
			//�擾
			preparedStatement = connection.prepareStatement(select);
			int i=1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainNo);			
			DatabaseUtil.setParameter(preparedStatement,i++,kubun);
			rset = preparedStatement.executeQuery();
			int count = 0;
			if (rset.next()) {
				count = rset.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("�R������񃌃R�[�h�������ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(rset, preparedStatement);
		}
	}	
	
	
	
	/**
	 * �R�����}�X�^���i�ꕔ�j�̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						�R�������i�ꕔ�j
	 * @throws ApplicationException
	 */
	public static List selectShinsainInfoList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " SHINSAIN_NO"
			+ ",JIGYO_KUBUN"
			+ ",NAME_KANJI_SEI"
			+ ",NAME_KANJI_MEI"
			+ ",NAME_KANA_SEI"
			+ ",NAME_KANA_MEI"
			+ ",SHOZOKU_CD"
			+ ",SHOZOKU_NAME"
			+ ",BUKYOKU_NAME"
			+ ",SHOKUSHU_NAME"
			+ " FROM MASTER_SHINSAIN A"
			+ " ORDER BY A.SHINSAIN_NO";								
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R�����}�X�^��񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�R�����}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}	
	
	
	
	/**
	 * ���Y���Ƌ敪�̐R�����}�X�^���i�ꕔ�j�̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @param  jigyoKubun          ���Ƌ敪
	 * @return						�R�������i�ꕔ�j
	 * @throws ApplicationException
	 */
	public static List selectShinsainInfoList(Connection connection, String jigyoKubun)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " SHINSAIN_NO"
			+ ",JIGYO_KUBUN"
			+ ",NAME_KANJI_SEI"
			+ ",NAME_KANJI_MEI"
			+ ",NAME_KANA_SEI"
			+ ",NAME_KANA_MEI"
			+ ",SHOZOKU_CD"
			+ ",SHOZOKU_NAME"
			+ ",BUKYOKU_NAME"
			+ ",SHOKUSHU_NAME"
			+ " FROM MASTER_SHINSAIN A"
			+ " WHERE A.JIGYO_KUBUN = ?"
			+ " ORDER BY A.SHINSAIN_NO";								
			StringBuffer query = new StringBuffer(select);

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString(), new String[]{jigyoKubun});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R�����}�X�^��񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		}
	}	
	
	
	
	/**
	 * �R���˗����pCSV�f�[�^��DB���擾����B�i�R�����Ǘ��p�j
	 * @param connection
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public List createCSV4Iraisho(Connection connection, ShinsainSearchInfo searchInfo)
		throws ApplicationException
	{
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		String select = "SELECT " 
						+ "to_char(substr(MS.shinsain_no,1,4), '9990') || "				//�R�����ԍ�
						+ 	"substr(MS.shinsain_no,5,1) || '�|' || "					//���T���i�ŏ��̂R���̃[���͏ȗ��j��
						+ 		"substr(MS.shinsain_no,6,2)		\"�R�����ԍ�\""			//��Q�����n�C�t���Ō���
						+ ",MS.NAME_KANJI_SEI                   \"�R�������i����-���j\""	//�R�������i����-���j
						+ ",MS.NAME_KANJI_MEI                   \"�R�������i����-���j\""	//�R�������i����-���j
						+ ",MS.SHOZOKU_NAME                     \"�R���������@�֖�\""	//�R���������@�֖�
						+ ",MS.BUKYOKU_NAME                     \"�R�������ǖ�\""		//�R�������ǖ�
						+ ",MS.SHOKUSHU_NAME                    \"�R�����E��\""			//�R�����E��
						+ ",MS.SOFU_ZIP                         \"���t��i�X�֔ԍ��j\""	//���t��i�X�֔ԍ��j
						+ ",MS.SOFU_ZIPADDRESS                  \"���t��i�Z���j\""		//���t��i�Z���j
						+ ",MS.SOFU_ZIPEMAIL                    \"���t��iEmail�j\""	//���t��iEmail�j
						+ ",MS.SHOZOKU_TEL                      \"�d�b�ԍ�\""			//�d�b�ԍ�
						+ ",MS.SHOZOKU_FAX                      \"FAX�ԍ�\""			//FAX�ԍ�
						+ ",MS.BIKO                             \"���l\""				//���l
						+ ",SI.SHINSAIN_ID                      \"�R����ID\""			//�R����ID
						+ ",SI.PASSWORD                         \"�p�X���[�h\""			//�p�X���[�h
						+ ",TO_CHAR(SI.YUKO_DATE,'YYYY/MM/DD')  \"�L������\""			//�L������
						+ " FROM"
						+ "  MASTER_SHINSAIN MS, SHINSAININFO SI"
						+ " WHERE"
						+ "  SI.DEL_FLG = 0"
						;

		StringBuffer query = new StringBuffer(select);
		
		//�R�����ԍ�
		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){			
			query.append(" AND MS.SHINSAIN_NO = '" + EscapeUtil.toSqlString(searchInfo.getShinsainNo()) + "'");
		}
		//�\���Ҏ����i����-���j
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){
			query.append(" AND MS.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		//�\���Ҏ����i����-���j
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){				
			query.append(" AND MS.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		//�����@�փR�[�h
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){					
			query.append(" AND MS.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		//�זڔԍ�		
		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){			//�זڔԍ�
			query.append(" AND (")
				.append("((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))")
				.append(" OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'))")
			.append(")");
		}
		//���Ƌ敪		
		if(searchInfo.getJigyoKubun() != null && !searchInfo.getJigyoKubun().equals("")){			
			query.append(" AND MS.JIGYO_KUBUN = '" + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "'");
		}

		//���������{�\�[�g�����i�R�����ԍ����j
		query.append(" AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)")	//7��
			 .append(" AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)")
			 .append(" ORDER BY MS.SHINSAIN_NO");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----DB���R�[�h�擾-----
		try{
			return SelectUtil.selectCsvList(connection, query.toString(), true);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV�o�̓f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4008"),
				e);
		}
		
	}
	
	
	/**
	 * �R���������X�V����B�i���[���t���O�̂݁j 
	 * @param connection			�R�l�N�V����
	 * @param updateInfo			�X�V����R�������
	 * @throws DataAccessException	�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateMailFlgShinsainInfo(
			Connection connection,
			ShinsainInfo updateInfo)
			throws DataAccessException, NoDataFoundException {
			
		//����
		ShinsainInfo shinsainInfo = selectShinsainInfo(connection, updateInfo);

		String query = "UPDATE SHINSAININFO"
				+ " SET"
				+ " MAIL_FLG = ?"
				+ " WHERE"
				+ " SHINSAIN_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�X�V(���[���t���O�̂�)
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getMailFlg());
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainInfo.getShinsainId());
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("�R�������X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	
	
	
	
}
