//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package et.bo.news.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * MyEclipse Struts Creation date: 10-22-2005
 * 
 * XDoclet definition:
 * 
 * @struts:form name="uploadFileForm"
 */
public class UploadFileForm extends ActionForm {

    // --------------------------------------------------------- Instance
    // Variables

    // --------------------------------------------------------- Methods

    private FormFile file;

    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    private String fname;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Method validate
     * 
     * @param mapping
     * @param request
     * @return ActionErrors
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        // TODO Auto-generated method stub
//        ActionErrors errors = new ActionErrors();
//        if (this.getFile().getContentType().equals("jpg")) {
//            errors.add("notimage",new ActionError("error.notimage"));
//        }
//        if (this.getFile().getFileSize()>1024*1024) {
//            errors.add("notlength",new ActionError("error.notlength"));
//        }
//        return errors;
        return null;
    }

    /**
     * Method reset
     * 
     * @param mapping
     * @param request
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        // TODO Auto-generated method stub
    }

}
