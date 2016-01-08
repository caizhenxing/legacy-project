package et.bo.expertAnswerLog.service;

import et.po.ExpertAnswerLog;

public interface ExpertAnswerLogService {
	public String getExpertNameByTel(String tel);
	public void addExpert(String tel, String agcwid,String name);
}
