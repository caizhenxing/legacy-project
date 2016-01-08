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

import excellence.common.tree.base.impl.TreeServiceImpl;
import excellence.common.tree.base.service.TreeControlNodeService;
/**
 * 视图树的实现
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class ViewTree extends TreeServiceImpl {
	/**
     * 当前被选中的节点
     */
    protected ViewTreeControlNode selected = null;
	
    /**
	 * 设置树节点的选中状态
	 * @param String id 节点id
	 * @version 2008-1-24
	 * @return 
	 */
    public void selectNode(String id) {
        if (selected != null) {
            selected.setSelected(false);
            selected = null;
        }this.
        selected = (ViewTreeControlNode)findNode(id);
        if (selected != null)
            selected.setSelected(true);
    }
    
	/**
	 * 得到树节点宽度 给外界调的
	 * @param
	 * @version 2008-1-24
	 * @return 树节点宽度
	 */
	public int getWidth() {
        if (root == null)
            return (0);
        else
            return (getWidth((ViewTreeControlNode)root));
    }
	
	/**
	 * 得到树节点宽度 类内部使用
	 * @param
	 * @version 2008-1-24
	 * @return 树节点宽度
	 */
    int getWidth(ViewTreeControlNode node) {
  
        int width = node.getWidth();
        //System.out.println(node.getId());
        //System.out.println("00000"+width);
        if (!node.isExpanded())
        {
        	//System.out.println("11111"+width);
            return (width);
        }
        TreeControlNodeService children[] = node.findChildren();
        for (int i = 0; i < children.length; i++) {
            int current = getWidth((ViewTreeControlNode)children[i]);
            if (current > width)
                width = current;
        }
        return (width);
        //return (0);
    }
}
