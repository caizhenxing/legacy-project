/**
 * 
 */
package et.test.tts;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * @author Administrator
 * 
 */
public final class ObjectPoolManager {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ObjectPoolManager.class);

	/**
	 * Holds instances of ObjectPool, the main key is the type of objects in
	 * this pool.
	 */
	private static HashMap objectPools = new HashMap();

	/**
	 * This class is not instanceable.
	 */
	private ObjectPoolManager() {
	}

	/**
	 * Get a ObjectPool instance from the objectPools, if not found, then
	 * lazilly create a new one.
	 * 
	 * @param clazz
	 *            the object type
	 * @return the ObjectPool instance
	 */
	private static synchronized ObjectPool getPool(Class clazz) {
		ObjectPool pool = (ObjectPool) objectPools.get(clazz);

		if (pool == null) {
			pool = new ObjectPool(clazz);
			objectPools.put(clazz, pool);
		}

		return pool;
	}

	/**
	 * Get an instance of the given object in the corresponding object pool.
	 * 
	 * @param clazz
	 *            the object type
	 * @return the instance of the object type
	 */
	public static synchronized Object getInstance(Class clazz) {

		return getPool(clazz).getInstance();
	}

	/**
	 * Free the instance to the corresponding object pool.
	 * 
	 * @param obj
	 *            the object to be freeed
	 */
	public static synchronized void freeInstance(Object obj) {
		logger.debug("Free object to pool" + obj);

		getPool(obj.getClass()).freeInstance(obj);
	}

}
