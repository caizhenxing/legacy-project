package excellence.flow.msg.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import excellence.flow.msg.FlowMsgService;
import excellence.flow.msg.MsgBean;

public class FlowMsgServiceMapImpl implements FlowMsgService {

	 private static ConcurrentMap<String, CopyOnWriteArrayList<MsgBean>> msgs=new ConcurrentHashMap<String,CopyOnWriteArrayList<MsgBean>>();
	
	public List<MsgBean> receiveMsg(String dest,boolean del) {
		// TODO Auto-generated method stub
		List<MsgBean> results=null;
		if(msgs.containsKey(dest))
	{
		CopyOnWriteArrayList<MsgBean> cl=msgs.get(dest);
		results=new ArrayList<MsgBean>(cl);
		if(del)
		cl.clear();
		if(results==null)
			results=new ArrayList<MsgBean>();
		return results;
	}
		return new ArrayList<MsgBean>();
	}

	public void sendMsg(MsgBean mb) {
		// TODO Auto-generated method stub
		String dest=mb.getDest();
		if(msgs.containsKey(dest))
		{
			CopyOnWriteArrayList<MsgBean> results=msgs.get(dest);
			results.addIfAbsent(mb);
			
		}
		CopyOnWriteArrayList<MsgBean> cwal=new CopyOnWriteArrayList<MsgBean>();
		cwal.addIfAbsent(mb);
		msgs.putIfAbsent(dest,cwal);
	}

}
