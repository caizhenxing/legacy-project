/**
 * 	@(#)EmailOutServiceImpl.java   Aug 30, 2006 6:36:54 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.email.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import et.bo.oa.communicate.email.service.EmailService;
import et.po.EmailBox;
import et.po.InadjunctInfo;
import et.po.InemailInfo;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.Constants;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * 
 * @author zhang
 * @version Aug 30, 2006
 * @see
 */
public class EmailOutServiceImpl implements EmailService {

	private BaseDAO dao = null;

	private KeyService ks = null;

	private int EMAIL_OUT_NUM = 0;

	private String OUTEMAIL_SAVE_IN_DATA = Constants
			.getProperty("outemail_save_in_data");

	// 收件箱
	private String EMAILTYPE_GETBOX = "1";

	// 发件箱
	private String EMAILTYPE_SENDBOX = "2";

	// 草稿箱
	private String EMAILTYPE_DRAFTBOX = "3";

	// 垃圾箱
	private String EMAILTYPE_DUSIBNBOX = "1";

	// 邮件发送是否成功
	private String EMAIL_SIGN_SUCCESS = "1";

	private String EMAIL_SIGN_FAIL = "2";

	// 是否读过
	private String OPER_SIGN_READ = "1";

	private String OPER_SIGN_UNREAD = "2";

	// 是否已经发送成功
	private String IS_SEND_SUCCESS = "1";

	private String IS_SEND_FAIL = "2";

	// 是内网还是外网邮件
	private String IN_EMAIL = "1";

	private String OUT_EMAIL = "2";

