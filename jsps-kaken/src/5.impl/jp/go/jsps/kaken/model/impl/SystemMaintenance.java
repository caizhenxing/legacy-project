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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.CheckListInfoDao;
import jp.go.jsps.kaken.model.dao.impl.JigyoKanriInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterBukyokuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterJigyoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKaigaibunyaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKanriInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKeizokuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKenkyushaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKeywordInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterRyouikiInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterSaimokuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterShokushuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.RuleInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinsaKekkaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinsainInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseishaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShozokuInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.CSVTokenizer;
import jp.go.jsps.kaken.util.CheckDiditUtil;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.util.HanZenConverter;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.RandomPwd;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;


/**
 * �V�X�e���Ǘ��ҋ@�\�����e�i���X�N���X.<br /><br />
 * <b>�T�v</b><br />
 * ���Ə��̊Ǘ��ƁA�e��}�X�^�̎�荞�ݏ������s���B
 * �}�X�^�̎�荞�݂ł́A�i�[�����CSV�̃o�b�N�A�b�v�ƁA�e�[�u���̃o�b�N�A�b�v��WAS�֊i�[�����B
 * �܂��A��荞�݂��s����������A�G���[�����������ۂ̃G���[���b�Z�[�W�̊Ǘ����}�X�^�Ǘ��}�X�^�ɂčs���B
 * 
 */
