package et.bo.sys.ivr.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.callinfirewall.service.CallinFirewallService;
import et.bo.sys.ivr.service.BusinessObjectService;
import et.bo.sys.user.service.UserService;
import excellence.common.classtree.ClassTreeService;
import excellence.framework.base.dao.BaseDAO;

public class BusinessObjectImpl implements BusinessObjectService {

	private static Log log = LogFactory.getLog(BusinessObjectImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.callcenter.business.BusinessObject#blacklist(java.lang.String)
	 */
	private CallinFirewallService callinFirewallService = null;

	private BaseDAO dao = null;

	private UserService userService = null;

	private ClassTreeService cts;

	public String blacklist(String phoneNum) {
		// TODO Auto-generated method stub
		Boolean flag = callinFirewallService.IfInBlacklist(phoneNum);
		if (flag) {
			return FAIL_DEFAULT;// 电话不在黑名单内
		} else {
			return SUCCEED;// 电话在黑名单内
		}
	}

	public CallinFirewallService getCallinFirewallService() {
		return callinFirewallService;
	}

	public void setCallinFirewallService(
			CallinFirewallService callinFirewallService) {
		this.callinFirewallService = callinFirewallService;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

}
