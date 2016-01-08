/*======================================================================
 *    SYSTEM      : �d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : KenkyushaMaintenance
 *    Description : �����ҏ��Ǘ��N���X
 *
 *    Author      : yoshikawa_h
 *    Date        : 2005/03/28
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
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * �����ҏ��Ǘ��N���X.<br>
 * @author yoshikawa_h
 */
public class KenkyushaMaintenance implements IKenkyushaMaintenance {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(KenkyushaMaintenance.class);
	
	/**
	 * ID�p�X���[�h�ʒm���t�@�C���i�[�t�H���_ .<br /><br />
	 * 
	 * ��̓I�Ȓl�́A"<b>${shinsei_path}/work/shinseisha/</b>"<br />
	 */
	private static String SHINSEISHA_WORK_FOLDER      = ApplicationSettings.getString(ISettingKeys.SHINSEISHA_WORK_FOLDER);		

	/**
	 * ID�p�X���[�h�ʒm��Word�t�@�C���i�[�t�H���_.<br /><br />
	 * 
	 * ��̓I�Ȓl�́A"<b>${shinsei_path}/settings/shinseisha/</b>"<br />
	 */
	private static String SHINSEISHA_FORMAT_PATH      = ApplicationSettings.getString(ISettingKeys.SHINSEISHA_FORMAT_PATH);		

	/**
	 * ID�p�X���[�h�ʒm��Word�t�@�C����.<br /><br />
	 * 
	 * ��̓I�Ȓl�́A"<b>shinseisha.doc</b>"<br />
	 */
	private static String SHINSEISHA_FORMAT_FILE_NAME = ApplicationSettings.getString(ISettingKeys.SHINSEISHA_FORMAT_FILE_NAME);		

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
    public KenkyushaMaintenance() {
        super();
    }
    
