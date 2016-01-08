/**
 * 
 */
package et.test.pool;

/**
 * <p>
 * Title: 对象缓冲池－－－采用最近、最久未使用策略管理对象,同时带有事件监听功能
 * </p>
 * <p>
 * 工作原理
 * <LI>采用集合框架(java.connection包)来实现最近最久未使用对象池</li>
 * <LI>首先构造对象池、设置池的大小</li>
 * <li>放置对象到池中，保存时候，池的指针指向该对象，以表明该对象最近最短被使用过</li>
 * <li>当把新的对象放入到池中时候，如果池已经满，那就删除最久没有被使用的对象，然后放入对象</li>
 * <li>从池中读取对象时，根据条件从池中获得对象；然后把池的指针指向该取出的对象，以表明该对象最近最短被使用过</li>
 * <li>当池中有对象被清除时候（当成垃圾清除），会触发相关事件
 * <li>当池被清空时候，会出发相关事件
 * </p>
 * <p>
 * 其他说明 这个类参考了org.apache.tomcat.util.collections.LRUCache的实现细节。
 * 当然原代码采用Hashtable来存储池的对象列表，这里采用另外的存储方式－－－HashMap来存储
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @see org.apache.tomcat.util.collections.LRUCache
 *      <li> 文件位置jakarta-tomcat-5.5.6\jakarta-tomcat-connectors\\util
 * @author wdz123@hotmail.com
 * @version 1.0
 */
import java.util.HashMap;
import java.util.Iterator;

public class LRUCacheWithListener {
	/**
	 * 池对象的包裹类，这样便于相关处理
	 */
	class CacheNode {
		CacheNode prev;

		CacheNode next;

		Abandon value;

		Object key;

		public CacheNode() {
		}
	}

	/**
	 * 对象池大小
	 */
	private int cacheSize;

	/***************************************************************************
	 * 对象列表、当然可以采用泛型编程，这样就实现自动装箱、解箱(boxing/unboxing)
	 **************************************************************************/
	private HashMap nodes;

	/***************************************************************************
	 * 对象池当前对象数
	 **************************************************************************/
	private int currentSize;

	/***************************************************************************
	 * 第一个节点
	 **************************************************************************/
	private CacheNode first;

	/***************************************************************************
	 * 最后一个节点
	 **************************************************************************/
	private CacheNode last;

	private static int DEFAULT_SIZE = 10;

	public LRUCacheWithListener() {
		this(DEFAULT_SIZE);
	}

	public LRUCacheWithListener(int poolSize) {
		cacheSize = poolSize;
		currentSize = 0;
		first = null; //
		last = null; //
		nodes = new HashMap(poolSize);
	}

	/***************************************************************************
	 * 读取一个对象
	 **************************************************************************/
	public synchronized Object get(Object key) {
		CacheNode node = (CacheNode) nodes.get(key);
		if (node != null) {
			moveToHead(node);
			return node.value;
		} else {
			return null;
		}
	}

	/**
	 * 把指定对象移动到链表的头部
	 */
	private void moveToHead(CacheNode node) {
		if (node == first) {
			return;
		}
		if (node.prev != null) {
			node.prev.next = node.next;
		}
		if (node.next != null) {
			node.next.prev = node.prev;
		}
		if (last == node) {
			last = node.prev;
		}
		if (first != null) {
			node.next = first;
			first.prev = node;
		}
		first = node;
		node.prev = null;
		if (last == null) {
			last = first;
		}
	}

	/***************************************************************************
	 * 删除池中指定对象
	 **************************************************************************/
	public synchronized Object remove(Object key) {
		CacheNode node = (CacheNode) nodes.get(key);
		if (node != null) {
			if (node.prev != null) {
				node.prev.next = node.next;
			}
			if (node.next != null) {
				node.next.prev = node.prev;
			}
			if (last == node) {
				last = node.prev;
			}
			if (first == node) {
				first = node.next;
			}
		}
		return node;
	}

	/***************************************************************************
	 * 放置一个对象到池中
	 */
	public synchronized void put(Object key, Abandon value) {
		CacheNode node = (CacheNode) nodes.get(key);
		if (node == null) {
			if (currentSize >= cacheSize) {// 池满，删除最久没有使用的对象
				if (last != null) {
					nodes.remove(last.key);
				}
				removeLast();
			} else {// 池没有满，直接把对象放入池中
				currentSize++;
			}
			node = getANewCacheNode();
		}
		node.value = value;
		node.key = key;
		// 把放入池的这个对象移动到链表的头部，表示最近最短被使用过
		moveToHead(node);
		nodes.put(key, node);
	}

	/***************************************************************************
	 * 清空池中对象
	 **************************************************************************/
	public synchronized void clear() {
		if (first != null) {
			Iterator i = nodes.values().iterator();
			// 触发事件，该池已经被清空
			CacheNode n;
			while (i.hasNext()) {
				n = (CacheNode) (i.next());
				n.value.poolClear();
			}
		}
		first = null;
		last = null;
	}

	/***************************************************************************
	 * 获得一个新的包裹对象
	 **************************************************************************/
	private CacheNode getANewCacheNode() {
		CacheNode node = new CacheNode();
		return node;
	}

	/***************************************************************************
	 * 删除池中最久没有使用的对象
	 **************************************************************************/
	private void removeLast() {
		if (last != null) {
			// 对象从池中被抛弃，触发事件
			last.value.onAbandon();
			if (last.prev != null) {
				last.prev.next = null;
			} else {
				first = null;
			}
			last = last.prev;
		}
	}
}
