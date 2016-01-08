/**
 * className IvrTreeServiceImpl 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.ivr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.common.mylog.MyLog;
import et.bo.sys.basetree.service.impl.IVRTreeNode;
import et.bo.sys.ivr.service.IvrClassTreeService;
import et.bo.sys.ivr.service.IvrTreeService;
import et.po.BaseTree;
import et.po.CcIvrTreeInfo;
import et.po.OperCustinfo;
import et.po.StaffBasic;
import et.po.ViewTreeDetail;
import excellence.common.key.KeyService;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.build.service.BuildTreeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeInfoService;
import excellence.common.tree.base.service.TreeService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * Ivr树的实现
 *
 * @version 	jan 01 2008 
 * @author 王文权
 */
public class IvrTreeServiceImpl implements IvrTreeService{
	/*
	 * 
	 */
	private BaseDAO dao=null;
	private KeyService ks = null;
	private IvrClassTreeService classTreeService;
	/*
	* 隔离数据库，用此集合直接生成树
	*/
	private TreeInfoService tis = null;
	/*
	 * 完成树的组装
	 */
	private BuildTreeService bts = null;
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
		classTreeService.reloadParamTree();
		return classTreeService.getParamTree();
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
    	//dto.set("createTime", bt.getCreateTime());
    	dto.set("type",bt.getType());
    	dto.set("remark",bt.getRemark());
    	
    	//加载父节点
    	pbt = (BaseTree)dao.loadEntity(BaseTree.class, bt.getParentId());
    	
    	dto.set("parentName", pbt.getLabel());
    	ViewTreeDetail viewTree = (ViewTreeDetail)dao.loadEntity(ViewTreeDetail.class, id);
    	dto.set("action", viewTree.getAction());
    	dto.set("icon", viewTree.getIcon());
    	dto.set("target",viewTree.getTarget());
    	
    	//加载CcIvrTreeInfo
    	CcIvrTreeInfo info = (CcIvrTreeInfo)dao.loadEntity(CcIvrTreeInfo.class, id);
    	if(info!=null)
    	{
	    	dto.set("CcheckValue",info.getCheckValue());
	    	dto.set("Cid",info.getId());
	    	dto.set("Ccontent",info.getContent());
	    	dto.set("CdeleteMark",info.getDeleteMark());
	
	    	dto.set("Cfunctype",info.getFunctype());
	    	dto.set("ClanguageType",info.getFunctype());
	    	dto.set("ClengthSize",info.getLengthSize());
	    	dto.set("Cnickname",info.getNickname());
	
	    	dto.set("CtelKey",info.getTelKey());
	    	dto.set("CtelNum",info.getTelNum());
	    	dto.set("CvoiceType",info.getVoiceType());
	    	/*
	    	 * <form-property name="CorderProgramme" type="java.lang.String" />
			<form-property name="CcustomizeCancel" type="java.lang.String" />
			<form-property name="CexpertId" type="java.lang.String" />
	    	 */
	    	dto.set("CorderProgramme", "Y".equals(info.getOrderProgramme())?"on":"");
	    	dto.set("CcustomizeCancel", "Y".equals(info.getCustomizeCancel())?"on":"");
	    	dto.set("CexpertId", info.getExpertId());
    	}
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
    	bt.setLayerOrder(dto.get("layerOrder")==null?"":dto.get("layerOrder").toString());
        //dto转ViewTreeDetail
    	ViewTreeDetail viewTree = new ViewTreeDetail();
    	viewTree.setId(id);
    	viewTree.setTarget(dto.get("target")==null?"blank":dto.get("target").toString());
    	viewTree.setAction(dto.get("action")==null?"":dto.get("action").toString());
    	viewTree.setIcon("expanded=folderOpen.gif;closed=folderClose.gif;leaf=leaf.gif;");
    	//dto转CcIvrTreeInfo
    	CcIvrTreeInfo info = new CcIvrTreeInfo();
    	info.setId(id);
    	info.setCheckValue(dto.get("CcheckValue")==null?"":dto.get("CcheckValue").toString());
    	//dto.get("CcheckValue")==null?"":dto.get("CcheckValue").toString()
    	info.setContent(dto.get("Ccontent")==null?"":dto.get("Ccontent").toString());
    	info.setDeleteMark(dto.get("CdeleteMark")==null?"":dto.get("CdeleteMark").toString());
    	info.setFunctype(dto.get("Cfunctype")==null?"":dto.get("Cfunctype").toString());
    	info.setLanguageType(dto.get("ClanguageType")==null?"":dto.get("ClanguageType").toString());
    	info.setLengthSize(dto.get("ClengthSize")==null?"":dto.get("ClengthSize").toString());
    	info.setNickname(dto.get("Cnickname")==null?"":dto.get("Cnickname").toString());
    	info.setTelKey(dto.get("CtelKey")==null?"":dto.get("CtelKey").toString());
    	info.setTelNum(dto.get("CtelNum")==null?"":dto.get("CtelNum").toString());
    	info.setVoiceType(dto.get("CvoiceType")==null?"":dto.get("CvoiceType").toString());
    	info.setDeleteMark("0");
    	
