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
public class CmppCancelRespBuilder extends CmppPacketBuilder
{

	private int SuccessId;
	
	
	public CmppCancelRespBuilder()
	{
		reset();
	}
	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#builder()
	 */
	public Packet builder()
	{
		return new CmppCancelResp(this);
	}

	

	/**
	 * @return
	 */
	public int getSuccessId()
	{
		return SuccessId;
	}

	/**
	 * @param successId
	 */
	public void setSuccessId(int successId)
	{
		SuccessId = successId;
	}

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#reset()
	 */
	public void reset()
	{
		SequenceId=0; 
		SuccessId=-1;
	}

}
