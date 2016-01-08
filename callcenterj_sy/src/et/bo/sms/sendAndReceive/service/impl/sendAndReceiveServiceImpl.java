package et.bo.sms.sendAndReceive.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import et.bo.flow.service.FlowService;
import et.bo.sms.ModermSendServiceHelp;
import et.bo.sms.sendAndReceive.service.sendAndReceiveService;
import et.po.HandsetNoteInfoAlready;
import et.po.HandsetNoteInfoNot;
import et.po.HandsetNoteInfoReceive;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class sendAndReceiveServiceImpl implements sendAndReceiveService {
	private BaseDAO dao = null;
	private KeyService ks = null;
	private FlowService flowService = null;
	private int numS= 0;
	private int num1= 0;
	private int ReceivetNum = 0;	
	public static HashMap hashmap = new HashMap();
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	} 
	public IBaseDTO getSendInfo(String id) {
	 // TODO Auto-generated method stub
	 HandsetNoteInfoAlready hsnia = (HandsetNoteInfoAlready)dao.loadEntity(HandsetNoteInfoAlready.class, id);
	 IBaseDTO dto = new DynaBeanDTO();
	 dto.set("id", id);
	 dto.set("receiveNum", hsnia.getReceiveNum());
	 dto.set("content", hsnia.getHandsetNoteInfo().getContent());
	 dto.set("operCount", hsnia.getOperCount());
	 dto.set("operTime", TimeUtil.getTheTimeStr(hsnia.getOperTime(), "yyyy-MM-dd hh:mm") ); 
	 dto.set("schedularTime", TimeUtil.getTheTimeStr(hsnia.getSchedularTime(), "yyyy-MM-dd hh:mm") ); 
	 dto.set("management", hsnia.getManagement());
	 dto.set("businessId", hsnia.getBusinessId());
	 dto.set("delSign", hsnia.getDelSign());
	 dto.set("operCount", hsnia.getOperCount()); 
	 dto.set("remark", hsnia.getRemark()); 
	 return dto;
	 }
	public int getSendSize() {
		// TODO Auto-generated method stub
		return numS;
	}
	public List sendQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub			
		List list = new ArrayList();
		sendAndReceiveHelp sarh = new sendAndReceiveHelp();	
		Object[] result = (Object[]) dao.findEntity(sarh.sendQuery(dto, pi));
		numS = dao.findEntitySize(sarh.sendQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			HandsetNoteInfoAlready hsnia = (HandsetNoteInfoAlready) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();	
			dbd.set("id", hsnia.getId());					
			dbd.set("receiveNum", hsnia.getReceiveNum()); 
			dbd.set("operCount", hsnia.getOperCount());
			dbd.set("operTime", TimeUtil.getTheTimeStr(hsnia.getOperTime(), "yyyy-MM-dd hh:mm")); 
			String handSetInfo = hsnia.getHandsetNoteInfo().getContent();
			if (handSetInfo.length()>=15) {
				handSetInfo = handSetInfo.substring(0,15)+"...";
			}
			dbd.set("content", handSetInfo);
			if(!hsnia.getSchedularTime().equals(""))
			{
				dbd.set("schedularTime", TimeUtil.getTheTimeStr(hsnia.getSchedularTime(), "yyyy-MM-dd hh:mm") ); 
			}
			
			if(hsnia.getManagement()!=null)
			{
				if(hsnia.getManagement().equals("Mobile"))
				{
					dbd.set("management", "中国移动"); 
				}
				else
				{
					dbd.set("management", "中国联通"); 
				}
			}
			dbd.set("businessId", hsnia.getBusinessId());
			dbd.set("delSign", hsnia.getDelSign()); 
			dbd.set("remark", hsnia.getRemark());
			list.add(dbd);
		}
		return list;
	}
	 	public IBaseDTO getSendNotInfo(String id) {
		 // TODO Auto-generated method stub
		HandsetNoteInfoNot hsnin = (HandsetNoteInfoNot)dao.loadEntity(HandsetNoteInfoNot.class, id);
		 IBaseDTO dto = new DynaBeanDTO();
		 dto.set("id", id);
		 dto.set("receiveNum", hsnin.getReceiveNum());
		 dto.set("operCount", hsnin.getOperCount());
		 dto.set("schedularTime", hsnin.getSchedularTime());
		 dto.set("management", hsnin.getManagement());
		 dto.set("businessId", hsnin.getBusinessId());
		 dto.set("content", hsnin.getHandsetNoteInfo().getContent());
		 dto.set("delSign", hsnin.getDelSign());
		 dto.set("sendState", hsnin.getSendState());
		 dto.set("remark", hsnin.getRemark());	 
		 return dto;
		 }
		public int getSendNotSize() {
			// TODO Auto-generated method stub
			return num1;
		}		
		public int getReceivetSize()
		{
			// TODO Auto-generated method stub
			return ReceivetNum;
		}
		public List sendNotQuery(IBaseDTO dto, PageInfo pi) {
			// TODO Auto-generated method stub			
			List list = new ArrayList();
			sendAndReceiveHelp sarh = new sendAndReceiveHelp();	
			Object[] result = (Object[]) dao.findEntity(sarh.sendNotQuery(dto, pi));
			num1 = dao.findEntitySize(sarh.sendNotQuery(dto, pi));					
			if(result.length>0)
			{
				for (int i = 0, size = result.length; i < size; i++) {
					HandsetNoteInfoNot hsnin = (HandsetNoteInfoNot) result[i];
					DynaBeanDTO dbd = new DynaBeanDTO();						
					dbd.set("id", hsnin.getId());					
					dbd.set("receiveNum", hsnin.getReceiveNum()); 
					dbd.set("operCount", hsnin.getOperCount()); 	
					String handSetInfo = hsnin.getHandsetNoteInfo().getContent();
					if (handSetInfo.length()>=15) {
						handSetInfo = handSetInfo.substring(0,15)+"...";
					}
					dbd.set("content", handSetInfo);
					if(hsnin.getSchedularTime()!=null)
					{
						dbd.set("schedularTime", TimeUtil.getTheTimeStr(hsnin.getSchedularTime(), "yyyy-MM-dd hh:mm") ); 
					}								
					if(hsnin.getManagement()!=null)
					{
						if(hsnin.getManagement().equals("Mobile"))
						{
							dbd.set("management", "中国移动"); 
						}
						else
						{
							dbd.set("management", "中国联通"); 
						}
					}					
					dbd.set("businessId", hsnin.getBusinessId());
					dbd.set("delSign", hsnin.getDelSign()); 
					dbd.set("sendState", hsnin.getSendState()); 
					dbd.set("remark", hsnin.getRemark()); 
	
					list.add(dbd);
				}
			}
			return list;
		}
		public void delSendNot(String id,String type)
		{
			HandsetNoteInfoNot hsnin = (HandsetNoteInfoNot)dao.loadEntity(HandsetNoteInfoNot.class, id);
			if(type.equals("delete"))//根据类型判断如果type为delete就为删除,否则将永久删除
			{
				hsnin.setDelSign("N");
				dao.updateEntity(hsnin);
			}
			else
			{
				dao.removeEntity(hsnin);
			}
		}
		public void delSend(String id,String type)
		{
			HandsetNoteInfoAlready hsnia = (HandsetNoteInfoAlready)dao.loadEntity(HandsetNoteInfoAlready.class, id);
			if(type.equals("delete"))//根据类型判断如果type为delete就为删除,否则将永久删除
			{
				hsnia.setDelSign("N");
				dao.updateEntity(hsnia);
			}
			else
			{
				dao.removeEntity(hsnia);
			}
		}
