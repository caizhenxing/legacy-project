/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.parameter.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.parameter.service.ParameterTreeService;
import et.po.BaseTree;
import et.po.ViewTreeDetail;
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
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * 构建部门tree
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class ParameterTreeServiceImpl implements ParameterTreeService{
	/*
	 * 
	 */
	private BaseDAO dao=null;
	private KeyService ks = null;
	
	private ClassTreeService classTreeService;
	/*
	* 隔离数据库，用此集合直接生成树
	*/
	//private TreeInfoService tis = null;
	/*
	 * 完成树的组装
	 */
	//private BuildTreeService bts = null;
	//private static TreeService paramTree = null;
	/*
	 * 树的根节点
	 */
	private String rootId;
	/**
	 * 根据hql语句 根节点id rootId 构造一刻树返回
	 * @param 
	 * @return TreeService 类型树
	 * @throws
	 */
	public TreeService buildTree()
	{
		TreeService ts = null;
		Map registry = classTreeService.getParamTree().getRegistry();//classTreeService.getSubTreeByNickName("paramTreeRoot", "./paramTree.do?method=toParamDtl", "operationframeTree");
		TreeInfoService tis = new TreeInfoServiceImpl();
		BuildTreeService bts = new BuildTreeServiceImpl();
		BaseTreeNodeService btn = null;
		List<BaseTreeNodeService> btnList = new ArrayList<BaseTreeNodeService>();
		Iterator it = registry.keySet().iterator();
		while(it.hasNext())
		{
			//先加载基表在加载子表 将子表注入基表中
			ViewTreeControlNode viewNode = (ViewTreeControlNode)registry.get((String)it.next());
			
			try {
				btn = (BaseTreeNodeService)viewNode.getBaseTreeNodeService().clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ViewTreeNode vtn = (ViewTreeNode)btn.getTreeNodeExtendedService();
			vtn.setAction("./paramTree.do?method=toParamDtl");
			vtn.setTarget("operationframeTree");
			btnList.add(btn);
			
			if(SysStaticParameter.PARAMETER_TREE_ROOT_ID.equals(btn.getId()))
			{
				//System.out.println("**********root************root************");
				ViewTreeControlNode vtcn = new ViewTreeControlNode();
				//vtcn.setTreeNodeService(vtn);
				vtcn.setBaseTreeNodeService(btn);
				vtcn.getBaseTreeNodeService().setIsRoot("1");
				//this.getTis().setRoot(vtcn);
				tis.setRoot(vtcn);
				//System.out.println(this.getTis().getRoot()+">>>>>>>>>>>>>>>>>>>>");
			}
			
		}
		tis.setTreeNodeList(btnList);
		bts.setTreeService(new ViewTree());
		return bts.creator(tis);
	}
	/**
	 * 根据nickName构造一刻树返回
	 * @param nickName 根节点唯一名
	 * @return TreeService 类型树
	 * @throws
	 */
	public TreeService buildTree(String nickName,boolean rootNeedAction)
	{
		TreeService ts = classTreeService.getSubTreeByNickName(nickName, "./paramTree.do?method=toParamDtl", "operationframeTree");
		if(rootNeedAction)
		{
			ViewTreeControlNode root = (ViewTreeControlNode)ts.getRoot();
			ViewTreeNode v = (ViewTreeNode)root.getBaseTreeNodeService().getTreeNodeExtendedService();
			v.setAction("./paramTree.do?method=toParamDtl");
			v.setTarget("operationframeTree");
		}
		return ts;
	}
	/**
	 * 根据NickName以label Value的形式显示该树的子节点列表
	 * @param id BaseTree的ID
	 * @return List<LabelValueBean> value is id label is name
	 * @throws
	 */
    public List<LabelValueBean> getLabelValueBeanListByNickName(String nickName,TreeService tree)
    {
    	//TreeService tree = this.getParamTree();
    	TreeControlNodeService tcn = tree.findNodeByNickName(nickName);
    	TreeControlNodeService[] stcn = tcn.findChildren();
    	List<LabelValueBean> lvList = new ArrayList<LabelValueBean>();
    	for(int i=0; i<stcn.length; i++)
    	{
    		LabelValueBean lv = new LabelValueBean();
    		lv.setLabel(((TreeControlNodeService)stcn[i]).getLabel());
    		lv.setValue(((TreeControlNodeService)stcn[i]).getId());
    		lvList.add(lv);
    	}
    	return lvList;
    }
    /**
     * 根据id将BaseTree and ViewTreeDtail加载到IBaseDTO中返回给视图调用
     * @param id
     * @return
     * @throws
     */
    public IBaseDTO loadViewBeanById(String id)
    {
    	BaseTree bt = (BaseTree)dao.loadEntity(BaseTree.class, id);
    	//父节点树
    	BaseTree pbt = null;
    	IBaseDTO dto = new DynaBeanDTO();
    	dto.set("id", bt.getId());
    	dto.set("parentId", bt.getParentId());
    	dto.set("label", bt.getLabel());
    	dto.set("nickName",bt.getNickName());
    	dto.set("deleteMark", bt.getDeleteMark());
    	dto.set("modifyTime", TimeUtil.getTheTimeStr(bt.getModifyTime(),"yyyy-MM-dd HH:mm:ss"));
    	dto.set("createTime", TimeUtil.getTheTimeStr(bt.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
    	dto.set("isRoot", bt.getIsRoot());
    	//dto.set("createTime", bt.getCreateTime());
    	dto.set("type",bt.getType());
    	dto.set("remark",bt.getRemark());
    	
    	//加载父节点
    	pbt = (BaseTree)dao.loadEntity(BaseTree.class, bt.getParentId());
    	if(pbt!=null)
    		dto.set("parentName", pbt.getLabel());
    	
    	ViewTreeDetail viewTree = (ViewTreeDetail)dao.loadEntity(ViewTreeDetail.class, id);
    	dto.set("action", viewTree.getAction());
    	dto.set("icon", viewTree.getIcon());
    	dto.set("target",viewTree.getTarget());
    	return dto;
    }
	/**
	 * 根据dto完成树的增加
	 * @param IBaseDTO dto 树的信息
	 * @return 
	 * @throws
	 */
    public void addParamTree(IBaseDTO dto)
    {
    	//dto转BaseTree
    	BaseTree bt = new BaseTree();
    	String id = ks.getNext("SYS_TREE");
    	bt.setId(id);
    	bt.setLabel(dto.get("label")==null?"":dto.get("label").toString());
    	bt.setNickName(dto.get("nickName")==null?"":dto.get("nickName").toString());
    	bt.setParentId(dto.get("parentId")==null?"":dto.get("parentId").toString());
    	bt.setType(dto.get("type")==null?"":dto.get("type").toString());
    	bt.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
    	bt.setDeleteMark("0");
    	bt.setCreateTime(new java.util.Date());
    	bt.setModifyTime(new java.util.Date());
    	bt.setLayerOrder(id);
    	String isRoot = (String)dto.get("isRoot");
        if(isRoot==null)
        	isRoot = "0";
    	bt.setIsRoot(isRoot);
    	//dto转ViewTreeDetail
    	ViewTreeDetail viewTree = new ViewTreeDetail();
    	viewTree.setId(id);
    	viewTree.setTarget(dto.get("target")==null?"blank":dto.get("target").toString());
    	viewTree.setAction(dto.get("action")==null?"":dto.get("action").toString());
    	viewTree.setIcon("expanded=folderOpen.gif;closed=folderClose.gif;leaf=leaf.gif;");
    	//存基表
    	dao.saveEntity(bt);
        //存子表
    	dao.saveEntity(viewTree);
    	
    }
    /**
	 * 根据dto完成树的修改
	 * @param IBaseDTO dto 树的信息
	 * @return 
	 * @throws
	 */
    public void updateParamTree(IBaseDTO dto)
    {
    	
    	String id = dto.get("id")==null?"":dto.get("id").toString();
    	BaseTree bt = (BaseTree)dao.loadEntity(BaseTree.class, id);
    	//bt.setId(id);
    	bt.setLabel(dto.get("label")==null?"":dto.get("label").toString());
    	bt.setNickName(dto.get("nickName")==null?"":dto.get("nickName").toString());
    	bt.setParentId(dto.get("parentId")==null?"":dto.get("parentId").toString());
    	bt.setType(dto.get("type")==null?"":dto.get("type").toString());
    	String deleteMark= "0";
    	if(dto.get("deleteMark")==null)
    	{
    		deleteMark = "0";
    	}
    	else
    	{
    		if("".equals(((String)dto.get("deleteMark")).trim()))
    		{
    			deleteMark = "0";
    		}
    		else
    			deleteMark = ((String)dto.get("deleteMark")).trim();
    	}
    	bt.setDeleteMark(deleteMark);
    	bt.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
    	bt.setModifyTime(new java.util.Date());
    	String isRoot = (String)dto.get("isRoot");
        if(isRoot==null)
        	isRoot = "0";
    	bt.setIsRoot(isRoot);
    	ViewTreeDetail viewTree = (ViewTreeDetail)dao.loadEntity(ViewTreeDetail.class, id);
    	viewTree.setTarget(dto.get("target")==null?"blank":dto.get("target").toString());
    	viewTree.setIcon(dto.get("icon")==null?"":dto.get("icon").toString());
    	viewTree.setAction(dto.get("action")==null?"":dto.get("action").toString());
    	//更新基表
    	dao.updateEntity(bt);
    	//更新子表
    	dao.updateEntity(viewTree);
    }
    /**
	 * 逻辑删除树 将deleteMark设1
	 * @param String id
	 * @return 
	 * @throws
	 */
    private void removeParamTree(String id)
    {
    	BaseTree bt=(BaseTree)dao.loadEntity(BaseTree.class,id);
		bt.setDeleteMark("1");
		dao.updateEntity(bt);
    }
	/**
	 * 删除树及其子节点
	 * @param TreeControlNodeService node 带删除节点
	 * @return
	 * @throws
	 */
    public void deleteNode(TreeControlNodeService node)
	{
		removeParamTree(node.getId());
		TreeControlNodeService[] nodearr = node.findChildren();
		if(nodearr.length>0)
		{
			for(int i=0; i<nodearr.length; i++)
			{
				deleteNode(nodearr[i]);
			}
		}
	}
	
	/**
	 * 根据NickName以该节点的id
	 * @param String nickName,TreeService tree
	 * @return
	 * @throws
	 */
    public String getIdByNidkName(String nickName,TreeService tree)
    {
    	return tree.findNodeByNickName(nickName).getId();
    }
    /**
	 * 得到BaseDAO dao的实现类
	 * @param 
	 * @version 2008-1-24
	 * @return BaseDAO dao
	 */
	public BaseDAO getDao() {
        return dao;
    }
	/**
	 * 注入BaseDAO的实现类
	 * @param BaseDAO dao
	 * @version 2008-1-24
	 * @return
	 */
    public void setDao(BaseDAO dao) {
        this.dao = dao;
    }
    /**
	 * 得到KeyService ks的实现类
	 * @param 
	 * @version 2008-1-24
	 * @return KeyService ks
	 */
	public KeyService getKs() {
		return ks;
	}
	/**
	 * 注入KeyService的实现类
	 * @param KeyService ks
	 * @version 2008-1-24
	 * @return
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	/**
	 * 得到TreeInfoService的实现类
	 * @param 
	 * @version 2008-1-24
	 * @return BuildTreeService bts
	 */
//	public TreeInfoService getTis() {
//		return tis;
//	}
	/**
	 * 注入TreeInfoService的实现类
	 * @param TreeInfoService tis
	 * @version 2008-1-24
	 * @return
	 */
//	public void setTis(TreeInfoService tis) {
//		this.tis = tis;
//	}
	/**
	 * 得到BuildTreeService的实现类
	 * @param 
	 * @version 2008-1-24
	 * @return BuildTreeService bts
	 */
//	public BuildTreeService getBts() {
//		return bts;
//	}
	/**
	 * 注入BuildTreeService的实现类
	 * @param BuildTreeService bts
	 * @version 2008-1-24
	 * @return
	 */
//	public void setBts(BuildTreeService bts) {
//		this.bts = bts;
//	}
	/**
	 * 得到根节点id
	 * @param 
	 * @return String rootId
	 * @throws
	 */
	public String getRootId()
	{
		return this.rootId;
	}
	/**
	 * 设置根节点id
	 * @param String rootId
	 * @return
	 * @throws
	 */
	public void setRootId(String rootId)
	{
		this.rootId = rootId;
	}
	public ClassTreeService getClassTreeService() {
		return classTreeService;
	}
	public void setClassTreeService(ClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
	}
	
}
