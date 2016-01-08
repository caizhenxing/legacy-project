package et.bo.oa.main.service;

import java.util.List;

import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p> 首页Service </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-15
 * 
 */
public interface MainSerivce {

	/**
	 * <p> 新闻列表 </p>
	 * @return
	 */
	public List newList();
	
	/**
	 * <p> 代办事宜列表 </p>
	 * @return
	 */
	public List waitingWork();
	
	/**
	 * <p> 公告列表 </p>
	 * @return
	 */
	public List afficheList();
	
	/**
	 * <p> 工作计划列表 </p>
	 * @return
	 */
	public List workList();
	
	/**
	 * <p> 内部邮件列表 </p>
	 * @return
	 */
	public List inemailList(String username);
	
	/**
	 * 邮件详细列表
	 * @param
	 * @version Sep 15, 2006
	 * @return
	 */
	public List emaliListIndex(IBaseDTO dto,PageInfo pi);
	public int getEmailIndexSize();
	
	/**
	 * 邮件详细信息
	 */
	public List getEmailInfo(String id);
	

	
}
