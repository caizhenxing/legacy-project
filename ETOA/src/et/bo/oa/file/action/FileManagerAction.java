/**
 * 	@(#)FileManagerAction.java   2006-9-4 下午03:06:45
 *	 。 
 *	 
 */
package et.bo.oa.file.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.oa.file.service.FileManagerService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.common.tree.TreeControlI;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author zhaoyifei
 * @version 2006-9-4
 * @see
 */
public class FileManagerAction extends BaseAction {

	private FileManagerService fms=null;

	public FileManagerService getFms() {
		return fms;
	}

	public void setFms(FileManagerService fms) {
		this.fms = fms;
	}
	/**
	 * 返回文件信息，返回 操作权限
	 * @param
	 * @version 2006-9-6
	 * @return
	 */
	public ActionForward load(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
            {
				UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
				String user=ui.getUserName();
				String id=request.getParameter("id");
				try
				{
				IBaseDTO dto=fms.loadFileInfo(id, user);
				if(dto==null)
					return mapping.findForward("noright");
				request.setAttribute(mapping.getName(), dto);
				return mapping.findForward("info");
				}catch(Exception e)
				{
					return mapping.findForward("error");
				}
            }
	/**
	 * forward首页
	 * @param
	 * @version 2006-9-6
	 * @return
	 */
	public ActionForward main(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
            {
				try
				{
				TreeControlI tci=fms.loadFolders();
				request.getSession().setAttribute("fileSession",tci.getTreeControl());
				return mapping.findForward("main");
				}catch(Exception e)
				{
					
					return mapping.findForward("error");
				}
				
            }
	/**
	 * 新建文件夹或文件
	 * 修改文件，更新文件
	 * @param
	 * @version 2006-9-8
	 * @return
	 */
	public ActionForward newfolder(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
            {
				IBaseDTO dto=(IBaseDTO)form;
				try
				{
					fms.addFile(dto);
					return mapping.findForward("main");
				}catch(Exception e)
				{
					return mapping.findForward("error");
				}
				
            }
	/**
	 * 恢复上版本
	 * @param
	 * @version 2006-9-8
	 * @return
	 */
	public ActionForward resume(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
            {
				String id=request.getParameter("id");
				try
				{
					fms.resumeFile(id);
					return mapping.findForward("success");
				}catch(Exception e)
				{
					return mapping.findForward("error");
				}
				
            }
	/**
	 * 下载文件
	 * @param
	 * @version 2006-9-8
	 * @return
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
            {
				String id=request.getParameter("id");
				try
				{
					fms.download(response, id);
					return mapping.findForward("success");
				}catch(Exception e)
				{
					return mapping.findForward("error");
				}
				
            }
	/**
	 * 授权
	 * @param
	 * @version 2006-9-8
	 * @return
	 */
	public ActionForward accredit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
            {
				IBaseDTO dto=(IBaseDTO)form;
				try
				{
					fms.accredit(dto);
					return mapping.findForward("success");
				}catch(Exception e)
				{
					return mapping.findForward("error");
				}
				
            }
	/**
	 * 审批文件
	 * @param
	 * @version 2006-9-9
	 * @return
	 */
	public ActionForward checkFile(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception 
            {
				String id=request.getParameter("id");
				String pass=request.getParameter("pass");
				UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
				String user=ui.getUserName();
				try
				{
					
					fms.checkFile(id, user, Boolean.getBoolean(pass));
					return mapping.findForward("success");
				}catch(Exception e)
				{
					return mapping.findForward("error");
				}
				
            }

}
