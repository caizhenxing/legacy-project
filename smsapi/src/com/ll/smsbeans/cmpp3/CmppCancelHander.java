/*
 * 创建日期 2004-9-12
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import com.ll.smsbeans.Packet;

/**
 * @author Administrator
 *
 * 
 */
public class CmppCancelHander extends CmppHander
{

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.cmpp3.cmpp3Hander#packetBuiler(byte[])
	 */
	public Packet packetBuiler(byte[] packbytes) throws Exception
	{
		
		CmppCancelBuilder ccb =new CmppCancelBuilder();
		
		
		ccb.setSequenceId(Bytes4ToInt(packbytes,4));
		
		ccb.setMsgId( Bytes8ToLong(packbytes,8 ));
		
		return ccb.builder();
	}

}
