/**
 * 	@(#)OperatorListenSearch.java   Oct 8, 2006 4:15:25 PM
 *	 �� 
 *	 
 */
package et.bo.pcc.operatorlisten.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.PoliceCallin;
import et.po.SysGroup;
import et.po.SysUser;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Oct 8, 2006
 * @see
 */
public class OperatorListenSearch extends MyQueryImpl{
	
    /**
	 * @describe ��ѯ��ϯ���
	 * @param  ����  InemailInfo inemailInfo
	 * @return ����  
	 * 
	 */
    public MyQuery searchOperatorListen(IBaseDTO dto,PageInfo pi) {
        MyQuery mq = new MyQueryImpl();
        DetachedCriteria dc = DetachedCriteria.forClass(PoliceCallin.class);
        String usernum = (String)dto.get("operator");
        if (usernum!=null&&!usernum.trim().equals("")) {
        	dc.add(Expression.eq("operator", usernum));
		}
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    /**
	 * @describe ��ϯԱ��Ϣ
	 * @param  ����  InemailInfo inemailInfo
	 * @return ����  
	 * 
	 */
    public MyQuery searchUserInfo(SysGroup sg) {
        MyQuery mq = new MyQueryImpl();
        DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
        dc.add(Expression.eq("sysGroup", sg));
        mq.setDetachedCriteria(dc);
        return mq;
    }
    
}
