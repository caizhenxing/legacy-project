/**
 * 
 * 项目名称：struts2
 * 制作时间：May 19, 20099:39:35 AM
 * 包名：base.zyf.common.tree
 * 文件名：TreeViewI.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree;

/**
 * 用于显示tree的接口，主要用于<code>TreeControlTag</code>中的现实作用
 * @author zhaoyifei
 * @version 1.0
 */
public interface TreeViewI extends TreeNodeI{
	
	/**
	 * 
	 * 功能描述 得到action链接
	 * @return
	 * May 19, 2009 10:41:37 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract String getAction();
	

	/**
	 * 
	 * 功能描述 是否展开
	 * @return true 展开，false 没有展开
	 * May 19, 2009 10:43:49 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract boolean isExpanded();

	/**
	 * 
	 * 功能描述 设置是否展开
	 * @param expanded
	 * May 19, 2009 10:46:53 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract void setExpanded(boolean expanded);

	/**
	 * 
	 * 功能描述 设置显示图标
	 * @return
	 * May 19, 2009 10:47:13 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract String getIcon();



	/**
	 * 
	 * 功能描述 设置是否被选中
	 * @param selected
	 * May 19, 2009 10:48:11 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract void setSelected(boolean selected);

	
	
	/**
	 * 
	 * 功能描述 得到宽度，显示用，宽度为展开节点的最大层数
	 * @return
	 * May 19, 2009 10:49:50 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract int getWidth();
	

	/**
	 * 
	 * 功能描述 设置图标
	 * @param string
	 * May 19, 2009 10:50:46 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract void setIcon(String string);


	/**
	 * 
	 * 功能描述 设置链接
	 * @param string
	 * May 19, 2009 10:51:17 AM
	 * @version 1.0
	 * @author 赵一非
	 */
	public abstract void setAction(String string);
	
	/**
	 * 
	 * 功能描述 判断是否是最后节点
	 * @return
	 * May 19, 2009 10:56:05 AM
	 * @version 1.0
	 * @author Administrator
	 */
	public abstract boolean isLast();

}
