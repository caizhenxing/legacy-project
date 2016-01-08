/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���iJSPS)
 *    Source name : PunchKanriInfoDao.java
 *    Description : �p���`�f�[�^�Ǘ��ׁ̈ADB�A�N�Z�X�̃N���X
 *
 *    Author      : Admin
 *    Date        : 2004/11/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/11/02    V1.0                       �V�K�쐬
 *    2005/05/31    V1.1        ���@�@�@�@�@�@�@����̈�p���`�f�[�^�o�͒ǉ�
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.PunchDataKanriInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.CSVLine;
import jp.go.jsps.kaken.util.HanZenConverter;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �p���`�Ǘ��f�[�^�A�N�Z�X�N���X�B ID RCSfile="$RCSfile: PunchKanriInfoDao.java,v $"
 * Revision="$Revision: 1.6 $" Date="$Date: 2007/07/25 10:18:04 $"
 */
public class PunchKanriInfoDao {

    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------

    /** ���O */
    protected static final Log log = LogFactory.getLog(PunchKanriInfoDao.class);

    private static final String LINE_SHIFT = "\r\n";

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
     * 
     * @param userInfo ���s���郆�[�U���
     */
    public PunchKanriInfoDao(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    //---------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------
    /**
     * �p���`�f�[�^���̈ꗗ���擾����B
     * 
     * @param connection �R�l�N�V����
     * @param userInfo
     * @return �p���`�f�[�^���
     * @throws ApplicationException
     */
    public List selectList(Connection connection, UserInfo userInfo)
            throws ApplicationException {

        //��������
        String addQuery = null;

        //�Ɩ��S���҂̏ꍇ�͎������S�����鎖�Ƌ敪�̂݁B
        if (userInfo.getRole().equals(UserRole.GYOMUTANTO)) {
            Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoKubun().iterator();
            String tantoJigyoKubun = StringUtil.changeIterator2CSV(ite, true);
            addQuery = new StringBuffer(" WHERE A.JIGYO_KUBUN IN (")
            							.append(tantoJigyoKubun)
            							.append(")").toString();
        } else {
            addQuery = "";
        }

        //-----------------------
        // SQL���̍쐬
        //-----------------------
        String query = "SELECT "
                + " A.PUNCH_SHUBETU " //�p���`�f�[�^���
                + ",A.PUNCH_NAME " //�p���`�f�[�^����
                + ",A.JIGYO_KUBUN " //���Ƌ敪
                + ",TO_CHAR(A.SAKUSEI_DATE,'YYYY/MM/DD HH24:MI:SS') SAKUSEI_DATE" //�쐬����
                + ",A.PUNCH_PATH " //�p���`�f�[�^�t�@�C���p�X
                //�\�[�g���̕s����C������ 2006/10/3
                + ",TO_CHAR(A.PUNCH_SHUBETU,'00') SORTNO"
                //+ "FROM PUNCHKANRI A" + addQuery + " ORDER BY A.PUNCH_SHUBETU";
                + " FROM PUNCHKANRI A" + addQuery + " ORDER BY SORTNO";

        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            return SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("�p���`�f�[�^�Ǘ��e�[�u����������DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�p���`�f�[�^�Ǘ��e�[�u����1�����f�[�^������܂���B", e);
        }
    }

    /**
     * �p���`�Ǘ������擾����B
     * 
     * @param connection �R�l�N�V����
     * @param punchShubetu �p���`��ʁi��L�[���j
     * @return �p���`�f�[�^�Ǘ����
     * @throws NoDataFoundException �f�[�^��������Ȃ������ꍇ
     * @throws DataAccessException �f�[�^�x�[�X�A�N�Z�X���ɃG���[�����������ꍇ
     */
    public PunchDataKanriInfo selectPunchKanriInfo(Connection connection,
            String punchShubetu) throws NoDataFoundException,
            DataAccessException {

        //-----------------------
        // SQL���̍쐬
        //-----------------------
        String query = "SELECT " + " A.PUNCH_SHUBETU " //�p���`�f�[�^���
                + ",A.PUNCH_NAME " //�p���`�f�[�^����
                + ",A.JIGYO_KUBUN " //���Ƌ敪
                + ",A.SAKUSEI_DATE " //�쐬����
                + ",A.PUNCH_PATH " //�p���`�f�[�^�t�@�C���p�X
                + "FROM PUNCHKANRI A" + " WHERE PUNCH_SHUBETU = ?";

        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        //-----------------------
        // �p���`�f�[�^���擾
        //-----------------------
        PreparedStatement preparedStatement = null;
        ResultSet recordSet = null;
        try {
            PunchDataKanriInfo result = new PunchDataKanriInfo();
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, punchShubetu);
            recordSet = preparedStatement.executeQuery();
            if (recordSet.next()) {
                result.setPunchShubetu(recordSet.getString("PUNCH_SHUBETU"));
                result.setPunchName(recordSet.getString("PUNCH_NAME"));
                result.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));
                result.setSakuseiDate(recordSet.getDate("SAKUSEI_DATE"));
                result.setPunchPath(recordSet.getString("PUNCH_PATH"));
                return result;
            } else {
                throw new NoDataFoundException("�p���`�f�[�^�Ǘ��e�[�u���ɊY������f�[�^��������܂���"
                        + punchShubetu + "'");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("�p���`�f�[�^�Ǘ��e�[�u���������s���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(recordSet, preparedStatement);
        }
    }

    /**
     * �p���`�Ǘ��e�[�u���̏����X�V����B SAKUSEI_DATE�ɂ��ẮA�f�[�^�x�[�X�̃V�X�e�������ōX�V����B
     * 
     * @param connection �R�l�N�V����
     * @param dataInfo �X�V����p���`�f�[�^�Ǘ����
     * @throws DataAccessException �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public void update(Connection connection, PunchDataKanriInfo dataInfo)
            throws DataAccessException, NoDataFoundException {

        String query = "UPDATE PUNCHKANRI"
        	           + " SET PUNCH_SHUBETU = ?"
        	           	   + ",PUNCH_NAME = ?" 
        			       + ",JIGYO_KUBUN = ?"
        			       + ",SAKUSEI_DATE = SYSDATE" 
        			       + ",PUNCH_PATH = ?" 
        		     + " WHERE PUNCH_SHUBETU = ? ";

        PreparedStatement preparedStatement = null;
        try {
            //�p���`�Ǘ��e�[�u���X�V
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getPunchShubetu());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getPunchName());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getJigyoKubun());
            //DatabaseUtil.setParameter(preparedStatement,i++,dataInfo.getSakuseiDate());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getPunchPath());
            DatabaseUtil.setParameter(preparedStatement, i++, dataInfo.getPunchShubetu());
            DatabaseUtil.executeUpdate(preparedStatement);

        } catch (SQLException ex) {
            throw new DataAccessException("�p���`�f�[�^�Ǘ��e�[�u���X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }

    /**
     * ���ʐ��i����������p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataTokushi(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

        String query = "SELECT "
        			//+ ",LPAD('01',2,'0') KENKYUSHUMOKU" 					//������ڔԍ�(2�o�C�g)
            		+ " NVL(A.SHINSEI_KUBUN,' ') SHINSEIKUBUN " 			//�V�K�p���敪(1�o�C�g)
	                + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 					//�����@�փR�[�h(5�o�C�g)
	                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" 	//�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���                 
	                /*
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KEI_NAME_NO,' ') END KEINAMENO" 					//�n���̋敪�ԍ�(1�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //���S���̗L��
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" 	//1�N�ڌ����o��(7�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" 	//2�N�ڌ����o��(7�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" 	//3�N�ڌ����o��(7�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" 	//4�N�ڌ����o��(7�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" 	//5�N�ڌ����o��(7�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAINOKEIZOKU" //�p�����̌����ۑ�ԍ��i8�o�C�g�j
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" 	//�����Ґ��i2�o�C�g�j
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(TO_CHAR(A.SHINSEI_FLG_NO),' ') END SHINSEIFLGNO" //�����v��ŏI�N�x�O�N�x�̉���i1�o�C�g�j
	                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE LPAD(NVL(A.KADAI_NO_SAISYU,' '),8) END KADAINOSAISYU" //�ŏI�N�x�ۑ�ԍ��i8�o�C�g�j
	                */
	                
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.KEI_NAME_NO,' ') END KEINAMENO" 					//�n���̋敪�ԍ�(1�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //���S���̗L��
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" 	//1�N�ڌ����o��(7�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" 	//2�N�ڌ����o��(7�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" 	//3�N�ڌ����o��(7�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" 	//4�N�ڌ����o��(7�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" 	//5�N�ڌ����o��(7�o�C�g)
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAINOKEIZOKU" //�p�����̌����ۑ�ԍ��i8�o�C�g�j
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" 	//�����Ґ��i2�o�C�g�j
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(TO_CHAR(A.SHINSEI_FLG_NO),' ') END SHINSEIFLGNO" //�����v��ŏI�N�x�O�N�x�̉���i1�o�C�g�j
	                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE LPAD(NVL(A.KADAI_NO_SAISYU,' '),8) END KADAINOSAISYU" //�ŏI�N�x�ۑ�ԍ��i8�o�C�g�j
	                	                
//UPDATE�@END�@ 2007/07/25 BIS �����_	                
	                + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//��\�ҕ��S�ҕ�(1�o�C�g)
					//2005.10.17 kainuma�ǉ�
					+ ",RPAD(B.NAME_KANA_SEI,32,'�@') NAMEKANASEI"	//�����i�t���K�i�[���j(32�o�C�g)

	                //2005.12.22 iso NULL���������̃o�O�C��
//					+ ",RPAD(B.NAME_KANA_MEI,32,'�@') NAMEKANAMEI"	//�����i�t���K�i�[���j(32�o�C�g)
					+ ",RPAD(NVL(B.NAME_KANA_MEI, '�@'),32,'�@') NAMEKANAMEI"	//�����i�t���K�i�[���j(32�o�C�g)
					+ ",RPAD(B.NAME_KANJI_SEI,32,'�@') NAMEKANJISEI"	//�����i�������[���j(32�o�C�g)
	                //2005.12.22 iso NULL���������̃o�O�C��
//					+ ",RPAD(B.NAME_KANJI_MEI,32,'�@') NAMEKANJIMEI"	//�����i�������[���j(32�o�C�g)
					+ ",RPAD(NVL(B.NAME_KANJI_MEI, '�@'),32,'�@') NAMEKANJIMEI"	//�����i�������[���j(32�o�C�g)
					
					//�ǉ��ȏ�
	                + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����(5�o�C�g)
	                + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//���ǖ�(�R�[�h)(3�o�C�g)
	                + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD"	//�E���R�[�h(2�o�C�g)
	                + ",LPAD(B.KENKYU_NO, 8,' ') KENKYUNO" 		//�����Ҕԍ�(8�o�C�g)
	                + ",LPAD(B.KEIHI, 7,'0') KEIHI" 			//�����o��(7�o�C�g)
	                + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//�G�t�H�[�g(3�o�C�g)
	                + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
	                + " WHERE A.DEL_FLG = 0"
	                + " AND A.SYSTEM_NO = B.SYSTEM_NO"
	                + " AND A.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_TOKUSUI
	                + " AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
	                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.UKETUKE_NO, SEIRINUMBER, BUNTAN_FLG, B.SEQ_NO"; //���Ƃh�c���A�@�ց[�����ԍ����A��\�ҕ��S�҃t���O���A�V�[�P���X�ԍ���
        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = new ArrayList();
//        List finalList = new ArrayList();

        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            list = SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4000"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�\���f�[�^�e�[�u����1�����f�[�^������܂���B", e);
        }

        //�ŏI�I��return����String��錾����
//        String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {

            //DB����擾����List�^list��String�^�ɂ��āA����String��List�����
            Map recordMap = (Map) list.get(i);
            String kenkyuShumoku = "01";		//������ڔԍ�(2�o�C�g)
            String shinseiKubun = (String) recordMap.get("SHINSEIKUBUN");
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");
            String keiNameNo = (String) recordMap.get("KEINAMENO");
            String buntankinFlg = (String) recordMap.get("BUNTANKINFLG");
            String keihi1 = (String) recordMap.get("KEIHI1");
            String keihi2 = (String) recordMap.get("KEIHI2");
            String keihi3 = (String) recordMap.get("KEIHI3");
            String keihi4 = (String) recordMap.get("KEIHI4");
            String keihi5 = (String) recordMap.get("KEIHI5");
            String kadaiNoKeizoku = (String) recordMap.get("KADAINOKEIZOKU");
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");
            String kenkyuNinzu = (String) recordMap.get("KENKYUNINZU");
            String shinseiFlgNo = (String) recordMap.get("SHINSEIFLGNO");
            String kadaiNoSaisyu = (String) recordMap.get("KADAINOSAISYU");
            String buntanFlg = (String) recordMap.get("BUNTANFLG");
			//2005.10.17�@kainuma�ǉ�
			String nameKanaSei = (String)recordMap.get("NAMEKANASEI");
			String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");
			String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");
			String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");
			//�ǉ��ȏ�
            String shozokuCd = (String) recordMap.get("SHOZOKUCD");
            String bukyokuNo = (String) recordMap.get("BUKYOKUCD");
            String shokusyuCd = (String) recordMap.get("SHOKUSHUCD");
            String kenkyuNo = (String) recordMap.get("KENKYUNO");
            String keihi = (String) recordMap.get("KEIHI");
            String effort = (String) recordMap.get("EFFORT");

            //DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
            recordMap = null;
            list.set(i, null);
            
            finalResult.append(kenkyuShumoku)
					   .append(shinseiKubun)
					   .append(syozokuCdDaihyo)
	                   .append(seiriNumber)
	                   .append(keiNameNo)
	                   .append(buntankinFlg)
	                   .append(keihi1)
	                   .append(keihi2)
	                   .append(keihi3)
	                   .append(keihi4)
	                   .append(keihi5)
	                   .append(kadaiNoKeizoku)
	                   .append(kadaiNameKanji)
	                   .append(kenkyuNinzu)
	                   .append(shinseiFlgNo)
	                   .append(kadaiNoSaisyu)
					   .append(buntanFlg)
						//2005.10.17 kainuma�ǉ�
						.append(nameKanaSei)
						.append(nameKanaMei)
						.append(nameKanjiSei)
						.append(nameKanjiMei)
						//�ǉ��ȏ�
	                   .append(shozokuCd)
					   .append(bukyokuNo)
	                   .append(shokusyuCd)
					   .append(kenkyuNo)
	                   .append(keihi)
	                   .append(effort)
	                   .append(LINE_SHIFT);

//            finalList.add(record);
//            finalResult = finalResult + (String) finalList.get(i);
        }
        return finalResult.toString();
    }
    
    /**
     * �w�p�n��������p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataGakuso(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

        String query = "SELECT "
                //+ " '52' SHUMOKUNO" 									//������ڔԍ��i2�o�C�g�j
                + " A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 					//�����@�փR�[�h(5�o�C�g)
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER"	//�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���                 
                /*
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" 	//1�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" 	//2�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" 	//3�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" 	//4�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" 	//5�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE A.KEI_NAME_NO END KEINAMENO" 			//�n���̋敪�ԍ��i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '    ' ELSE A.BUNKASAIMOKU_CD END BUNKASAIMOKUCD" //�זڃR�[�h�i4�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" //�����Ґ��i2�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //���S���̗L��
                */
                
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" 	//1�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" 	//2�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" 	//3�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" 	//4�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" 	//5�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE A.KEI_NAME_NO END KEINAMENO" 			//�n���̋敪�ԍ��i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '    ' ELSE A.BUNKASAIMOKU_CD END BUNKASAIMOKUCD" //�זڃR�[�h�i4�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" //�����Ґ��i2�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //���S���̗L��
                
//UPDATE�@END�@ 2007/07/25 BIS �����_                 
                + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//��\�ҕ��S�ҕ�(1�o�C�g)
                //2005.10.17 kainuma�ǉ�
                + ",RPAD(B.NAME_KANA_SEI,32,'�@') NAMEKANASEI"	//�����i�t���K�i�[���j(32�o�C�g)
                
                //2005.12.22 iso NULL���������̃o�O�C��
//				+ ",RPAD(B.NAME_KANA_MEI,32,'�@') NAMEKANAMEI"	//�����i�t���K�i�[���j(32�o�C�g)
				+ ",RPAD(NVL(B.NAME_KANA_MEI, '�@'),32,'�@') NAMEKANAMEI"	//�����i�t���K�i�[���j(32�o�C�g)
				+ ",RPAD(B.NAME_KANJI_SEI,32,'�@') NAMEKANJISEI"	//�����i�������[���j(32�o�C�g)
                //2005.12.22 iso NULL���������̃o�O�C��
//				+ ",RPAD(B.NAME_KANJI_MEI,32,'�@') NAMEKANJIMEI"	//�����i�������[���j(32�o�C�g)
				+ ",RPAD(NVL(B.NAME_KANJI_MEI, '�@'),32,'�@') NAMEKANJIMEI"	//�����i�������[���j(32�o�C�g)
				
				//�ǉ��ȏ�
                + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����(5�o�C�g)
                + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//���ǖ�(�R�[�h)(3�o�C�g)
                + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD"	//�E���R�[�h(2�o�C�g)
                + ",LPAD(B.KENKYU_NO, 8,' ') KENKYUNO" 		//�����Ҕԍ�(8�o�C�g)
                + ",LPAD(B.KEIHI, 7,'0') KEIHI" 			//�����o��(7�o�C�g)
                + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//�G�t�H�[�g(3�o�C�g)
                + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
                + " WHERE A.DEL_FLG = 0"
                + " AND A.SYSTEM_NO = B.SYSTEM_NO"
                + " AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO + "," + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO + ")"
                + " AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, SEIRINUMBER, BUNTAN_FLG, B.SEQ_NO"; //����ID,�@�ց[�����ԍ����A��\�ҕ��S�҃t���O���A�V�[�P���X�ԍ���
        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = new ArrayList();
//        List finalList = new ArrayList();

        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            list = SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�\���f�[�^�e�[�u����1�����f�[�^������܂���B", e);
        }

        //�ŏI�I��return����String��錾����
//        String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {

            //DB����擾����List�^list��String�^�ɂ��āA����String��List�����
            Map recordMap = (Map) list.get(i);
            String jigyoId = "52"; 			//������ڔԍ�
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");
            String keihi1 = (String) recordMap.get("KEIHI1");
            String keihi2 = (String) recordMap.get("KEIHI2");
            String keihi3 = (String) recordMap.get("KEIHI3");
            String keihi4 = (String) recordMap.get("KEIHI4");
            String keihi5 = (String) recordMap.get("KEIHI5");
            String keinameno = (String) recordMap.get("KEINAMENO");
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKUCD");
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");
            String kenkyuNinzu = (String) recordMap.get("KENKYUNINZU");
            String buntanKinFlg = (String) recordMap.get("BUNTANKINFLG");
            String buntanFlg = (String) recordMap.get("BUNTANFLG");
            //2005.10.17�@kainuma�ǉ�
            String nameKanaSei = (String)recordMap.get("NAMEKANASEI");
			String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");
			String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");
			String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");
			//�ǉ��ȏ�
            String syozokuCd = (String) recordMap.get("SHOZOKUCD");
            String bukyokuCd = (String) recordMap.get("BUKYOKUCD");
            String syokusyuCd = (String) recordMap.get("SHOKUSHUCD");
            String kenkyuNo = (String) recordMap.get("KENKYUNO");
            String keihi = (String) recordMap.get("KEIHI");
            String effort = (String) recordMap.get("EFFORT");

            //DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
            recordMap = null;
            list.set(i, null);
            
            finalResult.append(jigyoId)
					   .append(syozokuCdDaihyo)
					   .append(seiriNumber)
					   .append(keihi1)
					   .append(keihi2)
					   .append(keihi3)
					   .append(keihi4)
					   .append(keihi5)
					   .append(keinameno)
					   .append(bunkaSaimokuCd)
					   .append(kadaiNameKanji)
					   .append(kenkyuNinzu)
					   .append(buntanKinFlg)
					   .append(buntanFlg)
					   //2005.10.17 kainuma�ǉ�
					   .append(nameKanaSei)
					   .append(nameKanaMei)
					   .append(nameKanjiSei)
					   .append(nameKanjiMei)
					   //�ǉ��ȏ�
					   .append(syozokuCd)
					   .append(bukyokuCd)
					   .append(syokusyuCd)
					   .append(kenkyuNo)
					   .append(keihi)
					   .append(effort)
					   .append(LINE_SHIFT);

//            finalList.add(record);
//            finalResult = finalResult + (String) finalList.get(i);

        }
        return finalResult.toString();
    }

    /**
     * �]��\�p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataHyotei(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

        String query = "SELECT "
				+ "SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU"					//������ڔԍ��i2�o�C�g)
				//2007/5/10 ���S��ǉ������׏C��
				//+ ",NVL(A.SHINSA_KUBUN,'1') SHINSA_KUBUN" 				//�R���敪(1�o�C�g)  
				+ ",SUBSTR(A.JIGYO_ID,7,1) SHINSA_KUBUN" 					//�R���敪(1�o�C�g)
				//2007/5/10 end
                + ",NVL(SUBSTR(B.UKETUKE_NO,1,5),'     ') SHOZOKUCDDAIHYO"	//�����@�փR�[�h(5�o�C�g)
				+ ",NVL(A.BUNKASAIMOKU_CD,'    ') BUNKASAIMOKUCD" 			//�זڃR�[�h�i4�o�C�g�j
				+ ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
				+ "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"	//�����ԍ��i1�o�C�g�j
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���         				
				/*
				+ ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
				+ "      WHEN A.BUNKATSU_NO = '2' THEN '2' ELSE ' ' END BUNKATSUNO_12"	//�����ԍ��i1�o�C�g�j
				*/
				
				+ ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
				+ " 	 WHEN A.BUNKATSU_NO = '2' THEN '2' "
				+ " 	 WHEN A.BUNKATSU_NO = '3' THEN '3' "
				+ " 	 WHEN A.BUNKATSU_NO = '4' THEN '4' "
				+ "      WHEN A.BUNKATSU_NO = '5' THEN '5' ELSE ' ' END BUNKATSUNO_12"	//�����ԍ��i1�o�C�g�j
				
