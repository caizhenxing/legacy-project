/**
 * <p>��ϯ���ȡȡ������ʱ</p>
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
 * 	ͳ����ϯ�����ϯ��Ѷ����������Ѷ��������ʱ�������ߡ�ҽ�ơ���ҵ���񡢽�ũͨ������ͨ������
 * 	������ǲ�δʵ�� ũί������
 * </p>
 * @param String ymd ����
 * @param String agent ����
 * @param Map paramMap ��Щ��Ҫ����Ϣ 
 * @return String �Է�����ʽ��ʾ��ϯ���չ���ʱ��
 */
public class ZiXunSumInfo extends AgentInfoBean {
	private ScreenService ss;
	
	/**
	 * <p>
	 * 	ͳ����ϯ�����ϯ��Ѷ����������Ѷ��������ʱ�������ߡ�ҽ�ơ���ҵ���񡢽�ũͨ������ͨ������
 	 * 	������ǲ�δʵ�� ũί������
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
