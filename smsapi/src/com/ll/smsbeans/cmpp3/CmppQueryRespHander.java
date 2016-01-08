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
public class CmppQueryRespHander extends CmppHander
{

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.cmpp3.cmpp3Hander#packetBuiler(byte[])
	 */
	public Packet packetBuiler(byte[] packbytes) throws Exception
	{
		
		CmppQueryRespBuilder cqrb =new CmppQueryRespBuilder();
		
		
		cqrb.setSequenceId(Bytes4ToInt(packbytes,4));
		
		cqrb.setTime(BytesToString(packbytes,8,8 ) );
		cqrb.setQueryType(packbytes[16]);
		cqrb.setQueryCode(BytesToString(packbytes,17,10) );
		
		cqrb.setMtTlMsg(Bytes4ToInt(packbytes,27 ) );
		cqrb.setMtTlUsr(Bytes4ToInt(packbytes,31 ) );
		cqrb.setMtScs(Bytes4ToInt(packbytes,35 ) );
		cqrb.setMtWt(Bytes4ToInt(packbytes,39 ) );
		cqrb.setMtFl(Bytes4ToInt(packbytes,43 ) );
		cqrb.setMoScs(Bytes4ToInt(packbytes,47 ) );
		cqrb.setMoWt(Bytes4ToInt(packbytes,51 ) );
		cqrb.setMoFl(Bytes4ToInt(packbytes,55 ) );	
		
			
		
		return cqrb.builder();
	}

}
