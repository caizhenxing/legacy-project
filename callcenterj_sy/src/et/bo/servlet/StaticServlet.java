package et.bo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import et.bo.screen.oper.OperScreen;
import et.bo.sys.user.service.UserService;
import excellence.framework.base.container.SpringRunningContainer;
import excellence.framework.base.dto.IBaseDTO;

public class StaticServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	//加载上所有案例库的权限的信息
	public static final Map<String,List<String>> userPowerMap = new HashMap<String,List<String>>();
	
	//12316专题调查列表信息（包括农民版，企业版和政府版）
	public static final List<Object> inquiryList = new ArrayList<Object>();
	//普通安全库的信息列表（使用user大屏幕的经典案例）
	public static final List<IBaseDTO> caseList = new ArrayList<IBaseDTO>();
	

	public void init() throws ServletException {
		//得到用户权限列表信息
		UserService us = (UserService)SpringRunningContainer.getInstance().getBean("UserService");
		us.addPowerInStaticMap();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
