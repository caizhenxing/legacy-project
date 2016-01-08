/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 31, 200911:26:09 AM
 * ������base.zyf.common.code.impl
 * �ļ�����SysCodeServiceImpl.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.common.code.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import base.zyf.common.code.SysCodeService;
import base.zyf.web.condition.ContextInfo;
import base.zyf.web.view.ComboSupportList;

/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class SysCodeServiceImpl implements SysCodeService {

	private static Log logger = LogFactory.getLog(ContextInfo.class);
	
	private HibernateTemplate hibernateTemplate;
	/* (non-Javadoc)
	 * @see base.zyf.common.code.SysCodeService#getlist(java.lang.String)
	 */
	public ComboSupportList getlist(String code) {
		String[] s = code.split("-");
		ComboSupportList csl = new ComboSupportList(s[1],s[2]);
		try {
			Class cl = Class.forName(s[0]);// ClassLoader.getSystemClassLoader().loadClass(s[0]);
			List l = hibernateTemplate.findByExample(cl.newInstance());
			csl.addAll(l);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("��·����"+s[0]+"�޷��õ�");
			return null;
		} catch (DataAccessException e) {
			logger.error(e);
			logger.error("��·����"+s[0]+"�޷�����ʵ��");
			return null;
		} catch (InstantiationException e) {
			logger.error(e);
			logger.error("��·����"+s[0]+"�޷�����ʵ��");
			return null;
		} catch (IllegalAccessException e) {
			logger.error(e);
			logger.error("��·����"+s[0]+"�޷�����ʵ��");
			return null;
		}
		
		return csl;
		
	}
	/**
	 * @return the hibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	/**
	 * @param hibernateTemplate the hibernateTemplate to set
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
