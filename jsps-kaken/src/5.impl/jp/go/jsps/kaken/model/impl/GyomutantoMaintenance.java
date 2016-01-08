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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import jp.go.jsps.kaken.model.IGyomutantoMaintenance;
import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.AccessKanriDao;
import jp.go.jsps.kaken.model.dao.impl.GyomutantoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterJigyoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.RuleInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.AccessKanriInfo;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �Ɩ��S���ҏ��Ǘ����s���N���X.<br /><br />
 * 
 * �g�p���Ă���e�[�u��<br /><br />
 * �@�E�Ɩ��S���ҏ��e�[�u��:GYOMUTANTOINFO<br />
 * �@�@�@�Ɩ��i���ہj�S���҂̊�{�����Ǘ�����B<br /><br />
 * �@�E�A�N�Z�X����e�[�u��:ACCESSKANRI<br />
 * �@�@�@�Ɩ��S���҂̃A�N�Z�X��������Ǘ�����B<br /><br />
 * �@�E���ƃ}�X�^:MASTER_JIGYO<br />
 * �@�@�@���Ɩ��́A���Ƃ��Ƃ̌n���A���Ƌ敪���̎��ƂɊւ����{�����Ǘ�����B<br /><br />
 */
public class GyomutantoMaintenance implements IGyomutantoMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(GyomutantoMaintenance.class);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public GyomutantoMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IGyomutantoMaintenance
	//---------------------------------------------------------------------

	/**
	 * �Ɩ��S���ҏ��̓o�^.<br /><br />
	 * �Ɩ��S���ҏ��̓o�^�A�A�N�Z�X������̓o�^���s���A���̒l���i�[����GyomutantoInfo��ԋp����B<br /><br />
	 * <b>1.�p�X���[�h�̔��s</b><br />
	 * �Ώێ�ID����A�p�X���[�h�𔭍s����B<br /><br />
	 * �@(1)�ȉ���SQL���𔭍s���ăp�X���[�h���s���[�����擾����B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		*
	 * FROM
	 * 		RULEINFO A
	 * WHERE
	 * 		TAISHO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ώێ�ID</td><td>�萔GYOMUTANTO���g�p����B(��̓I�Ȓl��"3")</td></tr>
	 * </table><br />
	 * �@(2)�擾�����p�X���[�h���s���[�����p�X���[�h�𔭍s���AaddInfo�ɃZ�b�g����B<br /><br />
	 * 
	 * <b>2.�Ɩ��S���ҏ��̒ǉ�</b><br />
	 * �Ɩ��S���ҏ��e�[�u���ɒl��ǉ�����B<br /><br />
	 * �@(1)��d�o�^�ɂȂ�Ȃ����A�d���`�F�b�N���s���B<br />
	 * �@�@�ȉ���SQL���𔭍s���A�Ɩ��S���ҏ����擾����B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * 	SELECT
	 * 			GYOMUTANTO_ID
	 * 			,ADMIN_FLG
	 * 			,PASSWORD
	 * 			,NAME_KANJI_SEI
	 * 			,NAME_KANJI_MEI
	 * 			,NAME_KANA_SEI
	 * 			,NAME_KANA_MEI
	 * 			,BUKA_NAME
	 * 			,KAKARI_NAME
	 * 			,BIKO
	 * 			,YUKO_DATE
	 * 			,DEL_FLG
	 * 	FROM
	 * 			GYOMUTANTOINFO
	 * 	WHERE
	 * 			GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������addInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * �@�@�Y������l���������ꍇ�ɂ́A��O��throw����B<br /><br />
	 * �@(2)�o�^���s���B<br />
	 * �@�@�ȉ���SQL���𔭍s���A�Ɩ��S���ҏ���o�^����B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO
	 * 		GYOMUTANTOINFO
	 * 				(GYOMUTANTO_ID
	 * 				,ADMIN_FLG
	 * 				,PASSWORD
	 * 				,NAME_KANJI_SEI
	 * 				,NAME_KANJI_MEI
	 * 				,NAME_KANA_SEI
	 * 				,NAME_KANA_MEI
	 * 				,BUKA_NAME
	 * 				,KAKARI_NAME
	 * 				,BIKO
	 * 				,YUKO_DATE
	 * 				,DEL_FLG)
	 * 		VALUES
	 * 				(?,?,?,?,?,?,?,?,?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������addInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ǘ��҃t���O</td><td>0</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�p�X���[�h</td><td>������addInfo�̕ϐ�Password���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�����|���j</td><td>������addInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�����|���j</td><td>������addInfo�̕ϐ�NameKanjiMei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i�|���j</td><td>������addInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i�|���j</td><td>������addInfo�̕ϐ�NameKanaMei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ۖ�</td><td>������addInfo�̕ϐ�BukaName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�W��</td><td>������addInfo�̕ϐ�KakariName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>������addInfo�̕ϐ�Biko���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�L������</td><td>������addInfo�̕ϐ�YukoDate���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>0</td></tr>
	 * </table><br />
	 * 
	 * <b>3.�S�����Ə��̒ǉ�</b><br />
	 * 
	 * 
	 * �@(1)�e�[�u���̍폜<br />
	 * �@�@�o�^�f�[�^��DB�ɑ��݂���ƃG���[�ƂȂ邽�߁A�O�̂��ߕ����폜���s���B<br />
	 * �@�@���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * DELETE
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������addInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * �@(2)���Ƌ敪�̎擾<br />
	 * �@�@���Ƌ敪���擾����B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		NVL(A.JIGYO_KUBUN,0) JIGYO_KUBUN
	 * FROM
	 * 		MASTER_JIGYO A
	 * WHERE
	 * 		JIGYO_CD=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>������addInfo�̕ϐ�JigyoValues����jigyoCd���g�p����B</td></tr>
	 * </table><br />
	 * �@�@�擾�������Ƌ敪�Ǝ��ƃR�[�h���AAccessKanriInfo�Ɋi�[����B<br /><br />
	 * 
	 * 
	 * �@(3)�S�����Ə��̓o�^<br />
	 * �@�@��d�o�^�ɂȂ�Ȃ����d���`�F�b�N���s���A�d���łȂ���Γo�^���s���B<br />
	 * �@�@�ȉ���SQL���𔭍s���A�Ɩ��S���ҏ����擾����B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		GYOMUTANTO_ID
	 * 		,JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * 		,BIKO
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID=?
	 * 		AND JIGYO_CD=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>AccessKanriInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>AccessKanriInfo�̕ϐ�JigyoCd���g�p����B</td></tr>
	 * </table><br />
	 * �@�@�Y������l���������ꍇ�́A��O��throw����B<br />
	 * �@�@�d��������̂��Ȃ���΁A�o�^���s���B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO ACCESSKANRI
	 * 		(
	 * 		GYOMUTANTO_ID
	 * 		,JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * 		,BIKO
	 * 		)
	 * VALUES
	 * 		(?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>AccessKanriInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>AccessKanriInfo�̕ϐ�JigyoCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>AccessKanriInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>AccessKanriInfo�̕ϐ�JigyoBiko���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �@(2),(3)��addInfo��List<b>"getJigyoValues"</b>�̌��������J��Ԃ��B<br /><br />
	 * 
	 * �@(4)�o�^�f�[�^�̎擾<br />
	 * �@�@�Ɩ��S���҂̓o�^�f�[�^���擾����B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		GYOMUTANTO_ID
	 * 		,ADMIN_FLG
	 * 		,PASSWORD
	 * 		,NAME_KANJI_SEI
	 * 		,NAME_KANJI_MEI
	 * 		,NAME_KANA_SEI
	 * 		,NAME_KANA_MEI
	 * 		,BUKA_NAME
	 * 		,KAKARI_NAME
	 * 		,BIKO
	 * 		,YUKO_DATE
	 * 		,DEL_FLG
	 * FROM
	 * 		GYOMUTANTOINFO
	 * WHERE
	 * 		GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������addInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �@�@�擾�����l�́AGyomutantoInfo�Ɋi�[����B<br /><br />
	 * 
	 * �@(5)�A�N�Z�X������̎擾<br />
	 * �@�@�Y���Ɩ��S���҂̃A�N�Z�X��������擾����B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID=?
	 * ORDER BY
	 * 		GYOMUTANTO_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������addInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * �@�@�擾�����l�����ꂼ���HashSet�Ɋi�[���A���̓��HashSet��Map�Ɋi�[����B<br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>�l</td><td>HashSet�̖��O</td><td>Map�i�[���̃L�[��</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>tantoJigyoCd</td><td>tantoJigyoCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>tantoJigyoKubun</td><td>tantoJigyoKubun</td></tr>
	 * </table><br />
	 * �@�@�擾����Map������HashSet"<b>tantoJigyoCd</b>"�����o���AArrayList�Ɋi�[����B
	 * ����List��GyomutantoInfo�ɉ����ĕԋp����B
	 * @param userInfo UserInfo
	 * @param addInfo GyomutantoInfo
	 * @return �Ɩ��S���ҏ�񂪊i�[���ꂽGyomutantoInfo
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.GyomutantoInfo)
	 */
	public GyomutantoInfo insert(UserInfo userInfo, GyomutantoInfo addInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//RULEINFO�e�[�u����胋�[���擾����
			RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
			RulePk rulePk = new RulePk();
			rulePk.setTaishoId(ITaishoId.GYOMUTANTO);
			
			//---------------------------------------
			//�p�X���[�h���̍쐬
			//---------------------------------------
			String newPassword = rureInfoDao.getPassword(connection, rulePk);
			addInfo.setPassword(newPassword);

			//---------------------------------------
			//�Ɩ��S���ҏ��̒ǉ�
			//---------------------------------------
			GyomutantoInfoDao gyomutantoInfoDao = new GyomutantoInfoDao(userInfo);
			gyomutantoInfoDao.insertGyomutantoInfo(connection,addInfo);		

			//---------------------------------------
			//�S�����Ə��̒ǉ�
			//---------------------------------------
			AccessKanriInfo accessKanriInfo = new AccessKanriInfo();
			accessKanriInfo.setGyomutantoId(addInfo.getGyomutantoId());
			AccessKanriDao accessKanriDao = new AccessKanriDao(userInfo);
			//�o�^�f�[�^��DB�ɑ��݂���ƃG���[�ƂȂ邽�߁A�O�̂��ߍ폜���������s���Ă���
			accessKanriDao.deleteAccessKanri(connection, addInfo);
			for(int i = 0; i < addInfo.getJigyoValues().size(); i++) {
				String jigyoKubun = MasterJigyoInfoDao.selectJigyoKubun(connection, addInfo.getJigyoValues().get(i).toString());
				accessKanriInfo.setJigyoKubun(jigyoKubun);
				accessKanriInfo.setJigyoCd(addInfo.getJigyoValues().get(i).toString());
				accessKanriDao.insertAccessKanriInfo(connection, accessKanriInfo);
			}

			//---------------------------------------
			//�o�^�f�[�^�̎擾
			//---------------------------------------
			GyomutantoInfo result = gyomutantoInfoDao.selectGyomutantoInfo(connection, addInfo);

			//�A�N�Z�X����e�[�u������S�����Ə���ݒ�
			HashMap accessKanriMap = (HashMap)accessKanriDao.selectAccessKanri(connection, addInfo);
			HashSet tantoJigyoCdSet = (HashSet)accessKanriMap.get("tantoJigyoCd");
			ArrayList tantoJigyoCdList = new ArrayList(tantoJigyoCdSet);
			result.setJigyoValues(tantoJigyoCdList);

			//---------------------------------------
			//�o�^����I��
			//---------------------------------------
			success = true;			
			
			return result;
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�Ɩ��S���ҊǗ��f�[�^�o�^����DB�G���[���������܂����B",
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
					"�Ɩ��S���ҊǗ��f�[�^�o�^����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �Ɩ��S���ҏ��̍X�V.<br /><br />
	 * �Ɩ��S���ҏ��A�A�N�Z�X�Ǘ����̍X�V���s���B<br /><br />
	 * 
	 * <b>1.�Ɩ��S���ҏ��̍X�V</b><br /><br />
	 * �Ɩ��S���ҏ��̍X�V���s���B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 		GYOMUTANTOINFO
	 * SET
	 * 		ADMIN_FLG=?
	 * 		,PASSWORD=?
	 * 		,NAME_KANJI_SEI=?
	 * 		,NAME_KANJI_MEI=?
	 * 		,NAME_KANA_SEI=?
	 * 		,NAME_KANA_MEI=?
	 * 		,BUKA_NAME=?
	 * 		,KAKARI_NAME=?
	 * 		,BIKO=?
	 * 		,YUKO_DATE=?
	 * 		,DEL_FLG=?
	 * WHERE
	 * 		GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ǘ��҃t���O</td><td>������updateInfo�̕ϐ�AdminFlg���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�p�X���[�h</td><td>������updateInfo�̕ϐ�Password���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�����|���j</td><td>������updateInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�����|���j</td><td>������updateInfo�̕ϐ�NameKanjiMei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i�|���j</td><td>������updateInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i�|���j</td><td>������updateInfo�̕ϐ�NameKanaMei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ۖ�</td><td>������updateInfo�̕ϐ�BukaName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�W��</td><td>������updateInfo�̕ϐ�KakariName���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>������updateInfo�̕ϐ�Biko���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�L������</td><td>������updateInfo�̕ϐ�YukoDate���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>������updateInfo�̕ϐ�DelFlg���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������updateInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * <b>2.�A�N�Z�X�Ǘ����̍X�V</b><br /><br />
	 * �A�N�Z�X�Ǘ����̍X�V���s���B�X�V�́A��x���𕨗��폜������ɐV��������}�����邱�Ƃōs����B<br /><br />
	 * 
	 * 
	 * �@(1)�A�N�Z�X�Ǘ����̕����폜<br /><br />
	 * �@�@�Y������S���҂̃A�N�Z�X�Ǘ����̕����폜���s���B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * DELETE
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������updateInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * �@(2)�A�N�Z�X�Ǘ����̓o�^<br /><br />
	 * �@�@���Ƌ敪���擾����B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		NVL(A.JIGYO_KUBUN,0) JIGYO_KUBUN
	 * FROM
	 * 		MASTER_JIGYO A
	 * WHERE
	 * 		JIGYO_CD=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>������updateInfo�̕ϐ�JigyoValues����jigyoCd���g�p����B</td></tr>
	 * </table><br />
	 * �@�@�擾�������Ƌ敪�Ǝ��ƃR�[�h���AAccessKanriInfo�Ɋi�[����B<br /><br />
	 * 
	 * 
	 * �@(3)�S�����Ə��̓o�^<br />
	 * �@�@��d�o�^�ɂȂ�Ȃ����d���`�F�b�N���s���A�d���łȂ���Γo�^���s���B<br />
	 * �@�@�ȉ���SQL���𔭍s���A�Ɩ��S���ҏ����擾����B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		GYOMUTANTO_ID
	 * 		,JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * 		,BIKO
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID=?
	 * 		AND JIGYO_CD=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>AccessKanriInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>AccessKanriInfo�̕ϐ�JigyoCd���g�p����B</td></tr>
	 * </table><br />
	 * �@�@�Y������l���������ꍇ�́A��O��throw����B<br />
	 * �@�@�d��������̂��Ȃ���΁A�o�^���s���B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * INSERT INTO ACCESSKANRI
	 * 		(
	 * 		GYOMUTANTO_ID
	 * 		,JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * 		,BIKO
	 * 		)
	 * VALUES
	 * 		(?,?,?,?)</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>AccessKanriInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>AccessKanriInfo�̕ϐ�JigyoCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>AccessKanriInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>AccessKanriInfo�̕ϐ�JigyoBiko���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �@(2),(3)��addInfo��List<b>"getJigyoValues"</b>�̌��������J��Ԃ��B<br /><br />
	 * 
	 * �@�X�V��ƒ��ɗ�O������������A��O��throw����B<br /><br />
	 * @param userInfo UserInfo
	 * @param updateInfo GyomutantoInfo
	 * @return
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.GyomutantoInfo)
	 */
	public void update(UserInfo userInfo, GyomutantoInfo updateInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�Ɩ��S���ҏ��̍X�V
			//---------------------------------------
			GyomutantoInfoDao dao = new GyomutantoInfoDao(userInfo);
			dao.updateGyomutantoInfo(connection, updateInfo);
			
			//�A�N�Z�X�Ǘ����̍X�V(�폜���o�^)
			AccessKanriInfo accessKanriInfo = new AccessKanriInfo();
			accessKanriInfo.setGyomutantoId(updateInfo.getGyomutantoId());
			AccessKanriDao accessKanriDao = new AccessKanriDao(userInfo);
			accessKanriDao.deleteAccessKanri(connection, updateInfo);
			for(int i = 0; i < updateInfo.getJigyoValues().size(); i++) {
				String jigyoKubun = MasterJigyoInfoDao.selectJigyoKubun(connection, updateInfo.getJigyoValues().get(i).toString());
				accessKanriInfo.setJigyoKubun(jigyoKubun);
				accessKanriInfo.setJigyoCd(updateInfo.getJigyoValues().get(i).toString());
				accessKanriDao.insertAccessKanriInfo(connection, accessKanriInfo);
			}
			
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�Ɩ��S���ҊǗ��f�[�^�X�V����DB�G���[���������܂����B",
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
					"�Ɩ��S���ҊǗ��f�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �Ɩ��S���ҏ��̍폜.<br /><br />
	 * 
	 * �Ɩ��S���ҏ��̘_���폜���s���B<br /><br />
	 * 
	 * <b>1.�Ɩ��S���ҏ��̌���</b><br /><br />
	 * 
	 * �@�ȉ���SQL���𔭍s���A�Ɩ��S���ҏ�����������B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * 	SELECT
	 * 			GYOMUTANTO_ID
	 * 			,ADMIN_FLG
	 * 			,PASSWORD
	 * 			,NAME_KANJI_SEI
	 * 			,NAME_KANJI_MEI
	 * 			,NAME_KANA_SEI
	 * 			,NAME_KANA_MEI
	 * 			,BUKA_NAME
	 * 			,KAKARI_NAME
	 * 			,BIKO
	 * 			,YUKO_DATE
	 * 			,DEL_FLG
	 * 	FROM
	 * 			GYOMUTANTOINFO
	 * 	WHERE
	 * 			GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������addInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * <b>2.�Ɩ��S���ҏ��̘_���폜</b><br /><br />
	 * �@�ȉ���SQL���𔭍s���A�Ɩ��S���ҏ��̘_���폜���s���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 		GYOMUTANTOINFO
	 * SET
	 * 		DEL_FLG = 1
	 * WHERE
	 * 		GYOMUTANTO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������deleteInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * @param userInfo UserInfo
	 * @param deleteInfo GyomutantoInfo
	 * @return
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.GyomutantoInfo)
	 */
	public void delete(UserInfo userInfo, GyomutantoInfo deleteInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�Ɩ��S���ҏ��̍X�V
			//---------------------------------------
			GyomutantoInfoDao dao = new GyomutantoInfoDao(userInfo);
			dao.deleteFlgGyomutantoInfo(connection, deleteInfo);
			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�Ɩ��S���ҊǗ��f�[�^�폜����DB�G���[���������܂����B",
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
					"�Ɩ��S���ҊǗ��f�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	/**
	 * �Ɩ��S���ҏ��̎擾.<br /><br />
	 * �Ɩ��S���ҏ����擾���A�A�N�Z�X������������ĕԋp����B<br /><br />
	 * 
	 * <b>1.�o�^�f�[�^�̎擾</b><br /><br />
	 * �Ɩ��S���҂̓o�^�f�[�^���擾����B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		GYOMUTANTO_ID
	 * 		,ADMIN_FLG
	 * 		,PASSWORD
	 * 		,NAME_KANJI_SEI
	 * 		,NAME_KANJI_MEI
	 * 		,NAME_KANA_SEI
	 * 		,NAME_KANA_MEI
	 * 		,BUKA_NAME
	 * 		,KAKARI_NAME
	 * 		,BIKO
	 * 		,YUKO_DATE
	 * 		,DEL_FLG
	 * FROM
	 * 		GYOMUTANTOINFO
	 * WHERE
	 * 		GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������addInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́AGyomutantoInfo�Ɋi�[����B<br /><br />
	 * 
	 * <b>2.�A�N�Z�X�Ǘ����̐ݒ�</b><br /><br />
	 * �Y���Ɩ��S���҂̃A�N�Z�X��������擾����B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID=?
	 * ORDER BY
	 * 		GYOMUTANTO_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������addInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * �擾�����l�����ꂼ���HashSet�Ɋi�[���A���̓��HashSet��Map�Ɋi�[����B<br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>�l</td><td>HashSet�̖��O</td><td>Map�i�[���̃L�[��</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>tantoJigyoCd</td><td>tantoJigyoCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>tantoJigyoKubun</td><td>tantoJigyoKubun</td></tr>
	 * </table><br />
	 * �擾����Map������HashSet"<b>tantoJigyoCd</b>"�����o���AArrayList�Ɋi�[����B
	 * ����List��GyomutantoInfo�ɉ����ĕԋp����B
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo GyomutantoPk
	 * @return �Ɩ��S���ҏ�񂪊i�[���ꂽGyomutantoInfo
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#select(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.GyomutantoPk)
	 */
	public GyomutantoInfo select(UserInfo userInfo, GyomutantoPk pkInfo)
			throws ApplicationException {

		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			GyomutantoInfoDao dao = new GyomutantoInfoDao(userInfo);
			GyomutantoInfo gyomutantoInfo = dao.selectGyomutantoInfo(connection, pkInfo);
			
			//�A�N�Z�X�Ǘ����̐ݒ�
			AccessKanriInfo accessKanriInfo = new AccessKanriInfo();
			accessKanriInfo.setGyomutantoId(pkInfo.getGyomutantoId());
			AccessKanriDao accessKanriDao = new AccessKanriDao(userInfo);
			HashMap accessKanriMap = (HashMap)accessKanriDao.selectAccessKanri(connection, pkInfo);
			HashSet tantoJigyoCdSet = (HashSet)accessKanriMap.get("tantoJigyoCd");
			ArrayList tantoJigyoCdList = new ArrayList(tantoJigyoCdSet);
			gyomutantoInfo.setJigyoValues(tantoJigyoCdList);
			
			return gyomutantoInfo;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�Ɩ��S���ҊǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * �Ɩ��S���ҏ�������Page��Ԃ�.<br /><br />
	 * 
	 * <b>1.�Ɩ��S���҂̃f�[�^�̎擾</b><br /><br />
	 * �ȉ���SQL���𔭍s���āA�Ɩ��S���҂̃f�[�^���擾����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * SELECT
	 * 		*
	 * FROM
	 * 		GYOMUTANTOINFO
	 * WHERE
	 * 		DEL_FLG = 0
	 * 		AND ADMIN_FLG = 0
	 * ORDER BY
	 * 		GYOMUTANTO_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́A�񖼂��L�[�ɂ���Map�Ɋi�[�����B������܂Ƃ߂�List�Ɋi�[���A����List��Page�Ɋi�[�����B<br /><br />
	 * 
	 * <b>2.�A�N�Z�X�Ǘ����̎擾</b><br /><br />
	 * �擾����Page����List����Map�����o���āA�Y���Ɩ��S���҂̃A�N�Z�X��������i�[���Ă����B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		JIGYO_CD
	 * 		,JIGYO_KUBUN
	 * FROM
	 * 		ACCESSKANRI
	 * WHERE
	 * 		GYOMUTANTO_ID=?
	 * ORDER BY
	 * 		GYOMUTANTO_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>Map�Ɋi�[���ꂽGYOMUTANTO_ID�̒l���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾����Map�Ɋi�[����Ă���"���ƃR�[�h"�̒l�̓�����HashSet�����o���AArrayList�Ɋi�[����B
	 * ����ArrayList���A�L�[��"VALUES"�ŁA�͂��߂Ɏ��o����Map�ɍēx�i�[����B
	 * ���̏������AMap�̐������J��Ԃ��B<br /><br />
	 * 
	 * 
	 * <b>3.Page�̕ԋp</b><br /><br />
	 * ���ׂĂ�Map�ɃA�N�Z�X�����񂪊i�[���ꂽ��AList���ēxPage�Ɋi�[���A�ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @return �Ɩ��S���ҏ�������Page
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public Page search(UserInfo userInfo)
			throws ApplicationException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------

		String select = "SELECT * FROM GYOMUTANTOINFO"
			+ " WHERE DEL_FLG = 0"
			+ " AND ADMIN_FLG = 0"
			+ " ORDER BY GYOMUTANTO_ID";
			
		if(log.isDebugEnabled()){
			log.debug("select:" + select);
		}
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		SearchInfo searchInfo = new SearchInfo();
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			Page page = SelectUtil.selectPageInfo(connection, searchInfo, select);
			
			//�A�N�Z�X�Ǘ����̐ݒ�
			AccessKanriDao accessKanriDao = new AccessKanriDao(userInfo);
			List list = page.getList();
			for(int i = 0; i < list.size(); i++) {
				HashMap hashMap = (HashMap)list.get(i);
				GyomutantoPk pkInfo = new GyomutantoPk();
				pkInfo.setGyomutantoId(hashMap.get("GYOMUTANTO_ID").toString());
				HashMap accessKanriMap = (HashMap)accessKanriDao.selectAccessKanri(connection, pkInfo);
				HashSet tantoJigyoCdSet = (HashSet)accessKanriMap.get("tantoJigyoCd");
				ArrayList tantoJigyoCdList = new ArrayList(tantoJigyoCdSet);
				hashMap.put("VALUES", tantoJigyoCdList);
				list.set(i, hashMap);
			}
			page.setList(list);
			return page;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�Ɩ��S���ҊǗ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * 2�d�o�^�̃`�F�b�N.<br /><br />
	 * �Ɩ��S���ҏ��e�[�u���ɓ����Ɩ��S���҂��o�^����Ă��Ȃ����ǂ����m�F����B<br /><br />
	 * 
	 * <b>1.2�d�o�^�̃`�F�b�N�̎��s</b><br /><br />
	 * ��O������String"mode"���A"<b>add_mode</b>"�ł���Ƃ��ɁA�d���`�F�b�N�����s����B
	 * ���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		COUNT(*)"
	 * FROM
	 * 		GYOMUTANTOINFO"
	 * WHERE
	 * 		GYOMUTANTO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������info�̕ϐ�gyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �Y������Ɩ��S���҂�ID�����݂��Ă���ꍇ�ɂ́A��O��thorw����B<br /><br />
	 * 
	 * <b>2.�l�̕ԋp</b><br /><br />
	 * ��O������String"mode"���A"<b>add_mode</b>"�łȂ��Ƃ��A
	 * ���͓o�^���d�����Ă��Ȃ��Ƃ��ɂ́A��������info�����̂܂ܕԋp����B<br /><br />
	 * @param userInfo UserInfo
	 * @param info GyomutantoInfo
	 * @param mode String
	 * @return GyomutantoInfo
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.GyomutantoInfo)
	 */
	public GyomutantoInfo validate(UserInfo userInfo, GyomutantoInfo info, String mode)
			throws ApplicationException, ValidationException {
		if(mode.equals(IMaintenanceName.ADD_MODE)) {
			//2�d�o�^�`�F�b�N
			//�Ɩ��S���ҏ��e�[�u���ɂ��ł�ID�������Ɩ��S���҂��o�^����Ă��Ȃ����ǂ������m�F
			Connection connection = null;
			GyomutantoInfoDao dao = new GyomutantoInfoDao(userInfo);
			try {
				connection = DatabaseUtil.getConnection();
				int count = dao.countGyomutantoInfo(connection, info.getGyomutantoId());
				//���łɓo�^����Ă���ꍇ
				if(count > 0){
					String[] error = {"�Ɩ��S����"};
					throw new ApplicationException("���łɋƖ��S���҂��o�^����Ă��܂��B", 	new ErrorInfo("errors.4007", error));			
				}
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�Ɩ��S���҃f�[�^�`�F�b�N����DB�G���[���������܂����B",
					new ErrorInfo("errors.4005"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		return info;
	}

	/**
	 * �p�X���[�h�̍X�V.<br /><br />
	 * <b>1.�o�^�f�[�^�̎擾</b><br /><br />
	 * �Ɩ��S���҂̓o�^�f�[�^���擾����B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 		GYOMUTANTO_ID
	 * 		,ADMIN_FLG
	 * 		,PASSWORD
	 * 		,NAME_KANJI_SEI
	 * 		,NAME_KANJI_MEI
	 * 		,NAME_KANA_SEI
	 * 		,NAME_KANA_MEI
	 * 		,BUKA_NAME
	 * 		,KAKARI_NAME
	 * 		,BIKO
	 * 		,YUKO_DATE
	 * 		,DEL_FLG
	 * FROM
	 * 		GYOMUTANTOINFO
	 * WHERE
	 * 		GYOMUTANTO_ID=?</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������pkInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́AGyomutantoInfo�Ɋi�[����B<br /><br />
	 * 
	 * <b>2.�p�X���[�h�̍X�V</b><br /><br />
	 * 
	 * ��O����oldPassword���A�擾�����l�̃p�X���[�h�ƈقȂ�ꍇ�ɂ́A��O��throw����B<br />
	 * �������ꍇ�ɂ́A�p�X���[�h���l����newPassword�ɏ���������B���s�����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 		GYOMUTANTOINFO
	 * SET
	 * 		PASSWORD = ?
	 * WHERE
	 * 		GYOMUTANTO_ID = ?
	 * 		AND DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>��l����newPassword���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S��ID</td><td>������pkInfo�̕ϐ�GyomutantoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �X�V���I��������A"true"��ԋp����B�r���ŗ�O������������A��O��throw����B<br /><br />
	 * @param userInfo UserInfo
	 * @param pkInfo GyomutantoPk
	 * @param oldPassword String
	 * @param newPassword String
	 * @return boolean
	 * @see jp.go.jsps.kaken.model.IGyomutantoMaintenance#changePassword(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.GyomutantoPk, java.lang.String, java.lang.String)
	 */
	public boolean changePassword(
			UserInfo userInfo,
			GyomutantoPk pkInfo,
			String oldPassword,
			String newPassword)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			//---------------------------------------
			//�Ɩ��S���ҏ��̎擾
			//---------------------------------------
			GyomutantoInfoDao dao = new GyomutantoInfoDao(userInfo);
			GyomutantoInfo info = dao.selectGyomutantoInfo(connection, pkInfo);

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
			if(dao.changePasswordGyomutantoInfo(connection,pkInfo,newPassword)){
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
}
