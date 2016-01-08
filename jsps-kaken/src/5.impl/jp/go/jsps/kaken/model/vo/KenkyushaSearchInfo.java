/*
 * Created on 2005/04/16
 *
 */
package jp.go.jsps.kaken.model.vo;

/**
 * 研究者検索情報を保持するクラス.
 * 
 * @author masuo_t
 *
 */
public class KenkyushaSearchInfo extends SearchInfo {
	
	/** 申請者ID */
	private String kenkyuNo;

	/** 申請者氏名（漢字-姓） */
	private String nameKanjiSei;

	/** 申請者氏名（漢字-名） */
	private String nameKanjiMei;

	/** 申請者氏名（フリガナ-姓） */
	private String nameKanaSei;

	/** 申請者氏名（フリガナ-名） */
	private String nameKanaMei;

	/** 所属機関コード */
	private String shozokuCd;

	/**
	 * @return
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}

		/**
		 * @return
		 */
		public String getNameKanaMei() {
			return nameKanaMei;
		}

		/**
		 * @return
		 */
		public String getNameKanaSei() {
			return nameKanaSei;
		}

		/**
		 * @return
		 */
		public String getNameKanjiMei() {
			return nameKanjiMei;
		}

		/**
		 * @return
		 */
		public String getNameKanjiSei() {
			return nameKanjiSei;
		}

		/**
		 * @return
		 */
		public String getShozokuCd() {
			return shozokuCd;
		}

	/**
	 * @param string
	 */
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

		/**
		 * @param string
		 */
		public void setNameKanaMei(String string) {
			nameKanaMei = string;
		}

		/**
		 * @param string
		 */
		public void setNameKanaSei(String string) {
			nameKanaSei = string;
		}

		/**
		 * @param string
		 */
		public void setNameKanjiMei(String string) {
			nameKanjiMei = string;
		}

		/**
		 * @param string
		 */
		public void setNameKanjiSei(String string) {
			nameKanjiSei = string;
		}

		/**
		 * @param string
		 */
		public void setShozokuCd(String string) {
			shozokuCd = string;
		}

}
