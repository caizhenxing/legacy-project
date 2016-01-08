package et.bo.screen.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

public interface SpecialSurveyService {

	public int getRecordSize();

	/**
	 * 添加一条调查信息
	 * @param dto
	 */
	public void addSpecialSurvey(IBaseDTO dto);

	/**
	 * 删除一条调查信息
	 * @param id
	 */
	public void delSpecialSurvey(String id);

	/**
	 * 修改一条调查信息
	 * @param dto
	 * @return  boolean
	 */
	public boolean updateSpecialSurvey(IBaseDTO dto);

	/**
	 * 按时间 标题 关键字 撰稿人 模糊查询
	 * @param dto
	 * @param pi
	 * @return List
	 */
	public List getSSSList(IBaseDTO dto, PageInfo pi);

	/**
	 * 按id查找特定调查信息
	 * @param id
	 * @return 单条记录
	 */
	public IBaseDTO getObjById(String id);
	/**
	 * 得到大屏幕信息的列表
	 * @return
	 */
	public List screenList();

}