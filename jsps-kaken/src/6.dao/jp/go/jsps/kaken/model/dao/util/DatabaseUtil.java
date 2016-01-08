/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.util;

import java.io.StringReader;
import java.sql.*;
import java.util.Date;

import javax.sql.*;

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.util.*;
import oracle.jdbc.pool.*;

import org.apache.commons.logging.*;

/**
 * �f�[�^�x�[�X�֘A�̃��[�e�B���e�B�N���X�B
 * 
 * ID RCSfile="$RCSfile: DatabaseUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:08:01 $"
 * 
 */
public class DatabaseUtil {

	/**  ���O  */
	private static Log log = LogFactory.getLog(DatabaseUtil.class);

	/**
	 * �w�肳�ꂽ�p�����[�^�ɕ�������Z�b�g����B
	 * @param statement		�p�����[�^���Z�b�g����X�e�[�g�����g
	 * @param i				�ŏ��̃p�����[�^�� 1�A2 �Ԗڂ̃p�����[�^�� 2�A�ȂǂƂ���
	 * @param value			�p�����[�^�l
	 * @throws SQLException	�f�[�^�x�[�X�A�N�Z�X�G���[�����������ꍇ
	 */
	public static void setParameter(
		PreparedStatement statement,
		int i,
		String value)
		throws SQLException {

		if (value == null) {
			statement.setString(i, null);
		//2005.11.09 iso 666�����𒴂��Ă��ꍇ�́AsetCharacterStream���g��
		} else if(value.length() > 666) {
			setParameter666Over(statement, i,value);
		} else {
			value = value.trim().replaceAll("[\r\n]", "");	//�󔒁A���s�R�[�h���폜����
			statement.setString(i, value);
		}
	}

	//2005.11.09 iso 666���������ɑΉ�
	/**
	 * �w�肳�ꂽ�p�����[�^�ɕ�������Z�b�g����B
	 * 666�����𒴂��Ă�ꍇ�ɑΉ��B
	 * @param statement		�p�����[�^���Z�b�g����X�e�[�g�����g
	 * @param i				�ŏ��̃p�����[�^�� 1�A2 �Ԗڂ̃p�����[�^�� 2�A�ȂǂƂ���
	 * @param value			�p�����[�^�l
	 * @throws SQLException	�f�[�^�x�[�X�A�N�Z�X�G���[�����������ꍇ
	 */
	public static void setParameter666Over(
			PreparedStatement statement,
			int i,
			String value)
			throws SQLException {

		if (value == null) {
			statement.setString(i, null);
		} else {
			value = value.trim().replaceAll("[\r\n]", "");	//�󔒁A���s�R�[�h���폜����
			StringReader sReader = new StringReader(value);
			statement.setCharacterStream(i, sReader, value.length());
		}
	}

	/**
	 * �w�肳�ꂽ�p�����[�^��int�l���Z�b�g����B
	 * @param statement		�p�����[�^���Z�b�g����X�e�[�g�����g
	 * @param i				�ŏ��̃p�����[�^�� 1�A2 �Ԗڂ̃p�����[�^�� 2�A�ȂǂƂ���
	 * @param value			�p�����[�^�l
	 * @throws SQLException	�f�[�^�x�[�X�A�N�Z�X�G���[�����������ꍇ
	 */
	public static void setParameter(
		PreparedStatement statement,
		int i,
		int value)
		throws SQLException {
		statement.setInt(i, value);
	}

	/**
	 * �w�肳�ꂽ�p�����[�^��int�l���Z�b�g����B
	 * @param statement		�p�����[�^���Z�b�g����X�e�[�g�����g
	 * @param i				�ŏ��̃p�����[�^�� 1�A2 �Ԗڂ̃p�����[�^�� 2�A�ȂǂƂ���
	 * @param value			�p�����[�^�l
	 * @throws SQLException	�f�[�^�x�[�X�A�N�Z�X�G���[�����������ꍇ
	 */
	public static void setParameter(
		PreparedStatement statement,
		int i,
		Integer value)
		throws SQLException {
		statement.setInt(i, value.intValue());
	}

