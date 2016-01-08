/**
 * 	@(#)HRUploadAction.java   2006-9-16 下午02:52:01
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.caseinfo.effectCaseinfo.action;

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
import et.bo.caseinfo.effectCaseinfo.form.EffectForm;
import et.bo.caseinfo.effectCaseinfo.service.EffectCaseinfoService;
import et.bo.fileBean.FileUtil;
import excellence.framework.base.action.BaseAction;

/**
 * @author 
 * @version 2006-9-16
 * @see
 */
public class EffectUploadAction extends BaseAction {

	private EffectCaseinfoService ecs = null;



	public EffectCaseinfoService getEcs() {
		return ecs;
	}



	public void setEcs(EffectCaseinfoService ecs) {
		this.ecs = ecs;
	}



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
    			EffectForm dto=(EffectForm)form;
    			FormFile ff=dto.getFile();
    			FileUtil fileUtil = new FileUtil();

    			String filename = ff.getFileName();
    			//

    			InputStream is = ff.getInputStream();
    			String id=dto.getId();
    			fileUtil.newFile(Constants.getProperty("employee_photo_realpath"), filename, is);
    			if(id!=null&&!id.equals(""))
    				ecs.updatePhoto(id, Constants.getProperty("employee_photo_webpath")+filename);
    			//request.setAttribute("id",id);
    			request.setAttribute("path",Constants.getProperty("employee_photo_webpath")+filename);
    			request.setAttribute("check","success");
    			
    			request.setAttribute("id",id);
    			return map.findForward("upload");
            }
}
