/**
 * 	@(#)OperatorListenImpl.java   Oct 8, 2006 4:01:22 PM
 *	 ¡£ 
 *	 
 */
package et.bo.pcc.operatorlisten.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import et.bo.callcenter.ConstantsI;
import et.bo.pcc.operatorlisten.OperatorListenService;
import et.po.CcLog;
import et.po.PoliceCallin;
import et.po.SysGroup;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Oct 8, 2006
 * @see
 */
public class OperatorListenServiceImpl implements OperatorListenService {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private ClassTreeService ctree = null;

	private int OPERATOR_LISTEN_NUM = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.pcc.operatorlisten.OperatorListen#getOperatorListenSize()
	 */
	public int getOperatorListenSize() {
		// TODO Auto-generated method stub
		return OPERATOR_LISTEN_NUM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.pcc.operatorlisten.OperatorListen#operatorListenIndex(excellence.framework.base.dto.IBaseDTO,
	 *      excellence.common.page.PageInfo)
	 */
	public List operatorListenIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		OperatorListenSearch olSearch = new OperatorListenSearch();
		Object[] result = null;
		try {
			result = (Object[]) dao.findEntity(olSearch.searchOperatorListen(
					dto, pageInfo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int s = dao
				.findEntitySize(olSearch.searchOperatorListen(dto, pageInfo));
		OPERATOR_LISTEN_NUM = s;
		for (int i = 0, size = result.length; i < size; i++) {
			PoliceCallin policeCallin = (PoliceCallin) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			String policeCallinId = policeCallin.getId();
			dbd.set("id", policeCallinId);
			CcLog ccLog = (CcLog) dao.loadEntity(CcLog.class, policeCallinId);
			dbd.set("phonenum", ccLog.getPhoneNum());
			dbd.set("fuzzno", policeCallin.getFuzzNo());
			dbd.set("begintime", TimeUtil.getTheTimeStr(ccLog.getBeginTime(),
					"yyyy-MM-dd"));
			dbd.set("endtime", TimeUtil.getTheTimeStr(ccLog.getEndTime(),
					"yyyy-MM-dd"));
			String reccordFront = "http://"
					+ ctree.getvaluebyNickName(ConstantsI.CLIENT_IP) + "/";
			dbd.set("recfile", reccordFront + ccLog.getRecFile());
			l.add(dbd);
		}
		return l;
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

	public List<LabelValueBean> userlist() {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		SysGroup sg = (SysGroup) dao.loadEntity(SysGroup.class, "operator");
		OperatorListenSearch olSearch = new OperatorListenSearch();
		Object[] result = (Object[]) dao
				.findEntity(olSearch.searchUserInfo(sg));
		for (int i = 0, size = result.length; i < size; i++) {
			SysUser sysUser = (SysUser) result[i];
			l
					.add(new LabelValueBean(sysUser.getUserName(), sysUser
							.getUserId()));
		}
		return l;
	}

	public ClassTreeService getCtree() {
		return ctree;
	}

	public void setCtree(ClassTreeService ctree) {
		this.ctree = ctree;
	}

}
