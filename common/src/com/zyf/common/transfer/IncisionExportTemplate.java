/*
 * Copyright 2005-2010 the original author or authors.
 * 
 *      http://www.coheg.com.cn
 *
 * Project publicresource
 */
package com.zyf.common.transfer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.util.Assert;

import com.power.vfs.FileObject;
import com.power.vfs.FileObjectManager;
import com.power.vfs.FileObjectManagerFactory;
import com.zyf.container.ServiceProvider;
import com.zyf.framework.transfer.TransferExportRow;
import com.zyf.framework.transfer.exceptions.TransferException;
import com.zyf.framework.transfer.meta.FileType;
import com.zyf.framework.transfer.meta.TransferMetaData;
import com.zyf.framework.transfer.meta.TransferMetaDataFactory;
import com.zyf.framework.transfer.strategy.ProcessTransferRowContext;
import com.zyf.framework.transfer.strategy.ProcessTransferRowStrategy;
import com.zyf.utils.UUIDGenerator;
import com.zyf.utils.ZipUtils;

/**
 * �ָ���ļ� template, �ṩ�˸��ݲ�ͬ���Է���󵼳��ļ�, Ȼ��ѹ���� zip �ļ��Ĺ���
 * @since 2006-7-16
 * @author java2enterprise
 * @version $Id: IncisionExportTemplate.java,v 1.2 2007/12/17 11:02:39 lanxg Exp $
 */
public class IncisionExportTemplate {
	
	/** ���Ա����û������ļ�ʹ�õ� vfs ��·��, ��Ŀ¼������Ҫ����ʱ������� */
	public static final String EXPORT_FILE_VFS_ROOT = "temporary";
		
	/**
	 * �õ������ļ���Ҫ�� vfs FileObjectManager
	 * @return fileObjectManager
	 */
	public static FileObjectManager getFileObjectManager() {
		FileObjectManagerFactory factory = (FileObjectManagerFactory) ServiceProvider.getService(FileObjectManagerFactory.SERVICE_NAME);
		return factory.getFileObjectManager(EXPORT_FILE_VFS_ROOT);
	}
	
	/**
	 * ������ֱ�ӵ���Ϊexcel����ѹ�������Ҫע����Ӧ���ݲ�̫�������<10000�У�
	 */
	public String exportExcel(
		List exportRows,
		ExportRowsGrouper exportRowsGrouper,
		String fileName)
		throws TransferException {
		return exportExcel(exportRows, exportRowsGrouper, TransferMetaDataFactory.getDefaultTransferMetaData(), FileType.XLS, fileName);
	}

	public String exportExcel(
		List exportRows, 
		ExportRowsGrouper exportRowsGrouper, 
		TransferMetaData metaData, 
		FileType fileType, 
		String fileName) 
		throws TransferException {
		
		File file = getExportFile(exportRows, exportRowsGrouper,metaData,fileType,fileName);
		try{
			//�ļ�������0����Ϊֻ�ֳ�һ��
			String fileFullName = getVfsTemporaryRealPath() + "/" + file.getName()+"/"+ fileName+"0.xls";
			File newFile = new File(fileFullName);
			FileObject fileObject = new FileObject();
			fileObject.setName(fileName + "0.xls");
			fileObject = getFileObjectManager().create(fileObject, new BufferedInputStream(new FileInputStream(newFile)));
			//ɾ����ʱ�ļ�
			deleteFile(file);
			return fileObject.getFileName();
		} catch (IOException e) {
			throw new TransferException("����Excel�ļ�ʱ����ִ���", e);
		}
	}
	
	/**
	 * �������ݲ�ѹ���� zip ����ŵ� vfs ��, ��Ҫʱ����ֳɶ���ļ�, ����Ĺ����ɲ��� exportRowsGrouper ����
	 * @param exportRows ��Ҫ����������, list fill with {@link TransferExportRow}
	 * @param exportRowsGrouper ������
	 * @param distZipFileName Ŀ�� zip �ļ�������, ֻ��Ҫ���ּ���, ����Ҫ·������չ�� ".zip"
	 * @return ������� zip �ļ��� vfs �е�·��
	 * @throws TransferException ������������г����쳣
	 */
	public String exportAndDoIncisionIfNecessarily(
		List exportRows, 
		ExportRowsGrouper exportRowsGrouper, 
		String distZipFileName) 
		throws TransferException {
		return exportAndDoIncisionIfNecessarily(exportRows, exportRowsGrouper, TransferMetaDataFactory.getDefaultTransferMetaData(), FileType.XLS, "exportFile", distZipFileName);
	}
	
