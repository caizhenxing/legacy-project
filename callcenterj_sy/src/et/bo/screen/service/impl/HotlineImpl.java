package et.bo.screen.service.impl;
/**
 * 金农市场分析接口实现类
 * @author Chen Gang
 */
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.screen.service.HotlineService;
import et.po.OperCaseinfo;
import et.po.OperHotline;
import et.po.OperMarketAnalysis;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class HotlineImpl implements HotlineService {
	private BaseDAO dao = null;
	
	private ClassTreeService cts = null;
	
	private KeyService ks = null;
	
	private int num = 0;

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

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	/**
	 * 根据id取得一条金农市场分析信息详细
	 * @param id
	 * @return
	 */
	public IBaseDTO getMarAnalysisInfo(String id) {
		OperHotline oma = (OperHotline)dao.loadEntity(OperHotline.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", oma.getId());
		dto.set("hotlineContent", oma.getHotlineContent());
		
		return dto;
	}
	
	/**
	 * 根据查询条件返回金农市场分析信息列表
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List marketAnalysisInfoQuery(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		HotlineHelp mah = new HotlineHelp();
		
		Object[] result = dao.findEntity(mah.marketInfoQuery(dto, pi));
		num = dao.findEntitySize(mah.marketInfoQuery(dto, pi));

		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				OperHotline oma = (OperHotline)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("id", oma.getId());
				
				if(oma.getHotlineContent() != null) {
					if(oma.getHotlineContent().length() > 30)
						dbd.set("hotlineContent", oma.getHotlineContent().substring(0, 30)+"...");
					else
						dbd.set("hotlineContent", oma.getHotlineContent());
				} else
					dbd.set("hotlineContent", "");
				
				list.add(dbd);
			}
		}
		
		return list;
	}
	
	public List marketAnalysisInfoQuery2(){
		List list = new ArrayList();
		MarAnalysisHelp mah = new MarAnalysisHelp();
		
		Object[] result = dao.findEntity(mah.marketInfoQuery2());
		
		if(result.length < 5){//如果条数不够，就在原有基础上重复5次
			
			for(int j = 0; j < 5; j++){
				
				for(int i=0,size=result.length; i<size; i++) {
					OperMarketAnalysis po = (OperMarketAnalysis)result[i];
					
					DynaBeanDTO dto = new DynaBeanDTO();
					
					dto.set("typeTitle", po.getTypeTitle());//评别
					dto.set("subTitle", po.getSubTitle());//标题
					dto.set("overview", po.getMarketOverview());//市场概述
					dto.set("leaderboard", po.getPriceLeaderbord());//价格看板
					dto.set("quotes", po.getQuotesAnalysis());//行情分析
					dto.set("outlook", po.getInvestorsOutlook());//后市展望
					dto.set("remark", po.getInvestorsOutlook());//备注
					
					dto.set("analysisPerson", po.getAnalysisPerson());//分析员
					dto.set("analysisPersonInfo", po.getAnalysisPersonInfo());//分析员简介
					
					list.add(dto);
				}	
			}
		
		}else{
			//以下才是正常显示情况
			for(int i=0,size=result.length; i<size; i++) {
				OperMarketAnalysis po = (OperMarketAnalysis)result[i];
				
				DynaBeanDTO dto = new DynaBeanDTO();
				
				dto.set("typeTitle", po.getTypeTitle());//评别
				dto.set("subTitle", po.getSubTitle());//标题
				dto.set("overview", po.getMarketOverview());//市场概述
				dto.set("leaderboard", po.getPriceLeaderbord());//价格看板
				dto.set("quotes", po.getQuotesAnalysis());//行情分析
				dto.set("outlook", po.getInvestorsOutlook());//后市展望
				dto.set("remark", po.getInvestorsOutlook());//备注
				
				dto.set("analysisPerson", po.getAnalysisPerson());//分析员
				dto.set("analysisPersonInfo", po.getAnalysisPersonInfo());//分析员简介
				
				list.add(dto);
			}
		}
		return list;
	}
	
	public int getMarAnalysisInfoSize() {
		return num;
	}
	
	/**
	 * 添加金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean addMarketAnalysis(IBaseDTO dto) {
		try {
			dao.saveEntity(createHotline(dto));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private OperHotline createHotline(IBaseDTO dto) {
		OperHotline oma = new OperHotline();
		
		oma.setId(ks.getNext("OPER_HOTLINE"));
		oma.setHotlineContent(dto.get("hotlineContent").toString());
		return oma;
	}
	
	/**
	 * 修改金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean updateMarketAnalysis(IBaseDTO dto) {
		try {
			dao.updateEntity(modifyHotline(dto));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private OperHotline modifyHotline(IBaseDTO dto) {
		OperHotline oma = 
			(OperHotline)dao.loadEntity(OperHotline.class, dto.get("id").toString());
		
		oma.setHotlineContent(dto.get("hotlineContent").toString());
		
		return oma;
	}
	
	/**
	 * 删除金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean deleteMarketAnalysis(IBaseDTO dto) {
		OperHotline oma = 
			(OperHotline)dao.loadEntity(OperHotline.class, dto.get("id").toString());
		if(oma != null) {
			try {
				dao.removeEntity(oma);
				return true;
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		return false;
	}
	
	public List screenList() {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(OperHotline.class);
		
		dc.addOrder(Order.desc("id"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(20);
		
		Object[] result = (Object[]) dao.findEntity(mq);
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperHotline oci = (OperHotline) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("hotlineContent", oci.getHotlineContent());
			l.add(dbd);
		}
		return l;

	}	
	
}
