/*
 * Created on 2005/03/31
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * 部局担当者情報フォーム。
 *
 */
public class BukyokuForm extends BaseValidatorForm {
	
	/** 所属コード */
	private String shozokuCd;
	
	/** 所属機関名 */
	private String shozokuName;
	
	/** 所属機関名(英名) */
	private String shozokuNameEigo;
	
	/**	担当者係名 */
	private String kakariName;
	
	/** 担当者名(姓) */
	private String tantoNameSei;
	
	/** 担当者名(名) */
	private String tantoNameMei;
	
	/** 部局担当ID */
	private String bukyokutantoId;
	
	/** 部局コード */
	private List bukyokuList = new ArrayList(30);

	/** 電話番号 */
	private String bukyokuTel;

	/** FAX番号 */
	private String bukyokuFax;
	
	/** Email */
	private String bukyokuEmail;

	/** 部課名 */
	private String bukaName;
	

	//コンストラクタ
	public BukyokuForm(){
		shozokuCd = "";
		shozokuName = "";
		shozokuNameEigo = "";
		bukaName = "";
		kakariName = "";
		tantoNameSei = "";
		tantoNameMei = "";
		bukyokuTel = "";
		bukyokuFax = "";
		bukyokuEmail = "";
		bukyokutantoId = "";
		bukyokuList = new ArrayList();
		for(int i=0;i<30;i++){
			bukyokuList.add("");
		}
	}

	
	/**
	 * @return
	 */
	public List getBukyokuList() {
		return bukyokuList;
	}

	/**
	 * @return
	 */
	public String getKakariName() {
		return kakariName;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @return
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}


	/**
	 * @return
	 */
	public String getTantoNameMei() {
		return tantoNameMei;
	}

	/**
	 * @return
	 */
	public String getTantoNameSei() {
		return tantoNameSei;
	}


	/**
	 * @param list
	 */
	public void setBukyokuList(List list) {
		bukyokuList = list;
	}

	/**
	 * @param string
	 */
	public void setKakariName(String string) {
		kakariName = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameEigo(String string) {
		shozokuNameEigo = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}


	/**
	 * @param string
	 */
	public void setTantoNameMei(String string) {
		tantoNameMei = string;
	}

	/**
	 * @param string
	 */
	public void setTantoNameSei(String string) {
		tantoNameSei = string;
	}

	/**
	 * @return
	 */
	public String getBukyokutantoId() {
		return bukyokutantoId;
	}

	/**
	 * @param string
	 */
	public void setBukyokutantoId(String string) {
		bukyokutantoId = string;
	}

	/**
	 * @return
	 */
	public String getBukaName() {
		return bukaName;
	}

	/**
	 * @return
	 */
	public String getBukyokuEmail() {
		return bukyokuEmail;
	}

	/**
	 * @return
	 */
	public String getBukyokuFax() {
		return bukyokuFax;
	}

	/**
	 * @return
	 */
	public String getBukyokuTel() {
		return bukyokuTel;
	}

	/**
	 * @param string
	 */
	public void setBukaName(String string) {
		bukaName = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuEmail(String string) {
		bukyokuEmail = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuFax(String string) {
		bukyokuFax = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuTel(String string) {
		bukyokuTel = string;
	}

}
