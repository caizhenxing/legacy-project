package et.bo.callcenter.message.event;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import et.bo.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *����ϵͳ�����������ѯ����ϯ״̬��
PTSTAT:;
������������ϵͳ������ϯ״̬��Ϣ��

SETPRT:<����˿�>��<0/1>;
1- ����ϯ�������״̬��
0- ����ϯ�˳�����״̬��

 */
public class Ptstat extends BaseEvent {
	private static Log log = LogFactory.getLog(Ptstat.class);
	@Override
	protected String execute() {
		//ˢ�¹��ػ�����ϯ״̬
		this.eventHelper.refreshIccOperatorState();
		return null;
	}
}
