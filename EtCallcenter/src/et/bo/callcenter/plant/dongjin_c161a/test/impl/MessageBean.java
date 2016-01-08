/**
 * 	@(#)MessageBean.java   2007-1-5 ÉÏÎç09:57:02
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.test.impl;

import java.util.HashMap;
import java.util.Map;

 /**
 * @author zhaoyifei
 * @version 2007-1-5
 * @see
 */
public class MessageBean {

	private int line;
	public enum LineType{INLINE,OUTLINE,RECORDLINE,MEETINGLINE,OTHER};
	public enum MessageType{};
	private LineType lt;
	private MessageType mt;
	private Map<String,Object> params;
	/**
	 * 
	 */
	public MessageBean() {
		// TODO Auto-generated constructor stub
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public LineType getLt() {
		return lt;
	}
	public void setLt(LineType lt) {
		this.lt = lt;
	}
	public MessageType getMt() {
		return mt;
	}
	public void setMt(MessageType mt) {
		this.mt = mt;
	}
	public Object getParams(String pname) {
		if(params!=null)
		return params.get(pname);
		else
			return null;
	}
	public void setParams(String pname,Object pvalue) {
		if(params==null)
			params=new HashMap<String,Object>();
		params.put(pname,pvalue);
	}
	
}
