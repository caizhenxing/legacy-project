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
public class CmppActiveTestBuilder extends CmppPacketBuilder
{
	
	private int Reserved;
	
	
	public CmppActiveTestBuilder()
	{
		reset();
	}
	
	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#builder()
	 */
	public Packet builder()
	{	
		
		return new CmppActiveTest(this);
	}
	
	

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#reset()
	 */
	public void reset()
	{
		SequenceId=0; 		

	}

}
