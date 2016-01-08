/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.impl;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.impl.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.status.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * �R�����ʏ��f�[�^�A�N�Z�X�N���X�B
 */
public class ShinsaKekkaInfoDao {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static final Log log = LogFactory.getLog(ShinsaKekkaInfoDao.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���s���郆�[�U��� */
	private UserInfo userInfo = null;
	
	/** DB�����N�� */
	private String   dbLink   = "";
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	
	/**
	 * �R���X�g���N�^�B
	 * @param userInfo	���s���郆�[�U���
	 */
	public ShinsaKekkaInfoDao(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	/**
	 * �R���X�g���N�^�B
	 * @param userInfo	���s���郆�[�U���
	 * @param dbLink   DB�����N��
	 */
	public ShinsaKekkaInfoDao(UserInfo userInfo, String dbLink) {
		this.userInfo = userInfo;
		this.dbLink   = dbLink;
	}
	

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * �R�����ʏ����擾����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�R�����ʏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public ShinsaKekkaInfo selectShinsaKekkaInfo(
		Connection connection,
		ShinsaKekkaPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		return selectShinsaKekkaInfo(connection, primaryKeys, false);
	}
	
	/**
	 * �R�����ʏ����擾����B
	 * �擾�������R�[�h�����b�N����B�icomit���s����܂ŁB�j
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @return						�R�����ʏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public ShinsaKekkaInfo selectShinsaKekkaInfoForLock(
		Connection connection,
		ShinsaKekkaPk primaryKeys)
		throws DataAccessException, NoDataFoundException
	{
		return selectShinsaKekkaInfo(connection, primaryKeys, true);
	}
	
	/**
	 * �R�����ʏ����擾����B
	 * @param connection			�R�l�N�V����
	 * @param primaryKeys			��L�[���
	 * @param lock                 true�̏ꍇ�̓��R�[�h�����b�N����
	 * @return						�R�����ʏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	private ShinsaKekkaInfo selectShinsaKekkaInfo(
		Connection connection,
		ShinsaKekkaPk primaryKeys,
		boolean lock)
		throws DataAccessException, NoDataFoundException
	{
//8/20���݂̃e�[�u��	
		String query =
			"SELECT "
				+ " A.SYSTEM_NO"				//�V�X�e����t�ԍ�
				+ ",A.UKETUKE_NO"				//�\���ԍ�
				+ ",A.SHINSAIN_NO"				//�R�����ԍ�
				+ ",A.JIGYO_KUBUN"				//���Ƌ敪
				+ ",A.SEQ_NO"					//�V�[�P���X�ԍ�
				+ ",A.SHINSA_KUBUN"				//�R���敪
				+ ",A.SHINSAIN_NAME_KANJI_SEI"	//�R�������i�����|���j
				+ ",A.SHINSAIN_NAME_KANJI_MEI"	//�R�������i�����|���j
				+ ",A.NAME_KANA_SEI"			//�R�������i�t���K�i�|���j
				+ ",A.NAME_KANA_MEI"			//�R�������i�t���K�i�|���j
				+ ",A.SHOZOKU_NAME"				//�R���������@�֖�
				+ ",A.BUKYOKU_NAME"				//�R�������ǖ�
				+ ",A.SHOKUSHU_NAME"			//�R�����E��
				+ ",A.JIGYO_ID"					//����ID
				+ ",A.JIGYO_NAME"				//���Ɩ�
				+ ",A.BUNKASAIMOKU_CD"			//�זڔԍ�
				+ ",A.EDA_NO"					//�}��				
				+ ",A.CHECKDIGIT"				//�`�F�b�N�f�W�b�g
				+ ",A.KEKKA_ABC"				//�����]���iABC�j
				+ ",A.KEKKA_TEN"				//�����]���i�_���j
				+ ",A.COMMENT1"					//�R�����g1			
				+ ",A.COMMENT2"					//�R�����g2
				+ ",A.COMMENT3"					//�R�����g3
				+ ",A.COMMENT4"					//�R�����g4
				+ ",A.COMMENT5"					//�R�����g5
				+ ",A.COMMENT6"					//�R�����g6
				+ ",A.KENKYUNAIYO"				//�������e
				+ ",A.KENKYUKEIKAKU"			//�����v��		
				+ ",A.TEKISETSU_KAIGAI"			//�K�ؐ�-�C�O
				+ ",A.TEKISETSU_KENKYU1"		//�K�ؐ�-�����i1�j
				+ ",A.TEKISETSU"				//�K�ؐ�			
				+ ",A.DATO"						//�Ó���
				+ ",A.SHINSEISHA"				//������\��
				+ ",A.KENKYUBUNTANSHA"			//�������S��
				+ ",A.HITOGENOMU"				//�q�g�Q�m��
				+ ",A.TOKUTEI"					//������
				+ ",A.HITOES"					//�q�gES�זE
				+ ",A.KUMIKAE"					//��`�q�g��������
				+ ",A.CHIRYO"					//��`�q���×Տ�����			
				+ ",A.EKIGAKU"					//�u�w����
				+ ",A.COMMENTS"					//�R�����g
				//2005.10.25 kainuma	
				+ ",A.RIGAI"					//���Q�֌W
				+ ",A.DAIRI"					//�㗝�t���O			//2005/11/04 �ǉ�
				+ ",A.TEKISETSU_WAKATES"		//���(S)�Ƃ��Ă̑Ó���	//2007/5/8 �ǉ�
				+ ",A.JUYOSEI"					//�w�p�I�d�v���E�Ó���
				+ ",A.DOKUSOSEI"				//�Ƒn���E�v�V��
				+ ",A.HAKYUKOKA"				//�g�y���ʁE���Ր�
				+ ",A.SUIKONORYOKU"				//���s�\�́E���̓K�ؐ�
				+ ",A.JINKEN"					//�l���̕ی�E�@�ߓ��̏���
				+ ",A.BUNTANKIN"				//���S���z��			
				+ ",A.OTHER_COMMENT"			//���̑��R�����g
				//2005.10.25 kainuma
				+ ",A.KOSHIN_DATE"				//����U��X�V��		//2005/11/08 �ǉ�
				+ ",A.TENPU_PATH"				//�Y�t�t�@�C���i�[�p�X			
				+ ",DECODE"
				+ " ("
				+ "  NVL(A.TENPU_PATH,'null') "
				+ "  ,'null','FALSE'"			//�Y�t�t�@�C���i�[�p�X��NULL�̂Ƃ�
				+ "  ,      'TRUE'"				//�Y�t�t�@�C���i�[�p�X��NULL�ȊO�̂Ƃ�
				+ " ) TENPU_FLG"				//�Y�t�t�@�C���i�[�t���O
				+ ",A.SHINSA_JOKYO"				//�R����
				+ ",A.BIKO"						//���l
				+ " FROM SHINSAKEKKA"+dbLink+" A"
				+ " WHERE"
				+ "  SYSTEM_NO = ?"
				+ " AND"
				+ "  SHINSAIN_NO = ?"
				+ " AND"
				+ "  JIGYO_KUBUN = ?"
				;
				
			//�r��������s���ꍇ
			if(lock){
				query = query + " FOR UPDATE";
			}				
				
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			ShinsaKekkaInfo result = new ShinsaKekkaInfo();
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, primaryKeys.getSystemNo());		//�V�X�e����t�ԍ�
			DatabaseUtil.setParameter(preparedStatement, i++, primaryKeys.getShinsainNo());		//�R�����ԍ�
			DatabaseUtil.setParameter(preparedStatement, i++, primaryKeys.getJigyoKubun());		//���Ƌ敪
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				result.setSystemNo(recordSet.getString("SYSTEM_NO"));
				result.setUketukeNo(recordSet.getString("UKETUKE_NO"));
				result.setShinsainNo(recordSet.getString("SHINSAIN_NO"));		
				result.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));		
				result.setSeqNo(recordSet.getString("SEQ_NO"));
				result.setShinsaKubun(recordSet.getString("SHINSA_KUBUN"));
				result.setShinsainNameKanjiSei(recordSet.getString("SHINSAIN_NAME_KANJI_SEI"));
				result.setShinsainNameKanjiMei(recordSet.getString("SHINSAIN_NAME_KANJI_MEI"));
				result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
				result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
				result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
				result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
				result.setShokushuName(recordSet.getString("SHOKUSHU_NAME"));
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setJigyoName(recordSet.getString("JIGYO_NAME"));
				result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
				result.setEdaNo(recordSet.getString("EDA_NO"));
				result.setCheckDigit(recordSet.getString("CHECKDIGIT"));		
				result.setKekkaAbc(recordSet.getString("KEKKA_ABC"));		
				result.setKekkaTen(recordSet.getString("KEKKA_TEN"));
				result.setComment1(recordSet.getString("COMMENT1"));
				result.setComment2(recordSet.getString("COMMENT2"));
				result.setComment3(recordSet.getString("COMMENT3"));
				result.setComment4(recordSet.getString("COMMENT4"));
				result.setComment5(recordSet.getString("COMMENT5"));
				result.setComment6(recordSet.getString("COMMENT6"));
				result.setKenkyuNaiyo(recordSet.getString("KENKYUNAIYO"));
				result.setKenkyuKeikaku(recordSet.getString("KENKYUKEIKAKU"));
				result.setTekisetsuKaigai(recordSet.getString("TEKISETSU_KAIGAI"));			
				result.setTekisetsuKenkyu1(recordSet.getString("TEKISETSU_KENKYU1"));
				result.setTekisetsu(recordSet.getString("TEKISETSU"));
				result.setDato(recordSet.getString("DATO"));
				result.setShinseisha(recordSet.getString("SHINSEISHA"));
				result.setKenkyuBuntansha(recordSet.getString("KENKYUBUNTANSHA"));
				result.setHitogenomu(recordSet.getString("HITOGENOMU"));
				result.setTokutei(recordSet.getString("TOKUTEI"));
				result.setHitoEs(recordSet.getString("HITOES"));
				result.setKumikae(recordSet.getString("KUMIKAE"));
				result.setChiryo(recordSet.getString("CHIRYO"));
				result.setEkigaku(recordSet.getString("EKIGAKU"));
				result.setComments(recordSet.getString("COMMENTS"));
				//2005.10.26 kainuma
				result.setRigai(recordSet.getString("RIGAI"));
				result.setDairi(recordSet.getString("DAIRI"));
				result.setWakates(recordSet.getString("TEKISETSU_WAKATES"));	//2007/5/8�ǉ�
				result.setJuyosei(recordSet.getString("JUYOSEI"));
				result.setDokusosei(recordSet.getString("DOKUSOSEI"));
				result.setHakyukoka(recordSet.getString("HAKYUKOKA"));
				result.setSuikonoryoku(recordSet.getString("SUIKONORYOKU"));
				result.setJinken(recordSet.getString("JINKEN"));
				result.setBuntankin(recordSet.getString("BUNTANKIN"));
				result.setOtherComment(recordSet.getString("OTHER_COMMENT"));
				//
				result.setKoshinDate(recordSet.getDate("KOSHIN_DATE"));
				result.setTenpuPath(recordSet.getString("TENPU_PATH"));
				result.setTenpuFlg(recordSet.getString("TENPU_FLG"));
				result.setShinsaJokyo(recordSet.getString("SHINSA_JOKYO"));
				result.setBiko(recordSet.getString("BIKO"));
				return result;
			} else {
				throw new NoDataFoundException(
					"�R�����ʏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
						+ primaryKeys.getSystemNo()
						+ "', �R�����ԍ�'"
				        + primaryKeys.getShinsainNo()
						+ "', ���Ƌ敪'"
						+ primaryKeys.getJigyoKubun()
						+ "'");		
			}
		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}
	
	/**
	 * �R�����ʏ����擾����B
	 * �\�[�g���͑����]���iABC�j�̏����A�����]���i�_���j�̍~���A�R�����ԍ��̏����A���Ƌ敪�̏����Ƃ���B
	 * @param connection			�R�l�N�V����
     * @param shinseiDataPk         �L�[
	 * @return						�R�����ʏ��
	 * @throws DataAccessException	�f�[�^�擾���ɗ�O�����������ꍇ�B
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public ShinsaKekkaInfo[] selectShinsaKekkaInfo(
		Connection connection,
		ShinseiDataPk shinseiDataPk)
		throws DataAccessException, NoDataFoundException
	{
//8/20���݂̃e�[�u��			
		String query =
			"SELECT "
				+ " A.SYSTEM_NO"				//�V�X�e����t�ԍ�
				+ ",A.UKETUKE_NO"				//�\���ԍ�
				+ ",A.SHINSAIN_NO"				//�R�����ԍ�
				+ ",A.JIGYO_KUBUN"				//���Ƌ敪
				+ ",A.SEQ_NO"					//�V�[�P���X�ԍ�
				+ ",A.SHINSA_KUBUN"				//�R���敪
				+ ",A.SHINSAIN_NAME_KANJI_SEI"	//�R�������i�����|���j
				+ ",A.SHINSAIN_NAME_KANJI_MEI"	//�R�������i�����|���j
				+ ",A.NAME_KANA_SEI"			//�R�������i�t���K�i�|���j
				+ ",A.NAME_KANA_MEI"			//�R�������i�t���K�i�|���j
				+ ",A.SHOZOKU_NAME"				//�R���������@�֖�
				+ ",A.BUKYOKU_NAME"				//�R�������ǖ�
				+ ",A.SHOKUSHU_NAME"			//�R�����E��
				+ ",A.JIGYO_ID"					//����ID
				+ ",A.JIGYO_NAME"				//���Ɩ�
				+ ",A.BUNKASAIMOKU_CD"			//�זڔԍ�
				+ ",A.EDA_NO"					//�}��				
				+ ",A.CHECKDIGIT"				//�`�F�b�N�f�W�b�g
				+ ",A.KEKKA_ABC"				//�����]���iABC�j
				+ ",A.KEKKA_TEN"				//�����]���i�_���j
				+ ",NVL(REPLACE(A.KEKKA_TEN, '-', '0'), '-1') SORT_KEKKA_TEN"	//�\�[�g�p�B�R�����ʁi�_���j�̒lNULL��'-1'�A'-'��'0'�ɒu���j
				+ ",A.COMMENT1"					//�R�����g1			
				+ ",A.COMMENT2"					//�R�����g2
				+ ",A.COMMENT3"					//�R�����g3
				+ ",A.COMMENT4"					//�R�����g4
				+ ",A.COMMENT5"					//�R�����g5
				+ ",A.COMMENT6"					//�R�����g6
				+ ",A.KENKYUNAIYO"				//�������e
				+ ",A.KENKYUKEIKAKU"			//�����v��		
				+ ",A.TEKISETSU_KAIGAI"			//�K�ؐ�-�C�O
				+ ",A.TEKISETSU_KENKYU1"		//�K�ؐ�-�����i1�j
				+ ",A.TEKISETSU"				//�K�ؐ�			
				+ ",A.DATO"						//�Ó���
				+ ",A.SHINSEISHA"				//������\��
				+ ",A.KENKYUBUNTANSHA"			//�������S��
				+ ",A.HITOGENOMU"				//�q�g�Q�m��
				+ ",A.TOKUTEI"					//������
				+ ",A.HITOES"					//�q�gES�זE
				+ ",A.KUMIKAE"					//��`�q�g��������
				+ ",A.CHIRYO"					//��`�q���×Տ�����			
				+ ",A.EKIGAKU"					//�u�w����
				+ ",A.COMMENTS"					//�R�����g
				//2005.10.25 kainuma	
			  	+ ",A.RIGAI"					//���Q�֌W
			  	+ ",A.DAIRI"					//�㗝�t���O			//2005/11/04 �ǉ�
				+ ",A.TEKISETSU_WAKATES"		//���(S)�Ƃ��Ă̑Ó���	//2007/5/8 �ǉ�
				+ ",A.JUYOSEI"					//�w�p�I�d�v���E�Ó���
				+ ",A.DOKUSOSEI"				//�Ƒn���E�v�V��
				+ ",A.HAKYUKOKA"				//�g�y���ʁE���Ր�
			  	+ ",A.SUIKONORYOKU"				//���s�\�́E���̓K�ؐ�
				+ ",A.JINKEN"					//�l���̕ی�E�@�ߓ��̏���
				+ ",A.BUNTANKIN"				//���S���z��			
				+ ",A.OTHER_COMMENT"			//���̑��R�����g
				//2005.10.25 kainuma	
				+ ",A.KOSHIN_DATE"				//����U��X�V��			//2005/11/08 �ǉ�
				+ ",A.TENPU_PATH"				//�Y�t�t�@�C���i�[�p�X
				+ ",A.SHINSA_JOKYO"				//�R����
				+ ",A.BIKO"						//���l
				+ " FROM SHINSAKEKKA"+dbLink+" A"
				+ " WHERE"
				+ "  SYSTEM_NO = ?"
				+ " ORDER BY"
				+ " KEKKA_ABC ASC"						//�����]���iABC�j�̏���
				+ " ,SORT_KEKKA_TEN DESC"				//�����]���i�_���j�̍~��
				+ " ,SHINSAIN_NO ASC"					//�R�����ԍ��̏���
				+ " ,JIGYO_KUBUN ASC"					//���Ƌ敪�̏���
				;
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		ShinsaKekkaInfo[] shinseiKekkaInfo = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, shinseiDataPk.getSystemNo());	//�V�X�e����t�ԍ�
			recordSet = preparedStatement.executeQuery();

			List resultList = new ArrayList();
			while(recordSet.next()){
				ShinsaKekkaInfo result = new ShinsaKekkaInfo();
				result.setSystemNo(recordSet.getString("SYSTEM_NO"));
				result.setUketukeNo(recordSet.getString("UKETUKE_NO"));
				result.setShinsainNo(recordSet.getString("SHINSAIN_NO"));		
				result.setJigyoKubun(recordSet.getString("JIGYO_KUBUN"));
				result.setSeqNo(recordSet.getString("SEQ_NO"));
				result.setShinsaKubun(recordSet.getString("SHINSA_KUBUN"));		
				result.setShinsainNameKanjiSei(recordSet.getString("SHINSAIN_NAME_KANJI_SEI"));
				result.setShinsainNameKanjiMei(recordSet.getString("SHINSAIN_NAME_KANJI_MEI"));
				result.setNameKanaSei(recordSet.getString("NAME_KANA_SEI"));
				result.setNameKanaMei(recordSet.getString("NAME_KANA_MEI"));
				result.setShozokuName(recordSet.getString("SHOZOKU_NAME"));
				result.setBukyokuName(recordSet.getString("BUKYOKU_NAME"));
				result.setShokushuName(recordSet.getString("SHOKUSHU_NAME"));
				result.setJigyoId(recordSet.getString("JIGYO_ID"));
				result.setJigyoName(recordSet.getString("JIGYO_NAME"));
				result.setBunkaSaimokuCd(recordSet.getString("BUNKASAIMOKU_CD"));
				result.setEdaNo(recordSet.getString("EDA_NO"));
				result.setCheckDigit(recordSet.getString("CHECKDIGIT"));		
				result.setKekkaAbc(recordSet.getString("KEKKA_ABC"));		
				result.setKekkaTen(recordSet.getString("KEKKA_TEN"));	
				result.setComment1(recordSet.getString("COMMENT1"));
				result.setComment2(recordSet.getString("COMMENT2"));
				result.setComment3(recordSet.getString("COMMENT3"));
				result.setComment4(recordSet.getString("COMMENT4"));
				result.setComment5(recordSet.getString("COMMENT5"));
				result.setComment6(recordSet.getString("COMMENT6"));
				result.setKenkyuNaiyo(recordSet.getString("KENKYUNAIYO"));
				result.setKenkyuKeikaku(recordSet.getString("KENKYUKEIKAKU"));
				result.setTekisetsuKaigai(recordSet.getString("TEKISETSU_KAIGAI"));			
				result.setTekisetsuKenkyu1(recordSet.getString("TEKISETSU_KENKYU1"));
				result.setTekisetsu(recordSet.getString("TEKISETSU"));
				result.setDato(recordSet.getString("DATO"));
				result.setShinseisha(recordSet.getString("SHINSEISHA"));
				result.setKenkyuBuntansha(recordSet.getString("KENKYUBUNTANSHA"));
				result.setHitogenomu(recordSet.getString("HITOGENOMU"));
				result.setTokutei(recordSet.getString("TOKUTEI"));
				result.setHitoEs(recordSet.getString("HITOES"));
				result.setKumikae(recordSet.getString("KUMIKAE"));
				result.setChiryo(recordSet.getString("CHIRYO"));
				result.setEkigaku(recordSet.getString("EKIGAKU"));
				result.setComments(recordSet.getString("COMMENTS"));
				//2005.10.26 kainuma
				result.setRigai(recordSet.getString("RIGAI"));
				result.setDairi(recordSet.getString("DAIRI"));
				result.setWakates(recordSet.getString("TEKISETSU_WAKATES"));	//2007/5/8 �ǉ�
				result.setJuyosei(recordSet.getString("JUYOSEI"));
				result.setDokusosei(recordSet.getString("DOKUSOSEI"));
				result.setHakyukoka(recordSet.getString("HAKYUKOKA"));
				result.setSuikonoryoku(recordSet.getString("SUIKONORYOKU"));
				result.setJinken(recordSet.getString("JINKEN"));
				result.setBuntankin(recordSet.getString("BUNTANKIN"));
				result.setOtherComment(recordSet.getString("OTHER_COMMENT"));
				//
				result.setKoshinDate(recordSet.getDate("KOSHIN_DATE"));				
				result.setTenpuPath(recordSet.getString("TENPU_PATH"));
				result.setShinsaJokyo(recordSet.getString("SHINSA_JOKYO"));
				result.setBiko(recordSet.getString("BIKO"));			
				resultList.add(result);
			}
			
			//�߂�l
			shinseiKekkaInfo = (ShinsaKekkaInfo[])resultList.toArray(new ShinsaKekkaInfo[0]);
			if(shinseiKekkaInfo.length == 0){
				throw new NoDataFoundException(
					"�R�����ʏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�V�X�e����t�ԍ�'"
						+ shinseiDataPk.getSystemNo() + "'");
			}

		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
		
		return shinseiKekkaInfo;		
	}	

	/**
	 * �R�����ʏ���o�^����B
	 * @param connection				�R�l�N�V����
	 * @param addInfo					�o�^����R�����ʏ��
	 * @throws DataAccessException		�o�^���ɗ�O�����������ꍇ�B	
	 * @throws DuplicateKeyException	�L�[�Ɉ�v����f�[�^�����ɑ��݂���ꍇ�B
	 */
	public void insertShinsaKekkaInfo(
		Connection connection,
		ShinsaKekkaInfo addInfo)
		throws DataAccessException, DuplicateKeyException
	{
		//�d���`�F�b�N
		try {
			selectShinsaKekkaInfo(connection, addInfo);
			//NG
			throw new DuplicateKeyException(
				"'" + addInfo + "'�͊��ɓo�^����Ă��܂��B");
		} catch (NoDataFoundException e) {
			//OK
		}
//8/20���݂̃e�[�u��			
		String query =
			"INSERT INTO SHINSAKEKKA"+dbLink
				+ " ("
				+ " SYSTEM_NO"					//�V�X�e����t�ԍ�
				+ ",UKETUKE_NO"					//�\���ԍ�
				+ ",SHINSAIN_NO"				//�R�����ԍ�
				+ ",JIGYO_KUBUN"				//���Ƌ敪
				+ ",SEQ_NO"						//�V�[�P���X�ԍ�
				+ ",SHINSA_KUBUN"				//�R���敪
				+ ",SHINSAIN_NAME_KANJI_SEI"	//�R�������i�����|���j
				+ ",SHINSAIN_NAME_KANJI_MEI"	//�R�������i�����|���j
				+ ",NAME_KANA_SEI"				//�R�������i�t���K�i�|���j
				+ ",NAME_KANA_MEI"				//�R�������i�t���K�i�|���j
				+ ",SHOZOKU_NAME"				//�R���������@�֖�
				+ ",BUKYOKU_NAME"				//�R�������ǖ�
				+ ",SHOKUSHU_NAME"				//�R�����E��
				+ ",JIGYO_ID"					//����ID
				+ ",JIGYO_NAME"					//���Ɩ�
				+ ",BUNKASAIMOKU_CD"			//�זڔԍ�
				+ ",EDA_NO"						//�}��				
				+ ",CHECKDIGIT"					//�`�F�b�N�f�W�b�g
				+ ",KEKKA_ABC"					//�����]���iABC�j
				+ ",KEKKA_TEN"					//�����]���i�_���j
				+ ",COMMENT1"					//�R�����g1			
				+ ",COMMENT2"					//�R�����g2
				+ ",COMMENT3"					//�R�����g3
				+ ",COMMENT4"					//�R�����g4
				+ ",COMMENT5"					//�R�����g5
				+ ",COMMENT6"					//�R�����g6
				+ ",KENKYUNAIYO"				//�������e
				+ ",KENKYUKEIKAKU"				//�����v��		
				+ ",TEKISETSU_KAIGAI"			//�K�ؐ�-�C�O
				+ ",TEKISETSU_KENKYU1"			//�K�ؐ�-�����i1�j
				+ ",TEKISETSU"					//�K�ؐ�			
				+ ",DATO"						//�Ó���
				+ ",SHINSEISHA"					//������\��
				+ ",KENKYUBUNTANSHA"			//�������S��
				+ ",HITOGENOMU"					//�q�g�Q�m��
				+ ",TOKUTEI"					//������
				+ ",HITOES"						//�q�gES�זE
				+ ",KUMIKAE"					//��`�q�g��������
				+ ",CHIRYO"						//��`�q���×Տ�����			
				+ ",EKIGAKU"					//�u�w����
				+ ",COMMENTS"					//�R�����g
				//2005.10.25 kainuma	
				+ ",RIGAI"					    //���Q�֌W
				+ ",DAIRI"						//�㗝�t���O		2005/11/04 �ǉ�
				+ ",TEKISETSU_WAKATES"			//���(S)�Ƃ��Ă̑Ó���	//2007/5/8 �ǉ�
				+ ",JUYOSEI"					//�w�p�I�d�v���E�Ó���
				+ ",DOKUSOSEI"				    //�Ƒn���E�v�V��
				+ ",HAKYUKOKA"				    //�g�y���ʁE���Ր�
				+ ",SUIKONORYOKU"				//���s�\�́E���̓K�ؐ�
				+ ",JINKEN"					    //�l���̕ی�E�@�ߓ��̏���
				+ ",BUNTANKIN"				    //���S���z��			
				+ ",OTHER_COMMENT"			    //���̑��R�����g
				//2005.10.25 kainuma
				+ ",KOSHIN_DATE"				//����U��X�V��		2005/11/08 �ǉ�
				+ ",TENPU_PATH"					//�Y�t�t�@�C���i�[�p�X
				+ ",SHINSA_JOKYO"				//�R����
				+ ",BIKO"						//���l
				+ " )"
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"//30��
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//55��
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getUketukeNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoKubun());			
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSeqNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsaKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsainNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsainNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoId());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJigyoName());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBunkaSaimokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getEdaNo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getCheckDigit());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKekkaAbc());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKekkaTen());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment2());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment3());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment4());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment5());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComment6());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuNaiyo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuKeikaku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTekisetsuKaigai());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTekisetsuKenkyu1());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTekisetsu());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getDato());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinseisha());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKenkyuBuntansha());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHitogenomu());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTokutei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHitoEs());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKumikae());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getChiryo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getEkigaku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getComments());
			//2005.10.25 kainuma
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getRigai());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getDairi());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getWakates());//2007/5/8 �ǉ�
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJuyosei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getDokusosei());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getHakyukoka());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getSuikonoryoku());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getJinken());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBuntankin());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getOtherComment());	
			//
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getKoshinDate());				
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getTenpuPath());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getShinsaJokyo());
			DatabaseUtil.setParameter(preparedStatement,i++,addInfo.getBiko());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			log.error("�R�����ʏ��o�^���ɗ�O���������܂����B ", ex);
			throw new DataAccessException("�R�����ʏ��o�^���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * �R�����ʏ����X�V����B
	 * @param connection				�R�l�N�V����
	 * @param updateInfo				�X�V����R�����ʏ��
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateShinsaKekkaInfo(
		Connection connection,
		ShinsaKekkaInfo updateInfo)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectShinsaKekkaInfo(connection, updateInfo);
//8/20���݂̃e�[�u��
		String query =
			"UPDATE SHINSAKEKKA"+dbLink
				+ " SET"	
				+ " SYSTEM_NO = ? "					//�V�X�e����t�ԍ�
				+ ",UKETUKE_NO = ? "				//�\���ԍ�			
				+ ",SHINSAIN_NO = ? "				//�R�����ԍ�
				+ ",JIGYO_KUBUN = ? "				//���Ƌ敪
				+ ",SEQ_NO = ? "					//�V�[�P���X�ԍ�
				+ ",SHINSA_KUBUN = ? "				//�R���敪
				+ ",SHINSAIN_NAME_KANJI_SEI = ? "	//�R�������i�����|���j
				+ ",SHINSAIN_NAME_KANJI_MEI = ? "	//�R�������i�����|���j
				+ ",NAME_KANA_SEI = ? "				//�R�������i�t���K�i�|���j
				+ ",NAME_KANA_MEI = ? "				//�R�������i�t���K�i�|���j
				+ ",SHOZOKU_NAME = ? "				//�R���������@�֖�
				+ ",BUKYOKU_NAME = ? "				//�R�������ǖ�
				+ ",SHOKUSHU_NAME = ? "				//�R�����E��
				+ ",JIGYO_ID = ? "					//����ID
				+ ",JIGYO_NAME = ? "				//���Ɩ�
				+ ",BUNKASAIMOKU_CD = ? "			//�זڔԍ�
				+ ",EDA_NO = ? "					//�}��				
				+ ",CHECKDIGIT = ? "				//�`�F�b�N�f�W�b�g
				+ ",KEKKA_ABC = ? "					//�����]���iABC�j
				+ ",KEKKA_TEN = ? "					//�����]���i�_���j
				+ ",COMMENT1 = ? "					//�R�����g1			
				+ ",COMMENT2 = ? "					//�R�����g2
				+ ",COMMENT3 = ? "					//�R�����g3
				+ ",COMMENT4 = ? "					//�R�����g4
				+ ",COMMENT5 = ? "					//�R�����g5
				+ ",COMMENT6 = ? "					//�R�����g6
				+ ",KENKYUNAIYO = ? "				//�������e
				+ ",KENKYUKEIKAKU = ? "				//�����v��		
				+ ",TEKISETSU_KAIGAI = ? "			//�K�ؐ�-�C�O
				+ ",TEKISETSU_KENKYU1 = ? "			//�K�ؐ�-�����i1�j
				+ ",TEKISETSU = ? "					//�K�ؐ�			
				+ ",DATO = ? "						//�Ó���
				+ ",SHINSEISHA = ? "				//������\��
				+ ",KENKYUBUNTANSHA = ? "			//�������S��
				+ ",HITOGENOMU = ? "				//�q�g�Q�m��
				+ ",TOKUTEI = ? "					//������
				+ ",HITOES = ? "					//�q�gES�זE
				+ ",KUMIKAE = ? "					//��`�q�g��������
				+ ",CHIRYO = ? "					//��`�q���×Տ�����			
				+ ",EKIGAKU = ? "					//�u�w����
				+ ",COMMENTS = ? "					//�R�����g
				//2005.10.25 kainuma	
			  	+ ",RIGAI = ? "						//���Q�֌W
			  	+ ",DAIRI = ? "						//�㗝�t���O 2005/11/04 �ǉ�
			 	+ ",TEKISETSU_WAKATES = ? "			//���S�Ƃ��Ă̑Ó��� 2007/5/8�ǉ�
			 	+ ",JUYOSEI = ? "					//�w�p�I�d�v���E�Ó���
			 	+ ",DOKUSOSEI = ? "					//�Ƒn���E�v�V��
				+ ",HAKYUKOKA = ? "					//�g�y���ʁE���Ր�
			 	+ ",SUIKONORYOKU = ? "				//���s�\�́E���̓K�ؐ�
				+ ",JINKEN = ? "					//�l���̕ی�E�@�ߓ��̏���
			 	+ ",BUNTANKIN = ? "					//���S���z��			
			  	+ ",OTHER_COMMENT = ?"				//���̑��R�����g
			    //2005.10.25 kainuma	
			    + ",KOSHIN_DATE = ?"				//����U��X�V��		//2005/11/08 �ǉ�
				+ ",TENPU_PATH = ? "				//�Y�t�t�@�C���i�[�p�X
				+ ",SHINSA_JOKYO = ? "				//�R����
				+ ",BIKO = ? "						//���l
				+ " WHERE"
				+ "  SYSTEM_NO = ?"
				+ " AND"
				+ "  SHINSAIN_NO = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�X�V
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getUketukeNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());			
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSeqNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsaKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNameKanjiMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoId());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBunkaSaimokuCd());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getEdaNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getCheckDigit());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKekkaAbc());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKekkaTen());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment1());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment2());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment3());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment4());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment5());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComment6());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuNaiyo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuKeikaku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTekisetsuKaigai());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTekisetsuKenkyu1());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTekisetsu());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDato());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinseisha());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKenkyuBuntansha());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHitogenomu());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTokutei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHitoEs());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKumikae());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getChiryo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getEkigaku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getComments());
			//2005.10.26 kainuma
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getRigai());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDairi());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getWakates());//2007/5/8�ǉ�
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJuyosei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getDokusosei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getHakyukoka());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSuikonoryoku());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJinken());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBuntankin());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getOtherComment());
			//
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getKoshinDate());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getTenpuPath());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsaJokyo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBiko());			
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getSystemNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
			DatabaseUtil.executeUpdate(preparedStatement);
		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}


	/**
	 * �S���R�����ʏ��̐R���������X�V����B
	 * ����ID����Ȃ�A�S�Ă̎��Ƃ��ΏہB
	 * �R���󋵂���Ȃ�A�������̂ݑΏہB
	 * @param connection				�R�l�N�V����
	 * @param updateInfo				�X�V����R�������
	 * @param shinsaJokyo				�R����
	 * @param jigyoId					����ID
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateShinsainInfo(
			Connection connection,
			ShinsainInfo updateInfo,
			String shinsaJokyo,
			String jigyoId)
			throws DataAccessException, NoDataFoundException {

		//�R���󋵂���Ȃ�A�������̂ݑΏہB
		if(shinsaJokyo == null) {
			shinsaJokyo = ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET;
		}
//2006/11/2 �c�@�ǉ���������
        String jigyoKubun = updateInfo.getJigyoKubun();
        Set shinseishoSet = JigyoKubunFilter.Convert2ShinseishoJigyoKubun(jigyoKubun) ; //�R�����̎��Ƌ敪��Set
        if (shinseishoSet != null && !shinseishoSet.isEmpty()) {
            jigyoKubun = StringUtil.changeIterator2CSV(shinseishoSet.iterator(), true);
        }
//2006/11/2�@�c�@ �ǉ������܂�       

		String update
			= "UPDATE SHINSAKEKKA"+dbLink
				+ " SET"	
				+ " SHINSAIN_NO = ? "				//�R�����ԍ�
//2006/11/03 �c�@�폜��������                
//                + ", JIGYO_KUBUN = ? "              //���Ƌ敪
//2006/11/03�@�c�@�폜�����܂�                
				+ ", SHINSAIN_NAME_KANJI_SEI = ? "	//�R�������i�����|���j
				+ ", SHINSAIN_NAME_KANJI_MEI = ? "	//�R�������i�����|���j
				+ ", NAME_KANA_SEI = ? "			//�R�������i�t���K�i�|���j
				+ ", NAME_KANA_MEI = ? "			//�R�������i�t���K�i�|���j
				+ ", SHOZOKU_NAME = ? "				//�R���������@�֖�
				+ ", BUKYOKU_NAME = ? "				//�R�������ǖ�
				+ ", SHOKUSHU_NAME = ? "			//�R�����E��
				+ " WHERE SHINSAIN_NO = ?"
//2006/11/02�@�c�@�C����������                
//                + " AND JIGYO_KUBUN = ?"
                + " AND JIGYO_KUBUN IN("
                + jigyoKubun
                + ")";
//2006/11/02 �c�@�C�������܂�              
				;

		StringBuffer query = new StringBuffer(update);
		
		query.append(" AND SHINSA_JOKYO = '" + shinsaJokyo + "'");
		if(jigyoId != null && !jigyoId.equals("")) {
			query.append(" AND JIGYO_ID = '" + EscapeUtil.toSqlString(jigyoId) + "'");
		}
		query.append(" AND EXISTS")
			 .append(" (SELECT * FROM")
			 .append(" SHINSEIDATAKANRI"+dbLink+" A")
			 .append(", SHINSAKEKKA"+dbLink+" B")
			 .append(" WHERE A.DEL_FLG = 0")
			 .append(" AND A.SYSTEM_NO = B.SYSTEM_NO)")
			 ;
		
		PreparedStatement preparedStatement = null;
		try {
			//�X�V
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
//2006/11/03�@�c�@�폜��������            
//            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
//2006/11/03�@�c�@�폜�����܂�            
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());	
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
//2006/11/02 �c�@�폜��������            
//            DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
//2006/11/02�@�c�@�폜�����܂�            
			preparedStatement.executeUpdate();				//�X�V�����������Ȃ̂�
		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	//2004/05/25 �ǉ� ��������-------------------------------------------------------
	//���R�@ �V�X�e����t�ԍ��ǉ��̂���
	/**
	 * �S���R�����ʏ��̐R���������X�V����B
	 * ����ID����Ȃ�A�S�Ă̎��Ƃ��ΏہB
	 * �R���󋵂���Ȃ�A�������̂ݑΏہB
	 * @param connection				�R�l�N�V����
	 * @param updateInfo				�X�V����R�������
	 * @param shinsaJokyo				�R����
	 * @param jigyoId					����ID
     * @param systemNo                  �V�X�e����t�ԍ�
     * @param no                        ����
	 * @throws DataAccessException		�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void updateShinsainInfo(
			Connection connection,
			ShinsainInfo updateInfo,
			String shinsaJokyo,
			String jigyoId,
			String systemNo,
			int no)
			throws DataAccessException, NoDataFoundException {

		//�R���󋵂���Ȃ�A�������̂ݑΏہB
		if(shinsaJokyo == null) {
			shinsaJokyo = ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET;
		}

		String update
			= "UPDATE SHINSAKEKKA"+dbLink
				+ " SET"	
				+ " SHINSAIN_NO = ? "				//�R�����ԍ�
				+ ", JIGYO_KUBUN = ? "				//���Ƌ敪
				+ ", SHINSAIN_NAME_KANJI_SEI = ? "	//�R�������i�����|���j
				+ ", SHINSAIN_NAME_KANJI_MEI = ? "	//�R�������i�����|���j
				+ ", NAME_KANA_SEI = ? "			//�R�������i�t���K�i�|���j
				+ ", NAME_KANA_MEI = ? "			//�R�������i�t���K�i�|���j
				+ ", SHOZOKU_NAME = ? "				//�R���������@�֖�
				+ ", BUKYOKU_NAME = ? "				//�R�������ǖ�
				+ ", SHOKUSHU_NAME = ? "			//�R�����E��
				+ " WHERE SHINSAIN_NO = ?"
				+ " AND JIGYO_KUBUN = ?"
				+ " AND SYSTEM_NO = ?"
				;

		StringBuffer query = new StringBuffer(update);
		
		query.append(" AND SHINSA_JOKYO = '" + EscapeUtil.toSqlString(shinsaJokyo) + "'");
		if(jigyoId != null && !jigyoId.equals("")) {
			query.append(" AND JIGYO_ID = '" + EscapeUtil.toSqlString(jigyoId) + "'");
		}
		query.append(" AND EXISTS")
			 .append(" (SELECT * FROM")
			 .append(" SHINSEIDATAKANRI"+dbLink+" A")
			 .append(", SHINSAKEKKA"+dbLink+" B")
			 .append(" WHERE A.DEL_FLG = 0")
			 .append(" AND A.SYSTEM_NO = B.SYSTEM_NO)")
			 ;
		
		PreparedStatement preparedStatement = null;
		try {
			//�X�V
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanjiMei());	
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaSei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getNameKanaMei());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShozokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getBukyokuName());
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getShokushuName());
			DatabaseUtil.setParameter(preparedStatement,i++,"@00000"+no);
			DatabaseUtil.setParameter(preparedStatement,i++,updateInfo.getJigyoKubun());
			DatabaseUtil.setParameter(preparedStatement,i++,systemNo);
			preparedStatement.executeUpdate();				//�X�V�����������Ȃ̂�
		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	//�ǉ� �����܂�-------------------------------------------------------------------------

	/**
	 * �����]���iABC�j�A�����]���i�_���j��NULL�̐R�����ʏ��̐����擾����B
	 * @param connection				�R�l�N�V����
	 * @param shinsainNo
	 * @param jigyoKubun
	 * @param jigyoId
	 * @throws DataAccessException			�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public int countShinsaKekkaInfo(
		Connection connection,
		String shinsainNo,
		String jigyoKubun,
		String jigyoId)
		throws DataAccessException
	{

//8/20���݂̃e�[�u��
		String query =
			"SELECT COUNT(*) "
				+ " FROM SHINSAKEKKA"+dbLink+" A,"
				+ " (SELECT * FROM SHINSEIDATAKANRI"+dbLink
				+ "  WHERE "
				+ "   (JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "'"		//�\���󋵂�[10]
				+ "    OR "
				+ "    JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSA_KANRYO + "'"	//�\���󋵂�[11]
				+ "   )"
				+ "  AND"
				+ "   DEL_FLG = '0'"							//�폜����ĂȂ�����
				+ " ) B"
				+ " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
				+ " AND A.KEKKA_ABC IS NULL"	//�����]���iABC�j
				+ " AND A.KEKKA_TEN IS NULL"	//�����]���i�_���j
				//2005/11/15 ���Q�֌W�̎��A�u-�v���Z�b�g�����ׁA�s�v�Ƃ���@
				//+ " AND NVL(A.RIGAI,'0') = '0'"		//2005/11/14 ���Q�֌W�̏ꍇ���O	
				+ " AND A.SHINSAIN_NO = ?"
				+ " AND A.JIGYO_KUBUN = ?"
				+ " AND A.JIGYO_ID = ?"
				;
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			//�X�V
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainNo);
			DatabaseUtil.setParameter(preparedStatement,i++,jigyoKubun);
			DatabaseUtil.setParameter(preparedStatement,i++,jigyoId);
			DatabaseUtil.executeUpdate(preparedStatement);
			int count = 0;			
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}


	/**
	 * �R���������ō폜����Ă��Ȃ��S���R�����ʏ��̐����擾����B
	 * @param connection				�R�l�N�V����
	 * @param shinsainNo
	 * @param jigyoKubun
     * @return int ���R�[�h��
	 * @throws DataAccessException			�X�V���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException		�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public int countTantoShinsaKekkaInfo(
			Connection connection,
			String shinsainNo,
			String jigyoKubun)
			throws DataAccessException {

		String select =
			"SELECT COUNT(*) "
				+ " FROM SHINSAKEKKA"+dbLink+" A"
				+ ", SHINSEIDATAKANRI" + dbLink + " B"
				+ " WHERE A.SHINSAIN_NO = ?"
				+ " AND A.JIGYO_KUBUN = ?"
				;

		StringBuffer query = new StringBuffer(select);
		query.append(" AND A.SHINSA_JOKYO = '0'")				//�������̂�
			 .append(" AND A.SYSTEM_NO = B.SYSTEM_NO")
			 .append(" AND B.DEL_FLG = '0'");					//�폜����Ă��Ȃ����̂̂�
		
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;
		try {
			preparedStatement = connection.prepareStatement(query.toString());
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsainNo);
			DatabaseUtil.setParameter(preparedStatement,i++,jigyoKubun);
			int count = 0;			
			recordSet = preparedStatement.executeQuery();
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("�S���R�����ʏ��J�E���g���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

//2006-10-25 ���u�j  �ǉ� ��������
    /**
     * ���͏󋵂��X�V����B
     * @param connection �R�l�N�V����
     * @param shinsainNo
     * @param jigyoKubun
     * @param jigyoId
     * @param nyuryokuJokyo
     * @throws DataAccessException �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
    public void updateNyuryokuJokyo(Connection connection, String shinsainNo,
            String jigyoKubun, String jigyoId, String nyuryokuJokyo)
            throws DataAccessException, NoDataFoundException {
        
        String update = "UPDATE SHINSAKEKKA" + dbLink + " A" + " SET"
                + " A.NYURYOKU_JOKYO = ? " // ���͏�
        ;

        ArrayList list = new ArrayList();
        StringBuffer query = new StringBuffer(update);

        // �R�����ԍ�
        query.append(" WHERE A.SHINSAIN_NO = ?");
        list.add(shinsainNo);
        // ���Ƌ敪
        query.append(" AND A.JIGYO_KUBUN = ?");
        list.add(jigyoKubun);
        // ����ID
        query.append(" AND A.JIGYO_ID = ?");
        list.add(jigyoId);

        PreparedStatement preparedStatement = null;
        try {
            // �X�V
            preparedStatement = connection.prepareStatement(query.toString());
            int index = 1;
            // ���͏�
            DatabaseUtil.setParameter(preparedStatement, index++, nyuryokuJokyo); 
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                String value = (String) iterator.next();
                DatabaseUtil.setParameter(preparedStatement, index++, value);
            }
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new DataAccessException("�R�����ʏ��X�V���ɗ�O���������܂����B ", ex);
        } finally {
            DatabaseUtil.closeResource(null, preparedStatement);
        }
    }
// 2006-10-25 ���u�j  �ǉ� �����܂�

    /**
     * �R�����ʏ��̐R���󋵂��X�V����B
     * @param connection                �R�l�N�V����
     * @param shinsainNo
     * @param jigyoKubun
     * @param systemNo
     * @param jigyoId
     * @param shinsaJokyo
     * @throws DataAccessException      �X�V���ɗ�O�����������ꍇ
     * @throws NoDataFoundException �Ώۃf�[�^��������Ȃ��ꍇ
     */
	public void updateShinsaKekkaInfo(
		Connection connection,
		String shinsainNo,
		String jigyoKubun,
		String systemNo,
		String jigyoId,
		String shinsaJokyo
		)
		throws DataAccessException, NoDataFoundException
	{
		//2004/10/13�R�����g�A�E�g�i�f�[�^�����݂��Ȃ��Ă��X�V������Exception���������Ȃ����߁j
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
//		 int count = countShinsaKekkaInfo(connection, shinsainNo, jigyoKubun, systemNo, jigyoId, null);
//		 if(count == 0){
//			throw new NoDataFoundException(
//				"�R�����ʏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�R�����ԍ�'"
//					+ shinsainNo
//					+ "'"
//					+",���Ƌ敪''"
//					+ jigyoKubun
//					+ "'"					
//					+",�V�X�e���ԍ�''"
//					+ systemNo
//					+ "'"	
//					+",����ID''"
//					+ jigyoId
//					+ "'"					
//					);		 	
//		 }
		
		String update =
			"UPDATE SHINSAKEKKA"+dbLink+" A"
				+ " SET"	
				+ " A.SHINSA_JOKYO = ?"				//�R����
				;

		StringBuffer  query = new StringBuffer(update);
		query.append(" WHERE A.SYSTEM_NO IN");
		query.append(" (SELECT B.SYSTEM_NO FROM SHINSEIDATAKANRI"+dbLink+" B, SHINSAKEKKA"+dbLink+" C");
		query.append(" WHERE B.SYSTEM_NO=C.SYSTEM_NO");
		query.append(" AND B.DEL_FLG=0");
		query.append(" AND (B.JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "'");	//�\���󋵂�[10]
		query.append(" OR");
		query.append(" B.JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSA_KANRYO + "')");	//�\���󋵂�[11]
								
		ArrayList list = new ArrayList();
		//�V�X�e���ԍ�
		if(systemNo != null && systemNo.length() != 0){	
			query.append(" AND B.SYSTEM_NO= ?)");
			list.add(systemNo);
		}else{
			query.append(")");			
		}
		
		
//2005-01-26
//  ����null�������ꍇ�A�z��O�̃��R�[�h�ɑ΂��čX�V���Ă��܂����߁A
//  null�ł����������ɂ͒ǉ�����B�i����čX�V����邱�Ƃ�h���B�j
//		//�R�����ԍ�
//		if(shinsainNo != null && shinsainNo.length() != 0){	
//			query.append(" AND A.SHINSAIN_NO = ?");
//			list.add(shinsainNo);
//		}
//		//���Ƌ敪
//		if(jigyoKubun != null && jigyoKubun.length() != 0){
//			query.append(" AND A.JIGYO_KUBUN = ?");
//			list.add(jigyoKubun);
//		}
//		//����ID
//		if(jigyoId != null && jigyoId.length() != 0){
//			query.append(" AND A.JIGYO_ID = ?");
//			list.add(jigyoId);
//		}
		//�R�����ԍ�
		query.append(" AND A.SHINSAIN_NO = ?");
		list.add(shinsainNo);
		//���Ƌ敪
		query.append(" AND A.JIGYO_KUBUN = ?");
		list.add(jigyoKubun);
		//����ID
		query.append(" AND A.JIGYO_ID = ?");
		list.add(jigyoId);
		
		PreparedStatement preparedStatement = null;
		try {
			//�X�V
			preparedStatement = connection.prepareStatement(query.toString());
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement,index++,shinsaJokyo);		//�R����
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				String value = (String)iterator.next();
				DatabaseUtil.setParameter(preparedStatement, index++, value);
			}
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��X�V���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**	�R�����ʏ��̐����擾����B
     * @param connection �R�l�N�V����
     * @param shinsainNo �R�����ԍ�
     * @param jigyoKubun ���Ƌ敪
     * @param systemNo �V�X�e���ԍ�
     * @param jigyoId ���Ƃh�c
     * @param shinsaJokyo �R����
     * @param nyuryokuJokyo ���͏�
     * @return int ���R�[�h��
     * @throws DataAccessException
	 */
	public int countShinsaKekkaInfo(
				Connection connection, 
				String shinsainNo, 
				String jigyoKubun, 
				String systemNo, 
				String jigyoId,
                String shinsaJokyo,
                String nyuryokuJokyo)
						throws DataAccessException {
//8/17���݂̃e�[�u��
		String select =
			"SELECT COUNT(*)"					//�\���ԍ�
				+ " FROM SHINSAKEKKA"+dbLink+" A,"
				+ " (SELECT SYSTEM_NO FROM SHINSEIDATAKANRI"+dbLink
				+ " WHERE DEL_FLG=0"
				+ " AND (JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "'"	//�\���󋵂�[10]
				+ " OR"
				+ " JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSA_KANRYO + "')"	//�\���󋵂�[11]
				;
	
		StringBuffer  query = new StringBuffer(select);

		ArrayList list = new ArrayList();
		if(systemNo != null && systemNo.length() != 0){	
			query.append(" AND SYSTEM_NO= ?");					//�V�X�e���ԍ�
			list.add(systemNo);
		}
		query.append(") B");
		query.append(" WHERE A.SYSTEM_NO=B.SYSTEM_NO");	
		if(shinsainNo != null && shinsainNo.length() != 0){	
			query.append(" AND A.SHINSAIN_NO = ?");				//�R�����ԍ�
			list.add(shinsainNo);
		}			
		if(jigyoKubun != null && jigyoKubun.length() != 0){
//			if ("1".equals(jigyoKubun)){
//			query.append(" AND A.JIGYO_KUBUN = ?");				//���Ƌ敪
//			}else{
//				query.append(" AND (A.JIGYO_KUBUN = ? or A.JIGYO_KUBUN = 6)");//���Ƌ敪
//			}
//			list.add(jigyoKubun);
            
//2006/04/25 �ǉ���������
            Set shinsaiSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(jigyoKubun) ; //�R�����̎��Ƌ敪��Set
            String jigyoKubunTemp = (String)shinsaiSet.iterator().next();
            
            Set shinseiSet = JigyoKubunFilter.Convert2ShinseishoJigyoKubun(jigyoKubunTemp) ; //�\���̎��Ƌ敪��Set
            //CSV���擾
            String shinseiJigyoKubun =  StringUtil.changeIterator2CSV(shinseiSet.iterator(),true);
            
            select = select 
                + "   AND A.JIGYO_KUBUN IN ("
                + shinseiJigyoKubun
                + "   )";
//  �c�@�ǉ������܂�      
		}
		if(jigyoId != null && jigyoId.length() != 0){
			query.append(" AND A.JIGYO_ID = ?");				//����ID
			list.add(jigyoId);
		}
		if(shinsaJokyo != null && shinsaJokyo.length() != 0){	
            query.append(" AND A.SHINSA_JOKYO = ?");             //�R����
			list.add(shinsaJokyo);
		}
//2006-10-27 ���Q �ǉ���������
        if(nyuryokuJokyo != null && nyuryokuJokyo.length() != 0){   
            query.append(" AND NVL(A.NYURYOKU_JOKYO, 0) = ?");             //���͏�
            list.add(nyuryokuJokyo);
        }
//2006-10-27 ���Q �ǉ� �����܂�        
		if (log.isDebugEnabled()){
			log.debug(query.toString());
		}
				
		PreparedStatement preparedStatement = null;
		ResultSet recordSet = null;

		try {
//			ShinsaKekkaInfo result = new ShinsaKekkaInfo();
			preparedStatement = connection.prepareStatement(query.toString());
			int index = 1;
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				String value = (String)iterator.next();
				DatabaseUtil.setParameter(preparedStatement, index++, value);
			}
			recordSet = preparedStatement.executeQuery();

			int count = 0;
			if (recordSet.next()) {
				count = recordSet.getInt(1);
			} 
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(recordSet, preparedStatement);
		}
	}

	/**
	 * �R���S�����\���ꗗ��Ԃ��B�����]���i�_���j�͌��������Ƃ��Ȃ��B�i�w�n/�����p�j
     * @param connection �R�l�N�V����
     * @param shinsainNo �R�����ԍ�
     * @param jigyoKubun ���Ƌ敪
     * @param jigyoId ���Ƃh�c
     * @param searchInfo ��������
     * @return Page
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Page selectShinsaKekkaTantoList(
				Connection connection,
				String shinsainNo,
				String jigyoKubun,
				String jigyoId,	
				SearchInfo searchInfo)
			throws DataAccessException, NoDataFoundException, ApplicationException
	{
        return selectShinsaKekkaTantoList(connection, shinsainNo, jigyoKubun, jigyoId, null, 
                null, //2006/10/27�@�c�@�ǉ��@���̃��b�\�h�͌Ăяo���Ȃ�����Anull��ǉ����܂���
                searchInfo);
	}
	
	/**
	 * �R���S�����\���ꗗ��Ԃ��B
	 * �폜�ς݂̐\�����͏��O�B
	 * �����]���i�_���j���u-1�v�̏ꍇ�́A�����]���i�_���j��'IS NULL'�����������Ƃ���B
     * @param connection �R�l�N�V����
     * @param shinsainNo �R�����ԍ�
     * @param jigyoKubun ���Ƌ敪
     * @param jigyoId ���Ƃh�c
     * @param kekkaTen �]���i�_���j
     * @param rigai ���Q�֌W
     * @param searchInfo ��������
	 * @return
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public Page selectShinsaKekkaTantoList(
				Connection connection,
				String shinsainNo,
				String jigyoKubun,
				String jigyoId,
				String kekkaTen,	
                String rigai,
				SearchInfo searchInfo)
			throws DataAccessException, NoDataFoundException, ApplicationException
	{
        String select = "SELECT "
			+ " A.SYSTEM_NO,"							//�V�X�e����t�ԍ�
			+ " A.UKETUKE_NO,"							//�\���ԍ�
			+ " A.SHINSAIN_NO,"							//�R�����ԍ�
			+ " A.JIGYO_ID,"							//����ID
			+ " SUBSTR(A.JIGYO_ID,3,5) JIGYO_CD,"		//���ƃR�[�h
			+ " A.JIGYO_NAME,"							//���Ɩ�		
			+ " A.BUNKASAIMOKU_CD,"						//���ȍזڃR�[�h
			+ " A.KEKKA_ABC,"							//�����]���iABC�j
			+ " A.KEKKA_TEN,"							//�����]���i�_���j
			+ " A.KENKYUNAIYO,"							//�������e
			+ " A.KENKYUKEIKAKU,"						//�����v��
			+ " A.TEKISETSU_KAIGAI,"					//�K�ؐ�-�C�O
			+ " A.TEKISETSU_KENKYU1,"					//�K�ؐ�-����(1)
			+ " A.TEKISETSU,"							//�K�ؐ�
			+ " A.DATO,"								//�Ó���
			+ " A.COMMENTS,"							//�R�����g
			//2005.11.03 kainuma
//2006/10/30�@�c�@�C����������            
//            + " A.RIGAI,"                               //���Q�֌W
            + " NVL(A.RIGAI,'0') RIGAI,"                //���Q�֌W
//2006/10/30�@�c�@�C�������܂�            
			+ " A.DAIRI,"								//�㗝�t���O
			+ " A.TEKISETSU_WAKATES,"					//���(S)�Ƃ��Ă̑Ó���	//2007/5/8 �ǉ�
			+ " A.JUYOSEI,"								//�w�p�I�d�v���E�Ó���
			+ " A.DOKUSOSEI,"							//�Ƒn���E�v�V��
			+ " A.HAKYUKOKA,"							//�g�y���ʁE���Ր�
			+ " A.SUIKONORYOKU,"						//���s�\�́E���̓K�ؐ�
			+ " A.JINKEN,"								//�l���̕ی�
			+ " A.BUNTANKIN,"							//���S���z��
			+ " A.OTHER_COMMENT,"						//���̑��̃R�����g
			//
			+ " A.KOSHIN_DATE,"							//����U��X�V��		2005/11/08 �ǉ�
			+ " A.SHINSA_JOKYO,"						//�R����
//2006/10/27 �c�@�ǉ���������
            + " NVL(A.NYURYOKU_JOKYO,'0') NYURYOKU_JOKYO,"//���͏�
//2006/10/27�@�c�@�ǉ������܂�            
			+ " B.SHINSEISHA_ID,"						//�\����ID
			+ " B.NAME_KANJI_SEI,"						//�\���Ҏ����i������-���j
			+ " B.NAME_KANJI_MEI,"						//�\���Ҏ����i������-���j
			+ " B.SHOZOKU_CD,"							//�����@�փR�[�h
			+ " B.SHOZOKU_NAME_RYAKU,"					//�����@�֖��i���́j
			+ " B.BUKYOKU_NAME_RYAKU,"					//���ǖ��i���́j
			+ " B.SHOKUSHU_NAME_RYAKU,"					//�E���i���́j
			+ " B.KADAI_NAME_KANJI,"					//�����ۑ薼(�a���j
			+ " B.SAIMOKU_NAME,"						//�זږ�
			+ " DECODE"
			+ " ("
			+ "  NVL(B.SUISENSHO_PATH,'null') "
			+ "  ,'null','FALSE'"						//���E���p�X��NULL�̂Ƃ�
			+ "  ,      'TRUE'"							//���E���p�X��NULL�ȊO�̂Ƃ�
			+ " ) SUISENSHO_FLG, "						//���E���o�^�t���O
			+ " B.BUNKATSU_NO,"							//�����ԍ�	
			+ " B.BUNTANKIN_FLG,"						//���S���̗L��
			+ " B.KAIGAIBUNYA_CD,"						//�C�O����R�[�h
			//add start ly 2006/04/10
			+ " B.KAIGAIBUNYA_NAME,"                    //�C�O���얼
			+ " B.BUNYA_NAME,"                          //���얼
			//add end ly 2006/04/10
			+ " B.KAIGAIBUNYA_NAME_RYAKU,"				//�C�O���얼�i���́j
			+ " B.SHINSEI_FLG_NO,"						//�����v��ŏI�N�x�O�N�x�̉���			
			+ " B.JOKYO_ID,"							//�\����ID
			+ " C.SHINSAKIGEN,"							//�R������
			+ " C.NENDO,"								//�N�x
			+ " C.KAISU,"								//��
			+ " DECODE"
			+ " ("
			+ "  SIGN( TO_DATE(TO_CHAR(C.SHINSAKIGEN, 'YYYY/MM/DD'),'YYYY/MM/DD')  - TO_DATE(TO_CHAR(SYSDATE, 'YYYY/MM/DD'),'YYYY/MM/DD') )"
			+ "  ,0 , 'TRUE'"							//���ݎ����Ɠ����ꍇ
			+ "  ,1 , 'TRUE'"							//���ݎ����̕����R���������O
			+ "  ,-1, 'FALSE'"							//���ݎ����̕����R����������
			+ " ) SHINSAKIGEN_FLAG"						//�R���������B�t���O
			+ " FROM"
			+ " (SELECT * FROM SHINSAKEKKA"+dbLink
			+ "  WHERE "
			+ "  SHINSAIN_NO = ?"				//���Y�R�����ԍ�
			;
//		if ("1".equals(jigyoKubun)){
//			select = select	+ "  AND JIGYO_KUBUN = ?";			//���Y���Ƌ敪
//		}else{
//			select = select	+ "  AND (JIGYO_KUBUN = ? or JIGYO_KUBUN = 6)";			//���Y���Ƌ敪
//		}
//2006/04/25 �ǉ���������
        Set shinsaiSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(jigyoKubun) ; //�R�����̎��Ƌ敪��Set
        String jigyoKubunTemp = (String)shinsaiSet.iterator().next();
        
        Set shinseiSet = JigyoKubunFilter.Convert2ShinseishoJigyoKubun(jigyoKubunTemp) ; //�\���̎��Ƌ敪��Set
        //CSV���擾
        String shinseiJigyoKubun =  StringUtil.changeIterator2CSV(shinseiSet.iterator(),true);
        
        select = select 
            + "   AND JIGYO_KUBUN IN ("
            + shinseiJigyoKubun
            + "   )";
//�c�@�ǉ������܂�      
		select = select 
			+ "  AND JIGYO_ID = ?"				//���Y����ID				
			+ " ) A,"
			+ " (SELECT * FROM SHINSEIDATAKANRI"+dbLink
			+ "   WHERE "
			+ "    (JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "'"		//�\���󋵂�[10]
			+ "     OR "
			+ "     JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSA_KANRYO + "'"	//�\���󋵂�[11]
			+ "    )"
			+ "    AND DEL_FLG = '0'"		//�폜����ĂȂ�����				
			+ " ) B,"
//			+ " (SELECT * FROM JIGYOKANRI"+dbLink
//			+ "  WHERE"
//			+ "   JIGYO_ID = ?"				//���Y����ID
//			+ " ) C"
			+ " JIGYOKANRI" +dbLink 
			+ " C"
			+ " WHERE"
			+ " A.SYSTEM_NO = B.SYSTEM_NO"
			+ " AND"
			+ " A.JIGYO_ID = C.JIGYO_ID"			
			;
			
		StringBuffer query = new StringBuffer(select);
		
		//�p�����[�^�����Z�b�g
		ArrayList list = new ArrayList();
		list.add(shinsainNo);	//�R�����ԍ�
//		list.add(jigyoKubun);	//���Ƌ敪
		list.add(jigyoId);		//����ID
		
		//�����]���i�_���j
		//null�i�w�n�E�����Ɗ�Ղ̏����\���j�̏ꍇ�́A���������ɉ����Ȃ��B
		if(kekkaTen != null && kekkaTen.length() != 0){	
			if(kekkaTen.equals("-1")){
				//�u-1�F�����́v��I�����ꂽ�ꍇ�́ANULL�Ō���
				query.append(" AND A.KEKKA_TEN IS NULL");						
			}else{
				//�R�����ʂ��u5�`-�v�̏ꍇ
				query.append(" AND A.KEKKA_TEN = ?");
				list.add(kekkaTen);		
			}
		}
      
//2006/10/27 �c�@�ǉ���������
        //���Q�֌W
        //null�̏ꍇ�́A���������ɉ����Ȃ��B
        if(rigai != null && rigai.length() != 0){ 
            if(rigai.equals("0")){
                //�u0�F���Q�֌W�ȊO�̉ۑ� �v��I�����ꂽ�ꍇ�́ANULL�Ō���
                query.append(" AND NVL(A.RIGAI,'0') = 0");                       
            }else{
                query.append(" AND A.RIGAI = ?");
                list.add(rigai);     
            }
        }
//2006/10/27�@�c�@�ǉ������܂�        

		//�\�[�g���i�V�X�e����t�ԍ��̏����j
//		query.append(" ORDER BY A.SYSTEM_NO");
		//�\�[�g���̕ύX
		query.append(" ORDER BY");
		//��Ղ̏ꍇ�A�זڔԍ��Ń\�[�g����B
		
		//2006.07.03 iso ��X�^�E��Ց����̃\�[�g���ɍזڔԍ������f����Ȃ��o�O���C��
		//�w�n�E�����ȊO�́A������������悤�C�������B
//		if(jigyoKubun != null && jigyoKubun.equals("4")) {
		if(jigyoKubun != null
				&& !jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)
				&& !jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO)
				&& !jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_TOKUSUI)) {
			//2007/5/8 �זڃR�[�h�@���@����A�EB�@���@�@�ցE�����ԍ��i�@�փR�[�h�@���@�����ԍ��j�̏��Ń\�[�g����B
			query.append(" A.BUNKASAIMOKU_CD,");
			query.append(" B.BUNKATSU_NO,");
		}
		query.append(" A.UKETUKE_NO");
		
		//for debug
		if(log.isDebugEnabled()){
			log.debug("query:" + query.toString());
		}
		
		// �y�[�W�擾		
		return SelectUtil.selectPageInfo(connection, searchInfo, query.toString(), (String[])list.toArray(new String[list.size()]));
	}
	
	/**	�����]���i�_���j���Ƃ̌������擾����B
     * @param connection �R�l�N�V����
     * @param shinsainNo �R�����ԍ�
     * @param jigyoKubun ���Ƌ敪
     * @param jigyoId ���Ƃh�c
     * @return List
     * @throws DataAccessException
     * @throws NoDataFoundException
	 */
	public List getSogoHyokaList(
			Connection connection, 
			String shinsainNo, 
			String jigyoKubun,
			String jigyoId)
			throws NoDataFoundException, DataAccessException {

		String select =
			"SELECT "
				+ " A.KEKKA_TEN"					//�����]���i�_���j
				+ " ,COUNT(*) COUNT"				//����
				+ " FROM SHINSAKEKKA"+dbLink+" A,"
				+ " (SELECT * FROM SHINSEIDATAKANRI"+dbLink
				+ "   WHERE "
				+ "    (JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSATYU + "'"		//�\���󋵂�[10]
				+ "     OR "
				+ "     JOKYO_ID = '" + StatusCode.STATUS_1st_SHINSA_KANRYO + "'"	//�\���󋵂�[11]
				+ "    )"
				+ "    AND DEL_FLG = '0'"		//�폜����ĂȂ�����				
				+ " ) B"
				+ " WHERE A.SYSTEM_NO = B.SYSTEM_NO"
				+ "   AND A.SHINSAIN_NO = ?"
				;

		//+ "   AND A.JIGYO_KUBUN = ?"
//		if ("1".equals(jigyoKubun)){
//			select = select	+ "   AND A.JIGYO_KUBUN = ?";
//		}else{
//			select = select	+ "   AND (A.JIGYO_KUBUN = ? or A.JIGYO_KUBUN = 6)";
//		}
        
//2006/04/25 �ǉ���������
        Set shinsaiSet = JigyoKubunFilter.Convert2ShinsainJigyoKubun(jigyoKubun) ; //�R�����̎��Ƌ敪��Set
        String jigyoKubunTemp = (String)shinsaiSet.iterator().next();
        
        Set shinseiSet = JigyoKubunFilter.Convert2ShinseishoJigyoKubun(jigyoKubunTemp) ; //�\���̎��Ƌ敪��Set
        //CSV���擾
        String shinseiJigyoKubun =  StringUtil.changeIterator2CSV(shinseiSet.iterator(),true);
        
        select = select 
            + "   AND A.JIGYO_KUBUN IN ("
            + shinseiJigyoKubun
            + "   )";
//�c�@�ǉ������܂�         

		select = select	+ "   AND A.JIGYO_ID = ?"
				+ " GROUP BY A.KEKKA_TEN"
				;

		StringBuffer  query = new StringBuffer(select);
		
//2005/11/15 ���Q�֌W�̏ꍇ�A�����s���̃o�O���C������� by xiang
		//�\�[�g���i�����]���i�_���j�̍~���j
		//query.append(" ORDER BY NVL(REPLACE(A.KEKKA_TEN, '-', '0'), '-1') DESC");					
		query.append(" ORDER BY NVL(A.KEKKA_TEN,' ') DESC");

		//for debug
		if(log.isDebugEnabled()){
			log.debug("query:" + query.toString());
		}
		
		// ���X�g�擾		
		return SelectUtil.select(connection, query.toString(), new String[]{shinsainNo,jigyoId});
	}

	/**
	 * �R�����ʏ����폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param deletePk				�폜����R�����ʏ���L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 * @throws NoDataFoundException	�Ώۃf�[�^��������Ȃ��ꍇ
	 */
	public void deleteShinsaKekkaInfo(
		Connection connection,
		ShinsaKekkaPk deletePk)
		throws DataAccessException, NoDataFoundException
	{
		//�����i�Ώۃf�[�^�����݂��Ȃ������ꍇ�͗�O�����j
		selectShinsaKekkaInfo(connection, deletePk);

		String query =
			"DELETE FROM SHINSAKEKKA"+dbLink
				+ " WHERE"
				+ "  SYSTEM_NO = ?"
				+ " AND"
				+ "  SHINSAIN_NO = ?"
				+ " AND"
				+ "  JIGYO_KUBUN = ?"
				;

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, deletePk.getSystemNo());		//�V�X�e����t�ԍ�
			DatabaseUtil.setParameter(preparedStatement, i++, deletePk.getShinsainNo());	//�R�����ԍ�
			DatabaseUtil.setParameter(preparedStatement, i++, deletePk.getJigyoKubun());	//���Ƌ敪
			DatabaseUtil.executeUpdate(preparedStatement);

		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * �R�����ʏ����폜����B(�����폜) 
	 * @param connection			�R�l�N�V����
	 * @param deletePk				�폜����\������L�[���
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 */
	public void deleteShinsaKekkaInfo(
		Connection connection,
		ShinseiDataPk deletePk)
        throws DataAccessException
	{

		String query =
			"DELETE FROM SHINSAKEKKA"+dbLink
				+ " WHERE"
				+ "  SYSTEM_NO = ?"
				;

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, deletePk.getSystemNo());		//�V�X�e����t�ԍ�
			preparedStatement.executeUpdate();		//0���`�������Ԃ�

		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}

	/**
	 * �R�����ʏ����폜����B(�����폜)
	 * ����ID�ɕR�Â��Y�t����S�č폜����B
	 * �Y���f�[�^�����݂��Ȃ������ꍇ�͉����������Ȃ��B  
	 * @param connection			�R�l�N�V����
	 * @param jigyoId				���������i����ID�j
	 * @throws DataAccessException	�폜���ɗ�O�����������ꍇ
	 */
	public void deleteShinsaKekkaInfo(
		Connection connection,
		String jigyoId)
		throws DataAccessException
	{
		String query =
			"DELETE FROM SHINSAKEKKA"+dbLink
				+ " WHERE"
				+ " JIGYO_ID = ?";

		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement, i++, jigyoId);		//���������i����ID�j
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��폜���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}	
	
	/**
	 * ���[�J���ɑ��݂���Y�����R�[�h�̓��e��DBLink��̃e�[�u���ɑ}������B
	 * DBLink��ɓ������R�[�h������ꍇ�́A�\�ߍ폜���Ă������ƁB
	 * DBLink���ݒ肳��Ă��Ȃ��ꍇ�̓G���[�ƂȂ�B
	 * @param connection
	 * @param jigyoId
	 * @throws DataAccessException
	 */
	public void copy2HokanDB(
		Connection connection,
		String     jigyoId)
		throws DataAccessException
	{
		//DBLink�����Z�b�g����Ă��Ȃ��ꍇ
		if(dbLink == null || dbLink.length() == 0){
			throw new DataAccessException("DB�����N�����ݒ肳��Ă��܂���BDBLink="+dbLink);
		}
		
		String query =
			"INSERT INTO SHINSAKEKKA"+dbLink
				+ " SELECT * FROM SHINSAKEKKA WHERE JIGYO_ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			//�o�^
			preparedStatement = connection.prepareStatement(query);
			int index = 1;
			DatabaseUtil.setParameter(preparedStatement, index++, jigyoId);		//���������i����ID�j
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��ۊǒ��ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
	}
	
	/**
	 * ���R���̐\���������R�������i�R����No�A���[���A�h���X�j��
	 * ���Ə��i����ID�A�N�x�A���Ɩ��j��Ԃ��B
	 * �������Ɏw�肵�����t���R�������i�����̂݁j�ł��鎖�Ƃ݂̂���������B
	 * @param connection
	 * @param shinsaKigen
	 * @return
	 * @throws IllegalArgumentException  ��������null�̏ꍇ
	 * @throws DataAccessException
	 * @throws NoDataFoundException
	 */
	public List selectShinsainWithNonExamined(
		Connection connection,
		Date       shinsaKigen)
		throws IllegalArgumentException, DataAccessException, NoDataFoundException
	{	
		if(shinsaKigen == null){
			throw new IllegalArgumentException();
		}
		
		String query =
		"SELECT DISTINCT"
			+ "  S.SHINSAIN_NO,"
			+ "  J.JIGYO_ID,"
			+ "  J.NENDO,"
			+ "  J.JIGYO_NAME,"
			+ "  M.SOFU_ZIPEMAIL"
			+ " FROM"
			+ "  (SELECT"
			+ "    SHINSAIN_NO,"
			+ "    JIGYO_ID"
			+ "   FROM"
			+ "    SHINSAKEKKA"+dbLink
			+ "   WHERE"
			+ "    SHINSA_JOKYO = '0' OR SHINSA_JOKYO IS NULL) S,"	//�R���������̂���
			+ "  (SELECT"
			+ "    JIGYO_ID,"
			+ "    NENDO,"
			+ "    JIGYO_NAME"
			+ "   FROM"
			+ "    JIGYOKANRI"+dbLink
			+ "   WHERE"
			+ "    TO_CHAR(SHINSAKIGEN,'YYYY/MM/DD') = ?) J,"
			+ "  MASTER_SHINSAIN M"
			+ " WHERE"
			+ "  S.JIGYO_ID=J.JIGYO_ID"
			+ " AND"
			+ "  S.SHINSAIN_NO=M.SHINSAIN_NO"
			+ " ORDER BY"
			+ "  S.SHINSAIN_NO, J.JIGYO_ID"
			;
		
		//for debug
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}
		
		//���X�g�擾
		return SelectUtil.select(
					connection, 
					query, 
					new String[]{new SimpleDateFormat("yyyy/MM/dd").format(shinsaKigen)}
				);				

	}
	
	/**
	 * ���Y�R�������S�����铖�Y����ID�̐R�����ʂ��N���A����B
	 * �������A[�R��������]�̂��̂����B
	 * @param connection
	 * @param shinsaJokyoInfo
	 * @return �X�V���R�[�h��
	 * @throws DataAccessException
	 */
	public int clearShinsaKekka(
		Connection connection,
		ShinsaJokyoInfo shinsaJokyoInfo)
		throws DataAccessException
	{
		String query
			= "UPDATE SHINSAKEKKA"+dbLink
				+ " SET"	
				+ "  KEKKA_ABC = NULL "					//�����]���iABC�j
				+ " ,KEKKA_TEN = NULL "					//�����]���i�_���j
				+ " ,COMMENT1 = NULL "					//�R�����g1
				+ " ,COMMENT2 = NULL "					//�R�����g2
				+ " ,COMMENT3 = NULL "					//�R�����g3
				+ " ,COMMENT4 = NULL "					//�R�����g4
				+ " ,COMMENT5 = NULL "					//�R�����g5
				+ " ,COMMENT6 = NULL "					//�R�����g6
				+ " ,KENKYUNAIYO = NULL "				//�������e
				+ " ,KENKYUKEIKAKU = NULL "				//�����v��
				+ " ,TEKISETSU_KAIGAI = NULL "			//�K�ؐ�-�C�O
				+ " ,TEKISETSU_KENKYU1 = NULL "			//�K�ؐ�-�����i1�j
				+ " ,TEKISETSU = NULL "					//�K�ؐ�
				+ " ,DATO = NULL "						//�Ó���
				+ " ,SHINSEISHA = NULL "				//������\��
				+ " ,KENKYUBUNTANSHA = NULL "			//�������S��
				+ " ,HITOGENOMU = NULL "				//�q�g�Q�m��
				+ " ,TOKUTEI = NULL "					//������
				+ " ,HITOES = NULL "					//�q�gES�זE
				+ " ,KUMIKAE = NULL "					//��`�q�g��������
				+ " ,CHIRYO = NULL "					//��`�q���×Տ�����
				+ " ,EKIGAKU = NULL "					//�u�w����
				+ " ,COMMENTS = NULL "					//�R�����g
				//2005.11.03 kainuma
				+ " ,RIGAI = NULL"						//���Q�֌W
				+ " ,DAIRI = NULL"						//�㗝�t���O
				+ " ,TEKISETSU_WAKATES = NULL"			//���(S)�Ƃ��Ă̑Ó���	//2007/5/8 �ǉ�
				+ " ,JUYOSEI = NULL"					//�w�p�I�d�v���E�Ó���
				+ " ,DOKUSOSEI = NULL"					//�Ƒn���E�v�V��
				+ " ,HAKYUKOKA = NULL"					//�g�y���ʁE���Ր�
				+ " ,SUIKONORYOKU = NULL"				//���s�\�́E���̓K�ؐ�
				+ " ,JINKEN = NULL"						//�l���̕ی�
				+ " ,BUNTANKIN = NULL"					//���S���z��
				+ " ,OTHER_COMMENT = NULL"				//���̑��̃R�����g
				//
				+ " ,KOSHIN_DATE = NULL"				//����U��X�V��		2005/11/08 �ǉ�
				+ " ,TENPU_PATH = NULL "				//�Y�t�t�@�C���i�[�p�X
                //2006/10/23 ���u�j �ǉ� ��������
                     /** ���͏� */
                + " ,NYURYOKU_JOKYO = '0' "     
                //2006/10/23 ���u�j �ǉ� �����܂�
				+ " WHERE "
				+ "   SHINSAIN_NO = ?"
				+ " AND "
				+ "   JIGYO_ID = ?"
				+ " AND "
				+ "   SHINSA_JOKYO = '" + ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET + "'"
				;
		
		PreparedStatement preparedStatement = null;
		try {
			//�X�V
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsaJokyoInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,shinsaJokyoInfo.getJigyoId());
			
			return preparedStatement.executeUpdate();	
			
		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��X�V���i�N���A���j�ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(null, preparedStatement);
		}
		
	}
	
	

	/**
	 * ���Y�R�������S�����铖�Y����ID�̐R�����ʏ����擾����B
	 * �������A[�R��������]�̂��̂����B
	 * @param connection
	 * @param shinsaJokyoInfo
	 * @return �R�����ʏ��
	 * @throws DataAccessException
     * @throws NoDataFoundException
	 */
	public ShinsaKekkaInfo[] selectShinsaKekkaInfo(
			Connection connection,
			ShinsaJokyoInfo shinsaJokyoInfo)
			throws DataAccessException, NoDataFoundException{
		
		String query =
			"SELECT "
//�e�[�u�����͒P��selectShinsaKekkaInfo�ɍ��킹��
				+ " A.SYSTEM_NO"				//�V�X�e����t�ԍ�
				+ ",A.UKETUKE_NO"				//�\���ԍ�
				+ ",A.SHINSAIN_NO"				//�R�����ԍ�
				+ ",A.JIGYO_KUBUN"				//���Ƌ敪
				+ ",A.SEQ_NO"					//�V�[�P���X�ԍ�
				+ ",A.SHINSA_KUBUN"				//�R���敪
				+ ",A.SHINSAIN_NAME_KANJI_SEI"	//�R�������i�����|���j
				+ ",A.SHINSAIN_NAME_KANJI_MEI"	//�R�������i�����|���j
				+ ",A.NAME_KANA_SEI"			//�R�������i�t���K�i�|���j
				+ ",A.NAME_KANA_MEI"			//�R�������i�t���K�i�|���j
				+ ",A.SHOZOKU_NAME"				//�R���������@�֖�
				+ ",A.BUKYOKU_NAME"				//�R�������ǖ�
				+ ",A.SHOKUSHU_NAME"			//�R�����E��
				+ ",A.JIGYO_ID"					//����ID
				+ ",A.JIGYO_NAME"				//���Ɩ�
				+ ",A.BUNKASAIMOKU_CD"			//�זڔԍ�
				+ ",A.EDA_NO"					//�}��				
				+ ",A.CHECKDIGIT"				//�`�F�b�N�f�W�b�g
				+ ",A.KEKKA_ABC"				//�����]���iABC�j
				+ ",A.KEKKA_TEN"				//�����]���i�_���j
				+ ",A.COMMENT1"					//�R�����g1			
				+ ",A.COMMENT2"					//�R�����g2
				+ ",A.COMMENT3"					//�R�����g3
				+ ",A.COMMENT4"					//�R�����g4
				+ ",A.COMMENT5"					//�R�����g5
				+ ",A.COMMENT6"					//�R�����g6
				+ ",A.KENKYUNAIYO"				//�������e
				+ ",A.KENKYUKEIKAKU"			//�����v��		
				+ ",A.TEKISETSU_KAIGAI"			//�K�ؐ�-�C�O
				+ ",A.TEKISETSU_KENKYU1"		//�K�ؐ�-�����i1�j
				+ ",A.TEKISETSU"				//�K�ؐ�			
				+ ",A.DATO"						//�Ó���
				+ ",A.SHINSEISHA"				//������\��
				+ ",A.KENKYUBUNTANSHA"			//�������S��
				+ ",A.HITOGENOMU"				//�q�g�Q�m��
				+ ",A.TOKUTEI"					//������
				+ ",A.HITOES"					//�q�gES�זE
				+ ",A.KUMIKAE"					//��`�q�g��������
				+ ",A.CHIRYO"					//��`�q���×Տ�����			
				+ ",A.EKIGAKU"					//�u�w����
				+ ",A.COMMENTS"					//�R�����g
				//2005.10.25 kainuma	
			  	+ ",A.RIGAI"					//���Q�֌W
			  	+ ",A.DAIRI"					//�㗝�t���O
				+ ",A.TEKISETSU_WAKATES"		//���(S)�Ƃ��Ă̑Ó���	//2007/5/8 �ǉ�
			  	+ ",A.JUYOSEI"					//�w�p�I�d�v���E�Ó���
			  	+ ",A.DOKUSOSEI"				//�Ƒn���E�v�V��
			  	+ ",A.HAKYUKOKA"				//�g�y���ʁE���Ր�
			  	+ ",A.SUIKONORYOKU"				//���s�\�́E���̓K�ؐ�
			  	+ ",A.JINKEN"					//�l���̕ی�E�@�ߓ��̏���
			  	+ ",A.BUNTANKIN"				//���S���z��			
			  	+ ",A.OTHER_COMMENT"			//���̑��R�����g
			  	//2005.10.25 kainuma
			  	+ ",A.KOSHIN_DATE"				//����U��X�V��		2005/11/08 �ǉ�
				+ ",A.TENPU_PATH"				//�Y�t�t�@�C���i�[�p�X			
				+ ",DECODE"
				+ " ("
				+ "  NVL(A.TENPU_PATH,'null') "
				+ "  ,'null','FALSE'"			//�Y�t�t�@�C���i�[�p�X��NULL�̂Ƃ�
				+ "  ,      'TRUE'"				//�Y�t�t�@�C���i�[�p�X��NULL�ȊO�̂Ƃ�
				+ " ) TENPU_FLG"				//�Y�t�t�@�C���i�[�t���O
				+ ",A.SHINSA_JOKYO"				//�R����
				+ ",A.BIKO"						//���l
				+ " FROM SHINSAKEKKA"+dbLink+" A"
				+ " WHERE"
				+ " SHINSAIN_NO = ?"
				+ " AND JIGYO_ID = ?"
				+ " AND SHINSA_JOKYO = '" + ShinsaKekkaMaintenance.SHINSAJOKYO_COMPLETE_YET + "'"
				;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ShinsaKekkaInfo[] shinseiKekkaInfo = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			DatabaseUtil.setParameter(preparedStatement,i++,shinsaJokyoInfo.getShinsainNo());
			DatabaseUtil.setParameter(preparedStatement,i++,shinsaJokyoInfo.getJigyoId());
			resultSet = preparedStatement.executeQuery();

			List resultList = new ArrayList();
			while(resultSet.next()){
				ShinsaKekkaInfo result = new ShinsaKekkaInfo();
				result.setSystemNo(resultSet.getString("SYSTEM_NO"));
				result.setUketukeNo(resultSet.getString("UKETUKE_NO"));
				result.setShinsainNo(resultSet.getString("SHINSAIN_NO"));		
				result.setJigyoKubun(resultSet.getString("JIGYO_KUBUN"));		
				result.setSeqNo(resultSet.getString("SEQ_NO"));
				result.setShinsaKubun(resultSet.getString("SHINSA_KUBUN"));
				result.setShinsainNameKanjiSei(resultSet.getString("SHINSAIN_NAME_KANJI_SEI"));
				result.setShinsainNameKanjiMei(resultSet.getString("SHINSAIN_NAME_KANJI_MEI"));
				result.setNameKanaSei(resultSet.getString("NAME_KANA_SEI"));
				result.setNameKanaMei(resultSet.getString("NAME_KANA_MEI"));
				result.setShozokuName(resultSet.getString("SHOZOKU_NAME"));
				result.setBukyokuName(resultSet.getString("BUKYOKU_NAME"));
				result.setShokushuName(resultSet.getString("SHOKUSHU_NAME"));
				result.setJigyoId(resultSet.getString("JIGYO_ID"));
				result.setJigyoName(resultSet.getString("JIGYO_NAME"));
				result.setBunkaSaimokuCd(resultSet.getString("BUNKASAIMOKU_CD"));
				result.setEdaNo(resultSet.getString("EDA_NO"));
				result.setCheckDigit(resultSet.getString("CHECKDIGIT"));		
				result.setKekkaAbc(resultSet.getString("KEKKA_ABC"));		
				result.setKekkaTen(resultSet.getString("KEKKA_TEN"));
				result.setComment1(resultSet.getString("COMMENT1"));
				result.setComment2(resultSet.getString("COMMENT2"));
				result.setComment3(resultSet.getString("COMMENT3"));
				result.setComment4(resultSet.getString("COMMENT4"));
				result.setComment5(resultSet.getString("COMMENT5"));
				result.setComment6(resultSet.getString("COMMENT6"));
				result.setKenkyuNaiyo(resultSet.getString("KENKYUNAIYO"));
				result.setKenkyuKeikaku(resultSet.getString("KENKYUKEIKAKU"));
				result.setTekisetsuKaigai(resultSet.getString("TEKISETSU_KAIGAI"));			
				result.setTekisetsuKenkyu1(resultSet.getString("TEKISETSU_KENKYU1"));
				result.setTekisetsu(resultSet.getString("TEKISETSU"));
				result.setDato(resultSet.getString("DATO"));
				result.setShinseisha(resultSet.getString("SHINSEISHA"));
				result.setKenkyuBuntansha(resultSet.getString("KENKYUBUNTANSHA"));
				result.setHitogenomu(resultSet.getString("HITOGENOMU"));
				result.setTokutei(resultSet.getString("TOKUTEI"));
				result.setHitoEs(resultSet.getString("HITOES"));
				result.setKumikae(resultSet.getString("KUMIKAE"));
				result.setChiryo(resultSet.getString("CHIRYO"));
				result.setEkigaku(resultSet.getString("EKIGAKU"));
				result.setComments(resultSet.getString("COMMENTS"));
				//2005.10.26 kainuma
			  	result.setRigai(resultSet.getString("RIGAI"));
				result.setDairi(resultSet.getString("DAIRI"));
				result.setWakates(resultSet.getString("TEKISETSU_WAKATES"));//2007/5/8 �ǉ�
			  	result.setJuyosei(resultSet.getString("JUYOSEI"));
			  	result.setDokusosei(resultSet.getString("DOKUSOSEI"));
			  	result.setHakyukoka(resultSet.getString("HAKYUKOKA"));
			  	result.setSuikonoryoku(resultSet.getString("SUIKONORYOKU"));
			  	result.setJinken(resultSet.getString("JINKEN"));
			  	result.setBuntankin(resultSet.getString("BUNTANKIN"));
			  	result.setOtherComment(resultSet.getString("OTHER_COMMENT"));
			  	//
				result.setKoshinDate(resultSet.getDate("KOSHIN_DATE"));
				result.setTenpuPath(resultSet.getString("TENPU_PATH"));
				result.setTenpuFlg(resultSet.getString("TENPU_FLG"));
				result.setShinsaJokyo(resultSet.getString("SHINSA_JOKYO"));
				result.setBiko(resultSet.getString("BIKO"));			
				resultList.add(result);
			}
			
			//�߂�l
			shinseiKekkaInfo = (ShinsaKekkaInfo[])resultList.toArray(new ShinsaKekkaInfo[0]);
			if(shinseiKekkaInfo.length == 0){
				throw new NoDataFoundException(
					"�R�����ʏ��e�[�u���ɊY������f�[�^��������܂���B�����L�[�F�R�����ԍ�'"
						+ shinsaJokyoInfo.getShinsainNo() + "', ����ID'" + shinsaJokyoInfo.getJigyoId() + "'");
			}

		} catch (SQLException ex) {
			throw new DataAccessException("�R�����ʏ��e�[�u���������s���ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(resultSet, preparedStatement);
		}
		
		return shinseiKekkaInfo;		
		
	}



	/*
	 * 
	 * �R�������ꗗ�̑S������Ԃ��B
	 * @param	connection			�R�l�N�V����
     * @param select          �R�����ԍ�
     * @return int
     * @throws DataAccessException
	 */
	public int countTotalPage(Connection connection, String select)
			throws DataAccessException {

		//-----------------------
		// SQL���̍쐬
		//-----------------------
		//SQL���́AShinsajokyoKakunin�N���X�ō쐬�B
		//�{���͂����ō쐬���ׂ������A�������������𑼂Ŏg�p���Ă��邽�߁A�ړ��ł����B

		PreparedStatement preparedStatement = null;
		ResultSet         rset              = null;
		try {
			//�擾
			preparedStatement = connection.prepareStatement(select);
			rset = preparedStatement.executeQuery();
			int count = 0;
			if (rset.next()) {
				count = rset.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			throw new DataAccessException("�R����񃌃R�[�h�������ɗ�O���������܂����B ", ex);
		} finally {
			DatabaseUtil.closeResource(rset, preparedStatement);
		}
	}	
}
