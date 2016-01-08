/*======================================================================
 *    SYSTEM      : 電子申請システム（科学研究費補助金）
 *    Source name : KenkyushaMaintenance
 *    Description : 研究者情報管理クラス
 *
 *    Author      : yoshikawa_h
 *    Date        : 2005/03/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.impl;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.dao.exceptions.*;
import jp.go.jsps.kaken.model.dao.impl.*;
import jp.go.jsps.kaken.model.dao.select.*;
import jp.go.jsps.kaken.model.dao.util.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;

import org.apache.commons.logging.*;

/**
 * 研究者情報管理クラス.<br>
 * @author yoshikawa_h
 */
public class KenkyushaMaintenance implements IKenkyushaMaintenance {
	
	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログ */
	protected static Log log = LogFactory.getLog(KenkyushaMaintenance.class);
	
	/**
	 * IDパスワード通知書ファイル格納フォルダ .<br /><br />
	 * 
	 * 具体的な値は、"<b>${shinsei_path}/work/shinseisha/</b>"<br />
	 */
	private static String SHINSEISHA_WORK_FOLDER      = ApplicationSettings.getString(ISettingKeys.SHINSEISHA_WORK_FOLDER);		

	/**
	 * IDパスワード通知書Wordファイル格納フォルダ.<br /><br />
	 * 
	 * 具体的な値は、"<b>${shinsei_path}/settings/shinseisha/</b>"<br />
	 */
	private static String SHINSEISHA_FORMAT_PATH      = ApplicationSettings.getString(ISettingKeys.SHINSEISHA_FORMAT_PATH);		

	/**
	 * IDパスワード通知書Wordファイル名.<br /><br />
	 * 
	 * 具体的な値は、"<b>shinseisha.doc</b>"<br />
	 */
	private static String SHINSEISHA_FORMAT_FILE_NAME = ApplicationSettings.getString(ISettingKeys.SHINSEISHA_FORMAT_FILE_NAME);		

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------
	/**
	 * コンストラクタ。
	 */
    public KenkyushaMaintenance() {
        super();
    }
    
