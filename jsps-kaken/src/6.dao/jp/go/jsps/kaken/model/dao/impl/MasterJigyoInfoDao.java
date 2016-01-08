/*======================================================================
 *    SYSTEM      : �d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : MasterJigyoInfoDao
 *    Description : ���ƃ}�X�^�f�[�^�A�N�Z�X�N���X
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

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * ���ƃ}�X�^�f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: MasterJigyoInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:52 $"
 */
public class MasterJigyoInfoDao {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(MasterJigyoInfoDao.class);

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
	public MasterJigyoInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * ���Ə��̈ꗗ���擾����B
	 * @param	connection			�R�l�N�V����
	 * @return						���Ə��
	 * @throws ApplicationException
	 */
	public static List selectJigyoInfoList(Connection connection)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"//���Ɩ�CD
			+ ",A.JIGYO_NAME"//���Ɩ���
			+ " FROM MASTER_JIGYO A"
			+ " ORDER BY JIGYO_CD";		//���ƃR�[�h��						
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
				"���Ə�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"���ƃ}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}

	/**
	 * ���O�C�����[�U�p�̎��Ɩ����X�g��Ԃ��B
	 * �Ɩ��S���҈ȊO�́AselectJigyoInfoList(Connection connection) ��
	 * �������ʂ��Ԃ�B
	 * <li>�Ɩ��S���ҥ������������S�����鎖�Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
	 * <li>�Ɩ��S���҈ȊO����S��</li>
	 * @param connection
	 * @return
	 * @throws ApplicationException
	 */
	public List selectJigyoInfoList4Users(Connection connection)
		throws ApplicationException {

		//��������
		String addQuery = null;
		
		//�Ɩ��S���҂̏ꍇ�͎������S�����鎖�Ƌ敪�̂݁B
		if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
			Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoKubun().iterator();
			String tantoJigyoKubun = StringUtil.changeIterator2CSV(ite,true);
			addQuery = new StringBuffer(" WHERE A.JIGYO_KUBUN IN (")
						 .append(tantoJigyoKubun)
						 .append(")")
						 .toString();
		}else{
			return selectJigyoInfoList(connection);
		}
		
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"//���Ɩ�CD
			+ ",A.JIGYO_NAME"//���Ɩ���
			+ " FROM MASTER_JIGYO A"
			+ addQuery
			+ " ORDER BY JIGYO_CD";		//���ƃR�[�h��						
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
				"���Ə�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"���ƃ}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}

	/**
	 * ���O�C�����[�U�p�̎��Ɩ����X�g��Ԃ��B
	 * �R���Ώۂ̎��Ƌ敪�����Ɩ����X�g�ɒǉ�����B<br>
	 * <li>�Ɩ��S���ҥ������������S�����鎖�Ƌ敪�̂����A�R���Ώۂ̎��Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
	 * <li>�Ɩ��S���҈ȊO����R���Ώۂ̎��Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
	 * @param connection
	 * @return List
	 * @throws ApplicationException
     * @throws NoDataFoundException
	 */
	public List selectShinsaTaishoJigyoInfoList4Users(Connection connection)
		throws ApplicationException, NoDataFoundException {

		//��������
		String addQuery = null;

//delete start dyh 2006/06/06 �����F�g�p���Ȃ�
//		//���Ƌ敪���X�g
//		ArrayList jigyoKubunList = new ArrayList();
//delete end dyh 2006/06/06

		//�S�����Ƌ敪����R���Ώە��̎��Ƌ敪�̂ݎ擾
		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);			
		
		//CSV���擾
		String tantoJigyoKubun = StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(),true);
		addQuery = new StringBuffer(" WHERE A.JIGYO_KUBUN IN (")
					 .append(tantoJigyoKubun)
					 .append(")")
					 .toString();

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"				//���ƃR�[�h
			+ ",A.JIGYO_NAME"			//���Ɩ�
			+ " FROM MASTER_JIGYO A"
			+ addQuery
			+ " ORDER BY JIGYO_CD";		//���ƃR�[�h��						
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
				"���Ə�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw e;
		}
	}	

	/**
	 * �w�肳�ꂽ���Ƌ敪�̎��Ɩ����X�g��Ԃ��B
	 * @param connection
	 * @param jigyoKubun
	 * @return List
	 * @throws ApplicationException
     * @throws NoDataFoundException
	 */
	public List selectJigyoInfoList(Connection connection, String jigyoKubun)
			throws ApplicationException, NoDataFoundException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"				//���ƃR�[�h
			+ ",A.JIGYO_NAME"			//���Ɩ�
			+ " FROM MASTER_JIGYO A"
			+ " WHERE A.JIGYO_KUBUN = '"
			+ EscapeUtil.toSqlString(jigyoKubun)
			+ "' ORDER BY JIGYO_CD";		//���ƃR�[�h��						
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
				"���Ə�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw e;
		}
	}	

	/**
	 * ���Ƌ敪���擾����B
	 * @param connection			�R�l�N�V����
	 * @param jigyoCd				���ƃR�[�h
	 * @return						���Ə��
	 * @throws ApplicationException
     * @throws NoDataFoundException
	 */
	public static String selectJigyoKubun(
		Connection connection,
		String jigyoCd)
		throws ApplicationException, NoDataFoundException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
				+ " NVL(A.JIGYO_KUBUN,0) JIGYO_KUBUN"
				+ " FROM MASTER_JIGYO A"
				+ " WHERE JIGYO_CD = ?";
		StringBuffer query = new StringBuffer(select);

		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
			List result =
				SelectUtil.select(
					connection,
					query.toString(),
					new String[] { jigyoCd });
			return ((Map) result.get(0)).get("JIGYO_KUBUN").toString();
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���Ƌ敪�̎擾�Ɏ��s���܂����B���ƃR�[�h'" + jigyoCd + "'",
				new ErrorInfo("errors.4004"),
				e);
		}
	}

	/**
	 * ���ƃ}�X�^�̂P���R�[�h��Map�`���ŕԂ��B
	 * �����ɂ͎�L�[�l��n���B
	 * @param connection
	 * @param jigyoCd ���ƃR�[�h
	 * @return Map
	 * @throws NoDataFoundException
	 * @throws DataAccessException
	 */
	public static Map selectRecord(Connection connection, String jigyoCd)
		throws NoDataFoundException, DataAccessException {
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"				//���ƃR�[�h
			+ ",A.JIGYO_NAME"			//���Ɩ���
			+ ",A.KEI_KUBUN"			//�n���敪
			+ ",A.JIGYO_KUBUN"			//���Ƌ敪
			+ ",A.SHINSA_KUBUN"			//�R���敪
			+ ",A.BIKO"					//���l
			+ " FROM MASTER_JIGYO A"
			+ " WHERE JIGYO_CD = ? "
			;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// ���R�[�h�擾
		//-----------------------
		List result = SelectUtil.select(connection,
										select, 
										new String[]{jigyoCd});
		if(result.isEmpty()){
			throw new NoDataFoundException(
				"���Y���R�[�h�͑��݂��܂���B���ƃR�[�h="+jigyoCd);
		}
		return (Map)result.get(0);
	}

	//2005/04/28 �ǉ��@��������-------------------------------------------
	//�����̒S�����鎖��CD�ōi�荞�񂾎��Ɩ����X�g���擾���邽��
	/**
	 * ����CD�ōi�荞�񂾃��O�C�����[�U�p�̎��Ɩ����X�g��Ԃ��B
	 * �Ɩ��S���҈ȊO�́AselectJigyoInfoList(Connection connection) ��
	 * �������ʂ��Ԃ�B
	 * <li>�Ɩ��S���ҥ������������S�����鎖�Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
	 * <li>�Ɩ��S���҈ȊO����S��</li>
	 * @param connection
	 * @return
	 * @throws ApplicationException
	 */
	public List selectJigyoInfoListByJigyoCds(Connection connection)
		throws ApplicationException {
		//��������
		String addQuery = null;
		
		//�Ɩ��S���҂̏ꍇ�͎������S�����鎖�Ƃ̂݁B
		if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
			Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
			String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
			addQuery = new StringBuffer(" WHERE A.JIGYO_CD IN (")
						 .append(tantoJigyoCd)
						 .append(")")
						 .toString();
		}else{
			return selectJigyoInfoList(connection);
		}
		
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"//���Ɩ�CD
			+ ",A.JIGYO_NAME"//���Ɩ���
			+ " FROM MASTER_JIGYO A"
			+ addQuery
			+ " ORDER BY JIGYO_CD";		//���ƃR�[�h��						
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
				"���Ə�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw new SystemException(
				"���ƃ}�X�^��1�����f�[�^������܂���B",
				e);
		}
	}
	//�ǉ� �����܂�---------------------------------------------------------	

    /**
     * ���Ƌ敪�E����CD�ōi�荞�񂾃��O�C�����[�U�p�̎��Ɩ����X�g��Ԃ��B
     * �Ɩ��S���҈ȊO�́AselectJigyoInfoList(Connection connection) ��
     * �������ʂ��Ԃ�B
     * <li>�Ɩ��S���ҥ������������S�����鎖�Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
     * <li>�Ɩ��S���҈ȊO����S��</li>
     * @param connection
     * @param jigyoKubun ���Ƌ敪
     * @return List
     * @throws ApplicationException
     */
    public List selectJigyoInfoListByJigyoCds(Connection connection,
            String jigyoKubun) throws ApplicationException {

        //��������
        String addQuery = null;
        
        //�Ɩ��S���҂̏ꍇ�͎������S�����鎖�Ƃ̂݁B
        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
            Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
            String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
            addQuery = new StringBuffer(" WHERE A.JIGYO_CD IN (")
                         .append(tantoJigyoCd)
                         .append(")")
                         .append(" AND JIGYO_KUBUN = '")
                         .append(jigyoKubun)
                         .append("'")
                         .toString();
        }else{
            return selectJigyoInfoList(connection);
        }
        
        //-----------------------
        // SQL���̍쐬
        //-----------------------
        String select =
            "SELECT"
            + " A.JIGYO_CD"//���Ɩ�CD
            + ",A.JIGYO_NAME"//���Ɩ���
            + " FROM MASTER_JIGYO A "
            + addQuery
            + " ORDER BY JIGYO_CD";     //���ƃR�[�h��                        
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
                "���Ə�񌟍�����DB�G���[���������܂����B",
                new ErrorInfo("errors.4004"),
                e);
        } catch (NoDataFoundException e) {
            throw new SystemException(
                "���ƃ}�X�^��1�����f�[�^������܂���B",
                e);
        }
    }

