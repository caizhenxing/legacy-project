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

package jp.go.jsps.kaken.model.vo.shinsei;

import java.util.ArrayList;
import java.util.List;


/**
 * 研究代表者及び分担者（研究組織表）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KenkyuSoshikiKenkyushaInfo.java,v $"
 * Revision="$Revision: 1.4 $"
 * Date="$Date: 2007/07/20 01:31:07 $"
 */
public class KenkyuSoshikiKenkyushaInfo extends KenkyuSoshikiKenkyushaPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	//<!-- ADD　START 2007/07/20 BIS 張楠 -->
	/** 表示順 */
	private String hyojijun;
	//<!-- ADD　END 2007/07/20 BIS 張楠 -->
	/** 事業ID */
	private String jigyoID;
	
	/** 代表者分担者別 */
	private String buntanFlag;
	
	/** 研究者番号 */
	private String kenkyuNo;
	
	/** 氏名（漢字-姓） */
	private String nameKanjiSei;
	
	/** 氏名（漢字-名） */
	private String nameKanjiMei;
	
	/** 氏名（フリガナ-姓） */
	private String nameKanaSei;
	
	/** 氏名（フリガナ-名） */
	private String nameKanaMei;
	
	/** 所属機関名（コード） */
	private String shozokuCd;
	
	/** 所属機関名（和文） */
	private String shozokuName;
	
	/** 部局名（コード） */
	private String bukyokuCd;
	
	/** 部局名（和文） */
	private String bukyokuName;
	
	/** 職名コード */
	private String shokushuCd;
	
	/** 職名（和文） */
	private String shokushuNameKanji;
	
	/** 現在の専門 */
	private String senmon;
	
	/** 学位 */
	private String gakui;
	
	/** 役割分担 */
	private String buntan;
	
	/** 研究経費 */
	private String keihi;
	
	/** エフォート */
	private String effort;
	
	/** 年齢 */
	private String nenrei;
    
	//  2006/06/20 劉佳　追加 ここから--------------------------------------------
    /** 申請状況 */
    private String[] jokyoId;
    //  2006/06/20 劉佳　追加 ここまで--------------------------------------------
    
    // 2006/6/16 追加　李義華　ここから
    /** 仮領域番号 */
    private String kariryoikiNo;
    // 2006/6/16 追加　李義華　ここまで
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KenkyuSoshikiKenkyushaInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
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
	public String getBuntan() {
		return buntan;
	}

	/**
	 * @return
	 */
	public String getBuntanFlag() {
		return buntanFlag;
	}

	/**
	 * @return
	 */
	public String getEffort() {
		return effort;
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
	public String getJigyoID() {
		return jigyoID;
	}

	/**
	 * @return
	 */
	public String getKeihi() {
		return keihi;
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
	public String getSenmon() {
		return senmon;
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
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
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
	public String getShozokuName() {
		return shozokuName;
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
	public void setBuntan(String string) {
		buntan = string;
	}

	/**
	 * @param string
	 */
	public void setBuntanFlag(String string) {
		buntanFlag = string;
	}

	/**
	 * @param string
	 */
	public void setEffort(String string) {
		effort = string;
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
	public void setJigyoID(String string) {
		jigyoID = string;
	}

	/**
	 * @param string
	 */
	public void setKeihi(String string) {
		keihi = string;
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
	public void setSenmon(String string) {
		senmon = string;
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
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
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
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @return
	 */
	public String getNenrei() {
		return nenrei;
	}

	/**
	 * @param string
	 */
	public void setNenrei(String string) {
		nenrei = string;
	}

	//  2006/06/20 劉佳　追加 ここから--------------------------------------------
    /**
     * @return
     */
    public String[] getJokyoId() {
        return jokyoId;
    }

    /**
     * @param string
     */
    public void setJokyoId(String[] jokyoId) {
        this.jokyoId = jokyoId;
    }
    //  2006/06/20 劉佳　追加 ここまで--------------------------------------------
    // 2006/06/16 追加　李義華　ここから
	/**
	 * @return Returns the kariryoikiNo.
	 */
	public String getKariryoikiNo() {
		return kariryoikiNo;
	}

	/**
	 * @param kariryoikiNo The kariryoikiNo to set.
	 */
	public void setKariryoikiNo(String kariryoikiNo) {
		this.kariryoikiNo = kariryoikiNo;
	}
	// 2006/06/16 追加　李義華　ここまで
	//<!-- ADD　START 2007/07/20 BIS 張楠 -->
	/**
	 * @return Returns the hyojijun.
	 */
	public String getHyojijun() {
		return hyojijun;
	}

	/**
	 * @param hyojijun The hyojijun to set.
	 */
	public void setHyojijun(String hyojijun) {
		this.hyojijun = hyojijun;
	}
	//<!-- ADD　END 2007/07/20 BIS 張楠 -->
}
