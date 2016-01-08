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
public class CmppConnectRespHander extends CmppHander
{

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.cmpp3.cmpp3Hander#packetBuiler(byte[])
	 */
	public Packet packetBuiler(byte[] packbytes) throws Exception
	{
		// TODO 自动生成方法存根
		CmppConnectRespBuilder ccrb =new CmppConnectRespBuilder();
		
		
		ccrb.setSequenceId(Bytes4ToInt(packbytes,4));
		ccrb.setStatus(Bytes4ToInt(packbytes,8) );
		ccrb.setAuthenticatorISMG(BytesToString(packbytes,12,16));
		ccrb.setVersion(packbytes[28]);
		
		return ccrb.builder();
		
	}

}
