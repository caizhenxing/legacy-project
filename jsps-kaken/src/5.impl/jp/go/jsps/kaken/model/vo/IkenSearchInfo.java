/*
 * 作成日： 2005/05/23
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package jp.go.jsps.kaken.model.vo;

 

/**
 * @author user1
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
public class IkenSearchInfo extends SearchInfo {

	/** 申請者フラグ */
	private String shinseisya ;
	
	/** 所属機関担当者 */
	private String syozoku ;
	
	/** 部局担当者 */
	private String bukyoku ;
	
	/** 審査員　*/
	private String shinsyain ;
	
	/** 作成日(開始) */
	private String     sakuseiDateFrom;
	
	/** 作成日(終了) */
	private String     sakuseiDateTo;
	
	/** 表示方式 */
	private String dispmode ;
	
	/**
	 * 
	 */
	public IkenSearchInfo() {
		super();
		// TODO 自動生成したコンストラクター・スタブ
	}

	/**
	 * 申請者フラグの取得
	 * @return
	 */
	public String getShinseisya(){
		return shinseisya;
	}
	
	/**
	 * 申請者フラグの設定
	 * @param n
	 */
	public void setShinseisya(String n){
		shinseisya = n;
	}

	/**
	 * 所属機関担当者フラグの取得
	 * @return
	 */
	public String getSyozoku(){
		return syozoku;
	}
	
	/**
	 * 所属機関担当者フラグの設定
	 * @param n
	 */
	public void setSyozoku(String n){
		syozoku = n;
	}
	
	/**
	 * 部局担当者フラグの取得
	 * @return
	 */
	public String getBukyoku(){
		return bukyoku;
	}
	
	/**
	 * 部局担当者フラグの設定
	 * @param n
	 */
	public void setBukyoku(String n){
		bukyoku = n;
	}

	/**
	 * 申請者フラグの取得
	 * @return
	 */
	public String getShinsyain(){
		return shinsyain;
	}
	
	/**
	 * 申請者フラグの設定
	 * @param n
	 */
	public void setShinsyain(String n){
		shinsyain = n;
	}
	
	/**
	 * 投稿日開始
	 * @return
	 */
	public String getSakuseiDateFrom() {
		return sakuseiDateFrom;
	}

	/**
	 * 投稿日開始
	 * @return
	 */
	public void setSakuseiDateFrom(String s) {
		sakuseiDateFrom = s;
	}

	/**
	 * 投稿日終了
	 * @return
	 */
	public String getSakuseiDateTo() {
		return sakuseiDateTo;
	}
	
	/**
	 * 投稿日終了
	 * @return
	 */
	public void setSakuseiDateTo(String s ) {
		sakuseiDateTo = s;
	}

	/**
	 * 申請者フラグの取得
	 * @return
	 */
	public String getDispmode(){
		return dispmode;
	}
	
	/**
	 * 申請者フラグの設定
	 * @param n
	 */
	public void setDispmode(String n){
		dispmode = n;
	}
}
