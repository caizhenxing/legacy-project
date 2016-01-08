/*
 * 创建日期 2004-12-13
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package test;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
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
// 初始化名称服务环境
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
// 创建一个OracleDataSource实例
OracleDataSource ods = new OracleDataSource();

ods.setDriverType("thin");
ods.setServerName("Chicago");
ods.setNetworkProtocol("tcp");
ods.setDatabaseName("chidb");
ods.setPortNumber(1521);
ods.setUser("guest");
ods.setPassword("guest");


// 把OracleDataSource实例注册到JNDI中
System.out.println ("Doing a bind with the logical name : " + ln);
ctx.bind (ln,ods);
System.out.println ("Successfully bound");
}

static void lookup (Context ctx, String ln)
throws NamingException,SQLException
{
// 从JNDI中查询OracleDataSource实例
System.out.println ("Doing a lookup with the logical name : " + ln);
OracleDataSource ods = (OracleDataSource) ctx.lookup (ln);
System.out.println ("Successful lookup");

// 从查询到的OracleDataSource实例中获取数据库连接
Connection conn = ods.getConnection();
// 进行数据库操作
getUserName(conn);
// 关闭连接
conn.close();
conn = null;
}

static void getUserName(Connection conn)
throws SQLException
{
// 生成一个Statement实例
Statement stmt = conn.createStatement ();

// 从addressbook表中选中姓名列
ResultSet rset = stmt.executeQuery ("select NAME from addressbook");

// 列出addressbook表所有人的姓名
while (rset.next ())
System.out.println ("Name is " + rset.getString (1));

// 关闭RseultSet实例
rset.close();
rset = null;

// 关闭Statement实例
stmt.close();
stmt = null;
}
} 
