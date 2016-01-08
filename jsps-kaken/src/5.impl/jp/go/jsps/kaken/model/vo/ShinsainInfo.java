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

import java.util.Date;

/**
 * 審査員情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinsainInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class ShinsainInfo extends ShinsainPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 審査員ID */
	private String shinsainId;

//	/** 審査員番号 */
//	private String shinsainNo;

	/** 審査員氏名（漢字-姓） */
	private String nameKanjiSei;

	/** 審査員氏名（漢字-名） */
	private String nameKanjiMei;

	/** 審査員氏名（カナ-姓） */
	private String nameKanaSei;

	/** 審査員氏名（カナ-名） */
	private String nameKanaMei;

	/** 所属機関名（コード） */
	private String shozokuCd;

	/** 所属機関名（和文） */
	private String shozokuName;

//	/** 部局名（コード）*/
//	private String bukyokuCd;

	/** 部局名（和文） */
	private String bukyokuName;

//	/** 職種コード */
//	private String shokushuCd;

	/** 職種名称 */
	private String shokushuName;

//	/** 系別 */
//	private String keiCd;
//
//	/** 審査可不可 */
//	private String shinsaKahi;

	/** 送付先（郵便番号） */
	private String sofuZip;

	/** 送付先（住所） */
	private String sofuZipaddress;

	/** 送付先（Email） */
	private String sofuZipemail;

//	/** 送付先（Email2） */
//	private String sofuZipemail2;

	/** 電話番号 */
	private String shozokuTel;

//	/** 自宅電話番号 */
//	private String jitakuTel;

//	/** 新規・継続 */
//	private String sinkiKeizokuFlg;
//
//	/** 新規・継続(表示用) */
//	private String sinkiKeizokuHyoji;
//
//	/** 委嘱開始日 */
//	private Date kizokuStart;
//
//	/** 委嘱終了日 */
//	private Date kizokuEnd;
//
//	/** 謝金 */
//	private String shakin;
//
//	/** 謝金(表示用) */
//	private String shakinHyoji;

	/** URL */
	private String url;

//	/** 分科細目コード（A） */
//	private String levelA1;
//
//	/** 分科細目コード（B1-1） */
//	private String levelB11;
//
//	/** 分科細目コード（B1-2） */
//	private String levelB12;
//
//	/** 分科細目コード（B1-3） */
//	private String levelB13;
//
//	/** 分科細目コード（B2-1） */
//	private String levelB21;
//
//	/** 分科細目コード（B2-2） */
//	private String levelB22;
//
//	/** 分科細目コード（B2-3） */
//	private String levelB23;
//
//	/** 専門分野のキーワード(1) */
//	private String key1;
//
//	/** 専門分野のキーワード(2) */
//	private String key2;
//
//	/** 専門分野のキーワード(3) */
//	private String key3;
//
//	/** 専門分野のキーワード(4) */
//	private String key4;
//
//	/** 専門分野のキーワード(5) */
//	private String key5;
//
//	/** 専門分野のキーワード(6) */
//	private String key6;
//
//	/** 専門分野のキーワード(7) */
//	private String key7;

	/** パスワード */
	private String password;

	/** 備考 */
	private String biko;

	/** 有効期限 */
	private Date yukoDate;

	/** 削除フラグ */
	private String delFlg;

//	/** 担当事業区分 */
//	private String jigyoKubun;

	/** FAX番号 */
	private String shozokuFax;

	/** 専門 */
	private String senmon;

	/** 更新日(年) */
	private Date koshinDate;
	
//最終ログイン日を追加
	/** 最終ログイン日 */
	private Date loginDate; 

//メールフラグを追加
	/** メールフラグ */
	private String mailFlg;
	
//	2006/10/24 易旭 追加ここから
    /** 研究計画調書ダウンロードフラグ*/
	private String downloadFlag;
