/**
 * 	@(#)HRForm.java   2008-06-10
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
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
