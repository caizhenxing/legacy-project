package et.bo.screen.service.impl;
/**
 * 金农市场分析接口实现类
 * @author wwq
 */
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import et.bo.screen.service.ScreenDoctorService;
import et.po.OperHotline;
import et.po.OperMarketAnalysis;
import et.po.ScreenDoctor;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ScreenDoctorImpl implements ScreenDoctorService {
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
		ScreenDoctor oma = (ScreenDoctor)dao.loadEntity(ScreenDoctor.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", oma.getId());
		String type = "healthcare".equals(oma.getType())?"医疗保健常识":"普通疾病识别防治方法与措施";//其值为 healthcare代表 医疗保健常识；其值为prevention代表普通疾病识别防治方法与措施
		dto.set("type", type);
		dto.set("docContent", oma.getDocContent());
		dto.set("addtime", TimeUtil.getTheTimeStr(oma.getAddtime(),"yyyy-mm-dd"));
		
		return dto;
	}
	
	/**
	 * 根据查询条件返回金农市场分析信息列表
	 * @param dto
	 * @param pi
	 * @return
	 */
	public List marketAnalysisInfoQuery(IBaseDTO dto, PageInfo pi) {
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		ScreenDoctorHelp mah = new ScreenDoctorHelp();
		
		Object[] result = dao.findEntity(mah.marketInfoQuery(dto, pi));
		num = dao.findEntitySize(mah.marketInfoQuery(dto, pi));
		DynaBeanDTO dbd = null;
		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				ScreenDoctor oma = (ScreenDoctor)result[i];
				dbd = new DynaBeanDTO();
				dbd.set("id", oma.getId());
				
				String type = "healthcare".equals(oma.getType())?"医疗保健常识":"普通疾病识别防治方法与措施";//其值为 healthcare代表 医疗保健常识；其值为prevention代表普通疾病识别防治方法与措施
				dbd.set("docType", type);
				//System.out.println(type);
				dbd.set("docContent", oma.getDocContent());
				//System.out.println(type+"##"+oma.getDocContent());
				list.add(dbd);
			}
		}
		//System.out.println(list.get(0).get("id")+"###"+list.size()+list.get(0).get("docType"));
		
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
			dao.saveEntity(createDoctor(dto));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private ScreenDoctor createDoctor(IBaseDTO dto) {
		ScreenDoctor oma = new ScreenDoctor();
		oma.setAddtime(new java.util.Date());
		oma.setType((String)dto.get("docType"));
		oma.setDocContent((String)dto.get("docContent"));
		oma.setAddtime(new java.util.Date());
		oma.setId(ks.getNext("ScreenDoctor"));
		return oma;
	}
	
	/**
	 * 修改金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean updateMarketAnalysis(IBaseDTO dto) {
		try {
			dao.updateEntity(modifyDoctor(dto));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private ScreenDoctor modifyDoctor(IBaseDTO dto) {
		ScreenDoctor oma = 
			(ScreenDoctor)dao.loadEntity(ScreenDoctor.class, dto.get("id").toString());
		
		oma.setType((String)dto.get("docType"));
		oma.setDocContent((String)dto.get("docContent"));
		oma.setAddtime(new java.util.Date());
		
		return oma;
	}
	
	/**
	 * 删除金农市场分析信息
	 * @param dto
	 * @return
	 */
	public boolean deleteMarketAnalysis(IBaseDTO dto) {
		ScreenDoctor oma = 
			(ScreenDoctor)dao.loadEntity(ScreenDoctor.class, dto.get("id").toString());
		
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
	public List<DynaBeanDTO> getHealthcareList(int size)
	{
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		ScreenDoctorHelp sdh = new ScreenDoctorHelp();
		Object[] os = dao.findEntity(sdh.marketInfoQuery(size,"healthcare"));
		int count=0;
		int len = os.length;
		for(int i=0; i<len; i++)
		{
			count++;
			ScreenDoctor oma = (ScreenDoctor)os[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", oma.getId());
			
			String type = "healthcare".equals(oma.getType())?"医疗保健常识":"普通疾病识别防治方法与措施";//其值为 healthcare代表 医疗保健常识；其值为prevention代表普通疾病识别防治方法与措施
			dbd.set("docType", type);
			//System.out.println(type);
			dbd.set("docContent", oma.getDocContent());
			//System.out.println(type+"##"+oma.getDocContent());
			list.add(dbd);
		}
		
		add2Size(list,size);
		return list;
	}
	private void add2Size(List<DynaBeanDTO> list,int size)
	{
		int len = list.size();
		if(len==0)
			return;
		if(len<size)
		{
			for(int i=0;i<len;i++)
			{
				len = list.size();
				if(len==size)
					return;
				list.add(list.get(i));
			}
			add2Size(list,size);
		}
	}
	public List<DynaBeanDTO> getPreventionList(int size)
	{
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		ScreenDoctorHelp sdh = new ScreenDoctorHelp();
		Object[] os = dao.findEntity(sdh.marketInfoQuery(size,"prevention"));
		int count=0;
		int len = os.length;
		for(int i=0; i<len; i++)
		{
			count++;
			ScreenDoctor oma = (ScreenDoctor)os[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", oma.getId());
			
			String type = "healthcare".equals(oma.getType())?"医疗保健常识":"普通疾病识别防治方法与措施";//其值为 healthcare代表 医疗保健常识；其值为prevention代表普通疾病识别防治方法与措施
			dbd.set("docType", type);
			//System.out.println(type);
			dbd.set("docContent", oma.getDocContent());
			//System.out.println(type+"##"+oma.getDocContent());
			list.add(dbd);
		}
		add2Size(list,size);
		return list;
	}
	public List<DynaBeanDTO> getScreenList(int size)
	{
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();

		list = getHealthcareList(20);
		List<DynaBeanDTO> listPrevent =  getPreventionList(20);
		for(int i=0; i<size; i++)
		{
			list.get(i).set("docContent1", (String)listPrevent.get(i).get("docContent"));
		}
		
		return list;
	}
	
}
