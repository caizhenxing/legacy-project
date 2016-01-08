package et.bo.screen.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.screen.service.SpecialSurveyService;
import et.po.OperCaseinfo;
import et.po.ScreenSpecialSurvey;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class SpecialSurveyServiceImpl implements SpecialSurveyService {
	private BaseDAO dao = null;

	private KeyService ks = null;

	private int num;
	
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
	
	/* (non-Javadoc)
	 * @see et.bo.screen.service.impl.SpecialSurveyService#getRecordSize()
	 */
	public int getRecordSize(){
		return this.num;
	}
	
	/* (non-Javadoc)
	 * @see et.bo.screen.service.impl.SpecialSurveyService#addSpecialSurvey(excellence.framework.base.dto.IBaseDTO)
	 */
	public void addSpecialSurvey(IBaseDTO dto){
		dao.saveEntity(toSpecialObj(dto));
	}
	
	private ScreenSpecialSurvey toSpecialObj(IBaseDTO dto){
		ScreenSpecialSurvey sss = null;
		String id = dto.get("sssId").toString();
		
		//若id为空 这是添加操作 否则是修改操作
		if(id==null||id.equals("")){
			sss = new ScreenSpecialSurvey();
			String idd = ks.getNext("screen_specialSurvey");
			sss.setId(idd);
			sss.setSurveyTime(new Date());
		}
		if(id!=null&&!id.equals("")){
			 sss = (ScreenSpecialSurvey)dao.loadEntity(ScreenSpecialSurvey.class, id);
			 sss.setSurveyUpdatetime(new Date());
		}
				
		String s = dto.get("sssTitle").toString();
		if(s!=null){
			sss.setSurveyTitle(s.trim());
		}
		
		s = dto.get("sssEmaple").toString();
		if(s!=null){
			sss.setSurveyExample(s.trim());
		}
		
		s = dto.get("sssKeywords").toString();
		if(s!=null){
			sss.setKeywords(s.trim());
		}
		
		s = dto.get("sssSummary").toString();
		if(s!=null){
			sss.setSummary(s.trim());
		}
		
		s = dto.get("sssWritter").toString();
		if(s!=null){
			sss.setWritter(s.trim());
		}
		
		s = dto.get("sssDelegeteDep").toString();
		if(s!=null){
			sss.setDelegateDep(s);
		}
		
		return sss;
	}
	

	
	/* (non-Javadoc)
	 * @see et.bo.screen.service.impl.SpecialSurveyService#delSpecialSurvey(java.lang.String)
	 */
	public void delSpecialSurvey(String id){
		ScreenSpecialSurvey sss = (ScreenSpecialSurvey)dao.loadEntity(ScreenSpecialSurvey.class, id);
		dao.removeEntity(sss);
	}
	
	/* (non-Javadoc)
	 * @see et.bo.screen.service.impl.SpecialSurveyService#updateSpecialSurvey(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean updateSpecialSurvey(IBaseDTO dto){
		boolean flag = false;
		try{
			dao.updateEntity(toSpecialObj(dto));
			flag = true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}
	
	/* (non-Javadoc)
	 * @see et.bo.screen.service.impl.SpecialSurveyService#getSSSList(excellence.framework.base.dto.IBaseDTO, excellence.common.page.PageInfo)
	 */
	public List getSSSList(IBaseDTO dto, PageInfo pi){
		List li = new ArrayList();
		ScreenSpecialSurvey sss = null;
		Object[] o = (Object[])dao.findEntity(queryHelp(dto, pi));
		this.num = dao.findEntitySize(queryHelp(dto, pi));
		for(int i=0;i<o.length;i++){
			sss = (ScreenSpecialSurvey)o[i];
			li.add(toDTOObjCut(sss));
		}
		return li;
	}
	private IBaseDTO toDTOObjCut(ScreenSpecialSurvey sss){
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("sssId", sss.getId());
		dbd.set("sssDelegeteDep",strCut(sss.getDelegateDep()) );
		dbd.set("sssKeywords", strCut(sss.getKeywords()));
		dbd.set("sssSummary", strCut(sss.getSummary()));
		
		dbd.set("sssEmaple", strCut(sss.getSurveyExample()));
		//格式化时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		dbd.set("sssCreateTime", sdf.format(sss.getSurveyTime()));
		Date d = sss.getSurveyUpdatetime();
		if(d!=null&&!"".equals(d.toString())){
			dbd.set("sssUpdateTime", sdf.format(d));
		}
		dbd.set("sssTitle", strCut(sss.getSurveyTitle()));
		dbd.set("sssWritter",strCut( sss.getWritter()));
		
		return dbd;
	}
	private String strCut(String s){
		if(s.length()>=15){
			s = s.substring(0,14)+"...";
		}
		return s ;
	}
	
	
	private IBaseDTO toDTOObj(ScreenSpecialSurvey sss){
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("sssId", sss.getId());
		
		dbd.set("sssDelegeteDep", sss.getDelegateDep());
		dbd.set("sssKeywords", sss.getKeywords());
		dbd.set("sssSummary", sss.getSummary());
		
		dbd.set("sssEmaple", sss.getSurveyExample());
		//格式化时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		dbd.set("sssCreateTime", sdf.format(sss.getSurveyTime()));
		Date d = sss.getSurveyUpdatetime();
		if(d!=null&&!"".equals(d.toString())){
			dbd.set("sssUpdateTime", sdf.format(d));
		}
		
		System.out.println(" toDTOObj 格式化时间成功");
		dbd.set("sssTitle", sss.getSurveyTitle());
		dbd.set("sssWritter", sss.getWritter());
		
		return dbd;
	}

	/**
	 * 获取screen的专题调查数据
	 */
	public List screenList() {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ScreenSpecialSurvey.class);
		
//		dc.add(Restrictions.eq("dictCaseType", "putong"));
		dc.addOrder(Order.desc("surveyTime"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(20);
		
		Object[] result = (Object[]) dao.findEntity(mq);
		
		for (int i = 0, size = result.length; i < size; i++) {
			ScreenSpecialSurvey spo = (ScreenSpecialSurvey) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			
			String surveyTitle = spo.getSurveyTitle();
			if(surveyTitle==null) surveyTitle = "";
			String surveyTime = spo.getSurveyTime().toString();
			if(surveyTime==null) surveyTime = "";
			else surveyTime = surveyTime.substring(0,surveyTime.indexOf("."));
			String surveyExample = spo.getSurveyExample();
			if(surveyExample==null) surveyExample = "";
			String delegateDep = spo.getDelegateDep();
			if(delegateDep==null) delegateDep = "";
			String writter = spo.getWritter();
			if(writter==null) writter = "";
			String keywords = spo.getKeywords();
			if(keywords==null) keywords = "";
			String summary = spo.getSummary();
			if(summary==null) summary = "";
			
			dbd.set("surveyTitle", surveyTitle);
			dbd.set("surveyTime", surveyTime);
			dbd.set("surveyExample", surveyExample);
			dbd.set("delegateDep", delegateDep);
			dbd.set("writter", writter);
			dbd.set("keywords", keywords);
			dbd.set("summary", summary);
			l.add(dbd);
		}
		return l;
	}	
	
	private MyQuery queryHelp(IBaseDTO dto ,PageInfo pi){
		MyQuery mq = new MyQueryImpl();
		StringBuffer sb = new StringBuffer("from ScreenSpecialSurvey where 1=1");
		//按时间 调查主题  关键字 撰稿人 模糊查询
		String s = dto.get("sssBeginTime").toString();
		if(s!=null&&!"".equals(s)){
			sb.append(" and surveyTime>='"+s.trim()+"'");
		}
		
		s = dto.get("sssEndTime").toString();
		if(s!=null&&!"".equals(s)){
			sb.append(" and surveyTime<='"+s.trim()+"'");
		}
		
		s = dto.get("sssTitle").toString();
		if(s!=null&&!"".equals(s)){
			sb.append(" and surveyTitle like '%"+s.trim()+"%'");
		}
		
		s = dto.get("sssKeywords").toString();
		if(s!=null&&!"".equals(s)){
			sb.append(" and keywords like '%"+s.trim()+"%'");
		}
		
		s = dto.get("sssWritter").toString();
		if(s!=null&&!"".equals(s)){
			sb.append(" and writter like '%"+s.trim()+"%'");
		}
		sb.append(" order by surveyTime desc");
		mq.setHql(sb.toString());
		mq.setFirst(pi.getBegin());
		mq.setFetch(pi.getPageSize());
		return mq;
	}
	/* (non-Javadoc)
	 * @see et.bo.screen.service.impl.SpecialSurveyService#getObjById(java.lang.String)
	 */
	public IBaseDTO getObjById(String id){
		ScreenSpecialSurvey sss = (ScreenSpecialSurvey)dao.loadEntity(ScreenSpecialSurvey.class, id);
		return toDTOObj(sss);
	}
	
}
