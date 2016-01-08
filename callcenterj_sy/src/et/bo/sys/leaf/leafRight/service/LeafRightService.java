/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.leaf.leafRight.service;
import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tree.base.service.TreeService;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * Ҷ�ӽڵ��ֵ����Ȩ
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ ������
 */
public interface LeafRightService {

	/**
	 * ���ڵ������
	 * @param IBaseDTO dto ��BaseTree �� ViewTreeDict����Ϣ
	 * @return
	 * @throws
	 */
	//void addLeafRight(IBaseDTO dto);
	/**
	 * �õ�Ҷ�ӽڵ�Ȩ���б�
	 * @param nodeId
	 * @return List<SysLeafRight>
	 */
	List<DynaBeanDTO> getLeafRightByNodeId(String nodeId);
	/**
	 * ���ڵ���޸�
	 * @param IBaseDTO dto ��BaseTree �� ViewTreeDict����Ϣ
	 * @return
	 * @throws
	 */
	//void updateLeafRight(IBaseDTO dto);
	/**
	 * ɾ����ǰ��ɫ�����е�Ҷ�ӽڵ�Ȩ��
	 * @param String roleId ��ǰ��ɫ
	 * @param String treeId Ҷ�ӽڵ�
	 * @return
	 * @throws
	 */
	void deleteRoleRights(String roleId,String treeId);
	/**
	 * ���赱ǰ��ɫ�����е�Ҷ�ӽڵ�Ȩ��
	 * @param String roleId ��ǰ��ɫ
	 * @param String[] leafRightId Ȩ������
	 * @return
	 * @throws
	 */
	void grantRoleRights(String roleId,List<String> leafRightIds);
	/**
	 * �����û������е�ģ��Ȩ��
	 * @param String userId ��ǰ�û�id
	 * @return TreeService ģ�鼰Ҷ�ӽڵ�
	 * @throws
	 */
	TreeService loadTree(String userId);
	//void removeParamTree(String id);
	/**
	 * ɾ���������ӽڵ�
	 * @param TreeControlNodeService node ��ɾ���ڵ�
	 * @return
	 * @throws
	 */
	//void deleteLeafRight(String leafRightId);
	/**
	 * ����ɫ��Ȩ
	 * @param roleId
	 * @param TreeService rights
	 * @return
	 */
	void impowerRole(String roleId, TreeService rights);
	/**
	 * ���û�������Ȩ
	 * @param roleId
	 * @param TreeService rights
	 * @return
	 */
	void impowerBatchPerson2Role(String roleId, TreeService rights);
	/**
	 * ��ǰ��ɫ��Ȩ��ͼ�� ����������ʾ
	 * @param roleId
	 * @param TreeService rights
	 * @return
	 */
	void impowerRoleIcon(String roleId, TreeService rights, String tmpIcon);
	//**********************************************************
	/**
	 * ���ڵ������
	 * @param IBaseDTO dto ��BaseTree �� ViewTreeDict����Ϣ
	 * @return
	 * @throws
	 */
	void addDict(IBaseDTO dto,String treeId);
	/**
	 * ���ڵ���޸�
	 * @param IBaseDTO dto ��BaseTree �� ViewTreeDict����Ϣ
	 * @return
	 * @throws
	 */
	boolean updateDict(IBaseDTO dto);
	
	/**
	 * ɾ���������ӽڵ�
	 * @param TreeControlNodeService node ��ɾ���ڵ�
	 * @return
	 * @throws
	 */
	void deleteDict(String id);
	
	/**
	 * ����ID������ϸ��Ϣ
	 * @param 
	 * @return
	 * @throws
	 */
	IBaseDTO treeInfo(String id);
	/**
	 * ����ID���ز�ѯ��Ϣ
	 * @param 
	 * @return
	 * @throws
	 */
	List treeList(IBaseDTO dto, PageInfo pi,String id);
	
	/**
	 * @describe ȡ�ò�ѯ����
	 * @param
	 * @return int
	 */ 
	
	 int getTreeSize();
	 /*
	  * ������
	  */
	 public String getKey();
}
