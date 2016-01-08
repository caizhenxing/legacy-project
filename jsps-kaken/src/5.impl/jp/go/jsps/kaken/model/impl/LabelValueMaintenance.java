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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.ILabelValueMaintenance;
import jp.go.jsps.kaken.model.dao.exceptions.DataAccessException;
import jp.go.jsps.kaken.model.dao.impl.MasterJigyoInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKaigaibunyaInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKanrenBunyaBunruiInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKategoriInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanShubetuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterLabelInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterShokushuInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.LabelValueBean;
import jp.go.jsps.kaken.web.system.ryoikiKeikaku.RyoikiGaiyoSearchForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.go.jsps.kaken.model.dao.impl.MasterRyouikiInfoDao;

/**
 * �f�[�^�ۊǊǗ����s���N���X�B
 * 
 * ID RCSfile="$RCSfile: LabelValueMaintenance.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/20 06:05:57 $"
 */
public class LabelValueMaintenance implements ILabelValueMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O */
	protected static Log log = LogFactory.getLog(LabelValueMaintenance.class);
	
	/** �����t���O�F���X�g�p */
	public static final String FLAG_KENSAKU_LIST      = "0";
	
	/** �����t���O�F���ׂ� */
	public static final String FLAG_KENSAKU_ALL      = "1";
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * �R���X�g���N�^�B
	 */
	public LabelValueMaintenance() {
		super();
	}

	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
	
	
	//---------------------------------------------------------------------
	// implement ILabelValueMaintenance
	//---------------------------------------------------------------------	

// 20050526 Start
	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getKenkyuKubunList()
	 */
	public List getKenkyuKubunList() throws ApplicationException
	{
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = MasterRyouikiInfoDao.selectRyouikiKubunInfoList(connection);
		}
		catch(NoDataFoundException ex){
			throw new NoDataFoundException("�̈�}�X�^��1�����f�[�^������܂���B",ex);}
		catch(ApplicationException ex){
			throw new ApplicationException("�̈��񌟍�����DB�G���[���������܂����B",new ErrorInfo("errors.4004"),ex);}
		finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List kenkyuKubunList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			kenkyuKubunList.add(new LabelValueBean((String)datas.get("RYOIKI_RYAKU"), (String)datas.get("RYOIKI_NO")));
		}
		return kenkyuKubunList;

	}
