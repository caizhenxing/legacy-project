/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.layerOrder.service.impl;

import java.util.Iterator;
import java.util.Map;

import et.bo.sys.layerOrder.service.LayerOrderTreeService;
import et.po.BaseTree;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;

/**
 * �����������
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class LayerOrderTreeServiceImpl implements LayerOrderTreeService{
	private ClassTreeService classTreeService = null;
	private BaseDAO dao = null;
	private KeyService ks = null;
	/**
	 * ����nickName����TreeService�õ���Ӧ����
	 * @param nickName ���ڵ�nickName
	 * @param action �ڵ�action
	 * @param target ���ڵ�target
	 * @return TreeService ������
	 * @throws
	 */
	public TreeService buildTree(String nickName,String action, String target)
	{
		return classTreeService.getSubTreeByNickName(nickName, action, target);
	}
	/**
	 * ���ڵ������
	 * @param IBaseDTO dto ��BaseTree �� ViewTreeDict����Ϣ
	 * @return
	 * @throws
	 */
	public void addParamTree(IBaseDTO dto)
	{
		this.reloadParamTree();
	}
	/**
	 * ���ڵ���޸�
	 * @param IBaseDTO dto ��BaseTree �� ViewTreeDict����Ϣ
	 * @return
	 * @throws
	 */
	public void updateParamTree(IBaseDTO dto)
	{
		this.reloadParamTree();
	}
	/**
	 * ���ڵ��ɾ��
	 * @param id BaseTree��ID
	 * @return
	 * @throws
	 */
	public void removeParamTree(String id)
	{
		this.reloadParamTree();
	}
	
	/**
	 * ������layerOrder���Ը��������������
	 * @param ViewTree tree
	 * @return
	 * @throws
	 */
	public void updateParamTreeLayerOrder(ViewTree tree)
	{
		Map registry = tree.getRegistry();
		Iterator keys = registry.keySet().iterator();
		while(keys.hasNext())
		{
			String key = (String)keys.next();
			BaseTreeNodeService btn = ((TreeControlNodeService)registry.get(key)).getBaseTreeNodeService();
			String id = btn.getId();
			String layerOrder = btn.getLayerOrder();
			BaseTree bt = (BaseTree)dao.loadEntity(BaseTree.class, id);
			bt.setLayerOrder(layerOrder);
			dao.saveEntity(bt);
		}
		reloadParamTree();
	}
	
	public ClassTreeService getClassTreeService() {
		return classTreeService;
	}
	public void setClassTreeService(ClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
	}
	
	
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	
	/**
	 * �����ݿ�����ƻ��Բ�����Ҫ�Ӽ�����
	 *
	 */
	private void reloadParamTree()
	{
		classTreeService.reloadParamTree();
	}
	
}
