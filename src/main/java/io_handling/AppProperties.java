package io_handling;


import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    @Getter
    public static String ordersPath;
    @Getter
    public static String workLogPath;
    @Getter
    public static String orderResultPath;

    public static void loadProperties(){
        try {
            Properties prop = new Properties();
            String propFileName = "/config.properties";

            InputStream inputStream = AppProperties.class.getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            ordersPath = prop.getProperty("orders");
            workLogPath = prop.getProperty("workLog");
            orderResultPath = prop.getProperty("orderResult");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}