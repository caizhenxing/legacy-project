package et.test.mail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

public class GetMail {
    public static String receive(String popServer, String popUser, String popPassword) {
        String mailContent = "";
        Store store = null;
        Folder folder = null;
        try {
            Properties props = System.getProperties();
            Session session = Session.getDefaultInstance(props, null);
            store = session.getStore("pop3");
            store.connect(popServer, popUser, popPassword);
            folder = store.getDefaultFolder();
            if(folder == null) throw new Exception("No default folder");
            folder = folder.getFolder("INBOX");
            if(folder == null) throw new Exception("No POP3 INBOX");
            folder.open(Folder.READ_ONLY);
            Message[] msgs = folder.getMessages();
            for(int msgNum = 0; msgNum < msgs.length; msgNum++) {
                mailContent = mailContent + getMessage(msgs[msgNum]) + "\n\n\n\n";
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (folder!=null) folder.close(false);
                if (store!=null) store.close();
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
        return mailContent;
    }

    public static String getMessage(Message message) {
        String mailContent = null;
        try {
            String from = ((InternetAddress)message.getFrom()[0]).getPersonal();
            if(from==null) from = ((InternetAddress)message.getFrom()[0]).getAddress();
            mailContent = "FROM: "+from;
            String subject = message.getSubject();
            mailContent = mailContent + "\n" +"SUBJECT: "+subject;
            Part messagePart = message;
            Object content = messagePart.getContent();
            if(content instanceof Multipart) {
                messagePart = ((Multipart)content).getBodyPart(0);
                mailContent = mailContent + "\n" +"[ Multipart Message ]";
            }
            mailContent = mailContent + "\n" +"CONTENT: "+content.toString();
            String contentType = messagePart.getContentType();
            mailContent = mailContent + "\n" +"CONTENT:"+contentType;
            if(contentType.startsWith("text/plain") || contentType.startsWith("text/html")) {
                InputStream is = messagePart.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String thisLine = reader.readLine();
                while(thisLine!=null) {
                    mailContent = mailContent + "\n" +thisLine;
                    thisLine = reader.readLine();
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return mailContent;
    }    
    
    public static void main(String[] args) {
    	GetMail getMail = new GetMail();
    	getMail.receive("pop3.163.com", "lgstar888", "bystar888");
    	
	}
}
