/*
 * 创建日期 2004-9-17
 *
 * 
 */
package com.ll.smsbeans.cmpp3;

import com.ll.smsbeans.Packet;


/**
*
* @author  listlike <a href="mailto:listlike@hotmail.com">
*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
*
*/
public class CmppConnectRespBuilder extends CmppPacketBuilder
{

	int status;
	String AuthenticatorISMG;
	int Version;
	
	
	public CmppConnectRespBuilder()
	{
		reset();
	}
	
	
	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#builder()
	 */
	public Packet builder()
	{

		return new CmppConnectResp(this);
	}

	/**
		 * @param authenticatorISMG
		 */
	public void setAuthenticatorISMG(String authenticatorISMG)
	{
		AuthenticatorISMG = authenticatorISMG;
	}

	/**
	 * @param stat
	 */
	public void setStatus(int stat)
	{
		status = stat;
	}

	/**
	 * @param i
	 */
	public void setVersion(int version)
	{
		Version = version;
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
	 * @see com.ll.smsbeans.PacketBuilder#reset()
	 */
	public void reset()
	{
		SequenceId=0; 
		
		status = -1;
		AuthenticatorISMG = "";
		Version = 0;

	}

}
