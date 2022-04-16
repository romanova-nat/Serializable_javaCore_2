package ru.netoligy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(1, 2, 3, 4.5);
        GameProgress gameProgress2 = new GameProgress(2, 3, 4, 5.5);
        GameProgress gameProgress3 = new GameProgress(3, 4, 5, 6.5);

        List<String> list = new ArrayList<>();

        saveGame(gameProgress1, "D:\\Ystanovka_JavaCore_1.1\\src\\ru\\netoligy\\Games\\savegames\\save1.dat", list);
        saveGame(gameProgress2, "D:\\Ystanovka_JavaCore_1.1\\src\\ru\\netoligy\\Games\\savegames\\save2.dat", list);
        saveGame(gameProgress3, "D:\\Ystanovka_JavaCore_1.1\\src\\ru\\netoligy\\Games\\savegames\\save3.dat", list);

        zipFiles("D:\\Ystanovka_JavaCore_1.1\\src\\ru\\netoligy\\Games\\savegames\\zip.zip", list);
    }

    public static List<String> saveGame(GameProgress gameProgress, String save, List<String> list) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(save);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameProgress);
            list.add(save);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void zipFiles(String zip, List<String> list) {
        try (FileOutputStream fos = new FileOutputStream(zip);
             ZipOutputStream zout = new ZipOutputStream(fos)) {

            for (String s : list) {
                File file = new File(s);
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println("Не удалось заархивировать");
                }
            }
        } catch (Exception ex) {
            System.out.println("Не удалось заархивировать");
        }

        for (String del : list) {
            File file = new File(del);
            if (file.delete()) {
                System.out.println("Файл удален");
            }
        }
    }
}

