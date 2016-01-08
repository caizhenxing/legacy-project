/*
 * @(#)CustinfoAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.sys.playVoice.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.test.tts.demo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.tools.LabelValueBean;
import excellence.common.tree.base.service.TreeControlNodeService;
import excellence.common.tree.ext.view.impl.ViewTreeControlNode;
import excellence.common.util.Constants;
import excellence.framework.base.action.BaseAction;
/**
 * <p>语音播放相关action</p>
 * 
 * @version 2008-03-28
 * @author 王文权
 */

public class PlayVoiceAction extends BaseAction {
	
	static Logger log = Logger.getLogger(PlayVoiceAction.class.getName());
	private ClassTreeService classTreeService = null;

	/**
	 * 将字符转换成语音文件
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 转换成功页
	 */
	public ActionForward toCharacter2VoiceFile(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		String affixTxt = request.getParameter("affixTxt");
		if(affixTxt!=null&&!"".equals(affixTxt.trim()))
		{
			//do change character to voiceFile
			demo d = new demo();
			String filePath = null;
			filePath = null;
			filePath = d.playVoice(affixTxt,Constants.getProperty("tts_ip"));
			//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//			if(filePath!=null&&filePath.length()>1)
//			{
//				filePath = "E"+filePath.substring(1);
//			}
			request.setAttribute("filePath", filePath);
		}
		
		return map.findForward("changeFileSuccess");
	}
	/**
	 * 将字符转换成语音文件
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward 转换成功页
	 */
	public ActionForward toSelectIvrFile(ActionMapping map, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
		ViewTreeControlNode vNode = (ViewTreeControlNode)classTreeService.getNodeByNickName("IVRMultiVoice");
		List<TreeControlNodeService> cNodes = vNode.getChildren();
		List<LabelValueBean> lvList = new ArrayList<LabelValueBean>();
		for(int i=0; i<cNodes.size(); i++)
		{
			ViewTreeControlNode cNode = (ViewTreeControlNode)cNodes.get(i);
			LabelValueBean lv = new LabelValueBean();
			lv.setLabel(cNode.getBaseTreeNodeService().getNickName());
			lv.setValue(cNode.getLabel());
			lvList.add(lv);
		}
		request.setAttribute("lvList", lvList);
		return map.findForward("toSelectIvrFile");
	}
	
	public ClassTreeService getClassTreeService() {
		return classTreeService;
	}
	public void setClassTreeService(ClassTreeService classTreeService) {
		this.classTreeService = classTreeService;
	}
	
}
