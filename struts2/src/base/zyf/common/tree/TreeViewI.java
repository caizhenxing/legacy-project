/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 19, 20099:39:35 AM
 * ������base.zyf.common.tree
 * �ļ�����TreeViewI.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree;

/**
 * ������ʾtree�Ľӿڣ���Ҫ����<code>TreeControlTag</code>�е���ʵ����
 * @author zhaoyifei
 * @version 1.0
 */
public interface TreeViewI extends TreeNodeI{
	
	/**
	 * 
	 * �������� �õ�action����
	 * @return
	 * May 19, 2009 10:41:37 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract String getAction();
	

	/**
	 * 
	 * �������� �Ƿ�չ��
	 * @return true չ����false û��չ��
	 * May 19, 2009 10:43:49 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract boolean isExpanded();

	/**
	 * 
	 * �������� �����Ƿ�չ��
	 * @param expanded
	 * May 19, 2009 10:46:53 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract void setExpanded(boolean expanded);

	/**
	 * 
	 * �������� ������ʾͼ��
	 * @return
	 * May 19, 2009 10:47:13 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract String getIcon();



	/**
	 * 
	 * �������� �����Ƿ�ѡ��
	 * @param selected
	 * May 19, 2009 10:48:11 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract void setSelected(boolean selected);

	
	
	/**
	 * 
	 * �������� �õ���ȣ���ʾ�ã����Ϊչ���ڵ��������
	 * @return
	 * May 19, 2009 10:49:50 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract int getWidth();
	

	/**
	 * 
	 * �������� ����ͼ��
	 * @param string
	 * May 19, 2009 10:50:46 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract void setIcon(String string);


	/**
	 * 
	 * �������� ��������
	 * @param string
	 * May 19, 2009 10:51:17 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract void setAction(String string);
	
	/**
	 * 
	 * �������� �ж��Ƿ������ڵ�
	 * @return
	 * May 19, 2009 10:56:05 AM
	 * @version 1.0
	 * @author Administrator
	 */
	public abstract boolean isLast();

}
