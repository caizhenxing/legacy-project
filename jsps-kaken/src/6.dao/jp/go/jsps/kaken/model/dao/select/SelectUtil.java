/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/05
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.util.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �����p�f�[�^�A�N�Z�X�N���X�B
 * 
 * ID RCSfile="$RCSfile: SelectUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class SelectUtil {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(SelectUtil.class);

	//---------------------------------------------------------------------
	// Public Method
	//---------------------------------------------------------------------
	/**
	 * ����������SQL�Ɋ�Â��A�y�[�W�����擾����B
	 * @param connection			�R�l�N�V����
	 * @param searchInfo			�������
	 * @param query					SQL��
	 * @return						�y�[�W�f�[�^
	 * 								�Ώۃf�[�^��������Ȃ��ꍇ�́APage�I�u�W�F�N�g�̃��X�g����ƂȂ�B
	 * @throws DataAccessException	�f�[�^�x�[�X�A�N�Z�X���̗�O
	 * @throws NoDataFoundException	
	 * @throws RecordCountOutOfBoundsException	
	 */
	public static Page selectPageInfo(Connection connection,SearchInfo searchInfo, final String query)
		throws DataAccessException , NoDataFoundException, RecordCountOutOfBoundsException {
		//�����pSQL�쐬�I�u�W�F�N�g�𐶐�����B		
		PreparedStatementCreator dataCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
				return conn.prepareStatement(
					query.toString(),
					//--------------------------------------------------
					// 2004/12/08 takano 
					// TYPE_SCROLL_INSENSITIVE�̏ꍇ�A
					// �J�[�\���ړ��̌��ʂ���������ɓW�J���Ă��܂�����
					// ��ʌ����i1�����ȏ�H�j�ɂ͌����Ȃ��B
					// TYPE_FORWARD_ONLY�ɕύX����B
					// ���̐�̏����ł͏����ړ��������Ă��Ȃ����ߖ�薳���B
					//--------------------------------------------------
					//ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			}
		};
		//�����擾�p
		PreparedStatementCreator countCerater = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
				return conn.prepareStatement(
					"SELECT COUNT(*) AS COUNT FROM (" + query.toString() + ")");
			}
		};

// 20050629
		try{
			//���������s����B
			return new PageReading(searchInfo,countCerater,dataCreator).search(connection);
		}
		catch(NoDataFoundException e){
			//throw new NoDataFoundException("�Y�������񂪑��݂��܂���ł����B", new ErrorInfo("errors.4004"), e);}
			throw new NoDataFoundException("�Y�������񂪑��݂��܂���ł����B", new ErrorInfo("errors.5002"), e);}
		catch(DataAccessException e){
			throw new DataAccessException("�f�[�^��������DB�G���[���������܂����B");}
		catch(RecordCountOutOfBoundsException e){
			//throw new RecordCountOutOfBoundsException("�Y�������񂪑��݂��܂���ł����B",new ErrorInfo("errors.4004"),e);}
			throw new RecordCountOutOfBoundsException("�Y�������񂪑��݂��܂���ł����B",new ErrorInfo("errors.maxcount"),e);}
		finally{
			//TODO:
		}
