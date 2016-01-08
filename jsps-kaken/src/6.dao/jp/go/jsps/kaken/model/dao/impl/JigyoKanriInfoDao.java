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
import java.util.Date;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * ���ƊǗ����f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: JigyoKanriInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class JigyoKanriInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(JigyoKanriInfoDao.class);

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
	public JigyoKanriInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * �R���X�g���N�^�B
	 * @param userInfo	���s���郆�[�U���
	 * @param dbLink   DB�����N��
	 */
	public JigyoKanriInfoDao(UserInfo userInfo, String dbLink) {
		this.userInfo = userInfo;
		this.dbLink   = dbLink;
	}
	
	/**
	 * SEQ�e�[�u�����쐬����B
	 * @param connection			�R�l�N�V����
	 * @param jigyoId				����ID
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public void createSEQ(
		Connection connection,
		String jigyoId)
		throws DataAccessException {
		
		String query =
			"CREATE SEQUENCE SEQ_" + EscapeUtil.toSqlString(jigyoId)
			+ " INCREMENT BY 1"
			+ " START WITH 1"
			+ " MAXVALUE 999999"
			+ " NOMINVALUE"
			+ " CYCLE" 
			+ " CACHE 20"
			+ " NOORDER";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			recordSet = preparedStatement.executeQuery();
		} catch (SQLException ex) {
			throw new DataAccessException("SEQ�e�[�u���쐬���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * ���ƊǗ������擾����B�폜�t���O���u0�v�̏ꍇ�̂݌�������B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						���ƊǗ����
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public JigyoKanriInfo selectJigyoKanriInfo(
		Connection connection,
		JigyoKanriPk primaryKeys)
		throws DataAccessException, NoDataFoundException {
		String query =
			"SELECT "
				+ " A.JIGYO_ID"				//����ID
				+ ",A.NENDO"				//�N�x
				+ ",A.KAISU"				//��
				+ ",A.JIGYO_NAME"			//���Ɩ�
				+ ",A.JIGYO_KUBUN"			//���Ƌ敪
				+ ",A.SHINSA_KUBUN"			//�R���敪
				+ ",A.TANTOKA_NAME"			//�Ɩ��S����
				+ ",A.TANTOKAKARI"			//�Ɩ��S���W��
				+ ",A.TOIAWASE_NAME"		//�₢���킹��S���Җ�
				+ ",A.TOIAWASE_TEL"			//�₢���킹��d�b�ԍ�
				+ ",A.TOIAWASE_EMAIL"		//�₢���킹��E-mail
				+ ",A.UKETUKEKIKAN_START"	//�w�U��t���ԁi�J�n�j
				+ ",A.UKETUKEKIKAN_END"		//�w�U��t���ԁi�I���j
				//2005/04/25 �ǉ� ��������----------------------------------------------------
				//���R URL�̒ǉ��̂���
				+ ",A.URL_TITLE"			//URL(�^�C�g��)
				+ ",A.URL_ADDRESS"			//URL(�A�h���X)
				+ ",A.DL_URL"				//�_�E�����[�hURL
				//�ǉ� �����܂�---------------------------------------------------------------
				+ ",A.SHINSAKIGEN"			//�R������
				+ ",A.TENPU_NAME"			//�Y�t������
				+ ",A.TENPU_WIN"			//�Y�t�t�@�C���i�[�t�H���_�iWin�j
				+ ",A.TENPU_MAC"			//�Y�t�t�@�C���i�[�t�H���_�iMac�j
//2007/02/03 �c�@�ǉ���������
                + ",A.PAGE_FROM"            //������e�t�@�C���y�[�W��(����)
                + ",A.PAGE_TO"              //������e�t�@�C���y�[�W��(���)
//2007/02/03�@�c�@�ǉ������܂�                
				+ ",A.HYOKA_FILE_FLG"		//�]���p�t�@�C���L��
				+ ",A.HYOKA_FILE"			//�]���p�t�@�C���i�[�t�H���_
				//2006/02/08 �ǉ��@�c�@��������----------------------------------------------------
                //���R�@�����Җ�����ؓ��̒ǉ��̂���
				+ ",A.MEIBO_DATE"           //�����Җ�����ؓ�
                //�ǉ� �����܂�---------------------------------------------------------------
//				2006/06/14 �ǉ��@���m�@��������----------------------------------------------------
                //���R�@���̈�ԍ����s���ؓ��̒ǉ��̂���
				+ ",A.KARIRYOIKINO_UKETUKEKIKAN_END "           //���̈�ԍ����s���ؓ�
                //�ǉ� �����܂�---------------------------------------------------------------
//              2006/07/10 �ǉ��@���`�؁@��������----------------------------------------------------
                //���R�@�̈��\�Ҋm����ؓ��̒ǉ��̂���
                + ",A.RYOIKI_KAKUTEIKIKAN_END�@ "                //�̈��\�Ҋm����ؓ�                
                //�ǉ� �����܂�---------------------------------------------------------------                
				+ ",A.KOKAI_FLG"			//���J�t���O
				+ ",A.KESSAI_NO"			//���J���ٔԍ�
				+ ",A.KOKAI_ID"				//���J�m���ID
				+ ",A.HOKAN_DATE"			//�f�[�^�ۊǓ�
				+ ",A.YUKO_DATE"			//�ۊǗL������
				+ ",A.BIKO"					//���l
				+ ",A.DEL_FLG"				//�폜�t���O
//				 2006/10/24 �Ո� �ǉ� ��������
				+ ",A.RIGAI_KIKAN_END"		//���Q�֌W���͒��ؓ�
//				 2006/10/24 �Ո� �ǉ� �����܂�
				+ " FROM JIGYOKANRI"+dbLink+" A"
				+ " WHERE JIGYO_ID = ?"
//2004/10/26update�@�V�X�e���Ǘ��Ҍ����\����񌟍��ւ̑Ή�
//				+ " AND DEL_FLG = 0"//�폜�t���O
				;
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			JigyoKanriInfo result = new JigyoKanriInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, primaryKeys.getJigyoId());//����ID
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setJigyoId(recordSet.getString("JIGYO_ID"));							//����ID
				result.setNendo(recordSet.getString("NENDO"));								//�N�x
				result.setKaisu(recordSet.getString("KAISU"));								//��
				result.setJigyoName(recordSet.getString("JIGYO_NAME"));						//���Ɩ�
				result.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));					//���Ƌ敪
				result.setShinsaKubun(recordSet.getString("SHINSA_KUBUN"));					//�R���敪
				result.setTantokaName(recordSet.getString("TANTOKA_NAME"));					//�Ɩ��S����
				result.setTantoKakari(recordSet.getString("TANTOKAKARI"));					//�Ɩ��S���W��
				result.setToiawaseName(recordSet.getString("TOIAWASE_NAME"));				//�₢���킹��S���Җ�
				result.setToiawaseTel(recordSet.getString("TOIAWASE_TEL"));					//�₢���킹��d�b�ԍ�
				result.setToiawaseEmail(recordSet.getString("TOIAWASE_EMAIL"));				//�₢���킹��E-mail
				result.setUketukekikanStart(recordSet.getDate("UKETUKEKIKAN_START"));		//�w�U��t���ԁi�J�n�j
				result.setUketukekikanEnd(recordSet.getDate("UKETUKEKIKAN_END"));			//�w�U��t���ԁi�I���j
				result.setUrlTitle(recordSet.getString("URL_TITLE"));						//URL�^�C�g��
				result.setUrlAddress(recordSet.getString("URL_ADDRESS"));					//URL�A�h���X
				result.setDlUrl(recordSet.getString("DL_URL"));								//�_�E�����[�hURL
				result.setShinsaKigen(recordSet.getDate("SHINSAKIGEN"));					//�R������
				result.setTenpuName(recordSet.getString("TENPU_NAME"));						//�Y�t������
				result.setTenpuWin(recordSet.getString("TENPU_WIN"));						//�Y�t�t�@�C���i�[�t�H���_�iWin�j
				result.setTenpuMac(recordSet.getString("TENPU_MAC"));						//�Y�t�t�@�C���i�[�t�H���_�iMac�j
//2007/02/03 �c�@�ǉ���������
                result.setPageFrom(recordSet.getString("PAGE_FROM"));                          //������e�t�@�C���y�[�W��(����)
                result.setPageTo(recordSet.getString("PAGE_TO"));                              //������e�t�@�C���y�[�W��(���)
//2007/02/03�@�c�@�ǉ������܂�                
				result.setHyokaFileFlg(recordSet.getString("HYOKA_FILE_FLG"));				//�]���p�t�@�C���L��
				result.setHyokaFile(recordSet.getString("HYOKA_FILE"));						//�]���p�t�@�C���i�[�t�H���_
//2006/02/08 �ǉ��@�c�@��������----------------------------------------------------
//���R�@�����Җ�����ؓ��̒ǉ��̂���
				result.setMeiboDate(recordSet.getDate("MEIBO_DATE"));                       //�����Җ�����ؓ�
//�ǉ� �����܂�---------------------------------------------------------------
//				2006/02/08 �ǉ��@���m�@��������----------------------------------------------------
//				���R�@���̈�ԍ����s���ؓ��̒ǉ��̂���
				result.setKariryoikiNoEndDate(recordSet.getDate("KARIRYOIKINO_UKETUKEKIKAN_END"));    //���̈�ԍ����s���ؓ�
//				�ǉ� �����܂�---------------------------------------------------------------
//              2006/07/10 �ǉ��@���`�؁@��������----------------------------------------------------
//              ���R�@�̈��\�Ҋm����ؓ��̒ǉ��̂���
                result.setRyoikiEndDate(recordSet.getDate("RYOIKI_KAKUTEIKIKAN_END"));                //�̈��\�Ҋm����ؓ�
//              �ǉ� �����܂�---------------------------------------------------------------                
				result.setKokaiFlg(recordSet.getString("KOKAI_FLG"));						//���J�t���O
				result.setKessaiNo(recordSet.getString("KESSAI_NO"));						//���J���ϔԍ�	
				result.setKokaiID(recordSet.getString("KOKAI_ID"));							//���J�m���ID
				result.setHokanDate(recordSet.getDate("HOKAN_DATE"));						//�f�[�^�ۊǓ�
				result.setYukoDate(recordSet.getDate("YUKO_DATE"));							//�ۊǗL������
				result.setBiko(recordSet.getString("BIKO"));								//���l			
				result.setDelFlg(recordSet.getString("DEL_FLG"));							//�폜�t���O
				
//				 2006/10/24 �Ո� �ǉ� ��������
				result.setRigaiEndDate(recordSet.getDate("RIGAI_KIKAN_END"));				//���Q�֌W���͒��ؓ�
//				 2006/10/24 �Ո� �ǉ� �����܂�
				
				//2006.06.08 iso �R���S�����ƈꗗ�ł̎��Ɩ��\�������C��
				result.setJigyoCd(recordSet.getString("JIGYO_ID").substring(2, 7));			//����ID
				
				return result;
			} else {
				throw new NoDataFoundException(
					"���ƊǗ����e�[�u���ɊY������f�[�^��������܂���B�����L�[�F����ID'"
						+ primaryKeys.getJigyoId()
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("���ƊǗ����e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	
	
	/**
	 * ���ƊǗ�����Ԃ��B
	 * ���ƃR�[�h�A�N�x�A�񐔂����Ɏ��ƊǗ�����Ԃ��B
	 * @param connection
	 * @param jigyoCd
	 * @param nendo
	 * @param kaisu
	 * @return
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 */
	public JigyoKanriInfo selectJigyoKanriInfo(
            Connection connection,
            String     jigyoCd,
            String     nendo,
            String     kaisu)
		    throws DataAccessException, NoDataFoundException {

		//�a��N�x�𐼗�N�x�i���Q�P�^�j�ɕϊ�����
		nendo = DateUtil.changeWareki2Seireki(nendo);
			
		//----------����ID���쐬
		String jigyoId = nendo + jigyoCd + kaisu;
		JigyoKanriPk primaryKeys = new JigyoKanriPk(jigyoId);
		
		return selectJigyoKanriInfo(connection, primaryKeys);
		
	}

	/**
	 * ���ƊǗ����̐����擾����B�폜�t���O�Ɋ֌W�Ȃ���������B
	 * @param connection			�R�l�N�V����
	 * @param countInfo			    ��L�[���
	 * @return						���ƊǗ����
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 */
	public int countJigyoKanriInfo(
		Connection connection,
		JigyoKanriInfo countInfo)
		throws DataAccessException {
		String query =
			"SELECT COUNT(*)"
				+ " FROM JIGYOKANRI"+dbLink
				+ " WHERE JIGYO_NAME = ?"	//���Ɩ�
				+ " AND NENDO = ?"			//�N�x				
				+ " AND KAISU = ?";			//��
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,countInfo.getJigyoName());
			DatabaseUtil.setParameter(preparedStatement,i++,countInfo.getNendo());
			DatabaseUtil.setParameter(preparedStatement,i++,countInfo.getKaisu());
			recordSet = preparedStatement.executeQuery();
			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("���ƊǗ����e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * ���ƊǗ�����o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^���鎖�ƊǗ����
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertJigyoKanriInfo(
		Connection connection,
		JigyoKanriInfo addInfo)
		throws DataAccessException, DuplicateKeyException {
			
		//�d���`�F�b�N
		try {
			selectJigyoKanriInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO JIGYOKANRI"+dbLink+" A "
				+ "("
				+ " A.JIGYO_ID"				//����ID
				+ ",A.NENDO"				//�N�x
				+ ",A.KAISU"				//��
				+ ",A.JIGYO_NAME"			//���Ɩ�
				+ ",A.JIGYO_KUBUN"			//���Ƌ敪
				+ ",A.SHINSA_KUBUN"			//�R���敪
				+ ",A.TANTOKA_NAME"			//�Ɩ��S����
				+ ",A.TANTOKAKARI"			//�Ɩ��S���W��
				+ ",A.TOIAWASE_NAME"		//�₢���킹��S���Җ�
				+ ",A.TOIAWASE_TEL"			//�₢���킹��d�b�ԍ�
				+ ",A.TOIAWASE_EMAIL"		//�₢���킹��E-mail
				+ ",A.UKETUKEKIKAN_START"	//�w�U��t���ԁi�J�n�j
				+ ",A.UKETUKEKIKAN_END"		//�w�U��t���ԁi�I���j
                //�ǉ� �����܂�---------------------------------------------------------------
                // 2006/06/14 �ǉ� �������� ���m----------------------------------
                + ",A.KARIRYOIKINO_UKETUKEKIKAN_END " //���̈�ԍ����s���ؓ�
                + ",A.RYOIKI_KAKUTEIKIKAN_END " //�̈��\�Ҋm����ؓ�
                //2006/06/14 ���m �ǉ� �����܂�-----------------------------------
				//2005/04/21 �ǉ� ��������----------------------------------------------------
				//���R URL�̒ǉ��̂���
				+ ",A.URL_TITLE"			//URL(�^�C�g��)
				+ ",A.URL_ADDRESS"			//URL(�A�h���X)
				+ ",A.DL_URL"				//�_�E�����[�hURL
				//2005/04/21 �ǉ� �����܂�---------------------------------------------------------------
				//2006/02/10 �ǉ� ��������----------------------------------------------------
				+ ",A.MEIBO_DATE"			//�����Җ���o�^�ŏI���ؓ�
				+ ",A.SHINSAKIGEN"			//�R������
				+ ",A.TENPU_NAME"			//�Y�t������
				+ ",A.TENPU_WIN"			//�Y�t�t�@�C���i�[�t�H���_�iWin�j
				+ ",A.TENPU_MAC"			//�Y�t�t�@�C���i�[�t�H���_�iMac�j
//2007/02/03 �c�@�ǉ���������
                + ",A.PAGE_FROM"            //������e�t�@�C���y�[�W��(����)
                + ",A.PAGE_TO"              //������e�t�@�C���y�[�W��(���)
//2007/02/03�@�c�@�ǉ������܂�                
				+ ",A.HYOKA_FILE_FLG"		//�]���p�t�@�C���L��
				+ ",A.HYOKA_FILE"			//�]���p�t�@�C���i�[�t�H���_
				+ ",A.KOKAI_FLG"			//���J�t���O
				+ ",A.KESSAI_NO"			//���J���ٔԍ�
				+ ",A.KOKAI_ID"				//���J�m���ID
				+ ",A.HOKAN_DATE"			//�f�[�^�ۊǓ�
				+ ",A.YUKO_DATE"			//�ۊǗL������
				+ ",A.BIKO"					//���l
				+ ",A.DEL_FLG"				//�폜�t���O
//				 2006/10/24 �Ո� �ǉ� ��������
				+ ",A.RIGAI_KIKAN_END"		//���Q�֌W���͒��ؓ�
//				 2006/10/24 �Ո� �ǉ� �����܂�
				+ ") "
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoId());			//����ID
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNendo());			//�N�x
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKaisu());			//��
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoName());		//���Ɩ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoKubun());		//���Ƌ敪
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsaKubun());		//�R���敪
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantokaName());		//�Ɩ��S����
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTantoKakari());		//�Ɩ��S���W��
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getToiawaseName());		//�₢���킹��S���Җ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getToiawaseTel());		//�₢���킹��d�b�ԍ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getToiawaseEmail());	//�₢���킹��E-mail
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUketukekikanStart());//�w�U��t���ԁi�J�n�j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUketukekikanEnd());	//�w�U��t���ԁi�I���j
            // 2006/06/14 ���m �ǉ� �������� ----------------------------------------------------
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKariryoikiNoEndDate()); //���̈�ԍ����s���ؓ�
            // 2006/06/14 ���m �ǉ� �����܂�-----------------------------------------------------
