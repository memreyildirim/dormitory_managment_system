import javaDatabase.dbHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.sql.*;
import java.util.Vector;

public class dbTables extends JFrame{
    private JPanel mainPanel;
    private JTree tree1;
    private JList list1;
    private JTable table1;
    Connection connection= null;
    Statement myStmt;
    ResultSet myRs;
    int ColumnCount,i;
    DefaultTreeModel modelTable;
    DefaultMutableTreeNode database_tables = new DefaultMutableTreeNode("Database Tables");


    public dbTables(){
        add(mainPanel);


    }



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
}
