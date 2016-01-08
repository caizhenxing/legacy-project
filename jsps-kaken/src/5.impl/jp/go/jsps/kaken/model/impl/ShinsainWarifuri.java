/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.IShinsainWarifuri;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.*;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinsainPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.WarifuriInfo;
import jp.go.jsps.kaken.model.vo.WarifuriPk;
import jp.go.jsps.kaken.model.vo.WarifuriSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.CsvUtil;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �R��������U��N���X.<br />
 * <br />
 * <b>�T�v</b><br />
 * �R��������U����͐R�����ʃe�[�u���iSHINSAKEKKA�j�ɂĊǗ�����B<br />
 * �R�����ʃe�[�u���̃��R�[�h�͐\���ƂƂ��ɓo�^�����B�i�K�v�ȃ��R�[�h�͐\���̎�ނɂ���Č��܂��Ă���B�j<br />
 * ����U����̓o�^�́A�쐬�ς݂̃��R�[�h�ɑ΂��čX�V���s�����Ƃɂ��s���B<br />
 * �R�������X�V����ꍇ�́A�R���������̂ݍX�V���s���B���̍ہA�����R��������̐\���ɑ΂��ĕ����R�����邱�Ƃ͏o���Ȃ��B<br />
 * �R��������U������폜����ꍇ�́A���R�[�h��������Ԃɖ߂��B
 * 
 */
public class ShinsainWarifuri implements IShinsainWarifuri {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(ShinsainWarifuri.class);

	/** �R���˗��ʒm���t�@�C���i�[�t�H���_ */
	private static String IRAI_WORK_FOLDER = ApplicationSettings.getString(ISettingKeys.IRAI_WORK_FOLDER);		

	/** �R���˗���Word�t�@�C���i�[�t�H���_ */
	private static String IRAI_FORMAT_PATH = ApplicationSettings.getString(ISettingKeys.IRAI_FORMAT_PATH);		

	/** �R���˗���Word�t�@�C���� */
	private static String IRAI_FORMAT_FILE_NAME = ApplicationSettings.getString(ISettingKeys.IRAI_FORMAT_FILE_NAME);		

	/**
	 * CSV�t�@�C�����̐ړ����B
	 */
	public static final String CSV_FILENAME = "SHINSAIRAI";

	/** �\�����폜�t���O�i�폜�ς݁j */
	private static final String FLAG_APPLICATION_DELETE     = IShinseiMaintenance.FLAG_APPLICATION_DELETE;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsainWarifuri() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IShinsainWarifuri
	//---------------------------------------------------------------------

	/** 
	 * ����U�茋�ʏ���V�K�쐬����.<br/><br/> 
	 * �����������̂܂ܕԋp����B
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinseishaInfo)
	 */
	public WarifuriInfo insert(UserInfo userInfo, WarifuriInfo addInfo)
		throws ApplicationException {
			return addInfo;
	}

	/** 
	 * ����U�茋�ʏ����X�V����.<br/><br/>
	 * <b>1.�X�V�`�F�b�N</b><br/>
	 *	��������warifuriPk��OldShinsainNo�̒l�ƁAShinsainNo�̒l���r���A�����ł������ꍇ�͍X�V����Ă��Ȃ��̂ŁA
	 *	���������������ɏI������B<br/>
	 * <br/>
	 * <b>2.�R�������擾</b><br/>
	 *	�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	���R�[�h�����݂��Ȃ��ꍇ�͗�O��throw����B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		SI.SHINSAIN_ID SHINSAIN_ID
	 *		, MS.SHINSAIN_NO SHINSAIN_NO
	 *		, MS.JIGYO_KUBUN JIGYO_KUBUN
	 *		, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 *		, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 *		, MS.NAME_KANA_SEI NAME_KANA_SEI
	 *		, MS.NAME_KANA_MEI NAME_KANA_MEI
	 *		, MS.SHOZOKU_CD SHOZOKU_CD
	 *		, MS.SHOZOKU_NAME SHOZOKU_NAME
	 *		, MS.BUKYOKU_NAME BUKYOKU_NAME
	 *		, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 *		, MS.SOFU_ZIP SOFU_ZIP
	 *		, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 *		, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 *		, MS.SHOZOKU_TEL SHOZOKU_TEL
	 *		, MS.SHOZOKU_FAX SHOZOKU_FAX
	 *		, MS.URL URL
	 *		, MS.SENMON SENMON
	 *		, MS.KOSHIN_DATE KOSHIN_DATE
	 *		, MS.BIKO BIKO
	 *		, SI.PASSWORD PASSWORD
	 *		, SI.YUKO_DATE YUKO_DATE
	 *		, SI.DEL_FLG DEL_FLG
	 *	FROM 
	 *		MASTER_SHINSAIN MS
	 *		, SHINSAININFO SI
	 *	WHERE
	 *		SI.DEL_FLG = 0
	 *		AND MS.SHINSAIN_NO = ?
	 *		AND MS.JIGYO_KUBUN = ?
	 *		AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 *		AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1) </pre>
	 *	</td></tr>
	 *	</table><br>
	 *
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������warifuriPk�̕ϐ��iShinsainNo�j���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������warifuriPk�̕ϐ��iJigyoKubun�j���g�p����B</td></tr>
	 *	</table><br/>
	 * <b>3.�d���`�F�b�N</b><br/>
	 *	�����\���ɓ����R����������U���Ă��Ȃ����ǂ������`�F�b�N���߂ɁA�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	���R�[�h�����݂���ꍇ�͗�O��throw����B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		 A.SYSTEM_NO		--  �V�X�e����t�ԍ�
	 *		,A.UKETUKE_NO		--  �\���ԍ�
	 *		,A.SHINSAIN_NO		--  �R�����ԍ�
	 *		,A.JIGYO_KUBUN		--  ���Ƌ敪
	 *		,A.SEQ_NO			--  �V�[�P���X�ԍ�
	 *		,A.SHINSA_KUBUN		--  �R���敪
	 *		,A.SHINSAIN_NAME_KANJI_SEI	--  �R�������i�����|���j
	 *		,A.SHINSAIN_NAME_KANJI_MEI	--  �R�������i�����|���j
	 *		,A.NAME_KANA_SEI		--  �R�������i�t���K�i�|���j
	 *		,A.NAME_KANA_MEI		--  �R�������i�t���K�i�|���j
	 *		,A.SHOZOKU_NAME		--  �R���������@�֖�
	 *		,A.BUKYOKU_NAME		--  �R�������ǖ�
	 *		,A.SHOKUSHU_NAME		--  �R�����E��
	 *		,A.JIGYO_ID		--  ����ID
	 *		,A.JIGYO_NAME		--  ���Ɩ�
	 *		,A.BUNKASAIMOKU_CD		--  �זڔԍ�
	 *		,A.EDA_NO			--  �}��
	 *		,A.CHECKDIGIT		--  �`�F�b�N�f�W�b�g
	 *		,A.KEKKA_ABC		--  �����]���iABC�j
	 *		,A.KEKKA_TEN		--  �����]���i�_���j
	 *		,A.COMMENT1		--  �R�����g1
	 *		,A.COMMENT2		--  �R�����g2
	 *		,A.COMMENT3		--  �R�����g3
	 *		,A.COMMENT4		--  �R�����g4
	 *		,A.COMMENT5		--  �R�����g5
	 *		,A.COMMENT6		--  �R�����g6
	 *		,A.KENKYUNAIYO		--  �������e
	 *		,A.KENKYUKEIKAKU		--  �����v��
	 *		,A.TEKISETSU_KAIGAI		--  �K�ؐ�-�C�O
	 *		,A.TEKISETSU_KENKYU1		--  �K�ؐ�-�����i1�j
	 *		,A.TEKISETSU		--  �K�ؐ�
	 *		,A.DATO			--  �Ó���
	 *		,A.SHINSEISHA		--  ������\��
	 *		,A.KENKYUBUNTANSHA		--  �������S��
	 *		,A.HITOGENOMU		--  �q�g�Q�m��
	 *		,A.TOKUTEI			--  ������
	 *		,A.HITOES			--  �q�gES�זE
	 *		,A.KUMIKAE			--  ��`�q�g��������
	 *		,A.CHIRYO			--  ��`�q���×Տ�����
	 *		,A.EKIGAKU			--  �u�w����
	 *		,A.COMMENTS		--  �R�����g
	 *		,A.TENPU_PATH		--  �Y�t�t�@�C���i�[�p�X
	 *		,DECODE (
	 *			NVL(A.TENPU_PATH,'null') ,'null','FALSE','TRUE'
	 *		) TENPU_FLG		--  �Y�t�t�@�C���i�[�t���O
	 *					--   �Y�t�t�@�C���i�[�p�X��NULL:FALSE
	 *					--   NULL�ȊO:TRUE
	 *		,A.SHINSA_JOKYO		--  �R����
	 *		,A.BIKO			--  ���l
	 *	FROM
	 *		SHINSAKEKKA A
	 *	WHERE
	 *		SYSTEM_NO = '�V�X�e����t�ԍ�'
	 *		AND SHINSAIN_NO = '�R�����ԍ�'
	 *		AND JIGYO_KUBUN = '���Ƌ敪'
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">���V�X�e����t�ԍ��A�R�����ԍ��A���Ƌ敪�͑�������warifuriPk���擾����B</div><br />
	 *
	 * <b>4.�R�����ʎ擾�{�r������{���R�[�h�폜</b><br/>
	 *	�C���O�̐R�����ʏ����擾���r�����b�N���邽�߁A�ȉ���SQL�����s����B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		 A.SYSTEM_NO		--  �V�X�e����t�ԍ�
	 *		,A.UKETUKE_NO		--  �\���ԍ�
	 *		,A.SHINSAIN_NO		--  �R�����ԍ�
	 *		,A.JIGYO_KUBUN		--  ���Ƌ敪
	 *		,A.SEQ_NO			--  �V�[�P���X�ԍ�
	 *		,A.SHINSA_KUBUN		--  �R���敪
	 *		,A.SHINSAIN_NAME_KANJI_SEI	--  �R�������i�����|���j
	 *		,A.SHINSAIN_NAME_KANJI_MEI	--  �R�������i�����|���j
	 *		,A.NAME_KANA_SEI		--  �R�������i�t���K�i�|���j
	 *		,A.NAME_KANA_MEI		--  �R�������i�t���K�i�|���j
	 *		,A.SHOZOKU_NAME		--  �R���������@�֖�
	 *		,A.BUKYOKU_NAME		--  �R�������ǖ�
	 *		,A.SHOKUSHU_NAME		--  �R�����E��
	 *		,A.JIGYO_ID		--  ����ID
	 *		,A.JIGYO_NAME		--  ���Ɩ�
	 *		,A.BUNKASAIMOKU_CD		--  �זڔԍ�
	 *		,A.EDA_NO			--  �}��
	 *		,A.CHECKDIGIT		--  �`�F�b�N�f�W�b�g
	 *		,A.KEKKA_ABC		--  �����]���iABC�j
	 *		,A.KEKKA_TEN		--  �����]���i�_���j
	 *		,A.COMMENT1		--  �R�����g1
	 *		,A.COMMENT2		--  �R�����g2
	 *		,A.COMMENT3		--  �R�����g3
	 *		,A.COMMENT4		--  �R�����g4
	 *		,A.COMMENT5		--  �R�����g5
	 *		,A.COMMENT6		--  �R�����g6
	 *		,A.KENKYUNAIYO		--  �������e
	 *		,A.KENKYUKEIKAKU		--  �����v��
	 *		,A.TEKISETSU_KAIGAI		--  �K�ؐ�-�C�O
	 *		,A.TEKISETSU_KENKYU1		--  �K�ؐ�-�����i1�j
	 *		,A.TEKISETSU		--  �K�ؐ�
	 *		,A.DATO			--  �Ó���
	 *		,A.SHINSEISHA		--  ������\��
	 *		,A.KENKYUBUNTANSHA		--  �������S��
	 *		,A.HITOGENOMU		--  �q�g�Q�m��
	 *		,A.TOKUTEI			--  ������
	 *		,A.HITOES			--  �q�gES�זE
	 *		,A.KUMIKAE			--  ��`�q�g��������
	 *		,A.CHIRYO			--  ��`�q���×Տ�����
	 *		,A.EKIGAKU			--  �u�w����
	 *		,A.COMMENTS		--  �R�����g
	 *		,A.TENPU_PATH		--  �Y�t�t�@�C���i�[�p�X
	 *		,DECODE (
	 *			NVL(A.TENPU_PATH,'null') ,'null','FALSE','TRUE'
	 *	 	) TENPU_FLG		--  �Y�t�t�@�C���i�[�t���O
	 *					--   �Y�t�t�@�C���i�[�p�X��NULL:FALSE
	 *					--   NULL����Ȃ�TRUE
	 *		,A.SHINSA_JOKYO		--  �R����
	 *		,A.BIKO			--  ���l
	 *	FROM
	 *		SHINSAKEKKA A
	 *	WHERE
	 *		SYSTEM_NO = '�V�X�e����t�ԍ�'
	 *		AND SHINSAIN_NO = '�R�����ԍ�'
	 *		AND JIGYO_KUBUN = '���Ƌ敪'
	 *	FOR UPDATE
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">���V�X�e����t�ԍ��A�R�����ԍ��A���Ƌ敪�͑�������warifuriPk���擾����B�������A�R�����ԍ��͈ȑO�̔ԍ��iOldShinsainNo�j�𗘗p����B</div><br />
	 *	
	 *	�����Ĉȉ���SQL�Ń��R�[�h�̍폜���s���B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		SHINSAKEKKA
	 *	WHERE
	 *		SYSTEM_NO = '�V�X�e����t�ԍ�'
	 *		AND SHINSAIN_NO = '�R�����ԍ�'
	 *		AND JIGYO_KUBUN = '���Ƌ敪'
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">���V�X�e����t�ԍ��A�R�����ԍ��A���Ƌ敪�͑�������warifuriPk���擾����B�������A�R�����ԍ��͈ȑO�̔ԍ��iOldShinsainNo�j�𗘗p����B</div><br />
	 *
	 * <b>5.���R�[�h�o�^</b><br/>
	 *	�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	�قƂ�ǂ̏���4�Ŏ擾�����������̂܂܎g�p���邪�A�R�����ԍ��͐V�����ԍ��ŏ㏑������B
	 *	�܂��A�R����������2�Ŏ擾�����R�����}�X�^�̏��ŏ㏑������B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO SHINSAKEKKA(
	 *		SYSTEM_NO			--  �V�X�e����t�ԍ�
	 *		,UKETUKE_NO		--  �\���ԍ�
	 *		,SHINSAIN_NO		--  �R�����ԍ�
	 *		,JIGYO_KUBUN		--  ���Ƌ敪
	 *		,SEQ_NO			--  �V�[�P���X�ԍ�
	 *		,SHINSA_KUBUN		--  �R���敪
	 *		,SHINSAIN_NAME_KANJI_SEI	--  �R�������i�����|���j
	 *		,SHINSAIN_NAME_KANJI_MEI	--  �R�������i�����|���j
	 *		,NAME_KANA_SEI		--  �R�������i�t���K�i�|���j
	 *		,NAME_KANA_MEI		--  �R�������i�t���K�i�|���j
	 *		,SHOZOKU_NAME		--  �R���������@�֖�
	 *		,BUKYOKU_NAME		--  �R�������ǖ�
	 *		,SHOKUSHU_NAME		--  �R�����E��
	 *		,JIGYO_ID			--  ����ID
	 *		,JIGYO_NAME		--  ���Ɩ�
	 *		,BUNKASAIMOKU_CD		--  ���ȍזڃR�[�h
	 *		,EDA_NO			--  �}��
	 *		,CHECKDIGIT		--  �`�F�b�N�f�W�b�g
	 *		,KEKKA_ABC			--  �����]���iABC�j
	 *		,KEKKA_TEN			--  �����]���i�_���j
	 *		,COMMENT1			--  �R�����g1
	 *		,COMMENT2			--  �R�����g2
	 *		,COMMENT3			--  �R�����g3
	 *		,COMMENT4			--  �R�����g4
	 *		,COMMENT5			--  �R�����g5
	 *		,COMMENT6			--  �R�����g6
	 *		,KENKYUNAIYO		--  �������e
	 *		,KENKYUKEIKAKU		--  �����v��
	 *		,TEKISETSU_KAIGAI		--  �K�ؐ�-�C�O
	 *		,TEKISETSU_KENKYU1		--  �K�ؐ�-�����i1�j
	 *		,TEKISETSU			--  �K�ؐ�
	 *		,DATO			--  �Ó���
	 *		,SHINSEISHA		--  ������\��
	 *		,KENKYUBUNTANSHA		--  �������S��
	 *		,HITOGENOMU		--  �q�g�Q�m��
	 *		,TOKUTEI			--  ������
	 *		,HITOES			--  �q�gES�זE
	 *		,KUMIKAE			--  ��`�q�g��������
	 *		,CHIRYO			--  ��`�q���×Տ�����
	 *		,EKIGAKU			--  �u�w����
	 *		,COMMENTS			--  �R�����g
	 *		,TENPU_PATH		--  �Y�t�t�@�C���i�[�p�X
	 *		,SHINSA_JOKYO		--  �R����
	 *		,BIKO			--  ���l
	 *	) VALUES (
	 *		?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,
	 *		?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?
	 *	)
	 *	</td></tr>
	 *	</table><br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>4�Ŏ擾�����V�X�e����t�ԍ����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>4�Ŏ擾�����\���ԍ����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td style="color:882200">�������iwarifuriPk�j�̐R�����ԍ����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>4�Ŏ擾�������Ƌ敪���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�[�P���X�ԍ�</td><td>4�Ŏ擾�����V�[�P���X�ԍ����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R���敪</td><td>4�Ŏ擾�����R���敪���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�����|���j</td><td style="color:008855">2�Ŏ擾�����R�������i�����|���j���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�����|���j</td><td style="color:008855">2�Ŏ擾�����R�������i�����|���j���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td style="color:008855">2�Ŏ擾�����R�������i�t���K�i�|���j���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td style="color:008855">2�Ŏ擾�����R�������i�t���K�i�|���j���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R���������@�֖�</td><td style="color:008855">2�Ŏ擾�����R���������@�֖����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������ǖ�</td><td style="color:008855">2�Ŏ擾�����R�������ǖ����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����E��</td><td style="color:008855">2�Ŏ擾�����R�����E�����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����ID</td><td>4�Ŏ擾��������ID���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ɩ�</td><td>4�Ŏ擾�������Ɩ����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ȍזڃR�[�h</td><td>4�Ŏ擾�������ȍזڃR�[�h���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}��</td><td>4�Ŏ擾�����}�Ԃ��g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�`�F�b�N�f�W�b�g</td><td>4�Ŏ擾�����`�F�b�N�f�W�b�g���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����]���iABC�j</td><td>4�Ŏ擾���������]���iABC�j���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����]���i�_���j</td><td>4�Ŏ擾���������]���i�_���j���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g1</td><td>4�Ŏ擾�����R�����g1���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g2</td><td>4�Ŏ擾�����R�����g2���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g3</td><td>4�Ŏ擾�����R�����g3���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g4</td><td>4�Ŏ擾�����R�����g4���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g5</td><td>4�Ŏ擾�����R�����g5���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g6</td><td>4�Ŏ擾�����R�����g6���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�������e</td><td>4�Ŏ擾�����������e���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����v��</td><td>4�Ŏ擾���������v����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-�C�O</td><td>4�Ŏ擾�����K�ؐ�-�C�O���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-�����i1�j</td><td>4�Ŏ擾�����K�ؐ�-�����i1�j���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�K�ؐ�</td><td>4�Ŏ擾�����K�ؐ����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�Ó���</td><td>4�Ŏ擾�����Ó������g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������\��</td><td>4�Ŏ擾����������\�҂��g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�������S��</td><td>4�Ŏ擾�����������S�҂��g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�q�g�Q�m��</td><td>4�Ŏ擾�����q�g�Q�m�����g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������</td><td>4�Ŏ擾������������g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�q�gES�זE</td><td>4�Ŏ擾�����q�gES�זE���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��`�q�g��������</td><td>4�Ŏ擾������`�q�g�����������g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��`�q���×Տ�����</td><td>4�Ŏ擾������`�q���×Տ��������g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�u�w����</td><td>4�Ŏ擾�����u�w�������g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g</td><td>4�Ŏ擾�����R�����g���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�Y�t�t�@�C���i�[�p�X</td><td>4�Ŏ擾�����Y�t�t�@�C���i�[�p�X���g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R����</td><td>4�Ŏ擾�����R���󋵂��g�p����B</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���l</td><td>4�Ŏ擾�������l���g�p����B</td></tr>
	 *	</table><br/>
	 *
	 * 
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.WarifuriPk)
	 */
	public void update(UserInfo userInfo, WarifuriPk warifuriPk)
		throws ApplicationException {


		//---------------------------------------
		//�C������Ă��邩�ǂ������`�F�b�N
		//---------------------------------------
		//�R�����ԍ��i�C���O�j�ƐR�����ԍ��i�C����j����v���Ă���ꍇ
		if(warifuriPk.getOldShinsainNo().equals(warifuriPk.getShinsainNo())){
			//�����I��
			return;
		}

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			ShinsaKekkaInfoDao shinsaKekkaDao = new ShinsaKekkaInfoDao(userInfo);	
			//---------------------------------------
			//�R�����ԍ��`�F�b�N
			//---------------------------------------
			//�R�����}�X�^�ɊY������f�[�^�����݂��Ă���ǂ������`�F�b�N
			ShinsainInfo shinsainInfo = null;
			try {
				ShinsainPk shinsainPk = new ShinsainPk();
				shinsainPk.setShinsainNo(warifuriPk.getShinsainNo());		//�R�����ԍ�
				shinsainPk.setJigyoKubun(warifuriPk.getJigyoKubun());		//���Ƌ敪		
				ShinsainInfoDao shinsainDao = new ShinsainInfoDao(userInfo);
				shinsainInfo = shinsainDao.selectShinsainInfo(connection, shinsainPk);
			} catch (NoDataFoundException e) {
				throw e;
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�R�����}�X�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}
			
//�R�����ԍ��`�F�b�N�ŁA�R�����ʃe�[�u���̎��Ƌ敪�����������Ƃ��Ă��邽�߁A���߂Č�������K�v�Ȃ�			
//			//---------------------------------------
//			//�S�����ƃ`�F�b�N
//			//---------------------------------------
//			//�R�������̎��Ƌ敪�ƐR�����ʏ��̎��Ƌ敪����v���Ă��邩�ǂ������`�F�b�N
//			if(warifuriPk.getJigyoKubun() != null){
//				//��v���ĂȂ�������
//				if(!warifuriPk.getJigyoKubun().equals(shinsainInfo.getJigyoKubun())){
//					//��O����
//					throw new ApplicationException(
//						"���̐\���f�[�^�͒S�����Ƃł͂���܂���B" 
//						+ "�\���f�[�^�u�V�X�e���ԍ�'" + warifuriPk.getSystemNo() + "',���Ƌ敪'" + warifuriPk.getJigyoKubun() + "'�v,"
//						+ "�R�����u�R�����ԍ�'" + shinsainInfo.getShinsainNo() + "',���Ƌ敪'" + shinsainInfo.getJigyoKubun() + "'",
//						new ErrorInfo("errors.4010")); 
//				}
//			}
			

			//---------------------------------------
			//�d���`�F�b�N�p�̐R�����ʃL�[
			//�R�����ԍ��ɐR�����ԍ��i�C����j���Z�b�g
			//---------------------------------------
			ShinsaKekkaPk selectPk = new ShinsaKekkaPk();
			selectPk.setSystemNo(warifuriPk.getSystemNo());		//�V�X�e���ԍ�
			selectPk.setShinsainNo(warifuriPk.getShinsainNo());	//�R�����ԍ��i�C����j���Z�b�g
			selectPk.setJigyoKubun(warifuriPk.getJigyoKubun());	//���Ƌ敪			

			//---------------------------------------
			//�d���`�F�b�N
			//---------------------------------------
			//�����\���ɓ����R����������U���Ă��Ȃ����ǂ������`�F�b�N
			//�C����̐R�����ʏ����擾����
			try {
				shinsaKekkaDao.selectShinsaKekkaInfo(connection, selectPk);
				//NG
				throw new ApplicationException(
								"���łɓ���̐R�������o�^����Ă��܂��B�R�����ԍ�'" + selectPk.getShinsainNo() +"'," 
								+ "���Ƌ敪'" + selectPk.getJigyoKubun() + "'" , 
								new ErrorInfo("errors.4007", new String[]{"�R����"})
								);
			} catch (NoDataFoundException e) {
				//OK
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}
					
			//---------------------------------------
			//�X�V�p�̐R�����ʃL�[
			//�R�����ԍ��ɐR�����ԍ��i�C���O�j���Z�b�g
			//---------------------------------------
			ShinsaKekkaPk updatePk = new ShinsaKekkaPk();
			updatePk.setSystemNo(warifuriPk.getSystemNo());			//�V�X�e���ԍ�
			updatePk.setShinsainNo(warifuriPk.getOldShinsainNo());	//�R�����ԍ��i�C���O�j���Z�b�g
			updatePk.setJigyoKubun(warifuriPk.getJigyoKubun());		//���Ƌ敪		
			

			//---------------------------------------
			//�r������̂��ߊ����f�[�^���擾����
			//---------------------------------------
			ShinsaKekkaInfo shinsaKekkaInfo = null;
			try {
				shinsaKekkaInfo = shinsaKekkaDao.selectShinsaKekkaInfoForLock(connection, updatePk);
			} catch (NoDataFoundException e) {
				throw e;
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}

			//---------------------------------------
			//�R�����ʃe�[�u���̍폜
			//---------------------------------------
			//�C���O�̐R�����ʏ����폜����
			try {
				//DB�X�V
				shinsaKekkaDao.deleteShinsaKekkaInfo(connection, updatePk);
			} catch (NoDataFoundException e) {
				throw e;
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}			
			
//2005/11/04 �ǉ� start
			//---------------------------------------
			//�㗝�t���O�̃`�F�b�N
			//---------------------------------------
			String dairiFlg = shinsaKekkaInfo.getDairi();
			//�C���O�̐R�����ԍ����_�~�[�R�����̏ꍇ�A�㗝�t���O�𗧂Ă�i��Ղ̂݁j
//2006/05/31 �ǉ���������            
//			if(warifuriPk.getOldShinsainNo().startsWith("@") && warifuriPk.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)
            if(warifuriPk.getOldShinsainNo().startsWith("@") && ( warifuriPk.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_KIBAN) 
                    || warifuriPk.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART) 
                    || warifuriPk.getJigyoKubun().equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI))

//�c�@�ǉ������܂�                    
            ){
				dairiFlg = "1";
			}
//2005/11/04 �ǉ� end
//2005/11/08 �ǉ� start
			//---------------------------------------
			//ID�o�^���t�̍쐬
			//---------------------------------------
			Date koshin_date = new Date();
//2005/11/08 �ǉ� end

			//�R���������Z�b�g
			shinsaKekkaInfo.setShinsainNo(warifuriPk.getShinsainNo());					//�R�����ԍ��i�C����j
			shinsaKekkaInfo.setShinsainNameKanjiSei(shinsainInfo.getNameKanjiSei());	//�R�������i����-���j
			shinsaKekkaInfo.setShinsainNameKanjiMei(shinsainInfo.getNameKanjiMei());	//�R�������i����-���j
			shinsaKekkaInfo.setNameKanaSei(shinsainInfo.getNameKanaSei());				//�R�������i�t���K�i-���j
			shinsaKekkaInfo.setNameKanaMei(shinsainInfo.getNameKanaMei());				//�R�������i�t���K�i-���j
			shinsaKekkaInfo.setShozokuName(shinsainInfo.getShozokuName());				//�R���������@�֖�
			shinsaKekkaInfo.setBukyokuName(shinsainInfo.getBukyokuName());				//�R�������ǖ�
			shinsaKekkaInfo.setShokushuName(shinsainInfo.getShokushuName());			//�R�����E��
			shinsaKekkaInfo.setDairi(dairiFlg);											//�㗝�t���O	//2005/11/04 �ǉ�
			shinsaKekkaInfo.setKoshinDate(koshin_date);									//����U��X�V�� //2005/11/08 �ǉ�
			

			try {
				//DB�X�V
				shinsaKekkaDao.insertShinsaKekkaInfo(connection, shinsaKekkaInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}

			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;

		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * ����U�茋�ʏ��擾.<br/>
	 * <br />
	 * <b>1.���U���񌟍�</b><br/>
	 *	���N���X��search���\�b�h���g�p���Ċ���U�茋�ʏ�����������B
	 *	��������ہA�V�X�e���ԍ��A�R�����ԍ�����ю��Ƌ敪�͑�������pkInfo���擾����B<br />
	 *	<b>����ł͌������ʂ��Ȃ��ꍇ��NullPointer����������Ǝv���� �� <font color="red">�v�C��</font></b>
	 * <br />
	 * <b>2.����U�茋�ʏ��擾</b><br/>
	 *	1�Ŏ擾��������U�茋�ʏ���1���ڂ̃��R�[�h��WarifuriInfo�ɃZ�b�g����B
	 *	WarifuriInfo�ɃZ�b�g������͈ȉ��̒ʂ�B<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>�ϐ����i���{��j</td><td>�ϐ���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>Nendo</td><td>�N�x</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>Kaisu</td><td>��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>JigyoName</td><td>���Ɩ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SystemNo</td><td>�V�X�e���ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>UketukeNo</td><td>�\���ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KeiNameRyaku</td><td>�n���̋敪�i���́j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KadaiNameKanji</td><td>�����ۑ薼�i�a���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NameKanjiSei</td><td>�\���Җ��i������-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NameKanjiMei</td><td>�\���Җ��i������-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShozokuNameRyaku</td><td>�����@�֖��i���́j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BukyokuNameRyaku</td><td>���ǖ��i���́j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShokushuNameRyaku</td><td>�E���i���́j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainNo</td><td>�R�����ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainNameKanjiSei</td><td>�R�������i����-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainNameKanjiMei</td><td>�R�������i����-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainShozokuName</td><td>�R���������@�֖�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainBukyokuName</td><td>�R�������ǖ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>ShinsainShokuName</td><td>�R�����E��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>JigyoKubun</td><td>���Ƌ敪�i�R�����ʁj</td></tr>
	 *	</table><br/>
	 * <b>3.WarifuriInfo�ԋp</b><br/>
	 *	2�ō쐬����WarifuriInfo��ԋp����B
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsaKekkaPk)
	 */
	public WarifuriInfo select(UserInfo userInfo, ShinsaKekkaPk pkInfo)
		throws ApplicationException {

		WarifuriSearchInfo searchInfo = new WarifuriSearchInfo();
		
		searchInfo.setSystemNo(pkInfo.getSystemNo());		//�V�X�e���ԍ�
		searchInfo.setShinsainNo(pkInfo.getShinsainNo());	//�R�����ԍ�
		searchInfo.setJigyoKubun(pkInfo.getJigyoKubun());	//���Ƌ敪

		Page page = search(userInfo, searchInfo);
		HashMap editInfo = (HashMap)page.getList().get(0);

		WarifuriInfo warifuriInfo = new WarifuriInfo();
			
		warifuriInfo.setNendo((String)editInfo.get("NENDO"));									//�N�x
		warifuriInfo.setKaisu(editInfo.get("KAISU").toString());								//��
		warifuriInfo.setJigyoName((String)editInfo.get("JIGYO_NAME"));							//���Ɩ�
		warifuriInfo.setSystemNo((String)editInfo.get("SYSTEM_NO"));							//�V�X�e���ԍ�
		warifuriInfo.setUketukeNo((String)editInfo.get("UKETUKE_NO"));							//�\���ԍ�
		warifuriInfo.setKeiNameRyaku((String)editInfo.get("KEI_NAME_RYAKU"));					//�n���̋敪�i���́j
		warifuriInfo.setKadaiNameKanji((String)editInfo.get("KADAI_NAME_KANJI"));				//�����ۑ薼�i�a���j
		warifuriInfo.setNameKanjiSei((String)editInfo.get("NAME_KANJI_SEI"));					//�\���Җ��i������-���j
		warifuriInfo.setNameKanjiMei((String)editInfo.get("NAME_KANJI_MEI"));					//�\���Җ��i������-���j
		warifuriInfo.setShozokuNameRyaku((String)editInfo.get("SHOZOKU_NAME_RYAKU"));			//�����@�֖��i���́j
		warifuriInfo.setBukyokuNameRyaku((String)editInfo.get("BUKYOKU_NAME_RYAKU"));			//���ǖ��i���́j
		warifuriInfo.setShokushuNameRyaku((String)editInfo.get("SHOKUSHU_NAME_RYAKU"));			//�E���i���́j
		warifuriInfo.setShinsainNo((String)editInfo.get("SHINSAIN_NO"));						//�R�����ԍ�
		warifuriInfo.setShinsainNameKanjiSei((String)editInfo.get("SHINSAIN_NAME_KANJI_SEI"));	//�R�������i����-���j
		warifuriInfo.setShinsainNameKanjiMei((String)editInfo.get("SHINSAIN_NAME_KANJI_MEI"));	//�R�������i����-���j
		warifuriInfo.setShinsainShozokuName((String)editInfo.get("SHINSAIN_SHOZOKU_NAME"));		//�R���������@�֖�
		warifuriInfo.setShinsainBukyokuName((String)editInfo.get("SHINSAIN_BUKYOKU_NAME"));		//�R�������ǖ�
		warifuriInfo.setShinsainShokuName((String)editInfo.get("SHINSAIN_SHOKUSHU_NAME"));		//�R�����E��
//�����ԍ���ǉ��@2005/10/17
		warifuriInfo.setSeiriNo((String)editInfo.get("JURI_SEIRI_NO"));							//�����ԍ�  

		String jigyoKubun = new Integer(((Number) editInfo.get("JIGYO_KUBUN")).intValue()).toString();
		warifuriInfo.setJigyoKubun(jigyoKubun);						//���Ƌ敪�i�R�����ʁj
				
		return warifuriInfo;
	}

	/**
	 * ����U�茋�ʏ�����������.<br />
	 * <br />
	 * <b>1.����U�茋�ʏ�񌟍�</b><br/>
	 *	�ȉ��̌���SQL�����s����B
	 *	�Ȃ��A�������ʂ��Ȃ��ꍇ�A�������͌��ʌ�����serchInfo��MaxSize���傫���ꍇ�͗�O��throw����B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		B.SYSTEM_NO,			--  �V�X�e���ԍ�
	 *		A.NENDO,				--  �N�x
	 *		A.KAISU,				--  ��
	 *		B.JIGYO_NAME,			--  ���Ɩ�
	 *		B.UKETUKE_NO,			--  �\���ԍ�
	 *		A.KEI_NAME_RYAKU,			--  �n���̋敪�i���́j
	 *		A.KADAI_NAME_KANJI,			--  �����ۑ薼�i�a���j
	 *		A.NAME_KANJI_SEI,			--  �\���Ҏ����i������-���j
	 *		A.NAME_KANJI_MEI,			--  �\���Ҏ����i������-���j
	 *		A.SHOZOKU_NAME_RYAKU, 		--  �����@�֖��i���́j
	 *		A.BUKYOKU_NAME_RYAKU, 		--  ���ǖ��i���́j
	 *		A.SHOKUSHU_NAME_RYAKU, 		--  �E���i���́j
	 *		B.SHINSAIN_NO, 			--  �R�����ԍ�
	 *		B.JIGYO_KUBUN, 			--  ���Ƌ敪�i�R�����ʁj
	 *		B.SHINSAIN_NAME_KANJI_SEI,		--  �R�������i����-���j
	 *		B.SHINSAIN_NAME_KANJI_MEI,		--  �R�������i����-���j
	 *		B.SHOZOKU_NAME SHINSAIN_SHOZOKU_NAME,	--  �R���������@�֖�
	 *		B.BUKYOKU_NAME SHINSAIN_BUKYOKU_NAME,	--  �R�������ǖ�
	 *		B.SHOKUSHU_NAME SHINSAIN_SHOKUSHU_NAME,	--  �R�����E��
	 *		B.SHINSA_JOKYO,			--  �R�������t���O
	 *		A.JOKYO_ID				--  ��ID
	 *	FROM
	 *		(
	 *			SELECT * 
	 *			FROM SHINSEIDATAKANRI
	 *			WHERE DEL_FLG=0
	 *		) A,
	 *		SHINSAKEKKA B
	 *	WHERE
	 *		A.SYSTEM_NO = B.SYSTEM_NO
	 *	
	 *				<b><span style="color:#002288">�|�|���I��������1�|�|</span></b>
	 *	
	 *		AND (	--�@�X�e�[�^�X��
	 *			(A.JOKYO_ID = '06' AND A.SAISHINSEI_FLG IN ('0','2'))
	 *						--�@��,�Đ\���t���O
	 *						--  �i�����l�A�Đ\���ς݁j
	 *			OR (A.JOKYO_ID = '08')	--�@�R��������U�菈����
	 *			OR (A.JOKYO_ID = '09')	--�@����U��`�F�b�N����
	 *			OR (A.JOKYO_ID = '10')	--�@1���R����
	 *			OR (A.JOKYO_ID = '11')	--�@1���R������
	 *			OR (A.JOKYO_ID = '12')	--�@2���R������
	 *		)
	 *	
	 *				<b><span style="color:#882200">�|�|���I��������2�|�|</span></b>
	 *	
	 *	ORDER BY B.JIGYO_ID, B.UKETUKE_NO, B.SHINSAIN_NO
	 *	</td></tr>
	 *	</table><br />
	 *	<b><span style="color:#002288">���I��������1</span></b><br />
	 *	������searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr style="color:white;font-weight: bold"><td>�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>UketukeNo</td><td> AND B.UKETUKE_NO = '�\���ԍ�'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>jigyoCdValueList</td><td>AND SUBSTR(B.JIGYO_ID, 3, 5) IN ('���ƃR�[�h1','���ƃR�[�h2'�c)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�S�����Ƌ敪�i�����j</td><td>TantoJigyoKubun</td><td>AND B.JIGYO_KUBUN IN ('�S�����Ƌ敪1','�S�����Ƌ敪2'�c)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�N�x</td><td>Nendo</td><td>AND A.NENDO = '�N�x'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��</td><td>Kaisu</td><td>AND A.KAISU = '��'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i������-���j</td><td>NameKanjiSei</td><td>AND A.NAME_KANJI_SEI LIKE '%�\���Ҏ����i������-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i������-���j</td><td>NameKanjiMei</td><td>AND A.NAME_KANJI_MEI LIKE '%�\���Ҏ����i������-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i�t���K�i-���j</td><td>NameKanaSei</td><td>AND A.NAME_KANA_SEI LIKE '%�\���Ҏ����i�t���K�i-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i�t���K�i-���j</td><td>NameKanaMei</td><td>AND A.NAME_KANA_MEI LIKE '%�\���Ҏ����i�t���K�i-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i���[�}��-���j</td><td>NameRoSei</td><td>AND UPPER(A.NAME_RO_SEI) LIKE '%�\���Ҏ����i���[�}��-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i���[�}��-���j</td><td>NameRoMei</td><td>AND UPPER(A.NAME_RO_MEI) LIKE '%�\���Ҏ����i���[�}��-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>ShozokuCd</td><td>AND A.SHOZOKU_CD = '�����@�փR�[�h'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�זڔԍ�</td><td>BunkaSaimokuCd</td><td>AND A.BUNKASAIMOKU_CD = '�זڔԍ�'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�n���̋敪</td><td>KeiName</td><td>AND A.KEI_NAME LIKE '%�n���̋敪%'</td></tr>
	 *	</table><br/>
	 *	<b><span style="color:#882200">���I��������2</span></b><br />
	 *	������searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr style="color:white;font-weight: bold"><td>�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>SystemNo</td><td>AND B.SYSTEM_NO = �V�X�e���ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>ShinsainNo</td><td>AND B.SHINSAIN_NO = '�R�����ԍ�'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ƌ敪�i�R�����ʁj</td><td>JigyoKubun</td><td>AND B.JIGYO_KUBUN = '���Ƌ敪�i�R�����ʁj'</td></tr>
	 *	</table><br/>
	 * <b>2.Page�̕ԋp</b><br/>
	 *	�������ʂ�Page�Ɋi�[��ԋp����B
	 * 
	 * 
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.WarifuriSearchInfo)
	 */
	public Page search(UserInfo userInfo, WarifuriSearchInfo searchInfo)
		throws ApplicationException {

		//�ꗗ�ɕ\���ł���\���f�[�^�̑g�ݍ��킹�X�e�[�^�X�����������쐬
		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
		String[] saishinseiArray = new String[]{
                StatusCode.SAISHINSEI_FLG_DEFAULT,
                StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI
        };//�Đ\���t���O�i�����l�A�Đ\���ς݁j
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);    	 //�u�󗝁v:06
		statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null); 	     //�u�R��������U�菈����v:08
		statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);    		 //�u����U��`�F�b�N�����v:09
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);    				 //�u1���R�����v:10
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);    			 //�u1���R�������v:11
		statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);    			 //�u2���R�������v:12
		
		
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------

		String select = "SELECT"
							+ " B.SYSTEM_NO,"								//�V�X�e���ԍ�
							+ " A.NENDO,"									//�N�x
							+ " A.KAISU,"									//��
							+ " B.JIGYO_NAME,"								//���Ɩ�
							+ " B.UKETUKE_NO,"								//�\���ԍ�
							+ " B.NYURYOKU_JOKYO,"							//���v�������͏� 2006/10/24 add by liucy
							+ " A.KEI_NAME_RYAKU,"							//�n���̋敪�i���́j
							+ " A.JURI_SEIRI_NO,"							//�����ԍ�			2005/10/17�ǉ�
							+ " A.KADAI_NAME_KANJI,"						//�����ۑ薼�i�a���j
							+ " A.NAME_KANJI_SEI,"							//�\���Ҏ����i������-���j
							+ " A.NAME_KANJI_MEI,"							//�\���Ҏ����i������-���j
							+ " A.SHOZOKU_NAME_RYAKU, "						//�����@�֖��i���́j
							+ " A.BUKYOKU_NAME_RYAKU, "						//���ǖ��i���́j
							+ " A.SHOKUSHU_NAME_RYAKU, "					//�E���i���́j
							+ " B.SHINSAIN_NO, "							//�R�����ԍ�
							+ " B.JIGYO_KUBUN, "							//���Ƌ敪�i�R�����ʁj
							+ " B.SHINSAIN_NAME_KANJI_SEI,"					//�R�������i����-���j	
							+ " B.SHINSAIN_NAME_KANJI_MEI,"					//�R�������i����-���j
							+ " B.SHOZOKU_NAME SHINSAIN_SHOZOKU_NAME, "		//�R���������@�֖�
							+ " B.BUKYOKU_NAME SHINSAIN_BUKYOKU_NAME,"		//�R�������ǖ�
							+ " B.SHOKUSHU_NAME SHINSAIN_SHOKUSHU_NAME,"	//�R�����E��
							+ " B.SHINSA_JOKYO,"							//�R�������t���O
							+ " B.RIGAI,"									//���Q�֌W			2005/11/1�ǉ�
							+ " B.DAIRI,"									//�㗝�t���O		2005/11/4�ǉ�
							+ " B.KOSHIN_DATE,"								//����U��X�V��		2005/11/8�ǉ�
							+ " A.JOKYO_ID"									//��ID
	  						+ " FROM "
							+ " (SELECT * FROM SHINSEIDATAKANRI"
							+ " WHERE DEL_FLG=0"
//							+ " AND"
//							+ " (" 
//							+ " (JOKYO_ID = '" +StatusCode.STATUS_GAKUSIN_JYURI + "'"		//�\���󋵂�[06]�ōĐ\���t���O��[0]�܂���[1]
//							+ " AND SAISHINSEI_FLG IN ('" + StatusCode.SAISHINSEI_FLG_DEFAULT 
//							+ " ', '" + StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI + "')" 
//							+ " )"			
//							+ " OR"
//							+ " JOKYO_ID IN('" 	
//							+  StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO 		//�\���󋵂�[08]
//							+ "', '" + StatusCode.STATUS_WARIFURI_CHECK_KANRYO		//�\���󋵂�[09]
//							+ "', '" + StatusCode.STATUS_1st_SHINSATYU				//�\���󋵂�[10]
//							+ "', '" + StatusCode.STATUS_1st_SHINSA_KANRYO 		//�\���󋵂�[11]
//							+ "', '" + StatusCode.STATUS_2nd_SHINSA_KANRYO +  "')"	//�\���󋵂�[12]
//							+ " )"					
							+ " ) A,"
							+ " SHINSAKEKKA B"
	  						+ " WHERE A.SYSTEM_NO = B.SYSTEM_NO "
						//2005/04/28 �ǉ� ��������------------------------------------------
						//��Ղŏ�ID��06(�w�U��)�͕\�����Ȃ��悤�ɏ����̒ǉ�
//                            + " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
//2006/05/09 �ǉ���������               
	  						//���ŏ�ID��06(�w�U��)�͕\�����Ȃ��悤�ɏ����̒ǉ�
//							+ " AND NOT ((A.JIGYO_KUBUN = '6' OR A.JIGYO_KUBUN = '4') AND A.JOKYO_ID = '06')"
                            + " AND NOT ((A.JIGYO_KUBUN IN ("
                            + StringUtil.changeIterator2CSV(JigyoKubunFilter.Convert2ShinseishoJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(),true)
                            + ")) AND A.JOKYO_ID = '06')"
// �c�@�ǉ������܂�  
						//�ǉ� �����܂�-----------------------------------------------------
						;

		//2005.11.16 iso ���������͕ʃ��\�b�h��
//		StringBuffer query = new StringBuffer(select);
//
//		//�\���ԍ�
//		if(searchInfo.getUketukeNo() != null && !searchInfo.getUketukeNo().equals("")){
//			query.append(" AND B.UKETUKE_NO = '" + searchInfo.getUketukeNo() + "'");
//		}
//		//���ƃR�[�h
//		List jigyoCdValueList = searchInfo.getJigyoCdValueList();
//		if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
//			query.append(" AND SUBSTR(B.JIGYO_ID, 3, 5) IN (")
//				 .append(changeIterator2CSV(searchInfo.getJigyoCdValueList().iterator()))
//				 .append(")");
//		}
//		//�S�����Ƌ敪�i�����j
//		if(searchInfo.getTantoJigyoKubun() != null && searchInfo.getTantoJigyoKubun().size() != 0){
//			query.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(changeIterator2CSV(searchInfo.getTantoJigyoKubun().iterator()))
//				 .append(")");
//		}
//		//�N�x
//		if(searchInfo.getNendo() != null && !searchInfo.getNendo().equals("")){
//			query.append(" AND A.NENDO = '" + EscapeUtil.toSqlString(searchInfo.getNendo()) + "'");
//		}
//		//��
//		if(searchInfo.getKaisu() != null && !searchInfo.getKaisu().equals("")){
//			query.append(" AND A.KAISU = '" + EscapeUtil.toSqlString(searchInfo.getKaisu()) + "'");
//		}
//		//�\���Ҏ����i������-���j
//		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	
//			query.append(" AND A.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
//		}
//		//�\���Ҏ����i������-���j
//		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	
//			query.append(" AND A.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
//		}
//		//�\���Ҏ����i�t���K�i-���j
//		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	
//			query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
//		}
//		//�\���Ҏ����i�t���K�i-���j
//		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){
//			query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
//		}
//		//�\���Ҏ����i���[�}��-���j
//		if(searchInfo.getNameRoSei() != null && !searchInfo.getNameRoSei().equals("")){
//			query.append(" AND UPPER(A.NAME_RO_SEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()) + "%'");
//		}
//		//�\���Ҏ����i���[�}��-���j
//		if(searchInfo.getNameRoMei() != null && !searchInfo.getNameRoMei().equals("")){
//			query.append(" AND UPPER(A.NAME_RO_MEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()) + "%'");
//		}
//		//�����@�փR�[�h
//		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){
//			query.append(" AND A.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
//		}
//		//�זڔԍ�
//		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){
//			query.append(" AND A.BUNKASAIMOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'");
//		}
//		//�n���̋敪
//		if(searchInfo.getKeiName() != null && !searchInfo.getKeiName().equals("")){
//			query.append(" AND A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%'");
//		}
//		//�����ԍ�			2005/10/17�@�ǉ�
//		if(searchInfo.getSeiriNo() != null && !searchInfo.getSeiriNo().equals("")){
//			query.append(" AND A.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//
//		//2005/11/8�ǉ�
//		//���Q�֌W
//		if(searchInfo.getRigai() != null && "1".equals(searchInfo.getRigai())){
//			query.append(" AND B.SYSTEM_NO IN (SELECT DISTINCT SYSTEM_NO FROM SHINSAKEKKA");
//			query.append(					" WHERE RIGAI = '1' AND SHINSA_JOKYO = '1')");
//		}
//		
//		//���U��
//		if(searchInfo.getWarifuriFlg() != null && "1".equals(searchInfo.getWarifuriFlg())){
//			query.append(" AND B.SYSTEM_NO IN (SELECT A.SYSTEM_NO");
//			query.append("  FROM (");
//			query.append("    SELECT SYSTEM_NO,");
//			query.append("           SUM(CASE WHEN RIGAI='1' AND SHINSA_JOKYO = '1' THEN 1 ELSE 0 END) RIGAI_CNT,");
//			query.append("           SUM(DECODE(DAIRI,'1',1,0)) DAIRI_CNT");
//			query.append("      FROM SHINSAKEKKA");
//			query.append("     GROUP BY SYSTEM_NO) A");
//			query.append(" WHERE A.RIGAI_CNT > 0");
//			query.append("   AND A.DAIRI_CNT = 0");
//			query.append(")");
//		}
//		//2005/11/8�ǉ�����
//		
//		//�g�ݍ��킹�X�e�[�^�X��
//		if(statusInfo != null && statusInfo.hasQuery()){
//			query.append(" AND")
//					 .append(statusInfo.getQuery());
//		}
		StringBuffer query = new StringBuffer(getQueryString(select, userInfo, searchInfo, statusInfo));
				
		//----��������A�R��������U�茋�ʓo�^��ʕ\���ɕK�v�Ȍ�������
		//�V�X�e���ԍ�
		if(searchInfo.getSystemNo() != null && !searchInfo.getSystemNo().equals("")){
			query.append(" AND B.SYSTEM_NO = " + EscapeUtil.toSqlString(searchInfo.getSystemNo()));
		}
		//�R�����ԍ�
		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){
			query.append(" AND B.SHINSAIN_NO = '" + EscapeUtil.toSqlString(searchInfo.getShinsainNo())+ "'");
		}
		//���Ƌ敪�i�R�����ʁj
		if(searchInfo.getJigyoKubun() != null && !searchInfo.getJigyoKubun().equals("")){
			query.append(" AND B.JIGYO_KUBUN = '" + EscapeUtil.toSqlString(searchInfo.getJigyoKubun())+ "'");
		}

		//----�����܂�
			
		//�\�[�g���i����ID�A�\���ԍ��A�V�X�e���ԍ�(�\�������̃O���[�v���̂���)�A�R�����ԍ��̏����j
		query.append(" ORDER BY B.JIGYO_ID, B.UKETUKE_NO, B.SYSTEM_NO, B.SHINSAIN_NO ");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection,searchInfo, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"����U�茋�ʃf�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}


	/**
	 * ����U�茋�ʏ����폜����.<br />
	 * <br />
	 * <b>1.�R�����ʏ��擾</b><br/>
	 *	�ȉ���SQL�����s���A�擾��������ShinsaKekkaInfo�֊i�[����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	���R�[�h�����݂��Ȃ��ꍇ�͗�O��throw����B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		 A.SYSTEM_NO		--  �V�X�e����t�ԍ�
	 *		,A.UKETUKE_NO		--  �\���ԍ�
	 *		,A.SHINSAIN_NO		--  �R�����ԍ�
	 *		,A.JIGYO_KUBUN		--  ���Ƌ敪
	 *		,A.SEQ_NO			--  �V�[�P���X�ԍ�
	 *		,A.SHINSA_KUBUN		--  �R���敪
	 *		,A.SHINSAIN_NAME_KANJI_SEI	--  �R�������i�����|���j
	 *		,A.SHINSAIN_NAME_KANJI_MEI	--  �R�������i�����|���j
	 *		,A.NAME_KANA_SEI		--  �R�������i�t���K�i�|���j
	 *		,A.NAME_KANA_MEI		--  �R�������i�t���K�i�|���j
	 *		,A.SHOZOKU_NAME		--  �R���������@�֖�
	 *		,A.BUKYOKU_NAME		--  �R�������ǖ�
	 *		,A.SHOKUSHU_NAME		--  �R�����E��
	 *		,A.JIGYO_ID		--  ����ID
	 *		,A.JIGYO_NAME		--  ���Ɩ�
	 *		,A.BUNKASAIMOKU_CD		--  �זڔԍ�
	 *		,A.EDA_NO			--  �}��
	 *		,A.CHECKDIGIT		--  �`�F�b�N�f�W�b�g
	 *		,A.KEKKA_ABC		--  �����]���iABC�j
	 *		,A.KEKKA_TEN		--  �����]���i�_���j
	 *		,A.COMMENT1		--  �R�����g1
	 *		,A.COMMENT2		--  �R�����g2
	 *		,A.COMMENT3		--  �R�����g3
	 *		,A.COMMENT4		--  �R�����g4
	 *		,A.COMMENT5		--  �R�����g5
	 *		,A.COMMENT6		--  �R�����g6
	 *		,A.KENKYUNAIYO		--  �������e
	 *		,A.KENKYUKEIKAKU		--  �����v��
	 *		,A.TEKISETSU_KAIGAI		--  �K�ؐ�-�C�O
	 *		,A.TEKISETSU_KENKYU1		--  �K�ؐ�-�����i1�j
	 *		,A.TEKISETSU		--  �K�ؐ�
	 *		,A.DATO			--  �Ó���
	 *		,A.SHINSEISHA		--  ������\��
	 *		,A.KENKYUBUNTANSHA		--  �������S��
	 *		,A.HITOGENOMU		--  �q�g�Q�m��
	 *		,A.TOKUTEI			--  ������
	 *		,A.HITOES			--  �q�gES�זE
	 *		,A.KUMIKAE			--  ��`�q�g��������
	 *		,A.CHIRYO			--  ��`�q���×Տ�����
	 *		,A.EKIGAKU			--  �u�w����
	 *		,A.COMMENTS		--  �R�����g
	 *		,A.TENPU_PATH		--  �Y�t�t�@�C���i�[�p�X
	 *		,DECODE (
	 *			NVL(A.TENPU_PATH,'null') ,'null','FALSE','TRUE'
	 *	 	) TENPU_FLG		--  �Y�t�t�@�C���i�[�t���O
	 *					--  �Y�t�t�@�C���i�[�p�X��NULL:FALSE
	 *					--	NULL����Ȃ�:TRUE
	 *		,A.SHINSA_JOKYO		--  �R����
	 *		,A.BIKO			--  ���l
	 *	FROM
	 *		SHINSAKEKKA A
	 *	WHERE
	 *		SYSTEM_NO = '�V�X�e����t�ԍ�'
	 *		AND SHINSAIN_NO = '�R�����ԍ�'
	 *		AND JIGYO_KUBUN = '���Ƌ敪'
	 *	FOR UPDATE
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>��������shinsaKekkaPk�̕ϐ��isystemNo�j�̒l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>��������shinsaKekkaPk�̕ϐ��ishinsainNo�j�̒l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>��������shinsaKekkaPk�̕ϐ��ijigyoKubun�j�̒l</td></tr>
	 *	</table><br/>
	 * <br />
	 * <b>2.�R�����ʏ��폜</b><br/>
	 *	�ȉ���SQL�����s���A�R�����ʏ��𕨗��폜����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	���R�[�h�����݂��Ȃ��ꍇ�͗�O��throw����B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		SHINSAKEKKA
	 *	WHERE
	 *		SYSTEM_NO = '�V�X�e����t�ԍ�'
	 *		AND SHINSAIN_NO = '�R�����ԍ�'
	 *		AND JIGYO_KUBUN = '���Ƌ敪'
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>��������shinsaKekkaPk�̕ϐ��isystemNo�j�̒l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>��������shinsaKekkaPk�̕ϐ��ishinsainNo�j�̒l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>��������shinsaKekkaPk�̕ϐ��ijigyoKubun�j�̒l</td></tr>
	 *	</table><br/>
	 * <br />
	 * <b>3.�R�����ʂ�������Ԃɖ߂�</b><br/>
	 *	�ȉ���SQL�����s���A�R�����ʏ���INSERT����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	���R�[�h�����݂��Ȃ��ꍇ�͗�O��throw����B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO 
	 *		SHINSAKEKKA(
	 *			SYSTEM_NO			--  �V�X�e����t�ԍ�
	 *			,UKETUKE_NO		--  �\���ԍ�
	 *			,SHINSAIN_NO		--  �R�����ԍ�
	 *			,JIGYO_KUBUN		--  ���Ƌ敪
	 *			,SEQ_NO			--  �V�[�P���X�ԍ�
	 *			,SHINSA_KUBUN		--  �R���敪
	 *			,SHINSAIN_NAME_KANJI_SEI	--  �R�������i�����|���j
	 *			,SHINSAIN_NAME_KANJI_MEI	--  �R�������i�����|���j
	 *			,NAME_KANA_SEI		--  �R�������i�t���K�i�|���j
	 *			,NAME_KANA_MEI		--  �R�������i�t���K�i�|���j
	 *			,SHOZOKU_NAME		--  �R���������@�֖�
	 *			,BUKYOKU_NAME		--  �R�������ǖ�
	 *			,SHOKUSHU_NAME		--  �R�����E��
	 *			,JIGYO_ID			--  ����ID
	 *			,JIGYO_NAME		--  ���Ɩ�
	 *			,BUNKASAIMOKU_CD		--  ���ȍזڃR�[�h
	 *			,EDA_NO			--  �}��
	 *			,CHECKDIGIT		--  �`�F�b�N�f�W�b�g
	 *			,KEKKA_ABC			--  �����]���iABC�j
	 *			,KEKKA_TEN			--  �����]���i�_���j
	 *			,COMMENT1			--  �R�����g1
	 *			,COMMENT2			--  �R�����g2
	 *			,COMMENT3			--  �R�����g3
	 *			,COMMENT4			--  �R�����g4
	 *			,COMMENT5			--  �R�����g5
	 *			,COMMENT6			--  �R�����g6
	 *			,KENKYUNAIYO		--  �������e
	 *			,KENKYUKEIKAKU		--  �����v��
	 *			,TEKISETSU_KAIGAI		--  �K�ؐ�-�C�O
	 *			,TEKISETSU_KENKYU1		--  �K�ؐ�-�����i1�j
	 *			,TEKISETSU			--  �K�ؐ�
	 *			,DATO			--  �Ó���
	 *			,SHINSEISHA		--  ������\��
	 *			,KENKYUBUNTANSHA		--  �������S��
	 *			,HITOGENOMU		--  �q�g�Q�m��
	 *			,TOKUTEI			--  ������
	 *			,HITOES			--  �q�gES�זE
	 *			,KUMIKAE			--  ��`�q�g��������
	 *			,CHIRYO			--  ��`�q���×Տ�����
	 *			,EKIGAKU			--  �u�w����
	 *			,COMMENTS			--  �R�����g
	 *			,TENPU_PATH		--  �Y�t�t�@�C���i�[�p�X
	 *			,SHINSA_JOKYO		--  �R����
	 *			,BIKO			--  ���l
	 *		)
	 *	VALUES
	 *		(?,?,?,?,?,?,?,?,?,?,?,?,?,?,
	 *		 ?,?,?,?,?,?,?,?,?,?,?,?,?,?,
	 *		 ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>1�Ŏ擾�����V�X�e����t�ԍ��𗘗p</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>1�Ŏ擾�����\���ԍ��𗘗p</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>@00000+1�Ŏ擾�����V�[�P���X�ԍ��𗘗p</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>1�Ŏ擾�������Ƌ敪�𗘗p</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�[�P���X�ԍ�</td><td>1�Ŏ擾�����V�[�P���X�ԍ��𗘗p</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R���敪</td><td>1�Ŏ擾�����R���敪�𗘗p</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�����|���j</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�����|���j</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R���������@�֖�</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������ǖ�</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����E��</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����ID</td><td>1�Ŏ擾��������ID�𗘗p</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ɩ�</td><td>1�Ŏ擾�������Ɩ��𗘗p</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ȍזڃR�[�h</td><td>1�Ŏ擾�������ȍזڃR�[�h�𗘗p</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}��</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�`�F�b�N�f�W�b�g</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����]���iABC�j</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����]���i�_���j</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g1</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g2</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g3</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g4</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g5</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g6</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�������e</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����v��</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-�C�O</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-�����i1�j</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�K�ؐ�</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�Ó���</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������\��</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�������S��</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�q�g�Q�m��</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�q�gES�זE</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��`�q�g��������</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��`�q���×Տ�����</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�u�w����</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����g</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�Y�t�t�@�C���i�[�p�X</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R����</td><td>0(������)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���l</td><td>null</td></tr>
	 *	</table><br/>
	 * 
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsaKekkaPk)
	 */
	public void delete(UserInfo userInfo, ShinsaKekkaPk shinsaKekkaPk) throws ApplicationException {
		
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
				
			ShinsaKekkaInfoDao shinsaKekkaDao = new ShinsaKekkaInfoDao(userInfo);
			//---------------------------------------
			//�R�����ʏ��̎擾
			//---------------------------------------
			ShinsaKekkaInfo updateInfo = null;
			try {
				updateInfo = shinsaKekkaDao.selectShinsaKekkaInfoForLock(connection, shinsaKekkaPk);		
			} catch (NoDataFoundException e) {
				throw e;
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			}
			
			//---------------------------------------
			//�R�����ʃe�[�u���̍폜
			//---------------------------------------
			try {
				//DB�X�V
				shinsaKekkaDao.deleteShinsaKekkaInfo(connection, shinsaKekkaPk);
			} catch (NoDataFoundException e) {
				throw e;
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			}
			
			//2004/12/3update
			//�폜�̏ꍇ�́A�o�^���ꂽ�R�����ʂ�������Ԃɖ߂��悤�ɕύX�B
			//---------------------------------------
			//�R�����ʃe�[�u���̓o�^
			//---------------------------------------
			//�V�K�ɐR�����ʏ����쐬
			ShinsaKekkaInfo newShinsaKekkaInfo = new 	ShinsaKekkaInfo();
			newShinsaKekkaInfo.setSystemNo(updateInfo.getSystemNo());				//�V�X�e����t�ԍ�
			newShinsaKekkaInfo.setUketukeNo(updateInfo.getUketukeNo());				//�\���ԍ�
			if(Integer.parseInt(updateInfo.getSeqNo()) >= 10){
				newShinsaKekkaInfo.setShinsainNo("@0000" + updateInfo.getSeqNo());	//�R�����ԍ�(7��)
			}else{
				newShinsaKekkaInfo.setShinsainNo("@00000" + updateInfo.getSeqNo());	//�R�����ԍ�(7��)
			}
			newShinsaKekkaInfo.setJigyoKubun(updateInfo.getJigyoKubun());			//���Ƌ敪
			newShinsaKekkaInfo.setSeqNo(updateInfo.getSeqNo());						//�V�[�P���X�ԍ�
			newShinsaKekkaInfo.setShinsaKubun(updateInfo.getShinsaKubun());			//�R���敪
			newShinsaKekkaInfo.setJigyoId(updateInfo.getJigyoId());					//����ID
			newShinsaKekkaInfo.setJigyoName(updateInfo.getJigyoName());				//���Ɩ�
			newShinsaKekkaInfo.setBunkaSaimokuCd(updateInfo.getBunkaSaimokuCd());	//���ȍזڃR�[�h									
			newShinsaKekkaInfo.setShinsaJokyo("0");									//�R���󋵁i�������j											
						
			//---------------------------------------
			//�R�����ʃe�[�u���̓o�^
			//---------------------------------------
//			if(updateInfo.getSeqNo() != null){
//				updateInfo.setShinsainNo("@0000" + updateInfo.getSeqNo());		//�R�����ԍ�
//			}else{
//				throw new ApplicationException(
//					"�R�����ʏ��̃V�[�P���X�ԍ����s���ł��B"
//					+ "�R�����ԍ�'" + updateInfo.getShinsainNo() +"','"
//					+ "�V�[�P���X�ԍ�'" + updateInfo.getSeqNo() + "'" ,
//					new ErrorInfo("errors.4003"));				
//			}
//			updateInfo.setShinsainNameKanjiSei(null);		//�R�������i����-���j
//			updateInfo.setShinsainNameKanjiMei(null);		//�R�������i����-���j
//			updateInfo.setNameKanaSei(null);				//�R�������i�t���K�i-���j
//			updateInfo.setNameKanaMei(null);				//�R�������i�t���K�i-���j
//			updateInfo.setShozokuName(null);				//�R���������@�֖�
//			updateInfo.setBukyokuName(null);				//�R�������ǖ�
//			updateInfo.setShokushuName(null);				//�R�����E��	

			try {
				//DB�X�V
				shinsaKekkaDao.insertShinsaKekkaInfo(connection, newShinsaKekkaInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			}
			
			//2005.01.24
			//�\���f�[�^�e�[�u���̕]�����Ƀ\�[�g���ꂽ1���R�����ʏ����X�V����B
			//�����ӏ��Ŏg�p����̂ŕʃ��\�b�h�ɂ��ׂ��H
			//�\���f�[�^�Ǘ�DAO
			ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);

			//�r������̂��ߊ����f�[�^���擾����
			ShinseiDataPk shinseiDataPk = new ShinseiDataPk(shinsaKekkaPk.getSystemNo());
			ShinseiDataInfo existInfo = null;
			try{
				existInfo = shinseiDataInfoDao.selectShinseiDataInfoForLock(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�\�����Ǘ��f�[�^�r���擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
			//---�\���f�[�^�폜�t���O�`�F�b�N---
			String delFlag = existInfo.getDelFlg(); 
			if(FLAG_APPLICATION_DELETE.equals(delFlag)) {
				throw new ApplicationException(
					"���Y�\���f�[�^�͍폜����Ă��܂��BSystemNo=" + shinsaKekkaPk.getSystemNo(),
					new ErrorInfo("errors.9001"));
			}
			//---�\���f�[�^�X�e�[�^�X�`�F�b�N---
			String jyokyoId = existInfo.getJokyoId();
			//---�R��������U�菈����A����U��`�F�b�N�����A1���R�����A1���R�������ȊO�̏ꍇ�̓G���[
			if( !(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_WARIFURI_CHECK_KANRYO.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_1st_SHINSATYU.equals(jyokyoId)) &&
				 !(StatusCode.STATUS_1st_SHINSA_KANRYO.equals(jyokyoId)) )
			{
				throw new ApplicationException(
					"���Y�\���f�[�^�͂P���R���o�^�\�ȃX�e�[�^�X�ł͂���܂���BSystemNo="
					+ shinsaKekkaPk.getSystemNo(),
					new ErrorInfo("errors.9012"));
			}

			//---�R�����ʃ��R�[�h�擾�i����ABC�̏����j---
			ShinsaKekkaInfo[] shinsaKekkaInfoArray = null;						
			try{
				shinsaKekkaInfoArray = shinsaKekkaDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}

			//---�����]���}�X�^���瑍���]�����̈ꗗ���擾����---					
			HashMap sogoHyokaMap = new HashMap();
			List hyokaList = MasterSogoHyokaInfoDao.selectSogoHyokaInfoList(connection);	
			Iterator iterator = hyokaList.iterator();
			while(iterator.hasNext()){
				Map map = (Map)iterator.next();
				String sogoHyoka = (String) map.get("SOGO_HYOKA");											//�����]��
				String jigyoKubun = new Integer(((Number) map.get("JIGYO_KUBUN")).intValue()).toString();	//���Ƌ敪
				String tensu =  new Integer(((Number) map.get("TENSU")).intValue()).toString();			//�_��
				sogoHyokaMap.put(jigyoKubun + sogoHyoka, tensu);											//�L�[�F���Ƌ敪+�����]���A�l�F�_�� 
			}

			//---DB�X�V---
			try {
				String kekkaAbc = "";
				int intKekkaTen = 0;
				String kekkaTenSorted = "";
				boolean kekkaTenFlag = false;
				for(int i=0; i<shinsaKekkaInfoArray.length; i++){
					try{
						//�����]���iABC�j�Ƒ����]���i�_���j�͍��݂��Ȃ�
						if(shinsaKekkaInfoArray[i].getKekkaAbc() != null){
							kekkaAbc = kekkaAbc + shinsaKekkaInfoArray[i].getKekkaAbc();
							String tensu = (String) sogoHyokaMap.get(shinsaKekkaInfoArray[i].getJigyoKubun()
														+ shinsaKekkaInfoArray[i].getKekkaAbc());
							if(tensu == null){
								throw new ApplicationException(
									"�����]���}�X�^�Ɉ�v����f�[�^�����݂��܂���B�����L�[�F�����]��'"
									+ shinsaKekkaInfoArray[i].getKekkaAbc() 
									+ "',���Ƌ敪�F'" +  shinsaKekkaInfoArray[i].getJigyoKubun() + "'",
									new ErrorInfo("errors.4002"));	
							}
							intKekkaTen = intKekkaTen
												+ Integer.parseInt((String) sogoHyokaMap.get(
																shinsaKekkaInfoArray[i].getJigyoKubun()
																	 + shinsaKekkaInfoArray[i].getKekkaAbc()));
							kekkaTenFlag = true;	//1�ł��_�����ݒ肳��Ă����ꍇ[true]						
						}else if(shinsaKekkaInfoArray[i].getKekkaTen() != null){
								//�P���R������(�_��)
								intKekkaTen = intKekkaTen
													+ Integer.parseInt((String) sogoHyokaMap.get(
																	shinsaKekkaInfoArray[i].getJigyoKubun()
																		 + shinsaKekkaInfoArray[i].getKekkaTen()));
								
								//�P���R������(�_����)
								if(kekkaTenSorted.equals("")){
									kekkaTenSorted = kekkaTenSorted + shinsaKekkaInfoArray[i].getKekkaTen();
								}else{
									kekkaTenSorted = kekkaTenSorted + " " + shinsaKekkaInfoArray[i].getKekkaTen();									
								}
								
							kekkaTenFlag = true;	//1�ł��_�����ݒ肳��Ă����ꍇ[true]
						}
					}catch(NumberFormatException e){
						//���l�Ƃ��ĔF���ł��Ȃ��ꍇ�͏������΂�
					}
				}
				
				//���l�Ƃ��ĔF���ł���_�����P�ł��Z�b�g����Ă����ꍇ�͓o�^����
				String kekkaTen = null;
				if(kekkaTenFlag){
					kekkaTen = new Integer(intKekkaTen).toString();
				}
				
				//�X�V�f�[�^���Z�b�g����
				existInfo.setKekka1Abc(kekkaAbc);								//�P���R������(ABC)
				existInfo.setKekka1Ten(kekkaTen);								//�P���R������(�_��)
				existInfo.setKekka1TenSorted(kekkaTenSorted);					//�P���R������(�_����)				
				existInfo.setJokyoId(StatusCode.STATUS_1st_SHINSA_KANRYO);	//�\����
				shinseiDataInfoDao.updateShinseiDataInfo(connection, existInfo, true);
				
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�\�����X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} catch (ApplicationException e) {
				throw new ApplicationException(
					"�\�����X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}

			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;

		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}	
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.WarifuriInfo)
	 */
//	public WarifuriInfo validate(UserInfo userInfo, WarifuriInfo info, String mode)
//		throws ApplicationException, ValidationException {
//
//			Connection connection = null;	
//			try {
//				connection = DatabaseUtil.getConnection();
//				//�G���[���ێ��p���X�g
//				List errors = new ArrayList();
//
//				//2�d�o�^�`�F�b�N
//				//�����\���ɓ����R�������A����U���Ă��Ȃ����ǂ������m�F
//				ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);
//				ShinsaKekkaPk shinsaKekkaPk = new ShinsaKekkaPk();
//				shinsaKekkaPk.setSystemNo(info.getSystemNo());
//				shinsaKekkaPk.setShinsainNo(info.getShinsainNo());
//				shinsaKekkaPk.setJigyoKubun(info.getJigyoKubun());
//				try {
//					dao.selectShinsaKekkaInfo(connection, shinsaKekkaPk);
//					//NG
//					String[] error = {"�R����"};
//					throw new ApplicationException("���łɓ���̐R�������o�^����Ă��܂��B", 	new ErrorInfo("errors.4007", error));			
//				} catch (NoDataFoundException e) {
//					//OK
//				}
//
//				//-----���̓G���[���������ꍇ�͗�O���Ȃ���-----
//				if (!errors.isEmpty()) {
//					throw new ValidationException(
//						"�R��������U��f�[�^�`�F�b�N���ɃG���[��������܂����B",
//						errors);
//				}
//				return info;
//			} catch (DataAccessException e) {
//				throw new ApplicationException(
//					"�R�����Ǘ��f�[�^�`�F�b�N����DB�G���[���������܂����B",
//					new ErrorInfo("errors.4005"),
//					e);
//			} finally {
//				DatabaseUtil.closeConnection(connection);
//			}
//
//	}

	/**
	 * �˗����쐬.<br />
	 * <br />
	 * �˗����̍쐬���s���A�쐬����CSV�t�@�C����FileResource��ԋp����B<br />
	 * <b>1.�R�����擾</b><br/>
	 *	�ȉ���SQL�����s���A�R�����̏����擾����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	�������ʂ͊e���R�[�h�̏���List�Ɋi�[���A�X�ɑS�̂��Ǘ�����List�֊i�[����B
	 *	�������A�擾����R�����̏��́A�R�����U���ꂽ�҂����Ƃ���B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT DISTINCT
	 *		B.SHINSAIN_NO	"�R�����ԍ�",
	 *		C.NAME_KANJI_SEI	"�R�������i����-���j",
	 *		C.NAME_KANJI_MEI	"�R�������i����-���j",
	 *		C.SHOZOKU_NAME	"�R���������@�֖�",
	 *		C.BUKYOKU_NAME	"�R�������ǖ�",
	 *		C.SHOKUSHU_NAME	"�R�����E��",
	 *		C.SOFU_ZIP	"���t��i�X�֔ԍ��j",
	 *		C.SOFU_ZIPADDRESS	"���t��i�Z���j",
	 *		C.SOFU_ZIPEMAIL	"���t��iEmail�j",
	 *		C.SHOZOKU_TEL	"�d�b�ԍ�",
	 *		C.SHOZOKU_FAX	"FAX�ԍ�",
	 *		C.BIKO	"���l",
	 *		D.SHINSAIN_ID	"�R����ID",
	 *		D.PASSWORD	"�p�X���[�h",
	 *		TO_CHAR(D.YUKO_DATE, 'YYYY/MM/DD')	"�L������"
	 *	FROM 
	 *		SHINSEIDATAKANRI A,
	 *		SHINSAKEKKA B,
	 *		MASTER_SHINSAIN C,
	 *		SHINSAININFO D
	 *	WHERE
	 *		A.SYSTEM_NO = B.SYSTEM_NO		--�@�V�X�e���ԍ�
	 *		AND B.SHINSAIN_NO = C.SHINSAIN_NO	--�@�R�����ԍ�
	 *		AND B.SHINSAIN_NO = SUBSTR(D.SHINSAIN_ID,4,7)
	 *						--�@�R�����ԍ�(7��)
	 *		AND A.DEL_FLG = 0
	 *	
	 *					�|�|���I���������|�|
	 *	
	 *		AND (		--�@�X�e�[�^�X��
	 *			(A.JOKYO_ID = '06' AND A.SAISHINSEI_FLG IN ('0','2'))
	 *				--�@��,�Đ\���t���O�i�����l�A�Đ\���ς݁j
	 *			OR (A.JOKYO_ID = '08')	--�@�R��������U�菈����
	 *			OR (A.JOKYO_ID = '09')	--�@����U��`�F�b�N����
	 *			OR (A.JOKYO_ID = '10')	--�@1���R����
	 *			OR (A.JOKYO_ID = '11')	--�@1���R������
	 *			OR (A.JOKYO_ID = '12')	--�@2���R������
	 *		)
	 *	
	 *	ORDER BY B.SHINSAIN_NO
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<b><span style="color:#002288">���I��������</span></b><br />
	 *	������searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr style="color:white;font-weight: bold"><td>�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>UketukeNo</td><td>AND B.UKETUKE_NO = '�\���ԍ�'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>jigyoCdValueList</td><td>AND SUBSTR(B.JIGYO_ID, 3, 5) IN ('���ƃR�[�h1','���ƃR�[�h2'�c)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�S�����Ƌ敪�i�����j</td><td>TantoJigyoKubun</td><td>AND B.JIGYO_KUBUN IN ('�S�����Ƌ敪1','�S�����Ƌ敪2'�c)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�N�x</td><td>Nendo</td><td>AND A.NENDO = '�N�x'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��</td><td>Kaisu</td><td>AND A.KAISU = '��'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i������-���j</td><td>NameKanjiSei</td><td>AND A.NAME_KANJI_SEI LIKE '%�\���Ҏ����i������-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i������-���j</td><td>NameKanjiMei</td><td>AND A.NAME_KANJI_MEI LIKE '%�\���Ҏ����i������-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i�t���K�i-���j</td><td>NameKanaSei</td><td>AND A.NAME_KANA_SEI LIKE '%�\���Ҏ����i�t���K�i-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i�t���K�i-���j</td><td>NameKanaMei</td><td>AND A.NAME_KANA_MEI LIKE '%�\���Ҏ����i�t���K�i-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i���[�}��-���j</td><td>NameRoSei</td><td>AND UPPER(A.NAME_RO_SEI) LIKE '%�\���Ҏ����i���[�}��-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i���[�}��-���j</td><td>NameRoMei</td><td>AND UPPER(A.NAME_RO_MEI) LIKE '%�\���Ҏ����i���[�}��-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>ShozokuCd</td><td>AND A.SHOZOKU_CD = '�����@�փR�[�h'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�זڔԍ�</td><td>BunkaSaimokuCd</td><td>AND A.BUNKASAIMOKU_CD = '�זڔԍ�'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�n���̋敪</td><td>KeiName</td><td>AND A.KEI_NAME LIKE '%�n���̋敪%'</td></tr>
	 *	</table>
	 *	<br/><br/>
	 * <b>2.�V�X�e����t�ԍ��擾</b><br/>
	 *	�ȉ���SQL�����s���A�R���������U���Ă���\���f�[�^�̃V�X�e���ԍ����擾����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *	
	 *	SELECT DISTINCT 
	 *		B.SYSTEM_NO			--�@�V�X�e����t�ԍ�
	 *	FROM 
	 *		SHINSEIDATAKANRI A,
	 *		SHINSAKEKKA B
	 *	WHERE
	 *		A.SYSTEM_NO = B.SYSTEM_NO
	 *		AND A.DEL_FLG = 0
	 *		AND B.SHINSAIN_NAME_KANJI_SEI IS NOT NULL
	 *	
	 *			�|�|���I���������|�|
	 *	
	 *		AND (			--�@�X�e�[�^�X��
	 *			(A.JOKYO_ID = '06' AND A.SAISHINSEI_FLG IN ('0','2'))
	 *				--�@��,�Đ\���t���O�i�����l�A�Đ\���ς݁j
	 *			OR (A.JOKYO_ID = '08')	--�@�R��������U�菈����
	 *			OR (A.JOKYO_ID = '09')	--�@����U��`�F�b�N����
	 *			OR (A.JOKYO_ID = '10')	--�@1���R����
	 *			OR (A.JOKYO_ID = '11')	--�@1���R������
	 *			OR (A.JOKYO_ID = '12')	--�@2���R������
	 *		)
	 *	
	 *	ORDER BY B.SHINSAIN_NO
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<b><span style="color:#002288">���I��������</span></b><br />
	 *	������searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr style="color:white;font-weight: bold"><td>�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>UketukeNo</td><td>AND B.UKETUKE_NO = '�\���ԍ�'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>jigyoCdValueList</td><td>AND SUBSTR(B.JIGYO_ID, 3, 5) IN ('���ƃR�[�h1','���ƃR�[�h2'�c)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�S�����Ƌ敪�i�����j</td><td>TantoJigyoKubun</td><td>AND B.JIGYO_KUBUN IN ('�S�����Ƌ敪1','�S�����Ƌ敪2'�c)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�N�x</td><td>Nendo</td><td>AND A.NENDO = '�N�x'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��</td><td>Kaisu</td><td>AND A.KAISU = '��'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i������-���j</td><td>NameKanjiSei</td><td>AND A.NAME_KANJI_SEI LIKE '%�\���Ҏ����i������-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i������-���j</td><td>NameKanjiMei</td><td>AND A.NAME_KANJI_MEI LIKE '%�\���Ҏ����i������-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i�t���K�i-���j</td><td>NameKanaSei</td><td>AND A.NAME_KANA_SEI LIKE '%�\���Ҏ����i�t���K�i-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i�t���K�i-���j</td><td>NameKanaMei</td><td>AND A.NAME_KANA_MEI LIKE '%�\���Ҏ����i�t���K�i-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i���[�}��-���j</td><td>NameRoSei</td><td>AND UPPER(A.NAME_RO_SEI) LIKE '%�\���Ҏ����i���[�}��-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i���[�}��-���j</td><td>NameRoMei</td><td>AND UPPER(A.NAME_RO_MEI) LIKE '%�\���Ҏ����i���[�}��-���j%'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>ShozokuCd</td><td>AND A.SHOZOKU_CD = '�����@�փR�[�h'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�זڔԍ�</td><td>BunkaSaimokuCd</td><td>AND A.BUNKASAIMOKU_CD = '�זڔԍ�'</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�n���̋敪</td><td>KeiName</td><td>AND A.KEI_NAME LIKE '%�n���̋敪%'</td></tr>
	 *	</table>
	 *	<br/><br />
	 * <b>3.CSV�o��</b><br/>
	 *	�������ʂ�CSV�֓f���o���B<br />
	 *	�o�͂����CSV�ɂ́A�w�b�_������B�������A�w�b�_�́AResultSetMetaData����擾����B��SQL�̗񖼂�Alias���w�b�_�ƂȂ�B<br/>
	 *	CSV�t�@�C���̃p�X�t���t�@�C�����͈ȉ��̒ʂ�B<br/>
	 *	&nbsp;&nbsp;IRAI_WORK_FOLDERyyyyMMddHHmmss/SHINSAIRAI.csv<br/>
	 *	&nbsp;&nbsp;&nbsp;&nbsp;��IRAI_WORK_FOLDER��ApplicationSettings.properties�ɂĒ�`�����B<br/>
	 *	<br/><br />
	 * <b>4.�˗����t�@�C���̃R�s�[</b><br/>
	 *	�˗����t�@�C�����R�s�[����B<br />
	 *	�R�s�[���ƁA�R�s�[��͈ȉ��̒ʂ�
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr><td style="color:white;font-weight: bold" rowspan="2">�R�s�[���t�@�C��</td><td bgcolor="#FFFFFF"><b>IRAI_FORMAT_PATH</b>/<b>IRAI_FORMAT_FILE_NAME</b></td></tr>
	 *		<tr><td bgcolor="#FFFFFF"><b>IRAI_FORMAT_PATH</b>/$</td></tr>
	 *		<tr><td style="color:white;font-weight: bold">�R�s�[��</td><td bgcolor="#FFFFFF"><b>IRAI_WORK_FOLDER</b>yyyyMMddHHmmss/</td></tr>
	 *	</table>
	 *	<br/><br />
	 * <b>5.�˗������k</b><br/>
	 *	�˗����t�@�C���̈��k���s���B<br/>
	 *	���k��Lha32�ɂ���čs���A�ʏ�̈��k�`���̃t�@�C�����o�͌�A���ȉ𓀌`���̃t�@�C�����o�͂���B<br/>
	 *	���s�R�}���h�͈ȉ��̒ʂ�B<br/>
	 *	&nbsp;&nbsp;&nbsp;Lha32 u -a1 -n1 -o2 -jyo1 "IRAI_WORK_FOLDERyyyyMMddHHmmss/SHINSAIRAI_yyyyMMdd.lzh" "" "IRAI_WORK_FOLDERyyyyMMddHHmmss/*"<br/>
	 *	&nbsp;&nbsp;&nbsp;Lha32 s -gw2 -n1 -jyo1 "IRAI_WORK_FOLDERyyyyMMddHHmmss/SHINSAIRAI_yyyyMMdd" "IRAI_WORK_FOLDERyyyyMMddHHmmss/"<br/>
	 *	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��SHINSAIRAI_yyyyMMdd.exe�Ƃ����t�@�C�����쐬�����<br/>
	 *	<br/><br />
	 * <b>6.�X�e�[�^�X�X�V����</b><br/>
	 *	2.�Ŏ擾�����V�X�e����t�ԍ��ɑΉ����郌�R�[�h�̃X�e�[�^�X���X�V����B<br/>
	 *	�X�V�̍ۂɂ́A�ȉ���SQL�����s����B<br/>
	 * 	<br/>
	 * 	�@�r������
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *	
	 *	SELECT
	 *		*
	 *	FROM
	 *		SHINSEIDATAKANRI
	 *	WHERE
	 *		SYSTEM_NO IN (�V�X�e����t�ԍ�1,�V�X�e����t�ԍ�2�c)
	 *	FOR UPDATE
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 * 	�A�X�V����
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *	
	 *	UPDATE SHINSEIDATAKANRI
	 *	SET
	 *		JOKYO_ID = '10'			--  1���R����
	 *	WHERE
	 *		SYSTEM_NO IN (�V�X�e����t�ԍ�1,�V�X�e����t�ԍ�2�c)
	 *		AND	JOKYO_ID = '06' 		--  �w�U��
	 *	</pre>
	 *	</td></tr>
	 *	</table><br>
	 *	<br/><br />
	 * <b>7.FileResource�ԋp</b><br/>
	 * 	5.�ō쐬�������k�t�@�C����Ǎ��݁AFileResource�N���X�ɃZ�b�g���ĕԋp����B
	 * 
	 * @see jp.go.jsps.kaken.model.IShinsainWarifuri#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.WarifuriSearchInfo)
	 */
	public FileResource createIraisho(UserInfo userInfo, WarifuriSearchInfo searchInfo)
		throws ApplicationException {

		//�ꗗ�ɕ\���ł���\���f�[�^�̑g�ݍ��킹�X�e�[�^�X�����������쐬
		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
		String[] saishinseiArray = new String[]{
                StatusCode.SAISHINSEI_FLG_DEFAULT,
                StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI
        };     //�Đ\���t���O�i�����l�A�Đ\���ς݁j
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);     //�u�󗝁v:06
		statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);    //�u�R��������U�菈����v:08
		statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);        //�u����U��`�F�b�N�����v:09
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);                //�u1���R�����v:10
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);            //�u1���R�������v:11
		statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);            //�u2���R�������v:12
			
		//-----------------------
		// �����������CSV�f�[�^�擾SQL���̍쐬
		//-----------------------
		//2004/12/9update �R�������A�����@�֖��A���ǖ��A�E����R�����ʃe�[�u�����R�����}�X�^����擾����悤�ɕύX�B
