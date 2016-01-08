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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.jsps.kaken.model.ICodeMaintenance;
import jp.go.jsps.kaken.model.dao.impl.MasterBukyokuInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKeywordInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterKikanInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterRyouikiInfoDao;
import jp.go.jsps.kaken.model.dao.impl.MasterSaimokuInfoDao;
import jp.go.jsps.kaken.model.dao.util.DatabaseUtil;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;

import jp.go.jsps.kaken.model.vo.UserInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * コード表情報管理を行うクラス。
 * 
 * ID RCSfile="$RCSfile: CodeMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:47 $"
 */
public class CodeMaintenance implements ICodeMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	
	/** ログ */
	protected static Log log = LogFactory.getLog(ShozokuMaintenance.class);
	
		
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public CodeMaintenance() {
		super();
	}
	
	//---------------------------------------------------------------------
	// implement IShinsaKekkaMaintenance
	//---------------------------------------------------------------------
	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getKikanInfoList(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
	 */
//	public List getKikanInfoList(UserInfo userInfo, String shubetuCd) throws ApplicationException {
//		
//		Connection   connection  = null;
//		try {
//			//DBコネクションの取得
//			connection = DatabaseUtil.getConnection();
//			
//			//所属機関情報を取得する
//			return MasterKikanInfoDao.selectKikanList(connection, shubetuCd);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getKikanInfoList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
//	public Map getKikanInfoList(UserInfo userInfo) throws ApplicationException {
//
//		Connection   connection  = null;
//		Map codeMap = new HashMap();
//		try {	
//			//DBコネクションの取得
//			connection = DatabaseUtil.getConnection();
//						
//			//------索引データ
//			//カテゴリリスト（索引用）を取得
//			List kategoriList = MasterKikanInfoDao.selectKikanShubetuList(connection);;			
//				
//			//------一覧データ
//			//機関種別（区分）ごとに所属機関リストを作成する
//			List ichiranList = new ArrayList();
//			if(kategoriList != null){
//				Iterator iterator = kategoriList.iterator();			
//				while(iterator.hasNext()){
//					Map shubetuMap = (Map)iterator.next();
//					String shubetuCd = (String)shubetuMap.get("SHUBETU_CD");		//種別コード
//					String shubetuName = (String)shubetuMap.get("SHUBETU_NAME");	//機関種別名
//
//					//機関種別コードに該当する所属機関リストを取得
//					List kikanList = MasterKikanInfoDao.selectKikanList(connection, shubetuCd);
//					
//					//一覧用のマップを作成（１つの機関種別に対しての所属機関リスト）
//					Map ichiranMap = new HashMap();
//					ichiranMap.put("KIKAN_LIST", kikanList);			//所属機関リスト
//					ichiranMap.put("SHUBETU_NAME", shubetuName);		//所属機関種別名
//					ichiranMap.put("SHUBETU_CD", shubetuCd);			//所属機関種別コード			
//					
//					//一覧用リストに追加
//					ichiranList.add(ichiranMap);
//				}
//			}	
//			
//			//コード表用のマップを作成
//			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, kategoriList);		//索引用リスト
//			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//一覧用リスト
//					
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//					
//		return codeMap;
//	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getKikanInfoList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public Map getKikanInfoList(UserInfo userInfo) throws ApplicationException {

		Connection   connection  = null;
		Map codeMap = new HashMap();
		try {	
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
						
			//------索引データ
			List indexList = MasterKikanInfoDao.selectKikanCodeIndex(connection);		
				
			//------一覧データ
			List ichiranList = MasterKikanInfoDao.selectKikanCodeList(connection);;		
			
			//コード表用のマップを作成
			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, indexList);		//索引用リスト
			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//一覧用リスト
					
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
					
		return codeMap;
	}




	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getKikanShubetuInfoList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
//	public List getKikanShubetuInfoList(UserInfo userInfo) throws ApplicationException {
//
//		//種別コードの一覧を取得する
//		Connection   connection  = null;
//		try {
//			//DBコネクションの取得
//			connection = DatabaseUtil.getConnection();
//				
//			//所属機関種別情報を取得する
//			return MasterKikanInfoDao.selectKikanShubetuList(connection);			
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getBunkaNameList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
//	public List getBunkaNameList(UserInfo userInfo, String buName) throws ApplicationException {
//		Connection   connection  = null;
//		try {
//			//DBコネクションの取得
//			connection = DatabaseUtil.getConnection();
//			
//			//分科名を取得する
//			return MasterSaimokuInfoDao.selectBunkaNameList(connection, buName);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getSaimokuInfoList(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
	 */
