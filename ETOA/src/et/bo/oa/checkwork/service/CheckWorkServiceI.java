package et.bo.oa.checkwork.service;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * 
 * 考勤管理 接口
 * 
 * 
 * @author zkhuali Inc :wjlovegirl 2006-05-09
 * 
 */
public interface CheckWorkServiceI {

	/**
	 * 
	 * 获得考勤记录
	 * 
	 * 
	 * @return
	 */
	public Object[] seletCheckList(IBaseDTO infoDTO,PageInfo pageInfo);
	
	/**
	 * <p> 获得查询记录数 </p>
	 * @return：查询记录数
	 */
	public int getCheckListSize();
	/**
	 * @describe 取得人明列表LabelValueBean
	 * @param
	 * @return List<LabelValueBean>
	 * 
	 */
	public  List<LabelValueBean> getNameList();
	/**
	 * 
	 * 获得迟到,早退考勤记录
	 * 
	 * 
	 * @return
	 */
	public List seletLaterOrEarlyCheckList(IBaseDTO infoDTO);
	/**
	 *   
		 * @describe 外出查询
		 * @param  IBaseDTO infoDBaseDTO 类型  
		 * @return List类型  
		 *
	 */
	public List seletWaichuList(IBaseDTO infoDTO);
	/***
	 * 
		 * @describe 请假查询
		 * @param  IBaseDTO infoDTO  
		 * @return List 类型  
		 *
	 */
	public List selecQingjiaList(IBaseDTO infoDTO);
	/**
	 * 
		 * @describe 出差
		 * @param  IBaseDTO infoDTO  
		 * @return List 类型  
		 *
	 */
	public List selectChuchaiList(IBaseDTO infoDTO);
    
}
