/*
 * @(#)QuestionAction.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.screen.service;

import java.util.List;

import et.po.OperCustinfo;
import excellence.framework.base.container.SpringRunningContainer;
import excellence.framework.base.dto.impl.DynaBeanDTO;
/**
 * <p>大屏幕提供数据</p>
 * 
 * @version 2008-03-29
 * @author wangwenquan
 */
public interface ScreenService {

	/**
	 * 每日价格查询需要 产品名称(product_name) 报价产地(cust_addr) 产品价格(product_price/priduct_unit 8.00元/斤) 价格类型(价格类型)获得ScreenPriceInfo数组
	 * @return List
	 */
	public List getScreenPriceInfo();

	/**
	 * 大屏幕获得每日供求ScreenSadInfo对象的集合 
	 * @return List
	 */
	public List getScreenSadInfo();
	/**
	 * 获得金典案例列表
	 * @return  List
	 * @author wangwenquan
	 */
	public List<DynaBeanDTO> getCaseInfoList();
	/**
	 * 12316快讯增加
	 * @param title
	 * @param content
	 * @return void
	 */
	public void addQuickMessage(String title, String content);
	/**
	 * 12316快讯列表
	 * @param title
	 * @param content
	 */
	public List<DynaBeanDTO> quickMessageList();
	/**
	 * 话务实况统计
	 * @return List
	 */
	public List<DynaBeanDTO> huaWuList();
	/**
	 * 优质农资推介
	 * @return  List
	 * @author wangwenquan
	 */
	/**
	 * 焦点关注
	 */
	public List<DynaBeanDTO> jiaoDianAnliList();
	//public List<OperCaseinfo> getCaseInfoList();
	/**
	 * 格式化价格
	 * @param String priceStr
	 * @param int decimalNum 小数点后几位
	 * @return formatStr
	 */
	public String formatPriceStr(String priceStr, int decimalNum);
	/**
	 * 得到资询汇总
	 * return DynaBeanDTO
	 */
	public DynaBeanDTO getZiXunSumDtl();
	/**
	 * 得到所有专家
	 * @return List<OperCustinfo>
	 */
	public List<OperCustinfo> getAllExperts();
	/**
	 * 得到所有专家
	 * @param type 类型
	 * @return List<OperCustinfo>
	 */
	public List<OperCustinfo> getAllExpertsByType(String type);
	/**
	 * 得到所有互斥专家
	 * @param type 类型
	 * @return List<OperCustinfo>
	 */
	public List<OperCustinfo> getAllMutexExpertsByType(String type);
	/**
	 * 更新专家类型
	 * @param type 类型
	 * @param ids ids
	 * @return List<OperCustinfo>
	 */
	public void updateScreenExpertType(String type, String ids);
	
	/**
	 * 显示最近一个月的哪个类别接听电话的次数，横轴是类别，纵轴是显示的数据内容
	 * @return
	 */
//	public List getCallLogStatisByMonth();
	
	/**
	 * 显示的是当天的哪个类别接听电话的次数，横轴是类别，纵轴是显示的数据内容
	 * @return
	 */	
//	public List getCallLogStatisByDay();
	/**
	 * 显示最近一个月的哪个类别接听电话的次数，横轴是类别，纵轴是显示的数据内容
	 * 显示的是当天的哪个类别接听电话的次数，横轴是类别，纵轴是显示的数据内容
	 */
	public void createXml();
	
}
