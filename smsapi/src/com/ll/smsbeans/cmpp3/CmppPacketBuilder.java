/*
 * 创建日期 2004-9-17
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import com.ll.smsbeans.IdBuilder;
import com.ll.smsbeans.PacketBuilder;

/**
*
* @author  listlike <a href="mailto:listlike@hotmail.com">
*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
*
*/
public abstract class CmppPacketBuilder implements PacketBuilder
{
	
	protected int SequenceId;
	
	protected int CreateNextSequenceId()
	{	
		SequenceId=IdBuilder.getInstance().getSequenceId();
		return SequenceId;
	}
	
	/**
	 * @return
	 */
	public int getSequenceId()
	{
		return SequenceId;
	}

	/**
	 * @param seqId
	 */
	public void setSequenceId(int seqId)
	{
		SequenceId = seqId;
	}

}
