/*
 */
package jp.go.jsps.kaken.model.vo;

import java.io.Serializable;

/**
 * @author tanioka_h
 */
public class KaigaiBunyaInfo implements Serializable {

	/** ŠCŠO•ª–ìCD */
	private String kaigaibunyaCD;
	/** ŠCŠO•ª–ì–¼ */
	private String kaigaibunyaName;
	/** ŠCŠO•ª–ì–¼—ªÌ */
	private String kaigaibunyaNameRyaku;
	/** ”õl */
	private String biko;
	
	/**
	 * 
	 */
	public KaigaiBunyaInfo() {
	}

	/**
	 * @return Returns the biko.
	 */
	public String getBiko() {
		return biko;
	}
	/**
	 * @param biko The biko to set.
	 */
	public void setBiko(String biko) {
		this.biko = biko;
	}
	/**
	 * @return Returns the kaigaibunyaCD.
	 */
	public String getKaigaibunyaCD() {
		return kaigaibunyaCD;
	}
	/**
	 * @param kaigaibunyaCD The kaigaibunyaCD to set.
	 */
	public void setKaigaibunyaCD(String kaigaibunyaCD) {
		this.kaigaibunyaCD = kaigaibunyaCD;
	}
	/**
	 * @return Returns the kaigaibunyaName.
	 */
	public String getKaigaibunyaName() {
		return kaigaibunyaName;
	}
	/**
	 * @param kaigaibunyaName The kaigaibunyaName to set.
	 */
	public void setKaigaibunyaName(String kaigaibunyaName) {
		this.kaigaibunyaName = kaigaibunyaName;
	}
	/**
	 * @return Returns the kaigaibunyaNameRyaku.
	 */
	public String getKaigaibunyaNameRyaku() {
		return kaigaibunyaNameRyaku;
	}
	/**
	 * @param kaigaibunyaNameRyaku The kaigaibunyaNameRyaku to set.
	 */
	public void setKaigaibunyaNameRyaku(String kaigaibunyaNameRyaku) {
		this.kaigaibunyaNameRyaku = kaigaibunyaNameRyaku;
	}
}
