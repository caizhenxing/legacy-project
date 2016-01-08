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
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.IJigyoKanriMaintenance;
import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.ILabelKubun;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.JigyoKanriInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterLabelInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterSogoHyokaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinsaKekkaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.FileIOException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShoruiKanriSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.DateUtil;
//import jp.go.jsps.kaken.util.EscapeUtil;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.go.jsps.kaken.web.common.LabelValueBean;


/**
 * �R�����ʏ��Ǘ����s���N���X.<br /><br />
 * 
 * �g�p���Ă���e�[�u��<br /><br />
 * �@�E�R�����ʃe�[�u��:SHINSAKEKKA<br />
 * �@�@�@�R��������U�茋�ʏ��Ɛ\�����̐R�����ʂ��Ǘ�����B<br /><br />
 * �@�E�\���f�[�^�Ǘ��e�[�u��:SHINSEIDATAKANRI<br />
 * �@�@�@�\���f�[�^�̏����Ǘ�����B<br /><br />
 * �@�E���Ə��Ǘ��e�[�u��:JIGYOKNARI<br />
 * �@�@�@���Ƃ̊�{�����Ǘ�<br /><br />
 * �@�E���ފǗ��e�[�u��:SHORUIKANRI<br />
 * �@�@�@���Ɩ��̏��ނ��Ǘ�����B<br /><br />
 * �@�E���x���}�X�^:MASTER_LABEL<br />
 * �@�@�@�v���_�E�����̖��́A���̂��Ǘ�����B<br /><br />
 * �@�E�����]���}�X�^:MASTER_SOGOHYOKA<br />
 * �@�@�@�����]���̓_���z�����Ǘ�����B<br /><br />
 */
public class ShinsaKekkaMaintenance implements IShinsaKekkaMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/**
	 * �R�����ʃt�@�C���i�[�t�H���_.<br /><br />
	 * 
	 * �l��"${shinsei_path}/data/{0}/{1}/shinsa/{2}.{3}"
	 */
	private static final String SHINSEI_KEKKA_FOLDER = 
								ApplicationSettings.getString(ISettingKeys.SHINSEI_KEKKA_FOLDER);	
	
	/**
	 * �\�����폜�t���O�i�ʏ�j.<br /><br />
	 * 
	 * �l��"0"
	 */
	private static final String FLAG_APPLICATION_NOT_DELETE = IShinseiMaintenance.FLAG_APPLICATION_NOT_DELETE;
	
	/**
	 * �\�����폜�t���O�i�폜�ς݁j.<br /><br />
	 * 
	 * �l��"1"
	 */
	private static final String FLAG_APPLICATION_DELETE     = IShinseiMaintenance.FLAG_APPLICATION_DELETE;
	

	/**
	 * ���[���T�[�o�A�h���X.<br /><br />
	 * 
	 * �l��"localhost"
	 */
	private static final String SMTP_SERVER_ADDRESS = 
								ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS); 
	/**
	 * ���o�l�i���ꂵ�ĂP�j.<br /><br />
	 * 
	 * �l��ApplicationSettings.properties�ɐݒ�
	 */
	private static final String FROM_ADDRESS = 
								ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);
	/**
	 * �R���Ñ������܂ł̓��t.<br /><br />
	 * 
	 * �l��"3"
	 */
	private static final int DATE_BY_SHINSA_KIGEN = 
								ApplicationSettings.getInt(ISettingKeys.DATE_BY_SHINSA_KIGEN);
	/**
	 * ���[�����e�i�R�����ւ̐R���Ñ��j�u�����v.<br /><br />
	 */
	private static final String SUBJECT_SHINSEISHO_SHINSA_SAISOKU = 
								ApplicationSettings.getString(ISettingKeys.SUBJECT_SHINSEISHO_SHINSA_SAISOKU);
	/**
	 * ���[�����e�i�R�����ւ̐R���Ñ��j�u�{���v.<br /><br />
	 * 
	 * �l��"${shinsei_path}/settings/mail/shinseisho_shinsa_saisoku.txt"
	 */
	private static final String CONTENT_SHINSEISHO_SHINSA_SAISOKU = 
								ApplicationSettings.getString(ISettingKeys.CONTENT_SHINSEISHO_SHINSA_SAISOKU);
	
	/**
	 * ���[�����e�i�R�������R�����������Ƃ��j�u�����v.<br /><br />
	 */
	private static final String SUBJECT_SHINSAKEKKA_JURI_TSUCHI = 
								ApplicationSettings.getString(ISettingKeys.SUBJECT_SHINSAKEKKA_JURI_TSUCHI);

	/**
	 * ���[�����e�i�R�������R�����������Ƃ��j�u�{���v.<br /><br />
	 * 
	 * �l��"${shinsei_path}/settings/mail/shinsakekka_juri_tsuchi.txt"
	 */
	private static final String CONTENT_SHINSAKEKKA_JURI_TSUCHI = 
								ApplicationSettings.getString(ISettingKeys.CONTENT_SHINSAKEKKA_JURI_TSUCHI);
	
	/**
	 * �R���󋵁i�������j.<br /><br />
	 */
	public static final String SHINSAJOKYO_COMPLETE_YET = "0";	

	/**
	 * �R���󋵁i�����j.<br /><br />
	 */
	public static final String SHINSAJOKYO_COMPLETE = "1";
    
    /**
     * ���Q�֌W�Y���ۑ�.<br /><br />
     */
    public static final String RIEKISOHAN_FLAG = "riekiSohan";
    
    /**
     * �R���S���������.<br /><br />
     */
    public static final String SINNSA_FLAG = "sinnsa";
    
	
//2006-10-26 ���u�j �ǉ���������
	/**
	 * ���Q�֌W���͊����i�����j.<br /><br />
	 */
	public static final String NYURYOKUJOKYO_COMPLETE = "1";	
	
    /**
     * ���Q�֌W���͊����i�������j.<br /><br />
     */
    public static final String NYURYOKUJOKYO_COMPLETE_YET = "0";    
//2006-10-26 ���u�j �ǉ� �����܂�
	

//2006/05/12 �ǉ���������    
    /**
     * �����]�_(������).<br /><br />
     */
    public static final String SHINSAKEKKA_KEKKA_TEN_MI = "-1"; 
    
    /**
     * �����]�_(����).<br /><br />
     */
    public static final String SHINSAKEKKA_KEKKA_TEN_KANRYOU = "0";   								
