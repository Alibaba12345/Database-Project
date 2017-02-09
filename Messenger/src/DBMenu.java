
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class DBMenu extends JFrame implements ActionListener {

    JMenuBar menuBar=new JMenuBar();
    JMenu menu, submenu;
    JMenuItem studentItem;
    JMenuItem messageItem;
    JMenuItem studentSearchItem;

    DBMenu() {
        super("DB menu");
        menu = new JMenu("Table");
        menuBar.add(menu);

        studentItem = new JMenuItem("Student List");
        menu.add(studentItem);
        messageItem = new JMenuItem("MessagesList");
        menu.add(messageItem);
        studentSearchItem = new JMenuItem("Student Search");
        menu.add(studentSearchItem);        
        studentItem.addActionListener(this);      
        messageItem.addActionListener(this);      
        studentSearchItem.addActionListener(this);             

        
        setJMenuBar(menuBar);
        setMinimumSize(new Dimension(400, 250));
        setVisible(true);
    }
        public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DBMenu();
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(studentSearchItem==e.getSource())
        {
            new StudentSearchFrame();
        }
        if(studentItem==e.getSource())
        {
            new StudentListFrame();
        }
        if(messageItem==e.getSource())
        {
            new MessageListFrame(); 
        }
    }

}
