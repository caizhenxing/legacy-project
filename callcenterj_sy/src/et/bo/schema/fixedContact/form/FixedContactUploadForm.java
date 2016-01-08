/**
 * 	@(#)HRForm.java   2008-06-10
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.schema.fixedContact.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author
 * @version 2006-9-16
 * @see
 */
public class FixedContactUploadForm extends ActionForm
{
	private FormFile file;

	public FormFile getFile()
	{
		return file;
	}

	public void setFile(FormFile file)
	{
		this.file = file;
	}
}