//          2006/07/10 �ǉ� �������� ���`��----------------------------------------------------
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRyoikiEndDate());        //�̈��\�Ҋm����ؓ�
//          2006/07/10 �ǉ� �����܂� ���`��---------------------------------------------------------------
            //2005/04/21 �ǉ� ��������----------------------------------------------------
			//���R URL�̒ǉ��̂���
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUrlTitle());			//URL(�^�C�g��)
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUrlAddress());		//URL(�A�h���X)
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getDlUrl());			//�_�E�����[�hURL
			//�ǉ� �����܂�---------------------------------------------------------------	
			//2006/02/10 �ǉ� ��������----------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getMeiboDate());		//�����Җ���o�^�ŏI���ؓ�
			//�ǉ� �����܂�---------------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsaKigen());		//�R������
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTenpuName());		//�Y�t������
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTenpuWin());			//�Y�t�t�@�C���i�[�t�H���_�iWin�j
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTenpuMac());			//�Y�t�t�@�C���i�[�t�H���_�iMac�j
//2007/02/03 �c�@�ǉ���������
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getPageFrom());         //������e�t�@�C���y�[�W��(����)
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getPageTo());           //������e�t�@�C���y�[�W��(���)
//2007/02/03�@�c�@�ǉ������܂�            
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHyokaFileFlg());		//�]���p�t�@�C���L��
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHyokaFile());		//�]���p�t�@�C���i�[�t�H���_
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKokaiFlg());			//���J�t���O
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKessaiNo());			//���J���ٔԍ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKokaiID());			//���J�m���ID
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHokanDate());		//�f�[�^�ۊǓ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getYukoDate());			//�ۊǗL������
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());				//���l			
			DatabaseUtil.setParameter(preparedStatement, i++, 0);							//�폜�t���O
