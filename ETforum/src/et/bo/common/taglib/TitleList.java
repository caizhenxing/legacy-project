/**
 * 	@(#)TitleList.java   Dec 22, 2006 2:32:27 PM
 *	 ¡£ 
 *	 
 */
package et.bo.common.taglib;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author zhangfeng
 * @version Dec 22, 2006
 * @see
 */
public class TitleList extends TagSupport{
	
	private String areaId = "";

	public String getAreaId() {
		areaId = (String)pageContext.getAttribute("itemid",PageContext.REQUEST_SCOPE);
		return areaId;
	}

}
