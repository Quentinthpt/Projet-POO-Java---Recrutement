package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static Properties loadEmailConfig() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/config/email.properties"));
        } catch (IOException e) {
            e.printStackTrace(); // You can add user-friendly message or fallback
        }
        return props;
    }
}
