/*======================================================================
 *    SYSTEM      : 電子申請システム（科学研究費補助金）
 *    Source name : LabelValueManager
 *    Description : リストボックスやプルダウンメニューのリストを生成するためのクラス
 *
 *    Author      : Admin
 *    Date        : 2003/12/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ILabelKubun;
import jp.go.jsps.kaken.model.common.SystemServiceFactory;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.UserInfo;

import jp.go.jsps.kaken.model.IShinseiMaintenance;
import jp.go.jsps.kaken.util.StringUtil;

/**
 * リストボックスやプルダウンメニューのリストを生成するためのクラス。
 * ID RCSfile="$RCSfile: LabelValueManager.java,v $"
 * Revision="$Revision: 1.6 $"
 * Date="$Date: 2007/07/19 06:54:03 $"
 */
public class LabelValueManager {

	//---------------------------------------------------------------------
	// 汎用メソッド
	//---------------------------------------------------------------------
	/**
	 * LavelValueListを生成し取得する。
	 * 第1引数のListにはMap形式のオブジェクトが格納されていること。
	 * 格納されているMapから、第2引数をキーとして取得した値をラベルとして、
	 * 第3引数をキーとして取得した値をバリュー値としてLavelValueBeanを生成し、Listとして返す。
	 * @param recordList  レコードリスト
	 * @param labelName   ラベル名
	 * @param valueName   バリュー値
	 * @return LabelValueBeanのリスト
	 */
	public static List getLavelValueList(List recordList, 
										 String labelName, 
										 String valueName) {
		List beanList = new ArrayList();
		for(int i=0; i<recordList.size(); i++){
			Map record = (Map)recordList.get(i);
			beanList.add(new LabelValueBean((String)record.get(labelName),
											 (String)record.get(valueName)));
		}
		return beanList;
	}

	//2005.10.25 iso ラベルリスト一括取得のため追加
	/**
	 * ラベル名を取得する。
	 * ラベルが、Mapの形式で戻る。
	 * @param	labelKubun	ラベル区分
	 * @return				ラベルマップ
     * @throws ApplicationException
	 */
	public static Map getLabelMap(String[] labelKubun) throws ApplicationException {

		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelMap(labelKubun);
	}

	//---------------------------------------------------------------------
	// 所属機関種別
	//---------------------------------------------------------------------
	/**
	 * 所属機関種別を取得する。
	 * @return	所属機関種別リスト
     * @throws ApplicationException
	 */
	public static List getKikanShubetuCdList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List shubetuCdList = servise.getKikanShubetuCdList();
		return shubetuCdList;
	}

	/**
	 * 所属機関種別コードに一致する表示名称を取得する。
	 * @param value	所属機関種別コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getKikanShubetuCdValue(String value) 
		throws ApplicationException {		
		return getLabel(getKikanShubetuCdList(), value);
	}

	//---------------------------------------------------------------------
	// 部局種別リスト
	//---------------------------------------------------------------------
	/**
	 * 部局種別を取得する。
	 * @return	部局種別リスト
     * @throws ApplicationException
	 */
	public static List getBukyokuShubetuCdList()
			throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List bukyokuCdList = servise.getLabelList(ILabelKubun.BUKYOKU_SHUBETU);
		return bukyokuCdList;
	}

	/**
	 * 部局種別コードに一致する表示名称を取得する。
	 * @param value	部局種別コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getBukyokuShubetuCdValue(String value)
			throws ApplicationException {		
		return getLabel(getBukyokuShubetuCdList(), value);
	}

	//---------------------------------------------------------------------
	// 職種（職名）リスト
	//---------------------------------------------------------------------
	/**
	 * 職種（職名）リストを取得する。（申請書入力用）
	 * @return	職種（職名）リスト
     * @throws ApplicationException
	 */
	public static List getShokushuCdList()
			throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List shokushuList = servise.getShokushuList();
		return shokushuList;
	}

	/**
	 * 職種コードに一致する表示名称を取得する。
	 * @param value	 職種コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getShokushuCdValue(String value) 
			throws ApplicationException {		
		return getLabel(getShokushuCdList(), value);
	}

	//---------------------------------------------------------------------
	// 事業名リスト
	//---------------------------------------------------------------------
	/**
	 * 事業名リストを取得する。
	 * @deprecated getJigyoNameList(UserInfo userinfo)を使用すること。
	 * @see getJigyoNameList(UserInfo userinfo)
	 * @return	事業名リスト
     * @throws ApplicationException
	 */
	public static List getJigyoNameList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List jigyoList = servise.getJigyoNameList();
		return jigyoList;
	}

	/**
	 * 事業名リストを取得する。
	 * 渡されたUserInfoが業務担当者の場合は、
	 * 自分の担当する事業のみの事業名リストが返る。
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public static List getJigyoNameList(UserInfo userInfo)
		throws ApplicationException {
		//2005.05.13 iso 事業名リストの取得は、事業区分→事業CDになったので移行。
//		//サーバサービスの呼び出し
//		ISystemServise servise = SystemServiceFactory.getSystemService(
//									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
//		List jigyoList = servise.getJigyoNameList(userInfo);
//		return jigyoList;
		return getJigyoNameListByJigyoCds(userInfo);
	}

	/**
	 * 指定された事業区分の事業名リストを取得する。
	 * @param userInfo
	 * @param jigyoKubun
	 * @return
	 * @throws ApplicationException
	 */
	public static List getJigyoNameList(UserInfo userInfo, String jigyoKubun)
		throws ApplicationException {
		//2005.05.13 iso 事業名リストの取得は、事業区分→事業CDになったので移行。
//		//サーバサービスの呼び出し
//		ISystemServise servise = SystemServiceFactory.getSystemService(
//									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
//		List jigyoList = servise.getJigyoNameList(userInfo, jigyoKubun);
//		return jigyoList;
		return getJigyoNameListByJigyoCds(userInfo, jigyoKubun);
	}

	/**
	 * 事業名リストを取得する。
	 * 渡されたUserInfoが業務担当者の場合は、自分の担当する事業区分（1または4の場合）のみの
	 * 事業名リストが返る
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public static List getShinsaTaishoJigyoNameList(UserInfo userInfo)
		throws ApplicationException {
//		2005.05.13 iso 事業名リストの取得は、事業区分→事業CDになったので移行。
//		//サーバサービスの呼び出し
//		ISystemServise servise = SystemServiceFactory.getSystemService(
//									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
//		
//		List jigyoList = servise.getShinsaTaishoJigyoNameList(userInfo);		
//		return jigyoList;
		return getShinsaTaishoListByJigyoCds(userInfo);
	}

	/**
	 * 事業名コードに一致する表示名称を取得する。
	 * @param value	事業名コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getJigyoNameList(String value) 
		throws ApplicationException {		
		return getLabel(getJigyoNameList(), value);
	}

	//2005/04/27 追加ここから-------------------------------------------
	//理由 自分の担当する事業コードで絞り込んだ事業名を取得するため
	/**
	 * 事業名リストを取得する。
	 * 渡されたUserInfoが業務担当者の場合は、
	 * 自分の担当する事業のみの事業名リストが返る
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public static List getJigyoNameListByJigyoCds(UserInfo userInfo)
		throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		
		List jigyoList = servise.getJigyoNameListByJigyoCds(userInfo);		
		return jigyoList;
	}
	//追加 ここまで---------------------------------------------------------

	/**
	 * 事業名リストを取得する。
	 * 渡されたUserInfoが業務担当者の場合は、
	 * 自分の担当する事業CD・事業区分のみの事業名リストが返る
	 * @param userInfo
	 * @param jigyoKubun
	 * @return
	 * @throws ApplicationException
	 */
	public static List getJigyoNameListByJigyoCds(UserInfo userInfo, String jigyoKubun)
			throws ApplicationException {
		
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		
		List jigyoList = servise.getJigyoNameListByJigyoCds(userInfo, jigyoKubun);		
		return jigyoList;
	}
   
