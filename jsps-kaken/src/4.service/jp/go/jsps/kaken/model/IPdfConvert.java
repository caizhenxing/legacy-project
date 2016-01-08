/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoPk;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;

/**
 * PDF�ϊ��EPDF�쐬���s���C���^�[�t�F�[�X�B
 * 
 * ID RCSfile="$RCSfile: IPdfConvert.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:49 $"
 */
public interface IPdfConvert {

	/**
	 * �\���f�[�^���A�Y�t�t�@�C�������IOD�t�@�C�����쐬���A�\���f�[�^�Ǘ��ɓo�^����B
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�\���f�[�^��L�[�B
	 * @throws ApplicationException	
	 * @throws ConvertException		�ϊ��Ɏ��s�����Ƃ��B
	 */
	public void shinseiDataConvert(
		UserInfo userInfo,
		ShinseiDataPk pkInfo)
		throws ApplicationException,ConvertException;
    
    //2004.07.03 zhangt add start
    /**
     * �̈�v�揑PDF�f�[�^���PDF�t�@�C�����쐬����B
     * IOD��PDF�ɕϊ����APDF�t�@�C���p�X��DB�ɏ������ށB
     * @param userInfo              ���s���郆�[�U���B
     * @param pkInfo                �\���f�[�^��L�[�B
     * @throws ApplicationException 
     * @throws ConvertException     �ϊ��Ɏ��s�����Ƃ��B
     */
    public void convertRyoikiGaiyoPdf(
            UserInfo userInfo, 
            RyoikiKeikakushoPk ryoikiKeikakushoPk)
        throws ApplicationException;
    //2004.07.03 zhangt add start
	
	/**
	 * �\���f�[�^���PDF�t�@�C�����쐬����B
	 * �쐬���ꂽPDF�t�@�C���ɂ́A���O�C�����[�U�̃p�X���[�h���b�N��������B
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�\���f�[�^��L�[�B
	 * @return						�\���f�[�^�t�@�C�����\�[�X
	 * @throws ApplicationException	�\���f�[�^�̎擾�Ɏ��s�����ꍇ�B
	 */
	public FileResource getShinseiFileResource(
		UserInfo userInfo,
		final ShinseiDataPk pkInfo)
		throws ApplicationException, NoDataFoundException, ConvertException;
	
	
	/**
	 * �\���f�[�^���PDF�t�@�C�����쐬����B
	 * �쐬���ꂽPDF�t�@�C���ɂ́A�p�X���[�h���b�N�������Ȃ��B
	 * @param userInfo				���s���郆�[�U���B
	 * @param pkInfo				�\���f�[�^��L�[�B
	 * @return						�\���f�[�^�t�@�C�����\�[�X
	 * @throws ApplicationException	�\���f�[�^�̎擾�Ɏ��s�����ꍇ�B
	 */
	public FileResource getShinseiFileResourceWithoutLock(
		UserInfo userInfo,
		final ShinseiDataPk pkInfo)
		throws ApplicationException, NoDataFoundException, ConvertException;
	
	
	//2005/05/25 �ǉ� ��������-------------------------------------------------
	//���R�@PDF�t�@�C���擾�̂���
	
	/**
	 * �\��PDF�f�[�^�����擾���A�\��PDF�f�[�^��IOD�ɕϊ����A
	 * IOD��PDF�ɕϊ����APDF�t�@�C���p�X��DB�ɏ������ށB
	 * 
	 * @param connection	Connection
	 * @param userInfo	UserInfo
	 * @param pkInfo	CheckListSearchInfo
	 * @throws ApplicationException		�ϊ��Ɏ��s�����Ƃ��B
	 * @throws DataAccessException		
	 * @throws IOException
	 */
	public void convertHyoshiData(
		Connection connection,
		UserInfo userInfo,
		CheckListSearchInfo checkInfo)
		throws	ApplicationException, DataAccessException, IOException;
		
