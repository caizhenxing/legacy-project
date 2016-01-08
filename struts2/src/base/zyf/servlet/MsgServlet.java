/**
 * 
 * 制作时间：Nov 13, 20073:54:53 PM
 * 文件名：MsgServlet.java
 * 制作者：zhaoyifei
 * 
 */
package base.zyf.servlet;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import base.zyf.common.util.Constants;

/**
 * @author zhaoyifei
 *
 */
public class MsgServlet extends HttpServlet {
	
	// js file name which saves message as a hashmap
	private static final String OUTPUT_MSG_JS_FILE_NAME = "/base/js/msgInfo.js";
	
	public void init() throws ServletException {
		
		super.init();
		FileOutputStream msgJs = null;
		BufferedOutputStream msgJsBuffer = null;
		try {
			// Context path
			String contextPath = super.getServletContext().getRealPath("/");
			// Fullpath of the message property file
			
			// Content of script file which define message's array according to msg property file
			StringBuffer msgScriptBuffer = new StringBuffer();
			
			msgJs = new FileOutputStream(contextPath + OUTPUT_MSG_JS_FILE_NAME, false);
			msgJsBuffer = new BufferedOutputStream(msgJs);
			Constants.init();
			this.getServletContext().setAttribute("sysParameter", Constants.getSysParameter());
			/*
			 * Define script hash
			 */
			msgScriptBuffer.append("var msgHash = {");
			// Read message from property file
			for (Enumeration msgId = Constants.getSysParameter().propertyNames(); msgId.hasMoreElements(); ) {
				String tmp = (String)msgId.nextElement();
				msgScriptBuffer.append("\"");
				msgScriptBuffer.append(tmp);
				msgScriptBuffer.append("\"");
				msgScriptBuffer.append(":");
				msgScriptBuffer.append("\"");
				msgScriptBuffer.append(Constants.getProperty(tmp).replaceAll("\"","\\\\\""));
				msgScriptBuffer.append("\",");
				msgScriptBuffer.append("\r\n");
			}
			// delete the last useless ","
			msgScriptBuffer.deleteCharAt(msgScriptBuffer.length() - 3);
			msgScriptBuffer.append("};");
			msgJsBuffer.write(msgScriptBuffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// release resources
			try {
				msgJsBuffer.flush();
				msgJsBuffer.close();
				msgJs.flush();
				msgJs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
