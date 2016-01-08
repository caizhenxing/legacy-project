/**
 * 	@(#)AbsMissionBean.java   2007-2-28 下午01:23:16
 *	 。 
 *	 
 */
package et.bo.callcenter.operation;

import java.util.HashMap;
import java.util.Map;

 /**
 * @author zhaoyifei
 * @version 2007-2-28
 * @see
 */
public abstract class AbsMissionBean {

	protected Map<String,Object> params=new HashMap<String,Object>();
	
	/**
	 * 任务名
	 */
	protected String mn;
	
	/**
	 * 端口号
	 */
	protected int lineNum;
	
	protected LineService ls;
	/**
	 * @return the ls
	 */
	public LineService getLs() {
		return ls;
	}

	/**
	 * @param ls the ls to set
	 */
	public void setLs(LineService ls) {
		this.ls = ls;
	}

	/**
	 * @return the lineNum
	 */
	public int getLineNum() {
		return lineNum;
	}
	
	/**
	 * @param lineNum the lineNum to set
	 */
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}

	/**
	 * @return the mn
	 */
	public String getMn() {
		return mn;
	}

	/**
	 * @param mn the mn to set
	 */
	public void setMn(String mn) {
		this.mn = mn;
	}
	public void setParam(String key,Object value)
	{
		params.put(key, value);
	}
	
	public abstract void operate(EventBean eb);
	
	public abstract boolean isState(LineInfo li);
}
