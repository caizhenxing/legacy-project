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
	
	/** 職名リスト */
	private List shokushuCdList = new ArrayList();
	
	/** 性別リスト */
	private List seibetsuList = new ArrayList();
	
	/** 学位リスト */
	private List gakuiList = new ArrayList();

	/** 研究者番号 */
	private String kenkyuNo;
	
	/** 申請者氏名（漢字-姓） */
	private String nameKanjiSei;

	/** 申請者氏名（漢字-名） */
	private String nameKanjiMei;

	/** 申請者氏名（フリガナ-姓） */
	private String nameKanaSei;

	/** 申請者氏名（フリガナ-名） */
	private String nameKanaMei;
	
	/** 性別 */
	private String seibetsu;

	/** 学位 */
	private String gakui;
	
	/** 所属機関コード */
	private String shozokuCd;
	
	/** 部局コード */
	private String bukyokuCd;
	
	/** 部局名 */
	private String bukyokuName;
	
	/** 職名コード */
	private String shokushuCd;
	
	/** 職名 */
	private String shokushuName;
	
	/** 備考 */
	private String biko;
	
	/** 生年月日(年) */
	private String birthYear;
	
	/** 生年月日(月) */
	private String birthMonth;

	/** 生年月日(日) */
	private String birthDay;
	
	//2005/05/25 追加 ここから--------------------------------------
	//理由 所属機関名表示の為
	/** 所属機関名(和文) */
	private String shozokuNameKanji;
	
	/** 所属機関名(英文) */
	private String shozokuNameEigo;
	//追加 ここまで-------------------------------------------------
	
	
	//2006/02/27 追加ここから
	//理由　応募資格を追加する
	private String ouboShikaku;
	//ここまで　苗
	
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
	
	//2005/05/25 追加 ここから--------------------------------------
	//理由 所属機関名表示の為

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
	//追加 ここまで-------------------------------------------------

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
