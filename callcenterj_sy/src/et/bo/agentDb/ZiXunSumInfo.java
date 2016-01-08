/**
 * <p>座席面板取取服务总时</p>
 * 
 * @version 2008-07-09
 * @author wangwenquan
 */
package et.bo.agentDb;

import java.util.Map;

import et.bo.screen.service.ScreenService;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * <p>
 * 	统计座席面板座席资讯总量、日资讯量、工作时常、政策、医疗、企业服务、金农通、万事通、其他
 * 	这个类是并未实现 农委需求不明
 * </p>
 * @param String ymd 备用
 * @param String agent 备用
 * @param Map paramMap 放些需要的信息 
 * @return String 以分秒形式显示座席当日工作时间
 */
public class ZiXunSumInfo extends AgentInfoBean {
	private ScreenService ss;
	
	/**
	 * <p>
	 * 	统计座席面板座席资讯总量、日资讯量、工作时常、政策、医疗、企业服务、金农通、万事通、其他
 	 * 	这个类是并未实现 农委需求不明
 	 * </p>
	 */
	@Override
	public String getAgentInfo(String ymd, String agent, Map otherParam) {
		// TODO Auto-generated method stub
		DynaBeanDTO dto = ss.getZiXunSumDtl();
		StringBuffer sb = new StringBuffer();
		sb.append(dto.get("zixunsum")+"|");
		sb.append(dto.get("dayzixun")+"|");
		sb.append(dto.get("shichang")+"|");
		sb.append(dto.get("zhengce")+"|");
		sb.append(dto.get("yiliao")+"|");
		sb.append(dto.get("qiyefuwu")+"|");
		sb.append(dto.get("jinnongtong")+"|");
		sb.append(dto.get("wanshitong")+"|");
		sb.append(dto.get("other"));
		return null;
	}
	public ScreenService getSs() {
		return ss;
	}

	public void setSs(ScreenService ss) {
		this.ss = ss;
	}
}
