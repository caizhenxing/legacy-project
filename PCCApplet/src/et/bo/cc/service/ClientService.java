/*
 * ClientService.java
 *
 * Created on 2006年10月5日, 下午1:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package et.bo.cc.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import et.bo.cc.applet.AppJApplet;

/**
 *
 * @author zhao yifei
 */
public class ClientService {
    
    
    private int port=12000;
    private String host="192.168.1.3";
    
    private String state;
    
    static public String WAIT_TEL="waitTel";
    public static String CALLING="calling";
    public static String LEAVE="leave";
    public static String NOT_WORKING="notworking";
    
    //OORING
        //OOSERI
        //OODISP
        //OOVALI
    String ring="OORING";
    String seri="OOSERI";
    String disp="OODISP";
    String vali="OOVALI";
    
   
     //private boolean netState=true;
   private Queue<String> commands=new LinkedList<String>();
    
    private AppJApplet applet=null;
    
    private Socket socket=null;
    /** Creates a new instance of ClientService */
    public ClientService(AppJApplet a) {
        this.applet=a;
        connection();
        this.state=this.NOT_WORKING;
        Thread nl=new NetListener();
        nl.start();
        Thread sl=new ServerListener();
        sl.start();
       // Thread cl=new CmdOperListener();
       // cl.start();
        //applet.setBack(false);
       //applet.setSingIn(true);
       //applet.setLeave(false);
       //applet.setSingOut(false);
       //applet.setPassword(false);
    }
    private void init()
    {
       applet.setBack(false);
       applet.setSingIn(true);
       applet.setLeave(false);
       applet.setSingOut(false);
       applet.setPassword(false);
       this.state=this.NOT_WORKING;
    }
    class NetListener extends Thread
    {
        public void run()
        {
            //netConnectListener();
            while(true)
            {
                try {
                    this.sleep(10000);
                } catch (InterruptedException ex) {
                    
                }
            sendMsg("NETTST:;");
            }
        }
    }
    class ServerListener extends Thread
    {
        public void run()
        {
            //acceptDataListener();
            accept();
        }
    }
    class CmdOperListener extends Thread
    {
        public void run()
        {
           operateCmdListener();
        }
    }
    
    private void connection()
    {
        try {
            socket=new Socket(host,port);
            socket.setSoTimeout(3000);
            applet.net(true);
        } catch (Exception ex) {
           socket=new Socket();
           applet.net(false);
        } 
    }
   
    public String getState()
    {
        return state;
    }
    public boolean signIn()
    {
        if(!state.equals(this.NOT_WORKING))
            return false;
        this.state=this.WAIT_TEL;
        applet.setBack(false);
        applet.setSingIn(false);
        applet.setLeave(true);
        applet.setSingOut(true);
        applet.setPassword(false);
        this.sendMsg("00SIGN:1;");
        
        return true;
    }
    public boolean singOut()
    {
        if(!state.equals(this.WAIT_TEL))
            return false;
        this.state=this.NOT_WORKING;
        applet.setBack(false);
        applet.setSingIn(true);
        applet.setLeave(false);
        applet.setSingOut(false);
        applet.setPassword(false);
        this.sendMsg("OOSIGN:0;");
       
        return true;
    }
    public boolean leave()
    {
        if(!state.equals(this.WAIT_TEL))
            return false;
        this.state=this.LEAVE;
        applet.setBack(true);
        applet.setSingIn(false);
        applet.setLeave(false);
        applet.setSingOut(false);
        applet.setPassword(true);
        this.sendMsg("OOSEAT:0;");
        
        return true;
    }
    public boolean back(String password)
    {
        if(!state.equals(this.LEAVE))
            return false;
        this.sendMsg("OOSEAT:1,"+password+";");
        return true;
    }
   private void back()
   {
       this.state=this.WAIT_TEL;
       applet.setBack(false);
       applet.setSingIn(false);
       applet.setLeave(true);
       applet.setSingOut(true);
       applet.setPassword(false);
   }
    
