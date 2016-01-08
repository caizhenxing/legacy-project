package et.bo.caseinfo.casetype.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import javax.sql.RowSet;
import et.bo.caseinfo.casetype.service.CaseTypeService;

import et.po.OperType;

import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.query.MyQuery;
import excellence.framework.base.query.impl.MyQueryImpl;
import excellence.common.tools.LabelValueBean;

public class CaseTypeServiceImpl implements CaseTypeService {

	private BaseDAO dao = null;
	
	public BaseDAO getDao() {
		return dao;
	}

	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * 加载大类
	 * @return
	 */
	public List loadBigType() {
		List list=new ArrayList();
//		String sql="select type1 from oper_type group by type1 order by type1";
//		RowSet rs= dao.getRowSetByJDBCsql(sql);
//		try{
//			if(rs!=null){
//				while(rs.next()){
//					LabelValueBean lvb = new LabelValueBean();
//					lvb.setLabel(rs.getString("type1"));
//					lvb.setValue(rs.getString("type1"));
//					list.add(lvb);
//				}
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}		
		MyQuery mq=new MyQueryImpl();		
		mq.setHql("select type1 from OperType group by type1 ");
		Object[] result=dao.findEntity(mq);		
		for(int i=0;i<result.length;i++){
			LabelValueBean lvb = new LabelValueBean();
			lvb.setLabel((String)result[i]);
			lvb.setValue((String)result[i]);
			list.add(lvb);			
		}		
		return list;
	}
	
	/**
	 * 根据大类获取小类 案例类型模块
	 * @param bigType
	 * @return
	 */
	public Map getSmallTypeByBigType(String bigType){
		// TODO Auto-generated method stub
		MyQuery mq=new MyQueryImpl();
		Map map=new HashMap();		
		mq.setHql("From OperType where type1='"+bigType+"'");
		Object[] result=dao.findEntity(mq);		
		for(int i=0;i<result.length;i++){			
			OperType ot=(OperType)result[i];
			String type2 = ot.getType2();
			if(type2!=null){
				map.put(ot.getId(), type2);
			}			
		}		
		return map;
	}
	
	/**
	 * 根据大类获取小类 页面应用
	 * @param bigType
	 * @return
	 */
	public Map getSmallTypeByBigType_app(String bigType){
		// TODO Auto-generated method stub
		MyQuery mq=new MyQueryImpl();
		Map map=new HashMap();		
		mq.setHql("From OperType where type1='"+bigType+"'");
		Object[] result=dao.findEntity(mq);		
		for(int i=0;i<result.length;i++){			
			OperType ot=(OperType)result[i];
			String type2 = ot.getType2();
			if(type2!=null){
				map.put(type2, type2);
			}			
		}		
		return map;
	}
	
	/**
	 * 根据大类加载小类 页面加载
	 * @param bigType
	 * @return
	 */
	public List loadSmallTypeByBigType(String bigType){
		// TODO Auto-generated method stub
		MyQuery mq=new MyQueryImpl();
		List list=new ArrayList();		
		mq.setHql("From OperType where type1='"+bigType+"'");
		Object[] result=dao.findEntity(mq);		
		for(int i=0;i<result.length;i++){			
			OperType ot=(OperType)result[i];
			String type2 = ot.getType2();
			if(type2!=null){
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(type2);
				lvb.setValue(type2);
				list.add(lvb);
			}			
		}		
		return list;
	}
	
	/**
	 * 添加大类
	 * @param bigType
	 * @return
	 */
	public Map addBigType(String bigType){
		// TODO Auto-generated method stub	
		if(isExistBigType(bigType)){
			return null;
		}else{
			OperType ot =new OperType();
			ot.setType1(bigType);		
			dao.saveEntity(ot);
			return getBigType();
		}		
	}
	
	/**
	 * 获取大类
	 * @return
	 */
	public Map getBigType() {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		MyQuery mq=new MyQueryImpl();		
		mq.setHql("select type1 from OperType group by type1 ");
		Object[] result=dao.findEntity(mq);		
		for(int i=0;i<result.length;i++){
			map.put((String)result[i],(String)result[i]);
		}		
		return map;
	}
	
	/**
	 * 验证大类是否存在
	 * @param bigType
	 * @return
	 */
	private boolean isExistBigType(String bigType) {
		// TODO Auto-generated method stub		
		MyQuery mq=new MyQueryImpl();		
		mq.setHql("from OperType where type1 ='"+bigType+"'");
		Object[] result=dao.findEntity(mq);		
		if(result.length>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证小类是否存在
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	private boolean isExistSmallType(String bigType,String smallType){
		// TODO Auto-generated method stub		
		MyQuery mq=new MyQueryImpl();		
		mq.setHql("from OperType where type1 ='"+bigType+"' and type2 ='"+smallType+"'");
		Object[] result=dao.findEntity(mq);		
		if(result.length>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 修改大类
	 * @param oldBigType
	 * @param newBigType
	 * @return
	 */
	public Map updateBigType(String oldBigType,String newBigType) {
		if(isExistBigType(newBigType)){
			return null;
		}else{
			MyQuery mq=new MyQueryImpl();
			mq.setHql("From OperType where type1='"+oldBigType+"'");		
			Object[] result= dao.findEntity(mq);
			for(int i=0;i<result.length;i++){
				OperType ot =(OperType)result[i];
				ot.setType1(newBigType);
				dao.updateEntity(ot);				
			}
			return getBigType();
		}		
	}
	
	/**
	 * 删除大类
	 * @param bigType
	 * @return
	 */
	public int deleteBigType(String bigType) {	
		try{
			if(isExistBigType(bigType)){
				MyQuery mq=new MyQueryImpl();
				mq.setHql("From OperType where type1='"+bigType+"'");		
				Object[] result= dao.findEntity(mq);
				for(int i=0;i<result.length;i++){
					OperType ot =(OperType)result[i];				
					dao.removeEntity(ot);
				}
				return 0;
			}else{
				return 2;
			}
		}catch(Exception e){
			return 1;	
		}		
	}
	
	/**
	 * 添加小类
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public Map addSmallType(String bigType,String smallType){
		// TODO Auto-generated method stub
		if(isExistSmallType(bigType,smallType)){
			return null;		
		}else{
			OperType ot =new OperType();
			ot.setType1(bigType);	
			ot.setType2(smallType);	
			dao.saveEntity(ot);			
			return getSmallTypeByBigType(bigType);
		}
	}
	
	/**
	 * 修改小类
	 * @param id
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public Map updateSmallType(String id,String bigType,String smallType) {
		if(isExistSmallType(bigType,smallType)){
			return null;
		}else{
			OperType ot = (OperType) dao.loadEntity(OperType.class, Integer.parseInt(id));
			ot.setType2(smallType);				
			dao.updateEntity(ot);
			return getSmallTypeByBigType(bigType);
		}		
	}
	
	/**
	 * 删除小类
	 * @param id
	 * @return
	 */
	public int deleteSmallType(String id) {
		OperType ot = (OperType) dao.loadEntity(OperType.class, Integer.parseInt(id));
		if(ot!=null){
			dao.removeEntity(ot);
			return 0;
		}
		return 1;
	}
	
}