	// 删除标志
	private String del_SIGN = "N";

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.communicate.email.service.EmailService#delEmailForever(java.lang.String[])
	 */
	public boolean delEmailForever(String[] selectIt) {
		// TODO Auto-generated method stub
		boolean flag = false;
		for (int i = 0; i < selectIt.length; i++) {
			String articleid = selectIt[i];
			InemailInfo inemailInfo = (InemailInfo) dao.loadEntity(
					InemailInfo.class, articleid);
			dao.removeEntity(inemailInfo);
			flag = true;
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.communicate.email.service.EmailService#delEmailToDustbin(java.lang.String[])
	 */
	public boolean delEmailToDustbin(String[] selectIt) {
		// TODO Auto-generated method stub
		boolean flag = false;
		for (int i = 0; i < selectIt.length; i++) {
			String articleid = selectIt[i];
			InemailInfo inemailInfo = (InemailInfo) dao.loadEntity(
					InemailInfo.class, articleid);
			inemailInfo.setDelSign("y".toUpperCase());
			dao.updateEntity(inemailInfo);
			flag = true;
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.communicate.email.service.EmailService#emailListIndex(excellence.framework.base.dto.IBaseDTO,
	 *      excellence.common.page.PageInfo, java.lang.String)
	 */
	public List emailListIndex(IBaseDTO dto, PageInfo pageInfo,
			String mailboxType) {
		// TODO Auto-generated method stub
		List finalList = new ArrayList();
		OuterEmailSearch outerEmailSearch = new OuterEmailSearch();
		Object[] result = null;
		// 收件箱
		if (mailboxType.equals("getBox")) {
			result = (Object[]) dao.findEntity(outerEmailSearch
					.searchGetEmailList(dto, pageInfo));
		}
		// 已发邮件
		if (mailboxType.equals("sendBox")) {
			result = (Object[]) dao.findEntity(outerEmailSearch
					.searchSendEmailList(dto, pageInfo));
		}
		// 草稿箱
		if (mailboxType.equals("draftBox")) {
			result = (Object[]) dao.findEntity(outerEmailSearch
					.searchDraftEmailList(dto, pageInfo));
		}
		// 垃圾箱
		if (mailboxType.equals("dustbinBox")) {
			result = (Object[]) dao.findEntity(outerEmailSearch
					.searchDelEmailList(dto, pageInfo));
		}
		int s = 0;
		// 收件箱
		if (mailboxType.equals("getBox")) {
			s = dao.findEntitySize(outerEmailSearch.searchGetEmailList(dto,
					pageInfo));
		}
		// 已发邮件
		if (mailboxType.equals("sendBox")) {
			s = dao.findEntitySize(outerEmailSearch.searchSendEmailList(dto,
					pageInfo));
		}
		// 草稿箱
		if (mailboxType.equals("draftBox")) {
			s = dao.findEntitySize(outerEmailSearch.searchDraftEmailList(dto,
					pageInfo));
		}
		// 垃圾箱
		if (mailboxType.equals("dustbinBox")) {
			s = dao.findEntitySize(outerEmailSearch.searchDelEmailList(dto,
					pageInfo));
		}
		EMAIL_OUT_NUM = s;
		// 
		for (int i = 0, size = result.length; i < size; i++) {
			InemailInfo inEmailInfo = (InemailInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", inEmailInfo.getId());
			dbd.set("takeList", inEmailInfo.getTakeList());
			dbd.set("emailTitle", inEmailInfo.getEmailTitle());
			dbd.set("emailTime", TimeUtil.getTheTimeStr(inEmailInfo
					.getCreateTime()));
			finalList.add(dbd);
		}
		return finalList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.communicate.email.service.EmailService#getEmailIndexSize()
	 */
	public int getEmailIndexSize() {
		// TODO Auto-generated method stub
		return EMAIL_OUT_NUM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.communicate.email.service.EmailService#getInEmailInfo(java.lang.String)
	 */
	public IBaseDTO getInEmailInfo(String id) {
		// TODO Auto-generated method stub
		InemailInfo inemailInfo = (InemailInfo) dao.loadEntity(
				InemailInfo.class, id);
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("takeList", inemailInfo.getTakeUser());
		dto.set("takeUser", inemailInfo.getTakeUser());
		dto.set("emailTitle", inemailInfo.getEmailTitle());
		dto.set("emailInfo", inemailInfo.getEmailInfo());
		EmailSearch emailSearch = new EmailSearch();
		// dto.set("adjuctlist", );
		Object[] result = null;
		result = (Object[]) dao.findEntity(emailSearch
				.searchUploadListInfo(inemailInfo));
		List l = new ArrayList();
		for (int i = 0, size = result.length; i < size; i++) {
			InadjunctInfo inadjunctInfo = (InadjunctInfo) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("name", inadjunctInfo.getAdjunctName());
			dbd.set("url", inadjunctInfo.getAdjunctAddr());
			l.add(dbd);
		}
		dto.set("adjunctInfo", l);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.communicate.email.service.EmailService#saveEmailToAddresser(excellence.framework.base.dto.IBaseDTO,
	 *      java.util.List, java.lang.String)
	 */
	public boolean saveEmailToAddresser(IBaseDTO dto, List adjunctList,
			String mailType) {
		// TODO Auto-generated method stub
		boolean flag = false;
		// 从外网收取邮件保存到数据库
		if (mailType.equals("take")) {
			OperOuterEmail operOuterEmail = new OperOuterEmail();
			String emailboxid = dto.get("emailboxid").toString();
			EmailBox emailBox = (EmailBox) dao.loadEntity(EmailBox.class,
					emailboxid);
			List l = null;
			try {
				l = operOuterEmail.getOutEmail(emailBox.getPop3(), emailBox
						.getPop3User(), emailBox.getPopPassword());
				// adjunctList = operOuterEmail.pathList;
			} catch (Exception e) {
				e.printStackTrace();
			}
			Iterator it = l.iterator();
			while (it.hasNext()) {
				InemailInfo out = (InemailInfo) it.next();
				String key = ks.getNext("inemail_info");
				out.setSendUser(dto.get("sendUser").toString());
				out.setId(key);
				out.setEmailType(EMAILTYPE_GETBOX);
				out.setEmailSign("1");
				out.setInorout("2");
				out.setEmailboxId(emailboxid);
				out.setDelSign("n".toUpperCase());

				// 附件信息
				if (adjunctList != null) {
					Set set = new HashSet();
					Iterator iter = adjunctList.iterator();

					while (iter.hasNext()) {
						String adjunctAddr = iter.next().toString();
						// System.out.println("adjunctAddr========
						// "+adjunctAddr);
						InadjunctInfo inadjunctInfo = new InadjunctInfo();
						inadjunctInfo.setId(ks.getNext("inadjunct_info"));
						inadjunctInfo.setInemailInfo(out);
						inadjunctInfo.setAdjunctAddr(OUTEMAIL_SAVE_IN_DATA
								+ adjunctAddr);
						inadjunctInfo.setAdjunctName(adjunctAddr);
						inadjunctInfo.setCreateTime(TimeUtil.getNowTime());
						inadjunctInfo.setOperUser(dto.get("sendUser")
								.toString());
						set.add(inadjunctInfo);
					}

					out.setInadjunctInfos(set);
				}

				dao.saveEntity(out);
			}
			flag = true;
		}
		if (mailType.equals("send")) {
			OperOuterEmail operOuterEmail = new OperOuterEmail();
			String emailboxid = dto.get("emailboxid").toString();
			EmailBox emailBox = (EmailBox) dao.loadEntity(EmailBox.class,
					emailboxid);
			String sendlist = dto.get("takeList").toString();
			StringTokenizer strToken = new StringTokenizer(sendlist, ",");
			while (strToken.hasMoreTokens()) {
				String takeuser = strToken.nextToken();
				try {
					operOuterEmail.sendOutEmail(emailBox.getSmtp(), emailBox
							.getSmtpUser(), emailBox.getSmtpPassword(),
							takeuser, emailBox.getEmailAddress(), dto.get(
									"emailTitle").toString(), dto.get(
									"emailInfo").toString(), adjunctList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				InemailInfo inemailInfo = new InemailInfo();
				String key = ks.getNext("inemail_info");
				inemailInfo.setId(key);
				inemailInfo.setSendUser(dto.get("sendUser").toString());
				inemailInfo.setTakeUser(takeuser);
				inemailInfo.setTakeList(sendlist);
				inemailInfo.setEmailTitle(dto.get("emailTitle").toString());
				inemailInfo.setEmailInfo(dto.get("emailInfo").toString());
				inemailInfo.setCreateTime(TimeUtil.getNowTime());
				inemailInfo.setSendType("");
				inemailInfo.setEmailType(EMAILTYPE_SENDBOX);
				inemailInfo.setEmailSign(EMAIL_SIGN_SUCCESS);
				inemailInfo.setOperSign(OPER_SIGN_UNREAD);
				// 标识为内网邮件
				inemailInfo.setInorout(OUT_EMAIL);
				inemailInfo.setEmailboxId(emailboxid);
				inemailInfo.setIssend(IS_SEND_SUCCESS);
				inemailInfo.setDelSign(del_SIGN);

				// 附件信息
				if (adjunctList != null) {
					Set set = new HashSet();
					Iterator iter = adjunctList.iterator();

					while (iter.hasNext()) {
						String adjunctAddr = iter.next().toString();
						// System.out.println("adjunctAddr========
						// "+adjunctAddr);
						InadjunctInfo inadjunctInfo = new InadjunctInfo();
						inadjunctInfo.setId(ks.getNext("inadjunct_info"));
						inadjunctInfo.setInemailInfo(inemailInfo);
						inadjunctInfo.setAdjunctAddr(OUTEMAIL_SAVE_IN_DATA
								+ adjunctAddr);
						inadjunctInfo.setAdjunctName(adjunctAddr);
						inadjunctInfo.setCreateTime(TimeUtil.getNowTime());
						inadjunctInfo.setOperUser(dto.get("sendUser")
								.toString());
						set.add(inadjunctInfo);
					}

					inemailInfo.setInadjunctInfos(set);
				}

				dao.saveEntity(inemailInfo);
			}
			flag = true;
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see et.bo.oa.communicate.email.service.EmailService#saveEmailToDraft(excellence.framework.base.dto.IBaseDTO,
	 *      java.util.List)
	 */
	public boolean saveEmailToDraft(IBaseDTO dto, List adjunctList) {
		// TODO Auto-generated method stub
		return false;
	}

	public List getEmailBoxList(String userkey) {
		// TODO Auto-generated method stub
		List l = new ArrayList();
		OuterEmailSearch oeSearch = new OuterEmailSearch();
		SysUser sys = (SysUser) dao.loadEntity(SysUser.class, userkey);
		Object[] result = null;
		result = dao.findEntity(oeSearch.searchEmailBoxList(sys));
		for (int i = 0, size = result.length; i < size; i++) {
			EmailBox emailBox = (EmailBox) result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", emailBox.getId());
			dbd.set("emailname", emailBox.getName());
			// dbd.set("emailTitle", inEmailInfo.getEmailTitle());
			l.add(dbd);
		}
		return l;
	}

	public List<IBaseDTO> userList() {
		// TODO Auto-generated method stub
		return null;
	}

}
