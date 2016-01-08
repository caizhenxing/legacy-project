/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.export.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import et.bo.callback.service.CallbackService;
import et.bo.callback.service.impl.CallbackHelp;
import et.bo.export.service.ExportService;
import et.po.OperCallback;
import et.po.OperExportInfo;
import et.po.OperQuestion;
import excellence.common.key.KeyService;
import excellence.common.page.PageInfo;
import excellence.common.util.time.TimeUtil;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;

/**
 * <p>专家管理</p>
 * 
 * @version 2008-04-01
 * @author wang lichun
 */

public class ExportServiceImpl implements ExportService {
	
	private BaseDAO dao = null;
	private int num = 0;
	
	/**
	 * 添加专家
	 */
	public void addExport(IBaseDTO dto) {
		OperExportInfo oei=new OperExportInfo();
		oei.setName(dto.get("name").toString());
		oei.setRemark(dto.get("remark").toString());
		String path=dto.get("photo").toString();
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(path));
			Blob blob=Hibernate.createBlob(fis);
			oei.setPhoto(blob);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dao.saveEntity(oei);
	}
	public void delExport(String id) {
		int id1=Integer.parseInt(id);
		dao.removeEntity(OperExportInfo.class, id1);
		
	}
	
	private DynaBeanDTO exportToDynaBeanDTO(OperExportInfo po){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId());
		dto.set("name", po.getName());
		dto.set("remark", po.getRemark());
		dto.set("photo", po.getPhoto());
		return dto;
	}

	public List exportQuery(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		ExportHelp h = new ExportHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.exportQuery(dto, pi));
		num = dao.findEntitySize(h.exportQuery(dto, pi));
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperExportInfo po = (OperExportInfo) result[i];
			DynaBeanDTO d=exportToDynaBeanDTO(po);
			String remark=po.getRemark();
			if(remark!=null&&remark.length()>32){
				remark=remark.substring(0,32)+"...";				
				d.set("remark", remark);
			}
			list.add(d);
		}
		return list;
	}
	public List exportQuery2() {
		List list = new ArrayList();
		ExportHelp h = new ExportHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.exportQuery2());
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperExportInfo po = (OperExportInfo) result[i];
			DynaBeanDTO dto = new DynaBeanDTO();
			
			dto.set("id", po.getId());
			dto.set("name", po.getName());
			dto.set("remark", po.getRemark());
			
			list.add(dto);
		}
		return list;
	}
	
	public IBaseDTO getExportInfo(String id) {
		int id1=Integer.parseInt(id);
		OperExportInfo po = (OperExportInfo)dao.loadEntity(OperExportInfo.class,id1);
		IBaseDTO dto = new DynaBeanDTO();
		
		dto.set("id", po.getId().toString());
		dto.set("name", po.getName());
		dto.set("photo", po.getPhoto());
		dto.set("remark", po.getRemark());
		
		return dto;
	}
	public int getExportSize() {
		
		return num;
	}
	
	public boolean updateExport(IBaseDTO dto) {
		int id1=Integer.parseInt(dto.get("id").toString());
		OperExportInfo po = (OperExportInfo)dao.loadEntity(OperExportInfo.class,id1);//根据主键修改表
		try {
			po.setName(dto.get("name").toString());		
			po.setRemark(dto.get("remark").toString());	
			String path=dto.get("photo").toString();
			FileInputStream fis;
			fis = new FileInputStream(new File(path));
			Blob blob=Hibernate.createBlob(fis);
			po.setRemark(dto.get("remark").toString());			
			po.setPhoto(blob);
			dao.saveEntity(po);
			return true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
}
