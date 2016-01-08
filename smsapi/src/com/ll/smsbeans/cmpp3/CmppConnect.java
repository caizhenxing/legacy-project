/*
 * 创建日期 2004-9-10
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import java.io.IOException;

import com.ll.smsbeans.MD5;

/**
 * @author Administrator
 *
 * 
 */
public class CmppConnect extends CmppPacket
{

	String SourceAddr;
	String SpPassword;
	int Version;
	String timestamp;

	
	public CmppConnect(CmppConnectBuilder builder)
	{
		CommandId = CmppPacketCommon.ID_CMPP_CONNECT; //0x00000001
		builder.CreateNextSequenceId(); 
		SequenceId = builder.getSequenceId();
		
		this.SourceAddr = builder.getSourceAddr() ;
		this.SpPassword = builder.getSpPassword();
		Version = builder.getVersion() ;
		this.timestamp = builder.getTimestamp() ;
		
		
	}

	/* （非 Javadoc）
	 * @see com.ll.smsbean.Packet#getContent()
	 */
	public byte[] getContent() throws IOException
	{

		this.writeD(CommandId);
		this.writeD(SequenceId);

		byte[] bSourceAddr = SourceAddr.getBytes();
		byte[] bAuthenticatorSP = SpPassword.getBytes();
		byte[] btimestamp = timestamp.getBytes();

		byte[] bmd5 =
			new byte[bSourceAddr.length
				+ bAuthenticatorSP.length
				+ btimestamp.length
				+ 9];
		byte[] md5Bytes;

		int i;
		for (i = 0; i < 6; i++)
		{
			if (i < bSourceAddr.length)
			{
				bmd5[i] = bSourceAddr[i];
				writeC(bSourceAddr[i]);
			} else
			{
				writeC(0);
			}
		}

		for (i = 0; i < 9; i++)
		{
			bmd5[i + bSourceAddr.length] = 0;
		}

		for (i = 0; i < bAuthenticatorSP.length; i++)
		{
			bmd5[i + bSourceAddr.length + 9] = bAuthenticatorSP[i];
		}

		for (i = 0; i < btimestamp.length; i++)
		{
			bmd5[i + SourceAddr.length() + SpPassword.length() + 9] =
				btimestamp[i];
		}

		md5Bytes = MD5.md5(bmd5);

		writeBytes(md5Bytes);

		writeC(Version);
		writeD(Integer.parseInt(timestamp));

		return getBytes();
	}

	/* （非 Javadoc）
		 * @see java.lang.Object#toString()
		 */
	public String toString()
	{
		// TODO 自动生成方法存根
		return this.getClass().getName()
			+ ": SequenceId="
			+ SequenceId
			+ "\n  SourceAddr="
			+ SourceAddr
			+ "\n  SpPassword="
			+ SpPassword
			+ "\n  SequenceId="
			+ SequenceId
			+ "\n  timestamp="
			+ timestamp
			+ "\n  Version="
			+ Version;
	}

	/**
	 * @return
	 */
	public String getSourceAddr()
	{
		return SourceAddr;
	}

	/**
	 * @return
	 */
	public String getSpPassword()
	{
		return SpPassword;
	}

	/**
	 * @return
	 */
	public String getTimestamp()
	{
		return timestamp;
	}

	/**
	 * @return
	 */
	public int getVersion()
	{
		return Version;
	}

}
