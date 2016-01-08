package et.bo.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * XDoclet-based session bean.  The class must be declared
 * public according to the EJB specification.
 *
 * To generate the EJB related files to this EJB:
 *		- Add Standard EJB module to XDoclet project properties
 *		- Customize XDoclet configuration for your appserver
 *		- Run XDoclet
 *
 * Below are the xdoclet-related tags needed for this EJB.
 * 
 * @ejb.bean name="Testa"
 *           display-name="Name for Testa"
 *           description="Description for Testa"
 *           jndi-name="ejb/Testa"
 *           type="Stateless"
 *           view-type="remote"
 *           
 */
public class Testa implements SessionBean {

	/** The session context */
	private SessionContext context;

	public Testa() {
		// TODO Auto-generated constructor stub
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Set the associated session context. The container calls this method 
	 * after the instance creation.
	 * 
	 * The enterprise bean instance should store the reference to the context 
	 * object in an instance variable.
	 * 
	 * This method is called with no transaction context. 
	 * 
	 * @throws EJBException Thrown if method fails due to system-level error.
	 */
	public void setSessionContext(SessionContext newContext)
		throws EJBException {
		context = newContext;
	}

	/**
	 * An example business method
	 *
	 * @ejb.interface-method view-type = "remote"
	 * 
	 * @throws EJBException Thrown if method fails due to system-level error.
	 */
	public void replaceWithRealBusinessMethod() throws EJBException {
		// rename and start putting your business logic here
	}

}
