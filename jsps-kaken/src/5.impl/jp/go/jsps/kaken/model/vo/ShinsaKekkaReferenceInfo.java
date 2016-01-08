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

import java.util.Arrays;
import java.util.List;

/**
 * 1次審査結果情報（参照用）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaReferenceInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class ShinsaKekkaReferenceInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 申請番号 */
	private String    uketukeNo;
	
	/** 年度 */
	private String    nendo;
	
	/** 回数 */
	private String    kaisu;
	
	/** 事業ID */
	private String    jigyoId;
	
	/** 事業名 */
	private String    jigyoName;
	
	/** 事業コード */
	private String    jigyoCd;
	
	/** 申請者氏名（漢字等-姓） */
	private String    nameKanjiSei;
	
	/** 申請者氏名（漢字等-名） */
	private String    nameKanjiMei;
		
	/** 研究課題名(和文） */
	private String    kadaiNameKanji;

	/** 所属機関名 */
	private String    shozokuName;
	
	/** 部局名 */
	private String    bukyokuName;
	
	/** 職名 */
	private String    shokushuNameKanji;

	/** 研究番号 */
	private String    kenkyuNo;
			
	/** 1次審査結果情報 */
	private ShinsaKekkaInfo[] shinsaKekkaInfo;
	
	/** 業務担当者備考 */
	private String    shinsa1Biko;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsaKekkaReferenceInfo() {
		super();
	}
	
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------	
	
	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
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
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public String getKadaiNameKanji() {
		return kadaiNameKanji;
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
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public ShinsaKekkaInfo[] getShinsaKekkaInfo() {
		return shinsaKekkaInfo;
	}
	
	/**
	 * 
	 * @return
	 */
	public List getShinsaKekkaInfoList(){
		return Arrays.asList(shinsaKekkaInfo);
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
	public void setJigyoId(String string) {
		jigyoId = string;
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
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param string
	 */
	public void setKadaiNameKanji(String string) {
		kadaiNameKanji = string;
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
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param infos
	 */
	public void setShinsaKekkaInfo(ShinsaKekkaInfo[] infos) {
		shinsaKekkaInfo = infos;
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
	public String getShinsa1Biko() {
		return shinsa1Biko;
	}

	/**
	 * @param string
	 */
	public void setShinsa1Biko(String string) {
		shinsa1Biko = string;
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
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
	 * @return
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
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
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

}
