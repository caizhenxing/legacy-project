package et.test.tts;

import org.apache.commons.pool.PoolableObjectFactory;

public class MyObjectFactory implements PoolableObjectFactory{

	private static int counter;

	// returns a new string
	public Object makeObject() { return String.valueOf(counter++); }

	public void destroyObject(Object obj) { }
	public boolean validateObject(Object obj) { return true; }
	public void activateObject(Object obj) { }
	public void passivateObject(Object obj) { }

}