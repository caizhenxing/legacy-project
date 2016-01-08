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
public class CmppSubmitResp extends CmppPacket
{
	private long msgId;
	private int result;

	public CmppSubmitResp(CmppSubmitRespBuilder builder)
	{
		CommandId = CmppPacketCommon.ID_CMPP_SUBMIT_RESP; // 0x80000004;
		SequenceId = builder.getSequenceId();
		
		msgId = builder.getMsgId();
		result = builder.getResult();

	}

	public byte[] getContent() throws IOException
	{
		this.writeD(CommandId);
		this.writeD(SequenceId);

		this.writeDD(msgId);
		this.writeD(result);

		return getBytes();
	}

	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		// TODO 自动生成方法存根

		return this.getClass().getName()
			+ ":SequenceId="
			+ SequenceId
			+ "\n  MsgId="
			+ msgId
			+ "\n  Result="
			+ result;
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
	public int getResult()
	{
		return result;
	}

}
