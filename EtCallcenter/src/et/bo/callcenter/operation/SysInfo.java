/**
 * 	@(#)SysInfo.java   2007-1-16 ÉÏÎç10:24:54
 *	 ¡£ 
 *	 
 */
package et.bo.callcenter.operation;

import java.util.ArrayList;
import java.util.List;

 /**
 * @author zhaoyifei
 * @version 2007-1-16
 * @see
 */
public class SysInfo {

	private List<LineInfo> lines=new ArrayList<LineInfo>();
	private int lineCount;
	private boolean isSupportCalerId;
	private String id;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the isSupportCalerId
	 */
	public boolean isSupportCalerId() {
		return isSupportCalerId;
	}
	/**
	 * @param isSupportCalerId the isSupportCalerId to set
	 */
	public void setSupportCalerId(boolean isSupportCalerId) {
		this.isSupportCalerId = isSupportCalerId;
	}
	/**
	 * @return the lineCount
	 */
	public int getLineCount() {
		return lineCount;
	}
	/**
	 * @param lineCount the lineCount to set
	 */
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
	/**
	 * @return the lines
	 */
	public List<LineInfo> getLines() {
		return lines;
	}
	/**
	 * @param lines the lines to set
	 */
	public void setLines(List<LineInfo> lines) {
		this.lines = lines;
	}

}
