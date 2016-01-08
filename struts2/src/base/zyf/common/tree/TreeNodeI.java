/**
 * 
 * 项目名称：struts2
 * 制作时间：May 19, 20099:39:35 AM
 * 包名：base.zyf.common.tree
 * 文件名：TreeNodeI.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree;

import java.util.List;
/**
 * 
 * 树的基本接口
 * @author zhaoyifei
 * @version 1.0
 */
public interface TreeNodeI {

	public static final String TREE_ROOT="tree_root";
	
	/**
	 * 
	 * 功能描述 返回节点标识符
	 * @return
	 * May 20, 2009 2:19:50 PM
	 * @version 1.0
	 * @author Administrator
	 */
	public String getId();
	
	/**
	 * 
	 * 功能描述 得到显示名字
	 * @return 
	 * May 19, 2009 10:22:22 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract String getLabel();
	
	/**
	 * 
	 * 功能描述 得到节点id
	 * @return
	 * May 19, 2009 10:25:02 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract String getName();
	
	/**
	 * 
	 * 功能描述 得到父节点
	 * @return
	 * May 19, 2009 10:25:11 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract TreeNodeI getParent();

	/**
	 * 
	 * 功能描述 得到所有子节点
	 * @return
	 * May 19, 2009 10:25:38 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract List getChildren();

	

	/**
	 * 
	 * 功能描述 得到扩展类
	 * @return
	 * May 19, 2009 10:26:40 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract Object getExtender();

	/**
	 * 
	 * 功能描述 得到节点的别名
	 * @return
	 * May 19, 2009 5:26:44 PM
	 * @version 1.0
	 * @author Administrator
	 */
	public abstract String nickName();
	
	/**
	 * 
	 * 功能描述 得到层数
	 * @return
	 * May 19, 2009 10:27:24 AM
	 * @version 1.0
	 * @author Administrator
	 */
	public int getLayer();
}