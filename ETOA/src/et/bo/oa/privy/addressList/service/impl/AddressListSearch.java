/**
 * @(#)EmailService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.privy.addressList.service.impl;

import java.util.Date;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.AddresslistInfo;
import et.po.AficheInfo;
import et.po.EmployeeInfo;
import et.po.NewsArticle;
import et.po.SysUser;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
/**
 * @describe  通讯录查询
 * @author
 * @version 1.0, //
 * @see
 * @see
 */
public class AddressListSearch extends MyQueryImpl {
    /**
	 * @describe  通讯录列表
	 * @param
	 * @return    MyQuery mq
	 * 
	 */
    public MyQuery searchAddressListInfo(IBaseDTO dto,PageInfo pi,SysUser sys){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(AddresslistInfo.class);
        dc.add(Expression.eq("sysUser", sys));
        dc.add(Expression.eq("sign", dto.get("sign").toString()));
        if(!dto.get("sort").toString().equals("")){
        	dc.add(Expression.eq("sort", dto.get("sort").toString()));
        }
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
    
    /**
	 * @describe  公司通讯录列表
	 * @param
	 * @return    MyQuery mq
	 * 
	 */
    public MyQuery searchCompanyAddressListInfo(IBaseDTO dto,PageInfo pi){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(EmployeeInfo.class);
//        dc.add(Expression.eq("sign", dto.get("sign").toString()));
        if(!dto.get("name").toString().equals(""))
            dc.add(Expression.like("name", "%"+dto.get("name").toString()+"%"));
        if(!(dto.get("department").toString().equals("")||(dto.get("department")==null)))
        	dc.add(Expression.eq("department", dto.get("department").toString()));
        if(!dto.get("mobile").toString().equals(""))
        	dc.add(Expression.like("mobile", "%"+dto.get("mobile").toString()+"%"));
        if(!dto.get("companyPhone").toString().equals(""))
        	dc.add(Expression.like("companyPhone", "%"+dto.get("companyPhone").toString()+"%"));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }
}
