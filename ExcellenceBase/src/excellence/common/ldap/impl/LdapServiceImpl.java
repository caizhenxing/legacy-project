/**
 * 	@(#)LdapServiceImpl.java   2006-9-9 下午06:29:01
 *	版权所有 沈阳市卓越科技有限公司。 
 *	卓越科技 保留一切权利
 */
package excellence.common.ldap.impl;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import excellence.common.ldap.LdapService;
import excellence.framework.base.dto.IBaseDTO;

/**
 * @author 赵一非
 * @version 2006-9-9
 * @see
 */
public class LdapServiceImpl implements LdapService {

	private String id=null;//操作LDAP的帐户。默认就是Admin。
	private String password=null;//帐户Admin的密码。
	private String root=null;//LDAP的根节点的DC
	private String ldapServer=null;//"ldap://localhost:7001/"
	private DirContext dc=null;
	
	public LdapServiceImpl()
	{
		Hashtable env = new Hashtable();
        env.put(DirContext.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");//必须这样写，无论用什么LDAP服务器。
        env.put(DirContext.PROVIDER_URL, ldapServer + root);//LDAP服务器的地址:端口。对WLS端口就是7001
        env.put(DirContext.SECURITY_AUTHENTICATION, "none");//授权界别，可以有三种授权级别，但是如果设为另外两种都无法登录，我也不知道为啥，但是只能设成这个值"none"。
        env.put(DirContext.SECURITY_PRINCIPAL, "cn=" + id + "," + root);//载入登陆帐户和登录密码
        env.put(DirContext.SECURITY_CREDENTIALS, password);
       try {
			dc = new InitialDirContext(env);//初始化上下文
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try{
			   String newUserName = "stella";
			   BasicAttributes attrs = new BasicAttributes();
			   BasicAttribute objclassSet = new BasicAttribute("objectclass");
			   objclassSet.add("person"); 
			   objclassSet.add("top"); 
			   objclassSet.add("organizationalPerson"); 
			   objclassSet.add("inetOrgPerson");
			   objclassSet.add("wlsUser");
			   attrs.put(objclassSet);
			   attrs.put("sn", newUserName);
			   attrs.put("uid", newUserName);
			   attrs.put("cn", newUserName);
			   dc.createSubcontext("uid=" + newUserName+",ou=people,ou=myrealm", attrs);  //添加一个节点，我还不会添加目录
			  }catch(Exception e){
			   e.printStackTrace();
			  }

	}

	public void delete(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try{
			   String uid = "stella";
			   dc.destroySubcontext("uid=" + uid);  //按照UID删除某个节点。我还不会删除一个目录。
			   }catch(Exception e){
			    e.printStackTrace();
			   }

	}

	public IBaseDTO search(String id) {
		// TODO Auto-generated method stub
		SearchControls constraints = new SearchControls();
		   constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
		   NamingEnumeration en=null ;
		   try
		  {
		   
		  en = dc.search("", "uid="+id, constraints); //要查询的UID。如果是*则可以查到所有UID的节点
		  }catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   if(en == null){
		    System.out.println("Have no NamingEnumeration.");
		   }
		   if(!en.hasMoreElements()){
		    System.out.println("Have no element.");
		   }
		   while (en != null && en.hasMoreElements())
		   {//可以查出多个元素
		       Object obj = en.nextElement();
		       if(obj instanceof SearchResult)
		       {
		           SearchResult si = (SearchResult) obj;
		           System.out.println("\tname: " + si.getName());
		           Attributes attrs = si.getAttributes();
		           if (attrs == null){
		               System.out.println("\tNo attributes");
		           }else{
		     for (NamingEnumeration ae = attrs.getAll(); ae.hasMoreElements();)
		     {//获得该节点的所有属性
		       Attribute attr;
			try {
				attr = (Attribute) ae.next();//下一属性
				 String attrId = attr.getID();//获得该属性的属性名
			       for (Enumeration vals = attr.getAll();vals.hasMoreElements();)
			       {//获得一个属性中的所有属性值
			           System.out.print("\t\t"+attrId + ": ");
			           Object o = vals.nextElement();//下一属性值
			           if(o instanceof byte[])
			               System.out.println(new String((byte[])o));
			           else
			               System.out.println(o);
			       }
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		      }
		           }
		       }
		       else{
		           System.out.println(obj);
		       }
		   }
		 
		return null;
	}

	public void update(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try{
			   String account = "stella";//修改以前旧的值
			   String sn = "stella sn";//修改以后新的值
			   ModificationItem modificationItem[] = new ModificationItem[1];
			   modificationItem[0] =
			    new ModificationItem(
			     DirContext.REPLACE_ATTRIBUTE,
			     new BasicAttribute("sn", sn));//所修改的属性
			   dc.modifyAttributes("uid=" + account, modificationItem);    //执行修改操作
			  }catch(Exception e){
			   System.out.println("Exception in edit():"+e);
			  }

	}
	public void close(){
        if(dc != null)
        {
            try
            {
                dc.close();
            }
            catch (NamingException e)
            {
                System.out.println("NamingException in close():"+e);
            }
        }    
    }

}
