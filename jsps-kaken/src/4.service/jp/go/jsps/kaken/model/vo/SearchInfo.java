/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/08
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
 package jp.go.jsps.kaken.model.vo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 検索するための情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: SearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:38 $"
 */
public class SearchInfo extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	//static final long serialVersionUID = 2911234343007665744L;

	/** ログ */
	protected static Log log = LogFactory.getLog(SearchInfo.class);
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** 1 ページあたりの表示件数(すべてのデータを１ページに表示する場合は0) */
	private int pageSize = 0;

	/** 取得開始行番号(1行目から取得する場合は0) */
	private int startPosition = 0;

	/** データ件数MAX値（MAX値を設けない場合は0） */
	private int maxSize = 0;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public SearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/**
	 * 取得開始行番号を取得する。
	 * @return	取得開始行番号
	 */
	public int getStartPosition() {
		//開始位置が負の場合は0にする。
		if (startPosition < 0) {
			return 0;
		}
		return startPosition;
	}
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param i
	 */
	public void setPageSize(int i) {
		pageSize = i;
	}

	/**
	 * @param i
	 */
	public void setStartPosition(int i) {
		startPosition = i;
	}
	/**
	 * @return
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param i
	 */
	public void setMaxSize(int i) {
		maxSize = i;
	}

}
