/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : RyoikiKeikakushoInfo.java
 *    Description : 受理登録画面表示アクションクラス
 *
 *    Author      : DIS.jzx
 *    Date        : 2006/06/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.jzx        新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 領域計画書（概要）情報を保持するクラス。
 *
 * ID RCSfile="$RCSfile: RyoikiKeikakushoInfo.java,v $"
 * Revision="$Revision: 1.2 $"
 * Date="$Date: 2007/07/25 06:48:38 $"
 */
public class RyoikiKeikakushoInfo extends RyoikiKeikakushoPk {

    /** 仮領域番号 */
    private String kariryoikiNo;

    /** 受付番号（整理番号） */
    private String uketukeNo;

    /** 事業ID */
    private String jigyoId;
    
    /** 事業コード */
    private String jigyoCd;

    /** 年度 */
    private String nendo;
    
    /** 事業年度（西暦下２ケタ）*/
    private String nendoSeireki;

    /** 回数 */
    private String kaisu;

    /** 事業名 */
    private String jigyoName;

    /** 申請者ID */
    private String shinseishaId;

    /** 領域計画書確定日 */
    private Date kakuteiDate;

    /** 領域計画書概要作成日 */
    private Date sakuseiDate;

    /** 所属機関承認日 */
    private Date shoninDate;

    /** 学振受理日 */
    private Date jyuriDate;

    /** 領域代表者氏名（漢字等-姓） */
    private String nameKanjiSei;

    /** 領域代表者氏名（漢字等-名） */
    private String nameKanjiMei;

    /** 領域代表者氏名（フリガナ-姓） */
    private String nameKanaSei;

    /** 領域代表者氏名（フリガナ-名） */
    private String nameKanaMei;

    /** 年齢 */
    private String nenrei;

    /** 申請者研究者番号 */
    private String kenkyuNo;

    /** 所属機関コード */
    private String shozokuCd;

    /** 所属機関名 */
    private String shozokuName;

    /** 所属機関名（略称） */
    private String shozokuNameRyaku;

    /** 部局コード */
    private String bukyokuCd;

    /** 部局名 */
    private String bukyokuName;

    /** 部局名（略称） */
    private String bukyokuNameRyaku;

    /** 職名コード */
    private String shokushuCd;

    /** 職名（和文） */
    private String shokushuNameKanji;

    /** 職名（略称） */
    private String shokushuNameRyaku;

    /** 審査希望部門（系等）コード */
    private String kiboubumonCd;

    /** 審査希望部門（系等）名称 */
    private String kiboubumonName;

    /** 応募領域名 */
    private String ryoikiName;

    /** 英訳名 */
    private String ryoikiNameEigo;

    /** 領域略称名 */
    private String ryoikiNameRyaku;

    /** 研究概要 */
    private String kenkyuGaiyou;

    /** 事前調査の状況 */
    private String jizenchousaFlg;

    /** 事前調査の状況（その他） */
    private String jizenchousaSonota;

    /** 過去の応募状況 */
    private String kakoOubojyoukyou;

    /** 最終年度前年度の応募（該当の有無） */
    private String zennendoOuboFlg;

    /** 最終年度前年度の研究領域番号 */
    private String zennendoOuboNo;

    /** 最終年度前年度の領域略称名 */
    private String zennendoOuboRyoikiRyaku;

    /** 最終年度前年度の設定期間 */
    private String zennendoOuboSettei;

    /** 関連分野（細目番号）1 */
    private String bunkasaimokuCd1;

    /** 関連分野（分野）1 */
    private String bunyaName1;

    /** 関連分野（分科）1 */
    private String bunkaName1;

    /** 関連分野（細目）1 */
    private String saimokuName1;

    /** 関連分野（細目番号）2 */
    private String bunkasaimokuCd2;

    /** 関連分野（分野）2 */
    private String bunyaName2;

    /** 関連分野（分科）2 */
    private String bunkaName2;

    /** 関連分野（細目）2 */
    private String saimokuName2;

    /** 関連分野15分類（番号） */
    private String kanrenbunyaBunruiNo;
    
    /** 関連分野15分類（分類名） */
    private String kanrenbunyaBunruiName;

    /** 研究の必要性1 */
    private String kenkyuHitsuyousei1;

    /** 研究の必要性2 */
    private String kenkyuHitsuyousei2;

    /** 研究の必要性3 */
    private String kenkyuHitsuyousei3;

    /** 研究の必要性4 */
    private String kenkyuHitsuyousei4;

    /** 研究の必要性5 */
    private String kenkyuHitsuyousei5;

// 2006/08/25 dyh delete start 原因：仕様変更
//    /** 研究経費（1年目)-小計 */
//    private String kenkyuSyokei1;
//
//    /** 研究経費（1年目)-内訳 */
//    private String kenkyuUtiwake1;
// 2006/08/25 dyh delete end

    /** 研究経費（2年目)-小計 */
    private String kenkyuSyokei2;

    /** 研究経費（2年目)-内訳 */
    private String kenkyuUtiwake2;

    /** 研究経費（3年目)-小計 */
    private String kenkyuSyokei3;

    /** 研究経費（3年目)-内訳 */
    private String kenkyuUtiwake3;

    /** 研究経費（4年目)-小計 */
    private String kenkyuSyokei4;

    /** 研究経費（4年目)-内訳 */
    private String kenkyuUtiwake4;

    /** 研究経費（5年目)-小計 */
    private String kenkyuSyokei5;

    /** 研究経費（5年目)-内訳 */
    private String kenkyuUtiwake5;

    /** 研究経費（6年目)-小計 */
    private String kenkyuSyokei6;

    /** 研究経費（6年目)-内訳 */
    private String kenkyuUtiwake6;
    
    /** 研究経費（合計) */
    private String kenkyuTotal;

    /** 領域代表者（郵便番号） */
    private String daihyouZip;

    /** 領域代表者（住所） */
    private String daihyouAddress;

    /** 領域代表者（電話） */
    private String daihyouTel;

    /** 領域代表者（FAX） */
    private String daihyouFax;

    /** 領域代表者（メールアドレス） */
    private String daihyouEmail;

    /** 事務担当者氏名（漢字等-姓） */
    private String jimutantoNameKanjiSei;

    /** 事務担当者氏名（漢字等-名） */
    private String jimutantoNameKanjiMei;

    /** 事務担当者氏名（フリガナ-姓） */
    private String jimutantoNameKanaSei;

    /** 事務担当者氏名（フリガナ-名） */
    private String jimutantoNameKanaMei;

    /** 事務担当者研究機関番号 */
    private String jimutantoShozokuCd;

    /** 事務担当者研究機関名 */
    private String jimutantoShozokuName;

    /** 事務担当者部局番号 */
    private String jimutantoBukyokuCd;

    /** 事務担当者部局名 */
    private String jimutantoBukyokuName;

    /** 事務担当者職名番号 */
    private String jimutantoShokushuCd;

    /** 事務担当者職名（和文） */
    private String jimutantoShokushuNameKanji;

    /** 事務担当者（郵便番号） */
    private String jimutantoZip;

    /** 事務担当者（住所） */
    private String jimutantoAddress;

    /** 事務担当者（電話） */
    private String jimutantoTel;

    /** 事務担当者（FAX） */
    private String jimutantoFax;

    /** 事務担当者（メールアドレス） */
    private String jimutantoEmail;

    /** 関連分野の研究者-氏名1 */
    private String kanrenShimei1;

    /** 関連分野の研究者-所属研究機関1 */
    private String kanrenKikan1;

    /** 関連分野の研究者-部局1 */
    private String kanrenBukyoku1;

    /** 関連分野の研究者-職名1 */
    private String kanrenShoku1;

    /** 関連分野の研究者-専門分野1 */
    private String kanrenSenmon1;

    /** 関連分野の研究者-勤務先電話番号1 */
    private String kanrenTel1;

    /** 関連分野の研究者-自宅電話番号1 */
    private String kanrenJitakutel1;

    /** 関連分野の研究者-氏名2 */
    private String kanrenShimei2;

    /** 関連分野の研究者-所属研究機関2 */
    private String kanrenKikan2;

    /** 関連分野の研究者-部局2 */
    private String kanrenBukyoku2;

    /** 関連分野の研究者-職名2 */
    private String kanrenShoku2;

    /** 関連分野の研究者-専門分野2 */
    private String kanrenSenmon2;

    /** 関連分野の研究者-勤務先電話番号2 */
    private String kanrenTel2;

