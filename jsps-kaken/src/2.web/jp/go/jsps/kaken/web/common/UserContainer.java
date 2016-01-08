/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : UserContainer.java
 *    Description : セッションで使用する情報を格納するためのコンテナクラス
 *
 *    Author      : 
 *    Date        : 
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *                  V1.0                       新規作成
 *    2006/06/14    V1.1        DIS            修正
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.CheckListInfo;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.model.vo.PunchDataKanriInfo;
import jp.go.jsps.kaken.model.vo.QuestionInfo;
import jp.go.jsps.kaken.model.vo.RyoikiKeikakushoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.model.vo.TeishutsuShoruiSearchInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.model.vo.WarifuriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * セッションで使用する情報を格納するためのコンテナクラス。
 * 
 */
public class UserContainer implements HttpSessionBindingListener {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログクラス。 */
	private static final Log log = LogFactory.getLog(UserContainer.class);

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** ユーザ情報 */
	private UserInfo userInfo  = null;
	
	/** 登録更新用 申請者情報 */
	private ShinseishaInfo shinseishaInfo = null;
	
	/** 登録更新用 所属機関情報 */
	private ShozokuInfo shozokuInfo = null;
	
	/** 登録更新用 事業管理情報 */
	private JigyoKanriInfo jigyoKanriInfo = null;
	
	/** 登録用 書類管理情報 */
	private ShoruiKanriInfo shoruiKanriInfo = null;
	
	/** 表示用 書類管理情報リスト */
	private List shoruiKanriList = null;
	
	/** 登録更新用 業務担当者情報 */
	private GyomutantoInfo gyomutantoInfo = null;
	
	/** 登録更新用 審査員情報 */
	private ShinsainInfo shinsainInfo = null;
	
	/** 簡易申請情報 */
	private SimpleShinseiDataInfo simpleShinseiDataInfo = null;
	
	/** 登録更新用 審査結果入力情報 */
	private ShinsaKekkaInputInfo shinsaKekkaInputInfo = null;

	/** 審査員割り振り情報 */
	private WarifuriInfo warifuriInfo = null;
	
	/** 表示用 1次審査結果情報（参照用） */
	private ShinsaKekkaReferenceInfo shinsaKekkaReferenceInfo = null;

	/** 表示用 2次審査結果情報（2次審査結果入力用） */
	private ShinsaKekka2ndInfo shinsaKekka2ndInfo = null;
	
	/** 検索条件保持用 申請データ検索情報 */
	private ShinseiSearchInfo shinseiSearchInfo = null;

	/** パンチデータ情報 */
	private PunchDataKanriInfo punchDataKanriInfo = null;
		
	/** 表示用 総合評点情報 */
	private Map sogoHyotenInfo = null;

	/** 審査状況情報 */
	private ShinsaJokyoInfo shinsaJokyoInfo = null;
	
	// 2005/04/07 追加ここから----------------------------------------------
	// 理由 部局担当者情報保持のため	
	
	/** 部局担当者情報 */
	private BukyokutantoInfo bukyokutantoInfo = null;
	
	// 追加 ここまで--------------------------------------------------------
	
	// 2005/04/15 追加 ここから---------------------------------------------
	// 理由 チェックリスト情報保持のため
	
	/** チェックリスト情報 */
	private CheckListInfo checkListInfo = null;

    /** チェックリスト検索情報 */
	private CheckListSearchInfo checkListSearchInfo = null;
	
	// 追加 ここまで--------------------------------------------------------
	
	//2005/04/15 追加 ここから----------------------------------------------
	//理由 研究者情報保持のため
	
	/** 研究者情報 */
	private KenkyushaInfo kenkyushaInfo = null;
	
	//追加 ここまで---------------------------------------------------------
	
	//2005/04/20 追加 ここから----------------------------------------------
	//理由 申請者検索条件保持のため
	
	/** 申請者検索情報 */
	private ShinseishaSearchInfo shinseishaSearchInfo = null;
	
	//追加 ここまで---------------------------------------------------------
	
	/** アンケート情報 */
	private QuestionInfo questionInfo = null;
			
	//2005/04/29 追加 ----------------------------------------------ここから
	//理由 一括再設定用の研究者ID格納用に追加
    /** 研究者IDリスト */
	private String[] kenkyuNo = null;
	
	//2005/04/29 追加 ----------------------------------------------ここまで

    // 20060605 Wang Xiancheng add start
    /** 申請情報リスト */
    private SimpleShinseiDataInfo[] simpleShinseiDataInfos = null;
    // 20060605 Wang Xiancheng add start

