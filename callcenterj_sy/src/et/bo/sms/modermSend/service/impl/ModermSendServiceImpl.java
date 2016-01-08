/**
 * 沈阳卓越科技有限公司
 * 2008-4-22
 */
package et.bo.sms.modermSend.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;


import et.bo.callcenter.callOutSet.service.impl.OrderMenuHelp;
import et.bo.custinfo.service.impl.CustinfoHelp;
import et.bo.sms.ModermSendServiceHelp;
import et.bo.sms.modermSend.service.SmsSendNewService;
import et.po.HandsetNoteInfo;
import et.po.HandsetNoteInfoAlready;
import et.po.HandsetNoteInfoNot;
import et.po.OperCustinfo;
import et.po.OperSadinfo;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * 实现类
 * 负责在短信收发的记录工作(主要是记录数据库)
 * 具体的通过短信猫收发工作交由ModermSendServiceHelp类来完成
 * @author 荆玉琢
 * @version 1.0
 *
 */
public class ModermSendServiceImpl implements SmsSendNewService{
	
	
	private KeyService ks = null;
	private BaseDAO dao = null;
	private ClassTreeService cts = null;
	
//	短信群发
	public void sendToGroup(SMSContent smscontent) {
		// TODO Auto-generated method stub
		String id = null;//ID变量
		 Date da = TimeUtil.getNowTime();
		 String groupNum = smscontent.getReceiveNum();//定义接受群号码
		 String sendReg = null;//接受验证是否发送成功字符串
		 ModermSendServiceHelp mssh = new ModermSendServiceHelp();//调用Help类的发送功能接口
		 Map msgGroupMap = new HashMap(); 
		 String content = smscontent.getContent();//发送内容
		 String sendState = smscontent.getSendState();
		if(!"draft".equals(sendState) && !"time".equals(sendState))
		{															//判断只有不为定时状态与草稿状态或者是无状态时候才可以执行
			 msgGroupMap = mssh.sendmsgGroup(content,groupNum);	//调用Help类的发送功能接口 以返回的值来判断短信是否发送成功
		}
		else
		{
			String str[] = groupNum.split(",");
			for(int i = 0,size = str.length;i<size;i++)
			{
				id = ks.getNext("handsetNoteInfo");
				
		        smscontent.setReceiveNum(str[i]);
		        dao.saveEntity(createHandsetNoteInfo(smscontent,id,da));
				
		        dao.saveEntity(createHandsetNoteInfoNot(smscontent,id,da));
			}
		}
		 
		 Set set=msgGroupMap.entrySet();
	     Iterator iterator=set.iterator();
	        while(iterator.hasNext())
	        {
	        	id = ks.getNext("handsetNoteInfo");
		        Map.Entry mapentry=(Map.Entry)iterator.next();   
		        String num = (String)mapentry.getKey();
		        sendReg = (String)mapentry.getValue();
		        smscontent.setReceiveNum(num);
		        dao.saveEntity(createHandsetNoteInfo(smscontent,id,da));
		        if(!sendState.equals("time") && !sendState.equals("draft"))
		        {
			  		if(sendReg.equals("succee"))
					{
						dao.saveEntity(createHandsetNoteInfoAlready(smscontent,id,da));	
					}
			  		else if(sendReg.equals("error"))
			  		{
			  			smscontent.setSendState("error");
			  			dao.saveEntity(createHandsetNoteInfoNot(smscontent,id,da));
			  		}
		        }
//		        else if(sendState.equals("time") || sendState.equals("draft"))
//		        {
//		        	dao.saveEntity(createHandsetNoteInfoNot(smscontent,id,da));
//		        }
	        }
	}
	
//短信单发
	public void sendToOne(SMSContent smscontent) {
		// TODO Auto-generated method stub
	
		/*
		 * 在数据库中记录此次的发送信息、发送结果等
		 */
		String id = null;
		String sendReg = null;//信息是否发送成功
		 Date da = TimeUtil.getNowTime();
		 String sendState = smscontent.getSendState();
		 ModermSendServiceHelp mssh = new ModermSendServiceHelp();//调用Help类的发送功能接口
		 String content = smscontent.getContent();//发送内容
		 String receiveNum = smscontent.getReceiveNum();//接收号码
		 
		if(!sendState.equals("draft")&&!sendState.equals("time"))//判断只有不为定时状态与草稿状态或者是无状态时候才可以执行
			sendReg = mssh.sendmsg(content,receiveNum);//调用Help类的发送功能接口 以返回的值来判断短信是否发送成功
		else
			sendReg = "error";
		
		id = ks.getNext("handsetNoteInfo");
		HandsetNoteInfo info = createHandsetNoteInfo(smscontent,id,da); 
		dao.saveEntity(info);

		 if(!sendState.equals("time") && !sendState.equals("draft"))
	        {
				if(sendReg.equals("succee"))
				{
					dao.saveEntity(createHandsetNoteInfoAlready(smscontent,id,da));    
				}
				else if(sendReg.equals("error"))
				{
					smscontent.setSendState("error");//设置发送未成功
					dao.saveEntity(createHandsetNoteInfoNot(smscontent,id,da));
				}
	        }
		  else if(sendState.equals("time") || sendState.equals("draft"))
		   {
			 	dao.saveEntity(createHandsetNoteInfoNot(smscontent,id,da));
		   }
		
	}
	