//�c�@�ǉ������܂�
    
	/**
	 * ���O.<br /><br />
	 */
	protected static Log log = LogFactory.getLog(ShozokuMaintenance.class);
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsaKekkaMaintenance() {
		super();
	}
	
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------	
	/**
	 * �R�������̃`�F�b�N.<br /><br />
	 * 
	 * ���݂̓��t�����Y���Ƃ̐R���������߂��Ă��Ȃ����`�F�b�N����B<br />
	 * �R��������NULL�̏ꍇ�́ATRUE��Ԃ��B<br />
	 * ���ݓ��t�́AWAS����擾�B<br /><br />
	 * 
	 * �ȉ���SQL�����s���A���Y���Ƃ̎��Ə����擾����B<br />
	 * �i�o�C���h�ϐ���SQL�̉��̕\���Q�Ɓj<br /><br />
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td>
	 * <pre>
	 * SELECT
	 *     A.JIGYO_ID			-- ����ID
	 *     ,A.NENDO			-- �N�x
	 *     ,A.KAISU			-- ��
	 *     ,A.JIGYO_NAME			-- ���Ɩ�
	 *     ,A.JIGYO_KUBUN		-- ���Ƌ敪
	 *     ,A.SHINSA_KUBUN		-- �R���敪
	 *     ,A.TANTOKA_NAME		-- �Ɩ��S����
	 *     ,A.TANTOKAKARI		-- �Ɩ��S���W��
	 *     ,A.TOIAWASE_NAME		-- �₢���킹��S���Җ�
	 *     ,A.TOIAWASE_TEL		-- �₢���킹��d�b�ԍ�
	 *     ,A.TOIAWASE_EMAIL		-- �₢���킹��E-mail
	 *     ,A.UKETUKEKIKAN_START		-- �w�U��t���ԁi�J�n�j
	 *     ,A.UKETUKEKIKAN_END		-- �w�U��t���ԁi�I���j
	 *     ,A.SHINSAKIGEN		-- �R������
	 *     ,A.TENPU_NAME			-- �Y�t������
	 *     ,A.TENPU_WIN			-- �Y�t�t�@�C���i�[�t�H���_�iWin�j
	 *     ,A.TENPU_MAC			-- �Y�t�t�@�C���i�[�t�H���_�iMac�j
	 *     ,A.HYOKA_FILE_FLG		-- �]���p�t�@�C���L��
	 *     ,A.HYOKA_FILE			-- �]���p�t�@�C���i�[�t�H���_
	 *     ,A.KOKAI_FLG			-- ���J�t���O
	 *     ,A.KESSAI_NO			-- ���J���ٔԍ�
	 *     ,A.KOKAI_ID			-- ���J�m���ID
	 *     ,A.HOKAN_DATE			-- �f�[�^�ۊǓ�
	 *     ,A.YUKO_DATE			-- �ۊǗL������
	 *     ,A.BIKO			-- ���l
	 *     ,A.DEL_FLG			-- �폜�t���O
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
	 *     <tr bgcolor="#FFFFFF"><td>JIGYO_ID</td><td>������shinseiDataInfo�̕ϐ�jigyoId</td></tr>
	 * </table><br/><br/>
	 * 
	 * �擾�������Ə��̐R��������null�������ꍇ�Atrue��ԋp���Ċ����ƂȂ�B<br /><br />
	 * 
	 * �R��������null�ł͂Ȃ������ꍇ�A���݂̓��t�ƐR���������ׂ�B<br />
	 * �������߂��Ă�����false���A�߂��Ă��Ȃ����true��ԋp����B<br /><br />
	 * 
	 * @param userInfo
	 * @param shinseiDataInfo
	 * @return [false]�R���������߂��Ă���ꍇ [true]�R��������null���͐R���������߂��Ă��Ȃ��ꍇ
	 * @throws ApplicationException
	 */
	private boolean checkShinsaKigen(
			UserInfo userInfo, 
			ShinseiDataInfo shinseiDataInfo)
		throws ApplicationException
	{
		//-----���Y���Ƃ̎��Ə����擾����
		JigyoKanriPk pkInfo = new JigyoKanriPk(shinseiDataInfo.getJigyoId());
		JigyoKanriInfo jigyoKanriInfo = null;
		try{
			IJigyoKanriMaintenance jigyoMainte = new JigyoKanriMaintenance();
			jigyoKanriInfo = jigyoMainte.select(userInfo, pkInfo);
		} catch (ApplicationException e) {
			throw new ApplicationException(
				"���Ə��Ǘ��Ǘ��f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		}
		
		//�R�������̎擾
		if(jigyoKanriInfo.getShinsaKigen() == null){
			//�R��������null��������true
			return true;
		}
		
		DateUtil shinsaEnd = new DateUtil(jigyoKanriInfo.getShinsaKigen());
		DateUtil now = new DateUtil();
		
		//���ݓ��t�ƐR���������r
		boolean bFlag = true;
		int hi = now.getElapse(shinsaEnd);
		if(hi < 0){
			//�R���������߂��Ă���ꍇ
			bFlag = false;
		}
				
		return bFlag;		
		
	}
	
	//---------------------------------------------------------------------
	// implement IShinsaKekkaMaintenance
	//---------------------------------------------------------------------
	/**
	 * �R���Ώێ��Ə�������Page�̎擾.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s����B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.JIGYO_ID,
	 * 	A.SHINSAIN_NO,
	 * 	A.JIGYO_NAME,
	 * 	B.NENDO,
	 * 	B.KAISU,
	 * 	C.SHINSAKIGEN,
	 * 	C.SHINSAKIGEN_FLAG,
	 * 	COUNT(A.JIGYO_ID) COUNT,
	 * 	SUM(DECODE(A.SHINSA_JOKYO,1,1,0))
	 * 	SHINSA_JYOKYO_COUNT
	 * FROM(
	 * 	SELECT
	 * 		*
	 * 	FROM
	 * 		SHINSAKEKKA
	 * 	WHERE
	 * 		SHINSAIN_NO = ?				--�R�����ԍ�
	 * 		AND JIGYO_KUBUN = ?			--���Ƌ敪
	 * ) A
	 * INNER JOIN(
	 * 	SELECT
	 * 		*
	 * 	FROM
	 * 		SHINSEIDATAKANRI
	 * 	WHERE
	 * 		DEL_FLG=0
	 * 		AND JOKYO_ID IN('10','11')			--�R���󋵂�10����11�̂���
	 * ) B
	 * ON A.SYSTEM_NO = B.SYSTEM_NO
	 * INNER JOIN(
	 * 	SELECT
	 * 		JIGYO_ID,
	 * 		JIGYO_NAME,
	 * 		NENDO,
	 * 		KAISU,
	 * 		SHINSAKIGEN,
	 * 		DECODE(
	 * 			SIGN(TO_DATE(TO_CHAR(SHINSAKIGEN,'YYYY/MM/DD'),'YYYY/MM/DD')
	 * 			- TO_DATE(TO_CHAR(SYSDATE, 'YYYY/MM/DD'),'YYYY/MM/DD') )
	 * 			,0 ,'TRUE'			--���ݎ����Ɠ����ꍇ
	 * 			,1 ,'TRUE'			--���ݎ����̕����R���������O
	 * 			,-1,'FALSE'			--���ݎ����̕����R����������
	 * 		)
	 * 		SHINSAKIGEN_FLAG				--�R���������B�t���O
	 * 	FROM
	 * 		JIGYOKANRI
	 * ) C
	 * ON A.JIGYO_ID = C.JIGYO_ID
	 * 	WHERE
	 * 		A.SHINSA_JOKYO = '0'			--�R���󋵂��u0�F�������v�̏ꍇ
	 * 		OR(					--�܂���
	 * 			(C.SHINSAKIGEN_FLAG = 'TRUE'
	 * 			OR C.SHINSAKIGEN_FLAG IS NULL)	--�R���������܂��͐R�������Ȃ���
	 * 			AND				--����
	 * 			A.SHINSA_JOKYO = '1'		--�R���󋵂��u1�F�����v�̏ꍇ
	 * 		)
	 * 	GROUP BY
	 * 		A.JIGYO_ID
	 * 		,A.SHINSAIN_NO
	 * 		,A.JIGYO_NAME
	 * 		,B.NENDO
	 * 		,B.KAISU
	 * 		,C.SHINSAKIGEN
	 * 	ORDER BY
	 * 		A.JIGYO_ID				--����ID�̏���</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������userInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������userInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �@�E�R����10�c�ꎟ�R����<br/>
	 * �@�E�R����11�c�ꎟ�R������<br/><br/>
	 * 
	 * �擾�����R���Ώێ��Ə����A�񖼂��L�[��Map�Ɋi�[���AList�ɂ܂Ƃ߂���ŁAPage�ɃZ�b�g���ĕԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo SearchInfo
	 * @return �R���Ώێ��Ə�������Page
	 * @throws ApplicationException
	 */
	public Page searchShinsaJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException {
		
        String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();     //�R�����ԍ�         
        String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//���Ƌ敪
//2006/04/25 �ǉ���������
        Set shinsaiSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(jigyoKubun) ; //�R�����̎��Ƌ敪��Set
        String jigyoKubunTemp = (String)shinsaiSet.iterator().next();
        
        Set shinseiSet = JigyoKubunFilter.Convert2ShinseishoJigyoKubun(jigyoKubunTemp) ; //�\���̎��Ƌ敪��Set
        //CSV���擾
        String shinseiJigyoKubun =  StringUtil.changeIterator2CSV(shinseiSet.iterator(),true);
//�c�@�ǉ������܂�        
		
		//-----------------------
		// SQL���̍쐬
		//-----------------------
		String query = 
			"SELECT" 
			+ " A.JIGYO_ID,"
			+ " A.SHINSAIN_NO,"
			+ " A.JIGYO_NAME,"
//2006/04/18 �ǉ���������
			+ " A.JIGYO_KUBUN,"
//�c�@�ǉ������܂�	

//			2006/10/24 �Ո� �ǉ���������
//			+ " A.NYURYOKU_JOKYO,"
			+ " SUM( DECODE( NVL(A.NYURYOKU_JOKYO, '0') , 1 , 1 , 0 ) ) NYURYOKU_JOKYO_COUNT ," //���Q�֌W�p
//			2006/10/24 �Ո� �ǉ������܂�
			
			+ " B.NENDO,"
			+ " B.KAISU,"
			+ " C.SHINSAKIGEN,"
//          2006/10/24 �Ո� �ǉ���������
			+ " C.RIGAI_KIKAN_END,"             //���Q�֌W���͊���
//          2006/10/24 �Ո� �ǉ������܂�
			+ " C.SHINSAKIGEN_FLAG,"
			+ " COUNT(A.JIGYO_ID) COUNT,"
			+ " SUM( DECODE( A.SHINSA_JOKYO , 1 , 1 , 0 ) ) SHINSA_JYOKYO_COUNT "
			+ " FROM "
			+ "  (SELECT * FROM SHINSAKEKKA"
			+ "   WHERE SHINSAIN_NO = ?"		//�R�����ԍ�
//			+ "   AND JIGYO_KUBUN = ?"			//���Ƌ敪
//2006/04/24 �ǉ���������
            + "   AND JIGYO_KUBUN IN ("
            + shinseiJigyoKubun
            + "   )"  
//�c�@�ǉ������܂�              
//2006/04/23 �폜�������� �i2006/04/18�@�c�@�ǉ����܂����j
//			if (jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)) {
//				query = query + "   AND JIGYO_KUBUN = ?"; //���Ƌ敪
//			} else {
//				String jigyoKbns = StringUtil.changeArray2CSV(IJigyoKubun.SHINSA_KANOU_JIGYO_KUBUN, true);
//				query = query + "   AND JIGYO_KUBUN IN (" + jigyoKbns + ")"; //���Ƌ敪
//			}
//            query = query
//�c�@�폜�����܂�             
			+ "  ) A"
			+ " INNER JOIN "
			+ "  (SELECT * FROM SHINSEIDATAKANRI"
			+ "   WHERE DEL_FLG=0"
			+ "   AND JOKYO_ID IN('" 
			+ 	  StatusCode.STATUS_1st_SHINSATYU								//�\���󋵂�[10]	
			+ "', '" + StatusCode.STATUS_1st_SHINSA_KANRYO +  "')"			//�\���󋵂�[11]
			+ "   ) B"
			+ " ON A.SYSTEM_NO = B.SYSTEM_NO"
			+ " INNER JOIN" 
			+ "  (SELECT"
			+ "   JIGYO_ID,"
			+ "   JIGYO_NAME,"
			+ "   NENDO,"
			+ "   KAISU,"
			+ "   SHINSAKIGEN,"
			+ "   RIGAI_KIKAN_END,"
			+ "   DECODE"
			+ "   ("
			+ "    SIGN( TO_DATE(TO_CHAR(SHINSAKIGEN, 'YYYY/MM/DD'),'YYYY/MM/DD')  - TO_DATE(TO_CHAR(SYSDATE, 'YYYY/MM/DD'),'YYYY/MM/DD') )"
			+ "    ,0 , 'TRUE'"								//���ݎ����Ɠ����ꍇ
			+ "    ,1 , 'TRUE'"								//���ݎ����̕����R���������O
			+ "    ,-1, 'FALSE'"							//���ݎ����̕����R����������
			+ "   ) SHINSAKIGEN_FLAG "						//�R���������B�t���O
			+ "   FROM JIGYOKANRI"
			+ "   ) C"
			+ " ON A.JIGYO_ID = C.JIGYO_ID"
			+ " WHERE"
//			+ " NOT(C.SHINSAKIGEN_FLAG = 'FALSE'"	//�R�������߂��Ăā��R��������NULL�̂��FALSE�ȊO�Ƀq�b�g���Ă���Ȃ�
//			+ " AND"								//����
//			+ " A.SHINSA_JOKYO = '1')"				//�R���󋵂��u1�F�����v�ł͂Ȃ��ꍇ
			+ " A.SHINSA_JOKYO = '0'"				//�R���󋵂��u0�F�������v�̏ꍇ
			+ " OR"										//�܂���
			+ " ("
			+ " (C.SHINSAKIGEN_FLAG = 'TRUE' OR C.SHINSAKIGEN_FLAG IS NULL)"		//�R���������܂��͐R�������Ȃ���
			+ " AND"									//����
			+ " A.SHINSA_JOKYO = '1')"			//�R���󋵂��u1�F�����v�̏ꍇ
			+ " GROUP BY A.JIGYO_ID,A.SHINSAIN_NO,A.JIGYO_NAME,B.NENDO,B.KAISU,C.SHINSAKIGEN,A.JIGYO_KUBUN,C.RIGAI_KIKAN_END "
			+ " ORDER BY A.JIGYO_ID";	//����ID�̏���		
			
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
            
            return SelectUtil.selectPageInfo(connection, searchInfo, query, new String[]{ shinsainNo });
            
//2006/04/24 �폜��������i2006/04/18�@�ǉ����܂����j            
//            if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
//            	return SelectUtil.selectPageInfo(connection, searchInfo, query, new String[]{shinsainNo, jigyoKubun});
//            } else {  
//            	return SelectUtil.selectPageInfo(connection, searchInfo, query, new String[]{ shinsainNo });
//            }
//�c�@�폜�����܂�			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�R���Ώێ��ƈꗗ�f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * ���ޏ��̎擾.<br /><br />
	 * 
	 * ���N���X�̃��\�b�hSearchShinsaJigyo(UserInfo userInfo, SearchInfo searchInfo)���Ăяo���āA
	 * Page���擾����B���̌�A�ȉ���SQL���𔭍s���āA���ޏ���List���擾����B<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	SHORUIKANRI A
	 * WHERE
	 * 	DEL_FLG = 0
	 * 
	 * 		<b><span style="color:#002288">�|�|���I���������|�|</span></b>
	 * 
	 * ORDER BY
	 * 	SYSTEM_NO</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����ID</td><td>JigyoId</td><td>AND A.JIGYO_ID ='����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�Ώێ�ID</td><td>TaishoId</td><td>4(�R����������킷)</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>SystemNo</td><td>AND A.SYSYEM_NO ='�V�X�e����t�ԍ�'</td></tr>
	 * </table><br/>
	 * 
	 * �擾����List��Map�Ɋi�[���A�����ArrayList�ɉ�����B<br/><br/>
	 * 
	 * ����SQL�𔭍s���鏈�����A�擾����Page�̗v�f���Ȃ��Ȃ�܂ŌJ��Ԃ��B
	 * �Ō�ɁA����ArrayList��Page�Ɋi�[���āA�ԋp����B<br/><br/>
	 * 
	 * @param userInfo UserInfo
	 * @param searchInfo SearchInfo
	 * @return ���ޏ�������Page
	 * @throws ApplicationException
	 */
	public Page getShinsaJigyo(UserInfo userInfo, SearchInfo searchInfo) throws ApplicationException {

		Page page = searchShinsaJigyo(userInfo, searchInfo);
		
		List newList = new ArrayList();
		
		Iterator iterator = (page.getList()).iterator();
		while(iterator.hasNext()){
			Map map = (Map)iterator.next();
			String jigyoid = (String) map.get("JIGYO_ID");
			JigyoKanriMaintenance maintenance = new JigyoKanriMaintenance();
			ShoruiKanriSearchInfo shoruiInfo = new ShoruiKanriSearchInfo();
			shoruiInfo.setJigyoId(jigyoid);
			shoruiInfo.setTaishoId(ITaishoId.SHINSAIN);
			List list = maintenance.search(userInfo, shoruiInfo);
			map.put("SHORUI_INFO", list);
			newList.add(map);
		}
		
		page.setList(newList);
		
		return page;
	}

	/**
	 * �Y�t�t�@�C��(�]���t�@�C��)�̎擾.<br /><br />
	 * 
	 * �ȉ���SQL�����Y�t�t�@�C���p�X���擾���A�t�@�C����ǂݍ���Ńt�@�C�����\�[�X��ԋp����B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 * 	,A.UKETUKE_NO			--�\���ԍ�
	 * 	,A.SHINSAIN_NO			--�R�����ԍ�
	 * 	,A.JIGYO_KUBUN			--���Ƌ敪
	 * 	,A.SEQ_NO				--�V�[�P���X�ԍ�
	 * 	,A.SHINSA_KUBUN			--�R���敪
	 * 	,A.SHINSAIN_NAME_KANJI_SEI		--�R�������i�����|���j
	 * 	,A.SHINSAIN_NAME_KANJI_MEI		--�R�������i�����|���j
	 * 	,A.NAME_KANA_SEI			--�R�������i�t���K�i�|���j
	 * 	,A.NAME_KANA_MEI			--�R�������i�t���K�i�|���j
	 * 	,A.SHOZOKU_NAME			--�R���������@�֖�
	 * 	,A.BUKYOKU_NAME			--�R�������ǖ�
	 * 	,A.SHOKUSHU_NAME			--�R�����E��
	 * 	,A.JIGYO_ID			--����ID
	 * 	,A.JIGYO_NAME			--���Ɩ�
	 * 	,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 * 	,A.EDA_NO				--�}��				
	 * 	,A.CHECKDIGIT			--�`�F�b�N�f�W�b�g
	 * 	,A.KEKKA_ABC			--�����]���iABC�j
	 * 	,A.KEKKA_TEN			--�����]���i�_���j
	 * 	,A.COMMENT1			--�R�����g1			
	 * 	,A.COMMENT2			--�R�����g2
	 * 	,A.COMMENT3			--�R�����g3
	 * 	,A.COMMENT4			--�R�����g4
	 * 	,A.COMMENT5			--�R�����g5
	 * 	,A.COMMENT6			--�R�����g6
	 * 	,A.KENKYUNAIYO			--�������e
	 * 	,A.KENKYUKEIKAKU			--�����v��		
	 * 	,A.TEKISETSU_KAIGAI		--�K�ؐ�-�C�O
	 * 	,A.TEKISETSU_KENKYU1		--�K�ؐ�-�����i1�j
	 * 	,A.TEKISETSU			--�K�ؐ�			
	 * 	,A.DATO				--�Ó���
	 * 	,A.SHINSEISHA			--������\��
	 * 	,A.KENKYUBUNTANSHA			--�������S��
	 * 	,A.HITOGENOMU			--�q�g�Q�m��
	 * 	,A.TOKUTEI			--������
	 * 	,A.HITOES				--�q�gES�זE
	 * 	,A.KUMIKAE			--��`�q�g��������
	 * 	,A.CHIRYO				--��`�q���×Տ�����			
	 * 	,A.EKIGAKU			--�u�w����
	 * 	,A.COMMENTS			--�R�����g
	 * 	,A.TENPU_PATH			--�Y�t�t�@�C���i�[�p�X			
	 * 	,DECODE(
	 * 		NVL(A.TENPU_PATH,'null') 
	 * 		,'null','FALSE'		--�Y�t�t�@�C���i�[�p�X��NULL
	 * 		,      'TRUE'		--�Y�t�t�@�C���i�[�p�X��NULL�ȊO
	 * 	)TENPU_FLG			--�Y�t�t�@�C���i�[�t���O
	 * 	,A.SHINSA_JOKYO			--�R����
	 * 	,A.BIKO				--���l
	 * FROM
	 * 	SHINSAKEKKA A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * 	AND SHINSAIN_NO = ?
	 * 	AND JIGYO_KUBUN = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>������downloadPk�̕ϐ�SystemNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������downloadPk�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������downloadPk�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l����A�Y�t�t�@�C���i�[�p�X�����o���B
	 * ���̒l��null���͋󕶎��񂾂����ꍇ�ɂ͗�O��throw����B<br /><br />
	 * 
	 * �p�X�̒l�̃t�@�C����ǂݍ��݁A�t�@�C�����\�[�X��ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param downloadPk
	 * @return FileResource
	 * @throws ApplicationException
	 */
	public FileResource getHyokaFileRes(UserInfo userInfo, ShinsaKekkaPk downloadPk) throws ApplicationException {

		FileResource fileRes = null;
			
				
		//DB�R�l�N�V�����̎擾
		Connection connection = null;		
		try{
			
			ShinsaKekkaInfo shinsaKekkaInfo = null;
			try{
				connection = DatabaseUtil.getConnection();
				ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection, downloadPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}

			String filePath = shinsaKekkaInfo.getTenpuPath();
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
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		
		return fileRes;
	}

	/**
	 * Map�̎擾.<br /><br />
	 * 
	 * Map���擾���鎩�N���X�̃��\�b�h�A<br />
	 * selectShinsaKekkaTantoList(userInfo,jigyoId,null,searchInfo)<br />
	 * ���Ăяo���A�擾����Map�����̂܂ܕԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param jigyoId String
	 * @param searchInfo SearchInfo
	 * @return Map
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map selectShinsaKekkaTantoList(UserInfo userInfo, String jigyoId, SearchInfo searchInfo) throws NoDataFoundException, ApplicationException {

		return selectShinsaKekkaTantoList(userInfo,
											jigyoId,
											null,
                                            null,//2006/10/27�@�c�@�ǉ��@���̃��b�\�h�͌Ăяo���Ȃ�����Anull��ǉ����܂���
                                            null,//2006/10/27�@�c�@�ǉ��@���̃��b�\�h�͌Ăяo���Ȃ�����Anull��ǉ����܂���
											searchInfo);
	}

	/**
	 * Map�̎擾.<br /><br />
	 * 
	 * 1.�ȉ���SQL���𔭍s���āA�R���S���\���ꗗ�y�[�W�����擾����B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO,				--�V�X�e����t�ԍ�
	 * 	A.UKETUKE_NO,				--�\���ԍ�
	 * 	A.SHINSAIN_NO,				--�R�����ԍ�
	 * 	A.JIGYO_ID,				--����ID
	 * 	SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD,	--���ƃR�[�h
	 * 	A.JIGYO_NAME,				--���Ɩ�
	 * 	A.BUNKASAIMOKU_CD,			--���ȍזڃR�[�h
	 * 	A.KEKKA_ABC,				--�����]���iABC�j
	 * 	A.KEKKA_TEN,				--�����]���i�_���j
	 * 	A.KENKYUNAIYO,				--�������e
	 * 	A.KENKYUKEIKAKU,			--�����v��
	 * 	A.TEKISETSU_KAIGAI,			--�K�ؐ�-�C�O
	 * 	A.TEKISETSU_KENKYU1,		--�K�ؐ�-����(1)
	 * 	A.TEKISETSU,				--�K�ؐ�
	 * 	A.DATO,					--�Ó���
	 * 	A.COMMENTS,				--�R�����g
	 * 	A.SHINSA_JOKYO,				--�R����
	 * 	B.SHINSEISHA_ID,			--�\����ID
	 * 	B.NAME_KANJI_SEI,			--�\���Ҏ����i������-���j
	 * 	B.NAME_KANJI_MEI,			--�\���Ҏ����i������-���j
	 * 	B.SHOZOKU_CD,				--�����@�փR�[�h
	 * 	B.SHOZOKU_NAME_RYAKU,		--�����@�֖��i���́j
	 * 	B.BUKYOKU_NAME_RYAKU,		--���ǖ��i���́j
	 * 	B.SHOKUSHU_NAME_RYAKU,		--�E���i���́j
	 * 	B.KADAI_NAME_KANJI,		--�����ۑ薼���̓Z�~�i�[��(�a���j
	 * 	B.SAIMOKU_NAME,			--�זږ�
	 * 	DECODE(
	 * 		NVL(B.SUISENSHO_PATH,'null') 
	 * 		,'null','FALSE'			--���E���p�X��NULL�̂Ƃ�
	 * 		,      'TRUE'			--���E���p�X��NULL�ȊO�̂Ƃ�
	 * 	) SUISENSHO_FLG, 			--���E���o�^�t���O
	 * 	B.BUNKATSU_NO,				--�����ԍ�
	 * 	B.BUNTANKIN_FLG,			--���S���̗L��
	 * 	B.KAIGAIBUNYA_CD,			--�C�O����R�[�h
	 * 	B.KAIGAIBUNYA_NAME_RYAKU,	--�C�O���얼�i���́j
	 * 	B.SHINSEI_FLG_NO,		--�����v��ŏI�N�x�O�N�x�̉���
	 * 	B.JOKYO_ID,				--�\����ID
	 * 	C.SHINSAKIGEN,			--�R������
	 * 	C.NENDO,				--�N�x
	 * 	C.KAISU,				--��
	 * 	DECODE(
	 * 		SIGN( TO_DATE(TO_CHAR(C.SHINSAKIGEN,'YYYY/MM/DD'),
	 * 			'YYYY/MM/DD')
	 * 			- TO_DATE(TO_CHAR(SYSDATE,'YYYY/MM/DD'),
	 * 			'YYYY/MM/DD')
	 * 		)
	 * 		,0 ,'TRUE'			--���ݎ����Ɠ����ꍇ
	 * 		,1 ,'TRUE'			--���ݎ����̕����R���������O
	 * 		,-1,'FALSE'			--���ݎ����̕����R����������
	 * 	) SHINSAKIGEN_FLAG				--�R���������B�t���O
	 * FROM(
	 * 	SELECT
	 * 		*
	 * 	FROM
	 * 		SHINSAKEKKA
	 * 	WHERE 
	 * 	SHINSAIN_NO = ?				--���Y�R�����ԍ�
	 * 	AND JIGYO_KUBUN = ?			--���Y���Ƌ敪
	 * 	AND JIGYO_ID = ?			--���Y����ID
	 * 	) A,
	 * 	(
	 * 	SELECT
	 * 		*
	 * 	FROM
	 * 		SHINSEIDATAKANRI
	 * 	WHERE
	 * 		(JOKYO_ID = '10' OR JOKYO_ID = '11')
	 * 			--���Y����ID��10�܂���11�̂���
	 * 		AND DEL_FLG = '0'			--�폜����ĂȂ�����
	 * 	) B,
	 * 	JIGYOKANRI C
	 * WHERE
	 * 	A.SYSTEM_NO = B.SYSTEM_NO
	 * 	AND A.JIGYO_ID = C.JIGYO_ID
	 * 
	 * 	<b><span style="color:#002288">�|�|���I��������1�|�|</span></b>
	 * 
	 * ORDER BY
	 * 
	 * 	<b><span style="color:#002288">�|�|���I��������2�|�|</span></b>
	 * 
	 * 	A.UKETUKE_NO</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������userInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������userInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>������jigyoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B<br/><br/>
	 * 
	 * 
	 * <b><span style="color:#002288">���I��������1</span></b><br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>����</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����]���i�_���j</td><td>kekkaTen</td><td>-1(��1)</td><td>AND A.KEKKA_TEN IS NULL</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����]���i�_���j</td><td>kekkaTen</td><td>5�`-</td><td>AND A.KEKKA_TEN = ?</td></tr>
	 * </table><br />
	 * 
	 * 
	 * <b><span style="color:#002288">���I��������2</span></b><br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>����</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>jigyoKubun</td><td>4(��2)</td><td>A.BUNKASAIMOKU_CD,</td></tr>
	 * </table><br />
	 * 
	 * �@�E(��1)�����͂�����<br />
	 * �@�E(��2)��Ղ�����<br /><br />
	 * 
	 * 
	 * 
	 * 
	 * 2.�ȉ���SQL���𔭍s���āA�v���_�E���p�̃��x����������List���擾����B�擾����̂́A<br />
	 * �@�E�K�ؐ�-�C�O�@�@�@(TEKISETSU_KAIGAI)<br />
	 * �@�E�K�ؐ�-���� (1)�@(TEKISETSU_KENKYU1)<br />
	 * �@�E�K�ؐ��@�@�@�@�@�@(TEKISETSU)<br />
	 * �@�E�Ó����@�@�@�@�@�@(DATO)<br />
	 * �̂S��ނȂ̂ŁASQL���͂S�񔭍s����B()�̒l�́A���ꂼ��̃��x���敪�̒l�B<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.ATAI
	 * 	, A.NAME
	 * 	, A.RYAKU
	 * 	, A.SORT
	 * 	, A.BIKO
	 * FROM
	 * 	MASTER_LABEL A
	 * WHERE
	 * 	A.LABEL_KUBUN = ?
	 * 	AND A.SORT != 0
	 * ORDER BY
	 * 	SORT</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>���x���敪</td><td>�O�q�������x���敪�̒l���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 3.(1)1�Ŏ擾����Page����List�����o���B<br /><br />
	 * 
	 * �@(2)List����Map���炻�ꂼ��S�̒l<br />
	 * �@�@�@�E�K�ؐ�-�C�O<br />
	 * �@�@�@�E�K�ؐ�-���� (1)<br />
	 * �@�@�@�E�K�ؐ�<br />
	 * �@�@�@�E�Ó���<br />
	 * �@�@���擾����B<br /><br />
	 * 
	 * �@(3)���̒l��p���āA2�Ŏ擾����List���烉�x�������A���N���X�̃��\�b�hgetLabelName()���Ăяo���Ď��o���B<br /><br />
	 * 
	 * �@(4)���̃��x�����̒l���A��قǂ�Map�Ɋi�[����B<br /><br />
	 * 
	 * �@(5)(1)�Ŏ��o����List����A(2)�Ŏ��o����Map���폜���A(4)��Map�������Ɋi�[����B<br />
	 * �@�@(���x����������Map�ŏ㏑������)<br /><br />
	 * 
	 * �@(6)(2)�`(5)���AMap�̐������J��Ԃ��B<br /><br /><br />
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 4.�\���󋵂̊m�F<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)		--�\���ԍ�
	 * FROM
	 * 	SHINSAKEKKA A,
	 * 	(
	 * 	SELECT
	 * 		SYSTEM_NO
	 * 	FROM
	 * 		SHINSEIDATAKANRI
	 * 	WHERE
	 * 		DEL_FLG=0
	 * 		AND (JOKYO_ID = '10' OR JOKYO_ID = '11')
	 * 
	 * 	<b><span style="color:#002288">�|�|���I��������1�|�|</span></b>
	 * 
	 * 	) B
	 * WHERE
	 * 	A.SYSTEM_NO=B.SYSTEM_NO
	 * 
	 * 	<b><span style="color:#002288">�|�|���I��������2�|�|</span></b>
	 * 
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B<br/><br/>
	 * 
	 * 
	 * 
	 * <b><span style="color:#002288">���I��������1</span></b><br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>systemNo</td><td>AND SYSTEM_NO= ?</td></tr>
	 * </table><br/>
	 * 
	 * (��)�V�X�e���ԍ��ɂ́Anull��^���Ă���B <br/><br/>
	 * 
	 * 
	 * <b><span style="color:#002288">���I��������2</span></b><br/>
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>shinsainNo</td><td>AND A.SHINSAIN_NO ='�R�����ԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>jigyoKubun</td><td>AND A.JIGYO_KUBUN = '���Ƌ敪'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoId</td><td>AND A.JIGYO_ID = '����ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�R����</td><td>shinsaJokyo</td><td>AND A.SHINSA_JOKYO= '�R����'</td></tr>
	 * </table><br/>
	 * 
	 * (��)�R���󋵂ɂ́A0��^���Ă���B <br/><br/>
	 * 
	 * �R���󋵂��u0:�������v�̃f�[�^���Ȃ�������A
	 * �R�������t���O��String"<b>shinsaCompleteFlg</b>"��"TRUE"����������B
	 * �f�[�^�������"FALSE"����������B<br /><br /><br />
	 * 
	 * 
	 * 
	 * 5.Map�ɒl���i�[���Ă����B<br /><br />
	 * 
	 * HashMap�̃I�u�W�F�N�g"map"���쐬���A�ȉ��̒l���i�[����B<br /><br />
	 * �@�E3�ōX�V����Page�@�@�c�L�[��"key_shinsatanto_list"<br />
	 * �@�E4�Ŏ擾����String"<b>shinsaCompleteFlg</b>"�@�@�@�c�L�[��"key_shinsacomplete_flg"<br /><br />
	 * 
	 * ����"map"��ԋp���Ċ����ƂȂ邪�A
	 * ���Ƌ敪���u4:��Ձv�ł���ꍇ�̂݁A
	 * �����]�_��List��"map"�Ɋi�[����B<br /><br />
	 * 
	 * 
	 * �܂��A�ȉ���SQL���𔭍s���āA�����]�_���X�g���擾����B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.KEKKA_TEN			--�����]���i�_���j
	 * 	,COUNT(*) COUNT			--����
	 * FROM
	 * 	SHINSAKEKKA A,
	 * 	(
	 * 	SELECT
	 * 		*
	 * 	FROM
	 * 		SHINSEIDATAKANRI
	 * 	WHERE(
	 * 		JOKYO_ID = '10'	OR JOKYO_ID = '11')
	 * 		AND DEL_FLG = '0'		--�폜����ĂȂ�����
	 * 	) B
	 * WHERE
	 * 	A.SYSTEM_NO = B.SYSTEM_NO
	 * 	AND A.SHINSAIN_NO = ?
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.JIGYO_ID = ?
	 * GROUP BY
	 * 	A.KEKKA_TEN
	 * ORDER BY
	 * 	NVL(REPLACE(A.KEKKA_TEN, '-', '0'), '-1') DESC</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������userInfo�̕ϐ�shinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������userInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>������jigyoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾����List���ɂ́AMap�ɁA�e�L�[��kekkaTen��count�������Ă���B<br />
	 * �����ʂ�Map"hyotenMap"�ɃL�[�ckekkaTen�A�l�ccount�Ŋi�[���Ȃ����B<br />
	 * ���̍�Ƃ��AList����Map�̐������J��Ԃ��B<br /><br />
	 * 
	 * �Ō�ɁA���ׂĂ̌������L�[��"0"��"hyotenMap"�Ɋi�[����B<br />
	 * ����"hyotenMap"���L�["key_sogohyoten_list"�ɂ�"map"�Ɋi�[���A�ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param jigyoId String
	 * @param kekkaTen String
     * @param countKbn String
     * @param rigai String
	 * @param searchInfo SearchInfo
	 * @return Map
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Map selectShinsaKekkaTantoList(
			UserInfo userInfo,
			String jigyoId,
			String kekkaTen,
            String countKbn,
            String rigai,
			SearchInfo searchInfo)
		throws NoDataFoundException, ApplicationException {

		String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();		//�R�����ԍ�
		String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//���Ƌ敪
		
		//DB�R�l�N�V�����̎擾
		Connection connection = null;	
		try{
			connection = DatabaseUtil.getConnection();
			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);			
			//---�R���S���\���ꗗ�y�[�W���
			Page pageInfo = null;
			try {
				pageInfo = dao.selectShinsaKekkaTantoList(
											connection, 
											shinsainNo,
											jigyoKubun,
											jigyoId,
											kekkaTen,
                                            rigai,
											searchInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�R�����ʃf�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			} catch (NoDataFoundException e){
				//0���̃y�[�W�I�u�W�F�N�g�𐶐�
				pageInfo = Page.EMPTY_PAGE;			
			}

			//���x����
			List tekisetsuKaigaiList = null;		//�K�ؐ�-�C�O
			List tekisetsuKenkyu1List = null;		//�K�ؐ�-�����i1�j
			List tekisetsuList = null;				//�K�ؐ�			
			List datoList = null;					//�Ó���
			List jinkenList = null;				    //�l���̕ی�E�@�ߓ��̏���	
			List buntankinList = null;				//���S���z��
			
			
			try{
				String[] labelKubun = new String[]{ILabelKubun.TEKISETSU_KAIGAI,
													ILabelKubun.TEKISETSU_KENKYU1,
													ILabelKubun.TEKISETSU,
													ILabelKubun.DATO,
													ILabelKubun.JINKEN,
													ILabelKubun.BUNTANKIN};
				List bothList = new LabelValueMaintenance().getLabelList(labelKubun);	//4�̃��x�����X�g
				tekisetsuKaigaiList = (List)bothList.get(0);		
				tekisetsuKenkyu1List = (List)bothList.get(1);		
				tekisetsuList = (List)bothList.get(2);
				datoList = (List)bothList.get(3);				
				jinkenList = (List)bothList.get(4);
				buntankinList = (List)bothList.get(5);	
							
			}catch(ApplicationException e){
				throw new ApplicationException(
					"���x���}�X�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
									
			List list = pageInfo.getList();
			for(int i = 0;i< list.size();i ++){
				Map lineMap = (Map)list.get(i);
				//�l���擾
				String tekisetsuKaigaiValue = (String) lineMap.get("TEKISETSU_KAIGAI");		//�K�ؐ�-�C�O
				String tekisetsuKenkyu1Value = (String) lineMap.get("TEKISETSU_KENKYU1");	//�K�ؐ�-�����i1�j
				String tekisetsuValue = (String) lineMap.get("TEKISETSU");					//�K�ؐ�
				String datoValue = (String) lineMap.get("DATO");							//�Ó���
				String jinkenValue = (String) lineMap.get("JINKEN");						//�l���̕ی�E�@�ߓ��̏���	
				String buntankinValue = (String) lineMap.get("BUNTANKIN");					//���S���z��
				
				//���x�������擾
				String tekisetsuKaigaiLabel = getLabelName(tekisetsuKaigaiList, tekisetsuKaigaiValue);
				String tekisetsuKenkyu1Label = getLabelName(tekisetsuKenkyu1List, tekisetsuKenkyu1Value);
				String tekisetsuLabel =  getLabelName(tekisetsuList, tekisetsuValue);
				String datoLabel = getLabelName(datoList, datoValue);
				String jinkenLabel = getLabelName(jinkenList, jinkenValue);
				String buntankinLabel = getLabelName(buntankinList, buntankinValue);
				
				//���x�������Z�b�g
				lineMap.put("TEKISETSU_KAIGAI_LABEL", tekisetsuKaigaiLabel);
				lineMap.put("TEKISETSU_KENKYU1_LABEL", tekisetsuKenkyu1Label);
				lineMap.put("TEKISETSU_LABEL", tekisetsuLabel);
				lineMap.put("DATO_LABEL", datoLabel);
				lineMap.put("JINKEN_LABEL",jinkenLabel);
				lineMap.put("BUNTANKIN_LABEL", buntankinLabel);
				
				//���X�g����폜���Ēǉ����Ȃ���
				list.remove(i);
				list.add(i, lineMap);
				
			}
			
//2006/10/27 �c�@�C����������            
//			//---�R���󋵂��u0:�������v�̃f�[�^�����邩�ǂ������m�F
//			int count = 0;
//			try {
//				count = dao.countShinsaKekkaInfo(
//										connection,
//										shinsainNo,
//										jigyoKubun,
//										null,
//										jigyoId,
//										ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET									,null
//										);
//			} catch (DataAccessException e) {
//				throw new ApplicationException(
//					"�R�����ʃf�[�^��������DB�G���[���������܂����B",
//					new ErrorInfo("errors.4004"),
//					e);
//			}
//						
//			//�R���󋵂��u0:�������v�̃f�[�^���Ȃ�������TRUE��Ԃ�
//			String shinsaCompleteFlg = "FALSE";
//			if(count == 0){
//				shinsaCompleteFlg = "TRUE";	
//			}

			int count = 0;
			try {
                //---�R���󋵂��u0:�������v�̃f�[�^�����邩�ǂ������m�F
                if (SINNSA_FLAG.equals(countKbn)) {
                    count = dao.countShinsaKekkaInfo(connection, shinsainNo,
                            jigyoKubun, null, jigyoId,
                            ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET,
                            null);
                //---���͏󋵂��u0:�������v�̃f�[�^�����邩�ǂ������m�F    
                } else if (RIEKISOHAN_FLAG.equals(countKbn)) {
                    count = dao.countShinsaKekkaInfo(connection, shinsainNo,
                            jigyoKubun, null, jigyoId, null,
                            ShinsaKekkaMaintenance.NYURYOKUJOKYO_COMPLETE_YET);
                }
            } catch (DataAccessException e) {
                throw new ApplicationException("�R�����ʃf�[�^��������DB�G���[���������܂����B",
                        new ErrorInfo("errors.4004"), e);
            }
                        
            String shinsaCompleteFlg = "FALSE";
            String nyuryokuCompleteFlg = "FALSE";
            if (SINNSA_FLAG.equals(countKbn)) {
                //�R���󋵂��u0:�������v�̃f�[�^���Ȃ�������TRUE��Ԃ�
                if(count == 0){
                    shinsaCompleteFlg = "TRUE"; 
                }
            } else if (RIEKISOHAN_FLAG.equals(countKbn)) {
                //���͏󋵂��u0:�������v�̃f�[�^���Ȃ�������TRUE��Ԃ�
                if(count == 0){
                    nyuryokuCompleteFlg = "TRUE"; 
                }               
            }

			// �߂�l�}�b�v
			Map map = new HashMap();	
			// �R���S�����ꗗ�i�ꗗ�f�[�^�j
			map.put(KEY_SHINSATANTO_LIST, pageInfo);
			// �R���S�����ꗗ�i�R�������t���O�j
			map.put(KEY_SHINSACOMPLETE_FLG, shinsaCompleteFlg);
            // ���Q�֌W���͏󋵈ꗗ�i���͊����t���O�j
            map.put(KEY_NYURYOKUCOMPLETE_FLG, nyuryokuCompleteFlg);
//2007/10/27�@�c�@�C�������܂�            
			
			// ���Ƌ敪���u4:��Ձv��������
			if(IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKubun)
                    || IJigyoKubun.JIGYO_KUBUN_WAKATESTART.equals(jigyoKubun)
                    || IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI.equals(jigyoKubun)){
				
				//---�����]�_���X�g���擾
				List hyotenList = null;
				try {
					hyotenList = dao.getSogoHyokaList(
												connection, 
												shinsainNo,
												jigyoKubun,
												jigyoId);
				} catch (DataAccessException e) {
					throw new ApplicationException(
						"�R�����ʃf�[�^��������DB�G���[���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}

				//�}�b�v�ɓ���Ȃ���
				int allcount = 0;
				Map hyotenMap = new HashMap();
				for(int i=0; i<hyotenList.size(); i++){
					Map recordMap = (Map)hyotenList.get(i);	//�P���R�[�h
	
					String kekkaTen_ = (String)recordMap.get("KEKKA_TEN");
					String count_ = ((Number)recordMap.get("COUNT")).toString();
					allcount = allcount + Integer.parseInt(count_);
	
					hyotenMap.put(kekkaTen_, count_);
				}
				//�u���ׂāv�i�������j�̃L�[�́u0�v�ŃZ�b�g
				hyotenMap.put("0", Integer.toString(allcount));				

				//�߂�l�}�b�v�ɒǉ�
				//�R���S�����ꗗ�i�����]�_���X�g�j
				map.put(KEY_SOGOHYOTEN_LIST, hyotenMap);
			}

			//2006.06.08 iso �R���S�����ƈꗗ�ł̎��Ɩ��\�������C��
			JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(jigyoId);
			JigyoKanriInfoDao jigyoKanriInfoDao = new JigyoKanriInfoDao(userInfo);
			JigyoKanriInfo jigyoKanriInfo;
			try {
				jigyoKanriInfo = jigyoKanriInfoDao.selectJigyoKanriInfo(connection, jigyoKanriPk);
			} catch(DataAccessException e) {
				throw new ApplicationException(
					"���ƃf�[�^��������DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}
			map.put("JIGYO_INFO", jigyoKanriInfo);
			return map;
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	/**
	 * �R�����ʏ��̎擾.<br /><br />
	 * 
	 * �R�����ʏ����擾���āA������i�[����ShinsaKekkaInputInfo��ԋp����B<br /><br />
	 * 
	 * 1.�ȉ���SQL���𔭍s���āA�Y������\���f�[�^���擾����B<br />
	 * (�o�C���h�����́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 * 	,A.UKETUKE_NO			--�\���ԍ�
	 * 	,A.JIGYO_ID			--����ID
	 * 	,A.NENDO				--�N�x
	 * 	,A.KAISU				--��
	 * 	,A.JIGYO_NAME			--���Ɩ�
	 * 	,A.SHINSEISHA_ID			--�\����ID
	 * 	,A.SAKUSEI_DATE			--�\�����쐬��
	 * 	,A.SHONIN_DATE			--�����@�֏��F��
	 * 	,A.JYURI_DATE			--�w�U�󗝓�
	 * 	,A.NAME_KANJI_SEI			--�\���Ҏ����i������-���j
	 * 	,A.NAME_KANJI_MEI			--�\���Ҏ����i������-���j
	 * 	,A.NAME_KANA_SEI			--�\���Ҏ����i�t���K�i-���j
	 * 	,A.NAME_KANA_MEI			--�\���Ҏ����i�t���K�i-���j
	 * 	,A.NAME_RO_SEI			--�\���Ҏ����i���[�}��-���j
	 * 	,A.NAME_RO_MEI			--�\���Ҏ����i���[�}��-���j
	 * 	,A.NENREI				--�N��
	 * 	,A.KENKYU_NO			--�\���Ҍ����Ҕԍ�
	 * 	,A.SHOZOKU_CD			--�����@�փR�[�h
	 * 	,A.SHOZOKU_NAME			--�����@�֖�
	 * 	,A.SHOZOKU_NAME_RYAKU		--�����@�֖��i���́j
	 * 	,A.BUKYOKU_CD			--���ǃR�[�h
	 * 	,A.BUKYOKU_NAME			--���ǖ�
	 * 	,A.BUKYOKU_NAME_RYAKU		--���ǖ��i���́j
	 * 	,A.SHOKUSHU_CD			--�E���R�[�h
	 * 	,A.SHOKUSHU_NAME_KANJI		--�E���i�a���j
	 * 	,A.SHOKUSHU_NAME_RYAKU		--�E���i���́j
	 * 	,A.ZIP				--�X�֔ԍ�
	 * 	,A.ADDRESS			--�Z��
	 * 	,A.TEL				--TEL
	 * 	,A.FAX				--FAX
	 * 	,A.EMAIL				--E-Mail
	 * 	,A.SENMON				--���݂̐��
	 * 	,A.GAKUI				--�w��
	 * 	,A.BUNTAN				--�������S
	 * 	,A.KADAI_NAME_KANJI		--�����ۑ薼(�a���j
	 * 	,A.KADAI_NAME_EIGO			--�����ۑ薼(�p���j
	 * 	,A.JIGYO_KUBUN			--���Ƌ敪
	 * 	,A.SHINSA_KUBUN			--�R���敪
	 * 	,A.SHINSA_KUBUN_MEISHO		--�R���敪����
	 * 	,A.BUNKATSU_NO			--�����ԍ�
	 * 	,A.BUNKATSU_NO_MEISHO		--�����ԍ�����
	 * 	,A.KENKYU_TAISHO			--�����Ώۂ̗ތ^
	 * 	,A.KEI_NAME_NO			--�n���̋敪�ԍ�
	 * 	,A.KEI_NAME			--�n���̋敪
	 * 	,A.KEI_NAME_RYAKU			--�n���̋敪����
	 * 	,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 * 	,A.BUNYA_NAME			--����
	 * 	,A.BUNKA_NAME			--����
	 * 	,A.SAIMOKU_NAME			--�ז�
	 * 	,A.BUNKASAIMOKU_CD2		--�זڔԍ�2
	 * 	,A.BUNYA_NAME2			--����2
	 * 	,A.BUNKA_NAME2			--����2
	 * 	,A.SAIMOKU_NAME2			--�ז�2
	 * 	,A.KANTEN_NO			--���E�̊ϓ_�ԍ�
	 * 	,A.KANTEN				--���E�̊ϓ_
	 * 	,A.KANTEN_RYAKU			--���E�̊ϓ_����
	 * 	,A.KEIHI1				--1�N�ڌ����o��
	 * 	,A.BIHINHI1			--1�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI1			--1�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI1			--1�N�ڍ�������
	 * 	,A.GAIKOKURYOHI1			--1�N�ڊO������
	 * 	,A.RYOHI1				--1�N�ڗ���
	 * 	,A.SHAKIN1			--1�N�ڎӋ���
	 * 	,A.SONOTA1			--1�N�ڂ��̑�
	 * 	,A.KEIHI2				--2�N�ڌ����o��
	 * 	,A.BIHINHI2			--2�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI2			--2�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI2			--2�N�ڍ�������
	 * 	,A.GAIKOKURYOHI2			--2�N�ڊO������
	 * 	,A.RYOHI2				--2�N�ڗ���
	 * 	,A.SHAKIN2			--2�N�ڎӋ���
	 * 	,A.SONOTA2			--2�N�ڂ��̑�
	 * 	,A.KEIHI3				--3�N�ڌ����o��
	 * 	,A.BIHINHI3			--3�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI3			--3�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI3			--3�N�ڍ�������
	 * 	,A.GAIKOKURYOHI3			--3�N�ڊO������
	 * 	,A.RYOHI3				--3�N�ڗ���
	 * 	,A.SHAKIN3			--3�N�ڎӋ���
	 * 	,A.SONOTA3			--3�N�ڂ��̑�
	 * 	,A.KEIHI4				--4�N�ڌ����o��
	 * 	,A.BIHINHI4			--4�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI4			--4�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI4			--4�N�ڍ�������
	 * 	,A.GAIKOKURYOHI4			--4�N�ڊO������
	 * 	,A.RYOHI4				--4�N�ڗ���
	 * 	,A.SHAKIN4			--4�N�ڎӋ���
	 * 	,A.SONOTA4			--4�N�ڂ��̑�
	 * 	,A.KEIHI5				--5�N�ڌ����o��
	 * 	,A.BIHINHI5			--5�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI5			--5�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI5			--5�N�ڍ�������
	 * 	,A.GAIKOKURYOHI5			--5�N�ڊO������
	 * 	,A.RYOHI5				--5�N�ڗ���
	 * 	,A.SHAKIN5			--5�N�ڎӋ���
	 * 	,A.SONOTA5			--5�N�ڂ��̑�
	 * 	,A.KEIHI_TOTAL			--���v-�����o��
	 * 	,A.BIHINHI_TOTAL			--���v-�ݔ����i��
	 * 	,A.SHOMOHINHI_TOTAL		--���v-���Օi��
	 * 	,A.KOKUNAIRYOHI_TOTAL		--���v-��������
	 * 	,A.GAIKOKURYOHI_TOTAL		--���v-�O������
	 * 	,A.RYOHI_TOTAL			--���v-����
	 * 	,A.SHAKIN_TOTAL			--���v-�Ӌ���
	 * 	,A.SONOTA_TOTAL			--���v-���̑�
	 * 	,A.SOSHIKI_KEITAI_NO		--�����g�D�̌`�Ԕԍ�
	 * 	,A.SOSHIKI_KEITAI			--�����g�D�̌`��
	 * 	,A.BUNTANKIN_FLG			--���S���̗L��
	 * 	,A.KOYOHI				--�����x���Ҍٗp�o��
	 * 	,A.KENKYU_NINZU			--�����Ґ�
	 * 	,A.TAKIKAN_NINZU			--���@�ւ̕��S�Ґ�
	 * 	,A.SHINSEI_KUBUN			--�V�K�p���敪
	 * 	,A.KADAI_NO_KEIZOKU		--�p�����̌����ۑ�ԍ�
	 * 	,A.SHINSEI_FLG_NO			--�\���̗L���ԍ�
	 * 	,A.SHINSEI_FLG			--�\���̗L��
	 * 	,A.KADAI_NO_SAISYU			--�ŏI�N�x�ۑ�ԍ�
	 * 	,A.KAIJIKIBO_FLG_NO		--�J����]�̗L���ԍ�
	 * 	,A.KAIJIKIBO_FLG			--�J����]�̗L��
	 * 	,A.KAIGAIBUNYA_CD			--�C�O����R�[�h
	 * 	,A.KAIGAIBUNYA_NAME		--�C�O���얼��
	 * 	,A.KAIGAIBUNYA_NAME_RYAKU		--�C�O���엪��
	 * 	,A.KANREN_SHIMEI1			--�֘A����̌�����-����1
	 * 	,A.KANREN_KIKAN1			--�֘A����̌�����-�����@��1
	 * 	,A.KANREN_BUKYOKU1			--�֘A����̌�����-��������1
	 * 	,A.KANREN_SHOKU1			--�֘A����̌�����-�E��1
	 * 	,A.KANREN_SENMON1			--�֘A����̌�����-��啪��1
	 * 	,A.KANREN_TEL1			--�֘A����̌�����-�Ζ���d�b�ԍ�1
	 * 	,A.KANREN_JITAKUTEL1		--�֘A����̌�����-����d�b�ԍ�1
	 * 	,A.KANREN_MAIL1			--�֘A����̌�����-E-mail1
	 * 	,A.KANREN_SHIMEI2			--�֘A����̌�����-����2
	 * 	,A.KANREN_KIKAN2			--�֘A����̌�����-�����@��2
	 * 	,A.KANREN_BUKYOKU2			--�֘A����̌�����-��������2
	 * 	,A.KANREN_SHOKU2			--�֘A����̌�����-�E��2
	 * 	,A.KANREN_SENMON2			--�֘A����̌�����-��啪��2
	 * 	,A.KANREN_TEL2			--�֘A����̌�����-�Ζ���d�b�ԍ�2
	 * 	,A.KANREN_JITAKUTEL2		--�֘A����̌�����-����d�b�ԍ�2
	 * 	,A.KANREN_MAIL2			--�֘A����̌�����-E-mail2
	 * 	,A.KANREN_SHIMEI3			--�֘A����̌�����-����3
	 * 	,A.KANREN_KIKAN3			--�֘A����̌�����-�����@��3
	 * 	,A.KANREN_BUKYOKU3			--�֘A����̌�����-��������3
	 * 	,A.KANREN_SHOKU3			--�֘A����̌�����-�E��3
	 * 	,A.KANREN_SENMON3			--�֘A����̌�����-��啪��3
	 * 	,A.KANREN_TEL3			--�֘A����̌�����-�Ζ���d�b�ԍ�3
	 * 	,A.KANREN_JITAKUTEL3		--�֘A����̌�����-����d�b�ԍ�3
	 * 	,A.KANREN_MAIL3			--�֘A����̌�����-E-mail3
	 * 	,A.XML_PATH			--XML�̊i�[�p�X
	 * 	,A.PDF_PATH			--PDF�̊i�[�p�X
	 * 	,A.JURI_KEKKA			--�󗝌���
	 * 	,A.JURI_BIKO			--�󗝌��ʔ��l
	 * 	,A.SUISENSHO_PATH			--���E���̊i�[�p�X
	 * 	,A.KEKKA1_ABC			--�P���R������(ABC)
	 * 	,A.KEKKA1_TEN			--�P���R������(�_��)
	 * 	,A.KEKKA1_TEN_SORTED		--�P���R������(�_����)
	 * 	,A.SHINSA1_BIKO			--�P���R�����l
	 * 	,A.KEKKA2				--�Q���R������
	 * 	,A.SOU_KEHI			--���o��i�w�U���́j
	 * 	,A.SHONEN_KEHI			--���N�x�o��i�w�U���́j
	 * 	,A.SHINSA2_BIKO			--�Ɩ��S���ҋL����
	 * 	,A.JOKYO_ID			--�\����ID
	 * 	,A.SAISHINSEI_FLG			--�Đ\���t���O
	 * 	,A.DEL_FLG			--�폜�t���O
	 * FROM
	 * 	SHINSEIDATAKANRI A
	 * WHERE
	 * 	SYSTEM_NO = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>������shinsaKekkaPk�̕ϐ�SystemNo���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́AshinseiDataInfo�Ɋi�[����B<br /><br /><br />
	 * 
	 * 
	 * 
	 * 2.�ȉ���SQL���𔭍s���āA�Y������R�����ʃf�[�^���擾����B<br />
	 * (�o�C���h�����́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 * 	,A.UKETUKE_NO			--�\���ԍ�
	 * 	,A.SHINSAIN_NO			--�R�����ԍ�
	 * 	,A.JIGYO_KUBUN			--���Ƌ敪
	 * 	,A.SEQ_NO				--�V�[�P���X�ԍ�
	 * 	,A.SHINSA_KUBUN			--�R���敪
	 * 	,A.SHINSAIN_NAME_KANJI_SEI		--�R�������i�����|���j
	 * 	,A.SHINSAIN_NAME_KANJI_MEI		--�R�������i�����|���j
	 * 	,A.NAME_KANA_SEI			--�R�������i�t���K�i�|���j
	 * 	,A.NAME_KANA_MEI			--�R�������i�t���K�i�|���j
	 * 	,A.SHOZOKU_NAME			--�R���������@�֖�
	 * 	,A.BUKYOKU_NAME			--�R�������ǖ�
	 * 	,A.SHOKUSHU_NAME			--�R�����E��
	 * 	,A.JIGYO_ID			--����ID
	 * 	,A.JIGYO_NAME			--���Ɩ�
	 * 	,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 * 	,A.EDA_NO				--�}��
	 * 	,A.CHECKDIGIT			--�`�F�b�N�f�W�b�g
	 * 	,A.KEKKA_ABC			--�����]���iABC�j
	 * 	,A.KEKKA_TEN			--�����]���i�_���j
	 * 	,A.COMMENT1			--�R�����g1
	 * 	,A.COMMENT2			--�R�����g2
	 * 	,A.COMMENT3			--�R�����g3
	 * 	,A.COMMENT4			--�R�����g4
	 * 	,A.COMMENT5			--�R�����g5
	 * 	,A.COMMENT6			--�R�����g6
	 * 	,A.KENKYUNAIYO			--�������e
	 * 	,A.KENKYUKEIKAKU			--�����v��
	 * 	,A.TEKISETSU_KAIGAI		--�K�ؐ�-�C�O
	 * 	,A.TEKISETSU_KENKYU1		--�K�ؐ�-�����i1�j
	 * 	,A.TEKISETSU			--�K�ؐ�
	 * 	,A.DATO				--�Ó���
	 * 	,A.SHINSEISHA			--������\��
	 * 	,A.KENKYUBUNTANSHA			--�������S��
	 * 	,A.HITOGENOMU			--�q�g�Q�m��
	 * 	,A.TOKUTEI			--������
	 * 	,A.HITOES				--�q�gES�זE
	 * 	,A.KUMIKAE			--��`�q�g��������
	 * 	,A.CHIRYO				--��`�q���×Տ�����
	 * 	,A.EKIGAKU			--�u�w����
	 * 	,A.COMMENTS			--�R�����g
	 * 	,A.TENPU_PATH			--�Y�t�t�@�C���i�[�p�X
	 * 	,DECODE(
	 * 		NVL(A.TENPU_PATH,'null') 
	 * 		,'null','FALSE'		--�Y�t�t�@�C���i�[�p�X��NULL�̂Ƃ�
	 * 		,      'TRUE'		--�Y�t�t�@�C���i�[�p�X��NULL�ȊO�̂Ƃ�
	 * 	) TENPU_FLG			--�Y�t�t�@�C���i�[�t���O
	 * 	,A.SHINSA_JOKYO			--�R����
	 * 	,A.BIKO				--���l
	 * FROM
	 * 	SHINSAKEKKA A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * 	AND SHINSAIN_NO = ?
	 * 	AND JIGYO_KUBUN = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>������shinsaKekkaPk�̕ϐ�SystemNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������shinsaKekkaPk�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������shinsaKekkaPk�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́AshinsaKekkaInfo�Ɋi�[����B<br /><br /><br />
	 * 
	 * 
	 * 
	 * 3.�ȉ���SQL���𔭍s���āA�Y�����鎖�ƊǗ��f�[�^���擾����B<br />
	 * (�o�C���h�����́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.JIGYO_ID		--����ID
	 * 	,A.NENDO			--�N�x
	 * 	,A.KAISU			--��
	 * 	,A.JIGYO_NAME		--���Ɩ�
	 * 	,A.JIGYO_KUBUN		--���Ƌ敪
	 * 	,A.SHINSA_KUBUN		--�R���敪
	 * 	,A.TANTOKA_NAME		--�Ɩ��S����
	 * 	,A.TANTOKAKARI		--�Ɩ��S���W��
	 * 	,A.TOIAWASE_NAME		--�₢���킹��S���Җ�
	 * 	,A.TOIAWASE_TEL		--�₢���킹��d�b�ԍ�
	 * 	,A.TOIAWASE_EMAIL		--�₢���킹��E-mail
	 * 	,A.UKETUKEKIKAN_START	--�w�U��t���ԁi�J�n�j
	 * 	,A.UKETUKEKIKAN_END	--�w�U��t���ԁi�I���j
	 * 	,A.SHINSAKIGEN		--�R������
	 * 	,A.TENPU_NAME		--�Y�t������
	 * 	,A.TENPU_WIN		--�Y�t�t�@�C���i�[�t�H���_�iWin�j
	 * 	,A.TENPU_MAC		--�Y�t�t�@�C���i�[�t�H���_�iMac�j
	 * 	,A.HYOKA_FILE_FLG		--�]���p�t�@�C���L��
	 * 	,A.HYOKA_FILE		--�]���p�t�@�C���i�[�t�H���_
	 * 	,A.KOKAI_FLG		--���J�t���O
	 * 	,A.KESSAI_NO		--���J���ٔԍ�
	 * 	,A.KOKAI_ID		--���J�m���ID
	 * 	,A.HOKAN_DATE		--�f�[�^�ۊǓ�
	 * 	,A.YUKO_DATE		--�ۊǗL������
	 * 	,A.BIKO			--���l
	 * 	,A.DEL_FLG		--�폜�t���O
	 * FROM
	 * 	JIGYOKANRI A
	 * WHERE
	 * 	JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>�擾����shinseiDataInfo�̕ϐ�JigyoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́AjigyoKanriInfo�Ɋi�[����B<br /><br /><br />
	 * 
	 * 
	 * 
	 * 4.ShinsaKekkaInputInfo�I�u�W�F�N�g�ɒl���i�[����B<br /><br />
	 * 
	 * �@(1)1�`3�Ŏ擾�����l���i�[���Ă����B�i�[����l�́A�ȉ��̕\���Q�ƁB<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>���O(�a��)</td><td>���O</td><td>�l������Info�I�u�W�F�N�g</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>SystemNo</td><td>shinsaKekkaPk</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>getShinsainNo</td><td>shinsaKekkaPk</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>JigyoKubun</td><td>shinsaKekkaPk</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�N�x</td><td>Nendo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��</td><td>Kaisu</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�C�O��������(���)</td><td>KaigaibunyaName <span style="color:red">??</span></td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�זڔԍ�(���)</td><td>BunkaSaimokuCd</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�זږ�</td><td>SaimokuName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>JigyoId</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>������ږ�</td><td>JigyoName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>UketukeNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����ۑ薼</td><td>KadaiNameKanji</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Җ�-��</td><td>NameKanjiSei</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Җ�-��</td><td>NameKanjiMei</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�</td><td>ShozokuName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�</td><td>BukyokuName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E��</td><td>ShokushuName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���E�̊ϓ_</td><td>Kanten</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�n���̋敪</td><td>KeiName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����g�D�̌`�Ԕԍ�</td><td>SoshikiKeitaiNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����v��ŏI�N�x�O�N�x�̉���</td><td>ShinseiFlgNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���S���̗L��</td><td>BuntankinFlg</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����ԍ�</td><td>BunkatsuNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�C�O����R�[�h</td><td>KaigaibunyaCd</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�C�O���얼</td><td>KaigaibunyaName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����]�_(ABC)</td><td>KekkaAbc</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����]�_(�_��)</td><td>KekkaTen</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g1</td><td>Comment1</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g2</td><td>Comment2</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g3</td><td>Comment3</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g4</td><td>Comment4</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g5</td><td>Comment5</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g6</td><td>Comment6</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�������e(���)</td><td>KenkyuNaiyo</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����v��(���)</td><td>KenkyuKeikaku</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-�C�O(���)</td><td>TekisetsuKaigai</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-����(1)</td><td>TekisetsuKenkyu1</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�(���)</td><td>Tekisetsu</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ó���(���)</td><td>Dato</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>������\��(���)</td><td>Shinseisha</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�������S��(���)</td><td>KenkyuBuntansha</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�q�g�Q�m��(���)</td><td>Hitogenomu</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>������(���)</td><td>Tokutei</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�q�gES�זE(���)</td><td>HitoEs</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��`�q�g��������(���)</td><td>Kumikae</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��`�q���×Տ�����(���)</td><td>Chiryo</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�u�w����(���)</td><td>Ekigaku</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g(���)</td><td>Comments</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Y�t�t�@�C���i�[�t���O</td><td>TenpuFlg</td><td>shinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�]���p�t�@�C���L��</td><td>HyokaFileFlg</td><td>jigyoKanriInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ƃR�[�h</td><td>JigyoCd</td><td>shinsaKekkaInfo</td></tr>
	 * </table><br /><br />
	 * 
	 * �@(2)�Y�t�t�@�C�����̊i�[<br /><br />
	 * �@�@shinsaKekkaInfo����tenpuPath�̒l������Ƃ��́A�p�X����t�@�C�������擾���A�i�[����B<br /><br /><br />
	 * 
	 * �@(3)�\�����x�����̊i�[<br /><br />
	 * �@�@�ȉ���SQL���𔭍s���āA���x���}�X�^���烌�R�[�h������Map���擾����B<br />
	 * �@�@�擾����Map��"�\�����x����"�����ꂼ��i�[����B<br />
	 * �@�@�擾���郉�x�����́A�ȉ��̒l�̂��̂Ƃ���B<br />
	 * �@�@���́A�擾���郉�x�����̐������������s���B<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>�擾���郉�x����</td><td>���x���敪</td><td>�g�p����l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����]���iABC�j</td><td>KEKKA_ABC</td><td>KekkaAbc</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����]���i�_���j</td><td>KEKKA_TEN</td><td>KekkaTen</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�������e</td><td>KENKYUNAIYO</td><td>KenkyuNaiyo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����v��</td><td>KENKYUKEIKAKU</td><td>KenkyuKeikaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-�C�O</td><td>TEKISETSU_KAIGAI</td><td>TekisetsuKaigai</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-����(1)</td><td>TEKISETSU_KENKYU1</td><td>TekisetsuKenkyu1</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�</td><td>TEKISETSU</td><td>Tekisetsu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ó���</td><td>DATO</td><td>Dato</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>������\��</td><td>SHINSEISHA</td><td>Shinseisha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�������S��</td><td>KENKYUBUNTANSHA</td><td>KenkyuBuntansha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�q�g�Q�m��</td><td>HITOGENOMU</td><td>Hitogenomu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>������</td><td>TOKUTEI</td><td>Tokutei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�q�g�d�r�זE</td><td>HITOES</td><td>HitoEs</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��`�q�g��������</td><td>KUMIKAE</td><td>Kumikae</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��`�q���×Տ�����</td><td>CHIRYO</td><td>Chiryo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�u�w����</td><td>EKIGAKU</td><td>Ekigaku</td></tr>
	 * </table><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.ATAI
	 * 	, A.NAME
	 * 	, A.RYAKU
	 * 	, A.SORT
	 * 	, A.BIKO
	 * FROM
	 * 	MASTER_LABEL A
	 * WHERE
	 * 	A.LABEL_KUBUN = ?
	 * 	AND A.ATAI = ?
	 * ORDER BY
	 * 	SORT</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>���x���敪</td><td>��L�̕\�́w���x���敪�x���g�p</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�l</td><td>��L�̕\�́w�g�p����l�x���g�p</td></tr>
	 * </table><br /><br />
	 * 
	 * 5.�l�̊i�[���I��������AShinsaKekkaInputInfo�I�u�W�F�N�g��ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param shinsaKekkaPk ShinsaKekkaPk
	 * @return �R�����ʏ�������ShinsaKekkaInputInfo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
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
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
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
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);
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
			JigyoKanriInfoDao jigyoDao = new JigyoKanriInfoDao(userInfo);
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
			
			
			// 2005/03/03
			// ����ID  ??  �C�O�������얼  ??
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
			info.setTekisetsuKenkyu1(shinsaKekkaInfo.getTekisetsuKenkyu1());				//�K�ؐ�-����(1)�i��Ձj
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
			//2005.10.26 kainuma
			info.setRigai(shinsaKekkaInfo.getRigai());										//���Q�֌W
			info.setWakates(shinsaKekkaInfo.getWakates());									//���S�@2007/5/8
			info.setJuyosei(shinsaKekkaInfo.getJuyosei());									//�w�p�I�d�v���E�Ó���
			info.setDokusosei(shinsaKekkaInfo.getDokusosei());								//�Ƒn���E�v�V��
			info.setHakyukoka(shinsaKekkaInfo.getHakyukoka());								//�g�y���ʁE���Ր�
			info.setSuikonoryoku(shinsaKekkaInfo.getSuikonoryoku());						//���s�\�́E���̓K�ؐ�
			info.setJinken(shinsaKekkaInfo.getJinken());									//�l���̕ی�E�@�ߓ��̏���
			info.setBuntankin(shinsaKekkaInfo.getBuntankin());								//���S���z��
			info.setOtherComment(shinsaKekkaInfo.getOtherComment());						//���̑��R�����g

			info.setTenpuFlg(shinsaKekkaInfo.getTenpuFlg());								//�Y�t�t�@�C���i�[�t���O
			info.setHyokaFileFlg(jigyoKanriInfo.getHyokaFileFlg());							//�]���p�t�@�C���L��	
			info.setJigyoCd(shinsaKekkaInfo.getJigyoId().substring(2,7));					//���ƃR�[�h
//2006/04/10 �ǉ���������
			info.setShinsaryoikiCd(shinseiDataInfo.getShinsaRyoikiCd());                    //����ԍ�
			info.setShinsaryoikiName(shinseiDataInfo.getShinsaRyoikiName());                //���얼
//�c�@�ǉ������܂�			

			
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
			//�����]���i�_���j�̕\�����x�������Z�b�g
			if(info.getKekkaTen() != null && info.getKekkaTen().length() != 0){
				try{				
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																	ILabelKubun.KEKKA_TEN,
																	info.getKekkaTen());		
					info.setKekkaTenLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�����]���i�_���j���́i�G��j���Z�b�g
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
			
			
			//���Q�֌W�̕\�����x�������Z�b�g
		  	if (info.getRigai() != null && info.getRigai().length() != 0) {
                try {
                    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.RIGAI, info.getRigai());
                    info.setRigaiLabel((String) resultMap.get("NAME"));
                } catch (NoDataFoundException e) {
                    //��ONoDataFoundException�����������Ƃ��́A���Q�֌W���Z�b�g
                    info.setRigaiLabel(info.getRigai());
                } catch (DataAccessException e) {
                    throw new ApplicationException("���x���}�X�^���擾���ɗ�O���������܂����B",
                            new ErrorInfo("errors.4004"), e);
                }
            }

			//���S�Ƃ��Ă̑Ó����̕\�����x�������Z�b�g�@2007/5/9�ǉ�
			if (info.getWakates() != null && info.getWakates().length() != 0) {
                try {
                    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.JUYOSEI, info.getWakates());
					info.setWakatesLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A���S���Z�b�g
				  info.setWakatesLabel(info.getWakates());
				}catch (DataAccessException e) {
                    throw new ApplicationException("���x���}�X�^���擾���ɗ�O���������܂����B",
                            new ErrorInfo("errors.4004"), e);
                }
		    }			  
		  	
			//�w�p�I�d�v���E�Ó����̕\�����x�������Z�b�g
			if (info.getJuyosei() != null && info.getJuyosei().length() != 0) {
                try {
                    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.JUYOSEI, info.getJuyosei());
					info.setJuyoseiLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�Y���̒l���Z�b�g����
				  info.setJuyoseiLabel(info.getJuyosei());
				}catch (DataAccessException e) {
                    throw new ApplicationException("���x���}�X�^���擾���ɗ�O���������܂����B",
                            new ErrorInfo("errors.4004"), e);
                }
		    }			  
	
			//�Ƒn���E�v�V���̕\�����x�������Z�b�g
			if(info.getDokusosei() != null && info.getDokusosei().length() != 0){
				try{
					Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.DOKUSOSEI, info.getDokusosei());
					info.setDokusoseiLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�Y���̒l���Z�b�g����
					info.setDokusoseiLabel(info.getDokusosei());
				}catch (DataAccessException e) {
                    throw new ApplicationException("���x���}�X�^���擾���ɗ�O���������܂����B",
                            new ErrorInfo("errors.4004"), e);
                }
			}
			
			//�g�y���ʁE���Ր��̕\�����x�������Z�b�g
			if(info.getHakyukoka() != null && info.getHakyukoka().length() != 0){
				try {
                    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.HAKYUKOKA, info.getHakyukoka());
					info.setHakyukokaLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�Y���̒l���Z�b�g����
				  info.setHakyukokaLabel(info.getHakyukoka());
				}catch (DataAccessException e) {
                    throw new ApplicationException("���x���}�X�^���擾���ɗ�O���������܂����B",
                            new ErrorInfo("errors.4004"), e);
			  }		
		  }
			
			//���s�\�́E���̓K�ؐ��̕\�����x�������Z�b�g
			if(info.getSuikonoryoku() != null && info.getSuikonoryoku().length() != 0){
				try {
				  Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.SUIKONORYOKU, info.getSuikonoryoku());
					info.setSuikonoryokuLabel((String)resultMap.get("NAME"));
				}catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�Y���̒l���Z�b�g����
				  info.setSuikonoryokuLabel(info.getRigai());
				}catch (DataAccessException e) {
                    throw new ApplicationException("���x���}�X�^���擾���ɗ�O���������܂����B",
                            new ErrorInfo("errors.4004"), e);
                }		
		    }
		
            // �l���̕ی�E�@�ߓ��̏���̕\�����x�������Z�b�g
            if (info.getJinken() != null && info.getJinken().length() != 0) {
                try {
                    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
                            ILabelKubun.JINKEN, info.getJinken());
                    info.setJinkenLabel((String) resultMap.get("NAME"));
                } catch (NoDataFoundException e) {
					//��ONoDataFoundException�����������Ƃ��́A�Y���̒l���Z�b�g����
                    info.setJinkenLabel(info.getJinken());
                } catch (DataAccessException e) {
                    throw new ApplicationException("���x���}�X�^���擾���ɗ�O���������܂����B",
                            new ErrorInfo("errors.4004"), e);
                }
            }
			
		    //���S���z���̕\�����x�������Z�b�g
		    if(info.getBuntankin() != null && info.getBuntankin().length() != 0){
			    try{
				    Map resultMap = MasterLabelInfoDao.selectRecord(connection,
																//2005.01.17 iso ���S���̕\���o�O���C��
//																ILabelKubun.SUIKONORYOKU,
																ILabelKubun.BUNTANKIN,
																info.getBuntankin());		
				    info.setBuntankinLabel((String)resultMap.get("NAME"));
			    }catch(NoDataFoundException e){
					//��ONoDataFoundException�����������Ƃ��́A�Y���̒l���Z�b�g����
				    info.setBuntankinLabel(info.getRigai());
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
	 * �R�����ʂ̓o�^.<br /><br />
	 * 
	 * 1.�R�����ʂ̓o�^<br /><br />
	 * 
	 * (1)�ȉ���SQL���𔭍s���āA�Y������R�����ʃf�[�^���擾����B<br />
	 * (�o�C���h�����́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 * 	,A.UKETUKE_NO			--�\���ԍ�
	 * 	,A.SHINSAIN_NO			--�R�����ԍ�
	 * 	,A.JIGYO_KUBUN			--���Ƌ敪
	 * 	,A.SEQ_NO				--�V�[�P���X�ԍ�
	 * 	,A.SHINSA_KUBUN			--�R���敪
	 * 	,A.SHINSAIN_NAME_KANJI_SEI		--�R�������i�����|���j
	 * 	,A.SHINSAIN_NAME_KANJI_MEI		--�R�������i�����|���j
	 * 	,A.NAME_KANA_SEI			--�R�������i�t���K�i�|���j
	 * 	,A.NAME_KANA_MEI			--�R�������i�t���K�i�|���j
	 * 	,A.SHOZOKU_NAME			--�R���������@�֖�
	 * 	,A.BUKYOKU_NAME			--�R�������ǖ�
	 * 	,A.SHOKUSHU_NAME			--�R�����E��
	 * 	,A.JIGYO_ID			--����ID
	 * 	,A.JIGYO_NAME			--���Ɩ�
	 * 	,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 * 	,A.EDA_NO				--�}��
	 * 	,A.CHECKDIGIT			--�`�F�b�N�f�W�b�g
	 * 	,A.KEKKA_ABC			--�����]���iABC�j
	 * 	,A.KEKKA_TEN			--�����]���i�_���j
	 * 	,A.COMMENT1			--�R�����g1
	 * 	,A.COMMENT2			--�R�����g2
	 * 	,A.COMMENT3			--�R�����g3
	 * 	,A.COMMENT4			--�R�����g4
	 * 	,A.COMMENT5			--�R�����g5
	 * 	,A.COMMENT6			--�R�����g6
	 * 	,A.KENKYUNAIYO			--�������e
	 * 	,A.KENKYUKEIKAKU			--�����v��
	 * 	,A.TEKISETSU_KAIGAI		--�K�ؐ�-�C�O
	 * 	,A.TEKISETSU_KENKYU1		--�K�ؐ�-�����i1�j
	 * 	,A.TEKISETSU			--�K�ؐ�
	 * 	,A.DATO				--�Ó���
	 * 	,A.SHINSEISHA			--������\��
	 * 	,A.KENKYUBUNTANSHA			--�������S��
	 * 	,A.HITOGENOMU			--�q�g�Q�m��
	 * 	,A.TOKUTEI			--������
	 * 	,A.HITOES				--�q�gES�זE
	 * 	,A.KUMIKAE			--��`�q�g��������
	 * 	,A.CHIRYO				--��`�q���×Տ�����
	 * 	,A.EKIGAKU			--�u�w����
	 * 	,A.COMMENTS			--�R�����g
	 * 	,A.TENPU_PATH			--�Y�t�t�@�C���i�[�p�X
	 * 	,DECODE(
	 * 		NVL(A.TENPU_PATH,'null') 
	 * 		,'null','FALSE'		--�Y�t�t�@�C���i�[�p�X��NULL�̂Ƃ�
	 * 		,      'TRUE'		--�Y�t�t�@�C���i�[�p�X��NULL�ȊO�̂Ƃ�
	 * 	) TENPU_FLG			--�Y�t�t�@�C���i�[�t���O
	 * 	,A.SHINSA_JOKYO			--�R����
	 * 	,A.BIKO				--���l
	 * FROM
	 * 	SHINSAKEKKA A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * 	AND SHINSAIN_NO = ?
	 * 	AND JIGYO_KUBUN = ?
	 * FOR UPDATE</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>������shinsaKekkaInputInfo�̕ϐ�SystemNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������shinsaKekkaInputInfo�̕ϐ�ShinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������shinsaKekkaInputInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l��ShinsaKekkaInfo�Ɋi�[����B<br /><br />
	 * 
	 * ��������ShinsaKekkaInputInfo�̒�����AHyokaFileRes���擾����B<br />
	 * ���̒l��null�łȂ���΁A�ȉ��̏o�͐�̃p�X��HyokaFileRes���������ށB<br /><br />
	 * 
	 * <b>D:/shinsa-kaken/data/</b>����ID<b>/</b>�V�X�e���ԍ�<b>/shinsa/</b>�R�����ԍ�<b>.</b>�g���q<br /><br />
	 * 
	 * �E����ID�@�@�@�c������shinsaKekkaInputInfo�̕ϐ�JigyoId���g�p����B<br />
	 * �E�V�X�e���ԍ��c������shinsaKekkaInputInfo�̕ϐ�SystemNo���g�p����B<br />
	 * �E�R�����ԍ��@�c������shinsaKekkaInputInfo�̕ϐ�ShinsainNo���g�p����B<br />
	 * �E�g���q�@�@�@�cHyokaFileRes�̊g���q�Ɠ������̂��g�p����B<br /><br />
	 * 
	 * ���̃p�X�̒l���ASQL���Ŏ擾����ShinsaKekkaInfo��"�Y�t�t�@�C����"�ɉ�����B<br /><br />
	 * 
	 * (2)������userInfo������ShinsainInfo�A������shinsaKekkaInputInfo����A�ȉ��̒l��ShinsaKekkaInfo�ɉ�����B<br /><br />
	 * 
	 * �EShinsainInfo����擾����l(�R�������)<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>�l(�a��)</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i����-���j</td><td>ShinsainNameKanjiSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i����-���j</td><td>ShinsainNameKanjiMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>NameKanaSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>NameKanaMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R���������@�֖�</td><td>ShozokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������ǖ�</td><td>BukyokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����E��</td><td>ShokushuName</td></tr>
	 * </table><br />
	 * 
	 * �EshinsaKekkaInputInfo����擾����l(�X�V�f�[�^)<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>�l(�a��)</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����]���iABC�j</td><td>KekkaAbc</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����]���i�_���j</td><td>KekkaTen</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g1</td><td>Comment1</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g2</td><td>Comment2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g3</td><td>Comment3</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g4</td><td>Comment4</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g5</td><td>Comment5</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g6</td><td>Comment6</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�������e</td><td>KenkyuNaiyo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����v��</td><td>KenkyuKeikaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-�C�O</td><td>TekisetsuKaigai</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-����(1)</td><td>TekisetsuKenkyu1</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�</td><td>Tekisetsu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ó���</td><td>Dato</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>������\��</td><td>Shinseisha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�������S��</td><td>KenkyuBuntansha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�q�g�Q�m��</td><td>Hitogenomu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>������</td><td>Tokutei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�q�gES�זE</td><td>HitoEs</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��`�q�g��������</td><td>Kumikae</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��`�q���×Տ�����</td><td>Chiryo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�u�w����</td><td>Ekigaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g</td><td>Comments</td></tr>
	 * </table><br />
	 * 
	 * (3)�擾����shinsaKekkaInputInfo�̒l���g���āA�R�����ʃe�[�u���̍X�V���s���B
	 * ���s����SQL���͈ȉ��̒ʂ�ł���B(�o�C���h�ϐ��́ASQL�̉��̕\���Q��)
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAKEKKA
	 * SET	
	 * 	SYSTEM_NO = ?					--�V�X�e����t�ԍ�
	 * 	,UKETUKE_NO = ?				--�\���ԍ�
	 * 	,SHINSAIN_NO = ?				--�R�����ԍ�
	 * 	,JIGYO_KUBUN = ?				--���Ƌ敪
	 * 	,SEQ_NO = ?					--�V�[�P���X�ԍ�
	 * 	,SHINSA_KUBUN = ?				--�R���敪
	 * 	,SHINSAIN_NAME_KANJI_SEI = ?	--�R�������i�����|���j
	 * 	,SHINSAIN_NAME_KANJI_MEI = ?	--�R�������i�����|���j
	 * 	,NAME_KANA_SEI = ?				--�R�������i�t���K�i�|���j
	 * 	,NAME_KANA_MEI = ?				--�R�������i�t���K�i�|���j
	 * 	,SHOZOKU_NAME = ?				--�R���������@�֖�
	 * 	,BUKYOKU_NAME = ?				--�R�������ǖ�
	 * 	,SHOKUSHU_NAME = ?				--�R�����E��
	 * 	,JIGYO_ID = ?					--����ID
	 * 	,JIGYO_NAME = ?				--���Ɩ�
	 * 	,BUNKASAIMOKU_CD = ?			--�זڔԍ�
	 * 	,EDA_NO = ?					--�}��
	 * 	,CHECKDIGIT = ?				--�`�F�b�N�f�W�b�g
	 * 	,KEKKA_ABC = ?					--�����]���iABC�j
	 * 	,KEKKA_TEN = ?					--�����]���i�_���j
	 * 	,COMMENT1 = ?					--�R�����g1
	 * 	,COMMENT2 = ?					--�R�����g2
	 * 	,COMMENT3 = ?					--�R�����g3
	 * 	,COMMENT4 = ?					--�R�����g4
	 * 	,COMMENT5 = ?					--�R�����g5
	 * 	,COMMENT6 = ?					--�R�����g6
	 * 	,KENKYUNAIYO = ?				--�������e
	 * 	,KENKYUKEIKAKU = ?				--�����v��
	 * 	,TEKISETSU_KAIGAI = ?			--�K�ؐ�-�C�O
	 * 	,TEKISETSU_KENKYU1 = ?			--�K�ؐ�-�����i1�j
	 * 	,TEKISETSU = ?					--�K�ؐ�
	 * 	,DATO = ?						--�Ó���
	 * 	,SHINSEISHA = ?				--������\��
	 * 	,KENKYUBUNTANSHA = ?			--�������S��
	 * 	,HITOGENOMU = ?				--�q�g�Q�m��
	 * 	,TOKUTEI = ?					--������
	 * 	,HITOES = ?					--�q�gES�זE
	 * 	,KUMIKAE = ?					--��`�q�g��������
	 * 	,CHIRYO = ?					--��`�q���×Տ�����
	 * 	,EKIGAKU = ?					--�u�w����
	 * 	,COMMENTS = ?					--�R�����g
	 * 	,TENPU_PATH = ?				--�Y�t�t�@�C���i�[�p�X
	 * 	,SHINSA_JOKYO = ?				--�R����
	 * 	,BIKO = ?						--���l
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * 	AND SHINSAIN_NO = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * �o�C���h�ϐ��̒l�ɂ́A���ׂ�shinsaKekkaInputInfo�̎��l���g�p����B
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>SystemNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>UketukeNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>ShinsainNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>JigyoKubun</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�[�P���X�ԍ�</td><td>SeqNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R���敪</td><td>ShinsaKubun</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�����|���j</td><td>ShinsainNameKanjiSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�����|���j</td><td>ShinsainNameKanjiMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>NameKanaSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������i�t���K�i�|���j</td><td>NameKanaMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R���������@�֖�</td><td>ShozokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�������ǖ�</td><td>BukyokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����E��</td><td>ShokushuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>JigyoId</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ɩ�</td><td>JigyoName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�זڔԍ�</td><td>BunkaSaimokuCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�}��</td><td>EdaNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�`�F�b�N�f�W�b�g</td><td>CheckDigit</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����]���iABC�j</td><td>KekkaAbc</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����]���i�_���j</td><td>KekkaTen</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g1</td><td>Comment1</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g2</td><td>Comment2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g3</td><td>Comment3</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g4</td><td>Comment4</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g5</td><td>Comment5</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g6</td><td>Comment6</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�������e</td><td>KenkyuNaiyo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����v��</td><td>KenkyuKeikaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-�C�O</td><td>TekisetsuKaigai</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�-�����i1�j</td><td>TekisetsuKenkyu1</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�K�ؐ�</td><td>Tekisetsu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ó���</td><td>Dato</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>������\��</td><td>Shinseisha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�������S��</td><td>KenkyuBuntansha</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�q�g�Q�m��</td><td>Hitogenomu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>������</td><td>Tokutei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�q�gES�זE</td><td>HitoEs</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��`�q�g��������</td><td>Kumikae</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��`�q���×Տ�����</td><td>Chiryo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�u�w����</td><td>Ekigaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����g</td><td>Comments</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Y�t�t�@�C���i�[�p�X</td><td>TenpuPath</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R����</td><td>ShinsaJokyo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>Biko</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>SystemNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>ShinsainNo</td></tr>
	 * </table><br />
	 * 
	 * 
	 * 2.�\���f�[�^�X�V<br /><br />
	 * 
	 * (1)�ȉ���SQL���𔭍s���āA�Y������\���f�[�^���擾����B<br />
	 * (�o�C���h�����́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 * 	,A.UKETUKE_NO			--�\���ԍ�
	 * 	,A.JIGYO_ID			--����ID
	 * 	,A.NENDO				--�N�x
	 * 	,A.KAISU				--��
	 * 	,A.JIGYO_NAME			--���Ɩ�
	 * 	,A.SHINSEISHA_ID			--�\����ID
	 * 	,A.SAKUSEI_DATE			--�\�����쐬��
	 * 	,A.SHONIN_DATE			--�����@�֏��F��
	 * 	,A.JYURI_DATE			--�w�U�󗝓�
	 * 	,A.NAME_KANJI_SEI			--�\���Ҏ����i������-���j
	 * 	,A.NAME_KANJI_MEI			--�\���Ҏ����i������-���j
	 * 	,A.NAME_KANA_SEI			--�\���Ҏ����i�t���K�i-���j
	 * 	,A.NAME_KANA_MEI			--�\���Ҏ����i�t���K�i-���j
	 * 	,A.NAME_RO_SEI			--�\���Ҏ����i���[�}��-���j
	 * 	,A.NAME_RO_MEI			--�\���Ҏ����i���[�}��-���j
	 * 	,A.NENREI				--�N��
	 * 	,A.KENKYU_NO			--�\���Ҍ����Ҕԍ�
	 * 	,A.SHOZOKU_CD			--�����@�փR�[�h
	 * 	,A.SHOZOKU_NAME			--�����@�֖�
	 * 	,A.SHOZOKU_NAME_RYAKU		--�����@�֖��i���́j
	 * 	,A.BUKYOKU_CD			--���ǃR�[�h
	 * 	,A.BUKYOKU_NAME			--���ǖ�
	 * 	,A.BUKYOKU_NAME_RYAKU		--���ǖ��i���́j
	 * 	,A.SHOKUSHU_CD			--�E���R�[�h
	 * 	,A.SHOKUSHU_NAME_KANJI		--�E���i�a���j
	 * 	,A.SHOKUSHU_NAME_RYAKU		--�E���i���́j
	 * 	,A.ZIP				--�X�֔ԍ�
	 * 	,A.ADDRESS			--�Z��
	 * 	,A.TEL				--TEL
	 * 	,A.FAX				--FAX
	 * 	,A.EMAIL				--E-Mail
	 * 	,A.SENMON				--���݂̐��
	 * 	,A.GAKUI				--�w��
	 * 	,A.BUNTAN				--�������S
	 * 	,A.KADAI_NAME_KANJI		--�����ۑ薼(�a���j
	 * 	,A.KADAI_NAME_EIGO			--�����ۑ薼(�p���j
	 * 	,A.JIGYO_KUBUN			--���Ƌ敪
	 * 	,A.SHINSA_KUBUN			--�R���敪
	 * 	,A.SHINSA_KUBUN_MEISHO		--�R���敪����
	 * 	,A.BUNKATSU_NO			--�����ԍ�
	 * 	,A.BUNKATSU_NO_MEISHO		--�����ԍ�����
	 * 	,A.KENKYU_TAISHO			--�����Ώۂ̗ތ^
	 * 	,A.KEI_NAME_NO			--�n���̋敪�ԍ�
	 * 	,A.KEI_NAME			--�n���̋敪
	 * 	,A.KEI_NAME_RYAKU			--�n���̋敪����
	 * 	,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 * 	,A.BUNYA_NAME			--����
	 * 	,A.BUNKA_NAME			--����
	 * 	,A.SAIMOKU_NAME			--�ז�
	 * 	,A.BUNKASAIMOKU_CD2		--�זڔԍ�2
	 * 	,A.BUNYA_NAME2			--����2
	 * 	,A.BUNKA_NAME2			--����2
	 * 	,A.SAIMOKU_NAME2			--�ז�2
	 * 	,A.KANTEN_NO			--���E�̊ϓ_�ԍ�
	 * 	,A.KANTEN				--���E�̊ϓ_
	 * 	,A.KANTEN_RYAKU			--���E�̊ϓ_����
	 * 	,A.KEIHI1				--1�N�ڌ����o��
	 * 	,A.BIHINHI1			--1�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI1			--1�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI1			--1�N�ڍ�������
	 * 	,A.GAIKOKURYOHI1			--1�N�ڊO������
	 * 	,A.RYOHI1				--1�N�ڗ���
	 * 	,A.SHAKIN1			--1�N�ڎӋ���
	 * 	,A.SONOTA1			--1�N�ڂ��̑�
	 * 	,A.KEIHI2				--2�N�ڌ����o��
	 * 	,A.BIHINHI2			--2�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI2			--2�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI2			--2�N�ڍ�������
	 * 	,A.GAIKOKURYOHI2			--2�N�ڊO������
	 * 	,A.RYOHI2				--2�N�ڗ���
	 * 	,A.SHAKIN2			--2�N�ڎӋ���
	 * 	,A.SONOTA2			--2�N�ڂ��̑�
	 * 	,A.KEIHI3				--3�N�ڌ����o��
	 * 	,A.BIHINHI3			--3�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI3			--3�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI3			--3�N�ڍ�������
	 * 	,A.GAIKOKURYOHI3			--3�N�ڊO������
	 * 	,A.RYOHI3				--3�N�ڗ���
	 * 	,A.SHAKIN3			--3�N�ڎӋ���
	 * 	,A.SONOTA3			--3�N�ڂ��̑�
	 * 	,A.KEIHI4				--4�N�ڌ����o��
	 * 	,A.BIHINHI4			--4�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI4			--4�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI4			--4�N�ڍ�������
	 * 	,A.GAIKOKURYOHI4			--4�N�ڊO������
	 * 	,A.RYOHI4				--4�N�ڗ���
	 * 	,A.SHAKIN4			--4�N�ڎӋ���
	 * 	,A.SONOTA4			--4�N�ڂ��̑�
	 * 	,A.KEIHI5				--5�N�ڌ����o��
	 * 	,A.BIHINHI5			--5�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI5			--5�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI5			--5�N�ڍ�������
	 * 	,A.GAIKOKURYOHI5			--5�N�ڊO������
	 * 	,A.RYOHI5				--5�N�ڗ���
	 * 	,A.SHAKIN5			--5�N�ڎӋ���
	 * 	,A.SONOTA5			--5�N�ڂ��̑�
	 * 	,A.KEIHI_TOTAL			--���v-�����o��
	 * 	,A.BIHINHI_TOTAL			--���v-�ݔ����i��
	 * 	,A.SHOMOHINHI_TOTAL		--���v-���Օi��
	 * 	,A.KOKUNAIRYOHI_TOTAL		--���v-��������
	 * 	,A.GAIKOKURYOHI_TOTAL		--���v-�O������
	 * 	,A.RYOHI_TOTAL			--���v-����
	 * 	,A.SHAKIN_TOTAL			--���v-�Ӌ���
	 * 	,A.SONOTA_TOTAL			--���v-���̑�
	 * 	,A.SOSHIKI_KEITAI_NO		--�����g�D�̌`�Ԕԍ�
	 * 	,A.SOSHIKI_KEITAI			--�����g�D�̌`��
	 * 	,A.BUNTANKIN_FLG			--���S���̗L��
	 * 	,A.KOYOHI				--�����x���Ҍٗp�o��
	 * 	,A.KENKYU_NINZU			--�����Ґ�
	 * 	,A.TAKIKAN_NINZU			--���@�ւ̕��S�Ґ�
	 * 	,A.SHINSEI_KUBUN			--�V�K�p���敪
	 * 	,A.KADAI_NO_KEIZOKU		--�p�����̌����ۑ�ԍ�
	 * 	,A.SHINSEI_FLG_NO			--�\���̗L���ԍ�
	 * 	,A.SHINSEI_FLG			--�\���̗L��
	 * 	,A.KADAI_NO_SAISYU			--�ŏI�N�x�ۑ�ԍ�
	 * 	,A.KAIJIKIBO_FLG_NO		--�J����]�̗L���ԍ�
	 * 	,A.KAIJIKIBO_FLG			--�J����]�̗L��
	 * 	,A.KAIGAIBUNYA_CD			--�C�O����R�[�h
	 * 	,A.KAIGAIBUNYA_NAME		--�C�O���얼��
	 * 	,A.KAIGAIBUNYA_NAME_RYAKU		--�C�O���엪��
	 * 	,A.KANREN_SHIMEI1			--�֘A����̌�����-����1
	 * 	,A.KANREN_KIKAN1			--�֘A����̌�����-�����@��1
	 * 	,A.KANREN_BUKYOKU1			--�֘A����̌�����-��������1
	 * 	,A.KANREN_SHOKU1			--�֘A����̌�����-�E��1
	 * 	,A.KANREN_SENMON1			--�֘A����̌�����-��啪��1
	 * 	,A.KANREN_TEL1			--�֘A����̌�����-�Ζ���d�b�ԍ�1
	 * 	,A.KANREN_JITAKUTEL1		--�֘A����̌�����-����d�b�ԍ�1
	 * 	,A.KANREN_MAIL1			--�֘A����̌�����-E-mail1
	 * 	,A.KANREN_SHIMEI2			--�֘A����̌�����-����2
	 * 	,A.KANREN_KIKAN2			--�֘A����̌�����-�����@��2
	 * 	,A.KANREN_BUKYOKU2			--�֘A����̌�����-��������2
	 * 	,A.KANREN_SHOKU2			--�֘A����̌�����-�E��2
	 * 	,A.KANREN_SENMON2			--�֘A����̌�����-��啪��2
	 * 	,A.KANREN_TEL2			--�֘A����̌�����-�Ζ���d�b�ԍ�2
	 * 	,A.KANREN_JITAKUTEL2		--�֘A����̌�����-����d�b�ԍ�2
	 * 	,A.KANREN_MAIL2			--�֘A����̌�����-E-mail2
	 * 	,A.KANREN_SHIMEI3			--�֘A����̌�����-����3
	 * 	,A.KANREN_KIKAN3			--�֘A����̌�����-�����@��3
	 * 	,A.KANREN_BUKYOKU3			--�֘A����̌�����-��������3
	 * 	,A.KANREN_SHOKU3			--�֘A����̌�����-�E��3
	 * 	,A.KANREN_SENMON3			--�֘A����̌�����-��啪��3
	 * 	,A.KANREN_TEL3			--�֘A����̌�����-�Ζ���d�b�ԍ�3
	 * 	,A.KANREN_JITAKUTEL3		--�֘A����̌�����-����d�b�ԍ�3
	 * 	,A.KANREN_MAIL3			--�֘A����̌�����-E-mail3
	 * 	,A.XML_PATH			--XML�̊i�[�p�X
	 * 	,A.PDF_PATH			--PDF�̊i�[�p�X
	 * 	,A.JURI_KEKKA			--�󗝌���
	 * 	,A.JURI_BIKO			--�󗝌��ʔ��l
	 * 	,A.SUISENSHO_PATH			--���E���̊i�[�p�X
	 * 	,A.KEKKA1_ABC			--�P���R������(ABC)
	 * 	,A.KEKKA1_TEN			--�P���R������(�_��)
	 * 	,A.KEKKA1_TEN_SORTED		--�P���R������(�_����)
	 * 	,A.SHINSA1_BIKO			--�P���R�����l
	 * 	,A.KEKKA2				--�Q���R������
	 * 	,A.SOU_KEHI			--���o��i�w�U���́j
	 * 	,A.SHONEN_KEHI			--���N�x�o��i�w�U���́j
	 * 	,A.SHINSA2_BIKO			--�Ɩ��S���ҋL����
	 * 	,A.JOKYO_ID			--�\����ID
	 * 	,A.SAISHINSEI_FLG			--�Đ\���t���O
	 * 	,A.DEL_FLG			--�폜�t���O
	 * FROM
	 * 	SHINSEIDATAKANRI A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * FOR UPDATE</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>������shinsaKekkaInputInfo�̕ϐ�SystemNo���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́AexistInfo�Ɋi�[����B<br /><br />
	 * 
	 * existInfo�́ADelFlg(�폜�t���O)�̒l���A"1"(�폜�ς�)�ł������ꍇ�ɂ́A��O��throw����B<br />
	 * existInfo�́AJokyoId(�R����ID)�̒l���A"8","9","10","11"�ȊO�ł������ꍇ�ɂ́A��O��throw����B<br />
	 * �@�E8�c�R��������U�菈����<br />
	 * �@�E9�c����U��`�F�b�N����<br />
	 * �@�E10�c1���R����<br />
	 * �@�E11�c1���R������<br /><br />
	 * 
	 * 
	 * 
	 * 
	 * 
	 * (2)�ȉ���SQL���𔭍s���āA�Y������R�����ʂ��擾����B<br />
	 * (�o�C���h�����́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO		--�V�X�e����t�ԍ�
	 * 	,A.UKETUKE_NO		--�\���ԍ�
	 * 	,A.SHINSAIN_NO		--�R�����ԍ�
	 * 	,A.JIGYO_KUBUN		--���Ƌ敪
	 * 	,A.SEQ_NO			--�V�[�P���X�ԍ�
	 * 	,A.SHINSA_KUBUN		--�R���敪
	 * 	,A.SHINSAIN_NAME_KANJI_SEI	--�R�������i�����|���j
	 * 	,A.SHINSAIN_NAME_KANJI_MEI	--�R�������i�����|���j
	 * 	,A.NAME_KANA_SEI		--�R�������i�t���K�i�|���j
	 * 	,A.NAME_KANA_MEI		--�R�������i�t���K�i�|���j
	 * 	,A.SHOZOKU_NAME		--�R���������@�֖�
	 * 	,A.BUKYOKU_NAME		--�R�������ǖ�
	 * 	,A.SHOKUSHU_NAME		--�R�����E��
	 * 	,A.JIGYO_ID			--����ID
	 * 	,A.JIGYO_NAME		--���Ɩ�
	 * 	,A.BUNKASAIMOKU_CD		--�זڔԍ�
	 * 	,A.EDA_NO			--�}��
	 * 	,A.CHECKDIGIT		--�`�F�b�N�f�W�b�g
	 * 	,A.KEKKA_ABC		--�����]���iABC�j
	 * 	,A.KEKKA_TEN		--�����]���i�_���j
	 * 	,NVL(
	 * 		REPLACE(
	 * 			A.KEKKA_TEN,
	 * 			'-',
	 * 			'0'
	 * 		),'-1'
	 * 	) SORT_KEKKA_TEN
	 * 		--�\�[�g�p�B�R�����ʁi�_���j�̒lNULL��'-1'�A'-'��'0'�ɒu���j
	 * 	,A.COMMENT1		--�R�����g1
	 * 	,A.COMMENT2		--�R�����g2
	 * 	,A.COMMENT3		--�R�����g3
	 * 	,A.COMMENT4		--�R�����g4
	 * 	,A.COMMENT5		--�R�����g5
	 * 	,A.COMMENT6		--�R�����g6
	 * 	,A.KENKYUNAIYO		--�������e
	 * 	,A.KENKYUKEIKAKU		--�����v��
	 * 	,A.TEKISETSU_KAIGAI	--�K�ؐ�-�C�O
	 * 	,A.TEKISETSU_KENKYU1	--�K�ؐ�-�����i1�j
	 * 	,A.TEKISETSU		--�K�ؐ�
	 * 	,A.DATO			--�Ó���
	 * 	,A.SHINSEISHA		--������\��
	 * 	,A.KENKYUBUNTANSHA		--�������S��
	 * 	,A.HITOGENOMU		--�q�g�Q�m��
	 * 	,A.TOKUTEI		--������
	 * 	,A.HITOES			--�q�gES�זE
	 * 	,A.KUMIKAE		--��`�q�g��������
	 * 	,A.CHIRYO			--��`�q���×Տ�����
	 * 	,A.EKIGAKU		--�u�w����
	 * 	,A.COMMENTS		--�R�����g
	 * 	,A.TENPU_PATH		--�Y�t�t�@�C���i�[�p�X
	 * 	,A.SHINSA_JOKYO		--�R����
	 * 	,A.BIKO			--���l
	 * FROM
	 * 	SHINSAKEKKA A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * ORDER BY
	 * 	KEKKA_ABC ASC		--�����]���iABC�j�̏���
	 * 	,SORT_KEKKA_TEN DESC	--�����]���i�_���j�̍~��
	 * 	,SHINSAIN_NO ASC		--�R�����ԍ��̏���
	 * 	,JIGYO_KUBUN ASC		--���Ƌ敪�̏���</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>������shinsaKekkaInputInfo�̕ϐ�SystemNo���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́AShinsaKekkaInfo�̔z��Ɋi�[����B<br /><br />
	 * 
	 * 
	 * 
	 * 
	 * (3)�ȉ���SQL���𔭍s���āA�����]���}�X�^���瑍���]�����̈ꗗ��List���擾����B<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SOGO_HYOKA		--�����]��
	 * 	,A.JIGYO_KUBUN		--���Ƌ敪
	 * 	,A.TENSU			--�_��
	 * 	,A.BIKO				--���l
	 * FROM
	 * 	MASTER_SOGOHYOKA A
	 * ORDER BY
	 * 	JIGYO_KUBUN
	 * 	,SOGO_HYOKA</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * �擾����List����Map����A<br /><br />
	 * 
	 * �E�����]��<br />
	 * �E���Ƌ敪<br />
	 * �E�_��<br /><br />
	 * 
	 * �����o���B�����āAHashMap�I�u�W�F�N�g"sogoHyokaMap"�𐶐����A
	 * ���Ƌ敪�Ƒ����]����A��������String���L�[�ɁA�_�����i�[����B<br />
	 * 
	 * ���̍�Ƃ��AList����Map�̐������s���B<br /><br />
	 * 
	 * 
	 * 
	 * 
	 * (4)(2)�Ŏ擾����ShinsaKekkaInfo�̔z��́A�����]���iABC�j�Ƒ����]���i�_���j�̒l���`�F�b�N����B<br />
	 * �����]���iABC�j�Ƒ����]���i�_���j�͍��݂��Ȃ��̂ŁA�ǂ���̒l�����������āA�ꍇ�킯���s���B<br />
	 * ���̏������AShinsaKekkaInfo�̔z��̐������J��Ԃ��B<br /><br />
	 * 
	 * �@(4-1)�����]���iABC�j�̒l�����ꍇ<br /><br />
	 * 
	 * �@String"KekkaAbc"��A�����đ������킹�Ă����B
	 * �@�܂��AShinsaKekkaInfo�̎��Ƌ敪�Ƒ����]���iABC�j�̒l��p���āA(3)��Map����"�_��"�̒l���擾����B
	 * �@�l���Ȃ��ꍇ�ɂ͗�O��throw����B<br />
	 * 
	 * �@�擾�����_���́Aint�Ƃ��đ������킹��B<br /><br />
	 * 
	 * �@(4-2)�����]���i�_���j�̒l�����ꍇ<br /><br />
	 * 
	 * �@ShinsaKekkaInfo�̎��Ƌ敪�Ƒ����]���i�_���j�̒l��p���āA(3)��Map����"�_��"�̒l���擾����B<br />
	 * 
	 * �@�擾�����_���́Aint�Ƃ��đ������킹��B(4-1�ő������킹�Ă�����̂ɑ����Ă���)<br />
	 * �@�܂��A�_����String�Ƃ��āA�Ԃ�"�@"���͂���ŘA�����Ă����B<br /><br />
	 * 
	 * 
	 * 
	 * (5)���̒l���A(1)�Ŏ擾����existInfo�ɉ�����B<br /><br />
	 * 
	 * �Ekekka1Abc(�ꎟ�R������(ABC))�c(4-1)��String"KekkaAbc"��A�����đ������킹�����́B<br />
	 * �Ekekka1Ten(�ꎟ�R������(�_��))�c(4-1)�A(4-2)�ő������킹���_����int���AString�ɂ������́B<br />
	 * �Ekekka1TenSorted(�ꎟ�R������(�_����))�c(4-2)�œ_����String�Ƃ��āA�Ԃ�"�@"���͂���ŘA���������́B<br />
	 * �EjokyoId(�R����)�c"11"(1���R������)<br /><br />
	 * 
	 * (6)�擾����existInfo�ƈȉ���SQL�����g�p���āA�\���f�[�^�e�[�u���̍X�V���s���B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSEIDATAKANRI
	 * SET			
	 * 	SYSTEM_NO = ?				--�V�X�e����t�ԍ�
	 * 	,UKETUKE_NO = ?				--�\���ԍ�
	 * 	,JIGYO_ID = ?				--����ID
	 * 	,NENDO = ?				--�N�x
	 * 	,KAISU = ?				--��
	 * 	,JIGYO_NAME = ?				--���Ɩ�
	 * 	,SHINSEISHA_ID = ?				--�\����ID
	 * 	,SAKUSEI_DATE = ?				--�\�����쐬��
	 * 	,SHONIN_DATE = ?				--�����@�֏��F��
	 * 	,JYURI_DATE = ?				--�w�U�󗝓�
	 * 	,NAME_KANJI_SEI = ?			--�\���Ҏ����i������-���j
	 * 	,NAME_KANJI_MEI = ?			--�\���Ҏ����i������-���j
	 * 	,NAME_KANA_SEI = ?				--�\���Ҏ����i�t���K�i-���j
	 * 	,NAME_KANA_MEI = ?				--�\���Ҏ����i�t���K�i-���j
	 * 	,NAME_RO_SEI = ?				--�\���Ҏ����i���[�}��-���j
	 * 	,NAME_RO_MEI = ?				--�\���Ҏ����i���[�}��-���j
	 * 	,NENREI = ?				--�N��
	 * 	,KENKYU_NO = ?				--�\���Ҍ����Ҕԍ�
	 * 	,SHOZOKU_CD = ?				--�����@�փR�[�h
	 * 	,SHOZOKU_NAME = ?				--�����@�֖�
	 * 	,SHOZOKU_NAME_RYAKU = ?			--�����@�֖��i���́j
	 * 	,BUKYOKU_CD = ?				--���ǃR�[�h
	 * 	,BUKYOKU_NAME = ?				--���ǖ�
	 * 	,BUKYOKU_NAME_RYAKU = ?			--���ǖ��i���́j
	 * 	,SHOKUSHU_CD = ?				--�E���R�[�h
	 * 	,SHOKUSHU_NAME_KANJI = ?			--�E���i�a���j
	 * 	,SHOKUSHU_NAME_RYAKU = ?			--�E���i���́j
	 * 	,ZIP = ?					--�X�֔ԍ�
	 * 	,ADDRESS = ?				--�Z��
	 * 	,TEL = ?					--TEL
	 * 	,FAX = ?					--FAX
	 * 	,EMAIL = ?				--E-Mail
	 * 	,SENMON = ?				--���݂̐��
	 * 	,GAKUI = ?				--�w��
	 * 	,BUNTAN = ?				--�������S
	 * 	,KADAI_NAME_KANJI = ?			--�����ۑ薼(�a���j
	 * 	,KADAI_NAME_EIGO = ?			--�����ۑ薼(�p���j
	 * 	,JIGYO_KUBUN = ?				--���Ƌ敪
	 * 	,SHINSA_KUBUN = ?				--�R���敪
	 * 	,SHINSA_KUBUN_MEISHO = ?			--�R���敪����
	 * 	,BUNKATSU_NO = ?				--�����ԍ�
	 * 	,BUNKATSU_NO_MEISHO = ?			--�����ԍ�����
	 * 	,KENKYU_TAISHO = ?				--�����Ώۂ̗ތ^
	 * 	,KEI_NAME_NO = ?				--�n���̋敪�ԍ�
	 * 	,KEI_NAME = ?				--�n���̋敪
	 * 	,KEI_NAME_RYAKU = ?			--�n���̋敪����
	 * 	,BUNKASAIMOKU_CD = ?			--�זڔԍ�
	 * 	,BUNYA_NAME = ?				--����
	 * 	,BUNKA_NAME = ?				--����
	 * 	,SAIMOKU_NAME = ?				--�ז�
	 * 	,BUNKASAIMOKU_CD2 = ?			--�זڔԍ�2
	 * 	,BUNYA_NAME2 = ?				--����2
	 * 	,BUNKA_NAME2 = ?				--����2
	 * 	,SAIMOKU_NAME2 = ?				--�ז�2
	 * 	,KANTEN_NO = ?				--���E�̊ϓ_�ԍ�
	 * 	,KANTEN = ?				--���E�̊ϓ_
	 * 	,KANTEN_RYAKU = ?		--���E�̊ϓ_����
	 * 	,KEIHI1 = ?				--1�N�ڌ����o��
	 * 	,BIHINHI1 = ?			--1�N�ڐݔ����i��
	 * 	,SHOMOHINHI1 = ?		--1�N�ڏ��Օi��
	 * 	,KOKUNAIRYOHI1 = ?		--1�N�ڍ�������
	 * 	,GAIKOKURYOHI1 = ?		--1�N�ڊO������
	 * 	,RYOHI1 = ?				--1�N�ڗ���
	 * 	,SHAKIN1 = ?			--1�N�ڎӋ���
	 * 	,SONOTA1 = ?			--1�N�ڂ��̑�
	 * 	,KEIHI2 = ?				--2�N�ڌ����o��
	 * 	,BIHINHI2 = ?			--2�N�ڐݔ����i��
	 * 	,SHOMOHINHI2 = ?		--2�N�ڏ��Օi��
	 * 	,KOKUNAIRYOHI2 = ?		--2�N�ڍ�������
	 * 	,GAIKOKURYOHI2 = ?		--2�N�ڊO������
	 * 	,RYOHI2 = ?				--2�N�ڗ���
	 * 	,SHAKIN2 = ?			--2�N�ڎӋ���
	 * 	,SONOTA2 = ?			--2�N�ڂ��̑�
	 * 	,KEIHI3 = ?				--3�N�ڌ����o��
	 * 	,BIHINHI3 = ?			--3�N�ڐݔ����i��
	 * 	,SHOMOHINHI3 = ?		--3�N�ڏ��Օi��
	 * 	,KOKUNAIRYOHI3 = ?		--3�N�ڍ�������
	 * 	,GAIKOKURYOHI3 = ?		--3�N�ڊO������
	 * 	,RYOHI3 = ?				--3�N�ڗ���
	 * 	,SHAKIN3 = ?			--3�N�ڎӋ���
	 * 	,SONOTA3 = ?			--3�N�ڂ��̑�
	 * 	,KEIHI4 = ?				--4�N�ڌ����o��
	 * 	,BIHINHI4 = ?			--4�N�ڐݔ����i��
	 * 	,SHOMOHINHI4 = ?		--4�N�ڏ��Օi��
	 * 	,KOKUNAIRYOHI4 = ?		--4�N�ڍ�������
	 * 	,GAIKOKURYOHI4 = ?		--4�N�ڊO������
	 * 	,RYOHI4 = ?				--4�N�ڗ���
	 * 	,SHAKIN4 = ?			--4�N�ڎӋ���
	 * 	,SONOTA4 = ?			--4�N�ڂ��̑�
	 * 	,KEIHI5 = ?				--5�N�ڌ����o��
	 * 	,BIHINHI5 = ?			--5�N�ڐݔ����i��
	 * 	,SHOMOHINHI5 = ?		--5�N�ڏ��Օi��
	 * 	,KOKUNAIRYOHI5 = ?		--5�N�ڍ�������
	 * 	,GAIKOKURYOHI5 = ?		--5�N�ڊO������
	 * 	,RYOHI5 = ?				--5�N�ڗ���
	 * 	,SHAKIN5 = ?			--5�N�ڎӋ���
	 * 	,SONOTA5 = ?			--5�N�ڂ��̑�
	 * 	,KEIHI_TOTAL = ?		--���v-�����o��
	 * 	,BIHINHI_TOTAL = ?		--���v-�ݔ����i��
	 * 	,SHOMOHINHI_TOTAL = ?	--���v-���Օi��
	 * 	,KOKUNAIRYOHI_TOTAL = ?	--���v-��������
	 * 	,GAIKOKURYOHI_TOTAL = ?	--���v-�O������
	 * 	,RYOHI_TOTAL = ?		--���v-����
	 * 	,SHAKIN_TOTAL = ?		--���v-�Ӌ���
	 * 	,SONOTA_TOTAL = ?		--���v-���̑�
	 * 	,SOSHIKI_KEITAI_NO = ?		--�����g�D�̌`�Ԕԍ�
	 * 	,SOSHIKI_KEITAI = ?			--�����g�D�̌`��
	 * 	,BUNTANKIN_FLG = ?			--���S���̗L��
	 * 	,KOYOHI = ?				--�����x���Ҍٗp�o��
	 * 	,KENKYU_NINZU = ?			--�����Ґ�
	 * 	,TAKIKAN_NINZU = ?			--���@�ւ̕��S�Ґ�
	 * 	,SHINSEI_KUBUN = ?			--�V�K�p���敪
	 * 	,KADAI_NO_KEIZOKU = ?		--�p�����̌����ۑ�ԍ�
	 * 	,SHINSEI_FLG_NO = ?			--�����v��ŏI�N�x�O�N�x�̉���
	 * 	,SHINSEI_FLG = ?				--�\���̗L��
	 * 	,KADAI_NO_SAISYU = ?			--�ŏI�N�x�ۑ�ԍ�
	 * 	,KAIJIKIBO_FLG_NO = ?			--�J����]�̗L���ԍ�
	 * 	,KAIJIKIBO_FLG = ?				--�J����]�̗L��
	 * 	,KAIGAIBUNYA_CD = ?			--�C�O����R�[�h
	 * 	,KAIGAIBUNYA_NAME = ?			--�C�O���얼��
	 * 	,KAIGAIBUNYA_NAME_RYAKU = ?			--�C�O���엪��
	 * 	,KANREN_SHIMEI1 = ?			--�֘A����̌�����-����1
	 * 	,KANREN_KIKAN1 = ?			--�֘A����̌�����-�����@��1
	 * 	,KANREN_BUKYOKU1 = ?		--�֘A����̌�����-��������1
	 * 	,KANREN_SHOKU1 = ?			--�֘A����̌�����-�E��1
	 * 	,KANREN_SENMON1 = ?			--�֘A����̌�����-��啪��1
	 * 	,KANREN_TEL1 = ?			--�֘A����̌�����-�Ζ���d�b�ԍ�1
	 * 	,KANREN_JITAKUTEL1 = ?		--�֘A����̌�����-����d�b�ԍ�1
	 * 	,KANREN_MAIL1 = ?			--�֘A����̌�����-E-mail1
	 * 	,KANREN_SHIMEI2 = ?			--�֘A����̌�����-����2
	 * 	,KANREN_KIKAN2 = ?			--�֘A����̌�����-�����@��2
	 * 	,KANREN_BUKYOKU2 = ?		--�֘A����̌�����-��������2
	 * 	,KANREN_SHOKU2 = ?			--�֘A����̌�����-�E��2
	 * 	,KANREN_SENMON2 = ?			--�֘A����̌�����-��啪��2
	 * 	,KANREN_TEL2 = ?			--�֘A����̌�����-�Ζ���d�b�ԍ�2
	 * 	,KANREN_JITAKUTEL2 = ?		--�֘A����̌�����-����d�b�ԍ�2
	 * 	,KANREN_MAIL2 = ?			--�֘A����̌�����-E-mail2
	 * 	,KANREN_SHIMEI3 = ?			--�֘A����̌�����-����3
	 * 	,KANREN_KIKAN3 = ?			--�֘A����̌�����-�����@��3
	 * 	,KANREN_BUKYOKU3 = ?		--�֘A����̌�����-��������3
	 * 	,KANREN_SHOKU3 = ?			--�֘A����̌�����-�E��3
	 * 	,KANREN_SENMON3 = ?			--�֘A����̌�����-��啪��3
	 * 	,KANREN_TEL3 = ?			--�֘A����̌�����-�Ζ���d�b�ԍ�3
	 * 	,KANREN_JITAKUTEL3 = ?		--�֘A����̌�����-����d�b�ԍ�3
	 * 	,KANREN_MAIL3 = ?			--�֘A����̌�����-E-mail3
	 * 	,XML_PATH = ?				--XML�̊i�[�p�X
	 * 	,PDF_PATH = ?				--PDF�̊i�[�p�X
	 * 	,JURI_KEKKA = ?				--�󗝌���
	 * 	,JURI_BIKO = ?				--�󗝌��ʔ��l
	 * 	,SUISENSHO_PATH = ?			--���E���̊i�[�p�X
	 * 	,KEKKA1_ABC = ?				--�P���R������(ABC)
	 * 	,KEKKA1_TEN = ?				--�P���R������(�_��)
	 * 	,KEKKA1_TEN_SORTED = ?			--�P���R������(�_����)
	 * 	,SHINSA1_BIKO = ?				--�P���R�����l
	 * 	,KEKKA2 = ?				--�Q���R������
	 * 	,SOU_KEHI = ?				--���o��i�w�U���́j
	 * 	,SHONEN_KEHI = ?				--���N�x�o��i�w�U���́j
	 * 	,SHINSA2_BIKO = ?				--�Ɩ��S���ҋL����
	 * 	,JOKYO_ID = ?				--�\����ID
	 * 	,SAISHINSEI_FLG = ?			--�Đ\���t���O
	 * 	,DEL_FLG = ?				--�폜�t���O
	 * WHERE
	 * 	SYSTEM_NO = ?;				--�V�X�e����t�ԍ�</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * �l�́AShinseiDataInfo�̕ϐ��ł���B<br />
	 * �l�ɓY�����̂�����̂́A���ꂼ��AShinseiDataInfo�����I�u�W�F�N�g�̒l�B<br /><br />
	 * 
	 * �@�EA:DaihyouInfo<br />
	 * �@�EB:KadaiInfo<br />
	 * �@�EC:KenkyuKeihiInfo<br />
	 * �@�ED:KenkyuKeihiSoukeiInfo<br />
	 * �@�EE:KanrenBunyaKenkyushaInfo<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>SystemNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>uketukeNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>jigyoId</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�N�x</td><td>nendo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��</td><td>kaisu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ɩ�</td><td>jigyoName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>ShinseishaId</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\�����쐬��</td><td>SakuseiDate</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֏��F��</td><td>ShoninDate</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�w�U�󗝓�</td><td>JyuriDate</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i������-���j</td><td>A:NameKanjiSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i������-���j</td><td>A:NameKanjiMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i�t���K�i-���j</td><td>A:NameKanaSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i�t���K�i-���j</td><td>A:NameKanaMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i���[�}��-���j</td><td>A:NameRoSei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Ҏ����i���[�}��-���j</td><td>A:NameRoMei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�N��</td><td>A:Nenrei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Ҍ����Ҕԍ�</td><td>A:KenkyuNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>A:ShozokuCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�</td><td>A:ShozokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖��i���́j</td><td>A:ShozokuNameRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><td>A:BukyokuCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�</td><td>A:BukyokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ��i���́j</td><td>A:BukyokuNameRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E���R�[�h</td><td>A:ShokushuCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E���i�a���j</td><td>A:ShokushuNameKanji</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E���i���́j</td><td>A:ShokushuNameRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�X�֔ԍ�</td><td>A:Zip</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Z��</td><td>A:Address</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>TEL</td><td>A:Tel</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>FAX</td><td>A:Fax</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>E-Mail</td><td>A:Email</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���݂̐��</td><td>A:Senmon</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�w��</td><td>A:Gakui</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�������S</td><td>A:Buntan</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����ۑ薼(�a���j</td><td>B:KadaiNameKanji</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����ۑ薼(�p���j</td><td>B:KadaiNameEigo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>B:JigyoKubun</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R���敪</td><td>B:ShinsaKubun</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R���敪����</td><td>B:ShinsaKubunMeisho</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����ԍ�</td><td>B:BunkatsuNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����ԍ�����</td><td>B:BunkatsuNoMeisho</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ώۂ̗ތ^</td><td>B:KenkyuTaisho</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�n���̋敪�ԍ�</td><td>B:KeiNameNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�n���̋敪</td><td>B:KeiName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�n���̋敪����</td><td>B:KeiNameRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�זڔԍ�</td><td>B:BunkaSaimokuCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����</td><td>B:Bunya</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����</td><td>B:Bunka</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�ז�</td><td>B:SaimokuName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�זڔԍ�2</td><td>B:BunkaSaimokuCd2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����2</td><td>B:Bunya2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����2</td><td>B:Bunka2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�ז�2</td><td>B:SaimokuName2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���E�̊ϓ_�ԍ�</td><td>B:KantenNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���E�̊ϓ_</td><td>B:Kanten</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���E�̊ϓ_����</td><td>B:KantenRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1�N�ڌ����o��</td><td>C:Keihi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1�N�ڐݔ����i��</td><td>C:Bihinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1�N�ڏ��Օi��</td><td>C:Shomohinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1�N�ڍ�������</td><td>C:Kokunairyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1�N�ڊO������</td><td>C:Gaikokuryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1�N�ڗ���</td><td>C:Ryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1�N�ڎӋ���</td><td>C:Shakin</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1�N�ڂ��̑�</td><td>C:Sonota</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2�N�ڌ����o��</td><td>C:Keihi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2�N�ڐݔ����i��</td><td>C:Bihinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2�N�ڏ��Օi��</td><td>C:Shomohinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2�N�ڍ�������</td><td>C:Kokunairyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2�N�ڊO������</td><td>C:Gaikokuryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2�N�ڗ���</td><td>C:Ryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2�N�ڎӋ���</td><td>C:Shakin</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2�N�ڂ��̑�</td><td>C:Sonota</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3�N�ڌ����o��</td><td>C:Keihi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3�N�ڐݔ����i��</td><td>C:Bihinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3�N�ڏ��Օi��</td><td>C:Shomohinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3�N�ڍ�������</td><td>C:Kokunairyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3�N�ڊO������</td><td>C:Gaikokuryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3�N�ڗ���</td><td>C:Ryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3�N�ڎӋ���</td><td>C:Shakin</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>3�N�ڂ��̑�</td><td>C:Sonota</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4�N�ڌ����o��</td><td>C:Keihi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4�N�ڐݔ����i��</td><td>C:Bihinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4�N�ڏ��Օi��</td><td>C:Shomohinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4�N�ڍ�������</td><td>C:Kokunairyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4�N�ڊO������</td><td>C:Gaikokuryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4�N�ڗ���</td><td>C:Ryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4�N�ڎӋ���</td><td>C:Shakin</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>4�N�ڂ��̑�</td><td>C:Sonota</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5�N�ڌ����o��</td><td>C:Keihi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5�N�ڐݔ����i��</td><td>C:Bihinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5�N�ڏ��Օi��</td><td>C:Shomohinhi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5�N�ڍ�������</td><td>C:Kokunairyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5�N�ڊO������</td><td>C:Gaikokuryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5�N�ڗ���</td><td>C:Ryohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5�N�ڎӋ���</td><td>C:Shakin</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>5�N�ڂ��̑�</td><td>C:Sonota</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���v-�����o��</td><td>D:KeihiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���v-�ݔ����i��</td><td>D:BihinhiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���v-���Օi��</td><td>D:ShomohinhiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���v-��������</td><td>D:KokunairyohiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���v-�O������</td><td>D:GaikokuryohiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���v-����</td><td>D:RyohiTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���v-�Ӌ���</td><td>D:ShakinTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���v-���̑�</td><td>D:SonotaTotal</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����g�D�̌`�Ԕԍ�</td><td>soshikiKeitaiNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����g�D�̌`��</td><td>soshikiKeitai</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���S���̗L��</td><td>buntankinFlg</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����x���Ҍٗp�o��</td><td>koyohi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ґ�</td><td>kenkyuNinzu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���@�ւ̕��S�Ґ�</td><td>takikanNinzu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�K�p���敪</td><td>shinseiKubun</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�p�����̌����ۑ�ԍ�</td><td>kadaiNoKeizoku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����v��ŏI�N�x�O�N�x�̉���</td><td>shinseiFlgNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���̗L��</td><td>shinseiFlg</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�ŏI�N�x�ۑ�ԍ�</td><td>kadaiNoSaisyu</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�J����]�̗L���ԍ�</td><td>kaijikiboFlgNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�J����]�̗L��</td><td>kaijiKiboFlg</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�C�O����R�[�h</td><td>kaigaibunyaCd</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�C�O���얼��</td><td>kaigaibunyaName</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�C�O���엪��</td><td>kaigaibunyaNameRyaku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-����1</td><td>E:KanrenShimei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-�����@��1</td><td>E:KanrenKikan</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-��������1</td><td>E:KanrenBukyoku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-�E��1</td><td>E:KanrenShoku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-��啪��1</td><td>E:KanrenSenmon</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-�Ζ���d�b�ԍ�1</td><td>E:KanrenTel</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-����d�b�ԍ�1</td><td>E:KanrenJitakuTel</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-E-mail1</td><td>E:KanrenMail</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-����2</td><td>E:KanrenShimei</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-�����@��2</td><td>E:KanrenKikan</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-��������2</td><td>E:KanrenBukyoku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-�E��2</td><td>E:KanrenShoku</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-��啪��2</td><td>E:KanrenSenmon</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-�Ζ���d�b�ԍ�2</td><td>E:KanrenTel</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-����d�b�ԍ�2</td><td>E:KanrenJitakuTel</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�֘A����̌�����-E-mail2</td><td>E:KanrenMail</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>XML�̊i�[�p�X</td><td>XmlPath</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>PDF�̊i�[�p�X</td><td>PdfPath</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�󗝌���</td><td>JuriKekka</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�󗝌��ʔ��l</td><td>JuriBiko</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���E���̊i�[�p�X</td><td>SuisenshoPath</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�P���R������(ABC)</td><td>Kekka1Abc</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�P���R������(�_��)</td><td>Kekka1Ten</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�P���R������(�_����)</td><td>Kekka1TenSorted</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�P���R�����l</td><td>Shinsa1Biko</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Q���R������</td><td>Kekka2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���o��i�w�U���́j</td><td>SouKehi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���N�x�o��i�w�U���́j</td><td>ShonenKehi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S���ҋL����</td><td>Shinsa2Biko</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\����ID</td><td>JokyoId</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Đ\���t���O</td><td>SaishinseiFlg</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�폜�t���O</td><td>DelFlg</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>systemNo</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param shinsaKekkaInputInfo ShinsaKekkaInputInfo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void regist1stShinsaKekka(
		UserInfo userInfo,
		ShinsaKekkaInputInfo shinsaKekkaInputInfo)
		throws NoDataFoundException, ApplicationException
	{
		Connection   connection  = null;
		boolean     success     = false;
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			
			//�V�X�e����t�ԍ�
			String systemNo = shinsaKekkaInputInfo.getSystemNo();
			
			//--------------------
			// �R�����ʓo�^
			//--------------------						
			//�R������DAO
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);			
			//�����f�[�^���擾����
			ShinsaKekkaPk shinsaKekkaPk = (ShinsaKekkaPk)shinsaKekkaInputInfo;
			ShinsaKekkaInfo shinsaKekkaInfo = null;
			try{
				shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfoForLock(connection, shinsaKekkaPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʏ��擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}			

			//---�R�����ʃt�@�C���i�[---
			FileResource kekkaFileRes = shinsaKekkaInputInfo.getHyokaFileRes();
			String outPath = null;
			if(kekkaFileRes != null){
				//---�t�@�C�����---
				String   jigyoId    = shinsaKekkaInputInfo.getJigyoId();
				String   shinsainNo = shinsaKekkaInputInfo.getShinsainNo();
				String extension = FileUtil.getExtention(kekkaFileRes.getName());		//�g���q
				String[] pathInfo   = new String[]{jigyoId, systemNo, shinsainNo, extension};	
				outPath             = MessageFormat.format(SHINSEI_KEKKA_FOLDER, pathInfo);	//�o�͐�
				File     outFile    = new File(outPath);
			
				//�g���q���������t�@�C�������R�����ԍ��Ɠ����t�@�C���͍폜
				File[] list = outFile.getParentFile().listFiles();
				if (list != null && list.length > 0) {
					for (int i = 0; i < list.length; i++) {
						int index = list[i].getName().lastIndexOf(".");
						if(index > -1){
							String str = list[i].getName().substring(0, index);
							if(shinsainNo.equals(str)){
								FileUtil.delete(list[i]);
							}
						}
					}
				}
			
				try{
					FileUtil.writeFile(outFile, kekkaFileRes.getBinary());
				}catch(IOException e){
					throw new ApplicationException(
						"�R�����ʃt�@�C���i�[���ɃG���[���������܂����B",
						new ErrorInfo("errors.7001"),
						e);
				}
				//-------�Y�t�t�@�C�������Z�b�g
				shinsaKekkaInfo.setTenpuPath(outPath);								//�Y�t�t�@�C���p�X
			}

			
			//---DB�X�V---
			try {
				//�X�V�f�[�^���Z�b�g����		
				shinsaKekkaInfo.setKekkaAbc(shinsaKekkaInputInfo.getKekkaAbc());				//�����]���iABC�j
				shinsaKekkaInfo.setKekkaTen(shinsaKekkaInputInfo.getKekkaTen());				//�����]���i�_���j
				shinsaKekkaInfo.setComment1(shinsaKekkaInputInfo.getComment1());				//�R�����g1
				shinsaKekkaInfo.setComment2(shinsaKekkaInputInfo.getComment2());				//�R�����g2
				shinsaKekkaInfo.setComment3(shinsaKekkaInputInfo.getComment3());				//�R�����g3
				shinsaKekkaInfo.setComment4(shinsaKekkaInputInfo.getComment4());				//�R�����g4
				shinsaKekkaInfo.setComment5(shinsaKekkaInputInfo.getComment5());				//�R�����g5
				shinsaKekkaInfo.setComment6(shinsaKekkaInputInfo.getComment6());				//�R�����g6
				shinsaKekkaInfo.setKenkyuNaiyo(shinsaKekkaInputInfo.getKenkyuNaiyo());			//�������e
				shinsaKekkaInfo.setKenkyuKeikaku(shinsaKekkaInputInfo.getKenkyuKeikaku());		//�����v��
				shinsaKekkaInfo.setTekisetsuKaigai(shinsaKekkaInputInfo.getTekisetsuKaigai());	//�K�ؐ�-�C�O
				shinsaKekkaInfo.setTekisetsuKenkyu1(shinsaKekkaInputInfo.getTekisetsuKenkyu1());//�K�ؐ�-����(1)
				shinsaKekkaInfo.setTekisetsu(shinsaKekkaInputInfo.getTekisetsu());				//�K�ؐ�
				shinsaKekkaInfo.setDato(shinsaKekkaInputInfo.getDato());						//�Ó���
				shinsaKekkaInfo.setShinseisha(shinsaKekkaInputInfo.getShinseisha());			//������\��
				shinsaKekkaInfo.setKenkyuBuntansha(shinsaKekkaInputInfo.getKenkyuBuntansha());	//�������S��
				shinsaKekkaInfo.setHitogenomu(shinsaKekkaInputInfo.getHitogenomu());			//�q�g�Q�m��
				shinsaKekkaInfo.setTokutei(shinsaKekkaInputInfo.getTokutei());					//������
				shinsaKekkaInfo.setHitoEs(shinsaKekkaInputInfo.getHitoEs());					//�q�gES�זE
				shinsaKekkaInfo.setKumikae(shinsaKekkaInputInfo.getKumikae());					//��`�q�g��������
				shinsaKekkaInfo.setChiryo(shinsaKekkaInputInfo.getChiryo());					//��`�q���×Տ�����
				shinsaKekkaInfo.setEkigaku(shinsaKekkaInputInfo.getEkigaku());					//�u�w����
				shinsaKekkaInfo.setComments(shinsaKekkaInputInfo.getComments());				//�R�����g
				
				//2005.10.26 kainuma
				shinsaKekkaInfo.setRigai(shinsaKekkaInputInfo.getRigai());						//���Q�֌W
				shinsaKekkaInfo.setWakates(shinsaKekkaInputInfo.getWakates());					//���S�@2007/5/9
				shinsaKekkaInfo.setJuyosei(shinsaKekkaInputInfo.getJuyosei());					//�w�p�I�d�v���E�Ó���
				shinsaKekkaInfo.setDokusosei(shinsaKekkaInputInfo.getDokusosei());				//�Ƒn���E�v�V��
				shinsaKekkaInfo.setHakyukoka(shinsaKekkaInputInfo.getHakyukoka());				//�g�y���ʁE���Ր�
				shinsaKekkaInfo.setSuikonoryoku(shinsaKekkaInputInfo.getSuikonoryoku());		//���s�\�́E���̓K�ؐ�
				shinsaKekkaInfo.setJinken(shinsaKekkaInputInfo.getJinken());					//�l���̕ی�E�@�ߓ��̏���
				shinsaKekkaInfo.setBuntankin(shinsaKekkaInputInfo.getBuntankin());				//���S���z��
				shinsaKekkaInfo.setOtherComment(shinsaKekkaInputInfo.getOtherComment());		//���̑��R�����g
				//
				
				//�R���������Z�b�g����
				shinsaKekkaInfo.setShinsainNameKanjiSei(userInfo.getShinsainInfo().getNameKanjiSei());	//�R�������i����-���j
				shinsaKekkaInfo.setShinsainNameKanjiMei(userInfo.getShinsainInfo().getNameKanjiMei());	//�R�������i����-���j
				shinsaKekkaInfo.setNameKanaSei(userInfo.getShinsainInfo().getNameKanaSei());			//�R�������i�t���K�i�|���j
				shinsaKekkaInfo.setNameKanaMei(userInfo.getShinsainInfo().getNameKanaMei());			//�R�������i�t���K�i�|���j
				shinsaKekkaInfo.setShozokuName(userInfo.getShinsainInfo().getShozokuName());			//�R���������@�֖�				
				shinsaKekkaInfo.setBukyokuName(userInfo.getShinsainInfo().getBukyokuName());			//�R�������ǖ�
				shinsaKekkaInfo.setShokushuName(userInfo.getShinsainInfo().getShokushuName());			//�R�����E��
								
				shinsaDao.updateShinsaKekkaInfo(connection, shinsaKekkaInfo);
			} catch (DataAccessException e) {
				throw new ApplicationException(
					"�R�����ʏ��X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} catch (ApplicationException e) {
				throw new ApplicationException(
					"�R�����ʏ��X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}			
					
			//--------------------
			// �\���f�[�^�X�V
			//--------------------			
			//�\���f�[�^�Ǘ�DAO
			ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo);
						
			//�r������̂��ߊ����f�[�^���擾����
			ShinseiDataPk shinseiDataPk = new ShinseiDataPk(systemNo);
			ShinseiDataInfo existInfo = null;
			try{
				existInfo = dao.selectShinseiDataInfoForLock(connection, shinseiDataPk, true);
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
			if(FLAG_APPLICATION_DELETE.equals(delFlag))
			{
				throw new ApplicationException(
					"���Y�\���f�[�^�͍폜����Ă��܂��BSystemNo=" + systemNo,
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
					+ systemNo,
					new ErrorInfo("errors.9012"));
			}			
			
			//---�R�������`�F�b�N---
			//�Ȍ���́A�R�������߂��Ă����͂n�j
//			if(!checkShinsaKigen(userInfo, existInfo)){
//				throw new ApplicationException(
//					"���Y���Ƃ͐R���������߂��Ă��܂��BSystemNo="
//					+ systemNo,
//					new ErrorInfo("errors.9007"));
//			}

			//---�R�����ʃ��R�[�h�擾�i����ABC�̏����j---
			ShinsaKekkaInfo[] shinsaKekkaInfoArray = null;						
			try{
				shinsaKekkaInfoArray = shinsaDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}

			//---�����]���}�X�^���瑍���]�����̈ꗗ���擾����---					
			HashMap sogoHyokaMap = new HashMap();
			List hyokaList = MasterSogoHyokaInfoDao.selectSogoHyokaInfoList(connection);	
			Iterator iterator = hyokaList.iterator();
			while(iterator.hasNext()){
				Map map = (Map)iterator.next();
				String sogoHyoka = (String) map.get("SOGO_HYOKA");		//�����]��
				String jigyoKubun = new Integer(((Number) map.get("JIGYO_KUBUN")).intValue()).toString();	//���Ƌ敪
				String tensu =  new Integer(((Number) map.get("TENSU")).intValue()).toString();			//�_��
				sogoHyokaMap.put(jigyoKubun + sogoHyoka, tensu);		//�L�[�F���Ƌ敪+�����]���A�l�F�_�� 
			}	
			
			//---DB�X�V---
			try {
				String kekkaAbc = "";
				int intKekkaTen = 0;
				String kekkaTenSorted = "";
				boolean kekkaTenFlag = false;
				for(int i=0; i<shinsaKekkaInfoArray.length; i++){
					try{
						//�����]���iABC�j�Ƒ����]���i�_���j�͍��݂��Ȃ�
						if(shinsaKekkaInfoArray[i].getKekkaAbc() != null){
							kekkaAbc = kekkaAbc + shinsaKekkaInfoArray[i].getKekkaAbc();
							String tensu = (String) sogoHyokaMap.get(shinsaKekkaInfoArray[i].getJigyoKubun()
														+ shinsaKekkaInfoArray[i].getKekkaAbc());
							if(tensu == null){
								throw new ApplicationException(
									"�����]���}�X�^�Ɉ�v����f�[�^�����݂��܂���B�����L�[�F�����]��'"
									+ shinsaKekkaInfoArray[i].getKekkaAbc() 
									+ "',���Ƌ敪�F'" +  shinsaKekkaInfoArray[i].getJigyoKubun() + "'",
									new ErrorInfo("errors.4002"));	
							}
							intKekkaTen = intKekkaTen
												+ Integer.parseInt((String) sogoHyokaMap.get(
																shinsaKekkaInfoArray[i].getJigyoKubun()
																	 + shinsaKekkaInfoArray[i].getKekkaAbc()));
							kekkaTenFlag = true;	//1�ł��_�����ݒ肳��Ă����ꍇ[true]						
						}else if(shinsaKekkaInfoArray[i].getKekkaTen() != null){
								//�P���R������(�_��)
								intKekkaTen = intKekkaTen
													+ Integer.parseInt((String) sogoHyokaMap.get(
																	shinsaKekkaInfoArray[i].getJigyoKubun()
																		 + shinsaKekkaInfoArray[i].getKekkaTen()));
								
								//�P���R������(�_����)
								if(kekkaTenSorted.equals("")){
									kekkaTenSorted = kekkaTenSorted + shinsaKekkaInfoArray[i].getKekkaTen();
								}else{
									kekkaTenSorted = kekkaTenSorted + " " + shinsaKekkaInfoArray[i].getKekkaTen();									
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
				existInfo.setJokyoId(StatusCode.STATUS_1st_SHINSA_KANRYO);		//�\����
				dao.updateShinseiDataInfo(connection, existInfo, true);
				
				success = true;
				
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
			
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"�R�����ʏ��A�\�����DB�X�V���ɃG���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {							
				DatabaseUtil.closeConnection(connection);
			}
		}		
	}
		
	/**
	 * �R�����ʂ̎擾.<br /><br />
	 * 
	 * ���N���X�̃��\�b�h<br />
	 * �EgetShinsaKekkaReferenceInfo(userInfo, shinseiDataPk)<br />
	 * �EgetShinsaKekka2nd(userInfo, shinseiDataPk)<br />
	 * �ɂ��擾�����l��Map�Ɋi�[���A�ԋp����B<br /><br />
	 * 
	 * �i�[���̃L�[�́A���ꂼ��<br />
	 * �Ekey_shinsakekka_1st<br />
	 * �Ekey_shinsakekka_2nd<br />
	 * �ł���B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return Map
	 * @throws NoDataFoundException
	 * @throws ApplicationException
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
	 * �ꎟ�R�����ʂ̎擾.<br /><br />
	 * 
	 * 1.�ȉ���SQL���𔭍s���āA�Y������\���f�[�^���擾����B<br />
	 * (�o�C���h�����́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 * 	,A.UKETUKE_NO			--�\���ԍ�
	 * 	,A.JIGYO_ID			--����ID
	 * 	,A.NENDO				--�N�x
	 * 	,A.KAISU				--��
	 * 	,A.JIGYO_NAME			--���Ɩ�
	 * 	,A.SHINSEISHA_ID			--�\����ID
	 * 	,A.SAKUSEI_DATE			--�\�����쐬��
	 * 	,A.SHONIN_DATE			--�����@�֏��F��
	 * 	,A.JYURI_DATE			--�w�U�󗝓�
	 * 	,A.NAME_KANJI_SEI			--�\���Ҏ����i������-���j
	 * 	,A.NAME_KANJI_MEI			--�\���Ҏ����i������-���j
	 * 	,A.NAME_KANA_SEI			--�\���Ҏ����i�t���K�i-���j
	 * 	,A.NAME_KANA_MEI			--�\���Ҏ����i�t���K�i-���j
	 * 	,A.NAME_RO_SEI			--�\���Ҏ����i���[�}��-���j
	 * 	,A.NAME_RO_MEI			--�\���Ҏ����i���[�}��-���j
	 * 	,A.NENREI				--�N��
	 * 	,A.KENKYU_NO			--�\���Ҍ����Ҕԍ�
	 * 	,A.SHOZOKU_CD			--�����@�փR�[�h
	 * 	,A.SHOZOKU_NAME			--�����@�֖�
	 * 	,A.SHOZOKU_NAME_RYAKU		--�����@�֖��i���́j
	 * 	,A.BUKYOKU_CD			--���ǃR�[�h
	 * 	,A.BUKYOKU_NAME			--���ǖ�
	 * 	,A.BUKYOKU_NAME_RYAKU		--���ǖ��i���́j
	 * 	,A.SHOKUSHU_CD			--�E���R�[�h
	 * 	,A.SHOKUSHU_NAME_KANJI		--�E���i�a���j
	 * 	,A.SHOKUSHU_NAME_RYAKU		--�E���i���́j
	 * 	,A.ZIP				--�X�֔ԍ�
	 * 	,A.ADDRESS			--�Z��
	 * 	,A.TEL				--TEL
	 * 	,A.FAX				--FAX
	 * 	,A.EMAIL				--E-Mail
	 * 	,A.SENMON				--���݂̐��
	 * 	,A.GAKUI				--�w��
	 * 	,A.BUNTAN				--�������S
	 * 	,A.KADAI_NAME_KANJI		--�����ۑ薼(�a���j
	 * 	,A.KADAI_NAME_EIGO			--�����ۑ薼(�p���j
	 * 	,A.JIGYO_KUBUN			--���Ƌ敪
	 * 	,A.SHINSA_KUBUN			--�R���敪
	 * 	,A.SHINSA_KUBUN_MEISHO		--�R���敪����
	 * 	,A.BUNKATSU_NO			--�����ԍ�
	 * 	,A.BUNKATSU_NO_MEISHO		--�����ԍ�����
	 * 	,A.KENKYU_TAISHO			--�����Ώۂ̗ތ^
	 * 	,A.KEI_NAME_NO			--�n���̋敪�ԍ�
	 * 	,A.KEI_NAME			--�n���̋敪
	 * 	,A.KEI_NAME_RYAKU			--�n���̋敪����
	 * 	,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 * 	,A.BUNYA_NAME			--����
	 * 	,A.BUNKA_NAME			--����
	 * 	,A.SAIMOKU_NAME			--�ז�
	 * 	,A.BUNKASAIMOKU_CD2		--�זڔԍ�2
	 * 	,A.BUNYA_NAME2			--����2
	 * 	,A.BUNKA_NAME2			--����2
	 * 	,A.SAIMOKU_NAME2			--�ז�2
	 * 	,A.KANTEN_NO			--���E�̊ϓ_�ԍ�
	 * 	,A.KANTEN				--���E�̊ϓ_
	 * 	,A.KANTEN_RYAKU			--���E�̊ϓ_����
	 * 	,A.KEIHI1				--1�N�ڌ����o��
	 * 	,A.BIHINHI1			--1�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI1			--1�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI1			--1�N�ڍ�������
	 * 	,A.GAIKOKURYOHI1			--1�N�ڊO������
	 * 	,A.RYOHI1				--1�N�ڗ���
	 * 	,A.SHAKIN1			--1�N�ڎӋ���
	 * 	,A.SONOTA1			--1�N�ڂ��̑�
	 * 	,A.KEIHI2				--2�N�ڌ����o��
	 * 	,A.BIHINHI2			--2�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI2			--2�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI2			--2�N�ڍ�������
	 * 	,A.GAIKOKURYOHI2			--2�N�ڊO������
	 * 	,A.RYOHI2				--2�N�ڗ���
	 * 	,A.SHAKIN2			--2�N�ڎӋ���
	 * 	,A.SONOTA2			--2�N�ڂ��̑�
	 * 	,A.KEIHI3				--3�N�ڌ����o��
	 * 	,A.BIHINHI3			--3�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI3			--3�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI3			--3�N�ڍ�������
	 * 	,A.GAIKOKURYOHI3			--3�N�ڊO������
	 * 	,A.RYOHI3				--3�N�ڗ���
	 * 	,A.SHAKIN3			--3�N�ڎӋ���
	 * 	,A.SONOTA3			--3�N�ڂ��̑�
	 * 	,A.KEIHI4				--4�N�ڌ����o��
	 * 	,A.BIHINHI4			--4�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI4			--4�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI4			--4�N�ڍ�������
	 * 	,A.GAIKOKURYOHI4			--4�N�ڊO������
	 * 	,A.RYOHI4				--4�N�ڗ���
	 * 	,A.SHAKIN4			--4�N�ڎӋ���
	 * 	,A.SONOTA4			--4�N�ڂ��̑�
	 * 	,A.KEIHI5				--5�N�ڌ����o��
	 * 	,A.BIHINHI5			--5�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI5			--5�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI5			--5�N�ڍ�������
	 * 	,A.GAIKOKURYOHI5			--5�N�ڊO������
	 * 	,A.RYOHI5				--5�N�ڗ���
	 * 	,A.SHAKIN5			--5�N�ڎӋ���
	 * 	,A.SONOTA5			--5�N�ڂ��̑�
	 * 	,A.KEIHI_TOTAL			--���v-�����o��
	 * 	,A.BIHINHI_TOTAL			--���v-�ݔ����i��
	 * 	,A.SHOMOHINHI_TOTAL		--���v-���Օi��
	 * 	,A.KOKUNAIRYOHI_TOTAL		--���v-��������
	 * 	,A.GAIKOKURYOHI_TOTAL		--���v-�O������
	 * 	,A.RYOHI_TOTAL			--���v-����
	 * 	,A.SHAKIN_TOTAL			--���v-�Ӌ���
	 * 	,A.SONOTA_TOTAL			--���v-���̑�
	 * 	,A.SOSHIKI_KEITAI_NO		--�����g�D�̌`�Ԕԍ�
	 * 	,A.SOSHIKI_KEITAI			--�����g�D�̌`��
	 * 	,A.BUNTANKIN_FLG			--���S���̗L��
	 * 	,A.KOYOHI				--�����x���Ҍٗp�o��
	 * 	,A.KENKYU_NINZU			--�����Ґ�
	 * 	,A.TAKIKAN_NINZU			--���@�ւ̕��S�Ґ�
	 * 	,A.SHINSEI_KUBUN			--�V�K�p���敪
	 * 	,A.KADAI_NO_KEIZOKU		--�p�����̌����ۑ�ԍ�
	 * 	,A.SHINSEI_FLG_NO			--�\���̗L���ԍ�
	 * 	,A.SHINSEI_FLG			--�\���̗L��
	 * 	,A.KADAI_NO_SAISYU			--�ŏI�N�x�ۑ�ԍ�
	 * 	,A.KAIJIKIBO_FLG_NO		--�J����]�̗L���ԍ�
	 * 	,A.KAIJIKIBO_FLG			--�J����]�̗L��
	 * 	,A.KAIGAIBUNYA_CD			--�C�O����R�[�h
	 * 	,A.KAIGAIBUNYA_NAME		--�C�O���얼��
	 * 	,A.KAIGAIBUNYA_NAME_RYAKU		--�C�O���엪��
	 * 	,A.KANREN_SHIMEI1			--�֘A����̌�����-����1
	 * 	,A.KANREN_KIKAN1			--�֘A����̌�����-�����@��1
	 * 	,A.KANREN_BUKYOKU1			--�֘A����̌�����-��������1
	 * 	,A.KANREN_SHOKU1			--�֘A����̌�����-�E��1
	 * 	,A.KANREN_SENMON1			--�֘A����̌�����-��啪��1
	 * 	,A.KANREN_TEL1			--�֘A����̌�����-�Ζ���d�b�ԍ�1
	 * 	,A.KANREN_JITAKUTEL1		--�֘A����̌�����-����d�b�ԍ�1
	 * 	,A.KANREN_MAIL1			--�֘A����̌�����-E-mail1
	 * 	,A.KANREN_SHIMEI2			--�֘A����̌�����-����2
	 * 	,A.KANREN_KIKAN2			--�֘A����̌�����-�����@��2
	 * 	,A.KANREN_BUKYOKU2			--�֘A����̌�����-��������2
	 * 	,A.KANREN_SHOKU2			--�֘A����̌�����-�E��2
	 * 	,A.KANREN_SENMON2			--�֘A����̌�����-��啪��2
	 * 	,A.KANREN_TEL2			--�֘A����̌�����-�Ζ���d�b�ԍ�2
	 * 	,A.KANREN_JITAKUTEL2		--�֘A����̌�����-����d�b�ԍ�2
	 * 	,A.KANREN_MAIL2			--�֘A����̌�����-E-mail2
	 * 	,A.KANREN_SHIMEI3			--�֘A����̌�����-����3
	 * 	,A.KANREN_KIKAN3			--�֘A����̌�����-�����@��3
	 * 	,A.KANREN_BUKYOKU3			--�֘A����̌�����-��������3
	 * 	,A.KANREN_SHOKU3			--�֘A����̌�����-�E��3
	 * 	,A.KANREN_SENMON3			--�֘A����̌�����-��啪��3
	 * 	,A.KANREN_TEL3			--�֘A����̌�����-�Ζ���d�b�ԍ�3
	 * 	,A.KANREN_JITAKUTEL3		--�֘A����̌�����-����d�b�ԍ�3
	 * 	,A.KANREN_MAIL3			--�֘A����̌�����-E-mail3
	 * 	,A.XML_PATH			--XML�̊i�[�p�X
	 * 	,A.PDF_PATH			--PDF�̊i�[�p�X
	 * 	,A.JURI_KEKKA			--�󗝌���
	 * 	,A.JURI_BIKO			--�󗝌��ʔ��l
	 * 	,A.SUISENSHO_PATH			--���E���̊i�[�p�X
	 * 	,A.KEKKA1_ABC			--�P���R������(ABC)
	 * 	,A.KEKKA1_TEN			--�P���R������(�_��)
	 * 	,A.KEKKA1_TEN_SORTED		--�P���R������(�_����)
	 * 	,A.SHINSA1_BIKO			--�P���R�����l
	 * 	,A.KEKKA2				--�Q���R������
	 * 	,A.SOU_KEHI			--���o��i�w�U���́j
	 * 	,A.SHONEN_KEHI			--���N�x�o��i�w�U���́j
	 * 	,A.SHINSA2_BIKO			--�Ɩ��S���ҋL����
	 * 	,A.JOKYO_ID			--�\����ID
	 * 	,A.SAISHINSEI_FLG			--�Đ\���t���O
	 * 	,A.DEL_FLG			--�폜�t���O
	 * FROM
	 * 	SHINSEIDATAKANRI A
	 * WHERE
	 * 	SYSTEM_NO = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>������shinseiDataPk�̕ϐ�SystemNo���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l���AshinseiDataInfo�Ɋi�[����B<br /><br />
	 * 
	 * 2.�ȉ���SQL���𔭍s���āA�Y������R�����ʃf�[�^���擾����B<br />
	 * (�o�C���h�����́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SYSTEM_NO		--�V�X�e����t�ԍ�
	 * 	,A.UKETUKE_NO		--�\���ԍ�
	 * 	,A.SHINSAIN_NO		--�R�����ԍ�
	 * 	,A.JIGYO_KUBUN		--���Ƌ敪
	 * 	,A.SEQ_NO			--�V�[�P���X�ԍ�
	 * 	,A.SHINSA_KUBUN		--�R���敪
	 * 	,A.SHINSAIN_NAME_KANJI_SEI	--�R�������i�����|���j
	 * 	,A.SHINSAIN_NAME_KANJI_MEI	--�R�������i�����|���j
	 * 	,A.NAME_KANA_SEI		--�R�������i�t���K�i�|���j
	 * 	,A.NAME_KANA_MEI		--�R�������i�t���K�i�|���j
	 * 	,A.SHOZOKU_NAME		--�R���������@�֖�
	 * 	,A.BUKYOKU_NAME		--�R�������ǖ�
	 * 	,A.SHOKUSHU_NAME		--�R�����E��
	 * 	,A.JIGYO_ID			--����ID
	 * 	,A.JIGYO_NAME		--���Ɩ�
	 * 	,A.BUNKASAIMOKU_CD		--�זڔԍ�
	 * 	,A.EDA_NO			--�}��
	 * 	,A.CHECKDIGIT		--�`�F�b�N�f�W�b�g
	 * 	,A.KEKKA_ABC		--�����]���iABC�j
	 * 	,A.KEKKA_TEN		--�����]���i�_���j
	 * 	,NVL(
	 * 		REPLACE(
	 * 			A.KEKKA_TEN,
	 * 			'-',
	 * 			'0'
	 * 		),'-1'
	 * 	) SORT_KEKKA_TEN
	 * 		--�\�[�g�p�B�R�����ʁi�_���j�̒lNULL��'-1'�A'-'��'0'�ɒu���j
	 * 	,A.COMMENT1		--�R�����g1
	 * 	,A.COMMENT2		--�R�����g2
	 * 	,A.COMMENT3		--�R�����g3
	 * 	,A.COMMENT4		--�R�����g4
	 * 	,A.COMMENT5		--�R�����g5
	 * 	,A.COMMENT6		--�R�����g6
	 * 	,A.KENKYUNAIYO		--�������e
	 * 	,A.KENKYUKEIKAKU		--�����v��
	 * 	,A.TEKISETSU_KAIGAI	--�K�ؐ�-�C�O
	 * 	,A.TEKISETSU_KENKYU1	--�K�ؐ�-�����i1�j
	 * 	,A.TEKISETSU		--�K�ؐ�
	 * 	,A.DATO			--�Ó���
	 * 	,A.SHINSEISHA		--������\��
	 * 	,A.KENKYUBUNTANSHA		--�������S��
	 * 	,A.HITOGENOMU		--�q�g�Q�m��
	 * 	,A.TOKUTEI		--������
	 * 	,A.HITOES			--�q�gES�זE
	 * 	,A.KUMIKAE		--��`�q�g��������
	 * 	,A.CHIRYO			--��`�q���×Տ�����
	 * 	,A.EKIGAKU		--�u�w����
	 * 	,A.COMMENTS		--�R�����g
	 * 	,A.TENPU_PATH		--�Y�t�t�@�C���i�[�p�X
	 * 	,A.SHINSA_JOKYO		--�R����
	 * 	,A.BIKO			--���l
	 * FROM
	 * 	SHINSAKEKKA A
	 * WHERE
	 * 	SYSTEM_NO = ?
	 * ORDER BY
	 * 	KEKKA_ABC ASC		--�����]���iABC�j�̏���
	 * 	,SORT_KEKKA_TEN DESC	--�����]���i�_���j�̍~��
	 * 	,SHINSAIN_NO ASC		--�R�����ԍ��̏���
	 * 	,JIGYO_KUBUN ASC		--���Ƌ敪�̏���</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>������shinseiDataPk�̕ϐ�SystemNo���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l���AshinsaKekkaInfo�̔z��Ɋi�[����B<br /><br />
	 * 
	 * 
	 * 3.�ȉ���SQL���𔭍s���āA�v���_�E���p�̃��x����������List���擾����B�擾����̂́A<br />
	 * �E�����]���iABC�j�@(KEKKA_ABC)<br />
	 * �E�����]���i�_���j�@(KEKKA_TEN)<br />
	 * ��2��ނȂ̂ŁASQL����2�񔭍s����B()�̒l�́A���ꂼ��̃��x���敪�̒l�B<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.ATAI
	 * 	, A.NAME
	 * 	, A.RYAKU
	 * 	, A.SORT
	 * 	, A.BIKO
	 * FROM
	 * 	MASTER_LABEL A
	 * WHERE
	 * 	A.LABEL_KUBUN = ?
	 * 	AND A.SORT != 0
	 * ORDER BY
	 * 	SORT</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>���x���敪</td><td>�O�q�������x���敪�̒l���g�p����B</td></tr>
	 * </table><br /><br />
	 * 
	 * 
	 * 4.2�Ŏ擾����shinsaKekkaInfo�̔z��̊e�X�ɁA�ȉ��̒l�������Ă����B
	 * ���̏����́A�z��̐������J��Ԃ��B<br /><br />
	 * 
	 * (1)shinsaKekkaInfo�̓Y�t�t�@�C���p�X�̒l����Y�t�t�@�C���������o���āAshinsaKekkaInfo��TenpuName�ɉ�����B<br /><br />
	 * 
	 * (2)3�Ŏ擾�����\�����x������List����A�����]���iABC�j�̕\�����x������
	 * ���N���X�̃��\�b�hgetLabelName()���g�p���Ď��o���AshinsaKekkaInfo��KekkaAbcLabel�ɉ�����B<br /><br />
	 * 
	 * (3)3�Ŏ擾�����\�����x������List����A�����]���i�_���j�̕\�����x������
	 * ���N���X�̃��\�b�hgetLabelName()���g�p���Ď��o���AshinsaKekkaInfo��KekkaTenLabel�ɉ�����B<br /><br /><br />
	 * 
	 * 
	 * 
	 * 5.ShinsaKekkaReferenceInfo�̃I�u�W�F�N�g�𐶐����A�ȉ��̒l���i�[���ĕԋp����B<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>�l(�a��)</td><td>�l</td><td>�l�����I�u�W�F�N�g</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>SystemNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���ԍ�</td><td>UketukeNo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�N�x</td><td>Nendo</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>��</td><td>Kaisu</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>JigyoId</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ɩ�</td><td>JigyoName</td><td>shinseiDataInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����ۑ薼</td><td>KadaiNameKanji</td><td>shinseiDataInfo����KadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Җ��i���j</td><td>NameKanjiSei</td><td>shinseiDataInfo����KadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�\���Җ��i���j</td><td>NameKanjiMei</td><td>shinseiDataInfo����KadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�</td><td>ShozokuName</td><td>shinseiDataInfo����KadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�</td><td>BukyokuName</td><td>shinseiDataInfo����KadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E��</td><td>ShokushuNameKanji</td><td>shinseiDataInfo����KadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>KenkyuNo</td><td>shinseiDataInfo����KadaiInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>1���R�����ʏ��</td><td>ShinsaKekkaInfo</td><td>ShinsaKekkaInfo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S���җp���l</td><td>Shinsa1Biko</td><td>shinseiDataInfo</td></tr>
	 * </table><br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return ShinsaKekkaReferenceInfo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
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
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
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
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);
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
			

			//���x����
			List kekkaAbcList = null;		//�����]���iABC�j
			List kekkaTenList = null;		//�����]���i�_���j
			List kekkaTenHogaList = null;	//�����]���i�G��j
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
				
				//�����]���iABC�j�̕\�����x�������Z�b�g
				String kekkaAbcLabel = getLabelName(kekkaAbcList, shinsaKekkaInfo[i].getKekkaAbc());
				shinsaKekkaInfo[i].setKekkaAbcLabel(kekkaAbcLabel);

				//�����]���i�_���j�̕\�����x�������Z�b�g
				String kekkaTenLabel = getLabelName(kekkaTenList, shinsaKekkaInfo[i].getKekkaTen());
				shinsaKekkaInfo[i].setKekkaTenLabel(kekkaTenLabel);

				//�����]���i�G��j�̕\�����x�������Z�b�g
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
			refInfo.setJigyoCd(shinseiDataInfo.getJigyoCd());										//����ID
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
	 * �񎟐R������(�Q�Ɨp)�̎擾.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�Y������\���f�[�^���擾����B<br />
	 * (�o�C���h�����́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT 
	 * 	A.SYSTEM_NO			--�V�X�e����t�ԍ�
	 * 	,A.UKETUKE_NO			--�\���ԍ�
	 * 	,A.JIGYO_ID			--����ID
	 * 	,A.NENDO				--�N�x
	 * 	,A.KAISU				--��
	 * 	,A.JIGYO_NAME			--���Ɩ�
	 * 	,A.SHINSEISHA_ID			--�\����ID
	 * 	,A.SAKUSEI_DATE			--�\�����쐬��
	 * 	,A.SHONIN_DATE			--�����@�֏��F��
	 * 	,A.JYURI_DATE			--�w�U�󗝓�
	 * 	,A.NAME_KANJI_SEI			--�\���Ҏ����i������-���j
	 * 	,A.NAME_KANJI_MEI			--�\���Ҏ����i������-���j
	 * 	,A.NAME_KANA_SEI			--�\���Ҏ����i�t���K�i-���j
	 * 	,A.NAME_KANA_MEI			--�\���Ҏ����i�t���K�i-���j
	 * 	,A.NAME_RO_SEI			--�\���Ҏ����i���[�}��-���j
	 * 	,A.NAME_RO_MEI			--�\���Ҏ����i���[�}��-���j
	 * 	,A.NENREI				--�N��
	 * 	,A.KENKYU_NO			--�\���Ҍ����Ҕԍ�
	 * 	,A.SHOZOKU_CD			--�����@�փR�[�h
	 * 	,A.SHOZOKU_NAME			--�����@�֖�
	 * 	,A.SHOZOKU_NAME_RYAKU		--�����@�֖��i���́j
	 * 	,A.BUKYOKU_CD			--���ǃR�[�h
	 * 	,A.BUKYOKU_NAME			--���ǖ�
	 * 	,A.BUKYOKU_NAME_RYAKU		--���ǖ��i���́j
	 * 	,A.SHOKUSHU_CD			--�E���R�[�h
	 * 	,A.SHOKUSHU_NAME_KANJI		--�E���i�a���j
	 * 	,A.SHOKUSHU_NAME_RYAKU		--�E���i���́j
	 * 	,A.ZIP				--�X�֔ԍ�
	 * 	,A.ADDRESS			--�Z��
	 * 	,A.TEL				--TEL
	 * 	,A.FAX				--FAX
	 * 	,A.EMAIL				--E-Mail
	 * 	,A.SENMON				--���݂̐��
	 * 	,A.GAKUI				--�w��
	 * 	,A.BUNTAN				--�������S
	 * 	,A.KADAI_NAME_KANJI		--�����ۑ薼(�a���j
	 * 	,A.KADAI_NAME_EIGO			--�����ۑ薼(�p���j
	 * 	,A.JIGYO_KUBUN			--���Ƌ敪
	 * 	,A.SHINSA_KUBUN			--�R���敪
	 * 	,A.SHINSA_KUBUN_MEISHO		--�R���敪����
	 * 	,A.BUNKATSU_NO			--�����ԍ�
	 * 	,A.BUNKATSU_NO_MEISHO		--�����ԍ�����
	 * 	,A.KENKYU_TAISHO			--�����Ώۂ̗ތ^
	 * 	,A.KEI_NAME_NO			--�n���̋敪�ԍ�
	 * 	,A.KEI_NAME			--�n���̋敪
	 * 	,A.KEI_NAME_RYAKU			--�n���̋敪����
	 * 	,A.BUNKASAIMOKU_CD			--�זڔԍ�
	 * 	,A.BUNYA_NAME			--����
	 * 	,A.BUNKA_NAME			--����
	 * 	,A.SAIMOKU_NAME			--�ז�
	 * 	,A.BUNKASAIMOKU_CD2		--�זڔԍ�2
	 * 	,A.BUNYA_NAME2			--����2
	 * 	,A.BUNKA_NAME2			--����2
	 * 	,A.SAIMOKU_NAME2			--�ז�2
	 * 	,A.KANTEN_NO			--���E�̊ϓ_�ԍ�
	 * 	,A.KANTEN				--���E�̊ϓ_
	 * 	,A.KANTEN_RYAKU			--���E�̊ϓ_����
	 * 	,A.KEIHI1				--1�N�ڌ����o��
	 * 	,A.BIHINHI1			--1�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI1			--1�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI1			--1�N�ڍ�������
	 * 	,A.GAIKOKURYOHI1			--1�N�ڊO������
	 * 	,A.RYOHI1				--1�N�ڗ���
	 * 	,A.SHAKIN1			--1�N�ڎӋ���
	 * 	,A.SONOTA1			--1�N�ڂ��̑�
	 * 	,A.KEIHI2				--2�N�ڌ����o��
	 * 	,A.BIHINHI2			--2�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI2			--2�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI2			--2�N�ڍ�������
	 * 	,A.GAIKOKURYOHI2			--2�N�ڊO������
	 * 	,A.RYOHI2				--2�N�ڗ���
	 * 	,A.SHAKIN2			--2�N�ڎӋ���
	 * 	,A.SONOTA2			--2�N�ڂ��̑�
	 * 	,A.KEIHI3				--3�N�ڌ����o��
	 * 	,A.BIHINHI3			--3�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI3			--3�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI3			--3�N�ڍ�������
	 * 	,A.GAIKOKURYOHI3			--3�N�ڊO������
	 * 	,A.RYOHI3				--3�N�ڗ���
	 * 	,A.SHAKIN3			--3�N�ڎӋ���
	 * 	,A.SONOTA3			--3�N�ڂ��̑�
	 * 	,A.KEIHI4				--4�N�ڌ����o��
	 * 	,A.BIHINHI4			--4�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI4			--4�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI4			--4�N�ڍ�������
	 * 	,A.GAIKOKURYOHI4			--4�N�ڊO������
	 * 	,A.RYOHI4				--4�N�ڗ���
	 * 	,A.SHAKIN4			--4�N�ڎӋ���
	 * 	,A.SONOTA4			--4�N�ڂ��̑�
	 * 	,A.KEIHI5				--5�N�ڌ����o��
	 * 	,A.BIHINHI5			--5�N�ڐݔ����i��
	 * 	,A.SHOMOHINHI5			--5�N�ڏ��Օi��
	 * 	,A.KOKUNAIRYOHI5			--5�N�ڍ�������
	 * 	,A.GAIKOKURYOHI5			--5�N�ڊO������
	 * 	,A.RYOHI5				--5�N�ڗ���
	 * 	,A.SHAKIN5			--5�N�ڎӋ���
	 * 	,A.SONOTA5			--5�N�ڂ��̑�
	 * 	,A.KEIHI_TOTAL			--���v-�����o��
	 * 	,A.BIHINHI_TOTAL			--���v-�ݔ����i��
	 * 	,A.SHOMOHINHI_TOTAL		--���v-���Օi��
	 * 	,A.KOKUNAIRYOHI_TOTAL		--���v-��������
	 * 	,A.GAIKOKURYOHI_TOTAL		--���v-�O������
	 * 	,A.RYOHI_TOTAL			--���v-����
	 * 	,A.SHAKIN_TOTAL			--���v-�Ӌ���
	 * 	,A.SONOTA_TOTAL			--���v-���̑�
	 * 	,A.SOSHIKI_KEITAI_NO		--�����g�D�̌`�Ԕԍ�
	 * 	,A.SOSHIKI_KEITAI			--�����g�D�̌`��
	 * 	,A.BUNTANKIN_FLG			--���S���̗L��
	 * 	,A.KOYOHI				--�����x���Ҍٗp�o��
	 * 	,A.KENKYU_NINZU			--�����Ґ�
	 * 	,A.TAKIKAN_NINZU			--���@�ւ̕��S�Ґ�
	 * 	,A.SHINSEI_KUBUN			--�V�K�p���敪
	 * 	,A.KADAI_NO_KEIZOKU		--�p�����̌����ۑ�ԍ�
	 * 	,A.SHINSEI_FLG_NO			--�\���̗L���ԍ�
	 * 	,A.SHINSEI_FLG			--�\���̗L��
	 * 	,A.KADAI_NO_SAISYU			--�ŏI�N�x�ۑ�ԍ�
	 * 	,A.KAIJIKIBO_FLG_NO		--�J����]�̗L���ԍ�
	 * 	,A.KAIJIKIBO_FLG			--�J����]�̗L��
	 * 	,A.KAIGAIBUNYA_CD			--�C�O����R�[�h
	 * 	,A.KAIGAIBUNYA_NAME		--�C�O���얼��
	 * 	,A.KAIGAIBUNYA_NAME_RYAKU		--�C�O���엪��
	 * 	,A.KANREN_SHIMEI1			--�֘A����̌�����-����1
	 * 	,A.KANREN_KIKAN1			--�֘A����̌�����-�����@��1
	 * 	,A.KANREN_BUKYOKU1			--�֘A����̌�����-��������1
	 * 	,A.KANREN_SHOKU1			--�֘A����̌�����-�E��1
	 * 	,A.KANREN_SENMON1			--�֘A����̌�����-��啪��1
	 * 	,A.KANREN_TEL1			--�֘A����̌�����-�Ζ���d�b�ԍ�1
	 * 	,A.KANREN_JITAKUTEL1		--�֘A����̌�����-����d�b�ԍ�1
	 * 	,A.KANREN_MAIL1			--�֘A����̌�����-E-mail1
	 * 	,A.KANREN_SHIMEI2			--�֘A����̌�����-����2
	 * 	,A.KANREN_KIKAN2			--�֘A����̌�����-�����@��2
	 * 	,A.KANREN_BUKYOKU2			--�֘A����̌�����-��������2
	 * 	,A.KANREN_SHOKU2			--�֘A����̌�����-�E��2
	 * 	,A.KANREN_SENMON2			--�֘A����̌�����-��啪��2
	 * 	,A.KANREN_TEL2			--�֘A����̌�����-�Ζ���d�b�ԍ�2
	 * 	,A.KANREN_JITAKUTEL2		--�֘A����̌�����-����d�b�ԍ�2
	 * 	,A.KANREN_MAIL2			--�֘A����̌�����-E-mail2
	 * 	,A.KANREN_SHIMEI3			--�֘A����̌�����-����3
	 * 	,A.KANREN_KIKAN3			--�֘A����̌�����-�����@��3
	 * 	,A.KANREN_BUKYOKU3			--�֘A����̌�����-��������3
	 * 	,A.KANREN_SHOKU3			--�֘A����̌�����-�E��3
	 * 	,A.KANREN_SENMON3			--�֘A����̌�����-��啪��3
	 * 	,A.KANREN_TEL3			--�֘A����̌�����-�Ζ���d�b�ԍ�3
	 * 	,A.KANREN_JITAKUTEL3		--�֘A����̌�����-����d�b�ԍ�3
	 * 	,A.KANREN_MAIL3			--�֘A����̌�����-E-mail3
	 * 	,A.XML_PATH			--XML�̊i�[�p�X
	 * 	,A.PDF_PATH			--PDF�̊i�[�p�X
	 * 	,A.JURI_KEKKA			--�󗝌���
	 * 	,A.JURI_BIKO			--�󗝌��ʔ��l
	 * 	,A.SUISENSHO_PATH			--���E���̊i�[�p�X
	 * 	,A.KEKKA1_ABC			--�P���R������(ABC)
	 * 	,A.KEKKA1_TEN			--�P���R������(�_��)
	 * 	,A.KEKKA1_TEN_SORTED		--�P���R������(�_����)
	 * 	,A.SHINSA1_BIKO			--�P���R�����l
	 * 	,A.KEKKA2				--�Q���R������
	 * 	,A.SOU_KEHI			--���o��i�w�U���́j
	 * 	,A.SHONEN_KEHI			--���N�x�o��i�w�U���́j
	 * 	,A.SHINSA2_BIKO			--�Ɩ��S���ҋL����
	 * 	,A.JOKYO_ID			--�\����ID
	 * 	,A.SAISHINSEI_FLG			--�Đ\���t���O
	 * 	,A.DEL_FLG			--�폜�t���O
	 * FROM
	 * 	SHINSEIDATAKANRI A
	 * WHERE
	 * 	SYSTEM_NO = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>������shinseiDataPk�̕ϐ�SystemNo���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l��shinseiDataInfo�Ɋi�[���A��������ShinsaKekka2ndInfo�֒l��^����B<br />
	 * �^����l�́A�ȉ��̕\���Q�ƁB<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>�^����l�̖��O(�a��)</td><td>�l�̖��O</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e����t�ԍ�</td><td>SystemNo</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>2���R������</td><td>Kekka2</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���o��</td><td>SouKehi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���N�x�o��</td><td>ShonenKehi</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�Ɩ��S���ҋL����</td><td>Shinsa2Biko</td></tr>
	 * </table><br />
	 * 
	 * �l���󂯎����ShinsaKekka2ndInfo��ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param shinseiDataPk ShinseiDataPk
	 * @return ShinsaKekka2ndInfo
	 * @throws NoDataFoundException
	 * @throws ApplicationException
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
			ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
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
	 * �Ñ��̃��[�����M.<br /><br />
	 * 
	 * 1.�R�������E���Ə��̎擾<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA<br />
	 * �E���R���̐\���������R�������i�R����No�A���[���A�h���X�j<br />
	 * �E3���オ�R�������ł��鎖�Ə��i����ID�A�N�x�A���Ɩ��j<br />
	 * ������List���擾����B<br />
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT DISTINCT
	 * 	S.SHINSAIN_NO,
	 * 	J.JIGYO_ID,
	 * 	J.NENDO,
	 * 	J.JIGYO_NAME,
	 * 	M.SOFU_ZIPEMAIL
	 * FROM(
	 * 		SELECT
	 * 			SHINSAIN_NO,
	 * 			JIGYO_ID
	 * 		FROM
	 * 			SHINSAKEKKA
	 * 		WHERE
	 * 			SHINSA_JOKYO = '0' OR SHINSA_JOKYO IS NULL
	 * 	) S,	//�R���������̂���
	 * 	(
	 * 		SELECT
	 * 			JIGYO_ID,
	 * 			NENDO,
	 * 			JIGYO_NAME
	 * 		FROM
	 * 			JIGYOKANRI
	 * 		WHERE
	 * 			_CHAR(SHINSAKIGEN,'YYYY/MM/DD') = ?
	 * 	) J,
	 * 	MASTER_SHINSAIN M
	 * WHERE
	 * 	S.JIGYO_ID=J.JIGYO_ID
	 * 	AND S.SHINSAIN_NO=M.SHINSAIN_NO
	 * ORDER BY
	 * 	S.SHINSAIN_NO,
	 * 	J.JIGYO_ID</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R������</td><td>�{������3����̓��t��"yyyy/MM/dd"�Ŏg�p����B</td></tr>
	 * </table><br />
	 * 
	 * �Y������R���������݂��Ȃ������ꍇ�́A�����ŏ������I���ƂȂ�B<br /><br /><br />
	 * 
	 * 2.�f�[�^�\���̕ϊ�<br /><br />
	 * 
	 * �Y������R���������݂���ꍇ�́A
	 * �擾����List(�d���f�[�^���܂ޒP�ꃊ�X�g)
	 * ��R�����ԍ����Ƃ̃}�b�v�փf�[�^�\����ϊ�����B<br /><br />
	 * 
	 * (1)�擾����List����Map����A<br /><br />
	 * 
	 * �@�E�R�����ԍ�<br />
	 * �@�E���[���A�h���X<br />
	 * �@�E�N�x<br />
	 * �@�E���Ɩ�<br /><br />
	 * 
	 * �@��String�^�Ŏ��o���A���Ɩ���<br /><br />
	 * 
	 * �@�@"<b>����{1}�N�x{2}</b>"<br />
	 * �@�@{1}�c�N�x�̒l<br />
	 * �@�@{2}�c���Ɩ�<br /><br />
	 * 
	 * �@�̌`�ɂ���B<br /><br />
	 * 
	 * (2)���[���A�h���X�ƕϊ��������Ɩ���List"dataList"�ɃZ�b�g���A<br />
	 * �@����List���A�R�����ԍ����L�[�ɂ��āAMap"saisokuMap"�Ɋi�[����B<br /><br />
	 * 
	 * (3)(1)�A(2)�̏������AList����Map�̐������J��Ԃ����A<br />
	 * �@���̉ߒ��œ����R�����̏�񂪂������ꍇ�ɂ́A<br />
	 * �@����dataList�ɁA�ϊ��������Ɩ��������Z�b�g����B<br /><br /><br />
	 * 
	 * 
	 * 3.���[���̑��M<br /><br />
	 * �����ł́AdataList�̐������A�������J��Ԃ��B<br /><br />
	 * 
	 * (1)���[���̖{���̓ǂݍ���<br /><br />
	 * �@
	 * �@�{���̂���t�@�C����ǂݍ��ށB�ǂݍ��ނ̂́A<br />
	 * �@�@"<b>D:/shinsei-kaken/settings/mail/shinseisho_shinsa_saisoku.txt</b>"<br />
	 *�@ �̃t�@�C���B<br />
	 * �@���̌�A�t�@�C���̖{����String�^�ɂ��Ď󂯎��B<br />
	 * �@�ǂݍ��݂ł��Ȃ������ꍇ�ɂ͗�O��throw����B<br /><br />
	 * 
	 * 
	 * (2)���I���ڂ̍쐬<br /><br />
	 * 
	 * �@2�ō쐬����saisokuMap����dataList�����o���A���[���{���̓��I���ڂ��쐬����B<br />
	 * �@�Ȃ��AdataList���̃��[���A�h���X���ݒ肳��Ă��Ȃ��ꍇ�ɂ͏������΂��A����dataList�̏������s���B<br /><br />
	 * 
	 * �@(2-1)�ϊ����ꂽ���Ɩ�����A<br /><br />
	 * 
	 * �@�@�@�@"<b>�@�y������ږ��z</b>{1}�@<b>\n</b>"<br />
	 * �@�@�@�@�@�@{1}�c�ϊ����ꂽ���Ɩ�<br /><br />
	 * 
	 * �@�@�Ƃ���String���쐬����BdataList�̎��Ƃ̐������쐬���A�A�����Ă����B<br /><br />
	 * 
	 * �@(2-2)�R�����������A"M��d��"�̌`��String�ɂ���B<br /><br />
	 * 
	 * �@�@���̓��String��z��Ŏ����A�擾�����{���ƂƂ���<br /><br />
	 * 
	 * �@�@�@�@<b>MessageFormat.format(</b>�{��<b>,</b>String�̔z��<b>)</b><br /><br />
	 * 
	 * �@�@�ɗ^���A�{��������������B<br /><br /><br />
	 * 
	 * 
	 * (3)���M<br /><br />
	 * 
	 * �@���[���A�h���X�A�쐬�����{���ɉ����A�����A���o�l��^���āA���[���𑗐M����B<br /><br />
	 * 
	 * �@�E�����@�cSUBJECT_SHINSEISHO_SHINSA_SAISOKU�̒l���g�p�B<br />
	 * �@�E���o�l�cApplicationSettings.properties�ɐݒ�<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @throws ApplicationException
	 */
	public void sendMailShinsaSaisoku(UserInfo userInfo)
		throws ApplicationException 
	{
		List       shinsainList = null;
		Connection connection   = null;
		
		//�R�����������̐ݒ�
		DateUtil du = new DateUtil();
		du.addDate(DATE_BY_SHINSA_KIGEN);	//�w����t�����Z����
		Date date = du.getCal().getTime();
		try {
			//DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();		
			//�R������DAO
			ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);
			try{
				shinsainList = shinsaDao.selectShinsainWithNonExamined(connection, date);
			}catch(NoDataFoundException e){
				//�����������Ȃ�
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}					
		} finally {
			DatabaseUtil.closeConnection(connection);
		}		
		connection = null;		
		
		//�Y������R���������݂��Ȃ������ꍇ
		if(shinsainList == null || shinsainList.size() == 0){
			String strDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
			String msg = "�R������["+strDate+"]�̎��ƂŁA���R���̐\���������R�����͑��݂��܂���B";
			log.info(msg);
			return;
		}
		
		
		//-----�f�[�^�\����ϊ��i�d���f�[�^���܂ޒP�ꃊ�X�g����R�����ԍ����Ƃ̃}�b�v�֕ϊ�����j
		//�S��Map
		Map saisokuMap = new HashMap();
		for(int i=0; i<shinsainList.size(); i++){
			
			//1���R�[�h
			Map recordMap = (Map)shinsainList.get(i);
			String shinsain_no   = (String)recordMap.get("SHINSAIN_NO");
			String sofu_zipemail = (String)recordMap.get("SOFU_ZIPEMAIL");			
			String nendo         = (String)recordMap.get("NENDO");
			String jigyo_name    = (String)recordMap.get("JIGYO_NAME");
			
				//���Ɩ����u�N�x�{���Ɩ��v�֕ϊ�
				jigyo_name = new StringBuffer("����")
							 .append(nendo)
							 .append("�N�x ")
							 .append(jigyo_name)
							 .toString();
			
			//�S��Map�ɓ��Y�R�����f�[�^�����݂��Ă����ꍇ
			if(saisokuMap.containsKey(shinsain_no)){
				List dataList = (List)saisokuMap.get(shinsain_no);
				dataList.add(jigyo_name);				//�����R�[�h�ɔN�x+���Ɩ���ǉ�
			//���̐R�����f�[�^�̏ꍇ	
			}else{
				List dataList = new ArrayList();
				dataList.add(sofu_zipemail);			//1���R�[�h�ڂɃ��[���A�h���X
				dataList.add(jigyo_name);				//2���R�[�h�ڂɔN�x+���Ɩ�
				saisokuMap.put(shinsain_no, dataList);
			}
			
		}				
		
		
		
		//---------------
		// ���[�����M
		//---------------
		//-----���[���{���t�@�C���̓ǂݍ���
		String content = null;
		try{
			File contentFile = new File(CONTENT_SHINSEISHO_SHINSA_SAISOKU);
			FileResource fileRes = FileUtil.readFile(contentFile);
			content = new String(fileRes.getBinary());
		}catch(FileNotFoundException e){
			log.warn("���[���{���t�@�C����������܂���ł����B", e);
			return;
		}catch(IOException e){
			log.warn("���[���{���t�@�C���ǂݍ��ݎ��ɃG���[���������܂����B",e);
			return;
		}
		
		//-----���[�����M�i�P�l�����M�j
		String kigenDate = new SimpleDateFormat("M��d��").format(date);
		for(Iterator iter = saisokuMap.keySet().iterator(); iter.hasNext();){
			
			//�R�������Ƃ̃f�[�^���X�g���擾����
			String shinsainNo = (String)iter.next();
			List   dataList   = (List)saisokuMap.get(shinsainNo);
			
			//���[���A�h���X���ݒ肳��Ă��Ȃ��ꍇ�͏������΂�
			String to = (String)dataList.get(0);
			if(to == null || to.length() == 0){
				continue;
			}
			
			//-----���[���{���t�@�C���̓��I���ڕύX
			StringBuffer jigyoNameList = new StringBuffer("\n");
			for(int i=1; i<dataList.size(); i++){
				jigyoNameList.append("  �y������ږ��z")
							 .append(dataList.get(i))
							 .append("\n")
							 ;
			}
			String[] param = new String[]{
								jigyoNameList.toString(),	//���Ɩ����X�g
								kigenDate					//�R���������t
							 };
			String   body  = MessageFormat.format(content, param);			
			
			
			try{
				SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
				mailer.sendMail(FROM_ADDRESS,						//���o�l
								to,									//to
								null,								//cc
								null,								//bcc
								SUBJECT_SHINSEISHO_SHINSA_SAISOKU,	//����
								body);							    //�{��
			}catch(Exception e){
				log.warn("���[�����M�Ɏ��s���܂����B",e);
				continue;
			}
		}		
		
	}


	/**
	 * �R���󋵂̍X�V.<br /><br />
	 * 
	 * �R���󋵂��X�V���鎩�N���X�̃��\�b�h<br />
	 * updateJigyoShinsaComplete(userInfo, jigyoId, ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE)<br />
	 * ���Ăяo���A�R���󋵂��w�R�������x�ɍX�V����B<br />
	 * ���\�b�h�ɗ^�����O����(�R���󋵂�\��String)��"1"(�R��������\��)<br /><br />
	 * 
	 * ����ɍX�V���s��ꂽ��Atrue��ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param jigyoId String
	 * @return �X�V���ʂ�boolean
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaComplete(UserInfo userInfo, 
												String jigyoId) 
										throws NoDataFoundException, ApplicationException {
	
		return updateJigyoShinsaComplete(userInfo, jigyoId, ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE);

//		String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();		//�R�����ԍ�
//		String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//���Ƌ敪
//		
//		Connection   connection  = null;
//		boolean     success     = false;
//		try {
//			connection = DatabaseUtil.getConnection();
//	
//			//�R�����ʊǗ�DAO
//			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);
//					
//			//�����]����NULL�̃f�[�^�����邩�ǂ������m�F
//			int count = 0;
//			try{
//				count = dao.countShinsaKekkaInfo(
//									connection,
//									shinsainNo,
//									jigyoKubun,
//									jigyoId);
//			}catch(DataAccessException e){
//				throw new ApplicationException(
//					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
//					new ErrorInfo("errors.4004"),
//					e);
//			}		
//			
//			//�����]����NULL�̃f�[�^����������G���[��Ԃ�
//			if(count != 0){
//				return false;
//			}
//			
//			//�R���󋵂��X�V
//			try{
//				dao.updateShinsaKekkaInfo(
//								connection, 
//								shinsainNo, 
//								jigyoKubun, 
//								null,
//								jigyoId,
//								ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE);
//			}catch(NoDataFoundException e){
//				throw e;
//			}catch(DataAccessException e){
//				throw new ApplicationException(
//					"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
//					new ErrorInfo("errors.4002"),
//					e);
//			}
//			success = true;
//		} finally {
//			try {
//				if (success) {
//					DatabaseUtil.commit(connection);
//				} else {
//					DatabaseUtil.rollback(connection);
//				}
//			} catch (TransactionException e) {
//				throw new ApplicationException(
//					"�R�����ʏ��DB�X�V���ɃG���[���������܂����B",
//					new ErrorInfo("errors.4002"),
//					e);
//			} finally {							
//				DatabaseUtil.closeConnection(connection);
//			}
//		}		
//		return true;
	}



	/**
	 * �R���󋵂̍X�V.<br /><br />
	 * 
	 * �R���󋵂��X�V���鎩�N���X�̃��\�b�h<br />
	 * updateJigyoShinsaComplete(userInfo, jigyoId, ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET)<br />
	 * ���Ăяo���A�R���󋵂��w�R���������x�ɍX�V����B<br />
	 * ���\�b�h�ɗ^�����O����(�R���󋵂�\��String)��"0"(�R����������\��)<br /><br />
	 * 
	 * ����ɍX�V���s��ꂽ��Atrue��ԋp����B<br /><br />
	 * 
	 * 
	 * @param userInfo UserInfo
	 * @param jigyoId String
	 * @return boolean
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaCompleteYet(UserInfo userInfo, 
												String jigyoId) 
										throws NoDataFoundException, ApplicationException {
	
		return updateJigyoShinsaComplete(userInfo, jigyoId, ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET);
	
	}
	
	

	/**
	 * �R���󋵂̍X�V.<br /><br />
	 * 
	 * �^����ꂽshinsaJokyo�̒l�ɏ]���A�R���󋵂��X�V����B<br /><br />
	 * 
	 * 1.��O������shinsaJokyo�̒l��null�ł������ꍇ�ɂ́A
	 * shinsaJokyo��"1"(�R������)���Z�b�g����B<br /><br />
	 * 
	 * 2.�ȉ���SQL���𔭍s���āA�����]����NULL�̃f�[�^�����邩�ǂ������m�F����B
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	COUNT(*)
	 * FROM
	 * 	SHINSAKEKKA A,
	 * 		(
	 * 		SELECT
	 * 			*
	 * 		FROM
	 * 			SHINSEIDATAKANRI
	 * 		WHERE
	 * 			(JOKYO_ID = '10'
	 * 			OR JOKYO_ID = '11')
	 * 			AND DEL_FLG = '0'	--�폜����ĂȂ�����
	 * 		) B
	 * WHERE
	 * 	A.SYSTEM_NO = B.SYSTEM_NO
	 * 	AND A.KEKKA_ABC IS NULL		--�����]���iABC�j
	 * 	AND A.KEKKA_TEN IS NULL		--�����]���i�_���j
	 * 	AND A.SHINSAIN_NO = ?
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������userInfo�̕ϐ�shinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������userInfo�̕ϐ�JigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>������JigyoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �����]����NULL�̃f�[�^���������ꍇ�ɂ́Afalse��ԋp���Ċ����ƂȂ�B<br /><br />
	 * 
	 * 3.NULL�̃f�[�^���Ȃ��ꍇ�ɂ́A�ȉ���SQL���𔭍s���ĐR���󋵂��X�V����B
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAKEKKA A
	 * SET
	 * 	A.SHINSA_JOKYO = ?
	 * WHERE
	 * 	A.SYSTEM_NO IN(
	 * 		SELECT
	 * 			B.SYSTEM_NO
	 * 		FROM
	 * 			SHINSEIDATAKANRI B
	 * 			, SHINSAKEKKA C
	 * 		WHERE
	 * 			B.SYSTEM_NO=C.SYSTEM_NO
	 * 			AND B.DEL_FLG=0
	 * 			AND (B.JOKYO_ID = '10' OR B.JOKYO_ID = '11')
	 * 	)
	 * 	AND A.SHINSAIN_NO = ?"
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R����</td><td>��O����shinsaJokyo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������userInfo�̕ϐ�shinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������userInfo�̕ϐ�jigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>��������JigyoId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * 4.�X�V������ɏI��������Atrue��ԋp����B
	 * ����ɏI�����Ȃ������ꍇ�ɂ́A��O��throw����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param jigyoId String
	 * @param shinsaJokyo String
	 * @return boolean
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public boolean updateJigyoShinsaComplete(UserInfo userInfo, 
												String jigyoId,
												String shinsaJokyo) 
										throws NoDataFoundException, ApplicationException {
	

		String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();		//�R�����ԍ�
		String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//���Ƌ敪
		
		//�R���󋵂�null�Ȃ�R��������
		if(shinsaJokyo == null) {
			shinsaJokyo = ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE;
		}
		
		Connection   connection  = null;
		boolean     success     = false;
		try {
			connection = DatabaseUtil.getConnection();
	
			//�R�����ʊǗ�DAO
			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);
					
			//�����]����NULL�̃f�[�^�����邩�ǂ������m�F
			int count = 0;
			try{
				count = dao.countShinsaKekkaInfo(
									connection,
									shinsainNo,
									jigyoKubun,
									jigyoId);
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}		
			
			//�����]����NULL�̃f�[�^����������G���[��Ԃ�
			if(count != 0){
				return false;
			}
			
			//�R���󋵂��X�V
			try{
				dao.updateShinsaKekkaInfo(
								connection, 
								shinsainNo, 
								jigyoKubun, 
								null,
								jigyoId,
								shinsaJokyo);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}
			
			//���[���A�h���X�o�^��ʂŁu�o�^���Ȃ��v��I�����A���[���A�h���X���o�^����Ă���ꍇ�̂݃��[���𑗐M����
			if(!"1".equals(userInfo.getShinsainInfo().getMailFlg()) 
				&& !StringUtil.isBlank(userInfo.getShinsainInfo().getSofuZipemail())){
				
				//-----���[���{���t�@�C���̓ǂݍ���
				String content = null;
				try{
					File contentFile = new File(CONTENT_SHINSAKEKKA_JURI_TSUCHI);
					FileResource fileRes = FileUtil.readFile(contentFile);
					content = new String(fileRes.getBinary());
				}catch(FileNotFoundException e){
					log.warn("���[���{���t�@�C����������܂���ł����B", e);
					return success;
				}catch(IOException e){
					log.warn("���[���{���t�@�C���ǂݍ��ݎ��ɃG���[���������܂����B",e);
					return success;
				}
				
				//���Ə����擾
				JigyoKanriInfoDao jigyoKanriDao = new JigyoKanriInfoDao(userInfo);
				JigyoKanriPk jigyoKanriPk = new JigyoKanriPk();
				JigyoKanriInfo jigyoKanriInfo = null;
				
				jigyoKanriPk.setJigyoId(jigyoId);
				
				try {
					jigyoKanriInfo = jigyoKanriDao.selectJigyoKanriInfo(connection, jigyoKanriPk);
					
				}catch(NoDataFoundException e){
					throw e;
				}catch(DataAccessException e){
					throw new ApplicationException(
						"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
						new ErrorInfo("errors.4002"),
						e);
				}
				
				//������ږ����쐬
				String shumokuName = null;
				String nendo       = jigyoKanriInfo.getNendo();
				String jigyoName   = jigyoKanriInfo.getJigyoName();
				String kaisu       = jigyoKanriInfo.getKaisu();
				
				//�񐔂͕�����̂Ƃ��̂ݕ\������
				if(Integer.parseInt(kaisu) > 1){
					shumokuName = "����"+ nendo + "�N�x ��"+ kaisu +"�� "+ jigyoName; 
				}else{
					shumokuName = "����"+ nendo + "�N�x "+ jigyoName;
				}
				
				
				//---�����]�_���X�g���擾
				List hyotenList = null;
				try {
					hyotenList = dao.getSogoHyokaList(
												connection, 
												shinsainNo,
												jigyoKubun,
												jigyoId);
				} catch (DataAccessException e) {
					throw new ApplicationException(
						"�R�����ʃf�[�^��������DB�G���[���������܂����B",
						new ErrorInfo("errors.4004"),
						e);
				}

				//�}�b�v�ɓ���Ȃ���
				int allcount = 0;
				Map hyotenMap = new HashMap();
				for(int i=0; i<hyotenList.size(); i++){
					Map recordMap = (Map)hyotenList.get(i);	//�P���R�[�h
	
					String kekkaTen_ = (String)recordMap.get("KEKKA_TEN");
					String count_ = ((Number)recordMap.get("COUNT")).toString();
					allcount = allcount + Integer.parseInt(count_);
	
					hyotenMap.put(kekkaTen_, count_);
				}
				//�u���ׂāv�i�������j�̃L�[�́u0�v�ŃZ�b�g
				hyotenMap.put("0", Integer.toString(allcount));	
				
				
				//-----���[�����M
				SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
			
				
				//���[���{���ϊ��f�[�^
				String[] param = new String[]{
											  shumokuName,							//������ږ�
											  Integer.toString(allcount)			//�R������
											  };
				
				//���[���{���e���v���[�g��ϊ�							  
				String   body  = MessageFormat.format(content, param);			
			
				try{
					mailer.sendMail(FROM_ADDRESS,									//���o�l
									userInfo.getShinsainInfo().getSofuZipemail(),	//to
									null,											//cc
									null,											//bcc
									SUBJECT_SHINSAKEKKA_JURI_TSUCHI,				//����
									body);											//�{��
				
				}catch(Exception e){
					log.warn("���[�����M�Ɏ��s���܂����B",e);
					//return success;		2005/11/14�@tanabe ���[�����M�Ɏ��s���Ă������͑��s������B
				}
			}
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
					"�R�����ʏ��DB�X�V���ɃG���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {							
				DatabaseUtil.closeConnection(connection);
			}
		}		
		return true;
	}


	/**
	 * �R���󋵂̍X�V.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�R���󋵂𖢊����ɂ���B
	 * (�o�C���h�ϐ��́ASQL�̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * UPDATE
	 * 	SHINSAKEKKA A
	 * SET
	 * 	A.SHINSA_JOKYO = ?
	 * WHERE
	 * 	A.SYSTEM_NO IN(
	 * 		SELECT
	 * 			B.SYSTEM_NO
	 * 		FROM
	 * 			SHINSEIDATAKANRI B
	 * 			, SHINSAKEKKA C
	 * 		WHERE
	 * 			B.SYSTEM_NO=C.SYSTEM_NO
	 * 			AND B.DEL_FLG=0
	 * 			AND (B.JOKYO_ID = '10' OR B.JOKYO_ID = '11')
	 * 
	 * 	<b><span style="color:#002288">�|�|���I���������|�|</span></b>
	 * 
	 * 	)
	 * 	AND A.SHINSAIN_NO = ?"
	 * 	AND A.JIGYO_KUBUN = ?
	 * 	AND A.JIGYO_ID = ?</pre>
	 * </td></tr>
	 * </table><br />
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>systemNo</td><td>AND B.SYSTEM_NO= ?</td></tr>
	 * </table><br/>
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�R����</td><td>0(������)</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�V�X�e���ԍ�</td><td>������systemNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�R�����ԍ�</td><td>������userInfo�̕ϐ�shinsainNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���Ƌ敪</td><td>������userInfo�̕ϐ�jigyoKubun���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����ID</td><td>null</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param systemNo String
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public void updateShinseiShinsaComplete(UserInfo userInfo, String systemNo) throws NoDataFoundException, ApplicationException {

		String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();		//�R�����ԍ�
		String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//���Ƌ敪

		Connection   connection  = null;
		boolean     success     = false;
		try {
			connection = DatabaseUtil.getConnection();
	
			//�R�����ʊǗ�DAO
			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);
	
			
			//�R���󋵂��X�V
			try{
				dao.updateShinsaKekkaInfo(connection, shinsainNo, jigyoKubun, systemNo, null, ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�X�V����DB�G���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			}
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
					"�R�����ʏ��DB�X�V���ɃG���[���������܂����B",
					new ErrorInfo("errors.4002"),
					e);
			} finally {							
				DatabaseUtil.closeConnection(connection);
			}
		}		
	}

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

	//�g�p���Ȃ��B
	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.IShinsaKekkaMaintenance#selectShinsaKekkaTantoListKiban(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String, jp.go.jsps.kaken.model.vo.SearchInfo)
	 */
//	public Map selectShinsaKekkaTantoListKiban(UserInfo userInfo,
//												String jigyoId,
//												String kekkaTen,
//												SearchInfo searchInfo) 
//			throws NoDataFoundException, ApplicationException  
//	{
//
//		String shinsainNo = userInfo.getShinsainInfo().getShinsainNo();		//�R�����ԍ�
//		String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun();		//���Ƌ敪
//					
//		//DB�R�l�N�V�����̎擾
//		Connection connection = null;	
//		try{
//			connection = DatabaseUtil.getConnection();
//			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);			
//			//---�R���S���\���ꗗ�y�[�W���
//			Page pageInfo = null;
//			try {
//				pageInfo = dao.selectShinsaKekkaTantoList(
//											connection, 
//											shinsainNo,
//											jigyoKubun,
//											jigyoId,
//											kekkaTen,
//											searchInfo);
//			} catch (DataAccessException e) {
//				throw new ApplicationException(
//					"�R�����ʃf�[�^��������DB�G���[���������܂����B",
//					new ErrorInfo("errors.4004"),
//					e);
//			} catch (NoDataFoundException e){
//				//0���̃y�[�W�I�u�W�F�N�g�𐶐��i�_�����ƂɍČ��������ꍇ�A0���̏ꍇ���ꗗ�ɕ\�����邽�߁j
//				pageInfo = Page.EMPTY_PAGE;			
//			}
//
//			//���x���������x���}�X�^����擾���ăZ�b�g
//			List tekisetsuKaigaiList = null;		//�K�ؐ�-�C�O
//			List tekisetsuKenkyu1List = null;		//�K�ؐ�-�����i1�j
//			List tekisetsuList = null;				//�K�ؐ�			
//			List datoList = null;					//�Ó���
//			try{
//				String[] labelKubun = new String[]{ILabelKubun.TEKISETSU_KAIGAI,
//													ILabelKubun.TEKISETSU_KENKYU1,
//													ILabelKubun.TEKISETSU,
//													ILabelKubun.DATO};
//				List bothList = new LabelValueMaintenance().getLabelList(labelKubun);	//4�̃��x�����X�g
//				tekisetsuKaigaiList = (List)bothList.get(0);		
//				tekisetsuKenkyu1List = (List)bothList.get(1);		
//				tekisetsuList = (List)bothList.get(2);
//				datoList = (List)bothList.get(3);				
//			}catch(ApplicationException e){
//				throw new ApplicationException(
//					"���x���}�X�^��������DB�G���[���������܂����B",
//					new ErrorInfo("errors.4004"),
//					e);
//			}
//						
//			List list = pageInfo.getList();
//			for(int i = 0;i< list.size();i ++){
//				Map lineMap = (Map)list.get(i);
//				//�l���擾
//				String tekisetsuKaigaiValue = (String) lineMap.get("TEKISETSU_KAIGAI");		//�K�ؐ�-�C�O
//				String tekisetsuKenkyu1Value = (String) lineMap.get("TEKISETSU_KENKYU1");	//�K�ؐ�-�����i1�j
//				String tekisetsuValue = (String) lineMap.get("TEKISETSU");					//�K�ؐ�
//				String datoValue = (String) lineMap.get("DATO");							//�Ó���
//	
//				//���x�������擾
//				String tekisetsuKaigaiLabel = getLabelName(tekisetsuKaigaiList, tekisetsuKaigaiValue);
//				String tekisetsuKenkyu1Label = getLabelName(tekisetsuKenkyu1List, tekisetsuKenkyu1Value);
//				String tekisetsuLabel =  getLabelName(tekisetsuList, tekisetsuValue);
//				String datoLabel = getLabelName(datoList, datoValue);
//	
//				//���x�������Z�b�g
//				lineMap.put("TEKISETSU_KAIGAI_LABEL", tekisetsuKaigaiLabel);
//				lineMap.put("TEKISETSU_KENKYU1_LABEL", tekisetsuKenkyu1Label);
//				lineMap.put("TEKISETSU_LABEL", tekisetsuLabel);
//				lineMap.put("DATO_LABEL", datoLabel);
//	
//				//���X�g����폜���Ēǉ����Ȃ���
//				list.remove(i);
//				list.add(i, lineMap);
//			}	
//
//			//---�R���󋵂��u0:�������v�̃f�[�^�����邩�ǂ������m�F
//			int count = 0;
//			try {
//				count = dao.countShinsaKekkaInfo(
//										connection,
//										shinsainNo,
//										jigyoKubun,
//										null,
//										jigyoId,
//										ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET);
//			} catch (DataAccessException e) {
//				throw new ApplicationException(
//					"�R�����ʃf�[�^��������DB�G���[���������܂����B",
//					new ErrorInfo("errors.4004"),
//					e);
//			}
//			
//			//�R���󋵂��u0:�������v�̃f�[�^���Ȃ�������TRUE��Ԃ�
//			String shinsaCompleteFlg = "FALSE";
//			if(count == 0){
//				shinsaCompleteFlg = "TRUE";	
//			}
//			
//			//---�����]�_���X�g���擾
//			List hyotenList = null;
//			try {
//				hyotenList = dao.getSogoHyokaList(
//											connection, 
//											shinsainNo,
//											jigyoKubun,
//											jigyoId);
//			} catch (DataAccessException e) {
//				throw new ApplicationException(
//					"�R�����ʃf�[�^��������DB�G���[���������܂����B",
//					new ErrorInfo("errors.4004"),
//					e);
//			}
//
//			//�}�b�v�ɓ���Ȃ���
//			int allcount = 0;
//			Map hyotenMap = new HashMap();
//			for(int i=0; i<hyotenList.size(); i++){
//				Map recordMap = (Map)hyotenList.get(i);	//�P���R�[�h
//	
//				String kekkaTen_ = (String)recordMap.get("KEKKA_TEN");
//				String count_ = ((Number)recordMap.get("COUNT")).toString();
//				allcount = allcount + Integer.parseInt(count_);
//	
//				hyotenMap.put(kekkaTen_, count_);
//			}
//			//�u���ׂāv�i�������j�̃L�[�́u0�v�ŃZ�b�g
//			hyotenMap.put("0", Integer.toString(allcount));
//
//			//�߂�lMap���쐬
//			Map map = new HashMap();
//			//�R���S�����ꗗ�i�ꗗ�f�[�^�j
//			map.put(KEY_SHINSATANTO_LIST, pageInfo);
//			//�R���S�����ꗗ�i�R�������t���O�j
//			map.put(KEY_SHINSACOMPLETE_FLG,	shinsaCompleteFlg);
//			//�R���S�����ꗗ�i�����]�_���X�g�j
//			map.put(KEY_SOGOHYOTEN_LIST, hyotenMap);
//
//			return map;
//
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//
////		try{
////			connection = DatabaseUtil.getConnection();
////			ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);			
////			//---�R���S���\���ꗗ�y�[�W���
////			List hyotenList = null;
////			try {
////				hyotenList = dao.getSogoHyotenList(
////											connection, 
////											shinsainNo,
////											jigyoKubun,
////											jigyoId);
////			} catch (DataAccessException e) {
////				throw new ApplicationException(
////					"�R�����ʃf�[�^��������DB�G���[���������܂����B",
////					new ErrorInfo("errors.4004"),
////					e);
////			}
////
////			int allcount = 0;
////			Map hyotenMap = new HashMap();
////			for(int i=0; i<hyotenList.size(); i++){
////				Map recordMap = (Map)hyotenList.get(i);	//�P���R�[�h
////				
////				String kekkaTen_ = (String)recordMap.get("KEKKA_TEN");
////				String count = ((Number)recordMap.get("COUNT")).toString();
////				allcount = allcount + Integer.parseInt(count);
////				
////				hyotenMap.put(kekkaTen_, count);
////			}
////			hyotenMap.put("0", Integer.toString(allcount));
////
////			map.put("hyotenList", hyotenMap);
//
////-----�ʂ̕��@
////		Page pageInfo = (Page)map.get(IShinsaKekkaMaintenance.KEY_SHINSATANTO_LIST);
////		List recordList = pageInfo.getList();
//		
////		Map listMap = new HashMap();
////		Map countMap = new HashMap();
////		
////		listMap.put("0", recordList);
////		countMap.put("0", Integer.toString(recordList.size()));
////				
////		for(int i=0; i<recordList.size(); i++){
////			Map recordMap = (Map)recordList.get(i);	//�P���R�[�h
////				
////			//���X�g
////			List indexValueList = null;
////			if(listMap.containsKey(recordMap.get("KEKKA_TEN"))){
////				indexValueList = (List)listMap.get(recordMap.get("KEKKA_TEN"));
////			}else{
////				indexValueList = new ArrayList();
////				listMap.put(recordMap.get("KEKKA_TEN"), indexValueList);
////			}
////			indexValueList.add(recordMap);
////
////			//������
////			int count = 0;
////			if(countMap.containsKey(recordMap.get("KEKKA_TEN"))){
////				count = Integer.parseInt((String)recordMap.get("KEKKA_TEN"));
////				countMap.put((String)recordMap.get("KEKKA_TEN"), Integer.toString(count++));
////			}else{
////				countMap.put((String)recordMap.get("KEKKA_TEN"), "1");
////			}
////		}
////			map.put("list", listMap);
////			map.put("count", countMap);
//
////			return map;
////		} finally {
////			DatabaseUtil.closeConnection(connection);
////		}
//	}
    
// 2006-10-25 ���u�j ���Q�֌W���͊��� �ǉ� ��������
    /**
     * ���͏󋵂̍X�V.
     * ����ɍX�V���s��ꂽ��Atrue��ԋp����B
     * 
     * @param userInfo UserInfo
     * @param jigyoId String
     * @return void
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void updateRiekiSohanComplete(UserInfo userInfo, String jigyoId)
            throws NoDataFoundException, ApplicationException {

        String shinsainNo = userInfo.getShinsainInfo().getShinsainNo(); // �R�����ԍ�
        String jigyoKubun = userInfo.getShinsainInfo().getJigyoKubun(); // ���Ƌ敪
        Connection connection = null;
        boolean success = false;

        try {
            connection = DatabaseUtil.getConnection();

            // �R�����ʊǗ�DAO
            ShinsaKekkaInfoDao dao = new ShinsaKekkaInfoDao(userInfo);

            // ���͏󋵂��X�V
            try {
                dao.updateNyuryokuJokyo(connection, shinsainNo, jigyoKubun,
                        jigyoId, ShinsaKekkaMaintenance.NYURYOKUJOKYO_COMPLETE);
            } catch (NoDataFoundException e) {
                throw e;
            } catch (DataAccessException e) {
                throw new ApplicationException("�R�����ʏ��DB�X�V���ɃG���[���������܂����B",
                        new ErrorInfo("errors.4002"), e);
            }
            success = true;
        } finally {
            try {
                if (success) {
                    DatabaseUtil.commit(connection);
                } else {
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ApplicationException("�R�����ʏ��DB�X�V���ɃG���[���������܂����B",
                        new ErrorInfo("errors.4002"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
// 2006-10-25 ���u�j ���Q�֌W���͊��� �ǉ� �����܂�
  
//2006/10/27�@�c�@�ǉ���������
    /**
     * ���Q�֌W�ӌ��̓o�^
     * @param userInfo UserInfo
     * @param shinsaKekkaInputInfo ShinsaKekkaInputInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registRiekiSohan(
            UserInfo userInfo,
            ShinsaKekkaInputInfo shinsaKekkaInputInfo)
        throws NoDataFoundException, ApplicationException {
        
        Connection   connection  = null;
        boolean     success     = false;
        try {
            //DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();

			//�V�X�e����t�ԍ�
			String systemNo = shinsaKekkaInputInfo.getSystemNo();
            
            //--------------------
            // ���Q�֌W�ӌ��o�^
            //--------------------                      
            //�R������DAO
            ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);            
            //�����f�[�^���擾����
            ShinsaKekkaPk shinsaKekkaPk = (ShinsaKekkaPk)shinsaKekkaInputInfo;
            ShinsaKekkaInfo shinsaKekkaInfo = null;
            try{
                shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfoForLock(connection, shinsaKekkaPk);
            }catch(NoDataFoundException e){
                throw e;
            }catch(DataAccessException e){
                throw new ApplicationException(
                    "�R�����ʏ��擾����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"),
                    e);
            }           

            //---DB�X�V---
            try {
                //�X�V�f�[�^���Z�b�g����       
                shinsaKekkaInfo.setComments(shinsaKekkaInputInfo.getComments());                //�R�����g
                shinsaKekkaInfo.setRigai(shinsaKekkaInputInfo.getRigai());                      //���Q�֌W
                                              
                shinsaKekkaInfo.setKekkaAbc(shinsaKekkaInputInfo.getKekkaAbc());                //�����]���iABC�j
                shinsaKekkaInfo.setKekkaTen(shinsaKekkaInputInfo.getKekkaTen());                //�����]���i�_���j
                shinsaKekkaInfo.setKenkyuNaiyo(shinsaKekkaInputInfo.getKenkyuNaiyo());          //�������e
                shinsaKekkaInfo.setKenkyuKeikaku(shinsaKekkaInputInfo.getKenkyuKeikaku());      //�����v��
                shinsaKekkaInfo.setTekisetsuKaigai(shinsaKekkaInputInfo.getTekisetsuKaigai());  //�K�ؐ�-�C�O
                shinsaKekkaInfo.setTekisetsuKenkyu1(shinsaKekkaInputInfo.getTekisetsuKenkyu1());//�K�ؐ�-����(1)
                shinsaKekkaInfo.setTekisetsu(shinsaKekkaInputInfo.getTekisetsu());              //�K�ؐ�
                shinsaKekkaInfo.setDato(shinsaKekkaInputInfo.getDato());                        //�Ó���
                shinsaKekkaInfo.setWakates(shinsaKekkaInputInfo.getWakates());					//���S�@2007/5/11�ǉ�
                shinsaKekkaInfo.setJuyosei(shinsaKekkaInputInfo.getJuyosei());                  //�w�p�I�d�v���E�Ó���
                shinsaKekkaInfo.setDokusosei(shinsaKekkaInputInfo.getDokusosei());              //�Ƒn���E�v�V��
                shinsaKekkaInfo.setHakyukoka(shinsaKekkaInputInfo.getHakyukoka());              //�g�y���ʁE���Ր�
                shinsaKekkaInfo.setSuikonoryoku(shinsaKekkaInputInfo.getSuikonoryoku());        //���s�\�́E���̓K�ؐ�
                shinsaKekkaInfo.setJinken(shinsaKekkaInputInfo.getJinken());                    //�l���̕ی�E�@�ߓ��̏���
                shinsaKekkaInfo.setBuntankin(shinsaKekkaInputInfo.getBuntankin());              //���S���z��
                shinsaKekkaInfo.setOtherComment(shinsaKekkaInputInfo.getOtherComment());        //���̑��R�����g

                //�R���������Z�b�g����
                shinsaKekkaInfo.setShinsainNameKanjiSei(userInfo.getShinsainInfo().getNameKanjiSei());  //�R�������i����-���j
                shinsaKekkaInfo.setShinsainNameKanjiMei(userInfo.getShinsainInfo().getNameKanjiMei());  //�R�������i����-���j
                shinsaKekkaInfo.setNameKanaSei(userInfo.getShinsainInfo().getNameKanaSei());            //�R�������i�t���K�i�|���j
                shinsaKekkaInfo.setNameKanaMei(userInfo.getShinsainInfo().getNameKanaMei());            //�R�������i�t���K�i�|���j
                shinsaKekkaInfo.setShozokuName(userInfo.getShinsainInfo().getShozokuName());            //�R���������@�֖�              
                shinsaKekkaInfo.setBukyokuName(userInfo.getShinsainInfo().getBukyokuName());            //�R�������ǖ�
                shinsaKekkaInfo.setShokushuName(userInfo.getShinsainInfo().getShokushuName());          //�R�����E��
                shinsaDao.updateShinsaKekkaInfo(connection, shinsaKekkaInfo);
                
                success = true;              
            } catch (DataAccessException e) {
                throw new ApplicationException(
                    "�R�����ʏ��X�V����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4002"),
                    e);
            } catch (ApplicationException e) {
                throw new ApplicationException(
                    "�R�����ʏ��X�V����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4002"),
                    e);
            }
            
			//--------------------
			// �\���f�[�^�X�V
			//--------------------			
			//�\���f�[�^�Ǘ�DAO
			ShinseiDataInfoDao dao = new ShinseiDataInfoDao(userInfo);
						
			//�r������̂��ߊ����f�[�^���擾����
			ShinseiDataPk shinseiDataPk = new ShinseiDataPk(systemNo);
			ShinseiDataInfo existInfo = null;
			try{
				existInfo = dao.selectShinseiDataInfoForLock(connection, shinseiDataPk, true);
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
			if(FLAG_APPLICATION_DELETE.equals(delFlag))
			{
				throw new ApplicationException(
					"���Y�\���f�[�^�͍폜����Ă��܂��BSystemNo=" + systemNo,
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
					+ systemNo,
					new ErrorInfo("errors.9012"));
			}			
			
			//---�R�����ʃ��R�[�h�擾�i����ABC�̏����j---
			ShinsaKekkaInfo[] shinsaKekkaInfoArray = null;						
			try{
				shinsaKekkaInfoArray = shinsaDao.selectShinsaKekkaInfo(connection, shinseiDataPk);
			}catch(NoDataFoundException e){
				throw e;
			}catch(DataAccessException e){
				throw new ApplicationException(
					"�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
					new ErrorInfo("errors.4004"),
					e);
			}

			//---�����]���}�X�^���瑍���]�����̈ꗗ���擾����---					
			HashMap sogoHyokaMap = new HashMap();
			List hyokaList = MasterSogoHyokaInfoDao.selectSogoHyokaInfoList(connection);	
			Iterator iterator = hyokaList.iterator();
			while(iterator.hasNext()){
				Map map = (Map)iterator.next();
				String sogoHyoka = (String) map.get("SOGO_HYOKA");		//�����]��
				String jigyoKubun = new Integer(((Number) map.get("JIGYO_KUBUN")).intValue()).toString();	//���Ƌ敪
				String tensu =  new Integer(((Number) map.get("TENSU")).intValue()).toString();			//�_��
				sogoHyokaMap.put(jigyoKubun + sogoHyoka, tensu);		//�L�[�F���Ƌ敪+�����]���A�l�F�_�� 
			}	
			
			//---DB�X�V---
			try {
				String kekkaAbc = "";
				int intKekkaTen = 0;
				String kekkaTenSorted = "";
				boolean kekkaTenFlag = false;
				for(int i=0; i<shinsaKekkaInfoArray.length; i++){
					try{
//						//�����]���iABC�j�Ƒ����]���i�_���j�͍��݂��Ȃ�
//						if(shinsaKekkaInfoArray[i].getKekkaAbc() != null){
//							kekkaAbc = kekkaAbc + shinsaKekkaInfoArray[i].getKekkaAbc();
//							String tensu = (String) sogoHyokaMap.get(shinsaKekkaInfoArray[i].getJigyoKubun()
//														+ shinsaKekkaInfoArray[i].getKekkaAbc());
//							if(tensu == null){
//								throw new ApplicationException(
//									"�����]���}�X�^�Ɉ�v����f�[�^�����݂��܂���B�����L�[�F�����]��'"
//									+ shinsaKekkaInfoArray[i].getKekkaAbc() 
//									+ "',���Ƌ敪�F'" +  shinsaKekkaInfoArray[i].getJigyoKubun() + "'",
//									new ErrorInfo("errors.4002"));	
//							}
//							intKekkaTen = intKekkaTen
//												+ Integer.parseInt((String) sogoHyokaMap.get(
//																shinsaKekkaInfoArray[i].getJigyoKubun()
//																	 + shinsaKekkaInfoArray[i].getKekkaAbc()));
//							kekkaTenFlag = true;	//1�ł��_�����ݒ肳��Ă����ꍇ[true]						
//						}else 
						if(shinsaKekkaInfoArray[i].getKekkaTen() != null){
								//�P���R������(�_��)
								intKekkaTen = intKekkaTen
													+ Integer.parseInt((String) sogoHyokaMap.get(
																	shinsaKekkaInfoArray[i].getJigyoKubun()
																		 + shinsaKekkaInfoArray[i].getKekkaTen()));
								
								//�P���R������(�_����)
								if(kekkaTenSorted.equals("")){
									kekkaTenSorted = kekkaTenSorted + shinsaKekkaInfoArray[i].getKekkaTen();
								}else{
									kekkaTenSorted = kekkaTenSorted + " " + shinsaKekkaInfoArray[i].getKekkaTen();									
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
				existInfo.setJokyoId(StatusCode.STATUS_1st_SHINSA_KANRYO);		//�\����
				dao.updateShinseiDataInfo(connection, existInfo, true);
				
				success = true;
				
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
            
            
       } finally {
            try {
                if (success) {
                    DatabaseUtil.commit(connection);
                } else {
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ApplicationException(
                    "�R�����ʏ��A�\�����DB�X�V���ɃG���[���������܂����B",
                    new ErrorInfo("errors.4002"),
                    e);
            } finally {                         
                DatabaseUtil.closeConnection(connection);
            }
        }     
    }
    
    /**
     * �R�����ʏ��̎擾(���Q�֌W�p)
     * @param userInfo UserInfo
     * @param shinsaKekkaPk ShinsaKekkaPk
     * @return �R�����ʏ�������ShinsaKekkaInputInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public ShinsaKekkaInputInfo selectShinsaKekkaForRiekiSohan(
        UserInfo userInfo,
        ShinsaKekkaPk shinsaKekkaPk)
        throws NoDataFoundException, ApplicationException {
        
        Connection connection = null;
        try {
            //DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();

            //�\���f�[�^�Ǘ�DAO
            ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
            //�Y���\���f�[�^���擾����
            ShinseiDataPk shinseiDataPk = new ShinseiDataPk(shinsaKekkaPk
                    .getSystemNo());
            ShinseiDataInfo shinseiDataInfo = null;
            try {
                shinseiDataInfo = shinseiDao.selectShinseiDataInfo(connection,
                        shinseiDataPk, true);
            } catch (NoDataFoundException e) {
                throw e;
            } catch (DataAccessException e) {
                throw new ApplicationException("�\�����Ǘ��f�[�^�擾����DB�G���[���������܂����B",
                        new ErrorInfo("errors.4004"), e);
            }

            //�R������DAO
            ShinsaKekkaInfoDao shinsaDao = new ShinsaKekkaInfoDao(userInfo);
            //�Y���R�����ʃf�[�^���擾����
            ShinsaKekkaInfo shinsaKekkaInfo = null;
            try {
                shinsaKekkaInfo = shinsaDao.selectShinsaKekkaInfo(connection,
                        shinsaKekkaPk);
            } catch (NoDataFoundException e) {
                throw e;
            } catch (DataAccessException e) {
                throw new ApplicationException("�R�����ʃf�[�^�擾����DB�G���[���������܂����B",
                        new ErrorInfo("errors.4004"), e);
            }

            //---�R�����ʓ��̓I�u�W�F�N�g�̐���
            ShinsaKekkaInputInfo info = new ShinsaKekkaInputInfo();
            info.setSystemNo(shinsaKekkaPk.getSystemNo()); //�V�X�e����t�ԍ�
            info.setShinsainNo(shinsaKekkaPk.getShinsainNo()); //�R�����ԍ�
            info.setJigyoKubun(shinsaKekkaPk.getJigyoKubun()); //���Ƌ敪
            info.setNendo(shinseiDataInfo.getNendo()); //�N�x
            info.setKaisu(shinseiDataInfo.getKaisu()); //��

            info.setBunkaSaimokuCd(shinseiDataInfo.getKadaiInfo().getBunkaSaimokuCd()); //�זڔԍ�
            info.setSaimokuName(shinseiDataInfo.getKadaiInfo().getSaimokuName()); //�זږ�
            info.setKaigaibunyaCd(shinseiDataInfo.getKaigaibunyaCd());//�C�O����R�[�h
            info.setKaigaibunyaName(shinseiDataInfo.getKaigaibunyaName());//�C�O���얼
            info.setJigyoId(shinseiDataInfo.getJigyoId()); //����ID          
            info.setJigyoName(shinseiDataInfo.getJigyoName()); //������ږ�
            info.setUketukeNo(shinseiDataInfo.getUketukeNo()); //�\���ԍ�
            info.setKadaiNameKanji(shinseiDataInfo.getKadaiInfo().getKadaiNameKanji()); //�����ۑ薼
            info.setNameKanjiSei(shinseiDataInfo.getDaihyouInfo().getNameKanjiSei()); //�\���Җ�-��
            info.setNameKanjiMei(shinseiDataInfo.getDaihyouInfo().getNameKanjiMei()); //�\���Җ�-��
            info.setShozokuName(shinseiDataInfo.getDaihyouInfo().getShozokuName()); //�����@�֖�
            info.setBukyokuName(shinseiDataInfo.getDaihyouInfo().getBukyokuName()); //���ǖ�
            info.setShokushuName(shinseiDataInfo.getDaihyouInfo().getShokushuNameKanji()); //�E��
            info.setShinseiFlgNo(shinseiDataInfo.getShinseiFlgNo()); //�����v��ŏI�N�x�O�N�x�̉���
            info.setBuntankinFlg(shinseiDataInfo.getBuntankinFlg()); //���S���̗L��
            info.setRigai(shinsaKekkaInfo.getRigai()); //���Q�֌W
            info.setJigyoCd(shinsaKekkaInfo.getJigyoId().substring(2, 7)); //���ƃR�[�h
            info.setComments(shinsaKekkaInfo.getComments());

            return info;
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
//2006/10/27�@�c�@�ǉ������܂�    
}