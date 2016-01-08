package et.bo.callcenter.dbPoll.task;

import java.util.TimerTask;

import et.bo.callcenter.dbPoll.service.PollDBService;


public class PollDBTask extends TimerTask {
	private PollDBService ps;
	
	public PollDBService getPs() {
		return ps;
	}

	public void setPs(PollDBService ps) {
		this.ps = ps;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//smsPollService.pollResult();
//		System.out.println("开始执行定时任务！");
		ps.searchDB();
	}
}
