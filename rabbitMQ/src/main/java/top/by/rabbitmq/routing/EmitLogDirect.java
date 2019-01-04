package top.by.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import top.by.rabbitmq.Config;

public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Config.HOST);
        factory.setUsername(Config.USERNAME);
        factory.setPassword(Config.PASSWORD);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            String severity = getSeverity(argv);
            String message = getMessage(argv);

            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
        }
    }
    //..

    public static String getSeverity(String[] argv) {
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