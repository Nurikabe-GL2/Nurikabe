package io.github.nurikabe;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("resource")
public class IOUtils {
    public static final Path ROOT_PATH;

    static {
        try {
            final var classesRootPath = Path.of(IOUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (Files.isDirectory(classesRootPath)) {
                ROOT_PATH = classesRootPath;
            } else {
                ROOT_PATH = FileSystems.newFileSystem(classesRootPath).getPath("/");
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public static String getFileNameNoExtensions(@NotNull Path path) {
        final var fileName = path.getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
}
