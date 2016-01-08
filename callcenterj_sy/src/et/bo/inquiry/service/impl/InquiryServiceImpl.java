/**
 * 	@(#) InqueryServiceImpl.java 2008-4-1 下午01:13:21
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package et.bo.inquiry.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import et.bo.flow.service.FlowService;
import et.bo.inquiry.service.InquiryService;
import et.bo.messages.show.MessageCollection;
import et.bo.servlet.StaticServlet;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperInquiryCard;
import et.po.OperInquiryinfo;
import et.po.OperSadinfo;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.container.SpringRunningContainer;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaActionFormDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * et.bo.inquiry.service.InquiryService的实现类
 * 
 * @author 梁云锋
 * 
 */
public class InquiryServiceImpl implements InquiryService {
	static Logger log = Logger.getLogger(InquiryServiceImpl.class);

	private int num;

	private BaseDAO dao;

	private KeyService ks;

	private ClassTreeService classTree;
	private FlowService flowService = null;
	private BasicDataSource bds = null;
	public FlowService getFlowService() {
		return flowService;
	}
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public ClassTreeService getClassTree() {
		return classTree;
	}

	public void setClassTree(ClassTreeService classTree) {
		this.classTree = classTree;
	}
	public List<DynaBeanDTO> query(IBaseDTO dto, PageInfo pi) {

		// TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		InquiryHelp h = new InquiryHelp();
		String rootNodeId = classTree.getIdByNickname("inquiryTypes");
		
		Object[] result = (Object[]) dao.findEntity(h
				.query(dto, pi, rootNodeId));
		num = dao.findEntitySize(h.query(dto, pi, rootNodeId));

		for (int i = 0, size = result.length; i < size; i++) {
			OperInquiryinfo po = (OperInquiryinfo) result[i];
			list.add(po2dto(po));
		}
		return list;
	}

	/**
	 * 将PO对象信息转换为DTO对象信息
	 * @param po
	 * @return
	 */
	private DynaBeanDTO po2dto(OperInquiryinfo po) {
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("id", po.getId());
		dto.set("inquiryTypeLabel", classTree.getLabelById(po.getDictInquiryType()));
		dto.set("topic", po.getTopic());
		dto.set("organiztion", po.getOrganiztion());
		dto.set("organizers", po.getOrganizers());
		dto.set("beginTime", TimeUtil.getTheTimeStr(po.getBeginTime(), "yyyy-MM-dd"));
		
		Date now = new Date();
		String enddate = TimeUtil.getTheTimeStr(po.getEndTime(), "yyyy-MM-dd");
		if(po.getEndTime().after(now) ){
			enddate = "<font color=\"#0000FF\">"+ enddate +"</font>";
		}
		dto.set("endTime", enddate);
		dto.set("state", po.getState());
		dto.set("reportState", po.getReportState());
		return dto;
	}

	public int getInquirySize() {
		// TODO Auto-generated method stub
		return num;
	}

	public void add(DynaActionFormDTO dto) {
		// TODO Auto-generated method stub
		OperInquiryinfo po = new OperInquiryinfo();
		String id = ks.getNext("oper_inquiry");
		po.setId(id);
		po.setDictInquiryType((String) dto.get("inquiryType"));
		po.setBeginTime(TimeUtil.getTimeByStr((String) dto.get("beginTime"),
				"yyyy-MM-dd"));
		po.setEndTime(TimeUtil.getTimeByStr((String) dto.get("endTime"),
				"yyyy-MM-dd"));
		po.setOrganiztion((String) dto.get("organiztion"));
		po.setTopic((String) dto.get("topic"));
		po.setAim((String) dto.get("aim"));
		po.setOrganizers((String) dto.get("organizers"));
		po.setActors((String) dto.get("actors"));
		
		String state = (String) dto.get("state");
//		flowService.addOrUpdateFlow("调查问卷设计库", id, state, (String) dto.get("subid"));
		flowService.addOrUpdateFlow("调查问卷设计库", id, state, (String) dto.get("subid"),null);
		po.setState(state);
		po.setCaserid(dto.get("subid").toString());
		dao.saveEntity(po);
		addBatchQuestion(po, dto);
	}