//	2006/10/24 易旭 追加ここまで

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsainInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

    /**
     * 備考を取得
     * @return 備考
     */
    public String getBiko() {
        return biko;
    }

    /**
     * 備考を設定
     * @param string 備考
     */
    public void setBiko(String string) {
        biko = string;
    }

    /**
     * 部局名（和文）を取得
     * @return 部局名（和文）
     */
    public String getBukyokuName() {
        return bukyokuName;
    }

    /**
     * 部局名（和文）を設定
     * @param string 部局名（和文）
     */
    public void setBukyokuName(String string) {
        bukyokuName = string;
    }

    /**
     * 削除フラグを取得
     * @return 削除フラグ
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * 削除フラグを設定
     * @param string 削除フラグ
     */
    public void setDelFlg(String string) {
        delFlg = string;
    }

    /**
     * 更新日(年)を取得
     * @return 更新日(年)
     */
    public Date getKoshinDate() {
        return koshinDate;
    }

    /**
     * 更新日(年)を設定
     * @param date 更新日(年)
     */
    public void setKoshinDate(Date date) {
        koshinDate = date;
    }

    /**
     * 審査員氏名（カナ-名）を取得
     * @return 審査員氏名（カナ-名）
     */
    public String getNameKanaMei() {
        return nameKanaMei;
    }

    /**
     * 審査員氏名（カナ-名）を設定
     * @param string 審査員氏名（カナ-名）
     */
    public void setNameKanaMei(String string) {
        nameKanaMei = string;
    }

    /**
     * 審査員氏名（カナ-姓）を取得
     * @return 審査員氏名（カナ-姓）
     */
    public String getNameKanaSei() {
        return nameKanaSei;
    }

    /**
     * 審査員氏名（カナ-姓）を設定
     * @param string 審査員氏名（カナ-姓）
     */
    public void setNameKanaSei(String string) {
        nameKanaSei = string;
    }

    /**
     * 審査員氏名（漢字-名）を取得
     * @return 審査員氏名（漢字-名）
     */
    public String getNameKanjiMei() {
        return nameKanjiMei;
    }

    /**
     * 審査員氏名（漢字-名）を設定
     * @param string 審査員氏名（漢字-名）
     */
    public void setNameKanjiMei(String string) {
        nameKanjiMei = string;
    }

    /**
     * 審査員氏名（漢字-姓）を取得
     * @return 審査員氏名（漢字-姓）
     */
    public String getNameKanjiSei() {
        return nameKanjiSei;
    }

    /**
     * 審査員氏名（漢字-姓）を設定
     * @param string 審査員氏名（漢字-姓）
     */
    public void setNameKanjiSei(String string) {
        nameKanjiSei = string;
    }

    /**
     * パスワードを取得
     * @return パスワード
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードを設定
     * @param string パスワード
     */
    public void setPassword(String string) {
        password = string;
    }

    /**
     * 専門を取得
     * @return 専門
     */
    public String getSenmon() {
        return senmon;
    }

    /**
     * 専門を設定
     * @param string 専門
     */
    public void setSenmon(String string) {
        senmon = string;
    }

    /**
     * 審査員IDを取得
     * @return 審査員ID
     */
    public String getShinsainId() {
        return shinsainId;
    }

    /**
     * 審査員IDを設定
     * @param string 審査員ID
     */
    public void setShinsainId(String string) {
        shinsainId = string;
    }

