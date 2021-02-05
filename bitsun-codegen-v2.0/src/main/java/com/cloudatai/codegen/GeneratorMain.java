package com.cloudatai.codegen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * @author jinge
 * @email admin(a)cnbbx.com
 */
public class GeneratorMain {
    public static void main(String[] args) throws Exception {
//        Generator g = new Generator();
//        String createMode = PropertiesProvider.getProperty("createMode","rebuild");
//        if(!"cover".equals(createMode)){
//            g.clean();
//        }
//        System.out.println("<<<<<<<<<<starting!>>>>>>>>>>");
//        promptInfo();
//        g.generateSelectTables();
//        System.out.println("<<<<<<<<<<completed!>>>>>>>>>>");
//        System.exit(0);
        Connection conn = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://192.168.183.186:3306/?useSSL=false", "root", "123456");
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet rs = dbMetaData.getTables(conn.getCatalog(), "root", null, new String[]{"TABLE"});
        while(rs.next()) {
            System.out.println(rs.getString("TABLE_NAME"));
        }

    }

    private static void promptInfo() {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    System.out.println("project is running ,please wait!");
                }
            }catch (Exception ex){

            }
        }).start();
    }
}
