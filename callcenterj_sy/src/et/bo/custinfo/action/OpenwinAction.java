/*
 * @(#)OpenwinAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.custinfo.action;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;

import et.bo.custinfo.service.CustinfoService;
import et.bo.priceinfo.service.PriceinfoService;
import et.bo.question.service.QuestionService;
import et.bo.sad.service.SadService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>
 * 来电弹出窗口
 * </p>
 * 
 * @version 2008-03-28
 * @author nie
 */

public class OpenwinAction extends BaseAction {

	static Logger log = Logger.getLogger(OpenwinAction.class.getName());

	private CustinfoService custinfoService = null;

	private QuestionService questionService = null;

	private SadService sadService = null;

	private PriceinfoService priceinfoService = null;

	private KeyService ks = null;

	private ClassTreeService cts = null;

	BaseDAO dao = null;

	// 注入service
	public CustinfoService getCustinfoService() {
		return custinfoService;
	}

	public void setCustinfoService(CustinfoService custinfoService) {
		this.custinfoService = custinfoService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public SadService getSadService() {
		return sadService;
	}

	public void setSadService(SadService sadService) {
		this.sadService = sadService;
	}

	public PriceinfoService getPriceinfoService() {
		return priceinfoService;
	}

	public void setPriceinfoService(PriceinfoService priceinfoService) {
		this.priceinfoService = priceinfoService;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * 根据URL参数执行 toOpenwinLoad 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toOpenwinLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String tel = request.getParameter("tel");

		String date = new java.text.SimpleDateFormat("yyyy-MM-dd")
				.format(new java.util.Date());
		request.setAttribute("date", date);

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);

		// 专家类别
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);

		// 通讯录中人员及列表的信息
		List telNoteList = cts.getLabelVaList("telNoteType");
		request.setAttribute("telNoteList", telNoteList);

		if (tel != null && !tel.equals("")) {

			IBaseDTO dto = custinfoService.getopenwinInfo(tel);
			request.getSession().setAttribute("custinfoDTO", dto);
			request.setAttribute(map.getName(), dto);
			request.setAttribute("tel", tel);

			return map.findForward("load");
		}
		request.setAttribute("tel", "");

		// zhangfeng add
		request.setAttribute("flag", "");