//			 2006/10/24 �Ո� �ǉ� ��������
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRigaiEndDate());		//���Q�֌W���͒��ؓ�						//�폜�t���O
//			 2006/10/24 �Ո� �ǉ� �����܂�
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("���ƊǗ����o�^���ɗ�O���������܂����B ", ex);
			throw new DataAccessException("���ƊǗ����o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * ���ƊǗ������X�V����B
	 * @param connection                �R�l�N�V����
	 * @param updateInfo                �X�V���鎖�ƊǗ����
	 * @throws DataAccessException      �X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException     �Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateJigyoKanriInfo(
		Connection connection,
		JigyoKanriInfo updateInfo)
		throws DataAccessException, NoDataFoundException {

		//����
		selectJigyoKanriInfo(connection, updateInfo);

		String query =
			"UPDATE JIGYOKANRI"+dbLink+" A "
				+ " SET"	
				+ " A.JIGYO_ID = ? "			//����ID
				+ ",A.NENDO = ? "				//�N�x
				+ ",A.KAISU = ? "				//��
				+ ",A.JIGYO_NAME = ? "			//���Ɩ�
				+ ",A.JIGYO_KUBUN = ? "			//���Ƌ敪
				+ ",A.SHINSA_KUBUN = ? "		//�R���敪
				+ ",A.TANTOKA_NAME = ? "		//�Ɩ��S����
				+ ",A.TANTOKAKARI = ? "			//�Ɩ��S���W��
				+ ",A.TOIAWASE_NAME = ? "		//�₢���킹��S���Җ�
				+ ",A.TOIAWASE_TEL = ? "		//�₢���킹��d�b�ԍ�
				+ ",A.TOIAWASE_EMAIL = ? "		//�₢���킹��E-mail
				+ ",A.UKETUKEKIKAN_START = ? "	//�w�U��t���ԁi�J�n�j
				+ ",A.UKETUKEKIKAN_END = ? "	//�w�U��t���ԁi�I���j
                // 2006/06/14 ���m �ǉ� ��������---------------------------------------------------
                + ",A.KARIRYOIKINO_UKETUKEKIKAN_END = ?" //���̈�ԍ����s���ؓ�
                + ",A.RYOIKI_KAKUTEIKIKAN_END = ?" //���̈�ԍ����s���ؓ�
                // 2006/06/14 ���m �ǉ� �����܂�----------------------------------------------------
				//2005/04/24 �ǉ� ��������----------------------------------------------------
				//���R URL�̒ǉ��̂���
				+ ",A.URL_TITLE = ?"			//URL(�^�C�g��)
				+ ",A.URL_ADDRESS = ?"			//URL(�A�h���X)
				+ ",A.DL_URL = ?"				//�_�E�����[�hURL
				//�ǉ� �����܂�---------------------------------------------------------------
				//2006/02/10 �ǉ� ��������----------------------------------------------------
				+ ",A.MEIBO_DATE=?"			    //�����Җ���o�^�ŏI���ؓ�
				//�ǉ� �����܂�---------------------------------------------------------------
				
				+ ",A.SHINSAKIGEN = ? "			//�R������
				+ ",A.TENPU_NAME = ? "			//�Y�t������
				+ ",A.TENPU_WIN = ? "			//�Y�t�t�@�C���i�[�t�H���_�iWin�j
				+ ",A.TENPU_MAC = ? "			//�Y�t�t�@�C���i�[�t�H���_�iMac�j
//2007/02/03 �c�@�ǉ���������
                + ",A.PAGE_FROM = ?"            //������e�t�@�C���y�[�W��(����)
                + ",A.PAGE_TO = ?"              //������e�t�@�C���y�[�W��(���)
//2007/02/03 �c�@�ǉ������܂�                
				+ ",A.HYOKA_FILE_FLG = ? "		//�]���p�t�@�C���L��
				+ ",A.HYOKA_FILE = ? "			//�]���p�t�@�C���i�[�t�H���_
				+ ",A.KOKAI_FLG = ? "			//���J�t���O
				+ ",A.KESSAI_NO = ? "			//���J���ٔԍ�
				+ ",A.KOKAI_ID = ? "			//���J�m���ID
				+ ",A.HOKAN_DATE = ? "			//�f�[�^�ۊǓ�
				+ ",A.YUKO_DATE = ? "			//�ۊǗL������
				+ ",A.BIKO = ? "				//���l
				+ ",A.DEL_FLG = ? "				//�폜�t���O
//				 2006/10/24 �Ո� �ǉ� ��������
				+ ",A.RIGAI_KIKAN_END = ? "		//���Q�֌W���͒��ؓ�
//				 2006/10/24 �Ո� �ǉ� �����܂�
				+ " WHERE"
				+ " JIGYO_ID = ?";//����ID

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoId());			//����ID
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNendo());				//�N�x
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKaisu());				//��
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoName());			//���Ɩ�
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());		//���Ƌ敪
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsaKubun());		//�R���敪
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantokaName());		//�Ɩ��S����
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTantoKakari());		//�Ɩ��S���W��
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getToiawaseName());		//�₢���킹��S���Җ�
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getToiawaseTel());		//�₢���킹��d�b�ԍ�
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getToiawaseEmail());		//�₢���킹��E-mail
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUketukekikanStart());//�w�U��t���ԁi�J�n�j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUketukekikanEnd());	//�w�U��t���ԁi�I���j
            // 2006/06/14 ���m �ǉ� �������� --------------------------------------------------------------
            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKariryoikiNoEndDate()); //���̈�ԍ����s���ؓ�
            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getRyoikiEndDate()); //�̈��\�Ҋm����ؓ�
            // 2006/06/14 ���m �ǉ� �����܂�---------------------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUrlTitle());			//URL�^�C�g��
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUrlAddress());		//URL�A�h���X
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDlUrl());				//�_�E�����[�hURL		
			//2006/02/10 �ǉ� ��������----------------------------------------------------
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getMeiboDate());		    //�����Җ���o�^�ŏI���ؓ�
			//�ǉ� �����܂�---------------------------------------------------------------	
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsaKigen());		//�R������
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTenpuName());			//�Y�t������
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTenpuWin());			//�Y�t�t�@�C���i�[�t�H���_�iWin�j
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTenpuMac());			//�Y�t�t�@�C���i�[�t�H���_�iMac�j
//2007/02/03 �c�@�ǉ���������
            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPageFrom());          //������e�t�@�C���y�[�W��(����)
            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getPageTo());            //������e�t�@�C���y�[�W��(���)