//  2006/06/02 �@�c�@�ǉ���������    
    /**
     * ���Ƌ敪�E����CD�ōi�荞�񂾃��O�C�����[�U�p�̎��Ɩ����X�g��Ԃ��B
     * �Ɩ��S���҈ȊO�́AselectJigyoInfoList(Connection connection) ��
     * �������ʂ��Ԃ�B
     * <li>�Ɩ��S���ҥ������������S�����鎖�Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
     * <li>�Ɩ��S���҈ȊO����S��</li>
     * @param connection
     * @param jigyoCds ���ƃR�[�h���X�g
     * @param jigyoKubun ���Ƌ敪
     * @return List
     * @throws ApplicationException
     */
    public List selectJigyoInfoListByJigyoCds(
            Connection connection,
            String[] jigyoCds,
            String jigyoKubun)
            throws ApplicationException {

        //�Ɩ��S���҂̎������S�����鎖��
        Set tantoJigyoCds = userInfo.getGyomutantoInfo().getTantoJigyoCd();
        Set finalJigyoCdsSet = new HashSet();

        // �Ɩ��S���҂̏ꍇ
        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
            // �Ɩ��S���҂̎������S�����鎖�ƂƊ��S�d�q����Ղ̎���CD���r����
            if(jigyoCds != null && jigyoCds.length > 0){
                for( int i = 0 ; i < jigyoCds.length ; i++){
                    if (tantoJigyoCds.contains(jigyoCds[i])){
                        finalJigyoCdsSet.add(jigyoCds[i]);
                    }
                }
            }else{
                finalJigyoCdsSet = tantoJigyoCds;
            }
        }
        // �����@�֒S���҂ƕ��ǒS���҂̏ꍇ
        else{
            if(jigyoCds != null && jigyoCds.length > 0){
                for(int i = 0 ; i < jigyoCds.length ; i++){
                    finalJigyoCdsSet.add(jigyoCds[i]);
                }
            }
        }
        //WHERE����
        StringBuffer where = new StringBuffer("");
        if(finalJigyoCdsSet.size() > 0){
            if(finalJigyoCdsSet.size() == 1){
                where.append(" WHERE A.JIGYO_CD = ");
                where.append(StringUtil.changeIterator2CSV(finalJigyoCdsSet.iterator(),true));
            }else{
                where.append(" WHERE A.JIGYO_CD IN (");
                where.append(StringUtil.changeIterator2CSV(finalJigyoCdsSet.iterator(),true));
                where.append(")");
            }
        }
        if(!StringUtil.isBlank(jigyoKubun)){
            if(where.length() == 0){
                where.append(" WHERE ");
            }else{
                where.append(" AND ");
            }
            where.append(" JIGYO_KUBUN = '");
            where.append(jigyoKubun);
            where.append("'");
        }

        //-----------------------
        // SQL���̍쐬
        //-----------------------
        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" A.JIGYO_CD");//���Ɩ�CD
        query.append(",A.JIGYO_NAME");//���Ɩ���
        query.append(" FROM MASTER_JIGYO A ");
        query.append(where);
        query.append(" ORDER BY JIGYO_CD");//���ƃR�[�h��

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
                "���Ə�񌟍�����DB�G���[���������܂����B",
                new ErrorInfo("errors.4004"),
                e);
        } catch (NoDataFoundException e) {
            throw new SystemException(
                "���ƃ}�X�^��1�����f�[�^������܂���B",
                e);
        }
    }
