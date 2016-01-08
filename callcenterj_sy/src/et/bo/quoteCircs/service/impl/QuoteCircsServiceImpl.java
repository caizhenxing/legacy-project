
package et.bo.quoteCircs.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.RowSet;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContext;

import et.bo.quoteCircs.service.QuoteCircsService;
import et.po.OperCustinfo;
import et.po.SysUser;
import excellence.common.page.PageInfo;
import excellence.framework.base.container.SpringRunningContainer;
import excellence.framework.base.dao.BaseDAO;
import excellence.framework.base.dto.IBaseDTO;
import excellence.framework.base.dto.impl.DynaBeanDTO;


public class QuoteCircsServiceImpl implements QuoteCircsService {
	
	BaseDAO dao = null;
	private int num = 0;

	public BaseDAO getDao() {
		return dao;
	}
	public void setDao(BaseDAO dao) {
		this.dao = dao;
	}



	/**
	 * ��ѯ�����б�,���ؼ�¼��list�� ȡ�ò�ѯ�����б����ݡ�
	 * 
	 * @param dto
	 *            ���ݴ������
	 * @param pi
	 *            ҳ����Ϣ
	 * @return ���ݵ�list
	 */
	public List quoteCircsQuery(IBaseDTO dto, PageInfo pi) {
		List list = new ArrayList();
		QuoteCircsHelp h = new QuoteCircsHelp();
		Object[] result = (Object[]) dao.findEntity(h.linkManQuery(dto, pi));
		num = dao.findEntitySize(h.linkManQuery(dto, pi));
		
		String beginTime = (String)dto.get("beginTime");
        String endTime = (String)dto.get("endTime");
		
		for (int i = 0, size = result.length; i < size; i++) {
			OperCustinfo oc = (OperCustinfo) result[i];
			list.add(po2dto(oc, beginTime, endTime));
		}
		
		return list;
	}

	/**
	 * ��ѯ������ po ת dto
	 * 
	 * @param po
	 * @return dto
	 */
	private DynaBeanDTO po2dto(OperCustinfo po, String beginTime, String endTime) {

		DynaBeanDTO dto = new DynaBeanDTO();
		
		dto.set("custName", po.getCustName());
		dto.set("custAddr", po.getCustAddr());
		String tel = po.getCustTelMob();
		if(tel == null || tel.equals("")){
			tel = po.getCustTelHome();
		}else if(tel == null || tel.equals("")){
			tel = po.getCustTelWork();
		}
		dto.set("custTel", tel);
		
		String custId = po.getCustId();
		Date addtime = po.getAddtime();
		String regDate = "";
		if(addtime == null){	//���û��ע��ʱ��
			regDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(addtime);
		}else{
			regDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(addtime);
		}
		
		try{
			java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
	    	Calendar calendar = Calendar.getInstance();
	    	
			Date beginDate = df.parse(beginTime);
	        Date endDate = df.parse(endTime);
	        
	        calendar.setTime(beginDate);
	        int beginTimeWeek = calendar.get(calendar.DAY_OF_WEEK);
	        calendar.add(Calendar.DATE, - beginTimeWeek + 2);
	        beginDate = calendar.getTime();
	        
	        calendar.setTime(endDate);
	        int endTimeWeek = calendar.get(calendar.DAY_OF_WEEK);
	        
	        List countList = new ArrayList();
	        calendar.add(Calendar.DATE, - endTimeWeek + 2);	//��ʱ�Ѿ��ǵ�ǰ��һ������
	        
	        String date = df.format(calendar.getTime());       
	        
			if(isEmpty(custId)){
				int sum = 0;
				sum++;
				dto.set("succeed", "0");
				countList.add(0);
				while(endDate.after(beginDate)){						//��ǰ���ڴ���beginDate
		        	calendar.add(Calendar.DATE, -7);			//������ǰ����һ����һ
		        	endDate = calendar.getTime();	
		        	countList.add(0);
		        	
		        	sum++;
				}
				
				dto.set("must", sum);
			}else{
			
				int sum = 0;
				int ok = 0;
				
				
		        String count = getPriceCount(custId, date);
		        if(count.equals("0")){
	        		countList.add("0");
	        	}else{
	        		countList.add("1");
//	        		ok++;
	        	}
//		        countList.add(count);								//�����Ȱ����ܴ��ȥ
		        sum++;											//�涼���ˣ���Ҳ�ü��ϰ�
		        while(endDate.after(beginDate)){						//��ǰ���ڴ���beginDate
		        	calendar.add(Calendar.DATE, -7);			//������ǰ����һ����һ
		        	endDate = calendar.getTime();						//���һ��Ҫ���ϣ�����ͻ���ִ�˵�е���ѭ��
		        	date = df.format(endDate);
		        	count = getPriceCount(custId, date);
		        	if(count.equals("0")){
		        		countList.add("0");
		        	}else{
		        		countList.add("1");
		        		ok++;
		        	}
		        	
		        	sum++;
		        }
		        dto.set("succeed", ok);
		        dto.set("must", sum);
			}
			dto.set("countList", countList);
			
		}catch(Exception e){
    		System.err.println(e);
    	}
		return dto;
	}

	/**
	 * ��ѯ�����б�������� ȡ�������ѯ�б��������
	 * 
	 * @return �õ�list������
	 */
	public int getQuoteCircsSize() {
		return num;
	}
	/**
	 * �鿴���û��Ƿ񱨹����ݣ���Ϊ���û�����ȴû�б������ݵ���������������ж�һ�£�����ٺܴ�һ�����������
	 * 
	 * @return �õ�list������
	 */
	public boolean isEmpty(String cust_id){
		
		boolean b = true;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		BasicDataSource bds = (BasicDataSource) SpringRunningContainer.getInstance().getBean("datasource");

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from oper_priceinfo where cust_id = '"+ cust_id +"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				b = false;
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		return b;
	}
	/**
	 * �鿴���û��Ƿ񱨹����ݣ���Ϊ���û�����ȴû�б������ݵ���������������ж�һ�£�����ٺܴ�һ�����������
	 * 
	 * @return �õ�list������
	 */
	public boolean isSucceed(String cust_id, String date){
		
		boolean b = true;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		BasicDataSource bds = (BasicDataSource) SpringRunningContainer.getInstance().getBean("datasource");

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from oper_priceinfo where cust_id = '"+ cust_id +"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				b = false;
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		return b;
	}
	
	/**
	 * �鿴���û��Ƿ񱨹����ݣ���Ϊ���û�����ȴû�б������ݵ���������������ж�һ�£�����ٺܴ�һ�����������
	 * 
	 * @return �õ�list������
	 */
	public String getPriceCount(String cust_id, String date){
		
		String count = "";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		BasicDataSource bds = (BasicDataSource) SpringRunningContainer.getInstance().getBean("datasource");

		try {
			conn = bds.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "select count(*) from oper_priceinfo where deploy_time >= '"+ date +"' and deploy_time < dateadd(d,7,'"+ date +"') and cust_id = '"+ cust_id +"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				count = rs.getString(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		return count;
	}
	
	/**
	 * �������Ա�б�
	 * @param sql
	 * @return List
	 */
	public List userQuery(String sql) {
		RowSet rs=dao.getRowSetByJDBCsql(sql);
		List<OperCustinfo> list=new ArrayList<OperCustinfo>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				OperCustinfo oc = new OperCustinfo();
				oc.setCustName(rs.getString("cust_name"));
				list.add(oc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return list;
	}

}
