package top.by.rabbitmq.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import top.by.rabbitmq.Config;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Config.HOST);
        factory.setUsername(Config.USERNAME);
        factory.setPassword(Config.PASSWORD);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey = getRouting(argv);
            String message = getMessage(argv);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }
    //..

    public static String getRouting(String[] argv) {
        String severity = "SEVERITY => NULL";

        if (argv == null) {

        } else if (argv.length >= 1) {
            severity = argv[0];
        }

        return severity;
    }

    public static String getMessage(String[] argv) {
        String message = "MESSAGE => NULL";

        if (argv == null) {

        } else if (argv.length >= 2) {
            message = argv[1];
        } else if (argv.length == 1) {
            message = argv[0];
        }

        return message;
    }
}