//	public List getSaimokuInfoList(UserInfo userInfo, String bunkaName) throws ApplicationException {
//
//		Connection   connection  = null;
//		try {
//			//DBコネクションの取得
//			connection = DatabaseUtil.getConnection();
//			
//			//分科名を取得する
//			return MasterSaimokuInfoDao.selectSaimokuInfoList(connection, bunkaName);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}
	
	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getBuNameList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
//	public List getBuNameList(UserInfo userInfo) throws ApplicationException {
//		Connection   connection  = null;
//		try {
//			//DBコネクションの取得
//			connection = DatabaseUtil.getConnection();
//			
//			//分野名を取得する
//			return MasterSaimokuInfoDao.selectBunyaNameList(connection);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}


	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getSaimokuInfoList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public Map getSaimokuInfoList(UserInfo userInfo) throws ApplicationException {
		
		Connection   connection  = null;
		Map codeMap = new HashMap();
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			
			//------索引データ
			//分野名リスト（索引用）を取得
			List bunyaNameList = MasterSaimokuInfoDao.selectBunyaNameList(connection);
						

			List ichiranList = new ArrayList();
			
			//------一覧データ
			//分野ごとに分科リストを作成する
			if(bunyaNameList != null){		
				
				Iterator iterator = bunyaNameList.iterator();	
				while(iterator.hasNext()){
					Map bunyaMap = (Map)iterator.next();
					String bunyaName = (String)bunyaMap.get("BUNYA_NAME");			//分野名
					String bunyaCd = (String)(bunyaMap.get("BUNYA_CD")).toString();	//分野コード
					
					//分野コードに該当する分科リストを取得
					List bunyaBunkaList =MasterSaimokuInfoDao.selectBunkaNameList(connection, bunyaCd);
				
					//分科コードに該当する細目リストを取得
					List bunkaList = new ArrayList();
					if(bunyaBunkaList != null){
						Iterator ite = bunyaBunkaList.iterator();			
						while(ite.hasNext()){
							Map saimokuMap = (Map)ite.next();
							String bunkaName = (String)saimokuMap.get("BUNKA_NAME");	//分科名
							String bunkaCd = (String)saimokuMap.get("BUNKA_CD");		//分科コード
							
							//分科ごとの細目リストを取得
							List saimokuList = MasterSaimokuInfoDao.selectSaimokuInfoList(connection, bunkaCd);
							
							//分科ごとのマップを作成（１つの分科に対しての細目リスト）
							Map bunkaMap = new HashMap();
							
							bunkaMap.put("SAIMOKU_LIST", saimokuList);	//分科ごとの細目リスト
							bunkaMap.put("BUNKA_NAME", bunkaName);		//分科名						
							
							//分科リストに追加
							bunkaList.add(bunkaMap);
						}
					}
					
					//一覧用のマップを作成（１つの分野に対しての分科リスト）
					Map ichiranMap = new HashMap();
					ichiranMap.put("BUNYA_NAME", bunyaName);		//分野名
					ichiranMap.put("BUNYA_CD", bunyaCd);			//分野名コード
					ichiranMap.put("BUNKA_LIST", bunkaList);		//分野名毎の分科リスト
					
					//一覧用リストに追加
					ichiranList.add(ichiranMap);			
				}
			}
			
			//コード表用のマップを作成
			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, bunyaNameList);	//索引用リスト
			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//一覧用リスト
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return codeMap;

	}


	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getBukyokuInfoList(jp.go.jsps.kaken.model.vo.UserInfo, java.lang.String)
	 */
