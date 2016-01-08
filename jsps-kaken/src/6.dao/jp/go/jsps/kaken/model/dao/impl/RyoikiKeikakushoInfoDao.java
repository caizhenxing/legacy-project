/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : RyoikikeikakushoInfoDao.java
 *    Description : �̈�v�揑�i�T�v�j���Ǘ��f�[�^�A�N�Z�X�N���X
 *
 *    Author      : DIS
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS            �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.select.BaseCallbackHandler;
import jp.go.jsps.kaken.model.dao.select.JDBCReading;
import jp.go.jsps.kaken.model.dao.select.PreparedStatementCreator;
import jp.go.jsps.kaken.model.dao.select.RowCallbackHandler;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.UserAuthorityException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �̈�v�揑�i�T�v�j���Ǘ��f�[�^�A�N�Z�X�N���X�B
 * ID RCSfile="$RCSfile: RyoikiKeikakushoInfoDao.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:51 $"
 */
public class RyoikiKeikakushoInfoDao {

    /** ���O */
    protected static final Log log = LogFactory.getLog(RyoikiKeikakushoInfoDao.class);

    // ---------------------------------------------------------------------
    // Instance data
    // ---------------------------------------------------------------------
//  2006/06/20 by jzx add start
    /** DB�����N�� */
    private String dbLink = "";
    
    /** ���s���郆�[�U��� */
    private UserInfo userInfo = null;

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------
    
    /**
     * �R���X�g���N�^�B
     * @param userInfo  ���s���郆�[�U���
     */
    public RyoikiKeikakushoInfoDao(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * �R���X�g���N�^�B
     * @param userInfo  ���s���郆�[�U���
     * @param dbLink   DB�����N��
     */
    public RyoikiKeikakushoInfoDao(UserInfo userInfo, String dbLink) {
        this.userInfo = userInfo;
        this.dbLink   = dbLink;
    }

    //---------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------
    /**
     * �w�肳�ꂽ�V�X�e����t�ԍ��̗̈�v�揑�i�T�v�j�����擾����B
     * 
     * @param  connection �R�l�N�V����
     * @param  pkInfo �L�[���
     * @return RyoikikeikakushoInfo �̈�v�揑�i�T�v�j���
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @author DIS.jzx
     */
    public RyoikiKeikakushoInfo selectRyoikiKeikakushoInfo(
            Connection connection, RyoikiKeikakushoPk pkInfo)
            throws DataAccessException, NoDataFoundException {

        // �V�X�e����t�ԍ����w�肳��Ă��Ȃ��ꍇ��null��Ԃ�
        if (pkInfo == null) {
            throw new NoDataFoundException(
                    "�̈�v�揑�i�T�v�j���Ǘ��e�[�u���ɊY������f�[�^��������܂���B"
                    + "�����L�[�F�̈�v�揑�i�T�v�j���Ǘ�");
        }
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");
        query.append(" A.SHOZOKU_CD,");
        query.append(" A.SHOZOKU_NAME,");
        query.append(" A.KARIRYOIKI_NO,");
        query.append(" A.RYOIKI_NAME,");
        query.append(" A.BUKYOKU_NAME,");
        query.append(" A.SHOKUSHU_NAME_KANJI,");
        query.append(" A.NAME_KANA_SEI,");
        query.append(" A.NAME_KANA_MEI,");
        query.append(" A.EDITION,");
        query.append(" A.RYOIKI_JOKYO_ID,");
        query.append(" B.NENDO,");
        query.append(" B.KAISU,");
        query.append(" C.JIGYO_CD,");
        query.append(" C.JIGYO_NAME,");
        //add start ly 2006/06/15
        query.append(" A.NENDO AS RYOIKI_NENDO,");
        query.append(" A.KAISU AS RYOIKI_KAISU,");
        query.append(" A.SHINSEISHA_ID,");
        query.append(" A.KENKYU_NO,");
        query.append(" A.NAME_KANJI_SEI,");
        query.append(" A.NAME_KANJI_MEI,");
        query.append(" A.JIGYO_NAME");
        //add end ly 2006/06/15
//      query.append(" COUNT(*) COUNT ")
        query.append(" FROM RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" A");
        query.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" B");
        query.append(" ON A.JIGYO_ID = B.JIGYO_ID ");
        query.append(" INNER JOIN MASTER_JIGYO").append(dbLink).append(" C");
        query.append(" ON C.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) ");
// 2006/07/25 dyh update start
//        query.append(" WHERE A.RYOIKI_SYSTEM_NO ='");
//        query.append(EscapeUtil.toSqlString(pkInfo.getRyoikiSystemNo()));
//        query.append("'");
        query.append(" WHERE A.RYOIKI_SYSTEM_NO = ?");
// 2006/07/25 dyh update end
        
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        // -----------------------
        // ���X�g�擾
        // -----------------------
        PreparedStatement ps = null;
        ResultSet resutlt = null;

        try {
            ps = connection.prepareStatement(query.toString());
// 2006/07/25 dyh add start
            int i = 1;
            DatabaseUtil.setParameter(ps, i++, pkInfo.getRyoikiSystemNo());
// 2006/07/25 dyh add end
            resutlt = ps.executeQuery();
            RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
            if (resutlt.next()) {
                info.setShozokuCd(resutlt.getString("SHOZOKU_CD"));
                info.setShozokuName(resutlt.getString("SHOZOKU_NAME"));
                info.setKariryoikiNo(resutlt.getString("KARIRYOIKI_NO"));
                info.setRyoikiName(resutlt.getString("RYOIKI_NAME"));
                info.setBukyokuName(resutlt.getString("BUKYOKU_NAME"));
                info.setShokushuNameKanji(resutlt.getString("SHOKUSHU_NAME_KANJI"));
                info.setNameKanaSei(resutlt.getString("NAME_KANA_SEI"));
                info.setNameKanaMei(resutlt.getString("NAME_KANA_MEI"));
                info.setEdition(resutlt.getString("EDITION"));
                info.setRyoikiJokyoId(resutlt.getString("RYOIKI_JOKYO_ID"));
                info.setNendo(resutlt.getString("NENDO"));
                info.setKaisu(resutlt.getString("KAISU"));
                info.setJigyoName(resutlt.getString("JIGYO_NAME"));
                //add start ly 2006/06/15
                info.setShinseishaId(resutlt.getString("SHINSEISHA_ID"));
                info.setKenkyuNo(resutlt.getString("KENKYU_NO"));
                info.setNameKanjiSei(resutlt.getString("NAME_KANJI_SEI"));
                info.setNameKanjiMei(resutlt.getString("NAME_KANJI_MEI"));
                info.setRyoikiSystemNo(resutlt.getString("RYOIKI_SYSTEM_NO"));
                //add end ly 2006/06/15
            }
            else {
                throw new NoDataFoundException(
                        "�̈�v�揑�i�T�v�j���Ǘ��e�[�u���ɊY������f�[�^��������܂���B"
                        + "�����L�[�F�̈�v�揑�i�T�v�j���Ǘ�"
                        + pkInfo.getRyoikiSystemNo());
            }
            return info;
        }catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑�i�T�v�j���Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        }
        finally {
            DatabaseUtil.closeResource(resutlt, ps);
        }
    }

    /**
     * �󗝓o�^��ʌ`���`�F�b�N���X�V����B
     * @param  connection �R�l�N�V����
     * @param  pkInfo �L�[���
     * @throws DataAccessException �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException
     * @author DIS.jzx
     */
    public void checkKenkyuNoInfo(Connection connection,
            RyoikiKeikakushoPk pkInfo) throws DataAccessException,
            NoDataFoundException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" D.KENKYU_NO");
        query.append(" FROM RYOIKIKEIKAKUSHOINFO ").append(dbLink).append(" A");
        query.append(" INNER JOIN SHINSEIDATAKANRI").append(dbLink).append(" B"); 
        query.append(" ON A.KARIRYOIKI_NO = B.RYOIKI_NO ");
        query.append(" INNER JOIN KENKYUSOSHIKIKANRI").append(dbLink).append(" C");
        query.append(" ON B.SYSTEM_NO = C.SYSTEM_NO ");
        query.append(" INNER JOIN MASTER_KENKYUSHA").append(dbLink).append(" D");
        query.append(" ON C.KENKYU_NO = D.KENKYU_NO ");
// 2006/07/25 dyh update start
//        query.append(" WHERE A.RYOIKI_SYSTEM_NO ='");
//        query.append(EscapeUtil.toSqlString(pkInfo.getRyoikiSystemNo()));
//        query.append("'");
        query.append(" WHERE A.RYOIKI_SYSTEM_NO = ? ");
// 2006/07/25 dyh update end
        query.append(" AND A.DEL_FLG = 0");
        query.append(" AND B.DEL_FLG = 0");
        query.append(" AND D.DEL_FLG = 0");
        query.append( "GROUP BY D.KENKYU_NO");
        
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
      try{
          preparedStatement = connection.prepareStatement(query.toString());
// 2006/07/25 dyh add start
          int i = 1;
          DatabaseUtil.setParameter(preparedStatement, i++, pkInfo.getRyoikiSystemNo());
// 2006/07/25 dyh add end
          recordSet = preparedStatement.executeQuery();
          if(!(recordSet.next())){
              throw new NoDataFoundException("�Y�������҃}�X�^�Ɉȉ��̌����҂����݂��܂���",
                      new ErrorInfo("errors.5064"));
          }
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//  2006/06/21 jzx add end
    
// 2006/06/21 dyh add start
    /**
     * �w�肳�ꂽ�\����ID�̗̈�v�揑�i�T�v�j���Ǘ��f�[�^���擾����B
     * 
     * @param  connection �R�l�N�V����
     * @param  shinseishaId �\����ID
     * @return RyoikikeikakushoInfo
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectRyoikiKeikakushoInfo(
            Connection connection,String shinseishaId) 
            throws DataAccessException, NoDataFoundException {

        // �\����ID
        StringBuffer addQuery = new StringBuffer();
        if (!StringUtil.isBlank(shinseishaId)) {
            addQuery.append(" AND ");
            addQuery.append(" R.SHINSEISHA_ID = '");
            addQuery.append(EscapeUtil.toSqlString(shinseishaId));
            addQuery.append("' ");
        }
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");

        query.append(" J.NENDO,");                  // �N�x
        query.append(" J.KAISU,");                  // ��
        query.append(" J.JIGYO_NAME,");             // ������ږ�
        query.append(" R.RYOIKI_SYSTEM_NO, ");      // �V�X�e����t�ԍ�
        query.append(" R.KARIRYOIKI_NO,");          // ���̈�ԍ�
        query.append(" R.RYOIKI_NAME,");            // ����̈於
        query.append(" R.JIGYO_ID,");               // ����ID
        query.append(" R.NAME_KANJI_SEI,");         // �̈��\�Ҏ����i������-���j
        query.append(" R.NAME_KANJI_MEI,");         // �̈��\�Ҏ����i������-���j
        query.append(" J.UKETUKEKIKAN_END,");       // �w�U��t���ԁi�I���j
        query.append(" CASE WHEN TRUNC(sysdate, 'DD') < J.UKETUKEKIKAN_START ");
        query.append("           THEN 'FALSE' ");
        query.append("      WHEN TRUNC(sysdate, 'DD') > J.UKETUKEKIKAN_END ");
        query.append("           THEN 'FALSE' ");
        query.append("      ELSE 'TRUE' ");
        query.append(" END UKETUKE_END_FLAG,");     // �w�U��t���ԃt���O
        query.append(" J.RYOIKI_KAKUTEIKIKAN_END,");// �̈��\�Ҋm����ؓ�
        query.append(" CASE WHEN TRUNC(sysdate, 'DD') > J.RYOIKI_KAKUTEIKIKAN_END ");
        query.append("           THEN 'OVER' ");
        query.append("      ELSE 'UNDER' ");
        query.append(" END RYOIKI_END_FLAG,");      // �̈��\�Ҋm����ؓ��ȍ~�t���O
        query.append(" R.SAKUSEI_DATE,");           // �̈�v�揑�T�v�쐬��
        query.append(" R.SHONIN_DATE,");            // �����@�֏��F��
        query.append(" R.RYOIKI_JOKYO_ID,");        // �̈�v�揑�i�T�v�j�\����ID
        query.append(" DECODE(R.RYOIKIKEIKAKUSHO_KAKUTEI_FLG,");
        query.append("        NULL,0,R.RYOIKIKEIKAKUSHO_KAKUTEI_FLG)");
        query.append("        RYOIKIKEIKAKUSHO_KAKUTEI_FLG,");// �m��t���O
        query.append(" M.RYOIKIDAIHYOU_HYOJI, "); // �̈�v�揑�i�T�v�j�\����ID
        query.append(" M.JOKYO_NAME "); // �̈�v�揑�i�T�v�j�\���󋵖���
  
        query.append(" FROM ");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");// �̈�v�揑�i�T�v�j���Ǘ��e�[�u��
        query.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" J ");// ���Ə��Ǘ��e�[�u��
        query.append(" ON R.JIGYO_ID = J.JIGYO_ID ");
        query.append(" INNER JOIN MASTER_RYOIKI_STATUS");// �̈�v�揑�T�v�󋵃}�X�^�e�[�u��
        query.append(dbLink).append(" M ");
        query.append(" ON R.RYOIKI_JOKYO_ID = M.JOKYO_ID ");
        query.append(" AND R.CANCEL_FLG = M.KAIJYO_FLG ");

        query.append(" WHERE ");
        query.append(" R.DEL_FLG = 0 ");
        query.append(addQuery);
        query.append(" ORDER BY R.JIGYO_ID ASC,R.KARIRYOIKI_NO ASC");
        
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        // -----------------------
        // ���X�g�擾
        // -----------------------
        List resultList = SelectUtil.select(connection,query.toString());
        return resultList;
    }
// 2006/06/21 dyh add end

//  2006/06/28 mcj add start
    /**
     * �w�肳�ꂽ�V�X�e����t�ԍ��̗̈�v�揑�i�T�v�j���Ǘ��f�[�^���擾����B
     * 
     * @param  connection
     * @param  ryoikiSystemNo
     * @return resultList
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public RyoikiKeikakushoInfo selectInfo(
            Connection connection,
            String ryoikiSystemNo) 
            throws DataAccessException, NoDataFoundException {

        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" J.NENDO,");           // �N�x
        select.append(" J.KAISU,");           // ��
        select.append(" J.JIGYO_NAME,");      // ������ږ�
        select.append(" R.RYOIKI_SYSTEM_NO,");// �V�X�e����t�ԍ�
        select.append(" R.KARIRYOIKI_NO,");   // ���̈�ԍ�
        select.append(" R.RYOIKI_NAME,");     // ����̈於
        select.append(" R.NAME_KANJI_SEI,");  // �̈��\�Ҏ����i������-���j
        select.append(" R.NAME_KANJI_MEI,");  // �̈��\�Ҏ����i������-���j
        select.append(" J.UKETUKEKIKAN_END,");// �w�U��t���ԁi�I���j
        select.append(" R.SAKUSEI_DATE,");    // �̈�v�揑�T�v�쐬��
        select.append(" R.SHONIN_DATE,");     // �����@�֏��F��
        select.append(" R.RYOIKI_JOKYO_ID,"); // �̈�v�揑�i�T�v�j�\����
//        select.append(" M.RYOIKIDAIHYOU_HYOJI,");
        select.append(" R.CANCEL_FLG ");
        select.append(" FROM ");
        select.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");// �̈�v�揑�i�T�v�j���Ǘ��e�[�u��
        select.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" J ");// ���Ə��Ǘ��e�[�u��
        select.append(" ON R.JIGYO_ID = J.JIGYO_ID ");
//        select.append(" INNER JOIN MASTER_RYOIKI_STATUS").append(dbLink).append(" M ");// ���Ə��Ǘ��e�[�u��
//        select.append(" ON R.RYOIKI_JOKYO_ID = M.JOKYO_ID ");
//        select.append(" AND R.CANCEL_FLG = M.KAIJYO_FLG ");
        select.append(" AND ");
// 2006/07/25 dyh update start
//        select.append(" R.RYOIKI_SYSTEM_NO = '");
//        select.append(ryoikiSystemNo);            
//        select.append("' ");
        select.append(" R.RYOIKI_SYSTEM_NO = ?");
// 2006/07/25 dyh update end
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + select.toString());
        }
        // -----------------------
        // ���X�g�擾
        // -----------------------
        PreparedStatement preparedStatement = null;
        ResultSet resutlt = null;
        try {
            preparedStatement = connection.prepareStatement(select.toString());
// 2006/07/25 dyh add start
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, ryoikiSystemNo);
// 2006/07/25 dyh add end
            resutlt = preparedStatement.executeQuery();
            RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
            if (resutlt.next()) {
                info.setRyoikiSystemNo(resutlt.getString("RYOIKI_SYSTEM_NO"));
                info.setNendo(resutlt.getString("NENDO"));
                info.setKaisu(resutlt.getString("KAISU"));
                info.setJigyoName(resutlt.getString("JIGYO_NAME"));
                info.setRyoikiSystemNo(resutlt.getString("RYOIKI_SYSTEM_NO"));// �V�X�e����t�ԍ�
                info.setKariryoikiNo(resutlt.getString("KARIRYOIKI_NO"));                
                info.setRyoikiName(resutlt.getString("RYOIKI_NAME"));
                info.setNameKanjiSei(resutlt.getString("NAME_KANJI_SEI"));
                info.setNameKanjiMei(resutlt.getString("NAME_KANJI_MEI"));
                info.setUketukeNo(resutlt.getString("UKETUKEKIKAN_END"));
                info.setSakuseiDate(resutlt.getDate("SAKUSEI_DATE"));
                info.setShoninDate(resutlt.getDate("SHONIN_DATE"));                
                info.setRyoikiJokyoId(resutlt.getString("RYOIKI_JOKYO_ID"));               
                info.setCancelFlg(resutlt.getString("CANCEL_FLG"));
            }
            else {
                throw new NoDataFoundException(
                        "�̈�v�揑�i�T�v�j���Ǘ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�̈�v�揑�i�T�v�j���Ǘ�"
                                + info.getRyoikiSystemNo() + "'");
            }
            return info;
        }
        catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑�i�T�v�j���Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        }
        finally {
            DatabaseUtil.closeResource(resutlt, preparedStatement);
        }        
    }

    /**
     * �w�肳�ꂽ�V�X�e����t�ԍ��̗̈�v�揑�i�T�v�j���Ǘ��f�[�^���擾����B
     * 
     * @param  connection
     * @param  ryoikikeikakushoInfo
     * @return resultList
     * @throws DataAccessException
     * @throws DuplicateKeyException
     */
    public void existCount(
            Connection connection,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo) 
            throws DataAccessException, DuplicateKeyException{

        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" A.RYOIKI_NO ");
        select.append("FROM");
        select.append(" SHINSEIDATAKANRI A,");
        select.append(" RYOIKIKEIKAKUSHOINFO B ");
        select.append("WHERE");
        select.append(" A.RYOIKI_NO = B.KARIRYOIKI_NO");
        select.append(" AND");
        select.append(" A.JOKYO_ID IN (04,06,07,08,09,10,11,12,21,23,24)");
        select.append(" AND A.DEL_FLG = 0");
        select.append(" AND B.DEL_FLG = 0");
// 2006/07/26 dyh update start
//        select.append(" AND B.RYOIKI_SYSTEM_NO = '");
//        select.append(ryoikikeikakushoInfo.getRyoikiSystemNo());
//        select.append("' ");
        select.append(" AND B.RYOIKI_SYSTEM_NO = ?");
// 2006/07/26 dyh update end
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + select.toString());
        }
        // -----------------------
        // ���X�g�擾
        // -----------------------
        PreparedStatement preparedStatement = null;
        ResultSet resutlt = null;
        try {
            preparedStatement = connection.prepareStatement(select.toString());
// 2006/07/26 dyh add start
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, ryoikikeikakushoInfo.getRyoikiSystemNo());
// 2006/07/26 dyh add start
            resutlt = preparedStatement.executeQuery();
            if (resutlt.next()) {
                throw new DuplicateKeyException("�m�F���̌����v�撲��������܂��B�S�Ă̌����v�撲�����p�����Ă���A�폜���Ă��������B");
            }           
        }
        catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑�i�T�v�j���Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        }
        finally {
            DatabaseUtil.closeResource(resutlt, preparedStatement);
        }        
    }

