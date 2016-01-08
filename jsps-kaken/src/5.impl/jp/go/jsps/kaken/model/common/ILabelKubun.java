/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/08/04
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.common;

/**
 * ラベル区分を定義する。
 * 
 * ID RCSfile="$RCSfile: ILabelKubun.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public interface ILabelKubun {
	
	/** 非公募応募可フラグ */
	public static final String HIKOBO_FLG = "HIKOBO_FLG";

	/** 部局種別 */
	public static final String BUKYOKU_SHUBETU = "SHUBETU";

	/** 系統の区分（申請書入力用） */
	public static final String KEI_KUBUN = "KEI_KUBUN_";
	
	/** 推薦の観点（申請書入力用） */
	public static final String SUISEN = "SUISEN";

	/** 総合評価（ABC） */
	public static final String KEKKA_ABC = "KEKKA_ABC";

	/** 総合評価（点数） */
	public static final String KEKKA_TEN = "KEKKA_TEN";
	
	/** 総合評価（点数） 萌芽*/
	public static final String KEKKA_TEN_HOGA = "KEKKA_TEN_HOGA";
	
	/** 研究内容 */
	public static final String KENKYUNAIYO = "KENKYUNAIYO";
	
	/** 研究計画 */
	public static final String KENKYUKEIKAKU = "KENKYUKEIKAKU";
	
	/** 適切性-海外 */
	public static final String TEKISETSU_KAIGAI = "TEKISETSU_KAIGAI";
	
	/** 適切性-研究（1） */
	public static final String TEKISETSU_KENKYU1 = "TEKISETSU_KENKYU1";
	
	/** 適切性 */
	public static final String TEKISETSU = "TEKISETSU";	
	
	/** 妥当性 */
	public static final String DATO = "DATO";
	
	/** 研究代表者 */
	public static final String SHINSEISHA = "SHINSEISHA";
		
	/** 研究分担者 */
	public static final String KENKYUBUNTANSHA = "KENKYUBUNTANSHA";
	
	/** ヒトゲノム */
	public static final String HITOGENOMU = "HITOGENOMU";
	
	/** 特定胚 */
	public static final String TOKUTEI = "TOKUTEI";
	
	/** ヒトES細胞 */
	public static final String HITOES = "HITOES";
	
	/** 遺伝子組換え実験 */
	public static final String KUMIKAE = "KUMIKAE";	
	
	/** 遺伝子治療臨床研究 */
	public static final String CHIRYO = "CHIRYO";
	
	/** 疫学研究 */
	public static final String EKIGAKU = "EKIGAKU";	
	
	/** 利害関係 */
	public static final String RIGAI = "RIGAI";
	
	/** 学術的重要性・妥当性 */
	public static final String JUYOSEI = "JUYOSEI";
	
	/** 独創性・革新性 */
	public static final String DOKUSOSEI = "DOKUSOSEI";
	
	/** 波及効果・普遍性 */
	public static final String HAKYUKOKA = "HAKYUKOKA";
	
	/** 遂行能力・環境の適切性 */
	public static final String SUIKONORYOKU = "SUIKONORYOKU";	
	
	/** 人権の保護・法令等の遵守 */
	public static final String JINKEN = "JINKEN";
	
	/** 分担金配分 */
	public static final String BUNTANKIN = "BUNTANKIN";		
	
	/** 受理結果 */
	public static final String JURI_KEKKA = "JURI_KEKKA";	

	/** ２次審査結果 */
	public static final String KEKKA2 = "KEKKA2";
	
	
	/** アンケートラジオボタン */
	public static final String BENRI1 = "BENRI1";
	
	public static final String RIKAI1 = "RIKAI1";
	
	public static final String RIKAI2 = "RIKAI2";
	
	public static final String RIKAI3 = "RIKAI3";
	
	public static final String YONDA1 = "YONDA1";
	
	public static final String BENRI2 = "BENRI2";
	
	public static final String CALLRIYOU = "CALLRIYOU";
	
	public static final String CALLRIKAI = "CALLRIKAI";
	
	/** アンケートプルダウン */
	public static final String OS = "OS";
	
	public static final String WEB = "WEB";
	
	public static final String KEISIKI = "KEISIKI";
	
	public static final String RIYOUTIME = "RIYOUTIME";
	
	public static final String TOIAWASE1 = "TOIAWASE1";

	/** 応募者からの問い合わせの内容リスト */
	public static final String OUBO_TOI = "OUBO_TOI";
	
	/** 部局担当者からの問い合わせの内容リスト */
	public static final String BUKYOKU_TOI = "BUKYOKU_TOI";
    
//2006/06/21 苗　追加ここから    
    /** 審査希望分野 */
    public static final String SHINSAKIBO_BUNYA = "SHINSAKIBO_BUNYA";
    
    /** 事前調査 */
    public static final String JIZENCHOSA = "JIZENCHOSA";
    
    /** 研究必要性 */
    public static final String KENKYU_HITSUYOUSEI = "KENKYU_HITSUYOUSEI";
    
    /** 参考資料ファイル選択 */
    public static final String RYOIKI_TENPU_FLAG = "RYOIKI_TENPU_FLAG";
//2006/06/21　苗　追加ここまで
    
//2007/02/08 苗　追加ここから
    /** 審査希望分野（若手用） */
    public static final String SHINSAKIBO_BUNYA_WAKA = "SHINSAKIBO_WAKA";
//2007/02/08　苗　追加ここまで    
}
