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
public class CmppActiveTestResp extends CmppPacket
{
	
	private int Reserved;

	public CmppActiveTestResp(CmppActiveTestRespBuilder builder)
	{
		CommandId = CmppPacketCommon.ID_CMPP_ACTIVE_TEST_RESP;//0x80000008;
		SequenceId =builder.getSequenceId();
		
		Reserved=builder.getReserved();
	}

	public byte[] getContent() throws IOException
	{
		this.writeD(CommandId);
		this.writeD(SequenceId);
		this.writeC(Reserved);
		return getBytes();
	}

	
	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		// TODO 自动生成方法存根
		
		return this.getClass().getName() + ": SequenceId=" + SequenceId + "\n  Reserved=" +Reserved ;
	}

	/**
	 * @return
	 */
	public int getReserved()
	{
		return Reserved;
	}

	
}