    /** 関連分野の研究者-自宅電話番号2 */
    private String kanrenJitakutel2;

    /** 関連分野の研究者-氏名3 */
    private String kanrenShimei3;

    /** 関連分野の研究者-所属研究機関3 */
    private String kanrenKikan3;

    /** 関連分野の研究者-部局3 */
    private String kanrenBukyoku3;

    /** 関連分野の研究者-職名3 */
    private String kanrenShoku3;

    /** 関連分野の研究者-専門分野3 */
    private String kanrenSenmon3;

    /** 関連分野の研究者-勤務先電話番号3 */
    private String kanrenTel3;

    /** 関連分野の研究者-自宅電話番号3 */
    private String kanrenJitakutel3;

    /** 領域計画書概要PDFの格納パス */
    private String pdfPath;

    /** 表紙PDF格納パス */
    private String hyoshiPdfPath;

    /** 受理結果 */
    private String juriKekka;

    /** 受理結果備考 */
    private String juriBiko;

    /** 領域計画書（概要）申請状況ID */
    private String ryoikiJokyoId;
    
    /** 領域計画書（概要）応募状況 */
    private String ryoikiJokyoName;
    
    /** 版 */
    private String edition;

    /** 確定フラグ */
    private String ryoikikeikakushoKakuteiFlg;

    /** 解除フラグ */
    private String cancelFlg;

    /** 削除フラグ */
    private String delFlg;
    
    /** 応募件数 */
    private String count;
    
    /** 申請状況配列 */
    private String[] jokyoIds;
    
    /** 領域計画書（概要）申請状況ID配列 */
    private String[] ryoikiJokyoIds;
    
    //2006.07.04 zhangt add start
    /** 研究経費表 */
    private List kenkyuKeihiList = new ArrayList();

    /** 研究組織表 */
    private List KenkyuSosikiList = new ArrayList();

    /** 添付ファイル情報 */
    private TenpuFileInfo[] tenpuFileInfos;
    //2006.07.04 zhangt add start
    
    /** 添付ファールリストを選択するの値 */
    private String tenpuFileFlg;

    //2006.09.15 iso タイトルに「概要」をつけたPDF作成のため
    /** 変換PDFのタイトルに付加する文字列 */
    private String addTitele;
    
//ADD　START 2007-07-25 BIS 劉多良
//  入力した研究組織にエラーがある場合、「領域計画書入力」画面でエラーメッセージを表示し、「研究経費表」画面でエラーになる項目の背景色を変更する。	
    /** エラーメッセジー */
    private HashMap errorsMap;
//ADD　END 2007-07-25 BIS 劉多良

    //---------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------

    /**
     * コンストラクタ。
     */
    public RyoikiKeikakushoInfo() {
        super();
    }
        
    //---------------------------------------------------------------------
    // Methods
    //---------------------------------------------------------------------

    
    //---------------------------------------------------------------------
    // Properties
    //---------------------------------------------------------------------
    
    /**
     * 仮領域番号を取得
     * @return String 仮領域番号
     */
    public String getKariryoikiNo() {
        return kariryoikiNo;
    }

    /**
     * 仮領域番号を設定
     * @param kariryoikiNo 仮領域番号
     */
    public void setKariryoikiNo(String kariryoikiNo) {
        this.kariryoikiNo = kariryoikiNo;
    }

    /**
     * 受付番号（整理番号）を取得
     * @return String 受付番号（整理番号）
     */
    public String getUketukeNo() {
        return uketukeNo;
    }

    /**
     * 受付番号（整理番号）を設定
     * @param uketukeNo 受付番号（整理番号）
     */
    public void setUketukeNo(String uketukeNo) {
        this.uketukeNo = uketukeNo;
    }

    /**
     * 事業IDを取得
     * @return String 事業ID
     */
    public String getJigyoId() {
        return jigyoId;
    }

    /**
     * 事業IDを設定
     * @param jigyoId 事業ID
     */
    public void setJigyoId(String jigyoId) {
        this.jigyoId = jigyoId;
    }

    /**
     * 年度を取得
     * @return String 年度
     */
    public String getNendo() {
        return nendo;
    }

    /**
     * 年度を設定
     * @param nendo 年度
     */
    public void setNendo(String nendo) {
        this.nendo = nendo;
    }

    /**
     * 回数を取得
     * @return String 回数
     */
    public String getKaisu() {
        return kaisu;
    }

    /**
     * 回数を設定
     * @param kaisu 回数
     */
    public void setKaisu(String kaisu) {
        this.kaisu = kaisu;
    }

    /**
     * 事業名を取得
     * @return String 事業名
     */
    public String getJigyoName() {
        return jigyoName;
    }

    /**
     * 事業名を設定
     * @param jigyoName 事業名
     */
    public void setJigyoName(String jigyoName) {
        this.jigyoName = jigyoName;
    }

    /**
     * 申請者IDを取得
     * @return String 申請者ID
     */
    public String getShinseishaId() {
        return shinseishaId;
    }

    /**
     * 申請者IDを設定
     * @param shinseishaId 申請者ID
     */
    public void setShinseishaId(String shinseishaId) {
        this.shinseishaId = shinseishaId;
    }

    /**
     * 領域計画書確定日を取得
     * @return Date 領域計画書確定日
     */
    public Date getKakuteiDate() {
        return kakuteiDate;
    }

    /**
     * 領域計画書確定日を設定
     * @param kakuteiDate 領域計画書確定日
     */
    public void setKakuteiDate(Date kakuteiDate) {
        this.kakuteiDate = kakuteiDate;
    }

    /**
     * 領域計画書概要作成日を取得
     * @return Date 領域計画書概要作成日
     */
    public Date getSakuseiDate() {
        return sakuseiDate;
    }

    /**
     * 領域計画書概要作成日を設定
     * @param sakuseiDate 領域計画書概要作成日
     */
    public void setSakuseiDate(Date sakuseiDate) {
        this.sakuseiDate = sakuseiDate;
    }

    /**
     * 所属機関承認日を取得
     * @return Date 所属機関承認日
     */
    public Date getShoninDate() {
        return shoninDate;
    }

    /**
     * 所属機関承認日を設定
     * @param shoninDate 所属機関承認日
     */
    public void setShoninDate(Date shoninDate) {
        this.shoninDate = shoninDate;
    }

    /**
     * 学振受理日を取得
     * @return Date 学振受理日
     */
    public Date getJyuriDate() {
        return jyuriDate;
    }

    /**
     * 学振受理日を設定
     * @param jyuriDate 学振受理日
     */
    public void setJyuriDate(Date jyuriDate) {
        this.jyuriDate = jyuriDate;
    }

    /**
     * 領域代表者氏名（漢字等-姓）を取得
     * @return String 領域代表者氏名（漢字等-姓）
     */
    public String getNameKanjiSei() {
        return nameKanjiSei;
    }

    /**
     * 領域代表者氏名（漢字等-姓）を設定
     * @param nameKanjiSei 領域代表者氏名（漢字等-姓）
     */
    public void setNameKanjiSei(String nameKanjiSei) {
        this.nameKanjiSei = nameKanjiSei;
    }

    /**
     * 領域代表者氏名（漢字等-名）を取得
     * @return String 領域代表者氏名（漢字等-名）
     */
    public String getNameKanjiMei() {
        return nameKanjiMei;
    }

    /**
     * 領域代表者氏名（漢字等-名）を設定
     * @param nameKanjiMei 領域代表者氏名（漢字等-名）
     */
    public void setNameKanjiMei(String nameKanjiMei) {
        this.nameKanjiMei = nameKanjiMei;
    }

    /**
     * 領域代表者氏名（フリガナ-姓）を取得
     * @return String 領域代表者氏名（フリガナ-姓）
     */
    public String getNameKanaSei() {
        return nameKanaSei;
    }

    /**
     * 領域代表者氏名（フリガナ-姓）を設定
     * @param nameKanaSei 領域代表者氏名（フリガナ-姓）
     */
    public void setNameKanaSei(String nameKanaSei) {
        this.nameKanaSei = nameKanaSei;
    }

    /**
     * 領域代表者氏名（フリガナ-名）を取得
     * @return String 領域代表者氏名（フリガナ-名）
     */
    public String getNameKanaMei() {
        return nameKanaMei;
    }