	private HandsetNoteInfo createHandsetNoteInfo(SMSContent smscontent, String id, Date da)
	{
		 HandsetNoteInfo hsni = new HandsetNoteInfo();//短信平台发送信息表实例化表对象
		 String content = smscontent.getContent();//发送内容
		 String sendNum = smscontent.getSendNum();//发送人号码		
		 String sendManId = smscontent.getSendManId();//发送人ID

		 hsni.setId(id);
		 hsni.setSendNum(sendNum);
		 hsni.setContent(content);
		 hsni.setSendPerson(sendManId);
		 hsni.setOperTime(da);
		 hsni.setDelSign("Y");
//		hsni.setRemark(dto.get("remark").toString());
//		将Bean里面的值set进表的实例化对象里面
		return hsni;
	}
	
	private HandsetNoteInfoAlready createHandsetNoteInfoAlready( SMSContent smscontent, String id, Date da)
	{
		 HandsetNoteInfoAlready hsnia = new HandsetNoteInfoAlready();//短信平台已发信息表实例化表对象
		 String operCount = smscontent.getOperCount();//发送次数
		 String receiveNum = smscontent.getReceiveNum();//接收号码
		 String operationId = smscontent.getOperationId();//业务编号
		 String managementId = smscontent.getManagementId();//运营商ID
		 String management = null;
		 HandsetNoteInfo hsniId = (HandsetNoteInfo)dao.loadEntity(HandsetNoteInfo.class, id);
		
//		 System.out.println(receiveNum +" 看看接收号码");
		 
		hsnia.setId(ks.getNext("handsetNoteInfoAlready"));
		hsnia.setSchedularTime(da);
		hsnia.setOperTime(TimeUtil.getNowTime());
		hsnia.setOperCount(operCount);
		hsnia.setReceiveNum(receiveNum);
		hsnia.setBusinessId(operationId);		
		hsnia.setHandsetNoteInfo(hsniId);
		
		management = receiveNum.trim().substring(0,3);
		
		 System.out.println(management+"看看接收号码");
		
		if (management.equals("130") || management.equals("131") || management.equals("132")
				|| management.equals("133") || management.equals("153") || management.equals("155")
				|| management.equals("156")) {
			managementId = "Unicom";
			hsnia.setManagement(managementId);
		}
		else
		{
			managementId = "Mobile";
			hsnia.setManagement(managementId);
		}

		hsnia.setDelSign("Y");
//		hsnia.setRemark(dto.get("remark").toString());
//		将Bean里面的值set进表的实例化对象里面
		return hsnia;
	}
	
	private HandsetNoteInfoNot createHandsetNoteInfoNot(SMSContent smscontent, String id, Date da)
	{
		 HandsetNoteInfoNot hsnin = new HandsetNoteInfoNot();//短信平台未发信息表实例化表对象
		 String operCount = smscontent.getOperCount();//发送次数
		 String receiveNum = smscontent.getReceiveNum();//接收号码
		 String operationId = smscontent.getOperationId();//业务编号
		 String managementId = smscontent.getManagementId();//运营商ID
		 String sendState = smscontent.getSendState();
		 String management = null;
		 
		 
		hsnin.setId(ks.getNext("handsetNoteInfoNot"));
		HandsetNoteInfo hsniId = (HandsetNoteInfo)dao.loadEntity(HandsetNoteInfo.class, id);
		hsnin.setReceiveNum(receiveNum);
		hsnin.setOperCount(operCount);
		hsnin.setHandsetNoteInfo(hsniId);
//		hsnin.setSchedularTime(TimeUtil.getTimeByStr(dto.get("SchedularTime").toString(), "yyyy-MM-dd"));
		hsnin.setOperTime(TimeUtil.getNowTime());
		management = receiveNum.substring(0, 3);
		
		if (management.equals("130") || management.equals("131") || management.equals("132")
				|| management.equals("133") || management.equals("153") || management.equals("155")
				|| management.equals("156")) {
			managementId = "Unicom";
			hsnin.setManagement(managementId);
		}
		else
		{
			managementId = "Mobile";
			hsnin.setManagement(managementId);
		}
		
		
		
		hsnin.setManagement(managementId);
		hsnin.setBusinessId(operationId);
		
		if(sendState.equals("draft"))
			hsnin.setSendState("2");//这里2为草稿中状态
		else if(sendState.equals("time"))
			hsnin.setSendState("3");//这里3为定时中状态
		else if(smscontent.equals("error"))
			hsnin.setSendState("4");//这里4为发送失败状态
		else
			hsnin.setSendState("1");//这里1为发送中状态
		hsnin.setDelSign("Y");
//		hsnin.setRemark(dto.get("remark").toString());
//		将Bean里面的值set进表的实例化对象里面
		
		return hsnin;
	}
	
	
	public boolean saveDraft(SMSContent smscontent, IBaseDTO dto) {
		// TODO Auto-generated method stub
		
		String groupNum  = smscontent.getReceiveNum();//得到群组号码的值
		String[] infoReceiver = groupNum.split(",");
		int receiverCount = infoReceiver.length;
		if(receiverCount>0)
		sendToGroup(smscontent);//调用群发接口
		else
		sendToOne(smscontent);//调用单发接口
		return false;
	}
	
	
	/*
	 * 暂时没有用到
	 * 
	 */
	public boolean saveSuccess(SMSContent smscontent) {
		// TODO Auto-generated method stub
		
		return false;
	}
	
	
	public List linkGroupQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	public List linkManQuery(String groupId) {
		// TODO Auto-generated method stub
		return null;
	}
	

