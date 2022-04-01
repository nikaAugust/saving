package homework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        List<GameProgress> gameProgresses = new ArrayList<>();
        gameProgresses.add(new GameProgress(8, 4, 10, 10.5));
        gameProgresses.add(new GameProgress(5, 5, 8, 12.34));
        gameProgresses.add(new GameProgress(10, 3, 11, 15.65));

        List<String> filePaths = new ArrayList<>();
        filePaths.add("D:\\Games\\savegames\\save1.dat");
        filePaths.add("D:\\Games\\savegames\\save2.dat");
        filePaths.add("D:\\Games\\savegames\\save3.dat");

        saveGame(filePaths.get(0), gameProgresses.get(0));
        saveGame(filePaths.get(1), gameProgresses.get(1));
        saveGame(filePaths.get(2), gameProgresses.get(2));

        zipFiles("D:\\Games\\savegames\\saves.zip", filePaths);
    }


    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    public static void zipFiles(String zipFilePath, List<String> filePathsToPack) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String filePath : filePathsToPack) {
                File file = new File(filePath);
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zipOutputStream.putNextEntry(entry);
                    byte[] bytes = new byte[fileInputStream.available()];
                    fileInputStream.read(bytes);
                    zipOutputStream.write(bytes);
                    zipOutputStream.closeEntry();
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
                file.delete();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
