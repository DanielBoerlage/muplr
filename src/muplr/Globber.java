package muplr;

import java.nio.file.SimpleFileVisitor;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.nio.file.FileVisitResult;
import java.nio.file.FileSystems;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class Globber extends SimpleFileVisitor<Path> {

    private final PathMatcher[] pathMatchers;
    private static Playlist playlist;

    private Globber(String[] patterns) {
        pathMatchers = new PathMatcher[patterns.length];
        for(int i = 0; i < pathMatchers.length; i++)
            pathMatchers[i] = FileSystems.getDefault().getPathMatcher("glob:" + patterns[i]);
    }

    public static Playlist loadPlaylist(String[] patterns) {
        playlist = new Playlist();
        Globber globber = new Globber(patterns);
        try {
            Files.walkFileTree(Main.workingDirectory, globber);
        } catch (IOException e) {
            Output.printErr("Exception in Input.Globber");
        }
        return playlist;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
        for(PathMatcher pathMatcher : pathMatchers) {
            if(pathMatcher.matches(Main.workingDirectory.relativize(path))) {
                playlist.add(path.toFile());
                break;
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path path, IOException e) {
        Output.printErr("Input.Globber IOException generated message " + e.getMessage());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir,BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }
}