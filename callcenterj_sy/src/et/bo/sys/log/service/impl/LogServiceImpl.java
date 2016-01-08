package et.bo.sys.log.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.bo.sys.log.service.LogService;
import et.po.SysLog;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

public class LogServiceImpl implements LogService {

	private BaseDAO dao = null;

	private ClassTreeService cts = null;

	private KeyService ks = null;

	private IBaseDTO createDtoByPo(SysLog sl) {
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("id", sl.getId());
		dto.set("sysUser", sl.getSysUser().getUserId());
		dto.set("sysModule", sl.getModu());
		dto.set("dt", TimeUtil.getTheTimeStr(sl.getOperTime()));
		dto.set("actorType",sl.getOper());
		dto.set("remark", sl.getRemark());
		dto.set("ip", sl.getIp());
		return dto;
	}

	public List<IBaseDTO> listLog(IBaseDTO dto, PageInfo pi) {
		// TODO Auto-generated method stub
		LogHelp lh = new LogHelp();
		Object[] o = this.dao.findEntity(lh.listLogMQ(dto, pi));

		ArrayList l = new ArrayList();
		if (null != o && o.length > 0) {
			for (Object oo : o) {
				IBaseDTO tdto = createDtoByPo((SysLog) oo);
				l.add(tdto);

			}
			return l;
		}
		return l;
	}

	public int listLogSize(IBaseDTO dto) {
		// TODO Auto-generated method stub
		LogHelp lh = new LogHelp();
		MyQuery mq = lh.listLogMQ(dto);
		int i = dao.findEntitySize(mq);
		return i;
	}

	public void deleteLog(String id) {
		// TODO Auto-generated method stub

	}

	public void deleteLog(IBaseDTO dto) {
		// TODO Auto-generated method stub

	}

	public void test() {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(SysLog.class);
		dc.add(Expression.ge("dt", TimeUtil.getTimeByStr("2000-01-01",
				"yyyy-MM-dd")));
		SysUser su = new SysUser();
		su.setUserId("1");
		dc.add(Expression.eq("sysUser", su));
		mq.setDetachedCriteria(dc);
		Object[] o = this.dao.findEntity(mq);

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

	public void addLog(List logs) {
		// TODO Auto-generated method stub

		Iterator<IBaseDTO> i = logs.iterator();
		while (i.hasNext()) {
			IBaseDTO dto = i.next();
			SysLog sl = new SysLog();
			dto.loadValue(sl);
			sl.setId(ks.getNext("SYS_LOG"));
			// sl.setDt(TimeUtil.getNowTime());
			sl.setSysUser((SysUser) dao.loadEntity(SysUser.class, (String) dto
					.get("user")));

			dao.saveEntity(sl);
		}
		dao.flush();
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

}
