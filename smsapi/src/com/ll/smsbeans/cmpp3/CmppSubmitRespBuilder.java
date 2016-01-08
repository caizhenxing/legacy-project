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
public class CmppSubmitRespBuilder extends CmppPacketBuilder
{

	private long msgId;
	private int result;
	
	
	public CmppSubmitRespBuilder()
	{
		reset();
	}
	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#builder()
	 */
	public Packet builder()
	{
		return new CmppSubmitResp(this);
	}

	


	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#reset()
	 */
	public void reset()
	{
		SequenceId=0; 
		msgId=0;
		result=-1;
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

		/**
		 * @param id
		 */
		public void setMsgId(long id)
		{
			msgId = id;
		}

		/**
		 * @param res
		 */
		public void setResult(int res)
		{
			result = res;
		}
}
