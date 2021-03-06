package muplr;

import org.fusesource.jansi.AnsiConsole;

public class Output {

	private final static String ESC = "\033[";

	private static void print(Object obj, int... textAttr) {
		String attr = ESC;
		for(int code : textAttr)
			attr += code + ";";
		AnsiConsole.out.print(attr + "m" + obj + ESC + "0m");
	}

	public static void clear() {
		print(ESC + "2J" + ESC + "f");
	}

	public static void puts(String str) {
		print(str + "\n");
	}

	public static void printErr(String str) {
		print(str + "\n", 31, 1);  // red, bold
	}

	public static void printHeader(Playlist playlist, int width) {
		print("  muplr" + Utils.nChars(' ', width - 9 - playlist.toString().length()) + playlist + "  ", 30, 47);  // white background, black foreground
	}

	public static void printPlaying(String songName, boolean paused) {
		print(ESC + "2;2f");
		if(songName.length() > 53)
			songName = songName.substring(0, 50) + "...";
		print("Playing track: ");
		print(songName, 32, 1);
		if(paused)
			print("  (paused)", 1);
	}

	public static void printStatus(int position, int total) {
		print(ESC + "3;2f");
		int status = (int)((double)(position) / total * 64.0);
		print(timeRep(position) + " [" + Utils.nChars('#', status), 1);
		print(Utils.nChars((char)250, 64 - status));
		print("] " + timeRep(total), 1);
	}

	private static String timeRep(int seconds) {
		return String.format("%2d:%02d", seconds / 60, seconds % 60);
	}

	public static void printProperties(int volume, boolean repeat) {
		print(ESC + "4;2f");
		print("Volume: " + volume + "   Repeat: " + (repeat ? "on" : "off"));
	}

	public static void printDivider() {
		print(ESC + "5;f");
		print(Utils.nChars((char)196, 80));
	}

	public static void printPlaylist(Playlist playlist, int index, int playing, int rows) {
		print(ESC + "6;f");
		if(index == 0)
			print(playlist, 34, 1);
		else
			print("...");
		int i = index;
		while(true) {
			String songName = playlist.get(i).getName();
			songName = songName.substring(0, songName.indexOf("."));
			if(i == playing)
				print("\n> " + songName, 32, 1);
			else
				print("\n  " + songName, 32);
			if(i >= index + rows - 1) {
				if(i < playlist.size() - 1)
					print("\n...");
				break;
			}
			if(i >= playlist.size() - 1)
				break;
			i++;
		}
	}

	public static void printPrompt() {
		print(ESC + "25;f");
		print(":");
	}
}