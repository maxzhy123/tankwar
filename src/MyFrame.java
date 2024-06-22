import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    MyPanel myPanel = null;
    public static void main(String[] args) {

    }

    public Frame() throws HeadlessException {
        myPanel = new MyPanel();
        this.add(myPanel);
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
