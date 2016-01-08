/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.department.service;
import java.util.List;

import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;
import excellence.framework.base.dto.IBaseDTO;
/**
 * ��������tree
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public interface DepartmentTreeService {
	/**
	 * ����hql��� ���ڵ�id rootId ����һ��������
	 * @param rootId ���ڵ�id
	 * @return TreeService ������
	 * @throws
	 */
	TreeService buildTree();
	/**
	 * ���ڵ������
	 * @param IBaseDTO dto ��BaseTree �� ViewTreeDict����Ϣ
	 * @return
	 * @throws
	 */
	void addParamTree(IBaseDTO dto);
	/**
	 * ���ڵ���޸�
	 * @param IBaseDTO dto ��BaseTree �� ViewTreeDict����Ϣ
	 * @return
	 * @throws
	 */
	void updateParamTree(IBaseDTO dto);
	/**
	 * ���ڵ��ɾ��
	 * @param id BaseTree��ID
	 * @return
	 * @throws
	 */
	//void removeParamTree(String id);
	/**
	 * ɾ���������ӽڵ�
	 * @param TreeControlNodeService node ��ɾ���ڵ�
	 * @return
	 * @throws
	 */
	void deleteNode(TreeControlNodeService node);
    /**
     * ����id��BaseTree and ViewTreeDtail���ص�IBaseDTO�з��ظ���ͼ����
     * @param id
     * @return
     */
   IBaseDTO loadViewBeanById(String id);
	/**
	 * ����NickName��label Value����ʽ��ʾ�������ӽڵ��б�
	 * @param id BaseTree��ID
	 * @return
	 * @throws
	 */
	List<LabelValueBean> getLabelValueBeanListByNickName(String nickName,TreeService tree);
	/**
	 * ����NickName�Ըýڵ��id
	 * @param String nickName,TreeService tree
	 * @return
	 * @throws
	 */
	String getIdByNidkName(String nickName,TreeService tree);
	//void reloadParamTree();
	/**
	 * ���ø��ڵ�id
	 * @param String rootId
	 * @return
	 * @throws
	 */
	void setRootId(String rootId);
	
	
	/**
	 * ����hql��� ���ڵ�id rootId ����һ��������
	 * @param 
	 * @return TreeService ������
	 * @throws
	 */
	TreeService buildTree(String nodeAction,String target);


	/**
	 * ���ڵ��ɾ��
	 * @param id BaseTree��ID
	 * @return
	 * @throws
	 */
	void removeParamTree(String id);
 



	
	/**
	 * �õ��û��б� ͬһ����
	 * @param
	 * @version 2008-02-15
	 * @return
	 */
	public List<LabelValueBean> getUserListByDep(String id);
	
	
	
	/**
	 * �õ��û��б�
	 * @param
	 * @version 2008-02-15
	 * @return
	 */
	public List<LabelValueBean> getUserListselect(IBaseDTO dto);
}
