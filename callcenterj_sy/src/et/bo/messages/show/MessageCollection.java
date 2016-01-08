/**
 * ����׿Խ�Ƽ����޹�˾
 */
package et.bo.messages.show;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import et.bo.flow.service.FlowService;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperCaseinfo;
import et.po.OperCorpinfo;
import et.po.OperFocusinfo;
import et.po.OperInquiryinfo;
import et.po.OperMessages;
import et.po.OperPriceinfo;
import et.po.OperSadinfo;
import et.po.OperBookMedicinfo;
import et.po.OperMedicinfo;
import et.po.OperMarkanainfo;
import excellence.framework.base.container.SpringRunningContainer;

/**
 * ��Ϣ����
 * 
 * @author zhangfeng
 * 
 */
public class MessageCollection {
	//�����Ѷ���־
	public static final String IS_READ = "READED";
	//����δ����־
	public static final String NOT_READ = "NOTREAD";
	
	// ���������Ϣ��keyֵ����ϯԱ����ϯ�ţ�value����ϯԱ�����е���Ϣ�����������а�����ͨ����Ϣ��ҵ����Ϣ�ȣ�����ǰ�ߵ��ַ�������������Ϣ����ʵ���ɶ���
	// list�д�ŵ���Ϣ�ĸ���
	// Map<String,Object>
	// common ��ͨ����Ϣ ��Ӧ�Ķ����� OperMessages
	// dianxinganli ��������� ��Ӧ�Ķ����� OperCaseinfo
	public static Map<String, Map<String,Object>> m_common_instance = new HashMap<String, Map<String,Object>>();
	public static Map<String, List<Map<String,Object>>> m_instance = new HashMap<String, List<Map<String,Object>>>();
	
	//����ע�̴����࣬�����ж�״̬�����״̬�����ı䣬���Ӧ����ϢҲ�����з���
	private static FlowService fs = (FlowService)SpringRunningContainer.getInstance().getBean("FlowService");
	
	/**
	 * ������ϯ����ʾ����Ϣ������(��ͨ����Ϣ)
	 * @param agentNum
	 * @return result �����ʾ���������������ݣ���һ���ַ���
	 * ��Ҫ����˵�������������״̬
	 */
	public static String getMessageByagent(String agentNum){
		//���ݹ��ŵõ��б��Ƿ��Ƕ�����ύ��������Ϣ������Ƕ��ͬʱ����
		List l = (List)m_instance.get(agentNum);
//		System.out.println("agentNum = "+agentNum);
//		System.out.println("list lenght = "+l.size());
		
		StringBuffer sb = new StringBuffer();		
		if(l!=null){		
			Iterator it = l.iterator();
				
			//���б����ж���������Ϣ
			while(it.hasNext()){
				Map m = (Map)it.next();
				//�����ͨ������Ϊ��
				if (m.get(SysStaticParameter.DIANXINGANLI_MESSAGE)!=null) {
					OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.DIANXINGANLI_MESSAGE);
					sb.append(SysStaticParameter.DIANXINGANLI_MESSAGE+","+oc.getState()+",");
//					System.out.println("state = "+oc.getState());
				}
//				����ǽ��㰸����				
				if (m.get(SysStaticParameter.JIAODIAN_MESSAGE)!=null) {
					OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.JIAODIAN_MESSAGE);
					sb.append(SysStaticParameter.JIAODIAN_MESSAGE+","+oc.getState()+",");
				}
//				����ǻ��ﰸ����
				if (m.get(SysStaticParameter.HUIZHEN_MESSAGE)!=null) {
					OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.HUIZHEN_MESSAGE);
					sb.append(SysStaticParameter.HUIZHEN_MESSAGE+","+oc.getState()+",");
				}
//				�����Ч��������
				if (m.get(SysStaticParameter.XIAOGUOANLI_MESSAGE)!=null) {
					OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.XIAOGUOANLI_MESSAGE);
					sb.append(SysStaticParameter.XIAOGUOANLI_MESSAGE+","+oc.getState()+",");
				}
//				�����ũ��Ʒ�����
				if (m.get(SysStaticParameter.GONGQIU_MESSAGE)!=null) {
					OperSadinfo oc = (OperSadinfo)m.get(SysStaticParameter.GONGQIU_MESSAGE);
					sb.append(SysStaticParameter.GONGQIU_MESSAGE+","+oc.getState()+",");
				}
//				�����ũ��Ʒ�۸��
				if (m.get(SysStaticParameter.JIAGE_MESSAGE)!=null) {
					OperPriceinfo oc = (OperPriceinfo)m.get(SysStaticParameter.JIAGE_MESSAGE);
					sb.append(SysStaticParameter.JIAGE_MESSAGE+","+oc.getState()+",");
				}
