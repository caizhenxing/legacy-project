package et.bo.screen.service.impl;
/**
 * 金农市场分析接口实现类
 * @author Chen Gang
 */
import java.util.ArrayList;
import java.util.List;

import et.bo.screen.service.MarAnalysisService;
import et.po.OperMarketAnalysis;
import et.po.OperMarketAnalysisScreen;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

public class MarAnalysisImpl implements MarAnalysisService {
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
		OperMarketAnalysis oma = (OperMarketAnalysis)dao.loadEntity(OperMarketAnalysis.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", oma.getId());
		dto.set("typeTitle", oma.getTypeTitle());
		dto.set("subTitle", oma.getSubTitle());
		dto.set("overview", oma.getMarketOverview());
		dto.set("leaderboard", oma.getPriceLeaderbord());
		dto.set("quotes", oma.getQuotesAnalysis());
		dto.set("outlook", oma.getInvestorsOutlook());
		dto.set("remark", oma.getRemark());
		dto.set("analysisPerson", oma.getAnalysisPerson());
		dto.set("analysisPersonInfo", oma.getAnalysisPersonInfo());
		dto.set("recordTime", oma.getAddTime());
		dto.set("recordPerson", oma.getAddPerson());
		
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
		MarAnalysisHelp mah = new MarAnalysisHelp();
		
		Object[] result = dao.findEntity(mah.marketInfoQuery(dto, pi));
		num = dao.findEntitySize(mah.marketInfoQuery(dto, pi));

		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				OperMarketAnalysis oma = (OperMarketAnalysis)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("id", oma.getId());
				dbd.set("typeTitle", oma.getTypeTitle());
				dbd.set("subTitle", oma.getSubTitle());
				
				if(oma.getMarketOverview().length() > 10)
					dbd.set("overview", oma.getMarketOverview().substring(0, 10)+"...");
				else
					dbd.set("overview", oma.getMarketOverview());
				dbd.set("analysisPerson", oma.getAnalysisPerson());
				
				if(oma.getAnalysisPersonInfo() != null) {
					if(oma.getAnalysisPersonInfo().length() > 10)
						dbd.set("analysisPersonInfo", oma.getAnalysisPersonInfo().substring(0, 10)+"...");
					else
						dbd.set("analysisPersonInfo", oma.getAnalysisPersonInfo());
				} else
					dbd.set("analysisPersonInfo", "");
				
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
			dao.saveEntity(createMarAnalysis(dto));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private OperMarketAnalysis createMarAnalysis(IBaseDTO dto) {
		OperMarketAnalysis oma = new OperMarketAnalysis();
		
		oma.setId(ks.getNext("OPER_MARKET_ANALYSIS"));
		oma.setTypeTitle(dto.get("typeTitle").toString());
		oma.setSubTitle(dto.get("subTitle").toString());
		oma.setMarketOverview(dto.get("overview").toString());
		oma.setPriceLeaderbord(dto.get("leaderboard").toString());
		oma.setQuotesAnalysis(dto.get("quotes").toString());
		oma.setInvestorsOutlook(dto.get("outlook").toString());
		oma.setAnalysisPerson(dto.get("analysisPerson").toString());
		oma.setAnalysisPersonInfo(dto.get("analysisPersonInfo").toString());
		oma.setRemark(dto.get("remark").toString());
		oma.setAddPerson(dto.get("recordPerson").toString());
		oma.setAddTime(TimeUtil.getNowTime());
		return oma;
	}
	
	/**
	 * 修改金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean updateMarketAnalysis(IBaseDTO dto) {
		try {
			dao.updateEntity(modifyMarAnalysis(dto));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private OperMarketAnalysis modifyMarAnalysis(IBaseDTO dto) {
		OperMarketAnalysis oma = 
			(OperMarketAnalysis)dao.loadEntity(OperMarketAnalysis.class, dto.get("id").toString());
		
		oma.setTypeTitle(dto.get("typeTitle").toString());
		oma.setSubTitle(dto.get("subTitle").toString());
		oma.setMarketOverview(dto.get("overview").toString());
		oma.setPriceLeaderbord(dto.get("leaderboard").toString());
		oma.setQuotesAnalysis(dto.get("quotes").toString());
		oma.setInvestorsOutlook(dto.get("outlook").toString());
		oma.setAnalysisPerson(dto.get("analysisPerson").toString());
		oma.setAnalysisPersonInfo(dto.get("analysisPersonInfo").toString());
		oma.setRemark(dto.get("remark").toString());
		oma.setAddPerson(dto.get("recordPerson").toString());
		
		return oma;
	}
	
	/**
	 * 删除金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean deleteMarketAnalysis(IBaseDTO dto) {
		OperMarketAnalysis oma = 
			(OperMarketAnalysis)dao.loadEntity(OperMarketAnalysis.class, dto.get("id").toString());
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
	/**
	 * 根据查询条件返回金农市场分析信息列表 按时间倒序 显示头几条
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List marketAnalysisInfoQuery(int size)
	{
		List list = new ArrayList();
		MarAnalysisHelp mah = new MarAnalysisHelp();
		
		Object[] result = dao.findEntity(mah.marketInfoQuery(size));

		if(result != null && result.length > 0) {
			for(int i=0; i<result.length; i++) {
				OperMarketAnalysisScreen oma = (OperMarketAnalysisScreen)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("id", oma.getId());
				dbd.set("subTitle", oma.getTitle());
				
				if(oma.getAnalysisContent()!=null)
					dbd.set("analysisContent", oma.getAnalysisContent());
				else
					dbd.set("analysisContent", "");
				dbd.set("analysisPerson", oma.getAnalysiser());
				
				if(oma.getAnalysiserInfo() != null) {
						dbd.set("analysisPersonInfo", oma.getAnalysiserInfo());
				} else
					dbd.set("analysisPersonInfo", "");
				dbd.set("recordTime", oma.getAddTime());
				String photo = oma.getAnalysiserPhoto();
			
				
				dbd.set("analysiserPhoto", getPhoto(photo));
				list.add(dbd);
			}
		}
		
		return list;
	}
	/**
	 * add wwq 大屏幕得到图片信息
	 * @param photo
	 * @return
	 */
	private String getPhoto(String photo)
	{
		if(photo==null)
		{
			photo="";
		}
		else
		{
			photo = photo.trim();
		}
		if("".equals(photo)) //默认图片
		{
			photo = "images/Market Analysis1.jpg";
			return photo;
		}
		else
		{
			return photo;
		}
	}
	// **************************** add by chengang 2009-03-12 **********************
	
	/**
	 * 根据查询条件返回金农市场分析信息列表
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List marketAnalysisInfoQuery2(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		MarAnalysisHelp mah = new MarAnalysisHelp();
		
		Object[] result = dao.findEntity(mah.marketAnalysisInfoQuery(dto, pi));
		num = dao.findEntitySize(mah.marketAnalysisInfoQuery(dto, pi));

		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				OperMarketAnalysisScreen oma = (OperMarketAnalysisScreen)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("id", oma.getId());
				dbd.set("subTitle", oma.getTitle());
				
				if(oma.getAnalysisContent().length() > 10)
					dbd.set("analysisContent", oma.getAnalysisContent().substring(0, 10)+"...");
				else
					dbd.set("analysisContent", oma.getAnalysisContent());
				dbd.set("analysisPerson", oma.getAnalysiser());
				
				if(oma.getAnalysiserInfo() != null) {
					if(oma.getAnalysiserInfo().length() > 10)
						dbd.set("analysisPersonInfo", oma.getAnalysiserInfo().substring(0, 10)+"...");
					else
						dbd.set("analysisPersonInfo", oma.getAnalysiserInfo());
				} else
					dbd.set("analysisPersonInfo", "");
				dbd.set("recordTime", oma.getAddTime());
				
				list.add(dbd);
			}
		}
		
		return list;
	}
	
	/**
	 * 根据id取得一条金农市场分析信息详细
	 * @param id
	 * @return
	 */
	public IBaseDTO getMarAnalysisInfo2(String id) {
		OperMarketAnalysisScreen oma = (OperMarketAnalysisScreen)dao.loadEntity(OperMarketAnalysisScreen.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", oma.getId());
		dto.set("subTitle", oma.getTitle());
		dto.set("analysisContent", oma.getAnalysisContent());
		dto.set("analysisPerson", oma.getAnalysiser());
		dto.set("analysisPersonInfo", oma.getAnalysiserInfo());
		dto.set("analysisType", oma.getAnalysisType().trim());
		dto.set("remark", oma.getRemark());
		dto.set("analysiserPhoto", oma.getAnalysiserPhoto());
		dto.set("analysiserPhoto2", oma.getAnalysiserPhoto());
		dto.set("recordTime", oma.getAddTime());
		
		return dto;
	}
	
	/**
	 * 添加金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean addMarketAnalysis2(IBaseDTO dto) {
		try {
			dao.saveEntity(createMarAnalysis2(dto));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private OperMarketAnalysisScreen createMarAnalysis2(IBaseDTO dto) {
		OperMarketAnalysisScreen oma = new OperMarketAnalysisScreen();
		
		oma.setId(ks.getNext("OPER_MARKET_ANALYSIS_SCREEN"));
		oma.setTitle(dto.get("subTitle").toString());
		oma.setAnalysisType(dto.get("analysisType").toString());
		oma.setAnalysisContent(dto.get("analysisContent").toString());
		oma.setAnalysiserPhoto(dto.get("analysiserPhoto").toString());
		oma.setAnalysiser(dto.get("analysisPerson").toString());
		oma.setAnalysiserInfo(dto.get("analysisPersonInfo").toString());
		oma.setRemark(dto.get("remark").toString());
		oma.setAddTime(TimeUtil.getNowTime());
		return oma;
	}
	
	/**
	 * 修改金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean updateMarketAnalysis2(IBaseDTO dto) {
		try {
			dao.updateEntity(modifyMarAnalysis2(dto));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private OperMarketAnalysisScreen modifyMarAnalysis2(IBaseDTO dto) {
		OperMarketAnalysisScreen oma = 
			(OperMarketAnalysisScreen)dao.loadEntity(OperMarketAnalysisScreen.class, dto.get("id").toString());
		
		oma.setTitle(dto.get("subTitle").toString());
		oma.setAnalysisType(dto.get("analysisType").toString());
		oma.setAnalysisContent(dto.get("analysisContent").toString());
		if(!"".equals(dto.get("analysiserPhoto2").toString()) && "".equals(dto.get("analysiserPhoto").toString()))
			oma.setAnalysiserPhoto(dto.get("analysiserPhoto2").toString());
		else
			oma.setAnalysiserPhoto(dto.get("analysiserPhoto").toString());
		oma.setAnalysiser(dto.get("analysisPerson").toString());
		oma.setAnalysiserInfo(dto.get("analysisPersonInfo").toString());
		oma.setRemark(dto.get("remark").toString());
		
		return oma;
	}
	
	/**
	 * 删除金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean deleteMarketAnalysis2(IBaseDTO dto) {
		OperMarketAnalysisScreen oma = 
			(OperMarketAnalysisScreen)dao.loadEntity(OperMarketAnalysisScreen.class, dto.get("id").toString());
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
}
