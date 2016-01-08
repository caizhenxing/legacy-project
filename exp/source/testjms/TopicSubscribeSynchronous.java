package testjms;

import java.util.*;
import javax.jms.*;
import javax.naming.*;
/**
 * @author Liang.xf 2004-12-27
 * openJms �ǳ־ö���ͬ��������ʾ
 * www.javayou.com
 */
public class TopicSubscribeSynchronous {

    public static void main(String[] args) {
        try {
            System.out.println("������Ϣ����������");
            //ȡ��JNDI�����ĺ�����
            Hashtable properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
            properties.put(Context.PROVIDER_URL, "rmi://localhost:1099/");
            Context context = new InitialContext(properties);

            //���Topic������Connection
            TopicConnectionFactory factory =
                (TopicConnectionFactory) context.lookup(
                    "JmsTopicConnectionFactory");
            TopicConnection topicConnection = factory.createTopicConnection();
            topicConnection.start();

            //����Topic�ĻỰ�����ڽ�����Ϣ
            TopicSession topicSession =
                topicConnection.createTopicSession(
                    false,
                    Session.AUTO_ACKNOWLEDGE);

            //lookup topic1
            Topic topic = (Topic) context.lookup("topic1");
                    //����Topic subscriber
            TopicSubscriber topicSubscriber =
                topicSession.createSubscriber(topic);
            //����10��������Ϣ���˳�
            for (int i=0; i<10; i++) {
                //ͬ����Ϣ���գ�ʹ��receive�����������ȴ���ֱ��������Ϣ
                TextMessage message = (TextMessage) topicSubscriber.receive();
                System.out.println("���ն�����Ϣ["+i+"]: " + message.getText());
            }
            //��Դ�����������  ... ...
            System.out.println("���Ľ��ս���.");
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