	public void update(DynaActionFormDTO dto) {
		
		OperInquiryinfo inquiry = (OperInquiryinfo) dao.loadEntity(
				OperInquiryinfo.class, dto.get("id").toString());
		inquiry.setActors((String) dto.get("actors"));
		inquiry.setAim((String) dto.get("aim"));
		inquiry.setBeginTime(TimeUtil.getTimeByStr((String) dto
				.get("beginTime"), "yyyy-MM-dd"));
		inquiry.setEndTime(TimeUtil.getTimeByStr((String) dto.get("endTime"),
				"yyyy-MM-dd"));
		inquiry.setDictInquiryType((String) dto.get("inquiryType"));
		inquiry.setOrganizers((String) dto.get("organizers"));
		inquiry.setOrganiztion((String) dto.get("organiztion"));
		inquiry.setTopic((String) dto.get("topic"));
		
		String state = (String) dto.get("state");
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put(SysStaticParameter.WENJUANSHEJI_MESSAGE, inquiry);
		
//		flowService.addOrUpdateFlow("调查问卷设计库", (String)dto.get("id"), state, (String) dto.get("subid"));
		//flowService.addOrUpdateFlow("调查问卷设计库", (String)dto.get("id"), state, (String) dto.get("subid"),inquiry.getState());
		inquiry.setState(state);
		
		String submitUser = dto.get("subid").toString();
		inquiry.setCaserid(submitUser);
		
		addBatchQuestion(inquiry, dto);
		
//		 如果审核状态为待审和已审，发送的方向是向审核人的方向发送短消息
		if (state.equals("待审") || state.equals("已审")) {
			List l = StaticServlet.userPowerMap.get("调查问卷设计库");
			for (int i = 0; i < l.size(); i++) {
				String audingUser = (String) l.get(i);
			
				flowService.addOrUpdateFlow("调查问卷设计库",
						(String) dto.get("id"), state, submitUser,
						audingUser);
				
				//用来存放map的list
				List subList = new ArrayList();
				if(MessageCollection.m_instance.get(audingUser)!=null){
					subList = MessageCollection.m_instance.get(audingUser);
					subList.add(m);
				}else{
					subList.add(m);
				}
				MessageCollection.m_instance.put(audingUser, subList);
			}
		}
//		 如果审核状态是驳回，发送的方向是提交人的方向
		else if (state.equals("驳回")) {
			flowService.addOrUpdateFlow("调查问卷设计库",
					(String) dto.get("id"), state, submitUser,
					(String)dto.get("subid"));
			//用来存放map的list
			List subList = new ArrayList();
			if(MessageCollection.m_instance.get(submitUser)!=null&&MessageCollection.m_instance.get(submitUser).size()>0){
				subList = MessageCollection.m_instance.get(submitUser);
				subList.add(m);
			}else{
				subList.add(m);
			}
			MessageCollection.m_instance.put(submitUser, subList);
		}else if (state.equals("发布")) {
			//osi.setCaseTime(TimeUtil.getNowTime());
		}
	}

	public void delete(DynaActionFormDTO dto) {
		// TODO Auto-generated method stub
		OperInquiryinfo inquiry = (OperInquiryinfo) dao.loadEntity(
				OperInquiryinfo.class, dto.get("id").toString());
		Iterator<OperInquiryCard> i = inquiry.getOperInquiryCards().iterator();
		while (i.hasNext()) {
			OperInquiryCard tmp = i.next();
			dao.removeEntity(tmp);
		}
		dao.removeEntity(inquiry);
	}

