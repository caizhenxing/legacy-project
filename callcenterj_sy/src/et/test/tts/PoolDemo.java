package et.test.tts;

import org.apache.commons.pool.impl.GenericObjectPool;

public class PoolDemo {

	public static void main(String args[]){

		// create a GenericObjectPool using MyObject factory and default
		// values for the maxActive, maxIdle etc.
		GenericObjectPool pool = new GenericObjectPool(new MyObjectFactory());

		// lets see what the value if for max active
		System.err.println("Max Active: " + pool.getMaxActive());

		try{

			// now lets borrow a couple of values from the pool
			System.err.println("Borrowed: " + pool.borrowObject());
			System.err.println("Borrowed: " + pool.borrowObject());

			// so whats the number of active (borrowed objects) ?
			System.err.println("Active objects: " + pool.getNumActive());

			// return one of the object
			pool.returnObject("0");

			// whats the number of active (borrowed objects) now ?
			System.err.println("Active objects after returning one: " + pool.getNumActive());


		}catch(Exception e){ System.err.println(e); }


	}
}