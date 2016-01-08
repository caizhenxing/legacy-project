package et.bo.oa.checkwork.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.checkwork.service.AbsenceServiceI;
import et.bo.oa.checkwork.service.CheckWorkServiceI;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * <p> ���ڹ��� Action </p>
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-09
 * 
 */

public class CheckWorkAction extends BaseAction {

	private static Log logger = LogFactory.getLog(CheckWorkAction.class);

	private CheckWorkServiceI checkWorkService = null;
	
	// ��״�ṹ
	private ClassTreeService ctree = null;

	// ���Žṹ
	private ClassTreeService departTree = null;
	
	private KeyService keyService = null;

	public CheckWorkAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p> ת����ѯ������ҳ </p>
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
		return mapping.findForward("mainPage");
	}

	/**
	 * <p> ��ѯҳ </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List departLists = departTree.getLabelList("1");
		request.setAttribute("departLists",departLists);
		List nameList = checkWorkService.getNameList();
		request.setAttribute("nameList",nameList);
		return mapping.findForward("checkWrokQuery");
	}

	/**
	 * <p> ����б�ҳ </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;
//        long begintime = System.currentTimeMillis();
		String pageState = null;
		PageInfo pageInfo = null;
		pageState = request.getParameter("pagestate");
		String page = request.getParameter("pagestop");
		if (pageState == null && page == null) {
			pageInfo = new PageInfo();
		} else {
			PageTurning pageTurning = (PageTurning) request.getSession()
					.getAttribute("checkworkTurning");
			pageInfo = pageTurning.getPage();
			if (pageState != null)
				pageInfo.setState(pageState);
			formDto = (DynaActionFormDTO) pageInfo.getQl();
		}
		pageInfo.setPageSize(10);
		Object[] checkList = checkWorkService.seletCheckList(formDto,pageInfo);
		int size  = checkWorkService.getCheckListSize();
		pageInfo.setRowCount(size);
		pageInfo.setQl(formDto);	
		request.setAttribute("checkLists",checkList);
		PageTurning papgeTurning = new PageTurning(pageInfo, "/ETOA/", mapping, request);
        request.getSession().setAttribute("checkworkTurning", papgeTurning);
//        long endtime = System.currentTimeMillis();
//        System.out.println("ִ��ʱ�� : "+(endtime-begintime));
		return mapping.findForward("checkWorkList");
	}
	/**
	 * <p> �ٵ����˽���б�ҳ </p>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toLaterOrEarlyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formDto = (DynaActionFormDTO) form;			
//		long begintime = System.currentTimeMillis();	
		try {
				String startT = request.getParameter("st");
				String endT = request.getParameter("et");
				formDto.set("startT", startT);
				if(TimeUtil.getTimeByStr(TimeUtil.getTheTimeStr(TimeUtil.getNowTime()), "yyyy-MM-dd").compareTo(TimeUtil.getTimeByStr(endT, "yyyy-MM-dd"))>0)
				{
					formDto.set("endT", endT);
				}else{
					formDto.set("endT", TimeUtil.getTheTimeStr((TimeUtil.getNowTime()), "yyyy-MM-dd"));
				}
				formDto.set("repairUser", request.getParameter("employeeId"));
			} catch (RuntimeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return mapping.findForward("error");
			}
		try {
			List checkList = checkWorkService.seletLaterOrEarlyCheckList(formDto);
			request.setAttribute("checkLists",checkList);
			List waichuList = checkWorkService.seletWaichuList(formDto);
			request.setAttribute("waichuList",waichuList);
			List qingjiaList = checkWorkService.selecQingjiaList(formDto);
			request.setAttribute("qingjiaList",qingjiaList);
			List chuchaiList = checkWorkService.selectChuchaiList(formDto);
			request.setAttribute("chuchaiList",chuchaiList);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("toLaterOrEarlyList��ѯʧ��");
			e.printStackTrace();
			List departLists = departTree.getLabelList("1");
			request.setAttribute("departLists",departLists);
			List nameList = checkWorkService.getNameList();
			request.setAttribute("nameList",nameList);
			return mapping.findForward("error");
		}
//		long endtime = System.currentTimeMillis();
//        System.out.println("ִ��ʱ�� : "+(endtime-begintime));
		return mapping.findForward("checkLaterOrEarlyList");
	}

	/* set&get Method start */
	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

	public CheckWorkServiceI getCheckWorkService() {
		return checkWorkService;
	}

	public void setCheckWorkService(CheckWorkServiceI checkWorkService) {
		this.checkWorkService = checkWorkService;
	}

	public ClassTreeService getCtree() {
		return ctree;
	}

	public KeyService getKeyService() {
		return keyService;
	}

	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}

	public ClassTreeService getDepartTree() {
		return departTree;
	}

	public void setDepartTree(ClassTreeService departTree) {
		this.departTree = departTree;
	}
	
	/* set&get Method end */
}
