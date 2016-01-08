/**
 * className BaseTreeService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.base.service;

import java.io.Serializable;
/**
* 定义树的扩展属性
*
* @version 	jan 24 2008 
* 
* @author 王文权
*/
public interface TreeNodeExtendedService extends Serializable , Cloneable{
//	/**
//	 * 
//	 * 设置BaseTreeNodeService
//	 * @param BaseTreeService　bts
//	 * @version 2008-1-4
//	 * @return void
//	 * @throws
//	 */
//	void setBaseTreeNodeService(BaseTreeNodeService baseTreeNodeService);
//	/**
//	 * 
//	 * 得到BaseTreeService
//	 * @param 
//	 * @version 2008-1-4
//	 * @return BaseTreeService
//	 * @throws
//	 */
//	BaseTreeNodeService getBaseTreeNodeService();
//	/**
//     * 树节点的深考贝
//     * @param
//     * @version 2008-1-26
//     * @return Object 是树节点的实例
//     * @throws CloneNotSupportedException;
//     */
	Object clone() throws CloneNotSupportedException;
}
