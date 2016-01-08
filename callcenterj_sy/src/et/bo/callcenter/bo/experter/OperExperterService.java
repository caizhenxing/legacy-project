/**
 * className OperExperterService 
 * 
 * 创建日期 2008-5-12
 * 
 * @version
 * 
 * 版权所有 沈阳市卓越科技有限公司。
 */
package et.bo.callcenter.bo.experter;

import java.util.List;

import et.po.OperCustinfo;

/**
 * 操作专家信息
 * @version 	2008-05-06
 * @author 王文权
 */
public interface OperExperterService {
	/**
	 * 得到所有的专家列表
	 * @return List operCustinfoList
	 */
	List<OperCustinfo> getOperCustinfoList();
	/**
	 * 得到所有的专家列表字符串 专家名字 和电话 专家人 A类@小王:23834132,小李:13998823514;B类@小张:8002;
	 * @return String 专家名字:电话,专家名字:电话; 
	 */
	String getOperCustinfoStrs();
	/**
	 * 得到所有的专家列表字符串 专家ID 和NAME
	 * @return String ID#NAME,ID#NAME; 
	 */
	String getOperExperterIDNameStrs();
}
