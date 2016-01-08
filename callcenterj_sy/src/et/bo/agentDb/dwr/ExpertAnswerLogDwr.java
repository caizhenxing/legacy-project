package et.bo.agentDb.dwr;

import et.bo.expertAnswerLog.service.ExpertAnswerLogService;
import excellence.framework.base.container.SpringRunningContainer;



public class ExpertAnswerLogDwr {
	public void insertExpertAnswerLog(String telNum,String agcwid)
	{
		ExpertAnswerLogService log = (ExpertAnswerLogService)SpringRunningContainer.getInstance().getBean("expertAnswerLogService");
		String expertName = log.getExpertNameByTel(telNum);
		log.addExpert(telNum, agcwid,expertName);
	}
}
