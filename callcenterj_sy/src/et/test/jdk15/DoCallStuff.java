/**
 * 沈阳卓越科技有限公司
 * 2008-4-30
 */
package et.test.jdk15;

import java.util.concurrent.Callable;

/**
 * @author zhang feng
 * 
 */
public class DoCallStuff implements Callable {

	private int aInt;

	public DoCallStuff(int aInt) {

		this.aInt = aInt;

	}

	public String call() throws Exception { // *2

		boolean resultOk = false;

		if (aInt == 0) {

			resultOk = true;

		} else if (aInt == 1) {

			while (true) { // infinite loop

				System.out.println("looping....");

				Thread.sleep(3000);

			}

		} else {

			throw new Exception("Callable terminated with Exception!"); // *3

		}

		if (resultOk) {

			return "Task done.";

		} else {

			return "Task failed";

		}

	}

}
