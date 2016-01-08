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
 * ����U�茋�ʏ���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: WarifuriInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class WarifuriInfo extends WarifuriPk{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �N�x */
	private String nendo;

	/** �� */
	private String kaisu;

	/** ���Ɩ� */
	private String jigyoName;

	/** �\���ԍ� */
	private String uketukeNo;
	
	/** �n���̋敪 */
	private String keiNameRyaku;

	/** �����ۑ薼�i�a���j */
	private String kadaiNameKanji;

	/** �\���Ҏ����i������-���j */
	private String nameKanjiSei;

	/** �\���Ҏ����i������-���j */
	private String nameKanjiMei;

	/** �����@�֖��i���́j */
	private String shozokuNameRyaku;

	/** ���ǖ��i���́j */
	private String bukyokuNameRyaku;

	/** �E���i���́j */
	private String shokushuNameRyaku;

	/** �R�����ԍ� */
	private String shinsainNo;

	/** �R������-�� */
	private String shinsainNameKanjiSei;

	/** �R������-�� */
	private String shinsainNameKanjiMei;

	/** �R���������@�� */
	private String shinsainShozokuName;

	/** �R�������ǖ� */
	private String shinsainBukyokuName;

	/** �R�����E�� */
	private String shinsainShokuName;

//2005/10/17�ǉ�
	/** �����ԍ� */
	private String seiriNo;

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public WarifuriInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return
	 */
	public String getBukyokuNameRyaku() {
		return bukyokuNameRyaku;
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
	public String getKadaiNameKanji() {
		return kadaiNameKanji;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @return
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String getShinsainBukyokuName() {
		return shinsainBukyokuName;
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
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @return
	 */
	public String getShinsainShokuName() {
		return shinsainShokuName;
	}

	/**
	 * @return
	 */
	public String getShinsainShozokuName() {
		return shinsainShozokuName;
	}

	/**
	 * @return
	 */
	public String getShokushuNameRyaku() {
		return shokushuNameRyaku;
	}

	/**
	 * @return
	 */
	public String getShozokuNameRyaku() {
		return shozokuNameRyaku;
	}

	/**
	 * @return
	 */
	public String getUketukeNo() {
		return uketukeNo;
	}

//2005/10/17�@�����ԍ��ǉ�
	/**
	 * @return
	 */
	public String getSeiriNo() {
		return seiriNo;
	}

	/**
	 * @param string
	 */
	public void setBukyokuNameRyaku(String string) {
		bukyokuNameRyaku = string;
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
	public void setKadaiNameKanji(String string) {
		kadaiNameKanji = string;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainBukyokuName(String string) {
		shinsainBukyokuName = string;
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
	public void setShinsainNo(String string) {
		shinsainNo = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainShokuName(String string) {
		shinsainShokuName = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainShozokuName(String string) {
		shinsainShozokuName = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameRyaku(String string) {
		shokushuNameRyaku = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameRyaku(String string) {
		shozokuNameRyaku = string;
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
	public String getKeiNameRyaku() {
		return keiNameRyaku;
	}

	/**
	 * @param string
	 */
	public void setKeiNameRyaku(String string) {
		keiNameRyaku = string;
	}
	
//2005/10/17 �����ԍ��ǉ�
	/**
	 * @param string
	 */
	public void setSeiriNo(String string) {
		seiriNo = string;
	}

}
