package testjms;

import java.util.*;
import javax.jms.*;
import javax.naming.*;
/**
 * @author Liang.xf 2004-12-27
 * openJms 发布消息演示
 * www.javayou.com
 */
public class TopicPublish {
    public static void main(String[] args) {
        try {
            //取得JNDI上下文和连接
            Hashtable properties = new Hashtable();
            properties.put(
                Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
            //openJms默认的端口是1099
            properties.put(Context.PROVIDER_URL, "rmi://localhost:1099/");
            Context context = new InitialContext(properties);
            //获得JMS Topic连接队列工厂
            TopicConnectionFactory factory =
                (TopicConnectionFactory) context.lookup(
                    "JmsTopicConnectionFactory");

            //创建一个Topic连接，并启动
            TopicConnection topicConnection = factory.createTopicConnection();
            topicConnection.start();

            //创建一个Topic会话，并设置自动应答
            TopicSession topicSession =
                topicConnection.createTopicSession(false,
                        Session.AUTO_ACKNOWLEDGE);

            //lookup 得到 topic1
            Topic topic = (Topic) context.lookup("topic1");
            //用Topic会话生成Topic发布器
            TopicPublisher topicPublisher = topicSession.createPublisher(topic);

            //发布消息到Topic
            System.out.println("消息发布到Topic");
            TextMessage message = topicSession.createTextMessage
                ("你好，欢迎定购Topic类消息");
            topicPublisher.publish(message);

            //资源清除，代码略  ... ...     
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