	/**
	 * �������ݲ�ѹ���� zip ����ŵ� vfs ��, ��Ҫʱ����ֳɶ���ļ�, ����Ĺ����ɲ��� exportRowsGrouper ����
	 * @param exportRows ��Ҫ����������, list fill with {@link TransferExportRow}
	 * @param exportRowsGrouper ������
	 * @param metaData ����Ԫ��Ϣ
	 * @param fileType �����ļ�����
	 * @param zipEntryName zip ���е��ļ�����
	 * @param distZipFileName Ŀ�� zip �ļ�������, ֻ��Ҫ���ּ���, ����Ҫ·������չ�� ".zip"
	 * @return ������� zip �ļ��� vfs �е�·��
	 * @throws TransferException ������������г����쳣
	 */
	public String exportAndDoIncisionIfNecessarily(
		List exportRows, 
		ExportRowsGrouper exportRowsGrouper, 
		TransferMetaData metaData, 
		FileType fileType, 
		String zipEntryName,  
		String distZipFileName) 
		throws TransferException {
				
		File file = getExportFile(exportRows, exportRowsGrouper,metaData,fileType,zipEntryName);
		// ���������ļ�ѹ���� zip
		try {
			//String uniqueZipDirName = getVfsTemporaryRealPath() + "/" + new UUIDGenerator().generate();
			//File uniqueZipDir = new File(uniqueZipDirName);
			//uniqueZipDir.mkdir();
			String fullDistZipFileName = distZipFileName + ".zip";//uniqueZipDirName + "/" +
			ZipUtils.zipFile(file, fullDistZipFileName);
			File zipFile = new File(fullDistZipFileName);
			
			FileObject fileObject = new FileObject();
			fileObject.setName(distZipFileName + ".zip");
			fileObject = getFileObjectManager().create(fileObject, new BufferedInputStream(new FileInputStream(zipFile)));
			
			// ɾ����ʱ�ļ�
			deleteFile(file);
			//deleteFile(uniqueZipDir);
			return fileObject.getFileName();
		} catch (IOException e) {
			throw new TransferException("�����ļ�����", e);
		}
	}
	
	//�õ��������ļ�
	private File getExportFile(
		List exportRows, 
		ExportRowsGrouper exportRowsGrouper, 
		TransferMetaData metaData, 
		FileType fileType, 
		String zipEntryName)
		throws TransferException {		
		List groupedList = exportRowsGrouper.group(exportRows);
		
		File temporaryDir = new File(getVfsTemporaryRealPath());
		if (!temporaryDir.isDirectory()) {
			temporaryDir.mkdir();
		}
		File file = new File(getVfsTemporaryRealPath() + "/" + new UUIDGenerator().generate());
		if (!file.isDirectory()) {
			file.mkdir();
		}

		for (int i = 0; i < groupedList.size(); i++) {
			Object forEach = groupedList.get(i);
			Assert.isInstanceOf(List.class, forEach, " ����ȷʵ�� " + ExportRowsGrouper.class);
			List groupList = (List) forEach;
			ProcessTransferRowStrategy strategy = ProcessTransferRowContext.getStrategy(fileType);
			
			OutputStream out = null;
			try {
				out = new FileOutputStream(file.getPath() + "/" + zipEntryName + i + fileType.getFileSuffix());			
				strategy.writeTransferRows2OutputStream(groupList, metaData, out);			
				out.flush();
				out.close();
			} catch (IOException e) {
				throw new TransferException("�����ļ�����", e);
			}
		}
		return file;
	}
	
	private void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
			file.delete();
		} else {
			file.delete();
		}
	}
	
	public static String getVfsTemporaryRealPath() {
		FileObjectManager fileObjectManager  = getFileObjectManager();
		FileObject root = fileObjectManager.find("/");
		return (String) root.getProperty(FileObjectManager.KEY_REAL_PATH);
	}
	
	public static void main(String[] args) {
		System.out.println(getVfsTemporaryRealPath());
	}
}
