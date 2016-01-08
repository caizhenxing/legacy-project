package et.bo.cc.test;

import java.io.IOException;

public class Test {

	/**
	 * @param
	 * @version 2006-10-31
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long i=100000;
		while(i--!=0)
		{
			if(i%10000==0)
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