    /**
     * 領域代表者氏名（フリガナ-名）を設定
     * @param nameKanaMei 領域代表者氏名（フリガナ-名）
     */
    public void setNameKanaMei(String nameKanaMei) {
        this.nameKanaMei = nameKanaMei;
    }

    /**
     * 年齢を取得
     * @return String 年齢
     */
    public String getNenrei() {
        return nenrei;
    }

    /**
     * 年齢を設定
     * @param nenrei 年齢
     */
    public void setNenrei(String nenrei) {
        this.nenrei = nenrei;
    }

    /**
     * 申請者研究者番号を取得
     * @return String 申請者研究者番号
     */
    public String getKenkyuNo() {
        return kenkyuNo;
    }

    /**
     * 申請者研究者番号を設定
     * @param kenkyuNo 申請者研究者番号
     */
    public void setKenkyuNo(String kenkyuNo) {
        this.kenkyuNo = kenkyuNo;
    }

    /**
     * 所属機関コードを取得
     * @return String 所属機関コード
     */
    public String getShozokuCd() {
        return shozokuCd;
    }

    /**
     * 所属機関コードを設定
     * @param shozokuCd 所属機関コード
     */
    public void setShozokuCd(String shozokuCd) {
        this.shozokuCd = shozokuCd;
    }

    /**
     * 所属機関名を取得
     * @return String 所属機関名
     */
    public String getShozokuName() {
        return shozokuName;
    }

    /**
     * 所属機関名を設定
     * @param shozokuName 所属機関名
     */
    public void setShozokuName(String shozokuName) {
        this.shozokuName = shozokuName;
    }

    /**
     * 所属機関名（略称）を取得
     * @return String 所属機関名（略称）
     */
    public String getShozokuNameRyaku() {
        return shozokuNameRyaku;
    }

    /**
     * 所属機関名（略称）を設定
     * @param shozokuNameRyaku 所属機関名（略称）
     */
    public void setShozokuNameRyaku(String shozokuNameRyaku) {
        this.shozokuNameRyaku = shozokuNameRyaku;
    }

    /**
     * 部局コードを取得
     * @return String 部局コード
     */
    public String getBukyokuCd() {
        return bukyokuCd;
    }

    /**
     * 部局コードを設定
     * @param bukyokuCd 部局コード
     */
    public void setBukyokuCd(String bukyokuCd) {
        this.bukyokuCd = bukyokuCd;
    }

    /**
     * 部局名を取得
     * @return String 部局名
     */
    public String getBukyokuName() {
        return bukyokuName;
    }

    /**
     * 部局名を設定
     * @param bukyokuName 部局名
     */
    public void setBukyokuName(String bukyokuName) {
        this.bukyokuName = bukyokuName;
    }

    /**
     * 部局名（略称）を取得
     * @return String 部局名（略称）
     */
    public String getBukyokuNameRyaku() {
        return bukyokuNameRyaku;
    }

    /**
     * 部局名（略称）を設定
     * @param bukyokuNameRyaku 部局名（略称）
     */
    public void setBukyokuNameRyaku(String bukyokuNameRyaku) {
        this.bukyokuNameRyaku = bukyokuNameRyaku;
    }

    /**
     * 職名コードを取得
     * @return String 職名コード
     */
    public String getShokushuCd() {
        return shokushuCd;
    }

    /**
     * 職名コードを設定
     * @param shokushuCd 職名コード
     */
    public void setShokushuCd(String shokushuCd) {
        this.shokushuCd = shokushuCd;
    }

    /**
     * 職名（和文）を取得
     * @return String 職名（和文）
     */
    public String getShokushuNameKanji() {
        return shokushuNameKanji;
    }

    /**
     * 職名（和文）を設定
     * @param shokushuNameKanji 職名（和文）
     */
    public void setShokushuNameKanji(String shokushuNameKanji) {
        this.shokushuNameKanji = shokushuNameKanji;
    }

    /**
     * 職名（略称）を取得
     * @return Stirng 職名（略称）
     */
    public String getShokushuNameRyaku() {
        return shokushuNameRyaku;
    }

    /**
     * 職名（略称）を設定
     * @param shokushuNameRyaku 職名（略称）
     */
    public void setShokushuNameRyaku(String shokushuNameRyaku) {
        this.shokushuNameRyaku = shokushuNameRyaku;
    }

    /**
     * 審査希望部門（系等）コードを取得
     * @return String 審査希望部門（系等）コード
     */
    public String getKiboubumonCd() {
        return kiboubumonCd;
    }

    /**
     * 審査希望部門（系等）コードを設定
     * @param kiboubumonCd 審査希望部門（系等）コード
     */
    public void setKiboubumonCd(String kiboubumonCd) {
        this.kiboubumonCd = kiboubumonCd;
    }

    /**
     * 審査希望部門（系等）名称を取得
     * @return String 審査希望部門（系等）名称
     */
    public String getKiboubumonName() {
        return kiboubumonName;
    }

    /**
     * 審査希望部門（系等）名称を設定
     * @param kiboubumonName 審査希望部門（系等）名称
     */
    public void setKiboubumonName(String kiboubumonName) {
        this.kiboubumonName = kiboubumonName;
    }

    /**
     * 応募領域名を取得
     * @return String 応募領域名
     */
    public String getRyoikiName() {
        return ryoikiName;
    }

    /**
     * 応募領域名を設定
     * @param ryoikiName 応募領域名
     */
    public void setRyoikiName(String ryoikiName) {
        this.ryoikiName = ryoikiName;
    }

    /**
     * 英訳名を取得
     * @return String 英訳名
     */
    public String getRyoikiNameEigo() {
        return ryoikiNameEigo;
    }

    /**
     * 英訳名を設定
     * @param ryoikiNameEigo 英訳名
     */
    public void setRyoikiNameEigo(String ryoikiNameEigo) {
        this.ryoikiNameEigo = ryoikiNameEigo;
    }

    /**
     * 領域略称名を取得
     * @return String 領域略称名
     */
    public String getRyoikiNameRyaku() {
        return ryoikiNameRyaku;
    }

    /**
     * 領域略称名を設定
     * @param ryoikiNameRyaku 領域略称名
     */
    public void setRyoikiNameRyaku(String ryoikiNameRyaku) {
        this.ryoikiNameRyaku = ryoikiNameRyaku;
    }

    /**
     * 研究概要を取得
     * @return String 研究概要
     */
    public String getKenkyuGaiyou() {
        return kenkyuGaiyou;
    }

    /**
     * 研究概要を設定
     * @param kenkyuGaiyou 研究概要
     */
    public void setKenkyuGaiyou(String kenkyuGaiyou) {
        this.kenkyuGaiyou = kenkyuGaiyou;
    }

    /**
     * 事前調査の状況を取得
     * @return String 事前調査の状況
     */
    public String getJizenchousaFlg() {
        return jizenchousaFlg;
    }

    /**
     * 事前調査の状況を設定
     * @param jizenchousaFlg 事前調査の状況
     */
    public void setJizenchousaFlg(String jizenchousaFlg) {
        this.jizenchousaFlg = jizenchousaFlg;
    }

    /**
     * 事前調査の状況（その他）を取得
     * @return String 事前調査の状況（その他）
     */
    public String getJizenchousaSonota() {
        return jizenchousaSonota;
    }

    /**
     * 事前調査の状況（その他）を設定
     * @param jizenchousaSonota 事前調査の状況（その他）
     */
    public void setJizenchousaSonota(String jizenchousaSonota) {
        this.jizenchousaSonota = jizenchousaSonota;
    }

    /**
     * 過去の応募状況を取得
     * @return String 過去の応募状況
     */
    public String getKakoOubojyoukyou() {
        return kakoOubojyoukyou;
    }

    /**
     * 過去の応募状況を設定
     * @param kakoOubojyoukyou 過去の応募状況
     */
    public void setKakoOubojyoukyou(String kakoOubojyoukyou) {
        this.kakoOubojyoukyou = kakoOubojyoukyou;
    }

    /**
     * 最終年度前年度の応募（該当の有無）を取得
     * @return String 最終年度前年度の応募（該当の有無）
     */
    public String getZennendoOuboFlg() {
        return zennendoOuboFlg;
    }

    /**
     * 最終年度前年度の応募（該当の有無）を設定
     * @param zennendoOuboFlg 最終年度前年度の応募（該当の有無）
     */
    public void setZennendoOuboFlg(String zennendoOuboFlg) {
        this.zennendoOuboFlg = zennendoOuboFlg;
    }