    	info.setOrderProgramme("on".equals(dto.get("CorderProgramme"))?"Y":"N");
    	info.setCustomizeCancel("on".equals(dto.get("CcustomizeCancel"))?"Y":"N");
    	info.setExpertId(dto.get("CexpertId")==null?"":dto.get("CexpertId").toString());
    	
    	//存基表
    	dao.saveEntity(bt);
        //存子表
    	dao.saveEntity(viewTree);
    	//存CcIvrTreeInfo
    	dao.saveEntity(info);
    	
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
    		deleteMark = ((String)dto.get("deleteMark")).trim();
    	}
    	bt.setDeleteMark(deleteMark);
    	bt.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
    	bt.setModifyTime(new java.util.Date());
    	ViewTreeDetail viewTree = (ViewTreeDetail)dao.loadEntity(ViewTreeDetail.class, id);
    	viewTree.setTarget(dto.get("target")==null?"blank":dto.get("target").toString());
    	viewTree.setIcon(dto.get("icon")==null?"":dto.get("icon").toString());
    	viewTree.setAction(dto.get("action")==null?"":dto.get("action").toString());
    	
    	CcIvrTreeInfo info = (CcIvrTreeInfo)dao.loadEntity(CcIvrTreeInfo.class, id);
    	info.setId(id);
    	info.setCheckValue(dto.get("CcheckValue")==null?"":dto.get("CcheckValue").toString());
    	//dto.get("CcheckValue")==null?"":dto.get("CcheckValue").toString()
    	info.setContent(dto.get("Ccontent")==null?"":dto.get("Ccontent").toString());
    	info.setDeleteMark(dto.get("CdeleteMark")==null?"":dto.get("CdeleteMark").toString());
    	info.setFunctype(dto.get("Cfunctype")==null?"":dto.get("Cfunctype").toString());
    	info.setLanguageType(dto.get("ClanguageType")==null?"":dto.get("ClanguageType").toString());
    	info.setLengthSize(dto.get("ClengthSize")==null?"":dto.get("ClengthSize").toString());
    	info.setNickname(dto.get("Cnickname")==null?"":dto.get("Cnickname").toString());
    	//System.out.println(info.getNickname()+")))))))))))))))((((((((((((");
    	info.setTelKey(dto.get("CtelKey")==null?"":dto.get("CtelKey").toString());
    	info.setTelNum(dto.get("CtelNum")==null?"":dto.get("CtelNum").toString());
    	info.setVoiceType(dto.get("CvoiceType")==null?"":dto.get("CvoiceType").toString());
    	
    	info.setOrderProgramme("on".equals(dto.get("CorderProgramme"))?"Y":"N");
    	info.setCustomizeCancel("on".equals(dto.get("CcustomizeCancel"))?"Y":"N");
    	info.setExpertId(dto.get("CexpertId")==null?"":dto.get("CexpertId").toString());
    	//MyLog.info("###########****************************"+dto.get("CorderProgramme")+dto.get("CcustomizeCancel"));
    	//更新基表
    	dao.updateEntity(bt);
    	//更新子表 ViewTreeDetail
    	dao.updateEntity(viewTree);
    	//更新CcIvrTreeInfo
    	dao.updateEntity(info);
    }
    /**
	 * 逻辑删除树 将deleteMark设1
	 * @param String id
	 * @return 
	 * @throws
	 */
    public void removeParamTree(String id)
    {
    	BaseTree bt=(BaseTree)dao.loadEntity(BaseTree.class,id);
		bt.setDeleteMark("1");
		//ViewTreeDetail view= (ViewTreeDetail)dao.loadEntity(ViewTreeDetail.class, id);
		//view.s
		CcIvrTreeInfo info = (CcIvrTreeInfo)dao.loadEntity(CcIvrTreeInfo.class, id);
		info.setDeleteMark("1");
		dao.updateEntity(bt);
		dao.updateEntity(info);
    }
	/**
	 * 删除树及其子节点
	 * @param TreeControlNodeService node 带删除节点
	 * @return
	 * @throws
	 */
    public void deleteNode(TreeControlNodeService node)
	{
    	String id  = node.getId();
    	BaseTree bt = (BaseTree)dao.loadEntity(BaseTree.class, id);
    	bt.setDeleteMark("1");
    	CcIvrTreeInfo info = (CcIvrTreeInfo)dao.loadEntity(CcIvrTreeInfo.class, id);
    	info.setDeleteMark("1");
    	dao.saveEntity(bt);
    	dao.saveEntity(info);
	}
	/**
	 * 得到专家列表
	 * @return List<LabelValueBean> id name
	 */
	public List<LabelValueBean> getExperterList()
	{
		List<LabelValueBean> beanList = new ArrayList<LabelValueBean>();
		String hql = "from OperCustinfo a where a.dictCustType = '"+"SYS_TREE_0000000684"+"' order by a.custName";
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		Object[] o = dao.findEntity(mq);
		for(int i=0; i<o.length; i++)
		{
			OperCustinfo info = (OperCustinfo)o[i];
			LabelValueBean bean = new LabelValueBean();
			bean.setLabel(info.getCustName());
			bean.setValue(info.getCustId());
			beanList.add(bean);
		}
		return beanList;
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
	public TreeInfoService getTis() {
		return tis;
	}
	/**
	 * 注入TreeInfoService的实现类
	 * @param TreeInfoService tis
	 * @version 2008-1-24
	 * @return
	 */
	public void setTis(TreeInfoService tis) {
		this.tis = tis;
	}
	/**
	 * 得到BuildTreeService的实现类
	 * @param 
	 * @version 2008-1-24
	 * @return BuildTreeService bts
	 */
	public BuildTreeService getBts() {
		return bts;
	}
	/**
	 * 注入BuildTreeService的实现类
	 * @param BuildTreeService bts
	 * @version 2008-1-24
	 * @return
	 */
	public void setBts(BuildTreeService bts) {
		this.bts = bts;
	}
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
	//***************************************************888
	
	
	
	/**
	 * 根据hql语句 根节点id rootId 构造一刻树返回
	 * @param 
	 * @return TreeService 类型树
	 * @throws
	 */
	public TreeService buildTree(String nodeAction,String target)
	{
		if(classTreeService.getParamTree()==null)
		{
			classTreeService.loadParamTree();
		}
		TreeService tree = classTreeService.getParamTree();
		classTreeService.setNodeActionFromTree("../yuyin.do?method=toyuyinTreeLoad", tree);
		return tree;
	}
	
	/**
	 * 从内存中得到树状列表的信息
	 * 
	 * @return list<TreeNode>
	 */
	public List<IVRTreeNode> getStaticTreeInfo()
	{
		List<IVRTreeNode> ivrNodeList= new ArrayList<IVRTreeNode>();
		classTreeService.getParamTree();
		return ivrNodeList;
	}
//	要修改....................
	public List<LabelValueBean> getUserListselect(IBaseDTO dto) {
		// TODO Auto-generated method stub

		
		List<LabelValueBean> userList=new ArrayList<LabelValueBean>();
		
	
			MyQuery mq=new MyQueryImpl();
			DetachedCriteria dc=DetachedCriteria.forClass(StaffBasic.class);
			String name =(String)dto.get("selectName");
			
			if(name!=null&&!name.equals("")){
				dc.add(Restrictions.like("BStaffName", "%"+name+"%"));
			}
			
//			dc.add(Restrictions.eq("deleteMark", "1"));
			mq.setDetachedCriteria(dc);

		Object[] result = (Object[]) dao.findEntity(mq);

		for (int i = 0, size = result.length; i < size; i++) 
		{
			StaffBasic sb = (StaffBasic) result[i];
			LabelValueBean lvb=new LabelValueBean();
			
			lvb.setLabel(sb.getBStaffName());
			lvb.setValue(sb.getBStaffNickname());
			userList.add(lvb);
		}
		return userList;
	}
	public IvrClassTreeService getClassTreeService() {
		return classTreeService;
	}
	public void setClassTreeService(IvrClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
	}
}
