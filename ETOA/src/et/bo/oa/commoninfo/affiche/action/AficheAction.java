
package et.bo.oa.commoninfo.affiche.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


import et.bo.oa.commoninfo.affiche.service.AficheService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class AficheAction extends BaseAction{
    
    private AficheService aficheService = null;
    
    
    public AficheService getAficheService() {
        return aficheService;
    }

    public void setAficheService(AficheService aficheService) {
        this.aficheService = aficheService;
    }

    /**
     * <p>跳转到农用机器主柜架页面</p>
     *
     * @param info:跳转到农用机器主柜架页面
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toaficheMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        return map.findForward("main");
    }
    
    /**
     * <p>跳转到家用机器主查询</p>
     *
     * @param info:跳转到家用机器主查询
     * 
     * @return:
     * 
     * @throws
     */

    
    public ActionForward toaficheQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        return map.findForward("query");
    }
    
    /**
     * <p>农用车主机器信息load</p>
     *
     * @param info:农用车主机器信息load
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toaficheLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            String id = request.getParameter("id");
            IBaseDTO dto=aficheService.getAficheInfo(id);
            request.setAttribute(map.getName(),dto);
            return map.findForward("load");
        }
        return map.findForward("load");
    }
    
    
    /**
     * <p>农用车主机器信息显示</p>
     *
     * @param info:农用车主机器信息显示
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toaficheList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("afichepageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(5);
        List l = aficheService.findAficheInfo(formdto,pageInfo);
        int size = aficheService.getAficheInfoSize(); 
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/ETOA/",map,request);
        request.getSession().setAttribute("afichepageTurning",pt);
        //request.setAttribute("list",agroMachineService.findArgoMachineInfo(argoSearch.searchHirerOperInfo(qldto)));
        
        return map.findForward("list");
    }
    
    /**
     * <p>添加农用机器主基本信息</p>
     *
     * @param info:
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward operAfiche(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String type = request.getParameter("type");
        request.setAttribute("opertype",type);
        if (type.equals("insert")) {
            aficheService.addAficheInfo(formdto);
            request.setAttribute("idus_state","sys.addsuccess");
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            String[] str = formdto.getStrings("chk");
            ActionMessages errors = new ActionMessages();
            if (str.length == 0) {
                errors.add("nullselect", new ActionMessage(
                        "agrofront.afiche.aficheList.nullselect"));
                saveErrors(request, errors);
                return map.findForward("error");
            }
            aficheService.delAficheInfo(str);
            request.setAttribute("idus_state","sys.delsuccess");
            ActionForward actionforward = new ActionForward(
            "/oa/operaffiche.do?method=toaficheList&pagestop=pagestop");
            return actionforward;
        }
        return map.findForward("load");
    }
    
    public ActionForward toAficheInfo(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = request.getParameter("id");
        //IBaseDTO dto = articleService.getArticleInfo(id);
        //request.setAttribute(map.getName(), dto);
        request.setAttribute("list",aficheService.getAficheList(id));
        return map.findForward("aficheInfo");
    }
}
