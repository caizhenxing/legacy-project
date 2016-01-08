/**
 * className ClassTreeServiceImpl 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package excellence.common.classtree.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import excellence.common.classtree.ClassTreeLoadService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.build.impl.BuildTreeServiceImpl;
import excellence.common.tree.base.build.service.BuildTreeService;
import excellence.common.tree.base.impl.TreeInfoServiceImpl;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeInfoService;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTree;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.common.tree.ext.view.impl.ViewTreeNode;
import excellence.common.tree.ext.view.impower.ViewTreeControlImpowerNode;
import excellence.framework.base.dao.BaseDAO;
/**
 * ������ȡ�����Ȳ���
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class ClassTreeServiceImpl implements ClassTreeService{
	private static TreeService paramTree = null;
	//���������ڵ����� ������
	private static final String paramTreeRootNickName = "paramTreeRoot";
	private ClassTreeLoadService classTreeLoadService;
	
	private static String space=" �� ";
	private static String labelRoot="#��";
	private static String labelBegin="��";
	//String middleHead="����";//��
	private static String middleCenter="��";//��
	private static String middleTail="��";//��
	private static String middle="��";
	
	private BaseDAO dao=null;
	private KeyService ks = null;
	
	
	public KeyService getKs() {
		return ks;
	}
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	public ClassTreeLoadService getClassTreeLoadService() {
		return classTreeLoadService;
	}
	public void setClassTreeLoadService(ClassTreeLoadService classTreeLoadService) {
		this.classTreeLoadService = classTreeLoadService;
	}
	public String getIdByNickname(String nickName)
	{
		if(paramTree == null)
		{
			this.loadParamTree();
		}
		Iterator it = paramTree.getRegistry().keySet().iterator();
		while(it.hasNext())
		{
			TreeControlNodeService node = (TreeControlNodeService)paramTree.getRegistry().get((String)it.next());
			if(nickName.equals(node.getBaseTreeNodeService().getNickName()))
				return node.getId();
		}
		return null;
		//throw new NoSuchElementException("��ClassTreeServiceImpl �� ����getIdByNickname("+nickName+") δ�ҵ���Ӧ�����ڵ�");
	}
	public List<LabelValueBean> getLabelVaList(String nickName)
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
	public List<LabelValueBean> getLabelVaList_0(String nickName,boolean needRoot)
	{
		TreeControlNodeService tcns = this.getNodeByNickName(nickName);
		//System.out.println(tcns.getLabel()+tcns.getType());
		String type = tcns.getType()!=null?tcns.getType().trim():null;
		if(tcns==null)
		{
			throw new NullPointerException("�ڲ�����ClassTreeServiceImpl�� ����getLabelVaList(String nickName) ʱ nickName�õ��ġ��ڵ��Ϊnull����");
		}
		if(tcns.getType()==null)
		{
			throw new NullPointerException("�ڲ�����ClassTreeServiceImpl�� ����getLabelVaList(String nickName) ʱ nickName�õ��Ľڵ��typeΪnull����");
		}
		List<TreeControlNodeService> nodes = tcns.getChildren();
		String rootId = tcns.getId();

		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		this.recursion(list, tcns, rootId,type,needRoot);
		return list;
	}
	/**
	 * ����label value ���� list��
	 * @param String nickName
	 * @param boolean needRoot �Ƿ���Ҫ���ڵ�
	 * @return List<LabelValueBean>
	 * @exception
	 */
	public List<LabelValueBean> getLabelVaList(String nickName,boolean needRoot)
	{
		TreeControlNodeService tcns = this.getNodeByNickName(nickName);
		//System.out.println(tcns.getLabel()+tcns.getType());
		String type = tcns.getType()!=null?tcns.getType().trim():null;
		if(tcns==null)
		{
			return null;
			//throw new NullPointerException("�ڲ�����ClassTreeServiceImpl�� ����getLabelVaList(String nickName) ʱ nickName�õ��ġ��ڵ��Ϊnull����");
		}
		if(tcns.getType()==null)
		{
			//throw new NullPointerException("�ڲ�����ClassTreeServiceImpl�� ����getLabelVaList(String nickName) ʱ nickName�õ��Ľڵ��typeΪnull����");
		}
		List<TreeControlNodeService> nodes = tcns.getChildren();
		String rootId = tcns.getId();

		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		this.recursion(list, tcns, rootId,type,needRoot);
		return list;
	}
	/**
	 * ����label value ���� list��
	 * @param String nickName
	 * @param String changeRootId �޸ĸ��ڵ�Id
	 * @param String changeRootLabel �޸ĸ��ڵ�Label
	 * @return List<LabelValueBean>
	 * @exception
	 */
	public List<LabelValueBean> getLabelVaList_0(String nickName,String changeRootId,String changeRootLabel)
	{
		TreeControlNodeService tcns = this.getNodeByNickName(nickName);
		//System.out.println(tcns.getLabel()+tcns.getType());
		String type = tcns.getType()!=null?tcns.getType().trim():null;
		if(tcns==null)
		{
			throw new NullPointerException("�ڲ�����ClassTreeServiceImpl�� ����getLabelVaList(String nickName) ʱ nickName�õ��ġ��ڵ��Ϊnull����");
		}
		if(tcns.getType()==null)
		{
			throw new NullPointerException("�ڲ�����ClassTreeServiceImpl�� ����getLabelVaList(String nickName) ʱ nickName�õ��Ľڵ��typeΪnull����");
		}
		List<TreeControlNodeService> nodes = tcns.getChildren();
		String rootId = changeRootId;
		tcns.setId(changeRootId);
		tcns.setLabel(changeRootLabel);
//		if(tcns == null)
//			System.out.println("tcns is null error");
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		this.recursion(list, tcns, rootId,type);
		return list;
	}
	
	/**
	 * ����label value ���� list��
	 * @param String nickName
	 * @param String changeRootId �޸ĸ��ڵ�Id
	 * @param String changeRootLabel �޸ĸ��ڵ�Label
	 * @return List<LabelValueBean>
	 * @exception
	 */
	public List<LabelValueBean> getLabelVaList(String nickName,String changeRootId,String changeRootLabel)
	{
		TreeControlNodeService tcns = this.getNodeByNickName(nickName);
		//System.out.println(tcns.getLabel()+tcns.getType());
		String type = tcns.getType()!=null?tcns.getType().trim():null;
		if(tcns==null)
		{
			//throw new NullPointerException("�ڲ�����ClassTreeServiceImpl�� ����getLabelVaList(String nickName) ʱ nickName�õ��ġ��ڵ��Ϊnull����");
			return null;
		}
		if(tcns.getType()==null)
		{
			//throw new NullPointerException("�ڲ�����ClassTreeServiceImpl�� ����getLabelVaList(String nickName) ʱ nickName�õ��Ľڵ��typeΪnull����");
		}
		List<TreeControlNodeService> nodes = tcns.getChildren();
		String rootId = changeRootId;
		tcns.setId(changeRootId);
		tcns.setLabel(changeRootLabel);
//		if(tcns == null)
//			System.out.println("tcns is null error");
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		this.recursion(list, tcns, rootId,type);
		return list;
	}
	/**
	 * �ӹ�List<LabelValueBean> list
	 * @param list �����д��lv
	 * @param node �ڵ�
	 * @param rootId ���ڵ�
	 * @param needRoot ���ɵ�list�Ƿ�Ҫ���ڵ�
	 * @param type ivr department and others
	 */
	private void recursion_0 (List<LabelValueBean> list,TreeControlNodeService node,String rootId,String type,boolean needRoot)
	{
		ViewTreeControlNode vnode = (ViewTreeControlNode)node;
		if(type.equals(vnode.getType()))
		{
			LabelValueBean lv = new LabelValueBean();
			int nodeWidth = vnode.getWidth(rootId);
			if(needRoot==false)
			{
				nodeWidth = nodeWidth - 1;
			}
			lv.setLabel(this.getWidthImg(nodeWidth)+vnode.getLabel());
			//System.out.println("width is :"+vnode.getWidth(rootId)+vnode.getLabel()+":"+node.getParent().getLabel());
			lv.setValue(vnode.getId());
			List<TreeControlNodeService> nodes = vnode.getChildren();
		  if((needRoot==false)&&rootId.equals(node.getId()))
		  {
				//����Ҫ���ڵ� �ҽڵ�id==rootIdʱʲôҲ����
			}
			else
			{
				list.add(lv);
			}
			for(int i=0; i<nodes.size(); i++)
			{
				recursion (list,nodes.get(i),rootId,type,needRoot);
			}
		}
	}
	/**
	 * �ӹ�List<LabelValueBean> list
	 * @param list �����д��lv
	 * @param node �ڵ�
	 * @param rootId ���ڵ�
	 * @param needRoot ���ɵ�list�Ƿ�Ҫ���ڵ�
	 * @param type ivr department and others
	 */
	private void recursion (List<LabelValueBean> list,TreeControlNodeService node,String rootId,String type,boolean needRoot)
	{
		ViewTreeControlNode vnode = (ViewTreeControlNode)node;
		LabelValueBean lv = new LabelValueBean();
		int nodeWidth = vnode.getWidth(rootId);
		if(needRoot==false)
		{
			nodeWidth = nodeWidth - 1;
		}
		lv.setLabel(this.getWidthImg(nodeWidth)+vnode.getLabel());
		//System.out.println("width is :"+vnode.getWidth(rootId)+vnode.getLabel()+":"+node.getParent().getLabel());
		lv.setValue(vnode.getId());
		List<TreeControlNodeService> nodes = vnode.getChildren();
	  if((needRoot==false)&&rootId.equals(node.getId()))
	  {
			//����Ҫ���ڵ� �ҽڵ�id==rootIdʱʲôҲ����
		}
		else
		{
			list.add(lv);
		}
		for(int i=0; i<nodes.size(); i++)
		{
			recursion (list,nodes.get(i),rootId,type,needRoot);
		}
	}
	/**
	 * �ӹ�List<LabelValueBean> list
	 * @param list �����д��lv
	 * @param node �ڵ�
	 * @param rootId ���ڵ�
	 * @param type ivr department and others
	 */
	private void recursion_0 (List<LabelValueBean> list,TreeControlNodeService node,String rootId,String type)
	{
		ViewTreeControlNode vnode = (ViewTreeControlNode)node;
		if(type.equals(vnode.getType()))
		{
			LabelValueBean lv = new LabelValueBean();
	
			lv.setLabel(this.getWidthImg(vnode.getWidth(rootId))+vnode.getLabel());
			//System.out.println("width is :"+vnode.getWidth(rootId)+vnode.getLabel()+":"+node.getParent().getLabel());
			lv.setValue(vnode.getId());
			List<TreeControlNodeService> nodes = vnode.getChildren();
			list.add(lv);
			for(int i=0; i<nodes.size(); i++)
			{
				recursion (list,nodes.get(i),rootId,type);
			}
		}
	}
	/**
	 * �ӹ�List<LabelValueBean> list
	 * @param list �����д��lv
	 * @param node �ڵ�
	 * @param rootId ���ڵ�
	 * @param type ivr department and others
	 */
	private void recursion (List<LabelValueBean> list,TreeControlNodeService node,String rootId,String type)
	{
		ViewTreeControlNode vnode = (ViewTreeControlNode)node;
		LabelValueBean lv = new LabelValueBean();

		lv.setLabel(this.getWidthImg(vnode.getWidth(rootId))+vnode.getLabel());
		//System.out.println("width is :"+vnode.getWidth(rootId)+vnode.getLabel()+":"+node.getParent().getLabel());
		lv.setValue(vnode.getId());
		List<TreeControlNodeService> nodes = vnode.getChildren();
		list.add(lv);
		for(int i=0; i<nodes.size(); i++)
		{
			recursion (list,nodes.get(i),rootId,type);
		}
	}
	private String getWidthImg(int width)
	{
		if(width==0)
		{
			return middleCenter;
		}
		else
		{	
			String img = "";
			for(int i=0; i<width; i++)
			{
				img = middle+img;
			}
			return img+middleCenter;
		}
	}
	/**
	 * ����id�õ����ڵ�
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public TreeControlNodeService getNodeById(String id)
	{
		if(paramTree == null){
			this.loadParamTree();
		}
		
		Iterator it = paramTree.getRegistry().keySet().iterator();
		while(it.hasNext()){
			
			if(id.equals((String)it.next())){
				return (TreeControlNodeService)paramTree.getRegistry().get(id);
			}
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
		TreeControlNodeService node = this.getNodeById(id);
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
	public TreeControlNodeService getNodeByNickName_0(String nickName)
	{
		Map registry = paramTree.getRegistry();
		Iterator it = registry.keySet().iterator();
		while(it.hasNext())
		{
			TreeControlNodeService tcns = (TreeControlNodeService)registry.get(it.next());
			//System.out.println(nickName+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			if(nickName.equals(tcns.getBaseTreeNodeService().getNickName()))
			{
				return tcns;
			}
		}
		throw new NoSuchElementException("��ClassTreeServiceImpl �� ����getNodeByNickName("+nickName+") �ĵ������ڵ�Ϊ��error");
	}
	/**
	 * ����nickName�õ����ڵ�
	 * @param String nickName
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public TreeControlNodeService getNodeByNickName(String nickName)
	{
		Map registry = paramTree.getRegistry();
		Iterator it = registry.keySet().iterator();
		while(it.hasNext())
		{
			TreeControlNodeService tcns = (TreeControlNodeService)registry.get(it.next());
			//System.out.println(nickName+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			if(nickName.equals(tcns.getBaseTreeNodeService().getNickName()))
			{
				return tcns;
			}
		}
		return null;
		//throw new NoSuchElementException("��ClassTreeServiceImpl �� ����getNodeByNickName("+nickName+") �ĵ������ڵ�Ϊ��error");
	}
	/**
	 * ����nickName�õ����ڵ�label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public String getLabelByNickName(String nickName)
	{
		TreeControlNodeService node = this.getNodeByNickName(nickName);
		return node.getLabel();
	}
	@SuppressWarnings("unchecked")
	public void loadParamTree()
	{
		if(paramTree == null)
		{
			System.out.println("��¼ʱ���ز���tree");
			TreeInfoService tis = new TreeInfoServiceImpl();
			BuildTreeService bts = new BuildTreeServiceImpl();
	
			//String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
			List<BaseTreeNodeService> btnList =classTreeLoadService.loadTreeNodeService();
			for(int i=0; i<btnList.size(); i++)
			{
				//System.out.println("**********root************root************0"+btn.getIsRoot());
				BaseTreeNodeService btn = btnList.get(i);
				if(paramTreeRootNickName.equals(btn.getNickName()))
				{
					ViewTreeControlNode vtcn = new ViewTreeControlNode();
					vtcn.setBaseTreeNodeService(btn);
					vtcn.getBaseTreeNodeService().setIsRoot("1");
					tis.setRoot(vtcn);
				}
				
			}
			Collections.sort(btnList);
			tis.setTreeNodeList(btnList);
			//System.out.println(this.getTis().getRoot()+"aaaatis root");
			bts.setTreeService(new ViewTree());
			TreeService ts = bts.creator(tis);
			paramTree = ts;
		}
	}
	/**
	 * ���Ӽ���
	 */
	public void reloadParamTree()
	{
		if(paramTree != null)
		{
			paramTree.removeNode(paramTree.getRoot());
			paramTree = null;
		}
		loadParamTree();
	}
	/**
	 * 
	 * ��Ҷ�ӽڵ�ӵ����﹩Ҷ�ӽڵ���Ȩʹ��
	 * ʹ��˽�з���getAllLeafRights getBaseTreeNodeServiceFromTree
	 * @param TreeService tree
	 * @param String action Ҷ�ӽڵ�Ȩ��action
	 * @param String target Ҷ�ӽڵ�Ȩ��target
	 * @return TreeService
	 */
	public TreeService addLeafs2Tree(TreeService tree,String action,String target)
	{
		//�������е�Ҷ�ӽڵ�Ȩ��
		List nodeBtList = this.getBaseTreeNodeServiceFromTree(tree,action, target);
		List leafRightList = this.getAllLeafRights(action, target);
		for(int i=0; i<leafRightList.size(); i++)
		{
			nodeBtList.add(leafRightList.get(i));
		}

		TreeInfoService tis = new TreeInfoServiceImpl();
		BuildTreeService bts = new BuildTreeServiceImpl();
		//String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
		List<BaseTreeNodeService> vtnList = nodeBtList;
		
		tis.setTreeNodeList(vtnList);
		try {
			BaseTreeNodeService rootBt = (BaseTreeNodeService)tree.getRoot().getBaseTreeNodeService().clone();
			ViewTreeControlImpowerNode root = new ViewTreeControlImpowerNode();
			root.setBaseTreeNodeService(rootBt);
			tis.setRoot(root);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(this.getTis().getRoot()+"aaaatis root");
		tree.removeNode(tree.getRoot());
		System.out.println(tis.getRoot().getId()+"tis ��root ");
		bts.setTreeService(new ViewTree());
		TreeService ts = bts.creator(tis);
		return ts;
	}
	/**
	 * 
	 * ��������Ա�ڵ�ӵ�ģ�����﹩����������Ȩʹ��
	 * @param TreeService tree
	 */
	public TreeService addPersons2Tree(TreeService tree,String action,String target)
	{
		//�������е�Ҷ�ӽڵ�Ȩ��
		List nodeBtList = this.getBaseTreeNodeServiceFromTree(tree,action, target);
		List users = this.getAllUsersForDeptTree(action, target);
		for(int i=0; i<users.size(); i++)
		{
			nodeBtList.add(users.get(i));
		}

		TreeInfoService tis = new TreeInfoServiceImpl();
		BuildTreeService bts = new BuildTreeServiceImpl();
		//String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
		List<BaseTreeNodeService> vtnList = nodeBtList;
		
		tis.setTreeNodeList(vtnList);
		try {
			BaseTreeNodeService rootBt = (BaseTreeNodeService)tree.getRoot().getBaseTreeNodeService().clone();
			ViewTreeControlImpowerNode root = new ViewTreeControlImpowerNode();
			root.setBaseTreeNodeService(rootBt);
			tis.setRoot(root);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(this.getTis().getRoot()+"aaaatis root");
		tree.removeNode(tree.getRoot());
		//System.out.println(tis.getRoot().getId()+"tis ��root ");
		bts.setTreeService(new ViewTree());
		TreeService ts = bts.creator(tis);
		return ts;
	}
	
	//����SysLeafRight ����ת����BaseTreeNodeService
	private List<BaseTreeNodeService> getAllLeafRights(String action, String target)
	{
		return this.classTreeLoadService.loadLeafRights(action, target);
	}
	//����SysUser ����ת����BaseTreeNodeService ��Ϊ��������Ҷ�ӽڵ�
	/**
	 * ���û���Ϣ��װ��BaseTreeNodeService����������ʱ����ΪҶ�ӽڵ㸽�ӵ���������
	 * @param String action �ڵ���Ϊ
	 * @param String target �ڵ�Ŀ��
	 * @return List<BaseTreeNodeService> 
	 */
	private List<BaseTreeNodeService> getAllUsersForDeptTree(String action, String target)
	{
		return classTreeLoadService.getAllUsersForDeptTree(action, target);
	}
	//������õ����е�BaseTreeNodeService��clone
	private List<BaseTreeNodeService> getBaseTreeNodeServiceFromTree(TreeService tree,String action, String target)
	{
		List<BaseTreeNodeService> btList = new ArrayList<BaseTreeNodeService>();
		Map<String,TreeControlNodeService> registry = tree.getRegistry();
		Iterator it = registry.keySet().iterator();
		while(it.hasNext())
		{
			TreeControlNodeService node = registry.get((String)it.next());
			try {
				//System.out.println(action);
				((ViewTreeNode)node.getBaseTreeNodeService().getTreeNodeExtendedService()).setAction(action);
				((ViewTreeNode)node.getBaseTreeNodeService().getTreeNodeExtendedService()).setTarget(target);
				btList.add((BaseTreeNodeService)node.getBaseTreeNodeService().clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return btList;
	}
	/**
	 * ����nicknameȡ����
	 * @return TreeService tree
	 */
	public TreeService getSubTreeByNickName(String nickName)
	{
		if(paramTree == null)
			loadParamTree();
		ViewTreeControlNode node = (ViewTreeControlNode)this.getNodeByNickName(nickName);
		String type = node.getType();
		System.out.println("ȡ����tree");
		TreeInfoService tis = new TreeInfoServiceImpl();
		BuildTreeService bts = new BuildTreeServiceImpl();
		//String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
		List<BaseTreeNodeService> vtnList = new ArrayList<BaseTreeNodeService>();
		
		try {
			this.getBaseTreeNodeServiceList(vtnList, node,type);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tis.setTreeNodeList(vtnList);
		try {
			ViewTreeControlNode root = new ViewTreeControlNode();
			BaseTreeNodeService btnRoot = (BaseTreeNodeService)node.getBaseTreeNodeService().clone();
			root.setBaseTreeNodeService(btnRoot);
			tis.setRoot(root);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(this.getTis().getRoot()+"aaaatis root");
		bts.setTreeService(new ViewTree());
		TreeService ts = bts.creator(tis);
		return ts;
	}
	
	/**
     * ȡ���� 
     * @param nickName ���ڵ�
     * @param action �¶���
     * @param target ��Ŀ��
     * @return
     */
	public TreeService getSubTreeByNickName(String nickName,String action, String target)
	{
		if(paramTree == null)
			loadParamTree();
		//����nickNameȡ���ڵ㽫���ڵ��¡һ�ݱ����������
		ViewTreeControlNode node = (ViewTreeControlNode)this.getNodeByNickName(nickName);
		String type = node.getType();
		System.out.println("ȡ����tree");
		TreeInfoService tis = new TreeInfoServiceImpl();
		BuildTreeService bts = new BuildTreeServiceImpl();
		//String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
		List<BaseTreeNodeService> vtnList = new ArrayList<BaseTreeNodeService>();
		
		try {
			this.getBaseTreeNodeServiceList(vtnList, node,type);
			//����action target
			for(int i=0; i<vtnList.size(); i++)
			{
				ViewTreeNode viewExte = (ViewTreeNode)vtnList.get(i).getTreeNodeExtendedService();
				viewExte.setAction(action);
				viewExte.setTarget(target);
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tis.setTreeNodeList(vtnList);
		try {
			ViewTreeControlNode root = new ViewTreeControlNode();
			BaseTreeNodeService btnRoot = (BaseTreeNodeService)node.getBaseTreeNodeService().clone();
			root.setBaseTreeNodeService(btnRoot);
			tis.setRoot(root);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(this.getTis().getRoot()+"aaaatis root");
		bts.setTreeService(new ViewTree());
		TreeService ts = bts.creator(tis);
		return ts;
	}
	/**
	 * ��node����list�� ע�������нڵ����Ͳ���
	 * @param nodeList
	 * @param node
	 * @throws CloneNotSupportedException
	 */
	private void getBaseTreeNodeServiceList(List<BaseTreeNodeService> nodeList,TreeControlNodeService node,String type) throws CloneNotSupportedException
	{
		nodeList.add((BaseTreeNodeService)node.getBaseTreeNodeService().clone());
		//System.out.println(node.getLabel()+"+"+node.getClass()+":"+node.getBaseTreeNodeService().getClass()+":"+node.getBaseTreeNodeService().getTreeNodeExtendedService());
		List<TreeControlNodeService> children = node.getChildren();
		for(int i=0; i<children.size(); i++)
		{
			if(type.equals(children.get(i).getType()))
				getBaseTreeNodeServiceList(nodeList,children.get(i),type);
		}
	}
	
	
	/**
	 * �������ڵ��action��Ϊ
	 * @param action ��Ϊ
	 *  @param TreeService tree
	 */
	public void setNodeActionFromTree(String action,TreeService tree)
	{
		Map registry = tree.getRegistry();
		Iterator keys = registry.keySet().iterator();
		while(keys.hasNext())
		{
			ViewTreeControlNode node = (ViewTreeControlNode)registry.get((String)keys.next());
			node.setAction(action);
		}
	}
	/**
	 * ����id�õ����ڵ�nidkName
	 * @param String nickName
	 * @return String id
	 * @exception NoSuchElementException �ڵ�û�ҵ�
	 */
	public String getNickNameById(String id)
	{
		Map registry = paramTree.getRegistry();
		TreeControlNodeService node = (TreeControlNodeService)registry.get(id);
		return node.getBaseTreeNodeService().getNickName();

	}
	
	/**
	 * �����������нڵ�
	 * @return
	 */
	public TreeService getParamTree()
	{
		return this.paramTree;
	}
}
