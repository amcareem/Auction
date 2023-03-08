import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.DateFormat;
import java.util.TimerTask;
import java.util.Vector;
public class Admin {

    private JButton startButton;
    private JLabel timerLabel;
    private JPanel adminPanel;
    private JButton ADDITEMButton;
    private JTable table1;
    private JTextField nameData;
    private JTextField priceData;
    private JTextField path;
    private JButton SELECTIMAGEButton;
    private JLabel imageLabel;
    private JButton CLOSEButton;
    public static String adminNameData="",adminPriceData="";
    public static ImageIcon adminImageData;
    JFrame adminF = new JFrame();
    Timer timer;
    public static int sec = 60;
    public Admin() {
        adminF.setContentPane(adminPanel);
        adminF.pack();
        tableData();
        adminF.setVisible(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
                timer.start();
            }
        });
        ADDITEMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameData.getText().equals("")|| path.getText().equals("")|| priceData.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Please Fill All Fields to add Record.");
                }else{
                    String sql = "insert into auction"+"(ITEM_NAME,IMAGE,PRICE)"+"values (?,?,?)";
                    try {
                        File f = new File(path.getText());
                        InputStream inputStream = new FileInputStream(f);
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/intern","root","root");
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1,nameData.getText());
                        statement.setBlob(2, inputStream);
                        statement.setString(3, priceData.getText());
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(null,"DETAILS ADDED SUCCESSFULLY");
                        nameData.setText("");
                        priceData.setText("");
                        imageLabel.setIcon(null);
                        path.setText("");
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null,ex.getMessage());
                    }
                    tableData();
                }
            }
        });
        SELECTIMAGEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE","jpg","png");
                fileChooser.addChoosableFileFilter(filter);
                int rs = fileChooser.showSaveDialog(null);
                if(rs==JFileChooser.APPROVE_OPTION){
                    File selectedImage = fileChooser.getSelectedFile();
                    path.setText(selectedImage.getAbsolutePath());
                    imageLabel.setIcon(resize(path.getText()));
                }
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel dm = (DefaultTableModel)table1.getModel();
                int selectedRow = table1.getSelectedRow();
                adminNameData=dm.getValueAt(selectedRow,0).toString();
                nameData.setText(adminNameData);
                byte[] img = (byte[]) dm.getValueAt(selectedRow,1);
                ImageIcon imageIcon = new ImageIcon(img);
                Image im = imageIcon.getImage();
                Image newimg = im.getScaledInstance(200,200,Image.SCALE_SMOOTH);
                ImageIcon finalPic = new ImageIcon(newimg);
                adminImageData = finalPic;
                imageLabel.setIcon(adminImageData);
                adminPriceData=dm.getValueAt(selectedRow,2).toString();
                priceData.setText(adminPriceData);
            }
        });
        CLOSEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminF.dispose();
            }
        });
    }
    public ImageIcon resize(String path){
        ImageIcon myImg = new ImageIcon(path);
        Image image = myImg.getImage();
        Image newImage = image.getScaledInstance(200,200,Image.SCALE_SMOOTH);
        ImageIcon finalImage = new ImageIcon(newImage);
        return finalImage;
    }
    public void startTimer(){
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sec--;
                if(sec==-1){
                    timer.stop();
                    tableData();
                }
                else if(sec>=0&&sec<10) timerLabel.setText("00:0"+sec);
                else timerLabel.setText("00:"+ sec);
            }
        });
    }
    public void tableData() {
        try{
            String a= "Select* from auction";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/intern","root","root");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(a);
            table1.setModel(buildTableModel(rs));
        }catch (Exception ex1){
            JOptionPane.showMessageDialog(null,ex1.getMessage());
        }
    }
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
// names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
// data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
}
