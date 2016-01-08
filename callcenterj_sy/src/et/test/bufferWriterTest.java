package et.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class bufferWriterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
			int str=buf.read();
			System.out.println("str="+str);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
