import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

public class Recorder {
    static int destroyedNum = 0; //摧毁坦克数量
    private static String filePath = "tankwar_v1.0_bySwing\\resources\\myRecord.txt"; //存档的位置
    private static ObjectOutputStream ojo= null;
    private static ObjectInputStream oji= null;

    //存档方法
    public static void saveRecord(MyPanel myPanel) {
        try {
            ojo = new ObjectOutputStream(Files.newOutputStream(Paths.get(filePath)));
            ojo.writeInt(destroyedNum);                     //存入摧毁坦克数量
            ojo.writeInt(MyPanel.enemyTanks.size());        //存入敌人数量，方便读档读取
            for (EnemyTank enemyTank : MyPanel.enemyTanks) {
                ojo.writeObject(enemyTank);                 //存入敌人对象
            }
            ojo.writeObject(myPanel.userTank);              //存入玩家坦克对象
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                ojo.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //读档方法
    public static void readRecord(MyPanel myPanel){
        try {
            oji = new ObjectInputStream(Files.newInputStream(Paths.get(filePath)));
            destroyedNum = oji.readInt();               //读出摧毁坦克数量
            int tankNum = oji.readInt();                //读出敌人数量
            Vector<EnemyTank> tanks = new Vector<>();
            //根据敌人数量，读出敌人对象
            for (int i = 0; i < tankNum; i++) {
                tanks.add((EnemyTank)oji.readObject());
            }
            MyPanel.enemyTanks = tanks;
            myPanel.userTank = (UserTank) oji.readObject();//读出玩家坦克对象
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                oji.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
