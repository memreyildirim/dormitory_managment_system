import javaDatabase.dbHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class employeeEdit extends JFrame {
    private JPanel mainPanel;
    private JTable employeeTable;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton addEmployee;
    private JButton deleteEmployee;
    Connection connection= null;
    Statement myStmt;
    ResultSet myRs;
    int ColumnCount,i;


    DefaultTableModel EmployeeDataModel() {
        Object[][] data = {};
        String[] columnNames = {"employee_id", "employee_name", "employee_type", "employee_salary"};
        DefaultTableModel EmployeeDataModel = new DefaultTableModel(data, columnNames);
        EmployeeDataModel.setColumnIdentifiers(columnNames);

        dbHelper helper=new dbHelper();
        Connection connection=null;
        try {
            connection=helper.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM dormitory.employee ");
            while (result.next()) {
                int stu_id = result.getInt("employee_id");
                String stu_no = result.getString("employee_name");
                String stu_name = result.getString("employee_type");
                String stu_surname = result.getString("employee_salary");

                Object[] rowData = {stu_id, stu_no, stu_name, stu_surname};
                EmployeeDataModel.addRow(rowData);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return EmployeeDataModel;
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
            myRs = myStmt.executeQuery("select * from dormitory.employee");
            ResultSetMetaData stData = myRs.getMetaData();

            ColumnCount = stData.getColumnCount();
            DefaultTableModel RecordTable = (DefaultTableModel) employeeTable.getModel();
            RecordTable.setRowCount(0);

            employeeTable.setModel(EmployeeDataModel());

            TableRowSorter tableSorter1 = new TableRowSorter(employeeTable.getModel());
            tableSorter1.setModel(employeeTable.getModel());
            employeeTable.setRowSorter(tableSorter1);

            //results set
            while (myRs.next()) {
                Vector columnData = new Vector();

                for (i = 1; i <= ColumnCount; i++) {
                    columnData.add(myRs.getString("employee_id"));
                    columnData.add(myRs.getString("employee_name"));
                    columnData.add(myRs.getString("employee_type"));
                    columnData.add(myRs.getString("employee_salary"));
                }
                RecordTable.addRow(columnData);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }



    public  employeeEdit(){
        add(mainPanel);
        UpdatingDatabase();

        addEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                {
                    Connection connection=null;
                    dbHelper helper=new dbHelper();
                    Statement statement=null;
                    ResultSet resultSet;

                    try {
                        connection=helper.getConnection();


                        String employee_name = textField1.getText();
                        String employee_type = textField2.getText();
                        String employee_salary = textField3.getText();


                        PreparedStatement ps=connection.prepareStatement("INSERT INTO dormitory.employee (employee_name,employee_type,employee_salary) VALUES (?,?,?);");
                        ps.setString(1, employee_name);
                        ps.setString(2,employee_type);
                        ps.setString(3,employee_salary);

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


        deleteEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection=null;
                dbHelper helper=new dbHelper();
                try {
                    String name = textField1.getText();
                    String type=textField2.getText();

                    Class.forName("com.mysql.jdbc.Driver");

                    connection = helper.getConnection();

                    PreparedStatement ps = connection.prepareStatement("delete from dormitory.employee  where employee_name=? and employee_type=?;");

                    ps.setString(1, name);
                    ps.setString(2,type);


                    if (name.equals("")){
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

        employeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                try {
                    super.mouseClicked(e);
                    DefaultTableModel RecordTable = (DefaultTableModel) employeeTable.getModel();
                    int SelectedRows = employeeTable.getSelectedRow();
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