	//�ǉ� �����܂�------------------------------------------------------------

// 2006/06/28 dyh add start
    /**
     * �̈�v�揑�\��PDF�f�[�^���PDF�t�@�C�����쐬����B
     * IOD��PDF�ɕϊ����APDF�t�@�C���p�X��DB�ɏ������ށB
     * 
     * @param connection Connection
     * @param userInfo UserInfo
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @throws ApplicationException �ϊ��Ɏ��s�����Ƃ��B
     * @throws DataAccessException      
     * @throws IOException
     */
    public void convertGaiyoHyoshiPdf(
            Connection connection,
            UserInfo userInfo,
            String ryoikiSystemNo)
            throws ApplicationException, IOException;
// 2006/06/28 dyh add end

	//2005.07.15 iso PDF�Y�t�@�\�ǉ�
	/**
	 * PDF�t�@�C�����L�����𒲂ׂ�B
	 * @param fileRes					��������t�@�C�����\�[�X�B
	 * @return							�L���F0�@����:�G���[�R�[�h
	 * @throws ConvertException		�ϊ����ɗ�O�����������Ƃ��B
	 */
	public int checkPdf(FileResource fileRes) throws ApplicationException ;
    
    /**
     * �̈�v�揑�i�T�v�j�����PDF�t�@�C�����쐬����B lockFlag��true�̂Ƃ��A�쐬���ꂽPDF�t�@�C���Ƀp�X���[�h���b�N��������B
     * 
     * @param userInfo
     * @param pkInfo
     * @param lockFlag
     * @return
     * @throws ApplicationException
     * @throws NoDataFoundException
     * @throws ConvertException
     */
    public FileResource getGaiyouResource(UserInfo userInfo, final RyoikiKeikakushoPk pkInfo)
            throws ApplicationException, NoDataFoundException, ConvertException;
   
//2006/07/12 �c�@�ǉ���������    
    /**
     * �\���f�[�^���A�Y�t�t�@�C�������IOD�t�@�C�����쐬���A�\���f�[�^�Ǘ��ɓo�^����B(�����񖔂͌����v�撲���m�F�p)
     * @param userInfo              ���s���郆�[�U���B
     * @param pkInfo                �\���f�[�^��L�[�B
     * @throws ApplicationException 
     * @throws ConvertException     �ϊ��Ɏ��s�����Ƃ��B
     */
    public void shinseiDataConvertForConfirm(UserInfo userInfo, Connection connection, ShinseiDataPk pkInfo, File iodFile,
            File xmlFile) throws ApplicationException;

    /**
     * �̈�v�揑PDF�f�[�^���PDF�t�@�C�����쐬����B(�̈�v�揑�m�F�p)
     * IOD��PDF�ɕϊ����APDF�t�@�C���p�X��DB�ɏ������ށB
     * 
     * @param connection Connection
     * @param userInfo UserInfo
     * @param ryoikiSystemNo �V�X�e����t�ԍ�
     * @throws ApplicationException �ϊ��Ɏ��s�����Ƃ��B
     * @throws ConvertException      
     * @throws IOException
     */
    public void convertRyoikiGaiyoPdfForConfirm(Connection connection, UserInfo userInfo, File iodFile,
            RyoikiKeikakushoPk ryoikiKeikakushoPk) throws ApplicationException;
//2006/07/21�@�c�@�ǉ������܂�    


    //2006.09.15 iso �^�C�g���Ɂu�T�v�v������PDF�쐬�̂���
    /**
     * �\���f�[�^��PDF�ϊ����A�Y�t�t�@�C����PDF�ƌ�������B
     * @param connection
     * @param userInfo
     * @param ryoikikeikakushoInfo
     * @throws ApplicationException     �ϊ��Ɏ��s�����Ƃ��B   
     * @throws IOException
     */
    public FileResource convertRyoikiKeikakushoGaiyo(
    		Connection connection, UserInfo userInfo, RyoikiKeikakushoInfo ryoikikeikakushoInfo)
    		throws ApplicationException;
    

    //2006.09.29 iso �u�T�v�v��PDF���쐬���A�w��t�H���_�ɏ�������
    /**
     * �t�@�C�����X�g���w��t�H���_�ɏ������ށB
     * 
     * @param connection
     * @param userInfo
     * @param fileList
     * @param outFile
     * @return
     * @throws ApplicationException
     * @throws NoDataFoundException
     * @throws ConvertException
     */
    public void writeGaiyoFileResource(
    		Connection connection, UserInfo userInfo, FileResource fileResource, List fileList, File outFile)
    		throws ApplicationException, NoDataFoundException, ConvertException;
    
}