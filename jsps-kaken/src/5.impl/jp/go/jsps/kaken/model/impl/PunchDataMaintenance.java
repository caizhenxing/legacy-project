/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���iJSPS)
 *    Source name : PunchDataMaintenance.java
 *    Description : �p���`�f�[�^�Ǘ�
 *
 *    Author      : Yuji Kainuma
 *    Date        : 2004/11/01
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/11/01    V1.0                       �V�K�쐬
 *    2005/05/31    V1.1        ���@�@�@�@�@�@�@����̈�p���`�f�[�^�o�͒ǉ�
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.go.jsps.kaken.model.IPunchDataMaintenance;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.PunchKanriInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.PunchDataKanriInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;

/**
 * �p���`�f�[�^�쐬�Ǘ��N���X. <br />
 * <br />
 * 
 * �p���`�f�[�^�̍쐬�A�ۊǂȂǂ��s���B <br />
 * <br />
 * 
 * �g�p���Ă���e�[�u�� <br />
 * <br />
 * �E�p���`�f�[�^�Ǘ��e�[�u��:PUNCHKANRI <br />
 * �o�͂���p���`�f�[�^�����Ǘ�����B <br />
 * <br />
 * �E�\���f�[�^�Ǘ��e�[�u��:SHINSEIDATAKANRI <br />
 * �\���f�[�^�̏����Ǘ�����B <br />
 * <br />
 * �E�����g�D�\�Ǘ��e�[�u��:KENKYUSOSHIKIKANRI <br />
 * �\���f�[�^�̌����g�D�\�����Ǘ�����B <br />
 * <br />
 * �E�R�����ʃe�[�u��:SHINSAKEKKA <br />
 * �R��������U�茋�ʏ��Ɛ\�����̐R�����ʂ��Ǘ�����B <br />
 * <br />
 */
public class PunchDataMaintenance implements IPunchDataMaintenance {

	//	---------------------------------------------------------------------
	//	 Static data
	//	---------------------------------------------------------------------

	/**
	 * �p���`�f�[�^�ۊǐ�. <br />
	 * <br />
	 * ��̓I�Ȓl�́A" <b>${shinsei_path}/punchdata/{0} </b>"
	 */
	private final static String PUNCHDATA_HOKAN_LOCATION =
				ApplicationSettings.getString(ISettingKeys.PUNCHDATA_HOKAN_LOCATION);

	/**
	 * �p���`�f�[�^���̎擾. <br />
	 * <br />
	 * 
	 * �p���`�f�[�^���̈ꗗ��List�Ŏ擾����B���s����SQL���́A�ȉ��̒ʂ�B <br />
	 * <br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  
	 *  SELECT
	 *  	A.PUNCH_SHUBETU				--�p���`�f�[�^���
	 *  	,A.PUNCH_NAME				--�p���`�f�[�^����
	 *  	,A.JIGYO_KUBUN				--���Ƌ敪
	 *  	,TO_CHAR
	 *  		(A.SAKUSEI_DATE,'YYYY/MM/DD HH24:MI:SS')
	 *  		SAKUSEI_DATE			--�쐬����
	 *  	,A.PUNCH_PATH				--�p���`�f�[�^�t�@�C���p�X
	 *  FROM
	 *  	PUNCHKANRI A
	 *  WHERE
	 *  	A.JIGYO_KUBUN IN (&quot;&lt;b&gt;�S�����Ƌ敪&lt;/b&gt;&quot;) (��)
	 *  ORDER BY
	 *  	A.PUNCH_SHUBETU
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> <br />
	 * 
	 * (��)�Ɩ��S���҂̏ꍇ�͎������S�����鎖�Ƌ敪�݂̂Ȃ̂ŁA���̏�����������B " <b>�S�����Ƌ敪
	 * </b>"�ɂ́A����userInfo������GyomutantoInfo�̕ϐ�TantoJigyoKubun�̒l��","���͂���ł��ׂĕ��ԁB
	 * <br />
	 * <br />
	 * 
	 * �擾�����l�́A�񖼂��L�[�ɂ���Map�Ɋi�[�����B����Map��List�Ɋi�[����A�ԋp�����B
	 * 
	 * @param userInfo UserInfo
	 * @return �p���`�f�[�^���̈ꗗ������List
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IPunchDataMaintenance#selectList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public List selectList(UserInfo userInfo) throws ApplicationException
	{

		//PunchKanriDataInfoDao��new����
		PunchKanriInfoDao punchdatainfodao = new PunchKanriInfoDao(userInfo);

		//DB�R�l�N�V�������擾����
		Connection connection = null;

		//���X�g�I�u�W�F�N�g���쐬����
		List result = null;

		try {
			connection = DatabaseUtil.getConnection();
			//List�I�u�W�F�N�g���擾����
			result = punchdatainfodao.selectList(connection, userInfo);
			//List�I�u�W�F�N�g��return����
			return result;
		} finally {
			//DB�R�l�N�V���������
			DatabaseUtil.closeConnection(connection);
		}

	}

	/**
	 * �p���`�Ǘ����̎擾. <br />
	 * <br />
	 * 
	 * �p���`�Ǘ����̎擾���s���B���s����SQL���͈ȉ��̒ʂ�B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  SELECT
	 *  	A.PUNCH_SHUBETU			--�p���`�f�[�^���
	 *  	,A.PUNCH_NAME			--�p���`�f�[�^����
	 *  	,A.JIGYO_KUBUN			--���Ƌ敪
	 *  	,A.SAKUSEI_DATE			--�쐬����
	 *  	,A.PUNCH_PATH			--�p���`�f�[�^�t�@�C���p�X
	 *  FROM
	 *  	PUNCHKANRI A
	 *  WHERE
	 *  	PUNCH_SHUBETU = ?
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> <br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
	 * width="600">
	 * <tr style="color:white;font-weight: bold">
	 * <td>��</td>
	 * <td>�l</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�f�[�^���</td>
	 * <td>������punchShubetu���g�p����B</td>
	 * </tr>
	 * </table> <br />
	 * 
	 * �擾�����f�[�^����" <b>PunchPath </b>"�̒l����A�p���`�f�[�^�t�@�C�����擾����B
	 * ���̃t�@�C����ǂݍ���ŁAFileResource��ԋp����B
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param punchShubetu �쐬����p���`�f�[�^�̎��
	 * @return FileResource
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IPunchDataMaintenance#getPunchDataResource(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public FileResource getPunchDataResource(
				UserInfo userInfo,
				String punchShubetu) throws ApplicationException 
	{

		//PunchKanriInfoDao��new����
		PunchKanriInfoDao punchkanriinfodao = new PunchKanriInfoDao(userInfo);

		//���[�U���I������PunchDataKanriInfo�����
		PunchDataKanriInfo punchdatakanriInfo = null;

		//DB�R�l�N�V�������擾����
		Connection connection = null;

		try {
			connection = DatabaseUtil.getConnection();
			punchdatakanriInfo = punchkanriinfodao.selectPunchKanriInfo(connection, punchShubetu);

		} catch (ApplicationException e) {
			throw new ApplicationException("�p���`�f�[�^�������ɂ�DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"), e);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ApplicationException("�p���`�f�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"), e);
		} finally {
			//DB�R�l�N�V���������
			DatabaseUtil.closeConnection(connection);
		}

		//PunchDataKanriInfo����PUNCH_PATH���擾����
		String path = punchdatakanriInfo.getPunchPath();

		//Get����PUNCH_PATH�ɂ���p���`�f�[�^���擾����
		File file = new File(path);

		FileResource fileResource = null;
		try {
			fileResource = FileUtil.readFile(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw new ApplicationException("�w�肵���t�@�C����������܂���Bpath=" + path,
					new ErrorInfo("errors.7004"), e1);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new ApplicationException("�o���̓G���[�B", new ErrorInfo(
					"errors.appliaction"), e1);
		}

		//�擾�����p���`�f�[�^�t�@�C����PunchDataOutAction�N���X�ɕԂ�
		return fileResource;
	}

	/**
	 * �p���`�f�[�^�̐V�K�쐬. <br />
	 * <br />
	 * 
	 * ������punchShubetu�ɂ���āA�쐬����t�@�C���̎�ނ��ς��B�Ăяo�����\�b�h�́A���ׂĎ����\�b�h�B <br />
	 * <br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
	 * width="600">
	 * <tr style="color:white;font-weight: bold">
	 * <td>punchShubetu</td>
	 * <td>�쐬����t�@�C��</td>
	 * <td>�Ăяo�����\�b�h</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>1</td>
	 * <td>�w�p�n�����������p���`�f�[�^�t�@�C��</td>
	 * <td>getPunchDataGakuso(userInfo)</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>2</td>
	 * <td>���ʐ��i����������p���`�f�[�^�t�@�C��</td>
	 * <td>getPunchDataTokusui(userInfo)</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>3</td>
	 * <td>��Ռ�����������p���`�f�[�^�t�@�C��</td>
	 * <td>getPunchDataKibanKenkyu(userInfo)</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>4</td>
	 * <td>����̈挤��(�p���̈�)������p���`�f�[�^�t�@�C��</td>
	 * <td>getPunchDataTokutei(userInfo)</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>5</td>
	 * <td>��Ռ������]��\�p���`�f�[�^�t�@�C��</td>
	 * <td>getPunchDataKibanHyotei(userInfo)</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>���̑�</td>
	 * <td>null��Ԃ��B</td>
	 * <td>�Ȃ�</td>
	 * </tr>
	 * </table> <br />
	 * 
	 * �t�@�C�����쐬��A�쐬�����p���`�f�[�^��������PunchDataKanriInfo��ԋp����B
	 * 
	 * �Ȃ��A�Q�Ɛ�̃��\�b�h���Ŕ��s�����SQL�����́A " <b>AND TO_NUMBER(JOKYO_ID) IN
	 * (6,8,9,10,11,12) </b>"�́A�e�󋵂ɂ��ẮA <br />
	 * <br />
	 * �E6�c�w�U�� <br />
	 * �E8�c�R��������U�菈���� <br />
	 * �E9�c����U��`�F�b�N���� <br />
	 * �E10�c�P���R���� <br />
	 * �E11�c�P���R���F���� <br />
	 * �E12�c�Q���R������ <br />
	 * <br />
	 * 
	 * �Ƃ����󋵂̂��̂ł���B
	 * 
	 * @param userInfo UserInfo
	 * @param punchShubetu �쐬����p���`�f�[�^�̎��
	 * @return �쐬�����p���`�f�[�^��������PunchDataKanriInfo
	 * @throws ApplicationException
	 */

	public PunchDataKanriInfo getPunchData(UserInfo userInfo,
			String punchShubetu) throws ApplicationException {

		//��ʂɂ���ČĂяo��������U�蕪����
		if (punchShubetu.equals("1")) {
			//�w�p�n�����������p���`�f�[�^
			return getPunchDataGakuso(userInfo);
		}
		else if (punchShubetu.equals("2")) {
			//���ʐ��i����������p���`�f�[�^
			return getPunchDataTokusui(userInfo);
		}
		else if (punchShubetu.equals("3")) {
			//��Ռ�����������p���`�f�[�^
			return getPunchDataKibanKenkyu(userInfo);
		}
		else if (punchShubetu.equals("4")) {
			//����̈挤��(�p���̈�)����J�[�h�p���`�f�[�^
			return getPunchDataTokuteiKenzokuChosho(userInfo);
		}
		else if (punchShubetu.equals("5")) {
			//��Ռ������]��\�p���`�f�[�^
			return getPunchDataKibanHyotei(userInfo);
		}
//2006/04/11 �ǉ���������		
		else if (punchShubetu.equals("6")) {
			//��茤���i�X�^�[�g�A�b�v�j������p���`�f�[�^
			return getPunchDataWakastartKenkyu(userInfo);
		}
		else if (punchShubetu.equals("7")) {
			//���ʌ������i�����p���`�f�[�^
			return getPunchDataSokushinKenkyu(userInfo);
		}
//�c�@�ǉ������܂�
        
//2006/05/25 �ǉ���������
        else if (punchShubetu.equals("8")){
            //��茤���i�X�^�[�g�A�b�v�j�]��\�p���`�f�[�^
            return getPunchDataWakateHyotei(userInfo);
        }
        else if (punchShubetu.equals("9")){
            //���ʌ������i��]��\�p���`�f�[�^
            return getPunchDataSokushinHyotei(userInfo);
        }
//�c�@�ǉ������܂� 
        
//2006/06/20 �c�@�ǉ���������
        else if (punchShubetu.equals("10")){
            //����̈挤��(�V�K�̈�)������p���`�f�[�^
            return getPunchDataTokuteiSinnkiChosho(userInfo);
        }
        else if (punchShubetu.equals("11")){
            //����̈挤���i�V�K�̈�j�̈�v�揑�T�v�p���`�f�[�^
        	//Modify By Sai 2006.09.19 Start
            //return getPunchDataTokuteiSinnkiRyoiki(userInfo);
            return searchCsvData(userInfo);
//          Modify By Sai 2006.09.19 End
        }
//2006/06/20�@�c�@�ǉ������܂�        
		else {
			return null;
		}
	}

	/**
	 * ���ʐ��i�ۑ薼�J�[�h�p���`�f�[�^�t�@�C�����쐬. <br />
	 * <br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�擾�����l�����ׂĘA������1�̕�������쐬����B <br />
	 * <br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  SELECT
	 *  	A.SHOZOKU_CD                   		--�����@�փR�[�h(5�o�C�g)
	 *  	,NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER
	 *  		--�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
	 *  	,RPAD(NVL(A.KADAI_NAME_KANJI,' '),162,'�@') KADAINAMEKANJI
	 *  		--�����ۑ薼(�a��)(162�o�C�g)
	 *  FROM
	 *  	SHINSEIDATAKANRI A
	 *  WHERE
	 *  	DEL_FLG = 0
	 *  	AND JIGYO_KUBUN = IJigyoKubun.JIGYO_KUBUN_TOKUSUI(��)
	 *  	AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)
	 *  ORDER BY
	 *  	A.JIGYO_ID, A.UKETUKE_NO		--����ID,�\���ԍ�(�@�֐����ԍ�)
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> (��)" <b>IJigyoKubun.JIGYO_KUBUN_TOKUSUI </b>"�̒l��3�B <br />
	 * <br />
	 * 
	 * �쐬������������o�C�g�̔z��ɂ��Ċi�[����FileResource���쐬���A �����\�b�h��" <b>kakuno(FileResource,
	 * String "TOKUSUI-KADAI") </b>"�ɂāA�T�[�o�Ɋi�[����B <br />
	 * <br />
	 * 
	 * �p���`�f�[�^�Ǘ�info�Ƀp���`�f�[�^�����i�[���A���̏������Ƀp���`�f�[�^�Ǘ��e�[�u���̍X�V���s���B
	 * �쐬����SQL���͈ȉ��̒ʂ�B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��) <br />
	 * <br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  
	 *  UPDATE
	 *  		PUNCHKANRI
	 *  SET
	 *  		PUNCH_SHUBETU = ?
	 *  		,PUNCH_NAME = ?
	 *  		,JIGYO_KUBUN = ?
	 *  		,SAKUSEI_DATE = SYSDATE
	 *  		,PUNCH_PATH = ?
	 *  WHERE
	 *  		PUNCH_SHUBETU = ?
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> <br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
	 * width="600">
	 * <tr style="color:white;font-weight: bold">
	 * <td>��</td>
	 * <td>�l</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�f�[�^���</td>
	 * <td>2</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�f�[�^����</td>
	 * <td>"���ʐ��i�ۑ薼�p���`�f�[�^"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>���Ƌ敪</td>
	 * <td>3</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�p���`�f�[�^�t�@�C���p�X</td>
	 * <td>�i�[�����p���`�f�[�^�t�@�C���̐�΃p�X</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�f�[�^���</td>
	 * <td>2</td>
	 * </tr>
	 * </table> <br />
	 * 
	 * ���ʐ��i�����p���`�f�[�^�Ǘ�info��ԋp����B
	 * 
	 * @param userInfo UserInfo
	 * @return �������ʏ�������PunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataTokusui(UserInfo userInfo)
			throws ApplicationException
	{

		//DB�R�l�N�V�������擾����
		Connection connection = null;

		boolean success = false;

		//String�I�u�W�F�N�g���쐬����
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
			String tableName = "TOKUSUI-OUBO";

			//�p���`�f�[�^�쐬�ɕK�v��DAO��new����
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//List����������String���擾����
			punchResult = punchKanriInfodao.punchDataTokushi(connection);

			//FileResource���쐬
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//�i�[���\�b�h���Ă�
			String punchPath = kakuno(punchFileResource, tableName);

			//�p���`�f�[�^�Ǘ�info��new����
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//�p���`�f�[�^�Ǘ��e�[�u�����X�V����
			punchDataKanriinfo.setPunchShubetu("2");
			punchDataKanriinfo.setPunchName("���ʐ��i����������p���`�f�[�^");
			punchDataKanriinfo.setJigyoKubun("3");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB�X�V���\�b�h���Ăт���
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//���ʂ���return����
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
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
				throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �w�p�n�����������p���`�f�[�^�t�@�C�����쐬. <br />
	 * <br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�擾�����l�����ׂĘA������1�̕�������쐬����B <br />
	 * <br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  SELECT
	 *  	NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER
	 *  			--�����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
	 *  	,A.SHOZOKU_CD SHOZOKU_CD_DAIHYO		--�����@�փR�[�h(5�o�C�g)
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '       ' ELSE LPAD(A.KEIHI1,7,'0') END KEIHI1
	 * 			--1�N�ڌ����o��(7�o�C�g)
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '       ' ELSE LPAD(A.KEIHI2,7,'0') END KEIHI2
	 * 			--2�N�ڌ����o��(7�o�C�g)
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '       ' ELSE LPAD(A.KEIHI3,7,'0') END KEIHI3
	 * 			--3�N�ڌ����o��(7�o�C�g)
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '       ' ELSE LPAD(A.KEIHI4,7,'0') END KEIHI4
	 * 			--4�N�ڌ����o��(7�o�C�g)
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '       ' ELSE LPAD(A.KEIHI5,7,'0') END KEIHI5
	 * 			--5�N�ڌ����o��(7�o�C�g)
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN ' ' ELSE A.KEI_NAME_NO END KEINAMENO
	 * 			--�n���̋敪�ԍ��i1�o�C�g�j
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '    ' ELSE A.BUNKASAIMOKU_CD END BUNKASAIMOKUCD
	 * 			--�זڃR�[�h�i4�o�C�g�j
	 * 
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN
	 * 		'�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@�@'
	 * 		ELSE RPAD(A.KADAI_NAME_KANJI,80,'�@') END KADAINAMEKANJI
	 * 			--�����ۑ薼�i�a���j�i80�o�C�g�j
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN '  ' ELSE LPAD(A.KENKYU_NINZU,2,' ') END KENKYUNINZU
	 * 			--�����Ґ��i2�o�C�g�j
	 *  	,CASE WHEN B.BUNTAN_FLG = '2'
	 * 		THEN ' ' ELSE LPAD(A.BUNTANKIN_FLG,1,' ') END BUNTANKINFLG
	 * 			--���S���̗L��
	 *  	,NVL(B.BUNTAN_FLG,' ') BUNTANFLG	--��\�ҕ��S�ҕ�(1�o�C�g)
	 *  	,LPAD(B.SHOZOKU_CD,5,' ') SHOZOKUCD
	 *  			--�����@�֖�(�R�[�h)�����g�D�\�Ǘ��e�[�u�����(5�o�C�g)
	 *  	,LPAD(B.BUKYOKU_CD,3,' ') BUKYOKUCD	--���ǖ�(�R�[�h)(3�o�C�g)
	 *  	,LPAD(B.SHOKUSHU_CD,2,' ') SHOKUSHUCD--�E���R�[�h(2�o�C�g)
	 *  	,LPAD(B.KENKYU_NO, 8,' ')  KENKYUNO	--�����Ҕԍ�(8�o�C�g)
	 *  	,B.NAME_KANA_SEI NAMEKANASEI   	--�����i���j�t���K�i(32�o�C�g�A16�����j
	 *  	,B.NAME_KANA_MEI NAMEKANAMEI	--�����t���K�i(�R���}���܂�16�����j
	 *  FROM
	 *  	SHINSEIDATAKANRI A, KENKYUSOSHIKIKANRI B
	 *  WHERE
	 *  	DEL_FLG = 0&quot;
	 *  	AND A.SYSTEM_NO = B.SYSTEM_NO
	 *  	AND JIGYO_KUBUN = IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO(��)
	 *  	AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)
	 *  ORDER BY
	 *  	A.JIGYO_ID, A.UKETUKE_NO, B.SEQ_NO
	 *  			--����ID,�\���ԍ�(�@�֐����ԍ�),�����g�D�\�A��
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> (��)" <b>IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO </b>"�̒l��1�B
	 * <br />
	 * <br />
	 * 
	 * �쐬������������o�C�g�̔z��ɂ��Ċi�[����FileResource���쐬���A �����\�b�h��" <b>kakuno(FileResource,
	 * String "GAKUSO-SUISEN-SOSHIKI") </b>"�ɂāA�T�[�o�Ɋi�[����B <br />
	 * <br />
	 * 
	 * �p���`�f�[�^�Ǘ�info�Ƀp���`�f�[�^�����i�[���A���̏������Ƀp���`�f�[�^�Ǘ��e�[�u���̍X�V���s���B
	 * �쐬����SQL���͈ȉ��̒ʂ�B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��) <br />
	 * <br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  
	 *  UPDATE
	 *  		PUNCHKANRI
	 *  SET
	 *  		PUNCH_SHUBETU = ?
	 *  		,PUNCH_NAME = ?
	 *  		,JIGYO_KUBUN = ?
	 *  		,SAKUSEI_DATE = SYSDATE
	 *  		,PUNCH_PATH = ?
	 *  WHERE
	 *  		PUNCH_SHUBETU = ?
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> <br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
	 * width="600">
	 * <tr style="color:white;font-weight: bold">
	 * <td>��</td>
	 * <td>�l</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�f�[�^���</td>
	 * <td>4</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�f�[�^����</td>
	 * <td>"�w�p�n��(���E��)�g�D�\�p���`�f�[�^"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>���Ƌ敪</td>
	 * <td>1</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�p���`�f�[�^�t�@�C���p�X</td>
	 * <td>�i�[�����p���`�f�[�^�t�@�C���̐�΃p�X</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�f�[�^���</td>
	 * <td>4</td>
	 * </tr>
	 * </table> <br />
	 * 
	 * �p���`�f�[�^�Ǘ�info��ԋp����B
	 * 
	 * @param userInfo UserInfo
	 * @return �������ʏ�������PunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataGakuso(UserInfo userInfo)
			throws ApplicationException
	{

		//DB�R�l�N�V�������擾����
		Connection connection = null;

		boolean success = false;

		//String�I�u�W�F�N�g���쐬����
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
			String tableName = "GAKUSO-OUBO";

			//�p���`�f�[�^�쐬�ɕK�v��DAO��new����
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//List����������String���擾����
			punchResult = punchKanriInfodao.punchDataGakuso(connection);

			//FileResource���쐬
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//�i�[���\�b�h���Ă�
			String punchPath = kakuno(punchFileResource, tableName);

			//�p���`�f�[�^�Ǘ�info��new����
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//�p���`�f�[�^�Ǘ��e�[�u�����X�V����
			punchDataKanriinfo.setPunchShubetu("1");
			punchDataKanriinfo.setPunchName("�w�p�n�����������p���`�f�[�^");
			punchDataKanriinfo.setJigyoKubun("1");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB�X�V���\�b�h���Ăт���
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//���ʂ���return����
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
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
				throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �]��\�p���`�f�[�^�t�@�C�����쐬. <br />
	 * <br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�擾�����l�����ׂĘA������1�̕�������쐬����B <br />
	 * <br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  SELECT
	 *  	NVL(SUBSTR(B.UKETUKE_NO,1,5),'    ') SHOZOKUCDDAIHYO
	 *  			--�����@�փR�[�h(5�o�C�g)
	 *  
	 *  	,NVL(SUBSTR(B.UKETUKE_NO,7,4),'    ') BUNKASAIMOKUCD	--�����ԍ�(4�o�C�g)
	 *  	,NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO
	 *  			--�R�����}��(�R�����ԍ��̉��Q��)(2�o�C�g)
	 *  
	 *  	,NVL(B.KEKKA_TEN, ' ') KEKKATEN			--�����]��
	 *  	,NVL(B.KENKYUNAIYO, ' ') KENKYUNAIYO			--�������e
	 *  	,NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU		--�����v��
	 *  	,CASE WHEN B.TEKISETSU_KENKYU1 = '0'
	 *  		THEN ' ' ELSE NVL(B.TEKISETSU_KENKYU1, ' ')
	 *  		END TEKISETSUKENKYU1			--���S���]��
	 *  	,CASE WHEN B.TEKISETSU_KAIGAI = '0'
	 *  		THEN ' ' ELSE NVL(B.TEKISETSU_KAIGAI,' ')
	 *  		END TEKISETSUKAIGAI			--�K�ؐ��C�O
	 *  	,CASE WHEN B.DATO = '0'
	 *  		THEN ' ' ELSE NVL(B.DATO, ' ') END DATO	--�o��̑Ó���
	 *  	,CASE WHEN B.SHINSEISHA = '1'
	 *  		THEN '1' ELSE ' ' END DAIHYOSHAKUBUN		--��\�敪
	 *  	,CASE WHEN B.SHINSEISHA = '2'
	 *  		THEN '1' ELSE ' '  END BUNTANSHAKUBUN	--���S�ҋ敪
	 *  	,CASE WHEN B.SHINSEISHA = '3'
	 *  		THEN '1' ELSE ' '  END KANKEISHAKUBUN	--�֌W�ҋ敪
	 *  	,CASE WHEN B.COMMENTS IS NULL
	 *  		THEN '�@' ELSE TO_MULTI_BYTE(B.COMMENTS)
	 *  		END COMMENTS				--�R�����g
	 *  	,CASE WHEN B.SHINSA_JOKYO IS NULL
	 *  		THEN ' ' ELSE B.SHINSA_JOKYO
	 *  		END SHINSAJOKYO				--�R����	
	 *  FROM
	 *  	SHINSEIDATAKANRI A, SHINSAKEKKA B
	 *  WHERE
	 *  	A.DEL_FLG = 0
	 *  	AND A.JIGYO_KUBUN = B.JIGYO_KUBUN
	 *  	AND A.SYSTEM_NO = B.SYSTEM_NO
	 *  	AND A.JIGYO_KUBUN = IJigyoKubun.JIGYO_KUBUN_KIBAN
	 *  	AND TO_NUMBER(JOKYO_ID) IN (6,8,9,10,11,12)
	 *  	AND NOT B.SHINSAIN_NO LIKE '@%'
	 *  ORDER BY
	 *  	SHOZOKUCDDAIHYO,BUNKASAIMOKUCD, SHINSAINNO
	 *  			--�����@�փR�[�h�A�����ԍ��A�R�����}��
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> (��)" <b>IJigyoKubun.JIGYO_KUBUN_KIBAN </b>"�̒l��4�B <br />
	 * <br />
	 * 
	 * �쐬������������o�C�g�̔z��ɂ��Ċi�[����FileResource���쐬���A �����\�b�h��" <b>kakuno(FileResource,
	 * String "HYOTEI") </b>"�ɂāA�T�[�o�Ɋi�[����B <br />
	 * <br />
	 * 
	 * �p���`�f�[�^�Ǘ�info�Ƀp���`�f�[�^�����i�[���A���̏������Ƀp���`�f�[�^�Ǘ��e�[�u���̍X�V���s���B
	 * �쐬����SQL���͈ȉ��̒ʂ�B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��) <br />
	 * <br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
	 * width="600">
	 * <tr bgcolor="#FFFFFF">
	 * <td>
	 * 
	 * <pre>
	 * 
	 *  
	 *  UPDATE
	 *  		PUNCHKANRI
	 *  SET
	 *  		PUNCH_SHUBETU = ?
	 *  		,PUNCH_NAME = ?
	 *  		,JIGYO_KUBUN = ?
	 *  		,SAKUSEI_DATE = SYSDATE
	 *  		,PUNCH_PATH = ?
	 *  WHERE
	 *  		PUNCH_SHUBETU = ?
	 * </pre>
	 * 
	 * </td>
	 * </tr>
	 * </table> <br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
	 * width="600">
	 * <tr style="color:white;font-weight: bold">
	 * <td>��</td>
	 * <td>�l</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�f�[�^���</td>
	 * <td>6</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�f�[�^����</td>
	 * <td>"�]��\�p���`�f�[�^"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>���Ƌ敪</td>
	 * <td>4</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�p���`�f�[�^�t�@�C���p�X</td>
	 * <td>�i�[�����p���`�f�[�^�t�@�C���̐�΃p�X</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>�f�[�^���</td>
	 * <td>6</td>
	 * </tr>
	 * </table> <br />
	 * 
	 * ��Ռ������]��\�p���`�f�[�^�Ǘ�info��ԋp����B
	 * 
	 * @param userInfo  UserInfo
	 * @return �������ʏ�������PunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataKibanHyotei(UserInfo userInfo)
			throws ApplicationException 
	{

		//DB�R�l�N�V�������擾����
		Connection connection = null;

		boolean success = false;

		//String�I�u�W�F�N�g���쐬����
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
			String tableName = "HYOTEI";

			//�p���`�f�[�^�쐬�ɕK�v��DAO��new����
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//List����������String���擾����
			punchResult = punchKanriInfodao.punchDataHyotei(connection);

			//FileResource���쐬
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//�i�[���\�b�h���Ă�
			String punchPath = kakuno(punchFileResource, tableName);

			//�p���`�f�[�^�Ǘ�info��new����
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//�p���`�f�[�^�Ǘ��e�[�u�����X�V����
			punchDataKanriinfo.setPunchShubetu("5");
			punchDataKanriinfo.setPunchName("��Ռ������]��\�p���`�f�[�^");
			punchDataKanriinfo.setJigyoKubun("4");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB�X�V���\�b�h���Ăт���
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//���ʂ���return����
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
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
				throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �t�@�C������t�𖼑O�ɂ��ăT�[�o�ɕۑ�����. <br />
	 * <br />
	 * 
	 * �o�͐�̃t�H���_�́A" <b>D:/shinsei-kaken/punchdata/�������̃e�[�u���� </b>" <br />
	 * �p���`�f�[�^�̃f�[�^���́A" <b>�������̃e�[�u����_�{���̓��t.txt </b>" <br />
	 * ���t�́A�t�H�[�}�b�g��"yyyyMMdd"�ŁAWAS���擾�B <br />
	 * <br />
	 * �������̃e�[�u�����ɂ��ẮA�ȉ��̕\���Q�ƁB <table border="0" cellspacing="1"
	 * cellpadding="1" bgcolor="#000000">
	 * <tr style="color:white;font-weight: bold">
	 * <td>punchShubetu</td>
	 * <td>�Q�Ɛ�̃��\�b�h</td>
	 * <td>�^������e�[�u����</td>
	 * <tr/>
	 * <tr bgcolor="#FFFFFF">
	 * <td>1</td>
	 * <td>getPunchDataTokusuiShinsei(userInfo)</td>
	 * <td>"TOKUSUI-CARD"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>2</td>
	 * <td>getPunchDataTokusuiKadai(userInfo)</td>
	 * <td>"TOKUSUI-KADAI"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>3</td>
	 * <td>getPunchDataTokusuiBuntansya(userInfo)</td>
	 * <td>"TOKUSUI-BUNTANSYA"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>4</td>
	 * <td>getPunchDataGakusoSuisen(userInfo)</td>
	 * <td>"GAKUSO-SUISEN-SOSHIKI"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>5</td>
	 * <td>getPunchDataGakusoBoshu(userInfo)</td>
	 * <td>"GAKUSO-BOSYU-SOSHIKI"</td>
	 * </tr>
	 * <tr bgcolor="#FFFFFF">
	 * <td>6</td>
	 * <td>getPunchDataHyotei(userInfo)</td>
	 * <td>"HYOTEI"</td>
	 * </tr>
	 * </table> <br />
	 * �p���`�f�[�^�t�@�C�����T�[�o�Ɋi�[���A���̃t�@�C���̐�΃p�X��String��ԋp����B
	 * 
	 * @param fileRes �A�b�v���[�h�t�@�C�����\�[�X�B
	 * @param tableName �ۑ���̃t�H���_��
	 * @return �i�[�����p���`�f�[�^�t�@�C���̐�΃p�X��String
	 * @throws ApplicationException �p���`�f�[�^�t�@�C���t�@�C���i�[�Ɏ��s�����ꍇ
	 */
	private String kakuno(FileResource fileRes, String tableName)
			throws ApplicationException 
	{
		//�o�͐�t�H���_
		String path = MessageFormat.format(PUNCHDATA_HOKAN_LOCATION, new String[] { tableName });
		File parent = new File(path);

		//�p���`�f�[�^�̖����K�����K��
		String name = new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".txt";
		name = tableName + "_" + name;
		fileRes.setPath(name);

		try {
			//�p���`�f�[�^�t�@�C�����T�[�o�Ɋi�[���郁�\�b�h���Ăяo��
			FileUtil.writeFile(parent, fileRes);
		} catch (IOException e) {
			throw new ApplicationException("�p���`�f�[�^�t�@�C���̊i�[���Ɏ��s���܂����B",
					new ErrorInfo("errors.7001"), e);
		}
		return new File(parent, name).getAbsolutePath();
	}
	/*
	 * @param fileRes �A�b�v���[�h�t�@�C�����\�[�X�B
	 * @param tableName(Csv) �ۑ���̃t�H���_��
	 * @return �i�[�����p���`�f�[�^�t�@�C���̐�΃p�X��String
	 * @throws ApplicationException �p���`�f�[�^�t�@�C���t�@�C���i�[�Ɏ��s�����ꍇ
	 */
	private String kakunoCsv(FileResource fileRes, String tableName)
			throws ApplicationException 
	{
		//�o�͐�t�H���_
		String path = MessageFormat.format(PUNCHDATA_HOKAN_LOCATION, new String[] { tableName });
		File parent = new File(path);

		//�p���`�f�[�^�̖����K�����K��
		String name = new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".csv";
		name = tableName + "_" + name;
		fileRes.setPath(name);

		try {
			//�p���`�f�[�^�t�@�C�����T�[�o�Ɋi�[���郁�\�b�h���Ăяo��
			FileUtil.writeFile(parent, fileRes);
		} catch (IOException e) {
			throw new ApplicationException("�p���`�f�[�^�t�@�C���̊i�[���Ɏ��s���܂����B",
					new ErrorInfo("errors.7001"), e);
		}
		return new File(parent, name).getAbsolutePath();
	}	

	/**
	 * ��Ռ����p���`�f�[�^�t�@�C�����쐬. <br />
	 * <br />
	 * 
	 * @param userInfo UserInfo
	 * @return �������ʏ�������PunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataKibanKenkyu(UserInfo userInfo)
			throws ApplicationException
	{

		//DB�R�l�N�V�������擾����
		Connection connection = null;

		boolean success = false;

		//String�I�u�W�F�N�g���쐬����
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
			String tableName = "GAKUSIN-OUBO";

			//�p���`�f�[�^�쐬�ɕK�v��DAO��new����
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//List����������String���擾����
			punchResult = punchKanriInfodao.punchDataKibanKenkyu(connection);

			//FileResource���쐬
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//�i�[���\�b�h���Ă�
			String punchPath = kakuno(punchFileResource, tableName);

			//�p���`�f�[�^�Ǘ�info��new����
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//�p���`�f�[�^�Ǘ��e�[�u�����X�V����
			punchDataKanriinfo.setPunchShubetu("3");
			punchDataKanriinfo.setPunchName("��Ռ�����������p���`�f�[�^");
			punchDataKanriinfo.setJigyoKubun("4");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB�X�V���\�b�h���Ăт���
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//���ʂ���return����
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
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
				throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		//return null;
	}

	/**
	 * ����̈挤��(�p��)����J�[�h�p���`�f�[�^�t�@�C�����쐬. <br />
	 * <br />
	 * 
	 * 
	 * @param userInfo
	 * @return �������ʏ�������PunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataTokuteiKenzokuChosho(UserInfo userInfo)
			throws ApplicationException 
	{

		//DB�R�l�N�V�������擾����
		Connection connection = null;

		boolean success = false;

		//String�I�u�W�F�N�g���쐬����
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
			String tableName = "TOKUTEI-CARD";

			//�p���`�f�[�^�쐬�ɕK�v��DAO��new����
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//List����������String���擾����
			punchResult = punchKanriInfodao.punchDataTokuteiKeiZokuChosho(connection);
			

			//FileResource���쐬
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//�i�[���\�b�h���Ă�
			String punchPath = kakuno(punchFileResource, tableName);

			//�p���`�f�[�^�Ǘ�info��new����
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//�p���`�f�[�^�Ǘ��e�[�u�����X�V����
			punchDataKanriinfo.setPunchShubetu("4");
			punchDataKanriinfo.setPunchName("����̈挤��(�p���̈�)����J�[�h�p���`�f�[�^");
			punchDataKanriinfo.setJigyoKubun("5");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB�X�V���\�b�h���Ăт���
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//���ʂ���return����
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
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
				throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}

		//return null;
	}
//2006/04/11 �ǉ���������
	/**
	 * ��茤���i�X�^�[�g�A�b�v�j�p���`�f�[�^�t�@�C�����쐬. <br />
	 * <br />
	 * 
	 * @param userInfo UserInfo
	 * @return �������ʏ�������PunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataWakastartKenkyu(UserInfo userInfo)
			throws ApplicationException
	{

		//DB�R�l�N�V�������擾����
		Connection connection = null;

		boolean success = false;

		//String�I�u�W�F�N�g���쐬����
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
			String tableName = "WAKASTARTUP-OUBO";

			//�p���`�f�[�^�쐬�ɕK�v��DAO��new����
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//List����������String���擾����
			punchResult = punchKanriInfodao.punchDataWakastartKenkyu(connection);

			//FileResource���쐬
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//�i�[���\�b�h���Ă�
			String punchPath = kakuno(punchFileResource, tableName);

			//�p���`�f�[�^�Ǘ�info��new����
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//�p���`�f�[�^�Ǘ��e�[�u�����X�V����
			punchDataKanriinfo.setPunchShubetu("6");
			punchDataKanriinfo.setPunchName("��茤���i�X�^�[�g�A�b�v�j������p���`�f�[�^");
			punchDataKanriinfo.setJigyoKubun("6");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB�X�V���\�b�h���Ăт���
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//���ʂ���return����
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
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
				throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		//return null;
	}
	
	/**
	 * ���ʌ������i�����p���`�f�[�^�t�@�C�����쐬. <br />
	 * <br />
	 * 
	 * @param userInfo UserInfo
	 * @return �������ʏ�������PunchDataKanriInfo
	 * @throws ApplicationException
	 */
	private PunchDataKanriInfo getPunchDataSokushinKenkyu(UserInfo userInfo)
			throws ApplicationException
	{

		//DB�R�l�N�V�������擾����
		Connection connection = null;

		boolean success = false;

		//String�I�u�W�F�N�g���쐬����
		String punchResult = null;

		try {
			connection = DatabaseUtil.getConnection();

			//�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
			String tableName = "SOKUSHIN-OUBO";

			//�p���`�f�[�^�쐬�ɕK�v��DAO��new����
			PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

			//List����������String���擾����
			punchResult = punchKanriInfodao.punchDataSokushinKenkyu(connection);

			//FileResource���쐬
			FileResource punchFileResource = new FileResource();
			punchFileResource.setBinary(punchResult.getBytes());

			//�i�[���\�b�h���Ă�
			String punchPath = kakuno(punchFileResource, tableName);

			//�p���`�f�[�^�Ǘ�info��new����
			PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

			//�p���`�f�[�^�Ǘ��e�[�u�����X�V����
			punchDataKanriinfo.setPunchShubetu("7");
			punchDataKanriinfo.setPunchName("���ʌ������i�����p���`�f�[�^");
			punchDataKanriinfo.setJigyoKubun("7");
			punchDataKanriinfo.setSakuseiDate(new Date());
			punchDataKanriinfo.setPunchPath(punchPath);

			//DB�X�V���\�b�h���Ăт���
			punchKanriInfodao.update(connection, punchDataKanriinfo);

			success = true;

			//���ʂ���return����
			return punchDataKanriinfo;

		} catch (NoDataFoundException e) {
			throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4010"), e);
		} catch (ApplicationException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
		} catch (DataAccessException e) {
			throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
					new ErrorInfo("errors.4000"), e);
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
				throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
						new ErrorInfo("errors.4000"), e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		//return null;
	}

//�c�@�ǉ������܂�	
    
//2006/05/25 �ǉ���������
    /**
     * ��茤���i�X�^�[�g�A�b�v�j�]��\�p���`�f�[�^�t�@�C�����쐬. <br />
     * <br />
     * 
     * �ȉ���SQL���𔭍s���āA�擾�����l�����ׂĘA������1�̕�������쐬����B <br />
     * <br />
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
     * width="600">
     * <tr bgcolor="#FFFFFF">
     * <td>
     * 
     * <pre>
     * 
     *    SELECT 
     *         '14' KENKYUSHUMOKU       ������ڔԍ��i2�o�C�g)  
     *         ,'1' SHINSA_KUBUN        �R���敪(1�o�C�g)  
     *         ,NVL(SUBSTR(B.UKETUKE_NO,1,5),'     ') SHOZOKUCDDAIHYO"    �����@�փR�[�h(5�o�C�g)
     *         ,NVL(A.BUNKASAIMOKU_CD,'    ') BUNKASAIMOKUCD"             �זڃR�[�h�i4�o�C�g�j
     *         ,CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
     *               WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"    �����ԍ��i1�o�C�g�j
     *         ,' ' BUNKATSUNO_12"                                                    �����ԍ��i1�o�C�g�j
     *         ,NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER"                     �����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
     *         ,NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO"                      �R�����}��(�R�����ԍ��̉��Q���j�i2�o�C�g�j
     *         ,CASE WHEN B.RIGAI = '1' THEN '1' ELSE ' '  END RIGAIKANKEI"           ���Q�֌W
     *         ,NVL(B.JUYOSEI, ' ') JUYOSEI"                                          �����ۑ�̊w�p�I�d�v��
     *         ,NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU"                              �����v��E���@�̑Ó���
     *         ,NVL(B.DOKUSOSEI, ' ') DOKUSOSEI"                   �Ƒn���y�ъv�V��
     *         ,NVL(B.HAKYUKOKA, ' ') HAKYUKOKA"                   �g�y����
     *         ,NVL(B.SUIKONORYOKU, ' ') SUIKONORYOKU"             ���s�\��
     *         ,CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '111') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_HOGA"           �K�ؐ��E�G��
     *         ,CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '043')OR(SUBSTR(A.JIGYO_ID,5,3) = '053') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_KAIGAI"  �K�ؐ��E�G��
     *         ,CASE WHEN B.KEKKA_TEN = '-' THEN ' ' ELSE NVL(B.KEKKA_TEN, ' ') END KEKKATEN"   �����]��
     *         ,CASE WHEN B.JINKEN = '3' THEN '3' ELSE ' ' END JINKEN"            �l��
     *         ,CASE WHEN B.BUNTANKIN = '3' THEN '3' ELSE ' ' END BUNTANKIN"      ���S��
     *         ,CASE WHEN B.DATO = '0' THEN ' ' ELSE NVL(B.DATO, ' ') END DATO"   �o��̑Ó���
     *         ,CASE WHEN B.COMMENTS IS NULL THEN '�@' ELSE TO_MULTI_BYTE(B.COMMENTS) END COMMENTS"    �R���ӌ�
     *         ,CASE WHEN B.OTHER_COMMENT IS NULL THEN '�@' ELSE TO_MULTI_BYTE(B.OTHER_COMMENT) END OTHER_COMMENT"   �R�����g
     *         ,NVL(B.SHINSA_JOKYO, '0') SHINSAJOKYO"     �R����
     *         FROM SHINSEIDATAKANRI A, SHINSAKEKKA B"
     *         WHERE B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_WAKATESTART
     *               AND NOT B.SHINSAIN_NO LIKE '@%'"     �_�~�[�R���������O����(�_�~�[�R�����ԍ������t���Ă�)
     *               AND A.SYSTEM_NO = B.SYSTEM_NO" 
     *               AND A.JIGYO_KUBUN = B.JIGYO_KUBUN "
     *               AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART +  ")"
     *               AND A.DEL_FLG = 0"
     *               AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
     *         ORDER BY KENKYUSHUMOKU, SHINSA_KUBUN, SHOZOKUCDDAIHYO, BUNKASAIMOKUCD, A.BUNKATSU_NO, SHINSAINNO, SEIRINUMBER";   �����@�փR�[�h�A,�����ԍ��A�R�����}��
     * </pre>
     * 
     * </td>
     * </tr>
     * </table> (��)" <b>IJigyoKubun.JIGYO_KUBUN_WAKATESTART </b>"�̒l��6�B <br />
     * <br />
     * 
     * �쐬������������o�C�g�̔z��ɂ��Ċi�[����FileResource���쐬���A �����\�b�h��" <b>kakuno(FileResource,
     * String "HYOTEI") </b>"�ɂāA�T�[�o�Ɋi�[����B <br />
     * <br />
     * 
     * �p���`�f�[�^�Ǘ�info�Ƀp���`�f�[�^�����i�[���A���̏������Ƀp���`�f�[�^�Ǘ��e�[�u���̍X�V���s���B
     * �쐬����SQL���͈ȉ��̒ʂ�B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��) <br />
     * <br />
     * 
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
     * width="600">
     * <tr bgcolor="#FFFFFF">
     * <td>
     * 
     * <pre>
     * 
     *  
     *  UPDATE
     *          PUNCHKANRI
     *  SET
     *          PUNCH_SHUBETU = ?
     *          ,PUNCH_NAME = ?
     *          ,JIGYO_KUBUN = ?
     *          ,SAKUSEI_DATE = SYSDATE
     *          ,PUNCH_PATH = ?
     *  WHERE
     *          PUNCH_SHUBETU = ?
     * </pre>
     * 
     * </td>
     * </tr>
     * </table> <br />
     * 
     * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
     * width="600">
     * <tr style="color:white;font-weight: bold">
     * <td>��</td>
     * <td>�l</td>
     * <tr/>
     * <tr bgcolor="#FFFFFF">
     * <td>�f�[�^���</td>
     * <td>6</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>�f�[�^����</td>
     * <td>"�]��\�p���`�f�[�^"</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>���Ƌ敪</td>
     * <td>4</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>�p���`�f�[�^�t�@�C���p�X</td>
     * <td>�i�[�����p���`�f�[�^�t�@�C���̐�΃p�X</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>�f�[�^���</td>
     * <td>6</td>
     * </tr>
     * </table> <br />
     * 
     * ��茤���i�X�^�[�g�A�b�v�j�]��\�p���`�f�[�^�Ǘ�info��ԋp����B
     * 
     * @param userInfo  UserInfo
     * @return �������ʏ�������PunchDataKanriInfo
     * @throws ApplicationException
     */
    private PunchDataKanriInfo getPunchDataWakateHyotei(UserInfo userInfo)
            throws ApplicationException 
    {

        //DB�R�l�N�V�������擾����
        Connection connection = null;

        boolean success = false;

        //String�I�u�W�F�N�g���쐬����
        String punchResult = null;

        try {
            connection = DatabaseUtil.getConnection();

            //�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
            String tableName = "WAKASTARTUP-HYOTEI";

            //�p���`�f�[�^�쐬�ɕK�v��DAO��new����
            PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

            //List����������String���擾����
            punchResult = punchKanriInfodao.punchDataWakateHyotei(connection);

            //FileResource���쐬
            FileResource punchFileResource = new FileResource();
            punchFileResource.setBinary(punchResult.getBytes());

            //�i�[���\�b�h���Ă�
            String punchPath = kakuno(punchFileResource, tableName);

            //�p���`�f�[�^�Ǘ�info��new����
            PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

            //�p���`�f�[�^�Ǘ��e�[�u�����X�V����
            punchDataKanriinfo.setPunchShubetu("8");
            punchDataKanriinfo.setPunchName("��茤���i�X�^�[�g�A�b�v�j�]��\�p���`�f�[�^");
            punchDataKanriinfo.setJigyoKubun("6");
            punchDataKanriinfo.setSakuseiDate(new Date());
            punchDataKanriinfo.setPunchPath(punchPath);

            //DB�X�V���\�b�h���Ăт���
            punchKanriInfodao.update(connection, punchDataKanriinfo);

            success = true;

            //���ʂ���return����
            return punchDataKanriinfo;

        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4010"), e);
        } catch (ApplicationException e) {
            throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4000"), e);
        } catch (DataAccessException e) {
            throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4000"), e);
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
                throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
                        new ErrorInfo("errors.4000"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
    
    /**
     * ���ʌ������i��]��\�p���`�f�[�^�t�@�C�����쐬. <br />
     * <br />
     * 
     * �ȉ���SQL���𔭍s���āA�擾�����l�����ׂĘA������1�̕�������쐬����B <br />
     * <br />
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
     * width="600">
     * <tr bgcolor="#FFFFFF">
     * <td>
     * 
     * <pre>
     * 
     * 
     *    SELECT 
     *          �@�@�@�@'15' KENKYUSHUMOKU"    //������ڔԍ��i2�o�C�g)  
     *         ,CASE WHEN SUBSTR(A.JIGYO_ID,3,5) = '00152' THEN '2'"
     *               WHEN SUBSTR(A.JIGYO_ID,3,5) = '00153' THEN '3' " 
     *               WHEN SUBSTR(A.JIGYO_ID,3,5) = '00154' THEN '4' " 
     *               WHEN SUBSTR(A.JIGYO_ID,3,5) = '00155' THEN '5' " 
     *               WHEN SUBSTR(A.JIGYO_ID,3,5) = '00156' THEN '6' " 
     *               ELSE ' ' END SHINSA_KUBUN"  //�R���敪�i1�o�C�g�j
     *         ,NVL(SUBSTR(B.UKETUKE_NO,1,5),'     ') SHOZOKUCDDAIHYO"    �����@�փR�[�h(5�o�C�g)
     *         ,NVL(A.BUNKASAIMOKU_CD,'    ') BUNKASAIMOKUCD"             �זڃR�[�h�i4�o�C�g�j
     *         ,CASE WHEN A.BUNKATSU_NO = 'A' THEN 'A'"
     *               WHEN A.BUNKATSU_NO = 'B' THEN 'B' ELSE ' ' END BUNKATSUNO_AB"    �����ԍ��i1�o�C�g�j
     *         ,CASE WHEN A.BUNKATSU_NO = '1' THEN '1'"
     *               WHEN A.BUNKATSU_NO = '2' THEN '2' ELSE ' ' END BUNKATSUNO_12"  //�����ԍ��i1�o�C�g�j
     *         ,NVL(SUBSTR(A.UKETUKE_NO,7,4),'    ') SEIRINUMBER"                     �����ԍ�(4�o�C�g)�\���ԍ��̃n�C�t���ȉ�
     *         ,NVL(SUBSTR(B.SHINSAIN_NO,6,2), '  ') SHINSAINNO"                      �R�����}��(�R�����ԍ��̉��Q���j�i2�o�C�g�j
     *         ,CASE WHEN B.RIGAI = '1' THEN '1' ELSE ' '  END RIGAIKANKEI"           ���Q�֌W
     *         ,NVL(B.JUYOSEI, ' ') JUYOSEI"                                          �����ۑ�̊w�p�I�d�v��
     *         ,NVL(B.KENKYUKEIKAKU, ' ') KENKYUKEIKAKU"                              �����v��E���@�̑Ó���
     *         ,NVL(B.DOKUSOSEI, ' ') DOKUSOSEI"                   �Ƒn���y�ъv�V��
     *         ,NVL(B.HAKYUKOKA, ' ') HAKYUKOKA"                   �g�y����
     *         ,NVL(B.SUIKONORYOKU, ' ') SUIKONORYOKU"             ���s�\��
     *         ,CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '111') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_HOGA"           �K�ؐ��E�G��
     *         ,CASE WHEN (SUBSTR(A.JIGYO_ID,5,3) = '043')OR(SUBSTR(A.JIGYO_ID,5,3) = '053') THEN NVL(B.TEKISETSU_KAIGAI,' ') ELSE ' ' END TEKISETU_KAIGAI"  �K�ؐ��E�G��
     *         ,CASE WHEN B.KEKKA_TEN = '-' THEN ' ' ELSE NVL(B.KEKKA_TEN, ' ') END KEKKATEN"   �����]��
     *         ,CASE WHEN B.JINKEN = '3' THEN '3' ELSE ' ' END JINKEN"            �l��
     *         ,CASE WHEN B.BUNTANKIN = '3' THEN '3' ELSE ' ' END BUNTANKIN"      ���S��
     *         ,CASE WHEN B.DATO = '0' THEN ' ' ELSE NVL(B.DATO, ' ') END DATO"   �o��̑Ó���
     *         ,CASE WHEN B.COMMENTS IS NULL THEN '�@' ELSE TO_MULTI_BYTE(B.COMMENTS) END COMMENTS"    �R���ӌ�
     *         ,CASE WHEN B.OTHER_COMMENT IS NULL THEN '�@' ELSE TO_MULTI_BYTE(B.OTHER_COMMENT) END OTHER_COMMENT"   �R�����g
     *         ,NVL(B.SHINSA_JOKYO, '0') SHINSAJOKYO"     �R����
     *         FROM SHINSEIDATAKANRI A, SHINSAKEKKA B"
     *         WHERE B.JIGYO_KUBUN = " + IJigyoKubun.JIGYO_KUBUN_WAKATESTART
     *               AND NOT B.SHINSAIN_NO LIKE '@%'"     �_�~�[�R���������O����(�_�~�[�R�����ԍ������t���Ă�)
     *               AND A.SYSTEM_NO = B.SYSTEM_NO" 
     *               AND A.JIGYO_KUBUN = B.JIGYO_KUBUN "
     *               AND A.JIGYO_KUBUN IN (" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART +  ")"
     *               AND A.DEL_FLG = 0"
     *               AND TO_NUMBER(A.JOKYO_ID) IN (6,8,9,10,11,12)"
     *         ORDER BY KENKYUSHUMOKU, SHINSA_KUBUN, SHOZOKUCDDAIHYO, BUNKASAIMOKUCD, A.BUNKATSU_NO, SHINSAINNO, SEIRINUMBER";   �����@�փR�[�h�A,�����ԍ��A�R�����}��
     * </pre>
     * 
     * </td>
     * </tr>
     * </table> (��)" <b>IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI </b>"�̒l��7�B <br />
     * <br />
     * 
     * �쐬������������o�C�g�̔z��ɂ��Ċi�[����FileResource���쐬���A �����\�b�h��" <b>kakuno(FileResource,
     * String "HYOTEI") </b>"�ɂāA�T�[�o�Ɋi�[����B <br />
     * <br />
     * 
     * �p���`�f�[�^�Ǘ�info�Ƀp���`�f�[�^�����i�[���A���̏������Ƀp���`�f�[�^�Ǘ��e�[�u���̍X�V���s���B
     * �쐬����SQL���͈ȉ��̒ʂ�B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��) <br />
     * <br />
     * 
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1"
     * width="600">
     * <tr bgcolor="#FFFFFF">
     * <td>
     * 
     * <pre>
     * 
     *  
     *  UPDATE
     *          PUNCHKANRI
     *  SET
     *          PUNCH_SHUBETU = ?
     *          ,PUNCH_NAME = ?
     *          ,JIGYO_KUBUN = ?
     *          ,SAKUSEI_DATE = SYSDATE
     *          ,PUNCH_PATH = ?
     *  WHERE
     *          PUNCH_SHUBETU = ?
     * </pre>
     * 
     * </td>
     * </tr>
     * </table> <br />
     * 
     * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000"
     * width="600">
     * <tr style="color:white;font-weight: bold">
     * <td>��</td>
     * <td>�l</td>
     * <tr/>
     * <tr bgcolor="#FFFFFF">
     * <td>�f�[�^���</td>
     * <td>6</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>�f�[�^����</td>
     * <td>"�]��\�p���`�f�[�^"</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>���Ƌ敪</td>
     * <td>4</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>�p���`�f�[�^�t�@�C���p�X</td>
     * <td>�i�[�����p���`�f�[�^�t�@�C���̐�΃p�X</td>
     * </tr>
     * <tr bgcolor="#FFFFFF">
     * <td>�f�[�^���</td>
     * <td>6</td>
     * </tr>
     * </table> <br />
     * 
     * ���ʌ������i��]��\�p���`�f�[�^�Ǘ�info��ԋp����B
     * 
     * @param userInfo  UserInfo
     * @return �������ʏ�������PunchDataKanriInfo
     * @throws ApplicationException
     */
    private PunchDataKanriInfo getPunchDataSokushinHyotei(UserInfo userInfo)
            throws ApplicationException 
    {

        //DB�R�l�N�V�������擾����
        Connection connection = null;

        boolean success = false;

        //String�I�u�W�F�N�g���쐬����
        String punchResult = null;

        try {
            connection = DatabaseUtil.getConnection();

            //�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
            String tableName = "SOKUSHIN-HYOTEI";

            //�p���`�f�[�^�쐬�ɕK�v��DAO��new����
            PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

            //List����������String���擾����
            punchResult = punchKanriInfodao.punchDataSokushinHyotei(connection);

            //FileResource���쐬
            FileResource punchFileResource = new FileResource();
            punchFileResource.setBinary(punchResult.getBytes());

            //�i�[���\�b�h���Ă�
            String punchPath = kakuno(punchFileResource, tableName);

            //�p���`�f�[�^�Ǘ�info��new����
            PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

            //�p���`�f�[�^�Ǘ��e�[�u�����X�V����
            punchDataKanriinfo.setPunchShubetu("9");
            punchDataKanriinfo.setPunchName("���ʌ������i��]��\�p���`�f�[�^");
            punchDataKanriinfo.setJigyoKubun("7");
            punchDataKanriinfo.setSakuseiDate(new Date());
            punchDataKanriinfo.setPunchPath(punchPath);

            //DB�X�V���\�b�h���Ăт���
            punchKanriInfodao.update(connection, punchDataKanriinfo);

            success = true;

            //���ʂ���return����
            return punchDataKanriinfo;

        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4010"), e);
        } catch (ApplicationException e) {
            throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4000"), e);
        } catch (DataAccessException e) {
            throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4000"), e);
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
                throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
                        new ErrorInfo("errors.4000"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
//�c�@�ǉ������܂�    
    
//2006/06/20 �c�@�ǉ���������
    /**
     * ����̈挤��(�V�K�̈�)������p���`�f�[�^�t�@�C�����쐬. <br />
     * <br />
     * 
     * 
     * @param userInfo
     * @return �������ʏ�������PunchDataKanriInfo
     * @throws ApplicationException
     */
    private PunchDataKanriInfo getPunchDataTokuteiSinnkiChosho(UserInfo userInfo)
            throws ApplicationException 
    {

        //DB�R�l�N�V�������擾����
        Connection connection = null;

        boolean success = false;

        //String�I�u�W�F�N�g���쐬����
        String punchResult = null;

        try {
            connection = DatabaseUtil.getConnection();

            //�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
            String tableName = "TOKUTEI-SHINKI-CHOSHO";

            //�p���`�f�[�^�쐬�ɕK�v��DAO��new����
            PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

            //List����������String���擾����
            //TODO�@�@2006/06/20�@�c
            punchResult = punchKanriInfodao.punchDataTokuteiSinnkiChosho(connection);

            //FileResource���쐬
            FileResource punchFileResource = new FileResource();
            punchFileResource.setBinary(punchResult.getBytes());

            //�i�[���\�b�h���Ă�
            String punchPath = kakuno(punchFileResource, tableName);

            //�p���`�f�[�^�Ǘ�info��new����
            PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

            //�p���`�f�[�^�Ǘ��e�[�u�����X�V����
            punchDataKanriinfo.setPunchShubetu("10");
            punchDataKanriinfo.setPunchName("����̈挤��(�V�K�̈�)������p���`�f�[�^");
            punchDataKanriinfo.setJigyoKubun("5");
            punchDataKanriinfo.setSakuseiDate(new Date());
            punchDataKanriinfo.setPunchPath(punchPath);

            //DB�X�V���\�b�h���Ăт���
            punchKanriInfodao.update(connection, punchDataKanriinfo);

            success = true;

            //���ʂ���return����
            return punchDataKanriinfo;

        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4010"), e);
        } catch (ApplicationException e) {
            throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4000"), e);
        } catch (DataAccessException e) {
            throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4000"), e);
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
                throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
                        new ErrorInfo("errors.4000"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
    
    /**
     * ����̈挤���i�V�K�̈�j�̈�v�揑�T�v�p���`�f�[�^�t�@�C�����쐬. <br />
     * <br />
     * 
     * 
     * @param userInfo
     * @return �������ʏ�������PunchDataKanriInfo
     * @throws ApplicationException
     */
    private PunchDataKanriInfo getPunchDataTokuteiSinnkiRyoiki(UserInfo userInfo)
            throws ApplicationException 
    {

        //DB�R�l�N�V�������擾����
        Connection connection = null;

        boolean success = false;

        //String�I�u�W�F�N�g���쐬����
        String punchResult = null;

        try {
            connection = DatabaseUtil.getConnection();

            //�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
            String tableName = "TOKUTEI-SHINKI-RYOIKI";

            //�p���`�f�[�^�쐬�ɕK�v��DAO��new����
            PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);

            //List����������String���擾����
            //TODO 2006/06/20�@�c
            punchResult = punchKanriInfodao.punchDataTokuteiSinnkiGaiyo(connection);

            //FileResource���쐬
            FileResource punchFileResource = new FileResource();
            punchFileResource.setBinary(punchResult.getBytes());

            //�i�[���\�b�h���Ă�
            String punchPath = kakuno(punchFileResource, tableName);

            //�p���`�f�[�^�Ǘ�info��new����
            PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

            //�p���`�f�[�^�Ǘ��e�[�u�����X�V����
            punchDataKanriinfo.setPunchShubetu("11");
            punchDataKanriinfo.setPunchName("����̈挤���i�V�K�̈�j�̈�v�揑�T�v�p���`�f�[�^");
            punchDataKanriinfo.setJigyoKubun("5");
            punchDataKanriinfo.setSakuseiDate(new Date());
            punchDataKanriinfo.setPunchPath(punchPath);

            //DB�X�V���\�b�h���Ăт���
            punchKanriInfodao.update(connection, punchDataKanriinfo);

            success = true;

            //���ʂ���return����
            return punchDataKanriinfo;

        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4010"), e);
        } catch (ApplicationException e) {
            throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4000"), e);
        } catch (DataAccessException e) {
            throw new ApplicationException("�p���`�f�[�^�쐬����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4000"), e);
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
                throw new ApplicationException("�f�[�^�x�[�X�X�V���ɃG���[���������܂����B",
                        new ErrorInfo("errors.4000"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
    
//2006/06/20�@�c�@�ǉ������܂�  
    
//  CSV�o�͋@�\��ǉ�        2006.09.19
    /* 
     * ����̈挤���i�V�K�̈�j�̈�v�揑�T�v�p���`�f�[�^�t�@�C����CSV�o��
     * @see jp.go.jsps.kaken.model.IPunchDataMaintenance#searchCsvData(jp.go.jsps.kaken.model.vo.UserInfo)
     */
    private PunchDataKanriInfo searchCsvData(UserInfo userInfo)
            throws ApplicationException {

        //DB�R�l�N�V�����̎擾
        Connection connection = null;
        boolean success = false;
        //String�I�u�W�F�N�g���쐬����
        String punchResult = null;       

        try {
            //�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��

            connection = DatabaseUtil.getConnection();
            //�p���`�f�[�^�̃p���`�f�[�^�Ǘ��e�[�u���Ɋi�[�����t�@�C�������w��
            String tableName = "TOKUTEI-SHINKI-RYOIKI";
            //�p���`�f�[�^�쐬�ɕK�v��DAO��new����
            PunchKanriInfoDao punchKanriInfodao = new PunchKanriInfoDao(userInfo);
            
            try {
            	
                //�p���`�f�[�^�쐬�ɕK�v��DAO��new����
            	punchResult = punchKanriInfodao.punchDataTokuteiSinnkiGaiyoCsv(connection);
                //FileResource���쐬
                FileResource punchFileResource = new FileResource();
                punchFileResource.setBinary(punchResult.getBytes());

                //�i�[���\�b�h���Ă�
                String punchPath = kakunoCsv(punchFileResource, tableName);
                

                //�p���`�f�[�^�Ǘ�info��new����
                PunchDataKanriInfo punchDataKanriinfo = new PunchDataKanriInfo();

                //�p���`�f�[�^�Ǘ��e�[�u�����X�V����
                punchDataKanriinfo.setPunchShubetu("11");
                punchDataKanriinfo.setPunchName("����̈挤���i�V�K�̈�j�̈�v�揑�T�v�p���`�f�[�^");
                punchDataKanriinfo.setJigyoKubun("5");
                punchDataKanriinfo.setSakuseiDate(new Date());
                punchDataKanriinfo.setPunchPath(punchPath);

                //DB�X�V���\�b�h���Ăт���
                punchKanriInfodao.update(connection, punchDataKanriinfo);

                success = true;

                //���ʂ���return����
                return punchDataKanriinfo;                
            } catch (DataAccessException e) {
                throw new ApplicationException(
                        "����̈挤���i�V�K�̈�j�̈�v�揑�T�v�f�[�^��������DB�G���[���������܂����B",
                        new ErrorInfo("errors.4004"),
                        e);
            } catch(NoDataFoundException e) {
                throw new ApplicationException(
                        "����̈挤���i�V�K�̈�j�̈�v�揑�T�v��������܂���B",
                        new ErrorInfo("errors.4000"),
                        e);
            }
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }       
}

