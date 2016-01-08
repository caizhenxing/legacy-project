/**
 * 沈阳卓越科技有限公司
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
 * 消息集合
 * 
 * @author zhangfeng
 * 
 */
public class MessageCollection {
	//设置已读标志
	public static final String IS_READ = "READED";
	//设置未读标志
	public static final String NOT_READ = "NOTREAD";
	
	// 用来存放消息，key值是座席员的座席号，value是座席员现在有的消息的数量，其中包括普通短消息，业务消息等，根据前边的字符区分是哪种消息并且实例成对象
	// list中存放的消息的个数
	// Map<String,Object>
	// common 普通短消息 对应的对象是 OperMessages
	// dianxinganli 案例库管理 对应的对象是 OperCaseinfo
	public static Map<String, Map<String,Object>> m_common_instance = new HashMap<String, Map<String,Object>>();
	public static Map<String, List<Map<String,Object>>> m_instance = new HashMap<String, List<Map<String,Object>>>();
	
	//加载注程处理类，用于判断状态，如果状态发生改变，则对应短消息也不进行发送
	private static FlowService fs = (FlowService)SpringRunningContainer.getInstance().getBean("FlowService");
	
	/**
	 * 根据座席号显示短消息的内容(普通短消息)
	 * @param agentNum
	 * @return result 结果显示的是这样几条数据，是一个字符串
	 * 需要待审核的条数，类别，审核状态
	 */
	public static String getMessageByagent(String agentNum){
		//根据工号得到列表，是否是多个人提交过来的信息，如果是多个同时处理
		List l = (List)m_instance.get(agentNum);
//		System.out.println("agentNum = "+agentNum);
//		System.out.println("list lenght = "+l.size());
		
		StringBuffer sb = new StringBuffer();		
		if(l!=null){		
			Iterator it = l.iterator();
				
			//看列表中有多少条短消息
			while(it.hasNext()){
				Map m = (Map)it.next();
				//如果普通案例不为空
				if (m.get(SysStaticParameter.DIANXINGANLI_MESSAGE)!=null) {
					OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.DIANXINGANLI_MESSAGE);
					sb.append(SysStaticParameter.DIANXINGANLI_MESSAGE+","+oc.getState()+",");
//					System.out.println("state = "+oc.getState());
				}
//				如果是焦点案例库				
				if (m.get(SysStaticParameter.JIAODIAN_MESSAGE)!=null) {
					OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.JIAODIAN_MESSAGE);
					sb.append(SysStaticParameter.JIAODIAN_MESSAGE+","+oc.getState()+",");
				}
//				如果是会诊案例库
				if (m.get(SysStaticParameter.HUIZHEN_MESSAGE)!=null) {
					OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.HUIZHEN_MESSAGE);
					sb.append(SysStaticParameter.HUIZHEN_MESSAGE+","+oc.getState()+",");
				}
//				如果是效果案例库
				if (m.get(SysStaticParameter.XIAOGUOANLI_MESSAGE)!=null) {
					OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.XIAOGUOANLI_MESSAGE);
					sb.append(SysStaticParameter.XIAOGUOANLI_MESSAGE+","+oc.getState()+",");
				}
//				如果是农产品供求库
				if (m.get(SysStaticParameter.GONGQIU_MESSAGE)!=null) {
					OperSadinfo oc = (OperSadinfo)m.get(SysStaticParameter.GONGQIU_MESSAGE);
					sb.append(SysStaticParameter.GONGQIU_MESSAGE+","+oc.getState()+",");
				}
//				如果是农产品价格库
				if (m.get(SysStaticParameter.JIAGE_MESSAGE)!=null) {
					OperPriceinfo oc = (OperPriceinfo)m.get(SysStaticParameter.JIAGE_MESSAGE);
					sb.append(SysStaticParameter.JIAGE_MESSAGE+","+oc.getState()+",");
				}
//				如果是调查问卷设计库
				if (m.get(SysStaticParameter.WENJUANSHEJI_MESSAGE)!=null) {
					OperInquiryinfo oc = (OperInquiryinfo)m.get(SysStaticParameter.WENJUANSHEJI_MESSAGE);
					sb.append(SysStaticParameter.WENJUANSHEJI_MESSAGE+","+oc.getState()+",");
				}
