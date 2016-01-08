package et.bo.sms.cyc.service.task;

import java.util.TimerTask;

import et.bo.sms.cyc.service.CycService;

public class CycTask extends TimerTask {
	private CycService cs;
	


	public CycService getCs() {
		return cs;
	}



	public void setCs(CycService cs) {
		this.cs = cs;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
//		System.out.println("..................  begin Cyc(开始轮循查找数据库)  ...................");
		cs.cycSend();
	}

}
