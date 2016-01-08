// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ConsoleLogFormatter.java

package com.ll.smsbeans.log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleLogFormatter extends Formatter
{

    private static final String _ = " ";
    private static final String CRLF = "\r\n";

    public ConsoleLogFormatter()
    {
    }

    public String format(LogRecord record)
    {
        StringBuffer output = new StringBuffer();
		output.append(LogCommon.getDateTime() + _);
		output.append(record.getSourceClassName() + _);
		output.append(record.getSourceMethodName()+ _);
		
		output.append(CRLF);
		output.append(record.getThreadID() + _ + record.getLevel() + ":" + record.getMessage());
		output.append("\r\n----------------------END RECORD----------------------\r\n");
		return output.toString();
    }
}
