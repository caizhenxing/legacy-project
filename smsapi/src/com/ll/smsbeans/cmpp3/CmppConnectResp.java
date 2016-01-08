/*
 * 创建日期 2004-9-11
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import java.io.IOException;



/**
 * @author Administrator
 *
 * 
 */
public class CmppConnectResp extends CmppPacket
{
	int status;
	String AuthenticatorISMG;
	int Version;

	public CmppConnectResp(CmppConnectRespBuilder builder)
	{
		CommandId = CmppPacketCommon.ID_CMPP_CONNECT_RESP; //0x80000001;
		SequenceId = builder.getSequenceId();
		
		status = builder.getStatus();
		AuthenticatorISMG = builder.getAuthenticatorISMG();
		Version = builder.getVersion();

	}

	public byte[] getContent() throws IOException
	{

		return getBytes();
	}

	/**
	 * @return
	 */
	public String getAuthenticatorISMG()
	{
		return AuthenticatorISMG;
	}

	/**
	 * @return
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * @return
	 */
	public int getVersion()
	{
		return Version;
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
			+ "\n  status="
			+ status
			+ "\n  AuthenticatorISMG="
			+ AuthenticatorISMG
			+ "\n  Version="
			+ Version;
	}

}
