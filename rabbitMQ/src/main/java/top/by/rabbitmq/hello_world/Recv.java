package top.by.rabbitmq.hello_world;

import com.rabbitmq.client.*;
import top.by.rabbitmq.Config;

import java.io.IOException;

/**
 * <p>Title: Recv</p>
 * <p>Description: 接收消息</p>
 *
 * @author zwp
 * @date 2018/12/29 10:39
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(Config.HOST);
        factory.setUsername(Config.USERNAME);
        factory.setPassword(Config.PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }
}
