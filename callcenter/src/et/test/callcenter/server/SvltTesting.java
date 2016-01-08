package et.test.callcenter.server;

import et.bo.callcenter.server.Server;
import et.bo.common.testing.MySvltTesting;

public class SvltTesting extends MySvltTesting{
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SvltTesting().initServer();
	}

	public void initServer(){
		SvltTesting st =new SvltTesting();
		st.testing();
		Server s =(Server)st.springContainer.getBean("ServerService");
//		OperatorState os = (OperatorState)st.springContainer.getBean("OperatorStateService");		
		s.runServer();
//		try{
//			Thread.sleep(10000);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		OperatorState.setOperatorStateLogon("001", "192.168.1.3");
	}
}
