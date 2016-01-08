/*
 * Created on 2005/03/30
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Collection;

/**
 * チェックリスト検索情報を保持するクラス
 * @author masuo_t
 */
public class CheckListSearchInfo extends SearchInfo {

	//2005/04/11 追加 ここから--------------------------------------------------
	//理由 状況IDが03,04以外の場合に対応するため
	
//	/** 状況ID：作成中 */
//	public static final String SAKUSEITYU = "01";
//	/** 状況ID：申請書未確認 */
//	public static final String MIKAKUNIN = "02";
//	/** 状況ID：所属機関受付中 */
//	public static final String SHOZOKU_UKETUKE = "03";
//	/** 状況ID：学振受付中 */
//	public  static final String GAKUSIN_UKETUKE = "04";
//	/** 状況ID：所属機関却下 */
//	public static final String SHOZOKU_KYAKKA = "05";
//	/** 状況ID：学振受理 */
//	public static final String GAKUSIN_JYURI = "06";
//	/** 状況ID：学振不受理 */
//	public static final String GAKUSIN_HUJYURI = "07";
//	/** 状況ID：審査員割り振り処理後 */
//	public static final String SHINSAIN_WARIHURI = "08";
//	/** 状況ID：割り振りチェック完了 */
//	public static final String WARIHURI_CHECK = "09";
//	/** 状況ID：一次審査中 */
//	public static final String ITIJI_SINSA ="10";
//	/** 状況ID：一次審査:判定 */
//	public static final String ITIJI_HANTEI ="11";
//	/** 状況ID：二次審査完了 */
//	public static final String NIJI_SINSA ="12";
//// 2006/06/15 dyh add start
//    /** 状況ID：領域代表者確認中 */
//    public static final String RYOUIKIDAIHYOU_KAKUNIN ="21";
//    /** 状況ID：領域代表者却下 */
//    public static final String RYOUIKIDAIHYOU_KYAKKA ="22";
//    /** 状況ID：領域代表者確定済み */
//    public static final String RYOUIKIDAIHYOU_KAKUTEIZUMI ="23";
//    /** 状況ID：領域代表者所属研究機関受付中 */
//    public static final String RYOUIKISHOZOKU_UKETUKE ="24";
//// 2006/06/15 dyh add end

	//	追加 ここまで-------------------------------------------------------------

	/** 所属CD */
	private String shozokuCd;
	
	/** 事業ID */
	private String jigyoId;
	
	/** チェックリスト版 */
	private String edition;
	
	/** 更新前状況ID */
	private String jokyoId;
	
	/** 更新用状況ID */
	private String changeJokyoId;
	
	/** 検索用状況ID */
	private String[] searchJokyoId;

	//2005/04/12 追加 ここから---------------------------------------------------
	//事業CDの追加
	
	/** 事業CD */	
	private String jigyoCd;
    
    /** 事業コード（複数）*/
    private Collection tantoJigyoCd;
	
	//追加 ここまで--------------------------------------------------------------
	
	//2005/04/14 追加 ここから---------------------------------------------------
	//回数の追加
	/** 回数 */
	private String kaisu;
	//追加 ここまで--------------------------------------------------------------
	
	//2005/04/21 追加 ここから--------------------------------------------------
	//理由 検索条件に所属機関名と受理状況が追加されたため
	
	/** 確定解除フラグ */
	private String cancellationFlag;
	
	/** 所属機関名 */
	private String shozokuName;
	
// 20050606 Start 検索条件に事業区分を追加
	/** 事業区分 */
	private String JigyoKubun;
// Horikoshi

// 受理、不受理コメント追加
	private String juriComment;
// Horikoshi

	//追加 ここまで-------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @return
	 */
	public String getEdition() {
		return edition;
	}

	/**
	 * @param string
	 */
	public void setEdition(String string) {
		edition = string;
	}

	/**
	 * @return
	 */
	public String getChangeJokyoId() {
		return changeJokyoId;
	}

	/**
	 * @return
	 */
	public String getJokyoId() {
		return jokyoId;
	}

	/**
	 * @param string
	 */
	public void setChangeJokyoId(String string) {
		changeJokyoId = string;
	}

	/**
	 * @param string
	 */
	public void setJokyoId(String string) {
		jokyoId = string;
	}

	/**
	 * @return
	 */
	public String[] getSearchJokyoId() {
		return searchJokyoId;
	}

	/**
	 * @param strings
	 */
	public void setSearchJokyoId(String[] strings) {
		searchJokyoId = strings;
	}

	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	//2005/04/21 追加 ここから--------------------------------------------------
	//理由 検索条件に所属機関名と受理状況が追加されたため

	/**
	 * @return
	 */
	public String getCancellationFlag() {
		return cancellationFlag;
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
	public void setCancellationFlag(String string) {
		cancellationFlag = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}
	//追加 ここまで-------------------------------------------------------------
	
// 20050606 Start 検索条件に事業区分を追加
	/**
	 * @return jigyoKubun を戻します。
	 */
	public String getJigyoKubun() {return JigyoKubun;}
	/**
	 * @param jigyoKubun を設定。
	 */
	public void setJigyoKubun(String jigyoKubun) {JigyoKubun = jigyoKubun;}
// Horikoshi

	public String getJuriComment() {
		return juriComment;
	}
	public void setJuriComment(String juriComment) {
		this.juriComment = juriComment;
	}

    /**
     * @return Returns the tantoJigyoCd.
     */
    public Collection getTantoJigyoCd() {
        return tantoJigyoCd;
    }

    /**
     * @param tantoJigyoCd The tantoJigyoCd to set.
     */
    public void setTantoJigyoCd(Collection tantoJigyoCd) {
        this.tantoJigyoCd = tantoJigyoCd;
    }
}