	/**
	 * �w�肳�ꂽ�p�����[�^��long�l���Z�b�g����B
	 * @param statement		�p�����[�^���Z�b�g����X�e�[�g�����g
	 * @param i				�ŏ��̃p�����[�^�� 1�A2 �Ԗڂ̃p�����[�^�� 2�A�ȂǂƂ���
	 * @param value			�p�����[�^�l
	 * @throws SQLException	�f�[�^�x�[�X�A�N�Z�X�G���[�����������ꍇ
	 */
	public static void setParameter(
		PreparedStatement statement,
		int i,
		Long value)
		throws SQLException {
		statement.setLong(i, value.longValue());
	}

	/**
	 * �w�肳�ꂽ�p�����[�^�ɓ��t�l���Z�b�g����B
	 * @param statement		�p�����[�^���Z�b�g����X�e�[�g�����g
	 * @param i				�ŏ��̃p�����[�^�� 1�A2 �Ԗڂ̃p�����[�^�� 2�A�ȂǂƂ���
	 * @param value			�p�����[�^�l
	 * @throws SQLException	�f�[�^�x�[�X�A�N�Z�X�G���[�����������ꍇ
	 */
	public static void setParameter(
		PreparedStatement statement,
		int i,
		Date value)
		throws SQLException {

//JDBC�h���C�o�ɂ���ẮA�������Date�^��n���Ȃ��ƃG���[�ƂȂ�B(Oracle��OK, Weblogic��NG�j
//			if (value == null) {
//				statement.setString(i, null);
//			} else {
//				statement.setString(
//					i,
//					new SimpleDateFormat("yyyy/MM/dd").format(value));
//			}
			if(value == null){
				statement.setDate(i,null);
			}else{
				Date utilDate = new DateUtil(value).getDateYYYYMMDD();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				statement.setDate(i,sqlDate);
			}
	}

	/**
	 * �f�[�^�x�[�X�R�l�N�V�������N���[�Y���܂� 
	 * 
	 * @param connection �f�[�^�x�[�X�R�l�N�V����
	 */
	public static void closeConnection(Connection connection) {
		try {
			if (connection == null) {
				return;
			}
			connection.close();
		} catch (SQLException e) {
			log.error("�R�l�N�V�����̃N���[�Y�Ɏ��s���܂����B", e);
			throw new SystemException("�R�l�N�V�����̃N���[�Y�Ɏ��s���܂����B", e);
		}
	}

