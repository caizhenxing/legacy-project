/*
 * Created on 2005/04/18
 *
 */
package jp.go.jsps.kaken.model.vo;

/**
 * @author masuo_t
 *
 */
public class KeizokuInfo extends KeizokuPk {

	/** �O�N�x�敪 */
	public String zennendoKubun;
	
	/** ���l */
	public String biko;
//<!-- ADD�@START 2007/07/18 BIS ���� -->
	/** �����Ҕԍ� */
	public String kenkyuNo;

	/** �����ۑ薼 */
	public String kadaiNameKanji;
	
	/** 1�N�ړ���z */
	public String naiyakugaku1;
	
	/** 2�N�ړ���z */
	public String naiyakugaku2;
	
	/** 3�N�ړ���z */
	public String naiyakugaku3;
	
	/** 4�N�ړ���z */
	public String naiyakugaku4;
	
	/** 5�N�ړ���z */
	public String naiyakugaku5;
//<!-- ADD�@END�@ 2007/07/18 BIS ���� -->	
	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @return
	 */
	public String getZennendoKubun() {
		return zennendoKubun;
	}

	/**
	 * @param string
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @param string
	 */
	public void setZennendoKubun(String string) {
		zennendoKubun = string;
	}
//<!-- ADD�@START 2007/07/18 BIS ���� -->
	/**
	 * @return Returns the kadaiNameKanji.
	 */
	public String getKadaiNameKanji() {
		return kadaiNameKanji;
	}

	/**
	 * @param kadaiNameKanji The kadaiNameKanji to set.
	 */
	public void setKadaiNameKanji(String kadaiNameKanji) {
		this.kadaiNameKanji = kadaiNameKanji;
	}

	/**
	 * @return Returns the kenkyuNo.
	 */
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
	 * @param kenkyuNo The kenkyuNo to set.
	 */
	public void setKenkyuNo(String kenkyuNo) {
		this.kenkyuNo = kenkyuNo;
	}

	/**
	 * @return Returns the naiyakugaku1.
	 */
	public String getNaiyakugaku1() {
		return naiyakugaku1;
	}

	/**
	 * @param naiyakugaku1 The naiyakugaku1 to set.
	 */
	public void setNaiyakugaku1(String naiyakugaku1) {
		this.naiyakugaku1 = naiyakugaku1;
	}

	/**
	 * @return Returns the naiyakugaku2.
	 */
	public String getNaiyakugaku2() {
		return naiyakugaku2;
	}

	/**
	 * @param naiyakugaku2 The naiyakugaku2 to set.
	 */
	public void setNaiyakugaku2(String naiyakugaku2) {
		this.naiyakugaku2 = naiyakugaku2;
	}

	/**
	 * @return Returns the naiyakugaku3.
	 */
	public String getNaiyakugaku3() {
		return naiyakugaku3;
	}

	/**
	 * @param naiyakugaku3 The naiyakugaku3 to set.
	 */
	public void setNaiyakugaku3(String naiyakugaku3) {
		this.naiyakugaku3 = naiyakugaku3;
	}

	/**
	 * @return Returns the naiyakugaku4.
	 */
	public String getNaiyakugaku4() {
		return naiyakugaku4;
	}

	/**
	 * @param naiyakugaku4 The naiyakugaku4 to set.
	 */
	public void setNaiyakugaku4(String naiyakugaku4) {
		this.naiyakugaku4 = naiyakugaku4;
	}

	/**
	 * @return Returns the naiyakugaku5.
	 */
	public String getNaiyakugaku5() {
		return naiyakugaku5;
	}

	/**
	 * @param naiyakugaku5 The naiyakugaku5 to set.
	 */
	public void setNaiyakugaku5(String naiyakugaku5) {
		this.naiyakugaku5 = naiyakugaku5;
	}
//<!-- ADD�@END�@ 2007/07/18 BIS ���� -->
}
