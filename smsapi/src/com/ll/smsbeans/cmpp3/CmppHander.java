/*
 * 创建日期 2004-9-11
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
public abstract class CmppHander
{
	protected int Bytes4ToInt(byte mybytes[])
	{
		return Bytes4ToInt(mybytes, 0);
	}

	protected int Bytes4ToInt(byte mybytes[], int index)
	{
		int tmp =
			(0xff & mybytes[0 + index])
				<< 24 | (0xff & mybytes[1 + index])
				<< 16 | (0xff & mybytes[2 + index])
				<< 8 | 0xff & mybytes[3
				+ index];
		return tmp;
	}

	protected long Bytes8ToLong(byte mybytes[])
	{
		return Bytes8ToLong(mybytes, 0);
	}

	protected long Bytes8ToLong(byte mybytes[], int index)
	{
		long tmp1 =
			(0xff & mybytes[3 + index])
				<< 24 | (0xff & mybytes[2 + index])
				<< 16 | (0xff & mybytes[1 + index])
				<< 8 | (0xff & mybytes[index]);
				
		 

		long tmp2 =
			(0xff & mybytes[7 + index])
				<< 24 | (0xff & mybytes[6 + index])
				<< 16 | (0xff & mybytes[5 + index])
				<< 8 | (0xff & mybytes[4 + index]);
		
		
		long tmp=((tmp2 <<32) & 0xffffffff00000000L) + (tmp1 & 0x00000000ffffffffL) ;
		return tmp;
	}

	protected String BytesToString(byte mybytes[], int len)
	{
		return BytesToString(mybytes, 0, len);
	}

	protected String BytesToString(byte mybytes[], int index, int len)
	{

		byte[] sbytes = new byte[len];

		int slen = 0;
		for (int i = 0; i < len; i++)
		{
			sbytes[i] = mybytes[index + i];
			if (sbytes[i] == 0)
			{
				slen = i;
				break;
			}
		}
		slen = slen == 0 ? len : slen;
		return new String(sbytes, 0, slen);
	}

	public abstract Packet packetBuiler(byte[] packbytes) throws Exception;

}
