/**
 * className BaseTreeService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */


package excellence.common.tree.base.service;

import java.util.Map;

/**
 * 
* ��һ�����ӿ� �����˶��������Լ������ ���ӽڵ㡢ɾ���ڵ㡢�õ����ڵ��
*
* @version 	jan 24 2008 
* 
* @author ����Ȩ
*/
public interface TreeService {

    /**
     * �õ����ĸ��ڵ�
     * @param
     * @version 2008-1-25
     * @return TreeControlNodeService ���ڵ�
     */
    TreeControlNodeService getRoot();
    /**
     * �������ĸ��ڵ�
     * @param TreeControlNodeService root ���ڵ�
     * @version 2008-1-25
     * @return 
     */
    void setRoot(TreeControlNodeService root);
    /**
     * ����id�������Ľڵ�
     * @param String id ���ڵ��Ψһ��ʶ
     * @version 2008-1-25
     * @return TreeControlNodeService ���ڵ�
     */
    public TreeControlNodeService findNode(String id);
    /**
     * ����nickName�������Ľڵ�
     * @param String nickName ���ڵ��س�
     * @version 2008-1-25
     * @return TreeControlNodeService ���ڵ�
     */
    public TreeControlNodeService findNodeByNickName(String nickName);
    /**
     * �������Ľڵ�
     * @param TreeControlNodeService node ���ڵ�
     * @version 2008-1-25
     * @return
     */
    public void addNode(TreeControlNodeService node) throws IllegalArgumentException;
    /**
     * ɾ�����Ľڵ�
     * @param TreeControlNodeService node ���ڵ�
     * @version 2008-1-25
     * @return
     */
    public void removeNode(TreeControlNodeService node);
    public Map getRegistry();
    /**
     * �������
     * @param
     * @version 2008-1-26
     * @return Object ����ʵ��
     */
	public Object clone() throws CloneNotSupportedException;
}
