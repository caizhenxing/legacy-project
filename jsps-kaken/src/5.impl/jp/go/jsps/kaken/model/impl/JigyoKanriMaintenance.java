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

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

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
 * ���Ə��Ǘ��N���X.<br><br>
 * 
 * <b>�T�v</b><br>
 * �E�g�p�e�[�u��<br>
 * ���Ə��Ǘ��e�[�u��	�F���Ƃ̊�{�����Ǘ�<br>
 * ���ފǗ��e�[�u��		�F���Ƃ��Ƃ̏��ނ��Ǘ�<br>
 */
public class JigyoKanriMaintenance implements IJigyoKanriMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(ShozokuMaintenance.class);
	
	/** �V�X�e���ԍ��t�H�[�}�b�g */
	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	/** �Y�t�t�@�C���iWin�j */
	private static String SHINSEI_TENPUWIN_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_TENPUWIN_FOLDER);

	/** �Y�t�t�@�C���iMac�j */
	private static String SHINSEI_TENPUMAC_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_TENPUMAC_FOLDER);
	
	/** �]���p�t�@�C�� */
	private static String SHINSEI_HYOKA_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_HYOKA_FOLDER);

	/** ���ރt�@�C�� */
	private static String SHINSEI_SHORUI_FOLDER = ApplicationSettings.getString(ISettingKeys.SHINSEI_SHORUI_FOLDER);
	
	/** �����\���̎��Ƌ敪 */
	private static String[] HIKOBO_JIGYO_KUBUN = new String[]{IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO};
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^.
	 */
	public JigyoKanriMaintenance() {
		super();
	}
	
	
	
	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------
	
	/**
	 * �V�X�e���ԍ��̐���.
	 * 
	 * @return �V�X�e���ԍ�
	 */
	private static synchronized String getSystemNumber(){
		//���ݎ������V�X�e����t�ԍ��̃t�H�[�}�b�g�ɕϊ�����
		Date now = new Date();
		String systemNo = sdf.format(now);
		return systemNo;
	}
	

	
	//---------------------------------------------------------------------
	// implement IJigyoKanriMaintenance
	//---------------------------------------------------------------------
	
	/**
	 * ���Ə��̐V�K�쐬.<br/><br/>
	 * 
	 * ���Ə���V�K�쐬����B<br/><br/>
	 * 
	 * <b>1.����ID�̍쐬</b><br/>
	 * (1)DateUtil�N���X��changeWareki2Seireki()���\�b�h�ɂāA
	 * ������addInfo�̕ϐ�nendo��a��琼��ɕϊ�����B<br/><br/>
	 * 
	 * (2)����ID���쐬����B<br/>
	 * �@(1)�Ŏ擾�����N�x + ������addInfo�̕ϐ�jigyoCd + ������addInfo�̕ϐ�kaisu ������ID�Ƃ���B<br/><br/>
	 * 
	 * <b>2.�A�b�v���[�h�t�@�C���̏�������</b><br>
	 * �@Win�\���t�@�C���AMac�\���t�@�C���A�]���t�@�C���ɂ��Ă��ꂼ�ꏈ�����s���B<br><br>
	 * 
	 * (1)FileUtil�N���X��getExtention()���\�b�h�ŁA�t�@�C���̊g���q���擾����B<br><br>
	 * 
	 * (2)�t�@�C���p�X�𐶐�����B<br>
	 * �@Win�\���t�@�C���|�ϐ�SHINSEI_TENPUWIN_FOLDER<br>
	 * �@Mac�\���t�@�C���|�ϐ�SHINSEI_TENPUMAC_FOLDER<br>
	 * �@�]���t�@�C���|�ϐ�SHINSEI_HYOKA_FOLDER<br><br>
	 * 
	 * �@�z��pathInfo�ɁA1.�ō쐬��������ID�A(1)�Ŏ擾�����t�@�C���g���q���i�[����B<br/><br/>
	 * 
	 * �@�ϐ��̃p�^�[���ɏ]���A�z��pathInfo��MessageFormat�N���X��format()���\�b�h��p���ăt�H�[�}�b�g����B<br/>
	 * �@���������t�@�C���p�X�ŁA�t�@�C���𐶐�����B<br/><br/>
	 * 
	 * �@(��)Win�\���t�@�C���p�^�[���F${shinsei_path}/data/{0}/tenpufile/win/{0}.{1}<br/>
	 * �@�@�@�\���t�@�C���g���q�Fdoc�@�@����ID�F04000011�@�̂Ƃ�<br>
	 * �@�@�@�t�@�C���p�X�F${shinsei_path}/data/04000011/tenpufile/win/04000011.doc<br>
	 * �@�@�@�@��${shinsei_path}�̒l��ApplicationSettings.properties�ɐݒ�<br><br>
	 * 
	 * (3)�t�@�C�����������ށB<br>
	 * �@���N���X��writeFile()���\�b�h���ĂсA�t�@�C�����������ށB<br><br>
	 * 
	 * <b>3.���ƃ}�X�^�����K�v���̎擾�i���Ƌ敪�A�R���敪�j</b><br>
	 * (1)���ƃ}�X�^���̎擾
	 * �@���ƃ}�X�^�e�[�u�����������A���ʃ��R�[�h��Map�`���ŕԂ��B<br><br>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_CD				-- ���ƃR�[�h
	 *     ,A.JIGYO_NAME			-- ���Ɩ���
	 *     ,A.KEI_KUBUN				-- �n���敪
	 *     ,A.JIGYO_KUBUN			-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN			-- �R���敪
	 *     ,A.BIKO					-- ���l
	 * FROM
	 *     MASTER_JIGYO A
	 * WHERE
	 *     JIGYO_CD = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>������addInfo�̕ϐ�jigyoCd</td></tr>
	 * </table><br/><br/>
	 * 
	 * (2)���Ƌ敪�A�R���敪�̃Z�b�g<br/>
	 * �@(1)�Ŏ擾����Map���玖�Ƌ敪�A�R���敪���擾���A
	 * StringUtil�N���X��defaultString()���\�b�h�ŕ�������`�F�b�N����B<br/>
	 * �@addInfo�̕ϐ�jigyoKubun,�ϐ�shinsaKubun���Z�b�g����B<br/><br/>
	 * 
	 * <b>4.���Ə��̒ǉ�</b><br/>
	 * �@�d���`�F�b�N���A�f�[�^�����݂��Ȃ��Ƃ��o�^�������s���B<br/>
	 * �@�f�[�^�����݂���ꍇ�A��O��throw����B<br/><br/>
	 * 
	 * (1)�d���`�F�b�N<br/>
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- ����ID
	 *     ,A.NENDO					-- �N�x
	 *     ,A.KAISU					-- ��
	 *     ,A.JIGYO_NAME			-- ���Ɩ�
	 *     ,A.JIGYO_KUBUN			-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN			-- �R���敪
	 *     ,A.TANTOKA_NAME			-- �Ɩ��S����
	 *     ,A.TANTOKAKARI			-- �Ɩ��S���W��
	 *     ,A.TOIAWASE_NAME			-- �₢���킹��S���Җ�
	 *     ,A.TOIAWASE_TEL			-- �₢���킹��d�b�ԍ�
	 *     ,A.TOIAWASE_EMAIL		-- �₢���킹��E-mail
	 *     ,A.UKETUKEKIKAN_START	-- �w�U��t���ԁi�J�n�j
	 *     ,A.UKETUKEKIKAN_END		-- �w�U��t���ԁi�I���j
	 *     ,A.SHINSAKIGEN			-- �R������
	 *     ,A.TENPU_NAME			-- �Y�t������
	 *     ,A.TENPU_WIN				-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *     ,A.TENPU_MAC				-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *     ,A.HYOKA_FILE_FLG		-- �]���p�t�@�C���L��
	 *     ,A.HYOKA_FILE			-- �]���p�t�@�C���i�[�t�H���_
	 *     ,A.KOKAI_FLG				-- ���J�t���O
	 *     ,A.KESSAI_NO				-- ���J���ٔԍ�
	 *     ,A.KOKAI_ID				-- ���J�m���ID
	 *     ,A.HOKAN_DATE			-- �f�[�^�ۊǓ�
	 *     ,A.YUKO_DATE				-- �ۊǗL������
	 *     ,A.BIKO				-- ���l
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     JIGYO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������addInfo�̕ϐ�jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * (2)���Ə��̓o�^<br/>
	 * �@���Ə��Ǘ��e�[�u���ɓo�^����B<br/>
	 * �@(1)�̌��ʁA�Y���f�[�^�����݂��Ȃ��Ƃ��o�^�������s���B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * INSERT INTO JIGYOKANRI A(
	 *     A.JIGYO_ID				-- ����ID
	 *     ,A.NENDO					-- �N�x
	 *     ,A.KAISU					-- ��
	 *     ,A.JIGYO_NAME			-- ���Ɩ�
	 *     ,A.JIGYO_KUBUN			-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN			-- �R���敪
	 *     ,A.TANTOKA_NAME			-- �Ɩ��S����
	 *     ,A.TANTOKAKARI			-- �Ɩ��S���W��
	 *     ,A.TOIAWASE_NAME			-- �₢���킹��S���Җ�
	 *     ,A.TOIAWASE_TEL			-- �₢���킹��d�b�ԍ�
	 *     ,A.TOIAWASE_EMAIL		-- �₢���킹��E-mail
	 *     ,A.UKETUKEKIKAN_START	-- �w�U��t���ԁi�J�n�j
	 *     ,A.UKETUKEKIKAN_END		-- �w�U��t���ԁi�I���j
	 *     ,A.SHINSAKIGEN			-- �R������
	 *     ,A.TENPU_NAME			-- �Y�t������
	 *     ,A.TENPU_WIN				-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *     ,A.TENPU_MAC				-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *     ,A.HYOKA_FILE_FLG		-- �]���p�t�@�C���L��
	 *     ,A.HYOKA_FILE			-- �]���p�t�@�C���i�[�t�H���_
	 *     ,A.KOKAI_FLG				-- ���J�t���O
	 *     ,A.KESSAI_NO				-- ���J���ٔԍ�
	 *     ,A.KOKAI_ID				-- ���J�m���ID
	 *     ,A.HOKAN_DATE			-- �f�[�^�ۊǓ�
	 *     ,A.YUKO_DATE				-- �ۊǗL������
	 *     ,A.BIKO				-- ���l
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * )
	 * VALUES
	 *     (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������addInfo�̕ϐ�jigyoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>NENDO</td><td>������addInfo�̕ϐ�nendo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KAISU</td><td>������addInfo�̕ϐ�kaisu</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_NAME</td><td>������addInfo�̕ϐ�jigyoName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_KUBUN</td><td>������addInfo�̕ϐ�jigyoKubun</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHINSA_KUBUN</td><td>������addInfo�̕ϐ�shinsaKubun</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTOKA_NAME</td><td>������addInfo�̕ϐ�tantokaName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTOKAKARI</td><td>������addInfo�̕ϐ�tantoKakari</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_NAME</td><td>������addInfo�̕ϐ�toiawaseName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_TEL</td><td>������addInfo�̕ϐ�toiawaseTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_EMAIL</td><td>������addInfo�̕ϐ�toiawaseEmail</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>UKETUKEKIKAN_START</td><td>������addInfo�̕ϐ�uketukekikanStart</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>UKETUKEKIKAN_END</td><td>������addInfo�̕ϐ�uketukekikanEnd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHINSAKIGEN</td><td>������addInfo�̕ϐ�shinsaKigen</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_NAME</td><td>������addInfo�̕ϐ�tenpuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_WIN</td><td>������addInfo�̕ϐ�tenpuWin</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_MAC</td><td>������addInfo�̕ϐ�tenpuMac</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HYOKA_FILE_FLG</td><td>������addInfo�̕ϐ�hyokaFileFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HYOKA_FILE</td><td>������addInfo�̕ϐ�hyokaFile</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_FLG</td><td>������addInfo�̕ϐ�kokaiFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KESSAI_NO</td><td>������addInfo�̕ϐ�kessaiNo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_ID</td><td>������addInfo�̕ϐ�kokaiID</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HOKAN_DATE</td><td>������addInfo�̕ϐ�hokanDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>YUKO_DATE</td><td>������addInfo�̕ϐ�YukoDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BIKO</td><td>������addInfo�̕ϐ�Biko</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>DEL_FLG</td><td>0�@(�ʏ�)</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>5.�o�^�f�[�^�̎擾</b><br/>
	 * �@�������ʂ�0���̂Ƃ��A��O��throw����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- ����ID
	 *     ,A.NENDO					-- �N�x
	 *     ,A.KAISU					-- ��
	 *     ,A.JIGYO_NAME			-- ���Ɩ�
	 *     ,A.JIGYO_KUBUN			-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN			-- �R���敪
	 *     ,A.TANTOKA_NAME			-- �Ɩ��S����
	 *     ,A.TANTOKAKARI			-- �Ɩ��S���W��
	 *     ,A.TOIAWASE_NAME			-- �₢���킹��S���Җ�
	 *     ,A.TOIAWASE_TEL			-- �₢���킹��d�b�ԍ�
	 *     ,A.TOIAWASE_EMAIL		-- �₢���킹��E-mail
	 *     ,A.UKETUKEKIKAN_START	-- �w�U��t���ԁi�J�n�j
	 *     ,A.UKETUKEKIKAN_END		-- �w�U��t���ԁi�I���j
	 *     ,A.SHINSAKIGEN			-- �R������
	 *     ,A.TENPU_NAME			-- �Y�t������
	 *     ,A.TENPU_WIN				-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *     ,A.TENPU_MAC				-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *     ,A.HYOKA_FILE_FLG		-- �]���p�t�@�C���L��
	 *     ,A.HYOKA_FILE			-- �]���p�t�@�C���i�[�t�H���_
	 *     ,A.KOKAI_FLG				-- ���J�t���O
	 *     ,A.KESSAI_NO				-- ���J���ٔԍ�
	 *     ,A.KOKAI_ID				-- ���J�m���ID
	 *     ,A.HOKAN_DATE			-- �f�[�^�ۊǓ�
	 *     ,A.YUKO_DATE				-- �ۊǗL������
	 *     ,A.BIKO				-- ���l
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     JIGYO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������addInfo�̕ϐ�jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo	UserInfo
	 * @param addInfo	�o�^���(JigyoKanriInfo)
	 * @return ���Ə��
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public JigyoKanriInfo insert(UserInfo userInfo, JigyoKanriInfo addInfo)
		throws ApplicationException {
			
		boolean success = false;
		
		
		//---------------------------------------
		//�L�[���́i����ID�j���쐬
		//����N�x�i2���j+����CD�i5���j+�񐔁i1���j
		//---------------------------------------
		String jigyoId = null;
		//----------�N�x�𐼗�N�x�i2���j�ɕϊ�
		String nendo = addInfo.getNendo();
		
		try{
			nendo = DateUtil.changeWareki2Seireki(nendo);
			//----------����ID���쐬
			jigyoId = nendo + addInfo.getJigyoCd() + addInfo.getKaisu();
			addInfo.setJigyoId(jigyoId);
		}catch(Exception e){
			throw new ApplicationException(
				"����ID���쐬���ɃG���[���������܂����B",
				new ErrorInfo("errors.4001"),
				e);			
		}

		Connection connection = null;
		try {	
			connection = DatabaseUtil.getConnection();
			//--------�A�b�v���[�h�t�@�C���̏�������
			//---�Y�t�t�@�C���t�H���_�iWin�j
			FileResource tenpuWinFileRes = addInfo.getTenpuWinFileRes();	
			if(tenpuWinFileRes != null){
				//�g���q�̎擾
				String extension = FileUtil.getExtention(tenpuWinFileRes.getName());
				//�t�@�C���p�X�𐶐�
				String[] pathInfo = new String[]{jigyoId, extension};
				String   tenpuWinPath  = MessageFormat.format(SHINSEI_TENPUWIN_FOLDER, pathInfo);
				File     tenpuWinFile   = new File(tenpuWinPath);
				//�t�@�C���̏�������
				writeFile(tenpuWinFile, tenpuWinFileRes, true);
				//�t�@�C���p�X���Z�b�g
				addInfo.setTenpuWin(tenpuWinPath);
				//�Y�t�t�@�C�������Z�b�g
				addInfo.setTenpuName(tenpuWinFile.getName());				
			}
			
			//---�Y�t�t�@�C���t�H���_�iMac�j				
			FileResource tenpuMacFileRes = addInfo.getTenpuMacFileRes();	
			if(tenpuMacFileRes != null){
				//�g���q�̎擾
				String extension = FileUtil.getExtention(tenpuMacFileRes.getName());
				//�t�@�C���p�X�𐶐�
				String[] pathInfo = new String[]{jigyoId, extension};
				String   tenpuMacPath  = MessageFormat.format(SHINSEI_TENPUMAC_FOLDER, pathInfo);
				File     tenpuMacFile   = new File(tenpuMacPath);	
				//�t�@�C���̏�������
				writeFile(tenpuMacFile, tenpuMacFileRes, true);
				//�t�@�C���p�X���Z�b�g
				addInfo.setTenpuMac(tenpuMacPath);
				//�Y�t�t�@�C�������Z�b�g
				addInfo.setTenpuName(tenpuMacFile.getName());				
			}
			
			//---�]���p�t�@�C���t�H���_			
			FileResource hyokaFileRes = addInfo.getHyokaFileRes();	
			if(hyokaFileRes != null){
				//�g���q�̎擾
				String extension = FileUtil.getExtention(hyokaFileRes.getName());
				//�t�@�C���p�X�𐶐�
				String[] pathInfo = new String[]{jigyoId, extension};
				String   hyokaPath  = MessageFormat.format(SHINSEI_HYOKA_FOLDER, pathInfo);
				File     hyokaFile   = new File(hyokaPath);			
				//�t�@�C���̏�������
				writeFile(hyokaFile, hyokaFileRes, true);
				//�t�@�C���p�X���Z�b�g
				addInfo.setHyokaFile(hyokaPath);
			}
			
			
			//---------------------------------------
			//���ƃ}�X�^�����K�v���̎擾�i���Ƌ敪�A�R���敪�j
			//---------------------------------------
			Map masterInfo = MasterJigyoInfoDao.selectRecord(connection, addInfo.getJigyoCd());
			addInfo.setJigyoKubun(StringUtil.defaultString(masterInfo.get("JIGYO_KUBUN"),null));
			addInfo.setShinsaKubun(StringUtil.defaultString(masterInfo.get("SHINSA_KUBUN"),null));
			
			//---------------------------------------
			//���ƊǗ����̒ǉ�
			//---------------------------------------
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			dao.insertJigyoKanriInfo(connection,addInfo);		

			//---------------------------------------
			//�o�^�f�[�^�̎擾
			//---------------------------------------
			JigyoKanriInfo result = dao.selectJigyoKanriInfo(connection, addInfo);

			//---------------------------------------
			//SEQ�̓o�^
			//---------------------------------------
			//�Ȍ��ł͎g�p���Ȃ��B
			//dao.createSEQ(connection, jigyoId);	

			//---------------------------------------
			//�o�^����I��
			//---------------------------------------
			success = true;			
			return result;
	
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���ƊǗ��f�[�^�o�^����DB�G���[���������܂����B",
				new ErrorInfo("errors.4001"),
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
					"���ƊǗ��f�[�^�o�^����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	/**
	 * ���Ə��̍X�V.<br><br>
	 * 
	 * ���Ə����X�V����B<br><br>
	 * 
	 * <b>1.�A�b�v���[�h�t�@�C���̏�������</b><br/>
	 * (1)FileUtil�N���X��getExtention()���\�b�h�ɂāA�Y�t�t�@�C���̊g���q���擾����B<br/><br/>
	 * 
	 * <b>2.�A�b�v���[�h�t�@�C���̏�������</b><br>
	 * �@Win�\���t�@�C���AMac�\���t�@�C���A�]���t�@�C���ɂ��Ă��ꂼ�ꏈ�����s���B<br><br>
	 * 
	 * (1)FileUtil�N���X��getExtention()���\�b�h�ŁA�t�@�C���̊g���q���擾����B<br><br>
	 * 
	 * (2)�t�@�C���p�X�𐶐�����B<br>
	 * �@Win�\���t�@�C���|�ϐ�SHINSEI_TENPUWIN_FOLDER<br>
	 * �@Mac�\���t�@�C���|�ϐ�SHINSEI_TENPUMAC_FOLDER<br>
	 * �@�]���t�@�C���|�ϐ�SHINSEI_HYOKA_FOLDER<br><br>
	 * 
	 * �@�z��pathInfo�ɁA1.�ō쐬��������ID�A(1)�Ŏ擾�����t�@�C���g���q���i�[����B<br/><br/>
	 * 
	 * �@�ϐ��̃p�^�[���ɏ]���A�z��pathInfo��MessageFormat�N���X��format()���\�b�h��p���ăt�H�[�}�b�g����B<br/>
	 * �@���������t�@�C���p�X�ŁA�t�@�C���𐶐�����B<br/><br/>
	 * �@�@(��)Win�\���t�@�C���p�^�[���F${shinsei_path}/data/{0}/tenpufile/win/{0}.{1}<br/>
	 * �@�@�@�@�\���t�@�C���g���q�Fdoc�@�@����ID�F04000011�@�̂Ƃ�<br>
	 * �@�@�@�@�t�@�C���p�X�F${shinsei_path}/data/04000011/tenpufile/win/04000011.doc<br>
	 * �@�@�@�@�@��${shinsei_path}�̒l��ApplicationSettings.properties�ɐݒ�<br><br>
	 * 
	 * (3)�t�@�C�����������ށB<br>
	 * �@�@���N���X��writeFile()���\�b�h���ĂсA�t�@�C�����������ށB<br><br>
	 * 
	 * <b>2.���Ə��̍X�V</b><br/>
	 * �@���Ə��Ǘ��e�[�u�����X�V����B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 *     JIGYOKANRI A
	 * SET
	 *     A.JIGYO_ID = ?			-- ����ID
	 *     ,A.NENDO = ?				-- �N�x
	 *     ,A.KAISU = ?				-- ��
	 *     ,A.JIGYO_NAME = ?		-- ���Ɩ�
	 *     ,A.JIGYO_KUBUN = ?		-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN = ?		-- �R���敪
	 *     ,A.TANTOKA_NAME = ?		-- �Ɩ��S����
	 *     ,A.TANTOKAKARI = ?		-- �Ɩ��S���W��
	 *     ,A.TOIAWASE_NAME = ?		-- �₢���킹��S���Җ�
	 *     ,A.TOIAWASE_TEL = ?		-- �₢���킹��d�b�ԍ�
	 *     ,A.TOIAWASE_EMAIL = ?	-- �₢���킹��E-mail
	 *     ,A.UKETUKEKIKAN_START = ?	-- �w�U��t���ԁi�J�n�j
	 *     ,A.UKETUKEKIKAN_END = ?		-- �w�U��t���ԁi�I���j
	 *     ,A.SHINSAKIGEN = ?		-- �R������
	 *     ,A.TENPU_NAME = ?		-- �Y�t������
	 *     ,A.TENPU_WIN = ?			-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *     ,A.TENPU_MAC = ?			-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *     ,A.HYOKA_FILE_FLG = ?	-- �]���p�t�@�C���L��
	 *     ,A.HYOKA_FILE = ?		-- �]���p�t�@�C���i�[�t�H���_
	 *     ,A.KOKAI_FLG = ?			-- ���J�t���O
	 *     ,A.KESSAI_NO = ?			-- ���J���ٔԍ�
	 *     ,A.KOKAI_ID = ?			-- ���J�m���ID
	 *     ,A.HOKAN_DATE = ?		-- �f�[�^�ۊǓ�
	 *     ,A.YUKO_DATE = ?			-- �ۊǗL������
	 *     ,A.BIKO = ?				-- ���l
	 *     ,A.DEL_FLG = ?			-- �폜�t���O
	 *  WHERE
	 *     JIGYO_ID = ?				-- ����ID
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������addInfo�̕ϐ�jigyoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>NENDO</td><td>������addInfo�̕ϐ�nendo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KAISU</td><td>������addInfo�̕ϐ�kaisu</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_NAME</td><td>������addInfo�̕ϐ�jigyoName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_KUBUN</td><td>������addInfo�̕ϐ�jigyoKubun</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHINSA_KUBUN</td><td>������addInfo�̕ϐ�shinsaKubun</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTOKA_NAME</td><td>������addInfo�̕ϐ�tantokaName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TANTOKAKARI</td><td>������addInfo�̕ϐ�tantoKakari</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_NAME</td><td>������addInfo�̕ϐ�toiawaseName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_TEL</td><td>������addInfo�̕ϐ�toiawaseTel</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TOIAWASE_EMAIL</td><td>������addInfo�̕ϐ�toiawaseEmail</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>UKETUKEKIKAN_START</td><td>������addInfo�̕ϐ�uketukekikanStart</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>UKETUKEKIKAN_END</td><td>������addInfo�̕ϐ�uketukekikanEnd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHINSAKIGEN</td><td>������addInfo�̕ϐ�shinsaKigen</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_NAME</td><td>������addInfo�̕ϐ�tenpuName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_WIN</td><td>������addInfo�̕ϐ�tenpuWin</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TENPU_MAC</td><td>������addInfo�̕ϐ�tenpuMac</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HYOKA_FILE_FLG</td><td>������addInfo�̕ϐ�hyokaFileFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HYOKA_FILE</td><td>������addInfo�̕ϐ�hyokaFile</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_FLG</td><td>������addInfo�̕ϐ�kokaiFlg</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KESSAI_NO</td><td>������addInfo�̕ϐ�kessaiNo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_ID</td><td>������addInfo�̕ϐ�kokaiID</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>HOKAN_DATE</td><td>������addInfo�̕ϐ�hokanDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>YUKO_DATE</td><td>������addInfo�̕ϐ�YukoDate</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>BIKO</td><td>������addInfo�̕ϐ�Biko</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>DEL_FLG</td><td>0�@(�ʏ�)</td></tr>
	 * </table><br/>
	 * 
	 * ������ �o�C���h�ϐ�
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������addInfo�̕ϐ�jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param updateInfo	�X�V���(JigyokKanriInfo)
	 * @return �Ȃ�
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.JigyoKanriInfo)
	 */
	public void update(UserInfo userInfo, JigyoKanriInfo updateInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();			
			//-----�A�b�v���[�h�t�@�C���̏�������
			String jigyoId = updateInfo.getJigyoId();
			
			// 2005/04/24 �ǉ� �������� ----------------------------------------
			// ���R �X�V���A�t�@�C���폜�̂���
			
// 2005/04/25 �C�� �t�@�C�����̂̍폜�͕s�v
			//Win�t�@�C���폜
			if(updateInfo.isDelWinFileFlg()){
//				String path = updateInfo.getTenpuWin();
//				File winFile = new File(path);
//				FileUtil.delete(winFile);
				updateInfo.setTenpuWin(null);
			}
			//Mac�t�@�C���폜
			if(updateInfo.isDelMacFileFlg()){
//				String path = updateInfo.getTenpuMac();
//				File macFile = new File(path);
//				FileUtil.delete(macFile);
				updateInfo.setTenpuMac(null);
			}
			
			//Win�t�@�C���EMac�t�@�C���Ƃ��ɋ�̂Ƃ��A�Y�t����������ɂ���
			if(updateInfo.getTenpuWin() == null && updateInfo.getTenpuMac() == null){
				updateInfo.setTenpuName(null);
			}
			
			// �ǉ� �������� ----------------------------------------
			
			//---�Y�t�t�@�C���t�H���_�iWin�j
			FileResource tenpuWinFileRes = updateInfo.getTenpuWinFileRes();	
			if(tenpuWinFileRes != null){
				//�g���q�̎擾
				String extension = FileUtil.getExtention(tenpuWinFileRes.getName());
				//�t�@�C���p�X�𐶐�
				String[] pathInfo = new String[]{jigyoId, extension};
				String   tenpuWinPath  = MessageFormat.format(SHINSEI_TENPUWIN_FOLDER, pathInfo);
				File     tenpuWinFile   = new File(tenpuWinPath);			
				//�t�@�C���̏�������
				writeFile(tenpuWinFile, tenpuWinFileRes, true);
				//�t�@�C���p�X���Z�b�g
				updateInfo.setTenpuWin(tenpuWinPath);
				//-------�Y�t�t�@�C�������Z�b�g
				updateInfo.setTenpuName(tenpuWinFile.getName());				
			}
			
			//---�Y�t�t�@�C���t�H���_�iMac�j				
			FileResource tenpuMacFileRes = updateInfo.getTenpuMacFileRes();	
			if(tenpuMacFileRes != null){
				//�g���q�̎擾
				String extension = FileUtil.getExtention(tenpuMacFileRes.getName());
				//�t�@�C���p�X�𐶐�
				String[] pathInfo = new String[]{jigyoId, extension};
				String   tenpuMacPath  = MessageFormat.format(SHINSEI_TENPUMAC_FOLDER, pathInfo);
				File     tenpuMacFile   = new File(tenpuMacPath);			
				//�t�@�C���̏�������
				writeFile(tenpuMacFile, tenpuMacFileRes, true);
				//�t�@�C���p�X���Z�b�g
				updateInfo.setTenpuMac(tenpuMacPath);
				//-------�Y�t�t�@�C�������Z�b�g
				updateInfo.setTenpuName(tenpuMacFile.getName());				
			}
			
			//---�]���p�t�@�C���t�H���_			
			FileResource hyokaFileRes = updateInfo.getHyokaFileRes();	
			if(hyokaFileRes != null){
				//�g���q�̎擾
				String extension = FileUtil.getExtention(hyokaFileRes.getName());
				//�t�@�C���p�X�𐶐�
				String[] pathInfo = new String[]{jigyoId, extension};
				String   hyokaPath  = MessageFormat.format(SHINSEI_HYOKA_FOLDER, pathInfo);
				File     hyokaFile   = new File(hyokaPath);			
				//�t�@�C���̏�������
				writeFile(hyokaFile, hyokaFileRes, true);
				//�t�@�C���p�X���Z�b�g
				updateInfo.setHyokaFile(hyokaPath);
			}
	
			//---------------------------------------
			//���ƊǗ����̍X�V
			//---------------------------------------
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			dao.updateJigyoKanriInfo(connection, updateInfo);
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;	
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���ƊǗ��f�[�^�X�V����DB�G���[���������܂����B",
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
					"���ƊǗ��f�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	/**
	 * ���Ə��̌��J���̍X�V.<br/><br/>
	 * 
	 * �����̎��Ə��ɂ��āA���J�m������X�V����B<br/><br/>
	 * 
	 * <b>1.�p�X���[�h�m�F</b><br/><br/>
	 * ������userInfo����A�Ɩ��S���ҏ��̃p�X���[�h���擾����B<br/>
	 * ��l����password�Ɣ�r����B<br/>
	 * �@�p�X���[�h����v���Ȃ��Ƃ��A��O��throw����B<br/>
	 * �@�p�X���[�h����v�����Ƃ��A�X�V�������s���B<br/><br/>
	 * 
	 * <b>2.���Ə��̌��J�m������X�V</b><br/>
	 * �@1.�Ńp�X���[�h����v�����Ƃ��A���Ə��Ǘ��e�[�u�����X�V����B<br/><br/>
	 * �@�Y�����R�[�h�̌��J�t���O��1�ɂ���B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 *     JIGYOKANRI
	 * SET
	 *     KOKAI_FLG = ?	-- ���J�t���O�@(1�F���J 0�F����J(�K��l))
	 *     ,KESSAI_NO = ?	-- ���J���ٔԍ�
	 *     ,KOKAI_ID = ?	-- ���J�m���ID	
	 * WHERE
	 *     JIGYO_ID IN(	
	 *         ?		-- ����ID�@������jigyoPks��length�����J��Ԃ�
	 *     )
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_FLG</td><td>1�@(���J)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KESSAI_NO</td><td>��O����kessaiNo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KOKAI_ID</td><td>������userInfo�̕ϐ�gyomutantoId</td></tr>
	 * </table><br/>
	 * 
	 * ������ �o�C���h�ϐ�
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������jigyoPks�̕ϐ�jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo	UserInfo
	 * @param jigyoPks	����ID�z��(JigyoKanriPk[])
	 * @param kessaiNo	���J���ٔԍ�
	 * @param password	�p�X���[�h
	 * @return �Ȃ�
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#updateKokaiKakutei(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriInfo, java.lang.String)
	 */
	public void updateKokaiKakutei(UserInfo userInfo, JigyoKanriPk[] jigyoPks, String kessaiNo, String password) throws ApplicationException {
		
		//�p�X���[�h�����������ǂ������m�F
		String dbpassword = userInfo.getGyomutantoInfo().getPassword();
		
		if(!password.equals(dbpassword)){
			throw new ApplicationException(
				"�p�X���[�h���Ԉ���Ă��܂��B",
				new ErrorInfo("errors.2001", new String[]{"�p�X���[�h"}) );
		}
		
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//���ƊǗ����̍X�V
			//---------------------------------------
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			dao.updateKokaiKakutei(connection, jigyoPks, kessaiNo, userInfo.getGyomutantoInfo().getGyomutantoId());
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;	
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���ƊǗ��f�[�^�i���J�m��j�X�V����DB�G���[���������܂����B",
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
					"���ƊǗ��f�[�^�i���J�m��j�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}		
	}
	
	/**
	 * ���Ə��̍폜.<br><br>
	 * 
	 * ���Ə���_���폜����B<br><br>
	 * 
	 * �ȉ���SQL�����s���A���Ə��Ǘ��e�[�u��[JIGYOKANRI]����Ώۃ��R�[�h��_���폜����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * UPDATE
	 *     JIGYOKANRI
	 * SET
	 *     DEL_FLG = 1		-- �폜�t���O (1�F�폜�@0�F�ʏ�(�K��l))
	 * WHERE
	 *     JIGYO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������deleteInfo�̕ϐ�jigyoId</td></tr>
	 * </table><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param deleteInfo	�폜���(JigyoKanriInfo)
	 * @return �Ȃ�
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.JigyoKanriInfo)
	 */
	public void delete(UserInfo userInfo, JigyoKanriInfo deleteInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();			
			//---------------------------------------
			//���ƊǗ����̍폜
			//---------------------------------------
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			dao.deleteFlgJigyoKanriInfo(connection, deleteInfo);
			
			//---------------------------------------
			//�폜����I��
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���ƊǗ��f�[�^�폜����DB�G���[���������܂����B",
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
					"���ƊǗ��f�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
		//---------------------------------------
		//���ފǗ����̍폜
		//---------------------------------------
		ShoruiKanriPk shoruiPk = new ShoruiKanriPk();
		shoruiPk.setJigyoId(deleteInfo.getJigyoId());
		try{
			delete(userInfo, shoruiPk);
		}catch(NoDataFoundException e){
			//�����������Ȃ�
		}
		
	}
	
	/**
	 * ���Ə��̎擾.<br><br>
	 * 
	 * �P���̎��Ə����擾����B<br><br>
	 * 
	 * <b>1.���Ə��̎擾</b><br/>
	 * ���Ə��Ǘ��e�[�u�����������A�Y�����Ə���info�ɃZ�b�g����B<br/><br/>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- ����ID
	 *     ,A.NENDO					-- �N�x
	 *     ,A.KAISU					-- ��
	 *     ,A.JIGYO_NAME			-- ���Ɩ�
	 *     ,A.JIGYO_KUBUN			-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN			-- �R���敪
	 *     ,A.TANTOKA_NAME			-- �Ɩ��S����
	 *     ,A.TANTOKAKARI			-- �Ɩ��S���W��
	 *     ,A.TOIAWASE_NAME			-- �₢���킹��S���Җ�
	 *     ,A.TOIAWASE_TEL			-- �₢���킹��d�b�ԍ�
	 *     ,A.TOIAWASE_EMAIL		-- �₢���킹��E-mail
	 *     ,A.UKETUKEKIKAN_START	-- �w�U��t���ԁi�J�n�j
	 *     ,A.UKETUKEKIKAN_END		-- �w�U��t���ԁi�I���j
	 *     ,A.SHINSAKIGEN			-- �R������
	 *     ,A.TENPU_NAME			-- �Y�t������
	 *     ,A.TENPU_WIN				-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *     ,A.TENPU_MAC				-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *     ,A.HYOKA_FILE_FLG		-- �]���p�t�@�C���L��
	 *     ,A.HYOKA_FILE			-- �]���p�t�@�C���i�[�t�H���_
	 *     ,A.KOKAI_FLG				-- ���J�t���O
	 *     ,A.KESSAI_NO				-- ���J���ٔԍ�
	 *     ,A.KOKAI_ID				-- ���J�m���ID
	 *     ,A.HOKAN_DATE			-- �f�[�^�ۊǓ�
	 *     ,A.YUKO_DATE				-- �ۊǗL������
	 *     ,A.BIKO				-- ���l
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     JIGYO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������addInfo�̕ϐ�jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>2.�t�@�C�����̃Z�b�g</b><br/>
	 * FileUtil�N���X��getFileName()���\�b�h�Ăяo���B<br/>
	 * getFileName()���\�b�h�ŁA�p�X�̋�؂蕶��'\'��'/'�ɒu���A�t�@�C���p�X����t�@�C���������o�����������ԋp�B<br/>
	 * �ԋp�l��info�̕ϐ�hyokaName�ɃZ�b�g����B<br/><br/>
	 * 
	 * <b>3.info�ԋp</b><br/>
	 * info��ԋp����B
	 * 
	 * @param userInfo	UserInfo
	 * @param pkInfo	�������鎖�Ə���PK���(JigyoKanriPk)
	 * @return ���Ə��
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.JigyoKanriInfo)
	 */
	public JigyoKanriInfo select(UserInfo userInfo, JigyoKanriPk pkInfo)
		throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			JigyoKanriInfo info = dao.selectJigyoKanriInfo(connection, pkInfo);
			info.setHyokaName(FileUtil.getFileName(info.getHyokaFile()));	//�t�@�C�����𒊏o���ăZ�b�g����
			return info;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����@�֊Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	
	//ADD START 2007/07/23 BIS ����
	public RyoikiKeikakushoInfo ryoikiKeikakushoInfo(UserInfo userInfo,RyoikiKeikakushoInfo ryoikiKeikakushoInfo) throws ApplicationException
	{
		
		RyoikiKeikakushoInfo info =new RyoikiKeikakushoInfo();
		List errors=new ArrayList();
		if(null!=ryoikiKeikakushoInfo.getZennendoOuboNo()&&!"".equals(ryoikiKeikakushoInfo.getZennendoOuboNo().trim()))
		{
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			MasterRyouikiInfoDao masterRyoikidao = new MasterRyouikiInfoDao(userInfo);
			//JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			//RyoikiKeikakushoInfo info = dao.selectRyoikiKeikakushoInfo(connection, zennendoOuboNo);
			if(!checkAndSetRyoiki(connection,userInfo,ryoikiKeikakushoInfo,errors)){
				
		        throw new DataAccessException();
			}
//			�̈�����Z�b�g
	        Map ryoikiMap = new HashMap();
			info.setZennendoOuboFlg(ryoikiKeikakushoInfo.getZennendoOuboFlg());
			info.setZennendoOuboNo(ryoikiKeikakushoInfo.getZennendoOuboNo());
			info.setZennendoOuboRyoikiRyaku(ryoikiKeikakushoInfo.getZennendoOuboRyoikiRyaku());
			info.setZennendoOuboSettei(ryoikiKeikakushoInfo.getZennendoOuboSettei());
			return info;
		} catch (DataAccessException e) {
			String msg = "�ŏI�N�x�O�N�x�ɂ�����̈�ԍ�";
	        String property = "ryoikikeikakushoInfo.zennendoOuboNo";
			
			throw new ApplicationException(
				"���̈�ԍ����s���o�^��������DB�G���[���������܂����B",
				(ErrorInfo)errors.get(0),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		}
		else
		{
			RyoikiKeikakushoInfo result=new RyoikiKeikakushoInfo();
			result.setZennendoOuboNo("");
			result.setZennendoOuboRyoikiRyaku("");
			result.setZennendoOuboSettei("");
			result.setZennendoOuboFlg(ryoikiKeikakushoInfo.getZennendoOuboFlg());
			return result;
		}
		
	}
	/**
     * �����̈�ɂ���<br><br>
     * 
     * @param connection        Connection
     * @param userInfo          UserInfo
     * @param shinseiDataInfo   shinseiDataInfo
     * @return [true]�����̈���̃Z�b�g����
     *         [false]�̈�R�[�h���w�肳��Ă��Ȃ�/�̈�R�[�h���̈�}�X�^�e�[�u���ɑ��݂��Ȃ�
     * 
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    private boolean checkAndSetRyoiki(Connection connection, UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo, List errors) throws NoDataFoundException,
            ApplicationException {
        
        // �̈�ԍ��̎擾
        String ryoikiNo = ryoikikeikakushoInfo.getZennendoOuboNo();
        
        if(StringUtil.isBlank(ryoikiNo)){
            //�R�[�h����̏ꍇ�͏���������
            ryoikikeikakushoInfo.setZennendoOuboRyoikiRyaku(null);
            ryoikikeikakushoInfo.setZennendoOuboSettei(null);
            return false;
        }
        
        //�̈�}�X�^Dao�̐���
        MasterRyouikiInfoDao masterRyoikidao = new MasterRyouikiInfoDao(userInfo);
        
        String msg = "�ŏI�N�x�O�N�x�ɂ�����̈�ԍ�";
        String property = "ryoikikeikakushoInfo.zennendoOuboNo";
        
        try{
            if("0".equals(masterRyoikidao.selectRyoikiNoCount(connection, ryoikiNo))){
                errors.add(new ErrorInfo("errors.2001", new String[]{msg}, property));
                return false;
            }
        }catch(DataAccessException ex){
            throw new ApplicationException("�ŏI�N�x�O�N�x�ɂ�����̈�ԍ��̌������ɃG���[���������܂����B",new ErrorInfo("errors.4004"));
        }catch(NoDataFoundException ex){
            errors.add(new ErrorInfo("errors.2001", new String[]{msg}, property));
        }
        
        //�̈�����Z�b�g
        Map ryoikiMap = new HashMap();
        
        try{
            ryoikiMap = MasterRyouikiInfoDao.selectRecord(connection, ryoikiNo);
        }catch(DataAccessException ex){
            throw new ApplicationException("�̈��񌟍�����DB�G���[���������܂����B",new ErrorInfo("errors.4004"));
        }catch(NoDataFoundException ex){
            throw new NoDataFoundException("�Y�������񂪑��݂��܂���B", new ErrorInfo("errors.5002"), ex);
        }
        
        ryoikikeikakushoInfo.setZennendoOuboRyoikiRyaku((String)ryoikiMap.get("RYOIKI_RYAKU"));
        ryoikikeikakushoInfo.setZennendoOuboSettei((String)ryoikiMap.get("SETTEI_KIKAN"));
    
        return true;
    }
	//	ADD END 2007/07/23 BIS ����
    
	/**
	 * ���Ə��y�[�W���̎擾.<br><br>
	 * 
	 * ���ׂĂ̎��Ə��̃y�[�W�����擾����B<br><br>
	 * 
	 * �������œn���ꂽ���������Ɋ�Â��A���Ə��Ǘ��e�[�u������������B<br/><br/>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj<br/>
	 * �������ʂ�Page�I�u�W�F�N�g�Ɋi�[����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- ����ID
	 *     ,A.NENDO					-- �N�x
	 *     ,A.KAISU					-- ��
	 *     ,A.JIGYO_NAME			-- ���Ɩ�
	 *     ,A.JIGYO_KUBUN			-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN			-- �R���敪
	 *     ,A.TANTOKA_NAME			-- �Ɩ��S����
	 *     ,A.TANTOKAKARI			-- �Ɩ��S���W��
	 *     ,A.TOIAWASE_NAME			-- �₢���킹��S���Җ�
	 *     ,A.TOIAWASE_TEL			-- �₢���킹��d�b�ԍ�
	 *     ,A.TOIAWASE_EMAIL		-- �₢���킹��E-mail
	 *     ,A.UKETUKEKIKAN_START	-- �w�U��t���ԁi�J�n�j
	 *     ,A.UKETUKEKIKAN_END		-- �w�U��t���ԁi�I���j
	 *     ,A.SHINSAKIGEN			-- �R������
	 *     ,A.TENPU_NAME			-- �Y�t������
	 *     ,A.TENPU_WIN				-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *     ,A.TENPU_MAC				-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *     ,A.HYOKA_FILE_FLG		-- �]���p�t�@�C���L��
	 *     ,A.HYOKA_FILE			-- �]���p�t�@�C���i�[�t�H���_
	 *     ,A.KOKAI_FLG				-- ���J�t���O
	 *     ,A.KESSAI_NO				-- ���J���ٔԍ�
	 *     ,A.KOKAI_ID				-- ���J�m���ID
	 *     ,A.HOKAN_DATE			-- �f�[�^�ۊǓ�
	 *     ,A.YUKO_DATE				-- �ۊǗL������
	 *     ,A.BIKO				-- ���l
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 * 
	 * ORDER BY JIGYO_ID
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	��������(SearchInfo)
	 * @return ���Ə����i�[����Page�I�u�W�F�N�g
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuSearchInfo)
	 */
	public Page search(UserInfo userInfo, SearchInfo searchInfo)
		throws ApplicationException {

		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		String select = 
			"SELECT *  FROM JIGYOKANRI A WHERE DEL_FLG = 0";
			
		StringBuffer query = new StringBuffer(select);
		
		//�\�[�g���i����ID�̏����j
		query.append(" ORDER BY JIGYO_ID");		
		
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
			log.error("���ƊǗ��f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"���ƊǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);

		} finally {
			DatabaseUtil.closeConnection(connection);
		}

	}
	
	
	/**
	 * ���Ə��̌���.<br><br>
	 * 
	 * ���Ə�����������B<br><br>
	 * 
	 * �Ɩ��S���҈ȊO�́AselectJigyoInfoList(Connection connection) ��
	 * �������ʂ��Ԃ�B
	 * <li>�Ɩ��S���ҥ������������S�����鎖�Ƌ敪�ɊY�����鎖�ƃ��X�g</li>
	 * <li>�Ɩ��S���҈ȊO����S��</li><br/><br/>
	 * 
	 * <b>1.�����������w��</b><br/>
	 * ���[�U���Ɩ��S���҂̂Ƃ��A�S���敪���w�肷��B<br/>
	 * StringUtil�N���X��changeIterator2CSV()���\�b�h�ŁA�����񔽕��q��CSV�`���ŕԂ��B
	 * �ԋp�l��ϐ�tantoJigyoKubun�Ɋi�[�B<br><br>
	 * 
	 * <b>2.���Ə�������</b><br/>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- ����ID
	 *     ,A.NENDO"				-- �N�x
	 *     ,A.KAISU					-- ��
	 *     ,A.JIGYO_NAME			-- ���Ɩ�
	 *     ,A.JIGYO_KUBUN			-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN			-- �R���敪
	 *     ,A.TANTOKA_NAME			-- �Ɩ��S����
	 *     ,A.TANTOKAKARI			-- �Ɩ��S���W��
	 *     ,A.TOIAWASE_NAME			-- �₢���킹��S���Җ�
	 *     ,A.TOIAWASE_TEL			-- �₢���킹��d�b�ԍ�
	 *     ,A.TOIAWASE_EMAIL		-- �₢���킹��E-mail
	 *     ,A.UKETUKEKIKAN_START	-- �w�U��t���ԁi�J�n�j
	 *     ,A.UKETUKEKIKAN_END		-- �w�U��t���ԁi�I���j
	 *     ,A.SHINSAKIGEN			-- �R������
	 *     ,A.TENPU_NAME			-- �Y�t������
	 *     ,A.TENPU_WIN				-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *     ,A.TENPU_MAC				-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *     ,A.HYOKA_FILE_FLG		-- �]���p�t�@�C���L��
	 *     ,A.HYOKA_FILE			-- �]���p�t�@�C���i�[�t�H���_
	 *     ,A.KOKAI_FLG				-- ���J�t���O
	 *     ,A.KESSAI_NO				-- ���J���ٔԍ�
	 *     ,A.KOKAI_ID				-- ���J�m���ID
	 *     ,A.HOKAN_DATE			-- �f�[�^�ۊǓ�
	 *     ,A.YUKO_DATE				-- �ۊǗL������
	 *     ,A.BIKO			-- ���l
	 *     ,A.DEL_FLG				-- �폜�t���O
	 *     ,DECODE (
	 *         SIGN(TO_DATE(TO_CHAR(A.UKETUKEKIKAN_START, 'YYYY/MM/DD')
	 *              ,'YYYY/MM/DD') - TO_DATE(TO_CHAR(SYSDATE, 'YYYY/MM/DD'),'YYYY/MM/DD') )
	 *         ,0 , 'FALSE'		-- ���ݎ����Ɠ����ꍇ
	 *         ,1 , 'TRUE'		-- ���ݎ����̕����w�U��t���ԁi�J�n�j���O
	 *         ,-1, 'FALSE'		-- ���ݎ����̕����w�U��t���ԁi�J�n�j����
	 *     ) DELETE_BUTTON_FLAG	-- �w�U��t���ԁi�J�n�j���B�t���O
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 * 
	 * 		---���I��������1---
	 * 
	 * 		---���I��������2---
	 * 
	 * ORDER BY JIGYO_ID
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">���I��������1</span></b><br/>
	 * ���[�U�ɂ���Č������������I�ɕω�����B<br/>
	 * �Ɩ��S���҂̂Ƃ��A���������ǉ��B<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>tantoJigyoKubun</td><td>AND A.JIGYO_KUBUN IN  ('���Ƌ敪1', '���Ƌ敪', �c)</td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">���I��������2</span></b><br/>
	 * �������̔z��jigyoPks�̒l���ɂ���āA���������̃p�����^�������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoID</td><td>AND JIGYO_ID IN ('����ID1', '����ID2', �c)</td></tr>
	 * </table><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	��������(JigyoKanriSearchInfo)
	 * @return	���Ə����i�[����Page�I�u�W�F�N�g
	 * @throws ApplicationException	
	 */
	public Page search(UserInfo userInfo, JigyoKanriSearchInfo searchInfo)
		throws ApplicationException {

		//��������
		String addQuery = "";
		//�Ɩ��S���҂̏ꍇ�͎������S�����鎖�Ƌ敪�̂݁B-> ��Փ����ו������ꂽ���ߎ��ƃR�[�h�ɕύX
		if(userInfo.getRole().equals(UserRole.GYOMUTANTO)){
			//Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoKubun().iterator();
			//String tantoJigyoKubun = StringUtil.changeIterator2CSV(ite,true);
			//addQuery = new StringBuffer(" AND A.JIGYO_KUBUN IN (")
			//			 .append(tantoJigyoKubun)
			//			 .append(")")
			//			 .toString();
			Iterator ite = userInfo.getGyomutantoInfo().getTantoJigyoCd().iterator();
			String tantoJigyoCd = StringUtil.changeIterator2CSV(ite,true);
			addQuery = new StringBuffer(" AND SUBSTR(A.JIGYO_ID,3,5) IN (")
						 .append(tantoJigyoCd)
						 .append(")")
						 .toString();
		}

		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		String select = 		
			"SELECT "
				+ " A.JIGYO_ID"				//����ID
				+ ",A.NENDO"				//�N�x
				+ ",A.KAISU"				//��
				+ ",A.JIGYO_NAME"			//���Ɩ�
				+ ",A.JIGYO_KUBUN"			//���Ƌ敪
				+ ",A.SHINSA_KUBUN"			//�R���敪
				+ ",A.TANTOKA_NAME"			//�Ɩ��S����
				+ ",A.TANTOKAKARI"			//�Ɩ��S���W��
				+ ",A.TOIAWASE_NAME"		//�₢���킹��S���Җ�
				+ ",A.TOIAWASE_TEL"			//�₢���킹��d�b�ԍ�
				+ ",A.TOIAWASE_EMAIL"		//�₢���킹��E-mail
				+ ",A.UKETUKEKIKAN_START"	//�w�U��t���ԁi�J�n�j
				+ ",A.UKETUKEKIKAN_END"		//�w�U��t���ԁi�I���j
				//2005/04/25 �ǉ� ��������----------------------------------------------------
				//���R URL�̒ǉ��̂���
				+ ",A.URL_TITLE"			//URL(�^�C�g��)
				+ ",A.URL_ADDRESS"			//URL(�A�h���X)
				+ ",A.DL_URL"				//�_�E�����[�hURL
				//�ǉ� �����܂�---------------------------------------------------------------
				+ ",A.SHINSAKIGEN"			//�R������
				+ ",A.TENPU_NAME"			//�Y�t������
				+ ",A.TENPU_WIN"			//�Y�t�t�@�C���i�[�t�H���_�iWin�j
				+ ",A.TENPU_MAC"			//�Y�t�t�@�C���i�[�t�H���_�iMac�j
				+ ",A.HYOKA_FILE_FLG"		//�]���p�t�@�C���L��
				+ ",A.HYOKA_FILE"			//�]���p�t�@�C���i�[�t�H���_
				+ ",A.KOKAI_FLG"			//���J�t���O
				+ ",A.KESSAI_NO"			//���J���ٔԍ�
				+ ",A.KOKAI_ID"				//���J�m���ID
				+ ",A.HOKAN_DATE"			//�f�[�^�ۊǓ�
				+ ",A.YUKO_DATE"			//�ۊǗL������
				+ ",A.BIKO"					//���l
				+ ",A.DEL_FLG"				//�폜�t���O
				+ ",DECODE"
				+ " ("
				+ "  SIGN(TO_DATE(TO_CHAR(A.UKETUKEKIKAN_START, 'YYYY/MM/DD'),'YYYY/MM/DD') - TO_DATE(TO_CHAR(SYSDATE, 'YYYY/MM/DD'),'YYYY/MM/DD') )"
				+ "  ,0 , 'FALSE'"							//���ݎ����Ɠ����ꍇ
				+ "  ,1 , 'TRUE'"							//���ݎ����̕����w�U��t���ԁi�J�n�j���O
				+ "  ,-1, 'FALSE'"							//���ݎ����̕����w�U��t���ԁi�J�n�j����
				+ " ) DELETE_BUTTON_FLAG"					//�w�U��t���ԁi�J�n�j���B�t���O
				+ " FROM JIGYOKANRI A"
				+ " WHERE"
				+ "  DEL_FLG = 0 "
				+ addQuery
				;
			
		StringBuffer query = new StringBuffer(select);
		
		if(searchInfo.getJigyoPks() != null && searchInfo.getJigyoPks().length != 0){
			//����ID���Z�b�g
			JigyoKanriPk[] jigyoPks = searchInfo.getJigyoPks();		
			query.append(" AND JIGYO_ID IN (");
			String aSeparate = "";
			for (int i = 0; i < jigyoPks.length; i++) {
				query.append(aSeparate);
				query.append(EscapeUtil.toSqlString(jigyoPks[i].getJigyoId()));
				aSeparate = ",";
			}
			query.append(")");								
		}
		//�\�[�g���i����ID�̏����j
		query.append(" ORDER BY JIGYO_ID");		
		
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
			log.error("���ƊǗ��f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"���ƊǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);

		} finally {
			DatabaseUtil.closeConnection(connection);
		}

	}

	/**
	 * validate���\�b�h.<br><br>
	 * 
	 * <b>��d�o�^�`�F�b�N</b><br>
	 * �擾������0�łȂ��Ƃ��A��O��throw����B<br><br>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     COUNT(*)
	 * FROM
	 *     JIGYOKANRI
	 * WHERE
	 *     JIGYO_NAME = ?		-- ���Ɩ�
	 *     AND NENDO = ?"		-- �N�x
	 *     AND KAISU = ?		-- ��
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_NAME</td><td>������insertOrUpdateInfo�̕ϐ�jigyoName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>NENDO</td><td>������insertOrUpdateInfo�̕ϐ�nendo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>KAISU</td><td>������insertOrUpdateInfo�̕ϐ�kaisu</td></tr>
	 * </table><br><br>
	 * 
	 * @param userInfo				UserInfo
	 * @param insertOrUpdateInfo	JigyoKanriInfo
	 * @return ���Ə��
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriInfo)
	 */
	public JigyoKanriInfo validate(UserInfo userInfo, JigyoKanriInfo insertOrUpdateInfo, String mode) throws ApplicationException, ValidationException {
		
		if(mode.equals(IMaintenanceName.ADD_MODE)){
			
			//-----------------------
			// 2�d�o�^�`�F�b�N
			//-----------------------
			//���ƊǗ����e�[�u���ɂ��łɓ����u���Ɩ�+�N�x+�񐔁v���o�^����Ă��Ȃ����ǂ������m�F
			JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
			int count = 0;
			Connection connection = null;
			try {
				connection = DatabaseUtil.getConnection();
				//�G���[���ێ��p���X�g
				List errors = new ArrayList();				
				count = dao.countJigyoKanriInfo(connection, insertOrUpdateInfo);
	
				//���łɓo�^����Ă���ꍇ
				if(count > 0){				
					errors.add(
						new ErrorInfo("errors.4007", new String[] {"����"}));
					throw new ValidationException(
						"���łɎ��Ƃ��o�^����Ă��܂��B�����L�[�F" +
						"���Ɩ�'"+ insertOrUpdateInfo.getJigyoName() + "'"
						+ "�N�x'"+ insertOrUpdateInfo.getNendo() + "'"
						+ "��'"+ insertOrUpdateInfo.getKaisu() + "'",
						errors);
				}
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"���ƊǗ��f�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
			
//2004/08/30 takano �Ȍ��ł͈ȉ��̏d���`�F�b�N�͍s��Ȃ����߁A�R�����g�A�E�g�B
//			//-----------------------
//			// ���ƃ}�X�^���擾
//			//-----------------------
//			//���ƃ}�X�^���玖�ƃR�[�h���L�[�Ƃ��č����A�Ή��@�֖����擾
//			String select = 
//				"SELECT" +	" A.KUNI_NAME," 
//						  +	" A.TAIO_NAME " 
//						  +	" FROM MASTER_JIGYO A" ;
//			
//			StringBuffer query = new StringBuffer(select);
//			//���ƃR�[�h���Z�b�g		
//			query.append(" WHERE JIGYO_CD = '" + insertOrUpdateInfo.getJigyoCd() + "'");				
//		
//			if(log.isDebugEnabled()){
//				log.debug("query:" + query);
//			}
//
//			//------------------------
//		
//			connection = null;
//			try {
//				connection = DatabaseUtil.getConnection();
//				List list = SelectUtil.select(connection, query.toString());
//				Map datas = (Map)list.get(0);
//				insertOrUpdateInfo.setKuniName((String)datas.get("KUNI_NAME"));//����
//				insertOrUpdateInfo.setTaioName((String)datas.get("TAIO_NAME"));//�Ή��@�֖�
//			} catch (NoDataFoundException e) {
//				throw new SystemException(
//					"���ƃ}�X�^�ɊY������f�[�^������܂���B",
//					e);				
//			} catch (DataAccessException e) {
//				log.error("���ƃ}�X�^�f�[�^��������DB�G���[���������܂����B", e);
//				throw new ApplicationException(
//					"���ƃ}�X�^�f�[�^��������DB�G���[���������܂����B",
//					new ErrorInfo("errors.4004"),
//					e);
//
//			} finally {
//				DatabaseUtil.closeConnection(connection);
//			}

			
		}else if(mode.equals(IMaintenanceName.EDIT_MODE)){			
			//�Ȃɂ����Ȃ�
		}
		
		return insertOrUpdateInfo;
	}
	
	
	/**
	 * ���ޏ��̎擾.<br><br>
	 * 
	 * ���ޏ����擾����B<br><br>
	 * 
	 * ������pkInfo�Ɋ�Â��A���Ə��Ǘ��e�[�u���A���ފǗ��e�[�u������������B<br/>
	 * ���������͈ȉ��̒ʂ�B<br/><br/>
	 * 
	 * <b>1.���ފǗ����Z�b�g</b><br/>
	 * searchInfo�ɁA������pkInfo�̕ϐ�jigyoId���Z�b�g����B<br><br>
	 * ���N���X��select(UserInfo, JigyoKanriPk)���\�b�h���ĂсA���Ə����擾����B<br/>
	 * �����́A������userInfo��searchInfo��n���B<br/><br/>
	 * searchInfo�Ɏ擾�������Ə����i�[����B<br/><br/>
	 * 
	 * shoruiInfo�ցAsearchInfo�Ɋi�[���ꂽ����ID�A���Ɩ��A�N�x�A�񐔂��Z�b�g����B<br/><br/>
	 * 
	 * <b>2.���ރ��X�g�擾</b><br/>
	 * shoruiSearchInfo�ɁAsearchInfo�Ɋi�[���ꂽ����ID���Z�b�g����B<br/><br/>
	 * 
	 * ���N���X��search(UserInfo, ShoruiKanriSearchInfo)���\�b�h���ĂсA���ޏ����擾����B<br/>
	 * �����́A������userInfo��shoruiSearchInfo��n���B<br/><br/>
	 * shoruiKanriList�ɁA�擾�������ޏ����i�[����B<br/><br/>
	 * 
	 * <b>3.�擾���̕ԋp</b><br/>
	 * Map��shoruiInfo,shoruiKanriList���i�[���ĕԋp����B
	 * 
	 * @param userInfo	UserInfo
	 * @param pkInfo	�������鏑�ޏ���PK���(ShoruiKanriPk)
	 * @return ���ޏ��Map
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriPk)
	 */
	public Map select(UserInfo userInfo, ShoruiKanriPk pkInfo) throws ApplicationException {
		
		//-----------------------
		// ���ƊǗ����擾
		//-----------------------
		JigyoKanriInfo searchInfo = new JigyoKanriInfo();
		searchInfo.setJigyoId(pkInfo.getJigyoId());
		searchInfo = select(userInfo, searchInfo);
		
		//���ފǗ����Ɍ������ʂ��Z�b�g
		ShoruiKanriInfo shoruiInfo = new ShoruiKanriInfo();
		shoruiInfo.setJigyoId(searchInfo.getJigyoId());//����ID
		shoruiInfo.setJigyoName(searchInfo.getJigyoName());//���Ɩ�
		shoruiInfo.setNendo(searchInfo.getNendo());//�N�x	
		shoruiInfo.setKaisu(searchInfo.getKaisu());//��
		
		//-----------------------
		// ���ރ��X�g�擾
		//-----------------------
		ShoruiKanriSearchInfo shoruiSearchInfo = new ShoruiKanriSearchInfo();
		shoruiSearchInfo.setJigyoId(searchInfo.getJigyoId());
		List shoruiKanriList = search(userInfo, shoruiSearchInfo);
		
		//���ƊǗ���񃊃X�g�A���ފǗ���񃊃X�g��Map�Ɋi�[���ĕԂ�
		Map map = new HashMap();
		map.put(KEY_JIGYOKANRI_INFO, shoruiInfo);//���ƊǗ����
		map.put(KEY_SHORUIKANRI_LIST, shoruiKanriList);	//���ފǗ���񃊃X�g
		
		return map;
	}
	
	/**
	 * ���ޏ��̓o�^.<br><br>
	 * 
	 * ���ޏ���o�^����B<br><br>
	 * 
	 * <b>1.�V�X�e���ԍ��̔��s</b><br/>
	 * ���N���X��getSystemNumber()���\�b�h���ĂԁB<br/>
	 * ������addInfo�ɃZ�b�g����B<br/><br/>
	 * 
	 * <b>2.�A�b�v���[�h�t�@�C���̏�������</b><br/>
	 * (1)FileUtil�N���X��getExtention()���\�b�h�ŁA���ރt�@�C���̊g���q���擾����B<br/><br/>
	 * 
	 * (2)�t�@�C���p�X�𐶐�����B<br/>
	 * �@�z��pathInfo�ɁAaddInfo����擾��������ID�A�ΏہA�V�X�e���ԍ��A(1)�Ŏ擾�����t�@�C���g���q���i�[����B<br/><br/>
	 * �@�ϐ�SHINSEI_SHORUI_FOLDER�̃p�^�[���ɏ]���A�z��pathInfo��MessageFormat�N���X��format���\�b�h��p���ăt�H�[�}�b�g����B<br/>
	 * �@���������t�@�C���p�X�ŁA�t�@�C���𐶐�����B<br/><br/>
	 * 
	 * �@(��)���ރt�@�C���p�^�[���F${shinsei_path}/data/{0}/shorui/{1}/{2}.{3}<br/>
	 * �@�@�@�\���t�@�C���g���q�Fdoc�@�@����ID�F04000011�@�@�ΏہF2�@�@�V�X�e���ԍ��F20041122135506001�@�̂Ƃ�<br>
	 * �@�@�@�t�@�C���p�X�F${shinsei_path}/data/04000011/shorui/2/20041122135506001.doc<br>
	 * �@�@�@�@��${shinsei_path}�̒l��ApplicationSettings.properties�ɐݒ�<br><br>
	 * 
	 * (3)�t�@�C�����������ށB<br/>
	 * �@���N���X��writeFile()���\�b�h���ĂсA�t�@�C�����������ށB<br/><br/>
	 * 
	 * <b>3.���ޏ��̒ǉ�</b><br/>
	 * �d���`�F�b�N���A�f�[�^�����݂��Ȃ��Ƃ��o�^�������s���B<br/>
	 * �f�[�^�����݂���ꍇ�A��O��throw����B<br/><br/>
	 * 
	 * (1)�d���`�F�b�N<br/>
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID			-- ����ID
	 *     ,A.TAISHO_ID			-- �Ώ�
	 *     ,A.SYSTEM_NO			-- �V�X�e����t�ԍ�
	 *     ,A.SHORUI_FILE		-- �i�[��f�B���N�g��
	 *     ,A.SHORUI_NAME		-- ���ޖ�
	 *     ,A.DEL_FLG			-- �폜�t���O
	 * FROM
	 *     SHORUIKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 *     AND JIGYO_ID = ?
	 *     AND SYSTEM_NO = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������addInfo�̕ϐ�jigyoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SYSTEM_NO</td><td>1.�Ŏ擾�����V�X�e���ԍ�</td></tr>
	 * </table><br/><br/>
	 * 
	 * (2)���ޏ��̓o�^<br/>
	 * �@���ފǗ��e�[�u���ɓo�^����B<br/>
	 * �@(1)�̌��ʁA�Y���f�[�^�����݂��Ȃ��Ƃ��o�^�������s���B<br/><br/>
	 * 
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * INSERT INTO SHORUIKANRI (
	 *     JIGYO_ID			-- ����ID
	 *     ,TAISHO_ID		-- �Ώ�
	 *     ,SYSTEM_NO		-- �V�X�e����t�ԍ�
	 *     ,SHORUI_FILE		-- �i�[��f�B���N�g��
	 *     ,SHORUI_NAME		-- ���ޖ�
	 *     ,DEL_FLG)		-- �폜�t���O
	 * VALUES
	 *     (?,?,?,?,?,?)
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������addInfo�̕ϐ�jigyoCd</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>TAISHO_ID</td><td>������addInfo�̕ϐ�taishoId</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SYSTEM_NO</td><td>������addInfo�̕ϐ�systemNo</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHORUI_FILE</td><td>������addInfo�̕ϐ�shoruiFile</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>SHORUI_NAME</td><td>������addInfo�̕ϐ�shoruiName</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>DEL_FLG</td><td>0</td></tr>
	 * </table><br/><br/>
	 * 
	 * <b>4.���ރ��X�g�擾</b><br/>
	 * searchInfo��addInfo�̕ϐ�jigyoId���Z�b�g����B<br><br>
	 * ���N���X��search(UserInfo, ShoruiKanriSearchInfo)���\�b�h���ĂсA���ޏ�񃊃X�g���擾����B<br>
	 * �����́A������userInfo��searchInfo��n���B
	 * 
	 * @param userInfo	UserInfo
	 * @param addInfo	�o�^���(ShoruiKanriInfo)
	 * @return ���ޏ�񃊃X�g
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriInfo)
	 */
	public List insert(UserInfo userInfo, ShoruiKanriInfo addInfo) throws ApplicationException {
		
		boolean success = false;
		Connection connection = null;
		try {				
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�V�X�e���ԍ��̔��s
			//---------------------------------------
			addInfo.setSystemNo(getSystemNumber());
			String jigyoId = addInfo.getJigyoId();//����ID	
			String taishoId = addInfo.getTaishoId();//�Ώ�
			String systemNo = addInfo.getSystemNo();//�V�X�e����t�ԍ�
							
			//-----�A�b�v���[�h�t�@�C���̏�������
			//---���ރt�@�C��
			FileResource shoruiFileRes = addInfo.getShoruiFileRes();	
			if(shoruiFileRes != null){
				//�g���q�̎擾
				String extension = FileUtil.getExtention(shoruiFileRes.getName());
				//�t�@�C���p�X�𐶐�
				String[] pathInfo = new String[]{jigyoId, taishoId, systemNo, extension};
				String   shoruiPath  = MessageFormat.format(SHINSEI_SHORUI_FOLDER, pathInfo);
				File     shoruiFile   = new File(shoruiPath);			
				//�t�@�C���̏�������
				writeFile(shoruiFile, shoruiFileRes, false);
				//�t�@�C���p�X���Z�b�g
				addInfo.setShoruiFile(shoruiPath);
			}	
			
			//---------------------------------------
			//���ޏ��̒ǉ�
			//---------------------------------------
			ShoruiKanriInfoDao dao = new ShoruiKanriInfoDao(userInfo);
			dao.insertShoruiKanriInfo(connection,addInfo);		

			//---------------------------------------
			//�o�^����I��
			//---------------------------------------
			success = true;
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���ފǗ��f�[�^�o�^����DB�G���[���������܂����B",
				new ErrorInfo("errors.4001"),
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
					"���ފǗ��f�[�^�o�^����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
		
		//-----------------------
		// ���ރ��X�g�擾
		//-----------------------
		String jigyoId = addInfo.getJigyoId();
		//�����p�L�[�̎���ID���ăZ�b�g	
		ShoruiKanriSearchInfo searchInfo = new ShoruiKanriSearchInfo();
		searchInfo.setJigyoId(jigyoId);
		List shoruiKanriList = search(userInfo, searchInfo);

		return shoruiKanriList;
	}
	
	/**
	 * ���ޏ��̎擾.<br><br>
	 * 
	 * ���ޏ����擾����B<br><br>
	 * 
	 * <b>1.���ޏ��̎擾</b><br>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID, 			-- ����ID
	 *     A.TAISHO_ID,			-- �Ώ�
	 *     A.SYSTEM_NO,			-- �V�X�e����t�ԍ�
	 *     A.SHORUI_FILE, 			-- �i�[��f�B���N�g��
	 *     A.SHORUI_NAME,			-- ���ޖ�
	 *     A.DEL_FLG			-- �폜�t���O
	 * FROM
	 *     SHORUIKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 * 
	 * 	--- ���I��������1 ---
	 * 
	 * ORDER BY SYSTEM_NO
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">���I��������1</span></b><br/>
	 * ���[�U�ɂ���Č������������I�ɕω�����B<br/>
	 * �Ɩ��S���҂̂Ƃ��A���������ǉ��B<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoId</td><td>AND A.JIGYO_ID ='����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�Ώ�</td><td>taishoId</td><td>AND A.TAISHO_ID ='�Ώ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>systemNo</td><td>AND A.SYSYEM_NO ='�V�X�e���ԍ�'</td></tr>
 	 * </table><br>
 	 * 
 	 * shoruiKanriList�Ɏ擾�����i�[<br><br>
 	 * 
	 * <b>2.���ރt�@�C�����̃Z�b�g</b><br>
	 * ���ʃ��X�g��Iterator�^�̕ϐ�iterator�Ɋi�[����B<br><br>
	 * 
	 * FileUtil�N���X��getFileName()���\�b�h�Ăяo���B<br>
	 * getFileName()���\�b�h�ŁA�p�X�̋�؂蕶��'\'��'/'�ɒu���A�t�@�C���p�X����t�@�C���������o�����������ԋp�B<br>
	 * �ԋp���ꂽ�l��Map�ɃZ�b�g���AMap��List�ϐ�newList��add����B<br>
	 * iterator�̗v�f���Ȃ��Ȃ�܂ŌJ��Ԃ��B<br><br>
	 * 
	 * shoruiKanriList��newList��������B<br><br>
 	 * 
 	 * <b>3.���ޏ�񃊃X�g�̕ԋp</b><br>
 	 * shoruiKanriList��ԋp����B
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	��������(ShoruiKanriSearchInfo)
	 * @return ���ޏ�񃊃X�g
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriSearchInfo)
	 */
	public List search(UserInfo userInfo, ShoruiKanriSearchInfo searchInfo) throws ApplicationException {
		
		String select = 
			"SELECT *  FROM SHORUIKANRI A WHERE DEL_FLG = 0";	
			
		StringBuffer  query = new StringBuffer(select);

		if(searchInfo.getJigyoId() != null && searchInfo.getJigyoId().length() != 0){
			query.append(" AND A.JIGYO_ID ='" + EscapeUtil.toSqlString(searchInfo.getJigyoId()) + "'");		//����ID
		}
		if(searchInfo.getTaishoId() != null && searchInfo.getTaishoId().length() != 0){
			query.append(" AND A.TAISHO_ID ='" + EscapeUtil.toSqlString(searchInfo.getTaishoId()) + "'");		//�Ώ�
		}
		if(searchInfo.getSystemNo() != null && searchInfo.getSystemNo().length() != 0){
			query.append(" AND A.SYSYEM_NO ='" + EscapeUtil.toSqlString(searchInfo.getSystemNo()) + "'");		//�V�X�e����t�ԍ�
		}
				
		//�\�[�g���i�V�X�e���ԍ��̏����j
		query.append(" ORDER BY SYSTEM_NO");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		//------------------------
		List shoruiKanriList = null;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			shoruiKanriList = SelectUtil.select(connection, query.toString());
			
			//���ރt�@�C������t�@�C�������擾���ă}�b�v�ɃZ�b�g
			if(shoruiKanriList != null){
				List newList = new ArrayList();
				Iterator iterator = shoruiKanriList.iterator();
				while(iterator.hasNext()){
					Map shoruiMap = (Map)iterator.next();
					String shoruiFile = (String) shoruiMap.get("SHORUI_FILE");
					String filename = FileUtil.getFileName(shoruiFile);
					shoruiMap.put("FILE_NAME", filename);
					newList.add(shoruiMap);
				}
				shoruiKanriList = newList;
			}

		} catch (NoDataFoundException e) {
			//�Ȃɂ��������Ȃ�
		} catch (DataAccessException e) {
			log.error("���ފǗ��f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"���ފǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}	
		return shoruiKanriList;
	}
	

	/**
	 * ���Ə��̃y�[�W���̎擾.<br><br>
	 * 
	 * ��t���ԓ��̎��Ə��̃y�[�W�����擾����B<br><br>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID,				-- ����ID
	 *     A.JIGYO_NAME,			-- ���Ɩ�
	 *     A.NENDO,					-- �N�x
	 *     TO_CHAR(A.KAISU) KAISU,	-- ��
	 *     A.UKETUKEKIKAN_END,		-- ���Ǝ�t���ԁi�I�����j
	 *     A.TENPU_WIN,				-- ���Ǝ�t���ԁi�I�����j
	 *     A.TENPU_MAC				-- ���Ǝ�t���ԁi�I�����j
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 *     AND TO_DATE( TO_CHAR(SYSDATE,'YYYY/MM/DD')
	 *              , 'YYYY/MM/DD' ) >= UKETUKEKIKAN_START	-- ��t���ԓ��ł��邱�Ƃ��m�F
	 *          AND TO_DATE( TO_CHAR(SYSDATE,'YYYY/MM/DD')
	 *              , 'YYYY/MM/DD' ) <= UKETUKEKIKAN_END
	 * 
	 * --- <b><span style="color:#002288">���I��������1</span></b> ---
	 * 
	 * --- <b><span style="color:#008822">���I��������2</span></b> ---
	 * 
	 * ORDER BY JIGYO_ID
     * </pre>
     * </td></tr>
	 * </table>
	 * <br/>
	 * <b><span style="color:#002288">���I��������1</span></b><br/>
	 * ���[�U�ɂ���Č������������I�ɕω�����B<br/>
	 * �\���҂�����匠�����Ȃ��Ƃ��A���������ǉ��B<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>HIKOBO_JIGYO_KUBUN</td><td>AND JIGYO_KUBUN NOT IN  ('���Ƌ敪')</td></tr>
	 * </table>
	 * <br/>
	 * <b><span style="color:#008822">���I��������2</span></b><br/>
	 * ��������JigyoKanriSearchInfo�ł��AjigyoKubun��null�������͋�̂ł͂Ȃ��ꍇ�ɉ��L�i��������ǉ�����B<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>jigyoKubun</td><td>AND JIGYO_KUBUN IN  ('���Ƌ敪1','���Ƌ敪2'�c)</td></tr>
	 * </table>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	��������(SearchInfo)
	 * @return ���Ə����i�[����Page�I�u�W�F�N�g
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriInfo)
	 */
	public Page searchUketukeJigyo(UserInfo userInfo, 
									SearchInfo searchInfo) 
		throws ApplicationException
	{
		//�ǉ���������
		String hikoboQuery = "";
		
//		2005/04/22 �ǉ� ��������----------
//		���R:���L�ǉ������g�p���邽��
		
//		//�\���҂̏ꍇ�́A����匠�������邩�ǂ����`�F�b�N����B
//		if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
//			//�\���҂̔���匠���������ꍇ �� ����������ǉ��i����厖�Ƌ敪�������j
//			ShinseishaInfo shinseishaInfo = userInfo.getShinseishaInfo();
//			if(!"1".equals(shinseishaInfo.getHikoboFlg())){
//				hikoboQuery = new StringBuffer()
//							  .append(" AND JIGYO_KUBUN NOT IN (")
//							  .append(StringUtil.changeArray2CSV(HIKOBO_JIGYO_KUBUN, true))
//							  .append(")")
//							  .toString();
//			}
//		}
		
//		2005/04/22 �ǉ� �����܂�----------
		
		String[] jigyoKubun = null;
		//2005/03/24 �ǉ� ------------------------------------------------��������
		//���R ��Վ��ƂƂ��̑��̈ꗗ��ʁX�̉�ʂƂ��ĕ\�����邽��
		if( searchInfo!=null && searchInfo instanceof JigyoKanriSearchInfo){
			jigyoKubun=((JigyoKanriSearchInfo)searchInfo).getJigyoKubun();
			if(jigyoKubun!=null&&jigyoKubun.length>0){
				hikoboQuery = new StringBuffer()
				  .append(" AND JIGYO_KUBUN IN (")
				  .append(StringUtil.changeArray2CSV(jigyoKubun, true))
				  .append(")")
				  .toString();
			}
		}
        
//2006/06/21 �c�@�ǉ���������
        //���R 2006/06/21�̉���҃��j���[�̕ύX���邱��
        if (searchInfo != null && searchInfo instanceof JigyoKanriSearchInfo) {
            String jigyoCds = ((JigyoKanriSearchInfo) searchInfo).getJigyoCds();
            if(!StringUtil.isBlank(jigyoCds)){
//2006/07/09 �����@�ύX��������
                hikoboQuery = new StringBuffer()
                  .append(" AND JIGYO_KUBUN IN (")
                  .append(StringUtil.changeArray2CSV(jigyoKubun, true))
                  .append(")")
                  .append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (")
                  .append(StringUtil.changeArray2CSV(jigyoCds.split(","),true))
                  .append(")")
                  .toString();
//2006/07/09 �����@�ύX�����܂�
            }
        }
//2006/06/21 �c�@�ǉ������܂�        
		//2005/03/24 �ǉ� ------------------------------------------------�����܂�
		//       2006/06/15 �����@�ǉ���������
        //      ���R �ǉ����ځuKARIRYOIKINO_UKETUKEKIKAN_END�v�̂���
		String select = 
		"SELECT A.JIGYO_ID," 				//����ID
			 + " A.JIGYO_NAME,"				//���Ɩ�
			 + " A.NENDO,"					//�N�x
			 + " TO_CHAR(A.KAISU) KAISU,"	//��
			 + " A.UKETUKEKIKAN_END,"		//���Ǝ�t���ԁi�I�����j
			 + " A.TENPU_WIN,"				//������e�t�@�C���iWin)
			 + " A.TENPU_MAC,"				//������e�t�@�C���iMac)
			 + " A.URL_TITLE,"				//URL�^�C�g��
			 + " A.URL_ADDRESS,"			//URL�A�h���X
			 + " A.DL_URL,"				    //�����_�E�����[�hURL
             + " DECODE (SIGN(TO_DATE"
             + "( TO_CHAR(A.KARIRYOIKINO_UKETUKEKIKAN_END,"
             + "'YYYY/MM/DD'), 'YYYY/MM/DD' )- "
             + "TO_DATE( TO_CHAR(SYSDATE ,'YYYY/MM/DD'), 'YYYY/MM/DD' )),0 ,"
             + "'TRUE',1,'TRUE',-1,'FALSE') KARIRYOIKINO_FLAG ,"
             + " A.KARIRYOIKINO_UKETUKEKIKAN_END," //���̈�ԍ���t���ԁi�I���j
             + " DECODE (SIGN(TO_DATE"
             + "( TO_CHAR(A.RYOIKI_KAKUTEIKIKAN_END,"
             + "'YYYY/MM/DD'), 'YYYY/MM/DD' )- "
             + "TO_DATE( TO_CHAR(SYSDATE ,'YYYY/MM/DD'), 'YYYY/MM/DD' )),0 ,"
             + "'TRUE',1,'TRUE',-1,'FALSE') RYOIKINO_FLAG ,"
             + " A.RYOIKI_KAKUTEIKIKAN_END"; //�̈��\�Ҋm����ؓ�   
// 2006/06/15 �����@�ǉ������܂�
			//2005/04/26 �ǉ� ��������-----------------------------------
			//��t��������ڈꗗ�Łu�����@�֎�t���؁v�ƕ\�����邽��
// 20050708 ����̈��ǉ�
//			if(jigyoKubun != null && jigyoKubun.length>0 && jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
//				select += ", CHCK.JOKYO_ID";
//			}	 
		if(jigyoKubun != null && 
			jigyoKubun.length>0 && 
			(jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_KIBAN) || jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI) ||
//2006/03/07 �ǉ���������					
			 jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART) || jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)
//�c�@�ǉ������܂�
			)){
			select += ", CHCK.JOKYO_ID";
		}	 
// Horikoshi
			//�ǉ� �����܂�----------------------------------------------
			
		select += " FROM JIGYOKANRI A";
			 
// 20050708 ����̈�̒ǉ�
//			 //2005/04/26 �ǉ� ��������-----------------------------------
//			 //��t��������ڈꗗ�Łu�����@�֎�t���؁v�ƕ\�����邽��
//			 if(jigyoKubun != null && jigyoKubun.length>0 && jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
//			 	select = select + " LEFT JOIN CHCKLISTINFO CHCK "
//			 					+ " ON A.JIGYO_ID = CHCK.JIGYO_ID"
//			 					+ " AND CHCK.SHOZOKU_CD = "+ userInfo.getShinseishaInfo().getShozokuCd();
//			 }
//			 //�ǉ� �����܂�----------------------------------------------
		 if(jigyoKubun != null && 
		 	jigyoKubun.length>0 && 
			(jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_KIBAN) || jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_TOKUTEI) ||
//2006/03/07 �ǉ���������					
			 jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_WAKATESTART) || jigyoKubun[0].equals(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)
//�c�@�ǉ������܂�			
		     )){
		 	select = select + " LEFT JOIN CHCKLISTINFO CHCK "
		 					+ " ON A.JIGYO_ID = CHCK.JIGYO_ID"
		 					+ " AND CHCK.SHOZOKU_CD = "+ EscapeUtil.toSqlString(userInfo.getShinseishaInfo().getShozokuCd());
		 }
// Horikoshi

			 select = select + " WHERE DEL_FLG = 0"
			 + " AND "						//��t���ԓ��ł��邱�Ƃ��m�F
			 + "  TO_DATE( TO_CHAR(SYSDATE,'YYYY/MM/DD'), 'YYYY/MM/DD' ) >= UKETUKEKIKAN_START"		
			 + " AND "
			 + "  TO_DATE( TO_CHAR(SYSDATE,'YYYY/MM/DD'), 'YYYY/MM/DD' ) <= UKETUKEKIKAN_END"		
			 + hikoboQuery
			 ;
		StringBuffer  query = new StringBuffer(select);
		
		//�\�[�g���i����ID�̏����j
		query.append(" ORDER BY JIGYO_ID");
		
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
			log.error("���ƊǗ��f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"���ƊǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);

		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * ���ޏ��̍폜.<br><br>
	 * 
	 * ���ޏ����폜����B<br><br>
	 * 
	 * <b>1.���ޏ��̎擾</b><br>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID			-- ����ID
	 *     ,A.TAISHO_ID			-- �Ώ�
	 *     ,A.SYSTEM_NO			-- �V�X�e����t�ԍ�
	 *     ,A.SHORUI_FILE		-- �i�[��f�B���N�g��
	 *     ,A.SHORUI_NAME		-- ���ޖ�
	 *     ,A.DEL_FLG			-- �폜�t���O
	 * FROM
	 *     SHORUIKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 * 
	 * 	--- ���I��������1 ---
	 * 
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">���I��������1</span></b><br/>
	 * pkInfo�ɂ���Č������������I�ɕω�����B<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>jigyoId</td><td>AND JIGYO_ID = '����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>systemNo</td><td>AND SYSTEM_NO = '�V�X�e���ԍ�'</td></tr>
	 * </table><br><br>
	 * 
	 * <b>2.���ޏ��̍폜</b><br>
	 * 
	 * �ȉ���SQL�����s���A���ފǗ��e�[�u��[SHORUIKANRI]����Ώۃ��R�[�h��_���폜����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * 
	 * UPDATE
	 *     SHORUIKANRI
	 * SET
	 *     DEL_FLG = 1		-- �폜�t���O
	 * 
	 * 	--- ���I��������1 ---
	 * 
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">���I��������1</span></b><br/>
	 * pkInfo�ɂ���Č������������I�ɕω�����B<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>jigyoId</td><td>WHERE JIGYO_ID = '����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>systemNo</td><td>WHERE SYSTEM_NO = '�V�X�e���ԍ�'</td></tr>
	 * </table><br><br>
	 * 
	 * <b>3.���ރt�@�C�����̕ύX</b><br>
	 * 1.�Ŏ擾�������Ə��̏��ރt�@�C���p�X��null�łȂ��Ƃ��A
	 * File�N���X��renameTo()���\�b�h�Ńt�@�C������"_delete"��t���������O�ɕύX����B<br>
	 * (��)20041122135506001.xls�@���@20041122135506001_delete.xls
	 * 
	 * @param userInfo	UserInfo
	 * @param pkIfo		�폜���鏑�ޏ���PK���(ShoruiKanriPk)
	 * @return �Ȃ�
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriPk)
	 */
	public void delete(UserInfo userInfo, ShoruiKanriPk pkInfo) throws ApplicationException {
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();			
			//---------------------------------------
			//���ފǗ����̍X�V
			//---------------------------------------
			ShoruiKanriInfoDao dao = new ShoruiKanriInfoDao(userInfo);
			ShoruiKanriInfo selectInfo = dao.selectShoruiKanriInfo(connection, pkInfo);
			String shoruiPath = selectInfo.getShoruiFile();
			dao.deleteFlgShoruiKanriInfo(connection, pkInfo);
			
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;

			//---------------------------------------
			//���ރt�@�C���̃t�@�C������ύX
			//---------------------------------------
			if(shoruiPath != null){
				if(new File(shoruiPath).exists()){
					new File(shoruiPath).renameTo(new File(shoruiPath + "_delete"));
				}
			}

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���ފǗ��f�[�^�폜����DB�G���[���������܂����B",
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
					"���ފǗ��f�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}	
		
	}
	
	/**
	 * ���ޏ��̍폜.<br><br>
	 * 
	 * �V�X�e���ԍ����L�[�ɏ��ޏ���_���폜����B<br><br>
	 * 
	 * <b>1.���ޏ����폜</b><br>
	 * shoruiKanriPk�Ɉ���deleteInfo�̕ϐ�systemNo�̒l���Z�b�g����B<br>
	 * ������userInfo��shoruiPk�������Ɏ��N���X��delete(UserInfo, ShoruiKanriPk)���\�b�h���ĂсA���ޏ���_���폜����B<br><br>
	 * 
	 * <b>2.���ރ��X�g�擾</b><br>
	 * searchInfo�Ɉ���deleteInfo�̕ϐ�jigyoId�̒l���Z�b�g����B<br>
	 * ������userInfo��searchInfo�������Ɏ��N���X��search(UserInfo, ShoruiKanriSearchInfo)���ĂсA���ރ��X�g���擾����B<br><br>
	 * 
	 * @param userInfo		UserInfo
	 * @param deleteInfo	ShoruiKanriInfo
	 * @return ���ޏ��̃��X�g
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriInfo)
	 */
	public List delete(UserInfo userInfo, ShoruiKanriInfo deleteInfo) throws ApplicationException {
		
		//-----------------------
		// ���ފǗ����폜
		//-----------------------
		ShoruiKanriPk shoruiPk = new ShoruiKanriPk();
		shoruiPk.setSystemNo(deleteInfo.getSystemNo());
		delete(userInfo, shoruiPk);
		
		//-----------------------
		// ���ރ��X�g�擾
		//-----------------------
		String jigyoId = deleteInfo.getJigyoId();
		//�����p�L�[�̎���ID���ăZ�b�g	
		ShoruiKanriSearchInfo searchInfo = new ShoruiKanriSearchInfo();
		searchInfo.setJigyoId(jigyoId);
		List shoruiKanriList = search(userInfo, searchInfo);
		
		return shoruiKanriList;
	}
	
	/**
	 * ���ޏ��̎擾.<br><br>
	 * 
	 * ���ޏ����擾����B<br><br>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID			-- ����ID
	 *     ,A.TAISHO_ID			-- �Ώ�
	 *     ,A.SYSTEM_NO			-- �V�X�e����t�ԍ�
	 *     ,A.SHORUI_FILE		-- �i�[��f�B���N�g��
	 *     ,A.SHORUI_NAME		-- ���ޖ�
	 *     ,A.DEL_FLG			-- �폜�t���O
	 * FROM
	 *     SHORUIKANRI A
	 * WHERE
	 *     DEL_FLG = 0
	 * 
	 * 	--- ���I��������1 ---
	 * 
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">���I��������1</span></b><br/>
	 * pkInfo�ɂ���Č������������I�ɕω�����B<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>jigyoId</td><td>AND JIGYO_ID = '����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>systemNo</td><td>AND SYSTEM_NO = '�V�X�e���ԍ�'</td></tr>
	 * </table>
	 * 
	 * @param userInfo	UserInfo
	 * @param pkInfo	ShoruiKanriPk
	 * @return ���ޏ��
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#selectShoruiInfo(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriPk)
	 */
	public ShoruiKanriInfo selectShoruiInfo(UserInfo userInfo, ShoruiKanriPk pkInfo) throws ApplicationException {
		
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			ShoruiKanriInfoDao dao = new ShoruiKanriInfoDao(userInfo);
			return dao.selectShoruiKanriInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���ފǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * ���Ə��t�@�C�����\�[�X�̎擾.<br><br>
	 * 
	 * ���Ə��t�@�C�����\�[�X���擾����B<br><br>
	 * 
	 * <b>1.���Ə����擾</b><br>
	 * ������jigyoPk�̕ϐ�jigyoId���Z�b�g����Ă���Ƃ��A���Ə�����������B<br>
	 * ������userInfo�Ƒ�����jigyoPk�������Ɏ��N���X��select(UserInfo, JigyoKanriPk)���ĂсA
	 * ���Ə����擾����B<br><br>
	 * 
	 * <b>2.�t�@�C���p�X���擾</b><br>
	 * ��O����fileFlg�����ƂɃt�@�C���p�X���擾����B<br>
	 * fileFlg�̒l�ɂ���āAjigyoInfo��tenpuWin�AtenpuMac�AhyokaFile���擾��filePath�Ɋi�[����B<br><br>
	 * 
	 * <b>3.�t�@�C�����\�[�X���擾</b><br>
	 * filePath��null�܂��͋󔒂̂Ƃ��A��O��throw����B<br><br>
	 * 
	 * FileUtil�N���X��readFile()�ɂāA�t�@�C�����\�[�X���擾����B
	 * 
	 * @param userInfo	UserInfo
	 * @param jigyoPk	JigyoKanriPk
	 * @param fileFlg	�t�@�C���t���O(String)
	 * @return ���Ə��t�@�C�����\�[�X
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#getJigyoKanriFileRes(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriPk)
	 */
	public FileResource getJigyoKanriFileRes(UserInfo userInfo, JigyoKanriPk jigyoPk, String fileFlg) throws ApplicationException {

		FileResource fileRes = null;
		JigyoKanriInfo jigyoInfo = null;
		
		//id���Z�b�g����Ă���ꍇ�́A���ƊǗ�������������
		if(jigyoPk.getJigyoId() != null && !jigyoPk.equals("")){
			try{
				jigyoInfo = select(userInfo, jigyoPk);
			}catch(ApplicationException e){
				throw new FileIOException(
				"���ƊǗ��f�[�^��������DB�G���[���������܂����B");				
			}			
		}
	
		//�t�@�C���t���O�����ƂɃt�@�C���p�X���擾
		String filePath = null;
		if(fileFlg.equals(FILE_FLG_TENPU_WIN)){
			filePath = jigyoInfo.getTenpuWin();		//�Y�t�t�@�C���iWin�j
		}else if(fileFlg.equals(FILE_FLG_TENPU_MAC)){
			filePath = jigyoInfo.getTenpuMac();		//�Y�t�t�@�C���iMac�j			
		}else if(fileFlg.equals(FILE_FLG_HYOKA)){
			filePath = jigyoInfo.getHyokaFile();	//�]���p�t�@�C��					
		}
		
		if(filePath == null || filePath.equals("")){
			throw new FileIOException(
				"�t�@�C���p�X���s���ł��B�t�@�C���p�X'" + filePath + "'");			
		}
		try{
			File file = new File(filePath);
			fileRes = FileUtil.readFile(file);
		}catch(FileNotFoundException e){
			throw new FileIOException(
				"�t�@�C����������܂���ł����B",			
				e);
		}catch(IOException e){
			throw new FileIOException(
				"�t�@�C���̓��o�͒��ɃG���[���������܂����B",
				e);
		}
		return fileRes;

	}
	
	/**
	 * ���ޏ��t�@�C�����\�[�X�̎擾.<br><br>
	 * 
	 * ���ޏ��t�@�C�����\�[�X���擾����B<br><br>
	 * 
	 * <b>1.���ޏ����擾</b><br>
	 * ������userInfo�Ƒ�����shoruiPk�������Ɏ��N���X��selectShoruiInfo(UserInfo, shoruiKanriPk)���ĂсA
	 * ���ޏ����擾����B<br><br>
	 * 
	 * <b>2.�t�@�C�����\�[�X���擾</b><br>
	 * filePath��shoruiInfo�̕ϐ�shoruiFile���i�[����B<br>
	 * filePath��null�܂��͋󔒂̂Ƃ��A��O��throw����B<br><br>
	 * 
	 * FileUtil�N���X��readFile()�ɂāA�t�@�C�����\�[�X���擾����B
	 * 
	 * @param userInfo	UserInfo
	 * @param shoruiPk	ShoruiKanriPk
	 * @return ���ޏ��t�@�C�����\�[�X
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#getShoruiKanriFileRes(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriPk)
	 */
	public FileResource getShoruiKanriFileRes(UserInfo userInfo, ShoruiKanriPk shoruiPk) throws ApplicationException {

			FileResource fileRes = null;
			ShoruiKanriInfo shoruiInfo = null;
		
			JigyoKanriMaintenance mantenance = new JigyoKanriMaintenance();
			try{
				shoruiInfo = mantenance.selectShoruiInfo(userInfo, shoruiPk);
			}catch(ApplicationException e){
				throw new FileIOException(
				"���ފǗ��f�[�^��������DB�G���[���������܂����B");				
			}
		
			String filePath = shoruiInfo.getShoruiFile();
			if(filePath == null || filePath.equals("")){
				throw new FileIOException(
					"�t�@�C���p�X���s���ł��B�t�@�C���p�X'" + filePath + "'");			
			}
			try{
				File file = new File(filePath);
				fileRes = FileUtil.readFile(file);
			}catch(FileNotFoundException e){
				throw new FileIOException(
					"�t�@�C����������܂���ł����B",			
					e);
			}catch(IOException e){
				throw new FileIOException(
					"�t�@�C���̓��o�͒��ɃG���[���������܂����B",
					e);
			}
			return fileRes;
	}
	
	/**
	 * ���ވꗗ�y�[�W���̎擾.<br><br>
	 * 
	 * ���ވꗗ�̃y�[�W�����擾����B<br><br>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID, 			-- ����ID
	 *     B.TAISHO_ID,			-- �Ώ�
	 *     B.SHORUI_NAME,		-- ���ޖ�
	 *     B.SYSTEM_NO,			-- �V�X�e����t�ԍ�
	 *     A.JIGYO_NAME,		-- ���Ɩ�
	 *     A.NENDO,			-- �N�x
	 *     TO_CHAR(A.KAISU) KAISU,	-- ��
	 *     A.UKETUKEKIKAN_START,	-- ���Ǝ�t���ԁi�J�n���j
	 *     A.UKETUKEKIKAN_END		-- ���Ǝ�t���ԁi�I�����j
	 * FROM
	 *     JIGYOKANRI A, SHORUIKANRI B
	 * WHERE
	 *     A.JIGYO_ID = B.JIGYO_ID
	 *     AND A.DEL_FLG = 0
	 *     AND B.DEL_FLG = 0
	 * 
	 * 	--- ���I��������1 ---
	 * 
	 * ORDER BY A.JIGYO_ID,SYSTEM_NO
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">���I��������1</span></b><br/>
	 * searchInfo�ɂ���Č������������I�ɕω�����B<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>jigyoId</td><td>AND B.JIGYO_ID = '����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�Ώ�</td><td>taishoId</td><td>AND B.TAISHO_ID = '�Ώ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>systemNo</td><td>AND B.SYSYEM_NO = '�V�X�e���ԍ�'</td></tr>
	 * </table>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	ShoruiKanriSearchInfo
	 * @return ���ޏ�񃊃X�g
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#searchShoruiList(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShoruiKanriSearchInfo)
	 */
	public Page searchShoruiList(UserInfo userInfo, ShoruiKanriSearchInfo searchInfo) throws ApplicationException {
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select = 
			"SELECT A.JIGYO_ID," 		//����ID
			+ " B.TAISHO_ID,"			//�Ώ�
			+ " B.SHORUI_NAME,"			//���ޖ�
			+ " B.SYSTEM_NO,"			//�V�X�e����t�ԍ�
			+ " A.JIGYO_NAME,"			//���Ɩ�
			+ " A.NENDO,"				//�N�x
			+ " TO_CHAR(A.KAISU) KAISU,"//��
			+ " A.UKETUKEKIKAN_START,"	//���Ǝ�t���ԁi�J�n���j
			+ " A.UKETUKEKIKAN_END"		//���Ǝ�t���ԁi�I�����j
			+ " FROM JIGYOKANRI A, SHORUIKANRI B"
			+ " WHERE A.JIGYO_ID = B.JIGYO_ID"
			+ " AND A.DEL_FLG = 0"
			+ " AND B.DEL_FLG = 0";
			

		StringBuffer query = new StringBuffer(select);
		if(searchInfo.getJigyoId() != null && searchInfo.getJigyoId().length() != 0){
			query.append(" AND B.JIGYO_ID ='" + EscapeUtil.toSqlString(searchInfo.getJigyoId()) + "'");		//����ID
		}
		if(searchInfo.getTaishoId() != null && searchInfo.getTaishoId().length() != 0){
			query.append(" AND B.TAISHO_ID ='" + EscapeUtil.toSqlString(searchInfo.getTaishoId()) + "'");		//�Ώ�
		}
		if(searchInfo.getSystemNo() != null && searchInfo.getSystemNo().length() != 0){
			query.append(" AND B.SYSYEM_NO ='" + EscapeUtil.toSqlString(searchInfo.getSystemNo()) + "'");		//�V�X�e����t�ԍ�
		}
		query.append(" ORDER BY A.JIGYO_ID,SYSTEM_NO");	//����ID,�V�X�e����t�ԍ��̏���
				
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
			log.error("���ވꗗ�f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"���ވꗗ�f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * �y�[�W���̎擾.<br><br>
	 * 
	 * �y�[�W�����擾����B<br><br>
	 * 
	 * �������œn���ꂽ���������Ɋ�Â��A���Ə��Ǘ��e�[�u������������B<br/>
	 * ���֕��₢���킹�Ő\����ID���w�U�󗝂̃��R�[�h���擾����B<br/><br/>
	 * 
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj<br/>
	 * �������ʂ�Page�I�u�W�F�N�g�Ɋi�[���A�ԋp����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- ����ID
	 *     ,A.NENDO					-- �N�x
	 *     ,A.KAISU					-- ��
	 *     ,A.JIGYO_NAME			-- ���Ɩ�
	 *     ,A.JIGYO_KUBUN			-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN			-- �R���敪
	 *     ,A.TANTOKA_NAME			-- �Ɩ��S����
	 *     ,A.TANTOKAKARI			-- �Ɩ��S���W��
	 *     ,A.TOIAWASE_NAME			-- �₢���킹��S���Җ�
	 *     ,A.TOIAWASE_TEL			-- �₢���킹��d�b�ԍ�
	 *     ,A.TOIAWASE_EMAIL		-- �₢���킹��E-mail
	 *     ,A.UKETUKEKIKAN_START	-- �w�U��t���ԁi�J�n�j
	 *     ,A.UKETUKEKIKAN_END		-- �w�U��t���ԁi�I���j
	 *     ,A.SHINSAKIGEN			-- �R������
	 *     ,A.TENPU_NAME			-- �Y�t������
	 *     ,A.TENPU_WIN				-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *     ,A.TENPU_MAC				-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *     ,A.HYOKA_FILE_FLG		-- �]���p�t�@�C���L��
	 *     ,A.HYOKA_FILE			-- �]���p�t�@�C���i�[�t�H���_
	 *     ,A.KOKAI_FLG				-- ���J�t���O
	 *     ,A.KESSAI_NO				-- ���J���ٔԍ�
	 *     ,A.KOKAI_ID				-- ���J�m���ID
	 *     ,A.HOKAN_DATE			-- �f�[�^�ۊǓ�
	 *     ,A.YUKO_DATE				-- �ۊǗL������
	 *     ,A.BIKO				-- ���l
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     JIGYOKANRI A					-- ���Ə��Ǘ��e�[�u��
	 * WHERE
	 *     A.DEL_FLG = '0'
	 *     AND EXISTS(
	 *         SELECT
	 *             *
	 *         FROM
	 *             SHINSEIDATAKANRI B	-- �\���f�[�^�Ǘ��e�[�u��
	 *         WHERE
	 *             B.JIGYO_ID = A.JIGYO_ID
	 *             AND B.DEL_FLG = '0'
	 *             AND B.JOKYO_ID= '06'	-- �X�e�[�^�X���w�U��
	 *         )
	 * 
	 * ORDER BY A.JIGYO_ID
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>B.JOKYO_ID</td><td>'06'  [StatusCode.STATUS_GAKUSIN_JYURI]</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	��������(SearchInfo)
	 * @return ���Ə����i�[����Page�I�u�W�F�N�g
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#searchWarifuriJigyo(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.SearchInfo)
	 */
	public Page searchWarifuriJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException {

		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		StringBuffer query= new StringBuffer();
		query.append("SELECT * FROM JIGYOKANRI A ");
        query.append("WHERE A.DEL_FLG = '0' AND EXISTS(SELECT * FROM SHINSEIDATAKANRI B WHERE B.JIGYO_ID = A.JIGYO_ID AND B.DEL_FLG = '0' AND B.JOKYO_ID='"
                + StatusCode.STATUS_GAKUSIN_JYURI //�X�e�[�^�X���w�U�󗝂̂���
                + "') ");
		//�\�[�g���i����ID�̏����j
		query.append("ORDER BY A.JIGYO_ID");		
		
		if(log.isDebugEnabled()){
			log.debug("query:'" + query.toString() + "'");
		}
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
		} catch (DataAccessException e) {
			log.error("���ƊǗ��f�[�^��������DB�G���[���������܂����B", e);
			throw new ApplicationException(
				"���ƊǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * �t�@�C����������.<br><br>
	 * 
	 * �t�@�C���Ƀo�C�g�̔z����������ށB<br><br>
	 * 
	 * ������file����g���q���������t�@�C�������擾���A�ϐ�jigyoId�ɑ���B<br/><br/>
	 * 
	 * ������file�̐e�f�B���N�g���̃��X�g���擾����B<br/><br/>
	 * 
	 * �ϐ�jigyoId�ƈ�v���郊�X�g���̃t�@�C�������폜����B<br/>
	 * FileUtil�N���X��delete()���\�b�h�ɂāA�t�@�C�����폜����B<br/><br/>
	 * 
	 * FileUtil�N���X��writeFile()���\�b�h�ɂāA�t�@�C�����i�[����B<br/><br/>
	 * 
	 * @param	file	�t�@�C��
	 * @param	fileRes	�������ރ��\�[�X
	 * @return	�Ȃ�
	 * @throws	ApplicationException
	 */
	public void writeFile(File file, FileResource fileRes, boolean deleteFlg)
									 throws ApplicationException{
		try{
			if(deleteFlg){			
				//�g���q���������t�@�C�����������t�@�C���͍폜
				int index = file.getName().lastIndexOf(".");
				String jigyoId = "";
				if(index > -1){
					jigyoId = file.getName().substring(0, index);
				}
				File[] list = file.getParentFile().listFiles();
				if (list != null && list.length > 0) {
					for (int i = 0; i < list.length; i++) {
						int index1 = list[i].getName().lastIndexOf(".");
						if(index1 > -1){
							String str = list[i].getName().substring(0, index1);
							if(jigyoId.equals(str)){
								FileUtil.delete(list[i]);
							}
						}
					}
				}
			}
		
			//�t�@�C���i�[			
			FileUtil.writeFile(file, fileRes.getBinary());	
		}catch(IOException e){
			throw new ApplicationException(
				"�t�@�C���i�[���ɃG���[���������܂����B�t�@�C��'" + file.getName() + "'",
				new ErrorInfo("errors.7001"),
				e);				
		}
	}

	/**
	 * ���Ə��̎擾.<br><br>
	 * 
	 * ���Ə����擾����B<br><br>
	 * 
	 * ���ƃR�[�h�A�N�x�A�񐔂����Ɏ��Ə��Ǘ��e�[�u�����猟������B<br/><br/>
	 * 
	 * <b>1.����ID�̍쐬</b><br/>
	 * (1) DateUtil�N���X��changeWareki2Seireki()���\�b�h�ɂāA
	 * ��O����nendo��a��琼��ɕϊ�����B<br/><br/>
	 * 
	 * (2) ����ID���쐬����B<br/>
	 * �@(1)�Ŏ擾�����N�x + ������jigyoCd + ��l����kaisu ������ID�Ƃ���B<br/><br/>
	 * 
	 * <b>2.���Ə����擾</b><br>
	 * �@�ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID				-- ����ID
	 *     ,A.NENDO					-- �N�x
	 *     ,A.KAISU					-- ��
	 *     ,A.JIGYO_NAME			-- ���Ɩ�
	 *     ,A.JIGYO_KUBUN			-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN			-- �R���敪
	 *     ,A.TANTOKA_NAME			-- �Ɩ��S����
	 *     ,A.TANTOKAKARI			-- �Ɩ��S���W��
	 *     ,A.TOIAWASE_NAME			-- �₢���킹��S���Җ�
	 *     ,A.TOIAWASE_TEL			-- �₢���킹��d�b�ԍ�
	 *     ,A.TOIAWASE_EMAIL		-- �₢���킹��E-mail
	 *     ,A.UKETUKEKIKAN_START	-- �w�U��t���ԁi�J�n�j
	 *     ,A.UKETUKEKIKAN_END		-- �w�U��t���ԁi�I���j
	 *     ,A.SHINSAKIGEN			-- �R������
	 *     ,A.TENPU_NAME			-- �Y�t������
	 *     ,A.TENPU_WIN				-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *     ,A.TENPU_MAC				-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *     ,A.HYOKA_FILE_FLG		-- �]���p�t�@�C���L��
	 *     ,A.HYOKA_FILE			-- �]���p�t�@�C���i�[�t�H���_
	 *     ,A.KOKAI_FLG				-- ���J�t���O
	 *     ,A.KESSAI_NO				-- ���J���ٔԍ�
	 *     ,A.KOKAI_ID				-- ���J�m���ID
	 *     ,A.HOKAN_DATE			-- �f�[�^�ۊǓ�
	 *     ,A.YUKO_DATE				-- �ۊǗL������
	 *     ,A.BIKO				-- ���l
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     JIGYOKANRI A
	 * WHERE
	 *     JIGYO_ID = ?
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="40%">��</td><td>�l</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>1.�ō쐬��������ID</td></tr>
	 * </table><br/><br/>
	 * 
	 * @param userInfo	UserInfo
	 * @param jigyoCd	���ƃR�[�h
	 * @param nendo		�N�x
	 * @param kaisu		��
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IJigyoKanriMaintenance#getJigyoKanriInfo(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String, java.lang.String, java.lang.String)
	 */
	public JigyoKanriInfo getJigyoKanriInfo(
		UserInfo userInfo,
		String jigyoCd,
		String nendo,
		String kaisu)
		throws NoDataFoundException, ApplicationException
	{
		//DB�R�l�N�V�����̎擾
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---���Ə����擾����
			JigyoKanriInfo info = null;
			try {
				JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);
				info = dao.selectJigyoKanriInfo(connection, jigyoCd, nendo, kaisu);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"���ƊǗ����f�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			return info;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}	
}