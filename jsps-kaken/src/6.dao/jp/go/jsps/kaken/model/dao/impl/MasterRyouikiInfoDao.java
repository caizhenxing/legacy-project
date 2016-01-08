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

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.EscapeUtil;

import org.apache.commons.logging.*;

/**
 * �̈�}�X�^�f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: MasterRyouikiInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public class MasterRyouikiInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterRyouikiInfoDao.class);

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
	public MasterRyouikiInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �̈�̈ꗗ(�R���|�{�b�N�X�p)���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						���Ə��
	 * @throws ApplicationException
	 */
	public static List selectRyouikiKubunInfoList(Connection connection)
		throws ApplicationException,NoDataFoundException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.RYOIKI_NO"
			+ ",A.RYOIKI_RYAKU"
			+ " FROM MASTER_RYOIKI A"
			+ " ORDER BY RYOIKI_NO";		
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
				"�̈��񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new NoDataFoundException(
				"�̈�}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}
	

	/**
	 * �̈�}�X�^�̂P���R�[�h��Map�`���ŕԂ��B
	 * �����ɂ͎�L�[�l��n���B
	 * @param connection
	 * @param labelKubun
	 * @param value
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, String ryouikiNo)
		throws NoDataFoundException, DataAccessException
	{
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.RYOIKI_NO"
			+ ",A.RYOIKI_RYAKU"
			+ ",A.KOMOKU_NO"
//2006/06/26 �c�@�C����������
            + ",A.SETTEI_KIKAN"  //�ݒ����
            + ",A.SETTEI_KIKAN_KAISHI" //�ݒ���ԁi�J�n�N�x�j
            + ",A.SETTEI_KIKAN_SHURYO" //�ݒ���ԁi�I���N�x�j
//2006/06/26 �c�@�C�������܂�            
			+ ",A.BIKO"
			+ " FROM MASTER_RYOIKI A"
			+ " WHERE RYOIKI_NO = ? "
			;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// ���R�[�h�擾
		//-----------------------
		List result = SelectUtil.select(connection,
										select, 
										new String[]{ryouikiNo});
		if(result.isEmpty()){
			throw new NoDataFoundException(
				"���Y���R�[�h�͑��݂��܂���B�̈�No="+ryouikiNo);
		}
		return (Map)result.get(0);
		
	}

	/**
	 * �̈�}�X�^�̂P���R�[�h��Map�`���ŕԂ��B
	 * �����ɂ͎�L�[�l��n���B
	 * @param connection
	 * @param labelKubun
	 * @param value
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, RyouikiInfoPk pkInfo)
		throws NoDataFoundException, DataAccessException
	{
		return selectRecord(connection, pkInfo, "0");
	}
	
	/**
	 * �̈�}�X�^�̂P���R�[�h��Map�`���ŕԂ��B
	 * �����ɂ͎�L�[�l��n���B
	 * @param connection
	 * @param labelKubun
	 * @param value
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, RyouikiInfoPk pkInfo, String ryoikiKbn)
		throws NoDataFoundException, DataAccessException
	{
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.RYOIKI_NO"
			+ ",A.RYOIKI_RYAKU"
			+ ",A.KOMOKU_NO"
//2006/07/04 �c�@�C����������
            + ",A.SETTEI_KIKAN"  //�ݒ����
            + ",A.SETTEI_KIKAN_KAISHI" //�ݒ���ԁi�J�n�N�x�j
            + ",A.SETTEI_KIKAN_SHURYO" //�ݒ���ԁi�I���N�x�j
//2006/07/04 �c�@�C�������܂�              
			+ " FROM MASTER_RYOIKI A"
			+ " WHERE RYOIKI_NO = ? "
			+ " AND KOMOKU_NO = ? "
			;

		//�v�挤���̏ꍇ
		if ("1".equals(ryoikiKbn)){
			select = select + " AND KEIKAKU_FLG = '1'";
		}
		//���匤���̏ꍇ
		else if ("2".equals(ryoikiKbn)){
			select = select + " AND KOUBO_FLG = '1'";
		}
		
		
		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// ���R�[�h�擾
		//-----------------------
		List result = SelectUtil.select(connection,
										select, 
										new String[]{pkInfo.getRyoikiNo(), pkInfo.getKomokuNo() });
		if(result.isEmpty()){
			throw new NoDataFoundException("���Y���R�[�h�͑��݂��܂���B");
		}
		return (Map)result.get(0);
		
	}
	
	/**
	 * �̈����o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����L�[���[�h���
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertRyoikiInfo(
		Connection connection,
		RyouikiInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//�d���`�F�b�N
		try {
			selectRecord(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
			
		String query =
			"INSERT INTO MASTER_RYOIKI "
				+ "("
				+ " RYOIKI_NO"			//�̈�ԍ�
				+ ",RYOIKI_RYAKU"		//�̈旪�̖�
				+ ",KOMOKU_NO"			//�������ڔԍ�
				+ ",KOUBO_FLG"			//����t���O
				+ ",KEIKAKU_FLG"		//�v�挤���t���O
                //add start liuyi 2006/06/30
                + ",ZENNENDO_OUBO_FLG"  //�O�N�x����t���O
                + ",SETTEI_KIKAN_KAISHI"//�ݒ���ԁi�J�n�N�x�j
                + ",SETTEI_KIKAN_SHURYO"//�ݒ���ԁi�I���N�x�j
                + ",SETTEI_KIKAN"       //�ݒ����
                //add end liuyi 2006/06/30
				+ ",BIKO"				//���l
				+ ")"
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRyoikiNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRyoikiName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKomokuNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKobou());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKeikaku());
            //add start liuyi 2006/06/30
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getZennendoOuboFlg());
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSettelKikanKaishi());
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSettelKikanShuryo());
            DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSettelKikan());
            //add end liuyi 2006/06/30
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());

			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("�̈�}�X�^���o�^���ɗ�O���������܂����B ", ex);
			throw new DataAccessException("�̈�}�X�^���o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * �R�[�h�ꗗ�쐬�p���\�b�h�B<br>
	 * �̈�ԍ��Ɨ̈於�̂̈ꗗ���擾����B
	 * �̈�ԍ����Ƀ\�[�g����B
	 * @param	connection			�R�l�N�V����
	 * @return
	 * @throws ApplicationException
	 */
	public static List selectRyoikiInfoList(Connection connection, String kubun)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =	"SELECT"
						+ " RYOIKI_NO,"				//�̈�ԍ�
						+ " RYOIKI_RYAKU"			//�̈於��
						+ " FROM MASTER_RYOIKI";

		if ("1".equals(kubun)){
			select = select + " WHERE KEIKAKU_FLG = '1'";
		}else{
			select = select + " WHERE KOUBO_FLG = '1'";
		}
		select = select	+ " GROUP BY RYOIKI_NO, RYOIKI_RYAKU"
						+ " ORDER BY RYOIKI_NO";								

		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			return SelectUtil.select(connection, select);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�̈��񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�̈�}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}
