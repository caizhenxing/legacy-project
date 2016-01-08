/**
 * className IvrTreeServiceImpl 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.ivr.ivrClassTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import et.bo.sys.basetree.service.impl.IVRBean;
import et.bo.sys.ivr.service.IvrClassTreeLoadService;
import et.bo.sys.ivr.service.IvrClassTreeService;
import excellence.common.classtree.impl.ClassTreeServiceImpl;
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

/**
 * Ivr树的实现
 * 对ClassTreeServiceImpl做部分从写使其能够应用于ivr管理 IvrClassTreeService是个接口 GetCcIvrTreeInfoById方法 
 * 
 * @version 	april 01 2008 
 * @author 王文权
 */
public class IvrClassTreeServiceImpl implements IvrClassTreeService {
	private static TreeService paramIvrTree;
	private static TreeService paramTree;
	private static String space=" │ ";
	private static String labelRoot="#┬";
	private static String labelBegin="┬";
	//String middleHead="│┌";//┏
	private static String middleCenter="├";//┣
	private static String middleTail="└";//┗
	private static String middle="│";
	private static Map<String,IVRBean> ivrBeanMap = new HashMap<String,IVRBean>();
	private static final String ivrParamTreeRootNickName = "ivrNodeRoot";
	private static final String paramTreeRootNickName = "paramTreeRoot";
	//private Map<String,CcIvrTreeInfo> CcIvrTreeInfoMap = new HashMap<String,CcIvrTreeInfo>();
	//注入 IvrClassTreeLoadServiceImpl
	private IvrClassTreeLoadService classTreeLoadService;
	
	


	

