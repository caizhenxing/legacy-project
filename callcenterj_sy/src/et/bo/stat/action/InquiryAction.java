/*
 * @(#)InquiryService.java	 2008-04-14
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.stat.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.jfree.chart.JFreeChart;
import org.springframework.context.ApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;

import et.bo.stat.service.InquiryService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author nie
 */
public class InquiryAction extends BaseAction {
	private InquiryService service;

	public InquiryService getService() {
		return service;
	}

	public void setService(InquiryService service) {
		this.service = service;
	}

	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * 执行该方法，显示座席电话量统计的条件输入界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		ApplicationContext ac = (ApplicationContext) getServlet()
				.getServletContext().getAttribute(
						ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
		BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from oper_inquiryinfo";
			rs = stmt.executeQuery(sql);
				while(rs.next()) {
					String id = rs.getString("id");
					String topic = rs.getString("topic");
					list.add(new LabelValueBean(topic, id));
				}

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		request.setAttribute("list", list);
		
		return mapping.findForward("query");
	}

	/**
	 * 接受统计条件，执行相应的的统计方法，将统计结果回显到display页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String chartType = (String) dto.get("chartType");
		if (chartType.equals("")) {
			List<DynaBeanDTO> list=service.query(dto);
			request.setAttribute("list", list);
			return mapping.findForward("report");
		} else {
			JFreeChart chart = service.statistic(dto);
			request.setAttribute("chart", chart);
			return mapping.findForward("chartDisplay");
		}
	}
}
