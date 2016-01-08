/**
 * 	@(#)HandsetNoteServiceImpl.java   Sep 26, 2006 1:50:50 PM
 *	 。 
 *	 
 */
package et.bo.oa.communicate.handsetnote.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.news.service.impl.ArticleSearch;
import et.bo.oa.communicate.handsetnote.service.HandsetNoteService;
import et.po.HandsetNoteInfo;
import et.po.NewsArticle;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * @author zhang
 * @version Sep 26, 2006
 * @see
 */
public class HandsetNoteServiceImpl implements HandsetNoteService {
	
	//成功删除(标志位)
	private String DEL_SUCCESS = "Y";
	//未删除(标志位)
	private String DEL_FAIL = "N";
	//状态为保存，未发送
	private String STATE_SAVE = "1";
	//状态为发送
	private String STATE_SEND = "2";
	//状态为接收
	private String STATE_GET = "3";
	
	private int HandSet_NOTE_NUM = 0;
	
	private BaseDAO dao=null;
    
    private KeyService ks = null;
    
    //添加
    private HandsetNoteInfo createHandsetNoteInfo(IBaseDTO dto){
    	HandsetNoteInfo handsetNoteInfo = new HandsetNoteInfo();
    	handsetNoteInfo.setId(ks.getNext("handset_note_info"));
    	handsetNoteInfo.setHandsetNum(dto.get("handsetnum").toString());
    	handsetNoteInfo.setHandsetInfo(dto.get("handsetinfo").toString());
    	handsetNoteInfo.setSendState(dto.get("sendstate").toString());
    	handsetNoteInfo.setSendTime(TimeUtil.getNowTime());
    	handsetNoteInfo.setAppointSendTime(TimeUtil.getTimeByStr(dto.get("appointsendtime").toString()));
    	handsetNoteInfo.setDelSign(DEL_FAIL);
    	handsetNoteInfo.setRemark(dto.get("remark").toString());
    	return handsetNoteInfo;
    }

	public boolean saveHandsetNote(IBaseDTO dto, String sendtype) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if (sendtype.equals("save")) {
			dto.set("sendstate", STATE_SAVE);
	        dao.saveEntity(createHandsetNoteInfo(dto));
	        flag = true;
		}
		if (sendtype.equals("send")) {
			dto.set("sendstate", STATE_SEND);
	        dao.saveEntity(createHandsetNoteInfo(dto));
	        flag = true;
		}
        return flag;
	}
	
	//修改
    private HandsetNoteInfo upHandsetNoteInfo(IBaseDTO dto){
    	HandsetNoteInfo handsetNoteInfo = new HandsetNoteInfo();
    	handsetNoteInfo.setId(dto.get("id").toString());
    	handsetNoteInfo.setHandsetNum(dto.get("handsetnum").toString());
    	handsetNoteInfo.setHandsetInfo(dto.get("handsetinfo").toString());
    	handsetNoteInfo.setSendState(dto.get("sendstate").toString());
    	//handsetNoteInfo.setSendTime(TimeUtil.getTimeByStr(dto.get("sendtime").toString()));
    	//handsetNoteInfo.setAppointSendTime(TimeUtil.getTimeByStr(dto.get("appointsendtime").toString()));
    	handsetNoteInfo.setDelSign(DEL_FAIL);
    	handsetNoteInfo.setRemark(dto.get("remark").toString());
    	return handsetNoteInfo;
    }
    
	public boolean updateHandsetNote(IBaseDTO dto) {
		// TODO Auto-generated method stub
        boolean flag = false;
        dao.updateEntity(upHandsetNoteInfo(dto));
        flag = true;
        return flag;
	}

	public int getHandsetIndexSize() {
		// TODO Auto-generated method stub
		return HandSet_NOTE_NUM;
	}

	public List handsetIndex(IBaseDTO dto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
        List l = new ArrayList();
        HandsetNoteSearch hnSearch = new HandsetNoteSearch();
        Object[] result = (Object[]) dao.findEntity(hnSearch.searchHandsetInfo(dto, pageInfo));
        int s = dao.findEntitySize(hnSearch.searchHandsetInfo(dto, pageInfo));
        HandSet_NOTE_NUM = s;
        for (int i = 0, size = result.length; i < size; i++) {
        	HandsetNoteInfo hnInfo = (HandsetNoteInfo) result[i];
            DynaBeanDTO dbd = new DynaBeanDTO();
            dbd.set("id", hnInfo.getId());
            dbd.set("handsetnum", hnInfo.getHandsetNum());
            dbd.set("handsetinfo", hnInfo.getHandsetInfo());
            dbd.set("sendtime",TimeUtil.getTheTimeStr(hnInfo.getSendTime()));
            l.add(dbd);
        }
        return l;
	}
	
	public boolean delHandsetNote(String[] selectIt) {
		// TODO Auto-generated method stub
        boolean flag = false;
        for (int i = 0; i < selectIt.length; i++) {
            String id = selectIt[i];
            HandsetNoteInfo handsetNoteInfo = (HandsetNoteInfo) dao.loadEntity(
            		HandsetNoteInfo.class, id);
            handsetNoteInfo.setDelSign(DEL_SUCCESS);
            dao.updateEntity(handsetNoteInfo);
            flag = true;
        }
        return flag;
	}

	public boolean delHandsetNoteForever(String[] selectIt) {
		// TODO Auto-generated method stub
        boolean flag = false;
        for (int i = 0; i < selectIt.length; i++) {
            String id = selectIt[i];
            HandsetNoteInfo handsetNoteInfo = (HandsetNoteInfo) dao.loadEntity(
            		HandsetNoteInfo.class, id);
            dao.removeEntity(handsetNoteInfo);
            flag = true;
        }
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

	public IBaseDTO getHandsetNoteInfo(String id) {
		// TODO Auto-generated method stub
		HandsetNoteInfo handsetNoteInfo = (HandsetNoteInfo)dao.loadEntity(HandsetNoteInfo.class,id);
		IBaseDTO dto=new DynaBeanDTO();
		dto.set("id", handsetNoteInfo.getId());
		dto.set("handsetnum", handsetNoteInfo.getHandsetNum());
		dto.set("handsetinfo", handsetNoteInfo.getHandsetInfo());
		dto.set("sendstate", handsetNoteInfo.getSendState());
		dto.set("sendtime", handsetNoteInfo.getSendTime());
		dto.set("appointsendtime", handsetNoteInfo.getAppointSendTime());
		return dto;
	}

}
