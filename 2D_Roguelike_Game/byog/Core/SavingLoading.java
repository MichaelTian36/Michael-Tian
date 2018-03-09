package byog.Core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class SavingLoading {
    public static void save(Serializable data) {
        try {
            FileOutputStream fileOut = new FileOutputStream("./byog/Core/world.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            // Writes objects to file
            oos.writeObject(data);
            oos.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    public static Object load() {
        try {
            FileInputStream fileIn = new FileInputStream("./byog/Core/world.txt");
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            World w = (World) ois.readObject();
            System.out.println(w.toString());
            ois.close();
            fileIn.close();
            return w;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}