////UPDATE�@END�@ 2007/07/25 BIS �����_
				+ ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" 					//�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
                + ",NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO" 					//�R�����}��(�R�����ԍ��̉��Q���j�i2�o�C�g�j
				+ ",CASE WHEN B.RIGAI = '1' THEN '1' ELSE ' '  END RIGAIKANKEI" 		//���Q�֌W
				+ ",NVL(B.JUYOSEI, ' ') JUYOSEI" 										//�����ۑ�̊w�p�I�d�v��
				+ ",NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU"							//�����v��E���@�̑Ó���
				+ ",NVL(B.DOKUSOSEI, ' ') DOKUSOSEI"									//�Ƒn���y�ъv�V��
				+ ",NVL(B.HAKYUKOKA, ' ') HAKYUKOKA" 									//�g�y����
				+ ",NVL(B.SUIKONORYOKU, ' ') SUIKONORYOKU" 								//���s�\��
				+ ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '111') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_HOGA"	//�K�ؐ��E�G��
				+ ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '043')OR(SUBSTR(A.JIGYO_ID,5,3) = '053') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_KAIGAI"	//�K�ؐ��E�G��
				//2007/5/10 �ǉ�
				+ ",NVL(B.TEKISETSU_WAKATES, ' ') WAKATES"								//���S�Ƃ��Ă̑Ó���
				//2007/5/10 �ǉ�����
				+ ",CASE WHEN B.KEKKA_TEN = '-' THEN ' ' ELSE NVL(B.KEKKA_TEN, ' ') END KEKKATEN"	//�����]��
                + ",CASE WHEN B.JINKEN = '3' THEN '3' ELSE ' ' END JINKEN" 							//�l��
                + ",CASE WHEN B.BUNTANKIN = '3' THEN '3' ELSE ' ' END BUNTANKIN" 					//���S��
                + ",CASE WHEN B.DATO = '0' THEN ' ' ELSE NVL(B.DATO, ' ') END DATO" 				//�o��̑Ó���
                + ",CASE WHEN B.COMMENTS IS NULL THEN '�@' ELSE TO_MULTI_BYTE(B.COMMENTS) END COMMENTS" //�R���ӌ�
				+ ",CASE WHEN B.OTHER_COMMENT IS NULL THEN '�@' ELSE TO_MULTI_BYTE(B.OTHER_COMMENT) END OTHER_COMMENT" //�R�����g
				+ ",NVL(B.SHINSA_JOKYO, '0') SHINSAJOKYO" 											//�R����
                + " FROM SHINSEIDATAKANRI A, SHINSAKEKKA B"
                + " WHERE B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN
                + "   AND NOT B.SHINSAIN_NO LIKE '@%'"	//�_�~�[�R���������O����(�_�~�[�R�����ԍ������t���Ă�)
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO" 
                + "   AND A.JIGYO_KUBUN = B.JIGYO_KUBUN "
				+ "	  AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_KIBAN +  ")"
	//			
                + "   AND A.DEL_FLG = 0"
                + "   AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY KENKYUSHUMOKU, SHINSA_KUBUN, SHOZOKUCDDAIHYO, BUNKASAIMOKUCD, A.BUNKATSU_NO, SHINSAINNO, SEIRINUMBER"; //�����@�փR�[�h�A,�����ԍ��A�R�����}��

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            list = SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�f�[�^�x�[�X��1�����f�[�^������܂���B", e);
        }

        //�ŏI�I��return����String��錾����
        StringBuffer finalResult = new StringBuffer();
        char charToBeAdded = '�@'; //�S�p�X�y�[�X
        final int commentLength = 1000;	//150; //�R�����g�̕\��������
			   int otherCommentLength = 50; //�R�����g�̕\��������
        final char HOSHI = '��';

        //���p�S�p�R���o�[�^
        HanZenConverter hanZenConverter = new HanZenConverter();

        for (int i = 0; i < list.size(); i++) {

            //long start = System.currentTimeMillis();

            //DB����擾����List�^list��String�^�ɂ��āA����String��List�����
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //������ڔԍ�
			String shinsaKbn = (String) recordMap.get("SHINSA_KUBUN");
            String shozokuCd = (String) recordMap.get("SHOZOKUCDDAIHYO");
			String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKUCD");
			String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");
			String bunkatsNo12 = (String) recordMap.get("BUNKATSUNO_12");
			String seiriNumber = (String) recordMap.get("SEIRINUMBER");
			String shinsainNo = (String) recordMap.get("SHINSAINNO");
			String rigai = (String) recordMap.get("RIGAIKANKEI");
			String juyosei = (String) recordMap.get("JUYOSEI");
			String kenkyuKeikaku = (String) recordMap.get("KENKYUKEIKAKU");
			String dokusosei = (String) recordMap.get("DOKUSOSEI");
			String hakyukoka = (String) recordMap.get("HAKYUKOKA");
			String suikonoryoku = (String) recordMap.get("SUIKONORYOKU");
			String tekisetsuHoga = (String) recordMap.get("TEKISETU_HOGA");
			String tekisetsuKaigai = (String) recordMap.get("TEKISETU_KAIGAI");
			String wakates = (String) recordMap.get("WAKATES");	//2007/5/10 �ǉ�
            String kekkaTen = (String) recordMap.get("KEKKATEN");            
            String jinken = (String) recordMap.get("JINKEN");
            String buntankin = (String) recordMap.get("BUNTANKIN");
            String dato = (String) recordMap.get("DATO");
            String recordComments = (String) recordMap.get("COMMENTS");
			String recordOtherComment = (String) recordMap.get("OTHER_COMMENT");
            String shinsaJokyo = (String) recordMap.get("SHINSAJOKYO");

            //DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
            recordMap = null;
            list.set(i, null);

            //---�ӌ��𔼊p����S�p�ɂ���
            //char�̔z��ɕϊ�
            String ZenkakuComments = hanZenConverter.convert(recordComments);
            char[] cArray = ZenkakuComments.toCharArray();
            for (int j = 0; j < cArray.length; j++) {
                if (StringUtil.isHankaku(cArray[j])) {
                    cArray[j] = HOSHI; //���p�̏ꍇ�͒u������
                }

            }
            //������ɖ߂�
            String checkedZenkakuComments = new String(cArray);
            if (checkedZenkakuComments.length() > commentLength){
            	checkedZenkakuComments = checkedZenkakuComments.substring(0, commentLength);
            }

            //�ӌ���150�����̕����̌Œ蒷�ɂ���
            while (checkedZenkakuComments.length() < commentLength) {
                checkedZenkakuComments += charToBeAdded;
            }
			//�ӌ������܂�


			//---�R�����g�𔼊p����S�p�ɂ���
			//char�̔z��ɕϊ�
			String ZenOtherComment = hanZenConverter.convert(recordOtherComment);
			char[] ocArray = ZenOtherComment.toCharArray();
			for (int j = 0; j < ocArray.length; j++) {
				if (StringUtil.isHankaku(ocArray[j])) {
					ocArray[j] = HOSHI; //���p�̏ꍇ�͒u������
				}

			}
			//������ɖ߂�
			String checkedZenOtherComment = new String(ocArray);
			if (checkedZenOtherComment.length() > otherCommentLength){
				checkedZenOtherComment = checkedZenOtherComment.substring(0, otherCommentLength);
			}

			//�R�����g��50�����̕����̌Œ蒷�ɂ���
			while (checkedZenOtherComment.length() < otherCommentLength) {
				checkedZenOtherComment += charToBeAdded;
			}

            //�ŏI���ʂɃ��R�[�h�i�{�s�j�𕶎��񌋍�
            finalResult.append(jigyoId)
					   .append(shinsaKbn)
            		   .append(shozokuCd)
					   .append(bunkaSaimokuCd)
					   .append(bunkatsNoAB)
					   .append(bunkatsNo12)
					   .append(seiriNumber)
            		   .append(shinsainNo)
					   .append(rigai)
				  	   .append(juyosei)
					   .append(kenkyuKeikaku)
					   .append(dokusosei)
					   .append(hakyukoka)
					   .append(suikonoryoku)
					   .append(tekisetsuHoga)
					   .append(tekisetsuKaigai)
					   .append(wakates)
            		   .append(kekkaTen)
            		   .append(jinken)
            		   .append(buntankin)
            		   .append(dato)
            		   .append(checkedZenkakuComments)
					   .append(checkedZenOtherComment)
            		   .append(shinsaJokyo)
            		   .append(LINE_SHIFT);

            //long stop = System.currentTimeMillis() - start;
            //System.out.println(i+"�s�̏������ԁB" + stop);
            }
        return finalResult.toString();
    }
    
    /**
     * ��Ռ�����������p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataKibanKenkyu(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

    	log.info("�p���`�f�[�^�����J�n�B");
    	
        String query = "SELECT "
        		
                + " SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU"	//������ڔԍ��i2�o�C�g�j
                //2007/02/08 �c�@�C����������
//                + ",NVL(A.SHINSA_KUBUN,'1') SHINSA_KUBUN" 	//�R���敪(1�o�C�g)
                + ",CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00120' THEN '0' ELSE NVL(A.SHINSA_KUBUN,'1') END SHINSA_KUBUN"  //�R���敪(1�o�C�g)
                //2007/02/08 �c�@�C�������܂�
                + ",NVL(A.SHINSEI_KUBUN,'1') SHINSEI_KUBUN" //�V�K�p���敪(1�o�C�g)
                + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 		//�����@�փR�[�h(5�o�C�g)
                + ",A.BUNKASAIMOKU_CD" 						//�זڃR�[�h�i4�o�C�g�j
                + ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
                + "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"	//�����ԍ��i1�o�C�g�j
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���             
                //+ ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                //+ "      WHEN A.BUNKATSU_NO = '2' THEN '2' ELSE ' ' END BUNKATSUNO_12"	//�����ԍ��i1�o�C�g�j
                + ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                + " 	 WHEN A.BUNKATSU_NO = '2' THEN '2' "
                + "		 WHEN A.BUNKATSU_NO = '3' THEN '3' "
                + "		 WHEN A.BUNKATSU_NO = '4' THEN '4' "
                + "      WHEN A.BUNKATSU_NO = '5' THEN '5' ELSE ' ' END BUNKATSUNO_12"	//�����ԍ��i1�o�C�g�j                
//UPDATE�@END�@ 2007/07/25 BIS �����_                 
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" 					//�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���                
             /*  
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KENKYU_TAISHO,' ') END KENKYU_TAISHO" //�����Ώۂ̗ތ^�i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE NVL(A.KAIGAIBUNYA_CD,'  ') END KAIGAIBUNYA_CD" //�C�O����i2�o�C�g�j
                //2005.10.17kainuma �ǉ�
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//�Ő�
                //�ǉ��ȏ�
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAI_NO_KEIZOKU" //�p�����̌����ۑ�ԍ��i8�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //���S���̗L��
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' " 
                + "      WHEN A.KENKYU_NINZU IS NULL THEN '01' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END KENKYUNINZU" //�����Ґ��i2�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBO_FLG" //�J����]�̗L���i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(TO_CHAR(A.SHINSEI_FLG_NO),' ') END SHINSEI_FLG_NO" //�����v��ŏI�N�x�O�N�x�̉���i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_SAISYU,'        ') END KADAI_NO_SAISYU" //�ŏI�N�x�ۑ�ԍ��i8�o�C�g�j
             */   
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.KENKYU_TAISHO,' ') END KENKYU_TAISHO" //�����Ώۂ̗ތ^�i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE NVL(A.KAIGAIBUNYA_CD,'  ') END KAIGAIBUNYA_CD" //�C�O����i2�o�C�g�j
                //2005.10.17kainuma �ǉ�
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//�Ő�
                //�ǉ��ȏ�
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAI_NO_KEIZOKU" //�p�����̌����ۑ�ԍ��i8�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //���S���̗L��
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' " 
                + "      WHEN A.KENKYU_NINZU IS NULL THEN '01' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END KENKYUNINZU" //�����Ґ��i2�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBO_FLG" //�J����]�̗L���i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(TO_CHAR(A.SHINSEI_FLG_NO),' ') END SHINSEI_FLG_NO" //�����v��ŏI�N�x�O�N�x�̉���i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_SAISYU,'        ') END KADAI_NO_SAISYU" //�ŏI�N�x�ۑ�ԍ��i8�o�C�g�j
               
//UPDATE�@END�@ 2007/07/25 BIS �����_                
                + ",NVL(A.KEYWORD_CD,' ') KEYWORD_CD" 		//�זڕ\�L�[���[�h�i1�o�C�g�j                 
               // + ",DECODE(A.OTHER_KEYWORD,NULL,RPAD('�@',50),RPAD(SUBSTRB(A.OTHER_KEYWORD,1,50),50)) OTHER_KEYWORD" //�זڕ\�ȊO�̃L�[���[�h�i50�o�C�g�j
				+ ",CASE WHEN A.OTHER_KEYWORD IS NULL THEN '�@' ELSE TO_MULTI_BYTE(A.OTHER_KEYWORD) END OTHER_KEYWORD" //�זڕ\�ȊO�̃L�[���[�h�i50�o�C�g�j
                
                + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//��\�ҕ��S�ҕ�(1�o�C�g)
                //2005.10.17kainuma�@�ǉ�
				+ ",RPAD(B.NAME_KANA_SEI,32,'�@') NAMEKANASEI"	//�����i�t���K�i�[���j(32�o�C�g)

                //2005.12.22 iso NULL���������̃o�O�C��
//				+ ",RPAD(B.NAME_KANA_MEI,32,'�@') NAMEKANAMEI"	//�����i�t���K�i�[���j(32�o�C�g)
				+ ",RPAD(NVL(B.NAME_KANA_MEI, '�@'),32,'�@') NAMEKANAMEI"	//�����i�t���K�i�[���j(32�o�C�g)
				+ ",RPAD(B.NAME_KANJI_SEI,32,'�@') NAMEKANJISEI"	//�����i�������[���j(32�o�C�g)
                //2005.12.22 iso NULL���������̃o�O�C��
