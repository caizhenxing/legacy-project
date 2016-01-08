package et.bo.callcenter.serversocket.iconst;

public interface ConstRuleI {

	// ***********分割符号***************************
	// 冒号
	String SPLIT_SIGN_COLON = ":";

	// 分号
	String SPLIT_SIGN_SEMICOLON = ";";

	// #号
	String SPLIT_SIGN_jing = "#";

	// 逗号
	String SPLIT_SIGN_COMMA = ",";

	// @号
	String SPLIT_SIGN_AT = "@";

	// 左小括号
	String SPLIT_SIGN_LEFT_BRACKET = "(";

	// 右小括号
	String SPLIT_SIGN_RIGHT_BRACKET = ")";

	// /符号
	String SPLIT_SIGN_LEFT_BIAS = "/";

	// .符号
	String SPLIT_SIGN_PAUSE = ".";

	// **********命令（向座席端发出的）*****************
	String CMD_AGENT_OPENSCREEN = "CMD_INCOMMING";// 来电弹屏

	String CMD_TO_AGENT_USERLIST = "CMD_INLIST";// 内线用户列表

	String CMD_AGENT_REFRESH_AGENTPANEL = "CMD_AGENTPANEL";// 座席面板
    
	String CMD_AGENT_PANEL_EXPERTLIST = "CMD_AGENT_EXPERTERLIST"; //座席端 登陆时刷新专家列表
	String CMD_AGENTSTATEINFO = "CMD_AGENTSTATEINFO";  //座席端座席状态信息
	
	String CMD_INIT = "0";// 初始化状态的值

	String CMD_APENT_TXTZHUOXIZHUANGTAI = "TZXZT";// 座席状态

	String CMD_APENT_TXTDENGLUSHIJIAN = "TDLSJ";// 登陆时间

	String CMD_APENT_TXTRIZHIXUNLIANG = "TRZXL";// 登陆时间

	String CMD_APENT_CLANMUZHIXUNLIANG = "CLMZXL";// 栏目咨询量

	String CMD_APENT_TXTFUWUZHONGSHI = "TFWZS";// 服务总时

	String CMD_APENT_TXTBENCHISHICHANG = "TBCSC";// 本次时长

	String CMD_APENT_TXTDANGQIANPAIDUISHU = "TDQPDS";// 当前排队数

	String CMD_APENT_COMLAIDIANJILU = "CLDJL";// 来电记录

	String CMD_APENT_ZAIXIANZHUCHIREN = "CZXZCR";// 在线主持人

	String CMD_APENT_ZAIXIANZHUANJIA = "CZXZJ";// 在线专家
	//String CMD_APENT_THREEEXPERTLIST = "CTEL"; //三方专家列表

	String TEXT_STATE_ONE = "1";// 状态一

	String TEXT_STATE_TWO = "2";// 状态二

	String AGENT_BUTTON_ONE = "1";// 状态一

	String AGENT_BUTTON_TWO = "2";// 状态二

	String IMAGE_STATE_ONE = "0";// 图片状态一(没有状态发生)

	String IMAGE_STATE_TWO = "1";// 图片状态二(有状态发生)

	String CMD_image_RINGUP = "IRINGUP";// 震铃

	String CMD_image_OFFHOOK = "OFFHOOK"; // 摘机

	String CMD_image_ONHOOK = "ONHOOK"; // 挂机

	String CMD_APENT_DENGLU = "BDL";// 登陆

	String CMD_ZHUANJIENIEXIAN = "BZNX";// 转内线

	String CMD_ZHUANJIEWAIXIAN = "BZWX";// 转外线

	String CMD_APENT_ZHANTING = "BZT";// 暂停

	String CMD_APENT_DENGDAI = "BDD";// 等待

	String CMD_APENT_JIETING = "BJT";// 接听，挂断

	String CMD_APENT_ZHUANZHIDONG = "BZZD";// 接听，挂断

	String CMD_APENT_ZHUANYI = "BZY";// 转接

	String CMD_APENT_DIANHUABU = "BDHB";// 电话本

	String CMD_APENT_WAIHU = "BWH";// 外呼

	String CMD_APENT_SHOUTINGLUYIN = "BSTLY";// 收听录音

