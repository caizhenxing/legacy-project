package et.bo.common.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

import et.bo.forum.common.UserList;

public class ForumRightAction extends ForwardAction {

	public ActionForward execute(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		String userkey=(String)request.getSession().getAttribute("userkey");
		
		ForumRight fr=new ForumRight();
		Long l=fr.getRight(userkey);
		UserList.setUser("terry",5);
		request.getSession().setAttribute("forumRight",l);
		return super.execute(mapping,form,request,response);
    }
	
}
