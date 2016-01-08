/*
 * Created on 2005/04/05
 *
 */
package jp.go.jsps.kaken.model.vo;

/**
 * 部局管理情報検索用データ保持クラス
 *
 */
public class BukyokuSearchInfo extends SearchInfo {
	
	/** 所属機関コード */
	private String shozokuCd;
	
	/**
	 * @return 所属機関コードを返します。
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @param shozokuCd 所属機関コードを設定します。
	 */
	public void setShozokuCd(String shozokuCd) {
		this.shozokuCd = shozokuCd;
	}

}
