/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * �]����������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: HyokaSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class HyokaSearchInfo extends SearchInfo{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
//	/** ���Ɩ� */
//	private List jigyoList = new ArrayList();
//	
//	/** �N�x */
//	private String nendo;
//	
//	/** �� */
//	private String kaisu;
//	
//	/** ����E�n�� */
//	private String bunya;
//	
//	/** �\���Җ��E�� */
//	private String nameSei;
//	
//	/** �\���Җ��E�� */
//	private String nameMei;
//	
//	/** �\���Җ��E���[�}��-�� */
//	private String nameRoSei;
//	
//	/** �\���Җ��E���[�}��-�� */
//	private String nameRoMei;
//	
//	/** �\���ԍ� */
//	private String shinseiNo;
//	
//	/** �]���iFrom�j */
//	private String hyokaFrom;
//	
//	/** �]���iTo�j */
//	private String hyokaTo;
//	
//	/** �\������ */
//	private String hyojiHoshiki;

	/** �N�x */
	private String nendo;

	/** �� */
	private String kaisu;

	/** ����E�n�� */
	private String bunya;

	/** �\���Ҏ����i���j */
	private String nameKanjiSei;

	/** �\���Ҏ����i���j */
	private String nameKanjiMei;

	/** �\���Ҏ����i���[�}��-���j */
	private String nameRoSei;

	/** �\���Ҏ����i���[�}��-���j */
	private String nameRoMei;

	/** �\���Ҕԍ� */
	private String uketukeNo;

	/** �\������ */
	private String hyojiHoshiki;

//	/** ���ƃR�[�h */
//	private String jigyoCd;

	/** �n���̋敪 */
	private String keiName;

	/** �\���Ҏ����i�t���K�i�j */
	private String nameKanaSei;

	/** �\���Ҏ����i�t���K�i�j */
	private String nameKanaMei;

	/** �����@�փR�[�h */
	private String shozokuCd;

	/** �זڔԍ� */
	private String bunkasaimokuCd;

//2005/10/18�����ԍ��ǉ�	
	/** �����ԍ� */
	private String seiriNo;
	
//	/** ���Ɩ����X�g */
//	private List jigyoList;

	/** ���Ƌ敪 */
	private String jigyoKubun;

	/** �\�������i��՗p�j */
	private String hyojiHoshikiKiban;

//	/** �\���������X�g�i��՗p�j */
//	private List hyojiHoshikiListKiban;

	/** �]���i�_�j�i���j */
	private String hyokaHigh;

	/** �]���i�_�j�i��j */
	private String hyokaLow;

	/** �C�O���� */
	private String kaigaibunyaName;

	/** ���ƑI��l���X�g */
	private List values = new ArrayList();

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public HyokaSearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getBunkasaimokuCd() {
		return bunkasaimokuCd;
	}

	/**
	 * @return
	 */
	public String getBunya() {
		return bunya;
	}

	/**
	 * @return
	 */
	public String getHyojiHoshiki() {
		return hyojiHoshiki;
	}

//	/**
//	 * @return
//	 */
//	public String getJigyoCd() {
//		return jigyoCd;
//	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @return
	 */
	public String getKeiName() {
		return keiName;
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
	public String getNameRoMei() {
		return nameRoMei;
	}

	/**
	 * @return
	 */
	public String getNameRoSei() {
		return nameRoSei;
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
	public String getUketukeNo() {
		return uketukeNo;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @param string
	 */
	public void setBunkasaimokuCd(String string) {
		bunkasaimokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setBunya(String string) {
		bunya = string;
	}

	/**
	 * @param string
	 */
	public void setHyojiHoshiki(String string) {
		hyojiHoshiki = string;
	}

//	/**
//	 * @param string
//	 */
//	public void setJigyoCd(String string) {
//		jigyoCd = string;
//	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @param string
	 */
	public void setKeiName(String string) {
		keiName = string;
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
	public void setNameRoMei(String string) {
		nameRoMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameRoSei(String string) {
		nameRoSei = string;
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
	public void setUketukeNo(String string) {
		uketukeNo = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

//	/**
//	 * @return
//	 */
//	public List getJigyoList() {
//		return jigyoList;
//	}
//
//	/**
//	 * @param list
//	 */
//	public void setJigyoList(List list) {
//		jigyoList = list;
//	}

	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @return
	 */
	public String getHyojiHoshikiKiban() {
		return hyojiHoshikiKiban;
	}

//	/**
//	 * @return
//	 */
//	public List getHyojiHoshikiListKiban() {
//		return hyojiHoshikiListKiban;
//	}

	/**
	 * @param string
	 */
	public void setHyojiHoshikiKiban(String string) {
		hyojiHoshikiKiban = string;
	}

//	/**
//	 * @param list
//	 */
//	public void setHyojiHoshikiListKiban(List list) {
//		hyojiHoshikiListKiban = list;
//	}

/**
 * @return
 */
public String getHyokaHigh() {
	return hyokaHigh;
}

	/**
	 * @return
	 */
	public String getHyokaLow() {
		return hyokaLow;
	}

/**
 * @param string
 */
public void setHyokaHigh(String string) {
	hyokaHigh = string;
}

	/**
	 * @param string
	 */
	public void setHyokaLow(String string) {
		hyokaLow = string;
	}

	/**
	 * @return
	 */
	public String getKaigaibunyaName() {
		return kaigaibunyaName;
	}

	/**
	 * @param string
	 */
	public void setKaigaibunyaName(String string) {
		kaigaibunyaName = string;
	}

// 2005/10/17�����ԍ��ǉ�
   /**
	* @return
	*/
   public String getSeiriNo() {
	   return seiriNo;
   }
   /**
	* @param string
	*/
   public void setSeiriNo(String string) {
	   seiriNo = string;
   }
  


	/**
	 * @return
	 */
	public List getValues() {
		return values;
	}

	/**
	 * @param list
	 */
	public void setValues(List list) {
		values = list;
	}

	/**
	 * @param string
	 */
	public void setValues(Object value) {
		if(!values.contains(value)){
			values.add(value);
		}
	}
	
	
}
