/*
 * 创建日期 2004-9-18
 *
 * 
 */
package com.ll.smsbeans;

import java.util.Collection;
import java.util.HashMap;

import com.ll.smsbeans.cmpp3.CmppPacket;

/**
 * 记录当前发送包状态的列表。
 * @author listlike <a href="mailto:listlike@hotmail.com">
 * <i>&lt;listlike@hotmail.com&gt;</i></a>
 */
public class SendPackStateList
{

	private HashMap map;

	/**
	* Holds singleton instance
	*/
	private static SendPackStateList instance;

	/**
	 * 
	 */
	private SendPackStateList()
	{
		super();

		map = new HashMap();

	}

	/**
	 * 
	 */
	synchronized public void clear()
	{
		map.clear();
	}

	/**
	 * @param packet
	 * @return
	 */
	synchronized public  SendPacketState get(Packet packet)
	{

		if (packet instanceof CmppPacket)
		{
			CmppPacket cp = (CmppPacket) packet;
			return (SendPacketState) map.get(new Integer(cp.getSequenceId()));
		} else
			return null;
	}

	/**
	 * @return
	 */
	synchronized public boolean isEmpty()
	{
		return map.isEmpty();
	}

	/**
	 * @param key
	 * @return
	 */
	synchronized public SendPacketState remove(Packet value)
	{

		if (value instanceof CmppPacket)
		{
			CmppPacket cp = (CmppPacket) value;

			return (SendPacketState) map.remove(
				new Integer(cp.getSequenceId()));
		} else
			return null;
	}


	synchronized public SendPacketState add(Packet value)
	{

		if (value instanceof CmppPacket)
		{
			CmppPacket cp = (CmppPacket) value;

			return (SendPacketState) map.put(
				new Integer(cp.getSequenceId()),
				new SendPacketState(value));
		} else
			return null;

	}

	/**
	 * @return
	 */
	synchronized public int size()
	{
		return map.size();
	}

	/**
	 * @return
	 */
	synchronized public Collection values()
	{
		return map.values();

	}

	/**
	* Returns the singleton instance.
	 @return	the singleton instance
	*/
	static public SendPackStateList getInstance()
	{
		if (instance == null)
		{
			instance = new SendPackStateList();
		}
		return instance;
	}

}