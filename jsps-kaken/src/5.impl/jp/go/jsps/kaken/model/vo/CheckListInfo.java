/*
 * Created on 2005/04/12
 *
 */
package jp.go.jsps.kaken.model.vo;

/**
 * チェックリスト用データ保持クラス
 * 
 * @author masuo_t
 *
 */
public class CheckListInfo extends CheckListPk {

	/** 事業CD */	
	private String jigyoCd;

	//2005/04/21 追加 ここから--------------------------------------------------
	//理由 検索条件に所属機関名と受理状況が追加されたため
	
	/** 受理状況 */
	private String jyuriJokyo;
	
	/** 所属機関名 */
	private String shozokuName;
	
	//追加 ここまで-------------------------------------------------------------
	
	//2005/05/25 追加 ここから--------------------------------------------------
	//理由 PDF表示用のデータ保持のため

	/** 事業名 */
	private String jigyoName;
	
	/** 回数(→件数) */
	private String kaisu;
	
	/** 新規件数 */
	private int shinkiCount;
	
	/** 継続件数 */
	private int keizokuCount;
	
	/** 事業区分 */
	private String jigyoKbn;

	
	//追加 ここまで-------------------------------------------------------------

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

	//2005/04/21 追加 ここから--------------------------------------------------
	//理由 検索条件に所属機関名と受理状況が追加されたため

	/**
	 * @return
	 */
	public String getJyuriJokyo() {
		return jyuriJokyo;
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
	public void setJyuriJokyo(String string) {
		jyuriJokyo = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}
	//追加 ここまで-------------------------------------------------------------
	
	//2005/05/25 追加 ここから--------------------------------------------------
	//理由 PDF表示用のデータ保持のため
	/**
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
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
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}
	//追加 ここまで-------------------------------------------------------------

	/**
	 * @return 新規件数を返す
	 */
	public int getShinkiCount(){
		return shinkiCount;
	}

	/**
	 * @param cnt 新規件数を設定する
	 */
	public void setShinkiCount(int cnt){
		shinkiCount = cnt;
	}
	
	/**
	 * @return 継続件数を返す
	 */
	public int getKeizokuCount(){
		return keizokuCount;
	}
	
	/**
	 * @param cnt 継続件数を取得する
	 */
	public void setKeizokuCount(int cnt){
		keizokuCount = cnt;
	}

	/**
	 * @return 事業区分を返す
	 */
	public String getJigyoKbn(){
		return jigyoKbn;
	}
	
	/**
	 * @param str 事業区分を取得する
	 */
	public void setJigyoKbn(String str){
		jigyoKbn = str;
	}
}
