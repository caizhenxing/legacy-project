/*
 * �쐬��: 2005/03/31
 *
 */
package jp.go.jsps.kaken.model.impl;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.impl.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.status.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * �`�F�b�N���X�g���Ǘ��N���X.<br><br>
 *
 * <b>�T�v:</b><br>
 * �`�F�b�N���X�g�����Ǘ�����B<br><br>
 *
 * �g�p�e�[�u��<br>
 * <table>
 * <tr><td>�`�F�b�N���X�g�e�[�u��</td><td>�F�`�F�b�N���X�g�����Ǘ�</td></tr>
 * </table>
 *
 */
public class CheckListMaintenance implements ICheckListMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(CheckListMaintenance.class);
    
// 2007/02/06 ���u�j�@�ǉ���������
    /** ���O�i�X�e�C�^�X�j*/
    protected static Log statusLog = LogFactory.getLog("status");
// 2007/02/06�@���u�j�@�ǉ������܂�

    //ADD�@START 2007/07/20 BIS ����
    //���x�����J�X�^�}�C�Y
    //�ꊇ�󗝃��O�o��
    protected static Log juriLog = LogFactory.getLog("juri");
    //ADD�@END 2007/07/20 BIS ����
    
	/** ���[���T�[�o�A�h���X */
	private static final String SMTP_SERVER_ADDRESS =
								ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS);

	/** ���o�l�i���ꂵ�ĂP�j */
	private static final String FROM_ADDRESS =
								ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);

	/** ���[�����e�i�\���҂��\�����m�F�����������Ƃ��j�u�����v */
	private static final String SUBJECT_CHECKLIST_KAKUTEI =
								ApplicationSettings.getString(ISettingKeys.SUBJECT_CHECKLIST_KAKUTEI);

	/** ���[�����e�i�\���҂��\�����m�F�����������Ƃ��j�u�{���v */
	private static final String CONTENT_CHECKLIST_KAKUTEI =
								ApplicationSettings.getString(ISettingKeys.CONTENT_CHECKLIST_KAKUTEI);

	/**
	 * ���发�ނ̒�o���t�@�C���i�[�t�H���_ .<br /><br />
	 *
	 * ��̓I�Ȓl�́A"<b>${shinsei_path}/work/oubo/</b>"<br />
	 */
	private static String OUBO_WORK_FOLDER = ApplicationSettings.getString(ISettingKeys.OUBO_WORK_FOLDER);

	/**
	 * ���发�ނ̒�o��Word�t�@�C���i�[�t�H���_.<br /><br />
	 *
	 * ��̓I�Ȓl�́A"<b>${shinsei_path}/settings/oubo/</b>"<br />
	 */
	private static String OUBO_FORMAT_PATH = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_PATH);

	/**
	 * ���发�ނ̒�o��Word�t�@�C����.<br /><br />
	 *
	 * ��̓I�Ȓl(���)�́A"<b>kiban.doc</b>"<br />
	 * ��̓I�Ȓl(����̈�)�́A"<b>tokutei.doc</b>"<br />
	 * ��̓I�Ȓl(���X�^�[�g�A�b�v)�́A"<b>wakate.doc</b>"<br />
	 * ��̓I�Ȓl(���ʌ������i��)�́A"<b>shokushinhi.doc</b>"<br />
	 */
	private static String OUBO_FORMAT_FILE_NAME_KIBAN = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_FILE_NAME_KIBAN);
	private static String OUBO_FORMAT_FILE_NAME_TOKUTEI = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_FILE_NAME_TOKUTEI);
	private static String OUBO_FORMAT_FILE_NAME_WAKATESTART = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_FILE_NAME_WAKATESTART);
	private static String OUBO_FORMAT_FILE_NAME_SHOKUSHINHI = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_FILE_NAME_SHOKUSHINHI);

	/** �\���󗝃t���O�F�� */
	public static final String FLAG_JURI_KEKKA_JURI 	 = "0";

	/** �\���󗝃t���O�F�s�� */
	public static final String FLAG_JURI_KEKKA_FUJURI	 = "1";

	/** �\���󗝃t���O�F�C���˗� */
	public static final String FLAG_JURI_KEKKA_SHUSEIIRAI	 = "2";

	/** �\�����폜�t���O�i�ʏ�j */
	public static final String FLAG_APPLICATION_NOT_DELETE = "0";

	/** �\�����폜�t���O�i�폜�ς݁j */
	public static final String FLAG_APPLICATION_DELETE	   = "1";

	/** �d���\���`�F�b�N�t���O */
	protected static final boolean CHECK_DUPLICACATION_FLAG =
										ApplicationSettings.getBoolean(ISettingKeys.CHECK_DUPLICACATION_FLAG);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public CheckListMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement ICheckListMaintenance
	//---------------------------------------------------------------------

	/**
	 * �`�F�b�N���X�g�ꗗ�\���p�̃f�[�^���擾����.<br/><br/>
	 *
	 * �`�F�b�N���X�g�e�[�u���A�\���ҏ��e�[�u���A�ƎҒS�����e�[�u������A
	 * �����Ŏw�肳��郌�R�[�h�����擾����B<br/><br/>
	 *
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		CASE
	 * 		  WHEN sysdate < A.UKETUKEKIKAN_START THEN 'FALSE'
	 * 		  WHEN sysdate > A.UKETUKEKIKAN_END THEN 'FALSE'
	 * 		  ELSE 'TRUE'
	 * 		END DATE_FLAG,									   -- ��t���Ԕ��ʃt���O
	 * 		A.NENDO,										   -- �N�x
	 * 		A.KAISU,										   -- ��
	 * 		MASTER_JIGYO.JIGYO_NAME JIGYO_NAME, 			   -- ���Ɩ�
	 * 		A.UKETUKEKIKAN_END, 							   -- ��t���ԏI����
	 * 		COUNT(*) COUNT, 								   -- ����
	 * 		C.KAKUTEI_DATE, 								   -- �m���
	 * 		C.EDITION,										   -- ��
	 * 		A.JIGYO_ID, 									   -- ����ID
	 * 		C.JOKYO_ID, 									   -- ��ID
	 * 		B.SHOZOKU_CD,									   -- �����R�[�h
	 * 		MASTER_KIKAN.SHOZOKU_NAME_KANJI SHOZOKU_NAME,	   -- ������
	 * 		B.JYURI_DATE,									   -- �󗝓�
	 * 		C.CANCEL_FLG									   -- �󗝉����t���O
	 * FROM
	 * 		SHINSEIDATAKANRI B								   -- �\���f�[�^�Ǘ��e�[�u��
	 * INNER JOIN
	 * 		CHCKLISTINFO C									   -- �`�F�b�N���X�g���e�[�u��
	 * INNER JOIN
	 * 		JIGYOKANRI A									   -- ���ƊǗ��e�[�u��
	 * ON
	 * 		A.JIGYO_ID = C.JIGYO_ID
	 * INNER JOIN
	 * 		MASTER_JIGYO MASTER_JIGYO						   -- ���ƃ}�X�^�e�[�u��
	 * ON
	 * 		MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5)
	 * ON
	 * 		C.JIGYO_ID = B.JIGYO_ID
	 * 		AND C.SHOZOKU_CD = B.SHOZOKU_CD
	 * 		AND C.JOKYO_ID = B.JOKYO_ID
	 * INNER JOIN
	 * 		MASTER_KIKAN MASTER_KIKAN						   -- �@�փ}�X�^�e�[�u��
	 * ON
	 * 		MASTER_KIKAN.SHOZOKU_CD = B.SHOZOKU_CD
	 * ------------------------ ���ǒS���҂ŒS�����ǂ����ꍇ�ɒǉ�---------------------------
	 * INNER JOIN
	 * 		TANTOBUKYOKUKANRI TANTO 						   -- �S�����ǊǗ��e�[�u��
	 * ON
	 * 		B.SHOZOKU_CD = TANTO.SHOZOKU_CD
	 * 		AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD
	 * 		AND TANTO.BUKYOKUTANTO_ID = ?
	 * -------------------------------------------------------------------------------------
	 * -------------�Ɩ��S���҂̏ꍇ�Ɍ����̖�������\�����Ȃ��悤�ɂ��邽�ߒǉ�---------------
	 * INNER JOIN
	 * 		ACCESSKANRI AC									   -- �A�N�Z�X�Ǘ��e�[�u��
	 * ON
	 * 		AC.JIGYO_CD = SUBSTR(B.JIGYO_ID,3,5)
	 * 		AND AC.GYOMUTANTO_ID = ?
	 * -------------------------------------------------------------------------------------
	 * WHERE
	 * 		B.DEL_FLG = 0
	 * 		AND A.DEL_FLG = 0
	 * 		AND B.JIGYO_KUBUN = 4
	 * <b><span style="color:#002288">-- ���I�������� --</span></b>
	 * GROUP BY
	 * 		A.NENDO,
	 * 		A.KAISU,
	 * 		MASTER_JIGYO.JIGYO_NAME,
	 * 		A.UKETUKEKIKAN_START,
	 * 		A.UKETUKEKIKAN_END,
	 * 		C.KAKUTEI_DATE,
	 * 		C.EDITION,
	 * 		A.JIGYO_ID,
	 * 		C.JOKYO_ID,
	 * 		B.SHOZOKU_CD,
	 * 		MASTER_KIKAN.SHOZOKU_NAME_KANJI,
	 * 		B.JYURI_DATE,
	 * 		C.CANCEL_FLG
	 * ORDER BY
	 * 		A.JIGYO_ID,
	 * 		B.SHOZOKU_CD
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>TANTO.BUKYOKUTANTO_ID</td><td>������userInfo�̕��ǒS���ҏ��bukyokutantoInfo�̕ϐ�bukyokutantoId</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>AC.GYOMUTANTO_ID</td><td>������userInfo�̋Ɩ��S���ҏ��gyomutantoInfo�̕ϐ�gyomutantoId</td></tr>
	 * </table><br/><br/>
	 *
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>��ID</td><td>jokyoList</td><td>AND C.JOKYO_ID IN(��ID�̔z��)</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>����CD</td><td>shozokuCd</td><td>AND C.SHOZOKU_CD = '����CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>����CD</td><td>jigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) = '����CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>��</td><td>kaisu</td><td>AND A.KAISU = '��'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>�����@�֖�</td><td>shozokuName</td><td>AND B.SHOZOKU_NAME like '%�����@�֖�%'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>�󗝏�(�S��)</td><td>cancellationFlag="0"</td><td>AND (C.CANCEL_FLG = '1' OR C.JOKYO_ID <> '03') </td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>�󗝏�(�m�����)</td><td>cancellationFlag="1"</td><td>AND C.CANCEL_FLG = '1'</td></tr>
	 * </table><br />
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo 	CheckListSearchInfo
	 * @return page	�y�[�W���
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#selectCheckList(UserInfo userInfo, CheckListSearchInfo info)
	 */
	public Page selectCheckList(
		UserInfo userInfo,
		CheckListSearchInfo searchInfo)
		throws NoDataFoundException,ApplicationException {

		// ����̈挤���̎󗝂ɂ��g�p����Ă������ߕύX
		// ������̈挤���ɖ��Ή��ł�����
		// ����̈挤���̎󗝓o�^����Ăяo����鎞�̂ݎ���CD�łȂ�����ID�𒊏o�����ɂ���

		//-----------------------
		// �y�[�W���擾
		//-----------------------
		Connection connection = null;
		try {
			return selectCheckList(userInfo, searchInfo, false);
		}
		catch (NoDataFoundException e) {
			throw new NoDataFoundException("�Y�������񂪑��݂��܂���ł����B",
                    new ErrorInfo("errors.4004"),e);
        }
		catch (ApplicationException e) {
			throw new ApplicationException("�f�[�^��������DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"),e);
        }
		finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

    /**
     * �`�F�b�N���X�g�ꗗ�\���p�̃f�[�^���擾����
     * @param userInfo ���[�U���
     * @param searchInfo �����������
     * @param blnJuriflg 
     * @return Page �y�[�W���
     * @throws ApplicationException
     */
	public Page selectCheckList(
			UserInfo userInfo,
			CheckListSearchInfo searchInfo,
			boolean blnJuriflg)
			throws ApplicationException {

        //-----------------------
        // �����������SQL���̍쐬
        //-----------------------
        String select =
			//2005.12.09 iso ���ߐ؂蓖���Ɋm������{�^�����\������Ȃ��o�O���C��
//			  "SELECT CASE WHEN TRUNC(sysdate < A.UKETUKEKIKAN_START THEN 'FALSE' "
//                 + "WHEN TRUNC(sysdate > A.UKETUKEKIKAN_END THEN 'FALSE' "
            "SELECT CASE WHEN TRUNC(sysdate, 'DD') < A.UKETUKEKIKAN_START THEN 'FALSE' "
                + "WHEN TRUNC(sysdate, 'DD') > A.UKETUKEKIKAN_END THEN 'FALSE' "
                + "ELSE 'TRUE' "
                + "END DATE_FLAG,"
                + " A.NENDO, "
                + "A.KAISU, "
                + "MASTER_JIGYO.JIGYO_NAME JIGYO_NAME, "
                + "A.UKETUKEKIKAN_END, "
                + "COUNT(*) COUNT, "
                + "C.KAKUTEI_DATE, "
                + "C.EDITION, "
                + "A.JIGYO_ID, "
                + "C.JOKYO_ID, "
                + "B.SHOZOKU_CD, "
//add start dyh 2006/3/7 �����F�󗝓o�^��ʂŏ����l��\�����Ȃ�
                + "B.JURI_BIKO,"//�󗝔��l
//add end dyh 2006/3/7
                ;
        // 2005.08.10 iso �@�փ}�X�^�ɑ��݂��Ȃ��T���v�����ԁu99999�v�Ή�
        if (userInfo.getRole().equals(UserRole.GYOMUTANTO)) {
            select += "MASTER_KIKAN.SHOZOKU_NAME_KANJI SHOZOKU_NAME,";
        }
        select += "B.JYURI_DATE, "
                + "C.CANCEL_FLG "
                + "FROM SHINSEIDATAKANRI B "
                + "INNER JOIN  CHCKLISTINFO C "
                + "INNER JOIN JIGYOKANRI A "
                + "ON A.JIGYO_ID = C.JIGYO_ID "
                + "INNER JOIN MASTER_JIGYO MASTER_JIGYO "
                + "ON MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) "
                + "ON C.JIGYO_ID = B.JIGYO_ID "
                + "AND C.SHOZOKU_CD = B.SHOZOKU_CD "
                //2005.12.16 iso �`�F�b�N���X�g�����̕s��C��
//	            + "AND C.JOKYO_ID = B.JOKYO_ID "
                ;
        //2005.08.10 iso �@�փ}�X�^�ɑ��݂��Ȃ��T���v�����ԁu99999�v�Ή�
        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)) {
            select += "INNER JOIN MASTER_KIKAN MASTER_KIKAN "
                    + " ON MASTER_KIKAN.SHOZOKU_CD = B.SHOZOKU_CD ";
        }
        //���R �S�����ǊǗ��e�[�u���Ƀf�[�^�����݂���ꍇ�͒S�����ǊǗ��̕��ǃR�[�h�������ɒǉ�
        if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
            select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
                   + "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
                   + "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
                   + "AND TANTO.BUKYOKUTANTO_ID = '"
                   + EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())
                   + "' ";
        //���R �Ɩ��S���҂ɂă`�F�b�N���X�g�ꗗ��\������ƁA�����ŕ\������Ȃ������\������Ă��܂��B
        } else if(UserRole.GYOMUTANTO.equals(userInfo.getRole())){
            select += "INNER JOIN ACCESSKANRI AC "
                    + "ON AC.JIGYO_CD = SUBSTR(B.JIGYO_ID,3,5) "
                    + "AND AC.GYOMUTANTO_ID = '"
                    + EscapeUtil.toSqlString(userInfo.getGyomutantoInfo().getGyomutantoId())
                    + "' ";
        }
        select = select + "WHERE B.DEL_FLG = 0 "
               + "AND A.DEL_FLG = 0 ";

// 20050606 ���������Ɏ��Ƌ敪��ǉ��@������̈挤�����ǉ����ꂽ���ߌ��������ƂȂ鎖�Ƌ敪���K�v�ɂȂ���
        //���Ƌ敪�F"1"��Ռ����A"5"����̈挤���A"6"���X�^�[�g�A�b�v�A"7"���ʌ������i��
//update ly 2006/06/01
        if(! StringUtil.isBlank(searchInfo.getJigyoKubun())){
        //if(searchInfo.getJigyoKubun() != null){
//update ly 2006/06/01
            select = select + " AND B.JIGYO_KUBUN = "
                   + EscapeUtil.toSqlString(searchInfo.getJigyoKubun())
                   + " ";
        }
        else{
            //���Ƌ敪�����݂��Ȃ������ꍇ�ɂ͊�Ռ������Z�b�g�@������̈�̃`�F�b�N���X�g����J�ڂ���ꍇ�ɂ͂��Ȃ炸���Ƌ敪���Z�b�g����Ă��邽��
            select = select + " AND B.JIGYO_KUBUN = "
                   + IJigyoKubun.JIGYO_KUBUN_KIBAN + " ";
        }

        StringBuffer query = new StringBuffer(select);
        