//				+ ",RPAD(B.NAME_KANJI_MEI,32,'�@') NAMEKANJIMEI"	//�����i�������[���j(32�o�C�g)
				+ ",RPAD(NVL(B.NAME_KANJI_MEI, '�@'),32,'�@') NAMEKANJIMEI"	//�����i�������[���j(32�o�C�g)
				
				//�ǉ��ȏ�
                + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����(5�o�C�g)
                + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//���ǖ�(�R�[�h)(3�o�C�g)
                + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD" 	//�E���R�[�h(2�o�C�g)
                + ",LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO" 	//�����Ҕԍ�(8�o�C�g)
                + ",LPAD(NVL(B.KEIHI,0), 7,'0') KEIHI" 	    //�����o��(7�o�C�g)
                + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//�G�t�H�[�g(3�o�C�g)

                + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
                + " WHERE DEL_FLG = 0" 
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO"
                + "   AND JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN
                + "   AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.BUNKASAIMOKU_CD, A.BUNKATSU_NO, SEIRINUMBER, BUNTANFLG, B.SEQ_NO"; //����ID,�@�ց[�זڔԍ��[�����ԍ��[�����ԍ��A��\�ҕ��S�҃t���O���A�V�[�P���X�ԍ���

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = null; //new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            list = SelectUtil.select(connection, query);
        } catch (DataAccessException e) {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�\���f�[�^�e�[�u����1�����f�[�^������܂���B", e);
        }

        //�ŏI�I��return����String��錾����
        //String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();
		final char charToBeAdded = '�@'; //�S�p�X�y�[�X
		final int otherKeywordLength = 25; //�זڕ\�ӊO�̃L�[���[�h�̕\��������
		final char HOSHI = '��';
		
		//���p�S�p�R���o�[�^
		HanZenConverter hanZenConverter = new HanZenConverter();

        for (int i = 0; i < list.size(); i++) {
        	
			if(i % 1000 == 0) {
				log.info("�p���`�f�[�^������::" + i + "��");
			}
        	
        	
            //DB����擾����List�^list��String�^�ɂ��āA����String��List�����
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //������ڔԍ�
        	String shinsaKbn = (String) recordMap.get("SHINSA_KUBUN");
        	String shinseiKbn = (String) recordMap.get("SHINSEI_KUBUN");
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKU_CD");
            String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");
            String bunkatsNo12 = (String) recordMap.get("BUNKATSUNO_12");
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");
            String kenkyuTaisho = (String) recordMap.get("KENKYU_TAISHO");
            String kaigaiBunya = (String) recordMap.get("KAIGAIBUNYA_CD");
            //2005.10.17 kainuma�ǉ�
            String edition = (String)recordMap.get("EDITION");
            //�ǉ��ȏ�
        	String keihi1 = (String) recordMap.get("KEIHI1");
            String keihi2 = (String) recordMap.get("KEIHI2");
            String keihi3 = (String) recordMap.get("KEIHI3");
            String keihi4 = (String) recordMap.get("KEIHI4");
            String keihi5 = (String) recordMap.get("KEIHI5");
            String kadaiNoKeizoku = (String) recordMap.get("KADAI_NO_KEIZOKU");
            String buntanKinFlg = (String) recordMap.get("BUNTANKINFLG");
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");
            String kenkyuNinzu = (String) recordMap.get("KENKYUNINZU");
        	String kaijikibo = (String) recordMap.get("KAIJIKIBO_FLG");
            String shinseiFlg = (String) recordMap.get("SHINSEI_FLG_NO");
            String kadaiNoSaisyu = (String) recordMap.get("KADAI_NO_SAISYU");
            String keywordCd = (String) recordMap.get("KEYWORD_CD");
            String otherKeyword = (String) recordMap.get("OTHER_KEYWORD");
            String buntanFlg = (String) recordMap.get("BUNTANFLG");
			//2005.10.17�@kainuma�ǉ�
			String nameKanaSei = (String)recordMap.get("NAMEKANASEI");
			String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");
			String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");
			String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");
			//�ǉ��ȏ�
            String syozokuCd = (String) recordMap.get("SHOZOKUCD");
            String bukyokuCd = (String) recordMap.get("BUKYOKUCD");
            String syokusyuCd = (String) recordMap.get("SHOKUSHUCD");
            String kenkyuNo = (String) recordMap.get("KENKYUNO");
        	String keihi = (String) recordMap.get("KEIHI");
            String effort = (String) recordMap.get("EFFORT");
            
            
            //DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
            recordMap = null;
            list.set(i, null);
            
            
			//�זڈȊO�̃L�[���[�h�𔼊p����S�p�ɂ���
			//char�̔z��ɕϊ�
            //TODO
			String ZenkakuOtherKeyword = hanZenConverter.convert(otherKeyword);
			char[] cArray = ZenkakuOtherKeyword.toCharArray();
			for (int j = 0; j < cArray.length; j++) {
				if (StringUtil.isHankaku(cArray[j])) {
					cArray[j] = HOSHI; //���p�̏ꍇ�͒u������
				  }

			  }
			  //������ɖ߂�
			  String checkedOtherKeyword = new String(cArray);
			  if (checkedOtherKeyword.length() > otherKeywordLength){
				checkedOtherKeyword = checkedOtherKeyword.substring(0, otherKeywordLength);
			  }

			  //�זڈȊO�̃L�[���[�h��25�����̕����̌Œ蒷�ɂ���
			  while (checkedOtherKeyword.length() < otherKeywordLength) {
				checkedOtherKeyword += charToBeAdded;
			  }


			finalResult.append(jigyoId)
            		   .append(shinsaKbn)
            		   .append(shinseiKbn)
            		   .append(syozokuCdDaihyo)
           			   .append(bunkaSaimokuCd)
            		   .append(bunkatsNoAB)
            		   .append(bunkatsNo12)
            		   .append(seiriNumber)
            		   .append(kenkyuTaisho)
            		   .append(kaigaiBunya)
            		   .append(edition)
            		   .append(keihi1)
            		   .append(keihi2)
            		   .append(keihi3)
            		   .append(keihi4)
            		   .append(keihi5)
            		   .append(kadaiNoKeizoku)
            		   .append(buntanKinFlg)
            		   .append(kadaiNameKanji)
            		   .append(kenkyuNinzu)
            		   .append(kaijikibo)
            		   .append(shinseiFlg)
            		   .append(kadaiNoSaisyu)
            		   .append(keywordCd)
            		   .append(checkedOtherKeyword)
            		   .append(buntanFlg)
					   //2005.10.17 kainuma�ǉ�
					   .append(nameKanaSei)
					   .append(nameKanaMei)
					   .append(nameKanjiSei)
					   .append(nameKanjiMei)
					   //�ǉ��ȏ�
            		   .append(syozokuCd)
            		   .append(bukyokuCd)
            		   .append(syokusyuCd)
            		   .append(kenkyuNo)
            		   .append(keihi)
            		   .append(effort)
            		   .append(LINE_SHIFT).toString();

            //finalList.add(record);
            //finalResult = finalResult + (String) finalList.get(i);
        }
        log.info("�p���`�f�[�^�����I���B");
        return finalResult.toString();
    }

    /**
     * ����̈挤��(�V�K�̈�)�̈�v�揑�T�v�̃p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataTokuteiSinnkiGaiyo(Connection connection)
        throws ApplicationException,
            DataAccessException,
            NoDataFoundException
    {

        String query = "SELECT "
                //update liuyi start 2006/06/29
                + " SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU" //������ڔԍ�                           
                + ",NVL(A.UKETUKE_NO,'    ') UKETUKE_NO" // �����ԍ�                           
                + ",LPAD(NVL(A.KIBOUBUMON_CD,' '),2,' ') KIBOUBUMON_CD" // �R����]����i�n���j               
                + ",NVL(A.KARIRYOIKI_NO,'     ') KARIRYOIKI_NO" // ���̈�ԍ�                         
                + ",RPAD(NVL(A.RYOIKI_NAME,' '),80,'�@')  RYOIKI_NAME" // ����̈於�i�a���j                 
                + ",RPAD(NVL(A.RYOIKI_NAME_EIGO,' '),200,'�@')  RYOIKI_NAME_EIGO" // ����̈於�i�p���j                 
                + ",RPAD(NVL(A.RYOIKI_NAME_RYAKU,' '),16,'�@')  RYOIKI_NAME_RYAKU" // ����̈於�i���́j                 
                + ",RPAD(A.NAME_KANA_SEI,32,'�@') NAME_KANA_SEI" // �̈��\�ҁu�t���K�i�v�i���j       
                + ",RPAD(NVL(A.NAME_KANA_MEI, '�@'),32,'�@') NAME_KANA_MEI" // �̈��\�ҁu�t���K�i�v�i���j       
                + ",RPAD(A.NAME_KANJI_SEI,32,'�@') NAME_KANJI_SEI" // �̈��\�ҁu�������v�i���j         
                + ",RPAD(NVL(A.NAME_KANJI_MEI, '�@'),32,'�@') NAME_KANJI_MEI" // �̈��\�ҁu�������v�i���j         
                + ",A.SHOZOKU_CD  SHOZOKU_CD" // ���������@�փR�[�h                 
                + ",A.BUKYOKU_CD BUKYOKU_CD " // ���ǃR�[�h                         
                + ",RPAD(A.BUKYOKU_NAME,80,'�@') BUKYOKU_NAME" // ���ǖ�                             
                + ",LPAD(A.SHOKUSHU_CD,2,' ') SHOKUSHU_CD" // �E���R�[�h                         
                + ",RPAD(A.SHOKUSHU_NAME_KANJI,100,' ') SHOKUSHU_NAME_KANJI " // �E��                               
                + ",RPAD(NVL(A.KENKYU_GAIYOU,' '),40,' ') KENKYU_GAIYOU " // ����̈�̌����T�v                 
                + ",LPAD(A.JIZENCHOUSA_FLG,1,'0') JIZENCHOUSA_FLG " // ���������E���O�����̏󋵃R�[�h     
                + ",RPAD(NVL(A.JIZENCHOUSA_SONOTA,' '),40,' ') JIZENCHOUSA_SONOTA " // ���������E���O�����̏󋵖���       
                + ",LPAD(A.ZENNENDO_OUBO_FLG,1,'0') ZENNENDO_OUBO_FLG " // �O�N�x�̉���Y���t���O             
                + ",LPAD(NVL(A.ZENNENDO_OUBO_NO,' '),3,' ') ZENNENDO_OUBO_NO " // �O�N�x�̉���̈�R�[�h             
                + ",LPAD(NVL(A.BUNKASAIMOKU_CD1,' '),4,' ') BUNKASAIMOKU_CD1 " // �֘A����̍זڔԍ�1                
                + ",LPAD(NVL(A.BUNKASAIMOKU_CD2,' '),4,' ') BUNKASAIMOKU_CD2 " // �֘A����̍זڔԍ�2                
                + ",LPAD(NVL(A.KANRENBUNYA_BUNRUI_NO,''),2,' ') KANRENBUNYA_BUNRUI_NO " // �֘A����15���ރR�[�h               
                + ",LPAD(A.KENKYU_HITSUYOUSEI_1,1,'0') KENKYU_HITSUYOUSEI_1 " // �����̕K�v��1   
                + ",LPAD(A.KENKYU_HITSUYOUSEI_2,1,'0') KENKYU_HITSUYOUSEI_2 " // �����̕K�v��2                      
                + ",LPAD(A.KENKYU_HITSUYOUSEI_3,1,'0') KENKYU_HITSUYOUSEI_3 " // �����̕K�v��3                      
                + ",LPAD(A.KENKYU_HITSUYOUSEI_4,1,'0') KENKYU_HITSUYOUSEI_4 " // �����̕K�v��4                      
                + ",LPAD(A.KENKYU_HITSUYOUSEI_5,1,'0') KENKYU_HITSUYOUSEI_5 " // �����̕K�v��5                      
                + ",LPAD(NVL(A.KENKYU_SYOKEI_1,'0'),7,'0') KENKYU_SYOKEI_1 " // �o��F���匤���̏��v1�N��          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_1,' '),30,' ') KENKYU_UTIWAKE_1 " // �o��F���匤���̓���1�N��          
                + ",LPAD(NVL(A.KENKYU_SYOKEI_2,'0'),7,'0') KENKYU_SYOKEI_2 " // �o��F���匤���̏��v2�N��          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_2,' '),30,' ') KENKYU_UTIWAKE_2 " // �o��F���匤���̓���2�N��          
                + ",LPAD(NVL(A.KENKYU_SYOKEI_3,'0'),7,'0') KENKYU_SYOKEI_3 " // �o��F���匤���̏��v3�N��          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_3,' '),30,' ') KENKYU_UTIWAKE_3 " // �o��F���匤���̓���3�N��          
                + ",LPAD(NVL(A.KENKYU_SYOKEI_4,'0'),7,'0') KENKYU_SYOKEI_4 " // �o��F���匤���̏��v4�N��          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_4,' '),30,' ') KENKYU_UTIWAKE_4 " // �o��F���匤���̓���4�N��          
                + ",LPAD(NVL(A.KENKYU_SYOKEI_5,'0'),7,'0') KENKYU_SYOKEI_5 " // �o��F���匤���̏��v5�N��          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_5,' '),30,' ') KENKYU_UTIWAKE_5 " // �o��F���匤���̓���5�N��          
                + ",LPAD(NVL(A.KENKYU_SYOKEI_6,'0'),7,'0') KENKYU_SYOKEI_6 " // �o��F���匤���̏��v6�N��          
                + ",RPAD(NVL(A.KENKYU_UTIWAKE_6,' '),30,' ') KENKYU_UTIWAKE_6 " // �o��F���匤���̓���6�N��          
                + ",NVL(A.DAIHYOU_ZIP,'        ') DAIHYOU_ZIP " // �̈��\�ҁF�X�֔ԍ�               
                + ",RPAD(NVL(A.DAIHYOU_ADDRESS,' '),100,' ') DAIHYOU_ADDRESS " // �̈��\�ҁF�Z��                   
                + ",RPAD(NVL(A.DAIHYOU_TEL,' '),50,' ') DAIHYOU_TEL " // �̈��\�ҁF�d�b�ԍ�               
                + ",RPAD(NVL(A.DAIHYOU_FAX,' '),50,' ') DAIHYOU_FAX " // �̈��\�ҁFFAX�ԍ�                
                + ",RPAD(NVL(A.DAIHYOU_EMAIL,' '),50,' ') DAIHYOU_EMAIL " // �̈��\�ҁFEmail                  
                + ",RPAD(NVL(A.JIMUTANTO_NAME_KANJI_SEI, ' '),32,'�@') JIMUTANTO_NAME_KANJI_SEI" // �����S���ҁu�t���K�i�v�i���j       
                + ",RPAD(NVL(A.JIMUTANTO_NAME_KANJI_MEI, ' '),32,'�@') JIMUTANTO_NAME_KANJI_MEI" // �����S���ҁu�t���K�i�v�i���j       
                + ",RPAD(NVL(A.JIMUTANTO_NAME_KANA_SEI, ' '),32,'�@') JIMUTANTO_NAME_KANA_SEI" // �����S���ҁu�������v�i���j         
                + ",RPAD(NVL(A.JIMUTANTO_NAME_KANA_MEI, ' '),32,'�@') JIMUTANTO_NAME_KANA_MEI" // �����S���ҁu�������v�i���j         
                + ",LPAD(NVL(A.JIMUTANTO_SHOZOKU_CD, ' '),5,'�@') JIMUTANTO_SHOZOKU_CD" // �����S���ҁF���������@�փR�[�h     
                + ",LPAD(NVL(A.JIMUTANTO_BUKYOKU_CD, ' '),3,'�@') JIMUTANTO_BUKYOKU_CD" // �����S���ҁF���ǃR�[�h             
                + ",RPAD(NVL(A.JIMUTANTO_BUKYOKU_NAME, ' '),100,'�@') JIMUTANTO_BUKYOKU_NAME" // �����S���ҁF���ǖ�                 
                + ",LPAD(NVL(A.JIMUTANTO_SHOKUSHU_CD, '�@'),2,'�@') JIMUTANTO_SHOKUSHU_CD" // �����S���ҁF�E���R�[�h             
                + ",RPAD(NVL(A.JIMUTANTO_SHOKUSHU_NAME_KANJI, '�@'),40,'�@') JIMUTANTO_SHOKUSHU_NAME_KANJI"// �����S���ҁF�E��                               
                + ",NVL(A.JIMUTANTO_ZIP, '        ') JIMUTANTO_ZIP" // �����S���ҁF�X�֔ԍ�               
                + ",RPAD(NVL(A.JIMUTANTO_ADDRESS, '�@'),100,'�@') JIMUTANTO_ADDRESS" // �����S���ҁF�Z��                   
                + ",RPAD(NVL(A.JIMUTANTO_TEL, '�@'),50,'�@') JIMUTANTO_TEL" // �����S���ҁF�d�b�ԍ�               
                + ",RPAD(NVL(A.JIMUTANTO_FAX, '�@'),50,'�@') JIMUTANTO_FAX" // �����S���ҁFFAX�ԍ�                
                + ",RPAD(NVL(A.JIMUTANTO_EMAIL, '�@'),50,'�@') JIMUTANTO_EMAIL" // �����S���ҁFEmail                  
                + ",RPAD(NVL(A.KANREN_SHIMEI1, '�@'),32,'�@') KANREN_SHIMEI1" // �֘A�������쌤����1�F����          
                + ",RPAD(NVL(A.KANREN_KIKAN1, '�@'),80,'�@') KANREN_KIKAN1" // �֘A�������쌤����1�F�����@�֖�    
                + ",RPAD(NVL(A.KANREN_BUKYOKU1, '�@'),100,'�@') KANREN_BUKYOKU1" // �֘A�������쌤����1�F����          
                + ",RPAD(NVL(A.KANREN_SHOKU1, '�@'),40,'�@') KANREN_SHOKU1" // �֘A�������쌤����1�F�E            
                + ",RPAD(NVL(A.KANREN_SENMON1, '�@'),40,'�@') KANREN_SENMON1" // �֘A�������쌤����1�F���݂̐��    
                + ",RPAD(NVL(A.KANREN_TEL1, '�@'),50,'�@') KANREN_TEL1" // �֘A�������쌤����1�F�Ζ���d�b�ԍ�
                + ",RPAD(NVL(A.KANREN_JITAKUTEL1, '�@'),50,'�@') KANREN_JITAKUTEL1" // �֘A�������쌤����1�F����d�b�ԍ�  
                + ",RPAD(NVL(A.KANREN_SHIMEI2, '�@'),32,'�@') KANREN_SHIMEI2" // �֘A�������쌤����2�F����          
                + ",RPAD(NVL(A.KANREN_KIKAN2, '�@'),80,'�@') KANREN_KIKAN2" // �֘A�������쌤����2�F�����@�֖�    
                + ",RPAD(NVL(A.KANREN_BUKYOKU2, '�@'),100,'�@') KANREN_BUKYOKU2" // �֘A�������쌤����2�F����          
                + ",RPAD(NVL(A.KANREN_SHOKU2, '�@'),40,'�@') KANREN_SHOKU2" // �֘A�������쌤����2�F�E            
                + ",RPAD(NVL(A.KANREN_SENMON2, '�@'),40,'�@') KANREN_SENMON2" // �֘A�������쌤����2�F���݂̐��    
                + ",RPAD(NVL(A.KANREN_TEL2, '�@'),50,'�@') KANREN_TEL2" // �֘A�������쌤����2�F�Ζ���d�b�ԍ�
                + ",RPAD(NVL(A.KANREN_JITAKUTEL2, '�@'),50,'�@')KANREN_JITAKUTEL2" // �֘A�������쌤����2�F����d�b�ԍ�  
                + ",RPAD(NVL(A.KANREN_SHIMEI3, '�@'),32,'�@') KANREN_SHIMEI3" // �֘A�������쌤����3�F����          
                + ",RPAD(NVL(A.KANREN_KIKAN3, '�@'),80,'�@') KANREN_KIKAN3" // �֘A�������쌤����3�F�����@�֖�    
                + ",RPAD(NVL(A.KANREN_BUKYOKU3, '�@'),100,'�@') KANREN_BUKYOKU3" // �֘A�������쌤����3�F����          
                + ",RPAD(NVL(A.KANREN_SHOKU3, '�@'),40,'�@') KANREN_SHOKU3" // �֘A�������쌤����3�F�E            
                + ",RPAD(NVL(A.KANREN_SENMON3, '�@'),40,'�@') KANREN_SENMON3" // �֘A�������쌤����3�F���݂̐��    
                + ",RPAD(NVL(A.KANREN_TEL3, '�@'),50,'�@') KANREN_TEL3" // �֘A�������쌤����3�F�Ζ���d�b�ԍ�
                + ",RPAD(NVL(A.KANREN_JITAKUTEL3, '�@'),50,'�@') KANREN_JITAKUTEL3" // �֘A�������쌤����3�F����d�b�ԍ� 
                + " FROM RYOIKIKEIKAKUSHOINFO A"
                + " WHERE DEL_FLG = 0"
                + "   AND SUBSTR(A.JIGYO_ID,3,5) ='00022' "
                + "   AND TO_NUMBER(RYOIKI_JOKYO_ID) IN (6,8,9,10,11,12,21,22,23,24)"
                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.KARIRYOIKI_NO,  KARIRYOIKI_NO"; //����ID���A�@��-���̈�ԍ�-���ڔԍ�-�����ԍ���

        //for debug
        if (log.isDebugEnabled())
        {
            log.debug("query:" + query);
        }

        List list = new ArrayList();

        //-----------------------
        // ���X�g�擾
        //-----------------------
        try
        {
            list = SelectUtil.select(connection, query.toString());
        }
        catch (DataAccessException e)
        {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                                           new ErrorInfo("errors.4004"), e);
        }
        catch (NoDataFoundException e)
        {
            throw new NoDataFoundException("�f�[�^�x�[�X��1�����f�[�^������܂���B", e);
        }

        //�ŏI�I��return����String��錾����
        StringBuffer finalResult = new StringBuffer();

        for (int i = 0; i < list.size(); i++)
        {

            //	long start = System.currentTimeMillis();

            //DB����擾����List�^list��String�^�ɂ��āA����String��List�����
            Map recordMap = (Map)list.get(i);
            String KENKYUSHUMOKU = "02"; //������ڔԍ�                       
            String UKETUKE_NO = (String)recordMap.get("UKETUKE_NO"); //�����ԍ�                           
            String KIBOUBUMON_CD = (String)recordMap.get("KIBOUBUMON_CD"); //�R����]����i�n���j               
            String KARIRYOIKI_NO = (String)recordMap.get("KARIRYOIKI_NO"); //���̈�ԍ�                         
            String RYOIKI_NAME = (String)recordMap.get("RYOIKI_NAME"); //����̈於�i�a���j                 
            String RYOIKI_NAME_EIGO = (String)recordMap.get("RYOIKI_NAME_EIGO"); //����̈於�i�p���j                 
            String RYOIKI_NAME_RYAKU = (String)recordMap.get("RYOIKI_NAME_RYAKU");
            ; //����̈於�i���́jNAMEKANASEI                 
            String NAME_KANJI_SEI = (String)recordMap.get("NAME_KANJI_SEI"); //�̈��\�ҁu�t���K�i�v�i���j       
            String NAME_KANJI_MEI = (String)recordMap.get("NAME_KANJI_MEI"); //�̈��\�ҁu�t���K�i�v�i���j       
            String NAME_KANA_SEI = (String)recordMap.get("NAME_KANA_SEI"); //�̈��\�ҁu�������v�i���j         
            String NAME_KANA_MEI = (String)recordMap.get("NAME_KANA_MEI"); //�̈��\�ҁu�������v�i���j         
            String SHOZOKU_CD = (String)recordMap.get("SHOZOKU_CD"); //���������@�փR�[�h                 
            String BUKYOKU_CD = (String)recordMap.get("BUKYOKU_CD"); //���ǃR�[�h                         
            String BUKYOKU_NAME = (String)recordMap.get("BUKYOKU_NAME");
            ; //���ǖ�                             
            String SHOKUSHU_CD = (String)recordMap.get("SHOKUSHU_CD");
            ; //�E���R�[�h                         
            String SHOKUSHU_NAME_KANJI = (String)recordMap.get("SHOKUSHU_NAME_KANJI"); //�E��                               
            String KENKYU_GAIYOU = (String)recordMap.get("KENKYU_GAIYOU"); //����̈�̌����T�v                 
            String JIZENCHOUSA_FLG = (String)recordMap.get("JIZENCHOUSA_FLG"); //���������E���O�����̏󋵃R�[�h     
            String JIZENCHOUSA_SONOTA = (String)recordMap.get("JIZENCHOUSA_SONOTA"); //���������E���O�����̏󋵖���       
            String ZENNENDO_OUBO_FLG = (String)recordMap.get("ZENNENDO_OUBO_FLG"); //�O�N�x�̉���Y���t���O             
            String ZENNENDO_OUBO_NO = (String)recordMap.get("ZENNENDO_OUBO_NO"); //�O�N�x�̉���̈�R�[�h             
            String BUNKASAIMOKU_CD1 = (String)recordMap.get("BUNKASAIMOKU_CD1"); //�֘A����̍זڔԍ�1                
            String BUNKASAIMOKU_CD2 = (String)recordMap.get("BUNKASAIMOKU_CD2"); //�֘A����̍זڔԍ�2                
            String KANRENBUNYA_BUNRUI_NO = (String)recordMap.get("KANRENBUNYA_BUNRUI_NO"); //�֘A����15���ރR�[�h               
            String KENKYU_HITSUYOUSEI_1 = (String)recordMap.get("KENKYU_HITSUYOUSEI_1"); //�����̕K�v��1                      
            String KENKYU_HITSUYOUSEI_2 = (String)recordMap.get("KENKYU_HITSUYOUSEI_2"); //�����̕K�v��2                      
            String KENKYU_HITSUYOUSEI_3 = (String)recordMap.get("KENKYU_HITSUYOUSEI_3"); //�����̕K�v��3                      
            String KENKYU_HITSUYOUSEI_4 = (String)recordMap.get("KENKYU_HITSUYOUSEI_4"); //�����̕K�v��4                      
            String KENKYU_HITSUYOUSEI_5 = (String)recordMap.get("KENKYU_HITSUYOUSEI_5"); //�����̕K�v��5                      
            String KENKYU_SYOKEI_1 = (String)recordMap.get("KENKYU_SYOKEI_1"); //�o��F���匤���̏��v1�N��          
            String KENKYU_UTIWAKE_1 = (String)recordMap.get("KENKYU_UTIWAKE_1"); //�o��F���匤���̓���1�N��          
            String KENKYU_SYOKEI_2 = (String)recordMap.get("KENKYU_SYOKEI_2"); //�o��F���匤���̏��v2�N��          
            String KENKYU_UTIWAKE_2 = (String)recordMap.get("KENKYU_UTIWAKE_2"); //�o��F���匤���̓���2�N��          
            String KENKYU_SYOKEI_3 = (String)recordMap.get("KENKYU_SYOKEI_3"); //�o��F���匤���̏��v3�N��          
            String KENKYU_UTIWAKE_3 = (String)recordMap.get("KENKYU_UTIWAKE_3"); //�o��F���匤���̓���3�N��          
            String KENKYU_SYOKEI_4 = (String)recordMap.get("KENKYU_SYOKEI_4"); //�o��F���匤���̏��v4�N��          
            String KENKYU_UTIWAKE_4 = (String)recordMap.get("KENKYU_UTIWAKE_4"); //�o��F���匤���̓���4�N��          
            String KENKYU_SYOKEI_5 = (String)recordMap.get("KENKYU_SYOKEI_5"); //�o��F���匤���̏��v5�N��          
            String KENKYU_UTIWAKE_5 = (String)recordMap.get("KENKYU_UTIWAKE_5"); //�o��F���匤���̓���5�N��          
            String KENKYU_SYOKEI_6 = (String)recordMap.get("KENKYU_SYOKEI_6"); //�o��F���匤���̏��v6�N��          
            String KENKYU_UTIWAKE_6 = (String)recordMap.get("KENKYU_UTIWAKE_6"); //�o��F���匤���̓���6�N��          
            String DAIHYOU_ZIP = (String)recordMap.get("DAIHYOU_ZIP"); //�̈��\�ҁF�X�֔ԍ�               
            String DAIHYOU_ADDRESS = (String)recordMap.get("DAIHYOU_ADDRESS"); //�̈��\�ҁF�Z��                   
            String DAIHYOU_TEL = (String)recordMap.get("DAIHYOU_TEL"); //�̈��\�ҁF�d�b�ԍ�               
            String DAIHYOU_FAX = (String)recordMap.get("DAIHYOU_FAX"); //�̈��\�ҁFFAX�ԍ�                
            String DAIHYOU_EMAIL = (String)recordMap.get("DAIHYOU_EMAIL"); //�̈��\�ҁFEmail                  
            String JIMUTANTO_NAME_KANJI_SEI = (String)recordMap.get("JIMUTANTO_NAME_KANJI_SEI"); //�����S���ҁu�t���K�i�v�i���j       
            String JIMUTANTO_NAME_KANJI_MEI = (String)recordMap.get("JIMUTANTO_NAME_KANJI_MEI"); //�����S���ҁu�t���K�i�v�i���j       
            String JIMUTANTO_NAME_KANA_SEI = (String)recordMap.get("JIMUTANTO_NAME_KANA_SEI"); //�����S���ҁu�������v�i���j         
            String JIMUTANTO_NAME_KANA_MEI = (String)recordMap.get("JIMUTANTO_NAME_KANA_MEI"); //�����S���ҁu�������v�i���j         
            String JIMUTANTO_SHOZOKU_CD = (String)recordMap.get("JIMUTANTO_SHOZOKU_CD"); //�����S���ҁF���������@�փR�[�h     
            String JIMUTANTO_BUKYOKU_CD = (String)recordMap.get("JIMUTANTO_BUKYOKU_CD"); //�����S���ҁF���ǃR�[�h             
            String JIMUTANTO_BUKYOKU_NAME = (String)recordMap.get("JIMUTANTO_BUKYOKU_NAME"); //�����S���ҁF���ǖ�                 
            String JIMUTANTO_SHOKUSHU_CD = (String)recordMap.get("JIMUTANTO_SHOKUSHU_CD"); //�����S���ҁF�E���R�[�h             
            String JIMUTANTO_S_N_KANJI = (String)recordMap.get("JIMUTANTO_SHOKUSHU_NAME_KANJI"); //�����S���ҁF�E��                   
            String JIMUTANTO_ZIP = (String)recordMap.get("JIMUTANTO_ZIP"); //�����S���ҁF�X�֔ԍ�               
            String JIMUTANTO_ADDRESS = (String)recordMap.get("JIMUTANTO_ADDRESS"); //�����S���ҁF�Z��                   
            String JIMUTANTO_TEL = (String)recordMap.get("JIMUTANTO_TEL"); //�����S���ҁF�d�b�ԍ�               
            String JIMUTANTO_FAX = (String)recordMap.get("JIMUTANTO_FAX"); //�����S���ҁFFAX�ԍ�                
            String JIMUTANTO_EMAIL = (String)recordMap.get("JIMUTANTO_EMAIL"); //�����S���ҁFEmail                  
            String KANREN_SHIMEI1 = (String)recordMap.get("KANREN_SHIMEI1"); //�֘A�������쌤����1�F����          
            String KANREN_KIKAN1 = (String)recordMap.get("KANREN_KIKAN1"); //�֘A�������쌤����1�F�����@�֖�    
            String KANREN_BUKYOKU1 = (String)recordMap.get("KANREN_BUKYOKU1"); //�֘A�������쌤����1�F����          
            String KANREN_SHOKU1 = (String)recordMap.get("KANREN_SHOKU1"); //�֘A�������쌤����1�F�E            
            String KANREN_SENMON1 = (String)recordMap.get("KANREN_SENMON1"); //�֘A�������쌤����1�F���݂̐��    
            String KANREN_TEL1 = (String)recordMap.get("KANREN_TEL1"); //�֘A�������쌤����1�F�Ζ���d�b�ԍ�
            String KANREN_JITAKUTEL1 = (String)recordMap.get("KANREN_JITAKUTEL1"); //�֘A�������쌤����1�F����d�b�ԍ�  
            String KANREN_SHIMEI2 = (String)recordMap.get("KANREN_SHIMEI2"); //�֘A�������쌤����2�F����          
            String KANREN_KIKAN2 = (String)recordMap.get("KANREN_KIKAN2"); //�֘A�������쌤����2�F�����@�֖�    
            String KANREN_BUKYOKU2 = (String)recordMap.get("KANREN_BUKYOKU2"); //�֘A�������쌤����2�F����          
            String KANREN_SHOKU2 = (String)recordMap.get("KANREN_SHOKU2"); //�֘A�������쌤����2�F�E            
            String KANREN_SENMON2 = (String)recordMap.get("KANREN_SENMON2"); //�֘A�������쌤����2�F���݂̐��    
            String KANREN_TEL2 = (String)recordMap.get("KANREN_TEL2"); //�֘A�������쌤����2�F�Ζ���d�b�ԍ�
            String KANREN_JITAKUTEL2 = (String)recordMap.get("KANREN_JITAKUTEL2"); //�֘A�������쌤����2�F����d�b�ԍ�  
            String KANREN_SHIMEI3 = (String)recordMap.get("KANREN_SHIMEI3"); //�֘A�������쌤����3�F����          
            String KANREN_KIKAN3 = (String)recordMap.get("KANREN_KIKAN3"); //�֘A�������쌤����3�F�����@�֖�    
            String KANREN_BUKYOKU3 = (String)recordMap.get("KANREN_BUKYOKU3"); //�֘A�������쌤����3�F����          
            String KANREN_SHOKU3 = (String)recordMap.get("KANREN_SHOKU3"); //�֘A�������쌤����3�F�E            
            String KANREN_SENMON3 = (String)recordMap.get("KANREN_SENMON3"); //�֘A�������쌤����3�F���݂̐��    
            String KANREN_TEL3 = (String)recordMap.get("KANREN_TEL3"); //�֘A�������쌤����3�F�Ζ���d�b�ԍ�
            String KANREN_JITAKUTEL3 = (String)recordMap.get("KANREN_JITAKUTEL3"); //�֘A�������쌤����3�F����d�b�ԍ� 

            //DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
            recordMap = null;
            list.set(i, null);

            //�ŏI���ʂɃ��R�[�h�i�{�s�j�𕶎��񌋍�
            finalResult.append(KENKYUSHUMOKU) //������ڔԍ�                       
                    .append(UKETUKE_NO) //�����ԍ�                           
                    .append(KIBOUBUMON_CD) //�R����]����i�n���j               
                    .append(KARIRYOIKI_NO) //���̈�ԍ�                         
                    .append(RYOIKI_NAME) //����̈於�i�a���j                 
                    .append(RYOIKI_NAME_EIGO) //����̈於�i�p���j                 
                    .append(RYOIKI_NAME_RYAKU) //����̈於�i���́j                 
                    .append(NAME_KANJI_SEI) //�̈��\�ҁu�t���K�i�v�i���j       
                    .append(NAME_KANJI_MEI) //�̈��\�ҁu�t���K�i�v�i���j       
                    .append(NAME_KANA_SEI) //�̈��\�ҁu�������v�i���j         
                    .append(NAME_KANA_MEI) //�̈��\�ҁu�������v�i���j         
                    .append(SHOZOKU_CD) //���������@�փR�[�h                 
                    .append(BUKYOKU_CD) //���ǃR�[�h                         
                    .append(BUKYOKU_NAME) //���ǖ�                             
                    .append(SHOKUSHU_CD) //�E���R�[�h                         
                    .append(SHOKUSHU_NAME_KANJI) //�E��                               
                    .append(KENKYU_GAIYOU) //����̈�̌����T�v                 
                    .append(JIZENCHOUSA_FLG) //���������E���O�����̏󋵃R�[�h     
                    .append(JIZENCHOUSA_SONOTA) //���������E���O�����̏󋵖���       
                    .append(ZENNENDO_OUBO_FLG) //�O�N�x�̉���Y���t���O             
                    .append(ZENNENDO_OUBO_NO) //�O�N�x�̉���̈�R�[�h             
                    .append(BUNKASAIMOKU_CD1) //�֘A����̍זڔԍ�1                
                    .append(BUNKASAIMOKU_CD2) //�֘A����̍זڔԍ�2                
                    .append(KANRENBUNYA_BUNRUI_NO) //�֘A����15���ރR�[�h               
                    .append(KENKYU_HITSUYOUSEI_1) //�����̕K�v��1                      
                    .append(KENKYU_HITSUYOUSEI_2) //�����̕K�v��2                      
                    .append(KENKYU_HITSUYOUSEI_3) //�����̕K�v��3                      
                    .append(KENKYU_HITSUYOUSEI_4) //�����̕K�v��4                      
                    .append(KENKYU_HITSUYOUSEI_5) //�����̕K�v��5                      
                    .append(KENKYU_SYOKEI_1) //�o��F���匤���̏��v1�N��          
                    .append(KENKYU_UTIWAKE_1) //�o��F���匤���̓���1�N��          
                    .append(KENKYU_SYOKEI_2) //�o��F���匤���̏��v2�N��          
                    .append(KENKYU_UTIWAKE_2) //�o��F���匤���̓���2�N��          
                    .append(KENKYU_SYOKEI_3) //�o��F���匤���̏��v3�N��          
                    .append(KENKYU_UTIWAKE_3) //�o��F���匤���̓���3�N��          
                    .append(KENKYU_SYOKEI_4) //�o��F���匤���̏��v4�N��          
                    .append(KENKYU_UTIWAKE_4) //�o��F���匤���̓���4�N��          
                    .append(KENKYU_SYOKEI_5) //�o��F���匤���̏��v5�N��          
                    .append(KENKYU_UTIWAKE_5) //�o��F���匤���̓���5�N��          
                    .append(KENKYU_SYOKEI_6) //�o��F���匤���̏��v6�N��          
                    .append(KENKYU_UTIWAKE_6) //�o��F���匤���̓���6�N��          
                    .append(DAIHYOU_ZIP) //�̈��\�ҁF�X�֔ԍ�               
                    .append(DAIHYOU_ADDRESS) //�̈��\�ҁF�Z��                   
                    .append(DAIHYOU_TEL) //�̈��\�ҁF�d�b�ԍ�               
                    .append(DAIHYOU_FAX) //�̈��\�ҁFFAX�ԍ�                
                    .append(DAIHYOU_EMAIL) //�̈��\�ҁFEmail                  
                    .append(JIMUTANTO_NAME_KANJI_SEI) //�����S���ҁu�t���K�i�v�i���j       
                    .append(JIMUTANTO_NAME_KANJI_MEI) //�����S���ҁu�t���K�i�v�i���j       
                    .append(JIMUTANTO_NAME_KANA_SEI) //�����S���ҁu�������v�i���j         
                    .append(JIMUTANTO_NAME_KANA_MEI) //�����S���ҁu�������v�i���j         
                    .append(JIMUTANTO_SHOZOKU_CD) //�����S���ҁF���������@�փR�[�h     
                    .append(JIMUTANTO_BUKYOKU_CD) //�����S���ҁF���ǃR�[�h             
                    .append(JIMUTANTO_BUKYOKU_NAME) //�����S���ҁF���ǖ�                 
                    .append(JIMUTANTO_SHOKUSHU_CD) //�����S���ҁF�E���R�[�h             
                    .append(JIMUTANTO_S_N_KANJI) //�����S���ҁF�E��                   
                    .append(JIMUTANTO_ZIP) //�����S���ҁF�X�֔ԍ�               
                    .append(JIMUTANTO_ADDRESS) //�����S���ҁF�Z��                   
                    .append(JIMUTANTO_TEL) //�����S���ҁF�d�b�ԍ�               
                    .append(JIMUTANTO_FAX) //�����S���ҁFFAX�ԍ�                
                    .append(JIMUTANTO_EMAIL) //�����S���ҁFEmail                  
                    .append(KANREN_SHIMEI1) //�֘A�������쌤����1�F����          
                    .append(KANREN_KIKAN1) //�֘A�������쌤����1�F�����@�֖�    
                    .append(KANREN_BUKYOKU1) //�֘A�������쌤����1�F����          
                    .append(KANREN_SHOKU1) //�֘A�������쌤����1�F�E            
                    .append(KANREN_SENMON1) //�֘A�������쌤����1�F���݂̐��    
                    .append(KANREN_TEL1) //�֘A�������쌤����1�F�Ζ���d�b�ԍ�
                    .append(KANREN_JITAKUTEL1) //�֘A�������쌤����1�F����d�b�ԍ�  
                    .append(KANREN_SHIMEI2) //�֘A�������쌤����2�F����          
                    .append(KANREN_KIKAN2) //�֘A�������쌤����2�F�����@�֖�    
                    .append(KANREN_BUKYOKU2) //�֘A�������쌤����2�F����          
                    .append(KANREN_SHOKU2) //�֘A�������쌤����2�F�E            
                    .append(KANREN_SENMON2) //�֘A�������쌤����2�F���݂̐��    
                    .append(KANREN_TEL2) //�֘A�������쌤����2�F�Ζ���d�b�ԍ�
                    .append(KANREN_JITAKUTEL2) //�֘A�������쌤����2�F����d�b�ԍ�  
                    .append(KANREN_SHIMEI3) //�֘A�������쌤����3�F����          
                    .append(KANREN_KIKAN3) //�֘A�������쌤����3�F�����@�֖�    
                    .append(KANREN_BUKYOKU3) //�֘A�������쌤����3�F����          
                    .append(KANREN_SHOKU3) //�֘A�������쌤����3�F�E            
                    .append(KANREN_SENMON3) //�֘A�������쌤����3�F���݂̐��    
                    .append(KANREN_TEL3) //�֘A�������쌤����3�F�Ζ���d�b�ԍ�
                    .append(KANREN_JITAKUTEL3) //�֘A�������쌤����3�F����d�b�ԍ� 
                    .append(LINE_SHIFT).toString();

        }

        return finalResult.toString();
    }

//Add By Sai 2006.09.19
    /**
     * ����̈挤��(�V�K�̈�)�̈�v�揑�T�v�̃p���`�f�[�^(Csv)���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataTokuteiSinnkiGaiyoCsv(Connection connection)
        throws ApplicationException,
            DataAccessException,
            NoDataFoundException
    {

    	List list = new ArrayList();
    	String query = "SELECT "
                + " '02' \"������ڔԍ�\"" //������ڔԍ�                           
                + ",TRIM(A.UKETUKE_NO)" // �����ԍ�                           
                + ",A.KIBOUBUMON_CD" // �R����]����i�n���j               
                + ",A.KARIRYOIKI_NO" // ���̈�ԍ�                         
                + ",A.RYOIKI_NAME" // ����̈於�i�a���j                 
                + ",A.RYOIKI_NAME_EIGO" // ����̈於�i�p���j                 
                + ",A.RYOIKI_NAME_RYAKU" // ����̈於�i���́j                 
                + ",A.NAME_KANJI_SEI" // �̈��\�ҁu�������v�i���j         
                + ",A.NAME_KANJI_MEI" // �̈��\�ҁu�������v�i���j         
                + ",A.NAME_KANA_SEI" // �̈��\�ҁu�t���K�i�v�i���j       
                + ",A.NAME_KANA_MEI" // �̈��\�ҁu�t���K�i�v�i���j       
                + ",A.SHOZOKU_CD " // ���������@�փR�[�h                 
                + ",A.BUKYOKU_CD  " // ���ǃR�[�h                         
                + ",A.BUKYOKU_NAME" // ���ǖ�                             
                + ",A.SHOKUSHU_CD" // �E���R�[�h                         
                + ",A.SHOKUSHU_NAME_KANJI" // �E��                               
                + ",A.KENKYU_GAIYOU" // ����̈�̌����T�v                 
                + ",A.JIZENCHOUSA_FLG" // ���������E���O�����̏󋵃R�[�h     
                + ",A.JIZENCHOUSA_SONOTA" // ���������E���O�����̏󋵖���       
                + ",A.ZENNENDO_OUBO_FLG" // �O�N�x�̉���Y���t���O             
                + ",A.ZENNENDO_OUBO_NO" // �O�N�x�̉���̈�R�[�h             
                + ",A.BUNKASAIMOKU_CD1" // �֘A����̍זڔԍ�1                
                + ",A.BUNKASAIMOKU_CD2" // �֘A����̍זڔԍ�2                
                + ",A.KANRENBUNYA_BUNRUI_NO" // �֘A����15���ރR�[�h               
                + ",A.KENKYU_HITSUYOUSEI_1" // �����̕K�v��1   
                + ",A.KENKYU_HITSUYOUSEI_2" // �����̕K�v��2                      
                + ",A.KENKYU_HITSUYOUSEI_3" // �����̕K�v��3                      
                + ",A.KENKYU_HITSUYOUSEI_4" // �����̕K�v��4                      
                + ",A.KENKYU_HITSUYOUSEI_5" // �����̕K�v��5                      
                + ",A.KENKYU_SYOKEI_1" // �o��F���匤���̏��v1�N��          
                + ",A.KENKYU_UTIWAKE_1" // �o��F���匤���̓���1�N��          
                + ",A.KENKYU_SYOKEI_2" // �o��F���匤���̏��v2�N��          
                + ",A.KENKYU_UTIWAKE_2" // �o��F���匤���̓���2�N��          
                + ",A.KENKYU_SYOKEI_3" // �o��F���匤���̏��v3�N��          
                + ",A.KENKYU_UTIWAKE_3" // �o��F���匤���̓���3�N��          
                + ",A.KENKYU_SYOKEI_4" // �o��F���匤���̏��v4�N��          
                + ",A.KENKYU_UTIWAKE_4" // �o��F���匤���̓���4�N��          
                + ",A.KENKYU_SYOKEI_5" // �o��F���匤���̏��v5�N��          
                + ",A.KENKYU_UTIWAKE_5" // �o��F���匤���̓���5�N��          
                + ",A.KENKYU_SYOKEI_6" // �o��F���匤���̏��v6�N��          
                + ",A.KENKYU_UTIWAKE_6" // �o��F���匤���̓���6�N��  
                //ADD START 2007/07/04 BIS ����
                //H19���S�d�q���y�ѐ��x����
                //����V�K�i�̈�v�揑�T�v�j������p���`�f�[�^�̉�����z�̍��v�Ɍv�Z���@ 
                
                
                + ",A.KENKYU_SYOKEI_1+KEIHI1" // �o��F������z�̍��v1�N��          
                      
                + ",A.KENKYU_SYOKEI_2+KEIHI2"  // �o��F������z�̍��v2�N��          
                      
                + ",A.KENKYU_SYOKEI_3+KEIHI3"  // �o��F������z�̍��v3�N��          
                         
                + ",A.KENKYU_SYOKEI_4+KEIHI4" // �o��F������z�̍��v4�N��          
                        
                + ",A.KENKYU_SYOKEI_5+KEIHI5"  // �o��F������z�̍��v5�N��          
                         
                + ",A.KENKYU_SYOKEI_6+KEIHI6"  // �o��F������z�̍��v6�N��          
                
                
                
                
                //ADD END 2007/07/04 BIS ����
                + ",A.DAIHYOU_ZIP" // �̈��\�ҁF�X�֔ԍ�               
                + ",A.DAIHYOU_ADDRESS" // �̈��\�ҁF�Z��                   
                + ",A.DAIHYOU_TEL" // �̈��\�ҁF�d�b�ԍ�               
                + ",A.DAIHYOU_FAX" // �̈��\�ҁFFAX�ԍ�                
                + ",A.DAIHYOU_EMAIL" // �̈��\�ҁFEmail                  
                + ",A.JIMUTANTO_NAME_KANJI_SEI" // �����S���ҁu�������v�i���j        
                + ",A.JIMUTANTO_NAME_KANJI_MEI" // �����S���ҁu�������v�i���j       
                + ",A.JIMUTANTO_NAME_KANA_SEI" //  �����S���ҁu�t���K�i�v�i���j    
                + ",A.JIMUTANTO_NAME_KANA_MEI" // �����S���ҁu�t���K�i�v�i���j         
                + ",A.JIMUTANTO_SHOZOKU_CD" // �����S���ҁF���������@�փR�[�h     
                + ",A.JIMUTANTO_BUKYOKU_CD" // �����S���ҁF���ǃR�[�h             
                + ",A.JIMUTANTO_BUKYOKU_NAME" // �����S���ҁF���ǖ�                 
                + ",A.JIMUTANTO_SHOKUSHU_CD" // �����S���ҁF�E���R�[�h             
                + ",A.JIMUTANTO_SHOKUSHU_NAME_KANJI"// �����S���ҁF�E��                               
                + ",A.JIMUTANTO_ZIP" // �����S���ҁF�X�֔ԍ�               
                + ",A.JIMUTANTO_ADDRESS" // �����S���ҁF�Z��                   
                + ",A.JIMUTANTO_TEL" // �����S���ҁF�d�b�ԍ�               
                + ",A.JIMUTANTO_FAX" // �����S���ҁFFAX�ԍ�                
                + ",A.JIMUTANTO_EMAIL" // �����S���ҁFEmail 
                + ",A.KANREN_SHIMEI1" // �֘A�������쌤����1�F����          
                + ",A.KANREN_KIKAN1" // �֘A�������쌤����1�F�����@�֖�    
                + ",A.KANREN_BUKYOKU1" // �֘A�������쌤����1�F����          
                + ",A.KANREN_SHOKU1" // �֘A�������쌤����1�F�E            
                + ",A.KANREN_SENMON1" // �֘A�������쌤����1�F���݂̐��    
                + ",A.KANREN_TEL1" // �֘A�������쌤����1�F�Ζ���d�b�ԍ�
                + ",A.KANREN_JITAKUTEL1" // �֘A�������쌤����1�F����d�b�ԍ�  
                + ",A.KANREN_SHIMEI2" // �֘A�������쌤����2�F����          
                + ",A.KANREN_KIKAN2" // �֘A�������쌤����2�F�����@�֖�    
                + ",A.KANREN_BUKYOKU2" // �֘A�������쌤����2�F����          
                + ",A.KANREN_SHOKU2" // �֘A�������쌤����2�F�E            
                + ",A.KANREN_SENMON2" // �֘A�������쌤����2�F���݂̐��    
                + ",A.KANREN_TEL2" // �֘A�������쌤����2�F�Ζ���d�b�ԍ�
                + ",A.KANREN_JITAKUTEL2" // �֘A�������쌤����2�F����d�b�ԍ�  
                + ",A.KANREN_SHIMEI3" // �֘A�������쌤����3�F����          
                + ",A.KANREN_KIKAN3" // �֘A�������쌤����3�F�����@�֖�    
                + ",A.KANREN_BUKYOKU3" // �֘A�������쌤����3�F����          
                + ",A.KANREN_SHOKU3" // �֘A�������쌤����3�F�E            
                + ",A.KANREN_SENMON3" // �֘A�������쌤����3�F���݂̐��    
                + ",A.KANREN_TEL3" // �֘A�������쌤����3�F�Ζ���d�b�ԍ�
                + ",A.KANREN_JITAKUTEL3" // �֘A�������쌤����3�F����d�b�ԍ� 
                
//            	UPDATE START 2007/07/05 BIS ����
                
                
                //H19���S�d�q���y�ѐ��x����
                //����V�K�i�̈�v�揑�T�v�j������p���`�f�[�^�̉�����z�̍��v�Ɍv�Z���@ 
                
                
//                + " FROM RYOIKIKEIKAKUSHOINFO A"
//                + " WHERE DEL_FLG = 0"
//                + "   AND SUBSTR(A.JIGYO_ID,3,5) ='00022' "             
//                //2006.10.30 iso �u�󗝍ς݁v�̂݁i�̈�v�揑�ŁA8�ȍ~�̏�Ԃ͂Ȃ��j
////                + "   AND TO_NUMBER(RYOIKI_JOKYO_ID) IN (6,8,9,10,11,12,21,22,23,24)"
//                + "   AND TO_NUMBER(RYOIKI_JOKYO_ID) IN (6)"
//
//                
//                
//                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.KARIRYOIKI_NO,  KARIRYOIKI_NO"; //����ID���A�@��-���̈�ԍ�-���ڔԍ�-�����ԍ���
 
              + " FROM RYOIKIKEIKAKUSHOINFO A"
              + ",(Select SUM(B.KEIHI1) KEIHI1 , SUM(B.KEIHI2) KEIHI2,SUM(B.KEIHI3) KEIHI3,SUM(B.KEIHI4) KEIHI4,SUM(B.KEIHI5) KEIHI5,SUM(B.KEIHI6) KEIHI6,B.RYOIKI_NO From SHINSEIDATAKANRI B  Where JOKYO_ID='06' AND B.DEL_FLG = 0 AND SUBSTR(JIGYO_ID,3,5) ='00022' Group By B.RYOIKI_NO) C"
              + " WHERE A.DEL_FLG = 0"
              + "   AND SUBSTR(A.JIGYO_ID,3,5) ='00022' "             
                
                
              //2006.10.30 iso �u�󗝍ς݁v�̂݁i�̈�v�揑�ŁA8�ȍ~�̏�Ԃ͂Ȃ��j
//            + "   AND TO_NUMBER(RYOIKI_JOKYO_ID) IN (6,8,9,10,11,12,21,22,23,24)"
            + "   AND TO_NUMBER(A.RYOIKI_JOKYO_ID) IN (6)"
    		+" AND A.KARIRYOIKI_NO=C.RYOIKI_NO"
            
            
            + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.KARIRYOIKI_NO"; //����ID���A�@��-���̈�ԍ�-���ڔԍ�-�����ԍ���

//            	UPDATE END 2007/07/05 BIS ����
        //for debug
        if(log.isDebugEnabled()){
            log.debug("query:" + query);
        }
        //-----------------------
        // ���X�g�擾
        //-----------------------
        try
        {
            list = SelectUtil.selectCsvList(connection, query,true);
        }
        catch (DataAccessException e)
        {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                                           new ErrorInfo("errors.4004"), e);
        }
        catch (NoDataFoundException e)
        {
            throw new NoDataFoundException("�f�[�^�x�[�X��1�����f�[�^������܂���B", e);
        }

        String[] columnArray = {
        		"������ڔԍ�"
        		,"�����ԍ�"
        		,"�R����]����i�n���j"
        		,"���̈�ԍ�"
        		,"����̈於�i�a���j"
        		,"����̈於�i�p���j"
        		,"����̈於�i���́j"
        		,"�̈��\�ҁu�������v�i���j"
        		,"�̈��\�ҁu�������v�i���j"
        		,"�̈��\�ҁu�t���K�i�v�i���j"
        		,"�̈��\�ҁu�t���K�i�v�i���j"
        		,"���������@�փR�[�h"
        		,"���ǃR�[�h"
        		,"���ǖ�"
        		,"�E���R�[�h"
        		,"�E��"
        		,"����̈�̌����T�v"
        		,"���������E���O�����̏󋵃R�[�h"
        		,"���������E���O�����̏󋵖���"
        		,"�O�N�x�̉���Y���t���O"
        		,"�O�N�x�̉���̈�R�[�h"
        		,"�֘A����̍זڔԍ�1"
        		,"�֘A����̍זڔԍ�2"
        		,"�֘A����15���ރR�[�h"
        		,"�����̕K�v��1"
        		,"�����̕K�v��2"
        		,"�����̕K�v��3"
        		,"�����̕K�v��4"
        		,"�����̕K�v��5"
        		,"�o��F���匤���̏��v1�N��"
        		,"�o��F���匤���̓���1�N��"
        		,"�o��F���匤���̏��v2�N��"
        		,"�o��F���匤���̓���2�N��"
        		,"�o��F���匤���̏��v3�N��"
        		,"�o��F���匤���̓���3�N��"
        		,"�o��F���匤���̏��v4�N��"
        		,"�o��F���匤���̓���4�N��"
        		,"�o��F���匤���̏��v5�N��"
        		,"�o��F���匤���̓���5�N��"
        		,"�o��F���匤���̏��v6�N��"
        		,"�o��F���匤���̓���6�N��"
        		
        		
        		//ADD START 2007/07/04 BIS ����
                
                ,"�o��F������z�̍��v1�N��"     
                      
                ,"�o��F������z�̍��v2�N��"          
                      
                ,"�o��F������z�̍��v3�N��"         
                         
                ,"�o��F������z�̍��v4�N��"          
                        
                ,"�o��F������z�̍��v5�N��"          
                         
                ,"�o��F������z�̍��v6�N��"          
                
                
                
                
                //ADD END 2007/07/04 BIS ����
        		
        		
        		,"�̈��\�ҁF�X�֔ԍ�"
        		,"�̈��\�ҁF�Z��"
        		,"�̈��\�ҁF�d�b�ԍ�"
        		,"�̈��\�ҁFFAX�ԍ�"
        		,"�̈��\�ҁFEmail"
        		,"�����S���ҁu�������v�i���j"
        		,"�����S���ҁu�������v�i���j"
        		,"�����S���ҁu�t���K�i�v�i���j"
        		,"�����S���ҁu�t���K�i�v�i���j"
        		,"�����S���ҁF���������@�փR�[�h"
        		,"�����S���ҁF���ǃR�[�h"
        		,"�����S���ҁF���ǖ�"
        		,"�����S���ҁF�E���R�[�h"
        		,"�����S���ҁF�E��"
        		,"�����S���ҁF�X�֔ԍ�"
        		,"�����S���ҁF�Z��"
        		,"�����S���ҁF�d�b�ԍ�"
        		,"�����S���ҁFFAX�ԍ�"
        		,"�����S���ҁFEmail"
        		,"�֘A�������쌤����1�F����"
        		,"�֘A�������쌤����1�F�����@�֖�"
        		,"�֘A�������쌤����1�F����"
        		,"�֘A�������쌤����1�F�E"
        		,"�֘A�������쌤����1�F���݂̐��"
        		,"�֘A�������쌤����1�F�Ζ���d�b�ԍ�"
        		,"�֘A�������쌤����1�F����d�b�ԍ�"
        		,"�֘A�������쌤����2�F����"
        		,"�֘A�������쌤����2�F�����@�֖�"
        		,"�֘A�������쌤����2�F����"
        		,"�֘A�������쌤����2�F�E"
        		,"�֘A�������쌤����2�F���݂̐��"
        		,"�֘A�������쌤����2�F�Ζ���d�b�ԍ�"
        		,"�֘A�������쌤����2�F����d�b�ԍ�"
        		,"�֘A�������쌤����3�F����"
        		,"�֘A�������쌤����3�F�����@�֖�"
        		,"�֘A�������쌤����3�F����"
        		,"�֘A�������쌤����3�F�E"
        		,"�֘A�������쌤����3�F���݂̐��"
        		,"�֘A�������쌤����3�F�Ζ���d�b�ԍ�"
        		,"�֘A�������쌤����3�F����d�b�ԍ�"
            };

		list.set(0, Arrays.asList(columnArray));

        StringBuffer finalResult = new StringBuffer();
		//CSV�f�[�^�𐶐�����
		for(int i = 0;i < list.size(); i++){
			List line = (List)list.get(i);
			Iterator iterator = line.iterator();

			//1�s����CSV�f�[�^���쐬����
			CSVLine csvline = new CSVLine();

			//1�s���̃f�[�^���ڐ����肩����			
			while(iterator.hasNext()){
				String col = (String)iterator.next();
				csvline.addItem(col);
			}

			finalResult.append(csvline.getLine().toString()); //�֘A�������쌤����3�F����d�b�ԍ� 
            finalResult.append(LINE_SHIFT).toString();		
		}
		return finalResult.toString();

    }
       
//2006/04/11�@�ǉ���������
    /**
     * ��茤���i�X�^�[�g�A�b�v�j������p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataWakastartKenkyu(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

    	log.info("�p���`�f�[�^�����J�n�B");
    	
        String query = "SELECT "
        		
                + " SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU"	//������ڔԍ��i2�o�C�g�j
                + ",NVL(A.SHINSA_KUBUN,'1') SHINSA_KUBUN" 	//�R���敪(1�o�C�g)
                + ",NVL(A.SHINSEI_KUBUN,'1') SHINSEI_KUBUN" //�V�K�p���敪(1�o�C�g)
                + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 		//�����@�փR�[�h(5�o�C�g)
                + ",A.BUNKASAIMOKU_CD" 						//�זڃR�[�h�i4�o�C�g�j
                + ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
                + "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"	//�����ԍ��i1�o�C�g�j
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" 					//�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
                + ",CASE WHEN A.SHINSARYOIKI_CD IS NULL THEN '  ' "
                + "      ELSE LPAD(TRIM(A.SHINSARYOIKI_CD),2,'0') END SHINSARYOIKI_CD" //����i2�o�C�g�j
                + ",LPAD(A.EDITION,2,'0') EDITION"		//�Ő�
                + ",LPAD(A.KEIHI1,7,'0') KEIHI1" //1�N�ڌ����o��(7�o�C�g)
                + ",LPAD(A.KEIHI2,7,'0') KEIHI2" //2�N�ڌ����o��(7�o�C�g)
                + ",NVL(A.BUNTANKIN_FLG,' ') BUNTANKINFLG" //���S���̗L��
                + ",RPAD(A.KADAI_NAME_KANJI,80,'�@') KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
                + ",LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') KENKYUNINZU" //�����Ґ��i2�o�C�g�j
                + ",NVL(A.KAIJIKIBO_FLG_NO,' ') KAIJIKIBO_FLG" //�J����]�̗L���i1�o�C�g�j
                + ",NVL(TO_CHAR(A.SHINSEI_FLG_NO),' ') SHINSEI_FLG_NO" //�����v��ŏI�N�x�O�N�x�̉���i1�o�C�g�j
//                + ",CASE WHEN A.KEYWORD_CD IS NULL THEN ' ' ELSE ' ' END KEYWORD_CD" //�זڕ\�L�[���[�h�i1�o�C�g�j
                + ",' ' KEYWORD_CD" //�זڕ\�L�[���[�h�i1�o�C�g�j
                + ",CASE WHEN A.OTHER_KEYWORD IS NULL THEN '�@' ELSE TO_MULTI_BYTE(A.OTHER_KEYWORD) END OTHER_KEYWORD" //�זڕ\�ȊO�̃L�[���[�h�i50�o�C�g�j
                + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//��\�ҕ��S�ҕ�(1�o�C�g)
				+ ",RPAD(B.NAME_KANA_SEI,32,'�@') NAMEKANASEI"	//�����i�t���K�i�[���j(32�o�C�g)
				+ ",RPAD(NVL(B.NAME_KANA_MEI, '�@'),32,'�@') NAMEKANAMEI"	//�����i�t���K�i�[���j(32�o�C�g)
				+ ",RPAD(B.NAME_KANJI_SEI,32,'�@') NAMEKANJISEI"	//�����i�������[���j(32�o�C�g)
				+ ",RPAD(NVL(B.NAME_KANJI_MEI, '�@'),32,'�@') NAMEKANJIMEI"	//�����i�������[���j(32�o�C�g)
                + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����(5�o�C�g)
                + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//���ǖ�(�R�[�h)(3�o�C�g)
                + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD" 	//�E���R�[�h(2�o�C�g)
                + ",LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO" 	//�����Ҕԍ�(8�o�C�g)
                + ",LPAD(NVL(B.KEIHI,0), 7,'0') KEIHI" 		//�����o��(7�o�C�g)
                + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//�G�t�H�[�g(3�o�C�g)
                + ",LPAD(NVL(TO_CHAR(A.SAIYO_DATE,'YYYYMMDD'),' '),8,' ') SAIYO_DATE" //���������@�֍̗p�N����(8�o�C�g)
                + ",LPAD(A.KINMU_HOUR, 2,'0') KINMU_HOUR" 	//�P�T�Ԃ�����̋Ζ����Ԑ�(2�o�C�g)
                + ",LPAD(A.NAIYAKUGAKU, 4,'0') NAIYAKUGAKU" //���ʌ�������������z(4�o�C�g)
                //2007/02/08 �c�@�ǉ���������
                + ",NVL(A.SHOREIHI_NO_NENDO, '  ') SHOREIHI_NO_NENDO" //���ʌ����������ۑ�ԍ�-�N�x(2�o�C�g)
                + ",NVL(A.SHOREIHI_NO_SEIRI, '     ') SHOREIHI_NO_SEIRI" //���ʌ����������ۑ�ԍ�-�����ԍ�(5�o�C�g)
                //2007/02/08�@�c�@�ǉ������܂�

                + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
                + " WHERE DEL_FLG = 0" 
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO"
                + "   AND JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_WAKATESTART
                + "   AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.BUNKASAIMOKU_CD, A.BUNKATSU_NO, SEIRINUMBER"; //����ID,�@�ց[�זڔԍ��[�����ԍ��[�����ԍ�

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = null; //new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            list = SelectUtil.select(connection, query);
        } catch (DataAccessException e) {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�\���f�[�^�e�[�u����1�����f�[�^������܂���B", e);
        }

        //�ŏI�I��return����String��錾����
        //String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();
//2006/04/21 �ǉ���������        
        final char charToBeAdded = '�@'; //�S�p�X�y�[�X
        final int otherKeywordLength = 25; //�זڕ\�ӊO�̃L�[���[�h�̕\��������
        final char HOSHI = '��';

        //���p�S�p�R���o�[�^
        HanZenConverter hanZenConverter = new HanZenConverter();
//�c�@�ǉ������܂�
        for (int i = 0; i < list.size(); i++) {
        	
			if(i % 1000 == 0) {
				log.info("�p���`�f�[�^������::" + i + "��");
			}
        	
        	
            //DB����擾����List�^list��String�^�ɂ��āA����String��List�����
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //������ڔԍ�
        	String shinsaKbn = "1";//�R���敪
        	String shinseiKbn = "1";//�V�K�p���敪
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");//�����@�փR�[�h
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKU_CD");//�זڃR�[�h
            String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");//�����ԍ�
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");//�����ԍ�
            String kaigaiBunya = (String) recordMap.get("SHINSARYOIKI_CD");//����
            String edition = (String)recordMap.get("EDITION");//�Ő�
        	String keihi1 = (String) recordMap.get("KEIHI1");//1�N�ڌ����o��
            String keihi2 = (String) recordMap.get("KEIHI2");//2�N�ڌ����o��
            String buntanKinFlg = "2";//���S���̗L��
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");//�����ۑ薼�i�a���j
            String kenkyuNinzu = "01";//�����Ґ�
        	String kaijikibo = (String) recordMap.get("KAIJIKIBO_FLG");//�J����]�̗L��
            String shinseiFlg = "2";//�����v��ŏI�N�x�O�N�x�̉���
            String keywordCd = (String) recordMap.get("KEYWORD_CD");;//�זڕ\�L�[���[�h
            String otherKeyword = (String) recordMap.get("OTHER_KEYWORD");;//�זڕ\�ȊO�̃L�[���[�h
            String buntanFlg = "1";//��\�ҕ��S�ҕ�
			String nameKanaSei = (String)recordMap.get("NAMEKANASEI");//�����i�t���K�i�[���j
			String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");//�����i�t���K�i�[���j
			String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");//�����i�������[���j
			String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");//�����i�������[���j
            String syozokuCd = (String) recordMap.get("SHOZOKUCD");//�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����
            String bukyokuCd = (String) recordMap.get("BUKYOKUCD");//���ǖ�(�R�[�h)
            String syokusyuCd = (String) recordMap.get("SHOKUSHUCD");//�E���R�[�h
            String kenkyuNo = (String) recordMap.get("KENKYUNO");//�����Ҕԍ�
        	String keihi = (String) recordMap.get("KEIHI");//�����o��
            String effort = (String) recordMap.get("EFFORT");//�G�t�H�[�g
            String saiyoDate = (String) recordMap.get("SAIYO_DATE");//���������@�֍̗p�N����
            String kinmuHour = (String)recordMap.get("KINMU_HOUR");//�P�T�Ԃ�����̋Ζ����Ԑ�
            String naigakugaiku = (String) recordMap.get("NAIYAKUGAKU");//���ʌ�������������z
//2007/02/08 �c�@�ǉ���������
            String shoreihiNoNendo = (String) recordMap.get("SHOREIHI_NO_NENDO");//���ʌ����������ۑ�ԍ�-�N�x
            String shoreihiNoSeiri = (String) recordMap.get("SHOREIHI_NO_SEIRI");//���ʌ����������ۑ�ԍ�-�����ԍ�
//2007/02/08�@�c�@�ǉ������܂�            
            
            
            //DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
            recordMap = null;
            list.set(i, null);
            
//2006/04/21 �ǉ���������            
            String ZenkakuOtherKeyword = hanZenConverter.convert(otherKeyword);
            char[] cArray = ZenkakuOtherKeyword.toCharArray();
            for (int j = 0; j < cArray.length; j++) {
                if (StringUtil.isHankaku(cArray[j])) {
                    cArray[j] = HOSHI; //���p�̏ꍇ�͒u������
                  }

              }
              //������ɖ߂�
              String checkedOtherKeyword = new String(cArray);
              if (checkedOtherKeyword.length() > otherKeywordLength){
                checkedOtherKeyword = checkedOtherKeyword.substring(0, otherKeywordLength);
              }

              //�זڈȊO�̃L�[���[�h��25�����̕����̌Œ蒷�ɂ���
              while (checkedOtherKeyword.length() < otherKeywordLength) {
                checkedOtherKeyword += charToBeAdded;
              }
//�c�@�����܂�
            
			finalResult.append(jigyoId)
            		   .append(shinsaKbn)
            		   .append(shinseiKbn)
            		   .append(syozokuCdDaihyo)
           			   .append(bunkaSaimokuCd)
            		   .append(bunkatsNoAB)
            		   .append(seiriNumber)
            		   .append(kaigaiBunya)
            		   .append(edition)
            		   .append(keihi1)
            		   .append(keihi2)
            		   .append(buntanKinFlg)
            		   .append(kadaiNameKanji)
            		   .append(kenkyuNinzu)
            		   .append(kaijikibo)
            		   .append(shinseiFlg)
            		   .append(keywordCd)
            		   .append(checkedOtherKeyword)
            		   .append(buntanFlg)
					   .append(nameKanaSei)
					   .append(nameKanaMei)
					   .append(nameKanjiSei)
					   .append(nameKanjiMei)
            		   .append(syozokuCd)
            		   .append(bukyokuCd)
            		   .append(syokusyuCd)
            		   .append(kenkyuNo)
            		   .append(keihi)
            		   .append(effort)
            		   .append(saiyoDate)
            		   .append(kinmuHour)
            		   .append(naigakugaiku)
                       //2007/02/08 �c�@�ǉ���������
                       .append(shoreihiNoNendo)
                       .append(shoreihiNoSeiri)
                       //2007/02/08�@�c�@�ǉ������܂�
            		   .append(LINE_SHIFT).toString();

            //finalList.add(record);
            //finalResult = finalResult + (String) finalList.get(i);
        }
        log.info("�p���`�f�[�^�����I���B");
        return finalResult.toString();
    }
    
    /**
     * ���ʌ������i�����p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataSokushinKenkyu(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

    	log.info("�p���`�f�[�^�����J�n�B");
    	
        String query = "SELECT "
        		
                + " SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU"	//������ڔԍ��i2�o�C�g�j
                + ",NVL(A.SHINSA_KUBUN,' ') SHINSA_KUBUN" 	//�R���敪(1�o�C�g)
                + ",NVL(A.SHINSEI_KUBUN,'1') SHINSEI_KUBUN" //�V�K�p���敪(1�o�C�g)
                + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 		//�����@�փR�[�h(5�o�C�g)
                + ",A.BUNKASAIMOKU_CD" 						//�זڃR�[�h�i4�o�C�g�j
                + ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
                + "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"	//�����ԍ��i1�o�C�g�j
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���  
                /*
                + ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                + "      WHEN A.BUNKATSU_NO = '2' THEN '2' ELSE ' ' END BUNKATSUNO_12"	//�����ԍ��i1�o�C�g�j
                */
                
                + ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                + " 	 WHEN A.BUNKATSU_NO = '2' THEN '2' "
                + "		 WHEN A.BUNKATSU_NO = '3' THEN '3' "
                + "		 WHEN A.BUNKATSU_NO = '4' THEN '4' "
                + "      WHEN A.BUNKATSU_NO = '5' THEN '5' ELSE ' ' END BUNKATSUNO_12"	//�����ԍ��i1�o�C�g�j                
//UPDATE�@END�@ 2007/07/25 BIS �����_                
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" 					//�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
                //2007/02/14 �c�@�ǉ���������
                + ",NVL(A.SHINSARYOIKI_CD,'  ') SHINSARYOIKI_CD"                        //�R����]����R�[�h�i2�o�C�g�j
                //2007/02/14�@�c�@�ǉ������܂�
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���                  
                /*
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KENKYU_TAISHO,' ') END KENKYU_TAISHO" //�����Ώۂ̗ތ^�i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE NVL(A.KAIGAIBUNYA_CD,'  ') END KAIGAIBUNYA_CD" //�C�O����i2�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//�Ő�
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE '0000000' END KEIHI5" //5�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN A.OTHER_KEYWORD IS NULL THEN '        ' ELSE '        '  END KADAI_NO_KEIZOKU" //�p�����̌����ۑ�ԍ��i8�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //���S���̗L��
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
                */
                
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.KENKYU_TAISHO,' ') END KENKYU_TAISHO" //�����Ώۂ̗ތ^�i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE NVL(A.KAIGAIBUNYA_CD,'  ') END KAIGAIBUNYA_CD" //�C�O����i2�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//�Ő�
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE '0000000' END KEIHI5" //5�N�ڌ����o��(7�o�C�g)
                + ",CASE WHEN A.OTHER_KEYWORD IS NULL THEN '        ' ELSE '        '  END KADAI_NO_KEIZOKU" //�p�����̌����ۑ�ԍ��i8�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //���S���̗L��
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
               
// UPDATE�@END�@ 2007/07/25 BIS �����_                 
//2006/05/15 �ǉ���������                
//                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END KENKYUNINZU" //�����Ґ��i2�o�C�g�j
                + " ,CASE WHEN A.KENKYU_NINZU IS NULL THEN "
                + "     (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00155' THEN LPAD('1',2,'0') ELSE "
                + "         (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00156' THEN  LPAD('1',2,'0') ELSE '  ' END )" 
                + "     END ) "
                + " ELSE "
                + "     (CASE WHEN  B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END )"
                + " END KENKYUNINZU" //�����Ґ��i2�o�C�g�j
//�c�@�ǉ������܂�
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���   
                /*
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBO_FLG" //�J����]�̗L���i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE '2' END SHINSEI_FLG_NO" //�����v��ŏI�N�x�O�N�x�̉���i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_SAISYU,'        ') END KADAI_NO_SAISYU" //�ŏI�N�x�ۑ�ԍ��i8�o�C�g�j
                */
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBO_FLG" //�J����]�̗L���i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE '2' END SHINSEI_FLG_NO" //�����v��ŏI�N�x�O�N�x�̉���i1�o�C�g�j
                + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_SAISYU,'        ') END KADAI_NO_SAISYU" //�ŏI�N�x�ۑ�ԍ��i8�o�C�g�j
