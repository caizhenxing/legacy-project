/**
 * 	@(#)MailBoxServiceImpl.java   Aug 30, 2006 7:29:03 PM
 *	 ¡£ 
 *	 
 */
package et.bo.oa.communicate.mailbox.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.oa.communicate.mailbox.service.MailBoxService;
import et.po.EmailBox;
import et.po.SysUser;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Aug 30, 2006
 * @see
 */
public class MailBoxServiceImpl implements MailBoxService {
	
    private BaseDAO dao=null;
    
    private KeyService ks = null;
    
    private int EMAIL_NUM = 0;

	public KeyService getKs() {
		return ks;
	}

	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	
    private EmailBox createEmailBoxInfo(IBaseDTO dto) {
    	EmailBox emailBox = new EmailBox();
    	emailBox.setId(ks.getNext("email_box"));
    	SysUser sysuser = (SysUser)dao.loadEntity(SysUser.class, dto.get("userid").toString());
    	emailBox.setSysUser(sysuser);
    	emailBox.setName(dto.get("name").toString());
    	emailBox.setSmtp(dto.get("smtp").toString());
    	emailBox.setPop3(dto.get("pop3").toString());
    	String pop3user = dto.get("pop3user").toString();
    	String poppassword = dto.get("poppassword").toString();
    	//
    	emailBox.setPop3User(pop3user);
    	emailBox.setPopPassword(poppassword);
    	emailBox.setSmtpUser(pop3user);
    	emailBox.setSmtpPassword(poppassword);
    	//emailBox.setSmtpPort(Integer.parseInt(dto.get("smtpport").toString()));
    	//
    	emailBox.setSmtpSsl(dto.get("smtpssl").toString());
    	//emailBox.setPop3Port(Integer.parseInt(dto.get("pop3port").toString()));
    	emailBox.setPop3Ssl(dto.get("pop3ssl").toString());
    	emailBox.setEmailAddress(dto.get("emailaddress").toString());
    	emailBox.setReturnAdress(dto.get("returnadress").toString());
    	emailBox.setTagSmtpSsl(dto.get("tagsmtpssl").toString());
    	emailBox.setTagPop3Ssl(dto.get("tagpop3ssl").toString());
    	emailBox.setRemark(dto.get("remark").toString());
    	//
        return emailBox;
    }

