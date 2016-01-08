package excellence.framework.base.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import excellence.common.classtree.ClassTreeService;
import excellence.common.tree.base.service.TreeControlNodeService;

/**
 * @author zhaoyifei
 * @author wang wenquan
 * @version 2007-1-15
 * @see 基本action框架
 */
public class BaseAction extends DispatchAction {

	/*
	 * 记录日志
	 */
	private static Log log = LogFactory.getLog(BaseAction.class);

	/*
	 * 存的是当前操作节点的id
	 */
	protected String nickName = null;

	/*
	 * 日志所需的其它信息 key value
	 */
	protected Map logInfoMap = null;

	/*
	 * 里边是参数树 这个需要子类注入
	 */
	private ClassTreeService classTreeService = null;

	/*
	 * 对此action日志操作的整体控制 这个模块是否需要写日志 true 是 false 否 默认值true
	 */
	// private String writeLog = "true";
	//	
	// public String getWriteLog() {
	// return writeLog;
	// }
	// public void setWriteLog(String writeLog) {
	// this.writeLog = writeLog;
	// }
	/**
	 * 根据struts—config.xml里parameter值调用相应的方法
	 * 
	 * @param ActionMapping
	 *            mapping, ActionForm form, HttpServletRequest request,
	 *            HttpServletResponse response
	 * @return ActionForward 调用转发页面
	 * @throws
	 */
	public final ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session == null)
			return mapping.findForward("sessionTimeOut");
		this.before(mapping, form, request, response);
		ActionForward a = super.execute(mapping, form, request, response);
		this.after(mapping, form, request, response);
		return a;
	}

	/*
	 * 原则上，返回的一些信息写在request或者session中。
	 */
	protected void before(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// this.initNickName();

	}

	/*
	 * 原则上，返回的一些信息写在request或者session中。
	 */
	protected void after(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String logNickName = (String) request.getAttribute("logNickName");
		this.initNickName(logNickName);
		if (nickName != null) {
			try {
				TreeControlNodeService node = classTreeService
						.getNodeByNickName(nickName);

				// Map otherInfoMap = (Map) request.getSession().getAttribute(
				// SysStaticParameter.LOG_OTHER_INFO_MAP_INSESSION);
				// if (otherInfoMap == null)
				// throw new NullPointerException(
				// "session超时记录日志所需信息为空不能记录日志错误");
				// this.log2Db(node, otherInfoMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据节点像数据库中记录日志
	 * 
	 * @param TreeControlNodeService
	 *            node 记录的节点
	 * @param IBaseDTO
	 *            dto 日志所需的其他属性
	 */
	protected void log2Db(TreeControlNodeService node, Map logInfoMap) {
		// classTreeService.log2db(node, logInfoMap);
	}

	/**
	 * 初始化日志的其他信息
	 * 
	 */
	// protected abstract void initLogInfoMap();
	/**
	 * 初始化日志的其他信息
	 * 
	 */
	// protected abstract void initNickName();
	protected void initNickName(String logNickName) {
		if (logNickName != null)
			this.nickName = logNickName;
	}

	/**
	 * spring 注入
	 * 
	 * @return
	 */
	public ClassTreeService getClassTreeService() {
		return classTreeService;
	}

	/**
	 * spring 注入
	 * 
	 * @return
	 */
	public void setClassTreeService(ClassTreeService classTreeService) {
		// System.out.println("注入classTreeService"+classTreeService);
		this.classTreeService = classTreeService;
	}
	
	/**
	 * zhang feng add
	 * JSON数据输出
	 * 
	 * @param response
	 * @param json
	 */
	protected void outJsonString(HttpServletResponse response, String json) {
		response.setContentType("text/javascript;charset=UTF-8");
		try {
			outString(response,json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 输出xml格式的字符串
	 * @param response
	 * @param xmlStr
	 */
	protected void outXMLString(HttpServletResponse response,String xmlStr) {
		response.setContentType("application/xml;charset=UTF-8");
		try {
			outString(response,xmlStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//输出json格式数据
	private void outString(HttpServletResponse response,String str) {
		try {
			PrintWriter out = response.getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