//UPDATE�@END�@ 2007/07/25 BIS �����_                
                + ",NVL(A.KEYWORD_CD,' ') KEYWORD_CD" 		//�זڕ\�L�[���[�h�i1�o�C�g�j
                + ",CASE WHEN A.OTHER_KEYWORD IS NULL THEN '�@' ELSE TO_MULTI_BYTE(A.OTHER_KEYWORD) END OTHER_KEYWORD" //�זڕ\�ȊO�̃L�[���[�h�i50�o�C�g�j
                + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//��\�ҕ��S�ҕ�(1�o�C�g)
				+ ",RPAD(B.NAME_KANA_SEI,32,'�@') NAMEKANASEI"	//�����i�t���K�i�[���j(32�o�C�g)
				+ ",RPAD(NVL(B.NAME_KANA_MEI, '�@'),32,'�@') NAMEKANAMEI"	//�����i�t���K�i�[���j(32�o�C�g)
				+ ",RPAD(B.NAME_KANJI_SEI,32,'�@') NAMEKANJISEI"	//�����i�������[���j(32�o�C�g)
				+ ",RPAD(NVL(B.NAME_KANJI_MEI, '�@'),32,'�@') NAMEKANJIMEI"	//�����i�������[���j(32�o�C�g)				
                + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����(5�o�C�g)
                + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//���ǖ�(�R�[�h)(3�o�C�g)
                + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD" 	//�E���R�[�h(2�o�C�g)
                + ",LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO" 	//�����Ҕԍ�(8�o�C�g)
                + ",LPAD(NVL(B.KEIHI,0), 7,'0') KEIHI" 		//�����o��(7�o�C�g)
                + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//�G�t�H�[�g(3�o�C�g)
                + ",NVL(A.OUBO_SHIKAKU,' ') OUBO_SHIKAKU"   //���厑�i                
                + ",CASE WHEN A.OUBO_SHIKAKU = '1' THEN LPAD(NVL(TO_CHAR(A.SIKAKU_DATE,'YYYYMMDD'),' '),8,' ') ELSE '        ' END SIKAKU_DATE_SIN" //�V���ɉȌ���̉��厑�i�𓾂��N����(8�o�C�g)
                + ",CASE WHEN A.OUBO_SHIKAKU = '2' THEN LPAD(NVL(TO_CHAR(A.SIKAKU_DATE,'YYYYMMDD'),' '),8,' ') ELSE '        ' END SIKAKU_DATE_SAYI" //�ĂщȌ���̉��厑�i�𓾂��N����(8�o�C�g)
