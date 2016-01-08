package et.bo.oa.communicate.instant.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.communicate.instant.service.InstantMsgService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.util.regex.AnalyseString;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;



public class InstantMsgAction extends BaseAction {

	private InstantMsgService ims=null;
	public ActionForward hasMsg(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		
		String receiveUser=null;
		UserInfo ub=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		
		
		receiveUser=ub.getUserName();
		boolean has=ims.hasMsg(receiveUser);
		if(has)
		{
			request.setAttribute("hasMsg","yes");
			
			request.setAttribute("flash","/ETOA/images/im_2.gif");
		}
		else
		{
			request.setAttribute("hasMsg","no");
		request.setAttribute("flash","/ETOA/images/im_1.gif");
		}
		return mapping.findForward("top");
		
    }
	public ActionForward receiveMsg(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String receiveUser=null;
		UserInfo ub=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		receiveUser=ub.getUserName();
		
		IBaseDTO im=ims.receiveMsg(receiveUser,true);
		request.setAttribute(mapping.getName(),im);
		return mapping.findForward("detail");
		
    }
	public ActionForward sendMsg(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO daf=(IBaseDTO)form;
		try
		{
		List<String> receives=AnalyseString.parseString((String)daf.get("receivers"),",");
		String sendUser=null;
		UserInfo ub=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
		sendUser=ub.getUserName();
		ims.sendMsg(receives,(String)daf.get("contents"),sendUser);
		}catch(Exception e)
		{
			e.printStackTrace();
			return mapping.findForward("error");
		}
		return mapping.findForward("success");
		
    }
	public ActionForward send(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		IBaseDTO dto=(IBaseDTO)form;
		IBaseDTO dto1=new DynaBeanDTO();
		dto1.set("receivers",dto.get("sender"));
		dto1.set("contents",dto.get("contents"));
		request.setAttribute(mapping.getName(),dto1);
		return mapping.findForward("new");
		
    }
	public ActionForward userList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
    {
		String receivers=request.getParameter("receivers");
		List ul=ims.userList();
		List<IBaseDTO> ul2=new ArrayList<IBaseDTO>();
		StringTokenizer st=new StringTokenizer(receivers,",");
		List<String> users=new ArrayList<String>();
		while(st.hasMoreElements())
		{
			String s=(String)st.nextElement();
			users.add(s);
		}
		Iterator<IBaseDTO> i=ul.iterator();
		
		while(i.hasNext())
		{
			IBaseDTO dto=i.next();
			String id=(String)dto.get("id");
			if(users.contains(id))
			{
				ul2.add(dto);
				ul.remove(dto);
			}
		}
		
		request.setAttribute("userList",ul);
		request.setAttribute("userList2",ul2);
		return mapping.findForward("selectUser");
		
    }
	public InstantMsgService getIms() {
		return ims;
	}
	public void setIms(InstantMsgService ims) {
		this.ims = ims;
	}
	
}
