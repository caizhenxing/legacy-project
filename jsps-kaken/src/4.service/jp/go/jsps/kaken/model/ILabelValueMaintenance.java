/*======================================================================
 *    SYSTEM      : 電子申請システム（科学研究費補助金）
 *    Source name : ILabelValueMaintenance
 *    Description : ラベル管理を行うインターフェース
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;

/**
 * ラベル管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: ILabelValueMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface ILabelValueMaintenance {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	

	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
    
// 20050526 Start
    /**
	 * 研究領域区分を取得する。
	 * @return	研究領域区分リスト
     * @throws ApplicationException
	 */
	public List getKenkyuKubunList() throws ApplicationException;
// Horikoshi End

	/**
	 * 所属機関種別を取得する。
	 * @return	所属機関種別リスト
     * @throws ApplicationException
	 */
	public List getKikanShubetuCdList() throws ApplicationException;
	
	/**
	 * 事業名を取得する。
	 * @return	事業名リスト
     * @throws ApplicationException
	 */
	public List getJigyoNameList() throws ApplicationException;

	/**
	 * 事業名を取得する。
	 * 渡されたUserInfoが業務担当者の場合は、自分の担当する事業区分のみの
	 * 事業名リストが返る。
     * @param userInfo ユーザ情報
	 * @return	事業名リスト
     * @throws ApplicationException
	 */
	public List getJigyoNameList(UserInfo userInfo) throws ApplicationException;

	/**
	 * 指定された事業区分の事業名を取得する。
     * @param userInfo ユーザ情報
	 * @param	jigyoKubun	事業区分
	 * @return	事業名リスト
     * @throws ApplicationException
	 */
	public List getJigyoNameList(UserInfo userInfo, String jigyoKubun) throws ApplicationException;

	/**
	 * 事業名を取得する。
	 * 渡されたUserInfoが業務担当者の場合は、
	 * 自分の担当する事業・審査担当する事業区分（1または4の場合）のみの事業名リストが返る
     * @param userInfo ユーザ情報
	 * @return	事業名リスト
     * @throws ApplicationException
	 */
	public List getShinsaTaishoJigyoNameList(UserInfo userInfo) throws ApplicationException;
		
	/**
	 * 職種リストを取得する。
	 * @return	職種リスト
     * @throws ApplicationException
	 */
	public List getShokushuList() throws ApplicationException;

	/**
	 * カテゴリリストを取得する。
	 * @return	カテゴリリスト
     * @throws ApplicationException
	 */
	public List getKategoriCdList() throws ApplicationException;

	/**
	 * ラベル名を取得する。選択リストに表示するデータのみ取得する。
	 * 当該「ラベル区分」のデータが存在しなかった場合、NoDataFoundException を
	 * スローする。
	 * @param	labelKubun	ラベル区分
	 * @return				ラベル名リスト
     * @throws ApplicationException
	 */
	public List getLabelList(String labelKubun) throws ApplicationException;
	
	/**
	 * ラベル名を取得する。ラベル区分に一致するすべてのデータを取得する。
	 * 当該「ラベル区分」のデータが存在しなかった場合、NoDataFoundException を
	 * スローする。
	 * @param	labelKubun	ラベル区分
	 * @return				ラベル名リスト
     * @throws ApplicationException
	 */
	public List getAllLabelList(String labelKubun) throws ApplicationException;
	
	/**
	 * ラベル名を取得する。
	 * LabelValueBeanのListが、Listの形式で戻る。
	 * 当該「ラベル区分」のデータが存在しなかった場合、戻り値のList内には NULL が
	 * 格納される。
	 * @param	labelKubun	ラベル区分
	 * @return				ラベル名リスト
     * @throws ApplicationException
	 */
	public List getLabelList(String[] labelKubun) throws ApplicationException;	
	

	//2005.10.25 iso ラベルリストを一括取得するため追加
	/**
	 * ラベル名を取得する。
	 * LabelValueBeanのListが、Mapの形式で戻る。
	 * @param	labelKubun	ラベル区分
	 * @return				ラベルマップ
     * @throws ApplicationException
	 */
	public Map getLabelMap(String[] labelKubun) throws ApplicationException;
	
	/**
	 * ラベル名を取得する。ラベル区分に一致するすべてのデータを取得する。
	 * LabelValueBeanのListが、Listの形式で戻る。
	 * 当該「ラベル区分」のデータが存在しなかった場合、戻り値のList内には NULL が
	 * 格納される。
	 * @param	labelKubun	ラベル区分
	 * @return				ラベル名リスト
     * @throws ApplicationException
	 */
	public List getAllLabelList(String[] labelKubun) throws ApplicationException;

	/**
	 * ラベルマスタの１レコードをMap形式で返す。
	 * 当該データが存在しなかった場合、NoDataFoundException を
	 * スローする。
	 * @param	labelKubun	ラベル区分
	 * @param	value		値
	 * @return				ラベル名
     * @throws ApplicationException
	 */
	public Map selectRecord(String labelKubun, String value) throws ApplicationException;	
	
