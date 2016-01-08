/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 評価検索情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: HyokaSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class HyokaSearchInfo extends SearchInfo{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
//	/** 事業名 */
//	private List jigyoList = new ArrayList();
//	
//	/** 年度 */
//	private String nendo;
//	
//	/** 回数 */
//	private String kaisu;
//	
//	/** 分野・系別 */
//	private String bunya;
//	
//	/** 申請者名・姓 */
//	private String nameSei;
//	
//	/** 申請者名・名 */
//	private String nameMei;
//	
//	/** 申請者名・ローマ字-姓 */
//	private String nameRoSei;
//	
//	/** 申請者名・ローマ字-名 */
//	private String nameRoMei;
//	
//	/** 申請番号 */
//	private String shinseiNo;
//	
//	/** 評価（From） */
//	private String hyokaFrom;
//	
//	/** 評価（To） */
//	private String hyokaTo;
//	
//	/** 表示方式 */
//	private String hyojiHoshiki;

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;

	/** 分野・系別 */
	private String bunya;

	/** 申請者氏名（氏） */
	private String nameKanjiSei;

	/** 申請者氏名（名） */
	private String nameKanjiMei;

	/** 申請者氏名（ローマ字-氏） */
	private String nameRoSei;

	/** 申請者氏名（ローマ字-名） */
	private String nameRoMei;

	/** 申請者番号 */
	private String uketukeNo;

	/** 表示方式 */
	private String hyojiHoshiki;

//	/** 事業コード */
//	private String jigyoCd;

	/** 系等の区分 */
	private String keiName;

	/** 申請者氏名（フリガナ） */
	private String nameKanaSei;

	/** 申請者氏名（フリガナ） */
	private String nameKanaMei;

	/** 所属機関コード */
	private String shozokuCd;

	/** 細目番号 */
	private String bunkasaimokuCd;

//2005/10/18整理番号追加	
	/** 整理番号 */
	private String seiriNo;
	
//	/** 事業名リスト */
//	private List jigyoList;

	/** 事業区分 */
	private String jigyoKubun;

	/** 表示方式（基盤用） */
	private String hyojiHoshikiKiban;

//	/** 表示方式リスト（基盤用） */
//	private List hyojiHoshikiListKiban;

	/** 評価（点）（高） */
	private String hyokaHigh;

	/** 評価（点）（低） */
	private String hyokaLow;

	/** 海外分野 */
	private String kaigaibunyaName;

	/** 事業選択値リスト */
	private List values = new ArrayList();

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public HyokaSearchInfo() {
		super();
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
	public String getBunya() {
		return bunya;
	}

	/**
	 * @return
	 */
	public String getHyojiHoshiki() {
		return hyojiHoshiki;
	}

//	/**
//	 * @return
//	 */
//	public String getJigyoCd() {
//		return jigyoCd;
//	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
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
	public String getUketukeNo() {
		return uketukeNo;
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
	public void setBunkasaimokuCd(String string) {
		bunkasaimokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setBunya(String string) {
		bunya = string;
	}

	/**
	 * @param string
	 */
	public void setHyojiHoshiki(String string) {
		hyojiHoshiki = string;
	}

//	/**
//	 * @param string
//	 */
//	public void setJigyoCd(String string) {
//		jigyoCd = string;
//	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
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
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

//	/**
//	 * @return
//	 */
//	public List getJigyoList() {
//		return jigyoList;
//	}
//
//	/**
//	 * @param list
//	 */
//	public void setJigyoList(List list) {
//		jigyoList = list;
//	}

	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @return
	 */
	public String getHyojiHoshikiKiban() {
		return hyojiHoshikiKiban;
	}

//	/**
//	 * @return
//	 */
//	public List getHyojiHoshikiListKiban() {
//		return hyojiHoshikiListKiban;
//	}

	/**
	 * @param string
	 */
	public void setHyojiHoshikiKiban(String string) {
		hyojiHoshikiKiban = string;
	}

//	/**
//	 * @param list
//	 */
//	public void setHyojiHoshikiListKiban(List list) {
//		hyojiHoshikiListKiban = list;
//	}

/**
 * @return
 */
public String getHyokaHigh() {
	return hyokaHigh;
}

	/**
	 * @return
	 */
	public String getHyokaLow() {
		return hyokaLow;
	}

/**
 * @param string
 */
public void setHyokaHigh(String string) {
	hyokaHigh = string;
}

	/**
	 * @param string
	 */
	public void setHyokaLow(String string) {
		hyokaLow = string;
	}

	/**
	 * @return
	 */
	public String getKaigaibunyaName() {
		return kaigaibunyaName;
	}

	/**
	 * @param string
	 */
	public void setKaigaibunyaName(String string) {
		kaigaibunyaName = string;
	}

// 2005/10/17整理番号追加
   /**
	* @return
	*/
   public String getSeiriNo() {
	   return seiriNo;
   }
   /**
	* @param string
	*/
   public void setSeiriNo(String string) {
	   seiriNo = string;
   }
  


	/**
	 * @return
	 */
	public List getValues() {
		return values;
	}

	/**
	 * @param list
	 */
	public void setValues(List list) {
		values = list;
	}

	/**
	 * @param string
	 */
	public void setValues(Object value) {
		if(!values.contains(value)){
			values.add(value);
		}
	}
	
	
}