//	public List getBukyokuInfoList(UserInfo userInfo, String index) throws ApplicationException {
//
//		Connection   connection  = null;
//		try {
//			//DBコネクションの取得
//			connection = DatabaseUtil.getConnection();
//			
//			//部局情報を取得する
//			return MasterBukyokuInfoDao.selectBukyokuInfoList(connection, index);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//
//	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getBukyokuInfoList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
	public Map getBukyokuInfoList(UserInfo userInfo) throws ApplicationException {

		Connection   connection  = null;
		Map codeMap = new HashMap();
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
						
			//------索引データ
			//カテゴリリスト（索引用）を取得
			List indexList = MasterBukyokuInfoDao.selectKategoriInfoList(connection);;
				
			//------一覧データ
			//カテゴリごとに部局リストを作成する
			List ichiranList = new ArrayList();
			if(indexList != null){
				Iterator iterator = indexList.iterator();			
				while(iterator.hasNext()){
					Map kategoriMap = (Map)iterator.next();
					String bukaKategori = (String)kategoriMap.get("BUKA_KATEGORI");
					String kategoriName = (String)kategoriMap.get("KATEGORI_NAME");

					//カテゴリに該当する部局リストを取得
					List bukyokuList = MasterBukyokuInfoDao.selectBukyokuInfoList(connection, bukaKategori);
					
					//一覧用のマップを作成（１つのカテゴリに対しての部局リスト）
					Map ichiranMap = new HashMap();
					ichiranMap.put("BUKA_KATEGORI", bukaKategori);		//カテゴリ
					ichiranMap.put("KATEGORI_NAME", kategoriName);		//カテゴリ名称
					ichiranMap.put("BUKYOKU_LIST", bukyokuList);		//部局リスト
					ichiranList.add(ichiranMap);
				}
			}
			
			//コード表用のマップを作成
			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, indexList);		//索引用リスト
			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//一覧用リスト
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}			
			
		return codeMap;
	}

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.model.ICodeMaintenance#getSakuinList(jp.go.jsps.kaken.model.vo.UserInfo)
	 */
