package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    private static Properties prop = new Properties();

    private static String url;
    private static String user;
    private static String password;

    static {
        File propFile = new File("properties/db.properties");
        try {
            FileInputStream fis = new FileInputStream(propFile);
            prop.load(fis);
            System.out.println("db.properties File Load.");

            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

            Class.forName(prop.getProperty("driver"));
        } catch (FileNotFoundException e) {
            System.out.println(propFile.getAbsolutePath());
            System.out.println("db.properties File Not Found.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, password);

            return con;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void close(Object _obj) {
        if(_obj != null) {
            if(_obj instanceof Connection) {
                try {
                    ((Connection) _obj).close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (_obj instanceof ResultSet) {
                try {
                    ((ResultSet) _obj).close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (_obj instanceof Statement) {
                try {
                    ((Statement) _obj).close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (_obj instanceof PreparedStatement) {
                try {
                    ((PreparedStatement) _obj).close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(prop.get("url"));
    }

}
