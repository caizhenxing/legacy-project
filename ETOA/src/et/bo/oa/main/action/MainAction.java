package et.bo.oa.main.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.news.service.ArticleService;
import et.bo.oa.checkwork.service.AbsenceServiceI;
import et.bo.oa.commoninfo.affiche.service.AficheService;
import et.bo.oa.communicate.email.service.EmailService;
import et.bo.oa.main.service.MainSerivce;
import et.bo.oa.privy.plan.service.PlanService;
import et.bo.oa.workflow.service.WorkFlowService;
import et.bo.oa.workplan.service.WorkPlanService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p> 首页Action </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-15
 * 
 */
public class MainAction extends BaseAction {
	
	private Log log = LogFactory.getLog(MainAction.class);

	private MainSerivce mainService = null;
    
    private ArticleService articleService = null;
    
    private AficheService aficheService = null;
    
    private WorkFlowService workFlowService = null;
    
    private EmailService emailService = null;
    
    private WorkPlanService wps = null;
    
    private AbsenceServiceI absenceService = null;
	
	public AbsenceServiceI getAbsenceService() {
		return absenceService;
	}

	public void setAbsenceService(AbsenceServiceI absenceService) {
		this.absenceService = absenceService;
	}

	public AficheService getAficheService() {
        return aficheService;
    }

    public void setAficheService(AficheService aficheService) {
        this.aficheService = aficheService;
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public MainAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> 操作首页 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        request.setAttribute("newsList",articleService.getIndexList());
        request.setAttribute("afficheList",aficheService.getAficheList());
        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
        request.setAttribute("workList",workFlowService.getTaskList(ui.getRole(),ui.getUserName(),5));
        request.setAttribute("emailList",mainService.inemailList(ui.getUserName()));
        request.setAttribute("planList",wps.getMyMission(ui,5));
        request.setAttribute("outStateList", absenceService.getOutStateList());
		return mapping.findForward("mainPage");
	}
	/**
	 * <p> 返回工作计划 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toMorePlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
       // request.setAttribute("newsList",articleService.getIndexList());
       // request.setAttribute("afficheList",aficheService.getAficheList());
        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
       // request.setAttribute("workList",workFlowService.getTaskList(ui.getRole(),ui.getUserName(),5));
        //request.setAttribute("emailList",emailService.getIndexList());
        request.setAttribute("planList",wps.getMyMyMyMission(ui));
		return mapping.findForward("planMore");
	}
	
	public ActionForward toMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("messagePage");
	}
    
    /**
     * <p>跳转到新闻更多的页面</p>
     *
     * @param info:跳转到新闻更多的页面
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toMoreNews(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String) request.getParameter("pagestate");
        String page = request.getParameter("pagestop");
        if (pageState == null && page == null) {
            pageInfo = new PageInfo();
        } else {
            PageTurning pageTurning = (PageTurning) request.getSession()
                    .getAttribute("agropageTurning");
            pageInfo = pageTurning.getPage();
            if (pageState != null)
                pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO) pageInfo.getQl();
        }
        
        pageInfo.setPageSize(5);
        List l = articleService.findIndexInfo(formdto, pageInfo);
        int size = articleService.getIndexSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/ETOA/", mapping, request);
        request.getSession().setAttribute("agropageTurning", pt);
        // request.setAttribute("list",agroMachineService.findArgoMachineInfo(argoSearch.searchHirerOperInfo(qldto)));

        return mapping.findForward("newsList");
    }
    
    /**
     * <p>跳转到公告详细页</p>
     *
     * @param info:跳转到公告详细页
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toMoreAfiche(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String) request.getParameter("pagestate");
        String page = request.getParameter("pagestop");
        if (pageState == null && page == null) {
            pageInfo = new PageInfo();
        } else {
            PageTurning pageTurning = (PageTurning) request.getSession()
                    .getAttribute("agropageTurning");
            pageInfo = pageTurning.getPage();
            if (pageState != null)
                pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO) pageInfo.getQl();
        }
        
        pageInfo.setPageSize(5);
        List l = aficheService.getIndexAficheList(formdto, pageInfo);
        int size = aficheService.getAficheInfoSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/ETOA/", mapping, request);
        request.getSession().setAttribute("agropageTurning", pt);
        // request.setAttribute("list",agroMachineService.findArgoMachineInfo(argoSearch.searchHirerOperInfo(qldto)));

        return mapping.findForward("aficheList");
    }
    

    public ActionForward toMoreEmail(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);

        String pageState = null;
        PageInfo pageInfo = null;
        formdto.set("sendUser",ui.getUserName());
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("mainEmailPageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(5);
        List l = mainService.emaliListIndex(formdto,pageInfo);
        int size = mainService.getEmailIndexSize(); 
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("mainEmailPageTurning",pt);
        
        return map.findForward("emailList");
    }

    /**
	 * <p> 外出状态查询 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOutStateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List outStateList = absenceService.getOutStateList();
		request.setAttribute("outStateList",outStateList);
		return mapping.findForward("toGetOutStateList");
	}
	
	/**
	 * <p> 所有人外出状态查询 </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllOutStateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
		List outStateList = absenceService.getAllOutStateList();
		request.setAttribute("outStateList",outStateList);       
		return mapping.findForward("toGetAllOutStateList");
	}
	
    /**
     * <p>邮件详细信息</p>
     *
     * @param info:邮件详细信息
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toEmailInfo(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = request.getParameter("id");
        try {
        	request.setAttribute("list",mainService.getEmailInfo(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return map.findForward("emailInfo");
    }
    
    public WorkFlowService getWorkFlowService() {
        return workFlowService;
    }

    public void setWorkFlowService(WorkFlowService workFlowService) {
        this.workFlowService = workFlowService;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public MainSerivce getMainService() {
		return mainService;
	}

	public void setMainService(MainSerivce mainService) {
		this.mainService = mainService;
	}

	public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

	public WorkPlanService getWps() {
		return wps;
	}

	public void setWps(WorkPlanService wps) {
		this.wps = wps;
	}

    
}
