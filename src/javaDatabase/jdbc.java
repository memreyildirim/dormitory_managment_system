package javaDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbc {



    public static void main(String[] args) throws SQLException {
        Connection connection=null;
        dbHelper helper=new dbHelper();
        Statement statement=null;
        ResultSet resultSet;

        try {
            connection=helper.getConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM deneme.ogrenci;");

            while(resultSet.next()){
                System.out.println("ADI:"+resultSet.getString("ogr_ad"));
                System.out.println("NO:"+resultSet.getString("ogr_no"));

            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
        finally {
            connection.close();
        }

    }

}

