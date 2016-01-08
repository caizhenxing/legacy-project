/*
 * Created on 2005/04/15
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import java.util.ArrayList;
import java.util.List;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

/**
 * @author masuo_t
 *
 */
public class KenkyushaForm extends BaseValidatorForm {
	
	/** �E�����X�g */
	private List shokushuCdList = new ArrayList();
	
	/** ���ʃ��X�g */
	private List seibetsuList = new ArrayList();
	
	/** �w�ʃ��X�g */
	private List gakuiList = new ArrayList();

	/** �����Ҕԍ� */
	private String kenkyuNo;
	
	/** �\���Ҏ����i����-���j */
	private String nameKanjiSei;

	/** �\���Ҏ����i����-���j */
	private String nameKanjiMei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String nameKanaSei;

	/** �\���Ҏ����i�t���K�i-���j */
	private String nameKanaMei;
	
	/** ���� */
	private String seibetsu;

	/** �w�� */
	private String gakui;
	
	/** �����@�փR�[�h */
	private String shozokuCd;
	
	/** ���ǃR�[�h */
	private String bukyokuCd;
	
	/** ���ǖ� */
	private String bukyokuName;
	
	/** �E���R�[�h */
	private String shokushuCd;
	
	/** �E�� */
	private String shokushuName;
	
	/** ���l */
	private String biko;
	
	/** ���N����(�N) */
	private String birthYear;
	
	/** ���N����(��) */
	private String birthMonth;

	/** ���N����(��) */
	private String birthDay;
	
	//2005/05/25 �ǉ� ��������--------------------------------------
	//���R �����@�֖��\���̈�
	/** �����@�֖�(�a��) */
	private String shozokuNameKanji;
	
	/** �����@�֖�(�p��) */
	private String shozokuNameEigo;
	//�ǉ� �����܂�-------------------------------------------------
	
	
	//2006/02/27 �ǉ���������
	//���R�@���厑�i��ǉ�����
	private String ouboShikaku;
	//�����܂Ł@�c
	
	/**
	 * @return
	 */
	public List getShokushuCdList() {
		return shokushuCdList;
	}

	/**
	 * @param list
	 */
	public void setShokushuCdList(List list) {
		shokushuCdList = list;
	}

	/**
	 * @return
	 */
	public List getGakuiList() {
		return gakuiList;
	}

	/**
	 * @return
	 */
	public List getSeibetsuList() {
		return seibetsuList;
	}

	/**
	 * @param list
	 */
	public void setGakuiList(List list) {
		gakuiList = list;
	}

	/**
	 * @param list
	 */
	public void setSeibetsuList(List list) {
		seibetsuList = list;
	}

	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @return
	 */
	public String getBirthDay() {
		return birthDay;
	}

	/**
	 * @return
	 */
	public String getBirthMonth() {
		return birthMonth;
	}

	/**
	 * @return
	 */
	public String getBirthYear() {
		return birthYear;
	}

	/**
	 * @return
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}

	/**
	 * @return
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @return
	 */
	public String getGakui() {
		return gakui;
	}

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
	public String getSeibetsu() {
		return seibetsu;
	}

	/**
	 * @return
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}

	/**
	 * @return
	 */
	public String getShokushuName() {
		return shokushuName;
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
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @param string
	 */
	public void setBirthDay(String string) {
		birthDay = string;
	}

	/**
	 * @param string
	 */
	public void setBirthMonth(String string) {
		birthMonth = string;
	}

	/**
	 * @param string
	 */
	public void setBirthYear(String string) {
		birthYear = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
	}

	/**
	 * @param string
	 */
	public void setGakui(String string) {
		gakui = string;
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
	public void setSeibetsu(String string) {
		seibetsu = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuCd(String string) {
		shokushuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuName(String string) {
		shokushuName = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}
	
	//2005/05/25 �ǉ� ��������--------------------------------------
	//���R �����@�֖��\���̈�

	/**
	 * @return
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @return
	 */
	public String getShozokuNameKanji() {
		return shozokuNameKanji;
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
	public void setShozokuNameKanji(String string) {
		shozokuNameKanji = string;
	}
	//�ǉ� �����܂�-------------------------------------------------

	/**
	 * @return Returns the ouboShikaku.
	 */
	public String getOuboShikaku() {
		return ouboShikaku;
	}

	/**
	 * @param ouboShikaku The ouboShikaku to set.
	 */
	public void setOuboShikaku(String ouboShikaku) {
		this.ouboShikaku = ouboShikaku;
	}
}
