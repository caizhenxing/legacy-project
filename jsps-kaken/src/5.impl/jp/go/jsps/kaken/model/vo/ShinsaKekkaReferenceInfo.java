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

import java.util.Arrays;
import java.util.List;

/**
 * 1���R�����ʏ��i�Q�Ɨp�j��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaReferenceInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class ShinsaKekkaReferenceInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �\���ԍ� */
	private String    uketukeNo;
	
	/** �N�x */
	private String    nendo;
	
	/** �� */
	private String    kaisu;
	
	/** ����ID */
	private String    jigyoId;
	
	/** ���Ɩ� */
	private String    jigyoName;
	
	/** ���ƃR�[�h */
	private String    jigyoCd;
	
	/** �\���Ҏ����i������-���j */
	private String    nameKanjiSei;
	
	/** �\���Ҏ����i������-���j */
	private String    nameKanjiMei;
		
	/** �����ۑ薼(�a���j */
	private String    kadaiNameKanji;

	/** �����@�֖� */
	private String    shozokuName;
	
	/** ���ǖ� */
	private String    bukyokuName;
	
	/** �E�� */
	private String    shokushuNameKanji;

	/** �����ԍ� */
	private String    kenkyuNo;
			
	/** 1���R�����ʏ�� */
	private ShinsaKekkaInfo[] shinsaKekkaInfo;
	
	/** �Ɩ��S���Ҕ��l */
	private String    shinsa1Biko;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsaKekkaReferenceInfo() {
		super();
	}
	
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------	
	
	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
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
	public ShinsaKekkaInfo[] getShinsaKekkaInfo() {
		return shinsaKekkaInfo;
	}
	
	/**
	 * 
	 * @return
	 */
	public List getShinsaKekkaInfoList(){
		return Arrays.asList(shinsaKekkaInfo);
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
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
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
	 * @param infos
	 */
	public void setShinsaKekkaInfo(ShinsaKekkaInfo[] infos) {
		shinsaKekkaInfo = infos;
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
	public String getShinsa1Biko() {
		return shinsa1Biko;
	}

	/**
	 * @param string
	 */
	public void setShinsa1Biko(String string) {
		shinsa1Biko = string;
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
	public String getKenkyuNo() {
		return kenkyuNo;
	}

	/**
	 * @return
	 */
	public String getShokushuNameKanji() {
		return shokushuNameKanji;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
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
	public void setKenkyuNo(String string) {
		kenkyuNo = string;
	}

	/**
	 * @param string
	 */
	public void setShokushuNameKanji(String string) {
		shokushuNameKanji = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

}
