/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/07/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
 
package jp.go.jsps.kaken.model.vo;

import java.util.*;

import jp.go.jsps.kaken.util.*;

/**
 * 組み合わせステータス検索条件を保持するクラス。
 * 申請状況１つに対して、複数の再申請フラグ状況を検索条件とした場合に使用する。<br>
 * <p>
 * 次のようなSQLを使用したいときに用いる。<br>
 * ex. A.JOKYO_ID = 'jokyoID' AND A.SAISHINSEI_FLG IN ('flg1','flg2',...)<br>
 * </p>
 * <p>
 * 上記SQL文を複数結合することができる。<br>
 * addAndQuery()を呼び出した場合はAND条件、addOrQuery()を呼び出した場合はOR条件で検索する。<br>
 * 結合する順番は追加した順番となる。<br> 
 * </p>
 * 
 * ID RCSfile="$RCSfile: CombinedStatusSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class CombinedStatusSearchInfo extends ValueObject{
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	/** 検索結合条件 */
	private static final String[] LOGICAL_OPERATOR = new String[]{" AND"," OR"};
	
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** 検索条件SQLのリスト */
	private List queryList = new ArrayList();
	
	/** 検索結合条件のリスト（AND,OR）*/
	private List andOrList = new ArrayList();
	
	//...

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public CombinedStatusSearchInfo() {
		super();
		clearQuery();
	}


	//---------------------------------------------------------------------
	// Private Methods
	//---------------------------------------------------------------------
	
	/**
	 * SQLを生成する。引数がnullの場合はその条件を除外したSQLを作成する。
	 * 両方の引数がnull（または値が無い）場合は例外を発生する。
	 * @param jokyoId       申請状況
	 * @param saishinseiFlg 再申請フラグの配列
	 * @throws IllegalArgumentException 申請状況、再申請フラグのどちらも値が無かった場合
	 * @return
	 */
	private String createSQL(String jokyoId, String[] saishinseiFlg)
		throws IllegalArgumentException
	{
		boolean     exist = false;
		StringBuffer query = new StringBuffer(" (");
		//申請状況
		if(jokyoId != null && jokyoId.length() != 0){
			query.append(" A.JOKYO_ID = '")
			     .append(jokyoId)
			     .append("'");
			exist = true;
		}
		//再申請フラグ
		if(saishinseiFlg != null && saishinseiFlg.length != 0){
			if(exist){
				query.append(" AND");	//申請状況の条件がセットされていた場合はANDで結合
			}
			query.append(" A.SAISHINSEI_FLG IN (")
				 .append(StringUtil.changeArray2CSV(saishinseiFlg, true))
				 .append(")");
			exist = true;
		}
		
		//検索条件が指定されていなかった場合はエラー
		if(exist){
			return query.append(")").toString();
		}else{
			String msg = "検索条件の引数が無効です。jokyoId="+jokyoId+"saishinseiFlg="+Arrays.asList(saishinseiFlg);
			throw new IllegalArgumentException(msg);
		}
		
	}
	

	
	
	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------
	
	/**
	 * 検索条件をクリアする。 
	 */
	public void clearQuery(){
		queryList.clear();
		andOrList.clear();
	}
	
	
	/**
	 * 検索条件がセットされていればtrueを返す。
	 * @return
	 */
	public boolean hasQuery(){
		return !(queryList.isEmpty());
	}
	
	
	/**
	 * 渡された申請状況(jokyoId)で、かつ、渡された再申請フラグ(saishinseiFlg)のいずれか
	 * の検索条件を、AND条件で追加する。
	 * 最初に追加した条件の場合は、ANDが無視される。
	 * getQuery()で受け取ったSQLをANDで結合するか、ORで結合するかは上位側に依存。
	 * @param jokyoId       申請状況
	 * @param saishinseiFlg 再申請フラグの配列
	 * @throws IllegalArgumentException 申請状況、再申請フラグのどちらも値が無かった場合
	 */
	public void addAndQuery(String jokyoId, String[] saishinseiFlg) 
		throws IllegalArgumentException{
		queryList.add(createSQL(jokyoId, saishinseiFlg));
		andOrList.add(LOGICAL_OPERATOR[0]);
	}
	
	/**
	 * 渡された申請状況(jokyoId)で、かつ、渡された再申請フラグ(saishinseiFlg)のいずれか
	 * の検索条件を、OR条件で追加する。
	 * 最初に追加した条件の場合は、ORが無視される。
	 * getQuery()で受け取ったSQLをANDで結合するか、ORで結合するかは上位側に依存。
	 * @param jokyoId       申請状況
	 * @param saishinseiFlg 再申請フラグの配列
	 * @throws IllegalArgumentException 申請状況、再申請フラグのどちらも値が無かった場合
	 */
	public void addOrQuery(String jokyoId, String[] saishinseiFlg) 
		throws IllegalArgumentException{
		queryList.add(createSQL(jokyoId, saishinseiFlg));
		andOrList.add(LOGICAL_OPERATOR[1]);
	}
	
	/**
	 * 渡された申請状況(jokyoId)で、かつ、渡された再申請フラグ(saishinseiFlg)のいずれか
	 * の検索条件を、AND条件で指定インデックスに追加する。
	 * 最初に追加した条件の場合は、ANDが無視される。
	 * getQuery()で受け取ったSQLをANDで結合するか、ORで結合するかは上位側に依存。
	 * @param  jokyoId       申請状況
	 * @param  saishinseiFlg 再申請フラグの配列
	 * @param  index         条件を挿入するインデックス
	 * @throws IndexOutOfBoundsException インデックスが範囲外の場合
	 * @throws IllegalArgumentException 申請状況、再申請フラグのどちらも値が無かった場合
	 */
	public void addAndQuery(String jokyoId, String[] saishinseiFlg, int index)
		throws IndexOutOfBoundsException, IllegalArgumentException{
		queryList.add(index, createSQL(jokyoId, saishinseiFlg));
		andOrList.add(index, LOGICAL_OPERATOR[0]);
	}
	
	/**
	 * 渡された申請状況(jokyoId)で、かつ、渡された再申請フラグ(saishinseiFlg)のいずれか
	 * の検索条件を、OR条件で指定インデックスに追加する。
	 * 最初に追加した条件の場合は、ORが無視される。
	 * getQuery()で受け取ったSQLをANDで結合するか、ORで結合するかは上位側に依存。
	 * @param  jokyoId       申請状況
	 * @param  saishinseiFlg 再申請フラグの配列
	 * @param  index         条件を挿入するインデックス
	 * @throws IndexOutOfBoundsException インデックスが範囲外の場合
	 * @throws IllegalArgumentException 申請状況、再申請フラグのどちらも値が無かった場合
	 */
	public void addOrQuery(String jokyoId, String[] saishinseiFlg, int index)
		throws IndexOutOfBoundsException, IllegalArgumentException{
		queryList.add(index, createSQL(jokyoId, saishinseiFlg));
		andOrList.add(index, LOGICAL_OPERATOR[1]);
	}
	
	
	/**
	 * 検索条件SQL文を返す。<br>
	 * 申請データテーブルに対して「A」という別名をつけていること。<br>
	 * 検索条件が指定されていなかった場合は、nullが返る。
	 * <p>
	 * ex. 次のような文字列が返る。<br>
	 * ( A.JOKYO_ID = 'jokyoID' AND A.SAISHINSEI_FLG IN ('flg1','flg2',...) )<br>
	 *  [AND/OR]<br>
	 * ( A.JOKYO_ID = 'jokyoID2' AND A.SAISHINSEI_FLG IN ('flg1','flg2',...) )<br>
	 *  [AND/OR]<br>
	 * 　　：<br>
	 * </p>
	 * @return
	 */
	public String getQuery(){
		
		//検索条件無しの場合
		if(!hasQuery()){
			return null;
		}
		
		//検索SQLを構築
		StringBuffer query = new StringBuffer(" (");
		
		//最初のAND/OR条件は除外するため、1つ目だけは個別にセットする
		query.append(StringUtil.defaultString(queryList.get(0)));
		
		//2つ目以降は繰り返し
		for(int i=1; i<queryList.size(); i++){
			query.append(StringUtil.defaultString(andOrList.get(i)))
			     .append(StringUtil.defaultString(queryList.get(i)));
		}
		
		return query.append(")").toString();
		
	}	
	
	
}