//2006/04/15 �ǉ���������     
                + ",CASE WHEN A.OUBO_SHIKAKU = '2' THEN TO_MULTI_BYTE(NVL(A.SYUTOKUMAE_KIKAN,' ')) ELSE TO_MULTI_BYTE(' ') END SYUTOKUMAE_KIKAN" //���厑�i�𓾂�O�̏��������@�֖�(8�o�C�g)
//�c�@�����܂�                
                + ",CASE WHEN A.OUBO_SHIKAKU = '3' THEN LPAD(NVL(TO_CHAR(A.IKUKYU_START_DATE,'YYYYMMDD'),' '),8,' ') ELSE '        ' END IKUKYU_START_DATE" //��x���̎擾���ԁi�J�n�N�����j(8�o�C�g)
                + ",CASE WHEN A.OUBO_SHIKAKU = '3' THEN LPAD(NVL(TO_CHAR(A.IKUKYU_END_DATE,'YYYYMMDD'),' '),8,' ') ELSE '        ' END IKUKYU_END_DATE" //��x���̎擾���ԁi�I���N�����j(8�o�C�g)
                
                + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
                + " WHERE DEL_FLG = 0" 
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO"
                + "   AND JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI
                + "   AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.BUNKASAIMOKU_CD, A.BUNKATSU_NO, SEIRINUMBER, BUNTANFLG, B.SEQ_NO"; //����ID,�@�ց[�זڔԍ��[�����ԍ��[�����ԍ��A��\�ҕ��S�҃t���O���A�V�[�P���X�ԍ���

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = null; //new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            list = SelectUtil.select(connection, query);
        } catch (DataAccessException e) {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�\���f�[�^�e�[�u����1�����f�[�^������܂���B", e);
        }

        //�ŏI�I��return����String��錾����
        //String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();

//2006/04/15 �ǉ���������    
		final char charToBeAdded = '�@'; //�S�p�X�y�[�X
		final int syutokumaeKikanLength = 40; //���厑�i�𓾂�O�̏��������@�֖��̕\��������
        final int otherKeywordLength = 25; //�זڕ\�ӊO�̃L�[���[�h�̕\��������
		final char HOSHI = '��';

		//���p�S�p�R���o�[�^
		HanZenConverter hanZenConverter = new HanZenConverter();
//�c�@�����܂�  
		
        for (int i = 0; i < list.size(); i++) {
        	
			if(i % 1000 == 0) {
				log.info("�p���`�f�[�^������::" + i + "��");
			}
        	
        	
            //DB����擾����List�^list��String�^�ɂ��āA����String��List�����
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //������ڔԍ�
        	String shinsaKbn = (String) recordMap.get("SHINSA_KUBUN");//�R���敪
        	String shinseiKbn = "1";//�V�K�p���敪
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");//�����@�փR�[�h
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKU_CD");//�זڃR�[�h
            String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");//�����ԍ�
            String bunkatsNo12 = (String) recordMap.get("BUNKATSUNO_12");//�����ԍ�
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");//�����ԍ�
            //2007/02/14 �c�@�ǉ���������
            String shinsaRyoikiCd = (String) recordMap.get("SHINSARYOIKI_CD");//�R����]����R�[�h
            //2007/02/14�@�c�@�ǉ������܂�
            String kenkyuTaisho = " ";//�����Ώۂ̗ތ^
            String kaigaiBunya = "  ";//�C�O����
            String edition = (String)recordMap.get("EDITION");//�Ő�
        	String keihi1 = (String) recordMap.get("KEIHI1");//1�N�ڌ����o��
            String keihi2 = (String) recordMap.get("KEIHI2");//2�N�ڌ����o��
            String keihi3 = (String) recordMap.get("KEIHI3");//3�N�ڌ����o��
            String keihi4 = (String) recordMap.get("KEIHI4");//4�N�ڌ����o��
            String keihi5 = (String) recordMap.get("KEIHI5");;//5�N�ڌ����o��
            String kadaiNoKeizoku = (String) recordMap.get("KADAI_NO_KEIZOKU");//�p�����̌����ۑ�ԍ�
            String buntanKinFlg = (String) recordMap.get("BUNTANKINFLG");//���S���̗L��
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");//�����ۑ薼�i�a���j
            String kenkyuNinzu = (String) recordMap.get("KENKYUNINZU");//�����Ґ�
        	String kaijikibo = (String) recordMap.get("KAIJIKIBO_FLG");//�J����]�̗L��
            String shinseiFlg = (String) recordMap.get("SHINSEI_FLG_NO");//�����v��ŏI�N�x�O�N�x�̉���
            String kadaiNoSaisyu = (String) recordMap.get("KADAI_NO_KEIZOKU");//�ŏI�N�x�ۑ�ԍ�
            String keywordCd = (String) recordMap.get("KEYWORD_CD");//�זڕ\�L�[���[�h
            String otherKeyword = (String) recordMap.get("OTHER_KEYWORD");//�זڕ\�ȊO�̃L�[���[�h
            String buntanFlg = (String) recordMap.get("BUNTANFLG");//��\�ҕ��S�ҕ�
			String nameKanaSei = (String)recordMap.get("NAMEKANASEI");//�����i�t���K�i�[���j
			String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");//�����i�t���K�i�[���j
			String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");//�����i�������[���j
			String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");//�����i�������[���j
            String syozokuCd = (String) recordMap.get("SHOZOKUCD");//�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����
            String bukyokuCd = (String) recordMap.get("BUKYOKUCD");//���ǖ�(�R�[�h)
            String syokusyuCd = (String) recordMap.get("SHOKUSHUCD");//�E���R�[�h
            String kenkyuNo = (String) recordMap.get("KENKYUNO");//�����Ҕԍ�
        	String keihi = (String) recordMap.get("KEIHI");//�����o��
            String effort = (String) recordMap.get("EFFORT");//�G�t�H�[�g
            String ouboShikaka = (String) recordMap.get("OUBO_SHIKAKU");//���厑�i
            String sikakuDateSin = (String) recordMap.get("SIKAKU_DATE_SIN");//�V���ɉȌ���̉��厑�i�𓾂��N����
            String sikakuDateSayi = (String) recordMap.get("SIKAKU_DATE_SAYI");//�ĂщȌ���̉��厑�i�𓾂��N����
//2006/04/15 �ǉ���������              
            String syutokumaeKikan = (String) recordMap.get("SYUTOKUMAE_KIKAN");//���厑�i�𓾂�O�̏��������@�֖�
//�c�@�����܂�              
            String ikukyuStartDate = (String) recordMap.get("IKUKYU_START_DATE");//��x���̎擾���ԁi�J�n�N�����j
            String ikukyuEndDate = (String) recordMap.get("IKUKYU_END_DATE");////��x���̎擾���ԁi�I���N�����j

            
            //DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
            recordMap = null;
            list.set(i, null);

//2006/04/15 �ǉ���������              
			//---���厑�i�𓾂�O�̏��������@�֖��𔼊p����S�p�ɂ���
			//char�̔z��ɕϊ�
            //TODO
			String syutokumaeKikanZen = hanZenConverter.convert(syutokumaeKikan);
			char[] ocArray = syutokumaeKikanZen.toCharArray();
			for (int j = 0; j < ocArray.length; j++) {
				if (StringUtil.isHankaku(ocArray[j])) {
					ocArray[j] = HOSHI; //���p�̏ꍇ�͒u������
				}
			}
            
			//������ɖ߂�
			String checkedSyutokumaeKikanZen = new String(ocArray);
			if (checkedSyutokumaeKikanZen.length() > syutokumaeKikanLength){
				checkedSyutokumaeKikanZen = checkedSyutokumaeKikanZen.substring(0, syutokumaeKikanLength);
			}

			//���厑�i�𓾂�O�̏��������@�֖���40�����̕����̌Œ蒷�ɂ���
			while (checkedSyutokumaeKikanZen.length() < syutokumaeKikanLength) {
				checkedSyutokumaeKikanZen += charToBeAdded;
			}
//�c�@�����܂�              

//2006/04/21 �ǉ���������            
            String ZenkakuOtherKeyword = hanZenConverter.convert(otherKeyword);
            char[] cArray = ZenkakuOtherKeyword.toCharArray();
            for (int j = 0; j < cArray.length; j++) {
                if (StringUtil.isHankaku(cArray[j])) {
                    cArray[j] = HOSHI; //���p�̏ꍇ�͒u������
                  }

              }
              //������ɖ߂�
              String checkedOtherKeyword = new String(cArray);
              if (checkedOtherKeyword.length() > otherKeywordLength){
                checkedOtherKeyword = checkedOtherKeyword.substring(0, otherKeywordLength);
              }

              //�זڈȊO�̃L�[���[�h��25�����̕����̌Œ蒷�ɂ���
              while (checkedOtherKeyword.length() < otherKeywordLength) {
                checkedOtherKeyword += charToBeAdded;
              }
//�c�@�����܂�
			
			finalResult.append(jigyoId)
            		   .append(shinsaKbn)
            		   .append(shinseiKbn)
            		   .append(syozokuCdDaihyo)
           			   .append(bunkaSaimokuCd)
            		   .append(bunkatsNoAB)
            		   .append(bunkatsNo12)
            		   .append(seiriNumber)
                       //2007/02/14 �c�@�ǉ���������
                       .append(shinsaRyoikiCd)//�R����]����R�[�h
                       //2007/02/14�@�c�@�ǉ������܂�
            		   .append(kenkyuTaisho)
            		   .append(kaigaiBunya)
            		   .append(edition)
            		   .append(keihi1)
            		   .append(keihi2)
            		   .append(keihi3)
            		   .append(keihi4)
            		   .append(keihi5)
            		   .append(kadaiNoKeizoku)
            		   .append(buntanKinFlg)
            		   .append(kadaiNameKanji)
            		   .append(kenkyuNinzu)
            		   .append(kaijikibo)
            		   .append(shinseiFlg)
            		   .append(kadaiNoSaisyu)
            		   .append(keywordCd)
            		   .append(checkedOtherKeyword)
            		   .append(buntanFlg)
					   .append(nameKanaSei)
					   .append(nameKanaMei)
					   .append(nameKanjiSei)
					   .append(nameKanjiMei)
            		   .append(syozokuCd)
            		   .append(bukyokuCd)
            		   .append(syokusyuCd)
            		   .append(kenkyuNo)
            		   .append(keihi)
            		   .append(effort)
            		   .append(ouboShikaka)
            		   .append(sikakuDateSin)
            		   .append(sikakuDateSayi)
//2006/04/15 �ǉ���������              		   
            		   .append(checkedSyutokumaeKikanZen)
//�c�@�����܂�              		   
            		   .append(ikukyuStartDate)
            		   .append(ikukyuEndDate)
            		   .append(LINE_SHIFT).toString();

            //finalList.add(record);
            //finalResult = finalResult + (String) finalList.get(i);
        }
        log.info("�p���`�f�[�^�����I���B");
        return finalResult.toString();
    }

//�c�@�ǉ������܂�  
    
//2006/05/25 �ǉ���������
    /**
     * ��茤���i�X�^�[�g�A�b�v�j�]��\�p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataWakateHyotei(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

        String query = "SELECT "
                + "'14' KENKYUSHUMOKU"    //������ڔԍ��i2�o�C�g)  
                + ",'1' SHINSA_KUBUN"   //�R���敪(1�o�C�g)  
                + ",NVL(SUBSTR(B.UKETUKE_NO,1,5),'     ') SHOZOKUCDDAIHYO" //�����@�փR�[�h(5�o�C�g)
                + ",NVL(A.BUNKASAIMOKU_CD,'    ') BUNKASAIMOKUCD"                       //�זڃR�[�h�i4�o�C�g�j
                + ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
                + "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"  //�����ԍ��i1�o�C�g�j
                + ",' ' BUNKATSUNO_12"  //�����ԍ��i1�o�C�g�j
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER"                   //�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
                + ",NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO" //�R�����}��(�R�����ԍ��̉��Q���j�i2�o�C�g�j
                + ",CASE WHEN B.RIGAI = '1' THEN '1' ELSE ' '  END RIGAIKANKEI" //���Q�֌W
                + ",NVL(B.JUYOSEI, ' ') JUYOSEI" //�����ۑ�̊w�p�I�d�v��
                + ",NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU" //�����v��E���@�̑Ó���
                + ",NVL(B.DOKUSOSEI, ' ') DOKUSOSEI" //�Ƒn���y�ъv�V��
                + ",NVL(B.HAKYUKOKA, ' ') HAKYUKOKA" //�g�y����
                + ",NVL(B.SUIKONORYOKU, ' ') SUIKONORYOKU" //���s�\��
                + ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '111') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_HOGA" //�K�ؐ��E�G��
                + ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '043')OR(SUBSTR(A.JIGYO_ID,5,3) = '053') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_KAIGAI" //�K�ؐ��E�G��
                + ",CASE WHEN B.KEKKA_TEN = '-' THEN ' ' ELSE NVL(B.KEKKA_TEN, ' ') END KEKKATEN" //�����]��
                + ",CASE WHEN B.JINKEN = '3' THEN '3' ELSE ' ' END JINKEN" //�l��
                + ",CASE WHEN B.BUNTANKIN = '3' THEN '3' ELSE ' ' END BUNTANKIN" //���S��
                + ",CASE WHEN B.DATO = '0' THEN ' ' ELSE NVL(B.DATO, ' ') END DATO" //�o��̑Ó���
                + ",CASE WHEN B.COMMENTS IS NULL THEN '�@' ELSE TO_MULTI_BYTE(B.COMMENTS) END COMMENTS" //�R���ӌ�
                + ",CASE WHEN B.OTHER_COMMENT IS NULL THEN '�@' ELSE TO_MULTI_BYTE(B.OTHER_COMMENT) END OTHER_COMMENT" //�R�����g
                + ",NVL(B.SHINSA_JOKYO, '0') SHINSAJOKYO" //�R����
                + " FROM SHINSEIDATAKANRI A, SHINSAKEKKA B"
                + " WHERE B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_WAKATESTART
                + "   AND NOT B.SHINSAIN_NO LIKE '@%'"  //�_�~�[�R���������O����(�_�~�[�R�����ԍ������t���Ă�)
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO" 
                + "   AND A.JIGYO_KUBUN = B.JIGYO_KUBUN "
                + "   AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART +  ")"
    //          
                + "   AND A.DEL_FLG = 0"
                + "   AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY KENKYUSHUMOKU, SHINSA_KUBUN, SHOZOKUCDDAIHYO, BUNKASAIMOKUCD, A.BUNKATSU_NO, SHINSAINNO, SEIRINUMBER"; //�����@�փR�[�h�A,�����ԍ��A�R�����}��

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            list = SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�f�[�^�x�[�X��1�����f�[�^������܂���B", e);
        }

        //�ŏI�I��return����String��錾����
        StringBuffer finalResult = new StringBuffer();
        char charToBeAdded = '�@'; //�S�p�X�y�[�X
        final int commentLength = 150; //�R�����g�̕\��������
               int otherCommentLength = 50; //�R�����g�̕\��������
        final char HOSHI = '��';

        //���p�S�p�R���o�[�^
        HanZenConverter hanZenConverter = new HanZenConverter();

        for (int i = 0; i < list.size(); i++) {

            //long start = System.currentTimeMillis();

            //DB����擾����List�^list��String�^�ɂ��āA����String��List�����
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //������ڔԍ�
            String shinsaKbn = (String) recordMap.get("SHINSA_KUBUN");
            String shozokuCd = (String) recordMap.get("SHOZOKUCDDAIHYO");
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKUCD");
            String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");
            String bunkatsNo12 = (String) recordMap.get("BUNKATSUNO_12");
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");
            String shinsainNo = (String) recordMap.get("SHINSAINNO");
            String rigai = (String) recordMap.get("RIGAIKANKEI");
            String juyosei = (String) recordMap.get("JUYOSEI");
            String kenkyuKeikaku = (String) recordMap.get("KENKYUKEIKAKU");
            String dokusosei = (String) recordMap.get("DOKUSOSEI");
            String hakyukoka = (String) recordMap.get("HAKYUKOKA");
            String suikonoryoku = (String) recordMap.get("SUIKONORYOKU");
            String tekisetsuHoga = (String) recordMap.get("TEKISETU_HOGA");
            String tekisetsuKaigai = (String) recordMap.get("TEKISETU_KAIGAI");
            String kekkaTen = (String) recordMap.get("KEKKATEN");            
            String jinken = (String) recordMap.get("JINKEN");
            String buntankin = (String) recordMap.get("BUNTANKIN");
            String dato = (String) recordMap.get("DATO");
            String recordComments = (String) recordMap.get("COMMENTS");
            String recordOtherComment = (String) recordMap.get("OTHER_COMMENT");
            String shinsaJokyo = (String) recordMap.get("SHINSAJOKYO");

            //DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
            recordMap = null;
            list.set(i, null);

            //---�ӌ��𔼊p����S�p�ɂ���
            //char�̔z��ɕϊ�
            String ZenkakuComments = hanZenConverter.convert(recordComments);
            char[] cArray = ZenkakuComments.toCharArray();
            for (int j = 0; j < cArray.length; j++) {
                if (StringUtil.isHankaku(cArray[j])) {
                    cArray[j] = HOSHI; //���p�̏ꍇ�͒u������
                }

            }
            //������ɖ߂�
            String checkedZenkakuComments = new String(cArray);
            if (checkedZenkakuComments.length() > commentLength){
                checkedZenkakuComments = checkedZenkakuComments.substring(0, commentLength);
            }

            //�ӌ���150�����̕����̌Œ蒷�ɂ���
            while (checkedZenkakuComments.length() < commentLength) {
                checkedZenkakuComments += charToBeAdded;
            }
            //�ӌ������܂�


            //---�R�����g�𔼊p����S�p�ɂ���
            //char�̔z��ɕϊ�
            String ZenOtherComment = hanZenConverter.convert(recordOtherComment);
            char[] ocArray = ZenOtherComment.toCharArray();
            for (int j = 0; j < ocArray.length; j++) {
                if (StringUtil.isHankaku(ocArray[j])) {
                    ocArray[j] = HOSHI; //���p�̏ꍇ�͒u������
                }

            }
            //������ɖ߂�
            String checkedZenOtherComment = new String(ocArray);
            if (checkedZenOtherComment.length() > otherCommentLength){
                checkedZenOtherComment = checkedZenOtherComment.substring(0, otherCommentLength);
            }

            //�R�����g��50�����̕����̌Œ蒷�ɂ���
            while (checkedZenOtherComment.length() < otherCommentLength) {
                checkedZenOtherComment += charToBeAdded;
            }

            //�ŏI���ʂɃ��R�[�h�i�{�s�j�𕶎��񌋍�
            finalResult.append(jigyoId)
                       .append(shinsaKbn)
                       .append(shozokuCd)
                       .append(bunkaSaimokuCd)
                       .append(bunkatsNoAB)
                       .append(bunkatsNo12)
                       .append(seiriNumber)
                       .append(shinsainNo)
                       .append(rigai)
                       .append(juyosei)
                       .append(kenkyuKeikaku)
                       .append(dokusosei)
                       .append(hakyukoka)
                       .append(suikonoryoku)
                       .append(tekisetsuHoga)
                       .append(tekisetsuKaigai)
                       .append(kekkaTen)
                       .append(jinken)
                       .append(buntankin)
                       .append(dato)
                       .append(checkedZenkakuComments)
                       .append(checkedZenOtherComment)
                       .append(shinsaJokyo)
                       .append(LINE_SHIFT);

            //long stop = System.currentTimeMillis() - start;
            //System.out.println(i+"�s�̏������ԁB" + stop);
            }
        return finalResult.toString();
    }
    
    /**
     * ��茤���i�X�^�[�g�A�b�v�j�]��\�p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataSokushinHyotei(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {

        String query = "SELECT "
                + "'15' KENKYUSHUMOKU"    //������ڔԍ��i2�o�C�g)  
                + ",CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00152' THEN '2'"
                + "      WHEN SUBSTR(A.JIGYO_ID,3,5) = '00153' THEN '3' " 
                + "      WHEN SUBSTR(A.JIGYO_ID,3,5) = '00154' THEN '4' " 
                + "      WHEN SUBSTR(A.JIGYO_ID,3,5) = '00155' THEN '5' " 
                + "      WHEN SUBSTR(A.JIGYO_ID,3,5) = '00156' THEN '6' " 
                + "      ELSE ' ' END SHINSA_KUBUN"  //�R���敪�i1�o�C�g�j
                + ",NVL(SUBSTR(B.UKETUKE_NO,1,5),'     ') SHOZOKUCDDAIHYO" //�����@�փR�[�h(5�o�C�g)
                + ",NVL(A.BUNKASAIMOKU_CD,'    ') BUNKASAIMOKUCD"                       //�זڃR�[�h�i4�o�C�g�j
                + ",CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
                + "      WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"  //�����ԍ��i1�o�C�g�j
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���                 
                /*
                + ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                + "      WHEN A.BUNKATSU_NO = '2' THEN '2' ELSE ' ' END BUNKATSUNO_12"  //�����ԍ��i1�o�C�g�j
                */
                
                + ",CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
                + "		 WHEN A.BUNKATSU_NO = '2' THEN '2' "
                + "		 WHEN A.BUNKATSU_NO = '3' THEN '3' "
                + "		 WHEN A.BUNKATSU_NO = '4' THEN '4' "
                + "      WHEN A.BUNKATSU_NO = '5' THEN '5' ELSE ' ' END BUNKATSUNO_12"  //�����ԍ��i1�o�C�g�j
                 
                
//UPDATE  END�@ 2007/07/25 BIS �����_                 
                + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER"                   //�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
                + ",NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO" //�R�����}��(�R�����ԍ��̉��Q���j�i2�o�C�g�j
                + ",CASE WHEN B.RIGAI = '1' THEN '1' ELSE ' '  END RIGAIKANKEI" //���Q�֌W
                + ",NVL(B.JUYOSEI, ' ') JUYOSEI" //�����ۑ�̊w�p�I�d�v��
                + ",NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU" //�����v��E���@�̑Ó���
                + ",NVL(B.DOKUSOSEI, ' ') DOKUSOSEI" //�Ƒn���y�ъv�V��
                + ",NVL(B.HAKYUKOKA, ' ') HAKYUKOKA" //�g�y����
                + ",NVL(B.SUIKONORYOKU, ' ') SUIKONORYOKU" //���s�\��
                + ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '111') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_HOGA" //�K�ؐ��E�G��
                + ",CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '043')OR(SUBSTR(A.JIGYO_ID,5,3) = '053') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_KAIGAI" //�K�ؐ��E�G��
                + ",CASE WHEN B.KEKKA_TEN = '-' THEN ' ' ELSE NVL(B.KEKKA_TEN, ' ') END KEKKATEN" //�����]��
                + ",CASE WHEN B.JINKEN = '3' THEN '3' ELSE ' ' END JINKEN" //�l��
                + ",CASE WHEN B.BUNTANKIN = '3' THEN '3' ELSE ' ' END BUNTANKIN" //���S��
                + ",CASE WHEN B.DATO = '0' THEN ' ' ELSE NVL(B.DATO, ' ') END DATO" //�o��̑Ó���
                + ",CASE WHEN B.COMMENTS IS NULL THEN '�@' ELSE TO_MULTI_BYTE(B.COMMENTS) END COMMENTS" //�R���ӌ�
                + ",CASE WHEN B.OTHER_COMMENT IS NULL THEN '�@' ELSE TO_MULTI_BYTE(B.OTHER_COMMENT) END OTHER_COMMENT" //�R�����g
                + ",NVL(B.SHINSA_JOKYO, '0') SHINSAJOKYO" //�R����
                + " FROM SHINSEIDATAKANRI A, SHINSAKEKKA B"
                + " WHERE B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI
                + "   AND NOT B.SHINSAIN_NO LIKE '@%'"  //�_�~�[�R���������O����(�_�~�[�R�����ԍ������t���Ă�)
                + "   AND A.SYSTEM_NO = B.SYSTEM_NO" 
                + "   AND A.JIGYO_KUBUN = B.JIGYO_KUBUN "
                + "   AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI +  ")"
    //          
                + "   AND A.DEL_FLG = 0"
                + "   AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
                + " ORDER BY KENKYUSHUMOKU, SHINSA_KUBUN, SHOZOKUCDDAIHYO, BUNKASAIMOKUCD, A.BUNKATSU_NO, SHINSAINNO, SEIRINUMBER"; //�����@�փR�[�h�A,�����ԍ��A�R�����}��

        //for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = new ArrayList();
        //List finalList = new ArrayList();

        //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            list = SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�f�[�^�x�[�X��1�����f�[�^������܂���B", e);
        }

        //�ŏI�I��return����String��錾����
        StringBuffer finalResult = new StringBuffer();
        char charToBeAdded = '�@'; //�S�p�X�y�[�X
        final int commentLength = 150; //�R�����g�̕\��������
               int otherCommentLength = 50; //�R�����g�̕\��������
        final char HOSHI = '��';

        //���p�S�p�R���o�[�^
        HanZenConverter hanZenConverter = new HanZenConverter();

        for (int i = 0; i < list.size(); i++) {

            //long start = System.currentTimeMillis();

            //DB����擾����List�^list��String�^�ɂ��āA����String��List�����
            Map recordMap = (Map) list.get(i);
            String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //������ڔԍ�
            String shinsaKbn = (String) recordMap.get("SHINSA_KUBUN");
            String shozokuCd = (String) recordMap.get("SHOZOKUCDDAIHYO");
            String bunkaSaimokuCd = (String) recordMap.get("BUNKASAIMOKUCD");
            String bunkatsNoAB = (String) recordMap.get("BUNKATSUNO_AB");
            String bunkatsNo12 = (String) recordMap.get("BUNKATSUNO_12");
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");
            String shinsainNo = (String) recordMap.get("SHINSAINNO");
            String rigai = (String) recordMap.get("RIGAIKANKEI");
            String juyosei = (String) recordMap.get("JUYOSEI");
            String kenkyuKeikaku = (String) recordMap.get("KENKYUKEIKAKU");
            String dokusosei = (String) recordMap.get("DOKUSOSEI");
            String hakyukoka = (String) recordMap.get("HAKYUKOKA");
            String suikonoryoku = (String) recordMap.get("SUIKONORYOKU");
            String tekisetsuHoga = (String) recordMap.get("TEKISETU_HOGA");
            String tekisetsuKaigai = (String) recordMap.get("TEKISETU_KAIGAI");
            String kekkaTen = (String) recordMap.get("KEKKATEN");            
            String jinken = (String) recordMap.get("JINKEN");
            String buntankin = (String) recordMap.get("BUNTANKIN");
            String dato = (String) recordMap.get("DATO");
            String recordComments = (String) recordMap.get("COMMENTS");
            String recordOtherComment = (String) recordMap.get("OTHER_COMMENT");
            String shinsaJokyo = (String) recordMap.get("SHINSAJOKYO");

            //DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
            recordMap = null;
            list.set(i, null);

            //---�ӌ��𔼊p����S�p�ɂ���
            //char�̔z��ɕϊ�
            String ZenkakuComments = hanZenConverter.convert(recordComments);
            char[] cArray = ZenkakuComments.toCharArray();
            for (int j = 0; j < cArray.length; j++) {
                if (StringUtil.isHankaku(cArray[j])) {
                    cArray[j] = HOSHI; //���p�̏ꍇ�͒u������
                }

            }
            //������ɖ߂�
            String checkedZenkakuComments = new String(cArray);
            if (checkedZenkakuComments.length() > commentLength){
                checkedZenkakuComments = checkedZenkakuComments.substring(0, commentLength);
            }

            //�ӌ���150�����̕����̌Œ蒷�ɂ���
            while (checkedZenkakuComments.length() < commentLength) {
                checkedZenkakuComments += charToBeAdded;
            }
            //�ӌ������܂�


            //---�R�����g�𔼊p����S�p�ɂ���
            //char�̔z��ɕϊ�
            String ZenOtherComment = hanZenConverter.convert(recordOtherComment);
            char[] ocArray = ZenOtherComment.toCharArray();
            for (int j = 0; j < ocArray.length; j++) {
                if (StringUtil.isHankaku(ocArray[j])) {
                    ocArray[j] = HOSHI; //���p�̏ꍇ�͒u������
                }

            }
            //������ɖ߂�
            String checkedZenOtherComment = new String(ocArray);
            if (checkedZenOtherComment.length() > otherCommentLength){
                checkedZenOtherComment = checkedZenOtherComment.substring(0, otherCommentLength);
            }

            //�R�����g��50�����̕����̌Œ蒷�ɂ���
            while (checkedZenOtherComment.length() < otherCommentLength) {
                checkedZenOtherComment += charToBeAdded;
            }

            //�ŏI���ʂɃ��R�[�h�i�{�s�j�𕶎��񌋍�
            finalResult.append(jigyoId)
                       .append(shinsaKbn)
                       .append(shozokuCd)
                       .append(bunkaSaimokuCd)
                       .append(bunkatsNoAB)
                       .append(bunkatsNo12)
                       .append(seiriNumber)
                       .append(shinsainNo)
                       .append(rigai)
                       .append(juyosei)
                       .append(kenkyuKeikaku)
                       .append(dokusosei)
                       .append(hakyukoka)
                       .append(suikonoryoku)
                       .append(tekisetsuHoga)
                       .append(tekisetsuKaigai)
                       .append(kekkaTen)
                       .append(jinken)
                       .append(buntankin)
                       .append(dato)
                       .append(checkedZenkakuComments)
                       .append(checkedZenOtherComment)
                       .append(shinsaJokyo)
                       .append(LINE_SHIFT);

            //long stop = System.currentTimeMillis() - start;
            //System.out.println(i+"�s�̏������ԁB" + stop);
            }
        return finalResult.toString();
    }