    /**
     * 最終年度前年度の研究領域番号を取得
     * @return String 最終年度前年度の研究領域番号
     */
    public String getZennendoOuboNo() {
        return zennendoOuboNo;
    }

    /**
     * 最終年度前年度の研究領域番号を設定
     * @param zennendoOuboNo 最終年度前年度の研究領域番号
     */
    public void setZennendoOuboNo(String zennendoOuboNo) {
        this.zennendoOuboNo = zennendoOuboNo;
    }

    /**
     * 最終年度前年度の領域略称名を取得
     * @return String 最終年度前年度の領域略称名
     */
    public String getZennendoOuboRyoikiRyaku() {
        return zennendoOuboRyoikiRyaku;
    }

    /**
     * 最終年度前年度の領域略称名を設定
     * @param zennendoOuboRyoikiRyaku 最終年度前年度の領域略称名
     */
    public void setZennendoOuboRyoikiRyaku(String zennendoOuboRyoikiRyaku) {
        this.zennendoOuboRyoikiRyaku = zennendoOuboRyoikiRyaku;
    }

    /**
     * 最終年度前年度の設定期間を取得
     * @return String 最終年度前年度の設定期間
     */
    public String getZennendoOuboSettei() {
        return zennendoOuboSettei;
    }

    /**
     * 最終年度前年度の設定期間を設定
     * @param zennendoOuboSettei 最終年度前年度の設定期間
     */
    public void setZennendoOuboSettei(String zennendoOuboSettei) {
        this.zennendoOuboSettei = zennendoOuboSettei;
    }

    /**
     * 関連分野（細目番号）1を取得
     * @return String 関連分野（細目番号）1
     */
    public String getBunkasaimokuCd1() {
        return bunkasaimokuCd1;
    }

    /**
     * 関連分野（細目番号）1を設定
     * @param bunkasaimokuCd1 関連分野（細目番号）1
     */
    public void setBunkasaimokuCd1(String bunkasaimokuCd1) {
        this.bunkasaimokuCd1 = bunkasaimokuCd1;
    }

    /**
     * 関連分野（分野）1を取得
     * @return String 関連分野（分野）1
     */
    public String getBunyaName1() {
        return bunyaName1;
    }

    /**
     * 関連分野（分野）1を設定
     * @param bunyaName1 関連分野（分野）1
     */
    public void setBunyaName1(String bunyaName1) {
        this.bunyaName1 = bunyaName1;
    }

    /**
     * 関連分野（分科）1を取得
     * @return String 関連分野（分科）1
     */
    public String getBunkaName1() {
        return bunkaName1;
    }

    /**
     * 関連分野（分科）1を設定
     * @param bunkaName1 関連分野（分科）1
     */
    public void setBunkaName1(String bunkaName1) {
        this.bunkaName1 = bunkaName1;
    }

    /**
     * 関連分野（細目）1を取得
     * @return String 関連分野（細目）1
     */
    public String getSaimokuName1() {
        return saimokuName1;
    }

    /**
     * 関連分野（細目）1を設定
     * @param saimokuName1 関連分野（細目）1
     */
    public void setSaimokuName1(String saimokuName1) {
        this.saimokuName1 = saimokuName1;
    }

    /**
     * 関連分野（細目番号）2を取得
     * @return String 関連分野（細目番号）2
     */
    public String getBunkasaimokuCd2() {
        return bunkasaimokuCd2;
    }

    /**
     * 関連分野（細目番号）2を設定
     * @param bunkasaimokuCd2 関連分野（細目番号）2
     */
    public void setBunkasaimokuCd2(String bunkasaimokuCd2) {
        this.bunkasaimokuCd2 = bunkasaimokuCd2;
    }

    /**
     * 関連分野（分野）2を取得
     * @return String 関連分野（分野）2
     */
    public String getBunyaName2() {
        return bunyaName2;
    }

    /**
     * 関連分野（分野）2を設定
     * @param bunyaName2 関連分野（分野）2
     */
    public void setBunyaName2(String bunyaName2) {
        this.bunyaName2 = bunyaName2;
    }

    /**
     * 関連分野（分科）2を取得
     * @return String 関連分野（分科）2
     */
    public String getBunkaName2() {
        return bunkaName2;
    }

    /**
     * 関連分野（分科）2を設定
     * @param bunkaName2 関連分野（分科）2
     */
    public void setBunkaName2(String bunkaName2) {
        this.bunkaName2 = bunkaName2;
    }

    /**
     * 関連分野（細目）2を取得
     * @return String 関連分野（細目）2
     */
    public String getSaimokuName2() {
        return saimokuName2;
    }

    /**
     * 関連分野（細目）2を設定
     * @param saimokuName2 関連分野（細目）2
     */
    public void setSaimokuName2(String saimokuName2) {
        this.saimokuName2 = saimokuName2;
    }

    /**
     * 関連分野15分類（番号）を取得
     * @return String 関連分野15分類（番号）
     */
    public String getKanrenbunyaBunruiNo() {
        return kanrenbunyaBunruiNo;
    }

    /**
     * 関連分野15分類（番号）を設定
     * @param kanrenbunyaBunruiNo 関連分野15分類（番号）
     */
    public void setKanrenbunyaBunruiNo(String kanrenbunyaBunruiNo) {
        this.kanrenbunyaBunruiNo = kanrenbunyaBunruiNo;
    }

    /**
     * 関連分野15分類（分類名）を取得
     * @return String 関連分野15分類（分類名）
     */
    public String getKanrenbunyaBunruiName() {
        return kanrenbunyaBunruiName;
    }

    /**
     * 関連分野15分類（分類名）を設定
     * @param kanrenbunyaBunruiName 関連分野15分類（分類名）
     */
    public void setKanrenbunyaBunruiName(String kanrenbunyaBunruiName) {
        this.kanrenbunyaBunruiName = kanrenbunyaBunruiName;
    }

    /**
     * 研究の必要性1を取得
     * @return String 研究の必要性1
     */
    public String getKenkyuHitsuyousei1() {
        return kenkyuHitsuyousei1;
    }

    /**
     * 研究の必要性1を設定
     * @param kenkyuHitsuyousei1 研究の必要性1
     */
    public void setKenkyuHitsuyousei1(String kenkyuHitsuyousei1) {
        this.kenkyuHitsuyousei1 = kenkyuHitsuyousei1;
    }

    /**
     * 研究の必要性2を取得
     * @return String 研究の必要性2
     */
    public String getKenkyuHitsuyousei2() {
        return kenkyuHitsuyousei2;
    }

    /**
     * 研究の必要性2を設定
     * @param kenkyuHitsuyousei2 研究の必要性2
     */
    public void setKenkyuHitsuyousei2(String kenkyuHitsuyousei2) {
        this.kenkyuHitsuyousei2 = kenkyuHitsuyousei2;
    }

    /**
     * 研究の必要性3を取得
     * @return String 研究の必要性3
     */
    public String getKenkyuHitsuyousei3() {
        return kenkyuHitsuyousei3;
    }

    /**
     * 研究の必要性3を設定
     * @param kenkyuHitsuyousei3 研究の必要性3
     */
    public void setKenkyuHitsuyousei3(String kenkyuHitsuyousei3) {
        this.kenkyuHitsuyousei3 = kenkyuHitsuyousei3;
    }

    /**
     * 研究の必要性4を取得
     * @return String 研究の必要性4
     */
    public String getKenkyuHitsuyousei4() {
        return kenkyuHitsuyousei4;
    }

    /**
     * 研究の必要性4を設定
     * @param kenkyuHitsuyousei4 研究の必要性4
     */
    public void setKenkyuHitsuyousei4(String kenkyuHitsuyousei4) {
        this.kenkyuHitsuyousei4 = kenkyuHitsuyousei4;
    }

    /**
     * 研究の必要性5を取得
     * @return String 研究の必要性5
     */
    public String getKenkyuHitsuyousei5() {
        return kenkyuHitsuyousei5;
    }

