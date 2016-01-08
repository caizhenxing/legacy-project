/*
 * 创建日期 2004-9-11
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import java.io.IOException;

/**
 * 
 
 **
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 *
 */

public class CmppActiveTest extends CmppPacket
{
	

	public CmppActiveTest(CmppActiveTestBuilder builder)
	{
		CommandId = CmppPacketCommon.ID_CMPP_ACTIVE_TEST; //0x00000008
		
		builder.CreateNextSequenceId(); 
		SequenceId=builder.getSequenceId();
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
		
		return this.getClass().getName() + ": SequenceId=" + SequenceId;
	}

}
