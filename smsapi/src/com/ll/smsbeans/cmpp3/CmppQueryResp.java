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
public class CmppQueryResp extends CmppPacket
{

	public static int QUERY_TYPE_TOTAL = 0;
	public static int QUERY_TYPE_OPERATION = 1;

	private String Time;
	private int QueryType;
	private String QueryCode;
	private int MtTlMsg;
	private int MtTlUsr;
	private int MtScs;
	private int MtWt;
	private int MtFl;
	private int MoScs;
	private int MoWt;
	private int MoFl;

	public CmppQueryResp(CmppQueryRespBuilder builder)
	{
		CommandId = CmppPacketCommon.ID_CMPP_QUERY_RESP; // 0x80000002;
		SequenceId = builder.getSequenceId();

		Time = builder.getTime();
		QueryType = builder.getQueryType();
		QueryCode = builder.getQueryCode();
		MtTlMsg = builder.getMtTlMsg();
		MtTlUsr = builder.getMtTlUsr();
		MtScs = builder.getMtScs();
		MtWt = builder.getMtWt();
		MtFl = builder.getMtFl();
		MoScs = builder.getMoScs();
		MoWt = builder.getMoWt();
		MoFl = builder.getMoFl();

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

		return this.getClass().getName()
			+ ": SequenceId="
			+ SequenceId
			+ "\n  Time="
			+ Time
			+ "\n  QueryType="
			+ QueryType
			+ "\n  MtTlMsg="
			+ MtTlMsg
			+ "\n  MtTlUsr="
			+ MtTlUsr
			+ "\n  MtScs="
			+ MtScs
			+ "\n  MtWt="
			+ MtWt
			+ "\n  MtFl="
			+ MtFl
			+ "\n  MoScs="
			+ MoScs
			+ "\n  MoWt="
			+ MoWt
			+ "\n  MoFl="
			+ MoFl;

	}

	/**
	 * @return
	 */
	public int getMtTlUsr()
	{
		return MtTlUsr;
	}

	/**
	 * @return
	 */
	public int getMtWt()
	{
		return MtWt;
	}

	/**
	 * @return
	 */
	public String getQueryCode()
	{
		return QueryCode;
	}

	/**
	 * @return
	 */
	public int getQueryType()
	{
		return QueryType;
	}

	/**
	 * @return
	 */
	public String getTime()
	{
		return Time;
	}

	/**
	 * @return
	 */
	public int getMoFl()
	{
		return MoFl;
	}

	/**
	 * @return
	 */
	public int getMoScs()
	{
		return MoScs;
	}

	/**
	 * @return
	 */
	public int getMoWt()
	{
		return MoWt;
	}

	/**
	 * @return
	 */
	public int getMtFl()
	{
		return MtFl;
	}

	/**
	 * @return
	 */
	public int getMtScs()
	{
		return MtScs;
	}

	/**
	 * @return
	 */
	public int getMtTlMsg()
	{
		return MtTlMsg;
	}

}
