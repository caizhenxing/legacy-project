package et.bo.callcenter.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * ��Ա������Ϣ����
 * @author guxiaofeng
 *
 */
public class PoliceCallin {
	/*
	 * ��Ϣ��ˮ��,�����������־һ��.
	 */
	private String id;
	/*
	 * ����
	 */
	private String fuzzNum;
	/*
	 * ��֤����
	 */
	private int num;
	/*
	 * �Ƿ���Ч�ĺ���
	 */
	private String ValidInfo;
	/*
	 * ��ϯԱ
	 */
	private String operatorNum;
	/*
	 * ҵ������ˮ�ӱ�
	 */
	private List callinInfo = new ArrayList();
	/*
	 * ��Ա������Ϣ������ڴ�洢.��idΪ����.
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
