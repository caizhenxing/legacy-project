/**
 * 	@(#)HRUploadAction.java   2008-06-10
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.schema.fixedContact.action;

import java.io.File;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import et.bo.fileBean.FileUtil;
import et.bo.schema.fixedContact.form.FixedContactUploadForm;
import et.bo.schema.fixedContact.upload.service.impl.CheckUploadService;
import excellence.common.util.Constants;
import excellence.framework.base.action.BaseAction;

/**
 * @version 2008-06-10
 * @author ��Ĭ
 */
public class FixedContactUploadAction extends BaseAction
{
	private CheckUploadService cus;

	/**
   * �ϴ���Ƭ
   * @param
   * @version 2008-06-10
   * @return
   */
	public ActionForward upload(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		//System.out.println("FixedContactUploadAction----->�̶�����Ա�ϴ�ͼƬ��upload()��ʼִ��");
		FixedContactUploadForm fcuf = (FixedContactUploadForm) form;

		FormFile ff = fcuf.getFile();
		FileUtil fileUtil = new FileUtil();
		String filename = ff.getFileName();
		//System.out.println("FixedContactUploadAction----->�ϴ����ļ����ǣ�"+ filename);
		if (filename != null && !"".equals(filename))
		{
			String picFilePath = Constants.getProperty("fixedContact_pic_realpath");
			// System.out.println("�ϴ����ļ�������" +
      // picFilePath + "Ŀ¼��");

			String oldFileName = null;// ���ļ�������
			oldFileName = filename;
			// System.out.println("���ļ����ǣ�" +
      // oldFileName);

			String newFileName = null;// ���ļ�������

			int index = filename.lastIndexOf(".");
			filename = filename.substring(index);// ȡ��.��׺��
			/**
       * ����ϴ����ļ���c, '.jsp'
       */
			int checkFile = cus.checkFileSuffix(filename);
			//System.out.println("FixedContactUploadAction----->�ϴ����ļ���׺�ǣ�"+ checkFile);
			if (checkFile == 1)// �����׺��Ϊ".exe", ".com",
                          // ".cgi", ".jsp"֮һ
			{
				request.setAttribute("check", "errorType");
				return map.findForward("upload");
			}

			newFileName = new Long(System.currentTimeMillis()).toString() + filename;// ���ļ���������������
			// System.out.println("���ļ����ǣ�" +
      // newFileName);

			String oldPicPath = picFilePath + "/" + oldFileName;// ���ļ��ľ���·��
			// System.out.println("���ļ�����������·���ǣ�" +
      // oldPicPath);

			String newPicPath = picFilePath + "/" + newFileName;// ���ļ��ľ���·��
			// System.out.println("���ļ�����������·���ǣ�" +
      // newPicPath);

			File oldPicFile = new File(oldPicPath);
			File newPicFile = new File(newPicPath);
			oldPicFile.renameTo(newPicFile);
			// �Ѿ��ļ����浽�������ϵĴ��̺��ٽ����ļ�������
			InputStream is = ff.getInputStream();
			long v = 0;
			v = fileUtil.newFile(picFilePath, oldFileName, is);// �����ļ������ļ�д�����
			if (oldPicFile.exists() == true)
			{
				cus.renameFile(picFilePath, oldFileName, newFileName);// ���ļ�������Ϊ������Ϊǰ׺��
				// System.out.println("�ϴ�ͼƬ�������");
			}
			if (v > 0)// ���ͼƬ�ļ��ѱ����浽����
			{
				request.setAttribute("check", "success");
				HttpSession session = request.getSession();
				session.setAttribute("picName", newFileName);// ���ϴ���ͼƬ���Ʊ�����session��
				session.setAttribute("picPath", picFilePath);// ���ϴ���ͼƬ·��������session��
			}
		}
		else request.setAttribute("check", "fail");
		return map.findForward("upload");
	}

	public CheckUploadService getCus()
	{
		return cus;
	}

	public void setCus(CheckUploadService cus)
	{
		this.cus = cus;
	}
}
