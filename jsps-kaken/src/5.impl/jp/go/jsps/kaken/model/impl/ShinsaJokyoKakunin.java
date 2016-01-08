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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.IShinsaJokyoKakunin;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.MasterSogoHyokaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinsaKekkaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinsainInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInfo;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �R���󋵊m�F���s���N���X.<br><br>
 * 
 * �E�g�p�e�[�u��<br>
 * <table>
 * <tr>�R�����ʃe�[�u��<td>�F�R��������U�茋�ʏ��Ɛ\�����̐R�����ʂ��Ǘ�</td></tr>
 * <tr>�\���f�[�^�Ǘ��e�[�u��<td>�F�\���f�[�^�̏����Ǘ�</td></tr>
 * <tr>�R�����}�X�^�e�[�u��<td>�F�R�����̏����Ǘ�</td></tr>
 * </table>
 * 
 */
public class ShinsaJokyoKakunin implements IShinsaJokyoKakunin {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(ShinsaJokyoKakunin.class);

	/** �\�����폜�t���O�i�폜�ς݁j */
	private static final String FLAG_APPLICATION_DELETE     = IShinseiMaintenance.FLAG_APPLICATION_DELETE;
	
	/** �R������CSV�̋��ʕ���(�����̑O)�̌� **/
//2006/11/06 �c�@�C����������    
//	private static final int NUM_CSV_COMMON	= 18;
//	private static final int NUM_CSV_COMMON = 19;
    private static final int NUM_CSV_COMMON = 20;	//���Q�֌W���͊����󋵂�ǉ�������
//2006/11/06�@�c�@�C�������܂�    

	/** �R������CSV�̌����̌� **/
    
//2006/05/23 �X�V��������
//  private static final int NUM_CSV_COUNT	= 22;
//	private static final int NUM_CSV_COUNT	= 24; 2006/04/10 LY
//    private static final int NUM_CSV_COUNT  = 34;  2006/11/06 �c�@�폜
//�c�@�X�V�����܂�
    
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsaJokyoKakunin() {
		super();
	}

	//---------------------------------------------------------------------
	// implement IShinsaiJokyoKakunin
	//---------------------------------------------------------------------

	/**
	 * �R���󋵂�ԋp.<br />
	 * ���������������A�����������̂܂ܕԋp����B
	 * @param userInfo UserInfo
	 * @param addInfo ShinsaJokyoInfo
	 * @return ��������addInfo
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#insert(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoInfo)
	 */
	public ShinsaJokyoInfo insert(UserInfo userInfo, ShinsaJokyoInfo addInfo)
		throws ApplicationException {
			return addInfo;
	}

	/**
	 * ��������\�b�h.<br />
	 * @param userInfo UserInfo
	 * @param addInfo ShinsaJokyoInfo
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#update(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoInfo)
	 */
	public void update(UserInfo userInfo, ShinsaKekkaInfo updateInfo)
		throws ApplicationException {
	}

	/**
	 * ��������\�b�h.<br />
	 * @param userInfo UserInfo
	 * @param addInfo ShinsaJokyoInfo
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoInfo)
	 */
	public void delete(UserInfo userInfo, ShinsaJokyoInfo deleteInfo)
		throws ApplicationException {

	}

	/**
	 * ��������\�b�h.<br />
	 * @param userInfo UserInfo
	 * @param addInfo ShinsaJokyoInfo
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#delete(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoInfo)
	 */
	public ShinsaJokyoInfo select(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
		throws ApplicationException {
		return null;
	}


	/**
	 * 
	 * �\����������A�R����CSV�E�R������CSV�𔻒f����B
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return �������ʂ��i�[����List�I�u�W�F�N�g
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoSearchInfo)
	 */
	public List searchCsvData(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {

		List csvList = null;
		if(!StringUtil.isBlank(searchInfo.getHyojiHoshikiShinsaJokyo())) {
			if("1".equals(searchInfo.getHyojiHoshikiShinsaJokyo())) {
				//�R���󋵈ꗗ�o��
				csvList =  searchJokyoCsvData(userInfo, searchInfo);
			} else if("2".equals(searchInfo.getHyojiHoshikiShinsaJokyo())) {
				//�R�������ꗗ�o��
				csvList = searchKensuCsvData(userInfo, searchInfo);
			}
		} else {
			throw new ApplicationException(
				"�����������s���ł��B",
				new ErrorInfo("errors.4004"));
		}
		return csvList;
	}

	/**
	 * CSV�o�͗p��List�쐬.<br />
	 * <br />
	 * 1.SQL���̍쐬<br /><br />
	 * �ȉ���SQL�������s���A�R���������擾����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *  SELECT
	 * 		C.SHINSAIN_NO "�R�����ԍ�"
	 * 		, C.NAME_KANJI_SEI "�����i�������|���j"
	 * 		, C.NAME_KANJI_MEI "�����i�������|���j"
	 * 		, C.NAME_KANA_SEI "�����i�t���K�i�|���j"
	 * 		, C.NAME_KANA_MEI "�����i�t���K�i�|���j"
	 * 		, C.SHOZOKU_CD "�����@�֖��i�R�[�h�j"
	 * 		, C.SHOZOKU_NAME "�����@�֖��i�a���j"
	 * 		, C.BUKYOKU_NAME "���ǖ��i�a���j"
	 * 		, C.SHOKUSHU_NAME "�E�햼��"
	 * 		, C.SOFU_ZIP "���t��i�X�֔ԍ��j"
	 *		, C.SOFU_ZIPADDRESS "���t��i�Z���j"
	 *		, C.SOFU_ZIPEMAIL "���t��iEmail�j"
	 *		, C.SHOZOKU_TEL "�d�b�ԍ�"
	 *		, C.URL "URL"
	 *	 	, C.SENMON "��啪��"
	 * 		, TO_CHAR(C.KOSHIN_DATE, 'YYYY/MM/DD') "�f�[�^�X�V��"
	 * 		, C.BIKO "���l"
	 * 		, A.UKETUKE_NO "�\���ԍ�"
	 * 		, A.JIGYO_ID "����ID"
	 * 		, A.JIGYO_NAME "���Ɩ�"
	 * 		, A.KEKKA_ABC "�R������(ABC)"
	 * 		, A.KEKKA_TEN "�R������(�_��)"
	 * 		, A.COMMENT1 "�R�����g1"
	 * 		, A.COMMENT2 "�R�����g2"
	 * 		, A.COMMENT3 "�R�����g3"
	 * 		, A.COMMENT4 "�R�����g4"
	 * 		, A.COMMENT5 "�R�����g5"
	 * 		, A.COMMENT6 "�R�����g6"
	 * 		, A.KENKYUNAIYO "�������e"
	 * 		, A.KENKYUKEIKAKU "�����v��"
	 * 		, A.TEKISETSU_KAIGAI "�K�ؐ�-�C�O���撲����G�茤��"
	 * 		, A.TEKISETSU_KENKYU1 "�K�ؐ�-���S��"
	 * 		, A.DATO "�����o��̑Ó���"
	 * 		, A.SHINSEISHA "�֌W�ғ�"
	 * 		, A.COMMENTS "�R�����g"
	 * 		, REPLACE(
	 * 		         REPLACE(A.SHINSA_JOKYO, '0', '������')
	 * 		          , '1', '����') "�R����"
	 * 	FROM
	 * 		SHINSAKEKKA A
	 * 		, SHINSEIDATAKANRI B
	 * 		, MASTER_SHINSAIN C
	 * 	WHERE
	 * 		A.SYSTEM_NO = B.SYSTEM_NO
	 * 		AND A.SHINSAIN_NO = C.SHINSAIN_NO
	 * 		AND A.SHINSAIN_NO NOT LIKE '@%'
	 * 		AND B.DEL_FLG = 0 
	 *	
	 *				<b><span style="color:#002288">-- ���I�������� --</span></b>
	 *	
	 * ORDER BY A.SHINSAIN_NO, A.JIGYO_ID, B.UKETUKE_NO
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 *	<b><span style="color:#002288">���I��������</span></b><br />
	 *	������searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>shinsainNo</td><td>AND A.SHINSAIN_NO = '�R�����ԍ�'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�������|���j</td><td>nameKanjiSei</td><td>AND A.SHINSAIN_NAME_KANJI_SEI LIKE '%�����i�������|���j%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�������|���j</td><td>nameKanjiMei</td><td>AND A.SHINSAIN_NAME_KANJI_MEI LIKE '%�����i�������|���j%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i�|���j</td><td>nameKanaSei</td><td>AND A.NAME_KANA_SEI LIKE '%�����i�t���K�i�|���j%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i�|���j</td><td>nameKanaMei</td><td>AND A.NAME_KANA_MEI LIKE '%�����i�t���K�i�|���j%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�N�x</td><td>nendo</td><td>AND B.NENDO = '�N�x'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��</td><td>kaisu</td><td>AND B.KAISU = '��'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>values</td><td>AND SUBSTR(A.JIGYO_ID,3,5) IN ('���ƃR�[�h1,���ƃR�[�h2�c')</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>shozokuCd</td><td>AND B.SHOZOKU_CD = '�����@�փR�[�h'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�S�����Ƌ敪</td><td>-</td><td>AND B.JIGYO_KUBUN IN (1,4)</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�n���̋敪</td><td>keiName</td><td>AND (B.KEI_NAME LIKE '%�n���̋敪%' OR KEI_NAME_RYAKU LIKE '%�n���̋敪%')</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R����</td><td>shinsaJokyo</td><td>AND A.SHINSA_JOKYO = '�R����'</td></tr>
	 * 	</table>
	 *	<div style="font-size:8pt">���S�����Ƌ敪�̓��[�U���Ɩ��S���҂̏ꍇ�A�S�����鎖�Ƌ敪�ɂ����IN��̒l���ς��B���Ƌ敪�̒�`�͈ȉ��̒ʂ�B<br/>
	 *	1:�w�p�n���i���E���j�@4:��Ռ���</div><br />
	 * 
	 * 2.�����E�������ʕԋp<br />
	 * 1.�ō쐬����SQL�����s���������ʂ�List�Ɋi�[����B�������ʂ�List�ɂ͏������R�[�h�̏����i�[����List���i�[����Ă���B
	 * �Ȃ��A�������ʂ�List�̈�ڂɂ́AResultSetMetaData����擾�����e�[�u���񖼂��i�[���Ă���B<br/>
	 * ���ׂẴ��R�[�h�̊i�[�������I������i�K�ŁA�Ăяo�����֕ԋp����B
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return CSV�o�͗pList 
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoSearchInfo)
	 */
	private List searchJokyoCsvData(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {
	
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		//2005.10.04 iso �\��������A�����@�ց����������@�ցA�R�[�h���ԍ��A�\�����������v�撲��
		String select = "SELECT "
//2006/11/06�@�c�@�C����������            
//						+ " C.SHINSAIN_NO \"�R�����ԍ�\""
                        + " C.SHINSAIN_NO \"�R�������U�ԍ�\""
//2006/11/06�@�c�@�C�������܂�                        
						+ ", C.NAME_KANJI_SEI \"�����i�������|���j\""
						+ ", C.NAME_KANJI_MEI \"�����i�������|���j\""
						+ ", C.NAME_KANA_SEI \"�����i�t���K�i�|���j\""
						+ ", C.NAME_KANA_MEI \"�����i�t���K�i�|���j\""
						+ ", C.SHOZOKU_CD \"���������@�֖��i�ԍ��j\""
						+ ", C.SHOZOKU_NAME \"���������@�֖��i�a���j\""
//						+ ", C.BUKYOKU_CD \"���ǖ��i�R�[�h�j\""
						+ ", C.BUKYOKU_NAME \"���ǖ��i�a���j\""
//						+ ", C.SHOKUSHU_CD \"�E��R�[�h\""
						+ ", C.SHOKUSHU_NAME \"�E�햼��\""
//						+ ", C.SHINSA_KAHI \"�R���s��\""
						+ ", C.SOFU_ZIP \"���t��i�X�֔ԍ��j\""
						+ ", C.SOFU_ZIPADDRESS \"���t��i�Z���j\""
						+ ", C.SOFU_ZIPEMAIL \"���t��iEmail�j\""
//						+ ", C.SOFU_ZIPEMAIL2 \"���t��iEmail2�j\""
						+ ", C.SHOZOKU_TEL \"�d�b�ԍ�\""
//						+ ", C.SHOZOKU_FAX \"FAX�ԍ�\""
//						+ ", C.JITAKU_TEL \"����d�b�ԍ�\""
//						+ ", C.SINKI_KEIZOKU_FLG \"�V�K�E�p��\""
//						+ ", C.KIZOKU_START \"�Ϗ��J�n��\""
//						+ ", C.KIZOKU_END \"�Ϗ��I����\""
//						+ ", C.SHAKIN \"�Ӌ�\""
						+ ", C.URL \"URL\""
//						+ ", C.LEVEL_A1 \"���ȍזڃR�[�h(A)\""
//						+ ", C.LEVEL_B1_1 \"���ȍזڃR�[�h(B1-1)\""
//						+ ", C.LEVEL_B1_2 \"���ȍזڃR�[�h(B1-2)\""
//						+ ", C.LEVEL_B1_3 \"���ȍזڃR�[�h(B1-3)\""
//						+ ", C.LEVEL_B2_1 \"���ȍזڃR�[�h(B2-1)\""
//						+ ", C.LEVEL_B2_2 \"���ȍזڃR�[�h(B2-2)\""
//						+ ", C.LEVEL_B2_3 \"���ȍזڃR�[�h(B2-3)\""
//						+ ", C.KEY1 \"��啪��̃L�[���[�h�i1�j\""
//						+ ", C.KEY2 \"��啪��̃L�[���[�h�i2�j\""
//						+ ", C.KEY3 \"��啪��̃L�[���[�h�i3�j\""
//						+ ", C.KEY4 \"��啪��̃L�[���[�h�i4�j\""
//						+ ", C.KEY5 \"��啪��̃L�[���[�h�i5�j\""
//						+ ", C.KEY6 \"��啪��̃L�[���[�h�i6�j\""
//						+ ", C.KEY7 \"��啪��̃L�[���[�h�i7�j\""
						+ ", C.SENMON \"��啪��\""
						+ ", TO_CHAR(C.KOSHIN_DATE, 'YYYY/MM/DD') \"�f�[�^�X�V��\""
						+ ", C.BIKO \"���l\""
						+ ", TO_CHAR(D.LOGIN_DATE, 'YYYY/MM/DD') \"�ŏI���O�C�����t\""		//�ŏI���O�C�����ǉ�
						+ ", A.UKETUKE_NO \"����ԍ�\""
						+ ", A.JIGYO_ID \"����ID\""
						+ ", B.SHOZOKU_NAME \"�����@�֖�\""	
						+ ", B.SHOZOKU_CD \"�@�֔ԍ�\""
						+ ", B.BUNKASAIMOKU_CD \"�זڔԍ�\""
						+ ", B.BUNKATSU_NO \"�����ԍ�\""
						+ ",NVL(SUBSTR(B.UKETUKE_NO,7,4),'    ') \"�����ԍ�\"" 	
						+ ", A.JIGYO_NAME \"���Ɩ�\""
						+ ", B.JURI_SEIRI_NO \"�����ԍ��i�w�n�p�j\""
						+ ", B.KADAI_NAME_KANJI \"�����ۑ薼�i�a���j\""
						+ ", B.NAME_KANJI_SEI \"����Ҏ����i������-���j \""
						+ ", B.NAME_KANJI_MEI \"����Ҏ����i������-���j \""
						+ ", A.KEKKA_ABC \"�R������(ABC)\""
						+ ", A.KEKKA_TEN \"�R������(�_��)\""
//2006/11/06 �c�@�ǉ���������
                        + ", REPLACE(REPLACE(NVL(A.RIGAI, '0'), '0', '���Q�֌W�Ȃ�'), '1', '���Q�֌W����') \"���Q�֌W\""
                        + ", A.COMMENTS \"�R���ӌ�\""
                        + ", A.OTHER_COMMENT \"�R�����g\""
//2006/11/06�@�c�@�ǉ������܂�
//						+ ", A.SENMON_CHK \"���̈�`�F�b�N\""
						+ ", A.COMMENT1 \"�R�����g1\""
						+ ", A.COMMENT2 \"�R�����g2\""
						+ ", A.COMMENT3 \"�R�����g3\""
						+ ", A.COMMENT4 \"�R�����g4\""
						+ ", A.COMMENT5 \"�R�����g5\""
						+ ", A.COMMENT6 \"�R�����g6\""
//						+ ", A.KENKYUNAIYO \"�������e\""
//						+ ", A.KENKYUKEIKAKU \"�����v��\""
//						+ ", A.TEKISETSU_KAIGAI \"�K�ؐ�-�C�O���撲����G�茤��\""
//						+ ", A.TEKISETSU_KENKYU1 \"�K�ؐ�-���S��\""
//						+ ", A.TEKISETSU \"�K�ؐ�\""
//						+ ", A.DATO \"�����o��̑Ó���\""
//						+ ", A.SHINSEISHA \"�֌W�ғ�\""
//						+ ", A.SHINSEISHA \"������\��\""
//						+ ", A.KENKYUBUNTANSHA \"�������S��\""
//						+ ", A.HITOGENOMU \"�q�g�Q�m��\""
//						+ ", A.TOKUTEI \"������\""
//						+ ", A.HITOES \"�q�gES�זE\""
//						+ ", A.KUMIKAE \"��`�q�g��������\""
//						+ ", A.CHIRYO \"��`�q���×Տ�����\""
//						+ ", A.EKIGAKU \"�u�w����\""
//						+ ", A.COMMENTS \"�R�����g\""
						+ ", REPLACE(REPLACE(A.SHINSA_JOKYO, '0', '������'), '1', '����') \"�R����\""
						//2007/5/8 �ǉ�
						+ ", REPLACE(REPLACE(NVL(A.NYURYOKU_JOKYO,'0'), '0', '������'), '1', '����') \"���Q�֌W���͊�����\""
						//2007/5/8 �ǉ�����
						+ " FROM SHINSAKEKKA A INNER JOIN SHINSEIDATAKANRI B ON A.SYSTEM_NO = B.SYSTEM_NO" +
								//" INNER JOIN MASTER_SHINSAIN C ON A.SHINSAIN_NO = C.SHINSAIN_NO AND A.JIGYO_KUBUN = C.JIGYO_KUBUN " +
								" INNER JOIN MASTER_SHINSAIN C ON A.SHINSAIN_NO = C.SHINSAIN_NO AND "+
//2006/05/10 �ǉ���������
//								" ((A.JIGYO_KUBUN = C.JIGYO_KUBUN) OR (A.JIGYO_KUBUN = 6 AND C.JIGYO_KUBUN = 4)) " +
                                " ((A.JIGYO_KUBUN = C.JIGYO_KUBUN) OR (" 
                                + " A.JIGYO_KUBUN IN ("
                                + StringUtil.changeArray2CSV(JigyoKubunFilter.KIBAN_JIGYO_KUBUN, true) + ")" 
                                + " AND C.JIGYO_KUBUN = '" + 
                                IJigyoKubun.JIGYO_KUBUN_KIBAN + "'))" +
//�c�@�ǉ������܂�                                
								" INNER JOIN JIGYOKANRI E ON A.JIGYO_ID = E.JIGYO_ID" +
								" INNER JOIN SHINSAININFO D ON SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO" 					
						+ " WHERE"
						+ " B.DEL_FLG = 0";
						

		//2005.11.03 iso ����������ʃ��\�b�h�ɓƗ�
//		StringBuffer query = new StringBuffer(select);
//
//		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){					//�R�����ԍ�
//			query.append(" AND A.SHINSAIN_NO = '" + searchInfo.getShinsainNo() + "' ");
//		}
//		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){				//�R���������i����-���j
//			query.append(" AND A.SHINSAIN_NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
//		}
//		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){				//�R���������i����-���j
//			query.append(" AND A.SHINSAIN_NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
//		}
//		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){				//�R���������i�t���K�i-���j
//			query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
//		}
//		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){				//�R���������i�t���K�i-���j
//			query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
//		}
////		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){				//�R���������@�֖�
////			query.append(" AND A.SHOZOKU_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");
////		}
////		if(searchInfo.getJigyoName() != null && !searchInfo.getJigyoName().equals("")){					//���Ɩ��i���ƃR�[�h�j
////			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '" + searchInfo.getJigyoName() + "' ");
////		}
//		if(searchInfo.getNendo() != null && !searchInfo.getNendo().equals("")){							//�N�x
//			query.append(" AND B.NENDO = " + searchInfo.getNendo());
//		}
//		if(searchInfo.getKaisu() != null && !searchInfo.getKaisu().equals("")){							//��
//			query.append(" AND  B.KAISU = " + searchInfo.getKaisu());
//		}
//		if(searchInfo.getValues() != null && searchInfo.getValues().size() != 0){							//���Ɩ��i���ƃR�[�h�j
//			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
//			int i;
//			for(i = 0; i < searchInfo.getValues().size()-1; i++) {
//				query.append("'" + searchInfo.getValues().get(i) + "', ");
//			}
//			query.append("'" + searchInfo.getValues().get(i) + "')");
//		}
//		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){					//�R���������@�փR�[�h
//			query.append(" AND B.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
//		}
//		//�Ɩ��S���҂̒S�����Ƌ敪���擾
//		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);
//		if(shinsaTaishoSet != null && shinsaTaishoSet.size() != 0){
//			query.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(), false))
//				 .append(")");
//		}
////		Set set = userInfo.getGyomutantoInfo().getTantoJigyoKubun();
////		if(set != null) {
////			String comma = "";
////			query.append(" AND B.JIGYO_KUBUN IN (");
////			//�w�p�n���i�����j���܂܂�Ă����
////			if(set.contains(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
////				query.append("'" + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO + "'");
////				comma = ", ";
////			}
////			//��Ռ������܂܂�Ă����
////			if(set.contains(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
////				query.append(comma + "'" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'");
////			}
////			query.append(")");
////		}
//		//�n���̋敪�Ɨ��̗̂�������������
//		if(searchInfo.getKeiName() != null && !searchInfo.getKeiName().equals("")){						//�n���̋敪
//			query.append(" AND (B.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
//				+ "%' OR KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
//		}
//		if(searchInfo.getShinsaJokyo() != null && !searchInfo.getShinsaJokyo().equals("")
//				&& !searchInfo.getShinsaJokyo().equals("2")){												//�R����
//			query.append(" AND A.SHINSA_JOKYO = '" + EscapeUtil.toSqlString(searchInfo.getShinsaJokyo()) + "'");
//		}
//		
//		//�ŏI���O�C������ǉ�		2005/10/25�ǉ� 
//		if (searchInfo.getLoginDate() != null && searchInfo.getLoginDate().length() != 0){
//			if("1".equals(searchInfo.getLoginDate()) ){
//				query.append(" AND E.LOGIN_DATE IS NOT NULL");
//			}else{
//				query.append(" AND E.LOGIN_DATE IS NULL ");
//			}	 
//		}
//		
//		//���Q�֌W�҂�ǉ�			2005/10/26�ǉ�
//		if(searchInfo.getRigaiKankeisha() != null && searchInfo.getRigaiKankeisha().length() != 0){
//			if("1".equals(searchInfo.getRigaiKankeisha()) ){
//				query.append(" AND A.RIGAI = '" + searchInfo.getRigaiKankeisha() +"'");
//			}else{
//				query.append(" AND ( A.RIGAI IS NULL OR A.RIGAI = '" + searchInfo.getRigaiKankeisha() + "' ) ");
//			}
//		}
//
//		//�����ԍ���ǉ�	2005/11/2
//		if(searchInfo.getSeiriNo() != null && !searchInfo.getSeiriNo().equals("")){				//�R���������i�t���K�i-���j
//			query.append(" AND B.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//				
//		//�\�[�g���i�R�����ԍ��A����ID�A�\���ԍ��̏����j
//		query.append(" ORDER BY A.SHINSAIN_NO, A.JIGYO_ID, B.UKETUKE_NO ");
		String query = getQueryString(select, userInfo, searchInfo, true);

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
	 * 
	 * �\����������A�R���󋵈ꗗ�E�R�������ꗗ�𔻒f����B
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return �������ʂ��i�[����Page�I�u�W�F�N�g
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#search(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoSearchInfo)
	 */
	public Page search(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {
		Page page = null;
		if(!StringUtil.isBlank(searchInfo.getHyojiHoshikiShinsaJokyo())) {
			if("1".equals(searchInfo.getHyojiHoshikiShinsaJokyo())) {
				//�R���󋵈ꗗ
				page =  searchJokyo(userInfo, searchInfo);
			} else if("2".equals(searchInfo.getHyojiHoshikiShinsaJokyo())) {
				//�R�������ꗗ
				page = searchKensu(userInfo, searchInfo);
			}
		} else {
			page =  searchJokyo(userInfo, searchInfo);		//NULL�̎��u���Z�b�g�v�u�ĐR���v���ł��Ȃ����߁A���}���u�@2005/11/14�n��
				
//			throw new ApplicationException(
//				"�����������s���ł��B",
//				new ErrorInfo("errors.4004"));
			
		}
		return page;
	}

	/**
	 * SQL���̍쐬�E���s.<br /><br />
	 * 
	 * 1.SQL���̍쐬<br /><br />
	 * �܂��A�ȉ���SQL�����쐬����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *  SELECT
	 * 		B.NENDO
	 * 		, B.KAISU
	 * 		, B.JIGYO_NAME
	 * 		, A.SYSTEM_NO
	 * 		, A.SHINSAIN_NO
	 * 		, A.SHINSAIN_NAME_KANJI_SEI
	 * 		, A.SHINSAIN_NAME_KANJI_MEI
	 * 		, B.NAME_KANJI_SEI
	 * 		, B.NAME_KANJI_MEI
	 * 		, B.KADAI_NAME_KANJI
	 *		, A.KEKKA_ABC
	 *		, A.KEKKA_TEN
	 *		, C.SHINSAKIGEN
	 *		, A.SHINSA_JOKYO
	 *	 	, B.JIGYO_KUBUN
	 * 		, B.JOKYO_ID
	 * 		, A.JIGYO_ID
	 * 	FROM
	 * 		SHINSAKEKKA A
	 * 		, SHINSEIDATAKANRI B
	 * 		, JIGYOKANRI C
	 * 	WHERE
	 * 		A.SYSTEM_NO = B.SYSTEM_NO
	 * 		AND A.JIGYO_ID = C.JIGYO_ID
	 * 		AND A.SHINSAIN_NO NOT LIKE '@%'
	 * 		AND B.DEL_FLG = 0 </pre>
	 *	
	 *				<b><span style="color:#002288">-- ���I�������� --</span></b>
	 *	
	 *	ORDER BY A.SHINSAIN_NO, A.JIGYO_ID, B.UKETUKE_NO
	 * </td></tr>
	 * </table><br />
	 * 
	 *	<b><span style="color:#002288">���I��������</span></b><br />
	 *	������searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>shinsainNo</td><td>AND A.SHINSAIN_NO = '�R�����ԍ�'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�������|���j</td><td>nameKanjiSei</td><td>AND A.SHINSAIN_NAME_KANJI_SEI LIKE '%�����i�������|���j%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�������|���j</td><td>nameKanjiMei</td><td>AND A.SHINSAIN_NAME_KANJI_MEI LIKE '%�����i�������|���j%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i�|���j</td><td>nameKanaSei</td><td>AND A.NAME_KANA_SEI LIKE '%�����i�t���K�i�|���j%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����i�t���K�i�|���j</td><td>nameKanaMei</td><td>AND A.NAME_KANA_MEI LIKE '%�����i�t���K�i�|���j%'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�N�x</td><td>nendo</td><td>AND B.NENDO = '�N�x'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��</td><td>kaisu</td><td>AND B.KAISU = '��'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>values</td><td>AND SUBSTR(A.JIGYO_ID,3,5) IN ('���ƃR�[�h1,���ƃR�[�h2�c')</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>shozokuCd</td><td>AND B.SHOZOKU_CD = '�����@�փR�[�h'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�S�����Ƌ敪</td><td>-</td><td>AND B.JIGYO_KUBUN IN (1,4)</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�n���̋敪</td><td>keiName</td><td>AND (B.KEI_NAME LIKE '%�n���̋敪%' OR KEI_NAME_RYAKU LIKE '%�n���̋敪%')</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R����</td><td>shinsaJokyo</td><td>AND A.SHINSA_JOKYO = '�R����'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID(�ĐR���p)</td><td>jigyoId</td><td>AND A.JIGYO_ID = '����ID(�ĐR���p)'</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪(�ĐR���p)</td><td>jigyoKubun</td><td>AND A.JIGYO_KUBUN = '���Ƌ敪(�ĐR���p)'</td></tr>
	 * 	</table>
	 *	<div style="font-size:8pt">���S�����Ƌ敪�̓��[�U���Ɩ��S���҂̏ꍇ�A�S�����鎖�Ƌ敪�ɂ����IN��̒l���ς��B���Ƌ敪�̒�`�͈ȉ��̒ʂ�B<br/>
	 *	1:�w�p�n���i���E���j�@4:��Ռ���</div><br />
	 * 
	 * 2.Page�̍쐬�E�ԋp<br /><br />
	 * �������ʂ�Page�I�u�W�F�N�g�փZ�b�g���Ăяo�����֕ԋp����B�Ȃ��APage�I�u�W�F�N�g�ɃZ�b�g�������́A���ׂĂ̌������ʂ��i�[����List�ŁA
	 * �e�v�f�ɂ�1���R�[�h���̏����A�񖼂��L�[�ɒl���Z�b�g����Map���i�[����Ă���B
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return �������ʂ��i�[����Page�I�u�W�F�N�g
	 */
	public Page searchJokyo(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {

		
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------

		String select = "SELECT"
							+ " B.NENDO"
							+ ", B.KAISU"
							+ ", B.JIGYO_NAME"
							+ ", A.SYSTEM_NO"
							+ ", A.SHINSAIN_NO"
							+ ", A.SHINSAIN_NAME_KANJI_SEI"
							+ ", A.SHINSAIN_NAME_KANJI_MEI"
//							+ ", A.SHOZOKU_NAME"
							+ ", B.NAME_KANJI_SEI"
							+ ", B.NAME_KANJI_MEI"
							+ ", B.KADAI_NAME_KANJI"
							+ ", A.KEKKA_ABC"
							+ ", A.KEKKA_TEN"
							+ ", A.RIGAI"
//							+ ", A.SENMON_CHK"
							+ ", C.SHINSAKIGEN"
//							+ ", C.SHINSA_TYPE"
							+ ", A.SHINSA_JOKYO"
							+ ", A.NYURYOKU_JOKYO"  // 2006/10/24 ���L��
							+ ", B.JIGYO_KUBUN"
							+ ", B.JOKYO_ID"
							+ ", A.JIGYO_ID"
							+ ", D.LOGIN_DATE"
							+ ", B.JURI_SEIRI_NO"
						+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, JIGYOKANRI C, SHINSAININFO D"
						+ " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
						+ " AND A.JIGYO_ID = C.JIGYO_ID"
						+ " AND SUBSTR(D.SHINSAIN_ID,4,7)  = A.SHINSAIN_NO "
						+ " AND A.SHINSAIN_NO NOT LIKE '@%'"
						+ " AND B.DEL_FLG = 0";
	  	
		//2005.11.03 iso ����������ʃ��\�b�h�ɓƗ�
//		StringBuffer query = new StringBuffer(select);
//
//		if(searchInfo.getShinsainNo() != null && !searchInfo.getShinsainNo().equals("")){					//�R�����ԍ�
//			query.append(" AND A.SHINSAIN_NO = '" + searchInfo.getShinsainNo() + "' ");
//		}
//		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){				//�R���������i����-���j
//			query.append(" AND A.SHINSAIN_NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
//		}
//		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){				//�R���������i����-���j
//			query.append(" AND A.SHINSAIN_NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
//		}
//		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){				//�R���������i�t���K�i-���j
//			query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
//		}
//		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){				//�R���������i�t���K�i-���j
//			query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
//		}
////		if(searchInfo.getShozokuName() != null && !searchInfo.getShozokuName().equals("")){				//�R���������@�֖�
////			query.append(" AND A.SHOZOKU_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");
////		}
////		if(searchInfo.getJigyoName() != null && !searchInfo.getJigyoName().equals("")){					//���Ɩ��i���ƃR�[�h�j
////			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) = '" + searchInfo.getJigyoName() + "' ");
////		}
//		if(searchInfo.getNendo() != null && !searchInfo.getNendo().equals("")){							//�N�x
//			query.append(" AND B.NENDO = " + searchInfo.getNendo());
//		}
//		if(searchInfo.getKaisu() != null && !searchInfo.getKaisu().equals("")){							//��
//			query.append(" AND  B.KAISU = " + searchInfo.getKaisu());
//		}
//		if(searchInfo.getValues() != null && searchInfo.getValues().size() != 0){							//���Ɩ��i���ƃR�[�h�j
//			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (");
//			int i;
//			for(i = 0; i < searchInfo.getValues().size()-1; i++) {
//				query.append("'" + searchInfo.getValues().get(i) + "', ");
//			}
//			query.append("'" + searchInfo.getValues().get(i) + "')");
//		}
//		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){					//�R���������@�փR�[�h
//			query.append(" AND B.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
//		}
//		//�Ɩ��S���҂̒S�����Ƌ敪���擾
//		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);
//		if(shinsaTaishoSet != null && shinsaTaishoSet.size() != 0){
//			query.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(), false))
//				 .append(")");
//		}
////		Set set = userInfo.getGyomutantoInfo().getTantoJigyoKubun();
////		if(set != null) {
////			String comma = "";
////			query.append(" AND B.JIGYO_KUBUN IN (");
////			//�w�p�n���i�����j���܂܂�Ă����
////			if(set.contains(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
////				query.append("'" + IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO + "'");
////				comma = ", ";
////			}
////			//��Ռ������܂܂�Ă����
////			if(set.contains(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
////				query.append(comma + "'" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'");
////			}
////			query.append(")");
////		}
//		//�n���̋敪�Ɨ��̗̂�������������
//		if(searchInfo.getKeiName() != null && !searchInfo.getKeiName().equals("")){						//�n���̋敪
//			query.append(" AND (B.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
//				+ "%' OR KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
//		}
//		if(searchInfo.getShinsaJokyo() != null && !searchInfo.getShinsaJokyo().equals("")
//				&& !searchInfo.getShinsaJokyo().equals("2")){												//�R����
//			query.append(" AND A.SHINSA_JOKYO = '" + EscapeUtil.toSqlString(searchInfo.getShinsaJokyo()) + "'");
//		}	
//		
////�ŏI���O�C������ǉ�		2005/10/25�ǉ� 
//		if (searchInfo.getLoginDate() != null && searchInfo.getLoginDate().length() != 0){
//			if("1".equals(searchInfo.getLoginDate()) ){
//				query.append(" AND D.LOGIN_DATE IS NOT NULL");
//			}else{
//				query.append(" AND D.LOGIN_DATE IS NULL ");
//			}	 
//		}
//
////���Q�֌W�҂�ǉ�			2005/10/25�ǉ�
//		if(searchInfo.getRigaiKankeisha() != null && searchInfo.getRigaiKankeisha().length() != 0){
//			if("1".equals(searchInfo.getRigaiKankeisha()) ){
//				query.append(" AND A.RIGAI = '" + searchInfo.getRigaiKankeisha() + "'");
//			}else{
//				query.append(" AND ( A.RIGAI IS NULL OR A.RIGAI = '" + searchInfo.getRigaiKankeisha() + "' ) ");
//			}
//		}
////�����ԍ���ǉ�	2005/11/2
//		if(searchInfo.getSeiriNo() != null && !searchInfo.getSeiriNo().equals("")){				//�R���������i�t���K�i-���j
//			query.append(" AND B.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//				  			
//		if(searchInfo.getJigyoId() != null && !searchInfo.getJigyoId().equals("")){						//����ID(�ĐR���p)
//			query.append(" AND A.JIGYO_ID = " + searchInfo.getJigyoId());
//		}
//		if(searchInfo.getJigyoKubun() != null && !searchInfo.getJigyoKubun().equals("")){					//���Ƌ敪(�ĐR���p)
//			query.append(" AND A.JIGYO_KUBUN = " + searchInfo.getJigyoKubun());
//		}
//
//		//�\�[�g���i�R�����ԍ��A����ID�A�\���ԍ��̏����j
//		query.append(" ORDER BY A.SHINSAIN_NO, A.JIGYO_ID, B.UKETUKE_NO ");
		String query = getQueryString(select, userInfo, searchInfo, true);
		
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
				"�R���󋵃f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * �R�������ꗗ��Ԃ��B
	 * �ꗗ�\���ŁA�N�x�E�񐔂��l�����Ă��Ȃ��̂ŁA
	 * �N�x�E�񐔂��قȂ��Ă��A�������ƂȂ�܂Ƃ߂ăJ�E���g����B
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return �������ʂ��i�[����Page�I�u�W�F�N�g
	 */
	public Page searchKensu(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {

		Page result = null;
		int totalSize = 0;
		
		//�S�̂̌������ʂł̓y�[�W���䂪�s�\�Ȃ̂ŁA
		//�R�����ʂ���_�~�[���������R�����ԍ���ORDER���Ď擾���A
		//�X�^�[�g�|�W�V��������y�[�W�T�C�Y���擾����B
		int startPosition = searchInfo.getStartPosition();
		int pageSize = searchInfo.getPageSize();
		
		//�y�[�W����͐R�����ԍ��ōs�����߁A�S�̂ł͖����ɂ��Ă����B
		searchInfo.setStartPosition(0);
		searchInfo.setPageSize(0);
		
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		
		//�ΏېR����No����������SQL����
		String selectShinsainNo = "SELECT DISTINCT SHINSAIN_NO"
								+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, SHINSAININFO D"
								+ " WHERE"
								+ " A.SYSTEM_NO = B.SYSTEM_NO"
								+ " AND SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO"
								+ " AND A.SHINSAIN_NO NOT LIKE '@%'"
								+ " AND B.DEL_FLG = 0"
								//��茤���i�X�^�[�g�A�b�v�j��ǉ� 2006/4/3
								//+ " AND A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//2006/05/09 �ǉ���������                                 
//								+ " AND (A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//								+ " OR A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART + "'"
//                                + " OR A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI + "')"
                                + " AND A.JIGYO_KUBUN IN ("
                                + StringUtil.changeIterator2CSV(JigyoKubunFilter.Convert2ShinseishoJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(),true)
                                + ")"
//�c�@�ǉ������܂�                                
                                //�ǉ�����
								;
		String queryShinsainNo = getQueryString(selectShinsainNo, userInfo, searchInfo, false);

		//�ΏېR����No���J�E���g����SQL
		String countShinsainNo = "SELECT COUNT(SHINSAIN_NO) FROM ("
								+ queryShinsainNo
								+ ")"
								;

		//�y�[�W�f�[�^����SQL
		//�Ώۂ���Ղ݂̂Ȃ̂ŁA���́E�����͔���́A�u���Q�t���O�v�u�R������(�_��)�v�ōs���Ă���B
		String select = "SELECT"
						+ " SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD"
						+ ", A.JIGYO_KUBUN"						//2005.11.15 iso �R�������\���̂��ߎ��Ƌ敪��ǉ�
						+ ", A.SHINSAIN_NO"
//2006/11/02 �c�@�C����������                        
//						+ ", A.SHINSAIN_NAME_KANJI_SEI"
//						+ ", A.SHINSAIN_NAME_KANJI_MEI"
                        + ", E.NAME_KANJI_SEI"
                        + ", E.NAME_KANJI_MEI"
//2006/11/02�@�c�@�C�������܂�                        
						+ ", D.LOGIN_DATE"
						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) ALL_COUNT"
//2007/5/8				+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) - SUM(DECODE((A.KEKKA_TEN || A.RIGAI), NULL, 0, '', 0, 1)) NOT_INPUT_COUNT"
						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) - SUM(DECODE((A.KEKKA_TEN || A.RIGAI), NULL, 0, '', 0, '0', 0, 1)) NOT_INPUT_COUNT"
					+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, SHINSAININFO D"
//2006/11/02 �c�@�ǉ���������
                    + " , MASTER_SHINSAIN E"
//2006/11/02�@�c�@�ǉ������܂�                    
					+ " WHERE"
					+ " A.SYSTEM_NO = B.SYSTEM_NO"
					+ " AND SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO"
					//��������y�[�W����
//2006/11/02 �c�@�C����������                       
//					+ " AND SHINSAIN_NO IN"
                    + " AND A.SHINSAIN_NO IN"
//2006/11/02�@�c�@�C�������܂�                     
					+ " (SELECT SHINSAIN_NO FROM "
					+ "  (SELECT SHINSAIN_NO, ROWNUM ROW_COUNT FROM"
					+ "   (" + queryShinsainNo + " ORDER BY SHINSAIN_NO)"
					+ "  )"
					+ "  WHERE ROW_COUNT BETWEEN " + (startPosition + 1)
					+ "  AND " + (startPosition + pageSize)
					+ " )"
					//�����܂Ńy�[�W����
					+ " AND B.DEL_FLG = 0"
//2006/11/02 �c�@�ǉ��������� 
                    + " AND A.SHINSAIN_NO = E.SHINSAIN_NO"
                    + " AND E.JIGYO_KUBUN = "
                    + IJigyoKubun.JIGYO_KUBUN_KIBAN
//2006/11/02�@�c�@�ǉ������܂�                     
					;
		
		//1�q�b�g������S���Ƃ��o���̂ŁA�����������R�����g��
//		String query = getHyojiQueryString(slect, userInfo, searchInfo);
        
//      2006/10/26  �����\���w��̌������ �� �ǉ� ���L�� 
        select = getQueryString(select, userInfo, searchInfo, false);
		
		String query = select
				//2005.11.15 iso �R�������\���̂��ߎ��Ƌ敪��ǉ�
//				+ " GROUP BY SUBSTR(A.JIGYO_ID,3,5), A.SHINSAIN_NO, A.SHINSAIN_NAME_KANJI_SEI, A.SHINSAIN_NAME_KANJI_MEI, D.LOGIN_DATE"
//2006/11/02 �c�@�C����������        
//				+ " GROUP BY SUBSTR(A.JIGYO_ID,3,5), A.JIGYO_KUBUN, A.SHINSAIN_NO, A.SHINSAIN_NAME_KANJI_SEI, A.SHINSAIN_NAME_KANJI_MEI, D.LOGIN_DATE"
		        + " GROUP BY SUBSTR(A.JIGYO_ID,3,5), A.JIGYO_KUBUN, A.SHINSAIN_NO, E.NAME_KANJI_SEI, E.NAME_KANJI_MEI, D.LOGIN_DATE"
//2006/11/02�@�c�@�C�������܂�
				+ " ORDER BY A.SHINSAIN_NO, JIGYO_CD"
				;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//-----------------------
		// �y�[�W�擾
		//-----------------------
		Connection connection = null;
		ShinsaKekkaInfoDao shinsaKekkaDao = new ShinsaKekkaInfoDao(userInfo);
		try {
			connection = DatabaseUtil.getConnection();
			
			totalSize = shinsaKekkaDao.countTotalPage(connection, countShinsainNo);
			if(totalSize < 1) {
				throw new NoDataFoundException("�Y���f�[�^������܂���ł����B", new ErrorInfo("errors.5002"));
			}
			
			result = SelectUtil.selectPageInfo(connection,searchInfo, query);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R���󋵃f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		HashMap newMap = new HashMap();					//�V�������R�������Ƃ̃��X�g(�VList1�s��)
		ArrayList newList = new ArrayList();			//�y�[�W���Ƃ��ďo�͂��郊�X�g
		String beforeShinsainNo = "";
		int allCount = 0;									//���ׂĂ̑S��
		int notInputCount = 0;								//���ׂĂ̖�����
		int i = 0;
		for(Iterator ite = result.getList().iterator(); ite.hasNext(); i++) {
			Map recordMap = (Map)ite.next();
			String shinsainNo = recordMap.get("SHINSAIN_NO").toString();

			//1�O�̐R�����ԍ��ƈ�v�����ꍇ�A1�s���̃f�[�^�Ȃ̂�put����B
			if(shinsainNo.equals(beforeShinsainNo)) {
				newMap.put("ALL_COUNT_" + recordMap.get("JIGYO_CD").toString(), recordMap.get("ALL_COUNT"));
				newMap.put("NOT_INPUT_COUNT_" + recordMap.get("JIGYO_CD").toString(), recordMap.get("NOT_INPUT_COUNT"));
				//�ݐσJ�E���g��ǉ�
				allCount += Integer.parseInt(recordMap.get("ALL_COUNT").toString());
				notInputCount += Integer.parseInt(recordMap.get("NOT_INPUT_COUNT").toString());
			} else {
				//1�O�ƈႤ�R�����ԍ��̏ꍇ�A�O�̐R������1�s���f�[�^��VCSV���X�g�ɓo�^
				//�ŏ��͕K����f�[�^�Ȃ̂ŁA�o�^���Ȃ�
				if(i != 0) {
					//���ׂẴJ�E���g���Z�b�g���ď���������
					newMap.put("ALL_COUNT", Integer.toString(allCount));
					newMap.put("NOT_INPUT_COUNT", Integer.toString(notInputCount));
					allCount =0;
					notInputCount = 0;
					newList.add(newMap);
				}
				
				//���R�������̊�{�f�[�^���쐬
				newMap = new HashMap();
				newMap.put("JIGYO_KUBUN", recordMap.get("JIGYO_KUBUN"));		//2005.11.15 iso �R�������\���̂��ߎ��Ƌ敪��ǉ�
				newMap.put("SHINSAIN_NO", shinsainNo);
//2006/11/02 �c�@�C����������                
//				newMap.put("SHINSAIN_NAME_KANJI_SEI", recordMap.get("SHINSAIN_NAME_KANJI_SEI"));
//				newMap.put("SHINSAIN_NAME_KANJI_MEI", recordMap.get("SHINSAIN_NAME_KANJI_MEI"));
                newMap.put("NAME_KANJI_SEI", recordMap.get("NAME_KANJI_SEI"));
                newMap.put("NAME_KANJI_MEI", recordMap.get("NAME_KANJI_MEI"));
//2006/11/02�@�c�@�C�������܂�                
				newMap.put("LOGIN_DATE", recordMap.get("LOGIN_DATE"));
				
				//�e���Ƃ��Ƃ�NAME�ɂ́A���ƃR�[�h��t��
				newMap.put("ALL_COUNT_" + recordMap.get("JIGYO_CD").toString(), recordMap.get("ALL_COUNT"));
				newMap.put("NOT_INPUT_COUNT_" + recordMap.get("JIGYO_CD").toString(), recordMap.get("NOT_INPUT_COUNT"));

				//�ݐσJ�E���g��ǉ�
				allCount += Integer.parseInt(recordMap.get("ALL_COUNT").toString());
				notInputCount += Integer.parseInt(recordMap.get("NOT_INPUT_COUNT").toString());
				
				//���݂̐R�����ԍ������̔�r�Ɏg�����߂ɃZ�b�g
				beforeShinsainNo = shinsainNo;
			}

			//�Ō�̃f�[�^�͏��else�Ɉ���������Ȃ��̂ŁA������csvList�Ɋi�[����B
			if(i == result.getList().size()-1) {
				//���ׂẴJ�E���g���Z�b�g
				newMap.put("ALL_COUNT", Integer.toString(allCount));
				newMap.put("NOT_INPUT_COUNT", Integer.toString(notInputCount));
				newList.add(newMap);							//�o��CSV�ɐR�������Ƃɂ܂Ƃ߂��f�[�^���Z�b�g
			}
		}
		
		return new Page(newList, startPosition, pageSize, totalSize);
	}


	//2005.11.08 iso �ǉ�
	/**
	 * �R�������ꗗ��CSV��Ԃ��B
	 * �ꗗ�\���ŁA�N�x�E�񐔂��l�����Ă��Ȃ��̂ŁA
	 * �N�x�E�񐔂��قȂ��Ă��A�������ƂȂ�܂Ƃ߂ăJ�E���g����B
	 * @param userInfo UserInfo
	 * @param searchInfo ShinsaJokyoSearchInfo
	 * @return �������ʂ��i�[����Page�I�u�W�F�N�g
	 */
	public List searchKensuCsvData(UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo)
			throws ApplicationException {

		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------

		//2005.12.02 iso ����SQL���Ɠ��͂��ꂽ���������݂̂����o�͂���Ȃ��B
		//�R�������q�b�g����΁A�S���Ƃ̌������o���悤�ύX
//		//�y�[�W�f�[�^����SQL
//		//�Ώۂ���Ղ݂̂Ȃ̂ŁA���́E�����͔���́A�u���Q�t���O�v�u�R������(�_��)�v�ōs���Ă���B
//		String slect = "SELECT"
//						+ " C.SHINSAIN_NO"										//�R�����ԍ�
//						+ ", C.NAME_KANJI_SEI"									//�R������(�����|��)	���y�[�W�Ƃ͕ʂ̃e�[�u������擾
//						+ ", C.NAME_KANJI_MEI"									//�R������(�����|��)	���y�[�W�Ƃ͕ʂ̃e�[�u������擾
//						+ ", C.NAME_KANA_SEI"									//�R������(�t���K�i�|��)
//						+ ", C.NAME_KANA_MEI"									//�R������(�t���K�i�|��)
//						+ ", C.SHOZOKU_CD"										//�R���������@�փR�[�h
//						+ ", C.SHOZOKU_NAME"									//�R���������@�֖�
//						+ ", C.BUKYOKU_NAME"									//�R�������ǖ�
//						+ ", C.SHOKUSHU_NAME"									//�R�����E�햼
//						+ ", C.SOFU_ZIP"										//���t��(�X�֔ԍ�)
//						+ ", C.SOFU_ZIPADDRESS"									//���t��(�Z��)
//						+ ", C.SOFU_ZIPEMAIL"									//���t��(Email)
//						+ ", C.SHOZOKU_TEL"										//�d�b�ԍ�
//						+ ", C.URL"												//URL
//						+ ", C.SENMON"											//��啪��
//						+ ", TO_CHAR(C.KOSHIN_DATE , 'YYYY/MM/DD') KOSHIN_DATE"	//�f�[�^�X�V��
//						+ ", C.BIKO"											//���l
//						+ ", TO_CHAR(D.LOGIN_DATE, 'YYYY/MM/DD') LOGIN_DATE"	//���O�C����
//						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) ALL_COUNT"
//						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) - SUM(DECODE((A.KEKKA_TEN || A.RIGAI), NULL, 0, '', 0, 1)) NOT_INPUT_COUNT"
//						+ ", SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD"
//					+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, MASTER_SHINSAIN C, SHINSAININFO D"
//					+ " WHERE"
//					+ " A.SYSTEM_NO = B.SYSTEM_NO"
//					+ " AND SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO"
//					+ " AND C.SHINSAIN_NO = A.SHINSAIN_NO"
//					+ " AND A.SHINSAIN_NO NOT LIKE '@%'"
//					+ " AND B.DEL_FLG = 0"
//					//�w�n�œ����R����NO������Ɣ{�̌����ɂȂ�̂ŁA�O�̂��ߐR�����}�X�^�̋敪�ɂ���B
//					//�y�[�W�����ł́A�R�����ʃe�[�u���Ȃ̂Œ���
//					+ " AND C.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//					;
//
//		String query = getQueryString(slect, userInfo, searchInfo, false);
//				
//		query += " GROUP BY SUBSTR(A.JIGYO_ID,3,5), C.SHINSAIN_NO, C.NAME_KANJI_SEI"
//				+ ", C.NAME_KANA_MEI, C.NAME_KANA_SEI, C.NAME_KANJI_MEI, C.SHOZOKU_CD"
//				+ ", C.SHOZOKU_NAME, C.BUKYOKU_NAME, C.SHOKUSHU_NAME, C.SOFU_ZIP"
//				+ ", C.SOFU_ZIPADDRESS, C.SOFU_ZIPEMAIL, C.SHOZOKU_TEL, C.URL, C.SENMON"
//				+ ", C.KOSHIN_DATE, C.BIKO, D.LOGIN_DATE"
//				+ " ORDER BY C.SHINSAIN_NO, JIGYO_CD"
//				;
		
		//�ΏېR����No����������SQL����
		String selectShinsainNo = "SELECT DISTINCT SHINSAIN_NO"
								+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, SHINSAININFO D"
								+ " WHERE"
								+ " A.SYSTEM_NO = B.SYSTEM_NO"
								+ " AND SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO"
								+ " AND A.SHINSAIN_NO NOT LIKE '@%'"
								+ " AND B.DEL_FLG = 0"
//2006/05/09 �c�@�ǉ���������                                 
//								+ " AND (A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//							    + " AND A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//								+ " OR A.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART + "')"
                                + " AND A.JIGYO_KUBUN IN ("
                                + StringUtil.changeIterator2CSV(JigyoKubunFilter.Convert2ShinseishoJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(),true)
                                + ")"
//2006/05/09�@�c�@�ǉ������܂�                                     

								;
		String queryShinsainNo = getQueryString(selectShinsainNo, userInfo, searchInfo, false);

		//�y�[�W�f�[�^����SQL
		//�Ώۂ���Ղ݂̂Ȃ̂ŁA���́E�����͔���́A�u���Q�t���O�v�u�R������(�_��)�v�ōs���Ă���B
		String select = "SELECT"
						+ " C.SHINSAIN_NO"										//�R�����ԍ�
						+ ", C.NAME_KANJI_SEI"									//�R������(�����|��)	���y�[�W�Ƃ͕ʂ̃e�[�u������擾
						+ ", C.NAME_KANJI_MEI"									//�R������(�����|��)	���y�[�W�Ƃ͕ʂ̃e�[�u������擾
						+ ", C.NAME_KANA_SEI"									//�R������(�t���K�i�|��)
						+ ", C.NAME_KANA_MEI"									//�R������(�t���K�i�|��)
						+ ", C.SHOZOKU_CD"										//�R���������@�փR�[�h
						+ ", C.SHOZOKU_NAME"									//�R���������@�֖�
						+ ", C.BUKYOKU_NAME"									//�R�������ǖ�
						+ ", C.SHOKUSHU_NAME"									//�R�����E�햼
						+ ", C.SOFU_ZIP"										//���t��(�X�֔ԍ�)
						+ ", C.SOFU_ZIPADDRESS"								    //���t��(�Z��)
						+ ", C.SOFU_ZIPEMAIL"									//���t��(Email)
						+ ", C.SHOZOKU_TEL"									    //�d�b�ԍ�
						+ ", C.URL"											    //URL
						+ ", C.SENMON"											//��啪��
						+ ", TO_CHAR(C.KOSHIN_DATE , 'YYYY/MM/DD') KOSHIN_DATE" //�f�[�^�X�V��
						+ ", C.BIKO"											//���l
						+ ", TO_CHAR(D.LOGIN_DATE, 'YYYY/MM/DD') LOGIN_DATE"	//���O�C����
//2006/11/06 �c�@�ǉ���������
                        + ", SUM(NVL(A.SHINSA_JOKYO, 0)) SHINSA_JOKYO"        	//�R����
//2006/11/06�@�c�@�ǉ������܂�                        
                        + ", SUM(NVL(A.NYURYOKU_JOKYO, 0)) NYURYOKU_JOKYO"      //���Q�֌W���͊����� 2007/5/9
						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) ALL_COUNT"
//2007/5/8				+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) - SUM(DECODE((A.KEKKA_TEN || A.RIGAI), NULL, 0, '', 0, 1)) NOT_INPUT_COUNT"
						+ ", COUNT(SUBSTR(A.JIGYO_ID,3,5)) - SUM(DECODE((A.KEKKA_TEN || A.RIGAI), NULL, 0, '', 0, '0', 0, 1)) NOT_INPUT_COUNT"
//2006/11/06 �c�@�ǉ���������
                        + ", SUM(NVL(A.RIGAI,'0'))"                             //���v�������͌���
//2006/11/06�@�c�@�ǉ������܂�                         
						+ ", SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD"
					+ " FROM SHINSAKEKKA A, SHINSEIDATAKANRI B, MASTER_SHINSAIN C, SHINSAININFO D"
					+ " WHERE"
					+ " A.SYSTEM_NO = B.SYSTEM_NO"
					+ " AND SUBSTR(D.SHINSAIN_ID,4,7) = A.SHINSAIN_NO"
					+ " AND C.SHINSAIN_NO = A.SHINSAIN_NO"
					+ " AND B.DEL_FLG = 0"
					//�w�n�œ����R����NO������Ɣ{�̌����ɂȂ�̂ŁA�O�̂��ߐR�����}�X�^�̋敪�ɂ���B
					//�R�����ԍ����������ł́A�R�����ʃe�[�u���Ȃ̂Œ���
//2006/05/09 �c�@�ǉ���������  
//					+ " AND (CC.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//                  + " AND  CC.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_KIBAN + "'"
//					+ " OR CC.JIGYO_KUBUN = '" + IJigyoKubun.JIGYO_KUBUN_WAKATESTART + "')"
                    + " AND C.JIGYO_KUBUN IN ("
                    + StringUtil.changeIterator2CSV(JigyoKubunFilter.Convert2ShinseishoJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN).iterator(),true)
                    + ")"
//2006/05/09�@�c�@�ǉ������܂�   
					+ " AND A.SHINSAIN_NO IN (" + queryShinsainNo + ")"
					;
//2006/11/06 �c�@�ǉ���������
        select = getQueryString(select, userInfo, searchInfo, false);
//2006/11/06�@�c�@�ǉ������܂�        
		select += " GROUP BY SUBSTR(A.JIGYO_ID,3,5), C.SHINSAIN_NO, C.NAME_KANJI_SEI"
				+ ", C.NAME_KANA_MEI, C.NAME_KANA_SEI, C.NAME_KANJI_MEI, C.SHOZOKU_CD"
				+ ", C.SHOZOKU_NAME, C.BUKYOKU_NAME, C.SHOKUSHU_NAME, C.SOFU_ZIP"
				+ ", C.SOFU_ZIPADDRESS, C.SOFU_ZIPEMAIL, C.SHOZOKU_TEL, C.URL, C.SENMON"
//�R�������ꗗ�b�r�u�o�͂Ɏ�ڂ̌������s���̂��ߏC�� 2006/11/15
//				+ ", C.KOSHIN_DATE, C.BIKO, D.LOGIN_DATE, A.SHINSA_JOKYO"
				+ ", C.KOSHIN_DATE, C.BIKO, D.LOGIN_DATE"
				+ " ORDER BY C.SHINSAIN_NO, JIGYO_CD"
				;
		
		if(log.isDebugEnabled()){
			log.debug("query:" + select);
		}
		
		//-----------------------
		// ���X�g�擾
		//-----------------------
		List result = null;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			result = SelectUtil.selectCsvList(connection, select, false);
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"CSV�o�̓f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4008"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		//CSV�J�����̍쐬
//2006/11/06�@�c�@�ǉ���������
        StringBuffer columnName = new StringBuffer();
        columnName.append("�R�������U�ԍ�");
        columnName.append(",����(�������|��)");
        columnName.append(",����(�������|��)");
        columnName.append(",����(�t���K�i�|��)");
        columnName.append(",����(�t���K�i�|��)");
        columnName.append(",���������@�֖�(�ԍ�)");
        columnName.append(",���������@�֖�(�a��)");
        columnName.append(",���ǖ�(�a��)");
        columnName.append(",�E�햼��");
        columnName.append(",���t��(�X�֔ԍ�)");
        columnName.append(",���t��(�Z��)");
        columnName.append(",���t��(Email)");
        columnName.append(",�d�b�ԍ�");
        columnName.append(",URL");
        columnName.append(",��啪��");
        columnName.append(",�f�[�^�X�V��");
        columnName.append(",���l");
        columnName.append(",�ŏI���O�C����");
        columnName.append(",�R����");
        columnName.append(",���Q�֌W���͊�����");	//2007/5/9�ǉ�
        columnName.append(",���v(������)");
        columnName.append(",���v(���ׂ�)");
        if (!searchInfo.getValues().isEmpty()){
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_S)) {
                columnName.append(",��Ռ���(S)(������)");
                columnName.append(",��Ռ���(S)(���ׂ�)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN)) {
                columnName.append(",��Ռ���(A)���(������)");
                columnName.append(",��Ռ���(A)���(���ׂ�)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI)) {
                columnName.append(",��Ռ���(A)�C�O(������)");
                columnName.append(",��Ռ���(A)�C�O(���ׂ�)");
            }
            if ( searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN)) {
                columnName.append(",��Ռ���(B)���(������)");
                columnName.append(",��Ռ���(B)���(���ׂ�)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI)) {
                columnName.append(",��Ռ���(B)�C�O(������)");
                columnName.append(",��Ռ���(B)�C�O(���ׂ�)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN)) {
                columnName.append(",��Ռ���(C)���(������)");
                columnName.append(",��Ռ���(C)���(���ׂ�)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU)) {
                columnName.append(",��Ռ���(C)���(������)");
                columnName.append(",��Ռ���(C)���(���ׂ�)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_HOGA)) {
                columnName.append(",�G�茤��(������)");
                columnName.append(",�G�茤��(���ׂ�)");
            }
            //2007/5/9���S�ǉ�
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S)) {
                columnName.append(",��茤��(S)(������)");
                columnName.append(",��茤��(S)(���ׂ�)");
            }            
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A)) {
                columnName.append(",��茤��(A)(������)");
                columnName.append(",��茤��(A)(���ׂ�)");
            }            
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B)) {
                columnName.append(",��茤��(B)(������)");
                columnName.append(",��茤��(B)(���ׂ�)");
            }
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_WAKATESTART)) {
                columnName.append(",��茤���i�X�^�[�g�A�b�v�j(������)");
                columnName.append(",��茤���i�X�^�[�g�A�b�v�j(���ׂ�)");
            }
//2007/5/18 �C��
//            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A)) {
//                columnName.append(",���ʌ������i��(��Ռ���(A)����)�i�����́j");
//                columnName.append(",���ʌ������i��(��Ռ���(A)����)�i���ׂāj");
//            }
//            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B)) {
//                columnName.append(",���ʌ������i��(��Ռ���(B)����)�i�����́j");
//                columnName.append(",���ʌ������i��(��Ռ���(B)����)�i���ׂāj");
//            }
//2007/5/18 �C������
            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C)) {
//                columnName.append(",���ʌ������i��(��Ռ���(C)����)�i�����́j");
//                columnName.append(",���ʌ������i��(��Ռ���(C)����)�i���ׂāj");
                columnName.append(",���ʌ������i��(�N������̎��s)�i�����́j");
                columnName.append(",���ʌ������i��(�N������̎��s)�i���ׂāj");
            }
//          2007/5/18 �C��
//            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A)) {
//                columnName.append(",���ʌ������i��(��茤��(A)����)�i�����́j");
//                columnName.append(",���ʌ������i��(��茤��(A)����)�i���ׂāj");
//            }
//            if (searchInfo.getValues().contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B)) {
//                columnName.append(",���ʌ������i��(��茤��(B)����)�i�����́j");
//                columnName.append(",���ʌ������i��(��茤��(B)����)�i���ׂāj");
//            }
//          2007/5/18 �C������
        }
        columnName.append(",���v�������͌���");

//		String[] columnArray = {"�R�����ԍ�"
//								, "����(�������|��)"
//								, "����(�������|��)"
//								, "����(�t���K�i�|��)"
//								, "����(�t���K�i�|��)"
//								, "���������@�֖�(�ԍ�)"
//								, "���������@�֖�(�a��)"
//								, "���ǖ�(�a��)"
//								, "�E�햼��"
//								, "���t��(�X�֔ԍ�)"
//								, "���t��(�Z��)"
//								, "���t��(Email)"
//								, "�d�b�ԍ�"
//								, "URL"
//								, "��啪��"
//								, "�f�[�^�X�V��"
//								, "���l"
//								, "�ŏI���O�C����"                              
//								, "���v(������)"
//								, "���v(���ׂ�)"
//								, "��Ռ���(S)(������)"
//								, "��Ռ���(S)(���ׂ�)"
//								, "��Ռ���(A)���(������)"
//								, "��Ռ���(A)���(���ׂ�)"
//								, "��Ռ���(A)�C�O(������)"
//								, "��Ռ���(A)�C�O(���ׂ�)"
//								, "��Ռ���(B)���(������)"
//								, "��Ռ���(B)���(���ׂ�)"
//								, "��Ռ���(B)�C�O(������)"
//								, "��Ռ���(B)�C�O(���ׂ�)"
//								, "��Ռ���(C)���(������)"
//								, "��Ռ���(C)���(���ׂ�)"
//								, "��Ռ���(C)���(������)"
//								, "��Ռ���(C)���(���ׂ�)"
//								, "�G�茤��(������)"
//								, "�G�茤��(���ׂ�)"
//								, "��茤��(A)(������)"
//								, "��茤��(A)(���ׂ�)"
//								, "��茤��(B)(������)"
//								, "��茤��(B)(���ׂ�)"
//								//ADD START LY 2006/04/10
//								, "��茤���i�X�^�[�g�A�b�v�j(������) "
//								, "��茤���i�X�^�[�g�A�b�v�j(���ׂ�) "
//								//ADD END LY 2006/04/10
////2006/05/23 �c�@�ǉ���������
//                                , "���ʌ������i��(��Ռ���(A)����)�i�����́j"
//                                , "���ʌ������i��(��Ռ���(A)����)�i���ׂāj"
//                                , "���ʌ������i��(��Ռ���(B)����)�i�����́j"
//                                , "���ʌ������i��(��Ռ���(B)����)�i���ׂāj"
//                                , "���ʌ������i��(��Ռ���(C)����)�i�����́j"
//                                , "���ʌ������i��(��Ռ���(C)����)�i���ׂāj"
//                                , "���ʌ������i��(��茤��(A)����)�i�����́j"
//                                , "���ʌ������i��(��茤��(A)����)�i���ׂāj"
//                                , "���ʌ������i��(��茤��(B)����)�i�����́j"
//                                , "���ʌ������i��(��茤��(B)����)�i���ׂāj"
////2006/05/23�@�c�@�ǉ������܂�                          
//     							};
        String[] columnArray = columnName.toString().split(",");
//2006/11/06 �c�@�ǉ������܂�
//2006/11/06�@�c�@�C����������        
//		String[] initArray = new String[NUM_CSV_COUNT];
//		for(int i = 0; i < NUM_CSV_COUNT; i++) {
//			initArray[i] = "0";
//		}
        //�f�[�^����̌�����0�Ƃ��Ȃ���΂Ȃ�Ȃ��̂ŁA�����̏����z���0���i�[���Ă���
        String[] initArray = new String[columnArray.length - NUM_CSV_COMMON];
        for(int i = 0; i < columnArray.length - NUM_CSV_COMMON; i++) {
            initArray[i] = "0";
        }
        //�I���̌�����ڂ̐�
        int shumokuNum = columnArray.length - NUM_CSV_COMMON - 3;
        List tempList = searchInfo.getValues();
        //�I���̌�����ڂ̊i�[
        String[] jigyoCds = new String[shumokuNum / 2];
        //������ڂɏ��ɕ��ׂ� 
        for(int i = 0 ; i < shumokuNum / 2 ; i++){
            if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_S)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_S;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_S);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI);
            } else if ( tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_HOGA)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_HOGA;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_HOGA);
//2007/5/9 ���S�ǉ�
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S);
//2007/5/9 ���S�ǉ�����
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B;
                tempList.remove(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_WAKATESTART)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_WAKATESTART;
                tempList.remove(IJigyoCd.JIGYO_CD_WAKATESTART);
//              2007/5/18 �C��
//            } else if (tempList.contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A)) {
//                jigyoCds[i] = IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A;
//                tempList.remove(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A);
//            } else if (tempList.contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B)) {
//                jigyoCds[i] = IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B;
//                tempList.remove(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B);
            } else if (tempList.contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C)) {
                jigyoCds[i] = IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C;
                tempList.remove(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C);
//              2007/5/18 �C��
//            } else if (tempList.contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A)) {
//                jigyoCds[i] = IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A;
//                tempList.remove(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A);
//            } else if (tempList.contains(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B)) {
//                jigyoCds[i] = IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B;
//                tempList.remove(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B);
            }
        }
