/**
 * 
 */
package et.bo.callcenter.cclog.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import et.bo.callcenter.cclog.service.CclogMainService;
import et.bo.sys.basetree.service.impl.IVRBean;
import et.bo.sys.ivr.service.ClassBaseTreeService;
import et.po.CcIvrDate;
import et.po.CcMain;
import et.po.CcTalk;
import et.po.OperCustinfo;
import et.po.OperQuestion;
import et.po.SysUser;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.Constants;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @author Administrator
 * 
 */
public class CclogMainImpl implements CclogMainService {
	private BaseDAO dao = null;

	private KeyService ks = null;

	// private PoliceInfoService info = null;

	private ClassTreeService cts = null;

	private ClassBaseTreeService cbts = null;

	private BaseDAO ds = null;

	private int num;

	private int PHONE_NUM;
	
	private int PHONE_TODAY_NUM;

	public List queryCclog(IBaseDTO dto, PageInfo pi) {
		List<DynaBeanDTO> list = new ArrayList<DynaBeanDTO>();
		TelQueryHelp sh = new TelQueryHelp();
		PHONE_NUM = dao.findEntitySize(sh.telQuery(dto, pi));
		Object[] result = (Object[]) dao.findEntity(sh.telQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {

			CcTalk oci = (CcTalk) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			String telnum = "";
			if (oci.getCcMain() != null) {
				telnum = oci.getCcMain().getTelNum() == null ? "" : oci
						.getCcMain().getTelNum();
			}
			dbd.set("telNum", telnum);

			// 电话号不能为空
			if (telnum != null) {
				telnum = telnum.substring(telnum.length() - 7, telnum.length());
			}

			String beginTime = TimeUtil.getTheTimeStr(oci.getTouchBegintime(),
					"yyyy-MM-dd HH:mm:ss");
			dbd.set("ringBegintime", beginTime);
			String endTime = TimeUtil.getTheTimeStr(oci.getTouchEndtime(),
					"yyyy-MM-dd HH:mm:ss");
			dbd.set("processEndtime", endTime);
			// 写入talk和main表id
			dbd.set("id", oci.getCcMain().getId());
			dbd.set("talkid", oci.getId());
//			dbd.set("betweetTime",
//					Integer.parseInt(oci.getTouchKeeptime()) / 1000);
//			dbd.set("workNum", oci.getRespondent());

			// 将时间去掉，每次查询都查询到最后一条，相当于是根据日期做排序，但是以后可能越查询越慢
			String sql = "select filename from record where (caller='" + telnum
					+ "' or caller like '%" + telnum + "%')";

			// and starttime>=dateadd(hour,-1,'" + beginTime
			// + "') and starttime<=dateadd(hour,1,'" + endTime + "')"

			// System.out.println(" sql " + sql);

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

	private DynaBeanDTO CclogToDynaBeanDTO(CcMain cl) {
		DynaBeanDTO dbd = new DynaBeanDTO();

		// CclogHelp ch = new CclogHelp();
		// Object[] result = null;
		// result = dao.findEntity(ch.cctalkIdQuery(id));
		//				
		// for (int i = 0, size = result.length; i < size; i++) {
		// CclogTalk ct = (CclogTalk) result[i];
		// cid = ct.getId();
		// }

		// CclogTalk pci = (CclogTalk) dao
		// .loadEntity(CclogTalk.class, cid);

		dbd.set("id", cl.getId());
		// if (pci != null) {
		// String fuzznum = (String) pci.getFuzzNo();
		// dbd.set("fuzznum", fuzznum);
		// String operatornum = (String) pci.getRespondent();
		dbd.set("telNum", cl.getTelNum());
		// }

		// 是否回答成功,如果是0，就表示回答不成功，如果是1，表示回答成功
//		String sSet = "";
//		Set isSuccessSet = cl.getCcQuestions();
//		if (isSuccessSet.size() < 1) {
//			sSet = "0";
//		}
//		Iterator it = isSuccessSet.iterator();
//		String question = "";
//		while (it.hasNext()) {
//			CcQuestion pcii = (CcQuestion) it.next();
//			String iss = pcii.getIfAnswerSucceed();
//			if (pcii.getQuestion() != null || !pcii.getQuestion().equals("")) {
//				question = pcii.getQuestion();
//			}
//			if (iss == null)
//				iss = "0";
//			if (iss.equals("0")) {
//				sSet = "0";
//				break;
//			}
//			if (iss.equals("1")) {
//				sSet = "1";
//			}
//		}
//
//		dbd.set("issuccess", sSet);
//
//		String telNum = cl.getCclogMain().getTelNum();
//		if ("HandSet".equals(telNum))
//			dbd.set("telNum", "手台来电");
//		else
//			dbd.set("telNum", cl.getCclogMain().getTelNum());
		// dbd.set("inPort", cl.getInPort());
		dbd.set("ringBegintime", cl.getRingBegintime());
		System.out.println("keeptime is "+cl.getProcessKeeptime());
		dbd.set("processKeeptime", cl.getProcessKeeptime());
		dbd.set("processEndtime", cl.getProcessEndtime());
		// dbd.set("question", cl.getCcQuestions());
		// dbd.set("recFile", cl.getRecFile());
		// String recFile = (String)cl.getRecFile();
//		if (question.length() >= 15) {
//			question = question.substring(0, 15) + "......";
//		}
//		dbd.set("question", question);
//		if ((cl.getCclogMain().getRecordPath() == null || cl.getCclogMain()
//				.getRecordPath().equals(""))
//				&& !"3".equals(cl.getCclogMain().getCallManage())) {
//			dbd.set("recordPath", "");
//		} else if ((cl.getCclogMain().getRecordPath() == null || cl
//				.getCclogMain().getRecordPath().equals(""))
//				&& "3".equals(cl.getCclogMain().getCallManage())) {
//			dbd.set("recordPath", "interPhone");
//		} else {
//			StringBuffer sb = new StringBuffer();
//			String recordPath = Constants.getProperty("SOUND_PATH_BEFORE");
//			sb.append(recordPath);
//			sb.append(cl.getCclogMain().getRecordPath());
//			dbd.set("recordPath", sb.toString());
//		}
		return dbd;
	}

	

	public int getCclogSize() {
		int phone = PHONE_NUM;
		System.out.println("phone.................." + phone);
		return phone;
	}
	
	public int getRecordSize() {
		int recordNum = PHONE_TODAY_NUM;
		return recordNum;
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

	public BaseDAO getDs() {
		return ds;
	}

	public void setDs(BaseDAO ds) {
		this.ds = ds;
	}

	

	

	public int getCcLogInfoSize() {
		// TODO Auto-generated method stub
		return num;
	}

	public List getDepInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		// List<LabelValueBean> depList = depTree.getLabelList("1");
		// Iterator<LabelValueBean> it = depList.iterator();
		// CclogHelp ch = new CclogHelp();
		// while (it.hasNext()) {
		// LabelValueBean lvb = it.next();
		// String id = lvb.getValue();
		// DynaBeanDTO mdto = new DynaBeanDTO();
		// mdto.set("depname", depTree.getvaluebyId(id));
		// int size = dao.findEntitySize(ch.queryDep(dto,id));
		// mdto.set("count", size);
		// l.add(mdto);
		// }
		return l;
	}

	public IBaseDTO getInfo(String cclogId, String cctalkId) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		// InfoSearch infoSearch = new InfoSearch();
		Object[] result = (Object[]) dao.findEntity(searchCclogMain(cclogId));

		CcMain cm = (CcMain) result[0];
		String telNum = cm.getTelNum();
		dto.set("id", cm.getId());
		System.out.println("talkid is "+cctalkId);
		dto.set("talkid", cctalkId);
		dto.set("telNum", cm.getTelNum());
		dto.set("ringBegintime", cm.getRingBegintime());
		dto.set("processEndtime", cm.getProcessEndtime());
		String kt = cm.getProcessKeeptime();
		if(!"".equals(kt)){
			long keeptime = Long.parseLong(cm.getProcessKeeptime());
			dto.set("processKeeptime", keeptime/1000+"秒");
		} else{
			dto.set("processKeeptime", cm.getProcessKeeptime());
		}
		

		Object[] result2 = (Object[]) dao.findEntity(searchUserInfo(telNum));
		if(result2 != null && result2.length > 0) {
			OperCustinfo oc = (OperCustinfo)result2[0];
			dto.set("name", oc.getCustName());
			
			String sex = oc.getDictSex();
			dto.set("sex", cts.getLabelById(sex));
			dto.set("contactAddress", oc.getCustAddr());
			dto.set("mobile", oc.getCustTelMob());
			dto.set("tel", oc.getCustTelHome());
			dto.set("email", oc.getCustEmail());
			return dto;
		}
		return dto;
	}
	
	public IBaseDTO getQuesInfo(String questionId) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		// InfoSearch infoSearch = new InfoSearch();
		Object[] result = (Object[]) dao.findEntity(searchOperQuestion(questionId));
		
		if(result != null && result.length > 0) {
			OperQuestion oq = (OperQuestion)result[0];
			
			dto.set("id", oq.getId());
			
			OperCustinfo oc = oq.getOperCustinfo();
			dto.set("userName", oc.getCustName());
			
			dto.set("question", oq.getQuestionContent());
			String queState = oq.getDictIsAnswerSucceed();
			dto.set("quinfo", cts.getLabelById(queState));
			dto.set("ifNeedRevert", oq.getDictIsCallback());
			String answerMan = oq.getAnswerMan();
			dto.set("answerMan", cts.getLabelById(answerMan));
			dto.set("questionTime", oq.getAddtime());
			dto.set("answer", oq.getAnswerContent());
		}
		
		return dto;
	}

	private MyQuery searchCclogMain(String num) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(CcMain.class);
		if (num != null && !num.equals("")) {
			// System.out.println("号码传到这了吗"+num);
			dc.add(Expression.eq("id", num));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}
	
	private MyQuery searchOperQuestion(String qId) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria.forClass(OperQuestion.class);
		if (qId != null && !qId.equals("")) {
			// System.out.println("号码传到这了吗"+num);
			dc.add(Expression.eq("id", qId));
		}
		mq.setDetachedCriteria(dc);
		return mq;
	}


