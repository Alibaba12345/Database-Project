
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

public class StudentListFrame extends JFrame implements ActionListener {

    JButton nextButton = new JButton("Next");
    JButton prevButton = new JButton("Previous");
    JButton firstButton = new JButton("First");
    JButton lastButton = new JButton("Last");
    JTextField idText = new JTextField(10);
    JTextField nameText = new JTextField(10);
    JTextField studyYearText = new JTextField(10);
    ResultSet recordSet = null;
    Connection connection = null;
    Statement stmt = null;
    static final String jdbcDriver = "com.mysql.jdbc.Driver";
    static final String dbUrl = "jdbc:mysql://localhost/messagingsystem";
    //  Database credentials
    static final String userName = "root";
    static final String userPassword = "Oracle12";

    StudentListFrame() {
        super("Student List");
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
        tempPanel.add(new JLabel("ID:"));
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(idText);
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(new JLabel("Student Name:"));
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(nameText);
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(new JLabel("Year:"));
        displayPanel.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.add(studyYearText);
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
            nameText.setText(recordSet.getString("StudentName"));
            idText.setText(recordSet.getString("StudentID"));
            studyYearText.setText(recordSet.getString("StudyYear"));
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "DB Errror");
        }

    }

    public void readDB() {
        
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
           // connection = DriverManager.getConnection(dbUrl, userName, userPassword);
            connection = DriverManager.getConnection("jdbc:mysql://localhost/messagingsystem","root","");
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM Student";
            recordSet = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set      
            recordSet.first();
            displayRecord();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {            
            
        }//end try
        System.out.println("Goodbye!");
    }//end main

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentListFrame();
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
                if(recordSet.next())
                {
                    displayRecord();
                }
                else
                {
                    recordSet.last();
                }
            } else if (e.getSource() == prevButton) {
                if(recordSet.previous())
                {
                    displayRecord();
                }
                else
                {
                    recordSet.first();
                }
            }

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "DB Errror");
        }
    }

}
