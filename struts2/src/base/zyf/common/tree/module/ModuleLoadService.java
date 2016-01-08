/**
 * 
 * 项目名称：struts2
 * 制作时间：May 20, 200910:08:56 AM
 * 包名：base.zyf.common.tree.module
 * 文件名：ModuleLoadService.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree.module;

import java.util.Map;

import base.zyf.common.tree.TreeNodeI;

/**
 * 主要作用是加载系统模块
 * @author zhaoyifei
 * @version 1.0
 */
public interface ModuleLoadService {

	public static final String SERVICE_NAME = "sys.tree.ModuleLoadService";
	/**
	 * 
	 * 功能描述 加载模块树，按照用户的权限进行加载
	 * @param userId 用户id
	 * @return
	 * May 20, 2009 10:12:19 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public Map<String, ModuleTreeRight> loadTreeByUser(String userId);
	
	/**
	 * 
	 * 功能描述 加载完整的模块树
	 * @return
	 * May 20, 2009 10:15:03 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public TreeNodeI loadTree();
}
