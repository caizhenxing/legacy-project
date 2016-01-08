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
public class CmppDeliverHander extends CmppHander
{

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.cmpp3.cmpp3Hander#packetBuiler(byte[])
	 */
	public Packet packetBuiler(byte[] packbytes) throws Exception
	{

		CmppDeliverBuilder cdb = new CmppDeliverBuilder();

		cdb.setSequenceId(Bytes4ToInt(packbytes, 4));
		//TODO 加入其它属性的生成
		cdb.setMsgId(Bytes8ToLong(packbytes, 8));
		cdb.setDstId(BytesToString(packbytes, 16, 21));
		cdb.setServiceId(BytesToString(packbytes, 37, 10));
		cdb.setTpPid(packbytes[47]);
		cdb.setTpUdhi(packbytes[48]);
		cdb.setMsgFmt(packbytes[49]);
		cdb.setSrcTermId(BytesToString(packbytes, 50, 32));
		cdb.setSrcTermType(packbytes[82]);
		cdb.setRegisterDelivery(packbytes[83]);

		int msgLen = (packbytes[84] & 0xff);
		byte[] mc = new byte[msgLen];
		for (int i = 0; i < msgLen; i++)
		{
			mc[i] = packbytes[85 + i];
		}
		cdb.setMsgContent(mc);

		cdb.setLinkId(BytesToString(packbytes, 85 + msgLen, 20));

		return cdb.builder();
	}

}
