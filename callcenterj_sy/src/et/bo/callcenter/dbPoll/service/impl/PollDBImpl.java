package et.bo.callcenter.dbPoll.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import et.bo.callcenter.dbPoll.service.PollDBService;
import et.po.CcMain;
import et.po.CcTalk;
import et.po.EasyCdrTrkCopy;
import excellence.common.key.KeyService;
import excellence.framework.base.dao.BaseDAO;
/**
 * ��ѯ���ݿ⣬���м�����к�����ؼ�¼���뵽cc_main��cc_talk����
 * @author chen gang
 *
 */
public class PollDBImpl implements PollDBService {
	private BaseDAO dao;

	private KeyService ks;

	private Connection conn = null;

	private Statement st = null;

	private ResultSet rs = null;

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
	 * ��ѯ���ݿⷽ��
	 * 
	 */
	public void searchDB() {
//		PollDBHelp ph = new PollDBHelp();
		int countBefore = 0;
		int countNow = 1;
//		BaseTree bt = null;
//		Object[] res = dao.findEntity(ph.getDBCount());
//		if (res != null && res.length > 0) {
//			bt = (BaseTree) res[0];
//			String cLabel = bt.getLabel();
//			countBefore = Integer.parseInt(cLabel); // �����ݿ��в��������ѯǰ���м�¼��
//		}
//
//		try {
//			conn = ph.getConnection();
//			st = conn.createStatement();
//			String sql = "select count(*) from easy_cdr_trk";
//			ResultSet rs = st.executeQuery(sql);
//			while (rs.next()) {
//				String cNow = rs.getString(1);
//				bt.setLabel(cNow);
//				countNow = Integer.parseInt(cNow); // �����ݿ��в����ǰ���м�¼��
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (st != null)
//					st.close();
//				if (conn != null)
//					conn.close();
//
//				dao.updateEntity(bt);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}

		// �����м�¼��������ѯǰ��¼�����������⼸����¼
		if (countNow > countBefore) {
			try {
				conn = PollDBHelp.getConnection();
				st = conn.createStatement();
				// String sql = "SELECT ect.crs, ect.dir, ect.channo,
				// ect.caller, ect.starttime, " +
				// " ect.endtime, eca.agcworkid, eca.ringtime, eca.answertime,
				// eca.endtime AS Expr1" + //,ecar.filename
				// " from easy_cdr_trk as ect left join easy_cdr_agc as eca on
				// ect.crs = eca.crs" +
				// " where ect.dir ='2' or ect.dir='1' order by ect.starttime";
				// " INNER JOIN easy_cdr_agcrec AS ecar ON ect.crs = ecar.crs "
				// +

//				String sql = "select * from (select top("
//						+ countNow
//						+ ") ect.crs, ect.dir, ect.channo, ect.caller, ect.starttime,"
//						+ " ect.endtime, ect.callee, eca.agcworkid, eca.ringtime, eca.answertime, eca.endtime AS Expr1,"
//						+ " ROW_NUMBER() OVER(ORDER BY ect.starttime ASC) as flag"
//						+ " from easy_cdr_trk as ect left join easy_cdr_agc as eca on ect.crs = eca.crs"
//						+ " order by ect.starttime) as es where es.flag between "
//						+ (countBefore + 1) + " and " + countNow + "";
				
				String sql2 = "select ect.crs, ect.dir, ect.channo, ect.caller, ect.starttime,ect.endtime," + 
						" ect.callee, eca.agcworkid, eca.ringtime, eca.answertime, eca.endtime AS Expr1" +
						" from easy_cdr_trk_copy as ect left join easy_cdr_agc as eca on ect.crs = eca.crs" +
						" order by ect.starttime"; 

				rs = st.executeQuery(sql2);
				while (rs.next()) {
					insertCclog(rs);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (st != null)
						st.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ���ݲ�ѯ������Ϣ���в���cc_main��cc_talk��Ĳ���
	 * 
	 * @param rs
	 */
	private void insertCclog(ResultSet rs) {
		CcMain cm = new CcMain();
		CcTalk ct = new CcTalk();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			String mid = ks.getNext("CC_MAIN");
			String crs = rs.getString(1);
			
			String dir = rs.getString(2); // ����Ϊ1������Ϊ2
			String beginPost = rs.getString(3); // �������·����
			String telNum = rs.getString(4); // �������
			String ringBegintime = rs.getString(5); // ������ʼʱ��
			//System.out.println(".............." + ringBegintime);
			String processEndtime = rs.getString(6); // ��������ʱ��
			Long keeptime = sdf.parse(processEndtime).getTime()
					- sdf.parse(ringBegintime).getTime();
			String postType = "";
			String callee = rs.getString(7);

			String respondent = rs.getString(8);
			if (respondent == null) { // �������������뵫δת��ϯ
				if ("1".equals(dir)) { // ��ʾ���룬����δת��ϯ
					postType = "TRUNK";
					cm.setTelNum(telNum);
					cm.setId(mid);
					cm.setBeginPost(beginPost);

					cm.setRingBegintime(sdf.parse(ringBegintime));
					cm.setProcessEndtime(sdf.parse(processEndtime));
					cm.setProcessKeeptime(keeptime.toString());
					cm.setPostType(postType);
					cm.setIsDelete("0");
					cm.setRemark(crs);

					dao.saveEntity(cm);
				} else if ("2".equals(dir)) { // ��ʾ����
												// (������û�ж�Ӧ��ϯ��¼�����Լ����һ��������Ҫ�Ѻ����������phoneNum�ֶ�)
					postType = "OUTGOING";
					cm.setTelNum(telNum);
					cm.setId(mid);
					cm.setBeginPost(beginPost);
					cm.setRingBegintime(sdf.parse(ringBegintime));
					cm.setProcessEndtime(sdf.parse(processEndtime));
					cm.setProcessKeeptime(keeptime.toString());
					cm.setPostType(postType);
					cm.setIsDelete("0");
					cm.setRemark(crs);

					String tid = ks.getNext("CC_TALK");
					ct.setId(tid);
					ct.setCcMain(cm);
					// ct.setRespondent("expert");
					ct.setPhoneNum(telNum);
					ct.setRespondentType("OUTINGAGENT");
					ct.setRingBegintime(sdf.parse(ringBegintime));
					ct.setTouchBegintime(sdf.parse(ringBegintime));
					ct.setTouchEndtime(sdf.parse(processEndtime));
					Long touchKeeptime = sdf.parse(processEndtime).getTime()
							- sdf.parse(ringBegintime).getTime();
					// ct.setWaitingKeeptime(waitingKeeptime.toString());
					ct.setTouchKeeptime(touchKeeptime.toString());
					ct.setIsDelete("0");
//					ct.setInserttime(sdf.parse(inserttime));
					ct.setRemark(crs);

					dao.saveEntity(cm);
					dao.saveEntity(ct);
				}

			} else { // ��ϯ�����¼�����ж�Ӧ��¼��˵���ú�������ϯӦ��
				String tid = ks.getNext("CC_TALK");
				String respondentType = "";
				String talkBegintime = rs.getString(9);
				String talkTouchtime = rs.getString(10);
				Long waitingKeeptime = sdf.parse(talkTouchtime).getTime()
						- sdf.parse(talkBegintime).getTime();
				String talkEndtime = rs.getString(11);
				if(talkEndtime == null && talkTouchtime != null)
					talkEndtime = talkTouchtime;
				Long touchKeeptime = sdf.parse(talkEndtime).getTime()
						- sdf.parse(talkTouchtime).getTime();
				if ("1".equals(dir)) { // ��ʾ����
					postType = "TRUNK";
					respondentType = "AGENT";
				} else if ("2".equals(dir)) { // ��ʾ����
					postType = "OUTGOING";
					respondentType = "OUTINGAGENT";
				}

				cm.setId(mid);
				cm.setBeginPost(beginPost);
				cm.setTelNum(telNum);
				cm.setRingBegintime(sdf.parse(ringBegintime));
				cm.setProcessEndtime(sdf.parse(processEndtime));
				cm.setProcessKeeptime(keeptime.toString());
				cm.setPostType(postType);
				cm.setIsDelete("0");
				cm.setRemark(crs);

				ct.setId(tid);
				ct.setCcMain(cm);
				ct.setRespondent(respondent);
				ct.setRespondentType(respondentType);
				ct.setRingBegintime(sdf.parse(talkBegintime));
				ct.setTouchBegintime(sdf.parse(talkTouchtime));
				ct.setTouchEndtime(sdf.parse(talkEndtime));
				ct.setWaitingKeeptime(waitingKeeptime.toString());
				ct.setTouchKeeptime(touchKeeptime.toString());
				ct.setPhoneNum(telNum);
				ct.setIsDelete("0");
//				ct.setInserttime(sdf.parse(inserttime));
				ct.setRemark(crs);

				dao.saveEntity(cm);
				dao.saveEntity(ct);
			}
			
			deleteRecord(crs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ���ڱ��ݱ��е��Ѿ������ļ�¼
	 * @param crs
	 */
	private void deleteRecord(String crs) {
		PollDBHelp ph = new PollDBHelp();
		Object[] result = dao.findEntity(ph.getAlreadyReadRecord(crs));
		if(result != null && result.length > 0) {
			EasyCdrTrkCopy ec = (EasyCdrTrkCopy)result[0];
			dao.removeEntity(ec);
		}
	}
}
