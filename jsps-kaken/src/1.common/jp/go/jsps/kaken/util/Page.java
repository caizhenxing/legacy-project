/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ページ情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: Page.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class Page implements Serializable {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -4173912581720244628L;
	
	/** 空のページを示すオブジェクト */
	public static final Page EMPTY_PAGE =
		new Page(Collections.EMPTY_LIST, 0, 0, 0);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 取得データリスト */
	private List list;

	/** 取得データ開始位置番号*/
	int start;

	/** ページあたりの表示件数 */
	int pageSize;

	/** 対象データ総件数*/
	int totalSize;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 * @param list		取得データリスト
	 * @param start		取得データ開始位置番号
	 * @param pageSize	ページあたりの表示件数
	 * @param totalSize	対象データ総件数
	 */
	public Page(List list, int start, int pageSize, int totalSize) {
		super();
		this.list = new ArrayList(list);
		this.start = start;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * 次ページが存在するかどうかをチェックする。
	 * @return	存在する場合 true 以外 false
	 */
	public boolean hasNextPage() {
		return (start + list.size()) < totalSize;
	}

	/**
	 * 前ページが存在するかどうかをチェックする。
	 * @return	存在する場合 true 以外 false
	 */
	public boolean hasPreviousPage() {
		return start > 0;
	}

	/**
	 * 次ページの開始位置番号を取得する。
	 * @return	開始位置番号
	 */
	public int getStartOfNextPage() {
		return start + list.size();
	}

	/**
	 * 前ページの開始位置番号を取得する。
	 * @return
	 */
	public int getStartOfPreviousPage() {
		if (hasNextPage()) {
			return Math.max(start - list.size(), 0);
		}
		return start - pageSize;
	}

	/**
	 * 今回取得したデータ件数を取得する。
	 * @return
	 */
	public int getSize() {
		return list.size();
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer results = new StringBuffer();
		results.append("\tstart='" + start + "'\n");
		results.append("\ttotalSize='" + totalSize + "'\n");
		results.append("\tpageSize='" + pageSize + "'\n");
		results.append("\ttotalPages='" + getTotalPages() + "'\n");
		results.append("\tcurrentPages='" + getCurrentPages() + "'\n");
		return results.toString();
	}

	/**
	 * 処理結果リストを取得する。
	 * @return	検索結果を含むリストオブジェクト。
	 */
	public List getList() {
		return list;
	}

	/**
	 * 処理結果リストを設定する。
	 * @param	検索結果を含むリストオブジェクト。
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * 総ページ数を取得する。
	 * @return	検索結果を含むリストオブジェクト。
	 */
	public int getTotalPages() {
		//ページサイズが0のときはすべてのデータを1ページで表示する。
		if (pageSize == 0)
			return 1;
		return (int) Math.ceil((double) totalSize / pageSize);
	}

	/**
	 * 現在のページ数を取得する。
	 * @return	表示中のページ
	 */
	public Integer getCurrentPages() {
		//ページサイズが0のときはすべてのデータを1ページで表示する。
		if (pageSize == 0) {
			return new Integer(1);
		} else {
			return new Integer((start / pageSize) + 1);
		}
	}

	/**
	 * ページ遷移のインデックスを取得する。
	 * @return	取得開始位置番号を保持するリスト
	 */
	public List getPageIndexs() {
		int totalPages = getTotalPages();
		ArrayList index = new ArrayList(totalPages);
		//ページがある間スタートポジションをセットしていく。
		for (int i = 0; i < totalPages; i++) {
			index.add(Integer.toString((i * pageSize)));
		}
		return index;
	}
	
	/**
	 * 総件数を取得する。
	 * @return	検索条件に合う総データ件数。
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * ページあたりの表示件数を取得する。
	 * @return	検索条件に合う総データ件数。
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * ページの開始位置番号を取得する。
	 * @return
	 */
	public int getStart() {
		return start;
	}

}