//				����ǵ����ʾ���ƿ�
				if (m.get(SysStaticParameter.WENJUANSHEJI_MESSAGE)!=null) {
					OperInquiryinfo oc = (OperInquiryinfo)m.get(SysStaticParameter.WENJUANSHEJI_MESSAGE);
					sb.append(SysStaticParameter.WENJUANSHEJI_MESSAGE+","+oc.getState()+",");
				}
//				�������ҵ��Ϣ��
				if (m.get(SysStaticParameter.QIYE_MESSAGE)!=null) {
					OperCorpinfo oc = (OperCorpinfo)m.get(SysStaticParameter.QIYE_MESSAGE);
					sb.append(SysStaticParameter.QIYE_MESSAGE+","+oc.getState()+",");
				}
//				�������ͨҽ�Ʒ�����Ϣ��
				if (m.get(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE)!=null) {
					OperMedicinfo om = (OperMedicinfo)m.get(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE);
					sb.append(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE+","+om.getState()+",");
				}
//				�����ԤԼҽ�Ʒ�����Ϣ��
				if (m.get(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE)!=null) {
					OperBookMedicinfo obm = (OperBookMedicinfo)m.get(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE);
					sb.append(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE+","+obm.getState()+",");
				}				
//				������г�������
				if (m.get(SysStaticParameter.SHICHANGFENXI_MESSAGE)!=null) {
					OperMarkanainfo om = (OperMarkanainfo)m.get(SysStaticParameter.SHICHANGFENXI_MESSAGE);
					sb.append(SysStaticParameter.SHICHANGFENXI_MESSAGE+","+om.getState()+",");
				}
//				����ǽ���׷�ٿ�
				if (m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE)!=null) {
					OperFocusinfo oc = (OperFocusinfo)m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE);
					sb.append(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE+","+oc.getState()+",");
				}
				
			}

				//�ж����������͵Ķ���Ϣ
//				while(it.hasNext()){
//					String keyType = it.next().toString();
//					����ǵ��Ͱ�������
//					if (keyType.equals(SysStaticParameter.DIANXINGANLI_MESSAGE)) {
//						
//						OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.DIANXINGANLI_MESSAGE);
//						if(oc!=null){
//							if(fs.isRead(oc.getCaseId()).equals(NOT_READ)){
//								url = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=update&id="+oc.getCaseId();
//							}
//						}
//					}
//					����ǽ��㰸����
//					if (keyType.equals(SysStaticParameter.JIAODIAN_MESSAGE)) {
//						OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.JIAODIAN_MESSAGE);
//						if(oc!=null){
//							if(fs.isRead(oc.getCaseId()).equals(NOT_READ)){
//								url = "../caseinfo/focusCaseinfo.do?method=toFocusCaseinfoLoad&type=update&id="+oc.getCaseId();
//							}
//						}
//					}
//					����ǻ��ﰸ����
//					if (keyType.equals(SysStaticParameter.HUIZHEN_MESSAGE)) {
//						OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.HUIZHEN_MESSAGE);
//						if(oc!=null){
//							if(fs.isRead(oc.getCaseId()).equals(NOT_READ)){
//								url = "../caseinfo/hzinfo.do?method=tohzinfoLoad&type=update&id="+oc.getCaseId();
//							}
//						}
//					}
//					�����Ч��������
//					if (keyType.equals(SysStaticParameter.XIAOGUOANLI_MESSAGE)) {
//						OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.XIAOGUOANLI_MESSAGE);
//						if(oc!=null){
//							if(fs.isRead(oc.getCaseId()).equals(NOT_READ)){
//								url = "../caseinfo/effectCaseinfo.do?method=toEffectCaseinfoLoad&type=update&id="+oc.getCaseId();
//							}
//						}
//					}
//				}
		}
//		System.out.println("sb.toString("+agentNum+") = "+sb.toString());
		return sb.toString();
	}
	
	
	/**
	 * ������ϯ����ʾ����Ϣ������(��ͨ����Ϣ)
	 * @param agentNum
	 * @return
	 */
	public static String getCommonMessageByagent(String agentNum){
		String url = "";
		//��ͨ����Ϣ
		Map m = (Map)m_common_instance.get(agentNum);

		//˵���ж���Ϣ
		if(m!=null&&m.size()>0){
			Set s = m.keySet();
			Iterator it = s.iterator();
			//�ж����������͵Ķ���Ϣ
			while(it.hasNext()){
				String keyType = it.next().toString();
				//�������ͨ����Ϣ
				if (keyType.equals(SysStaticParameter.COMMON_MESSAGE)) {
					OperMessages om = (OperMessages)m.get(SysStaticParameter.COMMON_MESSAGE);
					if(om!=null){
						//�����������Ϣ��û�ж����ģ�����
						if (om.getDictReadFlag().equals("0")) {
							url = "../messages/messages.do?method=toMessagesLoad&type=show&id="+om.getMessageId();
						}
					}
				}
			}
		}
		return url;
	}

}
