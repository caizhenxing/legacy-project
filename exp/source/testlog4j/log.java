/*
 * �������� 2005-1-10
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package testlog4j;

import org.apache.log4j.*;
import org.apache.log4j.xml.*;

/**
 * @author ��һ��
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class log {

	static Logger l = Logger.getLogger(log.class);
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("e:\\test.properties");
		//DOMConfigurator.configure("e:\\log4j.xml");
		//l.setLevel((Level)Level.ERROR);
		l.debug("esdfd");
		l.warn("fdsafds");
		l.info("fefsdfds");
		l.error("fsdfdsf");
	}
}
