/**
 * className ClassTreeLoadService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 *  。
 */
package base.zyf.common.tree.classtree;

import java.util.Map;

import base.zyf.common.tree.TreeNodeI;


/**
 * 隔离数据库为ClassTreeService提供数据
 * 数据可来自db xml等
 *
 * @version 2
 * @author zhaoyifei
 */
public interface ClassTreeLoadService {
	public static final String SERVICE_NAME = "sys.tree.ClassTreeLoadService";
	/**
	 * 将db xml等的数据载入TreeNodeI里
	 * @return TreeNodeI
	 */
	public void loadTreeNodeService(ClassTreeService cts);
	
}
