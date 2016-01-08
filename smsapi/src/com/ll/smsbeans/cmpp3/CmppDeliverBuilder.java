/*
 * 创建日期 2004-9-16
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import com.ll.smsbeans.Packet;

/**
*
* @author  listlike <a href="mailto:listlike@hotmail.com">
*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
*
*/
public class CmppDeliverBuilder extends CmppPacketBuilder
{

	private long msgId;
	private String dstId;
	private String serviceId;
	private int tpPid;
	private int tpUdhi;
	private int msgFmt;

	private int srcTermType;
	private String srcTermId;

	private int registerDelivery;

	private byte msgContent[];

	private String linkId;

	

	/**
	 * 
	 */
	public CmppDeliverBuilder()
	{
		reset();
	}

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#builder()
	 */
	public Packet builder()
	{

		return new CmppDeliver(this);
	}

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#reset()
	 */
	public void reset()
	{
		SequenceId=0; 
		
		msgId = 0L;
		
		
		dstId="";
		serviceId = "";
		tpPid = 0;
		tpUdhi = 0;
		msgFmt = 0;
		srcTermType=0;
		srcTermId = "";
		registerDelivery = 0;	
		msgContent = new byte[0];

		linkId = "";
	
	}

	/**
	 * @return
	 */
	public String getDstId()
	{
		return dstId;
	}

	/**
	 * @return
	 */
	public String getLinkId()
	{
		return linkId;
	}

	/**
	 * @return
	 */
	public byte[] getMsgContent()
	{
		return msgContent;
	}

	/**
	 * @return
	 */
	public int getMsgFmt()
	{
		return msgFmt;
	}

	/**
	 * @return
	 */
	public long getMsgId()
	{
		return msgId;
	}

	/**
	 * @return
	 */
	public int getRegisterDelivery()
	{
		return registerDelivery;
	}

	/**
	 * @return
	 */
	public String getServiceId()
	{
		return serviceId;
	}

	/**
	 * @return
	 */
	public String getSrcTermId()
	{
		return srcTermId;
	}

	/**
	 * @return
	 */
	public int getSrcTermType()
	{
		return srcTermType;
	}

	/**
	 * @return
	 */
	public int getTpPid()
	{
		return tpPid;
	}

	/**
	 * @return
	 */
	public int getTpUdhi()
	{
		return tpUdhi;
	}

	/**
	 * @param string
	 */
	public void setDstId(String string)
	{
		dstId = string;
	}

	/**
	 * @param string
	 */
	public void setLinkId(String string)
	{
		linkId = string;
	}

	/**
	 * @param bs
	 */
	public void setMsgContent(byte[] bs)
	{
		msgContent = bs;
	}

	/**
	 * @param i
	 */
	public void setMsgFmt(int i)
	{
		msgFmt = i;
	}

	/**
	 * @param l
	 */
	public void setMsgId(long l)
	{
		msgId = l;
	}

	/**
	 * @param i
	 */
	public void setRegisterDelivery(int i)
	{
		registerDelivery = i;
	}

	/**
	 * @param string
	 */
	public void setServiceId(String string)
	{
		serviceId = string;
	}

	/**
	 * @param string
	 */
	public void setSrcTermId(String string)
	{
		srcTermId = string;
	}

	/**
	 * @param i
	 */
	public void setSrcTermType(int i)
	{
		srcTermType = i;
	}

	/**
	 * @param i
	 */
	public void setTpPid(int i)
	{
		tpPid = i;
	}

	/**
	 * @param i
	 */
	public void setTpUdhi(int i)
	{
		tpUdhi = i;
	}

}
