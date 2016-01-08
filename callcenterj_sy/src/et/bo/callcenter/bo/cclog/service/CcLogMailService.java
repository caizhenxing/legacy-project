/**
 * 	@(#)AgroKonwledgeService.java   2007-4-5 下午08:52:26
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.callcenter.bo.cclog.service;

import et.bo.callcenter.bo.cclog.bean.CcLogMailBoxBean;

/**
 * @describe CcLog日志添加
 * @param
 * @version 2007-7-26
 * @return
 */
public interface CcLogMailService {
	
	public void addCcLogMail(CcLogMailBoxBean ccLog);
	
}