//	2005/04/18 追加 ここから----------
//	理由:海外分野追加のため

	/**
	 * 海外分野リストを取得する。
	 * @return	職種リスト
     * @throws ApplicationException
	 */
	public List getKaigaiBunyaList() throws ApplicationException;
	
//	2005/04/12 追加 ここまで----------

//2007/02/08 苗　削除ここから　使用しない    
//2006/02/15 追加 ここから----------
//	/**
//	 * 分野リストを取得する。
//	 * @return	職種リスト
//     * @throws ApplicationException
//	 */
//	public List getKiboRyoikiList() throws ApplicationException;
// syuu 追加 ここまで ----------
//2007/02/08　苗　削除ここまで    


	//2005/04/27 追加 ここから--------------
	//自分の担当する事業コードで絞り込んだ事業名を表示するため
	/**
	 * 事業名を取得する。
	 * 渡されたUserInfoが業務担当者の場合は、
	 * 自分の担当する事業CDのみの事業名リストが返る
     * @param userInfo ユーザ情報
	 * @return	事業名リスト
     * @throws ApplicationException
	 */
	public List getJigyoNameListByJigyoCds(UserInfo userInfo) throws ApplicationException;
	//追加 ここまで-------------------------
	

	/**
	 * 事業名を取得する。
	 * 渡されたUserInfoが業務担当者の場合は、
	 * 自分の担当する事業CD・事業区分（1または4の場合）のみの事業名リストが返る
     * @param userInfo ユーザ情報
     * @param jigyoKubun 事業区分
	 * @return	事業名リスト
     * @throws ApplicationException
	 */
	public List getJigyoNameListByJigyoCds(UserInfo userInfo, String jigyoKubun)
		throws ApplicationException;
	

	/**
	 * 事業名を取得する。
	 * 渡されたUserInfoが業務担当者の場合は、
	 * 自分の担当する事業・審査担当する事業区分（1または4の場合）のみの事業名リストが返る
     * @param userInfo ユーザ情報
	 * @return	事業名リスト
     * @throws ApplicationException
	 */
	public List getShinsaTaishoListByJigyoCds(UserInfo userInfo) throws ApplicationException;
    
    
//  2006/06/02 苗 追加ここから
    /**
     * 事業名（事業（基盤S,A,B）の削除）を取得する。
     * 渡されたUserInfoが業務担当者の場合は、
     * 自分の担当する事業・審査担当する事業区分（1または4の場合）のみの事業名リストが返る
     * @param userInfo ユーザ情報
     * @param jigyoCds 事業コードリスト
     * @param jigyoKubun 事業区分
     * @return  事業名リスト
     * @throws ApplicationException
     */
     public List getJigyoNameListByJigyoCds(UserInfo userInfo, String[] jigyoCds, String jigyoKubun) throws ApplicationException; 
//2006/06/02 苗 追加ここまで    
    
//    //2006/06/06 jzx　add start
//    //自分の担当する事業コードで絞り込んだ事業名を表示するため
//    /**
//     * 事業名を取得する。
//     * 渡されたUserInfoが業務担当者の場合は、
//     * 自分の担当する事業CDのみの事業名リストが返る
//     * @param userInfo ユーザ情報
//     * @return  事業名リスト
//     * @throws ApplicationException
//     */
//     public List getJigyoNameListByJigyoCds2(UserInfo userInfo) throws ApplicationException;
//    //2006/06/06 jzx　add end
//     
//    //2006/06/06 lwj　add start
//     /**
//      * 事業名を取得する。
//      * 渡されたUserInfoが業務担当者の場合は、
//      * 自分の担当する事業CDのみの事業名リストが返る
//      * @param userInfo ユーザ情報
//      * @param jigyoCd
//      * @return  事業名リスト
//      * @throws ApplicationException
//      */
//     public List getJigyoNameListByJigyoCds3(UserInfo userInfo, String jigyoCd)throws ApplicationException;
//    //2006/06/06 lwj　add end
}