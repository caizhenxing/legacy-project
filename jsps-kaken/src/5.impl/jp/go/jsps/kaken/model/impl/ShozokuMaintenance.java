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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.IShozokuMaintenance;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.BukyokutantoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.RuleInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseishaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShozokuInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.KikanInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.model.vo.ShozokuSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.CheckDiditUtil;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.impl.JigyoKanriInfoDao;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.util.SendMailer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * �����@�֏��Ǘ��N���X</br>
 * </br>
 * <b>�T�v</b></br>
 * �����@�֏����Ǘ����܂��B
 * �����@�֒S���҂��X�V���ꂽ�ۂ́A���̏����@�ւɏ�������\���ҏ��Ɋւ��Ă��X�V���܂��B
 * �i�����@�ւ̖��̓���\���ҏ��Ƃ��ĕێ����Ă��邽�߁j
 * 
 */
public class ShozokuMaintenance implements IShozokuMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(ShozokuMaintenance.class);

	/** ���[���T�[�o�A�h���X */
	private static final String SMTP_SERVER_ADDRESS = ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS); 

	/** ���o�l�i���ꂵ�ĂP�j */
	private static final String FROM_ADDRESS = ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);
	
	/** �����F�\�������ߐ؂�����܂ł̓��t */
	protected static final int DATE_BY_SHONIN_KIGEN = ApplicationSettings.getInt(ISettingKeys.DATE_BY_SHONIN_KIGEN);
	
	/** ���[�����e�i�����@�֒S���҂ւ̖����F�m�F�ʒm�j�u�����v */
	protected static final String SUBJECT_SHINSEISHO_SHONIN_TSUCHI = ApplicationSettings.getString(ISettingKeys.SUBJECT_SHINSEISHO_SHONIN_TSUCHI);
		
	/** ���[�����e�i�����@�֒S���҂ւ̖����F�m�F�ʒm�j�u�{���v */
	protected static final String CONTENT_SHINSEISHO_SHONIN_TSUCHI = ApplicationSettings.getString(ISettingKeys.CONTENT_SHINSEISHO_SHONIN_TSUCHI);
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public ShozokuMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IShozokuMaintenance
	//---------------------------------------------------------------------

	/**
	 * �����@�֒S���ҏ���V�K�쐬����B<br/><br/>
	 * �o�^�f�[�^���������A�������ʂ�ԋp����B<br/><br/>
	 * 
	 * <b>1.�����@�֒S����ID�쐬</b><br/>
	 * �@(1)�����@�֖��̏��Ԃ��擾 <br/><br/>
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     TO_CHAR(MAX(SUBSTR(SHOZOKUTANTO_ID,6,2)) + 1,'FM00') COUNT
     * FROM
     *     SHOZOKUTANTOINFO
     * WHERE
     *     SHOZOKU_CD = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������addInfo�̕ϐ�shozokuCd</td></tr>
	 * </table><br/><br/>
	 * 
	 * �@(2)�����@�֒S����ID���쐬<br/>
	 * �����@�փR�[�h�i������addInfo�Ɋi�[����Ă���chozokuCd�j��(1)�Ŏ擾�����R�[�h��key�ɂ��ă`�F�b�N�f�W�b�g���擾����B
	 * �`�F�b�N�f�W�b�g��key�ɁA�擾�����`�F�b�N�f�W�b�g��t���������̂������@�֒S����ID�Ƃ���B
	 * �Ȃ��A�`�F�b�N�f�W�b�g��CheckDiditUtil�N���X��getCheckDigit()�ɂĎ擾����B<br/><br/>
	 * 
	 * <b>2.�p�X���[�h���쐬</b><br/>
	 * �p�X���[�h���s���[���Ɋ�Â��A�p�X���[�h���쐬����B<br/><br/>
	 * �@(1)�p�X���[�h���s���[���擾<br/><br/>
	 * �ȉ���SQL�����s���A�p�X���[�h���s���[�����擾����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     TAISHO_ID
	 *     ,MOJISU_CHK
	 *     ,CHAR_CHK1
	 *     ,CHAR_CHK2
	 *     ,CHAR_CHK3
	 *     ,CHAR_CHK4
	 *     ,CHAR_CHK5
	 *     ,YUKO_DATE
	 *     ,BIKO
	 * FROM
	 *     RULEINFO A
	 * WHERE
	 *     TAISHO_ID = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/><br/>
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TAISHO_ID</td><td>�Ώێ�ID '2'�@[�����@�֒S����]</td></tr>
	 * </table><br/><br/>
	 * �@(2)�p�X���[�h�쐬<br/><br/>
	 * RandomPwd�N���X��generate()���g�p���ăp�X���[�h���쐬����B
	 * �Ȃ��A�p�X���[�h�����Ɋւ���ڍׂ͊�������B<br/><br/>
	 * 
	 * <b>3.�����@�֒S���ҏ��̓o�^</b><br/>
	 * �@(1)�d���`�F�b�N<br/><br/>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * �����������ʁA���R�[�h���I�����ꂽ�ꍇ�́A���łɃ��R�[�h�o�^�ς݂Ȃ̂ŁA��O��throw����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- �����@�֒S����ID
	 *     ,A.SHOZOKU_CD				-- �����@�֖��i�R�[�h�j
	 *     ,A.SHOZOKU_NAME_KANJI			-- �����@�֖��i���{��j
	 *     ,A.SHOZOKU_RYAKUSHO			-- �����@�֖��i���́j
	 *     ,A.SHOZOKU_NAME_EIGO			-- �����@�֖��i�p���j
	 *     ,A.SHUBETU_CD				-- �@�֎��
	 *     ,A.PASSWORD				-- �p�X���[�h
	 *     ,A.SEKININSHA_NAME_SEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_NAME_MEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_YAKU			-- �ӔC�Җ�E
	 *     ,A.BUKYOKU_NAME				-- �S�����ۖ�
	 *     ,A.KAKARI_NAME				-- �S���W��
	 *     ,A.TANTO_NAME_SEI			-- �S���Җ��i���j
	 *     ,A.TANTO_NAME_MEI			-- �S���Җ��i���j
	 *     ,A.TANTO_TEL				-- �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
	 *     ,A.TANTO_FAX				-- �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
	 *     ,A.TANTO_EMAIL				-- �S���ҕ��Ǐ��ݒn�iEmail�j
	 *     ,A.TANTO_EMAIL2				-- �S���ҕ��Ǐ��ݒn�iEmail2�j
	 *     ,A.TANTO_ZIP				-- �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
	 *     ,A.TANTO_ADDRESS				-- �S���ҕ��Ǐ��ݒn�i�Z���j
	 *     ,A.NINSHOKEY_FLG				-- �F�؃L�[���s�t���O
	 *     ,A.BIKO				-- ���l
	 *     ,A.YUKO_DATE				-- �L������
	 *     ,A.ID_DATE				-- ID���s���t
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * <tr bgcolor="#FFFFFF"><td width="40%">SHOZOKUTANTO_ID</td><td>������addInfo�̕ϐ�shozokuTantoId</td></tr>
	 * </table><br/><br/>
	 * �@(2)�����@�֒S���҃e�[�u���֓o�^<br/>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * INSERT INTO SHOZOKUTANTO INFO(
	 *     SHOZOKUTANTO_ID				-- �����@�֒S����ID
	 *     ,SHOZOKU_CD				-- �����@�֖��i�R�[�h�j
	 *     ,SHOZOKU_NAME_KANJI			-- �����@�֖��i���{��j
	 *     ,SHOZOKU_RYAKUSHO			-- �����@�֖��i���́j
	 *     ,SHOZOKU_NAME_EIGO			-- �����@�֖��i�p���j
	 *     ,SHUBETU_CD				-- �@�֎��
	 *     ,PASSWORD				-- �p�X���[�h
	 *     ,SEKININSHA_NAME_SEI			-- �ӔC�Ҏ����i���j
	 *     ,SEKININSHA_NAME_MEI			-- �ӔC�Ҏ����i���j
	 *     ,SEKININSHA_YAKU				-- �ӔC�Җ�E
	 *     ,BUKYOKU_NAME				-- �S�����ۖ�
	 *     ,KAKARI_NAME				-- �S���W��
	 *     ,TANTO_NAME_SEI				-- �S���Җ��i���j
	 *     ,TANTO_NAME_MEI				-- �S���Җ��i���j
	 *     ,TANTO_TEL				-- �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
	 *     ,TANTO_FAX				-- �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
	 *     ,TANTO_EMAIL				-- �S���ҕ��Ǐ��ݒn�iEmail�j
	 *     ,TANTO_EMAIL2				-- �S���ҕ��Ǐ��ݒn�iEmail2�j
	 *     ,TANTO_ZIP				-- �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
	 *     ,TANTO_ADDRESS				-- �S���ҕ��Ǐ��ݒn�i�Z���j
	 *     ,NINSHOKEY_FLG				-- �F�؃L�[���s�t���O
	 *     ,BIKO					-- ���l
	 *     ,YUKO_DATE				-- �L������
	 *     ,A.ID_DATE				-- ID���s���t
	 *     ,DEL_FLG)				-- �폜�t���O
	 * VALUES(
	 *     ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?
	 * )
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֒S����ID</td><td>������addInfo�̕ϐ�shozokuTantoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i�R�[�h�j</td><td>������addInfo�̕ϐ�shozokuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i���{��j</td><td>������addInfo�̕ϐ�shozokuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i���́j</td><td>������addInfo�̕ϐ�shozokuRyakusho</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i�p���j</td><td>������addInfo�̕ϐ�shozokuNameEigo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�@�֎��</td><td>������addInfo�̕ϐ�shubetuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�p�X���[�h</td><td>������addInfo�̕ϐ�password</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�ӔC�Ҏ����i���j</td><td>������addInfo�̕ϐ�sekininshaNameSei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�ӔC�Ҏ����i���j</td><td>������addInfo�̕ϐ�sekininshaNameMei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�ӔC�Җ�E</td><td>������addInfo�̕ϐ�sekininshaYaku</td></tr>
 	 *     <tr bgcolor="#FFFFFF"><td>�S�����ۖ�</td><td>������addInfo�̕ϐ�bukyokuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���W��</td><td>������addInfo�̕ϐ�kakariName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���Җ��i���j</td><td>������addInfo�̕ϐ�tantoNameSei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���Җ��i���j</td><td>������addInfo�̕ϐ�tantoNameMei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j</td><td>������addInfo�̕ϐ�tantoTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j</td><td>������addInfo�̕ϐ�tantoFax</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�iEmail�j</td><td>������addInfo�̕ϐ�tantoEmail</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�iEmail2�j</td><td>������addInfo�̕ϐ�tantoEmail2</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j</td><td>������addInfo�̕ϐ�tantoZip</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�i�Z���j</td><td>������addInfo�̕ϐ�tantoAddress</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�F�؃L�[���s�t���O</td><td>������addInfo�̕ϐ�ninshokeyFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���l</td><td>������addInfo�̕ϐ�biko</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�L������</td><td>������addInfo�̕ϐ�yukoDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>ID���s���t</td><td>������addInfo�̕ϐ�idDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>0</td></tr>
	 * </table><br/><br/>
	 * 
	 * �@(3)�o�^�f�[�^�̎擾<br/>
	 * �o�^�f�[�^���擾����B<br/><br/>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- �����@�֒S����ID
	 *     ,A.SHOZOKU_CD				-- �����@�֖��i�R�[�h�j
	 *     ,A.SHOZOKU_NAME_KANJI			-- �����@�֖��i���{��j
	 *     ,A.SHOZOKU_RYAKUSHO			-- �����@�֖��i���́j
	 *     ,A.SHOZOKU_NAME_EIGO			-- �����@�֖��i�p���j
	 *     ,A.SHUBETU_CD				-- �@�֎��
	 *     ,A.PASSWORD				-- �p�X���[�h
	 *     ,A.SEKININSHA_NAME_SEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_NAME_MEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_YAKU			-- �ӔC�Җ�E
	 *     ,A.BUKYOKU_NAME				-- �S�����ۖ�
	 *     ,A.KAKARI_NAME				-- �S���W��
	 *     ,A.TANTO_NAME_SEI			-- �S���Җ��i���j
	 *     ,A.TANTO_NAME_MEI			-- �S���Җ��i���j
	 *     ,A.TANTO_TEL				-- �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
	 *     ,A.TANTO_FAX				-- �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
	 *     ,A.TANTO_EMAIL				-- �S���ҕ��Ǐ��ݒn�iEmail�j
	 *     ,A.TANTO_EMAIL2				-- �S���ҕ��Ǐ��ݒn�iEmail2�j
	 *     ,A.TANTO_ZIP				-- �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
	 *     ,A.TANTO_ADDRESS				-- �S���ҕ��Ǐ��ݒn�i�Z���j
	 *     ,A.NINSHOKEY_FLG				-- �F�؃L�[���s�t���O
	 *     ,A.BIKO				-- ���l
	 *     ,A.YUKO_DATE				-- �L������
	 *     ,A.ID_DATE				-- ID���s���t
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * <tr bgcolor="#FFFFFF"><td>SHOZOKUTANTO_ID</td><td>������addInfo�̕ϐ�shozokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>4.�\���ҏ��̍X�V</b><br/>
	 * �\���ҏ��e�[�u�����X�V����B<br/>
	 * �i�����@�֖������X�V����Ă���ꍇ�����邽�߁A�\���ҏ����X�V�������s���B�j<br/><br/>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * �Ώۃf�[�^��������Ȃ��Ƃ��A��O��throw����B<br/><br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE SHINSEISHAINFO SET
	 *     SHOZOKU_NAME = ?
	 *     ,SHOZOKU_NAME_EIGO = ?
	 *     ,SHOZOKU_NAME_RYAKU = ?
	 * WHERE
	 *     SHOZOKU_CD = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME</td><td>������addInfo�̕ϐ�shozokuName</td></tr>
	 * <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_EIGO</td><td>������addInfo�̕ϐ�shozokuNameEigo</td></tr>
	 * <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_RYAKU</td><td>������addInfo�̕ϐ�shozokuRyakusho</td></tr>
	 * </table><br/><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 * <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������addInfo�̕ϐ�shozokuCd</td></tr>
	 * </table><br/><br/>
	 * <b>��)�����@�֖����̏C���͐\���ҏ��݂̂̍X�V�Ƃ��A�R�����ʓ��̃e�[�u���͍X�V�ΏۂƂ��Ȃ�.</b>
	 * 
	 * @param userInfo	UserInfo
	 * @param addInfo	insert����ShozokuInfo
	 * @return �������Ɠ������
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public synchronized ShozokuInfo insert(UserInfo userInfo, ShozokuInfo addInfo)
		throws ApplicationException {
			
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();				

            //---------------------------------------
            //�����@�֒S���ҏ��f�[�^�A�N�Z�X�I�u�W�F�N�g
            //---------------------------------------
            ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);

			//---------------------------------------
			//�L�[���́i�����@�֒S����ID�j���쐬
			//�����@�փR�[�h�i5���j+�A�ԁi2���j+�`�F�b�N�f�W�b�g�i1���j�i���W�����X10�j
			//---------------------------------------
			try{
				//�����@�փR�[�h�i5���j+�A�ԁi2���j���쐬
				String key = addInfo.getShozokuCd() + dao.getSequenceNo(connection, addInfo.getShozokuCd());
				
				//�����@�֒S����ID���擾�i�`�F�b�N�f�W�b�g�̓A���t�@�x�b�g�`���j				
				String shozokuTantoId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
				addInfo.setShozokuTantoId(shozokuTantoId);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�V�[�P���X�ԍ��擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} catch (ApplicationException e) {
				//�����@�֒S����ID�̘A�Ԃ�09�𒴂����ꍇ
				throw e;
			}
						
			//---------------------------------------
			//�p�X���[�h���̍쐬
			//---------------------------------------
			try{
				//RULEINFO�e�[�u����胋�[���擾����
				RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
				RulePk rulePk = new RulePk();
				rulePk.setTaishoId(ITaishoId.SHOZOKUTANTO);

				//�p�X���[�h�擾
				String newPassword = rureInfoDao.getPassword(connection, rulePk);
				addInfo.setPassword(newPassword);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�����@�֒S���҃p�X���[�h�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			}

			//---------------------------------------
			//ID�o�^���t�̍쐬
			//---------------------------------------
			Date idDate = new Date();
			addInfo.setIdDate(idDate);
			
			//---------------------------------------
			//�����@�֒S���ҏ��̓o�^
			//---------------------------------------
			ShozokuInfo result = null;
			try{
				//�o�^
				dao.insertShozokuInfo(connection,addInfo);		
				//�o�^�f�[�^�̎擾
				result = dao.selectShozokuInfo(connection, addInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�����@�֊Ǘ��f�[�^�o�^����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			}
			
			//---------------------------------------
			//�\���ҏ��̍X�V
			//---------------------------------------
			try{
				ShinseishaInfoDao shinseishaDao = new ShinseishaInfoDao(userInfo);
				//�X�V
				shinseishaDao.updateShinseishaInfo(connection, addInfo);
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�\���ҏ��f�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			}
			
//			2005/04/21 �ǉ� ��������------------------------------------------
			
			//---------------------------------------
			//���ǒS���ҏ��̒ǉ�
			//---------------------------------------
			
			//���ǒS���Ґl������łȂ��Ƃ��A���ǒS���ҏ���ǉ�
			if(addInfo.getBukyokuNum() != null && ! addInfo.getBukyokuNum().equals("")){
				
				BukyokutantoInfoDao bukyokutantoDao = new BukyokutantoInfoDao(userInfo);
				BukyokutantoInfo bukyokuInfo;
				
				int seqNo = 0;
				try {
					//�A�Ԏ擾
					seqNo = Integer.parseInt(bukyokutantoDao.getSequenceNo(connection, addInfo.getShozokuCd()));
				} catch (DataAccessException e) {
					throw new ApplicationException(
							"�V�[�P���X�ԍ��擾����DB�G���[���������܂����B",
							new ErrorInfo("errors.4001"),
							e);
				}
				
				//�A�Ԃ�2���ɂ��邽��
				DecimalFormat df = new DecimalFormat("00");
				
				//���͂��ꂽ�l�����A�J��Ԃ�
				for(int i=0; i<Integer.parseInt(addInfo.getBukyokuNum()); i++){
					bukyokuInfo = new BukyokutantoInfo();
					
					//---------------------------------------
					//�L�[���́i���ǒS����ID�j���쐬
					//�����@�փR�[�h�i5���j+�A�ԁi2���j+�敪�i1���j�i�u1�v�Œ�j+�`�F�b�N�f�W�b�g�i1���j�i���W�����X10�j
					//---------------------------------------
					
					//�����@�փR�[�h�i5���j+�A�ԁi2���j���쐬
					//2005/07/14 �敪��B���Œ�Ƃ���
					//String key = addInfo.getShozokuCd() + df.format(seqNo+i) + "1";
					String key = addInfo.getShozokuCd() + df.format(seqNo+i) + "B";
					
					//���ǒS����ID���擾�i�`�F�b�N�f�W�b�g�̓A���t�@�x�b�g�`���j				
					String bukyokuTantoId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
					bukyokuInfo.setBukyokutantoId(bukyokuTantoId);
								
					//---------------------------------------
					//�p�X���[�h���̍쐬�i�f�t�H���g�p�X���[�h�j
					//---------------------------------------
					try{
						//RULEINFO�e�[�u����胋�[���擾����
						RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
						RulePk rulePk = new RulePk();
						rulePk.setTaishoId(ITaishoId.SHOZOKUTANTO);

						//�p�X���[�h�擾
						String defaultPassword = rureInfoDao.getPassword(connection, rulePk);

						//�f�B�t�H���g�p�X���[�h�ݒ�
						bukyokuInfo.setDefaultPassword(defaultPassword);

					} catch (DataAccessException e) {
						throw new ApplicationException(
							"���ǒS���҂̃p�X���[�h�쐬����DB�G���[���������܂����B",
							new ErrorInfo("errors.4001"),
							e);
					}
					
					//�����@�փR�[�h�̃Z�b�g
					bukyokuInfo.setShozokuCd(addInfo.getShozokuCd());
					
					try{
						//�o�^
						bukyokutantoDao.insertBukyokuData(connection,bukyokuInfo);
						
					} catch (DataAccessException e) {
						throw new ApplicationException(
							"���ǒS���҃f�[�^�o�^����DB�G���[���������܂����B",
							new ErrorInfo("errors.4001"),
							e);
					}
				}
			}
			
//			�ǉ� �����܂�-----------------------------------------------------
			
			//---------------------------------------
			//�o�^����I��
			//---------------------------------------
			success = true;			
			
			return result;	

		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�����@�֊Ǘ��f�[�^�o�^����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �����@�֒S���ҏ����X�V����B<br/><br/>
	 * 
	 * <b>1.�����@�֒S���ҏ��̍X�V</b><br/>
	 * �����@�֒S���҃e�[�u�����X�V����B<br/>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE SHOZOKUTANTOINFO SET
	 *     SHOZOKU_CD = ?				-- �����@�֖��i�R�[�h�j
	 *     ,SHOZOKU_NAME_KANJI = ?			-- �����@�֖��i���{��j
	 *     ,SHOZOKU_RYAKUSHO = ?			-- �����@�֖��i���́j
	 *     ,SHOZOKU_NAME_EIGO = ?			-- �����@�֖��i�p���j
	 *     ,SHUBETU_CD = ?				-- �@�֎��
	 *     ,PASSWORD = ?				-- �p�X���[�h
	 *     ,SEKININSHA_NAME_SEI = ?			-- �ӔC�Ҏ����i���j
	 *     ,SEKININSHA_NAME_MEI = ?			-- �ӔC�Ҏ����i���j
	 *     ,SEKININSHA_YAKU = ?			-- �ӔC�Җ�E
	 *     ,BUKYOKU_NAME = ?			-- �S�����ۖ�
	 *     ,KAKARI_NAME = ?				-- �S���W��
	 *     ,TANTO_NAME_SEI = ?			-- �S���Җ��i���j
	 *     ,TANTO_NAME_MEI = ?			-- �S���Җ��i���j
	 *     ,TANTO_TEL = ?				-- �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
	 *     ,TANTO_FAX = ?				-- �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
	 *     ,TANTO_EMAIL = ?				-- �S���ҕ��Ǐ��ݒn�iEmail�j
	 *     ,TANTO_EMAIL2 = ?			-- �S���ҕ��Ǐ��ݒn�iEmail2�j
	 *     ,TANTO_ZIP = ?				-- �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
	 *     ,TANTO_ADDRESS = ?			-- �S���ҕ��Ǐ��ݒn�i�Z���j
	 *     ,NINSHOKEY_FLG = ?			-- �F�؃L�[���s�t���O
	 *     ,BIKO = ?				-- ���l
	 *     ,YUKO_DATE = ?				-- �L������
	 *     ,A.ID_DATE = ?				-- ID���s���t
	 *     ,DEL_FLG = ?				-- �폜�t���O
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * �X�V�l
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i�R�[�h�j</td><td>������updateInfo�̕ϐ�shozokuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i���{��j</td><td>������updateInfo�̕ϐ�shozokuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i���́j</td><td>������updateInfo�̕ϐ�shozokuRyakusho</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i�p���j</td><td>������updateInfo�̕ϐ�shozokuNameEigo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�@�֎��</td><td>������updateInfo�̕ϐ�shubetuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�p�X���[�h</td><td>������updateInfo�̕ϐ�password</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�ӔC�Ҏ����i���j</td><td>������updateInfo�̕ϐ�sekininshaNameSei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�ӔC�Ҏ����i���j</td><td>������updateInfo�̕ϐ�sekininshaNameMei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�ӔC�Җ�E</td><td>������updateInfo�̕ϐ�sekininshaYaku</td></tr>
 	 *     <tr bgcolor="#FFFFFF"><td>�S�����ۖ�</td><td>������updateInfo�̕ϐ�bukyokuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���W��</td><td>������updateInfo�̕ϐ�kakariName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���Җ��i���j</td><td>������updateInfo�̕ϐ�tantoNameSei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���Җ��i���j</td><td>������updateInfo�̕ϐ�tantoNameMei</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j</td><td>������updateInfo�̕ϐ�tantoTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j</td><td>������updateInfo�̕ϐ�tantoFax</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�iEmail�j</td><td>������updateInfo�̕ϐ�tantoEmail</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�iEmail2�j</td><td>������updateInfo�̕ϐ�tantoEmail2</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j</td><td>������updateInfo�̕ϐ�tantoZip</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���ҕ��Ǐ��ݒn�i�Z���j</td><td>������updateInfo�̕ϐ�tantoAddress</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�F�؃L�[���s�t���O</td><td>������updateInfo�̕ϐ�ninshokeyFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���l</td><td>������updateInfo�̕ϐ�biko</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�L������</td><td>������updateInfo�̕ϐ�yukoDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>ID���s���t</td><td>������updateInfo�̕ϐ�idDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>0</td></tr>
	 * </table><br/><br/>
	 * �i���ݏ���
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֒S����ID</td><td>������updateInfo�̕ϐ�shozokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * 
	 * 
	 * <b>2.�\���ҏ��̍X�V</b><br/>
	 * �\���ҏ��e�[�u�����X�V����B<br/>
	 * �i�����@�֖������X�V����Ă���ꍇ�����邽�߁A�\���ҏ����X�V�������s���B�j<br/><br/>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE SHINSEISHAINFO SET
	 *     SHOZOKU_NAME = ?
	 *     ,SHOZOKU_NAME_EIGO = ?
	 *     ,SHOZOKU_NAME_RYAKU = ?
	 * WHERE
	 *     SHOZOKU_CD = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * �X�V�l
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME</td><td>������updateInfo�̕ϐ�shozokuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_EIGO</td><td>������addInfo�̕ϐ�shozokuNameEigo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_RYAKU</td><td>������updateInfo�̕ϐ�shozokuRyakusho</td></tr>
	 * </table><br/><br/>
	 * �i���ݏ���
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������updateInfo�̕ϐ�shozokuCd</td></tr>
	 * </table><br/><br/>
	 * <b>��)�����@�֖����̏C���͐\���ҏ��݂̂̍X�V�Ƃ��A�R�����ʓ��̃e�[�u���͍X�V�ΏۂƂ��Ȃ�.</b>
	 * 
	 * 
	 * @param userInfo		UserInfo
	 * @param updateInfo	�X�V���(ShozokuInfo)
	 * @return �Ȃ�
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public void update(UserInfo userInfo, ShozokuInfo updateInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//---------------------------------------
			//�����@�֒S���ҏ��̍X�V
			//---------------------------------------
			try{
				ShozokuInfoDao shozokuDao = new ShozokuInfoDao(userInfo);
				shozokuDao.updateShozokuInfo(connection, updateInfo);
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�����@�֏��f�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}

			//---------------------------------------
			//�\���ҏ��̍X�V
			//---------------------------------------
			try{
				ShinseishaInfoDao shinseishaDao = new ShinseishaInfoDao(userInfo);
				shinseishaDao.updateShinseishaInfo(connection, updateInfo);
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�\���ҏ��f�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}
			
//			2005/04/21 �ǉ� ��������------------------------------------------

			//---------------------------------------
			//���ǒS���ҏ��̒ǉ��o�^
			//---------------------------------------
			if(updateInfo.getAddBukyokuNum() > 0){
				BukyokutantoInfoDao bukyokutantoDao = new BukyokutantoInfoDao(userInfo);
				BukyokutantoInfo bukyokuInfo;
				
				int seqNo = 0;
				try {
					//�A�Ԏ擾
					seqNo = Integer.parseInt(bukyokutantoDao.getSequenceNo(connection, updateInfo.getShozokuCd()));
				} catch (DataAccessException e) {
					throw new ApplicationException(
							"�V�[�P���X�ԍ��擾����DB�G���[���������܂����B",
							new ErrorInfo("errors.4001"),
							e);
				}
				
				//�A�Ԃ�2���ɂ��邽��
				DecimalFormat df = new DecimalFormat("00");
				
				//���ǒS���Ґl���̑��������A�J��Ԃ�
				for(int i=0; i<updateInfo.getAddBukyokuNum(); i++){
					bukyokuInfo = new BukyokutantoInfo();
					
					//---------------------------------------
					//�L�[���́i���ǒS����ID�j���쐬
					//�����@�փR�[�h�i5���j+�A�ԁi2���j+�敪�i1���j�i�u1�v�Œ�j+�`�F�b�N�f�W�b�g�i1���j�i���W�����X10�j
					//---------------------------------------
					
					//�A�Ԃ�99�𒴂�����G���[�Ƃ��� 2005/07/14
					if (seqNo+i > 99){
						throw new ApplicationException(
								"���ǒS����ID�̘A�Ԃ�99�𒴂��܂����B",
								new ErrorInfo("errors.4001"));
					}
					
					//�����@�փR�[�h�i5���j+�A�ԁi2���j���쐬
					//�敪��B���Œ�Ƃ��� 2005/07/14
					//String key = updateInfo.getShozokuCd() + df.format(seqNo+i) + "1";
					String key = updateInfo.getShozokuCd() + df.format(seqNo+i) + "B";
					
					//���ǒS����ID���擾�i�`�F�b�N�f�W�b�g�̓A���t�@�x�b�g�`���j				
					String bukyokuTantoId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
					bukyokuInfo.setBukyokutantoId(bukyokuTantoId);
								
					//---------------------------------------
					//�p�X���[�h���̍쐬�i�f�t�H���g�p�X���[�h�j
					//---------------------------------------
					try{
						//RULEINFO�e�[�u����胋�[���擾����
						RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
						RulePk rulePk = new RulePk();
						rulePk.setTaishoId(ITaishoId.SHOZOKUTANTO);
						//�p�X���[�h�擾
						String defaultPassword = rureInfoDao.getPassword(connection, rulePk);
						bukyokuInfo.setDefaultPassword(defaultPassword);
					} catch (DataAccessException e) {
						throw new ApplicationException(
							"���ǒS���҂̃p�X���[�h�쐬����DB�G���[���������܂����B",
							new ErrorInfo("errors.4001"),
							e);
					}
						
					//�����@�փR�[�h�̃Z�b�g
					bukyokuInfo.setShozokuCd(updateInfo.getShozokuCd());
					
					try{
						//�o�^
						bukyokutantoDao.insertBukyokuData(connection,bukyokuInfo);
					
						} catch (DataAccessException e) {
						throw new ApplicationException(
							"���ǒS���҃f�[�^�o�^����DB�G���[���������܂����B",
							new ErrorInfo("errors.4001"),
							e);
					}
				}
			}
			
//			�ǉ� �����܂�-----------------------------------------------------
			
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
					"�����@�֊Ǘ��f�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �����@�֒S���ҏ����폜����B<br/><br/>
	 *  
	 * �ȉ���SQL�����s���ASHOZOKUTANTOINFO����A�ΏۂƂȂ郌�R�[�h��_���폜����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * 
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE SHOZOKUTANTOINFO SET
	 *     DEL_FLG = 1			-- �폜�t���O
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>deleteInfo</td><td>������addInfo�̕ϐ�shozokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param deleteInfo	�폜���(ShozokuInfo)
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public void delete(UserInfo userInfo, ShozokuInfo deleteInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();			
			//---------------------------------------
			//�����@�֒S���ҏ��̍X�V
			//---------------------------------------
			ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
			dao.deleteFlgShozokuInfo(connection, deleteInfo);
			
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^�폜����DB�G���[���������܂����B",
				new ErrorInfo("errors.4003"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�����@�֊Ǘ��f�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �����@�֒S���ҏ����擾����B<br/><br/>
	 * 
	 * �����@�֒S���҃e�[�u������A�Y�����R�[�h���擾����B<br/><br/>
	 * �ȉ���SQL�����s���A�擾��������ԋp����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * �������A�Y�����郌�R�[�h���Ȃ��ꍇ�͗�O��throw����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- �����@�֒S����ID
	 *     ,A.SHOZOKU_CD"				-- �����@�֖��i�R�[�h�j
	 *     ,A.SHOZOKU_NAME_KANJI			-- �����@�֖��i���{��j
	 *     ,A.SHOZOKU_RYAKUSHO			-- �����@�֖��i���́j
	 *     ,A.SHOZOKU_NAME_EIGO			-- �����@�֖��i�p���j
	 *     ,A.SHUBETU_CD				-- �@�֎��
	 *     ,A.PASSWORD				-- �p�X���[�h
	 *     ,A.SEKININSHA_NAME_SEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_NAME_MEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_YAKU			-- �ӔC�Җ�E
	 *     ,A.BUKYOKU_NAME				-- �S�����ۖ�
	 *     ,A.KAKARI_NAME				-- �S���W��
	 *     ,A.TANTO_NAME_SEI			-- �S���Җ��i���j
	 *     ,A.TANTO_NAME_MEI			-- �S���Җ��i���j
	 *     ,A.TANTO_TEL				-- �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
	 *     ,A.TANTO_FAX				-- �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
	 *     ,A.TANTO_EMAIL				-- �S���ҕ��Ǐ��ݒn�iEmail�j
	 *     ,A.TANTO_EMAIL2				-- �S���ҕ��Ǐ��ݒn�iEmail2�j
	 *     ,A.TANTO_ZIP				-- �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
	 *     ,A.TANTO_ADDRESS				-- �S���ҕ��Ǐ��ݒn�i�Z���j
	 *     ,A.NINSHOKEY_FLG				-- �F�؃L�[���s�t���O
	 *     ,A.BIKO				-- ���l
	 *     ,A.YUKO_DATE				-- �L������
	 *     ,A.ID_DATE				-- ID���s���t
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKUTANTO_ID</td><td>������pkInfo�̕ϐ�shozokuTantoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo	UserInfo
	 * @param pkInfo	�������s�������@�֏���PK���iShozokuPk�j
	 * @return �����@�֒S���ҏ��
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public ShozokuInfo select(UserInfo userInfo, ShozokuPk pkInfo)
		throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
			return dao.selectShozokuInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * �����@�փ}�X�^�����擾����B<br/><br/>
	 * 
	 * �����@�փ}�X�^�e�[�u������A�Y�����R�[�h���擾��KikanInfo�֊i�[��ԋp����B<br/><br/>
	 * �ȉ���SQL�����s���A�擾��������ԋp����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * �������A���R�[�h�̎擾���o���Ȃ������ꍇ�͗�O��throw����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     *
	 * FROM
	 *     MASTER_KIKAN
	 * WHERE
	 *     SHOZOKU_CD = ?
	 * </pre>
	 * </td></tr>
	 * </table><br/><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������pkInfo�̕ϐ�shozokuCd</td></tr>
	 * </table><br/>
	 * �擾������
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>KikanInfo�̊i�[��ϐ���</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHUBETU_CD</td><td>shubetuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KIKAN_KUBUN</td><td>kikanKubun</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>shozokuCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_KANJI</td><td>shozokuNameKanji</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_RYAKUSHO</td><td>shozokuRyakusho</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_EIGO</td><td>shozokuNameEigo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_ZIP</td><td>shozokuZip</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_ADDRESS1</td><td>shozokuAddress1</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_ADDRESS2</td><td>shozokuAddress2</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_TEL</td><td>shozokuTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_FAX</td><td>shozokuFax</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_DAIHYO_NAME</td><td>shozokuDaihyoName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BIKO</td><td>biko</td></tr>
	 * </table><br/>
	 * 
	 * <br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param kikanInfo		���̎擾�������Ȃ������@�֏��iKikanInfo�j
	 * @return �����@�փ}�X�^���
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#selectMaster(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShozokuPk)
	 */
	public KikanInfo select(UserInfo userInfo, KikanInfo kikanInfo)
		 throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return new MasterKikanInfoDao(userInfo).selectKikanInfo(connection,kikanInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			 DatabaseUtil.closeConnection(connection);
	 	}
	}

	/**
	 * �y�[�W�����擾����B<br/><br/>
	 * 
	 * �������œn���ꂽ���������Ɋ�Â��A�����@�֒S���҃e�[�u������������B<br/><br/>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * �������ʂ�Page�I�u�W�F�N�g�Ɋi�[���A�ԋp����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- �����@�֒S����ID
	 *     ,A.SHOZOKU_CD"				-- �����@�֖��i�R�[�h�j
	 *     ,A.SHOZOKU_NAME_KANJI			-- �����@�֖��i���{��j
	 *     ,A.SHOZOKU_RYAKUSHO			-- �����@�֖��i���́j
	 *     ,A.SHOZOKU_NAME_EIGO			-- �����@�֖��i�p���j
	 *     ,A.SHUBETU_CD				-- �@�֎��
	 *     ,A.PASSWORD				-- �p�X���[�h
	 *     ,A.SEKININSHA_NAME_SEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_NAME_MEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_YAKU			-- �ӔC�Җ�E
	 *     ,A.BUKYOKU_NAME				-- �S�����ۖ�
	 *     ,A.KAKARI_NAME				-- �S���W��
	 *     ,A.TANTO_NAME_SEI			-- �S���Җ��i���j
	 *     ,A.TANTO_NAME_MEI			-- �S���Җ��i���j
	 *     ,A.TANTO_TEL				-- �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
	 *     ,A.TANTO_FAX				-- �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
	 *     ,A.TANTO_EMAIL				-- �S���ҕ��Ǐ��ݒn�iEmail�j
	 *     ,A.TANTO_EMAIL2				-- �S���ҕ��Ǐ��ݒn�iEmail2�j
	 *     ,A.TANTO_ZIP				-- �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
	 *     ,A.TANTO_ADDRESS				-- �S���ҕ��Ǐ��ݒn�i�Z���j
	 *     ,A.NINSHOKEY_FLG				-- �F�؃L�[���s�t���O
	 *     ,A.BIKO				-- ���l
	 *     ,A.YUKO_DATE				-- �L������
	 *     ,A.ID_DATE				-- ID���s���t
	 *     ,BUKYOKU_NUM				-- ���ǒS���Ґl��
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     DEL_FLG = 0
	 *	
	 *				<b><span style="color:#002288">�|�|���I���������|�|</span></b>
	 * 
	 * ORDER BY SHOZOKU_CD
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�@�֎�ʃR�[�h</td><td>ShubetuCd</td><td>AND SHUBETU_CD = '�@�֎�ʃR�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i�R�[�h�j</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '�����@�֖��i�R�[�h�j'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i���{��j</td><td>ShozokuNameKanji</td><td>AND SHOZOKU_NAME_KANJI LIKE '%�����@�֖��i���{��j'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S����ID</td><td>ShozokuTantoId</td><td>AND SHOZOKUTANTO_ID = '�S����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���Җ��i���j</td><td>TantoNameSei</td><td>AND TANTO_NAME_SEI LIKE '%�S���Җ��i���j'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���Җ��i���j</td><td>TantoNameMei</td><td>AND TANTO_NAME_MEI LIKE '%�S���Җ��i���j'</td></tr>
	 * </table><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	���������iShozokuSearchInfo�j
	 * @return �����@�֒S���ҏ����i�[����Page�I�u�W�F�N�g
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuSearchInfo)
	 */
	public Page search(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException {

		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		
		String select = 
			"SELECT *  FROM SHOZOKUTANTOINFO A WHERE DEL_FLG = 0";	
			
		StringBuffer query = new StringBuffer(select);
		
		if(searchInfo.getShubetuCd() != null && !searchInfo.getShubetuCd().equals("")){			//�@�֎�ʃR�[�h�i���S��v�j
			query.append(" AND SHUBETU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShubetuCd()) + "'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//�����@�֖��i�R�[�h�j�i���S��v�j
			query.append(" AND SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){		//�����@�֖��i���{��j�i������v�j
			query.append(" AND SHOZOKU_NAME_KANJI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");			
		}
		if(searchInfo.getShozokuTantoId() != null && !searchInfo.getShozokuTantoId().equals("")){	//�S����ID�i���S��v�j
			query.append(" AND SHOZOKUTANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getShozokuTantoId()) + "'");			
		}
		if(searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals("")){	//�S���Җ��i���j�i������v�j
			query.append(" AND TANTO_NAME_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameSei()) + "%'");			
		}
		if(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals("")){	//�S���Җ��i���j�i������v�j
			query.append(" AND TANTO_NAME_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameMei()) + "%'");			
		}	
		//�\�[�g���i�����@�֖��i�R�[�h�j�̏����j
		query.append(" ORDER BY SHOZOKU_CD");		
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		//------------------------
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
		} catch (DataAccessException e) {
			log.error("�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

	}
	
	/**
	 * CSV�o�̓f�[�^�쐬.<br />
	 * 
	 * �����@�֒S���ҏ���CSV�o�͂��邽�߂ɁA���������ɊY�����郌�R�[�h��List�֊i�[���A�Ăяo�����֕ԋp����B
	 * ���̍ہA�e���R�[�h���͗񂲂Ƃ�List�֊i�[���ꂽ�����ŕԋp����List�֊i�[�����B(List�ɂ��2�����z��\��)
	 * �Ȃ��A�ԋp����List�̈�ڂ̗v�f�́A�w�b�_�[��񂪊i�[����Ă���B
	 * <br />
	 * <br />
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID "�����@�֒S����ID"			-- �����@�֒S����ID
	 *     ,A.SHOZOKU_CD "�����@�֖��i�R�[�h�j"			-- �����@�֖��i�R�[�h�j
	 *     ,A.SHUBETU_CD "�@�֎�ʃR�[�h"			-- �@�֎�ʃR�[�h
	 *     ,A.SHOZOKU_NAME_KANJI "�����@�֖��i�a���j"		-- �����@�֖��i�a���j
	 *     ,A.SHOZOKU_RYAKUSHO "�����@�֖��i���́j"		-- �����@�֖��i���́j
	 *     ,A.SHOZOKU_NAME_EIGO "�����@�֖��i�p���j"		-- �����@�֖��i�p���j
	 *     ,A.SEKININSHA_NAME_SEI "�ӔC�Җ��i���j"		-- �ӔC�Җ��i���j
	 *     ,A.SEKININSHA_NAME_MEI "�ӔC�Җ��i���j"		-- �ӔC�Җ��i���j
	 *     ,A.SEKININSHA_YAKU "�ӔC�Җ�E"			-- �ӔC�Җ�E
	 *     ,A.BUKYOKU_NAME "�S�����ۖ�"			-- �S�����ۖ�
	 *     ,A.KAKARI_NAME "�S���ҌW��"			-- �S���ҌW��
	 *     ,A.TANTO_NAME_SEI "�S���Җ��i���j"			-- �S���Җ��i���j
	 *     ,A.TANTO_NAME_MEI "�S���Җ��i���j"			-- �S���Җ��i���j
	 *     ,A.TANTO_TEL "�S���ҘA����i�d�b�ԍ��j"		-- �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
	 *     ,A.TANTO_FAX "�S���ҘA����iFAX�ԍ��j"		-- �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
	 *     ,A.TANTO_EMAIL "�S���ҘA����iEmail�j"		-- �S���ҕ��Ǐ��ݒn�iEmail�j
	 *     ,A.TANTO_EMAIL2 "�S���ҘA����iEmail2�j"		-- �S���ҕ��Ǐ��ݒn�iEmail2�j
	 *     ,A.TANTO_ZIP "�S���ҘA����i�X�֔ԍ��j"		-- �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
	 *     ,A.TANTO_ADDRESS "�S���ҘA����i�Z���j"		-- �S���ҕ��Ǐ��ݒn�i�Z���j
	 *     ,A.BIKO "���l"					-- ���l
	 *     ,TO_CHAR(A.YUKO_DATE,'YYYY/MM/DD') "�L������"		-- �L������
	 *     ,TO_CHAR(A.ID_DATE,'YYYY/MM/DD') "ID���s���t"		-- ID���s���t
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * 
	 * WHERE
	 *     DEL_FLG = 0
	 *	
	 *				<b><span style="color:#002288">�|�|���I���������|�|</span></b>
	 * 
	 * ORDER BY SHOZOKU_CD
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�@�֎�ʃR�[�h</td><td>ShubetuCd</td><td>AND SHUBETU_CD = '�@�֎�ʃR�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i�R�[�h�j</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '�����@�֖��i�R�[�h�j'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i���{��j</td><td>ShozokuNameKanji</td><td>AND SHOZOKU_NAME_KANJI LIKE '%�����@�֖��i���{��j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S����ID</td><td>ShozokuTantoId</td><td>AND SHOZOKUTANTO_ID = '�S����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���Җ��i���j</td><td>TantoNameSei</td><td>AND TANTO_NAME_SEI LIKE '%�S���Җ��i���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���Җ��i���j</td><td>TantoNameMei</td><td>AND TANTO_NAME_MEI LIKE '%�S���Җ��i���j%'</td></tr>
	 * </table><br/>
	 * 
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#search(jp.go.jsps.kaken.mode
	 * l.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuSearchInfo)
	 */
	public List searchCsvData(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		//2005.10.04 iso �\��������A�����@�ց����������@�ցA�R�[�h���ԍ��A�\�����������v�撲��
		String select =
			"SELECT "
				+ " A.SHOZOKUTANTO_ID \"���������@�֒S����ID\""			//�����@�֒S����ID
				+ ",A.SHOZOKU_CD \"���������@�֖��i�ԍ��j\""				//�����@�֖��i�R�[�h�j
				+ ",A.SHUBETU_CD \"���������@�֎�ʔԍ�\""				//�@�֎�ʃR�[�h
				+ ",A.SHOZOKU_NAME_KANJI \"���������@�֖��i�a���j\""		//�����@�֖��i�a���j
				+ ",A.SHOZOKU_RYAKUSHO \"���������@�֖��i���́j\""		//�����@�֖��i���́j
				+ ",A.SHOZOKU_NAME_EIGO \"���������@�֖��i�p���j\""		//�����@�֖��i�p���j
				+ ",A.SEKININSHA_NAME_SEI \"�ӔC�Җ��i���j\""			//�ӔC�Җ��i���j
				+ ",A.SEKININSHA_NAME_MEI \"�ӔC�Җ��i���j\""			//�ӔC�Җ��i���j
				+ ",A.SEKININSHA_YAKU \"�ӔC�Җ�E\""					//�ӔC�Җ�E
				+ ",A.BUKYOKU_NAME \"�S�����ۖ�\""						//�S�����ۖ�
				+ ",A.KAKARI_NAME \"�S���ҌW��\""						//�S���ҌW��
				+ ",A.TANTO_NAME_SEI \"�S���Җ��i���j\""					//�S���Җ��i���j
				+ ",A.TANTO_NAME_MEI \"�S���Җ��i���j\""					//�S���Җ��i���j
				+ ",A.TANTO_TEL \"�S���ҘA����i�d�b�ԍ��j\""				//�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
				+ ",A.TANTO_FAX \"�S���ҘA����iFAX�ԍ��j\""				//�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
				+ ",A.TANTO_EMAIL \"�S���ҘA����iEmail�j\""				//�S���ҕ��Ǐ��ݒn�iEmail�j
				+ ",A.TANTO_EMAIL2 \"�S���ҘA����iEmail2�j\""			//�S���ҕ��Ǐ��ݒn�iEmail2�j
				+ ",A.TANTO_ZIP \"�S���ҘA����i�X�֔ԍ��j\""				//�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
				+ ",A.TANTO_ADDRESS \"�S���ҘA����i�Z���j\""				//�S���ҕ��Ǐ��ݒn�i�Z���j
				+ ",A.BIKO \"���l\""										//���l
//				2005/04/22 �ǉ� ��������----------
//				���R:���ǒS���Ґl���ǉ��̂���
				+ ",A.BUKYOKU_NUM \"���ǒS���Ґl��\""					//���ǒS���Ґl��
//				2005/04/22 �ǉ� �����܂�----------
				+ ",TO_CHAR(A.YUKO_DATE,'YYYY/MM/DD') \"�L������\""		//�L������
				//2005.08.10 iso ID���s���t�̒ǉ�
				+ ",TO_CHAR(A.ID_DATE,'YYYY/MM/DD') \"ID���s���t\""		//ID���s���t
				+ " FROM SHOZOKUTANTOINFO A"
				+ " WHERE DEL_FLG = 0";																		

		StringBuffer query = new StringBuffer(select);

		if(searchInfo.getShubetuCd() != null && !searchInfo.getShubetuCd().equals("")){			//�@�֎�ʃR�[�h�i���S��v�j
			query.append(" AND SHUBETU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShubetuCd()) + "'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//�����@�֖��i�R�[�h�j�i���S��v�j
			query.append(" AND SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){		//�����@�֖��i���{��j�i������v�j
			query.append(" AND SHOZOKU_NAME_KANJI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");			
		}
		if(searchInfo.getShozokuTantoId() != null && !searchInfo.getShozokuTantoId().equals("")){	//�S����ID�i���S��v�j
			query.append(" AND SHOZOKUTANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getShozokuTantoId()) + "'");		
		}
		if(searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals("")){	//�S���Җ��i���j�i������v�j
			query.append(" AND TANTO_NAME_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameSei()) + "%'");			
		}
		if(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals("")){	//�S���Җ��i���j�i������v�j
			query.append(" AND TANTO_NAME_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameMei()) + "%'");			
		}

		//�\�[�g���i�����@�֖��i�R�[�h�j�̏����j
		query.append(" ORDER BY SHOZOKU_CD");

		if(log.isDebugEnabled()){
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
	
	//2005/04/26 �ǉ� -------------------------------------------------------------------------------------------��������
	//���R �V�X�e���Ǘ��҂̏����@�֒S���҈ꗗ�͕��ǒS���҂̏��������ɏo�͂���K�v�����邽��
	/**
	 * CSV�o�̓f�[�^�쐬.<br />
	 * 
	 * �����@�֒S���ҏ���CSV�o�͂��邽�߂ɁA���������ɊY�����郌�R�[�h��List�֊i�[���A�Ăяo�����֕ԋp����B
	 * ���̍ہA�e���R�[�h���͗񂲂Ƃ�List�֊i�[���ꂽ�����ŕԋp����List�֊i�[�����B(List�ɂ��2�����z��\��)
	 * �Ȃ��A�ԋp����List�̈�ڂ̗v�f�́A�w�b�_�[��񂪊i�[����Ă���B
	 * <br />
	 * <br />
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID "�����@�֒S����ID"			-- �����@�֒S����ID
	 *     ,A.SHOZOKU_CD "�����@�֖��i�R�[�h�j"			-- �����@�֖��i�R�[�h�j
	 *     ,A.SHUBETU_CD "�@�֎�ʃR�[�h"			-- �@�֎�ʃR�[�h
	 *     ,A.SHOZOKU_NAME_KANJI "�����@�֖��i�a���j"		-- �����@�֖��i�a���j
	 *     ,A.SHOZOKU_RYAKUSHO "�����@�֖��i���́j"		-- �����@�֖��i���́j
	 *     ,A.SHOZOKU_NAME_EIGO "�����@�֖��i�p���j"		-- �����@�֖��i�p���j
	 *     ,A.SEKININSHA_NAME_SEI "�ӔC�Җ��i���j"		-- �ӔC�Җ��i���j
	 *     ,A.SEKININSHA_NAME_MEI "�ӔC�Җ��i���j"		-- �ӔC�Җ��i���j
	 *     ,A.SEKININSHA_YAKU "�ӔC�Җ�E"			-- �ӔC�Җ�E
	 *     ,A.BUKYOKU_NAME "�S�����ۖ�"			-- �S�����ۖ�
	 *     ,A.KAKARI_NAME "�S���ҌW��"			-- �S���ҌW��
	 *     ,A.TANTO_NAME_SEI "�S���Җ��i���j"			-- �S���Җ��i���j
	 *     ,A.TANTO_NAME_MEI "�S���Җ��i���j"			-- �S���Җ��i���j
	 *     ,A.TANTO_TEL "�S���ҘA����i�d�b�ԍ��j"		-- �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
	 *     ,A.TANTO_FAX "�S���ҘA����iFAX�ԍ��j"		-- �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
	 *     ,A.TANTO_EMAIL "�S���ҘA����iEmail�j"		-- �S���ҕ��Ǐ��ݒn�iEmail�j
	 *     ,A.TANTO_EMAIL2 "�S���ҘA����iEmail2�j"		-- �S���ҕ��Ǐ��ݒn�iEmail2�j
	 *     ,A.TANTO_ZIP "�S���ҘA����i�X�֔ԍ��j"		-- �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
	 *     ,A.TANTO_ADDRESS "�S���ҘA����i�Z���j"		-- �S���ҕ��Ǐ��ݒn�i�Z���j
	 *     ,A.BIKO "���l"					-- ���l
	 *     ,TO_CHAR(A.YUKO_DATE,'YYYY/MM/DD') "�L������"		-- �L������
	 *     ,TO_CHAR(A.ID_DATE,'YYYY/MM/DD') "ID���s���t"		-- ID���s���t
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * 
	 * WHERE
	 *     DEL_FLG = 0
	 *	
	 *				<b><span style="color:#002288">�|�|���I���������|�|</span></b>
	 * 
	 * ORDER BY SHOZOKU_CD
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�@�֎�ʃR�[�h</td><td>ShubetuCd</td><td>AND SHUBETU_CD = '�@�֎�ʃR�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i�R�[�h�j</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '�����@�֖��i�R�[�h�j'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖��i���{��j</td><td>ShozokuNameKanji</td><td>AND SHOZOKU_NAME_KANJI LIKE '%�����@�֖��i���{��j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S����ID</td><td>ShozokuTantoId</td><td>AND SHOZOKUTANTO_ID = '�S����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���Җ��i���j</td><td>TantoNameSei</td><td>AND TANTO_NAME_SEI LIKE '%�S���Җ��i���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S���Җ��i���j</td><td>TantoNameMei</td><td>AND TANTO_NAME_MEI LIKE '%�S���Җ��i���j%'</td></tr>
	 * </table><br/>
	 * 
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#search(jp.go.jsps.kaken.mode
	 * l.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuSearchInfo)
	 */
	public List searchCsvDataForSysMng(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT "
			+ "	 INFO.SHOZOKUTANTO_ID \"���������@�֒S����ID\" "			//�����@�֒S����ID
			+ "	,INFO.SHOZOKU_CD \"���������@�֖��i�ԍ��j\" "				//�����@�֖��i�R�[�h�j
			+ "	,INFO.SHUBETU_CD \"���������@�֎�ʃR�[�h\" "				//�@�֎�ʃR�[�h
			+ "	,INFO.SHOZOKU_NAME_KANJI \"���������@�֖��i�a���j\" "		//�����@�֖��i�a���j
			+ "	,INFO.SHOZOKU_RYAKUSHO \"���������@�֖��i���́j\" "		//�����@�֖��i���́j
			+ "	,INFO.SHOZOKU_NAME_EIGO \"���������@�֖��i�p���j\" "		//�����@�֖��i�p���j
			+ "	,INFO.SEKININSHA_NAME_SEI \"�ӔC�Җ��i���j\" "			//�ӔC�Җ��i���j
			+ "	,INFO.SEKININSHA_NAME_MEI \"�ӔC�Җ��i���j\" "			//�ӔC�Җ��i���j
			+ "	,INFO.SEKININSHA_YAKU \"�ӔC�Җ�E\" "					//�ӔC�Җ�E
			+ "	,INFO.BUKYOKU_NAME \"�S�����ۖ�\" "						//�S�����ۖ�
			+ "	,INFO.KAKARI_NAME \"�S���ҌW��\" "						//�S���ҌW��
			+ "	,INFO.TANTO_NAME_SEI \"�S���Җ��i���j\" "				//�S���Җ��i���j
			+ "	,INFO.TANTO_NAME_MEI \"�S���Җ��i���j\" "				//�S���Җ��i���j
			+ "	,INFO.TANTO_TEL \"�S���ҘA����i�d�b�ԍ��j\" "			//�S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
			+ "	,INFO.TANTO_FAX \"�S���ҘA����iFAX�ԍ��j\" "				//�S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
			+ "	,INFO.TANTO_EMAIL \"�S���ҘA����iEmail�j\" "				//�S���ҕ��Ǐ��ݒn�iEmail�j
			+ "	,INFO.TANTO_EMAIL2 \"�S���ҘA����iEmail2�j\" "			//�S���ҕ��Ǐ��ݒn�iEmail2�j
			+ "	,INFO.TANTO_ZIP \"�S���ҘA����i�X�֔ԍ��j\" "			//�S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
			+ "	,INFO.TANTO_ADDRESS \"�S���ҘA����i�Z���j\" "			//�S���ҕ��Ǐ��ݒn�i�Z���j
			+ "	,INFO.BIKO \"���l\" "									//���l
			+ "	,INFO.BUKYOKU_NUM \"���ǒS���Ґl��\" "					//���ǒS���Ґl��
			+ "	,INFO.YUKO_DATE \"�L������\" "							//�L������
			+ "	,INFO.ID_DATE \"ID���s���t\" "							//ID���s���t
			+ " ,INFO.DEL_FLG \"�폜�t���O\" "							//�폜�t���O
			+ "FROM( "
			+ "	SELECT "
			+ "		 S.SHOZOKUTANTO_ID "
			+ "		,S.SHOZOKU_CD "
			+ "		,S.SHUBETU_CD "
			+ "		,S.SHOZOKU_NAME_KANJI "
			+ "		,S.SHOZOKU_RYAKUSHO "
			+ "		,S.SHOZOKU_NAME_EIGO "
			+ "		,S.SEKININSHA_NAME_SEI "
			+ "		,S.SEKININSHA_NAME_MEI "
			+ "		,S.SEKININSHA_YAKU "
			+ "		,S.BUKYOKU_NAME "
			+ "		,S.KAKARI_NAME "
			+ "		,S.TANTO_NAME_SEI "
			+ "		,S.TANTO_NAME_MEI "
			+ "		,S.TANTO_TEL "
			+ "		,S.TANTO_FAX "
			+ "		,S.TANTO_EMAIL "
			+ "		,S.TANTO_EMAIL2 "
			+ "		,S.TANTO_ZIP "
			+ "		,S.TANTO_ADDRESS "
			+ "		,S.BIKO "
			+ "		,S.BUKYOKU_NUM "
			+ "		,TO_CHAR(S.YUKO_DATE,'YYYY/MM/DD') YUKO_DATE "
			//2005.08.10 iso ID���s���t�̒ǉ�
			+ "		,TO_CHAR(S.ID_DATE,'YYYY/MM/DD') ID_DATE "
			+ "		,S.DEL_FLG "
			+ "	FROM "
			+ "		SHOZOKUTANTOINFO S "
			+ "	WHERE "
			+ "		S.DEL_FLG = 0 ";

		StringBuffer query = new StringBuffer(select);

		if(searchInfo.getShubetuCd() != null && !searchInfo.getShubetuCd().equals("")){			//�@�֎�ʃR�[�h�i���S��v�j
			query.append(" AND S.SHUBETU_CD = " + EscapeUtil.toSqlString(searchInfo.getShubetuCd()) );
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//�����@�֖��i�R�[�h�j�i���S��v�j
			query.append(" AND S.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){		//�����@�֖��i���{��j�i������v�j
			query.append(" AND S.SHOZOKU_NAME_KANJI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");			
		}
		if(searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals("")){	//�S���Җ��i���j�i������v�j
			query.append(" AND S.TANTO_NAME_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameSei()) + "%'");			
		}
		if(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals("")){	//�S���Җ��i���j�i������v�j
			query.append(" AND S.TANTO_NAME_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getTantoNameMei()) + "%'");			
		}

		select =
			"	UNION "
			+ "	SELECT "
			+ "		 B.BUKYOKUTANTO_ID "
			+ "		,B.SHOZOKU_CD "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,B.BUKA_NAME "
			+ "		,B.KAKARI_NAME "
			+ "		,B.TANTO_NAME_SEI "
			+ "		,B.TANTO_NAME_MEI "
			+ "		,B.BUKYOKU_TEL "
			+ "		,B.BUKYOKU_FAX "
			+ "		,B.BUKYOKU_EMAIL "
			+ "		,NULL "
			+ "		,B.BUKYOKU_ZIP "
			+ "		,B.BUKYOKU_ADDRESS "
			+ "		,NULL "
			+ "		,NULL "
			+ "		,NULL "
			//2005.08.10 iso ID���s���t�̒ǉ�
			+ "		,NULL "
			+ "		,B.DEL_FLG "
			+ "	FROM "
			+ "		BUKYOKUTANTOINFO B "
			+ "			INNER JOIN SHOZOKUTANTOINFO S "
			+ "			ON S.SHOZOKU_CD = B.SHOZOKU_CD "
			+ "			AND S.DEL_FLG = 0 "
			+ "	WHERE "
			+ "		B.REGIST_FLG = 1 ";
		
		query.append(select);
		
		if(searchInfo.getShubetuCd() != null && !searchInfo.getShubetuCd().equals("")){			//�@�֎�ʃR�[�h�i���S��v�j
			query.append(" AND S.SHUBETU_CD = " + EscapeUtil.toSqlString(searchInfo.getShubetuCd()) );
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//�����@�֖��i�R�[�h�j�i���S��v�j
			query.append(" AND S.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){		//�����@�֖��i���{��j�i������v�j
			query.append(" AND S.SHOZOKU_NAME_KANJI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");			
		}
		if((searchInfo.getTantoNameSei() != null && !(searchInfo.getTantoNameSei()).equals(""))
			||(searchInfo.getTantoNameMei() != null && !(searchInfo.getTantoNameMei()).equals(""))){	//�S���Җ��i���j�A�S���Җ��i���j
			//���ǒS���҂̏�񂪌����ɂ���đI������Ȃ��悤�ɂ���
			query.append(" AND B.BUKYOKUTANTO_ID = NULL ");
		}
		
		select =
			"  ) INFO "
			+ "WHERE "
			+ "	INFO.SHOZOKU_CD IS NOT NULL ";

		query.append(select);
		
		if(searchInfo.getBukyokuSearchFlg() != null && searchInfo.getBukyokuSearchFlg().equals("1")){
			query.append(" AND INFO.DEL_FLG = 1");
		}
		if(searchInfo.getBukyokuTantoId() != null && !(searchInfo.getBukyokuTantoId()).equals("")){	//���ǒS����ID�i���S��v�j
			query.append(" AND INFO.SHOZOKUTANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getBukyokuTantoId()) + "'");
		}
		if(searchInfo.getShozokuTantoId() != null && !searchInfo.getShozokuTantoId().equals("")){	//�S����ID�i���S��v�j
			query.append(" AND INFO.SHOZOKUTANTO_ID = '" + EscapeUtil.toSqlString(searchInfo.getShozokuTantoId()) + "'");			
		}

		//�\�[�g���i�����@�֖��i�R�[�h�j�̏����j
		query.append(" ORDER BY INFO.SHOZOKU_CD,INFO.SHUBETU_CD,INFO.SHOZOKUTANTO_ID");

		if(log.isDebugEnabled()){
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
	//2005/04/26 �ǉ� -------------------------------------------------------------------------------------------�����܂�

	/**
	 * �����@�֒S���ҏ��̐����擾����B<br/><br/>
	 * 
	 * �����@�֒S���҃e�[�u������A�w�肳�ꂽ�����@�փR�[�h�̃��R�[�h�������擾����B<br/><br/>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     COUNT(*)
	 * FROM
	 *     SHOZOKUTANTOINFO
	 * WHERE
	 *     SHOZOKU_CD = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������shozokuCd</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param shozokuCd		�����@�փR�[�h
	 * @return ���R�[�h����
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo,  java.lang.String)
	 */
	public int select(UserInfo userInfo, String shozokuCd) throws ApplicationException {
		

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
			return dao.countShozokuInfo(connection, shozokuCd);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * �p�X���[�h��ύX����B<br/><br/>
	 * 
	 * <b>1.�����@�֒S���ҏ��̎擾</b><br/>
	 * �@�����@�֒S���҃e�[�u������A�����Ŏw�肳��郌�R�[�h�����擾����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- �����@�֒S����ID
	 *     ,A.SHOZOKU_CD"				-- �����@�֖��i�R�[�h�j
	 *     ,A.SHOZOKU_NAME_KANJI			-- �����@�֖��i���{��j
	 *     ,A.SHOZOKU_RYAKUSHO			-- �����@�֖��i���́j
	 *     ,A.SHOZOKU_NAME_EIGO			-- �����@�֖��i�p���j
	 *     ,A.SHUBETU_CD				-- �@�֎��
	 *     ,A.PASSWORD				-- �p�X���[�h
	 *     ,A.SEKININSHA_NAME_SEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_NAME_MEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_YAKU			-- �ӔC�Җ�E
	 *     ,A.BUKYOKU_NAME				-- �S�����ۖ�
	 *     ,A.KAKARI_NAME				-- �S���W��
	 *     ,A.TANTO_NAME_SEI			-- �S���Җ��i���j
	 *     ,A.TANTO_NAME_MEI			-- �S���Җ��i���j
	 *     ,A.TANTO_TEL				-- �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
	 *     ,A.TANTO_FAX				-- �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
	 *     ,A.TANTO_EMAIL				-- �S���ҕ��Ǐ��ݒn�iEmail�j
	 *     ,A.TANTO_EMAIL2				-- �S���ҕ��Ǐ��ݒn�iEmail2�j
	 *     ,A.TANTO_ZIP				-- �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
	 *     ,A.TANTO_ADDRESS				-- �S���ҕ��Ǐ��ݒn�i�Z���j
	 *     ,A.NINSHOKEY_FLG				-- �F�؃L�[���s�t���O
	 *     ,A.BIKO				-- ���l
	 *     ,A.YUKO_DATE				-- �L������
	 *     ,A.ID_DATE				-- ID���s���t
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKUTANTO_ID</td><td>������pkInfo�̕ϐ�shozokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * <b>2.���݂̃p�X���[�h���`�F�b�N</b><br/>
	 * �@1.�Ŏ擾���������@�֒S���ҏ��̌��݂̃p�X���[�h�ƁA��O����oldPassword���r����B<br/>
	 * �@��v���Ȃ��Ƃ��A��O��throw����B<br/><br/>
	 * 
	 * <b>3.���݂̃p�X���[�h���X�V</b><br/>
	 * �@�p�X���[�h��V�����p�X���[�h�֕ύX���邽�߂ɁA�����@�֒S���҃e�[�u�����X�V����B<br/><br/>
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE SHOZOKUTANTOINFO SET
	 *     PASSWORD = ?
	 * WHERE
	 *     SHOZOKUTANTO_ID = ?
	 *     AND DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br/>
	 * �ύX�l
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>PASSWORD</td><td>��l����newPassword</td></tr>
	 * </table><br/>
	 * �i���ݏ���
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKUTANTO_ID</td><td>������pkInfo�̕ϐ�shozokuTantoId</td></tr>
	 * </table><br/><br/> 
	 * 
	 * @param userInfo		UserInfo
	 * @param pkInfo 		�p�X���[�h��ύX���郌�R�[�h��PK�iShozokuPk�j
	 * @param oldPassword ���p�X���[�h
	 * @param newPassword �V�p�X���[�h
	 * @return true
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#changePassword(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShozokuPk, java.lang.String, java.lang.String)
	 */
	public boolean changePassword(UserInfo userInfo, ShozokuPk pkInfo, String oldPassword, String newPassword) throws ApplicationException, ValidationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�����@�ւ̎擾
			//---------------------------------------
			ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
			ShozokuInfo info = dao.selectShozokuInfo(connection, pkInfo);

			//---------------------------------------
			//���݂̃p�X���[�h���`�F�b�N����B
			//---------------------------------------
			if (!info.getPassword().equals(oldPassword)) {
				//�G���[���ێ��p���X�g
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.2001", new String[] { "���݂̃p�X���[�h" }));
				throw new ValidationException(
						"�p�X���[�h�ύX�f�[�^�`�F�b�N���ɃG���[��������܂����B",
						errors);
			}

			//---------------------------------------
			//���݂̃p�X���[�h���X�V����B
			//---------------------------------------
			if(dao.changePasswordShozokuInfo(connection,pkInfo,newPassword)){
				//�X�V����I��
				success = true;
			}

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�p�X���[�h�ύX����DB�G���[���������܂���",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�p�X���[�h�ύX����DB�G���[���������܂���",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		return success;
	}

	/**
	 * validate���\�b�h.<br />
	 * <br />
	 * �������null��ԋp����B
	 * 
	 * @param userInfo				UserInfo
	 * @param insertOrUpdateInfo	ShozokuInfo
	 * @return null
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShozokuInfo)
	 */
	public ShozokuInfo validate(UserInfo userInfo, ShozokuInfo insertOrUpdateInfo) throws ApplicationException, ValidationException {
		return null;
	}

	/**
	 * �����@�֒S���ҏ��̃f�[�^���X�g���擾����.<br />
	 * <br />
	 * �����@�֒S���ҏ����擾���AList�֊i�[��ɌĂяo�����֕ԋp����B<br/><br/>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- �����@�֒S����ID
	 *     ,A.SHOZOKU_CD"				-- �����@�֖��i�R�[�h�j
	 *     ,A.SHOZOKU_NAME_KANJI			-- �����@�֖��i���{��j
	 *     ,A.SHOZOKU_RYAKUSHO			-- �����@�֖��i���́j
	 *     ,A.SHOZOKU_NAME_EIGO			-- �����@�֖��i�p���j
	 *     ,A.SHUBETU_CD				-- �@�֎��
	 *     ,A.PASSWORD				-- �p�X���[�h
	 *     ,A.SEKININSHA_NAME_SEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_NAME_MEI			-- �ӔC�Ҏ����i���j
	 *     ,A.SEKININSHA_YAKU			-- �ӔC�Җ�E
	 *     ,A.BUKYOKU_NAME				-- �S�����ۖ�
	 *     ,A.KAKARI_NAME				-- �S���W��
	 *     ,A.TANTO_NAME_SEI			-- �S���Җ��i���j
	 *     ,A.TANTO_NAME_MEI			-- �S���Җ��i���j
	 *     ,A.TANTO_TEL				-- �S���ҕ��Ǐ��ݒn�i�d�b�ԍ��j
	 *     ,A.TANTO_FAX				-- �S���ҕ��Ǐ��ݒn�iFAX�ԍ��j
	 *     ,A.TANTO_EMAIL				-- �S���ҕ��Ǐ��ݒn�iEmail�j
	 *     ,A.TANTO_EMAIL2				-- �S���ҕ��Ǐ��ݒn�iEmail2�j
	 *     ,A.TANTO_ZIP				-- �S���ҕ��Ǐ��ݒn�i�X�֔ԍ��j
	 *     ,A.TANTO_ADDRESS				-- �S���ҕ��Ǐ��ݒn�i�Z���j
	 *     ,A.NINSHOKEY_FLG				-- �F�؃L�[���s�t���O
	 *     ,A.BIKO				-- ���l
	 *     ,A.YUKO_DATE				-- �L������
	 *     ,A.ID_DATE				-- ID���s���t
	 *     ,BUKYOKU_NUM				-- ���ǒS���Ґl��
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     DEL_FLG = 0
	 *     <b>AND SHOZOKU_CD = '�����@�փR�[�h'(��)</b>
	 * ORDER BY SHOZOKUTANTO_ID		-- �����@�֒S����ID
	 * </pre>
	 * </td></tr>
	 * </table>
	 *	<div style="font-size:8pt">�i���j�̌��������ɂ��񂵂ẮA������shozokuCd��null�łȂ��Ƃ��̂݉�������B
	 *	�Ȃ��A'<b>�����@�փR�[�h</b>'�͑�������shozokuCd���Z�b�g�����B</div><br />
	 * 
	 * @param userInfo		UserInfo
	 * @param shozokuCd 	�����@�փR�[�h
	 * @return �����@�֒S���ҏ��̃��X�g
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShozokuInfo)
	 */
	public List searchShozokuInfo(UserInfo userInfo, String shozokuCd) throws ApplicationException {

		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		
		String select = 
			"SELECT *  FROM SHOZOKUTANTOINFO A WHERE DEL_FLG = 0";	
			
		StringBuffer query = new StringBuffer(select);
		if(shozokuCd != null && !shozokuCd.equals("")){		
			query.append(" AND SHOZOKU_CD = " + EscapeUtil.toSqlString(shozokuCd));
		}
		
		//�\�[�g���i�����@�֒S����ID�̏����j
		query.append(" ORDER BY SHOZOKUTANTO_ID");			

		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		//------------------------
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.select(connection, query.toString());
		} catch (DataAccessException e) {
			log.error("�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
//	2005/04/20 �ǉ� ��������----------------------------------------
//	���R �V�X�e���Ǘ��Ҍ����@�\�̏����@�֏�񌟍��p
	/**
	 * �����@�֏��̌���.<br><br>
	 * 
	 * �����@�֒S���҂ƕ��ǒS���ҏ����擾����B
	 * 
	 * @param userInfo
	 * @param searchInfo
	 * @return
	 * @throws ApplicationException
	 */
	public Page searchShozokuTantoList(UserInfo userInfo, ShozokuSearchInfo searchInfo)
		throws ApplicationException {
		
		ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
		return dao.selectShozokuAndBukyokuTanto(userInfo,searchInfo);
	}
//	�ǉ� �����܂�-----------------------------------------------------

//	2005/04/21 �ǉ� ��������----------------------------------------
//	���R �p�X���[�h�Đݒ菈���ǉ�
	
	/**
	 * �p�X���[�h�Đݒ�.<br><br>
	 * 
	 * @param userInfo
	 * @param pkInfo
	 * @return
	 * @throws ApplicationException
	 */
	public ShozokuInfo reconfigurePassword(UserInfo userInfo, ShozokuPk pkInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�����@�֒S���ҏ��̎擾
			//---------------------------------------
			ShozokuInfoDao dao = new ShozokuInfoDao(userInfo);
			ShozokuInfo info = dao.selectShozokuInfo(connection, pkInfo);

			//RULEINFO�e�[�u����胋�[���擾����
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHOZOKUTANTO);

			//�p�X���[�h���Đݒ肷��
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			success = dao.changePasswordShozokuInfo(connection,pkInfo,newPassword);

			//---------------------------------------
			//�����@�֒S���҃f�[�^�̎擾
			//---------------------------------------
			ShozokuInfo result = dao.selectShozokuInfo(connection, pkInfo);
			
			return result;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�p�X���[�h�Đݒ蒆��DB�G���[���������܂���",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�p�X���[�h�Đݒ蒆��DB�G���[���������܂���",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
//	�ǉ� �����܂�-----------------------------------------------------

	/**
	 * ���F�m�F���[�������ʒm���M���s
	 * @param userInfo
	 * @throws ApplicationException
	 */
	public void sendMailShoninTsuchi(UserInfo userInfo) throws ApplicationException
	{
		List jigyoList = null;
		Connection connection = null;

		//���F���ߐ؂�����̐ݒ�
		DateUtil du = new DateUtil();
		du.addDate(DATE_BY_SHONIN_KIGEN);	//�w����t�����Z����
		Date date = du.getCal().getTime();

		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			//���ƊǗ�DAO
			JigyoKanriInfoDao jigyoDao = new JigyoKanriInfoDao(userInfo);
			try {
				jigyoList = jigyoDao.selectShoninTsuchiJigyoInfo(connection, DATE_BY_SHONIN_KIGEN);
			} catch (NoDataFoundException e) {
				//�����������Ȃ�
			} catch (DataAccessException e) {
				throw new ApplicationException("���F�m�F���[�������ʒm���M���ƃf�[�^�擾����DB�G���[���������܂����B",
						new ErrorInfo("errors.4004"), e);
			}
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		connection = null;

		//�Y�����鎖�Ƃ����݂��Ȃ������ꍇ
		if (jigyoList == null || jigyoList.size() == 0) {
			String strDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
			String msg = "��W���ߐ؂肪[" + strDate + "]�̎��ƂŁA���F�m�F�̐\�����������Ƃ͑��݂��܂���B";
			log.info(msg);
			return;
		}

		//-----�f�[�^�\����ϊ��i�d���f�[�^���܂ޒP�ꃊ�X�g���珊���@�֒S���҂��Ƃ̃}�b�v�֕ϊ�����j
		//�S��Map
		Map saisokuMap = new HashMap();
		for (int i = 0; i < jigyoList.size(); i++) {

			//1���R�[�h
			Map recordMap = (Map) jigyoList.get(i);
			String nendo = (String) recordMap.get("NENDO");
			BigDecimal kaisu = (BigDecimal) recordMap.get("KAISU");
			String jigyo_name = (String) recordMap.get("JIGYO_NAME");
			String tanto_email = (String) recordMap.get("TANTO_EMAIL");

			String kaisu_hyoji = "";
			if (kaisu.intValue() > 1) {
				kaisu_hyoji = "��" + kaisu + "�� "; //�񐔂�1��ȏ�̏ꍇ�͕\������
			}

			//���Ɩ����u�N�x�{���Ɩ��v�֕ϊ����A�₢���킹����i���s�{�S�p�󔒁j�Ō����B
// UPDATE START 2007-07-09 BIS ���u��
//			String jigyo_info = new StringBuffer("�y������ږ��z����")
//											.append(nendo)
//											.append("�N�x ")
//											.append(kaisu_hyoji)
//											.append(jigyo_name)
//											.toString();
			String jigyo_info = new StringBuffer("    ����")
											.append(nendo)
											.append("�N�x ")
											.append(kaisu_hyoji)
											.append(jigyo_name)
											.toString();
// UPDATE END 2007-07-09 BIS ���u��
			//�S��Map�ɓ��Y�����@�֒S���҃f�[�^�����݂��Ă����ꍇ
			if (saisokuMap.containsKey(tanto_email)) {
				List dataList = (List) saisokuMap.get(tanto_email);
				dataList.add(jigyo_info); //�����R�[�h�Ɏ��Ə��
			} else {
				//���̎��Ƃ̏ꍇ	
				List dataList = new ArrayList();
				dataList.add(tanto_email); //1���R�[�h�ڂɃ��[���A�h���X
				dataList.add(jigyo_info); //2���R�[�h�ڂɎ��Ə��
				saisokuMap.put(tanto_email, dataList);
			}

		}

		//---------------
		// ���[�����M
		//---------------
		//-----���[���{���t�@�C���̓ǂݍ���
		String content = null;
		try {
			File contentFile = new File(CONTENT_SHINSEISHO_SHONIN_TSUCHI);
			FileResource fileRes = FileUtil.readFile(contentFile);
			content = new String(fileRes.getBinary());
		} catch (FileNotFoundException e) {
			log.warn("���[���{���t�@�C����������܂���ł����B", e);
			return;
		} catch (IOException e) {
			log.warn("���[���{���t�@�C���ǂݍ��ݎ��ɃG���[���������܂����B", e);
			return;
		}

		//���F���ߐ؂���t�t�H�[�}�b�g��ύX����
		String kigenDate = new StringBuffer("����")
								.append(new DateUtil(date).getNendo())
								.append("�N")
								.append(new SimpleDateFormat("M��d��").format(date))
								.toString();

		//-----���[�����M�i�P�l�����M�j
		for (Iterator iter = saisokuMap.keySet().iterator(); iter.hasNext();) {

			//�����@�֒S���҂��Ƃ̃f�[�^���X�g���擾����
			String tantoEmail = (String) iter.next();
			List dataList = (List) saisokuMap.get(tantoEmail);

			//���[���A�h���X���ݒ肳��Ă��Ȃ��ꍇ�͏������΂�
			String to = (String) dataList.get(0);
			if (to == null || to.length() == 0) {
				continue;
			}

			//-----���[���{���t�@�C���̓��I���ڕύX
// UPDATE START 2007-07-24 BIS ���u��
//			StringBuffer jigyoNameList = new StringBuffer("\n");
//			for (int i = 1; i < dataList.size(); i++) {
//				jigyoNameList.append(dataList.get(i)).append("\n");
//			}
			StringBuffer jigyoNameList = new StringBuffer("");
			for (int i = 1; i < dataList.size(); i++) {
				jigyoNameList.append("\n");
				jigyoNameList.append(dataList.get(i));
			}
// UPDATE END 2007-07-24 BIS ���u��
			String[] param = new String[] { kigenDate, //���F���ߐ؂���t
									jigyoNameList.toString(), //���Ɩ����X�g
								};
			String body = MessageFormat.format(content, param);

			if (log.isDebugEnabled()){
				log.debug("���M���F********************\n" + body);
			}
			try {
				SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
				mailer.sendMail(FROM_ADDRESS, //���o�l
						to, //to
						null, //cc
						null, //bcc
						SUBJECT_SHINSEISHO_SHONIN_TSUCHI, //����
						body); //�{��
			} catch (Exception e) {
				log.warn("���[�����M�Ɏ��s���܂����B", e);
				continue;
			}
		}

	}




}

