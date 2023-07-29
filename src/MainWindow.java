import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        setTitle("Snake");
        setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
        setSize(450,345);
        setLocation(400,300);
        add(new Panel());

        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow window  = new MainWindow();
    }
}
