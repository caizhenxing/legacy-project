/**
 * 	@(#)HRUploadAction.java   2008-06-10
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
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
 * @author 王默
 */
public class FixedContactUploadAction extends BaseAction
{
	private CheckUploadService cus;

	/**
   * 上传照片
   * @param
   * @version 2008-06-10
   * @return
   */
	public ActionForward upload(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		//System.out.println("FixedContactUploadAction----->固定联络员上传图片的upload()开始执行");
		FixedContactUploadForm fcuf = (FixedContactUploadForm) form;

		FormFile ff = fcuf.getFile();
		FileUtil fileUtil = new FileUtil();
		String filename = ff.getFileName();
		//System.out.println("FixedContactUploadAction----->上传的文件名是："+ filename);
		if (filename != null && !"".equals(filename))
		{
			String picFilePath = Constants.getProperty("fixedContact_pic_realpath");
			// System.out.println("上传的文件保存在" +
      // picFilePath + "目录下");

			String oldFileName = null;// 旧文件的名字
			oldFileName = filename;
			// System.out.println("旧文件名是：" +
      // oldFileName);

			String newFileName = null;// 新文件的名字

			int index = filename.lastIndexOf(".");
			filename = filename.substring(index);// 取得.后缀名
			/**
       * 检查上传的文件名c, '.jsp'
       */
			int checkFile = cus.checkFileSuffix(filename);
			//System.out.println("FixedContactUploadAction----->上传的文件后缀是："+ checkFile);
			if (checkFile == 1)// 如果后缀名为".exe", ".com",
                          // ".cgi", ".jsp"之一
			{
				request.setAttribute("check", "errorType");
				return map.findForward("upload");
			}

			newFileName = new Long(System.currentTimeMillis()).toString() + filename;// 把文件名用日期来命名
			// System.out.println("新文件名是：" +
      // newFileName);

			String oldPicPath = picFilePath + "/" + oldFileName;// 旧文件的绝对路径
			// System.out.println("旧文件的完整保存路径是：" +
      // oldPicPath);

			String newPicPath = picFilePath + "/" + newFileName;// 新文件的绝对路径
			// System.out.println("新文件的完整保存路径是：" +
      // newPicPath);

			File oldPicFile = new File(oldPicPath);
			File newPicFile = new File(newPicPath);
			oldPicFile.renameTo(newPicFile);
			// 把旧文件保存到服务器上的磁盘后再将旧文件重命名
			InputStream is = ff.getInputStream();
			long v = 0;
			v = fileUtil.newFile(picFilePath, oldFileName, is);// 将旧文件名的文件写入磁盘
			if (oldPicFile.exists() == true)
			{
				cus.renameFile(picFilePath, oldFileName, newFileName);// 将文件重命名为以日期为前缀的
				// System.out.println("上传图片处理完毕");
			}
			if (v > 0)// 如果图片文件已被保存到磁盘
			{
				request.setAttribute("check", "success");
				HttpSession session = request.getSession();
				session.setAttribute("picName", newFileName);// 把上传的图片名称保存在session里
				session.setAttribute("picPath", picFilePath);// 把上传的图片路径保存在session里
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
