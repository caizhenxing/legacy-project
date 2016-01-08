package et.bo.sys.login.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.struts.ContextLoaderPlugIn;

import et.bo.callcenter.portCompare.service.PortCompareService;
import et.bo.callcenter.serversocket.checklogin.LoginMapService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.bean.UserBean;
import et.bo.sys.login.service.loginService;
import et.bo.sys.roleClassTree.RoleClassTreeService;
import et.bo.sys.user.action.Password_encrypt;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.tree.base.service.TreeService;
import excellence.common.util.Constants;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;

public class loginAction extends BaseAction {

	private String path = "images";

	private loginService ls = null;

	// 端口管理
	private PortCompareService pcs = null;

	// 登陆验证
	private LoginMapService lms = null;

	private ClassTreeService classTreeService = null;

	// 用户叶子节点授权相关
	private RoleClassTreeService roleClassTreeService = null;

	/**
	 * @describe 登陆页面
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String css = Constants.getProperty("web_html_css");
		path = Constants.getProperty("web_images_link_path");
		IBaseDTO myForm = (IBaseDTO) form;
//		String rand = (String) request.getSession().getAttribute("rand");
		String select = myForm.get("select").toString();
		// 默认是春的模板
		String style = "chun";

		// 加载春夏秋冬四个样式文件
		if (select.equals("summer")) {
			style = "xia";
			// request.getSession().setAttribute("style", "xia");
		} else if (select.equals("spring")) {
			style = "chun";
//			request.getSession().setAttribute("style", "chun");
		} else if (select.equals("autumn")) {
			style = "qiu";
//			request.getSession().setAttribute("style", "qiu");
		} else if (select.equals("winter")) {
			style = "dong";
//			request.getSession().setAttribute("style", "dong");
		}
		
		
		// if (myForm.get("val") == null || !((String)
		// myForm.get("val")).trim().equals(rand)) {
		// request.setAttribute("error", "sys.login.error.val");
		// request.removeAttribute(mapping.getName());
		// return mapping.findForward("login");
		// }
		String user = (String) myForm.get("userName");

		String pw = (String) myForm.get("password");
		// System.out.println("user "+user+" pw "+pw);
		if (user == null || user.trim().equals("")) {
			request.setAttribute("error", "sys.login.error.user");
			request.removeAttribute(mapping.getName());
			return mapping.findForward("login");
		}
		if (pw == null || pw.trim().equals("")) {
			request.setAttribute("error", "sys.login.error.password");
			request.removeAttribute(mapping.getName());
			return mapping.findForward("login");
		}
		Password_encrypt pe = new Password_encrypt();
		pw = pe.pw_encrypt(pw);
		// System.out.println("pw --->>> "+pw);
		boolean pass = ls.login(user, pw);
		// System.out.println(pass+" pass ");
		if (!pass) {
			request.setAttribute("error", "sys.login.error.login");
			request.removeAttribute(mapping.getName());
			return mapping.findForward("login");
		}
		try {
			// System.out.println(pass+" pass ");
			SysUser su = new SysUser();
			su.setUserName(user);
			su.setPassword(pw);
			String cssinsession = "/" + Constants.getProperty("project_name")
					+ "/" + path + "/" + "css" + "/" + css;
			String imagesinsession = "/"
					+ Constants.getProperty("project_name") + "/" + path + "/";
			request.getSession().setAttribute(
					SysStaticParameter.IMAGES_IN_SESSION, imagesinsession);
			request.getSession().setAttribute(
					SysStaticParameter.CSS_IN_SESSION, cssinsession);

			// 将登陆用户的信息放入session中
			UserBean ub = ls.loadUserBean(user);
			String ip = request.getRemoteAddr();
			ub.setLineId(pcs.getPortByIp(ip));
			request.getSession().setAttribute(
					SysStaticParameter.USER_IN_SESSION, ub);
			request.getSession().setAttribute(
					SysStaticParameter.USER_IN_SESSION, ub);
			TreeService tree = ls.loadTree(user);
			request.getSession().setAttribute(
					SysStaticParameter.MOD_TREE_IN_SESSION, tree);
			request.getSession().setAttribute(
					SysStaticParameter.USERBEAN_IN_SESSION, ub);

			// classTreeService.loadParamTree();
			// classTreeService.reloadParamTree();

			// 加载用户角色权限放map里
			Map userLeafRightMap = roleClassTreeService
					.getRoleLeafRightByUserId(user);
			request.getSession().setAttribute(
					SysStaticParameter.USER_ROLE_LEAFRIGHT_INSESSION,
					userLeafRightMap);
			// 记录日志
			Map<String, String> otherInfoMap = new HashMap<String, String>();

			otherInfoMap.put("userId", user);
			otherInfoMap.put("password", pw);
			otherInfoMap.put("ip", request.getRemoteAddr());
			otherInfoMap.put("isLogin", "true");
			// classTreeService.log2db(null,otherInfoMap);
			otherInfoMap.remove("isLogin");
			otherInfoMap.put("isLogin", "false");
			request.getSession().setAttribute(
					SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION,
					otherInfoMap);

			// 取出消息数量
			// String num = executeQuery("select count(*) from oper_messages
			// where receive_id = '"+user+"'");
			// String num2 = executeQuery("select count(*) from oper_messages
			// where receive_id = '"+user+"' and dict_read_flag='0'");
			// request.getSession().setAttribute("msgNum", num);
			// request.getSession().setAttribute("msgNum2", num2);

			// 判断是座席中还是管理员登陆,如果是座席员,将页面置为座席面板,并且将页面信息加入map中
			String purview = ub.getUserGroup();
			// System.out.println("组的权限是......"+purview);
			String skill = ub.getSkill();
			if (purview != null && purview.equals("operator")
					|| purview.equals("opseating")) {
				// 通知！！
				String checkIp = lms.checkIp(ip);
				if (checkIp.equals("0")) {
					lms.setLoginUer(ip, user, skill);
					//只有座席有春夏秋冬四套模块
					request.getSession().setAttribute("style", style);
					return mapping.findForward("agentSuccess");
				}
				if (checkIp.equals("1")) {
					request.setAttribute("error", "sys.login.error.ipError");
					return mapping.findForward("error");
				}
			} else if (purview != null && purview.equals("TelConference")) {// 电话会议
				//样式中只有春的模板
				request.getSession().setAttribute("style", "chun");
				return mapping.findForward("TelConferenceSuccess");
			}
			//非座席员登陆之后只有春的模板
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "sys.login.error.other");
			return mapping.findForward("login");
		}
	}

	// 重加载模块页面
	public ActionForward reloadModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.getSession().removeAttribute(
				SysStaticParameter.MOD_TREE_IN_SESSION);
		UserBean ub = (UserBean) request.getSession().getAttribute(
				SysStaticParameter.USERBEAN_IN_SESSION);
		TreeService tree = ls.loadTree(ub.getUserId());
		request.getSession().setAttribute(
				SysStaticParameter.MOD_TREE_IN_SESSION, tree);
		// System.out.println("*******************************************&&&&&&&&&&&&&&&&");
		return mapping.findForward("gomodule");
	}

	// 切换登陆界面风格
	public ActionForward selectChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IBaseDTO myForm = (IBaseDTO) form;
		String select = myForm.get("select").toString();
		if (select.equals("summer"))
			request.getSession().setAttribute("style", "xia");
		else if (select.equals("spring"))
			request.getSession().setAttribute("style", "chun");
		else if (select.equals("autumn"))
			request.getSession().setAttribute("style", "qiu");
		else if (select.equals("winter"))
			request.getSession().setAttribute("style", "dong");
		return mapping.findForward("login2");
	}

	/**
	 * @describe 退出登陆页面
	 */
	public ActionForward logoff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.getSession().invalidate();
		return mapping.findForward("login");
	}

	public loginService getLs() {
		return ls;
	}

	public void setLs(loginService ls) {
		this.ls = ls;
	}

	public ClassTreeService getClassTreeService() {
		return classTreeService;
	}

	public void setClassTreeService(ClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
	}

	public RoleClassTreeService getRoleClassTreeService() {
		return roleClassTreeService;
	}

	public void setRoleClassTreeService(
			RoleClassTreeService roleClassTreeService) {
		this.roleClassTreeService = roleClassTreeService;
	}

	public LoginMapService getLms() {
		return lms;
	}

	public void setLms(LoginMapService lms) {
		this.lms = lms;
	}

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 * @return String num
	 */
	public String executeQuery(String sql) {
		String num = "";
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
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				num = rs.getString(1);
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

		return num;

	}

	public PortCompareService getPcs() {
		return pcs;
	}

	public void setPcs(PortCompareService pcs) {
		this.pcs = pcs;
	}
}