package et.bo.oa.communicate.email.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import et.bo.oa.communicate.email.service.EmailService;
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
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;

/**
 * @describe �ڲ��ʼ���Ϣ�ľ���ʵ��
* @author  zhangfeng
* @version 1.0, 2006/08/29
* @see	BaseDAO  
* @see	KeyService	
*/

public class EmailInServiceImpl implements EmailService {
	
    private BaseDAO dao=null;
    
    private KeyService ks = null;
    
    private int EMAIL_NUM = 0;
    
    private String TOMCAT_HOME_ADDRESS = Constants.getProperty("email_tomcat_real_path");

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

	public List emailListIndex(IBaseDTO dto, PageInfo pageInfo,
			String mailboxType) {
		// TODO Auto-generated method stub
		List finalList = new ArrayList();
		EmailSearch emailSearch = new EmailSearch();
		Object[] result = null;
		//�ռ���
		if (mailboxType.equals("getBox")) {
			result=(Object[])dao.findEntity(emailSearch.searchGetEmailList(dto,pageInfo));
		}
		//�ѷ��ʼ�
		if (mailboxType.equals("sendBox")) {
			result=(Object[])dao.findEntity(emailSearch.searchSendEmailList(dto,pageInfo));
		}
		//�ݸ���
		if (mailboxType.equals("draftBox")) {
			result=(Object[])dao.findEntity(emailSearch.searchDraftEmailList(dto,pageInfo));
		}
		//������
		if (mailboxType.equals("dustbinBox")) {
			result=(Object[])dao.findEntity(emailSearch.searchDelEmailList(dto,pageInfo));
		}
		int s = 0;
		//�ռ���
		if (mailboxType.equals("getBox")) {
			s=dao.findEntitySize(emailSearch.searchGetEmailList(dto,pageInfo));
		}
		//�ѷ��ʼ�
		if (mailboxType.equals("sendBox")) {
			s=dao.findEntitySize(emailSearch.searchSendEmailList(dto,pageInfo));
		}
		//�ݸ���
		if (mailboxType.equals("draftBox")) {
			s=dao.findEntitySize(emailSearch.searchDraftEmailList(dto,pageInfo));
		}
		//������
		if (mailboxType.equals("dustbinBox")) {
			s=dao.findEntitySize(emailSearch.searchDelEmailList(dto,pageInfo));
		}
		EMAIL_NUM = s;
		//
		for(int i = 0,size=result.length;i<size;i++){
			InemailInfo inEmailInfo = (InemailInfo)result[i];
			DynaBeanDTO dbd = new DynaBeanDTO();
			dbd.set("id", inEmailInfo.getId());
			dbd.set("takeList", inEmailInfo.getTakeList());
			dbd.set("emailTitle", inEmailInfo.getEmailTitle());
			dbd.set("emailTime",TimeUtil.getTheTimeStr(inEmailInfo.getCreateTime()));
			finalList.add(dbd);
		}
		return finalList;
	}

	public int getEmailIndexSize() {
		// TODO Auto-generated method stub
		return EMAIL_NUM;
	}

	/**
	 * @describe �����ʼ���������Ϣ,���ʼ����˵���Ϣ
	 * @param dto ����  IBaseDTO ������Ϣ
	 * @return inemailInfo ����  InemailInfo ����ҳ���ϲ������Ϣ
	 * 
	 */
	private InemailInfo addSendUserInfo(IBaseDTO dto,List adjunctList){
		InemailInfo inemailInfo = new InemailInfo();
	    String key = ks.getNext("inemail_info");
	    inemailInfo.setId(key);
	    inemailInfo.setSendUser(dto.get("sendUser").toString());
	    inemailInfo.setTakeUser(dto.get("takeUser").toString());
	    inemailInfo.setTakeList(dto.get("takeList").toString());
	    inemailInfo.setCopyList(dto.get("copyList").toString());
	    inemailInfo.setSecretList(dto.get("copyList").toString());
	    inemailInfo.setEmailTitle(dto.get("emailTitle").toString());
	    inemailInfo.setEmailInfo(dto.get("emailInfo").toString());
	    inemailInfo.setCreateTime(TimeUtil.getNowTime());
	    inemailInfo.setSendType("");
	    //inemailInfo.setEmailType("2".toString());
	    inemailInfo.setEmailType(dto.get("emailType").toString());
	    inemailInfo.setEmailSign("1".toString());
	    inemailInfo.setOperSign("2".toString());
	    //��ʶΪ�����ʼ�
	    inemailInfo.setInorout("1".toString());
	    inemailInfo.setIssend(dto.get("issend").toString());
	    inemailInfo.setDelSign("n".toUpperCase());
	    
	    //������Ϣ
	    if (adjunctList!=null) {
	    	Set set = new HashSet();
			Iterator iter = adjunctList.iterator();
			
			while(iter.hasNext()){
				String adjunctAddr = iter.next().toString(); 
				//
				InadjunctInfo inadjunctInfo = new InadjunctInfo();
				inadjunctInfo.setId(ks.getNext("inadjunct_info"));
				inadjunctInfo.setInemailInfo(inemailInfo);
				inadjunctInfo.setAdjunctAddr(adjunctAddr);
				inadjunctInfo.setAdjunctName(adjunctAddr);
				inadjunctInfo.setCreateTime(TimeUtil.getNowTime());
				inadjunctInfo.setOperUser(dto.get("sendUser").toString());
				set.add(inadjunctInfo);
			}
			inemailInfo.setInadjunctInfos(set);
		}
		
		return inemailInfo;
	}
	
