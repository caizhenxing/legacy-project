package testjms;

import java.util.*;
import javax.jms.*;
import javax.naming.*;
/**
 * @author Liang.xf 2004-12-27
 * openJms 非持久订阅同步接收演示
 * www.javayou.com
 */
public class TopicSubscribeSynchronous {

    public static void main(String[] args) {
        try {
            System.out.println("定购消息接收启动：");
            //取得JNDI上下文和连接
            Hashtable properties = new Hashtable();
            properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
            properties.put(Context.PROVIDER_URL, "rmi://localhost:1099/");
            Context context = new InitialContext(properties);

            //获得Topic工厂和Connection
            TopicConnectionFactory factory =
                (TopicConnectionFactory) context.lookup(
                    "JmsTopicConnectionFactory");
            TopicConnection topicConnection = factory.createTopicConnection();
            topicConnection.start();

            //创建Topic的会话，用于接收信息
            TopicSession topicSession =
                topicConnection.createTopicSession(
                    false,
                    Session.AUTO_ACKNOWLEDGE);

            //lookup topic1
            Topic topic = (Topic) context.lookup("topic1");
                    //创建Topic subscriber
            TopicSubscriber topicSubscriber =
                topicSession.createSubscriber(topic);
            //收满10条订阅消息则退出
            for (int i=0; i<10; i++) {
                //同步消息接收，使用receive方法，堵塞等待，直到接收消息
                TextMessage message = (TextMessage) topicSubscriber.receive();
                System.out.println("接收订阅消息["+i+"]: " + message.getText());
            }
            //资源清除，代码略  ... ...
            System.out.println("订阅接收结束.");
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

