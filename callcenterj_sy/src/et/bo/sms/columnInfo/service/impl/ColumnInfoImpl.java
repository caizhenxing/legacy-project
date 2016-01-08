package et.bo.sms.columnInfo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import et.bo.sms.columnInfo.service.ColumnInfoService;
import et.po.ColumnInfo;
import et.po.ColumnInfoMessage;
import et.po.ColumnInfoSendtime;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * 
 * @author chen gang
 *
 */
public class ColumnInfoImpl implements ColumnInfoService {
	static Logger log = Logger.getLogger(ColumnInfoImpl.class.getName());
	
	private BaseDAO dao = null;
	
	private int COLUMN_INFO_COUNT;
	
	private ClassTreeService cts = null;
	
	private KeyService ks = null;
	
	private int num = 0;

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

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * 添加业务定制信息记录
	 * @param dto
	 * @return
	 */
	public boolean addColumnInfo(IBaseDTO dto) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = sdf.format(TimeUtil.getNowTime());
//		String nowDate = sdf2.format(TimeUtil.getNowTime());
		
		String orderType = dto.get("orderType").toString();
		String mobileNum = dto.get("mobileNum").toString();
//		String beginDate = dto.get("beginDate").toString();
//		String beginTime = dto.get("beginTime").toString();
		String colInfo = dto.get("columnInfo").toString();
		
		ColumnInfo om = new ColumnInfo();
		ColumnInfoHelp omh = new ColumnInfoHelp();
		
		if("orderProgramme".equals(orderType)) {  // 点播
//			if("".equals(beginTime)){
				om.setId(ks.getNext("COLUMN_INFO"));
				om.setOrdeType("orderProgramme");
				try {
					om.setMenuBegintime(sdf.parse(nowTime));
					om.setMenuEndtime(sdf.parse(nowTime));
//					om.setEgressiveTime(sdf.parse(nowTime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				om.setMobileNum(mobileNum);
//				om.setEgressiveFlag("Y");
				om.setColInfo(colInfo);
//				om.setCalloutnum(0);
				
				dao.saveEntity(om);
				return true;
//			} else{
//				om.setMenuType("orderProgramme");
//				try {
//					om.setMenuBegintime(sdf.parse(nowTime));
//					om.setMenuEndtime(sdf.parse(nowTime));
//					if("".equals(beginDate))
//						om.setEgressiveTime(sdf.parse(nowDate+" "+beginTime+":00"));
//					else
//						om.setEgressiveTime(sdf.parse(beginDate+" "+beginTime+":00"));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				om.setTelnum(telNum);
//				om.setEgressiveFlag("Y");
//				om.setIvrTreeInfoId(ivrInfo);
//				om.setCalloutnum(0);
//				dao.saveEntity(om);
//				return true;
//			}
		} else if("customize".equals(orderType)) {  // 定制
			Object[] result = dao.findEntity(omh.getOrderRecord(mobileNum, colInfo));
			if(result != null && result.length > 0) {
				return false;
			}
			om.setId(ks.getNext("COLUMN_INFO"));
			om.setOrdeType("customize");
			try {
				om.setMenuBegintime(sdf.parse(nowTime));
//				om.setMenuEndtime(sdf.parse(nowTime));
//				om.setEgressiveTime(sdf.parse(nowDate+" "+beginTime+":00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			om.setMobileNum(mobileNum);
//			om.setEgressiveFlag("Y");
			om.setColInfo(colInfo);
//			om.setCalloutnum(0);
			dao.saveEntity(om);
			return true;
		} 
		
		return false;
	}
	
	
	/**
	 * 查询定制后未退订的信息
	 */
	public List columnInfoSearch(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		
		ColumnInfo ci = new ColumnInfo();
		ColumnInfoHelp omh = new ColumnInfoHelp();
		
		Object[] result = dao.findEntity(omh.getCustomizeRecord(dto, pi));
		this.COLUMN_INFO_COUNT = dao.findEntitySize(omh.getCustomizeRecord(dto,pi)); 
			//dao.findEntitySize(omh.getCustomizeRecord(telNum, pi));
		
		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				ci = (ColumnInfo)result[i];
				list.add(ColumnInfoToDynaBeanDTO(ci));
			}
		}
		