	/**
	 * @describe �����ʼ���������Ϣ���ʼ�����˭
	 * @param dto ����  IBaseDTO ������Ϣ
	 * @return inemailInfo ����  InemailInfo ����ҳ���ϲ������Ϣ
	 * 
	 */
	private InemailInfo addTakeUserInfo(IBaseDTO dto,List adjunctList){
		InemailInfo inemailInfo = new InemailInfo();
	    String key = ks.getNext("inemail_info");
	    inemailInfo.setId(key);
	    inemailInfo.setSendUser(dto.get("sendUser").toString());
	    inemailInfo.setTakeUser(dto.get("takeUser").toString());
	    inemailInfo.setTakeList(dto.get("takeList").toString());
	    inemailInfo.setCopyList(dto.get("copyList").toString());
	    inemailInfo.setSecretList(dto.get("copyList").toString());
	    inemailInfo.setEmailTitle(dto.get("emailTitle").toString());
	    inemailInfo.setEmailInfo(dto.get("emailInfo").toString());
	    inemailInfo.setCreateTime(TimeUtil.getNowTime());
	    inemailInfo.setSendType(dto.get("sendType").toString());
	    inemailInfo.setEmailType("1".toString());
	    inemailInfo.setEmailSign("1".toString());
	    //��ʶΪ�����ʼ�
	    inemailInfo.setInorout("1".toString());
	    inemailInfo.setOperSign("2".toString());
	    inemailInfo.setDelSign("n".toUpperCase());
	    //������Ϣ
	    if (adjunctList!=null) {
	    	Set set = new HashSet();
			Iterator iter = adjunctList.iterator();
			
			while(iter.hasNext()){
				String adjunctAddr = iter.next().toString(); 
				//
				InadjunctInfo inadjunctInfo = new InadjunctInfo();
				inadjunctInfo.setId(ks.getNext("inadjunct_info"));
				inadjunctInfo.setInemailInfo(inemailInfo);
				inadjunctInfo.setAdjunctAddr(adjunctAddr);
				inadjunctInfo.setAdjunctName(adjunctAddr);
				inadjunctInfo.setCreateTime(TimeUtil.getNowTime());
				inadjunctInfo.setOperUser(dto.get("sendUser").toString());
				set.add(inadjunctInfo);
			}
			inemailInfo.setInadjunctInfos(set);
		}
		return inemailInfo;
	}
	
	/**
	 * @describe �õ������û��б�
	 * @param copylist ����  String �������б�
	 * @param searctList ����  String �������б�
	 * @return  ����  
	 * 
	 */

	private List getUserList(String copylist,String searctlist){
		List finalList = new ArrayList();
		
		List copyList = new ArrayList();
		List searctList = new ArrayList();
		StringTokenizer strToken1 = new StringTokenizer(copylist,",");
		String tmpCopyList = "";
		while (strToken1.hasMoreTokens()) {
			tmpCopyList = strToken1.nextToken();
			copyList.add(tmpCopyList);
		}
		StringTokenizer strToken2 = new StringTokenizer(copylist,",");
		String tmpSearctList = "";
		while(strToken2.hasMoreTokens()){
			tmpSearctList = strToken2.nextToken();
			searctList.add(tmpSearctList);
		}
		for (int i = 0; i < copyList.size(); i++) {
			String tmp = copyList.get(i).toString();
			for (int j = 0; j < searctList.size(); j++) {
				if (tmp.equals(searctList.get(j).toString())) {
					finalList.add(tmp);
				}
			}
		}
		//copyList.removeAll(finalList);
		return finalList;
	}


