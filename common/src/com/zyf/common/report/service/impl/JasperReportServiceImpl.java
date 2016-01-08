/**
 * 沈阳卓越科技有限公司版权所有
 * 制作时间：2007-8-27下午09:14:01
 * 文件名：JasperReportServiceImpl.java
 * 制作者：liuduoliang,zhaoyifei
 * 
 */
package com.zyf.common.report.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zyf.common.report.service.JasperReportService;

/**
 * @author liuduoliang,zhaoyifei
 *
 */
public class JasperReportServiceImpl implements JasperReportService {


	private String path;
	private HibernateTemplate hibernateTemplate;
	

	/**
	 * @return the hibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	/**
	 * @param hibernateTemplate the hibernateTemplate to set
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public ByteArrayOutputStream getOutput(String filename, Map parameter, String filetype) {
		// TODO 自动生成方法存根
		JRDataSource jrds = null;
		return this.getOutput(filename, parameter, filetype, jrds);
	}
	/* (non-Javadoc)
	 * @see com.zyf.common.report.service.JasperReportService#getOutput(java.lang.String, java.util.Map, java.lang.String)
	 */
	public ByteArrayOutputStream getOutput(String filename, Map parameter,
			String filetype,JRDataSource jrds) {
		// TODO Auto-generated method stub
		
		JasperPrint jasperPrint;
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		try{
			String jasperFilename=filename+"."+this.jasper;
			File f=new File(jasperFilename);
			if(!f.exists())
			{
				JasperCompileManager.compileReportToFile(filename+"."+this.jrxml);
			}
			if (jrds == null)
				jasperPrint = JasperFillManager.fillReport(jasperFilename, parameter,new JREmptyDataSource());  
			else
				jasperPrint = JasperFillManager.fillReport(jasperFilename, parameter,jrds);
			//generates PDF		
			if(filetype.equalsIgnoreCase("xls")){
	            JRXlsExporter  exporter = new JRXlsExporter ();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
				try {
	                exporter.exportReport();
	            } catch (JRException e) {
	                e.printStackTrace();
	            }
			}else if(filetype.equalsIgnoreCase("pdf")){
				JasperExportManager.exportReportToPdfStream(jasperPrint, os);
			}
		}catch(JRException jrException){
			jrException.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return os;
	}
	public void setPath(String servletPath) {
		// TODO Auto-generated method stub
		this.path=servletPath;
	}

	/* (non-Javadoc)
	 * @see com.zyf.common.report.service.JasperReportService#getOutput(java.lang.String, java.util.Map, java.lang.String, java.sql.Connection)
	 */
	public ByteArrayOutputStream getOutput(String filename, Map parameter, String filetype, Connection con) {
		// TODO Auto-generated method stub
		JasperPrint jasperPrint;
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		try{
			String jasperFilename=filename+"."+this.jasper;
			File f=new File(jasperFilename);
			if(!f.exists())
			{
				JasperCompileManager.compileReportToFile(filename+"."+this.jrxml);				
			}
			jasperPrint = JasperFillManager.fillReport(jasperFilename, parameter,con);
            
			//generates PDF		
			if(filetype.equalsIgnoreCase("xls")){
	            JRXlsExporter  exporter = new JRXlsExporter ();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
				try {
	                exporter.exportReport();
	            } catch (JRException e) {
	                e.printStackTrace();
	            }
			}else if(filetype.equalsIgnoreCase("pdf")){
				JasperExportManager.exportReportToPdfStream(jasperPrint, os);
			}
		}catch(JRException jrException){
			jrException.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return os;
	}
	
}
