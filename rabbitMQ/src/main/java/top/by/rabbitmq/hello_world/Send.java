package top.by.rabbitmq.hello_world;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import top.by.rabbitmq.Config;

/**
 * <p>Title: Send</p>
 * <p>Description: 消息发送</p>
 *
 * @author zwp
 * @date 2018/12/28 19:02
 */
public class Send {

    private final static String QUEUE_NAME = "hello";

    // 模拟单个发送、单个接收
    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(Config.HOST);
        factory.setUsername(Config.USERNAME);
        factory.setPassword(Config.PASSWORD);

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
