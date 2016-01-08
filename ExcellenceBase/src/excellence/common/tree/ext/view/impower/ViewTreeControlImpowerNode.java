/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package excellence.common.tree.ext.view.impower;

import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
/**
 * 视图树权限节点
 * 这个类 扩展了ViewTreeControlNode 为了实现权限管理
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class ViewTreeControlImpowerNode extends ViewTreeControlNode {
	/*
	 * 临时组图标
	 */
	private String tmpGroupIcon;
	/*
	 * 临时用户图标
	 */
	private String tmpUserIcon;
	
	/**
	 * 
	 * 得到临时组图标
	 * @param 
	 * @version 2008-1-4
	 * @return String tmpGroupIcon 临时组图标
	 * @throws
	 */
	public String getTmpGroupIcon() {
		return tmpGroupIcon;
	}
	/**
	 * 
	 * 设置临时组图标
	 * @param String tmpGroupIcon 临时组图标
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	public void setTmpGroupIcon(String tmpGroupIcon) {
		this.tmpGroupIcon = tmpGroupIcon;
	}
	/**
	 * 
	 * 得到临时用户图标
	 * @param 
	 * @version 2008-1-4
	 * @return String tmpGroupIcon 临时组图标
	 * @throws
	 */
	public String getTmpUserIcon() {
		return tmpUserIcon;
	}
	/**
	 * 
	 * 设置临时用户图标
	 * @param String tmpGroupIcon 临时组图标
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	public void setTmpUserIcon(String tmpUserIcon) {
		this.tmpUserIcon = tmpUserIcon;
	}
	
	
}