		return map.findForward("load");
	}

	/**
	 * 根据URL参数执行 toOpenwinLoad 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toQuestionList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String tel = request.getParameter("tel");
		
		if (tel != null && !tel.equals("")) {

			custinfoService.getopenwinInfo(tel);
			List l = custinfoService.getQuestionList();
			request.setAttribute("list", l);
		}

		return map.findForward("questionList");

	}

	/**
	 * 根据URL参数执行 toSearch 方法，返回要forward页面。
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toSearch(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String v = "";
		String searchV = request.getParameter("searchV");
		try {
			v = new String(searchV.getBytes("iso8859-1"), "GBK");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		if (v != null && !v.equals("")) {

			List list = new ArrayList();

			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			ApplicationContext ac = (ApplicationContext) getServlet()
					.getServletContext().getAttribute(
							ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
			BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");

			try {
				conn = bds.getConnection();
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				String sql = "select * from oper_caseinfo where case_content like '%"
						+ v + "%' order by addtime desc";
				rs = stmt.executeQuery(sql);
				for (int i = 0; i < 20; i++) {
					if (rs.next()) {
						IBaseDTO dto = new DynaBeanDTO();
						dto.set("case_id", rs.getString("case_id"));
						dto.set("type", rs.getString("dict_case_type"));

						String case_content = rs.getString("case_content");
						String case_reply = rs.getString("case_reply");
						if (case_content != null && case_reply != null) {
							String compose = case_content + " " + case_reply;
							if (compose.length() > 52) {
								compose = compose.substring(0, 52) + " …";
							}
							dto.set("compose", compose);
							list.add(dto);
						}
					}
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
		}

		return map.findForward("search");

	}

	/**
	 * 根据URL参数执行 toOpenwinOper 方法，返回要forward页面。 并且根据URL参数中type的值来判断所执行的操作:CRUD
	 * 
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 返回列表页面
	 */
	public ActionForward toOpenwinOper(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List sexList = cts.getLabelVaList("custSex");
		request.setAttribute("sexList", sexList);

		List typeList = cts.getLabelVaList("custType");
		request.setAttribute("typeList", typeList);

		// 专家类别
		List expertList = cts.getLabelVaList("zhuanjialeibie");
		request.setAttribute("expertList", expertList);
		
		// 通讯录中人员及列表的信息
		List telNoteList = cts.getLabelVaList("telNoteType");
		request.setAttribute("telNoteList", telNoteList);

		DynaActionFormDTO dto = (DynaActionFormDTO) form;

		java.util.Map infoMap = (java.util.Map) request
				.getSession()
				.getAttribute(
						et.bo.sys.common.SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
		String userId = (String) infoMap.get("userId");
		String cust_id = dto.get("cust_id").toString();// 如果是新用户，则id为空

		if (cust_id == null || cust_id.equals("")) {
			dto.set("cust_rid", userId);// 传入受理工号
			custinfoService.addCustinfo(dto); // 添加新用户
			dto.set("cust_id", custinfoService.getCustId());
		} else {
			dto.set("cust_rid", userId);// 传入受理工号
			custinfoService.updateCustinfo(dto);
		}
		String tel = request.getParameter("tel");
		String ring_begintime = getRingBegintime(tel);
		if (ring_begintime == null || ring_begintime.equals("")) {
			ring_begintime = TimeUtil.getNowTimeSr();
		}

		String isout = request.getParameter("isout");
		// 首先记录到流水表里。以普通格式记录
		String questionId = questionService.addQuestionAndGetId(dto,
				ring_begintime, userId, isout, tel);
		dto.set("question_id", questionId);
		// 取出栏目类别
		String dict_question_type1 = dto.get("dict_question_type1").toString();
		if (dict_question_type1 == null)
			dict_question_type1 = "";

		// 判断是否为供求格式
		if (dict_question_type1.equals("供求发布")) {
			dto.set("remark", dto.get("remark2"));// 因为备注数据库字段名一样，所以表单用了一个remark2
			dto.set("sadRid", userId);
			sadService.addSad(dto);
		}

		// 判断是否为价格格式
		if (dict_question_type1.equals("价格报送")) {
			// 因为和别的表单冲突，所以要重新给这几个对象赋值为空
			dto.set("dictSadType", "");
			// dto.set("custName", "");
			// dto.set("custAddr", "");
			// dto.set("custTel", "");
			dto.set("productScale", "");
			dto.set("productPrice", "");
			dto.set("priceUnit", "");

			String dictProductType1[] = dto.getStrings("dict_product_type1");
			String dictProductType2[] = dto.getStrings("dict_product_type2");
			String productName[] = dto.getStrings("product_name");
			String dictPriceType[] = dto.getStrings("dict_price_type");
			String productPrice[] = dto.getStrings("product_price");
			String remark[] = dto.getStrings("remarkj");

			dto.set("priceExpert", dto.get("bill_num"));
			dto.set("priceRid", userId);
			dto.set("custName", dto.get("cust_name"));
			dto.set("custAddr", dto.get("cust_addr"));
			dto.set("custTel", tel);

			for (int i = 0; i < dictProductType1.length; i++) {
				if (productPrice[i] != null && !productPrice[i].equals("")) {

					dto.set("dictProductType1", dictProductType1[i]);
					dto.set("dictProductType2", dictProductType2[i]);
					dto.set("productName", productName[i]);
					dto.set("dictPriceType", dictPriceType[i]);
					dto.set("productPrice", productPrice[i]);
					dto.set("remark", remark[i]);

					priceinfoService.addOperPriceinfoSad(dto);
				}
			}
		}
		// 判断是否是调查格式
		if (dict_question_type1.equals("热线调查")) {
			String cust_name = dto.get("cust_name").toString();
			String inquiry_num = (String) request.getSession().getAttribute(
					"inquiry_num");
			executeUpdate("update oper_inquiry_result set actor = '"
					+ cust_name + "', question_id = '" + questionId
					+ "' where num = '" + inquiry_num + "'");
			request.getSession().removeAttribute("inquiry_num");
		}

		// 另存为XXX数据库
		String savedata = dto.get("savedata").toString();
		if (savedata == null)
			savedata = "";
		String dict_case_type = "";
		if (savedata.equals("普通案例库")) {
			dict_case_type = "putong";
		} else if (savedata.equals("焦点案例库")) {
			dict_case_type = "FocusCase";
		} else if (savedata.equals("会诊案例库")) {
			dict_case_type = "HZCase";
		} else if (savedata.equals("效果案例库")) {
			dict_case_type = "effectCase";
		}
		// 如果是案例库
		if (savedata.equals("普通案例库") || savedata.equals("焦点案例库")
				|| savedata.equals("会诊案例库") || savedata.equals("效果案例库")) {
			String case_id = ks.getNext("oper_caseinfo");
			String cust_name = filtrate(dto.get("cust_name").toString());
			String cust_addr = filtrate(dto.get("cust_addr").toString());
			String cust_tel = filtrate(request.getParameter("tel"));
			String expert_type = filtrate(dto.get("bill_num").toString());// 专家类别
			String case_expert = filtrate(dto.get("expert_name").toString());// 专家名
			String case_attr1 = filtrate(dto.get("dict_question_type2")
					.toString());
			String case_attr2 = filtrate(dto.get("dict_question_type3")
					.toString());
			String case_attr3 = filtrate(dto.get("dict_question_type4")
					.toString());
			String case_content = filtrate(dto.get("question_content")
					.toString());
			String case_reply = filtrate(dto.get("answer_content").toString());
			String sql = "insert into oper_caseinfo(case_id,case_rid,dict_case_type,cust_name,cust_addr,cust_tel,expert_type,case_expert,case_attr1,case_attr2,case_attr3,case_content,case_reply,case_time,state) values ('"
					+ case_id
					+ "','"
					+ userId
					+ "','"
					+ dict_case_type
					+ "','"
					+ cust_name
					+ "','"
					+ cust_addr
					+ "','"
					+ cust_tel
					+ "','"
					+ expert_type
					+ "','"
					+ case_expert
					+ "','"
					+ case_attr1
					+ "','"
					+ case_attr2
					+ "','"
					+ case_attr3
					+ "','"
					+ case_content + "','" + case_reply + "', getdate(),'原始')";
			int i = executeUpdate(sql);
		}
		// 如果是普通医疗库
		if (savedata.equals("普通医疗库")) {
			String id = ks.getNext("oper_medicinfo");
			String cust_name = filtrate(dto.get("cust_name").toString());
			String cust_addr = filtrate(dto.get("cust_addr").toString());
			String cust_tel = filtrate(request.getParameter("tel"));
			String dict_sex = filtrate(dto.get("dict_sex").toString());
			String expert = filtrate(dto.get("bill_num").toString());
			String contents = filtrate(dto.get("question_content").toString());
			String reply = filtrate(dto.get("answer_content").toString());
			String case_expert = filtrate(dto.get("expert_name").toString());// 专家名
			String sql = "insert into oper_medicinfo(id,medic_rid,cust_name,cust_addr,cust_tel,dict_sex,expert,contents,reply,create_time,state,expert_name) values ('"
					+ id
					+ "','"
					+ userId
					+ "','"
					+ cust_name
					+ "','"
					+ cust_addr
					+ "','"
					+ cust_tel
					+ "','"
					+ dict_sex
					+ "','"
					+ expert
					+ "','"
					+ contents + "','" + reply + "', getdate(),'原始','"+case_expert+"')";
			int i = executeUpdate(sql);
		}
		// 如果是预约医疗库
		if (savedata.equals("预约医疗库")) {
			String id = ks.getNext("oper_book_medicinfo");
			String cust_name = filtrate(dto.get("cust_name").toString());
			String cust_addr = filtrate(dto.get("cust_addr").toString());
			String cust_tel = filtrate(request.getParameter("tel"));
			String dict_sex = filtrate(dto.get("dict_sex").toString());
			String expert = filtrate(dto.get("bill_num").toString());
			String contents = filtrate(dto.get("question_content").toString());
			String reply = filtrate(dto.get("answer_content").toString());
			String sql = "insert into oper_book_medicinfo(id,book_rid,cust_name,cust_addr,cust_tel,dict_sex,expert,contents,reply,create_time,state) values ('"
					+ id
					+ "','"
					+ userId
					+ "','"
					+ cust_name
					+ "','"
					+ cust_addr
					+ "','"
					+ cust_tel
					+ "','"
					+ dict_sex
					+ "','"
					+ expert
					+ "','"
					+ contents + "','" + reply + "', getdate(),'原始')";
			int i = executeUpdate(sql);
		}
		// 如果是企业库
		if (savedata.equals("企业信息库")) {
			String id = ks.getNext("oper_corpinfo");
			String cust_name = filtrate(dto.get("cust_name").toString());
			String cust_addr = filtrate(dto.get("cust_addr").toString());
			String cust_tel = filtrate(request.getParameter("tel"));
			String expert = filtrate(dto.get("bill_num").toString());
			String contents = filtrate(dto.get("question_content").toString());
			String reply = filtrate(dto.get("answer_content").toString());
			String sql = "insert into oper_corpinfo(id,corp_rid,cust_name,cust_addr,cust_tel,expert,contents,reply,createtime,state) values ('"
					+ id
					+ "','"
					+ userId
					+ "','"
					+ cust_name
					+ "','"
					+ cust_addr
					+ "','"
					+ cust_tel
					+ "','"
					+ expert
					+ "','"
					+ contents
					+ "','"
					+ reply + "', getdate(),'原始')";
			int i = executeUpdate(sql);
		}
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);

		// add by zhangfeng
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd")
				.format(new java.util.Date());
		request.setAttribute("date", date);
		// IBaseDTO myDto =
		// (IBaseDTO)request.getSession().getAttribute("custinfoDTO");
		request.setAttribute("tel", tel);
		IBaseDTO myDto = custinfoService.getopenwinInfo(tel);
		request.setAttribute(map.getName(), myDto);
		// 将标志位置为不空，可显示添加成功
		request.setAttribute("flag", "flag");

		return map.findForward("load");
	}

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 * @return i 返回int
	 */
	public int executeUpdate(String sql) {

		Connection conn = null;
		Statement stmt = null;

		ApplicationContext ac = (ApplicationContext) getServlet()
				.getServletContext().getAttribute(
						ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
		BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int i = stmt.executeUpdate(sql);

			return i;

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		return 0;

	}

	/**
	 * 用于sql语句的字符串过滤单引号为全角单引号
	 * 
	 * @param string
	 * @return string
	 */
	public String filtrate(String s) {
		s = s.replaceAll("\'", "‘");
		return s;
	}

	public String getRingBegintime(String tel) {
		String ring_begintime = "";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		ApplicationContext ac = (ApplicationContext) getServlet()
				.getServletContext().getAttribute(
						ContextLoaderPlugIn.SERVLET_CONTEXT_PREFIX);
		BasicDataSource bds = (BasicDataSource) ac.getBean("datasource");

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from EasyState_Info where incomming_call = '"
					+ tel + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				ring_begintime = rs.getString("ring_begintime");
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

		return ring_begintime;
	}

}
