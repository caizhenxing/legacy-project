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

/**
 * ���ȍזڏ���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: RyouikiInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class RyouikiInfo extends RyouikiInfoPk {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �̈旪�̖� */
	private String ryoikiName;

	/** ���l */
	private String biko;
	
	/** ����t���O�@*/
	private String kobou;
	
	/** �v�挤���t���O�@*/
	private String keikaku;
    
    //add start liuyi 206/06/30
    /** �O�N�x����t���O�@*/
    private String zennendoOuboFlg;
    
    /** �ݒ���ԁi�J�n�N�x�j�@*/
    private String settelKikanKaishi;
    
    /** �ݒ���ԁi�I���N�x�j�@*/
    private String settelKikanShuryo;
    
    /** �ݒ���ԁ@*/
    private String settelKikan;
    
    //add end liuyi 2006/06/30

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public RyouikiInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return �̈旪�̖�
	 */
	public String getRyoikiName() {
		return ryoikiName;
	}

	/**
	 * @return ���l
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @param string �̈旪�̖�
	 */
	public void setRyoikiName(String string) {
		ryoikiName = string;
	}

	/**
	 * @param string ���l
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @return ����t���O
	 */
	public String getKobou(){
		return kobou;
	}
	
	/**
	 * @param str�@����t���O
	 */
	public void setKobou(String str){
		kobou = str;
	}
	/**
	 * @return keikaku �v�挤���t���O
	 */
	public String getKeikaku()
	{
		return keikaku;
	}
	/**
	 * @param keikaku �v�挤���t���O��ݒ肵�܂��B
	 */
	public void setKeikaku(String keikaku)
	{
		this.keikaku = keikaku;
	}

    /**
     * @return Returns the settelKikanKaishi.
     */
    public String getSettelKikanKaishi() {
        return settelKikanKaishi;
    }

    /**
     * @param settelKikanKaishi The settelKikanKaishi to set.
     */
    public void setSettelKikanKaishi(String settelKikanKaishi) {
        this.settelKikanKaishi = settelKikanKaishi;
    }

    /**
     * @return Returns the settelKikanShuryo.
     */
    public String getSettelKikanShuryo() {
        return settelKikanShuryo;
    }

    /**
     * @param settelKikanShuryo The settelKikanShuryo to set.
     */
    public void setSettelKikanShuryo(String settelKikanShuryo) {
        this.settelKikanShuryo = settelKikanShuryo;
    }

    /**
     * @return Returns the zennendoOuboFlg.
     */
    public String getZennendoOuboFlg() {
        return zennendoOuboFlg;
    }

    /**
     * @param zennendoOuboFlg The zennendoOuboFlg to set.
     */
    public void setZennendoOuboFlg(String zennendoOuboFlg) {
        this.zennendoOuboFlg = zennendoOuboFlg;
    }

    /**
     * @return Returns the settelKikan.
     */
    public String getSettelKikan() {
        return settelKikan;
    }

    /**
     * @param settelKikan The settelKikan to set.
     */
    public void setSettelKikan(String settelKikan) {
        this.settelKikan = settelKikan;
    }
}
