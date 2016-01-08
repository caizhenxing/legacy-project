package et.bo.forum.replaceManager.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import et.bo.forum.replaceManager.service.ReplaceManagerService;
import excellence.framework.base.action.BaseAction;
import excellence.framework.base.dto.impl.DynaActionFormDTO;

public class ReplaceManagerAction extends BaseAction {
	    static Logger log = Logger.getLogger(ReplaceManagerAction.class.getName());
	    
	    private ReplaceManagerService replaceManagerService = null;
		/**
		 * @describe Ìû×Ó´ÊÓï¹ýÂËÒ³
		 */
		public ActionForward toReplace(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
			String rules = replaceManagerService.getRule();
			dto.set("ruleArray", rules);
			request.setAttribute(map.getName(),dto);
			return map.findForward("toReplace");
		}
		
		/**
		 * @describe Ìû×Ó´ÊÓï¹ýÂË²Ù×÷
		 */
		public ActionForward toOperReplace(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response) throws Exception {
			DynaActionFormDTO dto = (DynaActionFormDTO) form;
			String ruleArray = (String)dto.get("ruleArray");
			try {
				replaceManagerService.addRule(ruleArray);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return map.findForward("error");
			}
			return map.findForward("success");
		}
		
		public ReplaceManagerService getReplaceManagerService() {
			return replaceManagerService;
		}
		public void setReplaceManagerService(ReplaceManagerService replaceManagerService) {
			this.replaceManagerService = replaceManagerService;
		}
					
}