public class SystemMaintenance implements ISystemMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log   = LogFactory.getLog(SystemMaintenance.class);
	
	/** ���O�i�}�X�^��荞�݁j*/
	protected static Log mtLog = LogFactory.getLog("masterTorikomi");
	
	/** �}�X�^�̃o�b�N�A�b�v�i�_���v�j���擾����ۂ̃R�}���h */
	private static String EXPORT_COMMAND  = ApplicationSettings.getString(ISettingKeys.EXPORT_COMMAND);	
	
	/** csv�t�@�C���ۑ��� */
	private static String CSV_TORIKOMI_LOCATION = ApplicationSettings.getString(ISettingKeys.CSV_TORIKOMI_LOCATION);	
	
	//2005.08.30 iso �������������O�ɏo�͂���@�\��ǉ�
	/** �������ƂɃ}�X�^��荞�݌�����\�����邩��ݒ� */
	private static final int LOGCOUNT = 1000;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public SystemMaintenance() {
		super();
	}
	
	

	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------
	
	/**
	 * CSV�t�@�C�����Ǎ�.<br />
	 * <br />
	 * CSV�t�@�C����FileResource�����X�g�̌`���֕ϊ�����B<br />
	 * �J���}��؂�̂P�J�����������X�g�Ɋi�[����B
	 * �����̃��X�g��S���R�[�h���A�ԋp�p���X�g�Ɋi�[���ԋp����B 
	 * @param fileRes		CSV�t�@�C����FileResource�C���X�^���X
	 * @return	CSV�t�@�C���̏����i�[����List
	 * @throws IOException	�t�@�C���Ǎ���O
	 */
	private List csvFile2List(FileResource fileRes)
		throws IOException
	{
		BufferedReader bir = new BufferedReader(
								new InputStreamReader(
									new ByteArrayInputStream(fileRes.getBinary())));
		//�S���R�[�h�̃��X�g
		List dt = new ArrayList();
		
		//�t�@�C������{�����̎擾
		String tmpData = null;
		while((tmpData = bir.readLine()) != null){
			dt.add(this.divideString(tmpData));	//1�s���ǉ����Ă���
		}		
		return dt;
		
	}
	
	
	/**
	 * CSV�f�[�^��List�ɕϊ�.<br />
	 * <br />
	 * CSV�f�[�^�iString�j���J���}�ŕ������AArrayList�Ɋi�[��ɕԋp����B
	 */
	private ArrayList divideString(String strData){
		ArrayList list = new ArrayList();
		CSVTokenizer ct = new CSVTokenizer(strData);
		while(ct.hasMoreTokens()){
			list.add(ct.nextToken());
		}
		return list;
	}


	/**
	 * ���p�����`�F�b�N.<br />
	 * <br />
	 * �����̕�����ɔ��p�����ȊO�̕��������݂��邩�`�F�b�N����B<br />
	 * ������null�̏ꍇ��false��ԋp�B
	 * @param strData �`�F�b�N���镶����
	 * @return true:���p�����Afalse:���p�����ȊO�̕�������܂�ł���
	 */
	private boolean checkNum(String strData){
	   //���p�����`�F�b�N
	   if (!checkHan (strData)) {
		   return false;
	   }
	   char chrField;
	   for (int i=0;i<strData.length();i++) {
		   chrField = strData.charAt (i);
		   if(!Character.isDigit(chrField)){
			   return false;			// 1�����ł����l�ȊO�Ȃ�false;
		   }
	   }
	   return true;					// ���ׂĐ��l�Ȃ�true
	}
	
	
	/**
	 * ���p�`�F�b�N.<br />
	 * <br />
	 * �����̕�����ɔ��p�ȊO�̕��������݂��邩�`�F�b�N����B<br />
	 * ������null�̏ꍇ��false��ԋp�B
	 * @param strData �`�F�b�N���镶����
	 * @return true:���p�Afalse:�S�p�̕�������܂�ł���
	 */
	private boolean checkHan (String strField) {
		//������null�̏ꍇ��false��Ԃ��B
		if(strField == null){
			return false;
		}
		char chrField;
		for (int i=0;i<strField.length();i++) {
			chrField = strField.charAt (i);
			if(!StringUtil.isAscii(chrField)){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * ������̒����`�F�b�N�i�o�C�g�j.<br />
	 * <br />
	 * �ő啶�����ȉ����ǂ����`�F�b�N����B<br />
	 * �����񒷂�S�p2�o�C�g�A���p1�o�C�g�̃o�C�g���Z�ŃJ�E���g����B
	 * ���p�J�i�ɑΉ��iShiftJIS��p�j
	 * @param strField �����Ώە�����
	 * @param intMaxLenB �ő�o�C�g��
	 * @return true:�ő啶�����ȓ��Afalse:�ő啶�������傫�� 
	 */
	private boolean checkLenB (String strField,int intMaxLenB) {
		char chrField;
		int lenB = 0;
		for (int i=0;i<strField.length();i++) {
			chrField = strField.charAt (i);
			boolean blIsHan = false;
			for (char c=' ';c<='~';c++) {
				if (chrField == c) {
					blIsHan = true;
					break;
				}
			}
			for (char ch='�';ch<='�';ch++) {
				if (chrField == ch) {
					blIsHan = true;
					break;
				}
			}
			if (blIsHan) {
				lenB++;
			}
			else {
				lenB+=2;
			}
		}

		if (lenB <= intMaxLenB) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * csv�t�@�C���̕ۑ�.<br />
	 * <br />
	 * csv�t�@�C���i��1�����œn�����FileResource�j���T�[�o�ɕۑ�����B
	 * �ۑ���t�H���_�A�t�@�C�����͈ȉ��̒ʂ�
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr>
	 *			<td style="color:white;font-weight: bold">�t�H���_</td>
	 *			<td bgcolor="#FFFFFF">
	 *				ApplicationSettings.properties��CSV_TORIKOMI_LOCATION�Ŏw�肳���ꏊ<br />
	 *				��)${shinsei_path}/mastercsv/������
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style="color:white;font-weight: bold">�t�@�C����</td>
	 *			<td bgcolor="#FFFFFF">yyyyMMddHHmmssSSS.csv</td>
	 *		</tr>
	 *	</table>
	 * 
	 * @param fileRes				 �A�b�v���[�h�t�@�C�����\�[�X�B
	 * @param tableName             �ۑ���̃t�H���_��
	 * @return                      �i�[����CSV�t�@�C���̐�΃p�X��
	 * @throws ApplicationException CSFV�t�@�C���i�[�Ɏ��s�����ꍇ
	 */
	private String kakuno(FileResource fileRes, String tableName)
		throws ApplicationException
	{
		//�o�͐�t�H���_
		String path = MessageFormat.format(CSV_TORIKOMI_LOCATION, new String[]{tableName});
		File parent = new File(path);
		
		//csv�t�@�C���̖����K�����K��
		String name = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".csv";
		fileRes.setPath(name);
		
		try {
			//csv�t�@�C�����T�[�o�Ɋi�[���郁�\�b�h���Ăяo��
			FileUtil.writeFile(parent, fileRes);
		} catch (IOException e) {
			throw new ApplicationException("CSV�t�@�C���̊i�[���Ɏ��s���܂����B",
											 new ErrorInfo("errors.7001"), 
											 e);
		}
		return new File(parent, name).getAbsolutePath();
		
	}	
	
	
	/**
	 * �@�֏��擾.<br />
	 * <br />
	 * �@�֏����擾����B
	 * ��������kikanMap�ɁA�@�փR�[�h���L�[�A�@�֖��i�a���j��l�Ƃ��ăZ�b�g����B
	 * ��O�����������ꍇ��false��Ԃ��B 
	 * �Ȃ��A�@�֏���MASTER_KIKAN�e�[�u�����擾����B
	 * @param connection	Connection
	 * @param kikanMap		�@�֏����i�[����Map
	 * @return	true:�i�[�����Afalse:�i�[���s
	 */
	private boolean setKikanNameList(Connection connection, Map kikanMap){
		try{
			List list = MasterKikanInfoDao.selectKikanList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				kikanMap.put(recordMap.get("SHOZOKU_CD"),
							 recordMap.get("SHOZOKU_NAME_KANJI"));
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}
	
	
	/**
	 * �@�֏��擾.<br />
	 * <br />
	 * �@�֏����擾����B<br />
	 * �@�փR�[�h���L�[�A�@�֏��iMap�j��l�Ƃ��āA��������kikanMap�Z�b�g����B
	 * �擾����@�֏��͈ȉ��̒ʂ�
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHUBETU_CD</td><td>�@�֎�ʃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KIKAN_KUBUN</td><td>�@�֋敪</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>�@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_KANJI</td><td>�@�֖��́i���{��j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_RYAKUSHO</td><td>�@�֗���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME_EIGO</td><td>�@�֖��́i�p��j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_ZIP</td><td>�X�֔ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_ADDRESS1</td><td>�Z���P</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_ADDRESS2</td><td>�Z���Q</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_TEL</td><td>�d�b�ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_FAX</td><td>FAX�ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_DAIHYO_NAME</td><td>��\��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BIKO</td><td>���l</td></tr>
	 *	</table><br/>
	 * �Ȃ��A�@�֏���MASTER_KIKAN�e�[�u�����擾����B
	 * �擾����ۂɁA��O�����������ꍇ��false��Ԃ��B 
	 * @param connection	Connection
	 * @param kikanMap		�@�֏����i�[����Map
	 * @return	true:�擾�����Afalse:�擾���s
	 */
	private boolean setKikanInfoList(Connection connection, Map kikanMap){
		try{
			List list = MasterKikanInfoDao.selectKikanList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				kikanMap.put(recordMap.get("SHOZOKU_CD"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}
	
	
	/**
	 * ���Ǐ��擾.<br />
	 * <br />
	 * ���Ǐ����擾����B<br />
	 * ���ǃR�[�h���L�[�A���Ǐ��iMap�j��l�Ƃ��āA��������bukyokuMap�Z�b�g����B
	 * �擾����@�֏��͈ȉ��̒ʂ�
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKYOKU_CD</td><td>���ǃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKA_NAME</td><td>���Ȗ���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKA_RYAKUSHO</td><td>���ȗ���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKA_KATEGORI</td><td>�J�e�S��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SORT_NO</td><td>�\�[�g�ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BIKO</td><td>���l</td></tr>
	 *	</table><br/>
	 * �Ȃ��A���Ǐ���MASTER_BUKYOKU�e�[�u�����擾����B
	 * �擾����ۂɁA��O�����������ꍇ��false��Ԃ��B 
	 * @param connection	Connection
	 * @param bukyokuMap	���Ǐ����i�[����Map
	 * @return	true:�擾�����Afalse:�擾���s
	 */
	private boolean setBukyokuInfoList(Connection connection, Map bukyokuMap){
		try{
			List list = MasterBukyokuInfoDao.selectBukyokuInfoList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				bukyokuMap.put(recordMap.get("BUKYOKU_CD"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}	
	
	
	/**
	 * �E���擾.<br />
	 * <br />
	 * �E�����擾����B<br />
	 * �E��R�[�h���L�[�A�E���iMap�j��l�Ƃ��āA��������shokuMap�Z�b�g����B
	 * �擾����E���͈ȉ��̒ʂ�
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOKUSHU_CD</td><td>�E��R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOKUSHU_NAME</td><td>�E����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOKUSHU_NAME_RYAKU</td><td>�E��(����)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BIKO</td><td>���l</td></tr>
	 *	</table><br/>
	 * �Ȃ��A�E����MASTER_SHOKUSHU�e�[�u�����擾����B
	 * �擾����ۂɁA��O�����������ꍇ��false��Ԃ��B 
	 * @param connection	Connection
	 * @param shokuMap		�E�����i�[����Map
	 * @return	true:�擾�����Afalse:�擾���s
	 */
	private boolean setShokuInfoList(Connection connection, Map shokuMap){
		try{
			List list = MasterShokushuInfoDao.selectShokushuList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				shokuMap.put(recordMap.get("SHOKUSHU_CD"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}		
	
	
	/**
	 * �זڕ�擾.<br />
	 * <br />
	 * �זڏ����擾����B<br />
	 * ���ȍזڃR�[�h���L�[�A�זڏ��iMap�j��l�Ƃ��āA��������saimokuMap�Z�b�g����B
	 * �擾����זڏ��͈ȉ��̒ʂ�
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUNKASAIMOKU_CD</td><td>���ȍזڃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SAIMOKU_NAME</td><td>�זږ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUNKA_CD</td><td>���ȃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUNKA_NAME</td><td>���Ȗ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUNYA_CD</td><td>����R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUNYA_NAME</td><td>���얼</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KEI</td><td>�n</td></tr>
	 *	</table><br/>
	 * �Ȃ��A�זڏ���MASTER_SAIMOKU�e�[�u�����擾����B
	 * �擾����ۂɁA��O�����������ꍇ��false��Ԃ��B 
	 * @param connection	Connection
	 * @param saimokuMap		�זڏ����i�[����Map
	 * @return	true:�擾�����Afalse:�擾���s
	 */
	private boolean setSaimokuInfoList(Connection connection, Map saimokuMap){
		try{
			List list = MasterSaimokuInfoDao.selectSaimokuInfoList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				saimokuMap.put(recordMap.get("BUNKASAIMOKU_CD"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}		
	
	
	/**
	 * �C�O�����擾.<br />
	 * <br />
	 * �C�O��������擾����B<br />
	 * �C�O����R�[�h���L�[�A�C�O������iMap�j��l�Ƃ��āA��������kaigaiMap�Z�b�g����B
	 * �擾����C�O������͈ȉ��̒ʂ�
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KAIGAIBUNYA_CD</td><td>�C�O����CD</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KAIGAIBUNYA_NAME</td><td>�C�O���얼</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>KAIGAIBUNYA_NAME_RYAKU</td><td>�C�O���얼��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BIKO</td><td>���l</td></tr>
	 *	</table><br/>
	 * �Ȃ��A�C�O�������MASTER_KAIGAIBUNYA�e�[�u�����擾����B
	 * �擾����ۂɁA��O�����������ꍇ��false��Ԃ��B 
	 * @param connection	Connection
	 * @param kaigaiMap		�C�O��������i�[����Map
	 * @return	true:�擾�����Afalse:�擾���s
	 */
	private boolean setKaigaibunyaInfoList(Connection connection, Map kaigaiMap){
		try{
			List list = MasterKaigaibunyaInfoDao.selectKaigaibunyaList(connection);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				kaigaiMap.put(recordMap.get("KAIGAIBUNYA_CD"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}		
	
	
	/**
	 * �R������擾.<br />
	 * <br />
	 * ���Ƌ敪��4�i��Ձj�̐R���������擾����B<br />
	 * �R�����R�[�h���L�[�A�R�������iMap�j��l�Ƃ��āA��������shinsainMap�Z�b�g����B
	 * �擾����R�������͈ȉ��̒ʂ�
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHINSAIN_NO</td><td>�R�����ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>JIGYO_KUBUN</td><td>���Ƌ敪</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANJI_SEI</td><td>�����i�������|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANJI_MEI</td><td>�����i�������|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANA_SEI</td><td>�����i�t���K�i�|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANA_MEI</td><td>�����i�t���K�i�|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>�����@�֖��i�R�[�h�j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME</td><td>�����@�֖��i�a���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKYOKU_NAME</td><td>���ǖ��i�a���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOKUSHU_NAME</td><td>�E�햼��</td></tr>
	 *	</table><br/>
	 * �Ȃ��A�R��������MASTER_SHINSAIN�e�[�u�����擾����B
	 * �擾����ۂɁA��O�����������ꍇ��false��Ԃ��B 
	 * @param connection	Connection
	 * @param shinsainMap	�R���������i�[����Map
	 * @return	true:�擾�����Afalse:�擾���s
	 */
	private boolean setShinsainInfoList(Connection connection, Map shinsainMap){
		try{
			//��Ղ̂�
			List list = ShinsainInfoDao.selectShinsainInfoList(connection, IJigyoKubun.JIGYO_KUBUN_KIBAN);
			for(int i=0; i<list.size(); i++){
				Map recordMap = (Map)list.get(i);
				shinsainMap.put(recordMap.get("SHINSAIN_NO"), recordMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}		
	
	
	/**
	 * ���ƕ�擾.<br />
	 * <br />
	 * ���Ƌ敪��4�i��Ձj�̎��Ə����擾����B
	 * ���ƃR�[�h���L�[�A���Ə��iMap�j��l�Ƃ��āA��������jigyoMap�Z�b�g����B
	 * �擾���鎖�Ə��͈ȉ��̒ʂ�
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>key</td><td>value</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHINSAIN_NO</td><td>���Ɣԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>JIGYO_KUBUN</td><td>���Ƌ敪</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANJI_SEI</td><td>�����i�������|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANJI_MEI</td><td>�����i�������|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANA_SEI</td><td>�����i�t���K�i�|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>NAME_KANA_MEI</td><td>�����i�t���K�i�|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_CD</td><td>�����@�֖��i�R�[�h�j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOZOKU_NAME</td><td>�����@�֖��i�a���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>BUKYOKU_NAME</td><td>���ǖ��i�a���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>SHOKUSHU_NAME</td><td>�E�햼��</td></tr>
	 *	</table><br/>
	 * �Ȃ��A���Ə���JIGYOKANRI�e�[�u�����擾����B
	 * �擾����ۂɁA��O�����������ꍇ��false��Ԃ��B 
	 * @param connection	Connection
	 * @param jigyoMap		���Ə����i�[����Map
	 * @return	true:�擾�����Afalse:�擾���s
	 */
	private boolean setJigyoInfoList(Connection connection, Map jigyoMap){
		try{
//			//��Ղ̂�
//			List list = JigyoKanriInfoDao.selectJigyoKanriList(connection, IJigyoKubun.JIGYO_KUBUN_KIBAN);
//			for(int i=0; i<list.size(); i++){
//				Map recordMap = (Map)list.get(i);
//				jigyoMap.put(recordMap.get("JIGYO_ID"), recordMap);
//			}
//            //���X�^�[�g�A�b�v
//			list = JigyoKanriInfoDao.selectJigyoKanriList(connection, IJigyoKubun.JIGYO_KUBUN_WAKATESTART);
//			for(int i=0; i<list.size(); i++){
//				Map recordMap = (Map)list.get(i);
//				jigyoMap.put(recordMap.get("JIGYO_ID"), recordMap);
//			}
//2006/04/26 �ǉ���������
            
            List list = JigyoKanriInfoDao.selectKibanJigyoKubun(connection);
            for (int i = 0; i < list.size(); i++)
            {
                Map recordMap = (Map)list.get(i);
                jigyoMap.put(recordMap.get("JIGYO_ID"), recordMap);
            }
//�c�@�ǉ������܂�            
		}catch(Exception e){
			e.printStackTrace();
			return false;	
		}	
		return true;	
	}			
	
	
	
	//---------------------------------------------------------------------
	// implement ISystemMaintenance
	//---------------------------------------------------------------------

	/**
	 * �}�X�^�Ǘ����擾.<br />
	 * <br />
	 * �}�X�^�Ǘ��}�X�^����}�X�^�Ǘ������擾���AList�Ɋi�[���ĕԋp����B<br />
	 * �ȉ���SQL�𔭍s���A�}�X�^�Ǘ������擾����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		A.MASTER_SHUBETU,	-- �}�X�^���
	 *		A.MASTER_NAME,	-- �}�X�^����
	 *		TO_CHAR(A.IMPORT_DATE, 'YYYY/MM/DD HH24:MI:SS')
	 * 			IMPORT_DATE,	-- ��荞�ݓ���
	 *		A.KENSU,	-- ����
	 *		A.IMPORT_TABLE,	-- ��荞�݃e�[�u����
	 *		A.IMPORT_FLG,	-- �V�K�E�X�V�t���O
	 *		A.IMPORT_MSG,	-- ������
	 *		A.CSV_PATH	-- CSV�t�@�C���p�X
	 *	FROM
	 *		MASTER_INFO A
	 *	ORDER BY
	 *		TO_NUMBER(MASTER_SHUBETU)
	 *	</pre>
	 *	</td></tr>
	 *	</table><br />
	 * 
	 *  �e���R�[�h�̏��̓L�[��񖼁A�Ƃ���Map�Ɋi�[���A�ԋp�p��List�փZ�b�g���ČĂяo�����֕ԋp�����B
	 * 
	 * @param userInfo	UserInfo
	 * @return	�}�X�^�Ǘ����
	 * @throws	ApplicationException	���炩�̗�O
	 * @see jp.go.jsps.kaken.model.IShozokuMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShozokuInfo)
	 */
	public List selectList(UserInfo userInfo)
		throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return MasterKanriInfoDao.selectList(connection);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	
	
	/**
	 * �}�X�^CSV�t�@�C����FileResource�擾.<br />
	 * <br />
	 * �������œn���ꂽ�}�X�^��ʂɑΉ��������CSV�t�@�C���p�X���A�}�X�^�Ǘ��}�X�^����擾����B<br />
	 * �擾����CSV�t�@�C���p�X�ɑΉ�����t�@�C����ǂݍ��݁A
	 * �t�@�C������FileResource�N���X�ɃZ�b�g���A�Ăяo�����Ƃ֕ԋp����B<br />
	 * �Ȃ��A�}�X�^�Ǘ��}�X�^�����擾����ۂ�SQL�͈ȉ��̒ʂ�B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		A.MASTER_SHUBETU,	-- �}�X�^���
	 *		A.MASTER_NAME,	-- �}�X�^����
	 *		A.IMPORT_DATE,	-- ��荞�ݓ���
	 *		A.KENSU,	-- ����
	 *		A.IMPORT_TABLE,	-- ��荞�݃e�[�u����
	 *		A.IMPORT_FLG,	-- �V�K�E�X�V�t���O
	 *		A.IMPORT_MSG,	-- ������
	 *		A.CSV_PATH	-- CSV�t�@�C���p�X
	 *	FROM
	 *		MASTER_INFO A
	 *	WHERE
	 *		MASTER_SHUBETU = ?
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">���o�C���h�ϐ��͑�������masterShubetu���Z�b�g����B</div><br />
	 * 
	 * @param userInfo UserInfo
	 * @param masterShubetu		�}�X�^���
	 * @return �}�X�^CSV�t�@�C�����iFileResource�j
	 * @throws ApplicationException ���炩�̗�O
	 * @see jp.go.jsps.kaken.model.ISystemMaintenance#getCsvFileResource(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
	 */
	public FileResource getCsvFileResource(UserInfo userInfo, String masterShubetu) 
		throws ApplicationException {
		
		//MasterKanriInfoDao��new����
		MasterKanriInfoDao masterKanriInfoDao = new MasterKanriInfoDao(userInfo);
		
		//���[�U���I������MasterKanriInfo�����
		MasterKanriInfo masterKanriInfo = null;
		
		Connection connection = null;
		try {
			//�R�l�N�V�����l��
			connection = DatabaseUtil.getConnection();
			masterKanriInfo = masterKanriInfoDao.selectMasterKanriInfo(connection, masterShubetu);
		} catch (NoDataFoundException e) {
			e.printStackTrace();
			throw new ApplicationException ("�}�X�^�Ǘ��t�@�C����������DB�G���[���������܂����B", new ErrorInfo("error.4004"),e);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ApplicationException ("�}�X�^�Ǘ��t�@�C����������DB�G���[���������܂����B", new ErrorInfo("error.4004"),e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		//MasterKanriInfo����CSV_PATH���擾����
		String path = masterKanriInfo.getCsvPath();
		
		//Get����CSV_PATH�ɂ���CSV�t�@�C�����擾����
		File file = new File(path);
		FileResource fileResource = null;
		try {
			fileResource = FileUtil.readFile(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw new ApplicationException("�w�肵���t�@�C����������܂���Bpath="+path, new ErrorInfo("errors.7004"),e1);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new ApplicationException("�o���̓G���[�B", new ErrorInfo("errors.appliaction"), e1);
		}
		
		//�擾����CSV�t�@�C����CsvOutAction�N���X�ɕԂ�
		return fileResource;

	}
	
	
	
	/**
	 * �}�X�^���捞.<br />
	 * �}�X�^���̎�荞�݂��s���B<br />
	 * ��荞�݂��s���}�X�^�́A��O�����̃}�X�^��ʂƁA��l�����̐V�K�X�V�t���O���猈�肷��B
	 * �}�X�^��ʁE�V�K�X�V�t���O�Ǝ�荞�݂��s���}�X�^�̑Ή��͈ȉ��̒ʂ�B<br/>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 *		<tr style="color:white;font-weight: bold"><td>�}�X�^���</td><td>�V�K�X�V�t���O</td><td>�Ώۃ}�X�^��</td><td>�Ăяo�����\�b�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>-</td><td>���ȍזڃ}�X�^</td><td>torikomiMasterSaimoku</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>-</td><td>�����@�փ}�X�^</td><td>torikomiMasterKikan</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>-</td><td>���ǃ}�X�^</td><td>torikomiMasterBukyoku</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>0</td><td>�w�n�R�����}�X�^�i�V�K�j</td><td>torikomiMasterShinsainGakusou(,,false)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>0�ȊO</td><td>�w�n�R�����}�X�^�i�X�V�j</td><td>torikomiMasterShinsainGakusou(,,true)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>0</td><td>��ՐR�����}�X�^�i�V�K�j</td><td>torikomiMasterShinsainKiban(,,false)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>0�ȊO</td><td>�w�n�R�����}�X�^�i�X�V�j</td><td>torikomiMasterShinsainKiban(,,true)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>9</td><td>-</td><td>��՗p����U��f�[�^</td><td>torikomiMasterWarifuriKekka</td></tr>
	 *	</table>
	 * ��L�e�[�u���Œ�`�����}�X�^��ʈȊO�������n���ꂽ�ꍇ�́A��O��throw����B
	 * @param userInfo			UserInfo
	 * @param fileRes			FileResource
	 * @param masterShubetu		�}�X�^���
	 * @param shinkiKoshinFlg	�V�K�X�V�t���O
	 * @exception	ApplicationException �Ȃ�炩�̗�O
	 * @see jp.go.jsps.kaken.model.ISystemMaintenance#torikomimaster(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.util.FileResource, java.lang.String, java.lang.String)
	 */
	public MasterKanriInfo torikomimaster(
		UserInfo userInfo,
		FileResource fileRes,
		String masterShubetu,
		String shinkiKoshinFlg)
		throws ApplicationException
	{
		if(MASTER_SAIMOKU.equals(masterShubetu)){
			return torikomiMasterSaimoku(userInfo, fileRes);						//���ȍזڃ}�X�^
			
		}else if(MASTER_KIKAN.equals(masterShubetu)){
			return torikomiMasterKikan(userInfo, fileRes);							//�����@�փ}�X�^
				
		}else if(MASTER_BUKYOKU.equals(masterShubetu)){
			return torikomiMasterBukyoku(userInfo, fileRes);						//���ǃ}�X�^
				
		}else if(MASTER_SHINSAIN_GAKUJUTU.equals(masterShubetu)){
			if(MASTER_TORIKOMI_SHINKI.equals(shinkiKoshinFlg)){
				return torikomiMasterShinsainGakusou(userInfo, fileRes ,false);	//�w�n�R�����}�X�^�i�V�K�j
			}else{
				return torikomiMasterShinsainGakusou(userInfo, fileRes, true);		//�w�n�R�����}�X�^�i�X�V�j
			}
		}else if(MASTER_SHINSAIN_KIBAN.equals(masterShubetu)){
			if(MASTER_TORIKOMI_SHINKI.equals(shinkiKoshinFlg)){
				return torikomiMasterShinsainKiban(userInfo, fileRes, false);		//��ՐR�����}�X�^�i�V�K�j
			}else{
				return torikomiMasterShinsainKiban(userInfo, fileRes, true);		//��ՐR�����}�X�^�i�X�V�j
			}
		}else if(MASTER_WARIFURIKEKKA.equals(masterShubetu)){
			return torikomiMasterWarifuriKekka(userInfo, fileRes);					//��՗p����U��f�[�^
		
		//2005/04/22 �ǉ� ��������-----------------------------------------------
		//�����҃}�X�^�̒ǉ��̂���
		}else if(MASTER_KENKYUSHA.equals(masterShubetu)){
			return torikomiMasterKenkyusha(userInfo, fileRes);						//�����҃}�X�^
		
		//�p���ۑ�}�X�^�̒ǉ��̂���
		}else if(MASTER_KEIZOKUKADAI.equals(masterShubetu)){
			return torikomiMasterKeizoku(userInfo, fileRes);						//�p���ۑ�}�X�^
		//�ǉ� �����܂�----------------------------------------------------------		
		
		//�̈�}�X�^��荞�� 2005/08/11�ǉ�
		}else if(MASTER_RYOIKI.equals(masterShubetu)){
			return torikomiMasterRyoiki(userInfo, fileRes);							//�̈�}�X�^
			
		//�L�[���[�h�}�X�^��荞�� 2005/07/21�ǉ�
		}else if(MASTER_KEYWORD.equals(masterShubetu)){
		//}else if(masterShubetu.equals("10")){
			return torikomiMasterKeyword(userInfo, fileRes);						//�L�[���[�h�}�X�^

		}else{
			throw new ApplicationException("*****�}�X�^��ʂ��s���ł��BmasterShubetu1="+masterShubetu);
		}
	}	
	
	
	
	/**
	 * ���ƊǗ���񌟍�.<br />
	 * ���ƊǗ�������������B���������́AJigyoKanriMaintenance�ɈϏ�����B
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	���������iSearchInfo�j
	 * @see jp.go.jsps.kaken.model.ISystemMaintenance#selectJigyoList(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.SearchInfo)
	 */
	public Page selectJigyoList(UserInfo userInfo, SearchInfo searchInfo)
		throws ApplicationException
	{
		//���Ə��Ǘ��r�W�l�X���W�b�N�ɏ������Ϗ�����
		IJigyoKanriMaintenance jigyoMainte = new JigyoKanriMaintenance();
		return jigyoMainte.search(userInfo, searchInfo);
	}
	
	
	
	/**
	 * ���Ə��폜.<br />
	 * ���Ə��̍폜���s���B<br />
	 * �폜�����͈ȉ��̒ʂ�B<br />
	 * <b>1.���Ə��擾</b><br />
	 * �ȉ���SQL�����s���Ď��Ə����擾���AJigyoKanriInfo�ɃZ�b�g����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	SELECT
	 *		 A.JIGYO_ID	-- ����ID
	 *		,A.NENDO	-- �N�x
	 *		,A.KAISU	-- ��
	 *		,A.JIGYO_NAME	-- ���Ɩ�
	 *		,A.JIGYO_KUBUN	-- ���Ƌ敪
	 *		,A.SHINSA_KUBUN	-- �R���敪
	 *		,A.TANTOKA_NAME	-- �Ɩ��S����
	 *		,A.TANTOKAKARI	-- �Ɩ��S���W��
	 *		,A.TOIAWASE_NAME	-- �₢���킹��S���Җ�
	 *		,A.TOIAWASE_TEL	-- �₢���킹��d�b�ԍ�
	 *		,A.TOIAWASE_EMAIL	-- �₢���킹��E-mail
	 *		,A.UKETUKEKIKAN_START	-- �w�U��t���ԁi�J�n�j
	 *		,A.UKETUKEKIKAN_END	-- �w�U��t���ԁi�I���j
	 *		,A.SHINSAKIGEN	-- �R������
	 *		,A.TENPU_NAME	-- �Y�t������
	 *		,A.TENPU_WIN	-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *		,A.TENPU_MAC	-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *		,A.HYOKA_FILE_FLG	-- �]���p�t�@�C���L��
	 *		,A.HYOKA_FILE	-- �]���p�t�@�C���i�[�t�H���_
	 *		,A.KOKAI_FLG	-- ���J�t���O
	 *		,A.KESSAI_NO	-- ���J���ٔԍ�
	 *		,A.KOKAI_ID	-- ���J�m���ID
	 *		,A.HOKAN_DATE	-- �f�[�^�ۊǓ�
	 *		,A.YUKO_DATE	-- �ۊǗL������
	 *		,A.BIKO	-- ���l
	 *		,A.DEL_FLG	-- �폜�t���O
	 *	FROM
	 *		JIGYOKANRI A
	 *	WHERE
	 *		JIGYO_ID = ?
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">���o�C���h�ϐ��͑�����jigyoPk��jigyoId���Z�b�g����B</div>
	 *	<br />
	 *	<br />
	 * <b>2.�ۊǍς݃`�F�b�N</b><br />
	 * 1.�Ŏ擾�������́A�ۊǓ��iHokanDate�j���m�F����Bnull�̏ꍇ�i���ۊǁj�͗�O��throw����B<br />
	 *	<br />
	 * <b>3.���Ə��f�[�^�폜</b><br />
	 * �ȉ���SQL�����s���āA���Ə���_���폜����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	UPDATE
	 *		JIGYOKANRI
	 *	SET
	 *		DEL_FLG = 1	-- �폜�t���O
	 *	WHERE
	 *		JIGYO_ID = ?
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">���o�C���h�ϐ��͑�����jigyoPk��jigyoId���Z�b�g����B</div>
	 *	<br />
	 *	<br />
	 * <b>4.�\���f�[�^�폜</b><br />
	 * �폜�������ƂɊւ���\���������LSQL�Ř_���폜����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	UPDATE
	 *		SHINSEIDATAKANRI
	 *	SET
	 *		DEL_FLG = 1	-- �폜�t���O
	 *	WHERE
	 *		JIGYO_ID = ?
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">���o�C���h�ϐ��͑�����jigyoPk��jigyoId���Z�b�g����B</div>
	 *	<br />
	 * 	1.�`4.�̏������s���ɗ�O�����������ꍇ�́Arollback���s���B
	 * @param userInfo	UserInfo
	 * @param jigyoPk	�폜���s�����Ə���PK���iJigyoKanriPk�j
	 * @see jp.go.jsps.kaken.model.ISystemMaintenance#deleteJigyo(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.JigyoKanriPk)
	 */
	public JigyoKanriInfo deleteJigyo(UserInfo userInfo, JigyoKanriPk jigyoPk)
		throws ApplicationException
	{
		//DB�R�l�N�V�����̎擾
		Connection connection = null;
		boolean success = false;	
		try{
			connection = DatabaseUtil.getConnection();
			
			//---���Ə��擾
			JigyoKanriInfo info = null;
			JigyoKanriInfoDao jigyoKanriDao = new JigyoKanriInfoDao(userInfo);
			try{
				info = jigyoKanriDao.selectJigyoKanriInfo(connection, jigyoPk);
			} catch (DataAccessException e) {
				success = false;
				throw new ApplicationException(
					"���Ə��Ǘ��f�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//�ۊǍς݃`�F�b�N
			if(info.getHokanDate() == null){
				throw new ApplicationException(
					"���Y���Ə��͕ۊǂ���Ă��܂���BjigyoId=" + jigyoPk.getJigyoId(),
					new ErrorInfo("errors.5012"));
			}
			
			//---���Ə��f�[�^�폜�i�폜�t���O�j
			try{
				jigyoKanriDao.deleteFlgJigyoKanriInfo(connection, jigyoPk);
			} catch (DataAccessException e) {
				success = false;
				throw new ApplicationException(
					"���Ə��Ǘ��f�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			}
			jigyoKanriDao = null;

			//---�\���f�[�^�폜�i�폜�t���O�j
			try {
				ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo);
				dao.deleteFlagShinseiDataInfo(connection, jigyoPk);
			} catch (DataAccessException e) {
				success = false;
				throw new ApplicationException(
					"�\�����Ǘ��f�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			}

			success = true;	//�����܂ŏ������i�߂ΐ���Ɣ��f�ł���
			return info;
			
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"���ƃf�[�^�폜���ɃG���[���������܂����B",
					new ErrorInfo("errors.4009"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
		
	/**
	 * ���ȍזڃ}�X�^�捞.<br />
	 * ���ȍזڃ}�X�^�����擾����B<br />
	 * <b>1.CSV�f�[�^�����X�g�`���ɕϊ�</b><br />
	 * �����œn���ꂽfileRes��List�`���֕ϊ�����BList�̗v�f�Ƃ��Ă����List�������A
	 * ���̗v�f��CSV���̈ꃌ�R�[�h���̏����i�[����B
	 * <br /><br />
	 * <b>2.�J�������`�F�b�N</b><br />
	 * 1.�ō쐬����List�̈�ڂ̗v�f��List�̗v�f�����A7�ł��邩�m�F����B����ȊO�̏ꍇ�́A��O��throw����B
	 * <br /><br />
	 * <b>3.csv�t�@�C���i�[</b><br />
	 * ���N���X��kakuno()���\�b�h���g�p����CSV�t�@�C�����T�[�o�֊i�[����B
	 * <br /><br />
	 * <b>4.�}�X�^�G�N�X�|�[�g</b><br />
	 * ���ȍזڃ}�X�^���o�b�N�A�b�v�p�ɃG�N�X�|�[�g����B�G�N�X�|�[�g�́AApplicationSettings.properties�́A
	 * EXPORT_COMMAND�Œ�`����Ă���R�}���h�����s���邱�Ƃɂ���čs����B<br />
	 * �o�͂����_���v�t�@�C���̃t�@�C�����͈ȉ��̒ʂ�B<br />
	 * MASTER_SAIMOKU_yyyyMMddHHmmssSSS.DMP
	 * <br />
	 * <br />
	 * <b>5.���ȍזڏ��폜</b><br />
	 * ���ȍזڏ����A�ȉ���SQL�����s���邱�Ƃɂ���č폜����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM MASTER_SAIMOKU
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * <br />
	 * <b>6.���捞</b><br />
	 * 1.�ō쐬����CSV�t�@�C�����List�𕪉ȍזڃ}�X�^��INSERT����B
	 * List�Ɋi�[����Ă���v�fList�ɂ͈ȉ��̏�񂪊i�[����Ă���B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>�v�f�ԍ�</td><td>�e�[�u����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>�זڃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>�זږ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>���ȃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>���Ȗ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>����R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>���얼</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>�n</td></tr>
	 *	</table><br />
	 * 
	 * �e��̏��́A�����`�F�b�N�A�^�`�F�b�N����ш�ӃL�[����`�F�b�N���s���A��肪�Ȃ����INSERT�������s���B
	 * ��肪�������ꍇ�́A�G���[�����Ǘ�����List�Ɋi�[�����̂��A���̏��֏�����i�߂�B
	 * �Ȃ��A���ȍזڃR�[�h�A���ȃR�[�h�A����R�[�h�́A�e�`�F�b�N�ƂƂ��ɍ�0���ߏ������s���B<br />
	 * INSERT����ۂɎ��s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		MASTER_SAIMOKU(
	 *			 BUNKASAIMOKU_CD		-- ���ȍזڃR�[�h
	 *			,SAIMOKU_NAME		-- �זږ�
	 *			,BUNKA_CD		-- ���ȃR�[�h
	 *			,BUNKA_NAME		-- ���Ȗ�
	 *			,BUNYA_CD		-- ����R�[�h
	 *			,BUNYA_NAME		-- ���얼
	 *			,KEI		-- �n
	 *		)
	 *	VALUES 
	 *		(?,?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ȍזڃR�[�h</td><td>CSV����擾�������ȍזڃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�זږ�</td><td>CSV����擾�����זږ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ȃR�[�h</td><td>CSV����擾�������ȃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ȗ�</td><td>CSV����擾�������Ȗ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����R�[�h</td><td>CSV����擾��������R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���얼</td><td>CSV����擾�������얼</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�n</td><td>CSV����擾�����n</td></tr>
	 *	</table><br/>
	 * 
	 * <br />
	 * <b>7.�}�X�^�Ǘ��}�X�^�X�V</b><br />
	 * �}�X�^�Ǘ��}�X�^���ȉ��̏��ōX�V����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�X�V���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^���</td><td>1</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^����</td><td>"���ȍזڃ}�X�^"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�ݓ���</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����</td><td>Insert�������R�[�h��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�݃e�[�u����</td><td>"MASTER_SAIMOKU"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�K�E�X�V�t���O</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�捞�G���[���b�Z�[�W</td><td>���R�[�hINSERT�Ɏ��s�����ꍇ�A�G���[���b�Z�[�W</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSV�t�@�C���p�X</td><td>CSV�i�[��t�H���_�{CSV�t�@�C����</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>8.�}�X�^�Ǘ����ԋp</b><br />
	 * 7.�ōX�V���������Ăяo�����֕ԋp����B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param fileRes				�A�b�v���[�h�t�@�C�����\�[�X�B
	 * @return						�������ʏ��
	 * @throws NoDateFoundException	�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 * @throws ApplicationException	���炩�̗�O
	 */
	private MasterKanriInfo torikomiMasterSaimoku(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException {
		
		//2005/04/22 �C�� ��������--------------------------------------------
		//���R �J�������ύX�̂���
		//int columnSize = 7;
		int columnSize = 8;
		//�C�� �����܂�-------------------------------------------------------
		
		boolean success = false;
		Connection connection = null;

		try{
			//CSV�f�[�^�����X�g�`���ɕϊ�����
			List dt = csvFile2List(fileRes);

			//�J�������`�F�b�N(1�s�ڂ̂�)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[���o�͂��������I������B
					throw new ApplicationException(
								"CSV�t�@�C�������ȍזڃ}�X�^�̒�`�ƈقȂ�܂��B",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csv�t�@�C���i�[
			String table_name = "MASTER_SAIMOKU";
			String csvPath = kakuno(fileRes, table_name);
			
			//DB�̃G�N�X�|�[�g
			String file_name = "MASTER_SAIMOKU_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//�R�l�N�V�����擾
			connection = DatabaseUtil.getConnection();

			//�捞�iDELETE �� INSERT�����j
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);
			
			int cnt = 0;									//����捞����
			ArrayList cd_list  = new ArrayList();			//�捞�f�[�^�̃L�[�i�[�z��(��Ӑ���`�F�b�N�p)
			ArrayList err_list = new ArrayList();			//�捞�݃G���[�����i�[�z��
			int dtsize = dt.size();							//�捞�S����
			
			//DAO
			MasterSaimokuInfoDao saimokuDao = new MasterSaimokuInfoDao(userInfo);
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);
				
				if(line.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�A�G���[�Ƃ��ď������΂��B�i�s�����m�ۂ���j
					String msg = i+"�s�� ���ȍזڃ}�X�^�̃e�[�u����`�ƈ�v���܂���B";
					mtLog.warn(msg);
					//err_list.add(msg);				//�G���[���e���i�[ �� �ۗ��B�R�����g�A�E�g
					err_list.add(Integer.toString(i));	//�G���[�̍s�����m��
					
				}else{
					//���폈��
					//�G���[�t���O
					int err_flg = 0;
					
					//�}�X�^�f�[�^�̎擾
					String bunkasaimoku_cd = (String)line.get(0);		//�זڃR�[�h
					//2005/04/22 �ǉ� ��������-----------------------------------------
					String bunkatsu_no      = (String)line.get(1);		//�����ԍ�
					//�ǉ� �����܂�----------------------------------------------------
					String saimoku_name    = (String)line.get(2);		//�זږ�
					String bunka_cd        = (String)line.get(3);		//���ȃR�[�h
					String bunka_name      = (String)line.get(4);		//���Ȗ�
					String bunya_cd        = (String)line.get(5);		//����R�[�h
					String bunya_name      = (String)line.get(6);		//���얼
					String kei             = (String)line.get(7);		//�n
										
					//-----���ȍזڃR�[�h�`�F�b�N
					//���l�`�F�b�N
					//2005/04/25 �ǉ� ��������------------------------------------------
					//���R �זڃR�[�h�̕K�{�`�F�b�N�ǉ��̂���
					if(bunkasaimoku_cd == null || bunkasaimoku_cd.equals("")){
						String msg = i+"�s�� �זڃR�[�h�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
					//�ǉ� �����܂�-----------------------------------------------------						
						if(!this.checkNum(bunkasaimoku_cd)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �זڃR�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i4���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = bunkasaimoku_cd.length();
							if(cd_length == 1){
								bunkasaimoku_cd = "000" + bunkasaimoku_cd;
							}else if(cd_length == 2){
								bunkasaimoku_cd = "00" + bunkasaimoku_cd;
							}else if(cd_length == 3){
								bunkasaimoku_cd = "0" + bunkasaimoku_cd;
							}else if(cd_length == 0 || cd_length > 4){
								String msg = i+"�s�� �זڃR�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
							//KEY���ڂ̏d���`�F�b�N
							//2005/04/25�@�ǉ� ��������----------------------------------
							//���R�@Key�l�ɕ����ԍ����ǉ����ꂽ����
							if(cd_list.contains(bunkasaimoku_cd + bunkatsu_no)){
								String msg = i+"�s�� Key�l(�זڃR�[�h,�����ԍ�)�͏d�����Ă��܂��B";
							//if(cd_list.contains(bunkasaimoku_cd)){
							//	String msg = i+"�s�� �זڃR�[�h�͏d�����Ă��܂��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}else{
								cd_list.add(bunkasaimoku_cd+bunkatsu_no);
							}
						}
					}
					
					//-----�זږ��`�F�b�N
					if(saimoku_name == null || saimoku_name.equals("")){
						String msg = i+"�s�� �זږ��͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(saimoku_name, 60)){
							String msg = i+"�s�� �זږ��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----���ȃR�[�h
					//���l�`�F�b�N
					//2005/04/25 �ǉ� ��������------------------------------------------
					//���R ���ȃR�[�h�̕K�{�`�F�b�N�ǉ��̂���
					if(bunka_cd == null || bunka_cd.equals("") || bunka_cd.equals("\"")){
						//2005.07.29 iso ���ȃR�[�h�͋������
//						String msg = i+"�s�� ���ȃR�[�h�͕K�{���ڂł��B";
//						mtLog.warn(msg);
//						err_flg = 1;	
					}else{
					//�ǉ� �����܂�-----------------------------------------------------
						if(!this.checkNum(bunka_cd)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� ���ȃR�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i4���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = bunka_cd.length();
							if(cd_length == 1){
								bunka_cd = "000" + bunka_cd;
							}else if(cd_length == 2){
								bunka_cd = "00" + bunka_cd;
							}else if(cd_length == 3){
								bunka_cd = "0" + bunka_cd;
							}else if(cd_length == 0 || cd_length > 4){
								String msg = i+"�s�� ���ȃR�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}
					
					//-----���Ȗ��`�F�b�N
					if(!this.checkLenB(bunka_name, 60)){
						String msg = i+"�s�� ���Ȗ��̒������s���ł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}
					//-----����R�[�h
					//���l�`�F�b�N
					if(!this.checkNum(bunya_cd)){
						//���p�����Ŗ����ꍇ
						String msg = i+"�s�� ����R�[�h�͔��p�����ł͂���܂���B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//���p�����̏ꍇ
						//�����`�F�b�N�i4���Ŗ����ꍇ�́A�擪��"0"��������j
						int cd_length = bunya_cd.length();
						if(cd_length == 1){
							bunya_cd = "000" + bunya_cd;
						}else if(cd_length == 2){
							bunya_cd = "00" + bunya_cd;
						}else if(cd_length == 3){
							bunya_cd = "0" + bunya_cd;
						}else if(cd_length == 0 || cd_length > 4){
							String msg = i+"�s�� ����R�[�h�̌������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----���얼�`�F�b�N
					if(!this.checkLenB(bunya_name, 60)){
						String msg = i+"�s�� ���얼�̒������s���ł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}
					//-----�n�`�F�b�N
					if(!this.checkLenB(kei, 1)){
						String msg = i+"�s�� �n�̒������s���ł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}
					
					//2005/04/22 �ǉ� ��������-----------------------------------------
					//�����ԍ��̒ǉ�
					//----�����ԍ��`�F�b�N
					//2005.08.15 iso �����ԍ�����̏ꍇ���u-�v�ɂ���悤�ύX
					if(bunkatsu_no == null|| bunkatsu_no.equals("")){
//						String msg = i+"�s�� �����ԍ��͕K�{���ڂł��B";
//						mtLog.warn(msg);
//						err_flg = 1;
						bunkatsu_no = "-";
					}else{
						if(!this.checkLenB(bunkatsu_no, 1)){
							String msg = i+"�s�� �����ԍ��̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						
						//<!-- UPDATE�@START 2007/07/21 BIS ���� -->
						/*�Â��R�[�h
						//�����ԍ���1,2,A,B�ȊO�̏ꍇ��"-"�œo�^����
						if(bunkatsu_no != null && !bunkatsu_no.equals("1") && !bunkatsu_no.equals("2") 
							&& !bunkatsu_no.equals("A") && !bunkatsu_no.equals("B")){
								bunkatsu_no = "-";
						}
						*/
						//�����ԍ���1,2,3,4,5,A,B�ȊO�̏ꍇ��"-"�œo�^����
						if(bunkatsu_no != null && !bunkatsu_no.equals("1") && !bunkatsu_no.equals("2") 
								&& !bunkatsu_no.equals("3") && !bunkatsu_no.equals("4") && !bunkatsu_no.equals("5") 
								&& !bunkatsu_no.equals("A") && !bunkatsu_no.equals("B")){
									bunkatsu_no = "-";
							}
						//<!-- UPDATE�@END 2007/07/21 BIS ���� -->
					}
					//�ǉ� �����܂�----------------------------------------------------					
					
					//�G���[�����������ꍇ�̂ݓo�^����
					if(err_flg == 0){
						//DB�ɓo�^����
						SaimokuInfo info = new SaimokuInfo();
						info.setBunkaSaimokuCd(bunkasaimoku_cd);
						info.setSaimokuName(saimoku_name);
						info.setBunkaCd(bunka_cd);
						info.setBunkaName(bunka_name);
						info.setBunyaCd(bunya_cd);
						info.setBunyaName(bunya_name);
						info.setKei(kei);
						//2005/04/22 �ǉ� ��������-----------------------------------------
						//�����ԍ��̒ǉ�
						info.setBunkatsuNo(bunkatsu_no);
						//�ǉ� �����܂�----------------------------------------------------
												
						saimokuDao = new MasterSaimokuInfoDao(userInfo);
						saimokuDao.insertSaimokuInfo(connection, info);
						cnt++;	//��荞�݌������J�E���g
					}else{
						//�f�[�^�ɕs�������邽�߁A�o�^���s��Ȃ��i�s�����m�ۂ���j
						err_list.add(Integer.toString(i));
					}
				}		
			}
			
			//�}�X�^�Ǘ��e�[�u���̍X�V
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_SAIMOKU);
			mkInfo.setMasterName("���ȍזڃ}�X�^");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);			//�g�p���ĂȂ��H		
			mkInfo.setImportErrMsg(err_list);	//�G���[���i��ʂŎg�p�j
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;
			
		} catch (IOException e) {
			throw new ApplicationException(
						"CSV�t�@�C���̓Ǎ����ɃG���[���������܂����B",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"�}�X�^�捞����DB�G���[���������܂����B",
						new ErrorInfo("errors.4001"),
						e);
		} finally {
			try {
				if (success) {
					//�R�~�b�g
					DatabaseUtil.commit(connection);
				} else {
					//���[���o�b�N
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�}�X�^�捞����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	/**
	 * �����@�փ}�X�^�捞.<br />
	 * �����@�փ}�X�^�����擾����B<br />
	 * <b>1.CSV�f�[�^�����X�g�`���ɕϊ�</b><br />
	 * �����œn���ꂽfileRes��List�`���֕ϊ�����BList�̗v�f�Ƃ��Ă����List�������A
	 * ���̗v�f��CSV���̈ꃌ�R�[�h���̏����i�[����B
	 * <br /><br />
	 * <b>2.�J�������`�F�b�N</b><br />
	 * 1.�ō쐬����List�̈�ڂ̗v�f��List�̗v�f�����A9�ł��邩�m�F����B����ȊO�̏ꍇ�́A��O��throw����B
	 * <br /><br />
	 * <b>3.csv�t�@�C���i�[</b><br />
	 * ���N���X��kakuno()���\�b�h���g�p����CSV�t�@�C�����T�[�o�֊i�[����B
	 * <br /><br />
	 * <b>4.�}�X�^�G�N�X�|�[�g</b><br />
	 * �����@�փ}�X�^���o�b�N�A�b�v�p�ɃG�N�X�|�[�g����B�G�N�X�|�[�g�́AApplicationSettings.properties�́A
	 * EXPORT_COMMAND�Œ�`����Ă���R�}���h�����s���邱�Ƃɂ���čs����B<br />
	 * �o�͂����_���v�t�@�C���̃t�@�C�����͈ȉ��̒ʂ�B<br />
	 * MASTER_KIKAN_yyyyMMddHHmmssSSS.DMP
	 * <br />
	 * <br />
	 * <b>5.�����@�֏��폜</b><br />
	 * �����@�֏����A�ȉ���SQL�����s���邱�Ƃɂ���č폜����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM MASTER_KIKAN
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * <br />
	 * <b>6.���捞</b><br />
	 * 1.�ō쐬����CSV�t�@�C�����List�������@�փ}�X�^��INSERT����B
	 * List�Ɋi�[����Ă���v�fList�ɂ͈ȉ��̏�񂪊i�[����Ă���B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>�v�f�ԍ�</td><td>�e�[�u����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>�@�֎�ʃR�[�h�iCSV�ł͋@�֋敪�j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>�@�֋敪�iCSV�ł͋@�֎�ʁj</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>�@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>�@�֖���(���{��)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>�@�֗���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>�X�֔ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>�Z���P</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>�Z���Q</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>���l</td></tr>
	 *	</table><br />
	 * 
	 * �e��̏��́A�����`�F�b�N�A�^�`�F�b�N����ш�ӃL�[����`�F�b�N���s���A��肪�Ȃ����INSERT�������s���B
	 * ��肪�������ꍇ�́A�G���[�����Ǘ�����List�Ɋi�[�����̂��A���̏��֏�����i�߂�B
	 * �Ȃ��A�@�փR�[�h�A�X�֔ԍ��́A�e�`�F�b�N�ƂƂ��ɍ�0���ߏ������s���B
	 * INSERT����ۂɎ��s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		MASTER_KIKAN(
	 *			 SHUBETU_CD		-- �@�֎�ʃR�[�h
	 *			,KIKAN_KUBUN		-- �@�֋敪
	 *			,SHOZOKU_CD		-- �@�փR�[�h
	 *			,SHOZOKU_NAME_KANJI		-- �@�֖���
	 *			,SHOZOKU_RYAKUSHO		-- �@�֖�����
	 *			,SHOZOKU_NAME_EIGO		-- �@�֖��́i�p��j
	 *			,SHOZOKU_ZIP		-- �X�֔ԍ�
	 *			,SHOZOKU_ADDRESS1		-- �Z��1
	 *			,SHOZOKU_ADDRESS2		-- �Z��2
	 *			,SHOZOKU_TEL		-- �d�b�ԍ�
	 *			,SHOZOKU_FAX		-- FAX
	 *			,SHOZOKU_DAIHYO_NAME		-- ��\�Ҏ���
	 *			,BIKO		-- ���l
	 *		)
	 *	VALUES
	 *		(?,?,?,?,?,?,?,?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�@�֎�ʃR�[�h</td><td>CSV����擾�����@�֎�ʃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�@�֋敪</td><td>CSV����擾�����@�֋敪</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�@�փR�[�h</td><td>CSV����擾�����@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�@�֖���</td><td>CSV����擾�����@�֖���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�@�֖�����</td><td>CSV����擾�����@�֗���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�@�֖��́i�p��j</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�X�֔ԍ�</td><td>CSV����擾�����X�֔ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�Z��1</td><td>CSV����擾�����Z��1</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�Z��2</td><td>CSV����擾�����Z��2</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�d�b�ԍ�</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>FAX</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��\�Ҏ���</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���l</td><td>CSV����擾�������l</td></tr>
	 *	</table><br/>
	 * INSERT������������A�ȉ��̓��SQL�����s���A�����S���ҏ��Ɛ\���ҏ����X�V����B
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	UPDATE
	 *		SHOZOKUTANTOINFO
	 *	SET
	 *		 SHOZOKU_NAME_KANJI = ?		-- �@�֖��i�a���j
	 *		,SHOZOKU_RYAKUSHO = ?		-- �@�֖��i���́j
	 *	WHERE
	 *		SHOZOKU_CD = ?	-- �@�փR�[�h
	 *		AND (
	 *			SHOZOKU_NAME_KANJI <> ?	-- �@�֖��i�a���j���Ⴄ
	 *			OR SHOZOKU_RYAKUSHO <> ?	-- �@�֖��i���́j���Ⴄ
	 *		)
	 *	/
	 *	UPDATE
	 *		SHINSEISHAINFO
	 *	SET
	 *		 SHOZOKU_NAME = ?	-- �@�֖��i�a���j
	 *		,SHOZOKU_NAME_RYAKU = ?		--�@�֖��i���́j
	 *	WHERE"
	 *		SHOZOKU_CD = ?	-- �@�փR�[�h
	 *		AND (
	 *			SHOZOKU_NAME <> ?	-- �@�֖��i�a���j���Ⴄ
	 *			OR SHOZOKU_NAME_RYAKU <> ?	-- �@�֖��i���́j���Ⴄ
	 *		 )
	 *	/
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<div style="font-size:8pt">���@�փR�[�h�A�@�֖��i�a���j����ы@�֖��i���́j��CSV�̏����g�p����B</div><br />
	 *	<br />
	 * 
	 * <br />
	 * <b>7.�}�X�^�Ǘ��}�X�^�X�V</b><br />
	 * �}�X�^�Ǘ��}�X�^���ȉ��̏��ōX�V����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�X�V���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^���</td><td>2</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^����</td><td>"�����@�փ}�X�^"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�ݓ���</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����</td><td>Insert�������R�[�h��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�݃e�[�u����</td><td>"MASTER_KIKAN"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�K�E�X�V�t���O</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�捞�G���[���b�Z�[�W</td><td>���R�[�hINSERT�Ɏ��s�����ꍇ�A�G���[���b�Z�[�W</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSV�t�@�C���p�X</td><td>CSV�i�[��t�H���_�{CSV�t�@�C����</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>8.�}�X�^�Ǘ����ԋp</b><br />
	 * 7.�ōX�V���������Ăяo�����֕ԋp����B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param fileRes				�A�b�v���[�h�t�@�C�����\�[�X�B
	 * @return						�������ʏ��
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	private MasterKanriInfo torikomiMasterKikan(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException {

		int columnSize = 9;
		
		boolean success = false;
		Connection connection = null;
		
		try{
			//CSV�f�[�^�����X�g�`���ɕϊ�����
			List dt = csvFile2List(fileRes);
			
			//�J�������`�F�b�N(1�s�ڂ̂�)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�A�G���[���o�͂��������I������B
					throw new ApplicationException(
								"CSV�t�@�C���������@�փ}�X�^�̒�`�ƈقȂ�܂��B",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csv�t�@�C���i�[
			String table_name = "MASTER_KIKAN";
			String csvPath = kakuno(fileRes, table_name);
			
			//DB�̃G�N�X�|�[�g
			String file_name = "MASTER_KIKAN_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//�R�l�N�V�����擾
			connection = DatabaseUtil.getConnection();

			//�捞�iDELETE �� INSERT�����j
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);
			
			int cnt = 0;											//����捞����
			ArrayList cd_list  = new ArrayList();					//�捞�f�[�^�̃L�[�i�[�z��(��Ӑ���`�F�b�N�p)
			ArrayList err_list = new ArrayList();					//�捞�݃G���[�s�i�[�z��
			int dtsize = dt.size();									//�捞�S����
			
			//DAO
			MasterKikanInfoDao kikanDao      = new MasterKikanInfoDao(userInfo);
			ShozokuInfoDao     shozokuDao    = new ShozokuInfoDao(userInfo);
			ShinseishaInfoDao  shinseishaDao = new ShinseishaInfoDao(userInfo);
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[�Ƃ��ď������΂��B�i�s�����m�ۂ���j
					String msg = i+"�s�� �@�փ}�X�^�̃e�[�u����`�ƈ�v���܂���B";
					mtLog.warn(msg);
					//err_list.add(msg);				//�G���[���e���i�[ �� �ۗ��B�R�����g�A�E�g
					err_list.add(Integer.toString(i));	//�G���[�̍s�����m��
					
				}else{
					//���폈��
					//�G���[�t���O
					int err_flg = 0;
					
					//�}�X�^�f�[�^�̎擾
					//���@�֖���(�p��)�A�d�b�ԍ��AFAX�ԍ��A��\�Җ���CSV�ɑ��݂��Ȃ��B
					String shubetu_cd         = (String)line.get(0);	//�@�֎�ʃR�[�h�iCSV�ł͋@�֋敪�j
					String kikan_kubun        = (String)line.get(1);	//�@�֋敪�iCSV�ł͋@�֎�ʁj
					String shozoku_cd         = (String)line.get(2);	//�@�փR�[�h
					String shozoku_name_kanji = (String)line.get(3);	//�@�֖���(���{��)
					String shozoku_ryakusho   = (String)line.get(4);	//�@�֗���
					String shozoku_zip        = (String)line.get(5);	//�X�֔ԍ�
					String shozoku_address1   = (String)line.get(6);	//�Z���P
					String shozoku_address2   = (String)line.get(7);	//�Z���Q
					String shozoku_biko       = (String)line.get(8);	//���l
					
					//-----�@�֎�ʃR�[�h�iCSV�ł͋@�֋敪�j�R�[�h�`�F�b�N
					if(shubetu_cd == null || shubetu_cd.equals("")){
						String msg = i+"�s�� �@�֋敪�iDB�ł͋@�֎�ʁj�͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//���l�`�F�b�N
						if(!this.checkNum(shubetu_cd)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �@�֋敪�iDB�ł͋@�֎�ʁj�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(shubetu_cd, 1)){
							String msg = i+"�s�� �@�֋敪�iDB�ł͋@�֎�ʁj�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----�@�֋敪�iCSV�ł͋@�֎�ʁj�`�F�b�N
					//���l�`�F�b�N
					if(!this.checkNum(kikan_kubun)){
						//���p�����Ŗ����ꍇ
						String msg = i+"�s�� �@�֎�ʁiDB�ł͋@�֋敪�j�͔��p�����ł͂���܂���B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(kikan_kubun, 1)){
							String msg = i+"�s�� �@�֎�ʁiDB�ł͋@�֋敪�j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----�@�փR�[�h�`�F�b�N
					//���l�`�F�b�N
					if(!this.checkNum(shozoku_cd)){
						//���p�����Ŗ����ꍇ
						String msg = i+"�s�� �@�փR�[�h�͔��p�����ł͂���܂���B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//���p�����̏ꍇ
						//�����`�F�b�N�i5���Ŗ����ꍇ�́A�擪��"0"��������j
						int cd_length = shozoku_cd.length();
						if(cd_length == 1){
							shozoku_cd = "0000" + shozoku_cd;
						}else if(cd_length == 2){
							shozoku_cd = "000" + shozoku_cd;
						}else if(cd_length == 3){
							shozoku_cd = "00" + shozoku_cd;
						}else if(cd_length == 4){
							shozoku_cd = "0" + shozoku_cd;
						}else if(cd_length == 0 || cd_length > 5){
							String msg = i+"�s�� �@�փR�[�h�̌������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						//KEY���ڂ̏d���`�F�b�N
						if(cd_list.contains(shozoku_cd)){
							String msg = i+"�s�� �@�փR�[�h�͏d�����Ă��܂��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							cd_list.add(shozoku_cd);
						}
					}
					//-----�@�֖���(���{��)�`�F�b�N
					if(shozoku_name_kanji == null || shozoku_name_kanji.equals("")){
						String msg = i+"�s�� �@�֖���(���{��)�͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//1005.07.29 iso ������ύX
//						if(!this.checkLenB(shozoku_name_kanji, 50)){
						if(!this.checkLenB(shozoku_name_kanji, 80)){
							String msg = i+"�s�� �@�֖���(���{��)�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----�@�֗��̃`�F�b�N
					if(shozoku_ryakusho == null || shozoku_ryakusho.equals("")){
						String msg = i+"�s�� �@�֗��͕̂K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(shozoku_ryakusho, 20)){
							String msg = i+"�s�� �@�֗��̂̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----�X�֔ԍ��`�F�b�N
					//���l�`�F�b�N
					if(!this.checkNum(shozoku_zip)){
						//���p�����Ŗ����ꍇ
						String msg = i+"�s�� �X�֔ԍ��͔��p�����ł͂���܂���B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//���p�����̏ꍇ
						//�����`�F�b�N�i7���Ŗ����ꍇ�́A�擪��"0"��������j
						int cd_length = shozoku_zip.length();
						if(cd_length == 1){
							shozoku_zip = "000000" + shozoku_zip;
						}else if(cd_length == 2){
							shozoku_zip = "00000" + shozoku_zip;
						}else if(cd_length == 3){
							shozoku_zip = "0000" + shozoku_zip;
						}else if(cd_length == 4){
							shozoku_zip = "000" + shozoku_zip;
						}else if(cd_length == 5){
							shozoku_zip = "00" + shozoku_zip;
						}else if(cd_length == 6){
							shozoku_zip = "0" + shozoku_zip;
						}else if(cd_length == 0 || cd_length > 7){
							String msg = i+"�s�� �X�֔ԍ��̌������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}					
						//4���ڂɃn�C�t����}������
						shozoku_zip = shozoku_zip.substring(0,3)
									  + "-"
									  + shozoku_zip.substring(3);
					}
					//-----�Z��1�`�F�b�N
					if(shozoku_address1 != null && !shozoku_address1.equals("")){
						if(!this.checkLenB(shozoku_address1, 50)){
							String msg = i+"�s�� �Z��1�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----�Z��2�`�F�b�N
					if(shozoku_address2 != null && !shozoku_address2.equals("")){
						if(!this.checkLenB(shozoku_address2, 50)){
							String msg = i+"�s�� �Z��2�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----���l�`�F�b�N
					if(shozoku_biko != null && !shozoku_biko.equals("")){
						if(!this.checkLenB(shozoku_biko, 200)){
							String msg = i+"�s�� ���l�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					
					//2007/4/27 �d�l�ύX���ǉ�
					//�@�֎�ʂƋ@�֋敪�͌��J�敪�}�X�^�ɑ��݂��Ȃ��ꍇ�G���[�Ƃ���
					if (err_flg == 0){
						int kensu = kikanDao.checkKokaiKubun(connection, shubetu_cd, kikan_kubun);
						if (kensu == 0){
							String msg = i+"�s�� �@�֋敪�Ƌ@�֎�ʂ̑g�ݍ��킹���u���J�敪�}�X�^�v�ɑ��݂��܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//2007/4/27 �d�l�ύX���ǉ�����
					
					//�G���[�����������ꍇ�̂ݓo�^����
					if(err_flg == 0){
						//DB�ɓo�^����
						KikanInfo info = new KikanInfo();
						info.setShubetuCd(shubetu_cd);
						info.setKikanKubun(kikan_kubun);
						info.setShozokuCd(shozoku_cd);
						info.setShozokuNameKanji(shozoku_name_kanji);
						info.setShozokuRyakusho(shozoku_ryakusho);
						info.setShozokuZip(shozoku_zip);
						info.setShozokuAddress1(shozoku_address1);
						info.setShozokuAddress2(shozoku_address2);
						info.setBiko(shozoku_biko);
						kikanDao.insertMasterKikan(connection, info);
						
						//�����S���҂̑��������X�V�i�@�֖��i�a���j�A�@�֖��i���́j�j
						shozokuDao.updateShozokuInfo(connection, info);			
						//�\���҂̑��������X�V�i�@�֖��i�a���j�A�@�֖��i���́j�j
						shinseishaDao.updateShinseishaInfo(connection, info);
						
						cnt++;	//��荞�݌������J�E���g
					}else{
						//�f�[�^�ɕs�������邽�߁A�o�^���s��Ȃ��i�s�����m�ۂ���j
						err_list.add(Integer.toString(i));
					}
						
				}
				
			}
			
			//�}�X�^�Ǘ��e�[�u���̍X�V
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_KIKAN);
			mkInfo.setMasterName("�����@�փ}�X�^");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);			//�g�p���ĂȂ��H		
			mkInfo.setImportErrMsg(err_list);	//�G���[���i��ʂŎg�p�j
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;
						
		} catch (IOException e) {
			throw new ApplicationException(
						"CSV�t�@�C���̓Ǎ����ɃG���[���������܂����B",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"�}�X�^�捞����DB�G���[���������܂����B",
						new ErrorInfo("errors.4001"),
						e);
		} finally {
			try {
				if (success) {
					//�R�~�b�g
					DatabaseUtil.commit(connection);
				} else {
					//���[���o�b�N
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�}�X�^�捞����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	/**
	 * ���ǃ}�X�^�捞.<br />
	 * ���ǃ}�X�^�����擾����B<br />
	 * <b>1.CSV�f�[�^�����X�g�`���ɕϊ�</b><br />
	 * �����œn���ꂽfileRes��List�`���֕ϊ�����BList�̗v�f�Ƃ��Ă����List�������A
	 * ���̗v�f��CSV���̈ꃌ�R�[�h���̏����i�[����B
	 * <br /><br />
	 * <b>2.�J�������`�F�b�N</b><br />
	 * 1.�ō쐬����List�̈�ڂ̗v�f��List�̗v�f�����A6�ł��邩�m�F����B����ȊO�̏ꍇ�́A��O��throw����B
	 * <br /><br />
	 * <b>3.csv�t�@�C���i�[</b><br />
	 * ���N���X��kakuno()���\�b�h���g�p����CSV�t�@�C�����T�[�o�֊i�[����B
	 * <br /><br />
	 * <b>4.�}�X�^�G�N�X�|�[�g</b><br />
	 * ���ǃ}�X�^���o�b�N�A�b�v�p�ɃG�N�X�|�[�g����B�G�N�X�|�[�g�́AApplicationSettings.properties�́A
	 * EXPORT_COMMAND�Œ�`����Ă���R�}���h�����s���邱�Ƃɂ���čs����B<br />
	 * �o�͂����_���v�t�@�C���̃t�@�C�����͈ȉ��̒ʂ�B<br />
	 * MASTER_BUKYOKU_yyyyMMddHHmmssSSS.DMP
	 * <br /><br />
	 * <b>5.���Ǐ��폜</b><br />
	 * ���Ǐ����A�ȉ���SQL�����s���邱�Ƃɂ���č폜����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM MASTER_BUKYOKU
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br /><br />
	 * <b>6.���捞</b><br />
	 * 1.�ō쐬����CSV�t�@�C�����List�𕔋ǃ}�X�^��INSERT����B
	 * List�Ɋi�[����Ă���v�fList�ɂ́A�ȉ��̏�񂪊i�[����Ă���B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>�v�f�ԍ�</td><td>�e�[�u����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>���ǃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>���Ȗ���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>���ȗ���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>�J�e�S��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>�\�[�g�ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>���l</td></tr>
	 *	</table><br />
	 * 
	 * �e��̏��́A�����`�F�b�N�A�^�`�F�b�N����ш�ӃL�[����`�F�b�N���s���A��肪�Ȃ����INSERT�������s���B
	 * ��肪�������ꍇ�́A�G���[�����Ǘ�����List�Ɋi�[�����̂��A���̏��֏�����i�߂�B
	 * �Ȃ��A���ǃR�[�h�́A�e�`�F�b�N�ƂƂ��ɍ�0���ߏ������s���B
	 * INSERT����ۂɎ��s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		MASTER_BUKYOKU(
	 *			 BUKYOKU_CD		-- ���ǃR�[�h
	 *			,BUKA_NAME		-- ���Ȗ���
	 *			,BUKA_RYAKUSHO		-- ���ȗ���
	 *			,BUKA_KATEGORI		-- �J�e�S��
	 *			,SORT_NO		-- �\�[�g�ԍ�
	 *			,BIKO		-- ���l
	 *		)
	 *	VALUES
	 *		(?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><td>CSV����擾�������ǃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ȗ���</td><td>CSV����擾�������Ȗ���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ȗ���</td><td>CSV����擾�������ȗ���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�J�e�S��</td><td>CSV����擾�����J�e�S��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�\�[�g�ԍ�</td><td>CSV����擾�����\�[�g�ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���l</td><td>CSV����擾�������l</td></tr>
	 *	</table>
	 * <br /><br />
	 * <b>7.�}�X�^�Ǘ��}�X�^�X�V</b><br />
	 * �}�X�^�Ǘ��}�X�^���ȉ��̏��ōX�V����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�X�V���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^���</td><td>4</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^����</td><td>"���ǃ}�X�^"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�ݓ���</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����</td><td>Insert�������R�[�h��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�݃e�[�u����</td><td>"MASTER_BUKYOKU"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�K�E�X�V�t���O</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�捞�G���[���b�Z�[�W</td><td>���R�[�hINSERT�Ɏ��s�����ꍇ�A�G���[���b�Z�[�W</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSV�t�@�C���p�X</td><td>CSV�i�[��t�H���_�{CSV�t�@�C����</td></tr>
	 *	</table>
	 * <br /><br />
	 * <b>8.�}�X�^�Ǘ����ԋp</b><br />
	 * 7.�ōX�V���������Ăяo�����֕ԋp����B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param fileRes				�A�b�v���[�h�t�@�C�����\�[�X�B
	 * @return						�������ʏ��
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	private MasterKanriInfo torikomiMasterBukyoku(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException {

		int columnSize = 6;
		
		boolean success = false;
		Connection connection = null;

		try{
			//CSV�f�[�^�����X�g�`���ɕϊ�����
			List dt = csvFile2List(fileRes);
			
			//�J�������`�F�b�N(1�s�ڂ̂�)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				
				if(line1.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�A�G���[���o�͂��������I������B
					throw new ApplicationException(
								"CSV�t�@�C�������ǃ}�X�^�̒�`�ƈقȂ�܂��B",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csv�t�@�C���i�[
			String table_name = "MASTER_BUKYOKU";
			String csvPath = kakuno(fileRes, table_name);
			
			//DB�̃G�N�X�|�[�g
			String file_name = "MASTER_BUKYOKU_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//�R�l�N�V�����擾
			connection = DatabaseUtil.getConnection();

			//�捞�iDELETE �� INSERT�����j
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);
			
			int cnt = 0;													//����捞����
			ArrayList cd_list  = new ArrayList();							//�捞�f�[�^�̃L�[�i�[�z��(��Ӑ���`�F�b�N�p)
			ArrayList err_list = new ArrayList();							//�捞�݃G���[�s�i�[�z��
			int dtsize = dt.size();											//�捞�S����
			
			//DAO
			MasterBukyokuInfoDao bukyokuDao = new MasterBukyokuInfoDao(userInfo);
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[�Ƃ��ď������΂��B�i�s�����m�ۂ���j
					String msg = i+"�s�� ����(����)�}�X�^�̃e�[�u����`�ƈ�v���܂���B";
					mtLog.warn(msg);
					//err_list.add(msg);				//�G���[���e���i�[ �� �ۗ��B�R�����g�A�E�g
					err_list.add(Integer.toString(i));	//�G���[�̍s�����m��
					
				}else{
					//���폈��
					//�G���[�t���O
					int err_flg = 0;
					
					//�}�X�^�f�[�^�̎擾
					String bukyoku_cd    = (String)line.get(0);		//���ǃR�[�h
					String buka_name     = (String)line.get(1);		//���Ȗ���
					String buka_ryakusho = (String)line.get(2);		//���ȗ���
					String buka_kategori = (String)line.get(3);		//�J�e�S��
					String sort_no       = (String)line.get(4);		//�\�[�g�ԍ�
					String biko          = (String)line.get(5);		//���l
										
					//-----���ǃR�[�h�`�F�b�N
					//���l�`�F�b�N
					if(!this.checkNum(bukyoku_cd)){
						//���p�����Ŗ����ꍇ
						String msg = i+"�s�� ���ǃR�[�h�͔��p�����ł͂���܂���B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//���p�����̏ꍇ
						//�����`�F�b�N�i3���Ŗ����ꍇ�́A�擪��"0"��������j
						int cd_length = bukyoku_cd.length();
						if(cd_length == 1){
							bukyoku_cd = "00" + bukyoku_cd;
						}else if(cd_length == 2){
							bukyoku_cd = "0" + bukyoku_cd;
						}else if(cd_length == 0 || cd_length > 3){
							String msg = i+"�s�� ���ǃR�[�h�̌������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						//KEY���ڂ̏d���`�F�b�N
						if(cd_list.contains(bukyoku_cd)){
							String msg = i+"�s�� ���ǃR�[�h�͏d�����Ă��܂��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							cd_list.add(bukyoku_cd);
						}
					}
					//-----���Ȗ��̃`�F�b�N
					if(buka_name == null || buka_name.equals("")){
						String msg = i+"�s�� ���Ȗ��͕̂K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(buka_name, 30)){
							String msg = i+"�s�� ���Ȗ��̂̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----���ȗ��̃`�F�b�N
					if(buka_ryakusho == null || buka_ryakusho.equals("")){
						String msg = i+"�s�� ���ȗ��͕̂K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(buka_ryakusho, 8)){
							String msg = i+"�s�� ���ȗ��̂̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----�J�e�S���`�F�b�N
					if(buka_kategori == null || buka_kategori.equals("")){
						String msg = i+"�s�� �J�e�S���͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//���l�`�F�b�N
						if(!this.checkNum(buka_kategori)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �J�e�S���͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i2���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = buka_kategori.length();
							if(cd_length == 1){
								buka_kategori = "0" + buka_kategori;
							}else if(cd_length == 0 || cd_length > 2){
								String msg = i+"�s�� �J�e�S���̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}					
					}
					//-----�\�[�g�ԍ��`�F�b�N
					if(sort_no != null && !sort_no.equals("")){
						//���l�`�F�b�N
						if(!this.checkNum(sort_no)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �\�[�g�ԍ��͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							if(!this.checkLenB(sort_no, 4)){
								String msg = i+"�s�� �\�[�g�ԍ��̒������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}					
					}					
					//-----���l�`�F�b�N
					if(biko != null && !biko.equals("")){
						if(!this.checkLenB(biko, 200)){
							String msg = i+"�s�� ���l�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					

					
					//�G���[�����������ꍇ�̂ݓo�^����
					if(err_flg == 0){
						//DB�ɓo�^����
						BukyokuInfo info = new BukyokuInfo();
						info.setBukyokuCd(bukyoku_cd);
						info.setBukaName(buka_name);
						info.setBukaRyakusyo(buka_ryakusho);
						info.setBukaKategori(buka_kategori);
						info.setSortNo(sort_no);
						info.setBiko(biko);
						bukyokuDao.insertBukyokuInfo(connection, info);
						cnt++;	//��荞�݌������J�E���g
					}else{
						//�f�[�^�ɕs�������邽�߁A�o�^���s��Ȃ��i�s�����m�ۂ���j
						err_list.add(Integer.toString(i));
					}
						
				}
				
			}

			//�}�X�^�Ǘ��e�[�u���̍X�V
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_BUKYOKU);
			mkInfo.setMasterName("���ǃ}�X�^");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);			//�g�p���ĂȂ��H		
			mkInfo.setImportErrMsg(err_list);	//�G���[���i��ʂŎg�p�j
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;
			
		} catch (IOException e) {
			throw new ApplicationException(
						"CSV�t�@�C���̓Ǎ����ɃG���[���������܂����B",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"�}�X�^�捞����DB�G���[���������܂����B",
						new ErrorInfo("errors.4001"),
						e);
		} finally {
			try {
				if (success) {
					//�R�~�b�g
					DatabaseUtil.commit(connection);
				} else {
					//���[���o�b�N
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�}�X�^�捞����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	/**
	 * �w�n�p�R�����}�X�^�捞.<br />
	 * �w�n�p�R�����}�X�^�����擾����B<br />
	 * <b>1.CSV�f�[�^�����X�g�`���ɕϊ�</b><br />
	 * �����œn���ꂽfileRes��List�`���֕ϊ�����BList�̗v�f�Ƃ��Ă����List�������A
	 * ���̗v�f��CSV���̈ꃌ�R�[�h���̏����i�[����B
	 * <br /><br />
	 * <b>2.�J�������`�F�b�N</b><br />
	 * 1.�ō쐬����List�̈�ڂ̗v�f��List�̗v�f�����A17�ł��邩�m�F����B����ȊO�̏ꍇ�́A��O��throw����B
	 * <br /><br />
	 * <b>3.csv�t�@�C���i�[</b><br />
	 * ���N���X��kakuno()���\�b�h���g�p����CSV�t�@�C�����T�[�o�֊i�[����B
	 * <br /><br />
	 * <b>4.�}�X�^�G�N�X�|�[�g</b><br />
	 * �w�n�p�R�����}�X�^���o�b�N�A�b�v�p�ɃG�N�X�|�[�g����B�G�N�X�|�[�g�́AApplicationSettings.properties�́A
	 * EXPORT_COMMAND�Œ�`����Ă���R�}���h�����s���邱�Ƃɂ���čs����B<br />
	 * �o�͂����_���v�t�@�C���̃t�@�C�����͈ȉ��̒ʂ�B<br />
	 * MASTER_SHINSAIN_GAKUSOU_KOSHIN_yyyyMMddHHmmssSSS.DMP(�X�V)<br />
	 * MASTER_SHINSAIN_GAKUSOU_SHINKI_yyyyMMddHHmmssSSS.DMP(�V�K)<br />
	 * <br />
	 * <br />
	 * <b>5.�w�n�p�R�������폜</b><br />
	 * �R�����}�X�^���A�ȉ���SQL�����s���邱�Ƃɂ���č폜����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		MASTER_SHINSAIN
	 *	WHERE
	 *		JIGYO_KUBUN = 1 -- ���Ƌ敪�i�w�n���E�j
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * ��O������true�̏ꍇ�́A�����ĐR���������ȉ���SQL�ɂč폜����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		SHINSAININFO
	 *	WHERE
	 *		WHERE SUBSTR(SHINSAIN_ID,3,1) = 1 -- ���Ƌ敪�i�w�n���E�j
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * <br />
	 * <b>6.���捞</b><br />
	 * 1.�ō쐬����CSV�t�@�C�����List��R�����}�X�^��INSERT����B
	 * List�Ɋi�[����Ă���v�fList�ɂ͈ȉ��̏�񂪊i�[����Ă���B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>�v�f�ԍ�</td><td>�e�[�u����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>�R�����ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>�����i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>�����i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>�����t���K�i�i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>�����t���K�i�i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>�@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>�@�֖�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>���ǖ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>�E��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>9</td><td>���t��X�֔ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>10</td><td>���t��Z��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>11</td><td>���t��Email</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>12</td><td>�d�b�ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>13</td><td>FAX�ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>14</td><td>URL</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>15</td><td>��啪��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>16</td><td>���l</td></tr>
	 *	</table><br />
	 * 
	 * �e��̏��́A�����`�F�b�N�A�^�`�F�b�N����ш�ӃL�[����`�F�b�N���s���A��肪�Ȃ����INSERT�������s���B
	 * INSERT����ہA���Ƌ敪��1�i�w�n���E�j�Ƃ���B
	 * ��肪�������ꍇ�́A�G���[�����Ǘ�����List�Ɋi�[�����̂��A���̏��֏�����i�߂�B
	 * �Ȃ��A�R�����ԍ��A�@�փR�[�h�́A�e�`�F�b�N�ƂƂ��ɍ�0���ߏ������s���B
	 * INSERT����ۂɎ��s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		MASTER_SHINSAIN(
	 *			 SHINSAIN_NO		-- �R�����ԍ�
	 *			,JIGYO_KUBUN		-- ���Ƌ敪
	 *			,NAME_KANJI_SEI		-- �����i���j
	 *			,NAME_KANJI_MEI		-- �����i���j
	 *			,NAME_KANA_SEI		-- �����t���K�i�i���j
	 *			,NAME_KANA_MEI		-- �����t���K�i�i���j
	 *			,SHOZOKU_CD		-- �����@�փR�[�h
	 *			,SHOZOKU_NAME		-- �@�֖���
	 *			,BUKYOKU_NAME		-- ���ǖ���
	 *			,SHOKUSHU_NAME		-- �E�햼��
	 *			,SOFU_ZIP		-- ���t��X�֔ԍ�
	 *			,SOFU_ZIPADDRESS		-- ���t��Z��
	 *			,SOFU_ZIPEMAIL		-- ���t��Email
	 *			,SHOZOKU_TEL		-- �d�b�ԍ�
	 *			,SHOZOKU_FAX		-- FAX
	 *			,URL		-- URL
	 *			,SENMON		-- ��啪��
	 *			,KOSHIN_DATE		-- �X�V��
	 *			,BIKO		-- ���l
	 *		)
	 *	VALUES
	 *		(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>CSV����擾�����R�����ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>1</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i���j</td><td>CSV����擾���������i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i���j</td><td>CSV����擾���������i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����t���K�i�i���j</td><td>CSV����擾���������t���K�i�i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����t���K�i�i���j</td><td>CSV����擾���������t���K�i�i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>CSV����擾���������@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�@�֖���</td><td>CSV����擾�����@�֖���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ǖ���</td><td>CSV����擾�������ǖ���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�E�햼��</td><td>CSV����擾�����E�햼��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���t��X�֔ԍ�</td><td>CSV����擾�������t��X�֔ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���t��Z��</td><td>CSV����擾�������t��Z��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���t��Email</td><td>CSV����擾�������t��Email</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�d�b�ԍ�</td><td>CSV����擾�����d�b�ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>FAX</td><td>CSV����擾����FAX</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>URL</td><td>CSV����擾����URL</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��啪��</td><td>CSV����擾������啪��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�X�V��</td><td>WAS�̌��ݓ��t</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���l</td><td>CSV����擾�������l</td></tr>
	 *	</table><br/>
	 * �R�����}�X�^��INSERT�����R������񂪁A�R�������e�[�u���ɑ��݂��Ȃ��ꍇ�́A������INSERT���s���B
	 * INSERT����ۂɎ��s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		SHINSAININFO(
	 *			 SHINSAIN_ID	-- �R����ID
	 *			,PASSWORD		-- �p�X���[�h
	 *			,YUKO_DATE		-- �L������
	 *			,DEL_FLG		-- �폜�t���O
	 *		)
	 *	VALUES
	 *		(?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R����ID</td><td>�N�x�{1�{�R�����ԍ�+�`�F�b�N�f�W�b�g</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�p�X���[�h</td><td>�h�c�p�X���[�h���s���[���ɂ���č쐬</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�L������</td><td>�h�c�p�X���[�h���s���[���e�[�u������擾</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>0</td></tr>
	 *	</table><br/>
	 * <br />
	 * <b>7.�}�X�^�Ǘ��}�X�^�X�V</b><br />
	 * �}�X�^�Ǘ��}�X�^���ȉ��̏��ōX�V����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�X�V���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^���</td><td>5</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^����</td><td>"�R�����}�X�^�i�w�p�n���p�j"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�ݓ���</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����</td><td>Insert�������R�[�h��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�݃e�[�u����</td><td>"MASTER_SHINSAIN_GAKUSOU"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�K�E�X�V�t���O</td><td>0:�V�K�A1:�X�V</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�捞�G���[���b�Z�[�W</td><td>���R�[�hINSERT�Ɏ��s�����ꍇ�A�G���[���b�Z�[�W</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSV�t�@�C���p�X</td><td>CSV�i�[��t�H���_�{CSV�t�@�C����</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>8.�}�X�^�Ǘ����ԋp</b><br />
	 * 7.�ōX�V���������Ăяo�����֕ԋp����B
	 * 
	 * @param userInfo		UserInfo
	 * @param fileRes		CSV�t�@�C�����
	 * @param koshinFlg		true:�X�V��荞�݁Afalse:�V�K��荞��
	 * @return	�w�n�p�R�����}�X�^�̃}�X�^�Ǘ����
	 * @throws ApplicationException	���炩�̗�O
	 */
	private MasterKanriInfo torikomiMasterShinsainGakusou(UserInfo     userInfo,
														  FileResource fileRes,
														  boolean     koshinFlg)
		throws ApplicationException
	{
		int columnSize = 17;
		
		boolean success = false;
		Connection connection = null;

		try{
			//CSV�f�[�^�����X�g�`���ɕϊ�����
			List dt = csvFile2List(fileRes);

			//�J�������`�F�b�N(1�s�ڂ̂�)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[���o�͂��������I������B
					throw new ApplicationException(
								"CSV�t�@�C�����R�����}�X�^�i�w�n�j�̒�`�ƈقȂ�܂��B",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csv�t�@�C���i�[
			String tableName = "MASTER_SHINSAIN_GAKUSOU";
			String csvPath = kakuno(fileRes, tableName);
			
			//DB�̃G�N�X�|�[�g
			String footer = (koshinFlg ? "_KOSHIN_" : "_SHINKI_"); 
			String table_name = "MASTER_SHINSAIN,SHINSAININFO";
			String file_name = tableName + footer + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//�R�l�N�V�����擾
			connection = DatabaseUtil.getConnection();

			//�L�������̎擾
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSAIN);
			RuleInfoDao ruledao = new RuleInfoDao(userInfo);
			RuleInfo rinfo = ruledao.selectRuleInfo(connection, rulePk);
//			String yukoDate = new SimpleDateFormat("yyyyMMdd").format(rinfo.getYukoDate());

			//�捞�iDELETE �� INSERT�����j�i���Ƌ敪���w�n[1]�̂݁j
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection,
							 "MASTER_SHINSAIN", 
							 "WHERE JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
			if(!koshinFlg){
				//�u�V�K�v�̂Ƃ��͐R�������e�[�u�����폜�i���Ƌ敪���w�n[1]�̂݁j
				dao.deleteMaster(connection, 
								 "SHINSAININFO",
								 "WHERE SUBSTR(SHINSAIN_ID,3,1) = " + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);
			}
			
			int cnt = 0;												//����捞����
			ArrayList cd_list  = new ArrayList();						//�捞�f�[�^�̃L�[�i�[�z��(��Ӑ���`�F�b�N�p)
			ArrayList err_list = new ArrayList();						//�捞�݃G���[�s�i�[�z��
			int dtsize = dt.size();										//�捞�S����
			Map kikanMap = new HashMap();								//�@�փR�[�h�Ƌ@�֖��̂�Map
			DateUtil nowDateUtil = new DateUtil(new Date());			//�����_�ł̓������
			String strNendo = nowDateUtil.getYearYY();					//�����_�ł̔N�x�i����N�̉��Q���j 
			
			//DAO
			ShinsainInfoDao shinsainDao = new ShinsainInfoDao(userInfo);
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[�Ƃ��ď������΂��B�i�s�����m�ۂ���j
					String msg = i+"�s�� �R�����}�X�^�i�w�n�j�̃e�[�u����`�ƈ�v���܂���B";
					mtLog.warn(msg);
					//err_list.add(msg);				//�G���[���e���i�[ �� �ۗ��B�R�����g�A�E�g
					err_list.add(Integer.toString(i));	//�G���[�̍s�����m��
					
				}else{
					//���폈��
					//�G���[�t���O
					int err_flg = 0;
					
					//�}�X�^�f�[�^�̎擾
					int j = 0;
					String shinsain_no        = (String)line.get(j++);	//�R�����ԍ�
					String shimei_kanji_sei   = (String)line.get(j++);	//�����i���j
					String shimei_kanji_mei   = (String)line.get(j++);	//�����i���j
					String shimei_kana_sei    = (String)line.get(j++);	//�����t���K�i�i���j
					String shimei_kana_mei    = (String)line.get(j++);	//�����t���K�i�i���j
					String kikan_cd           = (String)line.get(j++);	//�@�փR�[�h
					String kikan_name         = (String)line.get(j++);	//�@�֖�
					String bukyoku_name       = (String)line.get(j++);	//���ǖ�
					String shokushu           = (String)line.get(j++);	//�E��
					String sofusaki_zip       = (String)line.get(j++);	//���t��X�֔ԍ�
					String sofusaki_address   = (String)line.get(j++);	//���t��Z��
					String sofusaki_emai      = (String)line.get(j++);	//���t��Email
					String tel                = (String)line.get(j++);	//�d�b�ԍ�
					String fax                = (String)line.get(j++);	//FAX�ԍ�
					String url                = (String)line.get(j++);	//URL
					String senmonbunya        = (String)line.get(j++);	//��啪��
					String biko               = (String)line.get(j++);	//���l
					
					//-----�R�����ԍ��`�F�b�N
					//���l�`�F�b�N
					if(!this.checkNum(shinsain_no)){
						//���p�����Ŗ����ꍇ
						String msg = i+"�s�� �R�����ԍ��͔��p�����ł͂���܂���B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//���p�����̏ꍇ
						//�����`�F�b�N�i7���Ŗ����ꍇ�́A�擪��"0"��������j
						int cd_length = shinsain_no.length();
						if(cd_length == 1){
							shinsain_no = "000000" + shinsain_no;
						}else if(cd_length == 2){
							shinsain_no = "00000" + shinsain_no;
						}else if(cd_length == 3){
							shinsain_no = "0000" + shinsain_no;
						}else if(cd_length == 4){
							shinsain_no = "000" + shinsain_no;
						}else if(cd_length == 5){
							shinsain_no = "00" + shinsain_no;
						}else if(cd_length == 6){
							shinsain_no = "0" + shinsain_no;
						}else if(cd_length == 0 || cd_length > 7){
							String msg = i+"�s�� �R�����ԍ��̌������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						//KEY���ڂ̏d���`�F�b�N
						if(cd_list.contains(shinsain_no)){
							String msg = i+"�s�� �R�����ԍ��͏d�����Ă��܂��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							cd_list.add(shinsain_no);
						}
					}
					//-----�����i���j�`�F�b�N
					if(shimei_kanji_sei == null || shimei_kanji_sei.equals("")){
						String msg = i+"�s�� �����i�������|���j�͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(shimei_kanji_sei, 100)){
							String msg = i+"�s�� �����i�������|���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----�����i���j�`�F�b�N
					if(shimei_kanji_mei != null && !shimei_kanji_mei.equals("")){
						if(!this.checkLenB(shimei_kanji_mei, 100)){
							String msg = i+"�s�� �����i�������|���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----�����t���K�i�i���j�`�F�b�N
					if(shimei_kana_sei != null && !shimei_kana_sei.equals("")){
						if(!this.checkLenB(shimei_kana_sei, 100)){
							String msg = i+"�s�� �����i�t���K�i�|���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----�����t���K�i�i���j�`�F�b�N
					if(shimei_kana_mei != null && !shimei_kana_mei.equals("")){
						if(!this.checkLenB(shimei_kana_mei, 100)){
							String msg = i+"�s�� �����i�t���K�i�|���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----�@�փR�[�h�`�F�b�N
					//2005.11.21 iso ���p�`�F�b�N�ɕύX
//					//���l�`�F�b�N
//					if(!this.checkNum(kikan_cd)){
					if(!this.checkHan(kikan_cd)){
						//���p�����Ŗ����ꍇ
						String msg = i+"�s�� �@�փR�[�h�͔��p�����ł͂���܂���B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//���p�����̏ꍇ
						//�����`�F�b�N�i5���Ŗ����ꍇ�́A�擪��"0"��������j
						int cd_length = kikan_cd.length();
						if(cd_length == 1){
							kikan_cd = "0000" + kikan_cd;
						}else if(cd_length == 2){
							kikan_cd = "000" + kikan_cd;
						}else if(cd_length == 3){
							kikan_cd = "00" + kikan_cd;
						}else if(cd_length == 4){
							kikan_cd = "0" + kikan_cd;
						//2005.11.21 iso �󏊑��@�փR�[�h��œo�^����悤�ύX
//						}else if(cd_length == 0 || cd_length > 5){
						}else if(cd_length > 5){
							String msg = i+"�s�� �@�փR�[�h�̌������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----�@�֖��i�a���j�`�F�b�N
					if(kikan_name == null || kikan_name.equals("")){
						//�ŏ��̂P�񂾂�DB�A�N�Z�X
						if(kikanMap.isEmpty()){
							setKikanNameList(connection, kikanMap);
						}
						//���Y�@�փR�[�h�̋@�֖��i�a���j���Q�b�g
						if(kikanMap.containsKey(kikan_cd)){
							kikan_name = (String)kikanMap.get(kikan_cd);	//�}�b�v�ɑ��݂���΂�����Z�b�g	
						}else{
							kikan_name = null;								//���݂��Ȃ��ꍇ��null�œo�^
						}
					}else{
						if(!this.checkLenB(kikan_name, 100)){
							String msg = i+"�s�� �@�֖��i�a���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}															
					//-----���ǖ��`�F�b�N
					if(bukyoku_name != null && !bukyoku_name.equals("")){
						if(!this.checkLenB(bukyoku_name, 100)){
							String msg = i+"�s�� ���ǖ��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----�E�햼�`�F�b�N
					if(shokushu != null && !shokushu.equals("")){
						if(!this.checkLenB(shokushu, 40)){
							String msg = i+"�s�� �E�햼�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}															
					//-----���t��X�֔ԍ��`�F�b�N
					if(sofusaki_zip != null && !sofusaki_zip.equals("")){
						if(!this.checkLenB(sofusaki_zip, 8)){
							String msg = i+"�s�� ���t��X�֔ԍ��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}																				
					//-----���t��Email�`�F�b�N
					if(sofusaki_emai != null && !sofusaki_emai.equals("")){
						if(!GenericValidator.isEmail(sofusaki_emai)){
							String msg = i+"�s�� ���t��Email�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							if(!this.checkLenB(sofusaki_emai, 100)){
								String msg = i+"�s�� ���t��Email�̒������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}																				
					//-----�d�b�ԍ��`�F�b�N
					if(tel != null && !tel.equals("")){
						if(!this.checkLenB(tel, 50)){
							String msg = i+"�s�� �d�b�ԍ��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}															
					//-----FAX�ԍ��`�F�b�N
					if(fax != null && !fax.equals("")){
						if(!this.checkLenB(fax, 50)){
							String msg = i+"�s�� FAX�ԍ��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}																				
					//-----URL�`�F�b�N
					if(url != null && !url.equals("")){
						if(!this.checkLenB(url, 100)){
							String msg = i+"�s�� URL�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}																				
					//-----��啪��`�F�b�N
					if(senmonbunya != null && !senmonbunya.equals("")){
						if(!this.checkLenB(senmonbunya, 40)){
							String msg = i+"�s�� ��啪��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}																									
					//-----���l�`�F�b�N
					if(biko != null && !biko.equals("")){
						if(!this.checkLenB(biko, 200)){
							String msg = i+"�s�� ���l�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}																									

					
					//�G���[�����������ꍇ�̂ݓo�^����
					if(err_flg == 0){
						//�R����ID�̎擾�i�N�x�{�敪�i�w�n��[1]�Œ�j�{�R�����ԍ��j
						String key = strNendo + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO + shinsain_no;
						String shinsain_id = key + CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
						
						//�R�������̐���
						ShinsainInfo info = new ShinsainInfo();
						info.setShinsainId(shinsain_id);					//�R����ID
						info.setPassword(RandomPwd.generate(rinfo));		//�p�X���[�h�̎擾
						info.setYukoDate(rinfo.getYukoDate());				//�L������
						info.setDelFlg("0");								//�폜�t���O�i0:�Œ�j
						info.setShinsainNo(shinsain_no);
						info.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);	//���Ƌ敪�i�w�n��[1]�Œ�j
						info.setNameKanjiSei(shimei_kanji_sei);
						info.setNameKanjiMei(shimei_kanji_mei);
						info.setNameKanaSei(shimei_kana_sei);
						info.setNameKanaMei(shimei_kana_mei);
						info.setShozokuCd(kikan_cd);
						info.setShozokuName(kikan_name);
						info.setBukyokuName(bukyoku_name);
						info.setShokushuName(shokushu);
						info.setSofuZip(sofusaki_zip);
						info.setSofuZipaddress(sofusaki_address);
						info.setSofuZipemail(sofusaki_emai);						
						info.setShozokuTel(tel);
						info.setShozokuFax(fax);
						info.setUrl(url);
						info.setSenmon(senmonbunya);
						info.setKoshinDate(nowDateUtil.getDateYYYYMMDD());	//�X�V����
						info.setBiko(biko);
						
						//���[���t���O�i�u���t��E-mail�v�����͂���Ă�����t���O��"0"�ōX�V�A�����͂̏ꍇ��null�ōX�V�j
						if(StringUtil.isBlank(sofusaki_emai)){
							info.setMailFlg(null);
						}else{
							info.setMailFlg("0");
						}
						
						//---�V�K�̂Ƃ�---
						if(!koshinFlg){
							//DB�ɓo�^����i�R�����}�X�^�ƐR�������e�[�u���j
							shinsainDao.insertShinsainInfo(connection, info);
						//---�X�V�̂Ƃ�---	
						}else{
							//�X�V�ŁA�R�������e�[�u���Ɋ����f�[�^�������ꍇ
							if(shinsainDao.checkShinsainInfo(connection,
															 shinsain_no,
															 IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO) == 0){
								//DB�ɓo�^����i�R�����}�X�^�ƐR�������e�[�u���j
								shinsainDao.insertShinsainInfo(connection, info);
							//�X�V�ŁA�R�������e�[�u���Ɋ����f�[�^������ꍇ
							}else{
								//DB�ɓo�^����i�R�����}�X�^�ƐR�������e�[�u���̃��[���t���O�̂݁j
								shinsainDao.insertShinsainInfoOnlyMaster(connection, info);
								shinsainDao.updateMailFlgShinsainInfo(connection, info);
							}
						}

						cnt++;	//��荞�݌������J�E���g
						
					}else{
						//�f�[�^�ɕs�������邽�߁A�o�^���s��Ȃ��i�s�����m�ۂ���j
						err_list.add(Integer.toString(i));
					}
				
				}	
				
			}
			
			//�}�X�^�Ǘ��e�[�u���̍X�V
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_SHINSAIN_GAKUJUTU);
			mkInfo.setMasterName("�R�����}�X�^�i�w�p�n���p�j");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg((koshinFlg ? "1" : "0"));	//�V�K=0, �X�V=1
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);						//�g�p���ĂȂ��H		
			mkInfo.setImportErrMsg(err_list);				//�G���[���i��ʂŎg�p�j
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;			
			
		} catch (IOException e) {
			throw new ApplicationException(
						"CSV�t�@�C���̓Ǎ����ɃG���[���������܂����B",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"�}�X�^�捞����DB�G���[���������܂����B",
						new ErrorInfo("errors.4001"),
						e);
		} finally {
			try {
				if (success) {
					//�R�~�b�g
					DatabaseUtil.commit(connection);
				} else {
					//���[���o�b�N
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�}�X�^�捞����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	/**
	 * ��՗p�R�����}�X�^�捞.<br />
	 * ��՗p�R�����}�X�^�����擾����B<br />
	 * <b>1.CSV�f�[�^�����X�g�`���ɕϊ�</b><br />
	 * �����œn���ꂽfileRes��List�`���֕ϊ�����BList�̗v�f�Ƃ��Ă����List�������A
	 * ���̗v�f��CSV���̈ꃌ�R�[�h���̏����i�[����B
	 * <br /><br />
	 * <b>2.�J�������`�F�b�N</b><br />
	 * 1.�ō쐬����List�̈�ڂ̗v�f��List�̗v�f�����A9�ł��邩�m�F����B����ȊO�̏ꍇ�́A��O��throw����B
	 * <br /><br />
	 * <b>3.csv�t�@�C���i�[</b><br />
	 * ���N���X��kakuno()���\�b�h���g�p����CSV�t�@�C�����T�[�o�֊i�[����B
	 * <br /><br />
	 * <b>4.�}�X�^�G�N�X�|�[�g</b><br />
	 * ��՗p�R�����}�X�^���o�b�N�A�b�v�p�ɃG�N�X�|�[�g����B�G�N�X�|�[�g�́AApplicationSettings.properties�́A
	 * EXPORT_COMMAND�Œ�`����Ă���R�}���h�����s���邱�Ƃɂ���čs����B<br />
	 * �o�͂����_���v�t�@�C���̃t�@�C�����͈ȉ��̒ʂ�B<br />
	 * MASTER_SHINSAIN_KIBAN_KOSHIN_yyyyMMddHHmmssSSS.DMP(�X�V)<br />
	 * MASTER_SHINSAIN_KIBAN_SHINKI_yyyyMMddHHmmssSSS.DMP(�V�K)<br />
	 * <br />
	 * <br />
	 * <b>5.��՗p�R�������폜</b><br />
	 * �R�����}�X�^���A�ȉ���SQL�����s���邱�Ƃɂ���č폜����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		MASTER_SHINSAIN
	 *	WHERE
	 *		JIGYO_KUBUN = 4 -- ���Ƌ敪�i��Ձj
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * ��O������true�̏ꍇ�́A�����ĐR���������ȉ���SQL�ɂč폜����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM
	 *		SHINSAININFO
	 *	WHERE
	 *		WHERE SUBSTR(SHINSAIN_ID,3,1) = 4 -- ���Ƌ敪�i��Ձj
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * <br />
	 * <b>6.���捞</b><br />
	 * 1.�ō쐬����CSV�t�@�C�����List��R�����}�X�^��INSERT����B
	 * List�Ɋi�[����Ă���v�fList�ɂ͈ȉ��̏�񂪊i�[����Ă���B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>�v�f�ԍ�</td><td>�e�[�u����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>�R�����ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>�����i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>�����i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>�����t���K�i�i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>�����t���K�i�i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>�@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>�@�֖�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>���ǖ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>�E��</td></tr>
	 *	</table><br />
	 * 
	 * �e��̏��́A�����`�F�b�N�A�^�`�F�b�N����ш�ӃL�[����`�F�b�N���s���A��肪�Ȃ����INSERT�������s���B
	 * INSERT����ہA���ƃR�[�h��4�i��ՂƂ���B
	 * ��肪�������ꍇ�́A�G���[�����Ǘ�����List�Ɋi�[�����̂��A���̏��֏�����i�߂�B
	 * �Ȃ��A�@�փR�[�h�́A�e�`�F�b�N�ƂƂ��ɍ�0���ߏ������s���B
	 * INSERT����ۂɎ��s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		MASTER_SHINSAIN(
	 *			 SHINSAIN_NO		-- �R�����ԍ�
	 *			,JIGYO_KUBUN		-- ���Ƌ敪
	 *			,NAME_KANJI_SEI		-- �����i���j
	 *			,NAME_KANJI_MEI		-- �����i���j
	 *			,NAME_KANA_SEI		-- �����t���K�i�i���j
	 *			,NAME_KANA_MEI		-- �����t���K�i�i���j
	 *			,SHOZOKU_CD		-- �����@�փR�[�h
	 *			,SHOZOKU_NAME		-- �@�֖���
	 *			,BUKYOKU_NAME		-- ���ǖ���
	 *			,SHOKUSHU_NAME		-- �E�햼��
	 *			,SOFU_ZIP		-- ���t��X�֔ԍ�
	 *			,SOFU_ZIPADDRESS		-- ���t��Z��
	 *			,SOFU_ZIPEMAIL		-- ���t��Email
	 *			,SHOZOKU_TEL		-- �d�b�ԍ�
	 *			,SHOZOKU_FAX		-- FAX
	 *			,URL		-- URL
	 *			,SENMON		-- ��啪��
	 *			,KOSHIN_DATE		-- �X�V��
	 *			,BIKO		-- ���l
	 *		)
	 *	VALUES
	 *		(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>CSV����擾�����R�����ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>4</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i���j</td><td>CSV����擾���������i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i���j</td><td>CSV����擾���������i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����t���K�i�i���j</td><td>CSV����擾���������t���K�i�i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����t���K�i�i���j</td><td>CSV����擾���������t���K�i�i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>CSV����擾���������@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�@�֖���</td><td>CSV����擾�����@�֖���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ǖ���</td><td>CSV����擾�������ǖ���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�E�햼��</td><td>CSV����擾�����E�햼��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���t��X�֔ԍ�</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���t��Z��</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���t��Email</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�d�b�ԍ�</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>FAX</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>URL</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��啪��</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�X�V��</td><td>WAS�̌��ݓ��t</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���l</td><td>null</td></tr>
	 *	</table><br/>
	 * �R�����}�X�^��INSERT�����R������񂪁A�R�������e�[�u���ɑ��݂��Ȃ��ꍇ�́A������INSERT���s���B
	 * INSERT����ۂɎ��s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	INSERT INTO
	 *		SHINSAININFO(
	 *			 SHINSAIN_ID	-- �R����ID
	 *			,PASSWORD		-- �p�X���[�h
	 *			,YUKO_DATE		-- �L������
	 *			,DEL_FLG		-- �폜�t���O
	 *		)
	 *	VALUES
	 *		(?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R����ID</td><td>�N�x�{4�{�R�����ԍ�+�`�F�b�N�f�W�b�g</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�p�X���[�h</td><td>�h�c�p�X���[�h���s���[���ɂ���č쐬</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�L������</td><td>�h�c�p�X���[�h���s���[���e�[�u������擾</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>0</td></tr>
	 *	</table><br/>
	 * 
	 * <br />
	 * <b>7.�}�X�^�Ǘ��}�X�^�X�V</b><br />
	 * �}�X�^�Ǘ��}�X�^���ȉ��̏��ōX�V����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�X�V���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^���</td><td>6</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^����</td><td>"�R�����}�X�^�i��Ռ������j"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�ݓ���</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����</td><td>Insert�������R�[�h��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�݃e�[�u����</td><td>"MASTER_SHINSAIN,SHINSAININFO"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�K�E�X�V�t���O</td><td>0:�V�K�A1:�X�V</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�捞�G���[���b�Z�[�W</td><td>���R�[�hINSERT�Ɏ��s�����ꍇ�A�G���[���b�Z�[�W</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSV�t�@�C���p�X</td><td>CSV�i�[��t�H���_�{CSV�t�@�C����</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>8.�}�X�^�Ǘ����ԋp</b><br />
	 * 7.�ōX�V���������Ăяo�����֕ԋp����B
	 * 
	 * @param userInfo		UserInfo
	 * @param fileRes		CSV�t�@�C�����
	 * @param koshinFlg		true:�V�K��荞�݁Afalse:�X�V
	 * @return	��՗p�R�����}�X�^�̃}�X�^�Ǘ����
	 * @throws ApplicationException	���炩�̗�O
	 */
	private MasterKanriInfo torikomiMasterShinsainKiban(UserInfo     userInfo,
														FileResource fileRes,
														boolean     koshinFlg)
		throws ApplicationException
	{
		int columnSize = 9;
		
		boolean success = false;
		Connection connection = null;

		try{
			//CSV�f�[�^�����X�g�`���ɕϊ�����
			List dt = csvFile2List(fileRes);

			//�J�������`�F�b�N(1�s�ڂ̂�)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[���o�͂��������I������B
					throw new ApplicationException(
								"CSV�t�@�C�����R�����}�X�^�i��Ձj�̒�`�ƈقȂ�܂��B",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csv�t�@�C���i�[
			String tableName = "MASTER_SHINSAIN_KIBAN";
			String csvPath = kakuno(fileRes, tableName);
			
			//DB�̃G�N�X�|�[�g
			String footer = (koshinFlg ? "_KOSHIN_" : "_SHINKI_"); 
			String table_name = "MASTER_SHINSAIN,SHINSAININFO";
			String file_name = tableName + footer + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//�R�l�N�V�����擾
			connection = DatabaseUtil.getConnection();

			//�L�������̎擾
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSAIN);
			RuleInfoDao ruledao = new RuleInfoDao(userInfo);
			RuleInfo rinfo = ruledao.selectRuleInfo(connection, rulePk);
//			String yukoDate = new SimpleDateFormat("yyyyMMdd").format(rinfo.getYukoDate());

			//�捞�iDELETE �� INSERT�����j�i���Ƌ敪�����[4]�̂݁j
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection,
							 "MASTER_SHINSAIN", 
							 "WHERE JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_KIBAN);
			if(!koshinFlg){
				//�u�V�K�v�̂Ƃ��͐R�������e�[�u�����폜�i���Ƌ敪�����[4]�̂݁j
				dao.deleteMaster(connection, 
								 "SHINSAININFO",
								 "WHERE SUBSTR(SHINSAIN_ID,3,1) = " + IJigyoKubun.JIGYO_KUBUN_KIBAN);
			}
			
			int cnt = 0;												//����捞����
			ArrayList cd_list  = new ArrayList();						//�捞�f�[�^�̃L�[�i�[�z��(��Ӑ���`�F�b�N�p)
			ArrayList err_list = new ArrayList();						//�捞�݃G���[�s�i�[�z��
			int dtsize = dt.size();									//�捞�S����
			Map kikanMap = new HashMap();								//�@�փR�[�h�Ƌ@�֖��̂�Map
			DateUtil nowDateUtil = new DateUtil(new Date());			//�����_�ł̓������
			String strNendo = nowDateUtil.getYearYY();					//�����_�ł̔N�x�i����N�̉��Q���j 
			
			//���p�S�p�R���o�[�^
			HanZenConverter converter = new HanZenConverter();
			//DAO
			ShinsainInfoDao shinsainDao = new ShinsainInfoDao(userInfo);
			for(int i=1; i<=dtsize; i++){
				
				if((i-1) % 1000 == 0) {
					mtLog.info("�R�����}�X�^��荞��::" + (i-1) + "��");
				}
				
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[�Ƃ��ď������΂��B�i�s�����m�ۂ���j
					String msg = i+"�s�� �R�����}�X�^�i��Ձj�̃e�[�u����`�ƈ�v���܂���B";
					mtLog.warn(msg);
					//err_list.add(msg);				//�G���[���e���i�[ �� �ۗ��B�R�����g�A�E�g
					err_list.add(Integer.toString(i));	//�G���[�̍s�����m��
					
				}else{
					//���폈��
					//�G���[�t���O
					int err_flg = 0;
					
					//�}�X�^�f�[�^�̎擾
					int j = 0;
					String shinsain_no        = (String)line.get(j++);	//�R�����ԍ�
					String shimei_kanji_sei   = (String)line.get(j++);	//�����i���j
					String shimei_kanji_mei   = (String)line.get(j++);	//�����i���j
					String shimei_kana_sei    = (String)line.get(j++);	//�����t���K�i�i���j
					String shimei_kana_mei    = (String)line.get(j++);	//�����t���K�i�i���j
					String kikan_cd           = (String)line.get(j++);	//�@�փR�[�h
					String kikan_name         = (String)line.get(j++);	//�@�֖�
					String bukyoku_name       = (String)line.get(j++);	//���ǖ�
					String shokushu           = (String)line.get(j++);	//�E��
					
					//-----�R�����ԍ��`�F�b�N
					//���p�p�����`�F�b�N
					if(!this.checkHan(shinsain_no)){
						//���p�����Ŗ����ꍇ
						String msg = i+"�s�� �R�����ԍ��͔��p�p�����ł͂���܂���B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//���p�����̏ꍇ
						//�����`�F�b�N�i7���Ŗ����ꍇ�́A�擪��"0"��������j
						int cd_length = shinsain_no.length();
						if(cd_length == 1){
							shinsain_no = "000000" + shinsain_no;
						}else if(cd_length == 2){
							shinsain_no = "00000" + shinsain_no;
						}else if(cd_length == 3){
							shinsain_no = "0000" + shinsain_no;
						}else if(cd_length == 4){
							shinsain_no = "000" + shinsain_no;
						}else if(cd_length == 5){
							shinsain_no = "00" + shinsain_no;
						}else if(cd_length == 6){
							shinsain_no = "0" + shinsain_no;
						}else if(cd_length == 0 || cd_length > 7){
							String msg = i+"�s�� �R�����ԍ��̌������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						//KEY���ڂ̏d���`�F�b�N
						if(cd_list.contains(shinsain_no)){
							String msg = i+"�s�� �R�����ԍ��͏d�����Ă��܂��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							cd_list.add(shinsain_no);
						}
					}
					//-----�����i���j�`�F�b�N
					if(shimei_kanji_sei == null || shimei_kanji_sei.equals("")){
						String msg = i+"�s�� �����i�������|���j�͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(shimei_kanji_sei, 100)){
							String msg = i+"�s�� �����i�������|���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----�����i���j�`�F�b�N
					if(shimei_kanji_mei != null && !shimei_kanji_mei.equals("")){
						if(!this.checkLenB(shimei_kanji_mei, 100)){
							String msg = i+"�s�� �����i�������|���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----�����t���K�i�i���j�`�F�b�N
					if(shimei_kana_sei != null && !shimei_kana_sei.equals("")){
						shimei_kana_sei = converter.convert(shimei_kana_sei);	//���p�J�i�͑S�p�ɕϊ�
						if(!this.checkLenB(shimei_kana_sei, 100)){
							String msg = i+"�s�� �����i�t���K�i�|���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----�����t���K�i�i���j�`�F�b�N
					if(shimei_kana_mei != null && !shimei_kana_mei.equals("")){
						shimei_kana_mei = converter.convert(shimei_kana_mei);	//���p�J�i�͑S�p�ɕϊ�
						if(!this.checkLenB(shimei_kana_mei, 100)){
							String msg = i+"�s�� �����i�t���K�i�|���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----�@�փR�[�h�`�F�b�N
					//2005.11.21 iso ���p�`�F�b�N�ɕύX
//					//���l�`�F�b�N
//					if(!this.checkNum(kikan_cd)){
					if(!this.checkHan(kikan_cd)){
						//���p�����Ŗ����ꍇ
						String msg = i+"�s�� �@�փR�[�h�͔��p�����ł͂���܂���B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						//���p�����̏ꍇ
						//�����`�F�b�N�i5���Ŗ����ꍇ�́A�擪��"0"��������j
						int cd_length = kikan_cd.length();
						if(cd_length == 1){
							kikan_cd = "0000" + kikan_cd;
						}else if(cd_length == 2){
							kikan_cd = "000" + kikan_cd;
						}else if(cd_length == 3){
							kikan_cd = "00" + kikan_cd;
						}else if(cd_length == 4){
							kikan_cd = "0" + kikan_cd;
						//2005.11.21 iso �󏊑��@�փR�[�h��œo�^����悤�ύX
//						}else if(cd_length == 0 || cd_length > 5){
						}else if(cd_length > 5){
							String msg = i+"�s�� �@�փR�[�h�̌������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----�@�֖��i�a���j�`�F�b�N
					if(kikan_name == null || kikan_name.equals("")){
						//�ŏ��̂P�񂾂�DB�A�N�Z�X
						if(kikanMap.isEmpty()){
							setKikanNameList(connection, kikanMap);
						}
						//���Y�@�փR�[�h�̋@�֖��i�a���j���Q�b�g
						if(kikanMap.containsKey(kikan_cd)){
							kikan_name = (String)kikanMap.get(kikan_cd);	//�}�b�v�ɑ��݂���΂�����Z�b�g	
						}else{
							kikan_name = null;								//���݂��Ȃ��ꍇ��null�œo�^
						}
					}else{
						if(!this.checkLenB(kikan_name, 100)){
							String msg = i+"�s�� �@�֖��i�a���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}															
					//-----���ǖ��`�F�b�N
					if(bukyoku_name != null && !bukyoku_name.equals("")){
						if(!this.checkLenB(bukyoku_name, 100)){
							String msg = i+"�s�� ���ǖ��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}										
					//-----�E�햼�`�F�b�N
					if(shokushu != null && !shokushu.equals("")){
						if(!this.checkLenB(shokushu, 40)){
							String msg = i+"�s�� �E�햼�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}															

					
					//�G���[�����������ꍇ�̂ݓo�^����
					if(err_flg == 0){
						//�R����ID�̎擾�i�N�x�{�敪�i��Ղ�[4]�Œ�j�{�R�����ԍ��j
						String key = strNendo + IJigyoKubun.JIGYO_KUBUN_KIBAN + shinsain_no;
						String shinsain_id = key + CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
						
						//�R�������̐���
						ShinsainInfo info = new ShinsainInfo();
						info.setShinsainId(shinsain_id);					//�R����ID
						info.setPassword(RandomPwd.generate(rinfo));		//�p�X���[�h�̎擾
						info.setYukoDate(rinfo.getYukoDate());				//�L������
						info.setDelFlg("0");								//�폜�t���O�i0:�Œ�j
						info.setShinsainNo(shinsain_no);
						info.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);	//���Ƌ敪�i��Ղ�[4]��j
						info.setNameKanjiSei(shimei_kanji_sei);
						info.setNameKanjiMei(shimei_kanji_mei);
						info.setNameKanaSei(shimei_kana_sei);
						info.setNameKanaMei(shimei_kana_mei);
						info.setShozokuCd(kikan_cd);
						info.setShozokuName(kikan_name);
						info.setBukyokuName(bukyoku_name);
						info.setShokushuName(shokushu);
						info.setKoshinDate(nowDateUtil.getDateYYYYMMDD());	//�X�V����
						
						//---�V�K�̂Ƃ�---
						if(!koshinFlg){
							//DB�ɓo�^����i�R�����}�X�^�ƐR�������e�[�u���j
							shinsainDao.insertShinsainInfo(connection, info);
						//---�X�V�̂Ƃ�---	
						}else{
							//�X�V�ŁA�R�������e�[�u���Ɋ����f�[�^�������ꍇ
							if(shinsainDao.checkShinsainInfo(connection,
															 shinsain_no,
															 IJigyoKubun.JIGYO_KUBUN_KIBAN) == 0){
								//DB�ɓo�^����i�R�����}�X�^�ƐR�������e�[�u���j
								shinsainDao.insertShinsainInfo(connection, info);
							//�X�V�ŁA�R�������e�[�u���Ɋ����f�[�^������ꍇ
							}else{
								//DB�ɓo�^����i�R�����}�X�^�̂݁j
								shinsainDao.insertShinsainInfoOnlyMaster(connection, info);
							}
						}

						cnt++;	//��荞�݌������J�E���g
						
					}else{
						//�f�[�^�ɕs�������邽�߁A�o�^���s��Ȃ��i�s�����m�ۂ���j
						err_list.add(Integer.toString(i));
					}
				
				}	
				
			}

			mtLog.info("�R�����}�X�^��荞�ݑS������");
			
			//�}�X�^�Ǘ��e�[�u���̍X�V
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_SHINSAIN_KIBAN);
			mkInfo.setMasterName("�R�����}�X�^�i��Ռ������j");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg((koshinFlg ? "1" : "0"));	//�V�K=0, �X�V=1
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);						//�g�p���ĂȂ��H		
			mkInfo.setImportErrMsg(err_list);				//�G���[���i��ʂŎg�p�j
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;			
			
		} catch (IOException e) {
			throw new ApplicationException(
						"CSV�t�@�C���̓Ǎ����ɃG���[���������܂����B",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"�}�X�^�捞����DB�G���[���������܂����B",
						new ErrorInfo("errors.4001"),
						e);
		} finally {
			try {
				if (success) {
					//�R�~�b�g
					DatabaseUtil.commit(connection);
				} else {
					//���[���o�b�N
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�}�X�^�捞����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	
	/**
	 * ��՗p����U��f�[�^�捞.<br />
	 * ��՗p����U��f�[�^�����擾����B<br />
	 * <b>1.CSV�f�[�^�����X�g�`���ɕϊ�</b><br />
	 * �����œn���ꂽfileRes��List�`���֕ϊ�����BList�̗v�f�Ƃ��Ă����List�������A
	 * ���̗v�f��CSV���̈ꃌ�R�[�h���̏����i�[����B
	 * <br /><br />
	 * <b>2.�J�������`�F�b�N</b><br />
	 * 1.�ō쐬����List�̈�ڂ̗v�f��List�̗v�f�����A21�ł��邩�m�F����B����ȊO�̏ꍇ�́A��O��throw����B
	 * <br /><br />
	 * <b>3.csv�t�@�C���i�[</b><br />
	 * ���N���X��kakuno()���\�b�h���g�p����CSV�t�@�C�����T�[�o�֊i�[����B
	 * <br /><br />
	 * <b>4.���捞</b><br />
	 * 1.�ō쐬����CSV�t�@�C�����List����՗p����U��f�[�^��INSERT����B
	 * List�Ɋi�[����Ă���v�fList�ɂ͈ȉ��̏�񂪊i�[����Ă���B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>�v�f�ԍ�</td><td>�e�[�u����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>���ƃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>�N�x</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>�����ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>�����ۑ薼</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>������\�Ҏ����i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>������\�Ҏ����i���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>�@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>���ǃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>9</td><td>�E��R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>10</td><td>�זڃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>11</td><td>�����ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>12</td><td>�C�O����R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>13</td><td>�ŏI�N�x�O�N�x�\��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>14</td><td>���S���̗L��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>15</td><td>�R�����ԍ�1</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>16</td><td>�R�����ԍ�2</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>17</td><td>�R�����ԍ�3</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>18</td><td>�R�����ԍ�4</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>19</td><td>�R�����ԍ�5</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>20</td><td>�R�����ԍ�6</td></tr>
	 *	</table>
	 * ����ȊO�̏���CSV�ɂ͂Ȃ����߁Anull�Ƃ���B<br />
	 * 
	 * �e��̏��́A�����`�F�b�N�A�^�`�F�b�N����ш�ӃL�[����`�F�b�N���s���A��肪�����ꍇ�̓e�[�u�����X�V����B
	 * �X�V����e�[�u���͐\�����e�[�u���A�`�F�b�N���X�g���e�[�u���A�R�����ʃe�[�u���̂R�B
	 * �\�����e�[�u���͎��ƃR�[�h+�@�֔ԍ�+�זڃR�[�h+�����ԍ�+�����ԍ�����v����\���f�[�^�ɂ��ď�ID���X�V����B
	 * �`�F�b�N���X�g���e�[�u���͏����R�[�h�Ǝ���ID�ŏ�ID���擾���A�擾������ID��06�̏ꍇ��10��ύX����B
	 * �R�����ʃe�[�u���͎��Ƌ敪�ƃV�X�e����t�ԍ�����v���A�R�����ԍ���@000001�`@000006�̃f�[�^�ɂ��čX�V����B
	 * ��肪�������ꍇ�́A�G���[�����Ǘ�����List�Ɋi�[�����̂��A���̏��֏�����i�߂�B
	 * �Ȃ��A���ƃR�[�h�A�����ԍ��A�@�փR�[�h�A���ǃR�[�h�A�E�R�[�h�A�זڃR�[�h�A�C�O����R�[�h�A�R�����ԍ��́A
	 * �e�`�F�b�N�ƂƂ��ɍ�0���ߏ������s���B�܂��A�Y����ID���玖�Ɩ����A�@�փR�[�h����@�֖��̂Ƌ@�֖����̂��A
	 * ���ǃR�[�h���畔�ǖ��̂ƕ��ǖ����̂��A�Y�E�R�[�h����E�햼�̂ƐE�햼���̂��A�זڃR�[�h����זږ��ƕ��Ȗ�����ѕ��얼���A
	 * �C�O����R�[�h����C�O���얼�̂ƊC�O���얼���̂��A�R�����ԍ�����R�������i����-���A����-���A�J�i-���A�J�i-���j�A
	 * �@�֖��A���ǖ��A�E�����A���ꂼ��e�}�X�^����擾����B
	 * ���s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *	<b>�\�����</b>
		UPDATE 
				SHINSEIDATAKANRI
		SET
				JOKYO_ID = ?		
		WHERE
				SYSTEM_NO = ?
	
	 * 	<b>�`�F�b�N���X�g</b>	
		UPDATE 
				CHCKLISTINFO
		SET
				JOKYO_ID = ?
		WHERE
				SHOZOKU_CD = ?
		AND
				JIGYO_ID = ?
		AND
				JOKYO_ID = ?
				
	 *	<b>�R������</b>
		UPDATE 
				SHINSAKEKKA
		SET	
				SHINSAIN_NO = ? ,
				JIGYO_KUBUN = ? ,
				SHINSAIN_NAME_KANJI_SEI = ? ,
				SHINSAIN_NAME_KANJI_MEI = ? ,
				NAME_KANA_SEI = ? ,
				NAME_KANA_MEI = ? ,
				SHOZOKU_NAME = ? ,
				BUKYOKU_NAME = ? ,
				SHOKUSHU_NAME = ? ,
		WHERE 
				SHINSAIN_NO = ?
		AND 
				JIGYO_KUBUN = ?
		AND 
				SYSTEM_NO = ?
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<b>�\�����</b>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����ID</td><td>����N�x(yy)+CSV����擾�������ƃR�[�h+CSV����擾������</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>yyyyMMddHHmmssSSSSSS</td></tr>
	 *	</table>
	 *	<b>�`�F�b�N���X�g</b>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��ID</td><td>10</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����CD</td><td>CSV����擾���������R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����ID</td><td>����N�x(yy)+CSV����擾�������ƃR�[�h+CSV����擾������</td></tr>
	 *	</table>
	 *	<b>�R������</b>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>CSV����擾�����R�����ԍ�1�`6</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>"4"(���)</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�����|���j</td><td>�}�X�^����擾�����R�������i�����|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�����|���j</td><td>�}�X�^����擾�����R�������i�����|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>�}�X�^����擾�����R�������i�t���K�i�|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>�}�X�^����擾�����R�������i�t���K�i�|���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R���������@�֖�</td><td>�}�X�^����擾�����R���������@�֖�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�������ǖ�</td><td>�}�X�^����擾�����R�������ǖ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�R�����E��</td><td>�}�X�^����擾�����R�����E��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>yyyyMMddHHmmssSSSSSS�i�\�����̃V�X�e����t�ԍ��j</td></tr>
	 *	</table>
	 * <b>5.�}�X�^�Ǘ��}�X�^�X�V</b><br />
	 * �}�X�^�Ǘ��}�X�^���ȉ��̏��ōX�V����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�X�V���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^���</td><td>1</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^����</td><td>"��՗p����U��f�[�^"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�ݓ���</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����</td><td>Insert�������R�[�h��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�݃e�[�u����</td><td>"MASTER_SAIMOKU"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�K�E�X�V�t���O</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�捞�G���[���b�Z�[�W</td><td>���R�[�hINSERT�Ɏ��s�����ꍇ�A�G���[���b�Z�[�W</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSV�t�@�C���p�X</td><td>CSV�i�[��t�H���_�{CSV�t�@�C����</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>6.�}�X�^�Ǘ����ԋp</b><br />
	 * 5.�ōX�V���������Ăяo�����֕ԋp����B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param fileRes				�A�b�v���[�h�t�@�C�����\�[�X�B
	 * @return						�������ʏ��
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		�Ώۃf�[�^��������Ȃ��ꍇ�̗�O�B
	 */
	private MasterKanriInfo torikomiMasterWarifuriKekka(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException
	{
		int columnSize = 21;
		
		boolean success = false;
		Connection connection = null;

		try{
			//CSV�f�[�^�����X�g�`���ɕϊ�����
			List dt = csvFile2List(fileRes);

			//�J�������`�F�b�N(1�s�ڂ̂�)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[���o�͂��������I������B
					throw new ApplicationException(
								"CSV�t�@�C������Ռ���������U��f�[�^�̒�`�ƈقȂ�܂��B",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csv�t�@�C���i�[
			String tableName = "KIBAN_WARIFURIDATA";
			String csvPath = kakuno(fileRes, tableName);
			
			/* 2004/11/24
			 * �d�l�Ƃ���DB�G�N�X�|�[�g�_���v�����Ȃ��B
			 * �Y���������c��ł��邱�ƁA��ՈȊO�̐\���f�[�^���܂܂�Ă��܂����߁B
			 */
			//DB�̃G�N�X�|�[�g
			String table_name = "SHINSEIDATAKANRI,SHINSAKEKKA";
			//String file_name = tableName + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			//String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			//FileUtil.execCommand(cmd);
			
			//�R�l�N�V�����擾
			connection = DatabaseUtil.getConnection();

			int cnt = 0;												//����捞����
			ArrayList err_list = new ArrayList();						//�捞�݃G���[�s�i�[�z��
			int dtsize = dt.size();									//�捞�S����
			
			//�e�R�[�h�Ƃ���ɑΉ����鑮�����
//2006/4/13���g�p�ϐ����R�����g����
//			Map kikanMap    = new HashMap();							//�@�փR�[�h�Ƒ������
//			Map bukyokuMap  = new HashMap();							//���ǃR�[�h�Ƒ������
//			Map shokuMap    = new HashMap();							//�E��R�[�h�Ƒ������
//			Map saimokuMap  = new HashMap();							//�זڃR�[�h�Ƒ������
			Map shinsainMap = new HashMap();							//�R�����ԍ��Ƒ������
			Map jigyoMap    = new HashMap();							//����ID�Ƒ������
			
			//���Y�R�[�h�ɑΉ����閼�̂����݂��Ȃ������ꍇ�ɃZ�b�g���镶����
//			final String OTHER_NAME_VALUE    = "���̑�";
			//��荞�݃f�[�^�p�_�~�[�\����ID
			//final String DUMMY_SHINSEISHA_ID = "@torikomidata"; 	���g�p2005/9/27 
			
			//DAO
			MasterKanriInfoDao dao            = new MasterKanriInfoDao(userInfo);
			ShinseiDataInfoDao shinseiDao     = new ShinseiDataInfoDao(userInfo);
			ShinsaKekkaInfoDao shinsakekkaDao = new ShinsaKekkaInfoDao(userInfo);
			
			//2005/04/22�ǉ� ��������---------------------------------------------
			//�g�p����Dao���̒ǉ�
			
			CheckListInfoDao checkDao  = new CheckListInfoDao(userInfo);
			ArrayList cd_list          = new ArrayList();	

			//2005.12.05 iso �\���f�[�^���㏑�����Ȃ��悤�߂��B
			//2005.11.21 iso ��荞�݃f�[�^�Ő\�������㏑������悤�ύX
			ShinseiDataPk shinseiPk    = new ShinseiDataPk();
//			ShinseiDataInfo updateInfo	= new ShinseiDataInfo();
			
			CheckListSearchInfo checkInfo    = new CheckListSearchInfo();
			
			//�ǉ� �����܂�-------------------------------------------------------
			
			//---CSV���R�[�h�̌J��Ԃ�
			//csvRecordCount:
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[�Ƃ��ď������΂��B�i�s�����m�ۂ���j
					String msg = i+"�s�� ��Ռ���������U�茋�ʂ̒�`�ƈ�v���܂���B";
					mtLog.warn(msg);
					//err_list.add(msg);				//�G���[���e���i�[ �� �ۗ��B�R�����g�A�E�g
					err_list.add(Integer.toString(i));	//�G���[�̍s�����m��
					
				}else{
					//���폈��
					//�G���[�t���O
					int err_flg = 0;
					
					//CSV�f�[�^�̎擾
					int j = 0;
					String   jigyo_cd               = (String)line.get(j++);	//���ƃR�[�h
					String   jigyo_id               = null;						//����ID�iCSV�ɂ͖����j
//					String   jigyo_name             = null;						//���Ɩ��iCSV�ɂ͖����j
//					String   shinsa_kubun           = null;						//�R���敪�iCSV�ɂ͖����j
					String   nendo                  = (String)line.get(j++);	//�N�x
					String   kaisu                  = (String)line.get(j++);	//��
					String   seiri_no               = (String)line.get(j++);	//�����ԍ�
//					String   uketuke_no             = null;						//�\���ԍ��iCSV�ɂ͖����j
					String   kadai_name             = (String)line.get(j++);	//�����ۑ薼
					String   shimei_kanji_sei       = (String)line.get(j++);	//������\�Ҏ����i���j
					String   shimei_kanji_mei       = (String)line.get(j++);	//������\�Ҏ����i���j
					String   kikan_cd               = (String)line.get(j++);	//�@�փR�[�h
//					String   kikan_name             = null;						//�@�֖��iCSV�ɂ͖����j
//					String   kikan_name_ryaku       = null;						//�@�֖����iCSV�ɂ͖����j
					String   bukyoku_cd             = (String)line.get(j++);	//���ǃR�[�h
//					String   bukyoku_name           = null;						//���ǖ��iCSV�ɂ͖����j
//					String   bukyoku_name_ryaku     = null;						//���ǖ����iCSV�ɂ͖����j
					String   shoku_cd               = (String)line.get(j++);	//�E��R�[�h
//					String   shoku_name             = null;						//�E���iCSV�ɂ͖����j
//					String   shoku_name_ryaku       = null;						//�E�����iCSV�ɂ͖����j
					String   saimoku_cd             = (String)line.get(j++);	//�זڃR�[�h
//					String   saimoku_name           = null;						//�זږ��iCSV�ɂ͖����j
//					String   bunka_name             = null;						//���Ȗ��iCSV�ɂ͖����j
//					String   bunya_name             = null;						//���얼�iCSV�ɂ͖����j
					String   bunkatsu_no            = (String)line.get(j++);	//�����ԍ�
					String   kaigaibunya_cd         = (String)line.get(j++);	//�C�O����R�[�h
//					String   kaigaibunya_name       = null;						//�C�O���얼�iCSV�ɂ͖����j
//					String   kaigaibunya_name_ryaku = null;						//�C�O���얼���iCSV�ɂ͖����j
					String   zennendo_shinsei       = (String)line.get(j++);	//�ŏI�N�x�O�N�x�\��
					String   buntankin              = (String)line.get(j++);	//���S���̗L��
					String[] shinsain_no            = new String[6];			//�R�����ԍ�
					String[] shinsain_kanji_sei     = new String[6];			//�R�������i����-���j�iCSV�ɂ͖����j
					String[] shinsain_kanji_mei     = new String[6];			//�R�������i����-���j�iCSV�ɂ͖����j
					String[] shinsain_kana_sei      = new String[6];			//�R�������i�J�i-���j�iCSV�ɂ͖����j
					String[] shinsain_kana_mei      = new String[6];			//�R�������i�J�i-���j�iCSV�ɂ͖����j
					String[] shinsain_kikan_name    = new String[6];			//�R�����@�֖��iCSV�ɂ͖����j
					String[] shinsain_bukyoku_name  = new String[6];			//�R�������ǖ��iCSV�ɂ͖����j
					String[] shinsain_shoku_name    = new String[6];			//�R�����E���iCSV�ɂ͖����j
					
					//16�J��������6�͐R�����ԍ�
					for(int k=0; k<shinsain_no.length; k++){
						shinsain_no[k] = (String)line.get(j++);
					}
					
					//-----���ƃR�[�h�`�F�b�N
					if(jigyo_cd == null || jigyo_cd.length() == 0){
						String msg = i+"�s�� ���ƃR�[�h�͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{						
						//���l�`�F�b�N
						if(!this.checkNum(jigyo_cd)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� ���ƃR�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i5���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = jigyo_cd.length();
							if(cd_length == 1){
								jigyo_cd = "0000" + jigyo_cd;
							}else if(cd_length == 2){
								jigyo_cd = "000" + jigyo_cd;
							}else if(cd_length == 3){
								jigyo_cd = "00" + jigyo_cd;
							}else if(cd_length == 4){
								jigyo_cd = "0" + jigyo_cd;
							}
							//�ēx�������`�F�b�N���A��薳���Ȃ�ΐR���敪���擾�B
							if(cd_length == 0 || cd_length > 5){
								String msg = i+"�s�� ���ƃR�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}
					//-----�N�x�`�F�b�N
					if(nendo == null || nendo.length() == 0){
						String msg = i+"�s�� �N�x�͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{						
						//���l�`�F�b�N
						if(!this.checkNum(nendo)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �N�x�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i2���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = nendo.length();
							if(cd_length == 1){
								nendo = "0" + nendo;
							}else if(cd_length == 0 || cd_length > 2){
								String msg = i+"�s�� �N�x�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}		
					//-----�񐔃`�F�b�N
					if(kaisu == null || kaisu.length() == 0){
						String msg = i+"�s�� �񐔂͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{						
						//���l�`�F�b�N
						if(!this.checkNum(kaisu)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �񐔂͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							if(!this.checkLenB(kaisu, 1)){
								String msg = i+"�s�� �񐔂̒������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}
					//=====����ID�̑��݃`�F�b�N�i���킹�Ď��Ɩ����擾�j
					//����ID���쐬�i����N�x+���ƃR�[�h+�񐔁j
					if (err_flg != 1) {
						jigyo_id = DateUtil.changeWareki2Seireki(nendo) + jigyo_cd + kaisu;	
						//�ŏ��̂P�񂾂�DB�A�N�Z�X
						if(jigyoMap.isEmpty()){
							setJigyoInfoList(connection, jigyoMap);
						}
						//���Y����ID�̎��Ə����Q�b�g
						if(!jigyoMap.containsKey(jigyo_id)){
						//�}�b�v�ɑ��݂���΂�����Z�b�g
//							Map recordMap = (Map)jigyoMap.get(jigyo_id);	
//							jigyo_name    = (String)recordMap.get("JIGYO_NAME");
//						}else{
						//���݂��Ȃ��ꍇ�̓G���[
						String msg = i+"�s�� ���Y����ID�͑��݂��܂���B����ID="+jigyo_id;
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
						}
					}
					//-----�����ԍ��`�F�b�N
					if(seiri_no == null || seiri_no.length() == 0){
						String msg = i+"�s�� �����ԍ��͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{						
						//���l�`�F�b�N
						if(!this.checkNum(seiri_no)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �����ԍ��͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i4���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = seiri_no.length();
							if(cd_length == 1){
								seiri_no = "000" + seiri_no;
							}else if(cd_length == 2){
								seiri_no = "00" + seiri_no;
							}else if(cd_length == 3){
								seiri_no = "0" + seiri_no;
							}else if(cd_length == 0 || cd_length > 4){
								String msg = i+"�s�� �����ԍ��̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}		
					//-----�����ۑ薼�`�F�b�N
					if(kadai_name != null && kadai_name.length() != 0){
						if(!this.checkLenB(kadai_name, 80)){
							String msg = i+"�s�� �����ۑ薼�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}							
					//-----������\�Ҏ����i���j�`�F�b�N
					if(shimei_kanji_sei != null && shimei_kanji_sei.length() != 0){
						if(!this.checkLenB(shimei_kanji_sei, 100)){
							String msg = i+"�s�� ������\�Ҏ����i�������|���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}					
					//-----������\�Ҏ����i���j�`�F�b�N
					if(shimei_kanji_mei != null && shimei_kanji_mei.length() != 0){
						if(!this.checkLenB(shimei_kanji_mei, 100)){
							String msg = i+"�s�� ������\�Ҏ����i�������|���j�̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//-----�@�փR�[�h�`�F�b�N
					if(kikan_cd == null || kikan_cd.length() == 0){
						String msg = i+"�s�� �@�փR�[�h�͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{						
						//���l�`�F�b�N
						if(!this.checkNum(kikan_cd)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �@�փR�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i5���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = kikan_cd.length();
							if(cd_length == 1){
								kikan_cd = "0000" + kikan_cd;
							}else if(cd_length == 2){
								kikan_cd = "000" + kikan_cd;
							}else if(cd_length == 3){
								kikan_cd = "00" + kikan_cd;
							}else if(cd_length == 4){
								kikan_cd = "0" + kikan_cd;
							}else if(cd_length == 0 || cd_length > 5){
								String msg = i+"�s�� �@�փR�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
						//=====�@�֖��A�@�֖������擾
						//�ŏ��̂P�񂾂�DB�A�N�Z�X
//						if(kikanMap.isEmpty()){
//							setKikanInfoList(connection, kikanMap);
//						}
						//���Y�@�փR�[�h�̋@�֏����Q�b�g
//						if(kikanMap.containsKey(kikan_cd)){
							//�}�b�v�ɑ��݂���΂�����Z�b�g	
//							Map recordMap    = (Map)kikanMap.get(kikan_cd);
//							kikan_name       = (String)recordMap.get("SHOZOKU_NAME_KANJI");
//							kikan_name_ryaku = (String)recordMap.get("SHOZOKU_RYAKUSHO");
//						}else{
							//���݂��Ȃ��ꍇ�́u���̑��v�œo�^
//							kikan_name       = OTHER_NAME_VALUE;
//							kikan_name_ryaku = OTHER_NAME_VALUE;
//						}
					}
					//-----���ǃR�[�h�`�F�b�N
					if(bukyoku_cd != null && bukyoku_cd.length() != 0){
						//���l�`�F�b�N
						if(!this.checkNum(bukyoku_cd)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� ���ǃR�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i3���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = bukyoku_cd.length();
							if(cd_length == 1){
								bukyoku_cd = "00" + bukyoku_cd;
							}else if(cd_length == 2){
								bukyoku_cd = "0" + bukyoku_cd;
							}else if(cd_length == 0 || cd_length > 3){
								String msg = i+"�s�� ���ǃR�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
						//=====���ǖ��A���ǖ������擾
						//�ŏ��̂P�񂾂�DB�A�N�Z�X
//						if(bukyokuMap.isEmpty()){
//							setBukyokuInfoList(connection, bukyokuMap);
//						}
						//���Y���ǃR�[�h�̕��Ǐ����Q�b�g
//						if(bukyokuMap.containsKey(bukyoku_cd)){
							//�}�b�v�ɑ��݂���΂�����Z�b�g	
//							Map recordMap      = (Map)bukyokuMap.get(bukyoku_cd);
//							bukyoku_name       = (String)recordMap.get("BUKA_NAME");
//							bukyoku_name_ryaku = (String)recordMap.get("BUKA_RYAKUSHO");
//						}else{
							//���݂��Ȃ��ꍇ�́u���̑��v�œo�^
//							bukyoku_name       = OTHER_NAME_VALUE;
//							bukyoku_name_ryaku = OTHER_NAME_VALUE;
//						}				
					}
					//-----�E�R�[�h�`�F�b�N
					if(shoku_cd != null && shoku_cd.length() != 0){
						//���l�`�F�b�N
						if(!this.checkNum(shoku_cd)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �E�R�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i2���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = shoku_cd.length();
							if(cd_length == 1){
								shoku_cd = "0" + shoku_cd;
							}else if(cd_length == 0 || cd_length > 2){
								String msg = i+"�s�� �E�R�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
						//=====�E���A�E�������擾
						//�ŏ��̂P�񂾂�DB�A�N�Z�X
//						if(shokuMap.isEmpty()){
//							setShokuInfoList(connection, shokuMap);
//						}
						//���Y�E�R�[�h�̐E�����Q�b�g
//						if(shokuMap.containsKey(shoku_cd)){
							//�}�b�v�ɑ��݂���΂�����Z�b�g	
//							Map recordMap    = (Map)shokuMap.get(shoku_cd);
//							shoku_name       = (String)recordMap.get("SHOKUSHU_NAME");
//							shoku_name_ryaku = (String)recordMap.get("SHOKUSHU_NAME_RYAKU");
//						}else{
							//���݂��Ȃ��ꍇ�́u���̑��v�œo�^
//							shoku_name       = OTHER_NAME_VALUE;
//							shoku_name_ryaku = OTHER_NAME_VALUE;
//						}
					}										
					//-----�זڃR�[�h�`�F�b�N
					if(saimoku_cd != null && saimoku_cd.length() != 0){
						//���l�`�F�b�N
						if(!this.checkNum(saimoku_cd)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �זڃR�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i4���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = saimoku_cd.length();
							if(cd_length == 1){
								saimoku_cd = "000" + saimoku_cd;
							}else if(cd_length == 2){
								saimoku_cd = "00" + saimoku_cd;
							}else if(cd_length == 3){
								saimoku_cd = "0" + saimoku_cd;
							}else if(cd_length == 0 || cd_length > 4){
								String msg = i+"�s�� �זڃR�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
						//=====�זږ��A���Ȗ��A���얼���擾
						//�ŏ��̂P�񂾂�DB�A�N�Z�X
//						if(saimokuMap.isEmpty()){
//							setSaimokuInfoList(connection, saimokuMap);
//						}
						//���Y�זڃR�[�h�̍זڏ����Q�b�g
//						if(saimokuMap.containsKey(saimoku_cd)){
							//�}�b�v�ɑ��݂���΂�����Z�b�g	
//							Map recordMap    = (Map)saimokuMap.get(saimoku_cd);
//							saimoku_name     = (String)recordMap.get("SAIMOKU_NAME");
//							bunka_name       = (String)recordMap.get("BUNKA_NAME");
//							bunya_name       = (String)recordMap.get("BUNYA_NAME");
//						}else{
							//���݂��Ȃ��ꍇ�́u���̑��v�œo�^
//							saimoku_name     = OTHER_NAME_VALUE;
//							bunka_name       = OTHER_NAME_VALUE;
//							bunya_name       = OTHER_NAME_VALUE;
//						}
					}
					//-----�����ԍ��`�F�b�N
					if(bunkatsu_no != null && bunkatsu_no.length() != 0){
						if(!this.checkLenB(bunkatsu_no, 1)){
							String msg = i+"�s�� �����ԍ��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					//2005/04/22 �ǉ� ��������--------------------------------------------
					//���R �����ԍ���null�̏ꍇ��-�ɂ���
					else {
						bunkatsu_no = "-";
					}
					//�ǉ� �����܂�-------------------------------------------------------
											
					//-----�C�O����R�[�h�`�F�b�N
					if(kaigaibunya_cd != null && kaigaibunya_cd.length() != 0){
						//���l�`�F�b�N
						if(!this.checkNum(kaigaibunya_cd)){
							//���p�����Ŗ����ꍇ
							String msg = i+"�s�� �C�O����R�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}else{
							//���p�����̏ꍇ
							//�����`�F�b�N�i2���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = kaigaibunya_cd.length();
							if(cd_length == 1){
								kaigaibunya_cd = "0" + kaigaibunya_cd;
							}else if(cd_length == 0 || cd_length > 2){
								String msg = i+"�s�� �C�O����R�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
						//=====�C�O���얼�A�C�O���얼�����擾
						//�ŏ��̂P�񂾂�DB�A�N�Z�X
//						if(kaigaiMap.isEmpty()){
//							setKaigaibunyaInfoList(connection, kaigaiMap);
//						}
						//���Y�זڃR�[�h�̍זڏ����Q�b�g
//						if(kaigaiMap.containsKey(kaigaibunya_cd)){
							//�}�b�v�ɑ��݂���΂�����Z�b�g	
//							Map recordMap          = (Map)kaigaiMap.get(kaigaibunya_cd);
//							kaigaibunya_name       = (String)recordMap.get("KAIGAIBUNYA_NAME");
//							kaigaibunya_name_ryaku = (String)recordMap.get("KAIGAIBUNYA_NAME_RYAKU");
//						}else{
							//���݂��Ȃ��ꍇ�́u���̑��v�œo�^
//							kaigaibunya_name       = OTHER_NAME_VALUE;
//							kaigaibunya_name_ryaku = OTHER_NAME_VALUE;
//						}
					}
					//-----�ŏI�N�x�O�N�x�̐\���`�F�b�N
					if(zennendo_shinsei != null && zennendo_shinsei.length() != 0){
						if(!this.checkLenB(zennendo_shinsei, 1)){
							String msg = i+"�s�� �ŏI�N�x�O�N�x�̐\���̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}							
					//----���S���̗L���`�F�b�N
					if(buntankin != null && buntankin.length() != 0){
						if(!this.checkLenB(buntankin, 1)){
							String msg = i+"�s�� ���S���̗L���̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}

					//-----�R�����ԍ��`�F�b�N�i�P�`�U�j
					for(int k=0; k<shinsain_no.length; k++){
						//�ŏ��̂P�����͕K�{
						if( (k==0) && (shinsain_no[k] == null || shinsain_no[k].length() == 0) ){
							String msg = i+"�s�� �R�����ԍ�"+(k+1)+"�͕K�{���ڂł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						if(shinsain_no[k] != null && shinsain_no[k].length() != 0){
							//���l�`�F�b�N
							if(!this.checkHan(shinsain_no[k])){
								//���p�p�����Ŗ����ꍇ
								String msg = i+"�s�� �R�����ԍ�"+(k+1)+"�͔��p�p�����ł͂���܂���B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}else if(shinsain_no[k].length() > 7){
								String msg = i+"�s�� �R�����ԍ�"+(k+1)+"�̌������s���ł��B";
								mtLog.warn(msg);
								err_flg = 1;
							}else{
								//���p�����̏ꍇ
								//�����`�F�b�N�i7���Ŗ����ꍇ�́A�擪��"0"��������j
								int cd_length = shinsain_no[k].length();
								if(cd_length == 1){
									shinsain_no[k] = "000000" + shinsain_no[k];
								}else if(cd_length == 2){
									shinsain_no[k] = "00000" + shinsain_no[k];
								}else if(cd_length == 3){
									shinsain_no[k] = "0000" + shinsain_no[k];
								}else if(cd_length == 4){
									shinsain_no[k] = "000" + shinsain_no[k];
								}else if(cd_length == 5){
									shinsain_no[k] = "00" + shinsain_no[k];
								}else if(cd_length == 6){
									shinsain_no[k] = "0" + shinsain_no[k];
									//err_list.add(msg);
								}
								
							//=====�R�������i����-���A����-���A�J�i-���A�J�i-���j�A�@�֖��A���ǖ��A�E�����擾
							//�ŏ��̂P�񂾂�DB�A�N�Z�X
							if(shinsainMap.isEmpty()){
								setShinsainInfoList(connection, shinsainMap);
							}
							//���Y�R�����ԍ��̐R���������Q�b�g
							if(shinsainMap.containsKey(shinsain_no[k])){
								//�}�b�v�ɑ��݂���΂�����Z�b�g	
								Map recordMap            = (Map)shinsainMap.get(shinsain_no[k]);
								shinsain_kanji_sei[k]    = (String)recordMap.get("NAME_KANJI_SEI");
								shinsain_kanji_mei[k]    = (String)recordMap.get("NAME_KANJI_MEI");
								shinsain_kana_sei[k]     = (String)recordMap.get("NAME_KANA_SEI");
								shinsain_kana_mei[k]     = (String)recordMap.get("NAME_KANA_MEI");
								shinsain_kikan_name[k]   = (String)recordMap.get("SHOZOKU_NAME");
								shinsain_bukyoku_name[k] = (String)recordMap.get("BUKYOKU_NAME");
								shinsain_shoku_name[k]   = (String)recordMap.get("SHOKUSHU_NAME");
							}else{
								//���݂��Ȃ��ꍇ�̓G���[
								String msg = i+"�s�� �R�����ԍ�"+(k+1)+"�̐R�����͑��݂��܂���B�R�����ԍ�="+shinsain_no[k];
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
								}
							}
						}else{
							//�R�����ԍ���null�܂��͋�̏ꍇ�̓_�~�[�R�����ԍ����Z�b�g����
							shinsain_no[k] = "@00000"+ new Integer(k+1).toString();
						}
					}
					
					//�d���f�[�^�i����ID�{�\���ԍ��i�@�փR�[�h+�����ԍ��j�j���`�F�b�N����
					
					//2005/04/22 �폜 ��������-------------------------------------------
					//���R �d���`�F�b�N���@�ύX�̂���
					
					/*
 					uketuke_no = kikan_cd + "-" + seiri_no;
					if(shinseiDao.countShinseiData(connection, jigyo_id, uketuke_no) != 0){
						//���݂��Ȃ��ꍇ�̓G���[
						String msg = i+"�s�� ���Y���R�[�h�͏d�����Ă��܂��B����ID="+jigyo_id + ",�\���ԍ�="+uketuke_no;
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}
					*/					
					//�폜 �����܂�------------------------------------------------------
					//2005/04/22 �ǉ� ��������-------------------------------------------
					//���R �d���`�F�b�N���@�ύX�̂���
					String key = jigyo_id + kikan_cd + saimoku_cd +bunkatsu_no+seiri_no;
					if(cd_list.contains(key)){
						String msg = i+"�s�� Key����(���ƃR�[�h,�N�x,��,������\�ҏ����@�փR�[�h,�זڔԍ�,�����ԍ�,�����ԍ�)���d�����Ă��܂��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						cd_list.add(key);
					}
					
					String uketukeNo = kikan_cd + "-"+seiri_no;
//					String jigyoId =  DateUtil.changeWareki2Seireki(nendo) + jigyo_cd + kaisu;
					
					ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();
					shinseiInfo.setUketukeNo(uketukeNo);
					shinseiInfo.setJigyoId(jigyo_id);
					//shinseiInfo.getDaihyouInfo().setShozokuCd(kikan_cd);
					shinseiInfo.getKadaiInfo().setBunkaSaimokuCd(saimoku_cd);
					shinseiInfo.getKadaiInfo().setBunkatsuNo(bunkatsu_no);
					String systemNo = null;
					try{
						systemNo = shinseiDao.selectShinseiTorikomiData(connection, shinseiInfo);
					}catch(NoDataFoundException e){
						String msg = i+"�s�� �Y������\����񂪑��݂��܂���B";
						mtLog.warn(msg);
						err_flg = 1;					
					}

					//2005.12.05 iso �\���f�[�^���㏑�����Ȃ��悤�߂��B
					//2005.11.21 iso ��荞�݃f�[�^�Ő\�������㏑������悤�ύX
					shinseiPk.setSystemNo(systemNo);
//					updateInfo.setSystemNo(systemNo);
//					updateInfo.setKaigaibunyaCd(kaigaibunya_cd);
//					updateInfo.setShinseiFlgNo(zennendo_shinsei);
//					updateInfo.setBuntankinFlg(buntankin);
//					
//					KadaiInfo kadaiInfo = new KadaiInfo();
//					kadaiInfo.setKadaiNameKanji(kadai_name);
//					updateInfo.setKadaiInfo(kadaiInfo);
//					
//					DaihyouInfo daihyouInfo = new DaihyouInfo();
//					daihyouInfo.setNameKanjiSei(shimei_kanji_sei);
//					daihyouInfo.setNameKanjiMei(shimei_kanji_mei);
//					daihyouInfo.setBukyokuCd(bukyoku_cd);
//					daihyouInfo.setShokushuCd(shoku_cd);
//					updateInfo.setDaihyouInfo(daihyouInfo);
					
					checkInfo.setJigyoId(jigyo_id);
					checkInfo.setShozokuCd(kikan_cd);
								
					//�G���[�����������ꍇ�̂ݓo�^����
					if(err_flg == 0){
						
						//2005/04/22 �폜 ��������-------------------------------------------
						//���R ���荞�݌��ʏ��̎�荞�ݏ�����INSERT����UPDATE�ɕύX���ꂽ���ߍ폜
					
						/*
						Date now = new Date();										//���ݎ���
						String system_no = null;									//�V�X�e����t�ԍ��͓o�^���O�ɍ̔�
						
						//===== �\���f�[�^��� =====
						ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();
						shinseiInfo.setUketukeNo(uketuke_no);
						shinseiInfo.setJigyoId(jigyo_id);
						shinseiInfo.setNendo(nendo);
						shinseiInfo.setKaisu(kaisu);
						shinseiInfo.setJigyoName(jigyo_name);
						shinseiInfo.setShinseishaId(DUMMY_SHINSEISHA_ID);			//�_�~�[�\����ID
						shinseiInfo.setSakuseiDate(now);							//���ݎ���
						shinseiInfo.setShoninDate(now);								//���ݎ���
						shinseiInfo.setJyuriDate(now);								//���ݎ���
						//---�\���ҁi������\�ҁj
						DaihyouInfo daihyouInfo = shinseiInfo.getDaihyouInfo();
						daihyouInfo.setNameKanjiSei(shimei_kanji_sei);
						daihyouInfo.setNameKanjiMei(shimei_kanji_mei);
						daihyouInfo.setShozokuCd(kikan_cd);
						daihyouInfo.setShozokuName(kikan_name);
						daihyouInfo.setShozokuNameRyaku(kikan_name_ryaku);
						daihyouInfo.setBukyokuCd(bukyoku_cd);
						daihyouInfo.setBukyokuName(bukyoku_name);
						daihyouInfo.setBukyokuNameRyaku(bukyoku_name_ryaku);
						daihyouInfo.setShokushuCd(shoku_cd);
						daihyouInfo.setShokushuNameKanji(shoku_name);
						daihyouInfo.setShokushuNameRyaku(shoku_name_ryaku);
						//---�����ۑ�
						KadaiInfo kadaiInfo = shinseiInfo.getKadaiInfo();
						kadaiInfo.setKadaiNameKanji(kadai_name);
						kadaiInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);		//��ՌŒ�
						kadaiInfo.setShinsaKubun(shinsa_kubun);
						kadaiInfo.setBunkatsuNo(bunkatsu_no);
						kadaiInfo.setBunkaSaimokuCd(saimoku_cd);
						kadaiInfo.setBunya(bunya_name);
						kadaiInfo.setBunka(bunka_name);
						kadaiInfo.setSaimokuName(saimoku_name);
						//---��{���i���Ձj
						shinseiInfo.setBuntankinFlg(buntankin);
						shinseiInfo.setShinseiFlgNo(zennendo_shinsei);
						shinseiInfo.setKaigaibunyaCd(kaigaibunya_cd);
						shinseiInfo.setKaigaibunyaName(kaigaibunya_name);
						shinseiInfo.setKaigaibunyaNameRyaku(kaigaibunya_name_ryaku);						
						//---��{���i�㔼�j
						shinseiInfo.setJuriKekka(ShinseiMaintenance.FLAG_JURI_KEKKA_JURI);
						shinseiInfo.setJokyoId(StatusCode.STATUS_1st_SHINSATYU);	//�\����[�R����]
						shinseiInfo.setSaishinseiFlg(StatusCode.SAISHINSEI_FLG_DEFAULT);
						shinseiInfo.setDelFlg(IShinseiMaintenance.FLAG_APPLICATION_NOT_DELETE);
						
						//�\���f�[�^��DB�ɓo�^����i������L�[��񂪏d�����Ă����ꍇ�͍Ď��s�j
						final int TRY_REGIST_COUNT = 5;		//���s��
						for(int x=0; x<TRY_REGIST_COUNT; x++){
							try{
								system_no = ShinseiMaintenance.getSystemNumber();	//�V�X�e����t�ԍ�
								shinseiInfo.setSystemNo(system_no);
								shinseiDao.insertShinseiDataInfo(connection, shinseiInfo);
								break;	//����ɓo�^�ł����ꍇ�̓��[�v�𔲂���
							}catch(DuplicateKeyException dke){
								if(x == TRY_REGIST_COUNT){
									//���s�񐔌J��Ԃ��Ă��d�������ꍇ�̓G���[�Ƃ���
									String msg = i+"�s�� �̔ԏ�����"+TRY_REGIST_COUNT+"�񎎍s���܂������A"
										          +"�V�X�e����t�ԍ����d�����܂����BsystemNo="+system_no
										          +" -> ���Y���R�[�h�͏������X�L�b�v���܂��B";
									mtLog.warn(msg);
									//err_list.add(msg);
									err_list.add(Integer.toString(i));	//�G���[�̍s�����m�ۂ���
									continue csvRecordCount;			//���̃��R�[�h�փX�L�b�v����
								}else{
									//�ꉞ���O�ɂ͏o�͂���
									String msg = i+"�s�� �V�X�e����t�ԍ����d�����܂����BsystemNo="+system_no
									              +" -> �Ď��s���܂��B("+(x+1)+"���)";
									mtLog.warn(msg);
									continue;							//�Ď��s	
								}
							}
						}
						*/						
						//�폜 �����܂�-----------------------------------------------------
						//2005/04/22 �ǉ� ��������------------------------------------------
						//���R �\�����̍X�V������ǉ�

						//2006.11.15 iso �󗝑O�̃`�F�b�N���X�g����̏ꍇ�A�\���f�[�^�̂ݍX�V�����o�O�C���B
						//�A�b�v�f�[�g�̓`�F�b�N���X�g�̌�Ɉړ�����B
//						//2005.12.05 iso �\���f�[�^���㏑�����Ȃ��悤�߂��B
//						//2005.11.21 iso �}�X�^���Ő\���f�[�^���㏑������悤�ύX
//						//�����������񂪑����̂ŁA�ʏ�̐\���f�[�^�X�V���\�b�h���g���ׂ��H
////						//�\�����̏�ID��10�ɕύX
//						shinseiDao.updateStatus(connection, shinseiPk, StatusCode.STATUS_1st_SHINSATYU);
////						shinseiDao.updateTorikomiShinseiDataInfo(connection, updateInfo, StatusCode.STATUS_1st_SHINSATYU);
						String jigyoKbn = "";
//2006/04/28 �ǉ���������                        
//						if (IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyo_cd)){
//							jigyoKbn = IJigyoKubun.JIGYO_KUBUN_WAKATESTART;
//						}else{
//							jigyoKbn = IJigyoKubun.JIGYO_KUBUN_KIBAN;
//						}
            
						if (IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyo_cd)){
                            jigyoKbn = IJigyoKubun.JIGYO_KUBUN_WAKATESTART;
                        } else if (IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyo_cd) 
                                 ||IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyo_cd) 
                                 ||IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(jigyo_cd)	//���S��ǉ�2007/5/8
                                 ||IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyo_cd))
                        {
                            jigyoKbn = IJigyoKubun.JIGYO_KUBUN_KIBAN;
                        } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyo_cd) 
                                 ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyo_cd)
                                 ||IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyo_cd))
                        {
                            jigyoKbn = IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI;
                        }
//�c�@�ǉ������܂�                               
						checkInfo.setJigyoKubun(jigyoKbn);
//2006/10/26 �c�@�C����������                           
//��Ղr�A��Ղ`(��ʁE�C�O)�A��Ղa(��ʁE�C�O)�A��茤��S�̏ꍇ�A�`�F�b�N���X�g�����`�F�b�N���Ȃ�
//�����F�Y����ڂ̓`�F�b�N���X�g���ɓo�^���Ȃ��Ȃ邩��
						//�`�F�b�N���X�g�̃`�F�b�N
                        String jokyoId = "";
                        if (!(IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyo_cd)
                            || IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyo_cd)
                            || IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyo_cd)
                            || IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyo_cd) 
                            || IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyo_cd)
                            || IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(jigyo_cd)	//���S��ǉ�2007/5/8
                            || IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyo_cd)	//���i��i�N�����񉞕�̎��s�j2007/5/22
                            || IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyo_cd)		//���X�^�[�g 2007/5/23
                        )) {
                            // String jokyoId = checkDao.checkJokyoId(connection, checkInfo, true);
                            jokyoId = checkDao.checkJokyoId(connection, checkInfo, true);
                            //if (jokyoId != null && jokyoId.equals(StatusCode.STATUS_GAKUSIN_JYURI)) {
                            if (jokyoId != null && StatusCode.STATUS_GAKUSIN_JYURI.equals(jokyoId)) {
                                // �`�F�b�N���X�g�ɏ����R�[�h�Ǝ���ID�̈ꏏ�ȃf�[�^������A��ID��6�̏ꍇ�͏�ID��10�ɂ���
                                checkInfo.setChangeJokyoId(StatusCode.STATUS_1st_SHINSATYU);
                                checkInfo.setJokyoId(jokyoId);
                                checkDao.updateCheckListInfo(connection, checkInfo, false);
                                // 2006/05/22 �ǉ���������
                            }
                            else if (StatusCode.STATUS_1st_SHINSATYU.equals(jokyoId)
                                   || StatusCode.STATUS_1st_SHINSA_KANRYO.equals(jokyoId)
                                   || StatusCode.STATUS_2nd_SHINSA_KANRYO.equals(jokyoId)) {
                                // �����Ȃ�
                            }
                            else {
                                err_list.add(Integer.toString(i));
//2006/05/23 �C��Start By�@Sai��������                            
                                // String msg = i + "�s�� �\�����X�g�̐\���󋵂��󗝍ς݂ł͂���܂���B";
                                String msg = i + "�s�� �`�F�b�N���X�g�̉���󋵂��󗝍ς݂ł͂���܂���B";
//2006/05/23 �C�� End�@By�@Sai
                                mtLog.warn(msg);
                                continue;
                            }
                        }
//2006/10/26�@�c�@�C�������܂�                           
//�c�@�ǉ��܂�

						//2006.11.15 iso �󗝑O�̃`�F�b�N���X�g����̏ꍇ�A�\���f�[�^�̂ݍX�V�����o�O�C���B
                        //�`�F�b�N���X�g�̏����O�ɂ������̂��ړ�
						shinseiDao.updateStatus(connection, shinseiPk, StatusCode.STATUS_1st_SHINSATYU);
						
						//�ǉ� �����܂�-----------------------------------------------------
						
						
						//===== �R�����ʏ�� =====
						for(int k=0; k<shinsain_no.length; k++){
							ShinsainInfo shinsainInfo = new ShinsainInfo();
							
							shinsainInfo.setShinsainNo(shinsain_no[k]);
							//shinsainInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
							shinsainInfo.setJigyoKubun(jigyoKbn);
							shinsainInfo.setNameKanjiSei(shinsain_kanji_sei[k]);
							shinsainInfo.setNameKanjiMei(shinsain_kanji_mei[k]);
							shinsainInfo.setNameKanaSei(shinsain_kana_sei[k]);
							shinsainInfo.setNameKanaMei(shinsain_kana_mei[k]);
							shinsainInfo.setShozokuName(shinsain_kikan_name[k]);
							shinsainInfo.setBukyokuName(shinsain_bukyoku_name[k]);
							shinsainInfo.setShokushuName(shinsain_shoku_name[k]);
						/*	ShinsaKekkaInfo kekkaInfo = new ShinsaKekkaInfo();
							kekkaInfo = new ShinsaKekkaInfo();
							kekkaInfo.setSystemNo(system_no);
							kekkaInfo.setUketukeNo(uketuke_no);
							kekkaInfo.setShinsainNo(shinsain_no[k]);		
							kekkaInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);		//��ՌŒ�
							kekkaInfo.setSeqNo(Integer.toString(k+1));
							kekkaInfo.setShinsaKubun(shinsa_kubun);
							kekkaInfo.setShinsainNameKanjiSei(shinsain_kanji_sei[k]);
							kekkaInfo.setShinsainNameKanjiMei(shinsain_kanji_mei[k]);
							kekkaInfo.setNameKanaSei(shinsain_kana_sei[k]);
							kekkaInfo.setNameKanaMei(shinsain_kana_mei[k]);
							kekkaInfo.setShozokuName(shinsain_kikan_name[k]);
							kekkaInfo.setBukyokuName(shinsain_bukyoku_name[k]);
							kekkaInfo.setShokushuName(shinsain_shoku_name[k]);
							//kekkaInfo.setJigyoId();
							kekkaInfo.setJigyoName(jigyo_name);
							kekkaInfo.setBunkaSaimokuCd(saimoku_cd);
							//kekkaInfo.setShinsaJokyo("0");								//[0]�Œ�
						*/	//DB�ɓo�^����
							//shinsakekkaDao.insertShinsaKekkaInfo(connection, kekkaInfo);
							shinsakekkaDao.updateShinsainInfo(connection,shinsainInfo,"0",jigyo_id, systemNo, k+1);
						}
						
						//2005.12.14 iso 
						//�{�Ԋ��ł́A�����W�b�N�Ŏ󗝂����f�[�^(����U��6��)���Ȃ��B
						//�p�t�H�[�}���X�ɉe������̂ŁA�ȉ��̏����͍s��Ȃ��B
//						//2005/11/02 �ǉ� ��������------------------------------------------
//						//���R �R�����ʂ��U���i�_�~�[�f�[�^���܂ށj�����o�^����Ă��Ȃ��ꍇ�A����ɂU���ǉ�����K�v�����邽��
//						int shinsainCnt = shinseiDao.countShinsaKekkaData(connection, systemNo, IJigyoKubun.JIGYO_KUBUN_KIBAN);
//						
//						if(shinsainCnt == 6){
//							ShinsaKekkaInfo kekkaInfo = new ShinsaKekkaInfo();
//							kekkaInfo.setSystemNo(systemNo);							//�V�X�e���ԍ�
//							kekkaInfo.setUketukeNo(uketukeNo);							//�\���ԍ�
//							kekkaInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);		//���Ƌ敪
//							kekkaInfo.setShinsaKubun(shinsa_kubun);						//�R���敪
//							kekkaInfo.setJigyoId(jigyo_id);								//����ID
//							kekkaInfo.setJigyoName(jigyo_name);							//���Ɩ�
//							kekkaInfo.setBunkaSaimokuCd(saimoku_cd);					//�זڔԍ�
//							kekkaInfo.setShinsaJokyo("0");								//�R����
//							
//							//�_�~�[�f�[�^��ǉ�(�V�[�P���X�ԍ� = 7�`12)
//							for(int k = 6; k < IShinsainWarifuri.SHINSAIN_NINZU_KIBAN; k++){
//								if(k < 9){
//									kekkaInfo.setShinsainNo("@00000"+ new Integer(k+1).toString());	//�R�����ԍ�(7��)
//								}else{
//									kekkaInfo.setShinsainNo("@0000"+ new Integer(k+1).toString());		//�R�����ԍ�(7��)
//								}
//								kekkaInfo.setSeqNo(new Integer(k+1).toString());
//								shinsakekkaDao.insertShinsaKekkaInfo(connection, kekkaInfo);
//							}
//							
//						}
//						//�ǉ� �����܂�-----------------------------------------------------
						
//						mtLog.info(i+"�s�� ����B");
						cnt++;	//��荞�݌������J�E���g
						
					}else{
						//�f�[�^�ɕs�������邽�߁A�o�^���s��Ȃ��i�s�����m�ۂ���j
						err_list.add(Integer.toString(i));
					}
				
				}
				//2005.08.30 iso �������������O�ɏo�͂���@�\��ǉ�
				if((i > 0) && (i % LOGCOUNT == 0)) {
					mtLog.info("��Ռ���������U�茋�ʃ}�X�^��荞��" + i + "���I��"); 
				}
				
			}
			//2005.08.30 iso �������������O�ɏo�͂���@�\��ǉ�
			mtLog.info("��Ռ���������U�茋�ʃ}�X�^��荞�ݑS���I��"); 
			
			//�}�X�^�Ǘ��e�[�u���̍X�V
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_WARIFURIKEKKA);
			mkInfo.setMasterName("��Ռ���������U�茋��");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");						//�V�K=0�̂�
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);						//�g�p���ĂȂ��H		
			mkInfo.setImportErrMsg(err_list);				//�G���[���i��ʂŎg�p�j
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;			
			
		} catch (IOException e) {
			throw new ApplicationException(
						"CSV�t�@�C���̓Ǎ����ɃG���[���������܂����B",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"�}�X�^�捞����DB�G���[���������܂����B",
						new ErrorInfo("errors.4001"),
						e);
		} finally {
			try {
				if (success) {
					//�R�~�b�g
					DatabaseUtil.commit(connection);
					mtLog.info("��Ռ���������U�茋�� ��荞�ݏ�������I���B-> DB���R�~�b�g���܂����B");
				} else {
					//���[���o�b�N
					DatabaseUtil.rollback(connection);
					mtLog.info("��Ռ���������U�茋�� ��荞�ݏ����ُ�I���B-> DB�����[���o�b�N���܂����B");
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�}�X�^�捞����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	//2005/04/22 �ǉ� ��������------------------------------------------------------------
	//���R �����҃}�X�^��荞�݂ƌp���ۑ�}�X�^��荞�݂̒ǉ��̂���

	/**
	 * �����҃}�X�^�捞.<br />
	 * �����҃}�X�^�����擾����B<br />
	 * <b>1.CSV�f�[�^�����X�g�`���ɕϊ�</b><br />
	 * �����œn���ꂽfileRes��List�`���֕ϊ�����BList�̗v�f�Ƃ��Ă����List�������A
	 * ���̗v�f��CSV���̈ꃌ�R�[�h���̏����i�[����B
	 * <br /><br />
	 * <b>2.�J�������`�F�b�N</b><br />
	 * 1.�ō쐬����List�̈�ڂ̗v�f��List�̗v�f�����A13�ł��邩�m�F����B����ȊO�̏ꍇ�́A��O��throw����B
	 * <br /><br />
	 * <b>3.csv�t�@�C���i�[</b><br />
	 * ���N���X��kakuno()���\�b�h���g�p����CSV�t�@�C�����T�[�o�֊i�[����B
	 * <br /><br />
	 * <b>4.�}�X�^�G�N�X�|�[�g</b><br />
	 * �����҃}�X�^���o�b�N�A�b�v�p�ɃG�N�X�|�[�g����B�G�N�X�|�[�g�́AApplicationSettings.properties�́A
	 * EXPORT_COMMAND�Œ�`����Ă���R�}���h�����s���邱�Ƃɂ���čs����B<br />
	 * �o�͂����_���v�t�@�C���̃t�@�C�����͈ȉ��̒ʂ�B<br />
	 * MASTER_KENKYUSHA_yyyyMMddHHmmssSSS.DMP
	 * <br />
	 * <br />
	 * <b>5.�e�f�[�^�`�F�b�N</b><br />
	 * CSV�t�@�C���̊e�f�[�^�̕K�{�`�F�b�N�A�T�C�Y�`�F�b�N�A�`���`�F�b�N���s���B
	 * ��肪�������ꍇ�́A�G���[�����Ǘ�����List�Ɋi�[�����̂��A���̏��֏�����i�߂�B
	 * �Ȃ��A�����Ҕԍ��A�����@�փR�[�h�A���ǃR�[�h�A�E�R�[�h�́A�e�`�F�b�N�ƂƂ��ɍ�0���ߏ������s���B<br /> 
	 * <br />
	 * <br />
	 * <b>6.�����҃}�X�^����</b><br />
	 * �ȉ���SQL�����s���邱�Ƃɂ���Č����҃}�X�^�ɓ�����L�[�̒l�����邩�ǂ������`�F�b�N����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
		SELECT 
				KENKYU_NO,				//�����Ҕԍ�
				NAME_KANJI_SEI,			//�����i������-���j
				NAME_KANJI_MEI,			//�����i������-���j
				NAME_KANA_SEI,			//�����i�t���K�i-���j
				NAME_KANA_MEI,			//�����i�t���K�i-���j
				SEIBETSU,				//����
				BIRTHDAY,				//���N����
				GAKUI,					//�w��
				SHOZOKU_CD,				//�����@�փR�[�h
				BUKYOKU_CD,				//���ǃR�[�h
				SHOKUSHU_CD,			//�E�R�[�h
				KOSHIN_DATE,			//�X�V����
				BIKO,					//���l
				DEL_FLG					//�폜�t���O
		FROM 
				MASTER_KENKYUSHA KENKYU	//�����҃}�X�^
		WHERE 
				KENKYU.SHOZOKU_CD = ?
		AND 
				KENKYU.KENKYU_NO = ?
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<BR>
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>CSV����擾���������Ҕԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����R�[�h</td><td>CSV����擾���������R�[�h</td></tr>
	 *	<br />
	 * <br />
	 * <b>7-1.���捞</b><br />
	 * 6�œ�����L�[�̒l���擾�ł��Ȃ��ꍇ�A1.�ō쐬����CSV�t�@�C�����List�������҃}�X�^��INSERT����B
	 * List�Ɋi�[����Ă���v�fList�ɂ͈ȉ��̏�񂪊i�[����Ă���B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>�v�f�ԍ�</td><td>�e�[�u����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>�����Ҕԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>�����i�t���K�i-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>�����i�t���K�i-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>�����i������-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>�����i������-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>���N����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>�w��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>�����@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>���ǃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>�E���R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>�f�[�^�X�V��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>�폜�t���O</td></tr>
	 *	</table><br />
	 *
	 * INSERT����ۂɎ��s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	INSERT INTO 
		MASTER_KENKYUSHA (
				KENKYU_NO,
				NAME_KANA_SEI,
				NAME_KANA_MEI,
				NAME_KANJI_SEI,
				NAME_KANJI_MEI,
				SEIBETSU,
				BIRTHDAY,
				GAKUI,
				SHOZOKU_CD,
				BUKYOKU_CD,
				SHOKUSHU_CD,
				KOSHIN_DATE,
				BIKO,
				DEL_FLG
				)
		VALUES
				(?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>CSV����擾���������Ҕԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i-���j</td><td>CSV����擾���������i�t���K�i-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i-���j</td><td>CSV����擾���������i�t���K�i-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i������-���j</td><td>CSV����擾���������i������-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i������-���j</td><td>CSV����擾���������i������-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����</td><td>CSV����擾��������</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���N����</td><td>CSV����擾�������N����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�w��</td><td>CSV����擾�����w��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>CSV����擾���������@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><tdCSV����擾����>���ǃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�E���R�[�h</td><td>CSV����擾�����E���R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�f�[�^�X�V��</td><td>CSV����擾�����f�[�^�X�V��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���l</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>CSV����擾�����폜�t���O</td></tr>
	 *	</table><br/>
	 *	<br />
	 * <b>7-2.���捞</b><br />
	 * 6�œ�����L�[�̒l���擾�ł���ꍇ�A1.�ō쐬����CSV�t�@�C�����List�������҃}�X�^��UPDATE����B
	 * List�Ɋi�[����Ă���v�fList�ɂ͈ȉ��̏�񂪊i�[����Ă���B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>�v�f�ԍ�</td><td>�e�[�u����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>�����Ҕԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>�����i�t���K�i-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>�����i�t���K�i-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>�����i������-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>�����i������-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>5</td><td>����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>6</td><td>���N����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>7</td><td>�w��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>�����@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>���ǃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>�E���R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>�f�[�^�X�V��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>8</td><td>�폜�t���O</td></tr>
	 *	</table><br />
	 *
	 * UPDATE����ۂɎ��s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	UPDATE 
			MASTER_KENKYUSHA
	SET
			KENKYU_NO = ?,
			NAME_KANA_SEI = ?,
			NAME_KANA_MEI = ?,
			NAME_KANJI_SEI = ?,
			NAME_KANJI_MEI = ?,
			SEIBETSU = ?,
			BIRTHDAY = ?,
			GAKUI = ?,
			SHOZOKU_CD = ?,
			BUKYOKU_CD = ?,
			SHOKUSHU_CD = ?,
			KOSHIN_DATE = ?,
			BIKO = ?,
			DEL_FLG = ? 
	WHERE
			KENKYU_NO = ?
	AND 
			SHOZOKU_CD = ?
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>CSV����擾���������Ҕԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i-���j</td><td>CSV����擾���������i�t���K�i-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i-���j</td><td>CSV����擾���������i�t���K�i-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i������-���j</td><td>CSV����擾���������i������-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i������-���j</td><td>CSV����擾���������i������-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����</td><td>CSV����擾��������</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���N����</td><td>CSV����擾�������N����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�w��</td><td>CSV����擾�����w��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>CSV����擾���������@�փR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><tdCSV����擾����>���ǃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�E���R�[�h</td><td>CSV����擾�����E���R�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�f�[�^�X�V��</td><td>CSV����擾�����f�[�^�X�V��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>���l</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>CSV����擾�����폜�t���O</td></tr>
	 *	</table><br/>
	 * UPDATE������������ASHINSEISHAINFO���������A�����Ҕԍ��Ə����R�[�h����v����f�[�^�����邩�m�F����B
	 * ����ꍇ�́ASHINSEISHAINFO�ɂ��Ă��X�V�������s���B
	 * <br /> 
	 * <br />
	 * <b>8.�}�X�^�Ǘ��}�X�^�X�V</b><br />
	 * �}�X�^�Ǘ��}�X�^���ȉ��̏��ōX�V����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�X�V���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^���</td><td>2</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^����</td><td>"�����҃}�X�^"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�ݓ���</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����</td><td>Insert�������R�[�h��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�݃e�[�u����</td><td>"MASTER_KENKYUSHA"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�K�E�X�V�t���O</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�捞�G���[���b�Z�[�W</td><td>���R�[�hINSERT�Ɏ��s�����ꍇ�A�G���[���b�Z�[�W</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSV�t�@�C���p�X</td><td>CSV�i�[��t�H���_�{CSV�t�@�C����</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>9.�}�X�^�Ǘ����ԋp</b><br />
	 * 7.�ōX�V���������Ăяo�����֕ԋp����B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param fileRes				�A�b�v���[�h�t�@�C�����\�[�X�B
	 * @return						�������ʏ��
	 * @throws ApplicationException	
	 */
	private MasterKanriInfo torikomiMasterKenkyusha(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException {

		int columnSize = 26;	//14;	2007/4/27�d�l�ύX
		String ISHOKUMASK = "��";
		
		boolean success = false;
		Connection connection = null;
		//���p�S�p�R���o�[�^
		HanZenConverter converter = new HanZenConverter();
		DateUtil dateUtil = new DateUtil();
		try{
			//CSV�f�[�^�����X�g�`���ɕϊ�����
			List dt = csvFile2List(fileRes);
			
			//�J�������`�F�b�N(1�s�ڂ̂�)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�A�G���[���o�͂��������I������B
					throw new ApplicationException(
								"CSV�t�@�C���������҃}�X�^�̒�`�ƈقȂ�܂��B",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csv�t�@�C���i�[
			String table_name = "MASTER_KENKYUSHA";
			String csvPath = kakuno(fileRes, table_name);
			
			//DB�̃G�N�X�|�[�g
			String file_name = "MASTER_KENKYUSHA_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//�R�l�N�V�����擾
			connection = DatabaseUtil.getConnection();
			
			int cnt = 0;											//����捞����
			ArrayList cd_list = new ArrayList();					//�d���`�F�b�N�p�z��
			ArrayList err_list = new ArrayList();					//�捞�݃G���[�s�i�[�z��
			int dtsize = dt.size();									//�捞�S����
			
			//DAO
			MasterKanriInfoDao     dao            = new MasterKanriInfoDao(userInfo);
			MasterKenkyushaInfoDao kenkyuDao      = new MasterKenkyushaInfoDao(userInfo);
			MasterShokushuInfoDao  shokushuDao    = new MasterShokushuInfoDao(userInfo);
			MasterBukyokuInfoDao   bukyokuDao     = new MasterBukyokuInfoDao(userInfo);
			
			//PK
			ShokushuPk             shokushuPk     = new ShokushuPk();
			BukyokuPk              bukyokuPk      = new BukyokuPk();
			KenkyushaPk            pk             = new KenkyushaPk();
			
			//INFO
			MasterKanriInfo        mkInfo         = new MasterKanriInfo();
			ShokushuInfo           shokushuInfo   = new ShokushuInfo();
			BukyokuInfo            bukyokuInfo    = new BukyokuInfo();
			KenkyushaInfo          info           = new KenkyushaInfo();
			
			//�}�X�^�Ǘ��e�[�u������f�[�^�̎擾
			mkInfo = dao.selectMasterKanriInfo(connection, MASTER_KENKYUSHA);
			//Date lastUpdateDate = mkInfo.getImportDate();			
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[�Ƃ��ď������΂��B�i�s�����m�ۂ���j
					String msg = i+"�s�� �����҃}�X�^�̃e�[�u����`�ƈ�v���܂���B";
					mtLog.warn(msg);
					err_list.add(Integer.toString(i));	//�G���[�̍s�����m��
					
				}else{
					//���폈��
					//�G���[�t���O
					int err_flg = 0;
					
					//�}�X�^�f�[�^�̎擾
					String kenkyu_no        = (String)line.get(0);	//�����Ҕԍ�
					String name_kana_sei    = (String)line.get(1);	//�����i�t���K�i-���j
					String name_kana_mei    = (String)line.get(2);	//�����i�t���K�i-���j
					String name_kanji_sei   = (String)line.get(3);	//�����i������-���j
					String name_kanji_mei   = (String)line.get(4);	//�����i������-���j
					String seibetsu         = (String)line.get(5);	//����
					String birthday         = (String)line.get(6);	//���N����
					String gakui            = (String)line.get(7);	//�w��
					String shozoku_cd       = (String)line.get(8);	//�����@�փR�[�h
					String bukyoku_cd       = (String)line.get(9);	//���ǃR�[�h
					String shokushu_cd      = (String)line.get(10);	//�E���R�[�h
					String oubo_shikaku     = (String)line.get(11); //���厑�i
					//2007/4/27�@���S�J�X�^�}�C�Y���ǉ�
					String otherKikanFlg1	= (String)line.get(12);	//���̋@��1�i�Ϗ���}�[�N�j
					String otherKikanCd1	= (String)line.get(13);	//���̋@�֔ԍ�1
					String otherKikanName1	= (String)line.get(14);	//���̋@�֖�1
					String otherKikanFlg2	= (String)line.get(15);	//���̋@��2�i�Ϗ���}�[�N�j
					String otherKikanCd2	= (String)line.get(16);	//���̋@�֔ԍ�2
					String otherKikanName2	= (String)line.get(17);	//���̋@�֖�2
					String otherKikanFlg3	= (String)line.get(18);	//���̋@��3�i�Ϗ���}�[�N�j
					String otherKikanCd3	= (String)line.get(19);	//���̋@�֔ԍ�3
					String otherKikanName3	= (String)line.get(20);	//���̋@�֖�3
					String otherKikanFlg4	= (String)line.get(21);	//���̋@��4�i�Ϗ���}�[�N�j
					String otherKikanCd4	= (String)line.get(22);	//���̋@�֔ԍ�4
					String otherKikanName4	= (String)line.get(23);	//���̋@�֖�4
					//2007/4/27 �ǉ�����
					String koshin_date      = (String)line.get(24);	//�f�[�^�X�V��
					String del_flg          = (String)line.get(25);	//�폜�t���O
					
					Date birthdayDate = new Date();
					Date koshinDate = new Date();
					
					//�����Ҕԍ��`�F�b�N
					if(StringUtil.isBlank(kenkyu_no)){
						String msg = i+"�s�� �����Ҕԍ��͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						//���l�`�F�b�N
						if(!this.checkLenB(kenkyu_no, 8)){
							String msg = i+"�s�� �����Ҕԍ��̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						//�����҂�8���ȉ��̏ꍇ�͐擪��0��ǉ�
						if (kenkyu_no.length() == 7) {
							kenkyu_no = "0" + kenkyu_no;
						} else if (kenkyu_no.length() == 6) {
							kenkyu_no = "00" + kenkyu_no;
						} else if (kenkyu_no.length() == 5) {
							kenkyu_no = "000" + kenkyu_no;
						} else if (kenkyu_no.length() == 4) {
							kenkyu_no = "0000" + kenkyu_no;
						} else if (kenkyu_no.length() == 3) {
							kenkyu_no = "00000" + kenkyu_no;
						} else if (kenkyu_no.length() == 2) {
							kenkyu_no = "000000" + kenkyu_no;
						} else if (kenkyu_no.length() == 1) {
							kenkyu_no = "0000000" + kenkyu_no;
						}
					}
					
					//�����i�t���K�i-���j�`�F�b�N
					if(StringUtil.isBlank(name_kana_sei)){
						String msg = i+"�s�� �����i�t���K�i-���j�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						//2005/9/14 ���p�p�����ϊ��ł���悤�ɏC��
						//name_kana_sei = converter.convert(name_kana_sei);
						name_kana_sei = converter.convertAll(name_kana_sei);
						if(!this.checkLenB(name_kana_sei, 32)){
							String msg = i+"�s�� �����i�t���K�i-���j�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//�����i�t���K�i-���j�`�F�b�N
					if(!StringUtil.isBlank(name_kana_mei)){
						//2005/9/14 ���p�p�����ϊ��ł���悤�ɏC��
						//name_kana_mei = converter.convert(name_kana_mei);	//���p�J�i�͑S�p�ɕϊ�
						name_kana_mei = converter.convertAll(name_kana_mei);	//���p�J�i�͑S�p�ɕϊ�
						if(!this.checkLenB(name_kana_mei, 32)){
							String msg = i+"�s�� �����i�t���K�i-���j�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//�����i������-���j�`�F�b�N
					if(StringUtil.isBlank(name_kanji_sei)){
						String msg = i+"�s�� �����i������-���j�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						name_kanji_sei = converter.convert(name_kanji_sei);	//���p�J�i�͑S�p�ɕϊ�
						if(!this.checkLenB(name_kanji_sei, 32)){
							String msg = i+"�s�� �����i������-���j�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//�����i������-���j�`�F�b�N
					if(!StringUtil.isBlank(name_kanji_mei)){
						name_kanji_mei = converter.convert(name_kanji_mei);	//���p�J�i�͑S�p�ɕϊ�
						if(!this.checkLenB(name_kanji_mei, 32)){
							String msg = i+"�s�� �����i������-���j�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//���ʃ`�F�b�N
					if(StringUtil.isBlank(seibetsu)){
						String msg = i+"�s�� ���ʂ͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(seibetsu, 1)){
							String msg = i+"�s�� ���ʂ̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}	
						if(!(seibetsu.equals("1") || seibetsu.equals("2"))){
							String msg = i+"�s�� ���ʂ̒l��1(�j),2(��)�ȊO�ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					
					//-----���N�����`�F�b�N
					if(StringUtil.isBlank(birthday)){
						String msg = i+"�s�� ���N�����͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkNum(birthday)){
							String msg = i+"�s�� ���N�����͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(birthday.length() != 8){
							String msg = i+"�s�� ���N������8���ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(this.checkNum(birthday) && birthday.length() == 8){
							//2005/8/30�@���Ɠ��̎擾���s���̈׏C��
							//dateUtil.setCal(birthday.substring(0,4),birthday.substring(5,6),birthday.substring(7));
							dateUtil.setCal(birthday.substring(0,4),birthday.substring(4,6),birthday.substring(6));
							birthdayDate = dateUtil.getCal().getTime();
						}
					}
					//-----�w�ʃ`�F�b�N
					if(!StringUtil.isBlank(gakui) && !gakui.equals("\"")){
						if(!this.checkLenB(gakui, 2)){
							String msg = i+"�s�� �w�ʂ̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!(gakui.equals("") || gakui.equals("10") || gakui.equals("11"))){
							String msg = i+"�s�� �w�ʂ̒l��10(�C�m),11(���m),BLANK(�Y���Ȃ�)�ȊO�ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					//-----�����@�փR�[�h�`�F�b�N
					if(StringUtil.isBlank(shozoku_cd)){
						String msg = i+"�s�� �����@�փR�[�h�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}
					else {
						if(!this.checkNum(shozoku_cd)){
							String msg = i+"�s�� �����@�փR�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(shozoku_cd, 5)){
							String msg = i+"�s�� �����@�փR�[�h�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(shozoku_cd.length() == 4){
							shozoku_cd ="0"+shozoku_cd;
						}
						else if(shozoku_cd.length() == 3){
							shozoku_cd ="00"+shozoku_cd;
						}
						else if(shozoku_cd.length() == 2){
							shozoku_cd ="000"+shozoku_cd;
						}
						else if(shozoku_cd.length() == 1){
							shozoku_cd ="0000"+shozoku_cd;
						}
					}
					
					//-----���ǃR�[�h�`�F�b�N
					if(StringUtil.isBlank(bukyoku_cd)){
						String msg = i+"�s�� ���ǃR�[�h�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkNum(bukyoku_cd)){
							String msg = i+"�s�� ���ǃR�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(bukyoku_cd, 3)){
							String msg = i+"�s�� ���ǃR�[�h�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(bukyoku_cd.length() == 2){
							bukyoku_cd ="0"+bukyoku_cd;
						}
						else if(bukyoku_cd.length() == 1){
							bukyoku_cd ="00"+bukyoku_cd;
						}

						//���ǖ��̎擾
						bukyokuPk.setBukyokuCd(bukyoku_cd);
						try{
							bukyokuInfo = bukyokuDao.selectBukyokuInfo(connection, bukyokuPk);
						}catch(NoDataFoundException e){
							String msg = i+"�s�� ���ǃR�[�h�ƈ�v���镔�ǖ����}�X�^�ɂ���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}	
									
					//-----�E���R�[�h�`�F�b�N
					if(!StringUtil.isBlank(shokushu_cd)){
						if(!this.checkNum(shokushu_cd)){
							String msg = i+"�s�� �E���R�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(shokushu_cd, 2)){
							String msg = i+"�s�� �E���R�[�h�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(shokushu_cd.length() == 1){
							shokushu_cd = "0"+shokushu_cd;
						}
						//�E���̎擾
						shokushuPk.setShokushuCd(shokushu_cd);
						try{
							shokushuInfo = shokushuDao.selectShokushuInfo(connection,shokushuPk);	
						}catch(NoDataFoundException e){
							//�擾�Ɏ��s�����ꍇ�̓G���[��Ԃ�
							String msg = i+"�s�� �E���R�[�h�ƈ�v����E�����}�X�^�ɂ���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
                    
					//���厑�i�`�F�b�N
					if(!StringUtil.isBlank(oubo_shikaku)){
						if(!this.checkNum(shokushu_cd)){
							String msg = i+"�s�� ���厑�i�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(oubo_shikaku, 1)){
							String msg = i+"�s�� ���厑�i�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
//2007/4/27�@�d�l�ύX���ǉ�
					//���̋@��1�i�Ϗ���}�[�N�j�`�F�b�N
					if (!StringUtil.isBlank(otherKikanFlg1) && !ISHOKUMASK.equals(otherKikanFlg1)){
						String msg = i+"�s�� ���̋@��1�i�Ϗ���}�[�N�j�͗L���l�ł͂���܂���B";
						mtLog.warn(msg);
						err_flg = 1;
					}
					
					//���̋@�֔ԍ�1�`�F�b�N
					if (!StringUtil.isBlank(otherKikanCd1)){
						if(!this.checkNum(otherKikanCd1)){
							String msg = i+"�s�� ���̋@�֔ԍ�1�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(otherKikanCd1, 5)){
							String msg = i+"�s�� ���̋@�֔ԍ�1�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if (otherKikanCd1.length() < 5){
							otherKikanCd1 = StringUtil.fillLZero(otherKikanCd1, 5);
						}
					}

					//���̋@�֖�1�`�F�b�N
					if (!StringUtil.isBlank(otherKikanName1)){
						if(!this.checkLenB(otherKikanName1, 80)){
							String msg = i+"�s�� ���̋@�֖�1�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//���̋@��2�i�Ϗ���}�[�N�j�`�F�b�N
					if (!StringUtil.isBlank(otherKikanFlg2) && !ISHOKUMASK.equals(otherKikanFlg2)){
						String msg = i+"�s�� ���̋@��2�i�Ϗ���}�[�N�j�͗L���l�ł͂���܂���B";
						mtLog.warn(msg);
						err_flg = 1;
					}
					
					//���̋@�֔ԍ�2�`�F�b�N
					if (!StringUtil.isBlank(otherKikanCd2)){
						if(!this.checkNum(otherKikanCd2)){
							String msg = i+"�s�� ���̋@�֔ԍ�2�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(otherKikanCd2, 5)){
							String msg = i+"�s�� ���̋@�֔ԍ�2�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if (otherKikanCd2.length() < 5){
							otherKikanCd2 = StringUtil.fillLZero(otherKikanCd2, 5);
						}
					}

					//���̋@�֖�2�`�F�b�N
					if (!StringUtil.isBlank(otherKikanName2)){
						if(!this.checkLenB(otherKikanName2, 80)){
							String msg = i+"�s�� ���̋@�֖�2�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//���̋@��3�i�Ϗ���}�[�N�j�`�F�b�N
					if (!StringUtil.isBlank(otherKikanFlg3) && !ISHOKUMASK.equals(otherKikanFlg3)){
						String msg = i+"�s�� ���̋@��3�i�Ϗ���}�[�N�j�͗L���l�ł͂���܂���B";
						mtLog.warn(msg);
						err_flg = 1;
					}
					
					//���̋@�֔ԍ�3�`�F�b�N
					if (!StringUtil.isBlank(otherKikanCd3)){
						if(!this.checkNum(otherKikanCd3)){
							String msg = i+"�s�� ���̋@�֔ԍ�3�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(otherKikanCd3, 5)){
							String msg = i+"�s�� ���̋@�֔ԍ�3�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if (otherKikanCd3.length() < 5){
							otherKikanCd3 = StringUtil.fillLZero(otherKikanCd3, 5);
						}
					}

					//���̋@�֖�3�`�F�b�N
					if (!StringUtil.isBlank(otherKikanName3)){
						if(!this.checkLenB(otherKikanName3, 80)){
							String msg = i+"�s�� ���̋@�֖�3�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//���̋@��4�i�Ϗ���}�[�N�j�`�F�b�N
					if (!StringUtil.isBlank(otherKikanFlg4) && !ISHOKUMASK.equals(otherKikanFlg4)){
						String msg = i+"�s�� ���̋@��4�i�Ϗ���}�[�N�j�͗L���l�ł͂���܂���B";
						mtLog.warn(msg);
						err_flg = 1;
					}
					

					//���̋@�֔ԍ�4�`�F�b�N
					if (!StringUtil.isBlank(otherKikanCd4)){
						if(!this.checkNum(otherKikanCd4)){
							String msg = i+"�s�� ���̋@�֔ԍ�4�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(otherKikanCd4, 5)){
							String msg = i+"�s�� ���̋@�֔ԍ�4�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if (otherKikanCd4.length() < 5){
							otherKikanCd4 = StringUtil.fillLZero(otherKikanCd4, 5);
						}
					}

					//���̋@�֖�4�`�F�b�N
					if (!StringUtil.isBlank(otherKikanName4)){
						if(!this.checkLenB(otherKikanName4, 80)){
							String msg = i+"�s�� ���̋@�֖�4�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
//2007/4/27�@�d�l�ύX���ǉ�����
					
					//-----�f�[�^�X�V���`�F�b�N(�s�v�H�j
					if(!StringUtil.isBlank(koshin_date)){
						if(!this.checkNum(koshin_date)){
							String msg = i+"�s�� �f�[�^�X�V���͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(koshin_date != null && koshin_date.length() != 8){
							String msg = i+"�s�� �f�[�^�X�V����8���ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(this.checkNum(koshin_date) && koshin_date.length() == 8){
							//2005/8/30 �C��
							//dateUtil.setCal(koshin_date.substring(0,4),koshin_date.substring(5,6),koshin_date.substring(7));
							dateUtil.setCal(koshin_date.substring(0,4),koshin_date.substring(4,6),koshin_date.substring(6));
							koshinDate = dateUtil.getCal().getTime();
						}
					}
					//�폜�t���O�`�F�b�N
					if(!StringUtil.isBlank(del_flg) && !del_flg.equals("\"")){
						if(!this.checkLenB(del_flg, 1)){
							String msg = i+"�s�� �폜�t���O�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}	
						if(!(del_flg.equals("0") || del_flg.equals("1"))){
							String msg = i+"�s�� �폜�t���O�̒l��0,1�ȊO�ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}else{
						//�폜�t���O���Z�b�g����Ă��Ȃ��ꍇ��0(�폜���Ȃ�)�Ƃ���
						del_flg = "0";
					}
					
					//KEY���ڂ̏d���`�F�b�N
					String key = kenkyu_no + shozoku_cd;
					if(cd_list.contains(key)){
						String msg = i+"�s�� Key����(�����Ҕԍ��A�����@�փR�[�h)���d�����Ă��܂��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}else{
						cd_list.add(key);
					}

					//�G���[�����������ꍇ�̂ݓo�^����
					if(err_flg == 0){
						
						//DB�ɓo�^����
						pk.setKenkyuNo(kenkyu_no);
						pk.setShozokuCd(shozoku_cd);
						info.setKenkyuNo(kenkyu_no);
						info.setShozokuCd(shozoku_cd);
						info.setNameKanjiSei(name_kanji_sei);
						info.setNameKanjiMei(name_kanji_mei);
						info.setNameKanaSei(name_kana_sei);
						info.setNameKanaMei(name_kana_mei);
						info.setSeibetsu(seibetsu);
						info.setBirthday(birthdayDate);
						info.setGakui(gakui);
						info.setBukyokuCd(bukyoku_cd);
						info.setShokushuCd(shokushu_cd);
						if (oubo_shikaku.equals("1") || oubo_shikaku.equals("2") || oubo_shikaku.equals("3")){
							info.setOuboShikaku(oubo_shikaku);
						}else{
							info.setOuboShikaku(null);
						}
						//2007/4/27 �ǉ�
						info.setOtherKikanFlg1(otherKikanFlg1);
						info.setOtherKikanCd1(otherKikanCd1);
						info.setOtherKikanName1(otherKikanName1);
						info.setOtherKikanFlg2(otherKikanFlg2);
						info.setOtherKikanCd2(otherKikanCd2);
						info.setOtherKikanName2(otherKikanName2);
						info.setOtherKikanFlg3(otherKikanFlg3);
						info.setOtherKikanCd3(otherKikanCd3);
						info.setOtherKikanName3(otherKikanName3);
						info.setOtherKikanFlg4(otherKikanFlg4);
						info.setOtherKikanCd4(otherKikanCd4);
						info.setOtherKikanName4(otherKikanName4);
						//2007/4/27 �ǉ�����
						info.setKoshinDate(koshinDate);
						info.setDelFlg(del_flg);
						
						//��L�[(�����Ҕԍ�,�����R�[�h)�Ō��������s����
						//KenkyushaInfo result = new KenkyushaInfo();
						KenkyushaInfo result = null;	//�o�^�ƍX�V���d���������Ȃ���
						try{
							result = kenkyuDao.select(connection, pk);
						}catch(NoDataFoundException e){
							//��v����f�[�^�����݂��Ȃ��ꍇ�͓o�^�������s��
							kenkyuDao.insertKenkyushaInfo(connection, info);
						}
						//��v����f�[�^�����݂���ꍇ�͍X�V�������s��
						if(result != null){
						
							kenkyuDao.updateKenkyushaInfo(connection, info);
							
							//�\���ҏ��̃`�F�b�N
							HashMap shinseiMap = new HashMap();
							ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
							searchInfo.setKenkyuNo(info.getKenkyuNo());
							searchInfo.setShozokuCd(info.getShozokuCd());
							ShinseishaMaintenance shinsei = new ShinseishaMaintenance();
							boolean existShinseishaInfo = true;
							try {
								Page shinseishaPage = shinsei.search(userInfo, searchInfo, connection);
								shinseiMap = (HashMap) shinseishaPage.getList().get(0);
							} catch (NoDataFoundException e) {
								//�Y������f�[�^���Ȃ��ꍇ�͂��̂܂܏������I����
								existShinseishaInfo = false;
							}
							//�Y������f�[�^���\���҃e�[�u���ɑ��݂���ꍇ�͍X�V�������s��
							if (existShinseishaInfo
								&& shinseiMap.get("SHINSEISHA_ID") != null
								&& !shinseiMap.get("SHINSEISHA_ID").equals("")) {
								
								ShinseishaInfoDao shinseiDao = new ShinseishaInfoDao(userInfo);
								ShinseishaInfo shinseiInfo = new ShinseishaInfo();
								shinseiInfo.setShinseishaId((String) shinseiMap.get("SHINSEISHA_ID"));
								shinseiInfo.setBirthday(info.getBirthday());
								shinseiInfo.setBukyokuCd(info.getBukyokuCd());
								shinseiInfo.setBukyokuName(bukyokuInfo.getBukaName());
								shinseiInfo.setBukyokuNameRyaku(bukyokuInfo.getBukaRyakusyo());
								shinseiInfo.setKenkyuNo(info.getKenkyuNo());
								shinseiInfo.setNameKanaMei(info.getNameKanaMei());
								shinseiInfo.setNameKanaSei(info.getNameKanaSei());
								shinseiInfo.setNameKanjiMei(info.getNameKanjiMei());
								shinseiInfo.setNameKanjiSei(info.getNameKanjiSei());
								shinseiInfo.setShokushuCd(info.getShokushuCd());
								shinseiInfo.setShokushuNameKanji(shokushuInfo.getShokushuName());
								shinseiInfo.setShokushuNameRyaku(shokushuInfo.getShokushuNameRyaku());
								shinseiInfo.setShozokuCd(info.getShozokuCd());
								shinseiInfo.setShozokuName((String)shinseiMap.get("SHOZOKU_NAME"));
								shinseiInfo.setShozokuNameRyaku((String)shinseiMap.get("SHOZOKU_NAME_RYAKU"));
								shinseiInfo.setShozokuNameEigo((String)shinseiMap.get("SHOZOKU_NAME_EIGO"));
								shinseiInfo.setBukyokuShubetuName((String) shinseiMap.get("OTHER_BUKYOKU"));
								shinseiInfo.setBukyokuShubetuCd((String) shinseiMap.get("SHUBETU_CD"));
								shinseiInfo.setHakkoDate((Date) shinseiMap.get("HAKKO_DATE"));
								shinseiInfo.setHakkoshaId((String) shinseiMap.get("HAKKOSHA_ID"));
								shinseiInfo.setHikoboFlg(shinseiMap.get("HIKOBO_FLG").toString());
								shinseiInfo.setNameRoMei((String) shinseiMap.get("NAME_RO_MEI"));
								shinseiInfo.setNameRoSei((String) shinseiMap.get("NAME_RO_SEI"));
								shinseiInfo.setPassword((String) shinseiMap.get("PASSWORD"));
								shinseiInfo.setYukoDate((Date) shinseiMap.get("YUKO_DATE"));
								//2005/08/30 takano �폜�t���O�ɂ��Ă͔��f�����Ȃ��B�i����ɍ폜����[0]�j
								shinseiInfo.setDelFlg("0");
								//�\���҂̍X�V
								shinseiDao.updateShinseishaInfo(connection, shinseiInfo);
							}	
						}
						cnt++;	//��荞�݌������J�E���g

					}else{
						//�f�[�^�ɕs�������邽�߁A�o�^���s��Ȃ��i�s�����m�ۂ���j
						err_list.add(Integer.toString(i));
					}			
				}
				//2005.08.30 iso �������������O�ɏo�͂���@�\��ǉ�
				if((i > 0) && (i % LOGCOUNT == 0)) {
					mtLog.info("�����҃}�X�^��荞��" + i + "���I��"); 
				}
			}
			//2005.08.30 iso �������������O�ɏo�͂���@�\��ǉ�
			mtLog.info("�����҃}�X�^��荞�ݑS���I��"); 
			
			//�}�X�^�Ǘ��e�[�u���̍X�V
			mkInfo.setMasterShubetu(MASTER_KENKYUSHA);//�}�X�^���
			mkInfo.setMasterName("�����҃}�X�^");//�}�X�^����
			mkInfo.setImportDate(new Date());//��荞�ݓ���
			mkInfo.setKensu(Integer.toString(cnt));//����
			mkInfo.setImportTable(table_name);//��荞�݃e�[�u����
			mkInfo.setImportFlg("0");//�V�K�E�X�V�t���O
			mkInfo.setCsvPath(csvPath);//CSV�t�@�C���p�X
			mkInfo.setImportMsg(null);//������   //�g�p���ĂȂ��H		
			mkInfo.setImportErrMsg(err_list);	//�G���[���i��ʂŎg�p�j
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;
						
		} catch (IOException e) {
			throw new ApplicationException(
						"CSV�t�@�C���̓Ǎ����ɃG���[���������܂����B",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"�}�X�^�捞����DB�G���[���������܂����B",
						new ErrorInfo("errors.4001"),
						e);
		} finally {
			try {
				if (success) {
					//�R�~�b�g
					DatabaseUtil.commit(connection);
				} else {
					//���[���o�b�N
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�}�X�^�捞����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}


	/**
	 * �p���ۑ�}�X�^�捞.<br />
	 * �p���ۑ�}�X�^�����擾����B<br />
	 * <b>1.CSV�f�[�^�����X�g�`���ɕϊ�</b><br />
	 * �����œn���ꂽfileRes��List�`���֕ϊ�����BList�̗v�f�Ƃ��Ă����List�������A
	 * ���̗v�f��CSV���̈ꃌ�R�[�h���̏����i�[����B
	 * <br /><br />
	 * <b>2.�J�������`�F�b�N</b><br />
	 * 1.�ō쐬����List�̈�ڂ̗v�f��List�̗v�f�����A5�ł��邩�m�F����B����ȊO�̏ꍇ�́A��O��throw����B
	 * <br /><br />
	 * <b>3.csv�t�@�C���i�[</b><br />
	 * ���N���X��kakuno()���\�b�h���g�p����CSV�t�@�C�����T�[�o�֊i�[����B
	 * <br /><br />
	 * <b>4.�}�X�^�G�N�X�|�[�g</b><br />
	 * �����҃}�X�^���o�b�N�A�b�v�p�ɃG�N�X�|�[�g����B�G�N�X�|�[�g�́AApplicationSettings.properties�́A
	 * EXPORT_COMMAND�Œ�`����Ă���R�}���h�����s���邱�Ƃɂ���čs����B<br />
	 * �o�͂����_���v�t�@�C���̃t�@�C�����͈ȉ��̒ʂ�B<br />
	 * MASTER_KEIZOKU_yyyyMMddHHmmssSSS.DMP
	 * <br />
	 * <br />
	 * <b>5.�����@�֏��폜</b><br />
	 * �����@�֏����A�ȉ���SQL�����s���邱�Ƃɂ���č폜����B<br />
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
	 *
	 *	DELETE FROM MASTER_KIKAN
	 *	</pre>
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 * <br />
	 * <b>6.�e�f�[�^�`�F�b�N</b><br />
	 * CSV�t�@�C���̊e�f�[�^�̕K�{�`�F�b�N�A�T�C�Y�`�F�b�N�A�`���`�F�b�N���s���B
	 * ��肪�������ꍇ�́A�G���[�����Ǘ�����List�Ɋi�[�����̂��A���̏��֏�����i�߂�B
	 * �Ȃ��A���ƃR�[�h�A�ۑ�ԍ��́A�e�`�F�b�N�ƂƂ��ɍ�0���ߏ������s���B<br /> 
	 * <br />
	 * <br />
	 * <b>7.���捞</b><br />
	 * �f�[�^�`�F�b�N�Ŗ�肪�����ꍇ�A1.�ō쐬����CSV�t�@�C�����List�������҃}�X�^��INSERT����B
	 * List�Ɋi�[����Ă���v�fList�ɂ͈ȉ��̏�񂪊i�[����Ă���B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>�v�f�ԍ�</td><td>�e�[�u����</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>0</td><td>���ƃR�[�h</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>1</td><td>�N�x</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>2</td><td>��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>3</td><td>�ۑ�ԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>4</td><td>�O�N�x����ۋ敪</td></tr>
	 *	</table><br />
	 *
	 * INSERT����ۂɎ��s����SQL�͈ȉ��̒ʂ�B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 *	<table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 *	<tr bgcolor="#FFFFFF"><td><pre>
		INSERT INTO MASTER_KEIZOKU(
					JIGYO_ID,
					KADAI_NO,
					ZENNENDO_KUBUN,
					BIKO
					)					
		VALUES (?,?,?,?)
	 *	</td></tr>
	 *	</table>
	 *	<br />
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>CSV����擾���������Ҕԍ�</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i-���j</td><td>CSV����擾���������i�t���K�i-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i-���j</td><td>CSV����擾���������i�t���K�i-���j</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�����i������-���j</td><td>CSV����擾���������i������-���j</td></tr>
	 *	</table><br/>
	 *	<br />
	 * <b>8.�}�X�^�Ǘ��}�X�^�X�V</b><br />
	 * �}�X�^�Ǘ��}�X�^���ȉ��̏��ōX�V����B
	 *	<table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 *		<tr style="color:white;font-weight: bold"><td>��</td><td>�X�V���</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^���</td><td>2</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�}�X�^����</td><td>"�����҃}�X�^"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�ݓ���</td><td>SYSDATE</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>����</td><td>Insert�������R�[�h��</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>��荞�݃e�[�u����</td><td>"MASTER_KENKYUSHA"</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�V�K�E�X�V�t���O</td><td>0</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>������</td><td>null</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>�捞�G���[���b�Z�[�W</td><td>���R�[�hINSERT�Ɏ��s�����ꍇ�A�G���[���b�Z�[�W</td></tr>
	 *		<tr bgcolor="#FFFFFF"><td>CSV�t�@�C���p�X</td><td>CSV�i�[��t�H���_�{CSV�t�@�C����</td></tr>
	 *	</table><br />
	 * <br />
	 * <b>9.�}�X�^�Ǘ����ԋp</b><br />
	 * 7.�ōX�V���������Ăяo�����֕ԋp����B
	 * 
	 * @param userInfo				���s���郆�[�U���B
	 * @param fileRes				�A�b�v���[�h�t�@�C�����\�[�X�B
	 * @return						�������ʏ��
	 * @throws ApplicationException	
	 */
	private MasterKanriInfo torikomiMasterKeizoku(UserInfo userInfo, FileResource fileRes)
		throws ApplicationException {
//		 <!-- UPDATE�@START 2007/07/11 BIS ���� -->	
		//int columnSize = 5;
		int columnSize = 12;
//		 <!-- UPDATE�@END 2007/07/11 BIS ���� -->	
		boolean success = false;
		Connection connection = null;
		try{
			//CSV�f�[�^�����X�g�`���ɕϊ�����
			List dt = csvFile2List(fileRes);
			
			//�J�������`�F�b�N(1�s�ڂ̂�)
			if(dt.size() != 0){
				ArrayList line1 = (ArrayList)dt.get(0);
				if(line1.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�A�G���[���o�͂��������I������B
					throw new ApplicationException(
								"CSV�t�@�C�����p���ۑ�}�X�^�̒�`�ƈقȂ�܂��B",
								new ErrorInfo("errors.7002"));
				}
			}
			
			//csv�t�@�C���i�[
			String table_name = "MASTER_KEIZOKU";
			String csvPath = kakuno(fileRes, table_name);
			
			//DB�̃G�N�X�|�[�g
			String file_name = "MASTER_KEIZOKU_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[]{file_name, table_name});
			FileUtil.execCommand(cmd);
			
			//�R�l�N�V�����擾
			connection = DatabaseUtil.getConnection();
			
			//�捞�iDELETE �� INSERT�����j
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);
			
			int cnt = 0;											//����捞����
			ArrayList cd_list  = new ArrayList();
			ArrayList err_list = new ArrayList();					//�捞�݃G���[�s�i�[�z��
			int dtsize = dt.size();									//�捞�S����
			
			//DAO
			//JigyoKanriInfoDao      jigyoKanriDao  = new JigyoKanriInfoDao(userInfo); 2005/9/28���g�p
			MasterKeizokuInfoDao   keizokuDao     = new MasterKeizokuInfoDao(userInfo);
			
			//PK
			//JigyoKanriPk           jigyoKanriPk   = new JigyoKanriPk(); 2005/9/28���g�p
			
			//INFO
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			KeizokuInfo info = new KeizokuInfo();
					
			for(int i=1; i<=dtsize; i++){
				ArrayList line = (ArrayList)dt.get(i-1);

				if(line.size() != columnSize){
					//���ڐ����Ⴄ�ꍇ�̓G���[�Ƃ��ď������΂��B�i�s�����m�ۂ���j
					String msg = i+"�s�� �p���ۑ�}�X�^�̃e�[�u����`�ƈ�v���܂���B";
					mtLog.warn(msg);
					err_list.add(Integer.toString(i));	//�G���[�̍s�����m��
					
				}else{
					//���폈��
					//�G���[�t���O
					int err_flg = 0;
					
					//�}�X�^�f�[�^�̎擾
					String jigyo_cd           = (String)line.get(0);	//���ƃR�[�h
					String nendo              = (String)line.get(1);	//�N�x
					String kaisu              = (String)line.get(2);	//��
					String kadai_no           = (String)line.get(3);	//�ۑ�ԍ�
					String kahi_kubun         = (String)line.get(4);	//�O�N�x����ۋ敪
//					 <!-- ADD�@START 2007/07/11 BIS ���� -->	
					String kenkyu_no   		  = (String)line.get(5);	//�����Ҕԍ�
					String kadai_name_kanji   = (String)line.get(6);	//�����ۑ薼
					String naiyakugaku1       = (String)line.get(7);	//�P�N�ړ���z
					String naiyakugaku2       = (String)line.get(8);	//�Q�N�ړ���z
					String naiyakugaku3       = (String)line.get(9);	//�R�N�ړ���z
					String naiyakugaku4       = (String)line.get(10);	//�S�N�ړ���z
					String naiyakugaku5       = (String)line.get(11);	//�T�N�ړ���z
//					 <!-- ADD�@END 2007/07/11 BIS ���� -->	
					//���ƃR�[�h�`�F�b�N
					if(jigyo_cd == null || jigyo_cd.equals("")){
						String msg = i+"�s�� ���ƃR�[�h�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						//�����`�F�b�N
						if(!this.checkLenB(jigyo_cd, 5)){
							String msg = i+"�s�� ���ƃR�[�h�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						//���ƃR�[�h��5���ȉ��̏ꍇ�͐擪��0��ǉ�
						if (jigyo_cd.length() == 4) {
							jigyo_cd = "0" + jigyo_cd;
						} else if (jigyo_cd.length() == 3) {
							jigyo_cd = "00" + jigyo_cd;
						} else if (jigyo_cd.length() == 2) {
							jigyo_cd = "000" + jigyo_cd;
						} else if (jigyo_cd.length() == 1) {
							jigyo_cd = "0000" + jigyo_cd;
						}

						//DB�Ɏ��ƃR�[�h���o�^����Ă��邩�`�F�b�N����
						try{
							MasterJigyoInfoDao.selectRecord(connection, jigyo_cd);
						}catch(NoDataFoundException e){
							String msg = i+"�s�� �Y�����鎖�ƃR�[�h�����݂��܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					
					//�N�x�`�F�b�N
					if(nendo == null || nendo.equals("")){
						String msg = i+"�s�� �N�x�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(nendo, 2)){
							String msg = i+"�s�� �N�x�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
//						 <!-- ADD�@START 2007/07/04 BIS ���� -->
						if(!this.checkNum(nendo)){
							String msg = i+"�s�� �N�x�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
//						<!-- ADD�@END�@ 2007/07/04 BIS ���� -->
					}
					
					//�񐔃`�F�b�N
					if(kaisu == null || kaisu.equals("")){
						String msg = i+"�s�� �񐔂͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(kaisu, 1)){
							String msg = i+"�s�� �񐔂̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
//						 <!-- ADD�@START 2007/07/04 BIS ���� -->
						if(!this.checkNum(kaisu)){
							String msg = i+"�s�� �񐔂͔��p�p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
//						<!-- ADD�@END�@ 2007/07/04 BIS ���� -->
					}

					//�ۑ�ԍ��`�F�b�N
					if(kadai_no == null || kadai_no.equals("")){
						String msg = i+"�s�� �ۑ�ԍ��͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkNum(kadai_no)){
							String msg = i+"�s�� �ۑ�ԍ��͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(kadai_no, 8)){
							String msg = i+"�s�� �ۑ�ԍ��̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						else if(kadai_no.length() == 7){
							kadai_no ="0"+kadai_no;
						}
						else if(kadai_no.length() == 6){
							kadai_no ="00"+kadai_no;
						}
						else if(kadai_no.length() == 5){
							kadai_no ="000"+kadai_no;
						}
						else if(kadai_no.length() == 4){
							kadai_no ="0000"+kadai_no;
						}
						else if(kadai_no.length() == 3){
							kadai_no ="00000"+kadai_no;
						}
						else if(kadai_no.length() == 2){
							kadai_no ="000000"+kadai_no;
						}
						else if(kadai_no.length() == 1){
							kadai_no ="0000000"+kadai_no;
						}	
					}
					
					//�O�N�x����ۋ敪�`�F�b�N
					if(kahi_kubun == null || kahi_kubun.equals("")){
						String msg = i+"�s�� �O�N�x����ۋ敪�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					}else{
						if(!this.checkLenB(kahi_kubun, 1)){
							String msg = i+"�s�� �O�N�x����ۋ敪�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}	
						if(!(kahi_kubun.equals("1") || kahi_kubun.equals("2"))){
							String msg = i+"�s�� �O�N�x����ۋ敪�̒l��1(��),2(�s��)�ȊO�ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					
//					 <!-- ADD�@START 2007/07/11 BIS ���� -->	
					
					//�����Ҕԍ�
					if(kenkyu_no != null && !"".equals(kenkyu_no)){
						if(!this.checkHan(kenkyu_no)){
							String msg = i+"�s�� �����Ҕԍ��͔��p�p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(kenkyu_no, 8)){
							String msg = i+"�s�� �����Ҕԍ��̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						else if(kenkyu_no.length() == 7){
							kenkyu_no ="0"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 6){
							kenkyu_no ="00"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 5){
							kenkyu_no ="000"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 4){
							kenkyu_no ="0000"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 3){
							kenkyu_no ="00000"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 2){
							kenkyu_no ="000000"+kenkyu_no;
						}
						else if(kenkyu_no.length() == 1){
							kenkyu_no ="0000000"+kenkyu_no;
						}	
					}
					
					//�����ۑ薼
					if(kadai_name_kanji != null && !"".equals(kadai_name_kanji)){
						if(!this.checkLenB(kadai_name_kanji, 80)){
							String msg = i+"�s�� �����ۑ薼�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					
					//�P�N�ړ���z
					if(naiyakugaku1 != null && !"".equals(naiyakugaku1)){
						if(!this.checkNum(naiyakugaku1)){
							String msg = i+"�s�� �P�N�ړ���z�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(naiyakugaku1, 7)){
							String msg = i+"�s�� �P�N�ړ���z�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}
					
					//�Q�N�ړ���z
					if(naiyakugaku2 != null && !"".equals(naiyakugaku2)){
						if(!this.checkNum(naiyakugaku2)){
							String msg = i+"�s�� �Q�N�ړ���z�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(naiyakugaku2, 7)){
							String msg = i+"�s�� �Q�N�ړ���z�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//�R�N�ړ���z
					if(naiyakugaku3 != null && !"".equals(naiyakugaku3)){
						if(!this.checkNum(naiyakugaku3)){
							String msg = i+"�s�� �R�N�ړ���z�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(naiyakugaku3, 7)){
							String msg = i+"�s�� �R�N�ړ���z�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//�S�N�ړ���z
					if(naiyakugaku4 != null && !"".equals(naiyakugaku4)){
						if(!this.checkNum(naiyakugaku4)){
							String msg = i+"�s�� �S�N�ړ���z�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(naiyakugaku4, 7)){
							String msg = i+"�s�� �S�N�ړ���z�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//�T�N�ړ���z
					if(naiyakugaku5 != null && !"".equals(naiyakugaku5)){
						if(!this.checkNum(naiyakugaku5)){
							String msg = i+"�s�� �T�N�ړ���z�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						if(!this.checkLenB(naiyakugaku5, 7)){
							String msg = i+"�s�� �T�N�ړ���z�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

//					<!-- ADD�@END�@ 2007/07/11 BIS ���� -->
//					<!-- UPDATE�@START�@ 2007/07/27 BIS ���� -->
					/** 
					 * 	
					 * //2005/08/22 takano �d���`�F�b�N������ID�{�ۑ�ԍ��ɏC��
					    String jigyo_id = DateUtil.changeWareki2Seireki(nendo) + jigyo_cd + kaisu;
						//KEY���ڂ̏d���`�F�b�N
						//String key = jigyo_cd+kadai_no;
						String key = jigyo_id+kadai_no;
						if(cd_list.contains(key)){
							//String msg = i+"�s�� Key����(���ƃR�[�h�A�ۑ�ԍ�)���d�����Ă��܂��B";
							String msg = i+"�s�� Key����(����ID�A�ۑ�ԍ�)���d�����Ă��܂��B";
							mtLog.warn(msg);
							err_flg = 1;
						}else{
							cd_list.add(key);
						}
						
					*/
					//2005/08/22 takano �d���`�F�b�N������ID�{�ۑ�ԍ��ɏC��
					String jigyo_id = null;
					if(err_flg != 1){
						jigyo_id = DateUtil.changeWareki2Seireki(nendo) + jigyo_cd + kaisu;
						//KEY���ڂ̏d���`�F�b�N
						//String key = jigyo_cd+kadai_no;
						String key = jigyo_id+kadai_no;
						if(cd_list.contains(key)){
							//String msg = i+"�s�� Key����(���ƃR�[�h�A�ۑ�ԍ�)���d�����Ă��܂��B";
							String msg = i+"�s�� Key����(����ID�A�ۑ�ԍ�)���d�����Ă��܂��B";
							mtLog.warn(msg);
							err_flg = 1;
						}else{
							cd_list.add(key);
						}
					}
//					<!-- UPDATE�@END�@ 2007/07/27 BIS ���� -->
					//DB�Ɏ���ID���o�^����Ă��邩�`�F�b�N����
//					String jigyo_id = DateUtil.changeWareki2Seireki(nendo) + jigyo_cd + kaisu;	
//					2005/08/11 �s�v�ƂȂ�						
//					jigyoKanriPk.setJigyoId(jigyo_id);
//					try{
//						jigyoKanriDao.selectJigyoKanriInfo(connection,jigyoKanriPk);
//					}catch(NoDataFoundException e){
//						String msg = i+"�s�� �Y�����鎖��ID�����ƊǗ��e�[�u���ɑ��݂��܂���B";
//						mtLog.warn(msg);
//						err_flg = 1;
//					}
					
					//�G���[�����������ꍇ�̂ݓo�^����
					if(err_flg == 0){
						info.setJigyoId(jigyo_id);
						info.setKadaiNo(kadai_no);					
						info.setZennendoKubun(kahi_kubun);
						info.setBiko(null);			
//						<!-- ADD�@START�@ 2007/07/11 BIS ���� -->						
						info.setKenkyuNo(kenkyu_no);
						info.setKadaiNameKanji(kadai_name_kanji);
						info.setNaiyakugaku1(naiyakugaku1);
						info.setNaiyakugaku2(naiyakugaku2);
						info.setNaiyakugaku3(naiyakugaku3);
						info.setNaiyakugaku4(naiyakugaku4);
						info.setNaiyakugaku5(naiyakugaku5);
//						<!-- ADD�@END�@ 2007/07/11 BIS ���� -->	
						keizokuDao.insertKeizokuInfo(connection, info);
						cnt++;	//��荞�݌������J�E���g
					}else{
						//�f�[�^�ɕs�������邽�߁A�o�^���s��Ȃ��i�s�����m�ۂ���j
						err_list.add(Integer.toString(i));
					}			
				}
			}
			
			//�}�X�^�Ǘ��e�[�u���̍X�V
			mkInfo.setMasterShubetu(MASTER_KEIZOKUKADAI);
			mkInfo.setMasterName("�p���ۑ�}�X�^");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null);			
			mkInfo.setImportErrMsg(err_list);	//�G���[���i��ʂŎg�p�j
			
			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;
						
		} catch (IOException e) {
			throw new ApplicationException(
						"CSV�t�@�C���̓Ǎ����ɃG���[���������܂����B",
						new ErrorInfo("errors.7002"),
						e);
		} catch (DataAccessException e) {
			throw new ApplicationException(
						"�}�X�^�捞����DB�G���[���������܂����B",
						new ErrorInfo("errors.4001"),
						e);
		} finally {
			try {
				if (success) {
					//�R�~�b�g
					DatabaseUtil.commit(connection);
				} else {
					//���[���o�b�N
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�}�X�^�捞����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	// �ǉ� �����܂�-----------------------------------------------------------------

	/**
	 * �L�[���[�h�}�X�^��荞�ݏ��� 2005/07/21
	 * @param userInfo
	 * @param fileRes CVS�t�@�C��
	 * @return�@�}�X�^�Ǘ����
	 * @throws ApplicationException
	 */
	private MasterKanriInfo torikomiMasterKeyword(UserInfo userInfo, FileResource fileRes)
			throws ApplicationException
	{

		//�J�������ݒ�
		int columnSize = 10;

		boolean success = false;
		Connection connection = null;

		try {
			//CSV�f�[�^�����X�g�`���ɕϊ�����
			List dt = csvFile2List(fileRes);

			//�J�������`�F�b�N(1�s�ڂ̂�)
			if (dt.size() != 0) {
				ArrayList line1 = (ArrayList) dt.get(0);
				if (line1.size() != columnSize) {
					//���ڐ����Ⴄ�ꍇ�̓G���[���o�͂��������I������B
					throw new ApplicationException("CSV�t�@�C�����L�[���[�h�}�X�^�̒�`�ƈقȂ�܂��B",
							new ErrorInfo("errors.7002"));
				}
			}

			//csv�t�@�C���i�[
			String table_name = "MASTER_KEYWORD";
			String csvPath = kakuno(fileRes, table_name);

			//DB�̃G�N�X�|�[�g
			String file_name = "MASTER_KEYWORD_"
					+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[] { file_name,	table_name });
			FileUtil.execCommand(cmd);

			//�R�l�N�V�����擾
			connection = DatabaseUtil.getConnection();

			//�捞�iDELETE �� INSERT�����j
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);

			int cnt = 0; //����捞����
			ArrayList cd_list = new ArrayList(); //�捞�f�[�^�̃L�[�i�[�z��(��Ӑ���`�F�b�N�p)
			ArrayList err_list = new ArrayList(); //�捞�݃G���[�����i�[�z��
			int dtsize = dt.size(); //�捞�S����

			//DAO
			MasterKeywordInfoDao keywordDao = new MasterKeywordInfoDao(userInfo);
			for (int i = 1; i <= dtsize; i++) {
				ArrayList line = (ArrayList) dt.get(i - 1);

				if (line.size() != columnSize) {
					//���ڐ����Ⴄ�ꍇ�A�G���[�Ƃ��ď������΂��B�i�s�����m�ۂ���j
					String msg = i + "�s�� �L�[���[�h�}�X�^�̃e�[�u����`�ƈ�v���܂���B";
					mtLog.warn(msg);
					//err_list.add(msg); //�G���[���e���i�[ �� �ۗ��B�R�����g�A�E�g
					err_list.add(Integer.toString(i)); //�G���[�̍s�����m��

				} else {
					//���폈��
					//�G���[�t���O
					int err_flg = 0;
					int index = 0;
					//�}�X�^�f�[�^�̎擾
					String bunkasaimoku_cd = (String) line.get(index++);//�זڃR�[�h
					String bunkatsu_no = (String) line.get(index++);	//�����ԍ��̒ǉ�
					String saimoku_name = (String) line.get(index++); 	//�זږ�
					String bunka_cd = (String) line.get(index++); 		//���ȃR�[�h
					String bunka_name = (String) line.get(index++); 	//���Ȗ�
					String bunya_cd = (String) line.get(index++); 		//����R�[�h
					String bunya_name = (String) line.get(index++); 	//���얼
					String kei = (String) line.get(index++); 			//�n
					String keywordcd = (String) line.get(index++); 		//�L��
					String keyword = (String) line.get(index++); 		//�L�[���[�h

//					//----�����ԍ���A,B,1,2�ȊO��,"-"��ݒ肷��
//					if (bunkatsu_no == null || bunkatsu_no.equals("")) {
//						bunkatsu_no = "-";
//					}else if (!"A".equals(bunkatsu_no) && !"B".equals(bunkatsu_no)
//							&& !"1".equals(bunkatsu_no) && !"2".equals(bunkatsu_no)){
//						bunkatsu_no = "-";
//					}

					//-----���ȍזڃR�[�h�`�F�b�N
					//���l�`�F�b�N
					if (bunkasaimoku_cd == null || bunkasaimoku_cd.equals("")) {
						String msg = i + "�s�� �זڃR�[�h�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						if (!this.checkNum(bunkasaimoku_cd)) {
							//���p�����Ŗ����ꍇ
							String msg = i + "�s�� �זڃR�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						} else {
							//���p�����̏ꍇ
							//�����`�F�b�N�i4���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = bunkasaimoku_cd.length();
							if (cd_length == 1) {
								bunkasaimoku_cd = "000" + bunkasaimoku_cd;
							} else if (cd_length == 2) {
								bunkasaimoku_cd = "00" + bunkasaimoku_cd;
							} else if (cd_length == 3) {
								bunkasaimoku_cd = "0" + bunkasaimoku_cd;
							} else if (cd_length == 0 || cd_length > 4) {
								String msg = i + "�s�� �זڃR�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}

					//----�����ԍ��`�F�b�N
					if (bunkatsu_no == null || bunkatsu_no.equals("")) {
//						String msg = i + "�s�� �����ԍ��͕K�{���ڂł��B";
//						mtLog.warn(msg);
//						err_flg = 1;
						bunkatsu_no = "-";
					} else {
						if (!this.checkLenB(bunkatsu_no, 1)) {
							String msg = i + "�s�� �����ԍ��̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
						//<!-- UPDATE�@START 2007/07/21 BIS ���� -->
						//�����ԍ���1,2,A,B�ȊO�̏ꍇ��"-"�œo�^����
						/*�Â��R�[�h
						if ( !bunkatsu_no.equals("1") && !bunkatsu_no.equals("2")
								&& !bunkatsu_no.equals("A") && !bunkatsu_no.equals("B")) {
							bunkatsu_no = "-";
						}
						*/
						//�����ԍ���1,2,3,4,5,A,B�ȊO�̏ꍇ��"-"�œo�^����
						if ( !bunkatsu_no.equals("1") && !bunkatsu_no.equals("2")
								&& !bunkatsu_no.equals("3") && !bunkatsu_no.equals("4") && !bunkatsu_no.equals("5")
								&& !bunkatsu_no.equals("A") && !bunkatsu_no.equals("B")) {
							bunkatsu_no = "-";
						}
						//<!-- UPDATE�@END 2007/07/21 BIS ���� -->
					}

					//-----�זږ��`�F�b�N
					if (saimoku_name == null || saimoku_name.equals("")) {
						String msg = i + "�s�� �זږ��͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					} else {
						if (!this.checkLenB(saimoku_name, 60)) {
							String msg = i + "�s�� �זږ��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					
					//-----���ȃR�[�h
					if (bunka_cd == null || bunka_cd.equals("")) {
						String msg = i + "�s�� ���ȃR�[�h�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						//���l�`�F�b�N
						if (!this.checkNum(bunka_cd)) {
							//���p�����Ŗ����ꍇ
							String msg = i + "�s�� ���ȃR�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						} else {
							//���p�����̏ꍇ
							//�����`�F�b�N�i4���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = bunka_cd.length();
							if (cd_length == 1) {
								bunka_cd = "000" + bunka_cd;
							} else if (cd_length == 2) {
								bunka_cd = "00" + bunka_cd;
							} else if (cd_length == 3) {
								bunka_cd = "0" + bunka_cd;
							} else if (cd_length == 0 || cd_length > 4) {
								String msg = i + "�s�� ���ȃR�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}

					//-----���Ȗ��`�F�b�N
					if (!this.checkLenB(bunka_name, 60)) {
						String msg = i + "�s�� ���Ȗ��̒������s���ł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}

					//-----����R�[�h
					if (bunya_cd == null || bunya_cd.equals("")) {
						String msg = i + "�s�� ����R�[�h�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						//���l�`�F�b�N
						if (!this.checkNum(bunya_cd)) {
							//���p�����Ŗ����ꍇ
							String msg = i + "�s�� ����R�[�h�͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						} else {
							//���p�����̏ꍇ
							//�����`�F�b�N�i4���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = bunya_cd.length();
							if (cd_length == 1) {
								bunya_cd = "000" + bunya_cd;
							} else if (cd_length == 2) {
								bunya_cd = "00" + bunya_cd;
							} else if (cd_length == 3) {
								bunya_cd = "0" + bunya_cd;
							} else if (cd_length == 0 || cd_length > 4) {
								String msg = i + "�s�� ����R�[�h�̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}
					
					//-----���얼�`�F�b�N
					if (!this.checkLenB(bunya_name, 60)) {
						String msg = i + "�s�� ���얼�̒������s���ł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}
					
					//-----�n�`�F�b�N
					if (!this.checkLenB(kei, 1)) {
						String msg = i + "�s�� �n�̒������s���ł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}

					//----�L���`�F�b�N
					if (keywordcd == null || keywordcd.equals("")) {
						String msg = i + "�s�� �L���͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						if (!this.checkLenB(keywordcd, 1)) {
							String msg = i + "�s�� �L���̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					//----�L�[���[�h�`�F�b�N
					if (keyword == null || keyword.equals("")) {
						String msg = i + "�s�� �L�[���[�h�͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						if (!this.checkLenB(keyword, 120)) {
							String msg = i + "�s�� �L�[���[�h�̒������s���ł��B";
							mtLog.warn(msg);
							err_flg = 1;
						}
					}

					if (err_flg == 0) {
						//KEY���ڂ̏d���`�F�b�N
						//���R Key�l�ɕ����ԍ����ǉ����ꂽ����
						if (cd_list.contains(bunkasaimoku_cd + bunkatsu_no + keywordcd)) {
							String msg = i + "�s�� Key�l(�זڃR�[�h,�����ԍ�,�L��)�͏d�����Ă��܂��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						} else {
							cd_list.add(bunkasaimoku_cd + bunkatsu_no + keywordcd);
						}
					}
					
					//�G���[�����������ꍇ�̂ݓo�^����
					if (err_flg == 0) {
						//DB�ɓo�^����
						KeywordInfo info = new KeywordInfo();
						info.setBunkaSaimokuCd(bunkasaimoku_cd);
						info.setSaimokuName(saimoku_name);
						info.setBunkaCd(bunka_cd);
						info.setBunkaName(bunka_name);
						info.setBunyaCd(bunya_cd);
						info.setBunyaName(bunya_name);
						info.setKei(kei);
						info.setBunkatsuNo(bunkatsu_no);
						info.setKeywordCd(keywordcd);
						info.setKeyword(keyword);

						keywordDao.insertKeywordInfo(connection, info);
						cnt++; //��荞�݌������J�E���g
					} else {
						//�f�[�^�ɕs�������邽�߁A�o�^���s��Ȃ��i�s�����m�ۂ���j
						err_list.add(Integer.toString(i));
					}
				}
			}

			//�}�X�^�Ǘ��e�[�u���̍X�V
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_KEYWORD);
			mkInfo.setMasterName("�L�[���[�h�}�X�^");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null); //�g�p���ĂȂ��H
			mkInfo.setImportErrMsg(err_list); //�G���[���i��ʂŎg�p�j

			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;

		} catch (IOException e) {
			throw new ApplicationException("CSV�t�@�C���̓Ǎ����ɃG���[���������܂����B", new ErrorInfo(
					"errors.7002"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("�}�X�^�捞����DB�G���[���������܂����B", new ErrorInfo(
					"errors.4001"), e);
		} finally {
			try {
				if (success) {
					//�R�~�b�g
					DatabaseUtil.commit(connection);
				} else {
					//���[���o�b�N
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("�}�X�^�捞����DB�G���[���������܂����B", new ErrorInfo(
						"errors.4001"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �̈�}�X�^��荞�ݏ��� 2005/08/11
	 * @param userInfo
	 * @param fileRes CVS�t�@�C��
	 * @return�@�}�X�^�Ǘ����
	 * @throws ApplicationException
	 */
	private MasterKanriInfo torikomiMasterRyoiki(UserInfo userInfo, FileResource fileRes)
			throws ApplicationException
	{

		//�J�������ݒ�
		int columnSize = 9;

		boolean success = false;
		Connection connection = null;

		try {
			//CSV�f�[�^�����X�g�`���ɕϊ�����
			List dt = csvFile2List(fileRes);

			//�J�������`�F�b�N(1�s�ڂ̂�)
			if (dt.size() != 0) {
				ArrayList line1 = (ArrayList) dt.get(0);
				if (line1.size() != columnSize) {
					//���ڐ����Ⴄ�ꍇ�̓G���[���o�͂��������I������B
					throw new ApplicationException("CSV�t�@�C�����L�[���[�h�}�X�^�̒�`�ƈقȂ�܂��B",
							new ErrorInfo("errors.7002"));
				}
			}

			//csv�t�@�C���i�[
			String table_name = "MASTER_RYOIKI";
			String csvPath = kakuno(fileRes, table_name);

			//DB�̃G�N�X�|�[�g
			String file_name = "MASTER_RYOIKI_"
					+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			String cmd = MessageFormat.format(EXPORT_COMMAND, new String[] { file_name,	table_name });
			FileUtil.execCommand(cmd);

			//�R�l�N�V�����擾
			connection = DatabaseUtil.getConnection();

			//�捞�iDELETE �� INSERT�����j
			MasterKanriInfoDao dao = new MasterKanriInfoDao(userInfo);
			dao.deleteMaster(connection, table_name);

			int cnt = 0; //����捞����
			ArrayList cd_list = new ArrayList(); //�捞�f�[�^�̃L�[�i�[�z��(��Ӑ���`�F�b�N�p)
			ArrayList err_list = new ArrayList(); //�捞�݃G���[�����i�[�z��
			int dtsize = dt.size(); //�捞�S����

			//DAO
			MasterRyouikiInfoDao ryoikiDao = new MasterRyouikiInfoDao(userInfo);
			for (int i = 1; i <= dtsize; i++) {
				ArrayList line = (ArrayList) dt.get(i - 1);

				if (line.size() != columnSize) {
					//���ڐ����Ⴄ�ꍇ�A�G���[�Ƃ��ď������΂��B�i�s�����m�ۂ���j
					String msg = i + "�s�� �̈�}�X�^�̃e�[�u����`�ƈ�v���܂���B";
					mtLog.warn(msg);
					//err_list.add(msg); //�G���[���e���i�[ �� �ۗ��B�R�����g�A�E�g
					err_list.add(Integer.toString(i)); //�G���[�̍s�����m��

				} else {
					//���폈��
					//�G���[�t���O
					int err_flg = 0;
					int index = 0;
					//�}�X�^�f�[�^�̎擾
					String ryoiki_no = (String) line.get(index++);		//�̈�ԍ�
					String ryoiki_name = (String) line.get(index++); 	//�̈旪���̖�
					String komoku_no = (String) line.get(index++); 		//�������ڔԍ�
					String kobou = (String) line.get(index++); 			//����t���O
					String keikaku = (String) line.get(index++); 		//�v�挤���t���O
                    //update start ly 2006/06/30
                    String ZennendoOuboFlg = (String) line.get(index++);//�O�N�x����Ώۃt���O
                    String SettelKikanKaishi=(String)line.get(index++); //�ݒ���ԁi�J�n�N�x�j
                    String SettelKikanShuryo= (String)line.get(index++);//�ݒ���ԁi�I���N�x�j
					String biko = (String) line.get(index++); 			//���l
                    //update end ly 2006/06/30

					//-----�̈�ԍ��`�F�b�N
					//���l�`�F�b�N
					if (ryoiki_no == null || ryoiki_no.equals("")) {
						String msg = i + "�s�� �̈�ԍ��͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						if (!this.checkNum(ryoiki_no)) {
							//���p�����Ŗ����ꍇ
							String msg = i + "�s�� �̈�ԍ��͔��p�����ł͂���܂���B";
							mtLog.warn(msg);
							err_flg = 1;
						} else {
							//���p�����̏ꍇ
							//�����`�F�b�N�i3���Ŗ����ꍇ�́A�擪��"0"��������j
							int cd_length = ryoiki_no.length();
							if (cd_length == 1) {
								ryoiki_no = "00" + ryoiki_no;
							} else if (cd_length == 2) {
								ryoiki_no = "0" + ryoiki_no;
							} else if (cd_length == 0 || cd_length > 3) {
								String msg = i + "�s�� �̈�ԍ��̌������s���ł��B";
								mtLog.warn(msg);
								//err_list.add(msg);
								err_flg = 1;
							}
						}
					}

					//-----�̈旪���̖��`�F�b�N
					if (ryoiki_name == null || ryoiki_name.equals("")) {
						String msg = i + "�s�� �̈旪���̖��͕K�{���ڂł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					} else {
						if (!this.checkLenB(ryoiki_name, 16)) {
							String msg = i + "�s�� �̈旪���̖��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}
					
					//-----�������ڔԍ��`�F�b�N
					if (komoku_no == null || komoku_no.equals("")) {
						String msg = i + "�s�� �������ڔԍ��͕K�{���ڂł��B";
						mtLog.warn(msg);
						err_flg = 1;
					} else {
						//���p�p���`�F�b�N
						if (!this.checkHan(komoku_no)) {
							//���p�����Ŗ����ꍇ
							String msg = i + "�s�� �������ڔԍ��͔��p�p�����ł͂���܂���B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
						//�����`�F�b�N�@2005/9/28�ǉ�
						if (!this.checkLenB(komoku_no, 3)) {
							String msg = i + "�s�� �������ڔԍ��̒������s���ł��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						}
					}

					//-----����t���O�`�F�b�N
					if (!"1".equals(kobou)) {
						kobou = "0";	//1�ȊO�̏ꍇ�A�O��ݒ�
					}

					//-----�v�挤���t���O�`�F�b�N
					if (!"1".equals(keikaku)) {
						keikaku = "0";	//1�ȊO�̏ꍇ�A�O��ݒ�
					}
                    
                    // -----�O�N�x����t���O
                    if (!"1".equals(ZennendoOuboFlg)) {
                        ZennendoOuboFlg = "0";  //1�ȊO�̏ꍇ�A�O��ݒ�
                    }
					
					//-----���l�`�F�b�N
					if (!this.checkLenB(biko, 200)) {
						String msg = i + "�s�� ���l�̒������s���ł��B";
						mtLog.warn(msg);
						//err_list.add(msg);
						err_flg = 1;
					}

					if (err_flg == 0) {
						//KEY���ڂ̏d���`�F�b�N
						//���R Key�l�ɕ����ԍ����ǉ����ꂽ����
						if (cd_list.contains(ryoiki_no + komoku_no)) {
							String msg = i + "�s�� Key�l(�̈�ԍ�,�������ڔԍ�)�͏d�����Ă��܂��B";
							mtLog.warn(msg);
							//err_list.add(msg);
							err_flg = 1;
						} else {
							cd_list.add(ryoiki_no + komoku_no);
						}
					}
					//�ݒ���ԊJ�n�N�x,�ݒ���ԁi�I���N�x�j�`�F�b�N
                    if (!StringUtil.isBlank(SettelKikanKaishi)){
                        if (!this.checkNum(SettelKikanKaishi)) {
                            //���p�����Ŗ����ꍇ
                            String msg = i + "�s�� �ݒ����(�J�n�N�x)�͔��p�����ł͂���܂���B";
                            mtLog.warn(msg);
                            err_flg = 1;
                        } else if (!this.checkLenB(SettelKikanKaishi, 2)) {
                            String msg = i + "�s�� �ݒ����(�J�n�N�x)�̒������s���ł��B";
                            mtLog.warn(msg);
                            //err_list.add(msg);
                            err_flg = 1;
                        }
                    }
                    if (!StringUtil.isBlank(SettelKikanShuryo)){
                        if (!this.checkNum(SettelKikanShuryo)) {
                            //���p�����Ŗ����ꍇ
                            String msg = i + "�s�� �ݒ���ԁi�I���N�x�j�͔��p�����ł͂���܂���B";
                            mtLog.warn(msg);
                            err_flg = 1;
                        } else if (!this.checkLenB(SettelKikanShuryo, 2)) {
                            String msg = i + "�s�� �ݒ���ԁi�I���N�x�j�̒������s���ł��B";
                            mtLog.warn(msg);
                            //err_list.add(msg);
                            err_flg = 1;
                        }
                    }
                    if(!StringUtil.isBlank(SettelKikanKaishi)&&!StringUtil.isBlank(SettelKikanShuryo)&&err_flg != 1){
                       int kaishi=Integer.parseInt(SettelKikanKaishi) ;
                       int shuryo=Integer.parseInt(SettelKikanShuryo) ;
                       if(kaishi>shuryo){
                           String msg = i + "�s�� �ݒ���ԁi�I���N�x�j�ɕs���ȓ��t�͈̔͂��w�肳��Ă��܂��B";
                           mtLog.warn(msg);
                           err_flg = 1;  
                       }
                       
                    }
					//�G���[�����������ꍇ�̂ݓo�^����
					if (err_flg == 0) {
						//DB�ɓo�^����
						RyouikiInfo info = new RyouikiInfo();
						info.setRyoikiNo(ryoiki_no);
						info.setRyoikiName(ryoiki_name);
						info.setKomokuNo(komoku_no);
						info.setKobou(kobou);
						info.setKeikaku(keikaku);
                        //update start liuyi 2006/06/30
                        info.setZennendoOuboFlg(ZennendoOuboFlg);
                        info.setSettelKikanKaishi(SettelKikanKaishi);
                        info.setSettelKikanShuryo(SettelKikanShuryo);
                        String s="����"+SettelKikanKaishi+"�N�x�`����"+SettelKikanShuryo+"�N�x";
                        info.setSettelKikan(s);
						info.setBiko(biko);
                        //update end liuyi 2006/06/30 
						ryoikiDao.insertRyoikiInfo(connection, info);
						cnt++; //��荞�݌������J�E���g
					} else {
						//�f�[�^�ɕs�������邽�߁A�o�^���s��Ȃ��i�s�����m�ۂ���j
						err_list.add(Integer.toString(i));
					}
				}
			}

			//�}�X�^�Ǘ��e�[�u���̍X�V
			MasterKanriInfo mkInfo = new MasterKanriInfo();
			mkInfo.setMasterShubetu(MASTER_RYOIKI);
			mkInfo.setMasterName("�̈�}�X�^");
			mkInfo.setImportDate(new Date());
			mkInfo.setKensu(Integer.toString(cnt));
			mkInfo.setImportTable(table_name);
			mkInfo.setImportFlg("0");
			mkInfo.setCsvPath(csvPath);
			mkInfo.setImportMsg(null); //�g�p���ĂȂ��H
			mkInfo.setImportErrMsg(err_list); //�G���[���i��ʂŎg�p�j

			dao.update(connection, mkInfo);
			success = true;
			return mkInfo;

		} catch (IOException e) {
			throw new ApplicationException("CSV�t�@�C���̓Ǎ����ɃG���[���������܂����B", new ErrorInfo(
					"errors.7002"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("�}�X�^�捞����DB�G���[���������܂����B", new ErrorInfo(
					"errors.4001"), e);
		} finally {
			try {
				if (success) {
					//�R�~�b�g
					DatabaseUtil.commit(connection);
				} else {
					//���[���o�b�N
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException("�}�X�^�捞����DB�G���[���������܂����B", new ErrorInfo(
						"errors.4001"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

}
