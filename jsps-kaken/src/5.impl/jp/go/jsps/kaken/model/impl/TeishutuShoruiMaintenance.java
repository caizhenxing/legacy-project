/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : TeishutuShoruiMaintenance.java
 *    Description : ���̈�ԍ����s�E�̈�v�揑�쐬�E�̈�v�揑��o�m�F�i����̈挤���j�Ǘ����s���N���X�B
 *
 *    Author      : DIS
 *    Date        : 2006/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/14    V1.0        DIS            �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.IPdfConvert;
import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.model.IShozokuMaintenance;
import jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance;
import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.exceptions.DuplicateKeyException;
import jp.go.jsps.kaken.model.dao.impl.JigyoKanriInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKenkyushaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.RyoikiKeikakushoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.ShinseiDataInfoDao;
import jp.go.jsps.kaken.model.dao.select.SelectUtil;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.FileIOException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemBusyException;
import jp.go.jsps.kaken.model.exceptions.TransactionException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.KenkyushaPk;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoSystemInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.status.StatusGaiyoManager;
import jp.go.jsps.kaken.status.StatusManager;
import jp.go.jsps.kaken.util.CsvUtil;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.util.RandomPwd;
import jp.go.jsps.kaken.util.SendMailer;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.util.DateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���̈�ԍ����s�E�̈�v�揑�쐬�E�̈�v�揑��o�m�F�i����̈挤���j�Ǘ����s���N���X�B
 * 
 * ID RCSfile="$RCSfile: TeishutuShoruiMaintenance.java,v $"
 * Revision="$Revision: 1.13 $"
 * Date="$Date: 2007/07/25 07:56:37 $"
 */
public class TeishutuShoruiMaintenance implements ITeishutuShoruiMaintenance {
    
    /** ���̈�폜�t���O�i�폜�ς݁j */
    public static final String FLAG_APPLICATION_DELETE     = "1";

    //add start ly 2006/06/19
    /** ���O */
    protected static Log log = LogFactory.getLog(TeishutuShoruiMaintenance.class);

    /**
     * ���发�ނ̒�o���t�@�C���i�[�t�H���_ .<br /><br />
     * ��̓I�Ȓl�́A"<b>${shinsei_path}/work/oubo/</b>"<br />
     */
    private static String OUBO_WORK_FOLDER = ApplicationSettings.getString(ISettingKeys.OUBO_WORK_FOLDER);

    /**
     * ���发�ނ̒�o��Word�t�@�C���i�[�t�H���_.<br /><br />
     * ��̓I�Ȓl�́A"<b>${shinsei_path}/settings/oubo/</b>"<br />
     */
    private static String OUBO_FORMAT_PATH = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_PATH);

    /**
     * ���发�ނ̒�o��Word�t�@�C����.<br /><br />
     *
     * ��̓I�Ȓl(���)�́A"<b>kiban.doc</b>"<br />
     * ��̓I�Ȓl(����̈�)�́A"<b>tokutei.doc</b>"<br />
     * ��̓I�Ȓl(���X�^�[�g�A�b�v)�́A"<b>wakate.doc</b>"<br />
     * ��̓I�Ȓl(���ʌ������i��)�́A"<b>shokushinhi.doc</b>"<br />
     */
    private static String OUBO_FORMAT_FILE_NAME_TOKUTEI = ApplicationSettings.getString(ISettingKeys.OUBO_FORMAT_FILE_NAME_TOKUTEI_SHINKI);
    //add end ly 2006/06/19
    
//  2006/06/27 �ǉ��@���`�؁@��������
    /** ���[���T�[�o�A�h���X */
    private static final String SMTP_SERVER_ADDRESS = 
                                ApplicationSettings.getString(ISettingKeys.SMTP_SERVER_ADDRESS); 
    /** ���o�l ���ꂵ�ĂP�� */
    private static final String FROM_ADDRESS = 
                                ApplicationSettings.getString(ISettingKeys.FROM_ADDRESS);
    
    /** ���[�����e�i����҂����̈�ԍ����s����o�^�����Ƃ��j�u�����v */
    private static final String SUBJECT_KARIRYOIKINO_KAKUNIN_IRAI = 
                                ApplicationSettings.getString(ISettingKeys.SUBJECT_KARIRYOIKINO_KAKUNIN_IRAI);

    /** ���[�����e�i����҂����̈�ԍ����s����o�^�����Ƃ��j�u�{���v */
    private static final String CONTENT_KARIRYOIKINO_KAKUNIN_IRAI = 
                                ApplicationSettings.getString(ISettingKeys.CONTENT_KARIRYOIKINO_KAKUNIN_IRAI); 
//�@2006/06/27 �ǉ��@���`�؁@�����܂�
    
//  2006/06/29 �ǉ��@zjp�@��������
    /** �����F�\�������ߐ؂�����܂ł̓��t */
    protected static final int DATE_BY_KAKUNIN_TOKUSOKU = 
        ApplicationSettings.getInt(ISettingKeys.DATE_BY_KAKUNIN_TOKUSOKU);

    /** ���̈�ԍ��ő僊�g���C�� */
    protected static final int KARIRYOIKI_NO_MAX_RETRY_COUNT = 
        ApplicationSettings.getInt(ISettingKeys.SYSTEM_NO_MAX_RETRY_COUNT);

    /** ���[�����e�i�����@�֒S���҂ւ̖����F�m�F�ʒm�j�u�����v */
    protected static final String SUBJECT_SHINSEISHO_KAKUNIN_TOKUSOKU = 
        ApplicationSettings.getString(ISettingKeys.SUBJECT_SHINSEISHO_KAKUNIN_TOKUSOKU);
        
    /** ���[�����e�i�����@�֒S���҂ւ̖����F�m�F�ʒm�j�u�{���v */
    protected static final String CONTENT_SHINSEISHO_KAKUNIN_TOKUSOKU = 
        ApplicationSettings.getString(ISettingKeys.CONTENT_SHINSEISHO_KAKUNIN_TOKUSOKU);
    
    /** �����F�\�������ߐ؂�����܂ł̓��t */
    protected static final int DATE_BY_SHONIN_TOKUSOKU = 
        ApplicationSettings.getInt(ISettingKeys.DATE_BY_SHONIN_TOKUSOKU);
    
    /** ���[�����e�i�����@�֒S���҂ւ̖����F�m�F�ʒm�j�u�����v */
    protected static final String SUBJECT_RYOIKIGAIYO_SHONIN_TOKUSOKU = 
        ApplicationSettings.getString(ISettingKeys.SUBJECT_RYOIKIGAIYO_SHONIN_TOKUSOKU);
        
    /** ���[�����e�i�����@�֒S���҂ւ̖����F�m�F�ʒm�j�u�{���v */
    protected static final String CONTENT_RYOIKIGAIYO_SHONIN_TOKUSOKU = 
        ApplicationSettings.getString(ISettingKeys.CONTENT_RYOIKIGAIYO_SHONIN_TOKUSOKU);
