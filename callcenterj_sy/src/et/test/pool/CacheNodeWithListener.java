/**
 * 
 */
package et.test.pool;

/**
 * Title:
 * 
 * Description: ����ʹ�ô����¼��ص����ƵĶ��󻺳�أ�����������������δʹ�ò��Թ������
 * 
 * Copyright: Copyright (c) 2006
 * 
 * @see org.apache.tomcat.util.collections.LRUCache
 * @author wdz123@hotmail.com
 * @version 1.0
 */
public class CacheNodeWithListener implements Abandon {
	int id;

	public CacheNodeWithListener() {
	}

	public CacheNodeWithListener(int i) {
		id = i;
	}

	/***************************************************************************
	 * �����󱻳�������ʱ�򣬽�����ش���
	 **************************************************************************/
	public void onAbandon() {
		System.out.println(this + "---onAbandon()");
	}

	/***************************************************************************
	 * ������ر����ʱ�򣬽�����ش���
	 **************************************************************************/
	public void poolClear() {
		System.out.println(this + "---poolClear()");
	}

	public String toString() {
		return "id=" + id;
	}

	static public void main(String[] s) {
		LRUCacheWithListener pool = new LRUCacheWithListener(3);
		int i;
		for (i = 1; i <= 5; i++) {
			pool.put("obj" + i, new CacheNodeWithListener(i));
		}
		pool.clear();
	}
}