//		String select = "SELECT DISTINCT"
//							  + " B.SHINSAIN_NO \"�R�����ԍ�\","							//�R�����ԍ�
//							  + " C.NAME_KANJI_SEI \"�R�������i����-���j\","					//�R�������i����-���j
//							  + " C.NAME_KANJI_MEI \"�R�������i����-���j\","					//�R�������i����-���j
//							  + " C.SHOZOKU_NAME \"�R���������@�֖�\","						//�R���������@�֖�
//							  + " C.BUKYOKU_NAME \"�R�������ǖ�\","							//�R�������ǖ�
//							  + " C.SHOKUSHU_NAME \"�R�����E��\","							//�R�����E��
//							  + " C.SOFU_ZIP \"���t��i�X�֔ԍ��j\","						//���t��i�X�֔ԍ��j
//							  + " C.SOFU_ZIPADDRESS \"���t��i�Z���j\","						//���t��i�Z���j
//							  + " C.SOFU_ZIPEMAIL \"���t��iEmail�j\","						//���t��iEmail�j
//							  + " C.SHOZOKU_TEL \"�d�b�ԍ�\","								//�d�b�ԍ�
//							  + " C.SHOZOKU_FAX \"FAX�ԍ�\","								//FAX�ԍ�
//							  + " C.BIKO \"���l\","											//���l
//							  + " D.SHINSAIN_ID \"�R����ID\","								//�R����ID
//							  + " D.PASSWORD \"�p�X���[�h\","								//�p�X���[�h
//							  + " TO_CHAR(D.YUKO_DATE, 'YYYY/MM/DD') \"�L������\""			//�L������
//							  + " FROM SHINSEIDATAKANRI A,"
//							  + " SHINSAKEKKA B,"
//							  + " MASTER_SHINSAIN C,"
//							  + " SHINSAININFO D "
// 							  + " WHERE"
//							  + " A.SYSTEM_NO = B.SYSTEM_NO"							//�V�X�e���ԍ�
//							  + " AND B.SHINSAIN_NO = C.SHINSAIN_NO"					//�R�����ԍ�
//							  + " AND B.SHINSAIN_NO = SUBSTR(D.SHINSAIN_ID,4,7)"		//�R�����ԍ�(7��)
//							  +	" AND A.DEL_FLG = 0"
//							//2005/04/28 �ǉ� ��������------------------------------------------
//							//��Ղŏ�ID��06(�w�U��)��CSV�ɏo�͂��Ȃ��悤�ɏ����̒ǉ�
//							  + " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
//							  + " AND A.JIGYO_KUBUN = C.JIGYO_KUBUN"					//���Ƌ敪
//							//�ǉ� �����܂�-----------------------------------------------------
//							;
		//2005/10/27 ���Ƃ��Ƃ̌�����\������悤�ɕύX		
		
		//2005.01.06 iso �����ԍ��Ή�
		//�P���ɐ����ԍ���ǉ�����Ɛ����ԍ��̈قȂ�\���������Ƃ��ƂɃO���[�v������Ȃ��̂ő啝�ύX
