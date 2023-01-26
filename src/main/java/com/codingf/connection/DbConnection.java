package com.codingf.connection;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    public static Properties loadPropertiesFile() throws Exception {

        Properties prop = new Properties();
        InputStream in = new FileInputStream("db.properties");
        prop.load(in);
        in.close();
        return prop;
    }

    public static Connection dbConnection() {

        System.out.println("create jdbc connection using properties file");

        Connection con = null;

        try {

            Properties prop = loadPropertiesFile();

            String driverClass = prop.getProperty("db.driver");
            String host = prop.getProperty("db.host");
            String dbName = prop.getProperty("db.dbase");
            String dbport = prop.getProperty("db.port");
            //String url = prop.getProperty("db.url");

            String url ="jdbc:mysql://" + host + ":" + dbport + "/" + dbName;

            String username = prop.getProperty("db.user");
            String password = prop.getProperty("db.pass");

            Class.forName(driverClass);

            con = DriverManager.getConnection(url, username, password);

            if (con != null) {
                System.out.println("connection created successfully using properties file");
                return con;
            }

            else {
                System.out.println(" unable to create connection");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        /*finally {

            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }*/
        return con;
    }

}