/**
 * 	@(#)ForumLogServiceImpl.java   2007-1-8 …œŒÁ09:49:39
 *	 °£ 
 *	 
 */
package et.bo.forum.log.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.forum.log.service.ForumLogService;
import et.bo.forum.point.service.PointService;
import et.po.ForumLog;
import et.po.SysModule;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author “∂∆÷¡¡
 * @version 2007-1-8
 * @see
 */
public class ForumLogServiceImpl implements ForumLogService {
	
    int num = 0;
	
	private BaseDAO dao = null;
	
	private KeyService ks = null;

	public void addLog(String userId, String moduleName, String action, String ip, String flag) {
		// TODO Auto-generated method stub
		ForumLog fl = new ForumLog();
		fl.setId(ks.getNext("Forum_Log"));
		fl.setAction(action);
		fl.setIp(ip);
		fl.setModuleName(moduleName);
		fl.setOperTime(TimeUtil.getNowTime());
		fl.setUserId(userId);
		dao.saveEntity(fl);
	}

	public List logList(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		ForumLogHelp flh = new ForumLogHelp();
		Object[] result = (Object[])dao.findEntity(flh.logQuery(dto, pageInfo));
	    num = dao.findEntitySize(flh.logQuery(dto, pageInfo));
		for(int i=0,size=result.length;i<size;i++){
			ForumLog fl = (ForumLog)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", fl.getId());
			dbd.set("userId", fl.getUserId());
			dbd.set("moduleName", this.getModuleName(fl.getModuleName()));
			dbd.set("action", fl.getAction());
			dbd.set("ip", fl.getIp());
			dbd.set("operTime", fl.getOperTime());
			list.add(dbd);
		}
		return list;
	}
	
	private String getModuleName(String id){
		if(!id.equals("")){
			SysModule sm = (SysModule)dao.loadEntity(SysModule.class, id);
			if(sm!=null){
				return sm.getName();
			}else{
				return "";
			}
		}
		return "";
	}
	
	public int getSize(){
		return num;
	}
	
	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	

}
