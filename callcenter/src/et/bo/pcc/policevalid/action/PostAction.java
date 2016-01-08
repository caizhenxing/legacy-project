/**
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
import et.bo.pcc.policevalid.PostService;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

/**
 * @author Administrator
 *
 */
public class PostAction extends BaseAction{
	
	private PostService post = null;
	
	private ClassTreeService depTree=null;
	
	public ClassTreeService getDepTree() {
		return depTree;
	}

	public void setDepTree(ClassTreeService depTree) {
		this.depTree = depTree;
	}


	/**
	 * @describe 跳转到用户验证页面
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
	public ActionForward toValid(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        List l = depTree.getLabelVaList("1");
        request.setAttribute("ctreelist", l);
		return map.findForward("valid");
	}
	
	/**
	 * @describe 验证用户信息，成功则录入记录
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
	public ActionForward validInfo(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionFormDTO formdto = (DynaActionFormDTO) form;
		if (post.validPoliceInfo(formdto).equals("0")) {
			String dep = depTree.getvaluebyId((String)formdto.get("unit"));
			formdto.set("unit", dep);
			return map.findForward("passvalid");
		}else if(post.validPoliceInfo(formdto).equals("1")){
			request.setAttribute("errors", "et.pcc.phoneinfo.check.haslogin");
			return map.findForward("fail");
		}else if(post.validPoliceInfo(formdto).equals("2")){
			request.setAttribute("errors", "et.pcc.policeinfo.policevalid.inputerror");
			return map.findForward("fail");
		}else if(post.validPoliceInfo(formdto).equals("")){
			request.setAttribute("errors", "et.pcc.policeinfo.policevalid.inputerror");
			return map.findForward("fail");
		}
		return map.findForward("fail");
	}
	
	/**
	 * @describe 修改密码，成功录入记录
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
		if (post.addPoliceValidInfo(formdto)) {
			request.setAttribute(map.getName(), formdto);
			return map.findForward("success");
		}else{
			return map.findForward("fail");
		}
	}

	public PostService getPost() {
		return post;
	}

	public void setPost(PostService post) {
		this.post = post;
	}
}
