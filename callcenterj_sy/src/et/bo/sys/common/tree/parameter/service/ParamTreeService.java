/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.common.tree.parameter.service;

import java.util.List;

import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeService;
import excellence.framework.base.dto.IBaseDTO;
/**
 * ��������tree
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public interface ParamTreeService {
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
	void upateParamTree(IBaseDTO dto);
	/**
	 * ���ڵ��ɾ��
	 * @param id BaseTree��ID
	 * @return
	 * @throws
	 */
	void removeParamTree(String id);
	/**
	 * ����NickName��label Value����ʽ��ʾ�������ӽڵ��б�
	 * @param id BaseTree��ID
	 * @return
	 * @throws
	 */
	List<LabelValueBean> getLabelValueBeanListByNickName(String nickName,TreeService tree);
	/**
	 * ����NickName�Ըýڵ���б�
	 * @param id BaseTree��ID
	 * @return
	 * @throws
	 */
	String getIdByNidkName(String nickName,TreeService tree);
	//void reloadParamTree();
	void setRootId(String rootId);
	
}
