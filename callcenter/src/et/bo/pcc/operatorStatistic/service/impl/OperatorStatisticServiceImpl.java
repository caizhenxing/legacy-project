package et.bo.pcc.operatorStatistic.service.impl;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.pcc.operatorStatistic.service.OperatorStatisticService;
import et.po.OperatorWorkInfo;
import et.po.SysGroup;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class OperatorStatisticServiceImpl implements OperatorStatisticService {
  
	private String _SignIn = "1";
    
    private String _SignOut = "2";
    
    private String _AnswerPhone = "3";
    
    private String _DisconnectPhone ="4";
    
    private String _Setting = "5";
    
    private String _OutSetting = "6";
    
    private int num = 0;
    
	private BaseDAO dao = null;
	
	private KeyService ks = null;	
	
	
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

	private OperatorWorkInfo createWorkInfo(String operator,String operatorState){
		OperatorWorkInfo owi = new OperatorWorkInfo();
//		owi.setId(ks.getNext("OperatorWorkInfo"));
		owi.setId(ks.getNext("Operator_Work_Info"));
		owi.setOperator(operator);
		owi.setOperateDate(TimeUtil.getNowTime());
		owi.setOperateTime(TimeUtil.getNowTime());
		owi.setOperatorState(operatorState);
		return owi;
	}
	
	private OperatorWorkInfo createAnswerPhoneInfo(String operator,String operatorState,Date date){
		OperatorWorkInfo owi = new OperatorWorkInfo();
		owi.setId(ks.getNext("Operator_Work_Info"));
		owi.setOperator(operator);
		owi.setOperateDate(TimeUtil.getNowTime());
		owi.setOperateTime(date);
		owi.setOperatorState(operatorState);
		return owi;
	}
	 
	//签入
	public void addSignIn(String operator) {
		// TODO Auto-generated method stub
		dao.saveEntity(createWorkInfo(operator, _SignIn));
	}
    //签出
	public void addSignOut(String operator) {
		// TODO Auto-generated method stub
        dao.saveEntity(createWorkInfo(operator, _SignOut));
	}
    //入席
	public void addSetting(String operator) {
		// TODO Auto-generated method stub
        dao.saveEntity(createWorkInfo(operator, _Setting));
	}	
	//离席
	public void addOutSetting(String operator) {
		// TODO Auto-generated method stub
        dao.saveEntity(createWorkInfo(operator, _OutSetting));
	}	
    //接电话
	public void addAnswerPhone(String operator ,Date date) {
		// TODO Auto-generated method stub
        dao.saveEntity(createAnswerPhoneInfo(operator, _AnswerPhone ,date));
	}
    //挂电话
	public void addDisconnectPhone(String operator ,Date date) {
		// TODO Auto-generated method stub
		dao.saveEntity(createAnswerPhoneInfo(operator,_DisconnectPhone ,date));
	}

	public int getOperatorWorkInfoSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public List operatorWorkInfoQuery(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		OperatorStatisticHelp osh = new OperatorStatisticHelp();
		SysGroup sg = (SysGroup)dao.loadEntity(SysGroup.class, "operator");
		Object[] personResult = (Object[])dao.findEntity(osh.workInfoPersonQuery(dto, sg, pi));
		num = dao.findEntitySize(osh.workInfoPersonQuery(dto, sg, pi));
		List list = new ArrayList();		
		for(int i=0,size=personResult.length;i<size;i++){
			DynaBeanDTO dbd = new DynaBeanDTO();
			SysUser su = (SysUser)personResult[i];
			int qianru = dao.findEntitySize(osh.operatorWorkInfoQuery(dto, su.getUserId(), "1"));
			int ruxi = dao.findEntitySize(osh.operatorWorkInfoQuery(dto, su.getUserId(), "5"));
			int jiedianhua = dao.findEntitySize(osh.operatorWorkInfoQuery(dto, su.getUserId(), "3"));
			dbd.set("operator", su.getUserName());
			dbd.set("signIn", String.valueOf(qianru));
			dbd.set("setting", String.valueOf(ruxi));
			dbd.set("answerPhone",String.valueOf(jiedianhua));
			dbd.set("beginTime", dto.get("beginTime").toString());
			dbd.set("endTime", dto.get("endTime").toString());
			list.add(dbd);
		}
		return list;
	}	
	
	public List<LabelValueBean> getWorkInfoPersonList(){
		List list = new ArrayList();
		SysGroup sg = (SysGroup)dao.loadEntity(SysGroup.class, "operator");
		OperatorStatisticHelp osh = new OperatorStatisticHelp();
		Object[] result = (Object[])dao.findEntity(osh.getWorkInfoPersonList(sg));
		for(int i=0,size=result.length;i<size;i++){
			SysUser su = (SysUser)result[i];
			list.add(new LabelValueBean(su.getUserName(),su.getUserId()));
		}
		return list;
	}
	
	public List operatorWorkInfoDetailQuery(IBaseDTO dto){
		return null;
	}
	
	public List operatorChuXiQuery(IBaseDTO dto){
		OperatorStatisticHelp osh = new OperatorStatisticHelp();
//		SysGroup sg = (SysGroup)dao.loadEntity(SysGroup.class, "administrator");
//		Object[] personResult = (Object[])dao.findEntity(osh.workInfoPersonQuery(dto, sg, pi));	
		SysUser su = (SysUser)dao.loadEntity(SysUser.class, dto.get("operator").toString());
//		num = dao.findEntitySize(osh.workInfoPersonQuery(dto, sg, pi));
		List list = new ArrayList();
		Calendar ca = Calendar.getInstance();
//		for(int i=0,size=personResult.length;i<size;i++){
//			DynaBeanDTO dbd = new DynaBeanDTO();
//			SysUser su = (SysUser)personResult[i];
			Date startDate = TimeUtil.getTimeByStr(dto.get("beginTime").toString(),"yyyy-MM-dd");
			Date endDate = TimeUtil.getTimeByStr(dto.get("endTime").toString(),"yyyy-MM-dd");	
			while(startDate.compareTo(endDate)<=0){
				DynaBeanDTO dbd = new DynaBeanDTO();
//				String Sdate = TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd");
//				System.out.println("local :"+startDate.toString());
				Object[] chuxiResult = (Object[])dao.findEntity(osh.getWorkInfoByDate(startDate, su.getUserId(), "1","2"));
//				System.out.println(chuxiResult.length);
				StringBuilder builder = new StringBuilder("");
				for(int j=0,sizej=chuxiResult.length;j<sizej;j++){
					OperatorWorkInfo owi = (OperatorWorkInfo)chuxiResult[j];
//					System.out.println("状态码 :"+owi.getOperatorState());
					if(owi.getOperatorState().equals("1")){
						builder.append("签入");
						builder.append(" ");
						builder.append(TimeUtil.getTheTimeStr(owi.getOperateTime(), "HH:mm:ss"));
						builder.append(" ");
						builder.append("|");
						builder.append(" ");
					}
					if(owi.getOperatorState().equals("2")){
						builder.append("签出");
						builder.append(" ");
						builder.append(TimeUtil.getTheTimeStr(owi.getOperateTime(), "HH:mm:ss"));	
						builder.append(" ");
						builder.append("|");
						builder.append(" ");
					}	
//					System.out.println(" 具体情况 "+builder.toString());
				}
//				System.out.println("页面时间"+TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"));
				dbd.set("operateDate", TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"));
				dbd.set("chuxiInfo", builder.toString());
//				System.out.println(" 时间 "+dbd.get("operateDate"));
				list.add(dbd);
				ca.setTime(startDate);
				ca.add(ca.DATE, 1);
				startDate = ca.getTime();
			}
//		}
		return list;
	}
	public List operatorJieTingQuery(IBaseDTO dto){
		OperatorStatisticHelp osh = new OperatorStatisticHelp();
//		SysGroup sg = (SysGroup)dao.loadEntity(SysGroup.class, "administrator");
//		Object[] personResult = (Object[])dao.findEntity(osh.workInfoPersonQuery(dto, sg, pi));	
		SysUser su = (SysUser)dao.loadEntity(SysUser.class, dto.get("operator").toString());
//		num = dao.findEntitySize(osh.workInfoPersonQuery(dto, sg, pi));
		List list = new ArrayList();
		Calendar ca = Calendar.getInstance();
//		for(int i=0,size=personResult.length;i<size;i++){
//			DynaBeanDTO dbd = new DynaBeanDTO();
//			SysUser su = (SysUser)personResult[i];
			Date startDate = TimeUtil.getTimeByStr(dto.get("beginTime").toString(),"yyyy-MM-dd");
			Date endDate = TimeUtil.getTimeByStr(dto.get("endTime").toString(),"yyyy-MM-dd");	
			while(startDate.compareTo(endDate)<=0){
				DynaBeanDTO dbd = new DynaBeanDTO();
//				String Sdate = TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd");
//				System.out.println("local :"+startDate.toString());
				Object[] chuxiResult = (Object[])dao.findEntity(osh.getWorkInfoByDate(startDate, su.getUserId(), "3","4"));
//				System.out.println(chuxiResult.length);
				StringBuilder builder = new StringBuilder("");
				for(int j=0,sizej=chuxiResult.length;j<sizej;j++){
					OperatorWorkInfo owi = (OperatorWorkInfo)chuxiResult[j];
//					System.out.println("状态码 :"+owi.getOperatorState());
					if(owi.getOperatorState().equals("3")){
						builder.append("接听");
						builder.append(" ");
						builder.append(TimeUtil.getTheTimeStr(owi.getOperateTime(), "HH:mm:ss"));
						builder.append(" ");
						builder.append("|");
						builder.append(" ");
					}
					if(owi.getOperatorState().equals("4")){
						builder.append("挂断");
						builder.append(" ");
						builder.append(TimeUtil.getTheTimeStr(owi.getOperateTime(), "HH:mm:ss"));	
						builder.append(" ");
						builder.append("|");
						builder.append(" ");
					}	
					System.out.println(" 具体情况 "+builder.toString());
				}
//				System.out.println("页面时间"+TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"));
				dbd.set("operateDate", TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"));
				dbd.set("chuxiInfo", builder.toString());
//				System.out.println(" 时间 "+dbd.get("operateDate"));
				list.add(dbd);
				ca.setTime(startDate);
				ca.add(ca.DATE, 1);
				startDate = ca.getTime();
			}
		return list;
	}
	public List operatorOutQuery(IBaseDTO dto){
		OperatorStatisticHelp osh = new OperatorStatisticHelp();
//		SysGroup sg = (SysGroup)dao.loadEntity(SysGroup.class, "administrator");
//		Object[] personResult = (Object[])dao.findEntity(osh.workInfoPersonQuery(dto, sg, pi));	
		SysUser su = (SysUser)dao.loadEntity(SysUser.class, dto.get("operator").toString());
//		num = dao.findEntitySize(osh.workInfoPersonQuery(dto, sg, pi));
		List list = new ArrayList();
		Calendar ca = Calendar.getInstance();
//		for(int i=0,size=personResult.length;i<size;i++){
//			DynaBeanDTO dbd = new DynaBeanDTO();
//			SysUser su = (SysUser)personResult[i];
			Date startDate = TimeUtil.getTimeByStr(dto.get("beginTime").toString(),"yyyy-MM-dd");
			Date endDate = TimeUtil.getTimeByStr(dto.get("endTime").toString(),"yyyy-MM-dd");	
			while(startDate.compareTo(endDate)<=0){
				DynaBeanDTO dbd = new DynaBeanDTO();
//				String Sdate = TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd");
//				System.out.println("local :"+startDate.toString());
				Object[] chuxiResult = (Object[])dao.findEntity(osh.getWorkInfoByDate(startDate, su.getUserId(), "5","6"));
//				System.out.println(chuxiResult.length);
				StringBuilder builder = new StringBuilder("");
				for(int j=0,sizej=chuxiResult.length;j<sizej;j++){
					OperatorWorkInfo owi = (OperatorWorkInfo)chuxiResult[j];
//					System.out.println("状态码 :"+owi.getOperatorState());
					if(owi.getOperatorState().equals("5")){
						builder.append("入席");
						builder.append(" ");
						builder.append(TimeUtil.getTheTimeStr(owi.getOperateTime(), "HH:mm:ss"));
						builder.append(" ");
						builder.append("|");
						builder.append(" ");
					}
					if(owi.getOperatorState().equals("6")){
						builder.append("离席");
						builder.append(" ");
						builder.append(TimeUtil.getTheTimeStr(owi.getOperateTime(), "HH:mm:ss"));	
						builder.append(" ");
						builder.append("|");
						builder.append(" ");
					}	
					System.out.println(" 具体情况 "+builder.toString());
				}
//				System.out.println("页面时间"+TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"));
				dbd.set("operateDate", TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"));
				dbd.set("chuxiInfo", builder.toString());
//				System.out.println(" 时间 "+dbd.get("operateDate"));
				list.add(dbd);
				ca.setTime(startDate);
				ca.add(ca.DATE, 1);
				startDate = ca.getTime();
			}
		return list;
	}
    public List chuXiTimeQuery(IBaseDTO dto) {
    	OperatorStatisticHelp osh = new OperatorStatisticHelp();
		SysUser su = (SysUser)dao.loadEntity(SysUser.class, dto.get("operator").toString());
		List list = new ArrayList();
		Calendar ca = Calendar.getInstance();
			Date startDate = TimeUtil.getTimeByStr(dto.get("beginTime").toString(),"yyyy-MM-dd");
			Date endDate = TimeUtil.getTimeByStr(dto.get("endTime").toString(),"yyyy-MM-dd");	
			while(startDate.compareTo(endDate)<=0){
				DynaBeanDTO dbd = new DynaBeanDTO();
				Object[] chuxiResult = (Object[])dao.findEntity(osh.getWorkInfoByDate(startDate, su.getUserId(), "1","2"));
//				System.out.println(chuxiResult.length);
				StringBuilder builder = new StringBuilder("");
				Date sd = new Date();
				Date ed = new Date();
				long times = 0;
				for(int i=0,size=chuxiResult.length;i<size;i++){
					OperatorWorkInfo owi = (OperatorWorkInfo)chuxiResult[i];
				    int j=i%2;
					if(owi.getOperatorState().equals("1")){
                       sd = owi.getOperateTime();
					}
					if(owi.getOperatorState().equals("2")){
					   ed = owi.getOperateTime();
					   long between = ed.getTime()-sd.getTime();
					   times=times+between;
					}
				}
				dbd.set("operateDate", TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"));
//				System.out.println("时间的毫秒数为:  "+times);
				dbd.set("chuxiInfo", getTimeByMillis(times/1000));
				list.add(dbd);
				ca.setTime(startDate);
				ca.add(ca.DATE, 1);
				startDate = ca.getTime();
			}
		return list;
	}

	public List jieTingTimeQuery(IBaseDTO dto) {
		// TODO Auto-generated method stub
		OperatorStatisticHelp osh = new OperatorStatisticHelp();
		SysUser su = (SysUser)dao.loadEntity(SysUser.class, dto.get("operator").toString());
		List list = new ArrayList();
		Calendar ca = Calendar.getInstance();
			Date startDate = TimeUtil.getTimeByStr(dto.get("beginTime").toString(),"yyyy-MM-dd");
			Date endDate = TimeUtil.getTimeByStr(dto.get("endTime").toString(),"yyyy-MM-dd");	
			while(startDate.compareTo(endDate)<=0){
				DynaBeanDTO dbd = new DynaBeanDTO();
				Object[] chuxiResult = (Object[])dao.findEntity(osh.getWorkInfoByDate(startDate, su.getUserId(), "3","4"));
//				System.out.println(chuxiResult.length);
				StringBuilder builder = new StringBuilder("");
				Date sd = new Date();
				Date ed = new Date();
				long times = 0;
				for(int i=0,size=chuxiResult.length;i<size;i++){
					OperatorWorkInfo owi = (OperatorWorkInfo)chuxiResult[i];
				    int j=i%2;
					if(owi.getOperatorState().equals("3")){
                       sd = owi.getOperateTime();
					}
					if(owi.getOperatorState().equals("4")){
					   ed = owi.getOperateTime();
					   long between = ed.getTime()-sd.getTime();
					   times=times+between;
					}
				}
				dbd.set("operateDate", TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"));
				dbd.set("chuxiInfo", getTimeByMillis(times/1000));
				list.add(dbd);
				ca.setTime(startDate);
				ca.add(ca.DATE, 1);
				startDate = ca.getTime();
			}
		return list;
	}

	public List outTimeQuery(IBaseDTO dto) {
		// TODO Auto-generated method stub
		OperatorStatisticHelp osh = new OperatorStatisticHelp();
		SysUser su = (SysUser)dao.loadEntity(SysUser.class, dto.get("operator").toString());
		List list = new ArrayList();
		Calendar ca = Calendar.getInstance();
			Date startDate = TimeUtil.getTimeByStr(dto.get("beginTime").toString(),"yyyy-MM-dd");
			Date endDate = TimeUtil.getTimeByStr(dto.get("endTime").toString(),"yyyy-MM-dd");	
			while(startDate.compareTo(endDate)<=0){
				DynaBeanDTO dbd = new DynaBeanDTO();
				Object[] chuxiResult = (Object[])dao.findEntity(osh.getWorkInfoByDate(startDate, su.getUserId(), "5","6"));
//				System.out.println(chuxiResult.length);
				StringBuilder builder = new StringBuilder("");
				Date sd = new Date();
				Date ed = new Date();
				long times = 0;
				
				for(int i=0,size=chuxiResult.length;i<size;i++){
					OperatorWorkInfo owi = (OperatorWorkInfo)chuxiResult[i];
//				    int j=i%2;
//					System.out.println(owi.getOperatorState()+"  "+ed.getTime()+"  "+sd.getTime());
					if(i!=0&&owi.getOperatorState().equals("6")){
					   ed = owi.getOperateTime();	
                       long between = ed.getTime()-sd.getTime();
//                       System.out.println(between);
					   times=times+between;
					}
					if(i!=size&&owi.getOperatorState().equals("5")){
						sd = owi.getOperateTime();							   
					}
				}
				dbd.set("operateDate", TimeUtil.getTheTimeStr(startDate, "yyyy-MM-dd"));
//				System.out.println("时常： "+times);
				dbd.set("chuxiInfo", getTimeByMillis(times/1000));
				list.add(dbd);
				ca.setTime(startDate);
				ca.add(ca.DATE, 1);
				startDate = ca.getTime();
			}
		return list;
	}
	
	private String getTimeByMillis(long millis){
//		long timeInMillis = 123456789;
//        long timeInMillis = 7200;   
//		System.out.println("传进来的是： "+millis);
        int hours = (int)Math.floor(millis/(60*60));
        int minutes = (int)Math.floor((millis %(60*60))/60);
        int seconds = (int)(millis %(60*60))%60; 
        String times = hours + ":" + minutes + ":" + seconds;
//        System.out.println("time is : "+times);
        return  times;
//        formattedDate;
	}

}
