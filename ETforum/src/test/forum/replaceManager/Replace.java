package test.forum.replaceManager;

import et.bo.forum.common.io.RW;

public class Replace {
    
    public static void main(String[] args) {
    	String text ="abcdefghijkllmn";
    	
    	String[] tokens = text.split(".bc");
    	for(String token : tokens){
    		System.out.println(token+"  ");
    	}
    	
    	RW rw = new RW();
        rw.write("D://1.txt","22");
    	System.out.println("ab=de".replaceAll("=", "***"));
    	String yy = "jj";
    	String jj = "jj=**";
    	StringBuilder sb = new StringBuilder();
    	sb.append(yy);
    	sb.append("\t");
    	sb.append("=");
    	sb.append("\t");
    	sb.append(" ");
    	sb.append("\r\n");
    	String EL = "=";
    	System.out.println(sb.toString());
    	if(jj.matches(EL)){
    		System.out.println("сп=╨е");
    	}else{
    		System.out.println("нч");
    	}
    	int i = yy.indexOf("=");
    	System.out.println(i);
    	if(i==-1){
    		
    	}else{
    		String s = jj.substring(0, i);
        	System.out.println(s);
    	}
    	
	}
    
    
}
