/**
 * 	@(#)TcIniType.java   2006-11-27 上午09:24:00
 *	 。 
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
	 * 卡的总数
	 */
	public int wCardNo;
	
	/**
	 * 卡的类型
	 */
	public int wCardType;
	/**
	 * 卡之间是否连通
	 */
	public int wConnect;
	/**
	 * 卡断号
	 */
	public int wIRQ;
	/**
	 * 卡的安装目录
	 */
	public char[] cbDir;
	/**
	 * 每块卡的地址
	 */
	public int[] wAddress;
	/**
	 * 软件主版本号
	 */
	public int wMajorVer;
	/**
	 * 软件辅版本号
	 */
	public int wMinorVer;
	/**
	 * 通道类型
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
