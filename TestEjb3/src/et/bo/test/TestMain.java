/**
 * 	@(#)TestMain.java   2006-12-8 ÏÂÎç12:42:36
 *	 ¡£ 
 *	 
 */
package et.bo.test;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import et.bo.interfaces.TestString;
import et.bo.interfaces.TestStringHome;

 /**
 * @author ddddd
 * @version 2006-12-8
 * @see
 */
public class TestMain {

	/**
	 * @param
	 * @version 2006-12-8
	 * @return
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			Properties props=new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.cosnaming.CNCtxFactory");
			props.put(Context.PROVIDER_URL,"iiop://192.168.1.3:8081");
			InitialContext ctx=new InitialContext(props);
			TestStringHome tsh=(TestStringHome)PortableRemoteObject.narrow(ctx.lookup("ejb/TestString"),TestStringHome.class);
			TestString ts=tsh.create();
			System.out.println(ts.printString("sssssssssssssssssssssss"));
			ts.remove();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
