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
public class CmppSubmitRespHander extends CmppHander
{

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.cmpp3.cmpp3Hander#packetBuiler(byte[])
	 */
	public Packet packetBuiler(byte[] packbytes) throws Exception
	{
		
		CmppSubmitRespBuilder csrb =new CmppSubmitRespBuilder();
		
		
		csrb.setSequenceId(Bytes4ToInt(packbytes,4));
		
		csrb.setMsgId(Bytes8ToLong(packbytes,8));
		csrb.setResult(Bytes4ToInt(packbytes,16));
		return csrb.builder();
	}

}