//		String select = "SELECT"
//		              + " B.SHINSAIN_NO SHINSAIN_NO, "										//�R�����ԍ�
//		              + " C.NAME_KANJI_SEI NAME_KANJI_SEI,"									//�R�������i����-���j
//		              + " C.NAME_KANJI_MEI NAME_KANJI_MEI,"									//�R�������i����-���j
//		              + " C.SHOZOKU_NAME SHOZOKU_NAME,"										//�R���������@�֖�
//		              + " C.BUKYOKU_NAME BUKYOKU_NAME,"										//�R�������ǖ�
//		              + " C.SHOKUSHU_NAME SHOKUSHU_NAME,"									//�R�����E��
//		              + " C.SOFU_ZIP SOFU_ZIP,"												//���t��i�X�֔ԍ��j
//		              + " C.SOFU_ZIPADDRESS SOFU_ZIPADDRESS,"								//���t��i�Z���j
//		              + " C.SOFU_ZIPEMAIL SOFU_ZIPEMAIL,"									//���t��iEmail�j
//					  + " C.SHOZOKU_TEL SHOZOKU_TEL,"										//�d�b�ԍ�
//					  + " C.SHOZOKU_FAX SHOZOKU_FAX,"										//FAX�ԍ�
//		              + " C.BIKO BIKO,"														//���l
//		              + " D.SHINSAIN_ID SHINSAIN_ID,"										//�R����ID
//		              + " D.PASSWORD PASSWORD,"												//�p�X���[�h
//		              + " TO_CHAR(D.YUKO_DATE, 'YYYY/MM/DD') YUKO_DATE,"					//�L������
//					  + " A.NENDO NENDO, "													//���ƔN�x
//					  + " A.KAISU KAISU, "													//���Ɖ�
//					  + " A.JIGYO_NAME JIGYO_NAME, "										//���Ɩ�
//					  + " COUNT(*) COUNT"													//���Ɛ�
//		              
//		              
//		              + " FROM SHINSEIDATAKANRI A,"
//		              + " SHINSAKEKKA B,"
//		              + " MASTER_SHINSAIN C,"
//		              + " SHINSAININFO D "
//		              + " WHERE"
//		              + " A.SYSTEM_NO = B.SYSTEM_NO"							//�V�X�e���ԍ�
//				      + " AND B.SHINSAIN_NO = C.SHINSAIN_NO"					//�R�����ԍ�
//					  + " AND B.SHINSAIN_NO = SUBSTR(D.SHINSAIN_ID,4,7)"		//�R�����ԍ�(7��)
//					  +	" AND A.DEL_FLG = 0"
//					//2005/04/28 �ǉ� ��������------------------------------------------
//					//��Ղŏ�ID��06(�w�U��)��CSV�ɏo�͂��Ȃ��悤�ɏ����̒ǉ�
//					  + " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
//					  + " AND A.JIGYO_KUBUN = C.JIGYO_KUBUN"					//���Ƌ敪
//					//�ǉ� �����܂�-----------------------------------------------------
//					  //2005.11.16 iso �R�������������悤�ύX
//					  + " AND B.SHINSA_JOKYO <> '1'"
//					;
//		//2005.11.16 iso ���������͕ʃ��\�b�h��
//		StringBuffer query = new StringBuffer(select);
//
//		//�\���ԍ�
//		if(searchInfo.getUketukeNo() != null && !searchInfo.getUketukeNo().equals("")){
//			query.append(" AND B.UKETUKE_NO = '" + searchInfo.getUketukeNo() + "'");
//		}
//		//���ƃR�[�h
//		List jigyoCdValueList = searchInfo.getJigyoCdValueList();
//		if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
//			query.append(" AND SUBSTR(B.JIGYO_ID, 3, 5) IN (")
//				 .append(changeIterator2CSV(searchInfo.getJigyoCdValueList().iterator()))
//				 .append(")");
//		}
//		//�S�����Ƌ敪�i�����j
//		if(searchInfo.getTantoJigyoKubun() != null && searchInfo.getTantoJigyoKubun().size() != 0){
//			query.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(changeIterator2CSV(searchInfo.getTantoJigyoKubun().iterator()))
//				 .append(")");
//		}
//		//�N�x
//		if(searchInfo.getNendo() != null && !searchInfo.getNendo().equals("")){
//			query.append(" AND A.NENDO = '" + EscapeUtil.toSqlString(searchInfo.getNendo()) + "'");
//		}
//		//��
//		if(searchInfo.getKaisu() != null && !searchInfo.getKaisu().equals("")){
//			query.append(" AND A.KAISU = '" + EscapeUtil.toSqlString(searchInfo.getKaisu()) + "'");
//		}
//		//�\���Ҏ����i������-���j
//		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	
//			query.append(" AND A.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
//		}
//		//�\���Ҏ����i������-���j
//		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	
//			query.append(" AND A.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
//		}
//		//�\���Ҏ����i�t���K�i-���j
//		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	
//			query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
//		}
//		//�\���Ҏ����i�t���K�i-���j
//		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){
//			query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
//		}
//		//�\���Ҏ����i���[�}��-���j
//		if(searchInfo.getNameRoSei() != null && !searchInfo.getNameRoSei().equals("")){
//			query.append(" AND UPPER(A.NAME_RO_SEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()) + "%'");
//		}
//		//�\���Ҏ����i���[�}��-���j
//		if(searchInfo.getNameRoMei() != null && !searchInfo.getNameRoMei().equals("")){
//			query.append(" AND UPPER(A.NAME_RO_MEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()) + "%'");
//		}
//		//�����@�փR�[�h
//		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){
//			query.append(" AND A.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
//		}
//		//�זڔԍ�
//		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){
//			query.append(" AND A.BUNKASAIMOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'");
//		}
//		//�n���̋敪
//		if(searchInfo.getKeiName() != null && !searchInfo.getKeiName().equals("")){
//			query.append(" AND A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%'");
//		}
//		//�����ԍ�			2005/10/26�@�ǉ�
//		if(searchInfo.getSeiriNo() != null && !searchInfo.getSeiriNo().equals("")){
//		    query.append(" AND A.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//		
//		//�g�ݍ��킹�X�e�[�^�X��
//		if(statusInfo != null && statusInfo.hasQuery()){
//			query.append(" AND")
//					 .append(statusInfo.getQuery());
//		}
//		StringBuffer query = new StringBuffer(getQueryString(select, userInfo, searchInfo, statusInfo));
//		
////		2005.10.26 ���Ɩ��̌�����\�����邽�߂ɒǉ�
//		query.append("GROUP BY ")
//			.append("B.SHINSAIN_NO, C.NAME_KANJI_SEI, C.NAME_KANJI_MEI, ")
//			.append("C.SHOZOKU_NAME, C.BUKYOKU_NAME, C.SHOKUSHU_NAME,")
//			.append("C.SOFU_ZIP, C.SOFU_ZIPADDRESS, C.SOFU_ZIPEMAIL,")
//			.append("C.SHOZOKU_TEL, C.SHOZOKU_FAX,")
//			.append("C.BIKO,")
//			.append("D.SHINSAIN_ID, D.PASSWORD, D.YUKO_DATE,")
//			.append("A.NENDO, A.KAISU, A.JIGYO_NAME")
//			;
//					
//		//�\�[�g���i�R�����ԍ��̏����j
//		query.append(" ORDER BY B.SHINSAIN_NO");

		//2006/4/14�C���@By�@��
		//���X�^�[�g�̎��Ƌ敪���U�ł���̂ɁA��ՂƓ����R����������U�肵�Ă���B
		//�R�����}�X�^����R���������擾���鎞�A���ƃf�[�^�Ǘ��e�[�u���̎��Ƌ敪��
		//�R�����}�X�^�̎��Ƌ敪�͒��ڂɌ��Ԃ��Ƃ��ł��Ȃ��̂ŏC������
		
		//�R�������Ƌ敪��ݒ肷��
		String jigyoKbn = "";
		if (IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(searchInfo.getJigyoKubun())){
			//�w�p�n���i�����j
			jigyoKbn = IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO;
		}else{
			//��ՂƎ��X�^�[�g
			jigyoKbn = IJigyoKubun.JIGYO_KUBUN_KIBAN;
		}
		
		String selectSeiri = "SELECT DISTINCT"
						+ " B.SHINSAIN_NO SHINSAIN_NO, "						//�R�����ԍ�
						+ " C.NAME_KANJI_SEI NAME_KANJI_SEI,"					//�R�������i����-���j
						+ " C.NAME_KANJI_MEI NAME_KANJI_MEI,"					//�R�������i����-���j
						+ " C.SHOZOKU_NAME SHOZOKU_NAME,"						//�R���������@�֖�
						+ " C.BUKYOKU_NAME BUKYOKU_NAME,"						//�R�������ǖ�
						+ " C.SHOKUSHU_NAME SHOKUSHU_NAME,"						//�R�����E��
						+ " C.SOFU_ZIP SOFU_ZIP,"								//���t��i�X�֔ԍ��j
						+ " C.SOFU_ZIPADDRESS SOFU_ZIPADDRESS,"					//���t��i�Z���j
						+ " C.SOFU_ZIPEMAIL SOFU_ZIPEMAIL,"						//���t��iEmail�j
						+ " C.SHOZOKU_TEL SHOZOKU_TEL,"							//�d�b�ԍ�
						+ " C.SHOZOKU_FAX SHOZOKU_FAX,"							//FAX�ԍ�
						+ " C.BIKO BIKO,"										//���l
						+ " D.SHINSAIN_ID SHINSAIN_ID,"							//�R����ID
						+ " D.PASSWORD PASSWORD,"								//�p�X���[�h
						+ " TO_CHAR(D.YUKO_DATE, 'YYYY/MM/DD') YUKO_DATE,"		//�L������
						+ " A.JURI_SEIRI_NO,"									//�����ԍ��i�w�n�j
						+ " A.NENDO NENDO,"										//���ƔN�x
						+ " A.KAISU KAISU,"										//���Ɖ�
						+ " A.JIGYO_NAME JIGYO_NAME,"							//���Ɩ�
						+ " A.JIGYO_ID"											//����ID
						+ " FROM SHINSEIDATAKANRI A,"
						+ "      SHINSAKEKKA B,"
						//2006/4/14 begin
						//+ "      MASTER_SHINSAIN C,"
						+ " (SELECT * FROM MASTER_SHINSAIN WHERE JIGYO_KUBUN = " + jigyoKbn + ") C,"
						//end
						+ "      SHINSAININFO D "
						+ " WHERE"
						+ " A.SYSTEM_NO = B.SYSTEM_NO"							//�V�X�e���ԍ�
						+ " AND B.SHINSAIN_NO = C.SHINSAIN_NO"					//�R�����ԍ�
						+ " AND B.SHINSAIN_NO = SUBSTR(D.SHINSAIN_ID,4,7)"		//�R�����ԍ�(7��)
						+ " AND A.DEL_FLG = 0"
						//2005/04/28 �ǉ� ��������------------------------------------------
						//��Ղŏ�ID��06(�w�U��)��CSV�ɏo�͂��Ȃ��悤�ɏ����̒ǉ�
						//+ " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
						+ " AND (A.JIGYO_KUBUN = '1' OR A.JOKYO_ID != '06')"
						//+ " AND A.JIGYO_KUBUN = C.JIGYO_KUBUN"					//���Ƌ敪
						
						//�ǉ� �����܂�-----------------------------------------------------
						//2005.11.16 iso �R�������������悤�ύX
						+ " AND B.SHINSA_JOKYO <> '1'"
						;

		String selectCount = "SELECT"
						+ " B.SHINSAIN_NO SHINSAIN_NO, "						//�R�����ԍ�
						+ " A.JIGYO_ID JIGYO_ID, "								//���Ɩ�
						+ " COUNT(*) COUNT"										//���Ɛ�
						+ " FROM SHINSEIDATAKANRI A,"
						+ " SHINSAKEKKA B"
						+ " WHERE"
						+ " A.SYSTEM_NO = B.SYSTEM_NO"							//�V�X�e���ԍ�
						+	" AND A.DEL_FLG = 0"
						//2005/04/28 �ǉ� ��������------------------------------------------
						//��Ղŏ�ID��06(�w�U��)��CSV�ɏo�͂��Ȃ��悤�ɏ����̒ǉ�
						//+ " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
						+ " AND (A.JIGYO_KUBUN = '1' OR A.JOKYO_ID != '06')"
						//�ǉ� �����܂�-----------------------------------------------------
						//2005.11.16 iso �R�������������悤�ύX
						+ " AND B.SHINSA_JOKYO <> '1'"
						;

		StringBuffer querySeiri = new StringBuffer(getQueryString(selectSeiri, userInfo, searchInfo, statusInfo));
		StringBuffer queryCount = new StringBuffer(getQueryString(selectCount, userInfo, searchInfo, statusInfo));
		