//	public List getSakuinList(UserInfo userInfo) throws ApplicationException {
//		
//		Connection   connection  = null;
//		try {
//			//DBコネクションの取得
//			connection = DatabaseUtil.getConnection();
//			
//			//索引リストを取得する
//			return MasterBukyokuInfoDao.selectSakuinList(connection);
//		
//		} finally {
//			DatabaseUtil.closeConnection(connection);
//		}
//	}


	/**
	 * キーワード一覧表示情報を取得する
	 * @param userInfo
	 * @return　キーワード一覧表示情報
	 * @throws ApplicationException
	 */
	public Map getKeywordInfoList(UserInfo userInfo) throws ApplicationException {
		
		Connection   connection  = null;
		Map codeMap = new HashMap();
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
			
			//------索引データ
			//分野名リスト（索引用）を取得
			List bunyaNameList = MasterKeywordInfoDao.selectBunyaNameList(connection);
						

			List ichiranList = new ArrayList();
			
			//------一覧データ
			//分野ごとに分科リストを作成する
			if(bunyaNameList != null){		
				
				Iterator iterator = bunyaNameList.iterator();	
				while(iterator.hasNext()){
					Map bunyaMap = (Map)iterator.next();
					String bunyaName = (String)bunyaMap.get("BUNYA_NAME");			//分野名
					String bunyaCd = (String)(bunyaMap.get("BUNYA_CD")).toString();	//分野コード
					
					//分野コードに該当する分科リストを取得
					List bunyaBunkaList =MasterKeywordInfoDao.selectBunkaNameList(connection, bunyaCd);
				
					//分科コードに該当する細目リストを取得
					List bunkaList = new ArrayList();
					if(bunyaBunkaList != null){
						Iterator ite = bunyaBunkaList.iterator();			
						while(ite.hasNext()){
							Map saimokuMap = (Map)ite.next();
							String bunkaName = (String)saimokuMap.get("BUNKA_NAME");	//分科名
							String bunkaCd = (String)saimokuMap.get("BUNKA_CD");		//分科コード
							
							//分科ごとの細目リストを取得
							List saimokuList = MasterKeywordInfoDao.selectKeywordInfoList(connection, bunkaCd);
							
							//分科ごとのマップを作成（１つの分科に対しての細目リスト）
							Map bunkaMap = new HashMap();
							
							bunkaMap.put("SAIMOKU_LIST", saimokuList);	//分科ごとの細目リスト
							bunkaMap.put("BUNKA_NAME", bunkaName);		//分科名						
							
							//分科リストに追加
							bunkaList.add(bunkaMap);
						}
					}
					
					//一覧用のマップを作成（１つの分野に対しての分科リスト）
					Map ichiranMap = new HashMap();
					ichiranMap.put("BUNYA_NAME", bunyaName);		//分野名
					ichiranMap.put("BUNYA_CD", bunyaCd);			//分野名コード
					ichiranMap.put("BUNKA_LIST", bunkaList);		//分野名毎の分科リスト
					
					//一覧用リストに追加
					ichiranList.add(ichiranMap);			
				}
			}
			
			//コード表用のマップを作成
			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, bunyaNameList);	//索引用リスト
			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//一覧用リスト
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return codeMap;

	}

	/**
	 * 領域マスタ一覧情報取得
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public Map getRyoikiInfoList(UserInfo userInfo) throws ApplicationException {

		Connection   connection  = null;
		Map codeMap = new HashMap();
		try {
			//DBコネクションの取得
			connection = DatabaseUtil.getConnection();
						
			//------索引データ
			//カテゴリリスト（索引用）を作成
			List indexList = new ArrayList(2);
			Map map = new HashMap();
			map.put("KUBUN", "1");
			map.put("NAME",  "「計画研究」に係る研究課題の応募書類を提出する時期に当たる研究領域");
			indexList.add(map);
			map = new HashMap();
			map.put("KUBUN", "2");
			map.put("NAME",  "「公募研究」に係る研究課題の応募書類を提出する時期に当たる研究領域");
			indexList.add(map);
				
			//------一覧データ
			//カテゴリごとに研究領域リストを作成する
			List ichiranList = new ArrayList();
			if(indexList != null){
				Iterator iterator = indexList.iterator();			
				while(iterator.hasNext()){
					Map kategoriMap = (Map)iterator.next();
					String bukaKategori = (String)kategoriMap.get("KUBUN");
					String kategoriName = (String)kategoriMap.get("NAME");

					//カテゴリに該当する部局リストを取得
					List ryoikiList = MasterRyouikiInfoDao.selectRyoikiInfoList(connection, bukaKategori);
					
					//一覧用のマップを作成（１つのカテゴリに対しての部局リスト）
					Map ichiranMap = new HashMap();
					ichiranMap.put("KUBUN", bukaKategori);		//カテゴリ
					ichiranMap.put("NAME",  kategoriName);		//カテゴリ名称
					ichiranMap.put("RYOIKI_LIST", ryoikiList);		//領域リスト
					ichiranList.add(ichiranMap);
				}
			}
			
			//コード表用のマップを作成
			codeMap.put(ICodeMaintenance.KEY_INDEX_LIST, indexList);		//索引用リスト
			codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);		//一覧用リスト
		
		} finally {
			DatabaseUtil.closeConnection(connection);
		}			
			
		return codeMap;
	}

//2006/07/24　苗　追加ここから
    /**
     * 領域マスタ（新規領域）一覧情報取得
     * @param userInfo
     * @return
     * @throws ApplicationException
     */
    public Map getRyoikiSinnkiInfoList(UserInfo userInfo) throws ApplicationException {

        Connection   connection  = null;
        Map codeMap = new HashMap();
        try {
            //DBコネクションの取得
            connection = DatabaseUtil.getConnection();
                        
            //------一覧データ
            //カテゴリごとに研究領域リストを作成する
            List ichiranList = new ArrayList();

            //カテゴリに該当する部局リストを取得
            List ryoikiList = MasterRyouikiInfoDao.selectRyoikiSinnkiInfoList(connection);
            
            //一覧用のマップを作成（１つのカテゴリに対しての部局リスト）
            Map ichiranMap = new HashMap();
            ichiranMap.put("RYOIKI_LIST", ryoikiList);      //領域リスト
            ichiranList.add(ichiranMap);
            
            //コード表用のマップを作成
            codeMap.put(ICodeMaintenance.KEY_SEARCH_LIST, ichiranList);     //一覧用リスト
        
        } finally {
            DatabaseUtil.closeConnection(connection);
        }           
            
        return codeMap;
    }
//2006/07/24 苗　追加ここまで    
}
