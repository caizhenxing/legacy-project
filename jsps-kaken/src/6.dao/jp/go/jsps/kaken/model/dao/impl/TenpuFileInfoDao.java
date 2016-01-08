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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.TenpuFileInfo;
import jp.go.jsps.kaken.model.vo.TenpuFilePk;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �Y�t�t�@�C���Ǘ��e�[�u���A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: TenpuFileInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class TenpuFileInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** �Y�t�t�@�C���Ǘ��e�[�u���V�[�P���X���iSEQ_����ID */
	private static final String SEQ_TENPUFILEINFO = "SEQ_TENPU";
	
	/** �Y�t�t�@�C���Ǘ��e�[�u���V�[�P���X�̎擾�����i�A�ԗp�j */
	private static final int SEQ_FIGURE = 1;

	/** ���O */
	protected static final Log log = LogFactory.getLog(TenpuFileInfoDao.class);

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
	public TenpuFileInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	
	/**
	 * �R���X�g���N�^�B
	 * @param userInfo	���s���郆�[�U���
	 * @param dbLink   DB�����N��
	 */
	public TenpuFileInfoDao(UserInfo userInfo, String dbLink) {
		this.userInfo = userInfo;
		this.dbLink   = dbLink;
	}
	
	
	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * �Y�t�t�@�C�������擾����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���i�V�X�e����t�ԍ��{�V�[�P���X�ԍ��j
	 * @return						�����@�֏��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public TenpuFileInfo selectTenpuFileInfo(
		Connection connection,
		TenpuFilePk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		String query =
			"SELECT "
				+ " A.SYSTEM_NO,"					//�V�X�e����t�ԍ�
				+ " A.SEQ_TENPU,"					//�V�[�P���X�ԍ�
				+ " A.JIGYO_ID,"					//����ID
				+ " A.TENPU_PATH,"					//�i�[�p�X
				+ " A.PDF_PATH"						//�ϊ��t�@�C���i�[�p�X
				+ " FROM TENPUFILEINFO"+dbLink+" A"
				+ " WHERE"
				+ " SYSTEM_NO = ?"
				+ " AND"
				+ " SEQ_TENPU = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getSystemNo());	//���������i�V�X�e����t�ԍ��j
			DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getSeqTenpu());	//���������i�V�[�P���X�ԍ��j
			recordSet = preparedStatement.executeQuery();
			
			TenpuFileInfo result = new TenpuFileInfo();
			if (recordSet.next()) {
				result.setSystemNo(recordSet.getString("SYSTEM_NO"));
				result.setSeqTenpu(recordSet.getString("SEQ_TENPU"));
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setTenpuPath(recordSet.getString("TENPU_PATH"));
				result.setPdfPath(recordSet.getString("PDF_PATH"));
			}else{
				throw new NoDataFoundException(
					"�Y�t�t�@�C���Ǘ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
						+ primaryKeys.getSystemNo()
						+ "' �����L�[�F�V�[�P���X�ԍ�'"
						+ primaryKeys.getSeqTenpu()
						+ "'");
			}
			
			return result;
			
		} catch (SQLException ex) {
			throw new DataAccessException("�Y�t�t�@�C���Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}



	/**
	 * �Y�t�t�@�C�������擾����B
	 * �V�X�e����t�ԍ��ɕR�Â��Y�t����S�ĕԂ��B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			�L�[���i�V�X�e����t�ԍ��j
	 * @return						�����@�֏��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public TenpuFileInfo[] selectTenpuFileInfos(
		Connection connection,
		ShinseiDataPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		String query =
			"SELECT "
				+ " A.SYSTEM_NO,"					//�V�X�e����t�ԍ�
				+ " A.SEQ_TENPU,"					//�V�[�P���X�ԍ�
				+ " A.JIGYO_ID,"					//����ID
				+ " A.TENPU_PATH,"					//�i�[�p�X
				+ " A.PDF_PATH"						//�ϊ��t�@�C���i�[�p�X
				+ " FROM TENPUFILEINFO"+dbLink+" A"
				+ " WHERE SYSTEM_NO = ?";
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getSystemNo());	//���������i�V�X�e����t�ԍ��j
			recordSet = preparedStatement.executeQuery();
			
			//�Y�����R�[�h�����ׂĎ擾
			List list = new ArrayList();
			while(recordSet.next()){
				TenpuFileInfo result = new TenpuFileInfo();
				result.setSystemNo(recordSet.getString("SYSTEM_NO"));
				result.setSeqTenpu(recordSet.getString("SEQ_TENPU"));
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setTenpuPath(recordSet.getString("TENPU_PATH"));
				result.setPdfPath(recordSet.getString("PDF_PATH"));
				list.add(result);
			}
			
			//�R���N�V��������z��֕ϊ�
			TenpuFileInfo[] resultArray = (TenpuFileInfo[])list.toArray(new TenpuFileInfo[0]);
			
			//�Y�����R�[�h�����݂��Ȃ������ꍇ
			if(resultArray.length == 0){
				throw new NoDataFoundException(
					"�Y�t�t�@�C���Ǘ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
						+ primaryKeys.getSystemNo()
						+ "'");
			}
			
			return resultArray;
			
		} catch (SQLException ex) {
			throw new DataAccessException("�Y�t�t�@�C���Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	


	/**
	 * �Y�t�t�@�C������o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����Y�t�t�@�C���f�[�^
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ
	 */
	public void insertTenpuFileInfo(
		Connection connection,
		TenpuFileInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//�d���`�F�b�N
		try {
			selectTenpuFileInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
		
		String query =
			"INSERT INTO TENPUFILEINFO"+dbLink
				+ " ("
				+ "  SYSTEM_NO,"				//�V�X�e����t�ԍ�
				+ "  SEQ_TENPU,"				//�V�[�P���X�ԍ�
				+ "  JIGYO_ID,"					//����ID
				+ "  TENPU_PATH,"				//�i�[�p�X
				+ "  PDF_PATH"					//PDF�p�X
				+ " ) "
				+ "VALUES "
				+ "("
				+ "?,?,?,?,?"
				+ ")";		
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement,index++, addInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,index++, addInfo.getSeqTenpu());
			DatabaseUtil.setParameter(preparedStatement,index++, addInfo.getJigyoId());
			DatabaseUtil.setParameter(preparedStatement,index++, addInfo.getTenpuPath());
			DatabaseUtil.setParameter(preparedStatement,index++, addInfo.getPdfPath());			
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("�Y�t�t�@�C�����o�^���ɗ�O���������܂����B ", ex);
			throw new DataAccessException("�Y�t�t�@�C�����o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}



	/**
	 * �Y�t�t�@�C�������X�V����B
	 * @param connection				�R�l�N�V����
	 * @param updateInfo				�X�V����Y�t�t�@�C���f�[�^
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateTenpuFileInfo(
		Connection connection,
		TenpuFileInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�X�V�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectTenpuFileInfo(connection, updateInfo);

		String query =
			"UPDATE TENPUFILEINFO"+dbLink
				+ " SET"
				+ " SYSTEM_NO = ?,"				//�V�X�e����t�ԍ�
				+ " SEQ_TENPU = ?,"				//�V�[�P���X�ԍ�
				+ " JIGYO_ID = ?,"				//����ID
				+ " TENPU_PATH = ?,"			//�i�[�p�X	
				+ " PDF_PATH = ?"				//PDF�i�[�p�X	
				+ " WHERE"
				+ " SYSTEM_NO = ?"
				+ " AND"
				+ " SEQ_TENPU = ?";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSeqTenpu());
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getJigyoId());
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getTenpuPath());			
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getPdfPath());			
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSystemNo());	//���������i�V�X�e����t�ԍ��j
			DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getSeqTenpu());	//���������i�V�[�P���X�ԍ��j		
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("�Y�t�t�@�C�����X�V���ɗ�O���������܂����B ", ex);
			throw new DataAccessException("�Y�t�t�@�C�����X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}



	/**
	 * �Y�t�t�@�C�������폜����B
	 * @param connection				�R�l�N�V����
	 * @param deleteInfo				��L�[���i�V�X�e����t�ԍ��{�V�[�P���X�ԍ��j
	 * @throws DataAccessException		�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteTenpuFileInfo(
		Connection connection,
		TenpuFilePk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�폜�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectTenpuFileInfo(connection, deleteInfo);
		
		String query =
			"DELETE FROM TENPUFILEINFO"+dbLink
				+ " WHERE"
				+ " SYSTEM_NO = ?"
				+ " AND"
				+ " SEQ_TENPU = ?";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, deleteInfo.getSystemNo());	//���������i�V�X�e����t�ԍ��j
			DatabaseUtil.setParameter(preparedStatement, index++, deleteInfo.getSeqTenpu());	//���������i�V�[�P���X�ԍ��j
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("�Y�t�t�@�C�����폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

    
	/**
	 * �Y�t�t�@�C�������폜����B 
	 * �V�X�e����t�ԍ��ɕR�Â��Y�t����S�č폜����B
	 * @param connection				�R�l�N�V����
	 * @param deleteInfo				�L�[���i�V�X�e����t�ԍ��j
	 * @throws DataAccessException		�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^���P����������Ȃ��ꍇ
	 */
	public void deleteTenpuFileInfos(
		Connection connection,
		ShinseiDataPk deleteInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�폜�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectTenpuFileInfos(connection, deleteInfo);
		
		String query =
			"DELETE FROM TENPUFILEINFO"+dbLink
				+ " WHERE"
				+ " SYSTEM_NO = ?";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, deleteInfo.getSystemNo());	//���������i�V�X�e����t�ԍ��j
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("�Y�t�t�@�C�����폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
	
	/**
	 * �Y�t�t�@�C�������폜����B 
	 * ����ID�ɕR�Â��Y�t����S�č폜����B
	 * �Y���f�[�^�����݂��Ȃ������ꍇ�͉����������Ȃ��B
	 * @param connection				�R�l�N�V����
	 * @param jigyoId				�@�@���������i����ID�j
	 * @throws DataAccessException		�폜���ɗ�O�����������ꍇ
	 */
	public void deleteTenpuFileInfos(
		Connection connection,
		String     jigyoId)
		throws DataAccessException
	{
		String query =
			"DELETE FROM TENPUFILEINFO"+dbLink
				+ " WHERE"
				+ " JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);		//���������i����ID�j
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�Y�t�t�@�C�����폜���ɗ�O���������܂����B ", ex);
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
			"INSERT INTO TENPUFILEINFO"+dbLink
				+ " SELECT * FROM TENPUFILEINFO WHERE JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);		//���������i����ID�j
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�Y�t�t�@�C�����ۊǒ��ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	
//2006/06/27 �c�@�ǉ���������
    /**
     * �Y�t�t�@�C�������擾����B
     * �V�X�e����t�ԍ��ɕR�Â��Y�t����S�ĕԂ��B
     * @param connection            �R�l�N�V����
     * @param primaryKeys           �L�[���i�V�X�e����t�ԍ��j
     * @return                      �����@�֏��
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ�B
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public TenpuFileInfo[] selectTenpuFileInfosForGaiyo(
        Connection connection,
        RyoikiKeikakushoPk primaryKeys)
        throws DataAccessException, NoDataFoundException
    {
        String query =
            "SELECT "
                + " A.SYSTEM_NO,"                   //�V�X�e����t�ԍ�
                + " A.SEQ_TENPU,"                   //�V�[�P���X�ԍ�
                + " A.JIGYO_ID,"                    //����ID
                + " A.TENPU_PATH,"                  //�i�[�p�X
                + " A.PDF_PATH"                     //�ϊ��t�@�C���i�[�p�X
                + " FROM TENPUFILEINFO"+dbLink+" A"
                + " WHERE SYSTEM_NO = ?";
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getRyoikiSystemNo());   //���������i�V�X�e����t�ԍ��j
            recordSet = preparedStatement.executeQuery();
            
            //�Y�����R�[�h�����ׂĎ擾
            List list = new ArrayList();
            while(recordSet.next()){
                TenpuFileInfo result = new TenpuFileInfo();
                result.setSystemNo(recordSet.getString("SYSTEM_NO"));
                result.setSeqTenpu(recordSet.getString("SEQ_TENPU"));
                result.setJigyoId(recordSet.getString("JIGYO_ID"));
                result.setTenpuPath(recordSet.getString("TENPU_PATH"));
                result.setPdfPath(recordSet.getString("PDF_PATH"));
                list.add(result);
            }
            
            //�R���N�V��������z��֕ϊ�
            TenpuFileInfo[] resultArray = (TenpuFileInfo[])list.toArray(new TenpuFileInfo[0]);
            
            //�Y�����R�[�h�����݂��Ȃ������ꍇ
            if(resultArray.length == 0){
                throw new NoDataFoundException(
                    "�Y�t�t�@�C���Ǘ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
                        + primaryKeys.getRyoikiSystemNo()
                        + "'");
            }
            
            return resultArray;
            
        } catch (SQLException ex) {
            throw new DataAccessException("�Y�t�t�@�C���Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    
    
    /**
     * �Y�t�t�@�C�������폜����B 
     * �V�X�e����t�ԍ��ɕR�Â��Y�t����S�č폜����B
     * @param connection                �R�l�N�V����
     * @param deleteInfo                �L�[���i�V�X�e����t�ԍ��j
     * @throws DataAccessException      �폜���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^���P����������Ȃ��ꍇ
     */
    public void deleteTenpuFileInfosForGaiyo(
        Connection connection,
        RyoikiKeikakushoPk deleteInfo)
        throws DataAccessException, NoDataFoundException
    {
        //�����i�폜�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
        selectTenpuFileInfosForGaiyo(connection, deleteInfo);
        
        String query =
            "DELETE FROM TENPUFILEINFO"+dbLink
                + " WHERE"
                + " SYSTEM_NO = ?";
        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, deleteInfo.getRyoikiSystemNo());    //���������i�V�X�e����t�ԍ��j
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            throw new DataAccessException("�Y�t�t�@�C�����폜���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
//2006/06/27�@�c�@�ǉ������܂� 
//2006/07/14�@zhangt�@�ǉ���������   
    /**
     * �Y�t�t�@�C�������擾����B
     * �V�X�e����t�ԍ��ɕR�Â��Y�t����S�ĕԂ��B
     * @param connection            �R�l�N�V����
     * @param primaryKeys           �L�[���i�V�X�e����t�ԍ��j
     * @return                      �����@�֏��
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ�B
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public TenpuFileInfo[] selectTenpuFiles(
        Connection connection,
        RyoikiKeikakushoPk primaryKeys)
        throws DataAccessException, NoDataFoundException
    {
        String query =
            "SELECT "
                + " A.SYSTEM_NO,"                   //�V�X�e����t�ԍ�
                + " A.SEQ_TENPU,"                   //�V�[�P���X�ԍ�
                + " A.JIGYO_ID,"                    //����ID
                + " A.TENPU_PATH,"                  //�i�[�p�X
                + " A.PDF_PATH"                     //�ϊ��t�@�C���i�[�p�X
                + " FROM TENPUFILEINFO"+dbLink+" A"
                + " WHERE SYSTEM_NO = ?";
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, primaryKeys.getRyoikiSystemNo());   //���������i�V�X�e����t�ԍ��j
            recordSet = preparedStatement.executeQuery();
            
            //�Y�����R�[�h�����ׂĎ擾
            List list = new ArrayList();
            while(recordSet.next()){
                TenpuFileInfo result = new TenpuFileInfo();
                result.setSystemNo(recordSet.getString("SYSTEM_NO"));
                result.setSeqTenpu(recordSet.getString("SEQ_TENPU"));
                result.setJigyoId(recordSet.getString("JIGYO_ID"));
                result.setTenpuPath(recordSet.getString("TENPU_PATH"));
                result.setPdfPath(recordSet.getString("PDF_PATH"));
                list.add(result);
            }
            
            //�R���N�V��������z��֕ϊ�
            TenpuFileInfo[] resultArray = (TenpuFileInfo[])list.toArray(new TenpuFileInfo[0]);
            
            //�Y�����R�[�h�����݂��Ȃ������ꍇ
            if(resultArray.length == 0){
                throw new NoDataFoundException(
                    "�Y�t�t�@�C���Ǘ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
                        + primaryKeys.getRyoikiSystemNo()
                        + "'");
            }
            
            return resultArray;
            
        } catch (SQLException ex) {
            throw new DataAccessException("�Y�t�t�@�C���Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2006/07/14�@zhangt�@�ǉ������܂�
}
