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
public class CmppCancel extends CmppPacket
{
	
	private long MsgId;
	

	public CmppCancel(CmppCancelBuilder builder)
	{
		CommandId =  CmppPacketCommon.ID_CMPP_CANCEL;
		builder.CreateNextSequenceId(); 
		SequenceId 	=builder.getSequenceId();
		
		MsgId=builder.getMsgId() ;	 
	}

	public byte[] getContent() throws IOException
	{
		this.writeD(CommandId);
		this.writeD(SequenceId);
		
		this.writeDD(MsgId );
		return getBytes();
	}

	
	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		// TODO 自动生成方法存根
		
		return this.getClass().getName() + ": SequenceId=" + SequenceId
			+ "\n  MsgId=" + MsgId;
	}

	/**
	 * @return
	 */
	public long getMsgId()
	{
		return MsgId;
	}

}