//2006/11/06 �c�@�C�������܂�
		List newList = new ArrayList(Arrays.asList(columnArray));	//�V�������R�������Ƃ̃��X�g(�VList1�s��)
		List newCsvList = new ArrayList();							//�y�[�W���Ƃ��ďo�͂��郊�X�g
		String beforeShinsainNo = "";
		int allCountSubete = 0;									    //���ׂĂ̑S��
		int notInputCountSubete = 0;								//���ׂĂ̖�����
        int rigaiSubete = 0;                                        //���ׂĂ̗��Q�֌W
        int shinsaJokyo = 0;                                        //�R����
        int rigaiJokyo = 0;                                        	//���Q�֌W���͊�����
		int i = 0;
		for(Iterator ite = result.iterator(); ite.hasNext(); i++) {
			List recordList = (List)ite.next();
			String shinsainNo = recordList.get(0).toString();		//�R�����ԍ�
			
			//2005.11.15 iso ���̕����͕ϐ��Ƃ��Ă͎g��Ȃ�(����List�Ƃ��Đ؂�o���Ă���)�̂ŃR�����g��
//			String nameKanjiSei = recordList.get(1).toString();		//�R������(�����|��)
//			String nameKanjiMei = recordList.get(2).toString();		//�R������(�����|��)
//			String nameKanaSei = recordList.get(3).toString();		//�R������(�t���K�i�|��)
//			String nameKanaMei = recordList.get(4).toString();		//�R������(�t���K�i�|��)
//			String shozokuCd = recordList.get(5).toString();		//�R���������@�փR�[�h
//			String shozokuName = recordList.get(6).toString();		//�R���������@�֖�
//			String bukyokuName = recordList.get(7).toString();		//�R�������ǖ�
//			String shokushuName = recordList.get(8).toString();		//�R�����E�햼
//			String sofuZip = recordList.get(9).toString();			//���t��(�X�֔ԍ�)
//			String sofuZipAddress = recordList.get(10).toString();	//���t��(�Z��)
//			String sofuZipEmail = recordList.get(11).toString();	//���t��(Email)
//			String shozokuTel = recordList.get(12).toString();		//�d�b�ԍ�
//			String url = recordList.get(13).toString();				//URL
//			String senmon = recordList.get(14).toString();			//��啪��
//			String koshinDate = recordList.get(15).toString();		//�f�[�^�X�V��
//			String biko = recordList.get(16).toString();			//���l
//			String loginDate = recordList.get(17).toString();		//���O�C����
//			String allCount = recordList.get(18).toString();		//����(���ׂ�)
//			String motInputCount = recordList.get(19).toString();	//����(������)
			
//2006/11/06 �c�@�C����������
//            String jigyoCd = recordList.get(20).toString();           //���ƃR�[�h
            String jigyoCd = recordList.get(NUM_CSV_COMMON + 3).toString();//���ƃR�[�h        

			//1�O�̐R�����ԍ��ƈ�v�����ꍇ�A1�s���̃f�[�^�Ȃ̂�put����
            int j = 0;
			if(shinsainNo.equals(beforeShinsainNo)) {
				//�e���Ƃ̌������X�V����
				//�e���Ƃ̏��Ԃ̓J�������X�g�ɍ��킹�邱��
				//���������ɓ�������������1�A�����������ƂȂ����ǓƗ����\�b�h�ɂ��ׂ��H               
//                if(IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyoCd)) {                 //��Ռ���(S)�F00031
//                    newList.set(NUM_CSV_COMMON+2, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+3, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyoCd)) {        //��Ռ���(A)(���)�F00041
//                    newList.set(NUM_CSV_COMMON+4, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+5, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyoCd)) {       //��Ռ���(A)(�C�O�w�p����)�F00043
//                    newList.set(NUM_CSV_COMMON+6, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+7, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyoCd)) {        //��Ռ���(B)(���)�F00051
//                    newList.set(NUM_CSV_COMMON+8, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+9, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyoCd)) {       //��Ռ���(B)(�C�O�w�p����)�F00053
//                    newList.set(NUM_CSV_COMMON+10, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+11, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd)) {        //��Ռ���(C)(���)�F00061
//                    newList.set(NUM_CSV_COMMON+12, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+13, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd)) {       //��Ռ���(C)(��撲��)�F00062
//                    newList.set(NUM_CSV_COMMON+14, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+15, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyoCd)) {           //�G�茤���F00111
//                    newList.set(NUM_CSV_COMMON+16, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+17, recordList.get(18));
//                }else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd)) {        //��茤��(A)�F00121
//                    newList.set(NUM_CSV_COMMON+18, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+19, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)) {       //��茤��(B)�F00131
//                    newList.set(NUM_CSV_COMMON+20, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+21, recordList.get(18));
//                } 
//                //add start ly 2006/04/10
//                else if(IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyoCd)) {            //��茤���i�X�^�[�g�A�b�v�j�F00141
//                    newList.set(NUM_CSV_COMMON+22, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+23, recordList.get(18));
//                } 
//                //add end ly 2006/04/10
////2006/05/23�@�ǉ���������
//                else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyoCd)) {     //���ʌ������i��i��Ռ���(A)�����j�F00152
//                    newList.set(NUM_CSV_COMMON+24, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+25, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyoCd)) {     //���ʌ������i��i��Ռ���(B)�����j�F00153
//                    newList.set(NUM_CSV_COMMON+26, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+27, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyoCd)) {     //���ʌ������i��i��Ռ���(C)�����j�F00154
//                    newList.set(NUM_CSV_COMMON+28, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+29, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyoCd)) {     //���ʌ������i��i��茤��(A)�����j�F00155
//                    newList.set(NUM_CSV_COMMON+30, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+31, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyoCd)) {     //���ʌ������i��i��茤��(B)�����j�F00156
//                    newList.set(NUM_CSV_COMMON+32, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+33, recordList.get(18));
//                }    
////�c�@�ǉ������܂�
                //��Ռ���(S)�F00031
                if (IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_S)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //��Ռ���(A)(���)�F00041    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //��Ռ���(A)(�C�O�w�p����)�F00043
                } else if (IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //��Ռ���(B)(���)�F00051
                } else if (IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //��Ռ���(B)(�C�O�w�p����)�F00053
                } else if (IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //��Ռ���(C)(���)�F00061     
                } else if (IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //��Ռ���(C)(��撲��)�F00062    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //�G�茤���F00111    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_HOGA)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //��茤��(S)�F00120�@2007/5/9�ǉ�    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //��茤��(A)�F00121    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //��茤��(B)�F00131    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                //��茤���i�X�^�[�g�A�b�v�j�F00141
                } else if (IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_WAKATESTART)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
//                  2007/5/18 �C��
//                //���ʌ������i��i��Ռ���(A)�����j�F00152
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
//                //���ʌ������i��i��Ռ���(B)�����j�F00153
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
                //���ʌ������i��i��Ռ���(C)�����j�F00154
                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
// 2007/5/18 �C��
//                //���ʌ������i��i��茤��(A)�����j�F00155   
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyoCd)) {
//                    //���ʌ������i��i��茤��(A)�����j�F00155
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
//                //���ʌ������i��i��茤��(B)�����j�F00156 
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
                }            
