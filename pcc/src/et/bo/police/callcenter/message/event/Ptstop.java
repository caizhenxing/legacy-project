package et.bo.police.callcenter.message.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *当坐席分机与外线用户通话结束后，语音系统向服务器发送停止事件。
PTSTOP：<呼入端口>，<座席端口>；
如果在振铃前，座席端口是255字符串。
 */
public class Ptstop extends BaseEvent {
	private static Log log = LogFactory.getLog(Ptstop.class);
	@Override
	protected void execute() {
		//刷新CardState状态
		//@ todo
		//运行刷新座席面版流程。
		this.eventHelper.refreshOperatorPanel();
		//产生流水号码，写日志到数据库中。或者启动dbschema。并且清理ConnectInfo的map。
//		String id = 
//		this.eventHelper.saveInfo()
	}

}
