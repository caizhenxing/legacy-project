/**
 * className TreePropertyService 
 * 
 * 创建日期 2008-1-4
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.sys.leaf.leafRight.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.leaf.leafRight.service.LeafRightService;
import et.po.BaseTree;
import et.po.SysLeafRight;
import et.po.SysRightRole;
import et.po.SysRole;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tree.base.service.TreeService;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.common.tree.ext.view.impower.ViewTreeControlImpowerNode;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * 叶子节点字典表授权
 *
 * @version 	jan 01 2008 
 * @author 王文权 荆玉琢
 */
public class LeafRightServiceImpl implements LeafRightService{
	/*
	 *  
	 */
	private BaseDAO dao=null;
	private KeyService ks = null;
	private int num = 0;
	private LeafRightSearch searchHelp = new LeafRightSearch();
	/**
	 * 得到叶子节点权限列表
	 * @param nodeId
	 * @return List<SysLeafRight>
	 */
	public List<DynaBeanDTO> getLeafRightByNodeId(String nodeId)
	{
		List<DynaBeanDTO> rights = new ArrayList<DynaBeanDTO>();
		if(nodeId != null)
		{
			String hql = "from SysLeafRight where a.baseTree.id = '"+nodeId+"' order by a.nickName";
			MyQuery mq = new MyQueryImpl();
			mq.setHql(hql);
			Object[] o = dao.findEntity(searchHelp.searchLeafRightByNodeId(nodeId));
			DynaBeanDTO dto = null;
			for(int i=0; i<o.length; i++)
			{
				 dto = new DynaBeanDTO();
				 SysLeafRight right = (SysLeafRight)o[i];
				 dto.set("id", right.getId());
				 dto.set("treeId", right.getBaseTree().getId());
				 dto.set("treeName",right.getBaseTree().getLabel());
				 dto.set("type", right.getType());
				 dto.set("nickName",right.getNickName());
				 dto.set("deleteMark", right.getDeleteMark());
				 dto.set("remark", right.getRemark());
				 rights.add(dto);
			}
				
		}
		return rights;
	}

	public void deleteRoleRights(String roleId,String treeId)
	{
		Object[] o = dao.findEntity(searchHelp.searchRightRole(roleId, treeId));
		
		for(int i=0; i<o.length; i++)
		{
			SysRightRole srr = (SysRightRole)o[i];
			dao.removeEntity(srr);
		}
		dao.flush();
	}
	public void grantRoleRights(String roleId,List<String> leafRightIds)
	{
		
		SysRole sr = (SysRole)dao.loadEntity(SysRole.class, roleId);
		for(int i=0; i<leafRightIds.size(); i++)
		{
			SysRightRole srr = new SysRightRole();
			//System.out.println(leafRightIds.get(i));
			SysLeafRight slr = (SysLeafRight)dao.loadEntity(SysLeafRight.class, leafRightIds.get(i));
			srr.setId(ks.getNext("SYS_RIGHT_ROLE"));
			srr.setSysRole(sr);
			srr.setSysLeafRight(slr);
			dao.saveEntity(srr);
		}
		
		
	}
	
