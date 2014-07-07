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

	private static String completePath;
	private static String[] fragmentedPath;
	private static Playlist EMPTY_PLAYLIST = new Playlist();

	public static Playlist glob(String arg) {
		completePath = arg;
		fragmentedPath = arg.split("[/\\\\]");
		if(Utils.isAbsolutePath(arg))
			return loadPlaylist(Paths.get("/"), 1);
		return loadPlaylist(Main.WORKING_DIRECTORY, 0);
	}

	public static Playlist loadPlaylist(Path workingPath, int n) {
		if(n >= fragmentedPath.length)
			return EMPTY_PLAYLIST;
		switch(fragmentedPath[n]) {
			case ".":
				return loadPlaylist(workingPath, n+1);

			case "..":
				Path parent = workingPath.getParent();
				if(parent == null)
					Main.error("Invalid path: " + completePath);
				else
					return loadPlaylist(parent, n+1);

			/*case "**":
				PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + Utils.joinTrailingPath(fragmentedPath, n++));
				System.out.println(Utils.joinTrailingPath(fragmentedPath, n));
				int globSize = Math.max(1, fragmentedPath.length - n);
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
				if(fragmentedPath[n].matches("[^\\*\\?\\[\\]\\{\\}]*")) {  // if there is no globbish syntax in the path, no need to sweep the path
					File newPath = workingPath.resolve(fragmentedPath[n]).toFile();
					if(newPath.exists()) {
						if(newPath.isDirectory())
							return loadPlaylist(newPath.toPath(), n+1);
						else if(n == fragmentedPath.length - 1)
							return new Playlist(newPath);
					} else {
						if(n == fragmentedPath.length - 1)
							Main.error("File does not exist: " + Utils.joinPathHead(fragmentedPath, fragmentedPath.length));
						else
							Main.error("Directory does not exist: " + Utils.joinPathHead(fragmentedPath, n + 1) + "/");
						return EMPTY_PLAYLIST;
					}
				}
				try(DirectoryStream<Path> stream = Files.newDirectoryStream(workingPath, fragmentedPath[n])) {
					Playlist playlist = new Playlist();
					for(Path entry : stream) {
						if(entry.toFile().isDirectory())
							playlist.add(loadPlaylist(entry, n+1));
						else if(n == fragmentedPath.length - 1)
							playlist.add(entry.toFile());
					}
					return playlist;
				} catch (IOException e) {
					Main.error("Error when accessing file: " + completePath);
				}
		}
		return EMPTY_PLAYLIST;
	}
}