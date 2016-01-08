/**
 * @(#)EmailService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.oa.privy.addressListSort.service.impl;

import java.util.Date;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import et.po.AddresslistInfo;
import et.po.AddresslistsortInfo;
import et.po.AficheInfo;
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
public class AddressListSortSearch extends MyQueryImpl {
    /**
	 * @describe  通讯录列表
	 * @param
	 * @return
	 * 
	 */
    public MyQuery searchAddressListSortInfo(IBaseDTO dto,PageInfo pi,SysUser sys){
        MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(AddresslistsortInfo.class);
        if(dto.get("sortMark").toString().equals("0")){
        	dc.add(Expression.eq("sortMark", dto.get("sortMark").toString()));
        	mq.setDetachedCriteria(dc);
            mq.setFirst(pi.getBegin());
            mq.setFetch(pi.getPageSize());
            return mq;
        }
        dc.add(Expression.eq("sysUser", sys));
        dc.add(Expression.eq("sortMark", dto.get("sortMark").toString()));
        mq.setDetachedCriteria(dc);
        mq.setFirst(pi.getBegin());
        mq.setFetch(pi.getPageSize());
        return mq;
    }    
/**
 * @describe 查询是否同名
 * @param
 * @return   MyQuery mq
 * 
 */
    public MyQuery searchIsHaveSameNameInfo(IBaseDTO dto,SysUser sys){
    	MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(AddresslistsortInfo.class);
        if(dto.get("sortMark").toString().equals("0")){
        	dc.add(Expression.eq("sortName",dto.get("sortName").toString()));
        	dc.add(Expression.eq("sortMark", dto.get("sortMark").toString()));   
        	mq.setDetachedCriteria(dc);
            return mq;
        }
        dc.add(Expression.eq("sysUser", sys));
        dc.add(Expression.eq("sortMark", dto.get("sortMark").toString()));
        dc.add(Expression.eq("sortName",dto.get("sortName").toString()));
        mq.setDetachedCriteria(dc);
        return mq;
    }   
/**
 * @describe 取得LabelValueBean List结果
 * @param
 * @return   MyQuery mq
 * 
 */
    public MyQuery searchLabelList(SysUser sys, String sign){
    	MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(AddresslistsortInfo.class);
        if(sign.equals("0")){
        	dc.add(Expression.eq("sortMark", sign));
            mq.setDetachedCriteria(dc);
            return mq;
        }
        dc.add(Expression.eq("sysUser", sys));
        dc.add(Expression.eq("sortMark", sign));
        mq.setDetachedCriteria(dc);
        return mq;
    }
    /**
	 * @describe 查看所要删除的类别是否通讯录里有值
	 * @param
	 * @return   MyQuery mq
	 * 
	 */
    public MyQuery searchAddressListIsHavaSort(String id){
    	MyQuery mq=new MyQueryImpl();
        DetachedCriteria dc=DetachedCriteria.forClass(AddresslistInfo.class);
        dc.add(Expression.eq("sort", id));
        mq.setDetachedCriteria(dc);
        return mq;
    }
 
}
