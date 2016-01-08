/*
 * @(#)FlowServiceImpl.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.flow.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.flow.service.FlowService;
import et.bo.sys.common.SysStaticParameter;
import et.po.OperCaseinfo;
import et.po.OperFlow;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * <p>
 * 客户管理
 * </p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public class FlowServiceImpl implements FlowService {

	// 标记状态为已读
	private static final String STATE_READED = "READED";
	// 标记为未读
	private static final String STATE_NOTREAD = "NOTREAD";

	BaseDAO dao = null;
	private int num = 0;

	private BasicDataSource bds = null;
	public KeyService ks = null;

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	public BasicDataSource getBds() {
		return bds;
	}

	public void setBds(BasicDataSource bds) {
		this.bds = bds;
	}

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	/**
	 * 根据传参判断审核表里是否存在该审核记录。 根据传入的选择状态，判断操作情形。 如果存在则返回当前记录状态，如果不存在，则添加审核记录。
	 * 
	 * @param type
	 *            信息库类别名
	 * @param entry_id
	 *            要审核记录的ID是什么什么
	 * @param nowstate
	 *            传入的当前状态是什么
	 * @param subid
	 *            提交人ID
	 */
	public void addOrUpdateFlow(String type, String entry_id, String nowstate,
			String subid, String autding) {
		if (nowstate == null) {
			nowstate = "原始";
		}
		String power = type;
		if (type.equals("焦点追踪库") || type.equals("市场分析库")) {
			if (nowstate.equals("原始")) {
				nowstate = "初稿";
			}
			if (nowstate.equals("一审驳回") || nowstate.equals("初稿")) {
				// power += "一审";
			} else if (nowstate.equals("二审驳回") || nowstate.equals("一审")) {
				// power += "二审";
				power += "一审";
			} else if (nowstate.equals("三审驳回") || nowstate.equals("二审")) {
				// power += "三审";
				power += "二审";
			}
		}
		// 如果不是原始状态，则进行下一步的处理
		if (!nowstate.equals("原始") && !nowstate.equals("初稿")) {

			OperFlow po = (OperFlow) dao.loadEntity(OperFlow.class, entry_id);
			// 如果entry_id得到的对象为空则表示进行了提交的操作
			if (po == null) {

				po = new OperFlow();
				po.setFlowId(entry_id);
				po.setType(type);
				po.setPower(power);
				po.setSubmitId(subid);// 首次提交人
				// zhang feng update
				po.setSubmitIdEnd(autding);// 最后也是最近提交的是谁
				po.setSubmitTime(TimeUtil.getNowTime());// 首次提交时间
				po.setSubmitTimeEnd(TimeUtil.getNowTime());// 最后提交时是
				po.setState(nowstate);
				po.setIsRead(STATE_NOTREAD);
				dao.saveEntity(po);

			} else {
				// 如果有值，则进行修改操作
				if (!nowstate.equals(po.getState())) {// 只有当状态改变时，才操作审核表
					po.setPower(power);

					po.setSubmitId(subid);// 首次提交人
					// zhang feng update
					po.setSubmitIdEnd(autding);// 提交给谁进行处理
					po.setSubmitTimeEnd(TimeUtil.getNowTime());
					po.setState(nowstate);
					po.setIsRead(STATE_NOTREAD);
					dao.saveEntity(po);
				}
			}
		}
	}

	/**
	 * 根据类别判断具有审核权限的人员的信息
	 * 
	 * @param type
	 *            要审核的类别名称，如普通案例库
	 * @return
	 */
	// private String getSubidByType(String type) {
	// String resultType = "";
	// MyQuery mq = new MyQueryImpl();
	// DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
	// dc.add(Restrictions.like("auditing", "%" + type + "%"));
	// mq.setDetachedCriteria(dc);
	// Object[] result = dao.findEntity(mq);
	// for (int i = 0, size = result.length; i < size; i++) {
	// SysUser su = (SysUser) result[i];
	// resultType = su.getUserId();
	// }
	// return resultType;
	// }
	// public String subid2power(String subid) {
	// String auditing = "";
	// String sql = "select auditing,role_id from sys_user where user_id = '"
	// + subid + "'";
	// try {
	// RowSet rs = dao.getRowSetByJDBCsql(sql);
	// if (rs.next()) {
	// auditing = rs.getString(1);
	// String role_id = rs.getString(2);
	// if (role_id != null && role_id.equals("administrator")) {
	// auditing = "administrator";
	// }
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return auditing;
	// }
	/**
	 * 添加数据。 向数据库中添加一条记录。
	 * 
	 * @param dto
	 *            新数据的excellence.framework.base.dto.IBaseDTO对象
	 */
	public void addFlow(IBaseDTO dto) {

		OperFlow po = new OperFlow();

		po.setFlowId(ks.getNext("oper_flow"));
		po.setSubmitId(dto.get("submit_id").toString());
		po.setSubmitTime(TimeUtil.getTimeByStr(dto.get("submit_time")
				.toString(), "yyyy-MM-dd"));

		dao.saveEntity(po);

	}

	/**
	 * 查询数据列表,返回记录的list。 取得查询问题列表数据。
	 * 
	 * @param dto
	 *            数据传输对象
	 * @param pi
	 *            页面信息
	 * @return 数据的list
	 */
	public List flowQuery(IBaseDTO dto, PageInfo pi) {

		List list = new ArrayList();
		FlowHelp h = new FlowHelp();

		Object[] result = (Object[]) dao.findEntity(h.flowQuery(dto, pi));
		num = dao.findEntitySize(h.flowQuery(dto, pi));

		for (int i = 0, size = result.length; i < size; i++) {
			OperFlow po = (OperFlow) result[i];
			list.add(flowToDynaBeanDTO(po));
		}
		return list;
	}

	/**
	 * 查询列表方法的 po 转 dto
	 * 
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO flowToDynaBeanDTO(OperFlow po) {

		DynaBeanDTO dto = new DynaBeanDTO();

		dto.set("flow_id", po.getFlowId());
		dto.set("type", po.getType());
		dto.set("submit_id", po.getSubmitId());
		dto.set("submit_time", TimeUtil.getTheTimeStr(po.getSubmitTime(),
				"yyyy-MM-dd HH:mm:ss"));
		dto.set("submit_id_end", po.getSubmitIdEnd());
		dto.set("submit_time_end", TimeUtil.getTheTimeStr(
				po.getSubmitTimeEnd(), "yyyy-MM-dd HH:mm:ss"));
		dto.set("state", po.getState());

		String is_read = po.getIsRead();
		if (is_read != null && is_read.equals("1")) {
			is_read = "<font color=\"#0000FF\">已操作</font>";
		} else {
			is_read = "<font color=\"#FF0000\">未操作</font>";
		}
		dto.set("is_read", is_read);
		return dto;
	}

	/**
	 * 查询数据列表的条数。 取得问题查询列表的条数。
	 * 
	 * @return 得到list的条数
	 */
	public int getFlowSize() {
		return num;
	}

	/**
	 * 根据ID取得一条数据的excellence.framework.base.dto.IBaseDTO对象 取得某条数据的详细信息。
	 * 
	 * @param id
	 *            取得excellence.framework.base.dto.IBaseDTO的标识
	 * @return 包含数据信息的excellence.framework.base.dto.IBaseDTO对象
	 */
	public IBaseDTO getFlowInfo(String id) {

		OperFlow po = (OperFlow) dao.loadEntity(OperFlow.class, id);
		IBaseDTO dto = new DynaBeanDTO();

		// dto.set("flow_id", po.getFlowId());
		// dto.set("type_id", po.getTypeId());
		// dto.set("entry_id", po.getEntryId());
		// dto.set("submit_id", po.getSubmitId());
		// dto.set("accept_id", po.getAcceptId());
		// dto.set("dict_flow_state", po.getDictFlowState());
		// dto.set("remark",po.getRemark());
		//		
		// String submit_time =
		// DateFormat.getDateInstance().format(po.getSubmitTime());
		// dto.set("submit_time", submit_time);
		//		
		// String handle_time =
		// DateFormat.getDateInstance().format(po.getHandleTime());
		// dto.set("handle_time", handle_time);

		return dto;

	}

	/**
	 * 修改数据。 修改某条记录的内容。
	 * 
	 * @param dto
	 *            要更新的的excellence.framework.base.dto.IBaseDTO对象
	 * @return boolean
	 */
	public boolean updateFlow(IBaseDTO dto) {

		OperFlow po = (OperFlow) dao.loadEntity(OperFlow.class, dto.get(
				"flow_id").toString());// 根据主键修改表

		String type_id = dto.get("type_id").toString();
		String entry_id = dto.get("entry_id").toString();
		String dict_flow_state = dto.get("dict_flow_state").toString();

		String table = "";
		String field = "";
		String idName = "";
		// 根据type_id判断来源于哪个表，要修改哪个表的哪个字段
		if (type_id.equals("oper_caseinfo_putong")
				|| type_id.equals("oper_caseinfo_FocusCase")
				|| type_id.equals("oper_caseinfo_HZCase")
				|| type_id.equals("oper_caseinfo_effectCase")) { // 这个是案例库的

			table = "oper_caseinfo";
			field = "dict_case_state";
			idName = "case_id";

		} else if (type_id.equals("oper_priceinfo")) { // 价格库
			table = "oper_priceinfo";
			field = "dict_case_state";
			idName = "price_id";
		} else if (type_id.equals("oper_sadinfo")) { // 供求库
			table = "oper_sadinfo";
			field = "dict_sad_state";
			idName = "sad_id";
		} else if (type_id.equals("oper_medicinfo")) { // 医疗
			table = "oper_medicinfo";
			field = "dict_state";
			idName = "id";
		} else if (type_id.equals("oper_book_medicinfo")) { // 预约医疗
			table = "oper_book_medicinfo";
			field = "dict_state";
			idName = "id";
		} else if (type_id.equals("oper_corpinfo")) { // 企业
			table = "oper_corpinfo";
			field = "dict_sate";
			idName = "id";
		} else if (type_id.equals("oper_focusinfo")) { // 焦点追踪
			table = "oper_focusinfo";
			field = "dict_focus_state";
			idName = "focus_id";
		} else if (type_id.equals("oper_markanainfo")) { // 市场分析
			table = "oper_markanainfo";
			field = "dict_markana_state";
			idName = "markana_id";
		}

		String sql = "update " + table + " set " + field + " = '"
				+ dict_flow_state + "' where " + idName + " = '" + entry_id
				+ "'";
		executeUpdate(sql);
		// 只有备注和状态和受理人是可以修改的
		// po.setTypeId(type_id);
		// po.setEntryId(entry_id);
		// po.setSubmitId(dto.get("submit_id").toString());
		// po.setSubmitTime(TimeUtil.getTimeByStr(dto.get("submit_time").toString(),
		// "yyyy-MM-dd"));
		// po.setAcceptId(dto.get("accept_id").toString());
		// //po.setHandleTime(TimeUtil.getTimeByStr(dto.get("handle_time").toString(),
		// "yyyy-MM-dd"));
		// po.setDictFlowState(dict_flow_state);
		// po.setRemark(dto.get("remark").toString());

		try {
			dao.saveEntity(po);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除数据。 删除某条记录。
	 * 
	 * @param id
	 *            要删除数据的标识
	 */
	public void delFlow(String id) {

		OperFlow cq = (OperFlow) dao.loadEntity(OperFlow.class, id);
		dao.removeEntity(cq);

	}

	/**
	 * 标记删除。 标记字段"IS_DELETE"，为"1"时为删除，为"0"时未删除。实际上这个方法执行的是修改"IS_DELETE"字段的操作。
	 * 
	 * @param id
	 *            要标记删除数据的标识
	 */
	public boolean isDelete(String id) {

		OperFlow po = (OperFlow) dao.loadEntity(OperFlow.class, id);// 根据主键修改表
		// po.setIsDelete("1"); //"1"是已删除的意思

		try {
			dao.saveEntity(po);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 * @return i 返回int
	 */
	public int executeUpdate(String sql) {

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int i = stmt.executeUpdate(sql);

			return i;

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		return 0;

	}

	// 用户列表模型为 select提供数据
	public List<LabelValueBean> getUserList() {
		List<LabelValueBean> bList = new ArrayList<LabelValueBean>();
		String hql = SysStaticParameter.QUERY_AGENT_SQL;
		MyQuery mq = new MyQueryImpl();
		mq.setHql(hql);
		LabelValueBean bean = null;
		SysUser su = null;
		Object[] arrs = dao.findEntity(mq);
		for (int i = 0; i < arrs.length; i++) {
			su = (SysUser) arrs[i];
			bean = new LabelValueBean();
			bean.setLabel(su.getUserName());
			bean.setValue(su.getUserId());
			bList.add(bean);
		}
		return bList;
	}

	/**
	 * zhang feng add 判断此条记录是否已经读过，如果读过则不进行处理
	 */
	public String isRead(String operId) {
		String result = "";
		OperFlow of = (OperFlow) dao.loadEntity(OperFlow.class, operId);
		result = of.getIsRead();
		return result;
	}

	/**
	 * zhang feng add 将标志状态改变为已读状态
	 */
	public void modifyRead(String operId) {
		OperFlow of = (OperFlow) dao.loadEntity(OperFlow.class, operId);
		of.setIsRead("1");
		dao.saveEntity(of);
	}
}
