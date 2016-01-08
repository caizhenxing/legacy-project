/*
 * 作成日: 2005/03/24
 *
 */
package jp.go.jsps.kaken.model;

import java.util.List;
import java.util.Set;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.BukyokuSearchInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.BukyokutantoPk;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.Page;

/**
 * @author yoshikawa_h
 *
 */
public interface IBukyokutantoMaintenance {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------	
	/** 登録済みフラグ：未登録値 */
	public static final String REGIST_FLG_YET = "0";
	
	//---------------------------------------------------------------------
	// Methods 
	//---------------------------------------------------------------------	
	
	/**
	 * パスワードを変更する。
	 * @param userInfo					実行するユーザ情報
	 * @param pkInfo					パスワードを更新する検索するユーザ主キー情報。
	 * @param oldPassword				更新前パスワード
	 * @param newPassword				新しいパスワード
	 * @return							パスワードの変更に成功した場合 true 以外 false
	 * @throws ApplicationException	
	 * @throws ValidationException		更新前パスワードが一致しない場合等、検証にエラーがあった場合。
	 */
	public boolean changePassword(UserInfo userInfo, BukyokutantoPk pkInfo,String oldPassword ,String newPassword)
		throws ApplicationException,ValidationException;
	
	public BukyokutantoInfo[] select(UserInfo userInfo, BukyokutantoPk pkInfo)
		throws ApplicationException;

	// 2005/04/07 追加ここから---------------------------------------------
	// 理由 部局担当者情報の登録、更新、削除、パスワード変更処理追加のため	
	
	/** 
 	 * 部局担当者一覧情報の取得。
 	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		検索条件
	 * @return		Page		ページ情報
	 * @exception	ApplicationExcepiton
	 */
	public Page searchBukyokuList(UserInfo userInfo, BukyokuSearchInfo info)
		throws ApplicationException;	
	
	/** 
　	 * 部局担当者情報の登録。
	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		部局担当者情報
	 * @return		info		登録データを格納した部局担当者情報
	 * @exception	ApplicationException
	 */		
	public BukyokutantoInfo setBukyokuData(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException;
	
	/** 
	 * 入力された部局コードが部局マスタに含まれるかどうかを確認する。
	 *   
	 * @param		userInfo	ユーザ情報
	 * @param		array		部局コード配列	
	 * @exception	ApplicationException
	 */
	public void CheckBukyokuCd(UserInfo userInfo, Set set)
		throws ApplicationException;
	
	/** 
	 * 部局担当者情報を取得する。
	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		部局担当コードの格納された部局担当者情報
	 * @return		info		登録データを格納した部局担当者情報
	 * @exception	ApplicationException
	 */		
	public BukyokutantoInfo selectBukyokuData(UserInfo userInfo, BukyokutantoInfo info)
			throws ApplicationException;
    
    /** 
 	 * 部局担当者情報削除。
 	 * 
  	 * @param		userInfo	ユーザ情報
	 * @param		info		部局担当者情報
	 * @exception	ApplicationException
 	 */
	public void delete(UserInfo userInfo, BukyokutantoInfo info)
			throws ApplicationException;

    // 2005/04/22 追加 ここから---------------------------------------
    // 理由 所属機関担当者削除時、同所属機関に属する部局担当者情報も削除する
    /** 
 	 * 部局担当者情報削除。
 	 * 
  	 * @param		userInfo	ユーザ情報
	 * @param		shozokuCd		部局担当者情報
	 * @exception	ApplicationException
 	 */
	public void deleteAll(UserInfo userInfo, String shozokuCd)
			throws ApplicationException;
	// 追加 ここまで---------------------------------------------------
	
	/** 
	 * 所属担当者が部局担当者のパスワードを変更する。
	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		部局担当者情報
	 * @return		info		変更パスワードを格納した部局担当者情報
	 * @exception	ApplicationException
	 */		
	public BukyokutantoInfo changeBukyokuPassword(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException;

	/** 
	 * 所属機関担当者の連絡先や所属機関名等を取得する。
	 * 
	 * @param		userInfo	ユーザ情報
	 * @param		info		所属コードが格納された部局担当者情報
	 * @return		info		部局担当者情報
	 * @exception	ApplicationException
	 */
	public BukyokutantoInfo selectShozokuData(UserInfo userInfo, BukyokutantoInfo info)
		throws ApplicationException;

	// 追加 ここまで--------------------------------------------------------
	
	//2005/06/01 追加 ここから----------------------------------------------
	//理由 証明書発行用CSVに部局担当者情報を出力するため
	
	/**
	 * 証明書発行用CSVデータを取得する.<BR><BR>
	 * 
	 * @param userInfo		UserInfo
	 * @param info			ShozokuInfo
	 * @param list			List
	 * @return	証明書発行用CSVデータ
	 * @throws ApplicationException
	 */
	public List getShomeiCsvData(UserInfo userInfo, ShozokuInfo info, List list)
		throws ApplicationException;
		
	//追加 ここまで---------------------------------------------------------
}
