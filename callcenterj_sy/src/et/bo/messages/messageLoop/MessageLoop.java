/*
 * @(#)MessagesAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.messages.messageLoop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import et.bo.messages.service.MessagesService;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>消息轮询类 前台js调用这个类得到信息</p>
 * 
 * @version 2008-12-03
 * @author wangwenquan
 */
public class MessageLoop {
	/**
	 * key receiveMan
	 * value MessageCollection
	 */
	private static Map<String, MessageCollection> messages = new HashMap<String, MessageCollection>();
	//tomcat 从启动或其它原因是MessageLoop与数据库不同步调用这个方法
	public static void InitMessageLoop()
	{
		MessagesService ms = (MessagesService)SpringRunningContainer.getInstance().getBean("MessagesService");
		ms.appendAllNonReadMsg();
	}
	public static MessageCollection getMsgCols(String receiveMan)
	{
		return messages.get(receiveMan);
	}
	//add a MessageInfo
    public static void addMsgInfo(MessageInfo info)
    {
    
    	if(info != null)
    	{
    		String receiveMan = info.getReceive_id();
    		//看看有没有这个集合
    		MessageCollection cols = messages.get(receiveMan);
    		if(cols != null)
    		{
    			cols.addMsgInfo(info);
    		}
    		else
    		{
    			cols = new MessageCollection();
    			cols.setReceiveMan(receiveMan);
    			cols.addMsgInfo(info);
    			messages.put(receiveMan, cols);
    		}
    	}
    }
    //删除元素
    public static void removeMsgInfo(MessageInfo info)
    {
    	if(info != null)
    	{
    		String receiveMan = info.getReceive_id();
    		//看看有没有这个集合
    		MessageCollection cols = messages.get(receiveMan);
    		if(cols != null)
    		{
    			cols.removeMsgInfo(info.getMessage_id());
    		}
    	}
    }
    //删除元素
    public static void removeMsgInfo(String message_id,String receiveMan)
    {
		MessageCollection cols = messages.get(receiveMan);
		if(cols != null)
		{
			cols.removeMsgInfo(message_id);
		}
    }
}