//				如果是企业信息库
				if (m.get(SysStaticParameter.QIYE_MESSAGE)!=null) {
					OperCorpinfo oc = (OperCorpinfo)m.get(SysStaticParameter.QIYE_MESSAGE);
					sb.append(SysStaticParameter.QIYE_MESSAGE+","+oc.getState()+",");
				}
//				如果是普通医疗服务信息库
				if (m.get(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE)!=null) {
					OperMedicinfo om = (OperMedicinfo)m.get(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE);
					sb.append(SysStaticParameter.PUTONGYILIAOFUWU_MESSAGE+","+om.getState()+",");
				}
//				如果是预约医疗服务信息库
				if (m.get(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE)!=null) {
					OperBookMedicinfo obm = (OperBookMedicinfo)m.get(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE);
					sb.append(SysStaticParameter.YUYUEYILIAOFUWU_MESSAGE+","+obm.getState()+",");
				}				
//				如果是市场分析库
				if (m.get(SysStaticParameter.SHICHANGFENXI_MESSAGE)!=null) {
					OperMarkanainfo om = (OperMarkanainfo)m.get(SysStaticParameter.SHICHANGFENXI_MESSAGE);
					sb.append(SysStaticParameter.SHICHANGFENXI_MESSAGE+","+om.getState()+",");
				}
//				如果是焦点追踪库
				if (m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE)!=null) {
					OperFocusinfo oc = (OperFocusinfo)m.get(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE);
					sb.append(SysStaticParameter.JIAODIANZHUIZONG_MESSAGE+","+oc.getState()+",");
				}
				
			}

				//判断是哪种类型的短消息
//				while(it.hasNext()){
//					String keyType = it.next().toString();
//					如果是典型案例管理
//					if (keyType.equals(SysStaticParameter.DIANXINGANLI_MESSAGE)) {
//						
//						OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.DIANXINGANLI_MESSAGE);
//						if(oc!=null){
//							if(fs.isRead(oc.getCaseId()).equals(NOT_READ)){
//								url = "../caseinfo/generalCaseinfo.do?method=toGeneralCaseinfoLoad&type=update&id="+oc.getCaseId();
//							}
//						}
//					}
//					如果是焦点案例库
//					if (keyType.equals(SysStaticParameter.JIAODIAN_MESSAGE)) {
//						OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.JIAODIAN_MESSAGE);
//						if(oc!=null){
//							if(fs.isRead(oc.getCaseId()).equals(NOT_READ)){
//								url = "../caseinfo/focusCaseinfo.do?method=toFocusCaseinfoLoad&type=update&id="+oc.getCaseId();
//							}
//						}
//					}
//					如果是会诊案例库
//					if (keyType.equals(SysStaticParameter.HUIZHEN_MESSAGE)) {
//						OperCaseinfo oc = (OperCaseinfo)m.get(SysStaticParameter.HUIZHEN_MESSAGE);
//						if(oc!=null){
//							if(fs.isRead(oc.getCaseId()).equals(NOT_READ)){
//								url = "../caseinfo/hzinfo.do?method=tohzinfoLoad&type=update&id="+oc.getCaseId();
//							}
//						}
//					}
//					如果是效果案例库
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
	 * 根据座席号显示短消息的内容(普通短消息)
	 * @param agentNum
	 * @return
	 */
	public static String getCommonMessageByagent(String agentNum){
		String url = "";
		//普通短消息
		Map m = (Map)m_common_instance.get(agentNum);

		//说明有短消息
		if(m!=null&&m.size()>0){
			Set s = m.keySet();
			Iterator it = s.iterator();
			//判断是哪种类型的短消息
			while(it.hasNext()){
				String keyType = it.next().toString();
				//如果是普通短消息
				if (keyType.equals(SysStaticParameter.COMMON_MESSAGE)) {
					OperMessages om = (OperMessages)m.get(SysStaticParameter.COMMON_MESSAGE);
					if(om!=null){
						//如果这条短消息是没有读过的，则处理
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
