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
import jp.go.jsps.kaken.model.IKanrenBunyaMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.sql.Connection;
import java.util.List;



/**
 * �֘A������Ǘ����s���N���X.<br><br>
 * 
 * �T�v�F<br>
 * �\���f�[�^�Ǘ��e�[�u���F�\���f�[�^�̏����Ǘ�
 */
public class KanrenBunyaMaintenance implements IKanrenBunyaMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	
	/** ���O. */
	protected static Log log = LogFactory.getLog(KanrenBunyaMaintenance.class);
	
		
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^.
	 */
	public KanrenBunyaMaintenance() {
		super();
	}
	
	//---------------------------------------------------------------------
	// implement IKanrenBunyaMaintenance
	//---------------------------------------------------------------------
	/** 
	 * �֘A������̃y�[�W�����擾.<br><br>
	 * 
	 * �֘A������̃y�[�W�����擾����B<br><br>
	 * 
	 * ������searchInfo�����������Ɋ�Â��āA�\���f�[�^�Ǘ��e�[�u������������B<br>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.SYSTEM_NO,					-- �V�X�e����t�ԍ�
	 *     A.UKETUKE_NO,				-- �\���ԍ�
	 *     A.JIGYO_ID,					-- ����ID
	 *     A.NENDO,						-- �N�x
	 *     A.KAISU,						-- ��
	 *     A.JIGYO_NAME,				-- ���Ɩ�
	 *     A.SHINSEISHA_ID,				-- �\����ID
	 *     A.NAME_KANJI_SEI,			-- �\���Ҏ����i������-���j
	 *     A.NAME_KANJI_MEI,			-- �\���Ҏ����i������-���j
	 *     A.SHOZOKU_CD,				-- �����@�փR�[�h
	 *     A.SHOZOKU_NAME_RYAKU,		-- �����@�֖��i���́j
	 *     A.BUKYOKU_NAME_RYAKU,		-- ���ǖ��i���́j
	 *     A.SHOKUSHU_NAME_RYAKU,		-- �E���i���́j
	 *     A.KADAI_NAME_KANJI,			-- �����ۑ薼(�a���j
	 *     A.KEI_NAME_RYAKU,			-- �n���̋敪�i���́j
	 *     A.KANREN_SHIMEI1,		-- �֘A����̌�����-����1
	 *     A.KANREN_KIKAN1,			-- �֘A����̌�����-�����@��1
	 *     A.KANREN_BUKYOKU1,		-- �֘A����̌�����-��������1
	 *     A.KANREN_SHOKU1,			-- �֘A����̌�����-�E��1
	 *     A.KANREN_SENMON1,		-- �֘A����̌�����-��啪��1
	 *     A.KANREN_TEL1,			-- �֘A����̌�����-�Ζ���d�b�ԍ�1
	 *     A.KANREN_JITAKUTEL1,		-- �֘A����̌�����-����d�b�ԍ�1
	 *     A.KANREN_MAIL1,			-- �֘A����̌�����-E-Mail1
	 *     A.KANREN_SHIMEI2,		-- �֘A����̌�����-����2
	 *     A.KANREN_KIKAN2,			-- �֘A����̌�����-�����@��2
	 *     A.KANREN_BUKYOKU2,		-- �֘A����̌�����-��������2
	 *     A.KANREN_SHOKU2,			-- �֘A����̌�����-�E��2
	 *     A.KANREN_SENMON2,		-- �֘A����̌�����-��啪��2
	 *     A.KANREN_TEL2,			-- �֘A����̌�����-�Ζ���d�b�ԍ�2
	 *     A.KANREN_JITAKUTEL2,		-- �֘A����̌�����-����d�b�ԍ�2
	 *     A.KANREN_MAIL2,			-- �֘A����̌�����-E-Mail2
	 *     A.KANREN_SHIMEI3,		-- �֘A����̌�����-����3
	 *     A.KANREN_KIKAN3,			-- �֘A����̌�����-�����@��3
	 *     A.KANREN_BUKYOKU3,		-- �֘A����̌�����-��������3
	 *     A.KANREN_SHOKU3,			-- �֘A����̌�����-�E��3
	 *     A.KANREN_SENMON3,		-- �֘A����̌�����-��啪��3
	 *     A.KANREN_TEL3,			-- �֘A����̌�����-�Ζ���d�b�ԍ�3
	 *     A.KANREN_JITAKUTEL3,		-- �֘A����̌�����-����d�b�ԍ�3
	 *     A.KANREN_MAIL3,			-- �֘A����̌�����-E-Mail3
	 *     A.JOKYO_ID,				-- �\����ID
	 *     A.SAISHINSEI_FLG			-- �Đ\���t���O
	 * FROM
	 *     SHINSEIDATAKANRI A		-- �\���f�[�^�Ǘ��e�[�u��
	 * WHERE
	 *     A.DEL_FLG = 0			-- �폜�t���O��[0]
	 * 
	 * 	--- ���I��������1 ---
	 * 
     * </pre>
     * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">���I��������1</span></b><br/>
	 * searchInfo�ɂ���Č������������I�ɕω�����B<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>systemNo</td><td>AND JIGYO_ID = '�V�X�e���ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>uketukeNo</td><td>AND A.UKETUKE_NO = '�\���ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoId</td><td>AND A.JIGYO_ID = '����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����CD</td><td>jigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) = '����CD'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S������CD</td><td>tantoJigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) IN ('�S������CD1', '�S������CD2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ɩ�</td><td>jigyoName</td><td>AND A.JIGYO_NAME = '���Ɩ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�N�x</td><td>nend</td><td>AND A.NENDO = '�N�x'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>��</td><td>kaisu</td><td>AND A.KAISU = '��'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\����ID</td><td>shinseishaId</td><td>AND A.SHINSEISHA_ID = '�\����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i�����F���j</td><td>nameKanjiSei</td><td>AND A.NAME_KANJI_SEI like '%�\���Җ��i����)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i�����F���j</td><td>nameKanjiMei</td><td>AND A.NAME_KANJI_MEI like '%�\���Җ��i�����F���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i�J�i�F���j</td><td>nameKanaSei</td><td>AND A.NAME_KANA_SEI like '%�\���Җ��i�J�i�F���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i�J�i�F���j</td><td>nameKanaMei</td><td>AND A.NAME_KANA_MEI like '%�\���Җ��i�J�i�F���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i���[�}���F���j</td><td>nameRoSei</td><td>AND UPPER(A.NAME_RO_SEI) like '%�\���Җ��i���[�}���F���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i���[�}���F���j</td><td>nameRoMei</td><td>AND UPPER(A.NAME_RO_MEI) like '%�\���Җ��i���[�}���F���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҍ����Ҕԍ�</td><td>kenkyuNo</td><td>AND A.KENKYU_NO = '�\���Ҍ����Ҕԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>jigyoKubun</td><td>AND A.JIGYO_KUBUN IN ('���Ƌ敪1', '���Ƌ敪2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>shozokuCd</td><td>AND A.SHOZOKU_CD = '�����@�փR�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�n���̋敪�ԍ�</td><td>keiNameNo</td><td>AND A.KEI_NAME_NO = '�n���̋敪�ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�n���̋敪</td><td>keiName</td><td>AND (A.KEI_NAME like '%�n���̋敪%' OR A.KEI_NAME_RYAKU like '%�n���̋敪%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�זڔԍ�</td><td>bunkasaimokuCd</td><td>AND (A.BUNKASAIMOKU_CD = '�זڔԍ�' OR A.BUNKASAIMOKU_CD2 = '�זڔԍ�')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���E�̊ϓ_�ԍ�</td><td>kantenNo</td><td>AND A.KANTEN_NO = '���E�̊ϓ_�ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���E�̊ϓ_</td><td>kanten</td><td>AND A.KANTEN = '���E�̊ϓ_'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���E�̊ϓ_����</td><td>kantenRyaku</td><td>AND A.KANTEN_RYAKU = '���E�̊ϓ_����'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�֘A����̌����Ҏ���</td><td>kanrenShimei</td><td>AND (A.KANREN_SHIMEI1 like '%�֘A����̌����Ҏ���1%' OR A.KANREN_SHIMEI2 like '%�֘A����̌����Ҏ���%' OR A.KANREN_SHIMEI3 like '%�֘A����̌����Ҏ���%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\����</td><td>jokyoId</td><td>AND A.JOKYO_ID IN ('�\����1', '�\����2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�Đ\���t���O</td><td>saishinseiFlg</td><td>AND A.SAISHINSEI_FLG IN ('�Đ\���t���O1', '�Đ\���t���O2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>2���R������</td><td>kekka2</td><td>AND A.KEKKA2 IN ('2���R������1', '2���R������2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�쐬���iFrom�j</td><td>sakuseiDateFrom</td><td>AND A.SAKUSEI_DATE >= TO_DATE('�쐬���iFrom�j', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�쐬���iTo�j</td><td>sakuseiDateTo</td><td>AND A.SAKUSEI_DATE <= TO_DATE('�쐬���iTo�j', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֏��F���iFrom�j</td><td>shoninDateFrom</td><td>AND A.SHONIN_DATE >= TO_DATE('�����@�֏��F���iFrom�j', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֏��F���iTo�j</td><td>shoninDateTo</td><td>AND A.SHONIN_DATE <= TO_DATE('�����@�֏��F���iTo�j', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><td>bukyokuCd</td><td>AND A.BUKYOKU_CD = '���ǃR�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�g�ݍ��킹�X�e�[�^�X��</td><td>query</td><td>AND '�g�ݍ��킹�X�e�[�^�X��'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>delFlg</td><td>AND A.DEL_FLG IN ('�폜�t���O1', '�폜�t���O2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����L�[</td><td>order</td><td>ORDER BY '����L�[1', '����L�[2',�c</td></tr>
	 * </table><br><br>
	 * 
	 * �擾�����l��Page�^pageInfo�Ɋi�[����B<br><br>
	 * 
	 * pageInfo��ԋp����B
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	��������(ShinseiSearchInfo)
	 * @see jp.go.jsps.kaken.model.IKanrenBunyaMaintenance#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiSearchInfo)
	 */
	public Page search(UserInfo userInfo, ShinseiSearchInfo searchInfo)
		throws ApplicationException {
		
		//ShinseiDataInfoDao��new����
		ShinseiDataInfoDao shinseidatainfodao = new ShinseiDataInfoDao(userInfo);
		
		//Page�I�u�W�F�N�g���쐬����
		Page pageInfo = null;
		
		//DB�R�l�N�V�������擾����
		Connection connection = null;
		
		try {
			connection = DatabaseUtil.getConnection();
			//�Y�����R�[�h��S���擾����
			pageInfo = shinseidatainfodao.searchKanrenbunyaList(connection, searchInfo);
			
			//Page�I�u�W�F�N�g��return����
			return pageInfo;
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} 
		finally {
			//DB�R�l�N�V���������
			DatabaseUtil.closeConnection(connection);
		}
		
		
	}
	
	/**
	 * �֘A������CSV�o�̓f�[�^�쐬.<br><br>
	 * 
	 * �֘A�������CSV�o�͂��邽�߂ɁA���������ɊY�����郌�R�[�h��List�֊i�[���A�Ăяo�����֕ԋp����B<br>
	 * ���̍ہA�e���R�[�h���͗񂲂Ƃ�List�֊i�[���ꂽ�����ŕԋp����List�֊i�[�����B(List�ɂ��2�����z��\��)<br>
	 * �Ȃ��A�ԋp����List�̈�ڂ̗v�f�́A�w�b�_�[�����i�[����B<br><br>
	 * 
	 * (1)�֘A��������擾���ACSV�`���̃��X�g���쐬����B<br>
	 * �ȉ���SQL�����s����B�i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.NENDO,						-- �N�x
	 *     A.KAISU,						-- ��
	 *     A.JIGYO_NAME,				-- ���Ɩ�
	 *     A.KEI_NAME,					-- �n���̋敪
	 *     A.KADAI_NAME_KANJI,			-- �����ۑ薼(�a���j
	 *     A.NAME_KANJI_SEI,			-- �\���Ҏ����i������-���j
	 *     A.NAME_KANJI_MEI,			-- �\���Ҏ����i������-���j
	 *     A.SHOZOKU_CD,				-- �����@�փR�[�h
	 *     A.SHOZOKU_NAME,				-- �����@�֖�
	 *     A.BUKYOKU_NAME,				-- ���ǖ�
	 *     A.SHOKUSHU_NAME_KANJI,		-- �E��
	 *     A.KANREN_SHIMEI1,		-- �֘A����̌�����-����1
	 *     A.KANREN_KIKAN1,			-- �֘A����̌�����-�����@��1
	 *     A.KANREN_BUKYOKU1,		-- �֘A����̌�����-��������1
	 *     A.KANREN_SHOKU1,			-- �֘A����̌�����-�E��1
	 *     A.KANREN_SENMON1,		-- �֘A����̌�����-��啪��1
	 *     A.KANREN_TEL1,			-- �֘A����̌�����-�Ζ���d�b�ԍ�1
	 *     A.KANREN_JITAKUTEL1,		-- �֘A����̌�����-����d�b�ԍ�1
	 *     A.KANREN_MAIL1,			-- �֘A����̌�����-E-Mail1
	 *     A.KANREN_SHIMEI2,		-- �֘A����̌�����-����2
	 *     A.KANREN_KIKAN2,			-- �֘A����̌�����-�����@��2
	 *     A.KANREN_BUKYOKU2,		-- �֘A����̌�����-��������2
	 *     A.KANREN_SHOKU2,			-- �֘A����̌�����-�E��2
	 *     A.KANREN_SENMON2,		-- �֘A����̌�����-��啪��2
	 *     A.KANREN_TEL2,			-- �֘A����̌�����-�Ζ���d�b�ԍ�2
	 *     A.KANREN_JITAKUTEL2,		-- �֘A����̌�����-����d�b�ԍ�2
	 *     A.KANREN_MAIL2,			-- �֘A����̌�����-E-Mail2
	 *     A.KANREN_SHIMEI3,		-- �֘A����̌�����-����3
	 *     A.KANREN_KIKAN3,			-- �֘A����̌�����-�����@��3
	 *     A.KANREN_BUKYOKU3,		-- �֘A����̌�����-��������3
	 *     A.KANREN_SHOKU3,			-- �֘A����̌�����-�E��3
	 *     A.KANREN_SENMON3,		-- �֘A����̌�����-��啪��3
	 *     A.KANREN_TEL3,			-- �֘A����̌�����-�Ζ���d�b�ԍ�3
	 *     A.KANREN_JITAKUTEL3,		-- �֘A����̌�����-����d�b�ԍ�3
	 *     A.KANREN_MAIL3"			-- �֘A����̌�����-E-Mail3
	 * FROM
	 *     SHINSEIDATAKANRI A		-- �\���f�[�^�Ǘ��e�[�u��
	 * WHERE
	 *     A.DEL_FLG = 0				-- �폜�t���O��[0]
	 * 
	 * 	--- ���I��������1 ---
	 * 
     * </pre>
     * </td></tr>
	 * </table><br>
	 * 
	 * <b><span style="color:#002288">���I��������1</span></b><br>
	 * searchInfo�ɂ���Č������������I�ɕω�����B<br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>systemNo</td><td>AND JIGYO_ID = '�V�X�e���ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>uketukeNo</td><td>AND A.UKETUKE_NO = '�\���ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoId</td><td>AND A.JIGYO_ID = '����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����CD</td><td>jigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) = '����CD'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�S������CD</td><td>tantoJigyoCd</td><td>AND SUBSTR(A.JIGYO_ID, 3, 5) IN ('�S������CD1', '�S������CD2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ɩ�</td><td>jigyoName</td><td>AND A.JIGYO_NAME = '���Ɩ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�N�x</td><td>nend</td><td>AND A.NENDO = '�N�x'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>��</td><td>kaisu</td><td>AND A.KAISU = '��'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\����ID</td><td>shinseishaId</td><td>AND A.SHINSEISHA_ID = '�\����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i�����F���j</td><td>nameKanjiSei</td><td>AND A.NAME_KANJI_SEI like '%�\���Җ��i����)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i�����F���j</td><td>nameKanjiMei</td><td>AND A.NAME_KANJI_MEI like '%�\���Җ��i�����F���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i�J�i�F���j</td><td>nameKanaSei</td><td>AND A.NAME_KANA_SEI like '%�\���Җ��i�J�i�F���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i�J�i�F���j</td><td>nameKanaMei</td><td>AND A.NAME_KANA_MEI like '%�\���Җ��i�J�i�F���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i���[�}���F���j</td><td>nameRoSei</td><td>AND UPPER(A.NAME_RO_SEI) like '%�\���Җ��i���[�}���F���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Җ��i���[�}���F���j</td><td>nameRoMei</td><td>AND UPPER(A.NAME_RO_MEI) like '%�\���Җ��i���[�}���F���j%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҍ����Ҕԍ�</td><td>kenkyuNo</td><td>AND A.KENKYU_NO = '�\���Ҍ����Ҕԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>jigyoKubun</td><td>AND A.JIGYO_KUBUN IN ('���Ƌ敪1', '���Ƌ敪2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>shozokuCd</td><td>AND A.SHOZOKU_CD = '�����@�փR�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�n���̋敪�ԍ�</td><td>keiNameNo</td><td>AND A.KEI_NAME_NO = '�n���̋敪�ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�n���̋敪</td><td>keiName</td><td>AND (A.KEI_NAME like '%�n���̋敪%' OR A.KEI_NAME_RYAKU like '%�n���̋敪%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�זڔԍ�</td><td>bunkasaimokuCd</td><td>AND (A.BUNKASAIMOKU_CD = '�זڔԍ�' OR A.BUNKASAIMOKU_CD2 = '�זڔԍ�')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���E�̊ϓ_�ԍ�</td><td>kantenNo</td><td>AND A.KANTEN_NO = '���E�̊ϓ_�ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���E�̊ϓ_</td><td>kanten</td><td>AND A.KANTEN = '���E�̊ϓ_'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���E�̊ϓ_����</td><td>kantenRyaku</td><td>AND A.KANTEN_RYAKU = '���E�̊ϓ_����'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�֘A����̌����Ҏ���</td><td>kanrenShimei</td><td>AND (A.KANREN_SHIMEI1 like '%�֘A����̌����Ҏ���1%' OR A.KANREN_SHIMEI2 like '%�֘A����̌����Ҏ���%' OR A.KANREN_SHIMEI3 like '%�֘A����̌����Ҏ���%')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\����</td><td>jokyoId</td><td>AND A.JOKYO_ID IN ('�\����1', '�\����2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�Đ\���t���O</td><td>saishinseiFlg</td><td>AND A.SAISHINSEI_FLG IN ('�Đ\���t���O1', '�Đ\���t���O2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>2���R������</td><td>kekka2</td><td>AND A.KEKKA2 IN ('2���R������1', '2���R������2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�쐬���iFrom�j</td><td>sakuseiDateFrom</td><td>AND A.SAKUSEI_DATE >= TO_DATE('�쐬���iFrom�j', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�쐬���iTo�j</td><td>sakuseiDateTo</td><td>AND A.SAKUSEI_DATE <= TO_DATE('�쐬���iTo�j', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֏��F���iFrom�j</td><td>shoninDateFrom</td><td>AND A.SHONIN_DATE >= TO_DATE('�����@�֏��F���iFrom�j', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�֏��F���iTo�j</td><td>shoninDateTo</td><td>AND A.SHONIN_DATE <= TO_DATE('�����@�֏��F���iTo�j', 'YYYY/MM/DD')</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><td>bukyokuCd</td><td>AND A.BUKYOKU_CD = '���ǃR�[�h'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�g�ݍ��킹�X�e�[�^�X��</td><td>query</td><td>AND '�g�ݍ��킹�X�e�[�^�X��'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>delFlg</td><td>AND A.DEL_FLG IN ('�폜�t���O1', '�폜�t���O2', �c)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����L�[</td><td>order</td><td>ORDER BY '����L�[1', '����L�[2',�c</td></tr>
	 * </table><br><br>
	 * 
	 * (2)�J��������}��<br>
	 * �J���������X�g�𐶐����A(1)�Ŏ擾�������X�g�̍ŏ��̗v�f�ɑ}������B<br>
	 * �w�蕶�����SQL�̎��ʎq���𒴂��Ă��܂��\�������邽�ߕʂɃZ�b�g����B<br><br>
	 * �J���������X�g�͈ȉ��̒ʂ�B<br>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * �N�x
	 * ��
	 * ���Ɩ�
	 * �\���ԍ�
	 * �n���̋敪
	 * �����ۑ薼(�a���j
	 * �\���Ҏ����i������-���j
	 * �\���Ҏ����i������-���j
	 * �����@�փR�[�h
	 * �����@�֖�
	 * ���ǖ�
	 * �E���i�a���j
	 * �֘A����̌�����-����1�`3
	 * �֘A����̌�����-�����@��1�`3
	 * �֘A����̌�����-��������1�`3
	 * �֘A����̌�����-�E��1�`3
	 * �֘A����̌�����-��啪��1�`3
	 * �֘A����̌�����-�Ζ���d�b�ԍ�1�`3
	 * �֘A����̌�����-����d�b�ԍ�1�`3
	 * �֘A����̌�����-Email1�`3
	 * </pre>
     * </td></tr>
	 * </table><br><br>
	 * 
	 * (3)���X�g��ԋp����B<br>
	 * 
	 * @param userInfo		UserInfo
	 * @param searchInfo	��������(ShinseiSearchInfo)
	 * @return �֘A�����񃊃X�g(CSV)
	 * @see jp.go.jsps.kaken.model.IKanrenBunyaMaintenance#searchCsvData(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiSearchInfo)
	 */
	public List kanrenSearchCsvData(UserInfo userInfo,ShinseiSearchInfo searchInfo )
		throws ApplicationException
	{
		//ShinseiDataInfoDao��new����
		ShinseiDataInfoDao shinseidatainfodao = new ShinseiDataInfoDao(userInfo);
		
		//DB�R�l�N�V�������擾����
		Connection connection = null;
		
		//List�I�u�W�F�N�g���쐬����
		List result = null;
		
		try {
			
				connection = DatabaseUtil.getConnection();
				//List�I�u�W�F�N�g���擾����
				result = shinseidatainfodao.searchKanrenbunyaListCSV(connection, searchInfo);
				//List�I�u�W�F�N�g��return����
				return result;
					
		}catch(DataAccessException e){
			throw new ApplicationException(
			"DB�G���[���������܂����B",
			new ErrorInfo("errors.4004"),
			e);
			
		}
		finally{
				//DB�R�l�N�V���������
				DatabaseUtil.closeConnection(connection);
		}
		
	}
		

}


