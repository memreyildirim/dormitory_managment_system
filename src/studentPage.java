import javax.swing.*;
import java.awt.event.*;

public class studentPage extends JFrame {
    private JPanel generalPanel;
    private JMenuBar menuBar;
    private JMenu menu1;
    private JMenuItem exitMenuItem;
    private JList list1;


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


        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // JList üzerinde bir öğeye tıklandığında yapılacak işlemler
                if (e.getClickCount() == 1) { // Tek tıklama kontrolü
                    int index = list1.locationToIndex(e.getPoint());
                    String selectedValue = (String) list1.getModel().getElementAt(index);
                    System.out.println("Tıklanan öğe: " + selectedValue);

                    if (selectedValue.equals("ROOM CLEANING")){

                        roomCleaning roomCleaning=new roomCleaning();
                        roomCleaning.setVisible(true);
                        roomCleaning.setSize(500,800);
                        roomCleaning.setTitle("Room Cleaning Page");
                    }
                    else if (selectedValue.equals("PAYMENT METHOD")){

                        paymentMethod paymentMethod=new paymentMethod();
                        paymentMethod.setVisible(true);
                        paymentMethod.setSize(500,800);
                        paymentMethod.setTitle("Payment Method Page");
                    }
                    else if (selectedValue.equals("FOOD MENU")){

                        foodMenuPage foodMenuPage=new foodMenuPage();
                        foodMenuPage.setVisible(true);
                        foodMenuPage.setSize(500,800);
                        foodMenuPage.setTitle("Food Menu Page");

                    }
                }

            }
        });
    }

}
