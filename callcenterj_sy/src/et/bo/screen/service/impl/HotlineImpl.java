package et.bo.screen.service.impl;
/**
 * ��ũ�г������ӿ�ʵ����
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
	 * ����idȡ��һ����ũ�г�������Ϣ��ϸ
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
	 * ���ݲ�ѯ�������ؽ�ũ�г�������Ϣ�б�
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
	 * �޸Ľ�ũ�г�������Ϣ
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
	 * ɾ����ũ�г�������Ϣ
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
