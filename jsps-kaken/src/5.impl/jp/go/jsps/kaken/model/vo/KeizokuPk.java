/*
 * Created on 2005/04/18
 *
 */
package jp.go.jsps.kaken.model.vo;

/**
 * @author masuo_t
 *
 */
public class KeizokuPk extends ValueObject {

	/** Ž–‹ÆID */
	public String jigyoId;
	
	/** ‰Û‘èNO */
	public String kadaiNo;
	
	
	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @return
	 */
	public String getKadaiNo() {
		return kadaiNo;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @param string
	 */
	public void setKadaiNo(String string) {
		kadaiNo = string;
	}

}
