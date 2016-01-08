package et.test.callcenter;

import et.bo.callcenter.message.impl.EventStrValidImpl;

public class T {

	public T() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventStrValidImpl rs = new EventStrValidImpl();
//		boolean b=rs.checkString("[A-Z]{6}\\s*:\\(\\s*.*\\s*,\\)*", "EXRING:0,1000,4000,");
//		boolean b=rs.checkString("[A-Z]{6}\\s*:(\\s*.*\\s,)*", "EXRING:0,1000,4000,");
//		boolean b=rs.checkString("^\\s*[A-Z]{6}\\s*:(\\s*\\w*\\s,)*", "EXRING:0,1000,4000,");
//		boolean b=rs.checkString("^[A-Z]{6} : ", "EXRING: TT,");
//		int i=rs.getRegexCount("0", "EXRING:0,1000,4000,");
//		System.out.println(i);
//		System.out.println(b);
		String ss="EXRING:0,1000,4000;";
//		String[] sArray=ss.split(":",2);
//		for(int j=0;j<sArray.length;j++){
//			System.out.println(sArray[j]);
//		}
		rs.handle(ss);
		System.out.println(rs.getCommand());
		System.out.println(rs.getReceiveStr());
		for(int j=0;j<rs.getArgs().length;j++){
			System.out.println(rs.getArgs()[j]);
		}
	}

}
