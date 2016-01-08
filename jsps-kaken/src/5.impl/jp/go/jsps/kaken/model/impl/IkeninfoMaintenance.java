/*======================================================================
 *    SYSTEM      : 
 *    Source name : IIkeninfoMaintenance.java
 *    Description : �ӌ��E�v�]���ɍX�V���������N���X
 *
 *    Author      : Admin
 *    Date        : 2005/05/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *�@�@2005/05/20    1.0         Xiang Emin     �V�K�쐬
 *
 *====================================================================== 
 */package jp.go.jsps.kaken.model.impl;

import java.sql.Connection;
import java.util.List;
//import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.IIkeninfoMaintenance;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.impl.IkenInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.IkenInfo;
import jp.go.jsps.kaken.model.vo.IkenSearchInfo;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;

/**
 * @author user1
 * 
 * TODO ���̐������ꂽ�^�R�����g�̃e���v���[�g��ύX����ɂ͎����Q�ƁB �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� ��
 * �R�[�h�E�e���v���[�g
 */
public class IkeninfoMaintenance implements IIkeninfoMaintenance {
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(ShinseiMaintenance.class);

	/** �V�X�e����t�ԍ��擾���g���C�� */
	protected static final int SYSTEM_NO_MAX_RETRY_COUNT = 
					ApplicationSettings.getInt(ISettingKeys.SYSTEM_NO_MAX_RETRY_COUNT);

	/**
	 *  
	 */
	public IkeninfoMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------

