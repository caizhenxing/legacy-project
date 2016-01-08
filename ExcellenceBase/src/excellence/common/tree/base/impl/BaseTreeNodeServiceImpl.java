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

import java.util.Date;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeNodeExtendedService;
/**
* ��������ڵ��������Ϣ
*
* @version 	jan 01 2008 
* @author ����Ȩ
*/
public class BaseTreeNodeServiceImpl implements BaseTreeNodeService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//	*****************���ڵ�Ļ�������*********************
	/**
	 * 
	 * ���ڵ��id
	 * @version 2008-1-4
	 * @return String
	 * @throws
	 */
	private String id;
	/**
	 * 
	 * ���ڵ������
	 * 
	 * @version 2008-1-4
	 * @return String
	 * @throws
	 */
	private String type;
	/**
	 * 
	 * ���ڵ�ĸ��ڵ�id
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private String parentId;
	
	/**
	 * 
	 * ���ڵ���ʾ������
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	private String label;;

	/**
	 * 
	 * ���ڵ��ɾ����־
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private String deleteMark;
	/**
	 * 
	 * ���ڵ������
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private String layerOrder;
	/**
	 * 
	 * ���ڵ�Ĵ���ʱ��
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private Date createTime;
	/**
	 * 
	 * ���ڵ��Ƿ�Ϊ���ڵ�
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	/**
	 * 
	 * ���ڵ���س�
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private String isRoot;
	private String nickName; 
	/**
	 * 
	 * ���ڵ���޸�ʱ��
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private Date modifyTime;
	/**
	 * 
	 * ���ڵ�ı�ע��Ϣ
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private String remark;
	/**
	 * 
	 * ������չ����
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	private TreeNodeExtendedService tnes;
	//	*****************���Ļ������Ե�get set ����*********************

	/**
	 * 
	 * �õ����ڵ��Id
	 * @param 
	 * @version 2008-1-4
	 * @return String id ���ڵ��Ψһ��ʶ
	 * @throws
	 */
	public String getId()
	{
		return this.id;
	}
	/**
	 * 
	 * ��������Id
	 * @param String��id ���ڵ��Ψһ��ʶ
	 * @version 2008-1-4
	 * @return String
	 * @throws
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * 
	 * �õ����ڵ�ĸ��ڵ�Id
	 * @param 
	 * @version 2008-1-4
	 * @return String ���ĸ��ڵ�Id
	 * @throws
	 */
	public String getParentId()
	{
		return this.parentId;
	}
	/**
	 * 
	 * �������ڵ�ĸ��ڵ�Id
	 * @param String��parentId ���ĸ��ڵ�Id
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * 
	 * �õ����ڵ���ʾ������
	 * @param
	 * @version 2008-1-4
	 * @return String label����ʾ������
	 * @throws
	 */
	public String getLabel()
	{
		return this.label;
	}
	/**
	 * 
	 * �������ڵ���ʾ������
	 * @param��String��lable ����ʾ������
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * 
	 * �õ����ڵ��ɾ����־
	 * @param
	 * @version 2008-1-4
	 * @return String deleteMark �߼�ɾ����־
	 * @throws
	 */
	public String getDeleteMark()
	{
		return this.deleteMark;
	}
	/**
	 * 
	 * �������ڵ��ɾ����־
	 * @param��String deleteMark �߼�ɾ����־ 
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setDeleteMark(String deleteMark)
	{
		this.deleteMark = deleteMark;
	}
	/**
	 * 
	 * �õ����ڵ�Ĵ���ʱ��
	 * @param
	 * @version 2008-1-4
	 * @return Date createTime ���Ĵ���ʱ��
	 * @throws
	 */
	public Date getCreateTime()
	{
		return this.createTime;
	}
	
	/**
	 * 
	 * �������ڵ�Ĵ���ʱ��
	 * @param��Date createTime ���Ĵ���ʱ��
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	/**
	 * 
	 * �õ����ڵ���޸�ʱ��
	 * @param
	 * @version 2008-1-4
	 * @return Date modifyTime �����޸�ʱ��
	 * @throws
	 */
	public Date getModifyTime()
	{
		return this.modifyTime;
	}
	/**
	 * 
	 * ���������޸�ʱ��
	 * @param��Date modifyTime �����޸�ʱ��
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setModifyTime(Date modifyTime)
	{
		this.modifyTime = modifyTime;
	}
	
	/**
     * ���ڵ��Ƿ�Ϊ���ڵ�0��1����
     * @param
     * @version 2008-1-26
     * @return String 0��1����
     * @throws
     */
	public String getIsRoot()
	{
		return this.isRoot;
	}
	/**
     * ���ڵ��Ƿ�Ϊ���ڵ�0��1����
     * @param String 0��1����
     * @version 2008-1-26
     * @return 
     * @throws
     */
	public void setIsRoot(String isRoot)
	{
		this.isRoot = isRoot;
	}
	
	/**
	 * 
	 * �õ����ڵ�ı�ע��Ϣ
	 * @param
	 * @version 2008-1-4
	 * @return String remark ���ڵ�ı�ע��Ϣ
	 * @throws
	 */
	public String getRemark()
	{
		return this.remark;
	}
	/**
	 * 
	 * �������ڵ�ı�ע��Ϣ
	 * @param��String remark  ���ڵ�ı�ע��Ϣ
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	/**
	 * 
	 * �������ڵ���س�
	 * @param��String nickName ���ڵ���س�
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}
	/**
	 * 
	 * �õ����ڵ���س�
	 * @param��
	 * @version 2008-1-4
	 * @return String nickName ���ڵ���س�
	 * @throws
	 */
	public String getNickName()
	{
		return this.nickName;
	}
	
	/**
	 * 
	 * �������ڵ������
	 * @param��String type ���ڵ������
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	/**
	 * 
	 * �õ����ڵ������
	 * @param��
	 * @version 2008-1-4
	 * @return String type ���ڵ������
	 * @throws
	 */
	public String getType()
	{
		return this.type;
	}
	
	/**
	 * 
	 * �������ڵ������
	 * @param��String type ���ڵ������
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setLayerOrder(String layerOrder)
	{
		this.layerOrder = layerOrder;
	}
	/**
	 * 
	 * �õ����ڵ������
	 * @param��
	 * @version 2008-1-4
	 * @return String type ���ڵ������
	 * @throws
	 */
	public String getLayerOrder()
	{
		return this.layerOrder;
	}
	/**
	 * 
	 * �õ����ڵ����չ����
	 * @param��
	 * @version 2008-1-4
	 * @return TreeNodeExtendedService tnes
	 * @throws
	 */
	public TreeNodeExtendedService getTreeNodeExtendedService()
	{
		return this.tnes; 
	}
	/**
	 * 
	 * �������ڵ����չ����
	 * @param��TreeNodeExtendedService tnes
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	public void setTreeNodeExtendedService(TreeNodeExtendedService tnes)
	{
		this.tnes = tnes;
	}
	/**
	 * 
	 * �Ƚ��������ڵ�Ĵ�С
	 * @param Object o �����ڵ�
	 * @version 2008-1-4
	 * @return int -1,0,1 �ֱ��Ӧ С��,����,����
	 * @throws
	 */
	public int compareTo(Object o) {
		
		// TODO Auto-generated method stub
		BaseTreeNodeService btn = (BaseTreeNodeService) o;
		String layerOrder = this.getLayerOrder();
		if(layerOrder == null)
		{
			layerOrder = "";
		}
		String btnOrder = btn.getLayerOrder();
		if(btnOrder == null)
		{
			btnOrder = "";
		}
		return layerOrder.compareTo(btnOrder);

	}
	/**
     * ���ڵ�����
     * @param
     * @version 2008-1-26
     * @return Object �����ڵ��ʵ��
     * @throws CloneNotSupportedException;
     */
	public Object clone() throws CloneNotSupportedException {
		
		// TODO Auto-generated method stub
		
		//�򵥵����Ը���
		BaseTreeNodeServiceImpl bts = new BaseTreeNodeServiceImpl();
		bts.setCreateTime(this.getCreateTime());
		bts.setDeleteMark(this.getDeleteMark());
		bts.setLabel(this.getLabel());
		bts.setModifyTime(this.getModifyTime());
		bts.setId(this.getId());
		bts.setParentId(this.getParentId());
		bts.setRemark(this.getRemark());
		bts.setLayerOrder(this.getLayerOrder());
		bts.setTreeNodeExtendedService((TreeNodeExtendedService)this.getTreeNodeExtendedService().clone());
		return bts;
	}
}
