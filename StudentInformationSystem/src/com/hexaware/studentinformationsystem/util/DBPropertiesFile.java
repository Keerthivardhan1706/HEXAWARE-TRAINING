package com.hexaware.studentinformationsystem.util;


import java.util.Properties;
import java.io.FileInputStream;

public class DBPropertiesFile {

    public static String getConnectionString(String fileName) throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream(fileName));

        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String driver = props.getProperty("driver");

        return url + "|" + user + "|" + password + "|" + driver;
    }
}

