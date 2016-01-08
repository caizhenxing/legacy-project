/*
 * @(#)MessagesAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.messages.messageLoop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import et.bo.messages.service.MessagesService;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * <p>��Ϣ��ѯ�� ǰ̨js���������õ���Ϣ</p>
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
	//tomcat ������������ԭ����MessageLoop�����ݿⲻͬ�������������
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
    		//������û���������
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
    //ɾ��Ԫ��
    public static void removeMsgInfo(MessageInfo info)
    {
    	if(info != null)
    	{
    		String receiveMan = info.getReceive_id();
    		//������û���������
    		MessageCollection cols = messages.get(receiveMan);
    		if(cols != null)
    		{
    			cols.removeMsgInfo(info.getMessage_id());
    		}
    	}
    }
    //ɾ��Ԫ��
    public static void removeMsgInfo(String message_id,String receiveMan)
    {
		MessageCollection cols = messages.get(receiveMan);
		if(cols != null)
		{
			cols.removeMsgInfo(message_id);
		}
    }
}
