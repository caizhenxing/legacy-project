/**
 * 	@(#)ControlPanelServiceImpl.java   2006-12-19 œ¬ŒÁ04:03:01
 *	 °£ 
 *	 
 */
package et.bo.forum.controlPanel.service.impl;

import et.bo.forum.controlPanel.service.ControlPanelService;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;

/**
 * @author “∂∆÷¡¡
 * @version 2006-12-19
 * @see
 */
public class ControlPanelServiceImpl implements ControlPanelService {
	
	private BaseDAO dao = null;

	private KeyService ks = null;
	
	private int num = 0;

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

}
