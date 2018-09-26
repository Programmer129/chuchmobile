package services.mobile;

import java.io.IOException;

public interface MessageService {

    String [] sendMessage(String [] to) throws IOException;

     String getJoke() throws IOException;
}
