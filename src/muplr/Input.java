package muplr;

import java.util.Properties;
import java.lang.IllegalArgumentException;
import java.lang.NumberFormatException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryIteratorException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

public class Input {

	private final static String usage = "Usage: muplr [-r] [-v volume] File [File..]";

	public static void parseArgs(String[] args) {
		if(args.length < 1)
			Main.exit(-1, usage);
		if(args[0].equals("-h") || args[0].equals("--help"))
			Main.exit(0, usage);
		Properties properties = new Properties();
		int i = 0;
		while(args[i].startsWith("-")) {

			if(args[i].equals("-r") || args[i].equals("--repeat"))
				properties.setProperty("repeat", "true");

			else if(args[i].equals("-v") || args[i].equals("--volume")) {
				i++;
				try {
					int volume = Integer.parseInt(args[i]);
					if(volume < 0 || volume > 100)
						Main.exit(-1, "Volume must be between 0 and 100");
					properties.setProperty("volume", args[i]);
				} catch(NumberFormatException e) {
					Main.exit(-1, "The volume option must be followed by a valid integer");
				}
			}

			else
				Main.exit(-1, "Unrecognized option: " + args[i]);
			
			i++;
			if(i == args.length) // no file specified
				Main.exit(-1, usage);
		}

		Playlist loadedPlaylist = new Playlist();
		/*while(i < args.length) {
			System.out.println("arg [" + i + "]: " + args[i]);
			try(DirectoryStream<Path> stream = Files.newDirectoryStream(Main.workingDirectory, args[i])) {
				for(Path path : stream) {
					loadedPlaylist.add(path.toFile());
				}
			} catch (IOException e) {
				Main.exit(-1, e.getMessage());  // cause is of type IOException
			}
			i++;
		}*/

		/*for(; i < args.length; i++) {
			try {
				Path path = Paths.get(args[i]).toAbsolutePath().normalize();
				System.out.println(path);
			} catch(InvalidPathException e) {
				e.printStackTrace();
			}
		}*/
		
		/*Path path = Paths.get("dir/subdir/file.txt");
		System.out.println(path);
		System.out.println(path.getRoot());
		System.out.println(path.toAbsolutePath().getRoot());
		System.out.println(path.getParent());
		System.out.println(path.getName(0));
		System.out.println(path.getName(0).relativize(path));

		System.out.println();

		for(String str : path.toString().split("[/\\\\]"))
			System.out.println(str);*/

		for(; i < args.length; i++) {
			System.out.println("arg [" + i + "]: " + args[i]);
			loadedPlaylist.add(Globber.glob(args[i]));
		}

		System.out.println(loadedPlaylist);


	}
}