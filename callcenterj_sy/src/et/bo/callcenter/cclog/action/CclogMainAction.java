/**
 * 
 */
package et.bo.callcenter.cclog.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.callcenter.cclog.service.CclogMainService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;


/**
 * @author Administrator
 *
 */
public class CclogMainAction extends BaseAction {
	static Logger log = Logger.getLogger(CclogMainAction.class.getName());

	private CclogMainService cclogMainService = null;

	private ClassTreeService cts = null;
	
	private ClassTreeService depTree = null;
	
	//private ExeuteCCInfoService eccs = null;

	/**
	 * @describe 呼叫中心日志主页面
	 */
	public ActionForward toCclogMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("main......................");
		return map.findForward("main");
	}
	
	/**
	 * @describe 当日录音查询主页面
	 */
	public ActionForward toRecordMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("main......................");
		return map.findForward("recordmain");
	}

	/**
	 * @describe 呼叫中心日志查询页
	 */
	public ActionForward toCclogQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
//		List grouplist = cts.getLabelVaList("jingyuanwenti");
//		request.setAttribute("grouplist", grouplist);

		//所有部门
//		request.setAttribute("departlist", depTree.getLabelVaList("1"));
		
		List user = cclogMainService
		.userQuery("select user_id from sys_user where group_id = 'SYS_GROUP_0000000001'");
		request.setAttribute("user", user);
		
		return map.findForward("query");
	}
	
	/**
	 * @describe 当日录音查询页
	 */
	public ActionForward toRecordQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
//		List grouplist = cts.getLabelVaList("jingyuanwenti");
//		request.setAttribute("grouplist", grouplist);

		//所有部门
