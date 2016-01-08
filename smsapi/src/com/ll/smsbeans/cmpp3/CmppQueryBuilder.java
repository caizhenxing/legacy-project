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
public class CmppQueryBuilder extends CmppPacketBuilder
{
	
	private String Time;	//8
	private int QueryType;	//1
	private String QueryCode;//10
	private String Reserve;//8
	
	
	public CmppQueryBuilder()
	{
		reset();
	}
	
	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#builder()
	 */
	public Packet builder()
	{	
		
		return new CmppQuery(this);
	}
	
	

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#reset()
	 */
	public void reset()
	{
		SequenceId=0; 	

		Time="";	//8
		QueryType=0;	//1
		QueryCode="";//10
		Reserve="";//8	
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
	 * @param reserve
	 */
	public void setReserve(String reserve)
	{
		Reserve = reserve;
	}

	/**
	 * @param time
	 */
	public void setTime(String time)
	{
		Time = time;
	}

}
