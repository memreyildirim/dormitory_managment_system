import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import javaDatabase.dbHelper;
import javaDatabase.yap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;


public class adminPage extends JFrame  {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton studentDeleteButton;
    private JButton studentAddButton;
    private JPanel buttonPanel;
    private JPanel generalPanel;
    private JPanel panel1;
    Connection connection= null;
    Statement myStmt;
    ResultSet myRs;
    int ColumnCount,i;
    private JTable table1;


    DefaultTableModel StudentDataModel() {
        Object[][] data = {};
        String[] columnNames = {"stu_id", "stu_no", "stu_name", "stu_surname"};
        DefaultTableModel StudentModel = new DefaultTableModel(data, columnNames);
        StudentModel.setColumnIdentifiers(columnNames);
        try {
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/dormitory","root","240718");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM dormitory.students ");
            while (result.next()) {
                int stu_id = result.getInt("stu_id");
                String stu_no = result.getString("stu_no");
                String stu_name = result.getString("stu_name");
                String stu_surname = result.getString("stu_surname");
                Object[] rowData = {stu_id, stu_no, stu_name, stu_surname};
                StudentModel.addRow(rowData);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return StudentModel;
    }

    public void UpdatingDatabase() {

        try {
//    Class.forName("com.mysql.jdbc.Driver");
            //connection to database
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/dormitory","root","240718");
            //create statement
            myStmt = connection.createStatement();
            //execute sql query
            myRs = myStmt.executeQuery("select * from dormitory.students");
            ResultSetMetaData stData = myRs.getMetaData();

            ColumnCount = stData.getColumnCount();
            DefaultTableModel RecordTable = (DefaultTableModel) table1.getModel();
            RecordTable.setRowCount(0);

            table1.setModel(StudentDataModel());

            TableRowSorter tableSorter1 = new TableRowSorter(table1.getModel());
            tableSorter1.setModel(table1.getModel());
            table1.setRowSorter(tableSorter1);

            //results set
            while (myRs.next()) {
                Vector columnData = new Vector();

                for (i = 1; i <= ColumnCount; i++) {
                    columnData.add(myRs.getString("stu_id"));
                    columnData.add(myRs.getString("stu_no"));
                    columnData.add(myRs.getString("stu_name"));
                    columnData.add(myRs.getString("stu_surname"));
                }
                RecordTable.addRow(columnData);
                //System.out.println(myRs.getString("stu_id") + " , " + myRs.getString("stu_no") + " , " + myRs.getString("stu_name") + " , " + myRs.getString("stu_surname"));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }



    public adminPage(){
        add(generalPanel);
        String no=textField1.getText().toString();
        String name=textField2.getText();
        String surname=textField3.getText();
        UpdatingDatabase();



        studentAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {
                    Connection connection=null;
                    dbHelper helper=new dbHelper();
                    Statement statement=null;
                    ResultSet resultSet;

                    try {
                        connection=helper.getConnection();
                        statement=connection.createStatement();

                        String stu_no = textField1.getText();
                        String stu_name = textField2.getText();
                        String stu_surname = textField3.getText();


                        PreparedStatement ps=connection.prepareStatement("INSERT INTO dormitory.students (stu_no,stu_name,stu_surname) VALUES (?,?,?);");
                        ps.setString(1, stu_no);
                        ps.setString(2,stu_name);
                        ps.setString(3,stu_surname);

                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(rootPane,"ınserted okey");
                        UpdatingDatabase();
                    } catch (SQLException exception) {
                        helper.showErrorMessage(exception);
                    }
                    finally {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            }
        });


        studentDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String number = textField1.getText();

                    Class.forName("com.mysql.jdbc.Driver");
                    //connection to database
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dormitory","root","240718");

                    PreparedStatement ps = connection.prepareStatement("delete from dormitory.students  where stu_no=?");

                    ps.setString(1, number);
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(rootPane, "Delete is  OK");
                    UpdatingDatabase();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }



            }
        });


        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    super.mouseClicked(e);
                    DefaultTableModel RecordTable = (DefaultTableModel) table1.getModel();
                    int SelectedRows = table1.getSelectedRow();
                    if (SelectedRows > 0) {
                        textField1.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
                        textField2.setText(RecordTable.getValueAt(SelectedRows, 2).toString());
                        textField3.setText(RecordTable.getValueAt(SelectedRows, 3).toString());
                    } else System.out.println(" Empty Area");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }





}

