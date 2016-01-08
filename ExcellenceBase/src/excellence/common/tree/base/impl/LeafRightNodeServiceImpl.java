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

import excellence.common.tree.base.service.LeafRightNodeService;
/**
 * 这是个保留的接口
 * 隔离数据库对叶子节点权限信息的封装 类似po
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class LeafRightNodeServiceImpl implements LeafRightNodeService {

	private String id;
	private String treeId;
	private String type;
	private String icon;
	private String label;
	private String nickName;
	private String remark;
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * 树的叶子节点id
	 * @return
	 */
	public String getTreeId()
	{
		return this.treeId;
	}
	
	/**
	 * 树的叶子节点id
	 * @return String treeId
	 */
	public void setTreeId(String treeId)
	{
		this.treeId = treeId;
	}
	
	/**
	 * 节点类型 leaf_right_btn  leaf_right代表节点权限 btn 代表按钮权限
	 * @return
	 */
	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
	/**
	 * 节点图标的集合像expanded=folderOpen.gif;closed=folderClose.gif;leaf=leaf.gif;
	 * @return
	 */
	public String getIcon()
	{
		return this.icon;
	}
	public void setIcon(String icons)
	{
		this.icon = icons;
	}
	
	/**
	 * 节点显示的名称显示的是节点的功能 增加按钮 查询按钮 
	 * @return
	 */
	public String getLabel()
	{
		return this.label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	/**
	 * 节点唯一标识 格式 btn_dept_leaf_add 见名知意 部门叶子节点的增加按钮
	 * @return
	 */
	public String getNickName()
	{
		return this.nickName;
	}
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}
	
	/**
	 * 一些备注信息
	 * @return
	 */
	public String getRemark()
	{
		return this.remark;
	}
	public void setRemart(String remark)
	{
		this.remark = remark;
	}
}