	/* (non-Javadoc)
	 * @see et.bo.oa.communicate.mailbox.service.MailBoxService#addMailBox(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean addMailBox(IBaseDTO dto) {
		// TODO Auto-generated method stub
		boolean flag = false;
		//
        dao.saveEntity(createEmailBoxInfo(dto));
        flag = true;
        return flag;
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.communicate.mailbox.service.MailBoxService#delMailBox(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean delMailBox(IBaseDTO dto) {
		// TODO Auto-generated method stub
        boolean flag = false;
        EmailBox emailBox = (EmailBox) dao.loadEntity(
        		EmailBox.class, dto.get("id").toString());
        dao.removeEntity(emailBox);
        flag = true;
        return flag;
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.communicate.mailbox.service.MailBoxService#emailBoxIndex(excellence.framework.base.dto.IBaseDTO, excellence.common.page.PageInfo)
	 */
	public List emailBoxIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
        List l = new ArrayList();
        MailBoxSearch mailBoxSearch = new MailBoxSearch();
        Object[] result = (Object[]) dao.findEntity(mailBoxSearch.searchMailBoxList(dto, pageInfo));
        int s = dao.findEntitySize(mailBoxSearch.searchMailBoxList(dto, pageInfo));
        EMAIL_NUM = s;
        for (int i = 0, size = result.length; i < size; i++) {
        	EmailBox emailBox = (EmailBox) result[i];
            DynaBeanDTO dbd = new DynaBeanDTO();
            dbd.set("id", emailBox.getId());
            dbd.set("name", emailBox.getName());
            dbd.set("mailboxname", emailBox.getEmailAddress());
            dbd.set("smtp", emailBox.getSmtp());
            dbd.set("pop", emailBox.getPop3());
            l.add(dbd);
        }
        return l;
	}

	/* (non-Javadoc)
	 * @see et.bo.oa.communicate.mailbox.service.MailBoxService#getEmailBoxSize()
	 */
	public int getEmailBoxSize() {
		// TODO Auto-generated method stub
		return EMAIL_NUM;
	}

	
    private EmailBox updateEmailBoxInfo(IBaseDTO dto) {
    	EmailBox emailBox = new EmailBox();
    	emailBox.setId(dto.get("id").toString());
    	SysUser sysuser = (SysUser)dao.loadEntity(SysUser.class, dto.get("userid").toString());
    	emailBox.setSysUser(sysuser);
    	emailBox.setName(dto.get("name").toString());
    	emailBox.setSmtp(dto.get("smtp").toString());
    	emailBox.setPop3(dto.get("pop3").toString());
    	String pop3user = dto.get("pop3user").toString();
    	String poppassword = dto.get("poppassword").toString();
    	emailBox.setPop3User(pop3user);
    	emailBox.setPopPassword(poppassword);
    	emailBox.setSmtpUser(pop3user);
    	emailBox.setSmtpPassword(poppassword);
    	//emailBox.setSmtpPort(Integer.parseInt(dto.get("smtpport").toString()));
    	emailBox.setSmtpSsl(dto.get("smtpssl").toString());
    	//emailBox.setPop3Port(Integer.parseInt(dto.get("pop3port").toString()));
    	emailBox.setPop3Ssl(dto.get("pop3ssl").toString());
    	emailBox.setEmailAddress(dto.get("emailaddress").toString());
    	emailBox.setReturnAdress(dto.get("returnadress").toString());
    	emailBox.setTagSmtpSsl(dto.get("tagsmtpssl").toString());
    	emailBox.setTagPop3Ssl(dto.get("tagpop3ssl").toString());
    	emailBox.setRemark(dto.get("remark").toString());
        return emailBox;
    }
    
	/* (non-Javadoc)
	 * @see et.bo.oa.communicate.mailbox.service.MailBoxService#updateMailBox(excellence.framework.base.dto.IBaseDTO)
	 */
	public boolean updateMailBox(IBaseDTO dto) {
		// TODO Auto-generated method stub
        boolean flag = false;
        dao.updateEntity(updateEmailBoxInfo(dto));
        flag = true;
        return flag;
	}
	

	public IBaseDTO getEmailBox(String id) {
		// TODO Auto-generated method stub
		IBaseDTO dto = new DynaBeanDTO();
		EmailBox emailBox = (EmailBox)dao.loadEntity(EmailBox.class, id);
		
		dto.set("id", emailBox.getId());
		//dto.set("userid", emailBox.getSysUser().getUserId());
		dto.set("name", emailBox.getName());
		dto.set("smtp", emailBox.getSmtp());
		dto.set("pop3", emailBox.getPop3());
		dto.set("pop3user", emailBox.getPop3User());
		dto.set("poppassword", emailBox.getPopPassword());
		//dto.set("smtpuser", emailBox.getSmtpUser());
		//dto.set("smtppassword", emailBox.getSmtpPassword());
		//dto.set("smtpport", emailBox.getSmtpPort());
		dto.set("smtpssl", emailBox.getSmtpSsl());
		//dto.set("pop3port", emailBox.getPop3Port());
		dto.set("pop3ssl", emailBox.getPop3Ssl());
		dto.set("emailaddress", emailBox.getEmailAddress());
		dto.set("returnadress", emailBox.getReturnAdress());
		dto.set("tagsmtpssl", emailBox.getTagSmtpSsl());
		dto.set("tagpop3ssl", emailBox.getTagPop3Ssl());
		dto.set("remark", emailBox.getRemark());
		//
		return dto;
	}

}
