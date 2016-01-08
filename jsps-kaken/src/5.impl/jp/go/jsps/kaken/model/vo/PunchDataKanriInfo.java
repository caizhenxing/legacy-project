/*
 * 作成日: 2004/10/20
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;

/**
 * @author kainuma
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class PunchDataKanriInfo extends ValueObject{
	
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** パンチデータ種別 */
	private String punchShubetu;
	
	/** パンチデータ名称 */
	private String punchName;
	
	/** 事業区分 */
	private String jigyoKubun;
	
	/** 作成日時 */
	private Date sakuseiDate;
	
	/** パンチデータファイルパス */
	private String punchPath;
	


	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public PunchDataKanriInfo() {
		super();
	}
	
	
	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------
	
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------	


	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @return
	 */
	public String getPunchName() {
		return punchName;
	}

	/**
	 * @return
	 */
	public String getPunchPath() {
		return punchPath;
	}

	/**
	 * @return
	 */
	public String getPunchShubetu() {
		return punchShubetu;
	}

	/**
	 * @return
	 */
	public Date getSakuseiDate() {
		return sakuseiDate;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @param string
	 */
	public void setPunchName(String string) {
		punchName = string;
	}

	/**
	 * @param string
	 */
	public void setPunchPath(String string) {
		punchPath = string;
	}

	/**
	 * @param string
	 */
	public void setPunchShubetu(String string) {
		punchShubetu = string;
	}

	/**
	 * @param date
	 */
	public void setSakuseiDate(Date date) {
		sakuseiDate = date;
	}

}
