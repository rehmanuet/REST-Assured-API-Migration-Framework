package TestUtils;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

public class ReadPropertyFile {
    public static void main(String[] args) {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            System.out.println("Founder :- " + properties.getProperty("name"));


        } catch (IOException e) {
            System.out.println("Exception Occurred" + e.getMessage());
        }

    }
}