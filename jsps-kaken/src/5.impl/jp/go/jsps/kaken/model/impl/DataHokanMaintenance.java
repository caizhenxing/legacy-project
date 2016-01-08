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
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.status.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.LabelValueBean;

import org.apache.commons.logging.*;

/**
 * �f�[�^�ۊǊǗ����s���N���X.<br /><br />
 * �g�p���Ă���e�[�u��<br />
 * �@�E<b>���Ə��Ǘ��e�[�u��</b>�F���Ƃ̊�{�������Ǘ�<br />
 * �@�E<b>�R�����ʃe�[�u��</b>�F�R��������U�茋�ʏ��Ɛ\�����̐R�����ʂ��Ǘ�<br />
 * �@�E<b>�Y�t�t�@�C���Ǘ��e�[�u��</b>�F�\�����ɓY�t���ꂽ�t�@�C���̊i�[��f�B���N�g�������Ǘ�<br />
 * �@�E<b>�\���f�[�^�Ǘ��e�[�u��</b>�F�\���f�[�^�̏����Ǘ�<br />
 * �@�E<b>�����g�D�\�Ǘ��e�[�u��</b>�F�\���f�[�^�̌����g�D�\�����Ǘ�<br />
 * �@�E<b>���x���}�X�^</b>�F�v���_�E�����̖��́A���̏����Ǘ�<br />
 */
public class DataHokanMaintenance implements IDataHokanMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(DataHokanMaintenance.class);
	
	/** 
	 * �f�[�^�ۊǃT�[�oDB�����N��.<br /><br />
	 * �o�b�N�A�b�v�����f�[�^�x�[�X�̃f�[�^�x�[�X���B<br />
	 * ��̓I�Ȓl�́A"@KAKENHOKANDB"
	 */
	protected static final String HOKAN_SERVER_DB_LINK = 
										ApplicationSettings.getString(ISettingKeys.HOKAN_SERVER_DB_LINK);
	
	/** 
	 * �f�[�^�ۊǃT�[�oUNC.<br /><br />
	 * �p�X�������UNC�`���ɕϊ�����ۂɎg�p�����B<br />
	 * ��̓I�Ȓl�́A"\\\\127.0.0.1\\shinsei_hokan"
	 */
	protected static final String HOKAN_SERVER_UNC = 
										ApplicationSettings.getString(ISettingKeys.HOKAN_SERVER_UNC);
										
	/** 
	 * UNC�ɕϊ�����h���C�u���^�[.<br /><br />
	 * �p�X�������UNC�`���ɕϊ�����ۂɎg�p�����B<br />
	 * ���̒l���A<b>HOKAN_SERVER_UNC</b>�ɕϊ������B<br />
	 * ��̓I�Ȓl�́A"${shinsei_path}"
	 */
	protected static final String DRIVE_LETTER_CONVERTED_TO_UNC = 
										ApplicationSettings.getString(ISettingKeys.DRIVE_LETTER_CONVERTED_TO_UNC);
	
	/** 
	 * �f�[�^�ۊǃT�[�o�ɃR�s�[����f�B���N�g��.<br /><br />
	 * �f�[�^��ۊǂ���f�B���N�g���̃p�X�B<br />
	 * ��̓I�Ȓl�́A<br />
	 * �@�EHOKAN_TARGET_DIRECTORY[0]="${shinsei_path}/data/{0}"<br />
	 * �@�EHOKAN_TARGET_DIRECTORY[1]="${shinsei_path}/data/xml/{0}"<br />
	 * �@�EHOKAN_TARGET_DIRECTORY[2]="${shinsei_path}/data/pdf/{0}"<br />
	 */
	protected static final String[] HOKAN_TARGET_DIRECTORY = getDirectoryList();
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public DataHokanMaintenance() {
		super();
	}
	
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
	/**
	 * �ۊǑΏۃf�B���N�g�����X�g�̎擾.<br /><br />
	 * 
	 * (1)�l�̎擾<br />
	 * ���\�[�X�o���h������A�L�[�ɑΉ�����l(String)���擾����B<br /><br />
	 * �L�[�́A<b>"HOKAN_TARGET_DIRECTORY[i]"</b>,i=0,1,2<br /><br />
	 * �Ή�����l���Ȃ��ꍇ�́Anull��Ԃ��B<br />
	 * null���Ԃ����܂Œl�̎擾���J��Ԃ��AString�^�̔z��Ƃ��Ĉ����B<br /><br />
	 * 
	 * 
	 * (2)�z��̕ԋp<br />
	 * null���Ԃ��ꂽ�Ƃ���ŁA���̔z���toArray���\�b�h�Ń\�[�g���ĕԋp����B
	 * @return �ۊǑΏۃf�B���N�g����String[]
	 */
	private static String[] getDirectoryList()
	{
		List valueList = new ArrayList();
		for(int i=0;;i++){
			String key   = ISettingKeys.HOKAN_TARGET_DIRECTORY + "[" + i + "]";
			String value = ApplicationSettings.getString(key, false); 
			
			if(value == null){
				break;				//�l��null�ɂȂ����i�L�[�����݂��Ȃ��Ȃ����j���_�Ń��[�v�𔲂���
			}else{
				valueList.add(value);
			}
		}
		return (String[])valueList.toArray(new String[0]);		
	}
	
	
	
	/**
	 * �t�@�C���ۊǃ��\�b�h.<br /><br />
	 * 
	 * (1)�ۊǂ���f�B���N�g���̊m�F<br />
	 * "HOKAN_TARGET_DIRECTORY"����`����Ă��Ȃ��ꍇ�ɂ́A���O���o�͂���B<br /><br />
	 * 
	 * 
	 * (2)�R�s�[���̃f�B���N�g�����w��������̍쐬<br />
	 * String<b>"HOKAN_TARGET_DIRECTORY[i]"</b>�̒l�̐擪��"${shinsei_path}"��ApplicationSettings.properties�ɐݒ肳�ꂽ�l�Œu���������A
	 * ����ɁA�����ł���String<b>"jigyoId"</b>�̒l��A�����������̂��A<b>"fromPath"</b>�Ƃ���B<br /><br />
	 * 
	 * 
	 * (3)�R�s�[��̃f�B���N�g�����w��������̍쐬<br />
	 * fromPath��String<b>"DRIVE_LETTER_CONVERTED_TO_UNC"</b>���܂ޕ�����ł���΁A
	 * fromPath����DRIVE_LETTER_CONVERTED_TO_UNC�̕�����<b>"HOKAN_SERVER_UNC"</b>�ɒu�����A�����������<b>"toPath"</b>�Ƃ���B<br /><br />
	 * 
	 * 
	 * (4)�f�B���N�g���̃R�s�[���s<br />
	 * String�ł���A2,3�̒l���t�@�C���I�u�W�F�N�g�ɕϊ�����B<br />
	 * fromFile����toFile�֎��Ƃ��Ƃ̃f�B���N�g���̃R�s�[���s���B�R�s�[��ɓ����t�@�C�������݂��Ă����ꍇ�͏㏑������B
	 * ���̍ہA�R�s�[�Ɏ��s�����ꍇ�̓��O���o�͂��A��O��throw����B<br /><br />
	 * 
	 * 
	 * (5)�J��Ԃ�<br />
	 * ����2�`4���A�ۊǑΏۃf�B���N�g���̐������J��Ԃ��B
	 * @param jigyoId ����ID
	 * @return
	 * @throws Exception
	 */
	private void fileHokan(String jigyoId) throws Exception
	{
		if(HOKAN_TARGET_DIRECTORY.length == 0){
			if(log.isDebugEnabled()){
				String msg = "�ۊǑΏۂƂȂ�f�B���N�g�����ݒ肳��Ă��Ȃ��B-> �t�@�C���ۊǂ��s��Ȃ��B";
				log.info(msg);
			}
			return;
		}
		
		//�ۊǑΏۃf�B���N�g�����J��Ԃ�
		for(int i=0; i<HOKAN_TARGET_DIRECTORY.length; i++){
			
			String fromPath = MessageFormat.format(HOKAN_TARGET_DIRECTORY[i], new String[]{jigyoId});
			String toPath   = StringUtil.substrReplace(fromPath, DRIVE_LETTER_CONVERTED_TO_UNC, HOKAN_SERVER_UNC);
			File   fromFile = new File(fromPath);
			File   toFile   = new File(toPath);
			
			//�R�s�[���f�B���N�g�������݂��Ȃ������ꍇ�͏������΂�
			if(!fromFile.exists()){
				if(log.isDebugEnabled()){
					String msg = "�R�s�[���f�B���N�g�������݂��܂���BfromFile=" + fromFile;
					log.info(msg);
				}
				continue;				
			}
			
			//�f�B���N�g���R�s�[
			if(!FileUtil.directoryCopy(fromFile, toFile)){
				String msg = "�t�@�C���R�s�[���ɃG���[���������܂����B\n�������r���ŏI�����Ă���\��������܂��B";
				if(log.isDebugEnabled()){
					log.error(msg);
				}
				throw new IOException(msg);
			}
			
		}
		
	}
	
//2006/11/22 �c�@�ǉ���������    
    /**
     * �t�@�C���ۊǃ��\�b�h(�̈�v�揑�̂�).<br /><br />
     * 
     * (1)�ۊǂ���f�B���N�g���̊m�F<br />
     * "HOKAN_TARGET_DIRECTORY"����`����Ă��Ȃ��ꍇ�ɂ́A���O���o�͂���B<br /><br />
     * 
     * 
     * (2)�R�s�[���̃f�B���N�g�����w��������̍쐬<br />
     * String<b>"HOKAN_TARGET_DIRECTORY[i]"</b>�̒l�̐擪��"${shinsei_path}"��ApplicationSettings.properties�ɐݒ肳�ꂽ�l�Œu���������A
     * ����ɁA�����ł���String<b>"jigyoId" + "_RG"</b>�̒l��A�����������̂��A<b>"fromPathRG"</b>�Ƃ���B<br /><br />
     * 
     * 
     * (3)�R�s�[��̃f�B���N�g�����w��������̍쐬<br />
     * fromPathRG��String<b>"DRIVE_LETTER_CONVERTED_TO_UNC"</b>���܂ޕ�����ł���΁A
     * fromPathRG����DRIVE_LETTER_CONVERTED_TO_UNC�̕�����<b>"HOKAN_SERVER_UNC"</b>�ɒu�����A�����������<b>"toPathRG"</b>�Ƃ���B<br /><br />
     * 
     * 
     * (4)�f�B���N�g���̃R�s�[���s<br />
     * String�ł���A2,3�̒l���t�@�C���I�u�W�F�N�g�ɕϊ�����B<br />
     * fromFileRG����toFileRG�֎��Ƃ��Ƃ̃f�B���N�g���̃R�s�[���s���B�R�s�[��ɓ����t�@�C�������݂��Ă����ꍇ�͏㏑������B
     * ���̍ہA�R�s�[�Ɏ��s�����ꍇ�̓��O���o�͂��A��O��throw����B<br /><br />
     * 
     * 
     * (5)�J��Ԃ�<br />
     * ����2�`4���A�ۊǑΏۃf�B���N�g���̐������J��Ԃ��B
     * @param jigyoId ����ID
     * @return
     * @throws Exception
     */
    private void fileHokanGaiyo(String jigyoId) throws Exception {

        if (HOKAN_TARGET_DIRECTORY.length == 0) {
            if (log.isDebugEnabled()) {
                String msg = "�ۊǑΏۂƂȂ�f�B���N�g�����ݒ肳��Ă��Ȃ��B-> �t�@�C���ۊǂ��s��Ȃ��B";
                log.info(msg);
            }
            return;
        }

        //�ۊǑΏۃf�B���N�g�����J��Ԃ�
        for (int i = 0; i < HOKAN_TARGET_DIRECTORY.length; i++) {
            String formPathRG = MessageFormat.format(HOKAN_TARGET_DIRECTORY[i], new String[] { jigyoId
                    + "_RG" });
            String toPathRG = StringUtil.substrReplace(formPathRG, DRIVE_LETTER_CONVERTED_TO_UNC,
                    HOKAN_SERVER_UNC);
            File fromFileRG = new File(formPathRG);
            File toFileRG = new File(toPathRG);

            //�R�s�[���f�B���N�g�������݂��Ȃ������ꍇ�͏������΂�
            if (!fromFileRG.exists()) {
                if (log.isDebugEnabled()) {
                    String msg = "�R�s�[���f�B���N�g�������݂��܂���BfromFileRG=" + fromFileRG;
                    log.info(msg);
                }
                continue;
            }

            //�f�B���N�g���R�s�[
            if (!FileUtil.directoryCopy(fromFileRG, toFileRG)) {
                String msg = "�t�@�C���R�s�[���ɃG���[���������܂����B\n�������r���ŏI�����Ă���\��������܂��B";
                if (log.isDebugEnabled()) {
                    log.error(msg);
                }
                throw new IOException(msg);
            }

        }
    }
