/*
 * 创建日期 2004-9-16
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import java.util.Iterator;
import java.util.Vector;

import com.ll.smsbeans.Packet;

/**
*
* @author  listlike <a href="mailto:listlike@hotmail.com">
*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
*
*/
public class CmppSubmitBuilder extends CmppPacketBuilder
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

	private String linkId;

	private Vector dstTermIdList=new Vector();

	/**
	 * 
	 */
	public CmppSubmitBuilder()
	{
		reset();
	}

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#builder()
	 */
	public Packet builder()
	{

		return new CmppSubmit(this);
	}

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#reset()
	 */
	public void reset()
	{
		SequenceId=0; 
		
		msgId = 0L;
		pkTotal = 0;
		pkNumber = 0;
		registerDelivery = 0;
		msgLevel = 0;
		serviceId = "";
		feeUserType = 0;
		feeTermId = "";
		feeTermType = 0;
		tpPid = 0;
		tpUdhi = 0;
		msgFmt = 0;
		msgSrc = "";
		feeType = "";
		feeCode = "";
		validTime = "";
		atTime = "";
		srcTermId = "";

		dstTermType = 0;
		msgLen = 0;
		msgContent = new byte[0];

		linkId = "";
		
		ClearDstTermId(); 

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
	 * @param string
	 */
	public void setAtTime(String string)
	{
		atTime = string;
	}

	/**
	 * @param i
	 */
	public void setDstTermType(int i)
	{
		dstTermType = i;
	}

	/**
	 * @param string
	 */
	public void setFeeCode(String string)
	{
		feeCode = string;
	}

	/**
	 * @param string
	 */
	public void setFeeTermId(String string)
	{
		feeTermId = string;
	}

	/**
	 * @param i
	 */
	public void setFeeTermType(int i)
	{
		feeTermType = i;
	}

	/**
	 * @param string
	 */
	public void setFeeType(String string)
	{
		feeType = string;
	}

	/**
	 * @param i
	 */
	public void setFeeUserType(int i)
	{
		feeUserType = i;
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
	public void setMsgLevel(int i)
	{
		msgLevel = i;
	}

	/**
	 * @param string
	 */
	public void setMsgSrc(String string)
	{
		msgSrc = string;
	}

	/**
	 * @param i
	 */
	public void setPkNumber(int i)
	{
		pkNumber = i;
	}

	/**
	 * @param i
	 */
	public void setPkTotal(int i)
	{
		pkTotal = i;
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

	/**
	 * @param string
	 */
	public void setValidTime(String string)
	{
		validTime = string;
	}

	/**
	 * @param o
	 * @return
	 */
	public boolean addDstTermId(String dstTermId)
	{
		return dstTermIdList.add(dstTermId);
	}

	/**
	 * @param dstTermId
	 * @return
	 */
	public boolean removeDstTermId(String dstTermId)
	{
		return dstTermIdList.remove(dstTermId);
	}

	public void ClearDstTermId()
	{	
		if (dstTermIdList.size()<1) 
			return; 
		for (int i = 0; i <= dstTermIdList.size(); i++)
		{
			dstTermIdList.remove(0);
		}
	}

	/**
	 * @return
	 */
	public Iterator getDstTermIdList()
	{
		return dstTermIdList.iterator();
	}

}
