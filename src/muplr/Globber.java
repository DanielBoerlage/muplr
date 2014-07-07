package muplr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.nio.file.InvalidPathException;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.PathMatcher;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

public class Globber {

	private static String[] argPath;

	public static Playlist glob(String arg) {
		argPath = arg.split("[/\\\\]");
		if(Utils.isAbsolute(arg))
			return loadPlaylist(Paths.get("/"), 1);
		return loadPlaylist(Main.workingDirectory, 0);
	}

	public static Playlist loadPlaylist(Path path, int n) {
		if(n == argPath.length)
			return new Playlist();
		switch(argPath[n]) {
			case ".":
				return loadPlaylist(path, n + 1);

			case "..":
				Path parent = path.getParent();
				if(parent == null)
					throw new InvalidPathException(Utils.joinPath(argPath), "Error when parsing file path - Root directory doesn't have a parent directory");
				else
					return loadPlaylist(parent, n + 1);

			/*case "**":
				PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + Utils.joinTrailingPath(argPath, n++));
				System.out.println(Utils.joinTrailingPath(argPath, n));
				int globSize = Math.max(1, argPath.length - n);
				System.out.println(globSize);
				Playlist playlist = new Playlist();
				try {
					Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult visitFile(Path entry, BasicFileAttributes attrs) {
							System.out.println(Utils.getPathTail(entry, globSize));
							if(matcher.matches(Utils.getPathTail(entry, globSize))) {
								playlist.add(entry.toFile());
							}
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFileFailed(Path file, IOException e) {
							Main.error(e);
							return FileVisitResult.CONTINUE;
						}
					});
				} catch(IOException e) {
					Main.error(e);
				}
				return playlist;*/

			default:
				if(argPath[n].matches("[^\\*\\?\\[\\]\\{\\}\\!]*")) {  // if there is no globbish syntax in the path, no need to sweep the path
					File newPath = path.resolve(argPath[n]).toFile();
					if(newPath.exists()) {
						if(newPath.isDirectory())
							return loadPlaylist(newPath.toPath(), n + 1);
						else if(n == argPath.length - 1)
							return new Playlist(newPath);
					} else {
						Main.error("file does not exist");
						return new Playlist();
					}
				}
				try(DirectoryStream<Path> stream = Files.newDirectoryStream(path, argPath[n])) {
					Playlist playlist = new Playlist();
					for(Path entry : stream) {
						if(entry.toFile().isDirectory())
							playlist.add(loadPlaylist(entry, n + 1));
						else if(n == argPath.length - 1)
							playlist.add(entry.toFile());
					}
					return playlist;
				} catch (IOException e) {
					Main.error(e);
				}
		}
		return new Playlist();
	}
}