//  2006/06/15 �{�@��������
    /**
     * �w�肳�ꂽ�����̒�o�m�F�i����̈挤���i�V�K�̈�j)�����擾����B
     * 
     * @param connection �R�l�N�V����
     * @param ryoikiInfo
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List searchRyoikiInfoList(Connection connection,
            RyoikiKeikakushoInfo ryoikiInfo) throws DataAccessException,
            NoDataFoundException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");   // �V�X�e����t�ԍ�
        query.append(" A.KARIRYOIKI_NO,");      // ���̈�ԍ�
        query.append(" A.UKETUKE_NO,");         // ��t�ԍ��i�����ԍ��j
        query.append(" A.JIGYO_ID,");           // ����ID
        query.append(" A.NENDO,");              // �N�x
        query.append(" A.KAISU,");              // ��
        query.append(" A.JIGYO_NAME,");         // ���Ɩ�
        query.append(" A.SHINSEISHA_ID,");      // �\����ID
        query.append(" A.RYOIKI_NAME_RYAKU,");  // �̈旪�̖�
        query.append(" A.BUKYOKU_NAME_RYAKU,"); // ���ǖ��i���́j
        query.append(" A.SHOKUSHU_NAME_RYAKU,");// �E���i���́j
        query.append(" A.SHOZOKU_CD,");         // �����@�փR�[�h
        query.append(" A.NAME_KANJI_SEI,");     // �����S���Ҏ���
        query.append(" A.NAME_KANJI_MEI,");     // �����S���Ҏ���
        query.append(" A.EDITION,");            // ��
        query.append(" A.PDF_PATH,");           // �̈�v�揑�T�vPDF�̊i�[�p�X
        query.append(" A.SAKUSEI_DATE,");       // �̈�v�揑�T�v�쐬��
        query.append(" A.SHONIN_DATE,");        // �����@�֏��F��
        query.append(" A.RYOIKI_JOKYO_ID,");    // �̈�v�揑�i�T�v�j�\����ID
        query.append(" A.KENKYU_NO,");          // �\���Ҍ����Ҕԍ�
        query.append(" B.UKETUKEKIKAN_END,");   // �w�U��t���ԁi�I���j
        //2006/07/19 ADD START
        query.append(" B.KARIRYOIKINO_UKETUKEKIKAN_END,");// ���̈�ԍ���t���ԁi�I���j
        //2006/07/19 ADD END
        query.append(" A.CANCEL_FLG,");         // �����t���O

        // UPDADE START LY 2006/06/21
        query.append(" (SELECT");
        query.append("   COUNT(*) ");
        query.append("  FROM");
        query.append("   SHINSEIDATAKANRI C");
        query.append("  WHERE");
        query.append("   A.KARIRYOIKI_NO = C.RYOIKI_NO ");
        query.append("   AND C.DEL_FLG = 0 ");
        query.append("   AND A.DEL_FLG = 0 ");

        // �\����
        if (!StringUtil.isBlank(ryoikiInfo.getJokyoIds())) {
            query.append(" AND C.JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(ryoikiInfo.getJokyoIds(), true));
            query.append(")");
        }
        query.append(" ) AS COUNT ");

        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" A");
        query.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" B");
        query.append(" ON A.JIGYO_ID = B.JIGYO_ID ");
        query.append("WHERE");
        query.append(" A.DEL_FLG = 0 ");
        // UPDATE LY END 2006/06/21

        // �����@�փR�[�h
        if (!StringUtil.isBlank(ryoikiInfo.getShozokuCd())) {
            query.append(" AND A.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(ryoikiInfo.getShozokuCd()));
            query.append("'");
        }
        // ���ƃR�[�h
        if (!StringUtil.isBlank(ryoikiInfo.getJigyoCd())) {
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '");
            query.append(EscapeUtil.toSqlString(ryoikiInfo.getJigyoCd()));
            query.append("'");
        }

        // �\�[�g��:���Ƃh�c�̏����A���̈�ԍ��̏���
        query.append(" ORDER BY A.JIGYO_ID ASC,A.KARIRYOIKI_NO ASC");

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        return SelectUtil.select(connection, query.toString());
    }
    // 2006/06/15 �{ �����܂�
      
//    /**
//     * �\�����������I�u�W�F�N�g���猟���������擾��SQL�̖₢���킹�����𐶐�����B
//     * ���������₢���킹�����͑������̕�����̌��Ɍ��������B
//     * @param select      
//     * @param searchInfo
//     * @return
//     */
//    protected static String getQueryString(String select,
//            RyoikiKeikakushoInfo searchInfo) {
//
//        //-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
//        StringBuffer query = new StringBuffer(select);
//
//        if (!StringUtil.isBlank(searchInfo.getRyoikiSystemNo())) {
//            query.append(" AND A.RYOIKI_SYSTEM_NO = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getRyoikiSystemNo()))
//                    .append("'");
//        }
//        //�\���ԍ�
//        if (!StringUtil.isBlank(searchInfo.getUketukeNo())) {
//            query.append(" AND A.UKETUKE_NO = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getUketukeNo())).append(
//                    "'");
//        }
//        //����ID
//        if (!StringUtil.isBlank(searchInfo.getJigyoId())) {
//            query.append(" AND A.JIGYO_ID = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getJigyoId()))
//                    .append("'");
//        }
//        //����CD�i����ID��3�����ڂ���5�������j
//        if (!StringUtil.isBlank(searchInfo.getJigyoCd())) {
//            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getJigyoCd()))
//                    .append("'");
//        }
//        //      ���Ɩ�
//        if (!StringUtil.isBlank(searchInfo.getJigyoName())) {
//            query.append(" AND A.JIGYO_NAME = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getJigyoName())).append(
//                    "'");
//        }
//        //�N�x
//        if (!StringUtil.isBlank(searchInfo.getNendo())) {
//            query.append(" AND A.NENDO = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getNendo())).append("'");
//        }
//        //��
//        if (!StringUtil.isBlank(searchInfo.getKaisu())) {
//            query.append(" AND A.KAISU = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getKaisu())).append("'");
//        }
////        // ���Ƌ敪
////        if (searchInfo.getJigyoKubun() != null
////                && searchInfo.getJigyoKubun().size() != 0) {
////            query.append(" AND A.JIGYO_KUBUN IN (").append(
////                    changeIterator2CSV(searchInfo.getJigyoKubun().iterator()))
////                    .append(")");
////        }
//        // �����@�փR�[�h
//        if (!StringUtil.isBlank(searchInfo.getShozokuCd())) {
//            query.append(" AND A.SHOZOKU_CD = '").append(
//                    EscapeUtil.toSqlString(searchInfo.getShozokuCd())).append(
//                    "'");
//        }
//        //���������@�֖�(����)
//        if (!StringUtil.isBlank(searchInfo.getShozokuName())) {
//            query.append("AND A.SHOZOKU_NAME_RYAKU like '%").append(
//                    EscapeUtil.toSqlString(searchInfo.getShozokuName()))
//                    .append("%' ");
//        }
//        //�\����
//        if (searchInfo.getJokyoIds() != null
//                && searchInfo.getJokyoIds().length != 0) {
//            query.append(" AND C.JOKYO_ID IN (").append(
//                    StringUtil.changeArray2CSV(searchInfo.getJokyoIds(), true))
//                    .append(")");
//        }
//        //�̈�v�揑�i�T�v�j�\����ID
//        if (searchInfo.getRyoikiJokyoIds() != null
//                && searchInfo.getRyoikiJokyoIds().length != 0) {
//            query.append(" AND A.RYOIKI_JOKYO_ID IN (").append(
//                    StringUtil.changeArray2CSV(searchInfo.getJokyoIds(), true))
//                    .append(")");
//        }
//        query.append(" ORDER BY A.JIGYO_ID ASC,KARIRYOIKI_NO ASC");
//        return query.toString();
//    }
    
