
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MessageListFrame extends JFrame implements ActionListener {

    JButton nextButton = new JButton("Next");
    JButton prevButton = new JButton("Previous");
    JButton firstButton = new JButton("First");
    JButton lastButton = new JButton("Last");
    JTextField messageText = new JTextField(30);
    JTextField nameText = new JTextField(20);
    JTextField contactText = new JTextField(15);
    JTextField timeText = new JTextField(15);
    ResultSet recordSet = null;
    Connection connection = null;
    Statement stmt = null;
    static final String jdbcDriver = "com.mysql.jdbc.Driver";
    static final String dbUrl = "jdbc:mysql://localhost/messagingsystem";
    //  Database credentials
    static final String userName = "root";
    static final String userPassword = "Oracle12";

    MessageListFrame() {
        super("Messages & Guardian  List");
        this.setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 4));
        controlPanel.add(firstButton);
        controlPanel.add(prevButton);
        controlPanel.add(nextButton);
        controlPanel.add(lastButton);

        setMinimumSize(new Dimension(400, 250));
        setVisible(true);
        add(controlPanel, BorderLayout.SOUTH);
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(5, 2));
        JPanel tempPanel = new JPanel();
        tempPanel.add(new JLabel("MEssage text:"));
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(messageText);
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(new JLabel("Recipient:"));
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(nameText);
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(new JLabel("Contact:"));
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(contactText);
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(new JLabel("Time Sent:"));
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(timeText);
        displayPanel.add(tempPanel);

        add(displayPanel, BorderLayout.CENTER);
        firstButton.addActionListener(this);
        nextButton.addActionListener(this);
        lastButton.addActionListener(this);
        prevButton.addActionListener(this);
        pack();
        readDB();
    }

    private void displayRecord() {
        try {
            nameText.setText(recordSet.getString("Name"));
            messageText.setText(recordSet.getString("MessageContent"));
            contactText.setText(recordSet.getString("Contact"));
            timeText.setText(recordSet.getString("TimeSent"));
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "DB Errror" + se);
        }

    }

    public void readDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //connection = DriverManager.getConnection(dbUrl, userName, userPassword);
            connection = DriverManager.getConnection("jdbc:mysql://localhost/messagingsystem","root","");
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT Message.MessageID,Guardian.Name,Guardian.Contact,Message.MessageContent,Message.TimeSent \n"
                    + "FROM Guardian, MessageToGuardian,message \n"
                    + "Where Guardian.GuardianID=MessageToGuardian.GuardianID and MessageToGuardian.MessageId=Message.MessageId";
            recordSet = stmt.executeQuery(sql);
            recordSet.first();
            displayRecord();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MessageListFrame();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == firstButton) {
                recordSet.first();
                displayRecord();
            }
            if (e.getSource() == lastButton) {
                recordSet.last();
                displayRecord();
            } else if (e.getSource() == nextButton) {
                if (recordSet.next()) {
                    displayRecord();
                } else {
                    recordSet.last();
                }
            } else if (e.getSource() == prevButton) {
                if (recordSet.previous()) {
                    displayRecord();
                } else {
                    recordSet.first();
                }
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "DB Errror");
        }
    }

}
