/*
 * @(#)CustinfoServiceImpl.java	 2008-03-19
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */

package et.bo.sys.voiceLeave.service.impl;

import java.util.ArrayList;
import java.util.List;

import et.bo.sys.voiceLeave.service.VoiceLeaveService;
import et.po.CcVoiceLeave;
import excellence.common.classtree.ClassTreeService;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.common.util.Constants;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>��������</p>
 * 
 * @version 2008-03-19
 * @author ����Ȩ
 */

public class VoiceLeaveServiceImpl implements VoiceLeaveService {
	
	BaseDAO dao = null;
	private int num = 0;
	public KeyService ks = null;
	private ClassTreeService cts = null;
	
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
	
	public ClassTreeService getCts() {
		return cts;
	}
	public void setCts(ClassTreeService cts) {
		this.cts = cts;
	}

	/**
	 * ��ѯ�����б�,���ؼ�¼��list��
	 * ȡ�ò�ѯ�����б����ݡ�
	 * @param dto ���ݴ������
	 * @param pi ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List voiceLeaveQuery(IBaseDTO dto, PageInfo pi) {
	
		List list = new ArrayList();
		try
		{
		VoiceLeaveHelp h = new VoiceLeaveHelp();
		//System.out.println(pi+".................."+dto);
		Object[] result = (Object[]) dao.findEntity(h.voiceLeaveQuery(dto, pi));
		num = dao.findEntitySize(h.voiceLeaveQuery(dto, pi));
		for (int i = 0, size = result.length; i < size; i++) {
			CcVoiceLeave po = (CcVoiceLeave) result[i];
			list.add(voiceLeaveToDynaBeanDTO(po));
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}
	/**
	 * ����id�õ���ϸ��Ϣ����dto��
	 * @param id
	 * @return
	 */
	public IBaseDTO getVoiceLeaveInfo(String id)
	{
		CcVoiceLeave po = (CcVoiceLeave)dao.loadEntity(CcVoiceLeave.class, new Integer(id));
		return voiceLeaveToDynaBeanDTO(po);
	}
	/**
	 * ��ѯ������ po ת dto
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO voiceLeaveToDynaBeanDTO(CcVoiceLeave po){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		if(po != null)
		{
			dto.set("id", po.getId());
			dto.set("beginTime", po.getBeginTime());
			dto.set("endTime", po.getEndTime());
			String wavPath = po.getWavPath();
			String httpPath = Constants.getProperty("voiceLeaveServerAddress");
			wavPath = wavPath.substring(wavPath.lastIndexOf("\\"),wavPath.length());
			httpPath = httpPath+wavPath;
			dto.set("wavPath", httpPath);
			String ifDispose = po.getIfDispose();
			if(ifDispose!=null&&"1".equals(ifDispose))
			{
				dto.set("ifDispose", "�Ѵ���");
			}
			else
			{
				dto.set("ifDispose", "δ����");
			}
			dto.set("caller", po.getCaller());
			dto.set("disposeSuggest", po.getDisposeSuggest());
		}
		return dto;
	}
	/**
	 * ִ�д��� ��ifdispose �� 1 disposeSuggest ��ֵ
	 * @param dto ���ݴ������
	 */
	public void execDoWith(IBaseDTO dto)
	{
		String id = (String)dto.get("id");
		String disposeSuggest = (String)dto.get("disposeSuggest");
		if(id!=null&&"".equals(id.trim())==false)
		{
			if(disposeSuggest!=null && "".equals(disposeSuggest.trim())==false)
			{
				Integer i_id = new Integer(id);
				CcVoiceLeave cv = (CcVoiceLeave)dao.loadEntity(CcVoiceLeave.class, i_id);
				if(cv!=null)
				{
					cv.setDisposeSuggest(disposeSuggest);
					cv.setIfDispose("1");
					dao.saveEntity(cv);
				}
			}
		}
	}
	/**
	 * ��ѯ�����б��������
	 * ȡ�������ѯ�б��������
	 * @return �õ�list������
	 */
	public int getVoiceLeaveSize() {
		
		return num;
	
	}
	/**
	 * ȡ�����ڵ��б�
	 * 
	 * @return �����������ݵ�list
	 */
	public List<LabelValueBean> getVoiceNodeList()
	{
		List<LabelValueBean> l = new ArrayList<LabelValueBean>();
		l = cts.getLabelVaList("IVRMultiVoice", false);
		return l;
	}
	/**
	 * ����idȥnickName
	 * 
	 */
	public String getNickNameById(String id)
	{
		try
		{
			return cts.getNickNameById(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
}
