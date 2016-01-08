/*
 * 创建日期 2004-9-11
 *
 * 
 */
package com.ll.smsbeans.test;

import com.ll.smsbeans.cmpp3.CmppConnect;
import com.ll.smsbeans.cmpp3.CmppConnectBuilder;


/**
 * @author Administrator
 *
 * 
 */
public class cmppConnectTest
{

	static public long Bytes8ToLong(byte mybytes[], int index)
	{
		long tmp1 =
			(0xff & mybytes[3 + index])
				<< 24 | (0xff & mybytes[2 + index])
				<< 16 | (0xff & mybytes[1 + index])
				<< 8 | (0xff & mybytes[index]);
				
		System.err.println(" tmp1=" + (tmp1 & 0x0000ffff)); 

		long tmp2 =
			(0xff & mybytes[7 + index])
				<< 24 | (0xff & mybytes[6 + index])
				<< 16 | (0xff & mybytes[5 + index])
				<< 8 | (0xff & mybytes[4 + index]);
		System.err.println(" tmp2=" + ((tmp2 <<32) & 0xffff0000));
		
		long tmp=((tmp2 <<32) & 0xffffffff00000000L) + (tmp1 & 0x00000000ffffffffL) ;
		return tmp;
	}

	public static void main(String[] args)
	{

		byte[] lb=new byte[8];
		lb[0]=(byte)0xcc;
		lb[1]=(byte)0x02;
		lb[2]=(byte)0xe9;
		lb[3]=(byte)0x03;
//		
//		lb[0]=(byte)0x00;
//				lb[1]=(byte)0x00;
//				lb[2]=(byte)0x00;
//				lb[3]=(byte)0x00;
		lb[4]=(byte)0xc0;
		lb[5]=(byte)0x10;
		lb[6]=(byte)0x89;
		lb[7]=(byte)0x9a;
		
		System.err.println(" V=" + Bytes8ToLong(lb,0)); 
		
		CmppConnectBuilder ccb = new CmppConnectBuilder();
		String dateString = "0913185441";

		ccb.setSourceAddr("901234");
		ccb.setSpPassword("1234");
		ccb.setTimestamp(dateString);

		CmppConnect cm = (CmppConnect) ccb.builder();
		try
		{

			byte[] ba = cm.getContent();

			for (int i = 0; i < ba.length; i++)
			{
				System.out.println("ba[" + i + "]=" + ba[i]);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
