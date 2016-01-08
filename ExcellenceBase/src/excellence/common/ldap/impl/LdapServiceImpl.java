/**
 * 	@(#)LdapServiceImpl.java   2006-9-9 ����06:29:01
 *	��Ȩ���� ������׿Խ�Ƽ����޹�˾�� 
 *	׿Խ�Ƽ� ����һ��Ȩ��
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
 * @author ��һ��
 * @version 2006-9-9
 * @see
 */
public class LdapServiceImpl implements LdapService {

	private String id=null;//����LDAP���ʻ���Ĭ�Ͼ���Admin��
	private String password=null;//�ʻ�Admin�����롣
	private String root=null;//LDAP�ĸ��ڵ��DC
	private String ldapServer=null;//"ldap://localhost:7001/"
	private DirContext dc=null;
	
	public LdapServiceImpl()
	{
		Hashtable env = new Hashtable();
        env.put(DirContext.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");//��������д��������ʲôLDAP��������
        env.put(DirContext.PROVIDER_URL, ldapServer + root);//LDAP�������ĵ�ַ:�˿ڡ���WLS�˿ھ���7001
        env.put(DirContext.SECURITY_AUTHENTICATION, "none");//��Ȩ��𣬿�����������Ȩ���𣬵��������Ϊ�������ֶ��޷���¼����Ҳ��֪��Ϊɶ������ֻ��������ֵ"none"��
        env.put(DirContext.SECURITY_PRINCIPAL, "cn=" + id + "," + root);//�����½�ʻ��͵�¼����
        env.put(DirContext.SECURITY_CREDENTIALS, password);
       try {
			dc = new InitialDirContext(env);//��ʼ��������
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
			   dc.createSubcontext("uid=" + newUserName+",ou=people,ou=myrealm", attrs);  //���һ���ڵ㣬�һ��������Ŀ¼
			  }catch(Exception e){
			   e.printStackTrace();
			  }

	}

	public void delete(IBaseDTO dto) {
		// TODO Auto-generated method stub
		try{
			   String uid = "stella";
			   dc.destroySubcontext("uid=" + uid);  //����UIDɾ��ĳ���ڵ㡣�һ�����ɾ��һ��Ŀ¼��
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
		   
		  en = dc.search("", "uid="+id, constraints); //Ҫ��ѯ��UID�������*����Բ鵽����UID�Ľڵ�
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
		   {//���Բ�����Ԫ��
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
		     {//��øýڵ����������
		       Attribute attr;
			try {
				attr = (Attribute) ae.next();//��һ����
				 String attrId = attr.getID();//��ø����Ե�������
			       for (Enumeration vals = attr.getAll();vals.hasMoreElements();)
			       {//���һ�������е���������ֵ
			           System.out.print("\t\t"+attrId + ": ");
			           Object o = vals.nextElement();//��һ����ֵ
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
			   String account = "stella";//�޸���ǰ�ɵ�ֵ
			   String sn = "stella sn";//�޸��Ժ��µ�ֵ
			   ModificationItem modificationItem[] = new ModificationItem[1];
			   modificationItem[0] =
			    new ModificationItem(
			     DirContext.REPLACE_ATTRIBUTE,
			     new BasicAttribute("sn", sn));//���޸ĵ�����
			   dc.modifyAttributes("uid=" + account, modificationItem);    //ִ���޸Ĳ���
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
