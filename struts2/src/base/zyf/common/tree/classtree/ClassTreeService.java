/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 *  ��
 */
package base.zyf.common.tree.classtree;

import java.util.Map;
import java.util.NoSuchElementException;

import base.zyf.common.tree.TreeNodeI;
import base.zyf.common.tree.classtree.impl.ClassTreeServiceImpl;
import base.zyf.web.view.ComboSupportList;

/**
 * ������ȡ�����Ȳ���
 *
 * @version 2
 * @author zhaoyifei
 */
public interface ClassTreeService {
	
	
	/**
	 * ���������ڵ����� ������
	 */
	public static final String paramTreeRootNickName = "paramTreeRoot";
	
	public static final String SERVICE_NAME = "sys.tree.ClassTreeService";
	/**
	 * ����id�õ����ڵ�nidkName
	 * @param String nickName
	 * @return String id
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public String getIdByNickname(String nickName);
	/**
	 * �����������нڵ�
	 * @return
	 */
	public TreeNodeI getParamTree();
	
	/**
	 * ����id�õ����ڵ�
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public TreeNodeI getNodeById(String id);
	/**
	 * ����id�õ����ڵ�label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public String getLabelById(String id);

	/**
	 * ����nickName�õ����ڵ�
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public TreeNodeI getNodeByNickName(String nickName);
	/**
	 * ����nickName�õ����ڵ�label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public String getLabelByNickName(String nickName);
	/**
	 * ����label value ���� list��
	 * @param String nickName
	 * @return ComboSupportList
	 * @exception
	 */
	public ComboSupportList getLabelVaList(String nickName);
	/**
	 * ����label value ���� list��
	 * @param String nickName
	 * @param boolean needRoot �Ƿ���Ҫ���ڵ�
	 * @return ComboSupportList
	 * @exception
	 */
	public ComboSupportList getLabelVaList(String nickName,boolean needRoot);
	/**
	 * ���������ڴ���
	 */
	void loadParamTree();
	/**
	 * ����������
	 */
	public void reloadParamTree();
	
	public void setTreeId(Map<String, TreeNodeI> treeId);
	
	public void setTreeNick(Map<String, TreeNodeI> treeNick);
 
	public void setParamTree(TreeNodeI paramTree);
}
