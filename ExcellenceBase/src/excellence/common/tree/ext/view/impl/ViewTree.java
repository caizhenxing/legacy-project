/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.ext.view.impl;

import excellence.common.tree.base.impl.TreeServiceImpl;
import excellence.common.tree.base.service.TreeControlNodeService;
/**
 * ��ͼ����ʵ��
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class ViewTree extends TreeServiceImpl {
	/**
     * ��ǰ��ѡ�еĽڵ�
     */
    protected ViewTreeControlNode selected = null;
	
    /**
	 * �������ڵ��ѡ��״̬
	 * @param String id �ڵ�id
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
	 * �õ����ڵ��� ��������
	 * @param
	 * @version 2008-1-24
	 * @return ���ڵ���
	 */
	public int getWidth() {
        if (root == null)
            return (0);
        else
            return (getWidth((ViewTreeControlNode)root));
    }
	
	/**
	 * �õ����ڵ��� ���ڲ�ʹ��
	 * @param
	 * @version 2008-1-24
	 * @return ���ڵ���
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
