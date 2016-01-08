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
package jp.go.jsps.kaken.model.dao.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *	�f�[�^�x�[�X����̃e���v���[�g�N���X�B
 *
 * ID RCSfile="$RCSfile: JDBCReading.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 * 
 */
public class JDBCReading {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** ���O */
	private static Log log = LogFactory.getLog(JDBCReading.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public JDBCReading() {
		super();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * �������������s����B
	 * @param  connection			�R�l�N�V����
	 * @param  psc					�v���R���p�C�����ꂽSQL���B
	 * @param  rcbh		�����n���h���B
	 * @throws DataAccessException	�f�[�^�x�[�X�֘A�̗�O�B
	 *         NoDataFoundException�@�Ώۃf�[�^��������Ȃ��ꍇ�B  			
	 */
	public void query(Connection connection,PreparedStatementCreator psc, RowCallbackHandler rcbh)
		throws DataAccessException,NoDataFoundException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = psc.createPreparedStatement(connection);
			rs = pstmt.executeQuery();
			//�f�[�^�擾�`�F�b�N�t���O
			boolean isDataFound = false;
			while (rs.next()) {
				isDataFound = true;
				rcbh.processRow(rs);
			}
			//�f�[�^�擾�`�F�b�N
			if (isDataFound == false) {
				throw new NoDataFoundException("�Ώۃf�[�^��������܂���B");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("���������Ɏ��s���܂����B", ex);
		} finally {
			DatabaseUtil.closeResource(rs,pstmt);
		}
	}

	/**
	 * �������������s����B
	 * @param psc					�v���R���p�C�����ꂽSQL���B
	 * @param rcbh					�����n���h���B
	 * @param startPosition			�擾�J�n�s�ԍ�
	 * @param howManyRows			�擾����(0�̂Ƃ��͂��ׂẴf�[�^���擾���܂�)			
	 * @throws DataAccessException	DB�֘A�̃G���[�����������ꍇ�B
	 *         NoDataFoundException�@�Ώۃf�[�^��������Ȃ��ꍇ�B  			
	 */
	public void query(
		Connection connection,
		PreparedStatementCreator psc,
		RowCallbackHandler rcbh,
		int startPosition,
		int howManyRows)
		throws DataAccessException, NoDataFoundException {

		//check
		if (startPosition < 0)
			throw new IllegalArgumentException("startPosition < 0");

		//�y�[�W�T�C�Y��0�̂Ƃ��͂��ׂẴf�[�^��\������B
		if (howManyRows <= 0) {
			this.query(connection,psc, rcbh);
			return;
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = psc.createPreparedStatement(connection);
			rs = pstmt.executeQuery();

			//�w��ʒu�ɃJ�[�\���ړ�
			setStartPosition(startPosition + 1, rs);
			do{
				rcbh.processRow(rs);
			}while (rs.next() && (--howManyRows > 0));
		} catch (SQLException ex) {
			throw new DataAccessException("���������Ɏ��s���܂����B", ex);
		} finally {
			DatabaseUtil.closeResource(rs,pstmt);
		}
	}
	
	/**
	 * �w��ʒu�ɃJ�[�\�����ړ�����B
	 * @param startAtRow		
	 * @param resultSet
	 * @throws SQLException
	 */
	private void setStartPosition(
			int startAtRow, ResultSet resultSet) 
			throws SQLException,NoDataFoundException{
		if (startAtRow > 0) {
			if (resultSet.getType() !=
					ResultSet.TYPE_FORWARD_ONLY) {
				// Move the cursor using JDBC 2.0 API
				if (!resultSet.absolute(startAtRow)) {
					throw new NoDataFoundException("�w��ʒu�ɃJ�[�\�����ړ��ł��܂���B");
				}
			} else {
				//���[�v���Ĉړ�����B
				for (int i=0; i< startAtRow; i++) {
					if (!resultSet.next()) {
						throw new NoDataFoundException("�w��ʒu�ɃJ�[�\�����ړ��ł��܂���B");
					}
				}
			}
		}
	}
}
