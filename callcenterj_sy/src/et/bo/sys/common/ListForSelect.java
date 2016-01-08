/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-08-21
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.common;

import java.util.List;

import et.bo.stat.service.CorpInfoBySeatService;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * 列表显示用
 *
 * @version 	jan 01 2008 
 * @author 
 */
public class ListForSelect {
	/**
	 * 查询座席员的
	 * @return List<LabelValueBean> userid userid
	 */
	public static List<LabelValueBean> getUserList()
	{
		String sql = SysStaticParameter.QUERY_USER_SQL;
		CorpInfoBySeatService s = (CorpInfoBySeatService)SpringRunningContainer.getInstance().getBean("corpInfoBySeatService");
		
		return s.userLVQuery(sql);
	}

}
