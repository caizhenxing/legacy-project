/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : CheckListSearchForm.java
 *    Description : チェックリスト検索用フォーム
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

import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

/**
 * チェックリスト検索用フォーム
 * 
 * @author masuo_t
 */
public class CheckListSearchForm extends BaseSearchForm {
	
	/** 事業ID */
	private String jigyoId;

	/** 学振有効期限 */
	private boolean period;

	/** 研究種目名リスト */
	private List jigyoList = new ArrayList();

	/** 所属コード */
	private String shozokuCd;
	
	/** 事業CD */
	private String jigyoCd;
	
	//2005/04/21 追加 ここから--------------------------------------------------
	//理由 検索条件に所属機関名と受理状況が追加されたため
	
	/** 受理状況 */
	private String juriJokyo;

	/** 所属機関名 */
	private String shozokuName;
	
	/** 事業区分 */
	private String jigyoKbn;

	/** 受理状況リスト */
	private List juriList = new ArrayList();

	/** コンストラクタ */
	public CheckListSearchForm(){
		jigyoId = "";
		shozokuCd = "";
		jigyoCd = "";
		juriJokyo = "0";
		shozokuName = "";	
	}
	//追加 ここまで-------------------------------------------------------------
	
	/**
     * 事業IDを取得
	 * @return 事業ID
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
     * 事業IDを設定
	 * @param string 事業ID
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
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
     * 研究種目名リストを取得
	 * @return 研究種目名リスト
	 */
	public List getJigyoList() {
		return jigyoList;
	}

	/**
     * 研究種目名リストを設定
	 * @param list 研究種目名リスト
	 */
	public void setJigyoList(List list) {
		jigyoList = list;
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
     * 事業CDを取得
	 * @return 事業CD
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
     * 事業CDを設定
	 * @param string 事業CD
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
	}

	//2005/04/21 追加 ここから--------------------------------------------------
	//理由 検索条件に所属機関名と受理状況が追加されたため

	/**
     * 受理状況を取得
	 * @return 受理状況
	 */
	public String getJuriJokyo() {
		return juriJokyo;
	}

    /**
     * 受理状況を設定
     * @param string 受理状況
     */
    public void setJuriJokyo(String string) {
        juriJokyo = string;
    }

	/**
     * 所属機関名を取得
	 * @return 所属機関名
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
     * 所属機関名を設定
	 * @param string 所属機関名
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}
	
	/**
     * 受理状況リストを取得
	 * @return 受理状況リスト
	 */
	public List getJuriList() {
		return juriList;
	}
	
	/**
     * 受理状況リストを設定
	 * @param list 受理状況リスト
	 */
	public void setJuriList(List list) {
		juriList = list;
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
}