/**
 * 	@(#)AgroKonwledgeService.java   2007-4-5 下午08:52:26
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.callcenter.bo.cclog.service;

import et.bo.callcenter.serversocket.panel.impl.CtiBean;

/**
 * CclogMain表的增删改
 *
 * @version 2008-05-06 
 * @author 王文权
 */
public interface CcMainService {
	/**
	 * 将CtiBean转换成CcMain插入数据库中
	 * @param ctiBean
	 */
	void addCcLogMain(CtiBean ctiBean);
	/**
	 * 将ctiBean转换成CcMain修改数据库
	 * @param ctiBean
	 */
	void updateCcLogMain(CtiBean ctiBean);
}
