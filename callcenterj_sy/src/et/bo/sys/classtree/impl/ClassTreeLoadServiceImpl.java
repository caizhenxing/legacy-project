package et.bo.sys.classtree.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.BaseTree;
import et.po.SysLeafRight;
import et.po.SysUser;
import et.po.ViewTreeDetail;
import excellence.common.classtree.ClassTreeLoadService;
import excellence.common.key.KeyService;
import excellence.common.tree.base.impl.BaseTreeNodeServiceImpl;
import excellence.common.tree.base.service.BaseTreeNodeService;
import excellence.common.tree.ext.view.impl.ViewTreeNode;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ClassTreeLoadServiceImpl implements ClassTreeLoadService {
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
	/**
	 * 将db xml等的数据载入TreeNodeService里
	 * @return List<BaseTreeNodeService>
	 */
	public List<BaseTreeNodeService> loadTreeNodeService() {
		// TODO Auto-generated method stub
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(BaseTree.class);
		dc.add(Expression.ne("deleteMark", "1"));
		dc.addOrder(Order.asc("layerOrder"));
		mq.setDetachedCriteria(dc);
		//String hql = "select a.id,a.label,a.parentId,b.action, b.icon,a.nickName,a.deleteMark,a.isRoot, b.target from BaseTree a,ViewTreeDetail b where a.id = b.id and a.deleteMark != '1' and a.type = 'departParam'";
		List<BaseTreeNodeService> btnList = new ArrayList<BaseTreeNodeService>();
		
		Object[] o = dao.findEntity(mq);
		for(int i=0; i<o.length; i++)
		{
			BaseTree bt = (BaseTree)o[i];
			//先加载基表在加载子表 将子表注入基表中
			BaseTreeNodeService btn = new BaseTreeNodeServiceImpl();
			btn.setId(bt.getId());
			btn.setLabel(bt.getLabel());
			btn.setNickName(bt.getNickName());
			
			btn.setParentId(bt.getParentId());
			btn.setLayerOrder(bt.getLayerOrder());
			btn.setType(bt.getType());
			ViewTreeNode vtn = new ViewTreeNode();
			Iterator it =  bt.getViewTreeDetails().iterator();
			//System.out.println(bt.getViewTreeDetails().isEmpty()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
			if(it.hasNext())
			{
				ViewTreeDetail vtd = (ViewTreeDetail)it.next();
				vtn.setAction(vtd.getAction());
				
				vtn.setTarget(vtd.getTarget());
				vtn.setIcon(vtd.getIcon());
				vtn.setTagShow(vtd.getTagShow());
				//System.out.println(vtd.getTagShow()+"*********************************");
			}

		
			btn.setTreeNodeExtendedService(vtn);
			btn.setIsRoot(bt.getIsRoot());
			
			btnList.add(btn);
		}
		return btnList;
	}

	/**
	 * 将db xml等的数据载入TreeNodeService里
	 * @param String action 节点行为
	 * @param String target 节点目标
	 * @return List<BaseTreeNodeService>
	 */
	public List<BaseTreeNodeService> loadLeafRights(String action, String target)
	{
		List<BaseTreeNodeService> leafRights = new ArrayList<BaseTreeNodeService>();
		MyQuery mq = new MyQueryImpl();
		mq.setHql("from SysLeafRight a order by a.baseTree.id");
		Object [] os = dao.findEntity(mq);
		BaseTreeNodeService bt = null;
		ViewTreeNode vtn = null;
		SysLeafRight leafRight = null;
		for(int i=0; i<os.length; i++)
		{
			try{
			bt = new BaseTreeNodeServiceImpl();
			vtn = new ViewTreeNode();
			leafRight = (SysLeafRight)os[i];
			bt.setId(leafRight.getId());
			bt.setParentId(leafRight.getBaseTree().getId());
			bt.setNickName(leafRight.getNickName());
			bt.setType(leafRight.getType());
			bt.setLabel(leafRight.getLabel());
			bt.setRemark(leafRight.getRemark());
			vtn.setAction(action);
			vtn.setTarget(target);
			vtn.setIcon(leafRight.getIcon());
			bt.setTreeNodeExtendedService(vtn);
			leafRights.add(bt);
			}catch(Exception e){e.printStackTrace();}
		}
		
		return leafRights;
	}
	
	/**
	 * 将用户信息封装到BaseTreeNodeService里在生成部门树时其作为叶子节点附加到部门树上
	 * @param String action 节点行为
	 * @param String target 节点目标
	 * @return List<BaseTreeNodeService> 
	 */
	public List<BaseTreeNodeService> getAllUsersForDeptTree(String action, String target)
	{
		List<BaseTreeNodeService> users = new ArrayList<BaseTreeNodeService>();
		MyQuery mq = new MyQueryImpl();
		mq.setHql("from SysUser a where a.deleteMark = '1' order by a.sysDepartment.id ");
		Object [] os = dao.findEntity(mq);
		BaseTreeNodeService bt = null;
		ViewTreeNode vtn = null;
		SysUser user = null;
		for(int i=0; i<os.length; i++)
		{
			bt = new BaseTreeNodeServiceImpl();
			vtn = new ViewTreeNode();
			user = (SysUser)os[i];
			bt.setId(user.getUserId());
			bt.setParentId(user.getSysDepartment().getId());
			bt.setNickName(null);
			bt.setType("batchRightUser"+user.getUserId());
			bt.setLabel(user.getUserName());
			bt.setRemark(user.getRemark());
			vtn.setAction(action);
			vtn.setTarget(target);
			vtn.setIcon("expanded=folderOpen.gif;closed=folderClose.gif;leaf=renicon.gif;");
			bt.setTreeNodeExtendedService(vtn);
			users.add(bt);
		}
		return users;
	}
	
}