//    /**
//     * 審査員番号を取得
//     * @return 審査員番号
//     */
//    public String getShinsainNo() {
//        return shinsainNo;
//    }
//
//    /**
//     * 審査員番号を設定
//     * @param string 審査員番号
//     */
//    public void setShinsainNo(String string) {
//        shinsainNo = string;
//    }

    /**
     * 職種名称を取得
     * @return 職種名称
     */
    public String getShokushuName() {
        return shokushuName;
    }

    /**
     * 職種名称を設定
     * @param string 職種名称
     */
    public void setShokushuName(String string) {
        shokushuName = string;
    }

    /**
     * 所属機関名（コード）を取得
     * @return 所属機関名（コード）
     */
    public String getShozokuCd() {
        return shozokuCd;
    }

    /**
     * 所属機関名（コード）を設定
     * @param string 所属機関名（コード）
     */
    public void setShozokuCd(String string) {
        shozokuCd = string;
    }

    /**
     * FAX番号を取得
     * @return FAX番号
     */
    public String getShozokuFax() {
        return shozokuFax;
    }

    /**
     * FAX番号を設定
     * @param string FAX番号
     */
    public void setShozokuFax(String string) {
        shozokuFax = string;
    }

    /**
     * 所属機関名（和文）を取得
     * @return 所属機関名（和文）
     */
    public String getShozokuName() {
        return shozokuName;
    }

    /**
     * 所属機関名（和文）を設定
     * @param string 所属機関名（和文）
     */
    public void setShozokuName(String string) {
        shozokuName = string;
    }

    /**
     * 電話番号を取得
     * @return 電話番号
     */
    public String getShozokuTel() {
        return shozokuTel;
    }

    /**
     * 電話番号を設定
     * @param string 電話番号
     */
    public void setShozokuTel(String string) {
        shozokuTel = string;
    }

    /**
     * 送付先（郵便番号）を取得
     * @return 送付先（郵便番号）
     */
    public String getSofuZip() {
        return sofuZip;
    }

    /**
     * 送付先（郵便番号）を設定
     * @param string 送付先（郵便番号）
     */
    public void setSofuZip(String string) {
        sofuZip = string;
    }

    /**
     * 送付先（住所）を取得
     * @return 送付先（住所）
     */
    public String getSofuZipaddress() {
        return sofuZipaddress;
    }

    /**
     * 送付先（住所）を設定
     * @param string 送付先（住所）
     */
    public void setSofuZipaddress(String string) {
        sofuZipaddress = string;
    }

    /**
     * 送付先（Email）を取得
     * @return 送付先（Email）
     */
    public String getSofuZipemail() {
        return sofuZipemail;
    }

    /**
     * 送付先（Email）を設定
     * @param string 送付先（Email）
     */
    public void setSofuZipemail(String string) {
        sofuZipemail = string;
    }

    /**
     * URLを取得
     * @return URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * URLを設定
     * @param string URL
     */
    public void setUrl(String string) {
        url = string;
    }

//    /**
//     * 担当事業区分を取得
//     * @return 担当事業区分
//     */
//    public String getJigyoKubun() {
//        return jigyoKubun;
//    }
//
//    /**
//     * 担当事業区分を設定
//     * @param string 担当事業区分
//     */
//    public void setJigyoKubun(String string) {
//        jigyoKubun = string;
//    }

    /**
     * 有効期限を取得
     * @return 有効期限
     */
    public Date getYukoDate() {
        return yukoDate;
    }

    /**
     * 有効期限を設定
     * @param date 有効期限
     */
    public void setYukoDate(Date date) {
        yukoDate = date;
    }

    /**
     * 最終ログイン日を取得
     * @return 最終ログイン日
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * 最終ログイン日を設定
     * @param string 最終ログイン日
     */
    public void setLoginDate(Date date) {
        loginDate = date;
    }

    /**
     * メールフラグを取得
     * @return メールフラグ
     */
    public String getMailFlg() {
        return mailFlg;
    }

    /**
     * メールフラグを設定
     * @param string メールフラグ
     */
    public void setMailFlg(String string) {
        mailFlg = string;
    }

// 2006/10/24 易旭 追加ここから
    /**
     * 研究計画調書ダウンロードフラグを取得
     * @return 研究計画調書ダウンロードフラグ
     */
    public String getDownloadFlag() {
        return downloadFlag;
    }

    /**
     * 研究計画調書ダウンロードフラグを設定
     * @param downloadFlag 研究計画調書ダウンロードフラグ
     */
    public void setDownloadFlag(String downloadFlag) {
        this.downloadFlag = downloadFlag;
    }
//	2006/10/24 易旭 追加ここまで
}