//  2006/06/15 lwj �������� */
//    /**
//     * �w�肳�ꂽ���ƃR�[�h�̒�o���ވꗗ�i����̈挤���i�V�K�̈�j)�����擾����B
//     * @param connection �R�l�N�V����
//     * @param searchInfo
//     * @return List
//     * @throws DataAccessException
//     * @throws NoDataFoundException
//     */
//    public List selectAllTeishutuShorui(Connection connection,
//            TeishutsuShoruiSearchInfo searchInfo)
//            throws DataAccessException, NoDataFoundException {
//
//        String select = "SELECT " 
//                + " A.RYOIKI_SYSTEM_NO,"                   // �V�X�e����t�ԍ�
//                + " A.KARIRYOIKI_NO,"                      // ���̈�ԍ�
//                + " A.UKETUKE_NO,"                         // ��t�ԍ��i�����ԍ��j
//                + " A.JIGYO_ID,"                           // ����ID
//                + " A.CANCEL_FLG,"                         // �t���O
//                + " B.NENDO,"                              // �N�x
//                + " B.KAISU,"                              // ��
//                + " D.JIGYO_CD,"                           // ���ƃR�[�h
//                + " D.JIGYO_NAME,"                         // ���Ɩ�
//                + " A.SHINSEISHA_ID,"                      // �\����ID
//                + " A.DEL_FLG,"                            // �폜�t���O
//                + " A.RYOIKI_NAME_RYAKU,"                  // �̈旪�̖�
//                + " A.BUKYOKU_NAME_RYAKU,"                 // ���ǖ��i���́j
//                + " A.SHOKUSHU_NAME_RYAKU,"                // �E���i���́j
//                + " A.SHOZOKU_CD,"                         // �����@�փR�[�h
//                + " A.SHOZOKU_NAME,"                       // �����@�֖�
//                + " A.NAME_KANJI_SEI,"                     // �����S���Ҏ����i�t���K�i-���j
//                + " A.NAME_KANJI_MEI,"                     // �����S���Ҏ����i�t���K�i-���j
//                + " CASE WHEN TRUNC(sysdate, 'DD') < B.UKETUKEKIKAN_START "
//                + "            THEN 'FALSE' "
//                + "      WHEN TRUNC(sysdate, 'DD') > B.UKETUKEKIKAN_END "
//                + "            THEN 'FALSE' "
//                + "      ELSE 'TRUE' "
//                + " END UKETUKE_END_FLAG,"                 // �w�U��t���ԃt���O
//                + " A.EDITION,"                            // ��
//                + " A.PDF_PATH,"                           // �̈�v�揑�T�vPDF�̊i�[�p�X
//                + " A.SAKUSEI_DATE,"                       // �̈�v�揑�T�v�쐬��
//                + " A.JYURI_DATE,"                         // �󗝓�
//                + " A.RYOIKI_JOKYO_ID,"                    // �̈�v�揑�i�T�v�j�\����ID
//                + " A.KENKYU_NO,"                          // �\���Ҍ����Ҕԍ�
//                + " SYSDATE,"
//                + " (SELECT COUNT(*) FROM SHINSEIDATAKANRI C"
//                + " WHERE A.KARIRYOIKI_NO = C.RYOIKI_NO " + " AND C.DEL_FLG = 0 ";
//        
//        if(searchInfo.getDelFlg().equals("0")){
//            select = select + " AND A.DEL_FLG = " + searchInfo.getDelFlg() + "";
//        }
//        //�\����
//        if (searchInfo.getSearchJokyoId() != null && searchInfo.getSearchJokyoId().length != 0) {
//            select = select + " AND C.JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId(), true) + ")";
//        }
//        select = select
//                + " )AS COUNT FROM RYOIKIKEIKAKUSHOINFO "
//                + dbLink
//                + " A"
//                + " INNER JOIN JIGYOKANRI"
//                + dbLink
//                + " B"
//                + " ON A.JIGYO_ID = B.JIGYO_ID "
//                + " INNER JOIN MASTER_JIGYO"
//                + dbLink
//                + " D"
//                + " ON D.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) "
//                + " WHERE 2>1";
//
//        if (searchInfo.getDelFlg().equals("0")) {
//            select = select + " AND A.DEL_FLG = " + searchInfo.getDelFlg() + "";
//        }
//        if (!StringUtil.isBlank(searchInfo.getJigyoCd())) {
//            select = select + " AND D.JIGYO_CD = '"
//                    + EscapeUtil.toSqlString(searchInfo.getJigyoCd()) + "'";
//        }
//        if (!StringUtil.isBlank(searchInfo.getShozokuCd())) {
//            select = select + " AND A.SHOZOKU_CD = '"
//                    + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'";
//        }
//        if (!StringUtil.isBlank(searchInfo.getShozokuName())) {
//            select = select + " AND A.SHOZOKU_NAME like '%"
//                    + EscapeUtil.toSqlString(searchInfo.getShozokuName())
//                    + "%' ";
//        }
//        if ((searchInfo.getSearchJokyoId1() != null
//                && searchInfo.getSearchJokyoId1().length != 0)
//                && (searchInfo.getSearchJokyoId2() == null
//                        || searchInfo.getSearchJokyoId2().length == 0)) {
//            select = select + " AND A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId1(), true) + ")";
//        }
//        if ((searchInfo.getSearchJokyoId2() != null
//                && searchInfo.getSearchJokyoId2().length != 0)
//                && (searchInfo.getSearchJokyoId1() == null
//                        || searchInfo.getSearchJokyoId1().length == 0)) {
//            select = select + " AND (A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId2(), true) + ")"
//                    + " AND A.CANCEL_FLG =1 )";
//        }
//        if ((searchInfo.getSearchJokyoId1() != null
//                && searchInfo.getSearchJokyoId1().length != 0)
//                && !(searchInfo.getSearchJokyoId2() == null
//                        || searchInfo.getSearchJokyoId2().length == 0)) {
//            select = select + " AND (A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId1(), true) + ")"
//                    + " OR (A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId2(), true)
//                    + ") AND A.CANCEL_FLG =1 ))";
//        }
//        if((searchInfo.getRyoikiJokyoId() != null
//                && searchInfo.getRyoikiJokyoId().length != 0)
//                && searchInfo.getDelFlg().equals("0")) {
//            select = select
//                    + "AND A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getRyoikiJokyoId(),
//                            true) + ")";
//        }
//        if((searchInfo.getRyoikiJokyoId() != null
//                && searchInfo.getRyoikiJokyoId().length != 0)
//                && searchInfo.getDelFlg().equals("1")) {
//            select = select
//                    + "AND (A.RYOIKI_JOKYO_ID IN ("
//                    + StringUtil.changeArray2CSV(searchInfo.getRyoikiJokyoId(),
//                            true)
//                    + ") OR"
//                    + " A.DEL_FLG = "
//                    + searchInfo.getDelFlg()
//                    + ") AND A.UKETUKE_NO IS NOT NULL ORDER BY A.JIGYO_ID ASC,A.UKETUKE_NO ASC";
//        } else {
//            select = select + " ORDER BY A.SHOZOKU_CD ASC,A.UKETUKE_NO ASC";
//        }
//
//        // for debug
//        if (log.isDebugEnabled()) {
//            log.debug("query:" + select);
//        }
//        
//        return SelectUtil.select(connection, select);
//    }
    /**
     * �w�肳�ꂽ���ƃR�[�h�̒�o���ވꗗ�i����̈挤���i�V�K�̈�j)�����擾����B
     * @param connection �R�l�N�V����
     * @param searchInfo
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List selectAllTeishutuShorui(Connection connection,
            TeishutsuShoruiSearchInfo searchInfo)
            throws DataAccessException, NoDataFoundException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");                   // �V�X�e����t�ԍ�
        query.append(" A.KARIRYOIKI_NO,");                      // ���̈�ԍ�
        query.append(" A.UKETUKE_NO,");                         // ��t�ԍ��i�����ԍ��j
        query.append(" A.JIGYO_ID,");                           // ����ID
        query.append(" A.CANCEL_FLG,");                         // �t���O
        query.append(" B.NENDO,");                              // �N�x
        query.append(" B.KAISU,");                              // ��
        query.append(" D.JIGYO_CD,");                           // ���ƃR�[�h
        query.append(" D.JIGYO_NAME,");                         // ���Ɩ�
        query.append(" A.SHINSEISHA_ID,");                      // �\����ID
        query.append(" A.DEL_FLG,");                            // �폜�t���O
        query.append(" A.RYOIKI_NAME_RYAKU,");                  // �̈旪�̖�
        query.append(" A.BUKYOKU_NAME_RYAKU,");                 // ���ǖ��i���́j
        query.append(" A.SHOKUSHU_NAME_RYAKU,");                // �E���i���́j
        query.append(" A.SHOZOKU_CD,");                         // �����@�փR�[�h
        query.append(" A.SHOZOKU_NAME,");                       // �����@�֖�
        query.append(" A.NAME_KANJI_SEI,");                     // �����S���Ҏ����i�t���K�i-���j
        query.append(" A.NAME_KANJI_MEI,");                     // �����S���Ҏ����i�t���K�i-���j
        query.append(" CASE WHEN TRUNC(sysdate, 'DD') < B.UKETUKEKIKAN_START ");
        query.append("            THEN 'FALSE' ");
        query.append("      WHEN TRUNC(sysdate, 'DD') > B.UKETUKEKIKAN_END ");
        query.append("            THEN 'FALSE' ");
        query.append("      ELSE 'TRUE' ");
        query.append(" END UKETUKE_END_FLAG,");                 // �w�U��t���ԃt���O
        query.append(" A.EDITION,");                            // ��
        query.append(" A.PDF_PATH,");                           // �̈�v�揑�T�vPDF�̊i�[�p�X
        query.append(" A.SAKUSEI_DATE,");                       // �̈�v�揑�T�v�쐬��
        query.append(" A.JYURI_DATE,");                         // �󗝓�
        query.append(" A.RYOIKI_JOKYO_ID,");                    // �̈�v�揑�i�T�v�j�\����ID
        query.append(" A.KENKYU_NO,");                          // �\���Ҍ����Ҕԍ�
        query.append(" SYSDATE,");

        // �������� ��������---------------------
        query.append(" (SELECT COUNT(*) ");
        query.append("  FROM");
        query.append("    SHINSEIDATAKANRI").append(dbLink).append(" C");
        query.append("  WHERE A.KARIRYOIKI_NO = C.RYOIKI_NO ");
        query.append("    AND C.DEL_FLG = 0 ");
        // �폜�t���O
        if(searchInfo.getDelFlg().equals("0")){
            query.append(" AND A.DEL_FLG = ");
            query.append(searchInfo.getDelFlg());
        }
        //�\����
        if (searchInfo.getSearchJokyoId() != null && searchInfo.getSearchJokyoId().length != 0) {
            query.append(" AND C.JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId(), true));
            query.append(")");
        }
        query.append(" ) AS COUNT ");
        // �������� �����܂�---------------------

        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" A");
        query.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" B");
        query.append(" ON A.JIGYO_ID = B.JIGYO_ID ");
        query.append(" INNER JOIN MASTER_JIGYO").append(dbLink).append(" D");
        query.append(" ON D.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) ");
        query.append("WHERE");
        query.append(" 2 > 1");

        if (searchInfo.getDelFlg().equals("0")) {
            query.append(" AND A.DEL_FLG = ");
            query.append(searchInfo.getDelFlg());
        }
        if (!StringUtil.isBlank(searchInfo.getJigyoCd())) {
            query.append(" AND D.JIGYO_CD = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getJigyoCd()));
            query.append("'");
        }
        if (!StringUtil.isBlank(searchInfo.getShozokuCd())) {
            query.append(" AND A.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()));
            query.append("'");
        }
        if (!StringUtil.isBlank(searchInfo.getShozokuName())) {
            query.append(" AND A.SHOZOKU_NAME like '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()));
            query.append("%' ");
        }
        if ((searchInfo.getSearchJokyoId1() != null
                && searchInfo.getSearchJokyoId1().length != 0)
                && (searchInfo.getSearchJokyoId2() == null
                        || searchInfo.getSearchJokyoId2().length == 0)) {
            query.append(" AND A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId1(), true));
            query.append(")");
        }
        if ((searchInfo.getSearchJokyoId2() != null
                && searchInfo.getSearchJokyoId2().length != 0)
                && (searchInfo.getSearchJokyoId1() == null
                        || searchInfo.getSearchJokyoId1().length == 0)) {
            query.append(" AND (A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId2(), true));
            query.append(") AND A.CANCEL_FLG = 1)");
        }
        if ((searchInfo.getSearchJokyoId1() != null
                && searchInfo.getSearchJokyoId1().length != 0)
                && !(searchInfo.getSearchJokyoId2() == null
                        || searchInfo.getSearchJokyoId2().length == 0)) {
            query.append(" AND (A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId1(), true));
            query.append(")");
            query.append(" OR (A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getSearchJokyoId2(), true));
            query.append(") AND A.CANCEL_FLG =1 ))");
        }
        if((searchInfo.getRyoikiJokyoId() != null
                && searchInfo.getRyoikiJokyoId().length != 0)
                && searchInfo.getDelFlg().equals("0")) {
            query.append("AND A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getRyoikiJokyoId(), true));
            query.append(")");
        }
        if((searchInfo.getRyoikiJokyoId() != null
                && searchInfo.getRyoikiJokyoId().length != 0)
                && searchInfo.getDelFlg().equals("1")) {
            query.append("AND (A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(searchInfo.getRyoikiJokyoId(), true));
            query.append(") OR A.DEL_FLG = ");
            query.append(searchInfo.getDelFlg());
            query.append(")");
            query.append(" AND A.UKETUKE_NO IS NOT NULL ");
            //2006/08/01 zhangt add start
            query.append(" AND A.JIGYO_ID IN(");
            query.append(StringUtil.changeArray2CSV(searchInfo.getJigyoId(), true));
            query.append(")");
            //2006/08/01 zhangt add end
            query.append(" ORDER BY A.JIGYO_ID ASC, A.UKETUKE_NO ASC");
        } else {
            query.append(" ORDER BY A.SHOZOKU_CD ASC, A.UKETUKE_NO ASC");
        }

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        
        return SelectUtil.select(connection, query.toString());
    }
//   2006/06/15 lwj �����܂� 
    
//  2006/06/15 �c�@�ǉ���������
    /**
     * �̈�v�揑�T�v�e�[�u���i���Y�N�x�̍폜�t���O=0�j�ɉ��̈�ԍ������݂��邩�`�F�b�N
     * @param connection �R�l�N�V����
     * @param strSQL
     * @return strCount
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String selectRyoikiNoCount(Connection connection, String strSQL)
            throws DataAccessException {

        String              strCount            =   null;
        ResultSet           recordSet           =   null;
        PreparedStatement   preparedStatement   =   null;

        try{
            preparedStatement = connection.prepareStatement(strSQL);
            recordSet = preparedStatement.executeQuery();
            if(recordSet.next()){
                strCount =recordSet.getString(IShinseiMaintenance.STR_COUNT);
            }

        }catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑�T�v�e�[�u���̌������ɗ�O���������܂����B", ex);
        }

        return strCount;
    }
// 2006/06/15�@�c�@�ǉ������܂�   

// 2006/06/17 ly add start
    /**
     * ���发�ނ̒�o���o�͗pCSV�f�[�^��DB���擾����B�i�����@�֊Ǘ��p�j
     * @param connection �R�l�N�V����
     * @param ryoikiInfo �����@�֊Ǘ��ԍ�
     * @return
     * @throws ApplicationException
     */
    public List createTokuteiShinki(Connection connection,
            RyoikiKeikakushoInfo ryoikiInfo) throws ApplicationException {

        //-----------------------
        // �����������SQL���̍쐬
        //-----------------------
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" R.SHOZOKU_CD    \"�@�֔ԍ�\" ");   // �����@�փR�[�h//�������Word�Ƃ̃����N���؂�邽�߁u�����@�ցv�̂܂�
        query.append(",R.SHOZOKU_NAME  \"�����@�֖�\" "); // �����@�֖�     //�������Word�Ƃ̃����N���؂�邽�߁u�����@�ցv�̂܂�
        query.append(",COUNT(*)        \"�̈�v�揑�T�v����\" ");
        query.append("FROM (");
        query.append(  "SELECT ");
        query.append(  " A.SHOZOKU_CD     ");   // �����@�փR�[�h
        query.append(  ",A.SHOZOKU_NAME   ");   // �����@�֖�
        query.append(  ",A.KARIRYOIKI_NO  ");
        query.append(  " FROM ");
        query.append(  " RYOIKIKEIKAKUSHOINFO A");
        query.append(  " INNER JOIN JIGYOKANRI B");
        query.append(  " ON A.JIGYO_ID = B.JIGYO_ID ");
        query.append(  " INNER JOIN SHINSEIDATAKANRI C");
        query.append(  " ON A.KARIRYOIKI_NO = C.RYOIKI_NO");
        query.append(  " WHERE");
        query.append(  " C.DEL_FLG = 0 AND A.DEL_FLG = 0 ");

        // �\����
        if (ryoikiInfo.getJokyoIds() != null
                && ryoikiInfo.getJokyoIds().length > 0) {
            query.append(" AND C.JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(ryoikiInfo.getJokyoIds(),true));
            query.append(")");
        }

        // �̈�v�揑�i�T�v�j�\����ID
        if (ryoikiInfo.getRyoikiJokyoIds() != null
                && ryoikiInfo.getRyoikiJokyoIds().length != 0) {
            query.append(" AND A.RYOIKI_JOKYO_ID IN (");
            query.append(StringUtil.changeArray2CSV(ryoikiInfo
                    .getRyoikiJokyoIds(), true));
            query.append(")");
        }
        query.append(" GROUP BY A.SHOZOKU_CD, A.SHOZOKU_NAME, A.KARIRYOIKI_NO");
        query.append(" ) R ");

        //�����@�փR�[�h
        if (!StringUtil.isBlank(ryoikiInfo.getShozokuCd())) {
            query.append(" WHERE R.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(ryoikiInfo.getShozokuCd()));
            query.append("'");
        }
        query.append(" GROUP BY R.SHOZOKU_CD, R.SHOZOKU_NAME");

        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        
        // -----DB���R�[�h�擾-----
        try {
            return SelectUtil.selectCsvList(connection, query.toString(), true);
        }
        catch (DataAccessException e) {
            throw new ApplicationException(
                    "���发�ނ̒�o���o�̓f�[�^��������DB�G���[���������܂����B",
                    new ErrorInfo("errors.4008"), e);
        }
    }
// 2006/06/17 ly add end

//  2006/06/15 zjp ��������
    /**
     * �w�肳�ꂽ�V�X�e����t�ԍ��̉��发�ޏ����擾����B
     * 
     * @param connection �R�l�N�V����
     * @param pkInfo
     * @param ryoikikeikakushoInfo
     * @return
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @author DIS.zjp 2006/06/15
     */
    public RyoikiKeikakushoInfo searchSyoninInfo(Connection connection,
            RyoikiKeikakushoPk pkInfo,RyoikiKeikakushoInfo ryoikikeikakushoInfo) 
            throws NoDataFoundException,DataAccessException {

        // �V�X�e����t�ԍ����w�肳��Ă��Ȃ��ꍇ��null��Ԃ�
        if (pkInfo == null) {
            return null;
        }
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");                 //�V�X�e����t�ԍ�
        query.append(" A.KARIRYOIKI_NO,");                    //���̈�ԍ�
        query.append(" A.UKETUKE_NO,");                       //��t�ԍ��i�����ԍ��j
        query.append(" A.JIGYO_ID,");                         //����ID
        query.append(" A.NENDO,");                            //�N�x
        query.append(" A.KAISU," );                            //��
        query.append(" A.JIGYO_NAME,");                       //���Ɩ�
        query.append(" A.SHINSEISHA_ID,");                    //�\����ID
        query.append(" A.RYOIKI_NAME,");                      //�̈於
        query.append(" A.BUKYOKU_NAME,");                     //���ǖ�
        query.append(" A.SHOKUSHU_NAME_KANJI,");              //�E��
        query.append(" A.SHOZOKU_CD,");                       //�����@�փR�[�h
        query.append(" A.NAME_KANJI_SEI,");                   //�����S���Ҏ����i���j
        query.append(" A.NAME_KANJI_MEI,");                   //�����S���Ҏ����i���j
        query.append(" A.EDITION,");                          //��
        query.append(" A.PDF_PATH,");                         //�̈�v�揑�T�vPDF�̊i�[�p�X
        query.append(" A.SAKUSEI_DATE,");                     //�̈�v�揑�T�v�쐬��
        query.append(" A.SHONIN_DATE,");                      //�����@�֏��F��
        query.append(" A.RYOIKI_JOKYO_ID,");                  //�̈�v�揑�i�T�v�j�\����ID
        query.append(" B.UKETUKEKIKAN_END,");                 //�̈�v�揑�i�T�v�j�\����ID
        query.append(" (SELECT COUNT(*) FROM SHINSEIDATAKANRI C ");                     
        query.append(" WHERE A.KARIRYOIKI_NO = C.RYOIKI_NO ");                     
        query.append(" AND C.DEL_FLG = 0 ");                     
        query.append(" AND A.DEL_FLG = 0 ");
        query.append(" AND C.JOKYO_ID IN (");
        query.append(StringUtil.changeArray2CSV(ryoikikeikakushoInfo.getJokyoIds(), true));                       
        query.append(")");
        query.append(" ) AS COUNT " );                         //���匏��    
        query.append(" FROM RYOIKIKEIKAKUSHOINFO ").append(dbLink).append(" A" );
        query.append(" INNER JOIN JIGYOKANRI").append(dbLink).append(" B" );
        query.append(" ON A.JIGYO_ID = B.JIGYO_ID ");    
        query.append(" WHERE A.DEL_FLG = 0 " );
// 2006/07/26 update start
//        query.append(" AND A.RYOIKI_SYSTEM_NO = '");
//        query.append(pkInfo.getRyoikiSystemNo());
//        query.append("' ");
        query.append(" AND A.RYOIKI_SYSTEM_NO = ?");
// 2006/07/26 update end

        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        // -----------------------
        // ���X�g�擾
        // -----------------------
        PreparedStatement preparedStatement = null;
        ResultSet resutlt = null;

        try {
            preparedStatement = connection.prepareStatement(query.toString());
// 2006/07/26 add start
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, pkInfo.getRyoikiSystemNo());
// 2006/07/26 add end
            resutlt = preparedStatement.executeQuery();
            RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
            if (resutlt.next()) {
                info.setRyoikiSystemNo(resutlt.getString("RYOIKI_SYSTEM_NO"));
                info.setUketukeNo(resutlt.getString("UKETUKE_NO"));
                info.setJigyoId(resutlt.getString("JIGYO_ID"));
                info.setNendo(resutlt.getString("NENDO"));
                info.setKaisu(resutlt.getString("KAISU"));
                info.setJigyoName(resutlt.getString("JIGYO_NAME"));
                info.setShinseishaId(resutlt.getString("SHINSEISHA_ID"));
                info.setKariryoikiNo(resutlt.getString("KARIRYOIKI_NO"));
                info.setShozokuCd(resutlt.getString("SHOZOKU_CD"));
                info.setRyoikiName(resutlt.getString("RYOIKI_NAME"));
                info.setBukyokuName(resutlt.getString("BUKYOKU_NAME"));
                info.setPdfPath(resutlt.getString("PDF_PATH"));
                info.setShokushuNameKanji(resutlt.getString("SHOKUSHU_NAME_KANJI"));
                info.setNameKanjiSei(resutlt.getString("NAME_KANJI_SEI"));
                info.setNameKanjiMei(resutlt.getString("NAME_KANJI_MEI"));
                info.setCount(resutlt.getString("COUNT"));
                info.setEdition(resutlt.getString("EDITION"));
                info.setSakuseiDate(resutlt.getDate("SAKUSEI_DATE"));
                info.setShoninDate(resutlt.getDate("SHONIN_DATE"));
            }
            else {
                throw new NoDataFoundException(
                        "�̈�v�揑�i�T�v�j���Ǘ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�̈�v�揑�i�T�v�j���Ǘ�"
                                + info.getRyoikiSystemNo() + "'");
            }
            return info;
        }
        catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑�i�T�v�j���Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        }
        finally {
            DatabaseUtil.closeResource(resutlt, preparedStatement);
        }
    }
    
    /**
     * �w�肳�ꂽ���ƃR�[�h�̒�o�m�F�i����̈挤���i�V�K�̈�j)�����擾����B
     * 
     * @param connection �R�l�N�V����
     * @param kariryoikiNo
     * @param ryoikikeikakushoInfo
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @author DIS.zjp 2006/06/15
     */
    public List searchSystemNoList(Connection connection, String kariryoikiNo,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo)
            throws DataAccessException, NoDataFoundException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" S.SYSTEM_NO " );
        query.append(" FROM SHINSEIDATAKANRI").append(dbLink).append(" S" );
        query.append(" INNER JOIN RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R" );
        query.append(" ON R.KARIRYOIKI_NO = S.RYOIKI_NO " );
        query.append(" WHERE R.DEL_FLG = 0 " );
        query.append(" AND S.DEL_FLG = 0 " );
        query.append(" AND S.JOKYO_ID IN (");
        query.append(StringUtil.changeArray2CSV(ryoikikeikakushoInfo.getJokyoIds(), true));
        query.append(")");
        query.append(" AND S.RYOIKI_NO = '");
        query.append(EscapeUtil.toSqlString(kariryoikiNo));
        query.append("' ");
        query.append(" GROUP BY S.SYSTEM_NO ");

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        try {
            return SelectUtil.select(connection, query.toString());
        }
        catch (NoDataFoundException e) {
            throw new NoDataFoundException(e.getMessage(), new ErrorInfo(
                    "errors.5002"), e);
        }
        catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }
    
    /**
     * ���F�m�F���[�����M���ƊǗ������擾����B�w�U��t���ؓ���n���O�̎��Ə��̂�
     * �폜�t���O���u0�v�̏ꍇ�̂݌�������B
     * @param connection            �R�l�N�V����
     * @param days                  ���ؓ��܂ł̓���
     * @return                      ���ƊǗ����
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ�B
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public List selectKakuninTokusokuInfo(Connection connection, int days)
            throws DataAccessException, NoDataFoundException {
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT DISTINCT");
        query.append(" A.JIGYO_ID,");
        query.append(" A.NENDO,");
        query.append(" A.KAISU,");
        query.append(" A.JIGYO_NAME,");
        query.append(" B.SHOZOKU_CD,");
        query.append(" C.TANTO_EMAIL ");
        query.append("FROM");
        query.append(" JIGYOKANRI A,");
        query.append(" SHINSEIDATAKANRI B,");
        query.append(" SHOZOKUTANTOINFO C,");
        query.append(" RYOIKIKEIKAKUSHOINFO D ");
        query.append("WHERE");
        query.append(" A.RYOIKI_KAKUTEIKIKAN_END = TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD') + ");
        query.append(EscapeUtil.toSqlString(Integer.toString(days)));
        query.append(" AND B.JOKYO_ID IN ('01','02','03')");
        query.append(" AND A.DEL_FLG = 0");
        query.append(" AND A.JIGYO_ID = B.JIGYO_ID");
        query.append(" AND A.NENDO = B.NENDO");
        query.append(" AND A.KAISU = B.KAISU");
        query.append(" AND B.DEL_FLG = 0");
        query.append(" AND C.DEL_FLG = 0");
        query.append(" AND B.SHOZOKU_CD = C.SHOZOKU_CD");
        query.append(" AND B.RYOIKI_NO = D.KARIRYOIKI_NO");
        query.append(" AND D.RYOIKIKEIKAKUSHO_KAKUTEI_FLG <> 1");
        query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '00022' ");
        query.append("ORDER BY SHOZOKU_CD, JIGYO_ID, NENDO, KAISU");

        if (log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        return SelectUtil.select(connection, query.toString());
    }
    
    /**
     * ���F�����[�����M���ƊǗ������擾����B�w�U��t���ؓ���n���O�̎��Ə��̂�
     * �폜�t���O���u0�v�̏ꍇ�̂݌�������B
     * ����̈挤�����Ƃ̂�
     * @param connection            �R�l�N�V����
     * @param days                  ���ؓ��܂ł̓���
     * @return                      ���ƊǗ����
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ�B
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public List selectShoninTokusokuInfo(Connection connection, int days)
            throws DataAccessException, NoDataFoundException {
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT DISTINCT");
        query.append(" A.JIGYO_ID,");
        query.append(" A.NENDO,");
        query.append(" A.KAISU,");
        query.append(" A.JIGYO_NAME,");
        query.append(" B.SHOZOKU_CD,");
        query.append(" C.TANTO_EMAIL ");
        query.append("FROM");
        query.append(" JIGYOKANRI A,");
        query.append(" RYOIKIKEIKAKUSHOINFO B,");
        query.append(" SHOZOKUTANTOINFO C ");
        query.append("WHERE");
        query.append(" A.UKETUKEKIKAN_END = TO_DATE(TO_CHAR(SYSDATE,'YYYYMMDD'),'YYYYMMDD') + ");
        query.append(EscapeUtil.toSqlString(Integer.toString(days)));
        query.append(" AND B.RYOIKI_JOKYO_ID IN ('01','02','03','31','32','33')");
        query.append(" AND A.DEL_FLG = 0");
        query.append(" AND A.JIGYO_ID = B.JIGYO_ID");
        query.append(" AND A.NENDO = B.NENDO");
        query.append(" AND A.KAISU = B.KAISU");
        query.append(" AND B.DEL_FLG = 0");
        query.append(" AND C.DEL_FLG = 0");
        query.append(" AND B.SHOZOKU_CD = C.SHOZOKU_CD");
        query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '00022' ");
        query.append("ORDER BY SHOZOKU_CD, JIGYO_ID, NENDO, KAISU");

        if (log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        return SelectUtil.select(connection, query.toString());
    }
//  2006/06/15 zjp �����܂�
    
    
//2006/06/20 �c�@�ǉ���������
    /**
     * �̈�v�揑�T�v�e�[�u���i�폜�t���O=0�j�ɐ\����ID�����݂��邩�`�F�b�N
     * @param connection �R�l�N�V����
     * @param shinseishaInfo
     * @return strCount
     * @throws DataAccessException
     */
    public int selectCountByShinseishaId(Connection connection,
            ShinseishaInfo shinseishaInfo)
            throws DataAccessException {
        
        //�����ID
        String shinseishaId = shinseishaInfo.getShinseishaId();

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" COUNT(SHINSEISHA_ID)");
        query.append(" GET_COUNT ");
        query.append("FROM ");
        query.append(" RYOIKIKEIKAKUSHOINFO R ");
        query.append("WHERE");
        query.append(" R.DEL_FLG = 0 ");
// 2006/07/26 dyh update start
//        query.append(" AND R.SHINSEISHA_ID = '");
//        query.append(EscapeUtil.toSqlString(shinseishaId));
//        query.append("'");
        query.append(" AND R.SHINSEISHA_ID = ?");