	@SuppressWarnings("unchecked")
	public synchronized void loadParamTree()
	{
		List<BaseTreeNodeService> btnList = null;
		if(paramIvrTree == null||ivrBeanMap == null)
		{
			//System.out.println("登录时加载IVR参数tree");
			TreeInfoService tis = new TreeInfoServiceImpl();
			BuildTreeService bts = new BuildTreeServiceImpl();
	
			//String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
			btnList =classTreeLoadService.loadTreeNodeService();
			//这个循环是为了得到树的根节点
			for(int i=0; i<btnList.size(); i++)
			{
				//System.out.println("**********root************root************0"+btn.getIsRoot());
				BaseTreeNodeService btn = btnList.get(i);
				if(ivrParamTreeRootNickName.equals(btn.getNickName()))
				{
					ViewTreeControlNode vtcn = new ViewTreeControlNode();
					vtcn.setBaseTreeNodeService(btn);
					vtcn.getBaseTreeNodeService().setIsRoot("1");
					tis.setRoot(vtcn);
				}
				ViewTreeNode vNode = (ViewTreeNode)btn.getTreeNodeExtendedService();
			    vNode.setAction("./paramTree.do?method=toParamDtl");
			}
			Collections.sort(btnList);

			tis.setTreeNodeList(btnList);
			//System.out.println(this.getTis().getRoot()+"aaaatis root");
			bts.setTreeService(new ViewTree());
			TreeService ts = bts.creator(tis);
			paramIvrTree = ts;
			//for(int i=0; i<10; i++)
			//System.out.println(ts.getRegistry().keySet().size()+"****************************");
			paramTree = paramIvrTree;
			ivrBeanMap = classTreeLoadService.getIVRBeanMap();
		}
		/*
		if(paramTree == null && btnList != null)
		{
			loadParamTree(btnList);
		}
		*/
	}
	private void loadPParamTree(List<BaseTreeNodeService> btnList)
	{
		if(paramTree == null)
		{
			//System.out.println("登录时加载IVR参数tree");
			TreeInfoService tis = new TreeInfoServiceImpl();
			BuildTreeService bts = new BuildTreeServiceImpl();
	
			//这个循环是为了得到树的根节点
			for(int i=0; i<btnList.size(); i++)
			{
				//System.out.println("**********root************root************0"+btn.getIsRoot());
				BaseTreeNodeService btn = btnList.get(i);
				//System.out.println(btn.getLabel()+"::"+btn.getNickName());
				if(ivrParamTreeRootNickName.equals(btn.getNickName()))
				{
					ViewTreeControlNode vtcn = new ViewTreeControlNode();
					vtcn.setBaseTreeNodeService(btn);
					vtcn.getBaseTreeNodeService().setIsRoot("1");
					tis.setRoot(vtcn);
				}
				ViewTreeNode vNode = (ViewTreeNode)btn.getTreeNodeExtendedService();
			    vNode.setAction("./paramTree.do?method=toParamDtl");
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
	 * 树从加载
	 */
	public void reloadParamTree()
	{
		if(paramIvrTree != null)
		{
			if(paramIvrTree.getRoot()!=null)
			{
				paramIvrTree.removeNode(paramIvrTree.getRoot());
			}
			paramIvrTree = null;
		}
		if(ivrBeanMap!=null)
		{
			ivrBeanMap.clear();
		}
		ivrBeanMap = null;
		if(paramTree!=null)
		{
			if(paramTree.getRoot()!=null)
			{
				paramTree.removeNode(paramTree.getRoot());
			}
			paramTree = null;
		}
		loadParamTree();
	}
	/**
	 * 返回树中所有节点
	 * @return
	 */
	public TreeService getParamTree()
	{
		loadParamTree();
		return this.paramIvrTree;
	}
	public IVRBean getIVRBeanById(String id)
	{
		loadParamTree();
		return ivrBeanMap.get(id);
	}
	public List<IVRBean> getIVRBeanListById(String id)
	{
		loadParamTree();
		List<IVRBean> beanList = new ArrayList<IVRBean>();
		Iterator it = ivrBeanMap.values().iterator();
		while(it.hasNext())
		{
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			IVRBean bean = (IVRBean)it.next();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+id+"*"+);
			if(id.equals(bean.getParent_id()))
			{
				beanList.add(bean);
			}
		}
		return beanList;
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
		throw new NoSuchElementException("在ClassTreeServiceImpl 里 调用getIdByNickname("+nickName+") 未找到相应的树节点");
	}
	public List<LabelValueBean> getLabelVaList(String nickName)
	{
		return this.getLabelVaList(nickName, false);
	}
	/**
	 * 根据label value 放入 list里
	 * @param String nickName
	 * @param boolean needRoot 是否需要根节点
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
			throw new NullPointerException("在参数类ClassTreeServiceImpl里 调用getLabelVaList(String nickName) 时 nickName得到的×节点×为null错误");
		}
		if(tcns.getType()==null)
		{
			throw new NullPointerException("在参数类ClassTreeServiceImpl里 调用getLabelVaList(String nickName) 时 nickName得到的节点的type为null错误");
		}
		List<TreeControlNodeService> nodes = tcns.getChildren();
		String rootId = tcns.getId();

		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		this.recursion(list, tcns, rootId,type,needRoot);
		return list;
	}
	/**
	 * 根据label value 放入 list里
	 * @param String nickName
	 * @param String changeRootId 修改根节点Id
	 * @param String changeRootLabel 修改根节点Label
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
			throw new NullPointerException("在参数类ClassTreeServiceImpl里 调用getLabelVaList(String nickName) 时 nickName得到的×节点×为null错误");
		}
		if(tcns.getType()==null)
		{
			throw new NullPointerException("在参数类ClassTreeServiceImpl里 调用getLabelVaList(String nickName) 时 nickName得到的节点的type为null错误");
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
	 * 加工List<LabelValueBean> list
	 * @param list 往里边写入lv
	 * @param node 节点
	 * @param rootId 根节点
	 * @param needRoot 生成的list是否要根节点
	 * @param type ivr department and others
	 */
	private void recursion (List<LabelValueBean> list,TreeControlNodeService node,String rootId,String type,boolean needRoot)
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
				//不需要根节点 且节点id==rootId时什么也不做
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
	 * 加工List<LabelValueBean> list
	 * @param list 往里边写入lv
	 * @param node 节点
	 * @param rootId 根节点
	 * @param type ivr department and others
	 */
	private void recursion (List<LabelValueBean> list,TreeControlNodeService node,String rootId,String type)
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
	 * 根据id得到树节点
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public TreeControlNodeService getNodeById(String id)
	{
		System.out.println("id is :"+id);
		if(paramTree == null)
		{
			this.loadParamTree();
		}
		Iterator it = paramTree.getRegistry().keySet().iterator();
		while(it.hasNext())
		{
			if(id.equals((String)it.next()))
				return (TreeControlNodeService)paramTree.getRegistry().get(id);
		}
		throw new NoSuchElementException("在ClassTreeServiceImpl 里 调用getNodeById("+id+") 的到的树节点为空error");
	}
	/**
	 * 根据id得到树节点label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public String getLabelById(String id)
	{
		TreeControlNodeService node = this.getNodeById(id);
		return node.getLabel();
	}
	/**
	 * 根据nickName得到树节点
	 * @param String nickName
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public TreeControlNodeService getNodeByNickName(String nickName)
	{
		Map registry = paramTree.getRegistry();
		Iterator it = registry.keySet().iterator();
		while(it.hasNext())
		{
			String key = (String)it.next();
			TreeControlNodeService tcns = (TreeControlNodeService)registry.get(key);
			//System.out.println(nickName+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			if(nickName.equals(tcns.getBaseTreeNodeService().getNickName()))
			{
				return tcns;
			}
			System.out.println(tcns.getBaseTreeNodeService().getNickName()+"#######"+tcns.getBaseTreeNodeService().getType());
		}
		throw new NoSuchElementException("在ClassTreeServiceImpl 里 调用getNodeByNickName("+nickName+") 的到的树节点为空error");
	}
	/**
	 * 根据nickName得到树节点label
	 * @param id
	 * @return TreeControlNodeService node
	 * @exception NoSuchElementException 节点没找到
	 */
	public String getLabelByNickName(String nickName)
	{
		TreeControlNodeService node = this.getNodeByNickName(nickName);
		return node.getLabel();
	}
	public IvrClassTreeLoadService getClassTreeLoadService() {
		return classTreeLoadService;
	}


	public void setClassTreeLoadService(IvrClassTreeLoadService classTreeLoadService) {
		this.classTreeLoadService = classTreeLoadService;
	}
	/**
	 * 根据nickname取子树
	 * @return TreeService tree
	 */
	public TreeService getSubTreeByNickName(String nickName)
	{
		if(paramTree == null)
			loadParamTree();
		ViewTreeControlNode node = (ViewTreeControlNode)this.getNodeByNickName(nickName);
		String type = node.getType();
		System.out.println("取子树tree");
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
     * 取子树 
     * @param nickName 根节点
     * @param action 新动作
     * @param target 新目标
     * @return
     */
	public TreeService getSubTreeByNickName(String nickName,String action, String target)
	{
		if(paramTree == null)
			loadParamTree();
		//根据nickName取树节点将树节点克隆一份变成子树返回
		ViewTreeControlNode node = (ViewTreeControlNode)this.getNodeByNickName(nickName);
		String type = node.getType();
		System.out.println("取子树tree");
		TreeInfoService tis = new TreeInfoServiceImpl();
		BuildTreeService bts = new BuildTreeServiceImpl();
		//String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
		List<BaseTreeNodeService> vtnList = new ArrayList<BaseTreeNodeService>();
		
		try {
			this.getBaseTreeNodeServiceList(vtnList, node,type);
			//设置action target
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
	 * 将node加入list中 注意这里有节点类型参数
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
	 * 设置树节点的action行为
	 * @param action 行为
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
	 * 根据id得到树节点nidkName
	 * @param String nickName
	 * @return String id
	 * @exception NoSuchElementException 节点没找到
	 */
	public String getNickNameById(String id)
	{
		Map registry = paramTree.getRegistry();
		TreeControlNodeService node = (TreeControlNodeService)registry.get(id);
		return node.getBaseTreeNodeService().getNickName();

	}
	//************************************
	/**
	 * 
	 * 将叶子节点加到树里供叶子节点授权使用
	 * 使用私有方法getAllLeafRights getBaseTreeNodeServiceFromTree
	 * @param TreeService tree
	 * @param String action 叶子节点权限action
	 * @param String target 叶子节点权限target
	 * @return TreeService
	 */
	public TreeService addLeafs2Tree(TreeService tree,String action,String target)
	{
		//加载所有的叶子节点权限
		return null;
	}
	/**
	 * 
	 * 将部门人员节点加到模块树里供部门批量授权使用
	 * @param TreeService tree
	 */
	public TreeService addPersons2Tree(TreeService tree,String action,String target)
	{
		return null;
	}
	
//	加载SysLeafRight 将其转换成BaseTreeNodeService
	
}
