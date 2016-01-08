package et.bo.focusTracking.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import et.bo.focusTracking.service.FocusTrackingService;

import et.po.FocusTracking;

import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class FocusTrackingServiceImpl implements FocusTrackingService {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private int num = 0;

	public static HashMap hashmap = new HashMap();

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

	public void addFocusTracking(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.saveEntity(createFocusTracking(dto));
	}

	private FocusTracking createFocusTracking(IBaseDTO dto) {
		FocusTracking ft = new FocusTracking();
		String id = ks.getNext("FocusTracking");
		ft.setFtId(id);

		ft.setFtCreateTime(TimeUtil.getTimeByStr((String) dto
				.get("ftCreateTime"), "yyyy-MM-dd"));
		ft.setFtCreateUser((String) dto.get("ftCreateUser"));
		ft.setFtIsDel(0);
		ft.setFtPeriod((String) dto.get("ftPeriod"));
		ft.setFtSummary((String) dto.get("ftSummary"));
		ft.setFtTitle((String) dto.get("ftTitle"));

		return ft;
	}

	public void delFocusTracking(String id) {
		FocusTracking ft = (FocusTracking) dao.loadEntity(FocusTracking.class,id);
		ft.setFtIsDel(1);
		dao.updateEntity(ft);
//		dao.removeEntity(ft);
	}

	public IBaseDTO getFocusTracking(String id) {
		// TODO Auto-generated method stub
		FocusTracking ft = (FocusTracking) dao.loadEntity(FocusTracking.class,
				id);
		IBaseDTO dto = new DynaBeanDTO();

		dto.set("ftId", ft.getFtId());
		dto.set("ftPeriod", ft.getFtPeriod());
		dto.set("ftTitle", ft.getFtTitle());
		dto.set("ftSummary", ft.getFtSummary());
		dto.set("ftCreateTime", TimeUtil.getTheTimeStr(ft.getFtCreateTime(),
				"yyyy-MM-dd"));
		dto.set("ftCreateUser", ft.getFtCreateUser());

		return dto;
	}

	public int getFocusTrackingSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public void updateFocusTracking(IBaseDTO dto) {
		// TODO Auto-generated method stub
		dao.updateEntity(modifyFocusTracking(dto));
	}

	private FocusTracking modifyFocusTracking(IBaseDTO dto) {
		FocusTracking ft = (FocusTracking) dao.loadEntity(FocusTracking.class,
				dto.get("ftId").toString());

		ft.setFtCreateTime(TimeUtil.getTimeByStr((String) dto
				.get("ftCreateTime"), "yyyy-MM-dd"));
		ft.setFtCreateUser((String) dto.get("ftCreateUser"));
		ft.setFtPeriod((String) dto.get("ftPeriod"));
		ft.setFtSummary((String) dto.get("ftSummary"));
		ft.setFtTitle((String) dto.get("ftTitle"));

		return ft;
	}

	public List focusTrackingQuery(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		FocusTrackingHelp fph = new FocusTrackingHelp();
		MyQuery mq = fph.focusTrackingQuery(dto, pi);
		Object[] result = (Object[]) dao.findEntity(mq);
		num = dao.findEntitySize(mq);
		for (int i = 0, size = result.length; i < size; i++) {
			FocusTracking ft = (FocusTracking) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();

			dbd.set("ftId", ft.getFtId());
			dbd.set("ftPeriod", ft.getFtPeriod());
			dbd.set("ftTitle", ft.getFtTitle());
			dbd.set("ftSummary", ft.getFtSummary());

			list.add(dbd);
		}

		return list;
	}
	
	public List screenList() {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(FocusTracking.class);
		dc.add(Restrictions.eq("ftIsDel", 0));
		dc.addOrder(Order.desc("ftCreateTime"));		
		mq.setDetachedCriteria(dc);
		mq.setFirst(0);
		mq.setFetch(20);
		
		Object[] result = (Object[]) dao.findEntity(mq);
		
		for (int i = 0, size = result.length; i < size; i++) {
			FocusTracking ft = (FocusTracking) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("period", ft.getFtPeriod());
			dbd.set("title", ft.getFtTitle());
			dbd.set("summary", ft.getFtSummary());
			l.add(dbd);
		}
		return l;

	}	

}
