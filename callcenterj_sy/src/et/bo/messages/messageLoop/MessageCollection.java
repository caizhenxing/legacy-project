/*
 * @(#)MessagesAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.messages.messageLoop;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>消息集合 这里是未读消息</p>
 * 
 * @version 2008-12-03
 * @author wangwenquan
 */
public class MessageCollection {
	private String receiveMan;
	private List<MessageInfo> msgInfos = new ArrayList<MessageInfo>();
	private MessageInfo curMsgInfo; //最新的一条消息引用
	public String getReceiveMan() {
		return receiveMan;
	}
	public void setReceiveMan(String receiveMan) {
		this.receiveMan = receiveMan;
	}
	//移除一个元素
	public void removeMsgInfo(String message_id)
	{
		for(int i = 0; i<msgInfos.size(); i++)
		{
			if(message_id.equals(msgInfos.get(i).getMessage_id().trim()))
			{
				msgInfos.remove(i);
			}
		}
	}
	//消息数目
	public int getMessageSize()
	{
		return msgInfos.size();
	}
	//增加一个元素
	public void addMsgInfo(MessageInfo info)
	{
		if(validMsgInfo(info))
		{
			msgInfos.add(info);
			curMsgInfo = null;
			curMsgInfo = info;
		}
	}
	//看看增加的信息是否合理
	private boolean validMsgInfo(MessageInfo info)
	{
		if(info == null)
			return false;
		if(receiveMan.equals(info.getReceive_id()))
		{
			for(int i=0; i<msgInfos.size(); i++)
			{
				if(info.getMessage_id().equals(msgInfos.get(i).getMessage_id()))
					return false;
				
			}
			//集合中没有这个元素 可以增加
			return true;
		}
		return false;
			
	}
	private String getNonNullTrimStr(String str)
	{
		if(str == null)
		{
			return "";
		}
		else
			return str.trim();
	}
	public MessageInfo getCurMsgInfo() {
		return curMsgInfo;
	}
	public void setCurMsgInfo(MessageInfo curMsgInfo) {
		this.curMsgInfo = curMsgInfo;
	}
}
