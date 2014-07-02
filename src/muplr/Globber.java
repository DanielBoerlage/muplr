package muplr;

import java.nio.file.SimpleFileVisitor;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.FileVisitResult;
import java.nio.file.FileSystems;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class Globber extends SimpleFileVisitor<Path> {

	private final PathMatcher pathMatcher;

	Globber(String pattern) {
        pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        // do stuff
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) {
        Output.printErr(e.getMessage());
        return FileVisitResult.CONTINUE;
    }
}