//2006/11/06 �c�@�C�������܂�
				//�ݐσJ�E���g��ǉ�
				notInputCountSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON + 1).toString());
				allCountSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON).toString());
                rigaiSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON + 2).toString());
                //���Q�֌W���͊����󋵂�ǉ������� 2007/5/9
                //shinsaJokyo += Integer.parseInt(recordList.get(NUM_CSV_COMMON - 1).toString());
                shinsaJokyo += Integer.parseInt(recordList.get(NUM_CSV_COMMON - 2).toString());
                rigaiJokyo += Integer.parseInt(recordList.get(NUM_CSV_COMMON - 1).toString());
			} else {
				//�ݐσJ�E���g���Z�b�g�����珉����
				//�ŏ��̃J�����ł͍s��Ȃ��B
				if(i != 0) {
					newList.set(NUM_CSV_COMMON, Integer.toString(notInputCountSubete));
					newList.set(NUM_CSV_COMMON + 1, Integer.toString(allCountSubete));
                    newList.set(columnArray.length - 1, Integer.toString(rigaiSubete));
                    if(shinsaJokyo == allCountSubete){
                        newList.set(NUM_CSV_COMMON - 2, "����");
                    } else {
                        newList.set(NUM_CSV_COMMON - 2, "������");
                    }
                    if(rigaiJokyo == allCountSubete){
                        newList.set(NUM_CSV_COMMON - 1, "����");
                    } else {
                        newList.set(NUM_CSV_COMMON - 1, "������");
                    }
					allCountSubete =0;
					notInputCountSubete = 0;
                    rigaiSubete = 0;
                    shinsaJokyo = 0;
                    rigaiJokyo = 0;
				}
				//1�O�ƈႤ�R�����ԍ��̏ꍇ�A�O�̐R������1�s���f�[�^��VCSV���X�g�ɓo�^
				newCsvList.add(newList);
				
				//���R�������̊�{�f�[�^���쐬
				//���ʕ������Z�b�g
				newList = new ArrayList(recordList.subList(0, NUM_CSV_COMMON));
				//���������̏�����Ԃ��Z�b�g
				newList.addAll(Arrays.asList(initArray));

				//2005.12.02 iso �e�R�������Ƃ�1�ڂ̎��ƕ��̍X�V
				//�e���Ƃ̌������X�V����
				//�e���Ƃ̏��Ԃ̓J�������X�g�ɍ��킹�邱��
//2006/11/06 �c�@�C����������                
//                if(IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyoCd)) {                 //��Ռ���(S)�F00031
//                    newList.set(NUM_CSV_COMMON+2, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+3, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyoCd)) {        //��Ռ���(A)(���)�F00041
//                    newList.set(NUM_CSV_COMMON+4, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+5, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyoCd)) {       //��Ռ���(A)(�C�O�w�p����)�F00043
//                    newList.set(NUM_CSV_COMMON+6, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+7, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyoCd)) {        //��Ռ���(B)(���)�F00051
//                    newList.set(NUM_CSV_COMMON+8, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+9, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyoCd)) {       //��Ռ���(B)(�C�O�w�p����)�F00053
//                    newList.set(NUM_CSV_COMMON+10, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+11, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd)) {        //��Ռ���(C)(���)�F00061
//                    newList.set(NUM_CSV_COMMON+12, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+13, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd)) {       //��Ռ���(C)(��撲��)�F00062
//                    newList.set(NUM_CSV_COMMON+14, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+15, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyoCd)) {           //�G�茤���F00111
//                    newList.set(NUM_CSV_COMMON+16, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+17, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd)) {       //��茤��(A)�F00121
//                    newList.set(NUM_CSV_COMMON+18, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+19, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)) {       //��茤��(B)�F00131
//                    newList.set(NUM_CSV_COMMON+20, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+21, recordList.get(18));
//                } 
//                //add start ly 2006/04/10
//                else if(IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyoCd)) {            //��茤���i�X�^�[�g�A�b�v�j�F00141
//                    newList.set(NUM_CSV_COMMON+22, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+23, recordList.get(18));
//                } 
//                //add end ly 2006/04/10
////2006/05/23�@�ǉ���������
//                else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyoCd)) {     //���ʌ������i��i��Ռ���(A)�����j�F00152
//                    newList.set(NUM_CSV_COMMON+24, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+25, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyoCd)) {     //���ʌ������i��i��Ռ���(B)�����j�F00153
//                    newList.set(NUM_CSV_COMMON+26, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+27, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyoCd)) {     //���ʌ������i��i��Ռ���(C)�����j�F00154
//                    newList.set(NUM_CSV_COMMON+28, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+29, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyoCd)) {     //���ʌ������i��i��茤��(A)�����j�F00155
//                    newList.set(NUM_CSV_COMMON+30, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+31, recordList.get(18));
//                } else if(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyoCd)) {     //���ʌ������i��i��茤��(B)�����j�F00156
//                    newList.set(NUM_CSV_COMMON+32, recordList.get(19));
//                    newList.set(NUM_CSV_COMMON+33, recordList.get(18));
//                }    
////�c�@�ǉ������܂�  
                //��Ռ���(S)�F00031
                if (IJigyoCd.JIGYO_CD_KIBAN_S.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_S)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //��Ռ���(A)(���)�F00041    
				} else if(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN.equals(jigyoCd)) {		//��Ռ���(A)(���)�F00041
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //��Ռ���(A)(�C�O�w�p����)�F00043
                } else if (IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //��Ռ���(B)(���)�F00051
                } else if (IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //��Ռ���(B)(�C�O�w�p����)�F00053
                } else if (IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //��Ռ���(C)(���)�F00061     
                } else if (IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //��Ռ���(C)(��撲��)�F00062    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //�G�茤���F00111    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_HOGA.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_HOGA)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //��茤��(S)�F00120  2007/5/8 �ǉ�
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //��茤��(A)�F00121    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //��茤��(B)�F00131    
                } else if (IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
                    //��茤���i�X�^�[�g�A�b�v�j�F00141
                } else if (IJigyoCd.JIGYO_CD_WAKATESTART.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_WAKATESTART)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
