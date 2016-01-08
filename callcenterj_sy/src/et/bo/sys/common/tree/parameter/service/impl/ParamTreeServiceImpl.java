package et.bo.sys.common.tree.parameter.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.sys.common.tree.parameter.impl.ViewTreeNodeDict;
import et.bo.sys.common.tree.parameter.service.ParamTreeService;
import et.po.BaseTree;
import excellence.common.key.KeyService;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.build.service.BuildTreeService;
import excellence.common.tree.base.impl.BaseTreeNodeServiceImpl;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.base.service.TreeInfoService;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ParamTreeServiceImpl implements ParamTreeService{
	/*
	 * 
	 */
	private BaseDAO dao=null;
	private KeyService ks = null;
	private TreeInfoService tis = null;
	private BuildTreeService bts = null;
	//private static TreeService paramTree = null;
	private String rootId;
	/**
	 * 根据hql语句 根节点id rootId 构造一刻树返回
	 * @param rootId 根节点id
	 * @return TreeService 类型树
	 * @throws
	 */
	public TreeService buildTree()
	{
		String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' ";
		MyQuery mq = new MyQueryImpl();
		List<ViewTreeNodeDict> vtnList = new ArrayList<ViewTreeNodeDict>();
		mq.setHql(hql);
		Object[] o = dao.findEntity(mq);
		for(int i=0; i<o.length; i++)
		{
			Object[] os = (Object[])o[i];
			BaseTreeNodeService btn = new BaseTreeNodeServiceImpl();
			btn.setId(os[0].toString());
			btn.setLabel((os[1]==null?"":os[1].toString()));
			btn.setNickName((os[5]==null?"":os[5].toString()));
			//System.out.println(os[5]+"=====================");
			btn.setParentId((os[2]==null?"":os[2].toString()));
			ViewTreeNodeDict vtn = new ViewTreeNodeDict();
			//vtn.setAction((os[3]==null?"":(String)os[3]));
			vtn.setAction("./paramTree.do?method=toParamDtl&id="+os[0].toString());
			vtn.setTarget("operationframe");
			vtn.setIcon((os[4]==null?"":(String)os[4]));
	
			//vtn.setBaseTreeNodeService(btn);
			btn.setTreeNodeExtendedService(vtn);
			btn.setIsRoot((os[7]==null?"0":(String)os[7]));
			//System.out.println(btn.getIsRoot()+"*"+o[7]);
			vtnList.add(vtn);
			//if("SYS_TREE_0000000625".equals(btn.getParentId()))
			//System.out.println(rootId+":"+btn.getId());
			if(rootId.equals(btn.getId()))
			{
				ViewTreeControlNode vtcn = new ViewTreeControlNode();
				vtcn.setBaseTreeNodeService(btn);
				this.getTis().setRoot(vtcn);
				//System.out.println(this.getTis().getRoot()+">>>>>>>>>>>>>>>>>>>>");
			}
		}
		this.getTis().setTreeNodeList(vtnList);
		TreeService ts = this.getBts().creator(this.getTis());
		return ts;
	}
    
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
    public void addParamTree(IBaseDTO dto)
    {
    	BaseTree bt = new BaseTree();
    	bt.setId(dto.get("id")==null?"":dto.get("id").toString());
    	bt.setLabel(dto.get("label")==null?"":dto.get("label").toString());
    	bt.setNickName(dto.get("nickName")==null?"":dto.get("nickName").toString());
    	bt.setParentId(dto.get("parentId")==null?"":dto.get("parentId").toString());
    	bt.setType(dto.get("type")==null?"":dto.get("type").toString());
    	bt.setRemark(dto.get("remark")==null?"":dto.get("remark").toString());
    	dao.saveEntity(bt);
    	
    }
    public void upateParamTree(IBaseDTO dto)
    {
    	BaseTree bt = new BaseTree();
    	bt.setId(dto.get("id")==null?"":dto.get("id").toString());
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
    	//System.out.println("remark is : "+dto.get("remark"));
    	dao.updateEntity(bt);
    	
    }
    public void removeParamTree(String id)
    {
    	BaseTree bt=(BaseTree)dao.loadEntity(BaseTree.class,id);
		bt.setDeleteMark("1");
		dao.updateEntity(bt);
    }
    public String getIdByNidkName(String nickName,TreeService tree)
    {
    	return tree.findNodeByNickName(nickName).getId();
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

	public TreeInfoService getTis() {
		return tis;
	}

	public void setTis(TreeInfoService tis) {
		this.tis = tis;
	}

	public BuildTreeService getBts() {
		return bts;
	}

	public void setBts(BuildTreeService bts) {
		this.bts = bts;
	}
	
	public String getRootId()
	{
		return this.rootId;
	}
	public void setRootId(String rootId)
	{
		this.rootId = rootId;
	}
    
}