	String CMD_APENT_SHANFANFTONGHUA = "BSFTH";// 三方通话

	String CMD_THREE_CALLING = "BTHJ";// 三方开始呼叫

	String CMD_THREE_BEGIN = "BTKS";// 三方开始呼叫

	String CMD_QIANGCHAI = "BTQCHA";// 强拆

	String CMD_QIANGCHA = "BTQCHAI";// 强插

	String CMD_ZHUANJIAGUA = "BTEHOOK";// 专家挂

	String CMD_AGENTHOLD = "BTAHOOK";// 座席挂

	String CMD_THREEHOLD = "BTTHOOK";// 三方挂

	String CMD_APENT_CHAOSHI = "BCS";// 超时

	String CMD_APENT_DIANHUAHUIYI = "BDHHY";// 电话会议

	String CMD_APENT_JIESHUYANSHI = "BJSYS";// 结束延时

	String CMD_APENT_YONGHUPINGJIA = "BYHPJ";// 用户评价

	// **********命令（向IVR发出的）*****************
	String CMD_IVR_SEND_MAINKEY = "CMD_SENDIVRKEY";// 得到主键值

	String CMD_IVR_SEND_AGENTSTATE = "CMD_SENDTOIVRAGESTATE";// 发送座席状态到ivr

	String CMD_TURNTOIVR = "CMD_TURNIVR";// 由人工转换进入IVR菜单

	String CMD_APPRAISE = "CMD_APPRAISE";// 用户评价

	String SUCCESS = "0";// 成功

	String FAIL = "1";// 失败

	String CMD_WAIT_INFO = "CMD_WAIT";// 由人工转换进入IVR菜单

	// **********命令（向FAX发出的）*****************
	String CMD_FAX_SEND_MAINKEY = "CMD_SENDFAXKEY";

	// **********命令（向录音服务器发出的）*****************
	String CMD_RECORD_SEND_KET = "CMD_SENDRECORDPORT";
	
	// **********命令（向会议服务器发出的）*****************
	String CMD_CONF_SEND_HOLDMETTING = "CMD_HOLDMEETING";//发起会议
	String CMD_CONF_STOPMEETING = "CMD_OVERMEETING";//结束会议
	String CMD_CONF_ADDINCONF_MEMBER = "CMD_ADDMEMBER";//将某个人添加进会议
	String CMD_CONF_REMOVECONF_MEMBER = "CMD_REMOVEMEMBER";//将某人从会议中移除
	
	// **********命令（向会议服务器发出的）*****************
	String CMD_CSTA_OPENSESSION = "CMD_OPENSESSION";//打开通道
	String CMD_CSTA_CLOSESESSION = "CMD_CLOSESESSION";//关闭通道
	String CMD_CSTA_STARTMONITOR = "CMD_STARTMONITOR";//开启监控
	String CMD_CSTA_STOPMONITOR = "CMD_STOPMONITOR";//开启监控
	String CMD_CSTA_MAKECALL = "CMD_MAKECALL";//两方呼叫
	String CMD_CSTA_CLEARCONNECTION = "CMD_CLEARCONNECTION";//清除任意指定的通道
	String CMD_CSTA_CLEARCALL = "CMD_CLEARCALL";//清除所有的通道
	String CMD_CSTA_CONSULTATIONCALL = "CMD_CONSULTATIONCALL";//磋商呼叫
	String CMD_CSTA_CONFERENCECALL = "CMD_CONFERENCECALL";//三方会议
	String CMD_CSTA_RETRIEVECALL = "CMD_RETRIEVECALL";//告诉交换机重新得到电话
	String CMD_CSTA_HOLDCALL = "CMD_HOLDCALL";//挂起设备
	String CMD_CSTA_DEFLECTCALL = "CMD_DEFLECTCALL";//挂断一个通道，转到另一个通道
	String CMD_CSTA_TRANSFERCALL = "CMD_TRANSFERCALL";//指定转走的设备
	String CMD_CSTA_RECONNECTCALL = "CMD_RECONNECTCALL";//重新通话
	String CMD_CSTA_ALTERNATECALL = "CMD_ALTERNATECALL";//不震铃切换

}