/**
 * 	@(#)HRUploadAction.java   2006-9-16 下午02:52:01
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.caseinfo.generalCaseinfo.action;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


import et.bo.sad.form.SadForm;
import et.bo.sad.service.SadService;
import excellence.common.util.Constants;
import et.bo.caseinfo.effectCaseinfo.service.EffectCaseinfoService;
import et.bo.caseinfo.generalCaseinfo.form.GeneralForm;
import et.bo.caseinfo.generalCaseinfo.service.GeneralCaseinfoService;
import et.bo.fileBean.FileUtil;
import excellence.framework.base.action.BaseAction;

/**
 * @author 
 * @version 2006-9-16
 * @see
 */
public class GeneraUploadAction extends BaseAction {

	private GeneralCaseinfoService gcs = null;

	/**
     * 上传照片
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
    			GeneralForm dto=(GeneralForm)form;
    			FormFile ff=dto.getFile();
    			FileUtil fileUtil = new FileUtil();

    			String filename = ff.getFileName();
    			//

    			InputStream is = ff.getInputStream();
    			String id=dto.getId();
    			fileUtil.newFile(Constants.getProperty("employee_photo_realpath"), filename, is);
    			if(id!=null&&!id.equals(""))
    			gcs.updatePhoto(id, Constants.getProperty("employee_photo_webpath")+filename);
    			//request.setAttribute("id",id);
    			request.setAttribute("path",Constants.getProperty("employee_photo_webpath")+filename);
    			request.setAttribute("check","success");
    			
    			request.setAttribute("id",id);
    			return map.findForward("upload");
            }





	public GeneralCaseinfoService getGcs() {
		return gcs;
	}


	public void setGcs(GeneralCaseinfoService gcs) {
		this.gcs = gcs;
	}
}
