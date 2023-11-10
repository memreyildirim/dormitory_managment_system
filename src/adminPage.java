import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import javaDatabase.dbHelper;

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
    private JMenuBar menuBar1;
    private JMenu menu2;
    private JMenu menu1;
    private JButton updateButton;
    private JButton resetButton;
    private JTextField textField4;
    private JMenuItem exitItem;
    private JMenuItem Foods;


    DefaultTableModel StudentDataModel() {
        Object[][] data = {};
        String[] columnNames = {"stu_id", "stu_no", "stu_name", "stu_surname","room_no"};
        DefaultTableModel StudentModel = new DefaultTableModel(data, columnNames);
        StudentModel.setColumnIdentifiers(columnNames);

        dbHelper helper=new dbHelper();
        Connection connection=null;
        try {
            connection=helper.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM dormitory.students ");
            while (result.next()) {
                int stu_id = result.getInt("stu_id");
                String stu_no = result.getString("stu_no");
                String stu_name = result.getString("stu_name");
                String stu_surname = result.getString("stu_surname");
                String room_no=result.getString("room_no");
                Object[] rowData = {stu_id, stu_no, stu_name, stu_surname,room_no};
                StudentModel.addRow(rowData);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return StudentModel;
    }

    public void UpdatingDatabase() {

        Connection connection=null;
        dbHelper helper=new dbHelper();

        try {
//    Class.forName("com.mysql.jdbc.Driver");
            //connection to database
            connection=helper.getConnection();
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
                    columnData.add(myRs.getString("room_no"));
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

        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                login login=new login();
                login.setVisible(true);
                login.setTitle("Dormitory Management System");
                login.setSize(700,400);

                login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


            }
        });

        Foods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    foodBox foodBox=new foodBox();
                    foodBox.setVisible(true);
                    foodBox.setTitle("Food s and Calories");
                    foodBox.setSize(400,600);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });




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


                        String stu_no = textField1.getText();
                        String stu_name = textField2.getText();
                        String stu_surname = textField3.getText();
                        String room_no=textField4.getText();


                        PreparedStatement ps=connection.prepareStatement("INSERT INTO dormitory.students (stu_no,stu_name,stu_surname,room_no) VALUES (?,?,?,?);");
                        ps.setString(1, stu_no);
                        ps.setString(2,stu_name);
                        ps.setString(3,stu_surname);
                        ps.setString(4,room_no);

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
                Connection connection=null;
                dbHelper helper=new dbHelper();
                try {
                    String number = textField1.getText();

                    Class.forName("com.mysql.jdbc.Driver");

                    connection = helper.getConnection();

                    PreparedStatement ps = connection.prepareStatement("delete from dormitory.students  where stu_no=?");

                    ps.setString(1, number);


                    if (number.equals("")){
                        JOptionPane.showMessageDialog(rootPane,"please choose an option!!");
                    }else {
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(rootPane, "Delete is  OK");

                    }


                    UpdatingDatabase();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }



            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection connection;
                dbHelper helper=new dbHelper();

                try{

                    connection=helper.getConnection();
                    String no = textField1.getText();
                    String name = textField2.getText();
                    String surname = textField3.getText();
                    String roomNo=textField4.getText();

                    PreparedStatement ps=connection.prepareStatement("UPDATE students SET stu_name=?,stu_surname=?,room_no=? WHERE stu_no=?");
                    ps.setString(1,name);
                    ps.setString(2,surname);
                    ps.setString(3,roomNo);
                    ps.setString(4,no);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(rootPane,"updated ");
                    UpdatingDatabase();



                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                textField4.setText("");


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
                        textField4.setText(RecordTable.getValueAt(SelectedRows,4).toString());
                    } else System.out.println(" Empty Area");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }





}