// �@2006/06/29 �ǉ��@zjp�@�����܂�

 //2006/06/16 by jzx add start   
    /**
     * �󗝓o�^���͎󗝉����̏����擾����B
     * @param userInfo  UserInfo
     * @param pkInfo    RyoikikeikakushoPk
     * @return �̈�v�揑�i�T�v�j���Ǘ�(RyoikikeikakushoInfo)
     * @throws NoDataFoundException
     * @throws DataAccessException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#selectRyoikikeikakushoInfo(jp.go.jsps.kaken.model.vo.UserInfo, RyoikiKeikakushoPk)
     */
    public RyoikiKeikakushoInfo selectRyoikikeikakushoInfo(
            UserInfo userInfo,
            RyoikiKeikakushoPk pkInfo
            ) throws DataAccessException,
                     NoDataFoundException,
                     ApplicationException {

        // DB�R�l�N�V�����̎擾
        Connection connection = null;
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;

        // �ȈՐ\�����
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao.selectRyoikiKeikakushoInfo(connection, pkInfo);
        }catch (SystemBusyException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
                    "errors.4004"), se);
        } 
        // �\���󋵖����Z�b�g
        finally {
            DatabaseUtil.closeConnection(connection);
        }
        return ryoikikeikakushoInfo;
    }

    /**
     * �󗝓o�^�i��o���ށj�B
     * @param userInfo  UserInfo
     * @param pkInfo    RyoikikeikakushoPk
     * @param juriKekka String
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registTeisyutusyoJuri(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo,
            String juriKekka
            ) throws ApplicationException, NoDataFoundException {
        // DB�R�l�N�V�����̎擾
        Connection connection = null;
        boolean success = false;

        // �r������̂��ߊ����f�[�^���擾����
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = new RyoikiKeikakushoInfo();
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk();
        ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();

        // �\���f�[�^�Ǘ�DAO
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(
                userInfo);
        ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
        try {
            // DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();
            pkInfo.setRyoikiSystemNo(ryoikiInfo.getRyoikiSystemNo());
            ryoikikeikakushoInfoDao.checkKenkyuNoInfo(connection, pkInfo);
            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao
                    .selectRyoikiKeikakushoInfoForLock(connection, pkInfo);
            // ---�̈�v�揑�i�T�v�j���Ǘ��f�[�^�폜�t���O�`�F�b�N---
            String delFlag = ryoikikeikakushoInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("���Y�̈�v�揑���f�[�^�͍폜����Ă��܂��B",
                        new ErrorInfo("errors.9001"));
            }

            // ---DB�X�V---
            // �̈�v�揑�T�v�����ID
            ryoikikeikakushoInfo.setRyoikiJokyoId(juriKekka);
            // �����@�֏��F��
            ryoikikeikakushoInfo.setJyuriDate(new Date());
            shinseiInfo.setJokyoId(ryoikikeikakushoInfo.getRyoikiJokyoId());
            shinseiInfo.setRyouikiNo(ryoikikeikakushoInfo.getKariryoikiNo());
            shinseiInfo.setJokyoIds(ryoikiInfo.getJokyoIds());
            shinseiInfo.setJyuriDate(new Date());
            // shinseiInfo.setSaishinseiFlg("0"); //SAISHINSEI_FLG �Đ\���t���O

            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection,
                    ryoikikeikakushoInfo);
            shinseiDao.updateShinseis(connection, shinseiInfo, juriKekka);
            success = true;
        }
        catch (SystemBusyException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
                    "errors.4004"), se);
        }
        catch (DataAccessException e) {
            throw new ApplicationException("�r���擾����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        }
        finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }
    
    /**
     * �󗝉����i��o���ށj�B
     * @param  userInfo
     * @param  searchInfo
     * @param  pkInfo
     * @return void 
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void cancelTeisyutusyoJuri(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo
            ) throws NoDataFoundException, ApplicationException {

        // DB�R�l�N�V�����̎擾
        Connection connection = null;
        boolean success = false;

        // �r������̂��ߊ����f�[�^���擾����
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = new RyoikiKeikakushoInfo();
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk();
        ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();

        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = 
            new RyoikiKeikakushoInfoDao(userInfo);
        ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao(userInfo);
        try {
            // DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();
            pkInfo.setRyoikiSystemNo(ryoikiInfo.getRyoikiSystemNo());
            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao
                    .selectRyoikiKeikakushoInfoForLock(connection, pkInfo);

            //---�̈�v�揑�i�T�v�j���Ǘ��f�[�^�폜�t���O�`�F�b�N---
            String delFlag = ryoikikeikakushoInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("���Y�̈�v�揑���f�[�^�͍폜����Ă��܂��B",
                        new ErrorInfo("errors.9001"));
            }

            //---DB�X�V---
            ryoikikeikakushoInfo
                    .setRyoikiJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);// �̈�v�揑�T�v�����ID
            ryoikikeikakushoInfo.setJyuriDate(null); // �����@�֏��F��
            shinseiInfo.setJokyoId(ryoikikeikakushoInfo.getRyoikiJokyoId());//�̈�v�揑�i�T�v�j�\����ID
            shinseiInfo.setRyouikiNo(ryoikikeikakushoInfo.getKariryoikiNo());//���̈�ԍ�
            shinseiInfo.setJokyoIds(ryoikiInfo.getJokyoIds());//�\����ID�z��
            shinseiInfo.setJyuriDate(null);//�w�U�󗝓�
            shinseiInfo.setJyuriDateFlg("1");//�w�U�󗝓�Flg
            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection,
                    ryoikikeikakushoInfo);

            String status = ryoikikeikakushoInfo.getRyoikiJokyoId();
            shinseiDao.updateShinseis(connection, shinseiInfo, status);
            success = true;
        }
        catch (SystemBusyException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
                    "errors.4004"), se);
        }
        catch (DataAccessException e) {
            throw new ApplicationException("�r���擾����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        }
        finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }
    
    /**
     * �̈�������v�撲���m����s��
     * @param  userInfo
     * @param  ryoikiGaiyoForm
     * @return void
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void kakuteiRyoikiGaiyo(
            UserInfo userInfo,
            ShinseiDataInfo shinseiDataInfo
            ) throws NoDataFoundException, ApplicationException{

        //DB�R�l�N�V�����̎擾
        Connection connection = null;   
        boolean success = false;
        ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);
        try {
            connection = DatabaseUtil.getConnection();
            shinseiDataInfoDao.selectCheckKakuteiSaveInfo(connection, shinseiDataInfo);

            //�\���f�[�^�Ǘ��e�[�u���̝r������
            ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();
            shinseiInfo.setRyouikiNo(shinseiDataInfo.getRyouikiNo());//�̈�ԍ�
            shinseiInfo.setJokyoIds(shinseiDataInfo.getJokyoIds());//�����ID
            shinseiInfo.setDelFlg("0");//�����@�֏��F��

            //DB�X�V����
            String status = StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI;
            shinseiInfo.setSaishinseiFlg("0");
            shinseiInfo.setRyoikiKakuteiDate(new Date());
            shinseiDataInfoDao.updateShinseis(connection, shinseiInfo, status);

            //�̈�v�揑�i�T�v�j���Ǘ�
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
            RyoikiKeikakushoPk ryoikiPk = new RyoikiKeikakushoPk();
            ryoikiPk.setRyoikiSystemNo(shinseiDataInfo.getSystemNo());
            RyoikiKeikakushoInfo ryoikiInfo = ryoikikeikakushoInfoDao
                    .selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);

            //---�̈�v�揑�i�T�v�j���Ǘ��f�[�^�폜�t���O�`�F�b�N---
            String delFlag = ryoikiInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("���Y�̈�v�揑���f�[�^�͍폜����Ă��܂��B",
                        new ErrorInfo("errors.9001"));
            }
            ryoikiInfo.setRyoikikeikakushoKakuteiFlg("1");//�m��t���O
            ryoikiInfo.setKakuteiDate(new Date());//�̈�v�揑�m�����ݒ�

            //---DB�X�V---
            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection,ryoikiInfo);
            success = true;
        }catch (SystemBusyException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
            "errors.4004"), se);
        }catch (DataAccessException e) {
            throw new ApplicationException("���X�V����DB�G���[���������܂����B",
                new ErrorInfo("errors.4001"), e);
        }finally {
            if (success) {
                DatabaseUtil.commit(connection);
            } else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    } 

    /**
     * �̈�������v�撲���m������B
     * @param  userInfo
     * @param  ryoikiGaiyoForm
     * @return void
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void cancelKakuteiRyoikiGaiyo(
            UserInfo userInfo, 
            ShinseiDataInfo shinseiDataInfo
            ) throws  NoDataFoundException, ApplicationException {

        //DB�R�l�N�V�����̎擾
        Connection connection = null;   
        boolean success = false;
        ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);
        try {
            connection = DatabaseUtil.getConnection();

            //�\���f�[�^�Ǘ��e�[�u���̝r������
            ShinseiDataInfo shinseiInfo = new ShinseiDataInfo();
            shinseiInfo.setRyouikiNo(shinseiDataInfo.getRyouikiNo());//�̈�ԍ�
            shinseiInfo.setJokyoIds(shinseiDataInfo.getJokyoIds());//�����ID
            shinseiInfo.setDelFlg("0");//�폜�t���O 

            //DB�X�V����
            String status = StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN;
            shinseiInfo.setRyoikiKakuteiDate(null);
            shinseiInfo.setRyoikiKakuteiDateFlg("1");
            shinseiDataInfoDao.updateShinseis(connection, shinseiInfo, status);

            //�̈�v�揑�i�T�v�j���Ǘ�
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
            RyoikiKeikakushoPk ryoikiPk = new RyoikiKeikakushoPk();
            ryoikiPk.setRyoikiSystemNo(shinseiDataInfo.getSystemNo());
            RyoikiKeikakushoInfo ryoikiInfo = ryoikikeikakushoInfoDao
                    .selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);

            //---�̈�v�揑�i�T�v�j���Ǘ��f�[�^�폜�t���O�`�F�b�N---
            String delFlag = ryoikiInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("���Y�̈�v�揑���f�[�^�͍폜����Ă��܂��B",
                        new ErrorInfo("errors.9001"));
            }
            if(ryoikiInfo.getRyoikiJokyoId().equals(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN)||
                    ryoikiInfo.getRyoikiJokyoId().equals(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA)){
                ryoikiInfo.setRyoikiJokyoId(StatusCode.STATUS_SAKUSEITHU);
            }
            ryoikiInfo.setRyoikikeikakushoKakuteiFlg("0");//�m��t���O 
            ryoikiInfo.setKakuteiDate(null);//�̈�v�揑�m�����ݒ�

            //---DB�X�V---
            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection,ryoikiInfo);
            success = true;
        }catch (SystemBusyException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
            "errors.4004"), se);
        }catch (DataAccessException e) {
            throw new ApplicationException("���X�V����DB�G���[���������܂����B",
                new ErrorInfo("errors.4001"), e);
        }finally {
            if (success) {
                DatabaseUtil.commit(connection);
            } else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }
//2006/06/16 by jzx add end
    
    //  ���� 2006.6.15 ��������
    /**
     * ��єԍ����X�g�p�̃f�[�^���擾����B
     * @param userInfo  UserInfo
     * @return list
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#selectAllTeishutuShorui(jp.go.jsps.kaken.model.vo.UserInfo)
     */
    public List selectTeisyutusyoTobiSinkiList(
            UserInfo userInfo,
            TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo
            ) throws NoDataFoundException, ApplicationException {

        // DB�R�l�N�V�����̎擾
        Connection connection = null;
        List list = null;
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoinfoDao = new RyoikiKeikakushoInfoDao(userInfo);
            list = ryoinfoDao.selectAllTeishutuShorui(connection, teishutsuShoruiSearchInfo);
        } catch (SystemBusyException se) {
            throw new ApplicationException("�������s���ɃG���[���������܂����B",
                    new ErrorInfo("errors.4004"), se);
        } catch(NoDataFoundException e){
            throw new NoDataFoundException("�Y�������񂪑��݂��܂���ł����B",
                    new ErrorInfo("errors.5002"), e);
        } catch(DataAccessException de){
            throw new ApplicationException("��������DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"),de); 
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        return list;
    }
    // ���� 2006.6.15 �����܂�
    
    //  2006/06/15 lwj add start
    /**
     * ��o���ވꗗ�\���p�̃f�[�^���擾����B
     * @param userInfo ���s���郆�[�U���
     * @param teishutsuShoruiSearchInfo
     * @return List���
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List selectTeishutuShoruiList(UserInfo userInfo,
            TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo
            )throws NoDataFoundException, ApplicationException {

        //DB�R�l�N�V�����̎擾
        Connection connection = null;       
        List result = null;
        try{
            connection = DatabaseUtil.getConnection(); 
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao =
                new RyoikiKeikakushoInfoDao(userInfo);
            result = ryoikikeikakushoInfoDao.selectAllTeishutuShorui(
                    connection, teishutsuShoruiSearchInfo);
            new StatusGaiyoManager(userInfo).setRyoikiStatusName(result);
        } catch (SystemBusyException se) {
            throw new ApplicationException("DB�A�N�Z�X�����ŃG���[���������܂����B",
                    new ErrorInfo("errors.4000"), se);
        } catch(NoDataFoundException e){
            throw new NoDataFoundException("�Y�������񂪑��݂��܂���ł����B",
                    new ErrorInfo("errors.5002"), e);
        } catch(DataAccessException de){
            throw new ApplicationException("��������DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"),de); 
        } finally {
            DatabaseUtil.closeConnection(connection);
        }          
        return result;
    }
    // 2006/06/15 lwj add end
    
   //  2006/06/15 �{�@��������
    /**
     * ��o�m�F�i����̈挤��(�V�K�̈�)�j�ꗗ�����擾����B
     * @param userInfo ���s���郆�[�U���
     * @param ryoikiInfo
     * @return List ��o�m�F�p�̃f�[�^
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public List searchTeisyutuKakuninList(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException {

        Connection connection = null;
        List list=null;
        try{
           connection = DatabaseUtil.getConnection();
           RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
           list = ryoikikeikakushoInfoDao.searchRyoikiInfoList(connection, ryoikiInfo);
           new StatusGaiyoManager(userInfo).setRyoikiStatusName(list);
        }catch (SystemBusyException se) {
            throw new ApplicationException("DB�A�N�Z�X�����ŃG���[���������܂���",
                    new ErrorInfo("errors.4000"), se);
        }catch(NoDataFoundException e){
            throw new NoDataFoundException("�Y�������񂪑��݂��܂���ł����B",
                    new ErrorInfo("errors.5002"), e);
        }catch(DataAccessException de){
            throw new ApplicationException("DB�A�N�Z�X�����ŃG���[���������܂����B",
                    new ErrorInfo("errors.4000"), de);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }       
        return list;
    }    
    //  2006/06/15 �{�@�����܂�


    //add start ly 2006/06/15
    /**
     * �ȈՉ��̈�ԍ����s�����擾.<br><br>
     * �@�����̈�ԍ����s�m�F�Ɖ��̈�ԍ����s�p���m�F��ʂŎg�p<br><br>
     * ���N���X��selectRyoikikeikakushoInfo(UserInfo,ryoikiPk)���\�b�h���ĂԁB<br>
     * �����ɁA������userInfo�Ƒ�����ryoikiPko���i�[�����z��(ryoikiPk)��n���B<br><br>
     * �擾����RyoikikeikakushoInfo��ԋp����B<br><br>
     * 
     * @param userInfo  UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @return �ȈՉ��̈���(RyoikikeikakushoInfo)
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#sryoikiInfo(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.ShinseiDataPk)
     */
    public RyoikiKeikakushoInfo selectRyoikiInfo(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException {

        // DB�R�l�N�V�����̎擾
        Connection connection = null;
        RyoikiKeikakushoInfo ryoikiInfo = null;
        try {
            connection = DatabaseUtil.getConnection();

            //---�ȈՉ��̈���
            RyoikiKeikakushoInfoDao ryoikiDao = new RyoikiKeikakushoInfoDao(
                    userInfo);
            ryoikiInfo = ryoikiDao.selectRyoikiKeikakushoInfo(connection,
                    ryoikiPk);
        } catch (NoDataFoundException e) {
            throw new NoDataFoundException("���Y������͊��ɍ폜����Ă��܂��B",
                    new ErrorInfo("errors.9001"), e);
        } catch (DataAccessException ex) {
            throw new ApplicationException("�������s���ɃG���[���������܂����B",
                    new ErrorInfo("errors.4004"), ex);
        }catch (SystemBusyException se) {
            throw new ApplicationException("�������s���ɃG���[���������܂����B", new ErrorInfo(
            "errors.4004"), se);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        return ryoikiInfo;
    }
    //add end ly 2006/06/16
    
    //add start ly 2006/06/16
    /**
     * ���̈�ԍ����s�m�F�����s�i�����@�֒S���ҁj
     * �X�V����������ɍs��ꂽ�Ƃ��A�R�~�b�g���s���B<br>
     * �@DatabaseUtil�N���X��commit()���\�b�h�ɂāA�R�~�b�g����B<br><br>
     * ���̈�ԍ����s�m�F����B�i�����@�֒S���ҁj ������A�\RYOIKI_JOKYO_ID=33�ɍX�V�����B
     * �X�V�����̓r���ŗ�O�����������Ƃ��A���[���o�b�N���s���B<br>
     * �@DatabaseUtil�N���X��rollback()���\�b�h�ɂāA���[���o�b�N����B<br><br>
     * 
     * @param userInfo          UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#shoninApplication(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk)
     */
    public void confirmKariBangoHakko(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException {

        Connection connection = null;
        RyoikiKeikakushoInfo ryoikiInfo = null;
        boolean success = false;
        // �\���f�[�^�Ǘ�DAO
        RyoikiKeikakushoInfoDao ryoikiDao = new RyoikiKeikakushoInfoDao(userInfo);

        try {
            // DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();
            ryoikiInfo = ryoikiDao.selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);

            // ---�̈�v�揑�i�T�v�j���Ǘ��f�[�^�폜�t���O�`�F�b�N---
            String delFlag = ryoikiInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException(
                        "���Y�̈�v�揑���͊��ɍ폜����Ă��܂��BRYOIKI_SYSTEM_NO="
                                + ryoikiPk.getRyoikiSystemNo(),
                                new ErrorInfo("errors.9025"));
            }

            //---DB�X�V---
            ryoikiInfo.setRyoikiJokyoId(StatusCode.STATUS_KARIRYOIKINO_HAKKUZUMI); // �i�����ID�j

            int existCount = 0;// DB�ŊY�����s�������̈�ԍ��̌���
            int retryCount = 0;// ���g���C��

            // ���s�����ԍ���DB��0���łȂ���΁A���̈�ԍ��̔��s���ő僊�g���C����J��Ԃ�
            do {
                ryoikiInfo.setKariryoikiNo(RandomPwd.generate(true, false,true, 5)); // ���̈�ԍ������s
                existCount = ryoikiDao.KariryoikiNoCount(connection, ryoikiInfo);
                retryCount++;
            } while (existCount != 0 && retryCount < KARIRYOIKI_NO_MAX_RETRY_COUNT);

            // ���s�����̏ꍇ�A�o�^����
            if (existCount == 0) {
                ryoikiDao.updateRyoikiKeikakushoInfo(connection, ryoikiInfo);
                success = true;

            // ���s���s�̏ꍇ�A�G���[���\��
            } else {
                throw new ApplicationException(
                        "���s���悤�Ƃ��鉼�̈�ԍ������łɓo�^����Ă��邽�߁A���̈�ԍ������s�ł��܂���B",
                        new ErrorInfo("errors.9029"));
            }
        }catch (DataAccessException e) {
            throw new ApplicationException("�̈�v�揑�i�T�v�j���X�V����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4001"), e);
        }catch (SystemBusyException e) {
            throw new ApplicationException("�̈�v�揑�i�T�v�j���Ǘ��f�[�^�擾����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        }finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }

    /**
     * �X�V����������ɍs��ꂽ�Ƃ��A�R�~�b�g���s���B<br>
     * DatabaseUtil�N���X��commit()���\�b�h�ɂāA�R�~�b�g����B<br>
     * <br>
     * ���̈�ԍ����s�p���m�F����B�i�����@�֒S���ҁj ������A�\RYOIKI_JOKYO_ID=32�ɍX�V�����B
     * �X�V�����̓r���ŗ�O�����������Ƃ��A���[���o�b�N���s���B<br>
     * DatabaseUtil�N���X��rollback()���\�b�h�ɂāA���[���o�b�N����B<br>
     * <br>
     * 
     * @param userInfo UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @return �Ȃ�
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#shoninApplication(jp.go.jsps.kaken.model.vo.UserInfo,
     *      jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk)
     */
    public void rejectKariBangoHakko(UserInfo userInfo, RyoikiKeikakushoPk ryoikiPk)
            throws NoDataFoundException, ApplicationException {

        Connection connection = null;
        RyoikiKeikakushoInfo ryoikiInfo = null;
        boolean success = false;

        RyoikiKeikakushoInfoDao ryoikiDao = new RyoikiKeikakushoInfoDao(userInfo);
        try {
            // DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();

            ryoikiInfo = ryoikiDao.selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);

            // ---�̈�v�揑�i�T�v�j���Ǘ��f�[�^�폜�t���O�`�F�b�N---
            String delFlag = ryoikiInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException(
                        "���Y�̈�v�揑���͊��ɍ폜����Ă��܂��BRYOIKI_SYSTEM_NO="
                                + ryoikiPk.getRyoikiSystemNo(), new ErrorInfo(
                                "errors.9025"));
            }

            //---DB�X�V---
            ryoikiInfo.setRyoikiJokyoId(StatusCode.STATUS_KARIRYOIKINO_HAKKUKYAKKA); // �i�����ID�j���̈�ԍ����s�p��
            ryoikiDao.updateRyoikiKeikakushoInfo(connection, ryoikiInfo);
            success = true;

        }catch (DataAccessException e) {
            throw new ApplicationException("�̈�v�揑�i�T�v�j���X�V����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4001"), e);
        }catch (SystemBusyException e) {
            throw new ApplicationException("�̈�v�揑�i�T�v�j���Ǘ��f�[�^�擾����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        }finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }

    /**
     * ���发�ނ̒�o���o�́i��Ռ������A����̈挤���j
     * @param UserInfo userInfo ���O�C���ҏ��
     * @param checkInfo �`�F�b�N���X�g��������
     * @return�@FileResource�@�o�͏��CSV�t�@�E��
     */
    public FileResource createOuboTeishutusho(
            UserInfo userInfo,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws ApplicationException {

        //DB���R�[�h�擾
        List csv_data = null;
        Connection connection = null;
        RyoikiKeikakushoInfoDao ryoikiDao = new RyoikiKeikakushoInfoDao(userInfo);
        try {
            //DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();
            csv_data = ryoikiDao.createTokuteiShinki(connection,ryoikiInfo);
        }catch (ApplicationException e) {
            throw e;
        } finally {
            DatabaseUtil.closeConnection(connection);
        }

        //-----------------------
        // CSV�t�@�C���o��
        //-----------------------
        String csvFileName = "tokutei_shinki";
        String filepath = null;
        synchronized(log){
            filepath = OUBO_WORK_FOLDER + userInfo.getShozokuInfo().getShozokuTantoId()
                     + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";   
        }
        CsvUtil.output(csv_data, filepath, csvFileName);

        //-----------------------
        // �˗����t�@�C���̃R�s�[
        //-----------------------
        FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + OUBO_FORMAT_FILE_NAME_TOKUTEI),
                  new File(filepath + OUBO_FORMAT_FILE_NAME_TOKUTEI));
        FileUtil.fileCopy(new File(OUBO_FORMAT_PATH + "$5"), new File(filepath + "$"));
        
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
        } finally{
            //��ƃt�@�C���̍폜
            FileUtil.delete(exe_file.getParentFile());
        }

        //���ȉ𓀌^���k�t�@�C�������^�[��
        return compFileRes;
    }
    //add end ly 2006/06/16

//  add start zjp 2006/06/15
    /**
     * ���发�ޏ����擾.<br><br>
     * �@�����发�މ�ʂŎg�p<br><br>
     * 
     * ���N���X��searchSyonin(UserInfo,ryoikiPk)���\�b�h���ĂԁB<br>
     * �����ɁA������userInfo�Ƒ�����ryoikiPko���i�[�����z��(ryoikiPk)��n���B<br><br>
     * 
     * �擾����RyoikikeikakushoInfo��ԋp����B<br><br>
     * 
     * @param userInfo  UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @return ���发�ޏ��F���(RyoikikeikakushoInfo)
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#searchSyonin(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk)
     */
    public RyoikiKeikakushoInfo searchOuboSyoruiInfo(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikikeikakushoInfo)
            throws NoDataFoundException, ApplicationException {

        // DB�R�l�N�V�����̎擾
        Connection connection = null;

        // ---���发�ޏ��F���
        RyoikiKeikakushoInfo ryoikiInfo = null;
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(
                    userInfo);
            ryoikiInfo = ryoikikeikakushoInfoDao.searchSyoninInfo(connection,
                    ryoikiPk, ryoikikeikakushoInfo);
        } catch (DataAccessException e) {
            throw new ApplicationException("��������DB�G���[���������܂����B", new ErrorInfo(
                    "errors.4004"), e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        return ryoikiInfo;
    }

    /**
     * ���发�ނ����F.<br>
     * �����发�ޏ��F��ʂŎg�p<br>
     * ���N���X��searchSyonin(UserInfo,ryoikiPk)���\�b�h���ĂԁB<br>
     * �����ɁA������userInfo�Ƒ�����ryoikiPko���i�[�����z��(ryoikiPk)��n���B<br>
     * <br>
     * @param userInfo UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#searchSyonin(jp.go.jsps.kaken.model.vo.UserInfo,
     *      jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk)
     */
    public void approveOuboSyorui(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException {

        //DB�R�l�N�V�����̎擾
        Connection connection = null;   
        boolean success = false; 
        String kariryoikiNo = null;
        
        //�r������̂��ߊ����f�[�^���擾����
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;
        
        //�\���f�[�^�Ǘ�DAO
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
        ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);
        try {
            // DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();            
            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao.selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);   
            kariryoikiNo = ryoikikeikakushoInfo.getKariryoikiNo();
            ryoikikeikakushoInfoDao.searchSystemNoList(connection,kariryoikiNo,ryoikiInfo);

            // ---�̈�v�揑�i�T�v�j���Ǘ��f�[�^�폜�t���O�`�F�b�N---
            String delFlag = ryoikikeikakushoInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("���Y�̈�v�揑���f�[�^�͍폜����Ă��܂��B",
                        new ErrorInfo("errors.9001"));
            }
  
            // ---DB�X�V---
            ryoikikeikakushoInfo.setRyoikiJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU); // �̈�v�揑�T�v�����ID 
            ryoikikeikakushoInfo.setCancelFlg(StatusCode.SAISHINSEI_FLG_DEFAULT);      // Cancel�t���O 
            ryoikikeikakushoInfo.setShoninDate(new Date());                            // �����@�֏��F��                                     // �����@�֏��F��
            ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();
            String status = StatusCode.STATUS_GAKUSIN_SHORITYU;                        // �����ID
            shinseiDataInfo.setRyouikiNo(kariryoikiNo);                                // ���̈�ԍ�
            shinseiDataInfo.setJokyoIds(ryoikiInfo.getJokyoIds());                      
        
            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection, ryoikikeikakushoInfo);
            shinseiDataInfoDao.updateShinseis(connection,shinseiDataInfo,status);
            success = true;
        }
        catch (NoDataFoundException e) {
            throw e;
        }
        catch (DataAccessException e) {
            throw new ApplicationException("�r���擾����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        }       
        finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }       
    }
    
    /**
     * ���发�ނ��p��.<br>
     * �@�����发�ދp����ʂŎg�p<br>
     * 
     * ���N���X��searchKyakkaSave(UserInfo,ryoikiPk)���\�b�h���ĂԁB<br>
     * �����ɁA������userInfo�Ƒ�����ryoikiPko���i�[�����z��(ryoikiPk)��n���B<br><br>
     * 
     * @param userInfo  UserInfo
     * @param RyoikiKeikakushoPk ryoikiPk
     * @throws NoDataFoundException
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ITeishutuShoruiMaintenance#searchSyonin(jp.go.jsps.kaken.model.vo.UserInfo, jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk)
     */
    public void rejectOuboSyorui(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws NoDataFoundException, ApplicationException {

        // DB�R�l�N�V�����̎擾
        Connection connection = null;
        boolean success = false;
        String kariryoikiNo = null;

        // �r������̂��ߊ����f�[�^���擾����
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;

        // �\���f�[�^�Ǘ�DAO
        RyoikiKeikakushoInfoDao ryoikiDao = new RyoikiKeikakushoInfoDao(userInfo);
        ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);

        try {
            // DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();
            ryoikikeikakushoInfo = ryoikiDao.selectRyoikiKeikakushoInfoForLock(connection, ryoikiPk);
            kariryoikiNo = ryoikikeikakushoInfo.getKariryoikiNo();
            ryoikiDao.searchSystemNoList(connection, kariryoikiNo, ryoikiInfo);

            // ---�̈�v�揑�i�T�v�j���Ǘ��f�[�^�폜�t���O�`�F�b�N---
            String delFlag = ryoikikeikakushoInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("���Y�̈�v�揑���f�[�^�͍폜����Ă��܂��B",
                        new ErrorInfo("errors.9001"));
            }

            // ---DB�X�V---
            ryoikikeikakushoInfo.setRyoikiJokyoId(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA); // �̈�v�揑�T�v�����ID
            ryoikikeikakushoInfo.setShoninDate(null); // �����@�֏��F��
            ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();
            String status = StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI; // �����ID
            shinseiDataInfo.setRyouikiNo(kariryoikiNo); // ���̈�ԍ�
            shinseiDataInfo.setJokyoIds(ryoikiInfo.getJokyoIds());

            ryoikiDao.updateRyoikiKeikakushoInfo(connection,ryoikikeikakushoInfo);
            shinseiDataInfoDao.updateShinseis(connection, shinseiDataInfo,status);
            success = true;
        }catch (NoDataFoundException e) {
            throw e;
        }catch (DataAccessException e) {
            throw new ApplicationException("�r���擾����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        }finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }
    
    /**
     * �m�F���[�������ʒm���M���s
     * 
     * @param userInfo
     * @throws ApplicationException
     */
    public void sendMailKakuninTokusoku(UserInfo userInfo)
            throws ApplicationException {

        List jigyoList = null;
        Connection connection = null;

        //���F���ߐ؂�����̐ݒ�
        DateUtil du = new DateUtil();
        du.addDate(DATE_BY_KAKUNIN_TOKUSOKU);   //�w����t�����Z����
        Date date = du.getCal().getTime();

        try {
            //DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();
            //���ƊǗ�DAO
            RyoikiKeikakushoInfoDao ryoikidao = new RyoikiKeikakushoInfoDao(userInfo);
            try {
                jigyoList = ryoikidao.selectKakuninTokusokuInfo(connection, DATE_BY_KAKUNIN_TOKUSOKU);
            } catch (NoDataFoundException e) {
                //�����������Ȃ�
            } catch (DataAccessException e) {
                throw new ApplicationException("���F�m�F���[�������ʒm���M���ƃf�[�^�擾����DB�G���[���������܂����B",
                        new ErrorInfo("errors.4004"), e);
            }
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        connection = null;

        //�Y�����鎖�Ƃ����݂��Ȃ������ꍇ
        if (jigyoList == null || jigyoList.size() == 0) {
            String strDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
            String msg = "��W���ߐ؂肪[" + strDate + "]�̎��ƂŁA���F�m�F�̐\�����������Ƃ͑��݂��܂���B";
            log.info(msg);
            return;
        }

        //-----�f�[�^�\����ϊ��i�d���f�[�^���܂ޒP�ꃊ�X�g���珊���@�֒S���҂��Ƃ̃}�b�v�֕ϊ�����j
        //�S��Map
        Map saisokuMap = new HashMap();
        for (int i = 0; i < jigyoList.size(); i++) {

            //1���R�[�h
            Map recordMap = (Map) jigyoList.get(i);
            String nendo = (String) recordMap.get("NENDO");
            BigDecimal kaisu = (BigDecimal) recordMap.get("KAISU");
            String jigyo_name = (String) recordMap.get("JIGYO_NAME");
            String tanto_email = (String) recordMap.get("TANTO_EMAIL");

            String kaisu_hyoji = "";
            if (kaisu.intValue() > 1) {
                kaisu_hyoji = "��" + kaisu + "�� "; //�񐔂�1��ȏ�̏ꍇ�͕\������
            }

            //���Ɩ����u�N�x�{���Ɩ��v�֕ϊ����A�₢���킹����i���s�{�S�p�󔒁j�Ō����B
// UPDATE START 2007-07-06 BIS ���u��
//            String jigyo_info = new StringBuffer("�y������ږ��z����")
//                                            .append(nendo)
//                                            .append("�N�x ")
//                                            .append(kaisu_hyoji)
//                                            .append(jigyo_name)
//                                            .toString();
            String jigyo_info = new StringBuffer("    ����")
								            .append(nendo)
								            .append("�N�x ")
								            .append(kaisu_hyoji)
								            .append(jigyo_name)
								            .toString();
//�@UPDATE END 2007-07-06 BIS ���u��
            //�S��Map�ɓ��Y�����@�֒S���҃f�[�^�����݂��Ă����ꍇ
            if (saisokuMap.containsKey(tanto_email)) {
                List dataList = (List) saisokuMap.get(tanto_email);
                dataList.add(jigyo_info); //�����R�[�h�Ɏ��Ə��
            } else {
                //���̎��Ƃ̏ꍇ   
                List dataList = new ArrayList();
                dataList.add(tanto_email); //1���R�[�h�ڂɃ��[���A�h���X
                dataList.add(jigyo_info); //2���R�[�h�ڂɎ��Ə��
                saisokuMap.put(tanto_email, dataList);
            }
        }

        //---------------
        // ���[�����M
        //---------------
        //-----���[���{���t�@�C���̓ǂݍ���
        String content = null;
        try {
            File contentFile = new File(CONTENT_SHINSEISHO_KAKUNIN_TOKUSOKU);
            FileResource fileRes = FileUtil.readFile(contentFile);
            content = new String(fileRes.getBinary());
        } catch (FileNotFoundException e) {
            log.warn("���[���{���t�@�C����������܂���ł����B", e);
            return;
        } catch (IOException e) {
            log.warn("���[���{���t�@�C���ǂݍ��ݎ��ɃG���[���������܂����B", e);
            return;
        }

        //���F���ߐ؂���t�t�H�[�}�b�g��ύX����
        String kigenDate = new StringBuffer("����")
                                .append(new DateUtil(date).getNendo())
                                .append("�N")
                                .append(new SimpleDateFormat("M��d��").format(date))
                                .toString();

        //-----���[�����M�i�P�l�����M�j
        for (Iterator iter = saisokuMap.keySet().iterator(); iter.hasNext();) {

            //�����@�֒S���҂��Ƃ̃f�[�^���X�g���擾����
            String tantoEmail = (String) iter.next();
            List dataList = (List) saisokuMap.get(tantoEmail);

            //���[���A�h���X���ݒ肳��Ă��Ȃ��ꍇ�͏������΂�
            String to = (String) dataList.get(0);
            if (to == null || to.length() == 0) {
                continue;
            }

            //-----���[���{���t�@�C���̓��I���ڕύX
// UPDATE START 2007-07-24 BIS ���u��
//            StringBuffer jigyoNameList = new StringBuffer("\n");
//            for (int i = 1; i < dataList.size(); i++) {
//            	jigyoNameList.append(dataList.get(i)).append("\n");
//            }
            StringBuffer jigyoNameList = new StringBuffer("");
            for (int i = 1; i < dataList.size(); i++) {
            	jigyoNameList.append("\n");
            	jigyoNameList.append(dataList.get(i));
            }
// UPDATE END 2007-07-24 BIS ���u��
            String[] param = new String[] { kigenDate, //���F���ߐ؂���t
                                    jigyoNameList.toString()//���Ɩ����X�g
                                };
            String body = MessageFormat.format(content, param);

            if (log.isDebugEnabled()){
                log.debug("���M���F********************\n" + body);
            }
            try {
                SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
                mailer.sendMail(FROM_ADDRESS, //���o�l
                        to, //to
                        null, //cc
                        null, //bcc
                        SUBJECT_SHINSEISHO_KAKUNIN_TOKUSOKU, //����
                        body); //�{��
            } catch (Exception e) {
                log.warn("���[�����M�Ɏ��s���܂����B", e);
                continue;
            }
        }
    }
    
    /**
     * ���F�����[�������ʒm���M���s
     * ����̈挤�����Ƃ̂�
     * @param userInfo
     * @throws ApplicationException
     */
    public void sendMailShoninTokusoku(UserInfo userInfo)
            throws ApplicationException {

        List jigyoList = null;
        Connection connection = null;

        //���F���ߐ؂�����̐ݒ�
        DateUtil du = new DateUtil();
        du.addDate(DATE_BY_KAKUNIN_TOKUSOKU);   //�w����t�����Z����
        Date date = du.getCal().getTime();

        try {
            //DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikidao = new RyoikiKeikakushoInfoDao(userInfo);
            try {
                jigyoList = ryoikidao.selectShoninTokusokuInfo(connection, DATE_BY_SHONIN_TOKUSOKU);
            } catch (NoDataFoundException e) {
                //�����������Ȃ�
            } catch (DataAccessException e) {
                throw new ApplicationException("���F�����[�������ʒm���M���ƃf�[�^�擾����DB�G���[���������܂����B",
                        new ErrorInfo("errors.4004"), e);
            }
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        connection = null;

        //�Y�����鎖�Ƃ����݂��Ȃ������ꍇ
        if (jigyoList == null || jigyoList.size() == 0) {
            String strDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
            String msg = "��W���ߐ؂肪[" + strDate + "]�̎��ƂŁA���F���̐\�����������Ƃ͑��݂��܂���B";
            log.info(msg);
            return;
        }

        //-----�f�[�^�\����ϊ��i�d���f�[�^���܂ޒP�ꃊ�X�g���珊���@�֒S���҂��Ƃ̃}�b�v�֕ϊ�����j
        //�S��Map
        Map saisokuMap = new HashMap();
        for (int i = 0; i < jigyoList.size(); i++) {

            //1���R�[�h
            Map recordMap = (Map) jigyoList.get(i);
            String nendo = (String) recordMap.get("NENDO");
            BigDecimal kaisu = (BigDecimal) recordMap.get("KAISU");
            String jigyo_name = (String) recordMap.get("JIGYO_NAME");
            String tanto_email = (String) recordMap.get("TANTO_EMAIL");

            String kaisu_hyoji = "";
            if (kaisu.intValue() > 1) {
                kaisu_hyoji = "��" + kaisu + "�� "; //�񐔂�1��ȏ�̏ꍇ�͕\������
            }

            //���Ɩ����u�N�x�{���Ɩ��v�֕ϊ����A�₢���킹����i���s�{�S�p�󔒁j�Ō����B
// UPDATE START 2007-07-06 ���u��
//            String jigyo_info = new StringBuffer("�y������ږ��z����")
//                                            .append(nendo)
//                                            .append("�N�x ")
//                                            .append(kaisu_hyoji)
//                                            .append(jigyo_name)
//                                            .toString();
            String jigyo_info = new StringBuffer("    ����")
								            .append(nendo)
								            .append("�N�x ")
								            .append(kaisu_hyoji)
								            .append(jigyo_name)
								            .toString();
// UPDATE END 2007-07-06 ���u��
            //�S��Map�ɓ��Y�����@�֒S���҃f�[�^�����݂��Ă����ꍇ
            if (saisokuMap.containsKey(tanto_email)) {
                List dataList = (List) saisokuMap.get(tanto_email);
                dataList.add(jigyo_info); //�����R�[�h�Ɏ��Ə��
            } else {
                //���̎��Ƃ̏ꍇ   
                List dataList = new ArrayList();
                dataList.add(tanto_email); //1���R�[�h�ڂɃ��[���A�h���X
                dataList.add(jigyo_info); //2���R�[�h�ڂɎ��Ə��
                saisokuMap.put(tanto_email, dataList);
            }
        }

        //---------------
        // ���[�����M
        //---------------
        //-----���[���{���t�@�C���̓ǂݍ���
        String content = null;
        try {
            File contentFile = new File(CONTENT_RYOIKIGAIYO_SHONIN_TOKUSOKU);
            FileResource fileRes = FileUtil.readFile(contentFile);
            content = new String(fileRes.getBinary());
        } catch (FileNotFoundException e) {
            log.warn("���[���{���t�@�C����������܂���ł����B", e);
            return;
        } catch (IOException e) {
            log.warn("���[���{���t�@�C���ǂݍ��ݎ��ɃG���[���������܂����B", e);
            return;
        }

        //���F���ߐ؂���t�t�H�[�}�b�g��ύX����
        String kigenDate = new StringBuffer("����")
                                .append(new DateUtil(date).getNendo())
                                .append("�N")
                                .append(new SimpleDateFormat("M��d��").format(date))
                                .toString();

        //-----���[�����M�i�P�l�����M�j
        for (Iterator iter = saisokuMap.keySet().iterator(); iter.hasNext();) {

            //�����@�֒S���҂��Ƃ̃f�[�^���X�g���擾����
            String tantoEmail = (String) iter.next();
            List dataList = (List) saisokuMap.get(tantoEmail);

            //���[���A�h���X���ݒ肳��Ă��Ȃ��ꍇ�͏������΂�
            String to = (String) dataList.get(0);
            if (to == null || to.length() == 0) {
                continue;
            }

            //-----���[���{���t�@�C���̓��I���ڕύX
// UPDATE START 2007-07-24 BIS ���u��
//            StringBuffer jigyoNameList = new StringBuffer("\n");
//            for (int i = 1; i < dataList.size(); i++) {
//            	jigyoNameList.append(dataList.get(i)).append("\n");
//            }
			StringBuffer jigyoNameList = new StringBuffer("");
			for (int i = 1; i < dataList.size(); i++) {
				jigyoNameList.append("\n");
				jigyoNameList.append(dataList.get(i));
			}
// UPDATE END 2007-07-24 BIS ���u��
            String[] param = new String[] { kigenDate, //���F���ߐ؂���t
                                    jigyoNameList.toString() //���Ɩ����X�g
                                };
            String body = MessageFormat.format(content, param);

            if (log.isDebugEnabled()){
                log.debug("���M���F********************\n" + body);
            }
            try {
                SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
                mailer.sendMail(FROM_ADDRESS, //���o�l
                        to, //to
                        null, //cc
                        null, //bcc
                        SUBJECT_RYOIKIGAIYO_SHONIN_TOKUSOKU, //����
                        body); //�{��
            } catch (Exception e) {
                log.warn("���[�����M�Ɏ��s���܂����B", e);
                continue;
            }
        }
    }
// add end zjp 2006/06/15
    
// �{ 2006/06/19 ��������
    /**
     * ���̈�ԍ����s����o�^����
     * 
     * @param userInfo ���[�U���
     * @param addInfo
     * @param pkInfo
     * @return
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void registKariBangoHakkoInfo(UserInfo userInfo,JigyoKanriPk pkInfo)
            throws NoDataFoundException,ApplicationException {

        // DB�R�l�N�V�����̎擾
        Connection connection = null;  

        if ( log.isDebugEnabled() ){
            log.debug("���̈�ԍ����s����o�^�J�n");
        }
        
        //2006/07/17 start
        String jigyoId = pkInfo.getJigyoId();
        ShinseishaInfo addInfo = userInfo.getShinseishaInfo();

        String chNendo = jigyoId.substring(0, 2);
        chNendo = "20" + chNendo; //2000�N�ȍ~�ł悢
        if (StringUtil.isDigit(chNendo)){
            addInfo.setNenrei("" + DateFormat.getAgeOnApril1st(
                    addInfo.getBirthday(),StringUtil.parseInt(chNendo))); //�N��
        }else{
            //����s���ȏꍇ�́H
        }
        //2006/07/17 end
        
        //--------------------
        // �A���P�[�g�f�[�^�o�^
        //--------------------
        JigyoKanriInfo jigyoKanriInfo=null;
        boolean success = false;
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao =
                new RyoikiKeikakushoInfoDao(userInfo);

            // �f�[�^�����݂����ꍇ�A�G���[�Ƃ���
            ryoikikeikakushoInfoDao.isExistRyoikiInfo(connection, addInfo, pkInfo);
            // �̈�v�揑�T�v�̃e�[�u���Ƀf�[�^���}������A���������@�֒S���҈��Ɋm�F�˗��̃��[�������M�����B
            JigyoKanriInfoDao jigyoKanriInfoDao = 
                new JigyoKanriInfoDao(userInfo);
            jigyoKanriInfo = 
                jigyoKanriInfoDao.selectJigyoKanriInfo(connection, pkInfo);
            RyoikiKeikakushoInfo ryoikiKeikakushoInfo=new RyoikiKeikakushoInfo();
            ryoikiKeikakushoInfo.setRyoikiSystemNo(ShinseiMaintenance.getSystemNumberForGaiyo());
            
            ryoikiKeikakushoInfo.setJigyoId(jigyoKanriInfo.getJigyoId());
            ryoikiKeikakushoInfo.setNendo(jigyoKanriInfo.getNendo());
            ryoikiKeikakushoInfo.setKaisu(jigyoKanriInfo.getKaisu());
            ryoikiKeikakushoInfo.setJigyoName(jigyoKanriInfo.getJigyoName());           
            ryoikiKeikakushoInfo.setShinseishaId(addInfo.getShinseishaId());
            ryoikiKeikakushoInfo.setSakuseiDate(new Date());
            ryoikiKeikakushoInfo.setNameKanjiSei(addInfo.getNameKanjiSei());
            ryoikiKeikakushoInfo.setNameKanjiMei(addInfo.getNameKanjiMei());
            ryoikiKeikakushoInfo.setNameKanaSei(addInfo.getNameKanaSei());
            ryoikiKeikakushoInfo.setNameKanaMei(addInfo.getNameKanaMei());
            ryoikiKeikakushoInfo.setNenrei(addInfo.getNenrei());         
            ryoikiKeikakushoInfo.setKenkyuNo(addInfo.getKenkyuNo());
            ryoikiKeikakushoInfo.setShozokuCd(addInfo.getShozokuCd());
            ryoikiKeikakushoInfo.setShozokuName(addInfo.getShozokuName());
            ryoikiKeikakushoInfo.setShozokuNameRyaku(addInfo.getShozokuNameRyaku());
            ryoikiKeikakushoInfo.setBukyokuCd(addInfo.getBukyokuCd());
            ryoikiKeikakushoInfo.setBukyokuName(addInfo.getBukyokuName());
            ryoikiKeikakushoInfo.setBukyokuNameRyaku(addInfo.getBukyokuNameRyaku());
            ryoikiKeikakushoInfo.setShokushuCd(addInfo.getShokushuCd());
            ryoikiKeikakushoInfo.setShokushuNameKanji(addInfo.getShokushuNameKanji());
            ryoikiKeikakushoInfo.setShokushuNameRyaku(addInfo.getShokushuNameRyaku());
            ryoikiKeikakushoInfo.setRyoikiJokyoId(StatusCode.STATUS_KARIRYOIKINO_KAKUNINMATI);
            ryoikiKeikakushoInfo.setRyoikikeikakushoKakuteiFlg(IShinseiMaintenance.FLAG_RYOIKIKEIKAKUSHO_NOT_KAKUTEI); 
            ryoikiKeikakushoInfo.setCancelFlg(IShinseiMaintenance.FLAG_RYOIKIKEIKAKUSHO_NOT_CANCEL); 
            ryoikiKeikakushoInfo.setDelFlg(IShinseiMaintenance.FLAG_RYOIKIKEIKAKUSHO_NOT_CANCEL); 
            
//          ADD START 2007/07/23 BIS ����
            ryoikiKeikakushoInfo.setZennendoOuboFlg(pkInfo.getZennendoOuboFlg());
            ryoikiKeikakushoInfo.setZennendoOuboNo(pkInfo.getZennendoOuboNo());
            ryoikiKeikakushoInfo.setZennendoOuboRyoikiRyaku(pkInfo.getZennendoOuboRyoikiRyaku());
            ryoikiKeikakushoInfo.setZennendoOuboSettei(pkInfo.getZennendoOuboSettei());
            //ADD END 2007/07/23 BIS ���� 
            
            ryoikikeikakushoInfoDao.insertRyoikiKeikakushoInfo(connection,ryoikiKeikakushoInfo);
            
            // �o�^����I��
            success = true;
        } catch (DuplicateKeyException se) {
            throw new ApplicationException("�������s���ɃG���[���������܂����B",
                    new ErrorInfo("errors.4007",new String[] { "���̈�ԍ����s���" }),se);
        } catch (SystemBusyException se) {
            throw new ApplicationException("�������s���ɃG���[���������܂����B",
                                           new ErrorInfo("errors.4004"), se);
        } catch(DataAccessException de){
            throw new ApplicationException("DB�A�N�Z�X�����ŃG���[���������܂���",
                    new ErrorInfo("errors.4000"), de);
        } finally {
            try {
                if (success) {
                    DatabaseUtil.commit(connection);
                } else {
                    DatabaseUtil.rollback(connection);
                }
            } catch (TransactionException e) {
                throw new ApplicationException("DB�A�N�Z�X�����ŃG���[���������܂���",
                        new ErrorInfo("errors.4000"), e);
            } finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
//      2006/06/27 �ǉ��@���`�؁@��������
        //---------------
        // ���[�����M
        //---------------
        //-----���[�����o�l���擾
        String to = null;
        try{
            //���Y�\���҂̏����@�֒S���ҏ����擾����
            String shozokuCd = addInfo.getShozokuCd();
            IShozokuMaintenance shozokuMainte = new ShozokuMaintenance();
            List shozokuTantoList = shozokuMainte.searchShozokuInfo(userInfo, 
                                                                    shozokuCd);
            //�����S���ҏ�񂪎擾�ł��Ȃ������ꍇ
            if(shozokuTantoList == null || shozokuTantoList.size() == 0){
                log.warn("�����@�֒S���ҏ����擾�ł��܂���ł����B�����R�[�h:"+shozokuCd);
                return;
            }

            //���X�g�̂P�l�ڂ̏����擾����i�S����Email1�ɑ΂��Ă̂ݑ��M����j
            to = (String)( (Map)shozokuTantoList.get(0) ).get("TANTO_EMAIL");

        }catch(ApplicationException e){
            log.warn("���[��������擾�Ɏ��s���܂����B", e);
            return;
        }

        //-----���[���{���t�@�C���̓ǂݍ���
        String content = null;
        try{
            File contentFile = new File(CONTENT_KARIRYOIKINO_KAKUNIN_IRAI);
            FileResource fileRes = FileUtil.readFile(contentFile);
            content = new String(fileRes.getBinary());
        }catch(FileNotFoundException e){
            log.warn("���[���{���t�@�C����������܂���ł����B", e);
            return;
        }catch(IOException e){
            log.warn("���[���{���t�@�C���ǂݍ��ݎ��ɃG���[���������܂����B",e);
            return;
        }
        
//2006/07/31 �c�@�ǉ���������
        //���ƔN�x�̐ݒ�
        String nendo = "";
        if(jigyoKanriInfo.getKaisu().equals("1")){
            nendo = "����"+ jigyoKanriInfo.getNendo() +"�N�x";
        } else {
            nendo = "����" + jigyoKanriInfo.getNendo() + "�N�x" + " ��" + jigyoKanriInfo.getKaisu()
                    + "�� ";
        }
        //-----���[���{���t�@�C���̓��I���ڕύX
        String[] param = new String[]{
            nendo,
//2006/07/31 �c�@�ǉ������܂�            
            jigyoKanriInfo.getJigyoName(), //���Ɩ�
            addInfo.getNameKanjiSei(),     //�\���Җ��|��
            addInfo.getNameKanjiMei(),     //�\���Җ��|��                   
        };
        content = MessageFormat.format(content, param);
        
        if (log.isDebugEnabled()){
            log.debug("content:" + content);
        }

        //-----���[�����M
        try{
            SendMailer mailer = new SendMailer(SMTP_SERVER_ADDRESS);
            mailer.sendMail(FROM_ADDRESS,                       //���o�l
                            to,                                 //to
                            null,                               //cc
                            null,                               //bcc
                            SUBJECT_KARIRYOIKINO_KAKUNIN_IRAI,  //����
                            content);                           //�{��
        }catch(Exception e){
            log.warn("���[�����M�Ɏ��s���܂����B",e);
            return;
        }
//2006/06/27 �ǉ��@���`�؁@�����܂�
    }
    
//  mcj add start       
    /**
     * �ꊇ��(��o����)�����s
     * 
     * @param  userInfo
     * @param  searchInfo
     * @return �Ȃ� 
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void executeIkkatuJuri(UserInfo userInfo, 
            TeishutsuShoruiSearchInfo searchInfo)
            throws DataAccessException,
            NoDataFoundException,
            ApplicationException {

        Connection connection = null;   
        boolean success = false;
        
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = new RyoikiKeikakushoInfo();
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk();
        
        // �\���f�[�^�Ǘ�DAO
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
        ShinseiDataInfoDao shinseiDao = new ShinseiDataInfoDao (userInfo);
        MasterKenkyushaInfoDao mKenkyuDao = new MasterKenkyushaInfoDao (userInfo);
        List ryoikiResult =  null;
        try {
            // DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();
            ryoikiResult = ryoikikeikakushoInfoDao.selectAllTeishutuShorui(connection, searchInfo);
        }
        catch (DataAccessException e) {
            throw new ApplicationException("�̈�v�揑�i�T�v�j���Ǘ��f�[�^�r���擾����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"), e);
        }
        try{
          for(int i=0; i<ryoikiResult.size(); i++){
              HashMap hashMap = (HashMap)ryoikiResult.get(i);
              //�����̌��ʂ̎��
              String ryoikiSystemNo = (String)hashMap.get("RYOIKI_SYSTEM_NO");  //�V�X�e����t�ԍ�
              String kariryoikiNo = (String)hashMap.get("KARIRYOIKI_NO");       //���̈�ԍ�
              
              pkInfo.setRyoikiSystemNo(ryoikiSystemNo);

              //�̈�v�揑���Q�Ƃ��邱�Ƃ��ł��邩�`�F�b�N����
              ryoikikeikakushoInfo = ryoikikeikakushoInfoDao
                        .selectRyoikiKeikakushoInfoForLock(connection, pkInfo);
              
              ryoikikeikakushoInfo.setRyoikiJokyoId(StatusCode.STATUS_GAKUSIN_JYURI );//�����=06
              ryoikikeikakushoInfo.setJyuriDate(new Date());                          //�V�X�e�����t

              //�̈�v�揑�i�T�v�j��񂪍X�V 
              ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection,ryoikikeikakushoInfo);
              
              ryoikikeikakushoInfo.setJokyoIds(searchInfo.getSearchJokyoId());

              //�w�肳�ꂽ���ƃR�[�h�̐\���f�[�^�Ǘ��e�[�u���iSHINSEIDATAKANRI�j�̃V�X�e����t�ԍ����擾����B
              List sysNo = ryoikikeikakushoInfoDao.searchSystemNoList(
                        connection, kariryoikiNo, ryoikikeikakushoInfo);
              
              for(int j=0; j<sysNo.size(); j++){
                  HashMap sysNomap = (HashMap)sysNo.get(j);
                  String SNo = (String)sysNomap.get("SYSTEM_NO"); 
                  ShinseiDataPk shinseiPk = new ShinseiDataPk();
                  shinseiPk.setSystemNo(SNo);
                  
                  ShinseiDataInfo shinseiInfo=shinseiDao.selectShinseiDataInfo(connection, shinseiPk, true);
                  //�����҃}�X�^�̑��݃`�F�b�N�����{
                  KenkyushaPk kenkyushaPk=new KenkyushaPk();
                  kenkyushaPk.setKenkyuNo(shinseiInfo.getDaihyouInfo().getKenkyuNo());
                  kenkyushaPk.setShozokuCd(shinseiInfo.getDaihyouInfo().getShozokuCd());
                  try{
                	  //���݃`�F�b�N�Ȃ̂ŁA�r�����䂪�s�v�ł��B 2007/6/7
                      //mKenkyuDao.selectKenkyushaInfo(connection,kenkyushaPk,true);
                      mKenkyuDao.selectKenkyushaInfo(connection,kenkyushaPk,false);
                  }catch(NoDataFoundException e){
                      throw new NoDataFoundException(
                            "�Y�������҃}�X�^�Ɉȉ��̌����҂����݂��܂���'",
                            new ErrorInfo("errors.4004"), e);
                  }
                  
                  shinseiInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);
                  shinseiInfo.setJyuriDate(new Date());                  
                  shinseiDao.updateShinseiDataInfo(connection, shinseiInfo, true);
              }
          }
          success=true;
        }finally {
            if(success){
                DatabaseUtil.commit(connection);
            }else{
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }

    /**
     * ���F����(��o����)���s���B
     * 
     * @param userInfo
     * @param ryoikiPk
     * @param pkInfo
     * @return �Ȃ�
     * @throws DataAccessException
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void cancelTeisyutusyoSyonin(
            UserInfo userInfo,
            RyoikiKeikakushoPk ryoikiPk,
            RyoikiKeikakushoInfo ryoikiInfo)
            throws DataAccessException,
                   NoDataFoundException,
                   ApplicationException {

        Connection connection = null;
        boolean success = false;
        // �r������̂��ߊ����f�[�^���擾����
        RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;

        // �̈�v�揑�i�T�v�j���Ǘ�DAO�Ɛ\�����f�[�^DAO
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
        ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(userInfo);

        try {
            // DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();

            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao.selectRyoikiKeikakushoInfoForLock(
                    connection, ryoikiPk);
            // ---�̈�v�揑�i�T�v�j���Ǘ��f�[�^�폜�t���O�`�F�b�N---
            String delFlag = ryoikikeikakushoInfo.getDelFlg();
            if (FLAG_APPLICATION_DELETE.equals(delFlag)) {
                throw new ApplicationException("���Y�̈�v�揑���f�[�^�͍폜����Ă��܂��B",
                        new ErrorInfo("errors.9001"));
            }
            // ---DB�X�V---
            ryoikikeikakushoInfo.setCancelFlg(StatusCode.SAISHINSEI_FLG_SAISHINSEITYU); // Cancel�t���O
            ryoikikeikakushoInfo.setRyoikiJokyoId(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU); // �̈�v�揑�T�v�����ID
//mengchuanjun 2006/07/27 add start 
            ryoikikeikakushoInfo.setShoninDate(null);
//mengchuanjun 2006/07/27 add end                 
            ShinseiDataInfo shinseiDataInfo = new ShinseiDataInfo();
            shinseiDataInfo.setRyouikiNo(ryoikikeikakushoInfo.getKariryoikiNo());
            shinseiDataInfo.setJokyoIds(ryoikiInfo.getJokyoIds());
//mengchuanjun 2006/07/27 add start 
            //shinseiDataInfo.setShoninDate(null); // �������ԏ��F�� 
//mengchuanjun 2006/07/27 add end             
            shinseiDataInfo.setShoninDateMark("1");
            String status = StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE; // �����ID
            ryoikikeikakushoInfoDao.updateRyoikiKeikakushoInfo(connection, ryoikikeikakushoInfo);
            shinseiDataInfoDao.updateShinseis(connection, shinseiDataInfo, status);
            success = true;

        }
        catch (DataAccessException e) {
            throw new ApplicationException("�̈�v�揑�i�T�v�j���X�V����DB�G���[���������܂����B",
                    new ErrorInfo("errors.4001"), e);
        }
        finally {
            try {
                if (success) {
                    DatabaseUtil.commit(connection);
                }
                else {
                    DatabaseUtil.rollback(connection);
                }
            }
            catch (TransactionException e) {
                throw new ApplicationException("�̈�v�揑�i�T�v�j���Ǘ��f�[�^DB�o�^���ɃG���[���������܂����B",
                        new ErrorInfo("errors.4001"), e);
            }
            finally {
                DatabaseUtil.closeConnection(connection);
            }
        }
    }
    
    /**
     * �̈�v�揑�폜�����擾����B
     * 
     * @param userInfo
     * @param ryoikiSystemNo
     * @return RyoikiKeikakushoInfo
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public RyoikiKeikakushoInfo getRyoikiGaiyoDeleteInfo(
            UserInfo userInfo,
            String ryoikiSystemNo)
            throws NoDataFoundException, 
                   ApplicationException {

        // DB�R�l�N�V�����̎擾
        Connection connection = null;
        RyoikiKeikakushoInfo ryoikiKeikakushoInfo = new RyoikiKeikakushoInfo();
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
        
        // �ȈՐ\�����
        try {
            connection = DatabaseUtil.getConnection();
            ryoikiKeikakushoInfo = ryoikikeikakushoInfoDao.selectInfo(connection, ryoikiSystemNo);
            new StatusGaiyoManager(userInfo).setRyoikiStatusName(ryoikiKeikakushoInfo);
        }catch (NoDataFoundException se) {
            throw new ApplicationException(se.getMessage(), new ErrorInfo(
                    "errors.4004"), se);
        } 
        catch (DataAccessException e) {
            throw new ApplicationException("DB�G���[���������܂����B", new ErrorInfo(
                    "errors.4001"), e);
        }   
        finally {
            DatabaseUtil.closeConnection(connection);
        }
        return ryoikiKeikakushoInfo;
    }

    /**
     * �̈�v�揑�폜�����s����B
     * 
     * @param  userInfo
     * @param  ryoikiSystemNo
     * @return �Ȃ� 
     * @throws NoDataFoundException
     * @throws ApplicationException
     */
    public void deleteFlagRyoikiGaiyo(UserInfo userInfo, String ryoikiSystemNo) 
            throws NoDataFoundException, ApplicationException{
        
        // DB�R�l�N�V�����̎擾        
        Connection connection = null;
        boolean success = false;

        RyoikiKeikakushoInfo ryoikikeikakushoInfo = new RyoikiKeikakushoInfo();
        RyoikiKeikakushoPk pkInfo = new RyoikiKeikakushoPk();
        pkInfo.setRyoikiSystemNo(ryoikiSystemNo);     
        
        RyoikiKeikakushoInfoDao ryoikikeikakushoInfoDao = new RyoikiKeikakushoInfoDao(userInfo);
       
        try{
            //DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();

            //�`���`�F�b�N
            ryoikikeikakushoInfo = ryoikikeikakushoInfoDao
                    .selectRyoikiKeikakushoInfoForLock(connection, pkInfo);
            String ryoikiJokyoId = ryoikikeikakushoInfo.getRyoikiJokyoId();
            if (!(ryoikiJokyoId.equals(StatusCode.STATUS_KARIRYOIKINO_KAKUNINMATI)
                    || ryoikiJokyoId.equals(StatusCode.STATUS_KARIRYOIKINO_HAKKUKYAKKA)
                    || ryoikiJokyoId.equals(StatusCode.STATUS_KARIRYOIKINO_HAKKUZUMI)
                    || ryoikiJokyoId.equals(StatusCode.STATUS_SAKUSEITHU)
                    || ryoikiJokyoId.equals(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN)
                    || ryoikiJokyoId.equals(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA))){

                throw new ApplicationException("�`���`�F�b�N�ŃG���[������܂��A�폜�ł��܂���B",
                        new ErrorInfo("errors.5066"));
            }
            ryoikikeikakushoInfoDao.existCount(connection,ryoikikeikakushoInfo);

            //---DB�X�V---
            ryoikikeikakushoInfoDao.deleteFlagRyoikiKeikakushoInfo(connection, ryoikiSystemNo);
            success = true;
        }
        catch (NoDataFoundException e) {
            throw new NoDataFoundException("�Y�������񂪑��݂��܂���ł����B",
                    new ErrorInfo("errors.5002"), e);
        } 
        catch (DuplicateKeyException e) {
            throw new ApplicationException("�`���`�F�b�N�ŃG���[������܂��A�폜�ł��܂���B",
                    new ErrorInfo("errors.5066"));
        }
        catch (DataAccessException e) {
            throw new ApplicationException("DB�G���[���������܂����B",
                    new ErrorInfo("errors.4001"), e);
        }
        finally {
            if (success) {
                DatabaseUtil.commit(connection);
            }
            else {
                DatabaseUtil.rollback(connection);
            }
            DatabaseUtil.closeConnection(connection);
        }
    }
//mcj add end

//  2006/06/21 dyh add start
    /**    
     * �̈�v�揑�ƌ����v�撲���̈ꗗ���擾����B
     * @param userInfo ���[�U���
     * @param kariryoikiNo ���̈�ԍ�
     * @return List �̈�v�揑�ƌ����v�撲���̈ꗗ
     * @throws ApplicationException
     */
    public List getRyoikiAndKenkyuList(
            UserInfo userInfo,
            String kariryoikiNo)
            throws ApplicationException {

        // DB�R�l�N�V�����̎擾
        Connection connection = null;
        List resultList = new ArrayList(2);
        try {
            // DB�R�l�N�V�����̎擾
            connection = DatabaseUtil.getConnection();

            // �̈�v�揑�ꗗ���擾����
            RyoikiKeikakushoInfoDao ryoikiInfoDao = 
                new RyoikiKeikakushoInfoDao(userInfo);
            String shinseishaId = userInfo.getShinseishaInfo()
                    .getShinseishaId();
            List gaiyoList = ryoikiInfoDao
                    .selectRyoikiKeikakushoInfo(connection, shinseishaId);
            new StatusGaiyoManager(userInfo).setRyoikiStatusName(gaiyoList);
            resultList.add(gaiyoList);

            // �����v�撲���̈ꗗ���擾����
            String ryoikiNo = kariryoikiNo;
            if (StringUtil.isBlank(kariryoikiNo)) {
                ryoikiNo = (String) ((HashMap) gaiyoList.get(0))
                        .get("KARIRYOIKI_NO");
            }
            if (StringUtil.isBlank(ryoikiNo)) {
                return resultList;
            }
            ShinseiDataInfoDao shinseiDataInfoDao = new ShinseiDataInfoDao(
                    userInfo);
            List keikakusyoList = shinseiDataInfoDao.selectKeikakuTyosyoList(
                    connection, ryoikiNo);
            new StatusManager(userInfo).setRyoikiStatusName(connection,
                    keikakusyoList);
            resultList.add(keikakusyoList);

            return resultList;
        } catch (DataAccessException de) {
            throw new ApplicationException("DB�G���[���������܂����B", new ErrorInfo(
                    "errors.4004"), de);
        } catch (NoDataFoundException ne) {
            return resultList;
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    /**
     * �̈�v�揑�\��PDF�t�@�C�����擾�B
     * 
     * @param userInfo
     * @param ryoikiSystemNo
     * @return
     * @throws ApplicationException
     */
    public FileResource getGaiyoCoverPdfFile(
            UserInfo userInfo,
            String ryoikiSystemNo)
            throws ApplicationException {

        String pdfPath = "";
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikidao =
                new RyoikiKeikakushoInfoDao(userInfo);

            // PDF�t�@�C���p�X�̎擾
            IPdfConvert pdfConvert = new PdfConvert();
            pdfConvert.convertGaiyoHyoshiPdf(connection, userInfo, ryoikiSystemNo);
            pdfPath = ryoikidao.selectHyoshiPdfPath(connection, ryoikiSystemNo);
        } catch (IOException e) {
            throw new ApplicationException("�̈�v�揑�\��PDF�̍쐬�ŃG���[���������܂���",
                    new ErrorInfo("errors.4002"), e);
        } catch (DataAccessException e) {
            throw new ApplicationException("�̈�v�揑�\��PDF�̍쐬�ŃG���[���������܂���",
                    new ErrorInfo("errors.4004"), e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }

        FileResource fileRes = null;
        File file = new File(pdfPath);
        try {
            fileRes = FileUtil.readFile(file);
        }
        catch (IOException e) {
            throw new FileIOException("�t�@�C���̓��͒��ɃG���[���������܂����B", e);
        }

        return fileRes;
    }

    /**
     * �̈�v�揑�m�FPDF�t�@�C�����擾�B
     * 
     * @param userInfo ���O�C���ҏ��
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @return FileResource PDF�t�@�C��
     * @throws ApplicationException
     */
    public FileResource getRyoikiGaiyoPdfFile(
            UserInfo userInfo,
            RyoikiKeikakushoPk pkInfo)
            throws ApplicationException{

        //===== PDF�ϊ��T�[�r�X���\�b�h�Ăяo�� =====
        IPdfConvert pdfConvert = new PdfConvert();
        return pdfConvert.getGaiyouResource(userInfo, pkInfo);  
    }

    /**
     * �̈�v�揑�i�T�v�j���Ǘ��e�[�u���ɁA�f�[�^�̑��݃`�F�b�N
     * ���O�C���҂̐\����ID�ƈ�v���ADEL_FLG�i�폜�t���O�j��[0]
     * @param userInfo
     * @return boolean
     * @throws ApplicationException
     */
    public boolean isExistRyoikiGaiyoInfo(UserInfo userInfo)
            throws ApplicationException {

        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            RyoikiKeikakushoInfoDao ryoikiInfoDao = new RyoikiKeikakushoInfoDao(
                    UserInfo.SYSTEM_USER);
            int infoCount = ryoikiInfoDao.selectCountByShinseishaId(
                    connection, userInfo.getShinseishaInfo());
            if (infoCount > 0) {
                return true;
            } else {
                return false;
            }
        }catch(DataAccessException e){
            throw new ApplicationException(
                    "�̈�v�揑�i�T�v�j���Ǘ��e�[�u����������DB�G���[���������܂����B",
                    new ErrorInfo("errors.4004"),
                    e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
//  2006/06/21 dyh end

//ADD�@START 2007/07/02 BIS ���� -->   
	
    /**    
     * �̈�v�揑���̈ꗗ���擾����B
     * 
     * @param searchInfo �̈�v�揑���
     * @return List �̈�v�揑�̏��
     * @throws ApplicationException
     */	
	public List getRyoikiResult(RyoikiKeikakushoSystemInfo searchInfo) throws ApplicationException {
		
        // �V�X�e����t�ԍ����w�肳��Ă��Ȃ��ꍇ��null��Ԃ�
        if (searchInfo == null) {
            throw new NoDataFoundException(
                    "�̈�v�揑�i�T�v�j���Ǘ��e�[�u���ɊY������f�[�^��������܂���B"
                    + "�����L�[�F�̈�v�揑�i�T�v�j���Ǘ�");
        }
        
        StringBuffer query = new StringBuffer();
        
        query.append("SELECT ");
        query.append(" A.RYOIKI_SYSTEM_NO,");
        query.append(" A.JIGYO_NAME,");
        query.append(" A.SHOZOKU_CD,");
        query.append(" A.SHOZOKU_NAME,");
        query.append(" A.KARIRYOIKI_NO,");
        query.append(" A.UKETUKE_NO,");
        query.append(" A.RYOIKI_NAME,");
        query.append(" A.BUKYOKU_NAME,");
        query.append(" A.SHOKUSHU_NAME_KANJI,");
        query.append(" A.NAME_KANJI_SEI,");
        query.append(" A.NAME_KANJI_MEI,");
        query.append(" A.EDITION,");
        query.append(" A.PDF_PATH,");
        query.append(" A.JYURI_DATE,");
        query.append(" B.SYSTEM_HYOJI");
        query.append(" FROM RYOIKIKEIKAKUSHOINFO").append(" A");
        query.append(" INNER JOIN MASTER_RYOIKI_STATUS").append(" B");
        query.append(" ON A.RYOIKI_JOKYO_ID = B.JOKYO_ID ");
        query.append(" INNER JOIN MASTER_JIGYO").append(" C");
        query.append(" ON C.JIGYO_CD = SUBSTR(A.JIGYO_ID,3,5) ");
        query.append(" WHERE C.JIGYO_CD = '00022' ");
        query.append(" AND B.KAIJYO_FLG = '0' ");
        query.append(" AND A.DEL_FLG = 0 ");
        
        if (!"".equals(searchInfo.getJigyoName()) && searchInfo.getJigyoName() != null ){
            query.append(" AND A.JIGYO_NAME = '");
            query.append(searchInfo.getJigyoName());
            query.append("'");	
        }
        if (!"".equals(searchInfo.getKariryoikiNo()) && searchInfo.getKariryoikiNo() != null ){
            query.append(" AND A.KARIRYOIKI_NO = '");
            query.append(searchInfo.getKariryoikiNo());
            query.append("'");
        }
        if (!"".equals(searchInfo.getNameKanjiSei()) && searchInfo.getNameKanjiSei() != null ){
            query.append(" AND A.NAME_KANJI_SEI like '%");
            query.append(searchInfo.getNameKanjiSei());
            query.append("%'");
        }
        if (!"".equals(searchInfo.getNameKanjiMei()) && searchInfo.getNameKanjiMei() != null ){
            query.append(" AND A.NAME_KANJI_MEI like '%");
            query.append(searchInfo.getNameKanjiMei());
            query.append("%'");
        }
        if (!"".equals(searchInfo.getShozokuName()) && searchInfo.getShozokuName() != null ){
            query.append(" AND A.SHOZOKU_NAME like '%");
            query.append(searchInfo.getShozokuName());
            query.append("%'");
        }
        if (!"".equals(searchInfo.getShozokuCd()) && searchInfo.getShozokuCd() != null ){
            query.append(" AND A.SHOZOKU_CD = '");
            query.append(searchInfo.getShozokuCd());
            query.append("'");
        }
        if (!"".equals(searchInfo.getRyoikiJokyoId()) && searchInfo.getRyoikiJokyoId() != null ){
            query.append(" AND A.RYOIKI_JOKYO_ID = '");
            query.append(searchInfo.getRyoikiJokyoId());
            query.append("'");
        }
        query.append(" ORDER BY A.SHOZOKU_CD,A.KARIRYOIKI_NO, A.UKETUKE_NO ");
        
        
        //printf Debug:
        if (log.isDebugEnabled()) {
            log.debug("query:" + query.toString());
        }
        //DB�R�l�N�V�����̎擾
        Connection connection = null;
        try {
        	connection = DatabaseUtil.getConnection();
        	return SelectUtil.select(connection, query.toString());
        } catch (DataAccessException e) {
            throw new ApplicationException(
                "�f�[�^��������DB�G���[���������܂����B",
                new ErrorInfo("errors.4004"),
                e);
        }finally {
        	DatabaseUtil.closeConnection(connection);
        }
	}
	

// ADD�@END�@ 2007/07/02 BIS ���� -->    
    
}