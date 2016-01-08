/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/07/04
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.impl.LabelValueMaintenance;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���x���}�X�^�}�X�^�f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: MasterLabelInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class MasterLabelInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterLabelInfoDao.class);

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
	 * @param userInfo		���s���郆�[�U���
	 */
	public MasterLabelInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	
	/**
	 * ���x���}�X�^�̂P���R�[�h��Map�`���ŕԂ��B
	 * �����ɂ͎�L�[�l��n���B
	 * @param connection
	 * @param labelKubun
	 * @param value
	 * @return
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, String labelKubun, String value)
		throws NoDataFoundException, DataAccessException
	{
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		StringBuffer query = new StringBuffer("SELECT");
		query.append("  A.ATAI");
		query.append(", A.NAME");
		query.append(", A.RYAKU");
		query.append(", A.SORT");
		query.append(", A.BIKO");
		query.append(" FROM MASTER_LABEL A");
		query.append(" WHERE A.LABEL_KUBUN = ? AND A.ATAI = ?");
		query.append(" ORDER BY SORT");

		//2005.10.26 iso ����������񂪏o�Ȃ��̂ŃR�����g��
//		if(log.isDebugEnabled()){
//			log.debug("query:" + query);
//		}
		
		//-----------------------
		// ���R�[�h�擾
		//-----------------------
		List result = SelectUtil.select(connection,
										query.toString(), 
										new String[]{labelKubun,value});
		if(result.isEmpty()){
			String msg = "���Y���R�[�h�͑��݂��܂���B���x���敪="+labelKubun+", �l="+ value;
			throw new NoDataFoundException(msg);
		}
		return (Map)result.get(0);
		
	}
	
	/**
	 * ���x�����̈ꗗ���擾����B�i�v���_�E���j
	 * @param	connection			�R�l�N�V����
	 * @param	labelKubun			���x���敪���
	 * @return						���x�����
	 * @throws ApplicationException
	 */
	public static List selectLabelInfoList(Connection connection, String labelKubun)
		throws ApplicationException {
		return selectLabelInfoList(connection, labelKubun, LabelValueMaintenance.FLAG_KENSAKU_LIST);
	}


	/**
	 * ���x�����̈ꗗ���擾����B�i�S���j
	 * @param	connection			�R�l�N�V����
	 * @param	labelKubun			���x���敪���
	 * @return						���x�����
	 * @throws ApplicationException
	 */
	public static List selectAllLabelInfoList(Connection connection, String labelKubun)
		throws ApplicationException {
		return selectLabelInfoList(connection, labelKubun, LabelValueMaintenance.FLAG_KENSAKU_ALL);
	}

	
	/**
	 * ���x�����̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @param	labelKubun			���x���敪���
	 * @param	kensakuFlg			�����t���O�B
	 * @return						���x�����
	 * @throws ApplicationException
	 */
	public static List selectLabelInfoList(Connection connection, String labelKubun, String kensakuFlg)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		StringBuffer query = new StringBuffer("SELECT");
		query.append("  A.ATAI");
		query.append(", A.NAME");
		query.append(", A.RYAKU");
		query.append(", A.SORT");
		query.append(", A.BIKO");
		query.append(" FROM MASTER_LABEL A");
		query.append(" WHERE A.LABEL_KUBUN = ?");
		if((LabelValueMaintenance.FLAG_KENSAKU_LIST).equals(kensakuFlg)){
			query.append(" AND A.SORT != 0");	
		}
		query.append(" ORDER BY SORT");

		//2005.10.26 iso ����������񂪏o�Ȃ��̂ŃR�����g��
//		if(log.isDebugEnabled()){
//			log.debug("query:" + query);
//		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			return SelectUtil.select(connection,query.toString(), new String[]{labelKubun});
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���x����񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw e;
		}
	}

}
