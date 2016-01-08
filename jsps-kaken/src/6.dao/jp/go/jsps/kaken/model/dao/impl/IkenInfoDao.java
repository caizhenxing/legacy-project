/*======================================================================
 *    SYSTEM      : 
 *    Source name : IkenInfo.java
 *    Description : �ӌ��E�v�]�����c�a�A�N�Z�X�N���X
 *
 *    Author      : Admin
 *    Date        : 2005/05/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *�@�@2005/05/20    1.0         Xiang Emin     �V�K�쐬
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
//import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.IkenSearchInfo;
import jp.go.jsps.kaken.model.vo.SearchInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.vo.IkenInfo;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;

/**
 * @author user1
 *
 */
public class IkenInfoDao {

	/** ���O */
	protected static final Log log = LogFactory.getLog(IkenInfoDao.class);
	
	/** �ӌ����e  */
	private IkenInfo iken = null;
	
	
	/**
	 * �R���X�g���N�^�B
	 */
	public IkenInfoDao( ) {
	}

	
	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	
	/**
	 * �ӌ����e�o�^
	 * @param connection
	 * @param addInfo
	 * @throws DataAccessException
	 * @throws DuplicateKeyException
	 */
	public void insertIkenInfo(
			Connection connection,
			IkenInfo addInfo)
			throws DataAccessException, DuplicateKeyException {
		
		String query =
			"INSERT INTO IKEN_INFO ("
				+ "  SYSTEM_NO"
				+ ", SAKUSEI_DATE"
				+ ", TAISHO_ID"
				+ ", IKEN"
				+ ", BIKO"
				+ ") VALUES ("
				+ " TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSFF3')"
				+ ", SYSDATE, ?, ?, ? )";
				/*+ ", SYSDATE"
				+ "," + addInfo.getTaisho_id() 
				+ ", '" + addInfo.getIken() + "'"
				+ ", null )";*/
		
		PreparedStatement preparedStatement = null;
		try {
			if (log.isDebugEnabled()){
				log.debug("���ӌ��T�C�Y�F" + addInfo.getIken().length());
			}
			
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTaisho_id());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getIken());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("���ӌ��E���v�]���o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * �w�茟�������ɊY������ӌ��f�[�^���擾����B
	 * @param connection
	 * @param searchInfo
	 * @return
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 * @throws ApplicationException  �\���ҏ�񂪃Z�b�g����Ă��Ȃ������ꍇ
	 */
	public Page searchIkenInfo(
		Connection connection,
		IkenSearchInfo searchInfo)
		throws DataAccessException , NoDataFoundException, ApplicationException
	{

		String select =	"SELECT SYSTEM_NO, TAISHO_ID"
						+ ", DECODE(TAISHO_ID,1,'�����', 2,'���������@�֒S����', 4,'�R����', 6,'���ǒS����') TAISHO_NM"
						+ ", TO_CHAR(SAKUSEI_DATE,'YYYY\"�N\"MM\"��\"DD\"��\"') SAKUSEI_DATE"
						//+ ", IKEN"
						+ " FROM IKEN_INFO";
		String where = "";
		
		//���e���J�n
		String tmpDate = searchInfo.getSakuseiDateFrom();
		if ( tmpDate != null && !"".equals( tmpDate )){
			where = " WHERE SAKUSEI_DATE >= TO_DATE('" + EscapeUtil.toSqlString(searchInfo.getSakuseiDateFrom()) + "','YYYY/MM/DD')";
		}
		
		//���e���I��
		tmpDate = searchInfo.getSakuseiDateTo();
		if ( tmpDate != null && !"".equals(tmpDate)){
			if ("".equals(where)){
				where = " WHERE";
			}else{
				where = where + " AND";
			}
			where = where + " SAKUSEI_DATE < TO_DATE('" + EscapeUtil.toSqlString(searchInfo.getSakuseiDateTo()) + "','YYYY/MM/DD')+1";
		}
		
		//�Ώێ҂h�c
		if (!"".equals(searchInfo.getShinseisya()) || !"".equals(searchInfo.getSyozoku()) ||
			!"".equals(searchInfo.getBukyoku()) || !"".equals(searchInfo.getShinsyain()) ){
			if ("".equals(where)){
				where = " WHERE TAISHO_ID IN (";
			}else{
				where = where + " AND TAISHO_ID IN (";
			}

			int flg = 0;
			if (!"".equals(searchInfo.getShinseisya())){
				where = where + "1";
				flg = 1;
			}
			
			if (!"".equals(searchInfo.getSyozoku())){
				if (flg != 0){
					where = where + ",2";
				}else{
					where = where + "2";
					flg = 1;
				}
			}

			if (!"".equals(searchInfo.getBukyoku())){
				if (flg != 0){
					where = where + ",6";
				}else{
					where = where + "6";
					flg = 1;
				}
			}

			if (!"".equals(searchInfo.getShinsyain())){
				if (flg != 0){
					where = where + ",4";
				}else{
					where = where + "4";
					flg = 1;
				}
			}
			where = where + ")";
		}
		
		
		
		//��������������SQL���𐶐�����B
		String query = select + where;
		
		if ( "1".equals(searchInfo.getDispmode()) ){
			query = query + " ORDER BY TAISHO_ID, SAKUSEI_DATE";
		}else{
			query = query + " ORDER BY SAKUSEI_DATE, TAISHO_ID";
		}
		
		//for debug
		if(log.isDebugEnabled()){
			log.debug("�ӌ����query:" + query);
		}
		
		// �y�[�W�擾
		return SelectUtil.selectPageInfo(connection, (SearchInfo)searchInfo, query);
	}

	/**
	 * �w�茟�������ɊY������ӌ��f�[�^���擾����B
	 * @param connection
	 * @param searchInfo
	 * @return
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 * @throws ApplicationException  �\���ҏ�񂪃Z�b�g����Ă��Ȃ������ꍇ
	 */
	public IkenInfo getIkenInfo(
		Connection connection,
		String system_no,
		String taisho_id)
		throws DataAccessException , NoDataFoundException, ApplicationException
	{

		String select =	" SELECT TO_CHAR(SAKUSEI_DATE,'YYYY\"�N\"MM\"��\"DD\"��\"') SAKUSEI_DATE"
						+ "    , DECODE(TAISHO_ID,1,'�����', 2,'���������@�֒S����', 4,'�R����', 6,'���ǒS����') TAISHO_NM"
						+ "    , IKEN"
						+ " FROM IKEN_INFO "
						+ "WHERE SYSTEM_NO = '" + EscapeUtil.toSqlString(system_no) + "'"
						+ "  AND TAISHO_ID = " + EscapeUtil.toSqlString(taisho_id) ;
		
		//for debug
		if(log.isDebugEnabled()){
			log.debug("�ӌ����query:" + select);
		}

		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			IkenInfo result = new IkenInfo();
			preparedStatement = connection.prepareStatement(select);
			int index = 1;
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				//---��{���i�O���j
				result.setSakusei_date(recordSet.getString("SAKUSEI_DATE"));
				result.setTaisho_nm(recordSet.getString("TAISHO_NM"));
				result.setIken(recordSet.getString("IKEN"));
				result.setSystem_no(system_no);

				return result;
				
			} else {
				throw new NoDataFoundException(
					"�ӌ����e�e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
						+ system_no 
						+ "'");
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�ӌ����e�e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		
	}	
}