// 2006/07/26 dyh update end
        if (log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try{
            preparedStatement = connection.prepareStatement(query.toString());
// 2006/07/26 dyh add start
            int index =1;
            DatabaseUtil.setParameter(preparedStatement, index++, shinseishaId);
// 2006/07/26 dyh add end
            recordSet = preparedStatement.executeQuery();
            recordSet.next();
            int infoCount = recordSet.getInt(IShinseiMaintenance.STR_COUNT);
            
            return infoCount;
        } catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑�T�v�e�[�u���̌������ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2006/06/20�@�c�@�ǉ������܂�   
    
    
//  �{�@2006/06/20�@��������
    /**
     * ���̈�ԍ����s���o�^��ʂ̌`���`�F�b�N
     * @param connection �R�l�N�V����
     * @param shinseishaInfo 
     * @param pkInfo 
     * @return count
     * @throws DataAccessException �f�[�^�x�[�X�A�N�Z�X���̗�O
     * @throws DuplicateKeyException
     */
    public void isExistRyoikiInfo(Connection connection,
            ShinseishaInfo shinseishaInfo,JigyoKanriPk pkInfo)
            throws DataAccessException, DuplicateKeyException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT COUNT(*)");
        query.append(" FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO R");
        query.append(" WHERE R.DEL_FLG = 0");
        query.append(" AND R.KENKYU_NO = ?");
        query.append(" AND R.JIGYO_ID = ?");

        if (log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            preparedStatement.setString(i++, shinseishaInfo.getKenkyuNo());
            preparedStatement.setString(i++, pkInfo.getJigyoId());
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            if (count > 0) {
                throw new DuplicateKeyException("�Y��������͊��ɓo�^����Ă��܂��B");
            }
        }catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    //�{�@�����܂�
    
//  �{�@2006/06/21�@��������
    /**
     * �����񖔂͌����v�撲���m�F�����m�F��ʂ̌`���`�F�b�N
     * @param connection �R�l�N�V����
     * @param existInfo                
     * @return count
     * @throws DataAccessException �f�[�^�x�[�X�A�N�Z�X���̗�O
     * @throws ApplicationException
     */
    public void existRyoikiInfoCount(Connection connection,
            ShinseiDataInfo existInfo) throws DataAccessException,
            ApplicationException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT COUNT(*)");
        query.append(" FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        query.append(" WHERE R.DEL_FLG = 0");
        query.append(" AND R.KARIRYOIKI_NO = ?");

        if (log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            preparedStatement.setString(i++, existInfo.getRyouikiNo());
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            if (count == 0) {
                throw new ApplicationException("�Y���f�[�^�͂���܂���B",
                        new ErrorInfo("errors.5002"));
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
        finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    // �{ �����܂�

    /**
     * �\��PDF�i�[�p�X���擾����B
     * 
     * @param connection �R�l�N�V����
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @return String PDF�t�@�C���p�X
     * @throws DataAccessException
     * @author DIS.dyh
     */
    public String selectHyoshiPdfPath(Connection connection,
            String ryoikiSystemNo)
            throws DataAccessException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" R.HYOSHI_PDF_PATH ");
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        query.append("WHERE");
        query.append(" R.DEL_FLG = 0 ");
        query.append(" AND R.RYOIKI_SYSTEM_NO = ?");

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        String path = "";
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, ryoikiSystemNo);
            recordSet = preparedStatement.executeQuery();

            if (recordSet.next()) {
                path = recordSet.getString("HYOSHI_PDF_PATH");
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("�Ώۃf�[�^�����݂��܂���B ", ex);
        }
        finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
        return path;
    }
  //add end ly 2006/06/22

//  2006/06/27 dyh add start
    /**
     * �̈�v�揑�T�vPDF�̊i�[�p�X���擾����B
     * 
     * @param connection �R�l�N�V����
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @return String PDF�t�@�C���p�X
     * @throws DataAccessException
     * @author DIS.dyh
     */
    public String selectPdfPath(Connection connection,
            String ryoikiSystemNo)
            throws DataAccessException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" R.PDF_PATH ");
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        query.append("WHERE");
        query.append(" R.DEL_FLG = 0 ");
        query.append(" AND R.RYOIKI_SYSTEM_NO = ?");

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        String path = "";
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, ryoikiSystemNo);
            recordSet = preparedStatement.executeQuery();

            if (recordSet.next()) {
                path = recordSet.getString("PDF_PATH");
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("�Ώۃf�[�^�����݂��܂���B ", ex);
        }
        finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
        return path;
    }

    /**
     * �̈�v�揑�\��PDF�t�@�C���f�[�^����������B
     * 
     * @param connection �R�l�N�V����
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @return RyoikiKeikakushoInfo �̈�v�揑�\��PDF�t�@�C���f�[�^
     * @throws DataAccessException
     * @author DIS.dyh
     */
    public RyoikiKeikakushoInfo selectGaiyoHyoshiPdfData(Connection connection,
            String ryoikiSystemNo)
            throws DataAccessException {

        // ���������FRYOIKI_SYSTEM_NO,DEL_FLG?0
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" R.NENDO, ");              // �N�x
        query.append(" R.KARIRYOIKI_NO, ");      // ���̈�ԍ�
        query.append(" R.UKETUKE_NO, ");         // ��t�ԍ��i�����ԍ��j
        query.append(" R.JIGYO_ID, ");           // ����ID
        query.append(" R.RYOIKI_NAME, ");        // ����̈於
        query.append(" R.NAME_KANA_SEI, ");      // �̈��\�Ҏ����i������-���j
        query.append(" R.NAME_KANA_MEI, ");      // �̈��\�Ҏ����i������-���j
        query.append(" R.NAME_KANJI_SEI, ");     // �̈��\�Ҏ����i�t���K�i-���j
        query.append(" R.NAME_KANJI_MEI, ");     // �̈��\�Ҏ����i�t���K�i-���j
        query.append(" R.SHOZOKU_NAME, ");       // ���������@�֖�
        query.append(" R.BUKYOKU_NAME, ");       // ���ǖ�
        query.append(" R.SHOKUSHU_NAME_KANJI, ");// �E��
        query.append(" R.SAKUSEI_DATE ");        // �쐬���i�N�j
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");// �̈�v�揑�i�T�v�j���Ǘ��e�[�u��
        query.append("WHERE");
        query.append(" R.DEL_FLG = 0 AND R.RYOIKI_SYSTEM_NO = ? ");
        query.append("ORDER BY R.JIGYO_ID");

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, ryoikiSystemNo);
            recordSet = preparedStatement.executeQuery();

            RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
            if(recordSet.next()){
                info.setNendo(recordSet.getString("NENDO"));                // �N�x
                info.setKariryoikiNo(recordSet.getString("KARIRYOIKI_NO")); // ����̈於
                info.setUketukeNo(recordSet.getString("UKETUKE_NO"));       // ��t�ԍ��i�����ԍ��j
                info.setJigyoId(recordSet.getString("JIGYO_ID"));           // ����̈於
                info.setRyoikiName(recordSet.getString("RYOIKI_NAME"));     // ����̈於
                info.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));  // �����Ҏ����i�t���K�i�j�i���j
                info.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));  // �����Ҏ����i�t���K�i�j�i���j
                info.setNameKanjiSei(recordSet.getString("NAME_KANJI_SEI"));// �����Ҏ����i�������j�i���j
                info.setNameKanjiMei(recordSet.getString("NAME_KANJI_MEI"));// �����Ҏ����i�������j�i���j
                info.setShozokuName(recordSet.getString("SHOZOKU_NAME"));   // ���������@�֖�
                info.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));   // ���ǖ�
                info.setShokushuNameKanji(recordSet.getString("SHOKUSHU_NAME_KANJI"));// �E��
                info.setSakuseiDate(recordSet.getDate("SAKUSEI_DATE"));     // �쐬���i�N�j
            }
            return info;
        }catch (SQLException ex) {
               throw new DataAccessException("�Ώۃf�[�^�����݂��܂���B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �̈�v�揑�T�vPDF�̊i�[�p�X���X�V����B
     * @param connection            �R�l�N�V����
     * @param ryoikiSystemNo        ��L�[�l
     * @param iodFile               PDF�t�@�C��
     * @throws DataAccessException  �X�V�������̗�O�B
     * @throws NoDataFoundException�@�����Ώۃf�[�^���݂���Ȃ��Ƃ��B
     * @author DIS.dyh
     */
    public void updatePdfPath(
            Connection connection,
            final String ryoikiSystemNo,
            File iodFile)
            throws DataAccessException {

        StringBuffer uQuery = new StringBuffer();
        uQuery.append("UPDATE");
        uQuery.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        uQuery.append("SET ");
        uQuery.append(" R.PDF_PATH = ? ");
        uQuery.append("WHERE");
        uQuery.append(" R.DEL_FLG = 0 AND R.RYOIKI_SYSTEM_NO = ?");

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + uQuery.toString());
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(uQuery.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++,
                    EscapeUtil.toSqlString(iodFile.getAbsolutePath()));
            DatabaseUtil.setParameter(preparedStatement, index++,
                    EscapeUtil.toSqlString(ryoikiSystemNo));
            DatabaseUtil.executeUpdate(preparedStatement);

        } catch (SQLException ex) {
            throw new DataAccessException(
                "�̈�v�揑�T�vPDF�̊i�[�p�X�X�V���ɗ�O���������܂����B �F�V�X�e����t�ԍ�'"
                    + ryoikiSystemNo + "'", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �\��PDF�i�[�p�X���X�V����B
     * @param connection            �R�l�N�V����
     * @param ryoikiSystemNo        ��L�[�l
     * @param iodFile               PDF�t�@�C��
     * @throws DataAccessException  �X�V�������̗�O�B
     * @throws NoDataFoundException�@�����Ώۃf�[�^���݂���Ȃ��Ƃ��B
     * @author DIS.dyh
     */
    public void updateHyoshiPdfPath(
            Connection connection,
            final String ryoikiSystemNo,
            File iodFile)
            throws DataAccessException {

//        //�Q�Ɖ\�̈�v�揑�\���f�[�^���`�F�b�N
//        boolean isExist = isExist(connection, ryoikiSystemNo);
//        if(count == 0){
//            throw new UserAuthorityException("�Q�Ɖ\�ȗ̈�v�揑�\���f�[�^�ł͂���܂���B");
//        }
        
        StringBuffer uQuery = new StringBuffer();
        uQuery.append("UPDATE");
        uQuery.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        uQuery.append("SET ");
        uQuery.append(" R.HYOSHI_PDF_PATH = ? ");
        uQuery.append("WHERE");
        uQuery.append(" R.DEL_FLG = 0 AND R.RYOIKI_SYSTEM_NO = ?");

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + uQuery.toString());
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(uQuery.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++,
                    EscapeUtil.toSqlString(iodFile.getAbsolutePath()));
            DatabaseUtil.setParameter(preparedStatement, index++,
                    EscapeUtil.toSqlString(ryoikiSystemNo));
            DatabaseUtil.executeUpdate(preparedStatement);

        } catch (SQLException ex) {
            throw new DataAccessException(
                "�\��PDF�i�[�p�X�X�V���ɗ�O���������܂����B �F�V�X�e����t�ԍ�'"
                    + ryoikiSystemNo + "'", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
// 2006/06/27 dyh add end

//2006/06/27 �c�@�ǉ���������

    //2006.08.14 iso
    //�m������ō쐬���̎��A�R����]�����ύX���Ĉꎞ�ۑ������ꍇ�A
    //��t�ԍ����̔Ԃ���Ȃ��o�O���C���B
    //������ύX
//    /**
//     * �����ԍ���Ԃ��B
//     * @param connection �R�l�N�V����
//     * @param ryoikikeikakushoInfo
//     * @return String
//     * @throws DataAccessException
//     */
//    public String getUketukeNo(Connection connection,
//            RyoikiKeikakushoInfo ryoikikeikakushoInfo)
//            throws DataAccessException {
//        
//        StringBuffer query = new StringBuffer();
//        query.append("SELECT");
//        query.append(" TO_CHAR(count(*) + 1, 'FM00') SEQ_NUM ");
//        query.append("FROM");
//        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
//        query.append("WHERE");
//        query.append(" R.KIBOUBUMON_CD = ? ");
//        query.append(" AND R.JIGYO_ID = ? ");
//        
//        //2006.08.14 iso �ꎞ�ۑ��f�[�^���J�E���g���ꐳ�����̔Ԃ���Ȃ��o�O�C��
//        query.append(" AND R.UKETUKE_NO IS NOT NULL ");
//        
//        //for debug
//        if(log.isDebugEnabled()){
//            log.debug("query:" + query.toString());
//        }
//
//        //DB�ڑ�
//        PreparedStatement preparedStatement = null;
//        ResultSet recordSet = null;
//        try {
//            preparedStatement = connection.prepareStatement(query.toString());
//            int i = 1;
//            preparedStatement.setString(i++, ryoikikeikakushoInfo.getKiboubumonCd());
//            preparedStatement.setString(i++, ryoikikeikakushoInfo.getJigyoId());
//            
//            recordSet = preparedStatement.executeQuery();
//            String seqNumber = null;
//            if (recordSet.next()) {
//                seqNumber= recordSet.getString(1);
//                if(seqNumber == null){
//                    seqNumber = "01";
//                }
//            }
//
//            return StringUtils.right(seqNumber,2);
//             
//        } catch (SQLException ex) {
//            throw new DataAccessException("�̈�v�揑�i�T�v�j���Ǘ��e�[�u���������s���ɗ�O���������܂����B", ex);
//        } finally {
//            DatabaseUtil.closeResource(recordSet, preparedStatement);
//        }
//    }
    /**
     * �����ԍ���Ԃ��B
     * @param connection �R�l�N�V����
     * @param ryoikikeikakushoInfo
     * @param kiboubumonRyaku �R����]���嗪��
     * @return String
     * @throws DataAccessException
     */
    public String getUketukeNo(Connection connection,
    		RyoikiKeikakushoInfo ryoikikeikakushoInfo,
            String kiboubumonRyaku)
            throws DataAccessException {

        //��t�ԍ��u�lXX�v�A��]����R�[�h05(����)�̂悤�ȃf�[�^�����܂��\�������邽�߁A
        //���Ԃ̎擾�́A��t�ԍ��̓������ōs���B
        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" TO_CHAR(MAX(SUBSTR(UKETUKE_NO,2,2)) + 1, 'FM00') SEQ_NUM ");
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        query.append("WHERE");
        query.append(" R.JIGYO_ID = ? ");
        query.append(" AND SUBSTR(R.UKETUKE_NO, 1, 1) = ? ");
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        //DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            preparedStatement.setString(i++, ryoikikeikakushoInfo.getJigyoId());
            preparedStatement.setString(i++, kiboubumonRyaku);
            
            recordSet = preparedStatement.executeQuery();
            String seqNumber = null;
            if (recordSet.next()) {
                seqNumber= recordSet.getString(1);
                if(seqNumber == null){
                    seqNumber = "01";
                }
            }

            return StringUtils.right(seqNumber,2);
             
        } catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑�i�T�v�j���Ǘ��e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    
    /**
     * ���Y�̈�v�揑���Q�Ƃ��邱�Ƃ��ł��邩�`�F�b�N����B
     * �Q�Ɖ\�ɊY�����Ȃ��ꍇ�� NoDataAccessExceptioin �𔭐�������B
     * �w�萔�ƊY���f�[�^������v���Ȃ��ꍇ�� NoDataAccessExceptioin �𔭐�������B
     * 
     * @param connection �R�l�N�V����
     * @param primaryKey
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public void checkOwnRyoikiKakusyoData(Connection connection,
            RyoikiKeikakushoPk primaryKey)
            throws DataAccessException, NoDataFoundException {
        
        StringBuffer query = new StringBuffer();
        String table = " RYOIKIKEIKAKUSHOINFO"+dbLink+" R";
        
        //���O�C�����[�U�ɂ���ď������𕪊򂷂�
        //---�\����
        if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
            query.append(" AND");
            query.append(" R.SHINSEISHA_ID = '");
            query.append(EscapeUtil.toSqlString(userInfo.getShinseishaInfo().getShinseishaId()));
            query.append("'");
                    
        //---�����@�֒S����
        }else if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
            query.append(" AND");
            query.append(" R.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(userInfo.getShozokuInfo().getShozokuCd()));
            query.append("'");
                    
        // ---�R����
        }else if(userInfo.getRole().equals(UserRole.SHINSAIN)){
            table = " RYOIKIKEIKAKUSHOINFO"+dbLink+" R,";
            query.append("(SELECT SYSTEM_NO FROM SHINSAKEKKA");
            query.append(dbLink);
            query.append("  WHERE");
            query.append("    SHINSAIN_NO = '");
            query.append(EscapeUtil.toSqlString(userInfo.getShinsainInfo().getShinsainNo()));
            query.append("') B");

            query.append(" AND");
            query.append(" A.SYSTEM_NO = B.SYSTEM_NO"); 
        
        // ---�Ɩ��S����
        }else if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
            
            table = table+ " inner join  (SELECT JIGYO_CD FROM ACCESSKANRI";
            table = table+ "  WHERE";
            table = table+ "    GYOMUTANTO_ID = '";
            table = table+ EscapeUtil.toSqlString(userInfo.getGyomutantoInfo().getGyomutantoId());
            table = table+ "') B";

            table = table+ " on";
            table = table+ " B.JIGYO_CD = SUBSTR(R.JIGYO_ID,3,5)"; // ���ƃR�[�h
            query.append("") ;
        // ---����ȊO
        }else{

        }

        // SQL�̍쐬
        StringBuffer select = new StringBuffer();
        select.append("SELECT ");
        select.append(" COUNT(R.RYOIKI_SYSTEM_NO) ");
        select.append("FROM ");
        select.append(table);
        select.append(" WHERE ");
        select.append(" R.RYOIKI_SYSTEM_NO = ?");
        select.append(query);

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + select.toString());
        }
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        int count = 0;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(select.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, primaryKey.getRyoikiSystemNo());
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑�i�T�v�j���Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }       
        
        //�Y�������̃`�F�b�N
        if(count != 1){
            throw new UserAuthorityException("�Q�Ɖ\�ȗ̈�v��f�[�^�ł͂���܂���B");
        }        
    }

    /**
     * �̈�v�揑�����폜����i�_���j�B
     * @param connection                �R�l�N�V����
     * @param ryoikiSystemNo            �V�X�e����t�ԍ�
     * @throws DataAccessException      �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException     �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public void deleteFlagRyoikiKeikakushoInfo(Connection connection, String ryoikiSystemNo)
            throws DataAccessException {

        StringBuffer query = new StringBuffer();
        query.append("UPDATE");
        query.append(" RYOIKIKEIKAKUSHOINFO R ");
        query.append("SET");
        query.append(" R.DEL_FLG = 1 ");
        query.append("WHERE");
        query.append(" R.RYOIKI_SYSTEM_NO = ?");//�V�X�e����t�ԍ�

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement,index++, ryoikiSystemNo); //�V�X�e����t�ԍ�                  
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            log.error("�̈�v�揑���X�V���ɗ�O���������܂����B ", ex);
            throw new DataAccessException("�̈�v�揑���X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �̈�v�揑�����X�V����B
     * @param connection                �R�l�N�V����
     * @param updateInfo                �X�V����̈�v�揑���
     * @throws DataAccessException      �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException     �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public void updateRyoikiKeikakushoInfo(
        Connection connection,
        RyoikiKeikakushoInfo updateInfo)
        throws DataAccessException, NoDataFoundException {
        
        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnRyoikiKakusyoData(connection, updateInfo);

        StringBuffer query = new StringBuffer();
        query.append("UPDATE");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");    
        query.append("SET");
        query.append("  R.RYOIKI_SYSTEM_NO              = ? ");// �V�X�e����t�ԍ�
        query.append(" ,R.KARIRYOIKI_NO                 = ? ");// ���̈�ԍ�
        query.append(" ,R.UKETUKE_NO                    = ? ");// ��t�ԍ��i�����ԍ��j
        query.append(" ,R.JIGYO_ID                      = ? ");// ����ID
        query.append(" ,R.NENDO                         = ? ");// �N�x
        query.append(" ,R.KAISU                         = ? ");// ��
        query.append(" ,R.JIGYO_NAME                    = ? ");// ���Ɩ�
        query.append(" ,R.SHINSEISHA_ID                 = ? ");// �\����ID
        query.append(" ,R.KAKUTEI_DATE                  = ? ");// �̈�v�揑�m���
        query.append(" ,R.SAKUSEI_DATE                  = ? ");// �̈�v�揑�T�v�쐬��
        query.append(" ,R.SHONIN_DATE                   = ? ");// �����@�֏��F��
        query.append(" ,R.JYURI_DATE                    = ? ");// �w�U�󗝓�
        query.append(" ,R.NAME_KANJI_SEI                = ? ");// �̈��\�Ҏ����i������-���j
        query.append(" ,R.NAME_KANJI_MEI                = ? ");// �̈��\�Ҏ����i������-���j
        query.append(" ,R.NAME_KANA_SEI                 = ? ");// �̈��\�Ҏ����i�t���K�i-���j
        query.append(" ,R.NAME_KANA_MEI                 = ? ");// �̈��\�Ҏ����i�t���K�i-���j
        query.append(" ,R.NENREI                        = ? ");// �N��
        query.append(" ,R.KENKYU_NO                     = ? ");// �\���Ҍ����Ҕԍ�
        query.append(" ,R.SHOZOKU_CD                    = ? ");// �����@�փR�[�h
        query.append(" ,R.SHOZOKU_NAME                  = ? ");// �����@�֖�
        query.append(" ,R.SHOZOKU_NAME_RYAKU            = ? ");// �����@�֖��i���́j
        query.append(" ,R.BUKYOKU_CD                    = ? ");// ���ǃR�[�h
        query.append(" ,R.BUKYOKU_NAME                  = ? ");// ���ǖ�
        query.append(" ,R.BUKYOKU_NAME_RYAKU            = ? ");// ���ǖ��i���́j
        query.append(" ,R.SHOKUSHU_CD                   = ? ");// �E���R�[�h
        query.append(" ,R.SHOKUSHU_NAME_KANJI           = ? ");// �E���i�a���j
        query.append(" ,R.SHOKUSHU_NAME_RYAKU           = ? ");// �E���i���́j
        query.append(" ,R.KIBOUBUMON_CD                 = ? ");// �R����]����i�n���j�R�[�h
        query.append(" ,R.KIBOUBUMON_NAME               = ? ");// �R����]����i�n���j����
        query.append(" ,R.RYOIKI_NAME                   = ? ");// ����̈於
        query.append(" ,R.RYOIKI_NAME_EIGO              = ? ");// �p��
        query.append(" ,R.RYOIKI_NAME_RYAKU             = ? ");// �̈旪�̖�
        query.append(" ,R.KENKYU_GAIYOU                 = ? ");// �����T�v
        query.append(" ,R.JIZENCHOUSA_FLG               = ? ");// ���O�����̏�
        query.append(" ,R.JIZENCHOUSA_SONOTA            = ? ");// ���O�����̏󋵁i���̑��j
        query.append(" ,R.KAKO_OUBOJYOUKYOU             = ? ");// �ߋ��̉����
        query.append(" ,R.ZENNENDO_OUBO_FLG             = ? ");// �ŏI�N�x�O�N�x�̉���i�Y���̗L���j
        query.append(" ,R.ZENNENDO_OUBO_NO              = ? ");// �ŏI�N�x�O�N�x�̌����̈�ԍ�
        query.append(" ,R.ZENNENDO_OUBO_RYOIKI_RYAKU    = ? ");// �ŏI�N�x�O�N�x�̗̈旪�̖�
        query.append(" ,R.ZENNENDO_OUBO_SETTEI          = ? ");// �ŏI�N�x�O�N�x�̐ݒ����
        query.append(" ,R.BUNKASAIMOKU_CD1              = ? ");// �֘A����i�זڔԍ��j1
        query.append(" ,R.BUNYA_NAME1                   = ? ");// �֘A����i����j1
        query.append(" ,R.BUNKA_NAME1                   = ? ");// �֘A����i���ȁj1
        query.append(" ,R.SAIMOKU_NAME1                 = ? ");// �֘A����i�זځj1
        query.append(" ,R.BUNKASAIMOKU_CD2              = ? ");// �֘A����i�זڔԍ��j2
        query.append(" ,R.BUNYA_NAME2                   = ? ");// �֘A����i����j2
        query.append(" ,R.BUNKA_NAME2                   = ? ");// �֘A����i���ȁj2
        query.append(" ,R.SAIMOKU_NAME2                 = ? ");// �֘A����i�זځj2
        query.append(" ,R.KANRENBUNYA_BUNRUI_NO         = ? ");// �֘A����15���ށi�ԍ��j
        query.append(" ,R.KANRENBUNYA_BUNRUI_NAME       = ? ");// �֘A����15���ށi���ޖ��j
        query.append(" ,R.KENKYU_HITSUYOUSEI_1          = ? ");// �����̕K�v��1
        query.append(" ,R.KENKYU_HITSUYOUSEI_2          = ? ");// �����̕K�v��2
        query.append(" ,R.KENKYU_HITSUYOUSEI_3          = ? ");// �����̕K�v��3
        query.append(" ,R.KENKYU_HITSUYOUSEI_4          = ? ");// �����̕K�v��4
        query.append(" ,R.KENKYU_HITSUYOUSEI_5          = ? ");// �����̕K�v��5
        query.append(" ,R.KENKYU_SYOKEI_1               = ? ");// �����o��i1�N��)-���v
        query.append(" ,R.KENKYU_UTIWAKE_1              = ? ");// �����o��i1�N��)-����
        query.append(" ,R.KENKYU_SYOKEI_2               = ? ");// �����o��i2�N��)-���v
        query.append(" ,R.KENKYU_UTIWAKE_2              = ? ");// �����o��i2�N��)-����
        query.append(" ,R.KENKYU_SYOKEI_3               = ? ");// �����o��i3�N��)-���v
        query.append(" ,R.KENKYU_UTIWAKE_3              = ? ");// �����o��i3�N��)-����
        query.append(" ,R.KENKYU_SYOKEI_4               = ? ");// �����o��i4�N��)-���v
        query.append(" ,R.KENKYU_UTIWAKE_4              = ? ");// �����o��i4�N��)-����
        query.append(" ,R.KENKYU_SYOKEI_5               = ? ");// �����o��i5�N��)-���v
        query.append(" ,R.KENKYU_UTIWAKE_5              = ? ");// �����o��i5�N��)-����
        query.append(" ,R.KENKYU_SYOKEI_6               = ? ");// �����o��i6�N��)-���v
        query.append(" ,R.KENKYU_UTIWAKE_6              = ? ");// �����o��i6�N��)-����
        query.append(" ,R.DAIHYOU_ZIP                   = ? ");// �̈��\�ҁi�X�֔ԍ��j
        query.append(" ,R.DAIHYOU_ADDRESS               = ? ");// �̈��\�ҁi�Z���j
        query.append(" ,R.DAIHYOU_TEL                   = ? ");// �̈��\�ҁi�d�b�j
        query.append(" ,R.DAIHYOU_FAX                   = ? ");// �̈��\�ҁiFAX�j
        query.append(" ,R.DAIHYOU_EMAIL                 = ? ");// �̈��\�ҁi���[���A�h���X�j
        query.append(" ,R.JIMUTANTO_NAME_KANJI_SEI      = ? ");// �����S���Ҏ����i������-���j
        query.append(" ,R.JIMUTANTO_NAME_KANJI_MEI      = ? ");// �����S���Ҏ����i������-���j
        query.append(" ,R.JIMUTANTO_NAME_KANA_SEI       = ? ");// �����S���Ҏ����i�t���K�i-���j
        query.append(" ,R.JIMUTANTO_NAME_KANA_MEI       = ? ");// �����S���Ҏ����i�t���K�i-���j
        query.append(" ,R.JIMUTANTO_SHOZOKU_CD          = ? ");// �����S���Ҍ����@�֔ԍ�
        query.append(" ,R.JIMUTANTO_SHOZOKU_NAME        = ? ");// �����S���Ҍ����@�֖�
        query.append(" ,R.JIMUTANTO_BUKYOKU_CD          = ? ");// �����S���ҕ��ǔԍ�
        query.append(" ,R.JIMUTANTO_BUKYOKU_NAME        = ? ");// �����S���ҕ��ǖ�
        query.append(" ,R.JIMUTANTO_SHOKUSHU_CD         = ? ");// �����S���ҐE���ԍ�
        query.append(" ,R.JIMUTANTO_SHOKUSHU_NAME_KANJI = ? ");// �����S���ҐE���i�a���j
        query.append(" ,R.JIMUTANTO_ZIP                 = ? ");// �����S���ҁi�X�֔ԍ��j
        query.append(" ,R.JIMUTANTO_ADDRESS             = ? ");// �����S���ҁi�Z���j
        query.append(" ,R.JIMUTANTO_TEL                 = ? ");// �����S���ҁi�d�b�j
        query.append(" ,R.JIMUTANTO_FAX                 = ? ");// �����S���ҁiFAX�j
        query.append(" ,R.JIMUTANTO_EMAIL               = ? ");// �����S���ҁi���[���A�h���X�j
        query.append(" ,R.KANREN_SHIMEI1                = ? ");// �֘A����̌�����-����1
        query.append(" ,R.KANREN_KIKAN1                 = ? ");// �֘A����̌�����-���������@��1
        query.append(" ,R.KANREN_BUKYOKU1               = ? ");// �֘A����̌�����-����1
        query.append(" ,R.KANREN_SHOKU1                 = ? ");// �֘A����̌�����-�E��1
        query.append(" ,R.KANREN_SENMON1                = ? ");// �֘A����̌�����-��啪��1
        query.append(" ,R.KANREN_TEL1                   = ? ");// �֘A����̌�����-�Ζ���d�b�ԍ�1
        query.append(" ,R.KANREN_JITAKUTEL1             = ? ");// �֘A����̌�����-����d�b�ԍ�1
        query.append(" ,R.KANREN_SHIMEI2                = ? ");// �֘A����̌�����-����2
        query.append(" ,R.KANREN_KIKAN2                 = ? ");// �֘A����̌�����-���������@��2
        query.append(" ,R.KANREN_BUKYOKU2               = ? ");// �֘A����̌�����-����2
        query.append(" ,R.KANREN_SHOKU2                 = ? ");// �֘A����̌�����-�E��2
        query.append(" ,R.KANREN_SENMON2                = ? ");// �֘A����̌�����-��啪��2
        query.append(" ,R.KANREN_TEL2                   = ? ");// �֘A����̌�����-�Ζ���d�b�ԍ�2
        query.append(" ,R.KANREN_JITAKUTEL2             = ? ");// �֘A����̌�����-����d�b�ԍ�2
        query.append(" ,R.KANREN_SHIMEI3                = ? ");// �֘A����̌�����-����3
        query.append(" ,R.KANREN_KIKAN3                 = ? ");// �֘A����̌�����-���������@��3
        query.append(" ,R.KANREN_BUKYOKU3               = ? ");// �֘A����̌�����-����3
        query.append(" ,R.KANREN_SHOKU3                 = ? ");// �֘A����̌�����-�E��3
        query.append(" ,R.KANREN_SENMON3                = ? ");// �֘A����̌�����-��啪��3
        query.append(" ,R.KANREN_TEL3                   = ? ");// �֘A����̌�����-�Ζ���d�b�ԍ�3
        query.append(" ,R.KANREN_JITAKUTEL3             = ? ");// �֘A����̌�����-����d�b�ԍ�3
        query.append(" ,R.PDF_PATH                      = ? ");// �̈�v�揑�T�vPDF�̊i�[�p�X
        query.append(" ,R.HYOSHI_PDF_PATH               = ? ");// �\��PDF�i�[�p�X
        query.append(" ,R.JURI_KEKKA                    = ? ");// �󗝌���
        query.append(" ,R.JURI_BIKO                     = ? ");// �󗝌��ʔ��l
        query.append(" ,R.RYOIKI_JOKYO_ID               = ? ");// �̈�v�揑�i�T�v�j�\����ID
        query.append(" ,R.EDITION                       = ? ");// ��
        query.append(" ,R.RYOIKIKEIKAKUSHO_KAKUTEI_FLG  = ? ");// �m��t���O
        query.append(" ,R.CANCEL_FLG                    = ? ");// �����t���O
        query.append(" ,R.DEL_FLG                       = ? ");// �폜�t���O
        query.append("WHERE");
        query.append(" R.RYOIKI_SYSTEM_NO = ?");//�V�X�e����t�ԍ�

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query.toString());
            int index = this.setAllParameter(preparedStatement, updateInfo);
            DatabaseUtil.setParameter(preparedStatement,index++, updateInfo.getRyoikiSystemNo()); //�V�X�e����t�ԍ�                  
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            log.error("�̈�v�揑���X�V���ɗ�O���������܂����B ", ex);
            throw new DataAccessException("�̈�v�揑���X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * �w��PreparedStatement�ɐ\���f�[�^���ׂẴp�����[�^���Z�b�g����B
     * SQL�X�e�[�g�����g�ɂ͑S�Ẵp�����[�^���w��\�ɂ��Ă������ƁB
     * @param preparedStatement
     * @param ryoikikeikakushoInfo
     * @return index          ���̃p�����[�^�C���f�b�N�X
     * @throws SQLException
     */
    private int setAllParameter(PreparedStatement preparedStatement,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo) throws SQLException {
        int index = 1;
        //---��{���
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikiSystemNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKariryoikiNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getUketukeNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJigyoId());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNendo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKaisu());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJigyoName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShinseishaId());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKakuteiDate());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getSakuseiDate());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShoninDate());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJyuriDate());

        //---�\���ҁi�̈��\�ҁj
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNameKanjiSei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNameKanjiMei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNameKanaSei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNameKanaMei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getNenrei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShozokuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShozokuName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShozokuNameRyaku());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBukyokuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBukyokuName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBukyokuNameRyaku());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShokushuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShokushuNameKanji());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getShokushuNameRyaku());

        //---�̈���
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKiboubumonCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKiboubumonName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikiName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikiNameEigo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikiNameRyaku());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuGaiyou());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJizenchousaFlg());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJizenchousaSonota());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKakoOubojyoukyou());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getZennendoOuboFlg());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getZennendoOuboNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getZennendoOuboRyoikiRyaku());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getZennendoOuboSettei());
        
        //---�֘A����
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunkasaimokuCd1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunyaName1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunkaName1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getSaimokuName1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunkasaimokuCd2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunyaName2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getBunkaName2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getSaimokuName2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenbunyaBunruiNo());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenbunyaBunruiName());

        //---�����̕K�v��
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuHitsuyousei1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuHitsuyousei2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuHitsuyousei3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuHitsuyousei4());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuHitsuyousei5());
        
        //---�����o��
