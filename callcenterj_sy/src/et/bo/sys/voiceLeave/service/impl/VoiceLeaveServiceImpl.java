/*
 * @(#)CustinfoServiceImpl.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
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
 * <p>语音留言</p>
 * 
 * @version 2008-03-19
 * @author 王文权
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
	 * 查询数据列表,返回记录的list。
	 * 取得查询问题列表数据。
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * @return 数据的list
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
	 * 根据id得到详细信息放入dto里
	 * @param id
	 * @return
	 */
	public IBaseDTO getVoiceLeaveInfo(String id)
	{
		CcVoiceLeave po = (CcVoiceLeave)dao.loadEntity(CcVoiceLeave.class, new Integer(id));
		return voiceLeaveToDynaBeanDTO(po);
	}
	/**
	 * 查询方法的 po 转 dto
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
				dto.set("ifDispose", "已处理");
			}
			else
			{
				dto.set("ifDispose", "未处理");
			}
			dto.set("caller", po.getCaller());
			dto.set("disposeSuggest", po.getDisposeSuggest());
		}
		return dto;
	}
	/**
	 * 执行处理 将ifdispose 置 1 disposeSuggest 设值
	 * @param dto 数据传输对象
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
	 * 查询数据列表的条数。
	 * 取得问题查询列表的条数。
	 * @return 得到list的条数
	 */
	public int getVoiceLeaveSize() {
		
		return num;
	
	}
	/**
	 * 取语音节点列表
	 * 
	 * @return 语音留言数据的list
	 */
	public List<LabelValueBean> getVoiceNodeList()
	{
		List<LabelValueBean> l = new ArrayList<LabelValueBean>();
		l = cts.getLabelVaList("IVRMultiVoice", false);
		return l;
	}
	/**
	 * 根据id去nickName
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