//2006/06/02 �c�@�ǉ������܂�

//    //2006/06/06 jzx�@add start
//    //�����̒S�����鎖��CD�ōi�荞�񂾎��Ɩ����X�g���擾���邽��
//    /**
//     * ����CD�ōi�荞�񂾃��O�C�����[�U�p�̎��Ɩ����X�g��Ԃ��B
//     * �Ɩ��S���҈ȊO�́AselectJigyoInfoList(Connection connection) ��
//     * �������ʂ��Ԃ�B
//     * <li>�Ɩ��S���ҥ������������S�����鎖�Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
//     * <li>�Ɩ��S���҈ȊO����S��</li>
//     * @param connection
//     * @return List
//     * @throws ApplicationException
//     */
//    public List selectJigyoInfoListByJigyoCds2(Connection connection,
//                                               String[] validJigyoCds)
//            throws ApplicationException {
//
//        //��������
//        String addQuery = null;
//        
////        String[] validJigyoCds = new String[]{IJigyoCd.JIGYO_CD_TOKUSUI,        //���ʐ��i����
////                                              IJigyoCd.JIGYO_CD_KIBAN_S,        //��Ռ���(S)
////                                              IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN,  //��Ռ���(A)(���)
////                                              IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI, //��Ռ���(A)(�C�O�w�p����)
////                                              IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN,  //��Ռ���(B)(���)
////                                              IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI, //��Ռ���(B)(�C�O�w�p����)
////                                              IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO,//�w�p�n���i�����j
////                                              IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO}; //�w�p�n���i����j
//        
//        //�Ɩ��S���҂̏ꍇ�͎������S�����鎖�Ƃ̂݁B
//        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
//            Set tantoJigyoCds = userInfo.getGyomutantoInfo().getTantoJigyoCd();
//            //�Ɩ��S���҂̎������S�����鎖�ƂƊ��S�d�q����Ղ̎���CD���r����
//            Set finalJigyoCdsSet = new HashSet();
//            for( int i = 0 ; i < validJigyoCds.length ; i++){
//                if (tantoJigyoCds.contains(validJigyoCds[i])){
//                    finalJigyoCdsSet.add(validJigyoCds[i]);
//                }
//            }
//            addQuery = new StringBuffer(" WHERE A.JIGYO_CD IN (")
//                           .append(StringUtil.changeIterator2CSV(finalJigyoCdsSet.iterator(),true))
//                           .append(")")
//                           .toString();
//        }else{
//            addQuery = new StringBuffer(" WHERE A.JIGYO_CD IN (")
//                       .append(StringUtil.changeArray2CSV(validJigyoCds,true))
//                       .append(")")
//                       .toString();
//            
//        }        
//
//        //-----------------------
//        // SQL���̍쐬
//        //-----------------------
//        String select =
//            "SELECT"
//            + " A.JIGYO_CD"//���Ɩ�CD
//            + ",A.JIGYO_NAME"//���Ɩ���
//            + " FROM MASTER_JIGYO A"
//            + addQuery
//            + " ORDER BY A.JIGYO_CD";     //���ƃR�[�h��                        
//            StringBuffer query = new StringBuffer(select);
//
//        if(log.isDebugEnabled()){
//            log.debug("query:" + query);
//        }
//        
//        //-----------------------
//        // ���X�g�擾
//        //-----------------------
//        try {
//            return SelectUtil.select(connection,query.toString());
//        } catch (DataAccessException e) {
//            throw new ApplicationException(
//                "���Ə�񌟍�����DB�G���[���������܂����B",
//                new ErrorInfo("errors.4004"),
//                e);
//        } catch (NoDataFoundException e) {
//            throw new SystemException(
//                "���ƃ}�X�^��1�����f�[�^������܂���B",
//                e);
//        }
//    }
//    //2006/06/06 jzx�@add end
    