//2006/11/22�@�c�@�ǉ������܂� 
	
	
	//---------------------------------------------------------------------
	// implement IDataHokanMaintenance
	//---------------------------------------------------------------------	
	
	/**
	 * �e�[�u���f�[�^�̍X�V�E�ۊ�.<br /><br />
	 * �e�[�u���f�[�^�̍X�V�ƁA�o�b�N�A�b�v�p�̃f�[�^�x�[�X�Ƃ̓��������B<br /><br />
	 * 
	 * 
	 * <b>1.���ƊǗ��e�[�u���̍X�V</b><br /><br />
	 * 
	 * ���ƊǗ��e�[�u����<br />
	 * �@�E�f�[�^�ۊǓ�<br />
	 * �@�E�ۊǗL������<br />
	 * �̍X�V���s���B<br /><br />
	 * 
	 * (1)jigyoId�̎擾<br />
	 * �@������"jigyouKanriPk"���AString<b>"jigyoId"</b>���擾����B<br /><br />
	 * 
	 * 
	 * (2)�t�@�C���̕ۊ�<br />
	 * �@���\�b�h<b>"fileHokan()"</b>���A������"jigyoId"��^���Ď��s���A�t�@�C���̕ۊǂ��s���B<br />
	 * �@���\�b�h���s���ɃG���[�����������ꍇ�́A��O��throw����B<br /><br />
	 * 
	 * 
	 * (3)DB����l���擾<br />
	 * �@�ȉ���SQL���𔭍s����B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.JIGYO_ID			--����ID
	 *		,A.NENDO			--�N�x
	 *		,A.KAISU			--��
	 *		,A.JIGYO_NAME		--���Ɩ�
	 *		,A.JIGYO_KUBUN			--���Ƌ敪
	 *		,A.SHINSA_KUBUN			--�R���敪
	 *		,A.TANTOKA_NAME			--�Ɩ��S����
	 *		,A.TANTOKAKARI			--�Ɩ��S���W��
	 *		,A.TOIAWASE_NAME		--�₢���킹��S���Җ�
	 *		,A.TOIAWASE_TEL			--�₢���킹��d�b�ԍ�
	 *		,A.TOIAWASE_EMAIL		--�₢���킹��E-mail
	 *		,A.UKETUKEKIKAN_START	--�w�U��t���ԁi�J�n�j
	 *		,A.UKETUKEKIKAN_END		--�w�U��t���ԁi�I���j
	 *		,A.SHINSAKIGEN			--�R������
	 *		,A.TENPU_NAME			--�Y�t������
	 *		,A.TENPU_WIN		--�Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *		,A.TENPU_MAC		--�Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *		,A.HYOKA_FILE_FLG	--�]���p�t�@�C���L��
	 *		,A.HYOKA_FILE		--�]���p�t�@�C���i�[�t�H���_
	 *		,A.KOKAI_FLG		--���J�t���O
	 *		,A.KESSAI_NO		--���J���ٔԍ�
	 *		,A.KOKAI_ID			--���J�m���ID
	 *		,A.HOKAN_DATE		--�f�[�^�ۊǓ�
	 *		,A.YUKO_DATE		--�ۊǗL������
	 *		,A.BIKO				--���l
	 *		,A.DEL_FLG			--�폜�t���O
	 *	FROM
	 *		JIGYOKANRIA
	 *	WHERE
	 *		JIGYO_ID=? </pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>������primaryKeys�̕ϐ�JigyoId���g�p����B</td></tr>
	 * </table><br />
	 * �@�擾�����l��<b>"JigyoKanriInfo"</b>�̃I�u�W�F�N�g�Ɋi�[���A�擾����B<br />
	 * �@���̌�A<br />
	 * �@�@�E�f�[�^�ۊǓ�(WAS����擾�������݂̓��t)<br />
	 * �@�@�E�ۊǗL������(��O������Date<b>"yukokigen"</b>)<br />
	 * �@���擾���āA���̃I�u�W�F�N�g�Ɋi�[���A�X�V����B<br /><br />
	 * 
	 * 
	 * (4)���ƊǗ��e�[�u���̍X�V�i�Ɩ������T�[�o�j<br /><br />
	 * �@�ȉ���SQL���𔭍s���A�e�[�u���̍X�V���s���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	UPDATE JIGYOKANRI A 
	 *		 SET	
	 *		 A.JIGYO_ID = ?			--����ID
	 *		,A.NENDO = ?			--�N�x
	 *		,A.KAISU = ?			--��
	 *		,A.JIGYO_NAME = ?		--���Ɩ�
	 *		,A.JIGYO_KUBUN = ?		--���Ƌ敪
	 *		,A.SHINSA_KUBUN = ?		--�R���敪
	 *		,A.TANTOKA_NAME = ?		--�Ɩ��S����
	 *		,A.TANTOKAKARI = ?		--�Ɩ��S���W��
	 *		,A.TOIAWASE_NAME = ?	--�₢���킹��S���Җ�
	 *		,A.TOIAWASE_TEL = ?		--�₢���킹��d�b�ԍ�
	 *		,A.TOIAWASE_EMAIL = ?		--�₢���킹��E-mail
	 *		,A.UKETUKEKIKAN_START = ?	--�w�U��t���ԁi�J�n�j
	 *		,A.UKETUKEKIKAN_END = ?		--�w�U��t���ԁi�I���j
	 *		,A.SHINSAKIGEN = ?			--�R������
	 *		,A.TENPU_NAME = ?			--�Y�t������
	 *		,A.TENPU_WIN = ?		--�Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *		,A.TENPU_MAC = ?		--�Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *		,A.HYOKA_FILE_FLG = ?	--�]���p�t�@�C���L��
	 *		,A.HYOKA_FILE = ?		--�]���p�t�@�C���i�[�t�H���_
	 *		,A.KOKAI_FLG = ?			--���J�t���O
	 *		,A.KESSAI_NO = ?			--���J���ٔԍ�
	 *		,A.KOKAI_ID = ?			--���J�m���ID
	 *		,A.HOKAN_DATE = ?			--�f�[�^�ۊǓ�
	 *		,A.YUKO_DATE = ?			--�ۊǗL������
	 *		,A.BIKO = ?			--���l
	 *		,A.DEL_FLG = ?			--�폜�t���O
	 *	WHERE
	 *		 JIGYO_ID = ?			--����ID  </pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>����4�Ŏ擾����info�̕ϐ�(JigyoId)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�N�x</td><td>����4�Ŏ擾����info�̕ϐ�(Nendo)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��</td><td>����4�Ŏ擾����info�̕ϐ�(Kaisu)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ɩ�</td><td>����4�Ŏ擾����info�̕ϐ�(JigyoName)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>����4�Ŏ擾����info�̕ϐ�(JigyoKubun)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R���敪</td><td>����4�Ŏ擾����info�̕ϐ�(ShinsaKubun)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S����</td><td>����4�Ŏ擾����info�̕ϐ�(TantokaName)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S���W��</td><td>����4�Ŏ擾����info�̕ϐ�(TantoKakari)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�₢���킹��S���Җ�</td><td>����4�Ŏ擾����info�̕ϐ�(ToiawaseName)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�₢���킹��d�b�ԍ�</td><td>����4�Ŏ擾����info�̕ϐ�(ToiawaseTel)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�₢���킹��E-mail</td><td>����4�Ŏ擾����info�̕ϐ�(ToiawaseEmail)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�w�U��t���ԁi�J�n�j</td><td>����4�Ŏ擾����info�̕ϐ�(UketukekikanStart)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�w�U��t���ԁi�I���j</td><td>����4�Ŏ擾����info�̕ϐ�(UketukekikanEnd)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R������</td><td>����4�Ŏ擾����info�̕ϐ�(ShinsaKigen)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Y�t������</td><td>����4�Ŏ擾����info�̕ϐ�(TenpuName)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Y�t�t�@�C���i�[�t�H���_�iWin�j</td><td>����4�Ŏ擾����info�̕ϐ�(TenpuWin)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Y�t�t�@�C���i�[�t�H���_�iMac�j</td><td>����4�Ŏ擾����info�̕ϐ�(TenpuMac)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�]���p�t�@�C���L��</td><td>����4�Ŏ擾����info�̕ϐ�(HyokaFileFlg)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�]���p�t�@�C���i�[�t�H���_</td><td>����4�Ŏ擾����info�̕ϐ�(HyokaFile)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���J�t���O</td><td>����4�Ŏ擾����info�̕ϐ�(KokaiFlg)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���J���ٔԍ�</td><td>����4�Ŏ擾����info�̕ϐ�(KessaiNo)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���J�m���ID</td><td>����4�Ŏ擾����info�̕ϐ�(KokaiID)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�f�[�^�ۊǓ�</td><td>����4�Ŏ擾����info�̕ϐ�(HokanDate)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�ۊǗL������</td><td>����4�Ŏ擾����info�̕ϐ�(YukoDate)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>����4�Ŏ擾����info�̕ϐ�(getBiko)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>"0"���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>����4�Ŏ擾����info�̕ϐ�(JigyoId)���g�p����B</td></tr>
	 * </table><br />
	 * �@�G���[�����������ꍇ�ɂ́A��O��throw����B<br /><br /><br />
	 * 
	 * 
	 * <b>2.�e�[�u���f�[�^�̓��������.</b><br /><br />
	 * 
	 * �ȉ��̍�ƂŁA�o�b�N�A�b�v�p�̃f�[�^�x�[�X�̃f�[�^�̓��������B�ΏۂƂȂ�e�[�u���́A<br />
	 * �@�E���Ə��Ǘ��e�[�u��:JIGYOKNARI<br />
	 * �@�E�R�����ʃe�[�u��:SHINSAKEKKA<br />
	 * �@�E�Y�t�t�@�C���e�[�u��:TENPUFILEINFO<br />
	 * �@�E�\���f�[�^�e�[�u��:SHINSEIDATAKANRI<br />
	 * �@�E�����g�D�p�e�[�u��:KENKYUSOSHIKIKANRI<br />
	 * �ł���B"dbLink"�́A"<b>HOKAN_SERVER_DB_LINK</b>"�ł���B<br />
	 * (�ȉ���SQL���́��ɓ���̂́A�����ŋ�����5�̃e�[�u���̃e�[�u����)<br /><br />
	 * 
	 * (1)�f�[�^�̍폜<br />
	 * �@�f�[�^�̕����폜���s���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	DELETE 
	 *	FROM
	 *		(��)<b>�e�[�u����</b> "dbLink"
	 *	WHERE
	 *		JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoKanriPk�̕ϐ�JigyoId���g�p����B</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * (2)�f�[�^�̑}��<br />
	 * �f�[�^�̑}�����s���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	INSERT INTO (��)<b>�e�[�u����</b> "dbLink"
	 *		SELECT
	 *			*
	 *		FROM
	 * 			(��)<b>�e�[�u����</b>
	 * 		WHERE
	 * 			JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>��������JigyoId���g�p����B</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * (3)�\���f�[�^�ۊǌ����̎擾<br />
	 * �\���f�[�^�ۊǎ��̂݁A�����̎擾���s���B<br />
	 * ����4�̃e�[�u���Ɋւ��ẮA���̏����͍s��Ȃ��B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT 
	 *		COUNT(SYSTEM_NO) 
	 *	FROM 
	 *		SHINSEIDATAKANRI "dbLink"
	 *	WHERE
	 *		JIGYO_ID = ?  </pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>��������JigyoId���g�p����B</td></tr>
	 * </table><br /><br /><br />
	 * 
	 * 
	 * <b>3.�l�̕ԋp�ƃ��[���o�b�N</b><br /><br />
	 * 
	 * 
	 * (1)�f�[�^�ۊǌ����̕ԋp<br />
	 * �f�[�^�ۊǌ����̕ԋp���s���B<br /><br />
	 * 
	 * 
	 * (2)���[���o�b�N�̎��s<br />
	 * ���̏����ɂ��ǂ蒅���O�ɗ�O�����������ꍇ�́A��O��throw���A���[���o�b�N���s���B<br />
	 * ���[���o�b�N������ɍs���Ȃ������ꍇ�ɂ́A��O��throw����B
	 * @param userInfo UserInfo
	 * @param jigyoKanriPk JigyoKanriPk
	 * @param yukoKigen �f�[�^�ۊǂ̗L��������Date
	 * @return �ۊǃf�[�^�̌���������int
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#dataHokanInvoke(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriPk, java.util.Date)
	 */
	public int dataHokanInvoke(
		UserInfo userInfo,
		JigyoKanriPk jigyoKanriPk,
		Date yukoKigen)
		throws NoDataFoundException, ApplicationException
	{
		//����ID		
		String jigyoId = jigyoKanriPk.getJigyoId();
        //����CD
        String jigyoCd = jigyoId.substring(2, 7);
		

        //=====�t�@�C���ۊ�=====
		try{
			fileHokan(jigyoId);
			//2006/11/22 �c�@�ǉ���������            
            if (IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(jigyoCd)) {
                fileHokanGaiyo(jigyoId);
            }
            //2006/11/22 �c�@�ǉ������܂�            
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException(
				"�t�@�C���ۊǒ��ɃG���[���������܂����B",
				new ErrorInfo("errors.7003"),
				e);
		}
		
		
		//=====DB�ۊ�=====
		Connection connection = null;
		boolean success = false;
		try{
			connection = DatabaseUtil.getConnection();

			//-----���ƊǗ��e�[�u���̍X�V�i�Ɩ������T�[�o�j-----
			try{
				
				JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo);

				//--�X�V--
				JigyoKanriInfo info = dao.selectJigyoKanriInfo(connection, jigyoKanriPk);

				//�폜�t���O���P�̏ꍇ�A�G���[�Ƃ���2007/5/25
				if ("1".equals(info.getDelFlg())){
					throw new ApplicationException(
							"���Ə��f�[�^�͊��ɍ폜����܂����B",
							new ErrorInfo("errors.5002"));
				}
				
//2007/5/25 �ۊǓ��t�ƕۊǊ����݂̂��X�V����
//				info.setHokanDate(new Date());		//�f�[�^�ۊǓ�
//				info.setYukoDate(yukoKigen);		//�f�[�^�ۊǗL������
//				dao.updateJigyoKanriInfo(connection, info);
				dao.updateHokanInfo(connection, jigyoKanriPk, yukoKigen);
//2007/5/25 �C������			
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"���Ə��f�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4009"),
					e);
			}
			
			//-----���ƊǗ��e�[�u���̕ۊ�-----
			try{
				JigyoKanriInfoDao dao = new JigyoKanriInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				dao.deleteJigyoKanriInfoNoCheck(connection, jigyoKanriPk);
				dao.copy2HokanDB(connection, jigyoId);
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"���Ə��f�[�^�ۊǒ���DB�G���[���������܂����B",
					new ErrorInfo("errors.4009"),
					e);
			}
						
			//-----�R�����ʃe�[�u���̕ۊ�-----
			try{
				ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				dao.deleteShinsaKekkaInfo(connection, jigyoId);
				dao.copy2HokanDB(connection, jigyoId);
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"�R�����ʃf�[�^�ۊǒ���DB�G���[���������܂����B",
					new ErrorInfo("errors.4009"),
					e);
			}
						
			//-----�Y�t�t�@�C���e�[�u���̕ۊ�-----
			try{
				TenpuFileInfoDao dao = new TenpuFileInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				dao.deleteTenpuFileInfos(connection, jigyoId);
				dao.copy2HokanDB(connection, jigyoId);
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"�Y�t�t�@�C���f�[�^�ۊǒ���DB�G���[���������܂����B",
					new ErrorInfo("errors.4009"),
					e);
			}
			
			//-----�\���f�[�^�e�[�u���̕ۊ�-----
			int hokanDataCount = 0;
			try{
				ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				dao.deleteShinseiDataInfo(connection, jigyoId);
				dao.copy2HokanDB(connection, jigyoId);
				
				//�ۊǌ������擾����
				hokanDataCount = dao.countShinseiDataByJigyoID(connection, jigyoId);
				
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"�\�����Ǘ��f�[�^�ۊǒ���DB�G���[���������܂����B",
					new ErrorInfo("errors.4009"),
					e);
			}
			
			//-----�����g�D�\�e�[�u���̕ۊ�-----
			try{
				KenkyuSoshikikanriDao dao = new KenkyuSoshikikanriDao(userInfo, HOKAN_SERVER_DB_LINK);
				dao.deleteKenkyuSoshikiKanriInfo(connection, jigyoId);
				dao.copy2HokanDB(connection, jigyoId);
			}catch(DataAccessException e){
				success = false;
				throw new ApplicationException(
					"�Y�t�t�@�C���f�[�^�ۊǒ���DB�G���[���������܂����B",
					new ErrorInfo("errors.4009"),
					e);
			}	
			
// 2006/10/24 add by liucy start
            // -----�̈�v�揑�f�[�^�e�[�u���̕ۊ�-----
            if (IJigyoCd.JIGYO_CD_TOKUTEI_SINKI.equals(jigyoCd)) {
                try {
                    RyoikiKeikakushoInfoDao dao = new RyoikiKeikakushoInfoDao(
                            userInfo, HOKAN_SERVER_DB_LINK);
                    dao.deleteRyoikiKeikakushoInfo(connection, jigyoId);
                    dao.copy2HokanDB(connection, jigyoId);
                } catch (DataAccessException e) {
                    success = false;
                    throw new ApplicationException(
                            "�̈�v�揑�Ǘ��f�[�^�ۊǒ���DB�G���[���������܂����B", new ErrorInfo(
                                    "errors.4009"), e);
                }
            }
