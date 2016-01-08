package et.bo.productTypeDwr.service;

public interface ProductTypeService {

	//******************* ��Ʒ���� С�� ���ƵĲ�ѯ *******************************************
	/**
	 * �ַ�����ʽ�Ĳ�Ʒ���༯�� �߲�#ˮ��
	 * @return "�߲�#ˮ��"
	 */
	public String getAllName1();

	/**
	 * �����µ�С�� �� ˮ������-->���С�� 
	 * @param cityName
	 * @return "���#����#�˹�"
	 */
	public String getAllName2(String name1);

	/**
	 * С���µĲ�Ʒ����  �� ���С�� --> ����
	 * @param sectionName
	 * @return "����#С����#�����"
	 */
	public String getAllName3(String name2);

	//	/**
	//	 * �����µ�����/�� �徭�� �¼��� ����ׯ��
	//	 * @param villageName
	//	 * @return "�徭��#�¼���#����ׯ��"
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

	//************************��Ʒ���� �� ɾ �� ********************************************
	/**
	 * ���Ӳ�Ʒ����
	 * @param name1
	 */
	public void addName1(String name1);

	/**
	 * ɾ����Ʒ����
	 * @param cityName
	 */
	public void deleteName1(String name1);

	/**
	 * �޸Ĳ�Ʒ����
	 * @param name1
	 * 
	 */
	public void updateName1(String newName1, String oldName1);

	/**
	 * ���Ӳ�ƷС��
	 */
	public void addName2(String name1, String name2);

	/**
	 * ɾ����ƷС��
	 */
	public void deleteName2(String name1, String name2);

	/**
	 * 
	 * �޸Ĳ�ƷС��
	 */
	public void updateName2(String name1, String newname2, String oldname2);

	//	************************��Ʒ���� �� ɾ �� ********************************************
	/**
	 * ���Ӳ�Ʒ����
	 */
	public void addName3(String name1, String name2, String name3);

	/**
	 * ɾ����Ʒ����
	 */
	public void deleteName3(String name1, String name2, String name3);

	/**
	 * �޸Ĳ�Ʒ����
	 */
	public void updateName3(String name1, String name2, String newname3,
			String oldname3);

}