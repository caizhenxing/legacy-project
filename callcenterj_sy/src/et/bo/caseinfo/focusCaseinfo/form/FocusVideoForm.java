/**
 * 	@(#)HRForm.java   2006-9-16 下午02:56:20
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.caseinfo.focusCaseinfo.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author
 * @version 2006-9-16
 * @see
 */
public class FocusVideoForm extends ActionForm
{
	private FormFile file;

	private String	 id;

	public FormFile getFile()
	{
		return file;
	}

	public void setFile(FormFile file)
	{
		this.file = file;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
