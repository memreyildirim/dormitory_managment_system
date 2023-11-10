import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import javaDatabase.dbHelper;

public class foodBox extends JFrame {
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JLabel lbl1;
    private JLabel calorylbl;
    private Statement myst;

    public foodBox() throws SQLException {
        add(mainPanel);

        Connection connection = null;
        dbHelper helper = new dbHelper();

        connection = helper.getConnection();

        ArrayList<String> veriDizisi = new ArrayList<>();
        try {
            String sql = "SELECT food_name FROM dormitory.foods;";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Verileri ArrayList'e ekleme
            while (rs.next()) {
                String ad = rs.getString("food_name");
                veriDizisi.add(ad);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        for (String veri : veriDizisi) {
            comboBox1.addItem(veri);
        }
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == comboBox1) {
                    String selectedFood = (String) comboBox1.getSelectedItem();
                    System.out.println("Seçilen yemek: " + selectedFood);
                    try {
                        Connection connection = null;
                        dbHelper helper = new dbHelper();
                        connection = helper.getConnection();
                        String sql = "SELECT food_calory FROM dormitory.foods WHERE food_name = '" + selectedFood + "';";
                        Statement stmt = connection.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            int calory = rs.getInt("food_calory");
                            calorylbl.setText("Selected food s calory is: " + calory);
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }



    }




