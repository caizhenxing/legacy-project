package et.bo.pcc.operatorStatistic.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.pcc.cclog.service.CclogService;
import et.bo.pcc.operatorStatistic.service.OperatorStatisticService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class OperatorStatisticAction extends BaseAction {
    static Logger log = Logger.getLogger(OperatorStatisticAction.class.getName());
    
    private OperatorStatisticService operatorStatisticService = null;
	/**
	 * @describe ��ϯԱͳ�Ʋ�ѯ��ҳ��
	 */
	public ActionForward toOperatorStatisticMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("main");
    }
	/**
	 * @describe ��ϯԱ��ϯ��ѯ��ҳ��
	 */
	public ActionForward toChuxiInfoMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("chuxiMain");
    }
	/**
	 * @describe ��ϯԱ����ʱ����ҳ��
	 */
	public ActionForward toWorkTimeMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		return map.findForward("workTimeMain");
    }
	/**
	 * @describe ��ѯҳ
	 */
	public ActionForward toOperatorStatisticQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		List list = operatorStatisticService.getWorkInfoPersonList();
		request.setAttribute("personList",list);
		return map.findForward("query");
    }
	/**
	 * @describe ��ϯ��ѯҳ
	 */
	public ActionForward toChuxiInfoQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		List list = operatorStatisticService.getWorkInfoPersonList();
		request.setAttribute("personList",list);
		return map.findForward("chuxiQuery");
    }
	/**
	 * @describe ��ϯʱ���ѯҳ
	 */
	public ActionForward toWorkTimeQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		List list = operatorStatisticService.getWorkInfoPersonList();
		request.setAttribute("personList",list);
		return map.findForward("workTimeQuery");
    }
	/**
	 * @describe ��ϯԱͳ����ϸ��Ϣ��ѯ
	 */
	public ActionForward toOperatorStatisticDetail(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String type = request.getParameter("type");
//        request.setAttribute("opertype",type);
//        if(type.equals("detail")){
//        	String id = request.getParameter("id");
//        	IBaseDTO dto = operatorStatisticService.getCclogInfo(id);
//        	request.setAttribute(map.getName(), dto);
//        	return map.findForward("load");
//        }
		return map.findForward("detail");
    }
	/**
	 * @describe ��ϯԱ����ͳ��ҳ
	 */
	public ActionForward toOperatorStatisticList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("ospageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            dto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List list = operatorStatisticService.operatorWorkInfoQuery(dto, pageInfo);
        int size = operatorStatisticService.getOperatorWorkInfoSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(dto);
        request.setAttribute("list", list);
        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
        request.getSession().setAttribute("ospageTurning",pt);       
		return map.findForward("list");
    }
	/**
	 * @describe ��ϯԱ��ϯͳ��ҳ
	 */
	public ActionForward toChuxiInfoList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
//		String pageState = null;
//        PageInfo pageInfo = null;
//        pageState = (String)request.getParameter("pagestate");
//        if (pageState==null) {
//            pageInfo = new PageInfo();
//        }else{
//            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("chuxiInfopageTurning");
//            pageInfo = pageTurning.getPage();
//            pageInfo.setState(pageState);
//            dto = (DynaActionFormDTO)pageInfo.getQl();
//        }
//        pageInfo.setPageSize(10);
        List list = operatorStatisticService.operatorChuXiQuery(dto);
//        int size = operatorStatisticService.getOperatorWorkInfoSize();
//        pageInfo.setRowCount(size);
//        pageInfo.setQl(dto);
        request.setAttribute("list", list);
//        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
//        request.getSession().setAttribute("chuxiInfopageTurning",pt);       
		return map.findForward("chuxiList");
    }
	/**
	 * @describe ��ϯԱ�����绰ͳ��ҳ
	 */
	public ActionForward toJieTingInfoList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
//		String pageState = null;
//        PageInfo pageInfo = null;
//        pageState = (String)request.getParameter("pagestate");
//        if (pageState==null) {
//            pageInfo = new PageInfo();
//        }else{
//            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("chuxiInfopageTurning");
//            pageInfo = pageTurning.getPage();
//            pageInfo.setState(pageState);
//            dto = (DynaActionFormDTO)pageInfo.getQl();
//        }
//        pageInfo.setPageSize(10);
        try {
			List list = operatorStatisticService.operatorJieTingQuery(dto);
//			System.out.println("����"+list.size());
			request.setAttribute("list", list);
//			System.out.println("/JieTing.........................");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
		return map.findForward("chuxiList");
    }
	/**
	 * @describe ��ϯԱ����ϯͳ��ҳ
	 */
	public ActionForward toOutInfoList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
