/*
 * 创建日期 2004-9-11
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.ll.smsbeans.Packet;

/**
 * @author Administrator
 *
 * 
 */
public abstract class CmppPacket implements Packet
{

	ByteArrayOutputStream _bao;

	//protected int Total_Length;	
	protected int CommandId;
	protected int SequenceId;

	protected CmppPacket()
	{
		_bao = new ByteArrayOutputStream();
	}

	protected void writeDD(long value)
	{	
		_bao.write((int) (value & 0xff));
		_bao.write((int) (value >> 8 & 0xff));
		_bao.write((int) (value >> 16 & 0xff));
		_bao.write((int) (value >> 24 & 0xff));
		_bao.write((int) (value >> 32 & 0xff));
		_bao.write((int) (value >> 40 & 0xff));
		_bao.write((int) (value >> 48 & 0xff));
		_bao.write((int) (value >> 56 & 0xff));
		
		
	}

	protected void writeD(int value)
	{
		_bao.write(value >> 24 & 0xff);
		_bao.write(value >> 16 & 0xff);
		_bao.write(value >> 8 & 0xff);
		_bao.write(value & 0xff);
	}

	protected void writeH(int value)
	{
		_bao.write(value >> 8 & 0xff);
		_bao.write(value & 0xff);

	}

	protected void writeC(int value)
	{
		_bao.write(value & 0xff);
	}

	protected void writeBytes(byte[] ba, int len)
	{
		
		for (int i = 0; i < len; i++)
		{
			if(ba==null || i>= ba.length )
				_bao.write(0x00);
			else
				_bao.write(ba[i] & 0xff);
		}

	}

	protected void writeBytes(byte[] ba) throws IOException
	{
		_bao.write(ba);

	}

	protected void writeS(String str, int len) throws IOException
	{
		str=str==null?"":str;
		byte[] tb = new byte[len];
		byte[] sb = str.getBytes();
		for (int i = 0; i < len; i++)
		{
			if (i < sb.length)
				tb[i] = sb[i];
			else
				tb[i] = 0;
		}
		_bao.write(tb);
		;
	}

	
	public boolean isResponse()
	{
		return CommandId < 0 ? true : false;
	}

	
	public int getSequenceId()
	{
		return this.SequenceId;
	}

	public int getLength()
	{
		return _bao.size() + 4;
	}

	protected byte[] getBytes()
	{
		return _bao.toByteArray();
	}

	/**
	 * @return
	 */
	public int getCommandId()
	{
		return CommandId;
	}

	

}