// 2007/5/18 �C��
//                    //���ʌ������i��i��Ռ���(A)�����j�F00152
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
//                    //���ʌ������i��i��Ռ���(B)�����j�F00153
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
                    //���ʌ������i��i��Ռ���(C)�����j�F00154
                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(jigyoCd)) {
                    for (j = 0; j < jigyoCds.length; j++) {
                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C)) {
                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
                        }
                    }
// 2007/5/18 �C��
//                    //���ʌ������i��i��茤��(A)�����j�F00155   
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A.equals(jigyoCd)) {
//                    //���ʌ������i��i��茤��(A)�����j�F00155
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
//                    //���ʌ������i��i��茤��(B)�����j�F00156 
//                } else if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B.equals(jigyoCd)) {
//                    for (j = 0; j < jigyoCds.length; j++) {
//                        if (jigyoCds[j].equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B)) {
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2, recordList.get(NUM_CSV_COMMON + 1));
//                            newList.set(NUM_CSV_COMMON + 2 + j * 2 + 1, recordList.get(NUM_CSV_COMMON));
//                        }
//                    }
                } 
//2006/11/06 �c�@�C�������܂�
				//�ݐσJ�E���g��ǉ�
				notInputCountSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON + 1).toString());
				allCountSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON).toString());
                rigaiSubete += Integer.parseInt(recordList.get(NUM_CSV_COMMON + 2).toString());
                shinsaJokyo += Integer.parseInt(recordList.get(NUM_CSV_COMMON - 2).toString());
                rigaiJokyo += Integer.parseInt(recordList.get(NUM_CSV_COMMON - 1).toString());
				
				//���݂̐R�����ԍ������̔�r�Ɏg�����߂ɃZ�b�g
				beforeShinsainNo = shinsainNo;
			}

			//�Ō�̃f�[�^�͏��else�Ɉ���������Ȃ��̂ŁA������csvList�Ɋi�[����B
			if(i == result.size()-1) {
				//�ݐσJ�E���g���Z�b�g�����珉����
				newList.set(NUM_CSV_COMMON, Integer.toString(notInputCountSubete));
				newList.set(NUM_CSV_COMMON + 1, Integer.toString(allCountSubete));  
                newList.set(columnArray.length - 1, Integer.toString(rigaiSubete));
                if(shinsaJokyo == allCountSubete){
                    newList.set(NUM_CSV_COMMON - 2, "����");
                } else {
                    newList.set(NUM_CSV_COMMON - 2, "������");
                }
                if(rigaiJokyo == allCountSubete){
                    newList.set(NUM_CSV_COMMON - 1, "����");
                } else {
                    newList.set(NUM_CSV_COMMON - 1, "������");
                }
                //�o��CSV�ɐR�������Ƃɂ܂Ƃ߂��f�[�^���Z�b�g
				newCsvList.add(newList);
			}
		}  
		return newCsvList;
	}

	/**
	 * �R���󋵂̍X�V.<br /><br />
	 * 
	 * 1.�R�����̗L���̃`�F�b�N<br /><br />
	 * �܂��ĐR���ΏېR���������݂��邩�`�F�b�N����B<br />
	 * <b>shinsainInfoDao</b>��<b>selectShinsainInfo</b>���\�b�h��p���A�R���������݂���ꍇ�ɂ͐R���������擾����B<br />
	 * �ĐR���S���̐R�������폜����Ă���ꍇ�́A��O��throw����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *  SELECT
	 * 		SI.SHINSAIN_ID SHINSAIN_ID
	 * 		, MS.SHINSAIN_NO SHINSAIN_NO
	 * 		, MS.JIGYO_KUBUN JIGYO_KUBUN
	 * 		, MS.NAME_KANJI_SEI NAME_KANJI_SEI
	 * 		, MS.NAME_KANJI_MEI NAME_KANJI_MEI
	 * 		, MS.NAME_KANA_SEI NAME_KANA_SEI
	 * 		, MS.NAME_KANA_MEI NAME_KANA_MEI
	 * 		, MS.SHOZOKU_CD SHOZOKU_CD
	 * 		, MS.SHOZOKU_NAME SHOZOKU_NAME
	 * 		, MS.BUKYOKU_NAME BUKYOKU_NAME
	 *		, MS.SHOKUSHU_NAME SHOKUSHU_NAME
	 *		, MS.SOFU_ZIP SOFU_ZIP
	 *		, MS.SOFU_ZIPADDRESS SOFU_ZIPADDRESS
	 *		, MS.SOFU_ZIPEMAIL SOFU_ZIPEMAIL
	 *	 	, MS.SHOZOKU_TEL SHOZOKU_TEL
	 * 		, MS.SHOZOKU_FAX SHOZOKU_FAX
	 * 		, MS.URL URL
	 *		, MS.SENMON SENMON
	 *		, MS.KOSHIN_DATE KOSHIN_DATE
	 *		, MS.BIKO BIKO
	 *		, SI.PASSWORD PASSWORD
	 *		, SI.YUKO_DATE YUKO_DATE
	 *		, SI.DEL_FLG DEL_FLG
	 * 	FROM
	 * 		MASTER_SHINSAIN MS
	 * 		, SHINSAININFO SI
	 * 	WHERE
	 * 		SI.DEL_FLG = 0
	 * 		AND MS.SHINSAIN_NO = ?
	 * 		AND MS.JIGYO_KUBUN = ?
	 * 		AND MS.SHINSAIN_NO = SUBSTR(SI.SHINSAIN_ID,4,7) 
	 * 		AND MS.JIGYO_KUBUN = SUBSTR(SI.SHINSAIN_ID,3,1)	</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������shinsainInfo�̕ϐ�(ShinsainNo)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������shinsainInfo�̕ϐ�(JigyoKubun)���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 2.�R�����ʏ��̍X�V<br /><br />
	 * <b>shinsaKekkaInfoDao</b>��<b>updateShinsainInfo</b>���\�b�h��p���āA�S���R�����ʏ��̐R���������X�V����B<br />
	 * ����͑�l������<b>shinsaJokyoInfo.getJigyoId()</b>��null�ł͂Ȃ����߁A�R���������Ă�����̂��ΏۂƂȂ�B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	UPDATE SHINSAKEKKA
	 *	SET
	 * 		SHINSAIN_NO = ?			-- �R�����ԍ�
	 * 		, JIGYO_KUBUN = ?				-- ���Ƌ敪
	 * 		, SHINSAIN_NAME_KANJI_SEI = ?	-- �R�������i����-���j
	 * 		, SHINSAIN_NAME_KANJI_MEI = ?	-- �R�������i����-���j
	 * 		, NAME_KANA_SEI = ?		-- �R�������i�t���K�i�|���j
	 * 		, NAME_KANA_MEI = ?		-- �R�������i�t���K�i�|���j
	 * 		, SHOZOKU_NAME = ?		-- �R���������@�֖�
	 * 		, BUKYOKU_NAME = ?		-- �R�������ǖ�
	 * 		, SHOKUSHU_NAME = ?		-- �R�����E��
	 *	WHERE
	 * 		SHINSAIN_NO = ?				-- �R�����ԍ�
	 * 		AND JIGYO_KUBUN = ?			-- ���Ƌ敪
	 * 		AND SHINSA_JOKYO = 1	-- �R���󋵁i1:�R���󋵁i�����j�j
	 *	
	 *			<b><span style="color:#002288">-- ���I�������� --</span></b>
	 * 
	 * 		AND EXISTS (
	 * 			SELECT * 
	 * 			FROM
	 * 				SHINSEIDATAKANRI A
	 * 				, SHINSAKEKKA B
	 * 			WHERE
	 * 				A.DEL_FLG = 0
	 * 				AND A.SYSTEM_NO = B.SYSTEM_NO
	 * 		)
	 * 	</pre>
	 * </td></tr>
	 * </table><br />
	 * �o�C���h�ϐ�
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������shinsaJokyoInfo�̕ϐ�(ShinsainNo)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������shinsaJokyoInfo�̕ϐ�(JigyoKubun)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i����-���j</td><td>������shinsaJokyoInfo�̕ϐ�(NameKanjiSei)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i����-���j</td><td>������shinsaJokyoInfo�̕ϐ�(NameKanjiMei)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>������shinsaJokyoInfo�̕ϐ�(NameKanaSei)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>������shinsaJokyoInfo�̕ϐ�(NameKanaMei)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R���������@�֖�</td><td>������shinsaJokyoInfo�̕ϐ�(ShozokuName)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������ǖ�</td><td>������shinsaJokyoInfo�̕ϐ�(BukyokuName)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����E��</td><td>������shinsaJokyoInfo�̕ϐ�(ShokushuName)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������shinsaJokyoInfo�̕ϐ�(ShinsainNo)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������shinsaJokyoInfo�̕ϐ�(JigyoKubun)���g�p����B</td></tr>
	 * </table><br />
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����shinsaJokyoInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoId</td><td>AND JIGYO_ID = '����ID'</td></tr>
	 * </table><br/>
	 * 
	 * 3.�R���󋵂̍X�V<br /><br />
	 * <b>shinsaKekkaInfoDao</b>��<b>updateShinsaKekkaInfo</b>���\�b�h��p���A�R�����ʏ��̐R���󋵂��X�V����B<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	UPDATE
	 * 		SHINSAKEKKA A
	 *	SET
	 * 		A.SHINSA_JOKYO = ?		-- �R����
	 *	WHERE
	 * 		A.SYSTEM_NO IN (
	 * 			SELECT
	 * 				B.SYSTEM_NO
	 * 			FROM
	 * 				SHINSEIDATAKANRI B
	 * 				, SHINSAKEKKA C
	 * 			WHERE
	 * 				B.SYSTEM_NO=C.SYSTEM_NO
	 * 				AND B.DEL_FLG=0
	 * 				AND (
	 * 					B.JOKYO_ID = '10'
	 * 						-- �\����(10:�u1���R�����v)
	 * 					OR B.JOKYO_ID = '11'
	 * 						-- �\����(11:�u1���R�������v)
	 * 				)
	 *	
	 *				<b><span style="color:#002288">-- ���I�������� --</span></b>
	 * 
	 * 			)
	 * 		AND A.SHINSAIN_NO = ?			-- �R�����ԍ�
	 * 		AND A.JIGYO_KUBUN = ?			-- �S�����Ƌ敪
	 * 		AND A.JIGYO_ID = ?				-- ����ID
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 * �o�C���h�ϐ�
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R����</td><td>������shinsaJokyoInfo�̕ϐ�(ShinsainNo)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������shinsaJokyoInfo�̕ϐ�(JigyoKubun)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�S�����Ƌ敪</td><td>������shinsaJokyoInfo�̕ϐ�(NameKanjiSei)���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td><b>null</b>���Z�b�g</td></tr>
	 * </table>
	 * <br />
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param shinsaJokyoInfo ShinsaJokyoInfo
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#saishinsa(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoSearchInfo)
	 */
	public void saishinsa(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo)
			throws ApplicationException, ValidationException {
		
//		//�R���󋵃t���O�𖢊����ɐݒ�
//		UserInfo userInfoShinsain = new UserInfo();
//		userInfoShinsain.setShinsainInfo(shinsainInfo);
//		ShinsaKekkaMaintenance shinsaKekkaMaintenance = new ShinsaKekkaMaintenance();
//		shinsaKekkaMaintenance.updateJigyoShinsaCompleteYet(userInfoShinsain, shinsaJokyoInfo.getJigyoId());
		
		
		//�ĐR���ΏېR���������݂��邩�`�F�b�N
		ShinsainInfo shinsainInfo = new ShinsainInfo();
		shinsainInfo.setJigyoKubun(shinsaJokyoInfo.getJigyoKubun());
		shinsainInfo.setShinsainNo(shinsaJokyoInfo.getShinsainNo());

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			try {
				//�ĐR���S���̐R�������폜����Ă���ꍇ�A�G���[�Ƃ���B
				ShinsainInfoDao shinsainInfoDao = new ShinsainInfoDao(userInfo);
				//���݂���ꍇ�A�R�����ʃe�[�u���ɍŐV�̐R�������𔽉f�����邽�߁A�R���������擾����B
				shinsainInfo = shinsainInfoDao.selectShinsainInfo(connection, shinsainInfo);
			} catch(NoDataFoundException e) {
				//�G���[���ێ��p���X�g
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.5030"));
				throw new ValidationException(
					"�S���̐R�������폜����Ă��܂��B",
					errors);
			}
			shinsainInfo.setJigyoKubun(shinsaJokyoInfo.getJigyoKubun());
			
			ShinsaKekkaInfoDao shinsaKekkaInfoDao = new ShinsaKekkaInfoDao(userInfo);
			//�ĐR���Ώێ��Ƃ̐R�����ʏ��ŁA�R���������Ă�����̂��ΏہB
			shinsaKekkaInfoDao.updateShinsainInfo(connection,
												  shinsainInfo,
												  ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE,
												  shinsaJokyoInfo.getJigyoId());
			//�ĐR���ݒ���s���B
			shinsaKekkaInfoDao.updateShinsaKekkaInfo(connection, 
													 shinsainInfo.getShinsainNo(), 
													 shinsainInfo.getJigyoKubun(), 
													 null,
													 shinsaJokyoInfo.getJigyoId(),
													 ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET);

			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;
		} catch(NoDataFoundException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
				new ErrorInfo("errors.4005"),
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
					"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
	}
	
