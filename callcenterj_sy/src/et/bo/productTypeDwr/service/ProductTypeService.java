package et.bo.productTypeDwr.service;

public interface ProductTypeService {

	//******************* 产品大类 小类 名称的查询 *******************************************
	/**
	 * 字符串形式的产品大类集合 蔬菜#水果
	 * @return "蔬菜#水果"
	 */
	public String getAllName1();

	/**
	 * 大类下的小类 如 水果大类-->坚果小类 
	 * @param cityName
	 * @return "坚果#浆果#核果"
	 */
	public String getAllName2(String name1);

	/**
	 * 小类下的产品名称  如 坚果小类 --> 核桃
	 * @param sectionName
	 * @return "核桃#小核桃#大核桃"
	 */
	public String getAllName3(String name2);

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
	/**
	 * 增加产品大类
	 * @param name1
	 */
	public void addName1(String name1);

	/**
	 * 删除产品大类
	 * @param cityName
	 */
	public void deleteName1(String name1);

	/**
	 * 修改产品大类
	 * @param name1
	 * 
	 */
	public void updateName1(String newName1, String oldName1);

	/**
	 * 增加产品小类
	 */
	public void addName2(String name1, String name2);

	/**
	 * 删除产品小类
	 */
	public void deleteName2(String name1, String name2);

	/**
	 * 
	 * 修改产品小类
	 */
	public void updateName2(String name1, String newname2, String oldname2);

	//	************************产品名称 增 删 改 ********************************************
	/**
	 * 增加产品名称
	 */
	public void addName3(String name1, String name2, String name3);

	/**
	 * 删除产品名称
	 */
	public void deleteName3(String name1, String name2, String name3);

	/**
	 * 修改产品名称
	 */
	public void updateName3(String name1, String name2, String newname3,
			String oldname3);

}