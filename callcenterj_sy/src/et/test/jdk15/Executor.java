/**
 * 沈阳卓越科技有限公司
 * 2008-4-30
 */
package et.test.jdk15;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zhang feng
 * 
 */
public class Executor {
	public static void main(String[] args) {

		// *1

		DoCallStuff call1 = new DoCallStuff(0);

		DoCallStuff call2 = new DoCallStuff(1);

		DoCallStuff call3 = new DoCallStuff(2);

		// *2

		ExecutorService es = Executors.newFixedThreadPool(3);

		// *3

		Future future1 = es.submit(call1);

		Future future2 = es.submit(call2);

		Future future3 = es.submit(call3);

		try {

			// *4

			System.out.println(future1.get());

			// *5

			Thread.sleep(3000);

			System.out.println("Thread 2 terminated? :" + future2.cancel(true));

			// *6

			System.out.println(future3.get());

		} catch (ExecutionException ex) {

			ex.printStackTrace();

		} catch (InterruptedException ex) {

			ex.printStackTrace();

		}

	}

}
