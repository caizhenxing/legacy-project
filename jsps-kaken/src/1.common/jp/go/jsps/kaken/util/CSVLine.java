package jp.go.jsps.kaken.util;

import java.util.Vector;
import java.util.Enumeration;

/**
 * CSV形式の1行分のデータ内容を保持するクラス。
 * CSV形式への書き出し、書き出しの際のエンクォートの指定、
 * 項目の追加などが可能である。
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
	 * 空のCSVLine のインスタンスを作成する。
	 */
	public CSVLine() {
		items = new Vector();
	}

	/**
	 * 引数で指定された文字列を、末尾に追加する。
	 *
	 * @param item 追加する文字列
	 */
	public void addItem(String item) {
		addItem(item, false);
	}

	/**
	 * 引数で指定された文字列を、末尾に追加する。
	 * CSV形式のデータとして出力されるとき、その項目を強制的に
	 * エンクォートするかどうかを引数によって指定する。
	 *
	 * @param item 追加する文字列
	 * @param enquote trueだと、強制的にエンクォートされる。
	 */
	public void addItem(String item, boolean enquote) {
		items.addElement(new Element(item, enquote));
	}

	/**
	 * 引数で指定されたCSVTokenizerに含まれるすべての項目を、
	 * 末尾に追加する。
	 *
	 * @param ct CSVTokenizerのインスタンス。ここに含まれている項目
	 * は、末尾に追加される。
	 * @see jp.ac.wakhok.tomoharu.csv.CSVTokenizer
	 */
	public void addItem(CSVTokenizer ct) {
		while (ct.hasMoreTokens()) {
			String item = ct.nextToken();
			items.addElement(new Element(item));
		}
	}

	/**
	 * 1行のCSV形式のデータを返す。
	 *
	 * @return １行のCSV形式のデータ
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
	 * 1行の項目数を返す。
	 *
	 * @return CSVLineに含んでいる項目の数
	 */
	public int size() {
		return items.size();
	}

	/**
	 * n番目の項目を String で返す。
	 *
	 * @param n 項目の番号 [0 ～ size()-1]
	 * @return n番目の文字列。エンクォートはしない。
	 */
	public String getItem(int n) {
		Element element = (Element)items.elementAt(n);
		return element.getRawItem();
    }

	/**
	 * n番目の項目を削除する。
	 *
	 * @param n 項目の番号 [0 ～ size()-1]
	 */
	public void removeItem(int n) {
		items.removeElementAt(n);
	}

	/**
	 * CSVLineの項目のリストを返す。
	 *
	 * @return このCSVLineに含まれている文字列のリスト
	 * @see java.util.Enumeration
	 */
	public Enumeration elements() {
		return new CSVLineEnumerator(items);
	}

	/**
	 * CSVLineに含まれるそれぞれの項目を保持するインナークラス。
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
		 * 指定されていれば、エンクォートを行う。
		 */
		public String getItem() {
			return enquote(item, enquote);
		}

		/**
		 * エンクォートは一切しない。
		 */
		public String getRawItem() {
			return item;
		}
	}

	/**
	 * elements()メソッドで返される Enumerationクラス
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
	 * 引数の文字列 item を CSV で出力できるように加工した文字列を
	 * 返す。<br>
	 * item が " か , を含んでいるときには item 全体を " で囲み
	 * （エンクォートし）、" を "" に置き換える。また" と , のどち
	 * らも含んでいないときは、item をそのまま返す。<br>
	 *
	 * @param item 処理したい文字列
	 * @return item を処理した文字列
	 */
    public static String enquote(String item) {
		return enquote(item, false);
	}

	/**
	 * 引数の文字列 item を CSV で出力できるように加工した文字列を
	 * 返す。<br>
	 * enquote が true のときは、強制的にエンクォートする。つまり、
	 * item を " で囲った文字列を返す。<br>
	 * false のときは、エンクォートするかどうかは、item による。
	 * item が " か , を含んでいるときには item 全体を " で囲み
	 * （エンクォートし）、" を "" に置き換える。また" と , のどち
	 * らも含んでいないときは、item をそのまま返す。<br>
	 * item が空、つまり長さがゼロの文字列だった場合、何もせずに
	 * 空の文字列をそのまま返す。
	 *
	 * @param item 処理したい文字列
	 * @param enquote trueなら強制的にエンクォートする
	 * @return item を処理した文字列
	 */
	public static String enquote(String item, boolean enquote) {
		if (item.length() == 0) {
			return item;
		}
		
		//「”」と「“」は「"」に変換する。
		// wordとexcelで「”」に対するエスケープ処理が異なるため。
		item = item.replace('”','"');
		item = item.replace('“','"');

		//2005.08.10 iso \nを条件に追加
		if (item.indexOf('"') < 0 && item.indexOf(',') < 0 && item.indexOf('\n') < 0 && enquote == false) {
			return item;
        }

		// StringBufferのサイズは、最も異常な場合を想定した。
		// 文字列 """"" をエンクォートして出力するようなときのこと。

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
