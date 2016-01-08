/*======================================================================
 *    SYSTEM      : 
 *    Source name : IkenInfo.java
 *    Description : 意見・要望情報を保持するクラス
 *
 *    Author      : Admin
 *    Date        : 2005/05/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *　　2005/05/20    1.0         Xiang Emin     新規作成
 *
 *====================================================================== 
 */

package jp.go.jsps.kaken.model.vo;

import java.io.Serializable;


/**
 * @author user1
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
public class IkenInfo implements Serializable {

	/** システム受付番号 */
	private String system_no = "";
	
	/**　投稿日 */
	private String sakusei_date = "";
	
	/** 対象者ＩＤ */
	private int taisho_id = 0;

	/** 対象者名称 */
	private String taisho_nm = "";
	
	/** ご意見内容 */
	private String iken = "";
	
	/** 備考 */
	private String biko = "";
	
	/**
	 * @return システム受付番号
	 */
	public String getSystem_no(){
		return system_no;
	}
	
	/**
	 * @param s システム受付番号
	 */
	public void setSystem_no(String s){
		system_no = s;
	}
	
	/**
	 * @return 投稿日
	 */
	public String getSakusei_date(){
		return sakusei_date;
	}
	
	/**
	 * @param s 投稿日
	 */
	public void setSakusei_date(String s){
		sakusei_date = s;
	}
	
	/**
	 * @return 対象者ＩＤ
	 */
	public int getTaisho_id(){
		return taisho_id;
	}
	
	/**
	 * @param s 対象者ＩＤ
	 */
	public void setTaisho_id(int id){
		taisho_id = id;
	}

	/**
	 * @return 対象者名称
	 */
	public String getTaisho_nm(){
		return taisho_nm;
	}
	
	/**
	 * @param s 対象者名称
	 */
	public void setTaisho_nm(String nm){
		taisho_nm = nm;
	}
	
	/**
	 * @return 意見内容
	 */
	public String getIken(){
		return iken;
	}
	
	/**
	 * @param s 意見内容
	 */
	public void setIken(String s){
		iken = s;
	}
	
	/**
	 * @return 備考
	 */
	public String getBiko(){
		return biko;
	}
	
	/**
	 * @param s 備考
	 */
	public void setBiko(String s){
		biko = s;
	}

}