// 2006/10/24 add by liucy end
			
			//�����܂ŏ������i�߂ΐ���Ɣ��f�ł���
			success = true;
			return hokanDataCount;
			
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"DB�ۊǒ��ɃG���[���������܂����B",
					new ErrorInfo("errors.4009"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
	}	
	
	
	
	
	/**
	 * �y�[�W�����擾����.<br /><br />
	 * 
	 * �������œn���ꂽ���������Ɋ�Â��A�\�����ꗗ�̃y�[�W�����擾����B<br />
	 * �\�����ꗗ�̃y�[�W���ɂ́A���Y�\���f�[�^�̐\���󋵖��i�\���󋵂�\��������j���Z�b�g�����B<br /><br />
	 * 
	 * �@(1)������������SQL�����쐬���A���ʂ��擾����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *SELECT 
	 *	A.SYSTEM_NO,				--�V�X�e����t�ԍ�
	 *	A.UKETUKE_NO,				--�\���ԍ�
	 *	A.JIGYO_ID,				--����ID
	 *	A.NENDO,					--�N�x
	 *	A.KAISU,					--��
	 *	A.JIGYO_NAME,				--���Ɩ�
	 *	A.SHINSEISHA_ID,				--�\����ID
	 *	A.SAKUSEI_DATE,				--�\�����쐬��
	 *	A.SHONIN_DATE,				--�����@�֏��F��
   	 * 	A.NAME_KANJI_SEI,				--�\���Ҏ����i������-���j
	 *	A.NAME_KANJI_MEI,				--�\���Ҏ����i������-���j
	 *	A.KENKYU_NO,				--�\���Ҍ����Ҕԍ�
	 * 	A.SHOZOKU_CD,				--�����@�փR�[�h
	 *	A.SHOZOKU_NAME,				--�����@�֖�
	 *	A.SHOZOKU_NAME_RYAKU,			--�����@�֖��i���́j
	 *	A.BUKYOKU_NAME,				--���ǖ�
	 *	A.BUKYOKU_NAME_RYAKU,			--���ǖ��i���́j
	 *	A.SHOKUSHU_NAME_KANJI,			--�E��
	 *	A.SHOKUSHU_NAME_RYAKU,			--�E���i���́j
	 *	A.KADAI_NAME_KANJI,			--�����ۑ薼(�a���j
	 *	A.JIGYO_KUBUN,				--���Ƌ敪
	 *	A.KEKKA1_ABC,				--1���R������(ABC)
	 *	A.KEKKA1_TEN,				--1���R������(�_��)
	 *	A.KEKKA1_TEN_SORTED,			--1���R������(�_����)
	 *	A.KEKKA2,					--2���R������
	 *	A.JOKYO_ID,				--�\����ID
	 *	A.SAISHINSEI_FLG,				--�Đ\���t���O
	 *	A.KEI_NAME_RYAKU,				--�n���̋敪�i���́j
	 *	A.KANTEN_RYAKU,				--���E�̊ϓ_�i���́j
	 *	A.NENREI,					--�N��
	 *	DECODE
	 *	(
	 *	 NVL(A.SUISENSHO_PATH,'null') 
	 *	 ,'null','FALSE'		--���E���p�X��NULL�̂Ƃ�
	 *	 ,'TRUE'				--���E���p�X��NULL�ȊO�̂Ƃ�
	 *	) SUISENSHO_FLG, 		--���E���o�^�t���O
	 *	B.UKETUKEKIKAN_END,			--�w�U��t�����i�I���j
	 *	B.HOKAN_DATE,				--�f�[�^�ۊǓ�
	 *	B.YUKO_DATE,				--�ۊǗL������
	 *	DECODE
	 *	(
	 *	 SIGN( 
	 *	      TO_DATE( TO_CHAR(B.UKETUKEKIKAN_END,'YYYY/MM/DD'), 'YYYY/MM/DD' ) 
	 *	    - TO_DATE( TO_CHAR(SYSDATE           ,'YYYY/MM/DD'), 'YYYY/MM/DD' ) 
	 *	     )
	 *	 ,0 , 'TRUE'			--���ݎ����Ɠ����ꍇ
	 *	 ,1 , 'TRUE'			--���ݎ����̕�����t�������O
	 *	 ,-1, 'FALSE'			--���ݎ����̕�����t��������
	 *	) UKETUKE_END_FLAG,		--�w�U��t�����i�I���j���B�t���O
	 *	DECODE
	 *	(
	 *	 NVL(A.PDF_PATH,'null') 
	 *	 ,'null','FALSE'				--PDF�̊i�[�p�X��NULL�̂Ƃ�
	 * 	 ,      'TRUE'				--PDF�̊i�[�p�X��NULL�ȊO�̂Ƃ�
	 *	) PDF_PATH_FLG, 				--PDF�̊i�[�p�X�t���O
	 *	DECODE
	 *	(
	 *	 NVL(C.SYSTEM_NO,'null') 
	 *	 ,'null','FALSE'		--�Y�t�t�@�C����NULL�̂Ƃ�
	 *	 ,      'TRUE'			--�Y�t�t�@�C����NULL�ȊO�̂Ƃ�
	 *	) TENPUFILE_FLG 		--�Y�t�t�@�C���t���O
	 *	FROM
	 *	SHINSEIDATAKANRI A,			--�\���f�[�^�Ǘ��e�[�u��
	 *	JIGYOKANRI B,				--���Ə��Ǘ��e�[�u��
	 *	(SELECT DISTINCT SYSTEM_NO FROM TENPUFILEINFO
	 *	 WHERE TENPU_PATH IS NOT NULL) C
	 *		--�Y�t�t�@�C���e�[�u���i�Y�t�t�@�C���p�X��null�ł͂Ȃ��ꍇ�̂݁j
	 *	WHERE
	 *	A.JIGYO_ID = B.JIGYO_ID		--����ID����������
	 *	AND
	 *	A.SYSTEM_NO = C.SYSTEM_NO()
	 *		--�e�[�u���̘A��(C�ɘA������f�[�^���Ȃ��ꍇ���\��) </pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>������primaryKeys�̕ϐ�JigyoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * �@(2)Page���̃��X�g���擾���A�e���R�[�h�}�b�v�ɑ΂��Đ\���󋵖����Z�b�g����B<br />
	 * �@�@������SQL�������J�t���O���擾���A��������ɐ\���󋵖����擾����B<br />
	 * �@�@�E�\���󋵖��c���s���郆�[�U�ɊY������A�\���󋵂�\��������B<br />
	 * �@�@�E�\���󋵂�\��������c���s���郆�[�U�A���Ƃ̌��J�O��A�\����ID�ɊY�����镶����B<br />
	 * �@�@�������{KEKKA1}���܂܂�Ă����ꍇ�́A1���R������(ABC)��1���R������(�_��)��<br />
	 * �@�@�����񌋍��������̂ƒu���A{KEKKA2}���܂܂�Ă����ꍇ��2���R������(������)��<br />
	 * �@�@�u������������B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * SELECT 
	 * 	KOKAI_FLG 
	 * FROM 
	 * 	JIGYOKANRI 
	 * WHERE 
	 * 	JIGYO_ID = 'jigyoId'</pre>
	 * </td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(3)Page��ԋp����B<br /><br />
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseiSearchInfo
	 * @return �\�����ꗗ�y�[�W��񂪊i�[���ꂽPage
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#searchApplication(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiSearchInfo)
	 */
	public Page searchApplication(
		UserInfo userInfo,
		ShinseiSearchInfo searchInfo)
		throws NoDataFoundException, ApplicationException
	{
		//DB�R�l�N�V�����̎擾
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---�\�����ꗗ�y�[�W���
			Page pageInfo = null;
			try {
				ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				pageInfo = dao.searchApplication(connection, searchInfo);	//�Y�����R�[�h��S���擾
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�\�����Ǘ��f�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			//�\���󋵖����Z�b�g
			new StatusManager(userInfo, HOKAN_SERVER_DB_LINK).setStatusName(connection, pageInfo);
			return pageInfo;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}	
	
	
	/**
	 * PDF�f�[�^�̍쐬.<br /><br />
	 * �\���f�[�^���PDF�t�@�C�����쐬����B<br />
	 * �쐬���ꂽPDF�t�@�C���ɂ́A���O�C�����[�U�̃p�X���[�h���b�N����������B<br /><br />
	 * 
	 * 
	 * �@(1)�\���f�[�^�L�[�����ΏۂƂȂ�IOD�t�@�C�����擾����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * SELECT 
	 * 	0 SEQ_NUM
	 * 	,0 SEQ_TENPU
	 * 	,D.PDF_PATH IOD_FILE_PATH 
	 * FROM 
	 * 	SHINSEIDATAKANRI D 
	 * WHERE 
	 * 	D.SYSTEM_NO = ? 
	 * 	UNION ALL 
	 * 		SELECT
	 *  			1 SEQ_NUM
	 * 			,A.SEQ_TENPU SEQ_TENPU
	 * 			,A.PDF_PATH IOD_FILE_PATH 
	 * 		FROM 
	 * 			TENPUFILEINFO A 
	 * 		WHERE 
	 * 			A.SYSTEM_NO = ? 
	 * 		ORDER BY 
	 * 			SEQ_NUM
	 * 			,SEQ_TENPU</pre>
	 * </td></tr>
	 * </table><br />
	 * �@�@IOD�t�@�C���p�X�����݂��Ȃ������ꍇ�͍ēx�ϊ������������A���̏�łP�񂾂��Ď��s���s���B<br />
	 * �@�@����ł����s�����ꍇ�͗�O��throw����B<br /><br />
	 * 
	 * 
	 * �@(2)�擾����IOD�t�@�C�������X�g�ɉ����Ă����B<br /><br />
	 * 
	 * 
	 * �@(3)���[�U�̃p�X���[�h���擾����B<br /><br />
	 * 
	 * 
	 * �@(4)IOD�t�@�C���̃��X�g�ƃp�X���[�h��^���A�p�X���[�h�tPDF���\�[�X���擾���A�ԋp����B
	 * �@�@�����ł́Aiod�t�@�C������pdf�t�@�C���ւ̕ϊ����s���B�g�p���Ă���N���X�́A<br />
	 * �@�@�@�Eiodoc<br />
	 * �@�@�@�Eiodtopdf<br />
	 * �@�@�@�Ewebdocmem<br />
	 * �@�@�ł���B
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return PDF�f�[�^��FileResource
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getPdfFileRes(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public FileResource getPdfFileRes(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws ApplicationException
	{
		//===== PDF�ϊ��T�[�r�X���\�b�h�Ăяo�� =====
		IPdfConvert pdfConvert = new PdfConvert(HOKAN_SERVER_DB_LINK);
		return pdfConvert.getShinseiFileResource(userInfo, shinseiDataPk);
	}
	
	
	/**
	 * CSV�o�̓f�[�^�̍쐬.<br />
	 * �\�����ꗗ�y�[�W����CSV�o�͂��邽�߁ACSV�o�̓f�[�^�̍쐬���s���B<br /><br />
	 * 
	 * 
	 * �@(1)��������������SQL���𐶐����A�o�b�N�A�b�v�p�f�[�^�x�[�X���猟�������s����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT 
	 *	   A.SYSTEM_NO				--�V�X�e����t�ԍ�
	 *	  ,A.UKETUKE_NO				--�\���ԍ�
	 *	  ,A.JIGYO_ID				--����ID
	 *	  ,A.NENDO				--�N�x
	 *	  ,A.KAISU				--��
	 *	  ,A.JIGYO_NAME				--���Ɩ�
	 *	  ,A.SHINSEISHA_ID				--�\����ID
	 *	  ,TO_CHAR(A.SAKUSEI_DATE, 'YYYY/MM/DD')	--�\�����쐬��
	 *	  ,TO_CHAR(A.SHONIN_DATE, 'YYYY/MM/DD')	--�����@�֏��F��
	 *	  ,TO_CHAR(A.JYURI_DATE, 'YYYY/MM/DD')	--�w�U�󗝓�
	 *	  ,A.NAME_KANJI_SEI			--�\���Ҏ����i������-���j
	 *	  ,A.NAME_KANJI_MEI			--�\���Ҏ����i������-���j
	 *	  ,A.NAME_KANA_SEI				--�\���Ҏ����i�t���K�i-���j
	 *	  ,A.NAME_RO_SEI				--�\���Ҏ����i���[�}��-���j
	 *	  ,A.NAME_RO_MEI				--�\���Ҏ����i���[�}��-���j
	 *	  ,A.NENREI				--�N��
	 *	  ,A.KENKYU_NO				--�\���Ҍ����Ҕԍ�
	 *	  ,A.SHOZOKU_CD				--�����@�փR�[�h
	 *	  ,A.SHOZOKU_NAME				--�����@�֖�
	 *	  ,A.SHOZOKU_NAME_RYAKU			--�����@�֖��i���́j
	 *	  ,A.BUKYOKU_CD				--���ǃR�[�h
	 *	  ,A.BUKYOKU_NAME				--���ǖ�
	 *	  ,A.BUKYOKU_NAME_RYAKU			--���ǖ��i���́j
	 *	  ,A.SHOKUSHU_CD				--�E���R�[�h
	 *	  ,A.SHOKUSHU_NAME_KANJI			--�E���i�a���j
	 *	  ,A.SHOKUSHU_NAME_RYAKU			--�E���i���́j
	 *	  ,A.ZIP					--�X�֔ԍ�
	 *	  ,A.ADDRESS				--�Z��
	 *	  ,A.TEL					--TEL
	 *	  ,A.FAX					--FAX
	 *	  ,A.EMAIL				--E-Mail
	 *	  ,A.KADAI_NAME_KANJI			--�����ۑ薼(�a���j
	 *	  ,A.KADAI_NAME_EIGO			--�����ۑ薼(�p���j
	 *	  ,A.KEI_NAME_NO				--�n���̋敪�ԍ�
	 *	  ,A.KEI_NAME				--�n���̋敪
	 *	  ,A.KEI_NAME_RYAKU			--�n���̋敪����
	 *	  ,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 *	  ,A.BUNYA_NAME				--����
	 *	  ,A.BUNKA_NAME				--����
	 *	  ,A.SAIMOKU_NAME				--�ז�
	 *	  ,A.BUNKASAIMOKU_CD2			--�זڔԍ�2
	 *	  ,A.BUNYA_NAME2				--����2
	 *	  ,A.BUNKA_NAME2				--����2
	 *	  ,A.SAIMOKU_NAME2				--�ז�2
	 *	  ,A.KANTEN_NO				--���E�̊ϓ_�ԍ�
	 *	  ,A.KANTEN				--���E�̊ϓ_
	 *	  ,A.KANTEN_RYAKU				--���E�̊ϓ_����
	 *	  ,A.KEIHI1				--1�N�ڌ����o��
	 *	  ,A.BIHINHI1				--1�N�ڐݔ����i��
	 *	  ,A.SHOMOHINHI1				--1�N�ڏ��Օi��
	 *	  ,A.RYOHI1				--1�N�ڗ���
	 *	  ,A.SHAKIN1				--1�N�ڎӋ���
	 *	  ,A.SONOTA1				--1�N�ڂ��̑�
	 *	  ,A.KEIHI2				--2�N�ڌ����o��
	 *	  ,A.BIHINHI2				--2�N�ڐݔ����i��
	 *	  ,A.SHOMOHINHI2				--2�N�ڏ��Օi��
	 *	  ,A.RYOHI2				--2�N�ڗ���
	 *	  ,A.SHAKIN2				--2�N�ڎӋ���
	 *	  ,A.SONOTA2				--2�N�ڂ��̑�
	 *	  ,A.KEIHI3				--3�N�ڌ����o��
	 *	  ,A.BIHINHI3				--3�N�ڐݔ����i��
	 *	  ,A.SHOMOHINHI3				--3�N�ڏ��Օi��
	 *	  ,A.RYOHI3				--3�N�ڗ���
	 *	  ,A.SHAKIN3				--3�N�ڎӋ���
	 *	  ,A.SONOTA3				--3�N�ڂ��̑�
	 *	  ,A.KEIHI4				--4�N�ڌ����o��
	 *	  ,A.BIHINHI4				--4�N�ڐݔ����i��
	 *	  ,A.SHOMOHINHI4				--4�N�ڏ��Օi��
	 *	  ,A.RYOHI4				--4�N�ڗ���
	 *	  ,A.SHAKIN4				--4�N�ڎӋ���
	 *	  ,A.SONOTA4				--4�N�ڂ��̑�
	 *	  ,A.KEIHI5				--5�N�ڌ����o��
	 *	  ,A.BIHINHI5				--5�N�ڐݔ����i��
	 *	  ,A.SHOMOHINHI5				--5�N�ڏ��Օi��
	 *	  ,A.RYOHI5				--5�N�ڗ���
	 *	  ,A.SHAKIN5				--5�N�ڎӋ���
	 *	  ,A.SONOTA5				--5�N�ڂ��̑�
	 *	  ,A.KEIHI_TOTAL				--���v-�����o��
	 *	  ,A.BIHINHI_TOTAL				--���v-�ݔ����i��
	 *	  ,A.SHOMOHINHI_TOTAL			--���v-���Օi��
	 *	  ,A.RYOHI_TOTAL				--���v-����
	 *	  ,A.SHAKIN_TOTAL				--���v-�Ӌ���
	 *	  ,A.SONOTA_TOTAL				--���v-���̑�
	 *	  ,A.SOSHIKI_KEITAI_NO			--�����g�D�̌`�Ԕԍ�
	 *	  ,A.SOSHIKI_KEITAI			--�����g�D�̌`��
	 *	  ,A.BUNTANKIN_FLG				--���S���̗L��
	 *	  ,A.KENKYU_NINZU				--�����Ґ�
	 *	  ,A.TAKIKAN_NINZU				--���@�ւ̕��S�Ґ�
	 *	  ,A.SHINSEI_KUBUN				--�V�K�p���敪
	 *	  ,A.KADAI_NO_KEIZOKU			--�p�����̌����ۑ�ԍ�
	 *	  ,A.SHINSEI_FLG_NO			--�����v��ŏI�N�x�O�N�x�̉���
	 *	  ,A.KADAI_NO_SAISYU			--�ŏI�N�x�ۑ�ԍ�
	 *	  ,A.KAIGAIBUNYA_CD			--�C�O����R�[�h
	 *	  ,A.KAIGAIBUNYA_NAME			--�C�O���얼��
	 *	  ,A.KAIGAIBUNYA_NAME_RYAKU			--�C�O���엪��
	 *	  ,A.KANREN_SHIMEI1			--�֘A����̌�����-����1
	 *	  ,A.KANREN_KIKAN1			--�֘A����̌�����-�����@��1
	 *	  ,A.KANREN_BUKYOKU1		--�֘A����̌�����-��������1
	 *	  ,A.KANREN_SHOKU1			--�֘A����̌�����-�E��1
	 *	  ,A.KANREN_SENMON1			--�֘A����̌�����-��啪��1
	 *	  ,A.KANREN_TEL1			--�֘A����̌�����-�Ζ���d�b�ԍ�1
	 *	  ,A.KANREN_JITAKUTEL1		--�֘A����̌�����-����d�b�ԍ�1
	 *	  ,A.KANREN_MAIL1			--�֘A����̌�����-Email1
	 *	  ,A.KANREN_SHIMEI2			--�֘A����̌�����-����2
	 *	  ,A.KANREN_KIKAN2			--�֘A����̌�����-�����@��2
	 *	  ,A.KANREN_BUKYOKU2		--�֘A����̌�����-��������2
	 *	  ,A.KANREN_SHOKU2			--�֘A����̌�����-�E��2
	 *	  ,A.KANREN_SENMON2			--�֘A����̌�����-��啪��2
	 *	  ,A.KANREN_TEL2			--�֘A����̌�����-�Ζ���d�b�ԍ�2
	 *	  ,A.KANREN_JITAKUTEL2		--�֘A����̌�����-����d�b�ԍ�2
	 *	  ,A.KANREN_MAIL2			--�֘A����̌�����-Email2
	 *	  ,A.KANREN_SHIMEI3			--�֘A����̌�����-����3
	 *	  ,A.KANREN_KIKAN3			--�֘A����̌�����-�����@��3
	 *	  ,A.KANREN_BUKYOKU3		--�֘A����̌�����-��������3
	 *	  ,A.KANREN_SHOKU3			--�֘A����̌�����-�E��3
	 *	  ,A.KANREN_SENMON3			--�֘A����̌�����-��啪��3
	 *	  ,A.KANREN_TEL3			--�֘A����̌�����-�Ζ���d�b�ԍ�3
	 *	  ,A.KANREN_JITAKUTEL3		--�֘A����̌�����-����d�b�ԍ�3
	 *	  ,A.KANREN_MAIL3			--�֘A����̌�����-Email3
	 *	  ,A.JURI_KEKKA				--�󗝌���
	 *	  ,A.JURI_BIKO				--�󗝌��ʔ��l
	 *	  ,A.KEKKA1_ABC				--�P���R������(ABC)
	 *	  ,A.KEKKA1_TEN				--�P���R������(�_��)
	 *	  ,A.SHINSA1_BIKO				--�P���R�����l
	 *	  ,A.KEKKA2				--�Q���R������
	 *	  ,A.SHINSA2_BIKO				--�Ɩ��S���ҋL����
	 *	  ,A.JOKYO_ID				--�\����ID
	 *	  ,A.SAISHINSEI_FLG			--�Đ\���t���O
	 *	  ,TO_CHAR(B.HOKAN_DATE, 'YYYY/MM/DD')	--�f�[�^�ۊǓ�
	 *	  ,TO_CHAR(B.YUKO_DATE, 'YYYY/MM/DD')	--�ۊǗL������
	 *	FROM
	 *	  SHINSEIDATAKANRI 'dbLink' A,		--�\���f�[�^�Ǘ��e�[�u��
	 *	  JIGYOKANRI 'dbLink' B			--���Ə��Ǘ��e�[�u��
	 *	WHERE
	 *	  A.DEL_FLG = 0				--�폜�t���O��[0]
	 *	  AND
	 *	  A.JIGYO_ID = B.JIGYO_ID			--����ID����������</pre>
	 * </td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(2)List�̍ŏ��̗v�f�ɃJ���������X�g��}������B<br />
	 * �@�@�Z�b�g�����J�������́A�ȉ��̒ʂ�ł���B<br />
	 * �@�@�w�蕶�����SQL�̎��ʎq���𒴂��Ă��܂����ߕʂɃZ�b�g����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	�V�X�e����t�ԍ�
	 *	�\���ԍ�
	 *	����ID
	 *	�N�x
	 *	��
	 *	���Ɩ�
	 *	�\����ID
	 *	�\�����쐬��
	 *	�����@�֏��F��
	 *	�w�U�󗝓�
	 *	�\���Ҏ����i������-���j
	 *	�\���Ҏ����i������-���j
	 *	�\���Ҏ����i�t���K�i-���j
	 *	�\���Ҏ����i�t���K�i-���j
	 *	�\���Ҏ����i���[�}��-���j
	 *	�\���Ҏ����i���[�}��-���j
	 *	�N��
	 *	�\���Ҍ����Ҕԍ�
	 *	�����@�փR�[�h
	 *	�����@�֖�
	 *	�����@�֖��i���́j
	 *	���ǃR�[�h
	 *	���ǖ�
	 *	���ǖ��i���́j
	 *	�E���R�[�h
	 *	�E���i�a���j
	 *	�E���i���́j
	 *	�X�֔ԍ�
	 *	�Z��
	 *	TEL
	 *	FAX
	 *	E��ail
	 *	�����ۑ薼(�a���j
	 *	�����ۑ薼(�p���j
	 *	�n���̋敪�ԍ�
	 *	�n���̋敪
	 *	�n���̋敪����
	 *	�זڔԍ�
	 *	����
	 *	����
	 *	�ז�
	 *	�זڔԍ�2
	 *	����2
	 *	����2
	 *	�ז�2
	 *	���E�̊ϓ_�ԍ�
	 *	���E�̊ϓ_
	 *	���E�̊ϓ_����
	 *	1�N�ڌ����o��
	 *	1�N�ڐݔ����i��
	 *	1�N�ڏ��Օi��
	 *	1�N�ڗ���
	 *	1�N�ڎӋ���
	 *	1�N�ڂ��̑�
	 *	2�N�ڌ����o��
	 *	2�N�ڐݔ����i��
	 *	2�N�ڏ��Օi��
	 *	2�N�ڗ���
	 *	2�N�ڎӋ���
	 *	2�N�ڂ��̑�
	 *	3�N�ڌ����o��
	 *	3�N�ڐݔ����i��
	 *	3�N�ڏ��Օi��
	 *	3�N�ڗ���
	 *	3�N�ڎӋ���
	 *	3�N�ڂ��̑�
	 *	4�N�ڌ����o��
	 *	4�N�ڐݔ����i��
	 *	4�N�ڏ��Օi��
	 *	4�N�ڗ���
	 *	4�N�ڎӋ���
	 *	4�N�ڂ��̑�
	 *	5�N�ڌ����o��
	 *	5�N�ڐݔ����i��
	 *	5�N�ڏ��Օi��
	 *	5�N�ڗ���
	 *	5�N�ڎӋ���
	 *	5�N�ڂ��̑�
	 *	���v-�����o��
	 *	���v-�ݔ����i��
	 *	���v-���Օi��
	 *	���v-����
	 *	���v-�Ӌ���
	 *	���v-���̑�
	 *	�����g�D�̌`�Ԕԍ�
	 *	�����g�D�̌`��
	 *	���S���̗L��
	 *	�����Ґ�
	 *	���@�ւ̕��S�Ґ�
	 *	�V�K�p���敪
	 *	�p�����̌����ۑ�ԍ�
	 *	�����v��ŏI�N�x�O�N�x�̉���
	 *	�ŏI�N�x�ۑ�ԍ�
	 *	�C�O����R�[�h
	 *	�C�O���얼��
	 *	�C�O���엪��
	 *	�֘A����̌�����-����1
	 *	�֘A����̌�����-�����@��1
	 *	�֘A����̌�����-��������1
	 *	�֘A����̌�����-�E��1
	 *	�֘A����̌�����-��啪��1
	 *	�֘A����̌�����-�Ζ���d�b�ԍ�1
	 *	�֘A����̌�����-����d�b�ԍ�1
	 *	�֘A����̌�����-Email1
	 *	�֘A����̌�����-����2
	 *	�֘A����̌�����-�����@��2
	 *	�֘A����̌�����-��������2
	 *	�֘A����̌�����-�E��2
	 *	�֘A����̌�����-��啪��2
	 *	�֘A����̌�����-�Ζ���d�b�ԍ�2
	 *	�֘A����̌�����-����d�b�ԍ�2
	 *	�֘A����̌�����-Email2
	 *	�֘A����̌�����-����3
	 *	�֘A����̌�����-�����@��3
	 *	�֘A����̌�����-��������3
	 *	�֘A����̌�����-�E��3
	 *	�֘A����̌�����-��啪��3
	 *	�֘A����̌�����-�Ζ���d�b�ԍ�3
	 *	�֘A����̌�����-����d�b�ԍ�3
	 *	�֘A����̌�����-Email3
	 *	�󗝌���
	 *	�󗝌��ʔ��l
	 *	�P���R������(ABC)
	 *	�P���R������(�_��)
	 *	�P���R�����l
	 *	�Q���R������
	 *	�Ɩ��S���ҋL����
	 *	�\����ID
	 *	�Đ\���t���O
	 *	�f�[�^�ۊǓ�
	 *	�ۊǗL������</pre>
	 * </td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(3)CSV��List��ԋp����B<br /><br />
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseiSearchInfo
	 * @return CSV�o�̓f�[�^��List
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#searchCsvData(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiSearchInfo)
	 */
	public List searchCsvData(UserInfo userInfo, ShinseiSearchInfo searchInfo)
		throws ApplicationException
	{
		//DB�R�l�N�V�����̎擾
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---�\�����ꗗ�y�[�W���
			List csvList = null;
			try {
				ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				csvList = dao.searchCsvData(connection, searchInfo);	//�ۊǃ��R�[�h���擾
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�\�����Ǘ��f�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			return csvList;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}	
	
	

	/**
	 * CSV�o�̓f�[�^�̍쐬.<br /><br />
	 * �\�����ꗗ�ɕR�t�������g�D�f�[�^��CSV�o�͂��邽�߁ACSV�o�̓f�[�^�̍쐬���s���B<br /><br />
	 * 
	 * 
	 * �@(1)��������������SQL���𐶐����A�o�b�N�A�b�v�p�̃f�[�^�x�[�X���猟�������s����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT 
	 *		B.SYSTEM_NO			\�V�X�e����t�ԍ�\
	 *		,B.SEQ_NO				\�V�[�P���X�ԍ�\
	 *		,B.JIGYO_ID			\����ID\
	 *		,B.BUNTAN_FLG			\��\�ҕ��S�ҕ�\
	 *		,B.KENKYU_NO			\�����Ҕԍ�\
	 *		,B.NAME_KANJI_SEI			\�����i�����|���j\
	 *		,B.NAME_KANJI_MEI			\�����i�����|���j\
	 *		,B.NAME_KANA_SEI			\�����i�t���K�i�|���j\
	 *		,B.NAME_KANA_MEI			\�����i�t���K�i�|���j\
	 *		,B.SHOZOKU_CD			\�����@�֖��i�R�[�h�j\
	 *		,B.SHOZOKU_NAME			\�����@�֖��i�a���j\
	 *		,B.BUKYOKU_CD			\���ǖ��i�R�[�h�j\
	 *		,B.BUKYOKU_NAME			\���ǖ��i�a���j\
	 *		,B.SHOKUSHU_CD			\�E���R�[�h\
	 *		,B.SHOKUSHU_NAME_KANJI		\�E���i�a���j\
	 *		,B.SENMON				\���݂̐��\
	 *		,B.GAKUI				\�w��\
	 *		,B.BUNTAN				\�������S\
	 *		,B.KEIHI				\�����o��\
	 *		,B.EFFORT				\�G�t�H�[�g\
	 *		,B.NENREI				\�N��\
	 *	FROM
	 *		SHINSEIDATAKANRI 'dbLink' A
	 *		,KENKYUSOSHIKIKANRI 'dbLink' B
	 *	WHERE
	 *		B.SYSTEM_NO = A.SYSTEM_NO		-- �V�X�e����t�ԍ�����������</pre>
	 * </td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(2)List�̍ŏ��̗v�f�ɃJ���������X�g��}������B<br /><br />
	 * 
	 * 
	 * �@(3)CSV��List��ԋp����B<br /><br />
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseiSearchInfo
	 * @return CSV�o�̓f�[�^��List
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#searchKenkyuSoshikiCsvData(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiSearchInfo)
	 */
	public List searchKenkyuSoshikiCsvData(UserInfo userInfo, ShinseiSearchInfo searchInfo)
		throws ApplicationException
	{
		//DB�R�l�N�V�����̎擾
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---�\�����ꗗ�ɕR�t�������g�D�f�[�^
			List csvList = null;
			try {
				ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
				csvList = dao.searchKenkyuSoshikiCsvData(connection, searchInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�����g�D�Ǘ��f�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			return csvList;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}			
	
	
	/**
	 * 1��,2���R�����ʂ�Map�Ɋi�[.<br /><br />
	 * ���[�U���Ɛ\���f�[�^��񂩂�A1��,2���R�����ʂ�Map�Ɋi�[���A�ԋp����B<br />
	 * �Ăяo�����\�b�h�́A�ȉ��Ő�������鎩���\�b�h��<br />
	 * �@�E<b>getShinsaKekkaReferenceInfo(userInfo, shinseiDataPk)</b><br />
	 * �@�@�@�L�[��"key_shinsakekka_1st"<br />
	 * �@�E<b>getShinsaKekka2nd(userInfo, shinseiDataPk)</b><br />
	 * �@�@�@�L�[��"key_shinsakekka_2st"<br />
	 * �ł���B<br />
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return 1��,2���R�����ʂ�����Map
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getShinsaKekkaBoth(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public Map getShinsaKekkaBoth(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Map map = new HashMap();
		
		//1���R�����ʁi�Q�Ɨp�j
		map.put(
			KEY_SHINSAKEKKA_1ST,
			getShinsaKekkaReferenceInfo(userInfo, shinseiDataPk)
		);
		
		//2���R������
		map.put(
			KEY_SHINSAKEKKA_2ND,
			getShinsaKekka2nd(userInfo, shinseiDataPk)
		);
		
		return map;
	}

	
	
	/**
	 * 1���R�����ʁi�Q�Ɨp�j����.<br /><br />
	 * 
	 * 
	 * �@(1)��������������SQL���𐶐����A�o�b�N�A�b�v�p�̃f�[�^�x�[�X����Y���\���f�[�^���擾����B<br />
	 * �@�@�@(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 *		,A.UKETUKE_NO			--�\���ԍ�
	 *		,A.JIGYO_ID			--����ID
	 *		,A.NENDO				--�N�x
	 *		,A.KAISU				--��
	 *		,A.JIGYO_NAME			--���Ɩ�
	 *		,A.SHINSEISHA_ID			--�\����ID
	 *		,A.SAKUSEI_DATE			--�\�����쐬��
	 *		,A.SHONIN_DATE			--�����@�֏��F��
	 *		,A.JYURI_DATE			--�w�U�󗝓�
	 *		,A.NAME_KANJI_SEI			--�\���Ҏ����i������-���j
	 *		,A.NAME_KANJI_MEI			--�\���Ҏ����i������-���j
	 *		,A.NAME_KANA_SEI			--�\���Ҏ����i�t���K�i-���j
	 *		,A.NAME_KANA_MEI			--�\���Ҏ����i�t���K�i-���j
	 *		,A.NAME_RO_SEI			--�\���Ҏ����i���[�}��-���j
	 *		,A.NAME_RO_MEI			--�\���Ҏ����i���[�}��-���j
	 *		,A.NENREI				--�N��
	 *		,A.KENKYU_NO			--�\���Ҍ����Ҕԍ�
	 *		,A.SHOZOKU_CD			--�����@�փR�[�h
	 *		,A.SHOZOKU_NAME			--�����@�֖�
	 *		,A.SHOZOKU_NAME_RYAKU		--�����@�֖��i���́j
	 *		,A.BUKYOKU_CD			--���ǃR�[�h
	 *		,A.BUKYOKU_NAME			--���ǖ�
	 *		,A.BUKYOKU_NAME_RYAKU		--���ǖ��i���́j
	 *		,A.SHOKUSHU_CD			--�E���R�[�h
	 *		,A.SHOKUSHU_NAME_KANJI		--�E���i�a���j
	 *		,A.SHOKUSHU_NAME_RYAKU		--�E���i���́j
	 *		,A.ZIP				--�X�֔ԍ�
	 *		,A.ADDRESS			--�Z��
	 *		,A.TEL				--TEL
	 *		,A.FAX				--FAX
	 *		,A.EMAIL				--E-Mail
	 *		,A.SENMON				--���݂̐��
	 *		,A.GAKUI				--�w��
	 *		,A.BUNTAN				--�������S
	 *		,A.KADAI_NAME_KANJI		--�����ۑ薼(�a���j
	 *		,A.KADAI_NAME_EIGO			--�����ۑ薼(�p���j
	 *		,A.JIGYO_KUBUN			--���Ƌ敪
	 *		,A.SHINSA_KUBUN			--�R���敪
	 *		,A.SHINSA_KUBUN_MEISHO		--�R���敪����
	 *		,A.BUNKATSU_NO			--�����ԍ�
	 *		,A.BUNKATSU_NO_MEISHO		--�����ԍ�����
	 *		,A.KENKYU_TAISHO			--�����Ώۂ̗ތ^
	 *		,A.KEI_NAME_NO			--�n���̋敪�ԍ�
	 *		,A.KEI_NAME			--�n���̋敪
	 *		,A.KEI_NAME_RYAKU			--�n���̋敪����
	 *		,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 *		,A.BUNYA_NAME			--����
	 *		,A.BUNKA_NAME			--����
	 *		,A.SAIMOKU_NAME			--�ז�
	 *		,A.BUNKASAIMOKU_CD2		--�זڔԍ�2
	 *		,A.BUNYA_NAME2			--����2
	 *		,A.BUNKA_NAME2			--����2
	 *		,A.SAIMOKU_NAME2			--�ז�2
	 *		,A.KANTEN_NO			--���E�̊ϓ_�ԍ�
	 *		,A.KANTEN				--���E�̊ϓ_
	 *		,A.KANTEN_RYAKU			--���E�̊ϓ_����
	 *		,A.KEIHI1				--1�N�ڌ����o��
	 *		,A.BIHINHI1			--1�N�ڐݔ����i��
	 *		,A.SHOMOHINHI1			--1�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI1			--1�N�ڍ�������
	 *		,A.GAIKOKURYOHI1			--1�N�ڊO������
	 *		,A.RYOHI1				--1�N�ڗ���
	 *		,A.SHAKIN1			--1�N�ڎӋ���
	 *		,A.SONOTA1			--1�N�ڂ��̑�
	 *		,A.KEIHI2				--2�N�ڌ����o��
	 *		,A.BIHINHI2			--2�N�ڐݔ����i��
	 *		,A.SHOMOHINHI2			--2�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI2			--2�N�ڍ�������
	 *		,A.GAIKOKURYOHI2			--2�N�ڊO������
	 *		,A.RYOHI2				--2�N�ڗ���
	 *		,A.SHAKIN2			--2�N�ڎӋ���
	 *		,A.SONOTA2			--2�N�ڂ��̑�
	 *		,A.KEIHI3				--3�N�ڌ����o��
	 *		,A.BIHINHI3			--3�N�ڐݔ����i��
	 *		,A.SHOMOHINHI3			--3�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI3			--3�N�ڍ�������
	 *		,A.GAIKOKURYOHI3			--3�N�ڊO������
	 *		,A.RYOHI3				--3�N�ڗ���
	 *		,A.SHAKIN3			--3�N�ڎӋ���
	 *		,A.SONOTA3			--3�N�ڂ��̑�
	 *		,A.KEIHI4				--4�N�ڌ����o��
	 *		,A.BIHINHI4			--4�N�ڐݔ����i��
	 *		,A.SHOMOHINHI4			--4�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI4			--4�N�ڍ�������
	 *		,A.GAIKOKURYOHI4			--4�N�ڊO������
	 *		,A.RYOHI4				--4�N�ڗ���
	 *		,A.SHAKIN4			--4�N�ڎӋ���
	 *		,A.SONOTA4			--4�N�ڂ��̑�
	 *		,A.KEIHI5				--5�N�ڌ����o��
	 *		,A.BIHINHI5			--5�N�ڐݔ����i��
	 *		,A.SHOMOHINHI5			--5�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI5			--5�N�ڍ�������
	 *		,A.GAIKOKURYOHI5			--5�N�ڊO������
	 *		,A.RYOHI5				--5�N�ڗ���
	 *		,A.SHAKIN5			--5�N�ڎӋ���
	 *		,A.SONOTA5			--5�N�ڂ��̑�
	 *		,A.KEIHI_TOTAL			--���v-�����o��
	 *		,A.BIHINHI_TOTAL			--���v-�ݔ����i��
	 *		,A.SHOMOHINHI_TOTAL		--���v-���Օi��
	 *		,A.KOKUNAIRYOHI_TOTAL		--���v-��������
	 *		,A.GAIKOKURYOHI_TOTAL		--���v-�O������
	 *		,A.RYOHI_TOTAL			--���v-����
	 *		,A.SHAKIN_TOTAL			--���v-�Ӌ���
	 *		,A.SONOTA_TOTAL			--���v-���̑�
	 *		,A.SOSHIKI_KEITAI_NO		--�����g�D�̌`�Ԕԍ�
	 *		,A.SOSHIKI_KEITAI			--�����g�D�̌`��
	 *		,A.BUNTANKIN_FLG			--���S���̗L��
	 *		,A.KOYOHI				--�����x���Ҍٗp�o��
	 *		,A.KENKYU_NINZU			--�����Ґ�
	 *		,A.TAKIKAN_NINZU			--���@�ւ̕��S�Ґ�
	 *		,A.SHINSEI_KUBUN			--�V�K�p���敪
	 *		,A.KADAI_NO_KEIZOKU		--�p�����̌����ۑ�ԍ�
	 *		,A.SHINSEI_FLG_NO			--�p�����̌����ۑ�ԍ�
	 *		,A.SHINSEI_FLG			--�\���̗L��
	 *		,A.KADAI_NO_SAISYU			--�ŏI�N�x�ۑ�ԍ�
	 *		,A.KAIJIKIBO_FLG_NO		--�J����]�̗L���ԍ�
	 *		,A.KAIJIKIBO_FLG			--�J����]�̗L��
	 *		,A.KAIGAIBUNYA_CD			--�C�O����R�[�h
	 *		,A.KAIGAIBUNYA_NAME		--�C�O���얼��
	 *		,A.KAIGAIBUNYA_NAME_RYAKU		--�C�O���엪��
	 *		,A.KANREN_SHIMEI1		--�֘A����̌�����-����1
	 *		,A.KANREN_KIKAN1		--�֘A����̌�����-�����@��1
	 *		,A.KANREN_BUKYOKU1		--�֘A����̌�����-��������1
	 *		,A.KANREN_SHOKU1		--�֘A����̌�����-�E��1
	 *		,A.KANREN_SENMON1		--�֘A����̌�����-��啪��1
	 *		,A.KANREN_TEL1		--�֘A����̌�����-�Ζ���d�b�ԍ�1
	 *		,A.KANREN_JITAKUTEL1	--�֘A����̌�����-����d�b�ԍ�1
	 *		,A.KANREN_MAIL1			--�֘A����̌�����-E-mail1
	 *		,A.KANREN_SHIMEI2		--�֘A����̌�����-����2
	 *		,A.KANREN_KIKAN2		--�֘A����̌�����-�����@��2
	 *		,A.KANREN_BUKYOKU2		--�֘A����̌�����-��������2
	 *		,A.KANREN_SHOKU2		--�֘A����̌�����-�E��2
	 *		,A.KANREN_SENMON2		--�֘A����̌�����-��啪��2
	 *		,A.KANREN_TEL2		--�֘A����̌�����-�Ζ���d�b�ԍ�2
	 *		,A.KANREN_JITAKUTEL2	--�֘A����̌�����-����d�b�ԍ�2
	 *		,A.KANREN_MAIL2			--�֘A����̌�����-E-mail2
	 *		,A.KANREN_SHIMEI3		--�֘A����̌�����-����3
	 *		,A.KANREN_KIKAN3		--�֘A����̌�����-�����@��3
	 *		,A.KANREN_BUKYOKU3		--�֘A����̌�����-��������3
	 *		,A.KANREN_SHOKU3		--�֘A����̌�����-�E��3
	 *		,A.KANREN_SENMON3		--�֘A����̌�����-��啪��3
	 *		,A.KANREN_TEL3		--�֘A����̌�����-�Ζ���d�b�ԍ�3
	 *		,A.KANREN_JITAKUTEL3	--�֘A����̌�����-����d�b�ԍ�3
	 *		,A.KANREN_MAIL3			--�֘A����̌�����-E-mail3
	 *		,A.XML_PATH			--XML�̊i�[�p�X
	 *		,A.PDF_PATH			--PDF�̊i�[�p�X
	 *		,A.JURI_KEKKA			--�󗝌���
	 *		,A.JURI_BIKO			--�󗝌��ʔ��l
	 *		,A.SUISENSHO_PATH			--���E���̊i�[�p�X
	 *		,A.KEKKA1_ABC			--�P���R������(ABC)
	 *		,A.KEKKA1_TEN			--�P���R������(�_��)
	 *		,A.KEKKA1_TEN_SORTED		--�P���R������(�_����)
	 *		,A.SHINSA1_BIKO			--�P���R�����l
	 *		,A.KEKKA2				--�Q���R������
	 *		,A.SOU_KEHI			--���o��i�w�U���́j
	 *		,A.SHONEN_KEHI			--���N�x�o��i�w�U���́j
	 *		,A.SHINSA2_BIKO			--�Ɩ��S���ҋL����
	 *		,A.JOKYO_ID			--�\����ID
	 *		,A.SAISHINSEI_FLG			--�Đ\���t���O
	 *		,A.DEL_FLG			--�폜�t���O
	 *	FROM
	 *		SHINSEIDATAKANRI 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>������shinseiDataPk�̕ϐ�SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(2)��������������SQL���𐶐����A�o�b�N�A�b�v�p�̃f�[�^�x�[�X����Y���R�����ʃf�[�^���擾����B<br />
	 * �@�@�@(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 *		,A.UKETUKE_NO			--�\���ԍ�
	 *		,A.SHINSAIN_NO			--�R�����ԍ�
	 *		,A.JIGYO_KUBUN			--���Ƌ敪
	 *		,A.SEQ_NO				--�V�[�P���X�ԍ�
	 *		,A.SHINSA_KUBUN			--�R���敪
	 *		,A.SHINSAIN_NAME_KANJI_SEI		--�R�������i�����|���j
	 *		,A.SHINSAIN_NAME_KANJI_MEI		--�R�������i�����|���j
	 *		,A.NAME_KANA_SEI			--�R�������i�t���K�i�|���j
	 *		,A.NAME_KANA_MEI			--�R�������i�t���K�i�|���j
	 *		,A.SHOZOKU_NAME			--�R���������@�֖�
	 *		,A.BUKYOKU_NAME			--�R�������ǖ�
	 *		,A.SHOKUSHU_NAME			--�R�����E��
	 *		,A.JIGYO_ID			--����ID
	 *		,A.JIGYO_NAME			--���Ɩ�
	 *		,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 *		,A.EDA_NO				--�}��
	 *		,A.CHECKDIGIT			--�`�F�b�N�f�W�b�g
	 *		,A.KEKKA_ABC			--�����]���iABC�j
	 *		,A.KEKKA_TEN			--�����]���i�_���j
	 *		,NVL(REPLACE(A.KEKKA_TEN,'-','0'),'-1')SORT_KEKKA_TEN
	 *		--�\�[�g�p�B�R�����ʁi�_���j�̒lNULL��'-1'�A'-'��'0'�ɒu���j
	 *		,A.COMMENT1			--�R�����g1
	 *		,A.COMMENT2			--�R�����g2
	 *		,A.COMMENT3			--�R�����g3
	 *		,A.COMMENT4			--�R�����g4
	 *		,A.COMMENT5			--�R�����g5
	 *		,A.COMMENT6			--�R�����g6
	 *		,A.KENKYUNAIYO			--�������e
	 *		,A.KENKYUKEIKAKU			--�����v��
	 *		,A.TEKISETSU_KAIGAI		--�K�ؐ�-�C�O
	 *		,A.TEKISETSU_KENKYU1		--�K�ؐ�-�����i1�j
	 *		,A.TEKISETSU			--�K�ؐ�
	 *		,A.DATO				--�Ó���
	 *		,A.SHINSEISHA			--������\��
	 *		,A.KENKYUBUNTANSHA			--�������S��
	 *		,A.HITOGENOMU			--�q�g�Q�m��
	 *		,A.TOKUTEI			--������
	 *		,A.HITOES				--�q�gES�זE
	 *		,A.KUMIKAE			--��`�q�g��������
	 *		,A.CHIRYO				--��`�q���×Տ�����
	 *		,A.EKIGAKU			--�u�w����
	 *		,A.COMMENTS			--�R�����g
	 *		,A.TENPU_PATH			--�Y�t�t�@�C���i�[�p�X
	 *		,A.SHINSA_JOKYO			--�R����
	 *		,A.BIKO				--���l
	 *	FROM
	 *		SHINSAKEKKA 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?
	 *	ORDER BY
	 *		KEKKA_ABC ASC			--�����]���iABC�j�̏���
	 *		,SORT_KEKKA_TEN DESC		--�����]���i�_���j�̍~��
	 *		,SHINSAIN_NO ASC			--�R�����ԍ��̏���
	 *		,JIGYO_KUBUN ASC			--���Ƌ敪�̏���</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>������shinseiDataPk�̕ϐ�SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(3)�擾�����R�����ʃf�[�^�̊e�X�ɁA�Y�t�t�@�C�����E�����]���iABC�j�̕\�����x������������B<br />
	 * �@�@�@�Y�t�t�@�C�����́A�擾�����R�����ʃf�[�^����"�Y�t�t�@�C���i�[�p�X"�̒l����擾����B<br />
	 * �@�@�@�����]���iABC�j�̕\�����x�����́A�ȉ���SQL���𔭍s���Ď擾����B<br />
	 * �@�@�@(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.ATAI
	 *		,A.NAME
	 *		,A.RYAKU
	 *		,A.SORT
	 *		,A.BIKO
	 *	FROM
	 *		MASTER_LABEL A
	 *	WHERE
	 *		A.LABEL_KUBUN=?
	 *		AND A.ATAI=?
	 *	ORDER BY
	 *		SORT</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>���x���敪</td><td>���e����"KEKKA_ABC"</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�l</td><td>shinsaKekkaInfo�̕ϐ�KekkaAbc</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(4)�\���f�[�^�ƐR�����ʃf�[�^��1���R�����ʂƂ���<b>ShinsaKekkaReferenceInfo</b>�Ɋi�[���A�ԋp����B<br /><br />
	 * �@�@�i�[����l�́A�ȉ��̂��́B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * 	SystemNo			//�V�X�e����t�ԍ�
	 * 	UketukeNo			//�\���ԍ�
	 * 	Nendo			//�N�x
	 * 	Kaisu			//��
	 * 	JigyoId			//����ID
	 * 	JigyoName			//���Ɩ�
	 * 	KadaiNameKanji			//�����ۑ薼
	 * 	NameKanjiSei			//�\���Җ��i���j
	 * 	NameKanjiMei			//�\���Җ��i���j
	 * 	ShozokuName			//�����@�֖�
	 * 	BukyokuName			//���ǖ�
	 * 	ShokushuNameKanji			//�E��
	 * 	KenkyuNo			//�����Ҕԍ�
	 * 	shinsaKekkaInfo			//1���R�����ʏ��
	 * 	Shinsa1Biko			//�Ɩ��S���җp���l</pre>
	 * </td></tr>
	 * </table><br />
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return 1���R�����ʂ�����ShinsaKekkaReferenceInfo
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getShinsaKekkaReferenceInfo(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public ShinsaKekkaReferenceInfo getShinsaKekkaReferenceInfo(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			
			//�\���f�[�^�Ǘ�DAO
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//�Y���\���f�[�^���擾����
			ShinseiDataInfo shinseiDataInfo = null;
			try{
				shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�\�����Ǘ��f�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//�R������DAO
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//�Y���R�����ʃf�[�^���擾����
			ShinsaKekkaInfo[] shinsaKekkaInfo = null;
			try{
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//2005.09.09 iso ��Փ��̐R�����ʂ�\�������邽�ߒǉ�
			//���x����
			List kekkaAbcList = null;		//�����]���iABC�j
			List kekkaTenList = null;		//�����]���i�_���j
			List kekkaTenHogaList = null;		//�����]���i�G��j 
			try{
				String[] labelKubun = new String[]{ILabelKubun.KEKKA_ABC,
													ILabelKubun.KEKKA_TEN,
													ILabelKubun.KEKKA_TEN_HOGA};
				List bothList = new LabelValueMaintenance().getLabelList(labelKubun);	//4�̃��x�����X�g
				kekkaAbcList = (List)bothList.get(0);		
				kekkaTenList = (List)bothList.get(1);
				kekkaTenHogaList = (List)bothList.get(2);		
			}catch(ApplicationException e){
				throw new ApplicationException(
					"���x���}�X�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}

			for(int i = 0; i < shinsaKekkaInfo.length; i++){
				//�Y�t�t�@�C�������Z�b�g
				String tenpuPath = shinsaKekkaInfo[i].getTenpuPath();
				if(tenpuPath != null && tenpuPath.length() != 0){
					shinsaKekkaInfo[i].setTenpuName(new File(tenpuPath).getName());
				}

				//2005.09.09 iso ��Փ��̐R�����ʂ�\�������邽�ߕύX
//				//�����]���iABC�j�̕\�����x�������Z�b�g
//				if(shinsaKekkaInfo[i].getKekkaAbc() != null && shinsaKekkaInfo[i].getKekkaAbc().length() != 0){
//					try{
//						Map resultMap = MasterLabelInfoDao.selectRecord(connection,
//																		ILabelKubun.KEKKA_ABC,
//																		shinsaKekkaInfo[i].getKekkaAbc());
//						shinsaKekkaInfo[i].setKekkaAbcLabel((String)resultMap.get("NAME"));
//					}catch(NoDataFoundException e){
//						//��ONoDataFoundException�����������Ƃ��́A�����]���iABC�j���Z�b�g
//						shinsaKekkaInfo[i].setKekkaAbcLabel(shinsaKekkaInfo[i].getKekkaAbc());	
//					}catch (DataAccessException e) {
//						throw new ApplicationException(
//							"���x���}�X�^���擾���ɗ�O���������܂����B",
//							new ErrorInfo("errors.4004"),
//							e);
//					}
//				}
				//�����]���iABC�j�̕\�����x�������Z�b�g
				String kekkaAbcLabel = getLabelName(kekkaAbcList, shinsaKekkaInfo[i].getKekkaAbc());
				shinsaKekkaInfo[i].setKekkaAbcLabel(kekkaAbcLabel);

				//�����]���i�_���j�̕\�����x�������Z�b�g
				String kekkaTenLabel = getLabelName(kekkaTenList, shinsaKekkaInfo[i].getKekkaTen());
				shinsaKekkaInfo[i].setKekkaTenLabel(kekkaTenLabel);
				
//				�����]���i�G��j�̕\�����x�������Z�b�g
				String kekkaTenHogaLabel = getLabelName(kekkaTenHogaList, shinsaKekkaInfo[i].getKekkaTen());
				shinsaKekkaInfo[i].setKekkaTenHogaLabel(kekkaTenHogaLabel);
			}				
		
			//1���R�����ʁi�Q�Ɨp�j�̐���
			ShinsaKekkaReferenceInfo refInfo = new ShinsaKekkaReferenceInfo();
			refInfo.setSystemNo(shinseiDataInfo.getSystemNo());										//�V�X�e����t�ԍ�
			refInfo.setUketukeNo(shinseiDataInfo.getUketukeNo());									//�\���ԍ�
			refInfo.setNendo(shinseiDataInfo.getNendo());											//�N�x
			refInfo.setKaisu(shinseiDataInfo.getKaisu());											//��
			refInfo.setJigyoId(shinseiDataInfo.getJigyoId());										//����ID
			refInfo.setJigyoName(shinseiDataInfo.getJigyoName());									//���Ɩ�
			refInfo.setJigyoCd(shinseiDataInfo.getJigyoCd());										//���ƃR�[�h
			refInfo.setKadaiNameKanji(shinseiDataInfo.getKadaiInfo().getKadaiNameKanji());			//�����ۑ薼
			refInfo.setNameKanjiSei(shinseiDataInfo.getDaihyouInfo().getNameKanjiSei());			//�\���Җ��i���j
			refInfo.setNameKanjiMei(shinseiDataInfo.getDaihyouInfo().getNameKanjiMei());			//�\���Җ��i���j
			refInfo.setShozokuName(shinseiDataInfo.getDaihyouInfo().getShozokuName());				//�����@�֖�
			refInfo.setBukyokuName(shinseiDataInfo.getDaihyouInfo().getBukyokuName());				//���ǖ�
			refInfo.setShokushuNameKanji(shinseiDataInfo.getDaihyouInfo().getShokushuNameKanji());	//�E��
			refInfo.setKenkyuNo(shinseiDataInfo.getDaihyouInfo().getKenkyuNo());					//�����Ҕԍ�			
			refInfo.setShinsaKekkaInfo(shinsaKekkaInfo);											//1���R�����ʏ��
			refInfo.setShinsa1Biko(shinseiDataInfo.getShinsa1Biko());								//�Ɩ��S���җp���l

			return refInfo;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}
	
	
	
	/**
	 * 2���R�����ʁi�Q�Ɨp�j����.<br /><br />
	 * 
	 * 
	 * �@(1)��������������SQL���𐶐����A�o�b�N�A�b�v�p�̃f�[�^�x�[�X����Y���\���f�[�^���擾����B<br />
	 * �@�@�@(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 *		,A.UKETUKE_NO			--�\���ԍ�
	 *		,A.JIGYO_ID			--����ID
	 *		,A.NENDO				--�N�x
	 *		,A.KAISU				--��
	 *		,A.JIGYO_NAME			--���Ɩ�
	 *		,A.SHINSEISHA_ID			--�\����ID
	 *		,A.SAKUSEI_DATE			--�\�����쐬��
	 *		,A.SHONIN_DATE			--�����@�֏��F��
	 *		,A.JYURI_DATE			--�w�U�󗝓�
	 *		,A.NAME_KANJI_SEI			--�\���Ҏ����i������-���j
	 *		,A.NAME_KANJI_MEI			--�\���Ҏ����i������-���j
	 *		,A.NAME_KANA_SEI			--�\���Ҏ����i�t���K�i-���j
	 *		,A.NAME_KANA_MEI			--�\���Ҏ����i�t���K�i-���j
	 *		,A.NAME_RO_SEI			--�\���Ҏ����i���[�}��-���j
	 *		,A.NAME_RO_MEI			--�\���Ҏ����i���[�}��-���j
	 *		,A.NENREI				--�N��
	 *		,A.KENKYU_NO			--�\���Ҍ����Ҕԍ�
	 *		,A.SHOZOKU_CD			--�����@�փR�[�h
	 *		,A.SHOZOKU_NAME			--�����@�֖�
	 *		,A.SHOZOKU_NAME_RYAKU		--�����@�֖��i���́j
	 *		,A.BUKYOKU_CD			--���ǃR�[�h
	 *		,A.BUKYOKU_NAME			--���ǖ�
	 *		,A.BUKYOKU_NAME_RYAKU		--���ǖ��i���́j
	 *		,A.SHOKUSHU_CD			--�E���R�[�h
	 *		,A.SHOKUSHU_NAME_KANJI		--�E���i�a���j
	 *		,A.SHOKUSHU_NAME_RYAKU		--�E���i���́j
	 *		,A.ZIP				--�X�֔ԍ�
	 *		,A.ADDRESS			--�Z��
	 *		,A.TEL				--TEL
	 *		,A.FAX				--FAX
	 *		,A.EMAIL				--E-Mail
	 *		,A.SENMON				--���݂̐��
	 *		,A.GAKUI				--�w��
	 *		,A.BUNTAN				--�������S
	 *		,A.KADAI_NAME_KANJI		--�����ۑ薼(�a���j
	 *		,A.KADAI_NAME_EIGO			--�����ۑ薼(�p���j
	 *		,A.JIGYO_KUBUN			--���Ƌ敪
	 *		,A.SHINSA_KUBUN			--�R���敪
	 *		,A.SHINSA_KUBUN_MEISHO		--�R���敪����
	 *		,A.BUNKATSU_NO			--�����ԍ�
	 *		,A.BUNKATSU_NO_MEISHO		--�����ԍ�����
	 *		,A.KENKYU_TAISHO			--�����Ώۂ̗ތ^
	 *		,A.KEI_NAME_NO			--�n���̋敪�ԍ�
	 *		,A.KEI_NAME			--�n���̋敪
	 *		,A.KEI_NAME_RYAKU			--�n���̋敪����
	 *		,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 *		,A.BUNYA_NAME			--����
	 *		,A.BUNKA_NAME			--����
	 *		,A.SAIMOKU_NAME			--�ז�
	 *		,A.BUNKASAIMOKU_CD2		--�זڔԍ�2
	 *		,A.BUNYA_NAME2			--����2
	 *		,A.BUNKA_NAME2			--����2
	 *		,A.SAIMOKU_NAME2			--�ז�2
	 *		,A.KANTEN_NO			--���E�̊ϓ_�ԍ�
	 *		,A.KANTEN				--���E�̊ϓ_
	 *		,A.KANTEN_RYAKU			--���E�̊ϓ_����
	 *		,A.KEIHI1				--1�N�ڌ����o��
	 *		,A.BIHINHI1			--1�N�ڐݔ����i��
	 *		,A.SHOMOHINHI1			--1�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI1			--1�N�ڍ�������
	 *		,A.GAIKOKURYOHI1			--1�N�ڊO������
	 *		,A.RYOHI1				--1�N�ڗ���
	 *		,A.SHAKIN1			--1�N�ڎӋ���
	 *		,A.SONOTA1			--1�N�ڂ��̑�
	 *		,A.KEIHI2				--2�N�ڌ����o��
	 *		,A.BIHINHI2			--2�N�ڐݔ����i��
	 *		,A.SHOMOHINHI2			--2�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI2			--2�N�ڍ�������
	 *		,A.GAIKOKURYOHI2			--2�N�ڊO������
	 *		,A.RYOHI2				--2�N�ڗ���
	 *		,A.SHAKIN2			--2�N�ڎӋ���
	 *		,A.SONOTA2			--2�N�ڂ��̑�
	 *		,A.KEIHI3				--3�N�ڌ����o��
	 *		,A.BIHINHI3			--3�N�ڐݔ����i��
	 *		,A.SHOMOHINHI3			--3�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI3			--3�N�ڍ�������
	 *		,A.GAIKOKURYOHI3			--3�N�ڊO������
	 *		,A.RYOHI3				--3�N�ڗ���
	 *		,A.SHAKIN3			--3�N�ڎӋ���
	 *		,A.SONOTA3			--3�N�ڂ��̑�
	 *		,A.KEIHI4				--4�N�ڌ����o��
	 *		,A.BIHINHI4			--4�N�ڐݔ����i��
	 *		,A.SHOMOHINHI4			--4�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI4			--4�N�ڍ�������
	 *		,A.GAIKOKURYOHI4			--4�N�ڊO������
	 *		,A.RYOHI4				--4�N�ڗ���
	 *		,A.SHAKIN4			--4�N�ڎӋ���
	 *		,A.SONOTA4			--4�N�ڂ��̑�
	 *		,A.KEIHI5				--5�N�ڌ����o��
	 *		,A.BIHINHI5			--5�N�ڐݔ����i��
	 *		,A.SHOMOHINHI5			--5�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI5			--5�N�ڍ�������
	 *		,A.GAIKOKURYOHI5			--5�N�ڊO������
	 *		,A.RYOHI5				--5�N�ڗ���
	 *		,A.SHAKIN5			--5�N�ڎӋ���
	 *		,A.SONOTA5			--5�N�ڂ��̑�
	 *		,A.KEIHI_TOTAL			--���v-�����o��
	 *		,A.BIHINHI_TOTAL			--���v-�ݔ����i��
	 *		,A.SHOMOHINHI_TOTAL		--���v-���Օi��
	 *		,A.KOKUNAIRYOHI_TOTAL		--���v-��������
	 *		,A.GAIKOKURYOHI_TOTAL		--���v-�O������
	 *		,A.RYOHI_TOTAL			--���v-����
	 *		,A.SHAKIN_TOTAL			--���v-�Ӌ���
	 *		,A.SONOTA_TOTAL			--���v-���̑�
	 *		,A.SOSHIKI_KEITAI_NO		--�����g�D�̌`�Ԕԍ�
	 *		,A.SOSHIKI_KEITAI			--�����g�D�̌`��
	 *		,A.BUNTANKIN_FLG			--���S���̗L��
	 *		,A.KOYOHI				--�����x���Ҍٗp�o��
	 *		,A.KENKYU_NINZU			--�����Ґ�
	 *		,A.TAKIKAN_NINZU			--���@�ւ̕��S�Ґ�
	 *		,A.SHINSEI_KUBUN			--�V�K�p���敪
	 *		,A.KADAI_NO_KEIZOKU		--�p�����̌����ۑ�ԍ�
	 *		,A.SHINSEI_FLG_NO			--�p�����̌����ۑ�ԍ�
	 *		,A.SHINSEI_FLG			--�\���̗L��
	 *		,A.KADAI_NO_SAISYU			--�ŏI�N�x�ۑ�ԍ�
	 *		,A.KAIJIKIBO_FLG_NO		--�J����]�̗L���ԍ�
	 *		,A.KAIJIKIBO_FLG			--�J����]�̗L��
	 *		,A.KAIGAIBUNYA_CD			--�C�O����R�[�h
	 *		,A.KAIGAIBUNYA_NAME		--�C�O���얼��
	 *		,A.KAIGAIBUNYA_NAME_RYAKU		--�C�O���엪��
	 *		,A.KANREN_SHIMEI1		--�֘A����̌�����-����1
	 *		,A.KANREN_KIKAN1		--�֘A����̌�����-�����@��1
	 *		,A.KANREN_BUKYOKU1		--�֘A����̌�����-��������1
	 *		,A.KANREN_SHOKU1		--�֘A����̌�����-�E��1
	 *		,A.KANREN_SENMON1		--�֘A����̌�����-��啪��1
	 *		,A.KANREN_TEL1		--�֘A����̌�����-�Ζ���d�b�ԍ�1
	 *		,A.KANREN_JITAKUTEL1	--�֘A����̌�����-����d�b�ԍ�1
	 *		,A.KANREN_MAIL1		--�֘A����̌�����-E-mail1
	 *		,A.KANREN_SHIMEI2		--�֘A����̌�����-����2
	 *		,A.KANREN_KIKAN2		--�֘A����̌�����-�����@��2
	 *		,A.KANREN_BUKYOKU2		--�֘A����̌�����-��������2
	 *		,A.KANREN_SHOKU2		--�֘A����̌�����-�E��2
	 *		,A.KANREN_SENMON2		--�֘A����̌�����-��啪��2
	 *		,A.KANREN_TEL2		--�֘A����̌�����-�Ζ���d�b�ԍ�2
	 *		,A.KANREN_JITAKUTEL2	--�֘A����̌�����-����d�b�ԍ�2
	 *		,A.KANREN_MAIL2		--�֘A����̌�����-E-mail2
	 *		,A.KANREN_SHIMEI3		--�֘A����̌�����-����3
	 *		,A.KANREN_KIKAN3		--�֘A����̌�����-�����@��3
	 *		,A.KANREN_BUKYOKU3		--�֘A����̌�����-��������3
	 *		,A.KANREN_SHOKU3		--�֘A����̌�����-�E��3
	 *		,A.KANREN_SENMON3		--�֘A����̌�����-��啪��3
	 *		,A.KANREN_TEL3		--�֘A����̌�����-�Ζ���d�b�ԍ�3
	 *		,A.KANREN_JITAKUTEL3	--�֘A����̌�����-����d�b�ԍ�3
	 *		,A.KANREN_MAIL3		--�֘A����̌�����-E-mail3
	 *		,A.XML_PATH			--XML�̊i�[�p�X
	 *		,A.PDF_PATH			--PDF�̊i�[�p�X
	 *		,A.JURI_KEKKA			--�󗝌���
	 *		,A.JURI_BIKO			--�󗝌��ʔ��l
	 *		,A.SUISENSHO_PATH			--���E���̊i�[�p�X
	 *		,A.KEKKA1_ABC			--�P���R������(ABC)
	 *		,A.KEKKA1_TEN			--�P���R������(�_��)
	 *		,A.KEKKA1_TEN_SORTED		--�P���R������(�_����)
	 *		,A.SHINSA1_BIKO			--�P���R�����l
	 *		,A.KEKKA2				--�Q���R������
	 *		,A.SOU_KEHI			--���o��i�w�U���́j
	 *		,A.SHONEN_KEHI			--���N�x�o��i�w�U���́j
	 *		,A.SHINSA2_BIKO			--�Ɩ��S���ҋL����
	 *		,A.JOKYO_ID			--�\����ID
	 *		,A.SAISHINSEI_FLG			--�Đ\���t���O
	 *		,A.DEL_FLG			--�폜�t���O
	 *	FROM
	 *		SHINSEIDATAKANRI 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>������shinseiDataPk�̕ϐ�SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(2)�\���f�[�^��2���R�����ʂƂ���<b>ShinsaKekka2ndInfo</b>�Ɋi�[���A�ԋp����B<br /><br />
	 * �@�@�i�[����l�́A�ȉ��̂��́B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * 	SystemNo			-- �V�X�e����t�ԍ�
	 * 	Kekka2			-- 2���R������
	 * 	SouKehi			-- ���o��
	 * 	ShonenKehi			-- ���N�x�o��
	 * 	Shinsa2Biko			-- �Ɩ��S���ҋL����</pre>
	 * </td></tr>
	 * </table><br />
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return 2���R�����ʂ�����ShinsaKekka2ndInfo
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getShinsaKekka2nd(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public ShinsaKekka2ndInfo getShinsaKekka2nd(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			
			//�\���f�[�^�Ǘ�DAO
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//�Y���\���f�[�^���擾����
			ShinseiDataInfo shinseiDataInfo = null;
			try{
				shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�\�����Ǘ��f�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//2���R�����ʁi�Q�Ɨp�j�̐���
			ShinsaKekka2ndInfo kekka2ndInfo = new ShinsaKekka2ndInfo();
			kekka2ndInfo.setSystemNo(shinseiDataInfo.getSystemNo());		//�V�X�e����t�ԍ�
			kekka2ndInfo.setKekka2(shinseiDataInfo.getKekka2());			//2���R������
			kekka2ndInfo.setSouKehi(shinseiDataInfo.getSouKehi());			//���o��
			kekka2ndInfo.setShonenKehi(shinseiDataInfo.getShonenKehi());	//���N�x�o��
			kekka2ndInfo.setShinsa2Biko(shinseiDataInfo.getShinsa2Biko());	//�Ɩ��S���ҋL����
			
			return kekka2ndInfo;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
	}
	
	
	
	/**
	 * �R�����ʏ��̎擾.<br /><br />
	 * 
	 * 
	 * �@(1)��������������SQL���𐶐����A�o�b�N�A�b�v�p�̃f�[�^�x�[�X����Y���\���f�[�^���擾����B<br />
	 * �@�@�@(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 *		,A.UKETUKE_NO			--�\���ԍ�
	 *		,A.JIGYO_ID			--����ID
	 *		,A.NENDO				--�N�x
	 *		,A.KAISU				--��
	 *		,A.JIGYO_NAME			--���Ɩ�
	 *		,A.SHINSEISHA_ID			--�\����ID
	 *		,A.SAKUSEI_DATE			--�\�����쐬��
	 *		,A.SHONIN_DATE			--�����@�֏��F��
	 *		,A.JYURI_DATE			--�w�U�󗝓�
	 *		,A.NAME_KANJI_SEI			--�\���Ҏ����i������-���j
	 *		,A.NAME_KANJI_MEI			--�\���Ҏ����i������-���j
	 *		,A.NAME_KANA_SEI			--�\���Ҏ����i�t���K�i-���j
	 *		,A.NAME_KANA_MEI			--�\���Ҏ����i�t���K�i-���j
	 *		,A.NAME_RO_SEI			--�\���Ҏ����i���[�}��-���j
	 *		,A.NAME_RO_MEI			--�\���Ҏ����i���[�}��-���j
	 *		,A.NENREI				--�N��
	 *		,A.KENKYU_NO			--�\���Ҍ����Ҕԍ�
	 *		,A.SHOZOKU_CD			--�����@�փR�[�h
	 *		,A.SHOZOKU_NAME			--�����@�֖�
	 *		,A.SHOZOKU_NAME_RYAKU		--�����@�֖��i���́j
	 *		,A.BUKYOKU_CD			--���ǃR�[�h
	 *		,A.BUKYOKU_NAME			--���ǖ�
	 *		,A.BUKYOKU_NAME_RYAKU		--���ǖ��i���́j
	 *		,A.SHOKUSHU_CD			--�E���R�[�h
	 *		,A.SHOKUSHU_NAME_KANJI		--�E���i�a���j
	 *		,A.SHOKUSHU_NAME_RYAKU		--�E���i���́j
	 *		,A.ZIP				--�X�֔ԍ�
	 *		,A.ADDRESS			--�Z��
	 *		,A.TEL				--TEL
	 *		,A.FAX				--FAX
	 *		,A.EMAIL				--E-Mail
	 *		,A.SENMON				--���݂̐��
	 *		,A.GAKUI				--�w��
	 *		,A.BUNTAN				--�������S
	 *		,A.KADAI_NAME_KANJI		--�����ۑ薼(�a���j
	 *		,A.KADAI_NAME_EIGO			--�����ۑ薼(�p���j
	 *		,A.JIGYO_KUBUN			--���Ƌ敪
	 *		,A.SHINSA_KUBUN			--�R���敪
	 *		,A.SHINSA_KUBUN_MEISHO		--�R���敪����
	 *		,A.BUNKATSU_NO			--�����ԍ�
	 *		,A.BUNKATSU_NO_MEISHO		--�����ԍ�����
	 *		,A.KENKYU_TAISHO			--�����Ώۂ̗ތ^
	 *		,A.KEI_NAME_NO			--�n���̋敪�ԍ�
	 *		,A.KEI_NAME			--�n���̋敪
	 *		,A.KEI_NAME_RYAKU			--�n���̋敪����
	 *		,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 *		,A.BUNYA_NAME			--����
	 *		,A.BUNKA_NAME			--����
	 *		,A.SAIMOKU_NAME			--�ז�
	 *		,A.BUNKASAIMOKU_CD2		--�זڔԍ�2
	 *		,A.BUNYA_NAME2			--����2
	 *		,A.BUNKA_NAME2			--����2
	 *		,A.SAIMOKU_NAME2			--�ז�2
	 *		,A.KANTEN_NO			--���E�̊ϓ_�ԍ�
	 *		,A.KANTEN				--���E�̊ϓ_
	 *		,A.KANTEN_RYAKU			--���E�̊ϓ_����
	 *		,A.KEIHI1				--1�N�ڌ����o��
	 *		,A.BIHINHI1			--1�N�ڐݔ����i��
	 *		,A.SHOMOHINHI1			--1�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI1			--1�N�ڍ�������
	 *		,A.GAIKOKURYOHI1			--1�N�ڊO������
	 *		,A.RYOHI1				--1�N�ڗ���
	 *		,A.SHAKIN1			--1�N�ڎӋ���
	 *		,A.SONOTA1			--1�N�ڂ��̑�
	 *		,A.KEIHI2				--2�N�ڌ����o��
	 *		,A.BIHINHI2			--2�N�ڐݔ����i��
	 *		,A.SHOMOHINHI2			--2�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI2			--2�N�ڍ�������
	 *		,A.GAIKOKURYOHI2			--2�N�ڊO������
	 *		,A.RYOHI2				--2�N�ڗ���
	 *		,A.SHAKIN2			--2�N�ڎӋ���
	 *		,A.SONOTA2			--2�N�ڂ��̑�
	 *		,A.KEIHI3				--3�N�ڌ����o��
	 *		,A.BIHINHI3			--3�N�ڐݔ����i��
	 *		,A.SHOMOHINHI3			--3�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI3			--3�N�ڍ�������
	 *		,A.GAIKOKURYOHI3			--3�N�ڊO������
	 *		,A.RYOHI3				--3�N�ڗ���
	 *		,A.SHAKIN3			--3�N�ڎӋ���
	 *		,A.SONOTA3			--3�N�ڂ��̑�
	 *		,A.KEIHI4				--4�N�ڌ����o��
	 *		,A.BIHINHI4			--4�N�ڐݔ����i��
	 *		,A.SHOMOHINHI4			--4�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI4			--4�N�ڍ�������
	 *		,A.GAIKOKURYOHI4			--4�N�ڊO������
	 *		,A.RYOHI4				--4�N�ڗ���
	 *		,A.SHAKIN4			--4�N�ڎӋ���
	 *		,A.SONOTA4			--4�N�ڂ��̑�
	 *		,A.KEIHI5				--5�N�ڌ����o��
	 *		,A.BIHINHI5			--5�N�ڐݔ����i��
	 *		,A.SHOMOHINHI5			--5�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI5			--5�N�ڍ�������
	 *		,A.GAIKOKURYOHI5			--5�N�ڊO������
	 *		,A.RYOHI5				--5�N�ڗ���
	 *		,A.SHAKIN5			--5�N�ڎӋ���
	 *		,A.SONOTA5			--5�N�ڂ��̑�
	 *		,A.KEIHI_TOTAL			--���v-�����o��
	 *		,A.BIHINHI_TOTAL			--���v-�ݔ����i��
	 *		,A.SHOMOHINHI_TOTAL		--���v-���Օi��
	 *		,A.KOKUNAIRYOHI_TOTAL		--���v-��������
	 *		,A.GAIKOKURYOHI_TOTAL		--���v-�O������
	 *		,A.RYOHI_TOTAL			--���v-����
	 *		,A.SHAKIN_TOTAL			--���v-�Ӌ���
	 *		,A.SONOTA_TOTAL			--���v-���̑�
	 *		,A.SOSHIKI_KEITAI_NO		--�����g�D�̌`�Ԕԍ�
	 *		,A.SOSHIKI_KEITAI			--�����g�D�̌`��
	 *		,A.BUNTANKIN_FLG			--���S���̗L��
	 *		,A.KOYOHI				--�����x���Ҍٗp�o��
	 *		,A.KENKYU_NINZU			--�����Ґ�
	 *		,A.TAKIKAN_NINZU			--���@�ւ̕��S�Ґ�
	 *		,A.SHINSEI_KUBUN			--�V�K�p���敪
	 *		,A.KADAI_NO_KEIZOKU		--�p�����̌����ۑ�ԍ�
	 *		,A.SHINSEI_FLG_NO			--�p�����̌����ۑ�ԍ�
	 *		,A.SHINSEI_FLG			--�\���̗L��
	 *		,A.KADAI_NO_SAISYU			--�ŏI�N�x�ۑ�ԍ�
	 *		,A.KAIJIKIBO_FLG_NO		--�J����]�̗L���ԍ�
	 *		,A.KAIJIKIBO_FLG			--�J����]�̗L��
	 *		,A.KAIGAIBUNYA_CD			--�C�O����R�[�h
	 *		,A.KAIGAIBUNYA_NAME		--�C�O���얼��
	 *		,A.KAIGAIBUNYA_NAME_RYAKU		--�C�O���엪��
	 *		,A.KANREN_SHIMEI1		--�֘A����̌�����-����1
	 *		,A.KANREN_KIKAN1		--�֘A����̌�����-�����@��1
	 *		,A.KANREN_BUKYOKU1		--�֘A����̌�����-��������1
	 *		,A.KANREN_SHOKU1		--�֘A����̌�����-�E��1
	 *		,A.KANREN_SENMON1		--�֘A����̌�����-��啪��1
	 *		,A.KANREN_TEL1		--�֘A����̌�����-�Ζ���d�b�ԍ�1
	 *		,A.KANREN_JITAKUTEL1	--�֘A����̌�����-����d�b�ԍ�1
	 *		,A.KANREN_MAIL1		--�֘A����̌�����-E-mail1
	 *		,A.KANREN_SHIMEI2		--�֘A����̌�����-����2
	 *		,A.KANREN_KIKAN2		--�֘A����̌�����-�����@��2
	 *		,A.KANREN_BUKYOKU2		--�֘A����̌�����-��������2
	 *		,A.KANREN_SHOKU2		--�֘A����̌�����-�E��2
	 *		,A.KANREN_SENMON2		--�֘A����̌�����-��啪��2
	 *		,A.KANREN_TEL2		--�֘A����̌�����-�Ζ���d�b�ԍ�2
	 *		,A.KANREN_JITAKUTEL2	--�֘A����̌�����-����d�b�ԍ�2
	 *		,A.KANREN_MAIL2		--�֘A����̌�����-E-mail2
	 *		,A.KANREN_SHIMEI3		--�֘A����̌�����-����3
	 *		,A.KANREN_KIKAN3		--�֘A����̌�����-�����@��3
	 *		,A.KANREN_BUKYOKU3		--�֘A����̌�����-��������3
	 *		,A.KANREN_SHOKU3		--�֘A����̌�����-�E��3
	 *		,A.KANREN_SENMON3		--�֘A����̌�����-��啪��3
	 *		,A.KANREN_TEL3		--�֘A����̌�����-�Ζ���d�b�ԍ�3
	 *		,A.KANREN_JITAKUTEL3	--�֘A����̌�����-����d�b�ԍ�3
	 *		,A.KANREN_MAIL3		--�֘A����̌�����-E-mail3
	 *		,A.XML_PATH			--XML�̊i�[�p�X
	 *		,A.PDF_PATH			--PDF�̊i�[�p�X
	 *		,A.JURI_KEKKA			--�󗝌���
	 *		,A.JURI_BIKO			--�󗝌��ʔ��l
	 *		,A.SUISENSHO_PATH			--���E���̊i�[�p�X
	 *		,A.KEKKA1_ABC			--�P���R������(ABC)
	 *		,A.KEKKA1_TEN			--�P���R������(�_��)
	 *		,A.KEKKA1_TEN_SORTED		--�P���R������(�_����)
	 *		,A.SHINSA1_BIKO			--�P���R�����l
	 *		,A.KEKKA2				--�Q���R������
	 *		,A.SOU_KEHI			--���o��i�w�U���́j
	 *		,A.SHONEN_KEHI			--���N�x�o��i�w�U���́j
	 *		,A.SHINSA2_BIKO			--�Ɩ��S���ҋL����
	 *		,A.JOKYO_ID			--�\����ID
	 *		,A.SAISHINSEI_FLG			--�Đ\���t���O
	 *		,A.DEL_FLG			--�폜�t���O
	 *	FROM
	 *		SHINSEIDATAKANRI 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>������shinseiDataPk�̕ϐ�SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(2)��������������SQL���𐶐����A�o�b�N�A�b�v�p�̃f�[�^�x�[�X����Y���R�����ʃf�[�^���擾����B<br />
	 * �@�@�@(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 *		,A.UKETUKE_NO			--�\���ԍ�
	 *		,A.SHINSAIN_NO			--�R�����ԍ�
	 *		,A.JIGYO_KUBUN			--���Ƌ敪
	 *		,A.SEQ_NO				--�V�[�P���X�ԍ�
	 *		,A.SHINSA_KUBUN			--�R���敪
	 *		,A.SHINSAIN_NAME_KANJI_SEI		--�R�������i�����|���j
	 *		,A.SHINSAIN_NAME_KANJI_MEI		--�R�������i�����|���j
	 *		,A.NAME_KANA_SEI			--�R�������i�t���K�i�|���j
	 *		,A.NAME_KANA_MEI			--�R�������i�t���K�i�|���j
	 *		,A.SHOZOKU_NAME			--�R���������@�֖�
	 *		,A.BUKYOKU_NAME			--�R�������ǖ�
	 *		,A.SHOKUSHU_NAME			--�R�����E��
	 *		,A.JIGYO_ID			--����ID
	 *		,A.JIGYO_NAME			--���Ɩ�
	 *		,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 *		,A.EDA_NO				--�}��
	 *		,A.CHECKDIGIT			--�`�F�b�N�f�W�b�g
	 *		,A.KEKKA_ABC			--�����]���iABC�j
	 *		,A.KEKKA_TEN			--�����]���i�_���j
	 *		,NVL(REPLACE(A.KEKKA_TEN,'-','0'),'-1')SORT_KEKKA_TEN
	 *		--�\�[�g�p�B�R�����ʁi�_���j�̒lNULL��'-1'�A'-'��'0'�ɒu���j
	 *		,A.COMMENT1			--�R�����g1
	 *		,A.COMMENT2			--�R�����g2
	 *		,A.COMMENT3			--�R�����g3
	 *		,A.COMMENT4			--�R�����g4
	 *		,A.COMMENT5			--�R�����g5
	 *		,A.COMMENT6			--�R�����g6
	 *		,A.KENKYUNAIYO			--�������e
	 *		,A.KENKYUKEIKAKU			--�����v��
	 *		,A.TEKISETSU_KAIGAI		--�K�ؐ�-�C�O
	 *		,A.TEKISETSU_KENKYU1		--�K�ؐ�-�����i1�j
	 *		,A.TEKISETSU			--�K�ؐ�
	 *		,A.DATO				--�Ó���
	 *		,A.SHINSEISHA			--������\��
	 *		,A.KENKYUBUNTANSHA			--�������S��
	 *		,A.HITOGENOMU			--�q�g�Q�m��
	 *		,A.TOKUTEI			--������
	 *		,A.HITOES				--�q�gES�זE
	 *		,A.KUMIKAE			--��`�q�g��������
	 *		,A.CHIRYO				--��`�q���×Տ�����
	 *		,A.EKIGAKU			--�u�w����
	 *		,A.COMMENTS			--�R�����g
	 *		,A.TENPU_PATH			--�Y�t�t�@�C���i�[�p�X
	 *		,A.SHINSA_JOKYO			--�R����
	 *		,A.BIKO				--���l
	 *	FROM
	 *		SHINSAKEKKA 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?
	 *	ORDER BY
	 *		KEKKA_ABCASC			--�����]���iABC�j�̏���
	 *		,SORT_KEKKA_TENDESC		--�����]���i�_���j�̍~��
	 *		,SHINSAIN_NOASC			--�R�����ԍ��̏���
	 *		,JIGYO_KUBUNASC			--���Ƌ敪�̏���</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>������shinseiDataPk�̕ϐ�SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(3)��������������SQL���𐶐����A�o�b�N�A�b�v�p�̃f�[�^�x�[�X����Y�����ƊǗ��f�[�^���擾����B<br />
	 * �@�@�@(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.JIGYO_ID			--����ID
	 *		,A.NENDO				--�N�x
	 *		,A.KAISU				--��
	 *		,A.JIGYO_NAME			--���Ɩ�
	 *		,A.JIGYO_KUBUN			--���Ƌ敪
	 *		,A.SHINSA_KUBUN			--�R���敪
	 *		,A.TANTOKA_NAME			--�Ɩ��S����
	 *		,A.TANTOKAKARI			--�Ɩ��S���W��
	 *		,A.TOIAWASE_NAME			--�₢���킹��S���Җ�
	 *		,A.TOIAWASE_TEL			--�₢���킹��d�b�ԍ�
	 *		,A.TOIAWASE_EMAIL			--�₢���킹��E-mail
	 *		,A.UKETUKEKIKAN_START		--�w�U��t���ԁi�J�n�j
	 *		,A.UKETUKEKIKAN_END		--�w�U��t���ԁi�I���j
	 *		,A.SHINSAKIGEN			--�R������
	 *		,A.TENPU_NAME			--�Y�t������
	 *		,A.TENPU_WIN		--�Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *		,A.TENPU_MAC		--�Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *		,A.HYOKA_FILE_FLG			--�]���p�t�@�C���L��
	 *		,A.HYOKA_FILE			--�]���p�t�@�C���i�[�t�H���_
	 *		,A.KOKAI_FLG			--���J�t���O
	 *		,A.KESSAI_NO			--���J���ٔԍ�
	 *		,A.KOKAI_ID			--���J�m���ID
	 *		,A.HOKAN_DATE			--�f�[�^�ۊǓ�
	 *		,A.YUKO_DATE			--�ۊǗL������
	 *		,A.BIKO				--���l
	 *		,A.DEL_FLG			--�폜�t���O
	 *	FROM
	 *		JIGYOKANRI 'dbLink' A
	 *	WHERE
	 *		JIGYO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>JigyoKanriPk�̕ϐ�JigyoId</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(4)�擾�����\���f�[�^�E�R�����ʃf�[�^�E���ƊǗ��f�[�^�̒l���A<b>ShinsaKekkaInputInfo</b>�Ɋi�[����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * 	SystemNo			-- �V�X�e����t�ԍ�
	 * 	ShinsainNo			-- �R�����ԍ�
	 * 	JigyoKubun			-- ���Ƌ敪
	 * 	Nendo			-- �N�x
	 * 	Kaisu			-- ��
	 * 	JigyoId			-- �C�O�������얼�i��Ձj
	 * 	BunkaSaimokuCd			-- �זڔԍ��i��Ձj
	 * 	SaimokuName			-- �זږ��i��Ձj
	 * 	JigyoId			-- ����ID
	 * 	JigyoName			-- ������ږ�
	 * 	UketukeNo			-- �\���ԍ�
	 * 	KadaiNameKanji			-- �����ۑ薼
	 * 	NameKanjiSei			-- �\���Җ�-��
	 * 	NameKanjiMei			-- �\���Җ�-��
	 * 	ShozokuName			-- �����@�֖�
	 * 	BukyokuName			-- ���ǖ�
	 * 	ShokushuNameKanji			-- �E��
	 * 	Kanten			-- ���E�̊ϓ_
	 * 	KeiName			-- �n���̋敪
	 * 	KekkaAbc			-- �����]�_�iABC�j
	 * 	KekkaTen			-- �����]�_�i�_���j
	 * 	Comment1			-- �R�����g1
	 * 	Comment2			-- �R�����g2
	 * 	Comment3			-- �R�����g3
	 * 	Comment4			-- �R�����g4
	 * 	Comment5			-- �R�����g5
	 * 	Comment6			-- �R�����g6
	 * 	KenkyuNaiyo			-- �������e�i��Ձj
	 * 	KenkyuKeikaku			-- �����v��i��Ձj
	 * 	TekisetsuKaigai			-- �K�ؐ�-�C�O�i��Ձj
	 * 	TekisetsuKenkyu1			-- �K�ؐ�-����(1)�i��Ձj
	 * 	Tekisetsu			-- �K�ؐ��i��Ձj
	 * 	Dato			-- �Ó����i��Ձj
	 * 	Shinseisha			-- ������\�ҁi��Ձj
	 * 	KenkyuBuntansha			-- �������S�ҁi��Ձj
	 * 	Hitogenomu			-- �q�g�Q�m���i��Ձj
	 * 	Tokutei			-- ������i��Ձj
	 * 	HitoEs			-- �q�gES�זE�i��Ձj
	 * 	Kumikae			-- ��`�q�g���������i��Ձj
	 * 	Chiryo			-- ��`�q���×Տ������i��Ձj
	 * 	Ekigaku			-- �u�w�����i��Ձj
	 * 	Comments			-- �R�����g�i��Ձj
	 * 	TenpuFlg			-- �Y�t�t�@�C���i�[�t���O
	 * 	HyokaFileFlg			-- �]���p�t�@�C���L��</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * 
	 * �@(5)(4)�Œl���i�[����<b>ShinsaKekkaInputInfo</b>�ɁA�Y�t�t�@�C�����E�����]���iABC�j�̕\�����x������������B<br />
	 * �@�@�@�Y�t�t�@�C�����́A�擾�����R�����ʃf�[�^����"�Y�t�t�@�C���i�[�p�X"�̒l����擾����B<br />
	 * �@�@�@�����]���iABC�j�̕\�����x�����́A�ȉ���SQL���𔭍s���Ď擾����B<br />
	 * �@�@�@(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.ATAI
	 *		,A.NAME
	 *		,A.RYAKU
	 *		,A.SORT
	 *		,A.BIKO
	 *	FROM
	 *		MASTER_LABEL A
	 *	WHERE
	 *		A.LABEL_KUBUN=?
	 *		AND A.ATAI=?
	 *	ORDER BY
	 *		SORT</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>���x���敪</td><td>���e����"KEKKA_ABC"</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�l</td><td>ShinsaKekkaInputInfo�̕ϐ�KekkaAbc</td></tr>
	 * </table><br /><br />
	 * 
	 * �@(6)ShinsaKekkaInputInfo��ԋp����B
	 * @param userInfo UserInfo
	 * @param shinsaKekkaPk ShinsaKekkaPk
	 * @return �R�����ʏ��i�R�����ʓ��͉�ʗp�j��ێ�����ShinsaKekkaInputInfo
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#select1stShinsaKekka(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsaKekkaPk)
	 */
	public ShinsaKekkaInputInfo select1stShinsaKekka(
		UserInfo userInfo,
		ShinsaKekkaPk shinsaKekkaPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			
			//�\���f�[�^�Ǘ�DAO
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//�Y���\���f�[�^���擾����
			ShinseiDataPk shinseiDataPk = new ShinseiDataPk(shinsaKekkaPk.getSystemNo());
			ShinseiDataInfo shinseiDataInfo = null;
			try{
				shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�\�����Ǘ��f�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
		
			//�R������DAO
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//�Y���R�����ʃf�[�^���擾����
			ShinsaKekkaInfo shinsaKekkaInfo = null;
			try{
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection, shinsaKekkaPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//���ƊǗ�DAO
			JigyoKanriInfoDao jigyoDao = new JigyoKanriInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			//�Y�����ƊǗ��f�[�^���擾����
			JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(shinseiDataInfo.getJigyoId());
			
			JigyoKanriInfo jigyoKanriInfo = null;
			try{
				jigyoKanriInfo = jigyoDao.selectJigyoKanriInfo(connection, jigyoKanriPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"���ƊǗ��f�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//---�R�����ʓ��̓I�u�W�F�N�g�̐���
			ShinsaKekkaInputInfo info = new ShinsaKekkaInputInfo();
			info.setSystemNo(shinsaKekkaPk.getSystemNo());									//�V�X�e����t�ԍ�
			info.setShinsainNo(shinsaKekkaPk.getShinsainNo());								//�R�����ԍ�
			info.setJigyoKubun(shinsaKekkaPk.getJigyoKubun());								//���Ƌ敪
			
			info.setNendo(shinseiDataInfo.getNendo());										//�N�x
			info.setKaisu(shinseiDataInfo.getKaisu());										//��
			info.setJigyoId(shinseiDataInfo.getJigyoId());									//�C�O�������얼�i��Ձj
			info.setBunkaSaimokuCd(shinseiDataInfo.getKadaiInfo().getBunkaSaimokuCd());		//�זڔԍ��i��Ձj
			info.setSaimokuName(shinseiDataInfo.getKadaiInfo().getSaimokuName());			//�זږ��i��Ձj
			info.setJigyoId(shinseiDataInfo.getJigyoId());									//����ID
			info.setJigyoName(shinseiDataInfo.getJigyoName());								//������ږ�
			info.setUketukeNo(shinseiDataInfo.getUketukeNo());								//�\���ԍ�
			info.setKadaiNameKanji(shinseiDataInfo.getKadaiInfo().getKadaiNameKanji());		//�����ۑ薼
			info.setNameKanjiSei(shinseiDataInfo.getDaihyouInfo().getNameKanjiSei());		//�\���Җ�-��
			info.setNameKanjiMei(shinseiDataInfo.getDaihyouInfo().getNameKanjiMei());		//�\���Җ�-��
			info.setShozokuName(shinseiDataInfo.getDaihyouInfo().getShozokuName());			//�����@�֖�
			info.setBukyokuName(shinseiDataInfo.getDaihyouInfo().getBukyokuName());			//���ǖ�
			info.setShokushuName(shinseiDataInfo.getDaihyouInfo().getShokushuNameKanji());	//�E��
			info.setKanten(shinseiDataInfo.getKadaiInfo().getKanten());						//���E�̊ϓ_
			info.setKeiName(shinseiDataInfo.getKadaiInfo().getKeiName());					//�n���̋敪
			
			//2005.09.13 iso ��Փ��ǉ��Ή�
			info.setSoshikiKeitaiNo(shinseiDataInfo.getSoshikiKeitaiNo());					//�����g�D�̌`�Ԕԍ�
			info.setShinseiFlgNo(shinseiDataInfo.getShinseiFlgNo());						//�����v��ŏI�N�x�O�N�x�̉���
			info.setBuntankinFlg(shinseiDataInfo.getBuntankinFlg());						//���S���̗L��
			info.setBunkatsuNo(shinseiDataInfo.getKadaiInfo().getBunkatsuNo());				//�����ԍ�
			info.setKaigaibunyaCd(shinseiDataInfo.getKaigaibunyaCd());						//�C�O����R�[�h
			info.setKaigaibunyaName(shinseiDataInfo.getKaigaibunyaName());					//�C�O���얼
			
			info.setKekkaAbc(shinsaKekkaInfo.getKekkaAbc());								//�����]�_�iABC�j
			info.setKekkaTen(shinsaKekkaInfo.getKekkaTen());								//�����]�_�i�_���j
			info.setComment1(shinsaKekkaInfo.getComment1());								//�R�����g1
			info.setComment2(shinsaKekkaInfo.getComment2());								//�R�����g2
			info.setComment3(shinsaKekkaInfo.getComment3());								//�R�����g3
			info.setComment4(shinsaKekkaInfo.getComment4());								//�R�����g4
			info.setComment5(shinsaKekkaInfo.getComment5());								//�R�����g5
			info.setComment6(shinsaKekkaInfo.getComment6());								//�R�����g6
			info.setKenkyuNaiyo(shinsaKekkaInfo.getKenkyuNaiyo());							//�������e�i��Ձj
			info.setKenkyuKeikaku(shinsaKekkaInfo.getKenkyuKeikaku());						//�����v��i��Ձj
			info.setTekisetsuKaigai(shinsaKekkaInfo.getTekisetsuKaigai());					//�K�ؐ�-�C�O�i��Ձj
			info.setTekisetsuKenkyu1(shinsaKekkaInfo.getTekisetsuKenkyu1());					//�K�ؐ�-����(1)�i��Ձj
			info.setTekisetsu(shinsaKekkaInfo.getTekisetsu());								//�K�ؐ��i��Ձj
			info.setDato(shinsaKekkaInfo.getDato());										//�Ó����i��Ձj
			info.setShinseisha(shinsaKekkaInfo.getShinseisha());							//������\�ҁi��Ձj
			info.setKenkyuBuntansha(shinsaKekkaInfo.getKenkyuBuntansha());					//�������S�ҁi��Ձj	
			info.setHitogenomu(shinsaKekkaInfo.getHitogenomu());							//�q�g�Q�m���i��Ձj
			info.setTokutei(shinsaKekkaInfo.getTokutei());									//������i��Ձj
			info.setHitoEs(shinsaKekkaInfo.getHitoEs());									//�q�gES�זE�i��Ձj
			info.setKumikae(shinsaKekkaInfo.getKumikae());									//��`�q�g���������i��Ձj
			info.setChiryo(shinsaKekkaInfo.getChiryo());									//��`�q���×Տ������i��Ձj
			info.setEkigaku(shinsaKekkaInfo.getEkigaku());									//�u�w�����i��Ձj							
			info.setComments(shinsaKekkaInfo.getComments());								//�R�����g�i��Ձj
			info.setTenpuFlg(shinsaKekkaInfo.getTenpuFlg());								//�Y�t�t�@�C���i�[�t���O
			info.setHyokaFileFlg(jigyoKanriInfo.getHyokaFileFlg());							//�]���p�t�@�C���L��

			//2005.09.13 iso ��Փ��ǉ��Ή�
			info.setJigyoCd(shinsaKekkaInfo.getJigyoId().substring(2,7));					//���ƃR�[�h
			
			//�Y�t�t�@�C�������Z�b�g
			String tenpuPath = shinsaKekkaInfo.getTenpuPath();
			if(tenpuPath != null && tenpuPath.length() != 0){
				info.setTenpuName(new File(tenpuPath).getName());
			}
						
			//�����]���iABC�j�̕\�����x�������Z�b�g
			if(info.getKekkaAbc() != null && info.getKekkaAbc().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KEKKA_ABC,
																	info.getKekkaAbc());		
					info.setKekkaAbcLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�����]���iABC�j���Z�b�g
					info.setKekkaAbcLabel(info.getKekkaAbc());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}			
			}

			//2005.09.13 iso ��Փ��ǉ��Ή�
			//�����]���i�_���j�̕\�����x�������Z�b�g
			if(info.getKekkaTen() != null && info.getKekkaTen().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KEKKA_TEN,
																	info.getKekkaTen());		
					info.setKekkaTenLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�����]���i�_���j���Z�b�g
					info.setKekkaTenLabel(info.getKekkaTen());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			
			//�����]���i�G��j�̕\�����x�������Z�b�g
			if(info.getKekkaTen() != null && info.getKekkaTen().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KEKKA_TEN_HOGA,
																	info.getKekkaTen());		
					info.setKekkaTenHogaLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�����]���i�G��j���Z�b�g
					info.setKekkaTenHogaLabel(info.getKekkaTen());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			
			//�������e�̕\�����x�������Z�b�g
			if(info.getKenkyuNaiyo() != null && info.getKenkyuNaiyo().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KENKYUNAIYO,
																	info.getKenkyuNaiyo());		
					info.setKenkyuNaiyoLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�������e���Z�b�g
					info.setKenkyuNaiyoLabel(info.getKenkyuNaiyo());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//�����v��̕\�����x�������Z�b�g
			if(info.getKenkyuKeikaku() != null && info.getKenkyuKeikaku().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KENKYUKEIKAKU,
																	info.getKenkyuKeikaku());		
					info.setKenkyuKeikakuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�����v����Z�b�g
					info.setKenkyuKeikakuLabel(info.getKenkyuKeikaku());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//�K�ؐ�-�C�O�̕\�����x�������Z�b�g
			if(info.getTekisetsuKaigai() != null && info.getTekisetsuKaigai().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TEKISETSU_KAIGAI,
																	info.getTekisetsuKaigai());		
					info.setTekisetsuKaigaiLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�K�ؐ�-�C�O���Z�b�g
					info.setTekisetsuKaigaiLabel(info.getTekisetsuKaigai());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//�K�ؐ�-����(1)�̕\�����x�������Z�b�g
			if(info.getTekisetsuKenkyu1() != null && info.getTekisetsuKenkyu1().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TEKISETSU_KENKYU1,
																	info.getTekisetsuKenkyu1());		
					info.setTekisetsuKenkyu1Label((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�K�ؐ�-����(1)���Z�b�g
					info.setTekisetsuKenkyu1Label(info.getTekisetsuKenkyu1());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//�K�ؐ��̕\�����x�������Z�b�g
			if(info.getTekisetsu() != null && info.getTekisetsu().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TEKISETSU,
																	info.getTekisetsu());		
					info.setTekisetsuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�K�ؐ����Z�b�g
					info.setTekisetsuLabel(info.getTekisetsu());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//�Ó����̕\�����x�������Z�b�g
			if(info.getDato() != null && info.getDato().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.DATO,
																	info.getDato());		
					info.setDatoLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�Ó������Z�b�g
					info.setDatoLabel(info.getDato());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//������\�҂̕\�����x�������Z�b�g
			if(info.getShinseisha() != null && info.getShinseisha().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.SHINSEISHA,
																	info.getShinseisha());		
					info.setShinseishaLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A������\�҂��Z�b�g
					info.setShinseishaLabel(info.getShinseisha());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//�������S�҂̕\�����x�������Z�b�g
			if(info.getKenkyuBuntansha() != null && info.getKenkyuBuntansha().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KENKYUBUNTANSHA,
																	info.getKenkyuBuntansha());		
					info.setKenkyuBuntanshaLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�������S�҂��Z�b�g
					info.setKenkyuBuntanshaLabel(info.getKenkyuBuntansha());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//�q�g�Q�m���̕\�����x�������Z�b�g
			if(info.getHitogenomu() != null && info.getHitogenomu().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.HITOGENOMU,
																	info.getHitogenomu());		
					info.setHitogenomuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�q�g�Q�m�����Z�b�g
					info.setHitogenomuLabel(info.getHitogenomu());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//������̕\�����x�������Z�b�g
			if(info.getTokutei() != null && info.getTokutei().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.TOKUTEI,
																	info.getTokutei());		
					info.setTokuteiLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A��������Z�b�g
					info.setTokuteiLabel(info.getTokutei());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//�q�g�d�r�זE�̕\�����x�������Z�b�g
			if(info.getHitoEs() != null && info.getHitoEs().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.HITOES,
																	info.getHitoEs());		
					info.setHitoEsLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�q�g�d�r�זE���Z�b�g
					info.setHitoEsLabel(info.getHitoEs());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//��`�q�g���������̕\�����x�������Z�b�g
			if(info.getKumikae() != null && info.getKumikae().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KUMIKAE,
																	info.getKumikae());		
					info.setKumikaeLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A��`�q�g�����������Z�b�g
					info.setKumikaeLabel(info.getKumikae());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//��`�q���×Տ������̕\�����x�������Z�b�g
			if(info.getChiryo() != null && info.getChiryo().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.CHIRYO,
																	info.getChiryo());		
					info.setChiryoLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A��`�q���×Տ��������Z�b�g
					info.setChiryoLabel(info.getChiryo());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			//�u�w�����̕\�����x�������Z�b�g
			if(info.getEkigaku() != null && info.getEkigaku().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.EKIGAKU,
																	info.getEkigaku());		
					info.setEkigakuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�u�w�������Z�b�g
					info.setEkigakuLabel(info.getEkigaku());
				}catch (DataAccessException e) {
					throw new ApplicationException(
						"���x���}�X�^���擾���ɗ�O���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}		
			}
			
			return info;
			
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	
	}
	
	
	
	/**
	 * �Y�t�t�@�C���̎擾.<br /><br />
	 * 
	 * 
	 * �@(1)��������������SQL���𐶐����A�o�b�N�A�b�v�p�̃f�[�^�x�[�X����Y���R�����ʏ����擾����B<br />
	 * �@�@�@(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 *		,A.UKETUKE_NO			--�\���ԍ�
	 *		,A.SHINSAIN_NO			--�R�����ԍ�
	 *		,A.JIGYO_KUBUN			--���Ƌ敪
	 *		,A.SEQ_NO				--�V�[�P���X�ԍ�
	 *		,A.SHINSA_KUBUN			--�R���敪
	 *		,A.SHINSAIN_NAME_KANJI_SEI		--�R�������i�����|���j
	 *		,A.SHINSAIN_NAME_KANJI_MEI		--�R�������i�����|���j
	 *		,A.NAME_KANA_SEI			--�R�������i�t���K�i�|���j
	 *		,A.NAME_KANA_MEI			--�R�������i�t���K�i�|���j
	 *		,A.SHOZOKU_NAME			--�R���������@�֖�
	 *		,A.BUKYOKU_NAME			--�R�������ǖ�
	 *		,A.SHOKUSHU_NAME			--�R�����E��
	 *		,A.JIGYO_ID			--����ID
	 *		,A.JIGYO_NAME			--���Ɩ�
	 *		,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 *		,A.EDA_NO				--�}��
	 *		,A.CHECKDIGIT			--�`�F�b�N�f�W�b�g
	 *		,A.KEKKA_ABC			--�����]���iABC�j
	 *		,A.KEKKA_TEN			--�����]���i�_���j
	 *		,A.COMMENT1			--�R�����g1
	 *		,A.COMMENT2			--�R�����g2
	 *		,A.COMMENT3			--�R�����g3
	 *		,A.COMMENT4			--�R�����g4
	 *		,A.COMMENT5			--�R�����g5
	 *		,A.COMMENT6			--�R�����g6
	 *		,A.KENKYUNAIYO			--�������e
	 *		,A.KENKYUKEIKAKU			--�����v��
	 *		,A.TEKISETSU_KAIGAI		--�K�ؐ�-�C�O
	 *		,A.TEKISETSU_KENKYU1		--�K�ؐ�-�����i1�j
	 *		,A.TEKISETSU			--�K�ؐ�
	 *		,A.DATO				--�Ó���
	 *		,A.SHINSEISHA			--������\��
	 *		,A.KENKYUBUNTANSHA			--�������S��
	 *		,A.HITOGENOMU			--�q�g�Q�m��
	 *		,A.TOKUTEI			--������
	 *		,A.HITOES				--�q�gES�זE
	 *		,A.KUMIKAE			--��`�q�g��������
	 *		,A.CHIRYO				--��`�q���×Տ�����
	 *		,A.EKIGAKU			--�u�w����
	 *		,A.COMMENTS			--�R�����g
	 *		,A.TENPU_PATH			--�Y�t�t�@�C���i�[�p�X
	 *		,DECODE
	 *		(
	 *			NVL(A.TENPU_PATH,'null')
	 *			,'null','FALSE'	--�Y�t�t�@�C���i�[�p�X��NULL�̂Ƃ�
	 *			,'TRUE'		--�Y�t�t�@�C���i�[�p�X��NULL�ȊO�̂Ƃ�
	 *		)
	 *		TENPU_FLG				--�Y�t�t�@�C���i�[�t���O
	 *		,A.SHINSA_JOKYO			--�R����
	 *		,A.BIKO				--���l
	 *	FROM
	 *		SHINSAKEKKA 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?
	 *		AND SHINSAIN_NO=?
	 *		AND JIGYO_KUBUN=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>������ShinsaKekkaPk�̕ϐ�SystemNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������ShinsaKekkaPk�̕ϐ�ShinsainNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������ShinsaKekkaPk�̕ϐ�JigyoKubun</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(2)�擾�����R�����ʏ����̒l"�Y�t�t�@�C���i�[�p�X��"��UNC�`���ɕϊ�����B<br /><br />
	 * 
	 * 
	 * �@(3)�ϊ������t�@�C���p�X�𗘗p���ăt�@�C����ǂݍ��݁A�ԋp����B<br /><br />
	 * @param userInfo UserInfo
	 * @param pkInfo ShinsaKekkaPk
	 * @return �R�����ʏ��̓Y�t�t�@�C����FileResource
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getHyokaFileRes(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsaKekkaPk)
	 */
	public FileResource getHyokaFileRes(
		UserInfo userInfo,
		ShinsaKekkaPk pkInfo)
		throws ApplicationException
	{
		//-----�R�����ʏ��̎擾-----
		Connection   connection  = null;
		ShinsaKekkaInfo shinsaKekkaInfo = null;
		try {		
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			
			//�R������DAO�iDBLink�j
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			try{
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection, pkInfo);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}		
		
		
		//-----�p�X���̕ϊ�-----
		String filePath = shinsaKekkaInfo.getTenpuPath();
		if(filePath == null || filePath.equals("")){
			throw new FileIOException(
				"�t�@�C���p�X���s���ł��B�t�@�C���p�X'" + filePath + "'");			
		}
		//�p�X�������UNC�`���ɕϊ�����
		filePath = StringUtil.substrReplace(filePath, DRIVE_LETTER_CONVERTED_TO_UNC, HOKAN_SERVER_UNC);
		
		
		//-----�t�@�C���擾-----
		FileResource fileRes = null;
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
	 * ���E���t�@�C���̎擾.<br /><br />
	 * 
	 * 
	 * �@(1)��������������SQL���𐶐����A�o�b�N�A�b�v�p�̃f�[�^�x�[�X����Y���\���f�[�^���擾����B<br />
	 * �@�@�@(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	SELECT
	 *		A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 *		,A.UKETUKE_NO			--�\���ԍ�
	 *		,A.JIGYO_ID			--����ID
	 *		,A.NENDO				--�N�x
	 *		,A.KAISU				--��
	 *		,A.JIGYO_NAME			--���Ɩ�
	 *		,A.SHINSEISHA_ID			--�\����ID
	 *		,A.SAKUSEI_DATE			--�\�����쐬��
	 *		,A.SHONIN_DATE			--�����@�֏��F��
	 *		,A.JYURI_DATE			--�w�U�󗝓�
	 *		,A.NAME_KANJI_SEI			--�\���Ҏ����i������-���j
	 *		,A.NAME_KANJI_MEI			--�\���Ҏ����i������-���j
	 *		,A.NAME_KANA_SEI			--�\���Ҏ����i�t���K�i-���j
	 *		,A.NAME_KANA_MEI			--�\���Ҏ����i�t���K�i-���j
	 *		,A.NAME_RO_SEI			--�\���Ҏ����i���[�}��-���j
	 *		,A.NAME_RO_MEI			--�\���Ҏ����i���[�}��-���j
	 *		,A.NENREI				--�N��
	 *		,A.KENKYU_NO			--�\���Ҍ����Ҕԍ�
	 *		,A.SHOZOKU_CD			--�����@�փR�[�h
	 *		,A.SHOZOKU_NAME			--�����@�֖�
	 *		,A.SHOZOKU_NAME_RYAKU		--�����@�֖��i���́j
	 *		,A.BUKYOKU_CD			--���ǃR�[�h
	 *		,A.BUKYOKU_NAME			--���ǖ�
	 *		,A.BUKYOKU_NAME_RYAKU		--���ǖ��i���́j
	 *		,A.SHOKUSHU_CD			--�E���R�[�h
	 *		,A.SHOKUSHU_NAME_KANJI		--�E���i�a���j
	 *		,A.SHOKUSHU_NAME_RYAKU		--�E���i���́j
	 *		,A.ZIP				--�X�֔ԍ�
	 *		,A.ADDRESS			--�Z��
	 *		,A.TEL				--TEL
	 *		,A.FAX				--FAX
	 *		,A.EMAIL				--E-Mail
	 *		,A.SENMON				--���݂̐��
	 *		,A.GAKUI				--�w��
	 *		,A.BUNTAN				--�������S
	 *		,A.KADAI_NAME_KANJI		--�����ۑ薼(�a���j
	 *		,A.KADAI_NAME_EIGO			--�����ۑ薼(�p���j
	 *		,A.JIGYO_KUBUN			--���Ƌ敪
	 *		,A.SHINSA_KUBUN			--�R���敪
	 *		,A.SHINSA_KUBUN_MEISHO		--�R���敪����
	 *		,A.BUNKATSU_NO			--�����ԍ�
	 *		,A.BUNKATSU_NO_MEISHO		--�����ԍ�����
	 *		,A.KENKYU_TAISHO			--�����Ώۂ̗ތ^
	 *		,A.KEI_NAME_NO			--�n���̋敪�ԍ�
	 *		,A.KEI_NAME			--�n���̋敪
	 *		,A.KEI_NAME_RYAKU			--�n���̋敪����
	 *		,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 *		,A.BUNYA_NAME			--����
	 *		,A.BUNKA_NAME			--����
	 *		,A.SAIMOKU_NAME			--�ז�
	 *		,A.BUNKASAIMOKU_CD2		--�זڔԍ�2
	 *		,A.BUNYA_NAME2			--����2
	 *		,A.BUNKA_NAME2			--����2
	 *		,A.SAIMOKU_NAME2			--�ז�2
	 *		,A.KANTEN_NO			--���E�̊ϓ_�ԍ�
	 *		,A.KANTEN				--���E�̊ϓ_
	 *		,A.KANTEN_RYAKU			--���E�̊ϓ_����
	 *		,A.KEIHI1				--1�N�ڌ����o��
	 *		,A.BIHINHI1			--1�N�ڐݔ����i��
	 *		,A.SHOMOHINHI1			--1�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI1			--1�N�ڍ�������
	 *		,A.GAIKOKURYOHI1			--1�N�ڊO������
	 *		,A.RYOHI1				--1�N�ڗ���
	 *		,A.SHAKIN1			--1�N�ڎӋ���
	 *		,A.SONOTA1			--1�N�ڂ��̑�
	 *		,A.KEIHI2				--2�N�ڌ����o��
	 *		,A.BIHINHI2			--2�N�ڐݔ����i��
	 *		,A.SHOMOHINHI2			--2�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI2			--2�N�ڍ�������
	 *		,A.GAIKOKURYOHI2			--2�N�ڊO������
	 *		,A.RYOHI2				--2�N�ڗ���
	 *		,A.SHAKIN2			--2�N�ڎӋ���
	 *		,A.SONOTA2			--2�N�ڂ��̑�
	 *		,A.KEIHI3				--3�N�ڌ����o��
	 *		,A.BIHINHI3			--3�N�ڐݔ����i��
	 *		,A.SHOMOHINHI3			--3�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI3			--3�N�ڍ�������
	 *		,A.GAIKOKURYOHI3			--3�N�ڊO������
	 *		,A.RYOHI3				--3�N�ڗ���
	 *		,A.SHAKIN3			--3�N�ڎӋ���
	 *		,A.SONOTA3			--3�N�ڂ��̑�
	 *		,A.KEIHI4				--4�N�ڌ����o��
	 *		,A.BIHINHI4			--4�N�ڐݔ����i��
	 *		,A.SHOMOHINHI4			--4�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI4			--4�N�ڍ�������
	 *		,A.GAIKOKURYOHI4			--4�N�ڊO������
	 *		,A.RYOHI4				--4�N�ڗ���
	 *		,A.SHAKIN4			--4�N�ڎӋ���
	 *		,A.SONOTA4			--4�N�ڂ��̑�
	 *		,A.KEIHI5				--5�N�ڌ����o��
	 *		,A.BIHINHI5			--5�N�ڐݔ����i��
	 *		,A.SHOMOHINHI5			--5�N�ڏ��Օi��
	 *		,A.KOKUNAIRYOHI5			--5�N�ڍ�������
	 *		,A.GAIKOKURYOHI5			--5�N�ڊO������
	 *		,A.RYOHI5				--5�N�ڗ���
	 *		,A.SHAKIN5			--5�N�ڎӋ���
	 *		,A.SONOTA5			--5�N�ڂ��̑�
	 *		,A.KEIHI_TOTAL			--���v-�����o��
	 *		,A.BIHINHI_TOTAL			--���v-�ݔ����i��
	 *		,A.SHOMOHINHI_TOTAL		--���v-���Օi��
	 *		,A.KOKUNAIRYOHI_TOTAL		--���v-��������
	 *		,A.GAIKOKURYOHI_TOTAL		--���v-�O������
	 *		,A.RYOHI_TOTAL			--���v-����
	 *		,A.SHAKIN_TOTAL			--���v-�Ӌ���
	 *		,A.SONOTA_TOTAL			--���v-���̑�
	 *		,A.SOSHIKI_KEITAI_NO		--�����g�D�̌`�Ԕԍ�
	 *		,A.SOSHIKI_KEITAI			--�����g�D�̌`��
	 *		,A.BUNTANKIN_FLG			--���S���̗L��
	 *		,A.KOYOHI				--�����x���Ҍٗp�o��
	 *		,A.KENKYU_NINZU			--�����Ґ�
	 *		,A.TAKIKAN_NINZU			--���@�ւ̕��S�Ґ�
	 *		,A.SHINSEI_KUBUN			--�V�K�p���敪
	 *		,A.KADAI_NO_KEIZOKU		--�p�����̌����ۑ�ԍ�
	 *		,A.SHINSEI_FLG_NO			--�p�����̌����ۑ�ԍ�
	 *		,A.SHINSEI_FLG			--�\���̗L��
	 *		,A.KADAI_NO_SAISYU			--�ŏI�N�x�ۑ�ԍ�
	 *		,A.KAIJIKIBO_FLG_NO		--�J����]�̗L���ԍ�
	 *		,A.KAIJIKIBO_FLG			--�J����]�̗L��
	 *		,A.KAIGAIBUNYA_CD			--�C�O����R�[�h
	 *		,A.KAIGAIBUNYA_NAME		--�C�O���얼��
	 *		,A.KAIGAIBUNYA_NAME_RYAKU		--�C�O���엪��
	 *		,A.KANREN_SHIMEI1		--�֘A����̌�����-����1
	 *		,A.KANREN_KIKAN1		--�֘A����̌�����-�����@��1
	 *		,A.KANREN_BUKYOKU1		--�֘A����̌�����-��������1
	 *		,A.KANREN_SHOKU1		--�֘A����̌�����-�E��1
	 *		,A.KANREN_SENMON1		--�֘A����̌�����-��啪��1
	 *		,A.KANREN_TEL1		--�֘A����̌�����-�Ζ���d�b�ԍ�1
	 *		,A.KANREN_JITAKUTEL1	--�֘A����̌�����-����d�b�ԍ�1
	 *		,A.KANREN_MAIL1		--�֘A����̌�����-E-mail1
	 *		,A.KANREN_SHIMEI2		--�֘A����̌�����-����2
	 *		,A.KANREN_KIKAN2		--�֘A����̌�����-�����@��2
	 *		,A.KANREN_BUKYOKU2		--�֘A����̌�����-��������2
	 *		,A.KANREN_SHOKU2		--�֘A����̌�����-�E��2
	 *		,A.KANREN_SENMON2		--�֘A����̌�����-��啪��2
	 *		,A.KANREN_TEL2		--�֘A����̌�����-�Ζ���d�b�ԍ�2
	 *		,A.KANREN_JITAKUTEL2	--�֘A����̌�����-����d�b�ԍ�2
	 *		,A.KANREN_MAIL2		--�֘A����̌�����-E-mail2
	 *		,A.KANREN_SHIMEI3		--�֘A����̌�����-����3
	 *		,A.KANREN_KIKAN3		--�֘A����̌�����-�����@��3
	 *		,A.KANREN_BUKYOKU3		--�֘A����̌�����-��������3
	 *		,A.KANREN_SHOKU3		--�֘A����̌�����-�E��3
	 *		,A.KANREN_SENMON3		--�֘A����̌�����-��啪��3
	 *		,A.KANREN_TEL3		--�֘A����̌�����-�Ζ���d�b�ԍ�3
	 *		,A.KANREN_JITAKUTEL3	--�֘A����̌�����-����d�b�ԍ�3
	 *		,A.KANREN_MAIL3		--�֘A����̌�����-E-mail3
	 *		,A.XML_PATH			--XML�̊i�[�p�X
	 *		,A.PDF_PATH			--PDF�̊i�[�p�X
	 *		,A.JURI_KEKKA			--�󗝌���
	 *		,A.JURI_BIKO			--�󗝌��ʔ��l
	 *		,A.SUISENSHO_PATH			--���E���̊i�[�p�X
	 *		,A.KEKKA1_ABC			--�P���R������(ABC)
	 *		,A.KEKKA1_TEN			--�P���R������(�_��)
	 *		,A.KEKKA1_TEN_SORTED		--�P���R������(�_����)
	 *		,A.SHINSA1_BIKO			--�P���R�����l
	 *		,A.KEKKA2				--�Q���R������
	 *		,A.SOU_KEHI			--���o��i�w�U���́j
	 *		,A.SHONEN_KEHI			--���N�x�o��i�w�U���́j
	 *		,A.SHINSA2_BIKO			--�Ɩ��S���ҋL����
	 *		,A.JOKYO_ID			--�\����ID
	 *		,A.SAISHINSEI_FLG			--�Đ\���t���O
	 *		,A.DEL_FLG			--�폜�t���O
	 *	FROM
	 *		SHINSEIDATAKANRI 'dbLink' A
	 *	WHERE
	 *		SYSTEM_NO=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>������shinseiDataPk�̕ϐ�SystemNo</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * �@(2)�擾�����\���f�[�^���̒l"���E���̊i�[�p�X��"��UNC�`���ɕϊ�����B<br /><br />
	 * 
	 * 
	 * �@(3)�ϊ������t�@�C���p�X�𗘗p���Đ��E���t�@�C����ǂݍ��݁A�ԋp����B<br /><br />
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return FileResource
	 * @see jp.go.jsps.kaken.model.IDataHokanMaintenance#getSuisenFileRes(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
	 */
	public FileResource getSuisenFileRes(
		UserInfo userInfo,
		ShinseiDataPk shinseiDataPk)
		throws NoDataFoundException, ApplicationException
	{
		Connection connection = null;
		String     filePath   = null;
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo, HOKAN_SERVER_DB_LINK);
			
			ShinseiDataInfo dataInfo = 
								dao.selectShinseiDataInfo(connection, shinseiDataPk, true);
			
			filePath = dataInfo.getSuisenshoPath();
			if(filePath == null || filePath.length() == 0){
				throw new FileIOException(
					"�t�@�C���p�X���s���ł��BfilePath="+filePath);			
			}
			//�p�X�������UNC�`���ɕϊ�����
			filePath = StringUtil.substrReplace(filePath, DRIVE_LETTER_CONVERTED_TO_UNC, HOKAN_SERVER_UNC);
			
			//���E���t�@�C����ǂݍ���
			FileResource fileRes = FileUtil.readFile(new File(filePath));
			return fileRes;
			
		}catch(FileNotFoundException e){
			throw new FileIOException(
				"�t�@�C����������܂���ł����BfilePath="+filePath,
				e);
		}catch(IOException e){
			throw new FileIOException(
				"�t�@�C���̓��o�͒��ɃG���[���������܂����BfilePath="+filePath,
				e);
		}catch(DataAccessException e){
			throw new ApplicationException(
				"�\�����Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		}finally{
			DatabaseUtil.closeConnection(connection);
		}
		
	}
	

	//2005.09.09 iso ��Փ��̐R�����ʂ�\�������邽�ߒǉ�
	/**
	 * ���x�����̎擾.<br /><br />
	 * 
	 * ��������List������LabelValueBean�̒��̃��x��������A
	 * �w�肳�ꂽ�l�ɊY�����郉�x������Ԃ��B<br /><br />
	 * 
	 * �w�肳�ꂽ�l�ɊY�����郉�x���������݂��Ȃ��ꍇ�́A
	 * �l���̂��̂�Ԃ��B<br /><br />
	 * 
	 * @param list LabelValueBean������List
	 * @param value String
	 * @return ���x������String
	 */
	private String getLabelName(List list, String value){
		String labelName = value;  //�����l�Ƃ��Ēl���Z�b�g����
		for(int i=0; i<list.size(); i++){
			LabelValueBean bean = (LabelValueBean)list.get(i);
			if(bean.getValue().equals(value)){
				labelName = bean.getLabel();
				break;
			}
		}
		return labelName;
	}

	
	



}