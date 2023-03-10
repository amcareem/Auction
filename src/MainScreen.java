import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen {
    // Declare the GUI components needed for the program
    private JButton CUSTOMERButton;
    private JButton ADMINButton;
    private JPanel auctionPanel;
    JFrame auctionF =new JFrame();

    // Constructor for the MainScreen class
    public MainScreen(){
        // Set up the JFrame instance
        auctionF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specify what should happen when the user closes the window
        auctionF.setContentPane(auctionPanel); // Set the content pane of the window to the auctionPanel
        auctionF.pack(); // Automatically size the window based on the components in it
        auctionF.setLocationRelativeTo(null); // Center the window on the screen

        auctionF.setVisible(true); // Make the window visible

        // Add an ActionListener to the CUSTOMERButton
        CUSTOMERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new instance of the Customer class when the button is clicked
                new Customer();
            }
        });

        // Add an ActionListener to the ADMINButton
        ADMINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new instance of the Admin class when the button is clicked
                new Admin();
            }
        });
    }
}
