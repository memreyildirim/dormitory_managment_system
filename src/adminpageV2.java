import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class adminpageV2 extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton ADDButton;
    private JButton DELETEButton;
    private JTable table1;
    private JButton LOADButton;
    private JPanel mainpanel;
    private JScrollPane scroll;
    private JComboBox comboBox1;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("adminpageV2");
        frame.setContentPane(new adminpageV2().mainpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600,800);
        frame.setVisible(true);

        String [][] data ={{"1","emre","yıldırım"},{"2","buura","nergiz"},{"3","cem","durak"}};
        String [] title={"no","name","surname"};

        JTable table1=new JTable(data,title);
        table1.setVisible(true);




    }

    public adminpageV2() {
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }



}
