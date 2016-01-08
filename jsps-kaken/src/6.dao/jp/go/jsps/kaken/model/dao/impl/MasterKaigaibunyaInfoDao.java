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

import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;

import org.apache.commons.logging.*;

/**
 * �C�O����}�X�^�f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: MasterKaigaibunyaInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public class MasterKaigaibunyaInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterKaigaibunyaInfoDao.class);

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
	public MasterKaigaibunyaInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * �C�O������̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						�C�O������
	 * @throws ApplicationException
	 */
	public static List selectKaigaibunyaList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.KAIGAIBUNYA_CD"
			+ ",A.KAIGAIBUNYA_NAME"
			+ ",A.KAIGAIBUNYA_NAME_RYAKU"
			+ ",A.BIKO"					//���l
			+ " FROM MASTER_KAIGAIBUNYA A"
			+ " ORDER BY A.KAIGAIBUNYA_CD";								
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
				"�C�O����}�X�^��񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"�C�O����}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}
	
	
	
	/**
	 * �C�O����}�X�^�̂P���R�[�h��Map�`���ŕԂ��B
	 * �����ɂ͎�L�[�l��n���B
	 * @param connection
	 * @param pk
	 * @param value
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, String pk)
		throws NoDataFoundException, DataAccessException
	{
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.KAIGAIBUNYA_CD"
			+ ",A.KAIGAIBUNYA_NAME"
			+ ",A.KAIGAIBUNYA_NAME_RYAKU"
			+ ",A.BIKO"					//���l
			+ " FROM MASTER_KAIGAIBUNYA A"
			+ " WHERE KAIGAIBUNYA_CD = ? "
			+ " ORDER BY A.KAIGAIBUNYA_CD"
			;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// ���R�[�h�擾
		//-----------------------
		List result = SelectUtil.select(connection,
										select, 
										new String[]{pk});
		if(result.isEmpty()){
			throw new NoDataFoundException(
				"���Y���R�[�h�͑��݂��܂���B�C�O����R�[�h="+pk);
		}
		return (Map)result.get(0);
		
	}
	
	
	/**
	 * �C�O����}�X�^�̃��R�[�h���擾����B
	 * @param connection
	 * @param pk
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static KaigaiBunyaInfo getKaigaiBunyaInfo(Connection connection, String pk)	 throws NoDataFoundException, DataAccessException{
		Map record=selectRecord(connection,pk);
		if(record==null||record.isEmpty()){
			return null;
		}
		KaigaiBunyaInfo kaigaiBunyaInfo=new KaigaiBunyaInfo();
		kaigaiBunyaInfo.setKaigaibunyaCD((String)record.get("KAIGAIBUNYA_CD"));
		kaigaiBunyaInfo.setKaigaibunyaName((String)record.get("KAIGAIBUNYA_NAME"));
		kaigaiBunyaInfo.setKaigaibunyaNameRyaku((String)record.get("KAIGAIBUNYA_NAME_RYAKU"));
		kaigaiBunyaInfo.setBiko((String)record.get("BIKO"));
		
		return kaigaiBunyaInfo;
	}
	

}
