package et.bo.oa.assissant.document.action;


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

import et.bo.oa.assissant.document.config.TimeStamp;
import et.bo.oa.assissant.document.form.UploadFileForm;
import excellence.common.load.LoadService;

/**
 * MyEclipse Struts Creation date: 10-22-2005
 * 
 * XDoclet definition:
 * 
 * @struts:action path="/uploadFile" name="uploadFileForm"
 *                input="/form/uploadFile.jsp" scope="request" validate="true"
 */
public class DocUploadFileAction extends Action {

    // --------------------------------------------------------- Instance
    // Variables

    // --------------------------------------------------------- Methods
    private LoadService loadService;

    /**
     * 上传文件处理
     * Method execute
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        UploadFileForm uploadFileForm = (UploadFileForm) form;
//        
        FormFile file = uploadFileForm.getFile();
        HashMap map = new HashMap();
//        map.put("txt",new Long(2048));
        map.put("doc",new Long(2048));
//        map.put("xls",new Long(2048));
        loadService.setFileFormat(map);
        loadService.setCount(LoadService.OTHER,1);
        List l = null;
        if (request.getSession().getAttribute("uploadList")==null) {
            l = new ArrayList();
        }else{
            l = (List)request.getSession().getAttribute("uploadList");
        }
        if (uploadFileForm == null) {
            return mapping.findForward("uploadfail");
        }
        
        if (loadService.isLegality(l,file)==false) {
            request.setAttribute("check","fail");
            return mapping.findForward("success");
        }else{
            //loadService.setUrl("http://192.168.1.9:8089/LoveFront/upload/");
//            loadService.setUrl("http://disco.9i5i.com/upload/");
//            loadService.setUrl("http://upload.9i5i.com/upload");
//        	loadService.setUrl(StaticParameter.docUploadPath);
//        	loadService.setUrl("E:/Tomcat/upload");
        	String subPath = "/upload";
        	loadService.setUrl("../../upload");
            TimeStamp t = new TimeStamp();
            //String url = loadService.saveToLocal(file,t.Time_Article(),"E:/tomcat5/webapps/LoveFront/upload/");
//            String url = loadService.saveToFtp(file,t.Time_Article(),"218.25.68.143:21","administrator","topglory2005well","disco/upload");
//            String url = loadService.saveToFtp(file,t.Time_Article(),"upload.9i5i.com","upload","upload123456","/upload");
//            String url = loadService.saveToLocal(file,t.Time_Article(),"E:/Tomcat/upload");
            String url = loadService.saveToClasspath(file, t.Time_Article(), subPath);
            l.add(url);
            request.setAttribute("check","success");
            request.getSession().setAttribute("uploadList",l);
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
