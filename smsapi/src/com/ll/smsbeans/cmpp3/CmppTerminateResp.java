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
public class CmppTerminateResp extends CmppPacket
{
	

	public CmppTerminateResp(CmppTerminateRespBuilder builder)
	{
		CommandId =CmppPacketCommon.ID_CMPP_TERMINATE_RESP; // 0x80000002;
		SequenceId =builder.getSequenceId(); 
	}

	public byte[] getContent() throws IOException
	{
		this.writeD(CommandId);
		this.writeD(SequenceId);
		
		return getBytes();
	}

	
	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		// TODO 自动生成方法存根
		
		return this.getClass().getName() + ": SequenceId=" + SequenceId;
	}

}