//		String pageState = null;
//        PageInfo pageInfo = null;
//        pageState = (String)request.getParameter("pagestate");
//        if (pageState==null) {
//            pageInfo = new PageInfo();
//        }else{
//            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("chuxiInfopageTurning");
//            pageInfo = pageTurning.getPage();
//            pageInfo.setState(pageState);
//            dto = (DynaActionFormDTO)pageInfo.getQl();
//        }
//        pageInfo.setPageSize(10);
        List list = operatorStatisticService.operatorOutQuery(dto);
//        int size = operatorStatisticService.getOperatorWorkInfoSize();
//        pageInfo.setRowCount(size);
//        pageInfo.setQl(dto);
        request.setAttribute("list", list);
//        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
//        request.getSession().setAttribute("chuxiInfopageTurning",pt);       
		return map.findForward("chuxiList");
    }
	/**
	 * @describe ��ϯԱ��ϯʱ��ͳ��
	 */
	public ActionForward toChuXiTimeList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		if(!dto.get("endTime").toString().equals("")){
			if(TimeUtil.getTimeByStr(dto.get("endTime").toString(), "yyyy-MM-dd").compareTo(TimeUtil.getTimeByStr(TimeUtil.getTheTimeStr(TimeUtil.getNowTime()), "yyyy-MM-dd"))>0)
			{
				dto.set("endTime", TimeUtil.getTheTimeStr((TimeUtil.getNowTime()), "yyyy-MM-dd"));
			}
		}	
		List list = operatorStatisticService.chuXiTimeQuery(dto);
        request.setAttribute("opertype","et.pcc.operatorStatistic.list.chuxiTime");
        request.setAttribute("list", list);    
		return map.findForward("workTimeList");
    }
	/**
	 * @describe ��ϯԱ����ʱ��ͳ��
	 */
	public ActionForward toJieTingTimeList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		if(!dto.get("endTime").toString().equals("")){
			if(TimeUtil.getTimeByStr(dto.get("endTime").toString(), "yyyy-MM-dd").compareTo(TimeUtil.getTimeByStr(TimeUtil.getTheTimeStr(TimeUtil.getNowTime()), "yyyy-MM-dd"))>0)
			{
				dto.set("endTime", TimeUtil.getTheTimeStr((TimeUtil.getNowTime()), "yyyy-MM-dd"));
			}
		}	
		List list = operatorStatisticService.jieTingTimeQuery(dto);
        request.setAttribute("opertype","et.pcc.operatorStatistic.list.jietingTime");
        request.setAttribute("list", list);   
		return map.findForward("workTimeList");
    }
	/**
	 * @describe ��ϯԱ�뿪ʱ��ͳ��
	 */
	public ActionForward toOutTimeList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionFormDTO dto = (DynaActionFormDTO)form;
		if(!dto.get("endTime").toString().equals("")){
			System.out.println("................null...........");
			if(TimeUtil.getTimeByStr(dto.get("endTime").toString(), "yyyy-MM-dd").compareTo(TimeUtil.getTimeByStr(TimeUtil.getTheTimeStr(TimeUtil.getNowTime()), "yyyy-MM-dd"))>0)
			{
				System.out.println("time");
				System.out.println(TimeUtil.getTheTimeStr((TimeUtil.getNowTime()), "yyyy-MM-dd"));
				dto.set("endTime", TimeUtil.getTheTimeStr((TimeUtil.getNowTime()), "yyyy-MM-dd"));
			    System.out.println(dto.get("endTime").toString());
			}
		}	
		List list = new ArrayList();
		try {
			System.out.println("y");
			list = operatorStatisticService.outTimeQuery(dto);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        request.setAttribute("opertype","et.pcc.operatorStatistic.list.outTime");
        request.setAttribute("list", list);  
		return map.findForward("workTimeList");
    }
	public OperatorStatisticService getOperatorStatisticService() {
		return operatorStatisticService;
	}
	public void setOperatorStatisticService(
			OperatorStatisticService operatorStatisticService) {
		this.operatorStatisticService = operatorStatisticService;
	}
    
    
}
