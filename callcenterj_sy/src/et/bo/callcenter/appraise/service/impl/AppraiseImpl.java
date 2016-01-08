package et.bo.callcenter.appraise.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import et.bo.callcenter.appraise.service.AppraiseService;
import et.bo.common.CommonQuerySql;
import et.po.CcUserAppraiseInfo;
import et.po.SysUser;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * 
 * @author chen gang
 *
 */
public class AppraiseImpl implements AppraiseService {
	static Logger log = Logger.getLogger(AppraiseImpl.class.getName());
	
	private BaseDAO dao = null;
	
	private int APPRAISE_NUM;

	public List appQuery(IBaseDTO dto, PageInfo pi) {
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		AppraiseHelp ah = new AppraiseHelp();
		Object result[] = dao.findEntity(ah.appQuery(dto, pi));
		APPRAISE_NUM = dao.findEntitySize(ah.appQuerySize(dto, pi));
		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				CcUserAppraiseInfo cai = (CcUserAppraiseInfo)result[i];
				list.add(CcUserAppraiseInfoToDynaBeanDTO(cai));
			}
		}
		return list;
	}
	
	private DynaBeanDTO CcUserAppraiseInfoToDynaBeanDTO(
			CcUserAppraiseInfo pf) {
		DynaBeanDTO dbd = new DynaBeanDTO();
		dbd.set("id", pf.getId().toString());
		dbd.set("appType", pf.getType());
		dbd.set("telNum", pf.getCallerid());
		dbd.set("appResult", pf.getUserAppraise());
		dbd.set("appraiseObject", pf.getAppraiseNum());
		dbd.set("endTime", pf.getAppraiseTime());

		return dbd;
	}
	
	public List userQuery()
	{
		String sql = CommonQuerySql.AGENTWORKQUERYSQL;
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<SysUser> list=new ArrayList<SysUser>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				SysUser su=new SysUser();
				su.setUserId(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public int getAppSize() {
		return APPRAISE_NUM;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	
}