    private void sendMsg(String msg)
    {
        synchronized(socket)
        {
        if(!socket.isConnected())
        {
            connection();
            init();
        }
        OutputStream os;
        try {
            os = socket.getOutputStream();
            os.write(msg.getBytes());
            os.flush();
        } catch (IOException ex) {
            connection();
            init();
        }
        }
    }
    private void accept()
    {
        Queue<String> cms=new LinkedList<String>();
         while(true)
            {
             if(!(socket.isConnected()||!socket.isClosed()))
           {
                connection();
                init();
           }
            
        try{
            StringBuilder sb=new StringBuilder();
                synchronized(socket)
                {
                InputStream is=socket.getInputStream();
                int len=is.available();
                byte[] buffer=new byte[1024];
                if(len==0)
                    continue;
                
                while(0<len)
		{
                    int count=is.read(buffer);
                    
                    len=is.available();
                    sb.append(new String(buffer,0,count));
                    String mm=sb.toString();
                    while(mm.indexOf(";")!=-1)
                    {
                        cms.add(mm.substring(0,mm.indexOf(";")+1));
                        mm=mm.substring(mm.indexOf(";")+1);
                    }
		}
                }
            while(!cms.isEmpty()) {
                String cm=cms.poll();
                List<String> l=parseMsg(cm);
                if(l==null)
                    continue;
                String c=l.get(0);
                
                if(c.equals(this.ring)) {
                    
                    if(l.get(1)!=null&&l.get(1).equals("1")) {
                        applet.ring(true);
                    } else
                        applet.ring(false);
                }
                if(c.equals(this.seri)) {
                    if(l.get(1)!=null)
                        this.callIE(l.get(1));
                }
                if(c.equals(this.disp)) {
                    displayTel(l.get(1));
                }
                if(c.equals(this.vali)) {
                    if(l.get(1).equals("1"))
                        back();
                }
            }
        }catch(IOException e)
        {
           connection();
        }
        }
    }
    
    private void acceptDataListener()
    {
        while(true)
            {
        try{
            StringBuilder sb=new StringBuilder();
                synchronized(socket)
                {
                InputStream is=socket.getInputStream();
                int len=is.available();
                byte[] buffer=new byte[1024];
                if(len==0)
                    continue;
                
                while(0<len)
		{
                    int count=is.read(buffer);
                    
                    len=is.available();
                    sb.append(new String(buffer,0,count));
                    
		}
                }
                
                synchronized(commands)
                {
                commands.add(sb.toString());
                commands.notify();
                }
        }catch(IOException e)
        {
           connection();
        }
        }
    }
    
    private void operateCmdListener()
    {
        while(true)
        {
            String cm;
            synchronized(commands) {
                while(commands.size()==0) {
                    try {
                        commands.wait();
                    } catch (InterruptedException ex) {
                        
                    }
                }
                cm=commands.poll();
            }
        //OORING
        //OOSERI
        //OODISP
        //OOVALI
        
        List<String> l=parseMsg(cm);
        if(l==null)
            continue;
        String c=l.get(0);
        
        if(c.equals(this.ring))
        {
            
            if(l.get(1)!=null&&l.get(1).equals("1"))
            {
                applet.ring(true);
            }
            else
                applet.ring(false);
        }
        if(c.equals(this.seri))
        {
            if(l.get(1)!=null)
                this.callIE(l.get(1));
        }
        if(c.equals(this.disp))
        {
            displayTel(l.get(1));
        }
        if(c.equals(this.vali))
        {
            if(l.get(1).equals("1"))
                back();
        }
        }
    }
    
    private void netConnectListener()
    {
        while(true)
        {
           if(!(socket.isConnected()||!socket.isClosed()))
           {
                connection();
                init();
           }
        }
    }
    private List<String> parseMsg(String msg)
    {
        if(msg.indexOf(";")!=msg.length()-1)
            return null;
        List<String> l=new ArrayList<String>();
        int i=msg.indexOf(":");
        l.add(msg.substring(0,i));
        msg=msg.substring(i+1);
        do
        {
        i=msg.indexOf(",");
        if(i==-1)
        l.add(msg.substring(0,msg.length()-1));
        else
        {
            l.add(msg.substring(0,i));
            msg=msg.substring(i+1);
        }
        }while(i!=-1);
        return l;
    }
    private void callIE(String msg)
    {
        String url="/pcc/policeinfo/info.do?method=toInfoMain";
        applet.callIE(url);
    }
    private void displayTel(String msg)
    {
        /**
         *001#1#13912345678$002#1#**************;
         */
        if(msg==null)
            return;
        Scanner s=new Scanner(msg).useDelimiter("!");
        int i=0;
        while(s.hasNext())
        {
            String temp=s.next();
            i++;
            Scanner a=new Scanner(temp).useDelimiter("#");
            String oper=a.next();
            String state=a.next();
            String tel=a.next();
            applet.setTel(i,oper,state,tel);
            
        }
        
    }
    public static void main(String[] arg0)
    {
       // ClientService cs=new ClientService();
        /*List<String> l=cs.parseMsg("OOSERI:aaa,bbb,ccc");
        if(l!=null)
        for(String s:l)
        {
            System.out.println(s);
        }*/
        //cs.displayTel("001#1#13912345678!002#1#**************!003#2#02422511711!");
    }
    public AppJApplet getApplet() {
        return applet;
    }

    public void setApplet(AppJApplet applet) {
        this.applet = applet;
    }
   
}
