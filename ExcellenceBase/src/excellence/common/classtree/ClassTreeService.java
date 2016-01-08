/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.classtree;

import java.util.List;
import java.util.NoSuchElementException;

import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;
/**
 * ������ȡ�����Ȳ���
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public interface ClassTreeService {
	/**
	 * ����id�õ����ڵ�nidkName
	 * @param String nickName
	 * @return String id
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	String getIdByNickname(String nickName);
	/**
	 * �����������нڵ�
	 * @return
	 */
	TreeService getParamTree();
	/**
	 * ����id�õ����ڵ�nidkName
	 * @param String nickName
	 * @return String id
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	String getNickNameById(String id);
	/**
	 * ����id�õ����ڵ�
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	TreeControlNodeService getNodeById(String id);
	/**
	 * ����id�õ����ڵ�label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	String getLabelById(String id);

	/**
	 * ����nickName�õ����ڵ�
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	TreeControlNodeService getNodeByNickName(String nickName);
	/**
	 * ����nickName�õ����ڵ�label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	String getLabelByNickName(String nickName);
	/**
	 * ����label value ���� list��
	 * @param String nickName
	 * @return List<LabelValueBean>
	 * @exception
	 */
	List<LabelValueBean> getLabelVaList(String nickName);
	/**
	 * ����label value ���� list��
	 * @param String nickName
	 * @param boolean needRoot �Ƿ���Ҫ���ڵ�
	 * @return List<LabelValueBean>
	 * @exception
	 */
	List<LabelValueBean> getLabelVaList(String nickName,boolean needRoot);
	/**
	 * ����label value ���� list��
	 * @param String nickName
	 * @param String changeRootId �޸ĸ��ڵ�Id
	 * @param String changeRootLabel �޸ĸ��ڵ�Label
	 * @return List<LabelValueBean>
	 * @exception
	 */
	List<LabelValueBean> getLabelVaList(String nickName,String changeRootId,String changeRootLabel);
	/**
	 * ���������ڴ���
	 */
	void loadParamTree();
	/**
	 * ����������
	 */
	void reloadParamTree();
	/**
	 * ��ȡ����
	 * @param nickName
	 * @return
	 */
	TreeService getSubTreeByNickName(String nickName);
	
    /**
     * ȡ����
     * @param nickName ���ڵ�
     * @param action �¶���
     * @param target ��Ŀ��
     * @return
     */
	TreeService getSubTreeByNickName(String nickName,String action, String target);
	
	/**
	 * �������ڵ��action��Ϊ
	 * @param action ��Ϊ
	 * @param TreeService tree
	 */
	void setNodeActionFromTree(String action,TreeService tree);
	
	/**
	 * 
	 * ��Ҷ�ӽڵ�ӵ�ģ�����﹩Ҷ�ӽڵ���Ȩʹ��
	 * @param TreeService tree
	 */
	TreeService addLeafs2Tree(TreeService tree,String action,String target);
	/**
	 * 
	 * ��������Ա�ڵ�ӵ�ģ�����﹩����������Ȩʹ��
	 * @param TreeService tree
	 */
	TreeService addPersons2Tree(TreeService tree,String action,String target);
	/**
	 * �����ݿ����¼��־��Ϣ
	 * @param TreeContorlNodeService node
	 */
	//void log2db(TreeControlNodeService node,Map otherInfoMap);
}
