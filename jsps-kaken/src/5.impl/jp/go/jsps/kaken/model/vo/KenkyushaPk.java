/*
 * 作成日: 2005/03/28
 *
 */
package jp.go.jsps.kaken.model.vo;

/**
 * 研究者情報（主キー）を保持するクラス。
 * 
 * @author yoshikawa_h
 *
 */
public class KenkyushaPk extends ValueObject{
	
	/** 研究者番号 */
	private String kenkyuNo;
	
	/** 所属機関コード */
	private String ShozokuCd;

	/**
	 * コンストラクタ。
	 */
	public KenkyushaPk() {
		super();
	}
	
	/**
	 * @return kenkyuNo を戻します。
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}
	/**
	 * @param kenkyuNo kenkyuNo を設定。
	 */
	public void setKenkyuNo(String kenkyuNo) {
		this.kenkyuNo = kenkyuNo;
	}
	/**
	 * @return shozokuCd を戻します。
	 */
	public String getShozokuCd() {
		return ShozokuCd;
	}
	/**
	 * @param shozokuCd shozokuCd を設定。
	 */
	public void setShozokuCd(String shozokuCd) {
		ShozokuCd = shozokuCd;
	}
}
