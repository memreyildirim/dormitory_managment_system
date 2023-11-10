import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class studentPage extends JFrame {
    private JPanel generalPanel;
    private JMenuBar menuBar;
    private JMenu menu1;
    private JCheckBox mondayCheckBox;
    private JCheckBox tuesdayCheckBox;
    private JCheckBox wednesdayCheckBox;
    private JCheckBox thursdayCheckBox;
    private JCheckBox fridayCheckBox;
    private JCheckBox saturdayCheckBox;
    private JCheckBox sundayCheckBox;
    private JMenuItem exitMenuItem;


    public studentPage(){
        add(generalPanel);
        generalPanel.setSize(400,600);



        exitMenuItem.addActionListener(new ActionListener() {
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
    }

}
