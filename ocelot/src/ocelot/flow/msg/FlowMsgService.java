package ocelot.flow.msg;

import java.util.List;

public interface FlowMsgService {

	public List<MsgBean> receiveMsg(String dest,boolean del);
	
	public void sendMsg(MsgBean mb);
	
	
}