	public boolean saveEmailToAddresser(IBaseDTO dto, List adjunctList,String mailType) {
		// TODO Auto-generated method stub
		boolean flag = false;
		//���ʼ�
		if (mailType.equals("send")) {
			dto.set("emailType", "2");
			dao.saveEntity(addSendUserInfo(dto,adjunctList));
			String copyList = dto.get("copyList").toString();
			//
			String searctlist = dto.get("secretList").toString();
			List list = getUserList(copyList,searctlist);
			Iterator it = list.iterator();
			while(it.hasNext()){
				dto.set("takeUser", it.next().toString());
				//
				dao.saveEntity(addTakeUserInfo(dto,adjunctList));
			}
			flag = true;
		}
		//�ظ�
		if (mailType.equals("answer")) {
			String copyList = dto.get("copyList").toString();
			String searctlist = dto.get("secretList").toString();
			List list = getUserList(copyList,searctlist);
			Iterator it = list.iterator();
			while(it.hasNext()){
				dto.set("takeUser", it.next().toString());
				dao.saveEntity(addTakeUserInfo(dto,adjunctList));
			}
			flag = true;
		}
		//ת��
		if (mailType.equals("transmit")) {
			String copyList = dto.get("copyList").toString();
			String searctlist = dto.get("secretList").toString();
			List list = getUserList(copyList,searctlist);
			Iterator it = list.iterator();
			while(it.hasNext()){
				dto.set("takeUser", it.next().toString());
				dao.saveEntity(addTakeUserInfo(dto,adjunctList));
			}
			flag = true;
		}
		return flag;
	}

	public boolean saveEmailToDraft(IBaseDTO dto, List adjunctList) {
		// TODO Auto-generated method stub
		boolean flag = false;
		dto.set("emailType", "3");
		dao.saveEntity(addSendUserInfo(dto,adjunctList));
		flag = true;
		return flag;
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

	public IBaseDTO getInEmailInfo(String id) {
		// TODO Auto-generated method stub
        InemailInfo inemailInfo = (InemailInfo)dao.loadEntity(InemailInfo.class,id);
        IBaseDTO dto=new DynaBeanDTO();
        dto.set("sendUser",inemailInfo.getSendUser());
        dto.set("takeList",inemailInfo.getTakeList());
        dto.set("copyList",inemailInfo.getCopyList());
        dto.set("emailTitle",inemailInfo.getEmailTitle());
        dto.set("emailInfo",inemailInfo.getEmailInfo());
        dto.set("chk",inemailInfo.getSendType());
        EmailSearch emailSearch = new EmailSearch();
        //dto.set("adjuctlist", );
        Object[] result = null;
        result=(Object[])dao.findEntity(emailSearch.searchUploadListInfo(inemailInfo));
        List l = new ArrayList();
        for(int i = 0,size=result.length;i<size;i++){
        	InadjunctInfo inadjunctInfo = (InadjunctInfo)result[i];
        	DynaBeanDTO dbd = new DynaBeanDTO();
        	dbd.set("name", inadjunctInfo.getAdjunctName());
        	dbd.set("url", TOMCAT_HOME_ADDRESS + inadjunctInfo.getAdjunctAddr());
        	l.add(dbd);
        }
        dto.set("adjunctInfo", l);
        return dto;
	}

	public List getEmailBoxList(String userkey) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IBaseDTO> userList() {
		// TODO Auto-generated method stub
		List<IBaseDTO> result=new ArrayList<IBaseDTO>();
		MyQuery mq=new MyQueryImpl();
		DetachedCriteria dc=DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.ne("deleteMark","-1"));
		mq.setDetachedCriteria(dc);
		Object[] re=dao.findEntity(mq);
		for(int i=0,size=re.length;i<size;i++)
		{
			IBaseDTO dto=new DynaBeanDTO();
			SysUser su=(SysUser)re[i];
			dto.set("id",su.getUserId());
			dto.set("name",su.getUserName());
			result.add(dto);
		}
		return result;
	}

}
