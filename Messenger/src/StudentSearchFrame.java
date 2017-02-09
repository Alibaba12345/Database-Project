
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

public class StudentSearchFrame extends JFrame implements ActionListener {

    JButton searchButton = new JButton("Submit");
    JTextField searchText = new JTextField(10);
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

    StudentSearchFrame() {
        super("Student Search");
        this.setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 4));
        controlPanel.add(new JLabel("Enter student ID:"));
        controlPanel.add(searchText);
        controlPanel.add(searchButton);
        setMinimumSize(new Dimension(400, 250));
        setVisible(true);
        add(controlPanel, BorderLayout.NORTH);
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
        searchButton.addActionListener(this);
        pack();
        //readDB();
    }

    private void displayRecord() {
        try {
            nameText.setText(recordSet.getString("StudentName"));
            idText.setText(recordSet.getString("StudentID"));
            studyYearText.setText(recordSet.getString("StudyYear"));
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "No Results" );
        }

    }

    public void readDB() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            //connection = DriverManager.getConnection(dbUrl, userName, userPassword);
            connection = DriverManager.getConnection("jdbc:mysql://localhost/messagingsystem","root","");
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM Student where StudentID=" + searchText.getText();
            recordSet = stmt.executeQuery(sql);
            recordSet.first();
            displayRecord();

        } catch (SQLException se) {
            //Handle errors for JDBC
            JOptionPane.showMessageDialog(this, "No Results" );
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
                new StudentSearchFrame();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            readDB();
            displayRecord();
        }
    }

}
