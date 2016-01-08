/**
 * 	@(#)StationSearch.java   Sep 4, 2006 9:33:38 AM
 *	 。 
 *	 
 */
package et.bo.sys.station.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.po.SysStationInfo;
import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author zhang
 * @version Sep 4, 2006
 * @see
 */
public class StationSearch extends MyQueryImpl {

	/**
	 * <p>
	 * 根据部门岗位信息查询
	 * </p>
	 * 
	 * @param info:根据部门岗位信息查询
	 * 
	 * @return:MyQuery mq
	 * 
	 * @throws
	 */

	public MyQuery searchStationInfo(IBaseDTO dto, PageInfo pi) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria
				.forClass(SysStationInfo.class);
		String department = (String) dto.get("departmentid");
		if (department != null && !department.trim().equals("")) {
			if (department.equals("1")) {

			} else {
				dc.add(Expression.eq("departmentId", department));
			}
		}
		String depName = (String) dto.get("deppersonname");
		if (depName != null && !depName.trim().equals("")) {
			dc.add(Expression.eq("depPersonName", "%" + depName + "%"));
		}
		String depLevel = (String) dto.get("deplevel");
		if (depLevel != null && !depLevel.trim().equals("")) {
			dc.add(Expression.like("depLevel", "%" + depLevel + "%"));
		}
		mq.setDetachedCriteria(dc);
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}

	/**
	 * <p>
	 * 根据部门岗位信息查询
	 * </p>
	 * 
	 * @param info:根据部门岗位信息查询
	 * 
	 * @return:MyQuery mq
	 * 
	 * @throws
	 */

	public MyQuery searchStationInfo(String departmentid) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria
				.forClass(SysStationInfo.class);
		String department = departmentid;
		if (department != null && !department.trim().equals("")) {
			dc.add(Expression.eq("departmentId", department));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}

}
