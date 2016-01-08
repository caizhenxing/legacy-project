package et.bo.productTypeDwr.service.impl;

import java.sql.SQLException;

import javax.sql.RowSet;

import et.bo.addressMaintenance.service.AddressMainService;
import et.bo.productTypeDwr.service.ProductTypeService;
import excellence.framework.base.dao.BaseDAO;
/**
 * <p>产品类别信息维护</p>
 *   
 * @version 2009-03-11
 * @author lidan
 */
public class ProductTypeServiceImpl implements ProductTypeService{
	private BaseDAO dao = null;
	
	public BaseDAO getDao() {
		
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	
	//******************* 产品大类 小类 名称的查询 *******************************************
	/* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#getAllName1()
	 */
	public String getAllName1()
	{
		String sql = "select distinct name1 from oper_product order by name1";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		StringBuffer sb = new StringBuffer();
		int count=0;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				if(count>0)
				{
					sb.append("#"+rs.getString("name1"));
				}
				else
				{
					sb.append(rs.getString("name1"));
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#getAllName2(java.lang.String)
	 */
	public String getAllName2(String name1)
	{
		String sql = "select distinct name2 from oper_product  where name1 = '"+name1+"' order by name2";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		StringBuffer sb = new StringBuffer();
		int count=0;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				if(count>0)
				{
					sb.append("#"+rs.getString("name2"));
				}
				else
				{
					sb.append(rs.getString("name2"));
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#getAllName3(java.lang.String)
	 */
	public String getAllName3(String name2)
	{
		String sql = "select distinct name3 from oper_product  where name2 = '"+name2+"' order by name3";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		StringBuffer sb = new StringBuffer();
		int count=0;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				if(count>0)
				{
					sb.append("#"+rs.getString("name3"));
				}
				else
				{
					sb.append(rs.getString("name3"));
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
//	/**
//	 * 乡镇下的社区/村 五经街 孤家子 下潘庄子
//	 * @param villageName
//	 * @return "五经街#孤家子#下潘庄子"
//	 */
//	public String getAllCommunityByVillage(String villageName)
//	{
//		String sql = "select distinct community_village from oper_address where villages_and_towns = '"+villageName+"' order by community_village";
//		RowSet rs = dao.getRowSetByJDBCsql(sql);
//		StringBuffer sb = new StringBuffer();
//		int count=0;
//		try {
//			rs.beforeFirst();
//			while (rs.next()) {
//				if(count>0)
//				{
//					sb.append("#"+rs.getString("community_village"));
//				}
//				else
//				{
//					sb.append(rs.getString("community_village"));
//					count++;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return sb.toString();
//	}
	
	//************************产品大类 增 删 改 ********************************************
    /* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#addName1(java.lang.String)
	 */
	public void addName1(String name1)
	{
		String s = "select top(1) code1 from oper_product order by id desc";
		RowSet rss = dao.getRowSetByJDBCsql("select top(1) code1 from oper_product order by id desc");	
		System.out.println(s);
		int id = 0;
		try {
			rss.beforeFirst();
			if(rss.next())
			{
				id = rss.getInt("code1")+1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "insert into oper_product(name1,code1) values('"+name1+"','"+id+"')";
		dao.execute(sql);
		System.out.println(sql);
	}
	 /* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#deleteName1(java.lang.String)
	 */
	public void deleteName1(String name1)
	{
		String sql = "delete from oper_product where name1 = '"+name1+"'";
		dao.execute(sql);
		//System.out.println(sql);
	}
    /* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#updateName1(java.lang.String, java.lang.String)
	 */
	public void updateName1(String newName1,String oldName1)
	{
		String sql = "update oper_product set name1 = '"+newName1+"' where name1 = '"+oldName1+"'";
		dao.execute(sql);
		System.out.println(sql+":"+oldName1);
	}

//	************************产品小类 增 删 改 ********************************************

	
	/* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#addName2(java.lang.String, java.lang.String)
	 */
	public void addName2(String name1,String name2)
	{
		String querySql="select * from oper_product where name1='"+name1+"'  and name2 is null and name3 is null ";
		String operSql = "";
		RowSet rs = dao.getRowSetByJDBCsql(querySql);
		try {
			rs.beforeFirst();
			if(rs.next())
			{
				String id = rs.getString("id");
				operSql = "update oper_product set name2 = '"+name2+"'  where id = "+id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int id = 0;
		if("".equals(operSql)){
			RowSet rss = dao.getRowSetByJDBCsql("select top(1) code1 from oper_product order by id desc");//insert 处理
			try {
				rss.beforeFirst();
				if(rss.next())
				{
					id = rss.getInt("code1");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			operSql = "insert into oper_product(name1,code1,name2) values('"+name1+"','"+id+"','"+name2+"')";
			System.out.println(operSql);
		}
		
		dao.execute(operSql);
	}
	/* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#deleteName2(java.lang.String, java.lang.String)
	 */
	public void deleteName2(String name1,String name2)
	{
		String sql = "delete from oper_product where name1='"+name1+"' and name2 = '"+name2+"'";
		dao.execute(sql);
		//System.out.println(sql);
	}
	/* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#updateName2(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateName2(String name1,String newname2, String oldname2)
	{
		String sql = "update oper_product set name2='"+newname2+"' where name1='"+name1+"' and name2 = '"+oldname2+"'";
		dao.execute(sql);
		System.out.println(sql);
	}
	
	
//	************************产品名称 增 删 改 ********************************************
	/* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#addName3(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void addName3(String name1,String name2,String name3)
	{
		String querySql="select * from oper_product where name1='"+name1+"'  and name2 = '"+name2+"' and name3 is null ";
		String operSql = "";
		RowSet rs = dao.getRowSetByJDBCsql(querySql);
		try {
			rs.beforeFirst();
			if(rs.next())
			{
				String id = rs.getString("id");
				operSql = "update oper_product set name3 = '"+name3+"' where id = "+id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if("".equals(operSql))
		{
			int code = 0;
			RowSet rss = dao.getRowSetByJDBCsql("select top(1) code1 from oper_product order by id desc");
			try {
				rss.beforeFirst();
				if(rss.next())
				{
					code = rss.getInt("code1");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//insert 处理
			operSql = "insert into oper_product(name1,code1,name2,name3)values('"+name1+"','"+code+"','"+name2+"','"+name3+"')";
			System.out.println(operSql);
		}
		//System.out.println(operSql);
		dao.execute(operSql);
	}
	/* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#deleteName3(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void deleteName3(String name1,String name2,String name3)
	{
		String sql = "delete from oper_product where name1='"+name1+"' and name2 = '"+name2+"' and name3 = '"+name3+"'";
		dao.execute(sql);
		//System.out.println(sql);
	}
	/* (non-Javadoc)
	 * @see et.bo.productTypeDwr.ProductTypeService#updateName3(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateName3(String name1,String name2,String newname3, String oldname3)
	{
		String sql = "update oper_product set name3 = '"+newname3+"' where name1='"+name1+"' and name2 = '"+name2+"' and name3 = '"+oldname3+"'";
		dao.execute(sql);
		System.out.println(sql);
	}
}
//	/**
//	 * 增加村庄
//	 * @param cityName
//	 * @param cityNum
//	 * @param areaName
//	 * @param areaNum
//	 * @param townName
//	 * @param townNum
//	 * @param countyName
//	 * @param countyNum
//	 */
//	public void addCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum)
//	{
//		String querySql="select * from oper_address where city='"+cityName+"'  and section_county = '"+areaName+"' and villages_and_towns ='"+townName+"' and community_village is null";
//		String operSql = "";
//		RowSet rs = dao.getRowSetByJDBCsql(querySql);
//		try {
//			rs.beforeFirst();
//			if(rs.next())
//			{
//				String id = rs.getString("id");
//				operSql = "update oper_address set community_village = '"+countyName+"', community_village_num = '"+countyNum+"' where id = "+id;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		if("".equals(operSql))
//		{
//			//insert 处理
//			operSql = "insert into oper_address(city,city_num,section_county,section_county_num,villages_and_towns,villages_and_towns_num,community_village,community_village_num)values('"+cityName+"','"+cityNum+"','"+areaName+"','"+areaNum+"','"+townName+"','"+townNum+"','"+countyName+"','"+countyNum+"')";
//		}
//		//System.out.println(operSql);
//		dao.execute(operSql);
//	}
//	
//	/**
//	 * 删除村庄
//	 * @param cityName
//	 * @param cityNum
//	 * @param areaName
//	 * @param areaNum
//	 * @param townName
//	 * @param townNum
//	 * @param countyName
//	 * @param countyNum
//	 */
//	public void deleteCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum)
//	{
//		String sql = "delete from oper_address where city='"+cityName+"' and section_county = '"+areaName+"' and villages_and_towns = '"+townName+"' and community_village = '"+countyName+"'";
//		dao.execute(sql);
//		//System.out.println(sql);
//	}
//	/**
//	 * 修改村庄
//	 * @param cityName
//	 * @param cityNum
//	 * @param areaName
//	 * @param areaNum
//	 * @param townName
//	 * @param townNum
//	 * @param countyName
//	 * @param countyNum
//	 */
//	public void updateCommunityAvillages(String cityName,String areaName,String townName,String countyName,String srcName)
//	{
//		String sql = "update oper_address set community_village = '"+countyName+"' where city='"+cityName+"' and section_county = '"+areaName+"' and villages_and_towns = '"+townName+"' and community_village = '"+srcName+"'";
//		dao.execute(sql);
//	}
//}
