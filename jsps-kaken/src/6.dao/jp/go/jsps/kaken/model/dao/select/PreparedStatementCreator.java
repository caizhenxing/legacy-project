/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.dao.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * プリコンパイルされたステートメントを作成するインターフェース。
 *
 * ID RCSfile="$RCSfile: PreparedStatementCreator.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:22 $"
 */
public interface PreparedStatementCreator{

	/**
	 * コネクションより、ステートメントを作成する。
	 * @param conn			コネクション
	 * @return				ステートメント
	 * @throws SQLException	
	 */
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException;

}