//�c�@�ǉ������܂�    
    
//2006/06/20 �c�@�ǉ���������
//TODO�@2006/6/20�@�c    
    /**
     * ����̈挤��(�V�K�̈�)�����v�撲���̃p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataTokuteiSinnkiChosho(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {
    	//�C��By�@Sai�@2006.09.15
    	//SQL�����猤����ڔԍ��i2�o�C�g�j,�v�挤���E���匤���̕�,�J����]�̗L���i1�o�C�g�j���폜����
        String query = "SELECT "
            //update liuyi start 2006/06/29
//            + " SUBSTR(A.JIGYO_ID,5,2) KENKYUSHUMOKU"   //������ڔԍ��i2�o�C�g�j
//            + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO"         //�����@�փR�[�h(5�o�C�g)
            + " A.SHOZOKU_CD SHOZOKU_CD_DAIHYO"         //�����@�փR�[�h(5�o�C�g)
        	+ ",A.RYOIKI_NO RYOIKI_DAIHYO"          //���̈�ԍ�(5�o�C�g)
            + ",A.KOMOKU_NO KOMOKU_NO_DAIHYO"           //�������ڔԍ�(3�o�C�g)
            + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER" //�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
//            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE ( CASE WHEN A.KENKYU_KUBUN = '1' THEN '6'"
//            + "  ELSE ' ' END ) END KENKYU_KUBUN"  //�v�挤���E���匤���̕�
//            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE (CASE WHEN A.KENKYU_KUBUN = '1' AND A.KOMOKU_NO ='X00' THEN '1'"
//            + " ELSE(case WHEN A.KENKYU_KUBUN = '1' AND A.KOMOKU_NO ='Y00' THEN '2'"
//            + " ELSE '3' END)END) END KOMOKU_NO_NM"  //�����E�x���E�����ǂ̕�

            + ",CASE WHEN A.KOMOKU_NO = 'X00' AND B.BUNTAN_FLG = '1' THEN '1'"
            + "      WHEN A.KOMOKU_NO = 'Y00' AND B.BUNTAN_FLG = '1' THEN '2'"
            + "      WHEN A.CHOSEIHAN = '1'   AND B.BUNTAN_FLG = '1' THEN '3'"
            + "      ELSE ' ' END KOMOKU_NO_NM" 			////�����E�x���E�����ǂ̕�
            
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���                      
            /*
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"      //�Ő�
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //���S���̗L��
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI1,7,'0') END BIHINHI1" //1�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI1,7,'0') END SHOMOHINHI1" //1�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI1,7,'0') END RYOHI1" //1�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN1,7,'0') END SHAKIN1" //1�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA1,7,'0') END SONOTA1" //1�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI2,7,'0') END BIHINHI2" //2�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI2,7,'0') END SHOMOHINHI2" //2�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI2,7,'0') END RYOHI2" //2�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN2,7,'0') END SHAKIN2" //2�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA2,7,'0') END SONOTA2" //2�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI3,7,'0') END BIHINHI3" //3�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI3,7,'0') END SHOMOHINHI3" //3�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI3,7,'0') END RYOHI3" //3�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN3,7,'0') END SHAKIN3" //3�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA3,7,'0') END SONOTA3" //3�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI4,7,'0') END BIHINHI4" //4�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI4,7,'0') END SHOMOHINHI4" //4�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI4,7,'0') END RYOHI4" //4�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN4,7,'0') END SHAKIN4" //4�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA4,7,'0') END SONOTA4" //4�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI5,7,'0') END BIHINHI5" //5�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI5,7,'0') END SHOMOHINHI5" //5�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI5,7,'0') END RYOHI5" //5�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN5,7,'0') END SHAKIN5" //5�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA5,7,'0') END SONOTA5" //5�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI6,7,'0') END KEIHI6" //6�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI6,7,'0') END BIHINHI6" //6�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI6,7,'0') END SHOMOHINHI6" //6�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI6,7,'0') END RYOHI6" //6�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN6,7,'0') END SHAKIN6" //6�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA6,7,'0') END SONOTA6" //6�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
            + " ,CASE WHEN A.KENKYU_NINZU IS NULL THEN "
            + "     (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00155' THEN LPAD('1',2,'0') ELSE "
            + "         (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00156' THEN  LPAD('1',2,'0') ELSE '  ' END )" 
            + "     END ) "
            + " ELSE "
            + "     (CASE WHEN  B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END )"
            + " END KENKYUNINZU" //�����Ґ��i2�o�C�g�j
            */
            
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"      //�Ő�
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE NVL(A.BUNTANKIN_FLG,' ') END BUNTANKINFLG" //���S���̗L��
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI1,7,'0') END BIHINHI1" //1�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI1,7,'0') END SHOMOHINHI1" //1�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI1,7,'0') END RYOHI1" //1�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN1,7,'0') END SHAKIN1" //1�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA1,7,'0') END SONOTA1" //1�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI2,7,'0') END BIHINHI2" //2�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI2,7,'0') END SHOMOHINHI2" //2�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI2,7,'0') END RYOHI2" //2�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN2,7,'0') END SHAKIN2" //2�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA2,7,'0') END SONOTA2" //2�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI3,7,'0') END BIHINHI3" //3�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI3,7,'0') END SHOMOHINHI3" //3�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI3,7,'0') END RYOHI3" //3�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN3,7,'0') END SHAKIN3" //3�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA3,7,'0') END SONOTA3" //3�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI4,7,'0') END BIHINHI4" //4�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI4,7,'0') END SHOMOHINHI4" //4�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI4,7,'0') END RYOHI4" //4�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN4,7,'0') END SHAKIN4" //4�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA4,7,'0') END SONOTA4" //4�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI5,7,'0') END BIHINHI5" //5�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI5,7,'0') END SHOMOHINHI5" //5�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI5,7,'0') END RYOHI5" //5�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN5,7,'0') END SHAKIN5" //5�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA5,7,'0') END SONOTA5" //5�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI6,7,'0') END KEIHI6" //6�N�ڌ����o��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI6,7,'0') END BIHINHI6" //6�N�ڐݔ����i��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI6,7,'0') END SHOMOHINHI6" //6�N�ڏ��Օi��(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI6,7,'0') END RYOHI6" //6�N�ڗ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN6,7,'0') END SHAKIN6" //6�N�ڎӋ���(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA6,7,'0') END SONOTA6" //6�N�ڂ��̑�(7�o�C�g)
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
            + " ,CASE WHEN A.KENKYU_NINZU IS NULL THEN "
            + "     (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00155' THEN LPAD('1',2,'0') ELSE "
            + "         (CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00156' THEN  LPAD('1',2,'0') ELSE '  ' END )" 
            + "     END ) "
            + " ELSE "
            + "     (CASE WHEN  B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(NVL(A.KENKYU_NINZU,'0'),2,'0') END )"
            + " END KENKYUNINZU" //�����Ґ��i2�o�C�g�j            
