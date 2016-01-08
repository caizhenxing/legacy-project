package et.bo.forum.controlPanel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.forum.controlPanel.service.ControlPanelService;
import et.bo.forum.point.service.PointService;
import et.bo.forum.userInfo.service.UserInfoService;
import et.bo.sys.common.SysStaticParameter;
import et.bo.sys.login.UserInfo;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.IBaseDTO;

public class ControlPanelAction extends BaseAction {
	    static Logger log = Logger.getLogger(ControlPanelAction.class.getName());
	    
	    private PointService pointService = null;
	    
	    private ControlPanelService controlPanelService = null;
	    
	    private UserInfoService userInfoService = null;
		/**
		 * @describe 前台模块查询列表
		 */
		public ActionForward toMain(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			UserInfo ui=(UserInfo)request.getSession().getAttribute(SysStaticParameter.USER_IN_SESSION);
	        String userId = ui.getUserName();
	        IBaseDTO dto=null;
			try {
				dto = userInfoService.getUserInfo(userId);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//	        String point = pointService.getUserPiont(userId);
//	        String userLevel = pointService.getUserLevel(Integer.parseInt(point));
//	        request.setAttribute("userPoint", point);
//	        request.setAttribute("userLevel", userLevel);
	        request.setAttribute("userInfo", dto);
			return map.findForward("main");
		}
		public ControlPanelService getControlPanelService() {
			return controlPanelService;
		}
		public void setControlPanelService(ControlPanelService controlPanelService) {
			this.controlPanelService = controlPanelService;
		}
		public PointService getPointService() {
			return pointService;
		}
		public void setPointService(PointService pointService) {
			this.pointService = pointService;
		}
		public UserInfoService getUserInfoService() {
			return userInfoService;
		}
		public void setUserInfoService(UserInfoService userInfoService) {
			this.userInfoService = userInfoService;
		}
					
}
