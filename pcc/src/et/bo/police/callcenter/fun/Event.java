package et.bo.police.callcenter.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Event {
	private static String COLON=":";
	private static String SEMICOLON=";";
	private static String COMMA=",";
	/*
	 * �������ƣ�keyֵ������PTRING.
	 */
	private String action;
	/*
	 * ����������顣
	 */
	private String[] arg;
	private void praseString(String s){
		//ȥ���ֺ�
		StringTokenizer st=new StringTokenizer(s,Event.SEMICOLON);
		String s0=st.nextToken();
		//����ð�ţ��ҵ�����action
		st =new StringTokenizer(s0,Event.COLON);
		this.action = st.nextToken();
		//��������
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
