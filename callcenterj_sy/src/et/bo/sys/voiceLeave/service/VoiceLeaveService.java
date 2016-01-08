package et.bo.sys.voiceLeave.service;

/*
 * @(#)CustinfoService.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>语音留言</p>
 * 
 * @version 2008-03-19
 * @author wangwenquan
 */

public interface VoiceLeaveService {
	/*
        List list = custinfoService.custinfoQuery(dto,pageInfo);
        int size = custinfoService.getCustinfoSize();
	 */
	/**
	 * 查询数据列表,返回记录的list。
	 * 取得查询列表数据。
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * @return 数据的list
	 */
	public List voiceLeaveQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * 根据id得到详细信息放入dto里
	 * @param id
	 * @return
	 */
	public IBaseDTO getVoiceLeaveInfo(String id);
	/**
	 * 执行处理 将ifdispose 置 1 disposeSuggest 设值
	 * @param dto 数据传输对象
	 */
	public void execDoWith(IBaseDTO dto);
	/**
	 * 查询数据列表的条数。
	 * 取得查询列表的条数。
	 * @return 得到list的条数
	 */
	public int getVoiceLeaveSize(); 
	/**
	 * 取语音节点列表
	 * 
	 * @return 语音留言数据的list
	 */
	public List<LabelValueBean> getVoiceNodeList();
	/**
	 * 根据id去nickName
	 * 
	 */
	public String getNickNameById(String id);
}


