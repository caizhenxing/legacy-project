/**
 *  
 * �������� 2009-5-8
 * 
 * @version 2
 * ����汾����struts2�����еģ�dao��keyservice��LabelValueBeanȡ����.
 * 
 */
package base.zyf.common.tree.classtree.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import base.zyf.common.tree.TreeNodeI;
import base.zyf.common.tree.classtree.ClassTreeLoadService;
import base.zyf.common.tree.classtree.ClassTreeService;
import base.zyf.web.view.ComboSupportList;
import base.zyf.web.view.OptionBean;

/**
 * ������ȡ�����Ȳ���
 *
 * @version 2
 * @author zhaoyifei
 */
public class ClassTreeServiceImpl implements ClassTreeService{
	private static TreeNodeI paramTree = null;
	
	private ClassTreeLoadService classTreeLoadService;
	
	private Map<String,TreeNodeI> treeId;
	
	private Map<String,TreeNodeI> treeNick;
	
//	private static String space=" �� ";
//	private static String labelRoot="#��";
//	private static String labelBegin="��";
//	//String middleHead="����";//��
//	private static String middleCenter="��";//��
//	private static String middleTail="��";//��
//	private static String middle="��";
	
	
	
	public ClassTreeLoadService getClassTreeLoadService() {
		return classTreeLoadService;
	}
	public void setClassTreeLoadService(ClassTreeLoadService classTreeLoadService) {
		this.classTreeLoadService = classTreeLoadService;
	}
	public String getIdByNickname(String nickName)
	{
		TreeNodeI tni = this.getNodeByNickName(nickName);
		if(tni != null)
		{
			return tni.getName();
		}else
		{
			return "";
		}
		
	}
	public ComboSupportList getLabelVaList(String nickName)
	{
		//ע�͵����Ǵ����ڵ��
		//�������ڵ�
		return this.getLabelVaList(nickName, false);
	}
	
	/**
	 * ����label value ���� list��
	 * @param String nickName
	 * @param boolean needRoot �Ƿ���Ҫ���ڵ�
	 * @return List<LabelValueBean>
	 * @exception
	 */
	public ComboSupportList getLabelVaList(String nickName,boolean needRoot)
	{
		TreeNodeI tcns = this.getNodeByNickName(nickName);
		if(tcns == null)
			return null;
		List<TreeNodeI> nodes = tcns.getChildren();
		ComboSupportList csl = new ComboSupportList("id","label");
		csl.addAll(nodes);
		return csl;
	}
	
	
	
	
	/**
	 * �ӹ�List<LabelValueBean> list
	 * @param list �����д��lv
	 * @param node �ڵ�
	 * @param needAll �Ƿ�Ҫ�ڹ������ӽڵ�
	 * @param type ivr department and others
	 */
	private void recursion (ComboSupportList list, TreeNodeI node, boolean needAll)
	{
		OptionBean ob = new OptionBean();

		ob.setName(node.getLabel());
		ob.setCode(node.getName());
		List<TreeNodeI> nodes = node.getChildren();
		list.add(ob);
		if(needAll)
		for(int i=0; i<nodes.size(); i++)
		{
			recursion (list,nodes.get(i));
		}
	}
	
	/**
	 * �ӹ�List<LabelValueBean> list
	 * @param list �����д��lv
	 * @param node �ڵ�
	 */
	private void recursion (ComboSupportList list,TreeNodeI node)
	{
		recursion(list,node,false);
	}
	
	/**
	 * ����id�õ����ڵ�
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public TreeNodeI getNodeById(String id)
	{
		if(paramTree == null){
			this.loadParamTree();
		}
		if(this.treeId.containsKey(id))
		{
			return this.treeId.get(id);
		}
		return null;
		//throw new NoSuchElementException("��ClassTreeServiceImpl �� ����getNodeById("+id+") �ĵ������ڵ�Ϊ��error");
	}
	/**
	 * ����id�õ����ڵ�label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public String getLabelById(String id)
	{
		TreeNodeI node = this.getNodeById(id);
		if(node == null){
			return "";
		}
		return node.getLabel();
	}
	
	/**
	 * ����nickName�õ����ڵ�
	 * @param String nickName
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public TreeNodeI getNodeByNickName(String nickName)
	{
		if(paramTree == null)
		{
			this.loadParamTree();
		}
		if(this.treeNick.containsKey(nickName))
		{
			return this.treeNick.get(nickName);
		}
		return null;
		
	}
	/**
	 * ����nickName�õ����ڵ�label
	 * @param id
	 * @return label
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public String getLabelByNickName(String nickName)
	{
		TreeNodeI tni = this.getNodeByNickName(nickName);
		if(tni != null)
		{
			return tni.getLabel();
		}else
		{
			return "";
		}
	}
	@SuppressWarnings("unchecked")
	public void loadParamTree()
	{
		if (paramTree == null) {
			this.classTreeLoadService.loadTreeNodeService(this);
		}
	}
	/**
	 * ���Ӽ���
	 */
	public void reloadParamTree()
	{
		this.paramTree = null;
		loadParamTree();
	}
	
	/**
	 * �����������нڵ�
	 * @return
	 */
	public TreeNodeI getParamTree()
	{
		return this.paramTree;
	}
	/**
	 * @return the treeId
	 */
	public Map<String, TreeNodeI> getTreeId() {
		return treeId;
	}
	/**
	 * @param treeId the treeId to set
	 */
	public void setTreeId(Map<String, TreeNodeI> treeId) {
		this.treeId = treeId;
	}
	/**
	 * @return the treeNick
	 */
	public Map<String, TreeNodeI> getTreeNick() {
		return treeNick;
	}
	/**
	 * @param treeNick the treeNick to set
	 */
	public void setTreeNick(Map<String, TreeNodeI> treeNick) {
		this.treeNick = treeNick;
	}
	/**
	 * @param paramTree the paramTree to set
	 */
	public void setParamTree(TreeNodeI paramTree) {
		ClassTreeServiceImpl.paramTree = paramTree;
	}

	
}