//    //2006/06/14 lwj�@add start
//    /**
//     * �w�肳�ꂽ���Ƌ敪�̎��Ɩ����X�g��Ԃ��B
//     * @param connection
//     * @param jigyoCd
//     * @return List
//     * @throws ApplicationException
//     * @throws NoDataFoundException
//     */
//    public List selectJigyoInfoListByJigyoCds3(Connection connection,
//            String jigyoCd) throws ApplicationException{
//
//        //��������
//        String addQuery = null;
//        
//        //�Ɩ��S���҂̏ꍇ�͎������S�����鎖�Ƃ̂݁B
//        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
//            addQuery = " WHERE A.JIGYO_CD IN ("
//                         +jigyoCd
//                         +")";
//        }else{
//            return selectJigyoInfoList(connection);
//        }
//        
//        //-----------------------
//        // SQL���̍쐬
//        //-----------------------
//        String select =
//            "SELECT"
//            + " A.JIGYO_CD"//���Ɩ�CD
//            + ",A.JIGYO_NAME"//���Ɩ���
//            + " FROM MASTER_JIGYO A"
//            + addQuery
//            + " ORDER BY JIGYO_CD";     //���ƃR�[�h��   
//
//        if(log.isDebugEnabled()){
//            log.debug("select:" + select);
//        }
//        
//        //-----------------------
//        // ���X�g�擾
//        //-----------------------
//        try {
//            return SelectUtil.select(connection,select);
//        } catch (DataAccessException e) {
//            throw new ApplicationException(
//                "���Ə�񌟍�����DB�G���[���������܂����B",
//                new ErrorInfo("errors.4004"),
//                e);
//        } catch (NoDataFoundException e) {
//            throw new SystemException(
//                "���ƃ}�X�^��1�����f�[�^������܂���B",
//                e);
//        }
//    }
//    //2006/06/14 lwj�@add end

	/**
	 * ���O�C�����[�U�p�̎��Ɩ����X�g��Ԃ��B
	 * �R���Ώۂ̎��Ƌ敪�����Ɩ����X�g�ɒǉ�����B<br>
	 * <li>�Ɩ��S���ҥ������������S�����鎖�Ƃ̂����A�R���Ώۂ̎��Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
	 * <li>�Ɩ��S���҈ȊO����R���Ώۂ̎��Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
	 * @param connection
	 * @return
	 * @throws ApplicationException
     * @throws NoDataFoundException
	 */
	public List selectShinsaTaishoList4UsersByJigyoCds(Connection connection)
			throws ApplicationException, NoDataFoundException {
		
		//��������
		StringBuffer addQuery;

		//�Ɩ��S���҂̏ꍇ�͎������S�����鎖�Ƃ̂݁B
		if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
			Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
			String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
			addQuery = new StringBuffer(" WHERE A.JIGYO_CD IN (")
						 .append(tantoJigyoCd)
						 .append(") AND")
						 ;
		}else{
			addQuery = new StringBuffer(" WHERE");
		}
//delete start dyh 2006/06/06 �����F�g�p���Ȃ�		
//		//���Ƌ敪���X�g
//		ArrayList jigyoKubunList = new ArrayList();
//delete end dyh 2006/06/06

		//�S�����Ƌ敪����R���Ώە��̎��Ƌ敪�̂ݎ擾
		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);			
		
		//CSV���擾
		String tantoJigyoKubun = StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(),true);
		addQuery.append(" A.JIGYO_KUBUN IN (")
				.append(tantoJigyoKubun)
				.append(")")
					 ;

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
			+ " A.JIGYO_CD"				//���ƃR�[�h
			+ ",A.JIGYO_NAME"			//���Ɩ�
			+ " FROM MASTER_JIGYO A"
			+ addQuery.toString()
			+ " ORDER BY JIGYO_CD";		//���ƃR�[�h��						
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
				"���Ə�񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} catch (NoDataFoundException e) {
			throw e;
		}
	}
}