// Horikoshi End

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getKikanShubetuCdList()
	 */
	public List getKikanShubetuCdList() throws ApplicationException
	{
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = MasterKikanShubetuInfoDao.selectKikanShubetuInfoList(connection);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List shubetuCdList = new ArrayList();	
		shubetuCdList.add(new LabelValueBean("", ""));
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			shubetuCdList.add(new LabelValueBean((String)datas.get("SHUBETU_NAME"), (String)datas.get("SHUBETU_CD")));
		}	
		return shubetuCdList;
		
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getJigyoNameList()
	 */
	public List getJigyoNameList() throws ApplicationException
	{
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = MasterJigyoInfoDao.selectJigyoInfoList(connection);	
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"), (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;

	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getJigyoNameList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public List getJigyoNameList(UserInfo userInfo)
		throws ApplicationException
	{
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = new MasterJigyoInfoDao(userInfo).selectJigyoInfoList4Users(connection);	
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"), (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;

	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getJigyoNameList(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
	 */
	public List getJigyoNameList(UserInfo userInfo, String jigyoKubun) 
		throws ApplicationException 
	{
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = new MasterJigyoInfoDao(userInfo).selectJigyoInfoList(connection, jigyoKubun);
		}catch(NoDataFoundException e){
			list = new ArrayList();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"), (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getShinsaTaishoJigyoNameList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public List getShinsaTaishoJigyoNameList(UserInfo userInfo) 
		throws ApplicationException 
	{
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = new MasterJigyoInfoDao(userInfo).selectShinsaTaishoJigyoInfoList4Users(connection);
		}catch(NoDataFoundException e){
			list = new ArrayList();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"), (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;
	}	

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getShokushuList()
	 */
	public List getShokushuList() throws ApplicationException
	{
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = MasterShokushuInfoDao.selectShokushuList(connection);	
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List shokushuList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			shokushuList.add(new LabelValueBean((String)datas.get("SHOKUSHU_NAME"), (String)datas.get("SHOKUSHU_CD")));
		}	
		return shokushuList;

	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getKategoriNameList()
	 */
	public List getKategoriCdList() throws ApplicationException
	{
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = MasterKategoriInfoDao.selectKategoriList(connection);	
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List kategoriCdList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			kategoriCdList.add(new LabelValueBean((String)datas.get("KATEGORI_NAME"), (String)datas.get("BUKA_KATEGORI")));
		}	
		return kategoriCdList;

	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getLabelList()
	/**
	 * ���x�������擾����B
	 * ���Y�u���x���敪�v�̃f�[�^�����݂��Ȃ������ꍇ�ANoDataFoundException ��
	 * �X���[����B
	 * @param	labelKubun	���x���敪
	 * @return				���x�������X�g
	 */
	public List getLabelList(String labelKubun) throws ApplicationException {
		List list = (List)getLabelList(new String[]{labelKubun},LabelValueMaintenance.FLAG_KENSAKU_LIST).get(0);
		if(list == null){
			String msg = "���Y���R�[�h�͑��݂��܂���B���x���敪="+labelKubun;
			throw new NoDataFoundException(msg);
		}else{
			return list;
		}
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getALLLabelList(java.lang.String)
	 */
	public List getAllLabelList(String labelKubun) throws ApplicationException {
		List list = (List)getLabelList(new String[]{labelKubun}, LabelValueMaintenance.FLAG_KENSAKU_ALL).get(0);
		if(list == null){
			String msg = "���Y���R�[�h�͑��݂��܂���B���x���敪="+labelKubun;
			throw new NoDataFoundException(msg);
		}else{
			return list;
		}
	}

	/**
	 * ���x�������擾����B�i�v���_�E���j
	 * LabelValueBean��List���AList�̌`���Ŗ߂�B
	 * ���Y�u���x���敪�v�̃f�[�^�����݂��Ȃ������ꍇ�A�߂�l��List���ɂ� null ��
	 * �i�[�����B
	 * @param	labelKubun	���x���敪
	 * @return				���x�������X�g
	 */
	public List getLabelList(String[] labelKubun) throws ApplicationException {
		return getLabelList(labelKubun, LabelValueMaintenance.FLAG_KENSAKU_LIST);
	}

	//2005.10.25 iso ���x�����X�g���ꊇ�擾���邽�ߒǉ�
	/**
	 * ���x�������擾����B�i�v���_�E���j
	 * LabelValueBean��List���AMap�̌`���Ŗ߂�B
	 * @param	labelKubun	���x���敪
	 * @return				���x���}�b�v
	 */
	public Map getLabelMap(String[] labelKubun) throws ApplicationException {
		List labelList = getLabelList(labelKubun, LabelValueMaintenance.FLAG_KENSAKU_LIST);
		if(labelKubun.length == labelList.size()) {
			HashMap labelMap = new HashMap();
			for(int i = 0; i < labelKubun.length; i++) {
				labelMap.put(labelKubun[i], labelList.get(i));
			}
			return labelMap;
		} else {
			throw new ApplicationException("���x���̎擾������v���܂���B" , new ErrorInfo("errors.4004"));
		}
	}

	/**
	 * ���x�������擾����B�i�S���j
	 * LabelValueBean��List���AList�̌`���Ŗ߂�B
	 * ���Y�u���x���敪�v�̃f�[�^�����݂��Ȃ������ꍇ�A�߂�l��List���ɂ� null ��
	 * �i�[�����B
	 * @param	labelKubun	���x���敪
	 * @return				���x�������X�g
	 */
	public List getAllLabelList(String[] labelKubun) throws ApplicationException {
		return getLabelList(labelKubun, LabelValueMaintenance.FLAG_KENSAKU_ALL);
	}

	/*
	 * ���x�������擾����B
	 * LabelValueBean��List���AList�̌`���Ŗ߂�B
	 * ���Y�u���x���敪�v�̃f�[�^�����݂��Ȃ������ꍇ�A�߂�l��List���ɂ� NULL ��
	 * �i�[�����B
	 * @param	labelKubun	���x���敪
	 * @param	kensakuFlg	�����t���O
	 * @return				���x�������X�g
	 */
	private List getLabelList(String[] labelKubun, String kensakuFlg) throws ApplicationException {
		
		List resultList = new ArrayList();
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			//�������J��Ԃ�
			for(int j=0; j<labelKubun.length; j++){
				List labelList = null;
				try{
					List list  = MasterLabelInfoDao.selectLabelInfoList(connection, labelKubun[j], kensakuFlg);	
					//�v���_�E���f�[�^�𐶐�����
					labelList = new ArrayList();	
					for(int i = 0;i < list.size(); i++){
						Map datas = (Map)list.get(i);
						labelList.add(new LabelValueBean((String)datas.get("NAME"), (String)datas.get("ATAI")));
					}
				}catch(NoDataFoundException e){
					//�f�[�^��������Ȃ������ꍇ�� null ���i�[
					resultList.add(null);
				}
				resultList.add(labelList);
			}
			
		}finally{
			DatabaseUtil.closeConnection(connection);
		}
			
		return resultList;
	}

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getLabelList(java.lang.String, java.lang.String)
	 */
	public Map selectRecord(String labelKubun, String value) throws ApplicationException {
		Connection connection = null;
		Map recordMap = null;
		try{
			connection = DatabaseUtil.getConnection();
			recordMap  = MasterLabelInfoDao.selectRecord(connection, labelKubun, value);
		} catch (NoDataFoundException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"���x����񌟍�����DB�G���[���������܂����B",
				new ErrorInfo("errors.4004"),
				e);
		}finally{
			DatabaseUtil.closeConnection(connection);
		}
		
		return recordMap;
	}
	
	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getKaigaiBunyaList()
	 */
	public List getKaigaiBunyaList() throws ApplicationException
	{
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = MasterKaigaibunyaInfoDao.selectKaigaibunyaList(connection);	
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List bunyaList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			bunyaList.add(new LabelValueBean((String)datas.get("KAIGAIBUNYA_NAME"), (String)datas.get("KAIGAIBUNYA_CD")));
		}	
		return bunyaList;

	}

	//2005/04/27�@�ǉ� ��������----------------------------------------------
	//���R �����̒S�����鎖�ƃR�[�h�Ń��X�g�̍i���݂����邽��

    /**
     * ���ƃR�[�h���A���Ɩ��̂��擾
     * @param userInfo ���[�U���
     * @return List
     * @throws ApplicationException
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getJigyoNameListByJigyoCds(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public List getJigyoNameListByJigyoCds(UserInfo userInfo) 
		throws ApplicationException 
	{
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = new MasterJigyoInfoDao(userInfo).selectJigyoInfoListByJigyoCds(connection);
		}catch(NoDataFoundException e){
			list = new ArrayList();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"),
                                             (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;
	}
	//�ǉ� �����܂�----------------------------------------------------------

    /**
     * ���ƃR�[�h���A���Ɩ��̂��擾
     * @param userInfo ���[�U���
     * @param jigyoKubun ���Ƌ敪
     * @return List
     * @throws ApplicationException
     * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getJigyoNameListByJigyoCds(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
     */
    public List getJigyoNameListByJigyoCds(UserInfo userInfo, String jigyoKubun) 
            throws ApplicationException {

        Connection connection = null;
        List list = null;
        try {
            connection = DatabaseUtil.getConnection();
            list = new MasterJigyoInfoDao(userInfo).selectJigyoInfoListByJigyoCds(connection, jigyoKubun);
        }catch(NoDataFoundException e){
            list = new ArrayList();
        } finally {
            DatabaseUtil.closeConnection(connection);
        }

        //�v���_�E���f�[�^�𐶐�����
        List jigyoList = new ArrayList();   
        for(int i = 0;i < list.size(); i++){
            Map datas = (Map)list.get(i);
            jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"),
                                             (String)datas.get("JIGYO_CD")));
        }   
        return jigyoList;
    }

