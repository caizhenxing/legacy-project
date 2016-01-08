package et.bo.callcenter.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstOperI;
import et.bo.callcenter.ConstantsI;

public class CardStateHelper{
	private static Log log = LogFactory.getLog(CardStateHelper.class);	
	/*
	 * for 格式化电话号码所用的空格
	 */
	private static String[] sBlank=null;
	/*
	 * 初始化空格,从数组0-15,都是空格
	 */
	//---------------------------------------------zuoxi---------------------------
	/*
	 * 工作状态[离席、入席、振铃、通话]-离席
	 */
	private static final String WORK_LEAVE="101";
	/*
	 * 工作状态[离席、入席、振铃、通话]-入席
	 */
	private static final String WORK_IN="1";
	/*
	 * 工作状态[离席、入席、振铃、通话]-振铃
	 */
	private static final String WORK_RING="100";
	/*
	 * 工作状态[离席、入席、振铃、通话]-通话
	 */
	private static final String WORK_TEL="5";
	//---------------------------------------------zuoxi---------------------------
	private static void initBlank(){		
		sBlank = new String[ConstantsI.PHONE_NUM_FORMAT_LEN+1];
		sBlank[0] ="";
		for(int i=1;i<=ConstantsI.PHONE_NUM_FORMAT_LEN;i++){
			StringBuffer sb=new StringBuffer();
			for(int j=0;j<i;j++){
				sb.append(ConstantsI.BLANK);
			}
			sBlank[i] = sb.toString();
		}
	}

	/*
	 * 格式化成15位的电话号码
	 */
	private static String formatedPhoneNum(String phoneNum){
		
		if (null==sBlank) CardStateHelper.initBlank();
		if(null==phoneNum) return sBlank[ConstantsI.PHONE_NUM_FORMAT_LEN];
		int len=phoneNum.length();
		StringBuffer sb = new StringBuffer(phoneNum);
		sb.append(sBlank[ConstantsI.PHONE_NUM_FORMAT_LEN-len]);
		return sb.toString();		
	}
	/*
	 * 格式化成传回一个座席的字符串
	 */
	public static String formatOperatorStr(CardState cs){
		StringBuffer sb=new StringBuffer();
		//
		if(cs.getOperatorNum()==null){
			sb.append(ConstOperI.OPERATOR_NUM);
		}else{
			if(cs.getState()==cs.INNER_SIGNOUT)sb.append(ConstOperI.OPERATOR_NUM);
			else
			sb.append(cs.getOperatorNum().trim());
		}
			sb.append(ConstantsI.DELIM1);
////////////////////////
		if(cs.getState()==null){
//			sb.append(ConstOperI.OPERATOR_NUM);
			sb.append(CardStateHelper.WORK_LEAVE);
		}else{
			String sTmp = cs.getState().trim();
			CardState cs01=new CardState();
			
			if(cs01.INNER_CALLING.equals(sTmp)){
				sb.append(CardStateHelper.WORK_RING);
			}else if(cs01.INNER_IN.equals(sTmp)){
				sb.append(CardStateHelper.WORK_IN);
			}else if(cs01.INNER_NOBODY.equals(sTmp)){
				sb.append(CardStateHelper.WORK_LEAVE);
			}else if(cs01.INNER_OUT.equals(sTmp)){
				sb.append(CardStateHelper.WORK_LEAVE);
			}else if(cs01.INNER_RING.equals(sTmp)){
				sb.append(CardStateHelper.WORK_RING);
			}else if(cs01.INNER_SIGNIN.equals(sTmp)){
				sb.append(CardStateHelper.WORK_IN);
			}else if(cs01.INNER_SIGNOUT.equals(sTmp)){
				sb.append(CardStateHelper.WORK_LEAVE);
				System.err.print("=================="+CardStateHelper.WORK_LEAVE);
			}else if(cs01.OUTER_BUSY.equals(sTmp)){
				sb.append(CardStateHelper.WORK_TEL);
			}else if(cs01.OUTER_IDLE.equals(sTmp)){
				sb.append(CardStateHelper.WORK_LEAVE);
			}			
		}
////////////////////////
			sb.append(ConstantsI.DELIM1);
		if(cs.getPhoneNum()==null){
			sb.append("               ");
		}else{
			sb.append(formatedPhoneNum(cs.getPhoneNum()));
		}
			sb.append(ConstantsI.DELIM2);
		return sb.toString();
	}
	/*
	 * 传送到座席的字符串，多个串
	 */
	public static List transToOperator(){
		
		List l = new ArrayList();
//		first 是几个座席的状态
//		for(int i =0;i<innerActiveList.size();i++){
//			toOperatorStr((CardState)innerActiveList.get(i));
//		}
		for(int i =0;i<CardInfo.getInnerPortList().size();i++){
			String port = (String)CardInfo.getInnerPortList().get(i);
			String s;
			if(!CardState.isActiveInner(port)){
				continue;
			}else{
				s=formatOperatorStr(
						(CardState)CardState.getInnerActivePortMap().get(port)
						);
				System.err.println("&*&*&*&*&*");
				System.err.println(s);
				System.err.println("&*&*&*&*&*");
			}
//			sb.append(s);
			l.add(s);
		}
//		second 是几个待机的状态,此时还没有确定那个座席要接入。所以没有座席端口。
//		Iterator<CardState> i =CardState.getOuterWaitingMap().entrySet().iterator();
//		while(i.hasNext()){
//			CardState c=i.next();
//			String s=c.getClass().getName();
//			log.debug("!@#$%^&*()"+s);
//			CardState cs=(CardState)i.next();
//			sb.append(formatOperatorStr(cs));
//		}
		Iterator it =CardState.getOuterWaitingMap().keySet().iterator();
		while(it.hasNext()){
			String key=(String)it.next();			
			CardState cs=(CardState)CardState.getOuterWaitingMap().get(key);
//			sb.append(formatOperatorStr(cs));
			l.add(formatOperatorStr(cs));
		}
		int j =ConstOperI.OPERATOR_PANEL_NUM-l.size();
		for(int i=0;i<j;i++){
			l.add(ConstOperI.OPERATOR_NULL);
		}
		return l;
//		StringBuffer sb=new StringBuffer();
//		for(int i=0;i<ConstOperI.OPERATOR_PANEL_NUM;i++){
//			String s=(String)l.get(i);
//			sb.append(s);
//		}
//		return sb.toString();
	}
	/*
	 * 传送到工控机的参数
	 */
	public static List transToCard(){
		List list = new ArrayList();
//		for(int i=0;i<CardInfo.getInnerPortList().size();i++){
//			String port = (String)CardInfo.getInnerPortList().get(i);
//			String isHere = CardState.isActiveInner(port)?
//					ConstOperI.WORK_IN:ConstOperI.WORK_LEAVE;
//			String[] s={port,isHere};
//			list.add(s);			
//		}
		for(int i=0;i<CardInfo.getInnerPortList().size();i++){
			String port = (String)CardInfo.getInnerPortList().get(i);
			String isHere = CardState.isActiveInner(port)?
					ConstOperI.WORK_IN:ConstOperI.WORK_LEAVE;
			String[] s={port,isHere};
			System.err.println("state is :"+s[0]+s[1]);
			list.add(s);			
		}
		return list;
	}
	
}
