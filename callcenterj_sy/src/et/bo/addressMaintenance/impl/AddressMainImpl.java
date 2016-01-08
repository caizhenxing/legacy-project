/*
 * @(#)AddressMainService.java	 2008-04-01
 *
 * ��Ȩ���� ������׿Խ�Ƽ����޹�˾��
 */
package et.bo.addressMaintenance.impl;

import java.sql.SQLException;

import javax.sql.RowSet;

import et.bo.addressMaintenance.service.AddressMainService;
import excellence.framework.base.dao.BaseDAO;
/**
 * <p>ʡ���ص�ַ��Ϣά��</p>
 * 
 * @version 2009-03-04
 * @author wangwenquan
 */
public class AddressMainImpl implements AddressMainService{
	private BaseDAO dao = null;
	/**
	 * �ַ�����ʽ�ĳ��м��� ����#����
	 * @return "����#����"
	 */
	public String getAllCitys()
	{
		String sql = "select distinct city from oper_address order by city";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		StringBuffer sb = new StringBuffer();
		int count=0;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				if(count>0)
				{
					sb.append("#"+rs.getString("city"));
				}
				else
				{
					sb.append(rs.getString("city"));
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 * ������������ ��ƽ�� �˳��� ������
	 * @param cityName
	 * @return "��ƽ��#�˳���#������"
	 */
	public String getAllSectionByCityName(String cityName)
	{
		String sql = "select distinct section_county from oper_address where city = '"+cityName+"' order by section_county ";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		StringBuffer sb = new StringBuffer();
		int count=0;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				if(count>0)
				{
					sb.append("#"+rs.getString("section_county"));
				}
				else
				{
					sb.append(rs.getString("section_county"));
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 * �������������� �徭�� �¼��� ����ׯ��
	 * @param sectionName
	 * @return "�徭��#�¼���#����ׯ��"
	 */
	public String getAllVillageATownsBySection(String sectionName)
	{
		String sql = "select distinct villages_and_towns from oper_address where section_county='"+sectionName+"' order by villages_and_towns";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		StringBuffer sb = new StringBuffer();
		int count=0;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				if(count>0)
				{
					sb.append("#"+rs.getString("villages_and_towns"));
				}
				else
				{
					sb.append(rs.getString("villages_and_towns"));
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 * �����µ�����/�� �徭�� �¼��� ����ׯ��
	 * @param villageName
	 * @return "�徭��#�¼���#����ׯ��"
	 */
	public String getAllCommunityByVillage(String villageName)
	{
		String sql = "select distinct community_village from oper_address where villages_and_towns = '"+villageName+"' order by community_village";
		RowSet rs = dao.getRowSetByJDBCsql(sql);
		StringBuffer sb = new StringBuffer();
		int count=0;
		try {
			rs.beforeFirst();
			while (rs.next()) {
				if(count>0)
				{
					sb.append("#"+rs.getString("community_village"));
				}
				else
				{
					sb.append(rs.getString("community_village"));
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
    /**
     * ���ӳ���
     * @param cityName
     */
	public void addCity(String cityName,String num)
	{
		String sql = "insert into oper_address(city,city_num)values('"+cityName+"','"+num+"')";
		dao.execute(sql);
	}
	 /**
     * ɾ������
     * @param cityName
     */
	public void deleteCity(String cityName)
	{
		String sql = "delete from oper_address where city = '"+cityName+"'";
		dao.execute(sql);
		//System.out.println(sql);
	}
    /**
     * ���ӳ���
     * @param cityName
     */
	public void updateCity(String cityName,String srcName)
	{
		String sql = "update oper_address set city = '"+cityName+"' where city = '"+srcName+"'";
		dao.execute(sql);
		System.out.println(sql+":"+srcName);
	}
	public BaseDAO getDao() {
		
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}
	/**
	 * ��������
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void addArea(String cityName,String cityNum,String areaName, String areaNum)
	{
		String querySql="select * from oper_address where city='"+cityName+"'  and section_county is null and villages_and_towns is null and community_village is null";
		String operSql = "";
		RowSet rs = dao.getRowSetByJDBCsql(querySql);
		try {
			rs.beforeFirst();
			if(rs.next())
			{
				String id = rs.getString("id");
				operSql = "update oper_address set section_county = '"+areaName+"' , section_county_num = '"+areaNum+"' where id = "+id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if("".equals(operSql))
		{
			//insert ����
			operSql = "insert into oper_address(city,city_num,section_county,section_county_num)values('"+cityName+"','"+cityNum+"','"+areaName+"','"+areaNum+"')";
		}
		
		dao.execute(operSql);
	}
	/**
	 * ɾ������
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void deleteArea(String cityName,String cityNum,String areaName, String areaNum)
	{
		String sql = "delete from oper_address where city='"+cityName+"' and section_county = '"+areaName+"'";
		dao.execute(sql);
		//System.out.println(sql);
	}
	/**
	 * �޸�����
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 */
	public void updateArea(String cityName,String areaName, String srcName)
	{
		String sql = "update oper_address set section_county='"+areaName+"' where city='"+cityName+"' and section_county = '"+srcName+"'";
		dao.execute(sql);
	}
	/**
	 * ��������
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void addTowns(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum)
	{
		String querySql="select * from oper_address where city='"+cityName+"'  and section_county = '"+areaName+"' and villages_and_towns is null and community_village is null";
		String operSql = "";
		RowSet rs = dao.getRowSetByJDBCsql(querySql);
		try {
			rs.beforeFirst();
			if(rs.next())
			{
				String id = rs.getString("id");
				operSql = "update oper_address set villages_and_towns = '"+townName+"', villages_and_towns_num = '"+townNum+"' where id = "+id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if("".equals(operSql))
		{
			//insert ����
			operSql = "insert into oper_address(city,city_num,section_county,section_county_num,villages_and_towns,villages_and_towns_num)values('"+cityName+"','"+cityNum+"','"+areaName+"','"+areaNum+"','"+townName+"','"+townNum+"')";
		}
		//System.out.println(operSql);
		dao.execute(operSql);
	}
	/**
	 * ɾ������
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void deleteTowns(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum)
	{
		String sql = "delete from oper_address where city='"+cityName+"' and section_county = '"+areaName+"' and villages_and_towns = '"+townName+"'";
		dao.execute(sql);
		//System.out.println(sql);
	}
	/**
	 * �޸�����
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 */
	public void updateTowns(String cityName,String areaName,String townName, String srcName)
	{
		String sql = "update oper_address set villages_and_towns = '"+townName+"' where city='"+cityName+"' and section_county = '"+areaName+"' and villages_and_towns = '"+srcName+"'";
		dao.execute(sql);
	}
	/**
	 * ���Ӵ�ׯ
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 * @param countyName
	 * @param countyNum
	 */
	public void addCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum)
	{
		String querySql="select * from oper_address where city='"+cityName+"'  and section_county = '"+areaName+"' and villages_and_towns ='"+townName+"' and community_village is null";
		String operSql = "";
		RowSet rs = dao.getRowSetByJDBCsql(querySql);
		try {
			rs.beforeFirst();
			if(rs.next())
			{
				String id = rs.getString("id");
				operSql = "update oper_address set community_village = '"+countyName+"', community_village_num = '"+countyNum+"' where id = "+id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if("".equals(operSql))
		{
			//insert ����
			operSql = "insert into oper_address(city,city_num,section_county,section_county_num,villages_and_towns,villages_and_towns_num,community_village,community_village_num)values('"+cityName+"','"+cityNum+"','"+areaName+"','"+areaNum+"','"+townName+"','"+townNum+"','"+countyName+"','"+countyNum+"')";
		}
		//System.out.println(operSql);
		dao.execute(operSql);
	}
	
	/**
	 * ɾ����ׯ
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 * @param countyName
	 * @param countyNum
	 */
	public void deleteCommunityAvillages(String cityName,String cityNum,String areaName, String areaNum,String townName, String townNum,String countyName,String countyNum)
	{
		String sql = "delete from oper_address where city='"+cityName+"' and section_county = '"+areaName+"' and villages_and_towns = '"+townName+"' and community_village = '"+countyName+"'";
		dao.execute(sql);
		//System.out.println(sql);
	}
	/**
	 * �޸Ĵ�ׯ
	 * @param cityName
	 * @param cityNum
	 * @param areaName
	 * @param areaNum
	 * @param townName
	 * @param townNum
	 * @param countyName
	 * @param countyNum
	 */
	public void updateCommunityAvillages(String cityName,String areaName,String townName,String countyName,String srcName)
	{
		String sql = "update oper_address set community_village = '"+countyName+"' where city='"+cityName+"' and section_county = '"+areaName+"' and villages_and_towns = '"+townName+"' and community_village = '"+srcName+"'";
		dao.execute(sql);
	}
}
