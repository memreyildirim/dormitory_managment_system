import javaDatabase.yap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javaDatabase.dbHelper;

public class login extends JFrame{
    private JButton button1;
    private JPanel panel1;
    private JTextField textField1;
    private JLabel label1;
    private JLabel labelpassword;
    private JTextField textField2;




    public login(){
        add(panel1);



        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection=null;
                Statement statement;
                dbHelper helper=new dbHelper();

                try {
                    connection=helper.getConnection();
                    //String selectQuery="SELECT * FROM deneme.admins WHERE nameAdmin=? AND passwordAdmin=? ;";

                    String kullaniciAdi=textField1.getText();
                    String sifre=textField2.getText() ;



                    String selectQuery = "SELECT * FROM deneme.admins WHERE nameAdmin = ? AND passwordAdmin = ?";
                    PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                    selectStatement.setString(1, kullaniciAdi);
                    selectStatement.setString(2, sifre);
                    ResultSet resultSet = selectStatement.executeQuery();

                    // Eşleşme kontrolü
                    if (resultSet.next()) {
                        adminPage adminPage=new adminPage();
                        adminPage.setVisible(true);
                        adminPage.setTitle("Dormitory Management System (Admin)");
                        adminPage.setSize(900,600);
                        setVisible(false);
                        adminPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        System.out.println("Giriş başarılı.");

                    } else {
                        System.out.println("Giriş başarısız. Kullanici adi veya sifre hatali.");
                    }

                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

            }
        });
    }






}
