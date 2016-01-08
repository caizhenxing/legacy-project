package jp.go.jsps.kaken.util;

import java.util.Vector;
import java.util.Enumeration;

/**
 * CSV�`����1�s���̃f�[�^���e��ێ�����N���X�B
 * CSV�`���ւ̏����o���A�����o���̍ۂ̃G���N�H�[�g�̎w��A
 * ���ڂ̒ǉ��Ȃǂ��\�ł���B
 *
 * @version 1.0.1 (1999.4.13)
 * @author TAMURA Kent <kent@muraoka.info.waseda.ac.jp>
 * @author ANDOH Tomoharu <tomoharu@wakhok.ac.jp>
 */

public class CSVLine {
	public static final String copyright =
			"Copyright 1997 TAMURA Kent" + "\n" +
			"Copyright 1999 ANDOH Tomoharu";
	private Vector items;

	/**
	 * ���CSVLine �̃C���X�^���X���쐬����B
	 */
	public CSVLine() {
		items = new Vector();
	}

	/**
	 * �����Ŏw�肳�ꂽ��������A�����ɒǉ�����B
	 *
	 * @param item �ǉ����镶����
	 */
	public void addItem(String item) {
		addItem(item, false);
	}

	/**
	 * �����Ŏw�肳�ꂽ��������A�����ɒǉ�����B
	 * CSV�`���̃f�[�^�Ƃ��ďo�͂����Ƃ��A���̍��ڂ������I��
	 * �G���N�H�[�g���邩�ǂ����������ɂ���Ďw�肷��B
	 *
	 * @param item �ǉ����镶����
	 * @param enquote true���ƁA�����I�ɃG���N�H�[�g�����B
	 */
	public void addItem(String item, boolean enquote) {
		items.addElement(new Element(item, enquote));
	}

	/**
	 * �����Ŏw�肳�ꂽCSVTokenizer�Ɋ܂܂�邷�ׂĂ̍��ڂ��A
	 * �����ɒǉ�����B
	 *
	 * @param ct CSVTokenizer�̃C���X�^���X�B�����Ɋ܂܂�Ă��鍀��
	 * �́A�����ɒǉ������B
	 * @see jp.ac.wakhok.tomoharu.csv.CSVTokenizer
	 */
	public void addItem(CSVTokenizer ct) {
		while (ct.hasMoreTokens()) {
			String item = ct.nextToken();
			items.addElement(new Element(item));
		}
	}

	/**
	 * 1�s��CSV�`���̃f�[�^��Ԃ��B
	 *
	 * @return �P�s��CSV�`���̃f�[�^
	 */
	public String getLine() {
		StringBuffer list = new StringBuffer();
		for (int n = 0; n < items.size(); n ++) {
			Element element = (Element)items.elementAt(n);
			String item = element.getItem();
			list.append(item);
			if (items.size() - 1 != n) {
				list.append(',');
			}
		}
		return new String(list);
	}

	/**
	 * 1�s�̍��ڐ���Ԃ��B
	 *
	 * @return CSVLine�Ɋ܂�ł��鍀�ڂ̐�
	 */
	public int size() {
		return items.size();
	}

	/**
	 * n�Ԗڂ̍��ڂ� String �ŕԂ��B
	 *
	 * @param n ���ڂ̔ԍ� [0 �` size()-1]
	 * @return n�Ԗڂ̕�����B�G���N�H�[�g�͂��Ȃ��B
	 */
	public String getItem(int n) {
		Element element = (Element)items.elementAt(n);
		return element.getRawItem();
    }

	/**
	 * n�Ԗڂ̍��ڂ��폜����B
	 *
	 * @param n ���ڂ̔ԍ� [0 �` size()-1]
	 */
	public void removeItem(int n) {
		items.removeElementAt(n);
	}

	/**
	 * CSVLine�̍��ڂ̃��X�g��Ԃ��B
	 *
	 * @return ����CSVLine�Ɋ܂܂�Ă��镶����̃��X�g
	 * @see java.util.Enumeration
	 */
	public Enumeration elements() {
		return new CSVLineEnumerator(items);
	}

