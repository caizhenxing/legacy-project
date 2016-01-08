/*
 * <p>Title:       简单的标题</p>
 * <p>Description: 稍详细的说明</p>
 * <p>Copyright:   Copyright (c) 2004</p>
 * <p>Company:     </p>
 */
package et.bo.oa.assissant.conference.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import et.bo.common.ListValueService;
import et.bo.oa.assissant.conference.service.ConferenceService;
import et.bo.oa.workflow.service.OaWorkFlowService;
import et.po.SynodNote;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;
import excellence.framework.base.query.MyQuery;

public class ConferenceServiceImpl implements ConferenceService {

	private BaseDAO dao=null;
	private KeyService ks = null;
	private ListValueService listValueService =null;
	private OaWorkFlowService oawfs=null;
	/**
	 * @return Returns the ks.
	 */
	public KeyService getKs() {
		return ks;
	}

	/**
	 * @param ks The ks to set.
	 */
	public void setKs(KeyService ks) {
		this.ks = ks;
	}

	private SynodNote createPoByDTO(IBaseDTO dto)
	{
		TimeUtil a;
		SynodNote sn =new SynodNote();
		sn.setId(dto.get("id") !=null && !"".equals((String)dto.get("id")) ? (String)dto.get("id") :ks.getNext("synod_note"));
		String sdate =(String)dto.get("synodDate");
		String hour =(String)dto.get("synodHour");
		String sdt =sdate+" "+hour;
		Date dt =TimeUtil.getTimeByStr(sdt,"yyyy-MM-dd HH:mm");
//		
		sn.setSynodDate(null !=dt ?dt :new Date());
//		sn.setSynodDate(new Date());
		sn.setSynodAddr(null !=dto.get("synodAddr") && !"".equals((String)dto.get("synodAddr")) ?(String)dto.get("synodAddr") : "");
//		
//		
		sn.setSynodOwner(null !=dto.get("synodOwner") && !"".equals((String)dto.get("synodOwner")) ?(String)dto.get("synodOwner") : "");
		sn.setSynodPeople(null !=dto.get("synodPeople") && !"".equals((String)dto.get("synodPeople")) ?(String)dto.get("synodPeople") : "");
		sn.setSynodTopic(null !=dto.get("synodTopic") && !"".equals((String)dto.get("synodTopic")) ?(String)dto.get("synodTopic") : "");
		sn.setSynodOutline(null !=dto.get("synodOutline") && !"".equals((String)dto.get("synodOutline")) ?(String)dto.get("synodOutline") : "");
		String a1 =(String)dto.get("applyTime");
		sn.setApplyTime(null !=dto.get("applyTime") && !"".equals((String)dto.get("applyTime"))  ?TimeUtil.getTimeByStr((String)dto.get("applyTime"),"yyyy-MM-dd HH:mm") : new Date());
		sn.setFlowId(null !=dto.get("flowId") && !"".equals((String)dto.get("flowId")) ?(String)dto.get("flowId") : "");
		sn.setRemark(null !=dto.get("remark") && !"".equals((String)dto.get("remark")) ?(String)dto.get("remark") : "");
		sn.setExamId(null !=dto.get("examId") && !"".equals((String)dto.get("examId")) ?(String)dto.get("examId") : "");
		sn.setExamResult(null !=dto.get("examResult") && !"".equals((String)dto.get("examResult")) ?(String)dto.get("examResult") : "-1");
		//-1 --> 为结束
		sn.setEndFlag(null !=dto.get("endFlag") && !"".equals((String)dto.get("endFlag")) ?(String)dto.get("endFlag") : "-1");
		sn.setEndDoc(null !=dto.get("endDoc") && !"".equals((String)dto.get("endDoc")) ?(String)dto.get("endDoc") : "");
		sn.setSynodFile(null !=dto.get("synodFile") && !"".equals((String)dto.get("synodFile")) ?(String)dto.get("synodFile") : "");
		return sn;
	}
	
	private IBaseDTO createDTOByPo(IBaseDTO dto, SynodNote sn)
	{
		dto.set("id",sn.getId());
		String sdt =TimeUtil.getTheTimeStr(sn.getSynodDate(),"yyyy-MM-dd HH:mm");
		
		String [] sa =sdt.split(" ");
		String sdate =sa[0];
		String shour =sa[1];
		dto.set("synodDate",sdate);
		dto.set("synodHour",shour);
		dto.set("synodAddr",sn.getSynodAddr());
		dto.set("synodOwner",sn.getSynodOwner());
		dto.set("synodPeople",sn.getSynodPeople());
		dto.set("synodTopic",sn.getSynodTopic());
		dto.set("synodOutline",sn.getSynodOutline());
		String appTime =TimeUtil.getTheTimeStr(sn.getApplyTime(),"yyyy-MM-dd HH:mm");
		dto.set("applyTime",appTime);
		dto.set("flowId",sn.getFlowId());
		dto.set("remark",sn.getRemark());
		dto.set("examId",sn.getExamId());
		dto.set("examResult",sn.getExamResult());
		dto.set("endFlag",sn.getEndFlag());
		dto.set("endDoc",sn.getEndDoc());
		dto.set("synodFile",sn.getSynodFile());
		return dto;
	}
	
	public boolean insertC(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
		SynodNote sn =createPoByDTO(dto);
		dao.saveEntity(sn);
		oawfs.createAndNext("7",(String)dto.get("userid"),null,null,sn.getId());
//		
		return true;
	}