	//2006/06/14 by jzx add start
	/** 領域計画書情報 */
	private RyoikiKeikakushoInfo ryoikikeikakushoInfo = null;
    //2006/06/14 by jzx add end
    
    //2006/06/15 by mcj add start
    /** 領域計画書情報 */
    private TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo = null;
    //2006/06/15 by mcj add end
    
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public UserContainer() {
		super();
	}

	//---------------------------------------------------------------------
	// Implementation of HttpSessionBindingListener interface
	//---------------------------------------------------------------------

	/* (非 Javadoc)
	 * @see javax.servlet.http.HttpSessionBindingListener#valueBound(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void valueBound(HttpSessionBindingEvent e) {
		
	}

	/* (非 Javadoc)
	 * @see javax.servlet.http.HttpSessionBindingListener#valueUnbound(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void valueUnbound(HttpSessionBindingEvent e) {
		
	}
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * ログインユーザ情報を取得する。	
	 * @return	ログインユーザ情報
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * ログインユーザ情報をセットする。
	 * @param info	ログインユーザ情報
	 */
	public void setUserInfo(UserInfo info) {
		userInfo = info;
	}

	/**
	 * 登録更新申請者情報を取得する。
	 * @return	登録更新申請者情報
	 */
	public ShinseishaInfo getShinseishaInfo() {
		return shinseishaInfo;
	}

	/**
	 * 登録更新申請者情報をセットする。
	 * @param info	登録更新申請者情報
	 */
	public void setShinseishaInfo(ShinseishaInfo info) {
		shinseishaInfo = info;
	}

	/**
	 * 登録更新所属機関情報をセットする。
	 * @param info	登録更新所属機関情報
	 */
	public void setShozokuInfo(ShozokuInfo info) {
		shozokuInfo = info;
	}

	/**
	 * 登録更新所属機関情報を取得する。
	 * @return	登録更新所属機関情報
	 */
	public ShozokuInfo getShozokuInfo() {
		return shozokuInfo;
	}
	
	/**
	 * 登録更新事業管理情報をセットする。
	 * @param info	登録更新事業管理情報
	 */
	public void setJigyoKanriInfo(JigyoKanriInfo info) {
		jigyoKanriInfo = info;
	}

	/**
	 * 登録更新事業管理情報を取得する。
	 * @return	登録更新事業管理情報
	 */
	public JigyoKanriInfo getJigyoKanriInfo() {
		return jigyoKanriInfo;
	}

	/**
	 * 登録書類管理情報をセットする。
	 * @param info	登録書類管理情報
	 */
	public void setShoruiKanriInfo(ShoruiKanriInfo info) {
		shoruiKanriInfo = info;
	}

	/**
	 * 登録書類管理情報を取得する。
	 * @return	登録書類管理情報
	 */
	public ShoruiKanriInfo getShoruiKanriInfo() {
		return shoruiKanriInfo;
	}


	/**
	 * 表示書類管理情報リストを取得する。
	 * @return list	登録書類管理情報リスト
	 */
	public List getShoruiKanriList() {
		return shoruiKanriList;
	}

	/**
	 * 表示書類管理情報リストをセットする。
	 * @param list 登録書類管理情報リスト
	 */
	public void setShoruiKanriList(List list) {
		shoruiKanriList = list;
	}

	/**
	 * 登録更新業務担当者情報を取得する。
	 * @return 登録更新業務担当者情報
	 */
	public GyomutantoInfo getGyomutantoInfo() {
		return gyomutantoInfo;
	}

	/**
	 * 登録更新業務担当者情報をセットする。
	 * @param info	登録更新業務担当者情報
	 */
	public void setGyomutantoInfo(GyomutantoInfo info) {
		gyomutantoInfo = info;
	}

	/**
	 * 登録更新審査員情報を取得する。
	 * @return	登録更新審査員情報
	 */
	public ShinsainInfo getShinsainInfo() {
		return shinsainInfo;
	}

	/**
	 * 登録更新審査員情報をセットする。
	 * @param info	登録更新審査員情報
	 */
	public void setShinsainInfo(ShinsainInfo info) {
		shinsainInfo = info;
	}

	/**
	 * 簡易申請情報を取得する。
	 * @return 簡易申請情報
	 */
	public SimpleShinseiDataInfo getSimpleShinseiDataInfo() {
		return simpleShinseiDataInfo;
	}

	/**
	 * 簡易申請情報をセットする。
	 * @param info 簡易申請情報
	 */
	public void setSimpleShinseiDataInfo(SimpleShinseiDataInfo info) {
		simpleShinseiDataInfo = info;
	}

