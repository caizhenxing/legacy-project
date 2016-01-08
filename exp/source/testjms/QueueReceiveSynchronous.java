package testjms;

import java.util.*;
import javax.jms.*;
import javax.jms.Queue;
import javax.naming.*;
/**
 * @author Liang.xf 2004-12-24
 * For openJms ��ʾ, Message����
 * www.javayou.com
 */
public class QueueReceiveSynchronous {
    public static void main(String[] args) {
        try {
            //ȡ��JNDI�����ĺ�����
            Hashtable properties = new Hashtable();
            properties.put(
                Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
            properties.put(Context.PROVIDER_URL, 
                "rmi://localhost:1099/");
            Context context = new InitialContext(properties);

            //���JMS��Ϣ���Ӷ��й���
            QueueConnectionFactory queueConnectionFactory =
                (QueueConnectionFactory) context.lookup(
                    "JmsQueueConnectionFactory");

            //���JMS��Ϣ���Ӷ���
            QueueConnection queueConnection =
                queueConnectionFactory.createQueueConnection();

            //�������ն����߳�
            queueConnection.start();
            //��������Session����������Ϊfalse���Զ�Ӧ����Ϣ����
            QueueSession queueSession =
                queueConnection.createQueueSession(
                    false,
                    Session.AUTO_ACKNOWLEDGE);
            //���Ĭ���ڽ���JMS��Ķ���֮һ��queue1
            Queue queue = (Queue) context.lookup("queue1");
            //����JMS���н�����
            QueueReceiver queueReceiver =
                queueSession.createReceiver(queue);
            //ͨ��ͬ���ķ���������Ϣ
            Message message = queueReceiver.receive();
            String messageText = null;
            if (message instanceof TextMessage)
                messageText = ((TextMessage) message).                          
                    getText();
            System.out.println(messageText);
            //���������������������
            // ... ...
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

