/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.assissant.asset.service.impl;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.AssetsInfo;
import et.po.AssetsOper;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class AssetHelp {

	public MyQuery deficiency(String deficiencyAsset)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(AssetsInfo.class);
		AssetsOper ao =new AssetsOper();
		ao.setOperId(deficiencyAsset);
		dc.add(Expression.eq("assetsOper",ao));
		MyQuery mq =new MyQueryImpl();
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	public MyQuery listMQ(IBaseDTO dto, PageInfo pi)
	{
		MyQuery mq =new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(AssetsInfo.class);
		//TODO
		if(null !=dto.get("assetsId") && !"".equals(dto.get("assetsId").toString()))
			dc.add(Expression.like("assetsId","%"+(String)dto.get("assetsId")+"%"));
		if(null !=dto.get("assetsName") && !"".equals(dto.get("assetsName").toString()))
			dc.add(Expression.like("assetsName","%"+(String)dto.get("assetsName")+"%"));
		if(null !=dto.get("assetsType") && !"".equals(dto.get("assetsType").toString()))
			dc.add(Expression.eq("assetsType",(String)dto.get("assetsType")));
		if(null !=dto.get("assetsOper") && !"".equals(dto.get("assetsOper").toString()))
		{
			AssetsOper ao =new AssetsOper();
			ao.setOperId((String)dto.get("assetsOper"));
			dc.add(Expression.eq("assetsOper",ao));
		}
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
