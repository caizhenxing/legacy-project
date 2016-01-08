/*
 * @(#)CallbackServiceImpl.java	 2008-04-01
 *
 * 版权所有 沈阳市卓越科技有限公司。
 */

package et.bo.yznztj.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import et.bo.yznztj.service.YznztjService;
import et.po.OperYznztj;
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

public class YznztjServiceImpl implements YznztjService {
	
	private BaseDAO dao = null;
	private int num = 0;
	
	/**
	 * 添加专家
	 */
	public void addYznztj(IBaseDTO dto) {
		OperYznztj oy=new OperYznztj();
		oy.setAddMan(dto.get("addMan").toString());
		oy.setAddTime(TimeUtil.getNowTime());
		oy.setMethod(dto.get("method").toString());
		oy.setProductName(dto.get("productName").toString());
		oy.setScope(dto.get("scope").toString());
		oy.setSort(dto.get("sort").toString());
		oy.setTrait(dto.get("trait").toString());
		String path=dto.get("photo").toString();
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(path));
			Blob blob=Hibernate.createBlob(fis);
			oy.setPhoto(blob);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dao.saveEntity(oy);
	}
	public void delYznztj(String id) {
		int id1=Integer.parseInt(id);
		dao.removeEntity(OperYznztj.class, id1);
		
	}
	
	private DynaBeanDTO yznztjToDynaBeanDTO(OperYznztj po){
		
		DynaBeanDTO dto = new DynaBeanDTO();
		dto.set("id", po.getId());
		dto.set("photo", po.getPhoto());
		dto.set("sort", po.getSort());
		dto.set("productName", po.getProductName());
		String trait = po.getTrait();
		if(trait.length()>=10){
			trait = trait.substring(0,10)+"...";
		}
		dto.set("trait", trait);
		String scope = po.getScope();
		if (scope.length()>=5) {
			scope = scope.substring(0,5)+"...";
		}
		dto.set("scope", scope);
		dto.set("method", po.getMethod());
		dto.set("addTime", po.getAddTime());
		dto.set("addMan", po.getAddMan());
		return dto;
	}

	public List yznztjQuery(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		YznztjHelp h = new YznztjHelp();
		
		Object[] result = (Object[]) dao.findEntity(h.yznztjQuery(dto, pi));
		num = dao.findEntitySize(h.yznztjQuery(dto, pi));
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperYznztj po = (OperYznztj) result[i];
			DynaBeanDTO d=yznztjToDynaBeanDTO(po);
			String trait=po.getTrait();
			if(trait!=null&&trait.length()>10){
				trait=trait.substring(0,10)+"...";				
				d.set("trait", trait);
			}
			list.add(d);
		}
		return list;
	}
	public List yznztjQuery2(){
		List list = new ArrayList();
		YznztjHelp h = new YznztjHelp();
		Object[] result = (Object[]) dao.findEntity(h.yznztjQuery2());

		for (int i = 0, size = result.length; i < size; i++) {
			OperYznztj po = (OperYznztj) result[i];
			DynaBeanDTO dto = new DynaBeanDTO();

			dto.set("id", po.getId());
			dto.set("photo", po.getPhoto());
			dto.set("sort", po.getSort());
			dto.set("productName", po.getProductName());
			dto.set("trait", po.getTrait());
			dto.set("scope", po.getScope());
			dto.set("method", po.getMethod());
			
			list.add(dto);
		}
		return list;
	}
	
	public IBaseDTO getYznztjInfo(String id) {
		int id1=Integer.parseInt(id);
		OperYznztj po = (OperYznztj)dao.loadEntity(OperYznztj.class,id1);
		IBaseDTO dto = new DynaBeanDTO();
		dto.set("id", po.getId());
		dto.set("photo", po.getPhoto());
		dto.set("sort", po.getSort());
		dto.set("productName", po.getProductName());
		dto.set("trait", po.getTrait());
		dto.set("scope", po.getScope());
		dto.set("method", po.getMethod());
		dto.set("addTime", po.getAddTime());
		dto.set("addMan", po.getAddMan());
		
		return dto;
	}
	public int getYznztjSize() {
		
		return num;
	}
	
	public boolean updateYznztj(IBaseDTO dto) {
		int id1=Integer.parseInt(dto.get("id").toString());
		OperYznztj po = (OperYznztj)dao.loadEntity(OperYznztj.class,id1);//根据主键修改表
		try {
			po.setAddMan(dto.get("addMan").toString());
			po.setMethod(dto.get("method").toString());
			po.setProductName(dto.get("productName").toString());
			po.setScope(dto.get("scope").toString());
			po.setSort(dto.get("sort").toString());
			po.setTrait(dto.get("trait").toString());
			String path=dto.get("photo").toString();
			FileInputStream fis;
			fis = new FileInputStream(new File(path));
			Blob blob=Hibernate.createBlob(fis);		
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
