package et.bo.callcenter.message.event;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.callcenter.base.CardInfo;
import et.bo.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *������ϵͳ����������Ӻ���Ҫ���������������ϵͳ��Ӳ������״̬��
һ�η������еĿ�����Ϣ����ʽ��
����˿�#�߼��˿�#�˿�����#״̬! ����˿�#�߼��˿�#�˿�����#״̬!��������������������
PTTYPE:�ַ�����
����˿�-  ����ϵͳ������˿ںš�
�߼��˿�-  ����ϵͳ���߼��˿ڡ�
����-  �ö˿ڵ����͡�0-���߶˿ڣ�1-���߶˿ڡ�
״̬-  �ö˿ڵĵ�ǰ״̬��0-δ��װ��1-�𻵣�2-�رգ�3-������
 */
public class Pttype extends BaseEvent {
	private static Log log = LogFactory.getLog(Pttype.class);
//	private CardInfo cardInfo=null;
	private final String DELIM1="#";
	private final String DELIM2="!";
	@Override
	protected String execute() {
		//init		
		String sTmp =args[0];
		//��״̬���õ�map�У�ʵʱ��ӳ��صĵ�ǰ��״̬��
		CardInfo.initMap(parse(sTmp));
		/*
		 * ˢ����ϯ���ڹ��ػ�
		 */
		this.eventHelper.refreshIccOperatorState();
		return null;
	}
	
	/*
	 * s is:����˿�#�߼��˿�#�˿�����#״̬!����˿�#�߼��˿�#�˿�����#״̬!
	 * ������ƵĶ���
	 */
	private List parse(String s){
		log.debug("into parase!");
		List list = new ArrayList();
		if(null==s)return list;
		//need validate;
		//�����֤�ϸ�������£�
		String[] sPort=s.split(this.DELIM2);
		for(int i=0;i<sPort.length;i++){
			String[] s1 =sPort[i].split(this.DELIM1);
			log.debug("guxf001");
			log.debug(sPort[i]);
			CardInfo ci = new CardInfo();
			log.debug(ci.getIp());
			ci.setPhysicsPort(s1[0]);
			ci.setLogicPort(s1[1]);
			ci.setPortType(s1[2]);
			ci.setState(s1[3]);
			list.add(ci);
		}
		//
		return list;
	}
	
//	public static void main(String[] args) {
//		new MyLog();
//		String s="0#0#1#3!1#0#1#3!2#0#1#3!3#0#1#3!4#0#1#3!5#0#1#3!6#1#0#3!7#2#0#3!8#0#0#0!9#0#0#0!10#0#0#0!11#0#0#0!12#0#0#0!13#0#0#0!14#0#0#0!15#0#0#0!";
//		Pttype p=new Pttype();
//		p.parse(s);
//	}
	
}
