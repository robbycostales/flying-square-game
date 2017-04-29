import javax.swing.* ;
import java.awt.*;
import java.awt.event.*;

public class MenuBar extends JFrame implements ActionListener {
    private JMenuItem item1 = new JMenuItem("Open");
    private JMenuItem item2 = new JMenuItem("Cancel");
    public MenuBar(){
        JFrame F = new JFrame("Ismail");
        JMenuBar menubar = new JMenuBar();
        JMenu menu1 = new JMenu ("File");

        item1.addActionListener(this);
        item2.addActionListener(this);

        menu1.add(item1);
        menu1.add(item2);

        menubar.add(menu1);

        F.setJMenuBar(menubar);
        F.setSize(300, 100);
        F.setVisible(true);
    }

    public void actionPerformed(ActionEvent E){
        if(E.getSource() == item1){
            JFileChooser F = new JFileChooser(".");
            F.showOpenDialog(null);
        }
        if(E.getSource()==item2){
            System.exit(0);
        }
    }

    public static void main(String args[]){
        new MenuBar();
    }

}
