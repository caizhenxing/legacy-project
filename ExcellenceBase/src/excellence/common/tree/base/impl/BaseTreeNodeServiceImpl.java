/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.base.impl;

import java.util.Date;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeNodeExtendedService;
/**
* 存的是树节点的属性信息
*
* @version 	jan 01 2008 
* @author 王文权
*/
public class BaseTreeNodeServiceImpl implements BaseTreeNodeService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//	*****************树节点的基本属性*********************
	/**
	 * 
	 * 树节点的id
	 * @version 2008-1-4
	 * @return String
	 * @throws
	 */
	private String id;
	/**
	 * 
	 * 树节点的类型
	 * 
	 * @version 2008-1-4
	 * @return String
	 * @throws
	 */
	private String type;
	/**
	 * 
	 * 树节点的父节点id
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private String parentId;
	
	/**
	 * 
	 * 树节点显示的名称
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	private String label;;

	/**
	 * 
	 * 树节点的删除标志
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private String deleteMark;
	/**
	 * 
	 * 树节点的排序
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private String layerOrder;
	/**
	 * 
	 * 树节点的创建时间
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private Date createTime;
	/**
	 * 
	 * 树节点是否为根节点
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	/**
	 * 
	 * 树节点的呢称
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private String isRoot;
	private String nickName; 
	/**
	 * 
	 * 树节点的修改时间
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private Date modifyTime;
	/**
	 * 
	 * 树节点的备注信息
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private String remark;
	/**
	 * 
	 * 树的扩展属性
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private TreeNodeExtendedService tnes;
	//	*****************树的基本属性的get set 方法*********************

	/**
	 * 
	 * 得到树节点的Id
	 * @param 
	 * @version 2008-1-4
	 * @return String id 树节点的唯一标识
	 * @throws
	 */
	public String getId()
	{
		return this.id;
	}
	/**
	 * 
	 * 设置树的Id
	 * @param String　id 树节点的唯一标识
	 * @version 2008-1-4
	 * @return String
	 * @throws
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * 
	 * 得到树节点的父节点Id
	 * @param 
	 * @version 2008-1-4
	 * @return String 树的父节点Id
	 * @throws
	 */
	public String getParentId()
	{
		return this.parentId;
	}
	/**
	 * 
	 * 设置树节点的父节点Id
	 * @param String　parentId 树的父节点Id
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * 
	 * 得到树节点显示的名称
	 * @param
	 * @version 2008-1-4
	 * @return String label树显示的名称
	 * @throws
	 */
	public String getLabel()
	{
		return this.label;
	}
	/**
	 * 
	 * 设置树节点显示的名称
	 * @param　String　lable 树显示的名称
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * 
	 * 得到树节点的删除标志
	 * @param
	 * @version 2008-1-4
	 * @return String deleteMark 逻辑删除标志
	 * @throws
	 */
	public String getDeleteMark()
	{
		return this.deleteMark;
	}
	/**
	 * 
	 * 设置树节点的删除标志
	 * @param　String deleteMark 逻辑删除标志 
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setDeleteMark(String deleteMark)
	{
		this.deleteMark = deleteMark;
	}
	/**
	 * 
	 * 得到树节点的创建时间
	 * @param
	 * @version 2008-1-4
	 * @return Date createTime 树的创建时间
	 * @throws
	 */
	public Date getCreateTime()
	{
		return this.createTime;
	}
	
	/**
	 * 
	 * 设置树节点的创建时间
	 * @param　Date createTime 树的创建时间
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	/**
	 * 
	 * 得到树节点的修改时间
	 * @param
	 * @version 2008-1-4
	 * @return Date modifyTime 树的修改时间
	 * @throws
	 */
	public Date getModifyTime()
	{
		return this.modifyTime;
	}
	/**
	 * 
	 * 设置树的修改时间
	 * @param　Date modifyTime 树的修改时间
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setModifyTime(Date modifyTime)
	{
		this.modifyTime = modifyTime;
	}
	
	/**
     * 树节点是否为根节点0是1不是
     * @param
     * @version 2008-1-26
     * @return String 0是1不是
     * @throws
     */
	public String getIsRoot()
	{
		return this.isRoot;
	}
	/**
     * 树节点是否为根节点0是1不是
     * @param String 0是1不是
     * @version 2008-1-26
     * @return 
     * @throws
     */
	public void setIsRoot(String isRoot)
	{
		this.isRoot = isRoot;
	}
	
	/**
	 * 
	 * 得到树节点的备注信息
	 * @param
	 * @version 2008-1-4
	 * @return String remark 树节点的备注信息
	 * @throws
	 */
	public String getRemark()
	{
		return this.remark;
	}
	/**
	 * 
	 * 设置树节点的备注信息
	 * @param　String remark  树节点的备注信息
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	/**
	 * 
	 * 设置树节点的呢称
	 * @param　String nickName 树节点的呢称
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}
	/**
	 * 
	 * 得到树节点的呢称
	 * @param　
	 * @version 2008-1-4
	 * @return String nickName 树节点的呢称
	 * @throws
	 */
	public String getNickName()
	{
		return this.nickName;
	}
	
	/**
	 * 
	 * 设置树节点的类型
	 * @param　String type 树节点的类型
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	/**
	 * 
	 * 得到树节点的类型
	 * @param　
	 * @version 2008-1-4
	 * @return String type 树节点的类型
	 * @throws
	 */
	public String getType()
	{
		return this.type;
	}
	
	/**
	 * 
	 * 设置树节点的排序
	 * @param　String type 树节点的类型
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setLayerOrder(String layerOrder)
	{
		this.layerOrder = layerOrder;
	}
	/**
	 * 
	 * 得到树节点的排序
	 * @param　
	 * @version 2008-1-4
	 * @return String type 树节点的类型
	 * @throws
	 */
	public String getLayerOrder()
	{
		return this.layerOrder;
	}
	/**
	 * 
	 * 得到树节点的扩展属性
	 * @param　
	 * @version 2008-1-4
	 * @return TreeNodeExtendedService tnes
	 * @throws
	 */
	public TreeNodeExtendedService getTreeNodeExtendedService()
	{
		return this.tnes; 
	}
	/**
	 * 
	 * 设置树节点的扩展属性
	 * @param　TreeNodeExtendedService tnes
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setTreeNodeExtendedService(TreeNodeExtendedService tnes)
	{
		this.tnes = tnes;
	}
	/**
	 * 
	 * 比较两个树节点的大小
	 * @param Object o 是树节点
	 * @version 2008-1-4
	 * @return int -1,0,1 分别对应 小于,等于,大于
	 * @throws
	 */
	public int compareTo(Object o) {
		
		// TODO Auto-generated method stub
		BaseTreeNodeService btn = (BaseTreeNodeService) o;
		String layerOrder = this.getLayerOrder();
		if(layerOrder == null)
		{
			layerOrder = "";
		}
		String btnOrder = btn.getLayerOrder();
		if(btnOrder == null)
		{
			btnOrder = "";
		}
		return layerOrder.compareTo(btnOrder);

	}
	/**
     * 树节点的深考贝
     * @param
     * @version 2008-1-26
     * @return Object 是树节点的实例
     * @throws CloneNotSupportedException;
     */
	public Object clone() throws CloneNotSupportedException {
		
		// TODO Auto-generated method stub
		
		//简单的属性复制
		BaseTreeNodeServiceImpl bts = new BaseTreeNodeServiceImpl();
		bts.setCreateTime(this.getCreateTime());
		bts.setDeleteMark(this.getDeleteMark());
		bts.setLabel(this.getLabel());
		bts.setModifyTime(this.getModifyTime());
		bts.setId(this.getId());
		bts.setParentId(this.getParentId());
		bts.setRemark(this.getRemark());
		bts.setLayerOrder(this.getLayerOrder());
		bts.setTreeNodeExtendedService((TreeNodeExtendedService)this.getTreeNodeExtendedService().clone());
		return bts;
	}
}
