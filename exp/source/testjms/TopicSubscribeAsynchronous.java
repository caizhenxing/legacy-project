package testjms;

import java.util.*;
import javax.jms.*;
import javax.naming.*;
/**
 * @author Liang.xf 2004-12-27
 * openJms �ǳ־ö����첽������ʾ
 * www.javayou.com
 */
public class TopicSubscribeAsynchronous implements MessageListener {
    private TopicConnection topicConnection;
    private TopicSession topicSession;
    private Topic topic;
    private TopicSubscriber topicSubscriber;

    TopicSubscribeAsynchronous() {
        try {
            //ȡ��JNDI�����ĺ�����
            Hashtable properties = new Hashtable();
            properties.put(
                Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
            properties.put(Context.PROVIDER_URL, "rmi://localhost:1099/");
            Context context = new InitialContext(properties);

            //ȡ��Topic�����ӹ���������
            TopicConnectionFactory topicConnectionFactory =
                (TopicConnectionFactory) context.lookup(
                    "JmsTopicConnectionFactory");
            topicConnection = topicConnectionFactory.createTopicConnection();

            //����Topic�ĻỰ�����ڽ�����Ϣ
            topicSession = 
                topicConnection.createTopicSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            topic = (Topic) context.lookup("topic1");

            //����Topic subscriber
            topicSubscriber = topicSession.createSubscriber(topic);
            //���ö��ļ���
            topicSubscriber.setMessageListener(this);

            //������Ϣ����
            topicConnection.start();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("��ͬ��������Ϣ�Ľ��գ�");
        try {
            TopicSubscribeAsynchronous listener =
                new TopicSubscribeAsynchronous();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //�յ�������Ϣ���Զ����ô˷���
    public void onMessage(Message message) {
        try {
            String messageText = null;
            if (message instanceof TextMessage)
                messageText = ((TextMessage) message).getText();
            System.out.println(messageText);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

