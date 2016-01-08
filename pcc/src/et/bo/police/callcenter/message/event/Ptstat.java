package et.bo.police.callcenter.message.event;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.callcenter.CardState;
import et.bo.police.callcenter.ConstantRule;
import et.bo.police.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *语音系统可以向服务器询问坐席状态。
PTSTAT:;
服务器向语音系统发送坐席状态信息。

SETPRT:<物理端口>，<0/1>;
1- 该坐席进入服务状态。
0- 该坐席退出服务状态。

 */
public class Ptstat extends BaseEvent {
	private static Log log = LogFactory.getLog(Ptstat.class);
	@Override
	protected void execute() {
		//刷新工控机的座席状态
		this.eventHelper.refreshIccOperatorState();		
	}
}
