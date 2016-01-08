package excellence.framework.base.query.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.query.MyQuery;

/**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public class MyQueryImpl implements MyQuery {

	private List parasL = null;

	private Map parasM = null;

	private IBaseDTO dto = null;

	// 看是否有排序功能
	private Order order = null;

	private int first = 0;

	private int fetch = 0;

	private Class type = null;

	private String identifer = null;

	private DetachedCriteria detachedCriteria = null;

	private String hql = null;

	private String sql = null;

	private String asc = null;

	private String desc = null;

	private PageInfo pageInfo = null;

	private static Log log = LogFactory.getLog(DynaActionFormDTO.class);

	public String getIdentifer() {
		log.info(identifer);
		return identifer;
	}

	public void setIdentifer(String identifer) {
		log.info(identifer);
		this.identifer = identifer;
	}

	public IBaseDTO getDto() {
		// TODO Auto-generated method stub
		return dto;
	}

	public void setDto(IBaseDTO dto) {
		// TODO Auto-generated method stub
		this.dto = dto;
	}

	public int getFirst() {
		// TODO Auto-generated method stub
		log.info(first);
		return first;
	}

	public void setFirst(int first) {
		// TODO Auto-generated method stub
		log.info(first);
		this.first = first;
	}

	public int getFetch() {
		// TODO Auto-generated method stub
		log.info(fetch);
		return fetch;
	}

	public void setFetch(int fetch) {
		// TODO Auto-generated method stub
		log.info(fetch);
		this.fetch = fetch;
	}

	public DetachedCriteria getDetachedCriteria() {
		// TODO Auto-generated method stub
		return this.detachedCriteria;
	}

	public String getHql() {
		// TODO Auto-generated method stub
		return this.hql;
	}

	public String getSql() {
		// TODO Auto-generated method stub
		return this.sql;
	}

	public Class getType() {
		// TODO Auto-generated method stub
		return type;
	}

	public void setType(Class type) {
		// TODO Auto-generated method stub
		this.type = type;
	}

	public void setDetachedCriteria(DetachedCriteria dc) {
		// TODO Auto-generated method stub
		this.detachedCriteria = dc;
	}

	public void setHql(String hql) {
		// TODO Auto-generated method stub
		this.hql = hql;
	}

	public void setSql(String sql) {
		// TODO Auto-generated method stub
		this.sql = sql;
	}

	public void setParameter(int idx, Object para) {
		// TODO Auto-generated method stub
		if (parasL == null)
			parasL = new ArrayList();
		parasL.add(idx, para);
	}

	public void setParameter(String pname, Object para) {
		// TODO Auto-generated method stub
		if (parasM == null)
			parasM = new HashMap();
		parasM.put(pname, para);
	}

	public List getParameterL() {
		// TODO Auto-generated method stub
		return this.parasL;
	}

	public Map getParameterM() {
		// TODO Auto-generated method stub
		return this.parasM;
	}

	public String getAsc() {
		// TODO Auto-generated method stub
		return this.asc;
	}

	public String getDesc() {
		// TODO Auto-generated method stub
		return this.desc;
	}

	public void setAsc(String asc) {
		// TODO Auto-generated method stub
		this.asc = asc;
	}

	public void setDesc(String desc) {
		// TODO Auto-generated method stub
		this.desc = desc;
	}

	public void setPageInfo(PageInfo pi) {
		// TODO Auto-generated method stub
		this.fetch = pi.getPageSize();
		this.first = pi.getBegin();
		this.asc = pi.getFieldAsc();
		this.desc = pi.getFieldDesc();
	}

	public void setOrder(Order order) {
		// TODO Auto-generated method stub
		this.order = order;
	}

	public Order getOrder() {
		// TODO Auto-generated method stub
		return this.order;
	}

}
