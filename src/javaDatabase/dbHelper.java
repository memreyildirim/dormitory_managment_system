package javaDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbHelper {
    private String username="root";
    private String password="";
    private String dbUrl="jdbc:mysql://localhost:3306/dormitory";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl,username,password);
    }

    public void showErrorMessage(SQLException exception){
        System.out.println("Error:"+exception.getMessage());
        System.out.println("Error code"+exception.getErrorCode());
    }
}
