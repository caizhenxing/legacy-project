/**
 * 	@(#)HRForm.java   2006-9-16 ÏÂÎç02:56:20
 *	 ¡£ 
 *	 
 */
package et.bo.oa.assissant.hr.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author 
 * @version 2006-9-16
 * @see
 */
public class HRForm extends ActionForm{

	 private FormFile file;
	 private String id;
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
