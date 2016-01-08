/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：2007-8-27下午09:11:35
 * 文件名：JasperReportService.java
 * 制作者：liuduoliang,zhaoyifei
 * 
 */
package com.zyf.common.report.service;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

import com.zyf.core.ServiceBase;


/**
 * @author liuduoliang,zhaoyifei
 *
 */
public interface JasperReportService extends ServiceBase{

	public final static String jasper="jasper";
	public final static String jrxml="jrxml";
	public final static String path="com/qware/common/report/jasper/";
	public final static String xls="xls";
	public final static String pdf="pdf";
	public final static String SERVICE_NAME="common.JasperReportService";
	/**
	 * 
	 * 功能描述：报表不通过数据库获取数据
	 * @param filename
	 * @param parameter
	 * @param filetype
	 * @return
	 * 2007-8-27下午09:13:03
	 */
	public ByteArrayOutputStream getOutput(String filename,Map parameter,String filetype);
	/**
	 * 
	 * 功能描述：报表不通过数据库但通过List(JavaBean集合)获取数据
	 * @param filename
	 * @param parameter
	 * @param filetype
	 * @return
	 * 2007-8-27下午09:13:03
	 */
	public ByteArrayOutputStream getOutput(String filename,Map parameter,String filetype,JRDataSource jcds);
	 
	/**
	 * 
	 * 功能描述：报表需要通过数据库获取数据
	 * @param filename
	 * @param parameter
	 * @param filetype
	 * @return
	 * 2007-10-18下午3:43:20
	 */
	public ByteArrayOutputStream getOutput(String filename,Map parameter,String filetype,Connection con);
	
	public void setPath(String servletPath);
}