//UPDATE  END�@ 2007/07/25 BIS �����_
//            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBO_FLG" //�J����]�̗L���i1�o�C�g�j
            + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG"        //��\�ҕ��S�ҕ�(1�o�C�g)
            
            //2006.11.20 iso �����ƃt���K�i�̏������ւ�
            //�����͓���ւ��Ȃ��Ă����������킹�ďC��
//            + ",RPAD(B.NAME_KANJI_SEI,32,'�@') NAMEKANJISEI" //�����i�������[���j(32�o�C�g)
//            + ",RPAD(NVL(B.NAME_KANJI_MEI, '�@'),32,'�@') NAMEKANJIMEI"   //�����i�������[���j(32�o�C�g)        
            + ",RPAD(B.NAME_KANA_SEI,32,'�@') NAMEKANASEI"   //�����i�t���K�i�[���j(32�o�C�g)
            + ",RPAD(NVL(B.NAME_KANA_MEI, '�@'),32,'�@') NAMEKANAMEI" //�����i�t���K�i�[���j(32�o�C�g)
            + ",RPAD(B.NAME_KANJI_SEI,32,'�@') NAMEKANJISEI" //�����i�������[���j(32�o�C�g)
            + ",RPAD(NVL(B.NAME_KANJI_MEI, '�@'),32,'�@') NAMEKANJIMEI"   //�����i�������[���j(32�o�C�g)    
            
            + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD"     //�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����(5�o�C�g)
            + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD "    //���ǖ�(�R�[�h)(3�o�C�g)
            + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD"   //�E���R�[�h(2�o�C�g)
            + ",LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO"     //�����Ҕԍ�(8�o�C�g)
            + ",LPAD(NVL(B.KEIHI,0), 7,'0') KEIHI"      //�����o��(7�o�C�g)
            + ",LPAD(B.EFFORT, 3,'0') EFFORT"           //�G�t�H�[�g(3�o�C�g)
            + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
            + " WHERE DEL_FLG = 0" 
            + "   AND A.SYSTEM_NO = B.SYSTEM_NO"
            + "   AND SUBSTR(A.JIGYO_ID,3,5) ='00022' " 
            
            //2006.10.30 iso �̈��\�Ҋ֘A�̃X�e�[�^�X21�`24�͏o�͑Ώۂł͂Ȃ��̂ō폜�B
//            + "   AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12,21,22,23,24)"
            + "   AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)"
            
            + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.RYOIKI_NO, A.KOMOKU_NO,SEIRINUMBER,BUNTANFLG,B.SEQ_NO"; //����ID,�@�ց[�זڔԍ��[�����ԍ��[�����ԍ��A��\�ҕ��S�҃t���O���A�V�[�P���X�ԍ���


