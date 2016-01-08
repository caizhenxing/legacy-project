/**
 * className TreePropertyService 
 * 
 * �������� 2008-1-4
 * 
 * @version
 * 
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
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
 * Ҷ�ӽڵ��ֵ����Ȩ������
 *
 * @version 	jan 01 2008 
 * @author ����Ȩ ������
 */
public class LeafRightSearch {
	/**
	 * ���ݽڵ�id��Ҷ�ӽڵ�Ȩ��
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
	 * ���ݽ�ɫid �� �ڵ�id ��Ȩ��2
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
