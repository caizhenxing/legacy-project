/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.layerOrder.service;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.framework.base.dto.IBaseDTO;
/**
 * �����������
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public interface LayerOrderTreeService {
	/**
	 * ����nickName����TreeService�õ���Ӧ����
	 * @param nickName ���ڵ�nickName
	 * @param action �ڵ�action
	 * @param target ���ڵ�target
	 * @return TreeService ������
	 * @throws
	 */
	TreeService buildTree(String nickName,String action, String target);
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
	 * ������layerOrder���Ը��������������
	 * @param ViewTree tree
	 * @return
	 * @throws
	 */
	void updateParamTreeLayerOrder(ViewTree tree);
	/**
	 * ���ڵ��ɾ��
	 * @param id BaseTree��ID
	 * @return
	 * @throws
	 */
	void removeParamTree(String id);
}
