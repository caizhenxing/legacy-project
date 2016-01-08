package et.bo.oa.communicate.email.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import et.bo.oa.communicate.email.form.UploadFileForm;
import excellence.common.load.LoadService;
import excellence.common.util.Constants;
import excellence.common.util.time.TimeUtil;

public class UploadInAction extends Action {
	
	private LoadService loadService = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		UploadFileForm upform = (UploadFileForm) form;
		FormFile file = upform.getFile();
		HashMap map = new HashMap();
		map.put("gif", new Long(600));
		map.put("jpg", new Long(600));
		map.put("jpeg", new Long(600));
		loadService.setFileFormat(map);
		loadService.setCount(LoadService.PICTURE, 5);
		
        List l = null;
        if (request.getSession().getAttribute("inUploadList")==null) {
            l = new ArrayList();
        }else{
            l = (List)request.getSession().getAttribute("inUploadList");
        }
        
        if (loadService.isLegality(l,file)==false) {
            request.setAttribute("check","fail");
            return mapping.findForward("success");
        }else{
    		
    		String subPath = Constants.getProperty("email_upload_in_subpath");
    		String uploadFinalPath = Constants.getProperty("email_upload_in_uploadfinish_path");

    		loadService.setUrl(uploadFinalPath);
    		

    		String name = TimeUtil.getNowSTime();
    		String url = loadService.saveToClasspath(file, name, subPath);

    		l.add(url);
    		request.getSession().setAttribute("inUploadList",l);
    		request.setAttribute("check", "success");
    		return mapping.findForward("success");
        }
        
	}

	public LoadService getLoadService() {
		return loadService;
	}

	public void setLoadService(LoadService loadService) {
		this.loadService = loadService;
	}
}
