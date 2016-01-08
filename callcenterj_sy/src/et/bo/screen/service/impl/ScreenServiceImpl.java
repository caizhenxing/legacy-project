package et.bo.screen.service.impl;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import et.bo.screen.service.ScreenService;
import et.bo.xml.XmlBuild;

import et.po.OperCaseinfo;
import et.po.OperCustinfo;
import et.po.OperPriceinfo;
import et.po.OperQuestion;
import et.po.ScreenOperSadinfo;
import et.po.ScreenQuickMessage;

import excellence.common.classtree.ClassTreeService;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;

/**
 * <p>����Ļ���ĳ����ı�ʱ�������λһ��ǰ̨js��ȡ</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public class ScreenServiceImpl implements ScreenService {
	private BaseDAO dao = null;
	private ClassTreeService cts;
	/**
	 * ���ScreenPriceDay����ļ���
	 * ÿ�ռ۸��ѯ��Ҫ ��Ʒ����(product_name) ���۲���(cust_addr) ��Ʒ�۸�(product_price/priduct_unit 8.00Ԫ/��) �۸�����(�۸�����)
	 * @return List
	 */
	public List getScreenPriceInfo() {
		ScreenServiceHelp pish=new ScreenServiceHelp();
		Object[] aa = null;
		try
		{
			aa= dao.findEntity(pish.screenPriceInfoQuery());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		List list=new ArrayList();
		if(aa!=null&&aa.length>0){
			for(int i = 0, size = aa.length; i < size; i++)
			{
				OperPriceinfo opi = (OperPriceinfo) aa[i];
				//ScreenPriceDay oci = (ScreenPriceDay) aa[i];
				DynaBeanDTO dto = new DynaBeanDTO();
				dto.set("addtime", opi.getAddtime());
				String aa1=opi.getCustAddr();				
				if(opi.getCustAddr().indexOf("��")>1){				
					String str=opi.getCustAddr().substring(0, opi.getCustAddr().indexOf("��")+1);
					dto.set("custAddr",str);
				}else{
					dto.set("custAddr",aa1);
				}				
				dto.set("dictProductType1", opi.getDictProductType1());
				dto.set("dictProductType2", opi.getDictProductType2());
				dto.set("id", opi.getPriceId());
				dto.set("priceUnit", opi.getPriceUnit());
				dto.set("productName", opi.getProductName());
				dto.set("productPrice", formatPriceStr(opi.getProductPrice(),2));
				dto.set("remark", opi.getRemark());
				String dictPriceType = opi.getDictPriceType();
				String lType = "";
				try
				{
					if(dictPriceType!=null&&!"".equals(dictPriceType.trim()))
					lType = cts.getLabelById(dictPriceType);
				}
				catch(Exception e)
				{
					
				}
				dto.set("dictPriceType", lType);
				list.add(dto);
			}
		}
		return list;
	}
	
	
	/**
	 * ����Ļ���ÿ�չ���ScreenSadInfo����ļ��� 
	 * @return List
	 */
	public List getScreenSadInfo() {
		ScreenServiceHelp pish=new ScreenServiceHelp();
		Object[] aa = null;
		try
		{
			aa= dao.findEntity(pish.screenSadInfoQuery());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		List list=new ArrayList();
		if(aa!=null&&aa.length>0){
			for(int i = 0, size = aa.length; i < size; i++)
			{
				ScreenOperSadinfo oci = (ScreenOperSadinfo) aa[i];
				DynaBeanDTO dto = new DynaBeanDTO();
				dto.set("caseExpert", oci.getCaseExpert());
				dto.set("countUnit", oci.getCountUnit());
				dto.set("custName", oci.getCustName());
				dto.set("deployBegin", oci.getDeployBegin());
				dto.set("deployEnd", oci.getDeployEnd());
				dto.set("dictSadType", oci.getDictSadType());
				dto.set("priceUnit", oci.getPriceUnit());
				dto.set("productCount", oci.getProductCount());
				dto.set("productName", oci.getProductName());
				dto.set("productPrice", formatPriceStr(oci.getProductPrice(),2));
				dto.set("productScale", oci.getProductScale());
				dto.set("sadId", oci.getSadId());
				dto.set("sadRid", oci.getSadRid());
				dto.set("sadTime", oci.getSadTime());
				dto.set("custTel", oci.getCustTel());
				list.add(dto);
			}
		}
		return list;
	}
	/**
	 * ��ý�䰸���б� ��Ҫ case_content case_reply
	 * @return  List
	 * @author wangwenquan
	 */
	public List<DynaBeanDTO> getCaseInfoList()
	{
		List<DynaBeanDTO> caseInfoList = new ArrayList<DynaBeanDTO>(); 
		ScreenServiceHelp help = new ScreenServiceHelp();
		Object[] arrs = dao.findEntity(help.screenCaseInfoQuery());
		for(int i=0; i<arrs.length; i++)
		{
			OperCaseinfo info = (OperCaseinfo)arrs[i];
			DynaBeanDTO dto = new DynaBeanDTO();
			String content = info.getCaseContent();
			if(content == null || content.equalsIgnoreCase("null")){
				content = "";
			}
			String reply = info.getCaseReply();
			if(reply == null || reply.equalsIgnoreCase("null")){
				reply = "";
			}
					
			dto.set("caseContent", content);
			dto.set("caseReply", reply);
			caseInfoList.add(dto);
		}
		return caseInfoList;
	}
	
	/**
	 * @return dao
	 */
	public BaseDAO getDao() {
		return dao;
	}
	/**
	 * @param dao Ҫ���õ� dao
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * 12316��Ѷ����
	 * @param title
	 * @param content
	 */
	public void addQuickMessage(String title, String content)
	{
		ScreenQuickMessage quickMsg = new ScreenQuickMessage();
		quickMsg.setMsgTitle(title);
		quickMsg.setMsgContent(content);
		quickMsg.setCreateDate(new java.util.Date());
		dao.saveEntity(quickMsg);
	}
	/**
	 * 12316��Ѷ�б�
	 * @param title
	 * @param content
	 */
	public List<DynaBeanDTO> quickMessageList()
	{
		//screenQuickMessageQuery()
		List<DynaBeanDTO> infoList = new ArrayList<DynaBeanDTO>(); 
		ScreenServiceHelp help = new ScreenServiceHelp();
		Object[] arrs = dao.findEntity(help.screenQuickMessageQuery());
		for(int i=0; i<arrs.length; i++)
		{
			ScreenQuickMessage info = (ScreenQuickMessage)arrs[i];
			DynaBeanDTO dto = new DynaBeanDTO();
			dto.set("msgTitle", info.getMsgTitle());
			dto.set("msgContent", info.getMsgContent());
			infoList.add(dto);
		}
		return infoList;
	}
	/**
	 * ����ʵ��ͳ��
	 * 
	 */
	public List<DynaBeanDTO> huaWuList()
	{
		List<DynaBeanDTO> infoList = new ArrayList<DynaBeanDTO>(); 
		ScreenServiceHelp help = new ScreenServiceHelp();
		Object[] arrs = dao.findEntity(help.huaWuQuery());
		for(int i=0; i<arrs.length; i++)
		{
			OperQuestion info = (OperQuestion)arrs[i];
			DynaBeanDTO dto = new DynaBeanDTO();
			//����ʱ�� �û����� �û���Ϣ ��ѯ��Ŀ ��ѯ���� ���ߴ� ���״̬��ʾ
			dto.set("addtime", TimeUtil.getTheTimeStr(info.getAddtime(), "yyyy-MM-dd hh:mm:ss"));
			dto.set("userName", info.getOperCustinfo().getCustName());
			dto.set("userInfo", info.getOperCustinfo().getCustAddr());
			dto.set("questonType1", info.getDictQuestionType1());
			dto.set("questionContent", info.getQuestionContent());
			dto.set("answerContent", info.getAnswerContent());
			String successId = info.getDictIsAnswerSucceed();
			String successInfo = "";
			if(successId != null && "".equals(successId.trim()))
			{
				try
				{
					successInfo = cts.getLabelById(successId);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}//SYS_TREE_0000000725 
			String answerSuccceed = "";
			String dictIsAnswer = info.getDictIsAnswerSucceed();
			if(dictIsAnswer!=null&&!"".equals(dictIsAnswer.trim()))
			{
				try
				{
					answerSuccceed = cts.getLabelById(dictIsAnswer);
				}
				catch(Exception e)
				{
					
				}
			}
			dto.set("answerSucceed", answerSuccceed);
			infoList.add(dto);
		}
		return infoList;
	}
	/**
	 * ����Ļ��ý����ע��Ϣ�б�
	 * @return List
	 */
	public List<DynaBeanDTO> jiaoDianAnliList()
	{
		List<DynaBeanDTO> infoList = new ArrayList<DynaBeanDTO>(); 
		ScreenServiceHelp help = new ScreenServiceHelp();
		RowSet set = dao.getRowSetByJDBCsql(help.jiaoDianQuery());
		try {
			set.beforeFirst();
			while (set.next()) {
				DynaBeanDTO dto = new DynaBeanDTO();
				Clob clob = set.getClob(1);
				int length = 0;
				if(clob != null)
				{
					length = (int)clob.length();
				}
				if(length>0)
				{
					dto.set("msgContent", set.getClob(1).getSubString(1, length));
				}
				else
				{
					dto.set("msgContent", "");
				}
				Clob replySu = set.getClob(2);
				int lengthRS = 0;
				if(replySu!=null)
				{
					lengthRS = (int)replySu.length();
				}
				if(lengthRS>0)
				{
					dto.set("msgReplyOrSummary", set.getClob(2).getSubString(1, lengthRS));
				}
				else
				{
					dto.set("msgReplyOrSummary", "");
				}
				infoList.add(dto);
		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ֻҪһ����¼
		if(infoList.size()>0)
		{
			List<DynaBeanDTO> oneList = new ArrayList<DynaBeanDTO>(); 
			oneList.add(infoList.get(0));
			return oneList;
		}
		return infoList;
	}
	/**
	 * ����Ļ�����Ѷ������Ϣ�б�
	 * @return List
	 */
	public DynaBeanDTO getZiXunSumDtl()
	{
		DynaBeanDTO dto = new DynaBeanDTO();
		ScreenServiceHelp h = new ScreenServiceHelp();
		RowSet set = dao.getRowSetByJDBCsql(h.zixunSum());
		try {
			set.beforeFirst();
			while (set.next()) {
				dto.set("zixunsum", set.getString("zixunsum"));
				dto.set("dayzixun", set.getString("dayzixun"));
				dto.set("shengchan", set.getString("shengchan"));
				dto.set("shichang", set.getString("shichang"));
				//##
				dto.set("zhengce", set.getString("zhengce"));
				dto.set("yiliao", set.getString("yiliao"));
				dto.set("other", set.getString("other"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}
	/**
	 * ��ʽ���۸�
	 * @param String priceStr
	 * @param int decimalNum С�����λ
	 * @return formatStr
	 */
	public String formatPriceStr(String priceStr, int decimalNum)
	{
		String rStr;
		if(priceStr!=null&&!"".equals(priceStr.trim()))
		{
			int dot = priceStr.indexOf(".");
			if(dot == -1)
			{
				rStr = priceStr + "." + this.getNZeroStr(decimalNum);
			}
			else
			{
				int length = priceStr.length();
				int num = length - dot - 1;
				if(num<decimalNum)
				{
					rStr = priceStr + this.getNZeroStr(decimalNum-num);
				}
				else
				{
					rStr = priceStr.substring(0,dot+decimalNum+1);
				}
			}
			return rStr;
		}
		return "."+getNZeroStr(decimalNum);
	}
	/**
	 * �õ�����ר��
	 * @return List<OperCustinfo>
	 */
	public List<OperCustinfo> getAllExperts()
	{
		setDefault2Experts();
		ScreenServiceHelp h = new ScreenServiceHelp();
		List<OperCustinfo> l = new ArrayList<OperCustinfo>();
		MyQuery mq = h.allExpertsQuery();
		Object[] os = dao.findEntity(mq);
//		System.out.println();
		if(os!=null)
		{
			for(int i=0; i<os.length; i++)
			{
				l.add((OperCustinfo)os[i]);
			}
		}
	
		return l;
	}
	/**
	 * �õ�����ר��
	 * @return List<OperCustinfo>
	 */
	public List<OperCustinfo> getAllExpertsByType(String type)
	{
		//setDefault2Experts();
		ScreenServiceHelp h = new ScreenServiceHelp();
		List<OperCustinfo> l = new ArrayList<OperCustinfo>();
		MyQuery mq = h.allExpertsQuery(type);
		Object[] os = dao.findEntity(mq);
		System.out.println();
		if(os!=null)
		{
			for(int i=0; i<os.length; i++)
			{
				l.add((OperCustinfo)os[i]);
			}
		}
	
		return l;
	}
	/**
	 * �õ����л���ר��
	 * @param type ����
	 * @return List<OperCustinfo>
	 */
	public List<OperCustinfo> getAllMutexExpertsByType(String type)
	{
		ScreenServiceHelp h = new ScreenServiceHelp();
		List<OperCustinfo> l = new ArrayList<OperCustinfo>();
		MyQuery mq = h.allExpertsMutexQuery(type);
		Object[] os = dao.findEntity(mq);
		System.out.println();
		if(os!=null)
		{
			for(int i=0; i<os.length; i++)
			{
				l.add((OperCustinfo)os[i]);
			}
		}
	
		return l;
	}
	/**
	 * ����ר������
	 * @param type ����
	 * @param ids ids
	 * @return List<OperCustinfo>
	 */
	public void updateScreenExpertType(String type, String ids)
	{
		String sql = "update oper_custinfo set screen_expert_type = '"+type+"' where cust_id in("+ids+")";
		System.out.println(sql);
		dao.execute(sql);
	}
	//ȡ����ר��ʱ�Ƚ�����ΪĬ��ֵ
	private void setDefault2Experts()
	{
		String sql = "update oper_custinfo  set screen_expert_type = '0' where dict_cust_type = 'SYS_TREE_0000002103' ";
		dao.execute(sql);
	}
	private String getNZeroStr(int index)
	{
		String str = "";
		if(index > 0)
		{
			for(int i=0; i<index; i++)
			{
				str = str +"0";
			}
		}
		return str;
	}
	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}
	
	public void createXml(){
		try {
			XmlBuild xb = new XmlBuild();
			List list = new ArrayList();
			//��ʾ���һ���µ��ĸ��������绰�Ĵ����������������������ʾ����������
			String sql = "select column_name,count(dict_question_type1) from refer_column left join oper_question on column_name=dict_question_type1 " 
						+" and addtime between dateadd(day,-30,getdate()) and getdate() "+
						 "group by column_name order by column_name asc";
			RowSet rs = dao.getRowSetByJDBCsql(sql);
			while(rs.next()){
				String mydate = rs.getString(1);
				String mycount = rs.getString(2);
				ArrayList al = new ArrayList();
				al.add(mydate);
				al.add(mycount);
				list.add(al);
			}
			xb.modifyXML("CallLogStatisMonth2D",list,"12316�����������һ�¸���ѯ��Ŀ�ֲ�ͳ��","���","��������",16);
			
			XmlBuild xb2 = new XmlBuild();
			List list2 = new ArrayList();
			//��ʾ���ǵ�����ĸ��������绰�Ĵ����������������������ʾ����������
			String sql2 = "select column_name,count(dict_question_type1) from refer_column left join oper_question on column_name=dict_question_type1 " 
				+" and addtime between dateadd(hour,-24,getdate()) and getdate() "+
				 "group by column_name order by column_name asc";
			RowSet rs2 = dao.getRowSetByJDBCsql(sql2);
			while(rs2.next()){
				String mydate = rs2.getString(1);
				mydate = mydate.substring(mydate.indexOf("-")+1);
				String mycount = rs2.getString(2);
				ArrayList al = new ArrayList();
				al.add(mydate);
				al.add(mycount);
				list2.add(al);
			}
			xb2.modifyXML("CallLogStatisDayPie3D",list2,"12316�����������һ�ո���ѯ��Ŀ�ֲ�ͳ��","���","��������",16);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ��ʾ���һ���µ��ĸ��������绰�Ĵ����������������������ʾ����������
	 * @return
	 */
	/*public List getCallLogStatisByMonth(){
		try {
			List list = new ArrayList();
			String sql = "select dict_question_type1 mytype, count(*) mycount from oper_question where addtime between dateadd(day,-30,getdate()) and getdate() group by dict_question_type1 order by dict_question_type1 desc";
			RowSet rs = dao.getRowSetByJDBCsql(sql);
			while(rs.next()){
				String mytype = rs.getString("mytype");
				String mycount = rs.getString("mycount");
				if(mytype!=null){
//					System.out.println("type :["+mytype+"] ; count : ["+mycount+"]");
					ArrayList al = new ArrayList();
					if(mytype.length()<1){
//						al.add("δѡ��");
						continue;
					}
					else
						al.add(mytype);						
					
					al.add(mycount);
					list.add(al);	
				}				
			}			
			return list;			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}		
	}*/
	
	/**
	 * ��ʾ���ǵ�����ĸ��������绰�Ĵ����������������������ʾ����������
	 * @return
	 */	
	/*public List getCallLogStatisByDay(){		
		try {
			List list = new ArrayList();
//			String sql = "select dict_question_type1 mytype, count(*) mycount from oper_question where addtime between dateadd(hour,-24,getdate()) and getdate() group by dict_question_type1 order by dict_question_type1 desc";
//			����
			String sql = "select dict_question_type1 mytype, count(*) mycount from oper_question where addtime between dateadd(hour,-24,'2009-3-12 11:30:04') and '2009-3-12 11:30:04' group by dict_question_type1 order by dict_question_type1 desc";
			RowSet rs = dao.getRowSetByJDBCsql(sql);
			while(rs.next()){
				String mytype = rs.getString("mytype");
				String mycount = rs.getString("mycount");
				if(mytype!=null){
//					System.out.println("type :["+mytype+"] ; count : ["+mycount+"]");
					ArrayList al = new ArrayList();
					if(mytype.length()<1){
//						al.add("δѡ��");	
						continue;
					}
					else
						al.add(mytype);						
					
					al.add(mycount);
					list.add(al);	
				}				
			}			
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}*/
	
}
