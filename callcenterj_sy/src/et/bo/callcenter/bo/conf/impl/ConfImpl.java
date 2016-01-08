/**
 * 沈阳卓越科技有限公司
 * 2008-6-10
 */
package et.bo.callcenter.bo.conf.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import et.bo.callcenter.bo.conf.ConfService;
import et.po.EasyConfChannelState;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang feng
 * 
 */
public class ConfImpl implements ConfService {

	private BaseDAO dao = null;

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.callcenter.bo.conf.ConfService#confDeployList()
	 */
	public List confDeployList(String roomno) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		ConfSearch cs = new ConfSearch();
		Object[] result = (Object[]) dao.findEntity(cs.confdeploy(roomno));

		for (int i = 0; i < result.length; i++) {
			EasyConfChannelState etc = (EasyConfChannelState) result[i];
			if("Y".equals(etc.getDeleteMark()))
			{
				String newStateIcon="";
				String curStateIcon="";
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("id", etc.getId());
				dbd.set("channo", etc.getChannelno());
				dbd.set("callid", etc.getCallerid());
				dbd.set("roomid", etc.getRoomno());
				int currentState = etc.getCurrentstate(); //当前状态（１、能听能说；２、只听不说；３、挂机退出会议）
				String state = "";
				if (currentState == 1) {
					state = "能听能说";
					curStateIcon="newListenAndSay.gif";
				} else if (currentState == 2) {
					state = "只听不说";
					curStateIcon="newOnlyListen.gif";
				} else if (currentState == 3) {
					state = "挂机退出会议";
					curStateIcon="newExitConf.gif";
				}
				dbd.set("curStateIcon", curStateIcon);
				dbd.set("charroomstate", state);
				int iNewState = etc.getNewstate(); //申请新状态（１、能听能说；２、只听不说；３、挂机退出会议）
				String newState = "";
				if (iNewState == 1) {
					newState = "能听能说";
					newStateIcon="newListenAndSay.gif";
				} else if (iNewState == 2) {
					newState = "只听不说";
					newStateIcon="newOnlyListen.gif";
				} else if (iNewState == 3) {
					newState = "挂机退出会议";
					newStateIcon="newExitConf.gif";
				}
				dbd.set("newStateIcon",newStateIcon);
				dbd.set("newstate", newState);
				//allowflag 审批状态（null、无申请要求；0、未审批；1、已审批）
				String allowFlag = etc.getAllowflag();
				String parseFlag = null;
				if(allowFlag==null||"".equals(allowFlag))
				{
					parseFlag = "无申请要求";
				}
				else if("0".equals(allowFlag))
				{
					parseFlag="未审批";
				}
				else if("1".equals(allowFlag))
				{
					parseFlag="已审批";
				}
				dbd.set("allowflag", parseFlag);
				l.add(dbd);
			}
		}
		return l;
	}

	public void operConf(String id, String state) {
		// TODO Auto-generated method stub
		// System.out.println(id + " ... " + state);
		// EasyTqChannelState etc =
		// (EasyTqChannelState)dao.loadEntity(EasyTqChannelState.class, id);
		String sql = "update EasyConf_ChannelState set Newstate = " + state
				+ " where id = " + id + "";
		// etc.setNewstate(Integer.parseInt(state));
		// dao.updateEntity(etc);
		dao.execute(sql);
	}

	public List<LabelValueBean> getAllConfList() {
		// TODO Auto-generated method stub
		List<LabelValueBean> l = new ArrayList<LabelValueBean>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT roomno FROM EasyConf_ChannelState WHERE (currentstate = 1) OR (currentstate = 2) AND (Newstate <> 3) and delete_mark='Y' ");//增加删除标记，N:已删除；Y有效数据；

		RowSet rs = dao.getRowSetByJDBCsql(sql.toString());
		
		try {
			rs.beforeFirst();
			while (rs.next()) {
				LabelValueBean lv = new LabelValueBean();
				String roomno = rs.getObject(1).toString();
				lv.setLabel(roomno);
				lv.setValue(roomno);
				l.add(lv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

}