//2005.10.26 ���Ɩ��̌�����\�����邽�߂ɒǉ�
		queryCount.append("GROUP BY B.SHINSAIN_NO, A.JIGYO_ID");
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT")
			.append(" E.SHINSAIN_NO")
			.append(", E.NAME_KANJI_SEI")
			.append(", E.NAME_KANJI_MEI")
			.append(", E.SHOZOKU_NAME")
			.append(", E.BUKYOKU_NAME")
			.append(", E.SHOKUSHU_NAME")
			.append(", E.SOFU_ZIP")
			.append(", E.SOFU_ZIPADDRESS")
			.append(", E.SOFU_ZIPEMAIL")
			.append(", E.SHOZOKU_TEL")
			.append(", E.SHOZOKU_FAX")
			.append(", E.BIKO")
			.append(", E.SHINSAIN_ID")
			.append(", E.PASSWORD")
			.append(", E.YUKO_DATE")
			.append(", E.NENDO")
			.append(", E.KAISU")
			.append(", E.JIGYO_NAME")
			.append(", F.COUNT")
			.append(", E.JURI_SEIRI_NO")
			.append(" FROM")
			.append(" (" + querySeiri + ") E")
			.append(", (" + queryCount + ") F")
			.append(" WHERE E.JIGYO_ID = F.JIGYO_ID")
			.append(" AND E.SHINSAIN_NO = F.SHINSAIN_NO")
			;
		
		//�\�[�g���i�R�����ԍ��̏����j
		query.append(" ORDER BY E.SHINSAIN_NO");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		//-----------------------
		// �X�e�[�^�X�X�V�V�X�e����t�ԍ��擾SQL���̍쐬
		//-----------------------
		String select2 = "SELECT DISTINCT B.SYSTEM_NO"		//�V�X�e����t�ԍ�
					   + " FROM SHINSEIDATAKANRI A,"
					   + " SHINSAKEKKA B "
					   + " WHERE "
					   + " A.SYSTEM_NO = B.SYSTEM_NO"
					   + " AND A.DEL_FLG = 0"
					   + " AND B.SHINSAIN_NAME_KANJI_SEI IS NOT NULL "
					//2005/04/28 �ǉ� ��������------------------------------------------
					//��Ղŏ�ID��06(�w�U��)�͎擾���Ȃ��悤�ɏ����̒ǉ�
					   //+ " AND (A.JIGYO_KUBUN != '4' OR A.JOKYO_ID != '06')"
					   + " AND (A.JIGYO_KUBUN = '1' OR A.JOKYO_ID != '06')"
					//�ǉ� �����܂�-----------------------------------------------------
					   //2005.11.16 iso �R�������������悤�ύX
					   + " AND B.SHINSA_JOKYO <> '1'"
		;

