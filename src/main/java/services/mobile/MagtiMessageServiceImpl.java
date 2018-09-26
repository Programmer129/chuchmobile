package services.mobile;

import http.HttpUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MagtiMessageServiceImpl extends TwilioMessageServiceImpl {

    public MagtiMessageServiceImpl(HttpUtils httpUtils) {
        super(httpUtils);
        System.setProperty("webdriver.chrome.driver", "/home/levani/IdeaProjects/chromedriver");
    }

    @Override
    public String[] sendMessage(String[] to) throws IOException {
        List<String> logs = new ArrayList<>();

        for (String phone : to) {
            String message = getJoke();
            new Thread(createJob(phone, message)).start();
            logs.add("Message successfully sent to -> " + phone);
        }

        return logs.toArray(new String[0]);
    }

    private Runnable createJob(String phone, String message) {
        return () -> {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");

            WebDriver driver = new ChromeDriver(options);

            driver.get("http://www.magtifun.ge");

            driver.findElement(By.id("user")).sendKeys("551154243");
            driver.findElement(By.id("password")).sendKeys("გამერი21");
            driver.findElement(By.className("red_btn")).click();
            driver.findElement(By.className("english")).click();
            driver.findElement(By.id("recipient")).sendKeys(phone);
            driver.findElement(By.id("message_body")).sendKeys(message);
            driver.findElement(By.className("red_btn")).click();

            driver.quit();
        };
    }
}
