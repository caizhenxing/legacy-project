package et.bo.police.callcenter.message.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import et.bo.police.callcenter.message.BaseEvent;
/**
 * 
 * @author guxiaofeng
 *当语音系统与服务器连接后，需要向服务器发送语音系统的硬件配置状态。

PTTYPE:[端口],[端口类型],[状态]；
端口-  语音系统的物理端口号。
类型-  该端口的类型。0-内线端口；1-外线端口。
状态-  该端口的当前状态。0-未安装；1-损坏；2-关闭；3-正常。

 */
public class Pttype extends BaseEvent {
	private static Log log = LogFactory.getLog(Pttype.class);
	@Override
	protected void execute() {
		//将状态设置到map中，实时反映相关的当前的状态。

	}

}
