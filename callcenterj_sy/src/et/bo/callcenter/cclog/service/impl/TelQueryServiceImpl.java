/**
 * 
 */
package et.bo.callcenter.cclog.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import et.bo.callcenter.cclog.service.TelQueryService;
import et.bo.xml.XmlBuild;
import et.po.CcTalk;
import et.po.SysUser;
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
public class TelQueryServiceImpl implements TelQueryService {
	private BaseDAO dao = null;

	private KeyService ks = null;

	private int num;

	public void createXml(){
		try {
			XmlBuild xb = new XmlBuild();
			List list = new ArrayList();
			String sql = "select * from (select left(CONVERT(varchar, touch_begintime,120),7) mydate,"+
			"count(*) mycount from cc_main m,cc_talk t where t.cclog_id=m.id and touch_begintime<>''"+
			" and touch_begintime between dateadd(m,-11,getdate()) and getdate()"+
			" group by left(CONVERT(varchar, touch_begintime,120),7)) a order by a.mydate asc";
			RowSet rs = dao.getRowSetByJDBCsql(sql);
			while(rs.next()){
				String mydate = rs.getString(1);
				String mycount = rs.getString(2);
				ArrayList al = new ArrayList();
				al.add(mydate);
				al.add(mycount);
				list.add(al);
			}
			xb.modifyXML("yearPie3D",list,"12316呼叫中心最近一年各月话务咨询量统计","月份","话务量",12);
			
			XmlBuild xb2 = new XmlBuild();
			List list2 = new ArrayList();
			String sql2 = "select * from (select left(CONVERT(varchar, touch_begintime,120),10) mydate,"+
			"count(*) mycount from cc_main m,cc_talk t where t.cclog_id=m.id and touch_begintime<>''"+
			" and touch_begintime between dateadd(day,-30,getdate()) and getdate()"+
			" group by left(CONVERT(varchar, touch_begintime,120),10)) a order by a.mydate asc";
			RowSet rs2 = dao.getRowSetByJDBCsql(sql2);
			while(rs2.next()){
				String mydate = rs2.getString(1);
				mydate = mydate.substring(mydate.indexOf("-")+1);
				String mycount = rs2.getString(2);
				ArrayList al = new ArrayList();
				al.add(mydate);
				al.add(mycount);
				list2.add(al);
			}
			xb2.modifyXML("monthColumn2D",list2,"12316呼叫中心最近一月各日话务咨询量统计","日期","话务量",30);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public List telQuery(IBaseDTO dto, PageInfo pi) {
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		TelQueryHelp sh = new TelQueryHelp();
		num = dao.findEntitySize(sh.telQuery(dto, pi));
		Object[] result = (Object[]) dao.findEntity(sh.telQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {

			CcTalk oci = (CcTalk) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			String telnum = "";
			if (oci.getCcMain() != null) {
				try
				{
				telnum = oci.getCcMain().getTelNum() == null ? "" : oci
						.getCcMain().getTelNum();
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
			}
			dbd.set("telNum", telnum);
			// 电话号不能为空
//			if (telnum != null&&!telnum.equals("12316")&&!"".equals(telnum)) {
//				telnum = telnum.substring(telnum.length() - 7, telnum.length());
//			}
			String beginTime = TimeUtil.getTheTimeStr(oci.getTouchBegintime(),
					"yyyy-MM-dd HH:mm:ss");
			dbd.set("beginTime", beginTime);
			String endTime = TimeUtil.getTheTimeStr(oci.getTouchEndtime(),
					"yyyy-MM-dd HH:mm:ss");
			dbd.set("endTime", endTime);
			dbd.set("betweetTime",
					Integer.parseInt(oci.getTouchKeeptime()) / 1000);
			dbd.set("workNum", oci.getRespondent());

			// 将时间去掉，每次查询都查询到最后一条，相当于是根据日期做排序，但是以后可能越查询越慢
			String sql = "select filename from record where (caller='" + telnum
					+ "' or caller like '%" + telnum + "%') and wid='"+oci.getRespondent()+"' "+
					" and abs( datediff(s,'"+oci.getTouchBegintime()+"',starttime))<60";

			// and starttime>=dateadd(hour,-1,'" + beginTime
			// + "') and starttime<=dateadd(hour,1,'" + endTime + "')"

			 System.out.println(" sql " + sql);

			RowSet rs = dao.getRowSetByJDBCsql(sql);
			if (rs == null) {
				dbd.set("filename", "");
			} else {
				try {
					while (rs.next()) {
						String filename = rs.getString("filename");
						if (filename != null)
							filename = Constants
									.getProperty("RecordServerAddress")
									+ filename.substring(
											filename.indexOf(":") + 1, filename
													.length());
						dbd.set("filename", filename);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
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

}
