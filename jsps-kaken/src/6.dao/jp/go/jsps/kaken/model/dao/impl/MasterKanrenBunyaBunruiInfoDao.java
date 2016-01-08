/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : MasterKanrenBunyaBunruiInfoDao.java
 *    Description : �֘A����15���ރ}�X�^�}�X�^�f�[�^�A�N�Z�X�N���X
 *
 *    Author      : �c�c
 *    Date        : 2006/06/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/21    v1.0        �c�c                        �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.Connection;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MasterKanrenBunyaBunruiInfoDao {

/**
 * �֘A����15���ރ}�X�^�}�X�^�f�[�^�A�N�Z�X�N���X�B
 * 
 */
//  ---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------

    /** ���O */
    protected static final Log log = LogFactory.getLog(MasterKanrenBunyaBunruiInfoDao.class);

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
     * @param userInfo      ���s���郆�[�U���
     */
    public MasterKanrenBunyaBunruiInfoDao(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    //---------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------
    
    /**
     * �֘A����15���ރ}�X�^���̈ꗗ���擾����B�i�v���_�E���j
     * @param   connection          �R�l�N�V����
     * @return                      �֘A����15���ރ}�X�^���
     * @throws ApplicationException
     */
    public static List selectKanrenBunyaBunruiInfoList(Connection connection)
            throws ApplicationException {
        
        //-----------------------
        // SQL���̍쐬
        //-----------------------
        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" A.KANRENBUNYA_BUNRUI_NO KANRENBUNYA_BUNRUI_NO");       //�֘A����15���ށi�ԍ��j
        query.append(",A.KANRENBUNYA_BUNRUI_NAME KANRENBUNYA_BUNRUI_NAME");     //�֘A����15���ށi���ޖ��j
        query.append(" FROM MASTER_KANRENBUNYABUNRUI A");
        query.append(" ORDER BY TO_NUMBER(A.KANRENBUNYA_BUNRUI_NO) ASC");                   
            

        if(log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }
        
        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            return SelectUtil.select(connection,query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException(
                "�֘A����15���ރ}�X�^��񌟍�����DB�G���[���������܂����B",
                new ErrorInfo("errors.4004"),
                e);
        } catch (NoDataFoundException e) {
            throw new SystemException(
                "�֘A����15���ރ}�X�^��1�����f�[�^������܂���B",
                e);
        }
    }
}