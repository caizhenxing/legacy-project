package et.bo.callcenter.serversocket.iconst;

public interface ConstRuleI {

	// ***********�ָ����***************************
	// ð��
	String SPLIT_SIGN_COLON = ":";

	// �ֺ�
	String SPLIT_SIGN_SEMICOLON = ";";

	// #��
	String SPLIT_SIGN_jing = "#";

	// ����
	String SPLIT_SIGN_COMMA = ",";

	// @��
	String SPLIT_SIGN_AT = "@";

	// ��С����
	String SPLIT_SIGN_LEFT_BRACKET = "(";

	// ��С����
	String SPLIT_SIGN_RIGHT_BRACKET = ")";

	// /����
	String SPLIT_SIGN_LEFT_BIAS = "/";

	// .����
	String SPLIT_SIGN_PAUSE = ".";

	// **********�������ϯ�˷����ģ�*****************
	String CMD_AGENT_OPENSCREEN = "CMD_INCOMMING";// ���絯��

	String CMD_TO_AGENT_USERLIST = "CMD_INLIST";// �����û��б�

	String CMD_AGENT_REFRESH_AGENTPANEL = "CMD_AGENTPANEL";// ��ϯ���
    
	String CMD_AGENT_PANEL_EXPERTLIST = "CMD_AGENT_EXPERTERLIST"; //��ϯ�� ��½ʱˢ��ר���б�
	String CMD_AGENTSTATEINFO = "CMD_AGENTSTATEINFO";  //��ϯ����ϯ״̬��Ϣ
	
	String CMD_INIT = "0";// ��ʼ��״̬��ֵ

	String CMD_APENT_TXTZHUOXIZHUANGTAI = "TZXZT";// ��ϯ״̬

	String CMD_APENT_TXTDENGLUSHIJIAN = "TDLSJ";// ��½ʱ��

	String CMD_APENT_TXTRIZHIXUNLIANG = "TRZXL";// ��½ʱ��

	String CMD_APENT_CLANMUZHIXUNLIANG = "CLMZXL";// ��Ŀ��ѯ��

	String CMD_APENT_TXTFUWUZHONGSHI = "TFWZS";// ������ʱ

	String CMD_APENT_TXTBENCHISHICHANG = "TBCSC";// ����ʱ��

	String CMD_APENT_TXTDANGQIANPAIDUISHU = "TDQPDS";// ��ǰ�Ŷ���

	String CMD_APENT_COMLAIDIANJILU = "CLDJL";// �����¼

	String CMD_APENT_ZAIXIANZHUCHIREN = "CZXZCR";// ����������

	String CMD_APENT_ZAIXIANZHUANJIA = "CZXZJ";// ����ר��
	//String CMD_APENT_THREEEXPERTLIST = "CTEL"; //����ר���б�

	String TEXT_STATE_ONE = "1";// ״̬һ

	String TEXT_STATE_TWO = "2";// ״̬��

	String AGENT_BUTTON_ONE = "1";// ״̬һ

	String AGENT_BUTTON_TWO = "2";// ״̬��

	String IMAGE_STATE_ONE = "0";// ͼƬ״̬һ(û��״̬����)

	String IMAGE_STATE_TWO = "1";// ͼƬ״̬��(��״̬����)

	String CMD_image_RINGUP = "IRINGUP";// ����

	String CMD_image_OFFHOOK = "OFFHOOK"; // ժ��

	String CMD_image_ONHOOK = "ONHOOK"; // �һ�

	String CMD_APENT_DENGLU = "BDL";// ��½

	String CMD_ZHUANJIENIEXIAN = "BZNX";// ת����

	String CMD_ZHUANJIEWAIXIAN = "BZWX";// ת����

	String CMD_APENT_ZHANTING = "BZT";// ��ͣ

	String CMD_APENT_DENGDAI = "BDD";// �ȴ�

	String CMD_APENT_JIETING = "BJT";// �������Ҷ�

	String CMD_APENT_ZHUANZHIDONG = "BZZD";// �������Ҷ�

	String CMD_APENT_ZHUANYI = "BZY";// ת��

	String CMD_APENT_DIANHUABU = "BDHB";// �绰��

	String CMD_APENT_WAIHU = "BWH";// ���

