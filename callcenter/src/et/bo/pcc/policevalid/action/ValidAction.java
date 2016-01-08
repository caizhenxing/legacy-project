/**
 * 	@(#)ValidAction.java   Oct 20, 2006 5:13:49 PM
 *	 �� 
 *	 
 */
package et.bo.pcc.policevalid.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.pcc.policevalid.PoliceValidService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.page.PageTurning;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author zhang
 * @version Oct 20, 2006
 * @see
 */
public class ValidAction extends BaseAction{
	
	private PoliceValidService valid = null;
	
	private ClassTreeService depTree=null;
	
	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}

	public PoliceValidService getValid() {
		return valid;
	}

	public void setValid(PoliceValidService valid) {
		this.valid = valid;
	}

	/**
	 * @describe ��ת���û���֤ҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toValid(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        List l = depTree.getLabelVaList("1");
        request.setAttribute("ctreelist", l);
		return map.findForward("valid");
	}
	
	/**
	 * @describe ��֤�û���Ϣ���ɹ���¼���¼
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward validInfo(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		//formdto.set("ip", request.getRemoteAddr());
		//��֤��Ա��Ϣ�Ƿ�ɹ�
		if (valid.validPoliceInfo(formdto)) {
			//valid.addPoliceValidInfo(formdto);
			String dep = depTree.getvaluebyId((String)formdto.get("unit"));
			System.out.println("dep "+dep);
			formdto.set("unit", dep);
			return map.findForward("passvalid");
		}else{
//			String pass = (String)formdto.get("password");
//			if (pass.length()!=6) {
//				request.setAttribute("errors", "et.pcc.policeinfo.policevalid.isnot6");
//			}
			request.setAttribute("errors", "et.pcc.policeinfo.policevalid.inputerror");
			return map.findForward("fail");
		}
	}
	
	/**
	 * @describe �޸����룬�ɹ�¼���¼
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward upInfo(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		formdto.set("ip", request.getRemoteAddr());
		String password = (String)formdto.get("password");
		if (password.length()!=6) {
			request.setAttribute("errors", "et.pcc.policeinfo.policevalid.isnot6");
			return map.findForward("fail");
		}
		for(int i=0;i<password.length();i++){   
			if(password.charAt(i)<'0'||password.charAt(i)>'9'){   
				request.setAttribute("errors", "et.pcc.policeinfo.policevalid.mustnum");
				return map.findForward("fail");
	    	}  
		}
		if (valid.addPoliceValidInfo(formdto)) {
			request.setAttribute(map.getName(), formdto);
			return map.findForward("success");
		}else{
			return map.findForward("fail");
		}
	}
	
	/**
	 * @describe ����֤��ҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toSearchValid(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return map.findForward("main");
	}
	
	/**
	 * @describe ����֤��ҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toQuery(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        List l = depTree.getLabelVaList("1");
        request.setAttribute("ctreelist", l);
		return map.findForward("query");
	}
	
	/**
	 * @describe ��ѯ�б�ҳ��
	 * @param map
	 *            ���� ActionMapping
	 * @param form
	 *            ���� ActionForm
	 * @param request
	 *            ���� HttpServletRequest
	 * @param response
	 *            ���� HttpServletResponse
	 * @return ���� ActionForward
	 * 
	 */
	public ActionForward toValidList(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        DynaActionFormDTO formdto = (DynaActionFormDTO)form;
        String pageState = null;
        PageInfo pageInfo = null;
        pageState = (String)request.getParameter("pagestate");
        if (pageState==null) {
            pageInfo = new PageInfo();
        }else{
            PageTurning pageTurning = (PageTurning)request.getSession().getAttribute("validpageTurning");
            pageInfo = pageTurning.getPage();
            pageInfo.setState(pageState);
            formdto = (DynaActionFormDTO)pageInfo.getQl();
        }
        pageInfo.setPageSize(10);
        List l = valid.infoIndex(formdto, pageInfo);
        int size = valid.getInfoSize();
        pageInfo.setRowCount(size);
        pageInfo.setQl(formdto);
        request.setAttribute("list",l);
        PageTurning pt = new PageTurning(pageInfo,"/callcenter/",map,request);
        request.getSession().setAttribute("validpageTurning",pt);
        return map.findForward("list");
	}
	
}