	/**
	 * 给角色授权
	 * @param roleId
	 * @param TreeService rights
	 * @return
	 */
	public void impowerRole(String roleId, TreeService rights) {
		// TODO Auto-generated method stub
		SysRole sr=(SysRole)dao.loadEntity(SysRole.class,roleId);
		Set s=sr.getSysRightRoles();
		Iterator i=s.iterator();
		while(i.hasNext())
		{
			SysRightRole srr=(SysRightRole)i.next();
			dao.removeEntity(srr);
		}
		dao.flush();
		//String rootName = rights.getRoot().getId();
		Map registry = rights.getRegistry();
		Iterator iterator = registry.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = (String) iterator.next();
			ViewTreeControlNode node = (ViewTreeControlNode) registry.get(key);
			String nodeType = node.getType();
			if(nodeType==null)
				nodeType = "";
			//是角色图标 类型是leaf_right_%
			if (SysStaticParameter.RICON.equalsIgnoreCase(node.getTmpIcon())&&nodeType.indexOf("leaf_right")!=-1)
			{
				SysRightRole dbsrr=new SysRightRole();
				dbsrr.setId(ks.getNext("SYS_RIGHT_ROLE"));
				dbsrr.setSysRole(sr);
				SysLeafRight slr = (SysLeafRight)dao.loadEntity(SysLeafRight.class, node.getId());
				dbsrr.setSysLeafRight(slr);
				dao.saveEntity(dbsrr);
			}	
		}
	}
	
	/**
	 * 当前角色的权限图标 其他正常显示
	 * @param roleId
	 * @param TreeService rights
	 * @return
	 */
	public void impowerRoleIcon(String roleId, TreeService rights,String tmpIcon)
	{
		SysRole sr = (SysRole)dao.loadEntity(SysRole.class, roleId);
		Map registry = rights.getRegistry();
		Iterator keys = registry.keySet().iterator();
		Iterator it = sr.getSysRightRoles().iterator();
		//清除临时图标
		while(keys.hasNext())
		{
			//System.out.println("+++++++++++++++++++++"+registry.get((String)keys.next()).getClass());
			ViewTreeControlImpowerNode node = (ViewTreeControlImpowerNode)registry.get((String)keys.next());
			node.setTmpIcon(null);
		}
		//给角色拥有的权限赋图标
		while(it.hasNext())
		{
			SysRightRole srr = (SysRightRole)it.next();
			String leafRightId = srr.getSysLeafRight().getId();
			ViewTreeControlImpowerNode lnode = (ViewTreeControlImpowerNode)registry.get(leafRightId);
			if(lnode!=null)
				lnode.setTmpIcon(tmpIcon);
		}
	}
	/**
	 * 给用户批量授权
	 * @param roleId
	 * @param TreeService rights
	 * @return
	 */
	public void impowerBatchPerson2Role(String roleId, TreeService rights)
	{
		//TODO Auto-generated method stub
		SysRole sr=(SysRole)dao.loadEntity(SysRole.class,roleId);
		//String rootName = rights.getRoot().getId();
		Map registry = rights.getRegistry();
		Iterator iterator = registry.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = (String) iterator.next();
			ViewTreeControlNode node = (ViewTreeControlNode) registry.get(key);
			String nodeType = node.getType();
			if(nodeType==null)
				nodeType = "";
			//是角色图标 类型是leaf_right_%
			if (SysStaticParameter.RICON.equalsIgnoreCase(node.getTmpIcon())&&nodeType.indexOf("batchRightUser")!=-1)
			{
				SysUser su = (SysUser)dao.loadEntity(SysUser.class, node.getId());
				su.setSysRole(sr);
				dao.saveEntity(su);
			}	
		}
	}
	 /*
	  * 表主键
	  */
	 public String getKey()
	 {
		 return ks.getNext("SYS_LEAF");
	 }
	
	/**
	 * 加载用户所具有的模块权限
	 * @param String userId 当前用户id
	 * @return TreeService 模块及叶子节点
	 * @throws
	 */
	public TreeService loadTree(String userId)
	{
		TreeService tree = null;
		return tree;
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
	
	//**************************************************
	/**
	 * 删除树及其子节点
	 * @param TreeControlNodeService node 带删除节点
	 * @return
	 * @throws
	 */
	public void deleteDict(String id)
	{
		SysLeafRight slr = (SysLeafRight)dao.loadEntity(SysLeafRight.class, id);
		dao.removeEntity(slr);
	}
	
	/**
	 * 树节点的增加
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	public void addDict(IBaseDTO dto,String treeId)
	{
	
		SysLeafRight slr = new SysLeafRight();
		
		
		BaseTree bt =  (BaseTree)dao.loadEntity(BaseTree.class, treeId);
		
		slr.setId(ks.getNext("sys_leaf_right"));
		slr.setBaseTree(bt);
		slr.setType(dto.get("treeType").toString());
		slr.setLabel(dto.get("label").toString());
		slr.setNickName(dto.get("nickName").toString());
		slr.setRemark(dto.get("remark").toString());
		slr.setIcon(dto.get("icon").toString());
		slr.setDeleteMark(dto.get("deleteMark").toString());
		slr.setIcon((String)dto.get("icon"));
		dao.saveEntity(slr);
		
	}
	
	/**
	 * 树节点的修改
	 * @param IBaseDTO dto 是BaseTree 和 ViewTreeDict的信息
	 * @return
	 * @throws
	 */
	public boolean updateDict(IBaseDTO dto)
	{
		SysLeafRight slr = (SysLeafRight)dao.loadEntity(SysLeafRight.class, dto.get("id").toString());

		
		slr.setLabel(dto.get("label").toString());
		slr.setNickName(dto.get("nickName").toString());
		slr.setRemark(dto.get("remark").toString());
		slr.setDeleteMark(dto.get("deleteMark").toString());
		slr.setIcon(dto.get("icon").toString());
		slr.setType(dto.get("treeType").toString());
		
		dao.updateEntity(slr);
		
		return true;
	}
	
	
	/**
	 * 根据ID返回详细信息
	 * @param 
	 * @return
	 * @throws
	 */
	public IBaseDTO treeInfo(String id)
	{
		
		SysLeafRight slr = (SysLeafRight)dao.loadEntity(SysLeafRight.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", slr.getId());
		dto.set("treeId", slr.getId());
		dto.set("treeType", slr.getType());
		dto.set("label", slr.getLabel());
		dto.set("nickName", slr.getNickName());
		dto.set("remark", slr.getRemark());
		
		return dto;
	}
	
	
	
	
	public int getTreeSize() {
		// TODO Auto-generated method stub
		return num;
	}
	
	/**
	 * 根据ID返回详细信息
	 * @param 
	 * @return
	 * @throws
	 */
	public List treeList(IBaseDTO dto, PageInfo pi,String id)
	{
		
		List list = new ArrayList();

		LeafRightSearch lrs = new LeafRightSearch();
		
		Object[] result = (Object[]) dao.findEntity(lrs.treeList(dto, pi,id));
		num = dao.findEntitySize(lrs.treeList(dto, pi,id));
		for (int i = 0, size = result.length; i < size; i++) {
			SysLeafRight slr = (SysLeafRight) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			dbd.set("id", slr.getId());
			dbd.set("treeId", slr.getId());
			dbd.set("treeType", slr.getType());
			dbd.set("label", slr.getLabel());
			dbd.set("nickName", slr.getNickName());
			String remark = slr.getRemark()==null?"":slr.getRemark();
			if(remark.length()>15)
			{
				remark = remark.substring(0,15);
			}
			dbd.set("remark", remark);
			dbd.set("icon", slr.getIcon());
			list.add(dbd);
		}
		return list;
		
	}
}
