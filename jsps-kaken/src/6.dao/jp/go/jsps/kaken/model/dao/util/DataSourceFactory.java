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

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import oracle.jdbc.pool.*;

import org.apache.commons.logging.*;

/**
 * �f�[�^�\�[�X�擾�p�t�@�N�g���N���X�B
 * 
 * ID RCSfile="$RCSfile: DataSourceFactory.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:08:00 $"
 */
public class DataSourceFactory {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ���O�N���X�B 
	 */
	private static final Log log = LogFactory.getLog(DataSourceFactory.class);

	/** 
	 * �f�[�^�\�[�X 
	 */
	private static DataSource dataSource = 
								lookupDataSource(ApplicationSettings.getString(ISettingKeys.DB_DATA_SOURCE_TYPE));

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public DataSourceFactory() {
		super();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * �f�[�^�\�[�X���擾����B
	 * @return	�f�[�^�\�[�X�B
	 */
	public static DataSource getDataSouce() {
		return dataSource;
	}
	
	
	/**
	 * �f�[�^�\�[�X���擾����B
	 * dataSourceType���uWebLogic�v�̏ꍇ�AWebLogic�p�̃f�[�^�\�[�X��Ԃ��B
	 * @param dataSourceType
	 * @return
	 */
	private static DataSource lookupDataSource(String dataSourceType){
		//WebLogic�p
		if("WEBLOGIC".equals(dataSourceType.toUpperCase())){
			return settingDataSource4WebLogic();
		//����ȊO
		}else{
			return settingDataSource();
		}
	}
	
	
	
	
	
	/**
	 * �ڑ�������̃f�[�^�\�[�X���擾����B
	 * @param connectURI
	 * @return				�f�[�^�\�[�X
	 */
	private static DataSource settingDataSource() {
		try {
			// Create a OracleConnectionPoolDataSource as an factory
			// of PooledConnections for the Cache to create.
			OracleConnectionPoolDataSource ocpds =
				new OracleConnectionPoolDataSource();
			ocpds.setURL(ApplicationSettings.getString(ISettingKeys.DB_URL));
			ocpds.setUser(ApplicationSettings.getString(ISettingKeys.DB_USER));
			ocpds.setPassword(ApplicationSettings.getString(ISettingKeys.DB_PASSWORD));
			// Associate it with the Cache
			OracleConnectionCacheImpl ods =	new OracleConnectionCacheImpl(ocpds);
			// MIN�R�l�N�V�������̐ݒ�
			ods.setMinLimit(ApplicationSettings.getInteger(ISettingKeys.DB_MIN_LIMIT).intValue());
			// MAX�R�l�N�V�������̐ݒ�
			ods.setMaxLimit(ApplicationSettings.getInteger(ISettingKeys.DB_MAX_LIMIT).intValue());
			//�@�m�F�Ԋu			
			ods.setThreadWakeUpInterval(10);
			//���g�p�̕����ڑ����ێ������ő����
			//ods.setCacheInactivityTimeout(30);
			ods.setCacheInactivityTimeout(0);	//�^�C���A�E�g����
			// �R�l�N�V�����擾�����̐ݒ�
			ods.setCacheScheme(OracleConnectionCacheImpl.FIXED_RETURN_NULL_SCHEME);

			return ods;

		} catch (SQLException e) {
			throw new SystemException("�f�[�^�\�[�X�̎擾�Ɏ��s���܂���", e);
		}
	}
	
	
	
	/**
	 * �ڑ�������̃f�[�^�\�[�X���擾����B�iWebLogic�p�j
	 * @param connectURI
	 * @return				�f�[�^�\�[�X
	 */
	private static DataSource settingDataSource4WebLogic() {
		try {
			//�����R���e�L�X�g���Z�b�g
			Hashtable ht = new Hashtable();
			ht.put(Context.INITIAL_CONTEXT_FACTORY,
				   ApplicationSettings.getString(ISettingKeys.DB_INITIAL_CONTEXT_FACTORY));
			ht.put(Context.PROVIDER_URL, 
			       ApplicationSettings.getString(ISettingKeys.DB_PROVIDER_URL));
			
			//DataSource�̎擾
			Context context = new InitialContext(ht);
			return (DataSource)context.lookup(ApplicationSettings.getString(ISettingKeys.DB_DATA_SOURCE_NAME));

		} catch (Exception e) {
			throw new SystemException("�f�[�^�\�[�X�̎擾�Ɏ��s���܂���", e);
		}
	}
	
	
	
}
