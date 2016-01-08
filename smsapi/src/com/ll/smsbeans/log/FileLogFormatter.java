// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   FileLogFormatter.java

package com.ll.smsbeans.log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FileLogFormatter extends Formatter
{

    private static final String CRLF = "\r\n";
    private static final String _ = "\t";

    public FileLogFormatter()
    {
    }

    public String format(LogRecord record)
    {
        StringBuffer output = new StringBuffer();
        output.append(record.getMillis());
        output.append("\t");
        output.append(record.getLevel().getName());
        output.append("\t");
        output.append(record.getThreadID());
        output.append("\t");
        output.append(record.getLoggerName());
        output.append("\t");
        output.append(record.getMessage());
        output.append("\r\n");
        
        return output.toString();
    }
}
