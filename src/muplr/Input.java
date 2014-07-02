package muplr;

import java.util.Properties;
import java.lang.IllegalArgumentException;
import java.lang.NumberFormatException;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.FileVisitResult;
import java.nio.file.FileSystems;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class Input {

	private final static String usage = "Usage: muplr [-r] [-v volume] File(Pattern) [File(Pattern)..]";

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
		}





		System.out.println(properties.getProperty("repeat","false"));
		System.out.println(properties.getProperty("volume","100"));
		System.out.println(Main.workingDirectory);
	}

	private class Globber extends SimpleFileVisitor<Path> {

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
}