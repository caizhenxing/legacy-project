/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2005/01/17
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei.validate;

import javax.servlet.http.*;

import jp.go.jsps.kaken.web.struts.ActionMapping;

import org.apache.struts.action.*;

/**
 * ID RCSfile="$RCSfile: IShinseiValidator.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:09 $"
 */
public interface IShinseiValidator {
	
	/**
	 * 形式チェック
	 * @param mapping
	 * @param request
	 * @return
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request, int page, ActionErrors errors);
	
	
}
