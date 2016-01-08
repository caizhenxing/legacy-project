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
public class CmppSubmitHander extends CmppHander
{

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.cmpp3.cmpp3Hander#packetBuiler(byte[])
	 */
	public Packet packetBuiler(byte[] packbytes) throws Exception
	{
		
		CmppSubmitBuilder csb =new CmppSubmitBuilder();
		
		
		csb.setSequenceId(Bytes4ToInt(packbytes,4));
		//TODO 加入其它属性的生成
		
		return csb.builder() ;
	}

}
