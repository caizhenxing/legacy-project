package ocelot.framework.base.dao;

import java.io.File;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class DAOUtil {

	public static void createDBByMap(String classpath) {
		Configuration config = null;
		Transaction tx = null;
		Session session;
		String path=DAOUtil.class.getResource(classpath).getPath();
		
		try {
			config = new Configuration().configure(new File(path));
			SchemaExport schemaExport = new SchemaExport(config);
			schemaExport.create(true, true);
			SessionFactory sessionFactory = config.buildSessionFactory();
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
		} finally {
		}
	}
	public static void main(String[] arg0)
	{
		DAOUtil.createDBByMap("/ocelot/config/hibernate/hibernate.cfg.xml");
	}
}
