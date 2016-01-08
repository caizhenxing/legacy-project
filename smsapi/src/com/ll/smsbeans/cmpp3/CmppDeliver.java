/*
 * 创建日期 2004-9-11
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import java.io.IOException;

/**
 * @author Administrator
 *
 * 
 */
public class CmppDeliver extends CmppPacket
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

	public CmppDeliver(CmppDeliverBuilder builder)
	{
		CommandId = CmppPacketCommon.ID_CMPP_DELIVER; //0x00000005
		
		SequenceId = builder.getSequenceId();

		msgId = builder.getMsgId();
		dstId = builder.getDstId();
		serviceId = builder.getServiceId();
		tpPid = builder.getTpPid(); 
		tpUdhi = builder.getTpUdhi();
		msgFmt = builder.getMsgFmt();
		srcTermId = builder.getSrcTermId();
		srcTermType = builder.getSrcTermType();

		registerDelivery = builder.getRegisterDelivery();

		linkId = builder.getLinkId();

		msgContent = builder.getMsgContent();
	}

	public byte[] getContent() throws IOException
	{
		_bao.reset();
		this.writeD(CommandId);
		this.writeD(SequenceId);

		this.writeDD(msgId);

		this.writeS(dstId, 21);
		this.writeS(serviceId, 10);
		this.writeC(tpPid);
		this.writeC(tpUdhi);
		this.writeC(msgFmt);
		this.writeS(srcTermId, 32);
		this.writeC(srcTermType);
		this.writeC(registerDelivery);
		this.writeC(msgContent.length);
		this.writeBytes(msgContent);

		writeS(linkId, 20);

		return getBytes();
	}

	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{

		return this.getClass().getName()
			+ ": SequenceId="
			+ SequenceId
			+ "\n msgId ="
			+ msgId
			+ "\n dstId ="
			+ dstId
			+ "\n serviceId ="
			+ serviceId
			+ "\n tpPid ="
			+ tpPid
			+ "\n tpUdhi ="
			+ tpUdhi
			+ "\n msgFmt ="
			+ msgFmt
			
			+ "\n srcTermId ="
			+ srcTermId
			+ "\n srcTermType ="
			+ srcTermType
			+ "\n dstTermId ="
			+ "\n registerDelivery ="
			+ registerDelivery
			+ "\n msgLen ="
			+ msgContent.length
			+ "\n msgContent ="
			+ msgContent
			+ "\n linkId ="
			+ linkId;
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

	public int getMsgLen()
	{
		return msgContent.length;
	}
}