    /**
     * 研究の必要性5を設定
     * @param kenkyuHitsuyousei5 研究の必要性5
     */
    public void setKenkyuHitsuyousei5(String kenkyuHitsuyousei5) {
        this.kenkyuHitsuyousei5 = kenkyuHitsuyousei5;
    }

// 2006/08/25 dyh delete start 原因：仕様変更
//    /**
//     * 研究経費（1年目)-小計を取得
//     * @return Returns 研究経費（1年目)-小計
//     */
//    public String getKenkyuSyokei1() {
//        return kenkyuSyokei1;
//    }
//
//    /**
//     * 研究経費（1年目)-小計を設定
//     * @param kenkyuSyokei1 研究経費（1年目)-小計
//     */
//    public void setKenkyuSyokei1(String kenkyuSyokei1) {
//        this.kenkyuSyokei1 = kenkyuSyokei1;
//    }
//
//    /**
//     * 研究経費（1年目)-内訳を取得
//     * @return String 研究経費（1年目)-内訳
//     */
//    public String getKenkyuUtiwake1() {
//        return kenkyuUtiwake1;
//    }
//
//    /**
//     * 研究経費（1年目)-内訳を設定
//     * @param kenkyuUtiwake1 研究経費（1年目)-内訳
//     */
//    public void setKenkyuUtiwake1(String kenkyuUtiwake1) {
//        this.kenkyuUtiwake1 = kenkyuUtiwake1;
//    }
// 2006/08/25 dyh delete end

    /**
     * 研究経費（2年目)-小計を取得
     * @return String 研究経費（2年目)-小計
     */
    public String getKenkyuSyokei2() {
        return kenkyuSyokei2;
    }

    /**
     * 研究経費（2年目)-小計を設定
     * @param kenkyuSyokei2 研究経費（2年目)-小計
     */
    public void setKenkyuSyokei2(String kenkyuSyokei2) {
        this.kenkyuSyokei2 = kenkyuSyokei2;
    }

    /**
     * 研究経費（2年目)-内訳を取得
     * @return String 研究経費（2年目)-内訳
     */
    public String getKenkyuUtiwake2() {
        return kenkyuUtiwake2;
    }

    /**
     * 研究経費（2年目)-内訳を設定
     * @param kenkyuUtiwake2 研究経費（2年目)-内訳
     */
    public void setKenkyuUtiwake2(String kenkyuUtiwake2) {
        this.kenkyuUtiwake2 = kenkyuUtiwake2;
    }

    /**
     * 研究経費（3年目)-小計を取得
     * @return String 研究経費（3年目)-小計
     */
    public String getKenkyuSyokei3() {
        return kenkyuSyokei3;
    }

    /**
     * 研究経費（3年目)-小計を設定
     * @param kenkyuSyokei3 研究経費（3年目)-小計
     */
    public void setKenkyuSyokei3(String kenkyuSyokei3) {
        this.kenkyuSyokei3 = kenkyuSyokei3;
    }

    /**
     * 研究経費（3年目)-内訳を取得
     * @return String 研究経費（3年目)-内訳
     */
    public String getKenkyuUtiwake3() {
        return kenkyuUtiwake3;
    }

    /**
     * 研究経費（3年目)-内訳を設定
     * @param kenkyuUtiwake3 研究経費（3年目)-内訳
     */
    public void setKenkyuUtiwake3(String kenkyuUtiwake3) {
        this.kenkyuUtiwake3 = kenkyuUtiwake3;
    }

    /**
     * 研究経費（4年目)-小計を取得
     * @return String 研究経費（4年目)-小計
     */
    public String getKenkyuSyokei4() {
        return kenkyuSyokei4;
    }

    /**
     * 研究経費（4年目)-小計を設定
     * @param kenkyuSyokei4 研究経費（4年目)-小計
     */
    public void setKenkyuSyokei4(String kenkyuSyokei4) {
        this.kenkyuSyokei4 = kenkyuSyokei4;
    }

    /**
     * 研究経費（4年目)-内訳を取得
     * @return String 研究経費（4年目)-内訳
     */
    public String getKenkyuUtiwake4() {
        return kenkyuUtiwake4;
    }

    /**
     * 研究経費（4年目)-内訳を設定
     * @param kenkyuUtiwake4 研究経費（4年目)-内訳
     */
    public void setKenkyuUtiwake4(String kenkyuUtiwake4) {
        this.kenkyuUtiwake4 = kenkyuUtiwake4;
    }

    /**
     * 研究経費（5年目)-小計を取得
     * @return String 研究経費（5年目)-小計
     */
    public String getKenkyuSyokei5() {
        return kenkyuSyokei5;
    }

    /**
     * 研究経費（5年目)-小計を設定
     * @param kenkyuSyokei5 研究経費（5年目)-小計
     */
    public void setKenkyuSyokei5(String kenkyuSyokei5) {
        this.kenkyuSyokei5 = kenkyuSyokei5;
    }

    /**
     * 研究経費（5年目)-内訳を取得
     * @return String 研究経費（5年目)-内訳
     */
    public String getKenkyuUtiwake5() {
        return kenkyuUtiwake5;
    }

    /**
     * 研究経費（5年目)-内訳を設定
     * @param kenkyuUtiwake5 研究経費（5年目)-内訳
     */
    public void setKenkyuUtiwake5(String kenkyuUtiwake5) {
        this.kenkyuUtiwake5 = kenkyuUtiwake5;
    }

    /**
     * 研究経費（6年目)-小計を取得
     * @return String 研究経費（6年目)-小計
     */
    public String getKenkyuSyokei6() {
        return kenkyuSyokei6;
    }

    /**
     * 研究経費（6年目)-小計を設定
     * @param kenkyuSyokei6 研究経費（6年目)-小計
     */
    public void setKenkyuSyokei6(String kenkyuSyokei6) {
        this.kenkyuSyokei6 = kenkyuSyokei6;
    }

    /**
     * 研究経費（6年目)-内訳を取得
     * @return String 研究経費（6年目)-内訳
     */
    public String getKenkyuUtiwake6() {
        return kenkyuUtiwake6;
    }

    /**
     * 研究経費（6年目)-内訳を設定
     * @param kenkyuUtiwake6 研究経費（6年目)-内訳
     */
    public void setKenkyuUtiwake6(String kenkyuUtiwake6) {
        this.kenkyuUtiwake6 = kenkyuUtiwake6;
    }

    /**
     * 領域代表者（郵便番号）を取得
     * @return String 領域代表者（郵便番号）
     */
    public String getDaihyouZip() {
        return daihyouZip;
    }

    /**
     * 領域代表者（郵便番号）を設定
     * @param daihyouZip 領域代表者（郵便番号）
     */
    public void setDaihyouZip(String daihyouZip) {
        this.daihyouZip = daihyouZip;
    }

    /**
     * 領域代表者（住所）を取得
     * @return String 領域代表者（住所）
     */
    public String getDaihyouAddress() {
        return daihyouAddress;
    }

    /**
     * 領域代表者（住所）を設定
     * @param daihyouAddress 領域代表者（住所）
     */
    public void setDaihyouAddress(String daihyouAddress) {
        this.daihyouAddress = daihyouAddress;
    }

    /**
     * 領域代表者（電話）を取得
     * @return String 領域代表者（電話）
     */
    public String getDaihyouTel() {
        return daihyouTel;
    }

    /**
     * 領域代表者（電話）を設定
     * @param daihyouTel 領域代表者（電話）
     */
    public void setDaihyouTel(String daihyouTel) {
        this.daihyouTel = daihyouTel;
    }

    /**
     * 領域代表者（FAX）を取得
     * @return String 領域代表者（FAX）
     */
    public String getDaihyouFax() {
        return daihyouFax;
    }

    /**
     * 領域代表者（FAX）を設定
     * @param daihyouFax 領域代表者（FAX）
     */
    public void setDaihyouFax(String daihyouFax) {
        this.daihyouFax = daihyouFax;
    }

    /**
     * 領域代表者（メールアドレス）を取得
     * @return String 領域代表者（メールアドレス）
     */
    public String getDaihyouEmail() {
        return daihyouEmail;
    }

    /**
     * 領域代表者（メールアドレス）を設定
     * @param daihyouEmail 領域代表者（メールアドレス）
     */
    public void setDaihyouEmail(String daihyouEmail) {
        this.daihyouEmail = daihyouEmail;
    }

    /**
     * 事務担当者氏名（漢字等-姓）を取得
     * @return String 事務担当者氏名（漢字等-姓）
     */
    public String getJimutantoNameKanjiSei() {
        return jimutantoNameKanjiSei;
    }

    /**
     * 事務担当者氏名（漢字等-姓）を設定
     * @param jimutantoNameKanjiSei 事務担当者氏名（漢字等-姓）
     */
    public void setJimutantoNameKanjiSei(String jimutantoNameKanjiSei) {
        this.jimutantoNameKanjiSei = jimutantoNameKanjiSei;
    }

