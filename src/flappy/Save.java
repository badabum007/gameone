package flappy;


import java.io.*;
import java.util.ArrayList;

public class Save {
    public static int x = 0;
    static boolean flag;
    static ArrayList<Integer> arrlist = new ArrayList<Integer>();

    public static void record(int x) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt", true));
            writer.write(String.valueOf(x) + ";");
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void recovery() {
        flag = true;
        try {
            FileReader reader = new FileReader("file.txt");
            int c;
            int coord;
            while (true) {
                coord = 0;
                if ((c = reader.read()) == -1) {
                    Flappy.recovery = false;
                    break;
                }
                do {
                    coord *= 10;
                    c = Integer.parseInt(String.valueOf((char) c));
                    coord += c;
                } while ((c = reader.read()) != 59);
                arrlist.add(coord);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void value() {
        if (arrlist.size() != 0) {
            int column = arrlist.get(0);
            x = column;
            arrlist.remove(0);
        } else {
            if (flag) {
                Flappy.pause = true;
                Flappy.jumpflag = false;
                flag = false;
            }
            x = 0;
        }
    }

}
