package javaDatabase;

import javaDatabase.dbHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class yap  {
    public void yapp() throws SQLException {
        {
            Connection connection=null;
            dbHelper helper=new dbHelper();
            Statement statement=null;
            ResultSet resultSet;

            try {
                connection=helper.getConnection();
                statement=connection.createStatement();
                resultSet=statement.executeQuery("SELECT * FROM deneme.ogrenci;");

                while(resultSet.next()){
                    String ad=resultSet.getString("ogr_ad");
                    String no=resultSet.getString("ogr_no");



                }
            } catch (SQLException exception) {
                helper.showErrorMessage(exception);
            }
            finally {
                connection.close();
            }

        }
    }
}
