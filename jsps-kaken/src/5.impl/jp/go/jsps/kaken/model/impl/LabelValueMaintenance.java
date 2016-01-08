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
 * データ保管管理を行うクラス。
 * 
 * ID RCSfile="$RCSfile: LabelValueMaintenance.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/20 06:05:57 $"
 */
public class LabelValueMaintenance implements ILabelValueMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(LabelValueMaintenance.class);
	
	/** 検索フラグ：リスト用 */
	public static final String FLAG_KENSAKU_LIST      = "0";
	
	/** 検索フラグ：すべて */
	public static final String FLAG_KENSAKU_ALL      = "1";
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
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
	/* (非 Javadoc)
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
			throw new NoDataFoundException("領域マスタに1件もデータがありません。",ex);}
		catch(ApplicationException ex){
			throw new ApplicationException("領域情報検索中にDBエラーが発生しました。",new ErrorInfo("errors.4004"),ex);}
		finally {
			DatabaseUtil.closeConnection(connection);
		}

		//プルダウンデータを生成する
		List kenkyuKubunList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			kenkyuKubunList.add(new LabelValueBean((String)datas.get("RYOIKI_RYAKU"), (String)datas.get("RYOIKI_NO")));
		}
		return kenkyuKubunList;

	}
// Horikoshi End

	/* (非 Javadoc)
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

		//プルダウンデータを生成する
		List shubetuCdList = new ArrayList();	
		shubetuCdList.add(new LabelValueBean("", ""));
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			shubetuCdList.add(new LabelValueBean((String)datas.get("SHUBETU_NAME"), (String)datas.get("SHUBETU_CD")));
		}	
		return shubetuCdList;
		
	}

	/* (非 Javadoc)
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

		//プルダウンデータを生成する
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"), (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;

	}

	/* (非 Javadoc)
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

		//プルダウンデータを生成する
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"), (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;

	}

	/* (非 Javadoc)
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

		//プルダウンデータを生成する
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"), (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;
	}

	/* (非 Javadoc)
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

		//プルダウンデータを生成する
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"), (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;
	}	

	/* (非 Javadoc)
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

		//プルダウンデータを生成する
		List shokushuList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			shokushuList.add(new LabelValueBean((String)datas.get("SHOKUSHU_NAME"), (String)datas.get("SHOKUSHU_CD")));
		}	
		return shokushuList;

	}

	/* (非 Javadoc)
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

		//プルダウンデータを生成する
		List kategoriCdList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			kategoriCdList.add(new LabelValueBean((String)datas.get("KATEGORI_NAME"), (String)datas.get("BUKA_KATEGORI")));
		}	
		return kategoriCdList;

	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getLabelList()
	/**
	 * ラベル名を取得する。
	 * 当該「ラベル区分」のデータが存在しなかった場合、NoDataFoundException を
	 * スローする。
	 * @param	labelKubun	ラベル区分
	 * @return				ラベル名リスト
	 */
	public List getLabelList(String labelKubun) throws ApplicationException {
		List list = (List)getLabelList(new String[]{labelKubun},LabelValueMaintenance.FLAG_KENSAKU_LIST).get(0);
		if(list == null){
			String msg = "当該レコードは存在しません。ラベル区分="+labelKubun;
			throw new NoDataFoundException(msg);
		}else{
			return list;
		}
	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ILabelValueMaintenance#getALLLabelList(java.lang.String)
	 */
	public List getAllLabelList(String labelKubun) throws ApplicationException {
		List list = (List)getLabelList(new String[]{labelKubun}, LabelValueMaintenance.FLAG_KENSAKU_ALL).get(0);
		if(list == null){
			String msg = "当該レコードは存在しません。ラベル区分="+labelKubun;
			throw new NoDataFoundException(msg);
		}else{
			return list;
		}
	}

	/**
	 * ラベル名を取得する。（プルダウン）
	 * LabelValueBeanのListが、Listの形式で戻る。
	 * 当該「ラベル区分」のデータが存在しなかった場合、戻り値のList内には null が
	 * 格納される。
	 * @param	labelKubun	ラベル区分
	 * @return				ラベル名リスト
	 */
	public List getLabelList(String[] labelKubun) throws ApplicationException {
		return getLabelList(labelKubun, LabelValueMaintenance.FLAG_KENSAKU_LIST);
	}

	//2005.10.25 iso ラベルリストを一括取得するため追加
	/**
	 * ラベル名を取得する。（プルダウン）
	 * LabelValueBeanのListが、Mapの形式で戻る。
	 * @param	labelKubun	ラベル区分
	 * @return				ラベルマップ
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
			throw new ApplicationException("ラベルの取得数が一致しません。" , new ErrorInfo("errors.4004"));
		}
	}

	/**
	 * ラベル名を取得する。（全部）
	 * LabelValueBeanのListが、Listの形式で戻る。
	 * 当該「ラベル区分」のデータが存在しなかった場合、戻り値のList内には null が
	 * 格納される。
	 * @param	labelKubun	ラベル区分
	 * @return				ラベル名リスト
	 */
	public List getAllLabelList(String[] labelKubun) throws ApplicationException {
		return getLabelList(labelKubun, LabelValueMaintenance.FLAG_KENSAKU_ALL);
	}

	/*
	 * ラベル名を取得する。
	 * LabelValueBeanのListが、Listの形式で戻る。
	 * 当該「ラベル区分」のデータが存在しなかった場合、戻り値のList内には NULL が
	 * 格納される。
	 * @param	labelKubun	ラベル区分
	 * @param	kensakuFlg	検索フラグ
	 * @return				ラベル名リスト
	 */
	private List getLabelList(String[] labelKubun, String kensakuFlg) throws ApplicationException {
		
		List resultList = new ArrayList();
		Connection connection = null;
		try{
			connection = DatabaseUtil.getConnection();
			//引数分繰り返す
			for(int j=0; j<labelKubun.length; j++){
				List labelList = null;
				try{
					List list  = MasterLabelInfoDao.selectLabelInfoList(connection, labelKubun[j], kensakuFlg);	
					//プルダウンデータを生成する
					labelList = new ArrayList();	
					for(int i = 0;i < list.size(); i++){
						Map datas = (Map)list.get(i);
						labelList.add(new LabelValueBean((String)datas.get("NAME"), (String)datas.get("ATAI")));
					}
				}catch(NoDataFoundException e){
					//データが見つからなかった場合は null を格納
					resultList.add(null);
				}
				resultList.add(labelList);
			}
			
		}finally{
			DatabaseUtil.closeConnection(connection);
		}
			
		return resultList;
	}

	/* (非 Javadoc)
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
				"ラベル情報検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		}finally{
			DatabaseUtil.closeConnection(connection);
		}
		
		return recordMap;
	}
	
	/* (非 Javadoc)
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

		//プルダウンデータを生成する
		List bunyaList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			bunyaList.add(new LabelValueBean((String)datas.get("KAIGAIBUNYA_NAME"), (String)datas.get("KAIGAIBUNYA_CD")));
		}	
		return bunyaList;

	}

	//2005/04/27　追加 ここから----------------------------------------------
	//理由 自分の担当する事業コードでリストの絞込みをするため

    /**
     * 事業コードより、事業名称を取得
     * @param userInfo ユーザ情報
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

		//プルダウンデータを生成する
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"),
                                             (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;
	}
	//追加 ここまで----------------------------------------------------------

    /**
     * 事業コードより、事業名称を取得
     * @param userInfo ユーザ情報
     * @param jigyoKubun 事業区分
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

        //プルダウンデータを生成する
        List jigyoList = new ArrayList();   
        for(int i = 0;i < list.size(); i++){
            Map datas = (Map)list.get(i);
            jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"),
                                             (String)datas.get("JIGYO_CD")));
        }   
        return jigyoList;
    }

//  2006/06/02 苗 追加ここから
    /**
     * 事業区分・事業CDで絞り込んだログインユーザ用の事業名リストを返す
     * 渡されたUserInfoが業務担当者の場合は、
     * 自分の担当する事業・審査担当する事業区分（1または4の場合）のみの事業名リストが返る
     * @param userInfo ユーザ情報
     * @param jigyoCds 事業コードリスト
     * @param jigyoKubun 事業区分
     * @return  事業名リスト
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

        //プルダウンデータを生成する
        List jigyoList = new ArrayList();   
        for(int i = 0;i < list.size(); i++){
            Map datas = (Map)list.get(i);
            jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"),
                                             (String)datas.get("JIGYO_CD")));
        }   
        return jigyoList;
    }
//  2006/06/02 苗 追加ここまで

//    //2006/06/06 jzx　add start
//    //理由 自分の担当する事業コードでリストの絞込みをするため
//    /**
//     * 事業コードより、事業名称を取得
//     * @param userInfo ユーザ情報
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
//                    IJigyoCd.JIGYO_CD_TOKUSUI,        //特別推進研究
//                    IJigyoCd.JIGYO_CD_KIBAN_S,        //基盤研究(S)
//                    IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN,  //基盤研究(A)(一般)
//                    IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI, //基盤研究(A)(海外学術調査)
//                    IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN,  //基盤研究(B)(一般)
//                    IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI, //基盤研究(B)(海外学術調査)
//                    IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO,//学術創成（非公募）
//                    IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO}; //学術創成（公募）
//            list = new MasterJigyoInfoDao(userInfo)
//                    .selectJigyoInfoListByJigyoCds(connection,
//                            validJigyoCds,"");
//        }catch(NoDataFoundException e){
//            list = new ArrayList();
//        } finally {
//            DatabaseUtil.closeConnection(connection);
//        }
//
//        //プルダウンデータを生成する
//        List jigyoList = new ArrayList();   
//        for(int i = 0;i < list.size(); i++){
//            Map datas = (Map)list.get(i);
//            jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"),
//                                             (String)datas.get("JIGYO_CD")));
//        }   
//        return jigyoList;
//    }
//    //2006/06/06 jzx　add end
//
////  2006/06/14 李万軍　追加ここから-------------------------------------------  
//    /**
//     * 事業コードより、事業名称を取得
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
//        //プルダウンデータを生成する
//        List jigyoList = new ArrayList();   
//        for(int i = 0;i < list.size(); i++){
//            Map datas = (Map)list.get(i);
//            jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"),
//                                             (String)datas.get("JIGYO_CD")));
//        }   
//        return jigyoList;
//    }
////  追加 ここまで---------------------------------------------------------

	/* (非 Javadoc)
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

		//プルダウンデータを生成する
		List jigyoList = new ArrayList();	
		for(int i = 0;i < list.size(); i++){
			Map datas = (Map)list.get(i);
			jigyoList.add(new LabelValueBean((String)datas.get("JIGYO_NAME"), (String)datas.get("JIGYO_CD")));
		}	
		return jigyoList;
	}

//2007/02/08 苗　削除ここから　使用しない
//2006/02/16 Start
//	/* (非 Javadoc)
//	 * ラベル名マスタより審査領域名を取り出しLabelValueBeanに設定する
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
//		//プルダウンデータを生成する
//		List jigyoList = new ArrayList();	
//		for(int i = 0;i < list.size(); i++){
//			Map datas = (Map)list.get(i);
//			jigyoList.add(new LabelValueBean((String)datas.get("SHINSARYOIKI_NAME"), datas.get("SHINSARYOIKI_CD").toString()));
//		}	
//		return jigyoList;
//	}
// syuu End
//2007/02/08　苗　削除ここまで    
    
//2006/06/22 苗　追加ここから
    /**
     *  関連分野15分類（分類名)を取得する。
     *  LabelValueBeanのListが、Listの形式で戻る。
     *  @return   関連分野15分類（分類名)リスト
     *  @throws ApplicationException
     */
    public List getKanrenBunyaBunruiList() throws ApplicationException {
        
        List resultList = new ArrayList();
        Connection connection = null;

        List labelList = null;
        try {
            connection = DatabaseUtil.getConnection();

            List list = MasterKanrenBunyaBunruiInfoDao.selectKanrenBunyaBunruiInfoList(connection);
            //プルダウンデータを生成する
            labelList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Map datas = (Map) list.get(i);
                labelList.add(new LabelValueBean((String) datas.get("KANRENBUNYA_BUNRUI_NAME"),
                        (String) datas.get("KANRENBUNYA_BUNRUI_NO")));
            }
        } catch (NoDataFoundException e) {
            //データが見つからなかった場合は null を格納
            resultList.add(null);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
        
        resultList.add(labelList);

        return resultList;
    }
//2006/06/22　苗　追加ここまで    
    
//  ADD　START 2007/07/20 BIS 張楠 -->     
    /**    
     * 領域計画書検索画面の研究種目名を取得する。
     * 
     * @param searchJigyoCd 事業コート
     * @return List 研究種目名の一覧
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
//			DBコネクションの取得
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
//	ADD　END 2007/07/20 BIS 張楠 --> 	
}