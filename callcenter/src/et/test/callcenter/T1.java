package et.test.callcenter;

import java.text.MessageFormat;
import java.util.Date;

public class T1 {

	public T1() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String message =
			"Once upon a time ({1,date}, around about {1,time,short}), there " +
			"was a humble developer named Geppetto who slaved for " +
			"{0,number,integer} days with {2,number,percent} complete user " +
			"requirements. ";
			Object[ ] variables =
			new Object[ ] { new Integer(4), new Date(), new Double(0.21) };
			String output = MessageFormat.format( message, variables );
			System.out.println(output); 
	}

}
