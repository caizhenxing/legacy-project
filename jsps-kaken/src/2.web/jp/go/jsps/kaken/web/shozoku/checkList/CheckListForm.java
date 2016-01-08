/*
 * Created on 2005/03/31
 *
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import jp.go.jsps.kaken.web.struts.BaseForm;

/**
 * 
 * @author masuo_t
 *
 */
public class CheckListForm extends BaseForm {
	
	/** 事業ＩＤ */
	private String jigyoId;

	/** チェックリスト版 */
	private String edition;

	//	2005/04/11 追加 ここから--------------------------------------------------
	//有効期限の追加
	/** 学振有効期限 */
	private boolean period;
	//	追加 ここまで-------------------------------------------------------------
	
// 20050715 研究者存在の追加
    /** 研究者存在フラグ */
	private boolean blnKenkyushaExist;
// Horikoshi

	/** 事業区分 */
	private String jigyoKbn;

    //add start ly 2006/06/01
    /** 事業CD */
    private String jigyoCd;
    //add end ly 2006/06/01

    /**
	 * 事業区分を返す
	 * @return
	 */
	public String getJigyoKbn(){
		return jigyoKbn;
	}
	
	/**
	 * 事業区分を設定する
	 * @param str 
	 */
	public void setJigyoKbn(String str){
		jigyoKbn = str;
	}
	
	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @return
	 */
	public String getEdition() {
		return edition;
	}

	/**
	 * @param string
	 */
	public void setEdition(String string) {
		edition = string;
	}

	//2005/04/11 追加 ここから--------------------------------------------------
	//理由　有効期限の追加
	/**
	 * @return
	 */
	public boolean isPeriod() {
		return period;
	}

	/**
	 * @param b
	 */
	public void setPeriod(boolean b) {
		period = b;
	}
	//	追加 ここまで-------------------------------------------------------------

// 20050715 研究者存在の追加
    /**
     * 研究者存在フラグを取得
     * @return boolean 研究者存在フラグ
     */
	public boolean isKenkyushaExist() {
		return blnKenkyushaExist;
	}
    /**
     * 研究者存在フラグを設定
     * @param blnExist 研究者存在フラグ
     */
	public void setKenkyushaExist(boolean blnExist) {
		blnKenkyushaExist = blnExist;
	}
// Horikoshi

    /**
     * 事業CDを取得
     * @return String 事業CD
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
}