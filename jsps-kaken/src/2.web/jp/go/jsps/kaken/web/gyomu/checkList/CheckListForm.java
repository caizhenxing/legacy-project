/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : CheckListForm.java
 *    Description : チェックリスト用フォーム。
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/03/31    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import jp.go.jsps.kaken.web.struts.BaseForm;


/**
 * チェックリスト用フォーム
 * 
 * @author masuo_t
 */
public class CheckListForm extends BaseForm {
	
	/** 所属ID */
	private String jigyoId;

	/** チェックリスト版 */
	private String edition;

	/** 学振有効期限 */
	private boolean period;

	/** 所属コード */
	private String shozokuCd;
	
	/** 回数 */
	private String kaisu;

// 20050719
    /** 状況ID */
	private String jokyoId;
// Horikoshi

/************************************************************************************/

	/**
     * 所属IDを取得
	 * @return 所属ID
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
     * 所属IDを設定
	 * @param string 所属ID
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
     * チェックリスト版を取得
	 * @return チェックリスト版
	 */
	public String getEdition() {
		return edition;
	}

	/**
     * チェックリスト版を設定
	 * @param string チェックリスト版
	 */
	public void setEdition(String string) {
		edition = string;
	}

	/**
     * 学振有効期限を取得
	 * @return 学振有効期限
	 */
	public boolean isPeriod() {
		return period;
	}

	/**
     * 学振有効期限を設定
	 * @param b 学振有効期限
	 */
	public void setPeriod(boolean b) {
		period = b;
	}

	/**
     * 所属コードを取得
	 * @return 所属コード
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
     * 所属コードを設定
	 * @param string 所属コード
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
     * 回数を取得
	 * @return 回数
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
     * 回数を設定
	 * @param string 回数
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

// 20050719
    /**
     * 状況IDを取得
     * @return 状況ID
     */
	public String getJokyoId() {
		return jokyoId;
	}

    /**
     * 状況IDを設定
     * @param jokyoId 状況ID
     */
	public void setJokyoId(String jokyoId) {
		this.jokyoId = jokyoId;
	}
// Horikoshi
}