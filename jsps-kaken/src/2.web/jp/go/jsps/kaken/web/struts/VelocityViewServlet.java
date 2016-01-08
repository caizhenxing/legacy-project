/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 * 
 *    ID RCSfile="$RCSfile: VelocityViewServlet.java,v $"
 *    Revision="$Revision: 1.1 $"
 *    Date="$Date: 2007/06/28 02:07:28 $"
 *
 *======================================================================*/
package jp.go.jsps.kaken.web.struts;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.ISettingKeys;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Velociyを使用して画面を表示するサーブレット。
 * 
 * ID RCSfile="$RCSfile: VelocityViewServlet.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class VelocityViewServlet
	extends org.apache.velocity.tools.view.servlet.VelocityViewServlet {

    //---------------------------------------------------------------------
    // Static data
    //---------------------------------------------------------------------
    /** 
     * ログ
     */
    private static Log log = LogFactory.getLog(VelocityViewServlet.class);

	/* (非 Javadoc)
	 * @see org.apache.velocity.tools.view.servlet.VelocityViewServlet#error(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Exception)
	 */
	protected void error(
		HttpServletRequest request,
		HttpServletResponse response,
		Exception exception )
		throws ServletException, IOException {
        
        log.info("Velocityマージ中に例外が発生しました。",exception);
        //ディスパッチャを取得
        RequestDispatcher error  = getServletContext().getRequestDispatcher(ApplicationSettings.getString(ISettingKeys.VM_ERROR_PAGE)); 
        //ディスパッチャの forward メソッドを利用
        error.forward(request, response);
	}
}
