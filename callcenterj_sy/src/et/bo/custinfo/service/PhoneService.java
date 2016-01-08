/*
 * @(#)CustinfoService.java	 2008-03-19
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */


package et.bo.custinfo.service;

import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**
 * <p>客户管理</p>
 * 
 * @version 2008-03-19
 * @author nie
 */

public interface PhoneService {
	
	/**
	 * 查询数据列表,返回记录的list。
	 * 取得查询列表数据。
	 * @param dto 数据传输对象
	 * @param pi 页面信息
	 * @return 数据的list
	 */
	public List phoneQuery(IBaseDTO dto, PageInfo pi);


}
