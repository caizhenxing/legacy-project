package et.bo.oa.message.addrList.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.po.EmployeeInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;


/**
 * <p> 通讯录MyQuery实现 </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-22
 * 
 */
public class SreachService {

	/**
	 * <p> 获得通讯录集合 MyQuery </p>
	 * 
	 * @param dto
	 * @param pageInfo
	 * @return
	 */
	public MyQuery getAddrListMyQuery(IBaseDTO dto, PageInfo pageInfo) {
		DetachedCriteria dc = DetachedCriteria.forClass(EmployeeInfo.class);
		String depart = dto.get("departId").toString().trim();
		if (!(dto.get("departId") == null || "".equals(dto.get("departId")
				.toString()))) {
			dc.add(Restrictions
					.eq("department", depart));
			
		}

		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		myQuery.setFirst(pageInfo.getBegin());
		myQuery.setFetch(pageInfo.getPageSize());

		return myQuery;
	}
}
