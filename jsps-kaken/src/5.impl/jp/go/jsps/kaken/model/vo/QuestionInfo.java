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

import java.util.ArrayList;
import java.util.List;


/**
 * �A���P�[�g����ێ�����N���X�B
 * 
 */
public class QuestionInfo extends QuestionPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �����@�֖� */
	private String shozokuName;
	
	/** �@�d�q�\���V�X�e���S�ʂɂ���Q1 */
	private String benri1;
	
	/** �@�d�q�\���V�X�e���S�ʂɂ���Q1 */
	private String a1;
	
	/** �@�d�q�\���V�X�e���S�ʂɂ���Q1 */
	private String a2;
	
	/** �@�d�q�\���V�X�e���S�ʂɂ���Q2 */
	private String rikai1;	
	
	/** �@�d�q�\���V�X�e���S�ʂɂ���Q2 */
	private String a3;
	
	/** �A�����p���ɂ���Q */
	private String os;
	
	/** �A�����p���ɂ���Q */
	private String kankyoosSonota;
	
	/** �A�����p���ɂ���Q */
	private String web;
	
	/** �A�����p���ɂ���Q */
	private String kankyowebSonota;
	
	/** �B�d�q�\���̂��ē��y�[�WQ1 */
	private String rikai2;
	
	/** �B�d�q�\���̂��ē��y�[�WQ1 */
	private String rikai3;
	
	/** �B�d�q�\���̂��ē��y�[�WQ1 */
	private String rikai4;
	
	/** �B�d�q�\���̂��ē��y�[�WQ1 */
	private String rikai5;
	
	/** �B�d�q�\���̂��ē��y�[�WQ1 */
	private String rikai6;
	
	/** �B�d�q�\���̂��ē��y�[�WQ1 */
	private String rikai7;
	
	/** �B�d�q�\���̂��ē��y�[�WQ1 */
	private String rikai8;
	
	/** �B�d�q�\���̂��ē��y�[�WQ1 */
	private String rikai9;
	
	/** �B�d�q�\���̂��ē��y�[�WQ1 */
	private String rikai10;
	
	/** �B�d�q�\���̂��ē��y�[�WQ1 */
	private String rikai11;
	
	/** �B�d�q�\���̂��ē��y�[�WQ2 */
	private String a4;
	
	/** �C�������ɂ���Q1 */
	private String yonda1;
	
	/** �C�������ɂ���Q2 */
	private String rikai12;	

	/** �C�������ɂ���Q2 */
	private String a5;
	
	/** �C�������ɂ���Q3 */
	private String yonda2;
	
	/** �C�������ɂ���Q4 */
	private String rikai13;	
		
	/** �C�������ɂ���Q4 */
	private String a6;
	
	/** �C�������ɂ���Q5 */
	private String a7;
	
	/** �D�d�q�\���V�X�e���ɂ���Q1 */
	private String keisiki;
		
	/** �D�d�q�\���V�X�e���ɂ���Q1 */
	private String oubokeisikiSonota;
	
	/** �D�d�q�\���V�X�e���ɂ���Q2 */
	private String riyoutime;
	
	/** �D�d�q�\���V�X�e���ɂ���Q2 */
	private String benri2;
	
	/** �D�d�q�\���V�X�e���ɂ���Q2 */
	private String a8;
	
	/** �D�d�q�\���V�X�e���ɂ���Q3 */
	private String a9;
	
	/** �D�d�q�\���V�X�e���ɂ���Q4 */
	private String a10;
	
	/** �E�R�[���Z���^�[�ɂ���Q1 */
	private String callriyou;
	
	/** �E�R�[���Z���^�[�ɂ���Q2 */
	private String callrikai;
	
	/** �E�R�[���Z���^�[�ɂ���Q2 */
	private String a11;
	
	/** �E�R�[���Z���^�[�ɂ���Q3 */
	private String a12;
		
	/** �FQ3 */
	private String toiawase1;
	
	/** �FQ4 */
	private String a13;
	
	/** �FQ5 */
	private String toiawase2;
	
	/** �FQ6 */
	private String a14;
	
	
	//�R���������@2005.11.21�ǉ�
	
	/** �R�������i���j */
	private String SHINSAIN_NAME_SEI;
	
	/** �R�������i���j */
	private String SHINSAIN_NAME_MEI;

	/** �R������p�y�[�W�ɂ���Q1 */
	private String rikai14;
	
	/** �R������p�y�[�W�ɂ���Q1 */
	private String a15; 

	/** IP�A�h���X */
	private String ip;
	
	

	/** ���X�g���W�I�{�^�� */
	private List benri1List = new ArrayList();
	
	private List rikai1List = new ArrayList();
	
	private List rikai2List = new ArrayList();
	
	private List rikai3List = new ArrayList();
	
	private List yonda1List = new ArrayList();
	
	private List benri2List = new ArrayList();
	
	private List callriyouList = new ArrayList();
	
	private List callrikaiList = new ArrayList();

	/** ���X�g�v���_�E�� */
	private List osList = new ArrayList();
	
	private List webList = new ArrayList();
	
	private List keisikiList = new ArrayList();
	
	private List riyoutimeList = new ArrayList();
	
	private List toiawase1List = new ArrayList();
	
	

	/** ���x��-�֗�1 */
	private String benri1Label;
	
	/** ���x��-����1 */
	private String rikai1Label;

	/** ���x��-����2 */
	private String rikai2Label;
	
	/** ���x��-����3 */
	private String rikai3Label;
	
	/** ���x��-����4 */
	private String rikai4Label;
	
	/** ���x��-����5 */
	private String rikai5Label;
	
	/** ���x��-����6 */
	private String rikai6Label;
	
	/** ���x��-����7 */
	private String rikai7Label;
	
	/** ���x��-����8 */
	private String rikai8Label;
	
	/** ���x��-����9 */
	private String rikai9Label;
	
	/** ���x��-����10 */
	private String rikai10Label;
	
	/** ���x��-����11 */
	private String rikai11Label;
	
	/** ���x��-�ǂ�1 */
	private String yonda1Label;
	
	/** ���x��-����12 */
	private String rikai12Label;
	
	/** ���x��-�ǂ�2 */
	private String yonda2Label;
	
	/** ���x��-����13 */
	private String rikai13Label;
	
	/** ���x��-�֗�2 */
	private String benri2Label;
	
	/** ���x��-�R�[�����p */
	private String callriyouLabel;
	
	/** ���x��-�R�[������ */
	private String callrikaiLabel;
	
	/** ���x��-���p���� */
	private String riyoutimeLabel;
	
	/** ���x��-����Җ₢���킹���� */
	private String toiawase1Label;
	
	/** ���x��-���ǒS���Җ₢���킹���� */
	private String toiawase2Label;
	
	/** ���x��-OS */
	private String osLabel;
	
	/** ���x��-WEB */
	private String webLabel;
	
	/** ���x��-����`�� */
	private String keisikiLabel;
	
	/** ���x��-����14 */
	private String rikai14Label;
	
	
	/** ����҂���̖₢���킹�̓��e */
	private List ouboToiValues = new ArrayList();
	
	/** ���x��-����҂���̖₢���킹�̓��e */
	private List ouboToiLabelList = new ArrayList();
	
	/** ���ǒS���҂���̖₢���킹���e */
	private List bukyokuToiValues = new ArrayList();
	
	/** ���x��-���ǒS���҂���̖₢���킹�̓��e */
	private List bukyokuToiLabelList = new ArrayList();

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public QuestionInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------



	/**
	 * @return
	 */
	public String getA10() {
		return a10;
	}

	/**
	 * @return
	 */
	public String getA9() {
		return a9;
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
	public void setA10(String string) {
		a10 = string;
	}

	/**
	 * @param string
	 */
	public void setA9(String string) {
		a9 = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @return
	 */
	public String getA1() {
		return a1;
	}

	/**
	 * @return
	 */
	public String getA2() {
		return a2;
	}

	/**
	 * @return
	 */
	public String getA3() {
		return a3;
	}

	/**
	 * @return
	 */
	public String getA4() {
		return a4;
	}

	/**
	 * @return
	 */
	public String getA5() {
		return a5;
	}

	/**
	 * @return
	 */
	public String getA6() {
		return a6;
	}

	/**
	 * @return
	 */
	public String getA7() {
		return a7;
	}

	/**
	 * @return
	 */
	public String getA8() {
		return a8;
	}


	/**
	 * @return
	 */
	public String getKankyoosSonota() {
		return kankyoosSonota;
	}

	/**
	 * @return
	 */
	public String getKankyowebSonota() {
		return kankyowebSonota;
	}

	/**
	 * @return
	 */
	public String getOubokeisikiSonota() {
		return oubokeisikiSonota;
	}


	/**
	 * @param string
	 */
	public void setA1(String string) {
		a1 = string;
	}

	/**
	 * @param string
	 */
	public void setA2(String string) {
		a2 = string;
	}

	/**
	 * @param string
	 */
	public void setA3(String string) {
		a3 = string;
	}

	/**
	 * @param string
	 */
	public void setA4(String string) {
		a4 = string;
	}

	/**
	 * @param string
	 */
	public void setA5(String string) {
		a5 = string;
	}

	/**
	 * @param string
	 */
	public void setA6(String string) {
		a6 = string;
	}

	/**
	 * @param string
	 */
	public void setA7(String string) {
		a7 = string;
	}

	/**
	 * @param string
	 */
	public void setA8(String string) {
		a8 = string;
	}


	/**
	 * @param string
	 */
	public void setKankyoosSonota(String string) {
		kankyoosSonota = string;
	}

	/**
	 * @param string
	 */
	public void setKankyowebSonota(String string) {
		kankyowebSonota = string;
	}

	/**
	 * @param string
	 */
	public void setOubokeisikiSonota(String string) {
		oubokeisikiSonota = string;
	}

	/**
	 * @return
	 */
	public String getBenri1() {
		return benri1;
	}

	/**
	 * @return
	 */
	public List getBenri1List() {
		return benri1List;
	}

	/**
	 * @param string
	 */
	public void setBenri1(String string) {
		benri1 = string;
	}

	/**
	 * @param list
	 */
	public void setBenri1List(List list) {
		benri1List = list;
	}

	/**
	 * @return
	 */
	public String getBenri2() {
		return benri2;
	}

	/**
	 * @return
	 */
	public List getBenri2List() {
		return benri2List;
	}

	/**
	 * @return
	 */
	public String getRikai1() {
		return rikai1;
	}

	/**
	 * @return
	 */
	public String getRikai10() {
		return rikai10;
	}

	/**
	 * @return
	 */
	public String getRikai11() {
		return rikai11;
	}

	/**
	 * @return
	 */
	public String getRikai12() {
		return rikai12;
	}

	/**
	 * @return
	 */
	public String getRikai13() {
		return rikai13;
	}

	/**
	 * @return
	 */
	public List getRikai1List() {
		return rikai1List;
	}

	/**
	 * @return
	 */
	public String getRikai2() {
		return rikai2;
	}

	/**
	 * @return
	 */
	public List getRikai2List() {
		return rikai2List;
	}

	/**
	 * @return
	 */
	public String getRikai3() {
		return rikai3;
	}

	/**
	 * @return
	 */
	public List getRikai3List() {
		return rikai3List;
	}

	/**
	 * @return
	 */
	public String getRikai4() {
		return rikai4;
	}

	/**
	 * @return
	 */
	public String getRikai5() {
		return rikai5;
	}

	/**
	 * @return
	 */
	public String getRikai6() {
		return rikai6;
	}

	/**
	 * @return
	 */
	public String getRikai7() {
		return rikai7;
	}

	/**
	 * @return
	 */
	public String getRikai8() {
		return rikai8;
	}

	/**
	 * @return
	 */
	public String getRikai9() {
		return rikai9;
	}

	/**
	 * @return
	 */
	public String getYonda1() {
		return yonda1;
	}



	/**
	 * @return
	 */
	public String getYonda2() {
		return yonda2;
	}

	/**
	 * @param string
	 */
	public void setBenri2(String string) {
		benri2 = string;
	}

	/**
	 * @param list
	 */
	public void setBenri2List(List list) {
		benri2List = list;
	}

	/**
	 * @param string
	 */
	public void setRikai1(String string) {
		rikai1 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai10(String string) {
		rikai10 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai11(String string) {
		rikai11 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai12(String string) {
		rikai12 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai13(String string) {
		rikai13 = string;
	}

	/**
	 * @param list
	 */
	public void setRikai1List(List list) {
		rikai1List = list;
	}

	/**
	 * @param string
	 */
	public void setRikai2(String string) {
		rikai2 = string;
	}

	/**
	 * @param list
	 */
	public void setRikai2List(List list) {
		rikai2List = list;
	}

	/**
	 * @param string
	 */
	public void setRikai3(String string) {
		rikai3 = string;
	}

	/**
	 * @param list
	 */
	public void setRikai3List(List list) {
		rikai3List = list;
	}

	/**
	 * @param string
	 */
	public void setRikai4(String string) {
		rikai4 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai5(String string) {
		rikai5 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai6(String string) {
		rikai6 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai7(String string) {
		rikai7 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai8(String string) {
		rikai8 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai9(String string) {
		rikai9 = string;
	}

	/**
	 * @param string
	 */
	public void setYonda1(String string) {
		yonda1 = string;
	}


	/**
	 * @param string
	 */
	public void setYonda2(String string) {
		yonda2 = string;
	}


	/**
	 * @return
	 */
	public String getKeisiki() {
		return keisiki;
	}

	/**
	 * @return
	 */
	public List getKeisikiList() {
		return keisikiList;
	}

	/**
	 * @return
	 */
	public List getOsList() {
		return osList;
	}

	/**
	 * @return
	 */
	public String getRiyoutime() {
		return riyoutime;
	}

	/**
	 * @return
	 */
	public List getRiyoutimeList() {
		return riyoutimeList;
	}

	/**
	 * @return
	 */
	public String getToiawase1() {
		return toiawase1;
	}

	/**
	 * @return
	 */
	public List getToiawase1List() {
		return toiawase1List;
	}

	/**
	 * @return
	 */
	public String getToiawase2() {
		return toiawase2;
	}

	/**
	 * @return
	 */
	public List getWebList() {
		return webList;
	}

	/**
	 * @param string
	 */
	public void setKeisiki(String string) {
		keisiki = string;
	}

	/**
	 * @param list
	 */
	public void setKeisikiList(List list) {
		keisikiList = list;
	}

	/**
	 * @param list
	 */
	public void setOsList(List list) {
		osList = list;
	}

	/**
	 * @param string
	 */
	public void setRiyoutime(String string) {
		riyoutime = string;
	}

	/**
	 * @param list
	 */
	public void setRiyoutimeList(List list) {
		riyoutimeList = list;
	}

	/**
	 * @param string
	 */
	public void setToiawase1(String string) {
		toiawase1 = string;
	}

	/**
	 * @param list
	 */
	public void setToiawase1List(List list) {
		toiawase1List = list;
	}

	/**
	 * @param string
	 */
	public void setToiawase2(String string) {
		toiawase2 = string;
	}

	/**
	 * @param list
	 */
	public void setWebList(List list) {
		webList = list;
	}
	/**
	 * @return
	 */
	public String getA11() {
		return a11;
	}

	/**
	 * @return
	 */
	public String getA12() {
		return a12;
	}

	/**
	 * @return
	 */
	public String getA13() {
		return a13;
	}

	/**
	 * @return
	 */
	public String getA14() {
		return a14;
	}

	/**
	 * @return
	 */
	public String getCallrikai() {
		return callrikai;
	}

	/**
	 * @return
	 */
	public List getCallrikaiList() {
		return callrikaiList;
	}

	/**
	 * @return
	 */
	public String getCallriyou() {
		return callriyou;
	}

	/**
	 * @return
	 */
	public List getCallriyouList() {
		return callriyouList;
	}

	/**
	 * @param string
	 */
	public void setA11(String string) {
		a11 = string;
	}

	/**
	 * @param string
	 */
	public void setA12(String string) {
		a12 = string;
	}

	/**
	 * @param string
	 */
	public void setA13(String string) {
		a13 = string;
	}

	/**
	 * @param string
	 */
	public void setA14(String string) {
		a14 = string;
	}

	/**
	 * @param string
	 */
	public void setCallrikai(String string) {
		callrikai = string;
	}

	/**
	 * @param list
	 */
	public void setCallrikaiList(List list) {
		callrikaiList = list;
	}

	/**
	 * @param string
	 */
	public void setCallriyou(String string) {
		callriyou = string;
	}

	/**
	 * @param list
	 */
	public void setCallriyouList(List list) {
		callriyouList = list;
	}

	/**
	 * @return
	 */
	public String getBenri1Label() {
		return benri1Label;
	}

	/**
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param string
	 */
	public void setBenri1Label(String string) {
		benri1Label = string;
	}

	/**
	 * @param string
	 */
	public void setIp(String string) {
		ip = string;
	}

	/**
	 * @return
	 */
	public String getBenri2Label() {
		return benri2Label;
	}

	/**
	 * @return
	 */
	public String getRikai10Label() {
		return rikai10Label;
	}

	/**
	 * @return
	 */
	public String getRikai11Label() {
		return rikai11Label;
	}

	/**
	 * @return
	 */
	public String getRikai12Label() {
		return rikai12Label;
	}

	/**
	 * @return
	 */
	public String getRikai13Label() {
		return rikai13Label;
	}

	/**
	 * @return
	 */
	public String getRikai1Label() {
		return rikai1Label;
	}

	/**
	 * @return
	 */
	public String getRikai2Label() {
		return rikai2Label;
	}

	/**
	 * @return
	 */
	public String getRikai3Label() {
		return rikai3Label;
	}

	/**
	 * @return
	 */
	public String getRikai4Label() {
		return rikai4Label;
	}

	/**
	 * @return
	 */
	public String getRikai5Label() {
		return rikai5Label;
	}

	/**
	 * @return
	 */
	public String getRikai6Label() {
		return rikai6Label;
	}

	/**
	 * @return
	 */
	public String getRikai7Label() {
		return rikai7Label;
	}

	/**
	 * @return
	 */
	public String getRikai8Label() {
		return rikai8Label;
	}

	/**
	 * @return
	 */
	public String getRikai9Label() {
		return rikai9Label;
	}

	/**
	 * @return
	 */
	public String getYonda1Label() {
		return yonda1Label;
	}

	/**
	 * @return
	 */
	public String getYonda2Label() {
		return yonda2Label;
	}

	/**
	 * @param string
	 */
	public void setBenri2Label(String string) {
		benri2Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai10Label(String string) {
		rikai10Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai11Label(String string) {
		rikai11Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai12Label(String string) {
		rikai12Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai13Label(String string) {
		rikai13Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai1Label(String string) {
		rikai1Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai2Label(String string) {
		rikai2Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai3Label(String string) {
		rikai3Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai4Label(String string) {
		rikai4Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai5Label(String string) {
		rikai5Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai6Label(String string) {
		rikai6Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai7Label(String string) {
		rikai7Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai8Label(String string) {
		rikai8Label = string;
	}

	/**
	 * @param string
	 */
	public void setRikai9Label(String string) {
		rikai9Label = string;
	}

	/**
	 * @param string
	 */
	public void setYonda1Label(String string) {
		yonda1Label = string;
	}

	/**
	 * @param string
	 */
	public void setYonda2Label(String string) {
		yonda2Label = string;
	}

	/**
	 * @return
	 */
	public String getCallrikaiLabel() {
		return callrikaiLabel;
	}

	/**
	 * @return
	 */
	public String getCallriyouLabel() {
		return callriyouLabel;
	}

	/**
	 * @param string
	 */
	public void setCallrikaiLabel(String string) {
		callrikaiLabel = string;
	}

	/**
	 * @param string
	 */
	public void setCallriyouLabel(String string) {
		callriyouLabel = string;
	}

	/**
	 * @return
	 */
	public String getRiyoutimeLabel() {
		return riyoutimeLabel;
	}

	/**
	 * @param string
	 */
	public void setRiyoutimeLabel(String string) {
		riyoutimeLabel = string;
	}
	
	/**
	 * @return
	 */
	public String getToiawase1Label() {
		return toiawase1Label;
	}

	/**
	 * @return
	 */
	public String getToiawase2Label() {
		return toiawase2Label;
	}

	/**
	 * @param string
	 */
	public void setToiawase1Label(String string) {
		toiawase1Label = string;
	}

	/**
	 * @param string
	 */
	public void setToiawase2Label(String string) {
		toiawase2Label = string;
	}

	/**
	 * @return
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @return
	 */
	public String getWeb() {
		return web;
	}

	/**
	 * @param string
	 */
	public void setOs(String string) {
		os = string;
	}

	/**
	 * @param string
	 */
	public void setWeb(String string) {
		web = string;
	}

	/**
	 * @return
	 */
	public String getKeisikiLabel() {
		return keisikiLabel;
	}

	/**
	 * @return
	 */
	public String getOsLabel() {
		return osLabel;
	}

	/**
	 * @return
	 */
	public String getWebLabel() {
		return webLabel;
	}

	/**
	 * @param string
	 */
	public void setKeisikiLabel(String string) {
		keisikiLabel = string;
	}

	/**
	 * @param string
	 */
	public void setOsLabel(String string) {
		osLabel = string;
	}

	/**
	 * @param string
	 */
	public void setWebLabel(String string) {
		webLabel = string;
	}

	/**
	 * @return ouboToiLabelList ��߂��܂��B
	 */
	public List getOuboToiLabelList() {
		return ouboToiLabelList;
	}

	/**
	 * @param ouboToiLabelList �ݒ肷�� ouboToiLabelList�B
	 */
	public void setOuboToiLabelList(List ouboToiLabelList) {
		this.ouboToiLabelList = ouboToiLabelList;
	}

	/**
	 * @return ouboToiValues ��߂��܂��B
	 */
	public List getOuboToiValues() {
		return ouboToiValues;
	}

	/**
	 * @param ouboToiValues �ݒ肷�� ouboToiValues�B
	 */
	public void setOuboToiValues(List ouboToiValues) {
		this.ouboToiValues = ouboToiValues;
	}

	/**
	 * @return
	 */
	public List getYonda1List() {
		return yonda1List;
	}

	/**
	 * @param list
	 */
	public void setYonda1List(List list) {
		yonda1List = list;
	}

	/**
	 * @return
	 */
	public List getBukyokuToiLabelList() {
		return bukyokuToiLabelList;
	}

	/**
	 * @return
	 */
	public List getBukyokuToiValues() {
		return bukyokuToiValues;
	}

	/**
	 * @param bukyokuToiLabelList �ݒ肷�� bukyokuToiLabelList�B
	 */
	public void setBukyokuToiLabelList(List bukyokuToiLabelList) {
		this.bukyokuToiLabelList = bukyokuToiLabelList;
	}

	/**
	 * @param ouboToiValues �ݒ肷�� ouboToiValues�B
	 */
	public void setBukyokuToiValues(List bukyokuToiValues) {
		this.bukyokuToiValues = bukyokuToiValues;
	}

	/**
	 * @return
	 */
	public String getA15() {
		return a15;
	}

	/**
	 * @return
	 */
	public String getRikai14() {
		return rikai14;
	}

	/**
	 * @return
	 */
	public String getSHINSAIN_NAME_MEI() {
		return SHINSAIN_NAME_MEI;
	}

	/**
	 * @return
	 */
	public String getSHINSAIN_NAME_SEI() {
		return SHINSAIN_NAME_SEI;
	}

	/**
	 * @param string
	 */
	public void setA15(String string) {
		a15 = string;
	}

	/**
	 * @param string
	 */
	public void setRikai14(String string) {
		rikai14 = string;
	}

	/**
	 * @param string
	 */
	public void setSHINSAIN_NAME_MEI(String string) {
		SHINSAIN_NAME_MEI = string;
	}

	/**
	 * @param string
	 */
	public void setSHINSAIN_NAME_SEI(String string) {
		SHINSAIN_NAME_SEI = string;
	}

	/**
	 * @return
	 */
	public String getRikai14Label() {
		return rikai14Label;
	}

	/**
	 * @param string
	 */
	public void setRikai14Label(String string) {
		rikai14Label = string;
	}

}
