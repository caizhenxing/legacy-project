package com.ll.smsbeans;

import java.io.IOException;
import java.io.Serializable;

/**
 *一个Packet就是接收或者发送数据包的一个单元。
 * 
 * @author  listlike <a href="mailto:listlike@hotmail.com">
 *                      <i>&lt;listlike@hotmail.com&gt;</i></a>
 */

public interface Packet extends Serializable
{
	/**
	 * 对当前的数据包打包成字节数组，以便于发送。
	 * @return 返回字节数组
	 * @throws IOException
	 */
	public abstract byte[] getContent() throws IOException;

}