	/**
	 * CSVLine�Ɋ܂܂�邻�ꂼ��̍��ڂ�ێ�����C���i�[�N���X�B
	 */
	class Element {
		private String item;
		private boolean enquote;

		Element(String item) {
			this(item, false);
		}

		Element(String item, boolean enquote) {
			this.item = item;
			this.enquote = enquote;
		}

		/**
		 * �w�肳��Ă���΁A�G���N�H�[�g���s���B
		 */
		public String getItem() {
			return enquote(item, enquote);
		}

		/**
		 * �G���N�H�[�g�͈�؂��Ȃ��B
		 */
		public String getRawItem() {
			return item;
		}
	}

	/**
	 * elements()���\�b�h�ŕԂ���� Enumeration�N���X
	 */
	class CSVLineEnumerator implements Enumeration {
		private Vector items;
		private int n;

		CSVLineEnumerator(Vector items) {
			this.items = items;
			n = 0;
		}

		public Object nextElement() {
			n ++;
			Element element = (Element)items.elementAt(n-1);
			return element.getRawItem();
		}

		public boolean hasMoreElements() {
			return n < items.size();
		}
	}

	/**
	 * �����̕����� item �� CSV �ŏo�͂ł���悤�ɉ��H�����������
	 * �Ԃ��B<br>
	 * item �� " �� , ���܂�ł���Ƃ��ɂ� item �S�̂� " �ň͂�
	 * �i�G���N�H�[�g���j�A" �� "" �ɒu��������B�܂�" �� , �̂ǂ�
	 * ����܂�ł��Ȃ��Ƃ��́Aitem �����̂܂ܕԂ��B<br>
	 *
	 * @param item ����������������
	 * @return item ����������������
	 */
    public static String enquote(String item) {
		return enquote(item, false);
	}

	/**
	 * �����̕����� item �� CSV �ŏo�͂ł���悤�ɉ��H�����������
	 * �Ԃ��B<br>
	 * enquote �� true �̂Ƃ��́A�����I�ɃG���N�H�[�g����B�܂�A
	 * item �� " �ň͂����������Ԃ��B<br>
	 * false �̂Ƃ��́A�G���N�H�[�g���邩�ǂ����́Aitem �ɂ��B
	 * item �� " �� , ���܂�ł���Ƃ��ɂ� item �S�̂� " �ň͂�
	 * �i�G���N�H�[�g���j�A" �� "" �ɒu��������B�܂�" �� , �̂ǂ�
	 * ����܂�ł��Ȃ��Ƃ��́Aitem �����̂܂ܕԂ��B<br>
	 * item ����A�܂蒷�����[���̕����񂾂����ꍇ�A����������
	 * ��̕���������̂܂ܕԂ��B
	 *
	 * @param item ����������������
	 * @param enquote true�Ȃ狭���I�ɃG���N�H�[�g����
	 * @return item ����������������
	 */
	public static String enquote(String item, boolean enquote) {
		if (item.length() == 0) {
			return item;
		}
		
		//�u�h�v�Ɓu�g�v�́u"�v�ɕϊ�����B
		// word��excel�Łu�h�v�ɑ΂���G�X�P�[�v�������قȂ邽�߁B
		item = item.replace('�h','"');
		item = item.replace('�g','"');

		//2005.08.10 iso \n�������ɒǉ�
		if (item.indexOf('"') < 0 && item.indexOf(',') < 0 && item.indexOf('\n') < 0 && enquote == false) {
			return item;
        }

		// StringBuffer�̃T�C�Y�́A�ł��ُ�ȏꍇ��z�肵���B
		// ������ """"" ���G���N�H�[�g���ďo�͂���悤�ȂƂ��̂��ƁB

		StringBuffer sb = new StringBuffer(item.length() * 2 + 2);
		sb.append('"');
		for (int ind = 0; ind < item.length(); ind ++) {
			char ch = item.charAt(ind);
			if ('"' == ch) {
				sb.append("\"\"");
			} else {
				sb.append(ch);
			}
		}
		sb.append('"');

		return new String(sb);
	}
}
