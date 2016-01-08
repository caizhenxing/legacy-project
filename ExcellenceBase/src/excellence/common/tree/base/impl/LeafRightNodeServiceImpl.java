/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.base.impl;

import excellence.common.tree.base.service.LeafRightNodeService;
/**
 * ���Ǹ������Ľӿ�
 * �������ݿ��Ҷ�ӽڵ�Ȩ����Ϣ�ķ�װ ����po
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class LeafRightNodeServiceImpl implements LeafRightNodeService {

	private String id;
	private String treeId;
	private String type;
	private String icon;
	private String label;
	private String nickName;
	private String remark;
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * ����Ҷ�ӽڵ�id
	 * @return
	 */
	public String getTreeId()
	{
		return this.treeId;
	}
	
	/**
	 * ����Ҷ�ӽڵ�id
	 * @return String treeId
	 */
	public void setTreeId(String treeId)
	{
		this.treeId = treeId;
	}
	
	/**
	 * �ڵ����� leaf_right_btn  leaf_right����ڵ�Ȩ�� btn ����ťȨ��
	 * @return
	 */
	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
	/**
	 * �ڵ�ͼ��ļ�����expanded=folderOpen.gif;closed=folderClose.gif;leaf=leaf.gif;
	 * @return
	 */
	public String getIcon()
	{
		return this.icon;
	}
	public void setIcon(String icons)
	{
		this.icon = icons;
	}
	
	/**
	 * �ڵ���ʾ��������ʾ���ǽڵ�Ĺ��� ���Ӱ�ť ��ѯ��ť 
	 * @return
	 */
	public String getLabel()
	{
		return this.label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	/**
	 * �ڵ�Ψһ��ʶ ��ʽ btn_dept_leaf_add ����֪�� ����Ҷ�ӽڵ�����Ӱ�ť
	 * @return
	 */
	public String getNickName()
	{
		return this.nickName;
	}
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}
	
	/**
	 * һЩ��ע��Ϣ
	 * @return
	 */
	public String getRemark()
	{
		return this.remark;
	}
	public void setRemart(String remark)
	{
		this.remark = remark;
	}
}
