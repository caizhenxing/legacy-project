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
public class CmppQuery extends CmppPacket
{
	
	public static int QUERY_TYPE_TOTAL =0;
	public static int QUERY_TYPE_OPERATION=1;
	
	
	private String Time; //8
	private int QueryType; //1
	private String QueryCode; //10
	private String Reserve; //8

	public CmppQuery(CmppQueryBuilder builder)
	{
		CommandId = CmppPacketCommon.ID_CMPP_QUERY;
		builder.CreateNextSequenceId(); 
		SequenceId = builder.getSequenceId();

		Time = builder.getTime(); //8
		QueryType = builder.getQueryType(); //1
		QueryCode = builder.getQueryCode(); //10
		Reserve = builder.getReserve(); //8	

	}

	public byte[] getContent() throws IOException
	{
		this.writeD(CommandId);
		this.writeD(SequenceId);

		this.writeS(Time, 8);
		this.writeC(QueryType);
		this.writeS(QueryCode, 10);
		this.writeS(Reserve, 8);
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
			+ "\n  QueryCode="
			+ QueryCode
			+ "\n  Reserve="
			+ Reserve;
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
	public String getReserve()
	{
		return Reserve;
	}

	/**
	 * @return
	 */
	public String getTime()
	{
		return Time;
	}

}
