/*
 * 创建日期 2004-9-28
 *
 * 
 */
package com.ll.smsbeans.log;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
* 日志操作需要的函数
*
* @author  listlike <a href="mailto:listlike@hotmail.com">
*                      <i>&lt;listlike@hotmail.com&gt;</i></a>
*
*/
public class LogCommon
{
	
	public static Level DEBUG_LEVEL=debugLevel.DEBUG;
	/**
	 * 得到日期字符串。
	 * @return
	 */
	public static String getDateTime()
	{
		SimpleDateFormat formatter =
			new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	/**
	 * 将发送或者接收的数据转换为字符串。
	 * @param typeStr
	 * @param dataLen
	 * @param logData
	 * @return
	 */
	public static String getLogBin(
		String typeStr,
		byte[] dataLen,
		byte[] logData)
	{
		StringBuffer printStr = new StringBuffer();
		String tStr;
		String space;
		int m = 0;
		int ret = 0;
		int len = logData.length;

		printStr.append(getDateTime());
		printStr.append(' ');
		printStr.append(typeStr);
		printStr.append(':');

		for (int i = 0; i < 4 + len; i++)
		{
			space = " ";
			if (i % 16 == 0)
			{
				printStr.append('\n');
				m = i / 16;
				tStr = Long.toHexString(m);
				if (tStr.length() == 1)
					tStr = String.valueOf('0') + tStr;

				printStr.append('[');
				for (int j = 0; j < 8 - tStr.length(); j++)
				{
					printStr.append('0');
				}
				printStr.append(tStr);
				printStr.append(']');
				printStr.append(' ');
				printStr.append(' ');
			} else
			{
				if (i % 16 == 7)
					space = "  ";

			}

			if (i < 4)
				tStr = Integer.toString((int) dataLen[i] & (int) 255, 16);
			else
				tStr = Integer.toString((int) logData[i - 4] & (int) 255, 16);
			if (tStr.length() == 1)
				tStr = String.valueOf('0') + tStr;
			printStr.append(tStr);
			printStr.append(space);
			//printStr.append(' ');

		}
		printStr.append('\n');
		printStr.append("=====================End======================");
		return printStr.toString();

	}

	/**
	 * 日志初试化。
	 *
	 */
	public static void logInit()
	{
		File logFolder = new File("log");
		logFolder.mkdir();
		InputStream is = LogCommon.class.getResourceAsStream("/log.cfg");
		try
		{
			LogManager.getLogManager().readConfiguration(is);
			is.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}
		
		Logger log = Logger.getLogger("com.ll.smsbeans"); 
 
		log.setLevel(DEBUG_LEVEL); 
   }
}

class debugLevel extends Level
{
	//	  定义自己的消息级别SYSE 
	public static final Level DEBUG =
		new debugLevel("DEBUG", Level.FINEST.intValue() -100);
	public debugLevel(String ln, int v)
	{
		super(ln, v);
	}
}
