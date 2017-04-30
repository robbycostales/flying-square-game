import javax.swing.* ;
import java.awt.*;
import java.awt.event.*;

public class MenuBar extends JFrame implements ActionListener {
    private JMenuItem item1 = new JMenuItem("New Game");
    private JMenuItem item2 = new JMenuItem("Exit");
    public MenuBar(JFrame F){
        JMenuBar menubar = new JMenuBar();
        JMenu menu1 = new JMenu ("Options");

        item1.addActionListener(this);
        item2.addActionListener(this);

        menu1.add(item1);
        menu1.add(item2);

        menubar.add(menu1);

        F.setJMenuBar(menubar);
        F.setVisible(true);
    }

    public void actionPerformed(ActionEvent E){
        if(E.getSource() == item1){
            System.exit(0);
        }
        if(E.getSource()==item2){
            System.exit(0);
        }
    }
}