// Horikoshi
	}

	/**
	 * ����������SQL�Ɋ�Â��A�y�[�W�����擾����B
	 * @param connection			�R�l�N�V����
	 * @param searchInfo			�������
	 * @param param                PreparedStatement�ɓn�����I�p�����[�^
	 * @param query					SQL��
	 * @return						�y�[�W�f�[�^
	 * 								�Ώۃf�[�^��������Ȃ��ꍇ�́APage�I�u�W�F�N�g�̃��X�g����ƂȂ�B
	 * @throws DataAccessException	�f�[�^�x�[�X�A�N�Z�X���̗�O
	 * @throws NoDataFoundException	
	 * @throws RecordCountOutOfBoundsException	
	 */
	public static Page selectPageInfo(Connection connection,SearchInfo searchInfo, final String query, final String[] param)
		throws DataAccessException , NoDataFoundException, RecordCountOutOfBoundsException {
		//�����pSQL�쐬�I�u�W�F�N�g�𐶐�����B		
		PreparedStatementCreator dataCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
					PreparedStatement pre = conn.prepareStatement(
										query.toString(),
										//ResultSet.TYPE_SCROLL_INSENSITIVE,	//�R�����g���R�͏�̃��\�b�h�Q�ƁB
										ResultSet.TYPE_FORWARD_ONLY,
										ResultSet.CONCUR_READ_ONLY);
					for(int i=1; i<=param.length; i++){
						pre.setString(i, param[i-1]);
					}
				return pre;
			}
		};
		//�����擾�p
		PreparedStatementCreator countCerater = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
					PreparedStatement pre = conn.prepareStatement(
						"SELECT COUNT(*) AS COUNT FROM (" + query.toString() + ")");
					for(int i=1; i<=param.length; i++){
						pre.setString(i, param[i-1]);
					}				
				return pre;
			}
		};
		//���������s����B
		return new PageReading(searchInfo,countCerater,dataCreator).search(connection);
	}

	/**
	 * ����������SQL�Ɋ�Â��A�f�[�^���X�g���擾����B
	 * @param searchInfo			�������
	 * @param query				SQL��
	 * @param param                PreparedStatement�ɓn�����I�p�����[�^
	 * @return						���X�g
	 * @throws DataAccessException	�f�[�^�x�[�X�A�N�Z�X���̗�O
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public static List select(Connection connection,final String query, final String[] param)
		throws DataAccessException , NoDataFoundException{
		//�����pSQL�쐬�I�u�W�F�N�g�𐶐�����B		
		PreparedStatementCreator dataCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
					PreparedStatement pre = conn.prepareStatement(query.toString());
					for(int i=1; i<=param.length; i++){
						pre.setString(i, param[i-1]);
					}
				return pre;
			}
		};
		//���������s����B
		ListCallbackHandler callbackHandler = new ListCallbackHandler();
		new JDBCReading().query(connection,dataCreator,callbackHandler);
		return callbackHandler.getResult();
	}
	
	/**
	 * ����������SQL�Ɋ�Â��A�f�[�^���X�g���擾����B
	 * @param connection			�R�l�N�V����
	 * @param searchInfo			�������
	 * @param query					SQL��
	 * @return						���X�g
	 * @throws DataAccessException	�f�[�^�x�[�X�A�N�Z�X���̗�O
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public static List select(Connection connection, final String query)
		throws DataAccessException , NoDataFoundException{
		//�����pSQL�쐬�I�u�W�F�N�g�𐶐�����B		
		PreparedStatementCreator dataCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
				return conn.prepareStatement(
					query.toString());
			}
		};
		//���������s����B
		ListCallbackHandler callbackHandler = new ListCallbackHandler();
		new JDBCReading().query(connection,dataCreator,callbackHandler);
		return callbackHandler.getResult();
	}

	/**
	 * ����������SQL�Ɋ�Â��ACSV�f�[�^���X�g���擾����B
	 * @param connection			�R�l�N�V����
	 * @param query					SQL��
	 * @param includeHeader			�w�b�_�[�����܂߂�ꍇtrue�ȊOfalse
	 * @return						���X�g
	 * @throws DataAccessException	�f�[�^�x�[�X�A�N�Z�X���̗�O
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public static List selectCsvList(Connection connection,final String query,boolean includeHeader)
		throws DataAccessException , NoDataFoundException{
		//�����pSQL�쐬�I�u�W�F�N�g�𐶐�����B		
		PreparedStatementCreator dataCreator = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
				return conn.prepareStatement(
					query.toString());
			}
		};
		//���������s����B
		CsvListCallbackHandler callbackHandler = new CsvListCallbackHandler();
		//�w�b�_�[�����܂ނ��ǂ���
		callbackHandler.setIncludeHeader(includeHeader);
		new JDBCReading().query(connection,dataCreator,callbackHandler);
		return callbackHandler.getResult();
	}

}