    /**
     * 事務担当者氏名（漢字等-名）を取得
     * @return String 事務担当者氏名（漢字等-名）
     */
    public String getJimutantoNameKanjiMei() {
        return jimutantoNameKanjiMei;
    }

    /**
     * 事務担当者氏名（漢字等-名）を設定
     * @param jimutantoNameKanjiMei 事務担当者氏名（漢字等-名）
     */
    public void setJimutantoNameKanjiMei(String jimutantoNameKanjiMei) {
        this.jimutantoNameKanjiMei = jimutantoNameKanjiMei;
    }

    /**
     * 事務担当者氏名（フリガナ-姓）を取得
     * @return String 事務担当者氏名（フリガナ-姓）
     */
    public String getJimutantoNameKanaSei() {
        return jimutantoNameKanaSei;
    }

    /**
     * 事務担当者氏名（フリガナ-姓）を設定
     * @param jimutantoNameKanaSei 事務担当者氏名（フリガナ-姓）
     */
    public void setJimutantoNameKanaSei(String jimutantoNameKanaSei) {
        this.jimutantoNameKanaSei = jimutantoNameKanaSei;
    }

    /**
     * 事務担当者氏名（フリガナ-名）を取得
     * @return String 事務担当者氏名（フリガナ-名）
     */
    public String getJimutantoNameKanaMei() {
        return jimutantoNameKanaMei;
    }

    /**
     * 事務担当者氏名（フリガナ-名）を設定
     * @param jimutantoNameKanaMei 事務担当者氏名（フリガナ-名）
     */
    public void setJimutantoNameKanaMei(String jimutantoNameKanaMei) {
        this.jimutantoNameKanaMei = jimutantoNameKanaMei;
    }

    /**
     * 事務担当者研究機関番号を取得
     * @return String 事務担当者研究機関番号
     */
    public String getJimutantoShozokuCd() {
        return jimutantoShozokuCd;
    }

    /**
     * 事務担当者研究機関番号を設定
     * @param jimutantoShozokuCd 事務担当者研究機関番号
     */
    public void setJimutantoShozokuCd(String jimutantoShozokuCd) {
        this.jimutantoShozokuCd = jimutantoShozokuCd;
    }

    /**
     * 事務担当者研究機関名を取得
     * @return String 事務担当者研究機関名
     */
    public String getJimutantoShozokuName() {
        return jimutantoShozokuName;
    }

    /**
     * 事務担当者研究機関名を設定
     * @param jimutantoShozokuName 事務担当者研究機関名
     */
    public void setJimutantoShozokuName(String jimutantoShozokuName) {
        this.jimutantoShozokuName = jimutantoShozokuName;
    }

    /**
     * 事務担当者部局番号を取得
     * @return String 事務担当者部局番号
     */
    public String getJimutantoBukyokuCd() {
        return jimutantoBukyokuCd;
    }

    /**
     * 事務担当者部局番号を設定
     * @param jimutantoBukyokuCd 事務担当者部局番号
     */
    public void setJimutantoBukyokuCd(String jimutantoBukyokuCd) {
        this.jimutantoBukyokuCd = jimutantoBukyokuCd;
    }

    /**
     * 事務担当者部局名を取得
     * @return String 事務担当者部局名
     */
    public String getJimutantoBukyokuName() {
        return jimutantoBukyokuName;
    }

    /**
     * 事務担当者部局名を設定
     * @param jimutantoBukyokuName 事務担当者部局名
     */
    public void setJimutantoBukyokuName(String jimutantoBukyokuName) {
        this.jimutantoBukyokuName = jimutantoBukyokuName;
    }

    /**
     * 事務担当者職名番号を取得
     * @return String 事務担当者職名番号
     */
    public String getJimutantoShokushuCd() {
        return jimutantoShokushuCd;
    }

    /**
     * 事務担当者職名番号を設定
     * @param jimutantoShokushuCd 事務担当者職名番号
     */
    public void setJimutantoShokushuCd(String jimutantoShokushuCd) {
        this.jimutantoShokushuCd = jimutantoShokushuCd;
    }

    /**
     * 事務担当者職名（和文）を取得
     * @return String 事務担当者職名（和文）
     */
    public String getJimutantoShokushuNameKanji() {
        return jimutantoShokushuNameKanji;
    }

    /**
     * 事務担当者職名（和文）を設定
     * @param jimutantoShokushuNameKanji 事務担当者職名（和文）
     */
    public void setJimutantoShokushuNameKanji(String jimutantoShokushuNameKanji) {
        this.jimutantoShokushuNameKanji = jimutantoShokushuNameKanji;
    }

    /**
     * 事務担当者（郵便番号）を取得
     * @return String 事務担当者（郵便番号）
     */
    public String getJimutantoZip() {
        return jimutantoZip;
    }

    /**
     * 事務担当者（郵便番号）を設定
     * @param jimutantoZip 事務担当者（郵便番号）
     */
    public void setJimutantoZip(String jimutantoZip) {
        this.jimutantoZip = jimutantoZip;
    }

    /**
     * 事務担当者（住所）を取得
     * @return String 事務担当者（住所）
     */
    public String getJimutantoAddress() {
        return jimutantoAddress;
    }

    /**
     * 事務担当者（住所）を設定
     * @param jimutantoAddress 事務担当者（住所）
     */
    public void setJimutantoAddress(String jimutantoAddress) {
        this.jimutantoAddress = jimutantoAddress;
    }

    /**
     * 事務担当者（電話）を取得
     * @return String 事務担当者（電話）
     */
    public String getJimutantoTel() {
        return jimutantoTel;
    }

    /**
     * 事務担当者（電話）を設定
     * @param jimutantoTel 事務担当者（電話）
     */
    public void setJimutantoTel(String jimutantoTel) {
        this.jimutantoTel = jimutantoTel;
    }

    /**
     * 事務担当者（FAX）を取得
     * @return String 事務担当者（FAX）
     */
    public String getJimutantoFax() {
        return jimutantoFax;
    }

    /**
     * 事務担当者（FAX）を設定
     * @param jimutantoFax 事務担当者（FAX）
     */
    public void setJimutantoFax(String jimutantoFax) {
        this.jimutantoFax = jimutantoFax;
    }

    /**
     * 事務担当者（メールアドレス）を取得
     * @return String 事務担当者（メールアドレス）
     */
    public String getJimutantoEmail() {
        return jimutantoEmail;
    }

    /**
     * 事務担当者（メールアドレス）を設定
     * @param jimutantoEmail 事務担当者（メールアドレス）
     */
    public void setJimutantoEmail(String jimutantoEmail) {
        this.jimutantoEmail = jimutantoEmail;
    }

    /**
     * 関連分野の研究者-氏名1を取得
     * @return String 関連分野の研究者-氏名1
     */
    public String getKanrenShimei1() {
        return kanrenShimei1;
    }

    /**
     * 関連分野の研究者-氏名1を設定
     * @param kanrenShimei1 関連分野の研究者-氏名1
     */
    public void setKanrenShimei1(String kanrenShimei1) {
        this.kanrenShimei1 = kanrenShimei1;
    }

    /**
     * 関連分野の研究者-所属研究機関1を取得
     * @return String 関連分野の研究者-所属研究機関1
     */
    public String getKanrenKikan1() {
        return kanrenKikan1;
    }

    /**
     * 関連分野の研究者-所属研究機関1を設定
     * @param kanrenKikan1 関連分野の研究者-所属研究機関1
     */
    public void setKanrenKikan1(String kanrenKikan1) {
        this.kanrenKikan1 = kanrenKikan1;
    }

    /**
     * 関連分野の研究者-部局1を取得
     * @return String 関連分野の研究者-部局1
     */
    public String getKanrenBukyoku1() {
        return kanrenBukyoku1;
    }

    /**
     * 関連分野の研究者-部局1を設定
     * @param kanrenBukyoku1 関連分野の研究者-部局1
     */
    public void setKanrenBukyoku1(String kanrenBukyoku1) {
        this.kanrenBukyoku1 = kanrenBukyoku1;
    }

    /**
     * 関連分野の研究者-職名1を取得
     * @return String 関連分野の研究者-職名1
     */
    public String getKanrenShoku1() {
        return kanrenShoku1;
    }

    /**
     * 関連分野の研究者-職名1を設定
     * @param kanrenShoku1 関連分野の研究者-職名1
     */
    public void setKanrenShoku1(String kanrenShoku1) {
        this.kanrenShoku1 = kanrenShoku1;
    }

