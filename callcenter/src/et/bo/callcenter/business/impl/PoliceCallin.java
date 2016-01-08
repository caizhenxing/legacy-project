package et.bo.callcenter.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 警员呼入信息主表
 * @author guxiaofeng
 *
 */
public class PoliceCallin {
	/*
	 * 信息流水号,与呼叫中心日志一致.
	 */
	private String id;
	/*
	 * 警号
	 */
	private String fuzzNum;
	/*
	 * 验证次数
	 */
	private int num;
	/*
	 * 是否有效的呼入
	 */
	private String ValidInfo;
	/*
	 * 坐席员
	 */
	private String operatorNum;
	/*
	 * 业务主流水子表
	 */
	private List callinInfo = new ArrayList();
	/*
	 * 警员呼入信息主表的内存存储.以id为索引.
	 */
	private static Map callinIdMap=new HashMap();//key is id,value is PoliceCallin.
	
	public List getCallinInfo() {
		return callinInfo;
	}
	public void setCallinInfo(List callinInfo) {
		this.callinInfo = callinInfo;
	}
	public String getFuzzNum() {
		return fuzzNum;
	}
	public void setFuzzNum(String fuzzNum) {
		this.fuzzNum = fuzzNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getOperatorNum() {
		return operatorNum;
	}
	public void setOperatorNum(String operatorNum) {
		this.operatorNum = operatorNum;
	}
	public static Map getCallinIdMap() {
		return callinIdMap;
	}
	public String getValidInfo() {
		return ValidInfo;
	}
	public void setValidInfo(String validInfo) {
		ValidInfo = validInfo;
	}
}