// 2006/08/25 dyh update start �����F�d�l�ύX
//        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei1());
//        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake1());
        DatabaseUtil.setParameter(preparedStatement,index++, "");
        DatabaseUtil.setParameter(preparedStatement,index++, "");
// 2006/08/25 dyh update end
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei4());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake4());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei5());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake5());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuSyokei6());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKenkyuUtiwake6());

        //---�̈��\��
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDaihyouZip());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDaihyouAddress());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDaihyouTel());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDaihyouFax());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDaihyouEmail());
        
        //---�����S����
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoNameKanjiSei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoNameKanjiMei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoNameKanaSei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoNameKanaMei());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoShozokuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoShozokuName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoBukyokuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoBukyokuName());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoShokushuCd());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoShokushuNameKanji());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoZip());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoAddress());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoTel());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoFax());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJimutantoEmail());
        
        
        //---�֘A����̌�����
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShimei1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenKikan1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenBukyoku1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShoku1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenSenmon1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenTel1());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenJitakutel1());
        
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShimei2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenKikan2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenBukyoku2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShoku2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenSenmon2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenTel2());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenJitakutel2());
        
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShimei3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenKikan3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenBukyoku3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenShoku3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenSenmon3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenTel3());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getKanrenJitakutel3());
        
        //---��{���i�㔼�j
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getPdfPath());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getHyoshiPdfPath());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJuriKekka());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getJuriBiko());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikiJokyoId());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getEdition());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getRyoikikeikakushoKakuteiFlg());        
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getCancelFlg());
        DatabaseUtil.setParameter(preparedStatement,index++, ryoikikeikakushoInfo.getDelFlg());

        return index;
    }
    
    /**
     * ���Y�̈�v�揑���Q�Ƃ��邱�Ƃ��ł��邩�`�F�b�N����B �Q�Ɖ\�ɊY�����Ȃ��ꍇ�� NoDataAccessExceptioin �𔭐�������B
     * <p>
     * ���f��͈ȉ��̒ʂ�B
     * <li>�\���҂̏ꍇ��������̐\����ID�̂��̂��ǂ���
     * </p>
     * 
     * @param connection �R�l�N�V����
     * @param pkInfo
     * @param lock
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    private RyoikiKeikakushoInfo selectRyoikiInfo(Connection connection,
            RyoikiKeikakushoPk pkInfo, boolean lock)
            throws DataAccessException, NoDataFoundException {
        
        //�Q�Ɖ\�\���f�[�^���`�F�b�N
        checkOwnRyoikiKakusyoData(connection, pkInfo);
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");             // �V�X�e����t�ԍ�
        query.append(" A.KARIRYOIKI_NO,");                // ���̈�ԍ�            
        query.append(" A.UKETUKE_NO,");                   // ��t�ԍ��i�����ԍ��j
        query.append(" A.JIGYO_ID,");                     // ����ID
        query.append(" A.NENDO,");                        // �N�x
        query.append(" A.KAISU,");                        // ��
        query.append(" A.JIGYO_NAME,");                   // ���Ɩ�
        query.append(" A.SHINSEISHA_ID,");                // �\����ID
        query.append(" A.KAKUTEI_DATE,");                 // �̈�v�揑�m���                 
        query.append(" A.SAKUSEI_DATE,");                 // �̈�v�揑�T�v�쐬��
        query.append(" A.SHONIN_DATE,");                  // �����@�֏��F��
        query.append(" A.JYURI_DATE,");                   // �w�U�󗝓�
        query.append(" A.NAME_KANJI_SEI,");               // �̈��\�Ҏ����i������-���j
        query.append(" A.NAME_KANJI_MEI,");               // �̈��\�Ҏ����i������-���j
        query.append(" A.NAME_KANA_SEI,");                // �̈��\�Ҏ����i�t���K�i-���j
        query.append(" A.NAME_KANA_MEI,");                // �̈��\�Ҏ����i�t���K�i-���j
        query.append(" A.NENREI,");                       // �N��
        query.append(" A.KENKYU_NO,");                    // �\���Ҍ����Ҕԍ�
        query.append(" A.SHOZOKU_CD,");                   // �����@�փR�[�h
        query.append(" A.SHOZOKU_NAME,");                 // �����@�֖�
        query.append(" A.SHOZOKU_NAME_RYAKU,");           // �����@�֖��i���́j
        query.append(" A.BUKYOKU_CD,");                   // ���ǃR�[�h
        query.append(" A.BUKYOKU_NAME,");                 // ���ǖ�
        query.append(" A.BUKYOKU_NAME_RYAKU,");           // ���ǖ��i���́j
        query.append(" A.SHOKUSHU_CD,");                  // �E���R�[�h
        query.append(" A.SHOKUSHU_NAME_KANJI,");          // �E���i�a���j
        query.append(" A.SHOKUSHU_NAME_RYAKU,");          // �E���i���́j
        query.append(" A.KIBOUBUMON_CD,");                // �R����]����i�n���j�R�[�h
        query.append(" A.KIBOUBUMON_NAME,");              // �R����]����i�n���j����
        query.append(" A.RYOIKI_NAME,");                  // ����̈於
        query.append(" A.RYOIKI_NAME_EIGO,");             // �p��
        query.append(" A.RYOIKI_NAME_RYAKU,");            // �̈旪�̖�
        query.append(" A.KENKYU_GAIYOU,");                // �����T�v
        query.append(" A.JIZENCHOUSA_FLG,");              // ���O�����̏�
        query.append(" A.JIZENCHOUSA_SONOTA,");           // ���O�����̏󋵁i���̑��j
        query.append(" A.KAKO_OUBOJYOUKYOU,");            // �ߋ��̉����
        query.append(" A.ZENNENDO_OUBO_FLG,");            // �ŏI�N�x�O�N�x�̉���i�Y���̗L���j
        query.append(" A.ZENNENDO_OUBO_NO,");             // �ŏI�N�x�O�N�x�̌����̈�ԍ�
        query.append(" A.ZENNENDO_OUBO_RYOIKI_RYAKU,");   // �ŏI�N�x�O�N�x�̗̈旪�̖�
        query.append(" A.ZENNENDO_OUBO_SETTEI,");         // �ŏI�N�x�O�N�x�̐ݒ����
        query.append(" A.BUNKASAIMOKU_CD1,");             // �֘A����i�זڔԍ��j1
        query.append(" A.BUNYA_NAME1,");                  // �֘A����i����j1
        query.append(" A.BUNKA_NAME1,");                  // �֘A����i���ȁj1
        query.append(" A.SAIMOKU_NAME1,");                // �֘A����i�זځj1
        query.append(" A.BUNKASAIMOKU_CD2,");             // �֘A����i�זڔԍ��j2
        query.append(" A.BUNYA_NAME2,");                  // �֘A����i����j2
        query.append(" A.BUNKA_NAME2,");                  // �֘A����i���ȁj2
        query.append(" A.SAIMOKU_NAME2,");                // �֘A����i�זځj2
        query.append(" A.KANRENBUNYA_BUNRUI_NO,");        // �֘A����15���ށi�ԍ��j
        query.append(" A.KANRENBUNYA_BUNRUI_NAME,");      // �֘A����15���ށi���ޖ��j
        query.append(" A.KENKYU_HITSUYOUSEI_1,");         // �����̕K�v��1
        query.append(" A.KENKYU_HITSUYOUSEI_2,");         // �����̕K�v��2
        query.append(" A.KENKYU_HITSUYOUSEI_3,");         // �����̕K�v��3
        query.append(" A.KENKYU_HITSUYOUSEI_4,");         // �����̕K�v��4
        query.append(" A.KENKYU_HITSUYOUSEI_5,");         // �����̕K�v��5
        query.append(" A.KENKYU_SYOKEI_1,");              // �����o��i1�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_1,");             // �����o��i1�N��)-����
        query.append(" A.KENKYU_SYOKEI_2,");              // �����o��i2�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_2,");             // �����o��i2�N��)-����
        query.append(" A.KENKYU_SYOKEI_3,");              // �����o��i3�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_3,");             // �����o��i3�N��)-����
        query.append(" A.KENKYU_SYOKEI_4,");              // �����o��i4�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_4,");             // �����o��i4�N��)-����
        query.append(" A.KENKYU_SYOKEI_5,");              // �����o��i5�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_5,");             // �����o��i5�N��)-����
        query.append(" A.KENKYU_SYOKEI_6,");              // �����o��i6�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_6,");             // �����o��i6�N��)-����
        query.append(" A.DAIHYOU_ZIP,");                  // �̈��\�ҁi�X�֔ԍ��j
        query.append(" A.DAIHYOU_ADDRESS,");              // �̈��\�ҁi�Z���j
        query.append(" A.DAIHYOU_TEL,");                  // �̈��\�ҁi�d�b�j
        query.append(" A.DAIHYOU_FAX,");                  // �̈��\�ҁiFAX�j
        query.append(" A.DAIHYOU_EMAIL,");                // �̈��\�ҁi���[���A�h���X�j
        query.append(" A.JIMUTANTO_NAME_KANJI_SEI,");     // �����S���Ҏ����i������-���j
        query.append(" A.JIMUTANTO_NAME_KANJI_MEI,");     // �����S���Ҏ����i������-���j
        query.append(" A.JIMUTANTO_NAME_KANA_SEI,");      // �����S���Ҏ����i�t���K�i-���j
        query.append(" A.JIMUTANTO_NAME_KANA_MEI,");      // �����S���Ҏ����i�t���K�i-���j
        query.append(" A.JIMUTANTO_SHOZOKU_CD,");         // �����S���Ҍ����@�֔ԍ�
        query.append(" A.JIMUTANTO_SHOZOKU_NAME,");       // �����S���Ҍ����@�֖�
        query.append(" A.JIMUTANTO_BUKYOKU_CD,");         // �����S���ҕ��ǔԍ�
        query.append(" A.JIMUTANTO_BUKYOKU_NAME,");       // �����S���ҕ��ǖ�
        query.append(" A.JIMUTANTO_SHOKUSHU_CD,");        // �����S���ҐE���ԍ�
        query.append(" A.JIMUTANTO_SHOKUSHU_NAME_KANJI,");// �����S���ҐE���i�a���j
        query.append(" A.JIMUTANTO_ZIP,");                // �����S���ҁi�X�֔ԍ��j
        query.append(" A.JIMUTANTO_ADDRESS,");            // �����S���ҁi�Z���j
        query.append(" A.JIMUTANTO_TEL,");                // �����S���ҁi�d�b�j
        query.append(" A.JIMUTANTO_FAX,");                // �����S���ҁiFAX�j
        query.append(" A.JIMUTANTO_EMAIL,");              // �����S���ҁi���[���A�h���X�j
        query.append(" A.KANREN_SHIMEI1,");               // �֘A����̌�����-����1
        query.append(" A.KANREN_KIKAN1,");                // �֘A����̌�����-���������@��1
        query.append(" A.KANREN_BUKYOKU1,");              // �֘A����̌�����-����1
        query.append(" A.KANREN_SHOKU1,");                // �֘A����̌�����-�E��1
        query.append(" A.KANREN_SENMON1,");               // �֘A����̌�����-��啪��1
        query.append(" A.KANREN_TEL1,");                  // �֘A����̌�����-�Ζ���d�b�ԍ�1
        query.append(" A.KANREN_JITAKUTEL1,");            // �֘A����̌�����-����d�b�ԍ�1
        query.append(" A.KANREN_SHIMEI2,");               // �֘A����̌�����-����2
        query.append(" A.KANREN_KIKAN2,");                // �֘A����̌�����-���������@��2
        query.append(" A.KANREN_BUKYOKU2,");              // �֘A����̌�����-����2                             
        query.append(" A.KANREN_SHOKU2,");                // �֘A����̌�����-�E��2
        query.append(" A.KANREN_SENMON2,");               // �֘A����̌�����-��啪��2
        query.append(" A.KANREN_TEL2,");                  // �֘A����̌�����-�Ζ���d�b�ԍ�2
        query.append(" A.KANREN_JITAKUTEL2,");            // �֘A����̌�����-����d�b�ԍ�2
        query.append(" A.KANREN_SHIMEI3,");               // �֘A����̌�����-����3
        query.append(" A.KANREN_KIKAN3,");                // �֘A����̌�����-���������@��3
        query.append(" A.KANREN_BUKYOKU3,");              // �֘A����̌�����-����3                             
        query.append(" A.KANREN_SHOKU3,");                // �֘A����̌�����-�E��3
        query.append(" A.KANREN_SENMON3,");               // �֘A����̌�����-��啪��3
        query.append(" A.KANREN_TEL3,");                  // �֘A����̌�����-�Ζ���d�b�ԍ�3
        query.append(" A.KANREN_JITAKUTEL3,");            // �֘A����̌�����-����d�b�ԍ�3
        query.append(" A.PDF_PATH,");                     // �̈�v�揑�T�vPDF�̊i�[�p�X
        query.append(" A.HYOSHI_PDF_PATH,");              // �\��PDF�i�[�p�X
        query.append(" A.JURI_KEKKA,");                   // �󗝌���
        query.append(" A.JURI_BIKO,");                    // �󗝌��ʔ��l
        query.append(" A.RYOIKI_JOKYO_ID,");              // �̈�v�揑�i�T�v�j�\����ID                    
        query.append(" A.EDITION,");                      // ��
        query.append(" A.RYOIKIKEIKAKUSHO_KAKUTEI_FLG,"); // �m��t���O
        query.append(" A.CANCEL_FLG,");                   // �����t���O
        query.append(" A.DEL_FLG ");                      // �폜�t���O
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" A");
// 2006/07/26 dyh update start
//        query.append(" WHERE A.RYOIKI_SYSTEM_NO ='");
//        query.append(pkInfo.getRyoikiSystemNo());
//        query.append("'");
        query.append(" WHERE A.RYOIKI_SYSTEM_NO = ?");
// 2006/07/26 dyh update end

        //�r��������s���ꍇ
        if(lock){
            query.append(" FOR UPDATE");
        }

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        
        // -----------------------
        // ���X�g�擾
        // -----------------------
        PreparedStatement ps = null;
        ResultSet resutlt = null;

        try {
            ps = connection.prepareStatement(query.toString());
            int index = 1;
            DatabaseUtil.setParameter(ps,index++, pkInfo.getRyoikiSystemNo());
            resutlt = ps.executeQuery();

            RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
            if (resutlt.next()) {
                info.setRyoikiSystemNo(resutlt.getString("RYOIKI_SYSTEM_NO"));
                info.setKariryoikiNo(resutlt.getString("KARIRYOIKI_NO"));
                info.setUketukeNo(resutlt.getString("UKETUKE_NO"));
                info.setJigyoId(resutlt.getString("JIGYO_ID"));
                info.setJigyoCd(resutlt.getString("JIGYO_ID").substring(2,7));
                info.setNendo(resutlt.getString("NENDO"));
                info.setKaisu(resutlt.getString("KAISU"));
                info.setJigyoName(resutlt.getString("JIGYO_NAME"));
                info.setShinseishaId(resutlt.getString("SHINSEISHA_ID"));
                info.setKakuteiDate(resutlt.getDate("KAKUTEI_DATE"));
                info.setSakuseiDate(resutlt.getDate("SAKUSEI_DATE"));
                info.setShoninDate(resutlt.getDate("SHONIN_DATE"));
                info.setJyuriDate(resutlt.getDate("JYURI_DATE"));
                info.setNameKanjiSei(resutlt.getString("NAME_KANJI_SEI"));
                info.setNameKanjiMei(resutlt.getString("NAME_KANJI_MEI"));
                info.setNameKanaSei(resutlt.getString("NAME_KANA_SEI"));
                info.setNameKanaMei(resutlt.getString("NAME_KANA_MEI"));
                info.setNenrei(resutlt.getString("NENREI"));
                info.setKenkyuNo(resutlt.getString("KENKYU_NO"));
                info.setShozokuCd(resutlt.getString("SHOZOKU_CD"));
                info.setShozokuName(resutlt.getString("SHOZOKU_NAME"));
                info.setShozokuNameRyaku(resutlt.getString("SHOZOKU_NAME_RYAKU"));
                info.setBukyokuCd(resutlt.getString("BUKYOKU_CD"));
                info.setBukyokuName(resutlt.getString("BUKYOKU_NAME"));
                info.setBukyokuNameRyaku(resutlt.getString("BUKYOKU_NAME_RYAKU"));
                info.setShokushuCd(resutlt.getString("SHOKUSHU_CD"));
                info.setShokushuNameKanji(resutlt.getString("SHOKUSHU_NAME_KANJI"));
                info.setShokushuNameRyaku(resutlt.getString("SHOKUSHU_NAME_RYAKU"));
                info.setKiboubumonCd(resutlt.getString("KIBOUBUMON_CD"));
                info.setKiboubumonName(resutlt.getString("KIBOUBUMON_NAME"));
                info.setRyoikiName(resutlt.getString("RYOIKI_NAME"));
                info.setRyoikiNameEigo(resutlt.getString("RYOIKI_NAME_EIGO"));
                info.setRyoikiNameRyaku(resutlt.getString("RYOIKI_NAME_RYAKU"));
                info.setKenkyuGaiyou(resutlt.getString("KENKYU_GAIYOU"));
                info.setJizenchousaFlg(resutlt.getString("JIZENCHOUSA_FLG"));
                info.setJizenchousaSonota(resutlt.getString("JIZENCHOUSA_SONOTA"));
                info.setKakoOubojyoukyou(resutlt.getString("KAKO_OUBOJYOUKYOU"));
                info.setZennendoOuboFlg(resutlt.getString("ZENNENDO_OUBO_FLG"));
                info.setZennendoOuboNo(resutlt.getString("ZENNENDO_OUBO_NO"));
                info.setZennendoOuboRyoikiRyaku(resutlt.getString("ZENNENDO_OUBO_RYOIKI_RYAKU"));
                info.setZennendoOuboSettei(resutlt.getString("ZENNENDO_OUBO_SETTEI"));
                info.setBunkasaimokuCd1(resutlt.getString("BUNKASAIMOKU_CD1"));
                info.setBunyaName1(resutlt.getString("BUNYA_NAME1"));
                info.setBunkaName1(resutlt.getString("BUNKA_NAME1"));
                info.setSaimokuName1(resutlt.getString("SAIMOKU_NAME1"));
                info.setBunkasaimokuCd2(resutlt.getString("BUNKASAIMOKU_CD2"));
                info.setBunyaName2(resutlt.getString("BUNYA_NAME2"));
                info.setBunkaName2(resutlt.getString("BUNKA_NAME2"));
                info.setSaimokuName2(resutlt.getString("SAIMOKU_NAME2"));
                info.setKanrenbunyaBunruiNo(resutlt.getString("KANRENBUNYA_BUNRUI_NO"));
                info.setKanrenbunyaBunruiName(resutlt.getString("KANRENBUNYA_BUNRUI_NAME"));
                info.setKenkyuHitsuyousei1(resutlt.getString("KENKYU_HITSUYOUSEI_1"));
                info.setKenkyuHitsuyousei2(resutlt.getString("KENKYU_HITSUYOUSEI_2"));
                info.setKenkyuHitsuyousei3(resutlt.getString("KENKYU_HITSUYOUSEI_3"));
                info.setKenkyuHitsuyousei4(resutlt.getString("KENKYU_HITSUYOUSEI_4"));
                info.setKenkyuHitsuyousei5(resutlt.getString("KENKYU_HITSUYOUSEI_5"));
// 2006/08/25 dyh delete start �����F�d�l�ύX
                //info.setKenkyuSyokei1(resutlt.getString("KENKYU_SYOKEI_1"));
                //info.setKenkyuUtiwake1(resutlt.getString("KENKYU_UTIWAKE_1"));
// 2006/08/25 dyh delete end
                info.setKenkyuSyokei2(resutlt.getString("KENKYU_SYOKEI_2"));
                info.setKenkyuUtiwake2(resutlt.getString("KENKYU_UTIWAKE_2"));
                info.setKenkyuSyokei3(resutlt.getString("KENKYU_SYOKEI_3"));
                info.setKenkyuUtiwake3(resutlt.getString("KENKYU_UTIWAKE_3"));
                info.setKenkyuSyokei4(resutlt.getString("KENKYU_SYOKEI_4"));
                info.setKenkyuUtiwake4(resutlt.getString("KENKYU_UTIWAKE_4"));
                info.setKenkyuSyokei5(resutlt.getString("KENKYU_SYOKEI_5"));
                info.setKenkyuUtiwake5(resutlt.getString("KENKYU_UTIWAKE_5"));
                info.setKenkyuSyokei6(resutlt.getString("KENKYU_SYOKEI_6"));
                info.setKenkyuUtiwake6(resutlt.getString("KENKYU_UTIWAKE_6"));
                info.setDaihyouZip(resutlt.getString("DAIHYOU_ZIP"));
                info.setDaihyouAddress(resutlt.getString("DAIHYOU_ADDRESS"));
                info.setDaihyouTel(resutlt.getString("DAIHYOU_TEL"));
                info.setDaihyouFax(resutlt.getString("DAIHYOU_FAX"));
                info.setDaihyouEmail(resutlt.getString("DAIHYOU_EMAIL"));
                info.setJimutantoNameKanjiSei(resutlt.getString("JIMUTANTO_NAME_KANJI_SEI"));
                info.setJimutantoNameKanjiMei(resutlt.getString("JIMUTANTO_NAME_KANJI_MEI"));
                info.setJimutantoNameKanaSei(resutlt.getString("JIMUTANTO_NAME_KANA_SEI"));
                info.setJimutantoNameKanaMei(resutlt.getString("JIMUTANTO_NAME_KANA_MEI"));
                info.setJimutantoShozokuCd(resutlt.getString("JIMUTANTO_SHOZOKU_CD"));
                info.setJimutantoShozokuName(resutlt.getString("JIMUTANTO_SHOZOKU_NAME"));
                info.setJimutantoBukyokuCd(resutlt.getString("JIMUTANTO_BUKYOKU_CD"));
                info.setJimutantoBukyokuName(resutlt.getString("JIMUTANTO_BUKYOKU_NAME"));
                info.setJimutantoShokushuCd(resutlt.getString("JIMUTANTO_SHOKUSHU_CD"));
                info.setJimutantoShokushuNameKanji(resutlt.getString("JIMUTANTO_SHOKUSHU_NAME_KANJI"));
                info.setJimutantoZip(resutlt.getString("JIMUTANTO_ZIP"));
                info.setJimutantoAddress(resutlt.getString("JIMUTANTO_ADDRESS"));
                info.setJimutantoTel(resutlt.getString("JIMUTANTO_TEL"));
                info.setJimutantoFax(resutlt.getString("JIMUTANTO_FAX"));
                info.setJimutantoEmail(resutlt.getString("JIMUTANTO_EMAIL"));
                info.setKanrenShimei1(resutlt.getString("KANREN_SHIMEI1"));
                info.setKanrenKikan1(resutlt.getString("KANREN_KIKAN1"));
                info.setKanrenBukyoku1(resutlt.getString("KANREN_BUKYOKU1"));
                info.setKanrenShoku1(resutlt.getString("KANREN_SHOKU1"));
                info.setKanrenSenmon1(resutlt.getString("KANREN_SENMON1"));
                info.setKanrenTel1(resutlt.getString("KANREN_TEL1"));
                info.setKanrenJitakutel1(resutlt.getString("KANREN_JITAKUTEL1"));
                info.setKanrenShimei2(resutlt.getString("KANREN_SHIMEI2"));
                info.setKanrenKikan2(resutlt.getString("KANREN_KIKAN2"));
                info.setKanrenBukyoku2(resutlt.getString("KANREN_BUKYOKU2"));
                info.setKanrenShoku2(resutlt.getString("KANREN_SHOKU2"));
                info.setKanrenSenmon2(resutlt.getString("KANREN_SENMON2"));
                info.setKanrenTel2(resutlt.getString("KANREN_TEL2"));
                info.setKanrenJitakutel2(resutlt.getString("KANREN_JITAKUTEL2"));
                info.setKanrenShimei3(resutlt.getString("KANREN_SHIMEI3"));
                info.setKanrenKikan3(resutlt.getString("KANREN_KIKAN3"));
                info.setKanrenBukyoku3(resutlt.getString("KANREN_BUKYOKU3"));
                info.setKanrenShoku3(resutlt.getString("KANREN_SHOKU3"));
                info.setKanrenSenmon3(resutlt.getString("KANREN_SENMON3"));
                info.setKanrenTel3(resutlt.getString("KANREN_TEL3"));
                info.setKanrenJitakutel3(resutlt.getString("KANREN_JITAKUTEL3"));
                info.setPdfPath(resutlt.getString("PDF_PATH"));
                info.setHyoshiPdfPath(resutlt.getString("HYOSHI_PDF_PATH"));
                info.setJuriKekka(resutlt.getString("JURI_KEKKA"));
                info.setJuriBiko(resutlt.getString("JURI_BIKO"));
                info.setRyoikiJokyoId(resutlt.getString("RYOIKI_JOKYO_ID"));
                info.setEdition(resutlt.getString("EDITION"));
                info.setRyoikikeikakushoKakuteiFlg(resutlt.getString("RYOIKIKEIKAKUSHO_KAKUTEI_FLG"));
                info.setCancelFlg(resutlt.getString("CANCEL_FLG"));
                info.setDelFlg(resutlt.getString("DEL_FLG"));
            } else {
                throw new NoDataFoundException(
                        "�̈�v�揑�i�T�v�j���Ǘ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
                        + pkInfo.getRyoikiSystemNo()
                        + "'");
            }
            return info;
        }
        catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        finally {
            DatabaseUtil.closeResource(resutlt, ps);
        }
    }
    

    //2006.09.25 iso iso �^�C�g���Ɂu�T�v�v������PDF�쐬�̂���
    /**
     * �̈�v�揑�i�T�v�j�\����ID���u�󗝁v�̗̈�v�揑��񃊃X�g��Ԃ��B
     * 
     * @param connection �R�l�N�V����
     * @param lock
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    private RyoikiKeikakushoInfo[] selectRyoikiInfos(Connection connection, boolean lock)
            throws DataAccessException, NoDataFoundException {
        
    	//�ʂ�selectRyoikiInfo�Ɠ��������A2006�N�x�����g��Ȃ��@�\�ɂȂ肻���Ȃ̂ŁA�Ɨ������Ȃ��B
    	//���ケ�̃��\�b�h���g�p����Ȃ�ASQL���⌋�ʂ̃Z�b�g�����͓Ɨ�������ׂ��B
        StringBuffer query = new StringBuffer();
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");             // �V�X�e����t�ԍ�
        query.append(" A.KARIRYOIKI_NO,");                // ���̈�ԍ�            
        query.append(" A.UKETUKE_NO,");                   // ��t�ԍ��i�����ԍ��j
        query.append(" A.JIGYO_ID,");                     // ����ID
        query.append(" A.NENDO,");                        // �N�x
        query.append(" A.KAISU,");                        // ��
        query.append(" A.JIGYO_NAME,");                   // ���Ɩ�
        query.append(" A.SHINSEISHA_ID,");                // �\����ID
        query.append(" A.KAKUTEI_DATE,");                 // �̈�v�揑�m���                 
        query.append(" A.SAKUSEI_DATE,");                 // �̈�v�揑�T�v�쐬��
        query.append(" A.SHONIN_DATE,");                  // �����@�֏��F��
        query.append(" A.JYURI_DATE,");                   // �w�U�󗝓�
        query.append(" A.NAME_KANJI_SEI,");               // �̈��\�Ҏ����i������-���j
        query.append(" A.NAME_KANJI_MEI,");               // �̈��\�Ҏ����i������-���j
        query.append(" A.NAME_KANA_SEI,");                // �̈��\�Ҏ����i�t���K�i-���j
        query.append(" A.NAME_KANA_MEI,");                // �̈��\�Ҏ����i�t���K�i-���j
        query.append(" A.NENREI,");                       // �N��
        query.append(" A.KENKYU_NO,");                    // �\���Ҍ����Ҕԍ�
        query.append(" A.SHOZOKU_CD,");                   // �����@�փR�[�h
        query.append(" A.SHOZOKU_NAME,");                 // �����@�֖�
        query.append(" A.SHOZOKU_NAME_RYAKU,");           // �����@�֖��i���́j
        query.append(" A.BUKYOKU_CD,");                   // ���ǃR�[�h
        query.append(" A.BUKYOKU_NAME,");                 // ���ǖ�
        query.append(" A.BUKYOKU_NAME_RYAKU,");           // ���ǖ��i���́j
        query.append(" A.SHOKUSHU_CD,");                  // �E���R�[�h
        query.append(" A.SHOKUSHU_NAME_KANJI,");          // �E���i�a���j
        query.append(" A.SHOKUSHU_NAME_RYAKU,");          // �E���i���́j
        query.append(" A.KIBOUBUMON_CD,");                // �R����]����i�n���j�R�[�h
        query.append(" A.KIBOUBUMON_NAME,");              // �R����]����i�n���j����
        query.append(" A.RYOIKI_NAME,");                  // ����̈於
        query.append(" A.RYOIKI_NAME_EIGO,");             // �p��
        query.append(" A.RYOIKI_NAME_RYAKU,");            // �̈旪�̖�
        query.append(" A.KENKYU_GAIYOU,");                // �����T�v
        query.append(" A.JIZENCHOUSA_FLG,");              // ���O�����̏�
        query.append(" A.JIZENCHOUSA_SONOTA,");           // ���O�����̏󋵁i���̑��j
        query.append(" A.KAKO_OUBOJYOUKYOU,");            // �ߋ��̉����
        query.append(" A.ZENNENDO_OUBO_FLG,");            // �ŏI�N�x�O�N�x�̉���i�Y���̗L���j
        query.append(" A.ZENNENDO_OUBO_NO,");             // �ŏI�N�x�O�N�x�̌����̈�ԍ�
        query.append(" A.ZENNENDO_OUBO_RYOIKI_RYAKU,");   // �ŏI�N�x�O�N�x�̗̈旪�̖�
        query.append(" A.ZENNENDO_OUBO_SETTEI,");         // �ŏI�N�x�O�N�x�̐ݒ����
        query.append(" A.BUNKASAIMOKU_CD1,");             // �֘A����i�זڔԍ��j1
        query.append(" A.BUNYA_NAME1,");                  // �֘A����i����j1
        query.append(" A.BUNKA_NAME1,");                  // �֘A����i���ȁj1
        query.append(" A.SAIMOKU_NAME1,");                // �֘A����i�זځj1
        query.append(" A.BUNKASAIMOKU_CD2,");             // �֘A����i�זڔԍ��j2
        query.append(" A.BUNYA_NAME2,");                  // �֘A����i����j2
        query.append(" A.BUNKA_NAME2,");                  // �֘A����i���ȁj2
        query.append(" A.SAIMOKU_NAME2,");                // �֘A����i�זځj2
        query.append(" A.KANRENBUNYA_BUNRUI_NO,");        // �֘A����15���ށi�ԍ��j
        query.append(" A.KANRENBUNYA_BUNRUI_NAME,");      // �֘A����15���ށi���ޖ��j
        query.append(" A.KENKYU_HITSUYOUSEI_1,");         // �����̕K�v��1
        query.append(" A.KENKYU_HITSUYOUSEI_2,");         // �����̕K�v��2
        query.append(" A.KENKYU_HITSUYOUSEI_3,");         // �����̕K�v��3
        query.append(" A.KENKYU_HITSUYOUSEI_4,");         // �����̕K�v��4
        query.append(" A.KENKYU_HITSUYOUSEI_5,");         // �����̕K�v��5
        query.append(" A.KENKYU_SYOKEI_1,");              // �����o��i1�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_1,");             // �����o��i1�N��)-����
        query.append(" A.KENKYU_SYOKEI_2,");              // �����o��i2�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_2,");             // �����o��i2�N��)-����
        query.append(" A.KENKYU_SYOKEI_3,");              // �����o��i3�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_3,");             // �����o��i3�N��)-����
        query.append(" A.KENKYU_SYOKEI_4,");              // �����o��i4�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_4,");             // �����o��i4�N��)-����
        query.append(" A.KENKYU_SYOKEI_5,");              // �����o��i5�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_5,");             // �����o��i5�N��)-����
        query.append(" A.KENKYU_SYOKEI_6,");              // �����o��i6�N��)-���v
        query.append(" A.KENKYU_UTIWAKE_6,");             // �����o��i6�N��)-����
        query.append(" A.DAIHYOU_ZIP,");                  // �̈��\�ҁi�X�֔ԍ��j
        query.append(" A.DAIHYOU_ADDRESS,");              // �̈��\�ҁi�Z���j
        query.append(" A.DAIHYOU_TEL,");                  // �̈��\�ҁi�d�b�j
        query.append(" A.DAIHYOU_FAX,");                  // �̈��\�ҁiFAX�j
        query.append(" A.DAIHYOU_EMAIL,");                // �̈��\�ҁi���[���A�h���X�j
        query.append(" A.JIMUTANTO_NAME_KANJI_SEI,");     // �����S���Ҏ����i������-���j
        query.append(" A.JIMUTANTO_NAME_KANJI_MEI,");     // �����S���Ҏ����i������-���j
        query.append(" A.JIMUTANTO_NAME_KANA_SEI,");      // �����S���Ҏ����i�t���K�i-���j
        query.append(" A.JIMUTANTO_NAME_KANA_MEI,");      // �����S���Ҏ����i�t���K�i-���j
        query.append(" A.JIMUTANTO_SHOZOKU_CD,");         // �����S���Ҍ����@�֔ԍ�
        query.append(" A.JIMUTANTO_SHOZOKU_NAME,");       // �����S���Ҍ����@�֖�
        query.append(" A.JIMUTANTO_BUKYOKU_CD,");         // �����S���ҕ��ǔԍ�
        query.append(" A.JIMUTANTO_BUKYOKU_NAME,");       // �����S���ҕ��ǖ�
        query.append(" A.JIMUTANTO_SHOKUSHU_CD,");        // �����S���ҐE���ԍ�
        query.append(" A.JIMUTANTO_SHOKUSHU_NAME_KANJI,");// �����S���ҐE���i�a���j
        query.append(" A.JIMUTANTO_ZIP,");                // �����S���ҁi�X�֔ԍ��j
        query.append(" A.JIMUTANTO_ADDRESS,");            // �����S���ҁi�Z���j
        query.append(" A.JIMUTANTO_TEL,");                // �����S���ҁi�d�b�j
        query.append(" A.JIMUTANTO_FAX,");                // �����S���ҁiFAX�j
        query.append(" A.JIMUTANTO_EMAIL,");              // �����S���ҁi���[���A�h���X�j
        query.append(" A.KANREN_SHIMEI1,");               // �֘A����̌�����-����1
        query.append(" A.KANREN_KIKAN1,");                // �֘A����̌�����-���������@��1
        query.append(" A.KANREN_BUKYOKU1,");              // �֘A����̌�����-����1
        query.append(" A.KANREN_SHOKU1,");                // �֘A����̌�����-�E��1
        query.append(" A.KANREN_SENMON1,");               // �֘A����̌�����-��啪��1
        query.append(" A.KANREN_TEL1,");                  // �֘A����̌�����-�Ζ���d�b�ԍ�1
        query.append(" A.KANREN_JITAKUTEL1,");            // �֘A����̌�����-����d�b�ԍ�1
        query.append(" A.KANREN_SHIMEI2,");               // �֘A����̌�����-����2
        query.append(" A.KANREN_KIKAN2,");                // �֘A����̌�����-���������@��2
        query.append(" A.KANREN_BUKYOKU2,");              // �֘A����̌�����-����2                             
        query.append(" A.KANREN_SHOKU2,");                // �֘A����̌�����-�E��2
        query.append(" A.KANREN_SENMON2,");               // �֘A����̌�����-��啪��2
        query.append(" A.KANREN_TEL2,");                  // �֘A����̌�����-�Ζ���d�b�ԍ�2
        query.append(" A.KANREN_JITAKUTEL2,");            // �֘A����̌�����-����d�b�ԍ�2
        query.append(" A.KANREN_SHIMEI3,");               // �֘A����̌�����-����3
        query.append(" A.KANREN_KIKAN3,");                // �֘A����̌�����-���������@��3
        query.append(" A.KANREN_BUKYOKU3,");              // �֘A����̌�����-����3                             
        query.append(" A.KANREN_SHOKU3,");                // �֘A����̌�����-�E��3
        query.append(" A.KANREN_SENMON3,");               // �֘A����̌�����-��啪��3
        query.append(" A.KANREN_TEL3,");                  // �֘A����̌�����-�Ζ���d�b�ԍ�3
        query.append(" A.KANREN_JITAKUTEL3,");            // �֘A����̌�����-����d�b�ԍ�3
        query.append(" A.PDF_PATH,");                     // �̈�v�揑�T�vPDF�̊i�[�p�X
        query.append(" A.HYOSHI_PDF_PATH,");              // �\��PDF�i�[�p�X
        query.append(" A.JURI_KEKKA,");                   // �󗝌���
        query.append(" A.JURI_BIKO,");                    // �󗝌��ʔ��l
        query.append(" A.RYOIKI_JOKYO_ID,");              // �̈�v�揑�i�T�v�j�\����ID                    
        query.append(" A.EDITION,");                      // ��
        query.append(" A.RYOIKIKEIKAKUSHO_KAKUTEI_FLG,"); // �m��t���O
        query.append(" A.CANCEL_FLG,");                   // �����t���O
        query.append(" A.DEL_FLG ");                      // �폜�t���O
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" A");
        query.append(" WHERE A.RYOIKI_JOKYO_ID IN ('" + StatusCode.STATUS_GAKUSIN_JYURI + "')");		//����󋵁F��	2006�N�x�̂ݎg���@�\�Ȃ̂łׂ�����
        query.append(" AND DEL_FLG = 0");

        //�r��������s���ꍇ
        if(lock){
            query.append(" FOR UPDATE");
        }

        // printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        
        // -----------------------
        // ���X�g�擾
        // -----------------------
        PreparedStatement ps = null;
        ResultSet result = null;

        RyoikiKeikakushoInfo[] ryoikiKeikakushoInfos = null;
        try {
            ps = connection.prepareStatement(query.toString());
            result = ps.executeQuery();

            List resultList = new ArrayList();
            while(result.next()) {
                RyoikiKeikakushoInfo info = new RyoikiKeikakushoInfo();
                info.setRyoikiSystemNo(result.getString("RYOIKI_SYSTEM_NO"));
                info.setKariryoikiNo(result.getString("KARIRYOIKI_NO"));
                info.setUketukeNo(result.getString("UKETUKE_NO").trim());		//�󔒂����Ă���̂�trim��������
                info.setJigyoId(result.getString("JIGYO_ID"));
                info.setJigyoCd(result.getString("JIGYO_ID").substring(2,7));
                info.setNendo(result.getString("NENDO"));
                info.setKaisu(result.getString("KAISU"));
                info.setJigyoName(result.getString("JIGYO_NAME"));
                info.setShinseishaId(result.getString("SHINSEISHA_ID"));
                info.setKakuteiDate(result.getDate("KAKUTEI_DATE"));
                info.setSakuseiDate(result.getDate("SAKUSEI_DATE"));
                info.setShoninDate(result.getDate("SHONIN_DATE"));
                info.setJyuriDate(result.getDate("JYURI_DATE"));
                info.setNameKanjiSei(result.getString("NAME_KANJI_SEI"));
                info.setNameKanjiMei(result.getString("NAME_KANJI_MEI"));
                info.setNameKanaSei(result.getString("NAME_KANA_SEI"));
                info.setNameKanaMei(result.getString("NAME_KANA_MEI"));
                info.setNenrei(result.getString("NENREI"));
                info.setKenkyuNo(result.getString("KENKYU_NO"));
                info.setShozokuCd(result.getString("SHOZOKU_CD"));
                info.setShozokuName(result.getString("SHOZOKU_NAME"));
                info.setShozokuNameRyaku(result.getString("SHOZOKU_NAME_RYAKU"));
                info.setBukyokuCd(result.getString("BUKYOKU_CD"));
                info.setBukyokuName(result.getString("BUKYOKU_NAME"));
                info.setBukyokuNameRyaku(result.getString("BUKYOKU_NAME_RYAKU"));
                info.setShokushuCd(result.getString("SHOKUSHU_CD"));
                info.setShokushuNameKanji(result.getString("SHOKUSHU_NAME_KANJI"));
                info.setShokushuNameRyaku(result.getString("SHOKUSHU_NAME_RYAKU"));
                info.setKiboubumonCd(result.getString("KIBOUBUMON_CD"));
                info.setKiboubumonName(result.getString("KIBOUBUMON_NAME"));
                info.setRyoikiName(result.getString("RYOIKI_NAME"));
                info.setRyoikiNameEigo(result.getString("RYOIKI_NAME_EIGO"));
                info.setRyoikiNameRyaku(result.getString("RYOIKI_NAME_RYAKU"));
                info.setKenkyuGaiyou(result.getString("KENKYU_GAIYOU"));
                info.setJizenchousaFlg(result.getString("JIZENCHOUSA_FLG"));
                info.setJizenchousaSonota(result.getString("JIZENCHOUSA_SONOTA"));
                info.setKakoOubojyoukyou(result.getString("KAKO_OUBOJYOUKYOU"));
                info.setZennendoOuboFlg(result.getString("ZENNENDO_OUBO_FLG"));
                info.setZennendoOuboNo(result.getString("ZENNENDO_OUBO_NO"));
                info.setZennendoOuboRyoikiRyaku(result.getString("ZENNENDO_OUBO_RYOIKI_RYAKU"));
                info.setZennendoOuboSettei(result.getString("ZENNENDO_OUBO_SETTEI"));
                info.setBunkasaimokuCd1(result.getString("BUNKASAIMOKU_CD1"));
                info.setBunyaName1(result.getString("BUNYA_NAME1"));
                info.setBunkaName1(result.getString("BUNKA_NAME1"));
                info.setSaimokuName1(result.getString("SAIMOKU_NAME1"));
                info.setBunkasaimokuCd2(result.getString("BUNKASAIMOKU_CD2"));
                info.setBunyaName2(result.getString("BUNYA_NAME2"));
                info.setBunkaName2(result.getString("BUNKA_NAME2"));
                info.setSaimokuName2(result.getString("SAIMOKU_NAME2"));
                info.setKanrenbunyaBunruiNo(result.getString("KANRENBUNYA_BUNRUI_NO"));
                info.setKanrenbunyaBunruiName(result.getString("KANRENBUNYA_BUNRUI_NAME"));
                info.setKenkyuHitsuyousei1(result.getString("KENKYU_HITSUYOUSEI_1"));
                info.setKenkyuHitsuyousei2(result.getString("KENKYU_HITSUYOUSEI_2"));
                info.setKenkyuHitsuyousei3(result.getString("KENKYU_HITSUYOUSEI_3"));
                info.setKenkyuHitsuyousei4(result.getString("KENKYU_HITSUYOUSEI_4"));
                info.setKenkyuHitsuyousei5(result.getString("KENKYU_HITSUYOUSEI_5"));
                info.setKenkyuSyokei2(result.getString("KENKYU_SYOKEI_2"));
                info.setKenkyuUtiwake2(result.getString("KENKYU_UTIWAKE_2"));
                info.setKenkyuSyokei3(result.getString("KENKYU_SYOKEI_3"));
                info.setKenkyuUtiwake3(result.getString("KENKYU_UTIWAKE_3"));
                info.setKenkyuSyokei4(result.getString("KENKYU_SYOKEI_4"));
                info.setKenkyuUtiwake4(result.getString("KENKYU_UTIWAKE_4"));
                info.setKenkyuSyokei5(result.getString("KENKYU_SYOKEI_5"));
                info.setKenkyuUtiwake5(result.getString("KENKYU_UTIWAKE_5"));
                info.setKenkyuSyokei6(result.getString("KENKYU_SYOKEI_6"));
                info.setKenkyuUtiwake6(result.getString("KENKYU_UTIWAKE_6"));
                info.setDaihyouZip(result.getString("DAIHYOU_ZIP"));
                info.setDaihyouAddress(result.getString("DAIHYOU_ADDRESS"));
                info.setDaihyouTel(result.getString("DAIHYOU_TEL"));
                info.setDaihyouFax(result.getString("DAIHYOU_FAX"));
                info.setDaihyouEmail(result.getString("DAIHYOU_EMAIL"));
                info.setJimutantoNameKanjiSei(result.getString("JIMUTANTO_NAME_KANJI_SEI"));
                info.setJimutantoNameKanjiMei(result.getString("JIMUTANTO_NAME_KANJI_MEI"));
                info.setJimutantoNameKanaSei(result.getString("JIMUTANTO_NAME_KANA_SEI"));
                info.setJimutantoNameKanaMei(result.getString("JIMUTANTO_NAME_KANA_MEI"));
                info.setJimutantoShozokuCd(result.getString("JIMUTANTO_SHOZOKU_CD"));
                info.setJimutantoShozokuName(result.getString("JIMUTANTO_SHOZOKU_NAME"));
                info.setJimutantoBukyokuCd(result.getString("JIMUTANTO_BUKYOKU_CD"));
                info.setJimutantoBukyokuName(result.getString("JIMUTANTO_BUKYOKU_NAME"));
                info.setJimutantoShokushuCd(result.getString("JIMUTANTO_SHOKUSHU_CD"));
                info.setJimutantoShokushuNameKanji(result.getString("JIMUTANTO_SHOKUSHU_NAME_KANJI"));
                info.setJimutantoZip(result.getString("JIMUTANTO_ZIP"));
                info.setJimutantoAddress(result.getString("JIMUTANTO_ADDRESS"));
                info.setJimutantoTel(result.getString("JIMUTANTO_TEL"));
                info.setJimutantoFax(result.getString("JIMUTANTO_FAX"));
                info.setJimutantoEmail(result.getString("JIMUTANTO_EMAIL"));
                info.setKanrenShimei1(result.getString("KANREN_SHIMEI1"));
                info.setKanrenKikan1(result.getString("KANREN_KIKAN1"));
                info.setKanrenBukyoku1(result.getString("KANREN_BUKYOKU1"));
                info.setKanrenShoku1(result.getString("KANREN_SHOKU1"));
                info.setKanrenSenmon1(result.getString("KANREN_SENMON1"));
                info.setKanrenTel1(result.getString("KANREN_TEL1"));
                info.setKanrenJitakutel1(result.getString("KANREN_JITAKUTEL1"));
                info.setKanrenShimei2(result.getString("KANREN_SHIMEI2"));
                info.setKanrenKikan2(result.getString("KANREN_KIKAN2"));
                info.setKanrenBukyoku2(result.getString("KANREN_BUKYOKU2"));
                info.setKanrenShoku2(result.getString("KANREN_SHOKU2"));
                info.setKanrenSenmon2(result.getString("KANREN_SENMON2"));
                info.setKanrenTel2(result.getString("KANREN_TEL2"));
                info.setKanrenJitakutel2(result.getString("KANREN_JITAKUTEL2"));
                info.setKanrenShimei3(result.getString("KANREN_SHIMEI3"));
                info.setKanrenKikan3(result.getString("KANREN_KIKAN3"));
                info.setKanrenBukyoku3(result.getString("KANREN_BUKYOKU3"));
                info.setKanrenShoku3(result.getString("KANREN_SHOKU3"));
                info.setKanrenSenmon3(result.getString("KANREN_SENMON3"));
                info.setKanrenTel3(result.getString("KANREN_TEL3"));
                info.setKanrenJitakutel3(result.getString("KANREN_JITAKUTEL3"));
                info.setPdfPath(result.getString("PDF_PATH"));
                info.setHyoshiPdfPath(result.getString("HYOSHI_PDF_PATH"));
                info.setJuriKekka(result.getString("JURI_KEKKA"));
                info.setJuriBiko(result.getString("JURI_BIKO"));
                info.setRyoikiJokyoId(result.getString("RYOIKI_JOKYO_ID"));
                info.setEdition(result.getString("EDITION"));
                info.setRyoikikeikakushoKakuteiFlg(result.getString("RYOIKIKEIKAKUSHO_KAKUTEI_FLG"));
                info.setCancelFlg(result.getString("CANCEL_FLG"));
                info.setDelFlg(result.getString("DEL_FLG"));
                resultList.add(info);
            }
            //�߂�l
            ryoikiKeikakushoInfos = (RyoikiKeikakushoInfo[])resultList.toArray(new RyoikiKeikakushoInfo[0]);
            if(ryoikiKeikakushoInfos.length == 0){
                throw new NoDataFoundException(
                    "�̈�v�揑�Ǘ��e�[�u���ɊY������f�[�^��������܂���B����󋵁F�w�U��'"
                        + Arrays.asList(ryoikiKeikakushoInfos).toString()
                        + "'");
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
        finally {
            DatabaseUtil.closeResource(result, ps);
        }
        return ryoikiKeikakushoInfos;
    }
    
    
    /**
     * �̈�v�揑�i�T�v�j�����擾����B
     * �폜�t���O���u0�v�̏ꍇ�i�폜����Ă��Ȃ��ꍇ�j�̂݌�������B
     * �擾�������R�[�h�����b�N����B�icomit���s����܂ŁB�j
     * @param connection            �R�l�N�V����
     * @param primaryKey            ��L�[���
     * @return                      �����@�֏��
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public RyoikiKeikakushoInfo selectRyoikiKeikakushoInfoForLock(
        Connection connection,
        RyoikiKeikakushoPk primaryKey)
        throws DataAccessException, NoDataFoundException {
        
        return selectRyoikiInfo(connection, primaryKey, true);
    }
    
    /**
     * �̈�v�揑�i�T�v�j�����擾����B
     * �폜�t���O���u0�v�̏ꍇ�i�폜����Ă��Ȃ��ꍇ�j�̂݌�������B
     * @param connection            �R�l�N�V����
     * @param primaryKey            ��L�[���
     * @return                      �����@�֏��
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public RyoikiKeikakushoInfo selectRyoikiKeikakushoDataInfo(
        Connection connection,
        RyoikiKeikakushoPk primaryKey)
        throws DataAccessException, NoDataFoundException {
        
        return selectRyoikiInfo(connection, primaryKey, false);
    }

    //2006.09.25 iso iso �^�C�g���Ɂu�T�v�v������PDF�쐬�̂���
    //�����b�N�����͖�����
    /**
     * �̈�v�揑��񃊃X�g���擾����B
     * �����񂪁u�󗝁v�̏ꍇ�̂݌�������B
     * @param connection            �R�l�N�V����
     * @return                      �̈�v�揑��񃊃X�g
     * @throws DataAccessException  �f�[�^�擾���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public RyoikiKeikakushoInfo[] selectRyoikiKeikakushoDataInfosForLock(Connection connection)
        	throws DataAccessException, NoDataFoundException {
        
        return selectRyoikiInfos(connection, false);
    }
    
    /**
     * �̈�v�揑�i�T�v�j����o�^����B
     * @param connection                �R�l�N�V����
     * @param addInfo                   �o�^����\�����
     * @throws DataAccessException      �o�^���ɗ�O�����������ꍇ�B
     * @throws DuplicateKeyException    �L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ
     */
    public void insertRyoikiKeikakushoInfo(Connection connection,
            RyoikiKeikakushoInfo addInfo)
            throws DataAccessException, DuplicateKeyException {

        //�L�[�d���`�F�b�N
        try {
            selectRyoikiKeikakushoDataInfo(connection, addInfo);
            //NG
            throw new DuplicateKeyException(
                "'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
        } catch (NoDataFoundException e) {
            //OK
        }
        
        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink);
        query.append(" (");
        query.append(" RYOIKI_SYSTEM_NO   ");// �V�X�e����t�ԍ�
        query.append(",KARIRYOIKI_NO      ");// ���̈�ԍ�
        query.append(",UKETUKE_NO         ");// ��t�ԍ��i�����ԍ��j
        query.append(",JIGYO_ID           ");// ����ID
        query.append(",NENDO              ");// �N�x
        query.append(",KAISU              ");// ��
        query.append(",JIGYO_NAME         ");// ���Ɩ�
        query.append(",SHINSEISHA_ID      ");// �\����ID
        query.append(",KAKUTEI_DATE       ");// �̈�v�揑�m���
        query.append(",SAKUSEI_DATE       ");// �̈�v�揑�T�v�쐬��
        query.append(",SHONIN_DATE        ");// �����@�֏��F��
        query.append(",JYURI_DATE         ");// �w�U�󗝓�
        query.append(",NAME_KANJI_SEI     ");// �̈��\�Ҏ����i������-���j
        query.append(",NAME_KANJI_MEI     ");// �̈��\�Ҏ����i������-���j
        query.append(",NAME_KANA_SEI      ");// �̈��\�Ҏ����i�t���K�i-���j
        query.append(",NAME_KANA_MEI      ");// �̈��\�Ҏ����i�t���K�i-���j
        query.append(",NENREI             ");// �N��
        query.append(",KENKYU_NO          ");// �\���Ҍ����Ҕԍ�
        query.append(",SHOZOKU_CD         ");// �����@�փR�[�h
        query.append(",SHOZOKU_NAME       ");// �����@�֖�
        query.append(",SHOZOKU_NAME_RYAKU ");// �����@�֖��i���́j
        query.append(",BUKYOKU_CD         ");// ���ǃR�[�h
        query.append(",BUKYOKU_NAME       ");// ���ǖ�
        query.append(",BUKYOKU_NAME_RYAKU ");// ���ǖ��i���́j
        query.append(",SHOKUSHU_CD        ");// �E���R�[�h
        query.append(",SHOKUSHU_NAME_KANJI");// �E���i�a���j
        query.append(",SHOKUSHU_NAME_RYAKU");// �E���i���́j
        query.append(",KIBOUBUMON_CD      ");// �R����]����i�n���j�R�[�h
        query.append(",KIBOUBUMON_NAME    ");// �R����]����i�n���j����
        query.append(",RYOIKI_NAME        ");// ����̈於
        query.append(",RYOIKI_NAME_EIGO   ");// �p��
        query.append(",RYOIKI_NAME_RYAKU  ");// �̈旪�̖�
        query.append(",KENKYU_GAIYOU      ");// �����T�v
        query.append(",JIZENCHOUSA_FLG    ");// ���O�����̏�
        query.append(",JIZENCHOUSA_SONOTA ");// ���O�����̏󋵁i���̑��j
        query.append(",KAKO_OUBOJYOUKYOU  ");// �ߋ��̉����
        query.append(",ZENNENDO_OUBO_FLG  ");// �ŏI�N�x�O�N�x�̉���i�Y���̗L���j
        query.append(",ZENNENDO_OUBO_NO          ");// �ŏI�N�x�O�N�x�̌����̈�ԍ�
        query.append(",ZENNENDO_OUBO_RYOIKI_RYAKU");// �ŏI�N�x�O�N�x�̗̈旪�̖�
        query.append(",ZENNENDO_OUBO_SETTEI      ");// �ŏI�N�x�O�N�x�̐ݒ����
        query.append(",BUNKASAIMOKU_CD1          ");// �֘A����i�זڔԍ��j1
        query.append(",BUNYA_NAME1               ");// �֘A����i����j1
        query.append(",BUNKA_NAME1               ");// �֘A����i���ȁj1
        query.append(",SAIMOKU_NAME1             ");// �֘A����i�זځj1
        query.append(",BUNKASAIMOKU_CD2          ");// �֘A����i�זڔԍ��j2
        query.append(",BUNYA_NAME2               ");// �֘A����i����j2
        query.append(",BUNKA_NAME2               ");// �֘A����i���ȁj2
        query.append(",SAIMOKU_NAME2             ");// �֘A����i�זځj2
        query.append(",KANRENBUNYA_BUNRUI_NO     ");// �֘A����15���ށi�ԍ��j
        query.append(",KANRENBUNYA_BUNRUI_NAME   ");// �֘A����15���ށi���ޖ��j
        query.append(",KENKYU_HITSUYOUSEI_1      ");// �����̕K�v��1
        query.append(",KENKYU_HITSUYOUSEI_2      ");// �����̕K�v��2
        query.append(",KENKYU_HITSUYOUSEI_3      ");// �����̕K�v��3
        query.append(",KENKYU_HITSUYOUSEI_4      ");// �����̕K�v��4
        query.append(",KENKYU_HITSUYOUSEI_5      ");// �����̕K�v��5
        query.append(",KENKYU_SYOKEI_1           ");// �����o��i1�N��)-���v
        query.append(",KENKYU_UTIWAKE_1          ");// �����o��i1�N��)-����
        query.append(",KENKYU_SYOKEI_2           ");// �����o��i2�N��)-���v
        query.append(",KENKYU_UTIWAKE_2          ");// �����o��i2�N��)-����
        query.append(",KENKYU_SYOKEI_3           ");// �����o��i3�N��)-���v
        query.append(",KENKYU_UTIWAKE_3          ");// �����o��i3�N��)-����
        query.append(",KENKYU_SYOKEI_4           ");// �����o��i4�N��)-���v
        query.append(",KENKYU_UTIWAKE_4          ");// �����o��i4�N��)-����
        query.append(",KENKYU_SYOKEI_5           ");// �����o��i5�N��)-���v
        query.append(",KENKYU_UTIWAKE_5          ");// �����o��i5�N��)-����
        query.append(",KENKYU_SYOKEI_6           ");// �����o��i6�N��)-���v
        query.append(",KENKYU_UTIWAKE_6          ");// �����o��i6�N��)-����
        query.append(",DAIHYOU_ZIP                  ");// �̈��\�ҁi�X�֔ԍ��j
        query.append(",DAIHYOU_ADDRESS              ");// �̈��\�ҁi�Z���j
        query.append(",DAIHYOU_TEL                  ");// �̈��\�ҁi�d�b�j
        query.append(",DAIHYOU_FAX                  ");// �̈��\�ҁiFAX�j
        query.append(",DAIHYOU_EMAIL                ");// �̈��\�ҁi���[���A�h���X�j
        query.append(",JIMUTANTO_NAME_KANJI_SEI     ");// �����S���Ҏ����i������-���j
        query.append(",JIMUTANTO_NAME_KANJI_MEI     ");// �����S���Ҏ����i������-���j
        query.append(",JIMUTANTO_NAME_KANA_SEI      ");// �����S���Ҏ����i�t���K�i-���j
        query.append(",JIMUTANTO_NAME_KANA_MEI      ");// �����S���Ҏ����i�t���K�i-���j
        query.append(",JIMUTANTO_SHOZOKU_CD         ");// �����S���Ҍ����@�֔ԍ�
        query.append(",JIMUTANTO_SHOZOKU_NAME       ");// �����S���Ҍ����@�֖�
        query.append(",JIMUTANTO_BUKYOKU_CD         ");// �����S���ҕ��ǔԍ�
        query.append(",JIMUTANTO_BUKYOKU_NAME       ");// �����S���ҕ��ǖ�
        query.append(",JIMUTANTO_SHOKUSHU_CD        ");// �����S���ҐE���ԍ�
        query.append(",JIMUTANTO_SHOKUSHU_NAME_KANJI");// �����S���ҐE���i�a���j
        query.append(",JIMUTANTO_ZIP                ");// �����S���ҁi�X�֔ԍ��j
        query.append(",JIMUTANTO_ADDRESS            ");// �����S���ҁi�Z���j
        query.append(",JIMUTANTO_TEL                ");// �����S���ҁi�d�b�j
        query.append(",JIMUTANTO_FAX                ");// �����S���ҁiFAX�j
        query.append(",JIMUTANTO_EMAIL              ");// �����S���ҁi���[���A�h���X�j
        query.append(",KANREN_SHIMEI1               ");// �֘A����̌�����-����1
        query.append(",KANREN_KIKAN1                ");// �֘A����̌�����-���������@��1
        query.append(",KANREN_BUKYOKU1              ");// �֘A����̌�����-����1
        query.append(",KANREN_SHOKU1                ");// �֘A����̌�����-�E��1
        query.append(",KANREN_SENMON1               ");// �֘A����̌�����-��啪��1
        query.append(",KANREN_TEL1                  ");// �֘A����̌�����-�Ζ���d�b�ԍ�1
        query.append(",KANREN_JITAKUTEL1            ");// �֘A����̌�����-����d�b�ԍ�1
        query.append(",KANREN_SHIMEI2               ");// �֘A����̌�����-����2
        query.append(",KANREN_KIKAN2                ");// �֘A����̌�����-���������@��2
        query.append(",KANREN_BUKYOKU2              ");// �֘A����̌�����-����2
        query.append(",KANREN_SHOKU2                ");// �֘A����̌�����-�E��2
        query.append(",KANREN_SENMON2               ");// �֘A����̌�����-��啪��2
        query.append(",KANREN_TEL2                  ");// �֘A����̌�����-�Ζ���d�b�ԍ�2
        query.append(",KANREN_JITAKUTEL2            ");// �֘A����̌�����-����d�b�ԍ�2
        query.append(",KANREN_SHIMEI3               ");// �֘A����̌�����-����3
        query.append(",KANREN_KIKAN3                ");// �֘A����̌�����-���������@��3
        query.append(",KANREN_BUKYOKU3              ");// �֘A����̌�����-����3
        query.append(",KANREN_SHOKU3                ");// �֘A����̌�����-�E��3
        query.append(",KANREN_SENMON3               ");// �֘A����̌�����-��啪��3
        query.append(",KANREN_TEL3                  ");// �֘A����̌�����-�Ζ���d�b�ԍ�3
        query.append(",KANREN_JITAKUTEL3            ");// �֘A����̌�����-����d�b�ԍ�3
        query.append(",PDF_PATH                     ");// �̈�v�揑�T�vPDF�̊i�[�p�X
        query.append(",HYOSHI_PDF_PATH              ");// �\��PDF�i�[�p�X
        query.append(",JURI_KEKKA                   ");// �󗝌���
        query.append(",JURI_BIKO                    ");// �󗝌��ʔ��l
        query.append(",RYOIKI_JOKYO_ID              ");// �̈�v�揑�i�T�v�j�\����ID
        query.append(",EDITION                      ");// ��
        query.append(",RYOIKIKEIKAKUSHO_KAKUTEI_FLG ");// �m��t���O
        query.append(",CANCEL_FLG                   ");// �����t���O
        query.append(",DEL_FLG                      ");// �폜�t���O
        query.append(" ) ");
        query.append("VALUES");
        query.append(" (");
        query.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"); // 25��
        query.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"); // 50��
        query.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"); // 75��
        query.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"); // 100��
        query.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"); // 125��
        query.append(" )");

        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        PreparedStatement preparedStatement = null;
        try {
            //�o�^
            preparedStatement = connection.prepareStatement(query.toString());
            this.setAllParameter(preparedStatement, addInfo);           
            DatabaseUtil.executeUpdate(preparedStatement);
        } catch (SQLException ex) {
            log.error("�̈�v�揑�i�T�v�j���o�^���ɗ�O���������܂����B ", ex);
            log.info(addInfo);
            throw new DataAccessException("�̈�v�揑�i�T�v�j���o�^���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
//2006/06/27 �c�@�ǉ������܂� 

//2006/07/17 �c�@�ǉ���������
    /**
     * �̈�v�揑�i�T�v�j�L�[�����ΏۂƂȂ�IOD�t�@�C�����擾����B
     * @param connection            �R�l�N�V����
     * @param pkInfo                �̈�v�揑�i�T�v�j�L�[���
     * @return                      �ϊ�����IOF�t�@�C�����X�g
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List getIodFilesToMerge(Connection connection, final RyoikiKeikakushoPk pkInfo)
        throws NoDataFoundException, DataAccessException {
    
        //1.�t�@�C���擾�pSQL��
        final StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" 0 SEQ_NUM,0 SEQ_TENPU,D.PDF_PATH IOD_FILE_PATH ");
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" D ");
        query.append("WHERE D.RYOIKI_SYSTEM_NO = ? ");
        query.append("UNION ALL ");
        query.append("SELECT");
        query.append(" 1 SEQ_NUM,A.SEQ_TENPU SEQ_TENPU,A.PDF_PATH IOD_FILE_PATH ");
        query.append("FROM");
        query.append(" TENPUFILEINFO").append(dbLink).append(" A ");
        query.append("WHERE A.SYSTEM_NO = ? ");
        query.append("ORDER BY SEQ_NUM,SEQ_TENPU");

        if(log.isDebugEnabled()){
            log.debug("�̈�v�揑�i�T�v�j�L�[��� '" + pkInfo);
        }
        
        //2.�����pSQL�쐬�I�u�W�F�N�g�𐶐�����B        
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                throws SQLException {
                PreparedStatement statement = conn.prepareStatement(query.toString());
                int i = 1;
                DatabaseUtil.setParameter(statement,i++,pkInfo.getRyoikiSystemNo());
                DatabaseUtil.setParameter(statement,i++,pkInfo.getRyoikiSystemNo());
                //�V�X�e���ԍ�
                return statement;
            }
        };
        //3.�\���f�[�^���ԃt�@�C���E�Y�t�t�@�C�����ԃt�@�C�����擾����B  
        final List iodFile = new ArrayList();
        
        //4.���������s����B
        RowCallbackHandler handler = new BaseCallbackHandler() {
            protected void processRow(ResultSet rs, int rowNum)
                throws SQLException, NoDataFoundException {
                String iodFilePath = rs.getString("IOD_FILE_PATH");
                if (iodFilePath == null || "".equals(iodFilePath)) {
                    throw new NoDataFoundException("�̈�v�揑�i�T�v�jIOD�t�@�C����񂪌�����܂���ł����B�����L�[�F�V�X�e����t�ԍ�'"
                    + pkInfo.getRyoikiSystemNo()
                    + "'");
                } else {
                    iodFile.add(new File(iodFilePath));
                }
            }
        };
        new JDBCReading().query(connection,creator, handler);
        
        if (log.isDebugEnabled()) {
            for (Iterator iter = iodFile.iterator(); iter.hasNext();) {
                log.debug("�����Ώۃt�@�C��:" + iter.next());
            }
        }
        
        //5.�����t�@�C�����`�F�b�N����B
        if(iodFile.isEmpty()){
            throw new NoDataFoundException("��������t�@�C����������܂���ł����B");
        }
        return iodFile;
    }
//2006/07/17�@�c�@�ǉ������܂�   
    
    //2006.09.29 iso �u�T�v�v��PDF�쐬�̂��ߓY�t�t�@�C���̂ݎ擾����K�v���ł��̂œƗ�
    /**
     * �̈�v�揑�i�T�v�j�L�[�����ΏۂƂȂ�IOD�t�@�C�����擾����B
     * @param connection            �R�l�N�V����
     * @param pkInfo                �̈�v�揑�i�T�v�j�L�[���
     * @return                      �ϊ�����IOF�t�@�C�����X�g
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public List getTenpuFilesToMerge(Connection connection, final RyoikiKeikakushoPk pkInfo)
    		throws NoDataFoundException, DataAccessException {
    
        //1.�t�@�C���擾�pSQL��
        final StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" 1 SEQ_NUM,A.SEQ_TENPU SEQ_TENPU,A.PDF_PATH IOD_FILE_PATH ");
        query.append("FROM");
        query.append(" TENPUFILEINFO").append(dbLink).append(" A ");
        query.append("WHERE A.SYSTEM_NO = ? ");
        query.append("ORDER BY SEQ_NUM,SEQ_TENPU");

        if(log.isDebugEnabled()){
            log.debug("�̈�v�揑�i�T�v�j�L�[��� '" + pkInfo);
        }
        
        //2.�����pSQL�쐬�I�u�W�F�N�g�𐶐�����B        
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                throws SQLException {
                PreparedStatement statement = conn.prepareStatement(query.toString());
                int i = 1;
                DatabaseUtil.setParameter(statement,i++,pkInfo.getRyoikiSystemNo());
                //�V�X�e���ԍ�
                return statement;
            }
        };
        //3.�\���f�[�^���ԃt�@�C���E�Y�t�t�@�C�����ԃt�@�C�����擾����B  
        final List iodFile = new ArrayList();
        
        //4.���������s����B
        RowCallbackHandler handler = new BaseCallbackHandler() {
            protected void processRow(ResultSet rs, int rowNum)
                throws SQLException, NoDataFoundException {
                String iodFilePath = rs.getString("IOD_FILE_PATH");
                if (iodFilePath == null || "".equals(iodFilePath)) {
                    throw new NoDataFoundException("�̈�v�揑�i�T�v�jIOD�t�@�C����񂪌�����܂���ł����B�����L�[�F�V�X�e����t�ԍ�'"
                    + pkInfo.getRyoikiSystemNo()
                    + "'");
                } else {
                    iodFile.add(new File(iodFilePath));
                }
            }
        };
        new JDBCReading().query(connection,creator, handler);
        
        if (log.isDebugEnabled()) {
            for (Iterator iter = iodFile.iterator(); iter.hasNext();) {
                log.debug("�����Ώۃt�@�C��:" + iter.next());
            }
        }
        
        //5.�����t�@�C�����`�F�b�N����B
        if(iodFile.isEmpty()){
            throw new NoDataFoundException("��������t�@�C����������܂���ł����B");
        }
        return iodFile;
    }
    
    //add start ly 2006/07/31
    /**
     * ���s�������̈�ԍ������ɑ��݂��邩�`�F�b�N����B
     * @param connection �R�l�N�V����
     * @param ryoikikeikakushoInfo
     * @return int
     * @throws DataAccessException
     */
    public int KariryoikiNoCount(Connection connection,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo)
            throws DataAccessException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" COUNT(*) ");
        query.append("FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO R ");
        query.append("WHERE");
        query.append(" R.KARIRYOIKI_NO = ? ");
        
        // for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }

        // DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            preparedStatement.setString(i++, ryoikikeikakushoInfo
                    .getKariryoikiNo());
            recordSet = preparedStatement.executeQuery();
            int count = 0;
            if (recordSet.next()) {
                count = recordSet.getInt(1);
            }
            return count;
        }
        catch (SQLException ex) {
            throw new DataAccessException(
                    "���s�������̈�ԍ������ɑ��݂��邩�`�F�b�N�������s���ɗ�O���������܂����B", ex);
        }
        finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
    //add end ly 2006/07/31
    
    // 2006/10/24 add by liucy start
    /**
     * �̈�v�揑�����폜����B(�����폜) ����ID�ɕR�Â��\���f�[�^��S�č폜����B �Y���f�[�^�����݂��Ȃ������ꍇ�͉����������Ȃ��B
     * 
     * @param connection �R�l�N�V����
     * @param jigyoId ���������i����ID�j
     * @throws DataAccessException �폜���ɗ�O�����������ꍇ
     */
    public void deleteRyoikiKeikakushoInfo(Connection connection, String jigyoId)
            throws DataAccessException {

        String query = "DELETE FROM RYOIKIKEIKAKUSHOINFO" + dbLink + " WHERE"
                + " JIGYO_ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            // �o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, jigyoId); // ���������i����ID�j
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑���폜���i�����폜�j�ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * ���[�J���ɑ��݂���Y�����R�[�h�̓��e��DBLink��̃e�[�u���ɑ}������B DBLink��ɓ������R�[�h������ꍇ�́A�\�ߍ폜���Ă������ƁB
     * DBLink���ݒ肳��Ă��Ȃ��ꍇ�̓G���[�ƂȂ�B
     * 
     * @param connection
     * @param jigyoId
     * @throws DataAccessException
     */
    public void copy2HokanDB(Connection connection, String jigyoId)
            throws DataAccessException {

        // DBLink�����Z�b�g����Ă��Ȃ��ꍇ
        if (dbLink == null || dbLink.length() == 0) {
            throw new DataAccessException("DB�����N�����ݒ肳��Ă��܂���BDBLink=" + dbLink);
        }

        String query = "INSERT INTO RYOIKIKEIKAKUSHOINFO" + dbLink
                + " SELECT * FROM RYOIKIKEIKAKUSHOINFO WHERE JIGYO_ID = ?";
        PreparedStatement preparedStatement = null;
        try {
            // �o�^
            preparedStatement = connection.prepareStatement(query);
            int index = 1;
            DatabaseUtil.setParameter(preparedStatement, index++, jigyoId); // ���������i����ID�j
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑�f�[�^�Ǘ��e�[�u���ۊǒ��ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
    // add end 2006/10/24
    
//2007/02/06 �c�@�ǉ���������
    /**
     * �����ԍ��̃J�E���g��Ԃ��B
     * @param connection �R�l�N�V����
     * @param ryoikikeikakushoInfo
     * @return int
     * @throws DataAccessException
     */
    public int countUketukeNo(Connection connection,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo)
            throws DataAccessException {

        StringBuffer query = new StringBuffer();
        query.append("SELECT");
        query.append(" COUNT(UKETUKE_NO)");
        query.append(" FROM");
        query.append(" RYOIKIKEIKAKUSHOINFO").append(dbLink).append(" R ");
        query.append("WHERE");
        query.append(" R.JIGYO_ID = ? ");
        query.append(" AND R.UKETUKE_NO = ? ");
        
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query.toString());
        }

        //DB�ڑ�
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            preparedStatement = connection.prepareStatement(query.toString());
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, ryoikikeikakushoInfo.getJigyoId());
            DatabaseUtil.setParameter(preparedStatement, i++, ryoikikeikakushoInfo.getUketukeNo()); 
            
            recordSet = preparedStatement.executeQuery();
            int count = 0;
            if (recordSet.next()){
                count =  recordSet.getInt(1);
            }
            
            return count;
             
        } catch (SQLException ex) {
            throw new DataAccessException("�̈�v�揑�i�T�v�j���Ǘ��e�[�u���������s���ɗ�O���������܂����B", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }
//2007/02/06�@�c�@�ǉ������܂�    
}