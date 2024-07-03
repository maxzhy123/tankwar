import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

public class MyFrame extends JFrame {
    MyPanel myPanel = null;
    static Scanner scanner = new Scanner(System.in);

    public MyFrame() throws HeadlessException {
        System.out.println("请输入选择：1：新游戏\t2：继续游戏");
        int key = scanner.nextInt();
        myPanel = new MyPanel(key);
        this.add(myPanel);
        this.addKeyListener(myPanel);//添加键盘监听器
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("按1确实存档，否则不存档");
                if(scanner.nextInt() == 1) Recorder.saveRecord(myPanel);
                System.exit(0);
            }
        });
        this.setVisible(true);
        new Thread(myPanel).start();
    }
}
