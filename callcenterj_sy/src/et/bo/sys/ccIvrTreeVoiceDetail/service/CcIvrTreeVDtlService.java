package et.bo.sys.ccIvrTreeVoiceDetail.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.common.tools.LabelValueBean;
import excellence.framework.base.dto.IBaseDTO;

public interface CcIvrTreeVDtlService {
	/**
	 * @describe addCcIvrTreeInfo
	 * @param
	 * @return void
	 */ 
	public void addCcIvrTreeInfoText(IBaseDTO dto);
	/**
	 * @describe addCcIvrTreeInfo
	 * @param
	 * @return void
	 */ 
	public void addCcIvrTreeInfo(IBaseDTO dto);
	/**
	 * @describe 修改市场价格
	 * @param
	 * @return void
	 */ 
	public boolean updateCcIvrTreeInfo(IBaseDTO dto);
	/**
	 * @describe 删除市场价格
	 * @param
	 * @return void
	 */ 
	public void delCcIvrTreeInfo(String id);
	
	
	/**
	 * @describe 查询市场价格列表
	 * @param
	 * @return List
	 */ 
	public List operCcIvrTreeInfoList(IBaseDTO dto, PageInfo pi);
	
	
	/**
	 * @describe 取得查询条数
	 * @param
	 * @return int
	 */ 
	
	public int getCcIvrTreeInfoSize();
	
	
	/**
	 * @describe 根据Id取得信息
	 * @param
	 * @return dto(user类型)
	 */ 
	public IBaseDTO getCcIvrTreevoiceDetail(String id);
	/**
	 * 得到ivr id label List给视图用
	 * @return List LabelValueBeanList
	 */
	public List<LabelValueBean> getIvrLVList();
}
