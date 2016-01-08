/*
 * 
 */
package et.bo.callcenter.callOutSet.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.iflytek.Qtts;

import et.bo.callcenter.callOutSet.service.CallOutSetService;
import et.po.GroupCallback;
import et.po.OperCustinfo;
import et.po.OrderMenu;
import excellence.common.classtree.ClassTreeService;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.common.util.Constants;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * 
 * @author chen gang
 *
 */
public class CallOutSetImpl implements CallOutSetService {
	static Logger log = Logger.getLogger(CallOutSetImpl.class.getName());
	
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
	 * 添加批量外呼业务定制信息记录
	 * @param dto
	 * @return
	 */
	public boolean addOrderMenu(IBaseDTO dto) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = sdf.format(TimeUtil.getNowTime());
		String nowDate = sdf2.format(TimeUtil.getNowTime());
		
//		String orderType = dto.get("orderType").toString();
		String telNum = dto.get("telNum").toString();
		String beginDate = dto.get("beginDate").toString();
		String beginTime = dto.get("beginTime").toString();
		String ivrInfo = dto.get("ivrInfo").toString();
		
		
		String[] array = telNum.split(",");
		//System.out.println("array length is "+array.length);
		
		for(int i=0,size=array.length; i<size; i++) {
			OrderMenu om = new OrderMenu();
			//System.out.println("........ "+array[i]);
			if("".equals(beginTime)){
				om.setMenuType("orderProgramme");
				try {
					om.setMenuBegintime(sdf.parse(nowTime));
					om.setMenuEndtime(sdf.parse(nowTime));
					om.setEgressiveTime(sdf.parse(nowTime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				om.setTelnum(array[i]);
				om.setEgressiveFlag("Y");
				om.setIvrTreeInfoId(ivrInfo);
//				om.setCalloutnum(0);
				dao.saveEntity(om);
				
			} else{
				om.setMenuType("orderProgramme");
				try {
					om.setMenuBegintime(sdf.parse(nowTime));
					om.setMenuEndtime(sdf.parse(nowTime));
					if(!"".equals(beginDate))
						om.setEgressiveTime(sdf.parse(beginDate+" "+beginTime+":00"));
					else
						om.setEgressiveTime(sdf.parse(nowDate+" "+beginTime+":00"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				om.setTelnum(array[i]);
				om.setEgressiveFlag("Y");
				om.setIvrTreeInfoId(ivrInfo);
//				om.setCalloutnum(0);
				dao.saveEntity(om);
				
			}
			
		}
		return true;

	}
	
	
	/**
	 * 查询定制后未退订的信息
	 */
	public List orderMenuSearch(String telNum, PageInfo pi) {
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		
		OrderMenu om = new OrderMenu();
		OrderMenuHelp omh = new OrderMenuHelp();
		
		Object[] result = dao.findEntity(omh.getCustomizeRecord(telNum, pi));
		Object[] result2 = dao.findEntity(omh.getCustomizeRecordSize(telNum));
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
		dbd.set("orderType", "dingzhi");
		dbd.set("telNum", pf.getTelnum());
		dbd.set("beginTime", pf.getMenuBegintime());
		
		String ivrInfo = pf.getIvrTreeInfoId();
		String ivrLabel = cts.getLabelByNickName(ivrInfo);
		
		dbd.set("ivrInfo", ivrLabel);

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
	
	/**
	 * 添加由文本外呼
	 * @param dto
	 * @return
	 */
	public boolean addGroupCallBack(IBaseDTO dto) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			String nowTime = sdf.format(TimeUtil.getNowTime());
			String nowDate = sdf2.format(TimeUtil.getNowTime());
			
//		String orderType = dto.get("orderType").toString();
			String telNum = dto.get("telNum").toString();
			System.out.println("telNum is "+telNum);
			String beginDate = dto.get("beginDate").toString();
			String beginTime = dto.get("beginTime").toString();
			String textInfo = dto.get("textContent").toString();
			String subid = dto.get("subid").toString();
			
			String[] array = telNum.split(",");
			System.out.println("length is "+array.length);
			for(int i=0,size=array.length; i<size; i++) {
				GroupCallback gcb = new GroupCallback();
				try {
					if("".equals(beginTime)){
						gcb.setEgressiveTime(sdf.parse(nowTime));
					}else{
						if("".equals(beginDate)){
							gcb.setEgressiveTime(sdf.parse(nowDate+" "+beginTime+":00"));
						}else{
							gcb.setEgressiveTime(sdf.parse(beginDate+" "+beginTime+":00"));
						}
					}
					gcb.setAddtime(sdf.parse(nowTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gcb.setTelnum(array[i]);
				gcb.setContext(textInfo);
				gcb.setAgcwid(subid);
				String filepath = createVoicePath("groupCallback");
				gcb.setFilepath(filepath);
				gcb.setEgressiveFlag("Y");
				createTTsFile(filepath, textInfo);
				
				dao.saveEntity(gcb);
			}
			return true;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 生成指定的文件夹和音频全路径
	 * 
	 * @param ivrtype
	 * @return
	 */
	private String createVoicePath(String ivrtype) {
		StringBuffer sb = new StringBuffer();
		// 生成指定文件夹下的音频文件
		String ivrmorevoicepath = Constants.getProperty("ivrmorevoicepath");
		sb.append(ivrmorevoicepath);// 指定文件夹，需要提出去
		File f1 = new File(sb.toString());
		if (!f1.exists()) {
			f1.mkdir();
		}
		sb.append("\\");
		sb.append(ivrtype);// 根据IVR业务分类生成文件夹
		File f2 = new File(sb.toString());
		if (!f2.exists()) {
			f2.mkdir();
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
		sb.append("\\");
		sb.append(sdf.format(date));
		sb.append(".wav");
		return sb.toString();
	}

	/**
	 * 生成TTS文件
	 * 
	 * @param path
	 * @param str
	 */
	private void createTTsFile(String path, String str) {
		String serverip = Constants.getProperty("tts_ip");
		Qtts qttsobj = new Qtts();
		TimeUtil tu = new TimeUtil();
		//System.out.println("path: " + path);
		//System.out.println("str: " + str);
		qttsobj.synthesize(str, false, path, serverip, "", "1=3");// ip为服务器ip
		//System.out.println("执行成功...");
		//System.out.println("the end time "
		//		+ tu.getTheTimeStr(new Date(), "yyyyMMddHHmmss"));
	}
	
	public int getOrderMenuSize() {
		return this.ORDER_MENU_COUNT;
	}
}