//2007/02/03�@�c�@�ǉ������܂�            
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHyokaFileFlg());		//�]���p�t�@�C���L��
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHyokaFile());			//�]���p�t�@�C���i�[�t�H���_
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKokaiFlg());			//���J�t���O
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKessaiNo());			//���J���ٔԍ�
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKokaiID());			//���J�m���ID
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHokanDate());			//�f�[�^�ۊǓ�
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getYukoDate());			//�ۊǗL������
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());				//���l			
			DatabaseUtil.setParameter(preparedStatement, i++, 0);								//�폜�t���O
//			 2006/10/24 �Ո� �ǉ� ��������
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getRigaiEndDate());		//���Q�֌W���͒��ؓ�
//			 2006/10/24 �Ո� �ǉ� �����܂�
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoId());			//����ID
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("���ƊǗ����X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * ���ƊǗ����̌��J�m������X�V����B
	 * ���J�m������X�V����ꍇ�́A�p�X���[�h�̃`�F�b�N���s���B
	 * @param connection          �R�l�N�V����
     * @param jigyoPks            ���ƊǗ��e�[�u����L�[���X�g
     * @param kesssaiNo           ���ϔԍ�
	 * @param gyomutantoId        �Ɩ��S���h�c
     * @throws DataAccessException
     * @throws NoDataFoundException
	 */
	public void updateKokaiKakutei(Connection connection, JigyoKanriPk[] jigyoPks,
            String kesssaiNo, String gyomutantoId) 
		throws DataAccessException, NoDataFoundException {

		String query =
			"UPDATE JIGYOKANRI"+dbLink
				+ " SET"	
				+ " KOKAI_FLG = ?"//���J�t���O
				+ ",KESSAI_NO = ?"//���J���ٔԍ�
				+ ",KOKAI_ID = ?";//���J�m���ID		
				
		StringBuffer buffer = new StringBuffer(query);
		
		//�X�V�L�[���Z�b�g						
		String aSeparate = "";
		buffer.append(" WHERE JIGYO_ID IN(");	//����ID
		for (int i = 0; i < jigyoPks.length; i++) {
			buffer.append(aSeparate);
			buffer.append("?");
			aSeparate = ",";
		}
		buffer.append(")");


		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(buffer.toString());
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++, "1");							//���J�t���O
			DatabaseUtil.setParameter(preparedStatement,i++, kesssaiNo);					//���J���ٔԍ�		
			DatabaseUtil.setParameter(preparedStatement,i++, gyomutantoId);					//���J�m���ID	
			for (int n = 0; n < jigyoPks.length; n++) {					
				DatabaseUtil.setParameter(preparedStatement,i++, jigyoPks[n].getJigyoId());	//����ID
			}
			int count = preparedStatement.executeUpdate();
			
			if(log.isDebugEnabled()){
				log.debug(count + "���̌��J�m����̍X�V�ɐ������܂����B");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("���ƊǗ����i���J�m��j�X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * ���ƊǗ������폜����B(�폜�t���O) 
	 * @param connection			�R�l�N�V����
	 * @param deleteInfo            �폜���鏊���@�֎�L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteFlgJigyoKanriInfo(
		Connection connection,
		JigyoKanriPk deleteInfo)
		throws DataAccessException, NoDataFoundException {

		//����
		selectJigyoKanriInfo(connection, deleteInfo);
		
		String query =
			"UPDATE JIGYOKANRI"+dbLink
				+ " SET"
				+ " DEL_FLG = 1"//�폜�t���O				
				+ " WHERE"
				+ " JIGYO_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,deleteInfo.getJigyoId());//����ID
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {

			throw new DataAccessException("���ƊǗ����폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * ���ƊǗ������폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param deleteInfo			�폜���鏊���@�֎�L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteJigyoKanriInfo(
		Connection connection,
		JigyoKanriPk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		deleteJigyoKanriInfo(connection, deleteInfo, true);
	}
	
	
	
	/**
	 * ���ƊǗ������폜����B(�����폜) 
	 * �Y���f�[�^�����݂��Ȃ������ꍇ�͉����������Ȃ��B
	 * @param connection			�R�l�N�V����
	 * @param deleteInfo			�폜���鏊���@�֎�L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 */
	public void deleteJigyoKanriInfoNoCheck(
		Connection connection,
		JigyoKanriPk deleteInfo)
		throws DataAccessException
	{
		try{
			deleteJigyoKanriInfo(connection, deleteInfo, false);
		}catch(NoDataFoundException e){
			//��3������false�ɂ����ꍇ�A�{�����̗�O���������邱�Ƃ͖���
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * ���ƊǗ������폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param deleteInfo			�폜���鏊���@�֎�L�[���
	 * @param existsCheck          ���R�[�h�̑��݃`�F�b�N������ꍇ[true]
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ(existsCheck��true�̂Ƃ��̂݁j
	 */
	private void deleteJigyoKanriInfo(
		Connection connection,
		JigyoKanriPk deleteInfo,
		boolean existsCheck)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�폜�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		if(existsCheck){
			selectJigyoKanriInfo(connection, deleteInfo);
		}
		
		String query =
		"DELETE FROM JIGYOKANRI"+dbLink
			+ " WHERE"
			+ " JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement,index++,deleteInfo.getJigyoId());	//���������i����ID�j
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("���ƊǗ����폜���i�����폜�j�ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
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
			"INSERT INTO JIGYOKANRI"+dbLink
				+ " SELECT * FROM JIGYOKANRI WHERE JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);		//���������i����ID�j
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("���ƊǗ����ۊǒ��ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * ���Ə��i�ꕔ�j�̈ꗗ���擾����B�i�폜����Ă��Ȃ����̂̂݁j
	 * @param	connection			�R�l�N�V����
	 * @return						���Ə��i�ꕔ�j
	 * @throws ApplicationException
	 */
	public static List selectJigyoKanriList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " JIGYO_ID"			
			+ ",NENDO"				
			+ ",KAISU"				
			+ ",JIGYO_NAME"			
			+ ",JIGYO_KUBUN"		
			+ ",SHINSA_KUBUN"		
			+ " FROM JIGYOKANRI A"
			+ " WHERE A.DEL_FLG = '0'"
			+ " ORDER BY A.JIGYO_ID";								
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
				"���Ə�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		}
	}		
	
	
	
	/**
	 * ���Y���Ƌ敪�̎��Ə��i�ꕔ�j�̈ꗗ���擾����B�i�폜����Ă��Ȃ����̂̂݁j
	 * @param	connection			�R�l�N�V����
	 * @param  jigyoKubun          ���Ƌ敪
	 * @return						���Ə��i�ꕔ�j
	 * @throws ApplicationException
	 */
	public static List selectJigyoKanriList(Connection connection, String jigyoKubun)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " JIGYO_ID"			
			+ ",NENDO"				
			+ ",KAISU"				
			+ ",JIGYO_NAME"			
			+ ",JIGYO_KUBUN"		
			+ ",SHINSA_KUBUN"		
			+ " FROM JIGYOKANRI A"
			+ " WHERE A.DEL_FLG = '0'"
			+ " AND A.JIGYO_KUBUN = ?"
			+ " ORDER BY A.JIGYO_ID";								
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
				"���Ə�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		}
	}		
	
	/**
	 * ���F�m�F���[�����M���ƊǗ������擾����B�w�U��t���ؓ���n���O�̎��Ə��̂�
	 * �폜�t���O���u0�v�̏ꍇ�̂݌�������B
	 * @param connection			�R�l�N�V����
	 * @param days					���ؓ��܂ł̓���
	 * @return						���ƊǗ����
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public List selectShoninTsuchiJigyoInfo(Connection connection, int days)
		throws DataAccessException, NoDataFoundException
	{
		
		String query =
					  "SELECT DISTINCT A.JIGYO_ID, A.NENDO, A.KAISU, A.JIGYO_NAME,"
				          + " B.SHOZOKU_CD, C.TANTO_EMAIL"
					+ "  FROM JIGYOKANRI A, SHINSEIDATAKANRI B, SHOZOKUTANTOINFO C, CHCKLISTINFO D"
					+ " WHERE A.UKETUKEKIKAN_END = TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD') + " + EscapeUtil.toSqlString(Integer.toString(days)) 
					//+ "   AND A.JIGYO_KUBUN IN ('1','2','3')"
					//2005.11.16 iso ��ՁE����Ń`�F�b�N���X�g�m��ɂ͏o���Ȃ��B
//					+ "   AND B.JOKYO_ID IN ('01','02','03')"
					+ "   AND ("
//2006/05/09 �ǉ���������                    
//					+ "    (B.JOKYO_ID IN ('01','02','03') AND B.JIGYO_KUBUN IN ('1','2','3'))"
                    + "    (B.JOKYO_ID IN ('01','02','03') AND"
//                    + "     B.JIGYO_KUBUN IN ("
//                    + StringUtil.changeIterator2CSV(JigyoKubunFilter.getShoninTaishoJigyoKubun().iterator(),true)
//  2006/06/29 ZJP �ǉ��������� 
                    + " SUBSTR(B.JIGYO_ID,3,5) IN ("
                    + StringUtil.changeIterator2CSV(JigyoKubunFilter.getShoninTaishoJigyoCd().iterator(),true)
                    + "))"
// �@2006/06/29 ZJP �ǉ������܂� 
//�c�@�ǉ������܂�                    
					//2005.12.16 iso �w�n�Ń��[�������M����Ȃ��o�O���C��
					//�w�n�̏ꍇ�́A�`�F�b�N���X�g����������Ȃ��B
//					+ "    OR (D.JOKYO_ID = '03' AND B.JIGYO_KUBUN IN ('4','5'))"
//					+ "   )"
//					+ "   AND (B.JIGYO_ID = D.JIGYO_ID AND B.SHOZOKU_CD = D.SHOZOKU_CD)"
//2006/05/09 �ǉ���������                      
//					+ "    OR (D.JOKYO_ID = '03' AND B.JIGYO_KUBUN IN ('4','5') AND (B.JIGYO_ID = D.JIGYO_ID AND B.SHOZOKU_CD = D.SHOZOKU_CD))"
                    + "    OR (D.JOKYO_ID = '03' " 
//                    + "    AND B.JIGYO_KUBUN IN ("
//                    +      StringUtil.changeIterator2CSV(JigyoKubunFilter.getCheckListTaishoJigyoKubun().iterator(),true)
//                    + "    )"
//�@2006/06/29 ZJP �ǉ��������� 
                    + "    AND SUBSTR(B.JIGYO_ID,3,5) IN ("
                    +      StringUtil.changeIterator2CSV(JigyoKubunFilter.getCheckListTaishoJigyoCd().iterator(),true)
                    + "    )"
// 2006/06/29 ZJP �ǉ������܂�                    
                    + "    AND (B.JIGYO_ID = D.JIGYO_ID AND B.SHOZOKU_CD = D.SHOZOKU_CD))"
//�c�@�ǉ������܂�                       
					+ "   )"
					
					+ "   AND A.DEL_FLG = 0"
					+ "   AND A.JIGYO_ID = B.JIGYO_ID"
					+ "   AND A.NENDO = B.NENDO"
					+ "   AND A.KAISU = B.KAISU"
					+ "   AND B.DEL_FLG = 0"
					
					//2006.05.18 iso �폜���ꂽ���������@�֒S���҂փ��[�������M�����o�O���C��
					+ "   AND C.DEL_FLG = 0"
					
					+ "   AND B.SHOZOKU_CD = C.SHOZOKU_CD"
					+ " ORDER BY SHOZOKU_CD, JIGYO_ID, NENDO, KAISU";

		if (log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		return SelectUtil.select(connection, query);

	}
	
//2006/04/26 �ǉ���������
    /**
     * ��ՂȂǂ̎��Ə��̈ꗗ���擾����B�i�폜����Ă��Ȃ����݂̂̂����Ƌ敪��4,6,7�̂��́j
     * @param   connection          �R�l�N�V����
     * @return                      ���Ə��i��Ռn�̎��Ə��j
     * @throws ApplicationException
     */
    public static List selectKibanJigyoKubun(Connection connection)
        throws ApplicationException {

        //-----------------------
        // SQL���̍쐬
        //-----------------------
        String select =
            "SELECT"
            + " JIGYO_ID"           
            + ",NENDO"              
            + ",KAISU"              
            + ",JIGYO_NAME"         
            + ",JIGYO_KUBUN"        
            + ",SHINSA_KUBUN"       
            + " FROM JIGYOKANRI A"
            + " WHERE A.DEL_FLG = '0'"
            + " AND A.JIGYO_KUBUN IN(" 
            + IJigyoKubun.JIGYO_KUBUN_KIBAN
            + ","
            + IJigyoKubun.JIGYO_KUBUN_WAKATESTART
            + ","
            + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI
            + ")"
            + " ORDER BY A.JIGYO_ID";  
        
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
                "���Ə�񌟍�����DB�G���[���������܂����B",
                new ErrorInfo("errors.4004"),
                e);
        }
    }
//�c�@�ǉ������܂�  
    
//2006/06/20 lwj add begin
    /**
     * ���ƊǗ������擾����B�폜�t���O���u0�v�̏ꍇ�̂݌�������B
     * @param connection            �R�l�N�V����
     * @param primaryKeys           ��L�[���
     * @return                      ���ƊǗ����
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ�B
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public JigyoKanriInfo selectJigyoKanriInfos(
            Connection connection,
            JigyoKanriPk primaryKeys)
            throws DataAccessException, NoDataFoundException{
        
        String query =
            "SELECT "
                + " A.NENDO"                //�N�x
                + ",A.KAISU"                //��
                + ",A.JIGYO_NAME"           //���Ɩ�
                + ",B.KIBOUBUMON_CD"        //�R����]����i�n���j�R�[�h
                + " FROM JIGYOKANRI A"
                + " INNER JOIN RYOIKIKEIKAKUSHOINFO B"
                + " ON A.JIGYO_ID = B.JIGYO_ID "
                + " WHERE A.JIGYO_ID = ?";
        
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try{
            JigyoKanriInfo result = new JigyoKanriInfo();
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, primaryKeys.getJigyoId());//����ID
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()){
                result.setNendo(recordSet.getString("NENDO"));
                result.setKaisu(recordSet.getString("KAISU"));
                result.setJigyoName(recordSet.getString("JIGYO_NAME")); 
                result.setKiboubumonCd(recordSet.getString("KIBOUBUMON_CD"));
                
                return result;
            }else {
                throw new NoDataFoundException(
                        "�Y������f�[�^��������܂���B�����L�[�F����ID'"
                            + primaryKeys.getJigyoId()
                            + "'");
                }
        }catch (SQLException ex) {
            throw new DataAccessException("�e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2006/06/20 lwj add end

//2007/5/25 by xiang start
    /**
     * �ۊǓ��t�ƕۊǊ������X�V����
     * @param connection
     * @param jigyoPk�@����ID
     * @param yukoKigen �ۊǊ���
     */
	public void updateHokanInfo(Connection connection, JigyoKanriPk jigyoPk, Date yukoKigen) 
		throws DataAccessException, NoDataFoundException {

		String query =
					"UPDATE JIGYOKANRI"+dbLink
						+ " SET"	
						+ " HOKAN_DATE = SYSDATE"	//�ۊǓ��t
						+ ",YUKO_DATE = ?"			//�ۊǊ���
						+ " WHERE JIGYO_ID = ?"
						;
		
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++, yukoKigen);			//�ۊǊ���	
			DatabaseUtil.setParameter(preparedStatement,i++, jigyoPk.getJigyoId());	//����ID	

			int count = preparedStatement.executeUpdate();
			
			if(count != 1 && log.isDebugEnabled()){
				log.debug(count + "���̎��ƊǗ����̍X�V�ɐ������܂����B");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("���ƊǗ����i�ۊǓ��t�j�X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
//2007/5/25 by xiang end

}