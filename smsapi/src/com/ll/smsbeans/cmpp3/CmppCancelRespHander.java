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
public class CmppCancelRespHander extends CmppHander
{

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.cmpp3.cmpp3Hander#packetBuiler(byte[])
	 */
	public Packet packetBuiler(byte[] packbytes) throws Exception
	{
		
		CmppCancelRespBuilder ccrb=new CmppCancelRespBuilder();
		
		ccrb.setSequenceId(Bytes4ToInt(packbytes,4));
		
		ccrb.setSuccessId (Bytes4ToInt(packbytes,8));
		
		
		return ccrb.builder();
	}

}
