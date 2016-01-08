/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.ext.view.impl;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeNodeExtendedService;
/**
 * 视图树属性节点
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class ViewTreeNode implements TreeNodeExtendedService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//*****************树的基本扩展属性*********************
	/**
	 * 注入BaseTreeNodeService
	 */
	//private BaseTreeNodeService baseTreeNodeService;
	/**
	 * 显示图标
	 */
	private String icon;
	/**
	 * 显示临时图标
	 */
	private String tmpIcon;
	/**
	 * 节点行为
	 */
	private String action;
	/**
	 * 节点 target
	 */
	private String target;
	/**
	 * 节点是否展开
	 */
	private boolean expanded;
	/**
	 * 节点域
	 */
	private String domain;
	/**
	 * 节点是否显示
	 */
	 private String tagShow;
	/**
	 * 节点宽度
	 */
	//*****************树的基本扩展属性的get set方法*********************
	/**
	public BaseTreeNodeService getBaseTreeNodeService() {
		// TODO Auto-generated method stub
		return this.baseTreeNodeService;
	}
	public void setBaseTreeNodeService(BaseTreeNodeService bts) {
		// TODO Auto-generated method stub
		this.baseTreeNodeService = bts;
	}
	*/
	/**
	 * 得到节点行为
	 * @param
	 * @version 2008-1-24
	 * @return String
	 */
	public String getAction() {
		return action;
	}
	/**
	 * 设置节电行为
	 * @param String action
	 * @version 2008-1-24
	 * @return
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * 得到域
	 * @param
	 * @version 2008-1-24
	 * @return String
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * 设置域
	 * @param String domain
	 * @version 2008-1-24
	 * @return
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * 得到expanded
	 * @param
	 * @version 2008-1-24
	 * @return boolean
	 */
	public boolean isExpanded() {
		return expanded;
	}
	/**
	 * 设置expanded
	 * @param
	 * @version 2008-1-24
	 * @return boolean
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	/**
	 * 得到图标
	 * @param
	 * @version 2008-1-24
	 * @return String
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置图标
	 * @param String icon
	 * @version 2008-1-24
	 * @return
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 得到target
	 * @param
	 * @version 2008-1-24
	 * @return String
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 
	 */
	/**
	 * 设置target
	 * @param String target
	 * @version 2008-1-24
	 * @return
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	
	
	public String getTmpIcon() {
		return tmpIcon;
	}
	public void setTmpIcon(String tmpIcon) {
		this.tmpIcon = tmpIcon;
	}
	public void setTagShow(String tagShow)
	{
		this.tagShow = tagShow;
	}
	public String getTagShow()
	{
		return this.tagShow;
	}
	/**
	 * 深拷贝
	 * @param 
	 * @version 2008-1-24
	 * @return Ojbect instance of ViewTreeNode
	 */
	public Object clone() throws CloneNotSupportedException {
		ViewTreeNode node = new ViewTreeNode();
		node.setAction(this.getAction());
		node.setDomain(this.getDomain());
		node.setExpanded(this.isExpanded());
		node.setIcon(this.icon);
		node.setTarget(this.getTarget());
		node.setTmpIcon(this.tmpIcon);
		node.setTagShow(this.tagShow);
		return node;
		
	}
}
