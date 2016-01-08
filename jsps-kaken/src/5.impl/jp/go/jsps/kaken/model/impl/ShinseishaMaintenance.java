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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.IBukyokutantoMaintenance;
import jp.go.jsps.kaken.model.IShinseishaMaintenance;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.MasterBukyokuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterShokushuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.RuleInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseishaInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.BukyokuInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoPk;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.KikanInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaPk;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.model.vo.ShokushuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.CheckDiditUtil;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �\���ҏ��Ǘ����s���N���X�B
 * 
 * �g�p���Ă���e�[�u��<br /><br />
 * �@�E�\���ҏ��e�[�u��:SHINSEISHAINFO<br />
 * �@�@�@�\���҂̊�{�����Ǘ�����B<br /><br />
 * �@�E�����@�փ}�X�^:MASTER_KIKAN<br />
 * �@�@�@�����@�ւ̏����Ǘ�����B<br /><br />
 * �@�E�����@�֒S���ҏ��e�[�u��:SHOZOKUTANTOINFO<br />
 * �@�@�@�����@�֒S���҂̊�{�����Ǘ�����B<br /><br />
 * �@�E���ǃ}�X�^:MASTER_BUKYOKU<br />
 * �@�@�@���ǂ̏����Ǘ�����B<br /><br />
 * �@�E�E��}�X�^:MASTER_SHOKUSHU<br />
 * �@�@�@�E����Ǘ�����B<br /><br />
 * �@�E�h�c�p�X���[�h���s���[���e�[�u��:RULEINFO<br />
 * �@�@�@�eID�E�p�X���[�h�̔��s���[�����Ǘ�����B<br /><br />
 */
public class ShinseishaMaintenance implements IShinseishaMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(ShinseishaMaintenance.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public ShinseishaMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IShinseishaMaintenance
	//---------------------------------------------------------------------

	/**
	 * �\���ҏ��̒ǉ�.<br /><br />
	 * �\���ҏ��e�[�u���ɐV�K�Ńf�[�^�������A���̃f�[�^��ԋp����B<br /><br />
	 * 
	 * <b>1.���̒ǉ�</b><br /><br />
	 * ��������addInfo�ɁA���̒l�������Ă����B<br /><br />
	 * �@(1)�\����ID<br /><br />
	 * �@�@"<b>����N�x(2��)�{�����@�փR�[�h(5��)+�A��(5��)�{�`�F�b�N�f�W�b�g�L��(1��)</b>"��String���擾���AaddInfo�ɉ�����B<br /><br />
	 * 
	 * �@�@�@�E����N�x�@�@�@�@�@�@�@�c���݂̔N�x�𐼗�Ŏ擾�B<br />
	 * �@�@�@�E�����@�փR�[�h�@�@�@�@�c������addInfo����擾�B<br />
	 * �@�@�@�E�A�ԁ@�@�@�@�@�@�@�@�@�c�ȉ���SQL������擾�B<br />
	 * �@�@�@�E�`�F�b�N�f�W�b�g�L���@�c���\�b�h"<b>getCheckDigit(key,CheckDiditUtil.FORM_ALP)</b>"���擾�B<br />
	 * �@�@�@�@�@�@key�c����+�����@�փR�[�h+�A��<br />
	 * �@�@�@�@�@�@CheckDiditUtil.FORM_ALP�c"form_alp"<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	TO_CHAR(MAX(SUBSTR(SHINSEISHA_ID,8,5)) + 1,'FM00000') COUNT
	 * FROM
	 * 	SHINSEISHAINFO
	 * WHERE
	 * 	SHOZOKU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������addInfo�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * �@(2)�p�X���[�h<br /><br />
	 * �@�@�ȉ���SQL�����烋�[�����擾���A�p�X���[�h�̍쐬���s���B<br />
	 * �@�@(�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
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
	 * 		<tr bgcolor="#FFFFFF"><td>�Ώێ�ID</td><td>1(ITaishoId.SHINSEISHA)���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �@�@�擾�����p�X���[�h��addInfo�ɉ�����B<br /><br />
	 * 
	 * �@(3)�L�����t<br /><br />
	 * �@�@addInfo�̗L�����t�̒l��null�������ꍇ�̂݁A
	 * ��L��SQL���𔭍s���āA�擾�����l����L�����t��addInfo�Ɋi�[����B<br /><br />
	 * 
	 * 
	 * �@(4)�����t���O<br /><br />
	 * �@�@addInfo�̔����t���O�̒l��null���͋󕶎��񂾂����ꍇ�̂݁A"0"(�����\���s��)���i�[����B<br /><br />
	 * 
	 * 
	 * <b>2.�\���ҏ��̑}��</b><br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�\���ҏ��e�[�u���Ƀf�[�^��}������B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO SHINSEISHAINFO
	 * 	(SHINSEISHA_ID
	 * 	, SHOZOKU_CD
	 * 	, SHOZOKU_NAME
	 * 	, SHOZOKU_NAME_EIGO
	 * 	, SHOZOKU_NAME_RYAKU
	 * 	, PASSWORD
	 * 	, NAME_KANJI_SEI
	 * 	, NAME_KANJI_MEI
	 * 	, NAME_KANA_SEI
	 * 	, NAME_KANA_MEI
	 * 	, NAME_RO_SEI
	 * 	, NAME_RO_MEI
	 * 	, BUKYOKU_CD
	 * 	, BUKYOKU_NAME
	 * 	, BUKYOKU_NAME_RYAKU
	 * 	, KENKYU_NO
	 * 	, SHOKUSHU_CD
	 * 	, SHOKUSHU_NAME_KANJI
	 * 	, SHOKUSHU_NAME_RYAKU
	 * 	, HIKOBO_FLG
	 * 	, BIKO
	 * 	, YUKO_DATE
	 * 	, DEL_FLG)
	 * VALUES
	 * 	(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������addInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������addInfo�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(�a��)</td><td>������addInfo�̕ϐ�ShozokuName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(�p��)</td><td>������addInfo�̕ϐ�ShozokuNameEigo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(����)</td><td>������addInfo�̕ϐ�ShozokuNameRyaku���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�p�X���[�h</td><td>������addInfo�̕ϐ�Password���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������addInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������addInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������addInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������addInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(���[�}��-��)</td><td>������addInfo�̕ϐ�NameRoSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(���[�}��-��)</td><td>������addInfo�̕ϐ�NameRoSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�(�R�[�h)</td><td>������addInfo�̕ϐ�BukyokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�(�a��)</td><td>������addInfo�̕ϐ�BukyokuName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�(����)</td><td>������addInfo�̕ϐ�BukyokuNameRyaku���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������addInfo�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E���R�[�h</td><td>������addInfo�̕ϐ�ShokushuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E��(�a��)</td><td>������addInfo�̕ϐ�ShokushuNameKanji���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E��(����)</td><td>������addInfo�̕ϐ�ShokushuNameRyaku���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����t���O</td><td>������addInfo�̕ϐ�HikoboFlg���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>������addInfo�̕ϐ�Biko���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�L�����t</td><td>������addInfo�̕ϐ�YukoDate���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>0</td></tr>
	 * </table><br />
	 * 
	 * <b>3.�\���ҏ��̎擾</b><br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�\���ҏ��e�[�u������f�[�^���擾����B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SHINSEISHA_ID
	 * 	,A.SHOZOKU_CD
	 * 	,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME
	 * 	,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO
	 * 	,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU
	 * 	,A.PASSWORD
	 * 	,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI
	 * 	,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI
	 * 	,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI
	 * 	,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI
	 * 	,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI
	 * 	,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI
	 * 	,A.BUKYOKU_CD
	 * 	,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME
	 * 	,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU
	 * 	,A.KENKYU_NO
	 * 	,A.SHOKUSHU_CD
	 * 	,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI
	 * 	,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU
	 * 	,A.HIKOBO_FLG
	 * 	,NVL(A.BIKO,'') BIKO
	 * 	,A.YUKO_DATE
	 * 	,A.DEL_FLG
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������addInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l��ShinseishaInfo�Ɋi�[���A�ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param addInfo ShinseishaInfo
	 * @return �o�^�����\���ҏ�������ShinseishaInfo
	 * @throws ApplicationException
	 */
	public synchronized ShinseishaInfo insert(UserInfo userInfo, ShinseishaInfo addInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

            //---------------------------------------
            //�\���ҏ��f�[�^�A�N�Z�X�I�u�W�F�N�g
            //---------------------------------------
            ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);

			//---------------------------------------
			//�L�[���́i�\����ID�j���쐬
			//����N�x�i2���j+ �����@�փR�[�h�i5���j+ �A�ԁi5���j+ �`�F�b�N�f�W�b�g�i�P���j
			//---------------------------------------
			String wareki = new DateUtil().getNendo();
			String key = DateUtil.changeWareki2Seireki(wareki)
						+ addInfo.getShozokuCd()
						+ dao.getSequenceNo(connection,addInfo.getShozokuCd());
			
            //�\����ID���擾					
			String shinseishaId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
			addInfo.setShinseishaId(shinseishaId);

			//RULEINFO�e�[�u����胋�[���擾����
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSEISHA);
			
			//---------------------------------------
			//�p�X���[�h���̍쐬
			//---------------------------------------
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			addInfo.setPassword(newPassword);

			//---------------------------------------
			//�L�����t�����̍쐬(�S���җp)
			//---------------------------------------
			if(addInfo.getYukoDate() == null) {
				Date newYukoDate = rureInfoDao.selectRuleInfo(connection, rulePk).getYukoDate();
				addInfo.setYukoDate(newYukoDate);
			}

			//---------------------------------------
			//����剞��t���O(�S���җp)
			//---------------------------------------
			if(addInfo.getHikoboFlg() == null || addInfo.getHikoboFlg().equals("")) {
				addInfo.setHikoboFlg("0");
			}

			//---------------------------------------
			//�\���ҏ��̒ǉ�
			//---------------------------------------
			dao.insertShinseishaInfo(connection,addInfo);		

			//---------------------------------------
			//�o�^�f�[�^�̎擾
			//---------------------------------------
			ShinseishaInfo result = dao.selectShinseishaInfo(connection, addInfo);

			//---------------------------------------
			//�o�^����I��
			//---------------------------------------
			success = true;			
			
			return result;
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�\���ҊǗ��f�[�^�o�^����DB�G���[���������܂����B",
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
					"�\���ҊǗ��f�[�^�o�^����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �\���ҏ��̍X�V.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�\���ҏ��e�[�u���̍X�V���s���B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEISHAINFO
	 * SET
	 * 	SHOZOKU_CD = ?
	 * 	, SHOZOKU_NAME = ?
	 * 	, SHOZOKU_NAME_EIGO = ?
	 * 	, SHOZOKU_NAME_RYAKU = ?
	 * 	, PASSWORD = ?
	 * 	, NAME_KANJI_SEI = ?
	 * 	, NAME_KANJI_MEI = ?
	 * 	, NAME_KANA_SEI = ?
	 * 	, NAME_KANA_MEI = ?
	 * 	, NAME_RO_SEI = ?
	 * 	, NAME_RO_MEI = ?
	 * 	, BUKYOKU_CD = ?
	 * 	, BUKYOKU_NAME= ?
	 * 	, BUKYOKU_NAME_RYAKU= ?
	 * 	, KENKYU_NO = ?
	 * 	, SHOKUSHU_CD = ?
	 * 	, SHOKUSHU_NAME_KANJI = ?
	 * 	, SHOKUSHU_NAME_RYAKU = ?
	 * 	, HIKOBO_FLG = ?
	 * 	, BIKO = ?
	 * 	, YUKO_DATE = ?
	 * 	, DEL_FLG = ?
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������updateInfo�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(�a��)</td><td>������updateInfo�̕ϐ�ShozokuName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(�p��)</td><td>������updateInfo�̕ϐ�ShozokuNameEigo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(����)</td><td>������updateInfo�̕ϐ�ShozokuNameRyaku���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�p�X���[�h</td><td>������updateInfo�̕ϐ�Password���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������updateInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������updateInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������updateInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������updateInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(���[�}��-��)</td><td>������updateInfo�̕ϐ�NameRoSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(���[�}��-��)</td><td>������updateInfo�̕ϐ�NameRoSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�(�R�[�h)</td><td>������updateInfo�̕ϐ�BukyokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�(�a��)</td><td>������updateInfo�̕ϐ�BukyokuName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�(����)</td><td>������updateInfo�̕ϐ�BukyokuNameRyaku���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������updateInfo�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E���R�[�h</td><td>������updateInfo�̕ϐ�ShokushuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E��(�a��)</td><td>������updateInfo�̕ϐ�ShokushuNameKanji���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E��(����)</td><td>������updateInfo�̕ϐ�ShokushuNameRyaku���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����t���O</td><td>������updateInfo�̕ϐ�HikoboFlg���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>������updateInfo�̕ϐ�Biko���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�L�����t</td><td>������updateInfo�̕ϐ�YukoDate���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>0</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������updateInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param updateInfo ShinseishaInfo
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo, ShinseishaInfo updateInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�\���ҏ��̍X�V
			//---------------------------------------
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			dao.updateShinseishaInfo(connection, updateInfo);
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�\���ҊǗ��f�[�^�X�V����DB�G���[���������܂����B",
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
					"�\���ҊǗ��f�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �\���ҏ��̍폜.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�\���ҏ��e�[�u���̘_���폜���s���B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEISHAINFO
	 * SET
	 * 	DEL_FLG = 1
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������deleteInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param deleteInfo ShinseishaInfo
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo, ShinseishaInfo deleteInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�\���ҏ��̍X�V
			//---------------------------------------
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			dao.deleteFlgShinseishaInfo(connection, deleteInfo);
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�\���ҊǗ��f�[�^�폜����DB�G���[���������܂����B",
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
					"�\���ҊǗ��f�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �\���ҏ��̎擾.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�\���ҏ��e�[�u������f�[�^���擾����B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SHINSEISHA_ID
	 * 	,A.SHOZOKU_CD
	 * 	,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME
	 * 	,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO
	 * 	,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU
	 * 	,A.PASSWORD
	 * 	,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI
	 * 	,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI
	 * 	,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI
	 * 	,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI
	 * 	,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI
	 * 	,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI
	 * 	,A.BUKYOKU_CD
	 * 	,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME
	 * 	,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU
	 * 	,A.KENKYU_NO
	 * 	,A.SHOKUSHU_CD
	 * 	,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI
	 * 	,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU
	 * 	,A.HIKOBO_FLG
	 * 	,NVL(A.BIKO,'') BIKO
	 * 	,A.YUKO_DATE
	 * 	,A.DEL_FLG
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������ShinseishaPk�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l��ShinseishaInfo�Ɋi�[���A�ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinseishaPk
	 * @return ShinseishaInfo
	 * @throws ApplicationException
	 */
	public ShinseishaInfo select(UserInfo userInfo, ShinseishaPk pkInfo)
			throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			return dao.selectShinseishaInfo(connection, pkInfo);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�\���ҊǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * CSV�f�[�^List�̎擾.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āACSV�o�̓f�[�^�̎擾���s���A�ԋp����B<br />
	 * List�̏��߂̗v�f�ɂ́A�J������������B<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SHINSEISHA_ID		"�\����ID"			--ID
	 * 	, A.SHOZOKU_CD		"�����@�֖�(�R�[�h)"	-�����@�֖�(�R�[�h)
	 * 	, A.SHOZOKU_NAME		"�����@�֖�(�a��)"	--�����@�֖�(�a��)
	 * 	, A.SHOZOKU_NAME_EIGO	"�����@�֖�(�p��)"	--�����@�֖�(�p��)
	 * 	, A.SHOZOKU_NAME_RYAKU	"�����@�֖�(����)"	--�����@�֖�(����)
	 * 	, A.NAME_KANJI_SEI		"����(������-��)"	--����(������-��)
	 * 	, A.NAME_KANJI_MEI		"����(������-��)"	--����(������-��)
	 * 	, A.NAME_KANA_SEI		"����(�t���K�i-��)"	--����(�t���K�i-��)
	 * 	, A.NAME_KANA_MEI		"����(�t���K�i-��)"	--����(�t���K�i-��)
	 * 	, A.NAME_RO_SEI		"����(���[�}��-��)"	--����(���[�}��-��)
	 * 	, A.NAME_RO_MEI		"����(���[�}��-��)"	--����(���[�}��-��)
	 * 	, A.KENKYU_NO		"�����Ҕԍ�"		--�����Ҕԍ�
	 * 	, A.BUKYOKU_CD		"���ǖ�(�R�[�h)"	--���ǖ�(�R�[�h)
	 * 	, A.BUKYOKU_NAME		"���ǖ�(�a��)"	--���ǖ�(�a��)
	 * 	, A.BUKYOKU_NAME_RYAKU	"���ǖ�(����)"	--���ǖ�(����)
	 * 	, A.SHOKUSHU_CD		"�E��(�R�[�h)"		--�E��(�R�[�h)
	 * 	, A.SHOKUSHU_NAME_KANJI	"�E��(�a��)"	--�E��(�a��)
	 * 	, A.SHOKUSHU_NAME_RYAKU	"�E��(����)"	--�E��(����)
	 * 	, A.HIKOBO_FLG		"�����\���t���O"--�����\���ۃt���O
	 * 	, A.BIKO			"���l"			--���l
	 * 	, TO_CHAR(A.YUKO_DATE,
	 * 		'YYYY/MM/DD')	"�L������"		--�L������
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	DEL_FLG = 0				--�폜�t���O
	 * 
	 * 	<b><span style="color:#002288">-- ���I�������� --</span></b>
	 * 
	 * ORDER BY
	 * 	SHINSEISHA_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\����ID</td><td>ShinseishaId</td><td>AND SHINSEISHA_ID = '�\����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖�(�R�[�h)</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '�����@�֖�(�R�[�h)'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖�</td><td>ShozokuName</td><td>AND (SHOZOKU_NAME LIKE '%�����@�֖�%' OR SHOZOKU_NAME_RYAKU LIKE '%�����@�֖�%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(����-��)</td><td>NameKanjiSei</td><td>AND NAME_KANJI_SEI LIKE '%�\���Ҏ���(����-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(����-��)</td><td>NameKanjiMei</td><td>AND NAME_KANJI_MEI LIKE '%�\���Ҏ���(����-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(�t���K�i-��)</td><td>NameKanaSei</td><td>AND NAME_KANA_SEI LIKE '%�\���Ҏ���(�t���K�i-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(�t���K�i-��)</td><td>NameKanaMei</td><td>AND NAME_KANA_MEI LIKE '%�\���Ҏ���(�t���K�i-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(���[�}��-��)</td><td>NameRoSei</td><td>AND UPPER(NAME_RO_SEI) LIKE '%�\���Ҏ���(���[�}��-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(���[�}��-��)</td><td>NameRoMei</td><td>AND UPPER(NAME_RO_SEI) LIKE '%�\���Ҏ���(���[�}��-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><td>BukyokuCd</td><td>AND BUKYOKU_CD = '���ǃR�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>KenkyuNo</td><td>AND KENKYU_NO = '�����Ҕԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����t���O</td><td>HikoboFlg</td><td>AND HIKOBO_FLG = '�����t���O'</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseishaSearchInfo
	 * @return CSV�o�̓f�[�^��List
	 * @throws ApplicationException
	 */
	public List searchCsvData(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
			throws ApplicationException {
	
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String select =
			"SELECT "
				+ " A.SHINSEISHA_ID \"�����ID\""						//ID
				+ ", A.SHOZOKU_CD \"���������@�֖��i�ԍ��j\""				//�����@�֖��i�R�[�h�j
				+ ", A.SHOZOKU_NAME \"���������@�֖��i�a���j\""			//�����@�֖��i�a���j
				+ ", A.SHOZOKU_NAME_EIGO \"���������@�֖��i�p���j\""		//�����@�֖��i�p���j
				+ ", A.SHOZOKU_NAME_RYAKU \"���������@�֖��i���́j\""		//�����@�֖��i���́j
//				+ ", A.PASSWORD \"�p�X���[�h\""							//�p�X���[�h
				+ ", A.NAME_KANJI_SEI \"�����i������-���j\""				//�����i������-���j
				+ ", A.NAME_KANJI_MEI \"�����i������-���j\""				//�����i������-���j
				+ ", A.NAME_KANA_SEI \"�����i�t���K�i-���j\""				//�����i�t���K�i-���j
				+ ", A.NAME_KANA_MEI \"�����i�t���K�i-���j\""				//�����i�t���K�i-���j
				//2005/04/23 �C�� -----------------------------��������
				//���R csv�o�͎d�l�ύX�̂���
//				+ ", A.NAME_RO_SEI \"�����i���[�}��-���j\""				//�����i���[�}��-���j
//				+ ", A.NAME_RO_MEI \"�����i���[�}��-���j\""				//�����i���[�}��-���j
				//2005/04/23 �C�� -----------------------------�����܂�
				+ ", A.KENKYU_NO \"�����Ҕԍ�\""							//�����Ҕԍ�
				+ ", A.BUKYOKU_CD \"���ǖ��i�ԍ��j\""					//���ǖ��i�R�[�h�j
				+ ", A.BUKYOKU_NAME \"���ǖ��i�a���j\""					//���ǖ��i�a���j
				+ ", A.BUKYOKU_NAME_RYAKU \"���ǖ��i���́j\""			//���ǖ��i���́j
//				+ ", A.SHUBETU_CD \"���ǎ�ʁi�R�[�h�j\""					//���ǎ�ʁi�R�[�h�j
//				+ ", A.OTHER_BUKYOKU \"���ǎ��\""						//���ǎ��
				+ ", A.SHOKUSHU_CD \"�E���i�ԍ��j\""						//�E���i�R�[�h�j
				+ ", A.SHOKUSHU_NAME_KANJI \"�E���i�a���j\""				//�E���i�a���j
				+ ", A.SHOKUSHU_NAME_RYAKU \"�E���i���́j\""				//�E���i���́j
				+ ", A.HIKOBO_FLG \"����剞��t���O\""					//����剞��t���O
				+ ", A.BIKO \"���l\""									//���l
				+ ", TO_CHAR(A.YUKO_DATE, 'YYYY/MM/DD') \"�L������\""	//�L������
				//2005/04/23 �ǉ� -----------------------------��������
				//���R csv�o�͎d�l�ύX�̂���
				+ ", TO_CHAR(A.BIRTHDAY, 'YYYY/MM/DD') \"���N����\""	//���N����
				+ ", A.HAKKOSHA_ID \"���s��ID\""					//���s��ID
				+ ", TO_CHAR(A.HAKKO_DATE, 'YYYY/MM/DD') \"���s��\""	//���s��
				+ ", B.SEIBETSU \"����\""					//����
				+ ", B.GAKUI \"�w��\""					//�w��
				//2006/02/10 �ǉ�
				+",  B.OUBO_SHIKAKU \"���厑�i\""    //���厑�i
				+ ", TO_CHAR(B.KOSHIN_DATE, 'YYYY/MM/DD') \"�f�[�^�X�V��\""					//�f�[�^�X�V��
				//2005/04/23 �ǉ� -----------------------------�����܂�
//				+ ", A.DEL_FLG \"�폜�t���O\""							//�폜�t���O
				//2005.07.27 iso �قȂ鏊���@�ւœ��������Ҕԍ�����������Əo�̓f�[�^�����d�o�͂����o�O���C��
//				+ " FROM SHINSEISHAINFO A LEFT JOIN MASTER_KENKYUSHA B ON A.KENKYU_NO = B.KENKYU_NO"
				//+ " FROM SHINSEISHAINFO A LEFT JOIN MASTER_KENKYUSHA B ON A.KENKYU_NO = B.KENKYU_NO AND A.SHOZOKU_CD = B.SHOZOKU_CD"
				//2005/09/12 �����҃}�X�^�ɑ��݂��Ȃ��ꍇ�͉���҈ꗗ�ɕ\�����Ȃ�
				+ " FROM SHINSEISHAINFO A "
				+ "    INNER JOIN MASTER_KENKYUSHA B "
				+ "      ON  A.KENKYU_NO  = B.KENKYU_NO "
				+ "      AND A.SHOZOKU_CD = B.SHOZOKU_CD "
				+ "      AND B.DEL_FLG    = 0 "
				+ " WHERE A.DEL_FLG = 0"									//�폜�t���O
				;
				StringBuffer query = new StringBuffer(select);

			if(searchInfo.getShinseishaId() != null && !searchInfo.getShinseishaId().equals("")){	//�\����ID
				query.append(" AND A.SHINSEISHA_ID = '" + EscapeUtil.toSqlString(searchInfo.getShinseishaId()) +"'");
			}
			if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){		//�����@�փR�[�h
				query.append(" AND A.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) +"'");
			}
			//�����@�֖��͘a���Ɨ��̗̂�������������
			if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){	//�����@�֖�
				query.append(" AND (A.SHOZOKU_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName())
					+ "%' OR A.SHOZOKU_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%')");
			}
			if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	//�\���Ҏ����i����-���j
				query.append(" AND A.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
			}
			if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	//�\���Ҏ����i����-���j
				query.append(" AND A.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
			}
			if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	//�\���Ҏ����i�t���K�i-���j
				query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
			}
			if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){	//�\���Ҏ����i�t���K�i-���j
				query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
			}
			//2005/04/27 �폜 ��������-----------------------------------------------
			//�s�v�����̂���
			/*
			if(searchInfo.getNameRoSei() != null && !searchInfo.getNameRoSei().equals("")){		//�\���Ҏ����i���[�}��-���j
				query.append(" AND UPPER(A.NAME_RO_SEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()) + "%'");
			}
			if(searchInfo.getNameRoMei() != null && !searchInfo.getNameRoMei().equals("")){		//�\���Ҏ����i���[�}��-���j
				query.append(" AND UPPER(A.NAME_RO_MEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()) + "%'");
			}
			*/
			//�폜 �����܂�---------------------------------------------------------
			if(searchInfo.getBukyokuCd() != null && !searchInfo.getBukyokuCd().equals("")){		//���ǃR�[�h
				query.append(" AND A.BUKYOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBukyokuCd()) +"'");
			}
			if(searchInfo.getKenkyuNo() != null && !searchInfo.getKenkyuNo().equals("")){			//�����Ҕԍ�
				query.append(" AND A.KENKYU_NO = '" + EscapeUtil.toSqlString(searchInfo.getKenkyuNo()) +"'");
			}
			if(searchInfo.getHikoboFlg() != null && !searchInfo.getHikoboFlg().equals("")){		//�����\���ۃt���O
				query.append(" AND A.HIKOBO_FLG = '" + EscapeUtil.toSqlString(searchInfo.getHikoboFlg()) +"'");
			}

			//�\�[�g���i�\����ID�̏����j
			query.append(" ORDER BY A.SHINSEISHA_ID");

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
	 * �\���҂�Page���̎擾.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�\���҂�Page�����擾����B
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	SHINSEISHAINFO A
	 * 
	 * INNER JOIN --------------------------------�S�����ǊǗ��e�[�u���Ƀf�[�^������ꍇ�ɒǉ� 
	 * 	TANTOBUKYOKUKANRI TANTO 
	 * ON 
	 * 	TANTO.SHOZOKU_CD = SHINSEI.SHOZOKU_CD 
	 * AND 
	 * TANTO.BUKYOKU_CD = SHINSEI.BUKYOKU_CD
	 * 
	 * 
	 * WHERE
	 * 	DEL_FLG = 0
	 * 
	 * 	<b><span style="color:#002288">-- ���I�������� --</span></b>
	 * 
	 * ORDER BY
	 * 	SHINSEISHA_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\����ID</td><td>ShinseishaId</td><td>AND SHINSEISHA_ID = '�\����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖�(�R�[�h)</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '�����@�֖�(�R�[�h)'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖�</td><td>ShozokuName</td><td>AND (SHOZOKU_NAME LIKE '%�����@�֖�%' OR SHOZOKU_NAME_RYAKU LIKE '%�����@�֖�%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(����-��)</td><td>NameKanjiSei</td><td>AND NAME_KANJI_SEI LIKE '%�\���Ҏ���(����-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(����-��)</td><td>NameKanjiMei</td><td>AND NAME_KANJI_MEI LIKE '%�\���Ҏ���(����-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(�t���K�i-��)</td><td>NameKanaSei</td><td>AND NAME_KANA_SEI LIKE '%�\���Ҏ���(�t���K�i-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(�t���K�i-��)</td><td>NameKanaMei</td><td>AND NAME_KANA_MEI LIKE '%�\���Ҏ���(�t���K�i-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(���[�}��-��)</td><td>NameRoSei</td><td>AND UPPER(NAME_RO_SEI) LIKE '%�\���Ҏ���(���[�}��-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(���[�}��-��)</td><td>NameRoMei</td><td>AND UPPER(NAME_RO_SEI) LIKE '%�\���Ҏ���(���[�}��-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><td>BukyokuCd</td><td>AND BUKYOKU_CD = '���ǃR�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>KenkyuNo</td><td>AND KENKYU_NO = '�����Ҕԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����t���O</td><td>HikoboFlg</td><td>AND HIKOBO_FLG = '�����t���O'</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́A�񖼂��L�[��Map�ɃZ�b�g����AList�ɃZ�b�g�����B
	 * ����List���i�[����Page��ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseishaSearchInfo
	 * @return �\���ҏ���Page
	 */
	public Page search(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
			throws ApplicationException {
		
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return search(userInfo, searchInfo, connection);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
//ADD�@START 2007/07/25 BIS �����_  
	public Page getKenkyushaInfoByKenkyuNo(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
			throws ApplicationException {
		
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return getKenkyushaInfoByKenkyuNo(userInfo, searchInfo, connection);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/** �擾�����l�́A�񖼂��L�[��Map�ɃZ�b�g����AList�ɃZ�b�g�����B	 
	 * ����List���i�[����Page��ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseishaSearchInfo
	 * @return �����ҏ���Page
	 */
	protected Page getKenkyushaInfoByKenkyuNo(UserInfo userInfo, ShinseishaSearchInfo searchInfo, Connection connection)
	throws ApplicationException {
	
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		String select = 
			"SELECT * FROM (MASTER_KENKYUSHA KENKYU) "
			;
			select += "WHERE ";
		
		StringBuffer query = new StringBuffer(select);
		
		query.append(" KENKYU.KENKYU_NO = '" + EscapeUtil.toSqlString(searchInfo.getKenkyuNo()) +"'");//�����Ҕԍ�
				
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		try {
			return SelectUtil.selectPageInfo(connection,searchInfo, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����ҊǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		}
	}	
	
//ADD�@END�@ 2007/07/25 BIS �����_	
	/**
	 * �\���҂�Page���̎擾.<br /><br />
	 * ����g�����U�N�V�����Ŏ��{�������ꍇ�p�B
	 * @param userInfo
	 * @param searchInfo
	 * @param connection
	 * @return
	 * @throws ApplicationException
	 */
	protected Page search(UserInfo userInfo, ShinseishaSearchInfo searchInfo, Connection connection)
			throws ApplicationException {
		
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		
		//2005/04/20 �ǉ� ��������----------------------------------------------
		//���ǒS�����ɋ����ꂽ���ǂ��ǂ����̏�����ǉ�
		if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
			//���ǒS���҂̂Ƃ��A���������̕��ǃR�[�h�������̒S�����`�F�b�N����
			BukyokutantoInfo info = userInfo.getBukyokutantoInfo();
			
			if(info.getTantoFlg()){
				if(searchInfo.getBukyokuCd() != null && !searchInfo.getBukyokuCd().equals("")){
					IBukyokutantoMaintenance bukyokutantoMaintenance = new BukyokutantoMaintenance();
					
					//�L�[�̃Z�b�g
					BukyokutantoPk pkInfo = new BukyokutantoPk();
					pkInfo.setBukyokutantoId(info.getBukyokutantoId());
					pkInfo.setBukyokuCd(searchInfo.getBukyokuCd());
		            
					BukyokutantoInfo[] tanto = bukyokutantoMaintenance.select(userInfo,pkInfo);
		            
					if(tanto.length == 0){
						throw new NoDataFoundException(
								"���O�C�����[�U�̒S�����镔�ǂł͂���܂���B"
									+ "�����L�[�F���ǒS����ID'" + pkInfo.getBukyokutantoId() + "'"
									+ " �S�����ǃR�[�h'" + pkInfo.getBukyokuCd()
									+ "'", new ErrorInfo("errors.authority"));
					}
				}
			}
		}
		//�ǉ� �����܂�---------------------------------------------------------		
		
		// 2005/04/08 �폜�@��������--------------------------------------------
		// ���R ���ǒS���҂̐\���ҏ��擾�̂���
		// String select= "SELECT * FROM SHINSEISHAINFO SHINSEI WHERE DEL_FLG = 0";
		// �폜 �����܂�--------------------------------------------------------
		
		// 2005/04/08 �ǉ��@��������-------------------------------------------
		// ���R ���ǒS���҂̐\���ҏ��擾�̂���
		String select = 
		//2005/09/12 �����҃}�X�^�ɑ��݂��Ȃ��ꍇ�͉���҈ꗗ�ɕ\�����Ȃ�
		//"SELECT * FROM SHINSEISHAINFO SHINSEI ";
		"SELECT * FROM (SHINSEISHAINFO SHINSEI "
						+" INNER JOIN MASTER_KENKYUSHA MK "
						+" ON  SHINSEI.KENKYU_NO  = MK.KENKYU_NO "
						+" AND SHINSEI.SHOZOKU_CD = MK.SHOZOKU_CD "
						+" AND MK.DEL_FLG = 0 ) "
						;
		if(userInfo.getBukyokutantoInfo() != null && userInfo.getBukyokutantoInfo().getTantoFlg()){
			select = select 
						+ "INNER JOIN TANTOBUKYOKUKANRI TANTO " 
						+ "ON TANTO.SHOZOKU_CD = SHINSEI.SHOZOKU_CD "
						+ "AND TANTO.BUKYOKU_CD = SHINSEI.BUKYOKU_CD "
						//2005/04/20�@�ǉ� ��������------------------------------------------
						//���R �����s���̂��� 
						+ "AND TANTO.BUKYOKUTANTO_ID = '"+EscapeUtil.toSqlString(userInfo.getBukyokutantoInfo().getBukyokutantoId())+"' ";
						//�ǉ� �����܂�------------------------------------------------------
		}
			select += "WHERE SHINSEI.DEL_FLG = 0";
		//	�ǉ� �����܂�--------------------------------------------------------
			
		StringBuffer query = new StringBuffer(select);
	
		if(searchInfo.getShinseishaId() != null && !searchInfo.getShinseishaId().equals("")){	//�\����ID
			query.append(" AND SHINSEI.SHINSEISHA_ID = '" + EscapeUtil.toSqlString(searchInfo.getShinseishaId()) +"'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){		//�����@�փR�[�h
			query.append(" AND SHINSEI.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) +"'");
		}
		//�����@�֖��͘a���Ɨ��̗̂�������������
		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){	//�����@�֖�
			query.append(" AND (SHINSEI.SHOZOKU_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName())
				+ "%' OR SHINSEI.SHOZOKU_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%')");
		}
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	//�\���Ҏ����i����-���j
			query.append(" AND SHINSEI.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	//�\���Ҏ����i����-���j
			query.append(" AND SHINSEI.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	//�\���Ҏ����i�t���K�i-���j
			query.append(" AND SHINSEI.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
		}
		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){	//�\���Ҏ����i�t���K�i-���j
			query.append(" AND SHINSEI.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
		}
		//2005/04/27 �폜 ��������-----------------------------------------------
		//�s�v�����̂���
		/*
		if(searchInfo.getNameRoSei() != null && !searchInfo.getNameRoSei().equals("")){		//�\���Ҏ����i���[�}��-���j
			query.append(" AND SHINSEI.UPPER(NAME_RO_SEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoSei().toUpperCase()) + "%'");
		}
		if(searchInfo.getNameRoMei() != null && !searchInfo.getNameRoMei().equals("")){		//�\���Ҏ����i���[�}��-���j
			query.append(" AND SHINSEI.UPPER(NAME_RO_MEI) LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameRoMei().toUpperCase()) + "%'");
		}
		*/
		//�폜 �����܂�----------------------------------------------
		if(searchInfo.getBukyokuCd() != null && !searchInfo.getBukyokuCd().equals("")){		//���ǃR�[�h
			query.append(" AND SHINSEI.BUKYOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getBukyokuCd()) +"'");
		}
		if(searchInfo.getKenkyuNo() != null && !searchInfo.getKenkyuNo().equals("")){			//�����Ҕԍ�
			query.append(" AND SHINSEI.KENKYU_NO = '" + EscapeUtil.toSqlString(searchInfo.getKenkyuNo()) +"'");
		}
		if(searchInfo.getHikoboFlg() != null && !searchInfo.getHikoboFlg().equals("")){		//�����\���ۃt���O
			query.append(" AND SHINSEI.HIKOBO_FLG = '" + EscapeUtil.toSqlString(searchInfo.getHikoboFlg()) +"'");
		}

		//�\�[�g���i�\����ID�̏����j
		query.append(" ORDER BY SHINSEI.SHINSEISHA_ID");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		try {
			return SelectUtil.selectPageInfo(connection,searchInfo, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�\���ҊǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		}
	}
	
	/**
	 * �d���`�F�b�N.<br /><br />
	 * 
	 * <b>1.�����@�֏��̎擾</b><br /><br />
	 * 
	 * ������info�̃R�[�h�̒l���g�p���āA���O���擾����B
	 * �擾�́A�����\�b�h���g�p���čs���B�g�p����R�[�h�A���\�b�h�A�擾���閼�O�͈ȉ��̕\���Q�ƁB<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>�g�p����R�[�h</td><td>�擾���閼�O</td><td>�g�p���鎩���\�b�h</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>�����@�֖�</td><td>getKikanCodeValue4ShinseishaRegist()</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><td>���ǖ��A���ǖ�(����)</td><td>getBukyokuCodeMap()</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E��R�[�h</td><td>�E��</td><td>getShokushuMap()</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́Ainfo�Ɋi�[����B<br /><br />
	 * 
	 * 
	 * <b>2.�d���`�F�b�N</b><br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���A�����Ҕԍ��E�����@�փR�[�h��
	 * �����\���҂��o�^����Ă��Ȃ����ǂ������m�F����B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)
	 * FROM
	 * 	SHINSEISHAINFO
	 * WHERE
	 * 	SHINSEISHA_ID <> ?
	 * 	AND SHOZOKU_CD = ?
	 * 	AND KENKYU_NO = ?
	 * 	AND DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������info�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������info�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������info�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �������ʂ����݂���ꍇ�ɂ͗�O��throw���A���݂��Ȃ��ꍇ�ɂ�info��ԋp����B<br /><br />
	 * <b><span style="color:#FF0000">���N�̃��R�[�h������ꍇ�ɂ͗�O��throw�����B</span></b><br/>
	 * �\����ID�c����N�x(2��)+�����@�փR�[�h(5��)+�A��(5��)+�`�F�b�N�f�W�b�g(1��)
	 * @param userInfo UserInfo
	 * @param info ShinseishaInfo
	 * @param mode String
	 * @return �\���ҏ���ShinseishaInfo
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public ShinseishaInfo validate(UserInfo userInfo, ShinseishaInfo info, String mode)
			throws ApplicationException, ValidationException {

		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//�G���[���ێ��p���X�g
			List errors = new ArrayList();

			//�폜 ��������-----------------------------------------------
			//���[�}���������폜
			//---------------------------
			//�\���Ҏ����i���[�}��-���j��啶���ɕϊ�
			//---------------------------
			//info.setNameRoSei(info.getNameRoSei().toUpperCase());

			//---------------------------
			//�\���Ҏ����i���[�}��-���j��1�����ڂ�啶���Ɉȍ~���������ɕϊ�
			//---------------------------
			//if(info.getNameRoMei() != null && info.getNameRoMei().length() > 0) {
			//	info.setNameRoMei(info.getNameRoMei().substring(0,1).toUpperCase()
			//		+info.getNameRoMei().substring(1).toLowerCase());
			//}
			//�폜 �����܂�----------------------------------------------
			
			//---------------------------
			//�����@�փR�[�h�������@�֖��̃Z�b�g
			//---------------------------
			KikanInfo kikanInfo = new KikanInfo();
			kikanInfo = getKikanCodeValue4ShinseishaRegist(userInfo, info.getShozokuCd(), info.getShozokuName(), info.getShozokuNameEigo());
			info.setShozokuName(kikanInfo.getShozokuNameKanji());
			info.setShozokuNameEigo(kikanInfo.getShozokuNameEigo());
			info.setShozokuNameRyaku(kikanInfo.getShozokuRyakusho());
			
			//---------------------------
			//���ǃR�[�h�����ǖ��A���ǖ�(����)
			//---------------------------
			info.setBukyokuName((String)getBukyokuCodeMap(userInfo, info.getBukyokuCd(), info.getBukyokuName()).get("BUKA_NAME"));
			info.setBukyokuNameRyaku((String)getBukyokuCodeMap(userInfo, info.getBukyokuCd(), info.getBukyokuName()).get("BUKA_RYAKUSHO"));

//			//---------------------------
//			//���ǎ�ʃR�[�h�����ǎ�ʖ�
//			//---------------------------
//			info.setBukyokuShubetuName(getBukyokuShubetuValue(userInfo, info.getBukyokuShubetuCd(), info.getBukyokuShubetuName()));

			//---------------------------
			//�E��R�[�h���E��
			//---------------------------
			info.setShokushuNameKanji((String)getShokushuMap(userInfo, info.getShokushuCd(), info.getShokushuNameKanji()).get("SHOKUSHU_NAME"));
			info.setShokushuNameRyaku((String)getShokushuMap(userInfo, info.getShokushuCd(), info.getShokushuNameKanji()).get("SHOKUSHU_NAME_RYAKU"));

			//�����Ҕԍ��̃`�F�b�N�f�W�b�g�`�F�b�N
			if(!checkKenkyuNo(info.getKenkyuNo())) {
				throw new ApplicationException("�����Ҕԍ�����ł��B", 	new ErrorInfo("errors.required", new String[] {"�����Ҕԍ�"}));	
			}
//			String kenkyuNo = info.getKenkyuNo();
//			int checkDigit = (Integer.parseInt(kenkyuNo.substring(1, 2)) * 1
//								+ Integer.parseInt(kenkyuNo.substring(2, 3)) * 2
//								+ Integer.parseInt(kenkyuNo.substring(3, 4)) * 1
//								+ Integer.parseInt(kenkyuNo.substring(4, 5)) * 2
//								+ Integer.parseInt(kenkyuNo.substring(5, 6)) * 1
//								+ Integer.parseInt(kenkyuNo.substring(6, 7)) * 2
//								+ Integer.parseInt(kenkyuNo.substring(7, 8)) * 1)
//							% 10;
//			if(Integer.parseInt(kenkyuNo.substring(0, 1)) != checkDigit) {
//				throw new ApplicationException("�����Ҕԍ����Ԉ���Ă��܂��B", 	new ErrorInfo("errors.5018"));	
//			}

			//2�d�o�^�`�F�b�N
			//�\���ҏ��e�[�u���ɂ��łɌ����Ҕԍ��A�������ǃR�[�h�������\���҂��o�^����Ă��Ȃ����ǂ������m�F
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			int count = dao.countShinseishaInfo(connection, info);
			//���łɓo�^����Ă���ꍇ
			if(count > 0){
				String[] error = {"�\����"};
				throw new ApplicationException("���łɐ\���҂��o�^����Ă��܂��B", 	new ErrorInfo("errors.4007", error));			
			}

			//-----���̓G���[���������ꍇ�͗�O���Ȃ���-----
			if (!errors.isEmpty()) {
				throw new ValidationException(
					"�\���ҊǗ��f�[�^�`�F�b�N���ɃG���[��������܂����B",
					errors);
			}
			return info;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�\���ҊǗ��f�[�^�`�F�b�N����DB�G���[���������܂����B",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * �p�X���[�h�̍X�V.<br /><br />
	 * 
	 * <b>1.�\���ҏ��̎擾</b><br /><br />
	 * �ȉ���SQL���𔭍s���āA�\���ҏ����擾����B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SHINSEISHA_ID
	 * 	,A.SHOZOKU_CD
	 * 	,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME
	 * 	,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO
	 * 	,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU
	 * 	,A.PASSWORD
	 * 	,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI
	 * 	,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI
	 * 	,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI
	 * 	,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI
	 * 	,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI
	 * 	,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI
	 * 	,A.BUKYOKU_CD
	 * 	,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME
	 * 	,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU
	 * 	,A.KENKYU_NO
	 * 	,A.SHOKUSHU_CD
	 * 	,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI
	 * 	,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU
	 * 	,A.HIKOBO_FLG
	 * 	,NVL(A.BIKO,'') BIKO
	 * 	,A.YUKO_DATE
	 * 	,A.DEL_FLG
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������pkInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * <b>2.�p�X���[�h�̃`�F�b�N</b><br /><br />
	 * 
	 * �擾�����\���ҏ��̃p�X���[�h�̒l���A��O������oldPassword�Ɠ��������`�F�b�N����B
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
	 * 	SHINSEISHAINFO
	 * SET
	 * 	PASSWORD = ?
	 * WHERE
	 * 	SHINSEISHA_ID = ?
	 * 	AND DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�p�X���[�h</td><td>��l����newPassword���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>������pkInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * ����Ƀp�X���[�h�̍X�V���s��ꂽ�ꍇ�ɂ́Atrue��ԋp����B<br /><br />
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinseishaPk
	 * @param oldPassword
	 * @param newPassword
	 * @return �X�V���ʂ�boolean
	 * @throws ApplicationException
	 */
	public boolean changePassword(
			UserInfo userInfo,
			ShinseishaPk pkInfo,
			String oldPassword,
			String newPassword)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�\���ҏ��̎擾
			//---------------------------------------
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			ShinseishaInfo info = dao.selectShinseishaInfo(connection, pkInfo);

			//---------------------------------------
			//���݂̃p�X���[�h���`�F�b�N����B
			//---------------------------------------
			if (!info.getPassword().equals(oldPassword)) {
				//�G���[���ێ��p���X�g
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.2001", new String[] { "�p�X���[�h" }));
				throw new ValidationException(
						"�p�X���[�h�ύX�f�[�^�`�F�b�N���ɃG���[��������܂����B",
						errors);
			}

			//---------------------------------------
			//���݂̃p�X���[�h���X�V����B
			//---------------------------------------
			if(dao.changePasswordShinseishaInfo(connection,pkInfo,newPassword)){
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
	 * �p�X���[�h�̍X�V.<br /><br />
	 * 
	 * <b>1.�\���ҏ��̎擾</b><br /><br />
	 * �ȉ���SQL���𔭍s���āA�\���ҏ����擾����B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SHINSEISHA_ID
	 * 	,A.SHOZOKU_CD
	 * 	,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME
	 * 	,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO
	 * 	,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU
	 * 	,A.PASSWORD
	 * 	,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI
	 * 	,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI
	 * 	,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI
	 * 	,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI
	 * 	,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI
	 * 	,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI
	 * 	,A.BUKYOKU_CD
	 * 	,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME
	 * 	,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU
	 * 	,A.KENKYU_NO
	 * 	,A.SHOKUSHU_CD
	 * 	,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI
	 * 	,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU
	 * 	,A.HIKOBO_FLG
	 * 	,NVL(A.BIKO,'') BIKO
	 * 	,A.YUKO_DATE
	 * 	,A.DEL_FLG
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������pkInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
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
	 * 		<tr bgcolor="#FFFFFF"><td>�Ώێ�ID</td><td>1(1�͐\���҂�����킷)</td></tr>
	 * </table><br />
	 * 
	 * �ȉ���SQL�����A�Ĕ��s�����p�X���[�h��o�^����B
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEISHAINFO
	 * SET
	 * 	PASSWORD = ?
	 * WHERE
	 * 	SHINSEISHA_ID = ?
	 * 	AND DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�p�X���[�h</td><td>�Ĕ��s����newPassword���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������pkInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * <b>3.�\���ҏ��̎擾</b><br /><br />
	 * �ȉ���SQL���𔭍s���āA�ēx�\���ҏ����擾���A�ԋp����B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SHINSEISHA_ID
	 * 	,A.SHOZOKU_CD
	 * 	,NVL(A.SHOZOKU_NAME,'') SHOZOKU_NAME
	 * 	,NVL(A.SHOZOKU_NAME_EIGO,'') SHOZOKU_NAME_EIGO
	 * 	,NVL(A.SHOZOKU_NAME_RYAKU,'') SHOZOKU_NAME_RYAKU
	 * 	,A.PASSWORD
	 * 	,NVL(A.NAME_KANJI_SEI,'') NAME_KANJI_SEI
	 * 	,NVL(A.NAME_KANJI_MEI,'') NAME_KANJI_MEI
	 * 	,NVL(A.NAME_KANA_SEI,'') NAME_KANA_SEI
	 * 	,NVL(A.NAME_KANA_MEI,'') NAME_KANA_MEI
	 * 	,NVL(A.NAME_RO_SEI,'') NAME_RO_SEI
	 * 	,NVL(A.NAME_RO_MEI,'') NAME_RO_MEI
	 * 	,A.BUKYOKU_CD
	 * 	,NVL(A.BUKYOKU_NAME,'') BUKYOKU_NAME
	 * 	,NVL(A.BUKYOKU_NAME_RYAKU,'') BUKYOKU_NAME_RYAKU
	 * 	,A.KENKYU_NO
	 * 	,A.SHOKUSHU_CD
	 * 	,NVL(A.SHOKUSHU_NAME_KANJI,'') SHOKUSHU_NAME_KANJI
	 * 	,NVL(A.SHOKUSHU_NAME_RYAKU,'') SHOKUSHU_NAME_RYAKU
	 * 	,A.HIKOBO_FLG
	 * 	,NVL(A.BIKO,'') BIKO
	 * 	,A.YUKO_DATE
	 * 	,A.DEL_FLG
	 * FROM
	 * 	SHINSEISHAINFO A
	 * WHERE
	 * 	SHINSEISHA_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������pkInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l��ShinseishaInfo�Ɋi�[���ĕԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo ShinseishaPk
	 * @return �p�X���[�h�X�V���ShinseishaInfo
	 * @throws ApplicationException
	 */
	public ShinseishaInfo reconfigurePassword(UserInfo userInfo, ShinseishaPk pkInfo) throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�\���ҏ��̎擾
			//---------------------------------------
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			ShinseishaInfo info = dao.selectShinseishaInfo(connection, pkInfo);

			//RULEINFO�e�[�u����胋�[���擾����
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.SHINSEISHA);

			//�p�X���[�h���Đݒ肷��
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			success = dao.changePasswordShinseishaInfo(connection,pkInfo,newPassword);

			//---------------------------------------
			//�\���҃f�[�^�̎擾
			//---------------------------------------
			ShinseishaInfo result = dao.selectShinseishaInfo(connection, pkInfo);
			
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
	 * �@�֏��̕ԋp�i�����g�D�p�j.<br /><br />
	 * 
	 * �����@�փR�[�h���珊���@�֖����擾���鎩���\�b�h<br />
	 * getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, false, false)<br />
	 * ���Ăяo���B<br /><br />
	 * 
	 * ���\�b�h�ɗ^�����Z������"false"�ł��邽�߁A�������̒l��"99999"(���̑�)�͋�����Ȃ��B<br /><br />
	 * 
	 * @param userInfo			���s���郆�[�U���
	 * @param kikanCd          �@�փR�[�h
	 * @param nameKanji		�����@�֖�(����)
	 * @param nameEigo			�����@�֖�(�p��)
	 * @return KikanInfo
	 * @throws ApplicationException
	 */
	public KikanInfo getKikanCodeValue4KenkyuSoshiki(UserInfo userInfo,
													  String kikanCd, 
													  String nameKanji, 
													  String nameEigo)
			throws ApplicationException {
				
		return getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, false, false);
	}

	/**
	 * �@�֏��̕ԋp�i�\���ғo�^�p�j.<br /><br />
	 * 
	 * �����@�փR�[�h���珊���@�֖����擾���鎩���\�b�h<br />
	 * getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, true, true)<br />
	 * ���Ăяo���B<br /><br />
	 * 
	 * �R�[�h�����̑��̏ꍇ�͓��͕�������Z�b�g���ĕԂ��B
	 * �����@�֒S���҃e�[�u���ɓ��Y�R�[�h�̃��R�[�h�����݂����ꍇ�A
	 * ������̏���D��I�ɃZ�b�g���ĕԂ��B<br /><br />
	 * 
	 * @param  userInfo          	 ���s���郆�[�U���
	 * @param  kikanCd          	 �@�փR�[�h
	 * @param  nameKanji          	 �����@�֖�(����)
	 * @param  nameEigo          	 �����@�֖�(�p��)
	 * @return                      �@�֏��
	 * @throws ApplicationException 
	 */
	public KikanInfo getKikanCodeValue4ShinseishaRegist(UserInfo userInfo, 
														 String kikanCd, 
														 String nameKanji, 
														 String nameEigo)
			throws ApplicationException {
				
		return getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, true, true);
	}

	/**
	 * �@�֏��̕ԋp.<br /><br />
	 * 
	 * �����@�փR�[�h���珊���@�֖����擾���鎩���\�b�h<br />
	 * getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, false, true)<br />
	 * ���Ăяo���B<br /><br />
	 * 
	 * �R�[�h�����̑��̏ꍇ�͓��͕�������Z�b�g���ĕԂ��B<br /><br />
	 * 
	 * @param  userInfo          	 ���s���郆�[�U���
	 * @param  kikanCd          	 �@�փR�[�h
	 * @param  nameKanji          	 �����@�֖�(����)
	 * @param  nameEigo          	 �����@�֖�(�p��)
	 * @return KikanInfo
	 * @throws ApplicationException
	 */
	public KikanInfo getKikanCodeValue(UserInfo userInfo,
										String kikanCd, 
										String nameKanji, 
										String nameEigo)
			throws ApplicationException{
		return getKikanCodeValueAction(userInfo, kikanCd, nameKanji, nameEigo, false, true);
	}

	/**
	 * �@�֏��̕ԋp.<br /><br />
	 * 
	 * �����@�փR�[�h���珊���@�֖����擾����B<br />
	 * �n���������ɂ���āA�������啝�ɕς��B<br /><br />
	 * 
	 * �܂��A��������String"<b>kikanCd</b>"��null�������ꍇ�ɂ́A��O��throw����B<br /><br />
	 * 
	 * ���ɁA��Z������boolean"<b>otherFlg</b>"��true�ŁA
	 * ����������String"<b>kikanCd</b>"��"99999"(���̑�)�������ꍇ(��)�ɂ́A
	 * KikanInfo�Ɉȉ��̒l���i�[����B<br /><br />
	 * 
	 * �@�EShozokuNameKanji�c��O������String<br />
	 * �@�EShozokuNameEigo�c��l������String<br />
	 * �@�EShozokuRyakusho�c"���̑�"<br /><br />
	 * 
	 * ����ŁA�l���i�[����KikanInfo��ԋp���ďI���ƂȂ�B<br /><br /><br />
	 * 
	 * 
	 * 
	 * (��)�̏��������Ă͂܂�Ȃ��ꍇ�ɂ́A�ȉ���SQL���𔭍s����B<br /><br />
	 * 
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
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>��������String���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�������ʂ�KikanInfo�Ɋi�[����B�l���Ȃ��ꍇ�ɂ͗�O��throw����B<br />
	 * "���̑�"��ID�w99999�x���g�p�����ꍇ�ɂ́A�l���Ȃ����߂ɗ�O��throw���邱�ƂɂȂ�B<br /><br />
	 * 
	 * �����āA��܈�����boolean"<b>priorityFlg</b>"��false�̏ꍇ�ɂ́A
	 * ���擾����KikanInfo��ԋp���ďI���ƂȂ�B<br />
	 * true�̏ꍇ�ɂ́A�ȉ���SQL���𔭍s���āA�����@�֒S���ҏ�����������B<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
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
	 *     ,BUKYOKU_NUM				-- ���ǒS���Ґl��
	 *     ,A.DEL_FLG				-- �폜�t���O
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     DEL_FLG = 0
	 *	
	 * �@�@�@<b><span style="color:#002288">-- ���I�������� --</span></b>
	 * 
	 * ORDER BY SHOZOKU_CD</pre>
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
	 * �擾�����l����A�����@�֖�(�����A�p��)��KikanInfo�Ɋi�[���A�ԋp����B<br/><br/>
	 * 
	 * @param  userInfo          	 ���s���郆�[�U���
	 * @param  kikanCd          	 �@�փR�[�h
	 * @param  nameKanji          	 �����@�֖�(����)
	 * @param  nameEigo          	 �����@�֖�(�p��)
	 * @param  priorityFlg			 �����@�փe�[�u���D��t���O�@true:�����@�փe�[�u����D�悵�ĕԂ��@false:�@�փ}�X�^�̒l��Ԃ�
	 * @param  otherFlg			 ���̑��t���O�@true:���̑��R�[�h����@false:���̑��R�[�h�Ȃ��i���̑��R�[�h���G���[�ƂȂ�j
	 * @return                      �@�֏��
	 * @throws IllegalArgumentException  �@�փR�[�h��null�̏ꍇ
	 * @throws ApplicationException 
	 */
	private KikanInfo getKikanCodeValueAction(UserInfo userInfo, 
											   String kikanCd, 
											   String nameKanji, 
											   String nameEigo, 
											   boolean priorityFlg,
											   boolean otherFlg)
			throws IllegalArgumentException, ApplicationException {

		//�����`�F�b�N
		if(kikanCd == null){
			throw new IllegalArgumentException("kikanCd��null�ł��B");
		}

		KikanInfo kikanInfo = new KikanInfo();
		kikanInfo.setShozokuCd(kikanCd);
		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//�G���[���ێ��p���X�g
			List errors = new ArrayList();

	
			//---otherFlg��true�ŁA���A���̑��R�[�h�̂Ƃ��͂��̂܂܊i�[�B
			//---���̂́u���̑��v�Ƃ���B
			if(otherFlg && kikanCd.equals("99999")){
				kikanInfo.setShozokuNameKanji(nameKanji);
				kikanInfo.setShozokuNameEigo(nameEigo);
				kikanInfo.setShozokuRyakusho("���̑�");
	
			//---�f�[�^�x�[�X����������
			} else {
				try {
					kikanInfo = new MasterKikanInfoDao(userInfo).selectKikanInfo(connection,kikanInfo);
				} catch(NoDataFoundException noDataShozokuInfo) {
					//������Ȃ������Ƃ��B
					errors.add(
						new ErrorInfo("errors.2001", new String[] { "�����@�փR�[�h" }));
				}
		
				//�D��t���O��true�̏ꍇ�͏����@�փe�[�u������������
				if(priorityFlg) {
					//�����@�ւ����e��ύX���Ă���ꍇ������̂ŁA�����@�փe�[�u���̏���D��I�Ɏ擾����
					ShozokuSearchInfo shozokuSearchInfo = new ShozokuSearchInfo();
					shozokuSearchInfo.setShozokuCd(kikanCd);
					try {
						Page page = new ShozokuMaintenance().search(userInfo, shozokuSearchInfo);
						if(page.getTotalSize() > 0) {
							List list = page.getList();
							HashMap hashMap = (HashMap)list.get(0);

							if(hashMap.get("SHOZOKU_NAME_KANJI") != null) {
								kikanInfo.setShozokuNameKanji(hashMap.get("SHOZOKU_NAME_KANJI").toString());
							}
							if(hashMap.get("SHOZOKU_NAME_EIGO") != null) {
								kikanInfo.setShozokuNameEigo(hashMap.get("SHOZOKU_NAME_EIGO").toString());
							}
//							if(hashMap.get("SHOZOKU_RYAKUSHO") != null) {
//								kikanInfo.setShozokuRyakusho(hashMap.get("SHOZOKU_RYAKUSHO").toString());
//							}
						}
					} catch(ApplicationException e) {
						//������Ȃ������Ƃ��B
						//�������@�փe�[�u���ɖ����ꍇ�́A�@�փ}�X�^�̃f�[�^������̂ŃG���[�Ƃ��Ȃ��B
					}
				}

			}
	
			//������Ȃ������ꍇ
			if(!errors.isEmpty()){
				String msg = "�����@�փ}�X�^�ɓ��Y�f�[�^������܂���BkikanCD=" + kikanCd;
				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
			}
	
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����@�փf�[�^�`�F�b�N����DB�G���[���������܂����B",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return kikanInfo;
	}

	/**
	 * ���Ȗ��̂̕ԋp.<br /><br />
	 * 
	 * ���ǃR�[�h���畔�Ȗ��́E���ȗ��̂�Map���擾���鎩���\�b�h<br />
	 * getBukyokuCodeMap(userInfo, bukyokuCd, value)<br />
	 * ���Ăяo���A�������畔�Ȗ��̂����o���ĕԋp����B<br /><br />
	 * 
	 * @param  userInfo              ���s���郆�[�U���
	 * @param  bukyokuCd             ���ǃR�[�h
	 * @param  value                 ���Ȗ���
	 * @return String                ���Ȗ���
	 * @throws ApplicationException 
	 */
	public String getBukyokuCodeValue(UserInfo userInfo, String bukyokuCd, String value)
			throws ApplicationException {
		return (String)getBukyokuCodeMap(userInfo, bukyokuCd, value).get("BUKA_NAME");
	}

	/**
	 * ���Ȗ��́E���ȗ��̂�Map�̕ԋp.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA���Ǐ����擾����B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	MASTER_BUKYOKU
	 * WHERE
	 * 	BUKYOKU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><td>������bukyokuCd���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�������Ǐ���"���Ȗ���"�A"���ȗ���"��Map�Ɋi�[����B<br />
	 * �l���Ȃ������ꍇ�ɂ͗�O��throw����B<br /><br />
	 * 
	 * ��������bukyokuCd�̒l��"���̑�"��\������(��)�������ꍇ�ɂ́A
	 * ��O������value�𕔉Ȗ��̂Ƃ���Map�Ɋi�[����B<br />
	 * (��)���̑��c"709"�A"913"�A"899"�A"875"�A"903"�A"999"<br /><br />
	 * <b><span style="color:#FF0000">�n�[�h�R�[�f�B���O�ł����̂��H�H</span></b><br/><br />
	 * �쐬����Map��ԋp����B<br /><br />
	 * 
	 * @param  userInfo              ���s���郆�[�U���
	 * @param  bukyokuCd             ���ǃR�[�h
	 * @param  value                 ���ǖ�
	 * @return Map                   ���Ȗ��́E���ȗ��̂�Map
	 * @throws ApplicationException 
	 */
	public Map getBukyokuCodeMap(UserInfo userInfo, String bukyokuCd, String value)
			throws ApplicationException {

		HashMap hashMap = new HashMap();
		
		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//�G���[���ێ��p���X�g
			List errors = new ArrayList();

			try {
				BukyokuInfo bukyokuInfo = new BukyokuInfo();
				bukyokuInfo.setBukyokuCd(bukyokuCd);
					
				bukyokuInfo = new MasterBukyokuInfoDao(userInfo).selectBukyokuInfo(connection, bukyokuInfo);
				hashMap.put("BUKA_NAME", bukyokuInfo.getBukaName());
				hashMap.put("BUKA_RYAKUSHO", bukyokuInfo.getBukaRyakusyo());	//���̂̓}�X�^�̒l���g�p����
			} catch(NoDataFoundException e) {
				//������Ȃ������Ƃ��B
				errors.add(
					new ErrorInfo("errors.2001", new String[] { "���ǃR�[�h" }));
			}

			//���̑��̎��͓��͒lvalue���i�[����B
			//2005/8/27 ���̑��̎��͓��͒lvalue�I=NULL�̏ꍇ�A���͒lvalue���i�[����B
			//if(bukyokuCd != null
			//2005/8/29 ���ǃR�[�h�����̑��̏ꍇ�A���ǖ��ɑS�p�Ɣ��p�󔒂���������
			//if(value != null && value.length() > 0
//2006/06/30�@�c�@�C����������@   ���R�F���ǃR�[�h�̂��̑��ɁA�u901�v��ǉ�
//			if(!StringUtil.isSpaceString(value)
//					&& (bukyokuCd.equals("709") || bukyokuCd.equals("913") || bukyokuCd.equals("899")
//					|| bukyokuCd.equals("875") || bukyokuCd.equals("903") || bukyokuCd.equals("999"))) {
            if (!StringUtil.isSpaceString(value)
                    && (bukyokuCd.equals("709") || bukyokuCd.equals("901") || 
                        bukyokuCd.equals("913") || bukyokuCd.equals("899") || 
                        bukyokuCd.equals("875") || bukyokuCd.equals("903") || 
                        bukyokuCd.equals("999"))) {
//2006/06/30�@�c�@�C�������܂�            
				hashMap.put("BUKA_NAME", value);
			}
			//������Ȃ������ꍇ
			if(!errors.isEmpty()){
				String msg = "���ǃ}�X�^�ɓ��Y�f�[�^������܂���BkikanCD=" + bukyokuCd;
				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
			}
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���ǃ}�X�^�f�[�^�`�F�b�N����DB�G���[���������܂����B",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return hashMap;
	}

	/**
	 * �E�햼�̕ԋp.<br /><br />
	 * 
	 * �E��R�[�h����E�햼�E�E�햼(����)��Map���擾���鎩���\�b�h<br />
	 * getShokushuMap(userInfo, shokushuCd, value)<br />
	 * ���Ăяo���A��������E�햼�����o���ĕԋp����B<br /><br />
	 * 
	 * @param  userInfo          	 ���s���郆�[�U���
	 * @param  shokushuCd         	 �E�햼�R�[�h
	 * @param  value	          	 �E�햼
	 * @return                      �E�햼
	 * @throws ApplicationException 
	 */
	public String getShokushuCodeValue(UserInfo userInfo, String shokushuCd, String value)
			throws ApplicationException {
		return (String)getShokushuMap(userInfo, shokushuCd, value).get("SHOKUSHU_NAME");
	}

	/**
	 * �E�햼�E�E�햼(����)��Map�̕ԋp.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�E������擾����B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SHOKUSHU_CD				--�E��R�[�h
	 * 	,A.SHOKUSHU_NAME			--�E����
	 * 	,A.SHOKUSHU_NAME_RYAKU		--�E��(����)
	 * 	,A.BIKO						--���l
	 * FROM
	 * 	MASTER_SHOKUSHU A
	 * WHERE
	 * 	SHOKUSHU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�E��R�[�h</td><td>������shokushuCd���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����E�����"�E�햼"�A"�E�햼(����)"��Map�Ɋi�[����B<br />
	 * �l���Ȃ������ꍇ�ɂ͗�O��throw����B<br /><br />
	 * 
	 * ������shokushuCd��"25"(���̑�)�̏ꍇ�ɂ́A
	 * ��O������value��E�햼�Ƃ���Map�Ɋi�[����B<br /><br />
	 * 
	 * 
	 * ������shokushuCd��"24"(������)�ŁA����O������value���󕶎���łȂ��ꍇ�ɂ́A
	 * ��O������value��E�햼�Ƃ���Map�Ɋi�[����B<br /><br />
	 * 
	 * <b><span style="color:#FF0000">�n�[�h�R�[�f�B���O�ł����̂��H�H</span></b><br/><br />
	 * 
	 * �쐬����Map��ԋp����B<br /><br />
	 * 
	 * @param  userInfo          	 ���s���郆�[�U���
	 * @param  shokushuCd         	 �E�햼�R�[�h
	 * @param  value	          	 �E�햼
	 * @return                      �E�햼�E�E�햼(����)��Map
	 * @throws ApplicationException 
	 */
	public Map getShokushuMap(UserInfo userInfo, String shokushuCd, String value)
			throws ApplicationException {

//		String shokushuName = null;
		HashMap hashMap = new HashMap();
		
		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//�G���[���ێ��p���X�g
			List errors = new ArrayList();

			try {
				ShokushuInfo shokushuInfo = new ShokushuInfo();
				shokushuInfo.setShokushuCd(shokushuCd);
					
				shokushuInfo = new MasterShokushuInfoDao(userInfo).selectShokushuInfo(connection, shokushuInfo);
//				shokushuName = shokushuInfo.getShokushuName();
				hashMap.put("SHOKUSHU_NAME", shokushuInfo.getShokushuName());
				hashMap.put("SHOKUSHU_NAME_RYAKU", shokushuInfo.getShokushuNameRyaku());	//���̂̓}�X�^�̒l���g�p����
			} catch(NoDataFoundException e) {
				//������Ȃ������Ƃ��B
				errors.add(
					new ErrorInfo("errors.2001", new String[] { "�E��R�[�h" }));
			}

			//���̑�(25)�̎����͒lvalue���i�[����B
			if(shokushuCd != null && shokushuCd .equals("25")) {
				hashMap.put("SHOKUSHU_NAME", value);
			}
			//�E��R�[�h��������(24)�œ��͒lvalue����łȂ��ꍇ�A���͒lvalue��D�悷��B
			//2005/8/29 �S�p�Ɣ��p�󔒂���������
			//else if(value != null && !value.equals("")
			else if( !StringUtil.isSpaceString(value)
					&& shokushuCd != null && shokushuCd.equals("24")) {
						hashMap.put("SHOKUSHU_NAME", value);
			}

			//������Ȃ������ꍇ
			if(!errors.isEmpty()){
				String msg = "�E��}�X�^�ɓ��Y�f�[�^������܂���BkikanCD=" + shokushuCd;
				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
			}
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�E��}�X�^�f�[�^�`�F�b�N����DB�G���[���������܂����B",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return hashMap;
	}

//	/**
//	 * �J�e�S�����̂�Ԃ��B
//	 * �R�[�h�����̑�(05)�̏ꍇ�͓��͕������Ԃ��B
//	 * @param  userInfo          	 ���s���郆�[�U���
//	 * @param  bukaKategori       	 �J�e�S��
//	 * @param  value	          	 �J�e�S������
//	 * @return                      �J�e�S������
//	 * @throws ApplicationException 
//	 */
//	public String getKategoriCodeValue(UserInfo userInfo, String bukaKategori, String value)
//			throws ApplicationException {
//
//		String kategoriName = null;
//
//		Connection connection = null;	
//		try {
//			connection = DatabaseUtil.getConnection();
//			//�G���[���ێ��p���X�g
//			List errors = new ArrayList();
//
//			if(bukaKategori != null && !bukaKategori.equals("05")) {
//
//				try {
//					KategoriInfo kategoriInfo = new KategoriInfo();
//					kategoriInfo.setBukaKategori(bukaKategori);
//			
//					kategoriInfo = new MasterKategoriInfoDao(userInfo).selectKategoriInfo(connection, kategoriInfo);
//					kategoriName = kategoriInfo.getKategoriName();
//				} catch(NoDataFoundException e) {
//					//������Ȃ������Ƃ��B
//					errors.add(
//						new ErrorInfo("errors.2001", new String[] { "�J�e�S��" }));
//				}
//			} else {
//				kategoriName = value;
//			}
//			//������Ȃ������ꍇ
//			if(!errors.isEmpty()){
//				String msg = "�J�e�S���}�X�^�ɓ��Y�f�[�^������܂���BkikanCD=" + bukaKategori;
//				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
//			}
//		} catch (DataAccessException e) {
//			throw new ApplicationException(
//				"�J�e�S���}�X�^�f�[�^�`�F�b�N����DB�G���[���������܂����B",
//				new ErrorInfo("errors.4005"),
//				e);
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//		return kategoriName;
//	}
//
//
//
//	/**
//	 * ���ǎ�ʖ��̂�Ԃ��B
//	 * �R�[�h�����̑�(05)�̏ꍇ�͓��͕������Ԃ��B
//	 * @param  userInfo          	 ���s���郆�[�U���
//	 * @param  shubetuCd       	 ���ǎ��
//	 * @param  value	          	 ���ǎ�ʖ���
//	 * @return                      ���ǎ�ʖ���
//	 * @throws ApplicationException 
//	 */
//	public String getBukyokuShubetuValue(UserInfo userInfo, String shubetuCd, String value)
//			throws ApplicationException {
//
//		String shubetuName = null;
//
//		Connection connection = null;	
//		try {
//			connection = DatabaseUtil.getConnection();
//			//�G���[���ێ��p���X�g
//			List errors = new ArrayList();
//
//			if(shubetuCd != null && !shubetuCd.equals("9")) {
//
//				try {
//					HashMap hashmap
//						= (HashMap)MasterLabelInfoDao.selectRecord(connection, ILabelKubun.BUKYOKU_SHUBETU, shubetuCd);
//					
//					shubetuName = hashmap.get("NAME").toString();
//					
//				} catch(NoDataFoundException e) {
//					//������Ȃ������Ƃ��B
//					errors.add(
//						new ErrorInfo("errors.2001", new String[] { "���ǎ��" }));
//				}
//			} else {
//				shubetuName = value;
//			}
//			//������Ȃ������ꍇ
//			if(!errors.isEmpty()){
//				String msg = "���x���}�X�^�ɓ��Y�f�[�^������܂���BshubetuCd=" + shubetuCd;
//				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
//			}
//		} catch (DataAccessException e) {
//			throw new ApplicationException(
//				"���x���}�X�^�f�[�^�`�F�b�N����DB�G���[���������܂����B",
//				new ErrorInfo("errors.4005"),
//				e);
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//		return shubetuName;
//	}

	/**
	 * �����\���t���O�̉���.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�����\���t���O�̒l��"0"(�����\���s��)�ɕύX����B<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEISHAINFO
	 * SET
	 * 	HIKOBO_FLG = 0
	 * WHERE
	 * 
	 * 		<b><span style="color:#002288">-- ���I�������� --</span></b>
	 * 
	 * 	DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\����ID</td><td>ShinseishaId</td><td>SHINSEISHA_ID = '�\����ID' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(����-��)</td><td>NameKanjiSei</td><td>NAME_KANJI_SEI LIKE '%�\���Ҏ���(����-��)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(����-��)</td><td>NameKanjiMei</td><td>NAME_KANJI_MEI LIKE '%�\���Ҏ���(����-��)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(�t���K�i-��)</td><td>NameKanaSei</td><td>NAME_KANA_SEI LIKE '%�\���Ҏ���(�t���K�i-��)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(�t���K�i-��)</td><td>NameKanaMei</td><td>NAME_KANA_MEI LIKE '%�\���Ҏ���(�t���K�i-��)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(���[�}��-��)</td><td>NameRoSei</td><td>UPPER(NAME_RO_SEI) LIKE '%�\���Ҏ���(���[�}��-��)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(���[�}��-��)</td><td>NameRoMei</td><td>UPPER(NAME_RO_SEI) LIKE '%�\���Ҏ���(���[�}��-��)%' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖�(�R�[�h)</td><td>ShozokuCd</td><td>SHOZOKU_CD = '�����@�֖�(�R�[�h)' AND</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֖�</td><td>ShozokuName</td><td>(SHOZOKU_NAME LIKE '%�����@�֖�%' OR SHOZOKU_NAME_RYAKU LIKE '%�����@�֖�%') AND</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinseishaSearchInfo
	 * @throws ApplicationException
	 */
	public void deleteHikoboFlgInfo(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//����剞��t���O
			//---------------------------------------
			ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
			dao.deleteHikoboFlgInfo(connection, searchInfo);
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"����剞��t���O�폜����DB�G���[���������܂����B",
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
					"����剞��t���O�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �����Ҕԍ���������������.<br /><br />
	 * 
	 * �^����ꂽ�����Ҕԍ��ɑ΂��āA�ȉ��̃`�F�b�N�����������ɍs���B<br /><br />
	 * 
	 * �@1.�����Ҕԍ��̒l��null�������ꍇ�ɂ́Afalse��ԋp����B<br />
	 * 
	 * �@2.�����Ҕԍ��̒l�̌���8���ȊO�̏ꍇ�ɂ́A��O��throw����B<br />
	 * 
	 * �@3.�����Ҕԍ��̒l��8���ڂ̒l(��ԍ��̌�)���A�`�F�b�N�f�W�b�g�̒l�ƈقȂ�ꍇ�ɂ́A��O��throw����B<br /><br />
	 * 
	 * �ȏ�̃`�F�b�N�ɒʂ����ꍇ�ɂ́A�����������Ҕԍ��Ƃ������ƂŁAtrue��ԋp����B<br /><br />
	 * 
	 * @param  kenkyuNo				�����Ҕԍ�
	 * @return							true:�����Ҕԍ����������ꍇ�@false:�����Ҕԍ���null�̏ꍇ
	 * @throws ApplicationException	�����Ҕԍ����Ԉ���Ă���ꍇ
	 */
	public boolean checkKenkyuNo(String kenkyuNo) throws ApplicationException {

		List errors = new ArrayList();

		if(kenkyuNo == null) {
			return false;
		} else {
			if(kenkyuNo.length() != 8) {
				errors.add(new ErrorInfo("errors.length", new String[] {"�����Ҕԍ�", "8"}));
				throw new ValidationException("�����Ҕԍ���8�����ł͂���܂���B", errors);
			}

// 20050628 �`�F�b�N�f�W�b�g�ɂ��`�F�b�N���폜
//			try {
//				//2005/04/18 �ǉ� ��������-------------------------------------------------
//				//CHECK_DIGIT_FLAG�̃`�F�b�N��ǉ�
//				if(!ApplicationSettings.getBoolean(ISettingKeys.CHECK_DIGIT_FLAG)){
//					return true;
//				}
//				//�ǉ� �����܂�------------------------------------------------------------
//				int checkDigit = (Integer.parseInt(kenkyuNo.substring(1, 2)) * 1
//									+ Integer.parseInt(kenkyuNo.substring(2, 3)) * 2
//									+ Integer.parseInt(kenkyuNo.substring(3, 4)) * 1
//									+ Integer.parseInt(kenkyuNo.substring(4, 5)) * 2
//									+ Integer.parseInt(kenkyuNo.substring(5, 6)) * 1
//									+ Integer.parseInt(kenkyuNo.substring(6, 7)) * 2
//									+ Integer.parseInt(kenkyuNo.substring(7, 8)) * 1)
//								% 10;
//				if(Integer.parseInt(kenkyuNo.substring(0, 1)) == checkDigit) {
//					return true;
//			
//			//2005/05/13 �폜 ��������------------------------------------
//			//�`�F�b�N�f�W�b�g�̊m�F���@�͏�L�̕��@���g�����ߍ폜
//			//2005/04/28 �ǉ� ��������-----------------------------------------------
//			//�`�F�b�N�f�W�b�g�̊m�F���@��ύX
//			//String checkDiditString = CheckDiditUtil.getCheckDigit(kenkyuNo.substring(0, 7));
//			//String checkDidit = CheckDiditUtil.convertCheckDigit(new Integer(checkDiditString).intValue());
//			//	if(checkDidit != null && checkDidit.equals(kenkyuNo.substring(7))){
//			//		return true;
//			//�ǉ� �����܂�-----------------------------------------------
//			//�폜 �����܂�-----------------------------------------------
//				} else {
//					errors.add(new ErrorInfo("errors.5018", new String[] {"�����Ҕԍ�"}));
//					throw new ValidationException("�����Ҕԍ����Ԉ���Ă��܂��B", errors);	
//				}
//			} catch (NumberFormatException e) {
//			errors.add(new ErrorInfo("errors.mask_roma", new String[] {"�����Ҕԍ�"}));
//			throw new ValidationException("�`�F�b�N�f�W�b�g�������������Ҕԍ������l�ł͂���܂���B", errors);
//		}
			return true;
// Horikoshi

		}
	}

	//2005/04/06 �ǉ���������@�p�X���[�h�ꊇ�Đݒ菈���p�̃��\�b�h��ǉ�
	/**
	 * �p�X���[�h�̈ꊇ�Đݒ�
	 * 
	 * �ȉ��̏�����z��Ɋi�[���ꂽ�\����ID�̌����s���B
	 * 
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
	 * 		<tr bgcolor="#FFFFFF"><td>�Ώێ�ID</td><td>1(1�͐\���҂�����킷)</td></tr>
	 * </table><br />
	 * 
	 * �ȉ���SQL�����A�Ĕ��s�����p�X���[�h��o�^����B
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEISHAINFO
	 * SET
	 * 	PASSWORD = ?
	 * WHERE
	 * 	SHINSEISHA_ID = ?
	 * 	AND DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�p�X���[�h</td><td>�Ĕ��s����newPassword���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>������pkInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * </table><br />
	 *	 
 	 * @param userInfo UserInfo
	 * @param pkInfo ShinseishaPk
	 * @param array �\����ID�̔z��
	 * @throws ApplicationException  
	 */
	public void reconfigurePasswordAll(UserInfo userInfo, ShinseishaPk pkInfo, ArrayList array) throws ApplicationException {

			boolean success = false;
			Connection connection = null;
			try {
				connection = DatabaseUtil.getConnection();
				//---------------------------------------
				//�\���ҏ��̎擾
				//---------------------------------------
				ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);
				String newPassword = null;
				
				//RULEINFO�e�[�u����胋�[���擾����
				RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
				RulePk rulePk = new RulePk();
				rulePk.setTaishoId(ITaishoId.SHINSEISHA);
				
				//�p�X���[�h���Đݒ肷��
				for(int i = 0; i < array.size(); i++){
					pkInfo.setShinseishaId((String)array.get(i));
					newPassword = rureInfoDao.getPassword(connection, rulePk);
					success = dao.changePasswordShinseishaInfo(connection,pkInfo,newPassword);
				}
				//---------------------------------------
				//�\���҃f�[�^�̎擾
				//---------------------------------------

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
	//�ǉ������܂�	
}