//  2006/06/02 �c �ǉ���������
    /**
     * ���Ƌ敪�E����CD�ōi�荞�񂾃��O�C�����[�U�p�̎��Ɩ����X�g��Ԃ�
     * �n���ꂽUserInfo���Ɩ��S���҂̏ꍇ�́A
     * �����̒S�����鎖�ƁE�R���S�����鎖�Ƌ敪�i1�܂���4�̏ꍇ�j�݂̂̎��Ɩ����X�g���Ԃ�
     * @param userInfo ���[�U���
     * @param jigyoCds ���ƃR�[�h���X�g
     * @param jigyoKubun ���Ƌ敪
     * @return  ���Ɩ����X�g
     */
    public List getJigyoNameListByJigyoCds(
            UserInfo userInfo,
            String[] jigyoCds,
            String jigyoKubun) 
            throws ApplicationException {

        Connection connection = null;
        List list = null;
        try {
            connection = DatabaseUtil.getConnection();
            list = new MasterJigyoInfoDao(userInfo)
                    .selectJigyoInfoListByJigyoCds(connection, jigyoCds,
                            jigyoKubun);
        }catch(NoDataFoundException e){
            list = new ArrayList();
        } finally {
            DatabaseUtil.closeConnection(connection);
        }

        //�v���_�E���f�[�^�𐶐�����
        List jigyoList = new ArrayList();   
        for(int i = 0;i < list.size(); i++){
            Map datas = (Map)list.get(i);
            jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"),
                                             (String)datas.get("JIGYO_CD")));
        }   
        return jigyoList;
    }
