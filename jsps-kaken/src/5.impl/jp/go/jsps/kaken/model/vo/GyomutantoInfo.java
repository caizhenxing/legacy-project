/*======================================================================
 *    SYSTEM      :
 *    Source name :
 *    Description :
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *======================================================================
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * 業務担当者情報を保持するクラス。
 *
 * ID RCSfile="$RCSfile: GyomutantoInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class GyomutantoInfo extends GyomutantoPk {
    //---------------------------------------------------------------------
    // Instance data
    //---------------------------------------------------------------------

    /** 業務担当者ID */
    private String gyomutantoId;

    /** 管理者フラグ */
    private String adminFlg;

    /** パスワード */
    private String password;

    /** 業務担当者氏名（漢字-姓） */
    private String nameKanjiSei;

    /** 業務担当者氏名（漢字-名） */
    private String nameKanjiMei;

    /** 業務担当者氏名（フリガナ-姓） */
    private String nameKanaSei;

    /** 業務担当者氏名（フリガナ-名） */
    private String nameKanaMei;

    /** 部課名 */
    private String bukaName;

    /** 係名 */
    private String kakariName;

    /** 備考 */
    private String biko;

    /** 削除フラグ*/
    private String delFlg;

    /** 有効期限 */
    private Date yukoDate;

    /** 担当事業コード */
    private Set tantoJigyoCd;

    /** 担当事業区分 */
    private Set tantoJigyoKubun;

    /** 事業名 */
    private String jigyoName;

    /** 事業名選択リスト */
    private List jigyoNameList = new ArrayList();

    /** 事業選択値 */
    private List jigyoValues = new ArrayList();

    //...
    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------

    /**
     * コンストラクタ。
     */
    public GyomutantoInfo() {
        super();
    }

    //---------------------------------------------------------------------
    // Properties
    //---------------------------------------------------------------------

    /**
     * @return
     */
    public String getAdminFlg() {
        return adminFlg;
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
    public String getBukaName() {
        return bukaName;
    }

    /**
     * @return
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * @return
     */
    public String getGyomutantoId() {
        return gyomutantoId;
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
    public String getPassword() {
        return password;
    }

    /**
     * @return
     */
    public Date getYukoDate() {
        return yukoDate;
    }

    /**
     * @param string
     */
    public void setAdminFlg(String string) {
        adminFlg = string;
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
    public void setBukaName(String string) {
        bukaName = string;
    }

    /**
     * @param string
     */
    public void setDelFlg(String string) {
        delFlg = string;
    }

    /**
     * @param string
     */
    public void setGyomutantoId(String string) {
        gyomutantoId = string;
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
    public void setPassword(String string) {
        password = string;
    }

    /**
     * @param date
     */
    public void setYukoDate(Date date) {
        yukoDate = date;
    }

    /**
     * @return
     */
    public Set getTantoJigyoCd() {
        return tantoJigyoCd;
    }

    /**
     * @return
     */
    public Set getTantoJigyoKubun() {
        return tantoJigyoKubun;
    }

    /**
     * @return
     */
    public String getJigyoName() {
        return jigyoName;
    }

    /**
     * @return
     */
    public List getJigyoNameList() {
        return jigyoNameList;
    }

    /**
     * @return
     */
    public List getJigyoValues() {
        return jigyoValues;
    }

    /**
     * @param string
     */
    public void setJigyoName(String string) {
        jigyoName = string;
    }

    /**
     * @param list
     */
    public void setJigyoNameList(List list) {
        jigyoNameList = list;
    }

    /**
     * @param list
     */
    public void setJigyoValues(List list) {
        jigyoValues = list;
    }
	/**
	 * @param set
	 */
	public void setTantoJigyoCd(Set set) {
		tantoJigyoCd = set;
	}

	/**
	 * @param set
	 */
	public void setTantoJigyoKubun(Set set) {
		tantoJigyoKubun = set;
	}

}