//		StringBuffer query2 = new StringBuffer(select2);
//
//		//�\���ԍ�
//		if(searchInfo.getUketukeNo() != null && !searchInfo.getUketukeNo().equals("")){
//			query2.append(" AND B.UKETUKE_NO = '" + searchInfo.getUketukeNo() + "'");
//		}
//		//���ƃR�[�h
//		if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
//			query2.append(" AND SUBSTR(B.JIGYO_ID, 3, 5) IN (")
//				 .append(changeIterator2CSV(searchInfo.getJigyoCdValueList().iterator()))
//				 .append(")");
//		}
//		//�S�����Ƌ敪�i�����j
//		if(searchInfo.getTantoJigyoKubun() != null && searchInfo.getTantoJigyoKubun().size() != 0){
//			query2.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(changeIterator2CSV(searchInfo.getTantoJigyoKubun().iterator()))
//				 .append(")");
//		}
//		//�N�x
//		if(searchInfo.getNendo() != null && !searchInfo.getNendo().equals("")){
//			query2.append(" AND A.NENDO = '" + EscapeUtil.toSqlString(searchInfo.getNendo()) + "'");
//		}
//		//��
//		if(searchInfo.getKaisu() != null && !searchInfo.getKaisu().equals("")){
//			query2.append(" AND A.KAISU = '" + EscapeUtil.toSqlString(searchInfo.getKaisu()) + "'");
//		}
//		//�\���Ҏ����i������-���j
//		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	
//			query2.append(" AND A.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
//		}
//		//�\���Ҏ����i������-���j
//		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	
//			query2.append(" AND A.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
//		}
//		//�\���Ҏ����i�t���K�i-���j
//		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	
//			query2.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
//		}
//		////�\���Ҏ����i�t���K�i-���j
//		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){
//			query2.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
//		}
//		//�\���Ҏ����i���[�}��-���j
//		if(searchInfo.getNameRoSei() != null && !searchInfo.getNameRoSei().equals("")){
//			query2.append(" AND UPPER(A.NAME_RO_SEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()) + "%'");
//		}
//		//�\���Ҏ����i���[�}��-���j
//		if(searchInfo.getNameRoMei() != null && !searchInfo.getNameRoMei().equals("")){
//			query2.append(" AND UPPER(A.NAME_RO_MEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()) + "%'");
//		}
//		//�����@�փR�[�h
//		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){
//			query2.append(" AND A.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
//		}
//		//�זڔԍ�
//		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){
//			query2.append(" AND A.BUNKASAIMOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'");
//		}
//		//�n���̋敪
//		if(searchInfo.getKeiName() != null && !searchInfo.getKeiName().equals("")){
//			query2.append(" AND A.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%'");
//		}
//		//�����ԍ�			2005/10/26�@�ǉ�
//		if(searchInfo.getSeiriNo() != null && !searchInfo.getSeiriNo().equals("")){
//			query2.append(" AND A.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//		
//		//�g�ݍ��킹�X�e�[�^�X��
//		if(statusInfo != null && statusInfo.hasQuery()){
//			query2.append(" AND")
//					 .append(statusInfo.getQuery());
//		}
		StringBuffer query2 = new StringBuffer(getQueryString(select2, userInfo, searchInfo, statusInfo));
		
		if(log.isDebugEnabled()){
			log.debug("query2:" + query2);
		}

		//-----------------------
		// CSV�f�[�^���X�g�擾
		//-----------------------
		Connection connection = null;
		List csv_data = new ArrayList();
		List sys_no = new ArrayList();

		try {
			connection = DatabaseUtil.getConnection();
//			csv_data = SelectUtil.selectCsvList(connection,query.toString(), true);
//	���Ƃ��ƂɌ�����\������悤�ύX
			csv_data = SelectUtil.selectCsvList(connection,query.toString(), false);					
			sys_no = SelectUtil.select(connection,query2.toString());
		} catch (NoDataFoundException e) {
			List errors = new ArrayList();
			errors.add(new ErrorInfo("errors.5023"));
			throw new ValidationException("�R���˗������o�͂ł����Ԃ̐R�������o�^����Ă��܂���B", errors);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV�f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//-----------------------
		// CSV�t�@�C���o��
		//-----------------------
		//2005.10.26	���Ƃ��ƂɌ�����\������悤�ύX
		//-----�f�[�^�\����ϊ��i�d���f�[�^���܂ޒP�ꃊ�X�g����R�������Ƃ̃}�b�v�֕ϊ�����j
		String[] columnArray = {"�R�����ԍ�"
								,"�R�������i����-���j"
								,"�R�������i����-���j"
								,"�R�������������@�֖�"
								,"�R�������ǖ�"
								,"�R�����E��"
								,"���t��i�X�֔ԍ��j"
								,"���t��i�Z���j"
								,"���t��iEmail�j"
								,"�d�b�ԍ�"
								,"FAX�ԍ�"
								,"���l"
								,"�R����ID"
								,"�p�X���[�h"
								,"�L������"
								,"���Ə��"
								,"�����ԍ��i�w�n�j"						//2005.01.06 iso �ǉ�
								};
								
							
		List newList = new ArrayList(Arrays.asList(columnArray));		//�V�������R�������Ƃ̃��X�g(�VCSV1�s��)
		ArrayList newCsvList = new ArrayList();							//CSV�Ƃ��ďo�͂��郊�X�g
		String jigyoInfo = "";
		String beforeShinsainNo = "";
		//2005.01.06 iso �����ԍ��Ή�
		String beforeJigyo = "";
		String allSeiriNo = "";
			
		for(int i = 0; i < csv_data.size(); i++) {
			//SQL���̏��Ԃ�ύX������A�ς��̂Œ���
			List recordList 			= (List)csv_data.get(i);
			//2005.01.06 iso ������@��ύX
//			String shinsainNo  			= recordList.get(0).toString();		//�R�����ԍ�
////			String shinsainNameKanjiSei	= recordList.get(1).toString();		//�R�������i����-���j
////			String shinsainNameKanjiMei	= recordList.get(2).toString();		//�R�������i����-���j
////			String shozokuName			= recordList.get(3).toString();		//�R�������������@�֖�
////			String bukyokuName			= recordList.get(4).toString();		//�R�������ǖ�
////			String shokushuName			= recordList.get(5).toString();		//�R�����E��	
////			String sofuZip				= recordList.get(6).toString();		//���t��i�X�֔ԍ��j	
////			String sofuZipaddress		= recordList.get(7).toString();		//���t��i�Z���j
////			String sofuZipemail			= recordList.get(8).toString();		//���t��iEmail�j
////			String shozokuTel			= recordList.get(9).toString();		//�d�b�ԍ�
////			String shozokuFax			= recordList.get(10).toString();	//FAX�ԍ�
////			String biko					= recordList.get(11).toString();	//���l
////			String shinsainId  			= recordList.get(12).toString();	//�R����ID
////			String password  			= recordList.get(13).toString();	//�p�X���[�h
////			String yukoDate  			= recordList.get(14).toString();	//�L������
//			String nendo  				= recordList.get(15).toString();	//���ƔN�x
//			String kaisu  				= recordList.get(16).toString();	//���Ɖ�
//			String jigyoName  			= recordList.get(17).toString();	//���Ɩ�
//			String count	  			= recordList.get(18).toString();	//���Ɛ�
			
			String shinsainNo  			= recordList.get(0).toString();	//�R�����ԍ�
            int j = 15;
			String nendo  				= recordList.get(j++).toString();	//���ƔN�x
			String kaisu  				= recordList.get(j++).toString();	//���Ɖ�
			String jigyoName  			= recordList.get(j++).toString();	//���Ɩ�
			String count	  			= recordList.get(j++).toString();	//���Ɛ�
			//2005.01.06 iso �ǉ�
			String seiriNo				= recordList.get(j++).toString();	//�����ԓ��i�w�n�j
			
			String kaisuHyoji = "";
			if(!kaisu.equals("1")) {
				kaisuHyoji = "��" + kaisu + "�� ";							//�񐔂�1��ȏ�̏ꍇ�͕\������
			}
				
			//1�O�̐R�����ԍ��ƈ�v�����ꍇ�A�����˗����̃f�[�^�Ȃ̂Ŏ��Ə����܂Ƃ߂�(���s������)
			if(shinsainNo.equals(beforeShinsainNo)) {
//				jigyoInfo = jigyoInfo + "\r\n����" + nendo + "�N�x " + kaisuHyoji + jigyoName + " " + count + "��";
				//2005.01.06 iso �����ԍ��ǉ��Ή�
				if(!beforeJigyo.equals(nendo+kaisu+jigyoName)) {
					//�O�̎��Ƃƈ�v�����ꍇ�́A���Ə��͂��ł�jigyoInfo�Ɋi�[����Ă���B
					//��v���Ȃ����̂ݎ��Ə��Ɋi�[����B
					jigyoInfo = jigyoInfo + "\r\n����" + nendo + "�N�x " + kaisuHyoji + jigyoName + " " + count + "��";
					
					//2006.01.16 iso �������Ƃ��������\�������o�O���C��
					//1�O�̎��Ƃ��X�V
					beforeJigyo = nendo+kaisu+jigyoName;
				}
				if(!StringUtil.isBlank(seiriNo)) {
					allSeiriNo += "�@" + seiriNo;
				}
			} else {
				//1�O�ƈႤ�R�����ԍ��̏ꍇ�A�O�̐R������1�s���f�[�^��VCSV���X�g�ɓo�^�B
				if(jigyoInfo != null && !jigyoInfo.equals("")) {		//�ŏ��͋�Ȃ̂�(columnArray�i�[��)���Ə���ǉ����Ȃ�
					newList.add(jigyoInfo);		//�R����(1�O)���Ƃɂ܂Ƃ߂����Ə��
					//2005.01.06 iso �����ԍ��ǉ��Ή�
					newList.add(allSeiriNo);	//�R����(1�O)���Ƃɂ܂Ƃ߂������ԍ�
				}
				newCsvList.add(newList);		//�o��CSV�ɐR�������Ƃɂ܂Ƃ߂��f�[�^���Z�b�g
					
				//1�O�̐R�������̏����o�̓��X�g�ɃZ�b�g������A���R�������ŁA�f�[�^������������
				newList = new ArrayList(recordList.subList(0, 15));	//newList�����R�������ŏ�����
				jigyoInfo = "����" + nendo + "�N�x "				//���Ə��������ŏ�����
							+ kaisuHyoji + jigyoName + " " + count + "��";
				//2005.01.06 iso �����ԍ��ǉ��Ή�
				allSeiriNo = seiriNo;								//�����ԍ��i�w�n�j�������ŏ�����
				
				//���݂̐R�����ԍ������̔�r�Ɏg�����߂ɃZ�b�g
				beforeShinsainNo = recordList.get(0).toString();

				//2006.01.16 iso �������Ƃ��������\�������o�O���C��
				//1�O�̎��Ƃ����݂̎��Ƃŏ�����
				beforeJigyo = nendo+kaisu+jigyoName;
			}
			//�Ō�̃f�[�^�͏��else�Ɉ���������Ȃ��̂ŁA������csvList�Ɋi�[����B
			if(i == csv_data.size()-1) {
				newList.add(jigyoInfo);								//�R����(1�O)���Ƃɂ܂Ƃ߂����Ə��
				//2005.01.06 iso �����ԍ��ǉ��Ή�
				newList.add(allSeiriNo);							//�R����(1�O)���Ƃɂ܂Ƃ߂������ԍ�
				newCsvList.add(newList);							//�o��CSV�ɐR�������Ƃɂ܂Ƃ߂��f�[�^���Z�b�g
			}	
		}
		//�t�@�C���o�̓p�X���w��
		//2005/09/09 takano �t�H���_�����~���b�P�ʂɕύX�B�O�̂��ߓ����ɓ����������g�ݍ��݁B
		String filepath = null;
		synchronized(log){
			filepath = IRAI_WORK_FOLDER + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
		}
//		CsvUtil.output(csv_data, filepath, CSV_FILENAME);
		CsvUtil.output(newCsvList, filepath, CSV_FILENAME);

		//-----------------------
		// �˗����t�@�C���̃R�s�[
		//-----------------------
		//TODO ����̈ʒu�Ƀt�H�[�}�b�g�t�@�C�������������ꍇ�A�G���[�ɂ���ׂ����H
		FileUtil.fileCopy(new File(IRAI_FORMAT_PATH + IRAI_FORMAT_FILE_NAME), new File(filepath + IRAI_FORMAT_FILE_NAME));
		FileUtil.fileCopy(new File(IRAI_FORMAT_PATH + "$"), new File(filepath + "$"));

		//-----------------------
		// �t�@�C���̈��k
		//-----------------------
		//���k�t�@�C����
		String comp_file_name = CSV_FILENAME + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
		//���k����
		FileUtil.fileCompress(filepath, filepath, comp_file_name);

		//-------------------------------------
		// �����󋵔���t���O
		//-------------------------------------
		boolean success = false;
		
		//-------------------------------------
		//�쐬�����t�@�C����ǂݍ��ށB
		//-------------------------------------
		//�t�@�C�����擾����B
		File exe_file = new File(filepath + comp_file_name + ".EXE");
		FileResource iodFileResource = null;

		try {	
			iodFileResource = FileUtil.readFile(exe_file);
			success = true;
		} catch (IOException e) {
			throw new ApplicationException(
				"�쐬�t�@�C��'" + comp_file_name + ".EXE'���̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.8005"),
				e);
		}finally{
			if (success) {
				//-------------------------------------
				//��ƃt�@�C���̍폜
				//-------------------------------------
				//TODO �������� ���e�X�g�p�쐬IOD�t�@�C�����폜���Ȃ��B
				FileUtil.delete(exe_file.getParentFile());
			}
		}

		//-------------------------
		// �X�e�[�^�X�X�V����
		//-------------------------
		int cnt_sn = sys_no.size();
		ShinseiDataPk[] shinseiPk = new ShinseiDataPk[cnt_sn];
		for(int i=0; i<cnt_sn; i++){
			HashMap value = (HashMap)sys_no.get(i);

			shinseiPk[i] = new ShinseiDataPk((String)value.get("SYSTEM_NO"));
		}
		
		IShinseiMaintenance shinseiMainte = new ShinseiMaintenance();
		shinseiMainte.updateStatusForShinsaIraiIssue(userInfo, shinseiPk);

		//IOD�t�@�C���̃��^�[��
		return iodFileResource;
	}
	
	/**
	 * ���Ƌ敪���擾����B
	 */
	public String selectJigyoKubun(UserInfo userInfo, String jigyoCd)
		throws ApplicationException {
			
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			return MasterJigyoInfoDao.selectJigyoKubun(connection, jigyoCd);
			
		} catch (NoDataFoundException e) {
			throw e;
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * �����ŗ^����ꂽIterator���J���}��؂��String�ɕϊ����ĕԋp.<br />
	 * <br />
	 * ������Iterator�̗v�f��String�ŃL���X�e�B���O���āA���ׂĂ̗v�f���J���}��؂�ŘA������B
	 * �������A���ׂĂ̗v�f���_�u���N�H�[�e�[�V�����ň͂��B�����A������Itarator��null�̏ꍇ�́A��O��throw����B
	 * �܂��A�v�f����̏ꍇ��""��ԋp����B
	 * @param ite	Iterator
	 * @return	ite��v�f���J���}��؂�ŘA������string
	 */
	private static String changeIterator2CSV(Iterator ite){
		return StringUtil.changeIterator2CSV(ite, true);
	}	

	/**
	 * ����U�茟�������I�u�W�F�N�g���猟���������擾��SQL�̖₢���킹�����𐶐�����B
	 * ���������₢���킹�����͑������̕�����̌��Ɍ��������B
	 * @param select    
	 * @param userInfo  
	 * @param searchInfo
	 * @return String SQL�̖₢���킹����
	 */
	protected static String getQueryString(
										String select,
										UserInfo userInfo,
										WarifuriSearchInfo searchInfo,
										CombinedStatusSearchInfo statusInfo) {

		//-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
		StringBuffer query = new StringBuffer(select);

		//�\���ԍ�
		if(!StringUtil.isBlank(searchInfo.getUketukeNo())){
			query.append(" AND B.UKETUKE_NO = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getUketukeNo()));
            query.append("'");
		}
		//���ƃR�[�h
		List jigyoCdValueList = searchInfo.getJigyoCdValueList();
		if(jigyoCdValueList != null && jigyoCdValueList.size() != 0){
			query.append(" AND SUBSTR(B.JIGYO_ID, 3, 5) IN (");
            query.append(changeIterator2CSV(searchInfo.getJigyoCdValueList().iterator()));
            query.append(")");
		}
		//�S�����Ƌ敪�i�����j
		if(searchInfo.getTantoJigyoKubun() != null && searchInfo.getTantoJigyoKubun().size() != 0){
			query.append(" AND B.JIGYO_KUBUN IN (");
            query.append(changeIterator2CSV(searchInfo.getTantoJigyoKubun().iterator()));
            query.append(")");
		}
		//�N�x
		if(!StringUtil.isBlank(searchInfo.getNendo())){
			query.append(" AND A.NENDO = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getNendo()));
            query.append("'");
		}
		//��
		if(!StringUtil.isBlank(searchInfo.getKaisu())){
			query.append(" AND A.KAISU = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getKaisu()));
            query.append("'");
		}
		//�\���Ҏ����i������-���j
		if(!StringUtil.isBlank(searchInfo.getNameKanjiSei())){	
			query.append(" AND A.NAME_KANJI_SEI LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()));
            query.append("%'");
		}
		//�\���Ҏ����i������-���j
		if(!StringUtil.isBlank(searchInfo.getNameKanjiMei())){	
			query.append(" AND A.NAME_KANJI_MEI LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()));
            query.append("%'");
		}
		//�\���Ҏ����i�t���K�i-���j
		if(!StringUtil.isBlank(searchInfo.getNameKanaSei())){	
			query.append(" AND A.NAME_KANA_SEI LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameKanaSei()));
            query.append("%'");
		}
		//�\���Ҏ����i�t���K�i-���j
		if(!StringUtil.isBlank(searchInfo.getNameKanaMei())){
			query.append(" AND A.NAME_KANA_MEI LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameKanaMei()));
            query.append("%'");
		}
		//�\���Ҏ����i���[�}��-���j
		if(!StringUtil.isBlank(searchInfo.getNameRoSei())){
			query.append(" AND UPPER(A.NAME_RO_SEI) LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()));
            query.append("%'");
		}
		//�\���Ҏ����i���[�}��-���j
		if(!StringUtil.isBlank(searchInfo.getNameRoMei())){
			query.append(" AND UPPER(A.NAME_RO_MEI) LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()));
            query.append("%'");
		}
		//�����@�փR�[�h
		if(!StringUtil.isBlank(searchInfo.getShozokuCd())){
			query.append(" AND A.SHOZOKU_CD = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getShozokuCd()));
            query.append("'");
		}
		//�זڔԍ�
		if(!StringUtil.isBlank(searchInfo.getBunkaSaimokuCd())){
			query.append(" AND A.BUNKASAIMOKU_CD = '");
            query.append(EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()));
            query.append("'");
		}
		//�n���̋敪
		if(!StringUtil.isBlank(searchInfo.getKeiName())){
			query.append(" AND A.KEI_NAME LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getKeiName()));
            query.append("%'");
		}
// 2006/07/03 dyh add start �����F����U�茟����ʂŐR�������������@�֖�������ǉ�
        //�R�������������@�֖�
        if(!StringUtil.isBlank(searchInfo.getShozokuName())){
//2006/10/5 ���Q�֌W������Ή�ʕ\���͕���邽�߁A���ƒP�ʂ̑S�R���������擾�Ƃ���
//            query.append(" AND B.SHOZOKU_NAME LIKE '%");
//            query.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()));
//            query.append("%'");
			query.append(" AND B.SYSTEM_NO IN (SELECT DISTINCT SYSTEM_NO FROM SHINSAKEKKA");
			query.append(	" WHERE SHOZOKU_NAME LIKE '%");
			query.append(EscapeUtil.toSqlString(searchInfo.getShozokuName()));
			query.append("%')");
        }
// 2006/07/03 dyh add end
		//�����ԍ� 2005/10/17�@�ǉ�
		if(!StringUtil.isBlank(searchInfo.getSeiriNo())){
			query.append(" AND A.JURI_SEIRI_NO LIKE '%");
            query.append(EscapeUtil.toSqlString(searchInfo.getSeiriNo()));
            query.append("%'");
		}
		//���Q�֌W
		if(searchInfo.getRigai() != null && "1".equals(searchInfo.getRigai())){
			query.append(" AND B.SYSTEM_NO IN (SELECT DISTINCT SYSTEM_NO FROM SHINSAKEKKA");
			//2007.01.15 iso �����������u�R�������v���u���͊����v�֕ύX
//			query.append(					" WHERE RIGAI = '1' AND SHINSA_JOKYO = '1')");
			query.append(					" WHERE RIGAI = '1' AND NYURYOKU_JOKYO = '1')");
		}
		
		//���U��
		if(searchInfo.getWarifuriFlg() != null && "1".equals(searchInfo.getWarifuriFlg())){
			query.append(" AND B.SYSTEM_NO IN (SELECT SYSTEM_NO");
			query.append("  FROM (");
			query.append("    SELECT SYSTEM_NO,");
//			2007.01.15 iso �����������u�R�������v���u���͊����v�֕ύX
//			query.append("           SUM(CASE WHEN RIGAI='1' AND SHINSA_JOKYO = '1' THEN 1 ELSE 0 END) RIGAI_CNT,");
			query.append("           SUM(CASE WHEN RIGAI='1' AND NYURYOKU_JOKYO = '1' THEN 1 ELSE 0 END) RIGAI_CNT,");
			query.append("           SUM(DECODE(DAIRI,'1',1,0)) DAIRI_CNT");
			query.append("      FROM SHINSAKEKKA");
			query.append("     GROUP BY SYSTEM_NO ) ");
			query.append(" WHERE RIGAI_CNT > DAIRI_CNT");
//			query.append(" WHERE A.RIGAI_CNT > 0");
//			query.append("   AND A.DAIRI_CNT = 0");
			query.append(")");
		}
		
		//�g�ݍ��킹�X�e�[�^�X��
		if(statusInfo != null && statusInfo.hasQuery()){
			query.append(" AND")
				 .append(statusInfo.getQuery());
		}

		return query.toString();
	}
}