    /**
     * �����ҏ��̎擾.<br /><br />
     * 
     * �����҃}�X�^���猤���ҏ����擾����B
     * 
     * �ȉ���SQL���𔭍s���āA�����҃}�X�^�e�[�u������f�[�^���擾����B<br />
     * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
     * 
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
     * <tr bgcolor="#FFFFFF"><td><pre>
     *    SELECT
     *	      KENKYU.KENKYU_NO,	            //�����Ҕԍ�
     *        KENKYU.NAME_KANJI_SEI,        //�����i������-���j
     *        KENKYU.NAME_KANJI_MEI,        //�����i������-���j
     *        KENKYU.NAME_KANA_SEI,         //�����i�t���K�i-���j
     *        KENKYU.NAME_KANA_MEI,         //�����i�t���K�i-���j
     *        KENKYU.SEIBETSU,              //����
     *        KENKYU.BIRTHDAY,              //���N����
     *        KENKYU.GAKUI,                 //�w��
     *        KENKYU.SHOZOKU_CD,            //�����@�փR�[�h
     *        KIKAN.SHOZOKU_NAME_KANJI,     //�����@�֖��i�a���j
     *        KIKAN.SHOZOKU_NAME_EIGO,      //�����@�֖��i�p���j
     *        KIKAN.SHOZOKU_RYAKUSHO,       //�����@�֖��i���́j
     *        KENKYU.BUKYOKU_CD,            //���ǃR�[�h
     *        BUKYOKU.BUKA_NAME,            //���ǖ�
     *        BUKYOKU.BUKA_RYAKUSHO,        //���Ǘ���
     *        KENKYU.SHOKUSHU_CD,           //�E�R�[�h
     *        SHOKU.SHOKUSHU_NAME,          //�E��
     *        SHOKU.SHOKUSHU_NAME_RYAKU,    //�E����
     *        KENKYU.KOSHIN_DATE,           //�X�V����
     *        KENKYU.BIKO                   //���l
     *        KENKYU.DEL_FLG                //�폜�t���O
     *    FROM 
     *        MASTER_KENKYUSHA KENKYU       //�����҃}�X�^
     *    INNER JOIN 
     *        MASTER_KIKAN KIKAN            //�����@�փ}�X�^
     *    ON 
     *        KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD
     *    INNER JOIN 
     *        MASTER_BUKYOKU BUKYOKU        //���ǃ}�X�^
     *    ON 
     *        KENKYU.BUKYOKU_CD = BUKYOKU.BUKYOKU_CD
     *    INNER JOIN 
     *        MASTER_SHOKUSHU SHOKU         //�E��}�X�^
     *    ON 
     *        KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD
     *    WHERE 
     *        KENKYU.SHOZOKU_CD = ?
     *    AND 
     *        KENKYU.KENKYU_NO = ?
     *    AND 
     *        KENKYU.DEL_FLG = 0
     * </pre>
     * </td></tr>
     * </table><br />
     *
     * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
     *   <tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
     *   <tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������ShinseishaPk�̕ϐ�ShozokuCd���g�p����B</td></tr>
     *   <tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������ShinseishaPk�̕ϐ�KenkyuNo���g�p����B</td></tr>
     * </table><br />
     * 
     * �擾�����l��KenkyushaInfo�Ɋi�[���A�ԋp����B<br /><br />
     * 
     * @param userInfo UserInfo
     * @param pkInfo KenkyushaPk
     * @return KenkyushaInfo
     * @throws ApplicationException
     */
    public KenkyushaInfo select(UserInfo userInfo, KenkyushaPk pkInfo)
            throws ApplicationException {

        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
// ���b�N��False�ɏC���i�{���\�b�h���Ă񂾌�Ɍ����҃}�X�^���X�V���鏈�������������Ȃ̂ŁB�B�B�j
//            return dao.selectKenkyushaInfo(connection, pkInfo, true);
            return dao.selectKenkyushaInfo(connection, pkInfo, false);
        } catch (DataAccessException e) {
            throw new ApplicationException(
                "�����҃f�[�^��������DB�G���[���������܂����B",
                new ErrorInfo("errors.4004"),
                e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
    
    /**
     * ���o�^�\���҂�Page���̎擾.<br><br>
     * 
     * �ȉ���SQL���𔭍s���āA���o�^�\���҂�Page�����擾����B
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
     * <tr bgcolor="#FFFFFF"><td><pre>
     * 
     * SELECT
     *     KENKYU.KENKYU_NO            -- �����Ҕԍ�
     *     , KENKYU.NAME_KANJI_SEI     -- �����i������-���j
     *     , KENKYU.NAME_KANJI_MEI     -- �����i������-���j
     *     , KENKYU.NAME_KANA_SEI      -- �����i�t���K�i-���j
     *     , KENKYU.NAME_KANA_MEI      -- �����i�t���K�i-���j
     *     , KENKYU.SEIBETSU           -- ����
     *     , KENKYU.BIRTHDAY           -- ���N����
     *     , KENKYU.GAKUI              -- �w��
     *     , KENKYU.SHOZOKU_CD         -- �����@�փR�[�h
     *     , KENKYU.BUKYOKU_CD         -- ���ǃR�[�h
     *     , BUKYOKU.BUKA_RYAKUSHO     -- ���Ǘ���
     *     , KENKYU.SHOKUSHU_CD        -- �E�R�[�h
     *     , SHOKU.SHOKUSHU_NAME_RYAKU -- �E����
     *     , KENKYU.KOSHIN_DATE        -- �X�V����
     *     , KENKYU.BIKO               -- ���l
     * FROM MASTER_KENKYUSHA KENKYU                 -- �����҃}�X�^
     *         INNER JOIN MASTER_BUKYOKU BUKYOKU    -- ���ǃ}�X�^
     *             ON KENKYU.BUKYOKU_CD = BUKYOKU.BUKYOKU_CD
     *         INNER JOIN MASTER_SHOKUSHU SHOKU     -- �E��}�X�^
     *             ON KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD
     * WHERE KENKYU.KENKYU_NO NOT IN
     *             ( SELECT KENKYU_NO FROM SHINSEISHAINFO WHERE DEL_FLG = 0 AND SHOZOKU_CD = + shozokuCd + )
     *                     AND KENKYU.SHOZOKU_CD = + shozokuCd;
     * 
     *     <b><span style="color:#002288">-- ���I�������� --</span></b>
     * ORDER BY
     *        KENKYU.KENKYU_NO</pre>
     * </td></tr>
     * </table><br />
     * 
     * <b><span style="color:#002288">���I��������</span></b><br/>
     * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
     * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
     *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '�����@�փR�[�h'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(����-��)</td><td>NameKanjiSei</td><td>AND NAME_KANJI_SEI LIKE '%�\���Ҏ���(����-��)%'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(����-��)</td><td>NameKanjiMei</td><td>AND NAME_KANJI_MEI LIKE '%�\���Ҏ���(����-��)%'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(�t���K�i-��)</td><td>NameKanaSei</td><td>AND NAME_KANA_SEI LIKE '%�\���Ҏ���(�t���K�i-��)%'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(�t���K�i-��)</td><td>NameKanaMei</td><td>AND NAME_KANA_MEI LIKE '%�\���Ҏ���(�t���K�i-��)%'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><td>BukyokuCd</td><td>AND BUKYOKU_CD = '���ǃR�[�h'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>KenkyuNo</td><td>AND KENKYU_NO = '�����Ҕԍ�'</td></tr>
     * </table><br />
     * 
     * �擾�����l�́A�񖼂��L�[��Map�ɃZ�b�g����AList�ɃZ�b�g�����B
     * ����List���i�[����Page��ԋp����B<br /><br />
     * 
     * @param userInfo UserInfo
     * @param searchInfo ShinseishaSearchInfo
     * @return ���o�^�\���ҏ���Page
     */
    public Page searchUnregist(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
            throws ApplicationException {
        
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
        
        MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
        
        return dao.searchUnregist(userInfo, searchInfo);
    }
    
    
    /**
     * �\���ҏ��̈ꊇ�o�^.<br><br>
     * 
     * 
     * @param userInfo
     * @param kenkyuNo
     * @return
     * @throws ApplicationException
     */
    public synchronized void registShinseishaFromKenkyusha(UserInfo userInfo, String[] kenkyuNo)
            throws ApplicationException {

        boolean success = false;
        Connection connection = null;
		
		//------�o�^�Ώی����ҏ��̎擾
		KenkyushaPk pkInfo = new KenkyushaPk();
		
		KenkyushaInfo kenkyushaInfo = new KenkyushaInfo();
		ShinseishaInfo addInfo = new ShinseishaInfo();
		String hakkoshaId = null;
		String shozokuCd = null;
		String shozokuNameKanji = null;
		String shozokuNameEigo = null;
		String shozokuNameRyaku = null;
		
		try{
			connection = DatabaseUtil.getConnection();
			
			if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
				//�����@�֒S���҂̂Ƃ�
				ShozokuInfo info = userInfo.getShozokuInfo();
				hakkoshaId = info.getShozokuTantoId();
				shozokuCd = info.getShozokuCd();
				shozokuNameKanji = info.getShozokuName();
				shozokuNameEigo = info.getShozokuNameEigo();
				shozokuNameRyaku = info.getShozokuRyakusho();
				
			}else if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
				//���ǒS���҂̂Ƃ�
				BukyokutantoInfo info = userInfo.getBukyokutantoInfo();
				hakkoshaId = info.getBukyokutantoId();
				shozokuCd = info.getShozokuCd();
				
				try{
					//�@�֖��p��E���̓��������@�֒S���ҏ�񂩂�擾����
					ShozokuSearchInfo searchInfo = new ShozokuSearchInfo();
					searchInfo.setShozokuCd(shozokuCd);
					ShozokuMaintenance shozokuMainte = new ShozokuMaintenance();
					List list = shozokuMainte.search(userInfo, searchInfo).getList();;
					Map map = (Map)list.get(0);	//���ɕ������������Ƃ��Ă��P���ڂ����Q�Ƃ���
					shozokuNameKanji = (String)map.get("SHOZOKU_NAME_KANJI");
					shozokuNameEigo  = (String)map.get("SHOZOKU_NAME_EIGO");
					shozokuNameRyaku = (String)map.get("SHOZOKU_RYAKUSHO");
				}catch(NoDataFoundException e){
					if(log.isDebugEnabled()){
						String msg = "�����@�֒S���҂����݂��Ȃ��������߁A�@�֖��A�@�֖��p��A�@�֖����̂�null�B";
						log.debug(msg);
					}
				}
				
			}
			
			//�I���������o�^�\���҂̌������J��Ԃ�
			for(int i=0; i<kenkyuNo.length; i++){
				if(kenkyuNo[i] == null || kenkyuNo[i].equals("")){
					continue;
				}
				
				//------��L�[���̃Z�b�g
				pkInfo.setKenkyuNo(kenkyuNo[i]);
				pkInfo.setShozokuCd(shozokuCd);
				
				//------�L�[�������ɍX�V�f�[�^�擾	
				//kenkyushaInfo = select(userInfo,pkInfo);
				//2005/04/19 �C�� ��������---------------------------------------------------
				//FOR UPDATE�̃R�l�N�V�����𓯂��R�l�N�V�����ɂ��邽�ߏC��
				MasterKenkyushaInfoDao kenkyushaDao = new MasterKenkyushaInfoDao(userInfo);
				kenkyushaInfo = kenkyushaDao.selectKenkyushaInfo(connection, pkInfo, true);
				//�C�� �����܂�--------------------------------------------------------------
				
				//�\���ҏ����Z�b�g�i�@�֏��͏����S���҂���擾����j
				addInfo.setShozokuCd(kenkyushaInfo.getShozokuCd());
				addInfo.setShozokuName(shozokuNameKanji);
				addInfo.setShozokuNameEigo(shozokuNameEigo);
				addInfo.setShozokuNameRyaku(shozokuNameRyaku);
				addInfo.setNameKanjiSei(kenkyushaInfo.getNameKanjiSei());
				addInfo.setNameKanjiMei(kenkyushaInfo.getNameKanjiMei());
				addInfo.setNameKanaSei(kenkyushaInfo.getNameKanaSei());
				addInfo.setNameKanaMei(kenkyushaInfo.getNameKanaMei());
				addInfo.setKenkyuNo(kenkyushaInfo.getKenkyuNo());
				addInfo.setBukyokuCd(kenkyushaInfo.getBukyokuCd());
				addInfo.setBukyokuName(kenkyushaInfo.getBukyokuName());
				addInfo.setBukyokuNameRyaku(kenkyushaInfo.getBukyokuNameRyaku());
				addInfo.setShokushuCd(kenkyushaInfo.getShokushuCd());
				addInfo.setShokushuNameKanji(kenkyushaInfo.getShokushuName());
				addInfo.setShokushuNameRyaku(kenkyushaInfo.getShokushuNameRyaku());
				addInfo.setBirthday(kenkyushaInfo.getBirthday());
				addInfo.setHakkoshaId(hakkoshaId);;
				addInfo.setDelFlg("0");
				
	            //---------------------------------------
	            //�\���ҏ��f�[�^�A�N�Z�X�I�u�W�F�N�g
	            //---------------------------------------
	            ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);

				//2005.09.26 iso ���d�o�^�h�~�̂��ߒǉ�
				if(dao.countShinseishaInfoPreInsert(connection, addInfo) > 0) {
					throw new ApplicationException("���łɉ���҂��o�^����Ă��܂��B", 	new ErrorInfo("errors.4011"));
				}

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
			
			}
					
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

	//2005/04/15 �ǉ� ��������----------------------------------------------------------------
	//���R �����ҏ��̏����̂���	
	/**
	 * ���̓f�[�^�`�F�b�N.<br /><br />
	 * 
	 * <b>1.�����@�֏��̎擾</b><br /><br />
	 * 
	 * ������info�̃R�[�h�̒l���g�p���āA���O���擾����B
	 * �擾�́A�����\�b�h���g�p���čs���B�g�p����R�[�h�A���\�b�h�A�擾���閼�O�͈ȉ��̕\���Q�ƁB<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>�g�p����R�[�h</td><td>�擾���閼�O</td><td>�g�p���鎩���\�b�h</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>�����@�֖�</td><td>getKikanCodeValueAction()</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǃR�[�h</td><td>���ǖ��A���ǖ�(����)</td><td>getBukyokuCodeMap()</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E��R�[�h</td><td>�E��</td><td>getShokushuMap()</td></tr>
	 * </table><br />
	 * 
	 * �擾�����l�́Ainfo�Ɋi�[����B<br /><br />
	 * 
	 * <b>2.�����Ҕԍ��`�F�b�N</b><br/><br/>
	 * �����Ҕԍ������l�ȊO�A8���ȊO�Anull�̏ꍇ�͗�O��throw����B<br />
	 * �܂��AApplicationSettings.properties��CHECK_DIGIT_FLAG��true�̏ꍇ�́A<BR/>
	 * ShinseishaMaintenance��checkKenkyuNo()���\�b�h���g�p���Č����Ҕԍ��̃`�F�b�N�f�W�b�g�`�F�b�N���s���A<br>
	 * �����Ҕԍ��̒l��8���ڂ̒l(��ԍ��̌�)���A�`�F�b�N�f�W�b�g�̒l�ƈقȂ�ꍇ�ɂ́A��O��throw����B<br />
	 * 
	 * <b>3.�d���`�F�b�N</b><br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���A�����Ҕԍ��E�����@�փR�[�h��
	 * ���������҂��o�^����Ă��Ȃ����ǂ������m�F����B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		SELECT 
				COUNT(*)
		FROM 
				MASTER_KENKYUSHA 
		WHERE 
				KENKYU_NO = ? 
		AND 
				SHOZOKU_CD = ?
		AND 
				DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������info�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������info�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �������ʂ����݂���ꍇ�ɂ͗�O��throw���A���݂��Ȃ��ꍇ�ɂ�info��ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param info KenkyushaInfo
	 * @return �����ҏ���ShinseishaInfo
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public KenkyushaInfo validate(UserInfo userInfo, KenkyushaInfo info, String mode)
			throws ApplicationException, ValidationException {

		Connection connection = null;	
		try {
			ShinseishaMaintenance shinsehsha = new ShinseishaMaintenance();
			connection = DatabaseUtil.getConnection();

			//---------------------------
			//�����@�փR�[�h�������@�֖��̃Z�b�g
			//---------------------------
			KikanInfo kikanInfo = new KikanInfo();
			kikanInfo = getKikanCodeValueAction(userInfo, info.getShozokuCd(), true);
			info.setShozokuNameKanji(kikanInfo.getShozokuNameKanji());
			info.setShozokuNameEigo(kikanInfo.getShozokuNameEigo());
			info.setShozokuRyakusho(kikanInfo.getShozokuRyakusho());
			
			//---------------------------
			//���ǃR�[�h�����ǖ��A���ǖ�(����)
			//---------------------------
			if(info.getBukyokuCd() != null && !info.getBukyokuCd().equals("")){
				Map map = getBukyokuCodeMap(userInfo, info.getBukyokuCd());
				info.setBukyokuName((String)map.get("BUKA_NAME"));
				info.setBukyokuNameRyaku((String)map.get("BUKA_RYAKUSHO"));
			}
			//---------------------------
			//�E��R�[�h���E��	
			//---------------------------
			Map shokushuMap = getShokushuMap(userInfo, info.getShokushuCd());
			info.setShokushuName((String)shokushuMap.get("SHOKUSHU_NAME"));
			info.setShokushuNameRyaku((String)shokushuMap.get("SHOKUSHU_NAME_RYAKU"));
			
			//�����Ҕԍ��̃`�F�b�N�f�W�b�g�`�F�b�N
			if(!shinsehsha.checkKenkyuNo(info.getKenkyuNo())) {
				throw new ApplicationException("�����Ҕԍ�����ł��B", 	new ErrorInfo("errors.required", new String[] {"�����Ҕԍ�"}));	
			}
			
			//2�d�o�^�`�F�b�N
			//�����҃}�X�^�e�[�u���ɂ��łɌ����Ҕԍ��A�������ǃR�[�h�����������҂��o�^����Ă��Ȃ����ǂ������m�F
			MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
			int count = dao.countKenkyushaInfo(connection, info);
			
			//���łɓo�^����Ă���ꍇ
			if(count > 0 && mode.equals(IMaintenanceName.ADD_MODE)){
				String[] error = {"������"};
				throw new ApplicationException("���łɌ����҂��o�^����Ă��܂��B", 	new ErrorInfo("errors.4007", error));			
			}else if(count == 0 && mode.equals(IMaintenanceName.EDIT_MODE)){
				throw new ApplicationException("�X�V�Ώۂ̌����҂��o�^����Ă��܂���B", new ErrorInfo("errors.5002"));
			}

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����ҊǗ��f�[�^�`�F�b�N����DB�G���[���������܂����B",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return info;
	}
	
	
	/**
	 * �@�֏��̕ԋp.<br /><br />
	 * 
	 * �����@�փR�[�h���珊���@�֖����擾����B<br />
	 * �n���������ɂ���āA�������啝�ɕς��B<br /><br />
	 * 
	 * �܂��A��������String"<b>kikanCd</b>"��null�������ꍇ�ɂ́A��O��throw����B<br /><br />
	 * 
	 * null�ł͂Ȃ��ꍇ�ɂ́A�ȉ���SQL���𔭍s����B<br /><br />
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
	 * �����āA��������boolean"<b>priorityFlg</b>"��false�̏ꍇ�ɂ́A
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
	private KikanInfo getKikanCodeValueAction(UserInfo userInfo, String kikanCd, boolean priorityFlg)
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

			try {
				kikanInfo = new MasterKikanInfoDao(userInfo).selectKikanInfo(connection,kikanInfo);
			} catch(NoDataFoundException noDataShozokuInfo) {
				//������Ȃ������Ƃ��B
				errors.add(new ErrorInfo("errors.2001", new String[] { "�����@�փR�[�h" }));
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
					}
				} catch(ApplicationException e) {
					//������Ȃ������Ƃ��B
					//�������@�փe�[�u���ɖ����ꍇ�́A�@�փ}�X�^�̃f�[�^������̂ŃG���[�Ƃ��Ȃ��B
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
	 * ���Ȗ��́E���ȗ��̂�Map�̕ԋp.<br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA���Ǐ����擾����B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre> 
	 * SELECT
	 *	*
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
	 * �쐬����Map��ԋp����B<br /><br />
	 * 
	 * @param  userInfo          	 ���s���郆�[�U���
	 * @param  kikanCd          	 ���ǃR�[�h
	 * @return                      ���Ȗ��́E���ȗ��̂�Map
	 * @throws ApplicationException 
	 */
	private Map getBukyokuCodeMap(UserInfo userInfo, String bukyokuCd)
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
	 * �쐬����Map��ԋp����B<br /><br />
	 * 
	 * @param  userInfo          	 ���s���郆�[�U���
	 * @param  shokushuCd         	 �E�햼�R�[�h
	 * @return                      �E�햼�E�E�햼(����)��Map
	 * @throws ApplicationException 
	 */
	private Map getShokushuMap(UserInfo userInfo, String shokushuCd)
			throws ApplicationException {

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
				hashMap.put("SHOKUSHU_NAME", shokushuInfo.getShokushuName());
				hashMap.put("SHOKUSHU_NAME_RYAKU", shokushuInfo.getShokushuNameRyaku());	//���̂̓}�X�^�̒l���g�p����
			} catch(NoDataFoundException e) {
				//������Ȃ������Ƃ��B
				errors.add(
					new ErrorInfo("errors.2001", new String[] { "�E��R�[�h" }));
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
		
	
	/**
	 * �����ҏ��̒ǉ�.<br /><br />
	 * �����ҏ��e�[�u���ɐV�K�Ńf�[�^�������A���̃f�[�^��ԋp����B<br /><br />
	 * 
	 * <b>1.�X�V�f�[�^�m�F</b><br/><br/>
	 * �ȉ���SQL���𔭍s���A�����Ҕԍ��E�����@�փR�[�h��
	 * ���������҂��o�^����Ă��邩�ǂ������m�F����B<br />
	 * �܂��AFOR UPDATE�������邱�ƂŌ����҃}�X�^�e�[�u�������b�N����B<BR/>
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		SELECT 
				COUNT(*) 
		FROM 
				MASTER_KENKYUSHA
		WHERE 
				KENKYU_NO = ?
		AND 
				SHOZOKU_CD = ? 
		AND 
				DEL_FLG = 0
		FOR UPDATE</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������info�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������info�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * </table><br /> 
	 * �������ʂ����݂��鍇�͗�O��throw����B<br /><br />
	 *
	 * <b>2.�����ҏ��̑}��</b><br /><br />
	 * 
	 * �ȉ���SQL���𔭍s���āA�\���ҏ��e�[�u���Ƀf�[�^��}������B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
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
				DEL_FLG) 
	VALUES
			(?,?,?,?,?,?,?,?,?,?,?,sysdate,?,0)</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������addInfo�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������addInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������addInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������addInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������addInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����</td><td>������addInfo�̕ϐ�Seibetsu���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���N����</td><td>������addInfo�̕ϐ�Birthday���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�w��</td><td>������addInfo�̕ϐ�gakui���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������addInfo�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�(�R�[�h)</td><td>������addInfo�̕ϐ�BukyokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E���R�[�h</td><td>������addInfo�̕ϐ�ShokushuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>������addInfo�̕ϐ�Biko���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param addInfo KenkyushaInfo
	 * @return �o�^���������ҏ�������KenkyushaInfo
	 * @throws ApplicationException
	 */
	public synchronized void insert(UserInfo userInfo, KenkyushaInfo addInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			//---------------------------------------	
			//�����҃}�X�^�f�[�^�A�N�Z�X�I�u�W�F�N�g
			//---------------------------------------
			MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);

			//2005/8/26 �f�[�^�����݂��邩�`�F�b�N�i�폜�f�[�^���܂߂�j
			int cnt = dao.countKenkyushaInfo(connection, addInfo, false);
			
			//2005/04/22�@�ǉ� ��������---------------
			//�X�V���t�ƍ폜�t���O����SQL�������ł͂Ȃ��AKenkyushaInfo�œn���悤�ɂ���
			addInfo.setKoshinDate(new Date());
			addInfo.setDelFlg("0");
			//�ǉ� �����܂�---------------------------

			if (cnt > 0){
				//---------------------------------------
				//�����ҏ��̍X�V
				//---------------------------------------
				dao.updateKenkyushaInfo(connection,addInfo);
				
				//---------------------------------------
				//�����ҏ���艞��ҏ��̍X�V
				//---------------------------------------
				updateShinseishaFromKenkyusha(userInfo, connection, addInfo);
				
			}
			else {
				//---------------------------------------
				//�����ҏ��̒ǉ�
				//---------------------------------------
				dao.insertKenkyushaInfo(connection,addInfo);		
			}
			//---------------------------------------
			//�o�^����I��
			//---------------------------------------
			success = true;			
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����ҊǗ��f�[�^�o�^����DB�G���[���������܂����B",
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
					"�����ҊǗ��f�[�^�o�^����DB�G���[���������܂����B",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}	


	/**
	 * ���������ɍ��������ҏ����擾����B
	 * 
	 * �ȉ���SQL���𔭍s���āA�\���҂�Page�����擾����B
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 	SELECT
	 			KENKYU.KENKYU_NO,
	 			KENKYU.NAME_KANJI_SEI,
	 			KENKYU.NAME_KANJI_MEI,
	 			KIKAN.SHOZOKU_RYAKUSHO,
	 			SHOKUSHU.SHOKUSHU_NAME_RYAKU,
	 			KENKYU.SHOZOKU_CD
	 	FROM 
	 			MASTER_KENKYUSHA KENKYU 
	 	INNER JOIN 
	 			MASTER_KIKAN KIKAN 
	 	ON
	 			KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD
	 	INNER JOIN 
	 			MASTER_SHOKUSHU SHOKUSHU
	 	ON 
	 			KENKYU.SHOKUSHU_CD = SHOKUSHU.SHOKUSHU_CD
	 	WHERE 
	 			KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD 
	 	AND 
	 			KENKYU.DEL_FLG = 0

	 * 	<b><span style="color:#002288">-- ���I�������� --</span></b>
	 * 
	 * ORDER BY
	 * 	KENKYU.KENKYU_NO</pre>
	 * 
	 * <b><span style="color:#002288">���I��������</span></b><br/>
	 * ����searchInfo�̒l�ɂ���Č������������I�ɕω�����B
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">�ϐ����i���{��j</td><td>�ϐ���</td><td>���I��������</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>KenkyuNo</td><td>AND KENKYU_NO = '�����Ҕԍ�'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(����-��)</td><td>NameKanjiSei</td><td>AND NAME_KANJI_SEI LIKE '%�\���Ҏ���(����-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(����-��)</td><td>NameKanjiMei</td><td>AND NAME_KANJI_MEI LIKE '%�\���Ҏ���(����-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(�t���K�i-��)</td><td>NameKanaSei</td><td>AND NAME_KANA_SEI LIKE '%�\���Ҏ���(�t���K�i-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�\���Ҏ���(�t���K�i-��)</td><td>NameKanaMei</td><td>AND NAME_KANA_MEI LIKE '%�\���Ҏ���(�t���K�i-��)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '�����@�փR�[�h'</td></tr>
	 * </table><br /> 
	 * 
	 * �擾�����l�́A�񖼂��L�[��Map�ɃZ�b�g����AList�ɃZ�b�g�����B
	 * ����List���i�[����Page��ԋp����B<br /><br /> 
	 * 
	 * @param searchInfo	KenkyushaSearchInfo
	 * @return �����ҏ���Page
 	 * @throws ApplicationException
 	 */
	public Page search(KenkyushaSearchInfo searchInfo)
	throws ApplicationException {
			
		//-----------------------
		// �����������SQL���̍쐬
		//-----------------------
		String select =
			"SELECT"
				+ " KENKYU.KENKYU_NO,"
				+ " KENKYU.NAME_KANJI_SEI,"
				+ " KENKYU.NAME_KANJI_MEI,"
				+ " KIKAN.SHOZOKU_RYAKUSHO,"
				+ " SHOKUSHU.SHOKUSHU_NAME_RYAKU,"
				+ " KENKYU.SHOZOKU_CD"
		+ " FROM " 
				+ " MASTER_KENKYUSHA KENKYU "
	//�@�փ}�X�^�ƌ����҃}�X�^���O�������ɂ���
	//	+ " INNER JOIN " 
		+ " LEFT JOIN "
				+" MASTER_KIKAN KIKAN "
		+ " ON " 
				+ " KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD"
		+ " INNER JOIN " 
				+ " MASTER_SHOKUSHU SHOKUSHU"
		+ " ON " 
				+ " KENKYU.SHOKUSHU_CD = SHOKUSHU.SHOKUSHU_CD"
	//	+ " WHERE KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD "
		//2005/04/21�@�ǉ� ��������----------------------------------------
		//�������}�X�^�ɍ폜�t���O�ǉ��̂���
	//	+ " AND KENKYU.DEL_FLG = 0";
		+ " WHERE KENKYU.DEL_FLG = 0";
		//�ǉ� �����܂�----------------------------------------------------
		StringBuffer query = new StringBuffer(select);
		
		if(searchInfo.getKenkyuNo() != null && !searchInfo.getKenkyuNo().equals("")){			//�����Ҕԍ�
			query.append(" AND KENKYU.KENKYU_NO = '" + EscapeUtil.toSqlString(searchInfo.getKenkyuNo()) +"'");
		}
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	//�\���Ҏ����i����-���j
			query.append(" AND KENKYU.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	//�\���Ҏ����i����-���j
			query.append(" AND KENKYU.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	//�\���Ҏ����i�t���K�i-���j
			query.append(" AND KENKYU.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
		}
		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){	//�\���Ҏ����i�t���K�i-���j
			query.append(" AND KENKYU.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//�����@��CD
			query.append(" AND KENKYU.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) +"'");
		}
		//�\�[�g���i�\����ID�̏����j
		query.append(" ORDER BY KENKYU.KENKYU_NO");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		//-----------------------
		// �y�[�W�擾
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���o�^�\���҃f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	
	/**
	 * �����ҏ��̍X�V.<br /><br />
	 * 
	 * <b>1.�X�V�f�[�^�m�F</b><br/><br/>
	 * �ȉ���SQL���𔭍s���A�����Ҕԍ��E�����@�փR�[�h��
	 * ���������҂��o�^����Ă��邩�ǂ������m�F����B<br />
	 * �܂��AFOR UPDATE�������邱�ƂŌ����҃}�X�^�e�[�u�������b�N����B<BR/>
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		SELECT 
				COUNT(*) 
		FROM 
				MASTER_KENKYUSHA
		WHERE 
				KENKYU_NO = ?
		AND 
				SHOZOKU_CD = ? 
		FOR UPDATE</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������info�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������info�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * </table><br /> 
	 * �������ʂ����݂��Ȃ��ꍇ�͗�O��throw����B<br /><br />
	 *
	 * <b>2.�����ҏ��X�V</b><br/><br/>
	 * �@
	 * �ȉ���SQL���𔭍s���āA�����ҏ��e�[�u���̍X�V���s���B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		UPDATE 
					MASTER_KENKYUSHA
		SET
					KENKYU_NO = ? ,
					NAME_KANA_SEI = ? ,
					NAME_KANA_MEI = ? ,
					NAME_KANJI_SEI = ? ,
					NAME_KANJI_MEI = ? ,
					SEIBETSU = ? ,
					BIRTHDAY = ? ,
					GAKUI = ? ,
					SHOZOKU_CD = ? ,
					BUKYOKU_CD = ? ,
					SHOKUSHU_CD = ? ,
					KOSHIN_DATE = sysdate ,
					BIKO = ? 
		WHERE
					KENKYU_NO = ?
		AND 
					SHOZOKU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������updateInfo�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������updateInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(������-��)</td><td>������updateInfo�̕ϐ�NameKanjiSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������updateInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����(�t���K�i-��)</td><td>������updateInfo�̕ϐ�NameKanaSei���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����</td><td>������updateInfo�̕ϐ�Seibetsu���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���N����</td><td>������updateInfo�̕ϐ�Birthday���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�w��</td><td>������updateInfo�̕ϐ�Gakui���g�p����B</td></tr> 
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�֖�(�R�[�h)</td><td>������updateInfo�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���ǖ�(�R�[�h)</td><td>������updateInfo�̕ϐ�BukyokuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�E��</td><td>������updateInfo�̕ϐ�ShokushuCd���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>���l</td><td>������updateInfo�̕ϐ�Biko���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������updateInfo�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����CD</td><td>������updateInfo�̕ϐ�ShinseishaId���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * <b>3.�\���ҏ��X�V</b><br/><br/> 
	 * ShinseishaMaintenance��search���\�b�h���g�p���Đ\���ҏ��e�[�u���ɏC�����錤���҂̏�񂪊܂܂�Ă��邩�`�F�b�N����B<BR/>
	 * �Y������f�[�^�����݂���ꍇ�́AShinseishaMaintenance��update���\�b�h���g�p���čX�V����B<br>
	 * 
	 * @param userInfo UserInfo
	 * @param updateInfo KenkyushaInfo
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo, KenkyushaInfo updateInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
			
			//�X�V�`�F�b�N
			try{
				dao.getKenkyushaData(connection, updateInfo, true);
			}catch(NoDataFoundException e){//NG
				throw new ApplicationException("�X�V�Ώۂ̌����҂����݂��܂���B",
					new ErrorInfo("errors.4002"));
			}
			
			//2005/04/22�@�ǉ� ��������----------------------------------------------
			//�X�V���t�ƍ폜�t���O����SQL�������ł͂Ȃ��AKenkyushaInfo�œn���悤�ɂ���
			updateInfo.setKoshinDate(new Date());
			updateInfo.setDelFlg("0");
			//�ǉ� �����܂�----------------------------------------------------------
			
			//---------------------------------------
			//�����ҏ��̍X�V
			//---------------------------------------
			dao.updateKenkyushaInfo(connection, updateInfo);
			
			//---------------------------------------
			//�����ҏ���艞��ҏ��̍X�V
			//---------------------------------------
			updateShinseishaFromKenkyusha(userInfo, connection, updateInfo);
			
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
	 * �����ҏ��̍폜.<br /><br />
	 * 
	 * <b>1.�폜�f�[�^�m�F</b><br/><br/>
	 * �ȉ���SQL���𔭍s���A�����Ҕԍ��E�����@�փR�[�h��
	 * ���������҂��o�^����Ă��邩�ǂ������m�F����B<br />
	 * �܂��AFOR UPDATE�������邱�ƂŌ����҃}�X�^�e�[�u�������b�N����B<BR/>
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		SELECT 
				COUNT(*) 
		FROM 
				MASTER_KENKYUSHA
		WHERE 
				KENKYU_NO = ?
		AND 
				SHOZOKU_CD = ? 
		FOR UPDATE</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������info�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������info�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * </table><br /> 
	 * �������ʂ����݂��Ȃ��ꍇ�͗�O��throw����B<br /><br />
	 *
	 * <b>2.�����ҏ��폜</b><br/><br/> 
	 * �ȉ���SQL���𔭍s���āA�����ҏ��e�[�u���̏��𕨗��폜����br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
		DELETE FROM 
				MASTER_KENKYUSHA
		WHERE
				KENKYU_NO = ?
		AND
				SHOZOKU_CD = ? </pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������deleteInfo�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>����CD</td><td>������deleteInfo�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * <b>3.�\���ҏ��X�V</b><br/><br/> 
	 * ShinseishaMaintenance��search���\�b�h���g�p���Đ\���ҏ��e�[�u���ɏC�����錤���҂̏�񂪊܂܂�Ă��邩�`�F�b�N����B<BR/>
	 * �Y������f�[�^�����݂���ꍇ�́AShinseishaMaintenance��delete���\�b�h���g�p���č폜�t���O��1���Z�b�g����B<br>
	 * 
	 * @param userInfo UserInfo
	 * @param deleteInfo KenkyushaInfo
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo, KenkyushaInfo deleteInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
			//�폜�Ώۏ��̃`�F�b�N
			try{
				dao.getKenkyushaData(connection, deleteInfo, true);
			}catch(NoDataFoundException e){
				throw new ApplicationException("�폜�Ώۂ̌����҂����݂��܂���B", 
					new ErrorInfo("errors.4003"));
			}
			//�폜����
			dao.deleteKenkyushaInfo(connection, deleteInfo);
			
			//�폜����I��
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����ҊǗ��f�[�^�폜����DB�G���[���������܂����B",
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
					"�����ҊǗ��f�[�^�폜����DB�G���[���������܂����B",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	/**
	 * �����ҏ��̎擾.<br /><br />
	 * 
	 * �����҃}�X�^���猤���ҏ����擾����B
	 * 
	 * �ȉ���SQL���𔭍s���āA�����҃}�X�^�e�[�u������f�[�^���擾����B<br />
	 * (�o�C���h�ϐ��́ASQL���̉��̕\���Q��)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		SELECT
			KENKYU.KENKYU_NO,				//�����Ҕԍ�
			KENKYU.NAME_KANJI_SEI,			//�����i������-���j
			KENKYU.NAME_KANJI_MEI,			//�����i������-���j
			KENKYU.NAME_KANA_SEI,			//�����i�t���K�i-���j
			KENKYU.NAME_KANA_MEI,			//�����i�t���K�i-���j
			KENKYU.SEIBETSU,				//����
			KENKYU.BIRTHDAY,				//���N����
			KENKYU.GAKUI,					//�w��
			KENKYU.SHOZOKU_CD,				//�����@�փR�[�h
			KENKYU.BUKYOKU_CD,				//���ǃR�[�h
			KENKYU.SHOKUSHU_CD,				//�E�R�[�h
			KENKYU.KOSHIN_DATE,				//�X�V����
			KENKYU.BIKO,					//���l
			KIKAN.SHOZOKU_NAME_KANJI,		//�����@�֖��i�a���j
			KIKAN.SHOZOKU_NAME_EIGO,		//�����@�֖��i�p���j
			KIKAN.SHOZOKU_RYAKUSHO,			//�����@�֖��i���́j
			SHOKU.SHOKUSHU_NAME,			//�E��
			SHOKU.SHOKUSHU_NAME_RYAKU		//�E����
		FROM 
			MASTER_KENKYUSHA KENKYU			//�����҃}�X�^
		INNER JOIN 
			MASTER_KIKAN KIKAN				//�����@�փ}�X�^
		ON 
			KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD
		INNER JOIN 
			MASTER_SHOKUSHU SHOKU			//�E��}�X�^
		ON 
			KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD
		WHERE 
			KENKYU.SHOZOKU_CD = ?
		AND 
			KENKYU.KENKYU_NO = ?
		AND 
			KENKYU.DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>��</td><td>�l</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>�����@�փR�[�h</td><td>������ShinseishaPk�̕ϐ�ShozokuCd���g�p����B</td></tr>
	 *      <tr bgcolor="#FFFFFF"><td>�����Ҕԍ�</td><td>������ShinseishaPk�̕ϐ�KenkyuNo���g�p����B</td></tr>
	 * </table><br />
	 * 
	 * �������ʂ��畔��CD���擾�ł���ꍇ�͕��ǃ}�X�^���畔�ǖ��Ɨ��̂��擾����B<BR/>
	 * 
	 * �擾�����l��KenkyushaInfo�Ɋi�[���A�ԋp����B<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo KenkyushaPk
	 * @param lock	boolean
	 * @return KenkyushaInfo
	 * @throws ApplicationException
	 */
	public KenkyushaInfo selectKenkyushaData(UserInfo userInfo, KenkyushaPk pkInfo, boolean lock)
			throws ApplicationException {

		Connection connection = null;
		KenkyushaInfo result = new KenkyushaInfo();
		try {
			connection = DatabaseUtil.getConnection();
			MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
			result = dao.getKenkyushaData(connection, pkInfo, lock);
			
			//���ǂ��ݒ肳��Ă��錤���҂̏ꍇ�͕��ǖ����擾����
			String bukyokuCd = result.getBukyokuCd();
			if(bukyokuCd != null && !bukyokuCd.equals("") && !lock){
				MasterBukyokuInfoDao bukyokuDao = new MasterBukyokuInfoDao(userInfo);
				BukyokuPk pk = new BukyokuPk();
				pk.setBukyokuCd(bukyokuCd);
				BukyokuInfo info = bukyokuDao.selectBukyokuInfo(connection, pk);
				result.setBukyokuName(info.getBukaName());				//���ǖ�
				result.setBukyokuNameRyaku(info.getBukaRyakusyo());		//���Ǘ���
			}
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"�����҃f�[�^��������DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return result;
	}
	//�ǉ� �����܂�----------------------------------------------------------------------
	
	
	/**
	 * �p�X���[�h�Đݒ�ʒm���o�͏��̎擾
	 * @param UserInfo userInfo ���O�C���ҏ��
	 * @param String[] kenkyuNo �\���Ҍ����ԍ�
	 * @return�@FileResource�@�o�͏��CSV�t�@�E��
	 */
	public FileResource createTsuchisho(
			UserInfo userInfo,
			String[] kenkyuNo)
			throws ApplicationException
		{
			//DB���R�[�h�擾
			List csv_data = null;
			Connection connection = null;
			try {
				connection = DatabaseUtil.getConnection();
				String sozokuCd = null;		//�����R�[�h
				//�����S���҂̏ꍇ
				if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
					sozokuCd = userInfo.getShozokuInfo().getShozokuCd();
				}
				//���ǒS���҂̏ꍇ
				else{
					sozokuCd = userInfo.getBukyokutantoInfo().getShozokuCd();
				}

				csv_data = new ShinseishaInfoDao(userInfo).createCSV4Tsuchisho(connection, kenkyuNo, sozokuCd);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
			
			//-----------------------
			// CSV�t�@�C���o��
			//-----------------------
			String csvFileName = "SHINSEISHA";
			//2005/09/09 takano �t�H���_�����~���b�P�ʂɕύX�B�O�̂��ߓ����ɓ����������g�ݍ��݁B
			String filepath = null;
			synchronized(log){
				//2005/9/27 ���b�N���Ԃ��Z���ē������ۂ��Č������ׁA���O�C����ID���g�ݍ���
				//filepath = SHINSEISHA_WORK_FOLDER + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
				//�����S���҂̏ꍇ
				if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
					filepath = SHINSEISHA_WORK_FOLDER + userInfo.getShozokuInfo().getShozokuTantoId() + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
				}
				//���ǒS���҂̏ꍇ
				else{
					filepath = SHINSEISHA_WORK_FOLDER + userInfo.getBukyokutantoInfo().getBukyokutantoId() + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
				}
			}
			CsvUtil.output(csv_data, filepath, csvFileName);
			
			//-----------------------
			// �˗����t�@�C���̃R�s�[
			//-----------------------
			FileUtil.fileCopy(new File(SHINSEISHA_FORMAT_PATH + SHINSEISHA_FORMAT_FILE_NAME), new File(filepath + SHINSEISHA_FORMAT_FILE_NAME));
			FileUtil.fileCopy(new File(SHINSEISHA_FORMAT_PATH + "$"), new File(filepath + "$"));
			
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



	/**
	 * �����҃}�X�^�̏�񂩂牞��ҏ����X�V����B
	 * @param userInfo
	 * @param connection
	 * @param updateInfo
	 * @throws DataAccessException
	 * @throws ApplicationException
	 */
	private void updateShinseishaFromKenkyusha(
		UserInfo userInfo,
		Connection connection,
		KenkyushaInfo updateInfo)
		throws DataAccessException, ApplicationException
	{
		//2005/04/22�@�ǉ� ��������----------------------------------------------
		//�\���҂̍X�V��KenkyushaMaintenance�ōs���悤�ɏC��
		//�\���ҏ��̃`�F�b�N
		HashMap shinseiMap = new HashMap();
		ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
		searchInfo.setKenkyuNo(updateInfo.getKenkyuNo());
		searchInfo.setShozokuCd(updateInfo.getShozokuCd());
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
			shinseiInfo.setBirthday(updateInfo.getBirthday());
			shinseiInfo.setBukyokuCd(updateInfo.getBukyokuCd());
			shinseiInfo.setBukyokuName(updateInfo.getBukyokuName());
			shinseiInfo.setBukyokuNameRyaku(updateInfo.getBukyokuNameRyaku());
			shinseiInfo.setKenkyuNo(updateInfo.getKenkyuNo());
			shinseiInfo.setNameKanaMei(updateInfo.getNameKanaMei());
			shinseiInfo.setNameKanaSei(updateInfo.getNameKanaSei());
			shinseiInfo.setNameKanjiMei(updateInfo.getNameKanjiMei());
			shinseiInfo.setNameKanjiSei(updateInfo.getNameKanjiSei());
			shinseiInfo.setShokushuCd(updateInfo.getShokushuCd());
			shinseiInfo.setShokushuNameKanji(updateInfo.getShokushuName());
			shinseiInfo.setShokushuNameRyaku(updateInfo.getShokushuNameRyaku());
			shinseiInfo.setShozokuCd(updateInfo.getShozokuCd());
			shinseiInfo.setShozokuName(updateInfo.getShozokuNameKanji());
			shinseiInfo.setShozokuNameRyaku(updateInfo.getShozokuRyakusho());
			shinseiInfo.setShozokuNameEigo(updateInfo.getShozokuNameEigo());
			shinseiInfo.setBukyokuShubetuName((String) shinseiMap.get("OTHER_BUKYOKU"));
			shinseiInfo.setBukyokuShubetuCd((String) shinseiMap.get("SHUBETU_CD"));
			shinseiInfo.setDelFlg(updateInfo.getDelFlg());
			shinseiInfo.setHakkoDate((Date) shinseiMap.get("HAKKO_DATE"));
			shinseiInfo.setHakkoshaId((String) shinseiMap.get("HAKKOSHA_ID"));
			shinseiInfo.setHikoboFlg(shinseiMap.get("HIKOBO_FLG").toString());
			shinseiInfo.setNameRoMei((String) shinseiMap.get("NAME_RO_MEI"));
			shinseiInfo.setNameRoSei((String) shinseiMap.get("NAME_RO_SEI"));
			shinseiInfo.setPassword((String) shinseiMap.get("PASSWORD"));
			shinseiInfo.setYukoDate((Date) shinseiMap.get("YUKO_DATE"));
			//�\���҂̍X�V
			shinseiDao.updateShinseishaInfo(connection, shinseiInfo);
		}
	}
	//�ǉ� �����܂�---------------------------------------------------------

	//2007/5/7�ǉ�
	/**
	 * �����҃}�X�^�X�V�����擾����
	 * @return  �X�V��
	 */
	public String GetKenkyushaMeiboUpdateDate(UserInfo userInfo)
		throws ApplicationException
	{
		Connection connection = null;
		String strUpdateDate = "";

		try {
			connection = DatabaseUtil.getConnection();
			
			MasterKanriInfoDao mstDao = new MasterKanriInfoDao(userInfo);
			
			//�����҃}�X�^�X�V�����擾����
			strUpdateDate = mstDao.selectMeiboUpdateDate(connection);
			
		}catch(NoDataFoundException e){
			//�f�[�^���Ȃ��ꍇ�A��̍X�V����Ԃ�
		}catch(DataAccessException e){
			//���j���[��ʂ𐳏�ɕ\������ׁA�G���[�̏ꍇ����̍X�V����Ԃ�

			//throw new ApplicationException(
			//		"�����҃f�[�^��������DB�G���[���������܂����B",
			//		new ErrorInfo("errors.4004"),
			//		e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		return strUpdateDate;
	}
	
    /**
     * �����Җ���_�E�����[�h�f�[�^���擾����
     * @param userInfo
     * @return
     * @throws ApplicationException
     */
	public List searchMeiboCsvData(UserInfo userInfo)   throws ApplicationException {

	    //DB�R�l�N�V�����̎擾
	    Connection connection = null;    
	    try{
	        connection = DatabaseUtil.getConnection();
	        
	        //---�\�����ꗗ�y�[�W���
	        List csvList = null;
	        try {
	        	MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
	            csvList = dao.selectKenkyushaMeiboInfo(connection);
	        } catch (DataAccessException e) {
	            throw new ApplicationException(
	                "�����Җ���f�[�^��������DB�G���[���������܂����B",
	                new ErrorInfo("errors.4004"),
	                e);
	        }
	        return csvList;
	    
	    } finally {
	        DatabaseUtil.closeConnection(connection);
	    }
	}
}
