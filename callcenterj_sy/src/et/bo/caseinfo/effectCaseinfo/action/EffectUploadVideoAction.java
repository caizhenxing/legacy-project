/**
 * 	@(#)HRUploadAction.java   2006-9-16 ����02:52:01
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
 */
package et.bo.caseinfo.effectCaseinfo.action;

import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import excellence.common.util.Constants;
import et.bo.caseinfo.effectCaseinfo.form.EffectForm;
import et.bo.caseinfo.effectCaseinfo.service.EffectCaseinfoService;
import et.bo.fileBean.FileUtil;
import excellence.framework.base.action.BaseAction;

/**
 * @author ��Ĭ
 * @version 2008-6-4
 * @see
 */
public class EffectUploadVideoAction extends BaseAction
{
	private EffectCaseinfoService ecs = null;

	public EffectCaseinfoService getEcs()
	{
		return ecs;
	}

	public void setEcs(EffectCaseinfoService ecs)
	{
		this.ecs = ecs;
	}

	/**
   * �ϴ���Ƶ
   * @param
   * @version 2006-9-16
   * @return
   */
	public ActionForward uploadVideo(ActionMapping map, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		//System.out.println("EffectUploadVideoAction----->Ч����������ϢuploadVideo()��ʼִ��");
		String type = (String) request.getParameter("type");

		if (type != null && type.equals("page")) return map.findForward("upload");
		EffectForm dto = (EffectForm) form;
		FormFile ff = dto.getFile();
		FileUtil fileUtil = new FileUtil();

		String filename = ff.getFileName();

		InputStream is = ff.getInputStream();
		String id = dto.getId();
		fileUtil.newFile(Constants.getProperty("video_realpath"), filename, is);
		if (id != null && !id.equals("")) ecs.updateVideo(id, Constants.getProperty("employee_photo_webpath")
				+ filename);
		request.setAttribute("path", Constants.getProperty("employee_photo_webpath") + filename);
		request.setAttribute("check", "success");

		request.setAttribute("id", id);
		return map.findForward("uploadVideo");
	}
}
