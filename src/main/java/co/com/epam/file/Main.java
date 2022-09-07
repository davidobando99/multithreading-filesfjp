package co.com.epam.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Main {
    private static final String randomFolderName= "random-files";
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileService fileService = new FileService(null,null);
        FolderService folderService = new FolderService();
        System.out.println("¿Desea generar un directorio con archivos aleatorios? (S/N)");
        String respuesta =  scanner.nextLine();
        Path folderPath = null;
        if(respuesta.equals("S")){
            folderPath = createRandomFiles();
        }else{
            folderPath = folderService.getPathFromFolderName(randomFolderName);
        }
        List<File> files = Arrays.stream(
                Objects.requireNonNull(new File(folderPath.toUri()).listFiles()))
                .parallel().filter(File::isFile)
                .collect(Collectors.toList());
        List<File> directories = Arrays.stream(
                        Objects.requireNonNull(new File(folderPath.toUri()).listFiles()))
                .parallel().filter(File::isDirectory)
                .collect(Collectors.toList());
        FileService fileServiceParallel = new FileService(files,directories);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(fileServiceParallel);

    }

    private static Path createRandomFiles() throws IOException {
        FileService fileService = new FileService(null,null);
        return fileService.createRandomFiles(Main.randomFolderName);
    }
}