	/**
	 * 審査結果入力情報を取得する。
	 * @return
	 */
	public ShinsaKekkaInputInfo getShinsaKekkaInputInfo() {
		return shinsaKekkaInputInfo;
	}

	/**
	 * 審査結果入力情報をセットする。
	 * @param info 審査結果入力情報
	 */
	public void setShinsaKekkaInputInfo(ShinsaKekkaInputInfo info) {
		shinsaKekkaInputInfo = info;
	}

	/**
	 * 審査員割り振り情報を取得する。
	 * @return
	 */
	public WarifuriInfo getWarifuriInfo() {
		return warifuriInfo;
	}

	/**
	 * 審査員割り振り情報をセットする。
	 * @param info 審査員割り振り情報
	 */
	public void setWarifuriInfo(WarifuriInfo info) {
		warifuriInfo = info;
	}

	/**
	 *  1次審査結果情報（参照用）オブジェクトを取得する。
	 * @return
	 */
	public ShinsaKekkaReferenceInfo getShinsaKekkaReferenceInfo() {
		return shinsaKekkaReferenceInfo;
	}

	/**
	 *  1次審査結果情報（参照用）オブジェクトをセットする。
	 * @param info 1次審査結果情報（参照用）オブジェクト
	 */
	public void setShinsaKekkaReferenceInfo(ShinsaKekkaReferenceInfo info) {
		shinsaKekkaReferenceInfo = info;
	}

	/**
	 * 2次審査結果情報（2次審査結果入力用）オブジェクトを取得する。
	 * @return
	 */
	public ShinsaKekka2ndInfo getShinsaKekka2ndInfo() {
		return shinsaKekka2ndInfo;
	}

	/**
	 * 2次審査結果情報（2次審査結果入力用）オブジェクトをセットする。
	 * @param info 2次審査結果情報（2次審査結果入力用）オブジェクト
	 */
	public void setShinsaKekka2ndInfo(ShinsaKekka2ndInfo info) {
		shinsaKekka2ndInfo = info;
	}

	/**
	 * 申請データ検索情報（検索データ保持用）オブジェクトを取得する。
	 * @return
	 */
	public ShinseiSearchInfo getShinseiSearchInfo() {
		return shinseiSearchInfo;
	}

	/**
	 * 申請データ検索情報（検索データ保持用）オブジェクトをセットする。
	 * @param info 2次審査結果情報（2次審査結果入力用）オブジェクト
	 */
	public void setShinseiSearchInfo(ShinseiSearchInfo info) {
		shinseiSearchInfo = info;
	}
	
	/**
     * パンチデータ情報を取得する。
	 * @return パンチデータ情報
	 */
	public PunchDataKanriInfo getPunchDataKanriInfo() {
		return punchDataKanriInfo;
	}

	/**
     * パンチデータ情報をセットする。
	 * @param info パンチデータ情報
	 */
	public void setPunchDataKanriInfo(PunchDataKanriInfo info) {
		punchDataKanriInfo = info;
	}
	
	/**
	 * 総合評点リスト情報オブジェクトを取得する。
	 * @return
	 */
	public Map getSogoHyotenInfo() {
		return sogoHyotenInfo;
	}

	/**
	 * 総合評点リスト情報オブジェクトをセットする。
	 * @param map 総合評点リスト情報オブジェクト
	 */
	public void setSogoHyotenInfo(Map map) {
		sogoHyotenInfo = map;
	}

	/**
	 * 審査状況情報を取得する。
	 * @return
	 */
	public ShinsaJokyoInfo getShinsaJokyoInfo() {
		return shinsaJokyoInfo;
	}

	/**
	 * 審査状況情報をセットする。
	 * @param info 審査状況情報
	 */
	public void setShinsaJokyoInfo(ShinsaJokyoInfo info) {
		shinsaJokyoInfo = info;
	}

	// 2005/04/07 追加ここから---------------------------------------------
	// 理由 部局担当者情報保持のため	
	
	/**
	 * 部局担当者情報を取得する
	 * @return
	 */
	public BukyokutantoInfo getBukyokutantoInfo() {
		return bukyokutantoInfo;
	}

	/**
	 * 部局担当者情報をセットする
	 * @param info
	 */
	public void setBukyokutantoInfo(BukyokutantoInfo info) {
		bukyokutantoInfo = info;
	}
	// 追加 ここまで--------------------------------------------------------
	
	// 2005/04/15 追加 ここから---------------------------------------------
	// 理由 チェックリスト情報保持のため
		
	/**
     * チェックリスト情報を取得する。
	 * @return チェックリスト情報
	 */
	public CheckListInfo getCheckListInfo() {
		return checkListInfo;
	}

