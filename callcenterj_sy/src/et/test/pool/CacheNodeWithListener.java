/**
 * 
 */
package et.test.pool;

/**
 * Title:
 * 
 * Description: 测试使用带有事件回调机制的对象缓冲池－－－采用最近、最久未使用策略管理对象
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
	 * 当对象被池所抛弃时候，进行相关处理
	 **************************************************************************/
	public void onAbandon() {
		System.out.println(this + "---onAbandon()");
	}

	/***************************************************************************
	 * 当对象池被清空时候，进行相关处理
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
