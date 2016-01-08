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
public class CmppDeliverRespHander extends CmppHander
{

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.cmpp3.cmpp3Hander#packetBuiler(byte[])
	 */
	public Packet packetBuiler(byte[] packbytes) throws Exception
	{
		
		CmppDeliverRespBuilder cdrb =new CmppDeliverRespBuilder();
		
		
		cdrb.setSequenceId(Bytes4ToInt(packbytes,4));
		
		cdrb.setMsgId(Bytes8ToLong(packbytes,8));
		cdrb.setResult(Bytes4ToInt(packbytes,16));
		return cdrb.builder();
	}

}
