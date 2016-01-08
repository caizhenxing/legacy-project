/**
 * 	@(#)ConferenceInfo.java   2007-1-15 下午04:39:43
 *	 。 
 *	 
 */
package et.bo.callcenter.operation;

import java.util.List;
import java.util.Map;


 /**
 * @author zhaoyifei
 * @version 2007-1-15
 * @see
 */
public class ConferenceInfo {

	/**
	 * 会议号
	 */
	private int confNum;
	/**
	 * 主持人
	 */
	private int chairman;
	/**
	 * 参加会议人
	 */
	private Map<Integer,LineService> lines;
	/**
	 * 录音文件
	 */
	private String recordFile;
	
	public ConferenceInfo(int i,LineService ls)
	{
		confNum=i;
		chairman=ls.getLineNum();
		addLine(ls);
	}
	
	public int getConfNum() {
		return confNum;
	}
	public void setConfNum(int confNum) {
		this.confNum = confNum;
	}
	
	public boolean addLine(LineService ls)
	{
		if(lines.containsKey(ls.getLineNum()))
			return false;
		lines.put(Integer.valueOf(ls.getLineNum()),ls);
		return true;
	}
	public boolean subLine(LineService ls)
	{
		if(!lines.containsKey(ls.getLineNum()))
			return false;
		lines.remove(ls.getLineNum());
		return true;
	}
	public String getRecordFile() {
		return recordFile;
	}
	public void setRecordFile(String recordFile) {
		this.recordFile = recordFile;
	}
	
	public boolean containLine(LineService ls)
	{
		return lines.containsKey(ls.getLineNum());
	}
}
