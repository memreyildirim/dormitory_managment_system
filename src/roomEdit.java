import javaDatabase.dbHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class roomEdit extends JFrame {
    private JPanel mainPanel;
    private JTable roomTable;
    private JTextField textField1;
    private JTextField textField2;
    private JButton addRoom;
    private JButton deleteRoom;
    Connection connection= null;
    Statement myStmt;
    ResultSet myRs;
    int ColumnCount,i;

    DefaultTableModel RoomModel() {
        Object[][] data = {};
        String[] columnNames = {"room_no", "room_type"};
        DefaultTableModel RoomModel = new DefaultTableModel(data, columnNames);
        RoomModel.setColumnIdentifiers(columnNames);

        dbHelper helper=new dbHelper();
        Connection connection=null;
        try {
            connection=helper.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM dormitory.rooms ");
            while (result.next()) {
                int room_no = result.getInt("room_no");
                String room_type= result.getString("room_type");


                Object[] rowData = {room_no, room_type};
                RoomModel.addRow(rowData);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return RoomModel;
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
            myRs = myStmt.executeQuery("select * from dormitory.rooms");
            ResultSetMetaData stData = myRs.getMetaData();

            ColumnCount = stData.getColumnCount();
            DefaultTableModel RecordTable = (DefaultTableModel) roomTable.getModel();
            RecordTable.setRowCount(0);

            roomTable.setModel(RoomModel());

            TableRowSorter tableSorter1 = new TableRowSorter(roomTable.getModel());
            tableSorter1.setModel(roomTable.getModel());
            roomTable.setRowSorter(tableSorter1);

            //results set
            while (myRs.next()) {
                Vector columnData = new Vector();

                for (i = 1; i <= ColumnCount; i++) {
                    columnData.add(myRs.getString("room_no"));
                    columnData.add(myRs.getString("room_type"));

                }
                RecordTable.addRow(columnData);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public roomEdit(){
        add(mainPanel);
        UpdatingDatabase();
        addRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    Connection connection=null;
                    dbHelper helper=new dbHelper();
                    Statement statement=null;
                    ResultSet resultSet;

                    try {
                        connection=helper.getConnection();


                        String room_name = textField1.getText();
                        String room_type = textField2.getText();


                        PreparedStatement ps=connection.prepareStatement("INSERT INTO dormitory.rooms (room_no,room_type) VALUES (?,?);");
                        ps.setString(1, room_name);
                        ps.setString(2,room_type);

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
        });


        deleteRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection=null;
                dbHelper helper=new dbHelper();
                try {
                    String room_no = textField1.getText();

                    Class.forName("com.mysql.jdbc.Driver");

                    connection = helper.getConnection();

                    PreparedStatement ps = connection.prepareStatement("delete from dormitory.rooms  where room_no=? ;");

                    ps.setString(1, room_no);



                    if (room_no.equals("")){
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
    }
}
