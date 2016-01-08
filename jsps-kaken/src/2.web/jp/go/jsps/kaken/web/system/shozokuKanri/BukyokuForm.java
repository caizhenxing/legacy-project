/*
 * Created on 2005/03/31
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * ���ǒS���ҏ��t�H�[���B
 *
 */
public class BukyokuForm extends BaseValidatorForm {
	
	/** �����R�[�h */
	private String shozokuCd;
	
	/** �����@�֖� */
	private String shozokuName;
	
	/** �����@�֖�(�p��) */
	private String shozokuNameEigo;
	
	/**	�S���ҌW�� */
	private String kakariName;
	
	/** �S���Җ�(��) */
	private String tantoNameSei;
	
	/** �S���Җ�(��) */
	private String tantoNameMei;
	
	/** ���ǒS��ID */
	private String bukyokutantoId;
	
	/** ���ǃR�[�h */
	private List bukyokuList = new ArrayList(30);

	/** �d�b�ԍ� */
	private String bukyokuTel;

	/** FAX�ԍ� */
	private String bukyokuFax;
	
	/** Email */
	private String bukyokuEmail;

	/** ���ۖ� */
	private String bukaName;
	

	//�R���X�g���N�^
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
