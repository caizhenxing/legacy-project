/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.base.service;
/**
 * 这是个保留的接口
 * 隔离数据库对叶子节点权限信息的封装 类似po
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public interface LeafRightNodeService {
	String getId();
	void setId(String id);
	
	/**
	 * 树的叶子节点id
	 * @return
	 */
	String getTreeId();
	
	/**
	 * 树的叶子节点id
	 * @return String treeId
	 */
	void setTreeId(String treeId);
	
	/**
	 * 节点类型 leaf_right_btn  leaf_right代表节点权限 btn 代表按钮权限
	 * @return
	 */
	String getType();
	void setType(String type);
	
	/**
	 * 节点图标的集合像expanded=folderOpen.gif;closed=folderClose.gif;leaf=leaf.gif;
	 * @return
	 */
	String getIcon();
	void setIcon(String icons);
	
	/**
	 * 节点显示的名称显示的是节点的功能 增加按钮 查询按钮 
	 * @return
	 */
	String getLabel();
	void setLabel(String label);
	
	/**
	 * 节点唯一标识 格式 btn_dept_leaf_add 见名知意 部门叶子节点的增加按钮
	 * @return
	 */
	String getNickName();
	void setNickName(String nickName);
	
	/**
	 * 一些备注信息
	 * @return
	 */
	String getRemark();
	void setRemart(String remark);
}
