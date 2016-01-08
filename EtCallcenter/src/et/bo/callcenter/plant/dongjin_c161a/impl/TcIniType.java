/**
 * 	@(#)TcIniType.java   2006-11-27 ����09:24:00
 *	 �� 
 *	 
 */
package et.bo.callcenter.plant.dongjin_c161a.impl;

import java.util.List;

import excellence.common.util.regex.AnalyseString;



 /**
 * @author zhaoyifei
 * @version 2006-11-27
 * @see
 */
public class TcIniType {

	/**
	 * ��������
	 */
	public int wCardNo;
	
	/**
	 * ��������
	 */
	public int wCardType;
	/**
	 * ��֮���Ƿ���ͨ
	 */
	public int wConnect;
	/**
	 * ���Ϻ�
	 */
	public int wIRQ;
	/**
	 * ���İ�װĿ¼
	 */
	public char[] cbDir;
	/**
	 * ÿ�鿨�ĵ�ַ
	 */
	public int[] wAddress;
	/**
	 * ������汾��
	 */
	public int wMajorVer;
	/**
	 * ������汾��
	 */
	public int wMinorVer;
	/**
	 * ͨ������
	 */
	public int[] wChType;
	
	public int wMachineNo;
	
	public TcIniType(String[] p) {
		super();
		this.setPara(p);
	}

	public void setPara(String[] p)
	{
		this.wCardNo=Integer.parseInt(p[0]);
		this.wCardType=Integer.parseInt(p[1]);
		this.wConnect=Integer.parseInt(p[2]);
		this.wIRQ=Integer.parseInt(p[3]);
		this.cbDir=p[4].toCharArray();
		List<String> temp1=AnalyseString.parseString(p[5],",");
		this.wAddress=new int[temp1.size()];
		for(int i=0,size=temp1.size();i<size;i++)
		{
			wAddress[i]=Integer.parseInt(temp1.get(i));
		}
		this.wMajorVer=Integer.parseInt(p[6]);
		this.wMinorVer=Integer.parseInt(p[7]);
		List<String> temp2=AnalyseString.parseString(p[8],",");
		this.wChType=new int[temp2.size()];
		for(int i=0,size=temp2.size();i<size;i++)
		{
			wChType[i]=Integer.parseInt(temp2.get(i));
		}
		this.wMachineNo=Integer.parseInt(p[9]);
	}
	
}
