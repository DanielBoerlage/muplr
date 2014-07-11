package muplr;

import java.util.Properties;

public class Input {

	private final static String USAGE = "Usage: muplr [-r] [-v volume] File [File..]";

	public static Playlist parseArgs(String[] args) {
		int i = 0;
		for(; i < args.length && args[i].startsWith("-"); i++) {
			switch(args[i]) {
				case "-h": case "--help":
					Output.puts(USAGE);
					Main.exitSuccess();

				case "-r": case "--repeat":
					Main.properties.setProperty("repeat", "true");
					break;

				case "-v": case "--volume":
					try {
						int volume = Integer.parseInt(args[++i]);
						if(volume < 0 || volume > 100)
							Main.fatalError("Volume must be between 0 and 100");
						Main.properties.setProperty("volume", args[i]);
					} catch(NumberFormatException e) {
						Main.fatalError("The volume option must be followed by a valid integer");
					}
					break;

				default:
					Main.fatalError("Unrecognized option: " + args[i]);
			}
		}
		if(i == args.length)
			Main.fatalError(USAGE);

		Playlist playlist = new Playlist();
		for(; i < args.length; i++)
			playlist.add(Globber.glob(args[i]));

		return playlist;
	}
}