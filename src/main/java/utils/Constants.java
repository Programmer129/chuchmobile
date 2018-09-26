package utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Constants {
    public static final String CHUCK_API = "https://api.chucknorris.io/jokes/random";
    public static final String ACCOUNT_SID = "AC0714a5abec2a4918c7a7c4c637309ea1";
    public static final String AUTH_TOKEN = "2ba39c6df5a9c7f4850d3e592e0f2ed3";
    public static final String FROM = "+18654193208";
    public static final String URL = "jdbc:mysql://0.0.0.0:3306/chuck_service";
    public static final String USER = "root";
    public static final String PASS = "gameri21";
    public static final Path LOG_PATH = Paths.get(Utilities.getProjectPath() + "/src/main/resources/logs/register-users.txt");
    public static final String RM_HOST = "0.0.0.0";
    public static final Integer RM_PORT = 5672;
    public static final String RM_USER = "admin";
    public static final String RM_PASS = "gameri21";
    public static final String RM_QUEUE = "clients";
}
