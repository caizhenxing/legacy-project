package et.bo.callcenter.orderMenu.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import et.bo.callcenter.orderMenu.service.OrderMenuService;
import et.po.OrderMenu;
import excellence.common.classtree.ClassTreeService;
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
public class OrderMenuImpl implements OrderMenuService {
	static Logger log = Logger.getLogger(OrderMenuImpl.class.getName());
	
	private BaseDAO dao = null;
	
	private int ORDER_MENU_COUNT;
	
	private ClassTreeService cts = null;

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
	public boolean addOrderMenu(IBaseDTO dto) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = sdf.format(TimeUtil.getNowTime());
		String nowDate = sdf2.format(TimeUtil.getNowTime());
		
		String orderType = dto.get("orderType").toString();
		String telNum = dto.get("telNum").toString();
		String beginDate = dto.get("beginDate").toString();
		String beginTime = dto.get("beginTime").toString();
		String ivrInfo = dto.get("ivrInfo").toString();
		
		OrderMenu om = new OrderMenu();
		OrderMenuHelp omh = new OrderMenuHelp();
		
		if("dianbo".equals(orderType)) {  // 点播
			if("".equals(beginTime)){
				om.setMenuType("orderProgramme");
				try {
					om.setMenuBegintime(sdf.parse(nowTime));
					om.setMenuEndtime(sdf.parse(nowTime));
					om.setEgressiveTime(sdf.parse(nowTime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				om.setTelnum(telNum);
				om.setEgressiveFlag("Y");
				om.setIvrTreeInfoId(ivrInfo);
//				om.setCalloutnum(0);
				dao.saveEntity(om);
				return true;
			} else{
				om.setMenuType("orderProgramme");
				try {
					om.setMenuBegintime(sdf.parse(nowTime));
					om.setMenuEndtime(sdf.parse(nowTime));
					if("".equals(beginDate))
						om.setEgressiveTime(sdf.parse(nowDate+" "+beginTime+":00"));
					else
						om.setEgressiveTime(sdf.parse(beginDate+" "+beginTime+":00"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				om.setTelnum(telNum);
				om.setEgressiveFlag("Y");
				om.setIvrTreeInfoId(ivrInfo);
//				om.setCalloutnum(0);
				dao.saveEntity(om);
				return true;
			}
		} else if("dingzhi".equals(orderType)) {  // 定制
			if(telNum!=null||!"".equals(telNum)){
				if(ivrInfo!=null||!"".equals(ivrInfo)){
					String sql = "select count(*) aa from order_menu where telnum='"+telNum
					+"' and ivr_tree_info_id='"+ivrInfo+"' and menu_endtime is null";
					RowSet rs = dao.getRowSetByJDBCsql(sql);
					try {
						while(rs.next()){
							int count = Integer.parseInt(rs.getString(1));
							System.out.println("count: "+ count);
							if(count>0) return false;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			
			Object[] result = dao.findEntity(omh.getOrderRecord(telNum, ivrInfo));
			if(result != null && result.length > 0) {
				return false;
			}
			om.setMenuType("customize");
			try {
				om.setMenuBegintime(sdf.parse(nowTime));
//				om.setMenuEndtime(sdf.parse(nowTime));
				om.setEgressiveTime(sdf.parse(nowDate+" "+beginTime+":00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			om.setTelnum(telNum);
			om.setEgressiveFlag("Y");
			om.setIvrTreeInfoId(ivrInfo);
//			om.setCalloutnum(0);
			dao.saveEntity(om);
			return true;
		} else if("tuiding".equals(orderType)) {  // 退订
			Object[] result = dao.findEntity(omh.getOrderRecord(telNum, ivrInfo));
			if(result != null && result.length > 0) {
				OrderMenu o = (OrderMenu)result[0];
				try {
					o.setMenuEndtime(sdf.parse(nowTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dao.updateEntity(o);
				return true;
			} else{
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * 查询定制后未退订的信息
	 */
	public List orderMenuSearch(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		
		OrderMenu om = new OrderMenu();
		OrderMenuHelp omh = new OrderMenuHelp();
		
		Object[] result = dao.findEntity(omh.getBusinessMenuRecord(dto, pi));//根据电话号码显示用户点播、定制的所有历史记录信息列表
		Object[] result2 = dao.findEntity(omh.getBusinessMenuRecordSize(dto));//根据电话号码显示用户点播、定制的所有历史记录信息列表
//		Object[] result = dao.findEntity(omh.getCustomizeRecord(telNum, pi));//查询定制后未退订的信息
//		Object[] result2 = dao.findEntity(omh.getCustomizeRecordSize(telNum));//查询定制后未退订的信息
		this.ORDER_MENU_COUNT = result2.length; 
			//dao.findEntitySize(omh.getCustomizeRecord(telNum, pi));
		
		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				om = (OrderMenu)result[i];
				list.add(OrderMenuToDynaBeanDTO(om));
			}
		}
		
		return list;
	}
	
	private DynaBeanDTO OrderMenuToDynaBeanDTO(
			OrderMenu pf) {
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", pf.getId().toString());
		if("orderProgramme".equals(pf.getMenuType())){
			dbd.set("orderType", "点播");
		}
		if("customize".equals(pf.getMenuType())){
			dbd.set("orderType", "订制");
		}
//		dbd.set("orderType", "dingzhi");
		dbd.set("telNum", pf.getTelnum());
		dbd.set("beginTime", pf.getMenuBegintime());
		Object obj = pf.getEgressiveTime();
		if(obj!=null){
			String playTime = obj.toString();
			playTime = playTime.substring(playTime.indexOf(" "));
			dbd.set("playTime", playTime);
		}
		String ivrInfo = pf.getIvrTreeInfoId();
		if("".equals(ivrInfo)) dbd.set("ivrInfo", "");
		else {
			String sql = "select label from base_tree where nickName='"+ivrInfo+"'";
			RowSet rs = dao.getRowSetByJDBCsql(sql);
			try {
				if(rs.next()){
					String lab = rs.getObject(1).toString();
						dbd.set("ivrInfo", lab.toString());
				}else dbd.set("ivrInfo", "老版栏目");
			} catch (Exception e) {
				// TODO: handle exception
			}
//				String ivrLabel = cts.getLabelByNickName(ivrInfo);//这个方法有问题，此处不能用这个方法。
//				dbd.set("ivrInfo", ivrLabel);
		}
		dbd.set("egressiveflag", pf.getEgressiveFlag());
		return dbd;
	}
	
	/**
	 * 退订操作
	 * @param id
	 * @return
	 */
	public boolean delOrderMenu(String id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(TimeUtil.getNowTime());
		
		OrderMenuHelp omh = new OrderMenuHelp();
		Object[] result = dao.findEntity(omh.getOrderMenuById(id));
		if(result != null && result.length > 0) {
			OrderMenu om = (OrderMenu)result[0];
			try {
				om.setMenuEndtime(sdf.parse(nowTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			om.setEgressiveFlag("N");
			dao.updateEntity(om);
			return true;
		}
		return false;
	}
	
	public int getOrderMenuSize() {
		return this.ORDER_MENU_COUNT;
	}
}
