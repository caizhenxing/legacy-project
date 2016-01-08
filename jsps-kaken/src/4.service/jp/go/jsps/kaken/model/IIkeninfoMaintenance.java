/*======================================================================
 *    SYSTEM      : 
 *    Source name : IIkeninfoMaintenance.java
 *    Description : 意見・要望情報に更新処理のインタフェース
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *　　2005/05/23    1.0         Xiang Emin     新規作成
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.IkenInfo;
import jp.go.jsps.kaken.model.vo.IkenSearchInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * @author user1
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
public interface IIkeninfoMaintenance {

	/**
	 * ご意見情報を新規作成する。
	 * 
	 * @param addInfo				作成するご意見情報。
	 * @return						
	 * @throws ApplicationException	
	 */
	public void insert(IkenInfo addInfo) throws ApplicationException;

	/**
	 * 意見情報を検索する。
	 * @param searchInfo           意見検索条件情報
	 * @return						取得した申請情報ページオブジェクト
	 * @throws ApplicationException	
	 * @throws NoDataFoundException		対象データが見つからない場合の例外。
	 */
	public Page searchIken(IkenSearchInfo searchInfo) 
		throws NoDataFoundException, ApplicationException;
	
	/**
	 * 意見情報を抽出する。
	 * @param system_no
	 * @param taisho_id
	 * @return
	 * @throws NoDataFoundException
	 * @throws ApplicationException
	 */
	public IkenInfo selectIkenDataInfo(String system_no, String taisho_id)
    	throws NoDataFoundException, ApplicationException;

	/**
	 * CSV出力用の意見情報を検索する。
	 * 
	 * @param searchInfo			検索する条件情報。
	 * @return						取得した意見情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public List searchCsvData(IkenSearchInfo searchInfo) throws ApplicationException;
}
