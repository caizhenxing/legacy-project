package et.bo.police.callcenter.message.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *当坐席分机取机与外线用户开始通话后，语音系统向服务器发送接通事件。
	PTTALK：<呼入端口>，<座席端口>；
 */
public class Pttalk extends BaseEvent {
	private static Log log = LogFactory.getLog(Pttalk.class);
	@Override
	protected void execute() {
		//并对座席停止振铃命令
		//运行刷新座席面版流程。
		//开始录音，产生录音文件名。

	}

}