	/**
     * チェックリスト情報をセットする。
	 * @param info チェックリスト情報
	 */
	public void setCheckListInfo(CheckListInfo info) {
		checkListInfo = info;
	}
	
	/**
     * チェックリスト検索情報を取得する。
	 * @return チェックリスト検索情報を戻します。
	 */
	public CheckListSearchInfo getCheckListSearchInfo() {
		return checkListSearchInfo;
	}

	/**
     * チェックリスト検索情報をセットする。
	 * @param checkListSearchInfo チェックリスト検索情報を設定。
	 */
	public void setCheckListSearchInfo(CheckListSearchInfo checkListSearchInfo) {
		this.checkListSearchInfo = checkListSearchInfo;
	}
	//	追加 ここまで--------------------------------------------------------
	//	2005/04/15 追加 ここから---------------------------------------------
	//理由 研究者情報保持のため
	
	/**
     * 研究者情報を取得する。
	 * @return 研究者情報
	 */
	public KenkyushaInfo getKenkyushaInfo() {
		return kenkyushaInfo;
	}

	/**
     * 研究者情報をセットする。
	 * @param info 研究者情報
	 */
	public void setKenkyushaInfo(KenkyushaInfo info) {
		kenkyushaInfo = info;
	}
	
	//追加 ここまで----------------------------------------------------------
	
	//2005/04/20 追加 ここから----------------------------------------------
	//理由 申請者検索条件保持のため	
	/**
     * 申請者検索情報を取得する。
	 * @return 申請者検索情報
	 */
	public ShinseishaSearchInfo getShinseishaSearchInfo() {
		return shinseishaSearchInfo;
	}

	/**
     * 申請者検索情報をセットする。
	 * @param info 申請者検索情報
	 */
	public void setShinseishaSearchInfo(ShinseishaSearchInfo info) {
		shinseishaSearchInfo = info;
	}
	//追加 ここまで----------------------------------------------------------

	
	//2005/04/29 追加 ----------------------------------------------ここから
	//理由 一括再設定用のID格納用に追加
	/**
     * 研究者IDリストを取得する。
	 * @return 研究者IDリスト
	 */
	public String[] getKenkyuNo() {
		return kenkyuNo;
	}
	/**
     * 研究者IDリストをセットする。
	 * @param kenkyuNo 研究者IDリスト
	 */
	public void setKenkyuNo(String[] kenkyuNo) {
		this.kenkyuNo = kenkyuNo;
	}
	//2005/04/29 追加 ----------------------------------------------ここまで

	/**
     * アンケート情報を取得する。
	 * @return アンケート情報
	 */
	public QuestionInfo getQuestionInfo() {
		return questionInfo;
	}

	/**
     * アンケート情報をセットする。
	 * @param info アンケート情報
	 */
	public void setQuestionInfo(QuestionInfo info) {
		questionInfo = info;
	}

	// 20060605 Wang Xiancheng add start
	/**
     * 申請情報リストを取得する。
	 * @return 申請情報リスト
	 */
	public SimpleShinseiDataInfo[] getSimpleShinseiDataInfos() {
		return simpleShinseiDataInfos;
	}

	/**
     * 申請情報リストをセットする。
	 * @param simpleShinseiDataInfos 申請情報リスト
	 */
	public void setSimpleShinseiDataInfos(
			SimpleShinseiDataInfo[] simpleShinseiDataInfos) {
		this.simpleShinseiDataInfos = simpleShinseiDataInfos;
	}
	// 20060605 Wang Xiancheng add end

    /**
     * 領域計画書情報を取得する。
     * @return 領域計画書情報
     */
    public RyoikiKeikakushoInfo getRyoikikeikakushoInfo() {
        return ryoikikeikakushoInfo;
    }

    /**
     * 領域計画書情報をセットする。
     * @param ryoikikeikakushoInfo 領域計画書情報
     */
    public void setRyoikikeikakushoInfo(RyoikiKeikakushoInfo ryoikikeikakushoInfo) {
        this.ryoikikeikakushoInfo = ryoikikeikakushoInfo;
    }

    /**
     * @return Returns the teishutsuShoruiSearchInfo.
     */
    public TeishutsuShoruiSearchInfo getTeishutsuShoruiSearchInfo() {
        return teishutsuShoruiSearchInfo;
    }

    /**
     * @param teishutsuShoruiSearchInfo The teishutsuShoruiSearchInfo to set.
     */
    public void setTeishutsuShoruiSearchInfo(TeishutsuShoruiSearchInfo teishutsuShoruiSearchInfo) {
        this.teishutsuShoruiSearchInfo = teishutsuShoruiSearchInfo;
    }
}