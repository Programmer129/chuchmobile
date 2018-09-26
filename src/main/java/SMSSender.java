import services.ClientService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class SMSSender {

    private static final long PERIOD = 1000 * 60 * 60 * 8;

    public static void main(String[] args) throws SQLException {
        ClientService smsSender = new ClientService();

        new Thread(smsSender.receiveNotification()).start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    smsSender.jobber();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },0, PERIOD);
    }
}
