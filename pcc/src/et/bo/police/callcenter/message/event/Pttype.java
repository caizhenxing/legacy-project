package et.bo.police.callcenter.message.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *������ϵͳ����������Ӻ���Ҫ���������������ϵͳ��Ӳ������״̬��

PTTYPE:[�˿�],[�˿�����],[״̬]��
�˿�-  ����ϵͳ������˿ںš�
����-  �ö˿ڵ����͡�0-���߶˿ڣ�1-���߶˿ڡ�
״̬-  �ö˿ڵĵ�ǰ״̬��0-δ��װ��1-�𻵣�2-�رգ�3-������

 */
public class Pttype extends BaseEvent {
	private static Log log = LogFactory.getLog(Pttype.class);
	@Override
	protected void execute() {
		//��״̬���õ�map�У�ʵʱ��ӳ��صĵ�ǰ��״̬��

	}

}
