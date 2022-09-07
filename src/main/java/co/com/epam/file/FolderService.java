package co.com.epam.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FolderService {
    public Path createFolder(String folderName) throws IOException {
        Path source = Paths.get(Objects.requireNonNull(this.getClass().getResource("/")).getPath());
        Path newFolder = Paths.get(source.toAbsolutePath() + "/resources/"+folderName);
        return Files.createDirectories(newFolder);
    }

    public Path getPathFromFolderName(String folderName){
        Path source = Paths.get(Objects.requireNonNull(this.getClass().getResource("/")).getPath());
        return Paths.get(source.toAbsolutePath() + "/resources/"+folderName);
    }
}
