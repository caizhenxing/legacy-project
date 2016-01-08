package et.bo.oa.checkwork.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.po.CheckworkAbsence;
import et.po.CheckworkInfo;
import et.po.EmployeeInfo;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>
 * 考勤管理 MyQuery
 * </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-23
 * 
 */
public class SreachService {

	/**
	 * <p>
	 * 请假,外出,出差记录查询
	 * </p>
	 */
	public MyQuery selectAbsenseListQuery(IBaseDTO infoDTO, PageInfo pageInfo) {
		DetachedCriteria dc = DetachedCriteria.forClass(CheckworkAbsence.class);
		if (!("select".equals(infoDTO.get("absenceType").toString()))) {
			dc.add(Restrictions.eq("absenceType", infoDTO.get("absenceType")
					.toString()));
		}
		if (!("".equals(infoDTO.get("absenceUser").toString()))) {
			dc.add(Restrictions.eq("employeeId", infoDTO.get("absenceUser")
					.toString()));
		}
		if (!("select".equals(infoDTO.get("department").toString()))) {
			dc.add(Restrictions.eq("employeeDepart", infoDTO.get("department")
					.toString()));
		}
		if (!((infoDTO.get("startDate") == null) || "".equals(infoDTO.get(
				"startDate").toString()))
				&& !((infoDTO.get("endDate") == null) || "".equals(infoDTO.get(
						"endDate").toString()))) {

			dc.add(Restrictions.between("beginTime", TimeUtil.getTimeByStr(
					infoDTO.get("startDate").toString(), "yyyy-MM-dd"),
					TimeUtil.getTimeByStr(infoDTO.get("endDate").toString(),
							"yyyy-MM-dd")));
		}
		dc.addOrder(Order.desc("signDate"));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);

		myQuery.setFirst(pageInfo.getBegin());
		myQuery.setFetch(pageInfo.getPageSize());
		return myQuery;
	}

	/**
	 * <p>
	 * 补签查询记录集
	 * </p>
	 */
	public MyQuery seletCheckListQuery(IBaseDTO infoDTO, PageInfo pageInfo) {
		DetachedCriteria dc = DetachedCriteria.forClass(CheckworkInfo.class);
		// DetachedCriteria dc =
		// DetachedCriteria.forClass(CheckworkAbsence.class);
		// dc.add(Restrictions.eq("absenceType", infoDTO.get("absenceType")
		// .toString()));
		if (!(infoDTO.get("startT") == null || "".equals(infoDTO.get("startT")
				.toString()))
				&& !(infoDTO.get("endT") == null || "".equals(infoDTO.get(
						"endT").toString()))) {
			dc.add(Restrictions
					.between("checkDate", TimeUtil.getTimeByStr(infoDTO.get(
							"startT").toString(), "yyyy-MM-dd"), TimeUtil
							.getTimeByStr(infoDTO.get("endT").toString(),
									"yyyy-MM-dd")));
		}
		if (infoDTO.get("repairUser").toString().equals("y")) {
			if (!("y".equals(infoDTO.get("department").toString()))) {
				dc.add(Restrictions.eq("departId", infoDTO.get("department")
						.toString()));
			}
		} else {
			dc.add(Restrictions.eq("employeeId", infoDTO.get("repairUser")
					.toString()));
		}
		// if (!("y".equals(infoDTO.get("department").toString()))) {
		//
		// dc.add(Restrictions.eq("departId", infoDTO.get("department")
		// .toString()));
		//
		// }
		// dc.add(Restrictions.eq("employeeId", "yy"));
		// dc.addOrder(Order.desc("signDate"));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		myQuery.setFirst(pageInfo.getBegin());
		myQuery.setFetch(pageInfo.getPageSize());

		return myQuery;
	}

	/**
	 * <p>
	 * 查询人员姓名列表
	 * </p>
	 */
	public MyQuery getNameListQuery() {
		DetachedCriteria dc = DetachedCriteria.forClass(EmployeeInfo.class);
		dc.add(Restrictions.eq("delSign", "1"));
		dc.add(Restrictions.eq("isLeave", "1"));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		return myQuery;
	}

	/**
	 * <p>
	 * 请假,外出,出差 次数查询
	 * </p>
	 */
	public MyQuery getNumByEmployeeId(IBaseDTO infoDTO, String employeeId,
			String sign) {
		DetachedCriteria dc = DetachedCriteria.forClass(CheckworkAbsence.class);
		if (!(infoDTO.get("startT") == null || "".equals(infoDTO.get("startT")
				.toString()))
				&& !(infoDTO.get("endT") == null || "".equals(infoDTO.get(
						"endT").toString()))) {
			dc.add(Restrictions
					.between("beginTime", TimeUtil.getTimeByStr(infoDTO.get(
							"startT").toString(), "yyyy-MM-dd"), TimeUtil
							.getTimeByStr(infoDTO.get("endT").toString(),
									"yyyy-MM-dd")));
		}
		dc.add(Restrictions.eq("employeeId", employeeId));
		if (sign.equals("1")) {// 请假
			dc.add(Restrictions.eq("absenceType", sign));
		} else if (sign.equals("2")) {// 外出
			dc.add(Restrictions.eq("absenceType", sign));
		} else {// 出差
			dc.add(Restrictions.eq("absenceType", sign));
		}
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		return myQuery;
	}

	/**
	 * <p>
	 * 迟到,早退查询
	 * </p>
	 */
	public MyQuery seletLaterOrEarlyCheckListQuery(IBaseDTO infoDTO) {
		DetachedCriteria dc = DetachedCriteria.forClass(CheckworkInfo.class);
		// DetachedCriteria dc =
		// DetachedCriteria.forClass(CheckworkAbsence.class);
		// dc.add(Restrictions.eq("absenceType", infoDTO.get("absenceType")
		// .toString()));
		dc.add(Restrictions.ne("signMark", "Y"));
		if (!(infoDTO.get("startT") == null || "".equals(infoDTO.get("startT")
				.toString()))
				&& !(infoDTO.get("endT") == null || "".equals(infoDTO.get(
						"endT").toString()))) {
			dc.add(Restrictions
					.between("checkDate", TimeUtil.getTimeByStr(infoDTO.get(
							"startT").toString(), "yyyy-MM-dd"), TimeUtil
							.getTimeByStr(infoDTO.get("endT").toString(),
									"yyyy-MM-dd")));
		}
		dc.add(Restrictions.eq("employeeId", infoDTO.get("repairUser")));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		return myQuery;
	}
	/**
	 * <p>
	 * 补签查询
	 * </p>
	 */
	public MyQuery seletRepairCheckListQuery(IBaseDTO infoDTO){
		DetachedCriteria dc = DetachedCriteria.forClass(CheckworkInfo.class);
		dc.add(Restrictions.eq("signMark", "Y"));
		if (!(infoDTO.get("startT") == null || "".equals(infoDTO.get("startT")
				.toString()))
				&& !(infoDTO.get("endT") == null || "".equals(infoDTO.get(
						"endT").toString()))) {
			dc.add(Restrictions
					.between("repairDate", TimeUtil.getTimeByStr(infoDTO.get(
							"startT").toString(), "yyyy-MM-dd"), TimeUtil
							.getTimeByStr(infoDTO.get("endT").toString(),
									"yyyy-MM-dd")));
		}
		dc.add(Restrictions.eq("employeeId", infoDTO.get("repairUser")));
		MyQuery myQuery = new MyQueryImpl();
		myQuery.setDetachedCriteria(dc);
		return myQuery;
	}
}
