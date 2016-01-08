package testjms;

import java.util.*;
import javax.jms.*;
import javax.jms.Queue;
import javax.naming.*;
/**
 * @author Liang.xf 2004-12-24
 * For openJms 演示, Message 发送
 * www.javayou.com */
public class QueueSend {
    public static void main(String[] args) {
        try {
            //取得JNDI上下文和连接
            Hashtable properties = new Hashtable();
            properties.put(
                Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
            //openJms默认的端口是1099
            properties.put(Context.PROVIDER_URL,
                 "rmi://localhost:1099/");
            Context context = new InitialContext(properties); 
            //获得JMS信息连接队列工厂
            QueueConnectionFactory queueConnectionFactory =
                (QueueConnectionFactory) context.lookup(
                    "JmsQueueConnectionFactory");
            //获得JMS信息连接队列
            QueueConnection queueConnection =
                queueConnectionFactory.createQueueConnection();
            //产生队列Session，设置事务为false，自动应答消息接收
            QueueSession queueSession =
                queueConnection.createQueueSession(
                    false,
                    Session.AUTO_ACKNOWLEDGE);

            //获得默认内建在JMS里的队列之一：queue1
            Queue queue = (Queue) context.lookup("queue1");
            //产生JMS队列发送器
            QueueSender queueSender = 
                queueSession.createSender(queue);
            //发送数据到JMS 
            TextMessage message = queueSession.createTextMessage();
            message.setText("Hello, I'm openJms.");
            queueSender.send(message);

            System.out.println("信息写入JMS服务器队列");

            //以下做清除工作，代码略
            // ... ...
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