//2006/10/25 jinbaogang add start
	/**
	 * ���v�����ē��͊����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param shinsaJokyoInfo		���v�����ē��͏󋵏��B
	 * @throws ApplicationException	
	 * @throws ValidationException	
	 */
	public void updateSaiNyuryoku(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo)
			throws ApplicationException, ValidationException {
			
		ShinsainInfo shinsainInfo = new ShinsainInfo();
		shinsainInfo.setJigyoKubun(shinsaJokyoInfo.getJigyoKubun());
		shinsainInfo.setShinsainNo(shinsaJokyoInfo.getShinsainNo());

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			try {
				ShinsainInfoDao shinsainInfoDao = new ShinsainInfoDao(userInfo);
				shinsainInfo = shinsainInfoDao.selectShinsainInfo(connection, shinsainInfo);
			} catch(NoDataFoundException e) {
				List errors = new ArrayList();
				errors.add(new ErrorInfo("errors.5030"));
				throw new ValidationException(
					"���v�����ē��͊���",
					errors);
			}
			shinsainInfo.setJigyoKubun(shinsaJokyoInfo.getJigyoKubun());
			
			ShinsaKekkaInfoDao shinsaKekkaInfoDao = new ShinsaKekkaInfoDao(userInfo);
			//���v�����ē��͊������s��
			shinsaKekkaInfoDao.updateNyuryokuJokyo(connection, 
													 shinsainInfo.getShinsainNo(), 
													 shinsainInfo.getJigyoKubun(), 
													 shinsaJokyoInfo.getJigyoId(),
													 ShinsaKekkaMaintenance.NYURYOKUJOKYO_COMPLETE_YET);

			//---------------------------------------
			//�X�V����I��
			//---------------------------------------
			success = true;
		} catch(NoDataFoundException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
				new ErrorInfo("errors.4005"),
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
					"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}		
	}	
