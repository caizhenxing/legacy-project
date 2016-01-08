package et.test.callcenter.server;

import et.bo.callcenter.ConstantsI;

public class Test001 {
	private static String[] sBlank;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test001 t001 =new Test001();
		t001.initBlank();
		for(int i=0;i<Test001.sBlank.length;i++){
			System.out.println(i+sBlank[i]);
		}
	}
	private void initBlank(){		
		sBlank = new String[ConstantsI.PHONE_NUM_FORMAT_LEN+1];
		sBlank[0] ="";
		for(int i=1;i<=ConstantsI.PHONE_NUM_FORMAT_LEN;i++){
			StringBuffer sb=new StringBuffer();
			for(int j=0;j<i;j++){
				sb.append(ConstantsI.DELIM1);
			}
			sBlank[i] = sb.toString();
		}
	}

}