	String CMD_APENT_SHOUTINGLUYIN = "BSTLY";// ����¼��

	String CMD_APENT_SHANFANFTONGHUA = "BSFTH";// ����ͨ��

	String CMD_THREE_CALLING = "BTHJ";// ������ʼ����

	String CMD_THREE_BEGIN = "BTKS";// ������ʼ����

	String CMD_QIANGCHAI = "BTQCHA";// ǿ��

	String CMD_QIANGCHA = "BTQCHAI";// ǿ��

	String CMD_ZHUANJIAGUA = "BTEHOOK";// ר�ҹ�

	String CMD_AGENTHOLD = "BTAHOOK";// ��ϯ��

	String CMD_THREEHOLD = "BTTHOOK";// ������

	String CMD_APENT_CHAOSHI = "BCS";// ��ʱ

	String CMD_APENT_DIANHUAHUIYI = "BDHHY";// �绰����

	String CMD_APENT_JIESHUYANSHI = "BJSYS";// ������ʱ

	String CMD_APENT_YONGHUPINGJIA = "BYHPJ";// �û�����

	// **********�����IVR�����ģ�*****************
	String CMD_IVR_SEND_MAINKEY = "CMD_SENDIVRKEY";// �õ�����ֵ

	String CMD_IVR_SEND_AGENTSTATE = "CMD_SENDTOIVRAGESTATE";// ������ϯ״̬��ivr

	String CMD_TURNTOIVR = "CMD_TURNIVR";// ���˹�ת������IVR�˵�

	String CMD_APPRAISE = "CMD_APPRAISE";// �û�����

	String SUCCESS = "0";// �ɹ�

	String FAIL = "1";// ʧ��

	String CMD_WAIT_INFO = "CMD_WAIT";// ���˹�ת������IVR�˵�

	// **********�����FAX�����ģ�*****************
	String CMD_FAX_SEND_MAINKEY = "CMD_SENDFAXKEY";

	// **********�����¼�������������ģ�*****************
	String CMD_RECORD_SEND_KET = "CMD_SENDRECORDPORT";
	
	// **********������������������ģ�*****************
	String CMD_CONF_SEND_HOLDMETTING = "CMD_HOLDMEETING";//�������
	String CMD_CONF_STOPMEETING = "CMD_OVERMEETING";//��������
	String CMD_CONF_ADDINCONF_MEMBER = "CMD_ADDMEMBER";//��ĳ������ӽ�����
	String CMD_CONF_REMOVECONF_MEMBER = "CMD_REMOVEMEMBER";//��ĳ�˴ӻ������Ƴ�
	
	// **********������������������ģ�*****************
	String CMD_CSTA_OPENSESSION = "CMD_OPENSESSION";//��ͨ��
	String CMD_CSTA_CLOSESESSION = "CMD_CLOSESESSION";//�ر�ͨ��
	String CMD_CSTA_STARTMONITOR = "CMD_STARTMONITOR";//�������
	String CMD_CSTA_STOPMONITOR = "CMD_STOPMONITOR";//�������
	String CMD_CSTA_MAKECALL = "CMD_MAKECALL";//��������
	String CMD_CSTA_CLEARCONNECTION = "CMD_CLEARCONNECTION";//�������ָ����ͨ��
	String CMD_CSTA_CLEARCALL = "CMD_CLEARCALL";//������е�ͨ��
	String CMD_CSTA_CONSULTATIONCALL = "CMD_CONSULTATIONCALL";//���̺���
	String CMD_CSTA_CONFERENCECALL = "CMD_CONFERENCECALL";//��������
	String CMD_CSTA_RETRIEVECALL = "CMD_RETRIEVECALL";//���߽��������µõ��绰
	String CMD_CSTA_HOLDCALL = "CMD_HOLDCALL";//�����豸
	String CMD_CSTA_DEFLECTCALL = "CMD_DEFLECTCALL";//�Ҷ�һ��ͨ����ת����һ��ͨ��
	String CMD_CSTA_TRANSFERCALL = "CMD_TRANSFERCALL";//ָ��ת�ߵ��豸
	String CMD_CSTA_RECONNECTCALL = "CMD_RECONNECTCALL";//����ͨ��
	String CMD_CSTA_ALTERNATECALL = "CMD_ALTERNATECALL";//�������л�

}