	public boolean updateC(IBaseDTO dto) {
		//TODO 需要写出方法的具体实现
		SynodNote sn =createPoByDTO(dto);
//		
		dao.updateEntity(sn);
		return true;
	}

	public boolean deleteC(String id) {
		//TODO 需要写出方法的具体实现
		SynodNote sn =(SynodNote)dao.loadEntity(SynodNote.class,id);
		if(null ==sn)
		{
			return false;
		}
		else
		{
			dao.removeEntity(sn);
			return true;
		}
	}

	public IBaseDTO loadC(String id) {
		//TODO 需要写出方法的具体实现
		IBaseDTO dto =new DynaBeanDTO();
		SynodNote sn =(SynodNote)dao.loadEntity(SynodNote.class,id);
		createDTOByPo(dto,sn);
		return dto;
	}

	public List searchC(IBaseDTO dto ,PageInfo pi) {
		//TODO 需要写出方法的具体实现
		ConferenceHelp ch =new ConferenceHelp();
		MyQuery mq =ch.searchCMQ(dto ,pi);
		Object [] o =dao.findEntity(mq);
		ArrayList l =new ArrayList();
		if(null !=o && o.length>0)
		{
			for(Object oo : o)
			{
				IBaseDTO tdto =new DynaBeanDTO();
				createDTOByPo(tdto,(SynodNote)oo);
				l.add(tdto);
			}
			return l;
		}
		return l;
	}
	
	public List searchEC(IBaseDTO dto ,PageInfo pi) {
		//TODO 需要写出方法的具体实现
		ConferenceHelp ch =new ConferenceHelp();
		MyQuery mq =ch.searchExamCMQ(dto ,pi);
		Object [] o =dao.findEntity(mq);
		ArrayList l =new ArrayList();
		if(null !=o && o.length>0)
		{
			for(Object oo : o)
			{
				IBaseDTO tdto =new DynaBeanDTO();
				createDTOByPo(tdto,(SynodNote)oo);
				l.add(tdto);
			}
			return l;
		}
		return l;
	}
	
	public int searchCSize(IBaseDTO dto,PageInfo pi)
	{
		ConferenceHelp ch =new ConferenceHelp();
		MyQuery mq =ch.searchCMQ(dto ,pi);
		int i =dao.findEntitySize(mq);
		return i;
	}

	public int searchECSize(IBaseDTO dto,PageInfo pi)
	{
		ConferenceHelp ch =new ConferenceHelp();
		MyQuery mq =ch.searchExamCMQ(dto ,pi);
		int i =dao.findEntitySize(mq);
		return i;
	}
	
	public boolean setWF(String id,String fid)
	{
		SynodNote sn =(SynodNote)dao.loadEntity(SynodNote.class,id);
		if(null ==sn)
		{
			return false;
		}
		else
		{
			sn.setFlowId(fid);
			return true;
		}
		
	}
	
	public boolean setDocument(String id, String documentId)
	{
		SynodNote sn =(SynodNote)dao.loadEntity(SynodNote.class,id);
		if(null ==sn)
		{
			return false;
		}
		else
		{
			sn.setSynodFile(documentId);
			return true;
		}
	}

	public boolean examine(String id, IBaseDTO dto )
	{
		SynodNote sn =(SynodNote)dao.loadEntity(SynodNote.class,id);
		if(null ==sn)
		{
			return false;
		}
		if("".equals((String)dto.get("examResult")))
		{
			dto.set("examResult","-1");
		}
		String flowId =null !=dto.get("flowId")?(String)dto.get("flowId"):"";			
		String examId =(String)dto.get("examId");
		String examResult =(String)dto.get("examResult");
		sn.setFlowId(flowId);
		sn.setExamId(examId);
		sn.setExamResult(examResult);
		dao.updateEntity(sn);
		return true;
	}
	
	public boolean endOfConference(String did, IBaseDTO dto)
	{
		SynodNote sn =(SynodNote)dao.loadEntity(SynodNote.class,did);
		if(null ==sn)
			return false;
		String endDoc = (null !=dto.get("endDoc") && !"".equals((String)dto.get("endDoc")))?(String)dto.get("endDoc"):"";
		String synodFile =(null !=dto.get("synodFile") && !"".equals((String)dto.get("synodFile")))?(String)dto.get("synodFile"):"";
		String endFlag ="1";
		sn.setEndFlag(endFlag);
		sn.setEndDoc(endDoc);
		dao.saveEntity(sn);
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO 需要写出方法的具体实现
		
	}

	/**
	 * @return Returns the dao.
	 */
	public BaseDAO getDao() {
		return dao;
	}

	/**
	 * @param dao The dao to set.
	 */
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return Returns the listValueService.
	 */
	public ListValueService getListValueService() {
		return listValueService;
	}

	/**
	 * @param listValueService The listValueService to set.
	 */
	public void setListValueService(ListValueService listValueService) {
		this.listValueService = listValueService;
	}

	/**
	 * @return Returns the oawfs.
	 */
	public OaWorkFlowService getOawfs() {
		return oawfs;
	}

	/**
	 * @param oawfs The oawfs to set.
	 */
	public void setOawfs(OaWorkFlowService oawfs) {
		this.oawfs = oawfs;
	}

}
