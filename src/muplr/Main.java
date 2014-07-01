package muplr;

import java.util.Properties;
import java.lang.IllegalArgumentException;
import java.lang.NumberFormatException;

public class Main {

	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Usage: muplr [-r] [-v volume] FilePattern [FilePattern2..]");
			System.exit(1);
		}
		Properties properties = null;
		try {
			properties = parseArgs(args);
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		System.out.println(properties.getProperty("repeat","false"));
		System.out.println(properties.getProperty("volume","100"));
	}

	private static Properties parseArgs(String[] args) throws IllegalArgumentException {
		Properties properties = new Properties();
		int i = 0;
		while(args[i].startsWith("-")) {
			if(args[i].equals("-r") || args[i].equals("--repeat"))
				properties.setProperty("repeat", "true");
			else if(args[i].equals("-v") || args[i].equals("--volume")) {
				i++;
				int volume = 0;
				try {
					volume = Integer.parseInt(args[i]);
				} catch(NumberFormatException e) {
					throw new IllegalArgumentException("The volume option must be followed by a valid integer");
				}
				if(volume < 0 || volume > 100)
					throw new IllegalArgumentException("Volume must be between 0 and 100");
				properties.setProperty("volume", args[i]);
			}
			else
				throw new IllegalArgumentException("Unrecognized option: " + args[i]);
			i++;
		}
		return properties;
	}
}