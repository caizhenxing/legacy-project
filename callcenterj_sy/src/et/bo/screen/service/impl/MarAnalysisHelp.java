package et.bo.screen.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.OperMarketAnalysis;
import et.po.OperPriceinfo;
import et.po.ScreenOperSadinfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class MarAnalysisHelp {
	
	/**
	 * 查询金农市场分析信息
	 * @param dto
	 * @param pi
	 * @return
	 */
	public MyQuery marketInfoQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
//		DetachedCriteria dc = DetachedCriteria.forClass(OperMarketAnalysis.class);
//		
//		if(!"".equals(dto.get("typeTitle"))) {
//			dc.add(Restrictions.eq("typeTitle", dto.get("typeTitle").toString().trim()));
//		}
//		if(!"".equals(dto.get("subTitle"))) {
//			dc.add(Restrictions.eq("subTitle", dto.get("subTitle").toString().trim()));
//		}
//		if(!"".equals(dto.get("analysisPerson"))) {
//			dc.add(Restrictions.eq("analysisPerson", (String)dto.get("analysisPerson")));
//		}
		
		StringBuffer hql = new StringBuffer();
		hql.append("select oma from OperMarketAnalysis oma where oma.id = oma.id");
		if(!"".equals(dto.get("typeTitle"))) {
			hql.append(" and oma.typeTitle like '%"+ dto.get("typeTitle").toString() +"%'");
		}
		if(!"".equals(dto.get("subTitle"))) {
			hql.append(" and oma.subTitle like '%"+ dto.get("subTitle").toString() +"%'");
		}
		if(!"".equals(dto.get("analysisPerson"))) {
			hql.append(" and oma.analysisPerson like '%"+ dto.get("analysisPerson").toString() +"%'");
		}
		hql.append(" order by addTime desc");
		
//		mq.setDetachedCriteria(dc);
		mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	/**
	 * 查询金农市场分析信息 按时间倒序 显示头几条
	 * @param dto
	 * @param pi
	 * @param size
	 * @return
	 */
	public MyQuery marketInfoQuery( int size) {
		MyQuery mq = new MyQueryImpl();
		
		StringBuffer hql = new StringBuffer();
		hql.append("select oma from OperMarketAnalysisScreen oma where oma.id = oma.id");
		hql.append(" order by addTime desc");
		
//		mq.setDetachedCriteria(dc);
		mq.setHql(hql.toString());
		mq.setFirst(0);
		if(size<1)
		{
			size = 1;
		}
		mq.setFetch(size);
		return mq;
	}
	public MyQuery marketInfoQuery2() {
		MyQuery mq = new MyQueryImpl();
		
		String hql = "from OperMarketAnalysis order by addTime desc";
		mq.setHql(hql);
		mq.setFirst(0);
		mq.setFetch(10);
		return mq;
	}
	
	public MyQuery customMadeQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		StringBuffer hql = new StringBuffer();
		
		hql.append("select ocm from OperCustomMade ocm where ocm.id = ocm.id");
		if(!"".equals(dto.get("serviceType"))) {
			hql.append(" and ocm.serviceType like '%"+ dto.get("serviceType").toString() +"%'");
		}
		if(!"".equals(dto.get("columnName"))) {
			hql.append(" and ocm.columnName like '%"+ dto.get("columnName").toString()+"%'");
		}
		if(!"".equals(dto.get("priceInfo"))) {
			hql.append(" and ocm.priceInfo like '%"+ dto.get("priceInfo").toString() +"%'");
		}
		
		mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	
	public MyQuery customMadeQuery2() {
		MyQuery mq = new MyQueryImpl();
		String hql = "from OperCustomMade order by addTime desc";
		
		mq.setHql(hql);
		mq.setFirst(0);
		mq.setFetch(20);
		
		return mq;
	}
	
	// ********************* add by chengang 2009-03-12 ***************
	
	/**
	 * 查询金农市场分析信息
	 * @param dto
	 * @param pi
	 * @return
	 */
	public MyQuery marketAnalysisInfoQuery(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
//		DetachedCriteria dc = DetachedCriteria.forClass(OperMarketAnalysis.class);
//		
//		if(!"".equals(dto.get("typeTitle"))) {
//			dc.add(Restrictions.eq("typeTitle", dto.get("typeTitle").toString().trim()));
//		}
//		if(!"".equals(dto.get("subTitle"))) {
//			dc.add(Restrictions.eq("subTitle", dto.get("subTitle").toString().trim()));
//		}
//		if(!"".equals(dto.get("analysisPerson"))) {
//			dc.add(Restrictions.eq("analysisPerson", (String)dto.get("analysisPerson")));
//		}
		
		StringBuffer hql = new StringBuffer();
		hql.append("select oma from OperMarketAnalysisScreen oma where oma.id = oma.id");
		if(!"".equals(dto.get("subTitle"))) {
			hql.append(" and oma.title like '%"+ dto.get("subTitle").toString() +"%'");
		}
		if(!"".equals(dto.get("analysisPerson"))) {
			hql.append(" and oma.analysiser like '%"+ dto.get("analysisPerson").toString() +"%'");
		}
		if(!"".equals(dto.get("analysisContent"))) {
			hql.append(" and oma.analysisContent like '%"+ dto.get("analysisContent").toString() +"%'");
		}
		if(!"".equals(dto.get("analysisType"))) {
			hql.append(" and oma.analysisType = '"+ dto.get("analysisType").toString() +"'");
		}
		hql.append(" order by addTime desc");
		
//		mq.setDetachedCriteria(dc);
		mq.setHql(hql.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
}