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

import java.util.*;

/**
 * 申請書検索情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinseiSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class ShinseiSearchInfo extends SearchInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -800367715758497474L;

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 整列順序 */
	private static final String[] ORDER_SPEC          = new String[]{" ASC", " DESC"};
	
	/** 整列順序：昇順（デフォルト） */
	public static final int   ORDER_SPEC_ASC          = 0;
	
	/** 整列順序：降順　*/
	public static final int   ORDER_SPEC_DESC         = 1;
	
	/** 整列項目：システム受付番号　*/
	public static final String ORDER_BY_SYSTEM_NO      = "SYSTEM_NO";
	
	/** 整列項目：申請番号 */
	public static final String ORDER_BY_UKETUKE_NO     = "UKETUKE_NO";

	/** 整列項目：年度 */
	public static final String ORDER_BY_NENDO          = "NENDO";
	
	/** 整列項目：回数 */
	public static final String ORDER_BY_KAISU          = "KAISU";
	
	/** 整列項目：事業ID */
	public static final String ORDER_BY_JIGYO_ID       = "JIGYO_ID";
	
	/** 整列項目：申請者ID */
	public static final String ORDER_BY_SHINSEISHA_ID  = "SHINSEISHA_ID";
	
	/** 整列項目：研究者番号 */
	public static final String ORDER_BY_KENKYU_NO      = "KENKYU_NO";
	
	/** 整列項目：申請者名（漢字－姓） */
	public static final String ORDER_BY_SHINSEISHA_NAME_KANJI_SEI  = "NAME_KANJI_SEI";
	
	/** 整列項目：申請者名（漢字－名） */
	public static final String ORDER_BY_SHINSEISHA_NAME_KANJI_MEI  = "NAME_KANJI_MEI";
	
	/** 整列項目：申請者名（カナ－姓） */
	public static final String ORDER_BY_SHINSEISHA_NAME_KANA_SEI   = "NAME_KANA_SEI";
	
	/** 整列項目：申請者名（カナ－名） */
	public static final String ORDER_BY_SHINSEISHA_NAME_KANA_MEI   = "NAME_KANA_MEI";

	/** 整列項目：所属機関CD */
	public static final String ORDER_BY_SHOZOKU_CD     = "SHOZOKU_CD";
	
	/** 整列項目：1次審査結果(ABC) */
	public static final String ORDER_BY_KEKKA1_ABC     = "KEKKA1_ABC";
	
	/** 整列項目：1次審査結果(点数) */
	public static final String ORDER_BY_KEKKA1_TEN     = "KEKKA1_TEN";
	
	/** 整列項目：2次審査結果 */
	public static final String ORDER_BY_KEKKA2         = "KEKKA2";
	
	/** 整列項目：系統の区分番号 */
	public static final String ORDER_BY_KEI_NAME_NO    = "KEI_NAME_NO";
	
	/** 整列項目：推薦の観点番号 */
	public static final String ORDER_BY_KANTEN_NO      = "KANTEN_NO";

	/** 整列項目：整理番号 */
	public static final String ORDER_BY_SEIRI_NO      = "JURI_SEIRI_NO";
	
	/** 整列項目：シーケンス（研究組織表用） */
	public static final String ORDER_BY_SEQ_NO      	= "SEQ_NO";
	
	/** 削除フラグ「0」 */
	public static final String NOT_DELETE_FLG  = "0";	

	/** 削除フラグ「1」 */
	public static final String DELETE_FLG      = "1";	
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** テーブル名接頭辞（デフォルト空文字） */
	private String tablePrefix;
	
	/** 整列設定 */
	private String order;
	
	/** システム受付番号 */
	private String systemNo;
	
	/** 申請番号 */
	private String uketukeNo;
	
	/** 事業ID */
	private String jigyoId;
	
	/** 事業コード */
	private String jigyoCd;
	
	/** 事業コード（複数）*/
	private Collection tantoJigyoCd;
	
	/** 事業名 */
	private String jigyoName;
	
	/** 年度 */
	private String nendo;
	
	/** 回数 */
	private String kaisu;
	
	/** 申請者ID */
	private String shinseishaId;
	
	/** 申請者名（漢字：姓）　*/
	private String nameKanjiSei;
	
	/** 申請者名（漢字：名） */
	private String nameKanjiMei;
	
	/** 申請者名（カナ：姓）　*/
	private String nameKanaSei;
	
	/** 申請者名（カナ：名） */
	private String nameKanaMei;
	
	/** 申請者名（ローマ字：姓） */
	private String nameRoSei;
	
	/** 申請者名（ローマ字：名） */
	private String nameRoMei;
	
	/** 所属機関コード */
	private String shozokuCd;
    
