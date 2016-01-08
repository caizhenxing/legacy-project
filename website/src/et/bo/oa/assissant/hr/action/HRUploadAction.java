/**
 * 	@(#)HRUploadAction.java   2006-9-16 ÏÂÎç02:52:01
 *	 ¡£ 
 *	 
 */
package et.bo.oa.assissant.hr.action;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import et.bo.oa.assissant.hr.form.HRForm;
import et.bo.oa.assissant.hr.service.HRService;
import excellence.common.util.Constants;
import excellence.common.util.file.FileUtil;
import excellence.framework.base.action.BaseAction;

/**
 * @author 
 * @version 2006-9-16
 * @see
 */
public class HRUploadAction extends BaseAction {

	private HRService hrService = null;
	public HRService getHrService() {
		return hrService;
	}
	public void setHrService(HRService hrService) {
		this.hrService = hrService;
	}
	/**
     * ÉÏ´«ÕÕÆ¬
     * @param
     * @version 2006-9-16
     * @return
     */
    public ActionForward upload(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception 
            {
    			String type=(String)request.getParameter("type");
    			
    			if(type!=null&&type.equals("page"))
    			return map.findForward("upload");
    			HRForm dto=(HRForm)form;
    			FormFile ff=dto.getFile();
    			FileUtil fileUtil = new FileUtil();

    			String filename = ff.getFileName();
    			//

    			InputStream is = ff.getInputStream();
    			String id=dto.getId();
    			fileUtil.newFile(Constants.getProperty("employee_photo_realpath"), filename, is);
    			if(id!=null&&!id.equals(""))
    			hrService.updatePhoto(id, Constants.getProperty("employee_photo_webpath")+filename);
    			//request.setAttribute("id",id);
    			request.setAttribute("path",Constants.getProperty("employee_photo_webpath")+filename);
    			request.setAttribute("check","success");
    			return map.findForward("upload");
            }
}
