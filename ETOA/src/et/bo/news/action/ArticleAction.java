/*
 * <p>Title:       �򵥵ı���</p>
 * <p>Description: ����ϸ��˵��</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.news.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


import et.bo.news.service.ArticleService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class ArticleAction extends BaseAction {

    // ��������
    private ArticleService articleService = null;

    // ��״�ṹ��ע��
    private ClassTreeService ctree = null;

    public ActionForward toArticleMain(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return map.findForward("main");
    }
    
    /**
     * <p>������ϸҳ����Ϣ��ʾ</p>
     *
     * @param info:������ϸҳ����Ϣ��ʾ
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toNewsInfo(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = request.getParameter("id");
        //IBaseDTO dto = articleService.getArticleInfo(id);
        //request.setAttribute(map.getName(), dto);
        try{
        	request.setAttribute("list",articleService.getNewsList(id));
        }catch(Exception e){
        	e.printStackTrace();
        }
        return map.findForward("newsInfo");
    }

    // to query
    public ActionForward toArticleQuery(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List l = ctree.getLabelVaList("newsType");
        request.setAttribute("ctreelist", l);
        return map.findForward("query");
    }

    /**
     * <p>
     * ����ϵͳload
     * </p>
     * 
     * @param info:����ϵͳload
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toArticleLoad(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String type = request.getParameter("type");
        request.setAttribute("opertype", type);
        List l = ctree.getLabelVaList("newsType");
        request.setAttribute("ctreelist", l);
        if (type.equals("insert")) {
            return map.findForward("load");
        }
        if (type.equals("update")) {
            String id = request.getParameter("id");
            IBaseDTO dto = articleService.getArticleInfo(id);
            request.setAttribute(map.getName(), dto);
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            String id = request.getParameter("id");
            IBaseDTO dto = articleService.getArticleInfo(id);
            request.setAttribute(map.getName(), dto);
            return map.findForward("load");
        }
        if (type.equals("putpink")) {
            String id = request.getParameter("id");
            String state = request.getParameter("state");
            // �����Ƽ�����
            if (state.equals("put")) {
                articleService.putPink(state, id);
                // ִ�в���ʱ���ֱ�ҳ��Ϣ
                ActionForward actionforward = new ActionForward(
                        "/news/opernews.do?method=toArticleList&pagestop=pagestop");
                return actionforward;
            } else if (state.equals("unput")) {
                articleService.putPink(state, id);
                ActionForward actionforward = new ActionForward(
                        "/news/opernews.do?method=toArticleList&pagestop=pagestop");
                return actionforward;
            }
        }
        if (type.equals("puttop")) {
            String id = request.getParameter("id");
            String state = request.getParameter("state");
            // �����ö�����
            if (state.equals("put")) {
                articleService.putTop(state, id);
                // ִ�в���ʱ���ֱ�ҳ��Ϣ
                ActionForward actionforward = new ActionForward(
                        "/news/opernews.do?method=toArticleList&pagestop=pagestop");
                return actionforward;
            } else if (state.equals("unput")) {
                articleService.putTop(state, id);
                ActionForward actionforward = new ActionForward(
                        "/news/opernews.do?method=toArticleList&pagestop=pagestop");
                return actionforward;
            }
        }
        return map.findForward("load");
    }

    /**
     * <p>
     * ����ϵͳ��Ϣ��ʾ
     * </p>
     * 
     * @param info:����ϵͳ��Ϣ��ʾ
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toArticleList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String) request.getParameter("pagestate");
        String page = request.getParameter("pagestop");
        
        // ���������б�
        List list = ctree.getLabelVaList("newsType");
        request.setAttribute("ctreelist", list);
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
        List l = articleService.findArticleInfo(formdto, pageInfo);
        int size = articleService.getArticleSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/ETOA/", map, request);
        request.getSession().setAttribute("agropageTurning", pt);
        // request.setAttribute("list",agroMachineService.findArgoMachineInfo(argoSearch.searchHirerOperInfo(qldto)));

        return map.findForward("list");
    }

    /**
     * <p>
     * ����ϵͳ��Ϣ��ʾ
     * </p>
     * 
     * @param info:����ϵͳ��Ϣ��ʾ
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward toRecycleList(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String) request.getParameter("pagestate");
        String page = request.getParameter("pagestop");
        // ���������б�
        List list = ctree.getLabelVaList("newsType");
        request.setAttribute("ctreelist", list);
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
        pageInfo.setPageSize(15);
        List l = articleService.findRecycleInfo(formdto, pageInfo);
        int size = articleService.getRecycleSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list", l);
        PageTurning pt = new PageTurning(pageInfo, "/ETOA/", map, request);
        request.getSession().setAttribute("agropageTurning", pt);
        // request.setAttribute("list",agroMachineService.findArgoMachineInfo(argoSearch.searchHirerOperInfo(qldto)));

        return map.findForward("recyclelist");
    }

    /**
     * <p>
     * �������ϵͳ������Ϣ
     * </p>
     * 
     * @param info:�������ϵͳ������Ϣ
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward operArticle(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String type = request.getParameter("type");
        request.setAttribute("opertype", type);
        List l = ctree.getLabelVaList("newsType");
        request.setAttribute("ctreelist", l);
        if (type.equals("insert")) {
            articleService.addArticleInfo(formdto);
            request.setAttribute("idus_state", "sys.addsuccess");
            return map.findForward("load");
        }
        if (type.equals("update")) {
            articleService.updateArticleInfo(formdto);
            request.setAttribute("idus_state", "sys.updatesuccess");
            return map.findForward("load");
        }
        if (type.equals("delete")) {
            articleService.deleteArticleInfo(formdto);
            request.setAttribute("idus_state", "sys.delsuccess");
            return map.findForward("load");
        }
        return map.findForward("load");
    }

    /**
     * <p>
     * ɾ��������վ,�Ƽ����̶��Ȳ���
     * </p>
     * 
     * @param info:ɾ��������վ,�Ƽ����̶��Ȳ���
     *            0:����ɾ�� 1:�������վ 2:ȫ������Ϊ�Ƽ� 3:ȫ��ȡ���Ƽ� 4:ȫ���̶� 5:ȫ�����
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward operArticleInfo(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String[] str = formdto.getStrings("chk");
        ActionMessages errors = new ActionMessages();
        String type = (String) formdto.get("operator");
        if (type == null || type.equals("")) {
            errors.add("nulloper", new ActionMessage(
                    "agrofront.news.article.articlelist.nullerrors"));
            saveErrors(request, errors);
            return map.findForward("error");
        }
        // �ж��Ƿ�Ϊ��
        if (str.length == 0) {
            errors.add("nullselect", new ActionMessage(
                    "agrofront.news.article.articlelist.nullselect"));
            saveErrors(request, errors);
            return map.findForward("error");
        }
        request.setAttribute("idus_state", "sys.opersuccess");
        articleService.putArticleOper(type, str);
        ActionForward actionforward = new ActionForward(
                "/news/opernews.do?method=toArticleList&pagestop=pagestop");
        return actionforward;
    }

    /**
     * <p>
     * ����վ��Ϣ����
     * </p>
     * 
     * @param info:����վ��Ϣ����
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward operRecycleInfo(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String[] str = formdto.getStrings("chk");
        ActionMessages errors = new ActionMessages();
        if (request.getParameter("oper") == null) {
            errors.add("nullerrors", new ActionMessage(
                    "agrofront.news.article.recyclelist.nullerrors"));
            saveErrors(request, errors);
            return map.findForward("error");
        }
        String type = request.getParameter("oper");
        // ɾ�������ļ�
        if (type.equals("del")) {
            String articleid = request.getParameter("articleid");
            articleService.delNews(articleid, "");
            ActionForward actionforward = new ActionForward(
                    "/news/opernews.do?method=toRecycleList&pagestop=pagestop");
            return actionforward;
        }
        // ɾ��ѡ�����ļ�
        if (type.equals("delselect")) {
            if (str.length == 0) {
                errors.add("nullselect", new ActionMessage(
                        "agrofront.news.article.articlelist.nullselect"));
                saveErrors(request, errors);
                return map.findForward("error");
            }
            articleService.delNews(str);
            ActionForward actionforward = new ActionForward(
                    "/news/opernews.do?method=toRecycleList&pagestop=pagestop");
            return actionforward;
        }
        // ��ջ���վ
        if (type.equals("delall")) {
            articleService.delNews("", "all");
            ActionForward actionforward = new ActionForward(
                    "/news/opernews.do?method=toRecycleList&pagestop=pagestop");
            return actionforward;
        }
        // ��ԭ�����ļ�
        if (type.equals("rev")) {
            String articleid = request.getParameter("id");
            articleService.revertNews(articleid, "");
            ActionForward actionforward = new ActionForward(
                    "/news/opernews.do?method=toRecycleList&pagestop=pagestop");
            return actionforward;
        }
        // ��ԭѡ�����ļ�
        if (type.equals("revselect")) {
            if (str.length == 0) {
                errors.add("nullselect", new ActionMessage(
                        "agrofront.news.article.articlelist.nullselect"));
                saveErrors(request, errors);
                return map.findForward("error");
            }
            articleService.revertNews(str);
            ActionForward actionforward = new ActionForward(
                    "/news/opernews.do?method=toRecycleList&pagestop=pagestop");
            return actionforward;
        }
        // ��ԭ����
        if (type.equals("revall")) {
            articleService.revertNews("", "all");
            ActionForward actionforward = new ActionForward(
                    "/news/opernews.do?method=toRecycleList&pagestop=pagestop");
            return actionforward;
        }
        return null;
    }

    /**
     * <p>
     * �ƶ�ѡ�е��ļ�
     * </p>
     * 
     * @param info:�ƶ�ѡ�е��ļ�
     * 
     * @return:
     * 
     * @throws
     */

    public ActionForward moveAll(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO) form;
        String[] str = formdto.getStrings("chk");
        String classid = (String) formdto.get("classid");
        ActionMessages errors = new ActionMessages();
        if (classid.equals("")) {
            errors.add("pleaseselect", new ActionMessage(
                    "agrofront.news.article.articlelist.pleaseselect"));
            saveErrors(request, errors);
            return map.findForward("error");
        }
        if (str.length == 0) {
            errors.add("nullselect", new ActionMessage(
                    "agrofront.news.article.articlelist.nullselect"));
            saveErrors(request, errors);
            return map.findForward("error");
        }
        request.setAttribute("idus_state", "sys.opersuccess");
        articleService.moveAll(classid, str);
        ActionForward actionforward = new ActionForward(
                "/news/opernews.do?method=toArticleList&pagestop=pagestop");
        return actionforward;
    }
    
    public ActionForward toupload(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return map.findForward("upload");
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public ClassTreeService getCtree() {
        return ctree;
    }

    public void setCtree(ClassTreeService ctree) {
        this.ctree = ctree;
    }

}
