package ocelot.framework.base.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;








 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public class BaseAction extends DispatchAction {

	
	private static Log log = LogFactory.getLog(BaseAction.class);
	public final ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // TODO Auto-generated method stub
		HttpSession session=request.getSession();
		if(session==null)
			return mapping.findForward("sessionTimeOut");
        this.before(mapping,form,request,response);
        ActionForward a = super.execute(mapping,form,request,response);
        this.after(mapping,form,request,response);
        return a;
    }
	/*
	 * 原则上，返回的一些信息写在request或者session中。
	 */
	 protected void before(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	    {
	        ;
	    }
	 /*
		 * 原则上，返回的一些信息写在request或者session中。
		 */
	 protected void after(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	    {
	        ;
	    }
}
