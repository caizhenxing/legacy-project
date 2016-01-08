/*
 * 作成日: 2005/03/28
 *
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.model.vo.KenkyushaPk;
import jp.go.jsps.kaken.model.vo.KenkyushaSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.Page;

/**
 * 研究者情報の管理を行うインターフェース。
 * 
 * @author yoshikawa_h
 *
 */
public interface IKenkyushaMaintenance {
	
	/**
	 * 研究者情報を検索する。
	 * 
	 * @param userInfo				実行するユーザ情報
	 * @param pkInfo				検索するユーザ情報
	 * @return						取得したユーザ情報
	 * @throws ApplicationException	
	 * @throws NoDateFoundException		対象データが見つからない場合の例外。
	 */
	public KenkyushaInfo select(UserInfo userInfo,KenkyushaPk pkInfo) throws ApplicationException;
	
	
	/**
	 * 未登録申請者情報を検索する。
	 * @param userInfo				実行するユーザ情報。
	 * @param searchInfo			検索情報
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public Page searchUnregist(UserInfo userInfo,ShinseishaSearchInfo searchInfo) throws ApplicationException;

	
	/**
	 * 未登録申請者情報を登録する。
	 * @param userInfo				実行するユーザ情報。
	 * @param addInfo			登録情報
	 * @return						検索結果ページ情報
	 * @throws ApplicationException	
	 */
	public void registShinseishaFromKenkyusha(UserInfo userInfo,String[] kenkyuNo) throws ApplicationException;

	//2005/04/15 追加 ここから----------------------------------------------------------------
	//理由 研究者情報の処理のため	
	/**
	 * 入力データチェック。
	 * @param userInfo UserInfo
	 * @param info KenkyushaInfo
	 * @return 研究者情報のShinseishaInfo
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public KenkyushaInfo validate(UserInfo userInfo, KenkyushaInfo info, String mode) throws ApplicationException, ValidationException;
	
	/**
	 * 研究者情報の追加。
	 * @param userInfo UserInfo
	 * @param addInfo KenkyushaInfo
	 * @return 登録した研究者情報を持つKenkyushaInfo
	 * @throws ApplicationException
	 */
	public void insert(UserInfo userInfo, KenkyushaInfo addInfo) throws ApplicationException;
	
	/**
	 * 検索条件に合う研究者情報を取得する。
	 * @param searchInfo	KenkyushaSearchInfo
	 * @return 研究者情報のPage
	 * @throws ApplicationException
	 */
	public Page search(KenkyushaSearchInfo searchInfo) throws ApplicationException;
	
	/**
	 * 研究者情報の更新。
	 * @param userInfo UserInfo
	 * @param updateInfo KenkyushaInfo
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo, KenkyushaInfo updateInfo) throws ApplicationException;
	
	/**
	 * 研究者情報の削除。
	 * @param userInfo UserInfo
	 * @param deleteInfo KenkyushaInfo
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo, KenkyushaInfo deleteInfo) throws ApplicationException;
	
	/**
	 * 研究者情報の取得。
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo KenkyushaPk
	 * @param lock boolean
	 * @return KenkyushaInfo
	 * @throws ApplicationException
	 */
	public KenkyushaInfo selectKenkyushaData(UserInfo userInfo, KenkyushaPk pkInfo, boolean lock) throws ApplicationException;

	//追加 ここまで--------------------------------------------------------------------------
	
	/**
	 * 申請者一括登録のIDパスワード通知書を発行する。
	 * @param userInfo
	 * @param kenkyuNo
	 * @return FileResource
	 * @throws ApplicationException
	 */
	public FileResource createTsuchisho(UserInfo userInfo,	String[] kenkyuNo) throws ApplicationException;

	/**
	 * 研究者マスタ更新日を取得する
	 * @return  更新日
	 */
	public String GetKenkyushaMeiboUpdateDate(UserInfo userInfo) throws ApplicationException;
	
    /**
     * 研究者名簿ダウンロードデータを取得する
     * @param userInfo
     * @return
     * @throws ApplicationException
     */
	public List searchMeiboCsvData(UserInfo userInfo)   throws ApplicationException;
}