//  2006/06/14 李万軍　追加ここから------------------------------------------- 
    /**
     * 事業名リストを取得する。
     * 渡されたUserInfoが業務担当者の場合は、
     * 自分の担当する事業CD・事業区分のみの事業名リストが返る
     * @param userInfo
     * @param jigyoCd
     * @return
     * @throws ApplicationException
     */
    public static List getJigyoNameListByJigyoCds2(UserInfo userInfo, String jigyoCd)
            throws ApplicationException {
        
        //サーバサービスの呼び出し
        ISystemServise servise = SystemServiceFactory.getSystemService(
                                    IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
        String[] validJigyoCds = new String[]{jigyoCd};
        List jigyoList = servise.getJigyoNameListByJigyoCds(userInfo, validJigyoCds, "");      
        return jigyoList;
    }
//  追加 ここまで---------------------------------------------------------
    
	/**
	 * 事業名リストを取得する。
	 * 渡されたUserInfoが業務担当者の場合は、
	 * 自分の担当する事業・審査担当する事業区分（1または4の場合）のみの事業名リストが返る
	 * @param userInfo
	 * @return
	 * @throws ApplicationException
	 */
	public static List getShinsaTaishoListByJigyoCds(UserInfo userInfo)
            throws ApplicationException {

        // サーバサービスの呼び出し
        ISystemServise servise = SystemServiceFactory
                .getSystemService(IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);

        List jigyoList = servise.getShinsaTaishoListByJigyoCds(userInfo);
        return jigyoList;
    }

	//---------------------------------------------------------------------
	// 1次審査入力方式
	//---------------------------------------------------------------------
	/**
	 * 1次審査入力方式を取得する。
	 * @return	対応機関名リスト
	 */
	public static List getShinsaTypeList() {
        List shinsaTypeList = new ArrayList();
        shinsaTypeList.add(new LabelValueBean("ABC方式", "0"));
        shinsaTypeList.add(new LabelValueBean("点数方式", "1"));
        shinsaTypeList.add(new LabelValueBean("両方", "2"));

        return shinsaTypeList;
    }

	/**
	 * 1次審査入力方式コードに一致する表示名称を取得する。
	 * @param value	1次審査入力方式コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getShinsaTypeList(String value) 
		throws ApplicationException {		
		return getLabel(getShinsaTypeList(), value);
	}

	//---------------------------------------------------------------------
	// 評価用ファイル有無／コメント欄有無（なし／あり）
	//---------------------------------------------------------------------
	/**
	 * 評価用ファイル有無／コメント欄有無を取得する。
	 * @return	評価用ファイル有無／コメント欄有無リスト
	 */
	public static List getFlgList() {
        List flgList = new ArrayList();
        flgList.add(new LabelValueBean("なし", "0"));
        flgList.add(new LabelValueBean("あり", "1"));
        return flgList;
    }

	/**
	 * 評価用ファイル有無／コメント欄有無コードに一致する表示名称を取得する。
	 * @param value	評価用ファイル有無／コメント欄有無コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getFlgList(String value) 
		throws ApplicationException {		
		return getLabel(getFlgList(), value);
	}

	//---------------------------------------------------------------------
	// 期間－開始日指定なしフラグ／期間－終了日指定なしフラグ（指定なし／指定あり）
	//---------------------------------------------------------------------
	/**
	 * 期間－開始日指定なしフラグ／期間－終了日指定なしフラグを取得する。
	 * @return	期間－開始日指定なしフラグ／期間－終了日指定なしフラグリスト
	 */
	public static List getShiteiFlgList() {
        List flgList = new ArrayList();
        flgList.add(new LabelValueBean("指定なし", "0"));
        flgList.add(new LabelValueBean("", "1"));

        return flgList;
    }

	/**
	 * 期間－開始日指定なしフラグ／期間－終了日指定なしフラグコードに一致する表示名称を取得する。
	 * @param value	期間－開始日指定なしフラグ／期間－終了日指定なしフラグコード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getShiteiFlgList(String value) 
		throws ApplicationException {		
		return getLabel(getShiteiFlgList(), value);
	}

	//---------------------------------------------------------------------
	// 期間－指定フラグなしフラグ（指定なし／指定あり（年月）／指定あり（日））
	//---------------------------------------------------------------------
	/**
	 * 期間－指定フラグなしフラグを取得する。
	 * @return	期間－指定フラグなしフラグリスト
	 */
	public static List getKikanFlgList() {
        List flgList = new ArrayList();
        flgList.add(new LabelValueBean("指定なし", "0"));
        flgList.add(new LabelValueBean("", "1"));
        flgList.add(new LabelValueBean("", "2"));
        return flgList;
    }

	/**
	 * 期間－開始日指定なしフラグ／期間－終了日指定なしフラグコードに一致する表示名称を取得する。
	 * @param value	期間－開始日指定なしフラグ／期間－終了日指定なしフラグコード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getKikanFlgList(String value) 
		throws ApplicationException {		
		return getLabel(getKikanFlgList(), value);
	}

	//---------------------------------------------------------------------
	// 対象
	//---------------------------------------------------------------------
	/**
	 * 対象を取得する。
	 * @return	対象リスト
	 */
	public static List getTaishoIdList() {
		List taishoIdList = new ArrayList();
		taishoIdList.add(new LabelValueBean("所属機関向け"   , "2"));
		taishoIdList.add(new LabelValueBean("審査員向け" , "4"));
		//taishoIdList.add(new LabelValueBean("センター研究員向け" , "5"));
		
		return taishoIdList;
	}

	/**
	 * 対象コードに一致する表示名称を取得する。
	 * @param value	対象コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getTaishoIdList(String value) 
		throws ApplicationException {		
		return getLabel(getTaishoIdList(), value);
	}

//	2005/04/08 追加 ここから----------------------------
//  理由：研究対象の類型追加のため
	//---------------------------------------------------------------------
	// 研究対象の類型
	//---------------------------------------------------------------------
	/**
	 * 研究対象の類型を取得する。
	 * @return	新規・継続リスト
	 */
	public static List getKenkyuTaishoList() {
		List kenkyuTaishoList = new ArrayList();
// 20050712 企画調査の研究対象類型を変更
//		kenkyuTaishoList.add(new LabelValueBean("1", "1"));
//		kenkyuTaishoList.add(new LabelValueBean("2", "2"));
//		kenkyuTaishoList.add(new LabelValueBean("3", "3"));
		kenkyuTaishoList.add(new LabelValueBean("1：「特定領域研究」の新規発足研究領域として応募するための準備調査", "1"));
		kenkyuTaishoList.add(new LabelValueBean("2：学術振興上必要性の高い共同研究（国際共同研究を含む。）等の企画", "2"));
		kenkyuTaishoList.add(new LabelValueBean("3：日本での開催が予定されている国際研究集会の研究内容面に関する企画等の準備", "3"));
// Horikoshi
		return kenkyuTaishoList;
	}

	/**
	 * 研究対象コードに一致する表示名称を取得する。
	 * @param value	新規・継続コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getKenkyuTaishoList(String value) 
		throws ApplicationException {		
		return getLabel(getKenkyuTaishoList(), value);
	}
//	追加　ここまで--------------------------------------
	
//	2005/04/18 追加 ここから----------------------------
//  理由：海外分野追加のため
	//---------------------------------------------------------------------
	// 海外分野リスト
	//---------------------------------------------------------------------

	/**
	 * 海外分野リストを取得する。
	 * @return	海外分野リスト
	 * @throws ApplicationException
	 */
	public static List getKaigaiBunyaList()
		throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		
		List jigyoList = servise.getKaigaiBunyaList();		
		return jigyoList;
	}

	/**
	 * 海外分野コードに一致する表示名称を取得する。
	 * @param value	事業名コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getKaigaiBunyaList(String value) 
		throws ApplicationException {		
		return getLabel(getKaigaiBunyaList(), value);
	}

//	追加　ここまで--------------------------------------
	//---------------------------------------------------------------------
	// 新規・継続（審査員情報）
	//---------------------------------------------------------------------
	/**
	 * 新規・継続を取得する。
	 * @return	新規・継続リスト
	 */
	public static List getSinkiKeizokuFlgList(){
		return getSinkiKeizokuFlgList(false);
	}

    /**
     * 新規・継続を取得する。
     * @param tokuteiFlg
     * @return  新規・継続リスト
     */
	public static List getSinkiKeizokuFlgList(boolean tokuteiFlg) {
        List sinkiKeizokuFlgList = new ArrayList();
        sinkiKeizokuFlgList.add(new LabelValueBean("新規", "1"));
        sinkiKeizokuFlgList.add(new LabelValueBean("継続", "2"));
        if (tokuteiFlg) {
            sinkiKeizokuFlgList.add(new LabelValueBean("大幅な変更(特定領域研究)", "3"));
        }

        return sinkiKeizokuFlgList;
    }

	/**
	 * 新規・継続コードに一致する表示名称を取得する。
	 * @param value	新規・継続コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getSinkiKeizokuFlgList(String value) 
		throws ApplicationException {		
		return getLabel(getSinkiKeizokuFlgList(), value);
	}

	//---------------------------------------------------------------------
	// 謝金（審査員情報）
	//---------------------------------------------------------------------
	/**
	 * 謝金を取得する。
	 * @return	謝金リスト
	 */
	public static List getShakinList() {
        List shakinList = new ArrayList();
        shakinList.add(new LabelValueBean("支払う", "1"));
        shakinList.add(new LabelValueBean("辞退", "2"));
        shakinList.add(new LabelValueBean("該当なし", "3"));

        return shakinList;
    }

	/**
	 * 謝金コードに一致する表示名称を取得する。
	 * @param value	謝金コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getShakinList(String value) 
		throws ApplicationException {		
		return getLabel(getShakinList(), value);
	}

	//---------------------------------------------------------------------
	// 申請状況（所属機関・申請情報検索）
	//---------------------------------------------------------------------
	/**
	 * 申請状況を取得する。
	 * @return	申請状況
	 */
	public static List getJokyoList(){
		List shakinList = new ArrayList();
		shakinList.add(new LabelValueBean("　", "0"));
		//2005.08.19 iso 文言修正
//		shakinList.add(new LabelValueBean("申請者未確認", "1"));
		shakinList.add(new LabelValueBean("応募者未確認", "1"));
		shakinList.add(new LabelValueBean("所属機関受付中", "2"));
//       2006/7/31 zjp　追加　「11、12、13、14」 ここから----------------------
        shakinList.add(new LabelValueBean("領域代表者確認中", "11"));
        shakinList.add(new LabelValueBean("領域代表者確定済み", "12"));
        shakinList.add(new LabelValueBean("領域代表者却下", "13"));
        shakinList.add(new LabelValueBean("領域代表者所属研究機関受付中", "14"));
//      2006/7/31 zjp　追加　「11、12、13、14」　ここまで----------------------       
		shakinList.add(new LabelValueBean("学振受付中", "3"));
		shakinList.add(new LabelValueBean("学振受理", "4"));
		shakinList.add(new LabelValueBean("不受理", "8"));
//		shakinList.add(new LabelValueBean("採択", "4"));
//		shakinList.add(new LabelValueBean("不採択", "5"));
//		shakinList.add(new LabelValueBean("採択候補", "6"));
//		shakinList.add(new LabelValueBean("補欠", "7"));
		shakinList.add(new LabelValueBean("却下", "9"));
		shakinList.add(new LabelValueBean("修正依頼", "10"));
			
		return shakinList;
	}

	/**
	 * 申請状況コードに一致する表示名称を取得する。
	 * @param value	申請状況コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getJokyoList(String value) 
		throws ApplicationException {		
		return getLabel(getJokyoList(), value);
	}

	//---------------------------------------------------------------------
	// 申請状況（業務担当者・申請情報検索）
	//---------------------------------------------------------------------
	/**
	 * 申請状況を取得する。
	 * @return	申請状況
	 */
	public static List getGyomuJokyoList() {
        List shakinList = new ArrayList();
        shakinList.add(new LabelValueBean("　", "0"));
        shakinList.add(new LabelValueBean("受理前", "1"));
        shakinList.add(new LabelValueBean("受理済み", "2"));
        shakinList.add(new LabelValueBean("不受理", "3"));
        shakinList.add(new LabelValueBean("修正依頼", "4"));
        shakinList.add(new LabelValueBean("審査中", "5"));
        shakinList.add(new LabelValueBean("採択", "6"));
        shakinList.add(new LabelValueBean("不採択", "7"));
        shakinList.add(new LabelValueBean("採用候補", "8"));
        shakinList.add(new LabelValueBean("補欠", "9"));
        return shakinList;
    }

	/**
	 * 申請状況コードに一致する表示名称を取得する。
	 * @param value	申請状況コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getGyomuJokyoList(String value) 
		throws ApplicationException {		
		return getLabel(getGyomuJokyoList(), value);
	}

	//---------------------------------------------------------------------
	// 申請状況（システム管理者・申請情報検索）
	//---------------------------------------------------------------------
	/**
	 * 申請状況を取得する。
	 * @return	申請状況
	 */
	public static List getSystemJokyoList(){
		List shakinList = new ArrayList();		
		shakinList.add(new LabelValueBean("　", "0"));
		shakinList.add(new LabelValueBean("作成中", "1"));
		//2005.08.19 iso 文言修正
//		shakinList.add(new LabelValueBean("申請者未確認", "2"));
		shakinList.add(new LabelValueBean("応募者未確認", "2"));
		shakinList.add(new LabelValueBean("所属機関受付中", "3"));
//       2006/7/31 zjp　追加　「11、12、13、14」 ここから----------------------
        shakinList.add(new LabelValueBean("領域代表者確認中", "11"));
        shakinList.add(new LabelValueBean("領域代表者確定済み", "12"));
        shakinList.add(new LabelValueBean("領域代表者却下", "13"));
        shakinList.add(new LabelValueBean("領域代表者所属研究機関受付中", "14"));
//      2006/7/31 zjp　追加　「11、12、13、14」　ここまで----------------------       
		shakinList.add(new LabelValueBean("受理前", "4"));
		shakinList.add(new LabelValueBean("所属機関却下", "5"));
		shakinList.add(new LabelValueBean("受理済み", "6"));
		shakinList.add(new LabelValueBean("不受理", "7"));
		shakinList.add(new LabelValueBean("修正依頼", "8"));
		shakinList.add(new LabelValueBean("１次審査中", "9"));
		shakinList.add(new LabelValueBean("２次審査完了", "10"));	
		return shakinList;
	}

	/**
	 * 申請状況コードに一致する表示名称を取得する。
	 * @param value	申請状況コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getSystemJokyoList(String value) 
		throws ApplicationException {		
		return getLabel(getSystemJokyoList(), value);
	}

	//---------------------------------------------------------------------
	// 2次審査結果（業務担当者・2次審査結果登録）
	//---------------------------------------------------------------------
	/**
	 * 2次審査結果を取得する。リスト用データを取得する。ラベル区分に該当するソートが「0」以外のデータを取得する。
	 * @return	2次審査結果
     * @throws ApplicationException
	 */
	public static List getShinsaKekka2ndList()
					throws ApplicationException {

	   //サーバサービスの呼び出し
	   ISystemServise servise = SystemServiceFactory.getSystemService(
								   IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
	   List kekka2List = servise.getLabelList(ILabelKubun.KEKKA2);
	   return kekka2List;
	}

	/**
	 * 2次審査結果を取得する。ソートの値に関係なく、ラベル区分に該当するデータをすべて取得する。
	 * @return	2次審査結果
     * @throws ApplicationException
	 */
	public static List getAllShinsaKekka2ndList()
					throws ApplicationException {

	   //サーバサービスの呼び出し
	   ISystemServise servise = SystemServiceFactory.getSystemService(
								   IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
	   List kekka2List = servise.getAllLabelList(ILabelKubun.KEKKA2);
	   return kekka2List;
	}

	/**
	 * 2次審査結果コードに一致する表示名称を取得する。
	 * @param value	2次審査結果コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getShinsaKekka2ndList(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KEKKA2, value);
		String labelName = (String) recordMap.get("NAME");
		return labelName;
	}

	//---------------------------------------------------------------------
	// 受理結果
	//---------------------------------------------------------------------
	/**
	 * 受理結果を取得する。
	 * @return	受理結果
     * @throws ApplicationException
	 */
	public static List getJuriKekkaList() throws ApplicationException {
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List juriKekkaList = servise.getLabelList(ILabelKubun.JURI_KEKKA);
		return juriKekkaList;
	}

	/**
	 * 受理結果コードに一致する表示名称を取得する。
	 * @param value	受理結果コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getJuriKekkaList(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.JURI_KEKKA, value);
		String labelName = (String) recordMap.get("NAME");
		return labelName;
	}

	//---------------------------------------------------------------------
	// 表示選択（業務担当者向け・申請情報検索）
	//---------------------------------------------------------------------
	/**
	 * 申請情報検索の表示選択リストを取得する。
	 * @return	表示選択リスト
	 */
	public static List getShinseiHyojiSentakuList(){
		List hyojiSentakuList = new ArrayList();
		hyojiSentakuList.add(new LabelValueBean("研究種目毎に表示", "0"));
		//2005.08.19 iso 文言修正
//		hyojiSentakuList.add(new LabelValueBean("申請者毎に表示", "1"));	
		hyojiSentakuList.add(new LabelValueBean("応募者毎に表示", "1"));			
		hyojiSentakuList.add(new LabelValueBean("機関毎に表示", "2"));
		hyojiSentakuList.add(new LabelValueBean("系等の区分毎に表示", "3"));
		hyojiSentakuList.add(new LabelValueBean("推薦の観点毎に表示", "4"));
		hyojiSentakuList.add(new LabelValueBean("整理番号(学創用)順に表示", "5"));//2005/07/25 追加
		return hyojiSentakuList;
	}

	/**
	 * 表示選択に一致する表示名称を取得する。
	 * @param value	表示選択
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getShinseiHyojiSentakuList(String value) 
		throws ApplicationException {		
		return getLabel(getShinseiHyojiSentakuList(), value);
	}

	//---------------------------------------------------------------------
	// 共通
	//---------------------------------------------------------------------	
	/**
	 * リストより、選択された値に対する表示ラベルを取得する。
	 * formに設定されたコード情報より、選択さらた文字列を取得する。
	 * @param aList	LabelValueBeanのリスト
	 * @param value	選択された値を持つラベル
	 * @return
	 */
	public static String getLabel(List aList, String value) {
		for (Iterator iter = aList.iterator(); iter.hasNext();) {
			LabelValueBean element = (LabelValueBean) iter.next();
			if (element.getValue().equals(value)) {
				return element.getLabel();
			}
		}
		return "";
	}

	//---------------------------------------------------------------------
	// 表示方式（評価一覧検索）
	//---------------------------------------------------------------------
//	/**
//	 * 表示方式を取得する。
//	 * @return	表示方式リスト
//	 */
//	public static List getHyokaHyojiList()
//	{
//		List hyokaHyojiList = new ArrayList();
//		hyokaHyojiList.add(new LabelValueBean("評価順一覧", "1"));
//		hyokaHyojiList.add(new LabelValueBean("コメント一覧", "2"));
//		
//		return hyokaHyojiList;
//	}
//
//	/**
//	 * 表示方式コードに一致する表示名称を取得する。
//	 * @param value	表示方式コード
//	 * @return	表示名称
//	 */
//	public static String getHyokaHyojiList(String value) 
//		throws ApplicationException {		
//		return getLabel(getHyokaHyojiList(), value);
//	}

	/**
	 * 表示方式を取得する。
	 * @return	表示方式リスト
	 */
	public static List getHyokaHyojiList() {
        List hyokaHyojiList = new ArrayList();
        hyokaHyojiList.add(new LabelValueBean("点数順", "1"));
        hyokaHyojiList.add(new LabelValueBean("Aの多い順", "2"));

        return hyokaHyojiList;
    }

	/**
	 * 表示方式コードに一致する表示名称を取得する。
	 * @param value	表示方式コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getHyokaHyojiList(String value) 
		throws ApplicationException {		
		return getLabel(getHyokaHyojiList(), value);
	}

	//---------------------------------------------------------------------
	// 大文字の混在（パスワード発行ルール設定）
	//---------------------------------------------------------------------
	/**
	 * 大文字の混在を取得する。
	 * @return	大文字の混在リスト
	 */
	public static List getCharChk1List() {
		List charChk1 = new ArrayList();
		charChk1.add(new LabelValueBean("する", "0"));
		charChk1.add(new LabelValueBean("小文字のみ", "1"));
			
		return charChk1;
	}

	/**
	 * 大文字の混在に一致する表示名称を取得する。
	 * @param value	表示方式コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getCharChk1List(String value) 
		throws ApplicationException {		
		return getLabel(getCharChk1List(), value);
	}

	//---------------------------------------------------------------------
	// 数字の混在（パスワード発行ルール設定）
	//---------------------------------------------------------------------
	/**
	 * 数字の混在を取得する。
	 * @return	数字の混在リスト
	 */
	public static List getCharChk2List() {
		List charChk2 = new ArrayList();
		charChk2.add(new LabelValueBean("する", "0"));
		charChk2.add(new LabelValueBean("アルファベットのみ", "1"));
			
		return charChk2;
	}

	/**
	 * 数字の混在に一致する表示名称を取得する。
	 * @param value	表示方式コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getCharChk2List(String value) 
		throws ApplicationException {		
		return getLabel(getCharChk2List(), value);
	}

	//---------------------------------------------------------------------
	// 新規・更新（マスタ取込）
	//---------------------------------------------------------------------
	/**
	 * 新規・更新を取得する。
	 * @return	新規・更新リスト
	 */
	public static List getShinkiKoshinFlgList() {
        List sinkiKoshinFlgList = new ArrayList();
        sinkiKoshinFlgList.add(new LabelValueBean("新規", "0"));
        sinkiKoshinFlgList.add(new LabelValueBean("更新", "1"));

        return sinkiKoshinFlgList;
    }

	/**
	 * 新規・更新コードに一致する表示名称を取得する。
	 * @param value	新規・更新コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getShinkiKoshinFlgList(String value) 
		throws ApplicationException {		
		return getLabel(getShinkiKoshinFlgList(), value);
	}

	//---------------------------------------------------------------------
	// 推薦／非推薦（申請者登録）
	//---------------------------------------------------------------------
	/**
	 * 推薦／非推薦を取得する。
	 * @return	推薦／非推薦リスト
	 */
	public static List getHikoboFlgList() {
		List shuninFlgList = new ArrayList();
		shuninFlgList.add(new LabelValueBean("推薦", "1"));
		shuninFlgList.add(new LabelValueBean("非推薦", "0"));
		
		return shuninFlgList;
	}

	/**
	 * 推薦／非推薦コードに一致する表示名称を取得する。
	 * @param value	推薦／非推薦コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getHikoboFlgList(String value) 
			throws ApplicationException {		
		return getLabel(getHikoboFlgList(), value);
	}

	/**
	 * 研究計画最終年度前年度の応募リスト。
	 * isAsc:true時、応募しない／応募する。isAsc:false時、応募する／応募しない。
     * @param isAsc true:昇順、false:降順
	 * @return List
	 */
	public static List getZennendoOboList(boolean isAsc){
		List list = new ArrayList();
		//2005/8/18 by xiang
//		list.add(new LabelValueBean("該当しない", "2"));
//		list.add(new LabelValueBean("該当する", "1"));
// 2006/07/24 update start 理由：応募情報入力画面と応募情報検索画面の順序は違うです。
        if(isAsc){
            list.add(new LabelValueBean("応募する", "1"));
            list.add(new LabelValueBean("応募しない", "2"));
        }else{
            list.add(new LabelValueBean("応募しない", "2"));
            list.add(new LabelValueBean("応募する", "1"));
        }
// 2006/07/24 dyh update end
		return list;
	}

	/**
	 * 研究計画最終年度前年度の応募リスト(特別推進事業)。
	 * @return List
	 */
	public static List getZennendoOboListTokusui(){
		List list = new ArrayList();
		list.add(new LabelValueBean("該当しない", "2"));
		list.add(new LabelValueBean("該当する", "1"));
		return list;
	}
	
	/**
	 * 研究計画最終年度前年度の応募リスト番号に一致する表示名称を取得する。
	 * @param 研究計画最終年度前年度の応募リスト番号
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getZennendoOboList(String value) 
		throws ApplicationException {		
		return getLabel(getZennendoOboList(true), value);
	}

	/**
	 * 分担金の有無リスト。
	 * 有／無
	 * @return
	 */
	public static List getBuntankinList() {
        List list = new ArrayList();
        list.add(new LabelValueBean("有", "1"));
        list.add(new LabelValueBean("無", "2"));
        return list;
    }

	//2005/03/28 追加 ------------------------------ここから
	//理由 開示希望の有無追加のため
	/**
	 * 開示希望の有無リスト。
	 * 有／無
	 * @return
	 */
	public static List getKaijiKiboList() {
        List list = new ArrayList();
        list.add(new LabelValueBean("審査結果の開示を希望する", "1"));
        list.add(new LabelValueBean("審査結果の開示を希望しない", "2"));
        return list;
    }
	//2005/03/28 追加 ------------------------------ここまで

	//---------------------------------------------------------------------
	// 審査状況
	//---------------------------------------------------------------------
	/**
	 * 審査状況を取得する。
	 * @return	審査状況リスト
	 */
	public static List getShinsaJokyoList() {

		List shinsaJokyoList = new ArrayList();
		shinsaJokyoList.add(new LabelValueBean("すべて", "2"));
		shinsaJokyoList.add(new LabelValueBean("完了", "1"));
		shinsaJokyoList.add(new LabelValueBean("未完了", "0"));

		return shinsaJokyoList;
	}

	/**
	 * 審査状況コードに一致する表示名称を取得する。
	 * @param value	審査状況コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getShinsaJokyoList(String value) 
			throws ApplicationException {		
		return getLabel(getShinsaJokyoList(), value);
	}

//	//---------------------------------------------------------------------
//	// 非公募応募可フラグ
//	//---------------------------------------------------------------------
//	/**
//	 * 非公募応募可フラグを取得する。
//	 * @return	非公募応募可フラグリスト
//	 */
//	public static List getHikoboFlgList()
//		 throws ApplicationException {
//		//サーバサービスの呼び出し
//		ISystemServise servise = SystemServiceFactory.getSystemService(
//									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
//		List hikoboFlgList = servise.getLabelList(ILabelKubun.HIKOBO_FLG);
//		return hikoboFlgList;
//	}
//
//	/**
//	 * ラベル区分・値に一致する表示名称を取得する。
//	 * @param value	値
//	 * @return	表示名称
//	 */
//	public static String getHikoboFlgValue(String value) 
//		throws ApplicationException {		
//		return getLabel(getHikoboFlgList(), value);
//	}

	//---------------------------------------------------------------------
	// 総合評価（ABC）
	//---------------------------------------------------------------------
	/**
	 * 総合評価（ABC）を取得する。
	 * @return	総合評価（ABC）リスト
     * @throws ApplicationException
	 */
	public static List getKekkaAbcList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List sogoHyokaList = servise.getLabelList(ILabelKubun.KEKKA_ABC);
		return sogoHyokaList;
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getKekkaAbcName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KEKKA_ABC, value);
		String labelName = (String) recordMap.get("NAME");
		return labelName;
	}

	//---------------------------------------------------------------------
	// 総合評価（点数）
	//---------------------------------------------------------------------
	/**
	 * 総合評価（点数）を取得する。
	 * @return	総合評価（点数）リスト
     * @throws ApplicationException
	 */
	public static List getKekkaTenList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KEKKA_TEN);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getKekkaTenName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KEKKA_TEN, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 総合評価（点数）萌芽
	//---------------------------------------------------------------------
	/**
	 * 総合評価（点数）萌芽を取得する。
	 * @return	総合評価（点数）リスト
     * @throws ApplicationException
	 */
	public static List getKekkaTenHogaList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KEKKA_TEN_HOGA);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getKekkaTenHogaName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KEKKA_TEN_HOGA, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 研究内容
	//---------------------------------------------------------------------
	/**
	 * 研究内容を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getKenkyuNaiyoList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KENKYUNAIYO);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getKenkyuNaiyoName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KENKYUNAIYO, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 研究計画
	//---------------------------------------------------------------------
	/**
	 * 研究計画を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getKenkyuKeikakuList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KENKYUKEIKAKU);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getKenkyuKeikakuName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KENKYUKEIKAKU, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 適切性-海外
	//---------------------------------------------------------------------
	/**
	 * 適切性-海外を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getTekisetsuKaigaiList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.TEKISETSU_KAIGAI);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getTekisetsuKaigaiName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.TEKISETSU_KAIGAI, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 適切性-研究（1）
	//---------------------------------------------------------------------
	/**
	 * 適切性-研究（1）を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getTekisetsuKenkyu1List()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.TEKISETSU_KENKYU1);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getTekisetsuKenkyu1Name(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.TEKISETSU_KENKYU1, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 適切性
	//---------------------------------------------------------------------
	/**
	 * 適切性を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getTekisetsuList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.TEKISETSU);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getTekisetsuName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.TEKISETSU, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 妥当性
	//---------------------------------------------------------------------
	/**
	 * 妥当性を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getDatoList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.DATO);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getDatoName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.DATO, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 研究代表者
	//---------------------------------------------------------------------
	/**
	 * 研究代表者を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getShinseishaList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.SHINSEISHA);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getShinseishaName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.SHINSEISHA, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 研究分担者
	//---------------------------------------------------------------------
	/**
	 * 研究分担者を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getKenkyuBuntanshaList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KENKYUBUNTANSHA);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getKenkyuBuntanshaName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KENKYUBUNTANSHA, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// ヒトゲノム
	//---------------------------------------------------------------------
	/**
	 * ヒトゲノムを取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getHitogenomuList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.HITOGENOMU);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getHitogenomuName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.HITOGENOMU, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 特定胚
	//---------------------------------------------------------------------
	/**
	 * 特定胚を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getTokuteiList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.TOKUTEI);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getTokuteiName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.TOKUTEI, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// ヒトES細胞
	//---------------------------------------------------------------------
	/**
	 * ヒトES細胞を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getHitoEsList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.HITOES);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getHitoEsName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.HITOES, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 遺伝子組換え実験
	//---------------------------------------------------------------------
	/**
	 * 遺伝子組換え実験を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getKumikaeList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.KUMIKAE);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getKumikaeName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.KUMIKAE, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 遺伝子治療臨床研究
	//---------------------------------------------------------------------
	/**
	 * 遺伝子治療臨床研究を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getChiryoList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.CHIRYO);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getChiryoName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.CHIRYO, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 疫学研究
	//---------------------------------------------------------------------
	/**
	 * 疫学研究を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getEkigakuList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.EKIGAKU);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getEkigakuName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.EKIGAKU, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 利害関係
	//---------------------------------------------------------------------
	/**
	 * 利害関係を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getRigaiList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.RIGAI);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getRigaiName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.RIGAI, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 学術的重要性・妥当性
	//---------------------------------------------------------------------
	/**
	 * 学術的重要性・妥当性を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getJuyoseiList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.JUYOSEI);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getJuyoseiName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.JUYOSEI, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 独創性・革新性
	//---------------------------------------------------------------------
	/**
	 * 独創性・革新性を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getDokusoseiList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.DOKUSOSEI);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getDokusoseiName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.DOKUSOSEI, value);
		return (String) recordMap.get("NAME");
	}		

	//---------------------------------------------------------------------
	// 波及効果・普遍性
	//---------------------------------------------------------------------
	/**
	 * 波及効果・普遍性を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getHakyukokaList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.HAKYUKOKA);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getHakyukokaName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.HAKYUKOKA, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 遂行能力・環境の適切性
	//---------------------------------------------------------------------
	/**
	 * 遂行能力・環境の適切性を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getSuikonoryokuList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.SUIKONORYOKU);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getSuikonoryokuName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.SUIKONORYOKU, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 人権の保護・法令等の遵守
	//---------------------------------------------------------------------
	/**
	 * 人権の保護・法令等の遵守を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getJinkenList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.JINKEN);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getJinkenName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.JINKEN, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 分担金配分の遵守
	//---------------------------------------------------------------------
	/**
	 * 分担金配分を取得する。
	 * @return	研究内容リスト
     * @throws ApplicationException
	 */
	public static List getBuntankinhaibunList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		return servise.getLabelList(ILabelKubun.BUNTANKIN);
	}

	/**
	 * ラベル区分・値に一致する表示名称を取得する。
	 * @param value	値
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String  getBuntankinhaibunName(String value) 
		throws ApplicationException {		
		//サーバサービスの呼び出し		
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		Map recordMap = servise.selectRecord(ILabelKubun.BUNTANKIN, value);
		return (String) recordMap.get("NAME");
	}

	//---------------------------------------------------------------------
	// 推薦の観点
	//---------------------------------------------------------------------
	/**
	 * 推薦の観点を取得する。（申請情報検索）
	 * @return	推薦の観点リスト
     * @throws ApplicationException
	 */
	public static List getKantenList()
		 throws ApplicationException {
		//サーバサービスの呼び出し
		ISystemServise servise = SystemServiceFactory.getSystemService(
									IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
		List kantenList = servise.getLabelList(ILabelKubun.SUISEN);
		return kantenList;
	}

	//---------------------------------------------------------------------
	// 事業区分
	//---------------------------------------------------------------------
	/**
	 * 事業区分（審査員）を取得する。（まだ使わないでbyテラ）
     * @param userInfo ユーザ情報
	 * @return	事業区分リスト
     * @throws ApplicationException
	 */
	public static List getJigyoKubunList(UserInfo userInfo)
		 throws ApplicationException {

		List jigyoKubunList = new ArrayList();

		//業務担当者の担当事業区分を取得
		Set set = userInfo.getGyomutantoInfo().getTantoJigyoKubun();
		//学術創成（非公募）が含まれていれば
		if(set.contains(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
			jigyoKubunList.add(new LabelValueBean("学術創成研究費", "1"));			
		}
		//基盤研究が含まれていれば
		if(set.contains(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
			jigyoKubunList.add(new LabelValueBean("基盤研究等", "4"));			
		}
		
		return jigyoKubunList;
	}

	//---------------------------------------------------------------------
	// 表示方式（所属機関向け／システム管理者向け・申請情報検索）
	//---------------------------------------------------------------------
	/**
	 * 申請書の表示方式を取得する。
	 * @return	表示方式リスト
	 */
	public static List getShinseishoHyojiList() {
		List shinseishoHyojiList = new ArrayList();
		shinseishoHyojiList.add(new LabelValueBean("研究種目毎に表示", "1"));
		//2005.08.19 iso 文言修正
//		shinseishoHyojiList.add(new LabelValueBean("申請者毎に表示", "2"));
		shinseishoHyojiList.add(new LabelValueBean("応募者毎に表示", "2"));
		
		return shinseishoHyojiList;
	}

	/**
	 * 表示方式コードに一致する表示名称を取得する。
	 * @param value	表示方式コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getShinseishoHyojiList(String value) 
			throws ApplicationException {		
		return getLabel(getShinseishoHyojiList(), value);
	}

	//---------------------------------------------------------------------
	// 削除フラグ（システム管理者向け・申請情報検索）
	//---------------------------------------------------------------------
	/**
	 * 削除フラグを取得する。
	 * @return	表示方式リスト
	 */
	public static List getDelFlgList() {
		List deleteFlgList = new ArrayList();
		deleteFlgList.add(new LabelValueBean("削除データを除く", "1"));
		deleteFlgList.add(new LabelValueBean("削除データを含む", "2"));
		
		return deleteFlgList;
	}

//	 2005/04/21 追加 ここから--------------------------------------------------
//	 理由 所属機関情報検索で部局担当者の条件項目追加
	//---------------------------------------------------------------------
	// 削除フラグ（システム管理者向け・所属機関情報検索）
	//---------------------------------------------------------------------
	/**
	 * 削除フラグを取得する。
	 * @return	検索条件フラグリスト
	 */
	public static List getBukyokuSearchFlgList() {
        List flgList = new ArrayList();
        flgList.add(new LabelValueBean("すべて", "0"));
        flgList.add(new LabelValueBean("削除データのみ", "1"));

        return flgList;
    }
//	 追加 ここまで---------------------------------------------------------------

	//---------------------------------------------------------------------
	// 表示方式（基盤研究評価一覧検索）
	//---------------------------------------------------------------------
	/**
	 * 表示方式を取得する。
	 * @return	表示方式リスト
	 */
	public static List getHyokaHyojiListKiban() {
		List hyokaHyojiListKiban = new ArrayList();
		hyokaHyojiListKiban.add(new LabelValueBean("評価順一覧", "1"));
		hyokaHyojiListKiban.add(new LabelValueBean("コメント一覧", "2"));
		
		return hyokaHyojiListKiban;
	}

	/**
	 * 表示方式コードに一致する表示名称を取得する。
	 * @param value	表示方式コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getHyokaHyojiListKiban(String value) 
			throws ApplicationException {		
		return getLabel(getHyokaHyojiListKiban(), value);
	}

	/**
	 * 削除フラグコードに一致する表示名称を取得する。
	 * @param value	表示方式コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getDelFlgList(String value) 
			throws ApplicationException {		
		return getLabel(getDelFlgList(), value);
	}

	//2005/04/15 追加 ここから----------------------------------------------
	//理由 研究者登録で使用する学位と性別のリストを追加
	//---------------------------------------------------------------------
	// 学位リスト
	//---------------------------------------------------------------------
	/** 
	 * 学位を取得する。
	 * @return	表示方式リスト
	 */
	public static List getGakuiList() {
		List deleteFlgList = new ArrayList();
		deleteFlgList.add(new LabelValueBean("修士", "10"));
		deleteFlgList.add(new LabelValueBean("博士", "11"));
			
		return deleteFlgList;
	}

	/**
	 * 学位を取得する。
	 * @param value	表示方式コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getGakuiList(String value) 
		throws ApplicationException {		
			return getLabel(getGakuiList(), value);
	}

	//---------------------------------------------------------------------
	// 性別リスト
	//---------------------------------------------------------------------
	/** 性別を取得する。
	 * @return	表示方式リスト
	 */
	public static List getSeibetsuList() {
		List deleteFlgList = new ArrayList();
		deleteFlgList.add(new LabelValueBean("男", "1"));
		deleteFlgList.add(new LabelValueBean("女", "2"));
			
		return deleteFlgList;
	}

	/**
	 * 性別を取得する。
	 * @param value	表示方式コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getSeibetsuList(String value) 
		throws ApplicationException {		
			return getLabel(getSeibetsuList(), value);
	}
	//追加 ここまで---------------------------------------------------------
	
	//2005/04/21 追加 ここから----------------------------------------------
	//理由 業務担当者のチェックリスト検索で使用する受理状況のリストを追加
	/** 受理状況を取得する。
	 * 
	 * @return	表示方式リスト
	 */
	public static List getJuriJokyoList() {
		List juriList = new ArrayList();
		juriList.add(new LabelValueBean("すべて", "0"));
		juriList.add(new LabelValueBean("受理済み", "06"));
		juriList.add(new LabelValueBean("受理前", "04"));
		juriList.add(new LabelValueBean("確定解除", "03"));
// 20050719 不受理を追加
		juriList.add(new LabelValueBean("不受理", "07"));
// Horikoshi
		return juriList;
	}
    
//  2006/06/14 李万軍追加 ここから----------------------------------------------
    /** 受理状況を取得する。
     * 
     * @return  表示方式リスト
     */
    public static List getJuriJokyoList2() {
        List juriList = new ArrayList();
        juriList.add(new LabelValueBean("すべて", "0"));
        juriList.add(new LabelValueBean("受理済み", "06"));
        juriList.add(new LabelValueBean("受理前", "04"));
        juriList.add(new LabelValueBean("承認解除", "03"));
        
        return juriList;
    }
//  2006/06/14 追加 ここまで----------------------------------------------
    
	/**
	 * 受理状況を取得する。
	 * @param value	表示方式コード
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getJuriJokyoList(String value)
            throws ApplicationException {		
        return getLabel(getJuriJokyoList(), value);
	}
	//追加 ここまで---------------------------------------------------------

// 20050526 Start 特定領域のために追加
	//研究領域区分　※
    /**
     * 研究領域区分(特定領域)に一致する表示名称を取得する。
     * @param value　    研究領域区分
     * @return  表示名称
     * @throws ApplicationException
     */
	public static List getKenkyuKubunList() throws ApplicationException {

        List kenkyuKubunList = new ArrayList();
        kenkyuKubunList.add(new LabelValueBean(IShinseiMaintenance.NAME_KEIKAKU,
                IShinseiMaintenance.KUBUN_KEIKAKU));
        kenkyuKubunList.add(new LabelValueBean(IShinseiMaintenance.NAME_KOUBO,
                IShinseiMaintenance.KUBUN_KOUBO));
        kenkyuKubunList.add(new LabelValueBean(IShinseiMaintenance.NAME_SHUURYOU,
                IShinseiMaintenance.KUBUN_SHUURYOU));

        return kenkyuKubunList;
    }

	/**
	 * 研究領域区分に一致する表示名称を取得する。
     * @param value 研究領域区分
	 * @return	表示名称
     * @throws ApplicationException
	 */
	public static String getKenkyuKubunList(String value) 
            throws ApplicationException {
		return getLabel(getKenkyuKubunList(), value);
	}
// Horikoshi End

// 20050713 受理、不受理機能のために追加
    /**
     * 受理、不受理機能のために追加
     * @return  List
     * @throws ApplicationException
     */
	public static List getJuriFujuriList(){
		List lstJuriFujuri = new ArrayList();
		lstJuriFujuri.add(new LabelValueBean("受理",	"06"));
		lstJuriFujuri.add(new LabelValueBean("不受理", "07"));
		return lstJuriFujuri;
	}
// Horikoshi

	/**
	 * 調整班のリスト。
	 * 有／無
	 * @return
	 */
	public static List getChouseiList() {
        List list = new ArrayList();
        list.add(new LabelValueBean("調整班", "1"));
        return list;
    }

//	最終ログイン日を追加
    /**
     * 最終ログイン日の有無リスト。
     * 有／無
     * 
     * @return
     */
    public static List getLoginDateList() {
        List list = new ArrayList();
        list.add(new LabelValueBean("有", "1"));
        list.add(new LabelValueBean("無", "2"));
        return list;
    }

//	2005/10/25 追加 ここから--------------------------------------------------
	//---------------------------------------------------------------------
	// 利害関係者
	//---------------------------------------------------------------------
	/**
	 * 利害関係者を取得する。
	 * @return	利害関係者リスト
	 */
	public static List getRigaiKankeishaList() {

		List rigaiKankeishaList = new ArrayList();
		rigaiKankeishaList.add(new LabelValueBean("利害関係者のみ", "1"));
		rigaiKankeishaList.add(new LabelValueBean("利害関係者を除く", "0"));

		return rigaiKankeishaList;
	}
	//追加 ここまで---------------------------------------------------------

//	2005/10/27 追加 ここから--------------------------------------------------
	//---------------------------------------------------------------------
	// メールフラグ
	//---------------------------------------------------------------------
	/**
	 * メールフラグを取得する。
	 * @return	メールフラグリスト
	 */
	public static List getMailFlgList() {

		List mailFlgList = new ArrayList();
		mailFlgList.add(new LabelValueBean("登録する", "0"));
		mailFlgList.add(new LabelValueBean("登録しない", "1"));

		return mailFlgList;
	}
	//追加 ここまで---------------------------------------------------------

//審査状況検索「表示方式」を追加	2005/11/1
    //---------------------------------------------------------------------
    // 表示方式（審査状況一覧検索）
    //---------------------------------------------------------------------
    /**
     * 表示方式を取得する。
     * 
     * @return 表示方式リスト
     */
    public static List getHyokaHyojiListShinsaJokyo() {
        List hyokaHyojiListShinsaJokyo = new ArrayList();
        hyokaHyojiListShinsaJokyo.add(new LabelValueBean("審査状況一覧", "1"));
        hyokaHyojiListShinsaJokyo.add(new LabelValueBean("審査件数一覧", "2"));

        return hyokaHyojiListShinsaJokyo;
    }

    /**
     * 表示方式コードに一致する表示名称を取得する。
     * 
     * @param value 表示方式コード
     * @return 表示名称
     * @throws ApplicationException
     */
    public static String getHyokaHyojiListShinsaJokyo(String value)
            throws ApplicationException {
        return getLabel(getHyokaHyojiListShinsaJokyo(), value);
    }

	/**
	 * 値に一致するラベルを取得する。
	 * 値に一致するラベルがない場合、空文字を返す。
	 * @param labelList	ラベルリスト
	 * @param atai	値
	 * @return	表示ラベル
     * @throws ApplicationException
	 */
	public static String getlabelName(List labelList, String atai)
			throws ApplicationException {
		String labelName = "";
		for(Iterator itaretor = labelList.iterator(); itaretor.hasNext();) {
			LabelValueBean labelValueBean = (LabelValueBean)itaretor.next();
			if(!StringUtil.isBlank(atai) && atai.equals(labelValueBean.getValue())) {
				labelName = labelValueBean.getLabel();
			}
		}
		return labelName;
	}

	/**
	 * 値リストに一致するラベルを取得する。
	 * 値に一致するラベルがない場合、空文字を返す。
	 * 引数が不正の場合、空リストを返す。
	 * @param labelList	ラベルリスト
	 * @param values	値リスト
	 * @return	表示ラベルリスト
     * @throws ApplicationException
	 */
	public static List getlabelName(List labelList, List values)
			throws ApplicationException {
		return getlabelName(labelList, values, null, null);
	}

	/**
	 * 値リストに一致するラベルを取得する。
	 * 値リストで第3引数(その他コード)一致する値がある場合、第4引数を返す。
	 * 値に一致するラベルがない場合、空文字を返す。
	 * 引数が不正の場合、空リストを返す。
	 * @param labelList	ラベルリスト
	 * @param values	値リスト
     * @param other 
     * @param otherValue 
	 * @return	表示ラベルリスト
     * @throws ApplicationException
	 */
	public static List getlabelName(List labelList, List values, String other, String otherValue)
			throws ApplicationException {
		List labelNameList = new ArrayList();
		if(labelList.isEmpty() || values.isEmpty()) {
			return labelNameList;
		}
		for(Iterator iterator = values.iterator(); iterator.hasNext();) {
			String value = iterator.next().toString();
			if(!StringUtil.isBlank(other) && other.equals(value)) {
				labelNameList.add(getlabelName(labelList, value, other, otherValue.replaceAll("　", "").trim()));
			} else {
				labelNameList.add(getlabelName(labelList, value));
			}
		}
		return labelNameList;
	}

	/**
	 * 値に一致するラベルを取得する。
	 * 第2引数が第3引数(その他コード)と一致した場合は、第4引数を返す。
	 * 値に一致するラベルがない場合、空文字を返す。
	 * @param labelList	ラベルリスト
	 * @param atai	値
	 * @param other	その他コード
     * @param otherValue
	 * @param value	その他の時の返り値
	 * @return	表示ラベル
     * @throws ApplicationException
	 */
	public static String getlabelName(List labelList, String atai, String other, String otherValue)
			throws ApplicationException {
		if(!StringUtil.isBlank(atai) && !StringUtil.isBlank(other)
				&& atai.equals(other)) {
			return StringUtil.defaultString(otherValue.replaceAll("　", "").trim());
		} else {
			return getlabelName(labelList, atai);
		}
	}
//2007/02/08 苗　削除ここから　使用しない
//	2006/02/15  Start
//	/**
//	 * 審査希望分野を取得する。
//	 * @return	審査希望分野
//     * @throws ApplicationException
//	 */
//	public static List getKiboRyoikiList() throws ApplicationException {
//        // サーバサービスの呼び出し
//        ISystemServise servise = SystemServiceFactory
//                .getSystemService(IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
//
//        List jigyoList = servise.getKiboRyoikiList();
//        return jigyoList;
//    }

//    /**
//     * 審査希望分野コードに一致する表示名称を取得する。
//     * @param value	事業名コード
//     * @return	表示名称
//     * @throws ApplicationException
//     */
//    public static String getKiboRyoikiList(String value) 
//    	throws ApplicationException {		
//    	return getLabel(getKiboRyoikiList(), value);
//    }
// syuu  End
//2007/02/08 苗　削除ここまで　使用しない

//2006/06/02 苗　追加ここから
    /**
     * 事業名リスト（事業（基盤S,A,B）の削除）を取得する。
     * 渡されたUserInfoが業務担当者の場合は、
     * 自分の担当する事業CD・事業区分のみの事業名リストが返る
     * @param userInfo
     * @param jigyoKubun
     * @return
     * @throws ApplicationException
     */
    public static List getJigyoNameListByJigyoCdsWithoutKikanSAB(UserInfo userInfo, String jigyoKubun)
            throws ApplicationException {
        
        //サーバサービスの呼び出し
        ISystemServise servise = SystemServiceFactory.getSystemService(
                                    IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
        // 完全電子化基盤の事業CDの格納（基盤S,A,Bなし）
        String[] jigyoCdsKikan = {
                IJigyoCd.JIGYO_CD_KIBAN_C_IPPAN,
                IJigyoCd.JIGYO_CD_KIBAN_C_KIKAKU, 
                //DEL START 2007/07/02 BIS　趙一非
                //研究種目名に完全電子化事業（特定（継続）、萌芽）を追加
                //H19完全電子化カスタマイズ対応
                //IJigyoCd.JIGYO_CD_KIBAN_HOGA,//萌芽研究
                //DEL end 2007/07/02 BIS　趙一非
                IJigyoCd.JIGYO_CD_KIBAN_WAKATE_A,
                IJigyoCd.JIGYO_CD_KIBAN_WAKATE_B};
        List jigyoList = servise.getJigyoNameListByJigyoCds(userInfo, jigyoCdsKikan, jigyoKubun);      
        return jigyoList;
    }

    //2006/06/06 jzx　add start
//  /**
//   * 事業名リストを取得する。
//   * 渡されたUserInfoが業務担当者の場合は、
//   * 自分の担当する事業のみの事業名リストが返る。
//   * @param userInfo
//   * @return
//   * @throws ApplicationException
//   */
//  public static List getJigyoNameList2(UserInfo userInfo)
//      throws ApplicationException {
//      return getJigyoNameListByJigyoCds2(userInfo);
//  }

    /**
     * 事業名リストを取得する。
     * 渡されたUserInfoが業務担当者の場合は、
     * 自分の担当する事業のみの事業名リストが返る
     * @param userInfo
     * @return
     * @throws ApplicationException
     */
    public static List getJigyoNameListByJigyoCds2(UserInfo userInfo)
        throws ApplicationException {
        //サーバサービスの呼び出し
        ISystemServise servise = SystemServiceFactory.getSystemService(
                                    IServiceName.LABEL_VALUE_MAINTENANCE_SERVICE);
        String[] validJigyoCds = new String[]{
                IJigyoCd.JIGYO_CD_TOKUSUI,        //特別推進研究
                
               // ADD START 2007/07/02 趙一非
                //研究種目名に完全電子化事業（特定（継続）、萌芽）を追加
                //H19完全電子化カスタマイズ対応
                IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU,//特定領域研究（継続領域）
                // ADD END 2007/07/02 趙一非
                IJigyoCd.JIGYO_CD_KIBAN_S,        //基盤研究(S)
                IJigyoCd.JIGYO_CD_KIBAN_A_IPPAN,  //基盤研究(A)(一般)
                IJigyoCd.JIGYO_CD_KIBAN_A_KAIGAI, //基盤研究(A)(海外学術調査)
                IJigyoCd.JIGYO_CD_KIBAN_B_IPPAN,  //基盤研究(B)(一般)
                IJigyoCd.JIGYO_CD_KIBAN_B_KAIGAI, //基盤研究(B)(海外学術調査)
                // ADD START 2007/07/02 BIS　趙一非
                //研究種目名に完全電子化事業（特定（継続）、萌芽）を追加
                //H19完全電子化カスタマイズ対応
                IJigyoCd.JIGYO_CD_KIBAN_HOGA,	  //萌芽研究
                // ADD END 2007/07/02 BIS　趙一非
//2007/02/07 苗　追加ここから             
                IJigyoCd.JIGYO_CD_KIBAN_WAKATE_S, //若手研究(S)
                IJigyoCd.JIGYO_CD_WAKATESTART,    //若手研究（スタートアップ）
                IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A, //特別研究促進費（基盤研究(A)相当）
                IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B, //特別研究促進費（基盤研究(B)相当）
                IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C, //特別研究促進費（基盤研究(C)相当）
                IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A,//特別研究促進費（若手研究(A)相当）
                IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B,//特別研究促進費（若手研究(B)相当）
//2007/02/07　苗　追加ここまで
                IJigyoCd.JIGYO_CD_GAKUSOU_HIKOUBO,//学術創成研究費（推薦分）
                IJigyoCd.JIGYO_CD_GAKUSOU_KOUBO};//学術創成研究費（募集分）
                
                
                
        List jigyoList = servise.getJigyoNameListByJigyoCds(userInfo,validJigyoCds,"");
        return jigyoList;
    }
    //2006/06/06 jzx　add end
    
    //2006/06/21 lwj add begin
    /**
     * 研究領域最終年度前年度の応募（該当の有無）を取得する。
     * 
     * @return 研究領域最終年度前年度の応募（該当の有無）リスト
     */
    public static List getNenduYomuList() {
        List nenduYomuList = new ArrayList();
        nenduYomuList.add(new LabelValueBean("有", "1"));
        nenduYomuList.add(new LabelValueBean("無", "2"));

        return nenduYomuList;
    }
    //2006/06/21 lwj add end  
}