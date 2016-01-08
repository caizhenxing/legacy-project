/**
 * @(#)OperatorStatisticService.java 1.0 //
 * 
 *  。  
 * 
 */
package et.bo.pcc.operatorStatistic.service;

import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @describe 坐席员工作情况统计接口
 * @author 叶浦亮
 * @version 1.0, 2006--10-09 //
 * @see
 */
public interface OperatorStatisticService {
    /**
	 * @describe 签入记录添加 
	 * @param    String operator(操作员id)
	 * @return
	 */
	public void addSignIn(String operator);
	/**
	 * @describe 签出记录添加
	 * @param    String operator(操作员id)
	 * @return
	 */
	public void addSignOut(String operator);
	/**
	 * @describe 入席记录添加
	 * @param    String operator(操作员id)
	 * @return
	 */
	public void addSetting(String operator);
	/**
	 * @describe 离席记录添加
	 * @param    String operator(操作员id)
	 * @return
	 */
	public void addOutSetting(String operator);
	/**
	 * @describe 接听电话记录添加 
	 * @param    String operator(操作员id)
	 * @return
	 */
	public void addAnswerPhone(String operator ,Date date);
	/**
	 * @describe 挂断电话记录添加
	 * @param    String operator(操作员id)
	 * @return
	 */
	public void addDisconnectPhone(String operator ,Date date);

	/**
	 * @describe 坐席员工作情况统计
	 * @param    
	 * @return List
	 */
	public List operatorWorkInfoQuery(IBaseDTO dto, PageInfo pi);
	/**
	 * @describe 坐席员工作情况详细统计(操作具体时间明细)
	 * @param
	 * @return List
	 */
	public List operatorWorkInfoDetailQuery(IBaseDTO dto);
	/**
	 * @describe 坐席员出席情况统计查询
	 * @param
	 * @return List
	 */
	public List operatorChuXiQuery(IBaseDTO dto);
    /**
	 * @describe 坐席员接听情况统计查询
	 * @param
	 * @return List
	 */
	public List operatorJieTingQuery(IBaseDTO dto);
	/**
	 * @describe 坐席员离开情况统计查询
	 * @param
	 * @return List
	 */
	public List operatorOutQuery(IBaseDTO dto);
	/**
	 * @describe 得到记录条数
	 * @param
	 * @return int 
	 */
	public int getOperatorWorkInfoSize();
	/**
	 * @describe 座席员列表
	 * @param
	 * @return <labelValueBean>
	 */
	public List<LabelValueBean> getWorkInfoPersonList();
	/**
	 * @describe 坐席员出席时间
	 * @param
	 * @return List
	 */
	public List chuXiTimeQuery(IBaseDTO dto);
	/**
	 * @describe 坐席员离开时间查询
	 * @param
	 * @return List
	 */
	public List outTimeQuery(IBaseDTO dto);
	/**
	 * @describe 坐席员接听时间查询
	 * @param
	 * @return List
	 */
	public List jieTingTimeQuery(IBaseDTO dto);
}