//      for debug
        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        List list = null; //new ArrayList();
         //-----------------------
        // ���X�g�擾
        //-----------------------
        try {
            list = SelectUtil.select(connection, query);
        } catch (DataAccessException e) {
            throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�\���f�[�^�e�[�u����1�����f�[�^������܂���B", e);
        }

        //�ŏI�I��return����String��錾����
        //String finalResult = new String();
        StringBuffer finalResult = new StringBuffer();

//2006/04/15 �ǉ���������    
//        final char charToBeAdded = '�@'; //�S�p�X�y�[�X                                                                       //TODO
//        final int syutokumaeKikanLength = 40; //���厑�i�𓾂�O�̏��������@�֖��̕\��������
//        final int otherKeywordLength = 25; //�זڕ\�ӊO�̃L�[���[�h�̕\��������
//        final char HOSHI = '��';

        //���p�S�p�R���o�[�^
 //       HanZenConverter hanZenConverter = new HanZenConverter();
//�c�@�����܂�  
        
        for (int i = 0; i < list.size(); i++) {
            
            if(i % 1000 == 0) {
                log.info("�p���`�f�[�^������::" + i + "��");
            }
            
            
            //DB����擾����List�^list��String�^�ɂ��āA����String��List�����
            Map recordMap = (Map) list.get(i);
            //String jigyoId = (String) recordMap.get("KENKYUSHUMOKU"); //������ڔԍ�
            String syozokuCdDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");//�����@�փR�[�h
            String ryoikiDaihyo = (String) recordMap.get("RYOIKI_DAIHYO");//���̈�ԍ�
            String komokuNoDaihyo = (String) recordMap.get("KOMOKU_NO_DAIHYO");//�������ڔԍ�
            String seiriNumber = (String) recordMap.get("SEIRINUMBER");//�����ԍ�
            //String kenkyuKbn = (String) recordMap.get("KENKYU_KUBUN");//�v�挤���E���匤���̕�
            String komokuNoNm =(String) recordMap.get("KOMOKU_NO_NM");//�����E�x���E�����ǂ̕�
            String edition = (String)recordMap.get("EDITION");//�Ő�
            String buntanKinFlg = (String) recordMap.get("BUNTANKINFLG");//���S���̗L��
            String keihi1 = (String) recordMap.get("KEIHI1");//1�N�ڌ����o��
            String bihinhi1 = (String) recordMap.get("BIHINHI1");//1�N�ڐݔ����i��
            String shomohinhi1 = (String) recordMap.get("SHOMOHINHI1");//1�N�ڏ��Օi��
            String ryohi1 = (String) recordMap.get("RYOHI1");//1�N�ڗ���
            String shakin1 = (String) recordMap.get("SHAKIN1");//1�N�ڎӋ���
            String sonota1 = (String) recordMap.get("SONOTA1");//1�N�ڂ��̑�
            String keihi2 = (String) recordMap.get("KEIHI2");//2�N�ڌ����o��
            String bihinhi2 = (String) recordMap.get("BIHINHI2");//2�N�ڐݔ����i��
            String shomohinhi2 = (String) recordMap.get("SHOMOHINHI2");//2�N�ڏ��Օi��
            String ryohi2 = (String) recordMap.get("RYOHI2");//2�N�ڗ���
            String shakin2 = (String) recordMap.get("SHAKIN2");//2�N�ڎӋ���
            String sonota2 = (String) recordMap.get("SONOTA2");//2�N�ڂ��̑�
            String keihi3 = (String) recordMap.get("KEIHI3");//3�N�ڌ����o��
            String bihinhi3 = (String) recordMap.get("BIHINHI3");//3�N�ڐݔ����i��
            String shomohinhi3 = (String) recordMap.get("SHOMOHINHI3");//3�N�ڏ��Օi��
            String ryohi3 = (String) recordMap.get("RYOHI3");//3�N�ڗ���
            String shakin3 = (String) recordMap.get("SHAKIN3");//3�N�ڎӋ���
            String sonota3 = (String) recordMap.get("SONOTA3");//3�N�ڂ��̑�
            String keihi4 = (String) recordMap.get("KEIHI4");//4�N�ڌ����o��
            String bihinhi4 = (String) recordMap.get("BIHINHI4");//1�N�ڐݔ����i��
            String shomohinhi4 = (String) recordMap.get("SHOMOHINHI4");//4�N�ڏ��Օi��
            String ryohi4 = (String) recordMap.get("RYOHI4");//4�N�ڗ���
            String shakin4 = (String) recordMap.get("SHAKIN4");//4�N�ڎӋ���
            String sonota4 = (String) recordMap.get("SONOTA4");//4�N�ڂ��̑�
            String keihi5 = (String) recordMap.get("KEIHI5");;//5�N�ڌ����o��
            String bihinhi5 = (String) recordMap.get("BIHINHI5");//5�N�ڐݔ����i��
            String shomohinhi5 = (String) recordMap.get("SHOMOHINHI5");//5�N�ڏ��Օi��
            String ryohi5 = (String) recordMap.get("RYOHI5");//5�N�ڗ���
            String shakin5 = (String) recordMap.get("SHAKIN5");//5�N�ڎӋ���
            String sonota5 = (String) recordMap.get("SONOTA5");//5�N�ڂ��̑�
            String keihi6 = (String) recordMap.get("KEIHI6");;//6�N�ڌ����o��
            String bihinhi6 = (String) recordMap.get("BIHINHI6");//6�N�ڐݔ����i��
            String shomohinhi6 = (String) recordMap.get("SHOMOHINHI6");//6�N�ڏ��Օi��
            String ryohi6 = (String) recordMap.get("RYOHI6");//6�N�ڗ���
            String shakin6 = (String) recordMap.get("SHAKIN6");//6�N�ڎӋ���
            String sonota6 = (String) recordMap.get("SONOTA6");//6�N�ڂ��̑�
            String kadaiNameKanji = (String) recordMap.get("KADAINAMEKANJI");//�����ۑ薼�i�a���j
            String kenkyuNinzu = (String) recordMap.get("KENKYUNINZU");//�����Ґ�
            //String kaijikibo = (String) recordMap.get("KAIJIKIBO_FLG");//�J����]�̗L��
            String buntanFlg = (String) recordMap.get("BUNTANFLG");//��\�ҕ��S�ҕ�

            //2006.11.20 iso �����ƃt���K�i�̏������ւ�
            //�����͓���ւ��Ȃ��Ă����������킹�ďC��
//            String nameKanaSei = (String)recordMap.get("NAMEKANASEI");//�����i�t���K�i�[���j
//            String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");//�����i�t���K�i�[���j
            String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");//�����i�������[���j
            String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");//�����i�������[���j
            String nameKanaSei = (String)recordMap.get("NAMEKANASEI");//�����i�t���K�i�[���j
            String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");//�����i�t���K�i�[���j
            
            String syozokuCd = (String) recordMap.get("SHOZOKUCD");//�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����
            String bukyokuCd = (String) recordMap.get("BUKYOKUCD");//���ǖ�(�R�[�h)
            String syokusyuCd = (String) recordMap.get("SHOKUSHUCD");//�E���R�[�h
            String kenkyuNo = (String) recordMap.get("KENKYUNO");//�����Ҕԍ�
            String keihi = (String) recordMap.get("KEIHI");//�����o��
            String effort = (String) recordMap.get("EFFORT");//�G�t�H�[�g
            
            

            
            //DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
            recordMap = null;
            list.set(i, null);
            
            
//             finalResult.append(jigyoId)
            finalResult.append(syozokuCdDaihyo) 
                        .append(ryoikiDaihyo) 
                        .append(komokuNoDaihyo) 
                        .append(seiriNumber)
                        //.append(kenkyuKbn) 
                        .append(komokuNoNm) 
                        .append(edition )
                        .append(buntanKinFlg)
                        .append(keihi1 )
                        .append(bihinhi1) 
                        .append(shomohinhi1) 
                        .append(ryohi1) 
                        .append(shakin1) 
                        .append(sonota1) 
                        .append(keihi2) 
                        .append(bihinhi2) 
                        .append(shomohinhi2) 
                        .append(ryohi2) 
                        .append(shakin2)
                        .append(sonota2) 
                        .append(keihi3) 
                        .append(bihinhi3) 
                        .append(shomohinhi3) 
                        .append(ryohi3) 
                        .append(shakin3) 
                        .append(sonota3) 
                        .append(keihi4) 
                        .append(bihinhi4) 
                        .append(shomohinhi4)
                        .append(ryohi4) 
                        .append(shakin4)
                        .append(sonota4) 
                        .append(keihi5) 
                        .append(bihinhi5) 
                        .append(shomohinhi5)
                        .append(ryohi5) 
                        .append(shakin5) 
                        .append(sonota5) 
                        .append(keihi6) 
                        .append(bihinhi6) 
                        .append(shomohinhi6) 
                        .append(ryohi6) 
                        .append(shakin6) 
                        .append(sonota6) 
                        .append(kadaiNameKanji) 
                        .append(kenkyuNinzu) 
                        //.append(kaijikibo) 
                        .append(buntanFlg)
                        
                        //2006.11.20 iso �����ƃt���K�i�̏������ւ�
//                        .append(nameKanjiSei) 
//                        .append(nameKanjiMei) 
                        .append(nameKanaSei) 
                        .append(nameKanaMei) 
                        .append(nameKanjiSei) 
                        .append(nameKanjiMei) 
                        
                        .append(syozokuCd) 
                        .append(bukyokuCd) 
                        .append(syokusyuCd) 
                        .append(kenkyuNo) 
                        .append(keihi) 
                        .append(effort) 
                        .append(LINE_SHIFT).toString();
            //update end liuyi 2006/06/29
            //finalList.add(record);
            //finalResult = finalResult + (String) finalList.get(i);
        }
        log.info("�p���`�f�[�^�����I���B");
        return finalResult.toString();
    }
//2006/06/20�@�c�@�ǉ������܂� 
    
//  TODO�@2006/6/20�@�c    
    /**
     * ����̈挤��(�p��)�����v�撲���p���`�f�[�^���쐬����
     * 
     * @param connection
     * @return �p���`�f�[�^���(String)
     * @throws ApplicationException
     * @throws DataAccessException
     * @throws NoDataFoundException
     */
    public String punchDataTokuteiKeiZokuChosho(Connection connection)
            throws ApplicationException, DataAccessException,
            NoDataFoundException {
        String query = "SELECT " 
			//+ " '2' DATAKBN" //�f�[�^�敪(1�o�C�g)
			+ " NVL(A.SHINSEI_KUBUN,' ') SHINSEI_KUBUN" //�V�K�p���敪(1�o�C�g)
            + ",A.SHOZOKU_CD SHOZOKU_CD_DAIHYO" 		//�����@�փR�[�h(5�o�C�g)
            + ",NVL(A.RYOIKI_NO, '   ') RYOIKI_NO" 		//�̈�ԍ�(3�o�C�g)
            + ",NVL(A.KOMOKU_NO, '   ') KOMOKU_NO" 		//�������ڔԍ�(3�o�C�g)
            + ",NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SHERINO" //�����ԍ�(4�o�C�g)
            + ",CASE WHEN A.KENKYU_KUBUN = '1' AND B.BUNTAN_FLG = '1' THEN '6'"
            + "      WHEN A.KENKYU_KUBUN = '2' AND B.BUNTAN_FLG = '1' THEN '7'"
            + "      ELSE ' ' END KENKYU_KUBUN" 		//�v�挤���E���匤��
            + ",CASE WHEN A.KOMOKU_NO = 'X00' AND B.BUNTAN_FLG = '1' THEN '1'"
            + "      WHEN A.KOMOKU_NO = 'Y00' AND B.BUNTAN_FLG = '1' THEN '2'"
            + "      WHEN A.CHOSEIHAN = '1'   AND B.BUNTAN_FLG = '1' THEN '3'"
            + "      ELSE ' ' END CHOSEIHAN" 			//������
			//2005.10.17kainuma �ǉ�
//UPDATE�@START 2007/07/25 BIS �����_   //�o�̓t�@�C���d�l20070720.xls�ɂ���                 
			/*
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//�Ő�
			//�ǉ��ȏ�
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN ' ' ELSE LPAD(A.BUNTANKIN_FLG,1,' ') END BUNTANKIN_FLG" //���S���̗L��
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1�N�ڌ����o��(7�o�C�g)
			//2005.10.17kainuma �ǉ�
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI1,7,'0') END BIHINHI1" //1�N�ڐݔ����i��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI1,7,'0') END SHOMOHINHI1" //1�N�ڏ��Օi��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI1,7,'0') END RYOHI1" //1�N�ڗ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN1,7,'0') END SHAKIN1" //	1�N�ڎӋ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA1,7,'0') END SONOTA1" //1�N�ڂ��̑�(7�o�C�g)
			//�ǉ��ȏ�
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2�N�ڌ����o��(7�o�C�g)
			//2005.10.17kainuma �ǉ�
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI2,7,'0') END BIHINHI2" //2�N�ڐݔ����i��(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI2,7,'0') END SHOMOHINHI2" //2�N�ڏ��Օi��(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI2,7,'0') END RYOHI2" //2�N�ڗ���(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN2,7,'0') END SHAKIN2" //2�N�ڎӋ���(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA2,7,'0') END SONOTA2" //2�N�ڂ��̑�(7�o�C�g)
		    //�ǉ��ȏ�
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3�N�ڌ����o��(7�o�C�g)
			//2005.10.17kainuma �ǉ�
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI3,7,'0') END BIHINHI3" //3�N�ڐݔ����i��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI3,7,'0') END SHOMOHINHI3" //3�N�ڏ��Օi��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI3,7,'0') END RYOHI3" //3�N�ڗ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN3,7,'0') END SHAKIN3" //3�N�ڎӋ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA3,7,'0') END SONOTA3" //3�N�ڂ��̑�(7�o�C�g)
		    //�ǉ��ȏ�
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4�N�ڌ����o��(7�o�C�g)
			//2005.10.17kainuma �ǉ�
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI4,7,'0') END BIHINHI4" //4�N�ڐݔ����i��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI4,7,'0') END SHOMOHINHI4" //4�N�ڏ��Օi��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI4,7,'0') END RYOHI4" //4�N�ڗ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN4,7,'0') END SHAKIN4" //4�N�ڎӋ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA4,7,'0') END SONOTA4" //4�N�ڂ��̑�(7�o�C�g)
			//�ǉ��ȏ�
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5�N�ڌ����o��(7�o�C�g)
			//2005.10.17kainuma �ǉ�
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.BIHINHI5,7,'0') END BIHINHI5" //5�N�ڐݔ����i��(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHOMOHINHI5,7,'0') END SHOMOHINHI5" //5�N�ڏ��Օi��(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.RYOHI5,7,'0') END RYOHI5" //5�N�ڗ���(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SHAKIN5,7,'0') END SHAKIN5" //5�N�ڎӋ���(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '       ' ELSE LPAD(A.SONOTA5,7,'0') END SONOTA5" //5�N�ڂ��̑�(7�o�C�g)
		    //�ǉ��ȏ�
            
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAI_NO_KEIZOKU" //�p�����̌����ۑ�ԍ��i8�o�C�g�j
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
            + ",CASE WHEN B.BUNTAN_FLG = '2' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" //�����Ґ��i2�o�C�g�j
            //2005.10.24kainuma�@�ǉ�
            +",CASE WHEN  B.BUNTAN_FLG = '2' THEN ' '"
            +"		WHEN  B.BUNTAN_FLG = '1' AND A.KAIJIKIBO_FLG_NO = '0' THEN ' '"
            +"      ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBOFLGNO" //�J����]�̗L���i1�o�C�g�j"
            //�ǉ��ȏ�
            */
            
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.EDITION,2,'0') END EDITION"		//�Ő�
			//�ǉ��ȏ�
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN ' ' ELSE LPAD(A.BUNTANKIN_FLG,1,' ') END BUNTANKIN_FLG" //���S���̗L��
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1" //1�N�ڌ����o��(7�o�C�g)
			//2005.10.17kainuma �ǉ�
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI1,7,'0') END BIHINHI1" //1�N�ڐݔ����i��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI1,7,'0') END SHOMOHINHI1" //1�N�ڏ��Օi��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI1,7,'0') END RYOHI1" //1�N�ڗ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN1,7,'0') END SHAKIN1" //	1�N�ڎӋ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA1,7,'0') END SONOTA1" //1�N�ڂ��̑�(7�o�C�g)
			//�ǉ��ȏ�
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2" //2�N�ڌ����o��(7�o�C�g)
			//2005.10.17kainuma �ǉ�
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI2,7,'0') END BIHINHI2" //2�N�ڐݔ����i��(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI2,7,'0') END SHOMOHINHI2" //2�N�ڏ��Օi��(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI2,7,'0') END RYOHI2" //2�N�ڗ���(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN2,7,'0') END SHAKIN2" //2�N�ڎӋ���(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA2,7,'0') END SONOTA2" //2�N�ڂ��̑�(7�o�C�g)
		    //�ǉ��ȏ�
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3" //3�N�ڌ����o��(7�o�C�g)
			//2005.10.17kainuma �ǉ�
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI3,7,'0') END BIHINHI3" //3�N�ڐݔ����i��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI3,7,'0') END SHOMOHINHI3" //3�N�ڏ��Օi��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI3,7,'0') END RYOHI3" //3�N�ڗ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN3,7,'0') END SHAKIN3" //3�N�ڎӋ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA3,7,'0') END SONOTA3" //3�N�ڂ��̑�(7�o�C�g)
		    //�ǉ��ȏ�
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4" //4�N�ڌ����o��(7�o�C�g)
			//2005.10.17kainuma �ǉ�
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI4,7,'0') END BIHINHI4" //4�N�ڐݔ����i��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI4,7,'0') END SHOMOHINHI4" //4�N�ڏ��Օi��(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI4,7,'0') END RYOHI4" //4�N�ڗ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN4,7,'0') END SHAKIN4" //4�N�ڎӋ���(7�o�C�g)
			+ ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA4,7,'0') END SONOTA4" //4�N�ڂ��̑�(7�o�C�g)
			//�ǉ��ȏ�
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5" //5�N�ڌ����o��(7�o�C�g)
			//2005.10.17kainuma �ǉ�
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.BIHINHI5,7,'0') END BIHINHI5" //5�N�ڐݔ����i��(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHOMOHINHI5,7,'0') END SHOMOHINHI5" //5�N�ڏ��Օi��(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.RYOHI5,7,'0') END RYOHI5" //5�N�ڗ���(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SHAKIN5,7,'0') END SHAKIN5" //5�N�ڎӋ���(7�o�C�g)
		    + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '       ' ELSE LPAD(A.SONOTA5,7,'0') END SONOTA5" //5�N�ڂ��̑�(7�o�C�g)
		    //�ǉ��ȏ�
            
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD(' ',8) ELSE NVL(A.KADAI_NO_KEIZOKU,'        ') END KADAI_NO_KEIZOKU" //�p�����̌����ۑ�ԍ��i8�o�C�g�j
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN RPAD('�@',80,'�@') ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI" //�����ۑ薼�i�a���j�i80�o�C�g�j
            + ",CASE WHEN B.BUNTAN_FLG <> '1' THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,'0') END KENKYUNINZU" //�����Ґ��i2�o�C�g�j
            //2005.10.24kainuma�@�ǉ�
            +",CASE WHEN  B.BUNTAN_FLG <> '1' THEN ' '"
            +"		WHEN  B.BUNTAN_FLG = '1' AND A.KAIJIKIBO_FLG_NO = '0' THEN ' '"
            +"      ELSE NVL(A.KAIJIKIBO_FLG_NO,' ') END KAIJIKIBOFLGNO" //�J����]�̗L���i1�o�C�g�j"
            //�ǉ��ȏ�
            
//UPDATE�@END�@ 2007/07/25 BIS �����_            
            
            + ",NVL(B.BUNTAN_FLG,' ') BUNTANFLG" 		//��\�ҕ��S�ҕ�(1�o�C�g)
			//2005.10.17kainuma�@�ǉ�
		    + ",RPAD(B.NAME_KANA_SEI,32,'�@') NAMEKANASEI"	//�����i�t���K�i�[���j(32�o�C�g)

            //2005.12.22 iso NULL���������̃o�O�C��
//			+ ",RPAD(B.NAME_KANA_MEI,32,'�@') NAMEKANAMEI"	//�����i�t���K�i�[���j(32�o�C�g)
			+ ",RPAD(NVL(B.NAME_KANA_MEI, '�@'),32,'�@') NAMEKANAMEI"	//�����i�t���K�i�[���j(32�o�C�g)
			+ ",RPAD(B.NAME_KANJI_SEI,32,'�@') NAMEKANJISEI"	//�����i�������[���j(32�o�C�g)
            //2005.12.22 iso NULL���������̃o�O�C��
//			+ ",RPAD(B.NAME_KANJI_MEI,32,'�@') NAMEKANJIMEI"	//�����i�������[���j(32�o�C�g)
			+ ",RPAD(NVL(B.NAME_KANJI_MEI, '�@'),32,'�@') NAMEKANJIMEI"	//�����i�������[���j(32�o�C�g)
			
		    //�ǉ��ȏ�
            
            + ",LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD" 	//�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����(5�o�C�g)
            + ",LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD " 	//���ǖ�(�R�[�h)(3�o�C�g)
            + ",LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD" 	//�E���R�[�h(2�o�C�g)
            + ",LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO" 	//�����Ҕԍ�(8�o�C�g)
            + ",LPAD(B.KEIHI, 7,'0') KEIHI" 			//�����o��(7�o�C�g)
            + ",LPAD(B.EFFORT, 3,'0') EFFORT" 			//�G�t�H�[�g(3�o�C�g)

            + " FROM SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B"
            + " WHERE A.DEL_FLG = 0"
//Modify By Sai 2006.09.15 Start             
//            + "   AND A.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_TOKUTEI
            + "   AND SUBSTR(A.JIGYO_ID,3,5) ='00021' " 
//          Modify By Sai 2006.09.15 End             
            + "   AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
            + "   AND A.SYSTEM_NO = B.SYSTEM_NO"
            + " ORDER BY A.JIGYO_ID, A.SHOZOKU_CD, A.RYOIKI_NO, A.KOMOKU_NO, SHERINO, BUNTANFLG, B.SEQ_NO"; //����ID�A�@�ց[�̈�ԍ��[���ڔԍ��[�����ԍ����A��\�ҕ��S�҃t���O���A�V�[�P���X�ԍ���

		//for debug
		if (log.isDebugEnabled()) {
		log.debug("query:" + query);
		}
		
		List list = new ArrayList();
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		try {
		list = SelectUtil.select(connection, query.toString());
		} catch (DataAccessException e) {
		throw new ApplicationException("�f�[�^�x�[�X�A�N�Z�X����DB�G���[���������܂����B",
		        new ErrorInfo("errors.4004"), e);
		} catch (NoDataFoundException e) {
		throw new NoDataFoundException("�f�[�^�x�[�X��1�����f�[�^������܂���B", e);
		}
		
		//�ŏI�I��return����String��錾����
		StringBuffer finalResult = new StringBuffer();
		
		for (int i = 0; i < list.size(); i++) {
		
		//	long start = System.currentTimeMillis();
		
		//DB����擾����List�^list��String�^�ɂ��āA����String��List�����
		Map recordMap = (Map) list.get(i);
		String strDataKbn = "02";
		
		
		String strShinseiKbn = (String) recordMap.get("SHINSEI_KUBUN");
		String strShozokuDaihyo = (String) recordMap.get("SHOZOKU_CD_DAIHYO");
		String strRyoikiNo = (String) recordMap.get("RYOIKI_NO");
		String strKomokuNo = (String) recordMap.get("KOMOKU_NO");
		String strSheriNo = (String) recordMap.get("SHERINO");
		String strKENKYU_KUBUN = (String) recordMap.get("KENKYU_KUBUN");
		String strCHOSEIHAN = (String) recordMap.get("CHOSEIHAN");
		//2005.10.17 kainuma�ǉ�
		String edition = (String)recordMap.get("EDITION");
		//�ǉ��ȏ�
		String strBUNTANKIN_FLG = (String) recordMap.get("BUNTANKIN_FLG");
		String strKEIHI1 = (String) recordMap.get("KEIHI1");
		
		//2005.10.17 kainuma�ǉ�
		String strBIHINHI1 = (String)recordMap.get("BIHINHI1");
		String strSHOMOHINHI1 = (String)recordMap.get("SHOMOHINHI1");
		String strRYOHI1 = (String)recordMap.get("RYOHI1");
		String strSHAKIN1 = (String)recordMap.get("SHAKIN1");
		String strSONOTA1 = (String)recordMap.get("SONOTA1");
		//�ǉ��ȏ�
		
		
		String strKEIHI2 = (String) recordMap.get("KEIHI2");
		//2005.10.17 kainuma�ǉ�
		String strBIHINHI2 = (String)recordMap.get("BIHINHI2");
		String strSHOMOHINHI2 = (String)recordMap.get("SHOMOHINHI2");
		String strRYOHI2 = (String)recordMap.get("RYOHI2");
		String strSHAKIN2 = (String)recordMap.get("SHAKIN2");
		String strSONOTA2 = (String)recordMap.get("SONOTA2");
		//�ǉ��ȏ�
		
		String strKEIHI3 = (String) recordMap.get("KEIHI3");
		//2005.10.17 kainuma�ǉ�
		String strBIHINHI3 = (String)recordMap.get("BIHINHI3");
		String strSHOMOHINHI3 = (String)recordMap.get("SHOMOHINHI3");
		String strRYOHI3 = (String)recordMap.get("RYOHI3");
		String strSHAKIN3 = (String)recordMap.get("SHAKIN3");
		String strSONOTA3 = (String)recordMap.get("SONOTA3");
		//�ǉ��ȏ�
		
		String strKEIHI4 = (String) recordMap.get("KEIHI4");
		//2005.10.17 kainuma�ǉ�
		String strBIHINHI4 = (String)recordMap.get("BIHINHI4");
		String strSHOMOHINHI4 = (String)recordMap.get("SHOMOHINHI4");
		String strRYOHI4 = (String)recordMap.get("RYOHI4");
		String strSHAKIN4 = (String)recordMap.get("SHAKIN4");
		String strSONOTA4 = (String)recordMap.get("SONOTA4");
		//�ǉ��ȏ�
		            
		String strKEIHI5 = (String) recordMap.get("KEIHI5");
		//2005.10.17 kainuma�ǉ�
		String strBIHINHI5 = (String)recordMap.get("BIHINHI5");
		String strSHOMOHINHI5 = (String)recordMap.get("SHOMOHINHI5");
		String strRYOHI5 = (String)recordMap.get("RYOHI5");
		String strSHAKIN5 = (String)recordMap.get("SHAKIN5");
		String strSONOTA5 = (String)recordMap.get("SONOTA5");
		//�ǉ��ȏ�
		
		String strKADAI_NO_KEIZOKU = (String) recordMap.get("KADAI_NO_KEIZOKU");
		String strKadaiName = (String) recordMap.get("KADAINAMEKANJI");
		String strKENKYU_NINZU = (String) recordMap.get("KENKYUNINZU");
		
		//2005.10.24 kainuma�ǉ�
		String strKAIJIKIBO_FLG_NO = (String)recordMap.get("KAIJIKIBOFLGNO");
		//�ǉ��ȏ�
		String strBuntanFlg = (String) recordMap.get("BUNTANFLG");
		
		//2005.10.17�@kainuma�ǉ�
		String nameKanaSei = (String)recordMap.get("NAMEKANASEI");
		String nameKanaMei = (String)recordMap.get("NAMEKANAMEI");
		String nameKanjiSei = (String)recordMap.get("NAMEKANJISEI");
		String nameKanjiMei = (String)recordMap.get("NAMEKANJIMEI");
		//�ǉ��ȏ�
				  
		String strSHOZOKU_CD = (String) recordMap.get("SHOZOKUCD");
		String strBUKYOKU_CD = (String) recordMap.get("BUKYOKUCD");
		String strSHOKUSHU_CD = (String) recordMap.get("SHOKUSHUCD");
		String strKENKYU_NO = (String) recordMap.get("KENKYUNO");
		String strKeihi = (String) recordMap.get("KEIHI");
		String strEffort = (String) recordMap.get("EFFORT");
		
		//DB���擾�������R�[�h��GC�̑Ώۂɂ���i��ʃf�[�^�ɑΉ����邽�߁j
		recordMap = null;
		list.set(i, null);
		
		//�ŏI���ʂɃ��R�[�h�i�{�s�j�𕶎��񌋍�
		finalResult.append(strDataKbn)
				   .append(strShinseiKbn)
				   .append(strShozokuDaihyo)
				   .append(strRyoikiNo)
				   .append(strKomokuNo)
				   .append(strSheriNo)
				   .append(strKENKYU_KUBUN)
				   .append(strCHOSEIHAN)
				   .append(edition)
				   .append(strBUNTANKIN_FLG)
				   
				   .append(strKEIHI1)
				   //2005.10.17 kainuma�ǉ�
				   .append(strBIHINHI1)
				   .append(strSHOMOHINHI1)
				   .append(strRYOHI1)
				   .append(strSHAKIN1)
				   .append(strSONOTA1)
				   //�ǉ��ȏ�
				   
				  
				   .append(strKEIHI2)
					//2005.10.17 kainuma�ǉ�
				   .append(strBIHINHI2)
				   .append(strSHOMOHINHI2)
				   .append(strRYOHI2)
				   .append(strSHAKIN2)
				   .append(strSONOTA2)
					//�ǉ��ȏ�
							 
				   .append(strKEIHI3)
				   //2005.10.17 kainuma�ǉ�
				   .append(strBIHINHI3)
				   .append(strSHOMOHINHI3)
				   .append(strRYOHI3)
				   .append(strSHAKIN3)
				   .append(strSONOTA3)
				   //�ǉ��ȏ�
				   
				  .append(strKEIHI4)
				   //2005.10.17 kainuma�ǉ�
				  .append(strBIHINHI4)
				  .append(strSHOMOHINHI4)
				  .append(strRYOHI4)
				  .append(strSHAKIN4)
				  .append(strSONOTA4)
				   //�ǉ��ȏ�
				   
				   .append(strKEIHI5)
					//2005.10.17 kainuma�ǉ�
			   	   .append(strBIHINHI5)
				   .append(strSHOMOHINHI5)
				   .append(strRYOHI5)
				   .append(strSHAKIN5)
				   .append(strSONOTA5)
					//�ǉ��ȏ�
				   
				   .append(strKADAI_NO_KEIZOKU)
				   .append(strKadaiName)
				   .append(strKENKYU_NINZU)
					//2005.10.17 kainuma�ǉ�
				   .append(strKAIJIKIBO_FLG_NO)
				    //�ǉ��ȏ�
				   .append(strBuntanFlg)
					//2005.10.17 kainuma�ǉ�
				   .append(nameKanaSei)
				   .append(nameKanaMei)
				   .append(nameKanjiSei)
				   .append(nameKanjiMei)
				    //�ǉ��ȏ�
				   .append(strSHOZOKU_CD)
				   .append(strBUKYOKU_CD)
				   .append(strSHOKUSHU_CD)
				   .append(strKENKYU_NO)
				   .append(strKeihi)
				   .append(strEffort)
				   .append(LINE_SHIFT);
		
		}
		
		return finalResult.toString();
		}
    
}