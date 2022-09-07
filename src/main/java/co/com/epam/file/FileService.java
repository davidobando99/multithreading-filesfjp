package co.com.epam.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FileService extends RecursiveTask<String> {
    private final List<File> directoryList;
    private final List<File> fileList;
    private File selected;

    public FileService(List<File> directoryList, List<File> fileList) {
        this.directoryList = directoryList;
        this.fileList = fileList;
    }

    public FileService(List<File> directoryList, List<File> fileList, File selected) {
        this.directoryList = directoryList;
        this.fileList = fileList;
        this.selected = selected;
    }


    public Path createRandomFiles(String randomFolderName) throws IOException {
        FolderService folderService = new FolderService();
        Path randomFolderPath = folderService.createFolder(randomFolderName);
        for (int i = 0; i < 1000; i++) {
            new File(randomFolderPath.toString() + "/" + i + ".txt").createNewFile();
        }
        return randomFolderPath;
    }

    @Override
    protected String compute() {
        if (this.fileList.size() == 0 || this.directoryList.size() == 0) {
            return fileList.toString();
        }
        File file = this.fileList.get(0);
        File directory = this.fileList.get(0);
        this.fileList.remove(0);
        FileService fileService = new FileService(this.directoryList, this.fileList, file);
        FileService directoryService = new FileService(this.directoryList, this.fileList,directory);
        fileService.fork();
        System.out.println(directory);
        System.out.println(file);
        return directoryService.compute() + fileService.join();
    }
}