//  2006/06/02 jzx　add start
    /** 所属研究機関名(名称) */
    private String shozokNm;
//  2006/06/02 jzx　add end
	
	/** 研究者番号 */
	private String kenkyuNo;
	
	/** 事業区分 */
	private Collection jigyoKubun;
	
	/** 系統の区分番号 */
	private String keiNameNo;

	/** 系統の区分または系統の区分（略称） */
	private String keiName;
	
	/** 関連分野の研究者氏名1～3のいずれか */
	private String kanrenShimei;
	
	/** 推薦の観点番号 */
	private String kantenNo;
	
	/** 推薦の観点 */
	private String kanten;
	
	/** 推薦の観点略称 */
	private String kantenRyaku;
	
	/** 申請状況 */
	private String[] jokyoId;
	
	/** 再申請フラグ */
	private String[] saishinseiFlg;
	
	/** 細目番号1または細目番号2 */
	private String bunkasaimokuCd;
	
	/** 2次審査結果 */
	private int[] kekka2;
	
	/** 作成日（From）yyyy/MM/dd型 */
	private String sakuseiDateFrom;
	
	/** 作成日（To）yyyy/MM/dd型 */
	private String sakuseiDateTo;
	
	/** 所属機関承認日（From）yyyy/MM/dd型 */
	private String shoninDateFrom;
	
	/** 所属機関承認日（To）yyyy/MM/dd型 */
	private String shoninDateTo;
	
	/** 組み合わせステータス状況（申請状況と再申請フラグの組み合わせ） */
	private CombinedStatusSearchInfo statusSearchInfo;

	/** 部局コード */
	private String bukyokuCd;

	/** 削除フラグ */
	private String[] delFlg = new String[]{NOT_DELETE_FLG} ;		//初期化
	
	/** 受理整理番号 */
	private String seiriNo;

	/** 新規・継続区分 */
	private String shinseiKubun;
	
	/** 分割番号 */
	private String bunkatsuNo;
	
	/** 研究計画最終年度前年度の応募 */
	private String shinseiFlgNo;

	/** 計画研究・公募研究・終了研究領域区分 */
	private String kenkyuKubun;
	
	/** 調整班 */
	private String chouseiFlg;
	
	/** 領域番号 */
	private String ryouikiNo;

	/** 研究項目番号 */
	private String ryouikiKoumokuNo;

	/** 分担金の有無 */
	private String buntankinFlg;
	
	/** 開示希望の有無 */
	private String kaijiKiboFlg;
	
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinseiSearchInfo() {
		super();
	}


	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	/**
	 * 整列条件のテーブル接頭辞をセットする。
	 * 接頭辞のピリオドは必要無い。
	 * 例）引数に「A」をセットした場合。
	 *     SHINSEIDATAKANRI -> A.SHINSEIDATAKANRI となる。
	 * 
	 * 注意：setOrder()を呼び出す前に実行すること。
	 * @param prefix
	 */
	public void setTablePrefix(String prefix){
		tablePrefix = prefix + ".";
	}
	
	/**
	 * 指定されたカラム名を昇順にセットする。
	 * 既に整列条件が指定されていた場合は、その次のキーとして整列条件に追加する。
	 * カラム名は本クラスのパブリックフィールド[ORDER_BY_*]を参照すること。
	 * @param columnName
	 */
	public void setOrder(String columnName){
		setOrder(columnName, ORDER_SPEC_ASC);
	}
	
	/**
	 * 指定されたカラム名を指定順の整列条件にセットする。
	 * 既に整列条件が指定されていた場合は、その次のキーとして整列条件に追加する。
	 * カラム名は本クラスのパブリックフィールド[ORDER_BY_*]を参照すること。
	 * 昇順、降順のセット値は本クラスのパブリックフィールド[ORDER_SPEC_*]を参照すること。
	 * @param columnName
	 */
	public void setOrder(String columnName, int orderType){
		
		//テーブル接頭辞がセットされている場合は追加
		if(tablePrefix != null && tablePrefix.length() != 0){
			columnName = tablePrefix + columnName;
		}
		
		//整列条件文字列
		String orderBy = columnName + ORDER_SPEC[orderType];
		
		//最初のパラメータの場合
		if(order == null || order.length() == 0){
			order = orderBy;
		//２番目以降
		}else{
			order = order + ", " + orderBy;
		}
	}
	
	/**
	 * 設定されている整列順をクリアする。
	 */
	public void clrOrder(){
		this.order = null;
	}
	
	
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getBunkasaimokuCd() {
		return bunkasaimokuCd;
	}

	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
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
	public String[] getJokyoId() {
		return jokyoId;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @return
	 */
	public String getKanrenShimei() {
		return kanrenShimei;
	}

	/**
	 * @return
	 */
	public String getKanten() {
		return kanten;
	}

	/**
	 * @return
	 */
	public String getKantenNo() {
		return kantenNo;
	}

	/**
	 * @return
	 */
	public String getKantenRyaku() {
		return kantenRyaku;
	}

	/**
	 * @return
	 */
	public String getKeiName() {
		return keiName;
	}

	/**
	 * @return
	 */
	public String getKeiNameNo() {
		return keiNameNo;
	}

	/**
	 * @return
	 */
	public int[] getKekka2() {
		return kekka2;
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
	public String getNameRoMei() {
		return nameRoMei;
	}

	/**
	 * @return
	 */
	public String getNameRoSei() {
		return nameRoSei;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateFrom() {
		return sakuseiDateFrom;
	}

	/**
	 * @return
	 */
	public String getSakuseiDateTo() {
		return sakuseiDateTo;
	}

	/**
	 * @return
	 */
	public String getShinseishaId() {
		return shinseishaId;
	}

	/**
	 * @return
	 */
	public String getShoninDateFrom() {
		return shoninDateFrom;
	}

	/**
	 * @return
	 */
	public String getShoninDateTo() {
		return shoninDateTo;
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
	public String getSystemNo() {
		return systemNo;
	}

	/**
	 * @return
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @param string
	 */
	public void setBunkasaimokuCd(String string) {
		bunkasaimokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
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
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param strings
	 */
	public void setJokyoId(String[] strings) {
		jokyoId = strings;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @param string
	 */
	public void setKanrenShimei(String string) {
		kanrenShimei = string;
	}

	/**
	 * @param string
	 */
	public void setKanten(String string) {
		kanten = string;
	}

	/**
	 * @param string
	 */
	public void setKantenNo(String string) {
		kantenNo = string;
	}

	/**
	 * @param string
	 */
	public void setKantenRyaku(String string) {
		kantenRyaku = string;
	}

	/**
	 * @param string
	 */
	public void setKeiName(String string) {
		keiName = string;
	}

	/**
	 * @param string
	 */
	public void setKeiNameNo(String string) {
		keiNameNo = string;
	}

	/**
	 * @param is
	 */
	public void setKekka2(int[] is) {
		kekka2 = is;
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
	public void setNameRoMei(String string) {
		nameRoMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoSei(String string) {
		nameRoSei = string;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateFrom(String string) {
		sakuseiDateFrom = string;
	}

	/**
	 * @param string
	 */
	public void setSakuseiDateTo(String string) {
		sakuseiDateTo = string;
	}

	/**
	 * @param string
	 */
	public void setShinseishaId(String string) {
		shinseishaId = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateFrom(String string) {
		shoninDateFrom = string;
	}

	/**
	 * @param string
	 */
	public void setShoninDateTo(String string) {
		shoninDateTo = string;
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
	public void setSystemNo(String string) {
		systemNo = string;
	}

	/**
	 * @param string
	 */
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @return
	 */
	public Collection getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @param collection
	 */
	public void setJigyoKubun(Collection collection) {
		jigyoKubun = collection;
	}

	/**
	 * @return
	 */
	public String[] getSaishinseiFlg() {
		return saishinseiFlg;
	}

	/**
	 * @param strings
	 */
	public void setSaishinseiFlg(String[] strings) {
		saishinseiFlg = strings;
	}

	/**
	 * @return
	 */
	public CombinedStatusSearchInfo getStatusSearchInfo() {
		return statusSearchInfo;
	}

	/**
	 * @param info
	 */
	public void setStatusSearchInfo(CombinedStatusSearchInfo info) {
		statusSearchInfo = info;
	}

	/**
	 * @return
	 */
	public Collection getTantoJigyoCd() {
		return tantoJigyoCd;
	}

	/**
	 * @param collection
	 */
	public void setTantoJigyoCd(Collection collection) {
		tantoJigyoCd = collection;
	}

	/**
	 * @return
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}

	/**
	 * @param string
	 */
	public void setBukyokuCd(String string) {
		bukyokuCd = string;
	}
	
	/**
	 * @return
	 */
	public String[] getDelFlg() {
		return delFlg;
	}

	/**
	 * @param strings
	 */
	public void setDelFlg(String[] strings) {
		delFlg = strings;
	}

	/**
	 * @return 整理番号を返す
	 */
	public String getSeiriNo(){
		return seiriNo;
	}
	
	/**
	 * @param str 整理番号をセットする
	 */
	public void setSeiriNo(String str){
		seiriNo = str;
	}


	/**
	 * @return bunkatsuNo を戻します。
	 */
	public String getBunkatsuNo() {
		return bunkatsuNo;
	}


	/**
	 * @param bunkatsuNo 設定する bunkatsuNo。
	 */
	public void setBunkatsuNo(String bunkatsuNo) {
		this.bunkatsuNo = bunkatsuNo;
	}


	/**
	 * @return buntankinFlg を戻します。
	 */
	public String getBuntankinFlg() {
		return buntankinFlg;
	}


	/**
	 * @param buntankinFlg 設定する buntankinFlg。
	 */
	public void setBuntankinFlg(String buntankinFlg) {
		this.buntankinFlg = buntankinFlg;
	}


	/**
	 * @return chouseiFlg を戻します。
	 */
	public String getChouseiFlg() {
		return chouseiFlg;
	}


	/**
	 * @param chouseiFlg 設定する chouseiFlg。
	 */
	public void setChouseiFlg(String chouseiFlg) {
		this.chouseiFlg = chouseiFlg;
	}


	/**
	 * @return kaijiKiboFlg を戻します。
	 */
	public String getKaijiKiboFlg() {
		return kaijiKiboFlg;
	}


	/**
	 * @param kaijiKiboFlg 設定する kaijiKiboFlg。
	 */
	public void setKaijiKiboFlg(String kaijiKiboFlg) {
		this.kaijiKiboFlg = kaijiKiboFlg;
	}


	/**
	 * @return kenkyuKubun を戻します。
	 */
	public String getKenkyuKubun() {
		return kenkyuKubun;
	}


	/**
	 * @param kenkyuKubun 設定する kenkyuKubun。
	 */
	public void setKenkyuKubun(String kenkyuKubun) {
		this.kenkyuKubun = kenkyuKubun;
	}


	/**
	 * @return ryouikiNo を戻します。
	 */
	public String getRyouikiNo() {
		return ryouikiNo;
	}


	/**
	 * @param ryouikiNo 設定する ryouikiNo。
	 */
	public void setRyouikiNo(String ryouikiNo) {
		this.ryouikiNo = ryouikiNo;
	}


	/**
	 * @return shinseiFlgNo を戻します。
	 */
	public String getShinseiFlgNo() {
		return shinseiFlgNo;
	}


	/**
	 * @param shinseiFlgNo 設定する shinseiFlgNo。
	 */
	public void setShinseiFlgNo(String shinseiFlgNo) {
		this.shinseiFlgNo = shinseiFlgNo;
	}


	/**
	 * @return shinseiKubun を戻します。
	 */
	public String getShinseiKubun() {
		return shinseiKubun;
	}


	/**
	 * @param shinseiKubun 設定する 。
	 */
	public void setShinseiKubun(String shinseiKubun) {
		this.shinseiKubun = shinseiKubun;
	}


	/**
	 * @return ryouikiKoumokuNo を戻します。
	 */
	public String getRyouikiKoumokuNo() {
		return ryouikiKoumokuNo;
	}


	/**
	 * @param ryouikiKoumokuNo 設定する ryouikiKoumokuNo。
	 */
	public void setRyouikiKoumokuNo(String ryouikiKoumokuNo) {
		this.ryouikiKoumokuNo = ryouikiKoumokuNo;
	}


    /**
     * @return Returns the shozokNm.
     */
    public String getShozokNm() {
        return shozokNm;
    }


    /**
     * @param shozokNm The shozokNm to set.
     */
    public void setShozokNm(String shozokNm) {
        this.shozokNm = shozokNm;
    }
	
}
