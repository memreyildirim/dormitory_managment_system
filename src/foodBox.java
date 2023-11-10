import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;

import javaDatabase.dbHelper;

public class foodBox extends JFrame {
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JLabel lbl1;
    private JMenuBar menubar;
    private JMenu menu1;
    private JMenuItem exitMenuItem;
    private JMenu menu2;
    private JTree tree1;
    private JLabel caloryLbl;
    private JLabel lblinfo;
    private JLabel txtinfo2;
    private Statement myst;

    public foodBox() throws SQLException {
        add(mainPanel);
        TreeForm();

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
                        String sql = "SELECT daily_calory FROM dormitory.foods WHERE food_name = '" + selectedFood + "';";
                        Statement stmt = connection.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            int calory = rs.getInt("daily_calory");
                            caloryLbl.setText("Selected food s calory is: " + calory);
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

    }


    DefaultTreeModel model;
    DefaultMutableTreeNode courses = new DefaultMutableTreeNode("Daily Menu");

    public void Load() {
        DefaultMutableTreeNode mondayNode = new DefaultMutableTreeNode("Monday");
        mondayNode.add(new DefaultMutableTreeNode("Köfte"));
        mondayNode.add(new DefaultMutableTreeNode("Sprite"));
        mondayNode.add(new DefaultMutableTreeNode("Künefe"));
        mondayNode.add(new DefaultMutableTreeNode("Salata"));

        DefaultMutableTreeNode tuesdayNode = new DefaultMutableTreeNode("Tuesday");
        tuesdayNode.add(new DefaultMutableTreeNode("Döner"));
        tuesdayNode.add(new DefaultMutableTreeNode("Ayran"));
        tuesdayNode.add(new DefaultMutableTreeNode("Kemalpaşa"));
        tuesdayNode.add(new DefaultMutableTreeNode("Mevsim salata"));

        courses.add(mondayNode);
        courses.add(tuesdayNode);

        model = (DefaultTreeModel) tree1.getModel();
        model.setRoot(courses);
        tree1.setModel(model);
    }

    public void TreeForm() {


        Load();
        /*
        tree1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tree1.getSelectionPath() != null) {
                    courses = (DefaultMutableTreeNode) tree1.getSelectionPath().getLastPathComponent();

                    txtinfo2.setText(courses.getUserObject().toString());

                    String select = courses.getUserObject().toString();

                    if (select.equals("Photoshop")) {
                        lblinfo.setText("5");
                    } else if (select.equals("CorelDraw")) {
                        lblinfo.setText("6");
                    } else if (select.equals("InDesign")) {
                        lblinfo.setText("7");
                    } else if (select.equals("illustrator")) {
                        lblinfo.setText("4");
                    } else if (select.equals("C#")) {
                        lblinfo.setText("6");
                    } else if (select.equals("Java")) {
                        lblinfo.setText("5");
                    } else if (select.equals("Asp.Net")) {
                        lblinfo.setText("4");
                    } else if (select.equals("Python")) {
                        lblinfo.setText("3");
                    } else if (select.equals("Php")) {
                        lblinfo.setText("2");
                    }

                }
            }
        });
        */
    }



    }




