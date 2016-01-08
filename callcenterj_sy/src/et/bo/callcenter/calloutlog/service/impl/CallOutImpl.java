/**
 * 
 */
package et.bo.callcenter.calloutlog.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import et.bo.callcenter.calloutlog.service.CallOutService;
import et.bo.callcenter.cclog.service.TelQueryService;
import et.po.CcMain;
import et.po.GroupEgressive;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.Constants;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author Administrator
 * 
 */
public class CallOutImpl implements CallOutService {
	private BaseDAO dao = null;

	private KeyService ks = null;
	
	private ClassTreeService cts = null;

	private int num;

	public List telQuery(IBaseDTO dto, PageInfo pi) {
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		CallOutHelp sh = new CallOutHelp();
		num = dao.findEntitySize(sh.telQuery(dto, pi));
		Object[] result = (Object[]) dao.findEntity(sh.telQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {

			GroupEgressive oci = (GroupEgressive) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
//			String telnum = "";
//			if (oci.getCcMain() != null) {
//				telnum = oci.getCcMain().getTelNum() == null ? "" : oci
//						.getCcMain().getTelNum();
//			}
			dbd.set("telNum", oci.getTelnum());
			dbd.set("id", oci.getId());
			
			String beginTime = TimeUtil.getTheTimeStr(oci.getBegintime(),
					"yyyy-MM-dd HH:mm:ss");
			dbd.set("beginTime", beginTime);
			String endTime = TimeUtil.getTheTimeStr(oci.getEndtime(),
					"yyyy-MM-dd HH:mm:ss");
			dbd.set("endTime", endTime);
//			dbd.set("betweetTime",
//					Integer.parseInt(oci.getTouchKeeptime()) / 1000);
			String calloutType = oci.getMenuType();
			if("voice".equals(calloutType)){
				dbd.set("calloutType", "语音栏目");
				String ivrInfo = oci.getIvrTreeInfoId();
				try {
					String infoLabel = cts.getLabelByNickName(ivrInfo);
					if(infoLabel != null)
						dbd.set("context", infoLabel);
					else
						dbd.set("context", "");
				} catch (RuntimeException e) {
					dbd.set("context", "");
					list.add(dbd);
					return list;
				}
			}
			else if("message".equals(calloutType)){
				dbd.set("calloutType", "语音消息");
				if(oci.getContext().length()<9)
					dbd.set("context", oci.getContext());
				else
					dbd.set("context", oci.getContext().substring(0, 8)+"...");
			}
			else{
				dbd.set("calloutType", "语音栏目");
				dbd.set("context", cts.getLabelByNickName(oci.getIvrTreeInfoId()));
			}
			
			list.add(dbd);
		}
		return list;
	}

	public List userQuery(String sql) {
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		List<SysUser> list = new ArrayList<SysUser>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				SysUser su = new SysUser();
				su.setUserId(rs.getString("user_id"));
				list.add(su);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询外呼日志详细
	 * @param calloutlogId
	 * @return
	 */
	public IBaseDTO getInfo(String calloutlogId) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		// InfoSearch infoSearch = new InfoSearch();
		CallOutHelp coh = new CallOutHelp();
		Object[] result = (Object[]) dao.findEntity(coh.getInfo(calloutlogId));

		GroupEgressive cm = (GroupEgressive) result[0];
		dto.set("id", cm.getId());
		dto.set("telNum", cm.getTelnum());
		dto.set("begintime", cm.getBegintime());
		dto.set("endtime", cm.getEndtime());
		String kt = cm.getKeeptime();
		if(!"".equals(kt)){
			long keeptime = Long.parseLong(cm.getKeeptime());
			dto.set("betweetTime", cm.getKeeptime()+"秒");
		} else{
			dto.set("betweetTime", cm.getKeeptime());
		}
		if("voice".equals(cm.getMenuType())){
			dto.set("calloutType", "语音栏目");
//			dto.set("context", cts.getLabelByNickName(cm.getIvrTreeInfoId()));
			
			String ivrInfo = cm.getIvrTreeInfoId();
			try {
				String infoLabel = cts.getLabelByNickName(ivrInfo);
				if(infoLabel != null)
					dto.set("context", infoLabel);
				else
					dto.set("context", "");
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				dto.set("context", "");
				dto.set("filepath", cm.getFilepath());
				
				return dto;
			}
			
			System.out.println("******** is "+cts.getLabelByNickName(cm.getIvrTreeInfoId()));
		} else if("message".equals(cm.getMenuType())){
			dto.set("calloutType", "语音消息");
			dto.set("context", cm.getContext());
		}
		dto.set("filepath", cm.getFilepath());
		
		return dto;
	}

	public int getSize() {
		return num;
	}

	/**
	 * @return dao
	 */
	public BaseDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            要设置的 dao
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return ks
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * @param ks
	 *            要设置的 ks
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	/**
	 * @return num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            要设置的 num
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * 此方法不可用，已被删除
	 */
	public List getTel(IBaseDTO dto, PageInfo pi, String pageState) {
		String beginTime = dto.get("beginTime").toString();
		String endTime = dto.get("endTime").toString();
		String telNum = dto.get("telNum").toString();
		String caseId = dto.get("caseId").toString();
		int pState = 0;
		try {
			pState = Integer.parseInt(pageState);
		} catch (NumberFormatException e2) {
			pState = 1;
		}
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		String sqlV = "select top "
				+ pi.getPageSize()
				+ "  aaa.tel_num ,aaa.ring_begintime ,aaa.process_endtime ,aaa.process_keeptime ,aaa.filename"
				+ " from "
				+ " (select a.id, a.tel_num,a.ring_begintime,a.process_endtime,a.process_keeptime,b.filename from"
				+ " cc_main a"
				+ " left join"
				+ " ("
				+ " select a.id, b.filename"
				+ " from cc_main a,record b"
				+ " where (a.tel_num=b.caller or '%'+a.tel_num+'%' like b.caller)  and b.starttime>=a.ring_begintime and b.starttime <=a.process_endtime"
				+ " ) b"
				+ " on a.id = b.id ) aaa"
				+ " where aaa.id not in"
				+ " ("
				+ "	select top "
				+ (pState - 1) * pi.getPageSize()
				+ " ccc.id from (select a.id, a.tel_num,a.ring_begintime,a.process_endtime,a.process_keeptime,b.filename"
				+ "	from cc_main a"
				+ "	left join"
				+ "	("
				+ "	select a.id, b.filename"
				+ "	from cc_main a,record b"
				+ "	where (a.tel_num=b.caller or '%'+a.tel_num+'%' like b.caller)  and b.starttime>=a.ring_begintime and b.starttime <=a.process_endtime"
				+ "	) b" + "	on a.id = b.id) ccc" + "	order by id desc" + " )";

		if (!endTime.equals("") && !beginTime.equals("")) {
			sqlV += " and aaa.ring_begintime between '" + beginTime + "' and '"
					+ endTime + "' ";
		}
		if (!telNum.equals("")) {
			sqlV += " and aaa.tel_num='" + telNum + "' ";
		}
		sqlV += " order by aaa.id desc";
		System.out.println("sqlV :" + sqlV);
		String sql = "select count(*) as count from" + " cc_main"
				+ " where 1=1";
		if (!endTime.equals("") && !beginTime.equals("")) {
			sql += " and ring_begintime between '" + beginTime + "' and '"
					+ endTime + "' ";
		}
		if (!telNum.equals("")) {
			sql += " and tel_num='" + telNum + "' ";
		}
		RowSet rs = dao.getRowSetByJDBCsql(sqlV);

		try {
			rs.beforeFirst();
			System.out.println(rs.first());
			System.out.println(rs.getRow());
			System.out.println(rs.getMetaData().getColumnCount());
			while (rs.next()) {
				DynaBeanDTO dbd = new DynaBeanDTO();
				String bTime = rs.getString(2);
				String eTime = rs.getString(3);
				String filename = rs.getString(5);
				String process_keeptime = rs.getString(4);
				String tel_num = rs.getString(1);
				if (process_keeptime == null) {
					process_keeptime = "0";
				}
				if (filename != null)
					filename = Constants.getProperty("RecordServerAddress")
							+ filename.substring(filename.indexOf(":") + 1,
									filename.length());
				if (bTime != null && bTime.length() > 19) {
					bTime = bTime.substring(0, 19);
				}
				if (eTime != null && eTime.length() > 19) {
					eTime = eTime.substring(0, 19);
				}
				dbd.set("telNum", tel_num);
				dbd.set("beginTime", bTime);
				dbd.set("endTime", eTime);
				dbd.set("betweetTime",
						Integer.parseInt(process_keeptime) / 1000);
				// dbd.set("workNum",rs.getString("respondent") );
				dbd.set("filename", filename);
				System.out.println("tel_num :" + tel_num);
				System.out.println("bTime :" + bTime);
				System.out.println("eTime :" + eTime);
				System.out.println("process_keeptime :" + process_keeptime);
				System.out.println("filename :" + filename);
				System.out
						.println("-----------------------------------------------");
				list.add(dbd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			RowSet rs1 = dao.getRowSetByJDBCsql(sql);
			rs1.beforeFirst();
			int num11 = 0;
			while (rs1.next()) {
				num11 = Integer.parseInt(rs1.getString("count"));
			}
			num = num11;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return list;
	}

	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

}
