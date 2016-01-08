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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.SysLeafRight;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * 叶子节点字典表授权帮助类
 *
 * @version 	jan 01 2008 
 * @author 王文权 荆玉琢
 */
public class LeafRightSearch {
	/**
	 * 根据节点id找叶子节点权限
	 * @param nodeId
	 * @return
	 */
	public MyQuery searchLeafRightByNodeId(String nodeId){
	        MyQuery mq=new MyQueryImpl();
	        
	        DetachedCriteria dc=DetachedCriteria.forClass(SysLeafRight.class);
	        dc.add(Expression.eq("baseTree.id",nodeId));
	        mq.setDetachedCriteria(dc);
	        return mq;
	    }
	/**
	 * 根据角色id 及 节点id 找权限2
	 * @param roleId
	 * @param treeId
	 * @return
	 */
	public MyQuery searchRightRole(String roleId,String treeId){
        MyQuery mq=new MyQueryImpl();
        String hql = "from SysRightRole a where a.sysRole.id='"+roleId+"' and a.sysLeafRight.baseTree.id = '"+treeId+"'";
        
        mq.setHql(hql);
        return mq;
    }
	
	public MyQuery treeList(IBaseDTO dto, PageInfo pi,String nodeId){
        MyQuery mq=new MyQueryImpl();
        
        DetachedCriteria dc=DetachedCriteria.forClass(SysLeafRight.class);
        
        dc.add(Expression.eq("baseTree.id",nodeId));
        mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
        return mq;
    }
}
