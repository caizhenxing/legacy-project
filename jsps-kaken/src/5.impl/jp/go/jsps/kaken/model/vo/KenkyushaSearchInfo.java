/*
 * Created on 2005/04/16
 *
 */
package jp.go.jsps.kaken.model.vo;

/**
 * �����Ҍ�������ێ�����N���X.
 * 
 * @author masuo_t
 *
 */
public class KenkyushaSearchInfo extends SearchInfo {
	
	/** �\����ID */
	private String kenkyuNo;

	/** �\���Ҏ����i����-���j */
	private String nameKanjiSei;

	/** �\���Ҏ����i����-���j */
	private String nameKanjiMei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String nameKanaSei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String nameKanaMei;

	/** �����@�փR�[�h */
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
