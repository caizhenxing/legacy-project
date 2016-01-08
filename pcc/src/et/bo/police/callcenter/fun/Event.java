package et.bo.police.callcenter.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Event {
	private static String COLON=":";
	private static String SEMICOLON=";";
	private static String COMMA=",";
	/*
	 * 命令名称，key值，比如PTRING.
	 */
	private String action;
	/*
	 * 命令参数数组。
	 */
	private String[] arg;
	private void praseString(String s){
		//去掉分号
		StringTokenizer st=new StringTokenizer(s,Event.SEMICOLON);
		String s0=st.nextToken();
		//解析冒号，找到命令action
		st =new StringTokenizer(s0,Event.COLON);
		this.action = st.nextToken();
		//解析参数
		String sArg =st.nextToken();
		List l = new ArrayList();
		st =new StringTokenizer(sArg,Event.COMMA);
		while(st.hasMoreTokens()){
			l.add((String)st.nextToken());
		}
		//Object o = (String[])l.toArray();
		arg= new String[l.size()];
		l.toArray(arg);	
		
	}
	public void parse(String s){
	}
	public static void main(String[] a){
		String s="EXRING:0,1000,4000;";
		Event e = new Event();
		e.praseString(s);
		System.out.println(e.action);
		System.out.println(e.arg.length);
		for(int i=0;i<e.arg.length;i++){
			System.out.println(e.arg[i]);
		}
	}
}
