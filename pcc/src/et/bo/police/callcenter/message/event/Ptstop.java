package et.bo.police.callcenter.message.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *����ϯ�ֻ��������û�ͨ������������ϵͳ�����������ֹͣ�¼���
PTSTOP��<����˿�>��<��ϯ�˿�>��
���������ǰ����ϯ�˿���255�ַ�����
 */
public class Ptstop extends BaseEvent {
	private static Log log = LogFactory.getLog(Ptstop.class);
	@Override
	protected void execute() {
		//ˢ��CardState״̬
		//@ todo
		//����ˢ����ϯ������̡�
		this.eventHelper.refreshOperatorPanel();
		//������ˮ���룬д��־�����ݿ��С���������dbschema����������ConnectInfo��map��
//		String id = 
//		this.eventHelper.saveInfo()
	}

}
