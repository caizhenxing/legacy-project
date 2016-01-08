/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.assissant.asset.service.impl;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.AssetsOper;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class AssetOperHelp {

	public MyQuery listMQ(IBaseDTO dto, PageInfo pi)
	{
		MyQuery mq =new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(AssetsOper.class);
		if(null !=dto.get("operType") && !"".equals(dto.get("operType").toString()))
			dc.add(Expression.eq("operType",(String)dto.get("operType")));
		if(null !=dto.get("assetsOper") && !"".equals(dto.get("assetsOper").toString()))
			dc.add(Expression.eq("operId",(String)dto.get("assetsOper")));
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
	    mq.setFetch(pi.getPageSize());
		return mq;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO 需要写出方法的具体实现

	}

}
