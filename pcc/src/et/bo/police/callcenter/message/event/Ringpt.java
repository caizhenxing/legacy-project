package et.bo.police.callcenter.message.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *当语音系统对某个坐席分机振铃时，向服务器发送振铃事件。
RINGPT:<呼入端口>,<座席端口>,<座席逻辑号>,<主叫号码>,
 */

public class Ringpt extends BaseEvent {
	
	private static Log log = LogFactory.getLog(Ringpt.class);
	@Override
	protected void execute() {
		
		//1对于座席进行发送振铃的命令
		
		//2运行刷新座席面版流程。
		
	}

}
