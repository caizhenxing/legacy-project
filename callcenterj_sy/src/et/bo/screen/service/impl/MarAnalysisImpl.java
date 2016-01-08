package et.bo.screen.service.impl;
/**
 * ��ũ�г������ӿ�ʵ����
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
	 * ����idȡ��һ����ũ�г�������Ϣ��ϸ
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
	 * ���ݲ�ѯ�������ؽ�ũ�г�������Ϣ�б�
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
		
		if(result.length < 5){//�����������������ԭ�л������ظ�5��
			
			for(int j = 0; j < 5; j++){
				
				for(int i=0,size=result.length; i<size; i++) {
					OperMarketAnalysis po = (OperMarketAnalysis)result[i];
					
					DynaBeanDTO dto = new DynaBeanDTO();
					
					dto.set("typeTitle", po.getTypeTitle());//����
					dto.set("subTitle", po.getSubTitle());//����
					dto.set("overview", po.getMarketOverview());//�г�����
					dto.set("leaderboard", po.getPriceLeaderbord());//�۸񿴰�
					dto.set("quotes", po.getQuotesAnalysis());//�������
					dto.set("outlook", po.getInvestorsOutlook());//����չ��
					dto.set("remark", po.getInvestorsOutlook());//��ע
					
					dto.set("analysisPerson", po.getAnalysisPerson());//����Ա
					dto.set("analysisPersonInfo", po.getAnalysisPersonInfo());//����Ա���
					
					list.add(dto);
				}	
			}
		
		}else{
			//���²���������ʾ���
			for(int i=0,size=result.length; i<size; i++) {
				OperMarketAnalysis po = (OperMarketAnalysis)result[i];
				
				DynaBeanDTO dto = new DynaBeanDTO();
				
				dto.set("typeTitle", po.getTypeTitle());//����
				dto.set("subTitle", po.getSubTitle());//����
				dto.set("overview", po.getMarketOverview());//�г�����
				dto.set("leaderboard", po.getPriceLeaderbord());//�۸񿴰�
				dto.set("quotes", po.getQuotesAnalysis());//�������
				dto.set("outlook", po.getInvestorsOutlook());//����չ��
				dto.set("remark", po.getInvestorsOutlook());//��ע
				
				dto.set("analysisPerson", po.getAnalysisPerson());//����Ա
				dto.set("analysisPersonInfo", po.getAnalysisPersonInfo());//����Ա���
				
				list.add(dto);
			}
		}
		return list;
	}
	
	public int getMarAnalysisInfoSize() {
		return num;
	}
	
	/**
	 * ��ӽ�ũ�г�������Ϣ
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
	 * �޸Ľ�ũ�г�������Ϣ
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
	 * ɾ����ũ�г�������Ϣ
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
	 * ���ݲ�ѯ�������ؽ�ũ�г�������Ϣ�б� ��ʱ�䵹�� ��ʾͷ����
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
	 * add wwq ����Ļ�õ�ͼƬ��Ϣ
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
		if("".equals(photo)) //Ĭ��ͼƬ
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
	 * ���ݲ�ѯ�������ؽ�ũ�г�������Ϣ�б�
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
	 * ����idȡ��һ����ũ�г�������Ϣ��ϸ
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
	 * ��ӽ�ũ�г�������Ϣ
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
	 * �޸Ľ�ũ�г�������Ϣ
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
	 * ɾ����ũ�г�������Ϣ
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