		return list;
	}
	
	private DynaBeanDTO ColumnInfoToDynaBeanDTO(
			ColumnInfo pf) {
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", pf.getId());
		dbd.set("orderType", pf.getOrdeType());
		dbd.set("mobileNum", pf.getMobileNum());
		String time;
		String time1 = "";
		try {
			time = pf.getMenuBegintime().toString();
			if(time!=null&&time.length()>10){
				time=time.substring(0, 10);
			}
			dbd.set("beginTime",time);
		} catch (RuntimeException e) {
			e.printStackTrace();
			dbd.set("beginTime","");
		}try {
			if(pf.getMenuEndtime() != null)
				time1 = pf.getMenuEndtime().toString();
			if(time1!=null&&time1.length()>10){
				time1=time1.substring(0, 10);
			}
			dbd.set("endTime", time1);
		} catch (RuntimeException e) {
			e.printStackTrace();
			dbd.set("endTime", "");
		}
		
		
		
		String colInfo = pf.getColInfo();
		String colLabel = cts.getLabelByNickName(colInfo);
		
		dbd.set("columnInfo", colLabel);

		return dbd;
	}
	
	/**
	 * 退订操作
	 * @param id
	 * @return
	 */
	public boolean cancelColInfo(String id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(TimeUtil.getNowTime());
		
		ColumnInfoHelp omh = new ColumnInfoHelp();
		Object[] result = dao.findEntity(omh.getOrderMenuById(id));
		if(result != null && result.length > 0) {
			ColumnInfo om = (ColumnInfo)result[0];
			try {
				om.setMenuEndtime(sdf.parse(nowTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dao.updateEntity(om);
			return true;
		}
		return false;
	}
	
	public int getColumnInfoSize() {
		return this.COLUMN_INFO_COUNT;
	}
	
	/**
	 * 根据columnInfo查询发送时间表的记录
	 * @param nickname
	 * @return
	 */
	public ColumnInfoSendtime getCis(String nickname) {
		ColumnInfoSendtime cis = null;
		ColumnInfoHelp omh = new ColumnInfoHelp();
		
		Object[] result = dao.findEntity(omh.getColumnInfoTime(nickname));
		if(result != null && result.length > 0) {
			cis = (ColumnInfoSendtime)result[0];
		}
		return cis;
	}
	
	public boolean addColumnInfoSendtime(String nickname, String sendTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDate = sdf.format(TimeUtil.getNowTime());
		ColumnInfoHelp omh = new ColumnInfoHelp();
		ColumnInfoSendtime cis = null;
		Object[] result = dao.findEntity(omh.getColumnInfoTime(nickname));
		if(result != null && result.length > 0) {
			cis = (ColumnInfoSendtime)result[0];
			try {
				cis.setColumnSendtime(sdf2.parse(nowDate+" "+sendTime+":00"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.updateEntity(cis);
			return true;
			
		} else{
			cis = new ColumnInfoSendtime();
			cis.setId(ks.getNext("COLUMN_INFO_SENDTIME"));
			cis.setColumnInfo(nickname);
			try {
				cis.setColumnSendtime(sdf2.parse(nowDate+" "+sendTime+":00"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.saveEntity(cis);
			return true;
			
		}
	}

	public boolean addColumnInfoMessage(IBaseDTO dto) {
		try {
			dao.saveEntity(creatColumnInfoMessage(dto));
			return true;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return false;
		}
	}
	private ColumnInfoMessage creatColumnInfoMessage(IBaseDTO dto){
		ColumnInfoMessage cim=new ColumnInfoMessage();
		cim.setColumnInfo(dto.get("columnInfo").toString());
		cim.setMessageContent(dto.get("content").toString());
		cim.setMessageName(dto.get("columnName").toString());
		return cim;
	}

	public DynaBeanDTO colunmInfoQuery(String id) {
		ColumnInfo ci=(ColumnInfo)dao.loadEntity(ColumnInfo.class, id);
		return ColumnInfoToDynaBeanDTO(ci);
	}

	public ColumnInfo loadColunmInfo(String id) {
		ColumnInfo c=(ColumnInfo)dao.loadEntity(ColumnInfo.class, id);
		return c;
	}

	public void updateColumnInfo(ColumnInfo ci) {
		dao.updateEntity(ci);
	}
	
	/**
	 * 查询信息维护内容
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List messagesQuery(IBaseDTO dto, PageInfo pi) {
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		ColumnInfoHelp cif = new ColumnInfoHelp();
		Object[] result = (Object[])dao.findEntity(cif.messageQuery(dto, pi));
		if(result != null && result.length > 0) {
			num = dao.findEntitySize(cif.messageQuery(dto, pi));
			for(int i=0; i<result.length; i++) {
				ColumnInfoMessage cim = (ColumnInfoMessage)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("id", cim.getId());
				dbd.set("columnName", cim.getMessageName());
				dbd.set("columnInfo", cim.getColumnInfo());
				list.add(dbd);
			}
		}
		return list;
	}

	public DynaBeanDTO getMessagesInfo(String id) {
		DynaBeanDTO dbd = new DynaBeanDTO();
		ColumnInfoHelp cif = new ColumnInfoHelp();
		Object[] result = (Object[])dao.findEntity(cif.getMessageInfo(id));
		if(result != null && result.length > 0) {
			ColumnInfoMessage cim = (ColumnInfoMessage)result[0];
			
			dbd.set("id", cim.getId());
			dbd.set("messageName", cim.getMessageName());
			dbd.set("columnInfo", cim.getColumnInfo());
			dbd.set("content", cim.getMessageContent());
		}
		return dbd;
	}

	public int getMessagesSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public boolean addMessage(IBaseDTO dto) {
		try {
			ColumnInfoMessage cim = new ColumnInfoMessage();
			cim.setColumnInfo((String)dto.get("columnInfo"));
			cim.setMessageName(dto.get("messageName").toString());
			cim.setMessageContent(dto.get("content").toString());
			dao.saveEntity(cim);
			return true;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	public boolean deleteMessage(String messageId) {
		try {
			ColumnInfoHelp cif = new ColumnInfoHelp();
			Object[] result = (Object[])dao.findEntity(cif.getMessageInfo(messageId));
			if(result != null && result.length > 0) {
				ColumnInfoMessage cim = (ColumnInfoMessage)result[0];
				dao.removeEntity(cim);
				return true;
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean updateMessage(IBaseDTO dto) {
		try {
			ColumnInfoHelp cif = new ColumnInfoHelp();
			Object[] result = (Object[])dao.findEntity(cif.getMessageInfo(dto.get("id").toString()));
			if(result != null && result.length > 0) {
				ColumnInfoMessage cim = (ColumnInfoMessage)result[0];
				
				cim.setColumnInfo((String)dto.get("columnInfo"));
				cim.setMessageName(dto.get("messageName").toString());
				cim.setMessageContent(dto.get("content").toString());
				
				dao.updateEntity(cim);
				return true;
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