//2006/06/26 �c�@�ǉ���������
    /**
     * �̈�ԍ��̌������擾����B
     * �̈�ԍ����Ƀ\�[�g����B
     * @param   connection    �R�l�N�V����
     * @param   ryoikoNo      �̈�ԍ�      
     * @return
     * @throws ApplicationException
     * @throws DataAccessException 
     */
    public String selectRyoikiNoCount(Connection connection, String ryoikoNo)
        throws ApplicationException, DataAccessException {
        
        String              strCount            =   null;
        ResultSet           recordSet           =   null;
        PreparedStatement   preparedStatement   =   null;
        
        //-----------------------
        // SQL���̍쐬
        //-----------------------
        
        StringBuffer select =  new StringBuffer();
        select.append("SELECT COUNT(RYOIKI_NO) ");
        select.append(ISystemServise.STR_COUNT);
        select.append(" FROM");
        select.append(" (SELECT MR.RYOIKI_NO FROM MASTER_RYOIKI MR WHERE MR.ZENNENDO_OUBO_FLG = '1' ");
        select.append("  AND MR.RYOIKI_NO = '");
        select.append(EscapeUtil.toSqlString(ryoikoNo));
        select.append("')");                           

        if(log.isDebugEnabled()){
            log.debug("query:" + select.toString());
        }
        try{
            preparedStatement = connection.prepareStatement(select.toString());
            recordSet = preparedStatement.executeQuery();
            
            if(recordSet.next()){
                strCount =recordSet.getString(ISystemServise.STR_COUNT);
            }else{
                throw new NoDataFoundException("�̈�}�X�^�f�[�^�e�[�u���ɊY������f�[�^��������܂���B");
            }
        }catch (SQLException ex) {
            throw new DataAccessException("�̈�}�X�^�f�[�^�e�[�u���̌������ɗ�O���������܂����B",ex);
        } catch(NoDataFoundException ex){
            throw new NoDataFoundException("�Y������̈�ԍ������݂��܂���B",     ex);
        }   
 
        return strCount;    
    }
//2006/06/26�@�c�@�ǉ������܂�    
//2006/07/24�@�c�@�ǉ���������
    /**
     * �R�[�h�ꗗ�i�V�K�̈�j�쐬�p���\�b�h�B<br>
     * �̈�ԍ��Ɨ̈於�̂̈ꗗ���擾����B
     * �̈�ԍ����Ƀ\�[�g����B
     * @param   connection          �R�l�N�V����
     * @return�@�@List
     * @throws ApplicationException
     */
    public static List selectRyoikiSinnkiInfoList(Connection connection)
        throws ApplicationException {

        //-----------------------
        // SQL���̍쐬
        //-----------------------
        StringBuffer select = new StringBuffer();
            
        select.append("SELECT DISTINCT");
        select.append(" RYOIKI_NO,");//�̈�ԍ�
        select.append(" RYOIKI_RYAKU,");//�̈於��
        select.append(" SETTEI_KIKAN");//�ݒ����
        select.append(" FROM MASTER_RYOIKI");
        select.append(" WHERE ZENNENDO_OUBO_FLG = '1'");
        select.append(" ORDER BY RYOIKI_NO");

        if(log.isDebugEnabled()){
            log.debug("query:" + select);
        }
        
        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            return SelectUtil.select(connection, select.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException(
                "�̈��񌟍�����DB�G���[���������܂����B",
                new ErrorInfo("errors.4004"),
                e);
        } catch (NoDataFoundException e) {
            throw new SystemException(
                "�̈�}�X�^��1�����f�[�^������܂���B",
                e);
        }
    }
//2006/07/24�@�c�@�ǉ������܂�    
}
