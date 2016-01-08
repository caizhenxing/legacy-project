/*
 * �������� 2004-10-20
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
import java.util.*;
/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class SimpleCache {
	private final Map cache = new HashMap();

	  public Object load(String objectName) { 
		// load the object somehow
	  }

	  public void clearCache() { 
		synchronized (cache) { 
		  cache.clear();
		}
	  }

	  public Object getObject(String objectName) {
		Object o =new Object();
		synchronized (cache) { 
		   o = cache.get(objectName);
		  if (o == null) {
			o = load(objectName);
			cache.put(objectName, o);
		  }
		}

		return o;
	  }

}
