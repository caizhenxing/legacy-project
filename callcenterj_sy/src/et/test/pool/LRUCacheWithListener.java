/**
 * 
 */
package et.test.pool;

/**
 * <p>
 * Title: ���󻺳�أ�����������������δʹ�ò��Թ������,ͬʱ�����¼���������
 * </p>
 * <p>
 * ����ԭ��
 * <LI>���ü��Ͽ��(java.connection��)��ʵ��������δʹ�ö����</li>
 * <LI>���ȹ������ء����óصĴ�С</li>
 * <li>���ö��󵽳��У�����ʱ�򣬳ص�ָ��ָ��ö����Ա����ö��������̱�ʹ�ù�</li>
 * <li>�����µĶ�����뵽����ʱ��������Ѿ������Ǿ�ɾ�����û�б�ʹ�õĶ���Ȼ��������</li>
 * <li>�ӳ��ж�ȡ����ʱ�����������ӳ��л�ö���Ȼ��ѳص�ָ��ָ���ȡ���Ķ����Ա����ö��������̱�ʹ�ù�</li>
 * <li>�������ж������ʱ�򣨵���������������ᴥ������¼�
 * <li>���ر����ʱ�򣬻��������¼�
 * </p>
 * <p>
 * ����˵�� �����ο���org.apache.tomcat.util.collections.LRUCache��ʵ��ϸ�ڡ�
 * ��Ȼԭ�������Hashtable���洢�صĶ����б������������Ĵ洢��ʽ������HashMap���洢
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @see org.apache.tomcat.util.collections.LRUCache
 *      <li> �ļ�λ��jakarta-tomcat-5.5.6\jakarta-tomcat-connectors\\util
 * @author wdz123@hotmail.com
 * @version 1.0
 */
import java.util.HashMap;
import java.util.Iterator;

public class LRUCacheWithListener {
	/**
	 * �ض���İ����࣬����������ش���
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
	 * ����ش�С
	 */
	private int cacheSize;

	/***************************************************************************
	 * �����б���Ȼ���Բ��÷��ͱ�̣�������ʵ���Զ�װ�䡢����(boxing/unboxing)
	 **************************************************************************/
	private HashMap nodes;

	/***************************************************************************
	 * ����ص�ǰ������
	 **************************************************************************/
	private int currentSize;

	/***************************************************************************
	 * ��һ���ڵ�
	 **************************************************************************/
	private CacheNode first;

	/***************************************************************************
	 * ���һ���ڵ�
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
	 * ��ȡһ������
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
	 * ��ָ�������ƶ��������ͷ��
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
	 * ɾ������ָ������
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
	 * ����һ�����󵽳���
	 */
	public synchronized void put(Object key, Abandon value) {
		CacheNode node = (CacheNode) nodes.get(key);
		if (node == null) {
			if (currentSize >= cacheSize) {// ������ɾ�����û��ʹ�õĶ���
				if (last != null) {
					nodes.remove(last.key);
				}
				removeLast();
			} else {// ��û������ֱ�ӰѶ���������
				currentSize++;
			}
			node = getANewCacheNode();
		}
		node.value = value;
		node.key = key;
		// �ѷ���ص���������ƶ��������ͷ������ʾ�����̱�ʹ�ù�
		moveToHead(node);
		nodes.put(key, node);
	}

	/***************************************************************************
	 * ��ճ��ж���
	 **************************************************************************/
	public synchronized void clear() {
		if (first != null) {
			Iterator i = nodes.values().iterator();
			// �����¼����ó��Ѿ������
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
	 * ���һ���µİ�������
	 **************************************************************************/
	private CacheNode getANewCacheNode() {
		CacheNode node = new CacheNode();
		return node;
	}

	/***************************************************************************
	 * ɾ���������û��ʹ�õĶ���
	 **************************************************************************/
	private void removeLast() {
		if (last != null) {
			// ����ӳ��б������������¼�
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