//  2006/06/02 �c �ǉ������܂�

//    //2006/06/06 jzx�@add start
//    //���R �����̒S�����鎖�ƃR�[�h�Ń��X�g�̍i���݂����邽��
//    /**
//     * ���ƃR�[�h���A���Ɩ��̂��擾
//     * @param userInfo ���[�U���
//     * @param jigyoCd
//     * @return List
//     * @throws ApplicationException
//     * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getJigyoNameListByJigyoNames(jp.go.jsps.kaken.model.vo.UserInfo)
//     */
//    public List getJigyoNameListByJigyoCds2(UserInfo userInfo) 
//            throws ApplicationException {
//        Connection connection = null;
//        List list = null;
//        try {
//            connection = DatabaseUtil.getConnection();
//            String[] validJigyoCds = new String[]{
//                    IJigyoCd.JIGYO_CD_TOKUSUI,        //���ʐ��i����
//                    IJigyoCd.JIGYO_CD_KIBAN_S,        //��Ռ���(S)
//                    IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN,  //��Ռ���(A)(���)
//                    IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI, //��Ռ���(A)(�C�O�w�p����)
//                    IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN,  //��Ռ���(B)(���)
//                    IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI, //��Ռ���(B)(�C�O�w�p����)
//                    IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO,//�w�p�n���i�����j
//                    IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO}; //�w�p�n���i����j
//            list = new MasterJigyoInfoDao(userInfo)
//                    .selectJigyoInfoListByJigyoCds(connection,
//                            validJigyoCds,"");
//        }catch(NoDataFoundException e){
//            list = new ArrayList();
//        } finally {
//            DatabaseUtil.closeConnection(connection);
//        }
//
//        //�v���_�E���f�[�^�𐶐�����
//        List jigyoList = new ArrayList();   
//        for(int i = 0;i < list.size(); i++){
//            Map datas = (Map)list.get(i);
//            jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"),
//                                             (String)datas.get("JIGYO_CD")));
//        }   
//        return jigyoList;
//    }
//    //2006/06/06 jzx�@add end
//
////  2006/06/14 �����R�@�ǉ���������-------------------------------------------  
//    /**
//     * ���ƃR�[�h���A���Ɩ��̂��擾
//     * @param userInfo
//     * @param jigyoCd
//     * @return List
//     * @throws ApplicationException
//     * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getJigyoNameListByJigyoCds3(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
//     */
//    public List getJigyoNameListByJigyoCds3(UserInfo userInfo, String jigyoCd)
//            throws ApplicationException{
//        
//        Connection connection = null;
//        List list = null;
//        try {
//            connection = DatabaseUtil.getConnection();
//            String[] validJigyoCds = new String[]{jigyoCd};
//            list = new MasterJigyoInfoDao(userInfo)
//                    .selectJigyoInfoListByJigyoCds(connection,
//                            validJigyoCds,"");
//        }catch(NoDataFoundException e){
//            list = new ArrayList();
//        } finally {
//            DatabaseUtil.closeConnection(connection);
//        }
//        
//        //�v���_�E���f�[�^�𐶐�����
//        List jigyoList = new ArrayList();   
//        for(int i = 0;i < list.size(); i++){
//            Map datas = (Map)list.get(i);
//            jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"),
//                                             (String)datas.get("JIGYO_CD")));
//        }   
//        return jigyoList;
//    }
////  �ǉ� �����܂�---------------------------------------------------------

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getShinsaTaishoListByJigyoCds(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public List getShinsaTaishoListByJigyoCds(UserInfo userInfo) 
			throws ApplicationException {
		
		Connection connection = null;
		List list = null;
		try {
			connection = DatabaseUtil.getConnection();
			list = new MasterJigyoInfoDao(userInfo).selectShinsaTaishoList4UsersByJigyoCds(connection);
		}catch(NoDataFoundException e){
			list = new ArrayList();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		//�v���_�E���f�[�^�𐶐�����
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"), (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;
	}

//2007/02/08 �c�@�폜��������@�g�p���Ȃ�
//2006/02/16 Start
//	/* (�� Javadoc)
//	 * ���x�����}�X�^���R���̈於�����o��LabelValueBean�ɐݒ肷��
//	 * @see jp.go.jsps.model.ILabelValueMaintenance#getKiboRyoikiList(java.lang.String)
//	 */
//	public List getKiboRyoikiList() throws ApplicationException {
//		
//		Connection connection = null;
//		List list = null;
//		try {
//			connection = DatabaseUtil.getConnection();
//			list = MasterShinsaRyoikiInfoDao.selectShinsaRyoikiInfoList(connection);
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//
//		//�v���_�E���f�[�^�𐶐�����
//		List jigyoList = new ArrayList();	
//		for(int i = 0;i < list.size(); i++){
//			Map datas = (Map)list.get(i);
//			jigyoList.add(new LabelValueBean((String)datas.get("SHINSARYOIKI_NAME"), datas.get("SHINSARYOIKI_CD").toString()));
//		}	
//		return jigyoList;
//	}
// syuu End
//2007/02/08�@�c�@�폜�����܂�    
    
//2006/06/22 �c�@�ǉ���������
    /**
     *  �֘A����15���ށi���ޖ�)���擾����B
     *  LabelValueBean��List���AList�̌`���Ŗ߂�B
     *  @return   �֘A����15���ށi���ޖ�)���X�g
     *  @throws ApplicationException
     */
    public List getKanrenBunyaBunruiList() throws ApplicationException {
        
        List resultList = new ArrayList();
        Connection connection = null;

        List labelList = null;
        try {
            connection = DatabaseUtil.getConnection();

            List list = MasterKanrenBunyaBunruiInfoDao.selectKanrenBunyaBunruiInfoList(connection);
            //�v���_�E���f�[�^�𐶐�����
            labelList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Map datas = (Map) list.get(i);
                labelList.add(new LabelValueBean((String) datas.get("KANRENBUNYA_BUNRUI_NAME"),
                        (String) datas.get("KANRENBUNYA_BUNRUI_NO")));
            }
        } catch (NoDataFoundException e) {
            //�f�[�^��������Ȃ������ꍇ�� null ���i�[
            resultList.add(null);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        
        resultList.add(labelList);

        return resultList;
    }
//2006/06/22�@�c�@�ǉ������܂�    
    
//  ADD�@START 2007/07/20 BIS ���� -->     
    /**    
     * �̈�v�揑������ʂ̌�����ږ����擾����B
     * 
     * @param searchJigyoCd ���ƃR�[�g
     * @return List ������ږ��̈ꗗ
     * @throws ApplicationException
     */
	public String searchJigyoName(String searchJigyoCd) throws ApplicationException {
		String jigyoName = null;
		String query = 
			"SELECT DISTINCT "
			+"A.JIGYO_NAME "
			+"FROM "
			+"MASTER_JIGYO A "
			+"WHERE "
			+"A.JIGYO_CD = '" + searchJigyoCd + "'";
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resutlt = null;
		try {
//			DB�R�l�N�V�����̎擾
			connection = DatabaseUtil.getConnection();
			ps = connection.prepareStatement(query);
			resutlt = ps.executeQuery() ;
			if (resutlt.next()) {
				jigyoName = resutlt.getString("JIGYO_NAME");
			}
			return jigyoName;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return null;
	}    
//	ADD�@END 2007/07/20 BIS ���� --> 	
}