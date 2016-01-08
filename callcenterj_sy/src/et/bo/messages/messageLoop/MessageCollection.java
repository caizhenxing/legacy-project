/*
 * @(#)MessagesAction.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.messages.messageLoop;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>��Ϣ���� ������δ����Ϣ</p>
 * 
 * @version 2008-12-03
 * @author wangwenquan
 */
public class MessageCollection {
	private String receiveMan;
	private List<MessageInfo> msgInfos = new ArrayList<MessageInfo>();
	private MessageInfo curMsgInfo; //���µ�һ����Ϣ����
	public String getReceiveMan() {
		return receiveMan;
	}
	public void setReceiveMan(String receiveMan) {
		this.receiveMan = receiveMan;
	}
	//�Ƴ�һ��Ԫ��
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
	//��Ϣ��Ŀ
	public int getMessageSize()
	{
		return msgInfos.size();
	}
	//����һ��Ԫ��
	public void addMsgInfo(MessageInfo info)
	{
		if(validMsgInfo(info))
		{
			msgInfos.add(info);
			curMsgInfo = null;
			curMsgInfo = info;
		}
	}
	//�������ӵ���Ϣ�Ƿ����
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
			//������û�����Ԫ�� ��������
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
