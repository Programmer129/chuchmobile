import http.HttpUtilsImpl;
import http.MessageService;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SMSSender {

    private static final long PERIOD = 1000 * 60 * 60 * 3; // Send every 3 hour

    private static String[] to = {"+995551154243", "+995597706709"};

    private static void jobber() throws IOException {
        MessageService messageService = new MessageService(new HttpUtilsImpl());

        String[] sendMessage = messageService.sendMessage(to);

        for (String s : sendMessage) {
            System.out.println("Message sent: " + s);
        }

        System.out.println("All messages sent !!!");
    }

    public static void main(String[] args) {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    jobber();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, PERIOD);
    }
}