	/*
	 * ���ӌ��f�[�^�V�K�o�^����
	 * 
	 * @see jp.go.jsps.kaken.model.IIkeninfoMaintenance#insert(jp.go.jsps.kaken.model.vo.IkenInfo)
	 */
	public void insert(IkenInfo addInfo) throws ApplicationException
	{

		Connection connection = null;
		boolean success = false;

		if ( log.isDebugEnabled() ){
			log.debug("���ӌ����o�^�J�n");
		}
		
		//--------------------
		// ���ӌ��E�v�]�f�[�^�o�^
		//--------------------
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();

			IkenInfoDao dao = new IkenInfoDao();
			//-- �o�^���ɃL�[���d�Ȃ����ꍇ�̓��g���C�������� --
			int count = 0;
			while (true) {
				try {
					dao.insertIkenInfo(connection, addInfo);
					success = true;
					break;
				} catch (DuplicateKeyException e) {
					count++;
					if (count < SYSTEM_NO_MAX_RETRY_COUNT) {
						if ( log.isDebugEnabled() ){
							log.debug("���ӌ����o�^�ɑ�" + count + "�񎸔s���܂����B");
						}
						//dataInfo.setSystemNo(getSystemNumber());
						// //�V�X�e����t�ԍ����Ď擾
						continue;
					} else {
						throw e;
					}
				}
			}
		}
		catch (DataAccessException e) {

			throw new ApplicationException("���ӌ��E�v�]���o�^����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"), e);
		}
		finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("���ӌ��E�v�]���o�^����DB�G���[���������܂����B",
						new ErrorInfo("errors.4001"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}

	}

	/**
	 * �ӌ�������������B
	 * @param searchInfo           �ӌ������������
	 * @return						�擾�����\�����y�[�W�I�u�W�F�N�g
	 * @throws ApplicationException	
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public Page searchIken(IkenSearchInfo searchInfo) 
		throws NoDataFoundException, ApplicationException
	{
		//DB�R�l�N�V�����̎擾
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---�\�����ꗗ�y�[�W���
			Page pageInfo = null;
			try {
				IkenInfoDao dao = new IkenInfoDao();
				pageInfo = dao.searchIkenInfo(connection, searchInfo);	//�Y�����R�[�h��S���擾
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"���ӌ���񌟍�����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			return pageInfo;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}
	
	/**
	 * �ӌ����𒊏o����B
	 * @param system_no �V�X�e����t�ԍ�
	 * @param taisho_id �Ώێ҂h�c
	 * @return IkenInfo �ӌ����
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public IkenInfo selectIkenDataInfo(String system_no, String taisho_id)
    	throws NoDataFoundException, ApplicationException
	{

		//DB�R�l�N�V�����̎擾
		Connection connection = DatabaseUtil.getConnection();
		
		try{
			//---�ӌ����e���
			IkenInfo ikenDataInfo = null;

			try {
				IkenInfoDao dao = new IkenInfoDao();
				ikenDataInfo = dao.getIkenInfo(connection, system_no, taisho_id);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�ӌ����e��񌟍�����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
	
			return ikenDataInfo;
			
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}

	/**
	 * CSV�o�͗p�̈ӌ�������������B
	 * 
	 * @param searchInfo			��������������B
	 * @return						�擾�����ӌ����
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	public List searchCsvData(IkenSearchInfo searchInfo)
			throws ApplicationException
	{

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			 "SELECT "
				+ "  SYSTEM_NO \"�V�X�e����t�ԍ�\""
				+ ", TO_CHAR(SAKUSEI_DATE,'YYYY/MM/DD') \"���e��\""
				//+ ", TAISHO_ID \"�Ώێ�\"" 2005/6/29�C��
				+ ", DECODE(TAISHO_ID,1,'�����', 2,'���������@�֒S����', 4,'�R����', 6,'���ǒS����') \"�Ώێ�\""
				+ ", IKEN \"���ӌ����e\""
				+ ", BIKO \"���l\""
			+ " FROM IKEN_INFO"
			+ " WHERE 1 = 1"
			;

		StringBuffer query = new StringBuffer(select);

		//��������������SQL���𐶐�����B
		//���e���J�n
		String tmpDate = searchInfo.getSakuseiDateFrom();
		if ( tmpDate != null && !"".equals( tmpDate )){
			query.append(" AND SAKUSEI_DATE >= TO_DATE('" + EscapeUtil.toSqlString(searchInfo.getSakuseiDateFrom()) + "','YYYY/MM/DD')");
		}
		
		//���e���I��
		tmpDate = searchInfo.getSakuseiDateTo();
		if ( tmpDate != null && !"".equals(tmpDate)){
			query.append(" AND SAKUSEI_DATE < TO_DATE('" + EscapeUtil.toSqlString(searchInfo.getSakuseiDateTo()) + "','YYYY/MM/DD')+1");
		}
		
		//�Ώێ҂h�c
		if (!"".equals(searchInfo.getShinseisya()) || !"".equals(searchInfo.getSyozoku()) ||
			!"".equals(searchInfo.getBukyoku()) || !"".equals(searchInfo.getShinsyain()) )
		{

			query.append(" AND TAISHO_ID IN (");

			int flg = 0;
			if (!"".equals(searchInfo.getShinseisya())){
				query.append( "1" );
				flg = 1;
			}
			
			if (!"".equals(searchInfo.getSyozoku())){
				if (flg != 0){
					query.append( ",2" );
				}else{
					query.append( "2" );
					flg = 1;
				}
			}

			if (!"".equals(searchInfo.getBukyoku())){
				if (flg != 0){
					query.append( ",6" );
				}else{
					query.append( "6" );
					flg = 1;
				}
			}

			if (!"".equals(searchInfo.getShinsyain())){
				if (flg != 0){
					query.append( ",4" );
				}else{
					query.append( "4" );
					flg = 1;
				}
			}
			query.append( ")" );
		}
		
		
		//�\�[�g���𐶐�����B
		if ( "1".equals(searchInfo.getDispmode()) ){
			query.append( " ORDER BY TAISHO_ID, SAKUSEI_DATE" );
		}else{
			query.append( " ORDER BY SAKUSEI_DATE, TAISHO_ID" );
		}
	
		if(log.isDebugEnabled()){
			log.debug("�ӌ�query:" + query);
		}

		//-----------------------
		// ���X�g�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectCsvList(connection, query.toString(), true);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�ӌ����CSV�o�̓f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
}