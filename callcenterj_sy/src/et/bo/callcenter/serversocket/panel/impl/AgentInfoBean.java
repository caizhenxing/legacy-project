/**
 * 沈阳卓越科技有限公司
 * 2008-5-8
 */
package et.bo.callcenter.serversocket.panel.impl;
/**
 * 普通javabean记录座席状态信息
 * 
 * @author wangwenquan
 * 
 */
public class AgentInfoBean {
	//座席号
	private String angentNum;
	//座席状态	txtZuoXiXZhuangTai   	tZXZT
	private String tZXZT;
	//登录时间	txtDengLuShiJian      	tDLSJ
	private String tDLSJ;
	//当前时间:   	txtDangQianShiJian	tDQSJ
	private String tDQSJ;
	
	//日咨询量: 	txtRiZhiXunLiang 	tRZXL
	private String tRZXL;
	//栏目咨询量:  	comtLanMuZhiXunLia ng	cLMZXL
	private String cLMZXL;
	//服务总时:  	txtFuWuZongShi	tFWZS
	private String tFWZS;
	
	//本次时常: 		txtBenCiShiChang	tBCSC
	private String tBCSC;
	//当前排队数 :		txtDangQianPaiDuiShu	tDQPDS
	private String tDQPDS;
	//来电记录:			comLaiDianJiLu	cLDJL
	private String cLDJL;
	
	//在线主持人:	comZaiXianZhuChiRen	cZXZCR
	private String cZXZCR;
	//在线专家: 	comZaiXianZhuanJia	cZXZJ
	private String cZXZJ;
	
	public String getCLDJL() {
		return cLDJL;
	}
	public void setCLDJL(String cldjl) {
		cLDJL = cldjl;
	}
	public String getCLMZXL() {
		return cLMZXL;
	}
	public void setCLMZXL(String clmzxl) {
		cLMZXL = clmzxl;
	}
	public String getCZXZCR() {
		return cZXZCR;
	}
	public void setCZXZCR(String czxzcr) {
		cZXZCR = czxzcr;
	}
	public String getCZXZJ() {
		return cZXZJ;
	}
	public void setCZXZJ(String czxzj) {
		cZXZJ = czxzj;
	}
	public String getTBCSC() {
		return tBCSC;
	}
	public void setTBCSC(String tbcsc) {
		tBCSC = tbcsc;
	}
	public String getTDLSJ() {
		return tDLSJ;
	}
	public void setTDLSJ(String tdlsj) {
		tDLSJ = tdlsj;
	}
	public String getTDQPDS() {
		return tDQPDS;
	}
	public void setTDQPDS(String tdqpds) {
		tDQPDS = tdqpds;
	}
	public String getTDQSJ() {
		return tDQSJ;
	}
	public void setTDQSJ(String tdqsj) {
		tDQSJ = tdqsj;
	}
	public String getTFWZS() {
		return tFWZS;
	}
	public void setTFWZS(String tfwzs) {
		tFWZS = tfwzs;
	}
	public String getTRZXL() {
		return tRZXL;
	}
	public void setTRZXL(String trzxl) {
		tRZXL = trzxl;
	}
	public String getTZXZT() {
		return tZXZT;
	}
	public void setTZXZT(String tzxzt) {
		tZXZT = tzxzt;
	}
	public String getAngentNum() {
		return angentNum;
	}
	public void setAngentNum(String angentNum) {
		this.angentNum = angentNum;
	}
	
	
}