    /**
     * 関連分野の研究者-専門分野1を取得
     * @return String 関連分野の研究者-専門分野1
     */
    public String getKanrenSenmon1() {
        return kanrenSenmon1;
    }

    /**
     * 関連分野の研究者-専門分野1を設定
     * @param kanrenSenmon1 関連分野の研究者-専門分野1
     */
    public void setKanrenSenmon1(String kanrenSenmon1) {
        this.kanrenSenmon1 = kanrenSenmon1;
    }

    /**
     * 関連分野の研究者-勤務先電話番号1を取得
     * @return String 関連分野の研究者-勤務先電話番号1
     */
    public String getKanrenTel1() {
        return kanrenTel1;
    }

    /**
     * 関連分野の研究者-勤務先電話番号1を設定
     * @param kanrenTel1 関連分野の研究者-勤務先電話番号1
     */
    public void setKanrenTel1(String kanrenTel1) {
        this.kanrenTel1 = kanrenTel1;
    }

    /**
     * 関連分野の研究者-自宅電話番号1を取得
     * @return String 関連分野の研究者-自宅電話番号1
     */
    public String getKanrenJitakutel1() {
        return kanrenJitakutel1;
    }

    /**
     * 関連分野の研究者-自宅電話番号1を設定
     * @param kanrenJitakutel1 関連分野の研究者-自宅電話番号1
     */
    public void setKanrenJitakutel1(String kanrenJitakutel1) {
        this.kanrenJitakutel1 = kanrenJitakutel1;
    }

    /**
     * 関連分野の研究者-氏名2を取得
     * @return String 関連分野の研究者-氏名2
     */
    public String getKanrenShimei2() {
        return kanrenShimei2;
    }

    /**
     * 関連分野の研究者-氏名2を設定
     * @param kanrenShimei2 関連分野の研究者-氏名2
     */
    public void setKanrenShimei2(String kanrenShimei2) {
        this.kanrenShimei2 = kanrenShimei2;
    }

    /**
     * 関連分野の研究者-所属研究機関2を取得
     * @return String 関連分野の研究者-所属研究機関2
     */
    public String getKanrenKikan2() {
        return kanrenKikan2;
    }

    /**
     * 関連分野の研究者-所属研究機関2を設定
     * @param kanrenKikan2 関連分野の研究者-所属研究機関2
     */
    public void setKanrenKikan2(String kanrenKikan2) {
        this.kanrenKikan2 = kanrenKikan2;
    }

    /**
     * 関連分野の研究者-部局2を取得
     * @return String 関連分野の研究者-部局2
     */
    public String getKanrenBukyoku2() {
        return kanrenBukyoku2;
    }

    /**
     * 関連分野の研究者-部局2を設定
     * @param kanrenBukyoku2 関連分野の研究者-部局2
     */
    public void setKanrenBukyoku2(String kanrenBukyoku2) {
        this.kanrenBukyoku2 = kanrenBukyoku2;
    }

    /**
     * 関連分野の研究者-職名2を取得
     * @return String 関連分野の研究者-職名2
     */
    public String getKanrenShoku2() {
        return kanrenShoku2;
    }

    /**
     * 関連分野の研究者-職名2を設定
     * @param kanrenShoku2 関連分野の研究者-職名2
     */
    public void setKanrenShoku2(String kanrenShoku2) {
        this.kanrenShoku2 = kanrenShoku2;
    }

    /**
     * 関連分野の研究者-専門分野2を取得
     * @return String 関連分野の研究者-専門分野2
     */
    public String getKanrenSenmon2() {
        return kanrenSenmon2;
    }

    /**
     * 関連分野の研究者-専門分野2を設定
     * @param kanrenSenmon2 関連分野の研究者-専門分野2
     */
    public void setKanrenSenmon2(String kanrenSenmon2) {
        this.kanrenSenmon2 = kanrenSenmon2;
    }

    /**
     * 関連分野の研究者-勤務先電話番号2を取得
     * @return String 関連分野の研究者-勤務先電話番号2
     */
    public String getKanrenTel2() {
        return kanrenTel2;
    }

    /**
     * 関連分野の研究者-勤務先電話番号2を設定
     * @param kanrenTel2 関連分野の研究者-勤務先電話番号2
     */
    public void setKanrenTel2(String kanrenTel2) {
        this.kanrenTel2 = kanrenTel2;
    }

    /**
     * 関連分野の研究者-自宅電話番号2を取得
     * @return String 関連分野の研究者-自宅電話番号2
     */
    public String getKanrenJitakutel2() {
        return kanrenJitakutel2;
    }

    /**
     * 関連分野の研究者-自宅電話番号2を設定
     * @param kanrenJitakutel2 関連分野の研究者-自宅電話番号2
     */
    public void setKanrenJitakutel2(String kanrenJitakutel2) {
        this.kanrenJitakutel2 = kanrenJitakutel2;
    }

    /**
     * 関連分野の研究者-氏名3を取得
     * @return String 関連分野の研究者-氏名3
     */
    public String getKanrenShimei3() {
        return kanrenShimei3;
    }

    /**
     * 関連分野の研究者-氏名3を設定
     * @param kanrenShimei3 関連分野の研究者-氏名3
     */
    public void setKanrenShimei3(String kanrenShimei3) {
        this.kanrenShimei3 = kanrenShimei3;
    }

    /**
     * 関連分野の研究者-所属研究機関3を取得
     * @return String 関連分野の研究者-所属研究機関3
     */
    public String getKanrenKikan3() {
        return kanrenKikan3;
    }

    /**
     * 関連分野の研究者-所属研究機関3を設定
     * @param kanrenKikan3 関連分野の研究者-所属研究機関3
     */
    public void setKanrenKikan3(String kanrenKikan3) {
        this.kanrenKikan3 = kanrenKikan3;
    }

    /**
     * 関連分野の研究者-部局3を取得
     * @return String 関連分野の研究者-部局3
     */
    public String getKanrenBukyoku3() {
        return kanrenBukyoku3;
    }

    /**
     * 関連分野の研究者-部局3を設定
     * @param kanrenBukyoku3 関連分野の研究者-部局3
     */
    public void setKanrenBukyoku3(String kanrenBukyoku3) {
        this.kanrenBukyoku3 = kanrenBukyoku3;
    }

    /**
     * 関連分野の研究者-職名3を取得
     * @return String 関連分野の研究者-職名3
     */
    public String getKanrenShoku3() {
        return kanrenShoku3;
    }

    /**
     * 関連分野の研究者-職名3を設定
     * @param kanrenShoku3 関連分野の研究者-職名3
     */
    public void setKanrenShoku3(String kanrenShoku3) {
        this.kanrenShoku3 = kanrenShoku3;
    }

    /**
     * 関連分野の研究者-専門分野3を取得
     * @return String 関連分野の研究者-専門分野3
     */
    public String getKanrenSenmon3() {
        return kanrenSenmon3;
    }

    /**
     * 関連分野の研究者-専門分野3を設定
     * @param kanrenSenmon3 関連分野の研究者-専門分野3
     */
    public void setKanrenSenmon3(String kanrenSenmon3) {
        this.kanrenSenmon3 = kanrenSenmon3;
    }

    /**
     * 関連分野の研究者-勤務先電話番号3を取得
     * @return String 関連分野の研究者-勤務先電話番号3
     */
    public String getKanrenTel3() {
        return kanrenTel3;
    }

    /**
     * 関連分野の研究者-勤務先電話番号3を設定
     * @param kanrenTel3 関連分野の研究者-勤務先電話番号3
     */
    public void setKanrenTel3(String kanrenTel3) {
        this.kanrenTel3 = kanrenTel3;
    }

    /**
     * 関連分野の研究者-自宅電話番号3を取得
     * @return String 関連分野の研究者-自宅電話番号3
     */
    public String getKanrenJitakutel3() {
        return kanrenJitakutel3;
    }

    /**
     * 関連分野の研究者-自宅電話番号3を設定
     * @param kanrenJitakutel3 関連分野の研究者-自宅電話番号3
     */
    public void setKanrenJitakutel3(String kanrenJitakutel3) {
        this.kanrenJitakutel3 = kanrenJitakutel3;
    }

    /**
     * 領域計画書概要PDFの格納パスを取得
     * @return String 領域計画書概要PDFの格納パス
     */
    public String getPdfPath() {
        return pdfPath;
    }

