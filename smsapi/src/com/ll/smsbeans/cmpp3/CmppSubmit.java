/*
 * 创建日期 2004-9-11
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Administrator
 *
 * 
 */
public class CmppSubmit extends CmppPacket
{

	private long msgId;
	private int pkTotal;
	private int pkNumber;
	private int registerDelivery;
	private int msgLevel;
	private String serviceId;
	private int feeUserType;
	private String feeTermId;
	private int feeTermType;
	private int tpPid;
	private int tpUdhi;
	private int msgFmt;
	private String msgSrc;
	private String feeType;
	private String feeCode;
	private String validTime;
	private String atTime;
	private String srcTermId;

	private int dstTermType;
	private int msgLen;
	private byte msgContent[];
	private String msgStr;
	private String linkId;

	private Vector dstTermIdList;

	public CmppSubmit(CmppSubmitBuilder builder)
	{
		CommandId = CmppPacketCommon.ID_CMPP_SUBMIT; //0x00000004
		builder.CreateNextSequenceId(); 
		SequenceId = builder.getSequenceId();
		
		dstTermIdList = new Vector();

		msgId = builder.getMsgId();
		pkTotal = builder.getPkTotal();
		pkNumber = builder.getPkNumber();
		registerDelivery = builder.getRegisterDelivery();
		msgLevel = builder.getMsgLevel();
		serviceId = builder.getServiceId();
		feeUserType = builder.getFeeUserType();
		feeTermId = builder.getFeeTermId();

		feeTermType = builder.getFeeTermType();
		tpPid = builder.getTpPid();
		tpUdhi = builder.getTpUdhi();
		msgFmt = builder.getMsgFmt();
		msgSrc = builder.getMsgSrc();
		feeType = builder.getFeeType();
		feeCode = builder.getFeeCode();
		atTime = builder.getAtTime();
		validTime = builder.getValidTime();
		srcTermId = builder.getSrcTermId();
		dstTermType = builder.getDstTermType();
		linkId = builder.getLinkId();

		for (Iterator iter = builder.getDstTermIdList(); iter.hasNext();)
		{
			String element = (String) iter.next();
			dstTermIdList.add(element);
		}
		msgContent=builder.getMsgContent(); 
	}

	public byte[] getContent() throws IOException
	{
		_bao.reset();
		this.writeD(CommandId);
		this.writeD(SequenceId);

		this.writeDD(this.msgId);
		this.writeC(this.pkTotal);
		this.writeC(this.pkNumber);
		this.writeC(this.registerDelivery);
		this.writeC(this.msgLevel);
		this.writeS(this.serviceId, 10);
		this.writeC(this.feeUserType);
		this.writeS(this.feeTermId, 32);
		this.writeC(this.feeTermType);
		this.writeC(this.tpPid);
		this.writeC(this.tpUdhi);
		this.writeC(this.msgFmt);
		this.writeS(this.msgSrc, 6);
		this.writeS(this.feeType, 2);
		this.writeS(this.feeCode, 6);
		this.writeS(this.validTime, 17);
		this.writeS(this.atTime, 17);
		this.writeS(this.srcTermId, 21);
		this.writeC(this.dstTermIdList.size());

		for (Iterator iter = dstTermIdList.iterator(); iter.hasNext();)
		{
			String dstTerm = (String) iter.next();
			this.writeS(dstTerm, 32);
		}

		this.writeC(this.dstTermType);

		writeC(msgContent.length);
		writeBytes(msgContent);

		writeS(this.linkId, 20);
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
			+ "\n pkTotal ="
			+ pkTotal
			+ "\n pkNumber ="
			+ pkNumber
			+ "\n registerDelivery ="
			+ registerDelivery
			+ "\n msgLevel ="
			+ msgLevel
			+ "\n serviceId ="
			+ serviceId
			+ "\n feeUserType ="
			+ feeUserType
			+ "\n feeTermId ="
			+ feeTermId
			+ "\n feeTermType ="
			+ feeTermType
			+ "\n tpPid ="
			+ tpPid
			+ "\n tpUdhi ="
			+ tpUdhi
			+ "\n msgFmt ="
			+ msgFmt
			+ "\n msgSrc ="
			+ msgSrc
			+ "\n feeType ="
			+ feeType
			+ "\n feeCode ="
			+ feeCode
			+ "\n validTime ="
			+ validTime
			+ "\n atTime ="
			+ atTime
			+ "\n srcTermId ="
			+ srcTermId
			+ "\n destUsrTl ="
			+ this.getDestUsrTl()
			+ "\n dstTermId ="
			+ this.getDstTermId()
			+ "\n dstTermType ="
			+ dstTermType
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
	public String getAtTime()
	{
		return atTime;
	}

	/**
	 * @return
	 */
	public int getDestUsrTl()
	{
		return dstTermIdList.size();
	}

	/**
	 * @return
	 */
	public String getDstTermId()
	{
		String tmp;
		tmp = "";
		for (Iterator iter = dstTermIdList.iterator(); iter.hasNext();)
		{
			String element = (String) iter.next();
			tmp = tmp + element + ";";
		}
		return tmp;
	}

	/**
	 * @return
	 */
	public int getDstTermType()
	{
		return dstTermType;
	}

	/**
	 * @return
	 */
	public String getFeeCode()
	{
		return feeCode;
	}

	/**
	 * @return
	 */
	public String getFeeTermId()
	{
		return feeTermId;
	}

	/**
	 * @return
	 */
	public int getFeeTermType()
	{
		return feeTermType;
	}

	/**
	 * @return
	 */
	public String getFeeType()
	{
		return feeType;
	}

	/**
	 * @return
	 */
	public int getFeeUserType()
	{
		return feeUserType;
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
	public int getMsgLen()
	{
		return msgContent.length;
	}

	/**
	 * @return
	 */
	public int getMsgLevel()
	{
		return msgLevel;
	}

	/**
	 * @return
	 */
	public String getMsgSrc()
	{
		return msgSrc;
	}

	/**
	 * @return
	 */
	public int getPkNumber()
	{
		return pkNumber;
	}

	/**
	 * @return
	 */
	public int getPkTotal()
	{
		return pkTotal;
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
	 * @return
	 */
	public String getValidTime()
	{
		return validTime;
	}


	/**
	 * @return
	 */
	public Iterator getDstTermIdList()
	{
		return dstTermIdList.iterator();
	}

}