//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊收信息开始＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
		public List receiveSizeQuery()
		{
			ArrayList al = new ArrayList();
			ModermSendServiceHelp mssh = new ModermSendServiceHelp();	
			String NoReadNum = mssh.readAllMessageSize(0);	
			if(NoReadNum.equals("error"))
			{
				al.add("0");
			}
			else
			{
				al.add(NoReadNum);//未读信息条数
			}
			return al;	
		}
		public List receiveQuery(IBaseDTO dto, PageInfo pi,String number)
		{
			List al = new ArrayList();
			List all = new ArrayList();
			String str = null;
			ModermSendServiceHelp mssh = new ModermSendServiceHelp();				
			al = mssh.readAllMessage(0);
			ReceivetNum = al.size();//			
			if(al.size()>0)
			{
				for(int i = 0, size = al.size();i<size;i++)
				{
					str = (String)al.get(i);
					if(str.split(",").length>0)
					{
						String num[] = str.split(",");
						
							DynaBeanDTO dbd = new DynaBeanDTO();	
							HandsetNoteInfoReceive hsnir = new HandsetNoteInfoReceive();
							hsnir.setId(ks.getNext("handsetNoteInfoReceive"));
							hsnir.setSendNum(num[0]);
							hsnir.setContent(num[1]);
							hsnir.setSendTime(TimeUtil.getTimeByStr("20"+num[2], "yyyy-MM-dd hh:mm:ss"));							
							dao.saveEntity(hsnir);
							
							dbd.set("num",i+1);
							dbd.set("receiveNum",num[0]);
							
							if(!num[1].equals(""))
							{
								if(num[1].length()>15)
								{
									dbd.set("contents", num[1].substring(0, 15)+"......");
								}
								else
								{
									dbd.set("contents", num[1]);
								}
							}
							
							dbd.set("sendDateTime", num[2]);
							all.add(dbd);
						
					}
				}
			}
			return all;	
		}	
		public IBaseDTO receiveOneQuery(String number)
		{
			String str = null;
			List al = new ArrayList();
			ModermSendServiceHelp mssh = new ModermSendServiceHelp();
			IBaseDTO dto = null;
			al = mssh.readMessage(Integer.valueOf(number));
			if(al.size()>0)
			{
				for(int i = 0, size = al.size();i<size;i++)
				{
					str = (String)al.get(i);
					if(str.split(",").length>0)
					{
						String num[] = str.split(",");
						
						dto = new DynaBeanDTO();	
					
						dto.set("receiveNum", num[0]);							
						dto.set("contents", num[1]);
						dto.set("sendDateTime", num[2]);	
					}
				}
			}
			else
			{
//				System.out.println("无查询记录...............................");
			}

			return dto;	
		}
		public void delMessage(String id)
		{
			ModermSendServiceHelp mssh = new ModermSendServiceHelp();
			mssh.delMessage(Integer.valueOf(id));
		}
//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊收信息开始＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊		
		public KeyService getKs() {
			return ks;
		}
		public void setKs(KeyService ks) {
			this.ks = ks;
		}
}
