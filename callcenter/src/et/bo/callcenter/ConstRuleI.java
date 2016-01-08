package et.bo.callcenter;

public interface ConstRuleI {
	String CMD="COMMAND";
	String EVT="EVENT";
	//--------------v1.0-------------------------
	/*
	 * 分机命令与事件
	 */
	String CMD_EXRION="EXRION";
	String CMD_EXRIOF="EXRIOF";
	String EVT_EXHKOF="EXHKOF";
	String EVT_EXHKON="EXHKON";
	/*
	 * 中继命令与事件
	 */
	String CMD_TKHKOF="TKHKOF";
	String CMD_TKHKON="TKHKON";
	String EVT_TKRING="TKRING";
	String EVT_NDTONE="NDTONE";
	String EVT_BSYTON="BSYTON";
	String EVT_ANSTON="ANSTON";
	String EVT_TLKTON="TLKTON";
	/*
	 * 端口命令与事件
	 */
	String CMD_PTLINK="PTLINK";
	String CMD_UNLINK="UNLINK";
	String CMD_STTREC="STTREC";
	String CMD_STPREC="STPREC";
	String CMD_STTPLY="STTPLY";
	String CMD_STPPLY="STPPLY";
	String CMD_STTSIG="STTSIG";
	String CMD_STPSIG="STPSIG";
	String CMD_SDDTMF="SDDTMF";
	String EVT_PLYEND="PLYEND";
	/*
	 * 其它命令与事件
	 */
	String CMD_PRTSUM="PRTSUM";
	String CMD_PRTTYP="PRTTYP";
	String CMD_PRTSTA="PRTSTA";
	String EVT_COMMOK="COMMOK";
	String EVT_COMERR="COMERR";
	String EVT_PRTSUM="PRTSUM";
	String EVT_PRTTYP="PRTTYP";
	String EVT_PRTSTA="PRTSTA";
	//--------------v2.0-------------------------
	String CMD_CALLOK ="CALLOK";
	String CMD_CALLNO ="CALLNO";
	String CMD_CALLRE ="CALLRE";
	String CMD_RECMSG ="RECMSG";
	String CMD_SETPRT ="SETPRT";
	String CMD_MONITR ="MONITR";
	String CMD_SETJJY ="SETJJY";
	//OOVALI
	String CMD_OOVALI ="OOVALI";
	String CMD_OORING ="OORING";
	String CMD_OOSERI ="OOSERI";
	String CMD_OODISP ="OODISP";
	String CMD_ESTATE ="ESTATE";

}