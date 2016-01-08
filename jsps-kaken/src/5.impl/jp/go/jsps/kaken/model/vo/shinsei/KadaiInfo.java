/*======================================================================
 *    SYSTEM      : “ú–{ŠwpU‹»‰ï“dq\¿ƒVƒXƒeƒ€i‰ÈŠwŒ¤‹†”ï•â•‹àj
 *    Source name : KadaiInfo.java
 *    Description : Œ¤‹†‰Û‘èî•ñ‚ğ•Û‚·‚éƒNƒ‰ƒXB
 *
 *    Author      : Admin
 *    Date        : 2003/01/10
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/07/16    v1.0        Admin          V‹Kì¬
 *    2006/07/20    v1.1        DIS.dyh        •ÏX
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo.shinsei;

import jp.go.jsps.kaken.model.vo.ShinseiDataPk;

/**
 * Œ¤‹†‰Û‘èî•ñ‚ğ•Û‚·‚éƒNƒ‰ƒXB
 * 
 * ID RCSfile="$RCSfile: KadaiInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public class KadaiInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** Œ¤‹†‰Û‘è–¼(˜a•¶j */
	private String kadaiNameKanji;
	
	/** Œ¤‹†‰Û‘è–¼(‰p•¶j */
	private String kadaiNameEigo;
	
	/** –‹Æ‹æ•ª */
	private String jigyoKubun;
	
	/** R¸‹æ•ª */
	private String shinsaKubun;
	
	/** R¸‹æ•ª–¼Ì */
	private String shinsaKubunMeisho;
	
	/** •ªŠ„”Ô† */
	private String bunkatsuNo;
	
	/** •ªŠ„”Ô†–¼Ì */
	private String bunkatsuNoMeisho;
	
	/** Œ¤‹†‘ÎÛ‚Ì—ŞŒ^ */
	private String kenkyuTaisho;
	
	/** Œn“‚Ì‹æ•ª”Ô† */
	private String keiNameNo;
	
	/** Œn“‚Ì‹æ•ª */
	private String keiName;
	
	/** Œn“‚Ì‹æ•ªi—ªÌj */
	private String keiNameRyaku;
	
	/** ×–Ú”Ô† */
	private String bunkaSaimokuCd;
	
	/** •ª–ì */
	private String bunya;
	
	/** •ª‰È */
	private String bunka;
	
	/** ×–Ú */
	private String saimokuName;
	
	/** ×–Ú”Ô†2 */
	private String bunkaSaimokuCd2;
	
	/** •ª–ì2 */
	private String bunya2;
	
	/** •ª‰È2 */
	private String bunka2;
	
	/** ×–Ú2 */
	private String saimokuName2;
	
	/** „‘E‚ÌŠÏ“_”Ô† */
	private String kantenNo;
	
	/** „‘E‚ÌŠÏ“_ */
	private String kanten;
	
	/** „‘E‚ÌŠÏ“_—ªÌ */
	private String kantenRyaku;
	
	/** ”Å */
	private int edition;
	
	
	//EEEEEEEEEE

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * ƒRƒ“ƒXƒgƒ‰ƒNƒ^B
	 */
	public KadaiInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

    /**
     * Œ¤‹†‰Û‘è–¼(˜a•¶j‚ğæ“¾
     * @return Œ¤‹†‰Û‘è–¼(˜a•¶j
     */
    public String getKadaiNameKanji() {
        return kadaiNameKanji;
    }

    /**
     * Œ¤‹†‰Û‘è–¼(˜a•¶j‚ğİ’è
     * @param string Œ¤‹†‰Û‘è–¼(˜a•¶j
     */
    public void setKadaiNameKanji(String string) {
        kadaiNameKanji = string;
    }

    /**
     * Œ¤‹†‰Û‘è–¼(‰p•¶j‚ğæ“¾
     * @return Œ¤‹†‰Û‘è–¼(‰p•¶j
     */
    public String getKadaiNameEigo() {
        return kadaiNameEigo;
    }

    /**
     * Œ¤‹†‰Û‘è–¼(‰p•¶j‚ğİ’è
     * @param string Œ¤‹†‰Û‘è–¼(‰p•¶j
     */
    public void setKadaiNameEigo(String string) {
        kadaiNameEigo = string;
    }

    /**
     * –‹Æ‹æ•ª‚ğİ’è
     * @return –‹Æ‹æ•ª
     */
    public String getJigyoKubun() {
        return jigyoKubun;
    }

    /**
     * –‹Æ‹æ•ª‚ğİ’è
     * @param string –‹Æ‹æ•ª
     */
    public void setJigyoKubun(String string) {
        jigyoKubun = string;
    }

    /**
     * R¸‹æ•ª‚ğæ“¾
     * @return R¸‹æ•ª
     */
    public String getShinsaKubun() {
        return shinsaKubun;
    }

    /**
     * R¸‹æ•ª‚ğİ’è
     * @param string R¸‹æ•ª
     */
    public void setShinsaKubun(String string) {
        shinsaKubun = string;
    }

    /**
     * R¸‹æ•ª–¼Ì‚ğæ“¾
     * @return R¸‹æ•ª–¼Ì
     */
    public String getShinsaKubunMeisho() {
        return shinsaKubunMeisho;
    }

    /**
     * R¸‹æ•ª–¼Ì‚ğİ’è
     * @param string R¸‹æ•ª–¼Ì
     */
    public void setShinsaKubunMeisho(String string) {
        shinsaKubunMeisho = string;
    }

    /**
     * •ªŠ„”Ô†‚ğæ“¾
     * @return •ªŠ„”Ô†
     */
    public String getBunkatsuNo() {
        return bunkatsuNo;
    }

    /**
     * •ªŠ„”Ô†‚ğİ’è
     * @param string •ªŠ„”Ô†
     */
    public void setBunkatsuNo(String string) {
        bunkatsuNo = string;
    }

    /**
     * •ªŠ„”Ô†–¼Ì‚ğæ“¾
     * @return •ªŠ„”Ô†–¼Ì
     */
    public String getBunkatsuNoMeisho() {
        return bunkatsuNoMeisho;
    }

    /**
     * •ªŠ„”Ô†–¼Ì‚ğİ’è
     * @param string •ªŠ„”Ô†–¼Ì
     */
    public void setBunkatsuNoMeisho(String string) {
        bunkatsuNoMeisho = string;
    }

    /**
     * Œ¤‹†‘ÎÛ‚Ì—ŞŒ^‚ğæ“¾
     * @return Œ¤‹†‘ÎÛ‚Ì—ŞŒ^
     */
    public String getKenkyuTaisho() {
        return kenkyuTaisho;
    }

    /**
     * Œ¤‹†‘ÎÛ‚Ì—ŞŒ^‚ğİ’è
     * @param string Œ¤‹†‘ÎÛ‚Ì—ŞŒ^
     */
    public void setKenkyuTaisho(String string) {
        kenkyuTaisho = string;
    }

    /**
     * Œn“‚Ì‹æ•ª”Ô†‚ğæ“¾
     * @return Œn“‚Ì‹æ•ª”Ô†
     */
    public String getKeiNameNo() {
        return keiNameNo;
    }

    /**
     * Œn“‚Ì‹æ•ª”Ô†‚ğİ’è
     * @param string Œn“‚Ì‹æ•ª”Ô†
     */
    public void setKeiNameNo(String string) {
        keiNameNo = string;
    }

    /**
     * Œn“‚Ì‹æ•ª‚ğæ“¾
     * @return Œn“‚Ì‹æ•ª
     */
    public String getKeiName() {
        return keiName;
    }

    /**
     * Œn“‚Ì‹æ•ª‚ğİ’è
     * @param string Œn“‚Ì‹æ•ª
     */
    public void setKeiName(String string) {
        keiName = string;
    }

    /**
     * Œn“‚Ì‹æ•ªi—ªÌj‚ğæ“¾
     * @return Œn“‚Ì‹æ•ªi—ªÌj
     */
    public String getKeiNameRyaku() {
        return keiNameRyaku;
    }

    /**
     * Œn“‚Ì‹æ•ªi—ªÌj‚ğİ’è
     * @param string Œn“‚Ì‹æ•ªi—ªÌj
     */
    public void setKeiNameRyaku(String string) {
        keiNameRyaku = string;
    }

    /**
     * ×–Ú”Ô†‚ğæ“¾
     * @return ×–Ú”Ô†
     */
    public String getBunkaSaimokuCd() {
        return bunkaSaimokuCd;
    }

    /**
     * ×–Ú”Ô†‚ğİ’è
     * @param string ×–Ú”Ô†
     */
    public void setBunkaSaimokuCd(String string) {
        bunkaSaimokuCd = string;
    }

    /**
     * •ª–ì‚ğæ“¾
     * @return •ª–ì
     */
    public String getBunya() {
        return bunya;
    }

    /**
     * •ª–ì‚ğİ’è
     * @param string •ª–ì
     */
    public void setBunya(String string) {
        bunya = string;
    }

	/**
     * •ª‰È‚ğæ“¾
	 * @return String •ª‰È
	 */
	public String getBunka() {
		return bunka;
	}

    /**
     * •ª‰È‚ğİ’è
     * @param string •ª‰È
     */
    public void setBunka(String string) {
        bunka = string;
    }

    /**
     * ×–Ú‚ğæ“¾
     * @return ×–Ú
     */
    public String getSaimokuName() {
        return saimokuName;
    }

    /**
     * ×–Ú‚ğİ’è
     * @param string ×–Ú
     */
    public void setSaimokuName(String string) {
        saimokuName = string;
    }

    /**
     * ×–Ú”Ô†2‚ğæ“¾
     * @return ×–Ú”Ô†2
     */
    public String getBunkaSaimokuCd2() {
        return bunkaSaimokuCd2;
    }

    /**
     * ×–Ú”Ô†2‚ğİ’è
     * @param string ×–Ú”Ô†2
     */
    public void setBunkaSaimokuCd2(String string) {
        bunkaSaimokuCd2 = string;
    }

    /**
     * •ª–ì2‚ğæ“¾
     * @return •ª–ì2
     */
    public String getBunya2() {
        return bunya2;
    }

    /**
     * •ª–ì2‚ğİ’è
     * @param string •ª–ì2
     */
    public void setBunya2(String string) {
        bunya2 = string;
    }

	/**
     * •ª‰È2‚ğæ“¾
	 * @return •ª‰È2
	 */
	public String getBunka2() {
		return bunka2;
	}

    /**
     * •ª‰È2‚ğİ’è
     * @param string •ª‰È2
     */
    public void setBunka2(String string) {
        bunka2 = string;
    }

    /**
     * ×–Ú2‚ğæ“¾
     * @return ×–Ú2
     */
    public String getSaimokuName2() {
        return saimokuName2;
    }

    /**
     * ×–Ú2‚ğİ’è
     * @param string ×–Ú2
     */
    public void setSaimokuName2(String string) {
        saimokuName2 = string;
    }

    /**
     * „‘E‚ÌŠÏ“_”Ô†‚ğæ“¾
     * @return „‘E‚ÌŠÏ“_”Ô†
     */
    public String getKantenNo() {
        return kantenNo;
    }

    /**
     * „‘E‚ÌŠÏ“_”Ô†‚ğİ’è
     * @param string „‘E‚ÌŠÏ“_”Ô†
     */
    public void setKantenNo(String string) {
        kantenNo = string;
    }

	/**
     * „‘E‚ÌŠÏ“_‚ğæ“¾
	 * @return „‘E‚ÌŠÏ“_
	 */
	public String getKanten() {
		return kanten;
	}

    /**
     * „‘E‚ÌŠÏ“_‚ğİ’è
     * @param string „‘E‚ÌŠÏ“_
     */
    public void setKanten(String string) {
        kanten = string;
    }

	/**
     * „‘E‚ÌŠÏ“_—ªÌ‚ğæ“¾
	 * @return „‘E‚ÌŠÏ“_—ªÌ
	 */
	public String getKantenRyaku() {
		return kantenRyaku;
	}

    /**
     * „‘E‚ÌŠÏ“_—ªÌ‚ğİ’è
     * @param string „‘E‚ÌŠÏ“_—ªÌ
     */
    public void setKantenRyaku(String string) {
        kantenRyaku = string;
    }

	/**
     * ”Å‚ğİ’è
	 * @return int ”Å
	 */
	public int getEdition() {
		return edition;
	}

	/**
     * ”Å‚ğİ’è
	 * @param edition ”Å
	 */
	public void setEdition(int edition) {
		this.edition = edition;
	}
}
