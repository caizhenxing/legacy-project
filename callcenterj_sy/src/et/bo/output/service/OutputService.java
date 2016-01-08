/**
 * 沈阳卓越科技有限公司
 * 2008-5-27
 */
package et.bo.output.service;

import java.util.List;

import et.po.OperBookMedicinfo;
import et.po.OperCaseinfo;
import et.po.OperCorpinfo;
import et.po.OperFocusinfo;
import et.po.OperInquiryCard;
import et.po.OperInquiryinfo;
import et.po.OperMarkanainfo;
import et.po.OperMedicinfo;
import et.po.OperPriceinfo;
import et.po.OperSadinfo;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * 导出案例信息为word文档、txt文档、Excel文档
 * 
 * @author nie
 * 
 */
public interface OutputService {
	/**
	 * 删除“消息管理”中无用消息记录列表
	 * 
	 * @param ids 多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public void delMessagesList(String ids);
	/**
	 * 根据ID集取出相关供求记录列表
	 * 
	 * @param ids 多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperSadinfo> getSadList(String ids);
	
	/**
	 * 得到焦点追踪库的记录列表
	 * @param ids
	 * @return
	 */
	public List<OperFocusinfo> getFocusList(String ids);
	
	/**
	 * 得到普通医疗库记录信息
	 * @param ids
	 * @return
	 */
	public List<OperMarkanainfo> getMarkList(String ids);
	
	/**
	 * 得到普通医疗库记录信息
	 * @param ids
	 * @return
	 */
	public List<OperCorpinfo> getCrop(String ids);
	
	/**
	 * 得到普通医疗库记录信息
	 * @param ids
	 * @return
	 */
	public List<OperMedicinfo> getMedical(String ids);
	
	/**
	 * 得到普通医疗库记录信息
	 * @param ids
	 * @return
	 */
	public List<OperBookMedicinfo> getbookMedical(String ids);
	/**
	 * 根据ID集取出相关供求记录列表，转成dto用于输出
	 * 
	 * @param ids 多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<DynaBeanDTO> getSadList2(String ids);
	/**
	 * 根据ID集取出相关案例记录列表
	 * 
	 * @param ids 多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperCaseinfo> getCaseList(String ids);
	/**
	 * 根据ID集取出价格库中的相关记录列表
	 * 
	 * @param ids 多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperPriceinfo> getPriceList(String ids);
	/**
	 * @author wwq
	 * 根据ID得到市场分析库列表
	 * @param ids 多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperMarkanainfo> getMarkanaList(String ids);
	/**
	 * @author wwq
	 * 根据ID得到焦点追踪库列表
	 * @param ids 多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperFocusinfo> getTraceList(String ids);
	/**
	 * 导出案例信息到word文档
	 * 
	 * @param pos 案例信息数组,每个案例信息有一个OperCaseinfo代表
	 * @param fileName 文件路径和文件名
	 */
	public void outputWordFile(List<OperCaseinfo> pos, String fileName, String dbType);

	/**
	 * 导出案例信息到txt文档
	 * 
	 * @param pos 案例信息数组,每个案例信息由一个OperCaseinfo对象代表
	 * @param fileName 生成的文件路径和文件名
	 */
	public void outputTxtFile(List<OperCaseinfo> pos, String fileName, String dbType);

	/**
	 * 导出案例信息到Excel文档
	 * 
	 * @param pos 案例信息数组,每个案例信息由一个OperCaseinfo对象代表
	 * @param fileName 生成的文件路径和文件名
	 */
	public void outputExcelFile(List pos, String fileName, String dbType);
	
	/**
	 * zhang feng add(思路错误删除)
	 * 根据模板导出excel
	 * @param pos 列表信息
	 * @param fileName 文件名
	 * @param dbType 类型
	 */
//	public void outputExcel(List pos, String fileName, String dbType);
	
	/**
	 * 根据ID集取出调查信息问题库中的相关记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List getInquiryResultList(String ids);
	
	/**
	 * 根据ID集取出调查信息分析库中的相关记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List getInquiryResult2List(String ids);
	
	/**
	 * 根据ID集取出调查信息问题库中的相关记录列表
	 * 
	 * @param ids
	 *            多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List<OperInquiryinfo> getInquiryCardList(String ids);
	
	/**
	 * 根据ID取出OperInquiryCard的实体
	 * 
	 * @param id OperInquiryInfo的id
	 * @return oic 包含实体
	 */
	public List getCard(String id);
	
	/**
	 * 根据ID集取出调查信息分析库中的调查类别记录列表
	 * 
	 * @param ids 多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List getDictInquiryType(String ids);
	
	/**
	 * 根据ID集取出调查信息分析库中的相关记录列表
	 * 
	 * @param ids 多个ID中间用逗号隔开的字符串
	 * @return list 包含数据和dto列表
	 */
	public List getResultList(String ids);
	


}
