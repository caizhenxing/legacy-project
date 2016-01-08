/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.sys.department.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.sys.department.service.DepartmentTreeService;
import et.po.BaseTree;
import et.po.StaffBasic;
import et.po.ViewTreeDetail;
import excellence.common.key.KeyService;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.build.impl.BuildTreeServiceImpl;
import excellence.common.tree.base.build.service.BuildTreeService;
import excellence.common.tree.base.impl.BaseTreeNodeServiceImpl;
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
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * ��������tree
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ
 */
public class DepartmentTreeServiceImpl implements DepartmentTreeService{
	/*
	 * 
	 */
	private BaseDAO dao=null;
	private KeyService ks = null;
	/*
	* �������ݿ⣬�ô˼���ֱ��������
	*/
	private TreeInfoService tis = null;
	/*
	 * ���������װ
	 */
	private BuildTreeService bts = null;
	//private static TreeService paramTree = null;
	/*
	 * ���ĸ��ڵ�
	 */
	private String rootId;
	/**
	 * ����hql��� ���ڵ�id rootId ����һ��������
	 * @param 
	 * @return TreeService ������
	 * @throws
	 */
	public TreeService buildTree()
	{
		TreeInfoService tis = new TreeInfoServiceImpl();
		BuildTreeService bts = new BuildTreeServiceImpl();
		String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
		MyQuery mq = new MyQueryImpl();
		List<BaseTreeNodeService> vtnList = new ArrayList<BaseTreeNodeService>();
		mq.setHql(hql);
		Object[] o = dao.findEntity(mq);
		for(int i=0; i<o.length; i++)
		{
			Object[] os = (Object[])o[i];
			//�ȼ��ػ����ڼ����ӱ� ���ӱ�ע�������
			BaseTreeNodeService btn = new BaseTreeNodeServiceImpl();
			btn.setId(os[0].toString());
			btn.setLabel((os[1]==null?"":os[1].toString()));
			btn.setNickName((os[5]==null?"":os[5].toString()));
			
			btn.setParentId((os[2]==null?"":os[2].toString()));
			ViewTreeNode vtn = new ViewTreeNode();
			vtn.setAction((os[3]==null?"":(String)os[3]));
			
			vtn.setTarget("operationframe");
			vtn.setIcon((os[4]==null?"":(String)os[4]));
			vtn.setAction("./deptTree.do?method=toParamDtl");
			//vtn.setBaseTreeNodeService(btn);
			vtn.setTarget((String)os[8]);
			btn.setTreeNodeExtendedService(vtn);
			btn.setIsRoot((os[7]==null?"0":(String)os[7]));
			
			vtnList.add(btn);
			//System.out.println("**********root************root************0"+btn.getIsRoot());
			if("1".equals(btn.getIsRoot()))
			{
				//System.out.println("**********root************root************rrrrrrrrrrrr");
				ViewTreeControlNode vtcn = new ViewTreeControlNode();
				//vtcn.setTreeNodeService(vtn);
				vtcn.setBaseTreeNodeService(btn);
				vtcn.getBaseTreeNodeService().setIsRoot("1");
				//this.getTis().setRoot(vtcn);
				tis.setRoot(vtcn);
				//System.out.println(this.getTis().getRoot()+">>>>>>>>>>>>>>>>>>>>");
			}
			
		}
		tis.setTreeNodeList(vtnList);
		//System.out.println(this.getTis().getRoot()+"aaaatis root");
		bts.setTreeService(new ViewTree());
		TreeService ts = bts.creator(tis);
		return ts;
	}
	/**
	 * ����NickName��label Value����ʽ��ʾ�������ӽڵ��б�
	 * @param id BaseTree��ID
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
     * ����id��BaseTree and ViewTreeDtail���ص�IBaseDTO�з��ظ���ͼ����
     * @param id
     * @return
     * @throws
     */
    public IBaseDTO loadViewBeanById(String id)
    {
    	BaseTree bt = (BaseTree)dao.loadEntity(BaseTree.class, id);
    	//���ڵ���
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
    	
    	//���ظ��ڵ�
    	pbt = (BaseTree)dao.loadEntity(BaseTree.class, bt.getParentId());
    	
    	dto.set("parentName", pbt.getLabel());
    	ViewTreeDetail viewTree = (ViewTreeDetail)dao.loadEntity(ViewTreeDetail.class, id);
    	dto.set("action", viewTree.getAction());
    	dto.set("icon", viewTree.getIcon());
    	dto.set("target",viewTree.getTarget());
    	return dto;
    }
	/**
	 * ����dto�����������
	 * @param IBaseDTO dto ������Ϣ
	 * @return 
	 * @throws
	 */
    public void addParamTree(IBaseDTO dto)
    {
    	//dtoתBaseTree
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
        //dtoתViewTreeDetail
    	ViewTreeDetail viewTree = new ViewTreeDetail();
    	viewTree.setId(id);
    	viewTree.setTarget(dto.get("target")==null?"blank":dto.get("target").toString());
    	viewTree.setAction(dto.get("action")==null?"":dto.get("action").toString());
    	viewTree.setIcon("expanded=folderOpen.gif;closed=folderClose.gif;leaf=leaf.gif;");
    	//�����
    	dao.saveEntity(bt);
        //���ӱ�
    	dao.saveEntity(viewTree);
    	
    }
    /**
	 * ����dto��������޸�
	 * @param IBaseDTO dto ������Ϣ
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
    	//���»���
    	dao.updateEntity(bt);
    	//�����ӱ�
    	dao.updateEntity(viewTree);
    }
    /**
	 * �߼�ɾ���� ��deleteMark��1
	 * @param String id
	 * @return 
	 * @throws
	 */
    public void removeParamTree(String id)
    {
    	BaseTree bt=(BaseTree)dao.loadEntity(BaseTree.class,id);
		bt.setDeleteMark("1");
		dao.updateEntity(bt);
    }
	/**
	 * ɾ���������ӽڵ�
	 * @param TreeControlNodeService node ��ɾ���ڵ�
	 * @return
	 * @throws
	 */
    public void deleteNode(TreeControlNodeService node)
	{
//		removeParamTree(node.getId());
//		TreeControlNodeService[] nodearr = node.findChildren();
//		if(nodearr.length>0)
//		{
//			for(int i=0; i<nodearr.length; i++)
//			{
//				deleteNode(nodearr[i]);
//			}
//		}
    	String id  = node.getId();
    	BaseTree bt = (BaseTree)dao.loadEntity(BaseTree.class, id);
    	bt.setDeleteMark("1");
    	dao.saveEntity(bt);
	}
	/**
	 * ����NickName�Ըýڵ��id
	 * @param String nickName,TreeService tree
	 * @return
	 * @throws
	 */
    public String getIdByNidkName(String nickName,TreeService tree)
    {
    	return tree.findNodeByNickName(nickName).getId();
    }
    /**
	 * �õ�BaseDAO dao��ʵ����
	 * @param 
	 * @version 2008-1-24
	 * @return BaseDAO dao
	 */
	public BaseDAO getDao() {
        return dao;
    }
	/**
	 * ע��BaseDAO��ʵ����
	 * @param BaseDAO dao
	 * @version 2008-1-24
	 * @return
	 */
    public void setDao(BaseDAO dao) {
        this.dao = dao;
    }
    /**
	 * �õ�KeyService ks��ʵ����
	 * @param 
	 * @version 2008-1-24
	 * @return KeyService ks
	 */
	public KeyService getKs() {
		return ks;
	}
	/**
	 * ע��KeyService��ʵ����
	 * @param KeyService ks
	 * @version 2008-1-24
	 * @return
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}
	/**
	 * �õ�TreeInfoService��ʵ����
	 * @param 
	 * @version 2008-1-24
	 * @return BuildTreeService bts
	 */
	public TreeInfoService getTis() {
		return tis;
	}
	/**
	 * ע��TreeInfoService��ʵ����
	 * @param TreeInfoService tis
	 * @version 2008-1-24
	 * @return
	 */
	public void setTis(TreeInfoService tis) {
		this.tis = tis;
	}
	/**
	 * �õ�BuildTreeService��ʵ����
	 * @param 
	 * @version 2008-1-24
	 * @return BuildTreeService bts
	 */
	public BuildTreeService getBts() {
		return bts;
	}
	/**
	 * ע��BuildTreeService��ʵ����
	 * @param BuildTreeService bts
	 * @version 2008-1-24
	 * @return
	 */
	public void setBts(BuildTreeService bts) {
		this.bts = bts;
	}
	/**
	 * �õ����ڵ�id
	 * @param 
	 * @return String rootId
	 * @throws
	 */
	public String getRootId()
	{
		return this.rootId;
	}
	/**
	 * ���ø��ڵ�id
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
	 * �õ��û��б� ͬһ����
	 * @param
	 * @version 2008-02-15
	 * @return
	 */
	public List<LabelValueBean> getUserListByDep(String id) {
		// TODO Auto-generated method stub
		
		BaseTree sd=(BaseTree)dao.loadEntity(BaseTree.class,id);
		if(sd==null)
			return null;
		
		String hql = "select sb from StaffBasic sb where sb.staffId = sb.staffId and sb.BDictDepartment='"+id+"'";
		MyQuery mq = new MyQueryImpl();
		
		mq.setHql(hql);
		Object[] o = dao.findEntity(mq);
		List<LabelValueBean> userList=new ArrayList<LabelValueBean>();
		
		if(o.length>0)
		{
			for(int i=0; i<o.length; i++)
			{
				StaffBasic sb = (StaffBasic) o[i];
				LabelValueBean lvb=new LabelValueBean();
				lvb.setLabel(sb.getBStaffName());
				lvb.setValue(sb.getBStaffNickname());
				userList.add(lvb);
			}
			
			
		}
		
		//		Iterator<SysUser> i=sd.getSysUsers().iterator();
		//		while(i.hasNext())
		//		{
		//			LabelValueBean lvb=new LabelValueBean();
		//			SysUser su=i.next();
		//			if (su.getDeleteMark().equals("1")) {
		//				lvb.setLabel(su.getUserName());
		//				lvb.setValue(su.getUserId());
		//				userList.add(lvb);
		//			}
		//
		//		}
		return userList;
	}
	
	
	/**
	 * ����hql��� ���ڵ�id rootId ����һ��������
	 * @param 
	 * @return TreeService ������
	 * @throws
	 */
	public TreeService buildTree(String nodeAction,String target)
	{
		String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
		MyQuery mq = new MyQueryImpl();
		List<BaseTreeNodeService> vtnList = new ArrayList<BaseTreeNodeService>();
		mq.setHql(hql);
		Object[] o = dao.findEntity(mq);
		for(int i=0; i<o.length; i++)
		{
			Object[] os = (Object[])o[i];
			//�ȼ��ػ����ڼ����ӱ� ���ӱ�ע�������
			BaseTreeNodeService btn = new BaseTreeNodeServiceImpl();
			btn.setId(os[0].toString());
			btn.setLabel((os[1]==null?"":os[1].toString()));
			btn.setNickName((os[5]==null?"":os[5].toString()));
			
			btn.setParentId((os[2]==null?"":os[2].toString()));
			ViewTreeNode vtn = new ViewTreeNode();
			vtn.setAction((os[3]==null?"":(String)os[3]));
			
			vtn.setTarget("operationframe");
			vtn.setIcon((os[4]==null?"":(String)os[4]));
			vtn.setAction(nodeAction);
			//vtn.setBaseTreeNodeService(btn);
			vtn.setTarget(target);
			btn.setTreeNodeExtendedService(vtn);
			btn.setIsRoot((os[7]==null?"0":(String)os[7]));
			
			vtnList.add(btn);
			
			if("1".equals(btn.getIsRoot()))
			{
				//System.out.println("**********root************root************");
				ViewTreeControlNode vtcn = new ViewTreeControlNode();
				//vtcn.setTreeNodeService(vtn);
				vtcn.setBaseTreeNodeService(btn);
				vtcn.getBaseTreeNodeService().setIsRoot("1");
				this.getTis().setRoot(vtcn);
				//System.out.println(this.getTis().getRoot()+">>>>>>>>>>>>>>>>>>>>");
			}
			
		}
		this.getTis().setTreeNodeList(vtnList);
		TreeService ts = this.getBts().creator(this.getTis());
		return ts;
	}
	
	
//	Ҫ�޸�....................
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
}