//        if (searchInfo.getShozokuCd() != null
//                && !searchInfo.getShozokuCd().equals("")) { //�����@�փR�[�h
        if (!StringUtil.isBlank(searchInfo.getShozokuCd())) { //�����@�փR�[�h
            query.append(" AND C.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()));
            query.append("'");
        }
        String[] jokyoList = searchInfo.getSearchJokyoId();
        if(jokyoList != null && jokyoList.length != 0){
            //2005.12.16 iso �`�F�b�N���X�g�����̕s��C��
//	          query.append(" AND C.JOKYO_ID IN(")
            query.append(" AND B.JOKYO_ID IN(")
                 .append(StringUtil.changeArray2CSV(jokyoList, true))
                 .append(")");
        }

// 20050829 ��Ռ����ɂ����Ă��N�x���l������K�v�������� �� blnJuriflg�̕K�v���Ȃ��Ȃ���(�H)�̂�selectCheckList���\�b�h�𕪂���K�v���Ȃ��Ȃ����H
//        if(!blnJuriflg){
//	          String jigyoCd = searchInfo.getJigyoCd();
//	          if(jigyoCd != null && jigyoCd.length() != 0){
//	              //����CD�i����ID��3�����ڂ���5�������j
//	              query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
//    		           .append(EscapeUtil.toSqlString(jigyoCd))
//	                   .append("'");
//	          }
//    	  }
//	      else{
//        	  String jigyoId = searchInfo.getJigyoId();
//        	  if(jigyoId != null && jigyoId.length() != 0){
//                //����ID
//	              query.append(" AND JIGYO_ID = '")
//	                   .append(EscapeUtil.toSqlString(jigyoId))
//	                   .append("'");
//	          }
//    	  }
        String jigyoCd = searchInfo.getJigyoCd();
//update start dyh 2006/06/02
//        if(jigyoCd != null && jigyoCd.length() != 0){
//            //����CD�i����ID��3�����ڂ���5�������j
//            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
//                 .append(EscapeUtil.toSqlString(jigyoCd))
//                 .append("'");
//        }
        if(jigyoCd == null || jigyoCd.length() == 0){
            // �y�`�F�b�N���X�g�Ǘ��i��Ռ����iC�j�E�G�茤���E��茤���j�z�̃����N����J��
            if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(searchInfo.getJigyoKubun())) {
                // ���S�d�q����Ղ̎���CD�̊i�[�i���S,A,B�Ȃ��j
                String[] jigyoCdsKikan = {IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN,  //��Ռ���(C)(���)
                                          IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU, //��Ռ���(C)(��撲��)
                                          //DEL START 2007/07/02 BIS ����
                                          //H19���S�d�q���Ή�
                                          //�\��������ڂ��C��
                                          //IJigyoCd.JIGYO_CD_KIBAN_HOGA,     //�G�茤��
                                          //DEL END 2007/07/02  BIS ����
                                          IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A, //��茤��(A)
                                          IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B};//��茤��(B)
                    
                query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (");
                query.append(StringUtil.changeArray2CSV(jigyoCdsKikan,true));  
                query.append(")");
            }
        }else{
            //����CD�i����ID��3�����ڂ���5�������j
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
                 .append(EscapeUtil.toSqlString(jigyoCd))
                 .append("'");
        }
//update end dyh 2006/06/02
//add start ly 2006/06/01 
        //�S������CD�i����ID��3�����ڂ���5�������j
        if(searchInfo.getTantoJigyoCd() != null && searchInfo.getTantoJigyoCd().size() != 0){
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
                 .append(StringUtil.changeIterator2CSV(searchInfo.getTantoJigyoCd().iterator(), true))
                 .append(")");
        }
//add end ly 2006/06/01
        //2005/09/01 �Ɩ��S���҂̂Ƃ��͒S�����Ƃ������ɒǉ�����
        if(UserRole.GYOMUTANTO.equals(userInfo.getRole())){
            Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
            String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
                 .append(tantoJigyoCd)
                 .append(")")
                 .toString();
        }
            
        String jigyoId = searchInfo.getJigyoId();
        if(jigyoId != null && jigyoId.length() != 0){
            //����ID
            query.append(" AND JIGYO_ID = '")
                 .append(EscapeUtil.toSqlString(jigyoId))
                 .append("'");
        }

        //���R ���ƒS���҂̏ꍇ�̉񐔂̏�����ǉ�
        String kaisu = searchInfo.getKaisu();
        if(kaisu != null && kaisu.length() != 0){
            query.append(" AND A.KAISU = '")
                 .append(EscapeUtil.toSqlString(kaisu))
                 .append("'");
        }

        String shozokuName = searchInfo.getShozokuName();
        if (shozokuName != null && shozokuName.length() != 0) {
            query.append(" AND B.SHOZOKU_NAME like '%")
                 .append(EscapeUtil.toSqlString(shozokuName))
                 .append("%'");
        }

        String cancellationFlag = searchInfo.getCancellationFlag();
        //�󗝏󋵂��S�Ă̏ꍇ
        if(cancellationFlag != null && cancellationFlag.equals("0")){
            query.append(" AND (C.CANCEL_FLG = '1' OR C.JOKYO_ID <> '03') ");
        }
        //�󗝏󋵂��m������̏ꍇ
        else if(cancellationFlag != null && cancellationFlag.equals("1")){
            query.append(" AND C.CANCEL_FLG = '")
                 .append(EscapeUtil.toSqlString(cancellationFlag))
                 .append("'");
        }

        query.append(" GROUP BY A.NENDO, ");
        query.append("A.KAISU, ");
        query.append("MASTER_JIGYO.JIGYO_NAME, ");
        query.append("A.UKETUKEKIKAN_END, ");
        query.append("C.KAKUTEI_DATE, ");
        query.append("C.EDITION, ");
        query.append("A.JIGYO_ID, ");
        query.append("C.JOKYO_ID, ");
        query.append("B.SHOZOKU_CD, ");
//add start dyh 2006/3/7 �����F�󗝓o�^��ʂŏ����l��\�����Ȃ�
        query.append("B.JURI_BIKO,");//�󗝔��l
//add end dyh 2006/3/7

        //2005.08.10 iso �@�փ}�X�^�ɑ��݂��Ȃ��T���v�����ԁu99999�v�Ή�
        if(userInfo.getRole().equals(UserRole.GYOMUTANTO)) {
            query.append("MASTER_KIKAN.SHOZOKU_NAME_KANJI, ");
        }
        query.append("B.JYURI_DATE, ");
        query.append("A.UKETUKEKIKAN_START, ");
        query.append("C.CANCEL_FLG ");
        query.append("ORDER BY A.JIGYO_ID, B.SHOZOKU_CD");

        if (log.isDebugEnabled()) {
            log.debug("query:" + query);
        }

        //-----------------------
        // �y�[�W���擾
        //-----------------------
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
// 20050707 �X�e�[�^�X(��)���擾
            Page page = SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
            new StatusManager(userInfo).setJokyoName(connection, page);
            return page;
// Horikoshi
        }
        catch(NoDataFoundException e){
            throw new NoDataFoundException("�Y�������񂪑��݂��܂���ł����B",
                    new ErrorInfo("errors.4004"),e);
        }
        catch (DataAccessException e) {
            throw new ApplicationException("�f�[�^��������DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"),e);
        }
        catch(RecordCountOutOfBoundsException e){
            throw new ApplicationException("�Y��������MAX�l�𒴂��܂����B",
                    new ErrorInfo("errors.4004"),e);
        }
        finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    /**
     * �`�F�b�N���X�g�̊m�菈�����s��.<br/><br/>
     *
     * �`�F�b�N���X�g�e�[�u���A�\���ҏ��e�[�u���A�ƎҒS�����e�[�u������A
     * �����Ŏw�肳��郌�R�[�h�����擾����B<br/><br/>
     *
     * 1.�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
     * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
     * <tr bgcolor="#FFFFFF"><td>
     * <pre>
     * SELECT
     *     	A.JOKYO_ID		                -- ����ID
     * FROM
     * 	    CHCKLISTINFO A	                -- �`�F�b�N���X�g���e�[�u��
     * INNER JOIN
     * 		SHINSEIDATAKANRI B				-- �\���f�[�^�Ǘ��e�[�u��
	 * ON
	 * 		A.JOKYO_ID = B.JOKYO_ID
	 * 		AND A.SHOZOKU_CD = B.SYOZOKU_CD
	 * WHERE
	 * 		A.SHOZOKU_CD = ?
	 * 		AND A.JIGYO_ID = ?
	 * 		AND B.JIGYO_KUBUN = 4
	 * FOR UPDATE
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������searchInfo�̕ϐ�syozokuCd</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������searchInfo�̕ϐ�jigyoId</td></tr>
	 * </table><br/><br/>
	 *
	 * 2.�擾������ID���`�F�b�N���Ċ��ɕύX����Ă��Ȃ����m�F����B<BR><BR>
	 *
	 * 3.�ȉ���SQL�����s���āA�w�U�L���������`�F�b�N����B<BR>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		COUNT(*) COUNT							   -- �L���������f�[�^����
	 * FROM
	 * 		SHINSEIDATAKANRI DATA					   -- �\���f�[�^�Ǘ��e�[�u��
	 * INNER JOIN
	 * 		CHCKLISTINFO CHECKLIST					   -- �`�F�b�N���X�g���e�[�u��
	 * INNER JOIN
	 * 		JIGYOKANRI JIGYO						   -- ���ƊǗ��e�[�u��
	 * ON
	 * 		JIGYO.JIGYO_ID = CHECKLIST.JIGYO_ID
	 * ON
	 * 		CHECKLIST.JIGYO_ID = DATA.JIGYO_ID
	 * 		AND	CHECKLIST.SHOZOKU_CD = DATA.SHOZOKU_CD
	 * WHERE
	 * 		DATA.JOKYO_ID = CHECKLIST.JOKYO_ID
	 * 		AND DATA.DEL_FLG = 0
	 * 		AND JIGYO.DEL_FLG = 0
	 * 		AND DATA.JIGYO_KUBUN = 4
	 * 		AND SYSDATE BETWEEN JIGYO.UKETUKEKIKAN_START AND JIGYO.UKETUKEKIKAN_END
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * SQL���s���ʂ�0���ŁA��ID��06(�w�U��)�ł͂Ȃ��ꍇ�́A�L�������O�Ƃ���ValidationException��Ԃ��B
	 * <BR><BR>
	 *
	 * 4.DB�̍X�V���s���B<BR>
	 * �`�F�b�N���X�g�̍X�V�p�Ɉȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 * 		CHCKLISTINFO			  -- �`�F�b�N���X�g���e�[�u��
	 * SET
	 * 		JOKYO_ID = ?,
	 * 		KAKUTEI_DATE = sysdate,
	 * 		EDITION = ?,
	 * 		CANCEL_FLG = 0
	 * WHERE
	 * 		SHOZOKU_CD = ?
	 * 		AND JIGYO_ID = ?
	 * 		AND JOKYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br/>
	 * �ύX�l
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>������searchInfo�̕ϐ�jokyoId</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>EDITION</td><td>������searchInfo�̕ϐ�edition</td></tr>
	 * </table><br/>
	 * �i���ݏ���
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������searchInfo�̕ϐ�jokyoId</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������searchInfo�̕ϐ�jigyoId</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>������searchInfo�̕ϐ�jokyoId</td></tr>
	 * </table><br/><br/>
	 * �\�����Ǘ��̍X�V�p�Ɉȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 * 		SHINSEIDATAKANRI			-- �\���f�[�^�Ǘ��e�[�u��
	 * SET
	 * 		JOKYO_ID = ?
	 * WHERE
	 * 		JIGYO_ID = ?
	 * 		AND JOKYO_ID = ?
	 * 		AND SHOZOKU_CD = ?
	 * 		AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * �ύX�l
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>������searchInfo�̕ϐ�jokyoId</td></tr>
	 * </table><br/>
	 * �i���ݏ���
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������searchInfo�̕ϐ�jigyoId</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>������searchInfo�̕ϐ�jokyoId</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������searchInfo�̕ϐ�shozokuCd</td></tr>
	 * </table><br/><br/>
	 *
	 * 5.�s���Ă��鏈�����`�F�b�N���X�g�̊m�菈���̏ꍇ�́A�\��PDF���쐬����B<BR><BR>
	 * pdfConvert��convertHyoshiData(Connection, UserInfo, CheckListSearchInfo)���\�b�h���Ăяo���āA
	 * �\��PDF���쐬����B<BR>
	 * �쐬����PDF��CHCKLISTINFO�e�[�u����PDF_PATH�ɋL�q���ꂽ�p�X��Ɋi�[����Ă���<BR>
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo 		CheckListSearchInfo
	 * @param isVersionUp	boolean
	 * @exception	ApplicationException
	 * @exception	ValidationException
	 */
	public void checkListUpdate(UserInfo userInfo,
		CheckListSearchInfo searchInfo,
		boolean isVersionUp)
		throws ApplicationException,ValidationException {

		boolean success = false;
// 2007/02/06 ���u�j�@�ǉ���������
        String jokyoId= "";
        String afterJokyoId= searchInfo.getChangeJokyoId();
// 2007/02/06�@���u�j�@�ǉ������܂�
        
		//�X�V
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			CheckListInfoDao dao = new CheckListInfoDao(userInfo);
//  2007/02/06 ���u�j�@�C����������
            //String jokyoId = dao.checkJokyoId(connection, searchInfo, true);
            jokyoId = dao.checkJokyoId(connection, searchInfo, true);
//  2007/02/06�@���u�j�@�C�������܂�			
			//��ID�`�F�b�N
			if(jokyoId != null && jokyoId.equals(searchInfo.getJokyoId())){

				//2005/04/12 �ǉ� ��������--------------------------------------------------
				//�L�������O�̏ꍇ��ValidationException��Ԃ��悤�ɏC��
				int checkDate = dao.checkDate(connection, searchInfo);
				//�L�������`�F�b�N
// 2006/06/20 dyh update start �����FCheckListSearchInfo�萔���폜
//                if(checkDate == 0 && !jokyoId.equals(CheckListSearchInfo.GAKUSIN_JYURI)){
				if(checkDate == 0 && !StatusCode.STATUS_GAKUSIN_JYURI.equals(jokyoId)){
// 2006/06/20 dyh update end
					throw new ValidationException(
						"�w�U�L�������O�̃`�F�b�N���X�g�ł�", new ArrayList());
				}
				//�ǉ� �����܂�-------------------------------------------------------------

// 2007/02/06 ���u�j�@�ǉ���������
                /** ���O �X�V�O  */
                if (jokyoId.equalsIgnoreCase("03")){
                    statusLog.info( " ������m��O , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                            + " , ����ID : " + searchInfo.getJigyoId() + " , �@�փR�[�h : " + searchInfo.getShozokuCd() + " , �X�V�O�\����ID : " + jokyoId );    
                }
                else if (jokyoId.equalsIgnoreCase("04")){
                    statusLog.info( " �`�F�b�N���X�g�m������O , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                            + " , ����ID : " + searchInfo.getJigyoId() + " , �����@�փR�[�h : " + searchInfo.getShozokuCd() + " , �X�V�O�\����ID : " + jokyoId );
                }
                else if (jokyoId.equalsIgnoreCase("06")){
                    statusLog.info( " �`�F�b�N���X�g�󗝉����O , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                            + " , ����ID : " + searchInfo.getJigyoId() + " , �����@�փR�[�h : " + searchInfo.getShozokuCd() + " , �X�V�O�\����ID : " + jokyoId );
                }
// 2007/02/06�@���u�j�@�ǉ������܂� 
//              <!-- UPDATE�@START 2007/07/18 BIS ���� -->
                //�R�������U��f�[�^��荞�ݎ��̃`�b�N
                String jigyoCd = searchInfo.getJigyoId().substring(2,7);
    			if (IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd) || IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd) || 
    					IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd) || IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)){
    				//�`�F�b�N���X�g�̍X�V
    				dao.updateCheckListInfo(connection, searchInfo, isVersionUp);
    			}
//    			<!-- UPDATE�@END�@ 2007/07/18 BIS ���� -->
				//�\�����Ǘ��̍X�V
				dao.updateShinseiData(connection, searchInfo);

				//2005/05/25 �ǉ� ��������-------------------------------------------------
				//���R �\��PDF�쐬�̂���
// 2006/06/20 dyh update start �����FCheckListSearchInfo�萔��ύX
//                if(isVersionUp && jokyoId.equals(CheckListSearchInfo.SHOZOKU_UKETUKE)){
				if(isVersionUp && jokyoId.equals(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU)){
// 2006/06/20 dyh update end
					IPdfConvert pdfConvert = new PdfConvert();
					try{
						pdfConvert.convertHyoshiData(connection, userInfo, searchInfo);
					}catch(Exception e){
						throw new ApplicationException("�\��PDF�̍쐬�ŃG���[���������܂���",e);
					}
				}
				//�ǉ� �����܂�------------------------------------------------------------

			}else{
				throw new ApplicationException(
					"���Ɋm�肳�ꂽ�`�F�b�N���X�g�ł�",
					new ErrorInfo("errors.4002"));
			}

			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"��ID�X�V����DB�G���[���������܂���",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
// 2007/02/06 ���u�j�@�ǉ���������
                    /** ���O �X�V�� */
                    if (jokyoId.equalsIgnoreCase("03")){
                        statusLog.info( " ������m��� , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                                + " , ����ID : " + searchInfo.getJigyoId() + " , �@�փR�[�h : " + searchInfo.getShozokuCd() + " , �X�V��\����ID : " + afterJokyoId); 
                    } 
                    else if (jokyoId.equalsIgnoreCase("04")){
                        statusLog.info( " �`�F�b�N���X�g�m������� , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                                + " , ����ID : " + searchInfo.getJigyoId() + " , �����@�փR�[�h : " + searchInfo.getShozokuCd() + " , �X�V��\����ID : " + afterJokyoId);
                    }
                    else if (jokyoId.equalsIgnoreCase("06")){
                        statusLog.info( " �`�F�b�N���X�g�󗝉����� , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                                + " , ����ID : " + searchInfo.getJigyoId() + " , �����@�փR�[�h : " + searchInfo.getShozokuCd() + " , �X�V��\����ID : " + afterJokyoId);
                    }
// 2007/02/06�@���u�j�@�ǉ������܂�                    
				} else {
					DatabaseUtil.rollback(connection);
// 2007/02/06 ���u�j�@�ǉ���������
                    /** ���O �X�V���s  */
                     if (jokyoId.equalsIgnoreCase("03")){
                         statusLog.info( " ������m�莸�s , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                                 + " , ����ID : " + searchInfo.getJigyoId() + " , �@�փR�[�h : " + searchInfo.getShozokuCd());
                     }
                     else if (jokyoId.equalsIgnoreCase("04")){
                         statusLog.info( " �`�F�b�N���X�g�m��������s , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                                 + " , ����ID : " + searchInfo.getJigyoId() + " , �����@�փR�[�h : " + searchInfo.getShozokuCd());
                     }
                     else if (jokyoId.equalsIgnoreCase("06")){
                         statusLog.info( " �`�F�b�N���X�g�󗝉������s , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                                 + " , ����ID : " + searchInfo.getJigyoId() + " , �����@�փR�[�h : " + searchInfo.getShozokuCd());
                     }
// 2007/02/06�@���u�j�@�ǉ������܂�                    
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"��ID�X�V����DB�G���[���������܂���",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}

		//---------------
		// ���[�����M�i�����@�֒S���҂̂݁j 2005/07/14�ǉ�
		//---------------

		if (userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
            // 2006/08/21 dyh add start
            // [��Ռ������i���S,A,B�͏����j�E����̈�i�p���̈�j�E��茤���X�^�[�g�A�b�v�E���ʌ������i��]�ȊO�̏��������@�ւ��`�F�b�N���X�g���m�肵���Ƃ��A���M���Ȃ�
            String tempJigyoCd = "";
            if(searchInfo.getJigyoId() != null && searchInfo.getJigyoId().length() == 8){
                tempJigyoCd = searchInfo.getJigyoId().substring(2,7);
            }
            if(!JigyoKubunFilter.getCheckListKakuteiJigyoCd().contains(tempJigyoCd)){
                return;
            }
            // 2006/08/21 dyh add end

			//���Y�����@�֒S���҂̃��[���A�h���X�����擾����
			String to = userInfo.getShozokuInfo().getTantoEmail();

			//-----���[���{���t�@�C���̓ǂݍ���
			String content = null;
			try{
				File contentFile = new File(CONTENT_CHECKLIST_KAKUTEI);
				FileResource fileRes = FileUtil.readFile(contentFile);
				content = new String(fileRes.getBinary());
			}catch(FileNotFoundException e){
				throw new ApplicationException(
						"���[���{���t�@�C����������܂���ł����B",
// 2006/08/21 dyh update start
						//new ErrorInfo("errors.4002"),
                        new ErrorInfo("errors.7004"),
// 2006/08/21 dyh update end
						e);
			}catch(IOException e){
				throw new ApplicationException(
						"���[���{���t�@�C���ǂݍ��ݎ��ɃG���[���������܂����B",
// 2006/08/21 dyh update start
						//new ErrorInfo("errors.4002"),
                        new ErrorInfo("errors.8003"),
// 2006/08/21 dyh update end
						e);
			}

			//�֘A���擾
			//Page titleResult = null;
			try{
				List titleResult = this.selectTitleInfo(searchInfo);

				Map map = (Map)titleResult.get(0);

				//������ږ�
				String jigyoName = "����" + map.get("NENDO") + "�N�x ";

				//�񐔃`�F�b�N
				String kaishu = map.get("KAISU").toString();
				if (kaishu != null && Integer.parseInt(kaishu) > 1){
					jigyoName = jigyoName + "��" + kaishu + "�� ";
				}

				jigyoName = jigyoName + map.get("JIGYO_NAME");

				//�m����̎擾
				String kakuteiDate = "";
				Date dte = (Date)map.get("KAKUTEI_DATE");
				if (dte != null){
					kakuteiDate = new SimpleDateFormat("yyyy�NMM��dd��").format(dte);
				}

				//�Ő�
//				<!-- UPDATE�@START 2007/07/18 BIS ���u�� -->
				String edition = map.get("ALLEDITION") == null ? "" : map.get("ALLEDITION").toString();
				//<!-- �Â��R�[�h --> String edition = map.get("ALLEDITION").toString();
//				<!-- UPDATE�@END 2007/07/18 BIS ���u�� -->
				//����
				int kensu = selectListDataCount(userInfo, searchInfo);

//				log.debug("JIGYO_NAME:" + map.get("JIGYO_NAME").toString());
//				log.debug("ALLEDITION:" + map.get("ALLEDITION").toString());
//				log.debug("KENSHU:" + kensu);
//				log.debug("KAKUTEI_DATE:" + kakuteiDate);

				//-----���[���{���t�@�C���̓��I���ڕύX
				String[] param = new String[]{
						jigyoName, 						//���Ɩ�
						kakuteiDate,					//�m���
						edition,						//�Ő�
						Integer.toString(kensu) 		//���匏��
				};
				content = MessageFormat.format(content, param);

				if (log.isDebugEnabled()) {
					log.debug("content:" + content);
                }
// 2006/08/21 dyh update start
//            }catch (Exception e){
//                throw new ApplicationException(
//                        "�`�F�b�N���X�g�m��Ŋ֘A���擾�Ɏ��s���܂����B",
//                        new ErrorInfo("errors.4002"),
//                        e);
//            }
			}catch (NoDataFoundException e){
                throw new ApplicationException(
                        "�`�F�b�N���X�g�m��Ŋ֘A���擾�Ɏ��s���܂����B",
                        new ErrorInfo("errors.5002"),
                        e);
            }catch (Exception e){
				throw new ApplicationException(
						"�`�F�b�N���X�g�m��Ŋ֘A���擾�Ɏ��s���܂����B",
						new ErrorInfo("errors.8003"),
						e);
			}
// 2006/08/21 dyh update end

			//-----���[�����M
			try{
				SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
				mailer.sendMail(FROM_ADDRESS,							//���o�l
								to,										//to
								null,									//cc
								null,									//bcc
								SUBJECT_CHECKLIST_KAKUTEI,				//����
								content);								//�{��
			}catch(Exception e){
				//2005.08.03 iso ���[�����M���s���x���\���݂̂ɕύX
//				throw new ApplicationException(
//						"�`�F�b�N���X�g�m��Ń��[�����M�Ɏ��s���܂����B",
//						new ErrorInfo("errors.4002"),
//						e);
				log.warn("���[�����M�Ɏ��s���܂����B",e);
			}
		}
	}


	/**
	 * �`�F�b�N���X�g�̏�ID���擾����.<br/><br/>
	 *
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		A.JOKYO_ID				 -- ��ID
	 * FROM
	 * 		CHCKLISTINFO A			 -- �`�F�b�N���X�g���e�[�u��
	 * INNER JOIN
	 * 		SHINSEIDATAKANRI B		 -- �\���f�[�^�Ǘ��e�[�u��
	 * ON
	 * 		A.JOKYO_ID = B.JOKYO_ID
	 * WHERE
	 * 		A.SHOZOKU_CD = ?
	 * 		AND A.JIGYO_ID = ?
	 * 		AND A.JIGYO_KUBUN = 4
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������searchInfo�̕ϐ�syozokuCd</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������searchInfo�̕ϐ�jigyoId</td></tr>
	 * </table><br/><br/>
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo CheckListSearchInfo
	 * @return String	��ID
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#checkJokyoId(UserInfo userInfo, CheckListSearchInfo info)
	 */
	public String checkJokyoId(UserInfo userInfo,
		CheckListSearchInfo searchInfo)
		throws ApplicationException {

		String jokyoId = null;
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			CheckListInfoDao dao = new CheckListInfoDao(userInfo);

			jokyoId = dao.checkJokyoId(connection, searchInfo, false);
		} catch (DataAccessException e) {

			throw new ApplicationException(
				"�`�F�b�N���X�g�󋵎擾����DB�G���[���������܂���",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
				DatabaseUtil.closeConnection(connection);
		}

		return jokyoId;
	}


	/**
	 * �`�F�b�N���X�g�\���p�̃f�[�^���擾����.<br/><br/>
	 *
	 * �@�`�F�b�N���X�g�e�[�u���A�\���ҏ��e�[�u���A�ƎҒS�����e�[�u������A
	 * �@�����Ŏw�肳��郌�R�[�h�����擾����B<br/><br/>
	 *
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		A.NENDO,			   -- �N�x
	 * 		A.KAISU,			   -- ��
	 * 		A.JIGYO_NAME,		   -- ���Ɩ�
	 * 		C.KAKUTEI_DATE, 	   -- �m���
	 * 		C.EDITION ALLEDITION,  -- �`�F�b�N���X�g�̔�
	 * 		B.BUNKASAIMOKU_CD,	   -- �זڔԍ�
	 * 		B.UKETUKE_NO,		   -- �\���ԍ�
	 * 		B.EDITION EDITION,	   -- �\�����̔�
	 * 		B.BUKYOKU_NAME, 	   -- ���ǖ�
	 * 		B.SHOKUSHU_NAME_KANJI, -- �E�햼(����)
	 * 		B.NAME_KANJI_SEI,	   -- �\���Ҏ����i������-���j
	 * 		B.NAME_KANJI_MEI,	   -- �\���Ҏ����i������-���j
	 * 		B.KADAI_NAME_KANJI,    -- �����ۑ薼(�a���j
	 * 		B.SHINSEISHA_ID,	   -- �\����ID
	 * 		B.SAKUSEI_DATE, 	   -- �\�����쐬��
	 * 		B.SYSTEM_NO,		   -- �V�X�e����t�ԍ�
	 * 		B.SHOZOKU_CD,		   -- �����@�փR�[�h
	 * 		A.JIGYO_ID			   -- ����ID
	 * FROM
	 * 		SHINSEIDATAKANRI B	   -- �\���f�[�^�Ǘ��e�[�u��
	 * INNER JOIN
	 * 		CHCKLISTINFO C		   -- �`�F�b�N���X�g���e�[�u��
	 * INNER JOIN
	 * 		JIGYOKANRI A		   -- ���ƊǗ��e�[�u��
	 * ON
	 * 		A.JIGYO_ID = C.JIGYO_ID
	 * ON
	 * 		C.JIGYO_ID = B.JIGYO_ID
	 * 		AND C.SHOZOKU_CD = B.SHOZOKU_CD
	 *
	 * ------�S�����ǊǗ��e�[�u���Ƀf�[�^�����݂���ꍇ�ɒǉ� --------
	 * INNER JOIN
	 * 		TANTOBUKYOKUKANRI TANTO -- �S�����ǊǗ��e�[�u��
	 * ON
	 * 		B.SHOZOKU_CD = TANTO.SHOZOKU_CD
	 * 		AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD
	 * 		AND TANTO.BUKYOKUTANTO_ID = ?
	 * -----------------------------------------------------------
	 *
	 * WHERE
	 * 		B.JOKYO_ID = C.JOKYO_ID
	 * 		AND B.DEL_FLG = 0
	 * 		AND A.DEL_FLG = 0
	 * 		AND B.JIGYO_KUBUN = 4
	 * <b><span style="color:#002288">-- ���I�������� --</span></b>
	 * ORDER BY
	 * 		B.BUNKASAIMOKU_CD
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������searchInfo�̕ϐ�syozokuCd</td></tr>
	 * </table><br/>
	 *
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>����CD</td><td>shozokuCd</td><td>AND C.SHOZOKU_CD = '����CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoCd</td><td>AND B.JIGYO_ID = '����ID'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>��ID</td><td>jokyoList</td><td>AND C.JOKYO_ID IN(��ID�̔z��)</td></tr>
	 * </table><br />
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo 	CheckListSearchInfo
	 * @return page	�y�[�W���
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#selectCheckList(UserInfo userInfo, CheckListSearchInfo info)
	 */
	public Page selectListData(
		UserInfo userInfo,
		CheckListSearchInfo searchInfo)
		throws ApplicationException {

		String select =
			"SELECT A.NENDO, "
				+ "A.KAISU, "
				+ "A.JIGYO_NAME, "
				+ "C.KAKUTEI_DATE, "
				+ "C.EDITION ALLEDITION, "
				+ "B.BUNKASAIMOKU_CD, "
// 20050712 �\�[�g�A�\���̉\�������邽�ߕ����ԍ���ǉ�
				+ "B.BUNKATSU_NO, "
				+ "B.JOKYO_ID, "
// Horikoshi
				+ "B.UKETUKE_NO, "
				+ "B.EDITION EDITION, "
			//2005/04/12 �폜 ��������--------------------------------------------------
			//���R �����̕\����COUNT��p���Ă��Ȃ�����
			//	+ "SUM(DECODE(C.JOKYO_ID, B.JOKYO_ID, 1, 0)) COUNT, "
			//�폜 �����܂�-------------------------------------------------------------
				+ "B.BUKYOKU_NAME, "
				+ "B.BUKYOKU_NAME_RYAKU, "
				+ "B.SHOKUSHU_NAME_KANJI, "
				+ "B.SHOKUSHU_NAME_RYAKU, "
				+ "B.NAME_KANJI_SEI, "
				+ "B.NAME_KANJI_MEI, "
				+ "B.KADAI_NAME_KANJI, "
				+ "B.SHINSEISHA_ID, "
				+ "B.SAKUSEI_DATE, "
			//2005/04/013 �ǉ� ��������-------------------------------------------------
			//���R �\�����\���p�ɕK�v�Ȃ���
				+ "B.SYSTEM_NO, "
				+ "B.SHOZOKU_CD, "
				+ "A.JIGYO_ID "
			//�ǉ� �����܂�-------------------------------------------------------------

// 20050721
				+ ",B.RYOIKI_NO," +
				" B.KOMOKU_NO," +
				" B.SHINSEI_KUBUN," +
				" B.KENKYU_KUBUN "
// Horikoshi

			+ "FROM SHINSEIDATAKANRI B "
			//2005/04/12 �ǉ� ��������--------------------------------------------------
			//���R INNER JOIN�̏��ԕύX�Ə����ǉ�
			//(JIGYOKANRI->CHECKLISTINFO �� CHECKLISTINFO->JIGYOKANRI�ɕύX���āA����CD�̏����ǉ�)
				+ "INNER JOIN CHCKLISTINFO C "
					+ "INNER JOIN JIGYOKANRI A "
					+ "ON A.JIGYO_ID = C.JIGYO_ID "
				+ "ON C.JIGYO_ID = B.JIGYO_ID "
				+ "AND B.SHOZOKU_CD = C.SHOZOKU_CD ";
			//�ǉ� �����܂�-------------------------------------------------------------
			//2005/04/11 �ǉ� ��������--------------------------------------------------
			//���R �S�����ǊǗ��e�[�u���Ƀf�[�^�����݂���ꍇ�͒S�����ǊǗ��̕��ǃR�[�h�������ɒǉ�
			if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
				select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
								+ "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
								+ "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
								//2005/04/20�@�ǉ� ��������------------------------------------------
								//���R �����s���̂���
								+ "AND TANTO.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
								//�ǉ� �����܂�------------------------------------------------------
			}
			//�ǉ� �����܂�-------------------------------------------------------------

			//2005.12.19 iso �w�U��t���ȍ~��S�ďo�͂���悤�ύX
//			select = select + "WHERE B.JOKYO_ID = C.JOKYO_ID "
//				+ "AND B.DEL_FLG = 0 "
			select = select + "WHERE B.DEL_FLG = 0 "
// 20050606 Start ���������Ɏ��Ƌ敪��ǉ��@������̈挤�����ǉ��ɂȂ������ߌ��������Ɏ��Ƌ敪���K�v�ɂȂ���
//				+ "AND A.DEL_FLG = 0 " + "AND B.JIGYO_KUBUN = 4 ";
				+ "AND A.DEL_FLG = 0 ";
				if(searchInfo.getJigyoKubun() != null){
					select = select + " AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + " ";
				}
				else{
					//TODO:���Ƌ敪�����݂��Ȃ������ꍇ�ɂ̓G���[�Ƃ���H�@���@�b��I�Ɋ�Ղ��w�肷��
					select = select + " AND B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN + " ";
				}
// Horikoshi End
		StringBuffer query = new StringBuffer(select);
		if (searchInfo.getShozokuCd() != null
			&& !searchInfo.getShozokuCd().equals("")) { //�����@�փR�[�h
			query.append(
				" AND C.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
					+ "'");
		}
		//2005.11.18 iso �����@�ւ������Ă����̂Œǉ�
		if(!StringUtil.isBlank(searchInfo.getShozokuName())){
			query.append(" AND B.SHOZOKU_NAME like '%")
				.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()))
				.append("%'");
		}

		if(searchInfo.getJigyoId() != null
			&& !searchInfo.getJigyoId().equals("")) { //����ID
			query.append(
				" AND B.JIGYO_ID = '"
				+ EscapeUtil.toSqlString(searchInfo.getJigyoId())
				+ "'");
		}
		if(searchInfo.getJigyoCd() != null
			&& !searchInfo.getJigyoCd().equals("")) { //���ƃR�[�h
			query.append(
				" AND SUBSTR(B.JIGYO_ID,3,5) = '"
				+ EscapeUtil.toSqlString(searchInfo.getJigyoCd())
				+ "'");
		}
		//2005/09/01 �Ɩ��S���҂̂Ƃ��͒S�����Ƃ������ɒǉ�����
		if(UserRole.GYOMUTANTO.equals(userInfo.getRole())){
			
			//UPDATE START 2007/07/23 BIS ����
			//Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
			
			List jigyoCdsKikanList;
			List tantoJigyoCdList=new ArrayList();
			String[] jigyoCdsKikan = {IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN,  //��Ռ���(C)(���)
                    IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU, //��Ռ���(C)(��撲��)
                    IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A, //��茤��(A)
                    IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B};//��茤��(B)
			jigyoCdsKikanList=Arrays.asList(jigyoCdsKikan);
			Iterator i=jigyoCdsKikanList.iterator();
			while(i.hasNext())
			{
				String s=(String)i.next();
				if( userInfo.getGyomutantoInfo().getTantoJigyoCd().contains(s))
				{
					tantoJigyoCdList.add(s);
				}
			}
			Iterator ite =tantoJigyoCdList.iterator();
			//UPDATE END 2007/07/23 BIS ����
			String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
			query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
				 .append(tantoJigyoCd)
				 .append(")")
				 .toString();
		}
		
		//2005/04/14 �ǉ� ��������--------------------------------------------------
		//���R �󗝏��擾�̂��ߒǉ�
		String[] jokyoList = searchInfo.getSearchJokyoId();
		if(jokyoList != null && jokyoList.length != 0){
			//2005.12.19 iso �w�U��t���ȍ~��S�ďo�͂���悤�ύX
//			query.append(" AND C.JOKYO_ID IN(")
			query.append(" AND B.JOKYO_ID IN(")
				.append(StringUtil.changeArray2CSV(jokyoList, true))
				.append(")");
		}
		//�ǉ� �����܂�-------------------------------------------------------------

// 20050721 �\�[�g���̕ύX
//		query.append(
//				//2005/04/12 �폜 ��������--------------------------------------------------
//				//���R �s�v�Ȃ��ߍ폜
//			/*	" GROUP BY A.NENDO, "
//				+ "A.KAISU, "
//				+ "A.JIGYO_NAME, "
//				+ "C.KAKUTEI_DATE, "
//				+ "C.EDITION, "
//				+ "B.BUNKASAIMOKU_CD, "
//				+ "B.UKETUKE_NO, "
//				+ "B.EDITION, "
//				+ "B.BUKYOKU_NAME, "
//				+ "B.SHOKUSHU_NAME_KANJI, "
//				+ "B.NAME_KANJI_SEI, "
//				+ "B.NAME_KANJI_MEI, "
//				+ "B.KADAI_NAME_KANJI, "
//				+ "B.SHINSEISHA_ID, "
//				+ "B.SAKUSEI_DATE, "
//				//2005/04/013 �ǉ� ��������-------------------------------------------------
//				//���R �\�����\���p��SYSTEM_NO���K�v�Ȃ���
//				+ "B.SYSTEM_NO "*/
//				//�ǉ� �����܂�-------------------------------------------------------------
//				 "ORDER BY B.BUNKASAIMOKU_CD");

		if(searchInfo.getJigyoKubun() != null &&
			searchInfo.getJigyoKubun() != "" &&
			searchInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){

			//���Ƌ敪������̈�̏ꍇ
			query.append("ORDER BY")
//					.append(" B.SHINSEI_KUBUN DESC")
//					.append(", B.KENKYU_KUBUN ASC")
//					.append(", B.RYOIKI_NO ASC")
//					.append(", B.CHOSEIHAN DESC")
//					.append(", B.KOMOKU_NO ASC")
//					.append(", B.KADAI_NO_KEIZOKU ASC ")
					.append(" B.SHINSEI_KUBUN DESC")
					.append(", B.KENKYU_KUBUN ASC")
					.append(", B.RYOIKI_NO ASC")
					.append(", B.KOMOKU_NO ASC")
					.append(", B.CHOSEIHAN DESC")
					.append(", B.UKETUKE_NO ASC ")
					;
		}
		else{
			//���̑�(��Վ���)�̏ꍇ
			query.append("ORDER BY")
					.append(" B.SHINSEI_KUBUN ASC")
					.append(", B.BUNKASAIMOKU_CD ASC")
// 20050826 �����ԍ���ǉ�
					.append(", B.BUNKATSU_NO ASC")
					.append(", B.UKETUKE_NO ASC ")
					;
			}
//		else{
//			query.append("ORDER BY B.BUNKASAIMOKU_CD");
//		}
// Horikoshi

		//�\�[�g���i�\����ID�̏����j
		//query.append(" ORDER BY A.KENKYU_NO");

		if (log.isDebugEnabled()) {
			log.debug("query:" + query);
		}

		//-----------------------
		// �y�[�W���擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(
				connection,
				searchInfo,
				query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�\�������擾����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}


	/**
	 * ��єԍ��\���p�̃f�[�^���擾����.<br/><br/>
	 *
	 * �@�`�F�b�N���X�g�e�[�u���A�\���ҏ��e�[�u���A�ƎҒS�����e�[�u������A
	 * �@�����Ŏw�肳��郌�R�[�h�����擾����B<br/><br/>
	 *
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		A.NENDO,				  -- �N�x
	 * 		A.KAISU,				  -- ��
	 * 		A.JIGYO_NAME,			  -- ���Ɩ�
	 * 		C.KAKUTEI_DATE, 		  -- �m���
	 * 		C.EDITION,				  -- ��
	 * 		B.UKETUKE_NO,			  -- �\���ԍ�
	 * 		B.JOKYO_ID, 			  -- ��ID
	 * 		B.DEL_FLG,				  -- �폜�t���O
	 * 		B.BUNKASAIMOKU_CD		  -- �זڔԍ�
	 * FROM
	 * 		SHINSEIDATAKANRI B		  -- �\���f�[�^�Ǘ��e�[�u��
	 * INNER JOIN
	 * 		CHCKLISTINFO C			  -- �`�F�b�N���X�g���e�[�u��
	 * INNER JOIN
	 * 		JIGYOKANRI A			  -- ���ƊǗ��e�[�u��
	 * ON
	 * 		A.JIGYO_ID = C.JIGYO_ID
	 * ON
	 * 		C.JIGYO_ID = B.JIGYO_ID
	 * 		AND C.SHOZOKU_CD = B.SHOZOKU_CD
	 * ------���ǒS���҂ŒS�����ǂ����ꍇ�ɒǉ�--------
	 * INNER JOIN
	 * 		TANTOBUKYOKUKANRI TANTO   -- �S�����ǊǗ��e�[�u��
	 * ON
	 * 		B.SHOZOKU_CD = TANTO.SHOZOKU_CD
	 * 		AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD
	 * 		AND TANTO.BUKYOKUTANTO_ID = ?
	 * -----------------------------------------------
	 * WHERE
	 * 		(B.DEL_FLG = 1 OR B.JOKYO_ID = '05')
	 * 		AND A.DEL_FLG = 0
	 * 		AND B.JIGYO_KUBUN = 4
	 * 		AND C.SHOZOKU_CD = ?
	 * 		AND B.JIGYO_ID = ?
	 * GROUP BY
	 * 		A.NENDO,
	 * 		A.KAISU,
	 * 		A.JIGYO_NAME,
	 * 		C.KAKUTEI_DATE,
	 * 		C.EDITION,
	 * 		B.UKETUKE_NO,
	 * 		B.JOKYO_ID,
	 * 		B.DEL_FLG,
	 * 		B.BUNKASAIMOKU_CD
	 * ORDER BY
	 * 		B.UKETUKE_NO
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>TANTO.BUKYOKUTANTO_ID</td><td>������userInfo�̕��ǒS���ҏ��bukyokutantoInfo�̕ϐ�bukyokutantoId</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>C.SHOZOKU_CD</td><td>������searchInfo�̕ϐ�syozokuCd</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>B.JIGYO_ID</td><td>������searchInfo�̕ϐ�jigyoId</td></tr>
	 * </table><br/><br/>
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo 	CheckListSearchInfo
	 * @return page	Page
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#selectTobiList(UserInfo userInfo, CheckListSearchInfo info)
	 */
	public Page selectTobiList(UserInfo userInfo,
	CheckListSearchInfo searchInfo)
	throws ApplicationException {

	String select =
		"SELECT A.NENDO, "
			+ "A.KAISU, "
			+ "A.JIGYO_NAME, "
			+ "C.KAKUTEI_DATE, "
			+ "C.EDITION, "
		//2005/04/12 �폜 ��������--------------------------------------------------
		//���R �����̕\����COUNT��p���Ă��Ȃ�����
		//	+ "SUM(DECODE(C.JOKYO_ID, B.JOKYO_ID, 1, 0)) COUNT, "
		//�폜 �����܂�-------------------------------------------------------------
			+ "B.UKETUKE_NO, "
			+ "B.JOKYO_ID, "
			+ "B.DEL_FLG, "
		//2005/04/21 �ǉ� ��������--------------------------------------------------
		//���R �זڔԍ��擾�̂��ߒǉ�
			+ "B.BUNKASAIMOKU_CD "
		//�ǉ� �����܂�-------------------------------------------------------------

			// �\�[�g����ǉ���������
			+ ", B.SHINSEISHA_ID,"
			+ " B.RYOIKI_NO,"
			+ " B.KOMOKU_NO, "
			+ " B.BUNKATSU_NO, "
			+ " B.NAME_KANJI_SEI, "
			+ " B.NAME_KANJI_MEI,"
			+ " B.SHINSEI_KUBUN,"
			+ " B.KENKYU_KUBUN "

			+ "FROM SHINSEIDATAKANRI B "
		//2005/04/12 �C�� ��������--------------------------------------------------
		//���R INNER JOIN�̏��ԕύX�Ə����ǉ�
		//(JIGYOKANRI->CHECKLISTINFO �� CHECKLISTINFO->JIGYOKANRI�ɕύX���āA����CD�̏����ǉ�)
			+ "INNER JOIN CHCKLISTINFO C "
				+ "INNER JOIN JIGYOKANRI A "
				+ "ON A.JIGYO_ID = C.JIGYO_ID "
			+ "ON C.JIGYO_ID = B.JIGYO_ID "
			+ "AND C.SHOZOKU_CD = B.SHOZOKU_CD ";
		//�C�� �����܂�-------------------------------------------------------------
		//2005/04/11 �ǉ� ��������--------------------------------------------------
		//���R �S�����ǊǗ��e�[�u���Ƀf�[�^�����݂���ꍇ�͒S�����ǊǗ��̕��ǃR�[�h�������ɒǉ�
		if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
			select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
							+ "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
							+ "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
							//2005/04/20�@�ǉ� ��������------------------------------------------
							//���R �����s���̂���
							+ "AND TANTO.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
							//�ǉ� �����܂�------------------------------------------------------
		}
		//�ǉ� �����܂�-------------------------------------------------------------
		
		//2005.10.18 iso PDF�ϊ��ŃG���[�ƂȂ����P�[�X��\��������悤�ύX
		//�󋵂́u01�v�����A�󂯕t��NO�����݂���P�[�X
//// 20050811 �ԈႢ�̂��ߑΏۃX�e�[�^�X���C�� 03��02
////		select = select + "WHERE (B.DEL_FLG = 1 OR B.JOKYO_ID = '05') "
//		select = select + "WHERE (B.DEL_FLG = 1 OR B.JOKYO_ID = '02' OR B.JOKYO_ID = '05') "
//// Horikoshi
//		+" AND A.DEL_FLG = 0 "
//
//// 20050606 ���������Ɏ��Ƌ敪��ǉ��@������̈挤���̒ǉ��̂��ߏ����Ɏ��Ƌ敪���Z�b�g
//		+ "AND B.JIGYO_KUBUN = " + searchInfo.getJigyoKubun() + "  "
//		//��єԍ��ɂȂ�Ȃ��̂Ŏ�t�ԍ����Ȃ����̂͑ΏۂƂ��Ȃ�
//		+  "AND B.UKETUKE_NO IS NOT NULL ";
//// Horikoshi
		select = select
				+ "WHERE (B.DEL_FLG = 1 OR B.JOKYO_ID = '01' OR B.JOKYO_ID = '02' OR B.JOKYO_ID = '05') "
				+" AND A.DEL_FLG = 0 "
				+ "AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "  "
				+ "AND B.UKETUKE_NO IS NOT NULL ";
		
	StringBuffer query = new StringBuffer(select);
	if (searchInfo.getShozokuCd() != null
		&& !searchInfo.getShozokuCd().equals("")) { //�����@�փR�[�h
		query.append(
			" AND C.SHOZOKU_CD = '"
				+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
				+ "'");
	}
	if(searchInfo.getJigyoId() != null
		&& !searchInfo.getJigyoId().equals("")){	//����ID
		query.append(
			" AND B.JIGYO_ID = '"
			+ EscapeUtil.toSqlString(searchInfo.getJigyoId())
			+ "'");
	}
	query.append(
			" GROUP BY A.NENDO, "
			+ "A.KAISU, "
			+ "A.JIGYO_NAME, "
			+ "C.KAKUTEI_DATE, "
			+ "C.EDITION, "
			+ "B.UKETUKE_NO, "
			+ "B.JOKYO_ID, "
			+ "B.DEL_FLG, "
// �\�[�g���ǉ��̂���
			+ "B.SHINSEISHA_ID, "
			+ "B.SHINSEI_KUBUN, "
			+ "B.KENKYU_KUBUN, "
			+ "B.RYOIKI_NO, "
			+ "B.KOMOKU_NO, "
			+ "B.CHOSEIHAN, "
			+ "B.BUNKATSU_NO, "
			+ "B.NAME_KANJI_SEI, "
			+ "B.NAME_KANJI_MEI, "

			+ "B.BUNKASAIMOKU_CD "
			);

// 20050826 �\�[�g����ǉ�
	if(searchInfo.getJigyoKubun() != null &&
			searchInfo.getJigyoKubun() != "" &&
			searchInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){
			//���Ƌ敪������̈�̏ꍇ
			query.append("ORDER BY")
					.append(" B.SHINSEI_KUBUN DESC")
					.append(", B.KENKYU_KUBUN ASC")
					.append(", B.RYOIKI_NO ASC")
					.append(", B.KOMOKU_NO ASC")
					.append(", B.CHOSEIHAN DESC")
					.append(", B.UKETUKE_NO ASC ")
					;
		}
		else{
			//���̑�(��Վ���)�̏ꍇ
			query.append("ORDER BY")
					.append(" B.SHINSEI_KUBUN ASC")
					.append(", B.BUNKASAIMOKU_CD ASC")
// 20050826 �����ԍ���ǉ�
					.append(", B.BUNKATSU_NO ASC")
					.append(", B.UKETUKE_NO ASC ")
					;
		}

	    //�\�[�g���i�\����ID�̏����j
	    //query.append(" ORDER BY A.KENKYU_NO");

	    if (log.isDebugEnabled()) {
		    log.debug("query:" + query);
	    }

		//-----------------------
		// �y�[�W���擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(
				connection,
				searchInfo,
				query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�\�������擾����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}


	//2005/04/11 �ǉ� ��������--------------------------------------------------
	//�L�������`�F�b�N�̂��ߒǉ�
	/**
	 * �`�F�b�N���X�g�̏�񂪊w�U��t�����ȓ��ł��邩���m�F����.<br/><br/>
	 *
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		COUNT(*) COUNT				-- �L���������f�[�^����
	 * FROM
	 * 		SHINSEIDATAKANRI DATA		-- �\���f�[�^�Ǘ��e�[�u��
	 * INNER JOIN
	 * 		CHCKLISTINFO CHECKLIST		-- �`�F�b�N���X�g���e�[�u��
	 * INNER JOIN
	 * 		JIGYOKANRI JIGYO			-- ���ƊǗ��e�[�u��
	 * ON
	 * 		JIGYO.JIGYO_ID = CHECKLIST.JIGYO_ID
	 * ON
	 * 		CHECKLIST.JIGYO_ID = DATA.JIGYO_ID
	 * 		AND CHECKLIST.SHOZOKU_CD = DATA.SHOZOKU_CD
	 * WHERE
	 * 		DATA.JOKYO_ID = CHECKLIST.JOKYO_ID
	 * 		AND DATA.DEL_FLG = 0
	 * 		AND JIGYO.DEL_FLG = 0
	 * 		AND DATA.JIGYO_KUBUN = 4
	 * 		AND SYSDATE
	 * 		  BETWEEN JIGYO.UKETUKEKIKAN_START
	 * 		  AND JIGYO.UKETUKEKIKAN_END
	 *		AND CHECKLIST.SHOZOKU_CD = ?
	 * 		AND DATA.JIGYO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������searchInfo�̕ϐ�syozokuCd</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������searchInfo�̕ϐ�jigyoId</td></tr>
	 * </table><br/><br/>
	 *
	 * SQL�̌��ʂ̌�����0���̏ꍇ�͗L���������̒l���Ȃ��Ƃ���false��Ԃ��B<BR>
	 * ������1���ȏ゠��ꍇ��true��Ԃ��B
	 *
	 * @param userInfo		UserInfo
	 * @param searchInfo CheckListSearchInfo
	 * @return boolean		dateFlag
	 */
	public boolean checkLimitDate(UserInfo userInfo,
		CheckListSearchInfo searchInfo)
		throws ApplicationException {

		int dateCheck = 0;
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			CheckListInfoDao dao = new CheckListInfoDao(userInfo);

			dateCheck = dao.checkDate(connection, searchInfo);

			if(dateCheck != 0){
				return true;
			}
		} catch (DataAccessException e) {

			throw new ApplicationException(
				"�w�U��t���Ԋm�F����DB�G���[���������܂���",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
				DatabaseUtil.closeConnection(connection);
		}

		return false;
	}

	//�ǉ� �����܂�-------------------------------------------------------------


	//2005/04/14 �ǉ���������---------------------------------------------------
	//�ꊇ�󗝃��\�b�h��ǉ�
	/**
	 * �ꊇ�󗝓o�^���s��.<br>
	 *
	 * �ȉ���1,2�̏�����z��Ɋi�[���ꂽ����CD�̌����s���B<BR><br>
	 *
	 * <b>1.�`�F�b�N���X�g�e�[�u���̍X�V</b><br/>
	 * ����ID�Ə���CD����������������𐶐����A���̕�����HashSet�Ɋi�[����Ă��Ȃ��ꍇ�̂ݏ������s���B<BR>
	 * HashSet�ɕ����񂪊i�[����Ă��Ȃ��ꍇ�ɁAHashSet�ɐ���������������i�[����<BR>
	 * �ȉ���SQL�����s���A�`�F�b�N���X�g�̏�ID�̍X�V���s���B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 * 		CHCKLISTINFO	   -- �`�F�b�N���X�g���e�[�u��
	 * SET
	 * 		JOKYO_ID = ?
	 * WHERE
	 * 		SHOZOKU_CD = ?
	 * 		AND JIGYO_ID = ?
	 * 		AND JOKYO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * �ύX�l
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>06(�w�U��)</td></tr>
	 * </table><br/>
	 * �i���ݏ���
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������shozokuArray��i�Ԗڂ̒l(i�͌J��Ԃ���)</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>��O����jigyoArray��i�Ԗڂ̒l(i�͌J��Ԃ���)</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>JOKYO_ID</td><td>04(�w�U��t��)</td></tr>
	 * </table><br/><br/>
	 *
	 * <b>2.�\�����e�[�u���̎�</b><br/>
	 * ShinseiMaintenance�N���X��registShinseiJuri���\�b�h���Ăяo���āA
	 * �\�����̎󗝏����ƐR�����ʃe�[�u���ւ̃f�[�^�ǉ����s���B<BR>
	 *
	 * @param userInfo 	UserInfo	���[�U���
	 * @param shozokuArray	List		�����@��CD�̔z��
	 * @param jigyoArray	List		����ID�̔z��
	 * @param systemArray	List		�V�X�e����t�ԍ��̔z��
	 * @throws ApplicationException
	 */
// 20050721
//	public void juriAll(UserInfo userInfo, List shozokuArray, List jigyoArray, List systemArray)
	public void juriAll(UserInfo userInfo, List shozokuArray, List jigyoArray, List systemArray, String comment)
// Horikoshi
		throws ApplicationException {

		boolean success = false;
		ShinseiMaintenance shinsei = new ShinseiMaintenance();
		ShinseiDataPk shinseiPk = new ShinseiDataPk();
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		Connection connection = null;
		CheckListInfoDao dao = new CheckListInfoDao(userInfo);
		HashSet set = new HashSet();
		String data = null;
// 2007/02/08 ���u�j�@�ǉ��������� 
        //���O�̏o�͗p
        StringBuffer logBuffer = new StringBuffer();
// 2007/02/08�@���u�j�@�ǉ������܂�
        
		//�X�V�p��ID���Z�b�g
// 2006/06/20 dyh update start �����FCheckListSearchInfo�萔��ύX
//		checkInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);
//		checkInfo.setChangeJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);
        checkInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);
        checkInfo.setChangeJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);
// 2006/06/20 dyh update end

		try{
// 2007/02/08 ���u�j�@�ǉ���������            
            /** ���O �X�V�O  (����ID�A�@�փR�[�h�̏��ŁC����ID���ɉ��s��ǉ����ďo�͂���)*/
            TreeSet treeSet = new TreeSet();
            for(int i = 0; i < shozokuArray.size(); i++){
                //����ID�Ə���CD(CHCKLISTINFO�e�[�u���̎�L�[)�𕶎���ɂ���
                data = (String)jigyoArray.get(i) + (String)shozokuArray.get(i);
                //���Ɏ�L�[������HashSet�Ɋi�[����Ă���ꍇ�̓`�F�b�N���X�g�̍X�V�͍s��Ȃ�
                if(!treeSet.contains(data)){
                    //HashSet�Ɏ�L�[��������i�[
                    treeSet.add(data);
                }
            }
            Object[] tempArray = treeSet.toArray();           
 
//2007/03/13 �c�@�폜��������            
//            int x, y;
//            String temp = "";
//            for (x = tempArray.length - 1; x > 0; x--) {
//                for (y = 0; y < x; y++) {
//                    if (Long.parseLong(tempArray[y].toString()) > Long
//                            .parseLong(tempArray[y + 1].toString())) {
//                        temp = tempArray[y].toString();
//                        tempArray[y] = tempArray[y + 1];
//                        tempArray[y + 1] = temp;
//                    }
//                }
//            }
//2007/03/13 �c�@�폜�����܂�
            //���O�̕ҏW
            for (int n = 0; n < tempArray.length; n++) {
                String jigyoId = tempArray[n].toString().substring(0, 7);
                String beforeJigyoId = "";
                if (n > 0) {
                    beforeJigyoId = tempArray[n - 1].toString().substring(0, 7);
                }

                if (!jigyoId.equals(beforeJigyoId)) {
                    if (!StringUtil.isBlank(beforeJigyoId)) {
                        logBuffer.append(")");
                        logBuffer.append(" \n ");
                    }
                    logBuffer.append(" ����ID :" + jigyoId);
                    logBuffer.append(" , �@�փR�[�h(");
                    logBuffer.append(tempArray[n].toString().substring(8, 13));
                } else {
                    logBuffer.append(",");
                    logBuffer.append(tempArray[n].toString().substring(8, 13));
                }
                
                if (n == tempArray.length -1){
                    logBuffer.append(")"); 
                }
            }
            //���O�̏o��
          statusLog.info( " �`�F�b�N���X�g�ꊇ�󗝑O , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
          + " ,  " + logBuffer.toString() );
            //ADD START 2007/07/20 BIS ����
             juriLog.info( "�`�F�b�N���X�g�ꊇ�󗝂��J�n���܂��� , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                    + " ,  " + logBuffer.toString() );   
            //ADD END 2007/07/20 BIS ����
            data = "";
// 2007/02/08�@���u�j�@�ǉ������܂�         
			connection = DatabaseUtil.getConnection();
			//�f�[�^�̌����󗝓o�^���s��
			for(int i = 0; i < shozokuArray.size(); i++){                
               	if(i % 1000 == 0) {
					log.info("jigyoId=" + jigyoArray.get(i) + " ������::" + i + "��");
               	}
					
					//ADD START 2007/07/20 BIS ����
	            	//���x�����J�X�^�}�C�Y
	            	//�E1,000���P�ʂœr���o�߂̃��O���o�͂���B
					//�V�X�e������, "�`�F�b�N���X�g�ꊇ�󗝂��������i1000���j�ł�", ���[�U���, ���O�C��ID, ������IDn, �@�փR�[�hn���i���j
               	if(0==(i%1000)&0!=i)
            	{	
               		juriLog.info(" �`�F�b�N���X�g�ꊇ�󗝂��������i"+i+"���j�ł� , ���[�U��� : " + userInfo.getRole()
                            + " , ���O�C��ID : " + userInfo.getId()+ " ,  " + logBuffer.toString() );
            	}
					//ADD END 2007/07/20 BIS ����
					
					
				
				
				//����ID�Ə���CD(CHCKLISTINFO�e�[�u���̎�L�[)�𕶎���ɂ���
				data = (String)jigyoArray.get(i) + (String)shozokuArray.get(i);
				//���Ɏ�L�[������HashSet�Ɋi�[����Ă���ꍇ�̓`�F�b�N���X�g�̍X�V�͍s��Ȃ�
				if(!set.contains(data)){
					//HashSet�Ɏ�L�[��������i�[
					set.add(data);
					//��ID�X�V
					checkInfo.setJigyoId((String)jigyoArray.get(i));
					checkInfo.setShozokuCd((String)shozokuArray.get(i));
					//�`�F�b�N���X�g�̍X�V
					dao.updateCheckListInfo(connection, checkInfo, true);
				}
				//�\�����̎󗝏���
				shinseiPk.setSystemNo((String)systemArray.get(i));
				shinsei.registShinseiJuri(userInfo, shinseiPk, null, comment, null);
			}
			log.info("jigyoId=" + jigyoArray.get(shozokuArray.size()-1) + "�`"  + jigyoArray.get(shozokuArray.size()-1) + " ������::����");
            success = true;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"��ID�X�V����DB�G���[���������܂���",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
// 2007/02/08 ���u�j�@�ǉ���������
                     /** ���O �X�V�� */  
                  statusLog.info( " �`�F�b�N���X�g�ꊇ�󗝌� , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                  + " ,  " + logBuffer.toString() );
                  //ADD START 2007/07/20 BIS ����
                  juriLog.info( " �`�F�b�N���X�g�ꊇ�󗝂�����ɏI�����܂���, ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                            + " ,  " + logBuffer.toString() ); 
                  //ADD END 2007/07/20 BIS ����
// 2007/02/08�@���u�j�@�ǉ������܂� 
				} else {
					DatabaseUtil.rollback(connection);
//2007/02/08 ���u�j�@�ǉ���������
                    /** ���O �X�V���s */
                  statusLog.info( " �`�F�b�N���X�g�ꊇ�󗝎��s , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                  + " ,  " + logBuffer.toString() ); 
                  //ADD START 2007/07/20 BIS ����
                   juriLog.info( " �`�F�b�N���X�g�ꊇ�󗝂Ɏ��s���܂��� , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                           + " ,  " + logBuffer.toString() ); 
                   //ADD START 2007/07/20 BIS ����
//2007/02/08�@���u�j�@�ǉ������܂� 
                    
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"��ID�X�V����DB�G���[���������܂���",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}


	//�ǉ������܂�---------------------------------------------------------

	//	2005/04/24 �ǉ� ��������----------
	//	���R:�`�F�b�N���X�gCSV�o�͒ǉ��̂���

	/**
	 * �`�F�b�N���X�gCSV�o�͗p��List�쐬.<br />
	 *
	 * SelectUtil�N���X��selectCsvList���\�b�h���Ăяo���Ĉȉ���SQL�����s���ACSV�f�[�^���X�g�𐶐�����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj<BR>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *		B.JIGYO_ID \"����ID\",							-- ����ID
	 *		B.NENDO \"�N�x\",								-- �N�x
	 *		B.KAISU \"��\",								-- ��
	 *		B.JIGYO_NAME \"���Ɩ�\",						-- ���Ɩ�
	 *		B.BUNKASAIMOKU_CD \"�זڔԍ�\", 				-- �����ԍ�
	 *		B.SHOZOKU_CD \"�@�֔ԍ�\",						-- �@�֔ԍ�
	 *		SUBSTR(B.UKETUKE_NO,7) \"�����ԍ�\",			-- �����ԍ�
	 *		REPLACE(B.EDITION,'0','-') \"��\",				-- ��
	 *		B.BUKYOKU_CD \"���ǃR�[�h\",					-- ���ǃR�[�h
	 *		B.BUKYOKU_NAME \"���ǖ�\",						-- ���ǖ�
	 *		B.SHOKUSHU_CD \"�E�R�[�h\", 					-- �E�R�[�h
	 *		B.SHOKUSHU_NAME_KANJI \"�E��\", 				-- �E��
	 *		B.NAME_KANJI_SEI \"�����i������-���j\", 		 -- �����i������-���j
	 *		B.NAME_KANJI_MEI \"�����i������-���j\", 		 -- �����i������-���j
	 *		B.NAME_KANA_SEI \"�����i�t���K�i-���j\",		 -- �����i�t���K�i-���j
	 *		B.NAME_KANA_MEI \"�����i�t���K�i-���j\" 		 -- �����i�t���K�i-���j
	 * FROM
	 * 		SHINSEIDATAKANRI B
	 * INNER JOIN
	 * 		CHCKLISTINFO C
	 * INNER JOIN
	 * 		JIGYOKANRI A
	 * ON
	 * 		A.JIGYO_ID = C.JIGYO_ID
	 * ON
	 * 		C.JIGYO_ID = B.JIGYO_ID "
	 * 		AND B.SHOZOKU_CD = C.SHOZOKU_CD
	 * INNER JOIN TANTOBUKYOKUKANRI TANTO
	 * ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD
	 * 		AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD
	 * 		AND TANTO.BUKYOKUTANTO_ID = ?
	 * WHERE B.JOKYO_ID = C.JOKYO_ID
	 * 		AND B.DEL_FLG = 0
	 * 		AND A.DEL_FLG = 0
	 * 		AND B.JIGYO_KUBUN = 4
	 * <b><span style="color:#002288">-- ���I�������� --</span></b></pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>����CD</td><td>shozokuCd</td><td>AND C.SHOZOKU_CD = '����CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoCd</td><td>AND B.JIGYO_ID = '����ID'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>��ID</td><td>jokyoList</td><td>AND C.JOKYO_ID IN(��ID�̔z��)</td></tr>
	 * </table><br />
	 *
	 * @param userInfo		���[�U���
	 * @param searchInfo	��������
	 * @return				CSV�o�͗pLIST
	 * @throws ApplicationException
	 */
	public List searchCsvData(UserInfo userInfo, CheckListSearchInfo searchInfo)
	throws ApplicationException {
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------

		String select =
			"SELECT "
			+ "ROWNUM \"�`�F�b�N���X�g�\����\", "
			+ "JIGYO_NAME \"���Ɩ�\", "
			+ "SHINSEI_KUBUN \"�V�K�E�p���敪\", "
			+ "KADAI_NO_KEIZOKU \"�ۑ�ԍ�\", "
			//DEL START 2007/07/04 BIS ����
			//H19���S�d�q���y�ѐ��x����
			//�`�F�b�N���X�g�������̈�p���ڂ��폜
			//+ "KENKYU_KUBUN \"�v��E����E�I���敪\", "
			//DEL END 2007/07/04 BIS ����
			+ "SHOZOKU_CD \"�@�֔ԍ�\", "
			+ "SUBSTR(JIGYO_ID,5,2) \"������ڔԍ�\", "
			+ "SHINSA_KUBUN \"�R���敪�ԍ�\", "
			+ "BUNKASAIMOKU_CD \"�זڔԍ�\", "
			+ "BUNKATSU_NO \"�����ԍ�\", "
			
			
			//DEL START 2007/07/04 BIS ����
			//H19���S�d�q���y�ѐ��x����
			//�`�F�b�N���X�g�������̈�p���ڂ��폜
			//+ "RYOIKI_NO \"�̈�ԍ�\", "
			//+ "KOMOKU_NO \"�������ڔԍ�\", "
			//+ "OHABAHENKO \"�啝�ȕύX�𔺂������ۑ�\", "
			//+ "CHOSEIHAN \"������\", "
			//+ "RYOIKI_RYAKU \"�̈旪�̖�\", "
			//DEL END 2007/07/04 BIS ����
			+ "SUBSTR(UKETUKE_NO,7) \"�����ԍ�\", "
			+ "EDITION \"��\", "
			+ "BUKYOKU_CD \"���ǔԍ�\", "
			+ "BUKYOKU_NAME \"���ǖ�\", "
			+ "SHOKUSHU_CD \"�E�ԍ�\", "

			+ "SHOKUSHU_NAME_KANJI \"�E��\", "
			+ "NAME_KANA_SEI \"�����i�t���K�i�]���j\", "
			+ "NAME_KANA_MEI \"�����i�t���K�i�]���j\", "
			+ "NAME_KANJI_SEI \"�����i�������]���j\", "
			+ "NAME_KANJI_MEI \"�����i�������]���j\", "
			+ "NENREI \"�N��\", "
			+ "KENKYU_NO \"����Ҍ����Ҕԍ�\", "
			+ "SHOZOKU_NAME \"���������@�֖�\", "
			+ "ZIP \"�X�֔ԍ�\", "

			+ "ADDRESS \"�Z��\", "
			+ "TEL \"TEL\", "
			+ "FAX \"FAX\", "
			+ "EMAIL \"Email\", "
			+ "KADAI_NAME_KANJI \"�����ۑ薼\", "
			+ "BUNYA_NAME \"����\", "
			+ "BUNKA_NAME \"����\", "
			+ "SAIMOKU_NAME \"�ז�\", "
			+ "SAIMOKU_KEYWORD \"�זڕ\�t�\�L�[���[�h\", "
			+ "OTHER_KEYWORD \"�זڕ\�ȊO�̃L�[���[�h\", "

			+ "KENKYU_TAISHO \"�ތ^\", "
			+ "KEIHI1 \"1�N�ڌ����o��\", "
			+ "BIHINHI1 \"1�N�ڐݔ����i��\", "
			+ "SHOMOHINHI1 \"1�N�ڏ��Օi��\", "
			+ "RYOHI1 \"1�N�ڗ���\", "
			+ "SHAKIN1 \"1�N�ڎӋ���\", "
			+ "SONOTA1 \"1�N�ڂ��̑�\", "
			+ "KEIHI2 \"2�N�ڌ����o��\", "
			+ "BIHINHI2 \"2�N�ڐݔ����i��\", "
			+ "SHOMOHINHI2 \"2�N�ڏ��Օi��\", "

			+ "RYOHI2 \"2�N�ڗ���\", "
			+ "SHAKIN2 \"2�N�ڎӋ���\", "
			+ "SONOTA2 \"2�N�ڂ��̑�\", "
			+ "KEIHI3 \"3�N�ڌ����o��\", "
			+ "BIHINHI3 \"3�N�ڐݔ����i��\", "
			+ "SHOMOHINHI3 \"3�N�ڏ��Օi��\", "
			+ "RYOHI3 \"3�N�ڗ���\", "
			+ "SHAKIN3 \"3�N�ڎӋ���\", "
			+ "SONOTA3 \"3�N�ڂ��̑�\", "
			+ "KEIHI4 \"4�N�ڌ����o��\", "

			+ "BIHINHI4 \"4�N�ڐݔ����i��\", "
			+ "SHOMOHINHI4 \"4�N�ڏ��Օi��\", "
			+ "RYOHI4 \"4�N�ڗ���\", "
			+ "SHAKIN4 \"4�N�ڎӋ���\", "
			+ "SONOTA4 \"4�N�ڂ��̑�\", "
			+ "KEIHI5 \"5�N�ڌ����o��\", "
			+ "BIHINHI5 \"5�N�ڐݔ����i��\", "
			+ "SHOMOHINHI5 \"5�N�ڏ��Օi��\", "
			+ "RYOHI5 \"5�N�ڗ���\", "
			+ "SHAKIN5 \"5�N�ڎӋ���\", "

			+ "SONOTA5 \"5�N�ڂ��̑�\", "
			+ "KEIHI_TOTAL \"���v�����o��\", "
			+ "BIHINHI_TOTAL \"���v�ݔ����i��\", "
			+ "SHOMOHINHI_TOTAL \"���v���Օi��\", "
			+ "RYOHI_TOTAL \"���v����\", "
			+ "SHAKIN_TOTAL \"���v�Ӌ���\", "
			+ "SONOTA_TOTAL \"���v���̑�\", "
			+ "BUNTANKIN_FLG \"���S���̔z��\", "
			+ "KAIJIKIBO_FLG \"�J����]�̗L��\", "
			+ "KENKYU_NINZU \"�����Ґ�\", "

// 2007/03/02  ���u�j�@�C�� ��������
            //+ "TAKIKAN_NINZU \"�������@�ւ̕��S�Ґ�\", "
            + "TAKIKAN_NINZU \"���@�ւ̕��S�Ґ�\", "
// 2007/03/02  ���u�j�@�C�� �����܂�
			+ "SHINSEI_FLG_NO \"�����v��ŏI�N�x�O�N�x�̉���\", "
			+ "KADAI_NO_SAISYU \"�ŏI�N�x�ۑ�ԍ�\", "
// 2007/02/14  ���u�j�@�폜��������
//          //ADD START DYH 2006/2/15
//			+ "TO_CHAR(SAIYO_DATE, 'YYYY/MM/DD') \"�̗p�N����\","
//			+ "KINMU_HOUR \"�Ζ����Ԑ�\","
//			+ "NAIYAKUGAKU \"���ʌ�������������z\","
//			+ "OUBO_SHIKAKU \"���厑�i\","
//			+ "TO_CHAR(SIKAKU_DATE, 'YYYY/MM/DD') \"���i�擾�N����\","
//			+ "SYUTOKUMAE_KIKAN \"���i�擾�O�@�֖�\","
//			+ "TO_CHAR(IKUKYU_START_DATE, 'YYYY/MM/DD') \"��x���J�n��\","
//			+ "TO_CHAR(IKUKYU_END_DATE, 'YYYY/MM/DD') \"��x���I����\","
//           //ADD END DYH 2006/2/15
// 2007/02/14�@���u�j�@�폜�����܂�
			+ "TO_CHAR(SAKUSEI_DATE, 'YYYY/MM/DD') \"�쐬��\" "



			+ "FROM (SELECT "
			+ "B.JIGYO_NAME, "
			+ "B.SHINSEI_KUBUN, "
			+ "B.KADAI_NO_KEIZOKU, "
			+ "B.KENKYU_KUBUN, "
			+ "B.JIGYO_ID, "
			+ "B.SHINSA_KUBUN, "
			+ "B.BUNKASAIMOKU_CD, "
			+ "B.BUNKATSU_NO, "

			+ "B.RYOIKI_NO, "
			+ "B.KOMOKU_NO, "
			+ "B.OHABAHENKO, "
			+ "B.CHOSEIHAN, "
			+ "B.RYOIKI_RYAKU, "
			+ "B.UKETUKE_NO, "
			+ "B.EDITION, "
			+ "B.BUKYOKU_CD, "
			+ "B.BUKYOKU_NAME, "
			+ "B.SHOKUSHU_CD, "

			+ "B.SHOKUSHU_NAME_KANJI, "
			+ "B.NAME_KANJI_SEI, "
			+ "B.NAME_KANJI_MEI, "
			+ "B.NAME_KANA_SEI, "
			+ "B.NAME_KANA_MEI, "
			+ "B.NENREI, "
			+ "B.KENKYU_NO, "
			+ "B.SHOZOKU_CD, "
			+ "B.SHOZOKU_NAME, "
			+ "B.ZIP, "

			+ "B.ADDRESS, "
			+ "B.TEL, "
			+ "B.FAX, "
			+ "B.EMAIL, "
			+ "B.KADAI_NAME_KANJI, "
			+ "B.BUNYA_NAME, "
			+ "B.BUNKA_NAME, "
			+ "B.SAIMOKU_NAME, "
			+ "B.SAIMOKU_KEYWORD, "
			+ "B.OTHER_KEYWORD, "

			+ "B.KENKYU_TAISHO, "
			+ "B.KEIHI1, "
			+ "B.BIHINHI1, "
			+ "B.SHOMOHINHI1, "
			+ "B.RYOHI1, "
			+ "B.SHAKIN1, "
			+ "B.SONOTA1, "
			+ "B.KEIHI2, "
			+ "B.BIHINHI2, "
			+ "B.SHOMOHINHI2, "

			+ "B.RYOHI2, "
			+ "B.SHAKIN2, "
			+ "B.SONOTA2, "
			+ "B.KEIHI3, "
			+ "B.BIHINHI3, "
			+ "B.SHOMOHINHI3, "
			+ "B.RYOHI3, "
			+ "B.SHAKIN3, "
			+ "B.SONOTA3, "
			+ "B.KEIHI4, "

			+ "B.BIHINHI4, "
			+ "B.SHOMOHINHI4, "
			+ "B.RYOHI4, "
			+ "B.SHAKIN4, "
			+ "B.SONOTA4, "
			+ "B.KEIHI5, "
			+ "B.BIHINHI5, "
			+ "B.SHOMOHINHI5, "
			+ "B.RYOHI5, "
			+ "B.SHAKIN5, "

			+ "B.SONOTA5, "
			+ "B.KEIHI_TOTAL, "
			+ "B.BIHINHI_TOTAL, "
			+ "B.SHOMOHINHI_TOTAL, "
			+ "B.RYOHI_TOTAL, "
			+ "B.SHAKIN_TOTAL, "
			+ "B.SONOTA_TOTAL, "
			+ "B.BUNTANKIN_FLG, "
			+ "B.KAIJIKIBO_FLG, "
			+ "B.KENKYU_NINZU, "

			+ "B.TAKIKAN_NINZU, "
			+ "B.SHINSEI_FLG_NO, "
			+ "B.KADAI_NO_SAISYU, "
//2007/02/14  ���u�j�@�폜��������
////add start dyh 2006/2/15
//			+ "B.SAIYO_DATE,"
//			+ "B.KINMU_HOUR,"
//			+ "B.NAIYAKUGAKU,"
////2006/05/12 �ǉ���������            
////			+ "B.OUBO_SHIKAKU,"
//            + "CASE WHEN B.JIGYO_KUBUN = "
//            + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI
//            + "THEN B.OUBO_SHIKAKU ELSE ' ' END OUBO_SHIKAKU,"
////�c�@�ǉ������܂�            
//			+ "B.SIKAKU_DATE,"
//			+ "B.SYUTOKUMAE_KIKAN,"
//			+ "B.IKUKYU_START_DATE,"
//			+ "B.IKUKYU_END_DATE,"
////add end dyh 2006/2/15
//2007/02/14�@���u�j�@�폜�����܂�
			+ "B.SAKUSEI_DATE "

				+ "FROM SHINSEIDATAKANRI B "
				//2005/04/12 �ǉ� ��������--------------------------------------------------
				//���R INNER JOIN�̏��ԕύX�Ə����ǉ�
				//(JIGYOKANRI->CHECKLISTINFO �� CHECKLISTINFO->JIGYOKANRI�ɕύX���āA����CD�̏����ǉ�)
					+ "INNER JOIN CHCKLISTINFO C "
						+ "INNER JOIN JIGYOKANRI A "
						+ "ON A.JIGYO_ID = C.JIGYO_ID "
					+ "ON C.JIGYO_ID = B.JIGYO_ID "
					+ "AND B.SHOZOKU_CD = C.SHOZOKU_CD ";
				//�ǉ� �����܂�-------------------------------------------------------------
				//2005/04/11 �ǉ� ��������--------------------------------------------------
				//���R �S�����ǊǗ��e�[�u���Ƀf�[�^�����݂���ꍇ�͒S�����ǊǗ��̕��ǃR�[�h�������ɒǉ�
				if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
					select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
									+ "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
									+ "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
									//2005/04/20�@�ǉ� ��������------------------------------------------
									//���R �����s���̂���
									+ "AND TANTO.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
									//�ǉ� �����܂�------------------------------------------------------
				}
				//�ǉ� �����܂�-------------------------------------------------------------

				//2005.12.19 iso �w�U��t���ȍ~��S�ďo�͂���悤�ύX
//				select = select + "WHERE B.JOKYO_ID = C.JOKYO_ID "
//				+ "AND B.DEL_FLG = 0 "
				select = select + "WHERE B.DEL_FLG = 0 "
					
					+ "AND A.DEL_FLG = 0 "

// 20050606 Start ���������Ɏ��Ƌ敪��ǉ��@������̈挤���̒ǉ��̂��ߏ����Ɏ��Ƌ敪���Z�b�g
					+ "AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "  ";
// Horikoshi End

			StringBuffer query = new StringBuffer(select);
			if (searchInfo.getShozokuCd() != null
				&& !searchInfo.getShozokuCd().equals("")) { //�����@�փR�[�h
				query.append(
					" AND C.SHOZOKU_CD = '"
						+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
						+ "'");
			}
			if(searchInfo.getJigyoId() != null
				&& !searchInfo.getJigyoId().equals("")) { //���ƃR�[�h
				query.append(
					" AND B.JIGYO_ID = '"
					+ EscapeUtil.toSqlString(searchInfo.getJigyoId())
					+ "'");
			}
			//2005/04/14 �ǉ� ��������--------------------------------------------------
			//���R �󗝏��擾�̂��ߒǉ�
			String[] jokyoList = searchInfo.getSearchJokyoId();
			if(jokyoList != null && jokyoList.length != 0){
				//2005.12.19 iso �w�U��t���ȍ~��S�ďo�͂���悤�ύX
//				query.append(" AND C.JOKYO_ID IN(")
				query.append(" AND B.JOKYO_ID IN(")
					.append(StringUtil.changeArray2CSV(jokyoList, true))
					.append(")");
			}
			//�ǉ� �����܂�-------------------------------------------------------------

// �\�[�g�@�\��ǉ�
			if(searchInfo.getJigyoKubun() != null &&
				searchInfo.getJigyoKubun() != "" &&
				searchInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){
				//���Ƌ敪������̈�̏ꍇ
				query.append(" ORDER BY")
						.append(" B.SHINSEI_KUBUN DESC")
						.append(", B.KENKYU_KUBUN ASC")
						.append(", B.RYOIKI_NO ASC")
						.append(", B.KOMOKU_NO ASC")
						.append(", B.CHOSEIHAN DESC")
						.append(", B.UKETUKE_NO ASC ")
						;
			}
			else{
				//���̑�(��Վ���)�̏ꍇ
				query.append("ORDER BY")
						.append(" B.SHINSEI_KUBUN ASC")
						.append(", B.BUNKASAIMOKU_CD ASC")
						.append(", B.BUNKATSU_NO ASC")
						.append(", B.UKETUKE_NO ASC ")
						;
			}
			query.append(")");
// �����܂�

		if (log.isDebugEnabled()) {
			log.debug("query:" + query);
		}

		//-----------------------
		// ���X�g�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectCsvList(connection,query.toString(), true);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV�o�̓f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}


	/**
	 * �`�F�b�N���X�gCSV�o�͗p��List�쐬.<br />
	 *
	 * SelectUtil�N���X��selectCsvList���\�b�h���Ăяo���Ĉȉ���SQL�����s���ACSV�f�[�^���X�g�𐶐�����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj<BR>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 * 		C.SHOZOKU_CD \"�@�֔ԍ�\",						-- �@�֔ԍ�
	 * 		B.SHOZOKU_NAME \"�����@�֖�\",					-- �����@�֖�
	 * 		C.JIGYO_ID \"����ID\",							-- ����ID
	 * 		A.NENDO \"�N�x\",								-- �N�x
	 * 		A.KAISU \"��\",								-- ��
	 * 		B.JIGYO_NAME \"���Ɩ�\",						-- ���Ɩ�
	 * 		COUNT(*) \"�\������\",							-- �\������
	 * 		C.EDITION \"��\",								-- ��
	 * 		C.JOKYO_ID \"�󗝏�\",						-- �󗝏�
	 * 		TO_CHAR(B.JYURI_DATE, 'YYYY/MM/DD') \"�󗝓�\"	-- �󗝓�
	 * FROM
	 * 		SHINSEIDATAKANRI B
	 * INNER JOIN
	 * 		CHCKLISTINFO C
	 * INNER JOIN
	 * 		MASTER_JIGYO MASTER_JIGYO
	 * ON
	 * 		MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5)
	 * INNER JOIN
	 * 		JIGYOKANRI A
	 * ON
	 * 		A.JIGYO_ID = C.JIGYO_ID
	 * ON
	 * 		C.JIGYO_ID = B.JIGYO_ID
	 * 		AND C.SHOZOKU_CD = B.SHOZOKU_CD
	 * 		AND C.JOKYO_ID = B.JOKYO_ID
	 *
	 * ------------------------ ���ǒS���҂ŒS�����ǂ����ꍇ�ɒǉ�---------------------------
	 * INNER JOIN
	 * 		TANTOBUKYOKUKANRI TANTO 						   -- �S�����ǊǗ��e�[�u��
	 * ON
	 * 		B.SHOZOKU_CD = TANTO.SHOZOKU_CD
	 * 		AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD
	 * 		AND TANTO.BUKYOKUTANTO_ID = ?
	 * -------------------------------------------------------------------------------------
	 *
	 * -------------�Ɩ��S���҂̏ꍇ�Ɍ����̖�������\�����Ȃ��悤�ɂ��邽�ߒǉ�---------------
	 * INNER JOIN
	 * 		ACCESSKANRI AC									   -- �A�N�Z�X�Ǘ��e�[�u��
	 * ON
	 * 		AC.JIGYO_CD = SUBSTR(B.JIGYO_ID,3,5)
	 * 		AND AC.GYOMUTANTO_ID = ?
	 * -------------------------------------------------------------------------------------
	 *
	 * <b><span style="color:#002288">-- ���I�������� --</span></b>
	 *
	 * GROUP BY
	 * 		A.NENDO,
	 * 		A.KAISU,
	 * 		MASTER_JIGYO.JIGYO_NAME,
	 * 		C.EDITION,
	 * 		C.JIGYO_ID,
	 * 		C.JOKYO_ID,
	 * 		C.SHOZOKU_CD,
	 * 		MASTER_KIKAN.SHOZOKU_NAME_KANJI,
	 * 		B.JYURI_DATE
	 * ORDER BY
	 * 		C.JIGYO_ID, C.SHOZOKU_CD</pre>
	 * </td></tr>
	 * </table><br/>
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * 	   <tr bgcolor="#FFFFFF"><td>TANTO.BUKYOKUTANTO_ID</td><td>������userInfo�̕��ǒS���ҏ��bukyokutantoInfo�̕ϐ�bukyokutantoId</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>AC.GYOMUTANTO_ID</td><td>������userInfo�̋Ɩ��S���ҏ��gyomutantoInfo�̕ϐ�gyomutantoId</td></tr>
	 * </table><br/><br/>
	 *
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>��ID</td><td>jokyoList</td><td>AND C.JOKYO_ID IN(��ID�̔z��)</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>����CD</td><td>shozokuCd</td><td>AND C.SHOZOKU_CD = '����CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>����CD</td><td>jigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) = '����CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>��</td><td>kaisu</td><td>AND A.KAISU = '��'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>�����@�֖�</td><td>shozokuName</td><td>AND B.SHOZOKU_NAME like '%�����@�֖�%'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>�󗝏�(�S��)</td><td>cancellationFlag="0"</td><td>AND (C.CANCEL_FLG = '1' OR C.JOKYO_ID <> '03') </td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>�󗝏�(�m�����)</td><td>cancellationFlag="1"</td><td>AND C.CANCEL_FLG = '1'</td></tr>
	 * </table><br />
	 *
	 * @param userInfo		���[�U���
	 * @param searchInfo	��������
	 * @return				CSV�o�͗pLIST
	 * @throws ApplicationException
	 */
	public List searchCsvDataIchiran(UserInfo userInfo, CheckListSearchInfo searchInfo)
	throws ApplicationException {
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------

		//2005.10.04 iso �\��������A�����@�ց����������@�ցA�R�[�h���ԍ��A�\�����������v�撲��
		String select =
				"SELECT "
   					+ "C.SHOZOKU_CD \"�@�֔ԍ�\", "							//�@�֔ԍ�
				//2005/06/01 �ύX ��������--------------------------------------------------
				//���R �����@�ւ��}�X�^����擾���邽��
				//	+ "B.SHOZOKU_NAME \"�����@�֖�\", "						//�����@�֖�
					+ "MASTER_KIKAN.SHOZOKU_NAME_KANJI \"���������@�֖�\", "
				//�ύX �����܂�-------------------------------------------------------------
					+ "C.JIGYO_ID \"����ID\", "								//����ID
					+ "A.NENDO \"�N�x\", "									//�N�x
					+ "A.KAISU \"��\", "									//��
				//2005/06/01 �ύX ��������--------------------------------------------------
				//���R ���Ɩ����}�X�^����擾���邽��
				//	+ "B.JIGYO_NAME \"���Ɩ�\", "							//���Ɩ�
					+ "MASTER_JIGYO.JIGYO_NAME \"���Ɩ�\", "
				//�ύX �����܂�-------------------------------------------------------------
					+ "COUNT(*) \"���匏��\", "								//���匏��
					+ "C.EDITION \"��\", "									//��
					+ "C.JOKYO_ID \"�󗝏�\", "							//�󗝏�
					+ "TO_CHAR(B.JYURI_DATE, 'YYYY/MM/DD') \"�󗝓�\" "		//�󗝓�

				+ "FROM SHINSEIDATAKANRI B "
					+ "INNER JOIN  CHCKLISTINFO C "
						+ "INNER JOIN JIGYOKANRI A "
						+ "ON A.JIGYO_ID = C.JIGYO_ID "
					//2005/06/01�@�ǉ� ��������--------------------------------------
					//���R ���Ɩ����}�X�^����擾���邽��
						+ "INNER JOIN MASTER_JIGYO MASTER_JIGYO "
						+ "ON MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) "
					//�ǉ��@�����܂�-------------------------------------------------
					+ "ON C.JIGYO_ID = B.JIGYO_ID "
					+ "AND C.SHOZOKU_CD = B.SHOZOKU_CD "
//2006/05/25 �폜��������       �����F��� �̌�����CSV�̌����̕s��C��          
//					+ "AND C.JOKYO_ID = B.JOKYO_ID "
//�c�@�폜�����܂�  
				//2005/06/01�@�ǉ� ��������------------------------------------------
				//���R �����@�ւ��}�X�^����擾���邽��
					+ "INNER JOIN MASTER_KIKAN MASTER_KIKAN "
					+ " ON MASTER_KIKAN.SHOZOKU_CD = B.SHOZOKU_CD ";
				//�ǉ� �����܂�------------------------------------------------------
		 if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
				select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
					+ "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
					+ "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
					+ "AND TANTO.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
		//	}
		//2005/06/01�@�ǉ� ��������--------------------------------------------------
		//���R �Ɩ��S���҂ɂă`�F�b�N���X�g�ꗗ��\������ƁA�����ŕ\������Ȃ������\������Ă��܂����ߒǉ�
		} else if(UserRole.GYOMUTANTO.equals(userInfo.getRole())){
			select += "INNER JOIN ACCESSKANRI AC "
				+ "ON AC.JIGYO_CD = SUBSTR(B.JIGYO_ID,3,5) "
				+ "AND AC.GYOMUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getGyomutantoInfo().getGyomutantoId())+"' ";
		}
		//2005/06/01  �ǉ� �����܂�--------------------------------------------------
			select = select	+ "WHERE B.DEL_FLG = 0 "
					+ "AND A.DEL_FLG = 0 "

// 20050606 Start ���������Ɏ��Ƌ敪��ǉ��@������̈挤���̒ǉ��̂��ߏ����Ɏ��Ƌ敪���Z�b�g
//					+ "AND B.JIGYO_KUBUN = 4 ";
					+ "AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "  ";
// Horikoshi End

			StringBuffer query = new StringBuffer(select);

			if (searchInfo.getShozokuCd() != null
				&& !searchInfo.getShozokuCd().equals("")) { //�����@�փR�[�h
				query.append(
					" AND C.SHOZOKU_CD = '"
						+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
						+ "'");
			}
			String[] jokyoList = searchInfo.getSearchJokyoId();
			if(jokyoList != null && jokyoList.length != 0){
				//query.append(" AND C.JOKYO_ID IN(")
				query.append(" AND B.JOKYO_ID IN(")				
					.append(StringUtil.changeArray2CSV(jokyoList, true))
					.append(")");
			}
			
			String jigyoCd = searchInfo.getJigyoCd();
			//2006.11.14 iso ���S�d�q���Ή��������Ă����̂Œǉ�
//			if(jigyoCd != null && jigyoCd.length() != 0){
//				//����CD�i����ID��3�����ڂ���5�������j
//				query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
//					 .append(EscapeUtil.toSqlString(jigyoCd))
//					 .append("'");
//			}
	        if(jigyoCd == null || jigyoCd.length() == 0){
	            // �y�`�F�b�N���X�g�Ǘ��i��Ռ����iC�j�E�G�茤���E��茤���j�z�̃����N����J��
	            if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(searchInfo.getJigyoKubun())) {
	                // ���S�d�q����Ղ̎���CD�̊i�[�i���S,A,B�Ȃ��j
	                String[] jigyoCdsKikan = {IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN,  //��Ռ���(C)(���)
	                                          IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU, //��Ռ���(C)(��撲��)
	                                         //DEL START 2007/07/04 BIS ����
	                                          //H19���S�d�q��
	                                          //�\����ڕύX�i�G�茤�����폜�j
	                                          //IJigyoCd.JIGYO_CD_KIBAN_HOGA,     //�G�茤��
	                                          //DEL END 2007/07/04 BIS ����
	                                          IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A, //��茤��(A)
	                                          IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B};//��茤��(B)
	                    
	                query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (");
	                query.append(StringUtil.changeArray2CSV(jigyoCdsKikan,true));  
	                query.append(")");
	            }
	        }else{
	            //����CD�i����ID��3�����ڂ���5�������j
	            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) = '")
	                 .append(EscapeUtil.toSqlString(jigyoCd))
	                 .append("'");
	        }
			
	        //2006.11.14 iso �S�����ƂɊւ�������������Ă����̂Œǉ�
	        //�S������CD�i����ID��3�����ڂ���5�������j
	        if(searchInfo.getTantoJigyoCd() != null && searchInfo.getTantoJigyoCd().size() != 0){
	            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
	                 .append(StringUtil.changeIterator2CSV(searchInfo.getTantoJigyoCd().iterator(), true))
	                 .append(")");
	        }
	        //�Ɩ��S���҂̂Ƃ��͒S�����Ƃ������ɒǉ�����
	        if(UserRole.GYOMUTANTO.equals(userInfo.getRole())){
	            Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
	            String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
	            query.append(" AND SUBSTR(A.JIGYO_ID, 3, 5) IN (")
	                 .append(tantoJigyoCd)
	                 .append(")")
	                 .toString();
	        }
	        
			String kaisu = searchInfo.getKaisu();
			if(kaisu != null && kaisu.length() != 0){
				query.append(" AND A.KAISU = '")
					 .append(EscapeUtil.toSqlString(kaisu))
					 .append("'");
			}
			String shozokuName = searchInfo.getShozokuName();
			if(shozokuName != null && shozokuName.length() != 0){
				query.append(" AND B.SHOZOKU_NAME like '%")
					.append(EscapeUtil.toSqlString(shozokuName))
					.append("%'");
			}
			String cancellationFlag = searchInfo.getCancellationFlag();
			//�󗝏󋵂��S�Ă̏ꍇ
			if(cancellationFlag != null && cancellationFlag.equals("0")){
				query.append(" AND (C.CANCEL_FLG = '1' OR C.JOKYO_ID <> '03') ");
			}
			//�󗝏󋵂��m������̏ꍇ
			else if(cancellationFlag != null && cancellationFlag.equals("1")){
				query.append(" AND C.CANCEL_FLG = '")
					.append(EscapeUtil.toSqlString(cancellationFlag))
					.append("'");
			}

			query.append(
				" GROUP BY A.NENDO, "
					+ "A.KAISU, "
				//2005/06/01 �ύX ��������--------------------------------------------------
				//���R ���Ɩ����}�X�^����擾���邽��
				//	+ "B.JIGYO_NAME, "
					+ "MASTER_JIGYO.JIGYO_NAME, "
				//�ύX �����܂�-------------------------------------------------------------
					+ "C.EDITION, "
					+ "C.JIGYO_ID, "
					+ "C.JOKYO_ID, "
					+ "C.SHOZOKU_CD, "
				//2005/06/01 �ύX ��������--------------------------------------------------
				//���R �����@�ւ��}�X�^����擾���邽��
				//	+ "B.SHOZOKU_NAME, "
					+ "MASTER_KIKAN.SHOZOKU_NAME_KANJI, "
				//�ύX �����܂�-------------------------------------------------------------
					+ "B.JYURI_DATE "
					+ "ORDER BY C.JIGYO_ID, C.SHOZOKU_CD");

		if (log.isDebugEnabled()) {
			log.debug("query:" + query);
		}

		//-----------------------
		// ���X�g�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectCsvList(connection,query.toString(), true);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV�o�̓f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	//	2005/04/23 �ǉ� �����܂�----------


	//2005/05/19 �ǉ� ��������----------------------------------------------
	//���R�@�`�F�b�N���X�g��ʁA��єԍ����X�g��ʂ̃^�C�g�����擾�̂���
	/**
	 * �`�F�b�N���X�g�^�C�g�����擾.<BR><BR>
	 *
	 * ����SQL�����s���ACSV�f�[�^���X�g�𐶐�����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj<BR>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *	   JIGYO.NENDO
	 *	   , JIGYO.KAISU
	 *	   , JIGYO.JIGYO_NAME
	 *	   , C.KAKUTEI_DATE
	 *	   , C.EDITION ALLEDITION
	 *	   , C.JIGYO_ID
	 *	   , C.SHOZOKU_CD
	 * FROM
	 *	   CHCKLISTINFO C
	 * INNER JOIN
	 *	   JIGYOKANRI JIGYO
	 * ON
	 *	   A.JIGYO_ID = C.JIGYO_ID
	 * WHERE
	 *	   JIGYO.JIGYO_KUBUN =4
	 *
	 * <b><span style="color:#002288">-- ���I�������� --</span></b></pre>
	 * </td></tr>
	 * </table><br/>
	 *
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *	   <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>����CD</td><td>shozokuCd</td><td>AND C.SHOZOKU_CD = '����CD'</td></tr>
	 *	   <tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoId</td><td>AND C.JIGYO_ID = '����ID'</td></tr>
	 * </table><br />
	 *
	 * �擾�����l�́A�񖼂��L�[��Map�ɃZ�b�g����AList�ɃZ�b�g�����B
	 * ����List���i�[����Page��ԋp����B<br /><br />
	 *
	 * @param searchInfo	CheckListSearchInfo
	 * @return	Page	�y�[�W���
	 * @exception ApplicationException
	 */
	public Page selectTitle(CheckListSearchInfo searchInfo)
		throws ApplicationException {

		String select =
			"SELECT JIGYO.NENDO" +
				", JIGYO.KAISU" +
				", JIGYO.JIGYO_NAME" +
				", C.KAKUTEI_DATE" +
				", C.EDITION ALLEDITION " +
				", C.JIGYO_ID " +
				", C.SHOZOKU_CD " +
			"FROM CHCKLISTINFO C " +
			"INNER JOIN JIGYOKANRI JIGYO " +
				"ON JIGYO.JIGYO_ID = C.JIGYO_ID " +

// 20050606 Start ���������Ɏ��Ƌ敪��ǉ��@������̈挤���̒ǉ��̂��ߏ����Ɏ��Ƌ敪���Z�b�g
//			"WHERE JIGYO.JIGYO_KUBUN =4 ";
				"WHERE JIGYO.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + " ";
// Horikoshi End

		StringBuffer query = new StringBuffer(select);
		if (searchInfo.getShozokuCd() != null
			&& !searchInfo.getShozokuCd().equals("")) { //�����@�փR�[�h
			query.append(
				" AND C.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
					+ "'");
		}
		if(searchInfo.getJigyoId() != null
			&& !searchInfo.getJigyoId().equals("")){	//����ID
			query.append(
				" AND C.JIGYO_ID = '"
				+ EscapeUtil.toSqlString(searchInfo.getJigyoId())
				+ "'");
		}
		if (log.isDebugEnabled()) {
			log.debug("query:" + query);
		}

		//-----------------------
		// �y�[�W���擾
		//-----------------------
		Connection connection = null;
		Page page = null;
		try {
			connection = DatabaseUtil.getConnection();
			page = SelectUtil.selectPageInfo(
				connection,
				searchInfo,
				query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�\�������擾����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return page;
	}
	//�ǉ� �����܂�------------------------------------------------------


//	//2005/05/25 �ǉ� ��������-------------------------------------------
//	//���R�@�\��PDF�t�@�C���擾�̂���
//
//	/**
//	 * �\��PDF�t�@�C���p�X�擾.<BR><BR>
//	 *
//	 * ����SQL�����s���A�\��PDF�t�@�C���p�X���擾����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj<BR>
//	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
//	 * <tr bgcolor="#FFFFFF"><td>
//	 * <pre>
//	 *
//	 * SELECT
//	 *	   PDF_PATH 		  -- PDF�̊i�[�p�X
//	 * FROM
//	 *	   CHCKLISTINFO 	  -- �`�F�b�N���X�g���e�[�u��
//	 * WHERE
//	 *	   SHOZOKU_CD = ?
//	 *	   AND JIGYO_ID = ?</pre>
//	 * </td></tr>
//	 * </table><br/>
//	 *
//	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
//	 *	   <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
//	 * 	   <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������searchInfo�̕ϐ�shozokuCd</td></tr>
//	 * 	   <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������searchInfo�̕ϐ�jigyoId</td></tr>
//	 * </table><br />
//	 *
//	 * �擾�����t�@�C���p�X��Ԃ��B<br /><br />
//	 *
//	 * @param userInfo	UserInfo
//	 * @param searchInfo	CheckListSearchInfo
//	 * @return	�\��PDF�t�@�C���p�X
//	 * @exception ApplicationException
//	 */
//	public String getPdfFilePath(UserInfo userInfo,
//		CheckListSearchInfo searchInfo)
//		throws ApplicationException {
//
//		String pdfPath = null;
//		Connection connection = null;
//		try{
//			connection = DatabaseUtil.getConnection();
//			CheckListInfoDao dao = new CheckListInfoDao(userInfo);
//			//PDF�t�@�C���p�X�̎擾
//			pdfPath = dao.getPath(connection, searchInfo);
//
//		} catch (DataAccessException e) {
//
//			throw new ApplicationException(
//				"PDF�t�@�C���p�X�擾����DB�G���[���������܂���",
//				new ErrorInfo("errors.4002"),
//				e);
//		} finally {
//				DatabaseUtil.closeConnection(connection);
//		}
//		return pdfPath;
//	}
//	//�ǉ� �����܂�------------------------------------------------------


	//2005.08.11 iso �\���_�E�����[�h�̓t�@�C�����\�[�X�𒼐ڕԂ��悤�ύX�B
	//�p�X��Ԃ������ł́AWeb�T�[�o�ƋƖ��T�[�o���������ƑΉ��ł��Ȃ��B
	/**
	 * �\���pPDF���_�E�����[�h����B
	 * @param userInfo
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public FileResource getPdfFile(UserInfo userInfo,
			CheckListSearchInfo searchInfo)
			throws ApplicationException {

		String pdfPath = null;
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			CheckListInfoDao dao = new CheckListInfoDao(userInfo);
			//PDF�t�@�C���p�X�̎擾
			pdfPath = dao.getPath(connection, searchInfo);

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"PDF�t�@�C���p�X�擾����DB�G���[���������܂���",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
				DatabaseUtil.closeConnection(connection);
		}

		FileResource fileRes = null;
		File file = new File(pdfPath);
		try{
			fileRes = FileUtil.readFile(file);
		}catch(IOException e){
			throw new FileIOException(
				"�t�@�C���̓��͒��ɃG���[���������܂����B",
				e);
		}

		return fileRes;
	}

	/**
	 * �����ҏ�񃊃X�g�̃`�F�b�N�i�����Ҕԍ��Ƌ@�փR�[�h�̑��݃`�F�b�N�j
	 * @param userInfo ���O�I���������[�U���
	 * @param searchInfo �`�F�b�N���X�g�������
	 * @param shozokuCdArray �����Ҕԍ����X�g
	 * @param jigyoIdArray �@�փR�[�h���X�g
	 * @return List
	 * @throws ApplicationException
	 */
	public List IkkatuKenkyushaExist(
			UserInfo userInfo,
			CheckListSearchInfo searchInfo,
			List shozokuCdArray,
			List jigyoIdArray
			) throws
			ApplicationException{

		List lstErrors = new ArrayList();
		List lstResult = new ArrayList();
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			for(int count=0;count<shozokuCdArray.size();count++){
				searchInfo.setShozokuCd(shozokuCdArray.get(count).toString());
				searchInfo.setJigyoId(jigyoIdArray.get(count).toString());
				lstErrors = chkKenkyushaExist(userInfo, searchInfo, connection);
				for(int i=0; i<lstErrors.size(); i++){
					if(!lstResult.contains(lstErrors.get(i))){
						lstResult.add(lstErrors.get(i).toString());
					}
				}
			}
		}catch(ApplicationException ex){
			throw new ApplicationException("�����҂̌�������DB�G���[���������܂����B",new ErrorInfo("errors.4004"),ex);
		}finally{
			DatabaseUtil.closeConnection(connection);
		}
		return lstResult;
	}

	/**
	 * �����ҏ��̃`�F�b�N���\�b�h�i�����Ҕԍ��Ƌ@�փR�[�h�Ń`�F�b�N����j
	 */
	public List chkKenkyushaExist(
			UserInfo userInfo,
			CheckListSearchInfo searchInfo,
			Connection connection
			) throws
			ApplicationException{

		boolean connectFlg		= true;								//�R�l�N�V�������n���ꂽ�̂����ʁ@True�F�n���ꂽ�@False�FNull������
		List lstErrors			= new ArrayList();					//�G���[���b�Z�[�W�z��
		String chSQL			= new String();						//SQL������
		List lstKenkyusha		= new ArrayList();					//������NO�z��
		List lstSystemNo		= new ArrayList();					//�V�X�e��NO�z��
//delete start dyh 2006/2/10 �����F�g�p���Ȃ��̕ϐ�
//		List lstResult			= new ArrayList();					//
//delete end dyh 2006/2/10
		CheckListInfoDao dao	= new CheckListInfoDao(userInfo);

		try{
			if(connection == null){
				//�R�l�N�V�������n����Ȃ������ꍇ�ɂ͐���
				connection = DatabaseUtil.getConnection();
				connectFlg = false;
			}
			searchInfo.setJokyoId(dao.checkJokyoId(connection, searchInfo, false));

			//�V�X�e��NO�擾SQL�쐬���V�X�e��NO�擾
			chSQL = "Select" +
					" SYSTEM_NO," +
					" BUNKASAIMOKU_CD," +
					" BUNKATSU_NO," +
					" RYOIKI_NO," +
					" KOMOKU_NO," +
					" UKETUKE_NO," +
					" KENKYU_NO," +
					" NAME_KANJI_SEI," +
					" NAME_KANJI_MEI," +
					" NAME_KANA_SEI," +
					" NAME_KANA_MEI " +
					"From" +
					" SHINSEIDATAKANRI " +
					"WHERE" +
					" JIGYO_ID = '" + EscapeUtil.toSqlString(searchInfo.getJigyoId()) + "' " +		//�N���C�A���g������ł��錟������
					"AND" +
					" JOKYO_ID = '" + EscapeUtil.toSqlString(searchInfo.getJokyoId()) + "' " +		//���݂̃`�F�b�N���X�g�̏�ID
					"AND" +
					" SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "' " +	//�N���C�A���g������ł��錟������
					"AND" +
					" DEL_FLG = " + "0" + ""
					;
			if (log.isDebugEnabled()) {log.debug("query:" + chSQL);}
			String chItems[] = new String[]{
					"SYSTEM_NO","BUNKASAIMOKU_CD","BUNKATSU_NO","RYOIKI_NO","KOMOKU_NO","UKETUKE_NO","KENKYU_NO","NAME_KANJI_SEI","NAME_KANJI_MEI","NAME_KANA_SEI","NAME_KANA_MEI"
					};
			lstSystemNo = dao.select(connection, chSQL, chItems);
			if(lstSystemNo.size() <= 0){									//������񂪑��݂��Ȃ��ꍇ
				throw new ApplicationException("�����҂̌�������DB�G���[���������܂����B",new ErrorInfo("errors.4004"));
			}

			//�����ҏ��擾SQL(��{����)
			chSQL = "Select" +
					" KENKYU_NO," +
					" SHOZOKU_CD," +
					" NAME_KANJI_SEI," +
					" NAME_KANJI_MEI," +
					" NAME_KANA_SEI," +
					" NAME_KANA_MEI " +
					"From" +
					" KENKYUSOSHIKIKANRI " +
					"Where" +
					" SEQ_NO = '1'" +	//2005/08/31 �b��I�Ɍ�����\�҂݂̂��`�F�b�N����悤�ɏC���B�i���F�A�󗝁A����o�^�Ƃ��킹��j
					" AND" +
					" SYSTEM_NO = '"
					;
			for(int i = 0; i<lstSystemNo.size(); i++){
				Object objInfo[] = (Object[])lstSystemNo.get(i);
				String chSysSQL = chSQL + String.valueOf(objInfo[0]) + "'";
				if (log.isDebugEnabled()) {log.debug("query:" + chSysSQL);}

				String chKenkyuSearch[] = new String[]{"KENKYU_NO",
														"SHOZOKU_CD",
														"NAME_KANJI_SEI",
														"NAME_KANJI_MEI",
														"NAME_KANA_SEI",
														"NAME_KANA_MEI"
														};
				lstKenkyusha = dao.select(connection,chSysSQL,chKenkyuSearch);
				if(lstKenkyusha == null || lstKenkyusha.isEmpty()){				//�����ҏ�񂪑��݂��Ȃ��ꍇ
					throw new ApplicationException("�����҂̌�������DB�G���[���������܂����B",new ErrorInfo("errors.4004"));
				}

				//�����҃}�X�^�Ɍ����҂����݂��邩�`�F�b�N
				String chQuery = "Select" +
						" COUNT(KENKYU_NO) GET_COUNT " +
						"From" +
						" MASTER_KENKYUSHA " +
						"Where "
						;
				for(int n = 0;n<lstKenkyusha.size(); n++){
					Object objKenkyuInfo[] =(Object[])lstKenkyusha.get(n);
					//�ꊇ�󗝁A�`�F�b�N���X�g�m��i���F�j���͌����Ҕԍ��Ƌ@�փR�[�h�ő��݃`�F�b�N
					String Query =
							" KENKYU_NO = '" + EscapeUtil.toSqlString(String.valueOf(objKenkyuInfo[0])) + "' " +
							"AND" +
							" SHOZOKU_CD = '" + EscapeUtil.toSqlString(String.valueOf(objKenkyuInfo[1])) + "' " +
							"AND" +
							" DEL_FLG = 0 "
							;

					if (log.isDebugEnabled()) {log.debug("query:" + chQuery + Query);}
					String chCount[] = new String[]{"GET_COUNT"};
					List lstRes = dao.select(connection, chQuery + Query, chCount);
					Object objCount[] =(Object[])lstRes.get(0);

					if("0".equals(String.valueOf(objCount[0]))){
						String chMessage = null;
						if(searchInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
//							chMessage = "�����҃}�X�^�Ɉȉ��̌����҂����݂��܂���B�F(" +
//										StringUtil.defaultString(objInfo[1]) + "-" +
//										StringUtil.defaultString(objInfo[2]) + "-" +
//										StringUtil.defaultString(objInfo[5]) + ")[" +
//										StringUtil.defaultString(objInfo[7]) + "_" +
//										StringUtil.defaultString(objInfo[8]) + "]" +
//										StringUtil.defaultString(objInfo[6]) + ""
							chMessage = "�����҃}�X�^�Ɉȉ��̌����҂����݂��܂���B�F(" +
										StringUtil.defaultString(objInfo[1]) + "-" +
										StringUtil.defaultString(objInfo[2]) + "-" +
										StringUtil.defaultString(objInfo[5]) + ")[" +
										StringUtil.defaultString(objKenkyuInfo[2]) + "_" +
										StringUtil.defaultString(objKenkyuInfo[3]) + "]" +
										StringUtil.defaultString(objKenkyuInfo[0]) + ""
							;
						}
						else if(searchInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){
//							chMessage = "�����҃}�X�^�Ɉȉ��̌����҂����݂��܂���B�F(" +
//										StringUtil.defaultString(objInfo[3]) + "-" +
//										StringUtil.defaultString(objInfo[4]) + "-" +
//										StringUtil.defaultString(objInfo[5]) + ")[" +
//										StringUtil.defaultString(objInfo[7]) + "_" +
//										StringUtil.defaultString(objInfo[8]) + "]" +
//										StringUtil.defaultString(objInfo[6]) + ""
							chMessage = "�����҃}�X�^�Ɉȉ��̌����҂����݂��܂���B�F(" +
										StringUtil.defaultString(objInfo[3]) + "-" +
										StringUtil.defaultString(objInfo[4]) + "-" +
										StringUtil.defaultString(objInfo[5]) + ")[" +
										StringUtil.defaultString(objKenkyuInfo[2]) + "_" +
										StringUtil.defaultString(objKenkyuInfo[3]) + "]" +
										StringUtil.defaultString(objKenkyuInfo[0]) + ""
							;
						}
						else{
							chMessage = "�����҂����݂��܂���ł����B";
						}
						lstErrors.add(chMessage);
					}
					else if(!"1".equals(String.valueOf(objCount[0]))){
						break;
					}
				}
			}
		}
		catch(DataAccessException ex){
			throw new ApplicationException("�����҂̌�������DB�G���[���������܂����B",new ErrorInfo("errors.4004"),ex);
//		}catch(NoDataFoundException ex){
//			throw new NoDataFoundException("�����҂����݂��܂���ł����B",new ErrorInfo("errors.5048"),ex);
//			throw new NoDataFoundException(ex.getMessage());
		}catch(ApplicationException ex){
			throw new ApplicationException("�����҂̌�������DB�G���[���������܂����B",new ErrorInfo("errors.4004"),ex);
		}finally{
			if(!connectFlg){
				//�R�l�N�V�������n����Ȃ�����(Null������)�ꍇ�ɂ̓��\�b�h���ŃR�l�N�V�������N���[�Y
				DatabaseUtil.closeConnection(connection);
			}
		}
		return lstErrors;
	}

	/**
	 * �`�F�b�N���X�g�m���A���[�����M�p�����擾����
	 * @param searchInfo
	 * @return List
     * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	private List selectTitleInfo(CheckListSearchInfo searchInfo)
			throws NoDataFoundException, ApplicationException {

		String select = "SELECT JIGYO.NENDO"
                      + ", JIGYO.KAISU"
                      + ", JIGYO.JIGYO_NAME"
                      + ", C.KAKUTEI_DATE"
                      + ", C.EDITION ALLEDITION "
                      + ", C.JIGYO_ID "
                      + ", C.SHOZOKU_CD "
                      + "FROM CHCKLISTINFO C "
                      + "INNER JOIN JIGYOKANRI JIGYO "
                      + "ON JIGYO.JIGYO_ID = C.JIGYO_ID "

					//���������Ɏ��Ƌ敪��ǉ��@������̈挤���̒ǉ��̂��ߏ����Ɏ��Ƌ敪���Z�b�g
                      + "WHERE JIGYO.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun());
// 2006/08/21 dyh delete start
//// 2006/06/30 zjp �ǉ���������
//                        select = select + " AND SUBSTR(JIGYO.JIGYO_ID,3,5) IN ("
//                        + StringUtil.changeIterator2CSV(JigyoKubunFilter.getCheckListKakuteiJigyoCd().iterator(),true)
//                        + ") ";
////  zjp�@�ǉ������܂�
// 2006/08/21 dyh delete end
		StringBuffer query = new StringBuffer(select);
		if (searchInfo.getShozokuCd() != null
			&& !searchInfo.getShozokuCd().equals("")) { //�����@�փR�[�h
			query.append(
				" AND C.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
					+ "'");
		}
		if(searchInfo.getJigyoId() != null
			&& !searchInfo.getJigyoId().equals("")){	//����ID
			query.append(
				" AND C.JIGYO_ID = '"
				+ EscapeUtil.toSqlString(searchInfo.getJigyoId())
				+ "'");
		}
		if (log.isDebugEnabled()) {
			log.debug("query:" + query);
		}

		//-----------------------
		// �y�[�W���擾
		//-----------------------
		Connection connection = null;
//delete start dyh 2006/2/10 �����F�g�p���Ȃ��̕ϐ�
//		Page page = null;
//delete end dyh 2006/2/10
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.select(connection, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�`�F�b�N���X�g�^�C�g�����擾����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * �`�F�b�N���X�g���׃f�[�^�̌������擾����
	 * @param userInfo
	 * @param searchInfo
	 * @return ���׌���
	 * @throws ApplicationException
	 */
	private int selectListDataCount(UserInfo userInfo, CheckListSearchInfo searchInfo)
			throws ApplicationException
	{

		String select = "SELECT count(*) CNT "
						+ "FROM SHINSEIDATAKANRI B INNER JOIN CHCKLISTINFO C "
						+ "INNER JOIN JIGYOKANRI A "
						+ "ON A.JIGYO_ID = C.JIGYO_ID "
						+ "ON C.JIGYO_ID = B.JIGYO_ID "
						+ "AND B.SHOZOKU_CD = C.SHOZOKU_CD ";

//		if (userInfo.getBukyokutantoInfo() != null
//				&& userInfo.getBukyokutantoInfo().getTantoFlg()) {
//			select = select + "INNER JOIN TANTOBUKYOKUKANRI TANTO "
//					+ "ON B.SHOZOKU_CD = TANTO.SHOZOKU_CD "
//					+ "AND B.BUKYOKU_CD = TANTO.BUKYOKU_CD "
//					+ "AND TANTO.BUKYOKUTANTO_ID = '"
//					+ userInfo.getBukyokutantoInfo().getBukyokutantoId() + "' ";
//		}

		select = select + "WHERE B.JOKYO_ID = C.JOKYO_ID "
						  + "AND B.DEL_FLG = 0 "
						  + "AND A.DEL_FLG = 0 ";

		if (searchInfo.getJigyoKubun() != null) {
			select = select + " AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun());
		} else {
			select = select + " AND B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN;
		}

		StringBuffer query = new StringBuffer(select);
		if (searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")) { //�����@�փR�[�h
			query.append(" AND C.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if (searchInfo.getJigyoId() != null && !searchInfo.getJigyoId().equals("")) { //���ƃR�[�h
			query.append(" AND B.JIGYO_ID = '"
					+ EscapeUtil.toSqlString(searchInfo.getJigyoId()) + "'");
		}

		String[] jokyoList = searchInfo.getSearchJokyoId();
		if (jokyoList != null && jokyoList.length != 0) {
			query.append(" AND C.JOKYO_ID IN(").append(
					StringUtil.changeArray2CSV(jokyoList, true)).append(")");
		}

		if (log.isDebugEnabled()) {
			log.debug("�`�F�b�N���X�g���׌���query:" + query);
		}

		//-----------------------
		// �y�[�W���擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			List rstList = SelectUtil.select(connection, query.toString());
			return Integer.parseInt(((Map) rstList.get(0)).get("CNT").toString());
		} catch (DataAccessException e) {
			throw new ApplicationException("�`�F�b�N���X�g���׌����擾����DB�G���[���������܂����B",
						new ErrorInfo("errors.4004"), e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * ���发�ނ̒�o���o�́i��Ռ������A����̈挤���j
	 * @param UserInfo userInfo ���O�C���ҏ��
	 * @param checkInfo �`�F�b�N���X�g��������
	 * @return�@FileResource�@�o�͏��CSV�t�@�E��
	 */
	public FileResource createOuboTeishutusho(
			UserInfo userInfo,
			CheckListSearchInfo checkInfo)
			throws ApplicationException
	{
		//DB���R�[�h�擾
		List csv_data = null;
		try {
			csv_data = selectOuboTeishutushoInfo(checkInfo);
		} finally {
		}

//		if (csv_data.size() < 2){
//			throw new ApplicationException(
//					"�����񂪂���܂���B",
//					new ErrorInfo("errors.5002"));
//		}

		//-----------------------
		// CSV�t�@�C���o��
		//-----------------------
		String csvFileName = "";
		if (checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
			csvFileName = "KIBANDATA";//���
		}else if(checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){
			csvFileName = "TOKUTEIDATA";//����̈�
		}
// 2007/02/12  ���u�j�@�폜��������
//        else if(checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
//			csvFileName = "WAKATE";//���X�^�[�g�A�b�v
//		}else{
//			csvFileName = "SHOKUSHINHI";//���ʌ������i��
//		}
// 2007/02/12�@���u�j�@�폜�����܂�
        
		//2005/09/09 takano �t�H���_�����~���b�P�ʂɕύX�B�O�̂��ߓ����ɓ����������g�ݍ��݁B
		String filepath = null;
		synchronized(log){
			//2005/9/27 ���b�N���Ԃ��Z���ē������ۂ��Č������ׁA���O�C����ID���g�ݍ���
			//filepath = OUBO_WORK_FOLDER + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";
			filepath = OUBO_WORK_FOLDER + userInfo.getShozokuInfo().getShozokuTantoId() + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
		}
		CsvUtil.output(csv_data, filepath, csvFileName);

		//-----------------------
		// �˗����t�@�C���̃R�s�[
		//-----------------------
//update start dyh 2006/2/8
        //���
		if (checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + OUBO_FORMAT_FILE_NAME_KIBAN),
							  new File(filepath + OUBO_FORMAT_FILE_NAME_KIBAN));
			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + "$1"), new File(filepath + "$"));
        //����̈�
		}else if (checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI)){
			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + OUBO_FORMAT_FILE_NAME_TOKUTEI),
							  new File(filepath + OUBO_FORMAT_FILE_NAME_TOKUTEI));
			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + "$2"), new File(filepath + "$"));
		
		}
// 2007/02/12  ���u�j�@�폜��������
//        //���X�^�[�g�A�b�v
//        else if (checkInfo.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)){
//			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + OUBO_FORMAT_FILE_NAME_WAKATESTART),
//							  new File(filepath + OUBO_FORMAT_FILE_NAME_WAKATESTART));
//			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + "$3"), new File(filepath + "$"));
//		//���ʌ������i��
//		}else{
//			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + OUBO_FORMAT_FILE_NAME_SHOKUSHINHI),
//					  new File(filepath + OUBO_FORMAT_FILE_NAME_SHOKUSHINHI));
//			FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + "$4"), new File(filepath + "$"));
//		}
// 2007/02/12�@���u�j�@�폜�����܂�
//update end dyh 2006/2/8

		//-----------------------
		// �t�@�C���̈��k
		//-----------------------
		String comp_file_name = csvFileName + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
		FileUtil.fileCompress(filepath, filepath, comp_file_name);

		//-------------------------------------
		//�쐬�����t�@�C����ǂݍ��ށB
		//-------------------------------------
		File exe_file = new File(filepath + comp_file_name + ".EXE");
		FileResource compFileRes = null;
		try {
			compFileRes = FileUtil.readFile(exe_file);
		} catch (IOException e) {
			throw new ApplicationException(
				"�쐬�t�@�C��'" + comp_file_name + ".EXE'���̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.8005"),
				e);
		}finally{
			//��ƃt�@�C���̍폜
			FileUtil.delete(exe_file.getParentFile());
		}

		//���ȉ𓀌^���k�t�@�C�������^�[��
		return compFileRes;
	}

	/**
	 * ���发�ނ̒�o���o�͏��̎擾
	 * �擾�����F�@2005/8/30�@�ύX
	 * ��Ղ̏ꍇ�F�e��ڂ̌����͊Y����ڐV�K�p���敪���P�̌���
	 * �@�@�@�@�@�@�p���ۑ茏���͊e��ڐV�K�p���敪���Q�̍��v����
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	private List selectOuboTeishutushoInfo(CheckListSearchInfo searchInfo)
			throws ApplicationException {

		StringBuffer sbSelecct = new StringBuffer(1024);
		StringBuffer sbSelecct2 = new StringBuffer(1024);
		StringBuffer sbFrom = new StringBuffer(512);
		StringBuffer sbWhere = new StringBuffer(512);

		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
        // 2006/08/17 dyh add start
        String jigyoKubun = searchInfo.getJigyoKubun();
        // ���Ƌ敪�����݂��Ȃ������ꍇ�ɂ͊�Ռ������Z�b�g
        if(StringUtil.isBlank(jigyoKubun)){
            jigyoKubun = IJigyoKubun.JIGYO_KUBUN_KIBAN;
        }
        // 2006/08/17 add end

		//��Ղ̏ꍇ
		if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKubun)){
			sbSelecct.append("SELECT  ")
					.append(" SHOZOKU_CD                                \"�@�֔ԍ�\", \n")
					.append(" SHOZOKU_NAME                              \"�����@�֖�\", \n")	//�������Word�Ƃ̃����N���؂�邽�߁u�����@�ցv�̂܂�
// 2006/08/17 dyh delete start �����FWord�l������폜��������
					//.append(" DECODE(KIBAN_S_CNT,  0, '', KIBAN_S_CNT ) \"��Ղr����\", \n")
					//.append(" DECODE(KIBAN_A1_CNT, 0, '', KIBAN_A1_CNT) \"��Ղ`��ʌ���\", \n")
					//.append(" DECODE(KIBAN_A2_CNT, 0, '', KIBAN_A2_CNT) \"��Ղ`��������\", \n")
					//.append(" DECODE(KIBAN_B1_CNT, 0, '', KIBAN_B1_CNT) \"��Ղa��ʌ���\", \n")
					//.append(" DECODE(KIBAN_B2_CNT, 0, '', KIBAN_B2_CNT) \"��Ղa��������\", \n")
					.append(" DECODE(KIBAN_C1_CNT, 0, '', KIBAN_C1_CNT) \"��Ղb��ʌ���\", \n")
//2006/08/18 �폜�𕜊�
					.append(" DECODE(KIBAN_C2_CNT, 0, '', KIBAN_C2_CNT) \"��Ղb��������\", \n")
// 2006/08/17 dyh delete end
					.append(" DECODE(HOUGA_CNT,    0, '', HOUGA_CNT)    \"�G�茤������\", \n")
					.append(" DECODE(WAKATE_A_CNT, 0, '', WAKATE_A_CNT) \"���`����\", \n")
					.append(" DECODE(WAKATE_B_CNT, 0, '', WAKATE_B_CNT) \"���a����\", \n")
					.append(" DECODE(CONTU_CNT,    0, '', CONTU_CNT)    \"�p���ۑ茏��\", \n")
					.append(" TOTAL_CNT                                 \"������\" \n");

			sbSelecct.append(" FROM ( \n");

			sbSelecct.append(" SELECT  ")
					.append(" MIN(B.SHOZOKU_CD)   SHOZOKU_CD, \n")
					.append(" MIN(B.SHOZOKU_NAME) SHOZOKU_NAME, \n")
// 2006/08/17 dyh delete start �����FWord�l������폜��������
					//.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00031' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_S_CNT, \n")//��Ղr
					//.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00041' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_A1_CNT, \n")//��Ղ`���
					//.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00043' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_A2_CNT, \n")//��Ղ`����
					//.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00051' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_B1_CNT, \n")//��Ղa���
					//.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00053' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_B2_CNT, \n")//��Ղa����
					.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00061' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_C1_CNT, \n")//��Ղb���
//2006/08/18 �폜�𕜊�
					.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00062' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) KIBAN_C2_CNT, \n")//��Ղb����
// 2006/08/17 dyh delete end
					.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00111' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) HOUGA_CNT, \n")//�G�茤��
					.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00121' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) WAKATE_A_CNT, \n")//���`
					.append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00131' AND NVL(B.SHINSEI_KUBUN,'1') = '1' THEN 1 ELSE 0 END) WAKATE_B_CNT, \n")//���a
					.append(" SUM(DECODE(B.SHINSEI_KUBUN,'2',1,0)) CONTU_CNT, \n")
					.append(" COUNT(*)     TOTAL_CNT \n");
        //����̈�̏ꍇ
		}else if(IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(jigyoKubun)){
			sbSelecct.append("SELECT")
					.append(" DECODE(T1.SHOZOKU_CD,NULL,T2.SHOZOKU_CD,T1.SHOZOKU_CD) \"�@�֔ԍ�\", \n")
					.append(" DECODE(T1.SHOZOKU_NAME,NULL,T2.SHOZOKU_NAME,T1.SHOZOKU_NAME) \"�����@�֖�\", \n")
					.append(" DECODE(T1.KOUBO1_CNT,    0, '', T1.KOUBO1_CNT)    \"����v�挏��\", \n")
					.append(" DECODE(T1.KOUBO2_CNT,    0, '', T1.KOUBO2_CNT)    \"���匤������\", \n")
					.append(" DECODE(T1.NO_KOUBO_CNT,  0, '', T1.NO_KOUBO_CNT)  \"����匏��\", \n")
					.append(" DECODE(T2.KIKAN_END_CNT, 0, '', T2.KIKAN_END_CNT) \"�����I������\", \n")
					.append(" DECODE(T2.TOTAL_CNT,NULL,T1.TOTAL_CNT,T2.TOTAL_CNT) \"������\" \n");

			sbSelecct.append(" FROM ( \n");
			
			sbSelecct.append("SELECT")
					.append(" MIN(B.SHOZOKU_CD)   SHOZOKU_CD, \n")
					.append(" MIN(B.SHOZOKU_NAME) SHOZOKU_NAME, \n")
					.append(" SUM(CASE WHEN B.KENKYU_KUBUN = '1' AND D.KOUBO_FLG = '1'\n")
					.append(" THEN 1 ELSE 0 END) KOUBO1_CNT, \n")
					.append(" SUM(DECODE(B.KENKYU_KUBUN, 2,1,0)) KOUBO2_CNT, \n")
					.append(" SUM(CASE WHEN B.KENKYU_KUBUN = '1' AND NVL(D.KOUBO_FLG,'0') <> '1'\n")
					.append(" THEN 1 ELSE 0 END) NO_KOUBO_CNT, \n")
//					.append(" SUM(DECODE(B.KENKYU_KUBUN, 3,1,0)) KIKAN_END_CNT, \n")
					.append(" COUNT(*)     TOTAL_CNT \n");

			sbSelecct2.append("(SELECT")
					.append(" MIN(B.SHOZOKU_CD)   SHOZOKU_CD, \n")
					.append(" MIN(B.SHOZOKU_NAME) SHOZOKU_NAME, \n")
					.append(" SUM(DECODE(B.KENKYU_KUBUN, 3,1,0)) KIKAN_END_CNT, \n")
					.append(" COUNT(*)     TOTAL_CNT \n");
		
		}
// 2007/02/12  ���u�j�@�폜��������
//        //���X�^�[�g�A�b�v�̏ꍇ
//        else if(IJigyoKubun.JIGYO_KUBUN_WAKATESTART.equals(jigyoKubun)){
//			sbSelecct.append("SELECT ")
//			        .append(" SHOZOKU_CD                            \"�@�֔ԍ�\", \n")
//			        .append(" SHOZOKU_NAME                          \"�����@�֖�\", \n")
//			        .append(" DECODE(WAKATE_CNT, 0, '', WAKATE_CNT) \"�X�^�[�g�A�b�v����\" \n");
////delete start dyh 2006/2/22 �����F�d�l�ύX
//			        //.append(" TOTAL_CNT                             \"������\" \n");
////delete end dyh 2006/2/22 �����F�d�l�ύX
//
//	        sbSelecct.append(" FROM ( \n");
//
//	        sbSelecct.append(" SELECT ")
//			        .append(" MIN(B.SHOZOKU_CD)   SHOZOKU_CD, \n")
//			        .append(" MIN(B.SHOZOKU_NAME) SHOZOKU_NAME, \n")
//			        .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00141' THEN 1 ELSE 0 END) WAKATE_CNT \n");
////delete start dyh 2006/2/22 �����F�d�l�ύX
////			        .append(" COUNT(*)     TOTAL_CNT \n");
////delete end dyh 2006/2/22 �����F�d�l�ύX
//
//		//���ʌ������i��̏ꍇ
//		}else{
//			sbSelecct.append("SELECT ")
//	                .append(" SHOZOKU_CD                                \"�@�֔ԍ�\", \n")
//	                .append(" SHOZOKU_NAME                              \"�����@�֖�\", \n")
//	                .append(" DECODE(KIBAN_A_CNT, 0, '', KIBAN_A_CNT)   \"��Ղ`��������\", \n")
//	                .append(" DECODE(KIBAN_B_CNT, 0, '', KIBAN_B_CNT)   \"��Ղa��������\", \n")
//	                .append(" DECODE(KIBAN_C_CNT, 0, '', KIBAN_C_CNT)   \"��Ղb��������\", \n")
//	                .append(" DECODE(WAKATE_A_CNT, 0, '', WAKATE_A_CNT) \"���`��������\", \n")
//	                .append(" DECODE(WAKATE_B_CNT, 0, '', WAKATE_B_CNT) \"���a��������\", \n")
//	                .append(" TOTAL_CNT                                 \"������\" \n");
//
//	        sbSelecct.append(" FROM ( \n");
//
//	        sbSelecct.append(" SELECT ")
//	                .append(" MIN(B.SHOZOKU_CD)   SHOZOKU_CD, \n")
//	                .append(" MIN(B.SHOZOKU_NAME) SHOZOKU_NAME, \n")
//	                .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00152' THEN 1 ELSE 0 END) KIBAN_A_CNT, \n")
//	                .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00153' THEN 1 ELSE 0 END) KIBAN_B_CNT, \n")
//	                .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00154' THEN 1 ELSE 0 END) KIBAN_C_CNT, \n")
//	                .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00155' THEN 1 ELSE 0 END) WAKATE_A_CNT, \n")
//	                .append(" SUM(CASE WHEN SUBSTR(B.JIGYO_ID,3,5) = '00156' THEN 1 ELSE 0 END) WAKATE_B_CNT, \n")
//	                .append(" COUNT(*)     TOTAL_CNT \n");
//		}
// 2007/02/12�@���u�j�@�폜�����܂�
        
		sbFrom.append(" FROM SHINSEIDATAKANRI B \n")
				.append( "INNER JOIN  CHCKLISTINFO C \n")
				.append( "INNER JOIN JIGYOKANRI A \n")
				.append("ON A.JIGYO_ID = C.JIGYO_ID \n")
				//.append("INNER JOIN MASTER_JIGYO MASTER_JIGYO ")
				//.append("ON MASTER_JIGYO.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) ")
				.append("ON C.JIGYO_ID = B.JIGYO_ID \n")
				.append("AND C.SHOZOKU_CD = B.SHOZOKU_CD \n")
			// 2006/08/18 sai���̏����͍폜����	
				//.append("AND C.JOKYO_ID = B.JOKYO_ID \n")
			// 2006/08/18 sai	
				//.append("INNER JOIN MASTER_KIKAN MASTER_KIKAN ")
				//.append("ON MASTER_KIKAN.SHOZOKU_CD = B.SHOZOKU_CD")
				;

		//�����������쐬����
        //�폜�t���O��ݒ�
		sbWhere.append(" WHERE B.DEL_FLG = 0 \n")
				.append("AND A.DEL_FLG = 0 \n");

// 2006/08/17 dyh update start �����F��Ŏ��Ƌ敪�̔��f���ǉ����Ă���B
        //���Ƌ敪��ݒ�
        sbWhere.append( " AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(jigyoKubun));
//		if(searchInfo.getJigyoKubun() != null){
//			sbWhere.append( " AND B.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()));
//		}
//		else{
//			//���Ƌ敪�����݂��Ȃ������ꍇ�ɂ͊�Ռ������Z�b�g
//			//������̈�̃`�F�b�N���X�g����J�ڂ���ꍇ�ɂ͂��Ȃ炸���Ƌ敪���Z�b�g����Ă��邽��
//			sbWhere.append(" AND B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN );
//		}

        // ���ƃR�[�h��ݒ�(��Ղ̏ꍇ�̂�)
        if(IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKubun)){
            sbWhere.append(" AND SUBSTR(B.JIGYO_ID,3,5) IN (");
//          2006/08/18 ���ƃR�[�h'00062'��ǉ�
            sbWhere.append("'00061','00062','00111','00121','00131')");
        }
// 2006/08/17 dyh update end

        //2006.11.07 iso ���发�ނ̌����ɓ���V�K�����܂܂�Č���������Ȃ��o�O�C��
        // ���ƃR�[�h��ݒ�(����̈�̏ꍇ�A�p���݂̂��J�E���g����)
        if(IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(jigyoKubun)){
            sbWhere.append(" AND SUBSTR(B.JIGYO_ID,3,5) IN (");
            sbWhere.append("'00021')");
        }
        
		if (searchInfo.getShozokuCd() != null
			&& !searchInfo.getShozokuCd().equals("")) { //�����@�փR�[�h
			sbWhere.append(" AND C.SHOZOKU_CD = '"
					+ EscapeUtil.toSqlString(searchInfo.getShozokuCd())
					+ "'");
		}

		//��ID��03,04�ȊO�̏ꍇ�ɑΉ����邽��
		String[] jokyoList = searchInfo.getSearchJokyoId();
		if(jokyoList != null && jokyoList.length != 0){
//		 2006/08/18 sai B.JOKYO_ID�ɏC��
			//sbWhere.append(" AND C.JOKYO_ID IN(")
			sbWhere.append(" AND B.JOKYO_ID IN(")	
//		 2006/08/18 sai			
				.append(StringUtil.changeArray2CSV(jokyoList, true))
				.append(")");
		}

		String cancellationFlag = searchInfo.getCancellationFlag();
		//�󗝏󋵂��S�Ă̏ꍇ
		if(cancellationFlag != null && cancellationFlag.equals("0")){
			sbWhere.append(" AND (C.CANCEL_FLG = '1' OR C.JOKYO_ID <> '03') ");
		}
		//�󗝏󋵂��m������̏ꍇ
		else if(cancellationFlag != null && cancellationFlag.equals("1")){
			sbWhere.append(" AND C.CANCEL_FLG = '")
				.append(EscapeUtil.toSqlString(cancellationFlag))
				.append("'");
		}
		sbWhere.append(" )");

		//���茤���̈�̏ꍇ
		if (IJigyoKubun.JIGYO_KUBUN_TOKUTEI.equals(jigyoKubun)){
			//�v�挤���A���匤��
			sbSelecct.append(sbFrom.toString())
					.append("INNER JOIN MASTER_RYOIKI D \n")
					.append("ON B.RYOIKI_NO = D.RYOIKI_NO \n")
					.append("AND B.KOMOKU_NO = D.KOMOKU_NO \n");

			sbSelecct.append(sbWhere.toString())
					.append(" T1,")
					.append(sbSelecct2.toString())
					.append(sbFrom.toString())
					.append(sbWhere.toString())
					.append(" T2");

		}
		//��Ղ̏ꍇ
		else{
			sbSelecct.append(sbFrom.toString()).append(sbWhere.toString());
		}

		if (log.isDebugEnabled()) {
			log.debug("query:" + sbSelecct);
		}

		//-----------------------
		// �y�[�W���擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			return SelectUtil.selectCsvList(connection, sbSelecct.toString(), true);

		}
		catch(NoDataFoundException e){
			throw new NoDataFoundException("�Y�������񂪑��݂��܂���ł����B",new ErrorInfo("errors.4004"),e);}
		catch (DataAccessException e) {
			throw new ApplicationException("�f�[�^��������DB�G���[���������܂����B",new ErrorInfo("errors.4004"),e);}
		finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#blnCheckListAcceptUnacceptable(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.CheckListSearchInfo)
	 */
	public List CheckListAcceptUnacceptable(
						UserInfo userInfo,
						CheckListSearchInfo searchInfo
						)
			throws
			NoDataFoundException,
			ApplicationException
	{

/** �y�[�W���̌��������s���Ď󗝁A�s�󗝂̑ΏۂƂȂ�����擾 **/
		Page result = null;
		try{
			result = selectListData(userInfo, searchInfo);
			if (result == null) {
				//���������ꍇ�ɂ͗�O���X���[
				throw new ApplicationException("�󗝏��̎擾�����ŃG���[���������܂����B",new ErrorInfo("errors.5002"));
			}
		}catch(NoDataFoundException ex){
			throw new NoDataFoundException("�󗝏��̎擾�����ŃG���[���������܂����B",new ErrorInfo("errors.5002"),ex);
		}catch(ApplicationException ex){
			throw new ApplicationException("�󗝏��̎擾�����ŃG���[���������܂����B",new ErrorInfo("errors.4001"),ex);
		}finally{
		}

		//------�\����ID���ږ��FSHINSEISHA_ID
		List lstErrors				= new ArrayList();
		String shozokuCd			= ShinseiSearchInfo.ORDER_BY_SHOZOKU_CD;
		String jigyoId				= ShinseiSearchInfo.ORDER_BY_JIGYO_ID;
		String systemNo				= ShinseiSearchInfo.ORDER_BY_SYSTEM_NO;
		ArrayList shozokuCdArray	= new ArrayList();					//------����CD�̔z��
		ArrayList jigyoIdArray		= new ArrayList();					//------����ID�̔z��
		ArrayList systemNoArray		= new ArrayList();					//------SYSTEM_NO�̔z��
        
// 2007/02/06 ���u�j�@�ǉ���������
        /** ���O : �X�V��\����ID���擾  */                   
         String afterJokyoId= searchInfo.getChangeJokyoId();                                      
//2007/02/06�@���u�j�@�ǉ������܂� 
            
		//------�\���҂̐����J��Ԃ�
		for(int i = 0; i < result.getSize(); i++){
			//------�e�\���҂̏����擾
			HashMap juriDataMap		= (HashMap) result.getList().get(i);
			Object shozokuData		= juriDataMap.get(shozokuCd);
			Object jigyoData		= juriDataMap.get(jigyoId);
			Object systemData		= juriDataMap.get(systemNo);
			//------�\����ID�Ƀf�[�^������ꍇ�͔z��Ɋi�[
			if (shozokuData != null && !shozokuData.equals("")
				&& jigyoData != null && !jigyoData.equals("")
				&& systemData!= null && !systemData.equals("")) {
					shozokuCdArray.add(shozokuData);
					jigyoIdArray.add(jigyoData);
					systemNoArray.add(systemData);
			}
		}
		//��ID���擾
		HashMap dataMap = (HashMap)result.getList().get(0);
		Object jokyoID = dataMap.get("JOKYO_ID");
		searchInfo.setJokyoId(jokyoID.toString());

/** �󗝁A�s�󗝂̏����𔻕ʂ��Ď��s **/
		Connection connection = null;
// 2007/02/06 ���u�j�@�ǉ���������
        /** ���O �X�V�O */                   
            statusLog.info( " �`�F�b�N���X�g�󗝓o�^�O , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                    + " , ����ID : " + (String)jigyoIdArray.get(0) + " , �����@�փR�[�h : " + (String)shozokuCdArray.get(0) + " , �X�V�O�\����ID : " + jokyoID);                                     
//2007/02/06�@���u�j�@�ǉ������܂�
		try{
			connection = DatabaseUtil.getConnection();			//�R�l�N�V�����̎擾
// 2006/06/20 dyh update start �����FCheckListSearchInfo�萔��ύX
//            if(searchInfo.getChangeJokyoId().equals(StatusCode.STATUS_GAKUSIN_JYURI)){
			if(searchInfo.getChangeJokyoId().equals(StatusCode.STATUS_GAKUSIN_JYURI)){
// 2006/06/20 dyh update end
				// ��
				lstErrors = CheckListJuri(
						searchInfo, userInfo, connection,
						shozokuCdArray, jigyoIdArray, systemNoArray
						);
			}
// 2006/06/20 dyh update start �����FCheckListSearchInfo�萔��ύX
//            else if(searchInfo.getChangeJokyoId().equals(StatusCode.STATUS_GAKUSIN_FUJYURI)){
			else if(searchInfo.getChangeJokyoId().equals(StatusCode.STATUS_GAKUSIN_FUJYURI)){
// 2006/06/20 dyh update end
				// �s��
				for (int i = 0; i < result.getSize(); i++) {
					ShinseiDataPk addPk = new ShinseiDataPk(systemNoArray.get(i).toString());
					EntryFujuriInfo(
							userInfo, connection, addPk,
							searchInfo.getJuriComment(),
							null							//�����ԍ�(��)
							);
				}  
				//CHECKLISTINFO�̍X�V
				CheckListInfoDao dao = new CheckListInfoDao(userInfo);
				dao.updateCheckListInfo(connection, searchInfo, true);
			}
		}catch(SystemBusyException ex){
			throw new ApplicationException("�󗝏��̎擾�����ŃG���[���������܂����B",new ErrorInfo("errors.4001"),ex);
		}catch(DataAccessException ex){
			throw new ApplicationException("�󗝏��̎擾�����ŃG���[���������܂����B",new ErrorInfo("errors.4001"),ex);
//		}catch(NoDataFoundException ex){
//			throw new NoDataFoundException(ex.getMessage());
		}catch(ApplicationException ex){
			throw new ApplicationException("�󗝏��̎擾�����ŃG���[���������܂����B",new ErrorInfo("errors.4001"),ex);
		}finally{
			try{
				//�R�~�b�g�����[���o�b�N
				if(lstErrors.isEmpty()){
					DatabaseUtil.commit(connection);
// 2007/02/06 ���u�j�@�ǉ���������
                    /** ���O �X�V�� */                   
                        statusLog.info( " �`�F�b�N���X�g�󗝓o�^�� , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                                + " , ����ID : " + searchInfo.getJigyoId() + " , �����@�փR�[�h : " + searchInfo.getShozokuCd() + " , �X�V��\����ID : " + afterJokyoId);                                     
// 2007/02/06�@���u�j�@�ǉ������܂�  
				}else{
					DatabaseUtil.rollback(connection);
// 2007/02/06 ���u�j�@�ǉ���������
                    /** ���O �X�V���s */                   
                        statusLog.info( " �`�F�b�N���X�g�󗝓o�^���s , ���[�U��� : " + userInfo.getRole() + " , ���O�C��ID : " + userInfo.getId() 
                                + " , ����ID : " + searchInfo.getJigyoId() + " , �����@�փR�[�h : " + searchInfo.getShozokuCd());                                     
// 2007/02/06�@���u�j�@�ǉ������܂�  
				}
			}catch(TransactionException e){
				throw new ApplicationException("��ID�X�V����DB�G���[���������܂���", new ErrorInfo("errors.4002"), e);
			}finally{
			}
			DatabaseUtil.closeConnection(connection);
		}
		return lstErrors;
	}


	/**
	 * �`�F�b�N���X�g�ł̎󗝏��������s����i��ՁA����j
	 * @param		checkInfo
	 * @param		userInfo
	 * @param		connection
	 * @param		shozokuArray
	 * @param		jigyoArray
	 * @param		systemArray
	 * @throws		ApplicationException
	 */
	public List CheckListJuri(
			CheckListSearchInfo checkInfo,
			UserInfo userInfo,
			Connection connection,
			List shozokuArray,
			List jigyoArray,
			List systemArray
			)
			throws ApplicationException {

		List lstErrors				= new ArrayList();
		String data					= null;
		HashSet set					= new HashSet();
		ShinseiDataPk shinseiPk		= new ShinseiDataPk();
		CheckListInfoDao dao		= new CheckListInfoDao(userInfo);

		//�󗝂��\�Ȃ̂́u�w�U��t��(04)�v�u�w�U�s��(07)�v�̂�
// 2006/06/20 dyh update start �����FCheckListSearchInfo�萔��ύX
//        if(checkInfo.getJokyoId() == null ||
//                (!checkInfo.getJokyoId().equals(StatusCode.STATUS_GAKUSIN_SHORITYU) &&
//                !checkInfo.getJokyoId().equals(StatusCode.STATUS_GAKUSIN_FUJYURI))
//                ){
		if(checkInfo.getJokyoId() == null ||
			(!checkInfo.getJokyoId().equals(StatusCode.STATUS_GAKUSIN_SHORITYU) &&
			!checkInfo.getJokyoId().equals(StatusCode.STATUS_GAKUSIN_FUJYURI))
			){
// 2006/06/20 dyh update end
			throw new ApplicationException(
					"�󗝉\�ȏ�Ԃ̉���ł͂���܂���B",
					new ErrorInfo("errors.9015")
					);
		}

		//�����҂̑��݃`�F�b�N
		try{
			lstErrors = chkKenkyushaExist(userInfo, checkInfo, null);
			if(!lstErrors.isEmpty()){
				return lstErrors;
			}
			for(int i = 0; i < shozokuArray.size(); i++){							//�f�[�^�̌����󗝓o�^���s��
				data = (String)jigyoArray.get(i) + (String)shozokuArray.get(i);		//����ID�Ə���CD(CHCKLISTINFO�e�[�u���̎�L�[)�𕶎���ɂ���
				if(!set.contains(data)){											//���Ɏ�L�[������HashSet�Ɋi�[����Ă���ꍇ�̓`�F�b�N���X�g�̍X�V�͍s��Ȃ�
					set.add(data);													//HashSet�Ɏ�L�[��������i�[
					checkInfo.setJigyoId((String)jigyoArray.get(i));				//��ID�X�V
					checkInfo.setShozokuCd((String)shozokuArray.get(i));			//
					dao.updateCheckListInfo(connection, checkInfo, true);			//�`�F�b�N���X�g�̍X�V
				}
				shinseiPk.setSystemNo((String)systemArray.get(i));					//�\�����̎󗝏���
				EntryJuriInfo(
						userInfo, shinseiPk, connection, checkInfo.getJuriComment(),
						null														//�󗝐����ԍ�
						);
			}
		}catch(DataAccessException e){
			throw new ApplicationException("��ID�X�V����DB�G���[���������܂���", new ErrorInfo("errors.4002"), e);
		}catch(ApplicationException ex){
			throw new ApplicationException("�����҂̌�������DB�G���[���������܂����B",new ErrorInfo("errors.4004"),ex);
		}
		return lstErrors;
	}


	/**
	 * �󗝏��̓o�^�����s����
	 * @param userInfo
	 * @param shinseiDataPk
	 * @param connection
	 * @param comment
	 * @param seiriNo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public synchronized void EntryJuriInfo(
			UserInfo userInfo,
			ShinseiDataPk shinseiDataPk,
			Connection connection,
			String comment,
			String seiriNo)
		throws
			NoDataFoundException,
			ApplicationException
		{
//delete start dyh 2006/2/10 �����F�g�p���Ȃ��̕ϐ�
//		boolean success		= false;
//delete end dyh 2006/2/10
		ShinseiDataInfoDao dao	= new ShinseiDataInfoDao(userInfo);		//�\���f�[�^�Ǘ�DAO
		try {
			//�r������̂��ߊ����f�[�^���擾����
			ShinseiDataInfo existInfo = null;
			try{
				existInfo = dao.selectShinseiDataInfoForLock(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException("�\�����Ǘ��f�[�^�r���擾����DB�G���[���������܂����B", new ErrorInfo("errors.4001"), e);
			}

			//---�\���f�[�^�폜�t���O�`�F�b�N---
			String delFlag = existInfo.getDelFlg();
			if(FLAG_APPLICATION_DELETE.equals(delFlag))
			{
				throw new ApplicationException(
					"���Y�\���f�[�^�͍폜����Ă��܂��BSystemNo=" + shinseiDataPk.getSystemNo(),
					new ErrorInfo("errors.9001"));
			}

			//---�\���f�[�^�X�e�[�^�X�`�F�b�N---
			String jyokyoId = existInfo.getJokyoId();
// 2006/06/20 dyh update start �����FStatusCode�萔��ύX
//            if( !(StatusCode.STATUS_GAKUSIN_SHORITYU.equals(jyokyoId)) &&
//                     !(StatusCode.STATUS_GAKUSIN_JYURI.equals(jyokyoId)) &&
//                      !(StatusCode.STATUS_GAKUSIN_FUJYURI.equals(jyokyoId))
//                      ){
			if( !(StatusCode.STATUS_GAKUSIN_SHORITYU.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_GAKUSIN_JYURI.equals(jyokyoId)) &&
				  !(StatusCode.STATUS_GAKUSIN_FUJYURI.equals(jyokyoId))
				  ){
// 2006/06/20 dyh update end
				//---�w�U�������A�w�U�󗝁A�w�U�s�󗝈ȊO�̏ꍇ�̓G���[
				throw new ApplicationException(
					"���Y�\���f�[�^�͎󗝉\�ȃX�e�[�^�X�ł͂���܂���BSystemNo="
					+ shinseiDataPk.getSystemNo(),
					new ErrorInfo("errors.9015"));
			}

			//---�󗝓o�^���̏d���\���`�F�b�N---
			if(CHECK_DUPLICACATION_FLAG){
				int count = 0;
				try{
					count = dao.countDuplicateApplicationForJuri(connection, existInfo);
				}catch(DataAccessException e){
					throw new ApplicationException("�\�����Ǘ��f�[�^��������DB�G���[���������܂����B", new ErrorInfo("errors.4004"), e);
				}
				//�d���`�F�b�N����
				if(count != 0){
					throw new ApplicationException(
							"�\���󗝓o�^���ɏd���\����������܂����BSystemNo="
							+ shinseiDataPk.getSystemNo(),
							new ErrorInfo("errors.9017"));
				}
			}

			//---DB�X�V---
			try {
				try{
					ShinsaKekkaInfoDao shinsaKekkaDao = new ShinsaKekkaInfoDao(userInfo);	//---�R�����ʃe�[�u�����쐬---
					shinsaKekkaDao.deleteShinsaKekkaInfo(connection, shinseiDataPk);		//���s�f�[�^���폜

					//�V�K�f�[�^���쐬
					ShinsaKekkaInfo shinsaKekkaInfo = new ShinsaKekkaInfo();
					shinsaKekkaInfo.setSystemNo(existInfo.getSystemNo());								//�V�X�e���ԍ�
					shinsaKekkaInfo.setUketukeNo(existInfo.getUketukeNo());								//�\���ԍ�
					shinsaKekkaInfo.setJigyoKubun(existInfo.getKadaiInfo().getJigyoKubun());			//���Ƌ敪
					shinsaKekkaInfo.setShinsaKubun(existInfo.getKadaiInfo().getShinsaKubun());			//�R���敪
					shinsaKekkaInfo.setJigyoId(existInfo.getJigyoId());									//����ID
					shinsaKekkaInfo.setJigyoName(existInfo.getJigyoName());								//���Ɩ�
					shinsaKekkaInfo.setBunkaSaimokuCd(existInfo.getKadaiInfo().getBunkaSaimokuCd());	//�זڔԍ�
					shinsaKekkaInfo.setShinsaJokyo("0");												//�R����
					
					//TODO 2�i�K�R���@�_�~�[�f�[�^����Ղ̏ꍇ��12�A����ȊO�̏ꍇ��3�쐬����B
					int dummyCnt = 0;
//2006/04/27 �ǉ���������                    
//					if(IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(shinsaKekkaInfo.getJigyoKubun())){
					if(IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(shinsaKekkaInfo.getJigyoKubun())
                            || IJigyoKubun.JIGYO_KUBUN_WAKATESTART.equals(shinsaKekkaInfo.getJigyoKubun())
                            || IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI.equals(shinsaKekkaInfo.getJigyoKubun())){
//�c�@�ǉ������܂�                        
						dummyCnt = IShinsainWarifuri.SHINSAIN_NINZU_KIBAN;
					}else{
						dummyCnt = IShinsainWarifuri.SHINSAIN_NINZU_GAKUSOU;
					}
					//�R�����ԍ��A�V�[�P���X�ԍ����قȂ郌�R�[�h���쐬
					for(int i = 0 ; i < dummyCnt ; i++){
						//���������킹��
						if(i < 9){
							shinsaKekkaInfo.setShinsainNo("@00000"+ new Integer(i+1).toString());	//�R�����ԍ�(7��)
						}else{
							shinsaKekkaInfo.setShinsainNo("@0000"+ new Integer(i+1).toString());	//�R�����ԍ�(7��)
						}
						shinsaKekkaInfo.setSeqNo(new Integer(i+1).toString());					//�V�[�P���X�ԍ�
						shinsaKekkaDao.insertShinsaKekkaInfo(connection, shinsaKekkaInfo);
					}
				}catch(DataAccessException e){
					throw new ApplicationException( "�R�����ʏ��o�^����DB�G���[���������܂����B", new ErrorInfo("errors.4001"), e);
				}

				try{
					//�X�V�f�[�^���Z�b�g����
// 2006/06/20 dyh update start �����FStatusCode�萔��ύX
//                    if((StatusCode.SAISHINSEI_FLG_SAISHINSEITYU).equals(existInfo.getSaishinseiFlg())){
//                        //�\���t���O���u1�i�Đ\�����j�v�̏ꍇ�́u2�i�Đ\���ς݁j�v�ɕύX
//                        existInfo.setSaishinseiFlg(StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI);
//                    }
//                    existInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_JYURI); //�\���󋵁u06�F�w�U�󗝁v
					if(StatusCode.SAISHINSEI_FLG_SAISHINSEITYU.equals(existInfo.getSaishinseiFlg())){
						//�\���t���O���u1�i�Đ\�����j�v�̏ꍇ�́u2�i�Đ\���ς݁j�v�ɕύX
						existInfo.setSaishinseiFlg(StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI);
					}
					existInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);	//�\���󋵁u06�F�w�U�󗝁v
//2006/06/20 dyh update end
					existInfo.setJuriKekka(FLAG_JURI_KEKKA_JURI);				//�󗝌���
					existInfo.setJuriBiko(comment);								//�󗝌��ʔ��l
					existInfo.setSeiriNo(seiriNo);								//�����ԍ�
					existInfo.setJyuriDate(new Date());							//�w�U�󗝓�
					dao.updateShinseiDataInfo(connection, existInfo, true);
				}catch(DataAccessException e){
					throw new ApplicationException( "�\�����X�V����DB�G���[���������܂����B", new ErrorInfo("errors.4001"), e);
				}
//delete start dyh 2006/2/10 �����F�g�p���Ȃ��̕ϐ�
//				success = true;
//delete end dyh 2006/2/10
			}catch (ApplicationException e){
				throw new ApplicationException("�\�����X�V����DB�G���[���������܂����B", new ErrorInfo("errors.4001"), e);
			}
		}finally{
			//�R�~�b�g�����[���o�b�N�͏�ʑ��Ŏ��s
			//�R�l�N�V�����̐ؒf�͏�ʑ��Ŏ��s
		}
	}


	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ICheckListMaintenance#EntryFujuriInfo(jp.go.jsps.kaken.model.vo.UserInfo, java.sql.Connection, jp.go.jsps.kaken.model.vo.ShinseiDataPk, java.lang.String, java.lang.String)
	 */
	public boolean EntryFujuriInfo(
			UserInfo userInfo,
			Connection connection,
			ShinseiDataPk shinseiDataPk,
			String comment,
			String seiriNo)
            throws NoDataFoundException, ApplicationException {
        boolean success = false;
        try {
            ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo); // �\���f�[�^�Ǘ�DAO

            // �r������̂��ߊ����f�[�^���擾����
            ShinseiDataInfo existInfo = null;
            try {
                existInfo = dao.selectShinseiDataInfoForLock(connection,
                        shinseiDataPk, true);
            }
            catch (NoDataFoundException e) {
                throw e;
            }
            catch (DataAccessException e) {
                throw new ApplicationException(
                        "�\�����Ǘ��f�[�^�r���擾����DB�G���[���������܂����B",
                        new ErrorInfo("errors.4001"), e);
            }

            // ---�\���f�[�^�폜�t���O�`�F�b�N---
            String delFlag = existInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException(
                        "���Y�\���f�[�^�͍폜����Ă��܂��BSystemNo="
                        + shinseiDataPk.getSystemNo(),
                        new ErrorInfo("errors.9001"));
            }

			//---�\���f�[�^�X�e�[�^�X�`�F�b�N---
			String jyokyoId = existInfo.getJokyoId();
// 2006/06/20 dyh update start �����FStatusCode�萔��ύX
//            if(!(StatusCode.STATUS_GAKUSIN_SHORITYU.equals(jyokyoId)) &&
//                     !(StatusCode.STATUS_GAKUSIN_JYURI.equals(jyokyoId)) &&
//                      !(StatusCode.STATUS_GAKUSIN_FUJYURI.equals(jyokyoId))){
			if(!(StatusCode.STATUS_GAKUSIN_SHORITYU.equals(jyokyoId)) &&
				!(StatusCode.STATUS_GAKUSIN_JYURI.equals(jyokyoId)) &&
				!(StatusCode.STATUS_GAKUSIN_FUJYURI.equals(jyokyoId))){
// 2006/06/20 dyh update end
					//---�w�U�������A�w�U�󗝁A�w�U�s�󗝈ȊO�̏ꍇ�̓G���[
				throw new ApplicationException(
                        "���Y�\���f�[�^�͕s�󗝉\�ȃX�e�[�^�X�ł͂���܂���BSystemNo="
						+ shinseiDataPk.getSystemNo(),
						new ErrorInfo("errors.9016"));
			}

			//---DB�X�V---
			try{
				try{
					ShinsaKekkaInfoDao shinsaKekkaDao = new ShinsaKekkaInfoDao(userInfo);		//�R�����ʃe�[�u�����쐬
					shinsaKekkaDao.deleteShinsaKekkaInfo(connection, shinseiDataPk);			//���s�f�[�^���폜
				}catch(DataAccessException e){
					throw new ApplicationException("�R�����ʏ��폜����DB�G���[���������܂����B", new ErrorInfo("errors.4001"), e);
				}

				try{
					//�X�V�f�[�^���Z�b�g����
// 2006/06/20 dyh update start �����FStatusCode�萔��ύX
//                    if((StatusCode.SAISHINSEI_FLG_SAISHINSEITYU).equals(existInfo.getSaishinseiFlg())){
//                        //�\���t���O���u1�i�Đ\�����j�v�̏ꍇ�́u2�i�Đ\���ς݁j�v�ɕύX
//                        existInfo.setSaishinseiFlg(StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI);
//                    }
//                    existInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_FUJYURI);       //�\���󋵁u07�F�w�U�s�󗝁v
					if((StatusCode.SAISHINSEI_FLG_SAISHINSEITYU).equals(existInfo.getSaishinseiFlg())){
						//�\���t���O���u1�i�Đ\�����j�v�̏ꍇ�́u2�i�Đ\���ς݁j�v�ɕύX
						existInfo.setSaishinseiFlg(StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI);
					}
					existInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_FUJYURI);		//�\���󋵁u07�F�w�U�s�󗝁v
// 2006/06/20 dyh update end
					existInfo.setJuriKekka(FLAG_JURI_KEKKA_FUJURI);						//�󗝌���
					existInfo.setJuriBiko(comment);										//�󗝌��ʔ��l
					existInfo.setSeiriNo(seiriNo);										//�����ԍ�
					existInfo.setJyuriDate(new Date());									//�w�U�󗝓�
					dao.updateShinseiDataInfo(connection, existInfo, true);					//������̍X�V
					success = true;
				}catch(DataAccessException e){
					throw new ApplicationException("�\�����X�V����DB�G���[���������܂����B", new ErrorInfo("errors.4001"), e);
				}
			}catch(ApplicationException e){
				throw new ApplicationException("�\�����X�V����DB�G���[���������܂����B", new ErrorInfo("errors.4001"), e);
			}
		}
		finally{
			//�R�~�b�g�����[���o�b�N�͏�ʑ��Ŏ��s
			//�R�l�N�V�����̐ؒf�͏�ʑ��Ŏ��s
		}
		return success;
	}
}