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
public class CmppConnectBuilder extends CmppPacketBuilder
{

	String SourceAddr;
	String SpPassword;
	int Version;
	String timestamp;
	
	public CmppConnectBuilder()
	{
		reset();
	}
	
	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#builder()
	 */
	public Packet builder()
	{
		
		return new CmppConnect(this);
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

	/**
	 * @param string
	 */
	public void setSourceAddr(String string)
	{
		SourceAddr = string;
	}

	/**
	 * @param string
	 */
	public void setSpPassword(String string)
	{
		SpPassword = string;
	}

	/**
	 * @param string
	 */
	public void setTimestamp(String string)
	{
		timestamp = string;
	}

	/**
	 * @param i
	 */
	public void setVersion(int i)
	{
		Version = i;
	}

	/* （非 Javadoc）
	 * @see com.ll.smsbeans.PacketBuilder#reset()
	 */
	public void reset()
	{
		SequenceId=0; 
		
		SourceAddr = "";
		SpPassword = "";
		Version = 3;
		timestamp = "";
		
	}

}
