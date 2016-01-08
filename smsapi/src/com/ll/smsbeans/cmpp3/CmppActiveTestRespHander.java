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
public class CmppActiveTestRespHander extends CmppHander
{

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.cmpp3.cmpp3Hander#packetBuiler(byte[])
	 */
	public Packet packetBuiler(byte[] packbytes) throws Exception
	{
		
		CmppActiveTestRespBuilder catb=new CmppActiveTestRespBuilder();
		
		catb.setSequenceId(Bytes4ToInt(packbytes,4));
		catb.setReserved(packbytes[8]);
		
		
		return catb.builder();
	}

}
