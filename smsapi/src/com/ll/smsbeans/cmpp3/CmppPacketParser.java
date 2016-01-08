/*
 * 创建日期 2004-9-12
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import java.io.IOException;

import com.ll.smsbeans.Packet;

/**
 * @author Administrator
 *
 * 
 */
public class CmppPacketParser
{
	
	private static CmppHander[] mainParserList=new CmppHander[9];
	private static CmppHander[] respParserList=new CmppHander[9];
	 
	/**
	 * 
	 */
	public CmppPacketParser()
	{
		
		respParserList[1]=new CmppConnectRespHander();
		respParserList[2]=new CmppTerminateRespHander();
		respParserList[4]=new CmppSubmitRespHander();
		respParserList[5]=new CmppDeliverRespHander();
		respParserList[6]=new CmppQueryRespHander();
		respParserList[7]=new CmppCancelRespHander();
		respParserList[8]=new CmppActiveTestRespHander();
		
		mainParserList[1]=new CmppConnectHander();
		mainParserList[2]=new CmppTerminateHander();
		mainParserList[4]=new CmppSubmitHander();
		mainParserList[5]=new CmppDeliverHander();
		mainParserList[6]=new CmppQueryHander();
		mainParserList[7]=new CmppCancelHander();
		mainParserList[8]=new CmppActiveTestHander();
		// TODO 自动生成构造函数存根
	}
	
	public Packet parser(byte[] databytes) throws IOException,Exception
	{
		int commandid;
		CmppHander ch;
		
		ch=null;
		if((databytes[0] & 0xff)== 0x80)
		{	
			commandid=(0) << 24 | (0xff & databytes[1]) << 16 | (0xff & databytes[2]) << 8 | 0xff & databytes[3];
			if (commandid>0 && commandid <9)
				ch=	respParserList[commandid];
		}
		else
		{
			commandid=(0xff & databytes[0]) << 24 | (0xff & databytes[1]) << 16 | (0xff & databytes[2]) << 8 | 0xff & databytes[3];
			if (commandid>0 && commandid <9)
				ch=mainParserList[commandid];
		}

		if(ch!=null)
			return ch.packetBuiler(databytes );
		else
			return null;	
	}
}
