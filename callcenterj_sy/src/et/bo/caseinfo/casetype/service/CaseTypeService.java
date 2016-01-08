/**
 * @(#)PortCompareService.java 1.0 //
 * 
 * 版权所有 沈阳市卓越科技有限公司。 卓越科技 保留一切权利
 * 
 */
package et.bo.caseinfo.casetype.service;

import java.util.List;
import java.util.Map;

/**
 * @describe 案例类型
 * @author 
 * @version 1.0, 200-03-04
 * @see
 */
public interface CaseTypeService{
	
	/**
	 * 加载大类
	 * @return
	 */
	public List loadBigType() ;
	
	/**
	 * 根据大类获取小类 案例类型模块
	 * @param bigType
	 * @return
	 */
	public Map getSmallTypeByBigType(String bigType);
	
	/**
	 * 根据大类获取小类  页面应用
	 * @param bigType
	 * @return
	 */
	public Map getSmallTypeByBigType_app(String bigType);
	
	/**
	 * 根据大类加载小类 页面加载list
	 * @param bigType
	 * @return
	 */
	public List loadSmallTypeByBigType(String bigType);
	
	/**
	 * 添加大类
	 * @param bigType
	 * @return
	 */
	public Map addBigType(String bigType);
	
	/**
	 * 获取大类
	 * @return
	 */
	public Map getBigType();
	
	/**
	 * 添加小类
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public Map addSmallType(String bigType,String smallType);	
	
	/**
	 * 修改大类
	 * @param oldBigType
	 * @param newBigType
	 * @return
	 */
	public Map updateBigType(String oldBigType,String newBigType);
	
	/**
	 * 删除大类
	 * @param bigType
	 * @return
	 */
	public int deleteBigType(String bigType);
	
	/**
	 * 修改小类
	 * @param id
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public Map updateSmallType(String id,String bigType,String smallType);
	
	/**
	 * 删除小类
	 * @param id
	 * @return
	 */
	public int deleteSmallType(String id);
	
}
