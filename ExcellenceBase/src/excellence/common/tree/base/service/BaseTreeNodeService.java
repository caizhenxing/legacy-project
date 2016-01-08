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

import java.io.Serializable;
import java.util.Date;
/**
* ���ڵ�Ļ������Զ���
*
* @version 	jan 24 2008 
* @author ����Ȩ
*/
public interface BaseTreeNodeService extends Serializable , Comparable ,Cloneable{
//	*****************���Ļ������Ե�get set ����*********************

	/**
	 * 
	 * �õ����ڵ��Id
	 * @param 
	 * @version 2008-1-4
	 * @return String id ���ڵ��Ψһ��ʶ
	 * @throws
	 */
	String getId();
	/**
	 * 
	 * ��������Id
	 * @param String��id ���ڵ��Ψһ��ʶ
	 * @version 2008-1-4
	 * @return String
	 * @throws
	 */
	void setId(String id);

	/**
	 * 
	 * �õ����ڵ�ĸ��ڵ�Id
	 * @param 
	 * @version 2008-1-4
	 * @return String ���ĸ��ڵ�Id
	 * @throws
	 */
	String getParentId();
	/**
	 * 
	 * �������ڵ�ĸ��ڵ�Id
	 * @param String��parentId ���ĸ��ڵ�Id
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setParentId(String parentId);
	
	/**
	 * 
	 * �õ����ڵ���ʾ������
	 * @param
	 * @version 2008-1-4
	 * @return String label����ʾ������
	 * @throws
	 */
	String getLabel();
	/**
	 * 
	 * �������ڵ���ʾ������
	 * @param��String��lable ����ʾ������
	 * @version 2008-1-4
	 * @return
	 * @throws
	 */
	void setLabel(String lable);

	/**
	 * 
	 * �õ����ڵ��ɾ����־
	 * @param
	 * @version 2008-1-4
	 * @return String deleteMark �߼�ɾ����־
	 * @throws
	 */
	String getDeleteMark();
	/**
	 * 
	 * �������ڵ��ɾ����־
	 * @param��String deleteMark �߼�ɾ����־ 
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setDeleteMark(String deleteMark);
	/**
	 * 
	 * �õ����ڵ�Ĵ���ʱ��
	 * @param
	 * @version 2008-1-4
	 * @return Date createTime ���Ĵ���ʱ��
	 * @throws
	 */
	Date getCreateTime();
	
	/**
	 * 
	 * �������ڵ�Ĵ���ʱ��
	 * @param��Date createTime ���Ĵ���ʱ��
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setCreateTime(Date createTime);
	/**
	 * 
	 * �õ����ڵ���޸�ʱ��
	 * @param
	 * @version 2008-1-4
	 * @return Date modifyTime �����޸�ʱ��
	 * @throws
	 */
	Date getModifyTime();
	/**
	 * 
	 * ���������޸�ʱ��
	 * @param��Date modifyTime �����޸�ʱ��
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setModifyTime(Date modifyTime);
	/**
     * ���ڵ��Ƿ�Ϊ���ڵ�0��1����
     * @param
     * @version 2008-1-26
     * @return String 0��1����
     * @throws
     */
	String getIsRoot();
	/**
     * ���ڵ��Ƿ�Ϊ���ڵ�0��1����
     * @param String 0��1����
     * @version 2008-1-26
     * @return 
     * @throws
     */
	void setIsRoot(String isRoot);
	/**
	 * 
	 * �õ����ڵ�ı�ע��Ϣ
	 * @param
	 * @version 2008-1-4
	 * @return String remark ���ڵ�ı�ע��Ϣ
	 * @throws
	 */
	String getRemark();
	/**
	 * 
	 * �������ڵ�ı�ע��Ϣ
	 * @param��String remark  ���ڵ�ı�ע��Ϣ
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setRemark(String remark);
	/**
	 * 
	 * �������ڵ���س�
	 * @param��String nickName ���ڵ���س�
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setNickName(String nickName);
	/**
	 * 
	 * �õ����ڵ���س�
	 * @param��
	 * @version 2008-1-4
	 * @return String nickName ���ڵ���س�
	 * @throws
	 */
	String getNickName();
	/**
	 * 
	 * �������ڵ������
	 * @param��String type ���ڵ������
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setType(String type);
	/**
	 * 
	 * �õ����ڵ������
	 * @param��
	 * @version 2008-1-4
	 * @return String type ���ڵ������
	 * @throws
	 */
	String getType();
	/**
	 * 
	 * �������ڵ������
	 * @param��String type ���ڵ������
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setLayerOrder(String layerOrder);
	/**
	 * 
	 * �õ����ڵ������
	 * @param��
	 * @version 2008-1-4
	 * @return String type ���ڵ������
	 * @throws
	 */
	String getLayerOrder();
	/**
	 * 
	 * �õ����ڵ����չ����
	 * @param��
	 * @version 2008-1-4
	 * @return TreeNodeExtendedService tnes
	 * @throws
	 */
	TreeNodeExtendedService getTreeNodeExtendedService();
	/**
	 * 
	 * �������ڵ����չ����
	 * @param��TreeNodeExtendedService tnes
	 * @version 2008-1-4
	 * @return 
	 * @throws
	 */
	void setTreeNodeExtendedService(TreeNodeExtendedService tnes);
	/**
     * ���ڵ�����
     * @param
     * @version 2008-1-26
     * @return Object �����ڵ��ʵ��
     * @throws CloneNotSupportedException;
     */
	Object clone() throws CloneNotSupportedException;
}
