/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/17
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.hyoka;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * �]���ꗗ�����t�H�[��
 * 
 * ID RCSfile="$RCSfile: HyokaSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:19 $"
 */
public class HyokaSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

//	/** ���Ɩ� */
//	private List values = new ArrayList();

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

//	/** �\���Ҕԍ� */
//	private String shinseiNo;

//	/** �]���iFrom�j */
//	private String hyokaFrom;
//
//	/** �]���iTo�j */
//	private String hyokaTo;

	/** �\���ԍ� */
	private String uketukeNo;

	/** �\������ */
	private String hyojiHoshiki;

	/** ���Ɩ����X�g */
	private List jigyoList;

	/** �\���������X�g */
	private List hyojiHoshikiList;

//	/** ���ƃt���O */
//	private String jigyoFlg;

	/** ���ƃR�[�h */
	private String jigyoCd;

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

	/** ���Ƌ敪 */
	private String jigyoKubun;

	/** �\�������i��՗p�j */
	private String hyojiHoshikiKiban;

	/** �\���������X�g�i��՗p�j */
	private List hyojiHoshikiListKiban;

	/** �]���i�_�j�i���j */
	private String hyokaHigh;

	/** �]���i�_�j�i��j */
	private String hyokaLow;

	/** �C�O���� */
	private String kaigaibunyaName;
	
// 2005/10/18	�����ԍ��ǉ�
   /** �����ԍ� */
   private String seiriNo;


	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public HyokaSearchForm() {
		super();
		init();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * �����������B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//�E�E�E
	}

	/**
	 * ����������
	 */
	public void init() {
		nendo = "";
		kaisu = "";
		bunya = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		nameRoSei = "";
		nameRoMei = "";
		uketukeNo = "";
		hyojiHoshiki = "";
		jigyoCd = "";
		keiName = "";
		nameKanaSei = "";
		nameKanaMei = "";
		shozokuCd = "";
		bunkasaimokuCd = "";
		jigyoList = new ArrayList();
		hyojiHoshikiList = new ArrayList();
		jigyoKubun = "";
		hyojiHoshikiKiban = "";
		hyojiHoshikiListKiban = new ArrayList();
		hyokaHigh = "";
		hyokaLow = "";
		kaigaibunyaName = "";
		seiriNo = "";
	}

	/* 
	 * ���̓`�F�b�N�B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//��^����----- 
		ActionErrors errors = super.validate(mapping, request);
		//---------------------------------------------
		// ��{�I�ȃ`�F�b�N(�K�{�A�`�����j��Validator���g�p����B
		//---------------------------------------------

//		//���Ɩ��`�F�b�N�i�W��Ƃ��̑��i���������A�Z�~�i�[�j�������Ɏw�肳��ĂȂ����H
//		List jigyo = this.getValueList();
//		int cnt = jigyo.size();
//		int jigyo_err = 0;
//		if(cnt != 0){
//			//���Ɩ����w�肳��Ă���i�v�f��0�łȂ��j��
//			int jigyo_flg0 = 0;													//��b�ƂȂ�t���O(��1�v�f)�Ŕ���
//			for(int i=0; i<cnt; i++){
//				String no = (String)jigyo.get(i);
//				int jigyo_flg = 0;
//				
//				if(no.substring(0,2).equals("01")){
//					//�W��(01)�̏ꍇ�̓t���O��1�ɂ���
//					jigyo_flg = 1;
//				}
//
//				if(i == 0){
//					//��1�v�f�̏ꍇ�A��b�t���O�ɑ������
//					jigyo_flg0 = jigyo_flg;
//				}else{
//					//��2�v�f�ȍ~�́A��b�t���O�Ǝ��ȃt���O���������𔻒肷��
//					if(jigyo_flg0 != jigyo_flg){
//						//��b�t���O�Ǝ��ȃt���O���قȂ�ꍇ�̓G���[���o�͂���
//						errors.add(
//						ActionErrors.GLOBAL_ERROR,
//						new ActionError("errors.5009"));
//						
//						jigyo_err = 1;
//						
//						break;
//					}
//				}
//			}
//		}
//		
//		//�W��I�����̔N�x�K�{�`�F�b�N
//		if(cnt != 0 && jigyo_err == 0){
//			if(((String)jigyo.get(0)).substring(0,2).equals("01") && this.getNendo().equals("")){
//				//�W���I�����A�N�x�����I���̏ꍇ
//				errors.add(
//				ActionErrors.GLOBAL_ERROR,
//				new ActionError("errors.5011"));
//			}
//		}
//		
//		//�]���̓��̓`�F�b�N
//		//�]���iFrom�j
//		String base = "ABCF";
//		String hf = this.getHyokaFrom();
//		if(hf != null && !hf.equals("")){
//			for(int i=0; i<hf.length(); i++){
//				//���͒l�ꕶ������"ABCF"�̂����ꂩ�ł��邩���`�F�b�N����
//				if(base.indexOf(hf.charAt(i)) == -1){
//					//���͒l��"ABCF"�Ɋ܂܂�Ȃ������ꍇ�̓G���[���o�͂���
//					errors.add(
//					ActionErrors.GLOBAL_ERROR,
//					new ActionError("errors.5010", "�]��"));
//					return errors;						
////					break;					
//				}
//			}
//		}
//		//�]���iTo�j
//		String ht = this.getHyokaTo();
//		if(ht != null && !ht.equals("")){
//			for(int i=0; i<ht.length(); i++){
//				//���͒l�ꕶ������"ABCF"�̂����ꂩ�ł��邩���`�F�b�N����
//				if(base.indexOf(ht.charAt(i)) == -1){
//					//���͒l��"ABCF"�Ɋ܂܂�Ȃ������ꍇ�̓G���[���o�͂���
//					errors.add(
//					ActionErrors.GLOBAL_ERROR,
//					new ActionError("errors.5010", "�]��"));
//					return errors;						
////					break;					
//				}
//			}
//		}


		//��{���̓`�F�b�N�����܂�
		if (!errors.isEmpty()) {
			return errors;
		}

		//��^����----- 

		//�ǉ�����----- 

		//---------------------------------------------
		//�g�ݍ��킹�`�F�b�N	
		//---------------------------------------------
		return errors;
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

	/**
	 * @return
	 */
	public List getHyojiHoshikiList() {
		return hyojiHoshikiList;
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
	public List getJigyoList() {
		return jigyoList;
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
	public String geUketukeNo() {
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

	/**
	 * @param list
	 */
	public void setHyojiHoshikiList(List list) {
		hyojiHoshikiList = list;
	}

/**
 * @param string
 */
public void setJigyoCd(String string) {
	jigyoCd = string;
}

	/**
	 * @param list
	 */
	public void setJigyoList(List list) {
		jigyoList = list;
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

	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
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
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @return
	 */
	public String getHyojiHoshikiKiban() {
		return hyojiHoshikiKiban;
	}

	/**
	 * @return
	 */
	public List getHyojiHoshikiListKiban() {
		return hyojiHoshikiListKiban;
	}

	/**
	 * @param string
	 */
	public void setHyojiHoshikiKiban(String string) {
		hyojiHoshikiKiban = string;
	}

	/**
	 * @param list
	 */
	public void setHyojiHoshikiListKiban(List list) {
		hyojiHoshikiListKiban = list;
	}

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

// 2005/10/18	�����ԍ��ǉ�
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
}
