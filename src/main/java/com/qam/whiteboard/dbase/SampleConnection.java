package com.qam.whiteboard.dbase;

import java.sql.*;


public class SampleConnection {


    public static void main(String[] args) throws SQLException {
        final String host = "reitsolution.se.mysql:3306";
//        final String host = "11.56.0.43:3306";
        final String user = "qamaszy1";
        final String passwd = "9!xK8*~Sf0R7";
        final String database = "qamaszy1_qamaster";
        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?" + "user=" + user + "&password=" + passwd);

            Statement myStmt = connect.createStatement(); //execute sql query
            ResultSet myRs = myStmt.executeQuery("select * from jobs"); //results set

            System.out.println(myRs.getFetchSize());
            while (myRs.next()) {
                System.out.println("in side the result set");
                System.out.println(myRs.getString("last name") + " , " + myRs.getString("first name") + " , " + myRs.getString("email"));
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unexpectingly wrong");
        } finally {
            if (connect != null) {
                System.out.println("Shutting down the connection");
                connect.close();
            }
        }

    }
}
