/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 19, 20099:39:35 AM
 * ������base.zyf.common.tree
 * �ļ�����TreeNodeI.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree;

import java.util.List;
/**
 * 
 * ���Ļ����ӿ�
 * @author zhaoyifei
 * @version 1.0
 */
public interface TreeNodeI {

	public static final String TREE_ROOT="tree_root";
	
	/**
	 * 
	 * �������� ���ؽڵ��ʶ��
	 * @return
	 * May 20, 2009 2:19:50 PM
	 * @version 1.0
	 * @author Administrator
	 */
	public String getId();
	
	/**
	 * 
	 * �������� �õ���ʾ����
	 * @return 
	 * May 19, 2009 10:22:22 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract String getLabel();
	
	/**
	 * 
	 * �������� �õ��ڵ�id
	 * @return
	 * May 19, 2009 10:25:02 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract String getName();
	
	/**
	 * 
	 * �������� �õ����ڵ�
	 * @return
	 * May 19, 2009 10:25:11 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract TreeNodeI getParent();

	/**
	 * 
	 * �������� �õ������ӽڵ�
	 * @return
	 * May 19, 2009 10:25:38 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract List getChildren();

	

	/**
	 * 
	 * �������� �õ���չ��
	 * @return
	 * May 19, 2009 10:26:40 AM
	 * @version 1.0
	 * @author ��һ��
	 */
	public abstract Object getExtender();

	/**
	 * 
	 * �������� �õ��ڵ�ı���
	 * @return
	 * May 19, 2009 5:26:44 PM
	 * @version 1.0
	 * @author Administrator
	 */
	public abstract String nickName();
	
	/**
	 * 
	 * �������� �õ�����
	 * @return
	 * May 19, 2009 10:27:24 AM
	 * @version 1.0
	 * @author Administrator
	 */
	public int getLayer();
}