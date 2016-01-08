/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : CheckListForm.java
 *    Description : 受理登録対象応募情報一覧用フォーム
 *
 *    Author      : masuo_t
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/03/31    V1.0                       新規作成
 *    2006/06/06    V1.1        DIS.GongXB     修正（事業CDを追加）
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.checkList;

import jp.go.jsps.kaken.web.struts.BaseForm;

/**
 * チェックリスト一覧フォーム。
 * @author masuo_t
 */
public class CheckListForm extends BaseForm {
	
	/** 所属CD */
	private String jigyoId;

	/** チェックリスト版 */
	private String edition;
	
    /** 事業区分 */
	private String jigyoKbn;
    
//2006/06/02 gongXiuBin 添加ここから
    /** 事業CD */
    private String jigyoCd;
//2006/06/02 gongXiuBin 添加ここまで

    /**
     * 所属CDを取得
	 * @return 所属CD
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
     * 所属CDを設定
	 * @param string 所属CD
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
     * 事業区分を取得
     * @return 事業区分
     */
	public String getJigyoKbn() {
		return jigyoKbn;
	}

    /**
     * 事業区分を設定
     * @param jigyoKbn 事業区分
     */
	public void setJigyoKbn(String jigyoKbn) {
		this.jigyoKbn = jigyoKbn;
	}

//2006/06/02 gongXiuBin 添加ここから
    /**
     * 事業CDを取得
     * @return 事業CD
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * 事業CDを設定
     * @param jigyoCd 事業CD
     */
    public void setJigyoCd(String jigyoCd) {
        this.jigyoCd = jigyoCd;
    }
//2006/06/02 gongXiuBin 添加ここまで
}