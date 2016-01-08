   /**
 * @(#)LogService.java	 06/04/17
 *
 * Copyright zhaoyifei. All rights reserved.
 *  Use is subject to license terms.
 */
package et.bo.sys.log.service;


import java.util.List;

import excellence.common.page.PageInfo;
import excellence.framework.base.dto.IBaseDTO;

/**<code>LogService</code> is interface 
 * which contains a series of action about log
 * 
 * @author  yifei zhao
 * 
 * @version 06/04/17
 * @since   1.0
 *
 */
public interface LogService {

	/**
	 * @param logs 一系列log，类型为IBaseDTO.
	 * 
	 */
	public abstract void addLog(List logs);
	/**
	 * 
	 * @param dto is restrict about select
	 * @param pi <code>PageInfo</code> is about page's info
	 * @return <code>List<IBaseDTO></code> contains  Log's info
	 */
	public abstract List<IBaseDTO> listLog(IBaseDTO dto,PageInfo pi);
	
	/**
	 * 
	 * @param dto is restrict about select
	 * 
	 * @return int is list of log size
	 */
	public abstract int listLogSize(IBaseDTO dto);
	/**
	 * @param id a <code>String</code> is log's  id .
	 * 
	 * @since 1.0
	 */
	public abstract void deleteLog(String id);
	
}
