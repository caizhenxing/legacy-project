package et.bo.callcenter.message.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.ConstRuleI;
import et.bo.callcenter.base.CardInfo;
import et.bo.callcenter.base.CardState;
import et.bo.callcenter.base.ConnectInfo;
import et.bo.callcenter.base.OperatorState;
import et.bo.callcenter.message.BaseEvent;
import et.bo.common.testing.MyLog1;
import et.bo.common.testing.MyLog2;
/**
 * 
 * @author guxiaofeng
 *����ϯ�ֻ�ȡ���������û���ʼͨ��������ϵͳ����������ͽ�ͨ�¼���
	PTTALK��<����˿�>��<��ϯ�˿�>��
 */
public class Pttalk extends BaseEvent {
	private static Log log = LogFactory.getLog(Pttalk.class);
//	------------------����------------------
//	public final String DRIVE="D:\\WEB_SERVER\\";
	public final String DRIVE="D:/WEB_SERVER/";
	//����unix����Ҫ�ó�\.
//	public final String PATH = "CALLCENTER\\REC\\";
	public final String PATH = "CALLCENTER/REC/";
	public final String FILE_EXT = ".WAV";
//	------------------����------------------
	@Override
	protected String execute() {
//		this.eventHelper.printArg(args);
		//init and set ��ϯ�������
		String inPort =args[0];
		String operatorPort =args[1];
		CardInfo cardInfo1=CardInfo.getCardInfo(operatorPort);
		String ip = cardInfo1.getIp();
		//
		ConnectInfo ci =(ConnectInfo)ConnectInfo.getCurConn().get(inPort);
		new MyLog2().info(" pttalk in!");
		if(ci==null)return null;//exception exit.
		new MyLog2().info(" pttalk in1!");
		String id = ci.getId();
		new MyLog2().info(" pttalk in2! id is:"+id);
//		String ipOper=
		System.out.println("*******************************");
		System.out.println(ip);
		System.out.println("*******************************");
		OperatorState os1 = OperatorState.getOperatorState(ip);
		String on = os1.getOperatorNum();
		this.pi.setPcOperatorNum(id, on);
		
		//set class
		
		//1������ϯֹͣ��������
		String sCmd1 = cs.getSendMsg(ConstRuleI.CMD_OORING, 
				new String[]{"0"});
		System.out.println(sCmd1);
		server.command(ip, sCmd1);
		new MyLog2().info(" pttalk in3! stop ring");
		//oovild		
		String valid="1";
		String sCmd11 = cs.getSendMsg(ConstRuleI.CMD_OOSERI,
				new String[]{id,valid});
		server.command(ip, sCmd11);
		new MyLog2().info(" pttalk in4! ooseri cmd is:"+sCmd11);
		StringBuffer sb=new StringBuffer();
		sb.append("id is :"+id+" ");
		sb.append("����˿� is:"+inPort+" ");
		sb.append("��ϯ�˿� is:"+operatorPort);
		new MyLog1().info(sb.toString());
		//2��ʼ¼��������¼���ļ�������ʽ��CALLCENTER\\REC\\yyyymmdd\\hhmmss_port.wav
		String sRelativeFileName=this.getRelativeFileName(inPort);
		
		//���Ϲ��ط���ľ���·��
		String sFileName=this.getAbsoluteFileName(sRelativeFileName);
		String sCmd2 = cs.getSendMsg(ConstRuleI.CMD_RECMSG, 
				new String[]{inPort,sFileName});
		log.info(sCmd2);
		server.command(pi.getCardPcIp(), sCmd2);
		//3����״̬
		baseClassHandle0(sRelativeFileName);
		//4����ˢ����ϯ������̡�
		/*
		 * need to add below act&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
		 */
//		this.eventHelper.refreshIccAndOperator();
//		this.eventHelper.refreshIccOperatorState();
		this.eventHelper.refreshOperatorPanel();
		log.debug("^^^^^^^^^^^^^^^^^^^^^^^");
		return null;
	}
	private void baseClassHandle0(String sFileName){
//		init
		String inPort =args[0];
		String operatorPort =args[1];
		//^^^^^^^^ConnectInfo handle		
		ConnectInfo ci =(ConnectInfo)ConnectInfo.getCurConn().get(inPort);
		String id = ci.getId();
		ci.setOperateTime(new Date());
		ci.setOperatorPort(operatorPort);
		ci.setRecFile(sFileName);
		
		//^^^^^^^^CardState handle
		CardState cardState1 =CardState.getCardState(inPort);		
		
		cardState1.setLinkPort(operatorPort);
		CardState.getOuterWaitingMap().remove(inPort);
		
		String pn=cardState1.getPhoneNum();
		//
		
		CardState cardState2 =CardState.getCardState(operatorPort);
		cardState2.setPhoneNum(pn);
		cardState2.setState(cardState2.INNER_CALLING);
		cardState2.setLinkPort(inPort);
		CardInfo cardInfo1=CardInfo.getCardInfo(operatorPort);
		String ip = cardInfo1.getIp();
		OperatorState os11 = OperatorState.getOperatorState(ip);
		String on = os11.getOperatorNum();
		cardState2.setOperatorNum(on);
		CardState.setInnerActiveMap(cardState2);
		//^^^^^^^^pc handle
		String operatorNum =cardState2.getOperatorNum();
		this.pi.setPcOperatorNum(id, operatorNum);
	}
	/*
	 * ����¼���ļ���,����·��
	 */
	private String getAbsoluteFileName(String fileName){
		StringBuffer sb = new StringBuffer();
		sb.append(this.DRIVE);
		sb.append(fileName);		
		return sb.toString();
	}
	private String getRelativeFileName(String inPort){
		StringBuffer sb = new StringBuffer();
		sb.append(this.PATH);
		//
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/");
		SimpleDateFormat sdf1=new SimpleDateFormat("HHmmss");
		Date d = new Date();
		sb.append(sdf.format(d));
		sb.append(sdf1.format(d));
		//
		sb.append("_");		
		sb.append(inPort);
		//		
		sb.append(this.FILE_EXT);
		return sb.toString();
	}
	public static void main(String[] s){
		
//		System.out.println(ss);
	}
}
