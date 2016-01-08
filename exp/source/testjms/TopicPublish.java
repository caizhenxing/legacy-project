package testjms;

import java.util.*;
import javax.jms.*;
import javax.naming.*;
/**
 * @author Liang.xf 2004-12-27
 * openJms ������Ϣ��ʾ
 * www.javayou.com
 */
public class TopicPublish {
    public static void main(String[] args) {
        try {
            //ȡ��JNDI�����ĺ�����
            Hashtable properties = new Hashtable();
            properties.put(
                Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
            //openJmsĬ�ϵĶ˿���1099
            properties.put(Context.PROVIDER_URL, "rmi://localhost:1099/");
            Context context = new InitialContext(properties);
            //���JMS Topic���Ӷ��й���
            TopicConnectionFactory factory =
                (TopicConnectionFactory) context.lookup(
                    "JmsTopicConnectionFactory");

            //����һ��Topic���ӣ�������
            TopicConnection topicConnection = factory.createTopicConnection();
            topicConnection.start();

            //����һ��Topic�Ự���������Զ�Ӧ��
            TopicSession topicSession =
                topicConnection.createTopicSession(false,
                        Session.AUTO_ACKNOWLEDGE);

            //lookup �õ� topic1
            Topic topic = (Topic) context.lookup("topic1");
            //��Topic�Ự����Topic������
            TopicPublisher topicPublisher = topicSession.createPublisher(topic);

            //������Ϣ��Topic
            System.out.println("��Ϣ������Topic");
            TextMessage message = topicSession.createTextMessage
                ("��ã���ӭ����Topic����Ϣ");
            topicPublisher.publish(message);

            //��Դ�����������  ... ...     
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

