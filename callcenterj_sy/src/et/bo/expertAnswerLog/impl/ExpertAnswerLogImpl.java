package et.bo.expertAnswerLog.impl;
import java.util.Date;

import et.bo.expertAnswerLog.service.ExpertAnswerLogService;
import et.po.ExpertAnswerLog;
import et.po.OperCustinfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
public class ExpertAnswerLogImpl implements ExpertAnswerLogService {
	private BaseDAO dao = null;

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	public String getExpertNameByTel(String tel)
	{
		
		ExpertAnswerLogHelp h = new ExpertAnswerLogHelp();
		Object[] o = dao.findEntity(h.operCustQuery(tel));
		if(o.length>0)
		{
			OperCustinfo info = (OperCustinfo)o[0];
			return info.getCustName();
		}
		else
		{
			return "";
		}
	}
	public void addExpert(String tel, String agcwid, String name)
	{
		//String curTime = TimeUtil.getTheTimeStr(new Date(),"yyyy-mm-dd hh:mi:ss");
		ExpertAnswerLog log = new ExpertAnswerLog();

		log.setAgcwid(agcwid);
		log.setTelnum(tel);
		log.setName(name);
		log.setStarttime(new Date());
		dao.saveEntity(log);

	}
}