    /**
     * 研究者情報の取得.<br /><br />
     * 
     * 研究者マスタから研究者情報を取得する。
     * 
     * 以下のSQL文を発行して、研究者マスタテーブルからデータを取得する。<br />
     * (バインド変数は、SQL文の下の表を参照)<br /><br />
     * 
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
     * <tr bgcolor="#FFFFFF"><td><pre>
     *    SELECT
     *	      KENKYU.KENKYU_NO,	            //研究者番号
     *        KENKYU.NAME_KANJI_SEI,        //氏名（漢字等-姓）
     *        KENKYU.NAME_KANJI_MEI,        //氏名（漢字等-名）
     *        KENKYU.NAME_KANA_SEI,         //氏名（フリガナ-姓）
     *        KENKYU.NAME_KANA_MEI,         //氏名（フリガナ-名）
     *        KENKYU.SEIBETSU,              //性別
     *        KENKYU.BIRTHDAY,              //生年月日
     *        KENKYU.GAKUI,                 //学位
     *        KENKYU.SHOZOKU_CD,            //所属機関コード
     *        KIKAN.SHOZOKU_NAME_KANJI,     //所属機関名（和文）
     *        KIKAN.SHOZOKU_NAME_EIGO,      //所属機関名（英文）
     *        KIKAN.SHOZOKU_RYAKUSHO,       //所属機関名（略称）
     *        KENKYU.BUKYOKU_CD,            //部局コード
     *        BUKYOKU.BUKA_NAME,            //部局名
     *        BUKYOKU.BUKA_RYAKUSHO,        //部局略称
     *        KENKYU.SHOKUSHU_CD,           //職コード
     *        SHOKU.SHOKUSHU_NAME,          //職名
     *        SHOKU.SHOKUSHU_NAME_RYAKU,    //職略称
     *        KENKYU.KOSHIN_DATE,           //更新日時
     *        KENKYU.BIKO                   //備考
     *        KENKYU.DEL_FLG                //削除フラグ
     *    FROM 
     *        MASTER_KENKYUSHA KENKYU       //研究者マスタ
     *    INNER JOIN 
     *        MASTER_KIKAN KIKAN            //所属機関マスタ
     *    ON 
     *        KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD
     *    INNER JOIN 
     *        MASTER_BUKYOKU BUKYOKU        //部局マスタ
     *    ON 
     *        KENKYU.BUKYOKU_CD = BUKYOKU.BUKYOKU_CD
     *    INNER JOIN 
     *        MASTER_SHOKUSHU SHOKU         //職種マスタ
     *    ON 
     *        KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD
     *    WHERE 
     *        KENKYU.SHOZOKU_CD = ?
     *    AND 
     *        KENKYU.KENKYU_NO = ?
     *    AND 
     *        KENKYU.DEL_FLG = 0
     * </pre>
     * </td></tr>
     * </table><br />
     *
     * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
     *   <tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
     *   <tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数ShinseishaPkの変数ShozokuCdを使用する。</td></tr>
     *   <tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数ShinseishaPkの変数KenkyuNoを使用する。</td></tr>
     * </table><br />
     * 
     * 取得した値をKenkyushaInfoに格納し、返却する。<br /><br />
     * 
     * @param userInfo UserInfo
     * @param pkInfo KenkyushaPk
     * @return KenkyushaInfo
     * @throws ApplicationException
     */
    public KenkyushaInfo select(UserInfo userInfo, KenkyushaPk pkInfo)
            throws ApplicationException {

        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
// ロックをFalseに修正（本メソッドを呼んだ後に研究者マスタを更新する処理が無さそうなので。。。）
//            return dao.selectKenkyushaInfo(connection, pkInfo, true);
            return dao.selectKenkyushaInfo(connection, pkInfo, false);
        } catch (DataAccessException e) {
            throw new ApplicationException(
                "研究者データ検索中にDBエラーが発生しました。",
                new ErrorInfo("errors.4004"),
                e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
    
    /**
     * 未登録申請者のPage情報の取得.<br><br>
     * 
     * 以下のSQL文を発行して、未登録申請者のPage情報を取得する。
     * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
     * <tr bgcolor="#FFFFFF"><td><pre>
     * 
     * SELECT
     *     KENKYU.KENKYU_NO            -- 研究者番号
     *     , KENKYU.NAME_KANJI_SEI     -- 氏名（漢字等-姓）
     *     , KENKYU.NAME_KANJI_MEI     -- 氏名（漢字等-名）
     *     , KENKYU.NAME_KANA_SEI      -- 氏名（フリガナ-姓）
     *     , KENKYU.NAME_KANA_MEI      -- 氏名（フリガナ-名）
     *     , KENKYU.SEIBETSU           -- 性別
     *     , KENKYU.BIRTHDAY           -- 生年月日
     *     , KENKYU.GAKUI              -- 学位
     *     , KENKYU.SHOZOKU_CD         -- 所属機関コード
     *     , KENKYU.BUKYOKU_CD         -- 部局コード
     *     , BUKYOKU.BUKA_RYAKUSHO     -- 部局略称
     *     , KENKYU.SHOKUSHU_CD        -- 職コード
     *     , SHOKU.SHOKUSHU_NAME_RYAKU -- 職略称
     *     , KENKYU.KOSHIN_DATE        -- 更新日時
     *     , KENKYU.BIKO               -- 備考
     * FROM MASTER_KENKYUSHA KENKYU                 -- 研究者マスタ
     *         INNER JOIN MASTER_BUKYOKU BUKYOKU    -- 部局マスタ
     *             ON KENKYU.BUKYOKU_CD = BUKYOKU.BUKYOKU_CD
     *         INNER JOIN MASTER_SHOKUSHU SHOKU     -- 職種マスタ
     *             ON KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD
     * WHERE KENKYU.KENKYU_NO NOT IN
     *             ( SELECT KENKYU_NO FROM SHINSEISHAINFO WHERE DEL_FLG = 0 AND SHOZOKU_CD = + shozokuCd + )
     *                     AND KENKYU.SHOZOKU_CD = + shozokuCd;
     * 
     *     <b><span style="color:#002288">-- 動的検索条件 --</span></b>
     * ORDER BY
     *        KENKYU.KENKYU_NO</pre>
     * </td></tr>
     * </table><br />
     * 
     * <b><span style="color:#002288">動的検索条件</span></b><br/>
     * 引数searchInfoの値によって検索条件が動的に変化する。
     * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
     *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '所属機関コード'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>申請者氏名(漢字-姓)</td><td>NameKanjiSei</td><td>AND NAME_KANJI_SEI LIKE '%申請者氏名(漢字-姓)%'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>申請者氏名(漢字-名)</td><td>NameKanjiMei</td><td>AND NAME_KANJI_MEI LIKE '%申請者氏名(漢字-名)%'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>申請者氏名(フリガナ-姓)</td><td>NameKanaSei</td><td>AND NAME_KANA_SEI LIKE '%申請者氏名(フリガナ-姓)%'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>申請者氏名(フリガナ-名)</td><td>NameKanaMei</td><td>AND NAME_KANA_MEI LIKE '%申請者氏名(フリガナ-名)%'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>部局コード</td><td>BukyokuCd</td><td>AND BUKYOKU_CD = '部局コード'</td></tr>
     *     <tr bgcolor="#FFFFFF"><td>研究者番号</td><td>KenkyuNo</td><td>AND KENKYU_NO = '研究者番号'</td></tr>
     * </table><br />
     * 
     * 取得した値は、列名をキーにMapにセットされ、Listにセットされる。
     * そのListを格納したPageを返却する。<br /><br />
     * 
     * @param userInfo UserInfo
     * @param searchInfo ShinseishaSearchInfo
     * @return 未登録申請者情報のPage
     */
    public Page searchUnregist(UserInfo userInfo, ShinseishaSearchInfo searchInfo)
            throws ApplicationException {
        
        if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
            //部局担当者のとき、検索条件の部局コードが自分の担当かチェックする
            BukyokutantoInfo info = userInfo.getBukyokutantoInfo();
            
            if(info.getTantoFlg()){
                if(searchInfo.getBukyokuCd() != null && !searchInfo.getBukyokuCd().equals("")){
                    IBukyokutantoMaintenance bukyokutantoMaintenance = new BukyokutantoMaintenance();
                    
                    //キーのセット
                    BukyokutantoPk pkInfo = new BukyokutantoPk();
                    pkInfo.setBukyokutantoId(info.getBukyokutantoId());
                    pkInfo.setBukyokuCd(searchInfo.getBukyokuCd());
                    
                    BukyokutantoInfo[] tanto = bukyokutantoMaintenance.select(userInfo,pkInfo);
                    
                    if(tanto.length == 0){
                        throw new NoDataFoundException(
                                "ログインユーザの担当する部局ではありません。"
                                    + "検索キー：部局担当者ID'" + pkInfo.getBukyokutantoId() + "'"
                                    + " 担当部局コード'" + pkInfo.getBukyokuCd()
                                    + "'", new ErrorInfo("errors.authority"));
                    }
                }
            }
        }
        
        MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
        
        return dao.searchUnregist(userInfo, searchInfo);
    }
    
    
    /**
     * 申請者情報の一括登録.<br><br>
     * 
     * 
     * @param userInfo
     * @param kenkyuNo
     * @return
     * @throws ApplicationException
     */
    public synchronized void registShinseishaFromKenkyusha(UserInfo userInfo, String[] kenkyuNo)
            throws ApplicationException {

        boolean success = false;
        Connection connection = null;
		
		//------登録対象研究者情報の取得
		KenkyushaPk pkInfo = new KenkyushaPk();
		
		KenkyushaInfo kenkyushaInfo = new KenkyushaInfo();
		ShinseishaInfo addInfo = new ShinseishaInfo();
		String hakkoshaId = null;
		String shozokuCd = null;
		String shozokuNameKanji = null;
		String shozokuNameEigo = null;
		String shozokuNameRyaku = null;
		
		try{
			connection = DatabaseUtil.getConnection();
			
			if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
				//所属機関担当者のとき
				ShozokuInfo info = userInfo.getShozokuInfo();
				hakkoshaId = info.getShozokuTantoId();
				shozokuCd = info.getShozokuCd();
				shozokuNameKanji = info.getShozokuName();
				shozokuNameEigo = info.getShozokuNameEigo();
				shozokuNameRyaku = info.getShozokuRyakusho();
				
			}else if(userInfo.getRole().equals(UserRole.BUKYOKUTANTO)){
				//部局担当者のとき
				BukyokutantoInfo info = userInfo.getBukyokutantoInfo();
				hakkoshaId = info.getBukyokutantoId();
				shozokuCd = info.getShozokuCd();
				
				try{
					//機関名英語・略称等を所属機関担当者情報から取得する
					ShozokuSearchInfo searchInfo = new ShozokuSearchInfo();
					searchInfo.setShozokuCd(shozokuCd);
					ShozokuMaintenance shozokuMainte = new ShozokuMaintenance();
					List list = shozokuMainte.search(userInfo, searchInfo).getList();;
					Map map = (Map)list.get(0);	//仮に複数見つかったとしても１件目だけ参照する
					shozokuNameKanji = (String)map.get("SHOZOKU_NAME_KANJI");
					shozokuNameEigo  = (String)map.get("SHOZOKU_NAME_EIGO");
					shozokuNameRyaku = (String)map.get("SHOZOKU_RYAKUSHO");
				}catch(NoDataFoundException e){
					if(log.isDebugEnabled()){
						String msg = "所属機関担当者が存在しなかったため、機関名、機関名英語、機関名略称はnull。";
						log.debug(msg);
					}
				}
				
			}
			
			//選択した未登録申請者の件数分繰り返す
			for(int i=0; i<kenkyuNo.length; i++){
				if(kenkyuNo[i] == null || kenkyuNo[i].equals("")){
					continue;
				}
				
				//------主キー情報のセット
				pkInfo.setKenkyuNo(kenkyuNo[i]);
				pkInfo.setShozokuCd(shozokuCd);
				
				//------キー情報を元に更新データ取得	
				//kenkyushaInfo = select(userInfo,pkInfo);
				//2005/04/19 修正 ここから---------------------------------------------------
				//FOR UPDATEのコネクションを同じコネクションにするため修正
				MasterKenkyushaInfoDao kenkyushaDao = new MasterKenkyushaInfoDao(userInfo);
				kenkyushaInfo = kenkyushaDao.selectKenkyushaInfo(connection, pkInfo, true);
				//修正 ここまで--------------------------------------------------------------
				
				//申請者情報をセット（機関情報は所属担当者から取得する）
				addInfo.setShozokuCd(kenkyushaInfo.getShozokuCd());
				addInfo.setShozokuName(shozokuNameKanji);
				addInfo.setShozokuNameEigo(shozokuNameEigo);
				addInfo.setShozokuNameRyaku(shozokuNameRyaku);
				addInfo.setNameKanjiSei(kenkyushaInfo.getNameKanjiSei());
				addInfo.setNameKanjiMei(kenkyushaInfo.getNameKanjiMei());
				addInfo.setNameKanaSei(kenkyushaInfo.getNameKanaSei());
				addInfo.setNameKanaMei(kenkyushaInfo.getNameKanaMei());
				addInfo.setKenkyuNo(kenkyushaInfo.getKenkyuNo());
				addInfo.setBukyokuCd(kenkyushaInfo.getBukyokuCd());
				addInfo.setBukyokuName(kenkyushaInfo.getBukyokuName());
				addInfo.setBukyokuNameRyaku(kenkyushaInfo.getBukyokuNameRyaku());
				addInfo.setShokushuCd(kenkyushaInfo.getShokushuCd());
				addInfo.setShokushuNameKanji(kenkyushaInfo.getShokushuName());
				addInfo.setShokushuNameRyaku(kenkyushaInfo.getShokushuNameRyaku());
				addInfo.setBirthday(kenkyushaInfo.getBirthday());
				addInfo.setHakkoshaId(hakkoshaId);;
				addInfo.setDelFlg("0");
				
	            //---------------------------------------
	            //申請者情報データアクセスオブジェクト
	            //---------------------------------------
	            ShinseishaInfoDao dao = new ShinseishaInfoDao(userInfo);

				//2005.09.26 iso 多重登録防止のため追加
				if(dao.countShinseishaInfoPreInsert(connection, addInfo) > 0) {
					throw new ApplicationException("すでに応募者が登録されています。", 	new ErrorInfo("errors.4011"));
				}

				//---------------------------------------
				//キー情報の（申請者ID）を作成
				//西暦年度（2桁）+ 所属機関コード（5桁）+ 連番（5桁）+ チェックデジット（１桁）
				//---------------------------------------
				String wareki = new DateUtil().getNendo();
				String key = DateUtil.changeWareki2Seireki(wareki)
							+ addInfo.getShozokuCd()
							+ dao.getSequenceNo(connection,addInfo.getShozokuCd());
					
			    //申請者IDを取得					
				String shinseishaId = key +  CheckDiditUtil.getCheckDigit(key, CheckDiditUtil.FORM_ALP);
				addInfo.setShinseishaId(shinseishaId);
			
				//RULEINFOテーブルよりルール取得準備
				RuleInfoDao rureInfoDao = new RuleInfoDao(userInfo);
				RulePk rulePk = new RulePk();
				rulePk.setTaishoId(ITaishoId.SHINSEISHA);
				
				//---------------------------------------
				//パスワード情報の作成
				//---------------------------------------
				String newPassword = rureInfoDao.getPassword(connection, rulePk);
				addInfo.setPassword(newPassword);
			
				//---------------------------------------
				//有効日付情報情報の作成(担当者用)
				//---------------------------------------
				if(addInfo.getYukoDate() == null) {
					Date newYukoDate = rureInfoDao.selectRuleInfo(connection, rulePk).getYukoDate();
					addInfo.setYukoDate(newYukoDate);
				}
		
				//---------------------------------------
				//非公募応募可フラグ(担当者用)
				//---------------------------------------
				if(addInfo.getHikoboFlg() == null || addInfo.getHikoboFlg().equals("")) {
					addInfo.setHikoboFlg("0");
				}
			
				//---------------------------------------
				//申請者情報の追加
				//---------------------------------------
				dao.insertShinseishaInfo(connection,addInfo);		
			
				//---------------------------------------
				//登録データの取得
				//---------------------------------------
				ShinseishaInfo result = dao.selectShinseishaInfo(connection, addInfo);
			
				//---------------------------------------
				//登録正常終了
				//---------------------------------------
				success = true;	
			
			}
					
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請者管理データ登録中にDBエラーが発生しました。",
				new ErrorInfo("errors.4001"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"申請者管理データ登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}

	//2005/04/15 追加 ここから----------------------------------------------------------------
	//理由 研究者情報の処理のため	
	/**
	 * 入力データチェック.<br /><br />
	 * 
	 * <b>1.所属機関情報の取得</b><br /><br />
	 * 
	 * 第二引数infoのコードの値を使用して、名前を取得する。
	 * 取得は、自メソッドを使用して行う。使用するコード、メソッド、取得する名前は以下の表を参照。<br /><br />
	 * 
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>使用するコード</td><td>取得する名前</td><td>使用する自メソッド</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>所属機関名</td><td>getKikanCodeValueAction()</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局コード</td><td>部局名、部局名(略称)</td><td>getBukyokuCodeMap()</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職種コード</td><td>職名</td><td>getShokushuMap()</td></tr>
	 * </table><br />
	 * 
	 * 取得した値は、infoに格納する。<br /><br />
	 * 
	 * <b>2.研究者番号チェック</b><br/><br/>
	 * 研究者番号が数値以外、8桁以外、nullの場合は例外をthrowする。<br />
	 * また、ApplicationSettings.propertiesのCHECK_DIGIT_FLAGがtrueの場合は、<BR/>
	 * ShinseishaMaintenanceのcheckKenkyuNo()メソッドを使用して研究者番号のチェックデジットチェックを行い、<br>
	 * 研究者番号の値の8桁目の値(一番左の桁)が、チェックデジットの値と異なる場合には、例外をthrowする。<br />
	 * 
	 * <b>3.重複チェック</b><br /><br />
	 * 
	 * 以下のSQL文を発行し、研究者番号・所属機関コードの
	 * 同じ研究者が登録されていないかどうかを確認する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		SELECT 
				COUNT(*)
		FROM 
				MASTER_KENKYUSHA 
		WHERE 
				KENKYU_NO = ? 
		AND 
				SHOZOKU_CD = ?
		AND 
				DEL_FLG = 0</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数infoの変数KenkyuNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数infoの変数ShozokuCdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 検索結果が存在する場合には例外をthrowし、存在しない場合にはinfoを返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param info KenkyushaInfo
	 * @return 研究者情報のShinseishaInfo
	 * @throws ApplicationException
	 * @throws ValidationException
	 */
	public KenkyushaInfo validate(UserInfo userInfo, KenkyushaInfo info, String mode)
			throws ApplicationException, ValidationException {

		Connection connection = null;	
		try {
			ShinseishaMaintenance shinsehsha = new ShinseishaMaintenance();
			connection = DatabaseUtil.getConnection();

			//---------------------------
			//所属機関コード→所属機関名のセット
			//---------------------------
			KikanInfo kikanInfo = new KikanInfo();
			kikanInfo = getKikanCodeValueAction(userInfo, info.getShozokuCd(), true);
			info.setShozokuNameKanji(kikanInfo.getShozokuNameKanji());
			info.setShozokuNameEigo(kikanInfo.getShozokuNameEigo());
			info.setShozokuRyakusho(kikanInfo.getShozokuRyakusho());
			
			//---------------------------
			//部局コード→部局名、部局名(略称)
			//---------------------------
			if(info.getBukyokuCd() != null && !info.getBukyokuCd().equals("")){
				Map map = getBukyokuCodeMap(userInfo, info.getBukyokuCd());
				info.setBukyokuName((String)map.get("BUKA_NAME"));
				info.setBukyokuNameRyaku((String)map.get("BUKA_RYAKUSHO"));
			}
			//---------------------------
			//職種コード→職名	
			//---------------------------
			Map shokushuMap = getShokushuMap(userInfo, info.getShokushuCd());
			info.setShokushuName((String)shokushuMap.get("SHOKUSHU_NAME"));
			info.setShokushuNameRyaku((String)shokushuMap.get("SHOKUSHU_NAME_RYAKU"));
			
			//研究者番号のチェックデジットチェック
			if(!shinsehsha.checkKenkyuNo(info.getKenkyuNo())) {
				throw new ApplicationException("研究者番号が空です。", 	new ErrorInfo("errors.required", new String[] {"研究者番号"}));	
			}
			
			//2重登録チェック
			//研究者マスタテーブルにすでに研究者番号、所属部局コードが同じ研究者が登録されていないかどうかを確認
			MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
			int count = dao.countKenkyushaInfo(connection, info);
			
			//すでに登録されている場合
			if(count > 0 && mode.equals(IMaintenanceName.ADD_MODE)){
				String[] error = {"研究者"};
				throw new ApplicationException("すでに研究者が登録されています。", 	new ErrorInfo("errors.4007", error));			
			}else if(count == 0 && mode.equals(IMaintenanceName.EDIT_MODE)){
				throw new ApplicationException("更新対象の研究者が登録されていません。", new ErrorInfo("errors.5002"));
			}

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"研究者管理データチェック中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return info;
	}
	
	
	/**
	 * 機関情報の返却.<br /><br />
	 * 
	 * 所属機関コードから所属機関名を取得する。<br />
	 * 渡される引数によって、処理が大幅に変わる。<br /><br />
	 * 
	 * まず、第一引数のString"<b>kikanCd</b>"がnullだった場合には、例外をthrowする。<br /><br />
	 * 
	 * nullではない場合には、以下のSQL文を発行する。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	*
	 * FROM
	 * 	MASTER_KIKAN
	 * WHERE
	 * 	SHOZOKU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数のStringを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 続いて、第二引数のboolean"<b>priorityFlg</b>"がfalseの場合には、
	 * 今取得したKikanInfoを返却して終了となる。<br />
	 * trueの場合には、以下のSQL文を発行して、所属機関担当者情報を検索する。<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 *     A.SHOZOKUTANTO_ID			-- 所属機関担当者ID
	 *     ,A.SHOZOKU_CD"				-- 所属機関名（コード）
	 *     ,A.SHOZOKU_NAME_KANJI			-- 所属機関名（日本語）
	 *     ,A.SHOZOKU_RYAKUSHO			-- 所属機関名（略称）
	 *     ,A.SHOZOKU_NAME_EIGO			-- 所属機関名（英文）
	 *     ,A.SHUBETU_CD				-- 機関種別
	 *     ,A.PASSWORD				-- パスワード
	 *     ,A.SEKININSHA_NAME_SEI			-- 責任者氏名（姓）
	 *     ,A.SEKININSHA_NAME_MEI			-- 責任者氏名（名）
	 *     ,A.SEKININSHA_YAKU			-- 責任者役職
	 *     ,A.BUKYOKU_NAME				-- 担当部課名
	 *     ,A.KAKARI_NAME				-- 担当係名
	 *     ,A.TANTO_NAME_SEI			-- 担当者名（姓）
	 *     ,A.TANTO_NAME_MEI			-- 担当者名（名）
	 *     ,A.TANTO_TEL				-- 担当者部局所在地（電話番号）
	 *     ,A.TANTO_FAX				-- 担当者部局所在地（FAX番号）
	 *     ,A.TANTO_EMAIL				-- 担当者部局所在地（Email）
	 *     ,A.TANTO_EMAIL2				-- 担当者部局所在地（Email2）
	 *     ,A.TANTO_ZIP				-- 担当者部局所在地（郵便番号）
	 *     ,A.TANTO_ADDRESS				-- 担当者部局所在地（住所）
	 *     ,A.NINSHOKEY_FLG				-- 認証キー発行フラグ
	 *     ,A.BIKO				-- 備考
	 *     ,A.YUKO_DATE				-- 有効期限
	 *     ,BUKYOKU_NUM				-- 部局担当者人数
	 *     ,A.DEL_FLG				-- 削除フラグ
	 * FROM
	 *     SHOZOKUTANTOINFO A
	 * WHERE
	 *     DEL_FLG = 0
	 *	
	 * 　　　<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 * 
	 * ORDER BY SHOZOKU_CD</pre>
	 * </td></tr>
	 * </table><br/>
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>機関種別コード</td><td>ShubetuCd</td><td>AND SHUBETU_CD = '機関種別コード'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（コード）</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '所属機関名（コード）'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関名（日本語）</td><td>ShozokuNameKanji</td><td>AND SHOZOKU_NAME_KANJI LIKE '%所属機関名（日本語）'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者ID</td><td>ShozokuTantoId</td><td>AND SHOZOKUTANTO_ID = '担当者ID'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（姓）</td><td>TantoNameSei</td><td>AND TANTO_NAME_SEI LIKE '%担当者名（姓）'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>担当者名（名）</td><td>TantoNameMei</td><td>AND TANTO_NAME_MEI LIKE '%担当者名（名）'</td></tr>
	 * </table><br/>
	 * 
	 * 取得した値から、所属機関名(漢字、英語)をKikanInfoに格納し、返却する。<br/><br/>
	 * 
	 * @param  userInfo          	 実行するユーザ情報
	 * @param  kikanCd          	 機関コード
	 * @param  nameKanji          	 所属機関名(漢字)
	 * @param  nameEigo          	 所属機関名(英語)
	 * @param  priorityFlg			 所属機関テーブル優先フラグ　true:所属機関テーブルを優先して返す　false:機関マスタの値を返す
	 * @param  otherFlg			 その他フラグ　true:その他コードあり　false:その他コードなし（その他コードもエラーとなる）
	 * @return                      機関情報
	 * @throws IllegalArgumentException  機関コードがnullの場合
	 * @throws ApplicationException 
	 */
	private KikanInfo getKikanCodeValueAction(UserInfo userInfo, String kikanCd, boolean priorityFlg)
				throws IllegalArgumentException, ApplicationException {

		//引数チェック
		if(kikanCd == null){
			throw new IllegalArgumentException("kikanCdがnullです。");
		}

		KikanInfo kikanInfo = new KikanInfo();
		kikanInfo.setShozokuCd(kikanCd);
		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//エラー情報保持用リスト
			List errors = new ArrayList();

			try {
				kikanInfo = new MasterKikanInfoDao(userInfo).selectKikanInfo(connection,kikanInfo);
			} catch(NoDataFoundException noDataShozokuInfo) {
				//見つからなかったとき。
				errors.add(new ErrorInfo("errors.2001", new String[] { "所属機関コード" }));
			}
		
			//優先フラグがtrueの場合は所属機関テーブルを検索する
			if(priorityFlg) {
				//所属機関が内容を変更している場合があるので、所属機関テーブルの情報を優先的に取得する
				ShozokuSearchInfo shozokuSearchInfo = new ShozokuSearchInfo();
				shozokuSearchInfo.setShozokuCd(kikanCd);
				try {
					Page page = new ShozokuMaintenance().search(userInfo, shozokuSearchInfo);
					if(page.getTotalSize() > 0) {
						List list = page.getList();
						HashMap hashMap = (HashMap)list.get(0);

						if(hashMap.get("SHOZOKU_NAME_KANJI") != null) {
							kikanInfo.setShozokuNameKanji(hashMap.get("SHOZOKU_NAME_KANJI").toString());
						}
						if(hashMap.get("SHOZOKU_NAME_EIGO") != null) {
							kikanInfo.setShozokuNameEigo(hashMap.get("SHOZOKU_NAME_EIGO").toString());
						}
					}
				} catch(ApplicationException e) {
					//見つからなかったとき。
					//→所属機関テーブルに無い場合は、機関マスタのデータを見るのでエラーとしない。
				}
			}
	
			//見つからなかった場合
			if(!errors.isEmpty()){
				String msg = "所属機関マスタに当該データがありません。kikanCD=" + kikanCd;
				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
			}
	
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"所属機関データチェック中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return kikanInfo;
	}
		
		
	/**
	 * 部科名称・部科略称のMapの返却.<br /><br />
	 * 
	 * 以下のSQL文を発行して、部局情報を取得する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre> 
	 * SELECT
	 *	*
	 * FROM
	 * 	MASTER_BUKYOKU
	 * WHERE
	 * 	BUKYOKU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>部局コード</td><td>第二引数bukyokuCdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した部局情報の"部科名称"、"部科略称"をMapに格納する。<br />
	 * 値がなかった場合には例外をthrowする。<br /><br />
	 * 
	 * 作成したMapを返却する。<br /><br />
	 * 
	 * @param  userInfo          	 実行するユーザ情報
	 * @param  kikanCd          	 部局コード
	 * @return                      部科名称・部科略称のMap
	 * @throws ApplicationException 
	 */
	private Map getBukyokuCodeMap(UserInfo userInfo, String bukyokuCd)
		throws ApplicationException {

		HashMap hashMap = new HashMap();
		
		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//エラー情報保持用リスト
			List errors = new ArrayList();
			try {
				BukyokuInfo bukyokuInfo = new BukyokuInfo();
				bukyokuInfo.setBukyokuCd(bukyokuCd);
				
				bukyokuInfo = new MasterBukyokuInfoDao(userInfo).selectBukyokuInfo(connection, bukyokuInfo);
				hashMap.put("BUKA_NAME", bukyokuInfo.getBukaName());
				hashMap.put("BUKA_RYAKUSHO", bukyokuInfo.getBukaRyakusyo());	//略称はマスタの値を使用する
			} catch(NoDataFoundException e) {
				//見つからなかったとき。
				errors.add(
					new ErrorInfo("errors.2001", new String[] { "部局コード" }));
			}

			//見つからなかった場合
			if(!errors.isEmpty()){
				String msg = "部局マスタに当該データがありません。kikanCD=" + bukyokuCd;
				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
			}
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"部局マスタデータチェック中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return hashMap;
	}
	
	
	/**
	 * 職種名・職種名(略称)のMapの返却.<br /><br />
	 * 
	 * 以下のSQL文を発行して、職種情報を取得する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	 * SELECT
	 * 	A.SHOKUSHU_CD				--職種コード
	 * 	,A.SHOKUSHU_NAME			--職名称
	 * 	,A.SHOKUSHU_NAME_RYAKU		--職名(略称)
	 * 	,A.BIKO						--備考
	 * FROM
	 * 	MASTER_SHOKUSHU A
	 * WHERE
	 * 	SHOKUSHU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>職種コード</td><td>第二引数shokushuCdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 取得した職種情報の"職種名"、"職種名(略称)"をMapに格納する。<br />
	 * 値がなかった場合には例外をthrowする。<br /><br />
	 * 
	 * 作成したMapを返却する。<br /><br />
	 * 
	 * @param  userInfo          	 実行するユーザ情報
	 * @param  shokushuCd         	 職種名コード
	 * @return                      職種名・職種名(略称)のMap
	 * @throws ApplicationException 
	 */
	private Map getShokushuMap(UserInfo userInfo, String shokushuCd)
			throws ApplicationException {

		HashMap hashMap = new HashMap();
		
		Connection connection = null;	
		try {
			connection = DatabaseUtil.getConnection();
			//エラー情報保持用リスト
			List errors = new ArrayList();
		
			try {
				ShokushuInfo shokushuInfo = new ShokushuInfo();
				shokushuInfo.setShokushuCd(shokushuCd);
				
				shokushuInfo = new MasterShokushuInfoDao(userInfo).selectShokushuInfo(connection, shokushuInfo);
				hashMap.put("SHOKUSHU_NAME", shokushuInfo.getShokushuName());
				hashMap.put("SHOKUSHU_NAME_RYAKU", shokushuInfo.getShokushuNameRyaku());	//略称はマスタの値を使用する
			} catch(NoDataFoundException e) {
				//見つからなかったとき。
				errors.add(
					new ErrorInfo("errors.2001", new String[] { "職種コード" }));
			}

			//見つからなかった場合
			if(!errors.isEmpty()){
				String msg = "職種マスタに当該データがありません。kikanCD=" + shokushuCd;
				throw new NoDataFoundException(msg, (ErrorInfo)errors.get(0));
			}
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"職種マスタデータチェック中にDBエラーが発生しました。",
				new ErrorInfo("errors.4005"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return hashMap;
	}
		
	
	/**
	 * 研究者情報の追加.<br /><br />
	 * 研究者情報テーブルに新規でデータを加え、そのデータを返却する。<br /><br />
	 * 
	 * <b>1.更新データ確認</b><br/><br/>
	 * 以下のSQL文を発行し、研究者番号・所属機関コードの
	 * 同じ研究者が登録されているかどうかを確認する。<br />
	 * また、FOR UPDATEをかけることで研究者マスタテーブルをロックする。<BR/>
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		SELECT 
				COUNT(*) 
		FROM 
				MASTER_KENKYUSHA
		WHERE 
				KENKYU_NO = ?
		AND 
				SHOZOKU_CD = ? 
		AND 
				DEL_FLG = 0
		FOR UPDATE</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数infoの変数KenkyuNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数infoの変数ShozokuCdを使用する。</td></tr>
	 * </table><br /> 
	 * 検索結果が存在する合は例外をthrowする。<br /><br />
	 *
	 * <b>2.研究者情報の挿入</b><br /><br />
	 * 
	 * 以下のSQL文を発行して、申請者情報テーブルにデータを挿入する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
	INSERT INTO 
			MASTER_KENKYUSHA (
				KENKYU_NO,
				NAME_KANA_SEI,
				NAME_KANA_MEI,
				NAME_KANJI_SEI,
				NAME_KANJI_MEI,
				SEIBETSU,
				BIRTHDAY,
				GAKUI,
				SHOZOKU_CD,
				BUKYOKU_CD,
				SHOKUSHU_CD,
				KOSHIN_DATE,
				BIKO,
				DEL_FLG) 
	VALUES
			(?,?,?,?,?,?,?,?,?,?,?,sysdate,?,0)</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数addInfoの変数KenkyuNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-姓)</td><td>第二引数addInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-名)</td><td>第二引数addInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-姓)</td><td>第二引数addInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-名)</td><td>第二引数addInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>性別</td><td>第二引数addInfoの変数Seibetsuを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>生年月日</td><td>第二引数addInfoの変数Birthdayを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>学位</td><td>第二引数addInfoの変数gakuiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数addInfoの変数ShozokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名(コード)</td><td>第二引数addInfoの変数BukyokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名コード</td><td>第二引数addInfoの変数ShokushuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数addInfoの変数Bikoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * @param userInfo UserInfo
	 * @param addInfo KenkyushaInfo
	 * @return 登録した研究者情報を持つKenkyushaInfo
	 * @throws ApplicationException
	 */
	public synchronized void insert(UserInfo userInfo, KenkyushaInfo addInfo)
		throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			
			//---------------------------------------	
			//研究者マスタデータアクセスオブジェクト
			//---------------------------------------
			MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);

			//2005/8/26 データが存在するかチェック（削除データを含める）
			int cnt = dao.countKenkyushaInfo(connection, addInfo, false);
			
			//2005/04/22　追加 ここから---------------
			//更新日付と削除フラグををSQL直書きではなく、KenkyushaInfoで渡すようにする
			addInfo.setKoshinDate(new Date());
			addInfo.setDelFlg("0");
			//追加 ここまで---------------------------

			if (cnt > 0){
				//---------------------------------------
				//研究者情報の更新
				//---------------------------------------
				dao.updateKenkyushaInfo(connection,addInfo);
				
				//---------------------------------------
				//研究者情報より応募者情報の更新
				//---------------------------------------
				updateShinseishaFromKenkyusha(userInfo, connection, addInfo);
				
			}
			else {
				//---------------------------------------
				//研究者情報の追加
				//---------------------------------------
				dao.insertKenkyushaInfo(connection,addInfo);		
			}
			//---------------------------------------
			//登録正常終了
			//---------------------------------------
			success = true;			
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"研究者管理データ登録中にDBエラーが発生しました。",
				new ErrorInfo("errors.4001"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"研究者管理データ登録中にDBエラーが発生しました。",
					new ErrorInfo("errors.4001"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}	


	/**
	 * 検索条件に合う研究者情報を取得する。
	 * 
	 * 以下のSQL文を発行して、申請者のPage情報を取得する。
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 	SELECT
	 			KENKYU.KENKYU_NO,
	 			KENKYU.NAME_KANJI_SEI,
	 			KENKYU.NAME_KANJI_MEI,
	 			KIKAN.SHOZOKU_RYAKUSHO,
	 			SHOKUSHU.SHOKUSHU_NAME_RYAKU,
	 			KENKYU.SHOZOKU_CD
	 	FROM 
	 			MASTER_KENKYUSHA KENKYU 
	 	INNER JOIN 
	 			MASTER_KIKAN KIKAN 
	 	ON
	 			KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD
	 	INNER JOIN 
	 			MASTER_SHOKUSHU SHOKUSHU
	 	ON 
	 			KENKYU.SHOKUSHU_CD = SHOKUSHU.SHOKUSHU_CD
	 	WHERE 
	 			KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD 
	 	AND 
	 			KENKYU.DEL_FLG = 0

	 * 	<b><span style="color:#002288">-- 動的検索条件 --</span></b>
	 * 
	 * ORDER BY
	 * 	KENKYU.KENKYU_NO</pre>
	 * 
	 * <b><span style="color:#002288">動的検索条件</span></b><br/>
	 * 引数searchInfoの値によって検索条件が動的に変化する。
	 * <table border="0" bgcolor="#000000" cellspacing="1" cellpadding="1" width="600">
	 *     <tr style="color:white;font-weight:bold"><td width="30%">変数名（日本語）</td><td>変数名</td><td>動的検索条件</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>研究者番号</td><td>KenkyuNo</td><td>AND KENKYU_NO = '研究者番号'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(漢字-姓)</td><td>NameKanjiSei</td><td>AND NAME_KANJI_SEI LIKE '%申請者氏名(漢字-姓)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(漢字-名)</td><td>NameKanjiMei</td><td>AND NAME_KANJI_MEI LIKE '%申請者氏名(漢字-名)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(フリガナ-姓)</td><td>NameKanaSei</td><td>AND NAME_KANA_SEI LIKE '%申請者氏名(フリガナ-姓)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>申請者氏名(フリガナ-名)</td><td>NameKanaMei</td><td>AND NAME_KANA_MEI LIKE '%申請者氏名(フリガナ-名)%'</td></tr>
	 *     <tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>ShozokuCd</td><td>AND SHOZOKU_CD = '所属機関コード'</td></tr>
	 * </table><br /> 
	 * 
	 * 取得した値は、列名をキーにMapにセットされ、Listにセットされる。
	 * そのListを格納したPageを返却する。<br /><br /> 
	 * 
	 * @param searchInfo	KenkyushaSearchInfo
	 * @return 研究者情報のPage
 	 * @throws ApplicationException
 	 */
	public Page search(KenkyushaSearchInfo searchInfo)
	throws ApplicationException {
			
		//-----------------------
		// 検索条件よりSQL文の作成
		//-----------------------
		String select =
			"SELECT"
				+ " KENKYU.KENKYU_NO,"
				+ " KENKYU.NAME_KANJI_SEI,"
				+ " KENKYU.NAME_KANJI_MEI,"
				+ " KIKAN.SHOZOKU_RYAKUSHO,"
				+ " SHOKUSHU.SHOKUSHU_NAME_RYAKU,"
				+ " KENKYU.SHOZOKU_CD"
		+ " FROM " 
				+ " MASTER_KENKYUSHA KENKYU "
	//機関マスタと研究者マスタを外部結合にする
	//	+ " INNER JOIN " 
		+ " LEFT JOIN "
				+" MASTER_KIKAN KIKAN "
		+ " ON " 
				+ " KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD"
		+ " INNER JOIN " 
				+ " MASTER_SHOKUSHU SHOKUSHU"
		+ " ON " 
				+ " KENKYU.SHOKUSHU_CD = SHOKUSHU.SHOKUSHU_CD"
	//	+ " WHERE KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD "
		//2005/04/21　追加 ここから----------------------------------------
		//研究所マスタに削除フラグ追加のため
	//	+ " AND KENKYU.DEL_FLG = 0";
		+ " WHERE KENKYU.DEL_FLG = 0";
		//追加 ここまで----------------------------------------------------
		StringBuffer query = new StringBuffer(select);
		
		if(searchInfo.getKenkyuNo() != null && !searchInfo.getKenkyuNo().equals("")){			//研究者番号
			query.append(" AND KENKYU.KENKYU_NO = '" + EscapeUtil.toSqlString(searchInfo.getKenkyuNo()) +"'");
		}
		if(searchInfo.getNameKanjiSei() != null && !searchInfo.getNameKanjiSei().equals("")){	//申請者氏名（漢字-姓）
			query.append(" AND KENKYU.NAME_KANJI_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiSei()) + "%'");
		}
		if(searchInfo.getNameKanjiMei() != null && !searchInfo.getNameKanjiMei().equals("")){	//申請者氏名（漢字-名）
			query.append(" AND KENKYU.NAME_KANJI_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanjiMei()) + "%'");
		}
		if(searchInfo.getNameKanaSei() != null && !searchInfo.getNameKanaSei().equals("")){	//申請者氏名（フリガナ-姓）
			query.append(" AND KENKYU.NAME_KANA_SEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaSei()) + "%'");
		}
		if(searchInfo.getNameKanaMei() != null && !searchInfo.getNameKanaMei().equals("")){	//申請者氏名（フリガナ-名）
			query.append(" AND KENKYU.NAME_KANA_MEI LIKE '%" + EscapeUtil.toSqlString(searchInfo.getNameKanaMei()) + "%'");
		}
		if(searchInfo.getShozokuCd() != null && !searchInfo.getShozokuCd().equals("")){			//所属機関CD
			query.append(" AND KENKYU.SHOZOKU_CD = '" + EscapeUtil.toSqlString(searchInfo.getShozokuCd()) +"'");
		}
		//ソート順（申請者IDの昇順）
		query.append(" ORDER BY KENKYU.KENKYU_NO");
		
		if(log.isDebugEnabled()){
			log.debug("query:" + query);
		}

		//-----------------------
		// ページ取得
		//-----------------------
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			return SelectUtil.selectPageInfo(connection, searchInfo, query.toString());
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"未登録申請者データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	
	/**
	 * 研究者情報の更新.<br /><br />
	 * 
	 * <b>1.更新データ確認</b><br/><br/>
	 * 以下のSQL文を発行し、研究者番号・所属機関コードの
	 * 同じ研究者が登録されているかどうかを確認する。<br />
	 * また、FOR UPDATEをかけることで研究者マスタテーブルをロックする。<BR/>
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		SELECT 
				COUNT(*) 
		FROM 
				MASTER_KENKYUSHA
		WHERE 
				KENKYU_NO = ?
		AND 
				SHOZOKU_CD = ? 
		FOR UPDATE</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数infoの変数KenkyuNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数infoの変数ShozokuCdを使用する。</td></tr>
	 * </table><br /> 
	 * 検索結果が存在しない場合は例外をthrowする。<br /><br />
	 *
	 * <b>2.研究者情報更新</b><br/><br/>
	 * 　
	 * 以下のSQL文を発行して、研究者情報テーブルの更新を行う。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		UPDATE 
					MASTER_KENKYUSHA
		SET
					KENKYU_NO = ? ,
					NAME_KANA_SEI = ? ,
					NAME_KANA_MEI = ? ,
					NAME_KANJI_SEI = ? ,
					NAME_KANJI_MEI = ? ,
					SEIBETSU = ? ,
					BIRTHDAY = ? ,
					GAKUI = ? ,
					SHOZOKU_CD = ? ,
					BUKYOKU_CD = ? ,
					SHOKUSHU_CD = ? ,
					KOSHIN_DATE = sysdate ,
					BIKO = ? 
		WHERE
					KENKYU_NO = ?
		AND 
					SHOZOKU_CD = ?</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数updateInfoの変数KenkyuNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-姓)</td><td>第二引数updateInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(漢字等-名)</td><td>第二引数updateInfoの変数NameKanjiSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-姓)</td><td>第二引数updateInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>氏名(フリガナ-名)</td><td>第二引数updateInfoの変数NameKanaSeiを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>性別</td><td>第二引数updateInfoの変数Seibetsuを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>生年月日</td><td>第二引数updateInfoの変数Birthdayを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>学位</td><td>第二引数updateInfoの変数Gakuiを使用する。</td></tr> 
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関名(コード)</td><td>第二引数updateInfoの変数ShozokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>部局名(コード)</td><td>第二引数updateInfoの変数BukyokuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>職名</td><td>第二引数updateInfoの変数ShokushuCdを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>備考</td><td>第二引数updateInfoの変数Bikoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数updateInfoの変数KenkyuNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属CD</td><td>第二引数updateInfoの変数ShinseishaIdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * <b>3.申請者情報更新</b><br/><br/> 
	 * ShinseishaMaintenanceのsearchメソッドを使用して申請者情報テーブルに修正する研究者の情報が含まれているかチェックする。<BR/>
	 * 該当するデータが存在する場合は、ShinseishaMaintenanceのupdateメソッドを使用して更新する。<br>
	 * 
	 * @param userInfo UserInfo
	 * @param updateInfo KenkyushaInfo
	 * @throws ApplicationException
	 */
	public void update(UserInfo userInfo, KenkyushaInfo updateInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
			MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
			
			//更新チェック
			try{
				dao.getKenkyushaData(connection, updateInfo, true);
			}catch(NoDataFoundException e){//NG
				throw new ApplicationException("更新対象の研究者が存在しません。",
					new ErrorInfo("errors.4002"));
			}
			
			//2005/04/22　追加 ここから----------------------------------------------
			//更新日付と削除フラグををSQL直書きではなく、KenkyushaInfoで渡すようにする
			updateInfo.setKoshinDate(new Date());
			updateInfo.setDelFlg("0");
			//追加 ここまで----------------------------------------------------------
			
			//---------------------------------------
			//研究者情報の更新
			//---------------------------------------
			dao.updateKenkyushaInfo(connection, updateInfo);
			
			//---------------------------------------
			//研究者情報より応募者情報の更新
			//---------------------------------------
			updateShinseishaFromKenkyusha(userInfo, connection, updateInfo);
			
			//---------------------------------------
			//更新正常終了
			//---------------------------------------
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"申請者管理データ更新中にDBエラーが発生しました。",
				new ErrorInfo("errors.4002"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"申請者管理データ更新中にDBエラーが発生しました。",
					new ErrorInfo("errors.4002"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	/**
	 * 研究者情報の削除.<br /><br />
	 * 
	 * <b>1.削除データ確認</b><br/><br/>
	 * 以下のSQL文を発行し、研究者番号・所属機関コードの
	 * 同じ研究者が登録されているかどうかを確認する。<br />
	 * また、FOR UPDATEをかけることで研究者マスタテーブルをロックする。<BR/>
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		SELECT 
				COUNT(*) 
		FROM 
				MASTER_KENKYUSHA
		WHERE 
				KENKYU_NO = ?
		AND 
				SHOZOKU_CD = ? 
		FOR UPDATE</pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数infoの変数KenkyuNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数infoの変数ShozokuCdを使用する。</td></tr>
	 * </table><br /> 
	 * 検索結果が存在しない場合は例外をthrowする。<br /><br />
	 *
	 * <b>2.研究者情報削除</b><br/><br/> 
	 * 以下のSQL文を発行して、研究者情報テーブルの情報を物理削除するbr />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
	 * 
		DELETE FROM 
				MASTER_KENKYUSHA
		WHERE
				KENKYU_NO = ?
		AND
				SHOZOKU_CD = ? </pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数deleteInfoの変数KenkyuNoを使用する。</td></tr>
	 * 		<tr bgcolor="#FFFFFF"><td>所属CD</td><td>第二引数deleteInfoの変数ShozokuCdを使用する。</td></tr>
	 * </table><br />
	 * 
	 * <b>3.申請者情報更新</b><br/><br/> 
	 * ShinseishaMaintenanceのsearchメソッドを使用して申請者情報テーブルに修正する研究者の情報が含まれているかチェックする。<BR/>
	 * 該当するデータが存在する場合は、ShinseishaMaintenanceのdeleteメソッドを使用して削除フラグに1をセットする。<br>
	 * 
	 * @param userInfo UserInfo
	 * @param deleteInfo KenkyushaInfo
	 * @throws ApplicationException
	 */
	public void delete(UserInfo userInfo, KenkyushaInfo deleteInfo)
			throws ApplicationException {

		boolean success = false;
		Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();

			MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
			//削除対象情報のチェック
			try{
				dao.getKenkyushaData(connection, deleteInfo, true);
			}catch(NoDataFoundException e){
				throw new ApplicationException("削除対象の研究者が存在しません。", 
					new ErrorInfo("errors.4003"));
			}
			//削除処理
			dao.deleteKenkyushaInfo(connection, deleteInfo);
			
			//削除正常終了
			success = true;

		} catch (DataAccessException e) {
			throw new ApplicationException(
				"研究者管理データ削除中にDBエラーが発生しました。",
				new ErrorInfo("errors.4003"),
				e);
		} finally {
			try {
				if (success) {
					DatabaseUtil.commit(connection);
				} else {
					DatabaseUtil.rollback(connection);
				}
			} catch (TransactionException e) {
				throw new ApplicationException(
					"研究者管理データ削除中にDBエラーが発生しました。",
					new ErrorInfo("errors.4003"),
					e);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
		}
	}
	
	
	/**
	 * 研究者情報の取得.<br /><br />
	 * 
	 * 研究者マスタから研究者情報を取得する。
	 * 
	 * 以下のSQL文を発行して、研究者マスタテーブルからデータを取得する。<br />
	 * (バインド変数は、SQL文の下の表を参照)<br /><br />
	 * 
	 * <table border="0" bgcolor="#000000" cellpading="1" cellspacing="1" width="600">
	 * <tr bgcolor="#FFFFFF"><td><pre>
		SELECT
			KENKYU.KENKYU_NO,				//研究者番号
			KENKYU.NAME_KANJI_SEI,			//氏名（漢字等-姓）
			KENKYU.NAME_KANJI_MEI,			//氏名（漢字等-名）
			KENKYU.NAME_KANA_SEI,			//氏名（フリガナ-姓）
			KENKYU.NAME_KANA_MEI,			//氏名（フリガナ-名）
			KENKYU.SEIBETSU,				//性別
			KENKYU.BIRTHDAY,				//生年月日
			KENKYU.GAKUI,					//学位
			KENKYU.SHOZOKU_CD,				//所属機関コード
			KENKYU.BUKYOKU_CD,				//部局コード
			KENKYU.SHOKUSHU_CD,				//職コード
			KENKYU.KOSHIN_DATE,				//更新日時
			KENKYU.BIKO,					//備考
			KIKAN.SHOZOKU_NAME_KANJI,		//所属機関名（和文）
			KIKAN.SHOZOKU_NAME_EIGO,		//所属機関名（英文）
			KIKAN.SHOZOKU_RYAKUSHO,			//所属機関名（略称）
			SHOKU.SHOKUSHU_NAME,			//職名
			SHOKU.SHOKUSHU_NAME_RYAKU		//職略称
		FROM 
			MASTER_KENKYUSHA KENKYU			//研究者マスタ
		INNER JOIN 
			MASTER_KIKAN KIKAN				//所属機関マスタ
		ON 
			KENKYU.SHOZOKU_CD = KIKAN.SHOZOKU_CD
		INNER JOIN 
			MASTER_SHOKUSHU SHOKU			//職種マスタ
		ON 
			KENKYU.SHOKUSHU_CD = SHOKU.SHOKUSHU_CD
		WHERE 
			KENKYU.SHOZOKU_CD = ?
		AND 
			KENKYU.KENKYU_NO = ?
		AND 
			KENKYU.DEL_FLG = 0
	 * </pre>
	 * </td></tr>
	 * </table><br />
	 *
	 * <table border="0" cellspacing="1" cellpadding="1" bgcolor="#000000" width="600">
	 * 		<tr style="color:white;font-weight: bold"><td>列名</td><td>値</td><tr/>
	 * 		<tr bgcolor="#FFFFFF"><td>所属機関コード</td><td>第二引数ShinseishaPkの変数ShozokuCdを使用する。</td></tr>
	 *      <tr bgcolor="#FFFFFF"><td>研究者番号</td><td>第二引数ShinseishaPkの変数KenkyuNoを使用する。</td></tr>
	 * </table><br />
	 * 
	 * 検索結果から部局CDが取得できる場合は部局マスタから部局名と略称を取得する。<BR/>
	 * 
	 * 取得した値をKenkyushaInfoに格納し、返却する。<br /><br />
	 * 
	 * @param userInfo UserInfo
	 * @param pkInfo KenkyushaPk
	 * @param lock	boolean
	 * @return KenkyushaInfo
	 * @throws ApplicationException
	 */
	public KenkyushaInfo selectKenkyushaData(UserInfo userInfo, KenkyushaPk pkInfo, boolean lock)
			throws ApplicationException {

		Connection connection = null;
		KenkyushaInfo result = new KenkyushaInfo();
		try {
			connection = DatabaseUtil.getConnection();
			MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
			result = dao.getKenkyushaData(connection, pkInfo, lock);
			
			//部局が設定されている研究者の場合は部局名を取得する
			String bukyokuCd = result.getBukyokuCd();
			if(bukyokuCd != null && !bukyokuCd.equals("") && !lock){
				MasterBukyokuInfoDao bukyokuDao = new MasterBukyokuInfoDao(userInfo);
				BukyokuPk pk = new BukyokuPk();
				pk.setBukyokuCd(bukyokuCd);
				BukyokuInfo info = bukyokuDao.selectBukyokuInfo(connection, pk);
				result.setBukyokuName(info.getBukaName());				//部局名
				result.setBukyokuNameRyaku(info.getBukaRyakusyo());		//部局略称
			}
			
		} catch (DataAccessException e) {
			throw new ApplicationException(
				"研究者データ検索中にDBエラーが発生しました。",
				new ErrorInfo("errors.4004"),
				e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}
		return result;
	}
	//追加 ここまで----------------------------------------------------------------------
	
	
	/**
	 * パスワード再設定通知書出力情報の取得
	 * @param UserInfo userInfo ログイン者情報
	 * @param String[] kenkyuNo 申請者研究番号
	 * @return　FileResource　出力情報CSVファウル
	 */
	public FileResource createTsuchisho(
			UserInfo userInfo,
			String[] kenkyuNo)
			throws ApplicationException
		{
			//DBレコード取得
			List csv_data = null;
			Connection connection = null;
			try {
				connection = DatabaseUtil.getConnection();
				String sozokuCd = null;		//所属コード
				//所属担当者の場合
				if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
					sozokuCd = userInfo.getShozokuInfo().getShozokuCd();
				}
				//部局担当者の場合
				else{
					sozokuCd = userInfo.getBukyokutantoInfo().getShozokuCd();
				}

				csv_data = new ShinseishaInfoDao(userInfo).createCSV4Tsuchisho(connection, kenkyuNo, sozokuCd);
			} finally {
				DatabaseUtil.closeConnection(connection);
			}
			
			//-----------------------
			// CSVファイル出力
			//-----------------------
			String csvFileName = "SHINSEISHA";
			//2005/09/09 takano フォルダ名をミリ秒単位に変更。念のため同時に同期処理も組み込み。
			String filepath = null;
			synchronized(log){
				//2005/9/27 ロック時間が短くて同じ現象が再現した為、ログイン者IDも組み込む
				//filepath = SHINSEISHA_WORK_FOLDER + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
				//所属担当者の場合
				if(userInfo.getRole().equals(UserRole.SHOZOKUTANTO)){
					filepath = SHINSEISHA_WORK_FOLDER + userInfo.getShozokuInfo().getShozokuTantoId() + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
				}
				//部局担当者の場合
				else{
					filepath = SHINSEISHA_WORK_FOLDER + userInfo.getBukyokutantoInfo().getBukyokutantoId() + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "/";	
				}
			}
			CsvUtil.output(csv_data, filepath, csvFileName);
			
			//-----------------------
			// 依頼書ファイルのコピー
			//-----------------------
			FileUtil.fileCopy(new File(SHINSEISHA_FORMAT_PATH + SHINSEISHA_FORMAT_FILE_NAME), new File(filepath + SHINSEISHA_FORMAT_FILE_NAME));
			FileUtil.fileCopy(new File(SHINSEISHA_FORMAT_PATH + "$"), new File(filepath + "$"));
			
			//-----------------------
			// ファイルの圧縮
			//-----------------------
			String comp_file_name = csvFileName + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
			FileUtil.fileCompress(filepath, filepath, comp_file_name);
			
			//-------------------------------------
			//作成したファイルを読み込む。
			//-------------------------------------
			File exe_file = new File(filepath + comp_file_name + ".EXE");
			FileResource compFileRes = null;
			try {
				compFileRes = FileUtil.readFile(exe_file);
			} catch (IOException e) {
				throw new ApplicationException(
					"作成ファイル'" + comp_file_name + ".EXE'情報の取得に失敗しました。",
					new ErrorInfo("errors.8005"),
					e);
			}finally{
				//作業ファイルの削除
				FileUtil.delete(exe_file.getParentFile());
			}
			
			//自己解凍型圧縮ファイルをリターン
			return compFileRes;
			
		}



	/**
	 * 研究者マスタの情報から応募者情報を更新する。
	 * @param userInfo
	 * @param connection
	 * @param updateInfo
	 * @throws DataAccessException
	 * @throws ApplicationException
	 */
	private void updateShinseishaFromKenkyusha(
		UserInfo userInfo,
		Connection connection,
		KenkyushaInfo updateInfo)
		throws DataAccessException, ApplicationException
	{
		//2005/04/22　追加 ここから----------------------------------------------
		//申請者の更新はKenkyushaMaintenanceで行うように修正
		//申請者情報のチェック
		HashMap shinseiMap = new HashMap();
		ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
		searchInfo.setKenkyuNo(updateInfo.getKenkyuNo());
		searchInfo.setShozokuCd(updateInfo.getShozokuCd());
		ShinseishaMaintenance shinsei = new ShinseishaMaintenance();
		boolean existShinseishaInfo = true;
		try {
			Page shinseishaPage = shinsei.search(userInfo, searchInfo, connection);
			shinseiMap = (HashMap) shinseishaPage.getList().get(0);
		} catch (NoDataFoundException e) {
			//該当するデータがない場合はそのまま処理を終える
			existShinseishaInfo = false;
		}
		//該当するデータが申請者テーブルに存在する場合は更新処理を行う
		if (existShinseishaInfo
			&& shinseiMap.get("SHINSEISHA_ID") != null
			&& !shinseiMap.get("SHINSEISHA_ID").equals("")) {
		
			ShinseishaInfoDao shinseiDao = new ShinseishaInfoDao(userInfo);
			ShinseishaInfo shinseiInfo = new ShinseishaInfo();
			shinseiInfo.setShinseishaId((String) shinseiMap.get("SHINSEISHA_ID"));
			shinseiInfo.setBirthday(updateInfo.getBirthday());
			shinseiInfo.setBukyokuCd(updateInfo.getBukyokuCd());
			shinseiInfo.setBukyokuName(updateInfo.getBukyokuName());
			shinseiInfo.setBukyokuNameRyaku(updateInfo.getBukyokuNameRyaku());
			shinseiInfo.setKenkyuNo(updateInfo.getKenkyuNo());
			shinseiInfo.setNameKanaMei(updateInfo.getNameKanaMei());
			shinseiInfo.setNameKanaSei(updateInfo.getNameKanaSei());
			shinseiInfo.setNameKanjiMei(updateInfo.getNameKanjiMei());
			shinseiInfo.setNameKanjiSei(updateInfo.getNameKanjiSei());
			shinseiInfo.setShokushuCd(updateInfo.getShokushuCd());
			shinseiInfo.setShokushuNameKanji(updateInfo.getShokushuName());
			shinseiInfo.setShokushuNameRyaku(updateInfo.getShokushuNameRyaku());
			shinseiInfo.setShozokuCd(updateInfo.getShozokuCd());
			shinseiInfo.setShozokuName(updateInfo.getShozokuNameKanji());
			shinseiInfo.setShozokuNameRyaku(updateInfo.getShozokuRyakusho());
			shinseiInfo.setShozokuNameEigo(updateInfo.getShozokuNameEigo());
			shinseiInfo.setBukyokuShubetuName((String) shinseiMap.get("OTHER_BUKYOKU"));
			shinseiInfo.setBukyokuShubetuCd((String) shinseiMap.get("SHUBETU_CD"));
			shinseiInfo.setDelFlg(updateInfo.getDelFlg());
			shinseiInfo.setHakkoDate((Date) shinseiMap.get("HAKKO_DATE"));
			shinseiInfo.setHakkoshaId((String) shinseiMap.get("HAKKOSHA_ID"));
			shinseiInfo.setHikoboFlg(shinseiMap.get("HIKOBO_FLG").toString());
			shinseiInfo.setNameRoMei((String) shinseiMap.get("NAME_RO_MEI"));
			shinseiInfo.setNameRoSei((String) shinseiMap.get("NAME_RO_SEI"));
			shinseiInfo.setPassword((String) shinseiMap.get("PASSWORD"));
			shinseiInfo.setYukoDate((Date) shinseiMap.get("YUKO_DATE"));
			//申請者の更新
			shinseiDao.updateShinseishaInfo(connection, shinseiInfo);
		}
	}
	//追加 ここまで---------------------------------------------------------

	//2007/5/7追加
	/**
	 * 研究者マスタ更新日を取得する
	 * @return  更新日
	 */
	public String GetKenkyushaMeiboUpdateDate(UserInfo userInfo)
		throws ApplicationException
	{
		Connection connection = null;
		String strUpdateDate = "";

		try {
			connection = DatabaseUtil.getConnection();
			
			MasterKanriInfoDao mstDao = new MasterKanriInfoDao(userInfo);
			
			//研究者マスタ更新日を取得する
			strUpdateDate = mstDao.selectMeiboUpdateDate(connection);
			
		}catch(NoDataFoundException e){
			//データがない場合、空の更新日を返す
		}catch(DataAccessException e){
			//メニュー画面を正常に表示する為、エラーの場合も空の更新日を返す

			//throw new ApplicationException(
			//		"研究者データ検索中にDBエラーが発生しました。",
			//		new ErrorInfo("errors.4004"),
			//		e);
		} finally {
			DatabaseUtil.closeConnection(connection);
		}

		return strUpdateDate;
	}
	
    /**
     * 研究者名簿ダウンロードデータを取得する
     * @param userInfo
     * @return
     * @throws ApplicationException
     */
	public List searchMeiboCsvData(UserInfo userInfo)   throws ApplicationException {

	    //DBコネクションの取得
	    Connection connection = null;    
	    try{
	        connection = DatabaseUtil.getConnection();
	        
	        //---申請書一覧ページ情報
	        List csvList = null;
	        try {
	        	MasterKenkyushaInfoDao dao = new MasterKenkyushaInfoDao(userInfo);
	            csvList = dao.selectKenkyushaMeiboInfo(connection);
	        } catch (DataAccessException e) {
	            throw new ApplicationException(
	                "研究者名簿データ検索中にDBエラーが発生しました。",
	                new ErrorInfo("errors.4004"),
	                e);
	        }
	        return csvList;
	    
	    } finally {
	        DatabaseUtil.closeConnection(connection);
	    }
	}
}
