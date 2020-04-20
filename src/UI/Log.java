package UI;

// SELECTION for Login as CUSTOMERS / TECHNICIANS /MANAGERS
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class Log extends JFrame implements ActionListener {
    private Button b1, b2;
    public Log(){
        Create_Window();
    }

    public void Create_Window(){
        setSize(380,150);
        setLocation(500,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b1 = new Button(" Log in ");
        b2 = new Button(" Exit ");
        setLayout(new FlowLayout());
        b1.addActionListener(this);
        b2.addActionListener(this);
        add(b1);
        add(b2);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1){
            /*                                 Log IN                                                     */
            Main.page1.setVisible(false);

        } else{
            /*                                   EXIT                                        */
            Main.page1.dispose();

        }
    }
}
