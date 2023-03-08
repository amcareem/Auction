import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen {
    private JButton CUSTOMERButton;
    private JButton ADMINButton;
    private JPanel auctionPanel;
    JFrame auctionF =new JFrame();

    public MainScreen(){
        auctionF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        auctionF.setContentPane(auctionPanel);
        auctionF.pack();
        auctionF.setLocationRelativeTo(null);

        auctionF.setVisible(true);
        CUSTOMERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Customer();
            }
        });
        ADMINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Admin();
            }
        });
    }
}