    /**
     * 領域計画書概要PDFの格納パスを設定
     * @param pdfPath 領域計画書概要PDFの格納パス
     */
    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    /**
     * 表紙PDF格納パスを取得
     * @return String 表紙PDF格納パス
     */
    public String getHyoshiPdfPath() {
        return hyoshiPdfPath;
    }

    /**
     * 表紙PDF格納パスを設定
     * @param hyoshiPdfPath 表紙PDF格納パス
     */
    public void setHyoshiPdfPath(String hyoshiPdfPath) {
        this.hyoshiPdfPath = hyoshiPdfPath;
    }

    /**
     * 受理結果を取得
     * @return String 受理結果
     */
    public String getJuriKekka() {
        return juriKekka;
    }

    /**
     * 受理結果を設定
     * @param juriKekka 受理結果
     */
    public void setJuriKekka(String juriKekka) {
        this.juriKekka = juriKekka;
    }

    /**
     * 受理結果備考を取得
     * @return String 受理結果備考
     */
    public String getJuriBiko() {
        return juriBiko;
    }

    /**
     * 受理結果備考を設定
     * @param juriBiko 受理結果備考
     */
    public void setJuriBiko(String juriBiko) {
        this.juriBiko = juriBiko;
    }

    /**
     * 領域計画書（概要）申請状況IDを取得
     * @return String 領域計画書（概要）申請状況ID
     */
    public String getRyoikiJokyoId() {
        return ryoikiJokyoId;
    }

    /**
     * 領域計画書（概要）申請状況IDを設定
     * @param ryoikiJokyoId 領域計画書（概要）申請状況ID
     */
    public void setRyoikiJokyoId(String ryoikiJokyoId) {
        this.ryoikiJokyoId = ryoikiJokyoId;
    }

    /**
     * 版を取得
     * @return String 版
     */
    public String getEdition() {
        return edition;
    }

    /**
     * 版を設定
     * @param edition 版
     */
    public void setEdition(String edition) {
        this.edition = edition;
    }

    /**
     * 確定フラグを取得
     * @return String 確定フラグ
     */
    public String getRyoikikeikakushoKakuteiFlg() {
        return ryoikikeikakushoKakuteiFlg;
    }

    /**
     * 確定フラグを設定
     * @param ryoikikeikakushoKakuteiFlg 確定フラグ
     */
    public void setRyoikikeikakushoKakuteiFlg(String ryoikikeikakushoKakuteiFlg) {
        this.ryoikikeikakushoKakuteiFlg = ryoikikeikakushoKakuteiFlg;
    }

    /**
     * 解除フラグを取得
     * @return String 解除フラグ
     */
    public String getCancelFlg() {
        return cancelFlg;
    }

    /**
     * 解除フラグを設定
     * @param cancelFlg 解除フラグ
     */
    public void setCancelFlg(String cancelFlg) {
        this.cancelFlg = cancelFlg;
    }

    /**
     * 削除フラグを取得
     * @return String 削除フラグ
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * 削除フラグを設定
     * @param delFlg 削除フラグ
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * 事業コードを取得
     * @return String 事業コード
     */
    public String getJigyoCd() {
        return jigyoCd;
    }

    /**
     * 事業コードを設定
     * @param jigyoCd 事業コード
     */
    public void setJigyoCd(String jigyoCd) {
        this.jigyoCd = jigyoCd;
    }

    /**
     * 申請状況配列を取得
     * @return String[] 申請状況配列
     */
    public String[] getJokyoIds() {
        return jokyoIds;
    }

    /**
     * 申請状況配列を設定
     * @param jokyoIds 申請状況配列
     */
    public void setJokyoIds(String[] jokyoIds) {
        this.jokyoIds = jokyoIds;
    }

    /**
     * 領域計画書（概要）申請状況ID配列を取得
     * @return String[] 領域計画書（概要）申請状況ID配列
     */
    public String[] getRyoikiJokyoIds() {
        return ryoikiJokyoIds;
    }

    /**
     * 領域計画書（概要）申請状況ID配列を設定
     * @param ryoikiJokyoIds 領域計画書（概要）申請状況ID配列
     */
    public void setRyoikiJokyoIds(String[] ryoikiJokyoIds) {
        this.ryoikiJokyoIds = ryoikiJokyoIds;
    }

    /**
     * 応募件数を取得
     * @return Returns the count.
     */
    public String getCount() {
        return count;
    }

    /**
     * 応募件数を設定
     * @param count The count to set.
     */
    public void setCount(String count) {
        this.count = count;
    }

    /**
     * 事業年度（西暦下２ケタ））を取得
     * @return String 事業年度（西暦下２ケタ
     */
    public String getNendoSeireki() {
        return nendoSeireki;
    }

    /**
     * 事業年度（西暦下２ケタ）を設定
     * @param nendoSeireki 事業年度（西暦下２ケタ）
     */
    public void setNendoSeireki(String nendoSeireki) {
        this.nendoSeireki = nendoSeireki;
    }

    /**
     * 研究経費（合計)を取得.
     * @return String 研究経費（合計)
     */
    public String getKenkyuTotal() {
        return kenkyuTotal;
    }

    /**
     * 研究経費（合計)を設定.
     * @param kenkyuTotal 研究経費（合計)
     */
    public void setKenkyuTotal(String kenkyuTotal) {
        this.kenkyuTotal = kenkyuTotal;
    }

    /**
     * 領域計画書（概要）応募状況を取得
     * @return String 領域計画書（概要）応募状況
     */
    public String getRyoikiJokyoName() {
        return ryoikiJokyoName;
    }

    /**
     * 領域計画書（概要）応募状況を設定
     * @param ryoikiJokyoName 領域計画書（概要）応募状況
     */
    public void setRyoikiJokyoName(String ryoikiJokyoName) {
        this.ryoikiJokyoName = ryoikiJokyoName;
    }

    /**
     * 研究経費表を取得
     * @return List 研究経費表
     */
    public List getKenkyuKeihiList() {
        return kenkyuKeihiList;
    }

    /**
     * 研究経費表を設定
     * @param kenkyuKeihiList 研究経費表
     */
    public void setKenkyuKeihiList(List kenkyuKeihiList) {
        this.kenkyuKeihiList = kenkyuKeihiList;
    }

    /**
     * 研究組織表を取得
     * @return List 研究組織表
     */
    public List getKenkyuSosikiList() {
        return KenkyuSosikiList;
    }

    /**
     * 研究組織表を設定
     * @param kenkyuSosikiList 研究組織表
     */
    public void setKenkyuSosikiList(List kenkyuSosikiList) {
        KenkyuSosikiList = kenkyuSosikiList;
    }

    /**
     * 添付ファイル情報を取得
     * @return TenpuFileInfo[] 添付ファイル情報
     */
    public TenpuFileInfo[] getTenpuFileInfos() {
        return tenpuFileInfos;
    }

    /**
     * 添付ファイル情報を設定
     * @param tenpuFileInfos 添付ファイル情報
     */
    public void setTenpuFileInfos(TenpuFileInfo[] tenpuFileInfos) {
        this.tenpuFileInfos = tenpuFileInfos;
    }

    /**
     * 添付ファールリストを選択するの値を取得
     * @return String 添付ファールリストを選択するの値
     */
    public String getTenpuFileFlg() {
        return tenpuFileFlg;
    }

    /**
     * 添付ファールリストを選択するの値を設定
     * @param tenpuFileFlg 添付ファールリストを選択するの値
     */
    public void setTenpuFileFlg(String tenpuFileFlg) {
        this.tenpuFileFlg = tenpuFileFlg;
    }

	/**
	 * @return addTitele を戻します。
	 */
	public String getAddTitele() {
		return addTitele;
	}

	/**
	 * @param addTitele 設定する addTitele。
	 */
	public void setAddTitele(String addTitele) {
		this.addTitele = addTitele;
	}
//	ADD　START 2007-07-25 BIS 劉多良
//  入力した研究組織にエラーがある場合、「領域計画書入力」画面でエラーメッセージを表示し、「研究経費表」画面でエラーになる項目の背景色を変更する。	
	/**
	 * @return Returns the errorsMap.
	 */
	public HashMap getErrorsMap() {
		return errorsMap;
	}

	/**
	 * @param errorsMap The errorsMap to set.
	 */
	public void setErrorsMap(HashMap errorsMap) {
		this.errorsMap = errorsMap;
	}
//ADD　END 2007-07-25 BIS 劉多良

}