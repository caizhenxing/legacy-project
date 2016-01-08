package testjms;

import java.util.*;
import javax.jms.*;
import javax.naming.*;
/**
 * @author Liang.xf 2004-12-27
 * openJms 非持久订阅异步接收演示
 * www.javayou.com
 */
public class TopicSubscribeAsynchronous implements MessageListener {
    private TopicConnection topicConnection;
    private TopicSession topicSession;
    private Topic topic;
    private TopicSubscriber topicSubscriber;

    TopicSubscribeAsynchronous() {
        try {
            //取得JNDI上下文和连接
            Hashtable properties = new Hashtable();
            properties.put(
                Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
            properties.put(Context.PROVIDER_URL, "rmi://localhost:1099/");
            Context context = new InitialContext(properties);

            //取得Topic的连接工厂和连接
            TopicConnectionFactory topicConnectionFactory =
                (TopicConnectionFactory) context.lookup(
                    "JmsTopicConnectionFactory");
            topicConnection = topicConnectionFactory.createTopicConnection();

            //创建Topic的会话，用于接收信息
            topicSession = 
                topicConnection.createTopicSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            topic = (Topic) context.lookup("topic1");

            //创建Topic subscriber
            topicSubscriber = topicSession.createSubscriber(topic);
            //设置订阅监听
            topicSubscriber.setMessageListener(this);

            //启动信息接收
            topicConnection.start();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("非同步定购消息的接收：");
        try {
            TopicSubscribeAsynchronous listener =
                new TopicSubscribeAsynchronous();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //收到订阅信息后自动调用此方法
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

