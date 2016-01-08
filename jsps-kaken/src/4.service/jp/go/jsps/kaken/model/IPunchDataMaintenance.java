/*
 * 作成日: 2004/10/19
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.PunchDataKanriInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;


/**
 * パンチデータの管理を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IPunchDataMaintenance.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IPunchDataMaintenance {
	
	/**
	 * 
	 * @param userinfo
	 * @return
	 * @throws ApplicationException
	 */
	public List selectList(UserInfo userinfo) throws ApplicationException;

	
	/**
	 * パンチデータを取得する
	 * @param userInfo ユーザ情報
	 * @throws ApplicationException　アプリケーションエラーが発生した場合
	 * @throws DataAccessException データベースアクセス中にエラーが発生した場合
	 */
	public FileResource getPunchDataResource(UserInfo userInfo, String punchShubetu) 
		throws ApplicationException;
		

	public PunchDataKanriInfo getPunchData (UserInfo userInfo, String punchShubetu) 
		throws ApplicationException;
		
	
}