	/**
	 * �������N���[�Y����B
	 *
	 * @param preparedStatement DML�I�u�W�F�N�g
	 * @param resultSet ���ʃI�u�W�F�N�g
	 */
	public static void closeResource(
		ResultSet resultSet,
		PreparedStatement preparedStatement)
	{
		Exception ex = null;
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			log.error("���ʃI�u�W�F�N�g�̃N���[�Y�Ɏ��s���܂����B", e);
			ex = e;
			//throw new SystemException("���ʃI�u�W�F�N�g�̃N���[�Y�Ɏ��s���܂����B", e);
		}
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			log.error("�X�e�[�g�����g�̃N���[�Y�Ɏ��s���܂����B", e);
			ex = e;
			//throw new SystemException("�X�e�[�g�����g�̃N���[�Y�Ɏ��s���܂����B", e);
		}
		
		if(ex != null){
			String msg = "���ʃI�u�W�F�N�g�܂��̓X�e�[�g�����g�̃N���[�Y�Ɏ��s���܂����B";
			throw new SystemException(msg, ex);
		}
		
	}

	/**
	 * �R�l�N�V�������擾����B
	 * @return	�R�l�N�V�����B
	 */
	public static Connection getConnection() throws SystemBusyException {
		try {
			DataSource dataSource = DataSourceFactory.getDataSouce();
			if(log.isDebugEnabled()){
				if(dataSource instanceof OracleConnectionCacheImpl){
					log.debug("Active size : " + ((OracleConnectionCacheImpl)dataSource).getActiveSize());
					log.debug("Cache Size is " + ((OracleConnectionCacheImpl)dataSource).getCacheSize());
				}	
			}
			Connection connection = null;
			try{
				connection = dataSource.getConnection();
				if(connection == null){
					throw new SystemBusyException("�R�l�N�V�����̎擾�Ɏ��s���܂����B");
				}
			}catch(SQLException e){
				//weblogic�̏ꍇ�̓R�l�N�V�������𒴂����Ƃ��ɗ�O����������
				throw new SystemBusyException("�R�l�N�V�����̎擾�Ɏ��s���܂����B", e);
			}
			checkForWarning(connection.getWarnings());
			connection.setAutoCommit(false);
			return connection;
		} catch (SQLException e) {
			log.error("�R�l�N�V�����̎擾�Ɏ��s���܂����B", e);
			throw new SystemException("�R�l�N�V�����̎擾�Ɏ��s���܂����B", e);
		}
	}
	
	/**
	 * �R�l�N�V�����̏󋵂��擾����B
	 * @throws SystemBusyException
	 */
	public static void checkStatus() throws SystemBusyException {
		DataSource dataSource = DataSourceFactory.getDataSouce();
		if(log.isDebugEnabled()){
			log.debug("Active size : " + ((OracleConnectionCacheImpl)dataSource).getActiveSize());
			log.debug("Cache Size is " + ((OracleConnectionCacheImpl)dataSource).getCacheSize());
		}
	}	

	/**
	 * �R�~�b�g����B
	 * @param connection				�R�~�b�g�Ώۂ̃R�l�N�V����
	 * @throws TransactionException		�R�~�b�g�Ɏ��s�����ꍇ�B
	 */
	public static void commit(Connection connection)
		throws TransactionException {
		try {
			if (connection != null) {
				//�R�~�b�g
				connection.commit();
			}
		} catch (SQLException ex) {
			log.error("�R�~�b�g�Ɏ��s���܂����B", ex);
			throw new TransactionException("�R�~�b�g�Ɏ��s���܂����B", ex);
		}
	}
	

	/**
	 * ���[���o�b�N����B
	 * @param connection				���[���o�b�N�Ώۂ̃R�l�N�V����
	 * @throws TransactionException		���[���o�b�N�Ɏ��s�����ꍇ�B
	 */
	public static void rollback(Connection connection)
		throws TransactionException {
		try {
			if (connection != null) {
				connection.rollback();
			}
		} catch (SQLException ex) {
			log.error("���[���o�b�N�Ɏ��s���܂����B", ex);
			throw new TransactionException("���[���o�b�N�Ɏ��s���܂����B", ex);
		}
	}

	// Format and print any warnings from the connection
	private static void checkForWarning(SQLWarning warn) throws SQLException {
		// If a SQLWarning object was given, display the
		// warning messages.  Note that there could be
		// multiple warnings chained together

		if (warn != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("*** Warning ***\n");
			while (warn != null) {
				buffer.append("SQLState: " + warn.getSQLState());
				buffer.append("Message:  " + warn.getMessage());
				buffer.append("Vendor:   " + warn.getErrorCode());
				buffer.append("");
				warn = warn.getNextWarning();
			}
			log.info(buffer);
		}
	}
	
	/**
	 * Update�����s����B�X�V�������`�F�b�N����B
	 * @param statement
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public static void executeUpdate(PreparedStatement statement)
		throws SQLException, DataAccessException {
		int resultCount = statement.executeUpdate();
		if (resultCount != 1) {
			if (log.isErrorEnabled()) {
				log.error("�X�V�������s���ł��B�X�V����'" + resultCount + "'���B");
			}
			throw new DataAccessException(
				"�X�V�Ɏ��s���܂����B�X�V����'" + resultCount + "'���B");
		}
	}
	
}
