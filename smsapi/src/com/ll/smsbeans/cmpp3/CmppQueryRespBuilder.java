/*
 * 创建日期 2004-9-17
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import com.ll.smsbeans.Packet;


/**
*
* @author  listlike <a href="mailto:listlike@hotmail.com">
*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
*
*/
public class CmppQueryRespBuilder extends CmppPacketBuilder
{
	
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
	
	
	public CmppQueryRespBuilder()
	{
		reset();
	}
	
	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#builder()
	 */
	public Packet builder()
	{	
		
		return new CmppQueryResp(this);
	}
	
	

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#reset()
	 */
	public void reset()
	{
		SequenceId=0;
		
		Time="";
		QueryType=0;
		QueryCode="";
		MtTlMsg=0;
		MtTlUsr=0;
		MtScs=0;
		MtWt=0;
		MtFl=0;
		MoScs=0;
		MoWt=0;
		MoFl=0; 		
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
	 * @param moFl
	 */
	public void setMoFl(int moFl)
	{
		MoFl = moFl;
	}

	/**
	 * @param moScs
	 */
	public void setMoScs(int moScs)
	{
		MoScs = moScs;
	}

	/**
	 * @param moWt
	 */
	public void setMoWt(int moWt)
	{
		MoWt = moWt;
	}

	/**
	 * @param mtFl
	 */
	public void setMtFl(int mtFl)
	{
		MtFl = mtFl;
	}

	/**
	 * @param mtScs
	 */
	public void setMtScs(int mtScs)
	{
		MtScs = mtScs;
	}

	/**
	 * @param mtTlMsg
	 */
	public void setMtTlMsg(int mtTlMsg)
	{
		MtTlMsg = mtTlMsg;
	}

	/**
	 * @param mtTlUsr
	 */
	public void setMtTlUsr(int mtTlUsr)
	{
		MtTlUsr = mtTlUsr;
	}

	/**
	 * @param mtWt
	 */
	public void setMtWt(int mtWt)
	{
		MtWt = mtWt;
	}

	/**
	 * @param queryCode
	 */
	public void setQueryCode(String queryCode)
	{
		QueryCode = queryCode;
	}

	/**
	 * @param queryType
	 */
	public void setQueryType(int queryType)
	{
		QueryType = queryType;
	}

	/**
	 * @param time
	 */
	public void setTime(String time)
	{
		Time = time;
	}

}