	/**
	 * 查询数据列表,返回全部记录的list。
	 * 取得查询问题列表数据。
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * @return 数据的list
	 */
	public List custinfoAllQuery() {
		
		List list = new ArrayList();
		CustinfoHelp h = new CustinfoHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.allQuery());
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperCustinfo po = (OperCustinfo) result[i];
			list.add(custinfoToDynaBeanDTO(po));
		}
		return list;
	}
	
	/**
	 * 查询方法的 po 转 dto
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO custinfoToDynaBeanDTO(OperCustinfo po){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		
		dto.set("cust_id", po.getCustId());
		dto.set("cust_name", po.getCustName());
		dto.set("dict_sex", po.getDictSex());
		dto.set("cust_email", po.getCustEmail());
		dto.set("cust_addr", po.getCustAddr());
		dto.set("cust_pcode", po.getCustPcode());
		dto.set("cust_tel_home", po.getCustTelHome());
		dto.set("cust_tel_work", po.getCustTelWork());
		dto.set("cust_tel_mob", po.getCustTelMob());
		dto.set("cust_fax", po.getCustFax());
		dto.set("cust_voc", po.getDictCustVoc());
		dto.set("cust_scale", po.getDictCustScale());
		String cust_type = po.getDictCustType();
		if(cust_type != null && cust_type.length() > 15){
			cust_type = cts.getLabelById(cust_type);
		}
		dto.set("cust_type", cust_type);
		dto.set("remark", po.getRemark());

		return dto;
	}


	public String telByLinkMan(String custName)
	{
		String tel = null;
		String CustMob = null;
		MyQuery mq = telByLinkManQuery(custName,"OperCustinfo");
		Object[] UserResult = (Object[]) dao.findEntity(mq);
		if(UserResult.length>0)
		{
			for (int i = 0, size = UserResult.length; i < size; i++) {
				OperCustinfo oci = (OperCustinfo) UserResult[i];
				String custTelMob = oci.getCustTelMob();
				CustMob+= custTelMob+",";
			}
			tel = CustMob;
		}
		return tel;
	}
	private MyQuery telByLinkManQuery(String custName,String tableName){
		MyQuery mq=new MyQueryImpl();
		StringBuffer hql = new StringBuffer();			
		hql.append("select c from "+ tableName +" c where c.id = c.id");
		hql.append("  and c.id = '"+custName+"'");
		mq.setHql(hql.toString());
		return mq;
	}
	
	
	/**
	 * 得到用户list，label为用户姓名，value为用户电话.
	 * 电话首先取家庭电话，其次为办公电话，最后为手机
	 * @param userType the user type
	 * 
	 * @return the user list
	 */
	public List getUserList(String userType) {
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		OrderMenuHelp omh = new OrderMenuHelp();
		Object[] result = dao.findEntity(omh.getUserList(userType));
		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				OperCustinfo oci = (OperCustinfo)result[i];
				LabelValueBean lvb = new LabelValueBean();
				if(oci.getCustTelHome() != null && !"".equals(oci.getCustTelHome())){
					lvb.setLabel(oci.getCustName());
					lvb.setValue(oci.getCustTelHome());
					list.add(lvb);
				} else{
					if(oci.getCustTelWork() != null && !"".equals(oci.getCustTelWork())){
						lvb.setLabel(oci.getCustName());
						lvb.setValue(oci.getCustTelWork());
						list.add(lvb);
					} else{
						if(oci.getCustTelMob() != null && !"".equals(oci.getCustTelMob())){
							lvb.setLabel(oci.getCustName());
							lvb.setValue(oci.getCustTelMob());
							list.add(lvb);
						}
					}
				}
				
			}
		}
		return list;
	}
	
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao( BaseDAO dao) {
		this.dao = dao;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

}
