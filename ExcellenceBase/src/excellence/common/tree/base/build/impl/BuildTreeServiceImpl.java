/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.tree.base.build.impl;

import java.util.List;

import excellence.common.tree.base.build.service.BuildTreeService;
import excellence.common.tree.base.impl.BaseTreeNodeServiceImpl;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeInfoService;
import excellence.common.tree.base.service.TreeService;
/**
 * ���������װ
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class BuildTreeServiceImpl implements BuildTreeService {
	/**
	 * ע����TreeService��ʵ����,����springע��ʵ��
	 */
	private TreeService treeService;
	/**
	 * ע����TreeService��ʵ����
	 * @param TreeService treeService(����һ�ô���װ����)
	 * @version 2008-1-24
	 * @return
	 */
	public void setTreeService(TreeService treeService)
	{
		this.treeService = treeService;
	}
	/**
	 * �õ���TreeService��ʵ������BuildTreeService�ڲ�ʹ��
	 * @version 2008-1-24
	 * @return TreeService treeService(����һ�ô���װ����)
	 */
	private TreeService getTreeService()
	{
		return this.treeService;
	}
	/**
	 * ��װ�� ����TreeInfoService ����addTheNode��װ������
	 * @param TreeInfoService tii(�����root treeList)
	 * @version 2008-1-24
	 * @return TreeService ���ص�����
	 */
	public TreeService creator(TreeInfoService tis) {
		
		// TODO Auto-generated method stub
		if(this.getTreeService()==null)
			throw new NullPointerException("TreeServiceΪ�� �����setTreeService(TreeService treeService)ע����TreeService");
		TreeService treeService= this.getTreeService();
		treeService.setRoot(tis.getRoot());
		if(treeService.getRoot()==null)
			throw new NullPointerException("��BuildTreeService�����creator(TreeInfoService tis)��tis���*<�����ڵ��ǿ�>*����");
		addTheNode(treeService.getRoot(),tis.getTreeNodeList());
    	return treeService;
	}
	/**
	 * ���ӽڵ� ͬ��ѭ��list ��treeControlNode�ĺ��ӽڵ����treeControlNode��
	 * @param TreeControlNodeService ���ڵ� 
	 * @param List list �����BaseTreeNodeServiceԪ�ش�������Ļ���������Ϣ
	 * @version 2008-1-24
	 * @return
	 */
	public void addTheNode(TreeControlNodeService treeControlNode, List list) {
		
		// TODO Auto-generated method stub

		//�����ӱ��ڵ������Ӻ��ӽڵ�
		
		String id=treeControlNode.getId();
		for(int i=0;i<list.size();i++){
			//System.out.println(list.get(i).getClass()+"<<<<<<<<<<<<<<<<<<<<<<<");
			BaseTreeNodeService btns=(BaseTreeNodeServiceImpl)list.get(i);
			
			//�����ǲ��ǵ�ǰ�ڵ�ĺ��ӽڵ�
			if(id.equals(btns.getParentId())){//
				try
				{
					
					//System.out.println("id"+id+"<>parentId"+tns.getBaseTreeNodeService().getParentId()+"<>"+tns.getBaseTreeNodeService().getId());
					TreeControlNodeService tcn = treeControlNode.getClass().newInstance();
					//System.out.println(tcn.getClass());
					tcn.setBaseTreeNodeService(btns);
					treeControlNode.addChild(tcn);
					
					//�ݹ����Ӻ��ӽڵ�
					addTheNode(tcn,list);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
