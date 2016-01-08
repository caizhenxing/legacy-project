package et.workflow.jbpm;


import java.util.TimerTask;

import et.workflow.jbpm.impl.WorkFlowServiceImpl;

public class WorkFlowTimerTask extends TimerTask {

	WorkFlowService wfs=new WorkFlowServiceImpl();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		wfs.ListenMsg();
	}

}
