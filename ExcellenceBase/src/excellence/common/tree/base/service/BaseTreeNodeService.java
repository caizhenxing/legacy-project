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
import java.util.Date;
/**
* 树节点的基本属性定义
*
* @version 	jan 24 2008 
* @author 王文权
*/
public interface BaseTreeNodeService extends Serializable , Comparable ,Cloneable{
//	*****************树的基本属性的get set 方法*********************

	/**
	 * 
	 * 得到树节点的Id
	 * @param 
	 * @version 2008-1-4
	 * @return String id 树节点的唯一标识
	 * @throws
	 */
	String getId();
	/**
	 * 
	 * 设置树的Id
	 * @param String　id 树节点的唯一标识
	 * @version 2008-1-4
	 * @return String
	 * @throws
	 */
	void setId(String id);

	/**
	 * 
	 * 得到树节点的父节点Id
	 * @param 
	 * @version 2008-1-4
	 * @return String 树的父节点Id
	 * @throws
	 */
	String getParentId();
	/**
	 * 
	 * 设置树节点的父节点Id
	 * @param String　parentId 树的父节点Id
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setParentId(String parentId);
	
	/**
	 * 
	 * 得到树节点显示的名称
	 * @param
	 * @version 2008-1-4
	 * @return String label树显示的名称
	 * @throws
	 */
	String getLabel();
	/**
	 * 
	 * 设置树节点显示的名称
	 * @param　String　lable 树显示的名称
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	void setLabel(String lable);

	/**
	 * 
	 * 得到树节点的删除标志
	 * @param
	 * @version 2008-1-4
	 * @return String deleteMark 逻辑删除标志
	 * @throws
	 */
	String getDeleteMark();
	/**
	 * 
	 * 设置树节点的删除标志
	 * @param　String deleteMark 逻辑删除标志 
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setDeleteMark(String deleteMark);
	/**
	 * 
	 * 得到树节点的创建时间
	 * @param
	 * @version 2008-1-4
	 * @return Date createTime 树的创建时间
	 * @throws
	 */
	Date getCreateTime();
	
	/**
	 * 
	 * 设置树节点的创建时间
	 * @param　Date createTime 树的创建时间
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setCreateTime(Date createTime);
	/**
	 * 
	 * 得到树节点的修改时间
	 * @param
	 * @version 2008-1-4
	 * @return Date modifyTime 树的修改时间
	 * @throws
	 */
	Date getModifyTime();
	/**
	 * 
	 * 设置树的修改时间
	 * @param　Date modifyTime 树的修改时间
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setModifyTime(Date modifyTime);
	/**
     * 树节点是否为根节点0是1不是
     * @param
     * @version 2008-1-26
     * @return String 0是1不是
     * @throws
     */
	String getIsRoot();
	/**
     * 树节点是否为根节点0是1不是
     * @param String 0是1不是
     * @version 2008-1-26
     * @return 
     * @throws
     */
	void setIsRoot(String isRoot);
	/**
	 * 
	 * 得到树节点的备注信息
	 * @param
	 * @version 2008-1-4
	 * @return String remark 树节点的备注信息
	 * @throws
	 */
	String getRemark();
	/**
	 * 
	 * 设置树节点的备注信息
	 * @param　String remark  树节点的备注信息
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setRemark(String remark);
	/**
	 * 
	 * 设置树节点的呢称
	 * @param　String nickName 树节点的呢称
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setNickName(String nickName);
	/**
	 * 
	 * 得到树节点的呢称
	 * @param　
	 * @version 2008-1-4
	 * @return String nickName 树节点的呢称
	 * @throws
	 */
	String getNickName();
	/**
	 * 
	 * 设置树节点的类型
	 * @param　String type 树节点的类型
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setType(String type);
	/**
	 * 
	 * 得到树节点的类型
	 * @param　
	 * @version 2008-1-4
	 * @return String type 树节点的类型
	 * @throws
	 */
	String getType();
	/**
	 * 
	 * 设置树节点的排序
	 * @param　String type 树节点的类型
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setLayerOrder(String layerOrder);
	/**
	 * 
	 * 得到树节点的排序
	 * @param　
	 * @version 2008-1-4
	 * @return String type 树节点的类型
	 * @throws
	 */
	String getLayerOrder();
	/**
	 * 
	 * 得到树节点的扩展属性
	 * @param　
	 * @version 2008-1-4
	 * @return TreeNodeExtendedService tnes
	 * @throws
	 */
	TreeNodeExtendedService getTreeNodeExtendedService();
	/**
	 * 
	 * 设置树节点的扩展属性
	 * @param　TreeNodeExtendedService tnes
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setTreeNodeExtendedService(TreeNodeExtendedService tnes);
	/**
     * 树节点的深考贝
     * @param
     * @version 2008-1-26
     * @return Object 是树节点的实例
     * @throws CloneNotSupportedException;
     */
	Object clone() throws CloneNotSupportedException;
}