//2006/10/25 adde jinbaogang  end
	
	/**
	 * �R�����ʂ̃N���A.<br /><br />
	 * 
	 * �ȉ���SQL�����s���A�R�����ʂ��N���A����B
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 *	 UPDATE
	 *	 	SHINSAKEKKA
	 *	 SET
	 *	 	 KEKKA_ABC = NULL	-- �����]���iABC�j
	 *	 	,KEKKA_TEN = NULL	-- �����]���i�_���j
	 *	 	,COMMENT1 = NULL	-- �R�����g1
	 *	 	,COMMENT2 = NULL	-- �R�����g2
	 *	 	,COMMENT3 = NULL	-- �R�����g3
	 *	 	,COMMENT4 = NULL	-- �R�����g4
	 *	 	,COMMENT5 = NULL	-- �R�����g5
	 *	 	,COMMENT6 = NULL	-- �R�����g6
	 *	 	,KENKYUNAIYO = NULL	-- �������e
	 *	 	,KENKYUKEIKAKU = NULL	-- �����v��
	 *	 	,TEKISETSU_KAIGAI = NULL	-- �K�ؐ�-�C�O
	 *	 	,TEKISETSU_KENKYU1 = NULL	-- �K�ؐ�-�����i1�j
	 *	 	,TEKISETSU = NULL	-- �K�ؐ�
	 *	 	,DATO = NULL	-- �Ó���
	 *	 	,SHINSEISHA = NULL	-- ������\��
	 *	 	,KENKYUBUNTANSHA = NULL	-- �������S��
	 *	 	,HITOGENOMU = NULL	-- �q�g�Q�m��
	 *	 	,TOKUTEI = NULL	-- ������
	 *	 	,HITOES = NULL	-- �q�gES�זE
	 *	 	,KUMIKAE = NULL	-- ��`�q�g��������
	 *	 	,CHIRYO = NULL	-- ��`�q���×Տ�����
	 *	 	,EKIGAKU = NULL	-- �u�w����
	 *	 	,COMMENTS = NULL	-- �R�����g
	 *	 	,TENPU_PATH = NULL	-- �Y�t�t�@�C���i�[�p�X
	 *	 WHERE
	 *	 	SHINSAIN_NO = ?	-- �R�����ԍ�
	 *	 	AND JIGYO_ID = ?	-- ����ID
	 *	 	AND SHINSA_JOKYO = '0'	-- �R���󋵁i�������j
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 * ���\���f�[�^�̐R�����ʁi�\�[�g�ς݁j�ɂ��Ă̓N���A�����A�R���������̂��̂����X�V����B
	 * 
	 * @param userInfo	UserInfo
	 * @param shinsaJokyoInfo	�N���A����\�����iShinsaJokyoInfo�j
	 * @throws ApplicationException
	 */
	public void clearShinsaKekka(UserInfo userInfo, ShinsaJokyoInfo shinsaJokyoInfo)
			throws ApplicationException
	{
		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			ShinsaKekkaInfoDao shinsaKekkaInfoDao = new ShinsaKekkaInfoDao(userInfo);
			shinsaKekkaInfoDao.clearShinsaKekka(connection, shinsaJokyoInfo);
			
			//2005.01.24
			//�\���f�[�^�e�[�u���̕]�����Ƀ\�[�g���ꂽ1���R�����ʏ����X�V����B
			//�����ӏ��Ŏg�p����̂ŕʃ��\�b�h�ɂ��ׂ��H
			//�\���f�[�^�Ǘ�DAO
			ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);

			//�N���A�ΏېR�����ʏ��(�z��)���擾����B
			ShinsaKekkaInfo[] tantoShinsaKekkaInfoArray = null;					
			try {
				tantoShinsaKekkaInfoArray = shinsaKekkaInfoDao.selectShinsaKekkaInfo(connection, shinsaJokyoInfo);
			} catch(NoDataFoundException e) {
				throw e;
			} catch(DataAccessException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
			
			//�\���f�[�^�̏��������͕����ΏہB
			for(int i = 0; i < tantoShinsaKekkaInfoArray.length; i++) {
				//�r������̂��ߊ����f�[�^���擾����
				ShinseiDataPk shinseiDataPk = new ShinseiDataPk(tantoShinsaKekkaInfoArray[i].getSystemNo());
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
						"���Y�\���f�[�^�͍폜����Ă��܂��BSystemNo=" + tantoShinsaKekkaInfoArray[i].getSystemNo(),
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
						+ tantoShinsaKekkaInfoArray[i].getSystemNo(),
						new ErrorInfo("errors.9012"));
				}
	
				//---�R�����ʃ��R�[�h�擾�i����ABC�̏����j---
				ShinsaKekkaInfo[] shinsaKekkaInfoArray = null;						
				try{
					shinsaKekkaInfoArray = shinsaKekkaInfoDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
				}catch(NoDataFoundException e){
					throw e;
				}catch(DataAccessException e){
					throw new ApplicationException(
						"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}
	
				//���ꐫ���Ƃ邽��for�������A�O�ɏo���ׂ�?
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
					for(int j=0; j<shinsaKekkaInfoArray.length; j++){
						try{
							//�����]���iABC�j�Ƒ����]���i�_���j�͍��݂��Ȃ�
							if(shinsaKekkaInfoArray[j].getKekkaAbc() != null){
								kekkaAbc = kekkaAbc + shinsaKekkaInfoArray[j].getKekkaAbc();
								String tensu = (String) sogoHyokaMap.get(shinsaKekkaInfoArray[j].getJigyoKubun()
															+ shinsaKekkaInfoArray[j].getKekkaAbc());
								if(tensu == null){
									throw new ApplicationException(
										"�����]���}�X�^�Ɉ�v����f�[�^�����݂��܂���B�����L�[�F�����]��'"
										+ shinsaKekkaInfoArray[j].getKekkaAbc() 
										+ "',���Ƌ敪�F'" +  shinsaKekkaInfoArray[j].getJigyoKubun() + "'",
										new ErrorInfo("errors.4002"));	
								}
								intKekkaTen = intKekkaTen
													+ Integer.parseInt((String) sogoHyokaMap.get(
																	shinsaKekkaInfoArray[j].getJigyoKubun()
																		 + shinsaKekkaInfoArray[j].getKekkaAbc()));
								kekkaTenFlag = true;	//1�ł��_�����ݒ肳��Ă����ꍇ[true]						
							}else if(shinsaKekkaInfoArray[j].getKekkaTen() != null){
									//�P���R������(�_��)
									intKekkaTen = intKekkaTen
														+ Integer.parseInt((String) sogoHyokaMap.get(
																		shinsaKekkaInfoArray[j].getJigyoKubun()
																			 + shinsaKekkaInfoArray[j].getKekkaTen()));
									
									//�P���R������(�_����)
									if(kekkaTenSorted.equals("")){
										kekkaTenSorted = kekkaTenSorted + shinsaKekkaInfoArray[j].getKekkaTen();
									}else{
										kekkaTenSorted = kekkaTenSorted + " " + shinsaKekkaInfoArray[j].getKekkaTen();									
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
			}
			
			success = true;
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
				new ErrorInfo("errors.4005"),
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
					"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
		
	}
	
	
	
	/**
	 * ���͒l�`�F�b�N.<br/>
	 * ����������������info��ԋp.<br />
	 * @param userInfo UserInfo
	 * @param info ShinsaJokyoInfo
	 * @param mode String
	 * @see jp.go.jsps.kaken.model.IShinsaJokyoKakunin#validate(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.impl.vo.ShinsaJokyoSearchInfo)
	 */
	public ShinsaJokyoInfo validate(UserInfo userInfo, ShinsaJokyoInfo info, String mode)
		throws ApplicationException, ValidationException {
			return info;
	}
	
	
	

	/**
	 * �R���󋵌��������I�u�W�F�N�g���猟���������擾��SQL�̖₢���킹�����𐶐�����B
	 * ���������₢���킹�����͑������̕�����̌��Ɍ��������B
	 * ��l������OREDER����t�����邩���f����B
	 * @param select    
	 * @param userInfo  
	 * @param searchInfo
	 * @param orderFlg	ture:ORDER����t���@false:OREDER���ȗ�
	 * @return
	 */
	protected static String getQueryString(String select, UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo, boolean orderFlg) {

		//-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
		StringBuffer query = new StringBuffer(select);
		
		//2005.11.08 �����s���̃o�O(ORA-03113)�̂��ߐR�����ԍ��͍Ō�Ɉړ�
//		if(!StringUtil.isBlank(searchInfo.getShinsainNo())) {					//�R�����ԍ�
//			query.append(" AND A.SHINSAIN_NO = '" + searchInfo.getShinsainNo() + "' ");
//		}

		//�R���������i����-���j
		if(!StringUtil.isBlank(searchInfo.getNameKanjiSei())) {
			query.append(" AND A.SHINSAIN_NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		//�R���������i����-���j
		if(!StringUtil.isBlank(searchInfo.getNameKanjiMei())) {
			query.append(" AND A.SHINSAIN_NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		//�R���������i�t���K�i-���j
		if(!StringUtil.isBlank(searchInfo.getNameKanaSei())) {
			query.append(" AND A.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
		}
		//�R���������i�t���K�i-���j
		if(!StringUtil.isBlank(searchInfo.getNameKanaMei())) {
			query.append(" AND A.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
		}
    	//�R�������������@�֖�	 2006/07/03 dyh �ǉ�
        if(!StringUtil.isBlank(searchInfo.getShozokuName())) {
            query.append(" AND A.SHOZOKU_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getShozokuName()) + "%'");
        }
		//�N�x
		if(!StringUtil.isBlank(searchInfo.getNendo())) {
			query.append(" AND B.NENDO = " + EscapeUtil.toSqlString(searchInfo.getNendo()));
		}
		//��
		if(!StringUtil.isBlank(searchInfo.getKaisu())) {
			query.append(" AND  B.KAISU = " + EscapeUtil.toSqlString(searchInfo.getKaisu()));
		}
		//���Ɩ��i���ƃR�[�h�j
		if(!searchInfo.getValues().isEmpty()) {
			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (")
				 .append(StringUtil.changeIterator2CSV(searchInfo.getValues().iterator(), false))
				 .append(")");
		}
		//�R���������@�փR�[�h
		if(!StringUtil.isBlank(searchInfo.getShozokuCd())) {
			query.append(" AND B.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) + "'");
		}
		//�Ɩ��S���҂̒S�����Ƌ敪���擾
		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);
		if(!shinsaTaishoSet.isEmpty()) {
			query.append(" AND B.JIGYO_KUBUN IN (")
				 .append(StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(), false))
				 .append(")");
		}
		//�n���̋敪�Ɨ��̗̂�������������
		if(!StringUtil.isBlank(searchInfo.getKeiName())) {
			query.append(" AND (B.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
				+ "%' OR B.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
		}
		
		//���Q�֌W���͊����� 2007/5/9 �ǉ��@By xiang
		if(!StringUtil.isBlank(searchInfo.getRigaiJokyo())
				&& !"2".equals(searchInfo.getRigaiJokyo())) {
			query.append(" AND NVL(A.NYURYOKU_JOKYO,'0') = '" + EscapeUtil.toSqlString(searchInfo.getRigaiJokyo()) + "'");
		}	

		//�R����
		if(!StringUtil.isBlank(searchInfo.getShinsaJokyo())
				&& !"2".equals(searchInfo.getShinsaJokyo())) {
			query.append(" AND A.SHINSA_JOKYO = '" + EscapeUtil.toSqlString(searchInfo.getShinsaJokyo()) + "'");
		}	
		
		//�ŏI���O�C������ǉ�		2005/10/25�ǉ� 
		if (!StringUtil.isBlank(searchInfo.getLoginDate())) {
			if("1".equals(searchInfo.getLoginDate()) ){
				query.append(" AND D.LOGIN_DATE IS NOT NULL");
			}else{
				query.append(" AND D.LOGIN_DATE IS NULL ");
			}	 
		}

		//���Q�֌W�҂�ǉ�			2005/10/25�ǉ�
		if(!StringUtil.isBlank(searchInfo.getRigaiKankeisha())) {
			if("1".equals(searchInfo.getRigaiKankeisha()) ){
				query.append(" AND A.RIGAI = '" + EscapeUtil.toSqlString(searchInfo.getRigaiKankeisha()) + "'");
			}else{
				query.append(" AND ( A.RIGAI IS NULL OR A.RIGAI = '" + EscapeUtil.toSqlString(searchInfo.getRigaiKankeisha()) + "' ) ");
			}
		}
		//�����ԍ���ǉ�	2005/11/2
		if(!StringUtil.isBlank(searchInfo.getSeiriNo())) {
			query.append(" AND B.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
		}
				  			
		//����ID(�ĐR���p)
		if(!StringUtil.isBlank(searchInfo.getJigyoId())) {
			query.append(" AND A.JIGYO_ID = '" + EscapeUtil.toSqlString(searchInfo.getJigyoId()) + "' ");
		}

		//���Ƌ敪(�ĐR���p)
		if(!StringUtil.isBlank(searchInfo.getJigyoKubun())) {
			query.append(" AND A.JIGYO_KUBUN = " + EscapeUtil.toSqlString(searchInfo.getJigyoKubun()));
		}

		//2005.11.08 �����s���̃o�O(ORA-03113)�̂��ߐR�����ԍ����Ō�Ɉړ�
		if(!StringUtil.isBlank(searchInfo.getShinsainNo())) {		//�R�����ԍ�
			query.append(" AND A.SHINSAIN_NO = '" + EscapeUtil.toSqlString(searchInfo.getShinsainNo()) + "' ");
		}

		if(orderFlg) {
			//�\�[�g���i�R�����ԍ��A����ID�A�\���ԍ��̏����j
			query.append(" ORDER BY A.SHINSAIN_NO, A.JIGYO_ID, B.UKETUKE_NO ");
		}

		return query.toString();
	}
	


//	/**
//	 * �R���������ꗗ�̕\�����鎖��SQL�̖₢���킹�����𐶐�����B
//	 * ���������₢���킹�����͑������̕�����̌��Ɍ��������B
//	 * @param select    
//	 * @param userInfo  
//	 * @param searchInfo
//	 * @return
//	 */
//	protected static String getHyojiQueryString(String select, UserInfo userInfo, ShinsaJokyoSearchInfo searchInfo) {
//
//		//-----���������I�u�W�F�N�g�̓��e��SQL�Ɍ������Ă���-----
//		StringBuffer query = new StringBuffer(select);
//
//		if(!StringUtil.isBlank(searchInfo.getNendo())) {							//�N�x
//			query.append(" AND B.NENDO = " + EscapeUtil.toSqlString(searchInfo.getNendo()));
//		}
//		if(!StringUtil.isBlank(searchInfo.getKaisu())) {							//��
//			query.append(" AND  B.KAISU = " + EscapeUtil.toSqlString(searchInfo.getKaisu()));
//		}
//		if(!searchInfo.getValues().isEmpty()) {							//���Ɩ��i���ƃR�[�h�j
//			query.append(" AND SUBSTR(A.JIGYO_ID,3,5) IN (")
//				 .append(StringUtil.changeIterator2CSV(searchInfo.getValues().iterator(), false))
//				 .append(")");
//		}
//		//�Ɩ��S���҂̒S�����Ƌ敪���擾
//		Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(userInfo);
//		if(!shinsaTaishoSet.isEmpty()) {
//			query.append(" AND B.JIGYO_KUBUN IN (")
//				 .append(StringUtil.changeIterator2CSV(shinsaTaishoSet.iterator(), false))
//				 .append(")");
//		}
//		//�n���̋敪�Ɨ��̗̂�������������
//		if(!StringUtil.isBlank(searchInfo.getKeiName())) {						//�n���̋敪
//			query.append(" AND (B.KEI_NAME LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName())
//				+ "%' OR B.KEI_NAME_RYAKU LIKE '%" + EscapeUtil.toSqlString(searchInfo.getKeiName()) + "%')");
//		}
//		if(!StringUtil.isBlank(searchInfo.getShinsaJokyo())
//				&& !searchInfo.getShinsaJokyo().equals("2")) {												//�R����
//			query.append(" AND A.SHINSA_JOKYO = '" + EscapeUtil.toSqlString(searchInfo.getShinsaJokyo()) + "'");
//		}
//		if(!StringUtil.isBlank(searchInfo.getRigaiKankeisha())) {
//			if("1".equals(searchInfo.getRigaiKankeisha()) ){
//				query.append(" AND A.RIGAI = '" + EscapeUtil.toSqlString(searchInfo.getRigaiKankeisha()) + "'");
//			}else{
//				query.append(" AND ( A.RIGAI IS NULL OR A.RIGAI = '" + EscapeUtil.toSqlString(searchInfo.getRigaiKankeisha()) + "' ) ");
//			}
//		}
//		if(!StringUtil.isBlank(searchInfo.getSeiriNo())) {				//�����ԍ�
//			query.append(" AND B.JURI_SEIRI_NO LIKE '%" + EscapeUtil.toSqlString(searchInfo.getSeiriNo()) + "%'");
//		}
//	  	
//		return query.toString();
//	}
	
	
	
}