	/**
	 * 批量添加调查问题信息
	 * 在添加、修改调查主题时添加多条属于该主题的问题信息
	 * @param inquiry 调查主题对象
	 */
	private void addBatchQuestion(OperInquiryinfo inquiry, DynaActionFormDTO dto) {
		Date createtime = new Date();
		String[] dict_question_type = dto.getStrings("dict_question_type");
		String[] question = dto.getStrings("question");
		String[] alternatives = dto.getStrings("alternatives");
			
		for(int i = 0; i<question.length; i++){
				
			if(question[i] != null && !question[i].trim().equals("")){
				
				OperInquiryCard po = new OperInquiryCard();
				po.setId(ks.getNext("oper_inquiry_card"));
				po.setOperInquiryinfo(inquiry);
				po.setAlternatives(alternatives[i]);
				po.setCreateTime(createtime);
				po.setDictQuestionType(dict_question_type[i]);
				po.setQuestion(question[i]);
				po.setDisplayOrder(i+1);
				dao.saveEntity(po);
			}
		}
	}

	public IBaseDTO getInquiryInfo(String id) {
		// TODO Auto-generated method stub
		OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(
				OperInquiryinfo.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("id", po.getId());
		dto.set("inquiryType", po.getDictInquiryType());
		dto.set("beginTime", TimeUtil.getTheTimeStr(po.getBeginTime(),
				"yyyy-MM-dd"));
		dto.set("endTime", TimeUtil
				.getTheTimeStr(po.getEndTime(), "yyyy-MM-dd"));
		dto.set("organiztion", po.getOrganiztion());
		dto.set("topic", po.getTopic());
		dto.set("aim", po.getAim());
		dto.set("organizers", po.getOrganizers());
		dto.set("actors", po.getActors());
		dto.set("state", po.getState());
		dto.set("caserid", po.getCaserid());
		// 将OperInquiryCard的PO对象转换成DTO对象
		List<DynaBeanDTO> cards = new ArrayList<DynaBeanDTO>();

		Iterator<OperInquiryCard> i = po.getOperInquiryCards().iterator();
		while (i.hasNext()) {
			DynaBeanDTO card = new DynaBeanDTO();
			OperInquiryCard tmp = i.next();
			card.set("id", tmp.getId());
			card.set("dictQuestionType", InquiryHelp.getLabelByValue(tmp
					.getDictQuestionType()));
			card.set("question", tmp.getQuestion());
			card.set("alternatives", tmp.getAlternatives());
			cards.add(card);
		}
		BubbleVectorById(cards);
		dto.set("cards", cards);
		return dto;
	}
	private void BubbleVectorById(List<DynaBeanDTO> dList ) //外部不可调用
	{
		try
		{
			DynaBeanDTO temp = null;
			
			if(dList==null||dList.size()==0)
			{
				return;
			}
			for(int j=0;j<dList.size();j++)
			{
				 for(int i=0;i<dList.size()-j;i++) 
				 { 
					 if ((i+1)<dList.size())
					 {
						 String orderi = (String)dList.get(i).get("id");
						 String orderi2 = (String)dList.get(i+1).get("id");
						 if(orderi.compareTo(orderi2)>0) 
						 { 
							 temp=dList.get(i); 
							 dList.set(i, dList.get(i+1)); 
							 dList.set(i+1,temp); 
						 }
					 }
				 }
			} 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public List<DynaBeanDTO> filter() {
		// TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		InquiryHelp h = new InquiryHelp();
		Object[] result = (Object[]) dao.findEntity(h.filter());
		for (int i = 0, size = result.length; i < size; i++) {
			OperInquiryinfo po = (OperInquiryinfo) result[i];
			list.add(po2dto(po));
		}
		return list;
	}

	public List<DynaBeanDTO> getCardInfo(String id) {
		// TODO Auto-generated method stub
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		OperInquiryinfo inquiry = (OperInquiryinfo) dao.loadEntity(
				OperInquiryinfo.class, id);
		Set<OperInquiryCard> cards = inquiry.getOperInquiryCards();
		Iterator<OperInquiryCard> i = cards.iterator();
		while (i.hasNext()) {
			OperInquiryCard card = i.next();
			DynaBeanDTO dto = new DynaBeanDTO();
			dto.set("id",card.getId());
			dto.set("questionType", card.getDictQuestionType());
			dto.set("questionTypeLabel", InquiryHelp.getLabelByValue(card
					.getDictQuestionType()));
			dto.set("question", card.getQuestion());
			String[] s = card.getAlternatives().split(";");
			List<String> alternatives = Arrays.asList(s);
			dto.set("alternatives", alternatives);
			list.add(dto);
		}
		return list;
	}
	
	public List<DynaBeanDTO> getCardInfoReport(String id) {
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		OperInquiryinfo inquiry = (OperInquiryinfo) dao.loadEntity(OperInquiryinfo.class, id);
		Set<OperInquiryCard> cards = inquiry.getOperInquiryCards();
		Iterator<OperInquiryCard> i = cards.iterator();
		while (i.hasNext()) {
			OperInquiryCard card = i.next();
			DynaBeanDTO dto = new DynaBeanDTO();
			
			String card_id = card.getId();
			String questionType = card.getDictQuestionType();
			String question = card.getQuestion();
			
			dto.set("id", card_id);
			dto.set("questionType", questionType);
			dto.set("questionTypeLabel", InquiryHelp.getLabelByValue(questionType));
			dto.set("question", question);
			
			String[] s = card.getAlternatives().split(";");
			List alternatives = new ArrayList();
			int sum = getSum(card_id);
			if(questionType != null && questionType.equals("001") || questionType.equals("002")){
				for(int j = 0; j < s.length; j++){
					Map map = new HashMap();
					int count = getCount(card_id, s[j]);
					int imgWidth = 0;
					if(sum > 0){
						imgWidth = count*100/sum;
					}
					map.put("answer", s[j]);
					map.put("imgWidth", imgWidth);
					map.put("count", count);
					alternatives.add(map);
				}
			
			}else{
				alternatives = getAnswerList(card_id, question);
			}
			
			dto.set("alternatives", alternatives);
			
			list.add(dto);
		}
		return list;
	}
	
	private int getSum(String card_id){
		
		int sum = 0;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT count(*) FROM oper_inquiry_result where card_id = '"+ card_id +"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				sum = rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		return sum;
	}
	private int getCount(String card_id, String answer){
		
		int count = 0;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT count(*) FROM oper_inquiry_result where card_id = '"+ card_id +"' and answer = '"+ answer +"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		return count;
	}
	
	private List getAnswerList(String card_id, String question){
		List list = new ArrayList();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT answer FROM oper_inquiry_result where card_id = '"+ card_id +"' and question = '"+ question +"'";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String answer = rs.getString(1);
				list.add(answer);
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		return list;
	}
	
	public IBaseDTO getInquiryInfoReport(String id) {
		
		OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(OperInquiryinfo.class, id);
		
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("id", po.getId());
		dto.set("beginTime", TimeUtil.getTheTimeStr(po.getBeginTime(), "yyyy-MM-dd"));
		dto.set("endTime", TimeUtil.getTheTimeStr(po.getEndTime(), "yyyy-MM-dd"));
		dto.set("organiztion", po.getOrganiztion());
		dto.set("topic", po.getTopic());
		dto.set("organizers", po.getOrganizers());
		dto.set("actors", po.getActors());
		
		dto.set("reportTopic", po.getReportTopic());
		dto.set("reportTopic2", po.getReportTopic2());
		dto.set("reportCopywriter", po.getReportCopywriter());
		dto.set("reportKeyword", po.getReportKeyword());
		dto.set("reportAbstract", po.getReportAbstract());
		dto.set("reportSwatch", po.getReportSwatch());
		dto.set("reportEfficiency", po.getReportEfficiency());
		dto.set("reportContent", po.getReportContent());
		dto.set("reportReview", po.getReportReview());
		dto.set("reportRemark", po.getReportRemark());
		dto.set("reportState", po.getReportState());
		return dto;
	}
	
	public IBaseDTO getInquiryInfoReportLast() {
		IBaseDTO dto = new DynaBeanDTO();
		Object[] result = (Object[]) dao.findEntity(new InquiryHelp().queryLast());
		if(result!=null && result.length==1){
			OperInquiryinfo po = (OperInquiryinfo)result[0];
			dto.set("id", po.getId());
			dto.set("beginTime", TimeUtil.getTheTimeStr(po.getBeginTime(), "yyyy-MM-dd"));
			dto.set("endTime", TimeUtil.getTheTimeStr(po.getEndTime(), "yyyy-MM-dd"));
			dto.set("organiztion", po.getOrganiztion());
			dto.set("topic", po.getTopic());
			dto.set("organizers", po.getOrganizers());
			dto.set("actors", po.getActors());
			
			dto.set("reportTopic", po.getReportTopic());
			dto.set("reportTopic2", po.getReportTopic2());
			dto.set("reportCopywriter", po.getReportCopywriter());
			dto.set("reportKeyword", po.getReportKeyword());
			dto.set("reportAbstract", po.getReportAbstract());
			dto.set("reportSwatch", po.getReportSwatch());
			dto.set("reportEfficiency", po.getReportEfficiency());
			dto.set("reportContent", po.getReportContent());
			dto.set("reportReview", po.getReportReview());
			dto.set("reportRemark", po.getReportRemark());
		}
		return dto;
	}
	
	public void updateReport(DynaActionFormDTO dto) {
		
		OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(OperInquiryinfo.class, dto.get("id").toString());
		
		po.setBeginTime(TimeUtil.getTimeByStr((String) dto.get("beginTime"), "yyyy-MM-dd"));
		po.setEndTime(TimeUtil.getTimeByStr((String) dto.get("endTime"), "yyyy-MM-dd"));
		po.setOrganizers((String) dto.get("organizers"));
		po.setOrganiztion((String) dto.get("organiztion"));
		po.setTopic((String) dto.get("topic"));
		po.setActors((String) dto.get("actors"));
		
		po.setReportTopic((String)dto.get("reportTopic"));
		po.setReportTopic2((String)dto.get("reportTopic2"));
		po.setReportCopywriter((String)dto.get("reportCopywriter"));
		po.setReportKeyword((String)dto.get("reportKeyword"));
		po.setReportAbstract((String)dto.get("reportAbstract"));
		po.setReportSwatch((String)dto.get("reportSwatch"));
		po.setReportEfficiency((String)dto.get("reportEfficiency"));
		po.setReportContent((String)dto.get("reportContent"));
		po.setReportReview((String)dto.get("reportReview"));
		po.setReportRemark((String)dto.get("reportRemark"));

		String state = (String) dto.get("reportState");
//		flowService.addOrUpdateFlow("调查信息分析库", "report" + (String) dto.get("id"), state, (String) dto.get("subid"));
		flowService.addOrUpdateFlow("调查信息分析库", "report" + (String) dto.get("id"), state, (String) dto.get("subid"),po.getState());
		po.setReportState(state);
		
		dao.updateEntity(po);
	}
	
	
	/**
	 * 删除报告数据
	 * @param dto
	 */
	public void deleteReport(DynaActionFormDTO dto) {
		
		OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(OperInquiryinfo.class, dto.get("id").toString());
		
		po.setBeginTime(TimeUtil.getTimeByStr((String) dto.get("beginTime"), "yyyy-MM-dd"));
		po.setEndTime(TimeUtil.getTimeByStr((String) dto.get("endTime"), "yyyy-MM-dd"));
		po.setOrganizers((String) dto.get("organizers"));
		po.setOrganiztion((String) dto.get("organiztion"));
		po.setTopic((String) dto.get("topic"));
		po.setActors((String) dto.get("actors"));
		
		po.setReportTopic("");
		po.setReportTopic2("");
		po.setReportCopywriter("");
		po.setReportKeyword("");
		po.setReportAbstract("");
		po.setReportSwatch("");
		po.setReportEfficiency("");
		po.setReportContent("");
		po.setReportReview("");
		po.setReportRemark("");

		String state = "原始";
//		flowService.addOrUpdateFlow("调查信息分析库", "report" + (String) dto.get("id"), state, (String) dto.get("subid"));
		flowService.addOrUpdateFlow("调查信息分析库", "report" + (String) dto.get("id"), state, (String) dto.get("subid"),null);
		po.setReportState(state);
		
		dao.updateEntity(po);
	}
	
	/**
	 * 根据调查报告ID获得问题信息ID
	 * 
	 * @param id 调查报告的ID
	 */
	public String getInquiryId(String id){
		String inquiryid = null;
		OperInquiryinfo po = (OperInquiryinfo) dao.loadEntity(OperInquiryinfo.class, id);
		Iterator<OperInquiryCard> i = po.getOperInquiryCards().iterator();
		while (i.hasNext()) {
			OperInquiryCard tmp = i.next();
			inquiryid = tmp.getId().toString();
		}
		return inquiryid;
	}
	/**
	 * @return bds
	 */
	public BasicDataSource getBds() {
		return bds;
	}

	/**
	 * @param bds 要设置的 bds
	 */
	public void setBds(BasicDataSource bds) {
		this.bds = bds;
	}
	
	/**
	 * 状态转换
	 */
	public String changeState(String state){		
		if("wait".equals(state)){
			return "待审";		
		}else if("back".equals(state)){
			return "驳回";		
		}else if("pass".equals(state)){			
			return "已审";		
		}else if("issuance".equals(state)){
			return "发布";		
		}else{
			return "";		
		}		 
	}

	/**
	 * 删除消息
	 * @param agentId
	 * @param state
	 */
	public void clearMessage(String agentId,String state){
		if("back".equals(state)){
			String str_state="驳回";
			List l = (List)MessageCollection.m_instance.get(agentId);
			if(l!=null){		
				Iterator it = l.iterator();				
				while(it.hasNext()){
					Map m = (Map)it.next();
					//如果农产品供求库案例不为空
					if (m.get(SysStaticParameter.WENJUANSHEJI_MESSAGE)!=null) {
						OperInquiryinfo oc = (OperInquiryinfo)m.get(SysStaticParameter.WENJUANSHEJI_MESSAGE);	
						//如果状态相同						
						if(oc.getState().equals(str_state)){
							it.remove();
						}
					}
				}
			}
		}else{
			String str_state=changeState(state);
			if(str_state.length()>1){
				List l_agent = StaticServlet.userPowerMap.get("调查问卷设计库");
				for (int i = 0; i < l_agent.size(); i++) {
					String audingUser = (String) l_agent.get(i);				
					List l = (List)MessageCollection.m_instance.get(audingUser);
					
//					List l = (List)MessageCollection.m_instance.get(agentId);
					if(l!=null){		
						Iterator it = l.iterator();				
						while(it.hasNext()){
							Map m = (Map)it.next();
							//如果效果案例不为空
							if (m.get(SysStaticParameter.WENJUANSHEJI_MESSAGE)!=null) {
								OperInquiryinfo oc = (OperInquiryinfo)m.get(SysStaticParameter.WENJUANSHEJI_MESSAGE);	
								//如果状态相同
								if(oc.getState().equals(str_state)){
									it.remove();
								}
							}
						}
					}
				}
			}
		}		
	}
}
