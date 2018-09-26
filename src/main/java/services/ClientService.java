package services;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import db.ConsumersRepository;
import http.HttpUtilsImpl;
import services.mobile.MagtiMessageServiceImpl;
import services.mobile.MessageService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

import static utils.Constants.LOG_PATH;
import static utils.Constants.RM_HOST;
import static utils.Constants.RM_PASS;
import static utils.Constants.RM_PORT;
import static utils.Constants.RM_QUEUE;
import static utils.Constants.RM_USER;

public class ClientService {
    private String[] to;

    public ClientService() throws SQLException {
        this.updateTo();
    }

    private void updateTo() throws SQLException {
        ConsumersRepository repository = new ConsumersRepository();
        this.to = repository.getPhones();
    }

    public Runnable receiveNotification() {
        return () -> {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(RM_HOST);
                factory.setPort(RM_PORT);
                factory.setUsername(RM_USER);
                factory.setPassword(RM_PASS);
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                channel.queueDeclare(RM_QUEUE, false, false, false, null);

                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope,
                                               AMQP.BasicProperties properties, byte[] body)
                            throws IOException {
                        String message = new String(body, StandardCharsets.UTF_8).concat(" ".concat(LocalDate.now().toString()));
                        System.out.println("Bot Received: '" + message + "'");
                        Files.write(LOG_PATH, message.getBytes());
                        try {
                            updateTo();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                };
                channel.basicConsume(RM_QUEUE, true, consumer);
            } catch (TimeoutException | IOException ex) {
                ex.printStackTrace();
            }
        };
    }

    public void jobber() throws IOException {
        MessageService messageService = new MagtiMessageServiceImpl(new HttpUtilsImpl());

        String[] sendMessage = messageService.sendMessage(to);

        for (String s : sendMessage) {
            System.out.println(s);
        }

        System.out.println("All messages sent !!!");
    }
}