	private MyQuery searchUserInfo(String telNum) {
		MyQuery mq = new MyQueryImpl();
		DetachedCriteria dc = DetachedCriteria
				.forClass(OperCustinfo.class);
		StringBuffer sb = new StringBuffer();
		
		if (telNum != null && !telNum.equals("")) {
			// System.out.println("号码传到这了吗"+num);
			sb.append("select oc from OperCustinfo oc where oc.custId = oc.custId");
			sb.append(" and (oc.custTelHome = '"+telNum+"'");
			sb.append(" or oc.custTelWork = '"+telNum+"'");
			sb.append(" or oc.custTelMob = '"+telNum+"')");
		}
		mq.setHql(sb.toString());
		return mq;
	}

	
	

	
	
	/**
	 * 根据主表id查询IVR信息
	 * @param cclogId
	 * @return
	 */
	public List queryIVRInfo(String cclogId) {
		List list = new ArrayList();
		System.out.println("cclogId is "+cclogId);
		CclogMainHelp ch = new CclogMainHelp();
		Object[] result = dao.findEntity(ch.queryIVRInfo(cclogId));
		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				CcIvrDate cid = (CcIvrDate)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				IVRBean ib = cbts.getIVRBeanById(cid.getModuleId());
				dbd.set("moduleName", ib.getLabel());
				dbd.set("enterTime", cid.getModuleBegintime());
				dbd.set("leaveTime", cid.getModuleEndtime());
				list.add(dbd);
			}
		}
		return list;
	}
	
	/**
	 * 根据主表id查询问题信息
	 * @param cclogId
	 * @return
	 */
	public List queryQuesInfo(String cclogId) {
		List list = new ArrayList();
		System.out.println("cclogId is "+cclogId);
		CclogMainHelp ch = new CclogMainHelp();
		Object[] result = dao.findEntity(ch.queryQuesInfo(cclogId));
		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				OperQuestion oq = (OperQuestion)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("question", oq.getQuestionContent());
				String state = oq.getDictIsAnswerSucceed();
				dbd.set("quinfo", cts.getLabelById(state));
				dbd.set("ifNeedRevert", oq.getDictIsCallback());
				dbd.set("questionId", oq.getId());
				list.add(dbd);
			}
		}
		return list;
	}
	
	/**
	 * 根据座席员名查询其当天应答电话的录音信息
	 * @param userName
	 * @return
	 */
	public List queryRecord(String userName, String telNum, PageInfo pi) {
		
		//System.out.println("queryRecord userName is "+userName);
		List list = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayTime = sdf.format(TimeUtil.getNowTime());
		CclogMainHelp ch = new CclogMainHelp();
		Object[] result = dao.findEntity(ch.queryRecord(userName, telNum, todayTime, pi));
		PHONE_TODAY_NUM = dao.findEntitySize(ch.queryRecordSize(userName, telNum, todayTime, pi));
		System.out.println("count is "+PHONE_TODAY_NUM);
		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				CcTalk ct = (CcTalk)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("phoneNum", ct.getCcMain().getTelNum());
				dbd.set("ringBegintime", ct.getTouchBegintime());
				dbd.set("processEndtime", ct.getTouchEndtime());
				
				if (ct.getRecordPath() == null
						|| ct.getRecordPath().equals("")) {
					dbd.set("recordPath", "");
				} else {
					StringBuffer sb = new StringBuffer();
					String recordPath = Constants.getProperty("SOUND_PATH_BEFORE");
					sb.append(recordPath);
					sb.append(ct.getRecordPath());
					dbd.set("recordPath", sb.toString());
				}
				list.add(dbd);
			}
		}
		return list;
	}
	public List queryRecord(String userName,String telNum, String beginTime, String endTime, PageInfo pi)
	{
		if(userName == null)
		{
			userName = "";
		}
		List list = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayTime = sdf.format(TimeUtil.getNowTime());
		CclogMainHelp ch = new CclogMainHelp();
		Object[] result = dao.findEntity(ch.queryRecord(userName, telNum, beginTime,endTime, pi));
		PHONE_TODAY_NUM = dao.findEntitySize(ch.queryRecordSize(userName, telNum,beginTime,endTime, pi));
		if(result != null && result.length > 0) {
			for(int i=0,size=result.length; i<size; i++) {
				CcTalk ct = (CcTalk)result[i];
				DynaBeanDTO dbd = new DynaBeanDTO();
				dbd.set("phoneNum", ct.getCcMain().getTelNum());
				dbd.set("ringBegintime", ct.getTouchBegintime());
				dbd.set("processEndtime", ct.getTouchEndtime());
				dbd.set("agentMan", ct.getRespondent());
				if (ct.getRecordPath() == null
						|| ct.getRecordPath().equals("")) {
					dbd.set("recordPath", "");
				} else {
					StringBuffer sb = new StringBuffer();
					String recordPath = Constants.getProperty("SOUND_PATH_BEFORE");
					sb.append(recordPath);
					sb.append(ct.getRecordPath());
					dbd.set("recordPath", sb.toString());
				}
				list.add(dbd);
			}
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
	
	public ClassTreeService getCts() {
		return cts;
	}

	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	public ClassBaseTreeService getCbts() {
		return cbts;
	}

	public void setCbts(ClassBaseTreeService cbts) {
		this.cbts = cbts;
	}

	public List queryQuestionByAgent(String num) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addCclogInfo(IBaseDTO dto, String id) {
		// TODO Auto-generated method stub	
	}

	public void addQuestionInfo(IBaseDTO dto, String pid) {
		// TODO Auto-generated method stub	
	}

	public IBaseDTO getCclogInfo(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public IBaseDTO getQuestionInfo(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public IBaseDTO listFuzzInfo(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List listPhoneInfo(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List queryCcLogInfo(IBaseDTO dto, PageInfo pi, String begintime, String opernum) {
		// TODO Auto-generated method stub
		return null;
	}

	public List queryQuestion(IBaseDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	public void upQuestionInfo(IBaseDTO dto) {
		// TODO Auto-generated method stub
	}
}
