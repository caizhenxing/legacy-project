/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
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
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * �R�������Ǘ����s���N���X.<br /><br />
 * 
 * 
 * 
 * �g�p���Ă���e�[�u��<br /><br />
 * �@�E�R�����}�X�^:MASTER_SHINSAIN<br />
 * �@�@�@�R�����̏����Ǘ�����B<br /><br />
 * �@�E�R�������e�[�u��:SHINSAININFO<br />
 * �@�@�@�R������ID�E�p�X���[�h���Ǘ�����B<br /><br />
 * �@�E�R�����ʃe�[�u��:SHINSAKEKKA<br />
 * �@�@�@�R��������U�茋�ʏ��Ɛ\�����̐R�����ʂ��Ǘ�����B<br /><br />
 * �@�E�\���f�[�^�Ǘ��e�[�u��:SHINSEIDATAKANRI<br />
 * �@�@�@�\���f�[�^�̏����Ǘ�����B<br /><br />
 * �@�E�h�c�p�X���[�h���s���[���e�[�u��:RULEINFO<br />
 * �@�@�@�eID�E�p�X���[�h�̔��s���[�����Ǘ�����B<br /><br />
 */
public class ShinsainMaintenance implements IShinsainMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ���O
	 */
	protected static Log log = LogFactory.getLog(ShinsainMaintenance.class);
	
	/**
	 * �R���˗��ʒm���t�@�C���i�[�t�H���_ .<br /><br />
	 * 
	 * ��̓I�Ȓl�́A"<b>${shinsei_path}/work/irai2/</b>"<br />
	 */
	private static String IRAI_WORK_FOLDER2      = ApplicationSettings.getString(ISettingKeys.IRAI_WORK_FOLDER2);		

	/**
	 * �R���˗���Word�t�@�C���i�[�t�H���_.<br /><br />
	 * 
	 * ��̓I�Ȓl�́A"<b>${shinsei_path}/settings/irai2/</b>"<br />
	 */
	private static String IRAI_FORMAT_PATH2      = ApplicationSettings.getString(ISettingKeys.IRAI_FORMAT_PATH2);		

	/**
	 * �R���˗���Word�t�@�C����.<br /><br />
	 * 
	 * ��̓I�Ȓl�́A"<b>shinsairai2.doc</b>"<br />
	 */
	private static String IRAI_FORMAT_FILE_NAME2 = ApplicationSettings.getString(ISettingKeys.IRAI_FORMAT_FILE_NAME2);		

	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsainMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IShinseishaMaintenance
	//---------------------------------------------------------------------

	/**
	 * �R�������̑}��.<br /><br />
	 * 
	 * ���������̃f�[�^���g�p���A�R�����}�X�^�E�R�������e�[�u���Ƀf�[�^��}������B
	 * ���̌�A�}�������f�[�^������ShinsainInfo��ԋp����B<br /><br />
	 * 
	 * <b>1.�R��������ǉ�</b><br /><br />
	 * �ȉ��̃f�[�^���A��������addInfo�ɒǉ�����B<br /><br />
	 * 
	 * �@(1)�R����ID�̒ǉ�<br />
	 * �@�@"<b>����N�x(2��)�{���Ƌ敪(1��)+�R�����ԍ�(6��)�{�`�F�b�N�f�W�b�g�L��(1��)</b>"��String���擾����B<br /><br />
	 * 
	 * �@�@�@�E����N�x�@�@�@�@�@�@�@�c���݂̔N�x�𐼗�Ŏ擾�B<br />
	 * �@�@�@�E���Ƌ敪�@�@�@�@�@�@�@�@�@�c������addInfo����擾�B<br />
	 * �@�@�@�E�R�����ԍ��@�@�@�@�@�@�c������addInfo����擾�B<br />
	 * �@�@�@�E�`�F�b�N�f�W�b�g�L���@�c���\�b�h"<b>getCheckDigit(key</b>(��1), <b>CheckDiditUtil.FORM_ALP</b>(��2)<b>)</b>"���擾�B<br />
	 * �@�@�@�@(��1)����N�x(2��)�{���Ƌ敪(1��)+�R�����ԍ�(6��)��String�B<br />
	 * �@�@�@�@(��2)String "form_alp"<br /><br />
	 * 
	 * �@�@���߂���������A�R����ID�Ƃ���addInfo�ɒǉ�����B<br /><br />
	 * 
	 * �@(2)�p�X���[�h�̒ǉ�<br />
	 * �@�@�ȉ���SQL���𔭍s���āA�p�X���[�h�쐬���[�����擾����B(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	RULEINFO A
	 * WHERE
	 * 	TAISHO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ώێ�ID</td><td>4(ITaishoId.SHINSAIN)���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �@�@�擾�������[�����A�p�X���[�h�̕�������쐬���AaddInfo�ɒǉ�����B<br /><br />
	 * 
	 * �@(3)�f�[�^�X�V���̒ǉ�<br />
	 * 
	 * �@�@���݂̓��t��WAS���擾���AaddInfo�ɒǉ�����B<br /><br />
	 * 
	 * <b>2.�R�������̑}��</b><br /><br />
	 * 
	 * �@(1)�f�[�^�}���̑O�ɁA�ȉ���SQL���𔭍s���A�d���`�F�b�N���s���B<br />
	 * �@�@(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������addInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������addInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �@(2)�d�����Ȃ��ꍇ�́A�ȉ���SQL���𔭍s���A�R�����}�X�^�ƐR�������e�[�u���Ƀf�[�^��}������B<br />
	 * �@�@(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO MASTER_SHINSAIN
	 * 	(SHINSAIN_NO
	 * 	, JIGYO_KUBUN
	 * 	, NAME_KANJI_SEI
	 * 	, NAME_KANJI_MEI
	 * 	, NAME_KANA_SEI
	 * 	, NAME_KANA_MEI
	 * 	, SHOZOKU_CD
	 * 	, SHOZOKU_NAME
	 * 	, BUKYOKU_NAME
	 * 	, SHOKUSHU_NAME
	 * 	, SOFU_ZIP
	 * 	, SOFU_ZIPADDRESS
	 * 	, SOFU_ZIPEMAIL
	 * 	, SHOZOKU_TEL
	 * 	, SHOZOKU_FAX
	 * 	, URL
	 * 	, SENMON
	 * 	, KOSHIN_DATE
	 * 	, BIKO)
	 * VALUES
	 * 	(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������addInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������addInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������addInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������addInfo�̕ϐ�NameKanjiMei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������addInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������addInfo�̕ϐ�NameKanaMei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(�R�[�h)</td><td>������addInfo�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(�a��)</td><td>������addInfo�̕ϐ�ShozokuName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�(�a��)</td><td>������addInfo�̕ϐ�BukyokuName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E�햼��</td><td>������addInfo�̕ϐ�ShokushuName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���t��(�X�֔ԍ�)</td><td>������addInfo�̕ϐ�SofuZip���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���t��(�Z��)</td><td>������addInfo�̕ϐ�SofuZipaddress���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���t��(Email)</td><td>������addInfo�̕ϐ�SofuZipemail���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�d�b�ԍ�</td><td>������addInfo�̕ϐ�ShozokuTel���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>FAX�ԍ�</td><td>������addInfo�̕ϐ�ShozokuFax���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>URL</td><td>������addInfo�̕ϐ�Url���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��啪��</td><td>������addInfo�̕ϐ�Senmon���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�f�[�^�X�V��</td><td>������addInfo�̕ϐ�KoshinDate���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>������addInfo�̕ϐ�Biko���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO SHINSAININFO
	 * 	(SHINSAIN_ID
	 * 	, PASSWORD
	 * 	, YUKO_DATE
	 * 	, DEL_FLG)
	 * VALUES
	 * 	(?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R����ID</td><td>������addInfo�̕ϐ�ShinsainId���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�p�X���[�h</td><td>������addInfo�̕ϐ�Password���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>������addInfo�̕ϐ�YukoDate���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>0</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 
	 * <b>3.�l�̎擾</b><br /><br />
	 * 
	 * �@�R�����}�X�^�ƁA�R�������e�[�u������A���}�������l���擾����B���s����SQL���͈ȉ��̒ʂ�B<br />
	 * �@(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������addInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������addInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l���AShinsainInfo�Ɋi�[���āA�ԋp����B
	 * 
	 * @param userInfo UserInfo
	 * @param addInfo ShinsainInfo
	 * @return �o�^�����f�[�^������ShinsainInfo
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainInfo)
	 */
	public ShinsainInfo insert(UserInfo userInfo, ShinsainInfo addInfo) throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//---------------------------------------
			//�R�������̓o�^
			//---------------------------------------
			ShinsainInfoDao dao = new ShinsainInfoDao(userInfo);

			//---------------------------------------
			//�L�[���́i�R����ID�j���쐬
			//����N�x(2��)�{�敪�i1���j+�R�����ԍ�(6��) �{�`�F�b�N�f�W�b�g�L���i1���j
			//---------------------------------------
			String wareki = new DateUtil().getNendo();
			String key = DateUtil.changeWareki2Seireki(wareki)
						+ addInfo.getJigyoKubun()
						+ addInfo.getShinsainNo();
			
			//�R����ID���擾					
			String shinsainId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
			addInfo.setShinsainId(shinsainId);

			//RULEINFO�e�[�u����胋�[���擾����
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSAIN);
			
			//---------------------------------------
			//�p�X���[�h���̍쐬
			//---------------------------------------
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			addInfo.setPassword(newPassword);

			//---------------------------------------
			//�L�����t�����̍쐬(�S���җp)
			//---------------------------------------
//			Date yukoDate = rureInfoDao.selectRuleInfo(connection, rulePk).getYukoDate();
//			addInfo.setYukoDate(yukoDate);

			//�X�V���t�̐ݒ�		
			DateUtil dateUtil = new DateUtil();
			addInfo.setKoshinDate(dateUtil.getCal().getTime());

			dao.insertShinsainInfo(connection, addInfo);

			//---------------------------------------
			//�o�^�f�[�^�̎擾
			//---------------------------------------
			ShinsainInfo result = dao.selectShinsainInfo(connection, addInfo);
			
			//---------------------------------------
			//�o�^����I��
			//---------------------------------------
			success = true;

			return result;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R�����Ǘ��f�[�^�o�^����DB�G���[���������܂����B",
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
					"�R�����Ǘ��f�[�^�o�^����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �R�������̍X�V.<br /><br />
	 * 
	 * �R�����}�X�^�E�R�������e�[�u���̍X�V���s���B
	 * �܂��A���łɊ���U���Ă���R�����̏ꍇ�ɂ́A�R�����ʃe�[�u�����X�V���s���B<br /><br />
	 * 
	 * <b>1.�R�������̍X�V</b><br /><br />
	 * �@�ȉ���SQL���𔭍s���āA�R�����}�X�^�E�R�������e�[�u���̍X�V���s���B<br />
	 * �@(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	MASTER_SHINSAIN
	 * SET
	 * 	NAME_KANJI_SEI = ?
	 * 	, NAME_KANJI_MEI = ?
	 * 	, NAME_KANA_SEI = ?
	 * 	, NAME_KANA_MEI = ?
	 * 	, SHOZOKU_CD = ?
	 * 	, SHOZOKU_NAME = ?
	 * 	, BUKYOKU_NAME = ?
	 * 	, SHOKUSHU_NAME = ?
	 * 	, SOFU_ZIP = ?
	 * 	, SOFU_ZIPADDRESS = ?
	 * 	, SOFU_ZIPEMAIL = ?
	 * 	, SHOZOKU_TEL = ?
	 * 	, SHOZOKU_FAX = ?
	 * 	, URL = ?
	 * 	, SENMON = ?
	 * 	, KOSHIN_DATE = ?
	 * 	, BIKO = ?
	 * WHERE
	 * 	JIGYO_KUBUN = ?
	 * 	AND SHINSAIN_NO = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������updateInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������updateInfo�̕ϐ�NameKanjiMei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������updateInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������updateInfo�̕ϐ�NameKanaMei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(�R�[�h)</td><td>������updateInfo�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(�a��)</td><td>������updateInfo�̕ϐ�ShozokuName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�(�a��)</td><td>������updateInfo�̕ϐ�BukyokuName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E�햼��</td><td>������updateInfo�̕ϐ�ShokushuName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���t��(�X�֔ԍ�)</td><td>������updateInfo�̕ϐ�SofuZip���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���t��(�Z��)</td><td>������updateInfo�̕ϐ�SofuZipaddress���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���t��(Email)</td><td>������updateInfo�̕ϐ�SofuZipemail���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�d�b�ԍ�</td><td>������updateInfo�̕ϐ�ShozokuTel���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>FAX�ԍ�</td><td>������updateInfo�̕ϐ�ShozokuFax���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>URL</td><td>������updateInfo�̕ϐ�Url���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��啪��</td><td>������updateInfo�̕ϐ�Senmon���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�f�[�^�X�V��</td><td>������updateInfo�̕ϐ�KoshinDate���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>������updateInfo�̕ϐ�Biko���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������updateInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������updateInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAININFO
	 * SET
	 * 	PASSWORD = ?
	 * 	,YUKO_DATE = ?
	 * WHERE
	 * 	SHINSAIN_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�p�X���[�h</td><td>������updateInfo�̕ϐ�Password���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>������updateInfo�̕ϐ�YukoDate���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R����ID</td><td>������updateInfo�̕ϐ�ShinsainId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 
	 * <b>2.�R�����ʃe�[�u���̐R�������̍X�V</b><br /><br />
	 * ����U���Ă���΁A�R�����ʃe�[�u���̐R���������X�V����B<br /><br />
	 * 
	 * �@(1)����U���Ă��邩���m�F<br /><br />
	 * �@�@�ȉ���SQL���𔭍s���āA�R��������(SHINSA_JOKYO = '0')�ō폜����Ă��Ȃ�(DEL_FLG = '0')�S���R�����ʏ��̐����m�F����B<br />
	 * �@�@(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)
	 * FROM
	 * 	SHINSAKEKKA
	 * 	, SHINSEIDATAKANRI
	 * WHERE
	 * 	A.SHINSAIN_NO = ?"
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.SHINSA_JOKYO = '0'
	 * 	AND A.SYSTEM_NO = B.SYSTEM_NO
	 * 	AND B.DEL_FLG = '0'</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������updateInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������updateInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 
	 * �@(2)����U���Ă�����X�V���s��<br /><br />
	 * �@�@���ʂ�1���ȏ゠�����ꍇ�A�ȉ���SQL���𔭍s���ĐR�����ʃe�[�u���̍X�V���s���B<br />
	 * �@�@(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAKEKKA
	 * SET
	 * 	SHINSAIN_NO = ?			--�R�����ԍ�
	 * 	, JIGYO_KUBUN = ?			--���Ƌ敪
	 * 	, SHINSAIN_NAME_KANJI_SEI = ?	--�R�������i�����|���j
	 * 	, SHINSAIN_NAME_KANJI_MEI = ?	--�R�������i�����|���j
	 * 	, NAME_KANA_SEI = ?		--�R�������i�t���K�i�|���j
	 * 	, NAME_KANA_MEI = ?		--�R�������i�t���K�i�|���j
	 * 	, SHOZOKU_NAME = ?			--�R���������@�֖�
	 * 	, BUKYOKU_NAME = ?			--�R�������ǖ�
	 * 	, SHOKUSHU_NAME = ?		--�R�����E��
	 * WHERE
	 * 	SHINSAIN_NO = ?
	 * 	AND JIGYO_KUBUN = ?
	 * 	AND SHINSA_JOKYO = '0'
	 * 	AND EXISTS(
	 * 			SELECT
	 * 				*
	 * 			FROM
	 * 				SHINSEIDATAKANRI A
	 * 				, SHINSAKEKKA B
	 * 			WHERE
	 * 				A.DEL_FLG = 0
	 * 				AND A.SYSTEM_NO = B.SYSTEM_NO
	 * 		)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������updateInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������updateInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�����|���j</td><td>������updateInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�����|���j</td><td>������updateInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>������updateInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>������updateInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R���������@�֖�</td><td>������updateInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������ǖ�</td><td>������updateInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����E��</td><td>������updateInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������updateInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������updateInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo
	 * @param updateInfo ShinsainInfo
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainInfo)
	 */
	public ShinsainInfo update(UserInfo userInfo, ShinsainInfo updateInfo) throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//---------------------------------------
			//�R�������̍X�V
			//---------------------------------------
			ShinsainInfoDao shinsainInfoDao = new ShinsainInfoDao(userInfo);
			shinsainInfoDao.updateShinsainInfo(connection, updateInfo);
			
			//---------------------------------------
			//�R�����ʃe�[�u���̐R�������̍X�V
			//---------------------------------------
			ShinsaKekkaInfoDao shinsaKekkaInfoDao = new ShinsaKekkaInfoDao(userInfo);
			int tantoShinsaKekka
				= shinsaKekkaInfoDao.countTantoShinsaKekkaInfo(connection, updateInfo.getShinsainNo(), updateInfo.getJigyoKubun());
			//����U���Ă���΁A�R�����ʂ̐R���������X�V
			if(tantoShinsaKekka > 0) {
				shinsaKekkaInfoDao.updateShinsainInfo(connection, updateInfo, null, null);
			}
						
			//---------------------------------------
			//�X�V�f�[�^�̎擾
			//---------------------------------------
			ShinsainInfo result = shinsainInfoDao.selectShinsainInfo(connection, updateInfo);
			
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;
			
			return result;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R�����Ǘ��f�[�^�X�V����DB�G���[���������܂����B",
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
					"�R�����Ǘ��f�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
	}

	/**
	 * �R�������̍폜.<br /><br />
	 * 
	 * �R�����ʃe�[�u���̐R�������̍폜</b><br /><br />
	 * 
	 * <b>1.����U���Ă��邩���m�F</b><br /><br />
	 * �@����U���Ă��āA�R��������(SHINSA_JOKYO = '0')�̂��̂�����ꍇ�͍폜�ł��Ȃ��̂ŁA
	 * �ȉ���SQL���𔭍s���Ċm�F����B<br />
	 * �@(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)
	 * FROM
	 * 	SHINSAKEKKA
	 * 	, SHINSEIDATAKANRI
	 * WHERE
	 * 	A.SHINSAIN_NO = ?
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.SHINSA_JOKYO = '0'
	 * 	AND A.SYSTEM_NO = B.SYSTEM_NO
	 * 	AND B.DEL_FLG = '0'</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������updateInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������updateInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �@�R���������ō폜����Ă��Ȃ��S���R�����ʏ�񂪑��݂���ꍇ�ɂ͗�O��throw����B<br /><br />
	 * 
	 * <b>2.�R�������̍폜</b><br /><br />
	 * �R�������̕����폜���s���B<br /><br />
	 * 
	 * �@(1)�R�������̌���<br /><br />
	 * �@�@�Y������R����������������B���s����SQL���͈ȉ��̒ʂ�B<br />
	 * �@�@(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br />
	 * �@�@�Y��������̂��Ȃ������ꍇ�ɂ͗�O��throw����B<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������addInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������addInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �@�@���ʂ����݂���ꍇ�ɂ́A�l��ShinsainInfo�Ɋi�[����B<br /><br />
	 * 
	 * �@(2)�R�������̕����폜<br /><br />
	 * �@�@�R�������e�[�u���E�R�����}�X�^������̕����폜���s���B���s����SQL���͈ȉ��̒ʂ�B<br />
	 * �@�@(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * DELETE FROM
	 * 	SHINSAININFO
	 * WHERE
	 * 	SHINSAIN_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R����ID</td><td>ShinsainInfo�̕ϐ�ShinsainId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * DELETE FROM
	 * 	MASTER_SHINSAIN
	 * WHERE
	 * 	SHINSAIN_NO = ?
	 * 	AND JIGYO_KUBUN = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>ShinsainInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>ShinsainInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param deleteInfo ShinsainInfo
	 * @throws ApplicationException
	 * @throws ValidationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainInfo)
	 */
	public void delete(UserInfo userInfo, ShinsainInfo deleteInfo) throws ApplicationException, ValidationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//�G���[���ێ��p���X�g
			List errors = new ArrayList();

			//�R��������U���Ă���ꍇ�́A�폜�ł��Ȃ��̂Ń`�F�b�N
			ShinsaKekkaInfoDao shinsaKekkaInfoDao = new ShinsaKekkaInfoDao(userInfo);
			int tantoShinsaKekka
				= shinsaKekkaInfoDao.countTantoShinsaKekkaInfo(connection, deleteInfo.getShinsainNo(), deleteInfo.getJigyoKubun());
			if(tantoShinsaKekka> 0) {
				errors.add(new ErrorInfo("errors.5029"));
				throw new ValidationException(
					"�R���������ō폜����Ă��Ȃ��\���f�[�^��������܂����B",
					errors);
			}

			//---------------------------------------
			//�R�������̍폜(�R�����̓o�^�@�\������̂ŁA�R�����ԍ����_�u��Ȃ��悤�����폜)
			//---------------------------------------
			ShinsainInfoDao shinsainInfoDao = new ShinsainInfoDao(userInfo);
			shinsainInfoDao.deleteShinsainInfo(connection, deleteInfo);
			//---------------------------------------
			//�폜����I��
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R�����Ǘ��f�[�^�폜����DB�G���[���������܂����B",
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
					"�R�����Ǘ��f�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
	}

	/**
	 * �R�������̎擾.<br /><br />
	 * 
	 * �R���������擾���A���ʂ�ShinsainInfo�Ɋi�[���ĕԋp����B<br /><br />
	 * 
	 * <b>1.�R�������̎擾</b><br /><br />
	 * �ȉ���SQL���𔭍s���āA�R�������̎擾���s���B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��) <br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������pkInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������pkInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * <b>2.���ʂ̕ԋp</b><br /><br />
	 * 
	 * �擾�����l��ShinsainInfo�Ɋi�[���A�ԋp����B<br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinsainPk
	 * @return �R���������i�[����ShinsainInfo
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainPk)
	 */
	public ShinsainInfo select(UserInfo userInfo, ShinsainPk pkInfo) throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			ShinsainInfoDao dao = new ShinsainInfoDao(userInfo);
			return dao.selectShinsainInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R�����Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * �R������������Page�̎擾.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���A�R���������擾����B<br />
	 * SQL����where��ɂ́A�������̊e�l��null���͋󕶎���łȂ��ꍇ�ɏ��������������Ă����B<br />
	 * (��������SQL���ƁA�����ɂȂ�ϐ���SQL���̉��̕\���Q��)
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	MS.SHINSAIN_NO		--�R�����ԍ�
	 * 	, MS.NAME_KANJI_SEI	--�R���������i����-���j
	 * 	, MS.NAME_KANJI_MEI	--�R���������i����-���j
	 * 	, MS.SHOZOKU_NAME		--�����@�֖�
	 * 	, MS.SOFU_ZIPEMAIL		--���t��(Email)
	 * 	, MS.JIGYO_KUBUN"		--���Ƌ敪
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 
	 *				<b><span style="color:#002288">�|�|���I���������|�|</span></b>
	 *
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)
	 * ORDER BY
	 * 	MS.SHINSAIN_NO</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>ShinsainNo</td><td>AND MS.SHINSAIN_NO = '�R�����ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R������(��-������)</td><td>NameKanjiSei</td><td>AND MS.NAME_KANJI_SEI LIKE '%�R������(��-������)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R������(��-������)</td><td>NameKanjiMei</td><td>AND MS.NAME_KANJI_SEI LIKE '%�R������(��-������)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����R�[�h</td><td>ShozokuCd</td><td>AND MS.SHOZOKU_CD = '�����R�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�זڃR�[�h</td><td>BunkaSaimokuCd</td><td>AND (((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '�זڃR�[�h')AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '�זڃR�[�h'))</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>JigyoKubun</td><td>AND MS.JIGYO_KUBUN = '���Ƌ敪'</td></tr>
	 * </table><br/>
	 * 
	 * 
	 * �擾�����l�́A�񖼂��L�[�ɂ���Map�Ɋi�[�����B
	 * ������܂Ƃ߂�List�Ɋi�[���A����List��Page�Ɋi�[�����B
	 * �R��������������Page���A�ԋp�����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsainSearchInfo
	 * @return �R������������Page
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainSearchInfo)
	 */
	public Page search(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException {

		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------

		String select = "SELECT MS.SHINSAIN_NO"			//�R�����ԍ�
						+ ", MS.NAME_KANJI_SEI"			//�R���������i����-���j
						+ ", MS.NAME_KANJI_MEI"			//�R���������i����-���j
						+ ", MS.SHOZOKU_NAME"			//�����@�֖�
						+ ", MS.SOFU_ZIPEMAIL"			//���t��(Email)
						+ ", MS.JIGYO_KUBUN"			//���Ƌ敪
						+ " FROM MASTER_SHINSAIN MS"
						+ ", SHINSAININFO SI"
						+ " WHERE SI.DEL_FLG = 0";

		StringBuffer query = new StringBuffer(select);
	
		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){			//�R�����ԍ�
			query.append(" AND MS.SHINSAIN_NO = '" + EscapeUtil.toSqlString(searchInfo.getShinsainNo()) + "'");
		}
//		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){	//���ȍזڃR�[�h
//			ArrayList arrBscd = this.separateString(searchInfo.getBunkaSaimokuCd());
//
//			int cnt = arrBscd.size();
//			for(int i=0; i<cnt; i++){
//				String condi = (String)arrBscd.get(i);
//				
//				if(i == 0){
//					query.append("AND (MS.LEVEL_A1 = '" + condi + "' ");
//				}else{
//					query.append(" OR MS.LEVEL_A1 = '" + condi + "' ");
//				}
//				query.append("OR MS.LEVEL_B1_1 = '" + condi + "' ");
//				query.append("OR MS.LEVEL_B1_2 = '" + condi + "' ");
//				query.append("OR MS.LEVEL_B1_3 = '" + condi + "' ");
//				query.append("OR MS.LEVEL_B2_1 = '" + condi + "' ");
//				query.append("OR MS.LEVEL_B2_2 = '" + condi + "' ");
//				query.append("OR MS.LEVEL_B2_3 = '" + condi + "'");
//			}
//			if(cnt != 0){
//				query.append(") ");
//			}
//		}
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){				//�\���Ҏ����i����-���j
			query.append(" AND MS.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){				//�\���Ҏ����i����-���j
			query.append(" AND MS.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){					//�����@�փR�[�h
			query.append(" AND MS.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){			//�זڔԍ�
			query.append(" AND (")
				.append("((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))")
				.append(" OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'))")
			.append(")");
		}
//		if(searchInfo.getKeyword() != null && !searchInfo.getKeyword().equals("")){				//�L�[���[�h
//			ArrayList arrBscd = this.separateString(searchInfo.getKeyword());
//
//			int cnt = arrBscd.size();
//			for(int i=0; i<cnt; i++){
//				String condi = EscapeUtil.toSqlString((String)arrBscd.get(i));
//				
//				if(i == 0){
//					query.append("AND (MS.KEY1 LIKE '%" + condi + "%' ");
//				}else{
//					query.append(" OR MS.KEY1 LIKE '%" + condi + "%' ");
//				}
//				query.append("OR MS.KEY2 LIKE '%" + condi + "%' ");
//				query.append("OR MS.KEY3 LIKE '%" + condi + "%' ");
//				query.append("OR MS.KEY4 LIKE '%" + condi + "%' ");
//				query.append("OR MS.KEY5 LIKE '%" + condi + "%' ");
//				query.append("OR MS.KEY6 LIKE '%" + condi + "%' ");
//				query.append("OR MS.KEY7 LIKE '%" + condi + "%'");				
//			}
//			if(cnt != 0){
//				query.append(") ");
//			}
//		}
		if(searchInfo.getJigyoKubun() != null && !searchInfo.getJigyoKubun().equals("")){			//���Ƌ敪
			query.append(" AND MS.JIGYO_KUBUN = '" + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "'");
		}

		//���������{�\�[�g�����i�R�����ԍ����j
		query.append(" AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)")	//7��
			 .append(" AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)")
			 .append(" ORDER BY MS.SHINSAIN_NO");
		
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
				"�R�����Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

	}

	/**
	 * �R�������CSV�f�[�^�̍쐬.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���A�R���������擾����B<br />
	 * SQL����where��ɂ́A�������̊e�l��null���͋󕶎���łȂ��ꍇ�ɏ��������������Ă����B<br />
	 * (��������SQL���ƁA�����ɂȂ�ϐ���SQL���̉��̕\���Q��)
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * SELECT
	 * 	MS.SHINSAIN_NO			"�R�����ԍ�"
	 * 	, REPLACE(REPLACE(MS.JIGYO_KUBUN,
	 * 			'1',
	 * 			'�w�p�n��')
	 * 		'4',
	 * 		'��Ռ�����')		"���Ƌ敪"
	 * 	, MS.NAME_KANJI_SEI		"����(�������|��)"
	 * 	, MS.NAME_KANJI_MEI		"����(�������|��)"
	 * 	, MS.NAME_KANA_SEI			"����(�t���K�i�|��)"
	 * 	, MS.NAME_KANA_MEI			"����(�t���K�i�|��)"
	 * 	, MS.SHOZOKU_CD			"�����@�֖�(�R�[�h)"
	 * 	, MS.SHOZOKU_NAME			"�����@�֖�(�a��)"
	 * 	, MS.BUKYOKU_NAME			"���ǖ�(�a��)"
	 * 	, MS.SHOKUSHU_NAME			"�E�햼��"
	 * 	, MS.SOFU_ZIP			"���t��(�X�֔ԍ�)"
	 * 	, MS.SOFU_ZIPADDRESS		"���t��(�Z��)"
	 * 	, MS.SOFU_ZIPEMAIL			"���t��(Email)"
	 * 	, MS.SHOZOKU_TEL			"�d�b�ԍ�"
	 * 	, MS.SHOZOKU_FAX			"FAX�ԍ�"
	 * 	, MS.URL				"URL"
	 * 	, MS.SENMON			"��啪��"
	 * 	, TO_CHAR(MS.KOSHIN_DATE,
	 * 		'YYYY/MM/DD')		"�f�[�^�X�V��"
	 * 	, MS.BIKO				"���l"
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 
	 *	<b><span style="color:#002288">�|�|���I���������|�|</span></b>
	 *
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)
	 * ORDER BY
	 * 	MS.SHINSAIN_NO</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>ShinsainNo</td><td>AND MS.SHINSAIN_NO = '�R�����ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R������(��-������)</td><td>NameKanjiSei</td><td>AND MS.NAME_KANJI_SEI LIKE '%�R������(��-������)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R������(��-������)</td><td>NameKanjiMei</td><td>AND MS.NAME_KANJI_SEI LIKE '%�R������(��-������)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����R�[�h</td><td>ShozokuCd</td><td>AND MS.SHOZOKU_CD = '�����R�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�זڃR�[�h</td><td>BunkaSaimokuCd</td><td>AND (((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '�זڃR�[�h')AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '�זڃR�[�h'))</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>JigyoKubun</td><td>AND MS.JIGYO_KUBUN = '���Ƌ敪'</td></tr>
	 * </table><br />
	 * 
	 * List�̍ŏ��̗v�f�ɃJ��������}������B�������ē���CSV��List��ԋp����B<br /><br />
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsainSearchInfo
	 * @return �R��������CSV�o�̓f�[�^��List
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#searchCsvData(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainSearchInfo)
	 */
	public List searchCsvData(UserInfo userInfo, ShinsainSearchInfo searchInfo) throws ApplicationException {
	
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select = "SELECT MS.SHINSAIN_NO \"�R�����ԍ�\""			//�R�����ԍ�
						+ ", REPLACE(REPLACE(MS.JIGYO_KUBUN, '1', '�w�p�n��'), '4', '��Ռ�����') \"���Ƌ敪\""				//���Ƌ敪
						+ ", MS.NAME_KANJI_SEI \"����(�������|��)\""		//����(�������|��)
						+ ", MS.NAME_KANJI_MEI \"����(�������|��)\""		//����(�������|��)
						+ ", MS.NAME_KANA_SEI \"����(�t���K�i�|��)\""		//����(�t���K�i�|��)
						+ ", MS.NAME_KANA_MEI \"����(�t���K�i�|��)\""		//����(�t���K�i�|��)
						+ ", MS.SHOZOKU_CD \"���������@�֖�(�ԍ�)\""		//�����@�֖�(�R�[�h)
						+ ", MS.SHOZOKU_NAME \"�����@�����֖�(�a��)\""	//�����@�֖�(�a��)
//						+ ", MS.BUKYOKU_CD \"���ǖ�(�R�[�h)\""			//���ǖ�(�R�[�h)
						+ ", MS.BUKYOKU_NAME \"���ǖ�(�a��)\""			//���ǖ�(�a��)
//						+ ", MS.SHOKUSHU_CD \"�E��R�[�h\""				//�E��R�[�h
						+ ", MS.SHOKUSHU_NAME \"�E�햼��\""				//�E�햼��
//						+ ", MS.KEI_CD \"�n��\""							//�n��
//						+ ", MS.SHINSA_KAHI \"�R���s��\""				//�R���s��
						+ ", MS.SOFU_ZIP \"���t��(�X�֔ԍ�)\""			//���t��(�X�֔ԍ�)
						+ ", MS.SOFU_ZIPADDRESS \"���t��(�Z��)\""		//���t��(�Z��)
						+ ", MS.SOFU_ZIPEMAIL \"���t��(Email)\""			//���t��(Email)
						+ ", MS.SHOZOKU_TEL \"�d�b�ԍ�\""				//�d�b�ԍ�
						+ ", MS.SHOZOKU_FAX \"FAX�ԍ�\""					//�d�b�ԍ�
//						+ ", MS.SOFU_ZIPEMAIL2 \"���t��(Email2)\""		//���t��(Email2)
//						+ ", MS.SHOZOKU_TEL \"�@�֓d�b�ԍ�\""			//�@�֓d�b�ԍ�
//						+ ", MS.JITAKU_TEL \"����d�b�ԍ�\""				//����d�b�ԍ�
//						+ ", MS.SINKI_KEIZOKU_FLG \"�V�K�E�p��\""		//�V�K�E�p��
//						+ ", MS.KIZOKU_START \"�Ϗ��J�n��\""				//�Ϗ��J�n��
//						+ ", MS.KIZOKU_END \"�Ϗ��I����\""				//�Ϗ��I����
//						+ ", MS.SHAKIN \"�Ӌ�\""							//�Ӌ�
						+ ", MS.URL URL"								//URL
						+ ", MS.SENMON \"��啪��\""						//��啪��
						+ ", TO_CHAR(MS.KOSHIN_DATE, 'YYYY/MM/DD') \"�f�[�^�X�V��\""			//�f�[�^�X�V��
//						+ ", MS.LEVEL_A1 \"���ȍזڃR�[�h(A)\""			//���ȍזڃR�[�h(A)
//						+ ", MS.LEVEL_B1_1 \"���ȍזڃR�[�h(B1-1)\""		//���ȍזڃR�[�h(B1-1)
//						+ ", MS.LEVEL_B1_2 \"���ȍזڃR�[�h(B1-2)\""		//���ȍזڃR�[�h(B1-2)
//						+ ", MS.LEVEL_B1_3 \"���ȍזڃR�[�h(B1-3)\""		//���ȍזڃR�[�h(B1-3)
//						+ ", MS.LEVEL_B2_1 \"���ȍזڃR�[�h(B2-1)\""		//���ȍזڃR�[�h(B2-1)
//						+ ", MS.LEVEL_B2_2 \"���ȍזڃR�[�h(B2-2)\""		//���ȍזڃR�[�h(B2-2)
//						+ ", MS.LEVEL_B2_3 \"���ȍזڃR�[�h(B2-3)\""		//���ȍזڃR�[�h(B2-3)
//						+ ", MS.KEY1 \"��啪��̃L�[���[�h(1)\""		//��啪��̃L�[���[�h(1)
//						+ ", MS.KEY2 \"��啪��̃L�[���[�h(2)\""		//��啪��̃L�[���[�h(2)
//						+ ", MS.KEY3 \"��啪��̃L�[���[�h(3)\""		//��啪��̃L�[���[�h(3)
//						+ ", MS.KEY4 \"��啪��̃L�[���[�h(4)\""		//��啪��̃L�[���[�h(4)
//						+ ", MS.KEY5 \"��啪��̃L�[���[�h(5)\""		//��啪��̃L�[���[�h(5)
//						+ ", MS.KEY6 \"��啪��̃L�[���[�h(6)\""		//��啪��̃L�[���[�h(6)
//						+ ", MS.KEY7 \"��啪��̃L�[���[�h(7)\""		//��啪��̃L�[���[�h(7)
						+ ", MS.BIKO \"���l\""							//���l
						+ " FROM MASTER_SHINSAIN MS"
						+ ", SHINSAININFO SI"
						+ " WHERE SI.DEL_FLG = 0";
		
		StringBuffer query = new StringBuffer(select);
			
		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){			//�R�����ԍ�
			query.append(" AND MS.SHINSAIN_NO = '" + EscapeUtil.toSqlString(searchInfo.getShinsainNo()) + "'");
		}
//		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){	//���ȍזڃR�[�h
//			ArrayList arrBscd = this.separateString(searchInfo.getBunkaSaimokuCd());
//		
//			int cnt = arrBscd.size();
//			for(int i=0; i<cnt; i++){
//				String condi = (String)arrBscd.get(i);
//						
//				if(i == 0){
//					query.append(" AND (MS.LEVEL_A1 = '" + condi + "'");
//				}else{
//					query.append(" OR MS.LEVEL_A1 = '" + condi + "'");
//				}
//				query.append(" OR MS.LEVEL_B1_1 = '" + condi + "'");
//				query.append(" OR MS.LEVEL_B1_2 = '" + condi + "'");
//				query.append(" OR MS.LEVEL_B1_3 = '" + condi + "'");
//				query.append(" OR MS.LEVEL_B2_1 = '" + condi + "'");
//				query.append(" OR MS.LEVEL_B2_2 = '" + condi + "'");
//				query.append(" OR MS.LEVEL_B2_3 = '" + condi + "'");
//			}
//			if(cnt != 0){
//				query.append(") ");
//			}
//		}
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){				//�\���Ҏ����i����-���j
			query.append(" AND MS.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){				//�\���Ҏ����i����-���j
			query.append(" AND MS.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){					//�����@�փR�[�h
			query.append(" AND MS.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		if(searchInfo.getBunkaSaimokuCd() != null && !searchInfo.getBunkaSaimokuCd().equals("")){			//�זڔԍ�
			query.append(" AND (")
				.append("((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))")
				.append(" OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')")
					.append(" AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '" + EscapeUtil.toSqlString(searchInfo.getBunkaSaimokuCd()) + "'))")
			.append(")");
		}
//		if(searchInfo.getKeyword() != null && !searchInfo.getKeyword().equals("")){				//�L�[���[�h
//			ArrayList arrBscd = this.separateString(searchInfo.getKeyword());
//		
//			int cnt = arrBscd.size();
//			for(int i=0; i<cnt; i++){
//				String condi = EscapeUtil.toSqlString((String)arrBscd.get(i));
//						
//				if(i == 0){
//					query.append(" AND (MS.KEY1 LIKE '%" + condi + "%'");
//				}else{
//					query.append(" OR MS.KEY1 LIKE '%" + condi + "%'");
//				}
//				query.append(" OR MS.KEY2 LIKE '%" + condi + "%'");
//				query.append(" OR MS.KEY3 LIKE '%" + condi + "%'");
//				query.append(" OR MS.KEY4 LIKE '%" + condi + "%'");
//				query.append(" OR MS.KEY5 LIKE '%" + condi + "%'");
//				query.append(" OR MS.KEY6 LIKE '%" + condi + "%'");
//				query.append(" OR MS.KEY7 LIKE '%" + condi + "%'");				
//			}
//			if(cnt != 0){
//				query.append(")");
//			}
//		}
		if(searchInfo.getJigyoKubun() != null && !searchInfo.getJigyoKubun().equals("")){			//���Ƌ敪
			query.append(" AND MS.JIGYO_KUBUN = '" + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()) + "'");
		}
		
		//���������{�\�[�g�����i�R�����ԍ����j
		query.append(" AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)")	//7��
			 .append(" AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)")
			 .append(" ORDER BY MS.SHINSAIN_NO");
		
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

	/**
	 * �d���̃`�F�b�N.<br /><br />
	 * 
	 * <b>1.�@�֏��̎擾</b><br /><br />
	 * 
	 * �����@�փR�[�h����A�����@�֖����擾����B<br /><br />
	 * 
	 * 
	 * ������info�̕ϐ�ShozokuCd�̒l��"99999"(���̑�)�������ꍇ�ɂ́AkikanInfo�Ɉȉ��̒l��^����B<br /><br />
	 * 
	 * �@�EShozokuNameKanji	�c������info�̕ϐ�ShozokuName<br />
	 * �@�EShozokuNameEigo	�cnull<br />
	 * �@�EShozokuRyakusho	�c"���̑�"<br /><br />
	 * 
	 * ����ȊO�̏ꍇ�ɂ́A�ȉ���SQL���𔭍s���āA�L�[�Ɉ�v���鏊���@�֏����擾����B<br />
	 * �i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	MASTER_KIKAN
	 * WHERE
	 * 	SHOZOKU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>������info�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�̒�����A�����@�֖����������info�Ɋi�[����B<br /><br />
	 * 
	 * <b>2.�d���`�F�b�N</b><br /><br />
	 * �ȉ���SQL���𔭍s���āA�d���`�F�b�N���s���B<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND SI.DEL_FLG = 0		--�폜�t���O
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������info�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������info�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �������ʂ�1���ȏ゠�����ꍇ�ɂ͗�O��throw���A
	 * �d�����Ȃ������ꍇ�ɂ́Ainfo��ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param info ShinsainInfo
	 * @param mode String
	 * @return ShinsainInfo
	 * @throws ApplicationException
	 * @throws ValidationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainInfo, java.lang.String)
	 */
	public ShinsainInfo validate(UserInfo userInfo, ShinsainInfo info, String mode) throws ApplicationException, ValidationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//�G���[���ێ��p���X�g
			List errors = new ArrayList();

			//�e�R�[�h���疼�̂��擾���邽�߁AShinseishaMaintenance���g�p����
			ShinseishaMaintenance shinseishaMaintenance = new ShinseishaMaintenance();
			
			//---------------------------
			//�����@�փR�[�h�������@�֖��̃Z�b�g
			//---------------------------
			KikanInfo kikanInfo
				= shinseishaMaintenance.getKikanCodeValue(userInfo, info.getShozokuCd(), info.getShozokuName(), null);
			info.setShozokuName(kikanInfo.getShozokuNameKanji());

//			KikanInfo kikanInfo = new KikanInfo();
//			kikanInfo.setShubetuCd("0"); //�Ƃ肠�����O�Œ�
//			kikanInfo.setShozokuCd(info.getShozokuCd());
//			
//			if(info.getShozokuCd() != null && !info.getShozokuCd().equals("") && !info.getShozokuCd().equals("9999")) {
//				try {
//					kikanInfo = new MasterKikanInfoDao(userInfo).selectKikanInfo(connection,kikanInfo);
//					info.setShozokuName(kikanInfo.getShozokuNameKanji());
//				} catch (NoDataFoundException e) {
//					//������Ȃ������Ƃ��B
//					errors.add(
//						new ErrorInfo("errors.2001", new String[] { "�����@�փR�[�h" }));
//				}
//			}

//			//---------------------------
//			//���ǃR�[�h�����ǖ�
//			//---------------------------
//			BukyokuInfo bukyokuInfo = new BukyokuInfo();
//			bukyokuInfo.setBukyokuCd(info.getBukyokuCd());
//
//			if(info.getBukyokuCd() != null && !info.getBukyokuCd().equals("") && !info.getBukyokuCd().equals("999")) {
//				try {
//					bukyokuInfo =
//						new MasterBukyokuInfoDao(userInfo).selectBukyokuInfo(connection,bukyokuInfo);
//					info.setBukyokuName(bukyokuInfo.getBukaName());
//				} catch (NoDataFoundException e) {
//					//������Ȃ������Ƃ��B
//					errors.add(
//						new ErrorInfo("errors.2001", new String[] { "���ǃR�[�h" }));
//				}
//			}
//
//			//---------------------------
//			//�E��R�[�h���E�햼
//			//---------------------------
//			ShokushuInfo shokushuInfo = new ShokushuInfo();
//			shokushuInfo.setShokushuCd(info.getShokushuCd());
//
//			if(info.getShokushuCd() != null && !info.getShokushuCd().equals("") && !info.getShokushuCd().equals("999")) {
//				try {
//					shokushuInfo =
//						new MasterShokushuInfoDao(userInfo).selectShokushuInfo(connection,shokushuInfo);
//					info.setShokushuName(shokushuInfo.getShokushuName());
//				} catch (NoDataFoundException e) {
//					//������Ȃ������Ƃ��B
//					errors.add(
//						new ErrorInfo("errors.2001", new String[] { "�E��R�[�h" }));
//				}
//			}

			//2�d�o�^�`�F�b�N
			//�R�����ԍ��E���Ƌ敪�������ꍇ���`�F�b�N
			if(mode.equals(IMaintenanceName.ADD_MODE)) {
				ShinsainInfoDao dao = new ShinsainInfoDao(userInfo);
				int count = dao.countShinsainInfo(connection, info);
				//���łɓo�^����Ă���ꍇ
				if(count > 0){
					String[] error = {"�R����"};
					throw new ApplicationException("���łɐR�������o�^����Ă��܂��B", 	new ErrorInfo("errors.4007", error));			
				}
			}

			//-----���̓G���[���������ꍇ�͗�O���Ȃ���-----
			if (!errors.isEmpty()) {
				throw new ValidationException(
					"�R�����Ǘ��f�[�^�`�F�b�N���ɃG���[��������܂����B",
					errors);
			}

			return info;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R�����Ǘ��f�[�^�`�F�b�N����DB�G���[���������܂����B",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

	}

	/**
	 * �p�X���[�h�̍X�V.<br /><br />
	 * 
	 * <b>1.�R�������̎擾</b><br /><br />
	 * �ȉ���SQL���𔭍s���āA�R���������擾����B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������addInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������addInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * <b>2.�p�X���[�h�̃`�F�b�N</b><br /><br />
	 * 
	 * �擾�����R�������̃p�X���[�h�̒l���A��O������oldPassword�Ɠ��������`�F�b�N����B
	 * �قȂ�ꍇ�ɂ́A��O��throw����B<br /><br />
	 * 
	 * 
	 * <b>3.�p�X���[�h�̍X�V</b><br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�p�X���[�h�̍X�V���s���B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAININFO
	 * SET
	 * 	PASSWORD = ?
	 * WHERE
	 * 	SUBSTR(SHINSAIN_ID,3,8) = ?		--���Ƌ敪+�R�����ԍ�(7��)
	 * 	AND DEL_FLG = 0			--�폜�t���O</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�p�X���[�h</td><td>��l����newPassword���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>������pkInfo�̕ϐ�JigyoKubun��ShinsainNo��A���������̂��g�p����B</td></tr>
	 * </table><br />
	 * 
	 * ����Ƀp�X���[�h�̍X�V���s��ꂽ�ꍇ�ɂ́Atrue��ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinsainPk
	 * @param oldPassword String
	 * @param newPassword String
	 * @return �X�V���ʂ�boolean
	 * @throws ApplicationException
	 * @throws ValidationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#changePassword(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainPk, java.lang.String, java.lang.String)
	 */
	public boolean changePassword(UserInfo userInfo, ShinsainPk pkInfo, String oldPassword, String newPassword) throws ApplicationException, ValidationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			//---------------------------------------
			//�R�����̎擾
			//---------------------------------------
			ShinsainInfoDao dao = new ShinsainInfoDao(userInfo);
			ShinsainInfo info = dao.selectShinsainInfo(connection, pkInfo);

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
			if(dao.changePasswordShinsainInfo(connection,pkInfo,newPassword)){
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

//	/* (�� Javadoc)
//	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#separateString(java.lang.String)
//	 */
//	private ArrayList separateString(String str) throws ApplicationException {
//		str = str.trim();
//		ArrayList arrayl = new ArrayList();
//			
//		while(true){
//			int idx_low = str.indexOf(" ");										//���p�X�y�[�X�̃C���f�b�N�X
//			int idx_up = str.indexOf("�@");										//�S�p�X�y�[�X�̃C���f�b�N�X
//				
//			//���p�X�y�[�X�A�S�p�X�y�[�X�Ƃ��ɊY���Ȃ�
//			if(idx_low == -1 && idx_up == -1){
//				if(!str.equals("")){
//					arrayl.add(str);
//				}
//				break;
//			}
//			//�S�p�X�y�[�X���Y���Ȃ�
//			else if(idx_up == -1){
//				String condi = str.substring(0, idx_low);
//				if(!condi.equals("")){
//					arrayl.add(condi);
//				}
//				str = str.substring(idx_low+1);
//			}
//			//���p�X�y�[�X���Y���Ȃ�
//			else if(idx_low == -1){
//				String condi = str.substring(0, idx_up);
//				if(!condi.equals("")){
//					arrayl.add(condi);
//				}
//				str = str.substring(idx_up+1);
//			}
//			//���p�X�y�[�X�A�S�p�X�y�[�X�Ƃ��Y������
//			else{
//				//���p�X�y�[�X����ɊY������
//				if(idx_low < idx_up){
//					String condi = str.substring(0, idx_low);
//					if(!condi.equals("")){
//						arrayl.add(condi);
//					}
//					str = str.substring(idx_low+1);
//				}
//				//�S�p�X�y�[�X����ɊY������
//				else{
//					String condi = str.substring(0, idx_up);
//					if(!condi.equals("")){
//						arrayl.add(condi);
//					}
//					str = str.substring(idx_up+1);
//				}
//			}
//		}
//			
//		return arrayl;
//	}


	/**
	 * �p�X���[�h�̍X�V.<br /><br />
	 * 
	 * <b>1.�R�������̎擾</b><br /><br />
	 * �ȉ���SQL���𔭍s���āA�R���������擾����B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������addInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������addInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * <b>2.�p�X���[�h�̍Đݒ�</b><br /><br />
	 * �ȉ���SQL�����p�X���[�h���s���[�����擾���A�p�X���[�h�̍Ĕ��s���s���B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	RULEINFO A
	 * WHERE
	 * 	TAISHO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>������primaryKeys�̕ϐ�TaishoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �ȉ���SQL�����A�Ĕ��s�����p�X���[�h��o�^����B
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAININFO
	 * SET
	 * 	PASSWORD = ?
	 * WHERE
	 * 	SUBSTR(SHINSAIN_ID,3,8) = ?		--���Ƌ敪+�R�����ԍ�(7��)
	 * 	AND DEL_FLG = 0			--�폜�t���O</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�p�X���[�h</td><td>�Ĕ��s����newPassword���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>������pkInfo�̕ϐ�JigyoKubun��ShinsainNo��A���������̂��g�p����B</td></tr>
	 * </table><br />
	 * 
	 * <b>3.�R�������̎擾</b><br /><br />
	 * �ȉ���SQL���𔭍s���āA�ēx�R���������擾���A�ԋp����B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	SI.SHINSAIN_ID SHINSAIN_ID
	 * 	, MS.SHINSAIN_NO SHINSAIN_NO
	 * 	, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 	, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 	, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 	, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 	, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 	, MS.SHOZOKU_CD SHOZOKU_CD
	 * 	, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 	, MS.BUKYOKU_NAME BUKYOKU_NAME
	 * 	, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 * 	, MS.SOFU_ZIP SOFU_ZIP
	 * 	, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 * 	, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 * 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 	, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 	, MS.URL URL
	 * 	, MS.SENMON SENMON
	 * 	, MS.KOSHIN_DATE KOSHIN_DATE
	 * 	, MS.BIKO BIKO
	 * 	, SI.PASSWORD PASSWORD
	 * 	, SI.YUKO_DATE YUKO_DATE
	 * 	, SI.DEL_FLG DEL_FLG
	 * FROM
	 * 	MASTER_SHINSAIN MS
	 * 	, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 	AND MS.SHINSAIN_NO = ?
	 * 	AND MS.JIGYO_KUBUN = ?
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������addInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������addInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l��ShinsainInfo�Ɋi�[���ĕԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinsainPk
	 * @return ShinsainInfo
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#reconfigurePassword(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainPk)
	 */
	public ShinsainInfo reconfigurePassword(UserInfo userInfo, ShinsainPk pkInfo) throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�R�������̎擾
			//---------------------------------------
			ShinsainInfoDao dao = new ShinsainInfoDao(userInfo);
			ShinsainInfo info = dao.selectShinsainInfo(connection, pkInfo);

			//RULEINFO�e�[�u����胋�[���擾����
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSAIN);

			//�p�X���[�h���Đݒ肷��
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			success = dao.changePasswordShinsainInfo(connection,pkInfo,newPassword);

			//---------------------------------------
			//�R�����f�[�^�̎擾
			//---------------------------------------
			ShinsainInfo result = dao.selectShinsainInfo(connection, pkInfo);
			
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

	/**
	 * �˗����̍쐬.<br /><br />
	 * 
	 * 
	 * <b>1.CSV�f�[�^��List�̎擾</b><br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āACSV�f�[�^��List���擾����B<br />
	 * SQL����where��ɂ́A�������̊e�l��null���͋󕶎���łȂ��ꍇ�ɏ��������������Ă����B<br />
	 * (��������SQL���ƁA�����ɂȂ�ϐ���SQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	to_char(substr(MS.shinsain_no,1,4), '9990') ||	--�R�����ԍ�
	 * 		substr(MS.shinsain_no,5,1) || '�|' ||
	 * 		substr(MS.shinsain_no,6,2)		"�R�����ԍ�"
	 * 		--���T��(�ŏ��̂R���̃[���͏ȗ�)�ƌ�Q�����n�C�t���Ō���
	 * 
	 * 	,MS.NAME_KANJI_SEI				"�R������(����-��)"
	 * 	,MS.NAME_KANJI_MEI				"�R������(����-��)"
	 * 	,MS.SHOZOKU_NAME				"�R���������@�֖�"
	 * 	,MS.BUKYOKU_NAME				"�R�������ǖ�"
	 * 	,MS.SHOKUSHU_NAME				"�R�����E��"
	 * 	,MS.SOFU_ZIP				"���t��(�X�֔ԍ�)"
	 * 	,MS.SOFU_ZIPADDRESS			"���t��(�Z��)"
	 * 	,MS.SOFU_ZIPEMAIL				"���t��(Email)"
	 * 	,MS.SHOZOKU_TEL				"�d�b�ԍ�"
	 * 	,MS.SHOZOKU_FAX				"FAX�ԍ�"
	 * 	,MS.BIKO					"���l"
	 * 	,SI.SHINSAIN_ID				"�R����ID"
	 * 	,SI.PASSWORD				"�p�X���[�h"
	 * 	,TO_CHAR(SI.YUKO_DATE,'YYYY/MM/DD')		"�L������"
	 * FROM
	 * 	MASTER_SHINSAIN MS, SHINSAININFO SI
	 * WHERE
	 * 	SI.DEL_FLG = 0
	 * 
	 *				<b><span style="color:#002288">-- ���I�������� --</span></b>
	 *
	 * 	AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7)
	 * 	AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)
	 * ORDER BY
	 * 	MS.SHINSAIN_NO</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>ShinsainNo</td><td>AND MS.SHINSAIN_NO = '�R�����ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R������(��-������)</td><td>NameKanjiSei</td><td>AND MS.NAME_KANJI_SEI LIKE '%�R������(��-������)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R������(��-������)</td><td>NameKanjiMei</td><td>AND MS.NAME_KANJI_SEI LIKE '%�R������(��-������)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����R�[�h</td><td>ShozokuCd</td><td>AND MS.SHOZOKU_CD = '�����R�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�זڃR�[�h</td><td>BunkaSaimokuCd</td><td>AND (((SUBSTR(MS.SHINSAIN_NO, 1, 4) = '�זڃR�[�h')AND (SUBSTR(MS.SHINSAIN_NO, 5, 1) IN ('A', 'B')))OR ((SUBSTR(MS.SHINSAIN_NO, 1, 1) = '0')AND (SUBSTR(MS.SHINSAIN_NO, 2, 4) = '�זڃR�[�h'))</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>JigyoKubun</td><td>AND MS.JIGYO_KUBUN = '���Ƌ敪'</td></tr>
	 * </table><br/>
	 * 
	 * 
	 * <b>2.CSV�t�@�C���̏o��</b><br /><br />
	 * �t�@�C�����A�t�@�C���p�X���w�肵�āACSV�t�@�C���̏o�͂��s���B<br /><br />
	 * �@�E�t�@�C�����@�@�c"<b>SHINSAIRAI2</b>"<br />
	 * �@�E�t�@�C���p�X�@�c"<b>${shinsei_path}/work/irai2/�{���̓��t/</b>"<br />
	 * �@�@�@��${shinsei_path}�̒l��ApplicationSettings.properties�ɐݒ�<br><br>
	 * 
	 * <b>3.�t�@�C���̃R�s�[</b><br /><br />
	 * 
	 * �ȉ��̃p�X�ɁA�t�@�C���̃R�s�[���s���B<br /><br />
	 * �@�E${shinsei_path}/settings/irai2/shinsairai2.doc�@���@${shinsei_path}/work/irai2/�{���̓��t/shinsairai2.doc<br />
	 * �@�E${shinsei_path}/settings/irai2/$�@�@�@�@�@�@�@�@���@${shinsei_path}/work/irai2/�{���̓��t/$<br />
	 * �@�@�@��${shinsei_path}�̒l��ApplicationSettings.properties�ɐݒ�<br><br>
	 * 
	 * 
	 * <b>4.�t�@�C���̈��k</b><br /><br />
	 * 
	 * �t�@�C���p�X"${shinsei_path}/work/irai2/�{���̓��t/"�̂��̂����k����B
	 * ���k��������p�X�ŁA�t�@�C������"<b>SHINSAIRAI2_�{���̓��t</b>"�ƂȂ�B<br /><br />
	 * 
	 * 
	 * <b>5.�t�@�C���̕ԋp</b><br /><br />
	 * �쐬�������k�t�@�C����ǂݍ��݁A�ԋp����B<br /><br />
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsainSearchInfo
	 * @return FileResource
	 * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.IShinsainMaintenance#createIraisho(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinsainSearchInfo)
	 */
	public FileResource createIraisho(
		UserInfo userInfo,
		ShinsainSearchInfo searchInfo)
		throws ApplicationException
	{
		//DB���R�[�h�擾
		List csv_data = null;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			csv_data = new ShinsainInfoDao(userInfo).createCSV4Iraisho(connection, searchInfo);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		//-----------------------
		// CSV�t�@�C���o��
		//-----------------------
		String csvFileName = "SHINSAIRAI2";
		//2005/09/09 takano �t�H���_�����~���b�P�ʂɕύX�B�O�̂��ߓ����ɓ����������g�ݍ��݁B
		String filepath = null;
		synchronized(log){
			filepath = IRAI_WORK_FOLDER2 + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
		}
		CsvUtil.output(csv_data, filepath, csvFileName);
		
		//-----------------------
		// �˗����t�@�C���̃R�s�[
		//-----------------------
		FileUtil.fileCopy(new File(IRAI_FORMAT_PATH2 + IRAI_FORMAT_FILE_NAME2), new File(filepath + IRAI_FORMAT_FILE_NAME2));
		FileUtil.fileCopy(new File(IRAI_FORMAT_PATH2 + "$"), new File(filepath + "$"));
		
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

}
