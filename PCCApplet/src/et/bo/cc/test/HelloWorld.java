/*
 * HelloWorld.java
 *
 * Created on 2006��10��9��, ����10:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package et.bo.cc.test;

/**
 *
 * @author ddddd
 */
public class HelloWorld 
{ 
public static void main(String[] argv){ 
    try{ 
System.out.println("����");//1 
System.out.println("����".getBytes());//2 
System.out.println("����".getBytes("GB2312"));//3 
System.out.println("����".getBytes("ISO8859_1"));//4 
  
System.out.println(new String("����".getBytes()));//5 
System.out.println(new String("����".getBytes(),"GB2312"));//6 
System.out.println(new String("����".getBytes(),"ISO8859_1"));//7 
  
System.out.println(new String("����".getBytes("GB2312")));//8 
System.out.println(new String("����".getBytes("GB2312"),"GB2312"));//9 
System.out.println(new 
  
String("����".getBytes("GB2312"),"ISO8859_1"));//10 
  
System.out.println(new String("����".getBytes("ISO8859_1")));//11 
System.out.println(new 
  
String("����".getBytes("ISO8859_1"),"GB2312"));//12 
System.out.println(new 
  
String("����".getBytes("ISO8859_1"),"ISO8859_1"));//13 
} 
catch(Exception e){ 
e.printStackTrace(); 
} 
  } 
} 

