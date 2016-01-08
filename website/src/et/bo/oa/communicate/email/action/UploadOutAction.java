package et.bo.oa.communicate.email.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import et.bo.oa.communicate.email.form.UploadFileForm;
import excellence.common.util.Constants;
import excellence.common.util.file.FileUtil;

public class UploadOutAction extends Action {

	private String UPLOAD_PATH = Constants
			.getProperty("outemail_upload_out_localpath");

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UploadFileForm upform = (UploadFileForm) form;
		FormFile file = upform.getFile();

		List l = null;
		if (request.getSession().getAttribute("outUploadList") == null) {
			l = new ArrayList();
		} else {
			l = (List) request.getSession().getAttribute("outUploadList");
		}
		// 上传到服务器上的指定路径
		FileUtil fileUtil = new FileUtil();
		String filename = file.getFileName();
		InputStream is = file.getInputStream();
		fileUtil.newFile(UPLOAD_PATH, filename, is);

		l.add(filename);
		request.getSession().setAttribute("outUploadList", l);
		request.setAttribute("check", "success");
		return mapping.findForward("success");

	}
}
