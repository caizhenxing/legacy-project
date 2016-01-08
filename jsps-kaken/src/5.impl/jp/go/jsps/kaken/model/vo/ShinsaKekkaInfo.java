/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */

package jp.go.jsps.kaken.model.vo;

import java.io.File;
import java.util.*;

/**
 * 審査結果情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:15 $"
 */
public class ShinsaKekkaInfo extends ShinsaKekkaPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 申請番号 */
	private String uketukeNo;

	/** シーケンス番号 */
	private String seqNo;

	/** 審査区分 */
	private String shinsaKubun;
			
	/** 審査員名（漢字－姓） */
	private String shinsainNameKanjiSei;
	
	/** 審査員名（漢字－名） */
	private String shinsainNameKanjiMei;

	/** 審査員名（フリガナ－姓） */
	private String nameKanaSei;
	
	/** 審査員名（フリガナ－名） */
	private String nameKanaMei;
	
	/** 審査員所属機関名 */
	private String shozokuName;
	
	/** 審査員部局名 */
	private String bukyokuName;
	
	/** 審査員職名 */
	private String shokushuName;

	/** 事業ID */
	private String jigyoId;
	
	/** 事業名 */
	private String jigyoName;

	/** 細目番号 */
	private String bunkaSaimokuCd;	

	/** 枝番 */
	private String edaNo;	

	/** チェックデジット */
	private String checkDigit;	

	/** 総合評価（ABC） */
	private String kekkaAbc;
	
	/** 総合評価（ABC）表示ラベル */
	private String kekkaAbcLabel;
		
	/** 総合評価（点数） */
	private String kekkaTen;

	/** 総合評価（点数）表示ラベル */
	private String kekkaTenLabel;

	/** 総合評価（萌芽）表示ラベル */
	private String kekkaTenHogaLabel;

	/** コメント1 */
	private String comment1;

	/** コメント2 */
	private String comment2;

	/** コメント3 */
	private String comment3;

	/** コメント4 */
	private String comment4;

	/** コメント5 */
	private String comment5;

	/** コメント6 */
	private String comment6;

	/** 研究内容 */
	private String kenkyuNaiyo;

	/** 研究計画 */
	private String kenkyuKeikaku;

	/** 適切性-海外 */
	private String tekisetsuKaigai;

	/** 適切性-研究(1) */
	private String tekisetsuKenkyu1;

	/** 適切性 */
	private String tekisetsu;

	/** 妥当性 */
	private String dato;

	/** 研究代表者 */
	private String shinseisha;

	/** 研究分担者 */
	private String kenkyuBuntansha;

	/** ヒトゲノム */
	private String hitogenomu;

	/** 特定胚 */
	private String tokutei;

	/** ヒトES細胞 */
	private String hitoEs;

	/** 遺伝子組換え実験 */
	private String kumikae;

	/** 遺伝子治療臨床研究 */
	private String chiryo;

	/** 疫学研究 */
	private String ekigaku;

	/** コメント */
	private String comments;
	
	
	
	/** 利害関係 */
	private String rigai;
	
	/** 代理フラグ */
	private String dairi;
	
	/** 若手(S)としての妥当性 */
	private String wakates;

	/** 学術的重要性・妥当性 */
	private String juyosei;

	/** 独創性・革新性 */
	private String dokusosei;

	/** 波及効果・普遍性 */
	private String hakyukoka;

	/** 遂行能力・環境の適切性 */
	private String suikonoryoku;

	/** 人権の保護・法令等の遵守 */
	private String jinken;
	
	/** 分担金 */
	private String buntankin;

	/** その他コメント */
	private String otherComment;
	
	/** 割り振り更新日 */
	private Date koshinDate;
	
	/** 添付ファイル格納パス */
	private String tenpuPath;
	
	/** 分担金配分 */
	private String shinsaJokyo;
	
	/** 備考 */
	private String biko;

	/** 添付ファイル名 */
	private String tenpuName;

	/** 添付ファイルフラグ */
	private String tenpuFlg;	
	
	/** 事業コード */
	private String jigyoCd;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsaKekkaInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @return
	 */
	public String getBukyokuName() {
		return bukyokuName;
	}

	/**
	 * @return
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}
	
	/**
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public String getShinsainNameKanjiMei() {
		return shinsainNameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getShinsainNameKanjiSei() {
		return shinsainNameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @return
	 */
	public String getTenpuPath() {
		return tenpuPath;
	}

	/**
	 * @return
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @param string
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @param string
	 */
	public void setBukyokuName(String string) {
		bukyokuName = string;
	}

	/**
	 * @param string
	 */
	public void setComments(String string) {
		comments = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
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
	public void setShinsainNameKanjiMei(String string) {
		shinsainNameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNameKanjiSei(String string) {
		shinsainNameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @param string
	 */
	public void setTenpuPath(String string) {
		tenpuPath = string;
	}

	/**
	 * @param string
	 */
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @return
	 */
	public String getTenpuName() {
		if(tenpuName == null && tenpuPath != null){
			tenpuName = new File(tenpuPath).getName();
		}
		return tenpuName;
	}

	/**
	 * @return
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

	/**
	 * @return
	 */
	public String getCheckDigit() {
		return checkDigit;
	}

	/**
	 * @return
	 */
	public String getChiryo() {
		return chiryo;
	}

	/**
	 * @return
	 */
	public String getComment1() {
		return comment1;
	}

	/**
	 * @return
	 */
	public String getComment2() {
		return comment2;
	}

	/**
	 * @return
	 */
	public String getComment3() {
		return comment3;
	}

	/**
	 * @return
	 */
	public String getComment4() {
		return comment4;
	}

	/**
	 * @return
	 */
	public String getComment5() {
		return comment5;
	}

	/**
	 * @return
	 */
	public String getComment6() {
		return comment6;
	}

	/**
	 * @return
	 */
	public String getDato() {
		return dato;
	}

	/**
	 * @return
	 */
	public String getEdaNo() {
		return edaNo;
	}

	/**
	 * @return
	 */
	public String getEkigaku() {
		return ekigaku;
	}

	/**
	 * @return
	 */
	public String getHitoEs() {
		return hitoEs;
	}

	/**
	 * @return
	 */
	public String getHitogenomu() {
		return hitogenomu;
	}

	/**
	 * @return
	 */
	public String getKenkyuBuntansha() {
		return kenkyuBuntansha;
	}

	/**
	 * @return
	 */
	public String getKenkyuKeikaku() {
		return kenkyuKeikaku;
	}

	/**
	 * @return
	 */
	public String getKenkyuNaiyo() {
		return kenkyuNaiyo;
	}

	/**
	 * @return
	 */
	public String getKumikae() {
		return kumikae;
	}

	/**
	 * @return
	 */
	public String getNameKanaMei() {
		return nameKanaMei;
	}

	/**
	 * @return
	 */
	public String getNameKanaSei() {
		return nameKanaSei;
	}

	/**
	 * @return
	 */
	public String getShinsaJokyo() {
		return shinsaJokyo;
	}

	/**
	 * @return
	 */
	public String getShinseisha() {
		return shinseisha;
	}

	/**
	 * @return
	 */
	public String getShokushuName() {
		return shokushuName;
	}

	/**
	 * @return
	 */
	public String getKekkaAbc() {
		return kekkaAbc;
	}

	/**
	 * @return
	 */
	public String getTekisetsu() {
		return tekisetsu;
	}

	/**
	 * @return
	 */
	public String getTekisetsuKaigai() {
		return tekisetsuKaigai;
	}

	/**
	 * @return
	 */
	public String getTekisetsuKenkyu1() {
		return tekisetsuKenkyu1;
	}

	/**
	 * @return
	 */
	public String getTokutei() {
		return tokutei;
	}

	/**
	 * @param string
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setCheckDigit(String string) {
		checkDigit = string;
	}

	/**
	 * @param string
	 */
	public void setChiryo(String string) {
		chiryo = string;
	}

	/**
	 * @param string
	 */
	public void setComment1(String string) {
		comment1 = string;
	}

	/**
	 * @param string
	 */
	public void setComment2(String string) {
		comment2 = string;
	}

	/**
	 * @param string
	 */
	public void setComment3(String string) {
		comment3 = string;
	}

	/**
	 * @param string
	 */
	public void setComment4(String string) {
		comment4 = string;
	}

	/**
	 * @param string
	 */
	public void setComment5(String string) {
		comment5 = string;
	}

	/**
	 * @param string
	 */
	public void setComment6(String string) {
		comment6 = string;
	}

	/**
	 * @param string
	 */
	public void setDato(String string) {
		dato = string;
	}

	/**
	 * @param string
	 */
	public void setEdaNo(String string) {
		edaNo = string;
	}

	/**
	 * @param string
	 */
	public void setEkigaku(String string) {
		ekigaku = string;
	}

	/**
	 * @param string
	 */
	public void setHitoEs(String string) {
		hitoEs = string;
	}

	/**
	 * @param string
	 */
	public void setHitogenomu(String string) {
		hitogenomu = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuBuntansha(String string) {
		kenkyuBuntansha = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuKeikaku(String string) {
		kenkyuKeikaku = string;
	}

	/**
	 * @param string
	 */
	public void setKenkyuNaiyo(String string) {
		kenkyuNaiyo = string;
	}

	/**
	 * @param string
	 */
	public void setKumikae(String string) {
		kumikae = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaMei(String string) {
		nameKanaMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanaSei(String string) {
		nameKanaSei = string;
	}

	/**
	 * @param string
	 */
	public void setShinsaJokyo(String string) {
		shinsaJokyo = string;
	}

	/**
	 * @param string
	 */
	public void setShinseisha(String string) {
		shinseisha = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuName(String string) {
		shokushuName = string;
	}

	/**
	 * @param string
	 */
	public void setKekkaAbc(String string) {
		kekkaAbc = string;
	}

	/**
	 * @param string
	 */
	public void setTekisetsu(String string) {
		tekisetsu = string;
	}

	/**
	 * @param string
	 */
	public void setTekisetsuKaigai(String string) {
		tekisetsuKaigai = string;
	}

	/**
	 * @param string
	 */
	public void setTekisetsuKenkyu1(String string) {
		tekisetsuKenkyu1 = string;
	}

	/**
	 * @param string
	 */
	public void setTenpuName(String string) {
		tenpuName = string;
	}

	/**
	 * @param string
	 */
	public void setTokutei(String string) {
		tokutei = string;
	}

	/**
	 * @return
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @return
	 */
	public String getShinsaKubun() {
		return shinsaKubun;
	}

	/**
	 * @param string
	 */
	public void setSeqNo(String string) {
		seqNo = string;
	}

	/**
	 * @param string
	 */
	public void setShinsaKubun(String string) {
		shinsaKubun = string;
	}

	/**
	 * @return
	 */
	public String getKekkaTen() {
		return kekkaTen;
	}

	/**
	 * @param string
	 */
	public void setKekkaTen(String string) {
		kekkaTen = string;
	}

	/**
	 * @return
	 */
	public String getKekkaAbcLabel() {
		return kekkaAbcLabel;
	}

	/**
	 * @param string
	 */
	public void setKekkaAbcLabel(String string) {
		kekkaAbcLabel = string;
	}

	/**
	 * @return
	 */
	public String getKekkaTenLabel() {
		return kekkaTenLabel;
	}

	/**
	 * @param string
	 */
	public void setKekkaTenLabel(String string) {
		kekkaTenLabel = string;
	}

	/**
	 * @return
	 */
	public String getKekkaTenHogaLabel() {
		return kekkaTenHogaLabel;
	}

	/**
	 * @param string
	 */
	public void setKekkaTenHogaLabel(String string) {
		kekkaTenHogaLabel = string;
	}


	/**
	 * @return
	 */
	public String getTenpuFlg() {
		return tenpuFlg;
	}

	/**
	 * @param string
	 */
	public void setTenpuFlg(String string) {
		tenpuFlg = string;
	}

	/**
	 * @return
	 */
	public String getBuntankin() {
		return buntankin;
	}

	/**
	 * @return
	 */
	public String getDokusosei() {
		return dokusosei;
	}

	/**
	 * @return
	 */
	public String getHakyukoka() {
		return hakyukoka;
	}

	/**
	 * @return
	 */
	public String getJinken() {
		return jinken;
	}

	/**
	 * @return
	 */
	public String getJuyosei() {
		return juyosei;
	}

	/**
	 * @return
	 */
	public String getOtherComment() {
		return otherComment;
	}

	/**
	 * @return
	 */
	public String getRigai() {
		return rigai;
	}

	/**
	 * @return
	 */
	public String getSuikonoryoku() {
		return suikonoryoku;
	}

	/**
	 * @param string
	 */
	public void setBuntankin(String string) {
		buntankin = string;
	}

	/**
	 * @param string
	 */
	public void setDokusosei(String string) {
		dokusosei = string;
	}

	/**
	 * @param string
	 */
	public void setHakyukoka(String string) {
		hakyukoka = string;
	}

	/**
	 * @param string
	 */
	public void setJinken(String string) {
		jinken = string;
	}

	/**
	 * @param string
	 */
	public void setJuyosei(String string) {
		juyosei = string;
	}

	/**
	 * @param string
	 */
	public void setOtherComment(String string) {
		otherComment = string;
	}

	/**
	 * @param string
	 */
	public void setRigai(String string) {
		rigai = string;
	}

	/**
	 * @param string
	 */
	public void setSuikonoryoku(String string) {
		suikonoryoku = string;
	}

	/**
	 * @return
	 */
	public String getDairi() {
		return dairi;
	}

	/**
	 * @param string
	 */
	public void setDairi(String string) {
		dairi = string;
	}

	/**
	 * @return
	 */
	public Date getKoshinDate() {
		return koshinDate;
	}

	/**
	 * @param date
	 */
	public void setKoshinDate(Date date) {
		koshinDate = date;
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
     * wakatesを取得します。
     * 
     * @return wakates
     */
    
    public String getWakates() {
    	return wakates;
    }

	/**
     * wakatesを設定します。
     * 
     * @param wakates wakates
     */
    
    public void setWakates(String wakates) {
    	this.wakates = wakates;
    }
		
}
