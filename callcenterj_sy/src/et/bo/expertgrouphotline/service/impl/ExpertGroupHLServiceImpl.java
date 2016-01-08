package et.bo.expertgrouphotline.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import et.bo.expertgrouphotline.service.ExpertGroupHLService;
import et.po.ExpertgroupHotline;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class ExpertGroupHLServiceImpl implements ExpertGroupHLService {
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
	
	/**
	 * 增加一条专家信息记录
	 * @param dto
	 */
	public void addExperGroupHLinfo(IBaseDTO dto) {
		dao.saveEntity(toExpertOBJ(dto));
	}

	/**
	 * 增加 修改 用toExpertOBJ转换成ExpertgroupHotline对象
	 * @param dto
	 * @return
	 */
	private ExpertgroupHotline toExpertOBJ(IBaseDTO dto) {
		String s = (String)dto.get("ehId");
		
		ExpertgroupHotline eh = null;
		// ehId为空 这是增加操作，需new 一个新对象并设置主键
		if (s == null ||"".equals(s)) {
			eh = new ExpertgroupHotline();
			String id = ks.getNext("expertgroup_hotline");
			eh.setId(id);
		
		} 
		if(s!=null&&!"".equals(s)){
			// 否则是修改操作，只需获得数据库中对象 再做修改
			eh = (ExpertgroupHotline) dao.loadEntity(ExpertgroupHotline.class,s);
			
		}

		// 支持率 form中存的数据必须是数字，不包括％
		s = (String) dto.get("ehAgreeLevel");
		if (s != null && !"".equals(s.trim())) {
			
				Integer i = new Integer(s.trim());
				eh.setAgreelevel(i);
		}
		
		s = (String) dto.get("ehCallName");
		if (s != null && !"".equals(s.trim())) {
			eh.setCallName(s.trim());
		}
		
		s = (String) dto.get("ehExpertPic");
		if (s != null && !"".equals(s.trim())) {
			eh.setExpertPic(s.trim());
		}

		s = (String) dto.get("ehExpertSummary");
		if (s != null && !"".equals(s.trim())) {
			eh.setExpertSummary(s.trim());
		}

		s = (String) dto.get("ehExpertZone");
		if (s != null && !"".equals(s.trim())) {
			eh.setExpertzone(s.trim());
		}

		s = (String) dto.get("ehType");
		if (s != null && !"".equals(s)) {
			eh.setType(s);
		}

		
		return eh;
	}

	/**
	 * 删除一条专家信息
	 * @param id
	 */
	public void delExperGroupHLinfo(String id) {
		ExpertgroupHotline eh = (ExpertgroupHotline) dao.loadEntity(
				ExpertgroupHotline.class, id);
		dao.removeEntity(eh);
	}

	/**
	 * 修改一条专家信息
	 * @param dto
	 * @return boolean
	 */
	public boolean updateExpertGroupHLinfo(IBaseDTO dto) {
		boolean flag = false;
		try {
			dao.updateEntity(toExpertOBJ(dto));
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * 查询符合条件的专家信息
	 * @param dto
	 * @param pi
	 * @return list
	 */
	public List getExperGroupHLinfoList(IBaseDTO dto, PageInfo pi) {
		List li = new ArrayList();
		ExpertGroupHLHelp ehh = new ExpertGroupHLHelp();
		ExpertgroupHotline eh = null;
		Object[] o = (Object[]) dao.findEntity(ehh.sqlStr(dto, pi));
		num = dao.findEntitySize(ehh.sqlStr(dto, pi));
		for (int i = 0; i < o.length; i++) {
			eh = (ExpertgroupHotline) o[i];
			li.add(toDTOObjCut(eh));
		}
		return li;
	}

	/**
	 * 实现字符串截断 getExperGroupHLinfoList 方法专用
	 * @param eh
	 * @return
	 */
	private IBaseDTO toDTOObjCut(ExpertgroupHotline eh) {
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("ehId", eh.getId());
		Integer i = eh.getAgreelevel();
		if (i != null) {
			dto.set("ehAgreeLevel", i.toString() + "%");
		}

		String s = eh.getCallName();
		if (s != null) {
			dto.set("ehCallName", strCut(s));
		}
		s = eh.getExpertPic();
		if (s != null) {
			dto.set("ehExpertPic", strCut(s));
		}
		s = eh.getExpertSummary();
		if (s != null) {
			dto.set("ehExpertSummary", strCut(s));
		}
		s = eh.getExpertzone();
		if (s != null) {
			dto.set("ehExpertZone", strCut(s));
		}
		dto.set("ehType", eh.getType());

		return dto;
	}
	/**
	 * 得到大屏幕下 热线专家团的列表
	 * @param type 农民 政府
	 * @return List<DynaBeanDTO>
	 */
	public List<DynaBeanDTO> getScreenExpertList(String type)
	{
		List<DynaBeanDTO> ls = new ArrayList<DynaBeanDTO>();
		String sql = "select top 20 * from Expertgroup_Hotline where type like '%"+type+"%'order by id desc";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		DynaBeanDTO dto = null;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				dto = new DynaBeanDTO();
				dto.set("ehId", rs.getString("id"));
				String i = rs.getString("agreelevel");
					dto.set("ehAgreeLevel", getStr(i) + "%");

				String s = rs.getString("call_name");
					dto.set("ehCallName", getStr(s));
				s = rs.getString("expert_pic");
					dto.set("ehExpertPic", getStr(s)==""?"noImg":getStr(s));
				s = rs.getString("expert_summary");
					dto.set("ehExpertSummary", getStr(s));
				s = rs.getString("expertzone");
					dto.set("ehExpertZone", getStr(s));
				dto.set("ehType", rs.getString("type"));
				ls.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}
	private String getStr(String s)
	{
		if(s==null)
		{
			return "";
		}
		return s.trim();
	}
	private String strCut(String s) {
		if (s.length() > 15) {
			s = s.substring(0, 14) + "...";
		}
		return s;
	}

	/**
	 * 
	 * @return
	 */
	public int getRecordSize() {
		return num;
	}



	/**
	 * 根据id查找特定专家信息
	 * @param id
	 * @return IBaseDTO
	 */
	public IBaseDTO getExpertHotLineById(String id) {
		ExpertgroupHotline eh = (ExpertgroupHotline) dao.loadEntity(
				ExpertgroupHotline.class, id);
		return toDTOObj(eh);
	}

	private IBaseDTO toDTOObj(ExpertgroupHotline eh) {
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("ehId", eh.getId());
		
		Integer i = eh.getAgreelevel();
		if (i != null) {
			dto.set("ehAgreeLevel", i.toString());
		}
		String s = eh.getCallName();
		if (s != null) {
			dto.set("ehCallName", s);
		}
		s = eh.getExpertPic();
		if (s != null) {
			dto.set("ehExpertPic", s);
		}
		s = eh.getExpertSummary();
		if (s != null) {
			dto.set("ehExpertSummary", s);
		}
		s = eh.getExpertzone();
		if (s != null) {
			dto.set("ehExpertZone", s);
		}
		dto.set("ehType", eh.getType());
		return dto;
	}
	
	public List screenList() {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(ExpertgroupHotline.class);
		
		dc.addOrder(Order.desc("id"));
		
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(20);
		
		Object[] result = (Object[]) dao.findEntity(mq);
		
		for (int i = 0, size = result.length; i < size; i++) {
			ExpertgroupHotline oci = (ExpertgroupHotline) result[i];
			l.add(toDTOObj(oci));
//			toDTOObj(oci);
//			DynaBeanDTO dbd = new DynaBeanDTO();
//			dbd.set("hotlineContent", oci.getHotlineContent());
//			l.add(dbd);
		}
		return l;

	}	
}
