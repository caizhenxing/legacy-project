/*
 * �������� 2004-12-13
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package test;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
import java.sql.*;

import javax.sql.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.pool.OracleDataSource;
import javax.naming.*;
import javax.naming.spi.*;
import java.util.Hashtable;

public class DataSourceJNDI
{
public static void main (String args [])
throws SQLException
{
// ��ʼ�����Ʒ��񻷾�
Context ctx = null;
try
{
Hashtable env = new Hashtable (5);
env.put (Context.INITIAL_CONTEXT_FACTORY,
"com.sun.jndi.fscontext.RefFSContextFactory");
env.put (Context.PROVIDER_URL, "file:JNDI");
ctx = new InitialContext(env);
}
catch (NamingException ne)
{
ne.printStackTrace();
}


try {
	bind(ctx, "jdbc/chidb");
	lookup(ctx, "jdbc/chidb");
} catch (NamingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


}

static void bind (Context ctx, String ln)
throws NamingException, SQLException
{
// ����һ��OracleDataSourceʵ��
OracleDataSource ods = new OracleDataSource();

ods.setDriverType("thin");
ods.setServerName("Chicago");
ods.setNetworkProtocol("tcp");
ods.setDatabaseName("chidb");
ods.setPortNumber(1521);
ods.setUser("guest");
ods.setPassword("guest");


// ��OracleDataSourceʵ��ע�ᵽJNDI��
System.out.println ("Doing a bind with the logical name : " + ln);
ctx.bind (ln,ods);
System.out.println ("Successfully bound");
}

static void lookup (Context ctx, String ln)
throws NamingException,SQLException
{
// ��JNDI�в�ѯOracleDataSourceʵ��
System.out.println ("Doing a lookup with the logical name : " + ln);
OracleDataSource ods = (OracleDataSource) ctx.lookup (ln);
System.out.println ("Successful lookup");

// �Ӳ�ѯ����OracleDataSourceʵ���л�ȡ���ݿ�����
Connection conn = ods.getConnection();
// �������ݿ����
getUserName(conn);
// �ر�����
conn.close();
conn = null;
}

static void getUserName(Connection conn)
throws SQLException
{
// ����һ��Statementʵ��
Statement stmt = conn.createStatement ();

// ��addressbook����ѡ��������
ResultSet rset = stmt.executeQuery ("select NAME from addressbook");

// �г�addressbook�������˵�����
while (rset.next ())
System.out.println ("Name is " + rset.getString (1));

// �ر�RseultSetʵ��
rset.close();
rset = null;

// �ر�Statementʵ��
stmt.close();
stmt = null;
}
} 
