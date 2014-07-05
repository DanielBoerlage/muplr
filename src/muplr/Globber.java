package muplr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.nio.file.InvalidPathException;

public class Globber {

	public static Playlist loadPlaylist(Path path, String[] argPath, int n) throws InvalidPathException {
		System.out.println("path: " + path);
		if(n == argPath.length)
			return new Playlist();
		switch(argPath[n]) {
			case ".":
				return loadPlaylist(path, argPath, n + 1);
			case "..":
				Path parent = path.getParent();
				if(parent == null)
					throw new InvalidPathException(Utils.joinPath(argPath), "Error when parsing file path");
				else
					return loadPlaylist(parent, argPath, n + 1);
			case "**":


			default:
				if(argPath[n].matches("[^\\*\\?\\[\\]\\!]*")) {  // if there is no globbish syntax in the path, no need to sweep the path
					System.out.println("argP: " + argPath[n]);
					File newPath = path.resolve(argPath[n]).toFile();
					if(newPath.exists()) {
						if(newPath.isDirectory())
							return loadPlaylist(newPath.toPath(), argPath, n + 1);
						else if(n == argPath.length - 1)
							return new Playlist(newPath);
					}
				}
				try(DirectoryStream<Path> stream = Files.newDirectoryStream(path, argPath[n])) {
					Playlist playlist = new Playlist();
					for(Path entry : stream) {
						if(entry.toFile().isDirectory())
							playlist.add(loadPlaylist(entry, argPath, n + 1));
						else if(n == argPath.length - 1)
							playlist.add(entry.toFile());
					}
					return playlist;
				} catch (IOException e) {
					Main.exit(e);
				}
		}
		return new Playlist();
	}
}