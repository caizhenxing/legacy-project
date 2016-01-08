/**
 * 沈阳卓越科技有限公司
 * 2008-6-10
 */
package et.bo.callcenter.bo.conf;

import java.util.List;

import excellence.common.tools.LabelValueBean;

/**
 * @author zhang feng
 * 
 */
public interface ConfService {

	/**
	 * 得到会议的列表的信息
	 * 
	 * @return
	 */
	public List confDeployList(String roomno);

	/**
	 * 处理哪个会议里的哪组信息改变为相应的状态
	 * 
	 * @param id
	 */
	public void operConf(String id,String state);
	
	/**
	 * 得到所有正在进行的会议室的列表
	 * @return 会议室的列表信息
	 */
	public List<LabelValueBean> getAllConfList();

}