//		request.setAttribute("departlist", depTree.getLabelVaList("1"));
		
		return map.findForward("recordquery");
	}
	

	/**
	 * @describe 页面Load，到日志详细信息页，列出这个电话的明细信息
	 * 包括警员信息及电话明细，并且可以进行添加修改的操作
	 */
	public ActionForward toCclogLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		String count = request.getParameter("count");
		request.setAttribute("opertype", type);
		if (type.equals("detail")) {
			String id = request.getParameter("id");
			String talkid = request.getParameter("talkid");
//			String telNum = request.getParameter("phonenum");
			
			System.out.println("CclogMainAction cclogId is "+id);
			System.out.println("CclogMainAction cclogId is "+talkid);
			System.out.println("map Name is "+map.getName());
			request.setAttribute(map.getName(), cclogMainService.getInfo(id, talkid));
//			request.setAttribute("list", cclogMainService.listPhoneInfo(id));
			// 日志的主id，与policecallin和policecallininfo对应
			
			if(count == null) {
				request.setAttribute("cclogid", id);
				request.setAttribute("talkid", talkid);
				return map.findForward("load");
			} else{
				request.setAttribute("cclogid", id);
				return map.findForward("Load");
			}
		}
		return map.findForward("load");
	}

	/**
	 * 添加咨询问题信息
	 * @version 2007-9-22
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 */
	public ActionForward toAddLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");
		List grouplist = cts.getLabelVaList("jingyuanwenti");
		request.setAttribute("grouplist", grouplist);		
		request.getSession().setAttribute("opertype", type);
		// 添加
		if (type.equals("insert")) {
//			List nullist=igs.addQueryLoadDivision();
//			request.setAttribute("sortlist", nullist);
//			request.setAttribute("divisionlist", nullist);
			
			String pid = request.getParameter("pid");
			request.getSession().setAttribute("pid", pid);
			return map.findForward("addload");
		}
		// 改
		if (type.equals("update")) {
			String id = request.getParameter("id");
			
			IBaseDTO dto = cclogMainService.getQuestionInfo(id);
			
			String info_group = (String)dto.get("tagAllInfo");
//			String info_sort = (String)dto.get("tagBigInfo");
//			
//			List sortlist = igs.addQueryLoadSort(info_group);
//			request.setAttribute("sortlist", sortlist);
//
//			List divisionlist = igs.addQueryLoadSort(info_sort);
//			request.setAttribute("divisionlist", divisionlist);
			
			request.setAttribute(map.getName(), dto);
			request.getSession().setAttribute("pid", id);
			return map.findForward("addload");
		}
		return map.findForward("addload");
	}

	/**
	 * @describe 问题添加以及修改操作
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward operQuestion(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String type = request.getParameter("type");
		
		List grouplist = cts.getLabelVaList("jingyuanwenti");
		request.setAttribute("grouplist", grouplist);
				
//		List nullist=igs.addQueryLoadDivision();
//		request.setAttribute("sortlist", nullist);
//		request.setAttribute("divisionlist", nullist);
		
		request.getSession().setAttribute("opertype", type);
		if (type.equals("insert")) {
			String pid = request.getParameter("pid");
			cclogMainService.addQuestionInfo(formdto, pid);
			request.setAttribute("idus_state", "1");
			return map.findForward("addload");
		}
		if (type.equals("update")) {
			// question.upQuestionInfo(formdto);
			
			String pid = request.getParameter("pid");
			formdto.set("id", pid);
			cclogMainService.upQuestionInfo(formdto);
			request.setAttribute("idus_state", "sys.updatesuccess");
			return map.findForward("addload");
		}
		return map.findForward("addload");
	}

	/**
	 * @describe 日志信息列表页
	 * @param map
	 *            类型 ActionMapping
	 * @param form
	 *            类型 ActionForm
	 * @param request
	 *            类型 HttpServletRequest
	 * @param response
	 *            类型 HttpServletResponse
	 * @return 类型 ActionForward
	 * 
	 */
	public ActionForward toCclogList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("cclogPageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(16);
        List list = new ArrayList();
        try {
			list = cclogMainService.queryCclog(formdto, pageInfo);
		} catch (RuntimeException e) {
			 e.printStackTrace();
		}
		int size = cclogMainService.getCclogSize();

        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",list);
        PageTurning pt = new PageTurning(pageInfo,"/cc_police_heb/",map,request);
        request.getSession().setAttribute("cclogPageTurning",pt);
        return map.findForward("list");
        
	}

	/**
	 * @describe cclog登陆后列表页信息
	 */
	public ActionForward toCclogSList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return null;
	}

	/**
	 * @describe 部门main页
	 */
	public ActionForward toDepMain(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("depmain");
	}

	/**
	 * @describe 部门query页
	 */
	public ActionForward toDepQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("depquery");
	}

	/**
	 * 得到部门信息列表
	 * 
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toDepList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		request.setAttribute("deplist", cclogMainService.getDepInfo(dto));
		return map.findForward("deplist");
	}
	
	
	
	
	
	
	
	/**************************************************大连项目添加回答成功与否的界面****************************************************/
	
	
	
	
	
	/**
	 * 跳转到添加回答成功人员的信息页
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toAnswerManLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		String id = request.getParameter("id");
		
		
//		String phonenum = request.getParameter("phonenum");
		List operatorList = cts.getLabelVaList("huidachenggong");
//		List expertList = cts.getLabelVaList("answer_expert");
//		for(int i=0;i<expertList.size();i++){
//			LabelValueBean lb = (LabelValueBean)expertList.get(i);
//			operatorList.add(lb);
//		}
		request.setAttribute("chenggonglist", operatorList);
//		request.setAttribute("chenggonglist", cts.getLabelVaList("huidachenggong"));
		request.setAttribute("policecallinid", id);
//		request.setAttribute(map.getName(), cclogMainService.getInfo(phonenum));
//		request.setAttribute("list", cclogMainService.listPhoneInfo(id));
		// 日志的主id，与policecallin和policecallininfo对应
		
		return map.findForward("manload");
	}
	/**
	 * 在数据库中添加回答成功人的信息
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward answerMan(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String id = request.getParameter("id");		
		request.setAttribute("chenggonglist", cts.getLabelVaList("huidachenggong"));
		request.setAttribute("policecallinid", id);
		//添加信息
		cclogMainService.addCclogInfo(dto, id);
		request.setAttribute("idus_state", "sys.updatesuccess");
		return map.findForward("manload");
	}
	
	/**
	 * 在页面根据来电号码进行回拨
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toCallBack(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String telNum = request.getParameter("telNum");
		String ip = request.getRemoteAddr();
		System.out.println("获得来电号码为："+telNum);
		System.out.println("获得本地的ip为："+ip);
		return map.findForward("load");
	}
	
	/**
	 * 查询当天当前座席员应答电话录音信息
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toRecordList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO)form;
		String telNum = formdto.get("telNum").toString();
		String un = request.getParameter("agentName");
		if(un != null)
			request.getSession().setAttribute("uName", un);
		else {
//			request.getSession().setAttribute("uName", "zf");
			un = (String)request.getSession().getAttribute("uName");
		}
		
		//System.out.println("username is "+un);
		//System.out.println("telNum is "+telNum);
		
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("cclogPageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(5);
		
		
        formdto.set("userName", un);
        List list = new ArrayList();
        try {
        	String beginTime = request.getParameter("beginTime");
        	String endTime = request.getParameter("endTime");
			//list = cclogMainService.queryRecord(un, telNum, pageInfo);
        	list = cclogMainService.queryRecord(un, telNum, beginTime,endTime,pageInfo);
		} catch (RuntimeException e) {
			 e.printStackTrace();
		}

		int size = cclogMainService.getRecordSize();

        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        PageTurning pt = new PageTurning(pageInfo,"/cc_police_heb/",map,request);
        request.getSession().setAttribute("cclogPageTurning",pt);
        request.setAttribute("recordlist",list);
        return map.findForward("recordList");
	}
	
	/**
	 * 查询IVR详细信息
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toIVRInfo(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String cclogId = (String)dto.get("id");
		
		List list = new ArrayList();
		list = cclogMainService.queryIVRInfo(cclogId);
		request.setAttribute("ivrlist", list);

		return map.findForward("ivrInfo");
	}
	
	/**
	 * 查询咨询问题详细信息
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toQuesInfo(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO) form;
		String cclogId = (String)dto.get("id");
		
		List list = new ArrayList();
		list = cclogMainService.queryQuesInfo(cclogId);
		request.setAttribute("queslist", list);
		
		return map.findForward("quesInfo");
	}

	
	public ActionForward toQuestionLoad(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		request.setAttribute("opertype", type);
		if (type.equals("detail")) {
			String questionId = request.getParameter("id");
//			String telNum = request.getParameter("phonenum");
			
			System.out.println("CclogMainAction questionId is "+questionId);
			request.setAttribute(map.getName(), cclogMainService.getQuesInfo(questionId));
//			request.setAttribute("list", cclogMainService.listPhoneInfo(id));
			// 日志的主id，与policecallin和policecallininfo对应
			
			
			request.setAttribute("questionId", questionId);
			return map.findForward("quesLoad");
			
		}
		return map.findForward("quesLoad");
	}
	
	/****************************************************大连项目添加结束**************************************************************/


	public CclogMainService getCclogMainService() {
		return cclogMainService;
	}

	public void setCclogMainService(CclogMainService cclogMainService) {
		this.cclogMainService = cclogMainService;
	}

	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	
}
