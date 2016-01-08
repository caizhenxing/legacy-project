/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.news.action;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import et.bo.news.form.UploadFileForm;
import excellence.common.load.LoadService;
import excellence.common.util.time.TimeUtil;

public class UploadFileAction extends Action {

    private LoadService ls = null;



    public LoadService getLs() {
        return ls;
    }



    public void setLs(LoadService ls) {
        this.ls = ls;
    }



    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //UploadFileForm uploadFileForm = (UploadFileForm) form;
        UploadFileForm upform = (UploadFileForm)form;
        FormFile file = upform.getFile();
        HashMap map = new HashMap();
        map.put("gif", new Long(600));
        map.put("jpg", new Long(600));
        map.put("jpeg", new Long(600));
        ls.setFileFormat(map);
        ls.setCount(LoadService.PICTURE, 5);

        // loadService.setUrl("http://192.168.1.9:8089/LoveFront/upload/");
        String subPath="/news/upload";
        ls.setUrl("../upload");
        
        // String url =
        // loadService.saveToLocal(file,t.Time_Article(),"E:/tomcat5/webapps/LoveFront/upload/");
        //String url = loadService.saveToFtp(file, t.Time_Article(),
        //        "upload.9i5i.com", "upload", "upload123456", "/upload");
        String name=TimeUtil.getNowSTime();
        String url = ls.saveToClasspath(file,name,subPath);
     //   String type=file.getContentType();
        request.setAttribute("url",url);
        request.setAttribute("check", "success");
        return mapping.findForward("success");

    }

}
