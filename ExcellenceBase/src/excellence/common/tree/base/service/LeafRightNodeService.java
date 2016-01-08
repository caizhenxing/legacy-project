/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.base.service;
/**
 * ���Ǹ������Ľӿ�
 * �������ݿ��Ҷ�ӽڵ�Ȩ����Ϣ�ķ�װ ����po
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public interface LeafRightNodeService {
	String getId();
	void setId(String id);
	
	/**
	 * ����Ҷ�ӽڵ�id
	 * @return
	 */
	String getTreeId();
	
	/**
	 * ����Ҷ�ӽڵ�id
	 * @return String treeId
	 */
	void setTreeId(String treeId);
	
	/**
	 * �ڵ����� leaf_right_btn  leaf_right����ڵ�Ȩ�� btn ����ťȨ��
	 * @return
	 */
	String getType();
	void setType(String type);
	
	/**
	 * �ڵ�ͼ��ļ�����expanded=folderOpen.gif;closed=folderClose.gif;leaf=leaf.gif;
	 * @return
	 */
	String getIcon();
	void setIcon(String icons);
	
	/**
	 * �ڵ���ʾ��������ʾ���ǽڵ�Ĺ��� ���Ӱ�ť ��ѯ��ť 
	 * @return
	 */
	String getLabel();
	void setLabel(String label);
	
	/**
	 * �ڵ�Ψһ��ʶ ��ʽ btn_dept_leaf_add ����֪�� ����Ҷ�ӽڵ�����Ӱ�ť
	 * @return
	 */
	String getNickName();
	void setNickName(String nickName);
	
	/**
	 * һЩ��ע��Ϣ
	 * @return
	 */
	String getRemark();
	void setRemart(String remark);
}
