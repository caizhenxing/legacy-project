/*
 * @(#)FlowHelp.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.flow.service.impl;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>客户管理——查询</p>
 * 
 * @version 2008-03-19
 * @author nie
 */
public class FlowHelp extends MyQueryImpl{
	
	public MyQuery flowQuery(IBaseDTO dto, PageInfo pi){
		
		MyQuery mq = new MyQueryImpl();

		String begin1 = (String)dto.get("begin1");
		String end1 = (String)dto.get("end1");
		String begin2 = (String)dto.get("begin2");
		String end2 = (String)dto.get("end2");
		String submit_id = (String)dto.get("submit_id");
		String submit_id_end = (String)dto.get("submit_id_end");
		String type = (String)dto.get("type");
		String state = (String)dto.get("state");
		String is_read = (String)dto.get("is_read");
		
		String hql = "from OperFlow where type is not null";
		
		if(!begin1.equals("")){
			hql += " and submitTime >= " + begin1;
		}
		if(!end1.equals("")){
			hql += " and submitTime <= " + end1;
		}
		if(!begin2.equals("")){
			hql += " and submitTimeEnd >= " + begin2;
		}
		if(!end2.equals("")){
			hql += " and submitTimeEnd <= " + end2;
		}
		if(!submit_id.equals("")){
			hql += " and submitId like '%" + submit_id + "%'";
		}
		if(!submit_id_end.equals("")){
			hql += " and submitIdEnd like '%" + submit_id_end + "%'";
		}
		if(!type.equals("")){
			if(type.indexOf(",") != -1){
				hql += " and type in ( ";
				String[] types = type.split(",");
				for(int i = 0; i < types.length; i++){
					hql += "'" + types[i] + "',";
				}
				hql = hql.substring(0, hql.length()-1);
				hql += " )";
			}else{
				hql += " and type = '" + type + "'";
			}
		}
		if(!state.equals("")){
			hql += " and state = '" + state + "'";
		}
		if(!is_read.equals("")){
			hql += " and is_read = '" + is_read + "'";
		}
		hql += " order by submitTimeEnd desc";
		mq.setHql(hql);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		
		return mq;
	}

}
